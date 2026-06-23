/*
 * MIT License
 *
 * Copyright (c) 2026 Mark Schmieder. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * This file is part of the FxGuiToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * FxGuiToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxguitoolkit
 */
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.fxcontrols.control.XTextField;
import com.mhschmieder.jcommons.time.DurationFormat;
import com.mhschmieder.jcommons.time.DurationUtilities;
import com.mhschmieder.jcommons.time.TimeUtilities;
import com.mhschmieder.jcommons.util.ClientProperties;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyCode;

import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class DurationEditor extends XTextField {

    private static System.Logger LOGGER = System.getLogger( DurationEditor
            .class.getName() );

    // Cache the default data value, to use when backing out edits.
    protected Duration defaultValue = Duration.ZERO;

    // The amount to increment or decrement by, using the arrow keys.
    protected long valueIncrementSeconds;

    // Cache the raw numeric representation of the data value.
    // NOTE: This field must follow JavaFX Property Beans conventions.
    private final ObjectProperty< Duration > value;

    // ChronoUnit reference for how to interpret raw unformatted typed values.
    protected final ChronoUnit chronoUnit;

    // The format to use for the string/text representation of duration/time.
    private final DurationFormat durationFormat;

    // Number format cache used for locale-specific number parsing.
    protected NumberFormat numberParse;

    public DurationEditor( final ClientProperties pClientProperties,
                           final String pTooltipText,
                           final Duration pInitialValue,
                           final long pValueIncrementSeconds,
                           final ChronoUnit pChronoUnit,
                           final DurationFormat pDurationFormat ) {
        // Always call the superclass constructor first!
        super(
                pInitialValue.toString(),
                pTooltipText,
                true,
                pClientProperties );

        valueIncrementSeconds = pValueIncrementSeconds;
        chronoUnit = pChronoUnit;
        durationFormat = pDurationFormat;

        value = new SimpleObjectProperty<>( pInitialValue );

        try {
            initEditor();
        }
        catch ( final Exception ex ) {
            LOGGER.log( System.Logger.Level.ERROR, ex.getMessage(), ex );
        }
    }

    private void initEditor() {
        numberParse = NumberFormat.getNumberInstance( clientProperties.locale );
        numberParse.setGroupingUsed( true );

        // Validate committed input (via ENTER) and restrict to the legal range.
        // NOTE: Input character validation is deferred to the Duration class.
        setOnAction( evt -> {
            // Commit the current selection as-is, without giving up focus.
            commitValue();

            // Save edits from the Text Field to the property bean.
            saveEdits();

            // Post-process after caching the new value, due to order dependency
            // of the text adjustments in various callbacks.
            Platform.runLater( () -> {
                // Update the displayed text to include all formatting.
                formatText();

                // Reselect the reformatted text, to mimic Focus Gained.
                selectAll();
            } );
        } );

        // When focus is lost, commit the changes; otherwise format the text.
        focusedProperty().addListener(
                ( observableValue,
                  wasFocused,
                  isNowFocused ) -> {
                    if ( isNowFocused ) {
                        // Update the displayed text to include all formatting.
                        formatText();
                    }
                    else {
                        // Save edits from the Text Field to the property bean.
                        saveEdits();

                        // Update the displayed text to match the cached value.
                        updateText();
                    }
                } );

        // Make sure the value property is checked for non-null assignment, then
        // update the text field to be in sync with the formatted value.
        valueProperty().addListener(
                ( observableValue,
                  oldValue,
                  newValue ) -> {
                    // Format the number to match how we display committed
                    // values.
                    updateText( newValue );
                } );

        setOnKeyPressed( keyEvent -> {
            final KeyCode keyCode = keyEvent.getCode();
            switch ( keyCode ) {
                case ENTER:
                    // NOTE: Nothing to do; ENTER is best handled via onAction.
                    break;
                case ESCAPE:
                    // Revert to the most recent committed value.
                    cancelEdit();

                    // Post-process after caching the reverted value, due to
                    // order dependency of text adjustments in various callbacks.
                    Platform.runLater( () -> {
                        // Update the displayed text to include all decorations.
                        formatText();

                        // Reselect the reformatted text, to mimic Focus Gained.
                        selectAll();
                    } );

                    break;
                case TAB:
                    // NOTE: Nothing to do, as Text Input Controls commit edits
                    //  and then release focus when the TAB key is pressed, so
                    //  the Focus Lost handler is where value restrictions
                    //  should be applied.
                    break;
                case UP:
                    // Increment the current value by the set amount.
                    if ( valueIncrementSeconds != 0L ) {
                        setValue( getValue().plusSeconds(
                                valueIncrementSeconds ) );
                    }

                    break;
                case DOWN:
                    // Decrement the current value by the set amount.
                    if ( valueIncrementSeconds != 0L ) {
                        setValue( getValue().minusSeconds(
                                valueIncrementSeconds ) );
                    }

                    break;
                // $CASES-OMITTED$
                default:
                    break;
            }
        } );
    }

    public final void formatText() {
        // Get the most recently committed value.
        final Duration savedValue = getValue();

        // Get the most recently committed value, restoring formatting etc.
        final String formattedText = toString( savedValue );

        // Update the displayed text to include all formatting.
        setText( formattedText );
    }

    public final void saveEdits() {
        // Update the cached property from the edited value.
        final String savedText = getText();

        // Set the new value from the parsed text, which strips the formatting
        // as it is not part of the storage and differs on input vs. output.
        final Duration newValue = fromString( savedText );
        setValue( newValue );
    }

    public final void updateText() {
        // Get the most recently committed value.
        final Duration savedValue = getValue();

        // Update the displayed text to match the cached value.
        updateText( savedValue );
    }

    public final void updateText( final Duration newValue ) {
        try {
            // Show the formatted value to indicate we committed edits.
            final String formattedValue = toString( newValue );

            // Update the displayed text to match the cached value.
            setText( formattedValue );
        }
        catch ( final Exception e ) {
            LOGGER.log( System.Logger.Level.ERROR, e.getMessage(), e );
        }
    }

    public final void setValueIncrementSeconds(
            final long pValueIncrementSeconds ) {
        valueIncrementSeconds = pValueIncrementSeconds;
    }

    public final ObjectProperty< Duration > valueProperty() {
        return value;
    }

    public final Duration getValue() {
        return value.get();
    }

    public final void setValue( final Duration pValue ) {
        value.set( pValue );
    }

    /**
     * Converts the specified duration string to a Duration instance.
     * <p>
     * A {@code null}, empty, or otherwise invalid argument returns zero and
     * also executes the textField reset callback, if any.
     * <p>
     * NOTE: Text may be in ISO-8601 duration format, especially if unedited.
     *
     * @param durationText
     *            The duration string to convert to a Duration instance
     * @return The Duration instance value of {@code durationText}
     */
    public Duration fromString( final String durationText ) {
        // Return with current value vs. penalizing user for internal errors.
        final Duration currentValue = getValue();

        // Try to directly convert the string to a long integer.
        Duration newValue;
        try {
            newValue = DurationUtilities.getDurationFromText(
                    durationText,
                    numberParse,
                    chronoUnit );
        }
        catch ( final ParseException pe ) {
            try {
                newValue = parseDurationText( durationText );
            }
            catch ( final Exception ex ) {
                // Restore the previous value if there were parsing errors.
                // NOTE: No need to report the exception as it isn't a
                //  programming error in need of being fixed; just a user error.
                //  The reappearance of the previous value is enough feedback.
                newValue = currentValue;
            }
        }

        return newValue;
    }

    /**
     * Converts the specified duration/time string to a Duration instance.
     *
     * @param durationText
     *            The duration/time string to convert to a Duration instance
     * @return The Duration instance value of {@code durationText}
     */
    protected Duration parseDurationText( final String durationText )
            throws DateTimeParseException {
        Duration newDuration;

        switch( durationFormat ) {
            case ISO_8601 ->
                // Convert from ISO-8601 duration string to a Duration instance.
                    newDuration = Duration.parse( durationText );
            case HHH_MM_SS -> {
                // Convert from HHH:MM:SS time format to a Duration instance.
                final long seconds = TimeUtilities
                        .secondsFromFormattedHoursMinutesSeconds(
                                durationText );
                newDuration = Duration.ofSeconds( seconds );
            }
            default -> throw new InvalidParameterException(
                    "Unknown Duration Format for Conversion" );
        }

        return newDuration;
    }

    /**
     * Converts the specified duration to ISO-8601 duration format.
     *
     * @param duration
     *            The duration to convert to ISO-8601 duration format
     * @return The ISO-8601 string form of {@code duration}
     */
    public String toString( final Duration duration ) {
        String newText;

        switch( durationFormat ) {
            case ISO_8601 ->
                // Convert to ISO-8601 duration string from a Duration instance.
                    newText = duration.toString();
            case HHH_MM_SS -> {
                // Convert to HHH:MM:SS time format from a Duration instance.
                final long timeSeconds = duration.toSeconds();
                newText = TimeUtilities.secondsToFormattedHoursMinutesSeconds(
                        timeSeconds );
            }
            default -> throw new InvalidParameterException(
                    "Unknown Duration Format for Conversion" );
        }

        return newText;
    }
}

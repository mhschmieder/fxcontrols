/**
 * MIT License
 *
 * Copyright (c) 2020, 2023 Mark Schmieder
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
package com.mhschmieder.fxguitoolkit.control;

import com.mhschmieder.commonstoolkit.text.NumberFormatUtilities;
import com.mhschmieder.commonstoolkit.util.ClientProperties;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyEvent;
import org.apache.commons.math3.util.FastMath;

/**
 * This class formalizes aspects of list selection that are specific to
 * long value sets.
 */
public class LongSelector extends NumberSelector {

    public LongSelector( final ClientProperties clientProperties,
                         final boolean useLocale,
                         final String tooltipText,
                         final boolean applyToolkitCss,
                         final boolean editable,
                         final boolean searchable ) {
        // Always call the superclass constructor first!
        super( clientProperties,
               0,
               0,
               0,
               0,
               useLocale,
               tooltipText,
               applyToolkitCss,
               editable,
               searchable );
    }

    public LongSelector( final ClientProperties clientProperties,
                         final boolean useLocale,
                         final String tooltipText,
                         final boolean applyToolkitCss,
                         final boolean editable,
                         final boolean searchable,
                         final long minimumValue,
                         final long maximumValue,
                         final long increment ) {
        this( clientProperties, 
              useLocale, 
              tooltipText, 
              applyToolkitCss, 
              editable, 
              searchable );

        try {
            initComboBox( minimumValue, maximumValue, increment );
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("nls")
    private final void initComboBox( final long minimumValue,
                                     final long maximumValue,
                                     final long increment ) {
        // Put together the monotonically increasing list of choices.
        // NOTE: Static arrays can't be allocated with sizes that fit in long
        //  vs. int, so we use int here, but we should switch to a collection.
        final int numberOfChoices = ( int ) FastMath
                .floor( ( ( maximumValue - minimumValue ) + 1 ) / increment );
        final String[] longValues = new String[ numberOfChoices ];

        long longValue = minimumValue;
        for ( int i = 0; i < numberOfChoices; i++ ) {
            longValues[ i ] = NumberFormatUtilities.formatLong( longValue, _numberFormat );
            longValue += increment;
        }

        // Ensure that most items are visible before scrolling, but also make
        // sure the overall list doesn't get unwieldy.
        setVisibleRowCount( FastMath.min( numberOfChoices, 25 ) );

        // Set the non-editable list of supported integer values.
        setItems( FXCollections.observableArrayList( longValues ) );

        // Restrict keyboard input to numerals, sign, and delimiters.
        final String allowedCharacters = ( minimumValue < 0 )
            ? ( maximumValue > 0 ) ? "[0-9.,+-]" : "[0-9.,-]"
            : ( maximumValue > 0 ) ? "[0-9.,+]" : "[0-9.,]";
        addEventFilter( KeyEvent.KEY_TYPED, keyEvent -> {
            if ( !keyEvent.getCharacter().matches( allowedCharacters ) ) {
                keyEvent.consume();
            }
        } );
    }

    public final long getLongValue() {
        final String formattedValue = getValue();
        final long longValue = NumberFormatUtilities.parseLong( formattedValue, _numberFormat );
        return longValue;
    }

    public final void setLongValue( final long longValue ) {
        final String formattedValue = NumberFormatUtilities.formatLong( longValue,
                                                                        _numberFormat );
        setValue( formattedValue );
    }

}

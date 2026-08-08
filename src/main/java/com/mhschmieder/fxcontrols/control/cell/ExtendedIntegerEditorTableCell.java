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
 * This file is part of the fxcontrols Library
 *
 * You should have received a copy of the MIT License along with the fxcontrols
 * Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxcontrols
 */
package com.mhschmieder.fxcontrols.control.cell;

import com.mhschmieder.fxcontrols.control.NumberEditor;
import com.mhschmieder.jcommons.lang.Abbreviated;
import com.mhschmieder.jcommons.lang.Labeled;
import com.mhschmieder.jcommons.util.ClientProperties;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * An extension of {@link IntegerEditorTableCell} that adds support for unit
 * conversion between the UI data model and value displayed to the user.
 *
 * @param <RT> The type of the row data
 * @param <ET> The enum used for the units in the UI model and displayed value
 */
public abstract class ExtendedIntegerEditorTableCell< RT, ET extends Enum< ET > & Labeled< ET > & Abbreviated< ET > >
        extends IntegerEditorTableCell< RT, ET > {

    /**
     * Represents the data model unit associated with the current table cell.
     * This unit is used to handle data model-specific operations such as
     * conversion or representation in the context of the cell.
     * <p>
     * The unit is final as a constant is needed to ensure proper unit
     * conversion. It is used within the cell to coordinate between display
     * units and data model values.
     */
    protected final ET dataModelUnit;

    /**
     * Represents an observable property that defines the unit type used for
     * displaying values in the table cell. The unit type is generic and denoted
     * by the parameterized type {@code ET}.
     * <p>
     * This property is intended to allow dynamic updates to the display unit
     * without requiring individual listeners for each table cell. The
     * {@code displayUnit} is linked with {@link #getDisplayValue(Integer)} and
     * {@link #getDataModelValue(Integer)} to ensure proper synchronization
     * between the displayed values and the unit of measurement.
     */
    protected ObjectProperty< ET > displayUnit;

    protected ExtendedIntegerEditorTableCell( final boolean pAllowedToBeBlank,
                                              final ET pDataModelUnit,
                                              final ClientProperties pClientProperties ) {
        this( null, pAllowedToBeBlank, pDataModelUnit, pClientProperties );
    }

    protected ExtendedIntegerEditorTableCell( final List< Integer > pUneditableRows,
                                              final boolean pAllowedToBeBlank,
                                              final ET pDataModelUnit,
                                              final ClientProperties pClientProperties ) {
        // Always call the superclass constructor first!
        super( pUneditableRows, pAllowedToBeBlank, pClientProperties );

        dataModelUnit = pDataModelUnit;
        displayUnit = new SimpleObjectProperty<>( dataModelUnit );
    }

    @Override
    public void commitEdit( final Integer newValue ) {
        // NOTE: The value passed in is in the units of the displayed value and
        //  needs to be converted back into the units of the backend data model
        //  prior to updating the backend data model.
        final int dataModelValue = getDataModelValue( newValue );
        super.commitEdit( dataModelValue );
    }

    @Override
    public void updateItem( final Integer item,
                            final boolean empty ) {
        setMeasurementUnit( displayUnit.get().abbreviation() );

        // First get a handle on the textField from the EditorTableCell base
        // class to update the measurement unit string.
        if ( textField instanceof NumberEditor editor ) {
            editor.setMeasurementUnitString( displayUnit.get().abbreviation() );
        }

        Integer displayValue = null;

        // A manual check needs to be done since the passed value is the Double
        // class instead of primitive, which could cause the conversion to throw
        // an exception.
        if ( item != null ) {
            displayValue = getDisplayValue( item );
        }

        // Pass the value to the superclass for display without impacting the
        // backend data model.
        super.updateItem( displayValue, empty );
    }

    /**
     * Converts a given data model value into a display value based on the
     * {@link #displayUnit} property value.
     *
     * @param pDataModelValue The value from the data model to be converted into
     *                        a display value; may be null depending on the
     *                        implementation.
     * @return The converted display value; may return null if the input value
     *         is null or if the conversion cannot be performed.
     */
    public abstract Integer getDisplayValue( final Integer pDataModelValue );

    /**
     * Converts a given display value into a corresponding data model value
     * based on the specified {@code dataModelUnit} property.
     *
     * @param pDisplayValue The display value to be converted into a data model
     *                      value; may be null depending on the implementation
     * @return The converted data model value; may return null if the input
     *         value is null or if the conversion cannot be performed
     */
    public abstract Integer getDataModelValue( final Integer pDisplayValue );

    /**
     * Updates the instance of the {@link ObjectProperty} used to determine the
     * units to be used for displaying distance to the user.
     * <p>
     * NOTE: The observable is passed in to avoid the need for adding a listener
     *  for every table cell and for removing a listener when the cell is no
     *  longer in use.
     * @param pDisplayUnit The new observable property with the unit to display.
     */
    public void setDisplayUnitProperty(
            final ObjectProperty< ET > pDisplayUnit ) {
        displayUnit = pDisplayUnit;
    }
}

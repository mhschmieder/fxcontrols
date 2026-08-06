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

import com.mhschmieder.fxcontrols.control.DoubleEditor;
import com.mhschmieder.jcommons.lang.Abbreviated;
import com.mhschmieder.jcommons.lang.Labeled;
import com.mhschmieder.jcommons.util.ClientProperties;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TextField;

public abstract class ExtendedDoubleEditorTableCell< RT, ET extends Enum< ET > & Labeled< ET > & Abbreviated< ET > >
        extends ExtendedNumberEditorTableCell< RT, ET > {

    // Cache the raw Double representation of the data cachedValue.
    // NOTE: This field has to follow JavaFX Property Beans conventions.
    private final DoubleProperty cachedValue;

    protected ExtendedDoubleEditorTableCell( final boolean pAllowedToBeBlank,
                                             final ET pDataModelUnit,
                                             final ClientProperties pClientProperties ) {
        this( null, pAllowedToBeBlank, pDataModelUnit, pClientProperties );
    }

    protected ExtendedDoubleEditorTableCell( final List< Integer > pUneditableRows,
                                             final boolean pAllowedToBeBlank,
                                             final ET pDataModelUnit,
                                             final ClientProperties pClientProperties ) {
        // Always call the superclass constructor first!
        super( pUneditableRows,
               pAllowedToBeBlank,
               pDataModelUnit,
               pClientProperties );

        cachedValue = new SimpleDoubleProperty( 0.0d );

        // Use two decimal places of precision for doubles, in the default
        // locale.
        _numberFormat.setMaximumFractionDigits( 2 );
        _numberFormat.setParseIntegerOnly( false );
    }

    @Override
    protected Double getEditorValue() {
        final String textValue = textField.getText();
        if ( textValue == null ) {
            return null;
        }

        final double doubleValue = ( ( DoubleEditor ) textField ).fromString(
                textValue );

        return Double.valueOf( doubleValue );
    }

    @Override
    protected String getString() {
        final Number value = getItem();
        if ( value == null ) {
            return "";
        }

        // This text goes to the editor, so we don't want to clutter the user's
        // editing session with measurement units, but do need localization.
        final String stringValue
                =
                ( ( DoubleEditor ) textField ).toFormattedString( value.doubleValue() );

        return stringValue;
    }

    @Override
    protected TextField makeTextField() {
        return new DoubleEditor( clientProperties,
                                 "0",
                                 "",
                                 blankTextAllowed,
                                 0,
                                 2,
                                 0,
                                 4 );
    }

    @Override
    protected String getTextValue() {
        final Number value = getItem();
        if ( value == null ) {
            return "";
        }

        final String textValue
                =
                ( ( DoubleEditor ) textField ).toString( value.doubleValue() );

        return textValue;
    }

    @Override
    public final void setValue( final Number pValue ) {
        // Locally cache the new cachedValue, separately from the textField.
        cachedValue.set( pValue.doubleValue() );

        // Now do whatever we do for all data types in the base class.
        super.setValue( pValue );
    }

    public final DoubleProperty cachedValueProperty() {
        return cachedValue;
    }

    public final double getCachedValue() {
        return cachedValue.get();
    }

    public final void setCachedValue( final double pCachedValue ) {
        // Locally cache the new cachedValue, separately from the textField.
        cachedValue.set( pCachedValue );
    }
}

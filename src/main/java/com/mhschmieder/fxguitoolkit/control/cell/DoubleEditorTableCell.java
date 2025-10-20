/**
 * MIT License
 *
 * Copyright (c) 2020, 2024 Mark Schmieder
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
 * GuiToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxguitoolkit
 */
package com.mhschmieder.fxguitoolkit.control.cell;

import com.mhschmieder.commonstoolkit.util.ClientProperties;
import com.mhschmieder.fxguitoolkit.control.DoubleEditor;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TextField;

import java.util.List;

public class DoubleEditorTableCell< RT, VT > extends NumberEditorTableCell< RT, Double > {

    // Cache the raw Double representation of the data cachedValue.
    // NOTE: This field has to follow JavaFX Property Beans conventions.
    private final DoubleProperty cachedValue;

    public DoubleEditorTableCell( final boolean pAllowedToBeBlank,
                                  final ClientProperties pClientProperties ) {
        this( null, pAllowedToBeBlank, pClientProperties );
    }

    public DoubleEditorTableCell( final List< Integer > pUneditableRows,
                                  final boolean pAllowedToBeBlank,
                                  final ClientProperties pClientProperties ) {
        // Always call the superclass constructor first!
        super( pUneditableRows, pAllowedToBeBlank, pClientProperties );

        cachedValue = new SimpleDoubleProperty( 0.0d );

        // Use two decimal places of precision for doubles, in the default
        // locale.
        _numberFormat.setMaximumFractionDigits( 2 );
        _numberFormat.setParseIntegerOnly( false );
    }
    
    @Override
    protected TextField makeTextField() {
        return new DoubleEditor(
            clientProperties, "0", "", blankTextAllowed, 0, 2, 0, 4 );
    }

    @Override
    protected Double getEditorValue() {
        final String textValue = textField.getText();
        if ( textValue == null ) {
            return null;
        }
        
        final double doubleValue = ( ( DoubleEditor ) textField ).fromString( textValue );
        
        return Double.valueOf( doubleValue );
    }

    @Override
    protected String getString() {
        final Double doubleValue = getItem();
        if ( doubleValue == null ) {
            return "";
        }
        
        // This text goes to the editor, so we don't want to clutter the user's
        // editing session with measurement units, but do need localization.
        final String stringValue 
            = ( ( DoubleEditor ) textField ).toFormattedString( doubleValue );
        
        return stringValue;
    }

    @Override
    protected String getTextValue() {
        final Double doubleValue = getItem();
        if ( doubleValue == null ) {
            return "";
        }
        
        final String textValue = ( ( DoubleEditor ) textField ).toString( doubleValue );
        
        return textValue;
    }

    @Override
    public final void setValue( final Double pValue ) {
        // Locally cache the new cachedValue, separately from the textField.
        cachedValue.set( pValue );

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

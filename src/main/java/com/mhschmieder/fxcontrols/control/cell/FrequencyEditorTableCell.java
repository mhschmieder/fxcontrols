/*
 * MIT License
 *
 * Copyright (c) 2020, 2026 Mark Schmieder. All rights reserved.
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
 * This file is part of the FxSigProc Library
 *
 * You should have received a copy of the MIT License along with the
 * FxSigProc Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxsigproc
 */
package com.mhschmieder.fxcontrols.control.cell;

import com.mhschmieder.fxsigproccontrols.control.FrequencyEditor;
import com.mhschmieder.fxsigproccontrols.control.SigprocControlFactory;
import com.mhschmieder.jcommons.util.ClientProperties;
import com.mhschmieder.jmath.MathUtilities;
import javafx.scene.control.TextField;
import org.apache.commons.math3.util.FastMath;

import java.util.List;

public class FrequencyEditorTableCell< RT, VT >
        extends DoubleEditorTableCell< RT, Double > {
    
    // Declare default value for precision cutoff frequency.
    public static final double PRECISION_CUTOFF_FREQUENCY_DEFAULT_HZ = 100.0d;
    
    // Declare default value for number of decimal places precision (when active).
    public static final int NUMBER_OF_DECIMAL_PLACES_DEFAULT = 1;
    
    /**
     * Precision cutoff frequency for using integers vs. decimal places.
     */
    protected double precisionCutoffFrequencyHz;
    
    /**
     * The number of decimal places to use for display purposes, when active.
     */
    protected int numberOfDecimalPlaces;

    public FrequencyEditorTableCell( final boolean pAllowedToBeBlank,
                                     final ClientProperties pClientProperties ) {
        this( null, pAllowedToBeBlank, pClientProperties );
    }

    public FrequencyEditorTableCell( final List< Integer > pUneditableRows,
                                     final boolean pAllowedToBeBlank,
                                     final ClientProperties pClientProperties ) {
        this( pUneditableRows, 
              pAllowedToBeBlank, 
              PRECISION_CUTOFF_FREQUENCY_DEFAULT_HZ,
              NUMBER_OF_DECIMAL_PLACES_DEFAULT,
              pClientProperties );
    }

    public FrequencyEditorTableCell( final List< Integer > pUneditableRows,
                                     final boolean pAllowedToBeBlank,
                                     final double pPrecisionCutoffFrequencyHz,
                                     final int pNumberOfDecimalPlaces,
                                     final ClientProperties pClientProperties ) {
        // Always call the superclass constructor first!
        super( pUneditableRows, 
               pAllowedToBeBlank, 
               pClientProperties );
        
        precisionCutoffFrequencyHz = pPrecisionCutoffFrequencyHz;
        numberOfDecimalPlaces = pNumberOfDecimalPlaces;
        
        // NOTE: As the superclass constructor calls makeTextField() before we
        //  have had a chance to set class-specific variables, we must forward
        //  those values to the associated TextField instance now.
        final FrequencyEditor frequencyEditor = ( FrequencyEditor) textField;
        frequencyEditor.setPrecisionCutoffFrequencyHz( pPrecisionCutoffFrequencyHz );
        frequencyEditor.setNumberOfDecimalPlaces( pNumberOfDecimalPlaces );
        
        // NOTE: For now, we don't support conversion to and from kHz.
        setMeasurementUnit( " Hz" );
    }

    /**
     * Returns a FrequencyEditor to do the actual editing for this table cell.
     * <p>
     * NOTE: As this is called by the superclass constructor before all local
     *  fields are initialized, we must reset the extra fields on the TextField
     *  instance in this class's constructor after the super-constructor call.
     * 
     * @return a FrequencyEditor instance that matches the table cell's settings
     */
    @Override
    protected TextField makeTextField() {
        final FrequencyEditor frequencyEditor = SigprocControlFactory
                .getFrequencyEditor( 
            clientProperties, 
            "", 
            " Hz", 
            0.0d, 
            200000.0d, 
            0.0d,
            precisionCutoffFrequencyHz,
            numberOfDecimalPlaces );

        // In the table cell context, as each row may have different
        // restrictions, it is best to say "N/A" if NaN is encountered or other
        // cues that this cell should be uneditable and maybe disabled as well.
        frequencyEditor.setErrorText( "N/A" );
        
        return frequencyEditor;
    }

    public double adjustPrecision( final double doubleValue ) {
        final double precisionAdjustedValue 
            = ( doubleValue >= precisionCutoffFrequencyHz )
            ? FastMath.round( doubleValue )
            : MathUtilities.roundDecimal( doubleValue, numberOfDecimalPlaces );
        return precisionAdjustedValue;
    }
}

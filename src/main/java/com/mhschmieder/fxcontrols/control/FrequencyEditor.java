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
 * This file is part of the fxcontrols Library
 *
 * You should have received a copy of the MIT License along with the fxcontrols
 * Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxcontrols
 */
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.jcommons.util.ClientProperties;
import com.mhschmieder.jmath.MathUtilities;
import org.apache.commons.math3.util.FastMath;

public final class FrequencyEditor extends DoubleEditor {

    // Declare default value increment/decrement amount for up and down arrow keys.
    public static final double VALUE_INCREMENT_DEFAULT_HZ = 10.0d;
    
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

    public FrequencyEditor( final ClientProperties pClientProperties,
                            final String initialText,
                            final String tooltipText,
                            final double frequencyMinimumHz,
                            final double frequencyMaximumHz,
                            final double frequencyInitialHz ) {
        this( pClientProperties,
              initialText,
              tooltipText,
              frequencyMinimumHz,
              frequencyMaximumHz,
              frequencyInitialHz,
              PRECISION_CUTOFF_FREQUENCY_DEFAULT_HZ,
              NUMBER_OF_DECIMAL_PLACES_DEFAULT );
    }

    public FrequencyEditor( final ClientProperties pClientProperties,
                            final String initialText,
                            final String tooltipText,
                            final double frequencyMinimumHz,
                            final double frequencyMaximumHz,
                            final double frequencyInitialHz,
                            final double pPrecisionCutoffFrequencyHz,
                            final int pNumberOfDecimalPlaces ) {
        // Always call the superclass constructor first!
        // NOTE: We use up to one decimal place of precision for displaying
        //  frequency, and four decimal places for parsing frequency.
        super( pClientProperties,
               initialText,
               tooltipText,
               true,
               0,
               1,
               0,
               4,
               frequencyMinimumHz,
               frequencyMaximumHz,
               frequencyInitialHz,
               VALUE_INCREMENT_DEFAULT_HZ );
        
        precisionCutoffFrequencyHz = pPrecisionCutoffFrequencyHz;
        numberOfDecimalPlaces = pNumberOfDecimalPlaces;
    }

    @Override
    public double adjustPrecision( final double doubleValue ) {
        final double precisionAdjustedValue 
            = ( doubleValue >= precisionCutoffFrequencyHz )
            ? FastMath.round( doubleValue )
            : MathUtilities.roundDecimal( doubleValue, numberOfDecimalPlaces );
        return precisionAdjustedValue;
    }
    
    public final double getPrecisionCutoffFrequencyHz() {
        return precisionCutoffFrequencyHz;
    }
    
    public final void setPrecisionCutoffFrequencyHz( final double pPrecisionCutoffFrequencyHz ) {
        precisionCutoffFrequencyHz = pPrecisionCutoffFrequencyHz;
    }
    
    public final int getNumberOfDecimalPlaces() {
        return numberOfDecimalPlaces;
    }
    
    public final void setNumberOfDecimalPlaces( final int pNumberOfDecimalPlaces ) {
        numberOfDecimalPlaces = pNumberOfDecimalPlaces;
    }
}

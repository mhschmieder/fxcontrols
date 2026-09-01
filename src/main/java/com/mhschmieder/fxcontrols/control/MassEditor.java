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
import com.mhschmieder.jphysics.measure.MassConversion;
import com.mhschmieder.jphysics.measure.MassUnit;

public class MassEditor extends DoubleEditor {

    // Store the Mass Unit so we'll know when we need to convert.
    private MassUnit massUnit;

    // ////////////////////////////////////////////////////////////////////////
    // Constructors and Initialization
    public MassEditor( final ClientProperties pClientProperties,
                       final String initialText,
                       final String tooltipText ) {
        // Always call the superclass constructor first!
        // NOTE: We use up to two decimal place of precision for displaying
        //  Mass, and ten decimal places for parsing Mass.
        super( pClientProperties, initialText, tooltipText, true, 0, 2, 0, 10, 0.0d, 1e10 );

        setValue( 0.0d );

        try {
            initEditor();
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    private final void initEditor() {
        // Set the Mass Unit to the SI default.
        massUnit = MassUnit.KILOGRAMS;

        // Now it is safe to set the value increment amount.
        setValueIncrement( 1.0d );

        // Set the embedded unit label in the generic number textField.
        setMeasurementUnitString( massUnit.abbreviation() );
    }

    // Convert current mass value from display units to kilograms.
    // NOTE: This method is unused currently, but is provided in case we
    // change our mind about having the related slider be the data master.
    public final double getMassKg() {
        return MassConversion.convertMass( getValue(),
                                           massUnit,
                                           MassUnit.KILOGRAMS );
    }

    // Convert new mass value from kilograms to display units.
    public final void setMassKg( final double massKg ) {
        setValue( MassConversion.convertMass( massKg,
                                              MassUnit.KILOGRAMS,
                                              massUnit ) );
    }

    // Convert minimum Mass value from kilograms to display units.
    public final void setMinimumMassKg( final double minimumMassKg ) {
        setMinimumValue( MassConversion.convertMass( minimumMassKg,
                                                     MassUnit.KILOGRAMS,
                                                     massUnit ) );
    }

    // Convert maximum Mass value from kilograms to display units.
    public final void setMaximumMassKg( final double maximumMassKg ) {
        setMaximumValue( MassConversion.convertMass( maximumMassKg,
                                                     MassUnit.KILOGRAMS,
                                                     massUnit ) );
    }

    public final void updateMassUnit( final MassUnit newMassUnit ) {
        // Convert Mass range from old units to new units.
        final double minimumMass = MassConversion.convertMass(
                _minimumValue,
                massUnit,
                newMassUnit );
        final double maximumMass = MassConversion.convertMass(
                _maximumValue,
                massUnit,
                newMassUnit );

        // Convert the current Mass from previous units to new units.
        final double currentMass
                = MassConversion.convertMass( getValue(),
                                              massUnit,
                                              newMassUnit );

        // Cache the new Mass Unit to provide context for next change.
        massUnit = newMassUnit;

        // Set the level of precision based on the granularity of the unit.
        switch ( massUnit ) {
            case KILOGRAMS:
                _numberFormat.setMaximumFractionDigits( 2 );
                break;
            case GRAMS:
                _numberFormat.setMaximumFractionDigits( 0 );
                break;
            case METRIC_TONS:
                _numberFormat.setMaximumFractionDigits( 5 );
                break;
            case POUNDS:
                _numberFormat.setMaximumFractionDigits( 2 );
                break;
            case OUNCES:
                _numberFormat.setMaximumFractionDigits( 1 );
                break;
            default:
                break;
        }

        // NOTE: Text Editors must set their adjusted range before setting the
        //  adjusted current value, as we manage value legality within callbacks
        //  that check the locally cached minimum and maximum values.
        setMinimumValue( minimumMass );
        setMaximumValue( maximumMass );
        setValue( currentMass );

        // Set the embedded unit label in the generic number textField.
        setMeasurementUnitString( massUnit.abbreviation() );
    }
}

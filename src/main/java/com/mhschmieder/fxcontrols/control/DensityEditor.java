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
 * This file is part of the jphysics Library
 *
 * You should have received a copy of the MIT License along with the jphysics
 * Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/jphysics
 */
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.jcommons.util.ClientProperties;
import com.mhschmieder.jphysics.measure.DensityConversion;
import com.mhschmieder.jphysics.measure.DensityUnit;

public class DensityEditor extends DoubleEditor {

    // Declare value increment/decrement amount for up and down arrow keys.
    // NOTE: We increment by 0.1 degrees kilograms per meter cubed as this is
    // a typical default.
    // TODO: Use a different increment if units are grams per centimeter cubed?
    public static final double VALUE_INCREMENT_KILOGRAMS_PER_CUBIC_METER = 0.1d;

    // Store the Density Unit so we'll know when we need to convert.
    private DensityUnit densityUnit;

    //
    // ////////////////////////////////////////////////////////////////////////
    // Constructors and Initialization
    public DensityEditor( final ClientProperties pClientProperties,
                          final String tooltipText ) {
        this( pClientProperties,
              '0' + DensityUnit.KILOGRAMS_PER_CUBIC_METER.abbreviation(),
              tooltipText,
              0.0d,
              Double.MAX_VALUE,
              0.0d );
    }

    public DensityEditor( final ClientProperties pClientProperties,
                          final String initialText,
                          final String tooltipText,
                          final double minimumDensityKilogramsPerCubicMeter,
                          final double maximumDensityKilogramsPerCubicMeter,
                          final double initialDensityKilogramsPerCubicMeter ) {
        // Always call the superclass constructor first!
        // NOTE: We use up to two decimal places of precision for displaying
        //  Density, and six decimal places for parsing Density.
        super( pClientProperties,
               initialText,
               tooltipText,
               true,
               0,
               2,
               0,
               6,
               minimumDensityKilogramsPerCubicMeter,
               maximumDensityKilogramsPerCubicMeter,
               initialDensityKilogramsPerCubicMeter,
               VALUE_INCREMENT_KILOGRAMS_PER_CUBIC_METER );

        densityUnit = DensityUnit.defaultValue();

        try {
            initEditor();
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void initEditor() {
        // Update the Density Unit and related resolutions and ranges.
        updateDensityUnit( densityUnit );
    }

    public void updateDensityUnit( final DensityUnit pDensityUnit ) {
        // Store the new Density Unit to provide context for next change.
        densityUnit = pDensityUnit;

        // Set the level of precision based on the granularity of the unit.
        switch ( densityUnit ) {
            case GRAMS_PER_CUBIC_CENTIMETER:
                _numberFormat.setMaximumFractionDigits( 4 );
                break;
            case KILOGRAMS_PER_CUBIC_METER:
                _numberFormat.setMaximumFractionDigits( 1 );
                break;
            default:
                break;
        }

        // NOTE: Text Editors must set their adjusted range before setting the
        //  adjusted current value, as we manage value legality within callbacks
        //  that check the locally cached minimum and maximum values.
        // NOTE: Unit conversion is done in the sliders for the doubled-up
        //  controls, so ideally we can move that code to these respective
        //  editors to help make the editors consistently own the data and the
        //  measurement units. The Distance Editor is the model for doing this.
        // NOTE: The attempted consolidation of bindings strategies ended up
        //  causing too many conflicts and problems, as we aren't handling
        //  sliders and editors consistently so it gets confusing very quickly
        //  as to the order of callbacks and events as well as when and whether
        //  unit conversion has already been applied when values are synced
        //  or bound.
        setMinimumDensityKilogramsPerCubicMeter( 0.0d );
        setMaximumDensityKilogramsPerCubicMeter( Double.MAX_VALUE );

        // Set the embedded unit label in the generic number textField.
        setMeasurementUnitString( densityUnit.abbreviation() );
    }

    // Convert minimum Density value from kilograms per cubic meter to display
    // units.
    public void setMinimumDensityKilogramsPerCubicMeter( final double minimumDensityKilogramsPerCubicMeter ) {
        setMinimumValue( DensityConversion.convertDensity(
                minimumDensityKilogramsPerCubicMeter,
                DensityUnit.KILOGRAMS_PER_CUBIC_METER,
                densityUnit ) );
    }

    // Convert maximum Density value from kilograms per cubic meter to display
    // units.
    public void setMaximumDensityKilogramsPerCubicMeter( final double maximumDensityKilogramsPerCubicMeter ) {
        setMaximumValue( DensityConversion.convertDensity(
                maximumDensityKilogramsPerCubicMeter,
                DensityUnit.KILOGRAMS_PER_CUBIC_METER,
                densityUnit ) );
    }

    // Convert current Density value from display units to kilograms per cubic
    // meter.
    // NOTE: This method is unused currently, but is provided in case we
    //  change our mind about having a related slider be the data master.
    public double getDensityKilogramsPerCubicMeter() {
        return DensityConversion.convertDensity( getValue(),
                                                 densityUnit,
                                                 DensityUnit.KILOGRAMS_PER_CUBIC_METER );
    }

    // Convert new Density value from kilograms per cubic meter to display
    // units.
    // NOTE: This method is unused currently, but is provided in case we
    //  change our mind about having a related slider be the data master.
    public void setDensityKilogramsPerCubicMeter( final double densityKilogramsPerCubicMeter ) {
        setValue( DensityConversion.convertDensity(
                densityKilogramsPerCubicMeter,
                DensityUnit.KILOGRAMS_PER_CUBIC_METER,
                densityUnit ) );
    }
}

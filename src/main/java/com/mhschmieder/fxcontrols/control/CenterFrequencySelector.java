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
import com.mhschmieder.jphysics.acoustics.CenterFrequencies;
import com.mhschmieder.jphysics.acoustics.FrequencyRange;
import com.mhschmieder.jphysics.acoustics.FrequencySignalUtilities;
import com.mhschmieder.jphysics.acoustics.RelativeBandwidth;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.math3.util.FastMath;

import java.util.TreeSet;

public final class CenterFrequencySelector extends DoubleSelector {
    
    private final int _startIndexForOneOctave;
    private final int _startIndexForThirdOctave;

    public CenterFrequencySelector( final ClientProperties pClientProperties,
                                    final boolean applyToolkitCss,
                                    final int startIndexForOneOctave,
                                    final int startIndexForThirdOctave ) {
        // Always call the superclass constructor first!
        super( pClientProperties,
               0,
               3,
               0,
               3,
               true,
               "Center Frequency", //$NON-NLS-1$ ,
               applyToolkitCss,
               false,
               false );
        
        _startIndexForOneOctave = startIndexForOneOctave;
        _startIndexForThirdOctave = startIndexForThirdOctave;

        try {
            initComboBox();
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    public String getCenterFrequency() {
        return getValue();
    }

    private void initComboBox() throws Exception {
        // NOTE: Groupings are turned off, and we force US locale for now,
        // due to specifics about the implementation of the pattern-matcher.
        // TODO: Alternately, cast to DecimalFormat, query the decimal and
        // grouping separator chars, and pass them to pattern-matcher?
        _numberFormat.setGroupingUsed( false );

        // Make sure the list displays all items without scrolling.
        // NOTE: Commented out due to blank lines when list changes size.
        // NOTE: More precisely, it is safest to not set this at all at
        // startup, for a control whose list size changes dynamically as much as
        // this one does, as setting the initial size to some unknown number
        // larger than 16 and smaller than or equal to 25, prevents dynamic
        // changes from taking hold later on. So we now exclusively set this on
        // changes to the list, as the size usually changes then as well.
        // setVisibleRowCount( 40 );

        // Set the non-editable drop-list of Full Octave Band Center
        // Frequencies, selected at 4 kHz.
        updateCenterFrequencyForBandwidthAndOctave(
                RelativeBandwidth.defaultValue(),
                OctaveRangeSelector.OCTAVE_RANGE_WIDE_DEFAULT,
                4000.0d,
                false );
    }

    public void setCenterFrequency( final String sOctaveRange,
                                    final String sCenterFrequency ) {
        final ObservableList< String > items = getItems();
        if ( ( sCenterFrequency != null ) && items.contains( sCenterFrequency ) ) {
            setValue( sCenterFrequency );
        }
        else {
            final double centerFrequencyDefault = FrequencyRange
                    .getNominalCenterFrequencyDefaultForOctaveRange( sOctaveRange, true );
            final String sCenterFrequencyDefault = FrequencySignalUtilities
                    .getFormattedFrequency( centerFrequencyDefault, _numberFormat );
            setValue( sCenterFrequencyDefault );
        }
    }

    // Set the drop-list of Center Frequencies based on Relative Bandwidth.
    // TODO: Remember to narrow the range of which frequencies are shown!
    @SuppressWarnings("nls")
    public void updateCenterFrequencyForBandwidthAndOctave( final RelativeBandwidth relativeBandwidth,
                                                            final String sOctaveRange,
                                                            final double centerFrequency,
                                                            final boolean preserveSelection ) {
        // Start with a clean slate, as we are effectively replacing the entire
        // list. Be careful if restoring the current selection, as there are
        // many edge cases that either do the wrong thing, result in a blank
        // selection field, or do not generate a callback.
        // NOTE: Using a Tree Set forces all numbers to be in ascending order.
        final TreeSet< Double > centerFrequencies = new TreeSet<>();

        int startIndex;
        int stopIndex;
        boolean narrowBand = false;
        switch ( relativeBandwidth ) {
        case RelativeBandwidth.ONE_OCTAVE:
            // Update the list of Center Frequencies to match full octave
            // bandwidth, displaying only the valid operating range.
            startIndex = _startIndexForOneOctave;
            stopIndex = 15;
            for ( int i = startIndex; i < stopIndex; i++ ) {
                final double wideBandCenterFrequency = CenterFrequencies
                        .NOMINAL_FULL_OCTAVE_CENTER_FREQUENCIES[ i ];
                centerFrequencies.add( wideBandCenterFrequency );
            }
            break;
        case RelativeBandwidth.THIRD_OCTAVE:
            // Update the list of Center Frequencies to match third octave
            // bandwidth, displaying only the valid operating range.
            startIndex = _startIndexForThirdOctave;
            stopIndex = 43;
            for ( int i = startIndex; i < stopIndex; i++ ) {
                final double wideBandCenterFrequency = CenterFrequencies
                        .NOMINAL_THIRD_OCTAVE_CENTER_FREQUENCIES[ i ];
                centerFrequencies.add( wideBandCenterFrequency );
            }
            break;
        case RelativeBandwidth.SIXTH_OCTAVE:
        case RelativeBandwidth.TWELFTH_OCTAVE:
        case RelativeBandwidth.TWENTY_FOURTH_OCTAVE:
        case RelativeBandwidth.FORTY_EIGHTH_OCTAVE:
            narrowBand = true;

            final int octaveDivider = relativeBandwidth.toOctaveDivider();
            final int startIndexAt10Hz = 10 * ( int ) FastMath.ceil( octaveDivider / 3.0d );

            final int octaveOffsetFrom10Hz = FrequencySignalUtilities
                    .getOctaveOffsetFrom10Hz( sOctaveRange );

            startIndex = startIndexAt10Hz + ( octaveOffsetFrom10Hz * octaveDivider );
            stopIndex = startIndex + octaveDivider;

            // Update the list of Center Frequencies to match relative
            // bandwidth, displaying only the valid operating range.
            for ( int i = startIndex; i < stopIndex; i++ ) {
                final double narrowBandCenterFrequency = FrequencySignalUtilities
                        .getCenterFrequencyByBandNumber( i, octaveDivider );
                centerFrequencies.add( narrowBandCenterFrequency );
            }
            if ( "10 kHz to 20 kHz".equals( sOctaveRange ) ) {
                // Insert the specific 1 kHz frequencies between 10 kHz and
                // 20 kHz in the midst of the algorithmically generated ones.
                // In order to get the numbers in order and avoid possible
                // duplicates between integer values and algorithmically
                // generated values, we use a TreeSet of Double objects.
                for ( int j = 0; j <= 10; j++ ) {
                    final double narrowBandCenterFrequency = 10000d + ( j * 1000d );
                    centerFrequencies.add( narrowBandCenterFrequency );
                }
            }

            break;
        default:
            break;
        }

        // Make sure the list displays all items without scrolling.
        // NOTE: It is best to do this dynamically with list changes, for the
        // most reliable adherence at run-time to the desired new size, or else
        // blank lines can result and the new list size not being honored.
        setVisibleRowCount( centerFrequencies.size() );

        // Look for a simple search of the exact frequency, for a cheap return.
        boolean centerFrequencyInRange = false;
        double centerFrequencyDefault = Double.NaN;
        if ( centerFrequencies.contains( centerFrequency ) ) {
            centerFrequencyInRange = true;
            centerFrequencyDefault = centerFrequency;
        }
        else {
            // Make sure it's in range, so we know to look for closest match.
            centerFrequencyInRange = FrequencyRange
                    .isCenterFrequencyInOctaveRange( sOctaveRange, centerFrequency );
        }

        // Convert the numbers to formatted strings, and search for the closest
        // match to the previous Center Frequency while doing that.
        // TODO: Use Functional Programming and Streams API to find best match?
        final ObservableList< String > centerFrequenciesFormatted = FXCollections
                .observableArrayList();
        double previousFrequencyDifference = Double.MAX_VALUE;
        double currentFrequencyDifference = Double.MAX_VALUE;
        for ( final Double nominalCenterFrequency : centerFrequencies ) {
            if ( centerFrequencyInRange ) {
                currentFrequencyDifference = FastMath.abs( nominalCenterFrequency - centerFrequency );
                if ( currentFrequencyDifference < previousFrequencyDifference ) {
                    centerFrequencyDefault = nominalCenterFrequency;
                }
                previousFrequencyDifference = currentFrequencyDifference;
            }

            final String centerFrequencyFormatted = FrequencySignalUtilities
                    .getFormattedFrequency( nominalCenterFrequency, _numberFormat );
            centerFrequenciesFormatted.add( centerFrequencyFormatted );
        }

        // If the Center Frequency is no longer in range, choose a new default.
        if ( !centerFrequencyInRange ) {
            centerFrequencyDefault = FrequencyRange
                    .getNominalCenterFrequencyDefaultForOctaveRange( sOctaveRange, narrowBand );
        }

        // Replace the entire list, and re-assert the current selection.
        final String sCenterFrequencyDefault = FrequencySignalUtilities
                .getFormattedFrequency( centerFrequencyDefault, _numberFormat );
        updateValues( centerFrequenciesFormatted, sCenterFrequencyDefault, preserveSelection );
    }
}

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
 * This file is part of the FxAcoustics Library
 *
 * You should have received a copy of the MIT License along with the FxAcoustics
 *  Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxacoustics
 */
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.jacoustics.FrequencyRange;
import com.mhschmieder.jacoustics.RelativeBandwidth;
import com.mhschmieder.jcommons.util.ClientProperties;

/**
 * A selector for a switchable enumeration of Octave Ranges.
 * <p>
 * TODO: Redo as an enum-based XComboBox that provides a List Cell Factory.
 */
public class OctaveRangeSelector extends TextSelector {

    // Default Octave Ranges, for best "out of box" experience.
    public static final String    OCTAVE_RANGE_WIDE_DEFAULT   =
                                                            FrequencyRange.OCTAVE_RANGE_WIDE_DEFAULT;
    public static final String    OCTAVE_RANGE_NARROW_DEFAULT =
                                                              FrequencyRange.OCTAVE_RANGE_NARROW_DEFAULT;

    // List the single wide Octave Range for full frequency spectrum.
    private static final String[] OCTAVE_RANGES_WIDE          =
                                                     new String[] { OCTAVE_RANGE_WIDE_DEFAULT };

    // List each narrow Octave Range as a full inclusive range.
    private static final String[] OCTAVE_RANGES_NARROW        = new String[] {
                                                                               "20 Hz to 40 Hz",                   //$NON-NLS-1$
                                                                               "40 Hz to 80 Hz",                   //$NON-NLS-1$
                                                                               "80 Hz to 160 Hz",                  //$NON-NLS-1$
                                                                               "160 Hz to 315 Hz",                 //$NON-NLS-1$
                                                                               "315 Hz to 630 Hz",                 //$NON-NLS-1$
                                                                               "630 Hz to 1.25 kHz",               //$NON-NLS-1$
                                                                               "1.25 kHz to 2.5 kHz",              //$NON-NLS-1$
                                                                               "2.5 kHz to 5 kHz",                 //$NON-NLS-1$
                                                                               "5 kHz to 10 kHz",                  //$NON-NLS-1$
                                                                               "10 kHz to 20 kHz"                  //$NON-NLS-1$
    };
    private static final String[] OCTAVE_RANGES_NARROW_EXTENDED = new String[] {
                                                                               "10 Hz to 20 Hz",                   //$NON-NLS-1$
                                                                               "20 Hz to 40 Hz",                   //$NON-NLS-1$
                                                                               "40 Hz to 80 Hz",                   //$NON-NLS-1$
                                                                               "80 Hz to 160 Hz",                  //$NON-NLS-1$
                                                                               "160 Hz to 315 Hz",                 //$NON-NLS-1$
                                                                               "315 Hz to 630 Hz",                 //$NON-NLS-1$
                                                                               "630 Hz to 1.25 kHz",               //$NON-NLS-1$
                                                                               "1.25 kHz to 2.5 kHz",              //$NON-NLS-1$
                                                                               "2.5 kHz to 5 kHz",                 //$NON-NLS-1$
                                                                               "5 kHz to 10 kHz",                  //$NON-NLS-1$
                                                                               "10 kHz to 20 kHz"                  //$NON-NLS-1$
    };
    
    // Flag for whether to use the low frequency extended range, which goes
    // below the normal human hearing range but might be useful for scientific
    // applications as well as entertainment oriented sound effects.
    protected final boolean _useExtendedRange;

    public OctaveRangeSelector( final ClientProperties pClientProperties,
                                final boolean applyToolkitCss,
                                final boolean useExtendedRange ) {
        // Always call the superclass constructor first!
        super( pClientProperties,
               "Octave Range", //$NON-NLS-1$
               applyToolkitCss,
               false,
               false,
               12,
               OCTAVE_RANGE_NARROW_DEFAULT,
               useExtendedRange
                   ? OCTAVE_RANGES_NARROW_EXTENDED
                   : OCTAVE_RANGES_NARROW );
        
        _useExtendedRange = useExtendedRange;
    }

    public final String getOctaveRange() {
        return getTextValue();
    }

    public final void setOctaveRange( final String sOctaveRange ) {
        setTextValue( sOctaveRange );
    }

    // Set the drop-list of Octave Ranges based on Relative Bandwidth.
    public final void updateOctaveRangeForBandwidthAndFrequency( final RelativeBandwidth relativeBandwidth,
                                                                 final double centerFrequency ) {
        // Determine and set the correct Octave Ranges to use.
        final String[] octaveRanges = ( RelativeBandwidth.ONE_OCTAVE.equals( relativeBandwidth )
                || RelativeBandwidth.THIRD_OCTAVE.equals( relativeBandwidth ) )
                    ? OCTAVE_RANGES_WIDE
                    : _useExtendedRange
                        ? OCTAVE_RANGES_NARROW_EXTENDED
                        : OCTAVE_RANGES_NARROW;

        // Determine which Octave Range contains the current Center Frequency.
        final String defaultOctaveRange = ( RelativeBandwidth.ONE_OCTAVE.equals( relativeBandwidth )
                || RelativeBandwidth.THIRD_OCTAVE.equals( relativeBandwidth ) )
                    ? OCTAVE_RANGE_WIDE_DEFAULT
                    : FrequencyRange
                            .getNominalOctaveRangeDefaultForCenterFrequency( centerFrequency );

        // Replace the entire list, and re-assert or default the selection.
        updateValues( octaveRanges, defaultOctaveRange, true );
    }
}
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
import javafx.scene.control.ToggleButton;

public final class AllPassFilterControls {

    // Declare default constants, where appropriate, for all fields.
    public static final double    FREQUENCY_MINIMUM_HZ = 10.0d;
    public static final double    FREQUENCY_MAXIMUM_HZ = 20000d;
    public static final double    BANDWIDTH_MINIMUM_Q  = 0.5d;
    public static final double    BANDWIDTH_MAXIMUM_Q  = 10.0d;

    private static final Double[] DEFAULT_FREQUENCIES  = new Double[] { 32d, 64d, 128d, 256d };

    public ToggleButton           _filterToggleButton;
    public FrequencyEditor        _frequencyEditor;
    public DoubleEditor           _bandwidthEditor;

    public AllPassFilterControls( final ClientProperties clientProperties,
                                  final int filterNumber,
                                  final boolean useDefaultFrequencies ) {
        _filterToggleButton = LabeledControlFactory
                .getSingleFilterToggleButton(
                        filterNumber, true, false );

        _frequencyEditor = ControlFactory.getFrequencyEditor(
                clientProperties,
                 "All Pass Filter Center Frequency",
                 " Hz",
                 FREQUENCY_MINIMUM_HZ,
                 FREQUENCY_MAXIMUM_HZ,
                 useDefaultFrequencies
                     ? DEFAULT_FREQUENCIES[ filterNumber - 1 ]
                     : 100.0d,
                     1000.0d,
                     2 );
        _frequencyEditor.setValueIncrement( 0.1d );

        _bandwidthEditor = ControlFactory.getBandwidthEditor(
                clientProperties,
                BANDWIDTH_MINIMUM_Q,
                BANDWIDTH_MAXIMUM_Q,
                1.0d );
    }

    public void setGroupVisible( final boolean visible ) {
        _filterToggleButton.setVisible( visible );
        _frequencyEditor.setVisible( visible );
        _bandwidthEditor.setVisible( visible );
    }
}

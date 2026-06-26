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
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.fxcontrols.util.LabelFactory;
import com.mhschmieder.fxsigproccontrols.control.SigprocControlFactory;
import com.mhschmieder.fxsigproccontrols.control.SigprocLabeledControlFactory;
import com.mhschmieder.jcommons.util.ClientProperties;
import com.mhschmieder.jsigproc.filter.ElectronicFilterType;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

public final class HighLowPassFilterControls {

    public Label                         _highLowPassFilterLabel;
    public HighLowPassFilterTypeSelector _highLowPassFilterSelector;

    public Label                         _highLowPassCutoffFrequencyLabel;
    public FrequencyEditor               _highLowPassCutoffFrequencyEditor;

    public ToggleButton                  _highLowPassToggleButton;

    public HighLowPassFilterControls( final ClientProperties clientProperties,
                                      final boolean applyToolkitCss,
                                      final ElectronicFilterType filterType,
                                      final boolean showHighOrderFilters,
                                      final double minimumFrequencyHz,
                                      final double maximumFrequencyHz,
                                      final double initialFrequencyHz ) {
        final String filterTypeName = filterType.label();
        _highLowPassFilterLabel = ControlUtilities.getControlLabel( filterTypeName );
        final String highLowPassFilterSlopeTooltipText = LabelFactory
                .getHighLowPassFilterSlopeTooltip( filterType );
        _highLowPassFilterSelector =
                                   new HighLowPassFilterTypeSelector( clientProperties,
                                                                      highLowPassFilterSlopeTooltipText,
                                                                      applyToolkitCss,
                                                                      filterType,
                                                                      showHighOrderFilters );

        _highLowPassCutoffFrequencyLabel = ControlUtilities.getControlLabel(
                LabelFactory.getFrequencyLabel() );
        final String highLowPassCutoffFrequencyTooltip = filterTypeName + " Cutoff Frequency";
        _highLowPassCutoffFrequencyEditor = SigprocControlFactory
                .getFrequencyEditor( clientProperties,
                                     highLowPassCutoffFrequencyTooltip,
                                     " Hz",
                                     minimumFrequencyHz,
                                     maximumFrequencyHz,
                                     initialFrequencyHz,
                                     1000.0d,
                                     2 );
        _highLowPassCutoffFrequencyEditor.setValueIncrement( 0.1d );

        _highLowPassToggleButton = SigprocLabeledControlFactory
                .getHighLowPassToggleButton( filterTypeName, true, true );

        // Force all the labels to right-justify, to match standard constraints.
        GridPane.setHalignment( _highLowPassFilterLabel, HPos.RIGHT );
        GridPane.setHalignment( _highLowPassCutoffFrequencyLabel, HPos.RIGHT );

        // Try to get the toggle buttons to be as tall as possible.
        GridPane.setFillHeight( _highLowPassToggleButton, true );

        // Try to set smallish widths for the editable filter values.
        _highLowPassCutoffFrequencyEditor.setPrefWidth( 80.0d );

        // Try to set widths that force insets in the toggle buttons.
        _highLowPassToggleButton.setPrefWidth( 100.0d );
    }
}

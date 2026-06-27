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

import com.mhschmieder.fxgraphics.geometry.SurfaceMaterial;
import com.mhschmieder.jcommons.util.ClientProperties;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

/**
 * Surface Selector Controls is a set of controls used for enabling individual
 * Surfaces and setting their Materials according to the pre-configured list
 * from Olsen's acoustic textbook.
 */
public final class SurfaceSelectorControls {

    public Label        _surfaceIdLabel;
    public TextEditor   _surfaceNameEditor;
    public ToggleButton _surfaceStatusButton;
    public XComboBox< SurfaceMaterial > _surfaceMaterialSelector;

    public SurfaceSelectorControls( final ClientProperties pClientProperties,
                                    final boolean applyToolkitCss,
                                    final int surfaceNumber ) {
        // Make the permanent static label for the Surface ID.
        final String surfaceId = "Surface " + Integer.toString( surfaceNumber ); //$NON-NLS-1$
        _surfaceIdLabel = new Label( surfaceId );
        _surfaceIdLabel.setAlignment( Pos.CENTER );

        // Make a dummy default Surface Name until data is loaded.
        final String surfaceNameDefault = surfaceId;
        _surfaceNameEditor = new TextEditor( surfaceNameDefault, 
                                             applyToolkitCss, 
                                             pClientProperties );

        _surfaceStatusButton = LabeledControlFactory
                .getSurfaceBypassedToggleButton(
                        true,
                        3.0d,
                        false,
                        true );

        final String tooltipText
                = "The material whose absorption properties should be applied to "
                + surfaceId;
        _surfaceMaterialSelector = ControlFactory.getSurfaceMaterialSelector(
                pClientProperties,
                tooltipText,
                applyToolkitCss );

        // Try to get the buttons to be as tall as possible.
        GridPane.setFillHeight( _surfaceIdLabel, true );
        GridPane.setFillHeight( _surfaceNameEditor, true );
        GridPane.setFillHeight( _surfaceStatusButton, true );
        GridPane.setFillHeight( _surfaceMaterialSelector, true );

        // Try to set sufficient width for the editable Surface Name.
        _surfaceNameEditor.setPrefWidth( 180.0d );

        // Try to set widths that force insets in the buttons.
        _surfaceIdLabel.setPrefWidth( 80.0d );
        _surfaceStatusButton.setPrefWidth( 120.0d );

        // Try to prevent clipping of the longest Material Name.
        _surfaceMaterialSelector.setPrefWidth( 280.0d );

        // Make sure all the buttons stretch to match the height of the first
        // button in layout order (forward references do not apply the match).
        _surfaceNameEditor.prefHeightProperty().bind(
                _surfaceIdLabel.heightProperty() );
        _surfaceStatusButton.prefHeightProperty().bind(
                _surfaceIdLabel.heightProperty() );
        _surfaceMaterialSelector.prefHeightProperty().bind(
                _surfaceIdLabel.heightProperty() );
    }

}


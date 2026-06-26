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
 * This file is part of the FxPhysics Library
 *
 * You should have received a copy of the MIT License along with the
 * FxPhysics Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxphysics
 */
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.fxcontrols.action.NaturalEnvironmentActions;
import com.mhschmieder.jcommons.util.ClientProperties;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * This class implemented the main Tool Bar for Natural Environment.
 */
public final class NaturalEnvironmentToolBar extends ToolBar {

    public PredictButtons _predictButtons;
    public CheckBox       _useAirAttenuationCheckBox;
    public Button         _resetButton;

    // Default constructor
    public NaturalEnvironmentToolBar( final ClientProperties pClientProperties,
                                      final NaturalEnvironmentActions naturalEnvironmentActions ) {
        // Always call the superclass constructor first!
        super();

        try {
            initToolBar( pClientProperties, naturalEnvironmentActions );
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void initToolBar( final ClientProperties pClientProperties,
                              final NaturalEnvironmentActions naturalEnvironmentActions ) {
        // Make the Nodes for the Tool Bar.
        _predictButtons = new PredictButtons( pClientProperties,
                                              naturalEnvironmentActions.simulationActions );
        _useAirAttenuationCheckBox = PhysicsLabeledControlFactory
                .getUseAirAttenuationCheckBox( pClientProperties,
                                               naturalEnvironmentActions.useAirAttenuationAction );
        _resetButton = LabeledControlFactory
                .getResetButton( pClientProperties, naturalEnvironmentActions.resetAction );

        // Add some spacers to separate logical groupings.
        // NOTE: We also force the Reset button to right-justify, and to stay
        // right-justified if the window width changes.
        final int spacerWidth = 40;
        final Region spacer1 = new Region();
        final Region spacer2 = new Region();
        spacer2.setPrefWidth( spacerWidth );
        HBox.setHgrow( spacer1, Priority.ALWAYS );

        // Add all the Nodes to the Tool Bar.
        getItems().addAll( _predictButtons.predictButton,
                           _predictButtons.clearButton,
                           spacer1,
                           _useAirAttenuationCheckBox,
                           spacer2,
                           _resetButton );
    }
}

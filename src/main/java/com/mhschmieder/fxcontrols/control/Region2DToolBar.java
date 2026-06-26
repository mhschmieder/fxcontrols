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
 * This file is part of the FxCadGui Library
 *
 * You should have received a copy of the MIT License along with the FxCadGui
 * Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxcadgui
 */
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.fxcontrols.action.Region2DActions;
import com.mhschmieder.jcommons.util.ClientProperties;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public final class Region2DToolBar extends ToolBar {

    // Declare tool bar buttons for shortcuts, etc.
    public PredictButtons _predictButtons;
    public Button         _resetButton;

    // Default constructor
    public Region2DToolBar( final ClientProperties pClientProperties,
                            final Region2DActions region2DActions ) {
        // Always call the superclass constructor first!
        super();

        try {
            initToolBar( pClientProperties, region2DActions );
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void initToolBar( final ClientProperties pClientProperties,
                              final Region2DActions region2DActions ) {
        // Make the Nodes for the Tool Bar.
        _predictButtons = new PredictButtons( pClientProperties, region2DActions.simulationActions );
        _resetButton = LabeledControlFactory.getResetButton( pClientProperties,
                                                             region2DActions.resetAction );

        // Add a spacer to separate logical groupings.
        // NOTE: We also force the Reset Button to right-justify, and to stay
        // right-justified if the window width changes.
        final Region spacer1 = new Region();
        HBox.setHgrow( spacer1, Priority.ALWAYS );

        // Add all the Nodes to the Tool Bar.
        getItems().addAll( _predictButtons.predictButton,
                           _predictButtons.clearButton,
                           spacer1,
                           _resetButton );
    }

}

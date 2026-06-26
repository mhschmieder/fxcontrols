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

import com.mhschmieder.fxcontrols.action.MeasurementUnitsActions;
import com.mhschmieder.jcommons.util.ClientProperties;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class MeasurementUnitsToolBar extends ToolBar {

    // Declare tool bar buttons for shortcuts, etc.
    public Button _resetButton;

    // Default constructor
    public MeasurementUnitsToolBar( final ClientProperties pClientProperties,
                                    final MeasurementUnitsActions measurementUnitsActions ) {
        // Always call the superclass constructor first!
        super();

        try {
            initToolBar( pClientProperties, measurementUnitsActions );
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    private final void initToolBar( final ClientProperties pClientProperties,
                                    final MeasurementUnitsActions measurementUnitsActions ) {
        // Make the nodes for the Tool Bar.
        _resetButton = LabeledControlFactory.getResetButton( pClientProperties,
                                                             measurementUnitsActions._resetAction );

        final Region spacer = new Region();
        HBox.setHgrow( spacer, Priority.ALWAYS );

        // Add all the nodes to the tool bar.
        getItems().addAll( spacer, _resetButton );
    }
}

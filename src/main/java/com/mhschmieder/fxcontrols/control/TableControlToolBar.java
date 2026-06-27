/*
 * MIT License
 *
 * Copyright (c) 2024, 2026 Mark Schmieder. All rights reserved.
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

import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ToolBar;

// TODO: Use this to replace redundant custom classes, by passing the type.
public class TableControlToolBar extends ToolBar {

    // Declare all the Tool Bar Action Buttons.
    public TableActionButtons tableActionButtons;

    // Default constructor
    public TableControlToolBar( final String type,
    final Orientation orientation ) {
        // Always call the superclass constructor first!
        super();

        try {
            initToolBar( type, orientation );
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void initToolBar( final String type,
        final Orientation orientation ) {
        // Make the Nodes for the Tool Bar.
        tableActionButtons = new TableActionButtons( type, orientation );

        // TODO: We should force the Table Action Buttons to right-justify, and
        //  to stay right-justified if the window width changes.

        // Add all the Nodes to the Tool Bar.
        final ObservableList< Node > nodes = getItems();
        nodes.addAll(
            tableActionButtons.addButton,
            tableActionButtons.removeButton );

        // NOTE: This is a hack until the tool bar's parent node sets its
        //  background and CSS is called to set the custom button colors.
        setBackground( tableActionButtons.addButton.getBackground() );
    }

    public void setRemoveEnabled( final boolean removeEnabled ) {
        // Forward this method to the Table Action Button Group.
        tableActionButtons.setRemoveEnabled( removeEnabled );
    }
}

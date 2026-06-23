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
 * This file is part of the FxGuiToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * FxGuiToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxguitoolkit
 */
package com.mhschmieder.fxcontrols.control;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;

// TODO: Use this to replace redundant custom classes, by passing the type.
public class TableActionButtons {

    // Declare all the Table Action Buttons.
    public Button addButton;
    public Button removeButton;

    public TableActionButtons( final String type,
                               final Orientation orientation ) {
        // Make the Table Action Buttons.
        addButton = null;
        switch ( orientation ) {
            case HORIZONTAL:
                addButton = CommonLabeledControlFactory.getAddColumnButton( type );
                break;
            case VERTICAL:
                addButton = CommonLabeledControlFactory.getAddRowButton( type );
                break;
            default:
                throw new IllegalArgumentException(
                        "Unsupported orientation: " + orientation );
        }
        removeButton = CommonLabeledControlFactory.getRemoveElementButton( type );
    }

    public void setRemoveEnabled( final boolean removeEnabled ) {
        removeButton.setDisable( !removeEnabled );
    }
}

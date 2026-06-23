/*
 * MIT License
 *
 * Copyright (c) 2025, 2026 Mark Schmieder. All rights reserved.
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
package com.mhschmieder.fxcontrols.control.cell;

import com.mhschmieder.fxcontrols.control.cell.XTableCell;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class ColorTableCell< C > extends XTableCell< C, Color > {

    private Canvas canvas;

    public ColorTableCell(final double width,
                          final double height ) {
        // Always call the superclass constructor first!
        super();

        this.setWidth(width);
        this.setHeight(height);

        canvas = new ResizableCanvas();
    }

    @Override
    public void updateItem( final Color item,
                            final boolean empty ) {
        if ( empty || item == null ) {
            setText( null );
            setGraphic( null );
            return;
        }

        // TODO: Make a utility for this, as it likely is usable elsewhere too.
        canvas.getGraphicsContext2D().clearRect(
                0, 0, getWidth(), getHeight() );
        canvas.resize( getWidth(), getHeight() );
        canvas.getGraphicsContext2D().setFill( item );
        canvas.getGraphicsContext2D().fillRect(
                0, 0, getWidth(), getHeight() );
        setGraphic( canvas );
    }
}

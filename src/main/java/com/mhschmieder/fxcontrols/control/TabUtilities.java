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
 * This file is part of the fxcontrols Library
 *
 * You should have received a copy of the MIT License along with the fxcontrols
 * Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxcontrols
 */
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.fxgraphics.image.ImageUtilities;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.List;

public final class TabUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private TabUtilities() {}

    public static void initTab( final String tabTitle,
                                final String jarRelativeIconPath,
                                final Tab tab,
                                final TabPane tabPane ) {
        tab.setText( tabTitle );
        tab.setGraphic( ImageUtilities.createIcon( jarRelativeIconPath ) );

        tabPane.setTabDragPolicy( TabPane.TabDragPolicy.REORDER );
        tab.setContent( tabPane );

        // NOTE: The tab panes are the parameters, source grid, color gradient,
        //  etc. tabs within a models settings which were being removed when the
        //  tab was closed.
        //tab.setOnClosed( event -> tabPane.getTabs().clear() );
    }

    public static Tab makeTab( final String tabTitle,
                               final List< Tab > tabs,
                               final Node node ) {
        final Tab tab = new Tab( tabTitle );
        tab.setClosable( false );
        tab.setContent( node );

        tabs.add( tab );

        node.setVisible( true );

        return tab;
    }
}

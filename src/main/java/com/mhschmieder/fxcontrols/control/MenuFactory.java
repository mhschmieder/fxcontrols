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

import com.mhschmieder.fxcontrols.action.LayerManagementActions;
import com.mhschmieder.fxcontrols.action.NaturalEnvironmentActions;
import com.mhschmieder.fxcontrols.action.Region2DActions;
import com.mhschmieder.fxcontrols.action.XActionUtilities;
import com.mhschmieder.jcommons.util.ClientProperties;
import javafx.scene.control.MenuBar;
import org.controlsfx.control.action.Action;

import java.util.Collection;

/**
 * This is a factory class for generating Menus for CAD.
 */
public final class MenuFactory {

    public static MenuBar getLayerManagementMenuBar( final ClientProperties pClientProperties,
                                                     final LayerManagementActions layerManagementActions ) {
        final Collection< Action > layerManagementMenuBarActionCollection = layerManagementActions
                .getLayerManagementMenuBarActionCollection( pClientProperties );
        final MenuBar layerManagementMenuBar = XActionUtilities
                .createMenuBar( layerManagementMenuBarActionCollection );
        return layerManagementMenuBar;
    }

    public static MenuBar getRegion2DMenuBar( final ClientProperties pClientProperties,
                                              final Region2DActions region2DActions,
                                              final boolean vectorGraphicsSupported ) {
        final Collection< Action > region2DMenuBarActionCollection = region2DActions
                .getRegion2DMenuBarActionCollection( pClientProperties, vectorGraphicsSupported );
        final MenuBar region2DMenuBar = XActionUtilities
                .createMenuBar( region2DMenuBarActionCollection );
        return region2DMenuBar;
    }

    public static MenuBar getNaturalEnvironmentMenuBar( final ClientProperties pClientProperties,
                                                        final NaturalEnvironmentActions naturalEnvironmentActions ) {
        final Collection< Action > naturalEnvironmentMenuBarActionCollection =
                naturalEnvironmentActions
                        .getNaturalEnvironmentMenuBarActionCollection( pClientProperties );
        final MenuBar naturalEnvironmentMenuBar = XActionUtilities
                .createMenuBar( naturalEnvironmentMenuBarActionCollection );
        return naturalEnvironmentMenuBar;
    }
}

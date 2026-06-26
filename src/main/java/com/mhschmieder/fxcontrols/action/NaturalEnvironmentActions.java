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
 * You should have received a copy of the MIT License along with the FxPhysics
 * Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxphysics
 */
package com.mhschmieder.fxcontrols.action;

import com.mhschmieder.jcommons.util.ClientProperties;
import javafx.scene.paint.Color;
import org.controlsfx.control.action.Action;

import java.util.Arrays;
import java.util.Collection;

/**
 * This is a struct-like container for actions used by Natural Environment.
 */
public final class NaturalEnvironmentActions {

    public FileActions fileActions;
    public SettingsActions settingsActions;
    public SimulationActions simulationActions;

    public XAction useAirAttenuationAction;
    public XAction resetAction;
    
    protected final boolean vectorGraphicsSupported;

    public NaturalEnvironmentActions( final ClientProperties pClientProperties,
                                      final boolean pVectorGraphicsSupported ) {
        vectorGraphicsSupported = pVectorGraphicsSupported;
        
        fileActions = new FileActions( pClientProperties );
        settingsActions = new SettingsActions( pClientProperties );
        simulationActions = new SimulationActions( pClientProperties );

        useAirAttenuationAction = PhysicsLabeledActionFactory
                .getUseAirAttenuationAction( pClientProperties );

        resetAction = LabeledActionFactory.getResetAction( pClientProperties );

        // The tool tip for "Reset" is unique per context so isn't in the
        // locale-sensitive resources for the generic action lookup.
        resetAction.setLongText( "Reset Natural Environment to Default Values" );
    }

    public Collection< Action > getBackgroundColorChoiceCollection() {
        // Forward this method to the Settings actions container.
        return settingsActions.getBackgroundColorChoiceCollection();
    }

    public Collection< Action > getExportActionCollection() {
        // Forward this method to the File actions container.
        return fileActions.getExportActionCollection( true, false );
    }

    public Collection< Action > getFileActionCollection( 
            final ClientProperties pClientProperties ) {
        // Forward this method to the File actions container.
        // TODO: Enable standard Vector Graphics Export for XT.
        return fileActions
                .getFileActionCollection( pClientProperties, 
                                          vectorGraphicsSupported, 
                                          false );
    }

    public Collection< Action > getNaturalEnvironmentMenuBarActionCollection( 
            final ClientProperties pClientProperties ) {
        // TODO: Enable standard Vector Graphics Export for XT.
        final XActionGroup fileActionGroup = LabeledActionFactory
                .getFileActionGroup( pClientProperties, 
                                     fileActions, 
                                     vectorGraphicsSupported, 
                                     false );

        final XActionGroup settingsActionGroup = LabeledActionFactory
                .getSettingsActionGroup( pClientProperties, settingsActions, true );

        final XActionGroup simulationActionGroup = LabeledActionFactory
                .getSimulationActionGroup( pClientProperties, simulationActions );

        final Collection< Action > naturalEnvironmentMenuBarActionCollection = Arrays
                .asList( fileActionGroup, 
                         settingsActionGroup, 
                         simulationActionGroup );

        return naturalEnvironmentMenuBarActionCollection;
    }

    public String getSelectedBackgroundColorName() {
        // Forward this method to the Settings actions container.
        return settingsActions.getSelectedBackgroundColorName();
    }

    public Collection< Action > getSettingsActionCollection( 
            final ClientProperties pClientProperties ) {
        // Forward this method to the File actions container.
        return settingsActions.getSettingsActionCollection( pClientProperties, true );
    }

    public Collection< Action > getSimulationActionCollection( 
            final ClientProperties pClientProperties ) {
        // Forward this method to the Simulation actions container.
        return simulationActions.getSimulationActionCollection( pClientProperties );
    }

    public Collection< Action > getWindowSizeActionCollection() {
        // Forward this method to the Settings actions container.
        return settingsActions.getWindowSizeActionCollection( true );
    }

    public boolean isUseAirAttenuation() {
        return useAirAttenuationAction.isSelected();
    }

    public Color selectBackgroundColor( final String backgroundColorName ) {
        // Forward this method to the Settings actions container.
        return settingsActions.selectBackgroundColor( backgroundColorName );
    }

    public void setUseAirAttenuation( final boolean useAirAttenuation ) {
        useAirAttenuationAction.setSelected( useAirAttenuation );
    }
}

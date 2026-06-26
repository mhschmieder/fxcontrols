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
package com.mhschmieder.fxcontrols.model;

import com.mhschmieder.fxgraphics.LabelAssignable;
import com.mhschmieder.fxcontrols.model.LayerNameAssignable;
import com.mhschmieder.jcommons.lang.NumberUtilities;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

// TODO: Research why Number of Projections Zones is modeled as a String.
public class LinearObjectProperties implements LabelAssignable, LayerNameAssignable {

    private final StringProperty  label;
    private final StringProperty  layerName;
    private final BooleanProperty useAsProjector;
    private final StringProperty  numberOfProjectionZones;

    public LinearObjectProperties( final String pLabel,
                                   final String pLayerName,
                                   final boolean pUseAsProjector,
                                   final int pNumberOfProjectionZones ) {
        this( pLabel, pLayerName, pUseAsProjector, Integer.toString( pNumberOfProjectionZones ) );
    }

    public LinearObjectProperties( final String pLabel,
                                   final String pLayerName,
                                   final boolean pUseAsProjector,
                                   final String pNumberOfProjectionZones ) {
        label = new SimpleStringProperty( pLabel );
        layerName = new SimpleStringProperty( pLayerName );
        useAsProjector = new SimpleBooleanProperty( pUseAsProjector );
        numberOfProjectionZones = new SimpleStringProperty( pNumberOfProjectionZones );
    }

    @Override
    public final StringProperty labelProperty() {
        return label;
    }

    @Override
    public final void setLabel( final String pLabel ) {
        label.set( pLabel );
    }

    @Override
    public final String getLabel() {
        return label.get();
    }

    @Override
    public final StringProperty layerNameProperty() {
        return layerName;
    }

    @Override
    public final void setLayerName( final String pLayerName ) {
        layerName.set( pLayerName );
    }

    @Override
    public final String getLayerName() {
        return layerName.get();
    }

    public final BooleanProperty useAsProjectorProperty() {
        return useAsProjector;
    }

    public final void setUseAsProjector( final boolean pUseAsProjector ) {
        useAsProjector.set( pUseAsProjector );
    }

    public final boolean isUseAsProjector() {
        return useAsProjector.get();
    }

    public final StringProperty numberOfProjectionZonesProperty() {
        return numberOfProjectionZones;
    }

    public final void setNumberOfProjectionZones( final String pNumberOfProjectionZones ) {
        numberOfProjectionZones.set( pNumberOfProjectionZones );
    }

    public final void setNumberOfProjectionZones( final int pNumberOfProjectionZones ) {
        numberOfProjectionZones.set( Integer.toString( pNumberOfProjectionZones ) );
    }

    public final int getNumberOfProjectionZones() {
        return NumberUtilities.parseInteger( numberOfProjectionZones.get() );
    }

}

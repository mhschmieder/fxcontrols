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
package com.mhschmieder.fxcontrols.model;

import com.mhschmieder.fxcontrols.util.LayerPropertiesManagement;
import com.mhschmieder.jcommons.lang.LabeledObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

/**
 * Observable Layer implementation for common settings that cut across most
 * applications that expose some form of Layer Management. Use this version for
 * binding to related GUI controls purposes; there is a non-observable Layer
 * class in the graphics library for Layer Management, for most other purposes.
 */
public final class LayerProperties implements Comparable< LayerProperties >,
    LabeledObject {

    private final StringProperty layerName;
    private final ObjectProperty< Color > layerColor;
    private final BooleanProperty layerActive;
    private final BooleanProperty layerVisible;
    private final BooleanProperty layerLocked;

    public LayerProperties( final String pLayerName,
                            final Color pLayerColor,
                            final boolean pLayerActive,
                            final boolean pLayerVisible,
                            final boolean pLayerLocked ) {
        layerName = new SimpleStringProperty( pLayerName );
        layerColor = new SimpleObjectProperty<>( pLayerColor );
        layerActive = new SimpleBooleanProperty( pLayerActive );
        layerVisible = new SimpleBooleanProperty( pLayerVisible );
        layerLocked = new SimpleBooleanProperty( pLayerLocked );
    }

    // NOTE: This is implemented strictly for sorting by Layer Name.
    @Override
    public int compareTo( final LayerProperties otherLayerProperties ) {
        // If this Layer is the Default Layer, it is "less than" the other.
        final String thisLayerName = getLayerName();
        if ( LayerPropertiesManagement.DEFAULT_LAYER_NAME.equals( thisLayerName ) ) {
            return -1;
        }

        // If the other Layer is the Default Layer, it is "more than" this.
        final String otherLayerName = otherLayerProperties.getLayerName();
        if ( LayerPropertiesManagement.DEFAULT_LAYER_NAME.equals( otherLayerName ) ) {
            return 1;
        }

        return thisLayerName.compareTo( otherLayerName );
    }

    public StringProperty layerNameProperty() {
        return layerName;
    }

    public String getLayerName() {
        return layerName.get();
    }

    public void setLayerName( final String pLayerName ) {
        layerName.set( pLayerName );
    }

    public ObjectProperty< Color > layerColorProperty() {
        return layerColor;
    }

    public Color getLayerColor() {
        return layerColor.get();
    }

    public void setLayerColor( final Color pLayerColor ) {
        layerColor.set( pLayerColor );
    }

    public BooleanProperty layerActiveProperty() {
        return layerActive;
    }

    public boolean isLayerActive() {
        return layerActive.get();
    }

    public void setLayerActive( final boolean pLayerActive ) {
        layerActive.set( pLayerActive );
    }

    public BooleanProperty layerVisibleProperty() {
        return layerVisible;
    }

    public boolean isLayerVisible() {
        return layerVisible.get();
    }

    public void setLayerVisible( final boolean pLayerVisible ) {
        layerVisible.set( pLayerVisible );
    }

    public BooleanProperty layerLockedProperty() {
        return layerLocked;
    }

    public boolean isLayerLocked() {
        return layerLocked.get();
    }

    public void setLayerLocked( final boolean pLayerLocked ) {
        layerLocked.set( pLayerLocked );
    }

    @Override
    public String getLabel() {
        return getLayerName();
    }

    @Override
    public void setLabel( String label ) {
        setLayerName( label );
    }
}

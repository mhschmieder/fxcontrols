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
 * This file is part of the FxLayerGui Library
 *
 * You should have received a copy of the MIT License along with the
 * FxLayerGui Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxlayergui
 */
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.fxcontrols.control.TextSelector;
import com.mhschmieder.fxlayercontrols.util.LayerPropertiesManager;
import com.mhschmieder.fxgraphics.layers.Layer;
import com.mhschmieder.fxgraphics.layers.LayerManager;
import com.mhschmieder.jcommons.util.ClientProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.SingleSelectionModel;

import java.util.List;

public class LayerSelector extends TextSelector {

    // Cache the current Layer Collection, as it is needed for accurate names.
    protected List< Layer > _layerCollection;

    // Cache the displayed list of Layer Names, so we can compare during
    // updates.
    protected List< String >          _layerNames;

    // Keep track of whether this instance supports multi-edit ("various").
    protected boolean                           _supportMultiEdit;

    public LayerSelector( final ClientProperties pClientProperties,
                          final boolean applyToolkitCss,
                          final boolean supportMultiEdit ) {
        // Always call the superclass constructor first!
        super( pClientProperties, "Layer", applyToolkitCss, false, false, 32 ); //$NON-NLS-1$

        _supportMultiEdit = supportMultiEdit;

        try {
            initComboBox();
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    public final String getLayerName() {
        return getValue();
    }

    private final void initComboBox() {
        // Set the non-editable drop-list of Layers.
        // NOTE: We start with just the Default Layer.
        final ObservableList< Layer > defaultLayerCollection = FXCollections
                .observableArrayList();
        final Layer defaultLayer = LayerManager.makeDefaultLayer();
        defaultLayerCollection.add( defaultLayer );
        setLayerCollection( defaultLayerCollection, defaultLayer );

        // Make sure that "various" serves as status-only, and isn't selectable.
        setCellFactory( param -> {
            final ListCell< String > cell = new ListCell< String >() {
                @Override
                public void updateItem( final String item, final boolean empty ) {
                    super.updateItem( item, empty );
                    if ( item != null ) {
                        setText( item );

                        final boolean disable = LayerPropertiesManager
                                .VARIOUS_LAYER_NAME.equals( item );
                        setDisable( disable );
                    }
                    else {
                        setText( null );
                    }
                }
            };

            return cell;
        } );
    }

    // Set the drop-list of available Layer Names from the collection.
    public final void setLayerCollection( final List<Layer> layerCollection ) {
        // Cache the global reference so it stays in sync vs. using setItems()
        // -- otherwise renamings and Add/Delete would require resetting the
        // collection here vs. depending on run-time extraction methods.
        _layerCollection = layerCollection;

        // Reset the Combo Box to use the new list of Layer Names.
        updateLayerNames();
    }

    // Set the drop-list of available Layer Names from the collection.
    private final void setLayerCollection( final ObservableList< Layer > layerCollection,
                                           final Layer layerCurrent ) {
        // Save the selection to reinstate after replacing the drop-list.
        final String layerNameCurrent = ( layerCurrent != null )
            ? layerCurrent.getLayerName()
            : getValue();

        // Set the drop-list of available Layer Names from the collection.
        setLayerCollection( layerCollection );

        // Attempt to restore the previous selection, as this method is often
        // called when layers are added or deleted vs. just when the entire list
        // is replaced (such as when a new project file is opened).
        setLayerNameCurrent( layerNameCurrent );
    }

    public final void setLayerName( final String layerNameCandidate ) {
        final ObservableList< String > layerNames = getItems();
        if ( layerNames.contains( layerNameCandidate ) ) {
            setValue( layerNameCandidate );
        }
    }

    private final void setLayerNameCurrent( final String layerNameCurrent ) {
        if ( layerNameCurrent != null ) {
            final ObservableList< String > layerNames = getItems();
            if ( layerNames.contains( layerNameCurrent ) ) {
                setValue( layerNameCurrent );
            }
            else if ( layerNames.size() > 0 ) {
                // If no match found, default to first item in display list.
                final String layerNameDefault = layerNames.get(
                        LayerPropertiesManager.DEFAULT_LAYER_INDEX );
                if ( layerNameDefault != null ) {
                    setValue( layerNameDefault );
                }
            }
        }
    }

    public final void setLayerNameIfChanged( final String layerNameCandidate ) {
        // In order to avoid unnecessary callbacks and setting of the dirty
        // flag, we check to see if the value changed before setting it.
        // NOTE: This may be unnecessary based on how JavaFX handles change.
        final String selectedLayerName = getLayerName();
        if ( layerNameCandidate != null ) {
            if ( !layerNameCandidate.equals( selectedLayerName ) ) {
                setLayerName( layerNameCandidate );
            }
        }
    }

    public final boolean updateLayerNames() {
        // Conditionally replace the entire list with the new collection.
        final List< String > layerNames = LayerManager
                .getAssignableLayerNames( _layerCollection, _supportMultiEdit );
        if ( !layerNames.equals( _layerNames ) ) {
            // If the list size shrank, the selected index is automatically
            // invalid; whereas in other cases a Layer Name may be all that
            // changed. When the entire list is replaced, we ignore this flag.
            // Note that we delegate list order differences to the higher level
            // logic that decides whether to preserve by name or by index.
            final boolean selectedLayerIndexInvalid = ( _layerNames == null )
                ? true
                : layerNames.size() < _layerNames.size();

            _layerNames = layerNames;
            setItems(FXCollections.observableArrayList( _layerNames ) );

            // Make sure the Combo Box width grows, if necessary, to support
            // longer names that may have just been added or changed.
            setMaxWidth( 160d );
            setNeedsLayout( true );

            return selectedLayerIndexInvalid;
        }

        return false;
    }

    // Update the drop-list of available Layer Names from the collection.
    public final void updateLayerNames( final boolean preserveSelectedLayerByIndex,
                                        final boolean preserveSelectedLayerByName ) {
        // Cache the current selection so we can restore after updating Layer
        // Names, as we can't do this by setting the old name in the new list.
        final SingleSelectionModel< String > selectionModel = getSelectionModel();
        final int selectedLayerIndex = selectionModel.getSelectedIndex();
        updateLayerNames( selectedLayerIndex,
                          preserveSelectedLayerByIndex,
                          preserveSelectedLayerByName );
    }

    // Update the drop-list of available Layer Names from the collection.
    public final void updateLayerNames( final int currentSelectedIndex,
                                        final boolean preserveSelectedLayerByIndex,
                                        final boolean preserveSelectedLayerByName ) {
        // Cache the current selection so we can restore after updating Layer
        // Names, as we can't do this by setting the old name in the new list.
        final SingleSelectionModel< String > selectionModel = getSelectionModel();
        final int selectedLayerIndex = currentSelectedIndex;
        final String selectedLayerName = selectionModel.getSelectedItem();

        // Update the drop-list of available Layer Names from the collection.
        // NOTE: We need to verify the validity of the selected index as the
        // list size may have changed if some Layers were made Hidden (or
        // re-made Visible).
        final boolean selectedLayerIndexInvalid = updateLayerNames();

        // Restore the previous selected Layer Name by index or by name.
        if ( preserveSelectedLayerByIndex && ( selectedLayerIndex < _layerNames.size() )
                && !selectedLayerIndexInvalid ) {
            selectionModel.select( selectedLayerIndex );
        }
        else if ( ( preserveSelectedLayerByName
                || ( preserveSelectedLayerByIndex && selectedLayerIndexInvalid ) )
                && ( _layerNames.contains( selectedLayerName ) ) ) {
            selectionModel.select( selectedLayerName );
        }
        else {
            selectionModel.selectFirst();
        }
    }

}

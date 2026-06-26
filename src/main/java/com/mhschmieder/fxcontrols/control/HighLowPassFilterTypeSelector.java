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
 * This file is part of the FxSigProc Library
 *
 * You should have received a copy of the MIT License along with the
 * FxSigProc Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxsigproc
 */
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.jcommons.util.ClientProperties;
import com.mhschmieder.jsigproc.filter.ElectronicFilterType;
import com.mhschmieder.jsigproc.filter.HighLowPassFilterType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class HighLowPassFilterTypeSelector extends TextSelector {

    // Default High/Low Pass filter type, for best "out of box" experience.
    public static final String         HIGH_LOW_PASS_FILTER_TYPE_DEFAULT =
                                                                         HighLowPassFilterType.LOW_PASS
                                                                                 .toPresentationString();

    // Default High Pass filter type, for best "out of box" experience.
    public static final String         HIGH_PASS_FILTER_TYPE_DEFAULT     =
                                                                     HighLowPassFilterType.SECOND_ORDER_HIGH_PASS
                                                                             .toPresentationString();

    // Default Low Pass filter type, for best "out of box" experience.
    public static final String         LOW_PASS_FILTER_TYPE_DEFAULT      =
                                                                    HighLowPassFilterType.LOW_PASS
                                                                            .toPresentationString();

    // Cache the type of Electronic Filter this selector is being used for.
    private final ElectronicFilterType _filterType;

    public HighLowPassFilterTypeSelector( final ClientProperties clientProperties,
                                          final String tooltipText,
                                          final boolean applyToolkitCss,
                                          final ElectronicFilterType filterType,
                                          final boolean showHighOrderFilters ) {
        // Always call the superclass constructor first!
        super( clientProperties, tooltipText, applyToolkitCss, false, false, 16 );

        _filterType = filterType;

        // Update the non-editable drop-list of filter types.
        updateFilterTypes( showHighOrderFilters );
    }

    // Update the non-editable drop-list of filter types.
    public void updateFilterTypes( final boolean showHighOrderFilters ) {
        // Re-populate the drop-list with all filter types.
        final ObservableList< String > filterTypes = FXCollections.observableArrayList();

        switch ( _filterType ) {
        case ElectronicFilterType.HIGH_LOW_PASS:
            filterTypes.add( HighLowPassFilterType.LOW_PASS.toPresentationString() );
            filterTypes
                    .add( HighLowPassFilterType.SECOND_ORDER_HIGH_PASS.toPresentationString() );
            filterTypes
                    .add( HighLowPassFilterType.ELLIPTICAL_HIGH_PASS.toPresentationString() );
            break;
        case ElectronicFilterType.HIGH_PASS:
            filterTypes
                    .add( HighLowPassFilterType.SECOND_ORDER_HIGH_PASS.toPresentationString() );
            filterTypes
                    .add( HighLowPassFilterType.ELLIPTICAL_HIGH_PASS.toPresentationString() );
            filterTypes.add( HighLowPassFilterType.BUTTERWORTH_1_HIGH_PASS
                    .toPresentationString() );
            filterTypes.add( HighLowPassFilterType.BUTTERWORTH_2_HIGH_PASS
                    .toPresentationString() );
            filterTypes.add( HighLowPassFilterType.BUTTERWORTH_3_HIGH_PASS
                    .toPresentationString() );
            filterTypes.add( HighLowPassFilterType.BUTTERWORTH_4_HIGH_PASS
                    .toPresentationString() );
            if ( showHighOrderFilters ) {
                // NOTE: Of the higher order filters, only the highest order seems useful.
                // filterTypes.add(
                // HighLowPassFilterType.BUTTERWORTH_5_HIGH_PASS
                // .toPresentationString() );
                // filterTypes.add(
                // HighLowPassFilterType.BUTTERWORTH_6_HIGH_PASS
                // .toPresentationString() );
                // filterTypes.add(
                // HighLowPassFilterType.BUTTERWORTH_7_HIGH_PASS
                // .toPresentationString() );
                filterTypes.add( HighLowPassFilterType.BUTTERWORTH_8_HIGH_PASS
                        .toPresentationString() );
            }
            filterTypes.add( HighLowPassFilterType.LINKWITZ_RILEY_2_HIGH_PASS
                             .toPresentationString() );
                     filterTypes.add( HighLowPassFilterType.LINKWITZ_RILEY_4_HIGH_PASS
                             .toPresentationString() );
            break;
        case ElectronicFilterType.LOW_PASS:
            filterTypes.add( HighLowPassFilterType.LOW_PASS.toPresentationString() );
            filterTypes
                    .add( HighLowPassFilterType.BUTTERWORTH_1_LOW_PASS.toPresentationString() );
            filterTypes
                    .add( HighLowPassFilterType.BUTTERWORTH_2_LOW_PASS.toPresentationString() );
            filterTypes
                    .add( HighLowPassFilterType.BUTTERWORTH_3_LOW_PASS.toPresentationString() );
            filterTypes
                    .add( HighLowPassFilterType.BUTTERWORTH_4_LOW_PASS.toPresentationString() );
            if ( showHighOrderFilters ) {
                // NOTE: Of the higher order filters, only the highest order seems useful.
                // filterTypes.add(
                // HighLowPassFilterType.BUTTERWORTH_5_LOW_PASS
                // .toPresentationString() );
                // filterTypes.add(
                // HighLowPassFilterType.BUTTERWORTH_6_LOW_PASS
                // .toPresentationString() );
                // filterTypes.add(
                // HighLowPassFilterType.BUTTERWORTH_7_LOW_PASS
                // .toPresentationString() );
                filterTypes.add( HighLowPassFilterType.BUTTERWORTH_8_LOW_PASS
                        .toPresentationString() );
            }
            filterTypes.add( HighLowPassFilterType.LINKWITZ_RILEY_2_LOW_PASS
                    .toPresentationString() );
            filterTypes.add( HighLowPassFilterType.LINKWITZ_RILEY_4_LOW_PASS
                    .toPresentationString() );
            break;
        default:
            break;
        }

        // Determine the initial default High/Low Pass Filter Type.
        String defaultFilterType = null;
        switch ( _filterType ) {
        case ElectronicFilterType.HIGH_LOW_PASS:
            defaultFilterType = HIGH_LOW_PASS_FILTER_TYPE_DEFAULT;
            break;
        case ElectronicFilterType.HIGH_PASS:
            defaultFilterType = HIGH_PASS_FILTER_TYPE_DEFAULT;
            break;
        case ElectronicFilterType.LOW_PASS:
            defaultFilterType = LOW_PASS_FILTER_TYPE_DEFAULT;
            break;
        default:
            break;
        }

        // Replace the entire list, and re-assert the current selection.
        updateValues( filterTypes, defaultFilterType, true );
    }
}

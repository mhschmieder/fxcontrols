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

import com.mhschmieder.jcommons.util.ClientProperties;
import com.mhschmieder.jsigproc.dsp.DigitalFilterUtilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class ButterworthFilterSlopeSelector extends IntegerSelector {

    public ButterworthFilterSlopeSelector( final ClientProperties pClientProperties,
                                           final boolean applyToolkitCss,
                                           final String tooltipText,
                                           final short[] filterSlopeOrder,
                                           final int defaultFilterSlopeIndex ) {
        // Always call the superclass constructor first!
        super( pClientProperties, true, tooltipText, applyToolkitCss, false, false );

        try {
            initComboBox( filterSlopeOrder, defaultFilterSlopeIndex );
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    public String getFilterSlope() {
        return getValue();
    }

    private void initComboBox( final short[] filterSlopeOrder, final int defaultFilterSlopeIndex ) {
        // NOTE: Groupings are turned off, and we force US locale for now,
        // due to specifics about the implementation of the pattern-matcher.
        // TODO: Alternately, cast to DecimalFormat, query the decimal and
        //  grouping separator chars, and pass them to pattern-matcher?
        _numberFormat.setGroupingUsed( false );

        // Make sure the list displays all items without scrolling.
        setVisibleRowCount( 25 );

        // Set the non-editable drop-list of filter slope values per order.
        setFilterSlopes( filterSlopeOrder, defaultFilterSlopeIndex );
    }

    public void setFilterSlope( final String filterSlopeLabel ) {
        setValue( filterSlopeLabel );
    }

    // Set the drop-list of Filter Slopes.
    // TODO: Format the text for two decimal places at most (maybe one?).
    public void setFilterSlopes( final short[] filterSlopeOrders,
                                 final int defaultFilterSlopeIndex ) {
        // Start with a clean slate, as we are effectively replacing the entire
        // list. Be careful if restoring the current selection, as there are
        // many edge cases that either do the wrong thing, result in a blank
        // selection field, or do not generate a callback.
        final ObservableList< String > filterSlopes = FXCollections.observableArrayList();
        for ( final short filterSlopeOrder : filterSlopeOrders ) {
            final String filterSlopeLabel = DigitalFilterUtilities
                    .getButterworthFilterSlopeLabel( filterSlopeOrder );
            filterSlopes.add( filterSlopeLabel );
        }

        // Determine the initial default Filter Slope choice, by lookup index.
        final String defaultFilterSlope = DigitalFilterUtilities
                .getButterworthFilterSlopeLabel( filterSlopeOrders[ defaultFilterSlopeIndex ] );

        // Replace the entire list, and re-assert the current selection.
        updateValues( filterSlopes, defaultFilterSlope, true );
    }
}

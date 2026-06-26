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
 * This file is part of the FxAcoustics Library
 *
 * You should have received a copy of the MIT License along with the FxAcoustics
 * Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxacoustics
 */
package com.mhschmieder.fxcontrols.action;

import com.mhschmieder.jcommons.util.ClientProperties;
import org.controlsfx.control.action.Action;

import java.util.Arrays;
import java.util.Collection;

/**
 * This is a struct-like container for generic Frequency Range Horizontal Zoom
 * choices.
 */
public final class FrequencyRangeHorizontalZoomChoices {

    // TODO: Convert these to an enumeration "FrequencyRange".
    public static final int ZOOM_FULL_RANGE    = 0;
    public static final int ZOOM_LOW_FREQ      = ZOOM_FULL_RANGE + 1;
    public static final int ZOOM_LOW_MID_FREQ  = ZOOM_LOW_FREQ + 1;
    public static final int ZOOM_MID_FREQ      = ZOOM_LOW_MID_FREQ + 1;
    public static final int ZOOM_MID_HIGH_FREQ = ZOOM_MID_FREQ + 1;
    public static final int ZOOM_HIGH_FREQ     = ZOOM_MID_HIGH_FREQ + 1;

    // Declare all of the Frequency Range Horizontal Zoom choices.
    public XAction          _zoomFullRangeChoice;
    public XAction          _zoomLowFreqChoice;
    public XAction          _zoomLowMidFreqChoice;
    public XAction          _zoomMidFreqChoice;
    public XAction          _zoomMidHighFreqChoice;
    public XAction          _zoomHighFreqChoice;

    // Cache the associated choice group, for ease of overall enablement.
    public XActionGroup     _frequencyRangeHorizontalZoomChoiceGroup;

    // Default constructor
    @SuppressWarnings("nls")
    public FrequencyRangeHorizontalZoomChoices( final ClientProperties clientProperties ) {
        _zoomFullRangeChoice = AcousticsLabeledActionFactory
                .getZoomFullFrequencyRangeChoice( clientProperties );
        _zoomLowFreqChoice = AcousticsLabeledActionFactory
                .getZoomLowFrequencyRangeChoice( clientProperties );
        _zoomLowMidFreqChoice = AcousticsLabeledActionFactory
                .getZoomLowMidFrequencyRangeChoice( clientProperties );
        _zoomMidFreqChoice = AcousticsLabeledActionFactory
                .getZoomMidFrequencyRangeChoice( clientProperties );
        _zoomMidHighFreqChoice = AcousticsLabeledActionFactory
                .getZoomMidHighFrequencyRangeChoice( clientProperties );
        _zoomHighFreqChoice = AcousticsLabeledActionFactory
                .getZoomHighFrequencyRangeChoice( clientProperties );

        final Collection< Action > frequencyRangeHorizontalZoomChoiceCollection = Arrays
                .asList( _zoomFullRangeChoice,
                         _zoomLowFreqChoice,
                         _zoomLowMidFreqChoice,
                         _zoomMidFreqChoice,
                         _zoomMidHighFreqChoice,
                         _zoomHighFreqChoice );

        _frequencyRangeHorizontalZoomChoiceGroup = ActionFactory
                .makeChoiceGroup( clientProperties,
                                 frequencyRangeHorizontalZoomChoiceCollection,
                                 AcousticsLabeledActionFactory.BUNDLE_NAME,
                                 "horizontalZoom",
                                 "/icons/ahaSoft/LeftRight16.png",
                                 true );
    }

    public XActionGroup getFrequencyRangeHorizontalZoomChoiceGroup() {
        return _frequencyRangeHorizontalZoomChoiceGroup;
    }

    // Reverse-map the selected choice to an invertible index.
    public int getFrequencyRangeHorizontalZoomIndex() {
        if ( _zoomFullRangeChoice.isSelected() ) {
            return ZOOM_FULL_RANGE;
        }
        else if ( _zoomLowFreqChoice.isSelected() ) {
            return ZOOM_LOW_FREQ;
        }
        else if ( _zoomLowMidFreqChoice.isSelected() ) {
            return ZOOM_LOW_MID_FREQ;
        }
        else if ( _zoomMidFreqChoice.isSelected() ) {
            return ZOOM_MID_FREQ;
        }
        else if ( _zoomMidHighFreqChoice.isSelected() ) {
            return ZOOM_MID_HIGH_FREQ;
        }
        else if ( _zoomHighFreqChoice.isSelected() ) {
            return ZOOM_HIGH_FREQ;
        }
        else {
            return ZOOM_FULL_RANGE;
        }
    }

    public void setDisabled( final boolean disabled ) {
        _frequencyRangeHorizontalZoomChoiceGroup.setDisabled( disabled );
    }

    // Sync up the Horizontal Zoom choice with the current Frequency Range
    // Horizontal Zoom index.
    public void setFrequencyRangeHorizontalZoomIndex( final int frequencyRangeHorizontalZoomIndex ) {
        switch ( frequencyRangeHorizontalZoomIndex ) {
        case ZOOM_FULL_RANGE:
            _zoomFullRangeChoice.setSelected( true );
            break;
        case ZOOM_LOW_FREQ:
            _zoomLowFreqChoice.setSelected( true );
            break;
        case ZOOM_LOW_MID_FREQ:
            _zoomLowMidFreqChoice.setSelected( true );
            break;
        case ZOOM_MID_FREQ:
            _zoomMidFreqChoice.setSelected( true );
            break;
        case ZOOM_MID_HIGH_FREQ:
            _zoomMidHighFreqChoice.setSelected( true );
            break;
        case ZOOM_HIGH_FREQ:
            _zoomHighFreqChoice.setSelected( true );
            break;
        default:
            break;
        }
    }
}

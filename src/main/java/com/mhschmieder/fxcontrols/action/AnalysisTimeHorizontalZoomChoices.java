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
package com.mhschmieder.fxcontrols.action;

import com.mhschmieder.jcommons.util.ClientProperties;
import org.controlsfx.control.action.Action;

import java.util.Arrays;
import java.util.Collection;

/**
 * This is a struct-like container for generic Analysis Time Horizontal Zoom
 * choices.
 */
public final class AnalysisTimeHorizontalZoomChoices {

    // Zoom constants for easy referral and logic switching.
    // TODO: Convert these to an enumeration "AnalysisTime".
    public static final int ZOOM_7MS     = 0;
    public static final int ZOOM_70MS    = ZOOM_7MS + 1;
    public static final int ZOOM_14MS    = ZOOM_70MS + 1;
    public static final int ZOOM_140MS   = ZOOM_14MS + 1;
    public static final int ZOOM_28MS    = ZOOM_140MS + 1;
    public static final int ZOOM_280MS   = ZOOM_28MS + 1;
    public static final int ZOOM_56MS    = ZOOM_280MS + 1;
    public static final int ZOOM_560MS   = ZOOM_56MS + 1;
    public static final int ZOOM_112MS   = ZOOM_560MS + 1;
    public static final int ZOOM_1120MS  = ZOOM_112MS + 1;
    public static final int ZOOM_DEFAULT = ZOOM_280MS;

    // Get the Analysis Time edge for the Horizontal Zoom choices.
    public static int getAnalysisTimeEdgeMs( final int analysisTimeIndex ) {
        int analysisTimeEdgeMs = 280;
        
        switch ( analysisTimeIndex ) {
        case ZOOM_7MS:
            analysisTimeEdgeMs = 7;
            break;
        case ZOOM_70MS:
            analysisTimeEdgeMs = 70;
            break;
        case ZOOM_14MS:
            analysisTimeEdgeMs = 14;
            break;
        case ZOOM_140MS:
            analysisTimeEdgeMs = 140;
            break;
        case ZOOM_28MS:
            analysisTimeEdgeMs = 28;
            break;
        case ZOOM_280MS:
            analysisTimeEdgeMs = 280;
            break;
        case ZOOM_56MS:
            analysisTimeEdgeMs = 56;
            break;
        case ZOOM_560MS:
            analysisTimeEdgeMs = 560;
            break;
        case ZOOM_112MS:
            analysisTimeEdgeMs = 112;
            break;
        case ZOOM_1120MS:
            analysisTimeEdgeMs = 1120;
            break;
        default:
            break;
        }
        
        return analysisTimeEdgeMs;
    }

    // Get the Analysis Time index for the Horizontal Zoom choices.
    public static int getAnalysisTimeIndex( final int analysisTimeEdgeMs ) {
        int analysisTimeIndex = ZOOM_DEFAULT;
        
        switch ( analysisTimeEdgeMs ) {
        case 7:
            analysisTimeIndex = ZOOM_7MS;
            break;
        case 70:
            analysisTimeIndex = ZOOM_70MS;
            break;
        case 14:
            analysisTimeIndex = ZOOM_14MS;
            break;
        case 140:
            analysisTimeIndex = ZOOM_140MS;
            break;
        case 28:
            analysisTimeIndex = ZOOM_28MS;
            break;
        case 280:
            analysisTimeIndex = ZOOM_280MS;
            break;
        case 56:
            analysisTimeIndex = ZOOM_56MS;
            break;
        case 560:
            analysisTimeIndex = ZOOM_560MS;
            break;
        case 112:
            analysisTimeIndex = ZOOM_112MS;
            break;
        case 1120:
            analysisTimeIndex = ZOOM_1120MS;
            break;
        default:
            break;
        }
        
        return analysisTimeIndex;
    }

    // Declare all of the Analysis Time Horizontal Zoom choices.
    public XAction      _zoom7msAnalysisTimeChoice;
    public XAction      _zoom70msAnalysisTimeChoice;
    public XAction      _zoom14msAnalysisTimeChoice;
    public XAction      _zoom140msAnalysisTimeChoice;
    public XAction      _zoom28msAnalysisTimeChoice;
    public XAction      _zoom280msAnalysisTimeChoice;
    public XAction      _zoom56msAnalysisTimeChoice;
    public XAction      _zoom560msAnalysisTimeChoice;
    public XAction      _zoom112msAnalysisTimeChoice;
    public XAction      _zoom1120msAnalysisTimeChoice;

    // Cache the associated choice group, for ease of overall enablement.
    public XActionGroup _analysisTimeHorizontalZoomChoiceGroup;

    // Default constructor
    @SuppressWarnings("nls")
    public AnalysisTimeHorizontalZoomChoices( final ClientProperties clientProperties ) {
        _zoom7msAnalysisTimeChoice = LabeledActionFactory
                .getHorizontalZoom7msChoice( clientProperties );
        _zoom70msAnalysisTimeChoice = LabeledActionFactory
                .getHorizontalZoom70msChoice( clientProperties );
        _zoom14msAnalysisTimeChoice = LabeledActionFactory
                .getHorizontalZoom14msChoice( clientProperties );
        _zoom140msAnalysisTimeChoice = LabeledActionFactory
                .getHorizontalZoom140msChoice( clientProperties );
        _zoom28msAnalysisTimeChoice = LabeledActionFactory
                .getHorizontalZoom28msChoice( clientProperties );
        _zoom280msAnalysisTimeChoice = LabeledActionFactory
                .getHorizontalZoom280msChoice( clientProperties );
        _zoom56msAnalysisTimeChoice = LabeledActionFactory
                .getHorizontalZoom56msChoice( clientProperties );
        _zoom560msAnalysisTimeChoice = LabeledActionFactory
                .getHorizontalZoom560msChoice( clientProperties );
        _zoom112msAnalysisTimeChoice = LabeledActionFactory
                .getHorizontalZoom112msChoice( clientProperties );
        _zoom1120msAnalysisTimeChoice = LabeledActionFactory
                .getHorizontalZoom1120msChoice( clientProperties );

        final Collection< Action > analysisTimeHorizontalZoomChoiceCollection = Arrays
                .asList( _zoom7msAnalysisTimeChoice,
                         _zoom70msAnalysisTimeChoice,
                         _zoom14msAnalysisTimeChoice,
                         _zoom140msAnalysisTimeChoice,
                         _zoom28msAnalysisTimeChoice,
                         _zoom280msAnalysisTimeChoice,
                         _zoom56msAnalysisTimeChoice,
                         _zoom560msAnalysisTimeChoice,
                         _zoom112msAnalysisTimeChoice,
                         _zoom1120msAnalysisTimeChoice );

        _analysisTimeHorizontalZoomChoiceGroup = ActionFactory
                .makeChoiceGroup( clientProperties,
                                 analysisTimeHorizontalZoomChoiceCollection,
                                 LabeledActionFactory.BUNDLE_NAME,
                                 "horizontalZoom",
                                 "/icons/ahaSoft/LeftRight16.png",
                                 true );
    }

    public XActionGroup getAnalysisTimeHorizontalZoomChoiceGroup() {
        return _analysisTimeHorizontalZoomChoiceGroup;
    }

    public int getAnalysisTimeIndex() {
        int analysisTimeIndex = ZOOM_DEFAULT;

        if ( _zoom7msAnalysisTimeChoice.isSelected() ) {
            analysisTimeIndex = ZOOM_7MS;
        }
        else if ( _zoom70msAnalysisTimeChoice.isSelected() ) {
            analysisTimeIndex = ZOOM_70MS;
        }
        else if ( _zoom14msAnalysisTimeChoice.isSelected() ) {
            analysisTimeIndex = ZOOM_14MS;
        }
        else if ( _zoom140msAnalysisTimeChoice.isSelected() ) {
            analysisTimeIndex = ZOOM_140MS;
        }
        else if ( _zoom28msAnalysisTimeChoice.isSelected() ) {
            analysisTimeIndex = ZOOM_28MS;
        }
        else if ( _zoom280msAnalysisTimeChoice.isSelected() ) {
            analysisTimeIndex = ZOOM_280MS;
        }
        else if ( _zoom56msAnalysisTimeChoice.isSelected() ) {
            analysisTimeIndex = ZOOM_56MS;
        }
        else if ( _zoom560msAnalysisTimeChoice.isSelected() ) {
            analysisTimeIndex = ZOOM_560MS;
        }
        else if ( _zoom112msAnalysisTimeChoice.isSelected() ) {
            analysisTimeIndex = ZOOM_112MS;
        }
        else if ( _zoom1120msAnalysisTimeChoice.isSelected() ) {
            analysisTimeIndex = ZOOM_1120MS;
        }

        return analysisTimeIndex;
    }

    public boolean isAnalysisTimeZoomedIn() {
        if ( _zoom7msAnalysisTimeChoice.isSelected() || _zoom14msAnalysisTimeChoice.isSelected()
                || _zoom28msAnalysisTimeChoice.isSelected()
                || _zoom56msAnalysisTimeChoice.isSelected()
                || _zoom112msAnalysisTimeChoice.isSelected() ) {
            return true;
        }

        return false;
    }

    // Sync up the horizontal zoom radio button menu items with the current
    // analysis time.
    public void setAnalysisTimeIndex( final int analysisTimeIndex ) {
        switch ( analysisTimeIndex ) {
        case ZOOM_7MS:
            _zoom7msAnalysisTimeChoice.setSelected( true );
            break;
        case ZOOM_70MS:
            _zoom70msAnalysisTimeChoice.setSelected( true );
            break;
        case ZOOM_14MS:
            _zoom14msAnalysisTimeChoice.setSelected( true );
            break;
        case ZOOM_140MS:
            _zoom140msAnalysisTimeChoice.setSelected( true );
            break;
        case ZOOM_28MS:
            _zoom28msAnalysisTimeChoice.setSelected( true );
            break;
        case ZOOM_280MS:
            _zoom280msAnalysisTimeChoice.setSelected( true );
            break;
        case ZOOM_56MS:
            _zoom56msAnalysisTimeChoice.setSelected( true );
            break;
        case ZOOM_560MS:
            _zoom560msAnalysisTimeChoice.setSelected( true );
            break;
        case ZOOM_112MS:
            _zoom112msAnalysisTimeChoice.setSelected( true );
            break;
        case ZOOM_1120MS:
            _zoom1120msAnalysisTimeChoice.setSelected( true );
            break;
        default:
            // If we fell through to the default case, we probably were
            // invoked with a Frequency Range vs. Analysis Time index.
            break;
        }
    }

    public void setDisabled( final boolean disabled ) {
        _analysisTimeHorizontalZoomChoiceGroup.setDisabled( disabled );
    }

    public void updateAnalysisTimeEnablement( final int analysisTimeIndex ) {
        // It is safer to first disable all and then selectively enable.
        // NOTE: Frequency Range and Analysis Time choices cannot coexist,
        // so we must make them invisible and disabled at the same time.
        _zoom7msAnalysisTimeChoice.setDisabled( true );
        _zoom70msAnalysisTimeChoice.setDisabled( true );
        _zoom14msAnalysisTimeChoice.setDisabled( true );
        _zoom140msAnalysisTimeChoice.setDisabled( true );
        _zoom28msAnalysisTimeChoice.setDisabled( true );
        _zoom280msAnalysisTimeChoice.setDisabled( true );
        _zoom56msAnalysisTimeChoice.setDisabled( true );
        _zoom560msAnalysisTimeChoice.setDisabled( true );
        _zoom112msAnalysisTimeChoice.setDisabled( true );
        _zoom1120msAnalysisTimeChoice.setDisabled( true );

        // The Analysis Time determines which pair of "zoom in" and "zoom out"
        // options are enabled or disabled.
        switch ( analysisTimeIndex ) {
        case ZOOM_7MS:
        case ZOOM_70MS:
            _zoom7msAnalysisTimeChoice.setDisabled( false );
            _zoom70msAnalysisTimeChoice.setDisabled( false );
            break;
        case ZOOM_14MS:
        case ZOOM_140MS:
            _zoom14msAnalysisTimeChoice.setDisabled( false );
            _zoom140msAnalysisTimeChoice.setDisabled( false );
            break;
        case ZOOM_28MS:
        case ZOOM_280MS:
            _zoom28msAnalysisTimeChoice.setDisabled( false );
            _zoom280msAnalysisTimeChoice.setDisabled( false );
            break;
        case ZOOM_56MS:
        case ZOOM_560MS:
            _zoom56msAnalysisTimeChoice.setDisabled( false );
            _zoom560msAnalysisTimeChoice.setDisabled( false );
            break;
        case ZOOM_112MS:
        case ZOOM_1120MS:
            _zoom112msAnalysisTimeChoice.setDisabled( false );
            _zoom1120msAnalysisTimeChoice.setDisabled( false );
            break;
        default:
            break;
        }
    }
}

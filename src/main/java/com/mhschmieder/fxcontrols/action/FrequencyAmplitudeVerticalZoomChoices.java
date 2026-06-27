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
 * This is a struct-like container for generic Frequency Amplitude Vertical Zoom
 * choices.
 */
public final class FrequencyAmplitudeVerticalZoomChoices {

    // Declare all of the Vertical Zoom choices.
    public XAction      _div1dbChoice;
    public XAction      _div2dbChoice;
    public XAction      _div3dbChoice;
    public XAction      _div6dbChoice;
    public XAction      _div10dbChoice;
    public XAction      _div12dbChoice;
    public XAction      _div15dbChoice;
    public XAction      _div20dbChoice;
    public XAction      _div30dbChoice;

    // Cache the associated choice group, for ease of overall enablement.
    public XActionGroup _frequencyAmplitudeVerticalZoomChoiceGroup;

    // Default constructor
    @SuppressWarnings("nls")
    public FrequencyAmplitudeVerticalZoomChoices( final ClientProperties clientProperties ) {
        _div1dbChoice = LabeledActionFactory.getDiv1DbChoice( clientProperties );
        _div2dbChoice = LabeledActionFactory.getDiv2DbChoice( clientProperties );
        _div3dbChoice = LabeledActionFactory.getDiv3DbChoice( clientProperties );
        _div6dbChoice = LabeledActionFactory.getDiv6DbChoice( clientProperties );
        _div10dbChoice = LabeledActionFactory.getDiv10DbChoice( clientProperties );
        _div12dbChoice = LabeledActionFactory.getDiv12DbChoice( clientProperties );
        _div15dbChoice = LabeledActionFactory.getDiv15DbChoice( clientProperties );
        _div20dbChoice = LabeledActionFactory.getDiv20DbChoice( clientProperties );
        _div30dbChoice = LabeledActionFactory.getDiv30DbChoice( clientProperties );

        final Collection< Action > frequencyAmplitudeVerticalZoomChoiceCollection = Arrays
                .asList( _div1dbChoice,
                         _div2dbChoice,
                         _div3dbChoice,
                         _div6dbChoice,
                         _div10dbChoice,
                         _div12dbChoice,
                         _div15dbChoice,
                         _div20dbChoice,
                         _div30dbChoice );

        _frequencyAmplitudeVerticalZoomChoiceGroup = ActionFactory
                .makeChoiceGroup( clientProperties,
                                 frequencyAmplitudeVerticalZoomChoiceCollection,
                                 LabeledActionFactory.BUNDLE_NAME,
                                 "verticalZoom",
                                 "/icons/ahaSoft/UpDown16.png",
                                 true );
    }

    public XActionGroup getFrequencyAmplitudeVerticalZoomChoiceGroup() {
        return _frequencyAmplitudeVerticalZoomChoiceGroup;
    }

    // Reverse-map the selected choice to an invertible Vertical Grid Spacing.
    public int getVerticalGridSpacing() {
        if ( _div1dbChoice.isSelected() ) {
            return 1;
        }
        else if ( _div2dbChoice.isSelected() ) {
            return 2;
        }
        else if ( _div3dbChoice.isSelected() ) {
            return 3;
        }
        else if ( _div6dbChoice.isSelected() ) {
            return 6;
        }
        else if ( _div10dbChoice.isSelected() ) {
            return 10;
        }
        else if ( _div12dbChoice.isSelected() ) {
            return 12;
        }
        else if ( _div15dbChoice.isSelected() ) {
            return 15;
        }
        else if ( _div20dbChoice.isSelected() ) {
            return 20;
        }
        else if ( _div30dbChoice.isSelected() ) {
            return 30;
        }
        else {
            // Default to something in the middle of the choices.
            return 10;
        }
    }

    public void setDisabled( final boolean disabled ) {
        _frequencyAmplitudeVerticalZoomChoiceGroup.setDisabled( disabled );
    }

    // Sync up the Vertical Zoom choice with the current Vertical Grid Spacing
    // value.
    public void setVerticalGridSpacing( final int verticalGridSpacing ) {
        switch ( verticalGridSpacing ) {
        case 1:
            _div1dbChoice.setSelected( true );
            break;
        case 2:
            _div2dbChoice.setSelected( true );
            break;
        case 3:
            _div3dbChoice.setSelected( true );
            break;
        case 6:
            _div6dbChoice.setSelected( true );
            break;
        case 10:
            _div10dbChoice.setSelected( true );
            break;
        case 12:
            _div12dbChoice.setSelected( true );
            break;
        case 15:
            _div15dbChoice.setSelected( true );
            break;
        case 20:
            _div20dbChoice.setSelected( true );
            break;
        case 30:
            _div30dbChoice.setSelected( true );
            break;
        default:
            _div10dbChoice.setSelected( true );
            break;
        }
    }
}

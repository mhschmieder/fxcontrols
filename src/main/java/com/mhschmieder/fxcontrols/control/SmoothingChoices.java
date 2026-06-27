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
 *  Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxcontrols
 */
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.fxcontrols.action.ActionFactory;
import com.mhschmieder.fxcontrols.action.LabeledActionFactory;
import com.mhschmieder.fxcontrols.action.XAction;
import com.mhschmieder.fxcontrols.action.XActionGroup;
import com.mhschmieder.jcommons.util.ClientProperties;
import com.mhschmieder.jphysics.acoustics.Smoothing;
import org.controlsfx.control.action.Action;

import java.util.Arrays;
import java.util.Collection;

/**
 * This is a struct-like container for Smoothing choices.
 * <p>
 * TODO: Move the icon into the resources folder and reference correctly.
 */
public final class SmoothingChoices {

    // Declare all of the Smoothing choices.
    public XAction      _smoothingNarrowChoice;
    public XAction      _smoothingSixthOctaveChoice;
    public XAction      _smoothingThirdOctaveChoice;

    // Cache the associated choice group, for ease of overall enablement.
    public XActionGroup _smoothingChoiceGroup;

    // Default constructor
    @SuppressWarnings("nls")
    public SmoothingChoices( final ClientProperties clientProperties ) {
        _smoothingNarrowChoice = LabeledActionFactory
                .getSmoothingNarrowChoice( clientProperties );
        _smoothingSixthOctaveChoice = LabeledActionFactory
                .getSmoothingSixthOctaveChoice( clientProperties );
        _smoothingThirdOctaveChoice = LabeledActionFactory
                .getSmoothingThirdOctaveChoice( clientProperties );

        final Collection< Action > smoothingChoiceCollection = Arrays
                .asList( _smoothingNarrowChoice,
                         _smoothingSixthOctaveChoice,
                         _smoothingThirdOctaveChoice );

        _smoothingChoiceGroup = ActionFactory
                .makeChoiceGroup( clientProperties,
                                  smoothingChoiceCollection,
                                  LabeledActionFactory.BUNDLE_NAME,
                                  "smoothing",
                                  "/icons/ahaSoft/Smooth16.png",
                                  true );
    }

    public Smoothing getSmoothing() {
        if ( _smoothingNarrowChoice.isSelected() ) {
            return Smoothing.NARROW_BAND;
        }
        else if ( _smoothingSixthOctaveChoice.isSelected() ) {
            return Smoothing.SIXTH_OCTAVE_BAND;
        }
        else if ( _smoothingThirdOctaveChoice.isSelected() ) {
            return Smoothing.THIRD_OCTAVE_BAND;
        }
        else {
            return Smoothing.defaultValue();
        }
    }

    public XActionGroup getSmoothingChoiceGroup() {
        return _smoothingChoiceGroup;
    }

    public int getSmoothingOctaveDivider() {
        final Smoothing smoothing = getSmoothing();
        final int smoothingOctaveDivider = smoothing.toOctaveDivider();
        return smoothingOctaveDivider;
    }

    public void setDisabled( final boolean disabled ) {
        _smoothingChoiceGroup.setDisabled( disabled );
    }

    public void setSmoothing( final Smoothing smoothing ) {
        // Sync up the choices with the current Smoothing value.
        switch ( smoothing ) {
        case Smoothing.NARROW_BAND:
            _smoothingNarrowChoice.setSelected( true );
            break;
        case Smoothing.SIXTH_OCTAVE_BAND:
            _smoothingSixthOctaveChoice.setSelected( true );
            break;
        case Smoothing.THIRD_OCTAVE_BAND:
            _smoothingThirdOctaveChoice.setSelected( true );
            break;
        default:
            // NOTE: Theoretically impossible case.
            break;
        }
    }

    public void setSmoothingOctaveDivider( final int octaveDivider ) {
        // Sync up the radio button menu items with the current Smoothing value.
        final Smoothing smoothing = Smoothing.fromOctaveDivider( octaveDivider );
        setSmoothing( smoothing );
    }

}

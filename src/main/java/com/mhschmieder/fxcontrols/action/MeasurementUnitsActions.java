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
import org.controlsfx.control.action.Action;

import java.util.Arrays;
import java.util.Collection;

/**
 * This is a struct-like container for actions used by Measurement Units.
 */
public final class MeasurementUnitsActions {

    public XAction _resetAction;

    public MeasurementUnitsActions( final ClientProperties pClientProperties ) {
        _resetAction = com.mhschmieder.fxcontrols.action.LabeledActionFactory.getResetAction( pClientProperties );

        // The tool tip for "Reset" is unique per context so isn't in the
        // locale-sensitive resources for the generic action lookup.
        _resetAction.setLongText( "Reset to Default Scientific Units" ); //$NON-NLS-1$
    }

    public Collection< Action > getMeasurementUnitsActionCollection() {
        final Collection< Action > measurementUnitsActionCollection = Arrays.asList( _resetAction );

        return measurementUnitsActionCollection;
    }
}

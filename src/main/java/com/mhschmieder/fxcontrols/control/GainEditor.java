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

import com.mhschmieder.jcommons.lang.StringUtilities;
import com.mhschmieder.jcommons.util.ClientProperties;

public final class GainEditor extends DoubleEditor {

    // Declare value increment/decrement amount for up and down arrow keys.
    public static final double VALUE_INCREMENT_DEFAULT_DB = 0.1d;

    // Cache the preference for defaulting to negative gain.
    protected boolean          _defaultToNegativeGain;

    public GainEditor( final ClientProperties pClientProperties,
                       final String initialText,
                       final String tooltipText,
                       final double gainMinimumDb,
                       final double gainMaximumDb,
                       final double gainDefaultDb,
                       final boolean defaultToNegativeGain ) {
        // Always call the superclass constructor first!
        // NOTE: We use up to one decimal place of precision for displaying
        // gain, and up to four decimal places for parsing gain.
        super( pClientProperties,
               initialText,
               tooltipText,
               true,
               0,
               1,
               0,
               4,
               gainMinimumDb,
               gainMaximumDb,
               gainDefaultDb,
               VALUE_INCREMENT_DEFAULT_DB );

        _defaultToNegativeGain = defaultToNegativeGain;
    }

    @Override
    public String getDecoratedText( final double savedValue, final String savedText ) {
        // By default, Java does not allow the positive sign to be typed, but
        // many users may be in the habit of typing it in, so we allow it in our
        // key filter and re-add it here so that numbers get parsed correctly
        // vs. throwing exceptions and defaulting to previous values.
        // NOTE: If defaulting to negative numbers, we must also re-attach the
        //  positive sign (if not present) if a non-negative number is detected.
        // TODO: Find a way to exempt zero values from prepending the "+" sign?
        // TODO: Test to see if the new condition on value > 0 fixes this.
        final String decoratedText = _defaultToNegativeGain && ( savedValue > 0.0d )
            ? StringUtilities.attachPositiveSign( savedText )
            : savedText;

        return decoratedText;
    }

    @Override
    public String getUndecoratedText( final String savedText ) {
        // By default, Java does not allow the positive sign to be typed, but
        // many users may be in the habit of typing it in, so we allow it in our
        // key filter and then must strip it here so that numbers get parsed
        // correctly vs. throwing exceptions and defaulting to previous values.
        // NOTE: If defaulting to negative numbers, we must also add the
        //  negative sign when the positive sign is not explicitly typed.
        final String undecoratedText = _defaultToNegativeGain
            ? StringUtilities.defaultToNegativeNumber( savedText )
            : StringUtilities.stripPositiveSign( savedText );

        return undecoratedText;
    }
}

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
import com.mhschmieder.jmath.MathUtilities;
import org.apache.commons.math3.util.FastMath;

public class AngleEditor extends DoubleEditor {

    // Declare value increment/decrement amount for up and down arrow keys.
    // NOTE: We increment by 0.1 degrees as this is a common default.
    public static final double VALUE_INCREMENT_DEGREES = 0.1d;

    public AngleEditor( final ClientProperties pClientProperties,
                        final String initialText,
                        final String tooltipText,
                        final int minFractionDigitsFormat,
                        final int maxFractionDigitsFormat,
                        final int minFractionDigitsParse,
                        final int maxFractionDigitsParse,
                        final double minimumValue,
                        final double maximumValue,
                        final double initialValue ) {
        this( pClientProperties,
               initialText,
               tooltipText,
               minFractionDigitsFormat,
               maxFractionDigitsFormat,
               minFractionDigitsParse,
               maxFractionDigitsParse,
               minimumValue,
               maximumValue,
               initialValue,
               VALUE_INCREMENT_DEGREES );
    }

    public AngleEditor( final ClientProperties pClientProperties,
                        final String initialText,
                        final String tooltipText,
                        final int minFractionDigitsFormat,
                        final int maxFractionDigitsFormat,
                        final int minFractionDigitsParse,
                        final int maxFractionDigitsParse,
                        final double minimumValue,
                        final double maximumValue,
                        final double initialValue,
                        final double valueIncrement ) {
        // Always call the superclass constructor first!
        super( pClientProperties,
               initialText,
               tooltipText,
               true,
               minFractionDigitsFormat,
               maxFractionDigitsFormat,
               minFractionDigitsParse,
               maxFractionDigitsParse,
               minimumValue,
               maximumValue,
               initialValue,
               valueIncrement );
    }

    @Override
    public double getClampedValue( final double unclampedValue ) {
        // If the allowed angle range is a full period or more (360+ degrees),
        // then unwrap the angle. Otherwise, apply standard min/max clamping.
        final double clampedValue = ( FastMath.abs( _maximumValue - _minimumValue ) >= 360.0d )
            ? getUnwrappedAngleDegrees( unclampedValue )
            : super.getClampedValue( unclampedValue );

        return clampedValue;
    }

    public double getUnwrappedAngleDegrees( final double unclampedValue ) {
        // Unwrap the angle based on period, using the established minimum and
        // maximum so that we don't accidentally clamp, but still clamp if the
        // allowed range itself is less than a full period.
        double unwrappedAngleDegrees = MathUtilities.unwrapAngleRangeDegrees(
            unclampedValue, _minimumValue, _maximumValue );
        
        return unwrappedAngleDegrees;
    }
}

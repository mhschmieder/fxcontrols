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
package com.mhschmieder.fxcontrols.util;

import com.mhschmieder.jsigproc.filter.ElectronicFilterType;

public class LabelFactory {

    private LabelFactory() {
    }

    public static String getBandwidthLabel() {
        return "Bandwidth";
    }

    public static String getDelayLabel() {
        return "Delay";
    }

    public static String getFrequencyLabel() {
        return "Frequency";
    }

    public static String getGainLabel() {
        return "Gain";
    }

    public static String getMuteLabel() {
        return "Mute";
    }

    public static String getPolarityLabel() {
        return "Polarity";
    }

    public static String getQFactorLabel() {
        return "Q";
    }

    public static String getSlopeLabel() {
        return "Slope";
    }

    public static String getEqualizationLabel() {
        return "Equalization";
    }

    public static String getEqualizationLabelAbbreviated() {
        return "EQ";
    }

    public static String getParametricLabel() {
        return "Parametric";
    }

    public static String getHighLowPassFilterSlopeTooltip(final ElectronicFilterType filterType) {
        switch ( filterType ) {
        case ElectronicFilterType.HIGH_LOW_PASS:
            return "High/Low Pass Filter Slope";
        case ElectronicFilterType.HIGH_PASS:
            return "High Pass Filter Slope";
        case ElectronicFilterType.LOW_PASS:
            return "Low Pass Filter Slope";
        default:
            return "";
        }
    }

    public static String getHighLowPassLabel() {
        return "High/Low Pass";
    }

    public static String getHighPassLabel() {
        return "High Pass";
    }

    public static String getLowPassLabel() {
        return "Low Pass";
    }
}

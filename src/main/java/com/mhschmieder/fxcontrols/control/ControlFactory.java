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
 * This file is part of the FxGuiToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * FxGuiToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxguitoolkit
 */
package com.mhschmieder.fxcontrols.control;

import com.mhschmieder.fxcontrols.util.RegionUtilities;
import com.mhschmieder.fxgraphics.geometry.SurfaceMaterial;
import com.mhschmieder.jcommons.util.ClientProperties;
import com.mhschmieder.jphysics.measure.AngleUnit;
import com.mhschmieder.jphysics.measure.DistanceUnit;
import com.mhschmieder.jphysics.measure.PressureUnit;
import com.mhschmieder.jphysics.measure.TemperatureUnit;
import com.mhschmieder.jphysics.measure.WeightUnit;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;

/**
 * This is a factory for generating customized controls. It is a way of avoiding
 * class derivation where that is not necessary for altering the default
 * behavior of the core JavaFX controls -- especially for stuff like spinners.
 * <p>
 * One of its roles is to enforce preferred style guidelines and common
 * behavior.
 *
 * @version 0.1
 *
 * @author Mark Schmieder
 */
public class ControlFactory {

    // Predefine the notes/notices colors.
    public static final Color NOTES_BACKGROUND_COLOR = Color.FLORALWHITE;
    public static final Color NOTES_FOREGROUND_COLOR = Color.BLACK;

    // Default SPL range, for best "out of box" experience.
    public static final int SPL_RANGE_DB_DEFAULT = 42;

    // Default Dithering Amount, for best "out of box" experience.
    public static final double DITHERING_AMOUNT_DEFAULT = 8.0d;

    /**
     * The default constructor is disabled, as this is a static factory class.
     */
    private ControlFactory() {}

    public static IntegerEditor makeIntegerPercentEditor( final ClientProperties pClientProperties,
                                                          final String tooltipText,
                                                          final int defaultValue ) {
        // Set the current value and format it as initial text.
        final String initialText = defaultValue + "%";

        // Declare value increment/decrement amount for up and down arrow keys.
        // NOTE: We increment by 1 percent as this is an integer-based editor.
        final int valueIncrementPercent = 1;
    
        final IntegerEditor percentEditor = new IntegerEditor(
                 pClientProperties,
                 initialText,
                 tooltipText,
                 true,
                 0,
                 100,
                 defaultValue,
                 valueIncrementPercent );
    
        percentEditor.setMeasurementUnitString( "%" );
    
        return percentEditor;
    }

    public static DoubleEditor makeDoublePercentEditor( final ClientProperties pClientProperties,
                                                        final String tooltipText,
                                                        final double defaultValue ) {
        // Set the current value and format it as initial text.
        final String initialText = defaultValue + "%";

        // Declare value increment/decrement amount for up and down arrow keys.
        // NOTE: We increment by 1 percent as this is an integer-based editor.
        final double valueIncrementPercent = 1.0d;

        final DoubleEditor percentEditor = new DoubleEditor(
             pClientProperties,
             initialText,
             tooltipText,
             true,
             0,
             4,
             0,
             6,
             0.0d,
             100.0d,
             defaultValue,
             valueIncrementPercent );

        percentEditor.setMeasurementUnitString( "%" );

        return percentEditor;
    }

    // Helper method to get an Opacity Editor, standalone or paired.
    public static final DoubleEditor makeOpacityEditor( final ClientProperties clientProperties,
                                                        final String measurementUnit,
                                                        final double minimumOpacityPercent,
                                                        final double maximumOpacityPercent,
                                                        final double initialOpacityPercent ) {
        // Get the current value and format it as initial text.
        final String initialText = Double.toString( initialOpacityPercent ) + measurementUnit;

        // Declare value increment/decrement amount for up and down arrow keys,
        // set to 0.5% as a mid-way value of general use for auto-increment.
        final double valueIncrementPercent = 0.5d;

        // NOTE: We use up to one decimal place of precision for displaying
        //  opacity, and one decimal place for parsing opacity.
        final String tooltipText = "Enter an Opacity between " 
                + minimumOpacityPercent + measurementUnit
                + " and " + maximumOpacityPercent + measurementUnit;
        final DoubleEditor opacityEditor = new DoubleEditor( clientProperties,
                                                             initialText,
                                                             tooltipText,
                                                             true,
                                                             0,
                                                             2,
                                                             0,
                                                             4,
                                                             minimumOpacityPercent,
                                                             maximumOpacityPercent,
                                                             initialOpacityPercent,
                                                             valueIncrementPercent );

        opacityEditor.setMeasurementUnitString( measurementUnit );

        return opacityEditor;
    }

    // Helper method to get a default Opacity Editor, usually for a table cell.
    public static final DoubleEditor makeOpacityEditor( final ClientProperties clientProperties ) {
        return makeOpacityEditor( clientProperties,
                                  OpacitySlider.PERCENT_MEASUREMENT_UNIT,
                                  OpacitySlider.DEFAULT_MINIMUM_OPACITY_PERCENT,
                                  OpacitySlider.DEFAULT_MAXIMUM_OPACITY_PERCENT,
                                  OpacitySlider.DEFAULT_INITIAL_OPACITY_PERCENT );
    }

    // Helper method to get an Opacity Editor to pair with a slider.
    public static final DoubleEditor makeOpacitySliderEditor( final ClientProperties clientProperties,
                                                              final OpacitySlider opacitySlider ) {
        // Use the current slider value and limits to set the number textField.
        final DoubleEditor opacityEditor = makeOpacityEditor( clientProperties,
                                                              opacitySlider
                                                                      .getMeasurementUnitString(),
                                                              opacitySlider.getMin(),
                                                              opacitySlider.getMax(),
                                                              opacitySlider.getValue() );

        return opacityEditor;
    }

    public static final Spinner< Double > makeDoubleSpinner( final ClientProperties clientProperties,
                                                             final boolean applyToolkitCss,
                                                             final String valueDescriptor,
                                                             final double minimumNumericValue,
                                                             final double maximumNumericValue,
                                                             final double defaultNumericValue,
                                                             final double numericIncrement,
                                                             final boolean wrapAround,
                                                             final String numericFormatterPattern,
                                                             final String measurementUnit,
                                                             final double maximumSpinnerWidth ) {
        // Start with a fully initialized Spinner, with range specified.
        final Spinner< Double > doubleSpinner = new Spinner<>( minimumNumericValue,
                                                               maximumNumericValue,
                                                               defaultNumericValue,
                                                               numericIncrement );
        DoubleSpinnerStringConverter.createFor( doubleSpinner,
                                                valueDescriptor,
                                                defaultNumericValue,
                                                maximumSpinnerWidth,
                                                wrapAround,
                                                numericFormatterPattern,
                                                measurementUnit,
                                                clientProperties.locale );

        if ( applyToolkitCss ) {
            // Apply drop-shadow effects when the mouse enters this Node.
            ControlUtilities.applyDropShadowEffect( doubleSpinner );
        }
        else {
            // Set the full list of shared Spinner Properties (CSS etc.).
            ControlUtilities.setSpinnerProperties( doubleSpinner );
        }

        // Return the fully initialized double Spinner.
        return doubleSpinner;
    }

    public static final Spinner< Integer > makeIntegerSpinner( final ClientProperties clientProperties,
                                                               final boolean applyToolkitCss,
                                                               final String valueDescriptor,
                                                               final int minimumNumericValue,
                                                               final int maximumNumericValue,
                                                               final int defaultNumericValue,
                                                               final int numericIncrement,
                                                               final boolean wrapAround,
                                                               final String numericFormatterPattern,
                                                               final String measurementUnit,
                                                               final double maximumSpinnerWidth ) {
        // Start with a fully initialized Spinner, with range specified.
        final Spinner< Integer > integerSpinner = new Spinner<>( minimumNumericValue,
                                                                 maximumNumericValue,
                                                                 defaultNumericValue,
                                                                 numericIncrement );
        IntegerSpinnerStringConverter.createFor( integerSpinner,
                                                 valueDescriptor,
                                                 defaultNumericValue,
                                                 maximumSpinnerWidth,
                                                 wrapAround,
                                                 numericFormatterPattern,
                                                 measurementUnit,
                                                 clientProperties.locale );

        if ( applyToolkitCss ) {
            // Apply drop-shadow effects when the mouse enters this Node.
            ControlUtilities.applyDropShadowEffect( integerSpinner );
        }
        else {
            // Set the full list of shared Spinner Properties (CSS etc.).
            ControlUtilities.setSpinnerProperties( integerSpinner );
        }

        // Return the fully initialized integer Spinner.
        return integerSpinner;
    }

    public static final TextArea makeNoticeTextArea( final String noticeTextContent,
                                                     final boolean editable,
                                                     final int numberOfColumns,
                                                     final int numberOfRows ) {
        final TextArea noticeTextArea = new TextArea( noticeTextContent );
        noticeTextArea.setEditable( editable );
        noticeTextArea.setWrapText( true );
        noticeTextArea.setPrefColumnCount( numberOfColumns );
        noticeTextArea.setPrefRowCount( numberOfRows );

        final Background background = RegionUtilities.makeRegionBackground(
                NOTES_BACKGROUND_COLOR );
        noticeTextArea.setBackground( background );

        return noticeTextArea;
    }

    public static final TextFlow makeNoticeTextFlow( final Text noticeText,
                                                     final int numberOfColumns,
                                                     final int numberOfRows ) {
        // NOTE: The sizing is a temporary hack to avoid full-screen.
        final TextFlow noticeTextFlow = new TextFlow( noticeText );
        noticeTextFlow.setMaxWidth( numberOfRows * 12d );
        noticeTextFlow.setMaxHeight( numberOfColumns * 12d );

        final Background background = RegionUtilities.makeRegionBackground(
                NOTES_BACKGROUND_COLOR );
        noticeTextFlow.setBackground( background );

        return noticeTextFlow;
    }

    public static final WebView makeNoticeWebView( final String noticeHtmlContent ) {
        final WebView noticeWebView = new WebView();
        noticeWebView.getEngine().loadContent( noticeHtmlContent );
        noticeWebView.autosize();

        return noticeWebView;
    }

    public static final WebView makeNoticeWebView( final URL noticeUrl ) {
        final WebView noticeWebView = new WebView();
        final WebEngine noticeWebEngine = noticeWebView.getEngine();
        noticeWebEngine.load( noticeUrl.toString() );

        // NOTE: We're having problems with horizontal scroll bars showing up
        // on Windows 10, if not also on Mac OS, unless we down-scale the font.
        // NOTE: This has changed with recent Java updates, so the DPI based
        // scaling was backed out and replaced downstream with font scaling.
        // noticeWebView.setZoom( Screen.getPrimary().getDpi() / 96 );
        noticeWebView.autosize();

        return noticeWebView;
    }

    public static XComboBox<SurfaceMaterial> getSurfaceMaterialSelector(
            final ClientProperties pClientProperties,
            final String tooltipText,
            final boolean applyToolkitCss ) {
        final SurfaceMaterial[] supportedValues = {
                SurfaceMaterial.ACOUSTIC_TILE_ON_RIGID_SURF_KF,
                SurfaceMaterial.BRICK_WALL_PAINTED_LB,
                SurfaceMaterial.BRICK_WALL_UNPAINTED_LB,
                SurfaceMaterial.CARPET_HEAVY_ON_CONCRETE_CH,
                SurfaceMaterial.CONCRETE_BLOCK_PAINTED_CH,
                SurfaceMaterial.CONCRETE_BLOCK_UNPAINTED_CH,
                SurfaceMaterial.PLASTER_ON_LATHE_CH,
                SurfaceMaterial.POURED_CONCRETE_PAINTED_LB,
                SurfaceMaterial.POURED_CONCRETE_UNPAINTED_LB,
                SurfaceMaterial.RIGID,
                SurfaceMaterial.VELOUR_TEN_OZ_PER_YARD_SQR_TOUCHING_WALL_CH };
        return ListViewUtilities.makeLabeledSelector(
                pClientProperties,
                supportedValues,
                tooltipText,
                SurfaceMaterial.defaultValue() );
    }

    public static IntegerSelector getProjectionZonesSelector( final ClientProperties pClientProperties,
                                                              final boolean applyToolkitCss,
                                                              final String tooltipText) {
        // NOTE: Limit to 12 zones vs. 24 for now, due to moire patterns that
        // occur in integer-based pixel systems such as AWT (JavaFX is
        // floating-point based), when the number of zones is large relative to
        // the number of pixels between start and end points of the Linear Object.
        final int minimumNumberOfProjectionZones = 1;
        final int maximumNumberOfProjectionZones = 12; // 24
        final int projectionZonesIncrement = 1;

        return new IntegerSelector(
                pClientProperties,
                true,
                tooltipText,
                applyToolkitCss,
                false,
                false,
                minimumNumberOfProjectionZones,
                maximumNumberOfProjectionZones,
                projectionZonesIncrement );
    }

    // Helper method to get an Angle Editor, standalone or paired.
    public static AngleEditor makeAngleEditor(final ClientProperties clientProperties,
                                              final String tooltipText,
                                              final double minimumValue,
                                              final double maximumValue,
                                              final double initialValue) {
        return makeAngleEditor( clientProperties,
                tooltipText,
                AngleUnit.DEGREES.abbreviation(),
                minimumValue,
                maximumValue,
                initialValue );
    }

    // Helper method to get an Angle Editor, standalone or paired.
    public static AngleEditor makeAngleEditor(final ClientProperties clientProperties,
                                              final String tooltipText,
                                              final String measurementUnit,
                                              final double minimumValue,
                                              final double maximumValue,
                                              final double initialValue) {
        return makeAngleEditor( clientProperties,
                tooltipText,
                0,
                2,
                0,
                10,
                measurementUnit,
                minimumValue,
                maximumValue,
                initialValue );
    }

    // Helper method to get an Angle Editor, standalone or paired.
    public static AngleEditor makeAngleEditor(final ClientProperties clientProperties,
                                              final String tooltipText,
                                              final int minFractionDigitsFormat,
                                              final int maxFractionDigitsFormat,
                                              final int minFractionDigitsParse,
                                              final int maxFractionDigitsParse,
                                              final String measurementUnit,
                                              final double minimumValue,
                                              final double maximumValue,
                                              final double initialValue) {
        // Get the current value and format it as initial text.
        final String initialText = Double.toString( initialValue ) + measurementUnit;

        final AngleEditor angleEditor = new AngleEditor( clientProperties,
                initialText,
                tooltipText,
                minFractionDigitsFormat,
                maxFractionDigitsFormat,
                minFractionDigitsParse,
                maxFractionDigitsParse,
                minimumValue,
                maximumValue,
                initialValue );

        angleEditor.setMeasurementUnitString( measurementUnit );

        return angleEditor;
    }

    // Helper method to get an Angle Editor, standalone or paired.
    public static AngleEditor makeAngleEditor(final ClientProperties clientProperties,
                                              final String tooltipText,
                                              final int minFractionDigitsFormat,
                                              final int maxFractionDigitsFormat,
                                              final int minFractionDigitsParse,
                                              final int maxFractionDigitsParse,
                                              final String measurementUnit,
                                              final double minimumValue,
                                              final double maximumValue,
                                              final double initialValue,
                                              final double valueIncrement) {
        // Get the current value and format it as initial text.
        final String initialText = Double.toString( initialValue ) + measurementUnit;

        final AngleEditor angleEditor = new AngleEditor( clientProperties,
                initialText,
                tooltipText,
                minFractionDigitsFormat,
                maxFractionDigitsFormat,
                minFractionDigitsParse,
                maxFractionDigitsParse,
                minimumValue,
                maximumValue,
                initialValue,
                valueIncrement );

        angleEditor.setMeasurementUnitString( measurementUnit );

        return angleEditor;
    }

    // Helper method to get an Angle Editor to pair with a slider.
    public static AngleEditor makeAngleSliderEditor(
            final ClientProperties clientProperties,
            final AngleSlider angleSlider ) {

        return makeAngleSliderEditor(
                clientProperties,
                angleSlider,
                0,
                2,
                0,
                10 );
    }

    // Helper method to get an Angle Editor to pair with a slider.
    public static AngleEditor makeAngleSliderEditor(final ClientProperties clientProperties,
                                                    final AngleSlider angleSlider,
                                                    final int minFractionDigitsFormat,
                                                    final int maxFractionDigitsFormat,
                                                    final int minFractionDigitsParse,
                                                    final int maxFractionDigitsParse) {
        // Use the current slider value and limits to set the number textField.
        return makeAngleEditor( clientProperties,
                null,
                minFractionDigitsFormat,
                maxFractionDigitsFormat,
                minFractionDigitsParse,
                maxFractionDigitsParse,
                angleSlider.getMeasurementUnitString(),
                angleSlider.getMin(),
                angleSlider.getMax(),
                angleSlider.getValue() );
    }

    // Helper method to get a custom Temperature Editor.
    public static TemperatureEditor makeTemperatureEditor(
            final ClientProperties clientProperties ) {
        // Format the default Temperature value as the initial text.
        final double initialValue = TemperatureSlider.INITIAL_TEMPERATURE_KELVIN_DEFAULT;
        final String initialText = Double.toString( initialValue );

        return new TemperatureEditor(
                clientProperties,
                initialText,
                null,
                TemperatureSlider.MINIMUM_TEMPERATURE_KELVIN_DEFAULT,
                TemperatureSlider.MAXIMUM_TEMPERATURE_KELVIN_DEFAULT,
                initialValue );
    }

    // Helper method to get a custom Pressure Editor.
    public static PressureEditor makePressureEditor(
            final ClientProperties clientProperties) {
        // Format the default Pressure value as the initial text.
        final double initialValue = PressureSlider.INITIAL_PRESSURE_PASCALS_DEFAULT;
        final String initialText = Double.toString( initialValue );

        return new PressureEditor(
                clientProperties,
                initialText,
                null,
                PressureSlider.MINIMUM_PRESSURE_PASCALS_DEFAULT,
                PressureSlider.MAXIMUM_PRESSURE_PASCALS_DEFAULT,
                initialValue );
    }

    // Helper method to get a humidity textField to pair with a slider.
    public static HumidityEditor makeHumiditySliderEditor(final ClientProperties clientProperties,
                                                          final HumiditySlider humiditySlider) {
        // Get the current slider value and format it as initial text.
        final double initialValue = HumiditySlider.INITIAL_RELATIVE_HUMIDITY_DEFAULT;
        final String initialText = Double.toString( initialValue );

        final HumidityEditor humidityEditor =
                new HumidityEditor( clientProperties,
                        initialText,
                        null,
                        HumiditySlider.MINIMUM_RELATIVE_HUMIDITY_DEFAULT,
                        HumiditySlider.MAXIMUM_RELATIVE_HUMIDITY_DEFAULT,
                        initialValue );

        final String measurementUnitString = humiditySlider.getMeasurementUnitString();
        humidityEditor.setMeasurementUnitString( measurementUnitString );

        return humidityEditor;
    }

    public static XComboBox<DistanceUnit> makeDistanceUnitSelector(
            final ClientProperties pClientProperties,
            final boolean applyToolkitCss,
            final boolean includeUnitless,
            final DistanceUnit defaultDistanceUnit ) {
        final DistanceUnit[] distanceUnitsKnown = {
                DistanceUnit.MILLIMETERS,
                DistanceUnit.CENTIMETERS,
                DistanceUnit.METERS,
                DistanceUnit.INCHES,
                DistanceUnit.FEET,
                DistanceUnit.YARDS };
        final DistanceUnit[] supportedValues = includeUnitless
                ? DistanceUnit.values()
                : distanceUnitsKnown;
        return ListViewUtilities.makeLabeledSelector(
                pClientProperties,
                supportedValues,
                "Supported Distance Units",
                defaultDistanceUnit );
    }

    public static XComboBox< AngleUnit > makeAngleUnitSelector(
            final ClientProperties pClientProperties,
            final boolean applyToolkitCss,
            final AngleUnit defaultAngleUnit ) {
        return ListViewUtilities.makeLabeledSelector(
                pClientProperties,
                AngleUnit.values(),
                "Supported Angle Units",
                defaultAngleUnit );
    }

    public static XComboBox<WeightUnit> makeWeightUnitSelector(
            final ClientProperties pClientProperties,
            final boolean applyToolkitCss,
            final WeightUnit defaultWeightUnit ) {
        return ListViewUtilities.makeLabeledSelector(
                pClientProperties,
                WeightUnit.values(),
                "Supported Weight Units",
                defaultWeightUnit );
    }

    public static XComboBox<TemperatureUnit> makeTemperatureUnitSelector(
            final ClientProperties pClientProperties,
            final boolean applyToolkitCss,
            final TemperatureUnit defaultTemperatureUnit ) {
        return ListViewUtilities.makeLabeledSelector(
                pClientProperties,
                TemperatureUnit.values(),
                "Supported Temperature Units",
                defaultTemperatureUnit );
    }

    public static XComboBox<PressureUnit> makePressureUnitSelector(
            final ClientProperties pClientProperties,
            final boolean applyToolkitCss,
            final PressureUnit defaultPressureUnit ) {
        // NOTE: Atmospheres are generally an unwieldy and coarse unit in
        //  most contexts, so this default convenience method leaves it out.
        final PressureUnit[] supportedValues = {
                PressureUnit.KILOPASCALS,
                PressureUnit.PASCALS,
                PressureUnit.MILLIBARS };
        return ListViewUtilities.makeLabeledSelector(
                pClientProperties,
                supportedValues,
                "Supported Pressure Units",
                defaultPressureUnit );
    }

    // Helper method to get a standalone Frequency Editor.
    public static FrequencyEditor getFrequencyEditor(final ClientProperties clientProperties,
                                                     final String tooltipText,
                                                     final String measurementUnitString,
                                                     final double minimumValue,
                                                     final double maximumValue,
                                                     final double initialValue,
                                                     final double pPrecisionCutoffFrequencyHz,
                                                     final int pNumberOfDecimalPlaces ) {
        // Get the current value and format it as initial text.
        // TODO: Make sure this is locale-sensitive?
        final String initialText = Double.toString( initialValue );

        final FrequencyEditor frequencyEditor = new FrequencyEditor( clientProperties,
                initialText,
                tooltipText,
                minimumValue,
                maximumValue,
                initialValue,
                pPrecisionCutoffFrequencyHz,
                pNumberOfDecimalPlaces );

        frequencyEditor.setMeasurementUnitString( measurementUnitString );

        return frequencyEditor;
    }

    public static Spinner< Integer > getSplRangeSpinnerInstance(
            final ClientProperties clientProperties,
            final boolean applyToolkitCss,
            final boolean useExtendedRange ) {
        final int minimumSplRangeDb = useExtendedRange ? 3 : 42;
        final int maximumSplRangeDb = useExtendedRange ? 120 : 72;
        final int splRangeIncrementDb = 3;
        final int defaultSplRangeDb = SPL_RANGE_DB_DEFAULT;

        final String numericFormatterPattern = "##0";

        // Try to limit the size as this control can get too wide.
        final double maximumSpinnerWidth = 90.0d;

        // Return the fully initialized SPL Range Spinner.
        final String valueDescriptor = "an SPL range";
        return ControlFactory.makeIntegerSpinner(
                clientProperties,
                applyToolkitCss,
                valueDescriptor,
                minimumSplRangeDb,
                maximumSplRangeDb,
                defaultSplRangeDb,
                splRangeIncrementDb,
                false,
                numericFormatterPattern,
                " dB",
                maximumSpinnerWidth );
    }

    public static Spinner< Double > getDitheringAmountSpinnerInstance(
            final ClientProperties clientProperties,
            final boolean applyToolkitCss ) {
        // NOTE: The number formatter knows how to deal with percentages.
        final double minimumDitheringAmount = 0.0d;
        final double maximumDitheringAmount = 0.15d;
        final double ditheringAmountIncrement = 0.005d;
        final double defaultDitheringAmount = DITHERING_AMOUNT_DEFAULT;

        final String numericFormatterPattern = "##0.#";

        // Try to limit the size as this control can get too wide.
        final double maximumSpinnerWidth = 90.0d;

        // Return the fully initialized Dithering Amount Spinner.
        // TODO: Switch to a handcrafted percentile spinner (see examples).
        final String valueDescriptor = "amount to dither individual sound sources";
        return ControlFactory.makeDoubleSpinner(
                clientProperties,
                applyToolkitCss,
                valueDescriptor,
                minimumDitheringAmount,
                maximumDitheringAmount,
                defaultDitheringAmount,
                ditheringAmountIncrement,
                true,
                numericFormatterPattern,
                " %",
                maximumSpinnerWidth );
    }

    // This is a helper method to get a stand-alone Bandwidth Editor.
    public static DoubleEditor getBandwidthEditor( final ClientProperties clientProperties,
                                                   final double minimumValue,
                                                   final double maximumValue,
                                                   final double initialValue ) {
        // Get the current value and format it as initial text.
        // TODO: Make sure this is locale-sensitive?
        final String initialText = Double.toString( initialValue );

        // Declare value increment/decrement amount for up and down arrow keys.
        final double valueIncrement = 0.01d;

        final DoubleEditor bandwidthEditor = new DoubleEditor( clientProperties,
                initialText,
                null,
                true,
                0,
                2,
                0,
                4,
                minimumValue,
                maximumValue,
                0.0d,
                valueIncrement );

        return bandwidthEditor;
    }

    // Helper method to get a stand-alone Delay Editor.
    public static DoubleEditor getDelayEditor( final ClientProperties clientProperties,
                                               final String measurementUnitString,
                                               final double minimumValue,
                                               final double maximumValue,
                                               final double initialValue ) {
        // Get the current value and format it as initial text.
        // TODO: Make sure this is locale-sensitive?
        final String initialText = Double.toString( initialValue );

        // Declare value increment/decrement amount for up and down arrow keys.
        final double valueIncrementMs = 0.1d;

        final DoubleEditor delayEditor = new DoubleEditor( clientProperties,
                initialText,
                null,
                true,
                0,
                2,
                0,
                4,
                minimumValue,
                maximumValue,
                0.0d,
                valueIncrementMs );

        delayEditor.setMeasurementUnitString( measurementUnitString );

        return delayEditor;
    }

    // This is a helper method to get a standalone Gain Editor.
    public static GainEditor getGainEditor(final ClientProperties clientProperties,
                                           final String measurementUnitString,
                                           final double gainMinimumDb,
                                           final double gainMaximumDb,
                                           final double gainDefaultDb,
                                           final boolean defaultToNegativeGain ) {
        // Get the current value and format it as initial text.
        // TODO: Make sure this is locale-sensitive?
        final String initialText = Double.toString( gainDefaultDb );

        final GainEditor gainEditor = new GainEditor( clientProperties,
                initialText,
                null,
                gainMinimumDb,
                gainMaximumDb,
                gainDefaultDb,
                defaultToNegativeGain );

        gainEditor.setMeasurementUnitString( measurementUnitString );

        return gainEditor;
    }
}

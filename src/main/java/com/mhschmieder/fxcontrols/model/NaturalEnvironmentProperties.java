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
package com.mhschmieder.fxcontrols.model;

import com.mhschmieder.fxgraphics.beans.BeanFactory;
import com.mhschmieder.jphysics.PhysicsConstants;
import com.mhschmieder.jphysics.measure.HumidityUnit;
import com.mhschmieder.jphysics.measure.PressureUnit;
import com.mhschmieder.jphysics.measure.TemperatureUnit;
import com.mhschmieder.jphysics.measure.UnitConversion;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Objects;

public final class NaturalEnvironmentProperties {

    // Declare default constants, where appropriate, for all fields.
    public static final double    TEMPERATURE_K_DEFAULT           =
                                                        PhysicsConstants.ROOM_TEMPERATURE_K;
    public static final double    HUMIDITY_RELATIVE_DEFAULT       = 50d;
    public static final double    PRESSURE_PA_DEFAULT             =
                                                      PhysicsConstants.PRESSURE_REFERENCE_PA;
    public static final boolean   AIR_ATTENUATION_APPLIED_DEFAULT = true;

    // Natural Environment is stored in standard scientific units, even though
    // it will most likely be used in domain units associate with air on earth,
    // where the natural limits are well established and inform different units.
    private final DoubleProperty  temperatureK;
    private final DoubleProperty  humidityRelative;
    private final DoubleProperty  pressurePa;
    private final BooleanProperty airAttenuationApplied;

    // NOTE: This field has to follow JavaFX Property Beans conventions.
    private BooleanBinding         naturalEnvironmentChanged;

    /**
     * This is the default constructor; it sets all instance variables to
     * default values, initializing anything that requires memory allocation.
     */
    public NaturalEnvironmentProperties() {
        this( TEMPERATURE_K_DEFAULT,
              HUMIDITY_RELATIVE_DEFAULT,
              PRESSURE_PA_DEFAULT,
              AIR_ATTENUATION_APPLIED_DEFAULT );
    }

    /**
     * This is the fully qualified constructor.
     *
     * @param pTemperatureK
     *            The temperature to use, in degrees Kelvin
     * @param pHumidityRelative
     *            The Relative Humidity to use (percent)
     * @param pPressurePa
     *            The Pressure to use, in pascals
     * @param pAirAttenuationApplied
     *            {@code true} if Air Attenuation should be applied by
     *            downstream consumers of this environmental variable wrapper
     */
    public NaturalEnvironmentProperties(final double pTemperatureK,
                                        final double pHumidityRelative,
                                        final double pPressurePa,
                                        final boolean pAirAttenuationApplied ) {
        temperatureK = new SimpleDoubleProperty( pTemperatureK );
        humidityRelative = new SimpleDoubleProperty( pHumidityRelative );
        pressurePa = new SimpleDoubleProperty( pPressurePa );
        airAttenuationApplied = new SimpleBooleanProperty( pAirAttenuationApplied );

        // Bind all of the properties to the associated dirty flag.
        // NOTE: This is done during initialization, as it is best to make
        //  singleton objects and just update their values vs. reconstructing.
        naturalEnvironmentChanged = BeanFactory.makeBooleanBinding(
            temperatureKProperty(),
            humidityRelativeProperty(),
            pressurePaProperty(),
            airAttenuationAppliedProperty() );
    }

    /**
     * This is the copy constructor, and is offered in place of clone() to
     * guarantee that the source object is never modified by the new target
     * object created here.
     *
     * @param pNaturalEnvironmentProperties
     *            The Natural Environment reference for the copy
     */
    public NaturalEnvironmentProperties(final NaturalEnvironmentProperties pNaturalEnvironmentProperties) {
        this( pNaturalEnvironmentProperties.getTemperatureK(),
              pNaturalEnvironmentProperties.getHumidityRelative(),
              pNaturalEnvironmentProperties.getPressurePa(),
              pNaturalEnvironmentProperties.isAirAttenuationApplied() );
    }

    // NOTE: Cloning is disabled as it is dangerous; use the copy constructor
    //  instead.
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public boolean equals( final Object other ) {
        if ( this == other ) {
            return true;
        }
        if ( ( other == null ) || ( getClass() != other.getClass() ) ) {
            return false;
        }
        final NaturalEnvironmentProperties otherNaturalEnvironmentProperties = (NaturalEnvironmentProperties) other;
        return Objects.equals( temperatureK, otherNaturalEnvironmentProperties.temperatureK )
                && Objects.equals( humidityRelative, otherNaturalEnvironmentProperties.humidityRelative )
                && Objects.equals( pressurePa, otherNaturalEnvironmentProperties.pressurePa )
                && Objects.equals( airAttenuationApplied,
                                   otherNaturalEnvironmentProperties.airAttenuationApplied );
    }

    @Override
    public int hashCode() {
        return Objects.hash( temperatureK, humidityRelative, pressurePa, airAttenuationApplied );
    }

    // Default pseudo-constructor.
    public void reset() {
        setNaturalEnvironment( TEMPERATURE_K_DEFAULT,
                               HUMIDITY_RELATIVE_DEFAULT,
                               PRESSURE_PA_DEFAULT,
                               AIR_ATTENUATION_APPLIED_DEFAULT );
    }

    /**
     * Fully qualified pseudo-constructor.
     *
     * @param pTemperatureK
     *            The temperature to use, in degrees Kelvin
     * @param pHumidityRelative
     *            The Relative Humidity to use (percent)
     * @param pPressurePa
     *            The Pressure to use, in pascals
     * @param pAirAttenuationApplied
     *            {@code true} if Air Attenuation should be applied by
     *            downstream consumers of this environmental variable wrapper
     */
    public void setNaturalEnvironment( final double pTemperatureK,
                                       final double pHumidityRelative,
                                       final double pPressurePa,
                                       final boolean pAirAttenuationApplied ) {
        setTemperatureK( pTemperatureK );
        setHumidityRelative( pHumidityRelative );
        setPressurePa( pPressurePa );
        setAirAttenuationApplied( pAirAttenuationApplied );
    }

    /**
     * Copy pseudo-constructor.
     *
     * @param pNaturalEnvironmentProperties
     *            The Natural Environment reference for setting the values
     */
    public void setNaturalEnvironment( final NaturalEnvironmentProperties pNaturalEnvironmentProperties) {
        setNaturalEnvironment( pNaturalEnvironmentProperties.getTemperatureK(),
                               pNaturalEnvironmentProperties.getHumidityRelative(),
                               pNaturalEnvironmentProperties.getPressurePa(),
                               pNaturalEnvironmentProperties.isAirAttenuationApplied() );
    }

    public DoubleProperty temperatureKProperty() {
        return temperatureK;
    }

    public double getTemperature( final TemperatureUnit pTemperatureUnit ) {
        double temperature = Double.NaN;
        
        switch ( pTemperatureUnit ) {
        case KELVIN:
            temperature = getTemperatureK();
            break;
        case CELSIUS:
            temperature = getTemperatureC();
            break;
        case FAHRENHEIT:
            temperature = getTemperatureF();
            break;
        default:
            final String errMessage = "Unexpected TemperatureUnit " //$NON-NLS-1$
                    + pTemperatureUnit;
            throw new IllegalArgumentException( errMessage );
        }
        
        return temperature;
    }

    public double getTemperatureK() {
        return temperatureK.get();
    }

    public double getTemperatureC() {
        return UnitConversion.kelvinToCelsius( temperatureK.get() );
    }

    public double getTemperatureF() {
        return UnitConversion.kelvinToFahrenheit( temperatureK.get() );
    }

    public void setTemperature( final double pTemperature,
                                final TemperatureUnit pTemperatureUnit ) {
        switch ( pTemperatureUnit ) {
        case KELVIN:
            setTemperatureK( pTemperature );
            break;
        case CELSIUS:
            setTemperatureC( pTemperature );
            break;
        case FAHRENHEIT:
            setTemperatureF( pTemperature );
            break;
        default:
            final String errMessage = "Unexpected TemperatureUnit " //$NON-NLS-1$
                    + pTemperatureUnit;
            System.err.println( errMessage );
        }
    }

    public void setTemperatureK( final double pTemperatureK ) {
        temperatureK.set( pTemperatureK );
    }

    public void setTemperatureC( final double pTemperatureC ) {
        temperatureK.set( UnitConversion.celsiusToKelvin( pTemperatureC ) );
    }

    public void setTemperatureF( final double pTemperatureF ) {
        temperatureK.set( UnitConversion.fahrenheitToKelvin( pTemperatureF ) );
    }
    
    public DoubleProperty humidityRelativeProperty() {
        return humidityRelative;
    }

    public double getHumidityRelative() {
        return humidityRelative.get();
    }

    public void setHumidityRelative( final double pHumidity, 
                                     final HumidityUnit pHumidityUnit ) {
        // TODO: Implement molar humidity, which requires adding a conversion
        // method to UnitsConversion based on the C++ Physics Library code.
        if ( HumidityUnit.RELATIVE.equals( pHumidityUnit ) ) {
            setHumidityRelative( pHumidity );
        }
    }

    public void setHumidityRelative( final double pHumidityRelative ) {
        humidityRelative.set( pHumidityRelative );
    }

    public DoubleProperty pressurePaProperty() {
        return pressurePa;
    }

    public double getPressure( final PressureUnit pPressureUnit ) {
        double pressure = Double.NaN;
        
        switch ( pPressureUnit ) {
        case KILOPASCALS:
            pressure = getPressureKpa();
            break;
        case PASCALS:
            pressure = getPressurePa();
            break;
        case MILLIBARS:
            pressure = getPressureMb();
            break;
        case ATMOSPHERES:
            pressure = getPressureAtm();
            break;
        default:
            final String errMessage = "Unexpected PressureUnit " //$NON-NLS-1$
                    + pPressureUnit;
            throw new IllegalArgumentException( errMessage );
        }
        
        return pressure;
    }

    public double getPressureKpa() {
        return UnitConversion.pascalsToKilopascals( pressurePa.get() );
    }

    public double getPressurePa() {
        return pressurePa.get();
    }

    public double getPressureMb() {
        return UnitConversion.pascalsToMillibars( pressurePa.get() );
    }

    public double getPressureAtm() {
        return UnitConversion.pascalsToAtmospheres( pressurePa.get() );
    }

    public void setPressure( final double pPressure, final PressureUnit pPressureUnit ) {
        switch ( pPressureUnit ) {
        case KILOPASCALS:
            setPressureKpa( pPressure );
            break;
        case PASCALS:
            setPressurePa( pPressure );
            break;
        case MILLIBARS:
            setPressureMb( pPressure );
            break;
        case ATMOSPHERES:
            setPressureAtm( pPressure );
            break;
        default:
            final String errMessage = "Unexpected PressureUnit " //$NON-NLS-1$
                    + pPressureUnit;
            System.err.println( errMessage );
        }
    }

    public void setPressureKpa( final double pPressureKpa ) {
        pressurePa.set( UnitConversion.kilopascalsToPascals( pPressureKpa ) );
    }

    public void setPressurePa( final double pPressurePa ) {
        pressurePa.set( pPressurePa );
    }

    public void setPressureMb( final double pPressureMb ) {
        pressurePa.set( UnitConversion.millibarsToPascals( pPressureMb ) );
    }

    public void setPressureAtm( final double pPressureAtm ) {
        pressurePa.set( UnitConversion.atmospheresToPascals( pPressureAtm ) );
    }

    public BooleanProperty airAttenuationAppliedProperty() {
        return airAttenuationApplied;
    }

    public boolean isAirAttenuationApplied() {
        return airAttenuationApplied.get();
    }

    public void setAirAttenuationApplied( final boolean pAirAttenuationApplied ) {
        airAttenuationApplied.set( pAirAttenuationApplied );
    }

    public BooleanBinding naturalEnvironmentChangedProperty() {
        return naturalEnvironmentChanged;
    }
    
    public boolean isNaturalEnvironmentChanged() {
        return naturalEnvironmentChanged.get();
    }
}

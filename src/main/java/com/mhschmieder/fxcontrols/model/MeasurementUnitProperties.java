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

import com.mhschmieder.jphysics.measure.AngleUnit;
import com.mhschmieder.jphysics.measure.DistanceUnit;
import com.mhschmieder.jphysics.measure.PressureUnit;
import com.mhschmieder.jphysics.measure.TemperatureUnit;
import com.mhschmieder.jphysics.measure.WeightUnit;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This is a container for all the individual Measurement Units that are
 * likely needed by most applications that partially or fully model the real
 * physical world. Distance Unit is included as it is also used in Physics.
 */
public final class MeasurementUnitProperties {

    // Declare a static instance of an explicit default metric measurement
    // (MKS).
    public static final MeasurementUnitProperties MKS =
                                                        new MeasurementUnitProperties( DistanceUnit.METERS,
                                                                              AngleUnit.RADIANS,
                                                                              WeightUnit.KILOGRAMS,
                                                                              TemperatureUnit.KELVIN,
                                                                              PressureUnit.PASCALS );

    private final ObjectProperty< DistanceUnit >    distanceUnit;
    private final ObjectProperty< AngleUnit >       angleUnit;
    private final ObjectProperty< WeightUnit >      weightUnit;
    private final ObjectProperty< TemperatureUnit > temperatureUnit;
    private final ObjectProperty< PressureUnit >    pressureUnit;

    // Separately, we must cache the preferred default units.
    private final DistanceUnit                      distanceUnitDefault;
    private final AngleUnit                         angleUnitDefault;
    private final WeightUnit                        weightUnitDefault;
    private final TemperatureUnit                   temperatureUnitDefault;
    private final PressureUnit                      pressureUnitDefault;

    // NOTE: These fields have to follow JavaFX Property Beans conventions.
    private BooleanBinding                          distanceUnitChanged;
    private BooleanBinding                          angleUnitChanged;
    private BooleanBinding                          weightUnitChanged;
    private BooleanBinding                          temperatureUnitChanged;
    private BooleanBinding                          pressureUnitChanged;

    /**
     * This is the default constructor; it sets all instance variables to
     * default values in the standard standard MKS scientific unit system.
     */
    public MeasurementUnitProperties() {
        this( DistanceUnit.defaultValue(),
              AngleUnit.defaultValue(),
              WeightUnit.defaultValue(),
              TemperatureUnit.defaultValue(),
              PressureUnit.defaultValue() );
    }

    /**
     * This is the fully qualified constructor.
     *
     * @param pDistanceUnit
     *            The Distance Unit to use
     * @param pAngleUnit
     *            The Angle Unit to use
     * @param pWeightUnit
     *            The Weight Unit to use
     * @param pTemperatureUnit
     *            The Temperature Unit to use
     * @param pPressureUnit
     *            The Pressure Unit to use
     */
    public MeasurementUnitProperties(final DistanceUnit pDistanceUnit,
                                     final AngleUnit pAngleUnit,
                                     final WeightUnit pWeightUnit,
                                     final TemperatureUnit pTemperatureUnit,
                                     final PressureUnit pPressureUnit ) {
        distanceUnit = new SimpleObjectProperty<>( pDistanceUnit );
        angleUnit = new SimpleObjectProperty<>( pAngleUnit );
        weightUnit = new SimpleObjectProperty<>( pWeightUnit );
        temperatureUnit = new SimpleObjectProperty<>( pTemperatureUnit );
        pressureUnit = new SimpleObjectProperty<>( pPressureUnit );

        distanceUnitDefault = pDistanceUnit;
        angleUnitDefault = pAngleUnit;
        weightUnitDefault = pWeightUnit;
        temperatureUnitDefault = pTemperatureUnit;
        pressureUnitDefault = pPressureUnit;

        // Bind all of the properties to the associated dirty flag.
        // NOTE: This is done during initialization, as it is best to make
        //  singleton objects and just update their values vs. reconstructing.
        makeBooleanBindings();
    }

    /**
     * This is the copy constructor, and is offered in place of clone() to
     * guarantee that the source object is never modified by the new target
     * object created here.
     *
     * @param pMeasurementUnitProperties
     *            The Measurement Units reference for the copy
     */
    public MeasurementUnitProperties(final MeasurementUnitProperties pMeasurementUnitProperties) {
        this( pMeasurementUnitProperties.getDistanceUnit(),
              pMeasurementUnitProperties.getAngleUnit(),
              pMeasurementUnitProperties.getWeightUnit(),
              pMeasurementUnitProperties.getTemperatureUnit(),
              pMeasurementUnitProperties.getPressureUnit() );
    }

    public void makeBooleanBindings() {
        // Establish the dirty flag criteria as specific assignable value
        // change.
        distanceUnitChanged = new BooleanBinding() {
            {
                // When the specific assignable value of interest changes, the
                // distanceUnitChanged Boolean Binding is invalidated and
                // notifies its listeners.
                super.bind( distanceUnitProperty() );
            }

            // Just auto-clear the invalidation by overriding with a status that
            // is affirmative of a change having triggered the call.
            @Override
            protected boolean computeValue() {
                return true;
            }
        };
        angleUnitChanged = new BooleanBinding() {
            {
                // When the specific assignable value of interest changes, the
                // angleUnitChanged Boolean Binding is invalidated and notifies
                // its listeners.
                super.bind( angleUnitProperty() );
            }

            // Just auto-clear the invalidation by overriding with a status that
            // is affirmative of a change having triggered the call.
            @Override
            protected boolean computeValue() {
                return true;
            }
        };
        weightUnitChanged = new BooleanBinding() {
            {
                // When the specific assignable value of interest changes, the
                // weightUnitChanged Boolean Binding is invalidated and notifies
                // its listeners.
                super.bind( weightUnitProperty() );
            }

            // Just auto-clear the invalidation by overriding with a status that
            // is affirmative of a change having triggered the call.
            @Override
            protected boolean computeValue() {
                return true;
            }
        };
        temperatureUnitChanged = new BooleanBinding() {
            {
                // When the specific assignable value of interest changes, the
                // temperatureUnitChanged Boolean Binding is invalidated and
                // notifies its listeners.
                super.bind( temperatureUnitProperty() );
            }

            // Just auto-clear the invalidation by overriding with a status that
            // is affirmative of a change having triggered the call.
            @Override
            protected boolean computeValue() {
                return true;
            }
        };
        pressureUnitChanged = new BooleanBinding() {
            {
                // When the specific assignable value of interest changes, the
                // pressureUnitChanged Boolean Binding is invalidated and
                // notifies its listeners.
                super.bind( pressureUnitProperty() );
            }

            // Just auto-clear the invalidation by overriding with a status that
            // is affirmative of a change having triggered the call.
            @Override
            protected boolean computeValue() {
                return true;
            }
        };
    }

    // NOTE: Cloning is disabled as it is dangerous; use the copy constructor
    // instead.
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( final Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !( obj instanceof MeasurementUnitProperties) ) {
            return false;
        }
        final MeasurementUnitProperties other = (MeasurementUnitProperties) obj;
        if ( distanceUnit == null ) {
            if ( other.distanceUnit != null ) {
                return false;
            }
        }
        else if ( !distanceUnit.equals( other.distanceUnit ) ) {
            return false;
        }
        if ( angleUnit == null ) {
            if ( other.angleUnit != null ) {
                return false;
            }
        }
        else if ( !angleUnit.equals( other.angleUnit ) ) {
            return false;
        }
        if ( weightUnit == null ) {
            if ( other.weightUnit != null ) {
                return false;
            }
        }
        else if ( !weightUnit.equals( other.weightUnit ) ) {
            return false;
        }
        if ( temperatureUnit == null ) {
            if ( other.temperatureUnit != null ) {
                return false;
            }
        }
        else if ( !temperatureUnit.equals( other.temperatureUnit ) ) {
            return false;
        }
        if ( pressureUnit == null ) {
            if ( other.pressureUnit != null ) {
                return false;
            }
        }
        else if ( !pressureUnit.equals( other.pressureUnit ) ) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = ( prime * result ) + ( ( distanceUnit == null ) ? 0 : distanceUnit.hashCode() );
        result = ( prime * result ) + ( ( angleUnit == null ) ? 0 : angleUnit.hashCode() );
        result = ( prime * result ) + ( ( weightUnit == null ) ? 0 : weightUnit.hashCode() );
        result = ( prime * result )
                + ( ( temperatureUnit == null ) ? 0 : temperatureUnit.hashCode() );
        result = ( prime * result ) + ( ( pressureUnit == null ) ? 0 : pressureUnit.hashCode() );
        return result;
    }

    /**
     * Default pseudo-constructor.
     */
    public void reset() {
        setMeasurementUnits( distanceUnitDefault,
                             angleUnitDefault,
                             weightUnitDefault,
                             temperatureUnitDefault,
                             pressureUnitDefault );
    }

    /**
     * Fully qualified pseudo-constructor. Notifies listeners once instead of
     * for each changed property. Used by reset() and updatePreferences().
     *
     * @param pDistanceUnit
     *            The Distance Unit to use
     * @param pAngleUnit
     *            The Angle Unit to use
     * @param pWeightUnit
     *            The Weight Unit to use
     * @param pTemperatureUnit
     *            The Temperature Unit to use
     * @param pPressureUnit
     *            The Pressure Unit to use
     */
    public void setMeasurementUnits( final DistanceUnit pDistanceUnit,
                                     final AngleUnit pAngleUnit,
                                     final WeightUnit pWeightUnit,
                                     final TemperatureUnit pTemperatureUnit,
                                     final PressureUnit pPressureUnit ) {
        setDistanceUnit( pDistanceUnit );
        setAngleUnit( pAngleUnit );
        setWeightUnit( pWeightUnit );
        setTemperatureUnit( pTemperatureUnit );
        setPressureUnit( pPressureUnit );
    }

    /**
     * Copy pseudo-constructor. Unused at this time (201602).
     *
     * @param pMeasurementUnitProperties
     *            The Measurement Units to use to set this object
     */
    protected void setMeasurementUnits( final MeasurementUnitProperties pMeasurementUnitProperties) {
        setMeasurementUnits( pMeasurementUnitProperties.getDistanceUnit(),
                             pMeasurementUnitProperties.getAngleUnit(),
                             pMeasurementUnitProperties.getWeightUnit(),
                             pMeasurementUnitProperties.getTemperatureUnit(),
                             pMeasurementUnitProperties.getPressureUnit() );
    }

    public ObjectProperty< DistanceUnit > distanceUnitProperty() {
        return distanceUnit;
    }

    public DistanceUnit getDistanceUnit() {
        return distanceUnit.get();
    }

    public void setDistanceUnit( final DistanceUnit pDistanceUnit ) {
        distanceUnit.set( pDistanceUnit );
    }

    public ObjectProperty< AngleUnit > angleUnitProperty() {
        return angleUnit;
    }

    public AngleUnit getAngleUnit() {
        return angleUnit.get();
    }

    public void setAngleUnit( final AngleUnit pAngleUnit ) {
        angleUnit.set( pAngleUnit );
    }

    public ObjectProperty< WeightUnit > weightUnitProperty() {
        return weightUnit;
    }

    public WeightUnit getWeightUnit() {
        return weightUnit.get();
    }

    public void setWeightUnit( final WeightUnit pWeightUnit ) {
        weightUnit.set( pWeightUnit );
    }

    public TemperatureUnit getTemperatureUnit() {
        return temperatureUnit.get();
    }

    public ObjectProperty< TemperatureUnit > temperatureUnitProperty() {
        return temperatureUnit;
    }

    public void setTemperatureUnit( final TemperatureUnit pTemperatureUnit ) {
        temperatureUnit.set( pTemperatureUnit );
    }

    public ObjectProperty< PressureUnit > pressureUnitProperty() {
        return pressureUnit;
    }

    public PressureUnit getPressureUnit() {
        return pressureUnit.get();
    }

    public void setPressureUnit( final PressureUnit pPressureUnit ) {
        pressureUnit.set( pPressureUnit );
    }

    public BooleanBinding distanceUnitChangedProperty() {
        return distanceUnitChanged;
    }
    
    public boolean isDistanceUnitChanged() {
        return distanceUnitChanged.get();
    }
    
    public BooleanBinding angleUnitChangedProperty() {
        return angleUnitChanged;
    }
    
    public boolean isAngleUnitChanged() {
        return angleUnitChanged.get();
    }
    
    public BooleanBinding weightUnitChangedProperty() {
        return weightUnitChanged;
    }
    
    public boolean isWeightUnitChanged() {
        return weightUnitChanged.get();
    }
    
    public BooleanBinding temperatureUnitChangedProperty() {
        return temperatureUnitChanged;
    }
    
    public boolean isTemperatureUnitChanged() {
        return temperatureUnitChanged.get();
    }
    
    public BooleanBinding pressureUnitChangedProperty() {
        return pressureUnitChanged;
    }
    
    public boolean isPressureUnitChanged() {
        return pressureUnitChanged.get();
    }
}

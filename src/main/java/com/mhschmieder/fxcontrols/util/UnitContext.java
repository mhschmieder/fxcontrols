/*
 * MIT License
 *
 * Copyright (c) 2026 Mark Schmieder. All rights reserved.
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
package com.mhschmieder.fxcontrols.util;

import com.mhschmieder.jcommons.lang.Abbreviated;
import com.mhschmieder.jcommons.lang.Labeled;
import com.mhschmieder.jphysics.measure.UnitConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

/**
 * Describes the canonical and displayed units for a numeric value without
 * exposing a concrete unit enum to the consuming control.
 *
 * @param <U> The unit enum type
 */
public final class UnitContext< U extends Enum< U > & Labeled< U > & Abbreviated< U > > {

    private final U dataModelUnit;
    private final ObjectProperty< U > displayUnit;
    private final UnitConverter< U > unitConverter;

    public UnitContext(final U pDataModelUnit,
                       final U pDisplayUnit,
                       final UnitConverter< U > pUnitConverter ) {
        this( pDataModelUnit,
              new SimpleObjectProperty<>( pDisplayUnit ),
              pUnitConverter );
    }

    public UnitContext(final U pDataModelUnit,
                       final ObjectProperty< U > pDisplayUnit,
                       final UnitConverter< U > pUnitConverter ) {
        dataModelUnit = Objects.requireNonNull( pDataModelUnit );
        displayUnit = Objects.requireNonNull( pDisplayUnit );
        unitConverter = Objects.requireNonNull( pUnitConverter );
    }

    public U getDataModelUnit() {
        return dataModelUnit;
    }

    public ObjectProperty< U > displayUnitProperty() {
        return displayUnit;
    }

    public U getDisplayUnit() {
        return displayUnit.get();
    }

    public void setDisplayUnit( final U pDisplayUnit ) {
        displayUnit.set( Objects.requireNonNull( pDisplayUnit ) );
    }

    public String getDisplayUnitText() {
        final U unit = getDisplayUnit();
        return ( unit != null ) ? unit.abbreviation() : "";
    }

    public double toDisplayValue( final double dataModelValue ) {
        return unitConverter.convert( dataModelValue,
                                      dataModelUnit,
                                      getDisplayUnit() );
    }

    public double toDataModelValue( final double displayValue ) {
        return unitConverter.convert( displayValue,
                                      getDisplayUnit(),
                                      dataModelUnit );
    }
}

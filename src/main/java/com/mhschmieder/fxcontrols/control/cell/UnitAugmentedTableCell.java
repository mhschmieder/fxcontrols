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
package com.mhschmieder.fxcontrols.control.cell;

import com.mhschmieder.fxcontrols.util.UnitContext;
import com.mhschmieder.jcommons.lang.Abbreviated;
import com.mhschmieder.jcommons.lang.Labeled;
import com.mhschmieder.jcommons.util.ClientProperties;

/**
 * A reusable double table cell that converts between canonical model units and
 * an observable display unit and augments the value with the supplied units.
 *
 * @param <RT> The table row type
 * @param <U>  The unit enum type
 */
public final class UnitAugmentedTableCell< RT,
                                                 U extends Enum< U > & Labeled< U > & Abbreviated< U > >
        extends ExtendedDoubleEditorTableCell< RT, U > {

    private final UnitContext< U > unitContext;

    public UnitAugmentedTableCell(final boolean pAllowedToBeBlank,
                                  final UnitContext< U > pUnitContext,
                                  final ClientProperties pClientProperties ) {
        super( pAllowedToBeBlank,
               pUnitContext.getDataModelUnit(),
               pClientProperties );

        unitContext = pUnitContext;
        setDisplayUnitProperty( unitContext.displayUnitProperty() );
    }

    @Override
    public Double getDisplayValue( final Double pDataModelValue ) {
        return ( pDataModelValue != null )
               ? unitContext.toDisplayValue( pDataModelValue )
               : Double.NaN;
    }

    @Override
    public Double getDataModelValue( final Double pDisplayValue ) {
        return ( pDisplayValue != null )
               ? unitContext.toDataModelValue( pDisplayValue )
               : Double.NaN;
    }
}

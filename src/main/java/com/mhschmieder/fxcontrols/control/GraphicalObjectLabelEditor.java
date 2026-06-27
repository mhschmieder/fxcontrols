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

import com.mhschmieder.fxgraphics.collections.GraphicalObjectCollection;
import com.mhschmieder.fxgraphics.geometry.GraphicalObject;
import com.mhschmieder.jcommons.text.NumberFormatUtilities;
import com.mhschmieder.jcommons.util.ClientProperties;

import java.text.NumberFormat;

/**
 * This is a specialized label textField for Graphical Objects, that guarantees
 * name uniqueness, among other special contracts.
 */
public final class GraphicalObjectLabelEditor extends TextEditor {

    // Declare a default base label for new Graphical Objects.
    private final String                                             _graphicalObjectLabelDefault;

    // Declare a collection of Graphical Objects.
    protected GraphicalObjectCollection< ? extends GraphicalObject > _graphicalObjectCollection;

    // Number format cache used for locale-specific number formatting of
    // uniquefier appendices.
    public NumberFormat                                              _uniquefierNumberFormat;

    public GraphicalObjectLabelEditor( final ClientProperties pClientProperties,
                                       final String graphicalObjectLabelDefault,
                                       final GraphicalObjectCollection< ? extends GraphicalObject > graphicalObjectCollection ) {
        this( graphicalObjectCollection.getNewLabelDefault( graphicalObjectLabelDefault ),
              null,
              pClientProperties,
              graphicalObjectLabelDefault,
              graphicalObjectCollection );
    }

    public GraphicalObjectLabelEditor( final String initialText,
                                       final ClientProperties pClientProperties,
                                       final String graphicalObjectLabelDefault,
                                       final GraphicalObjectCollection< ? extends GraphicalObject > graphicalObjectCollection ) {
        this( initialText,
              null,
              pClientProperties,
              graphicalObjectLabelDefault,
              graphicalObjectCollection );
    }

    public GraphicalObjectLabelEditor( final String initialText,
                                       final String tooltipText,
                                       final ClientProperties pClientProperties,
                                       final String graphicalObjectLabelDefault,
                                       final GraphicalObjectCollection< ? extends GraphicalObject > graphicalObjectCollection ) {
        // Always call the superclass constructor first!
        super( initialText, tooltipText, true, true, pClientProperties );

        _graphicalObjectLabelDefault = graphicalObjectLabelDefault;
        _graphicalObjectCollection = graphicalObjectCollection;

        try {
            initEditor();
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void initEditor() {
        _uniquefierNumberFormat = NumberFormatUtilities
                .getUniquefierNumberFormat( clientProperties.locale );
    }

    @Override
    public String getAdjustedValue( final String text ) {
        // First, get the potentially trimmed version of the current input.
        final String trimmedValue = super.getAdjustedValue( text );

        // Make sure we use the most recent cached value as the default.
        final String currentValue = getValue();

        // Forward this method to the specialized label uniquefier.
        final String adjustedValue = getUniqueGraphicalObjectLabel( trimmedValue, currentValue );

        return adjustedValue;
    }

    public String getNewGraphicalObjectLabelDefault() {
        return _graphicalObjectCollection.getNewLabelDefault( _graphicalObjectLabelDefault );
    }

    // Get a unique Graphical Object Label from the candidate label.
    // NOTE: The default label is only used when the edited label is blank.
    public String getUniqueGraphicalObjectLabel( final String graphicalObjectLabelCandidate ) {
        return getUniqueGraphicalObjectLabel( graphicalObjectLabelCandidate, null );
    }

    // Get a unique Graphical Object Label from the candidate label.
    // NOTE: The default label is only used when the edited label is blank.
    public String getUniqueGraphicalObjectLabel( final String graphicalObjectLabelCandidate,
                                                 final String graphicalObjectLabelCurrent ) {
        final String graphicalObjectLabelDefault = getNewGraphicalObjectLabelDefault();
        return _graphicalObjectCollection.getUniqueLabel( graphicalObjectLabelCandidate,
                                                          graphicalObjectLabelDefault,
                                                          graphicalObjectLabelCurrent,
                                                          _uniquefierNumberFormat );
    }

    // Find out if the candidate label is unique.
    public boolean isGraphicalObjectLabelUnique( final String graphicalObjectLabelCandidate ) {
        return _graphicalObjectCollection.isLabelUnique( graphicalObjectLabelCandidate );
    }

}

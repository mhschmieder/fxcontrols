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
package com.mhschmieder.fxcontrols.util;

import com.mhschmieder.fxcontrols.model.SurfaceProperties;
import com.mhschmieder.jcommons.text.TextUtilities;
import javafx.collections.ObservableList;

import java.text.NumberFormat;

/**
 * This is a manager class for Surface Names, to guarantee their uniqueness.
 */
public final class SurfacePropertiesNameManager {

    public static String getSurfaceNameDefault( final int surfaceNumber ) {
        return "Surface " + surfaceNumber;
    }

    public static String getSurfaceNameDefault(
            final SurfaceProperties surfaceProperties ) {
        final int surfaceNumber = surfaceProperties.getSurfaceNumber();
        return getSurfaceNameDefault( surfaceNumber );
    }

    // Get a unique Surface Name from the candidate name.
    public static String getUniqueSurfaceName(
            final ObservableList< SurfaceProperties > surfacePropertiesList,
            final SurfaceProperties surfacePropertiesToExclude,
            final String surfaceNameCandidate,
            final int uniquefierNumber,
            final NumberFormat uniquefierNumberFormat ) {
        // Recursively search for (and enforce) name-uniqueness of the supplied
        // Surface Name candidate and uniquefier number.
        final String uniquefierAppendix = TextUtilities.getUniquefierAppendix(
                uniquefierNumber,
                uniquefierNumberFormat );
        String uniqueSurfaceName = surfaceNameCandidate + uniquefierAppendix;
        if ( !isSurfaceNameUnique( surfacePropertiesList,
                                   surfacePropertiesToExclude,
                                   uniqueSurfaceName ) ) {
            // Recursively guarantee the appendix-adjusted name is also unique,
            // using a hopefully-unique number as the appendix.
            uniqueSurfaceName = getUniqueSurfaceName(
                    surfacePropertiesList,
                    surfacePropertiesToExclude,
                    surfaceNameCandidate,
                    uniquefierNumber + 1,
                    uniquefierNumberFormat );
        }

        return uniqueSurfaceName;
    }

    public static String getUniqueSurfaceName(
            final ObservableList< SurfaceProperties > surfacePropertiesList,
            final SurfaceProperties surfacePropertiesToExclude,
            final String surfaceNameCandidate,
            final NumberFormat uniquefierNumberFormat ) {
        final String surfaceNameDefault = getSurfaceNameDefault(
                surfacePropertiesToExclude );
        return getUniqueSurfaceName(
                surfacePropertiesList,
                surfacePropertiesToExclude,
                surfaceNameCandidate,
                surfaceNameDefault,
                uniquefierNumberFormat );
    }

    // Get a unique Surface Name from the candidate name.
    public static String getUniqueSurfaceName(
            final ObservableList< SurfaceProperties > surfacePropertiesList,
            final SurfaceProperties surfacePropertiesToExclude,
            final String surfaceNameCandidate,
            final String surfaceNameDefault,
            final NumberFormat uniquefierNumberFormat ) {
        // Try to use the specified Surface Name if it exists and is non-empty;
        // otherwise apply the pre-assigned default name for the current
        // Surface, leaving unadorned if possible.
        final int uniquefierNumber = 0;
        final String surfaceNameCandidateAdjusted
                = ( surfaceNameCandidate == null )
                || surfaceNameCandidate.trim().isEmpty()
                    ? surfaceNameDefault
                    : surfaceNameCandidate;
        return getUniqueSurfaceName(
                surfacePropertiesList,
                surfacePropertiesToExclude,
                surfaceNameCandidateAdjusted,
                uniquefierNumber,
                uniquefierNumberFormat );
    }

    public static boolean isSurfaceNameUnique(
            final ObservableList< SurfaceProperties > surfacePropertiesList,
            final int surfaceToExcludeIndex,
            final String surfaceNameCandidate ) {
        // Iterate through all the Surfaces, to see if any use the proposed
        // Surface Name.
        boolean surfaceNameUnique = true;

        for ( int surfaceIndex = 0;
              surfaceIndex < surfacePropertiesList.size();
              surfaceIndex++ ) {
            // Skip the Surface if at the reference Surface's index.
            if ( surfaceIndex == surfaceToExcludeIndex ) {
                continue;
            }

            // Check for a naming collision of Surface Names.
            final SurfaceProperties surfaceProperties
                    = surfacePropertiesList.get( surfaceIndex );
            final String surfaceName = surfaceProperties.getSurfaceName();
            if ( surfaceName.equals( surfaceNameCandidate ) ) {
                surfaceNameUnique = false;
                break;
            }
        }

        return surfaceNameUnique;
    }

    public static boolean isSurfaceNameUnique(
            final ObservableList< SurfaceProperties > surfacePropertiesList,
            final SurfaceProperties surfacePropertiesToExclude,
            final String surfaceNameCandidate ) {
        final int surfaceToExcludeIndex
                = surfacePropertiesToExclude.getSurfaceNumber() - 1;
        return isSurfaceNameUnique(
                surfacePropertiesList,
                surfaceToExcludeIndex,
                surfaceNameCandidate );
    }
}

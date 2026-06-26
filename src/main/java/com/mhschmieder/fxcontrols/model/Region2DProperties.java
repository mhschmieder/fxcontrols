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
 * This file is part of the fxcadcontrols Library
 *
 * You should have received a copy of the MIT License along with the
 * fxcadcontrols Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxcadcontrols
 */
package com.mhschmieder.fxcontrols.model;

import com.mhschmieder.fxcadcontrols.util.SurfacePropertiesNameManager;
import com.mhschmieder.fxgraphics.geometry.Region2D;
import com.mhschmieder.fxgraphics.geometry.Surface;
import com.mhschmieder.fxgraphics.geometry.SurfaceMaterial;
import com.mhschmieder.fxgraphics.beans.BeanFactory;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;

/**
 * The <code>Region2DProperties</code> class is the implementation class for a
 * Region as used in some CAD apps. It currently contains a rectangle describing
 * the dimensions of a subspace of interest, along with surfaces and their
 * status/materials. As such, it isn't quite the same as a Reference Plane.
 * <p>
 * This class is loosely based on the Region object from AutoCAD, which is a
 * two-dimensional enclosed area that optionally has mass properties. In our
 * case, we don't compute the centroid but do model the surface properties.
 * <p>
 * This class is strictly for 2D CAD; the AutoCAD 3DFace object is a good model
 * for extending to 3D CAD later on.
 */
public final class Region2DProperties extends Extents2DProperties {

    /** An observable list of Surface Properties to support Data Binding. */
    private final ObservableList< SurfaceProperties > surfacePropertiesList;

    // NOTE: These fields have to follow JavaFX Property Beans conventions.
    // TODO: Split value changed to surface material changed and status changed?
    private BooleanBinding regionBoundaryChanged;
    private BooleanBinding surfaceNameChanged;
    private BooleanBinding surfaceValueChanged;

    /*
     * Default constructor when nothing is known.
     */
    public Region2DProperties() {
        this(
                X_METERS_DEFAULT,
                Y_METERS_DEFAULT,
                WIDTH_METERS_DEFAULT,
                HEIGHT_METERS_DEFAULT );
    }

    /*
     * Default constructor when surfaces are disabled.
     */
    private Region2DProperties( final double pBoundaryX,
                                final double pBoundaryY,
                                final double pBoundaryWidth,
                                final double pBoundaryHeight ) {
        this(
                pBoundaryX,
                pBoundaryY,
                pBoundaryWidth,
                pBoundaryHeight,
                SurfacePropertiesNameManager.getSurfaceNameDefault( 1 ),
                Surface.SURFACE_BYPASSED_DEFAULT,
                Surface.SURFACE_MATERIAL_DEFAULT,
                SurfacePropertiesNameManager.getSurfaceNameDefault( 2 ),
                Surface.SURFACE_BYPASSED_DEFAULT,
                Surface.SURFACE_MATERIAL_DEFAULT,
                SurfacePropertiesNameManager.getSurfaceNameDefault( 3 ),
                Surface.SURFACE_BYPASSED_DEFAULT,
                Surface.SURFACE_MATERIAL_DEFAULT,
                SurfacePropertiesNameManager.getSurfaceNameDefault( 4 ),
                Surface.SURFACE_BYPASSED_DEFAULT,
                Surface.SURFACE_MATERIAL_DEFAULT );
    }

    /*
     * Default constructor when surfaces are selectively enabled.
     */
    public Region2DProperties(
            final double pBoundaryX,
            final double pBoundaryY,
            final double pBoundaryWidth,
            final double pBoundaryHeight,
            final ObservableList< SurfaceProperties > pSurfaceProperties ) {
        this( pBoundaryX,
              pBoundaryY,
              pBoundaryWidth,
              pBoundaryHeight,
              pSurfaceProperties.get( 0 ).getSurfaceName(),
              pSurfaceProperties.get( 0 ).isSurfaceBypassed(),
              pSurfaceProperties.get( 0 ).getSurfaceMaterial(),
              pSurfaceProperties.get( 1 ).getSurfaceName(),
              pSurfaceProperties.get( 1 ).isSurfaceBypassed(),
              pSurfaceProperties.get( 1 ).getSurfaceMaterial(),
              pSurfaceProperties.get( 2 ).getSurfaceName(),
              pSurfaceProperties.get( 2 ).isSurfaceBypassed(),
              pSurfaceProperties.get( 2 ).getSurfaceMaterial(),
              pSurfaceProperties.get( 3 ).getSurfaceName(),
              pSurfaceProperties.get( 3 ).isSurfaceBypassed(),
              pSurfaceProperties.get( 3 ).getSurfaceMaterial() );
    }

    /*
     * Default constructor when surfaces are selectively enabled.
     */
    public Region2DProperties( final double pBoundaryX,
                               final double pBoundaryY,
                               final double pBoundaryWidth,
                               final double pBoundaryHeight,
                               final String pSurface1Name,
                               final boolean pSurface1Bypassed,
                               final SurfaceMaterial pSurface1Material,
                               final String pSurface2Name,
                               final boolean pSurface2Bypassed,
                               final SurfaceMaterial pSurface2Material,
                               final String pSurface3Name,
                               final boolean pSurface3Bypassed,
                               final SurfaceMaterial pSurface3Material,
                               final String pSurface4Name,
                               final boolean pSurface4Bypassed,
                               final SurfaceMaterial pSurface4Material ) {
        // Always call the super-constructor first!
        super( pBoundaryX, pBoundaryY, pBoundaryWidth, pBoundaryHeight );

        surfacePropertiesList = FXCollections.observableArrayList();

        final SurfaceProperties surface1Properties = new SurfaceProperties(
                1,
                pSurface1Name,
                pSurface1Bypassed,
                pSurface1Material );
        surfacePropertiesList.add( surface1Properties );

        final SurfaceProperties surface2Properties = new SurfaceProperties(
                2,
                pSurface2Name,
                pSurface2Bypassed,
                pSurface2Material );
        surfacePropertiesList.add( surface2Properties );

        final SurfaceProperties surface3Properties = new SurfaceProperties(
                3,
                pSurface3Name,
                pSurface3Bypassed,
                pSurface3Material );
        surfacePropertiesList.add( surface3Properties );

        final SurfaceProperties surface4Properties = new SurfaceProperties(
                4,
                pSurface4Name,
                pSurface4Bypassed,
                pSurface4Material );
        surfacePropertiesList.add( surface4Properties );

        // Bind all the properties to the associated dirty flag.
        // NOTE: This is done during initialization, as it is best to make
        //  singleton objects and just update their values vs. reconstructing.
        makeBooleanBindings();
    }

    /*
     * Default constructor when surfaces are selectively enabled.
     */
    public Region2DProperties(
            final Rectangle pBoundary,
            final ObservableList< SurfaceProperties > pSurfaceProperties ) {
        this( pBoundary.getX(),
              pBoundary.getY(),
              pBoundary.getWidth(),
              pBoundary.getHeight(),
              pSurfaceProperties );
    }

    /*
     * Copy constructor.
     */
    public Region2DProperties( final Region2DProperties pRegion2DProperties ) {
        this( pRegion2DProperties.getX(),
              pRegion2DProperties.getY(),
              pRegion2DProperties.getWidth(),
              pRegion2DProperties.getHeight(),
              pRegion2DProperties.getSurfaceProperties() );
    }

    public void makeBooleanBindings() {
        // Establish the Region Boundary Changed dirty flag criteria as any
        // boundary parameter change.
        regionBoundaryChanged = BeanFactory.makeBooleanBinding(
            xProperty(), 
            yProperty(), 
            widthProperty(), 
            heightProperty() );

        // Establish the Surface Name Changed dirty flag criteria as any surface
        // name change.
        // NOTE: Collections only flag a change if elements are added or removed,
        //  as opposed to when the settings on an element change, so we must bind 
        //  instead against all the mutable properties of the individual elements
        //  in the collection. This works because we have a known fixed list size.
        final SurfaceProperties surface1Properties
                = surfacePropertiesList.get( 0 );
        final SurfaceProperties surface2Properties
                = surfacePropertiesList.get( 1 );
        final SurfaceProperties surface3Properties
                = surfacePropertiesList.get( 2 );
        final SurfaceProperties surface4Properties
                = surfacePropertiesList.get( 3 );
        surfaceNameChanged = BeanFactory.makeBooleanBinding(
            surface1Properties.surfaceNameProperty(),
            surface2Properties.surfaceNameProperty(),
            surface3Properties.surfaceNameProperty(),
            surface4Properties.surfaceNameProperty() );

        // Establish the Surface Value Changed dirty flag criteria as any
        // surface value change.
        // NOTE: Collections only flag a change if elements are added or
        //  removed, as opposed to when the settings on an element change, so we
        //  must bind instead against all the mutable properties of the
        //  individual elements in the collection. This works because we have a
        //  known fixed list size.
        // TODO: Consider avoiding duplication by referencing surfaceNameChanged
        //  as long as that doesn't cause issues with invalidate status on get().
        surfaceValueChanged = BeanFactory.makeBooleanBinding(
            surface1Properties.surfaceBypassedProperty(),
            surface1Properties.surfaceMaterialProperty(),
            surface2Properties.surfaceBypassedProperty(),
            surface2Properties.surfaceMaterialProperty(),
            surface3Properties.surfaceBypassedProperty(),
            surface3Properties.surfaceMaterialProperty(),
            surface4Properties.surfaceBypassedProperty(),
            surface4Properties.surfaceMaterialProperty() );
    }

    // NOTE: Cloning is disabled as it is dangerous; use the copy constructor
    // instead.
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    public BooleanBinding regionBoundaryChangedProperty() {
        return regionBoundaryChanged;
    }
    
    public boolean isRegionBoundaryChanged() {
        return regionBoundaryChanged.get();
    }
    
    public BooleanBinding surfaceNameChangedProperty() {
        return surfaceNameChanged;
    }
    
    public boolean isSurfaceNameChanged() {
        return surfaceNameChanged.get();
    }
    
    public BooleanBinding surfaceValueChangedProperty() {
        return surfaceValueChanged;
    }
    
    public boolean isSurfaceValueChanged() {
        return surfaceValueChanged.get();
    }

    public ObservableList< SurfaceProperties > getSurfaceProperties() {
        return surfacePropertiesList;
    }

    /*
     * Default pseudo-constructor.
     */
    public void reset() {
        // NOTE: Do not reset the Surface Names.
        setRegion2D( X_METERS_DEFAULT,
                     Y_METERS_DEFAULT,
                     WIDTH_METERS_DEFAULT,
                     HEIGHT_METERS_DEFAULT,
                     surfacePropertiesList.get( 0 ).getSurfaceName(),
                Surface.SURFACE_BYPASSED_DEFAULT,
                Surface.SURFACE_MATERIAL_DEFAULT,
                     surfacePropertiesList.get( 1 ).getSurfaceName(),
                Surface.SURFACE_BYPASSED_DEFAULT,
                Surface.SURFACE_MATERIAL_DEFAULT,
                     surfacePropertiesList.get( 2 ).getSurfaceName(),
                Surface.SURFACE_BYPASSED_DEFAULT,
                Surface.SURFACE_MATERIAL_DEFAULT,
                     surfacePropertiesList.get( 3 ).getSurfaceName(),
                Surface.SURFACE_BYPASSED_DEFAULT,
                Surface.SURFACE_MATERIAL_DEFAULT);
    }

    /*
     * Pseudo-constructor. Private, so does not notify listeners.
     */
    public void setRegion2D(
            final double pBoundaryX,
            final double pBoundaryY,
            final double pBoundaryWidth,
            final double pBoundaryHeight,
            final ObservableList< SurfaceProperties > pSurfaceProperties ) {
        setExtents( pBoundaryX, pBoundaryY, pBoundaryWidth, pBoundaryHeight );

        setSurfaceProperties( pSurfaceProperties );
    }

    /*
     * Fully qualified pseudo-constructor.
     */
    public void setRegion2D( final double pBoundaryX,
                             final double pBoundaryY,
                             final double pBoundaryWidth,
                             final double pBoundaryHeight,
                             final String pSurface1Name,
                             final boolean pSurface1Bypassed,
                             final SurfaceMaterial pSurface1Material,
                             final String pSurface2Name,
                             final boolean pSurface2Bypassed,
                             final SurfaceMaterial pSurface2Material,
                             final String pSurface3Name,
                             final boolean pSurface3Bypassed,
                             final SurfaceMaterial pSurface3Material,
                             final String pSurface4Name,
                             final boolean pSurface4Bypassed,
                             final SurfaceMaterial pSurface4Material ) {
        setExtents( pBoundaryX, pBoundaryY, pBoundaryWidth, pBoundaryHeight );

        setSurfaceProperties( pSurface1Name,
                              pSurface1Bypassed,
                              pSurface1Material,
                              pSurface2Name,
                              pSurface2Bypassed,
                              pSurface2Material,
                              pSurface3Name,
                              pSurface3Bypassed,
                              pSurface3Material,
                              pSurface4Name,
                              pSurface4Bypassed,
                              pSurface4Material );
    }

    /*
     * Pseudo-constructor. Private, so does not notify listeners.
     */
    public void setRegion2D(
            final Rectangle pBoundary,
            final ObservableList< SurfaceProperties > pSurfaceProperties ) {
        setRegion2D( pBoundary.getX(),
                     pBoundary.getY(),
                     pBoundary.getWidth(),
                     pBoundary.getHeight(),
                     pSurfaceProperties );
    }

    /*
     * Copy pseudo-constructor.
     */
    public void setRegion2D( final Region2DProperties pRegion2DProperties ) {
        setRegion2D( pRegion2DProperties.getX(),
                     pRegion2DProperties.getY(),
                     pRegion2DProperties.getWidth(),
                     pRegion2DProperties.getHeight(),
                     pRegion2DProperties.getSurfaceProperties() );
    }

    public void setSurfaceProperties( final int pSurfaceIndex,
                                      final String pSurfaceName,
                                      final boolean pSurfaceBypassed,
                                      final SurfaceMaterial pSurfaceMaterial ) {
        final SurfaceProperties surfaceProperties = surfacePropertiesList.get(
                pSurfaceIndex );
        surfaceProperties.setSurfaceNumber( pSurfaceIndex + 1 );
        surfaceProperties.setSurfaceName( pSurfaceName );
        surfaceProperties.setSurfaceBypassed( pSurfaceBypassed );
        surfaceProperties.setSurfaceMaterial( pSurfaceMaterial );
    }

    private void setSurfaceProperties(
            final ObservableList< SurfaceProperties > pSurfacePropertiesList ) {
        for ( int surfaceIndex = 0;
              surfaceIndex < Region2D.NUMBER_OF_SURFACES;
              surfaceIndex++ ) {
            final SurfaceProperties surfaceProperties = pSurfacePropertiesList
                    .get( surfaceIndex );
            setSurfaceProperties( surfaceIndex,
                                  surfaceProperties.getSurfaceName(),
                                  surfaceProperties.isSurfaceBypassed(),
                                  surfaceProperties.getSurfaceMaterial() );
        }
    }

    public void setSurfaceProperties( final String pSurface1Name,
                                      final boolean pSurface1Bypassed,
                                      final SurfaceMaterial pSurface1Material,
                                      final String pSurface2Name,
                                      final boolean pSurface2Bypassed,
                                      final SurfaceMaterial pSurface2Material,
                                      final String pSurface3Name,
                                      final boolean pSurface3Bypassed,
                                      final SurfaceMaterial pSurface3Material,
                                      final String pSurface4Name,
                                      final boolean pSurface4Bypassed,
                                      final SurfaceMaterial pSurface4Material ) {
        setSurfaceProperties(
                0,
                pSurface1Name,
                pSurface1Bypassed,
                pSurface1Material );
        setSurfaceProperties(
                1,
                pSurface2Name,
                pSurface2Bypassed,
                pSurface2Material );
        setSurfaceProperties(
                2,
                pSurface3Name,
                pSurface3Bypassed,
                pSurface3Material );
        setSurfaceProperties(
                3,
                pSurface4Name,
                pSurface4Bypassed,
                pSurface4Material );
    }
}

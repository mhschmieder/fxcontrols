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
 * This file is part of the FxCadControls Library
 *
 * You should have received a copy of the MIT License along with the
 * FxCadControls Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/fxcadcontrols
 */
package com.mhschmieder.fxcontrols.model;

import com.mhschmieder.fxgraphics.beans.BeanFactory;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;

/**
 * The <code>DrawingLimitsProperties</code> class is the implementation class
 * for the inclusive bounds of a CAD drawing, such as venues used in CAD apps.
 * It currently contains a rectangle describing the boundary of the CAD space,
 * as well as a flag for whether to auto-sync to another boundary (usually a
 * Region2D, such as one that is used as a Prediction Plane in CAD apps).
 * <p>
 * This class is generally for 2D CAD, but is deliberately flexible towards
 * referring either to screen space (which is always 2D currently), or to
 * whatever projective two-dimensional axes are currently in use.
 */
public final class DrawingLimitsProperties extends Extents2DProperties {

    // Declare default constants, where appropriate, for all fields.
    public static final boolean     AUTO_SYNC_DEFAULT    = true;

    /**
     * Declare an invalid Bounding Box for convenience. According to the JavaFX
     * API docs, invalid Bounding Boxes are flagged by setting their width and
     * height to "-1". Default constructors might do that, but this is clearer.
     */
    public static final BoundingBox INVALID_BOUNDING_BOX = new BoundingBox(
            0.0d, 0.0d, -1d, -1d );

    /**
     * Cached observable copy of most recent auto-sync setting.
     */
    private BooleanProperty         autoSync;

    // NOTE: This field has to follow JavaFX Property Beans conventions.
    private BooleanBinding           drawingLimitsChanged;

    /**
     * Default constructor when nothing is known. Sets default extents.
     */
    public DrawingLimitsProperties() {
        this( AUTO_SYNC_DEFAULT );
    }

    /**
     * Default constructor when only auto-sync is known. Sets default bounds.
     *
     * @param pAutoSync
     *            {@code true} if auto-sync to other extents
     */
    public DrawingLimitsProperties( final boolean pAutoSync ) {
        super();

        // Initialize the fields that are unique to DrawingLimits vs.Extents2D.
        initDrawingLimits( pAutoSync );
    }

    /**
     * Cross-constructor from {@link Rectangle} to {@link DrawingLimitsProperties}.
     *
     * @param pBoundary
     *            The {@link Rectangle} to use for setting the fields
     */
    public DrawingLimitsProperties(final Rectangle pBoundary ) {
        this( AUTO_SYNC_DEFAULT, pBoundary );
    }

    /**
     * Cross-constructor from {@link Rectangle} to {@link DrawingLimitsProperties}.
     *
     * @param pAutoSync
     *            {@code true} if auto-sync to other extents
     * @param pBoundary
     *            The {@link Rectangle} to use for setting the fields
     */
    public DrawingLimitsProperties(final boolean pAutoSync, final Rectangle pBoundary ) {
        // Always call the super-constructor first!
        super( pBoundary );

        // Initialize the fields that are unique to DrawingLimits vs.Extents2D.
        initDrawingLimits( pAutoSync );
    }

    /**
     * Cross-constructor from {@link Rectangle2D} to {@link DrawingLimitsProperties}.
     *
     * @param pBounds
     *            The {@link Rectangle2D} to use for setting the fields
     */
    public DrawingLimitsProperties(final Rectangle2D pBounds ) {
        this( AUTO_SYNC_DEFAULT, pBounds );
    }

    /**
     * Cross-constructor from {@link Rectangle2D} to {@link DrawingLimitsProperties}.
     *
     * @param pAutoSync
     *            {@code true} if auto-sync to other extents
     * @param pBounds
     *            The {@link Rectangle2D} to use for setting the fields
     */
    public DrawingLimitsProperties(final boolean pAutoSync, final Rectangle2D pBounds ) {
        // Always call the super-constructor first!
        super( pBounds );

        // Initialize the fields that are unique to DrawingLimits vs.Extents2D.
        initDrawingLimits( pAutoSync );
    }

    /**
     * Cross-constructor from {@link Bounds} to {@link DrawingLimitsProperties}.
     *
     * @param computedBounds
     *            The {@link Bounds} to use for setting the fields
     */
    public DrawingLimitsProperties(final Bounds computedBounds ) {
        this( AUTO_SYNC_DEFAULT, computedBounds );
    }

    /**
     * Cross-constructor from {@link Bounds} to {@link DrawingLimitsProperties}.
     *
     * @param pAutoSync
     *            {@code true} if auto-sync to other extents
     * @param computedBounds
     *            The {@link Bounds} to use for setting the fields
     */
    public DrawingLimitsProperties(final boolean pAutoSync, final Bounds computedBounds ) {
        super( computedBounds );

        // Initialize the fields that are unique to DrawingLimits vs.Extents2D.
        initDrawingLimits( pAutoSync );
    }

    /**
     * Cross-constructor from {@link Extents2DProperties} to {@link DrawingLimitsProperties}.
     *
     * @param pExtents
     *            The {@link Extents2DProperties} to use for setting the fields
     */
    public DrawingLimitsProperties(final Extents2DProperties pExtents ) {
        this( AUTO_SYNC_DEFAULT, pExtents );
    }

    /**
     * Cross-constructor from {@link Extents2DProperties} to {@link DrawingLimitsProperties}.
     *
     * @param pAutoSync
     *            {@code true} if auto-sync to other extents
     * @param pExtents
     *            The {@link Extents2DProperties} to use for setting the fields
     */
    public DrawingLimitsProperties(final boolean pAutoSync, final Extents2DProperties pExtents ) {
        // Always call the super-constructor first!
        super( pExtents );

        // Initialize the fields that are unique to DrawingLimits vs.Extents2D.
        initDrawingLimits( pAutoSync );
    }

    /**
     * Partially qualified constructor. Turns auto-sync off by default.
     *
     * @param pBoundaryX
     *            The x-origin to use for the new {@link DrawingLimitsProperties}
     * @param pBoundaryY
     *            The y-origin to use for the new {@link DrawingLimitsProperties}
     * @param pBoundaryWidth
     *            The width to use for the new {@link DrawingLimitsProperties}
     * @param pBoundaryHeight
     *            The height to use for the new {@link DrawingLimitsProperties}
     */
    public DrawingLimitsProperties(final double pBoundaryX,
                                   final double pBoundaryY,
                                   final double pBoundaryWidth,
                                   final double pBoundaryHeight ) {
        this( AUTO_SYNC_DEFAULT, pBoundaryX, pBoundaryY, pBoundaryWidth, pBoundaryHeight );
    }

    /**
     * Fully qualified constructor.
     *
     * @param pAutoSync
     *            {@code true} if auto-sync to other extents
     * @param pBoundaryX
     *            The x-origin to use for the new {@link DrawingLimitsProperties}
     * @param pBoundaryY
     *            The y-origin to use for the new {@link DrawingLimitsProperties}
     * @param pBoundaryWidth
     *            The width to use for the new {@link DrawingLimitsProperties}
     * @param pBoundaryHeight
     *            The height to use for the new {@link DrawingLimitsProperties}
     */
    public DrawingLimitsProperties(final boolean pAutoSync,
                                   final double pBoundaryX,
                                   final double pBoundaryY,
                                   final double pBoundaryWidth,
                                   final double pBoundaryHeight ) {
        super( pBoundaryX, pBoundaryY, pBoundaryWidth, pBoundaryHeight );

        // Initialize the fields that are unique to DrawingLimits vs.Extents2D.
        initDrawingLimits( pAutoSync );
    }

    /**
     * Copy Constructor.
     *
     * @param pDrawingLimitsProperties
     *            The {@link DrawingLimitsProperties} to use for setting the fields
     */
    public DrawingLimitsProperties(final DrawingLimitsProperties pDrawingLimitsProperties) {
        this( pDrawingLimitsProperties.isAutoSync(),
              pDrawingLimitsProperties.getX(),
              pDrawingLimitsProperties.getY(),
              pDrawingLimitsProperties.getWidth(),
              pDrawingLimitsProperties.getHeight() );
    }

    // NOTE: Cloning is disabled as it is dangerous; use the copy constructor
    // instead.
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public BooleanProperty autoSyncProperty() {
        return autoSync;
    }

    public boolean isAutoSync() {
        return autoSync.get();
    }

    public void setAutoSync( final boolean pAutoSync ) {
        autoSync.set( pAutoSync );
    }
    
    public BooleanBinding drawingLimitsChangedProperty() {
        return drawingLimitsChanged;
    }
    
    public boolean isDrawingLimitsChanged() {
        return drawingLimitsChanged.get();
    }

    /*
     * Initialize the fields that are unique to {@link DrawingLimits}.
     * Generally called by constructors after setting Extents2D fields.
     */
    private void initDrawingLimits( final boolean pAutoSync ) {
        autoSync = new SimpleBooleanProperty( pAutoSync );

        // Bind all the properties to the associated dirty flag.
        // NOTE: This is done during initialization, as it is best to make
        //  singleton objects and just update their values vs. reconstructing.
        drawingLimitsChanged = BeanFactory.makeBooleanBinding(
             autoSyncProperty(),
             xProperty(),
             yProperty(),
             widthProperty(),
             heightProperty() );

    }

    /** Default pseudo-constructor. */
    public void reset() {
        setDrawingLimits( AUTO_SYNC_DEFAULT,
                          X_METERS_DEFAULT,
                          Y_METERS_DEFAULT,
                          WIDTH_METERS_DEFAULT,
                          HEIGHT_METERS_DEFAULT );
    }

    /*
     * Fully qualified pseudo-constructor.
     */
    public void setDrawingLimits( final boolean pAutoSync,
                                  final double pBoundaryX,
                                  final double pBoundaryY,
                                  final double pBoundaryWidth,
                                  final double pBoundaryHeight ) {
        setAutoSync( pAutoSync );

        setExtents( pBoundaryX, pBoundaryY, pBoundaryWidth, pBoundaryHeight );
    }

    /*
     * Fully qualified pseudo-constructor.
     */
    public void setDrawingLimits( final boolean pAutoSync, final Extents2DProperties pExtents ) {
        setAutoSync( pAutoSync );

        setExtents( pExtents );
    }

    /*
     * Fully qualified pseudo-constructor.
     */
    public void setDrawingLimits( final boolean pAutoSync, final Rectangle2D pRectangle ) {
        setAutoSync( pAutoSync );

        setExtents( pRectangle );
    }

    /*
     * Copy pseudo-constructor.
     */
    public void setDrawingLimits( final DrawingLimitsProperties pDrawingLimitsProperties) {
        setDrawingLimits( pDrawingLimitsProperties.isAutoSync(),
                          pDrawingLimitsProperties.getX(),
                          pDrawingLimitsProperties.getY(),
                          pDrawingLimitsProperties.getWidth(),
                          pDrawingLimitsProperties.getHeight() );
    }
}

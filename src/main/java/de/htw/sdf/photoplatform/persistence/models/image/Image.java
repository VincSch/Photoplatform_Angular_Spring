/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.models.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

/**
 * Entity class for a image representing the corresponding database table. Created by Sergej
 * Meister.
 */
@Entity
@Table(name = "RES_IMAGE")
public class Image extends AbstractBaseAuditEntity
{
    private static final long serialVersionUID = 5117200999390055688L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IS_PUBLIC")
    private boolean isPublic;

    @Column(name = "IS_ENABLED")
    private boolean enabled;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "COMRESSION")
    private String compression;

    @Column(name = "X_RESOLUTION")
    private double xResolution;

    @Column(name = "Y_RESOLUTION")
    private double yResolution;

    @Column(name = "RESOLUTION_UNIT")
    private String resolutionUnit;

    @Column(name = "X_DEMENSION")
    private Integer xDemension;

    @Column(name = "Y_DEMENSION")
    private Integer yDemension;

    @Column(name = "PATH", unique = true)
    private String path;

    /**
     * Returns image name.
     *
     * @return image name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets image name.
     * 
     * @param name
     *            image name.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns image description.
     *
     * @return image description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets image description.
     *
     * @param description
     *            image description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Is image shared.
     *
     * @return true if image shared.
     */
    public boolean isPublic()
    {
        return isPublic;
    }

    /**
     * Sets scope of image resource.
     *
     * @param isPublic
     *            true for shared, otherwise false.
     */
    public void setPublic(boolean isPublic)
    {
        this.isPublic = isPublic;
    }

    /**
     * Is image enabled. The image can be disabled from admin.
     *
     * @return true if active.
     */
    public boolean isEnabled()
    {
        return enabled;
    }

    /**
     * Sets image to enabled or disabled.
     *
     * @param enabled
     *            true if enabled.
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    /**
     * Returns image price.
     *
     * @return image price.
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * Sets image price.
     *
     * @param price
     *            image price.
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Returns image compression type.
     *
     * Supported compression type jpg,png.
     *
     * @return compression
     * @since 1.0
     */
    public String getCompression()
    {
        return compression;
    }

    /**
     * Sets compression type.
     *
     * Supported compression type jpg,png.
     *
     * @param compression
     *            image compression type.
     */
    public void setCompression(String compression)
    {
        this.compression = compression;
    }

    /**
     * Returns x resolution.
     *
     * @return x resolution.
     */
    public double getXResolution()
    {
        return xResolution;
    }

    /**
     * Sets x resolution.
     *
     * @param xResolution
     *            x resolution
     */
    public void setXResolution(double xResolution)
    {
        this.xResolution = xResolution;
    }

    /**
     * Returns y resolution.
     *
     * @return y resolution.
     */
    public double getYResolution()
    {
        return yResolution;
    }

    /**
     * Sets y resolution.
     *
     * @param yResolution
     *            y resolution
     */
    public void setYResolution(double yResolution)
    {
        this.yResolution = yResolution;
    }

    /**
     * Returns resolution unit.
     *
     * @return resolution unit.
     */
    public String getResolutionUnit()
    {
        return resolutionUnit;
    }

    /**
     * Sets resolution units
     * 
     * @param resolutionUnit
     *            resolution unit.
     */
    public void setResolutionUnit(String resolutionUnit)
    {
        this.resolutionUnit = resolutionUnit;
    }

    /**
     * Returns x demension.
     *
     * Image width.
     *
     * @return image width.
     */
    public Integer getXDemension()
    {
        return xDemension;
    }

    /**
     * Sets x demension.
     *
     * Image width.
     *
     * @param xDemension
     */
    public void setXDemension(Integer xDemension)
    {
        this.xDemension = xDemension;
    }

    /**
     * Returns image high.
     *
     * @return high
     */
    public Integer getYDemension()
    {
        return yDemension;
    }

    /**
     * Sets y demension.
     *
     * Image hight.
     *
     * @param yDemension
     *            image hight
     */
    public void setYDemension(Integer yDemension)
    {
        this.yDemension = yDemension;
    }

    /**
     * Returns path to image resource.
     *
     * @return path to image.
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Sets path to image resource.
     *
     * @param path
     *            path to image.
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "Image{" + "name='" + name + '\'' + ", path='" + path + '\'' + ", isPublic="
                + isPublic + ", enabled=" + enabled + ", price=" + price + ", compression='"
                + compression + '\'' + '}';
    }
}

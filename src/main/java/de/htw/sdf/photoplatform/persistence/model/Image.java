/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform.persistence.model;

import de.htw.sdf.photoplatform.persistence.AbstractBaseAuditEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity class for a image representing the corresponding database table.
 * Created by Sergej Meister.
 */
@Entity
@Table(name = "RES_IMAGE")
public class Image extends AbstractBaseAuditEntity {
    private static final long serialVersionUID = 5117200999390055688L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IS_PUBLIC")
    private boolean isPublic;

    @Column(name = "IS_ENABLED")
    private boolean enabled;

    @Column(name = "PRICE", precision = 10, scale = 2)
    private Double price;

    @Column(name = "PATH", unique = true)
    private String path;

    @Column(name = "THUMB_PATH", unique = true)
    private String thumbPath;

    @Column(name = "SMALL_THUMB_PATH", unique = true)
    private String smallThumbPath;

    @Column(name = "MOBILE_THUMB_PATH", unique = true)
    private String mobileThumbPath;

    @Column(name = "MIME")
    private String mime;

    @Column(name = "METADATA", columnDefinition="TEXT")
    private String metaData;

    /**
     * Returns image name.
     *
     * @return image name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets image name.
     *
     * @param name image name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns image description.
     *
     * @return image description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets image description.
     *
     * @param description image description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Is image shared.
     *
     * @return true if image shared.
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * Sets scope of image resource.
     *
     * @param isPublic true for shared, otherwise false.
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Is image enabled. The image can be disabled from admin.
     *
     * @return true if active.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets image to enabled or disabled.
     *
     * @param enabled true if enabled.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns image price.
     *
     * @return image price.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets image price.
     *
     * @param price image price.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Returns path to image resource.
     *
     * @return path to image.
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets path to image resource.
     *
     * @param path path to image.
     */
    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getSmallThumbPath() {
        return smallThumbPath;
    }

    public void setSmallThumbPath(String smallThumbPath) {
        this.smallThumbPath = smallThumbPath;
    }

    public String getMobileThumbPath() {
        return mobileThumbPath;
    }

    public void setMobileThumbPath(String mobileThumbPath) {
        this.mobileThumbPath = mobileThumbPath;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Image{" + "name='" + name + '\'' + ", path='" + path + '\''
            + ", isPublic=" + isPublic + ", enabled=" + enabled
            + ", price=" + price
            + '}';
    }
}

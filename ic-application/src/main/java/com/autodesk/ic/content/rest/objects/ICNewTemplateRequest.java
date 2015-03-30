package com.autodesk.ic.content.rest.objects;

import java.io.InputStream;

/**
 * Created by cataldp on 3/30/2015.
 */
public class ICNewTemplateRequest {

    private String description;
    private String[] categories;
    private String name;
    private String contributer;
    private String units;
    private long fileSize;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContributer() {
        return contributer;
    }

    public void setContributer(String contributer) {
        this.contributer = contributer;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}

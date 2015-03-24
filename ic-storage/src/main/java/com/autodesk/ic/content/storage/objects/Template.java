package com.autodesk.ic.content.storage.objects;

import java.net.URI;

/**
 * Created by cataldp on 3/16/2015.
 * Aggregate class for data contained in both TemplateFile and TemplateDescriptor
 */
public class Template {

    private TemplateFile templateFile;
    private String description;
    private String[] categories;
    private String name;
    private String contributer;

    private String units;
    private long fileSize; // Get this from the templateFile

}

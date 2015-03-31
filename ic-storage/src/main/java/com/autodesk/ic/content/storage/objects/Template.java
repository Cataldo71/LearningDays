package com.autodesk.ic.content.storage.objects;

import java.io.InvalidObjectException;
import java.net.URI;

/**
 * Created by cataldp on 3/16/2015.
 * Aggregate class for data contained in both TemplateFile and TemplateDescriptor
 */
public class Template {

    private TemplateFile templateFile;
    private TemplateDescriptor templateDescriptor;

    public Template(TemplateDescriptor descriptor, TemplateFile templateFile) {
        this.templateDescriptor = descriptor;
        this.templateFile = templateFile;
    }

    public TemplateFile getTemplateFile() {
        return templateFile;
    }

    public TemplateDescriptor getTemplateDescriptor() {
        return templateDescriptor;
    }
}
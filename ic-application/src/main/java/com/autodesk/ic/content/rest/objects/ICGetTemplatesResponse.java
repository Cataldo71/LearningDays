package com.autodesk.ic.content.rest.objects;

import java.util.List;

/**
 * Created by cataldp on 3/11/2015.
 */
public class ICGetTemplatesResponse {
    public List<ICTemplate> getTemplateDescriptors() {
        return templateDescriptors;
    }

    public void setTemplateDescriptors(List<ICTemplate> templateDescriptors) {
        this.templateDescriptors = templateDescriptors;
    }

    List<ICTemplate> templateDescriptors;


    }

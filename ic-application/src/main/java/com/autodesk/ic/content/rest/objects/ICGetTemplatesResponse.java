package com.autodesk.ic.content.rest.objects;

import java.util.List;

/**
 * Created by cataldp on 3/11/2015.
 */
public class ICGetTemplatesResponse {
    public List<ICTemplateDescriptor> getTemplateDescriptors() {
        return templateDescriptors;
    }

    public void setTemplateDescriptors(List<ICTemplateDescriptor> templateDescriptors) {
        this.templateDescriptors = templateDescriptors;
    }

    List<ICTemplateDescriptor> templateDescriptors;


    }

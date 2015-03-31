package com.autodesk.ic.content.service.objects;

import com.autodesk.ic.content.storage.objects.Template;

/**
 * Created by cataldp on 3/29/2015.
 */
public class CreateNewTemplateResponse {

    Template template;
    String token;

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

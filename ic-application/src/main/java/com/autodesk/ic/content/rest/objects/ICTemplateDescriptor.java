package com.autodesk.ic.content.rest.objects;

import java.net.URL;

/**
 * Created by cataldp on 3/11/2015.
 */
public class ICTemplateDescriptor {

    private String templateId;
    private String templateDesc;
    private URL thumbnailUrl;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public URL getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(URL thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}

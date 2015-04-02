package com.autodesk.ic.content.rest.marshall;

import com.autodesk.ic.content.storage.objects.Template;
import com.autodesk.ic.content.rest.objects.ICTemplate;

/**
 * Created by cataldp on 3/31/2015.
 */
public class ICTemplateMarshaller {
    public static ICTemplate fromServiceObject(Template serviceObject) {
        // TODO: marshall the object
        //
        ICTemplate apiObject = new ICTemplate.ICTemplateBuilder()
                .categories(serviceObject.getTemplateDescriptor().getCategories())
                .storageId(serviceObject.getTemplateFile().getStorageId())
                .contributer(serviceObject.getTemplateDescriptor().getContributor())
                .templateId(serviceObject.getTemplateFile().getId())
                .templateName(serviceObject.getTemplateDescriptor().getTemplateName())
                .uploadToken(serviceObject.getTemplateFile().getAccessToken())
                .description(serviceObject.getTemplateDescriptor().getDescription())
                .fileName(serviceObject.getTemplateFile().getFileName())
                .units(serviceObject.getTemplateDescriptor().getUnits())
                .fileSize(serviceObject.getTemplateFile().getFileSize())
                .build();


        return apiObject;
    }
}

package com.autodesk.ic.content.service;

import com.autodesk.ic.content.service.objects.CreateNewTemplateRequest;
import com.autodesk.ic.content.storage.AzureSqlDatabase;
import com.autodesk.ic.content.storage.objects.DbException;
import com.autodesk.ic.content.storage.objects.Template;
import com.autodesk.ic.content.storage.objects.TemplateDescriptor;

import java.io.BufferedInputStream;
import java.util.UUID;

/**
 * Created by cataldp on 3/23/2015.
 */


public class TemplateService {
    // needs to be injected
    //
    private AzureSqlDatabase db = new AzureSqlDatabase();
    private AzureStorage azureStorage = new AzureStorage();

    private static final String storageConnectionString =
            "DefaultEndpointsProtocol=https;AccountName=portalvhdsk24ch13b1vchf;AccountKey=sl6ypeaAUbOc7seYiQKPa2FjXmXiFhBcJ1TDYn3Fsa5/N9oHWWB44SFkee8ITbH3YjowNcorAytNfwNh2ZIGYg==";

    public String heartbeat() {
        return db.heartbeat();
    }
    public long AddNewTemplate(CreateNewTemplateRequest request) {
        long newTemplateId = -1;
        // Push the input stream over to Azure Storage Account
        //

        // put the template data into the database
        //
        TemplateDescriptor.TemplateDescriptorBuilder builder = new TemplateDescriptor.TemplateDescriptorBuilder();

        TemplateDescriptor newDescriptor = builder.templateName(request.getName())
                .categories(request.getCategories())
                .description(request.getDescription())
                .contributer(request.getContributer())
                .units(request.getUnits())
                .build();
        try {


            BufferedInputStream bis = new BufferedInputStream(request.getFileStream());
            // Not sure if we need to reset the input stream or not.
            //
//            bis.mark(0);
//            bis.reset();
            // push the file over to Azure
            //

            String storageId = UUID.randomUUID().toString();
            azureStorage.StoreFileAsBlob(bis,request.getFileSize(), storageId);

            // Add the entry to the database
            newTemplateId = db.AddTemplate(newDescriptor, storageId, request.getFileName(), request.getFileSize());
        }
        catch (DbException dbex)
        {
            // create an appropriate error response
            //
            dbex.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return newTemplateId;

    }

    public void RemoveTemplate(long templateId) {
        // get the full template from the db to get the storageID of this template
        //
        String storageId = null;

        // Delete the template from the db
        //
        try {
            db.DeleteTemplate(templateId);

            // clean up the azure storage
            //
            if (storageId != null)
                azureStorage.DeleteFile(storageId);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}

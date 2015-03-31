package com.autodesk.ic.content.service;

import com.autodesk.ic.content.service.objects.CreateNewTemplateRequest;
import com.autodesk.ic.content.service.objects.CreateNewTemplateResponse;
import com.autodesk.ic.content.storage.AzureSqlDatabase;
import com.autodesk.ic.content.storage.objects.DbException;
import com.autodesk.ic.content.storage.objects.Template;
import com.autodesk.ic.content.storage.objects.TemplateDescriptor;

import java.util.List;
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

    /**
     *
     * @return
     */
    public String heartbeat() {
        return db.heartbeat();
    }

    /**
     *
     * @param request
     * @return
     */
    public CreateNewTemplateResponse addNewTemplate(CreateNewTemplateRequest request) {
        long newTemplateId = -1;
        CreateNewTemplateResponse response = new CreateNewTemplateResponse();

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

            // If there is an input stream - push it up to Azure
            //
            String storageId = "__NOT_UPLOADED__";
 /*           if(request.getFileStream() != null) {
                BufferedInputStream bis = new BufferedInputStream(request.getFileStream());

                // push the file over to Azure
                //

                storageId = UUID.randomUUID().toString();
                azureStorage.StoreFileAsBlob(bis, request.getFileSize(), storageId);
            }*/
            // Create the storage ID for this file - this will be used by the client to upload the file
            //
            storageId = UUID.randomUUID().toString();

            //
            // Add the entry to the database
            newTemplateId = db.addTemplate(newDescriptor, storageId, request.getFileName(), request.getFileSize());

            // generate a shared access signature on the container so that the client can upload the file.
            //
            String sas = azureStorage.generateTemporaryAccessToken();

            // Build the response
            //
            Template t = db.getTemplateByFileId(newTemplateId);

            response.setTemplate(t);
            response.setToken(sas);

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
        return response;

    }

    /**
     *
     * @param templateId
     */
    public void removeTemplate(long templateId) {
        // get the full template from the db to get the storageID of this template
        //
        String storageId = null;

        // Delete the template from the db
        //
        try {
            db.deleteTemplateByFileId(templateId);

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

    /**
     *
     * @return
     */
    public List<Template> getAllTemplates()
    {
        List<Template> templates = null;
        try
        {
            templates = db.getAllTemplates();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return templates;
    }

    /**
     * Get a single template and append on a shared access string from Azure to allow it to be downloaded.
     * @param templateFileId
     * @return
     */
    public Template getTemplate(long templateFileId)
    {
        Template response = null;
        try {
            // Grab the template from the db.
            //
            response = db.getTemplateByFileId(templateFileId);

            azureStorage.generateDownloadToken(response.getTemplateFile().getStorageId());
        }
        catch (Exception e) {
            e.printStackTrace();

        }
        return response;
    }
}

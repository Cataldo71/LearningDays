package com.autodesk.ic.content.service;

import com.autodesk.ic.content.storage.AzureSqlDatabase;
import com.autodesk.ic.content.storage.objects.DbException;
import com.autodesk.ic.content.storage.objects.Template;
import com.autodesk.ic.content.storage.objects.TemplateDescriptor;

/**
 * Created by cataldp on 3/23/2015.
 */


public class TemplateService {
    // needs to be injected
    //
    private AzureSqlDatabase db = new AzureSqlDatabase();

    private static final String storageConnectionString =
            "DefaultEndpointsProtocol=https;AccountName=portalvhdsk24ch13b1vchf;AccountKey=sl6ypeaAUbOc7seYiQKPa2FjXmXiFhBcJ1TDYn3Fsa5/N9oHWWB44SFkee8ITbH3YjowNcorAytNfwNh2ZIGYg==";

    public String heartbeat() {
        return db.heartbeat();
    }
    public void AddNewTemplate(String tname, String desc, String filename, String[] categories, long length) {

        // Push the input stream over to Azure Storage Account
        //

        // put the template data into the database
        //
        TemplateDescriptor.TemplateDescriptorBuilder builder = new TemplateDescriptor.TemplateDescriptorBuilder();

        TemplateDescriptor newDescriptor = builder.templateName(tname)
                .categories(categories)
                .description(desc)
                .contributer("TestUser")
                .units("en").Build();
        try {
            db.AddTemplate(newDescriptor, filename, filename, length);
        }
        catch (DbException dbex)
        {
            // create an appropriate error response
            //
            dbex.printStackTrace();
        }

    }

}

package com.autodesk.ic.content.service;

import com.autodesk.ic.content.service.objects.CreateNewTemplateRequest;

import com.autodesk.ic.content.service.objects.CreateNewTemplateResponse;
import com.autodesk.ic.content.storage.objects.Template;
import com.autodesk.ic.content.storage.objects.TemplateDescriptor;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.*;
import java.util.List;

import static org.testng.Assert.*;

public class TemplateServiceTest {

    @Test
    public void testHeartbeat() throws Exception {
        TemplateService service = new TemplateService();
        String heartbeat = service.heartbeat();

        assertNotNull(heartbeat);
    }

    @Test
    public void testAddNewTemplate() throws Exception {
        CreateNewTemplateRequest.CreateNewTemplateRequestBuilder builder = new CreateNewTemplateRequest.CreateNewTemplateRequestBuilder();
        File newFile = new File("c:/temp/testfile.test");
        FileUtils.writeStringToFile(newFile, "hello world");
        if(newFile.createNewFile())
            FileUtils.writeStringToFile(newFile, "hello world");

        assertTrue(newFile.exists(), "Failed to create the file" + newFile.getName());


        // create the request
        //
        CreateNewTemplateRequest request = builder.name("Test Template")
                .fileSize(FileUtils.sizeOf(newFile))
                .categories(null)
                .description("Automation Test File")
                .fileName("testFile.test")
                .contributer("Automation Test")
                .fileStream(FileUtils.openInputStream(newFile))
                .units("in")
                .build();

        TemplateService templateService = new TemplateService();
        CreateNewTemplateResponse resp = null;
        try {
             resp = templateService.addNewTemplate(request);
            // validate the response
            //
            assertNotNull(resp.getToken(),"Null access token for new template");
            assertEquals(resp.getTemplate().getTemplateFile().getFileName(),"testFile.test");
        } catch (Exception ex)
        {
            fail("Exception when adding template to the database");
        }

        ////
        // Delete the template
        ////
        try {
            if(resp != null)
            templateService.removeTemplate(resp.getTemplate().getTemplateFile().getId());
        } catch (Exception ex)
        {
            fail("Exception when removing template from the database",ex);
        }
    }

    @Test
    public void testGetAllTemplates()
    {
        TemplateService service = new TemplateService();
        try {
            List<Template> descriptors = service.getAllTemplates();
        } catch(Exception e)
        {
            fail("failed to get all templates", e);
        }
    }
}
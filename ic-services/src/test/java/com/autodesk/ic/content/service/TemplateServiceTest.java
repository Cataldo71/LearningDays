package com.autodesk.ic.content.service;

import com.autodesk.ic.content.service.objects.CreateNewTemplateRequest;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.*;

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
        long templateId = -1;
        try {
             templateId = templateService.AddNewTemplate(request);
        } catch (Exception ex)
        {
            fail("Exception when adding template to the database");
        }

        ////
        // Delete the template
        ////
        try {

            templateService.RemoveTemplate(templateId);
        } catch (Exception ex)
        {
            fail("Exception when removing template from the database");
        }
    }
}
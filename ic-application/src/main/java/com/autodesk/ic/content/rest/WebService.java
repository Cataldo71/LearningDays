package com.autodesk.ic.content.rest;

import com.autodesk.ic.content.rest.marshall.ICTemplateMarshaller;
import com.autodesk.ic.content.rest.objects.ICGetTemplatesResponse;
import com.autodesk.ic.content.rest.objects.ICNewTemplateRequest;
import com.autodesk.ic.content.rest.objects.ICTemplate;
import com.autodesk.ic.content.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.autodesk.ic.content.service.objects.CreateNewTemplateRequest;
import com.autodesk.ic.content.service.objects.CreateNewTemplateResponse;
import com.autodesk.ic.content.storage.objects.Template;
import com.autodesk.ic.content.storage.objects.TemplateDescriptor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Created by cataldp on 1/22/2015.
 */
@Path(WebService.WEBSERVICE_PATH)
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class WebService {

    public static final String WEBSERVICE_PATH = "/contentservice/v1";

    @Context
    private HttpServletRequest httpRequest;


    TemplateService templateService;

    public WebService() {
        templateService = new TemplateService();
    }
    /**
     * Gets information about the running server.
     * This function does not require authentication.
     * @return Information about the running server.
     */
    @Path("system")
    @GET
    public String getSystem()  {

        return templateService.heartbeat();
    }

    @Path("templates")
    @GET
    public ICGetTemplatesResponse getTemplates()
    {
        List<Template> descriptors = templateService.getAllTemplates();

        ICGetTemplatesResponse response = new ICGetTemplatesResponse();
        ArrayList<ICTemplate> templates = new ArrayList<ICTemplate>();
        for(Template td : descriptors) {
            ICTemplate.ICTemplateBuilder builder = new ICTemplate.ICTemplateBuilder();
            ICTemplate newTemplate = builder.description(td.getTemplateDescriptor().getDescription())
                    .templateName(td.getTemplateDescriptor().getTemplateName())
                    .templateId(td.getTemplateFile().getId())
                    .units(td.getTemplateDescriptor().getUnits())
                    .contributer(td.getTemplateDescriptor().getContributer())
                    .storageId(td.getTemplateFile().getStorageId()).build();

            templates.add(newTemplate);

        }

        response.setTemplateDescriptors(templates);

        return response;
    }

    @Path("templates/{id}")
    @GET
    public ICTemplate getTemplate(@PathParam("id") long templateId)
    {
        ICTemplate response = null;

        // get the single template
        Template t = templateService.getTemplate(templateId);

        return ICTemplateMarshaller.fromServiceObject(t);

    }
    @Path("templates")
    @POST
    public ICTemplate postTemplates(ICNewTemplateRequest request)
    {
        ICTemplate response = null;
        try {
            CreateNewTemplateResponse serviceResponse = null;
            // not a multi part upload. Just create the metadata for now
            //
            // Create a service request
            //
            CreateNewTemplateRequest.CreateNewTemplateRequestBuilder requestBuilder =
                    new CreateNewTemplateRequest.CreateNewTemplateRequestBuilder();


            CreateNewTemplateRequest serviceRequest = requestBuilder.name(request.getName())
                    .units(request.getUnits())
                    .contributer(request.getContributer())
                    .description(request.getDescription())
                    .categories(request.getCategories())
                    .fileSize(request.getFileSize())
                    .fileStream(null)
                    .build();

            serviceResponse = templateService.addNewTemplate(serviceRequest);
            Template t = serviceResponse.getTemplate();
            t.getTemplateFile().setAccessToken(serviceResponse.getToken());
            // TODO: Move all of this conversion between service response and rest objects into marshalling
            response = new ICTemplate.ICTemplateBuilder()
                    .categories(serviceResponse.getTemplate().getTemplateDescriptor().getCategories())
                    .storageId(serviceResponse.getTemplate().getTemplateFile().getStorageId())
                    .contributer(serviceResponse.getTemplate().getTemplateDescriptor().getContributer())
                    .templateId(serviceResponse.getTemplate().getTemplateFile().getId())
                    .templateName(serviceResponse.getTemplate().getTemplateDescriptor().getTemplateName())
                    .uploadToken(serviceResponse.getToken())
                    .description(serviceResponse.getTemplate().getTemplateDescriptor().getDescription())
                    .fileName(serviceResponse.getTemplate().getTemplateFile().getFileName())
                    .units(serviceResponse.getTemplate().getTemplateDescriptor().getUnits())
                    .build();

        }
        catch(Exception e)
        {
            e.printStackTrace();
            // bad bad bad
        }
        return response;
    }

    @Path("templates/{id}")
    @DELETE
    public void deleteTemplate(@PathParam("id") long templateId) {
        // call to the template service to remove the template.
        //
        templateService.removeTemplate(templateId);
    }
}

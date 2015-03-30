package com.autodesk.ic.content.rest;

import com.autodesk.ic.content.rest.objects.ICGetTemplatesResponse;
import com.autodesk.ic.content.rest.objects.ICNewTemplateRequest;
import com.autodesk.ic.content.rest.objects.ICTemplateDescriptor;
import com.autodesk.ic.content.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.autodesk.ic.content.service.objects.CreateNewTemplateRequest;
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
        List<TemplateDescriptor> descriptors = templateService.getAllTemplates();
        ICGetTemplatesResponse response = new ICGetTemplatesResponse();
        ICTemplateDescriptor tdesc = new ICTemplateDescriptor();
        ArrayList<ICTemplateDescriptor> templateDescriptors = new ArrayList<ICTemplateDescriptor>();
        for(TemplateDescriptor td : descriptors) {
            tdesc = new ICTemplateDescriptor();
            tdesc.setDescription(td.getDescription());
            tdesc.setTemplateName(td.getTemplateName());
            tdesc.setTemplateId(td.getTemplateId());
            tdesc.setUnits(td.getUnits());
            tdesc.setContributer(td.getContributer());
            tdesc.setStorageId(td.getStorageId());
            try {
                tdesc.setThumbnailUrl(new URL("http://imagestoreurl/imageid"));
            } catch (MalformedURLException ex)
            {
                // do nothing, we like bad urls
            }
            templateDescriptors.add(tdesc);

        }

        response.setTemplateDescriptors(templateDescriptors);

        return response;
    }

    @Path("templates")
    @POST
    public void postTemplates(ICNewTemplateRequest request)
    {
        try {
            FileItemFactory factory = new DiskFileItemFactory();

            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setFileSizeMax(9999999); // 10 meg max?
            BufferedInputStream is = null;
            if (ServletFileUpload.isMultipartContent(httpRequest)) {
                List items = fileUpload.parseRequest(httpRequest);
                Iterator iter = items.iterator();
                while(iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if(!item.isFormField()) {
                        // Create a service request
                        //
                        CreateNewTemplateRequest.CreateNewTemplateRequestBuilder requestBuilder =
                                new CreateNewTemplateRequest.CreateNewTemplateRequestBuilder();
                        is = new BufferedInputStream(item.getInputStream());

                        CreateNewTemplateRequest serviceRequest = requestBuilder.name(request.getName())
                                .units(request.getUnits())
                                .contributer(request.getContributer())
                                .description(request.getDescription())
                                .categories(request.getCategories())
                                .fileSize(request.getFileSize())
                                .fileStream(is)
                                .build();

                        templateService.addNewTemplate(serviceRequest);
                    }
                }
            } else
            {
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

                templateService.addNewTemplate(serviceRequest);
            }

            if(is != null)
                is.close();
        }
        catch(Exception e)
        {
            // bad bad bad
        }
    }
}

package com.autodesk.ic.content.rest;

import com.autodesk.ic.content.rest.objects.ICGetTemplatesResponse;
import com.autodesk.ic.content.rest.objects.ICTemplateDescriptor;
import com.autodesk.ic.content.service.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import com.autodesk.ic.content.storage.AzureSqlDatabase;
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
        ICGetTemplatesResponse response = new ICGetTemplatesResponse();
        ICTemplateDescriptor tdesc = new ICTemplateDescriptor();
        tdesc.setTemplateDesc("Coolest Template Ever");
        tdesc.setTemplateId("Guid:Guid:Guid:Guid");
        try {
            tdesc.setThumbnailUrl(new URL("http://imagestoreurl/imageid"));
        } catch (MalformedURLException ex)
        {
            // do nothing, we like bad urls
        }

        response.setTemplateDescriptors(Arrays.asList(tdesc));

        return response;
    }

    @Path("templates")
    @POST
    public void postTemplates()
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
                        is = new BufferedInputStream(item.getInputStream());
                        is.mark(0);
                        is.reset();
                        File f = new File(item.getName());
                        String name = f.getName();
                        System.out.println("Storing: " + name + " to Azure File Storage");
                        AzureStorage storage = new AzureStorage();
                        storage.StoreFileAsBlob(is,f.length());
                    }
                }
            }
            is.close();
        }
        catch(Exception e)
        {
            // bad bad bad
        }
    }
}

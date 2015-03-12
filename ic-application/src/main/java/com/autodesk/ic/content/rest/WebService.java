package com.autodesk.ic.content.rest;

import com.autodesk.ic.content.rest.objects.ICGetTemplatesResponse;
import com.autodesk.ic.content.rest.objects.ICTemplateDescriptor;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.autodesk.ic.content.storage.AzureSqlDatabase;

/**
 * Created by cataldp on 1/22/2015.
 */
@Path(WebService.WEBSERVICE_PATH)
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class WebService {
    public static final String WEBSERVICE_PATH = "/contentservice/v1";

    // needs to be injected
    //
    private AzureSqlDatabase db = new AzureSqlDatabase();

    /**
     * Gets information about the running server.
     * This function does not require authentication.
     * @return Information about the running server.
     */
    @Path("system")
    @GET
    public String getSystem()  {
        String dbString = db.heartbeat();
        return "Pat and Steve's Template Service!" + "\n" + "Database Connection: " + dbString;
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

}

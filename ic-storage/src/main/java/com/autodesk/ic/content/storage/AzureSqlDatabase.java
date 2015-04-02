package com.autodesk.ic.content.storage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.autodesk.ic.content.storage.objects.DbException;
import com.autodesk.ic.content.storage.objects.Template;
import com.autodesk.ic.content.storage.objects.TemplateDescriptor;
import com.autodesk.ic.content.storage.objects.TemplateFile;

/**
 * Created by cataldp on 3/12/2015.
 */

public class AzureSqlDatabase implements IStorage {

    public AzureSqlDatabase()
    {
        initialize();
    }
    private static String connectionString =
            "jdbc:sqlserver://t107hhh86i.database.windows.net:1433;database=ldays;user=cataldp@t107hhh86i;password=LearningDays1!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    // private connection object for each instance of the database.
    // No access outside of the database object.
    //
    private  Connection connection = null;


    @Override
    public boolean initialize() {
        // The types for the following variables are
        // defined in the java.sql library.

        try {
            // Ensure the SQL Server driver class is available.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection.
            connection = DriverManager.getConnection(connectionString);

            // Turn off auto commit so that we can manage our own transactions.
            //
            connection.setAutoCommit(false);

            // Check that the schema has been initialized
            //
            if(!_checkDbExists())
                throw new Exception("Database is not initialized");
        }
        // Exception handling
        catch (ClassNotFoundException cnfe)
        {
            // Log the error!
            //
            System.out.println("ClassNotFoundException " +
                    cnfe.getMessage());
        }
        catch (Exception e)
        {
            // Log the error
            //
            System.out.println("Exception " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean close()
    {
        if(connection != null) {
            try
            {
                // Close resources.
                if (null != connection) connection.close();
            }
            catch (SQLException sqlException)
            {
                // No additional action if close() statements fail.
            }
        }

        // nothing to do, db connection is already closed
        //
        return true;
    }
    // Create a transaction in the database to ensure database health
    //
    @Override
    public String heartbeat()
    {
        // The types for the following variables are
        // defined in the java.sql library.

        Statement statement = null;    // For the SQL statement
        ResultSet resultSet = null;    // For the result set, if applicable

        String serverVersion = "Connection Failed";

        try
        {
            serverVersion = "\n\tServer Type: " +  connection.getMetaData().getDatabaseProductName()
                    + "\n\tServer Version: " + connection.getMetaData().getDatabaseProductVersion()
            + "\n\tServer URL: " + connection.getMetaData().getURL();
            // Provide a message when processing is complete.
            System.out.println("Processing complete.");

        }
        // Exception handling - note should do typed handling here...
        catch (Exception e)
        {
            serverVersion = e.getMessage();
            System.out.println("Exception " + e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                // Close resources. Leave the connection alone.
                if (null != statement) statement.close();
                if (null != resultSet) resultSet.close();
            }
            catch (SQLException sqlException)
            {
                // No additional action if close() statements fail.
            }
        }
        return serverVersion;
    }

    /**
     * @return
     */
    @Override
    public List<TemplateDescriptor> getAllTemplateDescriptors() throws DbException {

        Statement statement = null;
        ResultSet rs = null;
        ArrayList<TemplateDescriptor> results = new ArrayList<TemplateDescriptor>();
        try {
            statement = connection.createStatement();
            TemplateDescriptor.TemplateDescriptorBuilder builder = new TemplateDescriptor.TemplateDescriptorBuilder();

            rs = statement.executeQuery("Select IC_TEMPLATE_DESCRIPTOR.Id, IC_TEMPLATE_DESCRIPTOR.name, IC_TEMPLATE_DESCRIPTOR.description,\n" +
                    "  IC_TEMPLATE_DESCRIPTOR.Units, IC_TEMPLATE_DESCRIPTOR.Contributor from IC_TEMPLATE_DESCRIPTOR\n");

            while (rs.next())
            {
                results.add(builder.templateId(rs.getInt(1))
                        .templateName(rs.getString(2))
                        .description(rs.getString(3))
                        .units(rs.getString(4))
                        .contributer(rs.getString(5))
                        .build());

            }
        }
        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            try {
                System.out.println("Database Transaction Failure: Get All Templates");
                connection.rollback();
            } catch (SQLException innerex) {
                // no nothing here
            }

            throw new DbException(sqlEx,"Failed to get all templates");

        }
        finally
        {
            try {
                if(statement != null) statement.close();

            } catch (Exception innerex) {
                throw new DbException(innerex,"Error Closing Statement in Get All Templates");
            }
        }
        return results;
    }

    /**
     * Get a single template descriptor given an ID
     *
     * @param templateDescId Id of the template descriptor
     * @return the template descriptor with the given ID
     * @throws com.autodesk.ic.content.storage.objects.DbException
     */
    @Override
    public TemplateDescriptor getTemplateDescriptor(long templateDescId) throws DbException {
        return null;
    }

    /**
     * Gets a set of template descriptors given a set of template ids
     *
     * @param templateDescIds collection of template Id's to retrieve
     * @return collection of template descriptors
     * @throws com.autodesk.ic.content.storage.objects.DbException
     */
    @Override
    public List<TemplateDescriptor> getTemplateDescriptors(long[] templateDescIds) throws DbException {
        return null;
    }

    /**
     * Gets all of the templates in a given category
     *
     * @param categoryName the name of the category
     * @return collection of template descriptors with a relationship to the given category
     * @throws com.autodesk.ic.content.storage.objects.DbException
     */
    @Override
    public List<TemplateDescriptor> getTemplateDescriptorsInCategory(String categoryName) throws DbException {
        return null;
    }

    /**
     * Gets all of the templates from a given contributor
     *
     * @param contributor the name of the contributor
     * @return a collection of templates that were uploaded by the given contributor
     * @throws com.autodesk.ic.content.storage.objects.DbException
     */
    @Override
    public List<TemplateDescriptor> getTemplateDescriptorsByContributor(String contributor) throws DbException {
        return null;
    }

    /**
     * Adds a single new template to the database. Function assumes that the file has already been stored in
     * storage!
     *
     * @param template  The template descriptor that defines the meta data for this new template
     * @param storageId Id of the record in whatever storage backend is being used
     * @param Filename  name of the physical file
     * @param size      size of the file in bytes
     * @return the Template that was created
     */
    @Override
    public long addTemplate(TemplateDescriptor template, String storageId, String Filename, long size) throws DbException {
        Statement statement = null;
        long newTemplateId = -1;
        int templateFileId = -1;
        ResultSet resultSet = null;
        String categoryInsertString = null;
        String TemplateFileInsertString = null;
        String TemplateDescriptorInsertString = null;
        try {
            // Use the connection to create the SQL statement.
            statement = connection.createStatement();

            // Build the sql strings to insert the rows for each table
            //
            String[] newCategories = null;
            newCategories = _filterExistingCategories(statement, template.getCategories());

            // Add Any New Categories
            //
            if(newCategories != null)
            for(String newCat : newCategories)
                    statement.executeUpdate("INSERT into dbo.IC_TEMPLATE_CATEGORY (CategoryName) values ('" + newCat + "')");

            // Add the new Template File
            //
            statement.executeUpdate("INSERT into dbo.IC_TEMPLATE_FILE (Filename,FileSize,StorageId) values ('" +
                            Filename + "','" + size + "','" + storageId + "')",Statement.RETURN_GENERATED_KEYS);

            // pull out the ID of the newly created template file
            //
            resultSet = statement.getGeneratedKeys();

            while(resultSet.next())
                templateFileId = resultSet.getInt(1);

            // Add the Template Descriptor
            //
            String sql = "Insert into dbo.IC_TEMPLATE_DESCRIPTOR (Name,Description,Units,Contributor, FileId)" +
                    "values('" + template.getTemplateName() +
                    "','" + template.getDescription() +
                    "','" + template.getUnits() +
                    "','" + template.getContributor() +
                    "','" +templateFileId + "')";
            statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            resultSet = statement.getGeneratedKeys();
            while(resultSet.next())
                newTemplateId = resultSet.getInt(1);
            // TODO: Connect the categories up to the template descriptors, until this is done categorization will not work
            //
            // statement.executeUpdate("INSERT INTO IC_TEMPLATE_TO_CATEGORY ...
            connection.commit();
        } catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            try {
                System.out.println("Database Transaction Failure: addTemplate");
                connection.rollback();
            } catch (SQLException innerex) {
                // no nothing here
            }

            throw new DbException(sqlEx,"Add Template to db failed. Transaction rolled back. Template Data:" + template.toString());

        }
        finally
        {
            try {
                if(statement != null) statement.close();
                if(resultSet != null) resultSet.close();
            } catch (Exception innerex) {
                throw new DbException(innerex,"Error Closing Statement in Add Template. Template Data:" + template.toString());
            }
        }
        return templateFileId;
    }

    /**
     * Delete a template from the database. This will delete the template descriptor and the template file
     * as well as any associations to a category
     *
     * @param templateFileId
     * @return true if successful
     */
    @Override
    public boolean deleteTemplateByFileId(long templateFileId) throws DbException {
        // Database should cascade the delete
        // so just delete the template descriptor
        //
        Statement statement = null;

        try {
            statement = connection.createStatement();

            statement.executeUpdate("DELETE from IC_TEMPLATE_FILE WHERE ID=" + templateFileId);
            connection.commit();
        }
        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            try {
                System.out.println("Database Transaction Failure: Delete Template");
                connection.rollback();
            } catch (SQLException innerex) {
                // no nothing here
            }

            throw new DbException(sqlEx,"Delete Template from db failed. Transaction rolled back. Template Id:" + templateFileId);

        }
        finally
        {
            try {
                if(statement != null) statement.close();

            } catch (Exception innerex) {
                throw new DbException(innerex,"Error Closing Statement in Delete Template. Template Id:" + templateFileId);
            }
        }
        return false;
    }

    /**
     * Gets a fully hydrated template object from the ID in the descriptor
     *
     * @param templateDescId Id of the template descriptor for the requested template
     * @return fully hydrated Template object
     */
    @Override
    public Template getTemplateByDescriptorId(long templateDescId) throws DbException {
        return null;
    }

    /**
     * Gets a fully hydrated Template object given a storage ID
     *
     * @param templateFileId The id of the storage object
     * @return fully hydrated Template object
     */
    @Override
    public Template getTemplateByFileId(long templateFileId) throws DbException {
        Statement statement = null;
        ResultSet rs = null;
        Template response = null;
        try {
            statement = connection.createStatement();
            TemplateDescriptor.TemplateDescriptorBuilder descriptorBuilder = new TemplateDescriptor.TemplateDescriptorBuilder();
            TemplateFile.TemplateFileBuilder fileBuilder = new TemplateFile.TemplateFileBuilder();
            rs = statement.executeQuery("Select IC_TEMPLATE_DESCRIPTOR.Id, IC_TEMPLATE_DESCRIPTOR.name, IC_TEMPLATE_DESCRIPTOR.description,\n" +
                    "  IC_TEMPLATE_DESCRIPTOR.Units, IC_TEMPLATE_DESCRIPTOR.Contributor, IC_TEMPLATE_FILE.StorageId," +
                    "IC_TEMPLATE_FILE.Filename, IC_TEMPLATE_FILE.ID, IC_TEMPLATE_FILE.FileSize from IC_TEMPLATE_DESCRIPTOR\n" +
                    "JOIN IC_TEMPLATE_FILE ON IC_TEMPLATE_DESCRIPTOR.FileId = IC_TEMPLATE_FILE.ID WHERE IC_TEMPLATE_FILE.ID=" + templateFileId);

            while (rs.next())
            {
                TemplateDescriptor td = descriptorBuilder
                        .templateId(rs.getInt(1))
                        .templateName(rs.getString(2))
                        .description(rs.getString(3))
                        .units(rs.getString(4))
                        .contributer(rs.getString(5))
                        .build();
                TemplateFile tf = fileBuilder
                        .storageId(rs.getString(6))
                        .fileName(rs.getString(7))
                        .id(rs.getInt(8))
                        .fileSize(rs.getLong(9))
                        .build();

                // Should only be 1
                response = new Template(td,tf);

            }
        }
        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            try {
                System.out.println("Database Transaction Failure: Get All Templates");
                connection.rollback();
            } catch (SQLException innerex) {
                // no nothing here
            }

            throw new DbException(sqlEx,"Failed to get all templates");

        }
        finally
        {
            try {
                if(statement != null) statement.close();

            } catch (Exception innerex) {
                throw new DbException(innerex,"Error Closing Statement in Get All Templates");
            }
        }
        return response;
    }

    /**
     * Gets all of the fully hydrated template objects from the data store.
     *
     * @return
     * @throws com.autodesk.ic.content.storage.objects.DbException
     */
    @Override
    public List<Template> getAllTemplates() throws DbException {
        Statement statement = null;
        ResultSet rs = null;
        ArrayList<Template> results = new ArrayList<Template>();
        try {
            statement = connection.createStatement();
            TemplateDescriptor.TemplateDescriptorBuilder descriptorBuilder = new TemplateDescriptor.TemplateDescriptorBuilder();
            TemplateFile.TemplateFileBuilder fileBuilder = new TemplateFile.TemplateFileBuilder();
            rs = statement.executeQuery("Select IC_TEMPLATE_DESCRIPTOR.Id, IC_TEMPLATE_DESCRIPTOR.name, IC_TEMPLATE_DESCRIPTOR.description,\n" +
                    "  IC_TEMPLATE_DESCRIPTOR.Units, IC_TEMPLATE_DESCRIPTOR.Contributor, IC_TEMPLATE_FILE.StorageId," +
                    "IC_TEMPLATE_FILE.Filename, IC_TEMPLATE_FILE.ID, IC_TEMPLATE_FILE.FileSize from IC_TEMPLATE_DESCRIPTOR\n" +
                    "JOIN IC_TEMPLATE_FILE ON IC_TEMPLATE_DESCRIPTOR.FileId = IC_TEMPLATE_FILE.ID");

            while (rs.next())
            {
                TemplateDescriptor td = descriptorBuilder
                        .templateId(rs.getInt(1))
                        .templateName(rs.getString(2))
                        .description(rs.getString(3))
                        .units(rs.getString(4))
                        .contributer(rs.getString(5))
                        .build();
                TemplateFile tf = fileBuilder
                        .storageId(rs.getString(6))
                        .fileName(rs.getString(7))
                        .id(rs.getInt(8))
                        .fileSize(rs.getLong(9))
                        .build();
                results.add(new Template(td,tf));

            }
        }
        catch (SQLException sqlEx)
        {
            sqlEx.printStackTrace();
            try {
                System.out.println("Database Transaction Failure: Get All Templates");
                connection.rollback();
            } catch (SQLException innerex) {
                // no nothing here
            }

            throw new DbException(sqlEx,"Failed to get all templates");

        }
        finally
        {
            try {
                if(statement != null) statement.close();

            } catch (Exception innerex) {
                throw new DbException(innerex,"Error Closing Statement in Get All Templates");
            }
        }
        return results;
    }

    private boolean _checkDbExists()
    {
        ResultSet resultSet = null;    // For the result set, if applicable

        try {

            boolean schemaExists = false;
            resultSet = connection.getMetaData().getTables("ldays", null, "%", null);
            while(resultSet.next())
            {
                String tablename = resultSet.getString(3);
                if(tablename.equals("IC_TEMPLATE_FILE"))
                    return true;
            }
            return false;
        }
        catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        finally
        {
            try
            {
                // Close resources. Leave the connection alone.
                if (null != resultSet) resultSet.close();
            }
            catch (SQLException sqlException)
            {
                // No additional action if close() statements fail.
                return false;
            }
        }
    }

    String[] _filterExistingCategories(Statement stmt, String[] originalSet){
        List<String> newCategories = new ArrayList<String>();

        // TODO: call the db to get the existing set of categories and filter them out of the list
        //

        // Since categories are not hooked up properly, just return an empty set. While this is here
        // no categories will ever get created.
        //
        if(originalSet != null)
            return originalSet;
        else
            return null;
    }

}

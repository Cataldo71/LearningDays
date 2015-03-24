package com.autodesk.ic.content.storage;

import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;

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

            if(!_checkDbExists())
                _createDbSchema();
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
    // Create an entry in the database to ensure database health
    //
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

    private boolean _checkDbExists()
    {
        Statement statement = null;    // For the SQL statement
        ResultSet resultSet = null;    // For the result set, if applicable

        try {
//            // Use the connection to create the SQL statement.
//            statement = connection.createStatement();
//
//            // Execute the statement.
//            resultSet = statement.executeQuery(sqlString);
            boolean schemaExists = false;
            resultSet = connection.getMetaData().getTables("ldays", null, "%", null);
            while(resultSet.next())
            {
                String tablename = resultSet.getString(3);
                if(tablename.equals("IC_TemplateFile"))
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
                if (null != statement) statement.close();
                if (null != resultSet) resultSet.close();
            }
            catch (SQLException sqlException)
            {
                // No additional action if close() statements fail.
                return false;
            }
        }
    }

    private boolean _createDbSchema()
    {
        Statement statement = null;    // For the SQL statement
        ResultSet resultSet = null;    // For the result set, if applicable

        try {
            // Use the connection to create the SQL statement.
            statement = connection.createStatement();

            // Execute the statements
            int ftres = statement.executeUpdate(IC_TEMPLATE_FILE_TABLE_CREATE);
            int tdres = statement.executeUpdate(IC_TEMPLATE_DESCRIPTOR_TABLE_CREATE);



            boolean schemaExists = false;
            resultSet = connection.getMetaData().getCatalogs();
            while(resultSet.next())
            {
                String catName = resultSet.getString("TABLE_CAT");
                System.out.println(catName);

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
                if (null != statement) statement.close();
                if (null != resultSet) resultSet.close();
            }
            catch (SQLException sqlException)
            {
                // No additional action if close() statements fail.
                return false;
            }
        }
    }

    private static String IC_TEMPLATE_DESCRIPTOR_TABLE_CREATE = "CREATE TABLE ldays.IC_TemplateDescriptor" +
            "(" +
            "  Id int PRIMARY KEY NOT NULL," +
            "  Name NVARCHAR(256) NOT NULL," +
            "  Classification NVARCHAR(256) NOT NULL," +
            "  Thumbnail VARBINARY  NOT NULL," +
            "  FileId int FOREIGN KEY references ICTemplateFile(ID)" +
            ");" +
            "GO";

    private static String IC_TEMPLATE_FILE_TABLE_CREATE = "CREATE TABLE IC_TemplateFile" +
            "(\n" +
            "  ID int PRIMARY KEY NOT NULL," +
            "  Filename NVARCHAR NOT NULL ," +
            "  FileSize bigint ," +
            "  StorageId NVARCHAR " +
            ");" +
            "GO;";
}

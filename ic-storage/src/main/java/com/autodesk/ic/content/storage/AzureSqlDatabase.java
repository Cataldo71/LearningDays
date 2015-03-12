package com.autodesk.ic.content.storage;

import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;

/**
 * Created by cataldp on 3/12/2015.
 */

public class AzureSqlDatabase implements IStorage {
    private static String connectionString =
            "jdbc:sqlserver://t107hhh86i.database.windows.net:1433;database=ldays;user=cataldp@t107hhh86i;password=Sb24601!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    // Create an entry in the database to ensure database health
    //
    public String heartbeat()
    {
        // The types for the following variables are
        // defined in the java.sql library.
        Connection connection = null;  // For making the connection
        Statement statement = null;    // For the SQL statement
        ResultSet resultSet = null;    // For the result set, if applicable

        String serverVersion = "Connection Failed";

        try
        {
            // Ensure the SQL Server driver class is available.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection.
            connection = DriverManager.getConnection(connectionString);

            // Define the SQL string.
            String sqlString =
                    "SELECT @@VERSION";

            // Use the connection to create the SQL statement.
            statement = connection.createStatement();

            // Execute the statement.
            resultSet = statement.executeQuery(sqlString);

            serverVersion = "\n\tServer Type: " +  connection.getMetaData().getDatabaseProductName()
                    + "\n\tServer Version: " + connection.getMetaData().getDatabaseProductVersion()
            + "\n\tServer URL: " + connection.getMetaData().getURL();
            // Provide a message when processing is complete.
            System.out.println("Processing complete.");

        }
        // Exception handling
        catch (ClassNotFoundException cnfe)
        {
            serverVersion = cnfe.getMessage();
            System.out.println("ClassNotFoundException " +
                    cnfe.getMessage());
        }
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
                // Close resources.
                if (null != connection) connection.close();
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
}

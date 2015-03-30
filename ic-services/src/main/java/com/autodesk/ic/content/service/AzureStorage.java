package com.autodesk.ic.content.service; /**
 * Created by cataldp on 3/23/2015.
 */

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.file.FileInputStream;


import java.io.File;
import java.io.InputStream;

public class AzureStorage {

    private static final String storageConnectionString =
            "DefaultEndpointsProtocol=https;AccountName=portalvhdsk24ch13b1vchf;AccountKey=sl6ypeaAUbOc7seYiQKPa2FjXmXiFhBcJ1TDYn3Fsa5/N9oHWWB44SFkee8ITbH3YjowNcorAytNfwNh2ZIGYg==";

    private static final String blobContainer = "templates";

    public int StoreFileAsBlob(InputStream is, long length, String storageId ) {
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Get a reference to a container.
            // The container name must be lower case
            CloudBlobContainer container = blobClient.getContainerReference(blobContainer);

            // Create the container if it does not exist.
            container.createIfNotExists();

            // Create or overwrite the storage blob with contents from the input stream
            CloudBlockBlob blob = container.getBlockBlobReference(storageId);

            blob.upload(is, length);

        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }
        return 0;
    }

    public boolean DeleteFile(String storageId)
    {
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference(blobContainer);

            // Retrieve reference to a blob
            CloudBlockBlob blob = container.getBlockBlobReference(storageId);

            // Delete the blob.
            blob.deleteIfExists();
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

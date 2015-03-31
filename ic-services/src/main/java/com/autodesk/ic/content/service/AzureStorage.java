package com.autodesk.ic.content.service; /**
 * Created by cataldp on 3/23/2015.
 */

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import com.microsoft.azure.storage.file.FileInputStream;


import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.*;

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

    public String generateTemporaryAccessToken() {
        String sharedAccessSignature = null;
        try {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference(blobContainer);

            // Create a shared access policy.
            SharedAccessBlobPolicy policy = new SharedAccessBlobPolicy();

            // Create a UTC Gregorian calendar value.
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

            // Specify the current time as the start time for the shared access signature.
            calendar.setTime(new Date());
            policy.setSharedAccessStartTime(calendar.getTime());

            // Use the start time + 1 hour as the end time for the shared access signature.
            calendar.add(Calendar.HOUR, 1);
            policy.setSharedAccessExpiryTime(calendar.getTime());

            // Set READ and WRITE permissions.
            policy.setPermissions(EnumSet.of(SharedAccessBlobPermissions.READ, SharedAccessBlobPermissions.WRITE));


            // Create the container permissions.
            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

            // Turn off public access.
            containerPermissions.setPublicAccess(BlobContainerPublicAccessType.OFF);

            // Set the policy using the values set above.
            containerPermissions.getSharedAccessPolicies().put("mypolicy", policy);
            container.uploadPermissions(containerPermissions);

            // Create a shared access signature for the container.
            sharedAccessSignature = container.generateSharedAccessSignature(policy, null);

            System.out.println("The shared access signature was created.");
        }
        catch (URISyntaxException e)
        {
            System.out.println("URISyntaxException encountered.");
            e.printStackTrace();
        }
        catch (InvalidKeyException e)
        {
            System.out.println("InvalidKeyException encountered.");
            e.printStackTrace();
        }
        catch (StorageException e)
        {
            System.out.println("StorageException encountered.");
            e.printStackTrace();
        }
        catch (Exception e)
        {
            System.out.println("Exception encountered.");
            e.printStackTrace();
        }
        return sharedAccessSignature;

    }

    // Right now just give read access to the whole container
    //
    public String generateDownloadToken(String storageId)
    {
        String sharedAccessSignature = null;
        try {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference(blobContainer);

            // Create a shared access policy.
            SharedAccessBlobPolicy policy = new SharedAccessBlobPolicy();

            // Create a UTC Gregorian calendar value.
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));

            // Specify the current time as the start time for the shared access signature.
            calendar.setTime(new Date());
            policy.setSharedAccessStartTime(calendar.getTime());

            // Use the start time + 2 minute as the end time for the shared access signature.
            calendar.add(Calendar.MINUTE, 2);
            policy.setSharedAccessExpiryTime(calendar.getTime());

            // Set READ
            policy.setPermissions(EnumSet.of(SharedAccessBlobPermissions.READ));


            // Create the container permissions.
            BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

            // Turn off public access.
            containerPermissions.setPublicAccess(BlobContainerPublicAccessType.OFF);

            // Set the policy using the values set above.
            containerPermissions.getSharedAccessPolicies().put("mypolicy", policy);
            container.uploadPermissions(containerPermissions);

            // Create a shared access signature for the container.
            sharedAccessSignature = container.generateSharedAccessSignature(policy, null);

            System.out.println("The shared access signature was created.");
        }
        catch (URISyntaxException e)
        {
            System.out.println("URISyntaxException encountered.");
            e.printStackTrace();
        }
        catch (InvalidKeyException e)
        {
            System.out.println("InvalidKeyException encountered.");
            e.printStackTrace();
        }
        catch (StorageException e)
        {
            System.out.println("StorageException encountered.");
            e.printStackTrace();
        }
        catch (Exception e)
        {
            System.out.println("Exception encountered.");
            e.printStackTrace();
        }
        return sharedAccessSignature;
    }
}

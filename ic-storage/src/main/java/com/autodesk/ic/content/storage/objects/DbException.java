package com.autodesk.ic.content.storage.objects;

/**
 * Created by cataldp on 3/26/2015.
 */
public class DbException extends Exception {
    private String developerMessage;


    public DbException(Exception source, String devMessage) {
        super(source);
        developerMessage = devMessage;
    }

}

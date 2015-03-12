package com.autodesk.ic.content.storage;

/**
 * Created by cataldp on 3/11/2015.
 */
public class BerkeleydbStorage implements IStorage {
    public String heartbeat() {
        return "not connected";
    }
}

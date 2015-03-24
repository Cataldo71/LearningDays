package com.autodesk.ic.content.storage;

/**
 * Created by cataldp on 3/11/2015.
 */
public class BerkeleydbStorage implements IStorage {
    public boolean initialize()
    {
        // Not implemented
        return false;
    }
    public boolean close()
    {
        // not implemented
        return false;
    }
    public String heartbeat() {
        return "BerkeleyDb storage injected but not implemented";
    }
}

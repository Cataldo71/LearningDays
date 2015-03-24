/**
 * Created by cataldp on 3/11/2015.
 */
package com.autodesk.ic.content.storage;
public interface IStorage {

    public String heartbeat();

    public boolean initialize();

    public boolean close();
}

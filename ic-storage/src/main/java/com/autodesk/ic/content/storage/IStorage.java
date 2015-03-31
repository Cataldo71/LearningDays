/**
 * Created by cataldp on 3/11/2015.
 */
package com.autodesk.ic.content.storage;

import com.autodesk.ic.content.storage.objects.DbException;
import com.autodesk.ic.content.storage.objects.Template;
import com.autodesk.ic.content.storage.objects.TemplateDescriptor;

import java.util.List;

public interface IStorage {

    /**
     * Check the pulse of the db. Make a connection and execute a transaction to ensure the db
     * is operational
     * @return string representing the database version.
     */
    public String heartbeat();

    /**
     * Called to create the initial cached connection to the database
      * @return true if success, false if no connection can be created.
     */
    public boolean initialize();

    /**
     * Closes the database connection for the given database. A call to initialize
     * must be made to open a new connection to the same database.
     * @return
     */
    public boolean close();

    /***************************
     * CRUD OPERATIONS
     ***************************/
    /**
     *
     * @return
     */
    public List<TemplateDescriptor> getAllTemplateDescriptors() throws DbException;

    /**
     * Get a single template descriptor given an ID
     * @param templateDescId Id of the template descriptor
     * @return the template descriptor with the given ID
     * @throws DbException
     */
    public TemplateDescriptor getTemplateDescriptor(long templateDescId) throws DbException;

    /**
     * Gets a set of template descriptors given a set of template ids
     * @param templateDescIds collection of template Id's to retrieve
     * @return collection of template descriptors
     * @throws DbException
     */
    public List<TemplateDescriptor> getTemplateDescriptors(long[] templateDescIds) throws DbException;

    /**
     * Gets all of the templates in a given category
     * @param categoryName the name of the category
     * @return collection of template descriptors with a relationship to the given category
     * @throws DbException
     */
    public List<TemplateDescriptor> getTemplateDescriptorsInCategory(String categoryName) throws DbException;

    /**
     * Gets all of the templates from a given contributor
     * @param contributor the name of the contributor
     * @return a collection of templates that were uploaded by the given contributor
     * @throws DbException
     */
    public List<TemplateDescriptor> getTemplateDescriptorsByContributor(String contributor) throws DbException;

    /**
     * Adds a single new template to the database. Function assumes that the file has already been stored in
     * storage!
     * @param template The template descriptor that defines the meta data for this new template
     * @param storageId Id of the record in whatever storage backend is being used
     * @param Filename name of the physical file
     * @param size size of the file in bytes
     * @return the TemplateDescriptorId that was created
     */
    public long addTemplate(TemplateDescriptor template, String storageId, String Filename, long size) throws DbException;

    /**
     * Delete a template from the database. This will delete the template descriptor and the template file
     * as well as any associations to a category
     * @param templateFileId
     * @return true if successful
     */
    public boolean deleteTemplateByFileId(long templateFileId) throws DbException;

    /**
     * Gets a fully hydrated template object from the ID in the descriptor
     * @param templateDescId Id of the template descriptor for the requested template
     * @return fully hydrated Template object
     */
    public Template getTemplateByDescriptorId(long templateDescId) throws DbException;

    /**
     * Gets a fully hydrated Template object given a storage ID
     * @param templateFileId The id of the storage object
     * @return fully hydrated Template object
     */
    public Template getTemplateByFileId(long templateFileId) throws DbException;

    /**
     * Gets all of the fully hydrated template objects from the data store.
     *
     * @return
     * @throws DbException
     */
    public List<Template> getAllTemplates() throws DbException;
}

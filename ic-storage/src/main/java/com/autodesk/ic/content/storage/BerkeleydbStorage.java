package com.autodesk.ic.content.storage;

import com.autodesk.ic.content.storage.objects.DbException;
import com.autodesk.ic.content.storage.objects.Template;
import com.autodesk.ic.content.storage.objects.TemplateDescriptor;

import java.util.List;

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

    /**
     * Get a single template descriptor given an ID
     *
     * @param templateDescId Id of the template descriptor
     * @return the template descriptor with the given ID
     * @throws com.autodesk.ic.content.storage.objects.DbException
     */
    @Override
    public TemplateDescriptor GetTemplateDescriptor(long templateDescId) throws DbException {
        throw new DbException(new UnsupportedOperationException("Operation Not Supported"), "Berkely Db storage provider not implemented");
    }

    /**
     * Gets a set of template descriptors given a set of template ids
     *
     * @param templateDescIds collection of template Id's to retrieve
     * @return collection of template descriptors
     * @throws com.autodesk.ic.content.storage.objects.DbException
     */
    @Override
    public List<TemplateDescriptor> GetTemplateDescriptors(long[] templateDescIds) throws DbException {
        throw new DbException(new UnsupportedOperationException("Operation Not Supported"), "Berkely Db storage provider not implemented");
    }

    /**
     * Gets all of the templates in a given category
     *
     * @param categoryName the name of the category
     * @return collection of template descriptors with a relationship to the given category
     * @throws com.autodesk.ic.content.storage.objects.DbException
     */
    @Override
    public List<TemplateDescriptor> GetTemplateDescriptorsInCategory(String categoryName) throws DbException {
        throw new DbException(new UnsupportedOperationException("Operation Not Supported"), "Berkely Db storage provider not implemented");
    }

    /**
     * Gets all of the templates from a given contributor
     *
     * @param contributor the name of the contributor
     * @return a collection of templates that were uploaded by the given contributor
     * @throws com.autodesk.ic.content.storage.objects.DbException
     */
    @Override
    public List<TemplateDescriptor> GetTemplateDescriptorsByContributor(String contributor) throws DbException {
        throw new DbException(new UnsupportedOperationException("Operation Not Supported"), "Berkely Db storage provider not implemented");
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
    public long AddTemplate(TemplateDescriptor template, String storageId, String Filename, long size) throws DbException {
        throw new DbException(new UnsupportedOperationException("Operation Not Supported"), "Berkely Db storage provider not implemented");
    }

    /**
     * Gets a fully hydrated template object from the ID in the descriptor
     *
     * @param templateDescId Id of the template descriptor for the requested template
     * @return fully hydrated Template object
     */
    @Override
    public Template GetTemplateByDescriptorId(long templateDescId) throws DbException {
        throw new DbException(new UnsupportedOperationException("Operation Not Supported"), "Berkely Db storage provider not implemented");
    }

    /**
     * Gets a fully hydrated Template object given a storage ID
     *
     * @param templateFileId The id of the storage object
     * @return fully hydrated Template object
     */
    @Override
    public Template GetTemplateByFileId(long templateFileId) throws DbException {
        throw new DbException(new UnsupportedOperationException("Operation Not Supported"), "Berkely Db storage provider not implemented");
    }

    /**
     * Delete a template from the database. This will delete the template descriptor and the template file
     * as well as any associations to a category
     *
     * @param templateDescId
     * @return true if successful
     */
    @Override
    public boolean DeleteTemplate(long templateDescId) throws DbException {
        return false;
    }
}

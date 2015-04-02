package com.autodesk.ic.content.rest.objects;

import java.net.URL;

/**
 * Created by cataldp on 3/11/2015.
 */
public class ICTemplate {
    // Descriptor
    //
    private String description;
    private String[] categories;
    private String templateName;
    private String contributor;
    private String units;
    private URL thumbnailUrl;

    //File
    private long templateId; // Template File is the parent class so it will be the id for the template aggregation
    private String storageId;
    private String fileName;
    private String uploadToken;
    private String downloadToken;
    private long fileSize;

    protected ICTemplate(String description, String[] categories, String templateName, String contributer, String units, URL thumbnailUrl, long templateId, String storageId, String fileName, String uploadToken, String downloadToken, long size) {
        this.description = description;
        this.categories = categories;
        this.templateName = templateName;
        this.contributor = contributer;
        this.units = units;
        this.thumbnailUrl = thumbnailUrl;
        this.templateId = templateId;
        this.storageId = storageId;
        this.fileName = fileName;
        this.uploadToken = uploadToken;
        this.downloadToken = downloadToken;
        this.fileSize = size;
    }

    public String getDescription() {
        return description;
    }

    public String[] getCategories() {
        return categories;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getContributor() {
        return contributor;
    }

    public String getUnits() {
        return units;
    }

    public URL getThumbnailUrl() {
        return thumbnailUrl;
    }

    public long getTemplateId() {
        return templateId;
    }

    public String getStorageId() {
        return storageId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUploadToken() {
        return uploadToken;
    }

    public String getDownloadToken() {
        return downloadToken;
    }

    public long getFileSize() {
        return fileSize;
    }

    public static class ICTemplateBuilder
    {
        // Descriptor
        //
        private String _description;
        private String[] _categories;
        private String _templateName;
        private String _contributor;
        private String _units;
        private URL _thumbnailUrl;

        //File
        private long _templateId; // Template File is the parent class so it will be the id for the template aggregation
        private String _storageId;
        private String _fileName;
        private String _uploadToken;
        private String _downloadToken;
        private long _fileSize;

        public ICTemplateBuilder() {}

        public ICTemplate build() {
            return new ICTemplate(_description,_categories,
                    _templateName, _contributor,_units,_thumbnailUrl,_templateId,_storageId,_fileName,
                    _uploadToken,_downloadToken, _fileSize);
        }

        public ICTemplateBuilder description(String _description) {
            this._description = _description;
            return this;
        }

        public ICTemplateBuilder categories(String[] _categories) {
            this._categories = _categories;
            return this;
        }

        public ICTemplateBuilder templateName(String _templateName) {
            this._templateName = _templateName;
            return this;
        }

        public ICTemplateBuilder contributer(String _contributer) {
            this._contributor = _contributer;
            return this;
        }

        public ICTemplateBuilder units(String _units) {
            this._units = _units;
            return this;
        }

        public ICTemplateBuilder thumbnailUrl(URL _thumbnailUrl) {
            this._thumbnailUrl = _thumbnailUrl;
            return this;
        }

        public ICTemplateBuilder templateId(long _templateId) {
            this._templateId = _templateId;
            return this;
        }
        public ICTemplateBuilder fileSize(long _fileSize) {
            this._fileSize = _fileSize;
            return this;
        }
        public ICTemplateBuilder storageId(String _storageId) {
            this._storageId = _storageId;
            return this;
        }

        public ICTemplateBuilder fileName(String _fileName) {
            this._fileName = _fileName;
            return this;
        }

        public ICTemplateBuilder uploadToken(String _uploadToken) {
            this._uploadToken = _uploadToken;
            return this;
        }

        public ICTemplateBuilder downloadToken(String _downloadToken) {
            this._downloadToken = _downloadToken;
            return this;
        }
    }

}

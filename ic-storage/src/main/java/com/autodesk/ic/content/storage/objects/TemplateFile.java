package com.autodesk.ic.content.storage.objects;

/**
 * Created by cataldp on 3/16/2015.
 */
public class TemplateFile {

    private long id;
    private String fileName;
    private long fileSize;
    private String storageId;
    private String accessToken;

    public long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getStorageId() {
        return storageId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    protected TemplateFile(long id, String fileName, long fileSize, String storageId, String accessToken) {
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.storageId = storageId;
        this.accessToken = accessToken;
    }

    public static class TemplateFileBuilder {
        private long _id;
        private String _fileName;
        private long _fileSize;
        private String _storageId;
        private String _accessToken;

        public TemplateFile build() {
            return new TemplateFile(_id,_fileName,_fileSize,_storageId,_accessToken);
        }

        public TemplateFileBuilder id(long _Id) {
            this._id = _Id;
            return this;
        }

        public TemplateFileBuilder fileName(String _fileName) {
            this._fileName = _fileName;
            return this;
        }

        public TemplateFileBuilder fileSize(long _fileSize) {
            this._fileSize = _fileSize;
            return this;
        }

        public TemplateFileBuilder storageId(String _storageId) {
            this._storageId = _storageId;
            return this;
        }

        public TemplateFileBuilder accessToken(String _accessToken) {
            this._accessToken = _accessToken;
            return this;
        }
    }
}

package com.autodesk.ic.content.service.objects;

import java.io.InputStream;

/**
 * Created by cataldp on 3/29/2015.
 */
public class CreateNewTemplateRequest {

    public String getDescription() {
        return description;
    }

    public String[] getCategories() {
        return categories;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContributer() {
        return contributer;
    }

    public String getUnits() {
        return units;
    }

    public long getFileSize() {
        return fileSize;
    }

    public InputStream getFileStream() {
        return fileStream;
    }

    private String description;
    private String[] categories;
    private String name;
    private String contributer;
    private String units;
    private long fileSize;
    private String fileName;
    private InputStream fileStream;

    private CreateNewTemplateRequest(InputStream fileStream,
                                    String description,
                                    String[] categories,
                                    String name,
                                    String contributer,
                                    String units,
                                    long fileSize,
                                    String fileName) {

        this.description = description;
        this.categories = categories;
        this.name = name;
        this.contributer = contributer;
        this.units = units;
        this.fileSize = fileSize;
        this.fileStream = fileStream;
        this.fileName = fileName;

    }

    public static class CreateNewTemplateRequestBuilder {

        private String _description;
        private String[] _categories;
        private String _name;
        private String _contributer;
        private String _units;
        private long _fileSize;
        private String _fileName;
        private InputStream _fileStream;

        public CreateNewTemplateRequestBuilder()
        {

        }
        public CreateNewTemplateRequest build()
        {
            return new CreateNewTemplateRequest(_fileStream, _description,
                    _categories,
                    _name,
                    _contributer,
                    _units, _fileSize, _fileName);
        }
        public CreateNewTemplateRequestBuilder description(String description) {
            this._description = description;
            return this;
        }

        public CreateNewTemplateRequestBuilder categories(String[] categories) {
            this._categories = categories;
            return this;
        }

        public CreateNewTemplateRequestBuilder name(String name) {
            this._name = name;
            return this;
        }

        public CreateNewTemplateRequestBuilder contributer(String contributer) {
            this._contributer = contributer;
            return this;
        }

        public CreateNewTemplateRequestBuilder units(String units) {
            this._units = units;
            return this;
        }

        public CreateNewTemplateRequestBuilder fileSize(long fileSize) {
            this._fileSize = fileSize;
            return this;
        }

        public CreateNewTemplateRequestBuilder fileStream(InputStream fileStream) {
            this._fileStream = fileStream;
            return this;
        }

        public CreateNewTemplateRequestBuilder fileName(String _fileName) {
            this._fileName = _fileName;
            return this;
        }
    }
}

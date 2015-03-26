package com.autodesk.ic.content.storage.objects;

import java.io.InvalidObjectException;
import java.net.URI;

/**
 * Created by cataldp on 3/16/2015.
 * Aggregate class for data contained in both TemplateFile and TemplateDescriptor
 */
public class Template {

    private TemplateFile templateFile;
    private String description;
    private String[] categories;
    private String name;
    private String contributer;

    private String units;
    private long fileSize; // Get this from the templateFile

    protected Template(TemplateFile templateFile, String description, String[] categories, String name, String contributer, String units, long fileSize) {
        this.templateFile = templateFile;
        this.description = description;
        this.categories = categories;
        this.name = name;
        this.contributer = contributer;
        this.units = units;
        this.fileSize = fileSize;
    }

    public TemplateFile getTemplateFile() {
        return templateFile;
    }

    public String getDescription() {
        return description;
    }

    public String[] getCategories() {
        return categories;
    }

    public String getName() {
        return name;
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

    public static class TemplateBuilder
    {
        private TemplateFile nestedtemplateFile;
        private String nesteddescription;
        private String[] nestedcategories;
        private String nestedname;
        private String nestedcontributer;
        private String nestedunits;
        private long nestedfileSize;

        /**
         * Builder's build method. builds a new Template from collected data.
         * TODO: Validate data here!
         * @return Fully hydrated template
         */
        public Template Build()
        {
            return new Template(nestedtemplateFile, nesteddescription, nestedcategories, nestedname,
                    nestedcontributer,nestedunits, nestedfileSize);
        }

        /**
         * Sets the fully hydrated Template File
         * @param nestedtemplateFile TemplateFile object from the database
         * @return this builder
         */
        public TemplateBuilder templateFile(TemplateFile nestedtemplateFile) {
            this.nestedtemplateFile = nestedtemplateFile;
            return this;
        }

        /**
         * Description of this template
         * @param nesteddescription string description
         * @return this builder
         */
        public TemplateBuilder description(String nesteddescription) {
            this.nesteddescription = nesteddescription;
            return this;
        }

        /**
         * String values of all of the categories that this template belongs to.
         * @param nestedcategories collection of strings representing the categories
         * @return this builder
         */
        public TemplateBuilder categories(String[] nestedcategories) {
            this.nestedcategories = nestedcategories;
            return this;
        }

        /**
         * Name of the template
         * @param nestedname string name of the template
         * @return this builder
         */
        public TemplateBuilder templateName(String nestedname) {
            this.nestedname = nestedname;
            return this;
        }

        /**
         * user name of the person who contributed this template
         * @param nestedcontributer string value for the user name
         * @return this builder
         */
        public TemplateBuilder contributer(String nestedcontributer) {
            this.nestedcontributer = nestedcontributer;
            return this;
        }

        /**
         * The units that this template file uses.
         * @param nestedunits string representing the units
         * @return this builder
         */
        public TemplateBuilder units(String nestedunits) {
            this.nestedunits = nestedunits;
            return this;
        }

        /**
         * Size, in bytes, of the template file
         * @param nestedfileSize long value of the file size
         * @return this builder
         */
        public TemplateBuilder fileSize(long nestedfileSize) {
            this.nestedfileSize = nestedfileSize;
            return this;
        }


    }
}

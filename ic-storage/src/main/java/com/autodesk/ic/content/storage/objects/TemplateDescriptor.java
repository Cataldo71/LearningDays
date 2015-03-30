package com.autodesk.ic.content.storage.objects;

/**
 * Created by cataldp on 3/16/2015.
 */

/**
 * Lightweight descriptor only for template meta data.
 */
public class TemplateDescriptor {
    private String description;
    private String[] categories;
    private String templateName;
    private String contributer;
    private String units;

    public String getDescription() {
        return description;
    }

    public String[] getCategories() {
        return categories;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getContributer() {
        return contributer;
    }

    public String getUnits() {
        return units;
    }

    private TemplateDescriptor(String desc, String[] categories, String name, String creator, String units)
    {
        this.description = desc;
        this.categories = categories;
        this.templateName = name;
        this.contributer = creator;
        this.units = units;
    }

    public static class TemplateDescriptorBuilder {
        private String _description;
        private String[] _categories;
        private String _templateName;
        private String _contributer;
        private String _units;

        public TemplateDescriptor build()
        {
            return new TemplateDescriptor(_description,_categories,_templateName,_contributer,_units);
        }

        public TemplateDescriptorBuilder description(String _description) {
            this._description = _description;
            return this;
        }

        public TemplateDescriptorBuilder categories(String[] _categories) {
            this._categories = _categories;
            return this;
        }

        public TemplateDescriptorBuilder templateName(String _templateName) {
            this._templateName = _templateName;
            return this;
        }

        public TemplateDescriptorBuilder contributer(String _contributer) {
            this._contributer = _contributer;
            return this;
        }

        public TemplateDescriptorBuilder units(String _units) {
            this._units = _units;
            return this;
        }
    }
}

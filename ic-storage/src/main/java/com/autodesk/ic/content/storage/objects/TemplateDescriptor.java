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
    private String contributor;
    private String units;
    private long templateId;

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

    public long getTemplateId() {
        return templateId;
    }

    private TemplateDescriptor(String desc, String[] categories, String name, String creator, String units,  long templateId)
    {
        this.description = desc;
        this.categories = categories;
        this.templateName = name;
        this.contributor = creator;
        this.units = units;
        this.templateId = templateId;
    }

    public static class TemplateDescriptorBuilder {
        private String _description;
        private String[] _categories;
        private String _templateName;
        private String _contributor;
        private String _units;
        private long _templateId;

        public TemplateDescriptor build()
        {
            return new TemplateDescriptor(_description,_categories,_templateName, _contributor,_units, _templateId);
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
            this._contributor = _contributer;
            return this;
        }

        public TemplateDescriptorBuilder units(String _units) {
            this._units = _units;
            return this;
        }

        public TemplateDescriptorBuilder templateId(long templateId) {
            this._templateId = templateId;
            return this;
        }
    }
}

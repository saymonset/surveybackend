package com.dto;

/**
 * Created by simon on 5/23/2019.
 */
public class SettingsDTO {
    private boolean isIsCollapsedOnInit = true;
    private CssClassesDTO cssClasses = new CssClassesDTO();
    private TemplatesDTO templates = new TemplatesDTO();


    public boolean isIsCollapsedOnInit() {
        return isIsCollapsedOnInit;
    }

    public void setIsCollapsedOnInit(boolean isCollapsedOnInit) {
        isIsCollapsedOnInit = isCollapsedOnInit;
    }


    public TemplatesDTO getTemplates() {
        return templates;
    }

    public void setTemplates(TemplatesDTO templates) {
        this.templates = templates;
    }

    public CssClassesDTO getCssClasses() {
        return cssClasses;
    }

    public void setCssClasses(CssClassesDTO cssClasses) {
        this.cssClasses = cssClasses;
    }
}


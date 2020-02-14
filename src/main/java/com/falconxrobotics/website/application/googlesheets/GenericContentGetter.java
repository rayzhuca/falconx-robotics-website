package com.falconxrobotics.website.application.googlesheets;

import com.google.api.services.sheets.v4.model.ValueRange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericContentGetter {

    GoogleSheetRepository sheets;

    @Autowired
    public GenericContentGetter(GoogleSheetRepository sheets) {
        this.sheets = sheets;
    }

    public GenericContentGetterValue getAttributes(String title) {
        return getAttributes(title, null);
    }

    public GenericContentGetterValue getAttributes(String title, String valueRenderOption) {
        ValueRange valueRange = sheets.get(title, valueRenderOption);
        return new GenericContentGetterValue(valueRange);
    }
}
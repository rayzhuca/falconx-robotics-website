package com.falconxrobotics.website.application.googlesheets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.google.api.services.sheets.v4.model.ValueRange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContentGetter {

    private GoogleSheetRepository googleSheetRepository;

    @Autowired
    public ContentGetter(GoogleSheetRepository googleSheetRepository) {
        this.googleSheetRepository = googleSheetRepository;
    }

    public HashMap<String, String> getAttributes(String sheetTitle) {
        System.out.println("Getting");
        ValueRange value = googleSheetRepository.get(sheetTitle + "!A:B", null);

        if (value == null) {
            throw new RuntimeException("'value' not excepted to be null.");
        }

        try {
            System.out.println(value.toPrettyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<String, String>();

        for (List<Object> row : value.getValues()) {
            map.put(row.get(0).toString(), row.get(1).toString());
        }

        return map;
    }
}
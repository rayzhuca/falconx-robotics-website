package com.falconxrobotics.website.application.googlesheets;

import java.io.IOException;
import java.util.ArrayList;
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

    /**
     * @return the first and second column of the sheet
     */
    public HashMap<String, String> getAttributes(String sheetTitle) {
        System.out.println("Getting value from gsheets.");
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

    /**
     * Remember that if you just directly use this method for attributes. You would
     * need to do: key[0] because all items are lists regardless if they are
     * singleton.
     * 
     * @return all values of sheet
     */
    public HashMap<String, List<String>> getAllAttributes(String sheetTitle) {
        System.out.println("Getting value from gsheets.");
        ValueRange value = googleSheetRepository.get(sheetTitle, null);

        if (value == null) {
            throw new RuntimeException("'value' not excepted to be null.");
        }

        try {
            System.out.println(value.toPrettyString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashMap<String, List<String>> map = new HashMap<String, List<String>>();

        for (List<Object> row : value.getValues()) {
            map.put(row.get(0).toString(), toStringList(row.subList(1, row.size())));
        }

        return map;
    }

    private List<String> toStringList(List<? extends Object> collection) {
        List<String> output = new ArrayList<String>();
        for (Object value : collection) {
            output.add(value.toString());
        }
        return output;
    }
}
package com.falconxrobotics.website.application.googlesheets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

    public HashMap<String, Object> stripSingletonLists(HashMap<String, List<String>> input) {
        HashMap<String, Object> output = new HashMap<String, Object>();

        for (Entry<String, List<String>> entry : input.entrySet()) {
            if (entry.getValue().size() == 1) {
                output.put(entry.getKey(), entry.getValue().get(0));
            } else {
                output.put(entry.getKey(), entry.getValue());
            }
        }

        return output;
    }

    /**
     * @throws IndexOutOfBoundsException if size of l1 and l2 is not equal
     */
    public <T, V> Map<T, V> zip(List<T> l1, List<V> l2) {
        Map<T, V> output = new HashMap<>();
        for (int i = 0; i < l1.size(); i++) {
            output.put(l1.get(i), l2.get(i));
        }
        return output;
    }

    /**
     * @throws IndexOutOfBoundsException any array in arrays parameter have
     *                                   different lengths
     */
    public String[][] zipArrays(String[]... arrays) {
        String[][] output = new String[arrays[0].length][arrays.length];

        // [a,b,c], [d,e,f]
        // [[a, d], [b, e], [c, f]]

        for (int arraysI = 0; arraysI < arrays.length; arraysI++) {
            for (int i = 0; i < arrays[0].length; i++) {
                output[i][arraysI] = arrays[arraysI][i];
            }
        }

        return output;
    }
}
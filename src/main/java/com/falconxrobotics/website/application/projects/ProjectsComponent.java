package com.falconxrobotics.website.application.projects;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.falconxrobotics.website.application.googlesheets.ContentGetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectsComponent {

    private ContentGetter sheet;

    @Autowired
    public ProjectsComponent(ContentGetter sheet) {
        this.sheet = sheet;
    }

    public HashMap<String, String> getAttributes(String name) throws IOException {
        if (name == null) {
            return new HashMap<String, String>();
        }
        return sheet.getAttributes("project" + name);
    }

    public HashMap<String, List<String>> getAllAttributes(String name) throws IOException {
        if (name == null) {
            return new HashMap<String, List<String>>();
        }
        return sheet.getAllAttributes("project" + name);
    }

    public HashMap<String, Object> getProjectAttributes(String name) throws IOException {
        if (name == null) {
            return new HashMap<String, Object>();
        }
        HashMap<String, List<String>> sheetAttributes = sheet.getAllAttributes("project" + name);

        @SuppressWarnings("unchecked")
        HashMap<String, Object> attributes = stripSingletonLists( // So I don't need to do: key[0]
                (HashMap<String, List<String>>) sheetAttributes.clone());

        attributes.put("abilities", zip(sheetAttributes.get("abilityNames"), sheetAttributes.get("abilityScales")));
        attributes.put("compeitions",
                zipArrays(sheetAttributes.get("compeitionNames").toArray(new String[0]),
                        sheetAttributes.get("compeitionDescriptions").toArray(new String[0]),
                        sheetAttributes.get("compeitionYouTubeLinks").toArray(new String[0])));

        return attributes;
    }

    private HashMap<String, Object> stripSingletonLists(HashMap<String, List<String>> input) {
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
    private <T, V> Map<T, V> zip(List<T> l1, List<V> l2) {
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
    private String[][] zipArrays(String[]... arrays) {
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
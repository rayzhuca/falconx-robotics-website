package com.falconxrobotics.website.application.googlesheets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.api.services.sheets.v4.model.ValueRange;

/**
 * A container for {@link ValueRange}, which implements many operation for easy extensibilty. Intended to return attributes 
 * to be rendered to thymeleaf via {@link org.springframework.ui.Model#addAllAttributes(Map) Model#addAllAttributes(Map)}.
 */
public class GenericContentGetterValue implements Map<String, Object> {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_PURPLE = "\u001B[35m";

    private Map<String, Object> attributes = new HashMap<>(20);

    /**
     * @param valueRange the value range to be converted to a {@link Map} called {@code attributes}
     */
    public GenericContentGetterValue(final ValueRange valueRange) {
        for (final List<Object> row : valueRange.getValues()) {
            if (row == null || row.isEmpty())
                throw new RuntimeException("Data from sheets is expected to not be empty");
            if (row.size() == 1) {
                attributes.put(row.get(0).toString(), null);
            } else {
                attributes.put(row.get(0).toString(), row.subList(1, row.size()));
            }
        }
    }

    /**
     * @param attributes the map to be cloned
     */
    @SuppressWarnings("unchecked")
    public GenericContentGetterValue(final Map<String, ?> attributes) {
        this.attributes = new HashMap<String, Object>((Map<String, Object>) attributes);
    }

    /**
     * Attributes with value that contains a list a single value is replaced with
     * the value. For example: {"key": ["value"]} becomes {"key": "value"}.
     */
    public void unpackSingleton() {
        for (final String key : attributes.keySet()) {
            attributes.compute(key, (k, v) -> {
                if (v instanceof List) {
                    final List<?> list = (List<?>) v;
                    if (list.size() <= 1) {
                        return list.get(0);
                    }
                }
                return v;
            });
        }
    }

    /**
     * The specified value for the attribute will be zipped into a {@link Map}. For
     * example {"key": ["a", "b"]} becomes {"key": {"a": "b"}}.
     */
    public void link(final String... keys) {
        for (final String key : keys) {
            attributes.compute(key, (k, v) -> {
                final List<?> list = (List<?>) v;
                return Map.of(list.get(0), list.get(1));
            });
        }
    }

    /**
     * The transpose function transposes the rows and columns of its argument. For
     * example,
     * 
     * <pre>
     * <code>
     * transpose("key", List.of(List.of(1, 2), List.of(3, 4))); // -> [[1, 3], [2, 4]]
     * </code>
     * </pre>
     * 
     * @param input the rectangular double array to be transposed
     * @return transposed array
     * 
     * @see #transpose(String, String...)
     */
    public void transpose(final String key, final List<List<?>> input) {
        final List<List<?>> output = new ArrayList<>(input.get(0).size());
        for (int i = 0; i < input.get(0).size(); i++) {
            final List<Object> col = new ArrayList<>();
            for (final List<?> row : input) {
                col.add(row.get(i));
            }
            output.add(col);
        }
        attributes.put(key, output);
    }

    /**
     * The transpose function transposes the rows and columns of its argument. For
     * example,
     * 
     * <pre>
     * <code>
     * transpose("key", List.of(List.of(1, 2), List.of(3, 4))); // -> [[1, 3], [2, 4]]
     * </code>
     * </pre>
     * 
     * @param input             the rectangular double array to be transposed
     * @param transpositionKeys the keys of values in {@code attributes}, which will
     *                          be tranposed
     * @return transposed array
     * 
     * @see #transpose(String, List)
     */
    public void transpose(final String key, final String... transpositionKeys) {
        final List<List<?>> input = new ArrayList<>();
        for (final String transPosKey : transpositionKeys) {
            input.add((List<?>) get(transPosKey));
        }
        transpose(key, input);
    }

    /**
     * Packages all values into a list of maps containing the original key and value,
     * which is then added to {@attributes} with the key {@code target}.
     * The key is not included. 
     * 
     * @param target the new key that all values is stored to
     */
    public void packageAll(final String target) {
        List<Map<String, Object>> output = new ArrayList<>();
        attributes.forEach((k, v) -> {
            output.add(Map.of(k, v));
        });
        attributes.put(target, output);
    }

    /**
     * @return {@code attributes}
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Prints itself via {@link java.io.PrintStream#println(Object) println(Object)}.
     */
    public void print() {
        System.out.println(this);
    }

    /**
     * @return a string representation of {@code attributes}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(100);
        builder.append(attributes.hashCode() + " {\n");

        attributes.forEach((k, v) -> {
            builder.append("    " + ANSI_PURPLE + k + ANSI_RESET + ": " + ANSI_CYAN);
            if (v == null) {
                builder.append(ANSI_RED + "null" + ANSI_RESET + ",\n");
            } else if (v instanceof List<?>) {
                builder.append(Arrays.deepToString(((List<?>) v).toArray(new Object[0])) + ANSI_RESET + ",\n");
            } else if (v.getClass().isArray()) {
                builder.append(Arrays.deepToString((Object[]) v) + ANSI_RESET + ",\n");
            } else {
                builder.append(v + ANSI_RESET + ",\n");
            }
        });

        return builder.append("}").toString();
    }

    /**
     * @return the size of {@code attributes}
     */
    @Override
    public int size() {
        return attributes.size();
    }

    /**
     * @return true if {@code attributes}'s size is 0
     */
    @Override
    public boolean isEmpty() {
        return attributes.isEmpty();
    }

    /**
     * Returns true if {@code key} is a key in {@code attributes}. Returns false if
     * not.
     * 
     * @param key the key to be searched
     * @return if it contains {@code key}
     */
    @Override
    public boolean containsKey(final Object key) {
        return attributes.containsKey(key);
    }

    /**
     * Returns true if {@code value} is a value in {@code attributes}. Returns false
     * if not.
     * 
     * @param value the value to be searched
     * @return if {@code value} is in {@code attributes}
     */
    @Override
    public boolean containsValue(final Object value) {
        return attributes.containsValue(value);
    }

    /**
     * @param key the key whose associated value is to be returned
     * @return the value that correspondes to the key of {@code attributes}
     */
    @Override
    public Object get(final Object key) {
        return attributes.get(key);
    }

    /**
     * Puts a new entry to {@code attributes}.
     */
    @Override
    public Object put(final String key, final Object value) {
        return attributes.put(key, value);
    }

    /**
     * Removes a entry from {@code attributes} via the key.
     */
    @Override
    public Object remove(final Object key) {
        return attributes.remove(key);

    }

    /**
     * Copies all the mappings of {@code m}, and puts all of them to
     * {@code attributes}.
     * 
     * @param m the map to be copied to
     */
    @Override
    public void putAll(final Map<? extends String, ? extends Object> m) {
        attributes.putAll(m);
    }

    /**
     * Removes all the mappings of {@code attributes}. {@code attributes} will be empty after the call.
     */
    @Override
    public void clear() {
        attributes.clear();
    }

    /**
     * @return the key set of {@code attributes}
     */
    @Override
    public Set<String> keySet() {
        return attributes.keySet();
    }

    /**
     * @return the values of {@code attributes}
     */
    @Override
    public Collection<Object> values() {
        return attributes.values();
    }

    /**
     * @return the entry set of {@code attributes}
     */
    @Override
    public Set<Entry<String, Object>> entrySet() {
        return attributes.entrySet();
    }
}
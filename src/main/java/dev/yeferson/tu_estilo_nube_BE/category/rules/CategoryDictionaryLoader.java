package dev.yeferson.tu_estilo_nube_BE.category.rules;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Map;

public class CategoryDictionaryLoader {

    private static final String DICTIONARY_PATH = "/category-dictionary.json";

    public static Map<String, CategoryDefinition> loadDictionary() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = CategoryDictionaryLoader.class.getResourceAsStream(DICTIONARY_PATH)) {
            return mapper.readValue(is,
                    mapper.getTypeFactory().constructMapType(Map.class, String.class, CategoryDefinition.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
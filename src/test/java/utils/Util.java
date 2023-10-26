package utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Util {

    public static String jsonStringFromObject(Object jsonObject) {
        if (jsonObject == null) {
            return "";
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String jsonStr;
        try {
            jsonStr = mapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            throw new JacksonConversionException(e.getMessage());
        }
        return jsonStr;
    }

    public static <T> T objectFromJsonString(String json, Class<T> c) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructType(c));
        } catch (JsonProcessingException e) {
            throw new JacksonConversionException(e.getMessage());
        }
    }

    public static <T> T convertToNewObject(Object o, Class<T> c) {
        String json = jsonStringFromObject(o);
        return objectFromJsonString(json, c);
    }
}


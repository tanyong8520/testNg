package net.tany.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

/**
 * 对象序列化
 */
public class JacksonSerializer implements Serializer{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T deserialize(File src, Class<T> klass) {
        if( null == src || !src.exists()){
            return null;
        }

        try {
            T tobj = objectMapper.readValue(src, klass);
            return tobj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T deserialize(String rawStr, Class<T> klass) {
        if( null == rawStr || rawStr.length() == 0){
            return null;
        }

        try {
            T tobj = objectMapper.readValue(rawStr, klass);
            return tobj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

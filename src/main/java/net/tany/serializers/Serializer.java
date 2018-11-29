package net.tany.serializers;

import java.io.File;

public interface Serializer {
    String serialize(Object obj);

    <T> T deserialize(File src, Class<T> klass);

    <T> T deserialize(String rawStr, Class<T> klass);
}

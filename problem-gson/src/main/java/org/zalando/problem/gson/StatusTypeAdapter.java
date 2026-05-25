package org.zalando.problem.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.AllArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.zalando.problem.StatusType;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
final class StatusTypeAdapter extends TypeAdapter<StatusType> {

    private Map<Integer, StatusType> index;

    @Override
    public void write(final JsonWriter out, @Nullable final StatusType status) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public StatusType read(final JsonReader in) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

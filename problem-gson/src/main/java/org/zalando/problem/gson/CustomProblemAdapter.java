package org.zalando.problem.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AllArgsConstructor;
import org.zalando.problem.AbstractThrowableProblem;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import static java.util.Arrays.stream;

@AllArgsConstructor
final class CustomProblemAdapter<T> extends TypeAdapter<T> {

    private final Gson gson;

    private final TypeAdapter<T> delegate;

    private final boolean stackTraces;

    @Override
    public void write(final JsonWriter out, final T value) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void flattenParameters(final JsonObject object) {
        Optional.ofNullable(object.remove("parameters")).map(JsonElement::getAsJsonObject).ifPresent(params -> params.entrySet().forEach(e -> object.add(e.getKey(), e.getValue())));
    }

    @Override
    public T read(final JsonReader in) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

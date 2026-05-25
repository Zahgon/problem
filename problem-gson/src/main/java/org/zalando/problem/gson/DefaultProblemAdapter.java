package org.zalando.problem.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AllArgsConstructor;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import static java.util.Arrays.stream;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
final class DefaultProblemAdapter extends TypeAdapter<ThrowableProblem> {

    private final Gson gson;

    private final boolean stackTraces;

    private final TypeAdapter<String> stringAdapter;

    private final TypeAdapter<URI> uriAdapter;

    private final TypeAdapter<Map<String, Object>> parameters;

    private final TypeAdapter<StatusType> status;

    private final TypeAdapter<ThrowableProblem> cause;

    DefaultProblemAdapter(final Gson gson, final boolean stackTraces) {
        this(gson, stackTraces, gson.getAdapter(String.class), URITypeAdapter.TYPE, gson.getAdapter(new TypeToken<Map<String, Object>>() {
        }), gson.getAdapter(StatusType.class), gson.getAdapter(ThrowableProblem.class).nullSafe());
    }

    @Override
    public void write(final JsonWriter out, final ThrowableProblem problem) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ThrowableProblem read(final JsonReader in) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

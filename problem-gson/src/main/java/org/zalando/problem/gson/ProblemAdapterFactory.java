package org.zalando.problem.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AllArgsConstructor;
import org.apiguardian.api.API;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;
import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;
import static org.apiguardian.api.API.Status.EXPERIMENTAL;
import static org.zalando.problem.gson.URITypeAdapter.TYPE;

/**
 * Problem {@link TypeAdapterFactory}.
 */
@API(status = EXPERIMENTAL)
public final class ProblemAdapterFactory implements TypeAdapterFactory {

    private final boolean stackTraces;

    private final Map<URI, TypeToken<? extends Problem>> subtypes;

    private final StatusTypeAdapter statusAdapter;

    public ProblemAdapterFactory() {
        this(Status.class);
    }

    @SafeVarargs
    public <E extends Enum<?> & StatusType> ProblemAdapterFactory(final Class<? extends E>... statusTypes) {
        this(false, new StatusTypeAdapter(buildIndex(statusTypes)), Collections.emptyMap());
    }

    private ProblemAdapterFactory(final boolean stackTraces, final StatusTypeAdapter statusAdapter, final Map<URI, TypeToken<? extends Problem>> subtypes) {
        this.stackTraces = stackTraces;
        this.statusAdapter = statusAdapter;
        this.subtypes = Collections.unmodifiableMap(subtypes);
    }

    @SafeVarargs
    private static <E extends Enum<?> & StatusType> Map<Integer, StatusType> buildIndex(final Class<? extends E>... types) {
        final Map<Integer, StatusType> index = new HashMap<>();
        for (final Class<? extends E> type : types) {
            for (final E status : type.getEnumConstants()) {
                if (index.containsKey(status.getStatusCode())) {
                    throw new IllegalArgumentException("Duplicate status codes are not allowed");
                }
                index.put(status.getStatusCode(), status);
            }
        }
        return Collections.unmodifiableMap(index);
    }

    public ProblemAdapterFactory withStackTraces() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ProblemAdapterFactory withStackTraces(final boolean stackTraces) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // TODO @CheckReturnValue
    public ProblemAdapterFactory registerSubtype(final URI uri, final Class<? extends Problem> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // TODO @CheckReturnValue
    public ProblemAdapterFactory registerSubType(final URI uri, final TypeToken<? extends Problem> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @AllArgsConstructor(access = PRIVATE)
    private final class ProblemTypeAdapter<T> extends TypeAdapter<T> {

        private final Gson gson;

        private final TypeToken<T> type;

        private final TypeAdapter<ThrowableProblem> defaultAdapter;

        private final TypeAdapter<JsonElement> jsonElementAdapter;

        ProblemTypeAdapter(final Gson gson, final TypeToken<T> type) {
            this(gson, type, new DefaultProblemAdapter(gson, stackTraces), gson.getAdapter(JsonElement.class));
        }

        @Override
        public void write(final JsonWriter out, final T value) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @SuppressWarnings("unchecked")
        private TypeAdapter<T> selectAdapter(final T value) {
            if (value instanceof DefaultProblem) {
                return (TypeAdapter<T>) defaultAdapter;
            } else {
                final Class<T> valueType = (Class<T>) value.getClass();
                return createCustomAdapter(gson, TypeToken.get(valueType));
            }
        }

        @Override
        public T read(final JsonReader in) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private JsonElement parse(final JsonReader reader) throws IOException {
            boolean isEmpty = true;
            try {
                reader.peek();
                isEmpty = false;
                return jsonElementAdapter.read(reader);
            } catch (final EOFException e) {
                if (isEmpty) {
                    return JsonNull.INSTANCE;
                }
                throw new JsonSyntaxException(e);
            }
        }

        @SuppressWarnings("unchecked")
        private TypeAdapter<T> selectAdapter(final JsonObject problem) {
            @Nullable
            final TypeToken<? extends Problem> subType = Optional.ofNullable(problem.get("type")).map(TYPE::fromJsonTree).map(subtypes::get).orElse(null);
            if (subType == null) {
                return (TypeAdapter<T>) defaultAdapter;
            }
            final TypeToken<T> typeClass = (type.getRawType().isAssignableFrom(subType.getRawType()) ? (TypeToken<T>) subType : type);
            return createCustomAdapter(gson, typeClass);
        }

        private TypeAdapter<T> createCustomAdapter(final Gson gson, final TypeToken<T> type) {
            return new CustomProblemAdapter<>(gson, gson.getDelegateAdapter(ProblemAdapterFactory.this, type), stackTraces);
        }
    }
}

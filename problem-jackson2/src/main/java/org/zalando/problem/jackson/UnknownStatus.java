package org.zalando.problem.jackson;

import org.zalando.problem.StatusType;

final class UnknownStatus implements StatusType {

    private final int statusCode;

    UnknownStatus(final int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public int getStatusCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getReasonPhrase() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

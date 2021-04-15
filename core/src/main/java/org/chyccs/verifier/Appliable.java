package org.chyccs.verifier;

@FunctionalInterface
public interface Appliable<T> {
    void apply(final T context);
}

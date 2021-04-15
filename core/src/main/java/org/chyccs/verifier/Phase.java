package org.chyccs.verifier;

import lombok.var;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Phase<T> extends PhaseVerifier<T> {
    protected Phase(Callable<T> context) {
        super(context);
    }

    public Phase<T> expectCount(int count) {
        super.verifyCount(getResult(), count);
        return this;
    }

    public Phase<T> expectCountNot(int count) {
        super.verifyCountNot(getResult(), count);
        return this;
    }

    public <E> Phase<T> expect(E element) {
        super.verify(getResult(), element);
        return this;
    }

    public <E> Phase<T> expectNot(E element) {
        super.verifyNot(getResult(), element);
        return this;
    }

    public <R> Phase<T> expectCount(Function<? super T, ? extends R> mapper, int count) {
        super.verifyCount(mapper.apply(getResult()), count);
        return this;
    }

    public <R> Phase<T> expectCountNot(Function<? super T, ? extends R> mapper, int count) {
        super.verifyCountNot(mapper.apply(getResult()),count);
        return this;
    }

    public <E, R> Phase<T> expect(Function<? super T, ? extends R> mapper, E element) {
        super.verify(mapper.apply(getResult()), element);
        return this;
    }

    public <E, R> Phase<T> expectNot(Function<? super T, ? extends R> mapper, E element) {
        super.verifyNot(mapper.apply(getResult()), element);
        return this;
    }

    public Phase<T> expectMatches(Predicate<? super T> predicate) {
        super.verifyMatches(predicate);
        return this;
    }

    public Phase<T> expectNotMatches(Predicate<? super T> predicate) {
        super.verifyNotMatches(predicate);
        return this;
    }

    public Phase<T> expectMatchesPattern(Pattern pattern) {
        super.verifyMatchesPattern(getResult(), pattern);
        return this;
    }

    public Phase<T> expectNotMatchesPattern(Pattern pattern) {
        super.verifyNotMatchesPattern(getResult(), pattern);
        return this;
    }

    public Phase<T> expectMatchesPattern(String regex, int flag) {
        super.verifyMatchesPattern(getResult(), regex, flag);
        return this;
    }

    public Phase<T> expectNotMatchesPattern(String regex, int flag) {
        super.verifyNotMatchesPattern(getResult(), regex, flag);
        return this;
    }

    public Phase<T> expectMatchesPattern(String regex) {
        super.verifyMatchesPattern(getResult(), regex);
        return this;
    }

    public Phase<T> expectNotMatchesPattern(String regex) {
        super.verifyNotMatchesPattern(getResult(), regex);
        return this;
    }

    public Phase<T> expectMatchesPattern(Function<? super T, ? extends String> mapper, Pattern pattern) {
        super.verifyMatchesPattern(mapper.apply(getResult()), pattern);
        return this;
    }

    public Phase<T> expectNotMatchesPattern(Function<? super T, ? extends String> mapper, Pattern pattern) {
        super.verifyNotMatchesPattern(mapper.apply(getResult()),pattern);
        return this;
    }

    public Phase<T> expectMatchesPattern(Function<? super T, ? extends String> mapper, String regex, int flag) {
        super.verifyMatchesPattern(mapper.apply(getResult()),regex, flag);
        return this;
    }

    public Phase<T> expectNotMatchesPattern(Function<? super T, ? extends String> mapper, String regex, int flag) {
        super.verifyNotMatchesPattern(mapper.apply(getResult()),regex, flag);
        return this;
    }

    public Phase<T> expectMatchesPattern(Function<? super T, ? extends String> mapper, String regex) {
        super.verifyMatchesPattern(mapper.apply(getResult()),regex);
        return this;
    }

    public Phase<T> expectNotMatchesPattern(Function<? super T, ? extends String> mapper, String regex) {
        super.verifyNotMatchesPattern(mapper.apply(getResult()),regex);
        return this;
    }

    public final <R> PhaseWithPrevious<R, Phase<T>> then(Callable<R> callable) throws ExecutionException, InterruptedException {
        var verifier = new PhaseWithPrevious<>(callable, this);
        verifier.execute();
        return verifier;
    }

    public final <R> PhaseWithPrevious<R, Phase<T>> map(Function<? super T, ? extends R> mapper) {
        return new PhaseWithPrevious<>(() -> mapper.apply(getResult()), this);
    }

    public Phase<T> execute() throws ExecutionException, InterruptedException {
        super.run();
        return this;
    }
}

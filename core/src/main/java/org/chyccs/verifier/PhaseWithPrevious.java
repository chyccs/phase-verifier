package org.chyccs.verifier;

import lombok.var;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class PhaseWithPrevious<T,  P extends PhaseVerifier> extends PhaseVerifier<T> {

    private final P previous;

    protected PhaseWithPrevious(Callable<T> context, P previous) {
        super(context);
        this.previous = previous;
    }

    public PhaseWithPrevious<T, P> expectCount(int count) {
        super.verifyCount(getResult(), count);
        return this;
    }

    public PhaseWithPrevious<T, P> expectCountNot(int count) {
        super.verifyCountNot(getResult(), count);
        return this;
    }

    public <E> PhaseWithPrevious<T, P> expect(E element) {
        super.verify(getResult(), element);
        return this;
    }

    public <E> PhaseWithPrevious<T, P> expectNot(E element) {
        super.verifyNot(getResult(), element);
        return this;
    }

    public <R> PhaseWithPrevious<T, P> expectCount(Function<? super T, ? extends R> mapper, int count) {
        super.verifyCount(mapper.apply(getResult()), count);
        return this;
    }

    public <R> PhaseWithPrevious<T, P> expectCountNot(Function<? super T, ? extends R> mapper, int count) {
        super.verifyCountNot(mapper.apply(getResult()), count);
        return this;
    }

    public <E, R> PhaseWithPrevious<T, P> expect(Function<? super T, ? extends R> mapper, E element) {
        super.verify(mapper.apply(getResult()), element);
        return this;
    }

    public <E, R> PhaseWithPrevious<T, P> expectNot(Function<? super T, ? extends R> mapper, E element) {
        super.verifyNot(mapper.apply(getResult()), element);
        return this;
    }

    public PhaseWithPrevious<T, P> expectMatches(Predicate<? super T> predicate) {
        super.verifyMatches(predicate);
        return this;
    }

    public PhaseWithPrevious<T, P> expectNotMatches(Predicate<? super T> predicate) {
        super.verifyNotMatches(predicate);
        return this;
    }

    public PhaseWithPrevious<T, P> expectMatchesPattern(Pattern pattern) {
        super.verifyMatchesPattern(getResult(), pattern);
        return this;
    }

    public PhaseWithPrevious<T, P> expectNotMatchesPattern(Pattern pattern) {
        super.verifyNotMatchesPattern(getResult(), pattern);
        return this;
    }

    public PhaseWithPrevious<T, P> expectMatchesPattern(String regex, int flag) {
        super.verifyMatchesPattern(getResult(), regex, flag);
        return this;
    }

    public PhaseWithPrevious<T, P> expectNotMatchesPattern(String regex, int flag) {
        super.verifyNotMatchesPattern(getResult(), regex, flag);
        return this;
    }

    public PhaseWithPrevious<T, P> expectMatchesPattern(String regex) {
        super.verifyMatchesPattern(getResult(), regex);
        return this;
    }

    public PhaseWithPrevious<T, P> expectNotMatchesPattern(String regex) {
        super.verifyNotMatchesPattern(getResult(), regex);
        return this;
    }

    public PhaseWithPrevious<T, P> expectMatchesPattern(Function<? super T, ? extends String> mapper, Pattern pattern) {
        super.verifyMatchesPattern(mapper.apply(getResult()), pattern);
        return this;
    }

    public PhaseWithPrevious<T, P> expectNotMatchesPattern(Function<? super T, ? extends String> mapper, Pattern pattern) {
        super.verifyNotMatchesPattern(mapper.apply(getResult()),pattern);
        return this;
    }

    public PhaseWithPrevious<T, P> expectMatchesPattern(Function<? super T, ? extends String> mapper, String regex, int flag) {
        super.verifyMatchesPattern(mapper.apply(getResult()),regex, flag);
        return this;
    }

    public PhaseWithPrevious<T, P> expectNotMatchesPattern(Function<? super T, ? extends String> mapper, String regex, int flag) {
        super.verifyNotMatchesPattern(mapper.apply(getResult()),regex, flag);
        return this;
    }

    public PhaseWithPrevious<T, P> expectMatchesPattern(Function<? super T, ? extends String> mapper, String regex) {
        super.verifyMatchesPattern(mapper.apply(getResult()),regex);
        return this;
    }

    public PhaseWithPrevious<T, P> expectNotMatchesPattern(Function<? super T, ? extends String> mapper, String regex) {
        super.verifyNotMatchesPattern(mapper.apply(getResult()),regex);
        return this;
    }

    public P revert() {
        return previous;
    }

    public P revert(Appliable<T> appliable) {
        appliable.apply(getResult());
        return previous;
    }

    public P thenPrevious(Appliable<T> appliable) {
        return revert(appliable);
    }

    public <R> PhaseWithPrevious<R, PhaseWithPrevious<T, P>> then(Callable<R> callable) throws ExecutionException, InterruptedException {
        var verifier = new PhaseWithPrevious<>(callable, this);
        verifier.execute();
        return verifier;
    }

    public final <R> PhaseWithPrevious<R, PhaseWithPrevious<T, P>> map(Function<? super T, ? extends R> mapper) {
        return new PhaseWithPrevious<>(() -> mapper.apply(getResult()), this);
    }

    public PhaseWithPrevious<T, P> execute() throws ExecutionException, InterruptedException {
        super.run();
        return this;
    }
}

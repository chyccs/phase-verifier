package org.chyccs.verifier;

import lombok.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class PhaseVerifier<T> {

    private final Callable<T> context;
    private T result;

    public static <R> Phase<R> execute(Callable<R> callable) throws ExecutionException, InterruptedException {
        var verifier = new Phase(callable);
        verifier.execute();
        return verifier;
    }

    protected void run() throws ExecutionException, InterruptedException {
        ExecutorService execService = Executors.newCachedThreadPool();
        Future<T> result = execService.submit(getContext());
        setResult(result.get());
    }

    protected <R> void verifyCount(R result, int count) {
        assertTrue(hasSize(count).matches(convertToCollection(result)));
    }

    protected <R> void verifyCountNot(R result, int count) {
        assertFalse(hasSize(count).matches(convertToCollection(result)));
    }

    private Collection<Object> convertToCollection(Object obj) {
        Collection<Object> collection = null;
        if (obj.getClass().isArray()) {
            collection = Arrays.stream(((Object[])obj)).collect(Collectors.toList());
        } else if (obj instanceof Collection) {
            collection = (Collection<Object>)obj;
        } else
            return null;
        return collection;
    }

    private boolean isSame(Object obj1, Object obj2) {
        System.out.println(obj1 + "/" + obj2);
        if (obj1.getClass() != obj2.getClass()) {
            return false;
        }

        Collection<Object> listObject1 = convertToCollection(obj1);
        Collection<Object> listObject2 = convertToCollection(obj2);;

        if (listObject1 == null || listObject2 == null) {
            return obj1.equals(obj2);
        }

        Collection<Object> finalListObject = listObject2;
        return listObject1.stream().allMatch(o -> finalListObject.contains(o));
    }

    protected <E,R> void verify(R result, E element) {
        assertTrue(isSame(result, element));
    }

    protected <E,R> void verifyNot(R result, E element) {
        assertFalse(isSame(result, element));
    }

    protected void verifyMatches(Predicate<? super T> predicate) {
        assertTrue(predicate.test(result));
    }

    protected void verifyNotMatches(Predicate<? super T> predicate) {
        assertFalse(predicate.test(result));
    }

    protected <R> void verifyMatchesPattern(R result, Pattern pattern) {
        System.out.println(matchesPattern(pattern).matches((String)result));
        assertTrue(matchesPattern(pattern).matches(result));
    }

    protected <R> void verifyNotMatchesPattern(R result, Pattern pattern) {
        assertFalse(matchesPattern(pattern).matches(result));
    }

    protected <R> void verifyMatchesPattern(R result, String regex, int flag) {
        verifyMatchesPattern(result, Pattern.compile(regex, flag));
    }

    protected <R> void verifyNotMatchesPattern(R result, String regex, int flag) {
        verifyNotMatchesPattern(result, Pattern.compile(regex, flag));
    }

    protected <R> void verifyMatchesPattern(R result, String regex) {
        verifyMatchesPattern(result, Pattern.compile(regex));
    }

    protected <R> void verifyNotMatchesPattern(R result, String regex) {
        verifyNotMatchesPattern(result, Pattern.compile(regex));
    }
}

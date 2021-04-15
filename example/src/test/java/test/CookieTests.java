package test;

import org.junit.jupiter.api.Test;
import org.chyccs.verifier.PhaseVerifier;
import java.util.concurrent.ExecutionException;

public class CookieTests {
    @Test
    public void testSuccess() throws ExecutionException, InterruptedException {
        assert true;
        PhaseVerifier.execute(() -> "butter").expectNot("cream").expect("butter");
    }
    @Test
    public void testFailure() throws ExecutionException, InterruptedException {
        assert false;
        PhaseVerifier.execute(() -> "butter").expect("cream").expectNot("butter");
    }
}

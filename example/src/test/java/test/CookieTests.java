package test;

import org.junit.jupiter.api.Test;
import org.chyccs.verifier.PhaseVerifier;
import java.util.concurrent.ExecutionException;

public class CookieTests {
    @Test
    public void testSuccess() throws ExecutionException, InterruptedException {
        PhaseVerifier.execute(() -> "butter").expectNot("cream").expect("butter");
    }
}

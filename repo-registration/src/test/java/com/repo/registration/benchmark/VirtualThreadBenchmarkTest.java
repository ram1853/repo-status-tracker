package com.repo.registration.benchmark;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Benchmark harness for virtual thread comparison.
 * Runs only on benchmark profile.
 * The stub simulates 300ms I/O delay without making real network calls.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("benchmark")
@Disabled("This is disabled so it doesn't run during build. Remove this annotation, when you want to perform the benchmark test.")
public class VirtualThreadBenchmarkTest {

    private static final int BENCHMARK_DURATION_MS = 120_000; // 2 minutes - adjust as needed

    @Test
    void runBenchmark() throws InterruptedException {
        System.out.println("=== Benchmark server started - run JMeter now ===");
        System.out.println("=== Server will shut down in " + (BENCHMARK_DURATION_MS / 1000) + " seconds ===");
        Thread.sleep(BENCHMARK_DURATION_MS);
        System.out.println("=== Benchmark complete ===");
    }
}

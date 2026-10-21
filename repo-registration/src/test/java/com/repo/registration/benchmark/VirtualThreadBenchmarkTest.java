package com.repo.registration.benchmark;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD
import org.springframework.test.context.ActiveProfiles;

/**
 * Benchmark harness for virtual thread comparison.
 * Runs only on benchmark profile.
 * The stub simulates 300ms I/O delay without making real network calls.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("benchmark")
=======

/**
 * Benchmark harness for virtual thread comparison.
 * Set 'benchmark.test' to true in application.yaml to run this test.
 * The stub simulates 300ms I/O delay without making real network calls.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
>>>>>>> a19b37bc30b633154055f31fc4209a12b45f4a92
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

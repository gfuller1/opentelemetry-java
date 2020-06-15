package io.opentelemetry.example.metrics;

import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.metrics.LongValueObserver;
import io.opentelemetry.metrics.LongValueObserver.ResultLongValueObserver;
import io.opentelemetry.metrics.Meter;

/**
 * Example of using {@link LongValueObserver} to measure execution time of method.
 * Setting {@link LongValueObserver.Callback} a callback that gets executed every collection interval.
 * Useful for expensive measurements that would be wastefully to calculate each request.
 */
public class LongValueObserverExample {

  public static void main(String[] args) {
    Meter sampleMeter = OpenTelemetry.getMeterProvider()
        .get("io.opentelemetry.example.metrics", "0.5");
    LongValueObserver observer = sampleMeter.longValueObserverBuilder("jvm.memory.total")
        .setDescription("Reports JVM memory usage.")
        .setUnit("byte")
        .build();

    observer.setCallback(
        new LongValueObserver.Callback<LongValueObserver.ResultLongValueObserver>() {
          @Override
          public void update(ResultLongValueObserver result) {
            result.observe(Runtime.getRuntime().totalMemory());
          }
        });
    // someWork();
  }
}
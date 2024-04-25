package com.lvitaly.loom;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Virtual {
  
  public void simplestExample() {
    Thread.startVirtualThread(
      () -> System.out.println(Thread.currentThread())
    );
  }
  
  /* 
  Threads
    platform vs OS - 1:1
    virtual  vs OS - M:N
    green    vs OS - N:1 (early days of Java)
   */
  public void execute() {
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      IntStream.range(0, 10_000).parallel().forEach(i ->
        executor.submit(() -> {
          Thread.sleep(Duration.ofSeconds(1));
          System.out.println(i);
          return i;
        })
      );
    }
  }
  
  public static void main(String[] args) {
    final var main = new Virtual();

    final var start = Instant.now();

    main.execute();

    final var elapsed = Duration.between(start, Instant.now()).toMillis();

    System.out.printf("""
      Duration = %s ms
      """, elapsed);
  }

}

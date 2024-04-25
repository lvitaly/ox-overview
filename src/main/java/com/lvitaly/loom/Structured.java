package com.lvitaly.loom;

import com.lvitaly.loom.model.Response;
import com.lvitaly.loom.service.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class Structured implements Service {

  Response handle() throws ExecutionException, InterruptedException {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
      final var user  = scope.fork(this::findUser); 
      final var order = scope.fork(this::fetchOrder);
      
      scope.join();           // Join both forks
      scope.throwIfFailed();  // ... and propagate errors

      // Here, both forks have succeeded, so compose their results
      return new Response(user.get(), order.get());
    }
  }

  public static void main(String[] args) {
    final var main = new Structured();

    Response res = null;

    final var start = Instant.now();

    try {
      res = main.handle();
    } catch (ExecutionException | InterruptedException e) {
      System.out.println("Error: " + e.getMessage());
    }

    final var elapsed = Duration.between(start, Instant.now()).toMillis();

    System.out.printf("""
      Result   = %s
      Duration = %s ms
      """, res, elapsed);
  }
}

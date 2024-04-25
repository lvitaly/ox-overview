package com.lvitaly.loom;

import com.lvitaly.loom.model.Response;
import com.lvitaly.loom.service.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Unstructured implements Service {

  /**
   * The java.util.concurrent.ExecutorService API, introduced in Java 5, helps developers execute subtasks concurrently.
   */
  private final ExecutorService esvc = Executors.newCachedThreadPool();

  public Response handle() throws ExecutionException, InterruptedException {
    Future<String>  user  = esvc.submit(this::findUser);
    Future<Integer> order = esvc.submit(this::fetchOrder);
    
    String theUser  = user.get();   // Join findUser
    int    theOrder = order.get();  // Join fetchOrder
    
    return new Response(theUser, theOrder);
  }
  
  public void shutdown() {
    esvc.shutdown();
  }

  public static void main(String[] args) {
    final var main = new Unstructured();
    
    Response res = null;
    
    final var start = Instant.now();
    
    try {
      res = main.handle();
    } catch (ExecutionException | InterruptedException e) {
      System.out.println("Error: " + e.getMessage());
    } finally {
      main.shutdown();
    }
    
    final var elapsed = Duration.between(start, Instant.now()).toMillis();

    System.out.printf("""
      Result   = %s
      Duration = %s ms
      """, res, elapsed);
  }
}

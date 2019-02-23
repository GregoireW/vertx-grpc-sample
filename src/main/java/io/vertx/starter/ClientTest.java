package io.vertx.starter;

import io.grpc.ManagedChannel;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxChannelBuilder;
import io.vertx.starter.proto.GreeterGrpc;
import io.vertx.starter.proto.HelloRequest;

public class ClientTest extends AbstractVerticle {

  public static void main(String argv[]) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(ClientTest.class.getName());
  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    ManagedChannel channel = VertxChannelBuilder
      .forAddress(vertx, "localhost", 8080)
      .usePlaintext(true)
      .build();


    GreeterGrpc.GreeterVertxStub stub = GreeterGrpc.newVertxStub(channel);

    HelloRequest request = HelloRequest.newBuilder().setName("you").build();

    stub.sayHello(reques

      t, ar -> {
      if (ar.succeeded())
        System.out.println("OK: " + ar.result().getMessage());
      } else {
        System.out.println("Error: " + ar.cause().getMessage());
      }
      vertx.close();
    });
  }
}

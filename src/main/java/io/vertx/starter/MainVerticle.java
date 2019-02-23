package io.vertx.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;
import io.vertx.starter.proto.GreeterGrpc;
import io.vertx.starter.proto.HelloReply;
import io.vertx.starter.proto.HelloRequest;

public class MainVerticle extends AbstractVerticle {

  public static void main(String argv[]){
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(MainVerticle.class.getName());
  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    GreeterGrpc.GreeterVertxImplBase service = new GreeterGrpc.GreeterVertxImplBase() {
      @Override
      public void sayHello(HelloRequest request, Future<HelloReply> future) {
        future.complete(HelloReply.newBuilder().setMessage("Hello "+request.getName()).build());
      }
    };


    VertxServer rpcServer = VertxServerBuilder
      .forAddress(vertx, "localhost", 8080)
      .addService(service)
      .build();

    rpcServer.start();
    startFuture.complete();

  }

}

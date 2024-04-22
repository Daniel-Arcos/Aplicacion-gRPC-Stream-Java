package grpcholamundostream.client;

import com.proto.saludo.ArchivoServiceGrpc;
import com.proto.saludo.SaludoServiceGrpc;
import com.proto.saludo.Holamundo.ArchivoRequest;
import com.proto.saludo.Holamundo.SaludoRequest;
import com.proto.saludo.Holamundo.SaludoResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 8080;

        ManagedChannel ch = ManagedChannelBuilder
            .forAddress(host, puerto)
            .usePlaintext()
            .build();

        // saludarUnario(ch);

        //saludarStream(ch);
        archivoStream(ch);

        System.out.println("Apagando...");
        ch.shutdown();
    }

    public static void saludarUnario (ManagedChannel ch) {
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        SaludoRequest peticion = SaludoRequest.newBuilder().setNombre("Daniel").build();

        SaludoResponse respuesta = stub.saludo(peticion);

        System.out.println("Respuesta RPC: " + respuesta.getResultado());
    }

    public static void saludarStream(ManagedChannel ch) {
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        SaludoRequest peticion = SaludoRequest.newBuilder().setNombre("Daniel").build();

        stub.saludoStream(peticion).forEachRemaining(respuesta -> {
            System.out.println("Respuesta RPC: " + respuesta.getResultado());
        });
    }

    public static void archivoStream(ManagedChannel ch) {
        ArchivoServiceGrpc.ArchivoServiceBlockingStub stub = ArchivoServiceGrpc.newBlockingStub(ch);
        ArchivoRequest peticion = ArchivoRequest.newBuilder().setNombre("archivote.csv").build();

        stub.archivoStream(peticion).forEachRemaining(respuesta -> {
            System.out.println(respuesta.getLinea());
        });
    }
}

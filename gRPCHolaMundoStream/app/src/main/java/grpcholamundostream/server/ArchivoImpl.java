package grpcholamundostream.server;

import java.util.Scanner;

import com.proto.saludo.ArchivoServiceGrpc.ArchivoServiceImplBase;
import com.proto.saludo.Holamundo.ArchivoRequest;
import com.proto.saludo.Holamundo.ArchivoResponse;

import io.grpc.stub.StreamObserver;

public class ArchivoImpl extends ArchivoServiceImplBase {

    @Override
    public void archivoStream(ArchivoRequest request, StreamObserver<ArchivoResponse> responseObserver) {
        String archivoNombre = "/" + request.getNombre();
        System.out.println("Enviando el archivo: " + ArchivoImpl.class.getResource(archivoNombre + "..."));
        try (Scanner scanner = new Scanner(ArchivoImpl.class.getResourceAsStream(archivoNombre))) {
            while (scanner.hasNextLine()) {
                ArchivoResponse respuesta = ArchivoResponse.newBuilder().setLinea(scanner.nextLine()).build();
                System.out.print(".");

                responseObserver.onNext(respuesta);
            }
        } finally {
            responseObserver.onCompleted();
            System.out.println("\nEnvio de archivo completado.");
        }
    }
    
}

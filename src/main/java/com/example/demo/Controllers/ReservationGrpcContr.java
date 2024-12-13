package com.example.demo.Controllers;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import com.example.demo.Service.ReservationService;
import com.example.demo.entities.Reservation;
import com.example.demo.grpc.*;
import net.devh.boot.grpc.server.service.GrpcService;
import com.example.demo.grpc.ReservationServiceGrpc;


import java.time.LocalDate;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class ReservationGrpcContr extends ReservationServiceGrpc.ReservationServiceImplBase {

    private final ReservationService reservationService;

    @Override
    public void getReservationById(GetReservationByIdRequest request, StreamObserver<GetReservationByIdResponse> responseObserver) {
        Reservation reservation = reservationService.getReservation(request.getId());
        GetReservationByIdResponse response = GetReservationByIdResponse.newBuilder()
                .setReservation(toGrpcReservation(reservation))
                .build();

        // Send response and complete the call
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private com.example.demo.grpc.Reservation toGrpcReservation(Reservation reservation) {
        return com.example.demo.grpc.Reservation.newBuilder()
                .setId(reservation.getId())
                .setClientName(reservation.getClientName())
                .setRoomType(reservation.getRoomType())
                .setStartDate(reservation.getStartDate().toString())
                .setEndDate(reservation.getEndDate().toString())
                .build();
    }

    @Override
    public void getReservations(GetReservationsRequest request, StreamObserver<GetReservationsResponse> responseObserver) {
        // Fetch all reservations using service
        GetReservationsResponse response = GetReservationsResponse.newBuilder()
                .addAllReservations(reservationService.getAllReservations().stream()
                        .map(this::toGrpcReservation)
                        .collect(Collectors.toList()))
                .build();

        // Send response and complete the call
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createReservation(CreateReservationRequest request, StreamObserver<CreateReservationResponse> responseObserver) {
        Reservation reservation = reservationService.createReservation(
                request.getClientName(),
                request.getRoomType(),
                LocalDate.parse(request.getStartDate()),
                LocalDate.parse(request.getEndDate())
        );


        CreateReservationResponse response = CreateReservationResponse.newBuilder()
                .setReservation(toGrpcReservation(reservation))
                .build();


        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteReservation(DeleteReservationRequest request, StreamObserver<DeleteReservationResponse> responseObserver) {

        boolean success = reservationService.getReservation(request.getId()) != null;

        if (success) {
            reservationService.deleteReservation(request.getId());
        }


        DeleteReservationResponse response = DeleteReservationResponse.newBuilder()
                .setSuccess(success)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }




}

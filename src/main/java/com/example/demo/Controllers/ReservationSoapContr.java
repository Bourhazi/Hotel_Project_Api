package com.example.demo.Controllers;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import com.example.demo.Service.ReservationService;
import com.example.demo.entities.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@WebService(serviceName = "ReservWS")
public class ReservationSoapContr {
    private final ReservationService reservationService;

    public ReservationSoapContr(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @WebMethod
    public List<Reservation> getReservations() {
        return reservationService.getAllReservations();
    }

    @WebMethod
    public Reservation createReservation(
            @WebParam(name = "clientName") String clientName,
            @WebParam(name = "roomType") String roomType,
            @WebParam(name = "startDate") String startDate,
            @WebParam(name = "endDate") String endDate) {

        return reservationService.createReservation(
                clientName,
                roomType,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate));
    }
    @WebMethod
    public Reservation getReservationById(@WebParam(name = "id") Long id) {
        return reservationService.getReservation(id);
    }

    @WebMethod
    public boolean deleteReservation(@WebParam(name = "id") Long id) {
        reservationService.deleteReservation(id);
        return true;
    }
}

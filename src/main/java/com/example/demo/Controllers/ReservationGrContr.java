package com.example.demo.Controllers;

import com.example.demo.Service.ReservationService;
import com.example.demo.entities.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
public class ReservationGrContr {

    private final ReservationService reservationService;

    @QueryMapping
    public List<Reservation> allReservations() {
        return reservationService.getAllReservations();
    }

    @QueryMapping
    public Reservation reservationById(Long id) {
        return reservationService.getReservation(id);
    }

    @MutationMapping
    public Reservation createReservation(String clientName, String roomType, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return reservationService.createReservation(clientName, roomType, start, end);
    }
    @MutationMapping
    public boolean deleteReservation(Long id) {
        reservationService.deleteReservation(id);
        return true;
    }
}

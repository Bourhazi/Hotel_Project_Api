package com.example.demo.Service;

import com.example.demo.Repository.ReservationRepository;
import com.example.demo.entities.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public Reservation createReservation(String clientName, String roomType, LocalDate startDate, LocalDate endDate) {
        Reservation reservation = new Reservation();
        reservation.setClientName(clientName);
        reservation.setRoomType(roomType);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        return repository.save(reservation);
    }

    public Reservation getReservation(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found for id: " + id));
    }

    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    public Reservation updateReservation(Long id, String clientName, String roomType, LocalDate startDate, LocalDate endDate) {
        Reservation reservation = getReservation(id);
        reservation.setClientName(clientName);
        reservation.setRoomType(roomType);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        return repository.save(reservation);
    }

    public void deleteReservation(Long id) {
        repository.deleteById(id);
    }
}

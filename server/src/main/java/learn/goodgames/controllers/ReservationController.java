package learn.goodgames.controllers;

import learn.goodgames.domain.ReservationService;
import learn.goodgames.models.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reservation> findAllReservations() {
        return service.findAllReservations();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> findReservationById(@PathVariable int reservationId) {
        Reservation reservation = service.findReservationById(reservationId);
        if (reservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reservation);
    }
}

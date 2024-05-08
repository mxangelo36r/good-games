package learn.goodgames.domain;

import learn.goodgames.data.ReservationRepository;
import learn.goodgames.models.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<Reservation> findAllReservations() { return repository.findAllReservations(); }

    public Reservation findReservationById(int reservationId) { return repository.findReservationById(reservationId); }
}

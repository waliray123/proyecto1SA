package com.eatsleep.reservation.reservation.infrastructure.outputadapters.db;

import com.eatsleep.reservation.common.OutputAdapter;
import com.eatsleep.reservation.reservation.domain.Reservation;
import com.eatsleep.reservation.reservation.infrastructure.outputports.db.GetReservationByIdReservationOutputPort;
import com.eatsleep.reservation.reservation.infrastructure.outputports.db.MakeReservationOutputPort;
import com.eatsleep.reservation.reservation.infrastructure.outputports.db.GetReservationByUserHotelOutputPort;
import com.eatsleep.reservation.reservation.infrastructure.outputports.db.UpdateReservationOutputPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@OutputAdapter
public class ReservationDbOutputAdapter implements MakeReservationOutputPort,GetReservationByUserHotelOutputPort
    ,GetReservationByIdReservationOutputPort, UpdateReservationOutputPort{
    
    private ReservationDbEntityRepository reservationDbEntityRepository;

    @Autowired
    public ReservationDbOutputAdapter(ReservationDbEntityRepository reservationDbEntityRepository) {
        this.reservationDbEntityRepository = reservationDbEntityRepository;
    }
    
    @Override
    public Reservation makeReservation(Reservation reservation) {
        ReservationDbEntity reservationEntity = ReservationDbEntity.fromDomainToEntity(reservation);
        return reservationDbEntityRepository.save(reservationEntity).toDomainModel();
    }

    @Override
    public List<Reservation> findReservationByUserHotel(String idUser, String idHotel) {
        List<ReservationDbEntity> reservationEntities = reservationDbEntityRepository.findAllByUserAndIdLocation(idUser, idHotel);
        return reservationEntities.stream()
                                  .map(ReservationDbEntity::toDomainModel)
                                  .collect(Collectors.toList());
    }

    @Override
    public Optional<Reservation> findReservationByIdReservation(String idReservation) {
        return reservationDbEntityRepository.findById(idReservation)
                .map(ReservationDbEntity::toDomainModel);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        Optional<ReservationDbEntity> existingReservationOpt = reservationDbEntityRepository.findById(reservation.getId().toString());
    
        if (existingReservationOpt.isPresent()) {
            // Obtener la entidad existente
            ReservationDbEntity existingReservation = existingReservationOpt.get();

            // Actualizar los campos con los valores de la reservación del dominio
            existingReservation.setIdLocation(reservation.getIdLocation().toString());
            existingReservation.setDateStart(reservation.getDateStart());
            existingReservation.setDateEnd(reservation.getDateEnd());
            existingReservation.setTotal(reservation.getTotal());
            existingReservation.setStatus(StatusDbEntity.valueOf(reservation.getStatus().name()));
            existingReservation.setUser(reservation.getUser().toString());

            // Guardar la entidad actualizada en la base de datos
            ReservationDbEntity updatedReservation = reservationDbEntityRepository.save(existingReservation);

            // Devolver la entidad actualizada como un modelo de dominio
            return updatedReservation.toDomainModel();
        } else {
            throw new IllegalArgumentException("Reservation with ID " + reservation.getId() + " not found.");
        }
    }


}

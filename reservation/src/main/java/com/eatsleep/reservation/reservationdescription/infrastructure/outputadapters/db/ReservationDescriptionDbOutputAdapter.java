package com.eatsleep.reservation.reservationdescription.infrastructure.outputadapters.db;

import com.eatsleep.reservation.common.OutputAdapter;
import com.eatsleep.reservation.reservationdescription.domain.ReservationDescription;
import com.eatsleep.reservation.reservationdescription.infrastructure.outputports.db.GetReservationsDescriptionByIdReservation;
import com.eatsleep.reservation.reservationdescription.infrastructure.outputports.db.SaveReservationDescriptionOutputPort;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@OutputAdapter
public class ReservationDescriptionDbOutputAdapter implements SaveReservationDescriptionOutputPort,GetReservationsDescriptionByIdReservation {
    
    private ReservationDescriptionDbEntityRepository reservationDbEntityRepository;

    @Autowired
    public ReservationDescriptionDbOutputAdapter(ReservationDescriptionDbEntityRepository reservationDbEntityRepository) {
        this.reservationDbEntityRepository = reservationDbEntityRepository;
    }    

    @Override
    public ReservationDescription saveReservationDescription(ReservationDescription reservationDescription) {
        ReservationDescriptionDbEntity reservationDescriptionDbEntity = ReservationDescriptionDbEntity.fromDomainToEntity(reservationDescription);
        return this.reservationDbEntityRepository.save(reservationDescriptionDbEntity).toDomainModel();
    }

    @Override
    public Optional<ReservationDescription> findReservationByRoomAndDatePendingConfirmed(String idRoom, LocalDate date) {
        Optional<ReservationDescriptionDbEntity> reservationDescriptionDbEntity = 
            this.reservationDbEntityRepository.findReservationByRoomAndDatePendingConfirmed(idRoom, date);

        return reservationDescriptionDbEntity.map(ReservationDescriptionDbEntity::toDomainModel);
    }

    @Override
    public List<ReservationDescription> getReservationsDescriptionByIdReservation(String idReservation) {
        List<ReservationDescriptionDbEntity> entities = this.reservationDbEntityRepository.findAllByReservationIdNative(idReservation);

        return entities.stream()
                       .map(ReservationDescriptionDbEntity::toDomainModel)
                       .collect(Collectors.toList());
    }


}

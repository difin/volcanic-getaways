package org.difin.volcanic_getaways.reservation.model;

import org.difin.volcanic_getaways.reservation.data.entity.ManagedDate;
import org.difin.volcanic_getaways.reservation.data.entity.Reservation;
import org.difin.volcanic_getaways.reservation.model.internal.CancellationStatus;
import org.difin.volcanic_getaways.reservation.model.internal.UpdateStatus;
import org.difin.volcanic_getaways.reservation.model.out.ActionResult;
import org.difin.volcanic_getaways.reservation.model.out.AvailableDateModel;
import org.difin.volcanic_getaways.reservation.model.out.BookingReference;
import org.difin.volcanic_getaways.reservation.model.out.ReservationModel;
import org.difin.volcanic_getaways.reservation.service.common.DateConversionService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ModelConverter {

    public AvailableDateModel managedDateEntityToDTO(ManagedDate managedDate) {
        return new AvailableDateModel(managedDate.getDate());
    }

    public BookingReference reservationEntityToBookingReferenceDTO(Reservation reservation) {
        return new BookingReference(reservation.getBookingRef());
    }

    public ActionResult cancellationStatusToDTO(CancellationStatus cancellationStatus) {
        return new ActionResult(cancellationStatus.label);
    }

    public ActionResult updateStatusToDTO(UpdateStatus updateStatus) {
        return new ActionResult(updateStatus.label);
    }

    public ReservationModel reservationEntityToReservationDTO(Reservation reservation) {

        return ReservationModel.builder()
                .id(reservation.getId())
                .name(reservation.getName())
                .email(reservation.getEmail())
                .bookingRef(reservation.getBookingRef())
                .reservedDates(reservation.getReservedDates()
                        .stream()
                        .map(t -> t.getManagedDate().getDate())
                        .sorted()
                        .map(DateConversionService::dateToString)
                        .collect(Collectors.toList()))
                .build();
    }
}
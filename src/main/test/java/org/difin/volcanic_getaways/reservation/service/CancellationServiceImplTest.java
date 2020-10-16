package org.difin.volcanic_getaways.reservation.service;

import org.difin.volcanic_getaways.reservation.data.entity.Reservation;
import org.difin.volcanic_getaways.reservation.data.repository.ReservationRepository;
import org.difin.volcanic_getaways.reservation.model.request.BookingReferencePayload;
import org.difin.volcanic_getaways.reservation.model.internal.CancellationStatus;
import org.difin.volcanic_getaways.reservation.service.reservation.CancellationServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CancellationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private CancellationServiceImpl cancellationService;

    private int id = 1;
    private String name = "some name";
    private String email = "some email";
    private String bookingRef = "some booking reference";

    @DisplayName("When cancelling existing reservation then it gets cancelled successfully")
    @Test
    void cancellingExistingReservationTest(){

        BookingReferencePayload payload = new BookingReferencePayload(bookingRef);

        Reservation reservation = Reservation.builder()
                .id(id).name(name).email(email).bookingRef(bookingRef).reservedDates(Lists.emptyList()).build();

        when(reservationRepository.findByBookingRef(bookingRef)).thenReturn(reservation);

        CancellationStatus actual = cancellationService.cancelReservationInExistingTx(payload);

        assertEquals(CancellationStatus.SUCCESS, actual);
    }

    @DisplayName("When attempting to cancel a reservation that doesn't exist and error status is returned")
    @Test
    void attemptingToCancelNotExistentReservationTest(){

        BookingReferencePayload payload = new BookingReferencePayload(bookingRef);

        when(reservationRepository.findByBookingRef(bookingRef)).thenReturn(null);

        CancellationStatus actual = cancellationService.cancelReservationInExistingTx(payload);

        assertEquals(CancellationStatus.NOT_FOUND, actual);
    }
}
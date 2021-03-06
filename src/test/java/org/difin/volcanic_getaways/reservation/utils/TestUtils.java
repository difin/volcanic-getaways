package org.difin.volcanic_getaways.reservation.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.difin.volcanic_getaways.reservation.model.request.BookingDates;
import org.difin.volcanic_getaways.reservation.model.request.ReservationPayload;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class TestUtils {

    private final Faker faker = new Faker();
    private final Random random = new Random();
    private ObjectMapper objectMapper = new ObjectMapper();

    public ReservationPayload generateReservationPayload(int numPossibleDateRanges) {

        int stayLength = random.ints(1,4).findFirst().getAsInt();

        return generateReservationPayload(numPossibleDateRanges, stayLength);
    }

    public ReservationPayload generateReservationPayload(int numPossibleDateRanges, int stayLength) {

        List<BookingDates> bookingDatesList = new ArrayList<>();

        IntStream.range(1, numPossibleDateRanges+1)
                .forEach(dayOffset -> {
                    bookingDatesList.add(new BookingDates(
                            LocalDate.now().plusDays(dayOffset),
                            LocalDate.now().plusDays(dayOffset+stayLength)));
                });

        String name = faker.name().fullName();
        String email = faker.name().firstName() + faker.name().lastName() + "@somewhere.com";

        BookingDates bookingDates = bookingDatesList.get(random.ints(0,numPossibleDateRanges).findFirst().getAsInt());

        return ReservationPayload.builder().name(name).email(email).bookingDates(bookingDates).build();
    }

    public Map<String, String> jsonToMap(String jsonString) {

        try {
            return objectMapper.readValue(jsonString, Map.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}

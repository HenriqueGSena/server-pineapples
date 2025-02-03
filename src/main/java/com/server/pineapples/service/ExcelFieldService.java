package com.server.pineapples.service;

import com.server.pineapples.repository.AirbnbRepository;
import com.server.pineapples.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcelFieldService {

    @Autowired
    private final AirbnbRepository airbnbRepository;

    @Autowired
    private final BookingRepository bookingRepository;

    public ExcelFieldService(AirbnbRepository airbnbRepository, BookingRepository bookingRepository) {
        this.airbnbRepository = airbnbRepository;
        this.bookingRepository = bookingRepository;
    }
}

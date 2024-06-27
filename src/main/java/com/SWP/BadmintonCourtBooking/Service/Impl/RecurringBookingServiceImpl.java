package com.SWP.BadmintonCourtBooking.Service.Impl;

import com.SWP.BadmintonCourtBooking.Dto.Request.RecurringBookingRequest;
import com.SWP.BadmintonCourtBooking.Dto.ResponseCourtDto;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import com.SWP.BadmintonCourtBooking.Repository.SubCourtRepository;
import com.SWP.BadmintonCourtBooking.Service.RecurringBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RecurringBookingServiceImpl implements RecurringBookingService {

    @Autowired
    private SubCourtRepository subCourtRepository;
    @Override
    public ResponseCourtDto checkCourtAvailability(RecurringBookingRequest recurringBookingRequest) {
        List<SubCourt> subCourtList = new ArrayList<>();

        return null;
    }
}
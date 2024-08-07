package com.SWP.BadmintonCourtBooking.Dto;

import com.SWP.BadmintonCourtBooking.Entity.Images;
import com.SWP.BadmintonCourtBooking.Entity.Price;
import com.SWP.BadmintonCourtBooking.Entity.ServiceCourt;
import com.SWP.BadmintonCourtBooking.Entity.SubCourt;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourtDto {
    private Integer courtID;

    private String courtName;

    private String district;

    private String courtAddress;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer courtQuantity;

    private Integer duration;

    private Integer userID;

    private String phone;

    private Integer statusCourt;

    private List<Images> images;

    private List<SubCourt> subCourts;

    private List<Price> price;

    private List<ServiceCourt> serviceCourt;
}

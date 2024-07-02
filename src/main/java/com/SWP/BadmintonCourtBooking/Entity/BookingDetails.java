package com.SWP.BadmintonCourtBooking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "BookingDetails")
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "sub_court_id")
    private SubCourt subCourt;

    @Column(name = "quantity")
    private int Quantity;

    @Column(name = "unit_price")
    private Double UnitPrice;

//    @Column(name = "ServiceID")
//    private String ServiceID;

    @Column(name = "start_time")
    private LocalTime StartTime;
    //private Time StartTime;

    @Column(name = "end_time")
    private LocalTime EndTime;
    //private Time EndTime;
}

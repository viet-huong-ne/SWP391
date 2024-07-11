package com.SWP.BadmintonCourtBooking.Repository;


import com.SWP.BadmintonCourtBooking.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT p FROM Payment p WHERE p.booking.court.courtID = :courtID OR p.recurringBooking.court.courtID = :courtID")
    List<Payment> findByCourtID(@Param("courtID") int courtID);
}


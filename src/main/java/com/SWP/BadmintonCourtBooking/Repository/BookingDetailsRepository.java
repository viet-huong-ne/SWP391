package com.SWP.BadmintonCourtBooking.Repository;

import com.SWP.BadmintonCourtBooking.Entity.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Integer> {
    @Query("SELECT bd FROM BookingDetails bd WHERE bd.booking.BookingID = :bookingID")
    List<BookingDetails> findBookingDetailsByBookingID(@Param("bookingID") int bookingID);

    //    @Query("SELECT bd FROM BookingDetails bd WHERE :endTime < bd.StartTime OR bd.EndTime < :startTime")
//    List<BookingDetails> findOverlappingBookings(@Param("startTime") Time startTime, @Param("endTime") Time endTime);
    @Query("SELECT bd FROM BookingDetails bd WHERE bd.booking.booking_date = :bookingDate AND bd.booking.court.courtID = :courtID AND ((:startTime >= bd.StartTime AND :startTime < bd.EndTime) OR (:endTime > bd.StartTime AND :endTime <= bd.EndTime) OR (:startTime <= bd.StartTime AND :endTime >= bd.EndTime))")
    List<BookingDetails> findExistingTime(@Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime, @Param("courtID") int courtID, @Param("bookingDate") LocalDate bookingDate);

    @Modifying
    @Query(value = "INSERT INTO `badmintoncourtbookingdb`.`recurring_booking` (`end_date`, `end_time`, `start_date`, `start_time`, `court_id`, `user_id`) VALUES (:endDate, :endTime, :startDate, :startTime, :courtId, :userId)", nativeQuery = true)
    @Transactional
    void insertRecurringBooking(@Param("endDate") LocalDate endDate,
                                @Param("endTime") LocalTime endTime,
                                @Param("startDate") LocalDate startDate,
                                @Param("startTime") LocalTime startTime,
                                @Param("courtId") int courtId,
                                @Param("userId") int userId);

    @Query(nativeQuery = true, value = "SELECT LAST_INSERT_ID()")
    int getLastInsertId();

    @Modifying
    @Query(value = "INSERT INTO `badmintoncourtbookingdb`.`recurring_booking_sub_courts` (`recurring_booking_id`, `sub_court_id`) VALUES (:recurringBookingId, :subCourtId)", nativeQuery = true)
    @Transactional
    void insertRecurringBookingSubCourt(@Param("recurringBookingId") int recurringBookingId, @Param("subCourtId") int subCourtId);

    @Modifying
    @Query(value = "INSERT INTO `badmintoncourtbookingdb`.`recurring_booking_days` (`recurring_booking_id`, `day_of_week`) VALUES (:recurringBookingId, :dayOfWeek)", nativeQuery = true)
    @Transactional
    void insertRecurringBookingDay(@Param("recurringBookingId") int recurringBookingId, @Param("dayOfWeek") String dayOfWeek);

    @Query(value = "SELECT rbs.sub_court_id " +
            "FROM recurring_booking_sub_courts rbs " +
            "JOIN recurring_booking rb ON rbs.recurring_booking_id = rb.id " +
            "JOIN recurring_booking_days rbd ON rb.id = rbd.recurring_booking_id " +
            "WHERE rb.court_id = :courtId " +
            "AND rb.start_date >= :startDate " +
            "AND (rb.end_date IS NULL OR rb.end_date <= :endDate) " +
            "AND rbd.day_of_week = :dayOfWeek " +
            "AND rb.start_time >= :startTime " +
            "AND (rb.end_time IS NULL OR rb.end_time <= :endTime)", nativeQuery = true)
    List<Integer> findSubCourtIds(@Param("courtId") int courtId,
                                  @Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate,
                                  @Param("dayOfWeek") String dayOfWeek,
                                  @Param("startTime") LocalTime startTime,
                                  @Param("endTime") LocalTime endTime);
}
//if (endTime.after(x.getStartTime()) || startTime.before(x.getEndTime()) || (startTime.equals(x.getStartTime())) ||
// ((startTime.equals(x.getStartTime()) || startTime.after(x.getStartTime())) & (endTime.equals(x.getEndTime()) || endTime.before(x.getEndTime()))))
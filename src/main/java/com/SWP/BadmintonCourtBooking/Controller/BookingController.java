package com.SWP.BadmintonCourtBooking.Controller;

import com.SWP.BadmintonCourtBooking.Dto.*;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingPaymentRequest;
import com.SWP.BadmintonCourtBooking.Dto.Request.BookingRequest;
import com.SWP.BadmintonCourtBooking.Entity.Booking;
import com.SWP.BadmintonCourtBooking.Service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/booking")
public class BookingController {
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);
    @Autowired
    private BookingService bookingService;

    //API check time frame has been set
    @PostMapping("/check")
    public ResponseCourtDto checkBooking(@RequestBody BookingRequest bookingRequest) {
        if (bookingRequest.getStartTime() == null || bookingRequest.getEndTime() == null) {
            return null;
        }
//        ResponseCourtDto responseCourtDto = bookingService.checkCourtAvailability(bookingRequest);
        ResponseCourtDto responseCourtDto = bookingService.checkSubCourtStatus(bookingRequest);
        return responseCourtDto;
    }

    //API GET LẤY CÁC SÂN NHỎ CÓ THỂ ĐẶT
    @GetMapping("/getCourtStatus")
    public ResponseCourtDto getCourtStatus() {
        return bookingService.getLastAvailabilityCheck();
    }

    //API ĐẶT SÂN THEO NGÀY
    @PostMapping("/book")
    public ResponseEntity<BookingResponseDTO> createPreBooking(@RequestBody BookingDto bookingDto) {
        //log.info("BookingDto: {}", bookingDto);
        BookingResponseDTO booking = bookingService.saveBooking(bookingDto);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
    //API lưu booking khi đã thanh toán thành công
//    @GetMapping("/book/saveBooking/{payCode}")
//    public ResponseEntity<Booking> saveBooking(@RequestParam String payCode) {
//        //log.info("BookingDto: {}", bookingDto);
//        Booking booking = bookingService.saveBookingIfUserPaid(payCode);
//
//        return new ResponseEntity<>(booking, HttpStatus.CREATED);
//    }
    //API lưu booking khi đã thanh toán thành công v2
    //TODO: CREATE Booking
    @PostMapping("/book/saveBookingV2")
    public ResponseEntity<Booking> saveBookingV2(@RequestBody BookingPaymentRequest bookingPaymentRequest) {
        //log.info("BookingDto: {}", bookingDto);
        Booking booking = bookingService.saveBookingIfUserPaid(bookingPaymentRequest);

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }
    //API trả về số totalPrice
    @PostMapping("/provisionalInvoice")
    public ResponseEntity<Double> responseProvisionalInvoice(@RequestBody BookingDto bookingDto) {

        return new ResponseEntity<>(bookingService.preBooking(bookingDto), HttpStatus.OK);
    }

    //API TRẢ VỀ THÔNG TIN BILL ĐÃ ĐẶT
    @GetMapping("/book/bill")
    public ResponseEntity<BookingResponseDTO> bookBill() {
        return new ResponseEntity<>(bookingService.showBill(), HttpStatus.OK);
    }
    //API XEM LỊCH SỬ ĐẶT LỊCH CỦA USER
    @GetMapping("/booked/{userID}")
    public List<Booking> bookingOfUser(@PathVariable Integer userID) {
        List<Booking> bookingList = bookingService.getBooking(userID);
        if (bookingList == null) {
            throw new RuntimeException("No booking found for userID: " + userID);
        }
        return bookingList;
    }


}

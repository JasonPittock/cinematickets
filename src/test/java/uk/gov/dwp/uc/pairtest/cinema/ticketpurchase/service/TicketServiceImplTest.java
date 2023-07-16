package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.cinema.ticketprice.service.TicketPriceService;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketPurchaseResponseDTO;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest.Type;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.exception.InvalidPurchaseException;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

  @Mock
  TicketPaymentService ticketPaymentService;

  @Mock
  SeatReservationService seatReservationService;

  @Mock
  TicketPriceService ticketPriceService;

  @InjectMocks
  TicketServiceImpl ticketService;

  @Test
  @DisplayName("should purchase 1 adult ticket and successfully return TicketPurchaseResponse")
  void shouldPurchase1AdultTicketAndSuccessfullyReturnTicketPurchaseResponse() {

    when(ticketPriceService.getTicketPrice(Type.ADULT)).thenReturn(20);

    doNothing().when(ticketPaymentService).makePayment(anyLong(), anyInt());
    doNothing().when(seatReservationService).reserveSeat(anyLong(), anyInt());

    final TicketPurchaseResponseDTO ticketPurchaseResponse = ticketService.purchaseTickets(
        TicketPurchaseRequest.builder().accountId(1).ticketRequests(
                new TicketRequest[]{TicketRequest.builder().noOfTickets(1).type(Type.ADULT).build()})
            .build());

    assertEquals(1, ticketPurchaseResponse.totalTickets());
    assertEquals(1, ticketPurchaseResponse.totalSeats());
    assertEquals(20, ticketPurchaseResponse.totalCost());
    assertEquals(1L, ticketPurchaseResponse.accountId());

    verify(ticketPriceService, times(1)).getTicketPrice(Type.ADULT);
    verify(ticketPaymentService, times(1)).makePayment(anyLong(), anyInt());
    verify(seatReservationService, times(1)).reserveSeat(anyLong(), anyInt());
  }

  @Test
  @DisplayName("should purchase 1 adult ticket and 1 child ticket and successfully return TicketPurchaseResponse")
  void shouldPurchase1AdultAnd1ChildTicketAndSuccessfullyReturnTicketPurchaseResponse() {

    when(ticketPriceService.getTicketPrice(Type.ADULT)).thenReturn(20);
    when(ticketPriceService.getTicketPrice(Type.CHILD)).thenReturn(10);

    doNothing().when(ticketPaymentService).makePayment(anyLong(), anyInt());
    doNothing().when(seatReservationService).reserveSeat(anyLong(), anyInt());

    final TicketPurchaseResponseDTO ticketPurchaseResponse = ticketService.purchaseTickets(
        TicketPurchaseRequest.builder().accountId(1).ticketRequests(
            new TicketRequest[]{TicketRequest.builder().noOfTickets(1).type(Type.ADULT).build(),
                TicketRequest.builder().noOfTickets(1).type(Type.CHILD).build()}).build());

    assertEquals(2, ticketPurchaseResponse.totalSeats());
    assertEquals(2, ticketPurchaseResponse.totalTickets());
    assertEquals(30, ticketPurchaseResponse.totalCost());
    assertEquals(1L, ticketPurchaseResponse.accountId());

    verify(ticketPriceService, times(1)).getTicketPrice(Type.ADULT);
    verify(ticketPriceService, times(1)).getTicketPrice(Type.CHILD);
    verify(ticketPaymentService, times(1)).makePayment(anyLong(), anyInt());
    verify(seatReservationService, times(1)).reserveSeat(anyLong(), anyInt());

  }

  @Test
  @DisplayName("should purchase 7 adult tickets and 11 child ticket and 3 infant tickets and successfully return TicketPurchaseResponse")
  void shouldPurchase7AdultAnd11ChildTicketsAnd2InfantAndSuccessfullyReturnTicketPurchaseResponse() {

    when(ticketPriceService.getTicketPrice(Type.ADULT)).thenReturn(20);
    when(ticketPriceService.getTicketPrice(Type.CHILD)).thenReturn(10);

    doNothing().when(ticketPaymentService).makePayment(anyLong(), anyInt());
    doNothing().when(seatReservationService).reserveSeat(anyLong(), anyInt());

    final TicketPurchaseResponseDTO ticketPurchaseResponse = ticketService.purchaseTickets(
        TicketPurchaseRequest.builder().accountId(1).ticketRequests(
            new TicketRequest[]{TicketRequest.builder().noOfTickets(7).type(Type.ADULT).build(),
                TicketRequest.builder().noOfTickets(11).type(Type.CHILD).build(),
                TicketRequest.builder().noOfTickets(2).type(Type.INFANT).build()}).build());

    assertEquals(20, ticketPurchaseResponse.totalTickets());
    assertEquals(18, ticketPurchaseResponse.totalSeats());
    assertEquals(250, ticketPurchaseResponse.totalCost());
    assertEquals(1L, ticketPurchaseResponse.accountId());

    verify(ticketPriceService, times(1)).getTicketPrice(Type.ADULT);
    verify(ticketPriceService, times(1)).getTicketPrice(Type.CHILD);
    verify(ticketPaymentService, times(1)).makePayment(anyLong(), anyInt());
    verify(seatReservationService, times(1)).reserveSeat(anyLong(), anyInt());

  }

  @Test
  @DisplayName("should fail to purchase 15 adult and 7 child tickets and throw InvalidPurchaseException")
  void shouldFailToPurchase15AdultAnd7ChildTicketsAndThrowInvalidPurchaseException() {

    final InvalidPurchaseException invalidPurchaseException = assertThrowsExactly(
        InvalidPurchaseException.class, () -> ticketService.purchaseTickets(
            TicketPurchaseRequest.builder().accountId(1).ticketRequests(
                new TicketRequest[]{
                    TicketRequest.builder().noOfTickets(15).type(Type.ADULT).build(),
                    TicketRequest.builder().noOfTickets(7).type(Type.CHILD).build()}).build()));

    assertEquals("Ticket limit exceeded", invalidPurchaseException.getMessage());

    verifyNoInteractions(ticketPaymentService);
    verifyNoInteractions(seatReservationService);

  }

  @Test
  @DisplayName("should fail to purchase 2 child tickets and throw InvalidPurchaseException")
  void shouldFailToPurchase2ChildTicketsAndThrowInvalidPurchaseException() {

    final InvalidPurchaseException invalidPurchaseException = assertThrowsExactly(
        InvalidPurchaseException.class, () -> ticketService.purchaseTickets(
            TicketPurchaseRequest.builder().accountId(1).ticketRequests(
                new TicketRequest[]{TicketRequest.builder().noOfTickets(1).type(Type.CHILD).build(),
                    TicketRequest.builder().noOfTickets(1).type(Type.CHILD).build()}).build()));

    assertEquals("No adult ticket exists", invalidPurchaseException.getMessage());

    verifyNoInteractions(ticketPaymentService);
    verifyNoInteractions(seatReservationService);


  }

  @Test
  @DisplayName("should fail to purchase 2 infant tickets and throw InvalidPurchaseException")
  void shouldFailToPurchase2InfantTicketsAndThrowInvalidPurchaseException() {

    final InvalidPurchaseException invalidPurchaseException = assertThrowsExactly(
        InvalidPurchaseException.class, () -> ticketService.purchaseTickets(
            TicketPurchaseRequest.builder().accountId(1).ticketRequests(
                new TicketRequest[]{
                    TicketRequest.builder().noOfTickets(1).type(Type.INFANT).build(),
                    TicketRequest.builder().noOfTickets(1).type(Type.INFANT).build()}).build()));

    assertEquals("No adult ticket exists", invalidPurchaseException.getMessage());

    verifyNoInteractions(ticketPaymentService);
    verifyNoInteractions(seatReservationService);


  }

  @Test
  @DisplayName("should fail with accountId less than 0 and throw InvalidPurchaseException")
  void shouldFailWithAccountIdLessThan0AndThrowInvalidPurchaseException() {

    final InvalidPurchaseException invalidPurchaseException = assertThrowsExactly(
        InvalidPurchaseException.class, () -> ticketService.purchaseTickets(
            TicketPurchaseRequest.builder().accountId(-50).ticketRequests(
                    new TicketRequest[]{
                        TicketRequest.builder().noOfTickets(1).type(Type.ADULT).build()})
                .build()));

    assertEquals("Invalid account Id", invalidPurchaseException.getMessage());

    verifyNoInteractions(ticketPaymentService);
    verifyNoInteractions(seatReservationService);


  }

  @Test
  @DisplayName("should fail with valid accountId but empty ticket requests and throw InvalidPurchaseException")
  void shouldFailWithValidAccountIdButEmptyTicketRequestsAndThrowInvalidPurchaseException() {

    final InvalidPurchaseException invalidPurchaseException = assertThrowsExactly(
        InvalidPurchaseException.class, () -> ticketService.purchaseTickets(
            TicketPurchaseRequest.builder().accountId(50).ticketRequests(
                    new TicketRequest[]{})
                .build()));

    assertEquals("Empty ticket requests", invalidPurchaseException.getMessage());

    verifyNoInteractions(ticketPaymentService);
    verifyNoInteractions(seatReservationService);


  }

}
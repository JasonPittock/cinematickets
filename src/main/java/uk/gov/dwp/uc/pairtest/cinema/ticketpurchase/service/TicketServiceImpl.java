package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.service;

import static uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest.Type.ADULT;
import static uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest.Type.INFANT;

import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.cinema.ticketprice.service.TicketPriceService;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketPurchaseResponseDTO;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketResponseDTO;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.exception.InvalidPurchaseException;

/**
 * Should only have private methods other than the one below.
 */
public class TicketServiceImpl implements TicketService {

  private final TicketPaymentService ticketPaymentService;
  private final SeatReservationService seatReservationService;

  private final TicketPriceService ticketPriceService;

  public TicketServiceImpl(TicketPaymentService ticketPaymentService,
      SeatReservationService seatReservationService, TicketPriceService ticketPriceService) {
    this.ticketPaymentService = ticketPaymentService;
    this.seatReservationService = seatReservationService;
    this.ticketPriceService = ticketPriceService;
  }

  private static void validatePurchaseRequest(TicketPurchaseRequest ticketPurchaseRequest) {

    int noOfAdults = 0;
    int noOfInfants = 0;
    boolean adultPresent = false;

    if (ticketPurchaseRequest.accountId() < 0) {
      throw new InvalidPurchaseException("Invalid account Id");
    }

    if (ticketPurchaseRequest.ticketRequests().length == 0) {
      throw new InvalidPurchaseException("Empty ticket requests");
    }

    for (TicketRequest ticketRequest : ticketPurchaseRequest.ticketRequests()) {

      if (ticketRequest.type().equals(ADULT)) {
        adultPresent = true;
        noOfAdults += ticketRequest.noOfTickets();
      } else if (ticketRequest.type().equals(INFANT)) {
        noOfInfants += ticketRequest.noOfTickets();
      }

      if (!adultPresent) {
        throw new InvalidPurchaseException("No adult ticket exists");
      }

      if (noOfInfants > noOfAdults) {
        throw new InvalidPurchaseException("More infants than adults");
      }
    }

  }

  @Override
  public TicketPurchaseResponseDTO purchaseTickets(
      final TicketPurchaseRequest ticketPurchaseRequest) throws InvalidPurchaseException {

    int totalTickets = 0;
    int totalCost = 0;
    int totalSeats = 0;

    TicketResponseDTO ticketPurchase;

    validatePurchaseRequest(ticketPurchaseRequest);

    for (TicketRequest ticketRequest : ticketPurchaseRequest.ticketRequests()) {
      ticketPurchase = new TicketCalculatorFactory(ticketPriceService).ticketCalculator(
              ticketRequest.type())
          .calculateTicket(ticketRequest);

      totalTickets += ticketPurchase.totalTickets();
      totalSeats += ticketPurchase.totalSeats();
      totalCost += ticketPurchase.totalCost();

    }

    if (totalTickets > 20) {
      throw new InvalidPurchaseException("Ticket limit exceeded");
    }

    payAndReserveSeats(ticketPurchaseRequest, totalCost, totalSeats);

    return TicketPurchaseResponseDTO.builder().totalTickets(totalTickets).totalCost(totalCost)
        .totalSeats(totalSeats).accountId(ticketPurchaseRequest.accountId()).build();

  }

  private void payAndReserveSeats(TicketPurchaseRequest ticketPurchaseRequest, int totalCost,
      int totalSeats) {

    ticketPaymentService.makePayment(ticketPurchaseRequest.accountId(), totalCost);
    seatReservationService.reserveSeat(ticketPurchaseRequest.accountId(), totalSeats);

  }

}

package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.service;

import uk.gov.dwp.uc.pairtest.cinema.ticketprice.service.TicketPriceService;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketResponseDTO;

public class ChildCinemaCalculatorImpl implements CinemaCalculator {

  private final TicketPriceService ticketPriceService;

  public ChildCinemaCalculatorImpl(TicketPriceService ticketPriceService) {
    this.ticketPriceService = ticketPriceService;
  }

  @Override
  public TicketResponseDTO calculateTicket(TicketRequest ticketRequest) {

    return TicketResponseDTO.builder()
        .totalCost(
            ticketPriceService.getTicketPrice(ticketRequest.type()) * ticketRequest.noOfTickets())
        .totalTickets(ticketRequest.noOfTickets()).totalSeats(ticketRequest.noOfTickets()).build();

  }

}

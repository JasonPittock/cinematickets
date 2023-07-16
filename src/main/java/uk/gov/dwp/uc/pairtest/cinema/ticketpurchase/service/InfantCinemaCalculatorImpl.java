package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.service;

import javax.inject.Inject;
import uk.gov.dwp.uc.pairtest.cinema.ticketprice.service.TicketPriceService;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketResponseDTO;

public class InfantCinemaCalculatorImpl implements CinemaCalculator {

  private final @Inject TicketPriceService ticketPriceService;

  public InfantCinemaCalculatorImpl(TicketPriceService ticketPriceService) {
    this.ticketPriceService = ticketPriceService;
  }

  @Override
  public TicketResponseDTO calculateTicket(TicketRequest ticketRequest) {

    return TicketResponseDTO.builder()
        .totalTickets(ticketRequest.noOfTickets())
        .build();

  }

}

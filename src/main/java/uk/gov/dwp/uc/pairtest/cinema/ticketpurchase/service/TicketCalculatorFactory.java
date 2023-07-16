package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.service;

import uk.gov.dwp.uc.pairtest.cinema.ticketprice.service.TicketPriceService;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest.Type;

public class TicketCalculatorFactory {

  TicketPriceService ticketPriceService;

  public TicketCalculatorFactory(TicketPriceService ticketPriceService) {
    this.ticketPriceService = ticketPriceService;
  }

  public CinemaCalculator ticketCalculator(Type type) {

    return switch (type) {
      case ADULT -> new AdultCinemaCalculatorImpl(ticketPriceService);
      case CHILD -> new ChildCinemaCalculatorImpl(ticketPriceService);
      case INFANT -> new InfantCinemaCalculatorImpl(ticketPriceService);
    };
  }

}

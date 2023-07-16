package uk.gov.dwp.uc.pairtest.cinema.ticketprice.service;

import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest.Type;

public interface TicketPriceService {

  int getTicketPrice(Type type);

}

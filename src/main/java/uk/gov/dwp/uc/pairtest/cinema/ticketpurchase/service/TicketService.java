package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.service;

import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketPurchaseResponseDTO;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.exception.InvalidPurchaseException;

public interface TicketService {

  TicketPurchaseResponseDTO purchaseTickets(TicketPurchaseRequest ticketPurchaseRequest)
      throws InvalidPurchaseException;

}

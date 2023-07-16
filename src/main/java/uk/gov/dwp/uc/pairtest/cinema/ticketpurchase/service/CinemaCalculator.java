package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.service;

import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketResponseDTO;

public interface CinemaCalculator {

  TicketResponseDTO calculateTicket(TicketRequest ticketRequest);

}

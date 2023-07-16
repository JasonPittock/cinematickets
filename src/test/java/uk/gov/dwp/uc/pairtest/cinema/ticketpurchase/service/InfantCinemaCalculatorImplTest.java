package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest.Type;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketResponseDTO;

@ExtendWith(MockitoExtension.class)
class InfantCinemaCalculatorImplTest {

  @InjectMocks
  InfantCinemaCalculatorImpl infantCinemaCalculator;

  @Test
  void shouldCalculateInfantTicketAndReturnTicketResponseDTO() {

    final TicketResponseDTO ticketResponseDTO = infantCinemaCalculator.calculateTicket(
        new TicketRequest(Type.INFANT, 1));

    assertEquals(1, ticketResponseDTO.totalTickets());
    assertEquals(0, ticketResponseDTO.totalSeats());
    assertEquals(0, ticketResponseDTO.totalCost());


  }
}
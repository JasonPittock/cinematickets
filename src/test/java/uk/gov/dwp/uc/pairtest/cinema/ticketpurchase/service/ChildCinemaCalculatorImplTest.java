package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.dwp.uc.pairtest.cinema.ticketprice.service.TicketPriceService;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketRequest.Type;
import uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain.TicketResponseDTO;

@ExtendWith(MockitoExtension.class)
class ChildCinemaCalculatorImplTest {

  @Mock
  TicketPriceService ticketPriceService;

  @InjectMocks
  ChildCinemaCalculatorImpl childCinemaCalculator;

  @Test
  void shouldCalculateChildTicketAndReturnTicketResponseDTO() {

    when(ticketPriceService.getTicketPrice(Type.CHILD)).thenReturn(10);

    final TicketResponseDTO ticketResponseDTO = childCinemaCalculator.calculateTicket(
        new TicketRequest(Type.CHILD, 10));

    verify(ticketPriceService, times(1)).getTicketPrice(Type.CHILD);

    assertEquals(10, ticketResponseDTO.totalTickets());
    assertEquals(10, ticketResponseDTO.totalSeats());
    assertEquals(100, ticketResponseDTO.totalCost());


  }
}
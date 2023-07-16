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
class AdultCinemaCalculatorImplTest {

  @Mock
  TicketPriceService ticketPriceService;

  @InjectMocks
  AdultCinemaCalculatorImpl adultCinemaCalculator;

  @Test
  void shouldCalculateAdultTicketAndReturnTicketResponseDTO() {

    when(ticketPriceService.getTicketPrice(Type.ADULT)).thenReturn(20);

    final TicketResponseDTO ticketResponseDTO = adultCinemaCalculator.calculateTicket(
        new TicketRequest(Type.ADULT, 10));

    verify(ticketPriceService, times(1)).getTicketPrice(Type.ADULT);

    assertEquals(10, ticketResponseDTO.totalTickets());
    assertEquals(10, ticketResponseDTO.totalSeats());
    assertEquals(200, ticketResponseDTO.totalCost());


  }
}
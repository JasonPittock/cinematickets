package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain;

import lombok.Builder;

@Builder
public record TicketResponseDTO(int totalCost, int totalSeats, int totalTickets) {


}

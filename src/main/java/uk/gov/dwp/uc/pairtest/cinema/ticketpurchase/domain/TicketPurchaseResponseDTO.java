package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain;

import lombok.Builder;

@Builder
public record TicketPurchaseResponseDTO(long accountId, int totalCost, int totalSeats,
                                        int totalTickets) {


}

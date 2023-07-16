package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain;

import lombok.Builder;

/**
 * Should be an Immutable Object
 */
@Builder
public record TicketRequest(Type type, int noOfTickets) {

  public enum Type {

    ADULT,
    CHILD,
    INFANT

  }

}

package uk.gov.dwp.uc.pairtest.cinema.ticketpurchase.domain;

import java.util.Objects;
import lombok.Builder;

@Builder
public record TicketPurchaseRequest(long accountId, TicketRequest[] ticketRequests) {

  @Override
  public String toString() {
    return "ticket account Id " + accountId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketPurchaseRequest that = (TicketPurchaseRequest) o;
    return accountId == that.accountId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId);
  }


}

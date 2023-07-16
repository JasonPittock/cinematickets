# Cinema Tickets Application

A cinema ticket calculation service provided for an interview at the DWP.

Assumptions:

Spring is not used on this project as I was unsure if it was admissible.
However, the code would have been cleaner, and would have required less coding, if Spring was used to handle DI.

The TicketPriceService is left without logic, as it is assumed the prices would be sourced from elsewhere, possibly a DAO call?
However, all calls to TicketPriceService in the test cases are mocked to return the appropriate price.



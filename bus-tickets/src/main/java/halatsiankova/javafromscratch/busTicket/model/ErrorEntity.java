package halatsiankova.javafromscratch.busTicket.model;

import halatsiankova.javafromscratch.busTicket.enumerated.ErrorType;

import java.util.UUID;

public class ErrorEntity {
    private UUID ticketId;
    private ErrorType error;

    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public ErrorType getError() {
        return error;
    }

    public void setError(ErrorType error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ErrorEntity{" +
                "ticketId=" + ticketId +
                ", error='" + error + '\'' +
                '}';
    }
}

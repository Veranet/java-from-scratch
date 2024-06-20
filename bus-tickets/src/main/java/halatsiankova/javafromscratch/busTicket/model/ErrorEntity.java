package halatsiankova.javafromscratch.busTicket.model;

import halatsiankova.javafromscratch.busTicket.enumerated.ErrorType;

import java.util.Objects;
import java.util.UUID;

public class ErrorEntity {
    private UUID ticketId;
    private ErrorType error;

    public ErrorEntity() {
    }

    public ErrorEntity(UUID ticketId, ErrorType error) {
        this.ticketId = ticketId;
        this.error = error;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorEntity that = (ErrorEntity) o;
        return Objects.equals(ticketId, that.ticketId) && error == that.error;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, error);
    }
}

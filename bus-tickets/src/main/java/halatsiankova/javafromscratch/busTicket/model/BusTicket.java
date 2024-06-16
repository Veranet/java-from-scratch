package halatsiankova.javafromscratch.busTicket.model;

import halatsiankova.javafromscratch.busTicket.enumerated.TicketType;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class BusTicket {
    private UUID id;
    private String ticketClass;
    private TicketType ticketType;
    private String startDate;
    private BigDecimal price;

    public BusTicket() {
    }

    public BusTicket(String ticketClass, TicketType ticketType, String startDate, BigDecimal price) {
        this.ticketClass = ticketClass;
        this.ticketType = ticketType;
        this.startDate = startDate;
        this.price = price;
    }

    public void setTicketClass(String ticketClass) {
        this.ticketClass = ticketClass;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTicketClass() {
        return ticketClass;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public String getStartDate() {
        return startDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "BusTicket{" +
                "id=" + id +
                ", ticketClass='" + ticketClass + '\'' +
                ", ticketType='" + ticketType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusTicket ticket = (BusTicket) o;
        return Objects.equals(id, ticket.id)
                && Objects.equals(ticketClass, ticket.ticketClass)
                && ticketType == ticket.ticketType
                && Objects.equals(startDate, ticket.startDate)
                && Objects.equals(price, ticket.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticketClass, ticketType, startDate, price);
    }
}

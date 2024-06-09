package halatsiankova.javafromscratch.lesson2.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public class Ticket {

    private String ticketNumber;
    private String concertHall;
    private int eventCode;
    private long eventTime;
    private boolean isPromo;
    private StadiumSector stadiumSector;
    private double allowedBackpackWeight;
    private BigDecimal price;
    private OffsetDateTime createdDateTime;

    public Ticket() {
    }

    public Ticket(String ticketId, String concertHall, int eventCode, long eventTime, boolean isPromo,
                  StadiumSector stadiumSector, double allowedBackpackWeight, BigDecimal price, OffsetDateTime date) {
        this.ticketNumber = ticketId;
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.eventTime = eventTime;
        this.isPromo = isPromo;
        this.stadiumSector = stadiumSector;
        this.allowedBackpackWeight = allowedBackpackWeight;
        this.price = price;
        this.createdDateTime = date;
    }

    public Ticket(String concertHall, int eventCode, long time) {
        this(null, concertHall, eventCode, time, false, null, 0.0, null, null);
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public void setConcertHall(String concertHall) {
        this.concertHall = concertHall;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public void setPromo(boolean promo) {
        isPromo = promo;
    }

    public void setStadiumSector(StadiumSector stadiumSector) {
       this.stadiumSector = stadiumSector;
    }

    public void setAllowedBackpackWeight(double allowedBackpackWeight) {
        this.allowedBackpackWeight = allowedBackpackWeight;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public void setCreatedDateTime(OffsetDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public enum StadiumSector {
        A, B, C
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket ticket)) return false;
        return eventCode == ticket.eventCode && eventTime == ticket.eventTime && isPromo == ticket.isPromo
                && stadiumSector == ticket.stadiumSector
                && Double.compare(allowedBackpackWeight, ticket.allowedBackpackWeight) == 0
                && Objects.equals(ticketNumber, ticket.ticketNumber) && Objects.equals(concertHall, ticket.concertHall)
                && Objects.equals(price, ticket.price) && Objects.equals(createdDateTime, ticket.createdDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketNumber, concertHall, eventCode, eventTime, isPromo,
                stadiumSector, allowedBackpackWeight, price, createdDateTime);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketNumber + '\'' +
                ", concertHall='" + concertHall + '\'' +
                ", eventCode=" + eventCode +
                ", eventTime=" + eventTime +
                ", isPromo=" + isPromo +
                ", stadiumSector=" + stadiumSector +
                ", allowedBackpackWeight=" + allowedBackpackWeight +
                ", price=" + price +
                ", createdDateTime=" + createdDateTime +
                '}';
    }
}

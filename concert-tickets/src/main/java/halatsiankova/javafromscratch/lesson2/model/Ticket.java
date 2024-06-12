package halatsiankova.javafromscratch.lesson2.model;

import halatsiankova.javafromscratch.lesson2.annotation.NullableWarning;
import halatsiankova.javafromscratch.lesson2.enumerated.StadiumSector;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public class Ticket implements Entity<Integer>, Printable, Sharable {
    private Integer id;
    @NullableWarning
    private String ticketId;
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
        this.ticketId = ticketId;
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

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public void setStadiumSector(StadiumSector stadiumSector) {
       this.stadiumSector = stadiumSector;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getConcertHall() {
        return concertHall;
    }

    public int getEventCode() {
        return eventCode;
    }

    public long getEventTime() {
        return eventTime;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public StadiumSector getStadiumSector() {
        return stadiumSector;
    }

    public double getAllowedBackpackWeight() {
        return allowedBackpackWeight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OffsetDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }

    @Override
    public String share(String phone) {
        return String.format("Ticket with ticketId = %s share by phone %s .%n", ticketId, phone);
    }

    @Override
    public String share(String phone, String email) {
        return String
                .format("Ticket with ticketId = %s share by phone = %s and by email = %s .%n", ticketId, phone, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket ticket)) return false;
        return eventCode == ticket.eventCode
                && eventTime == ticket.eventTime
                && isPromo == ticket.isPromo
                && stadiumSector == ticket.stadiumSector
                && (int) id == ticket.id
                && Double.compare(allowedBackpackWeight, ticket.allowedBackpackWeight) == 0
                && Objects.equals(ticketId, ticket.ticketId)
                && Objects.equals(concertHall, ticket.concertHall)
                && Objects.equals(price, ticket.price)
                && Objects.equals(createdDateTime, ticket.createdDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticketId, concertHall, eventCode, eventTime, isPromo,
                stadiumSector, allowedBackpackWeight, price, createdDateTime);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", ticketId='" + ticketId + '\'' +
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

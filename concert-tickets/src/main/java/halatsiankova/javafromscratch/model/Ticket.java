package halatsiankova.javafromscratch.model;

import halatsiankova.javafromscratch.enumerated.StadiumSector;
import halatsiankova.javafromscratch.enumerated.TicketType;
import halatsiankova.javafromscratch.validator.NullValidatorProcessor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ticket")
public class Ticket implements Printable, Sharable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Transient
    private String ticketId;
    @Transient
    private String concertHall;
    @Transient
    private int eventCode;
    @Transient
    private long eventTime;
    @Transient
    private boolean isPromo;
    @Transient
    private StadiumSector stadiumSector;
    @Transient
    private double allowedBackpackWeight;
    @Transient
    private BigDecimal price;
    @Column(name = "creation_date")
    private LocalDateTime createdDateTime;
    @Column(name = "user_id")
    private int userId;
    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "ticket_type")
    private TicketType type;

    public Ticket() {
        NullValidatorProcessor.checkNullFields(this);
    }

    public Ticket(String ticketId, String concertHall, int eventCode, long eventTime, boolean isPromo,
                  StadiumSector stadiumSector, double allowedBackpackWeight, BigDecimal price, LocalDateTime date) {
        this.ticketId = ticketId;
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.eventTime = eventTime;
        this.isPromo = isPromo;
        this.stadiumSector = stadiumSector;
        this.allowedBackpackWeight = allowedBackpackWeight;
        this.price = price;
        this.createdDateTime = date;
        NullValidatorProcessor.checkNullFields(this);
    }

    public Ticket(String concertHall, int eventCode, long time) {
        this(null, concertHall, eventCode, time, false, null, 0.0, null, null);
        NullValidatorProcessor.checkNullFields(this);
    }

    public Ticket(Integer id, int userId, TicketType type, LocalDateTime createdDateTime) {
        this.id = id;
        this.createdDateTime = createdDateTime;
        this.userId = userId;
        this.type = type;
    }

    public Ticket(String ticketId, String concertHall, int eventCode, long eventTime, boolean isPromo,
                  StadiumSector stadiumSector, double allowedBackpackWeight, BigDecimal price, int userId) {
        this.ticketId = ticketId;
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.eventTime = eventTime;
        this.isPromo = isPromo;
        this.stadiumSector = stadiumSector;
        this.allowedBackpackWeight = allowedBackpackWeight;
        this.price = price;
        this.userId = userId;
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime date) {
        this.createdDateTime = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return eventCode == ticket.eventCode && eventTime == ticket.eventTime && isPromo == ticket.isPromo && Double.compare(allowedBackpackWeight, ticket.allowedBackpackWeight) == 0 && userId == ticket.userId && Objects.equals(id, ticket.id) && Objects.equals(ticketId, ticket.ticketId) && Objects.equals(concertHall, ticket.concertHall) && stadiumSector == ticket.stadiumSector && Objects.equals(price, ticket.price) && Objects.equals(createdDateTime, ticket.createdDateTime) && type == ticket.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticketId, concertHall, eventCode, eventTime, isPromo, stadiumSector, allowedBackpackWeight, price, createdDateTime, userId, type);
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
                ", userId=" + userId +
                ", type=" + type +
                '}';
    }

    @Override
    public String share(String phone) {
        return String.format("Ticket with ticketId = %s share by phone %s .%n.", ticketId, phone);
    }

    @Override
    public String share(String phone, String email) {
        return String
                .format("Ticket with ticketId = %s share by phone = %s and by email = %s .%n.", ticketId, phone, email);
    }
}

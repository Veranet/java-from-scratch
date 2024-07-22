package halatsiankova.javafromscratch.dto;

import halatsiankova.javafromscratch.enumerated.TicketType;

import java.time.LocalDateTime;

public class TicketDto {
    private TicketType ticketType;
    private LocalDateTime ticketDate;
    private Integer userId;

    public TicketDto() {
    }

    public TicketDto(TicketType ticketType, LocalDateTime ticketDate, Integer userId) {
        this.ticketType = ticketType;
        this.ticketDate = ticketDate;
        this.userId = userId;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public LocalDateTime getTicketDate() {
        return ticketDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public void setTicketDate(LocalDateTime ticketDate) {
        this.ticketDate = ticketDate;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

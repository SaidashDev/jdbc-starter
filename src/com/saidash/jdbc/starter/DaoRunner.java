package com.saidash.jdbc.starter;

import com.saidash.jdbc.starter.dao.TicketDao;
import com.saidash.jdbc.starter.entity.Ticket;
import dto.TicketFilter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DaoRunner {
    public static void main(String[] args) {

        var ticketFilter = new TicketFilter(3, 2, null, "A1");
        var tickets = TicketDao.getInstance().findAll(ticketFilter);
        System.out.println(tickets);


    }



    private static void updateTest() {
        var ticketDao = TicketDao.getInstance();
        var maybeTicket = ticketDao.findById(2L);
        System.out.println(maybeTicket);

        maybeTicket.ifPresent(ticket -> {
            ticket.setCost(BigDecimal.valueOf(188.88));
            ticketDao.update(ticket);
        });
    }

    private static void deleteTest(Long id) {
        var ticketDao = TicketDao.getInstance();
        var deleteResult = ticketDao.delete(id);
        System.out.println(deleteResult);
    }

    private static void saveTest() {
        var ticketDao = TicketDao.getInstance();
        var ticket = new Ticket();
        ticket.setPassengerNo("1234567");
        ticket.setPassengerName("Test");
        ticket.setFlightId(3L);
        ticket.setSeatNo("3B");
        ticket.setCost(BigDecimal.TEN);

        var savedTicket = ticketDao.save(ticket);
        System.out.println(savedTicket);
    }
}

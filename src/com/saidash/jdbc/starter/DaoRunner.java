package com.saidash.jdbc.starter;

import com.saidash.jdbc.starter.dao.TicketDao;
import com.saidash.jdbc.starter.entity.Ticket;

import java.math.BigDecimal;

public class DaoRunner {
    public static void main(String[] args) {


        var ticketDao = TicketDao.getInstance();
        var deleteResult = ticketDao.delete(57L);
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

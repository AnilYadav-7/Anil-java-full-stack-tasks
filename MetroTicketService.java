package com.example.metro.service;

import com.example.metro.entity.MetroTicket;

public interface MetroTicketService {
	MetroTicket bookTicket(String passengerName, String sourceStation, String destinationStation, int numberOfTickets);

    MetroTicket validateTicketEntry(String ticketId);

    MetroTicket validateTicketExit(String ticketId);

}

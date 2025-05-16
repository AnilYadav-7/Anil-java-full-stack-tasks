package com.example.metro.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.metro.entity.MetroTicket;
import com.example.metro.repository.MetroTicketRepository;
import com.example.metro.util.StationPriceLoader;

@Service
public class MetroTicketServiceImpl implements MetroTicketService {
	private static final long EXPIRY_DURATION_MS = 18 * 60 * 60 * 1000;

	@Autowired
	private MetroTicketRepository metroTicketRepository;
	
	@Autowired
	private StationPriceLoader stationPriceLoader;

	public MetroTicket bookTicket(String passengerName, String sourceStation, String destinationStation,
			int numberOfTickets) {
		// TODO Auto-generated method stub
		if (sourceStation.equalsIgnoreCase(destinationStation)) {
            throw new RuntimeException("Source and destination cannot be the same");
        }
		if (numberOfTickets <= 0) {
            throw new IllegalArgumentException("Number of tickets must be greater than zero.");
        }
		if (passengerName == null || passengerName.trim().isEmpty()) {
		    throw new IllegalArgumentException("Passenger name cannot be empty.");
		}
		double ticketPrice = calculateTicketPrice(sourceStation, destinationStation);
		String ticketId = UUID.randomUUID().toString();
		long createdAt = Instant.now().toEpochMilli();
		MetroTicket ticket = new MetroTicket(passengerName, sourceStation, destinationStation, numberOfTickets,
				ticketPrice * numberOfTickets, ticketId, createdAt);
		return metroTicketRepository.save(ticket);
	}

	@Override
	public MetroTicket validateTicketEntry(String ticketId) {
		MetroTicket ticket = getValidTicket(ticketId);
		if (ticket.getUsesLeft() < 2) {
			throw new RuntimeException("Ticket already used for entry.");
		}
		ticket.setUsesLeft(ticket.getUsesLeft() - 1);
		return metroTicketRepository.save(ticket);
	}
	
	@Override
	public MetroTicket validateTicketExit(String ticketId) {
		MetroTicket ticket = getValidTicket(ticketId);
        if (ticket.getUsesLeft() != 1) {
            throw new RuntimeException("You must enter first before exiting.");
        }
        ticket.setUsesLeft(ticket.getUsesLeft() - 1);
        return metroTicketRepository.save(ticket);
	}

	private MetroTicket getValidTicket(String ticketId) {
		// TODO Auto-generated method stub
		Optional<MetroTicket> optional = metroTicketRepository.findByTicketId(ticketId);
		if (optional.isEmpty()) {
			throw new RuntimeException("Invalid ticket ID.");
		}

		MetroTicket ticket = optional.get();
		long now = Instant.now().toEpochMilli();
		if ((now - ticket.getCreatedAt()) > EXPIRY_DURATION_MS) {
			throw new RuntimeException("Ticket expired.");
		}

		if (ticket.getUsesLeft() <= 0) {
			throw new RuntimeException("Ticket already used up.");
		}

		return ticket;
	}

	private double calculateTicketPrice(String sourceStation, String destinationStation) {
	    int sourcePrice = stationPriceLoader.getPrice(sourceStation);
	    int destinationPrice = stationPriceLoader.getPrice(destinationStation);
	    return Math.abs(destinationPrice - sourcePrice);
	}

}

package com.example.metro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.metro.entity.MetroTicket;
import com.example.metro.service.MetroTicketService;

@RestController
@RequestMapping("/api/tickets")
public class MetroTicketController {
	
	@Autowired
    private MetroTicketService metroTicketService;
	
	@PostMapping("/book")
    public ResponseEntity<MetroTicket> bookTicket(@RequestParam String passengerName,
                                                  @RequestParam String source,
                                                  @RequestParam String destination,
                                                  @RequestParam int numberOfTickets) {
        MetroTicket ticket = metroTicketService.bookTicket(passengerName, source, destination, numberOfTickets);
        return ResponseEntity.ok(ticket);
    }
	
	 @PostMapping("/enter")
	    public ResponseEntity<MetroTicket> enterStation(@RequestParam String ticketId) {
	        MetroTicket ticket = metroTicketService.validateTicketEntry(ticketId);
	        return ResponseEntity.ok(ticket);
	    }
	 
	 @PostMapping("/exit")
	    public ResponseEntity<MetroTicket> exitStation(@RequestParam String ticketId) {
	        MetroTicket ticket = metroTicketService.validateTicketExit(ticketId);
	        return ResponseEntity.ok(ticket);
	    }

}

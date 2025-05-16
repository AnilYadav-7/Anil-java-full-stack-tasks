package com.example.metro.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.metro.entity.MetroTicket;

public interface MetroTicketRepository extends JpaRepository<MetroTicket, Long> {
    
    // Find a ticket by its generated ticketId
    Optional<MetroTicket> findByTicketId(String ticketId);
}
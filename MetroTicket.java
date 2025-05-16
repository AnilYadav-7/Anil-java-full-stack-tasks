package com.example.metro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "metro_tickets")
public class MetroTicket {

	@Id
	@NotNull(message = "Id cannot be null")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotBlank(message = "Passenger name cannot be blank")
    @Size(min = 2, max = 30, message = "Passenger name must be between 2 and 30 characters")
	@Column(nullable = false)
	private String passengerName;
    
    @NotBlank(message = "Source station cannot be blank")
	@Column(nullable = false)
	private String sourceStation;

    @NotBlank(message = "Destination station cannot be blank")
	@Column(nullable = false)
	private String destinationStation;
    
	@Column(nullable = false)
	@Min(value = 1, message = "At least one ticket must be booked")
	private int numberOfTickets;
	
	@Column(nullable = false)
	@Min(value = 0, message = "Ticket price cannot be negative")
	private double ticketPrice;
	
	@Column(nullable = false, unique = true)
	private String ticketId;
	
	@Column(nullable = false)
	private long createdAt;
	
	@Column(nullable = false)
	private int usesLeft = 2;

	public MetroTicket(String passengerName, String sourceStation, String destinationStation, int numberOfTickets,
			double ticketPrice, String ticketId, long createdAt) {
		this.passengerName = passengerName;
		this.sourceStation = sourceStation;
		this.destinationStation = destinationStation;
		this.numberOfTickets = numberOfTickets;
		this.ticketPrice = ticketPrice;
		this.ticketId = ticketId;
		this.createdAt = createdAt;
		this.usesLeft = 2;
	}

}

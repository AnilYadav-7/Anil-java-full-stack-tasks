package com.example.gmail.dto;

public class EmailDTO {
	private String from;
    private String subject;
    public EmailDTO() {}
	public EmailDTO(String from, String subject) {
		this.from = from;
		this.subject = subject;
	}
	public String getFrom() {
		return from;
	}
	public String getSubject() {
		return subject;
	}
}

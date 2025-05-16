package com.example.gmail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gmail.dto.EmailDTO;
import com.example.gmail.service.GmailService;
import java.util.*;
import java.io.*;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/gmail")
public class GmailController {
	
	 @Autowired
	    private GmailService gmailService;

	    @GetMapping("/emails")
	    public List<EmailDTO> getEmails() throws IOException, GeneralSecurityException {
	        return gmailService.getLast200Emails();
	    }

}

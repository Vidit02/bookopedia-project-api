package com.service;

import org.springframework.stereotype.Service;

@Service
public class GenerateAuthToken {
	String AllWords = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789abcdefghijklmnopqrstuvwxyz";
	public String generateToken(int size) {
		String token = "";
		for(int i=0 ; i<size ; i++) {
			token = token + AllWords.charAt(((int) (61 * Math.random())));
		}
		return token;
	}
}

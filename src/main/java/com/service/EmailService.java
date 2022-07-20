package com.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
	public Boolean sendEmailForOtp(String email , String otp) {
		final String fromEmail = "viditn21102@gmail.com"; // email from 
		final String appPass   = "wqioqcuyobsvywou"; //app password
		
		Properties prop = System.getProperties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.ssl.enable", "false");
		
		Session session = Session.getInstance(prop,new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(fromEmail, appPass);
			}
		});
		
		session.setDebug(true);
		
		try {
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(fromEmail);
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			mimeMessage.setSubject("Book-o-pedia : OTP to reset your password");
			String code = "<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2;background-color:#f2f5f6; \">\n"
					+ "        <div style=\"margin:50px auto;width:70%;padding:20px 0\">\n"
					+ "          <div style=\"border-bottom:1px solid #eee;text-align: center;\">\n"
					+ "            <a href=\"\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600;\">\n"
					+ "              <img src=\"https://i.postimg.cc/bwYFS0tJ/android-chrome-256x256.png\" width=\"100\" >\n"
					+ "            </a>\n"
					+ "          </div>\n"
					+ "          <p style=\"font-size:1.1em\">Hi,</p>\n"
					+ "          <p>Thank you for Book-o-pedia. Use the following OTP to reset your password. OTP is valid for 90 seconds</p>\n"
					+ "          <h2 style=\"background:black;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">"+otp+"</h2>\n"
					+ "          <p style=\"font-size:0.9em;\">Regards,<br />Book-o-pedia</p>\n"
					+ "          <hr style=\"border:none;border-top:1px solid #eee\" />\n"
					+ "          <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">\n"
					+ "            <p>Book-o-pedia</p>\n"
					+ "            <p>Ahmedabad</p>\n"
					+ "          </div>\n"
					+ "        </div>\n"
					+ "      </div>";
			mimeMessage.setContent(code, "text/html");
			
			Transport.send(mimeMessage);
			System.out.println("email sent");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Problem has occured");
			return false;
		}
	}
}

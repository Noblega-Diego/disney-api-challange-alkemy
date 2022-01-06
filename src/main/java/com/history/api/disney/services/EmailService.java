package com.history.api.disney.services;

import api.sendGrid.BodySendEmail;
import api.sendGrid.Content;
import api.sendGrid.Email;
import api.sendGrid.Personalization;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {

    private final String URL_SERVICE = "https://api.sendgrid.com/v3/mail/send";
    private final String API_KEY;
    private final String EMAIL;
    private Environment env;

    public EmailService(Environment env){
        this.env = env;
        API_KEY = env.getProperty("api.sendgrid.key");
        EMAIL = env.getProperty("api.sendgrid.email_origin");
    }


    public String sendEmailContentText(String toEmail, String toName, String subject, String content){
        return this.sendEmail(MediaType.TEXT_PLAIN_VALUE, toEmail, toName, subject, content);
    }

    public String sendEmailContentHTML(String toEmail, String toName, String subject, String content){
        return this.sendEmail(MediaType.TEXT_HTML_VALUE, toEmail, toName, subject, content);
    }

    private String sendEmail(String mediaTypeValue, String toEmail, String toName, String subject, String content){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer " + API_KEY);
        BodySendEmail body = new BodySendEmail();
        body.setPersonalizations(new Personalization[]{new Personalization(new Email[]{new Email(toEmail, toName)}, subject)});
        body.setContent(new Content[]{new Content(mediaTypeValue, content)});
        body.setFrom(new Email(EMAIL,"auth-disney-api"));
        body.setReplyTo(new Email(EMAIL,"auth-disney-api"));
        HttpEntity<BodySendEmail> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = new RestTemplate().postForEntity(
                URL_SERVICE,
                httpEntity,String.class);
        return response.getBody();
    }

}


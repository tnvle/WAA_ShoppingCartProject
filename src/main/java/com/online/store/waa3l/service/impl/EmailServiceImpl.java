package com.online.store.waa3l.service.impl;

import com.online.store.waa3l.constant.ApplicationConstants;
import com.online.store.waa3l.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = Logger.getLogger(EmailServiceImpl.class.getName());

    @Value("${mail.smtp.ssl.trust}")
    private String mailSSLTrust;

    @Value("${mail.smtp.port}")
    private String mailPort;

    @Value("${mail.smtp.auth}")
    private String mailIsAuth;

    @Value("${mail.smtp.starttls.enable}")
    private String mailStartTLSEnable;

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.username}")
    private String mailUsername;

    @Value("${mail.password}")
    private String mailPassword;

    @Value("${mail.from.address}")
    private String mailAddress;

    @Value("${mail.from.name}")
    private String mailName;

    @Value("${production.site}")
    private String productSiteUrl;

    @Override
    public void sendEmail(String content, String subject, List<String> emailTos) {

        final String emailContent = String.format(content, productSiteUrl);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    // Configuration
                    Properties mailServerProperties;
                    Session getMailSession;
                    mailServerProperties = System.getProperties();
                    mailServerProperties.put(ApplicationConstants.EMAIL.SSL, mailSSLTrust);
                    mailServerProperties.put(ApplicationConstants.EMAIL.PORT, mailPort);
                    mailServerProperties.put(ApplicationConstants.EMAIL.IS_AUTH, mailIsAuth);
                    mailServerProperties.put(ApplicationConstants.EMAIL.TLS_ENABLE, mailStartTLSEnable);
                    getMailSession = Session.getDefaultInstance(mailServerProperties, null);

                    Transport transport = getMailSession.getTransport(ApplicationConstants.EMAIL.SMTP);
                    transport.connect(mailHost, mailUsername, mailPassword);

                    List<InternetAddress> emails = new ArrayList<>();
                    for (String email : emailTos) {
                        emails.add(new InternetAddress(email));
                    }
                    if (emails.size() > 0) {
                        MimeMessage generatedMailMessage = this.generateMailMessage(getMailSession, emailContent, subject,
                                emails.toArray(new InternetAddress[emails.size()]), mailAddress, mailName);

                        transport.sendMessage(generatedMailMessage, generatedMailMessage.getAllRecipients());

                        transport.close();
                        logger.info("<========= EMAIL SENT TO: " + emailTos);
                    }
                } catch (Exception me) {
                    logger.severe("<======EMAIL CONFIG ERROR=====>");
                    logger.severe(me.getMessage());
                    logger.severe("<======EMAIL CONFIG ERROR=====>");
                    me.printStackTrace();
                    throw new RuntimeException(me.getMessage());
                }

            }

            public MimeMessage generateMailMessage(Session getMailSession, String content, String subject,
                                                   InternetAddress[] toAddresses, String mailAddress, String mailName)
                    throws AddressException, MessagingException {

                MimeMessage generatedMailMessage;
                generatedMailMessage = new MimeMessage(getMailSession);
                try {
                    generatedMailMessage.setFrom(new InternetAddress(mailAddress, mailName));
                } catch (UnsupportedEncodingException e) {
                    logger.severe("Failed to set email address: " + mailAddress);
                }
                generatedMailMessage.addRecipients(Message.RecipientType.TO, toAddresses);
                generatedMailMessage.setSubject(subject, ApplicationConstants.EMAIL.UTF8);
                generatedMailMessage.setContent(content, ApplicationConstants.EMAIL.UTF8_WEB_FORMAT_CONTENT);
                return generatedMailMessage;
            }

        };
        new Thread(task, "SendEmailThread_" + emailTos.toString()).start();

    }

    @PostConstruct
    public void init() {

    }

}

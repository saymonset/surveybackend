package com.mail;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.tools.UtilDate;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Created by simon on 5/24/2019.
 */
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public static final String ENCODING = "UTF-8";
    @Autowired
    private VelocityEngine velocityEngine;
    private UtilDate utilDate = new UtilDate();
    public boolean send(String from, String replyTo, String to, String subject, String templateLocation, VelocityContext context)throws Exception  {
        String[] toArray = new String[1];
        toArray[0] = to;
        return send(from, replyTo, toArray, subject, templateLocation, context);
    }

    public boolean send(String from, String replyTo, String[] to, String subject, String templateLocation, VelocityContext context)throws Exception  {
        return send(from,  replyTo, to, utilDate.createDateMexicoLocalZone(), subject, templateLocation, context);
    }

    public boolean send(String from, String replyTo, String[] to,Date sentDate, String subject, String templateLocation,VelocityContext context) throws Exception {




            StringWriter stringWriter = new StringWriter();

                velocityEngine.mergeTemplate(templateLocation, "UTF-8", context, stringWriter);

            String text = stringWriter.toString();


            String body = "";//VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, ENCODING, model);
            //return send(from, nameFrom, replyTo, to, cc, bcc, sentDate, subject, body, true);
            boolean html = true;
         return   send( from,  replyTo,  sentDate,  subject,  text, html);

    }

    public boolean send(String from, String to, Date sentDate, String subject, String text, boolean html) throws MessagingException {
    //    try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, ENCODING);
            helper.setFrom(from);
            //helper.setReplyTo(from);
            helper.setTo(to);
            helper.setSentDate(sentDate);
            helper.setSubject(subject);
            helper.setText(text, html);
            mailSender.send(message);
            return true;
        /*} catch (MessagingException | MailException exception) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, "EmailService => send => (+)", exception);
            return false;
        }*/
    }
}

package geso.dms.center.util;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*;
import java.sql.*;

import geso.dms.db.sql.*;

public class SendMail
{
  protected String SMTP_HOST_NAME ;
  protected String SMTP_AUTH_USER ;
  protected String SMTP_AUTH_PWD  ;
  private  String emailFromAddress; 
  
  public SendMail()
  {
  }
  
  public void postMail( String[] recipients, String subject, String message ) throws MessagingException
  {
    boolean debug = false;
      dbutils sql = new dbutils();
      SMTP_HOST_NAME = "mail.geso.us";
      SMTP_AUTH_USER = "chinhpc@geso.us";
      SMTP_AUTH_PWD = "chinhphancong";
      emailFromAddress = "chinhpc@geso.us";

     //Set the host smtp address
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(props, auth);

        session.setDebug(debug);

    // create a message
        Message msg = new MimeMessage(session);

    // set the from and to address
        InternetAddress addressFrom = new InternetAddress(emailFromAddress);
    
        InternetAddress[] addressTo = new InternetAddress[1];
    for (int i = 1; i <= new Integer(recipients[0]).intValue(); i++)
    {
        System.out.println(recipients[i]);
        addressTo[0] = new InternetAddress(recipients[i]);
        msg.setFrom(addressFrom);
        msg.setRecipients(Message.RecipientType.TO, addressTo);
        msg.setSubject(subject);
        msg.setContent(message, "text/html; charset=UTF-8");//msg.setContent(message, "text/plain");
        try{
          Transport.send(msg);
        }catch(SendFailedException err){err.toString();}
    }
    

 }


private class SMTPAuthenticator extends javax.mail.Authenticator
{

    public PasswordAuthentication getPasswordAuthentication()
    {
        String username = SMTP_AUTH_USER;
        String password = SMTP_AUTH_PWD;
        return new PasswordAuthentication(username, password);
    }
}

}



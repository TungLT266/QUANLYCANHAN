package demo.test;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class SMSClient {

	static String url = "http://center.fibosms.com/Service.asmx";
    String clientNo = "CL6044";
    String clientPass = "123456";
    String smsGUID = "1";
    String serviceType = "1";
	
    public static void main(String args[]) 
    {
    	SMSClient sms = new SMSClient();
    	
    	sms.sendSMS("0909865115", "Test send MSG");
    }

  
    public SMSClient() 
    {
    	
    }
    
    public String sendSMS( String phoneNumber, String smsMessage ) 
    {
    	try 
        {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            //TEST SEND MSG
            String methodNAME = "SendSMS";
            String[] keys = new String[] { "clientNo", "clientPass", "phoneNumber", "smsMessage", "smsGUID", "serviceType" };
            String[] values =  new String[] { clientNo, clientPass, phoneNumber, smsMessage, smsGUID, serviceType };
            
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest( methodNAME, keys, values ), url);
            
            // Process the SOAP Response
            printSOAPResponse(soapResponse);

            soapConnection.close();
        } 
        catch (Exception e) 
        {
            System.err.println("Error occurred while sending SOAP Request to Server");
            e.printStackTrace();
            
            return "Error occurred while sending SOAP Request to Server " + e.getMessage();
        }
    	
		return "";
	}
    
    private static SOAPMessage createSOAPRequest( String methodNAME, String[] keys, String[] values ) throws Exception 
    {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://tempuri.org/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("example", serverURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(methodNAME, "example");
        
        /*SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("clientNo", "example");
        soapBodyElem1.addTextNode("CL6044");
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("clientPass", "example");
        soapBodyElem2.addTextNode("123456");*/
        
        if( keys != null )
        {
        	for(int i = 0; i < keys.length; i++)
        	{
        		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement( keys[i] , "example");
                soapBodyElem1.addTextNode( values[i] );
        	}
        }

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI + methodNAME );

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

    private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception 
    {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        transformer.transform(sourceContent, result);
    }

}
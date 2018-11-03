package demo.test;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;

import org.w3c.dom.*;
import jxl.Cell;

public class WebService
{
	/*public  static String urlService = "http://sqldb02/E3S_DMS_Webservice/Service.asmx";
	public final static String namespace ="E3S_Software"; */
	
	public final static String namespace ="http://tempuri.org/";
	public static String urlService = "http://115.79.59.115:8089/License/LicenseService.asmx";
	
	//public final static String urlService_Sync  ="http://localhost:9999/Website/SyncData.asmx";
	public static String namespace_Sync = "http://tempuri.org/";
	public final static String urlService_Sync ="http://dms.opv.com.vn:9999/Website/SyncData.asmx";

	public static NodeList getDataTableFromService(String methodName, String tagname, String[] keys, String[] values )
	{
		SOAPConnectionFactory soapConnectionFactory = null;
		SOAPConnection soapConnection = null;
		try {
			// Create SOAP Connection
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();

			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader("SOAPAction", namespace+(namespace.lastIndexOf("/") == namespace.length() - 1?"":"/")+methodName); // +methodName DangNhap_SalesUp

			SOAPPart sp = soapMessage.getSOAPPart();

			SOAPEnvelope env = sp.getEnvelope();
			env.addNamespaceDeclaration("xsd","http://www.w3.org/2001/XMLSchema");
			env.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema-instance");
			SOAPBody bd = env.getBody();
			SOAPElement be = bd.addChildElement(env.createName(methodName,"",namespace)); 

			// them param vao input
			if(keys != null)
			{
				if(keys.length != values.length)       		
					System.out.println("keys.length !=  values.length ");      		
				else
					for(int i= 0; i < keys.length; i ++)       			
						be.addChildElement(keys[i]).addTextNode(values[i]);       			
			}
			soapMessage.saveChanges();

			SOAPMessage soapResponse = soapConnection.call(soapMessage, urlService);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			Source sourceContent = soapResponse.getSOAPPart().getContent();
			DOMResult  result = new DOMResult();
			transformer.transform(sourceContent, result);            
			Document doc= (Document)result.getNode();

			//System.out.println("TagNme = " + tagname );

			NodeList nList = doc.getElementsByTagName(tagname);

			//soapResponse.writeTo(System.out);
			//System.out.println();

			soapConnection.close();
			return nList;
			
		} 
		catch (Exception e) 
		{
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
			if(soapConnection != null) 
				try{soapConnection.close();}catch (Exception e2) {
				}
				return null;	
		}
	}	

	public static NodeList getDataTableFromService_Sync(String methodName,String tagname,String[]keys,String[]values )
	{
		SOAPConnectionFactory soapConnectionFactory = null;
		SOAPConnection soapConnection = null;
		try {
			// Create SOAP Connection
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();
			// Send SOAP Message to SOAP Server

			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();

			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader("SOAPAction", namespace_Sync+(namespace_Sync.lastIndexOf("/") == namespace_Sync.length() - 1?"":"/")+methodName); // +methodName DangNhap_SalesUp

			SOAPPart sp = soapMessage.getSOAPPart();

			SOAPEnvelope env = sp.getEnvelope();
			env.addNamespaceDeclaration("xsd","http://www.w3.org/2001/XMLSchema");
			env.addNamespaceDeclaration("xsi","http://www.w3.org/2001/XMLSchema-instance");
			SOAPBody bd = env.getBody();
			SOAPElement be = bd.addChildElement(env.createName(methodName,"",namespace_Sync)); 

			// them param vao input
			if(keys != null)
			{
				if(keys.length != values.length)       		
					System.out.println("keys.length !=  values.length ");      		
				else
					for(int i= 0; i < keys.length; i ++)       			
						be.addChildElement(keys[i]).addTextNode(values[i]);       			
			}
			soapMessage.saveChanges();
			SOAPMessage soapResponse = soapConnection.call(soapMessage, urlService_Sync);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			Source sourceContent = soapResponse.getSOAPPart().getContent();
			DOMResult  result = new DOMResult();
			transformer.transform(sourceContent, result);            
			Document doc= (Document)result.getNode();
			NodeList nList = doc.getElementsByTagName(tagname);
			soapConnection.close();
			return nList;
		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
			if(soapConnection != null) 
				try{soapConnection.close();}catch (Exception e2) {
				}
				return null;	
		}
	}

	public static NodeList DMS_S_SalesRep(String Company,String SalesRepcode)
	{
		String methodName ="DMS_S_SalesRep",tagname = "Table";
		String[]keys = {"Company","SalesRepcode"};
		String[]values  = {"01",SalesRepcode};	    
		NodeList nList = getDataTableFromService_Sync(methodName,tagname,keys,values);
		return nList;

	}
	
	public static NodeList DMS_S_ARInvoiceCollection(String FromDate,String ToDate)
	{
		String methodName ="DMS_S_ARInvoiceCollection",tagname = "DMS_S_ARInvoiceCollection";
		String[]keys = {"Company","FromDate","ToDate"};
		String[]values  = {"01",FromDate,ToDate};	    
		NodeList nList = getDataTableFromService_Sync(methodName,tagname,keys,values);
		return nList;

	}
	
	public static NodeList DMS_S_SalesOrderDetail(String OrderNum, String DMSOrderNum)
	{
		String methodName ="DMS_S_SalesOrderDetail",tagname = "DMS_S_SalesOrderDetail";
		String[]keys = {"OrderNum","DMSOrderNum"};
		String[]values  = {OrderNum,DMSOrderNum};	    
		NodeList nList = getDataTableFromService_Sync(methodName,tagname,keys,values);
		return nList;
	}

	public static NodeList DMS_S_ShipTo(String Company,String CustID,String ShiptoID)
	{
		String methodName ="DMS_S_ShipTo",tagname = "Table";
		String[]keys = {"Company","CustID","ShiptoID"};
		String[]values  = {"01",CustID,ShiptoID};	    
		
		NodeList nList = getDataTableFromService_Sync(methodName,tagname,keys,values);
		return nList;
	}
	
	public static NodeList DMS_S_Customer(String Company, String CustID, String DMSCode, String CustNum)
	{
		String methodName ="DMS_S_Customer",tagname = "Table";
		String[]keys = {"Company","CustID","DMSCode","CustNum"};
		String[]values  = {"01",CustID,DMSCode,CustNum};	    
		NodeList nList = getDataTableFromService_Sync(methodName, tagname, keys, values);
		return nList;
	}
	
	public static NodeList DMS_S_ProposalInfo()
	{
		String methodName ="DMS_S_ProposalInfo",tagname = "Table";
		String[]keys = {"Company","FromDate","ToDate"};
		String[]values  = {"","",""};	    
		NodeList nList = getDataTableFromService(methodName,tagname,keys,values);
		return nList;

	}
	
	public static NodeList DMS_S_ARInvoiceVoid(String Company,String FromDate,String ToDate)
	{
		String methodName ="DMS_S_ARInvoiceVoid",tagname = "DMS_S_ARInvoiceVoid";
		String[]keys = {"Company","FromDate","ToDate"};
		String[]values  = {"01",FromDate,ToDate};	    
		
		NodeList nList = getDataTableFromService_Sync(methodName,tagname,keys,values);
		return nList;
	}

	public static NodeList DMS_S_SalesOrderVoid(String Company,String FromDate,String ToDate)
	{
		String methodName ="DMS_S_SalesOrderVoid",tagname = "DMS_S_SalesOrderVoid";
		String[]keys = {"Company","FromDate","ToDate"};
		String[]values  = {"01",FromDate,ToDate};	    
		
		NodeList nList = getDataTableFromService_Sync(methodName,tagname,keys,values);
		return nList;
	}
	
	public static NodeList DMS_S_PartOnHand(String nppMa )
	{
		String methodName ="DMS_S_PartOnHand",tagname = "Table";
		String[]keys = {"Company","WareHouse","PartNum"};
		String[]values  = {"",nppMa,""};	     
		NodeList nList = getDataTableFromService(methodName,tagname,keys,values);
		return nList;

	}
	
	public static NodeList DMS_S_PartOnHand(String nppMa,String spMa )
	{
		String methodName ="DMS_S_PartOnHand",tagname = "Table";
		String[]keys = {"Company","WareHouse","PartNum"};
		String[]values  = {"01",nppMa,spMa};	     
		NodeList nList = getDataTableFromService(methodName,tagname,keys,values);
		return nList;

	}

	public static NodeList SendOrder(String orderId)
	{
		String methodName ="SendOrder",tagname = "SendOrder";
		String[]keys = {"orderId"};
		String[]values  = {orderId};	    
		NodeList nList = getDataTableFromService_Sync(methodName, tagname, keys, values);
		return nList;
	}
	
	public static NodeList SendOrderDistributor(String orderId)
	{
		String methodName ="SendOrderDistributor",tagname = "SendOrderDistributor";
		String[]keys = {"orderId"};
		String[]values  = {orderId};	    
		NodeList nList = getDataTableFromService_Sync(methodName, tagname, keys, values);
		return nList;
	}

	public static String getStringValue(Cell[] cells,int vitri)
	{
		try
		{
			return cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();
		}
		catch (Exception e) {
			return "";
		}
	}

	public static  double getDoubleValue(Cell[] cells,int vitri)
	{
		try
		{
			return Double.parseDouble(cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim());
		}
		catch (Exception e) {
			return 0.0;
		}
	}
	
	public static  Integer getIntValue(Cell[] cells,int vitri)
	{
		try
		{
			return Integer.parseInt(cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim());
		}
		catch (Exception e) {
			return 0;
		}
	}

	
	public static void main(String[] arg)
	{
		String methodName = "UpdateLincense";
		String tagname = "UpdateLincense";
		String[] keys = { "serverIP", "serverNAME", "company", "dateRegister", "licenseKEY" };
		String[] values  = { "11", "22", "33", "44", "55"};	    
		
		getDataTableFromService(methodName, tagname, keys, values);
		System.out.println(":: CHAY XONG... ");
	}
	
	
	
}

package geso.traphaco.center.util;
import geso.traphaco.center.db.sql.dbutils;

import java.io.ByteArrayOutputStream;
import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.*;
import org.w3c.dom.Node;

public class WebService 
{
	//public static String urlService = "http://192.168.1.15/ErpService/ServiceDMS.asmx";
	//public static String urlService = "http://10.242.2.6/ErpService/ServiceDMS.asmx";
	
	//public static String urlService = "http://192.168.0.252/ErpService/ServiceDMS.asmx";
	//public static String urlService = "http://192.168.0.252/ErpServiceTEST/ServiceDMS.asmx";
	
	public static String urlService = "http://123.30.245.50/ErpServiceTEST/ServiceDMS.asmx";
	//public static String urlService = "http://123.30.245.50/ErpServiceTEST/ServiceDMS.asmx";
	
	public final static String namespace = "http://tempuri.org/"; 
	
	public static String secrect = "geso@@tra@@90123";

	public static String getJSONDataFromService(String methodName, String tagname, String[] keys, String[] values )
	{
		SOAPConnectionFactory soapConnectionFactory = null;
		SOAPConnection soapConnection = null;
		try 
		{
			// Create SOAP Connection
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();

			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader("SOAPAction", namespace+(namespace.lastIndexOf("/") == namespace.length() - 1?"":"/") + methodName); // +methodName DangNhap_SalesUp

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

			/*ByteArrayOutputStream stream = new ByteArrayOutputStream();
			soapResponse.writeTo(stream);
			String message = new String(stream.toByteArray(), "utf-8"); */
			//System.out.println("::: message: " + message );
			
			String xml = WebService.printSoapMessage(soapResponse);
			//System.out.println("::: 1.XML: " + xml);
			
			//xml = "<GetSanPhamListResult>[{\"ma\":\"3KOOL\",\"ten\":\"Kool Fever\",\"donvi\":\"Hộp\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":5}]</GetSanPhamListResult>";
			if( xml.contains(methodName + "Result") && xml.contains( "/" + methodName + "Result") )
			{
				int startIndex = xml.indexOf("<" + methodName + "Result>");
				int endIndex = xml.indexOf("</" + methodName + "Result>");
				
				xml = xml.substring(startIndex + ( "<" + methodName + "Result>" ).length(), endIndex ); 
			}
			
			xml = "<JSONData>" + xml + "</JSONData>";
			String json = WebService.XMLtoJSON(xml);
			
			soapConnection.close();
			return json;
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
	
	public static NodeList getDataTableFromService(String methodName,String tagname,String[]keys,String[]values )
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


		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
			if(soapConnection != null) 
				try{soapConnection.close();}catch (Exception e2) {
				}
				return null;	
		}
	}	
	
	public static String getXMLDataFromService(String methodName, String tagname, String[] keys, String[] values )
	{
		SOAPConnectionFactory soapConnectionFactory = null;
		SOAPConnection soapConnection = null;
		try 
		{
			// Create SOAP Connection
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();

			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader("SOAPAction", namespace+(namespace.lastIndexOf("/") == namespace.length() - 1?"":"/") + methodName); // +methodName DangNhap_SalesUp

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

			String xml = WebService.printSoapMessage(soapResponse);
			
			soapConnection.close();
			return xml;
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
	
	public static String getStringDataFromService(String methodName, String tagname, String[] keys, String[] values )
	{
		SOAPConnectionFactory soapConnectionFactory = null;
		SOAPConnection soapConnection = null;
		try 
		{
			// Create SOAP Connection
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();

			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader("SOAPAction", namespace+(namespace.lastIndexOf("/") == namespace.length() - 1?"":"/") + methodName); // +methodName DangNhap_SalesUp

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

			String xml = WebService.printSoapMessage(soapResponse);
			//System.out.println("::: 1.XML: " + xml);
			
			//xml = "<GetSanPhamListResult>[{\"ma\":\"3KOOL\",\"ten\":\"Kool Fever\",\"donvi\":\"Hộp\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":5}]</GetSanPhamListResult>";
			if( xml.contains(methodName + "Result") && xml.contains( "/" + methodName + "Result") )
			{
				int startIndex = xml.indexOf("<" + methodName + "Result>");
				int endIndex = xml.indexOf("</" + methodName + "Result>");
				
				xml = xml.substring(startIndex + ( "<" + methodName + "Result>" ).length(), endIndex ); 
			}
			
			/*ByteArrayOutputStream stream = new ByteArrayOutputStream();
			soapResponse.writeTo(stream);
			String message = new String(stream.toByteArray(), "utf-8"); */
			//System.out.println("::: message: " + message );
			
			soapConnection.close();
			return xml;
		} 
		catch (Exception e) 
		{
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
			if(soapConnection != null) 
				try{soapConnection.close();}catch (Exception e2) {
				}
			
			e.printStackTrace();
			return e.getMessage();	
		}
	}
	
	public static String execStringDataFromService(String methodName, String tagname, String[] keys, String[] values )
	{
		SOAPConnectionFactory soapConnectionFactory = null;
		SOAPConnection soapConnection = null;
		try 
		{
			// Create SOAP Connection
			soapConnectionFactory = SOAPConnectionFactory.newInstance();
			soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();

			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader("SOAPAction", namespace+(namespace.lastIndexOf("/") == namespace.length() - 1?"":"/") + methodName); // +methodName DangNhap_SalesUp

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

			String xml = WebService.printSoapMessage(soapResponse);
			//System.out.println("::: 1.XML: " + xml);
			
			//xml = "<GetSanPhamListResult>[{\"ma\":\"3KOOL\",\"ten\":\"Kool Fever\",\"donvi\":\"Hộp\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":5}]</GetSanPhamListResult>";
			if( xml.contains(methodName + "Result") && xml.contains( "/" + methodName + "Result") )
			{
				int startIndex = xml.indexOf("<" + methodName + "Result>");
				int endIndex = xml.indexOf("</" + methodName + "Result>");
				
				xml = xml.substring(startIndex + ( "<" + methodName + "Result>" ).length(), endIndex ); 
			}
			
			/*ByteArrayOutputStream stream = new ByteArrayOutputStream();
			soapResponse.writeTo(stream);
			String message = new String(stream.toByteArray(), "utf-8"); */
			//System.out.println("::: message: " + message );
			
			soapConnection.close();
			return xml;
		} 
		catch (Exception e) 
		{
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
			if(soapConnection != null) 
				try{soapConnection.close();}catch (Exception e2) {
				}
			
			e.printStackTrace();
			return e.getMessage();	
		}
	}
	
	
	public static String printSoapMessage(final SOAPMessage soapMessage) throws TransformerFactoryConfigurationError,
	    TransformerConfigurationException, SOAPException, TransformerException
	{
		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
		final Transformer transformer = transformerFactory.newTransformer();
		
		// Format it
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
		final Source soapContent = soapMessage.getSOAPPart().getContent();
		
		final ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
		final StreamResult result = new StreamResult(streamOut);
		transformer.transform(soapContent, result);
		
		return streamOut.toString();
	}
	
	public static String XMLtoJSON(String xml) 
	{
		try
		{
		    JSONObject jsonObj = XML.toJSONObject(xml);
		    String json = jsonObj.toString();
		    return json;
		}
		catch(Exception ex)
		{
			return "";
		}
	}

	
	public static String ERP_GetTonKho( String dvkdId, String kbhId, String nppId, String khoId, String filter, String secrect )
	{
		String methodName = "GetSanPhamList2", tagname = "Table";
		String[] keys = { "dvkdId", "kbhId", "nppId", "khoId", "filter", "secrect" };
		String[] values = { dvkdId, kbhId, nppId, khoId, filter, secrect };	     
		//String json = getJSONDataFromService(methodName, tagname, keys, values);
		//return json;
		
		String xml = getXMLDataFromService(methodName, tagname, keys, values);
		return xml;
	}
	
	public static NodeList ERP_GetTonKho2( String dvkdId, String kbhId, String nppId, String khoId, String filter, String secrect )
	{
		String methodName = "GetSanPhamList2", tagname = "SanPhamList";
		String[] keys = { "dvkdId", "kbhId", "nppId", "khoId", "filter", "secrect" };
		String[] values = { dvkdId, kbhId, nppId, khoId, filter, secrect };	     
		//String json = getJSONDataFromService(methodName, tagname, keys, values);
		//return json;
		
		NodeList nList = getDataTableFromService(methodName,tagname,keys,values);
		return nList;
	}
	
	public static NodeList ExecQueryFromDMS( String query, String secrect )
	{
		String methodName = "GetDataDMS", tagname = "DataDMS";
		String[] keys = { "query", "secrect" };
		String[] values = { query, secrect };	     

		NodeList nList = getDataTableFromService(methodName, tagname, keys, values);
		return nList;
	}
	
	public static String SynDataFromERP( String query )
	{
		String methodName = "SynDataFromERP", tagname = "DataERP";
		String[] keys = { "query", "secrect"  };
		String[] values = { query, WebService.secrect };	     

		String msg = execStringDataFromService(methodName, tagname, keys, values);
		return msg;
	}
	
	public static NodeList DeXuatLo(String khott_fk, String spMa, String ngaychungtu, String solo, String ngayhethan, String vitri, String secrect )
	{
		String methodName = "DeXuatLoERP", tagname = "DataERP";
		String[] keys = { "khott_fk", "spMa", "ngaychungtu", "solo", "ngayhethan", "vitri", "secrect",  };
		String[] values = { khott_fk, spMa, ngaychungtu, solo, ngayhethan, vitri, secrect };	     

		NodeList nList = getDataTableFromService(methodName,tagname,keys,values);
		return nList;
	}
	
	public static String Update_KhoTT(  String ngayyeucau, String ghichu, dbutils db, String khott_fk, 
										String spId, String solo, String ngayhethan, String ngaynhapkho,
										String MAME, String MATHUNG, String vitri,  
										String MAPHIEU, String phieudt, String phieueo, 
										String MARQ, String HAMLUONG, String HAMAM, 
										String loaidoituong, String doituongId,
										double soluong, double booked, double available,
										String loaichungtu, String chungtuId, String transactionId, String secrect )
	{
		String methodName = "Update_KhoTT", tagname = "DataERP";
		
		String[] keys = { 	"ngayyeucau",  "ghichu", "khott_fk", "spId",  "solo",  "ngayhethan",  "ngaynhapkho",
							"MAME",  "MATHUNG",  "vitri",  "MAPHIEU",  "phieudt",  "phieueo", 
							"MARQ",  "HAMLUONG",  "HAMAM", "loaidoituong",  "doituongId", "soluong",  "booked",  "available",
							"loaichungtu", "chungtuId", "transactionId", "secrect"  };
		
		String[] values = {  ngayyeucau,  ghichu, khott_fk, spId,  solo,  ngayhethan,  ngaynhapkho,
							 MAME,  MATHUNG,  vitri,  MAPHIEU,  phieudt,  phieueo, 
							 MARQ,  HAMLUONG,  HAMAM, loaidoituong, doituongId, Double.toString(soluong),  Double.toString(booked),  Double.toString(available), 
							 loaichungtu, chungtuId, transactionId, secrect };	     

		String msg = getStringDataFromService(methodName, tagname, keys, values);
		return msg;
	}
	
	public static String Revert_KhoTT( String loaichungtu, String chungtuId, String transactionId, String secrect )
	{
		String methodName = "Update_KhoTT_Revert", tagname = "DataERP";
		
		String[] keys = { "loaichungtu", "chungtuId", "transactionId", "secrect"   };
		String[] values = { loaichungtu, chungtuId, transactionId, secrect };	     
		
		String msg = getStringDataFromService(methodName, tagname, keys, values);
		return msg;
	}
	
	public static String CheckNetwork(  String secrect )
	{
		String methodName = "CheckNetwork", tagname = "";
		
		String[] keys = { 	 "secrect"  };
		String[] values = {   secrect };	     
		
		String msg = getStringDataFromService(methodName, tagname, keys, values);
		return msg;
	}
	
	
	
	public static Node[] convertToArray(NodeList list)
	{
		int length = list.getLength();
		Node[] copy = new Node[length];

		for (int n = 0; n < length; ++n)
			copy[n] = list.item(n);

		return copy;
	}

	public static void main(String[] arg)
	{
		String dvkdId = "100001"; 
		String kbhId = "100025";
		String nppId = "1";
		String khoId = ""; 
		String filter = "3K";
		String secrect = "geso@@tra@@90123";
		
		/*String msg = WebService.CheckNetwork(secrect);
		System.out.println("::: CHECK: " + msg);*/
		
		/*NodeList nodes = WebService.ERP_GetTonKho2(dvkdId, kbhId, nppId, khoId, filter, secrect);*/
		
		String khott_fk = "100023"; 
		String spMa = "6AMP1";
		String ngaychungtu = "2016-12-19";
		String solo = "1016";
		String ngayhethan = "2019-09-23";
		String vitri = "";
		NodeList nodes = WebService.DeXuatLo(khott_fk, spMa, ngaychungtu, solo, ngayhethan, vitri, secrect);
		for (int i = 0; i < nodes.getLength(); i++) 
	    {
	      Element element = (Element) nodes.item(i);

	      System.out.println(WebService.getValues(element, "KHOTT_FK"));
	      System.out.println(WebService.getValues(element, "sanpham_fk"));
	      System.out.println(WebService.getValues(element, "SOLO"));
	      System.out.println(WebService.getValues(element, "NGAYHETHAN"));
	      System.out.println(WebService.getValues(element, "ngaynhapkho"));
	      System.out.println(WebService.getValues(element, "MARQ"));
	      System.out.println(WebService.getValues(element, "HAMLUONG"));
	      System.out.println(WebService.getValues(element, "HAMAM"));
	      
	      System.out.println(WebService.getValues(element, "MAPHIEU"));
	      System.out.println(WebService.getValues(element, "phieudt"));
	      System.out.println(WebService.getValues(element, "phieueo"));
	      
	      System.out.println(WebService.getValues(element, "vitri"));
	      System.out.println(WebService.getValues(element, "MAME"));
	      System.out.println(WebService.getValues(element, "MATHUNG"));
	      
	      System.out.println(":::------------------------- ");
	      
	    }
		
		/*String json = WebService.ERP_GetTonKho(dvkdId, kbhId, nppId, khoId, filter, secrect);
		System.out.println("KQ: " + json );
		
		try
		{
			// {"JSONData":"[{\"ma\":\"3KOOL\",\"ten\":\"Kool Fever\",\"donvi\":\"Hộp\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":5},{\"ma\":\"2TUO3\",\"ten\":\"Túi  loại 03kg\",\"donvi\":\"KG\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KEO1\",\"ten\":\"Kéo cán nhựa dài  23 cm\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KHA1\",\"ten\":\"Khăn tắm coton mịn,dày 78 x36 cm\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KHA2\",\"ten\":\"Khăn lau viên\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KHA3\",\"ten\":\"Khăn sấy viên\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KHA4\",\"ten\":\"Khẩu trang chống độc\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KHA5\",\"ten\":\"Khẩu trang vải\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KIN1\",\"ten\":\"Kính bảo hộ (trắng)\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KHEO\",\"ten\":\"Khí EO\",\"donvi\":\"KG\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":null},{\"ma\":\"3KEPS1\",\"ten\":\"Kẹp số kiểm soát\",\"donvi\":\"Bộ\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":10},{\"ma\":\"3KHAY1\",\"ten\":\"Khay inox\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":10},{\"ma\":\"3KHOI1\",\"ten\":\"Khởi động từ\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":10},{\"ma\":\"3KHOP1\",\"ten\":\"Khớp nối\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":10},{\"ma\":\"3KHOV1\",\"ten\":\"Khoá van bướm inox\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":10},{\"ma\":\"TUTS0BT\",\"ten\":\"Tủ TS03-3K\",\"donvi\":\"Chiếc\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":null},{\"ma\":\"3KHUI1\",\"ten\":\"Khung Inox (304)\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":10},{\"ma\":\"2TU03\",\"ten\":\"Túi lọai 3kg\",\"donvi\":\"KG\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KHOA01\",\"ten\":\"Khóa đấm\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KEO01\",\"ten\":\"Keo Silicol\",\"donvi\":\"Lọ\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KEPI1\",\"ten\":\"Kẹp Inox\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0},{\"ma\":\"3KBLU\",\"ten\":\"Khẩu trang Blu giấy\",\"donvi\":\"Cái\",\"trongluong\":0,\"thetich\":0,\"qc\":1,\"giamua\":0.00,\"thuexuat\":0}]"}
			
			json = "{ " +
				   "		\"age\":100,  " +
				   "		\"name\":\"mkyong.com\",  " +
				   "		\"JSONData\":[\"msg 1\",\"msg 2\",\"msg 3\"]  " +
				   "} ";
			
			//READ JSON DATA
			JSONObject jsonObject = new JSONObject(json);
			
			System.out.println("::: DATA: " +  jsonObject.get("JSONData"));
			
			//Trong bản thân mỗi item lại có thể là 1 JSON khác
			
			JSONArray jArray = (JSONArray) jsonObject.get("JSONData");
			System.out.println("Length: " + jArray );
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}*/
		
		
		//READ XML
		try 
		{
			/*File fXmlFile = new File("/Users/mkyong/staff.xml");*/
			
			/*<company>
				<staff id="1001">
					<firstname>yong</firstname>
					<lastname>mook kim</lastname>
					<nickname>mkyong</nickname>
					<salary>100000</salary>
				</staff>
				<staff id="2001">
					<firstname>low</firstname>
					<lastname>yin fong</lastname>
					<nickname>fong fong</nickname>
					<salary>200000</salary>
				</staff>
			</company>*/
			
			/*String fXmlFile = WebService.ERP_GetTonKho(dvkdId, kbhId, nppId, khoId, filter, secrect);
			
			fXmlFile =  " <?xml version=\"1.0\"?> "+
						" <company>	" +
						" <SanPhamList id='1' > "+
						"     <ma>3KOOL</ma> "+
						"     <ten>Kool Fever</ten> "+
						"     <donvi>Hộp</donvi> "+
						"     <trongluong>0</trongluong> "+
						"     <thetich>0</thetich> "+
						"     <qc>1</qc> "+
						"     <giamua>0.00</giamua> "+
						"     <thuexuat>5</thuexuat> "+
						" </SanPhamList> "+
						" </company> ";
			
			fXmlFile =  " <company> "+
						" 	<staff id=\"1001\"> "+
						" 		<firstname>yong</firstname> "+
						" 		<lastname>mook kim</lastname> "+
						" 		<nickname>mkyong</nickname> "+
						" 		<salary>100000</salary> "+
						" 	</staff> "+
						" </company> ";
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("staff");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					//System.out.println("Staff id : " + eElement.getAttribute("id"));
					System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
					System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
					System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
					System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

				}
			}*/
			
			
			/*String xmlRecords = "<data><employee><name>A</name><title>Manager</title></employee><employee><name>B</name><title>Staff</title></employee></data>";

			xmlRecords =  WebService.ERP_GetTonKho(dvkdId, kbhId, nppId, khoId, filter, secrect);
			
		    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    InputSource is = new InputSource();
		    is.setCharacterStream(new StringReader(xmlRecords));

		    Document doc = db.parse(is);
		    NodeList nodes = doc.getElementsByTagName("SanPhamList");

		    for (int i = 0; i < nodes.getLength(); i++) 
		    {
		      Element element = (Element) nodes.item(i);

		      NodeList name = element.getElementsByTagName("ma");
		      Element line = (Element) name.item(0);
		      System.out.println("Name: " + getCharacterDataFromElement(line));

		      NodeList title = element.getElementsByTagName("ten");
		      System.out.println("Title: " + title.item(0).getTextContent() );
		    }*/
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static String getCharacterDataFromElement(Element e) 
	{
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	      CharacterData cd = (CharacterData) child;
	      return cd.getData();
	    }
	    return "";
	}

	public static String getValues(Element element, String tagName) 
	{
		try
		{
			return element.getElementsByTagName( tagName ).item(0).getTextContent();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "";
		}
		
	}
	
	public static double getDouble(Element element, String tagName) 
	{
		try
		{
			return Double.parseDouble( element.getElementsByTagName( tagName ).item(0).getTextContent() );
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
		
	}
	

}
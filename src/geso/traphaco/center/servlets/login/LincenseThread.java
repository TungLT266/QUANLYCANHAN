package geso.traphaco.center.servlets.login;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.DbInfo;
import geso.traphaco.center.util.SendMail;

import java.net.InetAddress;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.rp.util.DateTime;
 
public class LincenseThread implements Runnable   
{
	private ServletContext context;
	InetAddress ip;
	String company;
	String dateRegister;
	String licenseKey;
	 
    public LincenseThread(ServletContext context, String company, String dateRegister, String licenseKey) 
    {
        this.context = context;
        this.company = company;
        this.dateRegister = dateRegister;
        this.licenseKey = licenseKey;
    }
 
    @Override
    public void run() 
    {
        try 
        {
        	ip = InetAddress.getLocalHost();
        	
        	DateTime dt = new DateTime();  // current time
        	String hour = dt.getHour();     // gets the current month
        	String date = dt.getDate(); // gets hour of day
        	String minute=dt.getMinute();
        	//System.out.println("...Date: " + date + " Hour: " + hour);
        	//System.out.println("...DANG CHAY....IP: " + ip.getHostAddress() + " -- HostName: " + ip.getHostName() + " -- Lincense Key: " + this.licenseKey );
        	
        	//Tùy chỉnh thời gian chạy chỗ này, test cho mỗi ngày chạy 1 lần vào 22 giờ đêm
        /*	if( hour.equals("22") )
        	{
        		this.updateLicense(ip.getHostAddress(), ip.getHostName(), this.company, this.dateRegister, this.licenseKey  );
        	}
        	else  //Trong thoi gian huan luyen dong bo 2 lan, buoi trua va buoi toi
        	{
        		if( hour.equals("12") || hour.equals("23") )
        		{
        			this.SynData( hour );
        		}
        	}*/
        	
        	/*if( ( hour.equals("19") && Integer.parseInt(minute) <10) ||  ( hour.equals("12") && Integer.parseInt(minute) <10)  ) {
      		  this.Gui_email_check_PSKT_FIT();
      	  	}*/
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    private   void Gui_email_check_PSKT_FIT() {
		// TODO Auto-generated method stub
    	dbutils db=new dbutils();
    	if(!DbInfo.url.equals("jdbc:jtds:sqlserver://222.254.35.21:1433/TraphacoERP_HUNGYEN")) {
			System.out.println("comhere : return khonog phai link that ");
			return;
		}
		try{
 
			String[] par=new String[2];
			par[0]="";
			par[1]="";
			String msg1= db.execProceduce2("[Pro_check_phatsinh_ketoan]", par);
			String  query=" select distinct sochungtu,loaichungtu,'' as ngayghinhan,nguoixuly   "
					+ " from TBL_DOICHIEUKETOAN where   CONVERT (char(10),create_date ,126) = CONVERT (char(10),getdate() ,126)  ";
			  ResultSet rs=db.get(query);
			  
				String  table_str= " Dear all <br>"
						+ "  Xin lỗi vì đã làm phiền, đây là email nhắc nhở lỗi phát sinh về hệ thống TUELINH ERP, đề nghị người có tên chịu trách nhiệm tương ứng các mục bên dưới xứ lý trong thời gian 4 giờ <br>  "
						+ "<TABLE border=1  > " +
						  "<tr> " +
						  " <th> Số chứng từ  </th>" +
						  " <th> Loại chứng từ </th>" +
						  " <th> số lượng lệch </th>" +
						  " <th> Ngày ghi nhận </th>" +
						  " <th> Người xử lý </th>" +
						  "</tr>  ";
				boolean bien_go=false;
				
			  while(rs.next()){
				  if(rs.getDouble("soluong")>0)
				  {
					  table_str+= "<tr> " +
							  " <th> "+rs.getString("sochungtu")+" </th>" +
							  " <th> "+rs.getString("loaichungtu")+" </th>" +
							  " <th> "+rs.getString("soluong")+" </th>" +
							  " <th> "+rs.getString("ngayghinhan")+" </th>" +
							  " <th> "+rs.getString("nguoixuly")+" </th>" +
						  "</tr>  ";
					  bien_go=true;
				  }
				 
			  }
			  rs.close();
			  table_str+="</TABLE> <br> "
			  		+ " Xin chân thành cảm ơn ";
			
					SendMail sendMAIL = new SendMail();
					 query=" select distinct email  from TBL_DOICHIEUKETOAN where   CONVERT (char(10),create_date ,126) = CONVERT (char(10),getdate() ,126) and email<>'khoand@geso.us' ";
					 rs=db.get(query);
					// String stremail="haind@geso.us,maiptn@geso.us,khoand@geso.us";
					 String stremail="khoand@geso.us";
					 while(rs.next()){
						 stremail+=","+rs.getString("email");
						 
					 }
					 rs.close();
					 String[] str_email=stremail.split(",");
					 
					 String cc="";
					 if(bien_go){
						 sendMAIL.postMail_Phatsinhkettoan_FIT(str_email,cc,  "LOI HE THONG TRAHY ", table_str);
					 } else{
						// str_email= new String[]{"lamtt@geso.us"};
						 
						// sendMAIL.postMail_Phatsinhkettoan_FIT(str_email,"khoand@geso.us",  "THONG BAO GUI EMAIL  TRAHY ERP DA CHECK KHONG CO LOI ", " Không có lỗi nhé.");
					 }
			
		
			
		}catch(Exception er){
			er.printStackTrace();
			String query="insert into Mail_err (msg)values(N'Gui email check phat sinh : "+er.getMessage()+"')";
        	db.update(query);
        	
		}finally{
			db.shutDown();
		}
		
	}
    private void SynData( String hour ) 
    {
		//Check gio do da SYN CHUA
    	
    	//SYN KHACH HANG MOI
    	
		
	}

	private void updateLicense(String hostAddress, String hostName, String company, String dateRegister, String licenseKey)
    {
    	String methodName = "UpdateLincense";
		String tagname = "UpdateLincense";
		String[] keys = { "serverIP", "serverNAME", "company", "dateRegister", "licenseKEY" };
		String[] values  = { hostAddress, hostName, company, dateRegister, licenseKey};	    
		
		getDataTableFromService(methodName, tagname, keys, values);		
	}
    
    //SOAP SERVICE
    public final static String namespace ="http://tempuri.org/";
	public static String urlService = "http://115.79.59.115:8089/License/LicenseService.asmx";
	
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

 
}
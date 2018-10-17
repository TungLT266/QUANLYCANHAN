package geso.traphaco.distributor.servlets.jasper;

import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.InputStream;

import net.sf.jasperreports.engine.*;
@WebServlet("/Jasper_HoaDonPdfSvl")
public class Jasper_HoaDonPdfSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public Jasper_HoaDonPdfSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		dbutils db=new dbutils();
		   HttpSession session = request.getSession();
		   String userId = (String) session.getAttribute("userId");
		   if (userId.length() > 0){
				
				DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance(); JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.awt.igno‌​re.missing.font","true"); 
				 ServletOutputStream servletOutputStream = response.getOutputStream();
				 String hoadonId= request.getParameter("pdf");
						 //request.getParameter("pdf1")==null?"0":request.getParameter("pdf1");
				 System.out.println("hoa don id "+hoadonId);
				 HashMap<String, Object> parameterMap = new HashMap<String, Object>();
				 String ddh="";
				 System.out.println(hoadonId+"--------------------"+ddh);
					parameterMap.put("hoadonid", hoadonId);
					parameterMap.put("loai", 2);
					parameterMap.put("ddhid", ddh);
					try
					{				
						InputStream reportStream = getServletConfig().getServletContext().getResourceAsStream("/reports/RPTR13P.jasper");
						JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parameterMap, db.getConnection());
						db.shutDown();
						response.setContentType("application/pdf");
						servletOutputStream.flush();
						servletOutputStream.close();
						
						
					} catch (Exception e)
					{
						// display stack trace in the browser
						StringWriter stringWriter = new StringWriter();
						PrintWriter printWriter = new PrintWriter(stringWriter);
						e.printStackTrace(printWriter);
						response.setContentType("text/plain");
						response.getOutputStream().print(stringWriter.toString());
						e.printStackTrace();
					}
								return;
			}
				
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}
	
	public static void main(String[] arg)
	{
		
	}
}

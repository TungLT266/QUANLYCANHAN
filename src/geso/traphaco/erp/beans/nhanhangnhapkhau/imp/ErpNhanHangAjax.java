package geso.traphaco.erp.beans.nhanhangnhapkhau.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/ErpNhanHangAjax")
public class ErpNhanHangAjax extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpNhanHangAjax()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		response.getWriter().write("Hello World!");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String q = util.antiSQLInspection(request.getParameter("SanPhamId"));
		String id = util.antiSQLInspection(request.getParameter("MuaHangId"));
		String nppId = (String)session.getAttribute("nppId");
		
		if(id.equals("ddkdTen")) //loc dai dien kinh doanh
		{
			//System.out.println("Giam sat ban hang Id la: " + q + "\n");
			if(q != null)
			{
				dbutils db = new dbutils();
				String query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai = '1' and npp_fk = '" + nppId + "' and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk from nhapp_giamsatbh where gsbh_fk ='" + q + "') )";
				
				//System.out.println("Query lay dai dien kinh doanh la: " + query + "\n");
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						out.write("<option value=''></option>");
						while(rs.next())
						{
							out.write("<option value='" + rs.getString("ddkdId") + "'>" + rs.getString("ddkdTen") + "</option>");
						}
					} 
					catch (SQLException e) {}
				}
			}
		}
		else
		{
			if(id.equals("smartId"))
			{
				//System.out.println("Dai dien kinh doanh Id la: " + q + "\n");
				session.setAttribute("ddkdId", q);
			}
		}
	}

}

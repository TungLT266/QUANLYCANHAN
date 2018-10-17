package geso.erp.servlets.congdoansanxuat;

import geso.dms.center.util.Utility;
import geso.dms.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpAjaxCongDoanSxSvl")
public class ErpAjaxCongDoanSxSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpAjaxCongDoanSxSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		if (action == null)
		{
			action = "";
		}

		if (action.equals("ajaxBom"))
		{
			System.out.println("ajax Bom_______________");
			PrintWriter out = response.getWriter();
			String spId = util.antiSQLInspection(request.getParameter("spId"));
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (spId != null)
			{
				dbutils db = new dbutils();
				String query = "select * from erp_Danhmucvattu where trangthai='1' and sanpham_fk='" + spId + "'";
				ResultSet rs = db.get(query);
				if (rs != null)
				{
					try
					{
						out.write("<option value=''></option>");
						while (rs.next())
						{
							out.write("<option value='" + rs.getString("pk_seq") + "'>" + rs.getString("DienGiai") +
								"</option>");
						}
					} catch (Exception e)
					{	
						e.printStackTrace();
					}
				}
				System.out.println("query BOM "+query);
			}
			
		}
	}

}

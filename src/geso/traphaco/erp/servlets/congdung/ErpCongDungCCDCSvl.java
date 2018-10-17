package geso.traphaco.erp.servlets.congdung;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.congdung.IErpCongDungCCDC;
import geso.traphaco.erp.beans.congdung.imp.ErpCongDungCCDC;
import geso.traphaco.erp.db.sql.dbutils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCongDungCCDCSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	
	
	public ErpCongDungCCDCSvl( )
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do get ErpCongDungSvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		if (userId.length() == 0) userId = util.antiSQLInspection(request.getParameter("userId"));
		String action = util.getAction(querystring);
		String id = util.getId(querystring);
		
		IErpCongDungCCDC cd = new ErpCongDungCCDC();
		cd.setCtyId((String)session.getAttribute("congtyId"));
		System.out.println("cong ty id: " + (String)session.getAttribute("congtyId"));
		if (action.equals("delete"))
		{
			dbutils db = new dbutils();
			String query = "delete Erp_CongDungCCDC where pk_seq= '" + id+ "'";
			System.out.print(query);
			if(!db.update(query))
			{
				cd.setMsg("Công dụng này đã phát sinh dữ liệu nên không thể xóa");
				System.out.println("Công dụng này đã phát sinh dữ liệu nên không thể xóa");
			}
			db.shutDown();
		}
		
		cd.setCtyId((String)session.getAttribute("congtyId"));
		System.out.println("cong ty id: " + cd.getCtyId());
		cd.Search();
		cd.CreateRs();
		session.setAttribute("cd", cd);
		
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDungCCDC.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do post ErpCongDungCCDCSvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		IErpCongDungCCDC cd = new ErpCongDungCCDC();

		String nextJSP = "";
		String action = request.getParameter("action");
		String ma = util.antiSQLInspection(request.getParameter("Ma"));
		String ten = util.antiSQLInspection(request.getParameter("Ten"));
		String taikhoan = util.antiSQLInspection(request.getParameter("TaiKhoan"));
		
		if (action == null) action = "";
		
		cd.setTaiKhoan(taikhoan);
		cd.setMa(ma);
		cd.setTen(ten);
		cd.setCtyId((String)session.getAttribute("congtyId"));
		
		if (action.equals("New"))
		{
			IErpCongDungCCDC cdBean = new ErpCongDungCCDC();
			cdBean.setCtyId((String)session.getAttribute("congtyId"));
			cdBean.CreateRs();
			cd.Close();
			session.setAttribute("cdBean", cdBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDungCCDCNew.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			cd.Search();
			cd.CreateRs();
			session.setAttribute("cd", cd);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongDungCCDC.jsp";
			response.sendRedirect(nextJSP);
		}		
	}
}

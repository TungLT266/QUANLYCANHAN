package geso.traphaco.erp.servlets.loaitaikhoan;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaitaikhoan.IErpLoaiTaiKhoan;
import geso.traphaco.erp.beans.loaitaikhoan.imp.ErpLoaiTaiKhoan;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ErpLoaiTaiKhoanUpdateSvl")
public class ErpLoaiTaiKhoanUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public ErpLoaiTaiKhoanUpdateSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do get! ErpLoaiTaiKhoanUpdateSvl");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility util;
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
			String nextJSP = "";
			util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			
			IErpLoaiTaiKhoan ltkBean = new ErpLoaiTaiKhoan(id);
			ltkBean.setUserId(userId);
			
			String nppId = util.getIdNhapp(userId);
		    if(nppId == null)
		    	nppId = "";
		    ltkBean.setnppId(nppId);
		    
		    String congtyId = session.getAttribute("congtyId").toString();
		    if(congtyId == null)
		    	congtyId = "";
		    ltkBean.setcongtyId(congtyId);	  
		    
			if (request.getQueryString().indexOf("display") >= 0)
			{
				ltkBean.Init();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiTaiKhoanDisplay.jsp";
			}
			else
				if (request.getQueryString().indexOf("update") >= 0)
				{
					ltkBean.Init();
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiTaiKhoanUpdate.jsp";
				}
			System.out.println("Ban dang o trang" + nextJSP);
			session.setAttribute("ltkBean", ltkBean);
			response.sendRedirect(nextJSP);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do Post ErpLoaiTaiKhoanUpdateSvl!");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility util = (Utility) session.getAttribute("util");
		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			String nextJSP = "";
			IErpLoaiTaiKhoan ltkBean = new ErpLoaiTaiKhoan();
			String Id = util.antiSQLInspection(request.getParameter("id"));
			String Ma = util.antiSQLInspection(request.getParameter("Ma"));
			String Ten = util.antiSQLInspection(request.getParameter("Ten"));
			String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
			String action = request.getParameter("action");
			
			String nppId = util.getIdNhapp(userId);
		    if(nppId == null)
		    	nppId = "";
		    ltkBean.setnppId(nppId);
		    
		    String congtyId = session.getAttribute("congtyId").toString();
		    if(congtyId == null)
		    	congtyId = "";
		    ltkBean.setcongtyId(congtyId);	  
			
			if (action == null)
			{
				action = "";
			}
			if (Ma == null)
			{
				Ma = "";
			}
			if (Ten == null)
			{
				Ten = "";
			}
			if (trangthai == null)
			{
				trangthai = "0";
			}
		
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			ltkBean.setId(Id);
			System.out.println("Id: " + Id);
			
			ltkBean.setMa(Ma);
			ltkBean.setTen(Ten);
			ltkBean.setTrangThai(trangthai);
			ltkBean.setUserId(userId);
			
			if (action.equals("Create"))
			{
				System.out.println("Tao moi");
				if (ltkBean.Create())
				{
					System.out.println("Tao duoc");
					IErpLoaiTaiKhoan ltk = new ErpLoaiTaiKhoan();
					ltk.setnppId(nppId);
					ltk.setcongtyId(congtyId);	  
					ltk.search();
					session.setAttribute("ltk", ltk);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiTaiKhoan.jsp";
				}
				else
				{
					session.setAttribute("ltkBean", ltkBean);
					System.out.println("Khong tao  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiTaiKhoanNew.jsp";
				}
			}
			if (action.equals("Update"))
			{
				System.out.println("Update ErpLoaiTaiKhoanUpdateSvl ");
				if (!ltkBean.Update())
				{
					session.setAttribute("ltkBean", ltkBean);
					System.out.println("Khong Update  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiTaiKhoanUpdate.jsp";
				}
				else
				{
					System.out.println("Update duoc");
					IErpLoaiTaiKhoan ltk = new ErpLoaiTaiKhoan();
					ltk.search();
					session.setAttribute("ltk", ltk);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiTaiKhoan.jsp";
				}
			}
			response.sendRedirect(nextJSP);
		}
	}
}

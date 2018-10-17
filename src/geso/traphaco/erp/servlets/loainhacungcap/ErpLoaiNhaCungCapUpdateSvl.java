package geso.traphaco.erp.servlets.loainhacungcap;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loainhacungcap.IErpLoaiNhaCungCap;
import geso.traphaco.erp.beans.loainhacungcap.imp.ErpLoaiNhaCungCap;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ErpLoaiNhaCungCapUpdateSvl")
public class ErpLoaiNhaCungCapUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public ErpLoaiNhaCungCapUpdateSvl()
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
			
			IErpLoaiNhaCungCap lnccBean = new ErpLoaiNhaCungCap(id);
			
			lnccBean.setUserId(userId);
			if (request.getQueryString().indexOf("display") >= 0)
			{
				lnccBean.Init();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiNhaCungCapDisplay.jsp";
			}
			else if (request.getQueryString().indexOf("update") >= 0)
			{
				lnccBean.Init();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiNhaCungCapUpdate.jsp";
			}
			System.out.println("Ban dang o trang" + nextJSP);
			session.setAttribute("lnccBean", lnccBean);
			response.sendRedirect(nextJSP);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do Post ErpLoaiNhaCungCapUpdateSvl!");
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
			IErpLoaiNhaCungCap lnccBean = new ErpLoaiNhaCungCap();
			String Id = util.antiSQLInspection(request.getParameter("id"));
			String Ma = util.antiSQLInspection(request.getParameter("Ma"));
			String Ten = util.antiSQLInspection(request.getParameter("Ten"));
			String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
			
			String action = request.getParameter("action");
			
			
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
			lnccBean.setId(Id);
			System.out.println("Id: " + Id);
			
			lnccBean.setMa(Ma);
			lnccBean.setTen(Ten);
			lnccBean.setTrangThai(trangthai);
			lnccBean.setUserId(userId);
			
			if (action.equals("Create"))
			{
				System.out.println("Tao moi");
				if (lnccBean.Create())
				{
					System.out.println("Tao duoc");
					IErpLoaiNhaCungCap lncc = new ErpLoaiNhaCungCap();
					lncc.search();
					session.setAttribute("lncc", lncc);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiNhaCungCap.jsp";
				}
				else
				{
					session.setAttribute("lnccBean", lnccBean);
					System.out.println("Khong tao  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiNhaCungCapNew.jsp";
				}
			}
			if (action.equals("Update"))
			{
				System.out.println("Update ErpLoaiNhaCungCapUpdateSvl ");
				if (!lnccBean.Update())
				{
					session.setAttribute("lnccBean", lnccBean);
					System.out.println("Khong Update  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiNhaCungCapUpdate.jsp";
				}
				else
				{
					System.out.println("Update duoc");
					IErpLoaiNhaCungCap ltk = new ErpLoaiNhaCungCap();
					ltk.search();
					session.setAttribute("ltk", ltk);
//					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiNhaCungCap.jsp";
					nextJSP = "/../TraphacoHYERP/ErpLoaiNhaCungCapSvl?userId="+userId;
				}
			}
			
			System.out.println(nextJSP);
			response.sendRedirect(nextJSP);
		}
	}
}

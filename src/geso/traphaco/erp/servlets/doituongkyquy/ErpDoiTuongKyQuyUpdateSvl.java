package geso.traphaco.erp.servlets.doituongkyquy;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doituongkyquy.IErpDoiTuongKyQuy;
import geso.traphaco.erp.beans.doituongkyquy.imp.ErpDoiTuongKyQuy;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDoiTuongKyQuyUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	
	public ErpDoiTuongKyQuyUpdateSvl( )
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do get! ErpDoiTuongKyQuyUpdateSvl");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		String ctyId = (String)session.getAttribute("congtyId");
		
		IErpDoiTuongKyQuy lspBean;
				
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
			String nextJSP = "";
			
			String querystring = request.getQueryString();
			userId = cutil.getUserId(querystring);
			
			if (userId.length() == 0) userId = cutil.antiSQLInspection(request.getParameter("userId"));
			
			String id = cutil.getId(querystring);
			
			lspBean = new ErpDoiTuongKyQuy(id);
			lspBean.setCtyId(ctyId);
						
			lspBean.setUserId(userId);
			if (request.getQueryString().indexOf("display") >= 0)
			{
		
				lspBean.Init();
				lspBean.CreateRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKyQuyDisplay.jsp";
			}
			else
				if (request.getQueryString().indexOf("update") >= 0)
				{
					lspBean.Init();
					lspBean.CreateRs();
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKyQuyUpdate.jsp";
				}
			
			System.out.println("Ban dang o trang" + nextJSP);
			
			session.setAttribute("lspBean", lspBean);
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do Post ErpDoiTuongKyQuyUpdateSvl!");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		
		String ctyId = (String)session.getAttribute("congtyId");
		Utility util = (Utility) session.getAttribute("util");
		
		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			String nextJSP = "";
			IErpDoiTuongKyQuy lspBean;
			
			String action = request.getParameter("action");
			String ma = util.antiSQLInspection(request.getParameter("ma"));
			String ten = util.antiSQLInspection(request.getParameter("ten"));
			String taikhoan = util.antiSQLInspection(request.getParameter("taikhoan"));
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			String Id = util.antiSQLInspection(request.getParameter("id"));
			
			lspBean = new ErpDoiTuongKyQuy();
			
			if (action == null) action = "";
			if (trangthai == null) trangthai = "0";
			else
				trangthai = "1";
			
			lspBean.setId(Id);
			lspBean.setCtyId(ctyId);
			lspBean.setMa(ma);
			lspBean.setTen(ten);
			lspBean.setTaiKhoan(taikhoan);
			lspBean.setUserId(userId);
			lspBean.setTrangThai(trangthai);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
		
			if (action.equals("Create"))
			{
				System.out.println("Tao moi");
				if (lspBean.Create())
				{
					System.out.println("Tao duoc");
					IErpDoiTuongKyQuy lspList = new ErpDoiTuongKyQuy();
					lspList.setCtyId(ctyId);
					//lspList.Search();
					lspList.Search( session.getAttribute("congtyId").toString() );
					lspList.CreateRs();
					session.setAttribute("lspList", lspList);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKyQuy.jsp";
				}
				else
				{
					System.out.println("Khong tao  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKyQuyNew.jsp";
				}
			}
			else
				if (action.equals("Update"))
				{
					System.out.println("Update ErpDoiTuongKyQuyUpdateSvl ");
					if (!lspBean.Update())
					{
						System.out.println("Khong Update  duoc");
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKyQuyUpdate.jsp";
					}
					else
					{
						System.out.println("Update duoc");
						IErpDoiTuongKyQuy lspList = new ErpDoiTuongKyQuy();
						lspList.setCtyId(ctyId);
						lspList.Search( session.getAttribute("congtyId").toString() );
						lspList.CreateRs();
						session.setAttribute("lspList", lspList);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKyQuy.jsp";
					}
				}
				else
					if (Id == null) nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKyQuyNew.jsp";
					else
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKyQuyUpdate.jsp";
			
			
			lspBean.CreateRs();
			System.out.print("nextJSP " + nextJSP);
			session.setAttribute("lspBean", lspBean);
			response.sendRedirect(nextJSP);
		}
	}
}

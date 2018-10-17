package geso.traphaco.erp.servlets.loaisanpham;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaisanpham.IErpLoaiSanPham;
import geso.traphaco.erp.beans.loaisanpham.imp.ErpLoaiSanPham;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpLoaiSanPhamUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	
	public ErpLoaiSanPhamUpdateSvl( )
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do get! ErpLoaiSanPhamUpdateSvl");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		String ctyId = (String)session.getAttribute("congtyId");
		
		IErpLoaiSanPham lspBean;
				
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
			System.out.println("ID la" + id);
			
			lspBean = new ErpLoaiSanPham(id);
			lspBean.setCtyId(ctyId);
			
			System.out.println("ID : " + lspBean.getId());
			
			lspBean.setUserId(userId);
			if (request.getQueryString().indexOf("display") >= 0)
			{
				lspBean.Init();
				lspBean.CreateRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiSanPhamDisplay.jsp";
			}
			else
				if (request.getQueryString().indexOf("update") >= 0)
				{
					lspBean.Init();
					lspBean.CreateRs();
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiSanPhamUpdate.jsp";
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
		System.out.println("Do Post ErpLoaiSanPhamUpdateSvl!");
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
			IErpLoaiSanPham lspBean;
			
			String action = request.getParameter("action");
			String thuoclsp = util.antiSQLInspection(request.getParameter("thuoclsp"));
			String ma = util.antiSQLInspection(request.getParameter("ma"));
			String ten = util.antiSQLInspection(request.getParameter("ten"));
			String taikhoan = util.antiSQLInspection(request.getParameter("taikhoan"));
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			String Id = util.antiSQLInspection(request.getParameter("id"));
			
			String issanxuat = util.antiSQLInspection(request.getParameter("issanxuat"));
			if(issanxuat == null)
				issanxuat = "";
			
			lspBean = new ErpLoaiSanPham();
			
			if (action == null) action = "";
			if (trangthai == null) trangthai = "0";
			else
				trangthai = "1";
			
			lspBean.setId(Id);
			lspBean.setCtyId(ctyId);
			lspBean.setThuoclsp(thuoclsp);
			lspBean.setMa(ma);
			lspBean.setTen(ten);
			lspBean.setTaiKhoan(taikhoan);
			lspBean.setUserId(userId);
			lspBean.setTrangThai(trangthai);
			lspBean.setIsSanxuat(issanxuat);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
		
			if (action.equals("Create"))
			{
				System.out.println("Tao moi");
				if (lspBean.Create())
				{
					System.out.println("Tao duoc");
					IErpLoaiSanPham lspList = new ErpLoaiSanPham();
					lspList.setCtyId(ctyId);
					lspList.Search();
					lspList.CreateRs();
					session.setAttribute("lspList", lspList);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiSanPham.jsp";
				}
				else
				{
					System.out.println("Khong tao  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiSanPhamNew.jsp";
				}
			}
			else
				if (action.equals("Update"))
				{
					System.out.println("Update ErpLoaiSanPhamUpdateSvl ");
					if (!lspBean.Update())
					{
						System.out.println("Khong Update  duoc");
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiSanPhamUpdate.jsp";
					}
					else
					{
						System.out.println("Update duoc");
						IErpLoaiSanPham lspList = new ErpLoaiSanPham();
						lspList.setCtyId(ctyId);
						lspList.Search();
						lspList.CreateRs();
						session.setAttribute("lspList", lspList);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiSanPham.jsp";
					}
				}
				else
					if (Id == null) nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiSanPhamNew.jsp";
					else
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiSanPhamUpdate.jsp";
			
			
			lspBean.CreateRs();
			System.out.print("nextJSP " + nextJSP);
			session.setAttribute("lspBean", lspBean);
			response.sendRedirect(nextJSP);
		}
	}
}

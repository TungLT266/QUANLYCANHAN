package geso.traphaco.erp.servlets.congty;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.congty.IErpCongTy;
import geso.traphaco.erp.beans.congty.IErpCongTyList;
import geso.traphaco.erp.beans.congty.imp.ErpCongTy;
import geso.traphaco.erp.beans.congty.imp.ErpCongTyList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCongTyUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	public ErpCongTyUpdateSvl()
	{
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
			this.out = response.getWriter();
			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			IErpCongTy ctBean;
			String task = request.getParameter("task");
			if (task.equals("capnhat"))
			{
				ctBean = new ErpCongTy(id);
				ctBean.init();
				session.setAttribute("ctBean", ctBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongTyUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
			else if (task.equals("display"))
			{
				ctBean = new ErpCongTy(id);
				ctBean.init();
				session.setAttribute("ctBean", ctBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongTyDisplay.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			String ma = cutil.antiSQLInspection(request.getParameter("ma"));
			String ten = cutil.antiSQLInspection(request.getParameter("ten"));
			String tentienganh = cutil.antiSQLInspection(request.getParameter("tentienganh"));
			String diachi = cutil.antiSQLInspection(request.getParameter("diachi"));
			String diachitienganh = cutil.antiSQLInspection(request.getParameter("diachitienganh"));
			String masothue = cutil.antiSQLInspection(request.getParameter("masothue"));
			String nganhnghekinhdoanh = cutil.antiSQLInspection(request.getParameter("nganhnghekinhdoanh"));
			String dienthoai = cutil.antiSQLInspection(request.getParameter("dienthoai"));
			String fax = cutil.antiSQLInspection(request.getParameter("fax"));
			String giamdoc = cutil.antiSQLInspection(request.getParameter("giamdoc"));
			String ketoantruong = cutil.antiSQLInspection(request.getParameter("ketoantruong"));
			String trangthai = cutil.antiSQLInspection(request.getParameter("trangthai"));
			String nganhang = cutil.antiSQLInspection(request.getParameter("nganhang"));
			String sotaikhoan = cutil.antiSQLInspection(request.getParameter("sotaikhoan"));
			String tkctyId = cutil.antiSQLInspection(request.getParameter("tkctyId"));
			String isTongCty = request.getParameter("isTongCty");
			String vonDieuLe = request.getParameter("vondieule");
			String gpkd = request.getParameter("giayphepkinhdoanh");
			String ngaybatdautc = request.getParameter("ngaybatdautc");
			String ngayketthuctc = request.getParameter("ngayketthuctc");
			
			if(isTongCty == null)
				isTongCty = "0";
			
			System.out.println("Ngan hang" + nganhang == null ? "Null" : "Chua null");
			if (nganhang == null || nganhang.length() == 0)
			{
				nganhang = "NULL";
			}
			if (tkctyId == null || tkctyId.length() == 0)
			{
				tkctyId = "NULL";
			}
			if (sotaikhoan == null)
				sotaikhoan = "NULL";
			if (request.getParameter("trangthai") != null)
				trangthai = "1";
			else
				trangthai = "0";
			
			if(ngaybatdautc == null)
				ngaybatdautc = "";
			
			if(ngayketthuctc == null)
				ngayketthuctc = "";
			
			IErpCongTy ctBean = new ErpCongTy();
			ctBean.setUserId(userId);
			ctBean.setMA(ma);
			ctBean.setMASOTHUE(masothue);
			ctBean.setTEN(ten);
			ctBean.setTENTIENGANH(tentienganh);
			ctBean.setDIACHI(diachi);
			ctBean.setDIACHITIENGANH(diachitienganh);
			ctBean.setNGANHNGHEKINHDOANH(nganhnghekinhdoanh);
			ctBean.setDIENTHOAI(dienthoai);
			ctBean.setFAX(fax);
			ctBean.setGIAMDOC(giamdoc);
			ctBean.setKETOANTRUONG(ketoantruong);
			ctBean.setTRANGTHAI(trangthai);
			ctBean.setNganHang_FK(nganhang);
			ctBean.setSoTaiKhoan(sotaikhoan);
			ctBean.setTkCtyId(tkctyId);
			ctBean.setIsTongCty(isTongCty);
			ctBean.setVonDieuLe(vonDieuLe);
			ctBean.setGiayPhepKinhDoanh(gpkd);
			ctBean.setngaybatdautc(ngaybatdautc);
			ctBean.setngayketthuctc(ngayketthuctc);
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
			{
				if (ctBean.CreateCongty())
				{
					IErpCongTyList obj = new ErpCongTyList();
					obj.init("");
					session.setAttribute("obj", obj);
					ctBean.closeDB();
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongTy.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					session.setAttribute("ctBean", ctBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongTyNew.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				ctBean.setID(id);
				if (ctBean.UpdateCongty())
				{
					IErpCongTyList obj = new ErpCongTyList();
					obj.init("");
					ctBean.closeDB();
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongTy.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					session.setAttribute("ctBean", ctBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongTyUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
	}
}
package geso.traphaco.erp.servlets.congty;

import geso.traphaco.erp.beans.congty.IErpCongTy;
import geso.traphaco.erp.beans.congty.IErpCongTyList;
import geso.traphaco.erp.beans.congty.imp.ErpCongTy;
import geso.traphaco.erp.beans.congty.imp.ErpCongTyList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.traphaco.center.util.Utility;

public class ErpCongTySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ErpCongTySvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpCongTyList obj = new ErpCongTyList();
		String task = request.getParameter("task");
		String nextJSP = "";
		
		String msg = "";
		if (task != null)
		{
			if (task.equals("delete"))
			{
				String pk = request.getParameter("id");
				ErpCongTy ctBean = new ErpCongTy(pk);
				ctBean.Delete();
				msg = ctBean.getMessage();
			}
		}
		
		obj.setCongtyId((String)session.getAttribute("congtyId"));
		obj.init("");
		
		obj.setMessage(msg);
		session.setAttribute("obj", obj);
		nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongTy.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		IErpCongTyList obj = new ErpCongTyList();
		obj.setCongtyId((String)session.getAttribute("congtyId"));
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		String ten = util.antiSQLInspection(request.getParameter("ten"));
		String diachi = util.antiSQLInspection(request.getParameter("diachi"));
		String dienthoai = util.antiSQLInspection(request.getParameter("dienthoai"));
		String fax = util.antiSQLInspection(request.getParameter("fax"));
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (ma == null) ma = "";
		obj.setMA(ma);
		
		if (ten == null) ten = "";
		obj.setTEN(ten);
		
		if (diachi == null) diachi = "";
		obj.setDIACHI(diachi);
		
		if (dienthoai == null) dienthoai = "";
		obj.setDIENTHOAI(dienthoai);
		
		if (fax == null) fax = "";
		obj.setFAX(fax);
		
		System.out.println(trangthai);
		if (trangthai.equals("2")) trangthai = "";
		obj.setTRANGTHAI(trangthai);
		
		String query =
		"select  *,NV.TEN as TENNV,NV.PK_SEQ as MANV,NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS from ERP_CONGTY ct " +
		"inner join NHANVIEN nv on ct.nguoitao = nv.PK_SEQ inner join NHANVIEN nv2 on ct.NGUOISUA = nv2.PK_SEQ where 1 = 1 ";
		if (ma.length() > 0)
			query += " and ct.MA like '%"  + ma + "%'";
		if (ten.length() > 0)
			query += " and ct.TEN like '%" + ten + "%'";
		if (diachi.length() > 0)
			query += " and ct.diachi like N'%" + diachi + "%'";
		if (dienthoai.length() > 0)
			query += " and ct.dienthoai like '%" + dienthoai + "%'";
		if (fax.length() > 0)
			query += " and ct.fax like '%" + fax + "%'";
		if (trangthai.length() > 0)
			query += " and ct.trangthai = '" + trangthai + "'";
		String action = request.getParameter("action");
		System.out.println(query);
		if (action.equals("search"))
		{
			obj.init(query);
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongTy.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			IErpCongTy ctBean = new ErpCongTy();
			session.setAttribute("ctBean", ctBean);
			obj.closeDB();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCongTyNew.jsp";
			response.sendRedirect(nextJSP);
		}
	}
}

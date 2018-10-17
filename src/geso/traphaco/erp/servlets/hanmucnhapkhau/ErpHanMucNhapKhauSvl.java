package geso.traphaco.erp.servlets.hanmucnhapkhau;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hanmucnhapkhau.IErpHanMucNhapKhau;
import geso.traphaco.erp.beans.hanmucnhapkhau.IErpHanMucNhapKhauList;
import geso.traphaco.erp.beans.hanmucnhapkhau.imp.ErpHanMucNhapKhau;
import geso.traphaco.erp.beans.hanmucnhapkhau.imp.ErpHanMucNhapKhauList;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpHanMucNhapKhauSvl")
public class ErpHanMucNhapKhauSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	

	public ErpHanMucNhapKhauSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// logFilter.doFilter(request,response,this.)
		System.out.println("Do get ErpLoaiNhaCungCap");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String action = util.getAction(querystring);
		String Id = util.getId(querystring);
		
		IErpHanMucNhapKhauList lhmnk = new ErpHanMucNhapKhauList();
		
		
		if (action.equals("delete"))
		{
			lhmnk.setId(Id);
			lhmnk.Delete_Hanmuc();
		}
		lhmnk.Create_Sanpham();
		lhmnk.Init("");
		session.setAttribute("obj", lhmnk);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHanMucNhapKhau.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String nextJSP = "";
		String listsanpham = util.antiSQLInspection(request.getParameter("listsanpham"));
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		String action = request.getParameter("action");
		
		IErpHanMucNhapKhauList listhmnk = new ErpHanMucNhapKhauList();
		listhmnk.setSanPham_Fk(listsanpham);
		listhmnk.setTuNgay(tungay);
		listhmnk.setDenNgay(denngay);
		System.out.println("Action : " + action);
		if (action == null)
		{
			action = "";
		}
		if (action.equals("New"))
		{
			System.out.println("Vao trang tao moi");
			IErpHanMucNhapKhau listhmnkBean = new ErpHanMucNhapKhau();
			listhmnkBean.Create_Sanpham();
			session.setAttribute("obj", listhmnkBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ERPHanMucNhapKhauNew.jsp";
			response.sendRedirect(nextJSP);
		}
		if (action.equals("Search"))
		{
			System.out.println("dang trong search");
			String search = getSearchQuery(request,listhmnk);
			listhmnk.Create_Sanpham();
			listhmnk.Init(search);
			session.setAttribute("obj", listhmnk);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpHanMucNhapKhau.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	private String getSearchQuery(HttpServletRequest request, IErpHanMucNhapKhauList litshmnk) {
		String query;
		query=	"select  hmnk.PK_SEQ,hmnk.SANPHAM_FK,sp.MA,sp.TEN,hmnk.TUNGAY,hmnk.DENNGAY,hmnk.SOLUONG,hmnk.TRANGTHAI "
				+ "from ERP_HANMUCNHAPKHAU hmnk left join ERP_SANPHAM sp on sp.PK_SEQ=hmnk.SANPHAM_FK where 1=1";
	 		
		if (litshmnk.getSanPham_Fk().length() > 0) {
			query += " AND  hmnk.SANPHAM_FK LIKE '%" + litshmnk.getSanPham_Fk()+ "%'";
		}
		if (litshmnk.getTuNgay().length() > 0) {
			query += " AND hmnk.TUNGAY LIKE '%" + litshmnk.getTuNgay() + "%'";
		}
		if (litshmnk.getDenNgay().length() > 0) {
			query += " AND hmnk.DENNGAY LIKE '%" + litshmnk.getDenNgay()+ "%'";
		}
		return query;

	}
}

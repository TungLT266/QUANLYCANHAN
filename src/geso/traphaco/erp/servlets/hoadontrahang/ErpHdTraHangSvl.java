package geso.traphaco.erp.servlets.hoadontrahang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHang;
import geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHangList;
import geso.traphaco.erp.beans.hoadontrahang.imp.ErpHdTraHang;
import geso.traphaco.erp.beans.hoadontrahang.imp.ErpHdTraHangList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHdTraHangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpHdTraHangSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpHdTraHangList hdthList;
		String userId;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		out.println(action);

		String hdId = util.getId(querystring);

		userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		hdthList = new ErpHdTraHangList();
		hdthList.setCongtyId((String)session.getAttribute("congtyId"));
		out.println(userId);
		
		System.out.println("__ACTION: " + action);
		if (action.equals("chot"))
		{
			IErpHdTraHang hdth = new ErpHdTraHang(hdId);
			hdth.setCongtyId((String)session.getAttribute("congtyId"));
			
			hdth.setUserId(userId);
			hdthList.setMessage(hdth.ChotHd());

		} 
		else if (action.equals("delete"))
		{
			IErpHdTraHang hdth = new ErpHdTraHang(hdId);
			hdth.setCongtyId((String)session.getAttribute("congtyId"));
			
			hdth.setUserId(userId);
			hdthList.setMessage(hdth.XoaHd());
		}

		hdthList.setUserId(userId);
		hdthList.init();

		session.setAttribute("hdthList", hdthList);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHdTraHang.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IErpHdTraHangList hdthList;
		String userId;
		Utility util = new Utility();

		hdthList = new ErpHdTraHangList();
		String TuNgay = util.antiSQLInspection(request.getParameter("TuNgay"));
		String DenNgay = util.antiSQLInspection(request.getParameter("DenNgay"));
		String SoHoaDon = util.antiSQLInspection(request.getParameter("SoHoaDon"));
		String Trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
		String ngayhoadon = util.antiSQLInspection(request.getParameter("ngayhoadon"));
		String khachhangid = util.antiSQLInspection(request.getParameter("nppId"));
		hdthList.setSoChungTu(sochungtu);
		hdthList.setNgayHoaDon(ngayhoadon);
		hdthList.setKhachHang(khachhangid);
		hdthList.setDenNgay(DenNgay);
		hdthList.setTuNgay(TuNgay);
		hdthList.setSoHoaDon(SoHoaDon);
		hdthList.setTrangthai(Trangthai);
		
		HttpSession session = request.getSession();
		userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = request.getParameter("action");
		if (action == null)
			action = "";

		if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			hdthList = new ErpHdTraHangList();
			hdthList.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			hdthList.setUserId(userId);
			hdthList.init();
			hdthList.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("hdthList", hdthList);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoaDon.jsp";
			response.sendRedirect(nextJSP);
		} 
		else if (action.equals("new"))
		{
			IErpHdTraHang hdthBean = new ErpHdTraHang();
			hdthBean.setCongtyId( session.getAttribute("congtyId").toString() );
			System.out.println("usehha:"+userId);
			hdthBean.setUserId(userId);
			hdthBean.CreateRs();
			session.setAttribute("hdthBean", hdthBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHdTraHangNew.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			hdthList.setUserId(userId);
			hdthList.setCongtyId((String)session.getAttribute("congtyId"));
			hdthList.init();
			session.setAttribute("hdthList", hdthList);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHdTraHang.jsp";
			response.sendRedirect(nextJSP);
		}

	}
}

package geso.traphaco.erp.servlets.hoadontrahang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHang;
import geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHangList;
import geso.traphaco.erp.beans.hoadontrahang.imp.ErpHdTraHang;
import geso.traphaco.erp.beans.hoadontrahang.imp.ErpHdTraHangList;
import geso.traphaco.erp.beans.hoadontravencc.imp.ErpHoaDon;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHdTraHangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpHdTraHangUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
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

		String userId = util.getUserId(querystring);

		if (action.equals("update"))
		{
			IErpHdTraHang hdthBean = new ErpHdTraHang(hdId);
			hdthBean.setCongtyId((String)session.getAttribute("congtyId"));
			hdthBean.setUserId(userId);			
			hdthBean.InitDisplay();
			session.setAttribute("nppId",hdthBean.getNppdangnhap());
			session.setAttribute("hdthBean", hdthBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHdTraHangUpdate.jsp";
			response.sendRedirect(nextJSP);
		} 
		else if (action.equals("display"))
		{
			IErpHdTraHang hdthBean = new ErpHdTraHang(hdId);
			hdthBean.setCongtyId((String)session.getAttribute("congtyId"));
			hdthBean.setUserId(userId);
			hdthBean.initdisplay();
			session.setAttribute("nppId",hdthBean.getNppdangnhap());
			session.setAttribute("hdthBean", hdthBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHdTraHangDisplay.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

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
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			session.setMaxInactiveInterval(30000);

			IErpHdTraHang hdthBean = null;
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			Utility util = new Utility();
			session.setAttribute("util", util);

			String id = util.antiSQLInspection(request.getParameter("id"));
			
			
			if (id.equals(""))
				hdthBean = new ErpHdTraHang();
			else
				hdthBean = new ErpHdTraHang(id);
			
			//hdthBean.setId(id);
			hdthBean.setUserId(userId);
			hdthBean.setCongtyId( session.getAttribute("congtyId").toString() );
			
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);

			String action = request.getParameter("action");

			String nextJSP = "";

			String NgayXuatHD = util.antiSQLInspection(request.getParameter("NgayXuatHD"));
			hdthBean.setNgayXuatHD(NgayXuatHD);

			String SoHoaDon = util.antiSQLInspection(request.getParameter("SoHoaDon"));
			hdthBean.SetSoHoaDon(SoHoaDon);

			String KyHieu = util.antiSQLInspection(request.getParameter("KyHieu"));
			hdthBean.SetKyHieu(KyHieu);

			String HinhThucTT = util.antiSQLInspection(request.getParameter("HinhThucTT"));
			hdthBean.setHinhThucTT(HinhThucTT);

			String NguoiMuaHang = util.antiSQLInspection(request.getParameter("NguoiMuaHang"));
			hdthBean.SetNguoiMuaHang(NguoiMuaHang);
			
			String Ghichu = util.antiSQLInspection(request.getParameter("Ghichu"));
			if(Ghichu==null) Ghichu = "";
			hdthBean.SetGhiChu(Ghichu);

			String tienvat = util.antiSQLInspection(request.getParameter("TienVAT"));
			hdthBean.setTienVAT(Double.parseDouble( tienvat.replaceAll(",", "")));	
			
			String tienavat = util.antiSQLInspection(request.getParameter("tienavat"));	
			hdthBean.setTienAVAT(Double.parseDouble(tienavat.replaceAll(",", "")));
			
			String tienbvat = util.antiSQLInspection(request.getParameter("BVAT"));	
			hdthBean.setTienBVAT(Double.parseDouble(tienbvat.replaceAll(",", "")));
			
			String tienck = util.antiSQLInspection(request.getParameter("TienCK"));
			hdthBean.setTienCK(Double.parseDouble(tienck.replaceAll(",", "")));
			
			String vat = util.antiSQLInspection(request.getParameter("VAT"));
			hdthBean.setVAT(Double.parseDouble(vat.replaceAll(",", "")));
			
			String khId = util.antiSQLInspection(request.getParameter("KhId"));
			if (khId != null)
				hdthBean.SetKhId(khId);

			String khonghd = util.antiSQLInspection(request.getParameter("khonghd"));
			if (khonghd != null)
				hdthBean.SetKhongHD(khonghd);
			
			String KhTen = util.antiSQLInspection(request.getParameter("KhTen"));
			hdthBean.setKhTen(KhTen);
			
			String hoadonId = util.antiSQLInspection(request.getParameter("hoadonId"));
			hdthBean.SetHoadonCanTruId(hoadonId);

			String PoMt = util.antiSQLInspection(request.getParameter("POMT"));
			hdthBean.SetPOMT(PoMt);

			session.setAttribute("nppId", request.getParameter("nppId"));
			
			String[] dthId = request.getParameterValues("dthId");
			
			if (dthId != null)
			{
				hdthBean.setDonTraHang(dthId);

			}
			  
			if (id == "")
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHdTraHangNew.jsp";
			} else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHdTraHangUpdate.jsp";
			}
			
			
			
			if (action.equals("save"))
			{
				hdthBean.CreateRs();
				if (id == "")
				{
					if (!(hdthBean.Save()))
					{
						hdthBean.CreateRs(); 
						session.setAttribute("hdthBean", hdthBean);
						response.sendRedirect(nextJSP);
					} else
					{
						IErpHdTraHangList hdthList = new ErpHdTraHangList();
						hdthList.setUserId(userId);
						hdthList.setCongtyId("100005");
						hdthList.init();
						session.setAttribute("hdthList", hdthList);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHdTraHang.jsp";
						response.sendRedirect(nextJSP);
					}
				} else
				{
					hdthBean.CreateRs();
					if (!(hdthBean.Edit()))
					{
						hdthBean.CreateRs(); 
						session.setAttribute("hdthBean", hdthBean);
						response.sendRedirect(nextJSP);
					} else
					{
						IErpHdTraHangList hdthList = new ErpHdTraHangList();
						hdthList.setUserId(userId);
						hdthList.setCongtyId("100005");
						hdthList.init();
						session.setAttribute("hdthList", hdthList);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpHdTraHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else if (action.equals("reload"))
			{
				hdthBean.CreateRs(); 
				session.setAttribute("hdthBean", hdthBean);
				response.sendRedirect(nextJSP);
				
			}
			else {
				hdthBean.CreateRs();
				session.setAttribute("hdthBean", hdthBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
}

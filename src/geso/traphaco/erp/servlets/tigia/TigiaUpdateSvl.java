package geso.traphaco.erp.servlets.tigia;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tigia.ITiGia_TienTe;
import geso.traphaco.erp.beans.tigia.ITigia;
import geso.traphaco.erp.beans.tigia.ITigiaList;
import geso.traphaco.erp.beans.tigia.imp.TiGia_TienTe;
import geso.traphaco.erp.beans.tigia.imp.Tigia;
import geso.traphaco.erp.beans.tigia.imp.TigiaList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TigiaUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public TigiaUpdateSvl()
	{
		super();

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
		String ctyId = (String)session.getAttribute("congtyId");
		
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			session.setMaxInactiveInterval(30000);
			this.out = response.getWriter();
			
			String querystring = request.getQueryString();
			userId = cutil.getUserId(querystring);
			if (userId.length() == 0)
				userId = cutil.antiSQLInspection(request.getParameter("userId"));
			
			String id = cutil.getId(querystring);
			ITigia tgBean;
			
			String task = request.getParameter("task");
			if (task.equals("capnhat"))
			{
				tgBean = new Tigia(id);
				tgBean.setCongTy(ctyId);
				
				tgBean.init();
				session.setAttribute("tgBean", tgBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/TiGiaUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
			else if (task.equals("display"))
			{
				tgBean = new Tigia(id);
				tgBean.setCongTy(ctyId);
				
				tgBean.init();
				session.setAttribute("tgBean", tgBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/TiGiaDisplay.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		String ctyId = (String)session.getAttribute("congtyId");
		
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			String action = request.getParameter("action");
			String nextJSP = "";
			ITigia tgBean = new Tigia();
			tgBean.setCongTy(ctyId);
			
			if (action.equals("save"))
			{
				String TenTienTe = cutil.antiSQLInspection(request.getParameter("TenTienTe"));
				String CongTy = cutil.antiSQLInspection(request.getParameter("CongTy"));
				String MaTienTe = cutil.antiSQLInspection(request.getParameter("MaTienTe"));
				String TuNgay = cutil.antiSQLInspection(request.getParameter("TuNgay"));
				String DenNgay = cutil.antiSQLInspection(request.getParameter("DenNgay"));
				String TiGiaQuyDoi = cutil.antiSQLInspection(request.getParameter("TiGiaQuyDoi"));
				String TrangThai = cutil.antiSQLInspection(request.getParameter("TrangThai"));
				String SoLuongGoc = cutil.antiSQLInspection(request.getParameter("SoLuongGoc"));

				if (request.getParameter("TrangThai") != null)
					TrangThai = "1";
				else
					TrangThai = "0";
				TiGiaQuyDoi = TiGiaQuyDoi.replaceAll(",", "");

				tgBean.setUserId(userId);
				if (MaTienTe != null)
					tgBean.setMaTienTe(MaTienTe);
				if (SoLuongGoc != null)
					tgBean.setSoLuongGoc(SoLuongGoc);
				if (TenTienTe != null)
					tgBean.setTenTienTe(TenTienTe);
				if (TuNgay != null)
					tgBean.setTuNgay(TuNgay);
				if (DenNgay != null)
					tgBean.setDenNgay(DenNgay);
				if (TiGiaQuyDoi != null)
					tgBean.setTiGiaQuyDoi(TiGiaQuyDoi);
				if (CongTy != null)
					tgBean.setCongTy(CongTy);
				tgBean.setTrangThai(TrangThai);
				Utility util = new Utility();
				String id = util.antiSQLInspection(request.getParameter("id"));
				if (id != null)
				{
					tgBean.setId(id);
					if (tgBean.UpdateTigia())
					{
						ITigiaList tgList = new TigiaList();
						tgList.setCongTy(ctyId);
						tgList.init();
						session.setAttribute("tgList", tgList);
						nextJSP = "/TraphacoHYERP/pages/Erp/TiGia.jsp";
						response.sendRedirect(nextJSP);
					} else
					{
						session.setAttribute("tgBean", tgBean);
						nextJSP = "/TraphacoHYERP/pages/Erp/TiGiaUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else
			{
				String[] TenTienTe = request.getParameterValues("TenTienTe");
				String[] MaTienTe = request.getParameterValues("MaTienTe");
				String[] TuNgay = request.getParameterValues("TuNgay");
				String[] DenNgay = request.getParameterValues("DenNgay");
				String[] TiGiaQuyDoi = request.getParameterValues("TiGiaQuyDoi");
				String[] SoLuongGoc = request.getParameterValues("SoLuongGoc");
				String[] TiGiaId = request.getParameterValues("TiGiaId");
				String[] TienTeId = request.getParameterValues("TienTeId");
				
				int sodong = TiGiaId == null ? 0 : TiGiaId.length;
				tgBean.setUserId(userId);
				System.out.println(" action___" + action);
				if (!action.equals("new"))
					tgBean.CreateTiGiaTienTeList();
				else
				{
					List<ITiGia_TienTe> TiGia_TienTeList = new ArrayList<ITiGia_TienTe>();
					for (int i = 0; i < sodong; i++)
					{
						ITiGia_TienTe e = new TiGia_TienTe();
						e.setTenTienTe(TenTienTe[i]);
						e.setMaTienTe(MaTienTe[i]);
						e.setTiGiaId(TiGiaId[i]);
						e.setTienTeId(TienTeId[i]);
						e.setTuNgay(TuNgay[i]);
						e.setDenNgay(DenNgay[i]);
						e.setTiGiaQuyDoi(TiGiaQuyDoi[i].replace(",", ""));
						e.setSoLuongGoc(SoLuongGoc[i].replace(",", ""));
						TiGia_TienTeList.add(e);
					}
					tgBean.setTiGia_TienTeList(TiGia_TienTeList);
				}

				if (action.equals("new"))
				{
					if (tgBean.CreateTiGia())
					{
						ITigiaList tgList = new TigiaList();
						tgList.setCongTy(ctyId);
						tgList.init();
						session.setAttribute("tgList", tgList);
						nextJSP = "/TraphacoHYERP/pages/Erp/TiGia.jsp";
					} else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/TiGiaNew.jsp";
					}
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/TiGiaNew.jsp";
				}
				tgBean.createRs();
				session.setAttribute("tgBean", tgBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
}

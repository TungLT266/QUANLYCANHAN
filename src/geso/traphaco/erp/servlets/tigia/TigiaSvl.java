package geso.traphaco.erp.servlets.tigia;

import geso.traphaco.erp.beans.tigia.ITigia;
import geso.traphaco.erp.beans.tigia.ITigiaList;
import geso.traphaco.erp.beans.tigia.imp.Tigia;
import geso.traphaco.erp.beans.tigia.imp.TigiaList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.util.*;
import geso.traphaco.center.db.sql.dbutils;


public class TigiaSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public TigiaSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		ITigiaList tgList = new TigiaList();
		tgList.setCongTy(ctyId);
		
		String chixem = request.getParameter("chixem");
		tgList.setChixem(chixem);
		
		String task = request.getParameter("task");
		if (task != null)
		{
			if (task.equals("xoa"))
			{
				String pk = request.getParameter("id");
				dbutils db = new dbutils();
				String query = "delete ERP_TIGIA where pk_seq= '" + pk + "'";
				if (!db.update(query))
				{
					tgList.setMessage("Không thể xóa, lỗi: " + query);
				}
				db.shutDown();
			}
		}
		tgList.init();
		session.setAttribute("tgList", tgList);
		String nextJSP = "/TraphacoHYERP/pages/Erp/TiGia.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		String ctyId = (String)session.getAttribute("congtyId");		
		
		ITigiaList tgList = new TigiaList();
		tgList.setCongTy(ctyId);
		
		String chixem = request.getParameter("chixem");
		tgList.setChixem(chixem);
		
		String tientefk = util.antiSQLInspection(request.getParameter("loaitien"));
		String TuNgay = util.antiSQLInspection(request.getParameter("TuNgay"));
		String DenNgay = util.antiSQLInspection(request.getParameter("DenNgay"));
		String TyGiaQuyDoi = util.antiSQLInspection(request.getParameter("TyGiaQuyDoi"));
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));

		if (tientefk == null)
			tientefk = "";
		tgList.setTIENTE_FK(tientefk);

		if (TuNgay != null)
			tgList.setTuNgay(TuNgay);

		if (DenNgay != null)
			tgList.setDenNgay(DenNgay);

		if (TyGiaQuyDoi == null)
			TyGiaQuyDoi = "";
		
		tgList.setTIGIAQUYDOI(TyGiaQuyDoi);
		if (trangthai.equals("2"))
			trangthai = "";
		tgList.setTRANGTHAI(trangthai);

		String action = request.getParameter("action");
		if (action.equals("search"))
		{
			tgList.init();
			session.setAttribute("tgList", tgList);
			String nextJSP = "/TraphacoHYERP/pages/Erp/TiGia.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			ITigia tgBean = new Tigia();
			tgBean.setCongTy(ctyId);
			tgBean.CreateTiGiaTienTeList();
//			tgBean.createRs();
			session.setAttribute("tgBean", tgBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/TiGiaNew.jsp";
			response.sendRedirect(nextJSP);
		}
	}

}

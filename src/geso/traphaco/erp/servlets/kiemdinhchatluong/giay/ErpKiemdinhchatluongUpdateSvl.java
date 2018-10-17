package geso.traphaco.erp.servlets.kiemdinhchatluong.giay;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.*;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp.*;
import geso.traphaco.center.beans.thongtinsanpham.ITieuchikiemdinh;
import geso.traphaco.center.beans.thongtinsanpham.imp.Tieuchikiemdinh;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKiemdinhchatluongUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public ErpKiemdinhchatluongUpdateSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpKiemdinhchatluong dcsxBean;

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);

		dcsxBean = new ErpKiemdinhchatluong(id);
		dcsxBean.setYcId(id);
		dcsxBean.setUserId(userId);

		dcsxBean.init();
		session.setAttribute("dcsxBean", dcsxBean);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiayUpdate.jsp";
		if (querystring.indexOf("display") >= 0)
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiayDisplay.jsp";
		}

		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpKiemdinhchatluong dcsxBean;

		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
		{
			dcsxBean = new ErpKiemdinhchatluong();
		} else
		{
			dcsxBean = new ErpKiemdinhchatluong(id);
		}

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		dcsxBean.setUserId(userId);

		String denghixuly = util.antiSQLInspection(request.getParameter("denghixuly"));
		dcsxBean.setDeNghiXuLy(denghixuly);

		String[] tieuchi = request.getParameterValues("tieuchi");
		String[] toantu = request.getParameterValues("toantu");
		String[] giatrichuan = request.getParameterValues("giatrichuan");
		String[] diemdat = request.getParameterValues("diemdat");
		String[] trangthai = request.getParameterValues("trangthai");
		String[] dat = request.getParameterValues("dat");
 
		
		List<ITieuchikiemdinh> tckdList = new ArrayList<ITieuchikiemdinh>();
		if (tieuchi != null)
		{
			for (int i = 0; i < tieuchi.length; i++)
			{
				if (tieuchi[i] != "")
				{
					ITieuchikiemdinh tckd = new Tieuchikiemdinh();
					tckd.setTieuchi(tieuchi[i]);
					tckd.setToantu(toantu[i]);
					tckd.setGiatrichuan(giatrichuan[i]);
					tckd.setDiemdat(diemdat[i]);
					tckd.setTrangthai(trangthai[i]);
					tckd.setDat(dat[i]);
					System.out.println("DiemDat_______"+diemdat[i]);
					tckdList.add(tckd);
				}
			}
			dcsxBean.setTieuchikiemdinhList(tckdList);
		}

		String action = request.getParameter("action");
		if (action.equals("save"))
		{
			if (!(dcsxBean.updateKiemdinh(request)))
			{
				dcsxBean.init();
				session.setAttribute("dcsxBean", dcsxBean);
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiayUpdate.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				IErpKiemdinhchatluongList obj = new ErpKiemdinhchatluongList();
				String congtyId = ((String) session.getAttribute("congtyId"));
				obj.setCongtyId(congtyId);
				obj.init("");

				session.setAttribute("obj", obj);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiay.jsp";
				response.sendRedirect(nextJSP);
			}
		} else
		{
			dcsxBean.init();
			session.setAttribute("userId", userId);
			session.setAttribute("dcsxBean", dcsxBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiayUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
	}

}

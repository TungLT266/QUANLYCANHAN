package geso.traphaco.erp.servlets.loaitaisan;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaitaisan.IErp_loaiCCDC;
import geso.traphaco.erp.beans.loaitaisan.IErp_loaiCCDCList;
import geso.traphaco.erp.beans.loaitaisan.imp.Erp_loaiCCDC;
import geso.traphaco.erp.beans.loaitaisan.imp.Erp_loaiCCDCList;

public class Erp_LoaiCCDCUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_LoaiCCDCUpdateSvl()
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

		String Id = util.getId(querystring);

		String userId = util.getUserId(querystring);
		System.out.println("user update : " + userId);
		System.out.println("Action : " + action);

		if (action.equals("update"))
		{
			IErp_loaiCCDC lccdcBean = new Erp_loaiCCDC(Id);
			lccdcBean.setCtyId((String)session.getAttribute("congtyId"));
			lccdcBean.setId(Id);
			lccdcBean.init();
			lccdcBean.createRs();
			lccdcBean.setUserid(userId);
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LoaiCCDCUpdate.jsp";
			session.setAttribute("lccdcBean", lccdcBean);
			response.sendRedirect(nextJSP);
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
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			IErp_loaiCCDC lccdcBean = new Erp_loaiCCDC();
			lccdcBean.setCtyId((String)session.getAttribute("congtyId"));
			String nextJSP = "";
			String id = request.getParameter("id");
			String ma = request.getParameter("Ma");
			String ten = request.getParameter("Ten");
			String tk = request.getParameter("tk");
			String trangthai = request.getParameter("hoatdong");
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			lccdcBean.setTrangthai(trangthai);

			String ngaytao = getDateTime();
			String nguoitao = request.getParameter("userId");
			String ngaysua = getDateTime();
			String nguoisua = request.getParameter("userId");

			if (id != null)
				lccdcBean.setId(id);
			if (ma != null)
				lccdcBean.setMa(ma);
			if (ten != null)
				lccdcBean.setTen(ten);
			if (tk != null)
				lccdcBean.setTkId(tk);
			System.out.println("tai khoan kt: " + tk);

			lccdcBean.setNguoitao(nguoitao);
			lccdcBean.setNgaytao(ngaytao);

			lccdcBean.setNguoisua(nguoisua);
			lccdcBean.setNgaysua(ngaysua);

			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);

			String action = request.getParameter("action");

			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!lccdcBean.ThemMoiMa())
					{
						nextJSP = "pages/Erp/Erp_LoaiCCDCNew.jsp";
					} else
					{
						IErp_loaiCCDCList lccdcList = new Erp_loaiCCDCList();
						String ctId = (String)session.getAttribute("congtyId");
						lccdcBean.setCtyId(ctId);
						lccdcList.setCtyId(ctId);
						lccdcList.setUserid(userId);
						lccdcList.init();
						session.setAttribute("lccdcList", lccdcList);
						nextJSP = "pages/Erp/Erp_LoaiCCDC.jsp";
					}
				} else if (id != null)
				{
					if (!lccdcBean.UpdateMa())
					{
						nextJSP = "pages/Erp/Erp_LoaiCCDCUpdate.jsp";
					}

					else
					{
						IErp_loaiCCDCList lccdcList = new Erp_loaiCCDCList();
						lccdcList.setUserid(userId);
						lccdcList.setCtyId((String)session.getAttribute("congtyId"));
						System.out.println("cong ty id: " + (String)session.getAttribute("congtyId"));
						lccdcList.init();
						session.setAttribute("lccdcList", lccdcList);
						nextJSP = "pages/Erp/Erp_LoaiCCDC.jsp";
					}
				}
				lccdcBean.setCtyId((String)session.getAttribute("congtyId"));
				lccdcBean.createRs();
				session.setAttribute("lccdcBean", lccdcBean);
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}

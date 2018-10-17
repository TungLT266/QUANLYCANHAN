package geso.traphaco.erp.servlets.erp_nhomtaisan;

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
import geso.traphaco.erp.beans.erp_nhomtaisan.IErp_nhomCCDC;
import geso.traphaco.erp.beans.erp_nhomtaisan.IErp_nhomCCDCList;
import geso.traphaco.erp.beans.erp_nhomtaisan.imp.Erp_nhomCCDC;
import geso.traphaco.erp.beans.erp_nhomtaisan.imp.Erp_nhomCCDCList;

public class Erp_NhomCCDCUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_NhomCCDCUpdateSvl()
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
		String ctyId = (String)session.getAttribute("congtyId");
		
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
			IErp_nhomCCDC tsBean = new Erp_nhomCCDC(Id);
			tsBean.setCtyId(ctyId);
			tsBean.init();
			tsBean.setUserid(userId);
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_NhomCCDCUpdate.jsp";
			session.setAttribute("ntsBean", tsBean);
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
		String ctyId = (String)session.getAttribute("congtyId");
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			IErp_nhomCCDC ntsBean = new Erp_nhomCCDC();
			String nextJSP = "";
			String id = cutil.antiSQLInspection(request.getParameter("id"));
			String ma = cutil.antiSQLInspection(request.getParameter("Ma"));
			String ten = cutil.antiSQLInspection(request.getParameter("Ten"));
			String ltsId = cutil.antiSQLInspection(request.getParameter("ltsId"));
			String[] cdtsIds = request.getParameterValues("cdtsIds");
			String[] ttcpIds = request.getParameterValues("ttcpIds");
			String trangthai = cutil.antiSQLInspection(request.getParameter("trangthai"));
			
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			ntsBean.setTrangthai(trangthai);

			String ngaytao = getDateTime();
			String nguoitao = request.getParameter("userId");
			String ngaysua = getDateTime();
			String nguoisua = request.getParameter("userId");

			ntsBean.setCtyId(ctyId);
			if (id != null)
				ntsBean.setId(id);
			if (ma != null)
				ntsBean.setMa(ma);
			if (ten != null)
				ntsBean.setTen(ten);

			if(ltsId == null) ltsId = "";
			ntsBean.setLtsId(ltsId);
			
			ntsBean.setCdtsIds(cdtsIds);
			
			ntsBean.setTtcpIds(ttcpIds);

			ntsBean.setNguoitao(nguoitao);
			ntsBean.setNgaytao(ngaytao);

			ntsBean.setNguoisua(nguoisua);
			ntsBean.setNgaysua(ngaysua);

			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);

			String action = request.getParameter("action");
			boolean err = false;
			
			if(ma.length() == 0) {
				ntsBean.setMsg("Vui lòng nhập Mã Nhóm Công Cụ Dụng Cụ");
				err = true;
			}
			
			if(ten.length() == 0) {
				ntsBean.setMsg("Vui lòng nhập Diễn Giải Nhóm Công Cụ Dụng Cụ");
				err = true;
			}
			
			if(ltsId.length() == 0){
				ntsBean.setMsg("Vui lòng chọn Loại Công Cụ Dụng Cụ");
				err = true;
				
			}
			
			if (action.equals("save"))
			{
				if(!err){
					if (id == null)
					{
						if (!ntsBean.ThemMoiMa())
						{
							ntsBean.createRs();
							nextJSP = "pages/Erp/Erp_NhomCCDCNew.jsp";
						} else
						{
							IErp_nhomCCDCList ntsList = new Erp_nhomCCDCList();
							ntsList.setUserid(userId);
							ntsList.setCongTyId(ctyId);
							
							ntsList.init();
							ntsBean.DBClose();
							session.setAttribute("ntsList", ntsList);
							nextJSP = "pages/Erp/Erp_NhomCCDC.jsp";
						}
					} else if (id != null)
					{
						if (!ntsBean.UpdateMa())
						{
							ntsBean.createRs();
							nextJSP = "pages/Erp/Erp_NhomCCDCUpdate.jsp";
						}
						else
						{
							IErp_nhomCCDCList ntsList = new Erp_nhomCCDCList();
							ntsList.setCongTyId(ctyId);
							ntsList.setUserid(userId);
							ntsList.init();
							ntsBean.DBClose();
							session.setAttribute("ntsList", ntsList);
							nextJSP = "pages/Erp/Erp_NhomCCDC.jsp";
						}
					}
					session.setAttribute("ntsBean", ntsBean);
					response.sendRedirect(nextJSP);
				}
			}else{
				if(id == null){
					
					ntsBean.createRs();
					nextJSP = "pages/Erp/Erp_NhomCCDCNew.jsp";
				}else{
					
					ntsBean.createRs();
					nextJSP = "pages/Erp/Erp_NhomCCDCUpdate.jsp";
				}
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

package geso.traphaco.erp.servlets.taisancodinh;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.taisancodinh.IErp_CongCuDungCu;
import geso.traphaco.erp.beans.taisancodinh.IPhanBo;
import geso.traphaco.erp.beans.taisancodinh.imp.Erp_CongCuDungCu;
import geso.traphaco.erp.beans.taisancodinh.imp.PhanBo;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_CongCuDungCuUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public Erp_CongCuDungCuUpdateSvl( )
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

		IErp_CongCuDungCu ccdcBean = new Erp_CongCuDungCu(Id);
			
		ccdcBean.setCtyId((String) session.getAttribute("congtyId"));
		System.out.println("Công ty nhé các bác : "+(String) session.getAttribute("congtyId"));
		ccdcBean.setUserid(userId);
		ccdcBean.setId(Id);
		ccdcBean.init("");
		ccdcBean.createRs();
		
		String nextJSP = "";
		if (action.equals("update"))
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCuUpdate.jsp";
		}else{
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCuDisplay.jsp";
		}
		session.setAttribute("obj", ccdcBean);
		response.sendRedirect(nextJSP);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		
		String ctyId = (String)session.getAttribute("congtyId");
		IErp_CongCuDungCu ccdc = new Erp_CongCuDungCu();
		ccdc.setCtyId(ctyId);
		
		String nextJSP = "";
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		String userTen = util.antiSQLInspection(request.getParameter("userTen"));
		
		String id = util.antiSQLInspection(request.getParameter("id"));
		if(id == null)
			id = "";
		boolean err = false;

		
		String stkh 	= util.antiSQLInspection(request.getParameter("stkh"));
		if(stkh.trim().length() == 0) stkh = "0";

		String tbdkh 	= util.antiSQLInspection(request.getParameter("tbdkh"));
		if(tbdkh.length() == 0) tbdkh = "";
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if(diengiai == null) diengiai = "";
		
		
		
		
		String ma 		= util.antiSQLInspection(request.getParameter("ma"));
		
		if(ma == null) ma = "";
		

		String nccdcId 	= util.antiSQLInspection(request.getParameter("nccdcId"));
		if(nccdcId == null) nccdcId = "";	
		
		String LccdcId 	= util.antiSQLInspection(request.getParameter("LccdcId"));
		if(LccdcId == null)
		LccdcId = "";
		
		System.out.println("loại cong cụ dụng cụ"+LccdcId);
		
		
		String ttcpId = util.antiSQLInspection(request.getParameter("ttcpId"));
		if(ttcpId == null) ttcpId = null;	
		ccdc.setTtcp(ttcpId);
		
		
		String dvthId = util.antiSQLInspection(request.getParameter("dvthId"));
		if(dvthId == null) dvthId = null;	
		ccdc.setDvthId(dvthId);
		System.out.println("don vi thuc hien: "+dvthId);
		

	/*	if(ttcpId.length() == 0){
				
				ccdc.setMsg("Vui lòng chọn Trung tâm chi phí");
				err = true;
		}
*/
		if(diengiai.trim().length() == 0){
			ccdc.setMsg("Vui lòng nhập Diễn giải cho công cụ dụng cụ");
			err = true;
		}

		if(ma.length() == 0){
			ccdc.setMsg("Vui lòng nhập Mã cho công cụ dụng cụ");
			err = true;
		}
		
		if(dvthId.length() == 0){
			ccdc.setMsg("Vui lòng chọn đơn vị thực hiện");
			err = true;
		}

		String nId 	= util.antiSQLInspection(request.getParameter("nId"));
		if(nId == null) nId = "";	

/*		if(nccdcId.length() == 0){
			if(nId.length() > 0){
				
				nccdcId = nId;
				
			}else{
				
				ccdc.setMsg("Vui lòng chọn Nhóm công cụ dụng cụ");
				err = true;
				
			}
		}*/
		String[] khoanMucId = request.getParameterValues("khoanMucId");
		String[] phanTramKhauHao = request.getParameterValues("phanTramKhauHao");
		
		
		ArrayList<IPhanBo> phanBoList= new 	ArrayList<IPhanBo> ();
		
		if(khoanMucId != null){
			for (int i=0; i < khoanMucId.length; i++) {
				if (khoanMucId[i].trim().length() > 0 && !khoanMucId[i].trim().equals("")) {
					IPhanBo phanBo= new PhanBo();
					phanBo.setKhoanMucId(khoanMucId[i]);
					phanBo.setPhanTram(phanTramKhauHao[i]);
					phanBoList.add(phanBo);
				}
			
				
			}
		}
	
		
		
		
		ccdc.setPhanBoList(phanBoList);



		
		String ngaytao = getDateTime();
		String nguoitao = util.antiSQLInspection(request.getParameter("userId"));
		String ngaysua = getDateTime();
		String nguoisua = util.antiSQLInspection(request.getParameter("userId"));
			
		ccdc.setId(id);
		ccdc.setMa(ma);
		ccdc.setLccdcId(LccdcId);
		ccdc.setDiengiai(diengiai);
		ccdc.setNccdcId(nccdcId);
		ccdc.setSothangKH(stkh);
		ccdc.setThangbatdauKH(tbdkh);
		ccdc.setNguoitao(nguoitao);
		ccdc.setNgaytao(ngaytao);
		ccdc.setNguoisua(nguoisua);
		ccdc.setNgaysua(ngaysua);


		session.setAttribute("obj", ccdc);
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		String action = request.getParameter("action");
		
		System.out.println("Action la: " + action);
		
		if (action.equals("save"))
		{
			if(id.length() > 0 & !err){
				if (!ccdc.UpdateMa(request))
				{
					ccdc.createRs();
//					ccdc.setMsg("Update Khong Thanh Cong");
					session.setAttribute("obj", ccdc);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCuUpdate.jsp";
				}
				else
				{
					IErp_CongCuDungCu obj = new Erp_CongCuDungCu();
					obj.setUserid(userId);
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					ccdc.DBClose();
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCu.jsp";
				}
				
			}else if(id.length() > 0 & err){
				ccdc.createRs();
				session.setAttribute("obj", ccdc);
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCuUpdate.jsp";
				
			}
			else if(id.length() == 0 & !err){
				if (!ccdc.themmoiMa(request))
				{
					ccdc.createRs();
					session.setAttribute("obj", ccdc);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCuNew.jsp";
				}
				else
				{
					IErp_CongCuDungCu obj = new Erp_CongCuDungCu();
					obj.setUserid(userId);
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					ccdc.DBClose();
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCu.jsp";
				}
			}else if(id.length() == 0 & err){
				ccdc.createRs();
				session.setAttribute("obj", ccdc);
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCuNew.jsp";			
			}
		}
		else
		{
				ccdc.setUserid(userId);
				ccdc.createRs();
				
				if(id.trim().length() <= 0)
				{
					session.setAttribute("obj", ccdc);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCuNew.jsp";
				}
				else
				{
					session.setAttribute("obj", ccdc);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongCuDungCuUpdate.jsp";
				}

			
		}
		
		response.sendRedirect(nextJSP);
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}

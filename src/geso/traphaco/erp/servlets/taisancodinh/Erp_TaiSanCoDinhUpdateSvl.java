package geso.traphaco.erp.servlets.taisancodinh;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.taisancodinh.IErp_TaiSanCoDinh;
import geso.traphaco.erp.beans.taisancodinh.IPhanBo;
import geso.traphaco.erp.beans.taisancodinh.imp.Erp_TaiSanCoDinh;
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

import org.apache.poi.hssf.record.formula.functions.Npv;

public class Erp_TaiSanCoDinhUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public Erp_TaiSanCoDinhUpdateSvl( )
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

		IErp_TaiSanCoDinh tsBean = new Erp_TaiSanCoDinh(Id);
		String phanloai = util.antiSQLInspection(request.getParameter("phanloai"));
		System.out.println("phan loai: " + phanloai);
		if(phanloai.equals("TSCD")){
			phanloai = "1";
		}else{
			phanloai = "2";
		}
		
		tsBean.setCtyId((String) session.getAttribute("congtyId"));
		
		tsBean.setUserid(userId);
		tsBean.setId(Id);
		tsBean.setPhanloai(phanloai);
		tsBean.init("");
		tsBean.createRs();

		if (action.equals("update"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhUpdate.jsp";
			session.setAttribute("obj", tsBean);
			response.sendRedirect(nextJSP);
		}else{
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhDisplay.jsp";
			session.setAttribute("obj", tsBean);
			response.sendRedirect(nextJSP);			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		
		String ctyId = (String)session.getAttribute("congtyId");
		String nppId=(String) session.getAttribute("nppId");
		System.out.println("NPPID"+nppId);
		System.out.println("cong ty id tscd: " + ctyId);
		IErp_TaiSanCoDinh ts = new Erp_TaiSanCoDinh();
		ts.setCtyId(ctyId);
		String nextJSP = "";
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		String userTen = util.antiSQLInspection(request.getParameter("userTen"));
		
		String id = util.antiSQLInspection(request.getParameter("id"));
		if(id == null)
			id = "";
		boolean err = false;

		
		String stkh 	= util.antiSQLInspection(request.getParameter("stkh"));
		if(stkh == null) stkh = "0";
		
		String tbdkh 	= util.antiSQLInspection(request.getParameter("tbdkh"));
		if(tbdkh == null) tbdkh = "";
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if(diengiai == null) diengiai = "";
		
		if(diengiai.trim().length() == 0){
			ts.setMsg("Vui lòng nhập Diễn giải tài sản");
			err = true;
		}
		
		String xuatXu = util.antiSQLInspection(request.getParameter("xuatXu"));
		if(xuatXu == null) 
			xuatXu = "";
		
		
		String dvt = util.antiSQLInspection(request.getParameter("dvt"));
		if(dvt == null) 
			dvt = "";
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		
		if(ma == null) ma = "";
		
		if(ma.length() == 0){
			ts.setMsg("Vui lòng nhập Mã tài sản");
			err = true;
		}
		
		

		String lnsId = util.antiSQLInspection(request.getParameter("lnsId"));
		if(lnsId == null) lnsId = "";	
		
		String ttcpId = util.antiSQLInspection(request.getParameter("ttcpId"));
		if(ttcpId == null) 
			ttcpId = "";	
		ts.setTtcp(ttcpId);
		
		String dvthId = util.antiSQLInspection(request.getParameter("dvthId"));
		if(dvthId == null) dvthId ="";
		ts.setDvthId(dvthId);
		
		
//		String dvthId = util.antiSQLInspection(request.getParameter("dvth"));
//		if(dvthId == null) 
//			dvthId = null;	
//		ts.setDvthId(dvthId);
		
		
		String ltsId = util.antiSQLInspection(request.getParameter("ltsId"));
		if(ltsId == null) 
			ltsId = "";	
		System.out.println("Loại tài sản Id:" +ltsId);
		
		String nId 	= util.antiSQLInspection(request.getParameter("nId"));
		if(nId == null) 
			nId = "";	

		String phanloai = util.antiSQLInspection(request.getParameter("phanloai"));
		System.out.println("phan loai: " + phanloai);
		
		ts.setPhanloai(phanloai);

		/*String[] cdtsIds = request.getParameterValues("cdtsIds");
		String[] ttcpIds = request.getParameterValues("ttcpIds");*/
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
	
		
		
		
		ts.setPhanBoList(phanBoList);
			
		String ngaytao = getDateTime();
		String nguoitao = util.antiSQLInspection(request.getParameter("userId"));
		String ngaysua = getDateTime();
		String nguoisua = util.antiSQLInspection(request.getParameter("userId"));
			
		ts.setId(id);
		ts.setMa(ma);
		ts.setDiengiai(diengiai);
		ts.setLapngansachId(lnsId);
		ts.setLtsId(ltsId);
		ts.setDvt(dvt);
		ts.setSothangKH(stkh);
		ts.setThangbatdauKH(tbdkh);
		ts.setNguoitao(nguoitao);
		ts.setNgaytao(ngaytao);
		ts.setNguoisua(nguoisua);
		ts.setNgaysua(ngaysua);
		/*ts.setCdtsIds(cdtsIds);
		ts.setTtcpIds(ttcpIds);*/
	
		session.setAttribute("obj", ts);
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		String action = request.getParameter("action");	
		
		
		System.out.println("Action la: " + action);
		
		if (action.equals("new"))
		{
			if(id.length() > 0 & !err){
				if (!ts.UpdateMa(request))
				{
					ts.createRs();
					//ts.setMsg("Update Khong Thanh Cong");
					session.setAttribute("obj", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhUpdate.jsp";
				}
				else
				{
					IErp_TaiSanCoDinh obj = new Erp_TaiSanCoDinh();
					obj.setUserid(userId);
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinh.jsp";
				}
			}else if(id.length() > 0 & err){
				ts.createRs();
				session.setAttribute("obj", ts);
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhUpdate.jsp";
				
			}
			else if(id.length() == 0 & !err){
				if (!ts.themmoiMa(request))
				{
					ts.createRs();
					session.setAttribute("obj", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhNew.jsp";
				}
				else
				{
					IErp_TaiSanCoDinh obj = new Erp_TaiSanCoDinh();
					obj.setUserid(userId);
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinh.jsp";
				}
			}else if(id.length() == 0 & err){
				ts.createRs();
				session.setAttribute("obj", ts);
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhNew.jsp";			
			}
		}
		else
		{
			if (action.equals("save") & !err)
			{
				if (!ts.UpdateMa(request))
				{
					ts.createRs();
					//ts.setMsg("Update Khong Thanh Cong");
					session.setAttribute("obj", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhUpdate.jsp";
				}
				else
				{
					IErp_TaiSanCoDinh obj = new Erp_TaiSanCoDinh();
					obj.setUserid(userId);
					obj.setCtyId((String) session.getAttribute("congtyId"));
					String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
					GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
					obj.init("");
					ts.DBClose();
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinh.jsp";
				}
			}else if (action.equals("save") & err){
				ts.createRs();
				session.setAttribute("obj", ts);
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhUpdate.jsp";				
			}
			else
			{
				ts.setUserid(userId);
				ts.createRs();
				System.out.println("action: " + action);
				if(id.trim().length() <= 0)
				{
					session.setAttribute("obj", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhNew.jsp";
				}
				else
				{
					session.setAttribute("obj", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TaiSanCoDinhUpdate.jsp";
				}
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
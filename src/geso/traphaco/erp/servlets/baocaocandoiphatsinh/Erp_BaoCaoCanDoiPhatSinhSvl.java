package geso.traphaco.erp.servlets.baocaocandoiphatsinh;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.baocaocandoiphatsinh.IErp_BaoCaoCanDoiPhatSinhList;
import geso.traphaco.erp.beans.baocaocandoiphatsinh.imp.Erp_BaoCaoCanDoiPhatSinhList;
import geso.traphaco.erp.beans.khaibaomau.imp.ErpKhaiBaoMauList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_BaoCaoCanDoiPhatSinhSvl extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   
	   public Erp_BaoCaoCanDoiPhatSinhSvl() {
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
		    
		    Erp_BaoCaoCanDoiPhatSinhList obj = new Erp_BaoCaoCanDoiPhatSinhList();
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    
		    String congTyId = (String)session.getAttribute("congtyId");
		    if (congTyId == null)
		    	congTyId = "0";
		    obj.setCongTyId(congTyId);

		    String action = util.getAction(querystring);
		    out.println(action);
		    

	    	if (action.equals("exportExcel"))
	    	{
//	    		obj.export
	    	}
		   	
			obj.createRs();
			obj.DBClose();
			
	    	session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_BaoCaoCanDoiPhatSinh.jsp");
		}  	
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    Utility util = new Utility();
		    String querystring = request.getQueryString();
		    
			HttpSession session = request.getSession();
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = util.getAction(querystring);
		    	if (action == null)
		    		action = "";
		    }
		    String userId = request.getParameter("userId");
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    
		    if (action.equals("khaiBaoMau"))
		    {
		    	ErpKhaiBaoMauList obj = new ErpKhaiBaoMauList();
		    	String congTyId = (String)session.getAttribute("congtyId");
			    obj.setCongTyId(congTyId);
			    obj.init();
			    
			    session.setAttribute("userId", userId);
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKhaiBaoMau.jsp");
		    	return;
		    }
		    
		    IErp_BaoCaoCanDoiPhatSinhList obj = new Erp_BaoCaoCanDoiPhatSinhList();
			 
		    String congTyId = (String)session.getAttribute("congtyId");
		    obj.setCongTyId(congTyId);
		    
		    
		    String tuNgay = request.getParameter("tuNgay");
		    if (tuNgay == null)
		    	tuNgay = "";
		    obj.getDieuKienLoc().setTuNgay(tuNgay);
		    
		    String denNgay = request.getParameter("denNgay");
		    if (denNgay == null)
		    	denNgay = "";
		    obj.getDieuKienLoc().setDenNgay(denNgay);
		    
		    String soHieuTaiKhoanNo = request.getParameter("soHieuTaiKhoanNo");
		    if (soHieuTaiKhoanNo == null)
		    	soHieuTaiKhoanNo = "";
		    obj.getDieuKienLoc().setSoHieuTaiKhoanNo(soHieuTaiKhoanNo);
		    
		    String soHieuTaiKhoanCo = request.getParameter("soHieuTaiKhoanCo");
		    if (soHieuTaiKhoanCo == null)
		    	soHieuTaiKhoanCo = "";
		    obj.getDieuKienLoc().setSoHieuTaiKhoanCo(soHieuTaiKhoanCo);
		    
		    String loaiDoiTuongNoId = request.getParameter("loaiDoiTuongNoId");
		    if (loaiDoiTuongNoId == null)
		    	loaiDoiTuongNoId = "";
		    obj.getDieuKienLoc().setLoaiDoiTuongNoId(loaiDoiTuongNoId);
		    
		    String loaiDoiTuongCoId = request.getParameter("loaiDoiTuongCoId");
		    if (loaiDoiTuongCoId == null)
		    	loaiDoiTuongCoId = "";
		    obj.getDieuKienLoc().setLoaiDoiTuongCoId(loaiDoiTuongCoId);
		    
		    String doiTuongNoId = request.getParameter("doiTuongNoId");
		    if (doiTuongNoId == null)
		    	doiTuongNoId = "";
		    obj.getDieuKienLoc().setDoiTuongNoId(doiTuongNoId);
		    
		    String soChungTu = request.getParameter("soChungTu");
		    if (soChungTu == null)
		    	soChungTu = "";
		    obj.getDieuKienLoc().setSoChungTu(soChungTu);
		    
		    String congTyLId = request.getParameter("congTyLId");
		    if (congTyLId == null)
		    	congTyLId = "0";
		    obj.getDieuKienLoc().setCongTyId(congTyLId);
		    obj.createRs();
		    if (action.equals("exportExcel"))
		    {
		    	response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BangCanDoiPhatSinh_" + geso.traphaco.center.util.Utility.getCurrentDate()+ ".xlsm");
				String fileName = getServletContext().getInitParameter("path") + "\\BangCanDoiPhatSinh_.xlsm";
		    	obj.init();
		    	obj.exportExcel(response.getOutputStream(), fileName);
		    }
		    else if (action.equals("search"))
		    {
		    	obj.init();
		    }
		    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    {
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.init();
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    } 
		    
		    session.setAttribute("userId", userId);
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_BaoCaoCanDoiPhatSinh.jsp");
	    	
		   
		}   
	}
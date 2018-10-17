package geso.traphaco.erp.servlets.baocaocongno;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.baocaocongno.IErp_BaoCaoCongNoList;
import geso.traphaco.erp.beans.baocaocongno.imp.Erp_BaoCaoCongNoList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_BaoCaoCongNoSvl extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   
	   public Erp_BaoCaoCongNoSvl() {
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
		    
		    Erp_BaoCaoCongNoList obj = new Erp_BaoCaoCongNoList();
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    
		    String congTyId = (String)session.getAttribute("congtyId");
		    if (congTyId == null)
		    	congTyId = "0";
		    obj.setCongTyId(congTyId);

		    String action = util.getAction(querystring);
		    out.println(action);
		    

		    String soHieuTaiKhoan = Utility.getParameter(querystring, "soHieuTaiKhoan");
		    obj.getDieuKienLoc().setSoHieuTaiKhoanNo(soHieuTaiKhoan);
		    
		    String tuNgay = Utility.getParameter(querystring, "tuNgay");
		    if (tuNgay.trim().length() > 0)
		    	obj.getDieuKienLoc().setTuNgay(tuNgay);
		    
		    String denNgay = Utility.getParameter(querystring, "denNgay");
		    if (denNgay.trim().length() > 0)
		    	obj.getDieuKienLoc().setDenNgay(denNgay);
		    
		    congTyId = Utility.getParameter(querystring, "congTyId");
		    if(congTyId != null && congTyId.trim().length() > 0)
		    	obj.getDieuKienLoc().setCongTyId(congTyId);
		   	
			obj.createRs();
			obj.DBClose();
			
	    	session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_BaoCaoCongNo.jsp");
		}  	
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    Utility util = new Utility();
		    String querystring = request.getQueryString();
		    
			HttpSession session = request.getSession();
			IErp_BaoCaoCongNoList obj = new Erp_BaoCaoCongNoList();
			 
		    String userId = request.getParameter("userId");
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = util.getAction(querystring);
		    	if (action == null)
		    		action = "";
		    }
		      
		    String congTyId = (String)session.getAttribute("congtyId");
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    obj.setCongTyId(congTyId);
		    
		    
		    String nhomkhachhang = request.getParameter("nhomkhachhang");
		    if (nhomkhachhang == null||nhomkhachhang=="")
		    	nhomkhachhang = "";
		    obj.getDieuKienLoc().setNhomKhachHangId(nhomkhachhang);
		    System.out.println("nhomkhachhang: " + nhomkhachhang);
		    
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
		    System.out.println("soHieuTaiKhoanNo: " + soHieuTaiKhoanNo);
		    
		    String soHieuTaiKhoanCo = request.getParameter("soHieuTaiKhoanCo");
		    if (soHieuTaiKhoanCo == null)
		    	soHieuTaiKhoanCo = "";
		    obj.getDieuKienLoc().setSoHieuTaiKhoanCo(soHieuTaiKhoanCo);
		    
		    String loaiDoiTuongNoId = request.getParameter("loaiDoiTuongNoId");
		    if (loaiDoiTuongNoId == null)
		    	loaiDoiTuongNoId = "";
		    obj.getDieuKienLoc().setLoaiDoiTuongNoId(loaiDoiTuongNoId);
		    
		    
		    String loaiNhom = request.getParameter("loaiNhom");
		    if (loaiNhom == null)
		    	loaiNhom = "";
		    obj.getDieuKienLoc().setLoaiNhom(loaiNhom);
		    
		    String nhomdoituong = request.getParameter("nhomdoituong");
		    if (nhomdoituong == null)
		    	nhomdoituong = "";
		    obj.getDieuKienLoc().setNhomDoiTuongId(nhomdoituong);
		    
		    System.out.println("loaiDoiTuongNoId: " + loaiDoiTuongNoId);
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
		    
		    String nhomTaiKhoanId = request.getParameter("nhomTaiKhoanId");
		    if (nhomTaiKhoanId == null)
		    	nhomTaiKhoanId = "";
		    obj.getDieuKienLoc().setNhomTaiKhoanId(nhomTaiKhoanId);
		    
		    String congTyLId = request.getParameter("congTyLId");
		    if (congTyLId == null)
		    	congTyLId = "0";
		    obj.getDieuKienLoc().setCongTyId(congTyLId);
		    if (action.equals("exportExcel"))
		    {
		    	response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BangCanDoiPhatSinhCongNo_" + geso.traphaco.center.util.Utility.getCurrentDate()+ ".xlsm");
				String fileName = getServletContext().getInitParameter("path") + "\\BangCanDoiPhatSinhCongNo.xlsm";
		    	obj.init();
		    	obj.exportExcel(response.getOutputStream(), fileName);
		    }
		    else if (action.equals("search"))
		    {
		    	obj.init();
		    	session.setAttribute("obj", obj);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_BaoCaoCongNo.jsp");	    	
		    }
		    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    {
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.init();
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_BaoCaoCongNo.jsp");
		    } else if(action.equals("changTK")){
		    	obj.getLoaiTaiKhoan();
		    	obj.init();
		    	session.setAttribute("obj", obj);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_BaoCaoCongNo.jsp");	
		    }
		    obj.DBClose();
		}   
	}
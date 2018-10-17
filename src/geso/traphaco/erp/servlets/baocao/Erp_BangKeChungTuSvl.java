package geso.traphaco.erp.servlets.baocao;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.bangkechungtu.imp.Erp_BangKeChungTu;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Erp_BangKeChungTuSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_BangKeChungTuSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility Ult = new Utility();
		HttpSession session = request.getSession();

		String querystring = request.getQueryString();
		String userId = Ult.getUserId(querystring);
		
		Erp_BangKeChungTu obj = new Erp_BangKeChungTu();
		String ctyId = (String)session.getAttribute("congtyId");

		obj.setCtyId(ctyId);
		obj.setErpCongtyId(ctyId);
		obj.setuserId(userId);
		
		String view = Ult.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);

		obj.init();
		
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangKeChungTu.jsp";
		response.sendRedirect(nextJSP);
		obj.DBClose();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();
		   
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");

		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
//			OutputStream out = response.getOutputStream();
			Erp_BangKeChungTu obj = new Erp_BangKeChungTu();
			String ctyId = (String)session.getAttribute("congtyId");
			
			obj.setuserId(userId);		
			obj.setCtyId(ctyId);
			obj.setErpCongtyId(ctyId);
			
			String[] ctyIds = request.getParameterValues("ctyIds");
			obj.setCtyIds(ctyIds);
			
			obj.setView(util.antiSQLInspection(request.getParameter("view")));
			
			String year = util.antiSQLInspection(request.getParameter("year"));
			if (year == null)
				year = "";
			obj.setYear(year);

			String month = util.antiSQLInspection(request.getParameter("month"));
			if (month == null)
				month = "";
			
			obj.setMonth(month);
			
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";			
			obj.setTungay(tungay);
			
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";			
			obj.setDenngay(denngay);
			
				
			String tkId = util.antiSQLInspection(request.getParameter("tkId"));
			if (tkId == null)
				tkId = "";
			
			obj.setTkId(tkId);

			String nhomTaiKhoanId = util.antiSQLInspection(request.getParameter("nhomTaiKhoanId"));
			if (nhomTaiKhoanId == null)
				nhomTaiKhoanId = "";
			obj.setNhomTaiKhoanId(nhomTaiKhoanId);
			
			String loaiNghiepVu = util.antiSQLInspection(request.getParameter("loaiNghiepVu"));
			if (loaiNghiepVu == null)
				loaiNghiepVu = "";
			obj.setLoaiNghiepVu(loaiNghiepVu);
			
			String soChungTu = util.antiSQLInspection(request.getParameter("soChungTu"));
			if (soChungTu == null)
				soChungTu = "";
			obj.setSoChungTu(soChungTu);
			
			String loaiDoiTuong = util.antiSQLInspection(request.getParameter("loaiDoiTuong"));
			if (loaiDoiTuong == null)
				loaiDoiTuong = "";
			obj.setLoaiDoiTuong(loaiDoiTuong);
			
			String doiTuong = util.antiSQLInspection(request.getParameter("doiTuong"));
			if (doiTuong == null)
				doiTuong = "";
			obj.setDoiTuong(doiTuong);
			
			String taiKhoanNo = util.antiSQLInspection(request.getParameter("taiKhoanNo"));
			if (taiKhoanNo == null)
				taiKhoanNo = "";
			obj.setTaiKhoanNo(taiKhoanNo);
			System.out.println("taiKhoanNo: " + taiKhoanNo);
			
			String taiKhoanCo = util.antiSQLInspection(request.getParameter("taiKhoanCo"));
			if (taiKhoanCo == null)
				taiKhoanCo = "";
			obj.setTaiKhoanCo(taiKhoanCo);
			
			String congTy = util.antiSQLInspection(request.getParameter("congTy"));
			if (congTy == null)
				congTy = "";
			obj.setCongTy(congTy);
			
			obj.init();
			
			String action = request.getParameter("action");
		
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangKeChungTu.jsp";

			if (action.equals("tao")) {
				try {
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiTietNghiepVuKeToan.xlsm");

				} catch (Exception ex) {
					obj.setMsg(ex.getMessage());
					session.setAttribute("obj", obj);
					response.sendRedirect(nextJSP);
					return;
				}
			}
			if(action.equals("excel")){
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiTietNghiepVuKeToan_" + geso.traphaco.center.util.Utility.getCurrentDate()+ ".xlsm");
				String fileName = getServletContext().getInitParameter("path") + "\\BaoCaoChiTietNghiepVuKeToan.xlsm";
		    	obj.init();
		    	obj.exportExcel(response.getOutputStream(), fileName);
			}else

		    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    	{
			    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    		obj.init();
			    	session.setAttribute("obj", obj);
			    	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangKeChungTu.jsp";
			    }
		    	else
		    	{
		    		obj.init();
					
			    	session.setAttribute("obj", obj);  	
			
		    		nextJSP = "/TraphacoHYERP/pages/Erp/Erp_BangKeChungTu.jsp";
		    	}
		
			session.setAttribute("obj", obj);
			
			response.sendRedirect(nextJSP);
		}
	}
}
package geso.traphaco.erp.servlets.SoTongHopChuTMotTaiKhoanKH;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoanKH.imp.SoTongHopChuTMotTaiKhoanDT;
import geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoanKH.imp.SoTongHopChuTMotTaiKhoanKH;
import geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoanKH.ISoTongHopChuTMotTaiKhoanDT;
import geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoanKH.ISoTongHopChuTMotTaiKhoanKH;
import geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoanKH.imp.SoTongHopChuTMotTaiKhoanKH;
import geso.traphaco.erp.beans.baocaosocaitaikhoan.IErp_BaoCaoSoCaiTaiKhoanList;
import geso.traphaco.erp.beans.baocaosocaitaikhoan.imp.Erp_BaoCaoSoCaiTaiKhoanList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.extentech.formats.XLS.Obj;


public class SoTongHopChuTMotTaiKhoanDTSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SoTongHopChuTMotTaiKhoanDTSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
      
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    ISoTongHopChuTMotTaiKhoanDT obj = new SoTongHopChuTMotTaiKhoanDT();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
		obj.initRs();
		
    	session.setAttribute("obj", obj);

		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/TraphacoHYERP/pages/Erp/SoTongHopChuTCuaMotTaiKhoanDT.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    String querystring = request.getQueryString();
	    
		HttpSession session = request.getSession();
		 
	    String userId = request.getParameter("userId");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = util.getAction(querystring);
	    	if (action == null)
	    		action = "";
	    }
	      
	    String chiNhanh = (String)request.getParameter("chiNhanh");
	    if (chiNhanh == null)
	    	chiNhanh = "1";
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String tuNgay = request.getParameter("tuNgay");
	    if (tuNgay == null)
	    	tuNgay = "";
	    
	    String denNgay = request.getParameter("denNgay");
	    if (denNgay == null)
	    	denNgay = "";
	    
	    String soHieuTaiKhoanNo = request.getParameter("soHieuTaiKhoanNo");
	    if (soHieuTaiKhoanNo == null)
	    	soHieuTaiKhoanNo = "";
	    
	    String khachhang = request.getParameter("khachhang");
	    if (khachhang == null)
	    	khachhang = "";
	    
	    String chinhanhdoitac = request.getParameter("chinhanhdoitac");
	    if (chinhanhdoitac == null)
	    	chinhanhdoitac = "";

	 
	    
	 
	    

		String ctyId = (String)session.getAttribute("congtyId");
		if(ctyId == null) ctyId = "";
	    String nhomkhachhang = request.getParameter("nhomkhachhang");
	    if (nhomkhachhang == null)
	    	nhomkhachhang = "";
	    
	    
	    
	    ISoTongHopChuTMotTaiKhoanDT obj = new SoTongHopChuTMotTaiKhoanDT(tuNgay, denNgay, soHieuTaiKhoanNo, chiNhanh, ctyId);

	    
	    
	    String loaiNhom = request.getParameter("loaiNhom");
	    if (loaiNhom == null)
	    	loaiNhom = "";
	    obj.getDieuKienLoc().setLoaiNhom(loaiNhom);
	    
	    String nhomdoituong = request.getParameter("nhomdoituong");
	    if (nhomdoituong == null)
	    	nhomdoituong = "";
	    obj.getDieuKienLoc().setNhomDoiTuongId(nhomdoituong);

	    obj.setNhomKhachHang(nhomkhachhang);
	    
	    obj.getDieuKienLoc().setSoHieuTaiKhoanNo(soHieuTaiKhoanNo);

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
	    
	    
	    System.out.println("tao day ne"+nhomkhachhang);
	    if (action.equals("exportExcel"))
	    {
	    	response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=SoTongHopChuTCuaMotTaiKhoan_" + geso.traphaco.center.util.Utility.getCurrentDate()+ ".xlsm");
			String fileName = getServletContext().getInitParameter("path") + "\\SoTongHopChuTCuaMotTaiKhoan.xlsm";
	    	obj.initRs();
	    	obj.xuatExcel(response.getOutputStream(), fileName);
	    }
	    else if (action.equals("search"))
	    {
	    	obj.initRs();
	    	obj.initBC();
	    }else if (action.equals("changTK"))
	    {
	    	obj.getLoaiTaiKhoan();
	    	obj.initRs();
	    	
	    }else {
	    	obj.initRs();
	    }
	    	
	    
	   /* else if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    {
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.init();
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    } */
	    
	    session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/SoTongHopChuTCuaMotTaiKhoanDT.jsp");	
		
	}

}

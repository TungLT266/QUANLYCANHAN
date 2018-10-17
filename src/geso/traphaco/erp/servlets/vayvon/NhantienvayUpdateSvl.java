package geso.traphaco.erp.servlets.vayvon;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.vayvon.INhantienvay;
import geso.traphaco.erp.beans.vayvon.INhantienvayList;
import geso.traphaco.erp.beans.vayvon.imp.Nhantienvay;
import geso.traphaco.erp.beans.vayvon.imp.NhantienvayList;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhantienvayUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
       public NhantienvayUpdateSvl() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
//	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    String nppId = (String)session.getAttribute("nppId");
	    Utility util = new Utility();
//	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    nppId = util.getIdNhapp(userId);
	    String id = util.getId(querystring);  
	    //System.out.println("UserId Nhantienvay "+id);
	    String action = util.getAction(querystring);
	    
	    INhantienvay obj = new Nhantienvay(id); 
	    obj.setCtyId(ctyId);
	    obj.setNppId(nppId);
	    
	    obj.setUserId(userId);
   		obj.init();
   		 
	    obj.createPhieuChi();
	    
	    if(action.equals("delete"))
	    { 
	    	obj.Xoa();
	    	INhantienvayList obj1 = new NhantienvayList();
	    	obj1.setCtyId(ctyId);
	    	obj1.setNppId(nppId);
	        obj1.init("");
	        obj1.setMsg(obj.getmsg());
	    	
		    session.setAttribute("obj",obj1);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Nhantienvay.jsp");	
			obj.DbClose();
	    }
	    else if(action.equals("finish")){
	    	
	    	INhantienvayList obj1 = new NhantienvayList();
	    	
	    	obj1.setMsg(obj.Hoantat());
	    	
	    	obj1.setCtyId(ctyId);
	    	obj1.setNppId(nppId);
	    	obj1.init("");
	    	session.setAttribute("obj",obj1);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Nhantienvay.jsp");
	    	obj.DbClose();
	    }else if(action.equals("display")){
	    
	    	session.setAttribute("obj",obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/NhantienvayDisplay.jsp");
	    }else{
	    	session.setAttribute("obj",obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/NhantienvayUpdate.jsp");
	    }
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
// 	    PrintWriter out = response.getWriter();
 	    INhantienvay obj = new Nhantienvay();		
 		HttpSession session = request.getSession();
 		Utility util = new Utility();
 		
 		String ctyId  = (String)session.getAttribute("congtyId");
 		String nppId  = (String)session.getAttribute("nppId");
 		obj.setCtyId(ctyId);
 		obj.setNppId(nppId);
 		obj.init();
 		
 		
 	    String chonGN = util.antiSQLInspection(request.getParameter("chonGN"));
 	    if(chonGN ==null)
 	    	chonGN ="0";
 	    obj.setGiainganveTK(chonGN);
 	    
 	    if(chonGN.equals("0")){
 	    	String[] uncIds = request.getParameterValues("chiId");
 	    	obj.setUNCIds(uncIds);
 	    	
 	    	String[] nccIds = request.getParameterValues("nccId");
 	    	obj.setNccIds(nccIds);
 	    	
 	    	String[] nccTen = request.getParameterValues("nccTen");
 	    	obj.setNccTen(nccTen);
 	    	
 	    	String[] sotienNTs = request.getParameterValues("sotienHDNT");
 	    	obj.setSotienNTs(sotienNTs);	    	
 	    	
 	    	String[] tgs = request.getParameterValues("tigiaHD");
 	    	obj.setTgs(tgs);

 	    	String[] sotienVNDs = request.getParameterValues("sotienHDVND");
 	    	obj.setSotienVNDs(sotienVNDs);

 	    	String[] pays = request.getParameterValues("thanhtoan");
 	    	obj.setPays(pays);
 	    	
 	    	String[] conlai = request.getParameterValues("conlaiHD");
 	    	obj.setConlai(conlai);

 	    }
 	    
 	    String userId = util.antiSQLInspection(request.getParameter("userId"));
 	    if(userId == null)
 	    	userId = "";
 	    obj.setUserId(userId);
 	   nppId = util.getIdNhapp(userId);
 	    
 	    String Id = util.antiSQLInspection(request.getParameter("Id"));
 	    if(Id ==null)
 		   Id ="";
 	    obj.setId(Id);
 	   
 	    String hdId = util.antiSQLInspection(request.getParameter("hdId"));
 	    if(hdId == null)
 	    	hdId = "";
 	    obj.setSoHD(hdId);

 	    String ngay = util.antiSQLInspection(request.getParameter("ngay"));
 	    if(ngay == null)
 	    	ngay = "";
 	    obj.setNgay(ngay);
 	    
 	   String thoihan = util.antiSQLInspection(request.getParameter("thoihan"));
	    if(thoihan == null)
	    	thoihan = "";
	    obj.setThoihan(thoihan);
	    
 	// thời gian đáo hạn - 03/12/2015
 	    String ngaydaohan ="";
 	    // ngày đáo hạn kiểm tra xem sẽ tự động lấy ra hay lấy nội dung người ta đã nhập
 	    String ngaydaohan_check = "";
 	    if(request.getParameter("ngaydaohan")!=null){
 	    	if(thoihan.trim().length()>0 && ngay.trim().length() >0){
 	    		Date date = ConvertString2Date(ngay);
 	    		ngaydaohan_check= ConvertDate2String(addMonths(date, Integer.parseInt(thoihan)));
 	    	}
 	    	ngaydaohan= request.getParameter("ngaydaohan");
 	    	// nếu ngày đáo hạn tự động không xê dịch không quá 15 ngày// thì ngày đáo hạn bằng nhập

 	    	long d1 = ConvertString2Date(ngaydaohan_check).getTime();
 	    	long d2 = ConvertString2Date(ngaydaohan).getTime();

 	    	long songaychenhlech = Math.abs((d1-d2)/(1000*60*60*24));
 	    	if( songaychenhlech <15 ){
 	    		ngaydaohan = request.getParameter("ngaydaohan"); // bằng với ngày người dùng chỉnh sửa
 	    	} else{
 	    		ngaydaohan = ngaydaohan_check;  // bằng với ngày tự động tính ra
 	    	}
 	    }
 	    obj.setNgayDaoHan(ngaydaohan);

 	    
 	    String laisuat = util.antiSQLInspection(request.getParameter("laisuat"));
 	    if(laisuat == null)
 	    	laisuat = "";
 	    obj.setLaisuat(laisuat);
 	    
 	    String hinhthuc = util.antiSQLInspection(request.getParameter("hinhthuc"));
 	    if(hinhthuc == null)
 	    	hinhthuc = "";
 	    obj.setHinhthuc(hinhthuc);

 	 
 	    
 	    String[] ngaytravay = request.getParameterValues("ngaytravay");
 	    if(ngaytravay != null) obj.setNgaytravay(ngaytravay);
 	    	
 	    String[] travay = request.getParameterValues("travay");
 	    if(travay != null) obj.setTravay(travay);

 	    String[] tralai = request.getParameterValues("tralai");
	    if(tralai != null) obj.setTralai(tralai);
	    
	    String[] conlai = request.getParameterValues("conlai");
 	    if(conlai != null) obj.setConlai(conlai);
 	    
 	    obj.Init_Array();
 	    
 	    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
 	    if(ghichu == null) ghichu = "";
 	    obj.setGhichu(ghichu);


 	    
 	    String ttId = util.antiSQLInspection(request.getParameter("ttId"));
 	    if(ttId == null) ttId = "";
 	    obj.setTtId(ttId) ;
 	    
 	    String sotaikhoan = util.antiSQLInspection(request.getParameter("sotaikhoan"));
 	    if(sotaikhoan== null) sotaikhoan= "";
 	    obj.setSotaikhoan(sotaikhoan);
 	    
  	    String tienteId = util.antiSQLInspection(request.getParameter("tienteId"));
 	    if(tienteId== null) tienteId= "";
 	    obj.setTienteId(tienteId);
	    
	    String tigia = util.antiSQLInspection(request.getParameter("tigia"));
	    if(tigia== null) tigia= "1";
	    obj.setTigia(tigia.replaceAll(",", ""));
	    
	    String sotienNT = util.antiSQLInspection(request.getParameter("sotienNT"));
	    if(sotienNT== null) sotienNT= "0";
	    obj.setSotienNT(sotienNT.replaceAll(",", ""));
	    
	    String sotienVND = util.antiSQLInspection(request.getParameter("sotienVND"));
	    if(sotienVND== null) sotienVND= "0";
	    obj.setSotienVND(sotienVND.replaceAll(",", ""));
	    
	    String sotienconlai = util.antiSQLInspection(request.getParameter("sotienconlai"));
	    if(sotienconlai== null) sotienconlai= "0";
	    obj.setStConlai(sotienconlai.replaceAll(",", ""));
	    
	    String sotienvay = util.antiSQLInspection(request.getParameter("sotienvay"));
	    if(sotienvay== null) sotienvay= "0";
	    obj.setSotienvay(sotienvay.replaceAll(",", ""));
	    
	    String tkvay = util.antiSQLInspection(request.getParameter("tkvay"));
	    if(tkvay== null) tkvay= "";
	    obj.setTkvay(tkvay);
	    
	    System.out.println("chonGN: " + chonGN);
 	    String action = request.getParameter("action");
	    if(action.equals("save"))
	    {
	    	if( !obj.save())
	    	{
		    	obj.init();
		    	obj.createPhieuChi();	
	    		
	    		session.setAttribute("obj",obj);
	    	    response.sendRedirect("/TraphacoHYERP/pages/Erp/NhantienvayUpdate.jsp");
	    	    return;
	     	}
    		INhantienvayList obj1 = new NhantienvayList();
    		obj1.setCtyId(ctyId);
    		obj1.setNppId(nppId);
    		obj1.init("");
    		session.setAttribute("obj",obj1);
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Nhantienvay.jsp");	    	
	    }
	    else
	    {	
	    		
	    	//obj.init();
	    	obj.XacdinhTienTe();
	    	obj.createPhieuChi();
	    	session.setAttribute("obj",obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/NhantienvayUpdate.jsp");
    	}
    }
	
	private Date addMonths(Date date, int months)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.MONTH, months); //minus number would decrement the days
	    return (Date) cal.getTime();
	}
	private String ConvertDate2String(Date date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String reportDate = df.format(date);
		return reportDate;
	}
	private Date ConvertString2Date(String date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date reportDate = new Date();
		try {
			reportDate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reportDate;
	}
	    

}

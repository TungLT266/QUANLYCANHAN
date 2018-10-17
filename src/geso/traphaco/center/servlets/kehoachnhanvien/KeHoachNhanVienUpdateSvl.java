package geso.traphaco.center.servlets.kehoachnhanvien;

import geso.traphaco.center.beans.kehoachnhanvien.imp.*;
import geso.traphaco.center.beans.kehoachnhanvien.*;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KeHoachNhanVienUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public KeHoachNhanVienUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);	

	    IKeHoachNhanVien khnvBean = new KeHoachNhanVien(userId, id);
	    
	    String nextJSP = "";
	    if(request.getQueryString().indexOf("update") >= 0 && khnvBean.getTrangthai().equals("0") ) 
	    {
	    	//Update
	    	khnvBean.init();
		    khnvBean.createRs();
	    	nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVienUpdate.jsp";
	    } 
	    else
	    {
	    	//Display
	    	khnvBean.init(true);
	    	nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVienDisplay.jsp";
	    }
	    
        session.setAttribute("khnvBean", khnvBean);
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		boolean error = false;
		
		IKeHoachNhanVien khnvBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		
	    String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	khnvBean = new KeHoachNhanVien(userId, "");
	    } else {
	    	khnvBean = new KeHoachNhanVien(userId, id);
	    }
	    
    	String tennhanvien = util.antiSQLInspection(request.getParameter("tennhanvien"));
		if (tennhanvien == null) tennhanvien = ""; else tennhanvien = tennhanvien.trim();
    	khnvBean.setTenNhanVien(tennhanvien);
    	
    	String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null) thang = ""; else thang = thang.trim();
		khnvBean.setThang(thang);

		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null) nam = ""; else nam = nam.trim();
		khnvBean.setNam(nam);
		
		//Lấy số ngày trong tháng
		long _nam = 0;
		try { _nam = Math.round(Double.parseDouble(nam)); } catch(Exception e) { }
		
		long _thang = 0;
		try { _thang = Math.round(Double.parseDouble(thang)); } catch(Exception e) { }
		
		Calendar mycal = new GregorianCalendar((int)_nam, (int)_thang-1, 1);
		int _songay = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);		
		
		String ngaysua = getDateTime();
    	khnvBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		khnvBean.setNguoisua(nguoisua);
		
		String[] ghichunpps, ghichutts;
		IKeHoachNhanVienNgay[] ngays = new IKeHoachNhanVienNgay[_songay];
		IKeHoachNhanVienNgay khNgay;
		IKeHoachNhanVienChiTiet chitiet;
		String[] npps, tinhs, quanhuyens;
		List<String> nppErrorList = new ArrayList<String>();
		List<String> tinhErrorList = new ArrayList<String>();
		List<String> quanErrorList = new ArrayList<String>();
		for(int i = 0; i < _songay; i++) 
		{
			khNgay = new KeHoachNhanVienNgay("");
			khNgay.setNgay(String.valueOf(i+1));
			ngays[i] = khNgay;
			
			//Insert chi tiết			
			npps = request.getParameterValues("ngay"+khNgay.getNgay() + "_npp"); if(npps == null) npps = new String[0];
			ghichunpps = request.getParameterValues("ngay"+khNgay.getNgay() + "_nppghichu"); if(ghichunpps == null) ghichunpps = new String[0];
			tinhs = request.getParameterValues("ngay"+khNgay.getNgay() + "_tinhthanh"); if(tinhs == null) tinhs = new String[0];
			quanhuyens = request.getParameterValues("ngay"+khNgay.getNgay() + "_quanhuyen"); if(quanhuyens == null) quanhuyens = new String[0];
			ghichutts = request.getParameterValues("ngay"+khNgay.getNgay() + "_ttghichu"); if(ghichutts == null) ghichutts = new String[0];
			
			List<IKeHoachNhanVienChiTiet> nppList = khNgay.getNppList();
			for(int j = 0; j < npps.length; j++) {
				if(npps[j] == null) npps[j] = "";
				if(ghichunpps[j] == null) ghichunpps[j] = "";
				chitiet = new KeHoachNhanVienChiTiet("");
				chitiet.setNppId(npps[j]);
				chitiet.setGhiChu(ghichunpps[j]);
				nppList.add(chitiet);
				
				if(npps[j].trim().length() <= 0) {
					if(nppErrorList.indexOf(khNgay.getNgay()) < 0) {
						nppErrorList.add(khNgay.getNgay());
					}
				}
			}
			
			List<IKeHoachNhanVienChiTiet> thitruongList = khNgay.getThiTruongList();
			for(int j = 0; j < tinhs.length; j++) {
				if(tinhs[j] == null) tinhs[j] = "";
				if(quanhuyens[j] == null) quanhuyens[j] = "";
				if(ghichutts[j] == null) ghichutts[j] = "";
				chitiet = new KeHoachNhanVienChiTiet("");
				chitiet.setTinhId(tinhs[j]);
				chitiet.setQuanHuyenId(quanhuyens[j]);
				chitiet.setGhiChu(ghichutts[j]);
				thitruongList.add(chitiet);

				if(tinhs[j].trim().length() <= 0) {
					if(tinhErrorList.indexOf(khNgay.getNgay()) < 0) {
						tinhErrorList.add(khNgay.getNgay());
					}
				}
				if(quanhuyens[j].trim().length() <= 0) {
					if(quanErrorList.indexOf(khNgay.getNgay()) < 0) {
						quanErrorList.add(khNgay.getNgay());
					}
				}
			}
		}
		khnvBean.setNgayList(ngays);

		if (thang.trim().length()== 0) {
			khnvBean.setMessage("Vui lòng chọn tháng lập bảng kế hoạch!");
			error = true;
		} 
		else if (nam.trim().length()== 0) {
			khnvBean.setMessage("Vui lòng chọn năm lập bảng kế hoạch!");
			error = true;
		} else if (nppErrorList.size() > 0) {
			String ngayErrorStr = nppErrorList.get(0);
			for(int i = 1; i < nppErrorList.size(); i++) {
				ngayErrorStr += ", " + nppErrorList.get(i);
			}
			
			khnvBean.setMessage("Vui lòng chọn nhà phân phối ở kế hoạch ngày " + ngayErrorStr);
			error = true;
		} else if (tinhErrorList.size() > 0) {
			String ngayErrorStr = tinhErrorList.get(0);
			for(int i = 1; i < tinhErrorList.size(); i++) {
				ngayErrorStr += ", " + tinhErrorList.get(i);
			}
			
			khnvBean.setMessage("Vui lòng chọn tỉnh thành ở kế hoạch ngày " + ngayErrorStr);
			error = true;
		} else if (quanErrorList.size() > 0) {
			String ngayErrorStr = quanErrorList.get(0);
			for(int i = 1; i < quanErrorList.size(); i++) {
				ngayErrorStr += ", " + quanErrorList.get(i);
			}
			
			khnvBean.setMessage("Vui lòng chọn quận huyện ở kế hoạch ngày " + ngayErrorStr);
			error = true;
		}
 		
 		String action = request.getParameter("action");
	    if(!error) 
	    {
	    	if(action.equals("save"))
	    	{
	    		if ( id == null || id.trim().length() == 0) {
	    			if (!(khnvBean.create())) {
	    				khnvBean.createRs();
	    				session.setAttribute("khnvBean", khnvBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVienNew.jsp";
	    				response.sendRedirect(nextJSP);
	    			} else {
	    				khnvBean.closeDB();
	    				IKeHoachNhanVienList obj = new KeHoachNhanVienList();
	    				obj.setUserId(userId);
	    			    obj.init("");
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVien.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
				
	    		} else {
	    			if (!(khnvBean.update())) {
	    				khnvBean.createRs();
	    				session.setAttribute("khnvBean", khnvBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVienUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    			}
	    			else {
	    				khnvBean.closeDB();
	    				IKeHoachNhanVienList obj = new KeHoachNhanVienList();
	    				obj.setUserId(userId);
	    			    obj.init("");
	    				session.setAttribute("obj", obj);
						
	    				String nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVien.jsp";
	    				response.sendRedirect(nextJSP);			    			    									
	    			}
	    		}
	    	} else {
				khnvBean.createRs();
	    		session.setAttribute("khnvBean", khnvBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVienNew.jsp";
	    		}else{
	    			nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVienUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
			
	    	}
	    } 
	    else 
	    {
			khnvBean.createRs();
    		session.setAttribute("khnvBean", khnvBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVienNew.jsp";
    		}else{
    			nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVienUpdate.jsp";   						
    		}
    		response.sendRedirect(nextJSP);
	    	
	    }
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}

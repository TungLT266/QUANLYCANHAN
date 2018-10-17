package geso.traphaco.center.servlets.nhiemvu;

import geso.traphaco.center.beans.nhiemvu.*;
import geso.traphaco.center.beans.nhiemvu.imp.*;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhiemVuNhanVienUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public NhiemVuNhanVienUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	    
	    System.out.println("[NhiemVuNhanVienUpdateSvl.doGet] id = " + id);

	    INhiemVuNhanVien nvnvBean = new NhiemVuNhanVien(id);
	    nvnvBean.setUserId(userId);
        nvnvBean.init();
        nvnvBean.loadNhiemVuNhanVienChiTietList(id);
        
        session.setAttribute("nvnvBean", nvnvBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/NhiemVuNhanVienUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		INhiemVuNhanVien bean;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	bean = new NhiemVuNhanVien("");
	    } else {
	    	bean = new NhiemVuNhanVien(id);
	    }
	    System.out.println("[NhiemVuNhanVienUpdateSvl.doPost] id = " + bean.getId());
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		bean.setUserId(userId);
    	System.out.println("[NhiemVuNhanVienUpdateSvl.doPost] userId = " + bean.getUserId());
    	
    	String doituong = util.antiSQLInspection(request.getParameter("doituong"));
    	if (doituong == null) { doituong = ""; }
    	bean.setDoiTuong(doituong.trim());
    	System.out.println("[NhiemVuNhanVienUpdateSvl.doPost] doituong = " + bean.getDoiTuong());
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
    	if ( diengiai == null) { diengiai = ""; }
    	bean.setDienGiai(diengiai.trim());
    	System.out.println("[NhiemVuNhanVienUpdateSvl.doPost] diengiai = " + bean.getDienGiai());
    	
		String thang = util.antiSQLInspection(request.getParameter("thang"));
    	if ( thang == null) { thang = ""; }
    	bean.setThang(Integer.parseInt(thang));
    	System.out.println("[NhiemVuNhanVienUpdateSvl.doPost] thang = " + bean.getThang());
    	
		String nam = util.antiSQLInspection(request.getParameter("nam"));
    	if ( nam == null) { nam = ""; }
    	bean.setNam(Integer.parseInt(nam));
    	System.out.println("[NhiemVuNhanVienUpdateSvl.doPost] nam = " + bean.getNam());
    	
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkd"));
    	if ( dvkdId == null) { dvkdId = ""; }
    	bean.setDvkdId(dvkdId.trim());
    	System.out.println("[NhiemVuNhanVienUpdateSvl.doPost] dvkdId = " + bean.getDvkdId());
    	
		String kbhId = util.antiSQLInspection(request.getParameter("kbh"));
    	if ( kbhId == null) { kbhId = ""; }
    	bean.setKbhId(kbhId.trim());
    	System.out.println("[NhiemVuNhanVienUpdateSvl.doPost] kbhId = " + bean.getKbhId());
    	
    	//Các Nhiệm Vụ
    	List<INhiemVuNhanVienChiTiet> nvList = bean.createNhiemVuNhanVienChiTietList();
    	for(int i = 0; i < nvList.size(); i++) {
    		INhiemVuNhanVienChiTiet _nv = nvList.get(i);
    		String paramName = "thuong_"+_nv.getNvId();
    		String soluong = util.antiSQLInspection(request.getParameter("soluong_"+_nv.getNvId()));
    		String thuong = util.antiSQLInspection(request.getParameter(paramName));
    		if(soluong==null||soluong.trim().length()<=0)
    			soluong="0";    		
    		if(thuong == null) { thuong = ""; }
    			else { thuong = thuong.trim().replace(",", ""); }
    		System.out.println("[NhiemVuNhanVienUpdateSvl.doPost] name=" + paramName + ", thuong = " + thuong);    		
    		if(thuong.length() > 0) 
    		{
    			_nv.setThuong(Float.parseFloat(thuong));
    			_nv.setSoluong(Float.parseFloat(soluong));
    		}
    	}
    	
    	String ngaysua = getDateTime();
		bean.setNgaySua(ngaysua);
		System.out.println("[NhiemVuNhanVienUpdateSvl.doPost] ngaysua = " + bean.getNgaySua());
	
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
    		if (id == null || id.length() == 0 )
    		{ //Create New NhiemVuNhanVien
    			if(!bean.create())
    			{
    		    	bean.setUserId(userId);
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("nvnvBean", bean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/NhiemVuNhanVienNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else 
    			{
    				INhiemVuNhanVienList obj = new NhiemVuNhanVienList();
    				obj.setUserId(userId);
    				obj.init("");
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/TraphacoHYERP/pages/Center/NhiemVuNhanVien.jsp");
    			}
    		} 
    		else 
    		{
    			//Update NhiemVuNhanVien
    			if ( !(bean.update()) )
    			{
    				//Update unsuccessfully
    		    	bean.setUserId(userId);
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("nvnvBean", bean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/NhiemVuNhanVienUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else 
    			{
    				//Update successfully
    				INhiemVuNhanVienList obj = new NhiemVuNhanVienList();
    				obj.setUserId(userId);
    				obj.init("");
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/TraphacoHYERP/pages/Center/NhiemVuNhanVien.jsp");
    			}
    		}
	    }
		else
		{
			session.setAttribute("userId", userId);
			session.setAttribute("bean", bean);
			String nextJSP = id == null ?  "/TraphacoHYERP/pages/Center/NhiemVuNhanVienNew.jsp" : "/TraphacoHYERP/pages/Center/NhiemVuNhanVienUpdate.jsp";   						
			response.sendRedirect(nextJSP);
		}	
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}

package geso.traphaco.erp.servlets.kiemdinhchatluong.kho;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kiemdinhchatluong.kho.IErpKiemDinhChatLuongKho;
import geso.traphaco.erp.beans.kiemdinhchatluong.kho.IErpKiemDinhChatLuongKhoList;
import geso.traphaco.erp.beans.kiemdinhchatluong.kho.imp.ErpKiemDinhChatLuongKho;
import geso.traphaco.erp.beans.kiemdinhchatluong.kho.imp.ErpKiemDinhChatLuongKhoList;

public class ErpKiemDinhChatLuongKhoUpdateSvl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpKiemDinhChatLuongKhoUpdateSvl() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpKiemDinhChatLuongKho kdcl;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    kdcl = new ErpKiemDinhChatLuongKho();
	    kdcl.setId(id);
	    kdcl.setUserId(userId);
	    kdcl.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    
	    // lấy nó thuộc loại nào?
	    // lay loai mua hang de xem la mua hang nhap khau hay trong nuoc
		String loaimh = request.getParameter("loaiMH");
		 if(loaimh.equals("0")) // mua hang nhapkhau
	    {
		 	kdcl.setLoaimh("0");
	    }else if(loaimh.equals("1"))  // mua hang trong nuoc
	    {
	    	kdcl.setLoaimh("1");
	    }
		
	    kdcl.init();
        session.setAttribute("kdcl", kdcl);
        
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongKhoUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongKhoUpdate.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		NumberFormat formatter = new DecimalFormat("#######.###");  
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpKiemDinhChatLuongKho kdcl;

		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("id"));
		
		String loaispId = util.antiSQLInspection(request.getParameter("loaispId"));
		if(loaispId == null) loaispId = "";
		
		String khoId = util.antiSQLInspection(request.getParameter("khoId"));
		if(khoId == null) khoId = "";
		
		String loai = util.antiSQLInspection(request.getParameter("loai"));
		if(loai == null) loai = "";
		
		String spId = util.antiSQLInspection(request.getParameter("spId"));
		if(spId == null) spId = "";
		
		// cho phép chỉnh lại mã phiếu
		String maphieu = util.antiSQLInspection(request.getParameter("maphieu"));
		if(maphieu == null) maphieu = "";
		
		String datcl = util.antiSQLInspection(request.getParameter("datcl"));
		if(datcl == null) datcl = "";
		
		String soluongkiemdinh = util.antiSQLInspection(request.getParameter("soluongkiemdinh"));
		if(soluongkiemdinh == null) soluongkiemdinh = "";
		
		String soluongdat = util.antiSQLInspection(request.getParameter("soluongdat"));
		if(soluongdat == null) soluongdat = "";
		
		String solo = util.antiSQLInspection(request.getParameter("solo"));
		if(solo == null) solo = "";
		
		String ngaykiem = util.antiSQLInspection(request.getParameter("ngaykiem"));
		if(ngaykiem == null) ngaykiem = "";
		
		String tinhtrang = util.antiSQLInspection(request.getParameter("tinhtrang"));
		if(tinhtrang == null) tinhtrang = "";
		
		String denghixuly =  request.getParameter("denghixuly") ;
		if(denghixuly == null) denghixuly = "";

		if (id == null)
		{
			kdcl = new ErpKiemDinhChatLuongKho();
		} 
		else
		{
			kdcl = new ErpKiemDinhChatLuongKho();
			kdcl.setId(id);
			//set id
			
		}

	 
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		kdcl.setUserId(userId);
		String congtyId = ((String) session.getAttribute("congtyId"));
		kdcl.setCongtyId(congtyId);
		
		kdcl.setMaPhieu(maphieu);
		kdcl.setSoluongDat(soluongdat);
		kdcl.setSoLuongKiemDinh(soluongkiemdinh);
		
		double soluongmau_ct=0;
		try{
			soluongmau_ct =Double.parseDouble(request.getParameter("soluongmau").replaceAll(",",""));
		}catch (Exception e) {
			 
		}
		kdcl.setSoluongmau(formatter.format(soluongmau_ct));

		kdcl.setSoLo(solo);
		kdcl.setDatCl(datcl);
		kdcl.setKhoId(khoId);
		
		kdcl.setSpId(spId);
		kdcl.setNgaykiem(ngaykiem);
		kdcl.setTrangthai(tinhtrang);
		kdcl.setDenghixuly(denghixuly.trim());
		
		String action = request.getParameter("action");
		if (action.equals("save"))
		{
			if (!(kdcl.create()))
			{
				kdcl.createRs();
				session.setAttribute("kdcl", kdcl);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongKhoUpdate.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				IErpKiemDinhChatLuongKhoList obj = new ErpKiemDinhChatLuongKhoList();
				String loaimh = request.getParameter("loaiMH");
				 if(loaimh.equals("0")) // mua hang nhapkhau
			    {
					 obj.setloaimuahang("0");
			    }else if(loaimh.equals("1"))  // mua hang trong nuoc
			    {
			    	obj.setloaimuahang("1");
			    }
				
			    obj.setUserId(userId);
			    obj.init("");
				session.setAttribute("obj", obj);
			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongKho.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		else if (action.equals("duyet")){
			if ( !kdcl.update() || !(kdcl.duyetKiemDinh())){
				kdcl.init();
				kdcl.createRs();
				session.setAttribute("kdcl", kdcl);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongKhoUpdate.jsp";
				response.sendRedirect(nextJSP);
			} 
			else{
				IErpKiemDinhChatLuongKhoList obj = new ErpKiemDinhChatLuongKhoList();
			
				String loaimh = request.getParameter("loaiMH");
				 if(loaimh.equals("0")) // mua hang nhapkhau
			    {
					 obj.setloaimuahang("0");
			    }else if(loaimh.equals("1"))  // mua hang trong nuoc
			    {
			    	obj.setloaimuahang("1");
			    }
				 
			    obj.setUserId(userId);
			    obj.init("");
				session.setAttribute("obj", obj);
			    
			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongKho.jsp";
			    response.sendRedirect(nextJSP);
			}
		}else{
	    	
			kdcl.setUserId(userId);
			kdcl.createRs();
    		
	    	session.setAttribute("kdcl", kdcl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongKhoUpdate.jsp");

		}
	}
}

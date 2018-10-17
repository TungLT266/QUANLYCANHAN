package geso.traphaco.center.servlets.nhacungcap;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.nhacungcap.INhacungcap;
import geso.traphaco.center.beans.nhacungcap.imp.Nhacungcap;
import geso.traphaco.center.beans.nhacungcap.INhacungcapList;
import geso.traphaco.center.beans.nhacungcap.imp.NhacungcapList;

 public class NhacungcapUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   
	public NhacungcapUpdateSvl() {
		super();
	}   	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    INhacungcap ncc = new Nhacungcap();

// Get data from NhacungcapUpdate.jsp	
	    
	    Utility util = new Utility();
	    
	    String ten = util.antiSQLInspection(request.getParameter("TenNCC_long"));
    	String tenviettat = util.antiSQLInspection(request.getParameter("TenNCC_short"));
    	String diachi = util.antiSQLInspection(request.getParameter("DiaChi"));
    	String masothue = util.antiSQLInspection(request.getParameter("MaSoThue"));
		String ngaysua =  getDateTime();
		String nguoisua = util.antiSQLInspection(request.getParameter("userId"));
	 	String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon"));
	 	ncc.setKyhieuhoadon(kyhieuhoadon);
    	String sohoadontu = util.antiSQLInspection(request.getParameter("sohoadontu"));
    	ncc.setSohoadontu(sohoadontu);   	
    	String sohoadonden= util.antiSQLInspection(request.getParameter("sohoadonden"));
    	ncc.setSohoadonden(sohoadonden);
		String trangthai;
		if(util.antiSQLInspection(request.getParameter("trangthai"))!= null)
			trangthai = "1";
		else
			trangthai = "0";
		ncc.setTrangthai(trangthai);
		
		String nccId = util.antiSQLInspection(request.getParameter("nccId"));

// Save data into Nhacungcap Bean		
		ncc.setId(nccId);
		ncc.setNgaysua(ngaysua);
		ncc.setNguoisua(nguoisua);
		ncc.setTrangthai(trangthai);
	
		boolean error = false;
		if (masothue.trim().length()> 0)
			ncc.setMasothue(masothue);
		else{
			ncc.setMessage("Vui long nhap vao ma so thue Nha Cung Cap");
			error = true;
		}

		if (diachi.trim().length()> 0)
			ncc.setDiachi(diachi);
		else{
			ncc.setMessage("Vui long nhap vao dia chi Nha Cung Cap");
			error = true;
		}

		if (tenviettat.trim().length()> 0)
			ncc.setTenviettat(tenviettat);
		else{
			ncc.setMessage("Vui long nhap vao ten viet tat cua Nha Cung Cap");
			error = true;
		}
		
		if (ten.trim().length()> 0)
			ncc.setTen(ten);
		else{
			ncc.setMessage("Vui long nhap vao ten Nha Cung Cap");
			error = true;
		}

		HttpSession session = request.getSession();
		session.setAttribute("userId", nguoisua);
		if (error){
    		session.setAttribute("ncc", ncc);
    		String nextJSP = "/TraphacoHYERP/pages/Center/NhaCungCapUpdate.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			if (!ncc.UpdateNcc()){
				session.setAttribute("ncc", ncc);    		
	    		String nextJSP = "/TraphacoHYERP/pages/Center/NhaCungCapUpdate.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
		    	INhacungcapList obj = new NhacungcapList();	    	
		    	
		    	obj.init();
				
		    	//Data object is saved into session
		    	session.setAttribute("obj", obj);
								
		    	String nextJSP = "/TraphacoHYERP/pages/Center/NhaCungCap.jsp";
		    	response.sendRedirect(nextJSP);
			}
		}
	} 	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
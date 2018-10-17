package geso.traphaco.center.servlets.nhacungcap;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import geso.traphaco.center.beans.nhacungcap.imp.Nhacungcap;
import geso.traphaco.center.beans.nhacungcap.imp.NhacungcapList;
import geso.traphaco.center.beans.nhacungcap.INhacungcap;
import geso.traphaco.center.beans.nhacungcap.INhacungcapList;
import geso.traphaco.center.util.Utility;

 public class NhacungcapNewSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	public NhacungcapNewSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		INhacungcap nccBean = new Nhacungcap();	
		
		Utility util = new Utility();
		
		// Collecting data from NhaCungCapNew.jsp
    	String tenncc = util.antiSQLInspection(request.getParameter("TenNCC_long"));
    	String tenviettat = util.antiSQLInspection(request.getParameter("TenNCC_short"));
    	String diachi = util.antiSQLInspection(request.getParameter("DiaChi"));
    	String masothue = util.antiSQLInspection(request.getParameter("MaSoThue"));
    	String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon"));
		nccBean.setKyhieuhoadon(kyhieuhoadon);
    	String sohoadontu = util.antiSQLInspection(request.getParameter("sohoadontu"));
    	nccBean.setSohoadontu(sohoadontu);   	
    	String sohoadonden= util.antiSQLInspection(request.getParameter("sohoadonden"));
    	nccBean.setSohoadonden(sohoadonden);
		String ngaytao = getDateTime();
		String ngaysua = ngaytao;
		String nguoitao = util.antiSQLInspection(request.getParameter("userId"));
		String nguoisua = nguoitao;
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(util.antiSQLInspection(request.getParameter("trangthai"))!= null)
			trangthai = "1";
		else
			trangthai = "0";
		nccBean.setTrangthai(trangthai);
				
		boolean error = false;
		if (masothue.trim().length()> 0)
			nccBean.setMasothue(masothue);
		else{
			nccBean.setMessage("Vui long nhap vao ma so thue Nha Cung Cap");
			error = true;
		}

		if (diachi.trim().length()> 0)
			nccBean.setDiachi(diachi);
		else{
			nccBean.setMessage("Vui long nhap vao dia chi Nha Cung Cap");
			error = true;
		}

		if (tenviettat.trim().length()> 0)
			nccBean.setTenviettat(tenviettat);
		else{
			nccBean.setMessage("Vui long nhap vao ten viet tat cua Nha Cung Cap");
			error = true;
		}
		
		if (tenncc.trim().length()> 0)
			nccBean.setTen(tenncc);
		else{
			nccBean.setMessage("Vui long nhap vao ten Nha Cung Cap");
			error = true;
			out.println("Vui long nhap vao ten Nha Cung Cap");
		}
		HttpSession session = request.getSession();
		session.setAttribute("userId", nguoitao);
		if (error){
    		session.setAttribute("nccBean", nccBean);   		
    		String nextJSP = "/TraphacoHYERP/pages/Center/NhaCungCapNew.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// Prepare for saving the new distributor
			nccBean.setNgaytao(ngaytao);
			nccBean.setNgaysua(ngaysua);
			nccBean.setNguoitao(nguoitao);
			nccBean.setNguoisua(nguoisua);
			nccBean.setTrangthai(trangthai);
			
			//if there is any error when saving distributor
			if (!nccBean.saveNewNcc()){
				nccBean.setMessage("Ten nha cung cap da bi trung");
	    		session.setAttribute("nccBean", nccBean);
	    		String nextJSP = "/TraphacoHYERP/pages/Center/NhaCungCapNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
				// Collect all Supplier
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
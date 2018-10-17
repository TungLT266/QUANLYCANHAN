package geso.traphaco.center.servlets.khott;
import geso.traphaco.center.beans.khott.imp.KhoTT;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhoTTNewSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public KhoTTNewSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		  request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    HttpSession session = request.getSession();
			KhoTT khoBean = new KhoTT();	
			
			// Collecting data from KhoNew.jsp
			
			Utility util = new Utility();
			
			String userId = util.antiSQLInspection(request.getParameter("userId"));
			System.out.println("userID: " + userId);
	    	String ten = util.antiSQLInspection(request.getParameter("ten"));
	    	khoBean.setTen(ten);
	    	
	    	String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    	String action = request.getParameter("action");
	    	System.out.println("---ACTION: " + action);
	    	
	    	khoBean.setDiengiai(diengiai);

	    	String ngaytao = getDateTime();
			khoBean.setNgaytao(ngaytao);
			
			String ngaysua = ngaytao;
			khoBean.setNgaysua(ngaysua);
			
			String nguoitao = userId;
			khoBean.setNguoitao(userId);
			
			String nguoisua = nguoitao;
	    	khoBean.setNguoisua(nguoisua);
	    	
	    	String id=util.antiSQLInspection(request.getParameter("id"));
	    	khoBean.setId(id);
	    	String trangthai;
	    	if(util.antiSQLInspection(request.getParameter("trangthai"))!= null)
				trangthai = "1";
			else
				trangthai = "0";
			khoBean.setTrangthai(trangthai);
			
			System.out.println("---ACTION: " + action);
			
			boolean error = false;
			if (ten.trim().length()> 0)
				khoBean.setTen(ten);
			else{
				khoBean.setMessage("Vui long nhap vao Ten kho");
				error = true;
			}
			session.setAttribute("userId", nguoitao);
			if (error){ //error in data entry
				session.setAttribute("obj", khoBean);
	    		session.setAttribute("userId", userId);
	    		String nextJSP;
	    		if(action.equals("new")){
	    		 nextJSP = "/TraphacoHYERP/pages/Center/KhoTTNew.jsp";
	    		}else{
	    			nextJSP = "/TraphacoHYERP/pages/Center/KhoTTUpdate.jsp";
	    		}
	    		response.sendRedirect(nextJSP);
			 }
			else if(action.equals("new")) {
				if (!khoBean.saveNewKho()){			
					session.setAttribute("obj", khoBean);
		    		session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Center/KhoTTNew.jsp";
		    		response.sendRedirect(nextJSP);
				}else{// luu duoc
					
					khoBean.setListkho("");
					   khoBean.setTen("");//
			    	//Data object is saved into session
			    	session.setAttribute("obj", khoBean);
			    	// userId is saved into session
			    	session.setAttribute("userId", userId);
			    	String nextJSP = "/TraphacoHYERP/pages/Center/KhoTT.jsp";
			    	response.sendRedirect(nextJSP);
				}
					
			}else if(action.equals("update")){
				if (!khoBean.UpdateKho()){	
					
					session.setAttribute("obj", khoBean);
		    		session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Center/KhoTTUpdate.jsp";
		    		response.sendRedirect(nextJSP);
				}else{// luu duoc
					   khoBean.setTen("");//
					khoBean.setListkho("");
					session.setAttribute("obj", khoBean);
		    		session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Center/KhoTT.jsp";
		    		response.sendRedirect(nextJSP);
				}
			}
	}
}

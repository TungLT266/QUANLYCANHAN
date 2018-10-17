package geso.traphaco.center.servlets.nhanhang;

import geso.traphaco.center.beans.nhanhang.INhanhang;
import geso.traphaco.center.beans.nhanhang.INhanhangList;
import geso.traphaco.center.beans.nhanhang.imp.NhanhangList;
import geso.traphaco.center.beans.nhanhang.imp.Nhanhang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhanhangUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;

	public NhanhangUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    dbutils db ;
	    Utility util;
	    INhanhangList obj;
	    
	    db = new dbutils();
	    util = new Utility();
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String nhId = util.getId(querystring).split(";")[0];
	    out.println(nhId);
		
	    String dvkdId = util.getId(querystring).split(";")[1];
	    out.println(dvkdId);

	    //dbutils db = new dbutils();
		String query =  "select ten, trangthai from nhanhang where pk_seq = '"+ nhId +"' and dvkd_fk='" + dvkdId + "'" ;
//		out.println(query);    	
	    String[] param = new String[10];
        ResultSet rs =  db.get(query);
        
        try{
        	rs.next();
			param[0]= nhId;
			param[1]= rs.getString("ten");
			param[2]= "";	
			param[3]= "";
			param[4]= "";
			param[5]= "";
			param[6]= rs.getString("trangthai");
			param[7]= "";
			param[8]= dvkdId;
    	    INhanhang nhBean = new Nhanhang(param);

			// Data is saved into session
			session.setAttribute("nhanhangBean", nhBean);
			session.setAttribute("userId", userId);

			String nextJSP = "/TraphacoHYERP/pages/Center/NhanHangUpdate.jsp";
       		response.sendRedirect(nextJSP);

        }catch(Exception e){
	    	out.println("error here, Sir!");
	    }

	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		dbutils db ;
	    Utility util;
	    INhanhangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		db = new dbutils();
		obj = new NhanhangList();
//		PrintWriter out = response.getWriter();
		INhanhang nhBean = new Nhanhang();	
		
		// Collecting data from DonViKinhDoanhUpdate.jsp
		
		util = new Utility();
		
    	String nhId = util.antiSQLInspection(request.getParameter("nhId"));
    	nhBean.setId(nhId);

		String nhanhang = util.antiSQLInspection(request.getParameter("nhanhang"));
		nhBean.setNhanhang(nhanhang);

    	String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
    	nhBean.setDvkdId(dvkdId);
		
    	String ngaysua = getDateTime();
    	nhBean.setNgaysua(ngaysua);
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		nhBean.setNguoisua(userId);
    	
    	String trangthai;
    	if(util.antiSQLInspection(request.getParameter("trangthai"))!= null)
			trangthai = "1";
		else
			trangthai = "0";
    	nhBean.setTrangthai(trangthai);
		
		boolean error = false;
		if (nhanhang.trim().length()> 0)
			nhBean.setNhanhang(nhanhang);
		else{
			nhBean.setMessage("Vui lòng nhập vào Tên kho");
			error = true;
		}

	
		if (error){ //Error in data entry
			session.setAttribute("userId", userId);
			session.setAttribute("nhBean", nhBean);   		
    		String nextJSP = "/TraphacoHYERP/pages/Center/NhanHangUpdate.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", userId);
		
			//if there is any error when saving Business Unit

			if (!nhBean.UpdateNhanhang()){
				session.setAttribute("nhBean", nhBean);
				String nextJSP = "/TraphacoHYERP/pages/Center/NhanHangUpdate.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
			    // Collect all of Brands, each Brands is saved into Nhanhang
			    String query = "select a.pk_seq, a.ten, b.pk_seq as dvkdId, b.donvikinhdoanh, a.trangthai, a.ngaytao, a.ngaysua, c.ten as nguoitao, d.ten as nguoisua from nhanhang a, donvikinhdoanh b, nhanvien c, nhanvien d where a.dvkd_fk = b.pk_seq and a.nguoitao = c.PK_SEQ and a.nguoisua = d.PK_SEQ  ";
			    //dbutils db = new dbutils();
			    ResultSet nhlist = db.get(query);
			    
				// Save data into session
				obj.setNhlist(nhlist);
			    session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
					
				String nextJSP = "/TraphacoHYERP/pages/Center/NhanHang.jsp";
				response.sendRedirect(nextJSP);
							
		}
			
		}
		
	}   	  	 
	private List<INhanhang> getNhBeanList(String query){
		
		dbutils db ;
	    Utility util;
	    INhanhangList obj;
		
		db = new dbutils();
		ResultSet rs =  db.get(query);
		List<INhanhang> nhanhanglist = new ArrayList<INhanhang>();
		INhanhang nhanhangBean;
		String[] param = new String[10];
		if (!(rs==null)){
		try{
			while(rs.next()){
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("ten");
				param[2]= rs.getDate("ngaytao").toString();
				param[3]= rs.getDate("ngaysua").toString();
				param[4]= rs.getString("nguoitao");
				param[5]= rs.getString("nguoisua");			
				param[6]= rs.getString("trangthai");
				param[7]= rs.getString("donvikinhdoanh");
				param[8]= rs.getString("dvkdId");
				nhanhangBean = new Nhanhang(param);
				nhanhanglist.add(nhanhangBean);
			}
		}catch(Exception e){}
		}
		return nhanhanglist;
	}

	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}

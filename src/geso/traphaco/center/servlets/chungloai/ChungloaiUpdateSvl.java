package geso.traphaco.center.servlets.chungloai;

import geso.traphaco.center.beans.chungloai.IChungloai;
import geso.traphaco.center.beans.chungloai.IChungloaiList;
import geso.traphaco.center.beans.chungloai.imp.Chungloai;
import geso.traphaco.center.beans.chungloai.imp.ChungloaiList;
import geso.traphaco.center.db.sql.dbutils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.traphaco.center.util.Utility;

 public class ChungloaiUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   

   public ChungloaiUpdateSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    IChungloai cloai;
	    String userId;
	    Utility util;
	    HttpSession session = request.getSession();

	    util = new Utility();
	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String id = util.getId(querystring);
	    out.println(id);
	    
	    Update(id, userId, request, response);
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		IChungloai cloaiBean = new Chungloai();	
		Utility util = new Utility();
		// Collecting data from ChungloaiUpdate.jsp
		String id = util.antiSQLInspection(request.getParameter("Id"));
		cloaiBean.setId(id);
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		
    	String cloai = util.antiSQLInspection(request.getParameter("chungloai"));
    	cloaiBean.setChungloai(cloai);
    	
    	String ngaytao = getDateTime();
    	cloaiBean.setNgaytao(ngaytao);
		
		String ngaysua = ngaytao;
		cloaiBean.setNgaysua(ngaysua);
		
		String nguoitao = userId;
		cloaiBean.setNguoitao(userId);
		
		String nguoisua = nguoitao;
		cloaiBean.setNguoisua(nguoisua);
    	
    	String trangthai;
    	if(util.antiSQLInspection(request.getParameter("trangthai"))!= null)
			trangthai = "1";
		else
			trangthai = "0";
    	cloaiBean.setTrangthai(trangthai);
		
		String[] nhanhangSelected = request.getParameterValues("nhId");
		cloaiBean.setNhanhangSelected(nhanhangSelected);
		
		boolean error = false;
		if (nhanhangSelected == null){
			cloaiBean.setMessage("Vui lòng chọn Nhãn hàng");
			error = true;
		}

		if (cloai.trim().length()== 0){
			cloaiBean.setMessage("Vui lòng nhập vào Chủng loai");
			error = true;
		}

		
		HttpSession session = request.getSession();
		session.setAttribute("userId", request.getParameter("userId"));
		
		if (error){ //error in data entry
			cloaiBean.createNhList();
			session.setAttribute("cloaiBean", cloaiBean);

    		session.setAttribute("userId", userId);
    		String nextJSP = "/TraphacoHYERP/pages/Center/ChungLoaiUpdate.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", nguoitao);

			//if there is any error when saving Chungloai
			if (!cloaiBean.UpdateChungloai()){			
				cloaiBean.createNhList();
				session.setAttribute("cloaiBean", cloaiBean);
	    		session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Center/ChungLoaiUpdate.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
			    // Collect all of chungloai, each chungloai is saved into Chungloai
				IChungloaiList obj = new ChungloaiList();
				
			   	ResultSet clList = getChungloaiBeanList("");			   	
			    obj.setClList(clList);	    
					
				// Save data into session
				session.setAttribute("obj", obj);
					
				session.setAttribute("userId", userId);
					
				String nextJSP = "/TraphacoHYERP/pages/Center/ChungLoai.jsp";
				response.sendRedirect(nextJSP);
							
			}
			
		}
		

	}   	  	    
	public void Update(String Id, String userId, HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		dbutils db = new dbutils();
	    String query = "select ten, trangthai from chungloai where pk_seq='" + Id + "'";
	    HttpSession session = request.getSession();
	    String[] param = new String[10];
        ResultSet rs =  db.get(query);
        
        try{
        	rs.next();
			param[0]= Id;
			param[1]= rs.getString("ten");
			param[2]= "";			
			param[3]= "";
			param[4]= "";
			param[5]= userId;
			param[6]= userId;			
			param[7]= rs.getString("trangthai");
			param[8]= "";
			
			
    	    IChungloai cloaiBean = new Chungloai(param);
    	    cloaiBean.createNhList();
			// Data is saved into session
			session.setAttribute("cloaiBean", cloaiBean);			
			session.setAttribute("userId", userId);

			String nextJSP = "/TraphacoHYERP/pages/Center/ChungLoaiUpdate.jsp";
       		response.sendRedirect(nextJSP);
       		
        }catch(Exception e){
	    	
	    }
	
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private ResultSet getChungloaiBeanList(String search){
		ResultSet rs1;
		String query;
		dbutils db = new dbutils();
		if(search.length()==0){
			query = "select a.pk_seq as clId, a.ten as chungloai,a.trangthai, c.pk_seq as nhId, c.ten as nhanhang, a.ngaytao, a.ngaysua, d.ten as nguoitao, e.ten as nguoisua from chungloai a, nhanhang_chungloai b, nhanhang c, nhanvien d, nhanvien e where a.pk_seq = b.cl_fk and b.nh_fk= c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq order by chungloai ";
			rs1 = db.get(query);   					
		}else{
			query = search;
			rs1 = db.get(query);
		}
		
	return rs1;
	}
	
	
}
package geso.traphaco.center.servlets.nhanhang;
	
import geso.traphaco.center.beans.nhanhang.INhanhang;
import geso.traphaco.center.beans.nhanhang.INhanhangList;
import geso.traphaco.center.beans.nhanhang.imp.NhanhangList;
import geso.traphaco.center.beans.nhanhang.imp.Nhanhang;
import geso.traphaco.center.db.sql.dbutils;
import java.io.IOException;
//import java.io.PrintWriter;
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

 public class NhanhangNewSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   public NhanhangNewSvl() {
		super();
	}   	

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		dbutils db = new dbutils();   
		INhanhangList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();
//		PrintWriter out = response.getWriter();
		obj = new NhanhangList();
		INhanhang nhBean = new Nhanhang();	
		
		// Collecting data from NhanhangNew.jsp
		
		String userId = request.getParameter("userId");
    	String nhanhang = request.getParameter("nhanhang");
    	nhBean.setNhanhang(nhanhang);

    	String dvkdId = request.getParameter("dvkdId");
    	nhBean.setDvkdId(dvkdId);

    	String ngaytao = getDateTime();
		nhBean.setNgaytao(ngaytao);
		
		String ngaysua = ngaytao;
		nhBean.setNgaysua(ngaysua);
		
		String nguoitao = userId;
		nhBean.setNguoitao(userId);
		
		String nguoisua = nguoitao;
    	nhBean.setNguoisua(nguoisua);
    	
    	String trangthai;
    	if(request.getParameter("trangthai")!= null)
			trangthai = "1";
		else
			trangthai = "0";
		nhBean.setTrangthai(trangthai);
			
		boolean error = false;
		if (nhanhang.trim().length()> 0)
			nhBean.setNhanhang(nhanhang);
		else{
			nhBean.setMessage("Vui lòng nhập vào Nhãn hàng");
			error = true;
		}
		
		session.setAttribute("userId", request.getParameter("userId"));
		
		if (error){ //error in data entry
			session.setAttribute("nhBean", nhBean);
    		session.setAttribute("userId", userId);
    		String nextJSP = "/TraphacoHYERP/pages/Center/NhanHangNew.jsp";
    		response.sendRedirect(nextJSP);
		}
		else{
			// userId is saved into session
			session.setAttribute("userId", nguoitao);
			//if there is any error when saving a Brand?
			if (!nhBean.saveNewNhanhang()){			
				session.setAttribute("nhBean", nhBean);
	    		session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Center/NhanHangNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else{
			    // Collect all of warehouse, each warehouse is saved into nhanhang
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
		
		dbutils db;   
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
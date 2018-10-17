package geso.traphaco.center.servlets.tinhthanh;

import geso.traphaco.center.beans.tinhthanh.ITinhThanh;
import geso.traphaco.center.beans.tinhthanh.ITinhThanhList;
import geso.traphaco.center.beans.tinhthanh.imp.TinhThanh;
import geso.traphaco.center.beans.tinhthanh.imp.TinhThanhList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TinhThanhSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public TinhThanhSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		ITinhThanhList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new TinhThanhList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String kvId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(kvId);
	    	out.print(kvId);
	    }
	   	
	   
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/TinhThanh.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		ITinhThanhList obj = new TinhThanhList();
		Utility util = new Utility();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	  
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	ITinhThanh kvBean = (ITinhThanh) new TinhThanh("");
	    	kvBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("kvBean", kvBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Center/TinhThanhNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }	else      if(action.equals("view") || action.equals("next") || action.equals("prev")){
			//obj = new DonmuahangList();
			obj.setUserId(userId);
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

			obj.init("");

			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");

			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Center/TinhThanh.jsp";	    	

			response.sendRedirect(nextJSP);

		}    
	    
	    if (action.equals("search")){
	    	String search = getSearchQuery(request,obj);
	    	
	    	//obj = new KhuvucList(search);
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/TinhThanh.jsp");	    	
	    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request,ITinhThanhList obj)
	{
	
		Utility util = new Utility();
		
			/*String ttId = util.antiSQLInspection(request.getParameter("tinhthanh"));
	    	if ( ttId == null)
	    		ttId = "";
	    	obj.setTinhthanhId(ttId);*/
	    	
	    	/*String qhId = util.antiSQLInspection(request.getParameter("quanhuyen"));
	    	if ( qhId == null)
	    		qhId = "";
	    	obj.setQuanhuyenId(qhId);*/
	    	
	    	String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));   	
	    	if (trangthai == null)
	    		trangthai = "";    	
	    	 String ten = request.getParameter("ten");
	  	    obj.setTen(ten);
	  	    
	   if (trangthai.equals("2"))
	  	trangthai = "";
	    	
	    	obj.setTrangthai(trangthai);
	    	
			String query = 
				 " SELECT TT.pk_Seq, TT.Ten AS TENTT, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA, TT.NGAYTAO, TT.NGAYSUA, TT.TRANGTHAI,(select tenqg from quocgia where pk_seq = TT.quocgia_fk) as tenqg " + 
				 " FROM TINHTHANH TT " + 
				 //" INNER JOIN TINHTHANH TT ON TT.PK_SEQ = QH.TinhThanh_FK " + 
				 " INNER JOIN NHANVIEN NT ON NT.PK_SEQ = TT.NGUOITAO " + 
				 " INNER JOIN NHANVIEN NS ON NS.PK_SEQ = TT.NGUOISUA";
		
	    	/*if (ttId.length()>0){
	    		query = query + " and tt.pk_seq ='" + ttId + "'";
	    	}*/
	    	
	    	/*if (qhId.length()>0){
	    		query = query + " and qh.pk_seq ='" + qhId + "'";
	    	}*/
	  
	    	if(trangthai.length() > 0){
	    		query = query + " and tt.trangthai = '" + trangthai + "'";	
	    	}
	    	if(ten.length() > 0){
	    		query = query + " and tt.ten like N'%" + ten + "%'";	
	    	}
	    	System.out.println("cau lenh:"+ query);
	    	return query;
		}	
	
	boolean kiemtra(String sql)
	{dbutils db =new dbutils();
    	ResultSet rs = db.get(sql);
		try {//kiem tra ben san pham
		while(rs.next())
		{ if(rs.getString("num").equals("0"))
		   return true;
		}
		} catch(Exception e) {
		
			e.printStackTrace();
		}
		 return false;
	}

	private void Delete(String id)
	{	
		
		ITinhThanhList obj = new TinhThanhList();
		
		dbutils db = new dbutils();
		String sql ="SELECT COUNT(TINHTHANH_FK) AS NUM FROM KHACHHANG WHERE TINHTHANH_FK = '"+ id +"' ";
		
		if(kiemtra(sql))
		{
			db.update("DELETE FROM TINHTHANH WHERE PK_SEQ = '" + id + "'");
			db.shutDown();
		}
		else
			obj.setMsg("Thông tin tỉnh thành này đã được khai báo trong dữ liệu khách hàng. Không thể xóa !");
		
	}

}

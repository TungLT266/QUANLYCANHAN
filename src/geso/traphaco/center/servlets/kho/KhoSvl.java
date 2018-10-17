package geso.traphaco.center.servlets.kho;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.beans.kho.IKho;
import geso.traphaco.center.beans.kho.imp.Kho;
import geso.traphaco.center.beans.kho.IKhoList;
import geso.traphaco.center.beans.kho.imp.KhoList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
/**
 * Servlet implementation class for Servlet: KhoSvl
 *
 */
 public class KhoSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;

	public KhoSvl() {
		super();
	}   	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	
	    Utility util = new Utility();
	    //HttpServletRequest request;
	    IKhoList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	   obj = new KhoList();	   
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String khoId = util.getId(querystring);
	    out.println(khoId);
	    
	    //Is a Don Vi Kinh Doanh deleted?
	    if (action.equals("delete")){	   		
		   	Delete(khoId);
	    }
	    
	    // Collect all of Business Unit, each Business Unit is saved into Bean Donvikinhdoanh
	    String query = "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from kho a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
    	//IKhoList obj = new KhoList();	    	
    	List<IKho> kholist = getKhoData(query);
    	obj.setKhoList(kholist);
		
    	//Data object is saved into session
    	session.setAttribute("obj", obj);
		
    	// userId is saved into session
    	session.setAttribute("userId", userId);
		
    	String nextJSP = "/TraphacoHYERP/pages/Center/Kho.jsp";
    	response.sendRedirect(nextJSP);

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
	    Utility util = new Utility();
	    //HttpServletRequest request;
	    IKhoList obj;
		
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		HttpSession session = request.getSession();
	    //this.request = request;
	    obj = new KhoList();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if (request.getParameter("action").equals("search")){
	    	String search = getSearchQuery(request,obj);

	    	List<IKho> kholist = getKhoBeanList(search);	    	
	    	obj.setKhoList(kholist);
	    	
    		// Saving data into session
    		session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/Kho.jsp");
	    }
	    
	    // Create a new Business Unit
	    if (request.getParameter("action").equals("new")){
	    	// Empty Bean for distributor
	    	IKho khoBean = new Kho();
	    	
	    	// Save Data into session
    		session.setAttribute("khoBean", khoBean);
    		session.setAttribute("userId", userId);
    		
	    	String nextJSP = "/TraphacoHYERP/pages/Center/KhoNew.jsp";
    		response.sendRedirect(nextJSP);
	    
	    }
	}

	private void Delete(String khoId){
		
		dbutils db  = new dbutils();
	    IKhoList obj = new KhoList();
	   	
		String command = "select sum(soluong) as num from nhapp_kho where kho_fk='" + khoId + "'";
		System.out.println( command);
		ResultSet rs = db.get(command);
		
		try{
			while( rs.next())
			{
			if (rs.getString("num").equals("0")){
				command = "delete from nhapp_kho where kho_fk='" + khoId + "'";
				System.out.println( command);
				db.update(command);
				command = "delete from kho where pk_seq ='" + khoId + "'";
				System.out.println( command);
				db.update(command);
			}
			else
				obj.setMsg("Trong kho da chua so luong cua san pham roi, nen khong xoa duoc");
			}
		}catch(Exception e){}
		
	}

	private List<IKho> getKhoBeanList(String query){  
		
		dbutils db  = new dbutils();
	   	
		ResultSet rs =  db.get(query);
		List<IKho> kholist = new ArrayList<IKho>();
		if (rs != null){
			IKho khoBean;
			String[] param = new String[10];
			try{
				while(rs.next())
				{
					param[0]= rs.getString("pk_seq");
					param[1]= rs.getString("ten");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getDate("ngaytao").toString();
					param[4]= rs.getDate("ngaysua").toString();
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("nguoisua");			
					param[7]= rs.getString("trangthai");
					khoBean = new Kho(param);
					kholist.add(khoBean);
				}
			if(rs!=null) rs.close();
	        if(db!=null) db.shutDown();
			}catch(Exception e){
			
			}
		}
		return kholist;
	}

	private String getSearchQuery(ServletRequest request, IKhoList obj ){
		
	    Utility util = new Utility();
	    
		String ten = util.antiSQLInspection(request.getParameter("ten"));
    	if (ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	

    	obj.setDenngay(denngay);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";

    	obj.setTrangthai(trangthai);
    	
    	String query = "select a.pk_seq, a.ten, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from kho a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ ";
    	
    	if (ten.length()>0){
			query = query + " and upper(a.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%')";
    	}
    		
    	
/*    	if (diengiai.length()>0){
    		query = query + " and upper(a.diengiai) like upper('%" + util.replaceAEIOU(diengiai) + "%')";	    		
    	}*/
    	
    	if (tungay.length() > 0) {
    		query = query + " and a.ngaytao >= '" + tungay + "'";
    	}
    	
    	if (denngay.length() > 0) {
    		query = query + " and a.ngaytao =< '" + denngay + "'";
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    	}
    	System.out.print(query);
		return query;
	}
	
	private List<IKho> getKhoData(String query){
		
		dbutils db  = new dbutils();
		List<IKho> kholist = new ArrayList<IKho>();  		
		String[] param = new String[10];
        ResultSet rs =  db.get(query);
        try{
        	while(rs.next()){        	
        		param[0]= rs.getString("pk_seq");
        		param[1]= rs.getString("ten");
        		param[2]= rs.getString("diengiai");        		
        		param[3]= rs.getString("ngaytao").toString();
        		param[4]= rs.getString("ngaysua").toString();
        		param[5]= rs.getString("nguoitao");
        		param[6]= rs.getString("nguoisua");
        		param[7]= rs.getString("trangthai");
    			IKho kho = new Kho(param);
    			kholist.add(kho);    			
        	}
        	if(rs!=null) rs.close();
        	if(db!=null) db.shutDown();
        	
       }catch(Exception e){}
       return kholist;
	}
	
}
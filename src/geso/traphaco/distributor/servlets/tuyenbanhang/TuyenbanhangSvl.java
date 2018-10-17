package geso.traphaco.distributor.servlets.tuyenbanhang;

import geso.traphaco.distributor.beans.tuyenbanhang.*;
import geso.traphaco.distributor.beans.tuyenbanhang.imp.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TuyenbanhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
   
    public TuyenbanhangSvl()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		ITuyenbanhangList obj;
		PrintWriter out; 

		ITuyenbanhang tbhBean;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();
  

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String tbhId = util.getId(querystring);

	    if (action.equals("delete")){	
	    	System.out.println("Vao day");
	    	Delete(tbhId, userId);
	    	out.print(tbhId);
	    }
	   	    
	    obj = new TuyenbanhangList();

	    obj.setUserId(userId);	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHang.jsp";
		response.sendRedirect(nextJSP);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{ 
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		ITuyenbanhangList obj  = new TuyenbanhangList();
		PrintWriter out; 

		ITuyenbanhang tbhBean;
		
    	request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
		
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj.setUserId(userId);
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	    String nextJSP = "";

	    
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	tbhBean = (ITuyenbanhang) new Tuyenbanhang("");
	    	tbhBean.setUserId(userId);
		    tbhBean.createRS();
		    
	    	// Save Data into session
	    	session.setAttribute("tbhBean", tbhBean);
    		
    		nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHangNew.jsp";
    		
    		
	    }
	    
	    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	String search = getSearchQuery(request, obj);
	    	
	    	int i = Integer.parseInt(request.getParameter("nxtApprSplitting"));
	    		    	
	    	obj.setNxtApprSplitting(i);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHang.jsp";
	    }


	    else{
	    	    	

	    	    	String search = getSearchQuery(request, obj);	
	    	    	obj.setUserId(userId);
	    	    	obj.init(search);

	    	    	nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHang.jsp";  	
	    	    }
	    if (action.equals("search")){
	    	String search = getSearchQuery(request, obj);
	    	//search = search + " and a.npp_fk='" + userId + "' order by a.ten";
	    	System.out.print("sql: "+search+"\n");
	    	//obj = new KhachhangList(search);
	    	obj.setUserId(userId);
	    	
	    	obj.init(search);
	    	nextJSP = "/TraphacoHYERP/pages/Distributor/TuyenBanHang.jsp";
	    	   		    	
	    }
	    session.setAttribute("obj", obj);  	
		session.setAttribute("userId", userId);
    		
		response.sendRedirect(nextJSP);
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, ITuyenbanhangList obj)
	{		
		Utility util = new Utility();
		String tbhTen = util.antiSQLInspection(request.getParameter("tbhTen"));
    	if ( tbhTen == null)
    		tbhTen = "";
    	obj.setTuyenbh(tbhTen);
    	
    	String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String ddkdId = util.antiSQLInspection(request.getParameter("ddkdTen"));
    	if (ddkdId == null)
    		ddkdId = "";    	
    	obj.setDdkdId(ddkdId);
    	
    	String mafast = util.antiSQLInspection(request.getParameter("mafast"));
    	if (mafast== null)
    		mafast = "";    	
    	obj.setMafast(mafast);
    	
    	String makh= util.antiSQLInspection(request.getParameter("makh"));
    	if (makh == null)
    		makh = "";    	
    	obj.setMakh(makh);
    	
	    	
    	String 	query = "select a.pk_seq as tbhId, a.diengiai as tbhTen, a.ngaytao, a.ngaysua, a.ngaylamviec, b.ten as nguoitao, c.ten as nguoisua,";
		query = query +	" d.pk_seq as ddkdId, d.ten as ddkdTen, e.ten as nppTen, e.pk_seq as nppId,a.NGAYID ";
		query = query + " from tuyenbanhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq inner join daidienkinhdoanh d on a.ddkd_fk = d.pk_seq"; 
	    query = query + " inner join nhaphanphoi e on a.npp_fk = e.pk_seq where a.npp_fk = '" + nppId  + "'";
    	
    	if (tbhTen.length()>0)
    	{
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(tbhTen) + "%')";			
    	}
    	
    	if (ddkdId.length()>0)
    	{
			query = query + " and a.ddkd_fk ='" + ddkdId + "'";			
    	}
    	if(mafast.length()>0){
    		query = query + "and a.PK_SEQ in (select b1.TBH_FK from KHACHHANG_TUYENBH b1 inner join KHACHHANG c1 on b1.KHACHHANG_FK = c1.PK_SEQ where c1.maFAST like '%"+ mafast+  "%')";
    	}
    	
    	if(makh.length()>0){
    		query = query + "and a.PK_SEQ in (select b1.TBH_FK from KHACHHANG_TUYENBH b1 inner join KHACHHANG c1 on b1.KHACHHANG_FK = c1.PK_SEQ where [dbo].[fuConvertToUnsign1](lower(c1.TEN)) like N'%"+ util.replaceAEIOU(makh)  + "%' or c1.PK_SEQ like N'%"+ util.replaceAEIOU(makh)+"%')";
    	}
    	/*
    	if (mafast.length()>0)
    	{
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(tbhTen) + "%')";		
    	}
    	if (makh.length()>0)
    	{
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(tbhTen) + "%')";	
    	}
    	*/
    	 	
    	System.out.println("Search Query: "+query);
    	return query;
	}	
	
	private void Delete(String id, String userId)
	{
		dbutils db = new dbutils();
		
		Utility util=new Utility();
		String nppId =util.getIdNhapp(userId);
		
		String query = "select count(*) as num from khachhang_tuyenBH where tbh_fk='" + id + "'";
		System.out.println("Num : "+query);
		ResultSet rs = db.get(query);
		int a = 0;
		try
		{
			if(rs.next())
			{
				a = rs.getInt("num");
				
			}rs.close();
		} 
		catch(Exception e) {System.out.println("Loi : "+e.toString());}
		
		if(a > 0)
		{
			//Xoa Cac Bang Con Truoc 
			String sql = "delete from khachhang_tuyenBH where tbh_fk='" + id + "'";
			System.out.println("Xoa bang con 1 : "+sql);
			db.update(sql);
			
			//Xoa Bang Cha
			sql = "delete from tuyenbanhang where pk_seq = '" + id + "' and npp_fk='" + nppId + "'";
			System.out.println("Xoa bang cha : "+sql);
			db.update(sql);
			
			db.shutDown();
		}
		
	}

}

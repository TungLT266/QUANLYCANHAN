package geso.traphaco.distributor.servlets.nhanviengiaonhan;

import geso.traphaco.distributor.beans.nhanviengiaonhan.INhanviengiaonhan;
import geso.traphaco.distributor.beans.nhanviengiaonhan.INhanviengiaonhanList;
import geso.traphaco.distributor.beans.nhanviengiaonhan.imp.Nhanviengiaonhan;
import geso.traphaco.distributor.beans.nhanviengiaonhan.imp.NhanviengiaonhanList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NhanviengiaonhanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	
    public NhanviengiaonhanSvl() 
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
		
		INhanviengiaonhanList obj;
		PrintWriter out; 
		
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
	    
	    String nvgnId = util.getId(querystring);

	    if (action.equals("delete")){	   		  	    	
	    	Delete(nvgnId);
	    	out.print(nvgnId);
	    }
	   	    
	    obj = new NhanviengiaonhanList();
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/NhanVienGiaoNhan.jsp";
		response.sendRedirect(nextJSP);
		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		INhanviengiaonhanList obj  = new NhanviengiaonhanList();
		PrintWriter out; 
		String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    Utility util = new Utility();
		HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	INhanviengiaonhan nvgnBean = (INhanviengiaonhan) new Nhanviengiaonhan("");
	    	nvgnBean.setUserId(userId);
	    	nvgnBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("nvgnBean", nvgnBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/NhanVienGiaoNhanNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else
	    {
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/NhanVienGiaoNhan.jsp");	    		    	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, INhanviengiaonhanList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String nvgnTen = util.antiSQLInspection(request.getParameter("nvgnTen"));
    	if ( nvgnTen == null)
    		nvgnTen = "";
    	obj.setTennv(nvgnTen);
    	
    	String ddkdId = util.antiSQLInspection(request.getParameter("ddkdTen"));
    	if (ddkdId == null)
    		ddkdId = "";    	
    	obj.setDdkdId(ddkdId);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 	
    	if (trangthai == null)
    		trangthai = "";    		
    	if (trangthai.equals("2"))
    		trangthai = "";   	
    	obj.setTrangthai(trangthai);
    	    	
    	String query =  "select a.pk_seq as nvgnId, a.ten as nvgnTen, a.trangthai, a.diachi, a.npp_fk as nppId, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, a.dienthoai ";
		query = query + "from nhanviengiaonhan a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq where a.npp_fk='" + nppId + "' ";
    	if (nvgnTen.length() > 0)
    	{ 
			query = query + " and  upper(dbo.ftBoDau(a.ten)) like  upper(N'%" + util.replaceAEIOU(nvgnTen) + "%')";		
    	}
    	
    	if (ddkdId.length() > 0)
    	{
			query = query + " and a.ddkd_fk = '" + ddkdId + "'";		
    	}
    	
    	if (trangthai.length() > 0)
    	{
    		query = query + " and a.trangthai='" + trangthai + "'";
    	}
    	  	
    	query+=" order by a.pk_seq";
    	System.out.println("\nQuery search fina: "+query);		
    	
    	
    	return query;
	}

	private void Delete(String nvgnId) 
	{
		dbutils db = new dbutils(); 
		
		db.update("delete from NVGN__KH where nvgn_fk='" + nvgnId + "'");
		db.update("delete from nhanviengiaonhan where pk_seq='" + nvgnId + "'");
		
		db.update("commit");
		db.shutDown();
	}

}

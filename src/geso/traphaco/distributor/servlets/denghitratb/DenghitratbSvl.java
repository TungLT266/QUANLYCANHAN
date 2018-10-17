package geso.traphaco.distributor.servlets.denghitratb;

import geso.traphaco.distributor.beans.denghitratb.IDenghitratb;
import geso.traphaco.distributor.beans.denghitratb.IDenghitratbList;
import geso.traphaco.distributor.beans.denghitratb.imp.Denghitratb;
import geso.traphaco.distributor.beans.denghitratb.imp.DenghitratbList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DenghitratbSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
       
    public DenghitratbSvl() 
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
		IDenghitratbList obj;
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
	    
	    String dnttbId = util.getId(querystring);
	    obj = new DenghitratbList();
	    if (action.equals("delete"))
	    {	   		  	    	
	    	obj.setMsg(Delete(dnttbId));
	    }
	    obj.setUserId(userId);
	    obj.init("");
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/DeNghiTraTrungBay.jsp";
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
		IDenghitratbList obj = new DenghitratbList();
		PrintWriter out; 
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    
		Utility util = new Utility();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	          
	    if (action.equals("Tao moi"))
	    {
	    	// Empty Bean for distributor
	    	IDenghitratb dnttbBean = (IDenghitratb) new Denghitratb("");
	    	dnttbBean.setUserId(userId);
	    	dnttbBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("dnttbBean", dnttbBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/DeNghiTraTbNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }else
	    {
	    	String search = this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DeNghiTraTrungBay.jsp");	    		    	
	    }
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IDenghitratbList obj) 
	{	
		Utility util = new Utility();
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String sophieu = util.antiSQLInspection(request.getParameter("sophieu"));
    	if ( sophieu== null)
    		sophieu = "";
    	obj.setScheme(sophieu);
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if ( tungay== null)
    		tungay = "";
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if ( denngay == null)
    		denngay = "";
    	obj.setDenngay(denngay);
    	
    	String query = "select a.pk_seq as dnttbId, b.pk_seq as cttbId, b.diengiai as cttbTen, a.ngaydenghi, a.ngaysua, a.trangthai, c.ten as nguoitao, d.ten as nguoisua ";
		query = query + "from denghitratrungbay a inner join cttrungbay b on a.cttrungbay_fk = b.pk_seq inner join nhanvien c on a.nguoitao = c.pk_seq inner join nhanvien d on a.nguoisua = d.pk_seq ";
		query = query + "where a.npp_fk = '" + nppId + "'";
		
		if(sophieu.length() > 0)
			query =  query + " and a.pk_seq like '%" + sophieu + "%'";
		if(tungay.length() > 0)
			query = query + " and a.ngaydenghi >= '" + convertDate(tungay) + "'";
		if(denngay.length() > 0)
			query = query + " and a.ngaydenghi <= '" + convertDate(denngay) + "'";
    	
    	return query + " order by a.ngaydenghi DESC";
	}

	private String  Delete(String dnttbId) 
	{
		dbutils db = new dbutils(); 
		try 
		{
			db.getConnection().setAutoCommit(false);
			String sql="Delete from denghitratb_khachhang where denghitratb_fk='" + dnttbId + "'";
			if(!db.update(sql))
			{
				System.out.println(sql);
				db.update("rollback");
				return "Lỗi cập nhật "+sql;
			}
			sql="Delete from denghitratrungbay where pk_seq='" + dnttbId + "'";
			
			if(!db.update(sql))
			{
				System.out.println(sql);
				db.update("rollback");
				return "Lỗi cập nhật "+sql;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			return "Lỗi cập nhật "+e.getMessage();
		}
		return "";
	}
	
	private String convertDate(String date) 
	{		
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}
	

}

package geso.traphaco.center.servlets.nhapkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.nhapkho.IErpPhanbodonhang;
import geso.traphaco.center.beans.nhapkho.IErpPhanbodonhangList;
import geso.traphaco.center.beans.nhapkho.imp.ErpPhanbodonhang;
import geso.traphaco.center.beans.nhapkho.imp.ErpPhanbodonhangList;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpPhanbodonhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpPhanbodonhangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpPhanbodonhangList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpPhanbodonhangList();
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId);
			obj.setMsg(msg);
	    }
	    else if( action.equals("mochot") )
	    {
	    	String msg = this.MoChot(lsxId);
			obj.setMsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		String msg = this.Chot(lsxId);
    			obj.setMsg(msg);
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHang.jsp";
		response.sendRedirect(nextJSP);
	}

	private String MoChot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			query = "update ERP_PHANBODONHANG set trangthai = '0' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	private String Chot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			query = "update ERP_PHANBODONHANG set trangthai = '1' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			query = "update ERP_PHANBODONHANG set trangthai = '2' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
		IErpPhanbodonhangList obj = new ErpPhanbodonhangList();
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    obj.setUserId(userId);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpPhanbodonhang lsxBean = new ErpPhanbodonhang();
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHang.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHang.jsp";
	    		response.sendRedirect(nextJSP);
	    		
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpPhanbodonhangList obj)
	{
		String query =  "  select a.PK_SEQ, a.trangthai, a.tungay, a.denngay, a.lydo, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua,  " + 
						"  			 (	Select hd.TEN + ', ' AS [text()]   " + 
						"  				From ERP_PHANBODONHANG_DOITUONG YCXK1 inner join NHAPHANPHOI hd on  YCXK1.doituong_fk = hd.pk_seq   " + 
						"  				Where YCXK1.phanbo_fk = a.pk_seq   " + 
						"  				For XML PATH ('') )  as doituong    " + 
						"  from ERP_PHANBODONHANG a    " + 
						"  	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ    " + 
						"  	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
		String tungaySX = request.getParameter("tungay");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngay");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		if(tungaySX.length() > 0)
			query += " and a.tungay >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.denngay <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		System.out.print(query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public static void main(String[] arg)
	{
		ErpPhanbodonhangSvl pb = new ErpPhanbodonhangSvl();
		String text = "NÐ155";
		
		/*System.out.println(":: TEXT: " + pb.toNonUnicode(text) );
		System.out.println(":: TO UNICODE: " + pb.toNonUnicode("Đ") );
		System.out.println(":: TO UNICODE 2: " + pb.toNonUnicode("Ð") );
		
		char ch='Đ';
		int code = ch;
		System.out.println(code);*/
		System.out.println((char)208);
		System.out.println((char)272);
		
		//208
		System.out.println(":: TO UNICODE BEFORE: " + pb.toNonUnicode(text) );
		String kq = pb.XuLyChuoi(text);
		System.out.println(":: TO UNICODE AFTER: " + pb.toNonUnicode(kq) );
	}
	
	public static String toNonUnicode(String inputString)
	{ 
		return Normalizer.normalize(inputString, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").replaceAll("\u0111", "d").
		replaceAll("\u0110", "D");
	}
	
	private static String XuLyChuoi(String chuoi) 
	{
		String kq = "";
	    for( int i = 0; i < chuoi.length(); i++ )
	    {
	    	char c = chuoi.charAt(i);
	    	
	    	int code = c;
	    	if( code == 208 )
	    		code = 272;
	    	
	    	//System.out.println("__ CODE: " + code);
	    	kq += (char)code;
	    }
	    
	    System.out.println("__ KQ: " + kq);
	    return kq;
	}
	
	
}

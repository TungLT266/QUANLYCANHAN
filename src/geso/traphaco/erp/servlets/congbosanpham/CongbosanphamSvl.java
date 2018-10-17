package geso.traphaco.erp.servlets.congbosanpham;

import geso.traphaco.erp.beans.congbosanpham.ICongbosanpham;
import geso.traphaco.erp.beans.congbosanpham.ICongbosanphamList;
import geso.traphaco.erp.beans.congbosanpham.imp.Congbosanpham;
import geso.traphaco.erp.beans.congbosanpham.imp.CongbosanphamList;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CongbosanphamSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public CongbosanphamSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out  = response.getWriter();

		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId"); 
	    String ctyTen = (String)session.getAttribute("congtyTen");
	    out.println("cty id:="+ctyId);
		Utility util = new Utility();
		out = response.getWriter();

		ICongbosanphamList obj = new CongbosanphamList();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		obj.setCtyId(ctyId);
		obj.setCtyTen(ctyTen);
	    
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj.setUserId(userId);

		String action = util.getAction(querystring);
		out.println(action);

		String congbosanphamId = util.getId(querystring);

		obj.setRequestObj(request);
		if (action.equals("delete"))
		{
			String msg = Delete(congbosanphamId);
			if (msg.length() > 0)
			obj.setMsg(msg);
		}
		String msg = "";
		obj.setMsg(msg);

		obj.init("");
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Congbosanpham.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId"); 
	    String ctyTen = (String)session.getAttribute("congtyTen");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();
		this.out = response.getWriter();
		Utility util = new Utility();

		ICongbosanphamList obj = new CongbosanphamList();
		obj.setCtyId(ctyId);
		obj.setCtyTen(ctyTen);

		String userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = request.getParameter("action");
		if (action == null) action = "";

		String search = getSearchQuery(request, obj);
		obj.setRequestObj(request);
		if(action.equals("new"))
		{
			ICongbosanpham cbspnew = new Congbosanpham();
			//cbspnew.createCongbosanpham();
			session.setAttribute("obj", cbspnew);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/CongbosanphamNew.jsp");
		}
		else if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setUserId(userId);
			obj.init(search);
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Congbosanpham.jsp");
		}
		else
		{
			obj.init(search);
			obj.setUserId(userId);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			session.setAttribute("abc", search);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Congbosanpham.jsp");
		}
	}

	private String getSearchQuery(HttpServletRequest request, ICongbosanphamList obj)
	{
		Utility util = new Utility();
		
		String thoihan = request.getParameter("thoihan");
    	if (thoihan == null)
    		thoihan = "";
    	obj.setThoihan(thoihan);
    	
    	String sanpham = request.getParameter("sanpham");
    	if (sanpham == null)
    		sanpham = "";
    	obj.setSanPham(sanpham);
    	
    	System.out.println("thoihan: "+thoihan);
    	String query = "";
    	
    	query = "select ROW_NUMBER() OVER(ORDER BY sn.hancongbo ASC) AS 'stt', sn.pk_seq as id, sp.ma as masanpham, sp.TEN as sanpham, " +
    			" sp.loaisanpham_fk as loaisanpham, isnull(ncc.ma,ct.ma) as manhacungcap, isnull(ncc.TEN,ct.TEN) as nhacungcap, sn.hancongbo, sn.filename, sn.hinhcongbo " + 
    			" from erp_sanpham_nhacungcap sn left join erp_SANPHAM sp on sn.sanpham_fk = sp.PK_SEQ " +
    			" left join ERP_LOAISANPHAM lsp on lsp.PK_SEQ = sp.LOAISANPHAM_FK left join ERP_CONGTY ct on ct.PK_SEQ = sp.CONGTY_FK " +
    			" left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = sn.nhacungcap_fk " + 
    			" where sp.trangthai='1' and sp.congty_fk='" + obj.getCtyId() + "' ";
    			
    	if (thoihan.length()>0)
    	{
	    	int th = Integer.parseInt(thoihan);	    	
			query += " and DATEDIFF(dd, getdate(), sn.hancongbo) <= "+(30*th);		
			
	    }
    	if (sanpham.length()>0)
    	{
	    		
			query += "  and  timkiem like N'%" + util.replaceAEIOU(sanpham) + "%'    ";	
			
	    }
    	
		return query;
	}

	private String convertDate(String date)
	{
		// chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if (!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if (arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

	private String Delete(String congbosanphamId)
	{
		
		dbutils db = new dbutils();
		String query = "select count(*) as num from ";
		try{
		ResultSet rs = db.get(query);

		
		
			rs.next();
			if (!rs.getString("num").equals("0"))
			{
				rs.close();
				return "Congbosanpham nay da duoc su dung";
			}
			rs.close();

			db.update("delete from ");
			db.update("delete from TraphacoERP where pk_seq = '" + congbosanphamId + "'");
			db.update("commit");
			return "";

		} catch (Exception e)
		{
			return "Khong the xoa ";
		}
		finally
		{
		db.shutDown();
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}

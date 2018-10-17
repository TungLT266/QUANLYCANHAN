package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.xuatkho.IErpXuatkhoNpp;
import geso.traphaco.distributor.beans.xuatkho.IErpXuatkhoNppList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpXuatkhoNpp;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpXuatkhoNppList;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpXuatkhoNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpXuatkhoNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpXuatkhoNppList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    
    	String lsxId = util.getId(querystring);
	    obj = new ErpXuatkhoNppList();
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    System.out.println("---LOAI DON HANG: " + loaidonhang);
	    
    	if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId);
    		obj.setMsg(msg);
    	}
    	else if(action.equals("delete"))
    	{
    		String msg = this.Delete(lsxId);
    		obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXuatKhoNpp.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String Delete(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			query = "update ERP_YCXUATKHO set trangthai = '0' where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
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
			
			query = " update kho set kho.SoLuong = kho.SoLuong - CT.tongxuat, " +
							" 			   kho.Booked = kho.Booked - CT.tongxuat " +
							" from " +
							" ( " +
							" 	select a.kho_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat  " +
							" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
							" 	where ycxk_fk = '" + lsxId + "' " +
							" 	group by a.kho_fk, b.solo, b.sanpham_fk " +
							" ) " +
							" CT inner join ERP_KHOTT_SP_CHITIET kho on CT.kho_fk = kho.KHOTT_FK  " +
							" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = " update kho set kho.SoLuong = kho.SoLuong - CT.tongxuat, " +
							" 			   kho.Booked = kho.Booked - CT.tongxuat " +
							" from " +
							" ( " +
							" 	select a.kho_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
							" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
							" 	where ycxk_fk = '" + lsxId + "' " +
							" 	group by a.kho_fk, b.sanpham_fk " +
							" ) " +
							" CT inner join ERP_KHOTT_SANPHAM kho on CT.kho_fk = kho.KHOTT_FK  " +
							" 	and CT.sanpham_fk = kho.SANPHAM_FK  ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "update ERP_YCXUATKHO set trangthai = '1'  where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//CAP NHAT NHUNG DON HANG DA XUAT HET
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
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
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    
	    
		IErpXuatkhoNppList obj = new ErpXuatkhoNppList();
		
		
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    obj.setUserId(userId);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpXuatkhoNpp lsxBean = new ErpXuatkhoNpp();
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXuatKhoNppNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXuatKhoNpp.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
	    		obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXuatKhoNpp.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpXuatkhoNppList obj)
	{
		Utility util = new Utility();
		
		String query =  "select a.PK_SEQ, a.trangthai, a.ngayyeucau, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua,   " +
						"	 (	Select cast(YCXK1.DDH_FK as varchar(10)) + ',' AS [text()]  " +
						"		From ERP_YCXUATKHO_DDH YCXK1   " +
						"		Where YCXK1.ycxk_fk = a.pk_seq  " +
						"		For XML PATH ('') )  as ddhIds    " +
						"from ERP_YCXUATKHO a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 and a.trangthai >= 1  AND A.NPP_FK IN  "+util.quyen_npp(obj.getUserId()) +"   AND A.KHO_FK IN "+util.quyen_khoTT(obj.getUserId())+"   ";
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		if(tungay.length() > 0)
			query += " and a.ngayyeucau >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayyeucau <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		System.out.print(query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}

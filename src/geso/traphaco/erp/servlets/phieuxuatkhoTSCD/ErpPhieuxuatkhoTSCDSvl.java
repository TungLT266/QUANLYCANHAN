package geso.traphaco.erp.servlets.phieuxuatkhoTSCD;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phieuxuatkhoTSCD.*;
import geso.traphaco.erp.beans.phieuxuatkhoTSCD.imp.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpPhieuxuatkhoTSCDSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpPhieuxuatkhoTSCDSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpPhieuxuatkhoTSCDList obj;
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
	    
	    String nkId = util.getId(querystring);
	    
	    obj = new ErpPhieuxuatkhoTSCDList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(nkId);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		String msg = Chot(nkId, userId, obj.getCongtyId());
		    	if(msg.length() > 0)
		    		obj.setMsg(msg);
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCD.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpPhieuxuatkhoTSCDList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpPhieuxuatkhoTSCD pxkBean = new ErpPhieuxuatkhoTSCD();
	    	pxkBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	pxkBean.createRs();
    		
	    	session.setAttribute("pxkBean", pxkBean);
	    	session.setAttribute("ndxId", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCDNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpPhieuxuatkhoTSCDList();
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCD.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpPhieuxuatkhoTSCDList();
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpPhieuXuatKhoTSCD.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpPhieuxuatkhoTSCDList obj)
	{
		
		Utility util=new Utility();
		String  query = " select a.PK_SEQ as pxkId, a.NGAYXUAT, b.MA + ' - ' + b.TEN as ndxTen, " +
		" cast(a.PK_SEQ as varchar(20)) as soxuatkho, " +
		" g.PREFIX + f.PREFIX + cast(a.TRAHANGNCC_fk as varchar(20))   as SOCHUNGTU, " +
		" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua , " +
		" ( select  isnull(ma,'')+' ' +Ten from ERP_NHACUNGCAP where pk_seq =f.nhacungcap_fk) as DoiTuong   "+ 
		" from ERP_XUATTRATSCD a inner join ERP_NOIDUNGNHAP b on a.NOIDUNGXUAT = b.PK_SEQ " +
		"inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ " +
		"left join ERP_MUAHANG f on a.TRAHANGNCC_FK = f.pk_seq " +
		" left join ERP_DONVITHUCHIEN g on f.donvithuchien_fk = g.pk_seq" +
		" left join ERP_NHACUNGCAP ncc on ncc.pk_seq= f.nhacungcap_fk  "+
		" where a.congty_fk = '" + obj.getCongtyId() + "' ";
											
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
		
		String khachhang = request.getParameter("khachhang");
		if(khachhang == null)
			khachhang = "";
		obj.setKhachhang(khachhang);
		
		String sophieu = request.getParameter("sophieu");
		if(sophieu == null)
			sophieu = "";
		obj.setSoPhieu(sophieu);
		
		String dvkdId = request.getParameter("dvkdId");
		if(dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);
		
		String[] spId = request.getParameterValues("spId");
		String[] spMa = request.getParameterValues("spMa");
		String[] spTen = request.getParameterValues("spTen");
		
		String spIds="";
		List<ISanpham> spList= new ArrayList<ISanpham>();
		
		if(spId!=null)
		{
			for(int i=0;i<spId.length;i++)
			{
				if(spId[i].trim().length()>0)
				{
					ISanpham sp = new Sanpham();
					sp.setId(spId[i]);
					sp.setMa(spMa[i]);
					sp.setDiengiai(spTen[i]);
					spList.add(sp);
					spIds+=spId[i]+",";
				}
			}
			if(spIds.length()>0)
			{
				spIds=spIds.substring(0,spIds.length()-1);
			}
		}
		
		obj.setSanPhamId(spIds);
		obj.setSpList(spList);

		if(sophieu.length() > 0) {
			query += " and ( (cast(a.PK_SEQ as varchar(20)) like N'%"+sophieu+"%' )  or ( g.PREFIX + f.PREFIX + cast(a.TRAHANGNCC_fk as varchar(20)) like N'%"+sophieu+"%' ) ) ";
		}
		
		if(tungay.length() > 0)
			query += " and a.NGAYXUAT >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.NGAYXUAT <= '" + denngay + "'";
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(khachhang.trim().length() > 0)
			query += " and   (dbo.ftBoDau(isnull(ncc.ma,'')+' ' +ncc.Ten)) like N'%" + util.replaceAEIOU(khachhang) + "%'  ";
			
		
		if(spIds.length()>0)
		{
			query+=" and a.pk_seq in ( select xuattratscd_fk from erp_xuattratscd_tscd where tscd_fk in ("+spIds+")) ";
		}
		
		
		return query;
	}
	
	private String Delete(String nhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);		
			
			String query = "update ERP_XUATTRATSCD set trangthai = '2' where pk_seq = '" + nhId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa xuất kho: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (SQLException e)
		{ 
			db.shutDown(); 
			return "Khong the xoa xuat kho";
		}
	}
	
	private String Chot(String nhId, String userId, String ctyId)
	{
		IErpPhieuxuatkhoTSCD phieuxuatkho = new ErpPhieuxuatkhoTSCD(nhId);
		phieuxuatkho.setCongtyId(ctyId);
		phieuxuatkho.init();
		
		return phieuxuatkho.chotXuatKho(userId);
		
	}

}

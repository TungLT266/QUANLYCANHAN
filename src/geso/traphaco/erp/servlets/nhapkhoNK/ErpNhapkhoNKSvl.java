package geso.traphaco.erp.servlets.nhapkhoNK;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhapkhoNK.*;
import geso.traphaco.erp.beans.nhapkhoNK.imp.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhapkhoNKSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpNhapkhoNKSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpNhapkhoNKList obj;
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
	    
	    String shId = util.getId(querystring);
	    
	    String msg = "";
	    String ctyId = "";
	    
	    obj = new ErpNhapkhoNKList();
	    ctyId = (String)session.getAttribute("congtyId") ;
	    obj.setCongtyId(ctyId);
	    obj.setUserId(userId);
	    
	    if (action.equals("delete"))
	    {	
	    	msg = Delete(shId);
	    	if(msg.length() > 0)
	    	{
	    		obj.setmsg(msg);
	    	}

	    }
     
	 
	    obj.setmsg(msg);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNK.jsp";
		response.sendRedirect(nextJSP);
	}
	

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhapkhoNKList obj;
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
	    	IErpNhapkhoNK nhBean = new ErpNhapkhoNK();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nhBean.createRs();
    		
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoNKNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpNhapkhoNKList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhapKhoNK.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpNhapkhoNKList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
			
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhapKhoNK.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpNhapkhoNKList obj)
	{
		Utility util=new Utility();
		String query = " SELECT distinct a.PK_SEQ as nkId,ncc.pk_seq as nccId, ncc.Ten as nccTen, a.NGAYNHAP, \n"+
				" c.sopo as PoId,  CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, \n"+
				" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua \n"+
				" FROM ERP_NHAPKHONHAPKHAU a "+
				" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  \n"+
				" inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n"+
				" inner join ERP_MUAHANG c on  a.muahang_fk= c.pk_seq \n"+  
				" inner join ERP_NHACUNGCAP ncc on a.NCC_FK = ncc.pk_seq   \n"+
				" WHERE a.congty_fk = '" + obj.getCongtyId() + "' AND  A.NPP_FK= "+util.getIdNhapp(obj.getUserId())+" ";
		
		 		//" and b.pk_seq in  "+ util.quyen_donvithuchien(obj.getUserId()) ; 
		
		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		
		
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String soPo = request.getParameter("sopo");
		if(soPo == null)
			soPo = "";
		obj.setSoPO(soPo);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sonhapkho = request.getParameter("sonhapkho");
		if(sonhapkho == null)
			sonhapkho = "";
		obj.setSoNK(sonhapkho);
		
		
		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNCC(ncc);
		
		
		if(tungay.length() > 0)
			query += " and a.ngaychungtu >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaychungtu <= '" + denngay + "'";
		
		if(soPo.length() > 0)
		{			
		
			query += "  AND ( c.sopo like '%" + soPo.trim() + "%' ) ";
		}
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(sonhapkho.trim().length() > 0)
		{
			query += " and CAST(a.PK_SEQ as varchar(20)) like N'%" + sonhapkho + "%'  ";
		}
		if(ncc.trim().length() > 0)
		{
			query += " and  (ncc.ma like N'%" + ncc + "%' or ncc.ten like N'%" + ncc + "%' ) " ;					
		}

		
		//query += " order by a.NGAYNHAN desc, a.trangthai asc, a.pk_seq desc;";

		System.out.println(query);
		return query;
	}
	
	private String Delete(String nhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
						
			query = "update ERP_NHAPKHONHAPKHAU set trangthai = '2' where pk_seq = '" + nhId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể cập nhật ERP_NHAPKHONHAPKHAU: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 
			db.shutDown(); 
			return "Không thể xóa phiếu Nhập kho này"; 
		}
		
	}
	

	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String getTime()
	{
		Date date = new Date();
	    SimpleDateFormat simpDate;

	    //format 24h
	    simpDate = new SimpleDateFormat("kk:mm:ss");
	    
	    return simpDate.format(date);
	}
 
}

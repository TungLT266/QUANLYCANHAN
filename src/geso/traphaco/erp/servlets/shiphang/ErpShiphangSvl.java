package geso.traphaco.erp.servlets.shiphang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhapkhoNK.IErpNhapkhoNK;
import geso.traphaco.erp.beans.nhapkhoNK.imp.ErpNhapkhoNK;
import geso.traphaco.erp.beans.shiphang.IErpShiphangList;
import geso.traphaco.erp.beans.shiphang.IErpShiphang;
import geso.traphaco.erp.beans.shiphang.imp.ErpShiphangList;
import geso.traphaco.erp.beans.shiphang.imp.ErpShiphang;

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

public class ErpShiphangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpShiphangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpShiphangList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String loaidh = "0";
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
 
	    String action = util.getAction(querystring);
	    
	    String shId = util.getId(querystring);
	    
	    String msg = "";
	    String ctyId = "";
	    
	    obj = new ErpShiphangList();
	    ctyId = (String)session.getAttribute("congtyId") ;
	    
	    if (action.equals("delete"))
	    {	
	    	msg = Delete(shId);
	    	if(msg.length() > 0)
	    	{
	    		obj.setmsg(msg);
	    	}

	    }
	    else if(action.equals("chot"))
	    {	    	
    		msg = ChotShipHang(shId, userId, ctyId);
    		if(msg.length() > 0)
	    	{
	    		obj.setmsg(msg);
	    	}
		     
	    }
     
	 
	    obj.setmsg(msg);
	    obj.setCongtyId(ctyId);
	    obj.setUserId(userId);
	    obj.setLoaidh(loaidh);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpShipHang.jsp";
		response.sendRedirect(nextJSP);
	}
	

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpShiphangList obj;
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
	    	IErpShiphang nhBean = new ErpShiphang();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nhBean.createRs();
    		
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpShipHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpShiphangList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpShipHang.jsp");	
		    }
	    	else if(action.equals("convertNK"))
	    	{	
	    		String shId = util.antiSQLInspection(request.getParameter("shId"));
	    		IErpNhapkhoNK nhBean = new ErpNhapkhoNK("");
	    		String msg = nhBean.CreateNK(shId);
	    		
	    		if(msg.trim().length() > 0)
	    		{
	    			obj = new ErpShiphangList();
	    			obj.setUserId(userId);
			    	obj.setCongtyId((String)session.getAttribute("congtyId"));
			    	obj.setmsg(msg);
			    	 obj.setLoaidh("0");
			    	obj.init("");
					
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);			
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpShipHang.jsp");
	    		}else
	    		{    			    			    		
		    		nhBean.setCongtyId((String)session.getAttribute("congtyId"));
					nhBean.setUserId(userId); 
		    		nhBean.init();
					session.setAttribute("nhBean", nhBean);	
					response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhapKhoNKUpdate.jsp");
	    		}
	    			    		
	    	}
	    	else
	    	{
		    	obj = new ErpShiphangList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
			
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpShipHang.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpShiphangList obj)
	{
		Utility util=new Utility();
		String query = " SELECT distinct a.PK_SEQ as shId, ncc.Ten as nccTen, a.NGAYCHUNGTU, b.TEN as dvthTen, \n"+
				" c.sopo as PoId,  CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, \n"+
				" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, ISNULL(a.ISHOANTAT,0) ISHOANTAT \n"+
				" FROM ERP_SHIPHANG a "+
				" inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ \n"+
				" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  \n"+
				" inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n"+
				" inner join ERP_MUAHANG c on  a.muahang_fk= c.pk_seq \n"+  
				" inner join ERP_NHACUNGCAP ncc on a.NCC_FK = ncc.pk_seq   \n"+
				" WHERE c.LOAI = '0' and a.congty_fk = '" + obj.getCongtyId() + "' ";
		
		 		//" and b.pk_seq in  "+ util.quyen_donvithuchien(obj.getUserId()) ; 
		
		
		
		
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String dvthId = request.getParameter("dvth");
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);
		
		String soPo = request.getParameter("sopo");
		if(soPo == null)
			soPo = "";
		obj.setSoPO(soPo);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String soShiphang = request.getParameter("soshiphang");
		if(soShiphang == null)
			soShiphang = "";
		obj.setSoShiphang(soShiphang);
		
		
		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNCC(ncc);
		
		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		
		if(tungay.length() > 0)
			query += " and a.ngaychungtu >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaychungtu <= '" + denngay + "'";
		
		if(dvthId.length() > 0)
			query += " and b.pk_seq = '" + dvthId + "'";
		
		if(soPo.length() > 0)
		{
			//query += " and ( CAST(c.PK_SEQ as varchar(10)) + '/' + SUBSTRING(c.NGAYMUA, 6, 2) + '/' + SUBSTRING(c.NGAYMUA, 0, 5) like '%" + soPo + "%' ) ";
		
			query += "  AND ( c.sopo like '%" + soPo.trim() + "%' ) ";
		}
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(soShiphang.trim().length() > 0)
		{
			query += " and CAST(a.PK_SEQ as varchar(20)) like N'%" + soShiphang + "%'  ";
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
						
			query = "update ERP_SHIPHANG set trangthai = '2' where pk_seq = '" + nhId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể cập nhật ERP_SHIPHANG: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 
			db.shutDown(); 
			return "Không thể xóa phiếu Ship hàng"; 
		}
		
	}
	
	
	private String ChotShipHang(String shId, String userId, String ctyId) 
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_MUAHANG set trangthai = 2 where PK_SEQ in (select MUAHANG_FK from ERP_SHIPHANG where PK_SEQ = '" + shId + "')";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.shutDown();
				return "Không thể chôt nhận hàng: " + query;
			}
			
			query = "Update ERP_SHIPHANG set trangthai = '1', ngaychot = '" + getDateTime() + "' where pk_seq = '" + shId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.shutDown();
				return "Không thể chôt nhận hàng: " + query;
			}
							
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {
				e1.printStackTrace();
			}
			
			db.shutDown();
			
			return "Không thể chôt ship hàng: " + e.getMessage();
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

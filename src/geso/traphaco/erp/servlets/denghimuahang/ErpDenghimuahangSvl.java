package geso.traphaco.erp.servlets.denghimuahang;

import geso.traphaco.erp.beans.denghimuahang.*;
import geso.traphaco.erp.beans.denghimuahang.imp.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDenghimuahangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDenghimuahangSvl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDenghimuahangList obj;
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
	    
	    String dmhId = util.getId(querystring);
	    
	    String msg = "";
	    if (action.equals("delete"))
	    {	
	    	  	obj = new ErpDenghimuahangList();
	    	    obj.setUserId(userId);
	    	    obj.setCongtyId((String)session.getAttribute("congtyId"));
//	    	    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);    
//	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);

	    		msg = Delete(dmhId,userId);

	    	    if(msg.length() > 0)
	    	    	 obj.setmsg(msg);
	    	    obj.init("");
	    	   
	    	   
	    		session.setAttribute("obj", obj);
	    				
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiMuaHangList.jsp";
	    		response.sendRedirect(nextJSP); 	 	
	    	
	    	
	    }
	    else if(action.equals("chot"))
		    	{
		    		dbutils db = new dbutils();
		    		   obj = new ErpDenghimuahangList();
		    		if(db.updateReturnInt(" update ERP_denghiMUAHANG set DACHOT = '1' where pk_seq = '" + dmhId + "' and TRANGTHAI=0 and DACHOT=0")!=1)
		    		{
		    			
		    			obj.setmsg("Chốt không thành công, Vui lòng kiểm tra lại ! ");
		    		}

		    	    obj.setUserId(userId);
		    	    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	    
		    	    
		    	    
//		    	    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
//		    		GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		    	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	    if(msg.length() > 0) obj.setmsg(msg);
		    		obj.init("");
		    		session.setAttribute("obj", obj);
		    		db.shutDown();	
		    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiMuaHangList.jsp";
		    		response.sendRedirect(nextJSP);
		    		
		    		
		    		return;
		    	}
	    		
	    		else if(action.equals("unchot"))
		    	{
		    		dbutils db = new dbutils();
		    		 obj = new ErpDenghimuahangList();
		    		if(db.updateReturnInt(("update ERP_denghiMUAHANG set dachot = '0', istruongbpduyet = '0' where pk_seq = '" + dmhId + "' and dachot=1"))!=1)
		    		{
		    			obj.setmsg("Chốt không thành công, Vui lòng kiểm tra lại ! ");
		    		}
		    		
		    		 
			    	    obj.setUserId(userId);
			    	    obj.setCongtyId((String)session.getAttribute("congtyId"));
//			    	    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
//			    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
//			    	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			    	
			    	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			    	    if(msg.length() > 0) obj.setmsg(msg);
			    	    obj.init("");
			    	    
			    		session.setAttribute("obj", obj);
			    				
			    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiMuaHangList.jsp";
			    		response.sendRedirect(nextJSP);
			    		
		    		
		    		db.shutDown();
		    		return;
		    	
	    }else
	    {

	    obj = new ErpDenghimuahangList();
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.init("");
	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiMuaHangList.jsp";
		response.sendRedirect(nextJSP);
		return;
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDenghimuahangList obj;
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
	    	IErpDenghimuahang dmhBean = new ErpDenghimuahang();
	    	dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	dmhBean.setUserId(userId);
	    	
	    	dmhBean.createRs();
	    	session.setAttribute("lhhId", "");
	    	session.setAttribute("lspId", "");
	    	session.setAttribute("noibo", "");
	    	session.setAttribute("ctyId", "");
	    	
	    	session.setAttribute("dmhBean", dmhBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDeNghiMuaHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpDenghimuahangList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		this.getSearchQuery(request, obj);
		    	
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
//		    	String querySearch = GiuDieuKienLoc.createParams(obj);
//				util.setSearchToHM(userId, session,this.getServletName(), querySearch);

		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDeNghiMuaHangList.jsp");	
		    	return;
		    }
	    	else
	    	{
		    	obj = new ErpDenghimuahangList();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	this.getSearchQuery(request, obj);
		    	obj.init("");
//		    	String querySearch = GiuDieuKienLoc.createParams(obj);
//				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
				

		    	
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDeNghiMuaHangList.jsp");  
	    		return;
	    	}
	    }
	    
	}

	private void getSearchQuery(HttpServletRequest request, IErpDenghimuahangList obj)
	{
		Utility util = new Utility();
		
		geso.traphaco.center.db.sql.dbutils db = new geso.traphaco.center.db.sql.dbutils();
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String dvthId = util.antiSQLInspection(request.getParameter("dvth"));
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);
		
		String ncc = util.antiSQLInspection(request.getParameter("ncc"));
		if(ncc == null) 
			ncc = "";
		obj.setNCC(ncc);
		
		String tongtien = util.antiSQLInspection(request.getParameter("tongtien"));
		if(tongtien == null)
			tongtien = "";
		obj.setTongtiensauVat(tongtien);
		
		String soItem = util.antiSQLInspection(request.getParameter("soItems"));
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		
		String sodonmuahang = util.antiSQLInspection(request.getParameter("sodonmuahang"));
		if(sodonmuahang == null)
			sodonmuahang = "";
		obj.setSodonmuahang(sodonmuahang);
		
		String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
		if(loaihh == null)
			loaihh = "";
		obj.setLoaihanghoa(loaihh);
		
		String loaisanpham = util.antiSQLInspection(request.getParameter("loaisanpham"));
		if(loaisanpham == null)
			loaisanpham = "";
		obj.setLoaisanphamid(loaisanpham);
		
		String mactsp = util.antiSQLInspection(request.getParameter("mactsp"));
		if(mactsp == null)
			mactsp = "";
		obj.setMaCtSp(mactsp);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nguoitao = util.antiSQLInspection(request.getParameter("nguoitao"));
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitaoIds(nguoitao);

		
		String sothamchieu = util.antiSQLInspection(request.getParameter("sothamchieu"));
		if(sothamchieu == null)
			sothamchieu = "";
		obj.setsothamchieu(sothamchieu);

//		String query =  " select a.PK_SEQ as dmhId,ISNULL(( select SUM(soluong) from ERP_denghiMUAHANG_SP where denghi_FK = a.PK_SEQ ), 0) AS tongluong, a.NGAYdenghi , a.sodenghi,\n " +
//				" isnull((select TEN from ERP_DONVITHUCHIEN where PK_SEQ = a.DONVITHUCHIEN_FK), '') as ten, \n " +
//				" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, \n " +
//				" ISNULL(tt.ma, 'NA') as tiente, a.dachot, a.ISTRUONGBPDUYET, a.tooltip \n " +
//				" from erp_denghimuahang a  \n " +
//				" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n " +
//				" left join ERP_TIENTE tt on a.tiente_fk = tt.pk_seq  \n " +
//				" where  a.congty_fk = '" + obj.getCongtyId() + "' \n ";
//		
//		
//		if(tungay.length() > 0)
//			query += " and a.ngaydenghi >= '" + tungay + "'";
//		
//		if(denngay.length() > 0)
//			query += " and a.ngaydenghi <= '" + denngay + "'";
//		
//		if(dvthId.length() > 0)
//			query += " and a.DONVITHUCHIEN_FK = '" + dvthId + "'";
//		
//		if(loaihh.length() > 0)
//			query += " and a.LOAIHANGHOA_FK = '" + loaihh + "'";
//		
//		if(loaisanpham.length() > 0)
//			query += " and a.loaisanpham_fk = '" + loaisanpham + "'";
//		
//		if(nguoitao.trim().length() > 0)
//			query += " and a.nguoitao = '" + nguoitao.trim() + "' ";
//			
//		if(sodonmuahang.length() > 0)
//		{
//			query += " and a.sodenghi like '%" + sodonmuahang + "%' ";
//			
//		}
//		
//		String[] t;
//		if(trangthai.trim().length() > 0){
//			
//			t = trangthai.split(";");
//			
//			query += " and a.trangthai = 0 ";
//			
//			if((t[1].trim()).length() > 0){
//				query += "and a.dachot = '" + t[1] + "'";
//			}
//			
//			if((t[0].trim()).length() > 0){
//				query += "and a.ISTRUONGBPDUYET = '" + t[0] + "'";
//			}
//
//		}
//	
//		System.out.println("cau search "+query);
//
//		return query;
	}

	private String Delete(String dmhId,String userId)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
				
			query = "SELECT TRANGTHAI FROM ERP_denghiMUAHANG WHERE PK_SEQ =  '" + dmhId + "'";
			ResultSet rs = db.get(query);
			String tt = "";
			if(rs != null)
			{
				if(rs.next())
				{
					tt = rs.getString("TRANGTHAI");
					rs.close();
				}
			}
			
			if(tt.equals("0")){
				
				query = " DELETE ERP_denghiMUAHANG_SP WHERE denghi_FK = '" + dmhId + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể xóa đề nghị mua hàng này: " + query;
				}
				
				
				query = " DELETE ERP_denghiMUAHANG WHERE PK_SEQ = '" + dmhId + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể xóa đề nghị mua hàng này: " + query;
				}

			}else{
				query = " Update  ERP_denghiMUAHANG set trangthai=3, nguoisua="+userId+"  where pk_seq = '" + dmhId + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Không thể xóa đề nghị mua hàng này: " + query;
				}

			}
			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			db.shutDown(); 
			return "Loi-khong the xoa don mua hang:"+query; 
		}
		
		
	}

	private boolean deletefile(String file)
	{
		System.out.println(file);
		  File f1 = new File(file);
		  boolean success = f1.delete();
		  if (!success)
		  {
			return false;
		  }
		  else
		  {
			 return true;
		   }
	}
}

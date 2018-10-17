package geso.traphaco.erp.servlets.dutoanvattu;

import geso.traphaco.erp.beans.dutoanvattu.IErpDutoanvattu;
import geso.traphaco.erp.beans.dutoanvattu.IErpDutoanvattuList;
import geso.traphaco.erp.beans.dutoanvattu.imp.ErpDutoanvattu;
import geso.traphaco.erp.beans.dutoanvattu.imp.ErpDutoanvattuList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDutoanvattuListSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDutoanvattuListSvl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDutoanvattuList obj;
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
	    
	    String dtId = util.getId(querystring);
	    
	    String msg = "";
	    if (action.equals("delete"))
	    {	
	    	
	    	msg = Delete(dtId,userId);
	    	
	    	  obj = new ErpDutoanvattuList();
	   	    obj.setUserId(userId);
	   	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	   	   
	   	    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
    	    geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
    	    if(msg.length() > 0) obj.setmsg(msg);
	   	    obj.init("");
	   		session.setAttribute("obj", obj);
	   				
	   		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuList.jsp";
	   		response.sendRedirect(nextJSP);
	   		return;
	    	
	    }
	    else if(action.equals("chot"))
		    	{
		    		dbutils db = new dbutils();
		    		String ngaychot = getDateTime();
		    		 obj = new ErpDutoanvattuList();
		    		if(db.updateReturnInt(" update ERP_DUTOANVATTU set DACHOT = '1', ngaychot = '"+ngaychot+"' where pk_seq = '" + dtId + "' and DACHOT<>1")!=1)
		    		{
		    			obj.setmsg("Không thể chốt, Vui lòng kiểm tra lại ");
		    		}
		    	   
		    	    obj.setUserId(userId);
		    	    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	  

		    	    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
		    	    geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		    	    if(msg.length() > 0) obj.setmsg(msg);
		    	    obj.init("");
		    	    
		    		session.setAttribute("obj", obj);
		    				
		    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuList.jsp";
		    		response.sendRedirect(nextJSP);
		    		
		    		db.shutDown();
		    		return;
		    	}else if(action.equals("duyet"))
		    	{
		    		msg = Duyet(dtId,userId);
		    		obj = new ErpDutoanvattuList();
		    	    obj.setUserId(userId);
		    	    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	  

		    	    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
		    	    geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		    	    if(msg.length() > 0) obj.setmsg(msg);
		    	    obj.init("");
		    	    
		    		session.setAttribute("obj", obj);
		    				
		    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuList.jsp";
		    		response.sendRedirect(nextJSP);
		    		return;
		    	}
		    	else if(action.equals("boduyet"))
		    	{
		    		dbutils db = new dbutils();
		    		obj = new ErpDutoanvattuList();
		    		if(db.updateReturnInt(" update ERP_DUTOANVATTU set DACHOT = '0' where pk_seq = '" + dtId + "' and DACHOT<>0")!=1){
		    		obj.setmsg("Không thể bỏ duyệt, Vui lòng kiểm tra lại");
		    		}
		    	    obj.setUserId(userId);
		    	    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	  

		    	    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
		    	    geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		    	    if(msg.length() > 0) obj.setmsg(msg);
		    	    obj.init("");
		    	    
		    		session.setAttribute("obj", obj);
		    				
		    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuList.jsp";
		    		response.sendRedirect(nextJSP);
		    		db.shutDown();
		    		return;
		    	}
	    	
	    
	    else{
	    obj = new ErpDutoanvattuList();
	    obj.setUserId(userId);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    obj.init("");
	    util.setSearchToHM(userId, session, this.getServletName(), "");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuList.jsp";
		response.sendRedirect(nextJSP);
	    }
}
	

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDutoanvattuList obj;
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
	    	IErpDutoanvattu dmhBean = new ErpDutoanvattu();
	    	dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	dmhBean.setUserId(userId);
	    	
	    	dmhBean.createRs(false);
    		
	    	session.setAttribute("lhhId", "");
	    	session.setAttribute("lspId", "");
	    	session.setAttribute("noibo", "");
	    	
	    	session.setAttribute("dmhBean", dmhBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuToanVatTuNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpDutoanvattuList();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		this.getSearchQuery(request, obj);
		    	
		    	obj.init("");
		    	String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
				
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDuToanVatTuList.jsp");	
		    }
	    	else
	    	{
	    		if(action.equals("chuyenPO"))
	    		{
	    			obj = new ErpDutoanvattuList();
			    	obj.setUserId(userId);
			    	obj.setCongtyId((String)session.getAttribute("congtyId"));
			    	
			    	String dtId = (request.getParameter("dtId"));
			    	String nccId = (request.getParameter("nccId"));
			    	String msg = obj.ConvertPO(dtId, nccId);
			    	if(msg.trim().length() > 0){
			    		obj.setmsg(msg);
			    	}
			    
			    	obj.init("");
			    	String querySearch = GiuDieuKienLoc.createParams(obj);
					util.setSearchToHM(userId, session,this.getServletName(), querySearch);
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);			
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDuToanVatTuList.jsp"); 
			    	
	    		}else
	    		{
	    			obj = new ErpDutoanvattuList();
			    	obj.setUserId(userId);
			    	obj.setCongtyId((String)session.getAttribute("congtyId"));
			    	
			    	 this.getSearchQuery(request, obj);
			    	obj.init("");
			    	String querySearch = GiuDieuKienLoc.createParams(obj);
					util.setSearchToHM(userId, session,this.getServletName(), querySearch);
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
			
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDuToanVatTuList.jsp"); 
	    		}
		    	 
	    	}
	    }
	    
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private void getSearchQuery(HttpServletRequest request, IErpDutoanvattuList obj)
	{
		Utility util = new Utility();
		
		geso.traphaco.center.db.sql.dbutils db = new geso.traphaco.center.db.sql.dbutils();
		
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
		
		String madutoan = request.getParameter("madutoan");
		if(madutoan == null)
			madutoan = "";
		obj.setMadutoanvt(madutoan);
		
		String loaihh = request.getParameter("loaihh");
		if(loaihh == null)
			loaihh = "";
		obj.setLoaihanghoa(loaihh);
		
		String loaisanpham = request.getParameter("loaisanpham");
		if(loaisanpham == null)
			loaisanpham = "";
		obj.setLoaisanphamid(loaisanpham);
		
		String mactsp = request.getParameter("mactsp");
		if(mactsp == null)
			mactsp = "";
		obj.setMaCtSp(mactsp);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitaoIds(nguoitao);

		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		
		String madnmh = request.getParameter("madnmh");
		if(madnmh == null)
			madnmh = "";
		obj.setMadnmh(madnmh);

//		String query =  "SELECT DT.PK_SEQ AS DTID, DT.DENGHI_FK, DT.NGAY, DT.TRANGTHAI, NV1.TEN AS NGUOITAO, NV2.TEN AS NGUOISUA, DT.LOAIHANGHOA_FK, "+
//				        "   ISNULL(DT.DACHOT,'') DACHOT, DT.NGAYTAO, DT.NGAYSUA \n"+
//				        "	(SELECT COUNT(*) FROM ERP_CHUCDANH WHERE NHANVIEN_FK = "+ obj.getUserId() +" AND DVTH_FK = DT.DONVITHUCHIEN_FK and trangthai = 1 and DUYETDTVT = 1) DUYET \n"+
//				        "FROM ERP_DUTOANVATTU DT INNER JOIN NHANVIEN NV1 ON DT.NGUOITAO = NV1.PK_SEQ \n"+
//			            "     INNER JOIN NHANVIEN NV2 ON DT.NGUOISUA = NV2.PK_SEQ    \n"+
//						" WHERE DT.CONGTY_FK = "+ obj.getCongtyId() +" AND DT.PK_SEQ > 0 ";
//		
//		query = "SELECT DT.PK_SEQ AS DTID, DT.DENGHI_FK, DT.NGAY, DT.TRANGTHAI, NV1.TEN AS NGUOITAO, NV2.TEN AS NGUOISUA, DT.LOAIHANGHOA_FK, \n"+
//		"   ISNULL(DT.DACHOT,'') DACHOT, DT.NGAYTAO, DT.NGAYSUA, \n"+
//		"	(SELECT COUNT(*) FROM ERP_CHUCDANH WHERE NHANVIEN_FK = "+ obj.getUserId() +" AND DVTH_FK = DT.DONVITHUCHIEN_FK and trangthai = 1 and DUYETDTVT = 1) DUYET \n"+
//		"FROM ERP_DUTOANVATTU DT INNER JOIN NHANVIEN NV1 ON DT.NGUOITAO = NV1.PK_SEQ \n"+
//	    "     INNER JOIN NHANVIEN NV2 ON DT.NGUOISUA = NV2.PK_SEQ    \n"+
//	    " WHERE DT.PK_SEQ > 0 AND DT.CONGTY_FK = "+  obj.getCongtyId() ;
//		
//			
//		if(madnmh.trim().length() > 0)
//			query += " and DT.DENGHI_FK like '%" + madnmh + "%'";
//		
//		if(tungay.length() > 0)
//			query += " and DT.NGAY >= '" + tungay + "'";
//		
//		if(denngay.length() > 0)
//			query += " and DT.NGAY <= '" + denngay + "'";
//		
//		if(dvthId.length() > 0)
//			query += " and dt.DONVITHUCHIEN_FK = '" + dvthId + "'";
//		
//		if(loaihh.length() > 0)
//			query += " and dt.LOAIHANGHOA_FK = '" + loaihh + "'";
//		
//		if(nguoitao.trim().length() > 0)
//			query += " and DT.nguoitao = '" + nguoitao.trim() + "' ";
//		
//		if(loaihh.trim().length()> 0){
//			query += " and dt.LOAIHANGHOA_FK = '" + loaihh + "'";
//		}
//			
//		if(mactsp.trim().length()> 0){
//			query += " and dt.PK_SEQ in ( SELECT DTVT_FK FROM ERP_DUTOANVATTU_SANPHAM WHERE SANPHAM_FK = '"+ mactsp +"' )";
//		}
//			
//		if(madutoan.length() > 0)
//		{
//			query += " and DT.PK_SEQ like '%" + madutoan + "%' ";
//		}
//		
//		if(trangthai.trim().length() > 0)
//		{
//			if(trangthai.equals("-1")){  // Chưa chốt
//				query += " and ISNULL(DT.DACHOT,0) = 0 and dt.trangthai = 0 ";
//			}	
//			else if(trangthai.equals("0")){ // Chưa duyệt
//				query += " and ISNULL(DT.DACHOT,0) = 1  and dt.trangthai = 0 ";
//			}
//			else if(trangthai.equals("1")){  // Đã duyệt
//				query += " and dt.trangthai = 1  ";
//			}
//			else if(trangthai.equals("2")){  // Hoàn tất
//				query += " and dt.trangthai = 2  ";
//			}
//			else if(trangthai.equals("3")){  // Đã xóa
//				query += " and dt.trangthai = 3  ";
//			}	
//		}
//
//		
//		if(mactsp.trim().length() > 0)
//		{
//			query +=" and a.pk_seq in (" +
//					"     select distinct muahang_fk from erp_muahang_sp where sanpham_fk in ( select distinct pk_seq from erp_Sanpham where MA like N'%" + mactsp + "%' ) " +
//					" ) ";
//		}
//		
//		System.out.println("cau search "+query);
//
//		return query;
	}

	private String Delete(String dtId,String userId)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
				
				query = " UPDATE ERP_DUTOANVATTU SET TRANGTHAI = 3 WHERE PK_SEQ = '" + dtId + "' where trangthai=0";
				if(db.updateReturnInt(query)!=1)
				{
					db.getConnection().rollback();
					return "Không thể xóa dự toán vật tư  này: " + query;
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
			return "Loi-khong the xoa du toan nay:"+query; 
		}
		
	}
	
	private String Duyet(String dtId, String userId) 
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
				
				query = " UPDATE ERP_DUTOANVATTU SET TRANGTHAI = 1 WHERE PK_SEQ = '" + dtId + "' and TRANGTHAI=0";
				if(db.updateReturnInt(query)!=1)
				{
					db.getConnection().rollback();
					return "Không thể duyệt dự toán vật tư  này: " + query;
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
			return "Loi-khong the duyet du toan nay:"+query; 
		}
		
	}
}

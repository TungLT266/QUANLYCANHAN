package geso.traphaco.erp.servlets.park;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.park.IErpPark;
import geso.traphaco.erp.beans.park.IErpParkList;
import geso.traphaco.erp.beans.park.imp.ErpPark;
import geso.traphaco.erp.beans.park.imp.ErpParkList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpParkHoadonSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ErpParkHoadonSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpParkList obj;
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
	    
	    String nhId = util.getId(querystring);
	    
	    obj = new ErpParkList();	   
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setUserId(userId);
	    System.out.println("vaoday");
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(nhId);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
		    obj.setUserId(userId);
		    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    obj.setnppdangnhap(util.getIdNhapp(userId));
		    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
		    geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    	obj.init("");  
	 		session.setAttribute("obj", obj);
	 		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDon.jsp";
	 		response.sendRedirect(nextJSP);
	 		return;
	    	
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		dbutils db = new dbutils();
	    		
	    		    		
	    		String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    		GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    	/*	
	    		String msg = obj.checkSoHoaDon(nhId);
	    		
	    		if(msg.length() > 0)
		    		obj.setMsg(msg);
	    		else*/
	    			db.update("update ERP_PARK set trangthai = '1', nguoisua ="+userId+"  where pk_seq = '" + nhId + "' and trangthai=0");
	    					
	    	    obj.init("");  
	    	   
	    		session.setAttribute("obj", obj);
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDon.jsp";
	    		response.sendRedirect(nextJSP);
	    		
	    		db.shutDown();
	    		return;
	    	}
	    	else if (action.equals("unchot"))
	    	{
	    		
	    		String msg = obj.Unchot(nhId,userId );
	    		
	    		String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    		GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    		if(msg.length() > 0)
		    		obj.setMsg(msg);	
	    	    obj.init("");  
	    	   
	    		session.setAttribute("obj", obj);
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDon.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
	    		
	    	}
	    }
	    
	    //obj.setUserId(userId);
	   // obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    
	    obj.init(""); 
	    util.setSearchToHM(userId, session,this.getServletName(), "");
	    System.out.println("abc");
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDon.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpParkList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpPark pBean = new ErpPark();
	    	pBean.setCtyId((String)session.getAttribute("congtyId"));
	    	pBean.setUserId(userId);
	    	pBean.setnppdangnhap(util.getIdNhapp(userId));
	    	
	    	pBean.createRs();
	    	session.setAttribute("pBean", pBean);
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpParkList();
		    	this.getSearchQuery(request, obj);
		    	obj.setDuyet(1);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setnppdangnhap(util.getIdNhapp(userId));
		    	
		    	obj.init("");
		    	String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
				
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDon.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpParkList();

		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
				obj.setUserId(userId);
				obj.setnppdangnhap(util.getIdNhapp(userId));
				
		    	this.getSearchQuery(request, obj);
		    	obj.init("");
		      	String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpParkHoaDon.jsp");
	    	}
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpParkList obj)
	{
				
//		String query  =   
//			" SELECT A.PK_SEQ , A.NGAYGHINHAN, NCC.TEN AS TENNHACUNGCAP, A.TRANGTHAI ,     	NV.TEN AS TENNV,A.NGAYSUA,A.NGAYTAO,NV.PK_SEQ AS MANV,NV2.TEN AS \n" +  
//			"  		 TENNVS,NV2.PK_SEQ AS MANVS, A.PREFIX     	, HOANTAT.SODEM  AS DAHOANTAT , CASE WHEN THANHTOAN.PARK_FK IS NULL THEN 0 ELSE 1 END AS DACOTHANHTOAN,  ISNULL(HDNCC.SOHOADON,'') AS SOHOADON, HDNCC.LOAIHD,	 \n" +
//			"		(SELECT COUNT(PK_SEQ) FROM ERP_NHANHANG WHERE HDNCC_FK = HDNCC.PK_SEQ  AND TRANGTHAI NOT IN (3) ) DANHANHANG,  NCC.PK_SEQ NCCID,  HDNCC.PK_SEQ HOADONID, \n"+
//			" 		( SELECT CASE WHEN HDMH1.SANPHAM_FK IS NOT NULL THEN 0 WHEN HDMH1.TAISAN_FK IS NOT NULL THEN 1 WHEN HDMH1.CCDC_FK IS NOT NULL THEN 3 ELSE 2 END LOAIHH \n"+
//			" 		FROM \n"+
//			" 		(SELECT TOP (1) * " +
//			"  		FROM ERP_HOADONNCC_DONMUAHANG HDMH " +
//			"  		WHERE HDMH.HOADONNCC_FK = HDNCC.PK_SEQ ) HDMH1 ) LOAIHH \n"+
//			" FROM   ERP_PARK A   	INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = A.NCC_FK   	INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   \n" +  
//			"  		 INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ	     \n" +  
//			"  		 LEFT JOIN 	(   \n" +  
//			"   				 SELECT A.PARK_FK , (DEMA - ISNULL (DEMB,0)) AS SODEM \n" +
//			"					 FROM 	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMA  	\n" +
//			"							 FROM ERP_HOADONNCC   \n" +  
//			"							 WHERE CONGTY_FK = "+obj.getCongtyId()+
//			"   						 GROUP BY PARK_FK) AS A  	\n" +
//			"		LEFT JOIN   	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMB  	\n" +
//			"						 FROM ERP_HOADONNCC  	 \n" +  
//			"   					 WHERE TRANGTHAI = 2 AND CONGTY_FK = " +obj.getCongtyId()+
//			"						 GROUP BY PARK_FK) AS B ON B.PARK_FK = A.PARK_FK  \n" +  
//			"  						) HOANTAT ON HOANTAT.PARK_FK = A.PK_SEQ   	 \n" +  
//			"  LEFT JOIN 		(  \n" +  
//			"   SELECT 	DISTINCT HD.PARK_FK 		\n"+
//			"   FROM 	ERP_THANHTOANHOADON_HOADON TT		\n"+   
//			"   	 	INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT.HOADON_FK AND TT.LOAIHD = 0 \n"+
//			"   WHERE 	TT.THANHTOANHD_FK IN (SELECT PK_SEQ FROM ERP_THANHTOANHOADON WHERE TRANGTHAI != 2 AND CONGTY_FK = "+obj.getCongtyId()+" )  \n"+
//			"			AND HD.CONGTY_FK = "+obj.getCongtyId()+
//			"  ) THANHTOAN ON THANHTOAN.PARK_FK = A.PK_SEQ \n" +
//			" INNER JOIN ERP_HOADONNCC HDNCC ON A.PK_SEQ=HDNCC.PARK_FK  \n" +
//			" where 1=1  AND A.LOAIHD IN (2) AND  HDNCC.CONGTY_FK ="+obj.getCongtyId();
//				 
		
		String ngayghinhan = request.getParameter("ngayghinhan");
		if(ngayghinhan == null)
			ngayghinhan = "";
		if(obj.getDuyet()==1)
		{
			
			obj.setNgayghinhan(ngayghinhan);
		}
		else {
			obj.setNgayhoadon(ngayghinhan);
		}
		
		
		
		String nccId = request.getParameter("nccId");
		if(nccId == null)
			nccId = "";
		obj.setNccId(nccId);
		
		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNcc(ncc);

		String loaihang = request.getParameter("loaihang");
		if(loaihang == null)
			loaihang = "";
		obj.setLoaihang(loaihang);
		
		String sopark = request.getParameter("sopark");
		if(sopark == null)
			sopark = "";
		obj.setId(sopark);
		
		String trangthai = request.getParameter("trangthai");
		
		System.out.println("trangthai" +trangthai);
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSOHOADON(sohoadon);
		
		String sonhanhang = request.getParameter("sonhanhang");
		if(sonhanhang == null)
			sonhanhang = "";
		obj.setSonhanhang(sonhanhang);
		System.out.println("Số nhận hàng: "+sonhanhang);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNGUOITAO(nguoitao);
		
//		if(trangthai.length() > 0){
//			if(trangthai.equals("0") || trangthai.equals("1") ){
//			query += " and a.trangthai = '" + trangthai + "'";
//			}
//			else if(trangthai.equals("2")) 
//			{
//				query += " and a.trangthai = '" + trangthai + "' and hoantat.sodem != 0  and thanhtoan.park_fk is null";
//			}
//			else if(trangthai.equals("3")) 
//			{
//				query += " and a.trangthai = 2 and hoantat.sodem != 0  and thanhtoan.park_fk is not null";
//			}
//			else if(trangthai.equals("4")) 
//			{
//				query += " and ( (a.trangthai = 2 and hoantat.sodem = 0 ) or a.trangthai = 3 )";
//			}
//			else if(trangthai.equals("5")){
//				query += " and a.trangthai = 4 ";
//			}
//		}
//		
//		if(ngayghinhan.length() > 0)
//			query += " and a.ngayghinhan = '" + ngayghinhan + "'";
//		
//		if(nccId.length() > 0)
//			query += " and a.ncc_fk = '" + nccId + "'";
//		
//		if(loaihang.length() > 0)
//			query += " and a.loaihanghoa_fk = '" + loaihang + "'";
//		if(sopark.length() > 0)
//			query += " and cast(a.PREFIX as varchar(10))+ cast(a.pk_seq as varchar(10))  like '%" + sopark + "%'";
//		
//		
//		if(sohoadon.length() > 0)
//			query += " and (a.pk_seq in (select park_fk from ERP_HOADONNCC  where sohoadon like N'%"+ sohoadon +"%'))";
//		
//		
//		if(nguoitao.length() > 0)
//			query += " and NV.TEN like N'%" + nguoitao + "%'";
//		
//		if(sonhanhang.length() > 0)
//		{
//			query += "  and  HDNCC.pk_seq in ( select a.pk_seq from ERP_HOADONNCC a inner join ERP_HOADONNCC_DONMUAHANG  b on b.hoadonncc_fk= a.pk_seq" +
//					"	inner join ERP_MUAHANG c on b.MUAHANG_FK = c.PK_SEQ " +
//					 "  where c.SOPO = N'"+sonhanhang+"' and a.CONGTY_FK = "+obj.getCongtyId()+" ) ";
//		}
//
//		return query;
	}
	
	private String Delete(String nhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			/*if(!db.update("update ERP_NHAPKHO set trangthai='1'  where pk_seq in(select phieunhap_fk  from ERP_HOADONNCC_PHIEUNHAP where hoadonncc_fk in( select a.pk_seq as hoadonncc from ERP_HOADONNCC a inner join ERP_PARK b on b.PK_SEQ = a.PARK_FK where a.PARK_FK='"+ nhId+"'))"))
			{
				db.getConnection().rollback();
				return "Lỗi "+ "update ERP_NHAPKHO set trangthai='1'  where pk_seq in(select phieunhap_fk  from ERP_HOADONNCC_PHIEUNHAP where hoadonncc_fk in( select a.pk_seq as hoadonncc from ERP_HOADONNCC a inner join ERP_PARK b on b.PK_SEQ = a.PARK_FK where a.PARK_FK='"+ nhId+"'))";
			}*/
			
			if(!db.update("delete from erp_hoadonncc_phieunhap where hoadonncc_fk in (select pk_seq from erp_hoadonncc where park_fk = '" +nhId + "')"))
			{
				db.getConnection().rollback();
				return "Lỗi "+"delete from erp_hoadonncc_phieunhap where hoadonncc_fk in (select pk_seq from erp_hoadonncc where park_fk = '" +nhId + "')";
			}
			
			if(!db.update("delete from erp_hoadonncc_donmuahang where hoadonncc_fk in (select pk_seq from erp_hoadonncc where park_fk = '" +nhId + "')"))
			{
				db.getConnection().rollback();
				return "Lỗi "+"delete from erp_hoadonncc_donmuahang where hoadonncc_fk in (select pk_seq from erp_hoadonncc where park_fk = '" +nhId + "')";
			}

			if(!db.update("delete from ERP_hoadonncc where pk_seq in (select pk_seq from erp_hoadonncc where park_fk = '" +nhId + "')"))
			{
				db.getConnection().rollback();
				return "Lỗi "+"delete from ERP_hoadonncc where pk_seq in (select pk_seq from erp_hoadonncc where park_fk = '" +nhId + "')";
			}
			
			if(db.updateReturnInt("delete from ERP_PARK  where pk_seq = '" + nhId + "' and trangthai=0") !=1)
			{
				db.getConnection().rollback();
				return "Lỗi "+"delete from ERP_PARK  where pk_seq = '" + nhId + "'";
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			return "Không thể xóa! Lỗi: " + e.getMessage(); 
		}
		
	}

}

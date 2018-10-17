package geso.traphaco.erp.servlets.thanhtoanhoadon;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thanhtoanhoadon.IErpThanhtoanhoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.IErpThanhtoanhoadonList;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.ErpThanhtoanhoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.ErpThanhtoanhoadonList;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpThanhtoanhoadonSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpThanhtoanhoadonSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpThanhtoanhoadonList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    // khai bao Severlet
	    String ServerletName = this.getServletName();
	    


	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String tthdId = util.getId(querystring);
	    
	    obj = new ErpThanhtoanhoadonList();
	    
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    obj.setUserId(userId);
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(tthdId, userId);
	    	///// CÒN GET QUERY Ở CÁC CHỖ DOGET:svl
	    	//delete,CHOT,UNCHOT,

	    	// GETet vô phần search
	    	String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
	    	geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    					
	    	if(msg.length() > 0)
	    		obj.setmsg(msg);
	    	obj.init("");
		   
			session.setAttribute("obj", obj);

			session.setAttribute("doituong", obj.getDoiTuongChiPhiKhac());
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDon.jsp";
			response.sendRedirect(nextJSP);
			
	    
	    }
	    else if(action.equals("chot"))
	    	{
	    		IErpThanhtoanhoadon tthd = new ErpThanhtoanhoadon(tthdId);
	    		tthd.setCtyId((String)session.getAttribute("congtyId"));
	    		tthd.setUserId(userId);
	    		
	    		///// CÒN GET QUERY Ở CÁC CHỖ DOGET:svl
	    		//delete,CHOT,UNCHOT,

	    		// GETet vô phần search
	    		String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    		tthd.init();
	    		if(!tthd.chotTTHD(userId))
	    		{
	    			obj.setmsg(tthd.getMsg());
	    		}
	    		obj.init("");
	    		session.setAttribute("obj", obj);

				session.setAttribute("doituong", obj.getDoiTuongChiPhiKhac());
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDon.jsp";
				response.sendRedirect(nextJSP);
	    	}
	    
	    
	    else if(action.equals("unchot"))
    	{
    		IErpThanhtoanhoadon tthd = new ErpThanhtoanhoadon(tthdId);
    		tthd.setCtyId((String)session.getAttribute("congtyId"));
    		tthd.setUserId(userId);
    		
    		///// CÒN GET QUERY Ở CÁC CHỖ DOGET:svl
    		//delete,CHOT,UNCHOT,

    		// GETet vô phần search
    		String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
    		tthd.init();
    		if(!tthd.unchotTTHD(userId))
    		{
    			obj.setmsg(tthd.getMsg());
    		}
    		obj.init("");
    		String querySearch = GiuDieuKienLoc.createParams(obj);
    	    util.setSearchToHM(userId, session, ServerletName, querySearch);
    		session.setAttribute("obj", obj);

			session.setAttribute("doituong", obj.getDoiTuongChiPhiKhac());
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDon.jsp";
			response.sendRedirect(nextJSP);
    	}
    
	    
	    else 
	    {
	    
	    	
	    obj.init("");
	    String querySearch = GiuDieuKienLoc.createParams(obj);
	    util.setSearchToHM(userId, session, ServerletName, querySearch);
		session.setAttribute("obj", obj);

		session.setAttribute("doituong", obj.getDoiTuongChiPhiKhac());
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDon.jsp";
		response.sendRedirect(nextJSP);
	    }
	}
	    
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpThanhtoanhoadonList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    String ServerletName = this.getServletName();
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String ctyId = (String)session.getAttribute("congtyId");	  
	    
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpThanhtoanhoadon tthdBean = new ErpThanhtoanhoadon();
	    	tthdBean.setCtyId(ctyId);
	    	tthdBean.setnppdangnhap(util.getIdNhapp(userId));
	    	tthdBean.setUserId(userId);
	    	
	    	tthdBean.createRs();
    		
	    	session.setAttribute("doituongkhac", "");
	    	session.setAttribute("doituong", "");
	    	session.setAttribute("loaithanhtoan", "");
	    	session.setAttribute("nhomncccn", "");
	    	
	    	session.setAttribute("tthdBean", tthdBean);

    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDonNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpThanhtoanhoadonList();
	    		obj.setCongtyId(ctyId);
		    	obj.setUserId(userId);
		    	this.getSearchQuery(request, obj, util);
	
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setnppdangnhap(util.getIdNhapp(userId));
		    	
		    	obj.init("");
		    	
		    	//// SET QUERY Ở ĐIỀU KIỆN LỌC ( DOPOST ) + 	if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    	//LƯU Ý : ĐẶT SAU INIT
		    	String querySearch = GiuDieuKienLoc.createParams(obj);
		    	util.setSearchToHM(userId, session,ServerletName, querySearch);
		    	
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDon.jsp");	
				session.setAttribute("doituong", obj.getDoiTuongChiPhiKhac());
		    }
	    	else
	    	{
		    	obj = new ErpThanhtoanhoadonList();		    	
		    	obj.setnppdangnhap(util.getIdNhapp(userId));
		    	obj.setCongtyId(ctyId);
		    	obj.setUserId(userId);
		    	
		    	this.getSearchQuery(request, obj, util);
		    	obj.init("");
				obj.setUserId(userId);
				String querySearch = GiuDieuKienLoc.createParams(obj);
		    	util.setSearchToHM(userId, session,ServerletName, querySearch);

		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThanhToanHoaDon.jsp");
	    	}
	    }
	}
	
	public void getSearchQuery(HttpServletRequest request, IErpThanhtoanhoadonList obj, Utility util)
	{
		
		if(request != null)
		{
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if(tungay == null)
				tungay = "";
			obj.setTungay(tungay);
			
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if(denngay == null)
				denngay = "";
			obj.setDenngay(denngay);
			
			String doiTuong = util.antiSQLInspection(request.getParameter("doiTuong"));
			if(doiTuong == null)
				doiTuong = "";
			obj.setDoiTuongChiPhiKhac(doiTuong);
			
			String nccId = util.antiSQLInspection(request.getParameter("nccId"));
			String nccTen = util.antiSQLInspection(request.getParameter("nccTen"));
			if (nccId == null)
				nccId = "";
			if (nccTen == null)
				nccTen = "";
			if (nccTen.indexOf(",") > 0) {
				nccTen = nccTen.split(",")[1];
			} else {
				nccId = "";
			}
			obj.setNccId(nccId);
			obj.setNccTen(nccTen);
			
			String nvId = util.antiSQLInspection(request.getParameter("nvId"));
			if(nvId == null)
				nvId = "";
			obj.setNvId(nvId);
			
			String sotien = util.antiSQLInspection((request.getParameter("sotientt").replaceAll(",", "")));
			if(sotien == null)
				sotien = "";
			obj.setSotien(sotien);
			
			String[] loaihoadon = request.getParameterValues("loaihoadon");
			if (loaihoadon != null)
			{
				String _scheme = "";
				for(int i = 0; i < loaihoadon.length; i++)
				{
					_scheme += loaihoadon[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					obj.setLoaihoadon(_scheme);
				}
			}
			
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if(trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			
			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if(sochungtu == null)
				sochungtu = "";
			obj.setSochungtu(sochungtu);

			String soPost = util.antiSQLInspection(request.getParameter("soPost"));
			if(soPost == null)
				soPost = "";
			obj.setSoPost(soPost.trim());		
			
			String machungtu = util.antiSQLInspection(request.getParameter("machungtu"));
			if(machungtu == null)
				machungtu = "";
			obj.setMachungtu(machungtu);
			
			String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
			if(sohoadon == null)
				sohoadon = "";
			obj.setSohoadon(sohoadon);
		}
		
		
	
	}
	
	private String Delete(String tthdId, String userId)
	{
		dbutils db = new dbutils();
		
		String msg = "";
		
		String query = "UPDATE  ERP_THANHTOANHOADON set TRANGTHAI = '2' , NGUOISUA = "+ userId +" , NGAYSUA = '"+ getDateTime() +"' where PK_SEQ = '" + tthdId + "' and trangthai=0 ";
		System.out.println(query);
		if(db.updateReturnInt(query)!=1)
		{
			msg = "Không thể xóa ERP_THANHTOANHOADON: " + query;
		}
		
		String dntt_fk="";
		String dntu_fk="";
		
		query="SELECT DNTT_FK,dntu_fk  FROM ERP_THANHTOANHOADON WHERE PK_SEQ="+tthdId+" ";
		ResultSet rsCheck=db.get(query);
		try
		{
			while(rsCheck.next())
			{
				dntt_fk=rsCheck.getString("DNTT_FK");
				dntu_fk=rsCheck.getString("dntu_fk");
			}
		} catch (Exception e)
			{
				e.printStackTrace();
			}
		
		if(dntt_fk!=null&&dntt_fk.length()>0)
		{
				query = " UPDATE ERP_MUAHANG SET ISDACHI = 0, ISHOANTAT = 0,ISKTV=0 , TRANGTHAI = 0 WHERE PK_SEQ IN (SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE LOAIHD = 6 AND THANHTOANHD_FK = "+tthdId+" ) ";
				System.out.println(query);
				if(!db.update(query))
				{
					msg = "Không thể xóa cập nhật DNTT : " + query;
				}
				
				query = " UPDATE ERP_DUYETMUAHANG SET TRANGTHAI = 0 WHERE MUAHANG_FK IN (SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE LOAIHD = 6 AND THANHTOANHD_FK = "+tthdId+" ) AND LOAICAP_FK = 10004 ";
				System.out.println(query);
				if(!db.update(query))
				{
					msg = "Không thể xóa cập nhật DNTT : " + query;
				}
				
				
					query = "DELETE FROM ERP_PHATSINHKETOAN \n " + 
					"WHERE LOAICHUNGTU in(	N'Chi phí khác',N'Thanh toán tạm ứng' ) \n " +
					"AND SOCHUNGTU in (SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE LOAIHD IN (6) AND THANHTOANHD_FK = " + tthdId + ")";
			
			System.out.println(query);
			if(!db.update(query))
			{
				msg = "Không thể xóa cập nhật DNTT : " + query;
				return msg;
			
			}
		}
		
		
		// ĐẾM SỐ PHIẾU TẠM ỨNG
		if(dntu_fk!=null&&dntu_fk.length()>0)
		{		
			query = " UPDATE ERP_TAMUNG SET ISDACHI = 0, ISHOANTAT = 0,ISKTV=0, ISKTT = 0 , TRANGTHAI = 0 WHERE PK_SEQ IN (SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE LOAIHD = 1 AND THANHTOANHD_FK = "+tthdId+" ) ";
		
			System.out.println(query);
			if(!db.update(query))
			{
				msg = "Không thể xóa cập nhật DNTT : " + query;
			}
			
			query = " UPDATE ERP_DUYETTAMUNG SET TRANGTHAI = 0 WHERE TAMUNG_FK IN (SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE LOAIHD = 1 AND THANHTOANHD_FK = "+tthdId+") AND LOAICAP_FK = 10004 ";
			System.out.println(query);
			if(!db.update(query))
			{
				msg = "Không thể xóa cập nhật DNTT : " + query;
			}	
		}
			db.shutDown();
			
		return msg;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
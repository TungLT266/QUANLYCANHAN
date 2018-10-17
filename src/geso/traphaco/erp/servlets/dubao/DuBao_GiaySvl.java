package geso.traphaco.erp.servlets.dubao;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dubaokinhdoanh.IDubaokinhdoanh_Giay;
import geso.traphaco.erp.beans.dubaokinhdoanh.IDubaokinhdoanh_Giay_List;
import geso.traphaco.erp.beans.dubaokinhdoanh.imp.Dubaokinhdoanh_Giay;
import geso.traphaco.erp.beans.dubaokinhdoanh.imp.Dubaokinhdoanh_Giay_List;
import geso.traphaco.erp.db.sql.dbutils;

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


public class DuBao_GiaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DuBao_GiaySvl() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("VAO DU BAO LIST GET");
		IDubaokinhdoanh_Giay_List obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();	    
	    Utility util = new Utility();	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    String message = null;
	    String dbId = util.getId(querystring);
	    String sql = "select ERP_DUBAO.PK_SEQ, DATEDIFF(DAYOFYEAR,  CONVERT(DATETIME, NGAYDUBAO, 120),GETDATE()) as NGAY " +
	    			 "from ERP_DUBAO " +
	    			 "WHERE TRANGTHAI = 0 AND DATEDIFF(DAYOFYEAR,  CONVERT(DATETIME, NGAYDUBAO, 120),GETDATE()) > 365";
	    dbutils db = new dbutils();
	    System.out.println(sql);
	    ResultSet resultSet = db.get(sql);
	    List<String> listDubaoQuahan = new ArrayList<String>();
	    try {
			while (resultSet.next()) {
				listDubaoQuahan.add(resultSet.getString("PK_SEQ"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    System.out.println("QUA DAY");
		if(listDubaoQuahan.size()>0)
		{
			message = "Những dự báo kinh doanh số: " + listDubaoQuahan+
					  " đã cũ bạn có muốn xóa ?";
		}
		session.setAttribute("thongbao", message);
	
	    obj = new Dubaokinhdoanh_Giay_List();
	    obj.setCtyId(ctyId);
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(dbId);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		/*String kq = ChotChuyenkho(nhId);
	    		if(kq.length() > 0)
	    		{
	    			obj.setMsg("Chốt không thành công, lỗi: "+ kq);
	    		}*/
	    	}
	    }
	    System.out.println("TOI DAY");
	    obj.setUserId(userId);
	    obj.init("");    
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBao_Giay.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDubaokinhdoanh_Giay_List obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");
		
		String action = request.getParameter("action");
		
		if(action==null) action="";
		
		Utility util = new Utility();
		
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		 
		if(action.equals("DeleteOld"))
		{
			String sql = "select ERP_DUBAO.PK_SEQ,DATEDIFF(DAYOFYEAR,  CAST(NGAYDUBAO as DATE),GETDATE()) as NGAY from ERP_DUBAO WHERE TRANGTHAI = 0 AND DATEDIFF(DAYOFYEAR,  CAST(NGAYDUBAO as DATE),GETDATE()) > 365";
		    dbutils db = new dbutils();
		    ResultSet resultSet = db.get(sql);
		    List<String> listDubaoQuahan = new ArrayList<String>();
		    try {
				while (resultSet.next()) {
					listDubaoQuahan.add(resultSet.getString("PK_SEQ"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (String dubao : listDubaoQuahan) {
				Delete(dubao);
			}
			
    		response.sendRedirect("/../TraphacoHYERP/DuBao_GiaySvl?userId="+userId);		
		}else
		if(action.equals("Taomoi"))
		{
			IDubaokinhdoanh_Giay dbkdBean = new Dubaokinhdoanh_Giay();
			dbkdBean.setCtyId(ctyId);
			
			System.out.println("I am here");
			
			//dbkdBean.createRs_New();
			
			session.setAttribute("dbkdBean", dbkdBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBao_Giay_New.jsp";
    		
			response.sendRedirect(nextJSP);
		 }
		 else
		 {
			 if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    	{
		    		obj = new Dubaokinhdoanh_Giay_List();
		    		obj.setCtyId(ctyId);
		    		
			    	String search = getSearchQuery(request, obj);
			    	System.out.println("nxtApprSplitting "+Integer.parseInt(request.getParameter("nxtApprSplitting")));
			    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			    	obj.setUserId(userId);
			    	obj.init(search);
			    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			    	session.setAttribute("obj", obj);
			    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DuBao_Giay.jsp");	
			    }
		    	else
		    	{
		    		System.out.println("VAO DU BAO LIST");
			    	obj = new Dubaokinhdoanh_Giay_List();
			    	obj.setCtyId(ctyId);
			    	
			    	String search = getSearchQuery(request, obj);
			    	obj.init(search);
					obj.setUserId(userId);				
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DuBao_Giay.jsp");
		    	}
		 }
	}
	
	private String getSearchQuery(HttpServletRequest request, IDubaokinhdoanh_Giay_List obj)
	{
		String query	=	"select NV.TEN as TENNV,a.NGAYSUA,a.NGAYTAO,NV.PK_SEQ as MANV,a.MOHINH, " +
			 			"NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS ,a.TRANGTHAI,a.PK_SEQ, " +
			 			"a.NGAYDUBAO, a.DIENGIAI, c.TEN AS KHO_FK, c.TEN as KHO, a.HIEULUC,ISNULL(DVKD.DONVIKINHDOANH, '') AS DVKD " +
			 			"from ERP_DUBAO a " +
			 			"left join ERP_KHOTT c on c.pk_seq=a.KHO_FK " +
			 			" LEFT JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = a.DVKD_FK " +			 		
			 			"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ " +
			 			"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
			 			"WHERE a.CONGTY_FK = " + obj.getCtyId() + " ";

		Utility util = new Utility();	
		
		String ngaydubao = util.antiSQLInspection(request.getParameter("ngaydubao"));
	    if(ngaydubao == null)
	    	ngaydubao="";
	    obj.setNgaydubao(ngaydubao);
	    	    
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    if(diengiai == null)
	    	diengiai = "";
	    obj.setDiengiai(diengiai);
	    
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null) dvkdId = "";
		obj.setDvkdId(dvkdId);
	    
		
		if(ngaydubao.length() > 0)
			query += " and a.ngaydubao = '" + ngaydubao + "'";
			
		if(diengiai.length() > 0)
			query += " and a.DIENGIAI LIKE N'%" + diengiai + "%'";

		if(dvkdId.length() > 0)
			query += " and a.dvkd_fk = '" + dvkdId + "'";
		
		System.out.println("cau query loc la "+query);
		return query;
	}
	
	
	private String Delete(String Id)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			String sql="delete from ERP_DUBAOSANPHAMMTS_TUAN where DUBAO_FK='"+Id+"'";
			System.out.println(sql);
			if(!db.update(sql))
			{
				db.update("rollback");
				return "Không thể xóa! Lỗi: dự báo đã tạo yêu cầu sản xuất không thể xóa"; 
			}
			
			sql="delete from ERP_DUBAOSANPHAMMTS where DUBAO_FK='"+Id+"'";
			System.out.println(sql);
			if(!db.update(sql))
			{
				db.update("rollback");
				return "Không thể xóa! Lỗi: dự báo đã tạo yêu cầu sản xuất không thể xóa"; 
			}
			
			sql="delete from ERP_DUBAO where PK_SEQ='"+Id+"'";
			System.out.println(sql);
			if(!db.update(sql))
			{
				db.update("rollback");
				return "Không thể xóa! Lỗi:  dự báo đã tạo yêu cầu sản xuất không thể xóa"; 
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			return "Không thể xóa! Lỗi: " + e.getMessage(); 
		}
		
	}
	

}

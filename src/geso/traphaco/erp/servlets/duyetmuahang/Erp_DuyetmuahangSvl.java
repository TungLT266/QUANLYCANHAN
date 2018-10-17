package geso.traphaco.erp.servlets.duyetmuahang;

import geso.traphaco.erp.beans.duyetdonmuahang.IDuyetdonmuahang;
import geso.traphaco.erp.beans.duyetdonmuahang.imp.Duyetdonmuahang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_DuyetmuahangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Erp_DuyetmuahangSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    HttpSession session = request.getSession();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String loaidh = util.antiSQLInspection(request.getParameter("loai"));
	    	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));

	    String action = util.getAction(querystring);
	    
	    String dmhId = util.getId(querystring);
	    
	    String msg = "";
	    if (action.equals("delete"))
	    {	
	    	msg = Delete(dmhId,userId);
	    	IDuyetdonmuahang ddmhBean = new Duyetdonmuahang();
    	    ddmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, ddmhBean);

    	    ddmhBean.setUserId(userId);
    	    ddmhBean.setLoaidh(loaidh);
    	    if(msg.length()>0)
    	    {
    	    	ddmhBean.setMsg(msg);
    	    }
    	    ddmhBean.init();

	 		session.setAttribute("ddmhBean", ddmhBean);
	 		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDonMuaHangList.jsp";
	    	response.sendRedirect(nextJSP);
	    	
	    }else
	    {
	    
   	    IDuyetdonmuahang ddmhBean = new Duyetdonmuahang();
   	    ddmhBean.setCongtyId((String)session.getAttribute("congtyId"));
   	    ddmhBean.setUserId(userId);
   	    ddmhBean.setLoaidh(loaidh);
   	    
   	    ddmhBean.init();

  	    util.setSearchToHM(userId, session, this.getServletName(), "");
		// Data is saved into session
		session.setAttribute("ddmhBean", ddmhBean);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDonMuaHangList.jsp";
   		response.sendRedirect(nextJSP);
	    }

	}
	
	private String Delete(String dmhId,String userId)
	{
		dbutils db = new dbutils();
		String query="";
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			query = "select COUNT(*) as sodong from ERP_NHANHANG where MUAHANG_FK = '" + dmhId + "'";
			//System.out.println("1.Query check mua hang: " + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			}
			
			System.out.println("So dong la: " + sodong + "\n");
			
			if(sodong > 0)
			{
				return "Đơn mua hàng này đã có nhận hàng, bạn phải xóa nhận hàng trước khi xóa đơn mua hàng này";
			}
			
			
			query = "select count(*) as sodong  " +
				   "from ERP_HOADONNCC a inner join ERP_HOADONNCC_PHIEUNHAP b on a.pk_seq = b.hoadonncc_fk " +
				   "where b.muahang_fk = '" + dmhId + "'";
	
			//System.out.println("Query mua hang: " + query);
			sodong = 0;
			rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			}
			

			
			if(sodong > 0)
			{
				return "Mua hàng này đã xuất hóa đơn, bạn không thể xóa";
			}
					
	
			query = " Update  ERP_MUAHANG set trangthai=3 ,nguoisua="+userId+"  where pk_seq = '" + dmhId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa đơn mua hàng này: " + query;
			}
			
			
			// Cap nhat DADATHANG trong ERP_MUANGUYENLIEUDUKIEN
			query = "UPDATE ERP_MUANGUYENLIEUDUKIEN	" +  
					"SET ERP_MUANGUYENLIEUDUKIEN.DADATHANG = ISNULL(A.SOLUONG, 0) " + 
					"FROM " +
					"( " +
					"	SELECT SUM(SOLUONG) AS SOLUONG, SANPHAM_FK, SUBSTRING(NGAYNHAN, 1, 4) AS NAM,	" + 
					"	CONVERT(INT, SUBSTRING(NGAYNHAN, 6,2)) AS THANG	" +
					"	FROM ERP_MUAHANG_SP " +
					"	WHERE SANPHAM_FK IS NOT NULL	" +
					"	GROUP BY SANPHAM_FK, SUBSTRING(NGAYNHAN, 1, 4),CONVERT(INT, SUBSTRING(NGAYNHAN, 6,2))	" +
					")A  " +
					"WHERE ERP_MUANGUYENLIEUDUKIEN.NAM = A.NAM	" + 
					"AND ERP_MUANGUYENLIEUDUKIEN.THANG = A.THANG	" +
					"AND ERP_MUANGUYENLIEUDUKIEN.SANPHAM_FK = A.SANPHAM_FK ";
			
					
			System.out.println("Cap nhat mua NL: " + query);
			
			db.update(query);
			
			
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    Utility util = new Utility();
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String ctyId = util.antiSQLInspection(request.getParameter("ctyId"));
	    
	    String dvthId = util.antiSQLInspection(request.getParameter("dvthId"));
	    
	    String lspId = util.antiSQLInspection(request.getParameter("lspId"));

	    String Id = util.antiSQLInspection(request.getParameter("Id"));
	    
	    String ngaymua = util.antiSQLInspection(request.getParameter("ngaymua"));
	    
	    String maDMH = util.antiSQLInspection(request.getParameter("maDMH"));
	    
	    String nccId = util.antiSQLInspection(request.getParameter("nccId"));
	    
	    String loaidh = util.antiSQLInspection(request.getParameter("loaidh"));
	    
	    String[] ghichu = request.getParameterValues("ghichu");
	    
	    String[] mhid = request.getParameterValues("mhid");
	    
	    String[] spid = request.getParameterValues("spid");
	    
	    String[] slduyet = request.getParameterValues("slduyet");
	    
	    String[] dongia = request.getParameterValues("dongia");
	    
	    String [] donviid=request.getParameterValues("donviid");
	    
	    String [] ngaynhanid=request.getParameterValues("ngaynhanid");
	    
	    String [] thuexuatid=request.getParameterValues("thuexuatid");
	    
	    String action = util.antiSQLInspection(request.getParameter("action")); 
	    
	    
	    IDuyetdonmuahang ddmhBean = new Duyetdonmuahang();
	    ddmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    ddmhBean.setUserId(userId);
	    ddmhBean.setCtyId(ctyId);
   	    ddmhBean.setDvthId(dvthId);
   	    ddmhBean.setNccId(nccId);
   	    ddmhBean.setMaDMH(maDMH);
   	    ddmhBean.setNgaymua(ngaymua);
   	    ddmhBean.setLspId(lspId);
   	    ddmhBean.setLoaidh(loaidh);
   	    ddmhBean.setGhiChuArr(ghichu);
   	    ddmhBean.setMhIdArr(mhid);
   	    ddmhBean.setSpIdArr(spid);
   	    ddmhBean.setSoluongduyetArr(slduyet);
   	    ddmhBean.setDongiaArr(dongia);
   	    ddmhBean.setRequest(request);
   	    ddmhBean.setDonviArr(donviid);
   	    ddmhBean.setNgaynhanArr(ngaynhanid);
   	    ddmhBean.setThuexuatArr(thuexuatid);
   	    
   	    
   	    
   	    if(action.equals("duyet")){
   	    	ddmhBean.Duyetmuahang(Id);

   	    }
   	    else if(action.equals("luughichu"))
   	    {
   	    	ddmhBean.Capnhatmuahang(Id);
   	    	
   	    }
   	    ddmhBean.init();
		// Data is saved into session
		session.setAttribute("ddmhBean", ddmhBean);
//		session.setAttribute("userId", userId);
		GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, ddmhBean);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDonMuaHangList.jsp";
   		response.sendRedirect(nextJSP);

	}

}

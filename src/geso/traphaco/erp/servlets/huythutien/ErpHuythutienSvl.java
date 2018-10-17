package geso.traphaco.erp.servlets.huythutien;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huythutien.IErpHuythutien;
import geso.traphaco.erp.beans.huythutien.IErpHuythutienList;
import geso.traphaco.erp.beans.huythutien.imp.ErpHuythutien;
import geso.traphaco.erp.beans.huythutien.imp.ErpHuythutienList;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHuythutienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHuythutienSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHuythutienList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    util.setSearchToHM(userId, session, this.getServletName(), "");
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    

    	String id = util.getId(querystring);
	    
	    obj = new ErpHuythutienList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    obj.setUserId(userId);
	    
	    System.out.println("action:"+action);
	    if (action.equals("delete"))
	    {	
	    	String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    	GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    	String msg = Delete(id, userId);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    	 obj.init("");
	    	session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTien.jsp";
			response.sendRedirect(nextJSP);
	    }
	    
	    else if (action.equals("chot"))
	    {		    	
	    	//KIỂM TRA LOẠI CHỨNG TỪ
	    	
	    	String sochungtu = util.getSoChungTu(querystring);
	    	String msg = ChotHPC(id,sochungtu, obj.getCongtyId());
	    	
	    	String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    	GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    	 obj.init("");
			    
			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTien.jsp";
			response.sendRedirect(nextJSP);
	    }else
	    {
	   
		    obj.init("");
		    
			session.setAttribute("obj", obj);
			util.setSearchToHM(userId, session, this.getServletName(), "");	
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTien.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHuythutienList obj;
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
	    	IErpHuythutien hctmhBean = new ErpHuythutien();
	    	hctmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	hctmhBean.createRs();
    		
	    	session.setAttribute("hctmhBean", hctmhBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTienNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpHuythutienList();
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setUserId(userId);
	    		obj.setnppdangnhap(util.getIdNhapp(userId));
	    		
		    	this.getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));	
		    	
		    	String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
		    	
		    	obj.init("");
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHuyThuTien.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpHuythutienList();
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setUserId(userId);
		    	obj.setnppdangnhap(util.getIdNhapp(userId));
		    	
		    	this.getSearchQuery(request, obj);
		    	

		    	obj.init("");
		    	
		    	String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHuyThuTien.jsp");
	    	}
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpHuythutienList obj)
	{
//		String query = "SELECT a.PK_SEQ as SOPHIEU, a.SOCHUNGTU,  a.TRANGTHAI, a.NGAYTAO, a.NGAYSUA, b.TEN as NGUOITAO, c.TEN as NGUOISUA \n " +
//					   "FROM ERP_HUYCHUNGTUKETOAN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq \n " +
//					   "where a.congty_fk = '" + obj.getCongtyId() + "' and a.LOAICHUNGTU = 1  and a.npp_fk = "+obj.getnppdangnhap()+"";
//		
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
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String sochungtu = request.getParameter("sochungtu");
		if(sochungtu == null)
			sochungtu = "";
		obj.setsochungtu(sochungtu);
	
		String sotien = request.getParameter("sotientt").replaceAll(",", "");
		if(sotien == null)
			sotien = "";
		obj.setsotien(sotien);
		
//		if(tungay.length() > 0)
//			query += " and a.ngaytao >= '" + tungay + "'";
//		
//		if(denngay.length() > 0)
//			query += " and a.ngaysua <= '" + denngay + "'";
//		
//		if(trangthai.length() > 0)
//			query += " and a.trangthai = '" + trangthai + "'";
//		
//		if(nguoitao.length() > 0)
//			query += " and b.TEN like N'%" + nguoitao + "%'";
//		
//		if(sochungtu.length() > 0)
//			query += " and a.SOCHUNGTU like '%" + sochungtu + "%'";
//		
//		if(sotien.length()>0)
//			query += " and (select sum(SOTIEN) FROM ERP_HUYCHUNGTUKETOAN_CHUNGTU WHERE HCTKT_FK = a.PK_SEQ ) = "+sotien+"";
//				
//		
//		return query;
	}
	
	private String Delete(String hctmhId, String userId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			System.out.println("update ERP_HUYCHUNGTUKETOAN set trangthai = '2', nguoisua = "+userId+" where pk_seq = '" + hctmhId + "'");
			db.update("update ERP_HUYCHUNGTUKETOAN set trangthai = '2', nguoisua = "+userId+" where pk_seq = '" + hctmhId + "' and trangthai=0");
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (SQLException e)
		{ 
			db.shutDown(); 
			return "Khong the xoa huy chung tu"; 
		}
		
	}

	private String ChotHPC(String Id, String sochungtu, String congtyId)
	{
		try{
			dbutils db = new dbutils();
			
			String query = "";
			query ="UPDATE ERP_HUYCHUNGTUKETOAN SET TRANGTHAI = 1 WHERE PK_SEQ ='"+Id+"' and trangthai=0";
			
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return  "Khong the huy thu tien ERP_HUYCHUNGTUKETOAN, " + query;
			}

			query = " select NGAYGHISO as ngaynghiepvu from ERP_THUTIEN where pk_seq = '" + sochungtu + "' ";
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			ResultSet ngayRS = db.get(query);
			String ngaynghiepvu = "";
			
			try 
			{
				if(ngayRS.next())
				{
					ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
				}
				ngayRS.close();
			} 
			catch (Exception e) { e.printStackTrace(); }
			
			String nam = ngaynghiepvu.substring(0, 4);
			String thang = ngaynghiepvu.substring(5, 7);
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where ( LOAICHUNGTU = N'Thu tiền hóa đơn' OR LOAICHUNGTU = N'Thu tiền theo bảng kê' OR LOAICHUNGTU = N'Thu tiền KH trả trước' OR LOAICHUNGTU = N'Thu tiền theo hóa đơn'  OR LOAICHUNGTU = N'Thu khác') and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("1.CHECK NO-CO: " + query);
			
			ResultSet rsPSKT = db.get(query);
			try 
			{
				while(rsPSKT.next())
				{
					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
					double NO = rsPSKT.getDouble("NO");
					double CO = rsPSKT.getDouble("CO");
					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
					
					//NEU LA CO THI BAY GIO GHI GIAM CO LAI
					if( NO > 0 )
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					else
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					
					System.out.println("1.REVERT NO-CO: " + query);
					
					if(db.updateReturnInt(query)<0)
					{
						db.getConnection().rollback();
						return  "Khong the huy thu tien ERP_HUYCHUNGTUKETOAN, " + query;
					}
					
				}
				rsPSKT.close();
			} 
			catch (Exception e) { }
					
			
			//HỦY KẾ TOÁN ĐÃ GHI NHẬN
			query = " DELETE ERP_PHATSINHKETOAN WHERE ( LOAICHUNGTU = N'Thu tiền hóa đơn' OR LOAICHUNGTU = N'Thu tiền theo bảng kê' OR LOAICHUNGTU = N'Thu tiền KH trả trước' OR LOAICHUNGTU = N'Thu tiền theo hóa đơn' OR LOAICHUNGTU = N'Thu khác') and SOCHUNGTU = '"+sochungtu+"'";	
			System.out.println("1.CHECK NO-CO: " + query);
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return  "Khong the huy thu tien ERP_HUYCHUNGTUKETOAN, " + query;
			}			
			
			// XÓA ĐƠN HÀNG
			
			query = "update ERP_THUTIEN set trangthai = '2' where pk_seq = "+sochungtu+" ";
			System.out.println("1.CHECK NO-CO: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return  "Khong the huy thu tien ERP_HUYCHUNGTUKETOAN, " + query;
			}
			
			query = " update ERP_BANGKETHUTIEN SET TRANGTHAI = 0 WHERE PK_SEQ = (SELECT BANGKE_FK FROM ERP_THUTIEN WHERE PK_SEQ = "+sochungtu+") ";
			
			System.out.println("1.Cap nhat ERP_BANGKETHUTIEN: " + query);

			if(!db.update(query))
			{
				db.getConnection().rollback();
				return  "Khong the huy thu tien ERP_HUYCHUNGTUKETOAN, " + query;
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return "";
	}
}

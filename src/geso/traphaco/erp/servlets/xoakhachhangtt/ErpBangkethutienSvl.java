package geso.traphaco.erp.servlets.xoakhachhangtt;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.xoakhachhangtt.IErpBangkethutien;
import geso.traphaco.erp.beans.xoakhachhangtt.IErpBangkethutienList;
import geso.traphaco.erp.beans.xoakhachhangtt.imp.ErpBangkethutien;
import geso.traphaco.erp.beans.xoakhachhangtt.imp.ErpBangkethutienList;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpBangkethutienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpBangkethutienSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpBangkethutienList obj;
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
	    
	    String tthdId = util.getId(querystring);

	    obj = new ErpBangkethutienList();	    
	    obj.setCongtyId((String)session.getAttribute("congtyId"));  
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    System.out.println(":::: ACTION: " + action);
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(tthdId);
	    	if(msg.length() > 0)
	    		obj.setmsg(msg);
	    }
	    else if(action.equals("chot"))
    	{
    		IErpBangkethutien tthd = new ErpBangkethutien(tthdId);
    		tthd.setCongtyId((String)session.getAttribute("congtyId"));
    	    
    		if(!tthd.chotTTHD(userId, tthdId))
    		{
    			obj.setmsg(tthd.getMsg());
    		}
    	}

	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeThuTien.jsp";
		response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpBangkethutienList obj;
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
	    
	    String chungtu = util.antiSQLInspection(request.getParameter("chungtu"));
		if (chungtu == null)
			chungtu = "";
		
	    if(action.equals("Tao moi"))
	    {
	    	IErpBangkethutien tthdBean = new ErpBangkethutien();
	    	tthdBean.setUserId(userId);
	    	tthdBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			tthdBean.setDoituongId(session.getAttribute("doituongId"));
	    	tthdBean.setTungay(getDateTime());
    	    
	    	tthdBean.createRs();
    		
	    	session.setAttribute("tthdBean", tthdBean);

    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeThuTienNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpBangkethutienList();
	    		obj.setUserId(userId);
	    		obj.setNppId(request.getParameter("nppId").toString());
	    		
		    	String search = getSearchQuery(request, obj, userId);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpBangKeThuTien.jsp");	
		    }
	    	
	    	else
	    	{
		    	obj = new ErpBangkethutienList();
		    	obj.setUserId(userId);
		    	obj.setNppId(request.getParameter("nppId").toString());
		    	
		    	String search = getSearchQuery(request, obj, userId);
		    	obj.init(search);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpBangKeThuTien.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpBangkethutienList obj, String userId)
	{
		dbutils db = new dbutils();
		String dmquyen = 
			" SELECT DISTINCT C2.DMQ_fk, \n"+
			" SUBSTRING( \n"+
			"	( \n"+
			"		SELECT ','+ cast( C1.DMQ_fk as nvarchar(50)) \n"+
			"		FROM phanquyen C1 \n"+
			"		WHERE C1.dmq_fk = C2.dmq_fk \n"+
			"		ORDER BY C1.dmq_fk \n"+
			"		FOR XML PATH ('') \n"+
			"	), 2, 1000) danhmucq \n"+
			" FROM phanquyen C2 WHERE C2.NHANVIEN_FK = "+userId+" \n";
			
		ResultSet Rsdmq = db.get(dmquyen);
		String dmq = "";
		
		try
		{
			while(Rsdmq.next())
			{
				dmq = Rsdmq.getString("danhmucq");
			}
			Rsdmq.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		String query = "";
		if(dmq.contains("100044") || dmq.contains("100045") || dmq.contains("100026"))// QUYỀN KẾ TOÁN TRƯỞNG || QUYỀN KẾ TOÁN
		{
			query = " SELECT distinct a.pk_seq, a.trangthai, a.ngaychungtu, a.ngayghiso, a.ngaytao, a.ngaysua, \n" +
					" 				  d.ten as nguoitao, e.ten as nguoisua, isnull(a.tongtientt,0) TIENTT, ( SELECT count(BANGKE_FK) FROM ERP_THUTIEN WHERE TRANGTHAI IN (1) AND BANGKE_FK = a.PK_SEQ  ) isThuTien \n" +
					" FROM ERP_BANGKETHUTIEN a  \n" +
					" INNER JOIN ERP_BANGKETHUTIEN_HOADON b on a.pk_seq = b.bangke_fk  \n"+
				    " INNER JOIN ERP_HOADONNPP c on c.PK_SEQ = b.hoadon_fk  \n"+					  
					" INNER JOIN NHANVIEN d on a.nguoitao = d.pk_seq \n" +
					" INNER JOIN NHANVIEN e on a.nguoisua = e.pk_seq \n"+
					" LEFT JOIN KHACHHANG kh on c.KHACHHANG_FK = kh.PK_SEQ \n"+
					" LEFT JOIN NHAPHANPHOI npp on c.NPP_DAT_FK = npp.PK_SEQ \n"+
					" LEFT JOIN ERP_NHANVIEN nv on c.nhanvien_fk = nv.PK_SEQ \n"+
					" WHERE a.npp_fk = " + obj.getNppId() +" ";
		}
		else{
		 query =    " SELECT distinct a.pk_seq, a.trangthai, a.ngaychungtu, a.ngayghiso, a.ngaytao, a.ngaysua, \n" +
					" 				  d.ten as nguoitao, e.ten as nguoisua, isnull(a.tongtientt,0) TIENTT, ( SELECT count(BANGKE_FK) FROM ERP_THUTIEN WHERE TRANGTHAI IN (1) AND BANGKE_FK = a.PK_SEQ  ) isThuTien \n" +
					" FROM ERP_BANGKETHUTIEN a  \n" +
					" INNER JOIN ERP_BANGKETHUTIEN_HOADON b on a.pk_seq = b.bangke_fk  \n"+
				    " INNER JOIN ERP_HOADONNPP c on c.PK_SEQ = b.hoadon_fk  \n"+					  
					" INNER JOIN NHANVIEN d on a.nguoitao = d.pk_seq \n" +
					" INNER JOIN NHANVIEN e on a.nguoisua = e.pk_seq \n"+
					" LEFT JOIN KHACHHANG kh on c.KHACHHANG_FK = kh.PK_SEQ \n"+
					" LEFT JOIN NHAPHANPHOI npp on c.NPP_DAT_FK = npp.PK_SEQ \n"+
					" LEFT JOIN ERP_NHANVIEN nv on c.nhanvien_fk = nv.PK_SEQ \n"+
					" WHERE a.npp_fk = " + obj.getNppId() +" AND a.NGUOITAO = "+ userId;
		}
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
				
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String nvgnId = request.getParameter("nvgnId");
		if(nvgnId == null)
			nvgnId = "";
		obj.setNvgnId(nvgnId);
		
		String ddkdId = request.getParameter("ddkdId");
		if(ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);
		
		String makhachhang = request.getParameter("makhachhang");
		if(makhachhang == null)
			makhachhang = "";
		obj.setMakhachhang(makhachhang);
		
		String khuvucId = request.getParameter("khuvucId");
		if(khuvucId == null)
			khuvucId = "";
		obj.setKhuvucId(khuvucId);
		
		String maphieu = request.getParameter("maphieu");
		if(maphieu == null)
			maphieu = "";
		obj.setMaPhieu(maphieu);
		String khohh = request.getParameter("khohhid");
		if(khohh == null)
			khohh = "";
		obj.setKhohh(khohh);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		if(tungay.length() > 0)
			query += " and a.ngaychungtu >= '" + tungay.trim() + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaychungtu <= '" + denngay.trim() + "'";
		
		if(maphieu.length() > 0)
			query += " and a.pk_seq like '%" + maphieu.trim() + "%'";
		
		if(nvgnId.trim().length() > 0)
			query += " and a.nvgn_fk = '" + nvgnId.trim() + "' ";
		
		if(ddkdId.trim().length() > 0)
			query += " and a.ddkd_fk = '" + ddkdId.trim() + "' ";
		
		if(khuvucId.trim().length() > 0)
			query += " and a.khuvuc_fk = '" + khuvucId.trim() + "' ";
		
		if(khohh.trim().length() > 0)
			query += " and c.kho_fk = '" + khohh.trim() + "' ";
		
		if (makhachhang.trim().length() > 0)
			query += " and ( ( kh.ten like N'%" + makhachhang.trim()+ "%' or kh.ma like N'%" + makhachhang.trim()+ "%' or kh.mafast = N'%"+makhachhang.trim()+"%' ) or ( npp.ma like N'%" + makhachhang.trim()
					+ "%' or npp.ten like N'%" + makhachhang.trim()+ "%' or npp.mafast like N'%"+makhachhang.trim()+"%' ) ) ";

		// bổ sung tìm kiếm người tạo
		if(nguoitao.trim().length() >0){
			query += " and d.ten like N'%" + nguoitao.trim() + "%'";
		}
		
		if(sohoadon.trim().length()>0)
		{
			query += " and c.SOHOADON LIKE '%"+sohoadon.trim()+"%'";
		}
		
		System.out.println("tim kiem "+query);
		return query;
	}
	
	private String Delete(String tthdId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			// XÓA BẢNG KÊ
			String query = "update ERP_BANGKETHUTIEN set trangthai = 2 where pk_seq = '" + tthdId + "'";
			System.out.println("1.Cap nhat ERP_THUTIEN: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi xóa: " + query;
			}
			
			// XÓA THU TIỀN
			query = "update ERP_THUTIEN set TRANGTHAI = '2' where BANGKE_FK = '" + tthdId + "'";			
			System.out.println("1.Cap nhat ERP_THUTIEN: " + query);

			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi xóa: " + query;
			}
			
			String nam = "";
			String thang = "";
			
			// NẾU CÓ THU TIỀN RỒI THÌ GIẢM NỢ CÓ
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = " select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT, NGAYGHINHAN \n" +
				    " from ERP_PHATSINHKETOAN \n" +
				    " where ( LOAICHUNGTU = N'Thu tiền hóa đơn' OR LOAICHUNGTU = N'Thu tiền theo bảng kê' OR LOAICHUNGTU = N'Thu tiền KH trả trước' \n" +
				    " OR LOAICHUNGTU = N'Thu tiền theo hóa đơn') and SOCHUNGTU IN ( SELECT PK_SEQ FROM ERP_THUTIEN WHERE BANGKE_FK = "+tthdId+" ) ";
			
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
					
					nam = rsPSKT.getString("NGAYGHINHAN").substring(0, 4);
					thang = rsPSKT.getString("NGAYGHINHAN").substring(5, 7);
					
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
			query = " DELETE ERP_PHATSINHKETOAN WHERE ( LOAICHUNGTU = N'Thu tiền hóa đơn' OR LOAICHUNGTU = N'Thu tiền theo bảng kê' OR LOAICHUNGTU = N'Thu tiền KH trả trước' OR LOAICHUNGTU = N'Thu tiền theo hóa đơn') and SOCHUNGTU IN ( SELECT PK_SEQ FROM ERP_THUTIEN WHERE BANGKE_FK = "+tthdId+" ) ";	
			System.out.println("1.CHECK NO-CO: " + query);
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return  "Khong the huy thu tien ERP_HUYCHUNGTUKETOAN, " + query;
			}			
			
			// XÓA ĐƠN HÀNG
			
			query = "update ERP_THUTIEN set trangthai = '2' WHERE pk_seq IN ( SELECT PK_SEQ FROM ERP_THUTIEN WHERE BANGKE_FK = "+tthdId+" )  ";
			System.out.println("1.CHECK NO-CO: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return  "Khong the huy thu tien ERP_HUYCHUNGTUKETOAN, " + query;
			}			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (SQLException e)
		{ 
			db.shutDown(); 
			return "Khong the xoa ERP_XOAKHTRATRUOC"; 
		}
		
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}

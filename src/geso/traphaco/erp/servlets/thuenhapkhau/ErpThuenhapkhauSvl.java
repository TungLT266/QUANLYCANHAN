package geso.traphaco.erp.servlets.thuenhapkhau;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.masclon.imp.Erp_MaSCLon;
import geso.traphaco.erp.beans.thuenhapkhau.IErpThuenhapkhau;
import geso.traphaco.erp.beans.thuenhapkhau.IErpThuenhapkhauList;
import geso.traphaco.erp.beans.thuenhapkhau.imp.ErpThuenhapkhau;
import geso.traphaco.erp.beans.thuenhapkhau.imp.ErpThuenhapkhauList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpThuenhapkhauSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpThuenhapkhauSvl() {
        super();	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    System.out.println(" user id :" +request.getParameter("userId"));
	    
	    String loaimh = request.getParameter("loaimh");
	    
	    
	    IErpThuenhapkhauList obj = new ErpThuenhapkhauList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    obj.setLoaiMh(loaimh);
	    
	    String action = util.getAction(querystring);
	    String Id = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	
	    	// 	NẾU PHIẾU CHƯA CHỐT THÌ XÓA HẲN TRONG HT 
	    	try
	    	{
		    	db.getConnection().setAutoCommit(false);
		    	if(!db.update("delete ERP_THUENHAPKHAU_HOADONNCC where THUENHAPKHAU_FK = '" + Id + "'"))
		    	{
		    		msg = "D1.1 Không thể xóa";
		    		db.getConnection().rollback();
		    	}
		    	else
		    		if(!db.update("delete ERP_THUENHAPKHAU_CHITIET where THUENHAPKHAU_FK = '" + Id + "'"))
			    	{
			    		msg = "D1.2 Không thể xóa";
			    		db.getConnection().rollback();
			    	}
			    	else
		    		if(db.updateReturnInt("delete ERP_THUENHAPKHAU where PK_SEQ = '" + Id + "' and trangthai=0")!=1)
			    	{
			    		msg = "D1.3 Không thể xóa";
			    		db.getConnection().rollback();
			    	}
		    		else
		    			db.getConnection().commit();
	    	}catch (Exception e) {
				e.printStackTrace();
			}finally
			{
				db.shutDown();
			}
			GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			obj.init("");
	  	    obj.setMsg(msg);
	  		session.setAttribute("obj", obj);
	  	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp";
	  		response.sendRedirect(nextJSP);
	  		return;
	    }else if(action.trim().equals("chotthue"))
	    {
	    	msg = ChotThue_NhapKhau(Id);
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    	obj.init("");
	  	    obj.setMsg(msg);
	  		session.setAttribute("obj", obj);
	  	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp";
	  		response.sendRedirect(nextJSP);
	    }
	    
	    else if(action.trim().equals("chotVAT"))
	    {
	    	msg = ChotThue_VAT(Id);
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    	obj.init("");
	  	    obj.setMsg(msg);
	  		session.setAttribute("obj", obj);
	  	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp";
	  		response.sendRedirect(nextJSP);
	    }
	    else{
	   
	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    obj.init("");
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp";
		response.sendRedirect(nextJSP);
	    }
	}
	

	private String ChotThue_NhapKhau(String Id) 
	{
		String msg = "";
		
		dbutils db = new dbutils();
		
		Utility util = new Utility();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query =   " select  ct.CHIPHI_FK ,ISNULL((cast(tkSp.PK_SEQ as nvarchar(50))), isNull(cast(msc.TAIKHOAN_FK as nvarchar(50)), '')) as sohieutaikhoanNO_DS , " +
							 " (select pk_seq from erp_Taikhoankt where sohieutaikhoan= ncp.taikhoan_fk and congty_fk=cqt.congty_fk) as taikhoannocp_fk ,  \n" + 
							 "	  isNull(( select cast(taikhoan_fk as nvarchar(50)) from ERP_NHACUNGCAP where pk_seq = CQT.Coquanthue_FK ), '') as sohieutaikhoanCO_DS, \n" +
							 "		CQT.NGAY AS NGAYHOADON, THUENHAPKHAU as THUENK, CQT.Coquanthue_FK as NCC_FK \n" +
							 "		,case when ct.taisan_FK IS not  NULL then 'ts' else 'sp' end as loaiHH\n" +
							 ", isNull(cast(ts.PK_SEQ as nvarchar(50)), '') as idTaiSan, isNull(ts.isDaThanhLy, 0) as isDaThanhLy," +
							 " isNull(cast(msc.PK_SEQ as nvarchar(50)),'') as maSC,ISNULL(CQT.DIENGIAI,'') AS DIENGIAI \n" + 
							 "from ERP_THUENHAPKHAU CQT \n" +
							 "inner join ERP_THUENHAPKHAU_CHITIET ct on ct.THUENHAPKHAU_FK = CQT.PK_SEQ\n" +
							 "left join erp_sanpham sp on sp.PK_SEQ = ct.SANPHAM_FK and sp.trangThai = 1 and sp.TRANGTHAI = 1\n" +
							 "left join ERP_LOAISANPHAM lsp on lsp.PK_SEQ = sp.LOAISANPHAM_FK and lsp.TRANGTHAI = 1\n" +
							 "left join ERP_TAIKHOANKT tkSp on tkSp.SOHIEUTAIKHOAN = lsp.TAIKHOANKT_FK and tkSp.CONGTY_FK = CQT.congTy_FK and tkSp.TRANGTHAI = 1\n" +
							 "left join ERP_MASCLON msc on msc.PK_SEQ = ct.TAISAN_FK and msc.trangThai = 1 \n " +
							 " left join ERP_NHOMCHIPHI ncp on ncp.PK_SEQ=ct.CHIPHI_FK \n" +
							 "left join ERP_TAISANCODINH ts on ts.PK_SEQ = msc.TAISAN_FK\n" +
							 "where CQT.PK_SEQ = '" + Id + "'\n";
			
			System.out.println("____KE TOAN: \n" + query + "\n---------------------------------------------------");
			String taikhoanCO_DS = "";
			String taikhoanNO_DS = "";
			
//			String taikhoanCO_VAT = "";
//			String taikhoanNO_VAT = "";
			
			String loaidoituongCO_DS = "";
			String madoituongCO_DS = "";
			
			String loaidoituongNO = "";
			String madoituongNO = "";
			
			ResultSet rsTk = db.get(query);
			while(rsTk.next())
			{
				double totalDS = Math.round(rsTk.getDouble("THUENK"));
				String ngayghinhan = rsTk.getString("ngayhoadon");
				String dienGiai = rsTk.getString("DIENGIAI");
				String loaiHH = rsTk.getString("loaiHH");
//				String spId = rsTk.getString("spId");
				if (loaiHH.endsWith("ts"))
				{
					String maSC = rsTk.getString("maSC");
					//Thêm điều chỉnh cho mã sửa chữa lớn
					String result = Erp_MaSCLon.InsertDieuChinhMSCL
					(db, maSC, ngayghinhan, Double.toString(totalDS)
					, Id, "Thuế nhập khẩu", "ERP_THUENHAPKHAU");

					if (result.trim().length() > 0)
					{
						msg = "CTNK1.1 Không thể chốt thuế nhập khẩu \n" + result;
						db.getConnection().rollback();
						return msg;
					}
				 
				}
				
				if(rsTk.getString("chiphi_fk")!=null){
					taikhoanNO_DS = rsTk.getString("taikhoannocp_fk");
				}else{
					taikhoanNO_DS = rsTk.getString("sohieutaikhoanNO_DS");
				}
				
				taikhoanCO_DS = rsTk.getString("sohieutaikhoanCO_DS");
				String nam = ngayghinhan.substring(0, 4);
				String thang = ngayghinhan.substring(5, 7);
				
				String tiente_fk = "100000";
				
				loaidoituongNO = "";
				madoituongNO = "";
				
				loaidoituongCO_DS = "Nhà cung cấp";
				madoituongCO_DS = rsTk.getString("NCC_FK");
				
				System.out.println("tai khoản có ds: "+taikhoanCO_DS);
				
				if(totalDS > 0)
				{
					if((taikhoanCO_DS != null && taikhoanCO_DS.trim().length() <= 0) || (taikhoanNO_DS != null && taikhoanNO_DS.trim().length() <= 0 ))
					{
						msg = "CCPNK1.3 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						db.getConnection().rollback();
						return msg;
					}
					
					msg = util.Update_TaiKhoan( db, thang, nam, ngayghinhan, ngayghinhan, "Thuế nhập khẩu", Id, taikhoanNO_DS, taikhoanCO_DS, "", 
									Double.toString(totalDS), Double.toString(totalDS), loaidoituongNO, madoituongNO, loaidoituongCO_DS, madoituongCO_DS, "0", "", "", tiente_fk, "", "1", Double.toString(totalDS), Double.toString(totalDS), "Thuế nhập khẩu",dienGiai );
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						System.out.println("loi chot: " + msg);
						return msg;
					}
				}
			}
			rsTk.close();
			
			
			query = "update ERP_THUENHAPKHAU set TRANGTHAI = '1' where PK_SEQ = '" + Id + "' and trangthai=0";
			if(db.updateReturnInt(query)!=1)
	    	{
				db.getConnection().rollback();
	    		msg = "Không thể chot ERP_THUENHAPKHAU " + query;
	    		return msg;
	    	}
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return "CCPNK1.4 Lỗi chốt thuế nhập khẩu " + e.getMessage();
		}
    	return msg;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    if(userId==null){
	    	userId=(String)session.getAttribute("userId");
	    }
	    System.out.println(" user id :" +userId );
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String loaimh= request.getParameter("loaimh");
	    IErpThuenhapkhauList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpThuenhapkhau tnk = new ErpThuenhapkhau();
    		tnk.setCongtyId(ctyId);
    		tnk.setUserId(userId);
    		tnk.setLoaiMh(loaimh);
    		tnk.setnppdangnhap(util.getIdNhapp(userId));
    		
    		tnk.createRs();
    		
	    	session.setAttribute("tnkBean", tnk);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThueNhapKhauNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpThuenhapkhauList();
	    	obj.setCongtyId(ctyId);
	    	obj.setLoaiMh(loaimh);
	    	
		    obj.setUserId(userId);
		    obj.setnppdangnhap(util.getIdNhapp(userId));
		    System.out.println(" loaimh:" +loaimh );
		    
	    	 this.getSearchQuery(request, obj);
	    	
	    	obj.init("");
	    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp");	
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpThuenhapkhauList obj) 
	{	
		
		String sochungtu = request.getParameter("sochungtu");
		if(sochungtu == null)
			sochungtu = "";
		obj.setId(sochungtu);
		
		String sotokhai = request.getParameter("sotokhai");
		if(sotokhai == null)
			sotokhai = "";
		obj.setSotokhai(sotokhai);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		String tungayTK = request.getParameter("tungayTK");
		if(tungayTK == null)
			tungayTK = "";
		obj.setTungay(tungayTK);
		
		String denngayTK = request.getParameter("denngayTK");
		if(denngayTK == null)
			denngayTK = "";
		obj.setDenngay(denngayTK);
		
		String poId = request.getParameter("poId");
		if(poId == null)
			poId = "";
		obj.setPoId(poId);

		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNcc(ncc);
		
		String nccId = request.getParameter("nccId");
		if(nccId == null)
			nccId = "";
		obj.setNccId(nccId);

		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		  	/*"SELECT MH.sopo, TNK.PK_SEQ AS TNKID, TNK.DIENGIAI,TNK.NGAYCHUNGTU, TNK.SOCHUNGTU, TNK.TRANGTHAI, " +
						"NV1.TEN AS NGUOITAO, TNK.NGAYTAO, NV2.TEN AS NGUOISUA, TNK.NGAYSUA  " +
						"FROM ERP_THUENHAPKHAU TNK " +
						"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TNK.NCC_FK " +
						"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ =TNK.donmuahang_fk " +
						"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = TNK.NGUOITAO " +
						"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = TNK.NGUOISUA " +
						"WHERE 1 = 1 ";*/
//		String	 sql =	"\n SELECT TNK.PK_SEQ AS TNKID, TNK.DIENGIAI,  TNK.NGAYCHUNGTU,TNK.SOCHUNGTU, TNK.TRANGTHAI, " +
//						"\n NV1.TEN AS NGUOITAO, TNK.NGAYTAO, NV2.TEN AS NGUOISUA, TNK.NGAYSUA, isnull(MH.SOPO,'') " +
//						"\n FROM ERP_THUENHAPKHAU TNK" +
//						"\n INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TNK.NCC_FK   " +
//						"\n left join  ERP_MUAHANG MH ON MH.PK_SEQ = TNK.DONMUAHANG_FK   " +
//						"\n INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = TNK.NGUOITAO   " +
//						"\n INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = TNK.NGUOISUA    " +
//						"\n  WHERE 1 = 1 AND TNK.CONGTY_FK  ="+obj.getCongtyId();
		/*sql = 	"SELECT TNK.PK_SEQ AS TNKID, TNK.DIENGIAI, TNK.NGAYCHUNGTU , TNK.SOCHUNGTU , TNK.TRANGTHAI, " +
		"NV1.TEN AS NGUOITAO, TNK.NGAYTAO, NV2.TEN AS NGUOISUA, TNK.NGAYSUA " +
		"FROM ERP_THUENHAPKHAU TNK " +
		"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TNK.NCC_FK " +
		"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = TNK.NGUOITAO " +
		"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = TNK.NGUOISUA " +
		" WHERE 1 = 1 ";*/
		
//		if(obj.getnppdangnhap()!=null){
//			sql+="\n AND TNK.NPP_FK = "+obj.getnppdangnhap();
//		}
//		
//		
//		if(tungayTK.length() > 0)
//			sql += "\n and TNK.NGAYCHUNGTU >= '" + tungayTK + "' ";
//		if(denngayTK.length() > 0)
//			sql += "\n and TNK.NGAYCHUNGTU <= '" + denngayTK + "' ";
//		if(sotokhai.length() > 0)
//			sql += "\n and cast(TNK.SOCHUNGTU as varchar(20))  like N'%" + sotokhai + "%'";
//		if(sochungtu.length() > 0)
//			sql += "\n and cast(TNK.PK_SEQ as varchar(20))  like N'%" + sochungtu + "%'";
//		if(sohoadon.length() > 0)
//			sql += "\n and (isnull(TNK.hoadonncc,-1) in (select pk_seq from ERP_HOADONNCC where sohoadon  like N'%" + sohoadon + "%')" +
//					"\n OR ISNULL(TNK.SOHOADON,'') like N'%"+ sohoadon + "%')";
//		
//		if(nccId.length() > 0)
//			sql += "\n and NCC.PK_SEQ = '" + nccId + "' ";
//		
//		if(poId.length() > 0)
//			sql += "\n and MH.SOPO like N'%" + poId + "%'";
//		
//		if(diengiai.length() > 0)
//			sql += "\n and TNK.DIENGIAI like N'%" + diengiai + "%' ";
//		
//		if(trangthai.length() > 0)
//			sql += "\n and TNK.TRANGTHAI = '" + trangthai + "' ";
//		
//		sql += "\n ORDER BY TNK.PK_SEQ DESC ";
//		
//		System.out.println(" \n search thue nhap khau = \n"+sql +"\n");
//		return sql;
	}
	
	private String ChotThue_VAT(String Id) 
	{
		String msg = "";
		
		dbutils db = new dbutils();
		
		Utility util = new Utility();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query =  "select (select pk_seq from ERP_TAIKHOANKT  where SOHIEUTAIKHOAN = '13312000' and trangThai = 1 and congTy_FK = CQT.congTy_FK) as sohieutaikhoanNO_VAT,  \n" + 
			 				"(select pk_seq from ERP_TAIKHOANKT  where SOHIEUTAIKHOAN = '33312000' and trangThai = 1 and congTy_FK = CQT.congTy_FK) as sohieutaikhoanCO_VAT, \n" + 
			 				"		CQT.NGAY AS NGAYHOADON, CQT.VAT,ISNULL(CQT.DIENGIAI,'') AS DIENGIAI \n" + 
			 				"from ERP_THUENHAPKHAU CQT \n" + 
			 				"where PK_SEQ = '" + Id + "' \n"  ;
			
			System.out.println("____KE TOAN: \n" + query + "\n------------------------------------------------");
			
			String taikhoanCO_VAT = "";
			String taikhoanNO_VAT = "";
						
			ResultSet rsTk = db.get(query);
			while(rsTk.next())
			{
				double totalVAT = rsTk.getDouble("VAT");
								
				taikhoanCO_VAT = rsTk.getString("sohieutaikhoanCO_VAT");
				taikhoanNO_VAT = rsTk.getString("sohieutaikhoanNO_VAT");
				String dienGiai = rsTk.getString("DIENGIAI");
				String ngayghinhan = rsTk.getString("ngayhoadon");
				String nam = ngayghinhan.substring(0, 4);
				String thang = ngayghinhan.substring(5, 7);
				
				String tiente_fk = "100000";
								
				if(totalVAT > 0)
				{
					if(taikhoanCO_VAT.trim().length() <= 0 || taikhoanNO_VAT.trim().length() <= 0 )
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						db.getConnection().rollback();
						return msg;
					}
					
					msg = util.Update_TaiKhoan( db, thang, nam, ngayghinhan, ngayghinhan, "Thuế VAT nhập khẩu", Id, taikhoanNO_VAT, taikhoanCO_VAT, "", 
									Double.toString(totalVAT), Double.toString(totalVAT), "", "", "", "", "0", "", "", tiente_fk, "", "1", Double.toString(totalVAT), Double.toString(totalVAT), "VAT",dienGiai );
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;	
					}
				}
			
			}
			rsTk.close();
			
			
			query = "update ERP_THUENHAPKHAU set TRANGTHAI = '2' where PK_SEQ = '" + Id + "' and trangthai=1";
			if(db.updateReturnInt(query)!=1)
	    	{
				db.getConnection().rollback();
	    		msg = "Khong the chot ERP_THUENHAPKHAU " + query;
	    		return msg;
	    	}
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return "Lỗi chốt thuế VAT " + e.getMessage();
		}

    	return msg;
	}
}
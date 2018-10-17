package geso.traphaco.distributor.servlets.dontrahang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.distributor.beans.dontrahang.IErpNhaphangtrave;
import geso.traphaco.distributor.beans.dontrahang.IErpNhaphangtraveList;
import geso.traphaco.distributor.beans.dontrahang.imp.ErpNhaphangtrave;
import geso.traphaco.distributor.beans.dontrahang.imp.ErpNhaphangtraveList;
import geso.traphaco.distributor.db.sql.dbutils;


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

public class ErpNhaphangtraveSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public ErpNhaphangtraveSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhaphangtraveList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String action = util.getAction(querystring);
		
		String lsxId = util.getId(querystring);
		obj = new ErpNhaphangtraveList();
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		obj.setUserId(userId);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
	    obj.setLoaidonhang(loaidonhang);
	    
		if (action.equals("delete"))
		{
			String msg = this.DeleteChuyenKho(lsxId, userId);
			obj.setMsg(msg);	
		} 
		else if (action.equals("chot"))
		{
			String msg = this.Chot(lsxId, userId);
			obj.setMsg(msg);
		} 
		
		obj.init("");
		session.setAttribute("obj", obj);
		
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVe.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String Chot(String id, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		String ngaychot = "";
		String nppId = "";
		Utility util = new Utility();
		msg= util.Check_Huy_NghiepVu_KhoaSo("ErpNhanHang", id, "NgayNhan", db);
		if(msg.length()>0)
			return msg;

		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "    select a.NGAYCHOT, 100000 as nhomkenhId, a.NHAPHANPHOI_FK, SANPHAM_FK, KHO_FK, SOLO, NGAYHETHAN, SOLUONG " +
						   "	from ERP_NHANHANG a inner join ERP_NHANHANG_SP_CHITIET b on a.PK_SEQ = b.NHANHANG_FK  " +
						   "	where a.PK_SEQ = '" + id + "'";
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				ngaychot = rs.getString("NGAYCHOT");
				String nhomkenhId = rs.getString("nhomkenhId");
				nppId = rs.getString("NHAPHANPHOI_FK");
				String spId = rs.getString("SANPHAM_FK");
				String khoId = rs.getString("KHO_FK");
				String SOLO = rs.getString("SOLO");
				String NGAYHETHAN = rs.getString("NGAYHETHAN");
				String SOLUONG = rs.getString("SOLUONG");
				
				query = "select count(*) as soDONG from NHAPP_KHO " + 
						" where NPP_FK = '" + nppId + "' and nhomkenh_fk = '" + nhomkenhId + "' and sanpham_fk = '" + spId + "' and kho_fk = '" + khoId + "' ";
				ResultSet rsCHECK = db.get(query);
				
				int soDONG = 0;
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("soDONG");
					rsCHECK.close();
				}
				
				if(soDONG <= 0 )
				{
					query = "insert NHAPP_KHO( KHO_FK, NPP_FK, SANPHAM_FK, nhomkenh_fk, soluong, BOOKED, AVAILABLE ) " +
							"values( '" + khoId + "', '" + nppId + "', '" + spId + "', '" + nhomkenhId + "', '" + SOLUONG + "', '0', '" + SOLUONG + "' ) ";
				}
				else
				{
					query = "UPDATE NHAPP_KHO set soluong = soluong + '" + SOLUONG + "', AVAILABLE = AVAILABLE + '" + SOLUONG + "' " + 
							"where KHO_FK = '" + khoId + "' and NPP_FK = '" + nppId + "' and SANPHAM_FK = '" + spId + "' and nhomkenh_fk = '" + nhomkenhId + "'  ";
				}
				
				if(db.updateReturnInt(query)!=1)
				{
					msg = "1.Khong the chot: " + query;
					db.getConnection().rollback();
					return msg;
				}
				
				
				//KHO CHI TIET
				query = "select count(*) as soDONG from NHAPP_KHO_CHITIET " + 
						" where NPP_FK = '" + nppId + "' and nhomkenh_fk = '" + nhomkenhId + "' and sanpham_fk = '" + spId + "' and kho_fk = '" + khoId + "' and SOLO = '" + SOLO + "' and NGAYHETHAN = '" + NGAYHETHAN + "' ";
				rsCHECK = db.get(query);
				
				soDONG = 0;
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("soDONG");
					rsCHECK.close();
				}
				
				if(soDONG <= 0 )
				{
					query = "insert NHAPP_KHO_CHITIET( KHO_FK, NPP_FK, SANPHAM_FK, nhomkenh_fk, soluong, BOOKED, AVAILABLE, SOLO, NGAYHETHAN ) " +
							"values( '" + khoId + "', '" + nppId + "', '" + spId + "', '" + nhomkenhId + "', '" + SOLUONG + "', '0', '" + SOLUONG + "', '" + SOLO + "', '" + NGAYHETHAN + "' ) ";
				}
				else
				{
					query = "UPDATE NHAPP_KHO_CHITIET set soluong = soluong + '" + SOLUONG + "', AVAILABLE = AVAILABLE + '" + SOLUONG + "' " + 
							"where KHO_FK = '" + khoId + "' and NPP_FK = '" + nppId + "' and SANPHAM_FK = '" + spId + "' and nhomkenh_fk = '" + nhomkenhId + "' and SOLO = '" + SOLO + "' and NGAYHETHAN = '" + NGAYHETHAN + "' ";
				}
				
				if(db.updateReturnInt(query)!=1)
				{
					msg = "2.Khong the chot: " + query;
					db.getConnection().rollback();
					return msg;
				}
				
			}
			rs.close();

			
			// CÀI KẾ TOÁN-------------------- Tiền vốn & Tiền hàng trả lại
			
			Utility_Kho util_kho = new Utility_Kho();
			geso.traphaco.center.db.sql.dbutils db1 = new geso.traphaco.center.db.sql.dbutils();
			
			String nam = ngaychot.substring(0, 4);
			String thang = ngaychot.substring(5, 7);
						
			//KE TOAN TU DONG PHAT SINH
						 
			int namOLD = Integer.parseInt(nam);
			int thangOLD = Integer.parseInt(thang);
					
			if(thangOLD == 1)
			{
				thangOLD = 12;
				namOLD = namOLD - 1;
			}
			else
			{
				thangOLD = thangOLD - 1;
			}
			
			query = 	" SELECT DISTINCT a.CONGTY_FK, a.NHAPHANPHOI_FK, a.NCC_KH_FK, b.SANPHAM_FK, b.SOLUONG, c.DONGIA, \n"+
						"        d.TAIKHOAN_FK as taikhoanKH, ( select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = f.TAIKHOANKT_FK and CONGTY_FK = a.CONGTY_FK) as taikhoanLoaiSP,  "+
						"        (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52130000' AND CONGTY_FK = a.CONGTY_FK) taikhoanNo_TienHang, "+
						"        (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63211000' AND CONGTY_FK = a.CONGTY_FK) taikhoanCo_TienVon, b.SOLO,  e.TEN TENSP,c.DONVI, e.MA_FAST, a.KBH_FK, isnull( a.GHICHU, '' ) GHICHU, isnull(a.ISNPP, 0) ISNPP "+			       
						" FROM ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM c on a.PK_SEQ = c.NHANHANG_FK \n"+
						"      inner join ERP_NHANHANG_SP_CHITIET b on a.PK_SEQ = b.NHANHANG_FK  \n"+
						"      inner join KHACHHANG d on a.NCC_KH_FK = d.PK_SEQ  \n"+
						"      inner join SANPHAM e on b.SANPHAM_FK = e.PK_SEQ  \n"+
						"      inner join ERP_LOAISANPHAM f on e.LOAISANPHAM_FK = f.PK_SEQ  \n"+
						" WHERE a.PK_SEQ = '" + id + "'";
			
			System.out.println(query);
			ResultSet rsKT = db.get(query);
			
			while(rsKT.next())
			{
				String taikhoanktNo = "";
				String taikhoanktCo = "";
				
				String doituongno = "";
				String madoituongno = "";
				String doituongco = "";
				String madoituongco = "";
				
				String masp = "";
				String tensp = "";
				String donvi = "";
				
				String kbh_fk = "";
				String ghichu = "";
				String isNPP = "";
				
				double giaTonTH = 0;
				String[] str=util_kho.getGiaChayKT(ngaychot, db1, rsKT.getString("CONGTY_FK"), rsKT.getString("NHAPHANPHOI_FK"), rsKT.getString("SANPHAM_FK"),rsKT.getString("SOLO"));
				
				if(str[1].length()> 0){
					rsKT.close();
					db.getConnection().rollback();
					return str[1];
				}else{
					giaTonTH=Double.parseDouble(str[0]);
				}
				
				double tienhang = rsKT.getDouble("SOLUONG")*rsKT.getDouble("DONGIA");
				double tienvon =  rsKT.getDouble("SOLUONG")*giaTonTH;
				double soluong =  rsKT.getDouble("SOLUONG");
				
				masp = rsKT.getString("MA_FAST");
				tensp = rsKT.getString("TENSP");
				donvi = rsKT.getString("DONVI");
				kbh_fk = rsKT.getString("KBH_FK");
				ghichu = rsKT.getString("GHICHU");
				isNPP = rsKT.getString("isNPP");
								
				if(tienhang > 0)
				{
					doituongco = "Khách hàng";
					madoituongco = rsKT.getString("NCC_KH_FK");
					
					taikhoanktNo = rsKT.getString("taikhoanNo_TienHang")== null ?"": rsKT.getString("taikhoanNo_TienHang");
					taikhoanktCo = rsKT.getString("taikhoanKH")== null ?"": rsKT.getString("taikhoanKH");
					
					if(taikhoanktNo.trim().length() <= 0 || taikhoanktCo.trim().length() <= 0)
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						db.getConnection().rollback();
						return msg;
					}
					
					msg = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thang, nam, ngaychot, ngaychot, "Nhập hàng trả về từ KH", id, taikhoanktNo, taikhoanktCo, "", 
								Double.toString(tienhang), Double.toString(tienhang), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), "", "100000", "", "1", Double.toString(tienhang), 
								Double.toString(tienhang), "Nhập hàng trả về từ KH - Tiền hàng", "0" , ghichu , id ,isNPP ,masp , tensp, donvi, kbh_fk);
					
					/*msg = util.Update_TaiKhoan_SP(db, thang, nam, ngaychot, ngaychot, "Nhập hàng trả về từ KH", id, taikhoanktNo, taikhoanktCo, "",
							   Double.toString(tienhang), Double.toString(tienhang), doituongno, madoituongno, doituongco, madoituongco , "0", "", "", "100000", "", "1",  Double.toString(tienhang), Double.toString(tienhang) , "Nhập hàng trả về từ KH - Tiền hàng", masp, tensp, donvi) ;
*/
					if(msg.trim().length() > 0)
					{
						rsKT.close();
						db.getConnection().rollback();
						return msg;
					}
					
					
				}
				
				//TẠM THỜI BỎ CHECK TIENVON > 0
				/*if(tienvon > 0)
				{*/
					doituongno = "Sản phẩm";
					madoituongno = rsKT.getString("SANPHAM_FK");
					
					taikhoanktNo = rsKT.getString("taikhoanLoaiSP")== null ?"": rsKT.getString("taikhoanLoaiSP");
					taikhoanktCo = rsKT.getString("taikhoanCo_TienVon")== null ?"": rsKT.getString("taikhoanCo_TienVon");
					
					if(taikhoanktNo.trim().length() <= 0 || taikhoanktCo.trim().length() <= 0)
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						db.getConnection().rollback();
						return msg;
					}
					
					query=	" update ERP_NHANHANG_SP_CHITIET SET GIACHAYKT = "+giaTonTH+" , taikhoanktCO= "+taikhoanktCo+" ,taikhoanktNo ="+taikhoanktNo+" " +
							" where SANPHAM_FK="+ rsKT.getString("SANPHAM_FK")+" AND NHANHANG_FK ="+id+" AND SOLO='"+rsKT.getString("SOLO")+"'";
					if(db.updateReturnInt(query)!=1){
						db.getConnection().rollback();
						return "Lỗi dòng lệnh :"+query;
					}
			
					msg = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thang, nam, ngaychot, ngaychot, "Nhập hàng trả về từ KH", id, taikhoanktNo, taikhoanktCo, "", 
							Double.toString(tienvon), Double.toString(tienvon), doituongno, madoituongno, doituongco, madoituongco, "0", rsKT.getString("SOLUONG"), "", "100000", "", "1", Double.toString(tienvon), 
							Double.toString(tienvon), "Nhập hàng trả về từ KH - Tiền vốn", "0" , ghichu , id ,isNPP ,masp , tensp, donvi, kbh_fk);
				
					
				/*	msg = util.Update_TaiKhoan_SP(db, thang, nam, ngaychot, ngaychot, "Nhập hàng trả về từ KH", id, taikhoanktNo, taikhoanktCo, "",
							   Double.toString(tienvon), Double.toString(tienvon), doituongno, madoituongno, doituongco, madoituongco , "0", rsKT.getString("SOLUONG"), "", "100000", "", "1",  Double.toString(tienvon), Double.toString(tienvon) , "Nhập hàng trả về từ KH - Tiền vốn", masp, tensp, donvi) ;
*/
					if(msg.trim().length() > 0)
					{
						rsKT.close();
						db.getConnection().rollback();
						return msg;
					}
					
					
				/*}*/
				
			}
			rsKT.close();
			
			
			
			// END----------------------------
			
			msg = util.Check_Kho_Tong_VS_KhoCT(nppId, db);
			if(msg.length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			query = "update ERP_NHANHANG set trangthai = '1', nguoisua = '" + userId + "', ngaysua = getdate() " + 
					" where pk_seq = '" + id + "' and trangthai in( 0 ) ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}

		return "";
	}

	private String DeleteChuyenKho(String id, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";

		Utility util = new Utility();
		msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_NHANHANG", id, "NgayNhap", db);
		if(msg.length()>0)
			return msg;

		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "update ERP_NHANHANG set trangthai = '3', nguoisua = '" + userId + "', ngaysua = getdate() " + 
							" where pk_seq = '" + id + "' and trangthai in( 0 ) ";
			System.out.println("----CAP NHAT: " + query);
			if(db.updateReturnInt(query)!=1)
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}

		return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}

		IErpNhaphangtraveList obj = new ErpNhaphangtraveList();

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);
		obj.setNppId(util.getIdNhapp(userId));

		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
	    obj.setLoaidonhang(loaidonhang);
		
		if (action.equals("Tao moi")) {
			
			IErpNhaphangtrave lsxBean = new ErpNhaphangtrave();
			lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
			lsxBean.setNpp_duocchon_id(npp_duocchon_id);
			lsxBean.setUserId(userId);
			lsxBean.setLoaidonhang(loaidonhang);
			
			lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			lsxBean.setDoituongId(session.getAttribute("doituongId"));
			
			lsxBean.createRs();

			session.setAttribute("lsxBean", lsxBean);
			session.setAttribute("kenhId", "");
			session.setAttribute("khoxuat", "");
			session.setAttribute("nppId", lsxBean.getNppId());

			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVeNew.jsp";
			response.sendRedirect(nextJSP);
		} else {
			if (action.equals("view") || action.equals("next") || action.equals("prev")) {
				obj.setTdv_dangnhap_id(tdv_dangnhap_id);
				obj.setNpp_duocchon_id(npp_duocchon_id);
				String search = getSearchQuery(request, obj);				
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.setUserId(userId);
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);

				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVe.jsp";
				response.sendRedirect(nextJSP);
			} else {
				String search = getSearchQuery(request, obj);
				obj.setTdv_dangnhap_id(tdv_dangnhap_id);
				obj.setNpp_duocchon_id(npp_duocchon_id);
				obj.init(search);
				obj.setUserId(userId);

				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVe.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IErpNhaphangtraveList obj) 
	{
		String query =	 "select a.pk_Seq, a.NGAYNHAN,  " + 
				"	case when a.isNPP = 1 then e.TEN else f.TEN end as khachhang, " + 
				"		c.TEN as nguoiTao, d.TEN as nguoiSua, a.TRANGTHAI, 0 as tonggiatri, a.NGAYTAO, a.NGAYSUA  " + 
				"from ERP_NHANHANG a   " + 
				"	inner join NHANVIEN c on c.PK_SEQ=a.NGUOITAO   " + 
				"	inner join NHANVIEN d on d.PK_SEQ=a.NGUOISUA  " + 
				"	left join NHAPHANPHOI e on e.PK_SEQ = a.NCC_KH_FK  " + 
				"	left join KHACHHANG f on f.pk_seq = a.NCC_KH_FK  " + 
				"where a.NHAPHANPHOI_FK = '" + obj.getNppId() + "'  and a.TRAHANG_FK is not null ";

		String tungaySX = request.getParameter("tungay");
		if (tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);

		String denngaySX = request.getParameter("denngay");
		if (denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);

		String sochungtu = request.getParameter("sochungtu");
		if (sochungtu == null)
			sochungtu = "";
		obj.setSochungtu(sochungtu);

		String trangthai = request.getParameter("trangthai");
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String nppId = request.getParameter("nppId");
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String khId = request.getParameter("khId");
		if (khId == null)
			khId = "";
		obj.setKhId(khId);

		if (tungaySX.length() > 0)
			query += " and a.NGAYNHAN >= '" + tungaySX + "'";

		if (denngaySX.length() > 0)
			query += " and a.NGAYNHAN <= '" + denngaySX + "'";

		if (trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";

		if (sochungtu.length() > 0)
			query += " and a.sohoadon like '%" + sochungtu + "%'";

		if (khId.length() > 0)
			query += " and  a.ncc_kh_fk = '" + khId + "'";

		System.out.print(query);
		return query;

	}

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	
}
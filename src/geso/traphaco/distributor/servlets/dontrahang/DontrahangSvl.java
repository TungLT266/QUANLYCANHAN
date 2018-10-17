package geso.traphaco.distributor.servlets.dontrahang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.distributor.beans.dontrahang.IDontrahang;
import geso.traphaco.distributor.beans.dontrahang.IDontrahangList;
import geso.traphaco.distributor.beans.dontrahang.imp.Dontrahang;
import geso.traphaco.distributor.beans.dontrahang.imp.DontrahangList;
import geso.traphaco.center.db.sql.dbutils;

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

public class DontrahangSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public DontrahangSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDontrahangList obj;
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
		obj = new DontrahangList();
		obj.setUserId(userId);
		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
		
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
		else if (action.equals("UnChot"))
		{
			String msg = this.UnChot(lsxId, userId);
			obj.setMsg(msg);
		}
		
		obj.init("");
		session.setAttribute("obj", obj);
		
		String nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHang.jsp";
		response.sendRedirect(nextJSP);
	}
	
	public String Check_Huy_NghiepVu_KhoaSo(String table, String id, String column, dbutils db)
	{
		String query = " select DATEPART(MONTH,"+column+") as Thang,DATEPART(YEAR,"+column+") as Nam "+
						"from " + table + "  a  "+
						"where PK_SEQ = '" + id + "' and exists  "+
						"(  "+
						"	select 1 from KHOASOTHANG where THANGKS= DATEPART(MONTH,"+column+") "+
						"		and NAM=DATEPART(YEAR," + column + ") and NPP_FK=a.NPP_FK  "+
						")  ";
		String msg = "";
		System.out.println("SQL CHECK:: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
		    {
			    while(rs.next())
			    {
			    	msg= "Bạn không được thực hiện nghiệp vụ trong tháng đã khóa sổ !";
			    }
			    rs.close();
		    } 
			catch (Exception e)
		    {
			    e.printStackTrace();
			    return "Lỗi phát sinh khi check khóa sổ;";
		    }
		}
		return msg;
	}

	
	private String UnChot(String lsxId, String userId)
	{
		return "";
	}

	private String Chot(String id, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		String nppId = "";
		Utility util = new Utility();
		Check_Huy_NghiepVu_KhoaSo("DonTraHang", id, "NgayTra", db);
		
		if(msg.length()>0)
			return msg;

		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";
			/*String loaidonhang = "";
			String npp_tra_fk = "";

			String	query = " select loaidonhang, khachhang_fk, npp_tra_fk " + 
							" from DONTRAHANG  " +
							" where pk_seq = '" + id + "' ";
			ResultSet rsKENH = db.get(query);
			if(rsKENH != null)
			{
				if(rsKENH.next())
				{
					loaidonhang = rsKENH.getString("loaidonhang");
					npp_tra_fk = rsKENH.getString("npp_tra_fk") == null ? "" : rsKENH.getString("npp_tra_fk");
				}
				rsKENH.close();
			}

			// 
			Utility_Kho util_kho=new Utility_Kho();
			query = " 	select a.ngaytra,a.kho_fk as kho_fk, b.nhomkenh_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat,b.NgayHetHan, (SELECT loai FROM KHO WHERE PK_SEQ =  a.kho_fk) as LOAIKHO, a.npp_fk, a.khachhang_fk \n" +
					" 	from DonTraHang a inner join DONTRAHANG_SP b on a.pk_seq = b.dontrahang_fk \n" +
					" 	where dontrahang_fk = '" + id + "' \n" +
					" 	group by a.kho_fk, a.npp_tra_fk, b.nhomkenh_fk, b.solo, b.sanpham_fk,b.NgayHetHan, a.ngaytra, a.npp_fk, a.khachhang_fk ";
			
			ResultSet KhoRs = db.get(query);
			int loaiKHO = 0;
			
			System.out.println(query);
			
			while(KhoRs.next())
			{
				String ngayyeucau = KhoRs.getString("ngaytra");
				String khoxuat = KhoRs.getString("kho_fk");
				String sp = KhoRs.getString("sanpham_fk");
				String solo = KhoRs.getString("solo");
				String ngayhethan = KhoRs.getString("NgayHetHan");
				loaiKHO = KhoRs.getInt("loaiKHO");
				nppId =  KhoRs.getString("npp_fk");
				double soluong = KhoRs.getDouble("tongxuat");
				String nhomkenhId = KhoRs.getString("nhomkenh_fk");
				String khachhang_fk = KhoRs.getString("khachhang_fk");
				
				msg=util_kho.Update_NPP_Kho_Sp(ngayyeucau, "Chuyển kho", db, khoxuat, sp, nppId, nhomkenhId,soluong, 0 , soluong, 0);
				if(msg.length() >0){
					db.getConnection().rollback();
					return msg;
				}
				
				if(loaiKHO == 1|| loaiKHO == 3  ||loaiKHO  == 4 ){			
					
					msg=util_kho.Update_NPP_Kho_Sp_Chitiet(ngayyeucau, "Chuyển kho", db,khoxuat, sp , nppId, nhomkenhId,solo , "", ngayhethan,ngayyeucau, soluong, 0 , soluong, 0);
										
					if(msg.length() >0){
						db.getConnection().rollback();
						return msg;
					}
					
				 }else if(loaiKHO  == 2 || loaiKHO == 7  ){
						
					msg=util_kho.Update_NPP_Kho_Sp_NCC(ngayyeucau, "Chuyển kho", db, khoxuat,  sp,  nppId, nhomkenhId,soluong, 0 , soluong, 0, "");
					
						if(msg.length() >0){
							db.getConnection().rollback();
							return msg;
						}
						
					msg=util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(ngayyeucau, "Chuyển kho", db, khoxuat,  sp,  nppId, nhomkenhId, 
							"", solo,"", ngayhethan,ngayyeucau, soluong, 0 , soluong, 0) ;
					
					if(msg.length() >0){
						db.getConnection().rollback();
						return msg;
					}
					 
						
				}else if(loaiKHO == 5 ||loaiKHO == 8){
					// khach hang
				  
					msg=util_kho.Update_NPP_Kho_Sp_Kygui(ngayyeucau, "Chuyển kho", db, khoxuat,  sp,  
							nppId, nhomkenhId,soluong, 0 , soluong, 0, khachhang_fk); 
					if(msg.length() >0){
						db.getConnection().rollback();
						return msg;
					}
					
					msg=util_kho.Update_NPP_Kho_Sp_Kygui_Chitiet(ngayyeucau, "Chuyển kho", db, khoxuat,  sp,  nppId, nhomkenhId, khachhang_fk, 
							solo, "",ngayhethan,ngayyeucau, soluong, 0 , soluong, 0) ;
					if(msg.length() >0){
						db.getConnection().rollback();
						return msg;
					}
					
					
				}else if(loaiKHO == 6){
					// kho trinh duyet vien

					msg=util_kho.Update_NPP_Kho_Sp_DDKD(ngayyeucau, "Chuyển kho", db, khoxuat,  sp,  
							nppId, nhomkenhId,soluong, 0 , soluong, 0, ""); 
					if(msg.length() >0){
						db.getConnection().rollback();
						return msg;
					}
					msg=util_kho.Update_NPP_Kho_Sp_Chitiet_DDKD(ngayyeucau, "Chuyển kho", db, khoxuat,  sp,  nppId, nhomkenhId, "", solo, 
							"", ngayhethan,ngayyeucau,soluong, 0 , soluong, 0);
					
					if(msg.length() >0){
						db.getConnection().rollback();
						return msg;
					}
				}
				
			}*/
			
			query = "update DonTraHang set trangthai = '1', nguoisua = '" + userId + "', Modified_Date=getdate() where pk_seq = '" + id + "' and trangthai in(0,1) ";
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

	private String TaoHangTraLai_CapTren(String id,dbutils db,String userId ) throws SQLException
	{
		String  msg="";
		String 
		//TAO NHAP HANG CHO DOI TUONG NHAP
		query = 

		"	insert into Erp_HangTraLaiNpp(npp_fk,ngaytra,ghichu,kbh_fk,kho_fk,NgayTao,NgaySua,NguoiTao,NguoiSua,TrangThai,TienTruocThue,TienSauThue,TienThue,NppTra_FK,dontrahang_fk,DoiTuong)  "+
				"	 select tructhuoc_fk,NGAYTRA,N'Chi nhánh trả hàng',KBH_FK,KHO_FK,'' as NgayTao,'' as NgaySua,NGUOITAO,NGUOISUA,0 ,SOTIENBVAT,SOTIENBVAT,VAT,NPP_FK,PK_SEQ,0  "+
				"	 from DONTRAHANG	   a  "+
				"	 where a.PK_SEQ = '"+id+"' and trangthai in (0,1) "; 

		System.out.println("---INSERT ErpHangTraLaiNpp: " + query );
		if(!db.update(query))
		{
			msg = "Không tạo mới NHAPHANG " + query;
			return msg;
		}

		query = "select scope_identity() as pxkId";
		ResultSet	rs = db.get(query);
		rs.next();
		String	pxkId = rs.getString("pxkId");
		rs.close();

		query = 		
				"	insert into Erp_HangTraLaiNpp_SanPham(HangTraLai_fk,Sanpham_fk,Dvdl_Fk,SoLuong,DonGia,SoLo,NgayHetHan,VAT) "+
						"	select "+pxkId+" ,SANPHAM_FK,b.DVDL_FK,a.SOLUONG,a.DONGIA,a.SoLo,a.NgayHetHan,ptVat "+
						"	from DONTRAHANG_SP a inner join sanpham b on b.PK_SEQ=a.SANPHAM_FK" +
						"    inner join  DONTRAHANG c on c.pk_Seq=a.dontrahang_fk "+
						" where  c.pk_Seq='"+id+"' and c.trangthai in (0,1) ";
		System.out.println("---INSERT Erp_HangTraLaiNpp_SanPham: " + query );
		if(!db.update(query))
		{
			msg = "Không tạo mới Erp_HangTraLaiNpp_SanPham " + query;
			return msg;
		}

		query=
				" UPDATE hd SET TienSauThue=data.AVAT,TienTruocThue=data.BVAT,TienThue=data.VAT  "+
						" from   "+
						"	(  "+
						"	select sum(round( ROUND(SoLuong*DonGia,0)*(1+VAT/100 ),0)) as AVAT,SUM(round(SoLuong*DonGia,0)) as BVAT,SUM(round(ROUND(SoLuong*DonGia,0)*(VAT/100),0)) as VAT ,HangTraLai_fk from Erp_HangTraLaiNpp_SanPham  "+ 
						"	group by HangTraLai_fk   "+
						" ) as data inner join Erp_HangTraLaiNpp hd on hd.pk_seq=data.HangTraLai_fk " +
						"  where data.HangTraLai_fk='"+pxkId+"'   ";
		if(!db.update(query))
		{
			msg = "Không tạo mới Erp_HangTraLaiNpp_SanPham " + query;
			return msg;
		}

		return msg;
	}
	
	private String TaoHangTraLai_HO(String id,String userId,dbutils  db )
	{
		String msg="";
		String query=
				"	insert into ERP_NHAPKHO(NgayNhap,KhoNhap_FK,TRANGTHAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,GHICHU,DonTraHang_fk,SoChungTu ) "+
						"	select NGAYTRA,100001,0,'"+getDateTime()+"' as NgayTao,'"+getDateTime()+"' as nsua,NGUOITAO,NGUOISUA,N'Xuất trả Hàng',PK_SEQ,SoHoaDon "+
						"	from DonTraHang where TrucThuoc_FK =1 and pk_Seq='"+id +"' "; 
		if(!db.update(query))
		{
			msg = "Không tạo mới ERP_NHAPKHO " + query;
			return msg;
		}

		query = "insert ERP_NHAPKHO_SANPHAM( nhapkho_fk, SANPHAM_FK, DVDL_FK, soluong, gianhap, solo, ngaysanxuat, ngayhethan ) "+
				"select SCOPE_IDENTITY(),a.pk_Seq as spId,DVDL_FK,soluong as SoLuong,0 as GiaNhap,b.solo as SoLo,  "+
				"	b.ngayhethan as NgaySanXua,b.ngayhethan as NgayHetHan  "+
				"from SANPHAM a inner join DonTraHang_SP b on a.PK_SEQ=b.SANPHAM_FK "+ 
				" where b.dontrahang_Fk='"+id+"' ";

		System.out.println("_ERP_NHAPKHO_SANPHAM"+query);

		if(!db.update(query))
		{
			msg = "Không tạo mới ERP_NHAPKHO_SANPHAM " + query;
			return msg;
		}

		return msg;
	}
	
	private String DeleteChuyenKho(String lsxId, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";

		Utility util = new Utility();
		Check_Huy_NghiepVu_KhoaSo("DonTraHang", lsxId, "NgayTra", db);
		if(msg.length()>0)
			return msg;

		try
		{
			db.getConnection().setAutoCommit(false);

			String loaidonhang = "";
			String npp_tra_fk = "";

			String	query = " select loaidonhang, khachhang_fk, npp_tra_fk " + 
							" from DONTRAHANG  " +
							" where pk_seq = '" + lsxId + "' ";
			ResultSet rsKENH = db.get(query);
			if(rsKENH != null)
			{
				if(rsKENH.next())
				{
					loaidonhang = rsKENH.getString("loaidonhang");
					npp_tra_fk = rsKENH.getString("npp_tra_fk") == null ? "" : rsKENH.getString("npp_tra_fk");
				}
				rsKENH.close();
			}

			query = "update DonTraHang set trangthai = '3', nguoisua = '" + userId + "', Modified_Date=getdate() where pk_seq = '" + lsxId + "' and trangthai in(0,1) ";
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

		IDontrahangList obj = new DontrahangList();
		
		Utility util = new Utility();

		HttpSession session = request.getSession();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);
		obj.setNppId(util.getIdNhapp(userId));

		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
	    obj.setLoaidonhang(loaidonhang);
		
		if (action.equals("Tao moi")) {
			
			IDontrahang lsxBean = new Dontrahang();
			lsxBean.setUserId(userId);
			lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
			lsxBean.setNpp_duocchon_id(npp_duocchon_id);
			
			lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			lsxBean.setDoituongId(session.getAttribute("doituongId"));
			lsxBean.setLoaidonhang(loaidonhang);
			lsxBean.createRs();

			session.setAttribute("lsxBean", lsxBean);
			session.setAttribute("kenhId", "");
			session.setAttribute("khoxuat", "");
			session.setAttribute("nppId", lsxBean.getNppId());

			String nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHangNew.jsp";
			response.sendRedirect(nextJSP);
		} else {
			if (action.equals("view") || action.equals("next") || action.equals("prev")) {
				String search = getSearchQuery(request, obj);				
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.setTdv_dangnhap_id(tdv_dangnhap_id);
				obj.setNpp_duocchon_id(npp_duocchon_id);
				obj.setUserId(userId);
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);

				String nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			} else {
				String search = getSearchQuery(request, obj);
				obj.setTdv_dangnhap_id(tdv_dangnhap_id);
				obj.setNpp_duocchon_id(npp_duocchon_id);
				obj.init(search);
				obj.setUserId(userId);

				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Distributor/DonTraHang.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IDontrahangList obj) {
		String query = "	select a.pk_Seq,b.MA as nppMa,b.TEN as nppTen,a.NGAYTRA,c.TEN as nguoiTao,d.TEN as nguoiSua, isnull(e.TEN, f.ten) as tructhuoc,a.TRANGTHAI,a.SOTIENBVAT,a.Modified_Date,a.created_date "
				+ "		from DONTRAHANG a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "
				+ "		inner join NHANVIEN c on c.PK_SEQ=a.NGUOITAO  "
				+ "		inner join NHANVIEN d on d.PK_SEQ=a.NGUOISUA "
				+ "		left join NHAPHANPHOI e on e.PK_SEQ=a.NPP_TRA_FK "
				+ "		left join KHACHHANG f on f.pk_seq = a.KHACHHANG_FK " 
				+ " where a.npp_fk='" + obj.getNppId() + "' and a.loaidonhang = '" + obj.getLoaidonhang() + "' ";

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
			query += " and a.ngaytra >= '" + tungaySX + "'";

		if (denngaySX.length() > 0)
			query += " and a.ngaytra <= '" + denngaySX + "'";

		if (trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";

		if (sochungtu.length() > 0)
			query += " and a.sohoadon like '%" + sochungtu + "%'";

		if (khId.length() > 0)
			query += " and  a.tructhuoc_fk = '" + khId + "'";

		if (nppId.length() > 0)
			query += " and a.NPP_FK = '" + nppId + "'";

		System.out.print(query);
		return query;

	}

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	
}
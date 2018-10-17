package geso.traphaco.erp.beans.taikhoankt.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.taikhoankt.ITaikhoankt;
import geso.traphaco.erp.util.UtilitySyn;

public class Taikhoankt implements ITaikhoankt
{

	String Id, UserId, SoHieuTaiKhoan, TenTaiKhoan, CongTyId, TtcpId, LoaiTaiKhoanId, TrangThai, TaiKhoanCoChiTiet,
	TaiKhoanCoChiPhi, msg;
	dbutils db;
	ResultSet CongTyRs , LoaiTaiKhoanRs,loaiTKBaoCaoRs;
	String dungchoKho ;
	String dungchoNganhang ;
	String dungchoNCC, dungchonhanvien ;
	String dungchoTaisan ;
	String dungchoKhachhang ;
	String dungChoDanhMucDuAn;
	String dungChoDoiTuongKhac;
	String loaiTKBaoCao;
	String dungchoMSCL,dungchoCPTT;
	public String getDungchoMSCL() {
		return dungchoMSCL;
	}

	public void setDungchoMSCL(String dungchoMSCL) {
		this.dungchoMSCL = dungchoMSCL;
	}

	public String getDungchoCPTT() {
		return dungchoCPTT;
	}

	public void setDungchoCPTT(String dungchoCPTT) {
		this.dungchoCPTT = dungchoCPTT;
	}
	
	public Taikhoankt()
	{
		this.db = new dbutils();
		this.UserId = "";
		this.SoHieuTaiKhoan = "";
		this.TenTaiKhoan = "";
		this.loaiTKBaoCao="";
		this.CongTyId = "";
		this.LoaiTaiKhoanId = "";
		this.TrangThai = "1";
		this.TaiKhoanCoChiPhi = "";
		this.TaiKhoanCoChiTiet = "";
		this.dungchoKho = "";
		this.dungchoNganhang = "";
		this.dungchoNCC = "";
		this.dungchoTaisan = "";
		this.dungchoKhachhang = "";
		this.dungchonhanvien = "";
		this.dungChoDanhMucDuAn = "";
		this.dungChoDoiTuongKhac = "";
		this.dungchoCPTT="";
		this.dungchoMSCL="";
		this.msg = "";
	}
	
	public Taikhoankt(String id)
	{
		this.db = new dbutils();
		this.Id = id;
		this.UserId = "";
		this.SoHieuTaiKhoan = "";
		this.TenTaiKhoan = "";
		this.CongTyId = "";
		this.TtcpId = "";
		this.LoaiTaiKhoanId = "";
		this.TrangThai = "1";
		this.TaiKhoanCoChiPhi = "";
		this.TaiKhoanCoChiTiet = "";
		this.dungchoKho = "";
		this.dungchoNganhang = "";
		this.dungchoNCC = "";
		this.dungchoTaisan = "";
		this.dungchoKhachhang = "";
		this.dungchonhanvien = "";
		this.dungChoDanhMucDuAn = "";
		this.dungChoDoiTuongKhac = "";
		this.msg = "";
		this.dungchoCPTT="";
		this.dungchoMSCL="";
		this.loaiTKBaoCao="";
	}

	

	public void init()
	{
		String query = 	"SELECT  SOHIEUTAIKHOAN, TENTAIKHOAN, LOAITAIKHOAN_FK, TAIKHOANCOCHITIET\n" +
						", TRANGTHAI, CONGTY_FK, COTTCHIPHI, DUNGCHOKHO\n" +
						", DUNGCHONGANHANG, DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG\n" +
						", DUNGCHONHANVIEN, ISNULL(dungChoDanhMucDuAn, 0) AS dungChoDanhMucDuAn, isnull(dungChoDoiTuongKhac, 0) as dungChoDoiTuongKhac ,ISNULL(LOAITKBAOCAO,0) AS LOAITKBAOCAO, \n" +
						" isnull(DUNGCHOMASCLON,0) DUNGCHOMSCL,ISNULL(DUNGCHOCHIPHITRATRUOC,0) AS DUNGCHOCPTT \n"+
						"FROM ERP_TAIKHOANKT " +
						"WHERE PK_SEQ = '" +this.Id + "'";

		System.out.println("cau query la init " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.SoHieuTaiKhoan = rs.getString("SOHIEUTAIKHOAN");
					this.TenTaiKhoan = rs.getString("TENTAIKHOAN");
					this.LoaiTaiKhoanId = rs.getString("LOAITAIKHOAN_FK");
					this.CongTyId = rs.getString("CONGTY_FK");
					this.TrangThai = rs.getString("TRANGTHAI");
					this.TaiKhoanCoChiTiet = rs.getString("TAIKHOANCOCHITIET");
					this.TaiKhoanCoChiPhi = rs.getString("COTTCHIPHI");
					this.dungchoKho = rs.getString("DUNGCHOKHO");
					this.dungchoNganhang = rs.getString("DUNGCHONGANHANG");
					this.dungchoNCC = rs.getString("DUNGCHONCC");
					this.dungchoTaisan = rs.getString("DUNGCHOTAISAN");
					this.dungchoKhachhang = rs.getString("DUNGCHOKHACHHANG");
					this.dungchonhanvien = rs.getString("DUNGCHONHANVIEN");
					this.dungChoDanhMucDuAn = rs.getString("dungChoDanhMucDuAn");
					this.dungChoDoiTuongKhac = rs.getString("dungChoDoiTuongKhac");
					
					this.dungchoCPTT = rs.getString("dungchoCPTT");
					this.dungchoMSCL = rs.getString("dungchoMSCL");
				}
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		this.CreateRs();
	}

	public boolean CreateTaikhoankt()
	{
		try
		{
			// Kiểm tra Số hiệu TK có bị trùng không
			String query = " select count(*) as dem from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '"+ this.SoHieuTaiKhoan.trim() +"' ";
			ResultSet rsCheck = db.get(query);
			int ktra= 0;
			if(rsCheck!= null)
			{
				while(rsCheck.next())
				{
					ktra = rsCheck.getInt("dem");
				}
				rsCheck.close();
			}
			
			if(ktra > 0)
			{
				this.msg = "Số hiệu tài khoản này đã có trong hệ thống. Vui lòng kiểm tra lại. ";
				this.db.update("rollback");
				return false;
			}
			
			this.db.getConnection().setAutoCommit(false);
			
			String ngaytao = Utility.getCurrentDate();
			
			// 1 TK SẼ COPY CHO TẤT CẢ CÁC CÔNG TY TRÊN ERP
			query =  " SELECT PK_SEQ FROM ERP_CONGTY WHERE TRANGTHAI = 1 ";
			
			ResultSet ktcty = db.get(query);
			String cty_fk = "";
			
			if(ktcty!=null)
			{
				while(ktcty.next())
				{
					cty_fk = ktcty.getString("PK_SEQ");
					
					query = "INSERT INTO ERP_TAIKHOANKT(SOHIEUTAIKHOAN,TENTAIKHOAN,LOAITAIKHOAN_FK, CongTy_FK\n" +
							",TAIKHOANCOCHITIET,NGUOITAO, NGAYTAO,NGUOISUA\n" +
							",NgaySua,TRANGTHAI, COTTCHIPHI, DUNGCHOKHO\n" +
							", DUNGCHONGANHANG, DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG\n" +
							", DUNGCHONHANVIEN , NPP_FK, dungChoDanhMucDuAn, DUNGCHODOITUONGKHAC,LOAITKBAOCAO,"
							+ "DUNGCHOMASCLON,DUNGCHOCHIPHITRATRUOC) \n" +
							"VALUES('"+this.SoHieuTaiKhoan +"',N'"+this.TenTaiKhoan+"','"+this.LoaiTaiKhoanId+"','"+cty_fk+"'\n" +
							",'"+this.TaiKhoanCoChiTiet+"','"+this.UserId+"'," +"'"+ngaytao+"','"+this.UserId+"'\n" +
							",'"+ngaytao+"','"+this.TrangThai+"', '" + this.TaiKhoanCoChiPhi + "', '" + this.dungchoKho + "'\n" +
							", '" + this.dungchoNganhang + "', '" + this.dungchoNCC + "','" + this.dungchoTaisan + "','" + this.dungchoKhachhang + "'\n" +
							", "+this.dungchonhanvien+", 1, " + this.dungChoDanhMucDuAn + ", " + this.dungChoDoiTuongKhac + ",'"+this.loaiTKBaoCao+"'"
									+ ",'"+this.dungchoCPTT+"','"+this.dungchoMSCL+"')";

					System.out.println(query);
					if (!this.db.update(query))
					{
						this.msg = "Khong the tao moi tai khoan ke toan : " + query;
						this.db.update("rollback");
						return false;
					}
					
					query = "SELECT SCOPE_IDENTITY() AS ID";
					ResultSet rs = this.db.get(query);
					rs.next();
					String Id = rs.getString("ID");
					rs.close();
					
					query = "SELECT DISTINCT NAM, THANG FROM ERP_TAIKHOAN_NOCO";
					rs = this.db.get(query);
					while(rs.next()){
						query = "INSERT INTO ERP_TAIKHOAN_NOCO (TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
								"SELECT " + Id + ",  0, 0, PK_SEQ, 0, " + rs.getString("THANG") + ", " + rs.getString("NAM") + " " +
								"FROM ERP_TIENTE ";
						
						System.out.println(query);
						this.db.update(query);
					}
					rs.close();
				}
				ktcty.close();
			}	
			
			
			// MỖI TÀI KHOẢN MỚI TẠO SẼ TẠO CHO TỪNG CHI NHÁNH/ĐỐI TÁC Ở DMS
			
			String sql = " SELECT PK_SEQ FROM NHAPHANPHOI WHERE isKHACHHANG = '0' AND PK_SEQ != 1 ";
			
			ResultSet rstk = db.get(sql);
			
			String npp_fk = "";
			
			if(rstk!=null)
			{
				while(rstk.next())
				{
					npp_fk = rstk.getString("PK_SEQ");
					
					query = " INSERT INTO ERP_TAIKHOANKT( SOHIEUTAIKHOAN,TENTAIKHOAN,LOAITAIKHOAN_FK, TAIKHOANCOCHITIET,NGUOITAO, NGAYTAO,NGUOISUA,NgaySua,TRANGTHAI, " +
							" COTTCHIPHI, DUNGCHOKHO, DUNGCHONGANHANG, DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG, DUNGCHONHANVIEN, " +
							" dungChoDanhMucDuAn, DUNGCHODOITUONGKHAC, NPP_FK, CONGTY_FK ,DUNGCHOMASCLON,DUNGCHOCHIPHITRATRUOC) " +
							" VALUES('"+this.SoHieuTaiKhoan +"',N'"+this.TenTaiKhoan+"','"+this.LoaiTaiKhoanId+"'," +
							" '"+this.TaiKhoanCoChiTiet+"','"+this.UserId+"'," +"'"+ngaytao+"','"+this.UserId+"','"+ngaytao+"','"+this.TrangThai+"', " +
							" '" + this.TaiKhoanCoChiPhi + "', '" + this.dungchoKho + "', '" + this.dungchoNganhang + "', '" + this.dungchoNCC + "', " +
							"'" + this.dungchoTaisan + "','" + this.dungchoKhachhang + "', "+this.dungchonhanvien+", " + this.dungChoDanhMucDuAn + ", " + this.dungChoDoiTuongKhac + ", "+npp_fk+", NULL,"
									+ "'"+this.dungchoMSCL+"','"+this.dungchoCPTT+"' )";

					System.out.println(query);
					if (!this.db.update(query))
					{
						this.msg = "Khong the tao moi tai khoan ke toan : " + query;
						this.db.update("rollback");
						return false;
					}					
				}
			}
			rstk.close();
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			

			//SYN QUA DMS
			UtilitySyn.SynData(db, "ERP_TAIKHOANKT", "ERP_TAIKHOANKT", "SOHIEUTAIKHOAN", this.SoHieuTaiKhoan, "0", false);
			
			return true;
		} 
		catch (Exception er)
		{
			er.printStackTrace();
			this.db.update("rollback");
			this.msg = "khong the tao moi tai khoan  ke toan nay,loi tai dong lenh sau :" + er.toString();
			
			return false;
		}
	}

	public boolean UpdateTaikhoankt()
	{
		try
		{
			this.db.getConnection().setAutoCommit(true);
			// Kiểm tra Số hiệu TK có bị trùng không
			String query = " select count(*) as dem " +
					       " from ERP_TAIKHOANKT " +
					       " where SOHIEUTAIKHOAN = '" + this.SoHieuTaiKhoan.trim() + "'  " +
					       " and pk_seq != " + this.Id + " " +
					       " AND CONGTY_FK = (SELECT CONGTY_FK FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + this.Id + ") " ;
			
			System.out.println(query);
			
			ResultSet rsCheck = db.get(query);
			int ktra= 0;
			if(rsCheck!= null)
			{
				while(rsCheck.next())
				{
					ktra = rsCheck.getInt("dem");
				}
				rsCheck.close();
			}
			
			if(ktra > 0)
			{
				this.msg = "Số hiệu tài khoản này đã có trong hệ thống. Vui lòng kiểm tra lại. ";
				this.db.update("rollback");
				return false;
			}
			
			// Kiểm tra nếu Tài khoản đã có phát sinh dữ liệu thì không cho sửa thông tin
			String thongbao= "";
			/*thongbao = CheckTK(this.Id);*/  //--- Dữ liệu phát sinh, vẫn cho thay đổi
			
			if(thongbao.trim().length() > 0)
			{
				this.msg = thongbao;
				this.db.update("rollback");
				return false;
			}
			
			this.db.getConnection().setAutoCommit(false);
			String ngaysua = Utility.getCurrentDate();
			
			query = "UPDATE ERP_TAIKHOANKT SET DUNGCHOCHIPHITRATRUOC='"+this.dungchoCPTT+"',DUNGCHOMASCLON='"+this.dungchoMSCL+"',"
					+ "LoaiTaiKhoan_FK = '" + this.LoaiTaiKhoanId + "', " +
					"SoHieuTaiKhoan = '" + this.SoHieuTaiKhoan + "', " +
					"TenTaiKhoan = N'" + this.TenTaiKhoan + "', " +
					"TaiKhoanCoChiTiet = '" + this.TaiKhoanCoChiTiet + "' ," +
					"COTTCHIPHI = '" + this.TaiKhoanCoChiPhi + "', " +
					"DUNGCHOKHO = '" + this.dungchoKho + "', " + 
					"DUNGCHONGANHANG = '" + this.dungchoNganhang + "', " +
					"DUNGCHONCC = '" + this.dungchoNCC + "', " +
					"DUNGCHOTAISAN = '" + this.dungchoTaisan + "', " +
					"DUNGCHOKHACHHANG = '" + this.dungchoKhachhang + "', " +
					"DUNGCHONHANVIEN = '" + this.dungchonhanvien + "', "  +
					"NGUOISUA = '" + this.UserId + "', " +
					"NGAYSUA = '" + ngaysua + "', " +
					"TrangThai='" + this.TrangThai + "', " +
					"dungChoDanhMucDuAn = " + this.dungChoDanhMucDuAn + ", \n" +
					"dungChoDoiTuongKhac = " + this.dungChoDoiTuongKhac + ", \n" +
					"LOAITKBAOCAO = '"+this.loaiTKBaoCao+"' \n" +
					"Where SoHieuTaiKhoan ='" + this.SoHieuTaiKhoan + "'";

			System.out.println(query);
			if (!this.db.update(query))
			{
				this.msg = "Khong the tao moi tai khoan ke toan : " + query;
				this.db.update("rollback");
				return false;
			}					
						
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "ERP_TAIKHOANKT", "ERP_TAIKHOANKT", "SOHIEUTAIKHOAN", this.SoHieuTaiKhoan, "1", false);
			
			return true;
		} catch (Exception er)
		{
			er.printStackTrace();
			this.db.update("rollback");
			this.msg = "khong the cap nhat tai khoan  ke toan nay,loi tai dong lenh sau :" + er.toString();
			return false;
		}
	}

	private String CheckTK(String id) 
	{
		String thongbao = "";
		
		try
		{
			 int dem = CheckBang("ERP_DOITUONGKYQUY", "TAIKHOANKT_FK", id);
			 if(dem > 0) thongbao= "Tài khoản này đã phát sinh dữ liệu trong chức năng 'Đối tượng ký quỹ'. Không thể cập nhật được ";
			 
			 int dem1 = CheckBang("Erp_LoaiSanPham", "TAIKHOANKT_FK", id);
			 if(dem1 > 0) thongbao= "Tài khoản này đã phát sinh dữ liệu trong chức năng 'Loại sản phẩm'. Không thể cập nhật được ";
			 
			 int dem2 = CheckBang("Erp_NganHang_CongTy", "TAIKHOAN_FK", id); 
			 if(dem2 > 0) thongbao= "Tài khoản này đã phát sinh dữ liệu trong chức năng 'Tài khoản ngân hàng công ty'. Không thể cập nhật được ";
			 
			 int dem3 = CheckBang("ERP_NHOMCHIPHI", "TAIKHOAN_FK", id); 
			 if(dem3 > 0) thongbao= "Tài khoản này đã phát sinh dữ liệu trong chức năng 'Khoản mục chi phí'. Không thể cập nhật được ";
			 
			 int dem4 = CheckBang("ERP_DOANHTHU", "TAIKHOAN_FK", id); 
			 if(dem4 > 0) thongbao= "Tài khoản này đã phát sinh dữ liệu trong chức năng 'Khoản mục doanh thu'. Không thể cập nhật được ";
			 
			 int dem5 = CheckBang_SoHieu("ERP_THANHTOANHOADON", "DINHKHOANNO", id); 
			 if(dem5 > 0) thongbao= "Tài khoản này đã phát sinh dữ liệu trong chức năng 'Phiếu chi - UNC '. Không thể cập nhật được ";
			 
			 int dem6 = CheckBang("Erp_CongDung", "TAIKHOAN_FK", id); 
			 if(dem6 > 0) thongbao= "Tài khoản này đã phát sinh dữ liệu trong chức năng 'Công dụng tài sản'. Không thể cập nhật được ";
			 
			 int dem7 = CheckBang("erp_loaitaisan", "TAIKHOAN_FK", id); 
			 if(dem7 > 0) thongbao= "Tài khoản này đã phát sinh dữ liệu trong chức năng 'Loại tài sản'. Không thể cập nhật được ";
			 
			 int dem8 = CheckBang("ERP_BUTTOANTONGHOP_CHITIET", "TAIKHOANKT_FK", id);
			 if(dem8 > 0) thongbao= "Tài khoản này đã phát sinh dữ liệu trong chức năng 'Bút toán tổng hợp'. Không thể cập nhật được ";
			 
		}catch(Exception e){e.printStackTrace();}
		
		return thongbao;
	}

	private int CheckBang_SoHieu(String TABLE, String cot, String id) 
	{
		int dem = 0;
		try
		{
			 String sql = " select count(*) as dem from " + TABLE + "  where (select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN like '"+ cot +"') = '"+ id +"' and trangthai= 1 "; 
			 ResultSet rs = db.get(sql);
			 if(rs!= null)
			 {
				 while(rs.next())
				 {
					 dem = rs.getInt("dem");
				 }
			 }
	   }catch(Exception e)
	   {
		   e.printStackTrace();
		   dem= 0;
	   }
	   
		return dem;
	}

	private int CheckBang(String TABLE, String cot, String id ) 
	{
		int dem = 0;
		try
		{
			 String sql = " select count(*) as dem from " + TABLE + "  where "+ cot +" = '"+ id +"' and trangthai= 1 "; 
			 ResultSet rs = db.get(sql);
			 if(rs!= null)
			 {
				 while(rs.next())
				 {
					 dem = rs.getInt("dem");
				 }
			 }
	   }catch(Exception e)
	   {
		   e.printStackTrace();
		   dem= 0;
	   }
	   
		return dem;
	}

	public void CreateRs()
	{
		this.LoaiTaiKhoanRs = db.get("select PK_SEQ,Ma +'-'+ Ten As Loaitk from erp_loaitaikhoan WHERE TRANGTHAI=1 ");
		this.LoaiTaiKhoanRs = db.get("select PK_SEQ,Ma +'-'+ Ten As Loaitk from erp_loaitaikhoan WHERE TRANGTHAI=1 ");
		String query = "SELECT 1 AS PK_SEQ,N'Tài khoản dư nợ' AS LOAITK UNION ALL " +
					   "SELECT 2 AS PK_SEQ,N'Tài khoản dư có' AS LOAITK UNION ALL " +
					   "SELECT 3 AS PK_SEQ,N'Tài khoản lưỡng tính' AS LOAITK UNION ALL " +
					   "SELECT 4 AS PK_SEQ,N'Tài khoản dương bên nào thì hiện bên đó' AS LOAITK " ;
		this.loaiTKBaoCaoRs = db.get(query);
		this.CongTyRs = db.get("select PK_SEQ,TEN FROM ERP_CONGTY");
	}

	public boolean AllowtoChangeCty(){
		ResultSet rs = db.get("SELECT COUNT(*) AS NUM FROM ERP_TAIKHOAN_NOCO WHERE TAIKHOANKT_FK = '" + this.Id + "'");
		try{
			rs.next();
			if (!rs.getString("NUM").equals("0"))
				return false;
			else
				return true;
				
		}catch(java.sql.SQLException e){
			e.printStackTrace();
			return false;
		}		
	}

	public void closeDB()
	{
		try
		{
			if (this.LoaiTaiKhoanRs != null)
				this.LoaiTaiKhoanRs.close();
			if (this.CongTyRs != null)
				this.CongTyRs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		if (this.db != null)
			this.db.shutDown();
	}
	
	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	public String getUserId()
	{
		return this.UserId;
	}

	public void setUserId(String userId)
	{
		this.UserId = userId;
	}

	public String getId()
	{
		return this.Id;
	}

	public void setId(String Id)
	{
		this.Id = Id;
	}

	public String getLoaiTaiKhoanId()
	{
		return this.LoaiTaiKhoanId;
	}

	public void setLoaiTaiKhoanId(String LoaiTaiKhoanId)
	{
		this.LoaiTaiKhoanId = LoaiTaiKhoanId;
	}

	public String getSoHieuTaiKhoan()
	{
		return this.SoHieuTaiKhoan;
	}

	public void setSoHieuTaiKhoan(String SoHieuTaiKhoan)
	{
		this.SoHieuTaiKhoan = SoHieuTaiKhoan;
	}

	public String getTenTaiKhoan()
	{
		return this.TenTaiKhoan;
	}

	public void setTenTaiKhoan(String TenTaiKhoan)
	{
		this.TenTaiKhoan = TenTaiKhoan;
	}

	public String getTaiKhoanCoChiTiet()
	{
		return this.TaiKhoanCoChiTiet;
	}

	public void setTaiKhoanCoChiTiet(String TaiKhoanCoChiTiet)
	{
		this.TaiKhoanCoChiTiet = TaiKhoanCoChiTiet;
	}

	public String getCongTyId()
	{
		return this.CongTyId;
	}

	public void setCongTyId(String CongTyId)
	{
		this.CongTyId = CongTyId;
	}

	public String getTrangThai()
	{
		return this.TrangThai;
	}

	public void setTrangThai(String TrangThai)
	{
		this.TrangThai = TrangThai;
	}

	public String getTaiKhoanCoChiPhi()
	{
		return this.TaiKhoanCoChiPhi;
	}

	public void setTaiKhoanCoChiPhi(String TaiKhoanCoChiPhi)
	{
		this.TaiKhoanCoChiPhi = TaiKhoanCoChiPhi;
	}

	public String getDungchokhachhang()
	{
		return this.dungchoKhachhang ;
	}

	public void setDungchokhachhang(String dungchoKhachhang)
	{
		this.dungchoKhachhang  = dungchoKhachhang;
	}

	public String getDungchonhacungcap()
	{
		return this.dungchoNCC ;
	}

	public void setDungchonhacungcap(String dungchoNCC)
	{
		this.dungchoNCC   = dungchoNCC;
	}

	public String getDungchonganhang()
	{
		return this.dungchoNganhang;
	}

	public void setDungchonganhang(String dungchoNganhang)
	{
		this.dungchoNganhang   = dungchoNganhang;
	}

	public String getDungchokho()
	{
		return this.dungchoKho;
	}

	public void setDungchokho(String dungchoKho)
	{
		this.dungchoKho   = dungchoKho;
	}

	public String getDungchotaisan()
	{
		return this.dungchoTaisan;
	}

	public void setDungchotaisan(String dungchoTaisan)
	{
		this.dungchoTaisan   = dungchoTaisan;
	}

	public ResultSet getCongTyRs()
	{
		return this.CongTyRs;
	}

	public void setCongTyRs(ResultSet CongTyRs)
	{
		this.CongTyRs = CongTyRs;
	}

	public ResultSet getLoaiTaiKhoanRs()
	{
		return this.LoaiTaiKhoanRs;
	}

	public void setLoaiTaiKhoanRs(ResultSet LoaiTaiKhoanRs)
	{
		this.LoaiTaiKhoanRs = LoaiTaiKhoanRs;
	}

	public String getDungchonhanvien() {
		return this.dungchonhanvien;
	}
	
	public void setDungchonhanvien(String dungchonhanvien) {
		
		this.dungchonhanvien = dungchonhanvien;
	}

	public void setDungChoDanhMucDuAn(String dungChoDanhMucDuAn) {
		this.dungChoDanhMucDuAn = dungChoDanhMucDuAn;
	}

	public String getDungChoDanhMucDuAn() {
		return dungChoDanhMucDuAn;
	}
	
	public void setDungChoDoiTuongKhac(String dungChoDoiTuongKhac) {
		this.dungChoDoiTuongKhac = dungChoDoiTuongKhac;
	}

	public String getDungChoDoiTuongKhac() {
		return dungChoDoiTuongKhac;
	}

	public ResultSet getLoaiTKBaoCaoRs() {
		return loaiTKBaoCaoRs;
	}

	public void setLoaiTKBaoCaoRs(ResultSet loaiTKBaoCaoRs) {
		this.loaiTKBaoCaoRs = loaiTKBaoCaoRs;
	}

	public String getLoaiTKBaoCao() {
		return loaiTKBaoCao;
	}

	public void setLoaiTKBaoCao(String loaiTKBaoCao) {
		this.loaiTKBaoCao = loaiTKBaoCao;
	}
}
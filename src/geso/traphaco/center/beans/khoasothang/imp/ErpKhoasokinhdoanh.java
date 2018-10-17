package geso.traphaco.center.beans.khoasothang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.beans.khoasothang.IErpKhoasokinhdoanh;
import geso.traphaco.center.db.sql.dbutils;

public class ErpKhoasokinhdoanh implements IErpKhoasokinhdoanh
{
	String userId;
	String thang;
	String nam;
	String ngay;
	
	int sonhanhang;
	int sonhapkho;
	int soQc_NH;
	int soQc_LSX;
	int soTh_NH;
	int soTh_LSX;
	int coNhapKho_ChuaTH;
	int soDctk;
	int soKiemkho;
	int soHDNCC;
	int soxuatkho;
	int soxuatkhoCXHD;
	int sochuyenkho;
	int sodctk;
	int sohdtc;
	int solsx;
	int sophieuthu;
	int sophieuchi;
	int soBTTH;
	
	int sochaykhauhao;
	int sochaychenhlech;
	int sotinhtigia;
	
	ResultSet chungtuRs;
	int row;
	String msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	dbutils db;
	
	public ErpKhoasokinhdoanh()
	{
		this.thang = "";
		this.nam = "";
		this.ngay = Integer.toString( Integer.parseInt( this.getDate().split("-")[2] ) );
		this.row = 0;
		
		this.sonhanhang = 0;
		this.sonhapkho = 0;
		this.soQc_NH = 0;
		this.soQc_LSX = 0;
		this.soTh_NH = 0;
		this.soTh_LSX = 0;
		this.coNhapKho_ChuaTH = 0;
		this.soDctk = 0;
		this.soKiemkho = 0;
		this.soHDNCC = 0;
		this.soxuatkho = 0;
		this.soxuatkhoCXHD = 0;
		this.sochuyenkho = 0;
		this.sodctk = 0;
		this.sohdtc = 0;
		this.solsx = 0;
		this.sophieuthu = 0;
		this.sophieuchi = 0;
		this.soBTTH = 0;
		
		this.sochaykhauhao = 0;
		this.sochaychenhlech = 0;
		this.sotinhtigia = 0;
		
		this.msg = "";
		
		db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;
	}

	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}

	public ResultSet getChungtuRs() 
	{
		return this.chungtuRs;
	}

	public void setChungtuRs(ResultSet ctRs) 
	{
		this.chungtuRs = ctRs;
	}

	public int getRow() 
	{
		return this.row;
	}

	public void setRow(int row) 
	{
		this.row = row;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void init()
	{		
		try 
		{
			this.getNppInfo();
			
			String query = " select top(1) NAM as namMax, THANGKS as thangMax " + 
						   " from ERP_KHOASOKINHDOANH where npp_fk = '" + this.nppId + "' order by NAM desc, THANGKS desc ";
			System.out.println("1.Khoi tao thang: " + query);
			ResultSet rs = db.get(query);
			
			String thangKsMax = "";
			String namKsMax = "";
			
			if(rs != null)
			{
				while(rs.next())
				{
					thangKsMax = rs.getString("thangMax");
					namKsMax = rs.getString("namMax"); 
					
					if(thangKsMax.equals("12"))
					{
						this.thang = "1";
						this.nam = Integer.toString(Integer.parseInt(namKsMax) + 1);
 					}
					else
					{
						this.thang = Integer.toString(Integer.parseInt(thangKsMax) + 1);
						this.nam = namKsMax;
					}
				}
				rs.close();
			}
			
			if(this.thang.trim().length() <= 0)
				this.thang = "01";
			
			if(this.nam.trim().length() <= 0)
				this.nam = "2015";
			
			
			//check so nhap kho chua chot trong thang
			String _thang = "";
			if(Integer.parseInt(this.thang) < 10)
				_thang = "0" + this.thang;
			else
				_thang = this.thang;
			
			String _ngay = "";
			if(Integer.parseInt(this.ngay) < 10)
				_ngay = "0" + this.ngay;
			else
				_ngay = this.ngay;

			//String ngayChungTu = this.nam + "-" + str;
			String ngayChungTu = this.nam + "-" + _thang + "-" + _ngay;
			String queryChungTu = "";
			
			query = "select *  " +
					"from  " +
					"(  " +
						"select count(pk_seq) as sodongDONHANG from ERP_DONDATHANGNPP  " +
						//"where trangthai = '0' and substring(NGAYDONHANG, 0, 8) = '" + ngayChungTu + "'  " +
						"where trangthai = '0' and substring(NGAYDONHANG, 0, 8) = substring('" + ngayChungTu + "', 0, 8) and NGAYDONHANG <= '" + ngayChungTu + "'  " +
					") donhang,   " +
					"( " +
						"select count(pk_seq) as sodongHOADON from ERP_HOADONNPP  " +
						//"where trangthai in ( 0, 1 ) and substring(NGAYXUATHD, 0, 8) = '" + ngayChungTu + "' " +
						"where trangthai in ( 0, 1 ) and substring(NGAYXUATHD, 0, 8) = substring('" + ngayChungTu + "', 0, 8) and NGAYXUATHD <= '" + ngayChungTu + "' " +
					") hoadon ";
			
			System.out.println("Câu SQL" +query );
			rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					this.sonhapkho = rs.getInt("sodongDONHANG");
					this.sochuyenkho = rs.getInt("sodongHOADON");
				}
				rs.close();
			}
			
			String chungtuId = " ";
			String trangthai = " ";
			
			/***************** ĐƠN HÀNG ***********************/
			
			if(this.sonhapkho > 0)
			{
				chungtuId = "Số chứng từ: ";
				trangthai = "Chưa hoàn tất";
				
				query = "select pk_seq from ERP_DONDATHANGNPP " +
						//"where trangthai = '0'  and substring(NGAYDONHANG, 0, 8) = '" + ngayChungTu + "'";
						"where trangthai = '0'  and substring(NGAYDONHANG, 0, 8) = substring('" + ngayChungTu + "', 0, 8) and NGAYDONHANG <= '" + ngayChungTu + "' ";
				ResultSet rsChungtu = db.get(query);
				while(rsChungtu.next())
				{
					chungtuId += rsChungtu.getString("pk_seq") + ", ";
				}
				rsChungtu.close();
			}
			else
			{
				chungtuId = " ";
				trangthai = "Hoàn tất";
			}
			
			queryChungTu += " select N'1.Kiểm tra đơn hàng' as type, N'" + chungtuId + "' as chungtu, N'" + trangthai + "' as trangthai  union all ";
			
			/**************************************************/
			
			
			/***************** HÓA ĐƠN ***********************/
			
			if(this.sochuyenkho > 0)
			{
				chungtuId = "Số chứng từ: ";
				trangthai = "Chưa hoàn tất";
				
				query = "select pk_seq from ERP_HOADONNPP  " +
						//"where trangthai in ( 0, 1 ) and substring(NGAYXUATHD, 0, 8) = '" + ngayChungTu + "'  ";
						"where trangthai in ( 0, 1 ) and substring(NGAYXUATHD, 0, 8) = substring('" + ngayChungTu + "', 0, 8) and NGAYXUATHD <= '" + ngayChungTu + "'  ";
				
				ResultSet rsChungtu = db.get(query);
				while(rsChungtu.next())
				{
					chungtuId += rsChungtu.getString("pk_seq") + ", ";
				}
				rsChungtu.close();
			}
			else
			{
				chungtuId = " ";
				trangthai = "Hoàn tất";
			}
			
			queryChungTu += " select N'2.Kiểm tra hóa đơn' as type, N'" + chungtuId + "' as chungtu, N'" + trangthai + "' as trangthai  ";
			
			/**************************************************/
			
			
			System.out.println("___check chung tu: " + queryChungTu);
			this.chungtuRs = db.get(queryChungTu);
			
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
		}
	}
	
	public String KhoaSoThang()
	{
		try
		{
			//Ngày khóa sổ kinh doanh không được nhỏ hơn ngày 25 hàng tháng
			if( Integer.parseInt(this.ngay) < 25 )
			{
				this.msg = "Ngày khóa sổ kinh doanh không hợp lệ. Vui lòng kiểm tra lại";
				return this.msg;
			}
			
			this.getNppInfo();
			
			String query = "select top(1) NAM as namMax, THANGKS as thangMax from ERP_KHOASOKINHDOANH where npp_fk = '" + this.nppId + "' order by NAM desc, THANGKS desc ";
			System.out.println("1.Khoi tao thang: " + query);
			ResultSet rs = db.get(query);
			
			String thangKsMax = "";
			String namKsMax = "";
			
			if(rs != null)
			{
				while(rs.next())
				{
					thangKsMax = rs.getString("thangMax");
					namKsMax = rs.getString("namMax"); 
					
					if(thangKsMax.equals("12"))
					{
						this.thang = "1";
						this.nam = Integer.toString(Integer.parseInt(namKsMax) + 1);
						}
					else
					{
						this.thang = Integer.toString(Integer.parseInt(thangKsMax) + 1);
						this.nam = namKsMax;
					}
				}
				rs.close();
			}
			
			if(this.thang.trim().length() <= 0)
				this.thang = "01";
			
			if(this.nam.trim().length() <= 0)
				this.nam = "2015";
			
			//check so nhap kho chua chot trong thang
			String _thang = "";
			if(Integer.parseInt(this.thang) < 10)
				_thang = "0" + this.thang;
			else
				_thang = this.thang;
			
			String _ngay = "";
			if(Integer.parseInt(this.ngay) < 10)
				_ngay = "0" + this.ngay;
			else
				_ngay = this.ngay;

			//String ngayChungTu = this.nam + "-" + str;
			String ngayChungTu = this.nam + "-" + _thang + "-" + _ngay;
			
			query = "select *  " +
					"from  " +
					"(  " +
						"select count(pk_seq) as sodongDONHANG from ERP_DONDATHANGNPP  " +
						//"where trangthai = '0' and substring(NGAYDONHANG, 0, 8) = '" + ngayChungTu + "'  " +
						"where trangthai = '0' and substring(NGAYDONHANG, 0, 8) = substring('" + ngayChungTu + "', 0, 8) and NGAYDONHANG <= '" + ngayChungTu + "'  " +
					") donhang,   " +
					"( " +
						"select count(pk_seq) as sodongHOADON from ERP_HOADONNPP  " +
						"where trangthai in ( 0, 1 ) and substring(NGAYXUATHD, 0, 8) = substring('" + ngayChungTu + "', 0, 8) and NGAYXUATHD <= '" + ngayChungTu + "' " +
					") hoadon ";
			
			System.out.println("Câu SQL" +query );
			rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					this.sonhapkho = rs.getInt("sodongDONHANG");
					this.sochuyenkho = rs.getInt("sodongHOADON");
				}
				rs.close();
			}
		
			//YC đổi lại, không check gì nữa chỉ là căn cứ để TICK chuyển sales lúc tạo mới đơn hàng hây không
			/*if(this.sochuyenkho > 0)
				this.msg += "+ Vui Lòng kiểm tra các HÓA ĐƠN chưa xử lý trong tháng \n";*/
			
			if(this.msg.trim().length() > 0)
			{
				this.msg = "Trước khi khóa sổ, bạn PHẢI KIỂM TRA các thông tin sau: \n" + this.msg;
				return this.msg;
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "insert erp_khoasokinhdoanh(thangksgannhat, ngayks, thangks, nam, npp_fk, ngaytao, nguoitao) " +
					"values('" + thangKsMax + "', '" + this.ngay + "', '" + this.thang + "', '" + this.nam + "', '" + this.nppId + "', '" + this.getDate() + "', '" + this.userId + "')";
			
			System.out.println(":::KHOA SO KINH DOANH: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong the khoa so thang: " + query;
			}
			
			//NHỮNG ĐƠN HÀNG CHƯA CHỐT THÌ TĂNG NGÀY ĐƠN HÀNG LÊN SAU NGÀY KHÓA SỔ VÀ GHI NHẬN LÀ ĐƠN CHUYỂN SALES
			//BACKUP lại ngày gốc trước  --> ĐỔI LẠI YC KHÔNG TĂNG NGÀY ĐƠN HÀNG LÊN NỮA
			/*query = "update ERP_DONDATHANGNPP set ngaydonhang_goc = NGAYDONHANG  " +
					"where trangthai = '0' and substring(NGAYDONHANG, 0, 8) = substring('" + ngayChungTu + "', 0, 8) and NGAYDONHANG <= '" + ngayChungTu + "' ";
			
			System.out.println(":::TANG NGAY DON HANG: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong the khoa so thang: " + query;
			}*/
			
			/*query = "update ERP_DONDATHANGNPP set NGAYDONHANG = convert(varchar(10), DATEADD(dd, 1, '" + ngayChungTu + "'), 120), chuyenSALES = '1', thoidiem_tang_khoaso_kinhdoanh = getdate() " +
					"where trangthai = '0' and substring(NGAYDONHANG, 0, 8) = substring('" + ngayChungTu + "', 0, 8) and NGAYDONHANG <= '" + ngayChungTu + "' ";
			
			System.out.println(":::TANG NGAY DON HANG: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Khong the khoa so thang: " + query;
			}*/
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			e.printStackTrace();
			return "7.Khong the Khoa so Thang: " + e.getMessage();
		}
		
		return "";
	}
	

	public int getSonhapkho() 
	{
		return this.sonhapkho;
	}

	public void setSonhapkho(int row) 
	{
		this.sonhapkho = row;
	}

	public int getSoxuatkho() 
	{
		return this.soxuatkho;
	}

	public void setSoxuatkho(int row)
	{
		this.soxuatkho = row;
	}

	public int getSochuyenkho() 
	{
		return this.sochuyenkho;
	}

	public void setSochuyenkho(int row) 
	{
		this.sochuyenkho = row;
	}

	public int getSodctk()
	{
		return this.sodctk;
	}

	public void setSodctk(int row) 
	{
		this.sodctk = row;
	}
	
	public int getSonhanhang() 
	{
		return this.sonhanhang;
	}

	public void setSonhanhang(int row) 
	{
		this.sonhanhang = row;
	}

	public int getSohdNCC() 
	{
		return this.soHDNCC;
	}

	public void setSohdNCC(int row)
	{
		this.soHDNCC = row;
	}

	public int getSoxuatkhoChuaNhanHD() 
	{
		return this.soxuatkhoCXHD;
	}

	public void setSoxuatkhoChuaNhanHD(int row) 
	{
		this.soxuatkhoCXHD = row;
	}

	public int getSoHdtc() 
	{
		return this.sohdtc;
	}

	public void setSoHdtc(int row) 
	{
		this.sohdtc = row;
	}

	public int getSoLsx() 
	{
		return this.solsx;
	}

	public void setSoLsx(int row) 
	{
		this.solsx = row;
	}

	public int getSoPhieuthu() 
	{
		return this.sophieuthu;
	}
	
	public void setSoPhieuthu(int row)
	{
		this.sophieuthu = row;
	}

	public int getSoPhieuchi() 
	{
		return this.sophieuchi;
	}

	public void setSoPhieuchi(int row) 
	{
		this.sophieuchi = row;
	}

	public int getSoBtth() 
	{
		return this.soBTTH;
	}

	public void setSoBtth(int row) 
	{
		this.soBTTH = row;
	}

	public int getChaykhauhao() 
	{
		return this.sochaykhauhao;
	}

	public void setChaykhauhao(int row) 
	{
		this.sochaykhauhao = row;
	}

	public int getChaychenhlechtigia() 
	{
		return this.sochaychenhlech;
	}

	public void setChaychenhlechtigia(int row)
	{
		this.sochaychenhlech = row;
	}

	public int getTinhgiathanh() 
	{
		return this.sotinhtigia;
	}

	public void setTinhgiathanh(int row)
	{
		this.sotinhtigia = row;
	}

	public int getSoQC_NhanHang() {
		
		return this.soQc_NH;
	}

	
	public void setSoQC_NhanHang(int row) {
		
		this.soQc_NH = row;
	}

	
	public int getSoQC_LSX() {
		
		return this.soQc_LSX;
	}

	
	public void setSoQC_LSX(int row) {
		
		this.soQc_LSX = row;
	}

	
	public int getSoTH_NhanHang() {
		
		return this.soTh_NH;
	}

	
	public void setSoTH_NhanHang(int row) {
		
		this.soTh_NH = row;
	}

	
	public int getSoTH_LSX() {
		
		return this.soTh_LSX;
	}

	
	public void setSoTH_LSX(int row) {
		
		this.soTh_LSX = row;
	}

	
	public int getSoDctk() {
		
		return this.soDctk;
	}

	
	public void setSoDctk(int row) {
		
		this.soDctk = row;
	}

	
	public int getSoKiemkho() {
		
		return this.soKiemkho;
	}

	
	public void setSoKiemkho(int row) {
		
		this.soKiemkho = row;
	}

	
	public int getCoNhapKho_ChuaTH() {
		
		return this.coNhapKho_ChuaTH;
	}

	
	public void setCoNhapKho_ChuaTH(int row) {
		
		this.coNhapKho_ChuaTH = row;
	} 
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}


	public String getNgay() {

		return this.ngay;
	}

	public void setNgay(String ngay) {
		
		this.ngay = ngay;
	}

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		this.db.shutDown();
	}
	

}


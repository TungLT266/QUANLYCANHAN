package geso.traphaco.erp.beans.nganhangcongty.imp;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.erp.beans.nganhangcongty.IErpNganHangCongTy;
import geso.traphaco.erp.util.UtilitySyn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class ErpNganHangCongTy implements IErpNganHangCongTy
{
	String Id , CongTy , ctyId, SoTaiKhoan , ChuTaiKhoan , LoaiTien , ChiNhanh , NganHang , TaiKhoanKeToan , TrangThai , msg ,
	UserId;
	ResultSet rsNganHang , rsCongTy , rsLoaiTien , rsChiNhanh , rsTaiKhoan;
	dbutils db;
	String Masothue;
	private String nppId;
	private List<Erp_Item> nppList;
	
	public ErpNganHangCongTy( )
	{
		this.Id = "";
		this.ctyId = "";
		this.SoTaiKhoan = "";
		this.ChuTaiKhoan = "";
		this.LoaiTien = "";
		this.ChiNhanh = "";
		this.NganHang = "";
		this.Masothue= "";
		this.TaiKhoanKeToan = "";
		this.LoaiTien = "";
		this.TrangThai = "1";
		this.msg = "";
		this.UserId = "";
		this.CongTy = "";
		this.nppId = "";
		this.nppList = new ArrayList<Erp_Item>();
		
		this.db = new dbutils();
	}
	
	public ErpNganHangCongTy( String id )
	{
		this.Id = id;
		this.ctyId = "";
		this.SoTaiKhoan = "";
		this.Masothue= "";
		this.ChuTaiKhoan = "";
		this.LoaiTien = "";
		this.ChiNhanh = "";
		this.NganHang = "";
		this.TaiKhoanKeToan = "";
		this.LoaiTien = "";
		this.TrangThai = "";
		this.msg = "";
		this.UserId = "";
		this.CongTy = "";
		
		this.nppId = "";
		this.nppList = new ArrayList<Erp_Item>();
		
		db = new dbutils();
	}
	
	public String getId()
	{
		return this.Id;
	}
	
	public void setId(String id)
	{
		this.Id = id;
	}
	
	public String getMsg()
	{
		return this.msg;
	}
	
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public String getCongTy()
	{
		return this.CongTy;
	}
	
	public void setCongTy(String congty)
	{
		this.CongTy = congty;
	}
	
	public String getCtyId()
	{
		return this.ctyId;
	}
	
	public void setCTyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public String getChuTaiKhoan()
	{
		return this.ChuTaiKhoan;
	}
	
	public void setChuTaiKhoan(String chutaikhoan)
	{
		this.ChuTaiKhoan = chutaikhoan;
	}
	
	public String getLoaiTien()
	{
		return this.LoaiTien;
	}
	
	public void setLoaiTien(String loaitien)
	{
		this.LoaiTien = loaitien;
	}
	
	public String getNganHang()
	{
		return this.NganHang;
	}
	
	public void setNganHang(String nganhang)
	{
		this.NganHang = nganhang;
	}
	
	public String getSoTaiKhoan()
	{
		return this.SoTaiKhoan;
	}
	
	public void setSoTaiKhoan(String sotaikhoan)
	{
		this.SoTaiKhoan = sotaikhoan;
	}
	
	public String getChiNhanh()
	{
		return this.ChiNhanh;
	}
	
	public void setChiNhanh(String chinhanh)
	{
		this.ChiNhanh = chinhanh;
	}
	
	public void init()
	{
		String query =
		" Select PK_SEQ,ISNULL(NganHang_FK,'0')NganHang,ISNULL(ChiNhanh_FK,'0') ChiNhanh," +
		" ISNULL(CongTy_FK,'0')CongTy,ISNULL(TaiKhoan_FK,'0')TaiKhoan," +
		" ISNULL(TienTe_FK,'0') TienTe,ISNULL(SoTaiKhoan,'')SoTaiKhoan,ChuTaiKhoan,TrangThai,isnull(masothue,'') as masothue \n" +
		", isNull(convert(nvarchar, npp_fk), '') npp_fk\n" +
		" From Erp_NganHang_CongTy Where PK_SEQ=" + this.Id + "\n";
		System.out.println("Init " + query);
		ResultSet rs = this.db.get(query);
		try
		{
			if (rs != null) while (rs.next())
			{
				this.nppId = rs.getString("npp_fk");
				this.NganHang = rs.getString("NganHang");
				this.CongTy = rs.getString("CongTy");
				this.TaiKhoanKeToan = rs.getString("TaiKhoan");
				this.LoaiTien = rs.getString("TienTe");
				this.TrangThai = rs.getString("TrangThai");
				this.SoTaiKhoan = rs.getString("SoTaiKhoan");
				this.ChuTaiKhoan = rs.getString("ChuTaiKhoan");
				this.ChiNhanh = rs.getString("ChiNhanh");
				this.Masothue=rs.getString("masothue");
				
				System.out.println("ChiNhanh" + ChiNhanh);
				
			}
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void createaRs()
	{
		String query = "Select PK_SEQ,Ma,Ten From Erp_NganHang Where TrangThai=1 ";
		this.rsNganHang = this.db.get(query);
		query = "Select PK_SEQ,Ma,Ten From Erp_TienTe ";
		this.rsLoaiTien = this.db.get(query);
		query = "Select PK_SEQ,Ma,Ten From ERP_CONGTY Where TrangThai=1";
		this.rsCongTy = this.db.get(query);
		query = "Select PK_SEQ,TenTaiKhoan Ten,SoHieuTaiKhoan As Ma From Erp_TaiKhoanKT Where TrangThai=1 AND CONGTY_FK = "+this.ctyId+" ";
		this.rsTaiKhoan = this.db.get(query);
		 query ="Select PK_SEQ,Ten,Ma From Erp_ChiNhanh Where TrangThai=1 ";
		this.rsChiNhanh = this.db.get(query);
		
		query = "select npp.pk_seq, npp.ma + ' - ' + npp.ten as ten from nhaPhanPhoi npp where npp.trangThai = 1 and npp.isKhachHang = 0";
		Erp_Item.getListFromQuery(db, query, this.nppList);
	}
	
	public boolean Update()
	{
		String query =
				"Update Erp_NganHang_CongTy Set CongTy_FK=" + this.ctyId + " ,ChuTaiKhoan=N'" + this.ChuTaiKhoan +
				"',SoTaiKhoan='" + this.SoTaiKhoan + "',NganHang_FK=" + this.NganHang + ",ChiNhanh_FK=" + this.ChiNhanh +
				",TienTe_FK=" + this.LoaiTien + ",TaiKhoan_FK=" + this.TaiKhoanKeToan + ",TrangThai=" + this.TrangThai +
				",NguoiSua=" + this.UserId + ",NgaySua='" + getDateTime() + "' ,masothue=N'"+this.Masothue+"' \n" +
				", npp_fk = " + this.nppId + "\n" +
				"Where PK_SEQ=" + this.Id + "";
		System.out.println("Update  Erp_NganHang_CongTy " + query);
		if (!this.db.update(query))
		{
			this.msg = "Không thể cập nhật " + query;
			return false;
		}
		else
		{
			//SYN QUA DMS
			String  result = UtilitySyn.SynData(db, "Erp_NganHang_CongTy", "Erp_NganHang_CongTy", "PK_SEQ", this.Id, "1", false);
//			if (result.trim().length() > 0)
//			{
//				this.msg = "Chưa đồng bộ được hệ thống với mã vừa sửa";
//				return false;
//			}
			return true;
		}
	}
	
	public boolean Create()
	{
		String query =
		" Insert into Erp_NganHang_CongTy(CongTy_FK,ChuTaiKhoan,SoTaiKhoan,NganHang_FK\n" +
		",ChiNhanh_FK,TaiKhoan_FK, NguoiTao,NguoiSua\n" +
		",NgayTao,NgaySua,TrangThai,TienTe_FK\n" +
		",MASOTHUE, npp_fk)\n"
		+ "" + "values(" +this.ctyId +",N'" +this.ChuTaiKhoan +"','" +this.SoTaiKhoan +"'," +this.NganHang + "\n" +
		"," +this.ChiNhanh +"," +this.TaiKhoanKeToan +"," +this.UserId +"," +"" +this.UserId +"\n" +
		",'" +getDateTime() + "','" +	getDateTime() + "'," + this.TrangThai + "," + this.LoaiTien + "\n" +
		",N'"+this.Masothue+"', " + this.nppId + ")";
		try {
			if (this.db.update(query)) 
			{
				query = "Select IDENT_CURRENT('erp_nganhang') as currentId";
				ResultSet rsId = this.db.get(query);
				String currentId = "";
				if (rsId != null)
				{
					while (rsId.next())
					{
						currentId = rsId.getString("currentId");
					}
					rsId.close();
				}
				//SYN QUA DMS
				String result = UtilitySyn.SynData(db, "Erp_NganHang_CongTy", "Erp_NganHang_CongTy", "pk_seq", currentId, "0", false);
//				if (result.trim().length() > 0)
//				{
//					this.msg = "Chưa đồng bộ được hệ thống với mã vừa tạo";
//					return false;
//				}
				return true;
			}
			else
			{
				this.msg = "Không thể tạo mới " + query;
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "Không thể tạo mới " + query;
			return false;
		}
	}
	
	public boolean Enable()
	{
		String query = "Update Erp_NganHang_CongTy Set TrangThai=1 Where PK_SEQ ='" + this.Id + "'";
		System.out.println("Hoat dong " + query);
		System.out.print("Update" + query);
		try {
			if (!this.db.update(query)) 
				return false;
			else
			{
				//SYN QUA DMS
				String result = UtilitySyn.SynData(db, "Erp_NganHang_CongTy", "Erp_NganHang_CongTy", "PK_SEQ", this.Id, "1", false);
//				if (result.trim().length() > 0)
//				{
//					this.msg = "Chưa đồng bộ được hệ thống với mã vừa sửa";
//					return false;
//				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean Delete()
	{
		String query = "Delete From  Erp_NganHang_CongTy  Where PK_SEQ ='" + this.Id + "'";
		System.out.print("Update" + query);
		
		try {
			if (!this.db.update(query)) 
				return false;
			else
			{
				//SYN QUA DMS
				String result = UtilitySyn.SynData(db, "Erp_NganHang_CongTy", "Erp_NganHang_CongTy", "PK_SEQ", this.Id, "2", true);
//				if (result.trim().length() > 0)
//				{
//					this.msg = "Chưa đồng bộ được hệ thống với mã vừa xóa";
//					return false;
//				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ResultSet getCongTyRs()
	{
		return this.rsCongTy;
	}
	
	public void setCongTyRs(ResultSet congty)
	{
		this.rsCongTy = congty;
	}
	
	public ResultSet getLoaiTienRs()
	{
		return this.rsLoaiTien;
	}
	
	public void setLoaiTien(ResultSet loaitien)
	{
		this.rsLoaiTien = loaitien;
	}
	
	public ResultSet getChiNhanhRs()
	{
		return this.rsChiNhanh;
	}
	
	public void setNganHang(ResultSet nganhang)
	{
		this.rsNganHang = nganhang;
	}
	
	public ResultSet getNganHangRs()
	{
		return this.rsNganHang;
	}
	
	public void setChiNhanhRs(ResultSet chinhanh)
	{
		this.rsChiNhanh = chinhanh;
	}
	
	public void setTrangThai(String trangthai)
	{
		this.TrangThai = trangthai;
	}
	
	public String getTrangThai()
	{
		return this.TrangThai;
	}
	
	public String getTaiKhoanKeToan()
	{
		return this.TaiKhoanKeToan;
	}
	
	public void setTaiKhoanKeToan(String taikhoanketoan)
	{
		this.TaiKhoanKeToan = taikhoanketoan;
	}
	
	public ResultSet getTaiKhoanRs()
	{
		return this.rsTaiKhoan;
	}
	
	public void setTaiKhoanRs(ResultSet taikhoan)
	{
		this.rsTaiKhoan = taikhoan;
	}
	
	public void setUserId(String userId)
	{
		this.UserId = userId;
	}
	
	public String getUserId()
	{
		return this.UserId;
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void closeDB(){
		try{
			if (rsNganHang != null)	rsNganHang.close();
			if (rsCongTy != null) rsCongTy.close();
			if (rsLoaiTien != null) rsLoaiTien.close();
			if (rsChiNhanh != null) rsChiNhanh.close();
			if (rsTaiKhoan != null) rsTaiKhoan.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		if (this.db != null)
			this.db.shutDown();
	}

	
	public String getMasothue() {
		
		return this.Masothue;
	}

	
	public void setMasothue(String Masothue) {
		
		this.Masothue=Masothue;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppList(List<Erp_Item> nppList) {
		this.nppList = nppList;
	}

	public List<Erp_Item> getNppList() {
		return nppList;
	}
}
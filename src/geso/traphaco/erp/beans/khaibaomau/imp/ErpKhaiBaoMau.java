package geso.traphaco.erp.beans.khaibaomau.imp;

import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErpKhaiBaoMau
{
	private String stt;
	private String maSo;
	private String thuyetMinh;
	private String tenTieuChi;
	private int isCongThuc;
	private String congThuc;
	
	private int inDam;
	private int isCongThucExcel;
	
	
	private List<Erp_Item> taiKhoanList;
	private List<ErpKhaiBaoMauChiTiet> chiTietList;
	
	private String msg;
	private dbutils db;

	public ErpKhaiBaoMau()
	{
		this.stt = "";
		this.maSo = "";
		this.thuyetMinh = "";
		this.tenTieuChi = "";
		this.isCongThuc = 0;
		this.congThuc = "";
		
		this.inDam = 0;
		this.isCongThucExcel = 0;
		
		this.taiKhoanList = new ArrayList<Erp_Item>();
		this.chiTietList = new ArrayList<ErpKhaiBaoMauChiTiet>();
		
		this.msg = "";
		this.db = new dbutils();
	}

	public void init()
	{
		if (this.maSo.trim().length() > 0)
		{
			String query = "select stt, maSo,tenChiTieu, isCongThuc, congThuc,inDam,ISCONGTHUCEXCEL from ERP_MAUCDKT where MASO = '" + this.maSo + "'";
			
			try {
				ResultSet rs = this.db.get(query);
				
				if (rs != null)
				{
					if (rs.next())
					{
						this.stt = rs.getString("stt");
						this.maSo = rs.getString("maSo");
						this.tenTieuChi = rs.getString("tenChiTieu");
						this.isCongThuc = rs.getInt("isCongThuc");
						this.congThuc = rs.getString("congThuc");
						this.inDam = rs.getInt("inDam");
						this.isCongThucExcel = rs.getInt("isCongThucExcel");
					}
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		initList();
	}
	
	public boolean create()
	{
		boolean result = kiemTraThongTin();
		if (result == false)
			return false;
		
		String query = 
			"insert into ERP_MAUCDKT(STT, MASO, THUYETMINH, TENCHITIEU\n" +
			", ISCONGTHUC, CONGTHUC, INDAM, ISCONGTHUCEXCEL)\n" +
			"values('" + this.stt + "', '" + this.maSo + "', N'" + this.thuyetMinh + "', N'" + this.tenTieuChi + "'\n" +
			", " + this.isCongThuc + ", '" + this.congThuc + "', " + this.inDam + ", " + this.isCongThucExcel + ")\n";
		System.out.println("them moi: \n" + query + "\n------------------------");
		try {
			this.db.getConnection().setAutoCommit(false);
			
			if (!this.db.update(query))
			{
				this.msg = "C1.1 Không thể tạo mới khai báo mẫu";
				this.db.getConnection().rollback();
				return false;
			}
			query = "";
			
			for (ErpKhaiBaoMauChiTiet chiTiet : this.chiTietList)
			{
				query += 
					"insert into ERP_MAUCDKT_SOHIEUTAIKHOAN(MASO, LOAITSNV, LAYSODU, LAYSODU_NO\n" +
					", LAYSODU_CO, KHONGAM, SOHIEUTAIKHOAN)\n" +
					"values('" + this.maSo + "', " + chiTiet.getLoaiTSNV() + ", " + chiTiet.getLaySoDu() + ", " + chiTiet.getLaySoDuNo() + "\n" +
					", " + chiTiet.getLaySoDuCo() + ", " + chiTiet.getKhongAm() + ", '" + chiTiet.getSoHieuTaiKhoan() + "')\n";
			}
		
			System.out.println("them moi chi tiet: \n" + query + "\n------------------------");
			if (query.trim().length() > 0)
				if (!this.db.update(query))
				{
					this.msg = "C1.2 Không thể tạo mới khai báo mẫu";
					this.db.getConnection().rollback();
					return false;
				}
			
			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "C1.3 Không thể tạo mới khai báo mẫu";
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		
		
		return true;
	}
	
	public boolean edit()
	{
		boolean result = kiemTraThongTin();
		if (result == false)
			return false;
		
		String query = 
			"update ERP_MAUCDKT \n" +
			"set STT = '" + this.stt + "', THUYETMINH = N'" + this.thuyetMinh + "'\n" +
			", TENCHITIEU = N'" + this.tenTieuChi + "', ISCONGTHUC = " + this.isCongThuc + ", CONGTHUC = '" + this.congThuc + "'\n" +
			", INDAM = " + this.inDam + ", ISCONGTHUCEXCEL = " + this.isCongThucExcel + " where  MASO = '" + this.maSo + "';\n";
		
		try {
			this.db.getConnection().setAutoCommit(false);
			
			if (!this.db.update(query))
			{
				this.msg = "E1.1 Không thể cập nhật khai báo mẫu";
				this.db.getConnection().rollback();
				return false;
			}
			
			query = 
				"delete ERP_MAUCDKT_SOHIEUTAIKHOAN \n" +
				"where  MASO = '" + this.maSo + "';\n";
			
			if (!this.db.update(query))
			{
				this.msg = "E1.2 Không thể cập nhật khai báo mẫu";
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "";
			for (ErpKhaiBaoMauChiTiet chiTiet : this.chiTietList)
			{
				query += 
					"insert into ERP_MAUCDKT_SOHIEUTAIKHOAN(MASO, LOAITSNV, LAYSODU, LAYSODU_NO\n" +
					", LAYSODU_CO, KHONGAM, SOHIEUTAIKHOAN)\n" +
					"values('" + this.maSo + "', " + chiTiet.getLoaiTSNV() + ", " + chiTiet.getLaySoDu() + ", " + chiTiet.getLaySoDuNo() + "\n" +
					", " + chiTiet.getLaySoDuCo() + ", " + chiTiet.getKhongAm() + ", '" + chiTiet.getSoHieuTaiKhoan() + "')\n";
			}
			
			if (query.trim().length() > 0)
				if (!this.db.update(query))
				{
					this.msg = "E1.3 Không thể tạo mới khai báo mẫu";
					this.db.getConnection().rollback();
					return false;
				}
			
			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "E1.4 Không thể tạo mới khai báo mẫu";
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		
		
		return true;
	}
	

	
	private boolean kiemTraThongTin() {
		if (this.stt.trim().length() == 0)
			this.msg = "Vui lòng nhập 'STT'\n";
		
		if (this.maSo.trim().length() == 0)
			this.msg += "Vui lòng nhập 'Mã số'\n";
		
		if (this.tenTieuChi.trim().length() == 0)
			this.msg += "Vui lòng nhập 'Tên tiêu chí'\n";

		if (this.msg.trim().length() > 0)
			return false;
		
		return true;
	}
	
	private void initList() {
		initTaiKhoanList();
		initChiTietList();
	}

	private void initChiTietList() {
		if (this.maSo.trim().length() > 0)
		{
			String query = 
				"select mct.PK_SEQ id, mct.MASO, mct.LOAITSNV, mct.LAYSODU\n" +
				", mct.LAYSODU_NO laySoDuNo, mct.LAYSODU_CO laySoDuCo, mct.KHONGAM, mct.SOHIEUTAIKHOAN \n" +
				"from ERP_MAUCDKT_SOHIEUTAIKHOAN mct where mct.maSo = '" + this.maSo + "' order by mct.soHieuTaiKhoan\n";
			ErpKhaiBaoMauChiTiet.getListFromQuery(db, query, this.chiTietList);
		}
	}

	private void initTaiKhoanList() {
		String query = "select SOHIEUTAIKHOAN as pk_seq, SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN as ten from ERP_TAIKHOANKT where npp_fk = 1 and TRANGTHAI = 1 order by soHieuTaiKhoan";
		geso.traphaco.center.util.Erp_Item.getListFromQuery(db, query, this.taiKhoanList);
	}

	public String getStt() {
		return stt;
	}

	public void setStt(String stt) {
		this.stt = stt;
	}

	public String getMaSo() {
		return maSo;
	}

	public void setMaSo(String maSo) {
		this.maSo = maSo;
	}

	public String getTenTieuChi() {
		return tenTieuChi;
	}

	public void setTenTieuChi(String tenTieuChi) {
		this.tenTieuChi = tenTieuChi;
	}

	public int getIsCongThuc() {
		return isCongThuc;
	}

	public void setIsCongThuc(int isCongThuc) {
		this.isCongThuc = isCongThuc;
	}

	public String getCongThuc() {
		return congThuc;
	}

	public void setCongThuc(String congThuc) {
		this.congThuc = congThuc;
	}

	public int getInDam() {
		return inDam;
	}

	public void setInDam(int inDam) {
		this.inDam = inDam;
	}

	public int getIsCongThucExcel() {
		return isCongThucExcel;
	}

	public void setIsCongThucExcel(int isCongThucExcel) {
		this.isCongThucExcel = isCongThucExcel;
	}

	public List<Erp_Item> getTaiKhoanList() {
		return taiKhoanList;
	}

	public void setTaiKhoanList(List<Erp_Item> taiKhoanList) {
		this.taiKhoanList = taiKhoanList;
	}

	public List<ErpKhaiBaoMauChiTiet> getChiTietList() {
		return chiTietList;
	}

	public void setChiTietList(List<ErpKhaiBaoMauChiTiet> chiTietList) {
		this.chiTietList = chiTietList;
	}

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}

	public void setThuyetMinh(String thuyetMinh) {
		this.thuyetMinh = thuyetMinh;
	}

	public String getThuyetMinh() {
		return thuyetMinh;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void DbClose() {
		if (this.db != null)
			db.shutDown();
	}	
}
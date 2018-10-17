package geso.traphaco.erp.beans.baohong.imp;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ErpBaoHong
{
	private String id;
	private String ccdcId;
	private String tenCcdc;
	private String ghiChu;
	private String trangThai;
	private String ngayTao;
	private String ngaySua;
	private String nguoiTao;
	private String nguoiSua;
	private String congTyId;
	private String ngayBaoHong;
	private String msg;
	
	private List<Erp_Item> ccdcList;
	private dbutils db;

	public ErpBaoHong()
	{
		id = "";
		this.ccdcId = "";
		this.tenCcdc = "";
		this.ghiChu = "";
		this.trangThai = "";
		this.ngayTao = "";
		this.ngaySua = "";
		this.nguoiTao = "";
		this.nguoiSua = "";
		this.congTyId = "";
		this.ngayBaoHong = "";
		this.msg = "";
		
		this.ccdcList = new ArrayList<Erp_Item>();
		this.db = new dbutils();
	}
	
	public void init()
	{
		if (this.id.trim().length() > 0)
		{
			String query = 
				"select cc.PK_SEQ, isNull(cc.ngayBaoHong, '') ngayBaoHong\n" +
				"from ERP_CONGCUDUNGCU cc where cc.PK_SEQ = " + this.id;
			
			ResultSet rs = null;
			
			try {
				rs = this.db.get(query);
				
				if (rs != null)
					if (rs.next())
					{
						this.id = rs.getString("PK_SEQ");
						this.ngayBaoHong = rs.getString("ngayBaoHong");
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
		initList();
	}
	
	private void initList() {

		List<Integer> l = new ArrayList<Integer>();
		geso.traphaco.center.util.Utility.GetThangKhoaSoMax(this.db, l);
		
		this.ccdcList.clear();
		String query = 
			"SELECT cc.PK_SEQ, (cc.ma + ' - ' + cc.DIENGIAI) as ten\n" +
			"FROM ERP_CONGCUDUNGCU cc\n" +
			"where ((cc.isBaoHong is Null or cc.isBaoHong = 0) " + 
			(this.id.trim().length() > 0 ? ("or cc.PK_SEQ = " + this.id) : "") + ")\n" +
			"and cc.CONGTY_FK = " + this.congTyId;
		
		System.out.println("query lay ccdc : \n " + query + "\n===================");
		ResultSet rs = null;
		
		try {
			rs = this.db.get(query);
			
			if (rs != null)
				while (rs.next())
				{					
					Erp_Item item = new Erp_Item(rs.getString("PK_SEQ"), rs.getString("TEN"));
					this.ccdcList.add(item);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public boolean updateCCDC() {
		try {
			this.db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_CONGCUDUNGCU " +
					"set isBaoHong = '1', ngayBaoHong = '" + this.ngayBaoHong + "'\n" +
					"where pk_seq = " + this.id;
			if (!this.db.update(query))
			{
				this.msg = "N1.1 Không thể báo hỏng";
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public void DbClose()
	{
		if (this.db != null)
			this.db.shutDown();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCcdcId() {
		return ccdcId;
	}

	public void setCcdcId(String ccdcId) {
		this.ccdcId = ccdcId;
	}

	public String getTenCcdc() {
		return tenCcdc;
	}

	public void setTenCcdc(String tenCcdc) {
		this.tenCcdc = tenCcdc;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}

	public String getNgaySua() {
		return ngaySua;
	}

	public void setNgaySua(String ngaySua) {
		this.ngaySua = ngaySua;
	}

	public String getNguoiTao() {
		return nguoiTao;
	}

	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}

	public String getNguoiSua() {
		return nguoiSua;
	}

	public void setNguoiSua(String nguoiSua) {
		this.nguoiSua = nguoiSua;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
	
	public List<Erp_Item> getCcdcList() {
		return ccdcList;
	}

	public void setCcdcList(List<Erp_Item> ccdcList) {
		this.ccdcList = ccdcList;
	}

	public void setNgayBaoHong(String ngayBaoHong) {
		this.ngayBaoHong = ngayBaoHong;
	}

	public String getNgayBaoHong() {
		return ngayBaoHong;
	}

	public static String getLastDayOfMonth(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate;
		Calendar c = null;
		try {
			convertedDate = dateFormat.parse(date);
			c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Date lastDayOfMonth = c.getTime();  
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		return sdf.format(lastDayOfMonth);
	}
}
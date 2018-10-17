

package geso.traphaco.center.beans.nhiemvu.imp;
import java.sql.ResultSet;
import geso.traphaco.center.beans.nhiemvu.INhiemVuNhanVienChiTiet;
import geso.traphaco.distributor.db.sql.dbutils;

public class NhiemVuNhanVienChiTiet implements INhiemVuNhanVienChiTiet {

	String nvId;
	String nvTen;
	String doituong;
	float thuong;
	float soluong;
	
	dbutils db;
	 
	public NhiemVuNhanVienChiTiet() {
		db = new dbutils();
		this.nvId = "";
		this.nvTen = "";
		this.doituong = "";
		this.thuong = 0.0f;
		this.soluong=0.0f;
	}
	
	public NhiemVuNhanVienChiTiet(String nvId, String nvTen, String doituong) {
		db = new dbutils();
		this.nvId = nvId;
		this.nvTen = nvTen;
		this.doituong = doituong;
		this.thuong = 0.0f;
		this.soluong=0.0f;
	}
	 
	public float getSoluong()
	{
		return soluong;
	}

	public void setSoluong(float soluong)
	{
		this.soluong = soluong;
	}

	public NhiemVuNhanVienChiTiet(String[] param) {
		db = new dbutils();
		this.nvId = param[0];
		this.nvTen = param[1];
		this.doituong = param[2];
		this.thuong = Float.parseFloat(param[3]);
	}
	
	@Override
	public void setNvId(String value) {
		this.nvId = value;
	}

	@Override
	public String getNvId() {
		return this.nvId;
	}

	@Override
	public void setNvTen(String value) {
		this.nvTen = value;
	}

	@Override
	public String getNvTen() {
		return this.nvTen;
	}

	@Override
	public void setDoiTuong(String value) {
		this.doituong = value;
	}

	@Override
	public String getDoiTuong() {
		return this.doituong;
	}

	@Override
	public void setThuong(float value) {
		this.thuong = value;
	}

	@Override
	public float getThuong() {
		return this.thuong;
	}
	
	public void init(String nvnvId) {
		String query = "SELECT A.TIEUCHI as NVTEN, A.DOITUONG, B.THUONG,ISNULL(B.SOLUONG,0) AS SOLUONG FROM NHIEMVU A INNER JOIN NHIEMVUNHANVIEN_CHITIET B ON A.PK_SEQ = B.NHIEMVU_FK WHERE B.NHIEMVUNHANVIEN_FK='" + nvnvId + "' AND B.NHIEMVU_FK = '"+ this.nvId + "'";
		ResultSet rs = this.db.get(query);
		try{
			
			rs.next();
			this.nvTen = rs.getString("NVTEN");
			this.doituong = rs.getString("DOITUONG");
			this.thuong = rs.getFloat("THUONG");
			this.soluong=rs.getFloat("SoLuong");
			rs.close();
			
			System.out.println("[NhiemVuNhanVienChiTiet.init] " + this.toString());
			
		} catch(Exception e) {
			System.out.println("[NhiemVuNhanVienChiTiet.init] Exception Message: " + e.getMessage());
		}
	}	

	@Override
	public void closeDB() {
		if(this.db != null) {
			this.db.shutDown();
		}
	}
	
	public String toString() {
		return "NhiemVu {nvId: " + this.nvId+", thuong: " + this.thuong+"}";
	}

	
}

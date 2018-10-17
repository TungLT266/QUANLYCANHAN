package geso.traphaco.erp.beans.khaibaomau.imp;

import geso.traphaco.center.db.sql.Idbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ErpKhaiBaoMauChiTiet
{
	private String id;
	private String maSo;
	private int loaiTSNV;
	private int laySoDu;
	private int laySoDuNo;
	private int laySoDuCo;
	private int khongAm;
	private String soHieuTaiKhoan;
		
	public ErpKhaiBaoMauChiTiet()
	{
		this.id = "";
		this.maSo = "";
		this.loaiTSNV = 0;
		this.laySoDu = 0;
		this.laySoDuNo = 0;
		this.laySoDuCo = 0;
		this.khongAm = 0;
		this.soHieuTaiKhoan = "";
	}

	public static String getListFromQuery(Idbutils db, String query, List<ErpKhaiBaoMauChiTiet> list)
	{
		String msg = "";
		
		if (list == null)
			list = new ArrayList<ErpKhaiBaoMauChiTiet>();
		else
			list.clear();	

		ResultSet rs = null;
		
		try {
			rs = db.get(query);
			
			if (rs != null)
				while (rs.next())
				{
					ErpKhaiBaoMauChiTiet item = new ErpKhaiBaoMauChiTiet();
					item.setId(rs.getString("id"));
					item.setMaSo(rs.getString("MASO"));
					item.setLoaiTSNV(rs.getInt("loaiTSNV"));
					item.setLaySoDu(rs.getInt("laySoDu"));
					item.setLaySoDuNo(rs.getInt("laySoDuNo"));
					item.setLaySoDuCo(rs.getInt("laySoDuCo"));
					item.setKhongAm(rs.getInt("khongAm"));
					item.setSoHieuTaiKhoan(rs.getString("soHieuTaiKhoan"));
					list.add(item);
				}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "GLFQ1.0 Lỗi đổ list";
		}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return msg;
	}
	
	public static ErpKhaiBaoMauChiTiet isContainValue(List<ErpKhaiBaoMauChiTiet> list, String value)
	{
		for (ErpKhaiBaoMauChiTiet item : list)
		{
			if (item.getSoHieuTaiKhoan().trim().equals(value))
				return item;
		}
		
		return null;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaSo() {
		return maSo;
	}

	public void setMaSo(String maSo) {
		this.maSo = maSo;
	}

	public int getLoaiTSNV() {
		return loaiTSNV;
	}

	public void setLoaiTSNV(int loaiTSNV) {
		this.loaiTSNV = loaiTSNV;
	}

	public int getLaySoDu() {
		return laySoDu;
	}

	public void setLaySoDu(int laySoDu) {
		this.laySoDu = laySoDu;
	}

	public int getLaySoDuNo() {
		return laySoDuNo;
	}

	public void setLaySoDuNo(int laySoDuNo) {
		this.laySoDuNo = laySoDuNo;
	}

	public int getLaySoDuCo() {
		return laySoDuCo;
	}

	public void setLaySoDuCo(int laySoDuCo) {
		this.laySoDuCo = laySoDuCo;
	}

	public int getKhongAm() {
		return khongAm;
	}

	public void setKhongAm(int khongAm) {
		this.khongAm = khongAm;
	}

	public String getSoHieuTaiKhoan() {
		return soHieuTaiKhoan;
	}

	public void setSoHieuTaiKhoan(String soHieuTaiKhoan) {
		this.soHieuTaiKhoan = soHieuTaiKhoan;
	}
}
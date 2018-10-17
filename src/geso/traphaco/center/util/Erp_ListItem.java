package geso.traphaco.center.util;

import geso.traphaco.center.db.sql.Idbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Erp_ListItem
{
	private String id;
	private String ma;
	private String ten;
	private String ngayTao;
	private String ngaySua;
	private String nguoiTao;
	private String nguoiSua;
	private String trangThai;
	private String tenTrangThai;
	private String field1;
	
	public Erp_ListItem()
	{
		this.id = "";
		this.ma = "";
		this.setTen("");
		this.ngayTao = "";
		this.ngaySua = "";
		this.nguoiTao = "";
		this.nguoiSua = "";
		this.trangThai = "";
		this.tenTrangThai = "";
		this.field1 = "";
	}
	
	public Erp_ListItem(String id, String ma, String ten, String ngayTao
			, String ngaySua, String nguoiTao, String nguoiSua, String trangThai)
	{
		this.id = id;
		this.ma = ma;
		this.setTen(ten);
		this.ngayTao = ngayTao;
		this.ngaySua = ngaySua;
		this.nguoiTao = nguoiTao;
		this.nguoiSua = nguoiSua;
		this.trangThai = trangThai;
		this.tenTrangThai = "";
		this.field1 = "";
	}
	
	public static String getListFromQuery(Idbutils db, String query, List<Erp_ListItem> list)
	{
		ResultSet rs = null;
		
		rs = db.get(query);
		return  getListFromQuery(rs, list);
	}

	public static String getListFromQuery(ResultSet rs, List<Erp_ListItem> list)
	{
		String msg = "";
		
		if (list == null)
			list = new ArrayList<Erp_ListItem>();
		else
			list.clear();	
		
		try {
			if (rs != null)
				while (rs.next())
				{
					String id = rs.getString("id");
					String ma = rs.getString("ma");
					String ten = rs.getString("ten");
					String ngayTao = rs.getString("ngayTao");
					String ngaySua = rs.getString("ngaySua");
					String nguoiTao = rs.getString("nguoiTao");
					String nguoiSua = rs.getString("nguoiSua");
					String trangThai = rs.getString("trangThai");
//					String tenTrangThai = rs.getString("tenTrangThai");
//					String field1 = rs.getString("field1");
					
					Erp_ListItem item = new Erp_ListItem(id, ma, ten, ngayTao
							, ngaySua, nguoiTao, nguoiSua, trangThai);
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

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
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
	public String getNguoitSua() {
		return nguoiSua;
	}
	public void setNguoitSua(String nguoiSua) {
		this.nguoiSua = nguoiSua;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTen() {
		return ten;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField1() {
		return field1;
	}

	public void setTenTrangThai(String tenTrangThai) {
		this.tenTrangThai = tenTrangThai;
	}

	public String getTenTrangThai() {
		return tenTrangThai;
	}
}
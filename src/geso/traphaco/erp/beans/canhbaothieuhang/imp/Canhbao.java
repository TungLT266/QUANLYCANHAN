package geso.traphaco.erp.beans.canhbaothieuhang.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.beans.canhbaothieuhang.ICanhbao;
import geso.traphaco.erp.db.sql.dbutils;

public class Canhbao implements ICanhbao 
{
	String chungtu;
	String khachhang;
	String spId;
	String sanpham;
	String ngaygiao;
	String ngaygiaoOLD;
	String soluonggiao;
	String thieu;
	
	ResultSet lsxRs;
	private String maKH;
	private String spMa;

	public Canhbao()
	{
		this.chungtu = "";
		this.khachhang = "";
		this.spId = "";
		this.sanpham = "";
		this.ngaygiao = "";
		this.ngaygiaoOLD = "";
		this.soluonggiao = "";
		this.thieu = "";
	}
	
	public String getSanPhamMa() {
		return spMa!=null?spMa:"";
	}

	public void setSanPhamMa(String spMa) {
		this.spMa = spMa;
	}
	
	public String getMaKH() {
		return maKH!=null?maKH:"";
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getChungtu() {
		
		return this.chungtu;
	}

	
	public void setChungtu(String chungtuId) {
		
		this.chungtu = chungtuId;
	}

	
	public String getKhachhang() {
		
		return this.khachhang;
	}

	
	public void setKhachhang(String khId) {
		
		this.khachhang = khId;
	}

	
	public String getSanpham() {
		
		return this.sanpham;
	}

	
	public void setSanpham(String sanpham) {
		
		this.sanpham = sanpham;
	}

	
	public String getNgaygiao() {
		
		return this.ngaygiao;
	}

	
	public void setNgaygiao(String ngaygiao) {
		
		this.ngaygiao = ngaygiao;
	}

	
	public String getSoluonggiao() {
		
		return this.soluonggiao;
	}

	
	public void setSoluonggiao(String soluonggiao) {
		
		this.soluonggiao = soluonggiao;
	}

	
	public String getThieu() {
		
		return this.thieu;
	}

	
	public void setThieu(String thieu) {
		
		this.thieu = thieu;
	}

	
	public ResultSet getLsxRs() {
		
		String query = " select  a.PK_SEQ AS chungtu, '' AS khMa,  a.NGAYDUKIENHT, b.SOLUONG , a.TRANGTHAI   " +
		" from ERP_LENHSANXUAT_GIAY a inner join ERP_LENHSANXUAT_SANPHAM b on a.PK_SEQ = b.LENHSANXUAT_FK  " +
		" inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " +
		" where a.NGAYDUKIENHT >= '" + this.getNgaygiao() + "'  and b.SANPHAM_FK = '" + this.spId + "' " +
		//String.format(" AND A.PK_SEQ LIKE '%%%s%%'", this.getChungtu()) +
		" order by a.NGAYDUKIENHT asc ";

		//System.out.println("__Lay LSX: " + query);
		dbutils db = new dbutils();
		ResultSet rsLsx = db.getScrol(query);
		if(rsLsx != null)
		{
			this.setLsxRs(rsLsx);
		}
		return this.lsxRs;
}

	
	public void setLsxRs(ResultSet lsxRs) {
		
		this.lsxRs = lsxRs;
	}


	public String getSanphamId() {
		
		return this.spId;
	}

	public void setSanphamId(String spId) {
		
		this.spId = spId;
	}

	public String getNgaygiaoOld() {
		
		return this.ngaygiaoOLD;
	}

	public void setNgaygiaoOld(String ngaygiaoOld) {
		
		this.ngaygiaoOLD = ngaygiaoOld;
	}

	public void DBClose()
	{
		try {
			if (this.lsxRs != null)
				this.lsxRs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}

package geso.traphaco.erp.beans.erp_dieuchinhsolokhott.imp;


import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.erp_dieuchinhsolokhott.IErp_DieuChinhSoLoKho_List;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;

public class Erp_DieuChinhSoLoKhoTT_List extends Phan_Trang implements IErp_DieuChinhSoLoKho_List
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String KhoTT_FK;
	String TuNgay;
	String DenNgay;
	String TrangThai;
	String MSG;
	String Khu;
	ResultSet rsSanPham;
	ResultSet rsKho;
	ResultSet rsDieuChinhTonKho;
	ResultSet RsKhu;
	dbutils db;

	public Erp_DieuChinhSoLoKhoTT_List()
	{
		this.MSG = "";
		this.KhoTT_FK = "";
		this.TuNgay = "";
		this.DenNgay = "";
		this.TrangThai = "";
		this.Khu = "";
		db = new dbutils();
	}

	public void init(String search)
	{
		String query =  " Select d.PK_SEQ,d.NgayDieuChinh,d.LyDoDieuChinh," +
						" d.TrangThai,d.NgayTao,isnull(d.NgaySua,'') as NgaySua," +
						" isnull(nt.ten,'') as NguoiTao,isnull(ns.Ten,'') as NguoiSua"
					+ 	" from ERP_DieuChinhSoLoKhoTT d "
					+ 	" Left join  nhanvien nt on nt.PK_SEQ =d.NguoiTao "
					+ 	" Left Join NhanVien ns on ns.PK_Seq=d.NguoiSua "
					+	" WHERE 1=1 ";
		if (TrangThai.length() > 0)
		{
			query += " and d.trangthai='" + TrangThai + "' ";

		}

		if (TuNgay.length() > 0)
		{
			query += " and ngaydieuchinh >='" + TuNgay + "' ";
		}
		
		if (DenNgay.length() > 0)
		{
			query += " and ngaydieuchinh<='" + DenNgay + "' ";
		}

		this.rsDieuChinhTonKho = createSplittingDataNew(this.db, 50, 10, " NGAYDIEUCHINH DESC ,TRANGTHAI  ,PK_SEQ DESC", query);
		
		System.out.println(query);
		
//		query = " Select PK_SEQ,Ten from ERP_KhoTT";
//		this.rsKho = db.get(query);
//		query = " Select PK_SEQ ,Ten From Erp_ViTriKho ";
//		this.RsKhu = this.db.get(query);

	}

	public String getKhoTT_FK()
	{
		return this.KhoTT_FK;
	}

	public void setKhoTT_FK(String khoTT_FK)
	{
		this.KhoTT_FK = khoTT_FK;
	}

	public String getTuNgay()
	{
		return this.TuNgay;
	}

	public void setDenNgay(String denngay)
	{
		this.DenNgay = denngay;
	}

	public String getDenNgay()
	{
		return this.DenNgay;
	}

	public void setTuNgay(String tungay)
	{
		this.TuNgay = tungay;
	}

	public String getTrangThai()
	{
		return this.TrangThai;
	}

	public void setTrangThai(String trangThai)
	{
		this.TrangThai = trangThai;
	}

	public ResultSet getRsKho()
	{
		return this.rsKho;
	}

	public void setRsKho(ResultSet rsKho)
	{
		this.rsKho = rsKho;
	}

	public ResultSet getRsSanPham()
	{
		return this.rsSanPham;
	}

	public void setRsSanPham(ResultSet rsSanPham)
	{
		this.rsSanPham = rsSanPham;
	}

	public ResultSet getRsDieuChinhTonKho()
	{
		return this.rsDieuChinhTonKho;
	}

	public void setRsDieChinhTonKho(ResultSet rsDieuChinhTonKho)
	{
		this.rsDieuChinhTonKho = rsDieuChinhTonKho;
	}

	public String getMSG()
	{
		return this.MSG;
	}

	public void setMSG(String MSG)
	{
		this.MSG = MSG;
	}

	public ResultSet getRsKhu()
	{

		return this.RsKhu;
	}

	public void setRsKhu(ResultSet RsKhu)
	{
		this.RsKhu = RsKhu;

	}

	public String getKhu()
	{

		return this.Khu;
	}

	public void setKhu(String Khu)
	{
		this.Khu = Khu;

	}

	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
	try{
			
			if(rsSanPham!=null){
				rsSanPham.close();
			}
			if(rsKho!=null){
				rsKho.close();
			}
			if(rsDieuChinhTonKho!=null){
				rsDieuChinhTonKho.close();
			}
			if(RsKhu!=null){
				RsKhu.close();
			}
			
			
			
		}catch(Exception err){
			err.printStackTrace();
		}
		finally
		{
			if(this.db!=null)
				this.db.shutDown();
		}
	}
}

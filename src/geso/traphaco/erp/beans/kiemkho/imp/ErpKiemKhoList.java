package geso.traphaco.erp.beans.kiemkho.imp;


import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.erp_dieuchinhsolokhott.IErp_DieuChinhSoLoKho_List;
import geso.traphaco.erp.beans.kiemkho.IErpKiemKhoList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;

public class ErpKiemKhoList extends Phan_Trang implements IErpKiemKhoList
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
	String tuthang;
	String denthang;
	String tunam;
	String dennam;
	String usedId;

	public ErpKiemKhoList()
	{
		this.MSG = "";
		this.KhoTT_FK = "";
		this.TuNgay = "";
		this.DenNgay = "";
		this.TrangThai = "";
		this.Khu = "";
		//this.usedId="";
		 tuthang = "0";
		 denthang = "0";
		 tunam = "0";
		 dennam = "0";
		db = new dbutils();
	}

	public void init(String search)
	{
		Utility util = new Utility();
		String query = " Select d.PK_SEQ,THANG,NAM,d.DIENGIAI ,K.TEN As TenKho "+
					" ,d.KhoTT_FK,d.TrangThai,d.NgayTao,isnull(d.NgaySua,'') as NgaySua,  "+
					" isnull(nt.ten,'') as NguoiTao,isnull(ns.Ten,'') as NguoiSua,ngaykiem "+
					" from ERP_KIEMKHO d  "+
					" Left join  nhanvien nt on nt.PK_SEQ =d.NguoiTao "+ 
					" Left Join NhanVien ns on ns.PK_Seq=d.NguoiSua  "+
					" inner Join erp_KhoTT k on k.PK_SEQ=d.KhoTT_FK  "+
					" WHERE 1=1 ";
		if (TrangThai.length() > 0)
		{
			query += " and d.trangthai='" + TrangThai + "' ";

		}
		if (KhoTT_FK.length() > 0)
		{
			query += " and d.khott_fk='" + KhoTT_FK + "' ";
		}
		
		if(!this.tuthang.equals("00") && !this.tunam.equals("0")){
			query += " and substring(d.ngaykiem,1,7) >='"+this.tunam+"-"+this.tuthang+"' ";
		}
		
		if(!this.denthang.equals("00") && !this.dennam.equals("0")){
			query += " and substring(d.ngaykiem,1,7) <='"+this.dennam+"-"+this.denthang+"' ";
		}
		
 
		
		this.rsDieuChinhTonKho = createSplittingDataNew(this.db, 50, 10, " PK_SEQ DESC,TRANGTHAI", query);
		query = " Select PK_SEQ,Ten from ERP_KhoTT where trangthai='1' and pk_seq in "+util.quyen_khott(this.usedId);
		this.rsKho = db.get(query);
		

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

	
	public void DbClose() {
		
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

	
	public void setFromMonth(String month) {
		
		this.tuthang=month;
	}

	
	public String getFromMonth() {
		
		return this.tuthang;
	}

	
	public void setToMonth(String month) {
		
		this.denthang=month;
	}

	
	public String getToMonth() {
		
		return this.denthang;
	}

	
	public void setFromYear(String fromyear) {
		
		this.tunam=fromyear;
	}

	
	public String getFromYear() {
		
		return this.tunam;
	}

	
	public void setToYear(String toyear) {
		
		this.dennam=toyear;
	}

	
	public String getToYear() {
		
		return this.dennam;
	}

	
	public void Delete(String Id) {
		
		try{
			String sql="update erp_kiemkho set trangthai='2' where pk_seq="+Id +" and trangthai=0";
			if(db.updateReturnInt(sql)!=1)
				this.MSG="Không thể xóa kiểm kho, vui lòng kiểm tra lại";
			
		}catch(Exception err){
			this.MSG="Error : "+ err.toString();
		}
	}

	public String getUsedId() {
		return this.usedId;
	}

	public void setUsedId(String usedid) {
		this.usedId=usedid;
		
	}
}

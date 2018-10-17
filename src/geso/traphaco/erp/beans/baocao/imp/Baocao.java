package geso.traphaco.erp.beans.baocao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.db.sql.*;
import geso.traphaco.center.util.*;
import geso.traphaco.erp.beans.baocao.IBaocao;

public class Baocao implements IBaocao 
{
	String userId;
	String userTen;
	ResultSet LsxRs;
	ResultSet loaiSpRs;
	ResultSet spRs;
	ResultSet khoRs;
	ResultSet khonhanRs;
	String loaibaocao="1";
	ResultSet RsReportName;
	
	ResultSet lsxRs;
	String lsxId;
	String ReportName;
	
	String congtyId;
	
	public String getLoaibaocao() {
		return loaibaocao;
	}


	public void setLoaibaocao(String loaibaocao) {
		this.loaibaocao = loaibaocao;
	}


	public ResultSet getKhonhanRs() {
		return khonhanRs;
	}


	public void setKhonhanRs(ResultSet khonhanRs) {
		this.khonhanRs = khonhanRs;
	}

	ResultSet chungloaiRs;
	ResultSet dvkdRs;
	String Layhangkho_CXL;
	String lenhsanxuat;
	String loaisanphamIds;
	String Check_SpCoPhatSinh;
	String spIds;
	String khoIds;
	String khonhanIds;
	public String getKhonhanIds() {
		return khonhanIds;
	}


	public void setKhonhanIds(String khonhanIds) {
		this.khonhanIds = khonhanIds;
	}

	String khoId_CXL;
	String chungloaiIds;
	String khoTen;
	String dvkdIds;
	String NhamayId;
	ResultSet giaRs;
	String tungay;
	String denngay;
	String msg;
	String flag;
	String laychokiem;
	ResultSet ndnhapRs;
	String ndnhapIds;
	ResultSet RsNhamay;
	ResultSet ndxuatRs;
	String ndxuatIds;
	String pivot = "0";
	String Xemtheolo;	
	ResultSet maspRS;
	String maspIds;
	String IsHoDaPhanBo;
	String viewtchitiet="";
	dbutils db;
	Utility util;
	String lsxID;
	String year;
	String month;
	ResultSet doituongRs;
	String doituongId;
	String khotong;
	public String getKhotong() {
		return khotong;
	}


	public void setKhotong(String khotong) {
		this.khotong = khotong;
	}


	public String getLsxID() {
		return lsxID;
	}


	public void setLsxID(String lsxID) {
		this.lsxID = lsxID;
	}


	public String getSoluong() {
		return soluong;
	}


	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}


	public String getMaSp() {
		return maSp;
	}


	public void setMaSp(String maSp) {
		this.maSp = maSp;
	}


	public String getTenSp() {
		return tenSp;
	}


	public void setTenSp(String tenSp) {
		this.tenSp = tenSp;
	}


	String soluong;
	String maSp;
	String tenSp;
	ResultSet nccRs;
	String nccIds;
	
	public Baocao()
	{
		this.userId = "";
		this.NhamayId="";
		this.IsHoDaPhanBo="";
		this.userTen = "";
		this.loaisanphamIds = "";
		this.dvkdIds = "";
		this.chungloaiIds = "";
		this.spIds = "";
		this.khoIds = "";
		this.Layhangkho_CXL="";
		this.khoTen = "";
		this.tungay = "";
		this.NhamayId="";
		this.denngay = "";
		this.msg = "";
		this.khoId_CXL="";
		this.flag = "1";
		this.Xemtheolo="0";
		this.laychokiem = "0";
		this.maspIds = "";
		this.ndnhapIds = "";
		this.ndxuatIds= "";
		this.Check_SpCoPhatSinh="";
		this.viewtchitiet="";
		this.nccIds = "";
		this.congtyId = "";
		this.month = "";
		this.year = "";
		this.db = new dbutils();
		this.util = new Utility();
	}
	
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;	
	}

	public String getUserTen() 
	{
		return this.userTen;
	}

	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
	}
	
	public ResultSet getLoaiSanPhamRs() 
	{
		return this.loaiSpRs;
	}
	
	public void setLoaiSanPhamRs(ResultSet loaisp) 
	{
		this.loaiSpRs = loaisp;
	}

	public ResultSet getDvkdRs() 
	{
		return this.dvkdRs;
	}
	
	public void setDvkdRs(ResultSet dvkdRs) 
	{
		this.dvkdRs = dvkdRs;
	}

	public String getLoaiSanPhamIds() 
	{
		return this.loaisanphamIds;
	}

	public void setLoaiSanPhamIds(String loaispIds)
	{
		this.loaisanphamIds = loaispIds; 
	}

	public String getDvkdIds() 
	{
		return this.dvkdIds;
	}

	public void setDvkdIds(String dvkdIds)
	{
		this.dvkdIds = dvkdIds; 
	}

	public String getTuNgay()
	{
		return this.tungay;
	}

	public void setTuNgay(String tungay)
	{
		this.tungay = tungay;
	}

	public String getDenNgay() 
	{
		return this.denngay;
	}

	public void setDenNgay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	public ResultSet getSanPhamRs() 
	{
		return this.spRs;
	}

	public void setSanPhamRs(ResultSet sanpham) 
	{
		this.spRs = sanpham;	
	}

	public String getSanPhamIds() 
	{	
		return this.spIds;
	}

	public void setSanPhamIds(String spIds) 
	{
		this.spIds = spIds;
	}

	public void createRsBCGDLSX() {
		String query = "select pk_seq, ten + ', ' + diachi as khoTen from erp_khott where trangthai = '1' and pk_seq in "+util.quyen_khott(this.userId);
		System.out.println("::: LAY KHO LSX : "+ query);
		this.khoRs = db.get(query);
		query =  " select pk_seq, ma, ten  from ERP_SanPham where trangthai = '1'";
		System.out.println("::: LAY SP LSX : "+ query);
		this.spRs = db.get(query);
	}
	
	public void createRs() 
	{
		// LẤY kho
		String query = "select pk_seq, ten + ', ' + diachi as khoTen from erp_khott where trangthai = '1' and pk_seq in "+util.quyen_khott(this.userId);
		System.out.println("::: LAY KHO : "+ query);
		this.khoRs = db.get(query);
		this.khonhanRs = db.get(query);
		
		query = "SELECT DISTINCT LSP.PK_SEQ, LSP.MA, LSP.MA + ', ' + LSP.TEN AS TEN " +
				"FROM ERP_LOAISANPHAM LSP " +
				"WHERE LSP.TRANGTHAI = 1 ";
		
		if(this.khoIds.trim().length() > 0 )
			  query+=" AND LSP.PK_SEQ  in ( select loaisanpham_fk from ERP_KHOTT_LOAISANPHAM where khott_fk in ( " + this.khoIds + " ) )";
		
		this.loaiSpRs = this.db.get(query);
		System.out.println("::: LAY LSP : "+ query);
		
		// LẤY dvkd
		query = "SELECT DISTINCT DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH AS DVKD, DVKD.DIENGIAI  " +
				"FROM DONVIKINHDOANH DVKD " +
				"INNER JOIN ERP_SANPHAM SP ON DVKD.PK_SEQ = SP.DVKD_FK " +
				"INNER JOIN ERP_KHOTT_SANPHAM KHOSP ON KHOSP.SANPHAM_FK = SP.PK_SEQ " ;
		
		if(this.khoIds.trim().length() > 0 )
		{
			query+=" AND KHOSP.KHOTT_FK in ( " + this.khoIds + ")";
		}

		if(this.loaisanphamIds.trim().length() > 0)
			query += " and SP.loaisanpham_fk in (" + this.loaisanphamIds + ") ";
		
		
		//this.dvkdRs = this.db.get(query);
		System.out.println("::: DVKD : "+ query);
		
		
		// LẤY SP
		query = " select distinct MA, MA as TEN from ERP_SANPHAM where pk_seq > 0 ";
		if(this.khoIds.trim().length() > 0)
			query += " and pk_seq in ( select sanpham_fk from ERP_KHOTT_SANPHAM where khott_fk  in (" + this.khoIds + " )  ) ";
		
		if(this.loaisanphamIds.trim().length() > 0)
			query += " and loaisanpham_fk in (" + this.loaisanphamIds + ") ";
		
		if(this.dvkdIds.trim().length() > 0)
			query += " and DVKD_FK in (" + this.dvkdIds + ") ";
		
		query += "order by MA ASC";
		
		
		System.out.println("::: LAY SP : "+ query);
		this.maspRS = db.get(query);

		if(this.khoIds.length() > 0)
		{
			query = " select pk_seq, ma, ten " +
					" from ERP_SanPham " +
					" where trangthai = '1' and pk_seq in ( select sanpham_fk from ERP_KhoTT_SanPham where khott_fk  in (" + this.khoIds + ") )" ;
			
			if(this.loaisanphamIds.length() > 0)
			{
				query += " and loaisanpham_fk in (" + this.loaisanphamIds + ")";
			}
			
			if(this.chungloaiIds.length() > 0)
			{
				query += " and chungloai_fk in (" + this.chungloaiIds + ") ";
			}
			
			if(this.maspIds.trim().length() > 0)
			{
				query += " and MA in (" + this.maspIds + ") ";
			}
			
			if(this.dvkdIds.length() >0 ){
				query += " and DVKD_FK in (" + this.dvkdIds + ") ";
			}
			this.spRs = db.get(query);
		}
		
		query="select pk_seq, isnull(ma, ten) as ma, ten from chungloai where trangthai = '1' ";
		this.chungloaiRs = this.db.get(query);
		
		// Lay Noi dung nhap
		 query =" select pk_seq, TEN \n"+
				" from ERP_NOIDUNGNHAP \n"+
				" where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('NK') 	\n"+			
				"union all \n"+
				" select distinct '300000' as pk_seq, N'Nhập nguyên liệu' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300001' as pk_seq, N'Nhập chuyển kho' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300002' as pk_seq, N'Điều chỉnh tồn kho' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300003' as pk_seq, N'Kiểm định chất lượng từ LSX' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300004' as pk_seq, N'Kiểm định chất lượng từ nhận hàng' as TEN  from ERP_NOIDUNGNHAP ";
		 
		System.out.println(" noi dung nhap : "+ query);
		this.ndnhapRs = db.get(query);
		
		// Lay Noi dung xuat
		 query =" select pk_seq, TEN \n"+
				" from ERP_NOIDUNGNHAP \n"+
				" where  trangthai = '1' and upper(substring(ma, 0, 2)) = upper('X')  \n"+			
				"union all \n"+
				" select distinct '400000' as pk_seq, N'Xuất nguyên liệu' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '400001' as pk_seq, N'Điều chỉnh tồn kho' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '400002' as pk_seq, N'Tiêu hao nguyên liệu' as TEN  from ERP_NOIDUNGNHAP ";
		 
		 
		this.ndxuatRs = db.get(query);
		
		query=" SELECT PK_SEQ,TENNHAMAY AS TEN FROM ERP_NHAMAY ";
		this.RsNhamay=db.get(query);
	 
		query="select pk_seq, ma + ' - ' + ten as ten from ERP_NHACUNGCAP where trangthai = '1' order by ten asc ";
		this.nccRs = this.db.get(query);
		
	}
	
	
	public void createRs_XUATKHO() 
	{
		String query = "select pk_seq, ten + ', ' + diachi as khoTen from erp_khott where trangthai = '1' and pk_seq in "+util.quyen_khott(this.userId);
		System.out.println("::: LAY KHO : "+ query);
		this.khoRs = db.get(query);
		this.khonhanRs= db.get(query);
		
		query = "SELECT DISTINCT LSP.PK_SEQ, LSP.MA, LSP.MA + ', ' + LSP.TEN AS TEN " +
				"FROM ERP_LOAISANPHAM LSP " +
				"WHERE LSP.TRANGTHAI = 1 ";
		
		if(this.khoIds.trim().length() > 0 )
			  query+=" AND LSP.PK_SEQ  in ( select loaisanpham_fk from ERP_KHOTT_LOAISANPHAM where khott_fk in ( " + this.khoIds + " ) )";
		
		this.loaiSpRs = this.db.get(query);
		System.out.println("::: LAY LSP : "+ query);
		
		
		query = "SELECT DISTINCT DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH AS DVKD, DVKD.DIENGIAI  " +
				"FROM DONVIKINHDOANH DVKD " +
				"INNER JOIN ERP_SANPHAM SP ON DVKD.PK_SEQ = SP.DVKD_FK " +
				"INNER JOIN ERP_KHOTT_SANPHAM KHOSP ON KHOSP.SANPHAM_FK = SP.PK_SEQ " ;
		
		if(this.khoIds.trim().length() > 0 )
		{
			query+=" AND KHOSP.KHOTT_FK in ( " + this.khoIds + ")";
		}

		if(this.loaisanphamIds.trim().length() > 0)
			query += " and SP.loaisanpham_fk in (" + this.loaisanphamIds + ") ";
		
		//this.dvkdRs = this.db.get(query);
		
		// LẤY SP
		query = " select distinct MA, MA as TEN from ERP_SANPHAM where pk_seq > 0 ";
		if(this.khoIds.trim().length() > 0)
			query += " and pk_seq in ( select sanpham_fk from ERP_KHOTT_SANPHAM where khott_fk  in (" + this.khoIds + " )  ) ";
		
		if(this.loaisanphamIds.trim().length() > 0)
			query += " and loaisanpham_fk in (" + this.loaisanphamIds + ") ";
		
		if(this.dvkdIds.trim().length() > 0)
			query += " and DVKD_FK in (" + this.dvkdIds + ") ";
		
		query += "order by MA ASC";
		
		System.out.println("::: LAY SP : "+ query);
		this.maspRS = db.get(query);

		if(this.khoIds.length() > 0)
		{
			query = " select pk_seq, ma, ten " +
					" from ERP_SanPham " +
					" where trangthai = '1' and pk_seq in ( select sanpham_fk from ERP_KhoTT_SanPham where khott_fk  in (" + this.khoIds + ") )" ;
			
			if(this.loaisanphamIds.length() > 0)
			{
				query += " and loaisanpham_fk in (" + this.loaisanphamIds + ")";
			}
			
			if(this.chungloaiIds.length() > 0)
			{
				query += " and chungloai_fk in (" + this.chungloaiIds + ") ";
			}
			
			if(this.maspIds.trim().length() > 0)
			{
				query += " and MA in (" + this.maspIds + ") ";
			}
			
			if(this.dvkdIds.length() >0 ){
				query += " and DVKD_FK in (" + this.dvkdIds + ") ";
			}
			this.spRs = db.get(query);
		}
		else{
			query = " select pk_seq, ma, ten " +
			" from ERP_SanPham " +
			" where trangthai = '1' " ;
	
			if(this.loaisanphamIds.length() > 0)
			{
				query += " and loaisanpham_fk in (" + this.loaisanphamIds + ")";
			}
			
			if(this.chungloaiIds.length() > 0)
			{
				query += " and chungloai_fk in (" + this.chungloaiIds + ") ";
			}
			
			if(this.maspIds.trim().length() > 0)
			{
				query += " and MA in (" + this.maspIds + ") ";
			}
			
			if(this.dvkdIds.length() >0 ){
				query += " and DVKD_FK in (" + this.dvkdIds + ") ";
			}
			this.spRs = db.get(query);	
		}
		
		query="select pk_seq, isnull(ma, ten) as ma, ten from chungloai where trangthai = '1' ";
		this.chungloaiRs = this.db.get(query);
		
		// Lay Noi dung nhap
		 query =" select pk_seq, TEN \n"+
				" from ERP_NOIDUNGNHAP \n"+
				" where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('NK') 	\n"+			
				"union all \n"+
				" select distinct '300000' as pk_seq, N'Nhập nguyên liệu' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300001' as pk_seq, N'Nhập chuyển kho' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300002' as pk_seq, N'Điều chỉnh tồn kho' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300003' as pk_seq, N'Kiểm định chất lượng từ LSX' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300004' as pk_seq, N'Kiểm định chất lượng từ nhận hàng' as TEN  from ERP_NOIDUNGNHAP ";
		 
		System.out.println(" noi dung nhap : "+ query);
		this.ndnhapRs = db.get(query);
		
		// Lay Noi dung xuat
		 query =" select pk_seq, TEN \n"+
				" from ERP_NOIDUNGNHAP \n"+
				" where  trangthai = '1' and upper(substring(ma, 0, 2)) = upper('X')  \n"+			
				"union all \n"+
				" select distinct '400000' as pk_seq, N'Xuất nguyên liệu' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '400001' as pk_seq, N'Điều chỉnh tồn kho' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '400002' as pk_seq, N'Tiêu hao nguyên liệu' as TEN  from ERP_NOIDUNGNHAP ";
		 
		 
		this.ndxuatRs = db.get(query);
		
		query=" SELECT PK_SEQ,TENNHAMAY AS TEN FROM ERP_NHAMAY ";
		this.RsNhamay=db.get(query);
	 
		query="select pk_seq, ma + ' - ' + ten as ten from ERP_NHACUNGCAP where trangthai = '1' order by ten asc ";
		this.nccRs = this.db.get(query);
		
	}
	
	
	
	public void createRsBCKHO() 
	{
		try
		{
			String query = "select pk_seq, ten + ', ' + diachi as khoTen from erp_khott where trangthai = '1' and pk_seq in "+util.quyen_khott(this.userId);
			System.out.println("::: LAY KHO : "+ query);
			this.khoRs = db.get(query);
			
			query = "SELECT DISTINCT LSP.PK_SEQ, LSP.MA, LSP.MA + ', ' + LSP.TEN AS TEN " +
					"FROM ERP_LOAISANPHAM LSP " +
					"WHERE LSP.TRANGTHAI = 1 ";
			
			if(this.khoIds.trim().length() > 0 )
				  query+=" AND LSP.PK_SEQ  in ( select loaisanpham_fk from ERP_KHOTT_LOAISANPHAM where khott_fk in ( " + this.khoIds + " ) )";
			
			this.loaiSpRs = this.db.get(query);
			System.out.println("::: LAY LSP : "+ query);
			
			if(this.khoIds.length() > 0)
			{
				query = " select pk_seq, ma, ten " +
						" from ERP_SanPham " +
						" where trangthai = '1' and pk_seq in ( select sanpham_fk from ERP_KhoTT_SanPham where khott_fk  in (" + this.khoIds + ") )" ;
				
				if(this.loaisanphamIds.length() > 0)
				{
					query += " and loaisanpham_fk in (" + this.loaisanphamIds + ")";
				}
				
				if(this.maspIds.trim().length() > 0)
				{
					query += " and MA in (" + this.maspIds + ") ";
				}
				
				System.out.println("::: LAY SP : "+ query);
				this.spRs = db.get(query);
				
				query = "select loaikho from ERP_KhoTT where pk_seq in (" + this.khoIds + ") ";
				ResultSet rs = db.get(query);
				if(rs.next())
				{
					String loaikho = rs.getString("loaikho");
					if(loaikho.trim().equals("12"))
					{
						query = "select pk_seq, ten from NHAPHANPHOI where loaiNPP in (0, 1, 2, 3)";
						this.doituongRs = db.get(query);
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void close()
	{
		this.db.shutDown();
		
		try 
		{
			if(this.loaiSpRs != null)
				this.loaiSpRs.close();
			
			if(this.chungloaiRs != null)
				this.chungloaiRs.close();
				
			if(this.spRs != null)
				this.spRs.close();
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		
	}

	public ResultSet getKhoRs() 
	{
		return this.khoRs;
	}

	public void setKhoRs(ResultSet khoRs) 
	{
		this.khoRs = khoRs;
	}

	public String getKhoIds() 
	{
		return this.khoIds;
	}

	public void setKhoIds(String khoId) 
	{
		this.khoIds = khoId;
	}

	public String getKhoTen() 
	{
		return this.khoTen;
	}

	public void setKhoTen(String khoTen) 
	{
		this.khoTen = khoTen;
	}

	
	public ResultSet getChungloaiRs() 
	{
		return this.chungloaiRs;
	}

	public void setChungloaiRs(ResultSet clRs)
	{
		this.chungloaiRs = clRs;
	}

	
	public String getChungloaiIds() {
		
		return this.chungloaiIds;
	}

	
	public void setChungloaiIds(String loaispIds) {
		
		this.chungloaiIds = loaispIds;
	}

	public String getMsg() {

		return this.msg;
	}

	public void setMsg(String msg) {
		
		this.msg = msg;
	}

	public String getFlag() {

		return this.flag;
	}

	public void setFlag(String flag) {
	
		this.flag = flag;
	}

	public String getHangchokiem() {

		return this.laychokiem;
	}

	public void setHangchokiem(String chokiem) {
		
		this.laychokiem = chokiem;
	}
 
	public String getXemtheolo() {
		 
		return this.Xemtheolo;
	}

 
	public void setXemtheolo(String Xemtheolo) {
		this.Xemtheolo=Xemtheolo;
	}

	
	public String getPivot() {
		return this.pivot;
	}

	
	public void setPivot(String pivot) {
		this.pivot = pivot;
	}

	
	public ResultSet getMaSanPhamRs() {
		
		return this.maspRS;
	}

	
	public void setMaSanPhamRs(ResultSet loaisp) {
		
		this.maspRS = loaisp;
	}

	
	public String getMaSanPhamIds() {
		
		return this.maspIds;
	}

	
	public void setMaSanPhamIds(String loaispIds) {
		
		this.maspIds = loaispIds;
	}

	public ResultSet getNdnhapRs()
	{
		return ndnhapRs;
	}
	
	public void setNdnhapRs(ResultSet ndnhapRs) 
	{
		this.ndnhapRs = ndnhapRs;
	}
	
	public String getNdnhapIds()
	{
		return ndnhapIds;
	}
	
	public void setNdnhapIds(String ndnhapIds) 
	{
		this.ndnhapIds = ndnhapIds;
	}
	
	public ResultSet getNdxuatRs()
	{
		return ndxuatRs;
	}
	
	public void setNdxuatRs(ResultSet ndxuatRs) 
	{
		this.ndxuatRs = ndxuatRs;
	}
	
	public String getNdxuatIds()
	{
		return ndxuatIds;
	}
	
	public void setNdxuatIds(String ndxuatIds) 
	{
		this.ndxuatIds = ndxuatIds;
	}



	
	public String getCheck_SpCoPhatSinh() {
		
		return this.Check_SpCoPhatSinh;
	}



	
	public void setCheck_SpCoPhatSinh(String sp_cophatsinh) {
		
		this.Check_SpCoPhatSinh=sp_cophatsinh;
	}



	
	public String getHoPhanBo() {
		
		return this.IsHoDaPhanBo;
	}



	
	public void setHoDaPhanBo(String hodaphanbo) {
		
		this.IsHoDaPhanBo=hodaphanbo;
	}



	
	public ResultSet getRsNhamay() {
		
		return RsNhamay;
	}



	
	public String getNhamayId() {
		
		return NhamayId;
	}



	
	public void setNhamayId(String NhamayId) {
		
		this.NhamayId=NhamayId;
	}



	
	public String getLayHangKho_CXL() {
		
		return this.Layhangkho_CXL;
	}



	
	public void setLayHangKho_CXL(String layhangkho_cxl) {
		
		 this.Layhangkho_CXL=layhangkho_cxl;
	}



	
	public String getKhoId_CXL() {
		
		return khoId_CXL;
	}



	
	public void setKhoId_CXL(String khoId_CXL) {
		
		this.khoId_CXL=khoId_CXL;
	}



	
	public void set_view_chitiet(String view_chitiet) {
		
		this.viewtchitiet=view_chitiet;
	}



	
	public String get_view_chitiet() {
		
		return this.viewtchitiet;
	}


	
	public ResultSet getNccRs() {
		
		return this.nccRs;
	}


	
	public void setNccRs(ResultSet nccRs) {
		
		this.nccRs = nccRs;
	}
	
	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void createRsBC_GiaThanh() 
	{
		String[] _date = this.getDate().split("-");
		
		if( this.month.trim().length() <= 0 )
			this.month = Integer.toString( Integer.parseInt( _date[1] ) );
		
		if( this.year.trim().length() <= 0 )
			this.year = Integer.toString( Integer.parseInt( _date[0] ) );
		
		String query = "select pk_seq, ma, ten from ERP_SANPHAM where congty_fk = '" + this.congtyId + "' " + 
						" and pk_seq in ( select sanpham_fk from ERP_GIATHANH_LENHSANXUAT where congty_fk = '" + this.congtyId + "' and thang = '" + this.month + "' and nam = '" + this.year + "' ) ";
		
		System.out.println(":: LAY SAN PHAM: " + query);
		this.spRs = db.get(query);
		
		if( this.spIds.trim().length() > 0 )
		{
			query = "select pk_seq, diengiai " +
					"from ERP_LENHSANXUAT_GIAY  " +
					"where pk_seq in (  select lenhsanxuat_fk from ERP_GIATHANH_LENHSANXUAT " + 
					" 					where sanpham_fk in ( " + this.spIds + " )   and thang = '" + this.month + "' and nam = '" + this.year + "' ) " + 
					"order by pk_seq ";
			
			System.out.println(":: LAY LSX: " + query);
			this.lsxRs = db.get(query);
		}
	}


	
	public String getNccIds() {
		
		return this.nccIds;
	}


	
	public void setNccIds(String nccId) {
		
		this.nccIds = nccId;
	}


	@Override
	public void setLenhsanxuat(String lsxID) {
		// TODO Auto-generated method stub
		this.lenhsanxuat= lsxID;
	}


	@Override
	public String getLenhsanxuat() {
		// TODO Auto-generated method stub
		return this.lenhsanxuat;
	}


	@SuppressWarnings("null")
	@Override
	public ResultSet getListGia (String lenhsanxuat) {
		// TODO Auto-generated method stub
		String[] lenhsanxuatID = null;
		lenhsanxuatID[0]=lenhsanxuat;
			  
		this.giaRs= db.getRsByPro("REPORT_CHITIET_LSX", lenhsanxuatID);
		
		
		return this.giaRs;
	}


	@Override
	public String getMonth() {
		// TODO Auto-generated method stub
		return this.month;
	}


	@Override
	public void setMonth(String month) {
		// TODO Auto-generated method stub
		this.month=month;
	}


	@Override
	public String getYear() {
		// TODO Auto-generated method stub
		return this.year;
	}


	@Override
	public void setYear(String year) {
		// TODO Auto-generated method stub
		this.year=year;
	}


	@Override
	public ResultSet getDoituongRs() {
		return this.doituongRs;
	}


	@Override
	public void setDoituongRs(ResultSet doituongRs) {
		this.doituongRs = doituongRs;
	}


	@Override
	public String getDoituongId() {
		return this.doituongId;
	}


	@Override
	public void setDoituongId(String doituongId) {
		this.doituongId = doituongId;	
	}
	
	public ResultSet getLsxRs() {
		
		return this.lsxRs;
	}

	public void setLsxRs(ResultSet lsx) {
		
		this.lsxRs = lsx;
	}


	@Override
	public void Init_ReportDoichieu() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				String query = "SELECT TEN_THUTUC,TENCHUCNANG FROM ERP_BAOCAODOICHIEU";
				System.out.println("::: LAY KHO : "+ query);
				this.RsReportName = db.get(query);
	}


	@Override
	public void setReportName(String reportname) {
		// TODO Auto-generated method stub
		this.ReportName=reportname;
	}


	@Override
	public String getReportName() {
		// TODO Auto-generated method stub
		return ReportName;
	}


	@Override
	public ResultSet getRsReportName() {
		// TODO Auto-generated method stub
		return RsReportName;
	}


	@Override
	public void getRsReportName(ResultSet rs) {
		// TODO Auto-generated method stub
		RsReportName=rs;
	}
	
	public String getLsxIds() {
		
		return this.lsxId;
	}

	public void setLsxIds(String lsxIds) {
		
		this.lsxId = lsxIds;
	}


	public String getCongtyId() {
		
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}

	

 

}

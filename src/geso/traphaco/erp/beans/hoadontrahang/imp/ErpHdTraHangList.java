package geso.traphaco.erp.beans.hoadontrahang.imp;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHangList;
import geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHang_SanPham;
import geso.traphaco.center.db.sql.dbutils;

public class ErpHdTraHangList  extends Phan_Trang implements  IErpHdTraHangList
{
	private static final long serialVersionUID = -1304656630159425968L;
	
	String congtyId;
	String SoHoaDon,SoChungTu,Tungay,DenNgay,Trangthai,Message,UserId;
	String khachhang,ngayhoadon;
	
	ResultSet rsHdTraHang;
	dbutils db;
	Utility util;
	List<IThongTinHienThi> hienthiList;
	
	public ErpHdTraHangList()
	{
		this.SoChungTu="";
		this.SoHoaDon="";
		this.Tungay="";
		this.Trangthai="";
		this.DenNgay="";
		this.Message="";
		this.ngayhoadon="";
		this.khachhang="";
		this.UserId="";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		db =new dbutils();
		util=new Utility();
	}
	
	public String getSoHoaDon() 
	{
		return this.SoHoaDon;
	}

	
	public void setSoHoaDon(String SoHoaDon) 
	{
		this.SoHoaDon=SoHoaDon;
		
	}

	
	public String getSoChungTu() 
	{
		
		return this.SoChungTu;
	}

	
	public void setSoChungTu(String SoChungTu) 
	{
		
		this.SoChungTu= SoChungTu;
	}

	
	public String getTuNgay()
	{
		
		return this.Tungay;
	}

	
	public void setTuNgay(String Tungay)
	{
		this.Tungay=Tungay;	
		
	}

	
	public String getDenNgay() {
		
		return this.DenNgay;
	}

	
	public void setDenNgay(String DenNgay) 
	{
	this.DenNgay= DenNgay;	
		
	}

	
	public String getTrangthai() {
		
		return this.Trangthai;
	}

	
	public void setTrangthai(String Trangthai)
	{
	this.Trangthai=Trangthai;	
		
	}

	
	public void closeDB() 
	{
			try 
			{
				if(this.rsHdTraHang!=null)
				this.rsHdTraHang.close();
				if(this.db!=null)
					this.db.shutDown();
				
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		
	}

	private String LayDuLieu(String id) {
		 String query = 
				"select b.ngayxuathd , (select SOHIEUTAIKHOAN from ERP_TAIKHOANKT WHERE a.taikhoan_fk = pk_seq) as TAIKHOANKTCO_KH, '33311000'  as TAIKHOANNO_THUE, " +					
				"		 a.pk_seq khachhang_fk, b.VAT "+
				"from 	ERP_HOADON b inner join khachhang a  on b.khachhang_fk = a.pk_seq \n" +
				"where  b.PK_SEQ = "+ id +" and a.pk_seq = ( select khachhang_fk from erp_hoadon where pk_seq = '" + id + "' )  ";
				 
		 	db = new dbutils();
		 	System.out.println(query);
			ResultSet rs = db.get(query);
			
			String laykt = "";
			String Khachhang = "";	
			String taikhoanNo_THUE ="";
			String taikhoanCo_KH = "";
			
			double TienVAT = 0;
			
			try{
				if(rs != null)
				{	
					if (rs.next()){
						TienVAT= rs.getDouble("VAT");
						Khachhang = rs.getString("KHACHHANG_FK");
						taikhoanNo_THUE =  rs.getString("TAIKHOANNO_THUE");
		
						taikhoanCo_KH = rs.getString("TAIKHOANKTCO_KH");
						
						
						if(TienVAT > 0){
							laykt +=
							"	 SELECT distinct N'NỢ' NO_CO,"+id+" PK_SEQ, "+taikhoanNo_THUE+" SOHIEUTAIKHOAN,"+TienVAT+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+
							"	 FROM khachhang a \n"+
							"	 where a.pk_seq = ( select khachhang_fk from erp_hoadon where pk_seq = '"+id+"'  ) \n"+ 
								 
							"	 UNION ALL \n"+
								 
							"	  SELECT distinct N'CÓ' NO_CO,"+id+" PK_SEQ, "+taikhoanCo_KH+" SOHIEUTAIKHOAN, "+TienVAT+" SOTIEN, a.Ten DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+
							"	 FROM khachhang a \n"+
							"	 where a.pk_seq = ( select khachhang_fk from erp_hoadon where pk_seq = '"+id+"' )  \n"; 
						}
						
					}rs.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			

			if(laykt.trim().length() > 0 ) 
					{laykt+=" ORDER BY PK_SEQ, STT, SAPXEP \n";}
			else {
				laykt +=
					" SELECT '' NO_CO, '' PK_SEQ, '' SOHIEUTAIKHOAN, \n"+
					"		'' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+
					" FROM ERP_HOADON  \n"+
					" WHERE PK_SEQ ='"+id+"' \n";
			}
			
		return laykt;
	}
	
	public void init() 
	{
		String query =
		"SELECT HD.PK_SEQ,HD.NGAYXUATHD,HD.Trangthai,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT, "+ 
		"	CASE WHEN HD.isNPP = 0 THEN KH.TEN WHEN HD.isNPP = 1 THEN NPP.TEN WHEN HD.isNPP = 2 THEN NV.TEN END NPP, " +
		" NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA  "+
		"FROM 	ERP_HOADON HD " +
		" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KhachHang_FK 	"+
		" LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.KhachHang_FK 	"+
		" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = HD.KhachHang_FK  "+
		"		INNER JOIN NHANVIEN NT ON NT.PK_SEQ = HD.NGUOITAO "+ 
		"		INNER JOIN NHANVIEN NS ON NS.PK_SEQ = HD.NGUOISUA "+
		"WHERE 	HD.LOAIHOADON=2 AND HD.CONGTY_FK = "+this.congtyId; // and npp.pk_seq in  "+ util.quyen_npp(this.UserId);
		if(this.SoChungTu.trim().length()>0)
			query+=" AND HD.PK_SEQ LIKE'%"+this.SoChungTu+"%'";
		
		if(this.khachhang.trim().length()>0)
	    		query+=" and ( NPP.Ten like N'%"+this.khachhang+"%' OR NPP.PK_SEQ LIKE '%"+this.khachhang+"%' OR NPP.MA like '%"+this.khachhang+"%')  ";
	    	
		if(this.SoHoaDon.trim().length()>0)
			query+=" AND HD.SOHOADON LIKE'%"+this.SoHoaDon+"%'";
		
		if(this.Tungay.trim().length()>0)
			query+=" AND HD.NGAYXUATHD >='"+this.Tungay+"'";
		
		if(this.ngayhoadon.trim().length()>0)
			query+=" AND HD.NGAYXUATHD ='"+this.ngayhoadon+"'";
		
		if(this.DenNgay.trim().length()>0)
			query+=" AND HD.NGAYXUATHD <='"+this.DenNgay+"'";
		
		if(this.Trangthai.trim().length()>0)
			query+=" AND HD.Trangthai ='"+this.Trangthai+"'";
		
		System.out.println("__Init Hd tra hang List__"+query);
		
		String query_init = createSplittingData_ListNew(this.db, 50, 10, " Trangthai asc ,NGAYXUATHD DESC", query) ;
		
		this.rsHdTraHang = db.get(query_init);
		
	}

	
	public ResultSet getRsHdTraHang() {
		
		return this.rsHdTraHang;
	}

	
	public void setRsHdTraHang(ResultSet rsHdTraHang) 
	{
		this.rsHdTraHang=rsHdTraHang;
	}

	
	public String getMessage() 
	{

		return this.Message;
	}

	
	public void setMessage(String Message)
	{
		this.Message=Message;
		
	}

	
	public String getUserId() 
	{
		return this.UserId;
	}

	
	public void setUserId(String UserId) 
	{
		this.UserId= UserId;		
	}

	public String getCongtyId()
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
	}

	
	public String getNgayHoaDon() {
		
		return this.ngayhoadon;
	}

	
	public void setNgayHoaDon(String ngayhoadon) {
		this.ngayhoadon=ngayhoadon;
		
	}

	
	public String getKhachHang() {
		
		return this.khachhang;
	}

	
	public void setKhachHang(String khachhang) {
		this.khachhang=khachhang;
		
	}

}

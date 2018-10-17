package geso.traphaco.center.beans.hoadonphelieu.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.beans.hoadonphelieu.IErpXuatkmkhonghdList;
import geso.traphaco.center.db.sql.dbutils;

public class ErpXuatkmkhonghdList extends Phan_Trang implements IErpXuatkmkhonghdList
{
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	String sohoadon;
	String khachhang;
	String tennguoitao="";
	
	String tungay;
	String denngay;
	
	String nppId;
	ResultSet khRs;
	
	ResultSet giamgiaRs;
	
	dbutils db;
	
	public ErpXuatkmkhonghdList()
	{
		this.userId = "";
		this.tennguoitao="";
		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		this.sohoadon= "";
		this.khachhang="";
		this.tungay = "";
		this.denngay = "";
		this.nppId = "";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public String getTennguoitao() {
		return tennguoitao;
	}
	public void setTennguoitao(String tennguoitao) {
		this.tennguoitao = tennguoitao;
	}
	
	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	public String getNppId() 
	{
		return this.nppId;
	}
	
	public void setKhachhang(String khachhang) 
	{
		this.khachhang = khachhang;
	}
	public String getKhachhang() 
	{
		return khachhang;
	}
	
	public void setSohoadon(String sohoadon)
	{
		this.sohoadon = sohoadon;
	}
	public String getSohoadon() 
	{
		return sohoadon;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getTungay() {
		return tungay;
	}

	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

	public String getDenngay() {
		return denngay;
	}

	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
	}
	
	public void init(String query) 
	{
		this.getNppInfo();
		
		String query1 = "select  PK_SEQ, MAFAST + '-' + TEN AS TEN from NHAPHANPHOI where TRANGTHAI = '1' and loainpp=4 ";
		this.khRs = db.get(query1);
		
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = 	"  select a.pk_seq, a.ngayghinhan, case a.xuatcho when 0 then d.TEN else e.TEN end as khTen, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua,  " + 
					"         a.vat, a.avat as tongtien      " + 
					"  from ERP_XUATKMKHONGHD a inner join NHANVIEN b on a.nguoitao = b.pk_seq       " + 
					"       inner join NHANVIEN c on a.nguoisua = c.pk_seq " + 
					"       left join NHAPHANPHOI d on a.doituongId = d.pk_seq " + 
					"       left join KHACHHANG e on a.doituongId = e.pk_seq ";
		}
		
		this.giamgiaRs = createSplittingData(50, 10, "pk_seq desc", sql);
		
	}
	
	public void initBC(String search) {
		String query1 = "select  PK_SEQ, MAFAST + '-' + TEN AS TEN from NHAPHANPHOI where TRANGTHAI = '1' and loainpp=4 ";
		this.khRs = db.get(query1);
		
		String queryBC = "";
		if (search.equals("")) {
			queryBC = "SELECT npp.TEN AS Donvi, km.pk_seq AS Madonhang, km.ngayghinhan AS Ngayxuat, sp.MA AS Mavattu, sp.TEN AS Tenvattu, SUM(kmsp.soluong) AS soluong, kmspct.SOLO, kmspct.NGAYHETHAN, dvdl.DONVI AS DVT, km.mavuviec " +
					"FROM dbo.ERP_XUATKMKHONGHD km  " +
					"	INNER JOIN dbo.NHAPHANPHOI npp ON km.doituongId = npp.PK_SEQ " + 
					"	INNER JOIN dbo.ERP_XUATKMKHONGHD_SANPHAM kmsp ON km.pk_seq = kmsp.xuatkm_fk " +
					"	INNER JOIN dbo.ERP_SANPHAM sp ON sp.PK_SEQ = kmsp.sanpham_fk " +
					"	INNER JOIN dbo.ERP_XUATKMKHONGHD_SANPHAM_CHITIET kmspct ON km.pk_seq = kmspct.xuatkm_fk AND kmspct.sanpham_fk = sp.PK_SEQ " +
					"	INNER JOIN dbo.DONVIDOLUONG dvdl ON dvdl.PK_SEQ = sp.DVDL_FK  " +
					"GROUP BY npp.TEN, km.pk_seq, km.ngayghinhan, sp.MA, sp.TEN, kmspct.SOLO, kmspct.NGAYHETHAN, dvdl.DONVI, km.mavuviec ";
		} else {
			queryBC = search;
		}
		System.out.println("queryBC: " + queryBC);
		
		this.giamgiaRs = createSplittingData(50, 10, "Ngayxuat desc", queryBC);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.giamgiaRs != null)
				this.giamgiaRs.close();
			
			if(this.khRs != null)
				this.khRs.close();
			
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getKhRs() 
	{
		return this.khRs;
	}

	public void setKhRs(ResultSet khRs) 
	{
		this.khRs = khRs;
	}
	
	public ResultSet getGiamgiaRs() 
	{
		return this.giamgiaRs;
	}

	public void setGiamgiaRs(ResultSet giamgiaRs) 
	{
		this.giamgiaRs = giamgiaRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}



}

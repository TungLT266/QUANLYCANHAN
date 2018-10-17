package geso.traphaco.erp.beans.butrucongno.imp;

import geso.traphaco.erp.beans.butrucongno.IButrucongnoList;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import B.FI;

public class ButrucongnoList extends Phan_Trang implements IButrucongnoList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String tungay;
	String denngay;
	String trangthai;	
	ResultSet ddkd;
	String ddkdId;
	String sohoadon;
	String congtyId;
	String nppdangnhap;
	
	ResultSet btcnlist;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	String sochungtu;
	String khchuyenno;
	String khnhanno;
	
	String sotien;
	
	List<IThongTinHienThi> hienthiList;
	
	String Mgs;
	
	dbutils db;
	
	public ButrucongnoList(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.ddkdId = param[3];
		this.sohoadon = param[4];
		this.sotien = param[5];
		db = new dbutils();
	}
	
	public ButrucongnoList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.ddkdId = "";
		this.sohoadon= "";
		this.sochungtu = "";
		this.khnhanno = "";
		this.khchuyenno = "";
		this.Mgs ="";
		this.sotien = "";
		this.congtyId ="";
		this.nppdangnhap = "";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	public String getTungay() 
	{	
		return this.tungay;
	}
	
	public void setTungay(String tungay) 
	{
		this.tungay = tungay;		
	}
	
	public String getDenngay() 
	{	
		return this.denngay;
	}
	
	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;		
	}

	public String getTrangthai()
	{	
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai) 
	{		
		this.trangthai = trangthai;
	}
	
	public ResultSet getDdkd() 
	{		
		return this.ddkd;
	}
	
	public void setDdkd(ResultSet ddkd) 
	{		
		this.ddkd = ddkd;
	}
	
	public String getDdkdId() 
	{	
		return this.ddkdId;
	}
	
	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;		
	}
	
	public ResultSet getBtcnList() 
	{	
		return this.btcnlist;
	}
	
	public void setBtcnList(ResultSet btcnlist)
	{
		this.btcnlist = btcnlist;		
	}
	
	private String LayDuLieu(String id) {
		String laykt ="";
		String query = " SELECT bt.PK_SEQ, bt.TIENTE_FK , ISNULL(TIGIA,1) AS TIGIA, \n" +
		   			   "         bt.KH_CHUYENNO , bt.KH_NHANNO, bt.NGAYBUTRU , bt.TONGTIEN , \n"+
		   			   "        (select TAIKHOAN_FK from KHACHHANG where PK_SEQ = bt.KH_NHANNO ) as taikhoanNO,  \n" +
		   			   "        (select TAIKHOAN_FK from KHACHHANG where PK_SEQ = bt.KH_CHUYENNO ) as taikhoanCO  \n" +
		   			   " FROM ERP_BUTRUKHACHHANG bt \n" +
		   			   " WHERE bt.PK_SEQ = "+ id +" ";
		System.out.println("Câu cài KT: "+query);
		ResultSet rsKT = db.get(query);
		if(rsKT!= null)
		{
			try{
			while(rsKT.next())
			{
				String nam =  rsKT.getString("NGAYBUTRU").substring(0, 4);
				String thang =  rsKT.getString("NGAYBUTRU").substring(5, 7);
				
				String madoituongNO = rsKT.getString("KH_NHANNO");
				String madoituongCO = rsKT.getString("KH_CHUYENNO");
				
				String tienteId = rsKT.getString("TIENTE_FK");
				String tigia = rsKT.getString("TIGIA");
				
				String taikhoanNO = rsKT.getString("taikhoanNO") == null ? "":  rsKT.getString("taikhoanNO");
				String taikhoanCO = rsKT.getString("taikhoanCO") == null ? "":  rsKT.getString("taikhoanCO");
				
				double tongtien =  rsKT.getDouble("TONGTIEN");
				
				if(tongtien > 0)
				{
					if(laykt.trim().length()>0) laykt += " UNION ALL \n";
					laykt +=
						"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanNO+"' AND CONGTY_FK="+this.congtyId+") SOHIEUTAIKHOAN, \n"+
						"       "+tongtien+" SOTIEN, (SELECT MA+' - '+ TEN FROM KHACHHANG WHERE PK_SEQ = '"+madoituongNO+"') DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+
						"   FROM ERP_BUTRUKHACHHANG TT \n"+
						"   WHERE TT.PK_SEQ = '"+id+"' \n"+
				
						"   UNION ALL \n"+
				
						"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanCO+"' AND CONGTY_FK = "+this.congtyId+")  SOHIEUTAIKHOAN, \n"+
						"       "+tongtien+" SOTIEN, (SELECT MA+' - '+ TEN FROM KHACHHANG WHERE PK_SEQ = '"+madoituongCO+"') DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+
						" 	FROM ERP_BUTRUKHACHHANG TT \n"+
						"   WHERE TT.PK_SEQ = '"+id+"' \n";
				}
					
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
		System.out.println(laykt);
		return laykt;
	}
	public void init(String search) 
	{		
	
		this.getNppInfo();
		
		String query = "";	
		if (search.length() == 0)
		{
			query =  	" SELECT 	a.PK_SEQ, a.NGAYBUTRU, a.TRANGTHAI, CASE a.isNPP WHEN 0 then a_kh.TEN WHEN 1 THEN a_npp.TEN WHEN 2 THEN a_nv.TEN END as KH_CHUYENNO, " +
						"			CASE a.isNPPNHANNO WHEN 0 then b_kh.TEN WHEN 1 THEN b_npp.TEN WHEN 2 THEN b_nv.TEN END as KH_NHANNO, a.TONGTIEN \n"+
		 				" FROM 	ERP_BUTRUKHACHHANG a \n"+
		 				" LEFT JOIN KHACHHANG a_kh on a.KH_CHUYENNO = a_kh.PK_SEQ \n"+
		 				" LEFT JOIN NHAPHANPHOI a_npp on a.KH_CHUYENNO = a_npp.PK_SEQ \n"+
		 				" LEFT JOIN ERP_NHANVIEN a_nv on a.KH_CHUYENNO = a_nv.PK_SEQ \n"+
		 				" LEFT JOIN KHACHHANG b_kh on a.KH_NHANNO = b_kh.PK_SEQ \n"+
		 				" LEFT JOIN NHAPHANPHOI b_npp on a.KH_NHANNO = b_npp.PK_SEQ \n"+
		 				" LEFT JOIN ERP_NHANVIEN b_nv on a.KH_NHANNO = b_nv.PK_SEQ \n"+		 				
		 				" WHERE 1=1 and a.CONGTY_FK = "+this.congtyId+" ORDER BY a.PK_SEQ desc \n";
			
			System.out.println("INIT_______ : "+query);
		}
		else
		{
			query = search;
		}		
				
		this.btcnlist = db.get(query);
		
	}



	public void DBclose() 
	{
			try 
			{
				if(this.db != null)
					this.db.shutDown();
				
				if(!(ddkd == null))
					ddkd.close();
			} 
			catch(Exception e) {}
	}

	public String getSohoadon() 
	{
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) 
	{
		this.sohoadon = sohoadon;
	}

	
	public String getSochungtu() {
		
		return this.sochungtu;
	}

	
	public void setSochungtu(String sochungtu) {
		
		this.sochungtu = sochungtu;
	}

	
	public String getKHChuyenNo() {
		
		return this.khchuyenno;
	}

	
	public void setKhChuyenNo(String KHChuyenNo) {
		
		this.khchuyenno = KHChuyenNo;
	}

	
	public String getKHNhanNo() {
		
		return this.khnhanno;
	}

	
	public void setKhNhanNo(String KHNhanNo) {
		
		this.khnhanno = KHNhanNo;
	}

	
	public String getMgs() {
		
		return this.Mgs;
	}

	
	public void setMgs(String Mgs) {
		
		this.Mgs = Mgs;
	}

	
	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}

	
	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}

	
	public String getSotien() {
		
		return this.sotien;
	}

	
	public void setSotien(String Sotien) {
		
		this.sotien = Sotien;
	}

	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}

	
}

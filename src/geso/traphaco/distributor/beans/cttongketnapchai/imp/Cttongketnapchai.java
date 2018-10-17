package geso.traphaco.distributor.beans.cttongketnapchai.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import geso.traphaco.distributor.beans.cttongketnapchai.ICttongketnapchai;
import geso.traphaco.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluy;
import geso.traphaco.distributor.db.sql.dbutils;

public class Cttongketnapchai implements ICttongketnapchai, Serializable
{
	private static final long serialVersionUID = 1L;
	
	String Id;
	String userId;
    String nppId;
    String nppTen;
    String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String trangthai;
	String ma;
	String diengiai;
    String tungay;
    String denngay;
    String soluong;
    ResultSet nvbhRS;
	String nvbhIds;
	ResultSet spRS;
	String spId;
//	
    ResultSet khRs;
	String khId;

	Hashtable<String, String> khsoluonghtb= new Hashtable<String, String>();
	
	String Msg;
	
	dbutils db;
	
	public Cttongketnapchai()
	{
		 this.userId="";
	     this.nppId="";
	     this.nppTen="";
	     this.ngaytao = "";
	     this.nguoitao = "";
	     this.ngaysua = "";
	     this.nguoisua = "";
		 this.nvbhIds = "";
		 this.ma="";
		 this.diengiai="";
		 this.khId = "";
		 this.spId="";
		 this.Msg ="";
		 this.Id = "";
		 this.trangthai = "1";
		 this.tungay = "";
		 this.denngay = "";
		 this.soluong = "";
		 this.db = new dbutils();
		
	}
	public String getUserId() {
		
		return this.userId;
	}
	
	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	
	public String getNppId() {
		
		return this.nppId;
	}

	
    public void setNppId(String nppId) {
		
	   this.nppId = nppId;	
	}

    public String getNgaytao()
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNguoitao() 
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;
	}

	public String getNgaysua() 
	{
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;
	}

	public String getNguoisua() 
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;
	}
	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
		
	}
	private void getNppInfo(){
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
	
	}
	
	
	public String getNppTen() {
		
		return this.nppTen;
	}
	
	public void init() 
	{
		try
		{	
			System.out.println("vao init roi");
			getNppInfo();
			String query = "select * from cttknapchai where pk_seq = '"+this.Id+"'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					this.ma = rs.getString("ma")==null?"":rs.getString("ma");
					this.diengiai = rs.getString("diengiai")==null?"":rs.getString("diengiai");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.nvbhIds = rs.getString("tdv_fk");
					this.spId = rs.getString("sp_fk");
					this.soluong = rs.getString("soluong");
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.createRs();
		
	}
	
	public void setMessage(String Msg) {
		
		this.Msg = Msg;
	}
	
	public String getMessage() {
		
		return this.Msg;
	}
	
	public boolean save() 
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			if(this.spId.trim().length() < 0)
			{
				this.Msg = "Khong the tao moi CTTKNAPCHAI: chưa chọn sản phẩm.";
				db.getConnection().rollback();
				return false;
			}
			
			if(this.nvbhIds.trim().length() < 0)
			{
				this.Msg = "Khong the tao moi CTTKNAPCHAI: chưa chọn nhân viên bán hàng.";
				db.getConnection().rollback();
				return false;
			}
			String sql="";
			Enumeration<String> keys = this.khsoluonghtb.keys();
			double slnapban = 0;
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				String value=this.khsoluonghtb.get(key);
				sql +="select '"+key+"' as khId,'"+value+"' as soluong union ";
				slnapban += Double.parseDouble(value);
				System.out.println("KEY____"+key);
			}
			
			double slnapthuve = Double.parseDouble(this.soluong);
			System.out.println("sl nap ban " + slnapban + " sl thu ve " + slnapthuve);
			if(slnapban <= slnapthuve)
			{
				this.Msg = "Khong the tao moi CTTKNAPCHAI: số lượng thu được lớn hơn tổng lượng bán của TDV";
				db.getConnection().rollback();
				return false;
			}
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			
			String insert_="insert into CTTKNAPCHAI (ma, diengiai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, sp_fk, tdv_fk, soluong, tungay, denngay) " +
							" values('"+this.ma+"', '"+this.diengiai+"', 1,'" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "', '"+ this.spId+"', '"+this.nvbhIds+"', '"+this.soluong+"', '"+this.tungay+"', '"+this.denngay+"')";
				
			if(!db.update(insert_)){
				
				    this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ insert_;
					db.update("rollback");
				    return false;
				    
			}
			
			String id = "";
			String query = "select IDENT_CURRENT('CTTKNAPCHAI') as id";
			
			ResultSet rs = this.db.get(query);						
			rs.next();
			id = rs.getString("id");
			rs.close();
			
			if(this.khId.trim().length() > 0)
			{
				sql = sql.substring(0, sql.length() - 6);
				query = 
					"Insert CTTKNAPCHAI_KHACHHANG(TKNAPCHAI_FK, KH_FK,SOLUONG) " + 
					"select '"+id+"', khId, soluong from (" + sql + ") CTTKNAPCHAI_KHACHHANG  ";
				System.out.println("4.Insert: " + query);
				if (!db.update(query))
				{
					this.Msg = "3.Khong the tao moi CTTKNAPCHAI_KHACHHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			db.update("rollback");
			return false;
		}
		return true;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose() 
	{	
		try {
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(db!=null)
		db.shutDown();
	}
	public void setId(String Id) {
		
		this.Id = Id;
	}
	
	public String getId() {
		
		return this.Id;
	}

	public boolean edit() 
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			if(this.spId.trim().length() < 0)
			{
				this.Msg = "Khong the tao moi CTTKNAPCHAI: chưa chọn sản phẩm.";
				db.getConnection().rollback();
				return false;
			}
			
			if(this.nvbhIds.trim().length() < 0)
			{
				this.Msg = "Khong the tao moi CTTKNAPCHAI: chưa chọn nhân viên bán hàng.";
				db.getConnection().rollback();
				return false;
			}
			
			String sql="";
			Enumeration<String> keys = this.khsoluonghtb.keys();
			double slnapban = 0;
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				String value=this.khsoluonghtb.get(key);
				sql +="select '"+key+"' as khId,'"+value+"' as soluong union ";
				slnapban += Double.parseDouble(value);
				System.out.println("KEY____"+key);
			}
			
			double slnapthuve = Double.parseDouble(this.soluong);
			
			if(slnapban <= slnapthuve)
			{
				this.Msg = "Khong the tao moi CTTKNAPCHAI: số lượng thu được lớn hơn tổng lượng bán của TDV";
				db.getConnection().rollback();
				return false;
			}
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			
			String update_="update CTTKNAPCHAI set ma = '"+this.ma+"', diengiai = '"+this.diengiai+"', trangthai = '" + this.trangthai +"', ngaysua = '" + this.ngaysua+"', nguoisua='" +this.nguoisua+"', sp_fk = '"+this.spId+"', tdv_fk='"+this.nvbhIds+"', soluong = '"+this.soluong+"', tungay = '"+this.tungay+"', denngay = '"+this.denngay+"' where pk_seq = '"+ this.Id+"'";
				
			if(!db.update(update_))
			{
			    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ update_;
				db.update("rollback");
			    return false;
			}
			
			String id = "";
			String query = "select IDENT_CURRENT('CTTKNAPCHAI') as id";
			
			ResultSet rs = this.db.get(query);						
			rs.next();
			id = rs.getString("id");
			rs.close();
						
			if(this.khId.trim().length() > 0)
			{
				query = "delete CTTKNAPCHAI_KHACHHANG where TKNAPCHAI_FK='" + this.Id+"'";
				if (!db.update(query))
				{
					this.Msg = "3.Khong the cap nhat CTTKNAPCHAI_KHACHHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				sql = sql.substring(0, sql.length() - 6);
				query = 
					"Insert CTTKNAPCHAI_KHACHHANG(TKNAPCHAI_FK, KH_FK,SOLUONG) " + 
					"select '"+id+"', khId, soluong from (" + sql + ") CTTKNAPCHAI_KHACHHANG  ";
				System.out.println("4.Insert: " + query);
				if (!db.update(query))
				{
					this.Msg = "3.Khong the cap nhat CTTKNAPCHAI_KHACHHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
					
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			db.update("rollback");
			return false;
		}
		return true;
	}

//	public boolean Chot() 
//	{
//
//		try{
//			db.getConnection().setAutoCommit(false);
//			String insert_="update DANGKYKM_TICHLUY set trangthai=1 ,ngaysua="+this.getDateTime()+",nguoisua="+this.userId+
//					" where pk_seq="+ this.Id;		
//			if(!db.update(insert_)){
//				    this.Msg="Khong The Thuc Hien Update ,Vui Long Thu Lai. Loi "+ insert_;
//					db.update("rollback");
//				    return false;
//			}
//
//			db.getConnection().commit();
//			db.getConnection().setAutoCommit(true);
//		}catch(Exception er){
//			this.Msg="Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
//			db.update("rollback");
//			return false;
//		}
//		return true;
//	}

	public boolean Delete() {

		try{
			db.getConnection().setAutoCommit(false);
			String query="delete CTTKNAPCHAI where pk_seq="+ this.Id;		
			if(!db.update(query)){
				    this.Msg="Khong The Thuc Hien Huy  ,Vui Long Thu Lai. Loi "+ query;
					db.update("rollback");
				    return false;
			}
			
			query = "delete CTTKNAPCHAI_KHACHHANG where TKNAPCHAI_FK='"+this.Id+"'";

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.Msg="Khong The Thuc Hien Huy ,Vui Long Thu Lai. Loi "+ er.toString();
			db.update("rollback");
			return false;
		}
		return true;
	}
	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}
	public String getNvbhIds() {
		
		return this.nvbhIds;
	}
	
	public void setNvbhIds(String nvbdIds) {
		
		this.nvbhIds = nvbdIds;
	}
	
	public String getKhId() {
		
		return this.khId;
	}
	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}
	
	public void createRs() 
	{
		this.getNppInfo();		

		System.out.println("vao createRs roi, nvbhId=" + this.nvbhIds + ", spId = " + this.spId);
		String query = "select a.PK_SEQ, a.SMARTID, a.TEN, isnull(a.MANHANVIEN, '') as maNV from DAIDIENKINHDOANH a inner join daidienkinhdoanh_npp b on a.pk_seq = b.ddkd_fk where b.NPP_FK = '" + this.nppId + "' and TRANGTHAI = '1'";
		System.out.println("nvbhRS " + query);
		this.nvbhRS = db.get(query);
		
		query = "select pk_seq, ten from sanpham where trangthai = 1";
		this.spRS = db.get(query);
		
		if(this.nvbhIds.trim().length() > 0 && this.spId.trim().length() > 0)
		{
			query = "select PK_SEQ, MA, TEN, DIACHI from KHACHHANG where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "'  and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' )";
			query += " and pk_seq in ( select KHACHHANG_FK from KHACHHANG_TUYENBH where TBH_FK in ( select PK_SEQ from TUYENBANHANG where DDKD_FK = '" + this.nvbhIds + "' ) ) ";
		
			query += " order by SMARTID asc ";
			
			this.khRs = db.get(query);
			System.out.println("lay khach hang " + query);
			ResultSet rsKh = db.get(query);
			if(rsKh != null)
			{
				String khid = "";
				this.khsoluonghtb = new Hashtable<String, String>();
				try
				{
					while(rsKh.next())
					{
						khid = rsKh.getString("pk_seq");
						System.out.println("khid " + khid);
						query = "select c.sanpham_fk, sum(c.soluong) as soluong from KHACHHANG a inner join ERP_DondathangNPP b on a.PK_SEQ = b.KHACHHANG_FK " +
								"inner join ERP_DONDATHANGNPP_SANPHAM c on b.PK_SEQ = c.dondathang_fk " +
								"where b.TRANGTHAI = '4' and a.PK_SEQ = '"+khid+"' and c.sanpham_fk = '"+this.spId+"' ";
						if(this.tungay.trim().length() > 0)
							query += "and b.NgayDonHang >= '"+this.tungay+"' ";
						if(this.denngay.trim().length() > 0)
							query += "and b.NgayDonHang <= '"+this.denngay+"' ";
						query += " group by c.sanpham_fk";
						System.out.println("san pham + so luong " + query);
						ResultSet rs = db.get(query);
						if(rs != null)
						{
							while(rs.next())
							{
								
								this.khsoluonghtb.put(khid, rs.getString("SOLUONG"));
							}
						}
					}
					rsKh.close();
				}
				catch(SQLException ex)
				{
					
				}
			}	
		}
	}

	public ResultSet getNvBanhang() {
		
		return this.nvbhRS;
	}
	
	public void setNvBanhang(ResultSet nvbanhang) {
		
		this.nvbhRS = nvbanhang;
	}
	
	public void setKhList(ResultSet KhList) {
		
		this.khRs = KhList;
	}
	
	public ResultSet getKhList() {
		
		return this.khRs;
	}
	@Override
	public void setSpRS(ResultSet SpRS) {
		// TODO Auto-generated method stub
		this.spRS = SpRS;
	}
	@Override
	public ResultSet getSpRS() {
		// TODO Auto-generated method stub
		return this.spRS;
	}
	@Override
	public String getSpId() {
		// TODO Auto-generated method stub
		return this.spId;
	}
	@Override
	public void setSpId(String SpId) {
		// TODO Auto-generated method stub
		this.spId = SpId;
	}
	@Override
	public Hashtable<String, String> getKhSoluonHtb() {
		// TODO Auto-generated method stub
		return this.khsoluonghtb;
	}
	@Override
	public void setKhSoluonHtb(Hashtable<String, String> khsoluonghtb) {
		// TODO Auto-generated method stub
		this.khsoluonghtb = khsoluonghtb;
	}
	@Override
	public String getMa() {
		// TODO Auto-generated method stub
		return this.ma;
	}
	@Override
	public void setMa(String ma) {
		// TODO Auto-generated method stub
		this.ma = ma;
	}
	@Override
	public String getDiengiai() {
		// TODO Auto-generated method stub
		return this.diengiai;
	}
	@Override
	public void setDiengiai(String diengiai) {
		// TODO Auto-generated method stub
		this.diengiai = diengiai;
	}
	@Override
	public String getTungay() {
		// TODO Auto-generated method stub
		return tungay;
	}
	@Override
	public void setTungay(String tungay) {
		// TODO Auto-generated method stub
		this.tungay = tungay;
	}
	@Override
	public String getDenngay() {
		// TODO Auto-generated method stub
		return this.denngay;
	}
	@Override
	public void setDenngay(String denngay) {
		// TODO Auto-generated method stub
		this.denngay = denngay;
	}
	@Override
	public String getSoluong() {
		// TODO Auto-generated method stub
		return this.soluong;
	}
	@Override
	public void setSoluong(String soluong) {
		// TODO Auto-generated method stub
		this.soluong = soluong;
	}
	
}

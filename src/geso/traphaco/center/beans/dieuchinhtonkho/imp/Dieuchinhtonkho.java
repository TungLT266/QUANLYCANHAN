package geso.traphaco.center.beans.dieuchinhtonkho.imp;

import geso.traphaco.center.beans.dieuchinhtonkho.IDieuchinhtonkho;
import geso.traphaco.distributor.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.Hashtable;

public class Dieuchinhtonkho implements IDieuchinhtonkho
{
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String nppId;
	String nppTen;
	String userId;
	String userTen;
	String ngaydc;
	String trangthai;
	String nccId;
	ResultSet ncc;
	String dvkdId;
	ResultSet dvkd;
	String kbhId;
	ResultSet kbh;
	String khoId;
	ResultSet kho;

	String gtdc;

	String msg;
	String[] spId;
	String[] masp;
	String[] tensp;
	String[] tkht;
	String[] dv;
	String[] dc;
	String[] tkm;
	String[] giamua;
	String[] ttien;
	String size;
	String maspstr;
	dbutils db;
	
	public Dieuchinhtonkho(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.nppId = param[1];
		this.nppTen = param[2];
		this.userId = param[3];
		this.userTen = param[4];
		this.ngaydc = param[5];
		this.nccId = param[6];
		this.dvkdId = param[7];
		this.kbhId = param[8];
		this.gtdc = param[9];
	}
	
	public Dieuchinhtonkho()
	{
		this.db = new dbutils();
		this.id = "";
		this.nppId = "";
		this.nppTen = "";
		this.userId = "";
		this.userTen = "";
		this.ngaydc = "";
		this.nccId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.khoId = "";
		this.gtdc = "";
		this.msg = "";
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
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
	
	public String getNgaydc()
	{
		return this.ngaydc;
	}
	
	public void setNgaydc(String ngaydc)
	{
		this.ngaydc = ngaydc;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String thaitrang)
	{
		this.trangthai = trangthai;
	}

	public String getNccId()
	{
		return this.nccId;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}

	public ResultSet getNcc()
	{
		return this.ncc;
	}
	
	public void setNcc(ResultSet ncc)
	{
		this.ncc = ncc;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkd()
	{
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd)
	{
		this.dvkd = dvkd;
	}

	
	public String getKbhId()
	{
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}

	public ResultSet getKbh()
	{
		return this.kbh;
	}
	
	public void setKbh(ResultSet kbh)
	{
		this.kbh = kbh;
	}

	public String getKhoId()
	{
		return this.khoId;
	}
	
	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public ResultSet getKho()
	{
		return this.kho;
	}
	
	public void setKho(ResultSet kho)
	{
		this.kho = kho;
	}

	public String getGtdc()
	{
		return this.gtdc;
	}
	
	public void setGtdc(String gtdc)
	{
		this.gtdc = gtdc;
	}
	
	public String getSize()
	{
		return this.size;
	}
	
	public void setSize(String size)
	{
		this.size = size;
	}

	public String getMaspstr()
	{
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr)
	{
		this.maspstr = maspstr;
	}

	public String[] getSpId()
	{
		return this.spId;
	}
	
	public void setSpId(String[] spId)
	{
		this.spId = spId;
	}

	public String[] getMasp()
	{
		return this.masp;
	}
	
	public void setMasp(String[] masp)
	{
		this.masp = masp;
	}
	
	public String[] getTensp()
	{
		return this.tensp;
	}
	
	public void setTenSp(String[] tensp)
	{
		this.tensp = tensp;
	}
	
	public String[] getTkht()
	{
		return this.tkht;
	}
	
	public void setTkht(String[] tkht)
	{
		this.tkht = tkht;
	}
	
	public String[] getTkm()
	{
		return this.tkm;
	}
	
	public void setTkm(String[] tkm)
	{
		this.tkm = tkm;
	}

	public String[] getDonvi()
	{
		return this.dv;
	}
	
	public void setDonvi(String[] dv)
	{
		this.dv = dv;
	}

	public String[] getDc()
	{
		return this.dc;
	}
	
	public void setDc(String[] dc)
	{
		this.dc = dc;
	}
	
	public String[] getGiamua()
	{
		return this.giamua;
	}
	
	public void setGiamua(String[] giamua)
	{
		this.giamua = giamua;
	}

	public String[] getTtien()
	{
		return this.ttien;
	}
	
	public void setTtien(String[] ttien)
	{
		this.ttien = ttien;
	}
	
	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public void init0(){

		String query = "select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh where trangthai = '1'";
		this.dvkd = this.db.get(query);

		query = "select pk_seq as kbhId, diengiai as kbh from kenhbanhang";
		this.kbh = this.db.get(query);
	
		query = "select distinct pk_seq as khoId, ten,diengiai from kho" ;
		this.kho = this.db.get(query);
		
	}
	


	public void initDisplay(){
		init0();
		try{
			Hashtable ht = new Hashtable();
			String query;
			ResultSet rs;
			query = "select ngaydc, npp_fk, dvkd_fk,npp.ten as tennpp, kbh_fk, d.tongtien, d.kho_fk, d.trangthai from dieuchinhtonkho d inner join nhaphanphoi npp on npp.pk_seq=npp_fk where d.pk_seq='" + this.id + "'";
			
			rs = this.db.get(query);
			
			rs.next();
			this.ngaydc = rs.getString("ngaydc");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nppId	= rs.getString("npp_fk");
			this.kbhId	= rs.getString("kbh_fk");
			this.khoId	= rs.getString("kho_fk");
			this.gtdc 	= rs.getString("tongtien");
			this.trangthai = rs.getString("trangthai");
			this.nppTen =rs.getString("tennpp");
			rs.close();
				
			query = "select count(sanpham_fk) as num from dieuchinhtonkho_sp where dieuchinhtonkho_fk='" + this.id + "'";
			rs = this.db.get(query);

			if (rs != null){
				rs.next();
				int size = Integer.valueOf(rs.getString("num")).intValue();
				this.size = "" + size;
				this.spId = new String[size];
				this.masp = new String[size];
				this.tensp = new String[size];
				this.tkht = new String[size];
				this.dv = new String[size];
				this.dc  = new String[size];
				this.giamua = new String[size];
				this.ttien = new String[size];
				this.tkm = new String[size];
				rs.close();
			}
			
			maspstr = "";
			
			query = "select a.sanpham_fk as spId, b.ma, b.ten, a.dieuchinh as dc, a.donvi, a.giamua, a.thanhtien, a.tonhientai, a.tonmoi from dieuchinhtonkho_sp a, sanpham b where dieuchinhtonkho_fk='" + this.id + "' and a.sanpham_fk=b.pk_seq";
			rs = this.db.get(query);
			int i = 0;
			while(rs.next()){
				this.spId[i] = rs.getString("spId");
				this.masp[i] = rs.getString("ma");

				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[i] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + this.masp[i] + "'";
				}
				
				this.tensp[i]= rs.getString("ten");
				this.tkht[i] = rs.getString("tonhientai");
				this.dv[i] = rs.getString("donvi");
				this.giamua[i] = rs.getString("giamua");
				this.dc[i] = rs.getString("dc");
				this.ttien[i] = rs.getString("thanhtien");
				this.tkm[i]= rs.getString("tonmoi");
				i++;
			}
				
		}catch(java.sql.SQLException e){}
	}

	private String convertDate2(String date){
		String newdate = "";
		int year = Integer.valueOf(date.substring(0, 4)).intValue();
		int month = Integer.valueOf(date.substring(5, 7)).intValue();		
		int day = Integer.valueOf(date.substring(8, 10)).intValue();
	    if (month == 1)
	    	newdate = "" + day + "-Jan-" + year;
	    if (month == 2)
	    	newdate = "" + day + "-Feb-" + year;
	    if (month == 3)
	    	newdate = "" + day + "-Mar-" + year;
	    if (month == 4)
	    	newdate = "" + day + "-Apri-" + year;
	    if (month == 5)
	    	newdate = "" + day + "-May-" + year;
	    if (month == 6)
	    	newdate = "" + day + "-Jun-" + year;
	    if (month == 7)
	    	newdate = "" + day + "-Jul-" + year;
	    if (month == 8)
	    	newdate = "" + day + "-Aug-" + year;
	    if (month == 9)
	    	newdate = "" + day + "-Sep-" + year;
	    if (month == 10)
	    	newdate = "" + day + "-Oct-" + year;
	    if (month == 11)
	    	newdate = "" + day + "-Nov-" + year;
	    if (month == 12)
	    	newdate = "" + day + "-Dec-" + year;

        return newdate;	

	}

	public void DBclose(){
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}
	
}
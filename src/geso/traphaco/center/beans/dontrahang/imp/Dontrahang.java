package geso.traphaco.center.beans.dontrahang.imp;

import geso.traphaco.center.beans.dontrahang.IDontrahang;
import geso.traphaco.center.db.sql.dbutils;
import java.sql.ResultSet;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
public class Dontrahang implements IDontrahang
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String nppId;
	String kho;
	String id;
	String ngayth;
	String soct;
	String nguoitao;
	String nguoisua;
	String nppTen;
	String nccId;
	String trangthai;
	ResultSet nccIds;
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet kbhIds;
	String kbhId;
	ResultSet khoIds;
	String khoId;
	String tongtienaVAT;
	String vat;
	String tongtienbVAT;
	String[] spId;
	String[] masp;
	String[] tensp;
	String[] tonkho;
	String[] Tonle;
	String[] tonthung;
	String[] tra;
	String[] sl;
	String[] dongia;
	String[] dongiathung;
	String[] qc;
	String[] dv1;
	String[] donvi;
	String[] tienbVAT;
	String[] solo;
	String[] soluongthung;
	String size;
	String msg;
	String maspstr;
	dbutils db;
	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.####");
	
	public Dontrahang()
	{
		this.db = new dbutils();
		this.id = "";
		this.ngayth = getDateTime();
		this.soct = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.nppTen = "";
		this.nccId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.khoId = "";
		this.tongtienaVAT = "0";		
		this.vat = "0";
		this.tongtienbVAT = "0";
		this.size = "";
		this.msg="";
		this.maspstr = "";
		this.trangthai="";
	}
	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
		this.nguoitao = userId;
		this.nguoisua = userId;
	}
	
	public String getNppId()
	{
		return this.nppId;
	}
	
	public void setNppId(String id)
	{
		this.nppId = id;
	}

	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}

	public String getSize()
	{
		return this.size;
	}
	
	public void setSize(String size)
	{
		this.size = size;
	}
	
	
	public String getNgayth()
	{
		return this.ngayth;
	}
	
	public void setNgayth(String ngayth)
	{
		this.ngayth = ngayth;
	}
	
	public String getSoct()
	{
		return this.soct;
	}
	
	public void setSoct(String soct)
	{
		this.soct = soct;
	}
			
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}
	
	public String getNguoisua()
	{
		return this.nguoisua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getNppTen()
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
	}
	
	public String getNccId()
	{
		return this.nccId;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkdIds()
	{
		return this.dvkdIds;
	}
	
	public void setDvkdIds(ResultSet dvkdIds)
	{
		this.dvkdIds = dvkdIds;
	}

	public String getKbhId()
	{
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}

	public String getKhoId()
	{
		return this.khoId;
	}
	
	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public ResultSet getKhoIds()
	{
		return this.khoIds;
	}
	
	public void setKhoIds(ResultSet khoIds)
	{
		this.khoIds = khoIds;
	}
	
	public ResultSet getKbhIds()
	{
		return this.kbhIds;
	}
	
	public void setKbhIds(ResultSet kbhIds)
	{
		this.kbhIds = kbhIds;
	}
	
	public String getTongtienbVAT()
	{
		return this.tongtienbVAT;
	}

	public void setTongtienbVAT(String tongtienbVAT)
	{
		this.tongtienbVAT = tongtienbVAT;
	}
	
	public String getVat()
	{
		return this.vat;
	}
	
	public void setVat(String vat)
	{
		this.vat = vat;
	}
	
	public String getTongtienaVAT()
	{
		return this.tongtienaVAT;
	}
	
	public void setTongtienaVAT(String tongtienaVAT)
	{
		this.tongtienaVAT = tongtienaVAT;
	}
	
	public ResultSet getNcc()
	{
		return this.nccIds;
	}
	
	public void setNcc(ResultSet ncc)
	{
		this.nccIds = ncc;
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
	
	public void setTensp(String[] tensp)
	{
		this.tensp = tensp;
	}	
	
	public String[] getQuycach()
	{
		return this.qc;
	}
	
	public void setQuycach(String[] qc)
	{
		this.qc = qc;
	}

	public String[] getDv1()
	{
		return this.dv1;
	}
	
	public void setDv1(String[] dv1)
	{
		this.dv1 = dv1;
	}

	public String[] getSl()
	{
		return this.sl;
	}
	
	public void setSl(String[] sl)
	{
		this.sl = sl;
	}

	public String[] getDongia()
	{
		return this.dongia;
	}
	
	public void setDongia(String[] dongia)
	{
		this.dongia = dongia;
	}
	
	public String[] getTienbVAT()
	{
		return this.tienbVAT;
	}
	
	public void setTienbVAT(String[] tienbVAT)
	{
		this.tienbVAT = tienbVAT;
	}

	public String[] getDonvi()
	{
		return this.donvi;
	}
	
	public void setDonvi(String[] donvi)
	{
		this.donvi = donvi;
	}

	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public String getMaspstr() 
	{
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
		this.maspstr = maspstr;
	}
	public String getTrangthai() 
	{
		return this.trangthai;
	}
	public void setTrangthai(String trangthai)
	{
		this.trangthai=trangthai;
	}	
	//truong hop xem lai 1 don tra hang
	public void initUpdate()
	{
		String query; 	
		query=
			"select PK_SEQ, NGAYTRA,SOTIENAVAT,SOTIENBVAT, NPP_FK,VAT,DVKD_FK,KBH_FK,KHO_FK,trangthai,(select ten from nhaphanphoi where pk_seq=npp_fk)  as nppten "+
			"from dontrahang where pk_seq='"+this.id+"'";
		ResultSet rs = null;
		try{
			rs=this.db.get(query);
			if(rs!=null)
			{
				while(rs.next())
				{
					this.nppId=rs.getString("npp_fk");
					this.nppTen=rs.getString("nppTen");
					this.dvkdId=rs.getString("dvkd_fk");
					this.khoId=rs.getString("kho_fk");
					this.kbhId=rs.getString("kbh_fk");
					this.ngayth=rs.getString("ngaytra");
					this.vat=rs.getString("vat");
					this.tongtienaVAT=rs.getString("sotienavat");
					this.tongtienbVAT=rs.getString("sotienbvat");
					this.trangthai=rs.getString("trangthai");
				}
				rs.close();
			}
			rs= this.db.get("select count(*) as num from DONTRAHANG_SP a where  DonTraHang_fk='"+this.id+"'  and a.Soluong>0  ");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			this.spId = new String[i];
			this.masp = new String[i];
			this.tonkho=new String[i];
			this.Tonle = new String[i];
			this.tonthung=new String[i]; 
			this.tensp = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dv1 = new String[i];
			this.dongia = new String[i];
			this.dongiathung= new String[i];
			this.tienbVAT = new String[i];
			this.donvi = new String[i];
			this.solo= new String[i];
			this.soluongthung= new String[i];
			this.spNgayHetHan= new String[i];
			
			rs.close();
			query="select ngaytra, ncc_fk, dvkd_fk, kbh_fk, kho_fk, sum(soluong*dongia) as sotienbvat , "+
					" sum(soluong*dongia*0.1 ) as vat,sum(soluong*dongia*1.1 ) as sotienavat from dontrahang dth "+ 
					" inner join dontrahang_sp dth_sp  on dth.pk_Seq=dth_sp.dontrahang_fk   "+
					" where pk_seq='"+this.id+"' group by  ngaytra, ncc_fk, dvkd_fk, kbh_fk, kho_fk";
			System.out.println("[::::]=>"+query);
			rs = this.db.get(query);
			rs.next();
			this.ngayth = rs.getString("ngaytra");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nccId = rs.getString("ncc_fk");
			this.kbhId = rs.getString("kbh_fk");
			this.khoId = rs.getString("kho_fk");
			this.tongtienbVAT =""+ Math.round(rs.getDouble(("sotienbvat")));
			this.vat =""+ Math.round(rs.getDouble(("vat")));
			this.tongtienaVAT =""+Math.round(rs.getDouble(("sotienavat")));
			
			rs.close();
			
			query = "select a.sanpham_fk as spId, b.ma as masp, b.ten as tensp, c.soluong1 as qc, d.donvi as dv1,  a.soluong as sl, h.available+a.soluong as ton,  a.donvi, a.dongia, a.sotienbvat, a.vat, a.sotienavat,h.solo,a.NgayHetHan " 
					+  "from dontrahang_sp a, sanpham b, quycach c, donvidoluong d, dontrahang f, nhapp_kho_chitiet h, nhaphanphoi i " 
					+  "where  c.DVDL2_FK=100018 and a.sanpham_fk = b.pk_seq and a.dontrahang_fk='" + this.id + "' and a.dontrahang_fk = f.pk_seq " +
					" and b.pk_seq = c.sanpham_fk and c.dvdl1_fk = d.pk_seq and b.pk_seq = h.sanpham_fk and h.kho_fk=f.kho_fk and h.npp_fk = i.pk_seq " +
					" and h.kbh_fk = f.kbh_fk and f.npp_fk = i.pk_seq and a.solo=h.solo  and a.Soluong>0 and a.NgayHetHan=h.NgayHetHan  and h.SoLO=A.sOlO  " +
					"order by b.ma,h.NGAYHETHAN ";
			rs = this.db.get(query);				
			System.out.println("[::::]=>"+query);
			int m =0;
			while(rs.next())
			{
				
				this.spNgayHetHan[m] = rs.getString("NgayHetHan");
				
				this.spId[m] = rs.getString("spId");			 
				this.masp[m] = rs.getString("masp");
				this.tensp[m]= rs.getString("tensp");
				this.qc[m] = rs.getString("qc");
				this.dv1[m]= rs.getString("dv1");
				this.dongia[m] = rs.getString("dongia");
				this.tienbVAT[m] =formatter.format( rs.getDouble("sotienbvat"));				
				float dongia=rs.getFloat("dongia");
				double ton=rs.getDouble("ton");
				int quycach=rs.getInt("qc");
				int thung = (int) ton/quycach;
				int le = (int) (ton  % quycach);
				this.tonkho[m]=rs.getString("ton");
				this.tonthung[m] =formatter.format( thung  );
				this.Tonle[m]=formatter.format( le  );
				double soluong=rs.getDouble("sl");
				float trathung=(float) soluong/quycach;
				this.sl[m] = formatter.format( soluong  );
				this.soluongthung[m]=formatter2.format( trathung  );
				this.donvi[m] = "THG";
				this.solo[m]=rs.getString("SoLo");
				this.dongia[m] = formatter2.format(dongia);
				this.dongiathung[m]=formatter.format(dongia*quycach);								
				m++;				
			}
			this.size = "" + m;
			rs.close();		
		}catch(Exception e){e.printStackTrace();}
		finally{
			try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {

		}}
		createRs();
	}
	public void createRs()
	{
		String	query = "select pk_seq as nccId, ten as nccTen from nhacungcap where trangthai ='1'";
		this.nccIds = this.db.get(query);

		if(nccId.length()>0){
			query = "select distinct e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq and c.checked ='1' and e.trangthai ='1'";
		
			this.dvkdIds = this.db.get(query);
			
			System.out.println("Get DVKD Loai 0:"+query);
		}else{
			query="select distinct a.pk_seq as dvkdId, a.donvikinhdoanh as dvkd from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.dvkd_fk and a.trangthai='1' and b.checked='1'";
			this.dvkdIds = db.get(query);
			System.out.println("get DVKD Loai 1"+query);
		}

		query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
	System.out.println(query);
		this.kbhIds = db.get(query);
		query = "select distinct a.kho_fk as khoId, b.ten as kho from nhapp_kho a, kho b where a.kho_fk = b.pk_seq and a.npp_fk = '"+ this.nppId +"' and b.trangthai ='1'";

		//query = "select distinct a.kho_fk as khoId, b.ten as kho from nhapp_kho a, kho b where a.kho_fk = b.pk_seq and a.npp_fk = '"+ this.nppId +"'";
		if (this.kbhId.length() > 0)
			query = query + " and a.kbh_fk = '" + this.kbhId + "'";
		System.out.println(query);
		this.khoIds = db.get(query);
	}
	
	public boolean DuyetDthnpp()
	{	
		ResultSet rs = this.db.get("select npp_fk, kbh_fk, kho_fk from dontrahang where pk_seq='" + this.id + "'");
		try{
			this.db.getConnection().setAutoCommit(false);
	        rs.next();
			this.nppId = rs.getString("npp_fk");
			this.kbhId = rs.getString("kbh_fk");
			this.khoId = rs.getString("kho_fk");
			
			String  query="update dontrahang set nguoisua="+this.userId+", trangthai='2' where pk_seq='" + this.id + "' AND TRANGTHAI=1 ";
			
			if(db.updateReturnInt(query)!=1)
			{
				this.msg="Đơn trả hàng này đã duyệt rồi ! "+ query;
				this.db.update("rollback");
				return false;
			}
			
			rs = this.db.get("select sanpham_fk, sum(soluong) as SoLuong from dontrahang_sp where dontrahang_fk='" + this.id + "' group by sanpham_fk ");
			while (rs.next())
			{
				query = "update nhapp_kho set booked = booked - " + rs.getString("soluong") + ", soluong = soluong - " + rs.getString("soluong") + " where npp_fk = '" + this.nppId + "' and sanpham_fk = '" + rs.getString("sanpham_fk") + "' and kbh_fk = '" + this.kbhId + "' and kho_fk='"+ this.khoId + "'";
				if(this.db.updateReturnInt(query)!=1)
				{
					this.msg="Vui Long Thu Lai,Loi :"+ query;
					this.db.update("rollback");
					return false;
				}
			}
			rs = this.db.get("select sanpham_fk, soluong,solo,NgayHetHan from dontrahang_sp where dontrahang_fk='" + this.id + "'  ");
			while (rs.next())
			{
				query = "update nhapp_kho_chitiet set booked = booked - " + rs.getString("soluong") + ", soluong = soluong - " + rs.getString("soluong") + " where npp_fk = '" + this.nppId + "' and sanpham_fk = '" + rs.getString("sanpham_fk") + "' and kbh_fk = '" + this.kbhId + "' and kho_fk='"+ this.khoId + "' and solo= '" + rs.getString("solo") + "'  and NgayHetHan='" + rs.getString("NgayHetHan") + "'  ";
				if(this.db.updateReturnInt(query)!=1)
				{
					this.msg="Vui Long Thu Lai,Loi :"+ query;
					this.db.update("rollback");
					return false;
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			}catch(Exception e){
				this.msg="Vui Long Thu Lai,Loi :"+ e.toString();
				this.db.update("rollback");
				return false;
			}
			
			return true;
	}
	

	
	private void getNppInfo(){
		String query;
		if (this.id.length()== 0){
			query = "select a.pk_seq, a.ten, a.khosap from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'";
		}else{
			query = "select b.pk_seq, b.ten, b.khosap from dontrahang a, nhaphanphoi b where a.npp_fk = b.pk_seq and a.pk_seq = '" + this.id + "'";
		}
		ResultSet rs = this.db.get(query);
		try{
			if (rs != null){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");

			}else{
				this.nppId = "";
				this.nppTen = "";
			}
		}catch(Exception e){}				
	}
	
	public boolean isInteger( String input )  
	{  
	   try  
	   {  
	      Integer.parseInt( input );  
	      return true;  
	   }  
	   catch(Exception e)  
	   {  
	      return false;  
	   }  
	 }  

	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	private String convertDate2(String date){
		String d = "";
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);
		d = "" + day + "/" + month + "/" + year;
		return d;
	}
	
	
	private String convertDate() {
        String newdate ="";
	    int day = Integer.valueOf(this.ngayth.substring(0, 2)).intValue();		
	    int month = Integer.valueOf(this.ngayth.substring(3, 5)).intValue();
	    int year = Integer.valueOf(this.ngayth.substring(6, 10)).intValue();
        
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

	public void DBclose()
	{
		if(!(this.db == null))
		{
			this.db.shutDown();
		}
	}
	public String[] getSolo()
	{
		return solo;
	}
	public void setSolo(String[] solo)
	{
		this.solo = solo;
	}
	public String[] getSoluongthung()
	{
		return soluongthung;
	}
	public void setSoluongthung(String[] soluongthung)
	{
		this.soluongthung = soluongthung;
	}
	public String[] getTonthung()
	{
		return tonthung;
	}
	public void setTonthung(String[] tonthung)
	{
		this.tonthung = tonthung;
	}
	public String[] getTonkho()
	{
		return tonkho;
	}
	public void setTonkho(String[] tonkho)
	{
		this.tonkho = tonkho;
	}
	public String[] getDongiathung()
	{
		return dongiathung;
	}
	public void setDongiathung(String[] dongiathung)
	{
		this.dongiathung = dongiathung;
	}
	public String[] getTonle()
	{
		return this.Tonle;
	}
	
	public void setTonle(String[] Tonle)
	{
		this.Tonle = Tonle;
	}
	
	String[] spNgayHetHan;

	public String[] getSpNgayHetHan()
  {
  	return spNgayHetHan;
  }

	public void setSpNgayHetHan(String[] ngayHetHan)
  {
  	this.spNgayHetHan = ngayHetHan;
  }
	

}

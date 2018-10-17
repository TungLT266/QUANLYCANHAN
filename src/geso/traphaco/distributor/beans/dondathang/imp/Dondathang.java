package geso.traphaco.distributor.beans.dondathang.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import geso.traphaco.distributor.beans.dondathang.IDondathang;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.*;

import javax.servlet.http.HttpServletRequest;
public class Dondathang implements IDondathang, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	NumberFormat formatter = new DecimalFormat("#,###,###.##");
	String userId;
	String nppId;
	String kho;
	String id;
	Double ChietkhauTrucTiep;
	
	String ngaydh;
	String NgayDeNghiGH="";
	String soct;
	String nguoitao;
	String nguoisua;
	String nppTen;
	String nccId;
	ResultSet ncc;
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet kbhIds;
	String kbhId;
	String tongtienaVAT;
	String vat;
	String tongtienbVAT;
	String GiaVanChuyen;
	
	String dndhId;
	String LoaiDonhang;
	String DoiHang;
	double ThueSuat;
	
	String LyDoHuy;
	
	String tuvanchuyen;
	String GhiChu;
	
	
	String[] spId;
	String[] masp;
	String[] tensp;
	String[]denghi;
	String[] ton;
	String[] sl;
	String[] slduyet;
	String[] tspId;
	String[] tmasp;
	String[] ttensp;
	String[] tdenghi;
	String[] tton;
	String[] tsl;
	String[] ttsl;
	String[] tdongia;
	String[] ttienbVAT;
	String[] tdonvi;
	String[] tkhoiluong;
	String[] tthetich;
	
	String[] dongia;
	String[] dongia_tuvc;
	String[] dongia_kovc;
	String[] goivc;
	
	String[] qc;
	String[] dv1;
	String[] donvi;
	String[] tienbVAT;
	String[] chietkhau;
	String size;
	String msg;
	String maspstr;
	dbutils db;
	double ChietKhauThuongMai;

	public Dondathang()
	{
		this.db = new dbutils();
		try{
			ResultSet	rs=this.db.get("select NGAYTONKHO,MUCTANGTRUONG,isnull(thue,0) as thue from CONGTHUCDNDH ");	
			if(rs.next())
			{
			this.ThueSuat= rs.getFloat("thue")/100;
			}
			
		rs.close();
		}catch(Exception er){
			er.printStackTrace();
		}
		this.id = "";
		this.ngaydh = getDateTime();
		
		this.ChietkhauTrucTiep=3.5;
		
	
		if(getDateTime().substring(0,4).equals("2013"))
		{
			if(Double.parseDouble(getDateTime().split("-")[2]) <=15){
				this.ChietkhauTrucTiep=4.0;
			}else{
				this.ChietkhauTrucTiep=3.0;
			}
		}else
		{
				this.ChietkhauTrucTiep=3.5;
		}
		System.out.println("[NgayDatHang]"+getDateTime().substring(0,4)+"ChietKhauTrucTiep"+this.ChietkhauTrucTiep);
		this.soct = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.nppTen = "";
		this.nccId = "";
		this.dvkdId = "";
		this.GhiChu="";
		this.tongtienaVAT = "0";		
		this.vat = "0";
		this.tongtienbVAT = "0";
		this.dndhId = "0";
		this.size = "";
		this.msg="";
		this.maspstr = "";
		this.NgayDeNghiGH="";
		this.DoiHang="0";
		this.LoaiDonhang="0";
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
	
	
	public String getNgaydh()
	{
		return this.ngaydh;
	}
	
	public void setNgaydh(String ngaydh)
	{
		this.ngaydh = ngaydh;
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
		return this.ncc;
	}
	
	public void setNcc(ResultSet ncc)
	{
		this.ncc = ncc;
	}
	
	public String[] getSpId()
	{
		return this.spId;
	}
	
	public void setSpId(String[] spId)
	{
		this.spId = spId;
	}

	public String[] gettSpId()
	{
		return this.tspId;
	}
	
	public void settSpId(String[] tspId)
	{
		this.tspId = tspId;
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
	
	public String[] gettTensp()
	{
		return this.ttensp;
	}
	
	public void settTensp(String[] ttensp)
	{
		this.ttensp =ttensp;
	}

	public String[] getDenghi()
	{
		return this.denghi;
	}
	
	public void setDenghi(String[] denghi)
	{
		this.denghi = denghi;
	}

	public String[] gettDenghi()
	{
		return this.tdenghi;
	}
	
	public void settDenghi(String[] tdenghi)
	{
		this.tdenghi = tdenghi;
	}

	public String[] getTonicp()
	{
		return this.ton;
	}
	
	public void setTonicp(String[] ton)
	{
		this.ton = ton;
	}
	
	public String[] gettTonicp()
	{
		return this.tton;
	}
	
	public void settTonicp(String[] tton)
	{
		this.tton = tton;
	}

	public Hashtable<String, String> getThieuMasp()
	{
		Hashtable<String, String > tmasp = new Hashtable();
		if (this.tmasp != null){
			for(int i = 0; i < this.tmasp.length; i++){
				if (this.tmasp[i] != null){
					tmasp.put(this.tmasp[i], "1");
				}
			}
		}
		return tmasp;
	}
	
	public String[] getThieuMaspArray()
	{
		return this.tmasp;
	}

	public void setThieuMasp(String[] tmasp)
	{
		this.tmasp = tmasp;
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

	public String[] gettSl()
	{
		return this.ttsl;
	}
	
	public void settSl(String[] ttsl)
	{
		this.ttsl = ttsl;
	}

	public String[] getDongia()
	{
		return this.dongia;
	}
	
	public void setDongia(String[] dongia)
	{
		this.dongia = dongia;
	}
	
	public String[] gettDongia()
	{
		return this.tdongia;
	}
	
	public void settDongia(String[] tdongia)
	{
		this.tdongia = tdongia;
	}

	public String[] getTienbVAT()
	{
		return this.tienbVAT;
	}
	
	public void setTienbVAT(String[] tienbVAT)
	{
		this.tienbVAT = tienbVAT;
	}

	public String[] gettTienbVAT()
	{
		return this.ttienbVAT;
	}
	
	public void settTienbVAT(String[] ttienbVAT)
	{
		this.ttienbVAT = ttienbVAT;
	}

	public String[] getDonvi()
	{
		return this.donvi;
	}
	
	public void setDonvi(String[] donvi)
	{
		this.donvi = donvi;
	}

	public String[] gettDonvi()
	{
		return this.tdonvi;
	}
	
	public void settDonvi(String[] tdonvi)
	{
		this.tdonvi = tdonvi;
	}

	public String getDndhId()
	{
		return this.dndhId;
	}
	
	public void setDndhId(String dndhId)
	{
		this.dndhId = dndhId;
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

	// Tao moi
	public void init0()
	{
		getNppInfo();
		String query = 
		"select d.pk_seq as nccId, d.ten as nccTen "+ 
		"from NHACUNGCAP d "+
		"where PK_SEQ in "+
		"( "+
		"	select NCC_FK from	nhacungcap_dvkd where PK_SEQ in "+
		"	( "+
		"		select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK='"+this.nppId+"' "+ 
			") "+
		")";
		this.ncc = this.db.get(query);		
		query="select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh ";
		if(this.nppId.length()>0)
		query+=
		" where PK_SEQ in "+ 
		" (	select dvkd_fk from NHACUNGCAP_DVKD where PK_SEQ in "+ 
		"	( "+
		"		select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where  npp_fk in ('"+this.nppId+"') "+ 
		"	) "+
		") and TRANGTHAI=1 ";
		this.dvkdIds=this.db.get(query);
		query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
		this.kbhIds = this.db.get(query);
	}
	//truong hop xem lai 1 don dat hang co de nghi dat hang
	public void init1()
	{
		getNppInfo();
		Hashtable ht = new Hashtable<String, String>();
		String query; 	
		ResultSet rs = null;
		try{
			rs= this.db.get("select count(ma) as num from sanpham where trangthai='1'");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			this.spId = new String[i];
			this.masp = new String[i];
			this.denghi = new String[i];
			this.ton = new String[i];
			this.tensp = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dv1 = new String[i];
			this.dongia = new String[i];
			this.dongia_tuvc=new String[i];
			this.goivc=new String[i];
			this.dongia_kovc=new String[i];
			
			this.tienbVAT = new String[i];
			this.donvi = new String[i];
			this.tkhoiluong = new String[i];
			this.tthetich = new String[i];
			rs.close();
			
			query = " select  isnull(ddh.thongtin,'') as thongtin ,isnull(npp.GIAVANCHUYEN,0) as GIAVANCHUYEN ,isnull(ddh.tuvanchuyen,'0') as tuvanchuyen ,isnull(ddh.loaidonhang,'0') as loaidonhang " +
					" ,isnull( ddh.doihang,'0')  as doihang ,ddh.ngaydenghigh,ddh.ngaydat, ddh.ncc_fk, ddh.dvkd_fk, ddh.kbh_fk, ddh.sotienbvat,  " +
					" ddh.vat, ddh.sotienavat, isnull(ddh.denghidathang_fk, '0') as dndhId from dondathang  ddh " +
					" inner join nhaphanphoi npp on npp.pk_seq=ddh.NPP_FK " +
					" where ddh.pk_seq='"+ this.id + "'";
			System.out.println("Get Dat hang :"+query);
			String dndhId = "0";
			rs = this.db.get(query);
			rs.next();
			this.ngaydh = rs.getString("ngaydat");
			this.GhiChu=rs.getString("thongtin");
			this.GiaVanChuyen=rs.getString("giavanchuyen");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nccId = rs.getString("ncc_fk");
			this.kbhId = rs.getString("kbh_fk");
			this.tongtienbVAT = rs.getString("sotienbvat");
			this.vat = rs.getString("vat");
			this.tongtienaVAT = rs.getString("sotienavat");
			////System.out.println("TONG TIEN A VAT : "+	this.tongtienaVAT );
			this.NgayDeNghiGH=rs.getString("ngaydenghigh");
			this.DoiHang=rs.getString("doihang");
			this.LoaiDonhang=rs.getString("loaidonhang");
			this.tuvanchuyen=rs.getString("tuvanchuyen");
			
			dndhId = rs.getString("dndhId");
			rs.close();
			
			query = "";
			
		
			query=
			"	SELECT DISTINCT NH.TEN AS NHTEN, A.SANPHAM_FK, B.MA ,B.TRONGLUONG AS KHOILUONG,B.THETICH, B.TEN AS TENSP, "+  
			"		ISNULL(E.DENGHIDAT,0) AS DENGHI, C.SOLUONG1 AS QC, D.DONVI AS DV1, A.SOLUONG AS SL,  "+
			"		0 AS  TON, QCVC.SOLUONG2/QCVC.SOLUONG1 AS GOIVC , A.DONVI,ISNULL(( BG.GIAMUANPP),0) AS DONGIA, GIAMUANPP_TUVC, "+ 
			"		A.SOTIENBVAT, A.VAT, A.SOTIENAVAT  "+
			"	FROM  SANPHAM B LEFT JOIN DONDATHANG_SP A  ON B.PK_SEQ =A.SANPHAM_FK  INNER JOIN DONDATHANG F ON A.DONDATHANG_FK = F.PK_SEQ   "+
			"		LEFT JOIN NGANHHANG NH ON NH.PK_SEQ=B.NGANHHANG_FK    "+
			"		INNER JOIN QUYCACH C ON B.PK_SEQ = C.SANPHAM_FK  AND B.DVDL_FK=C.DVDL1_FK  AND C.DVDL2_FK=100018 "+  
			"		INNER JOIN DONVIDOLUONG D ON C.DVDL2_FK = D.PK_SEQ   "+
			"		LEFT JOIN QUYCACH QCVC ON QCVC.SANPHAM_FK=B.PK_SEQ   AND QCVC.DVDL2_FK='100052' "+   
			"		LEFT JOIN DENGHIDATHANG_SP E ON E.SANPHAM_FK=A.SANPHAM_FK AND E.DENGHIDATHANG_FK = F.DENGHIDATHANG_FK "+    
			"		LEFT JOIN   "+
			"	(   "+
			"		SELECT  D.SANPHAM_FK,D.GIAMUANPP,ISNULL(GIAMUANPP_TUVC,0) AS GIAMUANPP_TUVC "+ 
			"		FROM     			  "+
			"		BGMUANPP_SANPHAM D  "+
			"		WHERE D.BGMUANPP_FK  "+
			"		IN   "+
			"		(   "+
			"		SELECT TOP(1) B.PK_SEQ FROM BANGGIAMUANPP B "+ 
			"			INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK "+ 
			"		WHERE C.NPP_FK="+this.nppId+" AND B.TUNGAY <='"+this.ngaydh+"' AND B.DVKD_FK="+this.dvkdId+" AND B.KENH_FK="+this.kbhId+" "+ 
			"		ORDER BY TUNGAY DESC "+
			"	)  "+
			"	) BG ON BG.SANPHAM_FK=B.PK_SEQ  AND BG.GIAMUANPP>0 "+ 
			"	WHERE A.DONDATHANG_FK='"+this.id+"' AND B.TRANGTHAI=1 "+ 
			"	ORDER BY NH.TEN, B.MA "; 
			rs = this.db.get(query);				
			System.out.println("Query lay du lieu la: " + query);
			int m =0;

			while(rs.next()){
				this.spId[m] = rs.getString("sanpham_fk");			 
				this.masp[m] = rs.getString("ma");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[m] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[m] + "'";
				}				
				this.tensp[m]= rs.getString("tensp");
				
				this.denghi[m] =""+ Math.round( Double.parseDouble( rs.getString("denghi")));
				
				this.qc[m] = rs.getString("qc");
				this.dv1[m]= rs.getString("dv1");
				this.sl[m] = "" + Math.round(Double.valueOf(rs.getString("sl")).doubleValue())/Double.valueOf(rs.getString("qc")).doubleValue();
				
				this.ton[m]="0";
				
				this.donvi[m] = rs.getString("donvi");
				
				
				//this.dongia_tuvc[m]= "" + Math.round(Double.valueOf(rs.getString("Giamuanpp_tuvc")).doubleValue())* Double.valueOf(rs.getString("qc")).doubleValue();
				
				this.dongia_tuvc[m] = "" + Math.round( rs.getDouble("dongia") * rs.getDouble("qc") - ( rs.getDouble("goivc")  * Double.parseDouble(this.GiaVanChuyen)) ) ;
				
				this.dongia_kovc[m] =""+ Math.round(rs.getDouble("dongia") * rs.getDouble("qc"));
				
				if(this.tuvanchuyen.equals("1")){
					this.dongia[m] =this.dongia_tuvc[m];
				}else{
					this.dongia[m] =this.dongia_kovc[m];
				}
				
				this.tienbVAT[m] = rs.getString("sotienbvat");
				this.tkhoiluong[m] = rs.getString("khoiluong");
				this.tthetich[m] = rs.getString("thetich");
				m++;
			}
			this.size = "" + m;
			rs.close();
			
		
			query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
			this.ncc = this.db.get(query);

			if(nccId.length()>0){
				query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
				this.dvkdIds = this.db.get(query);
			}else{
				this.dvkdIds = db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
			}

			query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
			this.kbhIds = db.get(query);
		
		}catch(Exception e){
			e.printStackTrace();
			//System.out.println("Error Don Dat Hang .jav : "+e.toString());
		}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
		}}
	}
	
	public void initDisplay()
	{
		getNppInfo();
		Hashtable ht = new Hashtable<String, String>();
		String query; 	
		ResultSet rs = null;
		try{
			rs= this.db.get("select count(ma) as num from sanpham where trangthai='1'");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			this.spId = new String[i];
			this.masp = new String[i];
			this.denghi = new String[i];
			this.ton = new String[i];
			this.tensp = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dv1 = new String[i];
			this.dongia = new String[i];
			this.dongia_tuvc=new String[i];
			this.goivc=new String[i];
			this.dongia_kovc=new String[i];
			
			this.tienbVAT = new String[i];
			this.donvi = new String[i];
			this.tkhoiluong = new String[i];
			this.tthetich = new String[i];
			rs.close();
			
			query = " select  isnull(ddh.thongtin,'') as thongtin ,isnull(npp.GIAVANCHUYEN,0) as GIAVANCHUYEN ,isnull(ddh.tuvanchuyen,'0') as tuvanchuyen ,isnull(ddh.loaidonhang,'0') as loaidonhang " +
					" ,isnull( ddh.doihang,'0')  as doihang ,ddh.ngaydenghigh,ddh.ngaydat, ddh.ncc_fk, ddh.dvkd_fk, ddh.kbh_fk, ddh.sotienbvat,  " +
					" ddh.vat, ddh.sotienavat, isnull(ddh.denghidathang_fk, '0') as dndhId from dondathang  ddh " +
					" inner join nhaphanphoi npp on npp.pk_seq=ddh.NPP_FK " +
					" where ddh.pk_seq='"+ this.id + "'";
			
			String dndhId = "0";
			rs = this.db.get(query);
			rs.next();
			this.ngaydh = rs.getString("ngaydat");
			this.GhiChu=rs.getString("thongtin");
			this.GiaVanChuyen=rs.getString("giavanchuyen");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nccId = rs.getString("ncc_fk");
			this.kbhId = rs.getString("kbh_fk");
			this.tongtienbVAT = rs.getString("sotienbvat");
			this.vat = rs.getString("vat");
			this.tongtienaVAT = rs.getString("sotienavat");
			////System.out.println("TONG TIEN A VAT : "+	this.tongtienaVAT );
			this.NgayDeNghiGH=rs.getString("ngaydenghigh");
			this.DoiHang=rs.getString("doihang");
			this.LoaiDonhang=rs.getString("loaidonhang");
			this.tuvanchuyen=rs.getString("tuvanchuyen");
			
			dndhId = rs.getString("dndhId");
			rs.close();
			
			query = "";
			
		
			query=
			"	SELECT DISTINCT NH.TEN AS NHTEN, A.SANPHAM_FK, B.MA ,B.TRONGLUONG AS KHOILUONG,B.THETICH, B.TEN AS TENSP,  "+ 
			"		ISNULL(E.DENGHIDAT,0) AS DENGHI, C.SOLUONG1 AS QC, D.DONVI AS DV1, A.SOLUONG AS SL,  "+
			"		0 AS  TON, QCVC.SOLUONG2/QCVC.SOLUONG1 AS GOIVC , A.DONVI,ISNULL(( a.DONGIA),0) AS DONGIA, a.DONGIA AS  GIAMUANPP_TUVC, "+ 
			"		A.SOTIENBVAT, A.VAT, A.SOTIENAVAT   "+
			"	FROM DONDATHANG_SP A INNER JOIN DONDATHANG F ON A.DONDATHANG_FK = F.PK_SEQ "+   
			"		INNER JOIN SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ     "+
			"		LEFT JOIN NGANHHANG NH ON NH.PK_SEQ=B.NGANHHANG_FK     "+
			"		INNER JOIN QUYCACH C ON B.PK_SEQ = C.SANPHAM_FK  AND B.DVDL_FK=C.DVDL1_FK  AND C.DVDL2_FK=100018 "+   
			"		INNER JOIN DONVIDOLUONG D ON C.DVDL2_FK = D.PK_SEQ    "+
			"		LEFT JOIN QUYCACH QCVC ON QCVC.SANPHAM_FK=B.PK_SEQ   AND QCVC.DVDL2_FK='100052' "+    
			"		LEFT JOIN DENGHIDATHANG_SP E ON E.SANPHAM_FK=A.SANPHAM_FK AND E.DENGHIDATHANG_FK = F.DENGHIDATHANG_FK "+     
			"	WHERE A.DONDATHANG_FK='"+this.id+"'  AND ( A.SOLUONG>0 OR A.SOLUONGDUYET>0 )   "+
			"	ORDER BY NH.TEN, B.MA  "; 
			rs = this.db.get(query);				
			System.out.println("Query lay du lieu la: " + query);
			int m =0;

			while(rs.next()){
				this.spId[m] = rs.getString("sanpham_fk");			 
				this.masp[m] = rs.getString("ma");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[m] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[m] + "'";
				}				
				this.tensp[m]= rs.getString("tensp");
				
				this.denghi[m] =""+ Math.round( Double.parseDouble( rs.getString("denghi")));
				
				this.qc[m] = rs.getString("qc");
				this.dv1[m]= rs.getString("dv1");
				this.sl[m] = "" + Math.round(Double.valueOf(rs.getString("sl")).doubleValue())/Double.valueOf(rs.getString("qc")).doubleValue();
				
				this.ton[m]="0";
				
				this.donvi[m] = rs.getString("donvi");
				
				
				//this.dongia_tuvc[m]= "" + Math.round(Double.valueOf(rs.getString("Giamuanpp_tuvc")).doubleValue())* Double.valueOf(rs.getString("qc")).doubleValue();
				
				this.dongia_tuvc[m] = "" + Math.round( rs.getDouble("dongia") * rs.getDouble("qc") - ( rs.getDouble("goivc")  * Double.parseDouble(this.GiaVanChuyen)) ) ;
				
				this.dongia_kovc[m] =""+ Math.round(rs.getDouble("dongia") * rs.getDouble("qc"));
				
				if(this.tuvanchuyen.equals("1")){
					this.dongia[m] =this.dongia_tuvc[m];
				}else{
					this.dongia[m] =this.dongia_kovc[m];
				}
				
				this.tienbVAT[m] = rs.getString("sotienbvat");
				this.tkhoiluong[m] = rs.getString("khoiluong");
				this.tthetich[m] = rs.getString("thetich");
				m++;
			}
			this.size = "" + m;
			rs.close();
			
		
			query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
			this.ncc = this.db.get(query);

			if(nccId.length()>0){
				query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
				this.dvkdIds = this.db.get(query);
			}else{
				this.dvkdIds = db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
			}

			query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
			this.kbhIds = db.get(query);
		
		}catch(Exception e){
			e.printStackTrace();
			//System.out.println("Error Don Dat Hang .jav : "+e.toString());
		}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
		}}
	}

	public void initSelectboxData(){
		getNppInfo();
		String query = "select ncc_fk, dvkd_fk, kbh_fk from denghidathang where pk_seq = '"+this.dndhId+"'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.dvkdId = rs.getString("dvkd_fk");
			this.nccId = rs.getString("ncc_fk");
			this.kbhId = rs.getString("kbh_fk");
			rs.close();
		}catch(Exception e){}
		finally{try {
			if(rs != null) rs.close();
		} catch (Exception e2) {
			
		}}
		
		query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
		this.ncc = this.db.get(query);

		if(this.nccId.length()>0){
			query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
			this.dvkdIds = this.db.get(query);
		}else{
			this.dvkdIds = db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
		}
//		this.msg = query;
		query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
		this.kbhIds = db.get(query);
		
	}
	// Chuyen tu PR sang PO
	public void init2(){
		//System.out.println("vo init2");
		getNppInfo();
		Hashtable ht = new Hashtable<String, String>();
		String query; 

		ResultSet rs = null;
		try{
			query="select isnull(tuvanchuyen,0) as tuvanchuyen ,isnull(giavanchuyen,0) as giavanchuyen  from nhaphanphoi  where pk_seq="+this.nppId;
			
			 rs=db.get(query);
			if(rs.next()){
				this.tuvanchuyen=rs.getString("tuvanchuyen");
				this.GiaVanChuyen=rs.getString("giavanchuyen");
				
			}
			rs.close();

			rs= this.db.get("select count(ma) as num from sanpham where trangthai='1'");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			this.spId = new String[i];
			this.masp = new String[i];
			this.tensp = new String[i];
			this.denghi = new String[i];
			this.ton   = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dongia = new String[i];
			this.dongia_kovc=new String[i];
			this.dongia_tuvc=new String[i];
			this.tienbVAT = new String[i];
			this.tkhoiluong=new String[i];
			this.tthetich=new String[i];
			
			this.donvi = new String[i];
			rs.close();
			
			query = "select ncc_fk, dvkd_fk, kbh_fk from denghidathang where pk_seq = '"+this.dndhId+"'";
			rs = this.db.get(query);
			if(rs.next()){
				this.dvkdId = rs.getString("dvkd_fk");
				this.nccId = rs.getString("ncc_fk");
				this.kbhId = rs.getString("kbh_fk");
			}
			rs.close();
			
			query ="select distinct sanpham_fk, denghidat, dadathang, dongia from denghidathang_sp where denghidathang_fk='"+ this.dndhId +"' and dongia >0";
			rs = this.db.get(query);
			
			
			while(rs.next()){
				double tmp = Double.valueOf(rs.getString("dadathang")).doubleValue()/Double.valueOf(rs.getString("dongia")).doubleValue(); 
				tmp = Double.valueOf(rs.getString("denghidat")).doubleValue() - tmp;
				if (tmp < 0)
					tmp = 0;
				String sl = "" + Math.round(tmp);
				//System.out.println("soluong de nghi : "+sl);
				ht.put(rs.getString("sanpham_fk"), sl);
			}
			rs.close();
			
			maspstr = "";
			this.tongtienbVAT = this.tongtienbVAT.replace(",", "");
			this.vat = this.vat.replace(",", "");
			this.tongtienaVAT = this.tongtienaVAT.replace(",", "");

	
			
			String t= 
			"	SELECT DISTINCT NH.TEN AS NHTEN,A.PK_SEQ AS ID, A.MA AS MA, A.TEN AS TEN, ISNULL(BG.GIAMUANPP,0) AS GIACHUAN   "+
			"		,ISNULL(BG.GIAMUANPP,0)  AS DONGIA,GIAMUANPP_TUVC , C.SOLUONG1 AS QC, QCVC.SOLUONG2/QCVC.SOLUONG1 AS GOIVC,  D.DONVI AS DV "+    
			"	FROM SANPHAM A    "+
			"		LEFT JOIN NGANHHANG NH ON NH.PK_SEQ=A.NGANHHANG_FK   "+
			"		INNER JOIN QUYCACH C ON A.PK_SEQ= C.SANPHAM_FK AND C.DVDL1_FK=A.DVDL_FK AND DVDL2_FK=100018 "+  
			"		INNER JOIN QUYCACH QCVC ON A.PK_SEQ= QCVC.SANPHAM_FK  AND QCVC.DVDL2_FK=100052   "+
			"		INNER JOIN DONVIDOLUONG D ON A.DVDL_FK = D.PK_SEQ LEFT JOIN ERP_KHOTT_SANPHAM E ON E.SANPHAM_FK = A.PK_SEQ "+   
			"	INNER JOIN "+     
			"	(    "+
			"		 SELECT  D.SANPHAM_FK,D.GIAMUANPP,ISNULL(GIAMUANPP_TUVC,0) AS GIAMUANPP_TUVC "+ 
			"		 FROM     			   "+
			"			 BGMUANPP_SANPHAM D  "+
			"			 WHERE D.BGMUANPP_FK "+ 
			"			 IN   "+
			"			 (   "+
			"				SELECT TOP(1) B.PK_SEQ FROM BANGGIAMUANPP B  "+
			"					INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK "+
			"			WHERE C.NPP_FK="+this.nppId+" AND B.TUNGAY <='"+ngaydh+"' AND B.DVKD_FK="+this.dvkdId+" AND B.KENH_FK="+this.kbhId +" "+
			"				ORDER BY TUNGAY DESC "+ 
			"			)  "+
			"	) BG ON BG.SANPHAM_FK=A.PK_SEQ "+   
			"	 WHERE A.TRANGTHAI='1' "+  
			" ORDER BY NH.TEN, A.MA "; 
			
			System.out.println("query lay ton: "+t);
			
			
			rs= this.db.get(t);
			
			i = 0;		
			double tmpbvat = 0;
			double tmpvat = 0;
			double tmpavat = 0;
			
			while(rs.next()){
				this.spId[i] = rs.getString("id");
				this.masp[i] = rs.getString("ma");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[i] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[i] + "'";
				}
			
				this.tensp[i] = rs.getString("ten");
				
				this.qc[i] = rs.getString("qc");				
				this.ton[i]="0";							
				if (Double.valueOf(this.ton[i].trim()).doubleValue() < 0){
					this.ton[i] = "0";
				}
				
				if(ht.containsKey(spId[i])){
					this.denghi[i] =""+ Math.round(Double.parseDouble((String)ht.get(this.spId[i]))) ;
				}else{
					this.denghi[i]	 = "0";
				}
				//System.out.println("deng nghi lan 2 "+denghi[i]);
				

				this.sl[i]	 = "0";
				
				this.dongia_kovc[i]= "" + Math.round( rs.getDouble("dongia") * Double.parseDouble(this.qc[i]));
			
				this.dongia_tuvc[i] = "" + Math.round( rs.getDouble("dongia") * Double.parseDouble(this.qc[i]) - rs.getDouble("goivc")  * Double.parseDouble(this.GiaVanChuyen)) ;
				//this.dongia_tuvc[i]= "" + Math.round((Double.valueOf(rs.getString("GIAMUANPP_TUVC")).doubleValue()))*Double.valueOf(this.qc[i]).doubleValue();
				
				if(this.tuvanchuyen.equals("1")){
					this.dongia[i]=this.dongia_tuvc[i];
				}else{
					this.dongia[i]=this.dongia_kovc[i];
				}
					this.tienbVAT[i] = "0";
					
				
				this.donvi[i] = "Thùng";
				
				i++;
			}
			rs.close();
			
			this.size = "" + i;	
			this.tongtienbVAT = "" + tmpbvat; 
			tmpvat = this.ThueSuat;
			this.vat = "" + tmpvat;
			tmpavat = Math.round(tmpbvat)+ this.ThueSuat;	
			this.tongtienaVAT = "" + tmpavat;
			
			query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
			this.ncc = this.db.get(query);
		
			query = "select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh";
			this.dvkdIds = this.db.get(query);
			
			query = "select pk_seq as kbhId, diengiai as kbh from kenhbanhang";
			this.kbhIds = this.db.get(query);

		}catch(Exception e){
			e.printStackTrace();
			this.msg="Loi : "+e.toString();
			//System.out.println("Loi : "+e.toString());
		}
		finally{try {
			if(rs != null) rs.close();
		} catch (Exception e2) {
			
		}}
	}

	// Tao Dondathang khong qua Denghidathang
	public void init3(){
		getNppInfo();
		Hashtable ht = new Hashtable<String, String>();
		String query; 

		ResultSet rs = null;
		try{
			query=  "  select isnull(tuvanchuyen,0) as tuvanchuyen ,isnull(GiaVanChuyen,0) as GiaVanChuyen  " +
					"  from nhaphanphoi  where pk_seq="+this.nppId;
			//System.out.println("tu van chyyen "+query);
			 rs=db.get(query);
			if(rs.next()){
				this.tuvanchuyen=rs.getString("tuvanchuyen");
				this.GiaVanChuyen=rs.getString("GiaVanChuyen");
				
			}
			
			rs.close();

			rs= this.db.get("select count(ma) as num from sanpham where trangthai='1'");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			this.spId = new String[i];
			this.masp = new String[i];
			this.tensp = new String[i];
			this.denghi = new String[i];
			this.ton   = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dongia = new String[i];
			this.tienbVAT = new String[i];
			this.donvi = new String[i];
			this.dongia_tuvc=new String[i];
			this.dongia_kovc=new String[i];
			this.goivc=new String[i];
			this.tthetich=new String[i];
			this.tkhoiluong=new String[i];
			
			rs.close();
		
			
			String sql= 
			"		 SELECT  SP.THETICH,SP.TRONGLUONG  , SP.PK_SEQ,SP.TEN,SP.MA,QC.SOLUONG2 AS SOLUONGCHAN,QC.SOLUONG1 AS SOLUONGLE,QC.DVDL1_FK AS DVDLCHAN "+  
			"			, ISNULL(BG.GIAMUANPP,0)  AS DONGIA,QCVC.SOLUONG2/QCVC.SOLUONG1 AS GOIVC  "+
			"			,ISNULL(BG.GIAMUANPP_TUVC,0)  AS DONGIA_TUVC  ,DVDL.DONVI AS DONVICHAN,0 AS TONKHOCHAN "+ 
			"		 FROM SANPHAM SP   "+
			"			LEFT JOIN NGANHHANG NH ON NH.PK_SEQ=SP.NGANHHANG_FK "+  
			"			INNER JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND QC.DVDL1_FK=SP.DVDL_FK  AND QC.DVDL2_FK=100018 "+ 
			"			INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=QC.DVDL1_FK   "+
			"			LEFT JOIN QUYCACH QCVC ON QCVC.SANPHAM_FK=SP.PK_SEQ  AND QCVC.SANPHAM_FK=SP.PK_SEQ AND QCVC.DVDL2_FK='100052' "+  
			"		    INNER JOIN          "+
			"		(    "+
			"			 SELECT  D.SANPHAM_FK,D.GIAMUANPP,ISNULL(GIAMUANPP_TUVC,0) AS GIAMUANPP_TUVC "+   
			"			 FROM     			"+
			"				 BGMUANPP_SANPHAM D "+  
			"				 WHERE D.BGMUANPP_FK "+
			"				 IN "+
			"				 ( "+
			"					SELECT TOP(1) B.PK_SEQ FROM BANGGIAMUANPP B "+    
			"						INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK "+   
			"					WHERE C.NPP_FK="+this.nppId+" AND B.TUNGAY <='"+this.ngaydh+"' and b.dvkd_fk="+dvkdId+" and b.kenh_fk="+kbhId+" "+
			"					ORDER BY TUNGAY DESC "+ 
			"				) "+
			"		 ) BG ON BG.SANPHAM_FK=SP.PK_SEQ  AND BG.GIAMUANPP>0 AND SP.TRANGTHAI = 1  "+ 
			"		 ORDER BY NH.TEN,SP.MA " ;
			System.out.println("Du Lieu SQL : "+sql);
			rs= this.db.get(sql);
			i = 0;		
			double tmpbvat = 0;
			double tmpvat = 0;
			double tmpavat = 0;
			
			while(rs.next()){
				this.spId[i] = rs.getString("pk_seq");
				this.masp[i] = rs.getString("ma");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[i] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[i] + "'";
				}
			
				this.tensp[i] = rs.getString("ten");
				
				this.tthetich[i] = rs.getString("thetich");
				this.tkhoiluong[i] = rs.getString("trongluong");
				
				this.qc[i] = rs.getString("SOLUONGLE");
			
				this.ton[i]   = "0" ;
				this.goivc[i]=rs.getString("goivc");
				if (Double.valueOf(this.ton[i].trim()).doubleValue() < 0){
					this.ton[i] = "0";
				}
				
				this.denghi[i]	 = "0";
				this.sl[i]	 = "0";
				
				this.dongia_tuvc[i] = "" + Math.round( rs.getDouble("dongia")* Double.parseDouble(this.qc[i]) - rs.getDouble("goivc")  * Double.parseDouble(this.GiaVanChuyen)) ;
				this.dongia_kovc[i] = "" + Math.round(rs.getDouble("dongia")* Double.parseDouble(this.qc[i]));
				if(this.tuvanchuyen.equals("1")){
					this.dongia[i]=this.dongia_tuvc[i];	
				}else{
					this.dongia[i]=	this.dongia_kovc[i]	;
				}
				
				this.tienbVAT[i] = "0";
				this.donvi[i] = "Thùng";
				
				i++;
			}
			rs.close();
			
			this.size = "" + i;	
			this.tongtienbVAT = "0"; 		
			this.vat = "0" ;
			this.tongtienaVAT = "0";
			
			query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
			this.ncc = this.db.get(query);
		
			query = "select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh";
			this.dvkdIds = this.db.get(query);
			
			query = "select pk_seq as kbhId, diengiai as kbh from kenhbanhang";
			this.kbhIds = this.db.get(query);
			
			

		}catch(Exception e){ 
			e.printStackTrace();
			//System.out.println("Loi day nek : "+e.toString());
		}
		finally{try {
			if(rs != null) rs.close();
		} catch (Exception e2) {
			
		}}
	}

	private Hashtable Quycach(){
		String query = "select distinct a.ma, b.soluong1 from sanpham a, quycach b where a.pk_seq = b.sanpham_fk and b.dvdl2_fk=100018 order by a.ma";
		ResultSet rs = this.db.get(query);
		Hashtable h = new Hashtable();
		try{
			while(rs.next()){
				h.put(rs.getString("ma"), rs.getString("soluong1"));
			}
		}catch(Exception e){}
		return h;
	}
	
	public boolean CreateDdhnpp(HttpServletRequest request) 
	{	
		getNppInfo();
		ResultSet rs = null;
		String query;
		Hashtable h = Quycach();
		try
		{
			if(this.LoaiDonhang.equals("1"))
			{
				this.ThueSuat=0;
			}
			this.tongtienbVAT = request.getParameter("tongtienbvat");	
			this.vat = request.getParameter("vat");
			this.tongtienaVAT = request.getParameter("tongtienavat");

			this.tongtienbVAT = this.tongtienbVAT.replace(",", "");
			this.vat = this.vat.replace(",", "");
			this.tongtienaVAT = this.tongtienaVAT.replace(",", "");

			String gsbh = "null"; 
			String asm = "null";
			String bm = "null";

			double tmpbvat = 0;
			double tmpvat = 0;
			double tmpavat = 0;
			double total = 0;
			double ck=3.5;
			this.masp = request.getParameterValues("masp");
			this.spId = request.getParameterValues("spId");	
			
			this.size = "" + this.masp.length;
			int size = this.masp.length;
			this.tspId = new String[size];
			
			this.denghi = request.getParameterValues("denghi");
			this.tdenghi = new String[size];
			
			this.tensp = request.getParameterValues("tensp");
			this.ttensp = new String[size];
			
			this.ton = new String[size];
			this.tton = new String[size];
			
			this.sl = new String[size];
			
			this.tmasp = new String[size];
			this.tsl = new String[size];
			this.ttsl = new String[size];
			
			this.dongia = new String[size];
			this.tdongia = new String[size];
			
			this.tienbVAT = new String[size];
			this.ttienbVAT = new String[size];
			
			this.donvi = new String[size];
			this.tdonvi = new String[size];
			this.dongia_kovc=new String[size];
			this.dongia_tuvc=new String[size];
			this.qc=new String [size];
			boolean checkTon = true;  
			int n = 0;
			this.maspstr = "";
			
			for (int i = 0; i < size ; i++)
			{
				if (this.maspstr.length()==0)
				{
					this.maspstr = "'" + this.masp[i] + "'";
				}else
				{
					this.maspstr = this.maspstr + ",'" + masp[i] + "'";
				}
				this.qc[i]=request.getParameter("qc" + this.masp[i]).replaceAll(",", "");;				
				this.sl[i] =request.getParameter("sl" + this.masp[i]).replaceAll(",", "");;
				this.ton[i] = request.getParameter("ton" + this.masp[i]).replaceAll(",", "");;
				this.ton[i]=this.ton[i].replaceAll(",", "");
				this.donvi[i] = request.getParameter("dv" + this.masp[i]);				
				this.dongia[i] =request.getParameter("dg" + this.masp[i]).replaceAll(",", "");;				
				this.dongia_kovc[i] =request.getParameter("dg_kovc" + this.masp[i]).replaceAll(",", "");;
				this.dongia_tuvc[i] =request.getParameter("dg_tuvc" + this.masp[i]).replaceAll(",", "");;
				if (this.dongia[i].length()==0)
				{
					this.dongia[i] = "0";
				}else
				{
					this.dongia[i] = this.dongia[i].replace(",", "");
				}				
				this.tienbVAT[i] = request.getParameter("t" + this.masp[i]).replaceAll(",", "");;
				this.tienbVAT[i] = this.tienbVAT[i].replace(",", "");
				this.denghi[i]=this.denghi[i].replaceAll(",", "");
				
				this.sl[i]=this.sl[i].replaceAll(",", "");				
				if (this.sl[i].length()==0)
					this.sl[i] = "0";
			}
			
			db.getConnection().setAutoCommit(false);
			this.ChietKhauThuongMai=0;

			String HinhThucVanChuyen="";
			if(this.tuvanchuyen.equals("1"))
			{
				HinhThucVanChuyen="KHVC";
			}
			
			query = "insert into dondathang (thongtin,ChietKhauThuongMai,CHIETKHAU,TUVANCHUYEN,doihang,loaidonhang,ngaydenghigh,ngaydat,sotienbvat,nguoitao,nguoisua,trangthai,npp_fk,ncc_fk,vat,sotienavat,dvkd_fk,denghidathang_fk,kbh_fk,soid,gsbh_fk,asm,bm,tinhtrang,iskm, khott_fk,HinhThucVanChuyen) " +
					" values(N'"+this.GhiChu+"','"+this.ChietKhauThuongMai+"','"+ck+"','"+this.tuvanchuyen+"','"+this.DoiHang+"','"+this.LoaiDonhang+"','"+this.NgayDeNghiGH+"','" + this.ngaydh + "', '" + this.tongtienbVAT + "','" + this.nguoitao + "','" + this.nguoisua + "','0'," +
							"  '" + this.nppId + "','"+this.nccId+"','" + this.ThueSuat + "','" + this.tongtienaVAT +"' " +
									",'" + this.dvkdId + "','" + this.dndhId + "','" + this.kbhId + "', '0', "+gsbh+","+asm+", " + bm + ", '0','0','"+this.kho+"','"+HinhThucVanChuyen+"')";			
			if(!this.db.update(query))
			{
				db.update("rollback");
				this.msg = "Loi:" +query;
				return false;
			}

			query = "select IDENT_CURRENT('dondathang') as ddhId";
			rs = this.db.get(query);		
			try
			{
			rs.next();
			this.id = rs.getString("ddhId");
			//System.out.println("Id don dat hang la: " + this.id + "\n");
			}catch(Exception er){}
		
			
			String slg;
			String dg;
			double tienbvat = 0;
			double vat =0;
			double tienavat = 0;

			
			for (int i = 0; i < this.masp.length ; i++)
			{
				double quycach=0;
				try{
					 quycach=Double.parseDouble(qc[i]);
				}catch(Exception er)
				{
					
				}
				slg = "" + Double.valueOf(this.sl[i]).doubleValue()* Double.valueOf((String)h.get(this.masp[i])).doubleValue();				
				dg = "" + Double.valueOf(this.dongia[i]).doubleValue()/ Double.valueOf((String)h.get(this.masp[i])).doubleValue();						
				
				double giachuan=Double.parseDouble(this.dongia_kovc[i])/Double.valueOf((String)h.get(this.masp[i])).doubleValue();
				tienbvat =Double.valueOf(slg).doubleValue()*Double.valueOf(dg).doubleValue();
				
				vat = tienbvat*this.ThueSuat;				
				tmpbvat = tmpbvat + Double.valueOf(tienbvat).doubleValue();								
				tmpvat = tmpvat +Double.valueOf(tienbvat).doubleValue()*this.ThueSuat;				
				tienavat = Double.valueOf(tienbvat).doubleValue()+Double.valueOf(tienbvat).doubleValue()* this.ThueSuat;				
				tmpavat = tmpavat +Double.valueOf(tienavat).doubleValue(); 
				
				query = "insert into dondathang_sp (dondathang_fk,sanpham_fk,soluong,soluongduyet,donvi,dongia,sotienbvat,vat,sotienavat,khott,GiaChuan,dvdl_duyet_fk,sott) " +
						" values('" + this.id + "','" + this.spId[i] + "','" + slg + "','"+slg+"',N'"+donvi[i]+"','" + dg + "','" + tienbvat + "','" + vat +"','" + tienavat + "','"+this.kho+"','"+giachuan+"',100018,"+i+")";		
				if(!this.db.update(query))
				{
					this.msg = query;
					//System.out.println("sQL: "+ query + "row: "+ i);
					this.db.update("rollback");
					return false;					
				}
				 if (this.dndhId.length()>0)
				 {
					query = "update denghidathang_sp set dadathang ='" + tienbvat + "' where sanpham_fk='" + this.spId[i] + "' and denghidathang_fk='" + dndhId + "'";				
					if(!this.db.update(query))
					{	
						this.msg = query;
						//System.out.println("sQL: "+ query + "row: "+ i);
						this.db.update("rollback");
						return false;
					}
					total = total + Double.valueOf(tienbvat).doubleValue();
				 }
			}
			double tienCKTT=tmpbvat*ck/100;
			double tiensauck=tmpbvat-tienCKTT-this.ChietKhauThuongMai;;
			vat = tiensauck * this.ThueSuat;
			double tongtiencovat = tiensauck + vat;
			if(this.ChietKhauThuongMai>tiensauck)
			{
				if(tmpbvat-tienCKTT-1000<ChietKhauThuongMai)
					ChietKhauThuongMai=tmpbvat-tienCKTT-1000;
				vat=(tmpbvat-tienCKTT-ChietKhauThuongMai)*this.ThueSuat;
				tongtiencovat=tmpbvat-tienCKTT-ChietKhauThuongMai+vat;
			}
		 query = "update dondathang set  ChietKhauThuongMai='"+this.ChietKhauThuongMai+"',sotienbvat='" + tmpbvat + "', vat = '" + this.ThueSuat*100 + "', sotienavat ='" + tongtiencovat + "' where pk_seq ='" + this.id + "'";
		 if(!this.db.update(query))
		 {
			this.msg = query;
			//System.out.println("sQL: "+ query  );
			this.db.update("rollback");
			return false;
		 }
					
		 if (this.dndhId.length()>0)
		 {
			query = "update denghidathang set trangthai='2', dadathang= cast(dadathang as float) + " +total + " where pk_seq = '" + this.dndhId + "'";
			 if(!this.db.update(query))
			 {
				 this.msg = query;
				 this.db.update("rollback");
					//System.out.println("sQL: "+ query  );
					return false;
			 }
		 }
		/* query=
			"	update ThanhToanCKTM set sudung=ISNULL(SuDung,0)+ISNULL(ChietKhauThuongMai,0) "+
			"	from  "+
			"	( "+
			"		select "+ 
			"		sum  "+
			"		(   "+
			"			(  "+
			"				a.soluongduyet*a.dongia*(1-(isnull(a.chietkhau,0)/100)) "+ 
			"			) *(1-isnull(b.chietkhau,0)/100 )  "+
			"		) as doanhso,b.npp_fk,(select convert(varchar(10),dateadd(month,-1,substring(ngaydat,0,8)+'-01'),20)) as thoigian,ChietKhauThuongMai "+
			"		from dondathang_sp a inner join dondathang b on a.dondathang_fk=b.pk_seq   "+
			"		where a.dondathang_fk='"+this.id+"'  "+
			"		group by b.npp_fk,ngaydat,ChietKhauThuongMai "+
			"	) as dh inner join ThanhToanCKTM as ck on "+
			"	dh.npp_fk=ck.npp_fk and ck.nam=substring(dh.thoigian,0,5) and ck.thang=substring(dh.thoigian,6,2) ";
			if (!db.update(query))
			{
				this.msg = "Lỗi hệ thống " + query;
				db.update("rollback");
				return false;
			}		*/
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);		
				
		return true;
		
		}catch(Exception  e)
		{	
			e.printStackTrace();
			db.update("rollback");
		
			try
			{
				if(rs != null)
					rs.close();
			} catch (Exception e2) 
			{
				//System.out.println("Println ErroerR: "+e2.toString());
			}
			return false;	
		}
	}
	
	public boolean ChotDdhnpp(HttpServletRequest request){
		if (  UpdateDdhnpp(request)){
			this.db.update("update dondathang set trangthai='1',NgayDat='"+getDateTime()+"',NgayGioDat=getdate() where pk_seq='" + this.id + "'");
			this.db.update("delete dondathang_sp where soluong <=0 and dondathang_fk="+this.id);

			return true;
		}else{
			return false;
		}
	}
	public boolean UpdateDdhnpp(HttpServletRequest request) {
		getNppInfo();
		ResultSet rs = null;
	
		try{
		
			Utility util = new Utility();
			Hashtable h = Quycach();
			this.masp = request.getParameterValues("masp");
			if(this.LoaiDonhang.equals("1")){
				this.ThueSuat=0;
			}
			this.spId = request.getParameterValues("spId");	
			this.denghi = request.getParameterValues("denghi");
			this.tensp = request.getParameterValues("tensp");
			double ck=3.5;
/*			if(Integer.parseInt(this.ngaydh.substring(8,10))<=15)
			{
				ck=4;
			}*/
			this.size = "" + masp.length;
			int size = masp.length;
			
			this.tspId = new String[size];	
			this.ton = new String[size];
			this.sl = new String[size];
			this.qc=new String[size];
			this.tmasp = new String[size];
			this.tsl = new String[size];
			this.ttsl = new String[size];
			this.dongia = new String[size];
			this.tienbVAT = new String[size];
			this.dongia = new String[size];
			this.dongia_tuvc = new String[size];
			this.dongia_kovc = new String[size];
			this.donvi = new String[size];

			this.ttensp = new String[size];
			this.tdenghi = new String[size];
			this.tton = new String[size];
			this.tdonvi = new String[size];
			this.tdongia = new String[size];
			this.ttienbVAT = new String[size];
			
			this.tongtienbVAT = request.getParameter("tongtienbvat");
			this.tongtienbVAT = this.tongtienbVAT.replace(",", "");
			
			this.vat = request.getParameter("vat");
			this.vat = this.vat.replace(",", "");
			
			this.tongtienaVAT = request.getParameter("tongtienavat");
			this.tongtienaVAT = this.tongtienaVAT.replace(",", "");

			String ddhId = this.id; 
							
			double tmpbvat = 0;
			double tmpvat = 0;
			double tmpavat = 0;
			double total = 0;
			
			int n = 0;
			this.maspstr = "";
			
			for (int i = 0; i < size ; i++){
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[i] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[i] + "'";
				}
				
				this.sl[i] = request.getParameter("sl" + this.masp[i]).replaceAll(",", "");;
				this.qc[i]=request.getParameter("qc" + this.masp[i]);
				this.ton[i] = request.getParameter("ton" + this.masp[i]).replaceAll(",", "");;
				this.ton[i]  = this.ton[i].replaceAll(",", "");
				this.donvi[i] = request.getParameter("dv" + this.masp[i]);
				this.dongia[i] = request.getParameter("dg" + this.masp[i]).replaceAll(",", "");;
				this.dongia_kovc[i] =request.getParameter("dg_kovc" + this.masp[i]).replace(",", "");
				this.dongia_tuvc[i] =request.getParameter("dg_tuvc" + this.masp[i]).replace(",", "");
				if (this.dongia[i].length()==0){
					this.dongia[i] = "0";
				}else{
					this.dongia[i] = this.dongia[i].replace(",", "");
				}
				if (this.sl[i] .length()==0){
					this.sl[i]  = "0";
				}else{
					this.sl[i]  = this.sl[i].replaceAll(",", "");
				}
				
				this.tienbVAT[i] = request.getParameter("t" + this.masp[i]);
				this.tienbVAT[i] = this.tienbVAT[i].replace(",", "");
				this.denghi[i]=this.denghi[i].replaceAll(",", "");
				if (this.sl[i].length()==0)
					this.sl[i] = "0";
			}
		
		
		db.getConnection().setAutoCommit(false); 
		this.ChietKhauThuongMai=0;
		
		String query = "select denghidathang_fk as dndhId from dondathang where pk_seq = '" + this.id + "'";
		rs = this.db.get(query);
		//System.out.println("query3: "+query);
		
		rs.next();
		String dndhId = rs.getString("dndhId");
		rs.close();
		
		query = "select count(dondathang_fk) as num from dondathang_sp where dondathang_fk='"+ this.id + "'";
		rs = this.db.get(query);
		//System.out.println("query5: "+ query);
		rs.next();
		int ddh_sp = Integer.valueOf(rs.getString("num")).intValue();
		rs.close();
		this.db.update("update dondathang_sp set soluong='0' ,soluongduyet='0' where dondathang_fk='" + this.id + "'");
		String slg;
		String dg;
		double tienbvat = 0;
		double vat = 0;
		double tienavat = 0;
		for (int i = 0; i < this.masp.length ; i++){
			
				double quycach=0;
				try{
					quycach=Double.parseDouble(this.qc[i]);
				}catch (Exception e) {
					// TODO: handle exception
				}
				slg = "" + (Double.valueOf(this.sl[i]).doubleValue())* Double.valueOf((String)h.get(this.masp[i])).doubleValue();
				
				dg = "" + Double.parseDouble(this.dongia[i])/ Double.valueOf((String)h.get(this.masp[i])).doubleValue();
								
				tienbvat =Double.valueOf(slg).doubleValue()*Double.valueOf(dg).doubleValue();
					
				vat =  Double.valueOf(tienbvat).doubleValue()* this.ThueSuat;
				
				tmpbvat = tmpbvat + (Double.valueOf(tienbvat).doubleValue());				
				
				tmpvat = tmpvat + Double.valueOf(tienbvat).doubleValue()*this.ThueSuat;
						
				tienavat = Double.valueOf(tienbvat).doubleValue() +Double.valueOf(tienbvat).doubleValue()* this.ThueSuat;
				
				tmpavat = tmpavat + Math.round(Double.valueOf(tienavat).doubleValue());
				
				double giachuan=Double.parseDouble(this.dongia_kovc[i])/Double.valueOf((String)h.get(this.masp[i])).doubleValue();
				
				if (ddh_sp == 0){
					query = "insert into dondathang_sp (dondathang_fk,sanpham_fk,soluong,soluongduyet,donvi,dongia,sotienbvat,vat,sotienavat,khott,GiaChuan,dvdl_duyet_fk,sott) " +
							" values('" + this.id + "','" + this.spId[i] + "','" + slg + "','"+slg+"',N'"+donvi[i]+"','" + dg + "','" + tienbvat + "','" + vat +"','" + tienavat + "','"+this.kho+"','"+giachuan+"',100018,"+i+")";										
				}else{
					query = " update dondathang_sp set sott="+i+", dongia="+dg+",Giachuan="+giachuan+", soluongduyet='"+slg+"',soluong ='" + slg + "', sotienbvat = '" + tienbvat + "', vat ='" + vat +"', sotienavat = '" + tienavat + "' where sanpham_fk = '" + this.spId[i] + "' and dondathang_fk = '"+ this.id + "'";
				}

			//System.out.println("sQL: "+ query + "row: "+ i);
			
			if(!this.db.update(query)){
				db.update("rollback");
				this.msg = query;
				//System.out.println("sQL: "+ query + "row: "+ i);
				return false;
					
			}

			if(dndhId.length() >0){
			query = "update denghidathang_sp set dadathang ='" + tienbvat + "' where sanpham_fk='" + this.spId[i] + "' and denghidathang_fk='" + dndhId + "'";
			if(!this.db.update(query)){
			
				db.update("rollback");
				this.msg = query;
				//System.out.println("sQL: "+ query + "row: "+ i);
				return false;
			}
			total = total + Double.valueOf(tienbvat).doubleValue();
			}
		}
			double tienCKTT=tmpbvat*ck/100;
			double tiensauck=tmpbvat-tienCKTT-this.ChietKhauThuongMai;;
			vat = tiensauck * this.ThueSuat;
			double tongtiencovat = tiensauck + vat;
			if(this.ChietKhauThuongMai>tiensauck)
			{
				if(tmpbvat-tienCKTT-1000<ChietKhauThuongMai)
					ChietKhauThuongMai=tmpbvat-tienCKTT-1000;
				vat=(tmpbvat-tienCKTT-ChietKhauThuongMai)*this.ThueSuat;
				tongtiencovat=tmpbvat-tienCKTT-ChietKhauThuongMai+vat;
				}
			
			String HinhThucVanChuyen="";
			if(this.tuvanchuyen.equals("1"))
			{
				HinhThucVanChuyen="KHVC";
			}
			
			 query = "update dondathang set  HinhThucVanChuyen='"+HinhThucVanChuyen+"',ChietKhauThuongMai='"+this.ChietKhauThuongMai+"',sotienbvat='" + tmpbvat + "', vat = '" + this.ThueSuat*100 + "', sotienavat ='" + tongtiencovat + "' " +
			 		" ,thongtin=N'"+this.getghichu()+"',nguoisua="+this.userId+",ngaydenghigh='"+this.NgayDeNghiGH+"',tuvanchuyen='"+this.tuvanchuyen+"' where pk_seq ='" + this.id + "'";
			 if(!this.db.update(query))
			 {
				this.msg = query;
				this.db.update("rollback");
				return false;
			 }
			query = "update denghidathang set dadathang='" + total + "', trangthai='1' where pk_seq ='" + dndhId + "'";
			if(!this.db.update(query))
			{
				db.update("rollback");
				this.msg = query;
				return false;
			}		 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
				
			}catch(Exception  e){
				e.printStackTrace();
				db.update("rollback");
				return false;
			}
			 
		
		
	}

	

	private void getNppInfo(){
		
		
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		this.kho = util.getKhoSAP();
		
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
		d = "" + day + "-" + month + "-" + year;
		return d;
	}
	
	
	private String convertDate() {
        String newdate ="";
	    int day = Integer.valueOf(this.ngaydh.substring(0, 2)).intValue();		
	    int month = Integer.valueOf(this.ngaydh.substring(3, 5)).intValue();
	    int year = Integer.valueOf(this.ngaydh.substring(6, 10)).intValue();
        
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

		
		try {
			if(this.dvkdIds != null)
				this.dvkdIds.close();
			if(this.kbhIds != null)
				this.kbhIds.close();
			if(this.ncc != null)
				this.ncc.close();
			if(!(this.db == null)){
				this.db.shutDown();
			}
			
		} catch (Exception e) {
			
		}
	}
	
	public String[] getKhoiluong() 
	{	
		return this.tkhoiluong;
	}
	
	public void setKhoiluong(String[] khoiluong) 
	{
		this.tkhoiluong = khoiluong;
	}
	
	public String[] getThetich() 
	{
		return this.tthetich;
	}
	
	public void setThetich(String[] thetich) 
	{
		this.tthetich = thetich;
	}
	@Override
	public String[] getSlDuyet() {
		
		return this.slduyet;
	}
	@Override
	public void setSlDuyet(String[] tslduyet) {
		
		this.slduyet=tslduyet;
	}
	@Override
	public String getNgaydenghigh() {
		
		return this.NgayDeNghiGH;
	}
	@Override
	public void setNgaydenghigh(String ngaydenghigh) {
		
		this.NgayDeNghiGH=ngaydenghigh;
		
	}
	@Override
	public String getDoiHang() {
		
		return this.DoiHang;
	}
	@Override
	public void setDoiHang(String doihang) {
		
		this.DoiHang=doihang;
	}
	@Override
	public String getLoaiDonHang() {
		
		return this.LoaiDonhang;
	}
	@Override
	public void setLoaiDonHang(String loaidonhang) {
		
		this.LoaiDonhang=loaidonhang;
		
	}
	@Override
	public double getThueSuat() {
		
		return this.ThueSuat;
	}
	@Override
	public void setThueSuat(double thuesuat) {
		
		this.ThueSuat=thuesuat;
	}
	@Override
	public String getLyDoHuy() {
		
		return this.LyDoHuy;
	}
	@Override
	public void setLyDoHuy(String _LyDoHuy) {
		
		this.LyDoHuy=_LyDoHuy;
	}
	@Override
	public String getTuVanchuyen() {
		
		return this.tuvanchuyen;
	}
	@Override
	public void settuvanchuyen(String tuvanchuyen) {
		
		this.tuvanchuyen=tuvanchuyen;	
	}
	@Override
	public String[] getDongia_tuvc() {
		
		return this.dongia_tuvc;
	}
	@Override
	public void setDongia_tuvc(String[] dongia_tuvc) {
		
		this.dongia_tuvc=dongia_tuvc;
	}
	@Override
	public String[] getDongia_kovc() {
		
		return this.dongia_kovc;
	}
	@Override
	public void setDongia_kovc(String[] dongia_kovc) {
		
		this.dongia_kovc=dongia_kovc;
	}
	@Override
	public void init_reload(HttpServletRequest request) 
	{	
	    this.initSelectboxData();
		if(this.LoaiDonhang.equals("1"))
		{
			this.ThueSuat=0;
		}
		this.tongtienbVAT = request.getParameter("tongtienbvat");	
		this.vat = request.getParameter("vat");
		this.tongtienaVAT = request.getParameter("tongtienavat");

		this.tongtienbVAT = this.tongtienbVAT.replace(",", "");
		this.vat = this.vat.replace(",", "");
		this.tongtienaVAT = this.tongtienaVAT.replace(",", "");
		
		this.masp = request.getParameterValues("masp");
		this.spId = request.getParameterValues("spId");	
		
		this.size = "" + this.masp.length;
		int size = this.masp.length;
		this.tspId = new String[size];
		
		this.denghi = request.getParameterValues("denghi");
		this.tdenghi = new String[size];
		
		this.tensp = request.getParameterValues("tensp");
		this.ttensp = new String[size];
		
		this.ton = new String[size];
		this.tton = new String[size];
		
		this.sl = new String[size];
		
		this.tmasp = new String[size];
		this.tsl = new String[size];
		this.ttsl = new String[size];
		
		this.dongia = new String[size];
		this.tdongia = new String[size];
		
		this.tienbVAT = new String[size];
		this.ttienbVAT = new String[size];
		
		this.donvi = new String[size];
		this.tdonvi = new String[size];
		this.dongia_kovc=new String[size];
		this.dongia_tuvc=new String[size];
		this.qc=new String [size];
		
		int n = 0;
		this.maspstr = "";
		
		for (int i = 0; i < size ; i++){
			if (this.maspstr.length()==0){
				this.maspstr = "'" + this.masp[i] + "'";
			}else{
				this.maspstr = this.maspstr + ",'" + masp[i] + "'";
			}
			this.qc[i]=request.getParameter("qc" + this.masp[i]);
			this.sl[i] =request.getParameter("sl" + this.masp[i]);
			this.ton[i] = request.getParameter("ton" + this.masp[i]);
			this.ton[i]=this.ton[i].replaceAll(",", "");		    
			this.donvi[i] = request.getParameter("dv" + this.masp[i]);
			this.dongia[i] =request.getParameter("dg" + this.masp[i]);
			this.dongia_kovc[i] =request.getParameter("dg_kovc" + this.masp[i]).replace(",","");
			this.dongia_tuvc[i] =request.getParameter("dg_tuvc" + this.masp[i]).replace(",","");
			if(this.tuvanchuyen.equals("1"))
			{
				this.dongia[i]=this.dongia_tuvc[i];
			}else
			{
				this.dongia[i]=this.dongia_kovc[i];
			}
			this.denghi[i]=this.denghi[i].replaceAll(",", "");			
			this.sl[i]=this.sl[i].replaceAll(",", "");
			if (this.sl[i].length()==0)
			{
				this.sl[i] = "0";
			}
			this.tienbVAT[i] =Math.round(Double.parseDouble(this.sl[i])* Double.parseDouble(this.dongia[i]))+"";
		}
	}
	public static void main(String[] arg)
	{
		String sql="2013-02-12";
		//System.out.println("__"+sql.substring(8,10));
	}
	@Override
	public String[] getChietkhau()
	{
		return this.chietkhau;
	}
	@Override
	public void setChietkhau(String[] chietkhau)
	{
		this.chietkhau=chietkhau;
	}
	
	public double getChietKhauThuongMai(String nppId,String ngaydat,String ddhId)
	{
		dbutils db=new dbutils();
		double thuong=0;
		boolean flag=false;
		String thoigian="";
		String query="select convert(varchar(10),DATEADD(month,-1,'"+ngaydat.split("-")[0]+"-"+ngaydat.split("-")[1]+"-01'),20) as ThoiGian ";		
		
		ResultSet rs=db.get(query);
		try
		{
			while(rs.next())
			{
				thoigian=rs.getString("thoigian");
			}rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		String ngaycuoithang ="";
		 query ="select convert(varchar(10),DATEADD(day,-1,'"+ngaydat.split("-")[0]+"-"+ngaydat.split("-")[1]+"-01'),20) as ThoiGian ";
		 rs=db.get(query);
		try
		{
			while(rs.next())
			{
				ngaycuoithang=rs.getString("thoigian");
			}rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
		query="select DISTINCT t.THANG,t.NAM,t.KBH_FK,t.DVKD_FK,b.Thuong,b.Tu from TIEUCHITINHTHUONG t "+ 
				" inner join TIEUCHITINHTHUONG_CHITIET b on t.PK_SEQ=b.TIEUCHITINHTHUONG_FK  "+
				" where t.THANG="+thoigian.split("-")[1]+" and t.NAM="+thoigian.split("-")[0]+" and t.LOAI=5 and b.STT=1 and b.Tieuchi=1 and t.TRANGTHAI=1";
					
			String mucngay="0";
			String thuongdunghan="0";
			rs=db.get(query);
			try{
				if(rs.next())
				{
					mucngay=rs.getString("Tu");
					thuongdunghan=""+(rs.getDouble("Thuong")/100);
				}
			}catch(Exception er)
			{
				er.printStackTrace();
			}
		
		
		//System.out.println("[Check CKTM 112]"+query);
		String nam=thoigian.split("-")[0];
		String thang=thoigian.split("-")[1];
		
		 query=
		"select count(*) from ThanhToanCKTM WHERE NPP_FK='"+nppId+"' and thang='"+thang+"' and nam='"+nam+"' " ;
		 rs=db.get(query);
		try
		{
			while(rs.next())
			{
				if(rs.getInt(1)<=0)
				flag=true;
			}
			rs.close();
		} catch (Exception e1)
		{
			e1.printStackTrace();
		} 
		System.out.println("[Check CKTM 111]"+query);
		if(flag)
		{
			 query=
 			" select sum(thuongdspri)as thuongdspri  \n" +
			"  from  \n" +
			"  ( 		  \n" +
			" 	select isnull(thuong.thuongdspri,0) * isnull(thucdat.doanhso,0) +isnull(thucdat.doanhso,0)*ThuongDungHan.Thuong/100 as thuongdspri \n" +
			" 	from  \n" +
			" 	(	 \n" +
			" 		select ctprinpp.CHITIEU_FK,thang, nam, ctprinpp.nhapp_fk as npp_fk, kenh_fk as kbh_fk, ctpri.dvkd_fk, ctprinpp.chitieu       \n" +
			" 		from chitieu ctpri inner join chitieu_nhapp ctprinpp on ctpri.pk_seq = ctprinpp.chitieu_fk      \n" +
			" 		where ctpri.trangthai<> '2'   and   ctpri.thang ='"+thang+"' and ctpri.nam ='"+nam+"' and ctprinpp.NHAPP_FK='"+nppId+"' \n" +
			" 	)as chitieu left join  \n" +
			" 	(  \n" +
			" 			select nh.npp_fk,nh.kbh_fk,sp1.dvkd_fk , sum(isnull(nhsp.gianet,0)* isnull(nhsp.soluong,0)) -sum(isnull(cktt,0))  as doanhso        \n" +
			" 			from  \n" +
			" 		(    \n" +
			" 			select * from nhaphang where NGAYCHUNGTU>= '"+thoigian+"' and NGAYCHUNGTU <='"+nam+"-"+thang+"-31' and npp_fk='"+nppId+"'     \n" +
			" 			and trangthai !=2 and pk_seq not in(select nhaphang_fk from huynhaphang where TRANGTHAI=1 ) and loaihoadon=0    \n" +
			" 		) nh inner join nhaphang_sp nhsp on nhsp.nhaphang_fk = nh.pk_seq  left join sanpham sp1 on sp1.ma = nhsp.sanpham_fk           \n" +
			" 		group by nh.npp_fk,nh.kbh_fk,sp1.dvkd_fk   \n" +
			" 	)as thucdat  on thucdat.dvkd_fk=chitieu.dvkd_fk and thucdat.kbh_fk=chitieu.kbh_fk  and thucdat.npp_fk=chitieu.npp_fk  \n" +
			" left join  \n" +
			" ( \n" +
			" 	select  npp_fk ,"+thuongdunghan+" as thuong    		 \n" +
			"  \n" +
			" from khoasongay    		 \n" +
			" 	where ngayks='"+ngaycuoithang+"' and  replace(convert(char(10),  ngaygio  , 102) , '.', '-' ) <= replace(convert(char(10), dateadd(day, "+mucngay+", cast ('"+ngaycuoithang+"' as datetime)) , 102) , '.', '-' )     \n" +
			" 	and npp_fk="+nppId+" \n" +
			" )  KhoaSo on KhoaSo.NPP_FK =chitieu.npp_fk \n" +
			" left join \n" +
			" ( \n" +
			" 	select DISTINCT t.THANG,t.NAM,t.KBH_FK,t.DVKD_FK,b.Thuong,b.Tu from TIEUCHITINHTHUONG t \n" +
			" 		inner join TIEUCHITINHTHUONG_CHITIET b on t.PK_SEQ=b.TIEUCHITINHTHUONG_FK  \n" +
			" 	where t.THANG="+thang+" and t.NAM="+nam+" and t.LOAI=5 and b.STT=1 and b.Tieuchi=1 and t.TRANGTHAI=1 \n" +
			" ) as ThuongDungHan on ThuongDungHan.DVKD_FK=chitieu.DVKD_FK and ThuongDungHan.KBH_FK=chitieu.kbh_fk  \n" +
			" left join  \n" +
			" (	  \n" +
			" 	select  t.thang,t.nam,t.kbh_fk,t.dvkd_fk,b.thuong /100  as thuongdspri,b.tieuchi,b.tu,b.den  \n" +
			" 	from tieuchitinhthuong t    \n" +
			" 		inner join tieuchitinhthuong_chitiet b on t.pk_seq=b.tieuchitinhthuong_fk    \n" +
			" 	where t.thang='"+thang+"' and t.nam='"+nam+"' and t.loai=5  and b.tieuchi=4 and t.trangthai=1  \n" +
			" )as thuong on thuong.dvkd_fk=chitieu.dvkd_fk  \n" +
			" and thuong.kbh_fk=chitieu.kbh_fk and isnull(thucdat.doanhso,0) *100 / (isnull(chitieu.chitieu,0)+0.001) > = thuong.tu  " +
			" and   isnull(thucdat.doanhso,0) *100 / (isnull(chitieu.chitieu,0)+0.001) <  thuong.den )as thuong  " ;
			 System.out.println("[Check CKTM 113]"+query);
			 rs=db.get(query);
			 try
			{
				while(rs.next())
				 {
					thuong= rs.getDouble("thuongdspri");
					if(thuong>0)
					{
						 query=
						" insert into ThanhToanCKTM(Thang,Nam,npp_fk,thuong,sudung) " +
					 	" select '"+thang+"','"+nam +"','"+nppId+"','"+thuong+"',0  ";
						 if(!db.update(query))
						 {
							 System.out.println("Error"+query);
						 }
					}
					 System.out.println("[Check CKTM 114]"+query);
				 }
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			
		}else
		{
			query=
				"select (Thuong-isnull(SuDung,0)  	" ;
			if(ddhId!=null&&ddhId.length()>0 )
			query+=
				" + ISNULL(	(SELECT ChietKhauThuongMai 	"+
				"	FROM 								"+  
				"	DonDatHang   						"+
				"	WHERE PK_SEQ="+ddhId+") ,0) 		";	
			query+=" ) as Thuong From ThanhToanCKTM WHERE NPP_FK='"+nppId+"' and thang='"+thang+"' and nam='"+nam+"' ";
			rs=db.get(query);
			System.out.println("[CKTM 115]"+query);
			try
			{
				while(rs.next())
				{
					thuong=rs.getDouble("thuong");
				}rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return thuong;
	}
	public double getChietKhauThuongMai()
	{
		return ChietKhauThuongMai;
	}
	public void setChietKhauThuongMai(double chietKhauThuongMai)
	{
		ChietKhauThuongMai = chietKhauThuongMai;
	}
	@Override
	public String[] getGoivc() {

		return this.goivc;
	}
	@Override
	public void setGoivc(String[] Goivc) {

		this.goivc=Goivc;
	}
	@Override
	public String getGiavanchuyen() {

		return this.GiaVanChuyen;
	}
	@Override
	public void setGiavanchuyen(String Giavanchuyen) {

		this.GiaVanChuyen=Giavanchuyen;
		
	}
	@Override
	public String getghichu() {

		return this.GhiChu;
	}
	@Override
	public void setghichu(String ghichu) {

		this.GhiChu=ghichu;
	}
	@Override
	public double getChietkhauTrucTiep() {

		return this.ChietkhauTrucTiep;
	}
	@Override
	public void setChietkhauTrucTiep(double chietkhau) {

		this.ChietkhauTrucTiep=chietkhau;
	}
	
}
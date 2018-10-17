package geso.traphaco.distributor.beans.hoadontaichinh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspose.cells.Color;
import com.aspose.cells.Workbook;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hoadontaichinh.IBCChiTietCongNo;
import geso.traphaco.distributor.db.sql.dbutils;

public class BCChiTietCongNo implements IBCChiTietCongNo
{ 
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private String tuNgay;
	private String denNgay;
	private String nppId;
	private String nppID;
	private String ngayKS;
	private String KHid;
	private ResultSet rs;
	private ResultSet KHrs;
	private ResultSet NppRs;
	String vungId;
	String khuvucId;
	String doitacId;
	String ttId;
	ResultSet khuvuc;
	ResultSet vung;
	ResultSet doitacRs;
	
	String type;
	dbutils db;
	
	public BCChiTietCongNo()
	{
		this.userId = "";
		this.userName = "";
		this.tuNgay = "";
		this.denNgay = "";
		//this.nppId = "";
		this.nppID="";
		this.ngayKS = "";
		this.KHid="";
		this.vungId="";
		this.khuvucId ="";
		this.ttId = "";
		this.type  = "0";
		this.doitacId = "";
		//init1();
		
		db = new dbutils();
	}
	
	public void setUserId(String userId) {
		
		if (userId != null)
			this.userId = userId;
		this.getNgayKS();
	}
	
	public String getUserId() {
		
		return this.userId;
	}

	public void setTuNgay(String tuNgay) {
		
		if (tuNgay != null)
			this.tuNgay = tuNgay;
	}
	
	public String getTuNgay() {
		
		return this.tuNgay;
	}


	public void setDenNgay(String denNgay) {
		
		if (denNgay != null)
			this.denNgay = denNgay;
	}


	public String getDenNgay() {
		
		return this.denNgay;
	}


	public String getNPPID() {
		
		return this.nppId;
	}


	public void getNgayKS() {
		
		Utility ut = new Utility();
		this.nppId = ut.getIdNhapp(userId);

		dbutils db = new dbutils();
		String sql = "SELECT TOP(1) NGAYKS FROM KHOASONGAY WHERE NPP_FK = '"+ this.nppId +"' ORDER BY NGAYKS DESC";

		ResultSet ks = db.get(sql);
		try{
			ks.next();
			this.ngayKS = ks.getString("ngayks");
		
		}catch(Exception e){
			this.ngayKS = "";
		}
		if (db != null)
			db.shutDown();


	}
	public void dbClose() {
			
			try {
				if (rs != null)
					rs.close();
	
			} catch (Exception e) {
				
			}
	
		}	
	
	public String getUserName() {
		
		return this.userName;
	}


	
	public void setUserName(String userName) {
		
		this.userName=userName;
	}

	public ResultSet getKHList() {
		
		return this.KHrs;
	}



	public void setKHList(ResultSet KHlist) {
		
		this.KHrs=KHlist;
	}



	public String getKHId() {
		
		return this.KHid;
	}



	public void setKHId(String khid) {
		
		this.KHid=khid;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		//this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		//this.sitecode=util.getSitecode();
	}
	
	public String getNppId() {
		return this.nppId;
	}


	public void setNppId(String nppId) {
		this.nppId=nppId;
		
	}

	public ResultSet getNppRs() {
		
		return this.NppRs;
	}

	public void setNppRs(ResultSet nppRs) {
		this.NppRs=nppRs;
		
	}
	
	
	public String getPhanloai()
  {
  	return phanloai;
  }

	public void setPhanloai(String phanloai)
  {
  	this.phanloai = phanloai;
  }

	public String getLoaiNv()
  {
  	return loaiNv;
  }

	public void setLoaiNv(String loaiNv)
  {
  	this.loaiNv = loaiNv;
  }



	String phanloai="";
	String loaiNv="";

	
	Utility Ult = new Utility();
	public void init1()
	{
		System.out.println("query ne: "+ this.nppId);
		String query="";
		
			getNppInfo();
						
			this.vung = db.get("select pk_seq,ten,diengiai from vung ");
			
			if (this.vungId.length() > 0)
			{			
				this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk ='" + this.vungId + "'");
			} else
			{
				String query_khuvuc=" select PK_SEQ, TEN from KHUVUC "
						+ "	where PK_SEQ in (select KHUVUC_Fk from NHAPHANPHOI "
						+ "	where pk_seq in "+ Ult.quyen_npp(userId)+")"; 			
				this.khuvuc= db.get(query_khuvuc);		
			}
			query="select pk_Seq,ten,ma from nhaphanphoi where iskhachhang=0 and trangthai=1 and pk_Seq in (" + Ult.quyen_npp(userId)+")";
			if (this.khuvucId.length() > 0)
			{
				query = query + " and khuvuc_fk ='" + this.khuvucId + "'";
			}
			if (this.vungId.length() > 0)
			{
				query = query + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
			}
			this.NppRs = this.db.get(query);
			
			query="select  kh.PK_SEQ, (kh.MAFAST + ' - ' + kh.TEN) AS TEN from KHACHHANG kh inner join NHAPHANPHOI npp on kh.NPP_FK=npp.PK_SEQ where 1=1";
			
			if(this.nppId.trim().length()>0)
				query+=	" and kh.NPP_FK = '" + this.nppId + "' ";
			if (this.khuvucId.length() > 0)
			{
				query = query + " and npp.khuvuc_fk ='" + this.khuvucId + "'";
			}
			if (this.vungId.length() > 0)
			{
				query = query + " and npp.khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
			}
			System.out.println("KH "+query);
			this.KHrs= db.get(query);
			
			query = 
				"	select PK_SEQ,  isnull(maFAST, '') + ', ' + TEN as TEN  " +
				"	from NHAPHANPHOI where TRANGTHAI = '1' ";
			if(nppId.trim().length()>0)
				query += " and tructhuoc_fk = '" + this.nppId + "' ";
			else
				query += " and iskhachhang=0 ";
			
			System.out.println(query);
			this.doitacRs = this.db.get(query);
			
		}
			
	public void DBclose() 
	{
		this.db.shutDown();
	}

	
	public void createStaticHeader(Workbook workbook) {
		
		
	}

	
	public void init() {
		
		
	}

	
	public void tieuDe(Workbook workbook, int rowIndex) {
		
		
	}

	
	public void createStaticData(Workbook workbook) {
		
		
	}

	
	public void getCellStyle(Workbook workbook, String cellName, Color color,
			Boolean bold, int size) {
		
		
	}

	
	public void createBorderSetting(Workbook workbook, String fileName) {
		
		
	}

	
	public String getDateTime() {
		
		return null;
	}

	
	public void setvungId(String vungId) {
		
		this.vungId = vungId;
	}

	
	public String getvungId() {
		
		return this.vungId;
	}

	
	public void setvung(ResultSet vung) {
		
		this.vung=vung;
	}

	
	public ResultSet getvung() {
		
		return this.vung;
	}

	
	public void setkhuvucId(String khuvucId) {
		
		this.khuvucId=khuvucId;
	}

	
	public String getkhuvucId() {
		
		return this.khuvucId;
	}

	
	public void setkhuvuc(ResultSet khuvuc) {
		
		this.khuvuc=khuvuc;
	}

	
	public ResultSet getkhuvuc() {
		
		return this.khuvuc;
	}

	
	public ResultSet getTtRs() {
		
		return null;
	}

	
	public void setTtRs(ResultSet ttRs) {
		
		
	}

	
	public String getTtId() {
		
		return this.ttId;
	}

	
	public void setTtId(String ttId) {
		
		this.ttId = ttId;
	}

	
	public String getBCTheoKH() {
		
		String query = "";

     	String condition0="";
     	String condition="";
     		if(nppId.trim().length()>0){     			
     			if(KHid.trim().length()>0){
    	    		condition=KHid;	    		
    	    	}
     			else{
     				dbutils db = new dbutils();
     				String pr="";
     				pr ="SELECT PK_SEQ, isnull(maFAST,'')+ ' - '+ TEN as TEN FROM KHACHHANG where 1=1 and npp_fk ='"+ nppId +"' ";
     				ResultSet khrs =db.get(pr);
     				condition0 = "";
     				String s="";
					 if(khrs!=null)
					 {
						 try {
							while(khrs.next())
							 {
								 s+=khrs.getString("PK_SEQ")+",";
							 }
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
						 
						 s = s.substring(0, s.length()-1);
						 
					 }
					 condition0 += s;
     			}     				
     		}
     		
     		String dnkh ="";
     		String thangdn="";
			dbutils db = new dbutils();
			dnkh= "SELECT MONTH('"+tuNgay+"')-1 as Month";	
	    	ResultSet month = db.get(dnkh);
	    	if(month!=null){
	    		try {
					while(month.next()){
						thangdn= month.getString("Month");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    	dnkh="";
	    	
	    	String khdn ="";
	    	String nppdn="";
	    	String Search_kh_dndk="";
	    	String Search_npp_dndk="";
	    	String Search_vung_mien="";
	    	String ddkd="";
	    	if(this.KHid.trim().length()>0)
	    	{	
	    		khdn ="   and kh.PK_SEQ='"+this.KHid+"'";
	    		Search_kh_dndk =  "   and hd.KHACHHANG_FK in ("+this.KHid+") \n";
	    	}
	    	
	    	if(nppId.trim().length()>0)
	    	{
	    		nppdn=" and kh.NPP_FK='"+nppId+"'";
	    		Search_npp_dndk=  "   and hd.NPP_FK ='"+nppId+"' \n";
	    	}
	    	 
	    	if(this.vungId.trim().length()>0)
	    		Search_vung_mien+="  and v.PK_SEQ='"+this.vungId+"' \n";
	    	if(this.khuvucId.trim().length()>0)
	    		Search_vung_mien+="  and kv.PK_SEQ='"+this.khuvucId+"' \n";
	    	 
	    	if(this.ttId.trim().length()>0)
	    	{
	    		khdn+=" and kh.npp_fk in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+ttId+"' ) ";
	    		Search_npp_dndk+=" and hd.npp_fk in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+ttId+"' ) ";
	    	}
	    	    	
     			
     		// CÂU LẤY BÁO CÁO
	    	query= "" +	    			
	    		"	( \n"+ 
	    		" 			SELECT hd.NGAY, SOHOADON  as ChungTu, DienGiai, tongtienAVAT as PSN, '' as PSC, 1 as sott, (CAST (hd.HOADON_FK as nvarchar(20)))  as HOADONNPP_FK \n"+
	    		"			FROM \n"+
	    		" 				( \n"+
	    		" 					SELECT hd.NPP_FK,hd.KHACHHANG_FK , (''+ CAST (hd.HOADON_FK AS Varchar(20))) HOADON_FK ,hd.NGAYXUATHD as NGAY,hd.SOHOADON, tongtienAVAT,DienGiai \n"+ 
	    		" 					FROM  \n"+
	    		"						(" +
	    		"							SELECT 	hd.NPP_FK,hd.KHACHHANG_FK, ('HD KHAC '+ CAST (hd.PK_SEQ AS Varchar(20))) HOADON_FK  ,('HD '+ CAST(hd.SOHOADON as nvarchar(20))) as SOHOADON,hd.ngayhoadon NGAYXUATHD ,hd.KHACHHANG_FK  PK_SEQ, \n"+ 
				"									hd.avat tongtienavat, '' as DienGiai \n"+ 
				"							FROM 	ERP_HoaDonPheLieu hd \n"+ 
				"									LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+
				"									LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+ 
				"									LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
				"							WHERE	hd.TRANGTHAI = 1 and hd.ngayhoadon >='"+tuNgay+"' and hd.ngayhoadon <='"+denNgay+"'   \n";
											    	if(this.KHid.trim().length()>0)
										    		{
										    			query+=" and hd.KHACHHANG_FK='"+this.KHid+"'";
										    		}
													if(this.nppId.trim().length()>0)
													{
														query+= " and hd.NPP_FK ='"+this.nppId+"' \n";
													}
													if(this.ttId.trim().length()>0)
											    	{
														query+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
											    	}
				query+=									
				"							UNION ALL \n"+
	    		"							SELECT 	hd.NPP_FK,hd.KHACHHANG_FK, ('HD OTC '+ CAST (hd.PK_SEQ AS Varchar(20))) HOADON_FK  ,('HD '+ CAST(hd.SOHOADON as nvarchar(20))) as SOHOADON,hd.NGAYXUATHD ,hd.KHACHHANG_FK  PK_SEQ, \n"+
				"									hd.tongtienavat, '' as DienGiai \n"+	
				"		 					FROM 	HOADON hd \n"+
				"									LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+
				"									LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"									LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
				"		 					WHERE 	hd.LOAIHOADON =0 and hd.TRANGTHAI in (2,4) and hd.NGAYXUATHD >='"+tuNgay+"' and hd.NGAYXUATHD <='"+denNgay+"' "+ Search_vung_mien+" \n";

								    		if(this.KHid.trim().length()>0)
								    		{
								    			query+=" and hd.KHACHHANG_FK='"+this.KHid+"'";
								    		}
											if(this.nppId.trim().length()>0)
											{
												query+= " and hd.NPP_FK ='"+this.nppId+"' \n";
											}
											if(this.ttId.trim().length()>0)
									    	{
												query+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
									    	}
				
				query+=	
				"\n							UNION ALL \n"+
				
				" 							SELECT 	hd.NPP_FK,bt.KHACHHANG_FK,('HD OTC ' + CAST(bt.HOADON_FK AS nvarchar(20))) AS HOADON_FK,(''+ CAST(bt.PK_SEQ as nvarchar(20))) as SOHOADON, bt.NGAYCHUNGTU AS NGAYXUATHD, hd.KHACHHANG_FK PK_SEQ, \n"+
				"									round(bt.GHINO,0) AS tongtienavat, (N'Phiếu bù trừ công nợ HD OTC '+ hd.SOHOADON) DienGiai  \n"+ 
			"	  							FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ inner join \n"+ 
				"									(   \n"+
				"		  								SELECT 	bt.PK_SEQ, bt.NGAYCHUNGTU,bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as GHINO \n"+ 
				"		  								FROM 	BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+ 
				"		  								WHERE 	bt.TRANGTHAI = 1 and bt.LOAIBUTRU=0 \n"+
				"		  								GROUP BY bt.PK_SEQ, bthd.KHACHHANG_FK, bthd.HOADON_FK, bt.NGAYCHUNGTU \n"+
				"		 							) \n"+
				"		 							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n" +
				"									LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+
		 		"									LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"									LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+	
				"							WHERE 	1=1 and hd.NGAYXUATHD <='"+denNgay+"' and hd.NGAYXUATHD >='"+tuNgay+"' and hd.TRANGTHAI in (2,4) and hd.LOAIHOADON =0 and bt.GHINO !=0 "+Search_vung_mien+" \n";
				 
											if(this.KHid.trim().length() >0){
												query += " 						and hd.KHACHHANG_FK in ("+this.KHid+") \n";
											}
											if(this.nppId.trim().length()>0)
											{
												query+= " 						and hd.NPP_FK='"+this.nppId+"' \n";
											}
											if(this.ttId.trim().length()>0)
									    	{
												query+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
									    	}
				query+=
				"\n							UNION ALL \n"+
				
				" 							SELECT 	hd.NPP_FK,bt.KHACHHANG_FK,('HD OTC ' + CAST(bt.HOADON_FK AS nvarchar(20))) AS HOADON_FK,(''+ CAST(bt.PK_SEQ as nvarchar(20))) as SOHOADON, bt.NGAYCHUNGTU AS NGAYXUATHD, hd.KHACHHANG_FK PK_SEQ, \n"+
				"									round(bt.GHINO,0) AS tongtienavat, (N'Phiếu bù trừ công nợ HD ETC '+ hd.SOHOADON) DienGiai \n"+ 
				"	  						FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+
				"									INNER JOIN \n"+
				"										(   \n"+
				"		  									SELECT 	bt.PK_SEQ, bt.NGAYCHUNGTU,bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as GHINO \n"+ 
				"		  									FROM 	BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+ 
				"		  									WHERE 	bt.TRANGTHAI = 1 and bt.LOAIBUTRU=0 \n"+
				"		  									GROUP BY bt.PK_SEQ, bthd.KHACHHANG_FK, bthd.HOADON_FK, bt.NGAYCHUNGTU \n"+
				"		 								) "+
				"		 								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK " +
				"										LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
				"										LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"										LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
				"							WHERE 	1=1 and hd.TRANGTHAI in (2,4) and hd.NGAYXUATHD <='"+this.denNgay+"' and hd.NGAYXUATHD >='"+this.tuNgay+"' and bt.GHINO !=0 "+Search_vung_mien+" \n";
								if(this.KHid.trim().length() >0){
									query += " and hd.KHACHHANG_FK in ("+this.KHid+") \n";
								}
								if(this.nppId.trim().length()>0)
								{
									query+= " and hd.NPP_FK='"+this.nppId+"' \n";
								}	
								if(this.ttId.trim().length()>0)
						    	{
									query+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
						    	}
				query+=
					"			     		UNION ALL \n"+ 
					"						  SELECT 	btcn.NPP_FK,btcn_hd.KHACHHANG_FK ,(N'PKT ' + CAST(btcn.PK_SEQ as nvarchar(20))) as HOADON_FK,''  as SOHOADON,btcn.NGAYCHUNGTU as NGAYXUATHD, btcn_hd.KHACHHANG_FK PK_SEQ, \n"+									  
					"						  			round(sum(isnull(btcn_hd.GHINO,0)),0) as tongtienavat, (N'Phiếu kế toán ' + CAST(btcn.PK_SEQ AS nvarchar(20))) DienGiai \n"+
					"						  FROM 		BUTRUCONGNO btcn INNER JOIN BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+ 	 									
					"													 LEFT JOIN NHAPHANPHOI npp on btcn.NPP_FK = npp.PK_SEQ \n"+ 
					"													 LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+ 
					"													 LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+ 
									 
					"						  WHERE 	1=1 and btcn.LoaiBuTru = 1 and btcn.TRANGTHAI=1 and btcn.NGAYCHUNGTU >='"+tuNgay+"' and btcn.NGAYCHUNGTU <='"+denNgay+"' \n"+Search_vung_mien;
		     		if(nppId.trim().length()>0)
		     			query+=" 						and btcn.NPP_FK='"+this.nppId+"' \n";
		     		if(this.KHid.trim().length()>0)
		     			query+=" 						and btcn_hd.KHACHHANG_FK='"+this.KHid+"' \n";
		     		if(ttId.trim().length()>0)
			    	{
		     			query+="  and btcn.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+ttId+"' ) ";
			    	}
		     		query+=
				"						  GROUP BY btcn.NGAYCHUNGTU, btcn.PK_SEQ, btcn.NGAYCHUNGTU, btcn_hd.KHACHHANG_FK, btcn.NPP_FK \n"+
				" 						  having   round(sum(isnull(btcn_hd.GHINO,0)),0)!=0 \n"+
	    		"							UNION ALL \n"+	
	    		
	    		"							SELECT 	hdETC.NPP_FK,hdETC.KHACHHANG_FK, ('HD ETC '+ CAST (hdETC.PK_SEQ AS Varchar(20))) HOADON_FK,('HD '+ CAST(hdETC.SOHOADON as nvarchar(20))) as SOHOADON,hdETC.NGAYXUATHD,kh.PK_SEQ, \n"+ 
	    		"									round(SUM(hdETC.AVAT - hdETC.AVAT_CK),0) as tongtienavat,'' as DienGiai  \n"+
	    		"							FROM \n"+
	    		"								(  \n"+	    			
	    		" 									SELECT  ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK,ETC.ddkd_fk,ETC.PK_SEQ as HOADONNPP_FK,npp_fk,\n"+
				" 											sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia, \n"+    
				" 											sum( soluong * dongia )  as BVAT,( sum( soluong * dongia*thuexuat/100 ) ) as VAT, \n"+  
				" 											sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT, \n"+
				" 											sum(isnull(chietkhau,0)*(1+thuexuat/100)) as AVAT_CK, \n"+        
				" 											sum(isnull(thuexuat,0)) as BVAT_CK \n"+       
				" 									FROM ( \n"+  
				" 											SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau,c.vat, \n"+
				" 												( \n"+   
				"													SELECT	top(1) bb.DDKD_FK \n"+   
				"													FROM 	ERP_HOADONNPP_DDH aa INNER JOIN ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+    
				"													WHERE 	aa.HOADONNPP_FK=c.HOADON_FK \n"+   
				"	 											) as ddkd_fk , \n"+ 
				" 												case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+   
				" 												case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat \n"+   
				" 											FROM 	ERP_HOADONNPP a \n"+   
				"													INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk \n"+   
				"													INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq \n"+   
				"													INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq \n"+  
				" 											WHERE 	1=1 and c.SOLUONG > 0 and a.trangthai in ( 2 , 4 ) \n"+
				"													and a.NgayXuatHD>='"+tuNgay+"' \n"+ 
				"													and a.NgayXuatHD<='"+denNgay+"' \n"+  
				" 										)ETC \n"+
				" 						  		    GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK,ETC.ddkd_fk,ETC.PK_SEQ,npp_fk \n"+
	    		"								)as hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ=hdETC.HOADONNPP_FK \n"+
	    		"									   	  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=hdETC.DDKD_FK \n"+
	    		"									   	  INNER JOIN KHACHHANG kh on kh.PK_SEQ=hdETC.KHACHHANG_FK \n"+
	    		"									   	  INNER JOIN NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK \n"+
				"										  LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"										  LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
	    		"	 						WHERE 1=1 "+Search_vung_mien+" \n";
							    		if(this.KHid.trim().length()>0)
							    		{
							    			query+=" 				and kh.PK_SEQ='"+this.KHid+"'";
							    		}
										if(this.nppId.trim().length()>0)
										{
											query+= " 				and hd.NPP_FK ='"+this.nppId+"' \n";
										}
										if(this.ttId.trim().length()>0)
								    	{
											query+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+ttId+"' ) ";
								    	}
				query+=
				"               			GROUP BY hdETC.NPP_FK,hdETC.KHACHHANG_FK, hdETC.SOHOADON,hdETC.NGAYXUATHD,kh.PK_SEQ,hdETC.PK_SEQ \n"+
	    		"					)hd \n"+
	    		"				GROUP BY hd.NPP_FK,hd.KHACHHANG_FK,hd.HOADON_FK,hd.NGAYXUATHD ,hd.SOHOADON, hd.tongtienAVAT,hd.DienGiai  \n"+ 
	    		"			)	hd \n"+
	    	
	    		"	) 	\n"+
	    		
	    		"	UNION ALL \n"+	
	    		
	    		"	(	\n"+

	    		"	 SELECT tt.NGAYCHUNGTU as NGAY, tt.PK_SEQ as ChungTu, DienGiai, '' as PSN, tt.SOTIENTT as PSC, 2 as sott, tt.HOADONNPP_FK \n"+ 
	    		"	 FROM \n"+
	    		"		( \n"+
	    		"			SELECT 	tt.NGAYCHUNGTU, CAST(tt.PK_SEQ as nvarchar(20)) as PK_SEQ, ('HD OTC '+ CAST (hd.PK_SEQ AS Varchar(20))) as HOADONNPP_FK, hd.NGAYXUATHD, (N'Thu tiền hóa đơn OTC '+ hd.SOHOADON) DienGiai, round(sum(isnull(tt.sotienTT,0)),0) as SOTIENTT \n"+ 
	    		"			FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
	    		"							  INNER JOIN ( \n"+
	    		"												SELECT 	tt.NPP_FK,tt.NGAYCHUNGTU,tt.PK_SEQ,tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+ 
	    		"												FROM 	ERP_THUTIENNPP tt INNER JOIN ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK \n"+
	    		"												WHERE 	tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >='"+tuNgay+"' and tt.NGAYCHUNGTU <='"+denNgay+"' and tthd.LOAIHD = 0 \n"+
	    		"												GROUP BY tt.NPP_FK, tt.NGAYCHUNGTU,tt.PK_SEQ,tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+
	    		"							  			  ) tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+
				"								 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
				"								 LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"								 LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+	
	    		"			WHERE 	1=1 "+Search_vung_mien+" \n";
	    		
					    		if(this.KHid.trim().length() >0){
									query += " 				and hd.KHACHHANG_FK in ("+this.KHid+") \n";
								}
								if(this.nppId.trim().length()>0)
								{
									query+= " 				and hd.NPP_FK='"+nppId+"' \n";
								}
								if(this.ttId.trim().length()>0)
						    	{
									query+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
						    	}
				
				query+=
	    		"			GROUP BY tt.NGAYCHUNGTU, tt.PK_SEQ, hd.PK_SEQ , hd.NGAYXUATHD, hd.SOHOADON \n"+	
	    			 
	    		"	 		UNION ALL \n"+
	    		
	    		"			SELECT 	tt.NGAYCHUNGTU, CAST(tt.PK_SEQ as nvarchar(20)) as PK_SEQ, ('HD Khac '+ CAST (hd.PK_SEQ AS Varchar(20))) as HOADONNPP_FK, hd.NGAYHOADON NGAYXUATHD, (N'Thu tiền hóa đơn khác '+ hd.SOHOADON) DienGiai, round(sum(isnull(tt.sotienTT,0)),0) as SOTIENTT \n"+ 
	    		"			FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
	    		"							  INNER JOIN ( \n"+
	    		"												SELECT 	tt.NPP_FK,tt.NGAYCHUNGTU,tt.PK_SEQ,tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+ 
	    		"												FROM 	ERP_THUTIENNPP tt INNER JOIN ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK \n"+
	    		"												WHERE 	tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >='"+tuNgay+"' and tt.NGAYCHUNGTU <='"+denNgay+"' and tthd.LOAIHD = 1 \n"+
	    		"												GROUP BY tt.NPP_FK, tt.NGAYCHUNGTU,tt.PK_SEQ,tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+
	    		"							  			  ) tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+
				"								 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
				"								 LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"								 LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+	
	    		"			WHERE 	1=1 "+Search_vung_mien+" \n";
	    		
					    		if(this.KHid.trim().length() >0){
									query += " 				and hd.KHACHHANG_FK in ("+this.KHid+") \n";
								}
								if(this.nppId.trim().length()>0)
								{
									query+= " 				and hd.NPP_FK='"+nppId+"' \n";
								}
								if(this.ttId.trim().length()>0)
						    	{
									query+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
						    	}
				
				query+=
	    		"			GROUP BY tt.NGAYCHUNGTU, tt.PK_SEQ, hd.PK_SEQ , hd.NGAYHOADON, hd.SOHOADON \n"+	
	    			 
	    		"	 		UNION ALL \n"+
	    			 
	    		"			SELECT 	tt.NGAYCHUNGTU, CAST (tt.PK_SEQ as nvarchar(20)) as PK_SEQ, 'HD ETC '+ CAST( hd.PK_SEQ AS VARCHAR(20)) as HOADONNPP_FK, hd.NGAYXUATHD, (N'Thu tiền hóa đơn ETC ' +hd.SOHOADON) DienGiai, round(sum(isnull(tt.sotienTT,0)),0) as SOTIENTT \n"+ 
	    		"			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
	    		"								INNER JOIN ( \n"+
	    		"											SELECT 	tt.NPP_FK, tt.NGAYCHUNGTU,tt.PK_SEQ,tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+ 
	    		"											FROM 	ERP_THUTIENNPP tt INNER JOIN ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK \n"+
	    		"											WHERE 	tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >='"+tuNgay+"' and tt.NGAYCHUNGTU <='"+denNgay+"' and tthd.LOAIHD = 0  \n"+
	    		"											GROUP BY tt.NGAYCHUNGTU,tt.PK_SEQ,tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, tt.NPP_FK \n"+
	    		"							  			   ) tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+
				"				    		 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
				"							 LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"							 LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+	
	    		"		 	WHERE 	1=1 "+Search_vung_mien+" \n";
				    		if(this.KHid.trim().length() >0){
								query += " and hd.KHACHHANG_FK in ("+this.KHid+") \n";
							}
							if(this.nppId.trim().length()>0)
							{
								query+= " and hd.NPP_FK='"+this.nppId+"' \n";
							}
							if(this.ttId.trim().length()>0)
					    	{
								query+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
					    	}
				
				query+=						
	    		"		 	GROUP BY tt.NGAYCHUNGTU, tt.PK_SEQ, hd.PK_SEQ , hd.NGAYXUATHD, hd.SOHOADON \n"+
	    			 
	    		"	 		UNION ALL \n"+
	    			 
	    		"		 	SELECT 	tt.NGAYCHUNGTU,('PT HDDN '+ CAST (tt.PK_SEQ as nvarchar(20))) as PK_SEQ, ('HD DN '+ CAST (dnkh.PK_SEQ as nvarchar(20)))  as HOADONNPP_FK,dnkh.NgayDuNo as NGAYXUATHD, N'Thu tiền hóa đơn dư nợ' + CAST(dnkh.PK_SEQ as nvarchar(20)) SOHOADON,round(SUM(tt.sotienTT),0) as SOTIENTT \n"+  
	    		"		 	FROM 	DUNO_KHACHHANG dnkh inner join KHACHHANG k on dnkh.KHACHHANG_FK=k.PK_SEQ \n"+ 
	    		"		 			INNER JOIN \n"+ 
	    		"							( \n"+
	    		"								SELECT 	tt.NPP_FK,tt.NGAYCHUNGTU, tt.PK_SEQ,tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+ 
	    		"								FROM 	ERP_THUTIENNPP tt INNER JOIN ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK \n"+ 
	    		"								WHERE 	tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU <='"+denNgay+"' and tt.NGAYCHUNGTU >='"+tuNgay+"' and tthd.LOAIHD = 2 \n"+
	    		"								GROUP BY tt.NPP_FK,tt.NGAYCHUNGTU, tt.PK_SEQ,tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+ 
	    		"			 				)tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+
				"		    		LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ \n"+ 
				"		 			LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+	
	    		"		 	WHERE 	1=1 "+Search_vung_mien+" \n";
				    		if(this.KHid.trim().length() >0){
								query += " and dnkh.KHACHHANG_FK in ("+this.KHid+") \n";
							}
							if(this.nppId.trim().length()>0)
							{
								query+= " and dnkh.NPP_FK='"+this.nppId+"' \n";
							}
							if(this.ttId.trim().length()>0)
					    	{
								query+="  and dnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
					    	}
				query+=
	    		"		  GROUP BY tt.NGAYCHUNGTU, tt.PK_SEQ, dnkh.NgayDuNo , dnkh.KHACHHANG_FK,dnkh.PK_SEQ \n"+
	    			 
	    		"	 	  UNION ALL \n"+
	    			 
	    		"		  SELECT 	tttl.NgayTra, (N'Phiếu nhập lại '+ CAST(tttl.PK_SEQ  as nvarchar(20))) as PK_SEQ, ('HangTra') as HOADONNPP_FK, tttl.NgayTra as NGAYXUATHD, N'Nhập lại hàng do kH trả lại' as DienGiai, round(sum(isnull(tttl.TienSauThue,0)),0) as SOTIENTT \n"+
	    		"		  FROM 		Erp_HangTraLaiNpp tttl \n"+
				"		    		LEFT JOIN NHAPHANPHOI npp on tttl.NPP_FK = npp.PK_SEQ \n"+ 
				"	 				LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
	    		"		  WHERE 	tttl.trangthai=1 and tttl.Ngaytra >='"+tuNgay+"' and tttl.Ngaytra <=	'"+denNgay+"' "+Search_vung_mien+" \n";
				    		if(this.KHid.trim().length() >0){
								query += " 				and tttl.KHACHHANG_FK in ("+this.KHid+") \n";
							}
							if(nppId.trim().length()>0)
							{
								query+= " 				and tttl.NPP_FK='"+nppId+"' \n";
							}
							if(ttId.trim().length()>0)
					    	{
								query+="  and tttl.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+ttId+"' ) ";
					    	}
				query+=
	    		"		  GROUP BY  tttl.NgayTra, tttl.PK_SEQ,  tttl.NgayTra, tttl.SOHOADON \n"+
	    		
	    		" 		  UNION ALL \n"+
				
				" 		  SELECT 	xnkh.NGAYCHUNGTU, (N'Phiếu xóa nợ ' + CAST(xnkh.PK_SEQ as nvarchar(20))) as PK_SEQ,('HD OTC '+ CAST (xnkh_hd.HOADON_FK as nvarchar(20)))  as HOADONNPP_FK,xnkh.NGAYCHUNGTU as NGAYXUATHD, (N'Xóa dư nợ HD OTC ' +hd.SOHOADON) DienGiai, round(sum(isnull(xnkh_hd.SOTIENXOA,0)),0) as SOTIENTT \n"+ 
				" 		  FROM 		XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+ 
				"	 									INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+
				"										LEFT JOIN NHAPHANPHOI npp on xnkh.NPP_FK = npp.PK_SEQ \n"+ 
				"										LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"										LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+Search_vung_mien+" \n"+
				" 		  WHERE 	1=1 and xnkh.NGAYCHUNGTU <='"+denNgay+"' and xnkh.NGAYCHUNGTU >='"+tuNgay+"' \n";
							
							if(this.KHid.trim().length() >0){
								query += " 				and hd.KHACHHANG_FK in ("+this.KHid+") \n";
							}
							if(this.nppId.trim().length()>0)
							{	
								query+= " 				and xnkh.NPP_FK='"+nppId+"' \n";
							}
							if(this.ttId.trim().length()>0)
					    	{
								query+="  and xnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
					    	}
				
				query+=
				" 		  GROUP BY xnkh.NGAYCHUNGTU, xnkh.PK_SEQ, xnkh.NGAYCHUNGTU, hd.KHACHHANG_FK, xnkh_hd.HOADON_FK, hd.SOHOADON \n"+
				
				" 		  UNION ALL \n"+
				
				" 		  SELECT 	xnkh.NGAYCHUNGTU, (N'Phiếu xóa nợ ' + CAST(xnkh.PK_SEQ as nvarchar(20))) as PK_SEQ,('GESO '+ CAST (xnkh_hd.HOADON_FK as nvarchar(20)))  as HOADONNPP_FK,xnkh.NGAYCHUNGTU as NGAYXUATHD, (N'Xóa dư nợ GESO ' +CAST (hd.PK_SEQ as nvarchar(20))) DienGiai, round(sum(isnull(xnkh_hd.SOTIENXOA,0)),0) as SOTIENTT \n"+ 
				" 		  FROM 		XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+ 
				"	 									INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2  ) \n"+
				"										LEFT JOIN NHAPHANPHOI npp on xnkh.NPP_FK = npp.PK_SEQ \n"+ 
				"										LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"										LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+Search_vung_mien+" \n"+
				" 		  WHERE 	1=1 and xnkh.NGAYCHUNGTU <='"+denNgay+"' and xnkh.NGAYCHUNGTU >='"+tuNgay+"' \n";
							
							if(this.KHid.trim().length() >0){
								query += " 				and hd.KHACHHANG_FK in ("+this.KHid+") \n";
							}
							if(this.nppId.trim().length()>0)
							{	
								query+= " 				and xnkh.NPP_FK='"+nppId+"' \n";
							}
							if(this.ttId.trim().length()>0)
					    	{
								query+="  and xnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
					    	}
				
				query+=
				" 		  GROUP BY xnkh.NGAYCHUNGTU, xnkh.PK_SEQ, xnkh.NGAYCHUNGTU, hd.KHACHHANG_FK, xnkh_hd.HOADON_FK, hd.PK_SEQ \n"+
				
				" 		  UNION ALL \n"+
				
				" 		  SELECT 	xnkh.NGAYCHUNGTU, (N'Phiếu xóa nợ ' + CAST(xnkh.PK_SEQ as nvarchar(20))) as PK_SEQ,('HD ETC '+ CAST (xnkh_hd.HOADON_FK as nvarchar(20)))  as HOADONNPP_FK,xnkh.NGAYCHUNGTU as NGAYXUATHD, (N'Xóa dư nợ HD ETC ' +hd.SOHOADON) DienGiai, round(sum(isnull(xnkh_hd.SOTIENXOA,0)),0) as SOTIENTT \n"+ 
				" 		  FROM		XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+ 
				"	 								INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+
				"					LEFT JOIN NHAPHANPHOI npp on xnkh.NPP_FK = npp.PK_SEQ \n"+ 
				"					LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
				" 		  WHERE 	1=1 and XNKH.NGAYCHUNGTU <='"+denNgay+"' and XNKH.NGAYCHUNGTU >='"+tuNgay+"' "+Search_vung_mien+" \n";
				
							if(this.KHid.trim().length() >0){
								query += " 				and hd.KHACHHANG_FK in ("+this.KHid+") \n";
							}
							if(this.nppId.trim().length()>0)
							{
								query+= " 				and xnkh.NPP_FK='"+this.nppId+"' \n";
							}
							if(this.ttId.trim().length()>0)
					    	{
								query+="  and xnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
					    	}
				
				query+=
				" 			GROUP BY xnkh.NGAYCHUNGTU, xnkh.PK_SEQ, xnkh.NGAYCHUNGTU, hd.KHACHHANG_FK, xnkh_hd.HOADON_FK, hd.SOHOADON \n"+
				
				" 		  UNION ALL \n"+
				
				" 		  SELECT 	xnkh.NGAYCHUNGTU, (N'Phiếu xóa nợ ' + CAST(xnkh.PK_SEQ as nvarchar(20))) as PK_SEQ,('HD Khac '+ CAST (xnkh_hd.HOADON_FK as nvarchar(20)))  as HOADONNPP_FK,xnkh.NGAYCHUNGTU as NGAYXUATHD, (N'Xóa nợ HD Khác ' +hd.SOHOADON) DienGiai, round(sum(isnull(xnkh_hd.SOTIENXOA,0)),0) as SOTIENTT \n"+ 
				" 		  FROM		XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+ 
				"	 								INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1 ) \n"+
				"					LEFT JOIN NHAPHANPHOI npp on xnkh.NPP_FK = npp.PK_SEQ \n"+ 
				"					LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
				" 		  WHERE 	1=1 and XNKH.NGAYCHUNGTU <='"+denNgay+"' and XNKH.NGAYCHUNGTU >='"+tuNgay+"' "+Search_vung_mien+" \n";
				
							if(this.KHid.trim().length() >0){
								query += " 				and hd.KHACHHANG_FK in ("+this.KHid+") \n";
							}
							if(this.nppId.trim().length()>0)
							{
								query+= " 				and xnkh.NPP_FK='"+this.nppId+"' \n";
							}
							if(this.ttId.trim().length()>0)
					    	{
								query+="  and xnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
					    	}
				
				query+=
				" 			GROUP BY xnkh.NGAYCHUNGTU, xnkh.PK_SEQ, xnkh.NGAYCHUNGTU, hd.KHACHHANG_FK, xnkh_hd.HOADON_FK, hd.SOHOADON \n"+
				"\n 		UNION ALL \n"+
					 
				"	  		SELECT 	bt.NGAYCHUNGTU, (N'Phiếu bù trừ ' +CAST(bt.PK_SEQ AS nvarchar(20))) AS PK_SEQ, ('HD ETC ' + CAST(bt.HOADON_FK AS nvarchar(20))) AS HOADONNPP_FK, bt.NGAYCHUNGTU AS NGAYXUATHD, (N'Phiếu bù trừ công nợ HD ETC '+ hd.SOHOADON) DienGiai, \n"+
				"					round(sum(isnull(bt.GHICO,0)),0) AS SOTIENTT \n"+ 
				"			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+ 
	   			"					INNER JOIN ( \n"+
			  	"									SELECT 	bt.PK_SEQ,bt.NGAYCHUNGTU,bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+ 
			  	"									FROM 	BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+ 
			  	"									WHERE   bt.TRANGTHAI = 1 and bt.LOAIBUTRU=0 \n"+ 
			  	"									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK, bt.PK_SEQ,bt.NGAYCHUNGTU \n"+ 
			  	"								) \n"+ 
			  	"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
				"				  	LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
				"		 			LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
			 	"			WHERE 	1=1 and hd.NGAYXUATHD <='"+denNgay+"' and hd.NGAYXUATHD >='"+tuNgay+"' and bt.GHICO !=0 "+Search_vung_mien+" \n";
				if(this.KHid.trim().length() >0){
					query += " 				and hd.KHACHHANG_FK in ("+this.KHid+") \n";
				}
				if(this.nppId.trim().length()>0)
				{
					query+= " 				and hd.NPP_FK='"+this.nppId+"' \n";
				}					
				if(this.ttId.trim().length()>0)
		    	{
					query+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
		    	}
				query+=
				"	  		GROUP BY bt.NGAYCHUNGTU, bt.PK_SEQ, bt.HOADON_FK,bt.NGAYCHUNGTU,hd.SOHOADON  \n"+
					  
				"	   		UNION ALL \n"+
					 
				"	  		SELECT 	bt.NGAYCHUNGTU, (N'Phiếu bù trừ ' +CAST(bt.PK_SEQ AS nvarchar(20))) AS PK_SEQ, ('HD OTC ' + CAST(bt.HOADON_FK AS nvarchar(20))) AS HOADONNPP_FK, bt.NGAYCHUNGTU AS NGAYXUATHD, (N'Bù trừ công nợ HD OTC '+ hd.SOHOADON) DienGiai, \n"+ 
	  			"					round(sum(isnull(bt.GHICO,0)),0) AS SOTIENTT \n"+ 
	  			"			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+ 
		   		"					INNER JOIN ( \n"+ 
			  	"									SELECT bt.PK_SEQ,bt.NGAYCHUNGTU,bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+ 
			  	"									FROM 	BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+ 
			  	"									WHERE   bt.TRANGTHAI = 1 and bt.LOAIBUTRU=0 \n"+ 
			  	"									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK, bt.PK_SEQ,bt.NGAYCHUNGTU \n"+ 
 			 	"								) \n"+ 
			 	"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
				"				 	LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
				"		 			LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
			 	"	  		WHERE 	1=1 and hd.NGAYXUATHD <='"+denNgay+"' and hd.NGAYXUATHD >='"+tuNgay+"' and bt.GHICO !=0 "+Search_vung_mien+" \n";			
							if(this.KHid.trim().length() >0){
								query += " and hd.KHACHHANG_FK in ("+this.KHid+") \n";
							}
							if(this.nppId.trim().length()>0)
							{
								query+= " and hd.NPP_FK='"+this.nppId+"' \n";
							}
							if(this.ttId.trim().length()>0)
					    	{
								query+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
					    	}
				
				query+=
				"	  		GROUP BY bt.NGAYCHUNGTU, bt.PK_SEQ, bt.HOADON_FK,bt.NGAYCHUNGTU,hd.SOHOADON \n"+
				
				" \n 		UNION ALL \n"+
				  
				"  			SELECT 	tt.NGAYCHUNGTU, (N''+CAST(tt.PK_SEQ as nvarchar(20))) AS PK_SEQ, ('HD NA') AS HOADONNPP_FK, tt.NGAYCHUNGTU AS NGAYXUATHD, (N'Phiếu thu tiền HD NA ') DienGiai, round(isnull(ttna.SOTIENTT,0),0) AS SOTIENTT \n"+ 
				" 			FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ \n"+
				"					LEFT JOIN NHAPHANPHOI npp on ttna.NPP_FK = npp.PK_SEQ \n"+ 
				"		 			LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
				"  			WHERE 	1=1 and tt.NGAYCHUNGTU >='"+tuNgay+"' and tt.NGAYCHUNGTU <='"+denNgay+"' "+Search_vung_mien+" \n";
				
							if(this.KHid.trim().length() >0){
								query += " and ttna.KHACHHANG_FK in ("+this.KHid+") \n";
							}
							if(this.nppId.trim().length()>0)
							{
								query+= " and ttna.NPP_FK='"+nppId+"' \n";
							}
							if(this.ttId.trim().length()>0)
					    	{
								query+="  and ttna.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
					    	}
				
				query+=
				"			UNION ALL \n"+
				"	SELECT 	ct.NGAYCHUNGTU, (N'Phiếu cấn trừ ' +CAST(ct.PK_SEQ AS nvarchar(20))) AS PK_SEQ, ('HD OTC ' + CAST(ct.HOADON_FK AS nvarchar(20))) AS HOADONNPP_FK, ct.NGAYCHUNGTU AS NGAYXUATHD, (N'Cấn trừ công nợ HD OTC '+ hd.SOHOADON) DienGiai, \n"+ 
				"			round(sum(isnull(ct.SOTIENCANTRU,0)),0) AS SOTIENTT \n"+ 
				"	FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+ 
				"			INNER JOIN ( \n"+ 
				"							SELECT ct.PK_SEQ,isnull(ct.NGAYCHUNGTU,'') NGAYCHUNGTU,cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+
				"							FROM 	CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+
				"							WHERE   ct.TRANGTHAI = 1 \n"+ 
				"							GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK, ct.PK_SEQ,ct.NGAYCHUNGTU \n"+ 
				"						) \n"+ 
				"						ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+
				"			LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
				"		 	LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
				"			LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
			  	"	WHERE 	1=1 and hd.NGAYXUATHD <='"+denNgay+"' and hd.NGAYXUATHD >='"+tuNgay+"' "+Search_vung_mien+" \n";
				if(this.KHid.trim().length() >0){
					query += " and hd.KHACHHANG_FK in ("+this.KHid+") \n";
				}
				if(this.nppId.trim().length()>0)
				{
					query+= " and hd.NPP_FK='"+this.nppId+"' \n";
				}
				if(this.ttId.trim().length()>0)
		    	{
					query+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
		    	}
				
				query+=
			  	"	GROUP BY ct.NGAYCHUNGTU, ct.PK_SEQ, ct.HOADON_FK,ct.NGAYCHUNGTU,hd.SOHOADON \n"+ 
			  	
			  	" UNION ALL \n"+ 
		 		"  SELECT 	btcn.NGAYCHUNGTU, (N'Phiếu kế toán ' + CAST(btcn.PK_SEQ as nvarchar(20))) as PK_SEQ,('PKT ' + CAST(btcn.PK_SEQ AS nvarchar(20)))  as HOADONNPP_FK,btcn.NGAYCHUNGTU as NGAYXUATHD, (N'Phiếu kế toán ' + CAST(btcn.PK_SEQ AS nvarchar(20))) DienGiai, \n"+ 
		 		"  round(sum(isnull(btcn_hd.GHICO,0)),0) as SOTIENTT \n"+ 
		 		"  FROM 		BUTRUCONGNO btcn INNER JOIN BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+ 	 									
				"								LEFT JOIN NHAPHANPHOI npp on btcn.NPP_FK = npp.PK_SEQ \n"+ 
				"								LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+ 
				"								LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+ 		 
		 		"  WHERE 	1=1 and btcn.TRANGTHAI=1 and btcn.LOAIBUTRU =1 and btcn.NGAYCHUNGTU >='"+tuNgay+"' and btcn.NGAYCHUNGTU <='"+denNgay+"' \n"+Search_vung_mien;
				
		 		if(this.KHid.trim().length() >0){
					query += " and btcn_hd.KHACHHANG_FK in ("+this.KHid+") \n";
				}
				if(this.nppId.trim().length()>0)
				{
					query+= " and btcn.NPP_FK='"+this.nppId+"' \n";
				}
				if(this.ttId.trim().length()>0)
		    	{
					query+="  and btcn.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
		    	}
				
				query+=
		 		"  GROUP BY btcn.NGAYCHUNGTU, btcn.PK_SEQ, btcn.NGAYCHUNGTU, btcn_hd.KHACHHANG_FK \n"+
		 		"  having   round(sum(isnull(btcn_hd.GHICO,0)),0)!=0 \n"+
	    		"	)tt \n"+
	    		"	) 	ORDER BY HOADONNPP_FK,sott "; 	
	    			
	    	System.out.println("câu báo cáo: "+ query);     
	    	
		return query;
	}

	
	public String getBCTheoDoiTac() {
		
		String query = "";
		
		query = 
		"	( \n"+ 
		" 			SELECT hd.NGAY, SOHOADON  as ChungTu, DienGiai, tongtienAVAT as PSN, '' as PSC, 1 as sott, (CAST (hd.HOADON_FK as nvarchar(20)))  as HOADONNPP_FK \n"+ 
		"			FROM \n"+
		" 				( \n" +		
		"					SELECT hd.NPP_FK,hd.KHACHHANG_FK , (''+ CAST (hd.HOADON_FK AS Varchar(20))) HOADON_FK ,hd.NGAYXUATHD as NGAY,hd.SOHOADON, tongtienAVAT,DienGiai \n"+ 
		"					FROM  \n"+
		"						(	\n"+		
		"							SELECT 	hd.NPP_FK,hd.NPP_DAT_FK KHACHHANG_FK, ('HD DOITAC '+ CAST (hd.PK_SEQ AS Varchar(20))) HOADON_FK  ,('HD '+ CAST(hd.SOHOADON as nvarchar(20))) as SOHOADON,hd.NGAYXUATHD ,hd.NPP_DAT_FK  PK_SEQ, \n"+
		"									hd.tongtienavat, '' as DienGiai \n"+
		"		 					FROM 	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK = npp.PK_SEQ \n"+
		"		 					WHERE 	hd.LOAIXUATHD =0 and hd.TRANGTHAI not in (1,3,5) and hd.NGAYXUATHD >='"+this.tuNgay+"' and hd.NGAYXUATHD <='"+this.denNgay+"' and hd.NPP_DAT_FK is not null \n";
		if(this.nppId.trim().length()>0)
			query+= "									and hd.NPP_FK ='"+this.nppId+"'";		
		if(this.doitacId.trim().length()>0){
			query += "							and hd.NPP_DAT_FK ='"+this.doitacId+"'			\n";
		}		
		query+=
		"			\n"+
		"							UNION ALL \n"+ 
		"  \n"+							
		"							SELECT 	hd.NPP_FK,bt.NPP_DAT_FK KHACHHANG_FK,('HD DOITAC ' + CAST(bt.HOADON_FK AS nvarchar(20))) AS HOADON_FK,(''+ CAST(bt.PK_SEQ as nvarchar(20))) as SOHOADON, bt.NGAYCHUNGTU AS NGAYXUATHD, hd.NPP_DAT_FK PK_SEQ, \n"+
		"									round(bt.GHINO,0) AS tongtienavat, (N'Phiếu bù trừ công nợ HD DOITAC '+ hd.SOHOADON) DienGiai \n"+
		"							FROM    ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK=npp.PK_SEQ \n"+
		"									inner join (  \n"+
		"													SELECT bt.PK_SEQ,bthd.NPP_DAT_FK, bthd.HOADON_FK,bt.NGAYCHUNGTU ,SUM(ROUND(ISNULL(bthd.GHINO,0),0)) GHINO \n"+
		"													FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
		"													WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1  and bthd.NPP_DAT_FK is not null \n";
		if(this.nppId.trim().length()>0)
			 query+= " and bt.NPP_FK = '"+this.nppId+"' \n";
		if(this.doitacId.trim().length()>0){
		query+="					and bthd.NPP_DAT_FK ='"+this.doitacId+"' "; 
		}
		query+=
		"													GROUP BY bt.PK_SEQ,bthd.NPP_DAT_FK, bthd.HOADON_FK,bt.NGAYCHUNGTU   \n"+
		"												)   \n"+
		"												bt on hd.NPP_DAT_FK = bt.NPP_DAT_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
		"							WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIXUATHD = 0 and hd.NGAYXUATHD <= '"+this.denNgay+"' and hd.NGAYXUATHD >= '"+this.tuNgay+"' and bt.GHINO != 0  and hd.NPP_DAT_FK is not null \n";
		if(this.nppId.trim().length()>0)
				query+= "	and hd.NPP_FK = '"+this.nppId+"'  ";
		
		if(this.doitacId.trim().length()>0){
			query += "  					and hd.NPP_DAT_FK ='"+this.doitacId+"'";
		}
		query+=
		"				   							\n"+
		"			     		UNION ALL \n"+
		"			     			\n"+
		"			     			  SELECT	btcn.NPP_FK,btcn_hd.NPP_DAT_FK KHACHHANG_FK ,(N'PKT ' + CAST(btcn.PK_SEQ as nvarchar(20))) as HOADON_FK,''  as SOHOADON,btcn.NGAYCHUNGTU as NGAYXUATHD, btcn_hd.NPP_DAT_FK PK_SEQ, \n"+ 
		"			     						round(sum(isnull(btcn_hd.GHINO,0)),0) as tongtienavat, (N'Phiếu kế toán ' + CAST(btcn.PK_SEQ AS nvarchar(20))) DienGiai \n"+
		"							  FROM 		BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+
		"							  WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '"+this.tuNgay+"' and btcn.NGAYCHUNGTU <= '"+this.denNgay+"' and btcn_hd.NPP_DAT_FK is not null ";
		if(this.nppId.trim().length()>0)
			query+= " 	and btcn.NPP_FK = '"+this.nppId+"' ";	
		if(this.doitacId.trim().length()>0){
			query+="								and btcn_hd.NPP_DAT_FK = '"+this.doitacId+"' \n";
		}
		
		query+=	
		"							  GROUP BY	btcn.NPP_FK,btcn_hd.NPP_DAT_FK,btcn.PK_SEQ, btcn.NGAYCHUNGTU \n"+
		"							  HAVING    round(sum(isnull(btcn_hd.GHINO,0)),0)!=0 \n"+
		"					)hd \n"+
		"				GROUP BY hd.NPP_FK,hd.KHACHHANG_FK,hd.HOADON_FK,hd.NGAYXUATHD ,hd.SOHOADON, hd.tongtienAVAT,hd.DienGiai \n"+  
		"			)	hd \n"+
		"	) 	\n"+
		"	UNION ALL \n"+ 
		"	(	\n"+
		"	 SELECT tt.NGAYCHUNGTU as NGAY, tt.PK_SEQ as ChungTu, DienGiai, '' as PSN, tt.SOTIENTT as PSC, 2 as sott, tt.HOADONNPP_FK \n"+ 
		"	 FROM \n"+
		"		( \n"+
		"		\n"+
		"			SELECT 	tt.NGAYCHUNGTU, CAST(tt.PK_SEQ as nvarchar(20)) as PK_SEQ, ('HD DOITAC '+ CAST (hd.PK_SEQ AS Varchar(20))) as HOADONNPP_FK, hd.NGAYXUATHD, (N'Thu tien DOITAC '+ hd.SOHOADON) DienGiai, round(sum(isnull(tt.sotienTT,0)),0) as SOTIENTT \n"+
		"			FROM 	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK=npp.PK_SEQ \n"+
		"					INNER JOIN (    \n"+
		"									SELECT 	tthd.NPP_FK,tt.NGAYCHUNGTU,tt.PK_SEQ,tthd.NPP_FK NPP_DAT_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+ 									
		"									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK			\n"+
		"									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+this.tuNgay+"' and tt.NGAYCHUNGTU <= '"+this.denNgay+"' and tthd.NPP_FK is not null ";
		if(this.nppId.trim().length()>0)
			query +=  "	and tt.NPP_FK ='"+this.nppId+"'";		
		if(this.doitacId.trim().length()>0){
			query+=" 						and tthd.NPP_FK = '"+this.doitacId+"'  \n";
		}
		query+=
		"									GROUP BY tthd.NPP_FK,tt.NGAYCHUNGTU,tt.PK_SEQ,tthd.NPP_FK , tthd.HOADONNPP_FK  \n"+
		"								) 	  \n"+
		"								tt on hd.NPP_DAT_FK = tt.NPP_DAT_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+ 
		"			WHERE 	1=1 and hd.NPP_DAT_FK is not null ";
		if(this.nppId.trim().length()>0)
			query+= "	hd.NPP_FK ='"+this.nppId+"'  " ;
		if(this.doitacId.trim().length()>0){
			query += "				and hd.NPP_DAT_FK ='"+this.doitacId+"' \n";
		}
		
		query+=
		"			GROUP BY tt.NGAYCHUNGTU,tt.PK_SEQ, hd.SOHOADON, hd.PK_SEQ,hd.NGAYXUATHD \n"+
		"			\n"+
		"			UNION ALL \n"+
		"			\n"+
		"			SELECT 	btcn.NGAYCHUNGTU, (N'Phiếu kế toán ' + CAST(btcn.PK_SEQ as nvarchar(20))) as PK_SEQ,('PKT ' + CAST(btcn.PK_SEQ AS nvarchar(20)))  as HOADONNPP_FK,btcn.NGAYCHUNGTU as NGAYXUATHD, (N'Phiếu kế toán ' + CAST(btcn.PK_SEQ AS nvarchar(20))) DienGiai, \n"+ 
		"					round(sum(isnull(btcn_hd.GHICO,0)),0) as SOTIENTT \n"+
		"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+ 
		"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU <= '"+this.denNgay+"' and btcn.NGAYCHUNGTU >= '"+this.tuNgay+"' and btcn_hd.NPP_DAT_FK is not null ";
		if(this.nppId.trim().length()>0)
			query+=	"and btcn.NPP_FK = '"+this.nppId+"'";		
		
		if(this.doitacId.trim().length()>0){
			query += " 					and btcn_hd.NPP_DAT_FK = '"+this.doitacId+"' \n";
		}
		query+=
		"			GROUP BY btcn.NGAYCHUNGTU, btcn.PK_SEQ \n"+
		"			\n"+
		"			UNION ALL \n"+ 
		"			\n"+
		"			SELECT 	bt.NGAYCHUNGTU, (N'Phiếu kế toán ' + CAST(bt.PK_SEQ as nvarchar(20))) as PK_SEQ,('PKT ' + CAST(bt.PK_SEQ AS nvarchar(20)))  as HOADONNPP_FK,bt.NGAYCHUNGTU as NGAYXUATHD, (N'Phiếu kế toán ' + CAST(bt.PK_SEQ AS nvarchar(20))) DienGiai, \n"+ 
		"					round(sum(isnull(bt.GHICO,0)),0) as SOTIENTT \n"+
		"			FROM 	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK=npp.PK_SEQ \n"+ 
		"					INNER JOIN (   \n"+
		"	  							SELECT bt.NGAYCHUNGTU,bt.PK_SEQ,bthd.NPP_DAT_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+ 
		"	  							FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
		"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1 and bthd.NPP_DAT_FK is not null ";
		if(this.nppId.trim().length()>0)
			query += "	and bt.NPP_FK = '"+this.nppId+"' \n"; 
		
		if(this.doitacId.trim().length()>0){
			query+="				 		and bthd.NPP_DAT_FK ="+this.doitacId;
		}
		query+=
		"	  							GROUP BY  bt.NGAYCHUNGTU,bt.PK_SEQ,bthd.NPP_DAT_FK, bthd.HOADON_FK \n"+
		"	 						   )   \n"+
		"	 							bt on hd.NPP_DAT_FK = bt.NPP_DAT_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
		"			WHERE 	1=1 and hd.NGAYXUATHD <= '"+this.denNgay+"' and hd.NGAYXUATHD >= '"+this.tuNgay+"' and hd.NPP_DAT_FK is not null ";
		if(this.nppId.trim().length()>0)
			query+="	and hd.NPP_FK = '"+this.nppId+"' ";
		
		if(this.doitacId.trim().length()>0){
			query+="			and hd.NPP_DAT_FK ="+this.doitacId+" \n";		
		}
		query+=		 
		"			GROUP BY bt.NGAYCHUNGTU,bt.PK_SEQ,hd.NPP_DAT_FK  \n"+
		"			\n"+
		"			UNION ALL \n"+
		"			\n"+
		"			SELECT 	xnkh.NGAYCHUNGTU, (N'Phiếu xóa nợ ' + CAST(xnkh.PK_SEQ as nvarchar(20))) as PK_SEQ,('HD DOITAC '+ CAST (xnkh_hd.HOADON_FK as nvarchar(20)))  as HOADONNPP_FK,xnkh.NGAYCHUNGTU as NGAYXUATHD, (N'Xóa nợ HD DOI TAC ' +hd.SOHOADON) DienGiai, round(sum(isnull(xnkh_hd.SOTIENXOA,0)),0) as SOTIENTT \n"+ 
		"			FROM	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+
		"				    INNER JOIN ERP_HOADONNPP hd on xnkh_hd.HOADON_FK = hd.PK_SEQ \n"+
		"			WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU <= '"+this.denNgay+"' and XNKH.NGAYCHUNGTU >= '"+this.tuNgay+"' and xnkh_hd.NPP_DAT_FK is not null ";
		if(this.nppId.trim().length()>0)
			query +=  "  and xnkh.NPP_FK = '"+this.nppId+"'";		
		if(this.doitacId.trim().length()>0){
			query+="			and xnkh_hd.NPP_DAT_FK = "+this.doitacId+" \n";
		}
		query+=		 
		"			GROUP BY xnkh.NGAYCHUNGTU,xnkh_hd.HOADON_FK,xnkh.PK_SEQ,hd.SOHOADON	\n"+	
		"			\n"+
		"			UNION ALL \n"+
		"			\n"+
		"			SELECT 	tt.NGAYCHUNGTU as NGAYCHUNGTU, ('PT HDDN '+ CAST (tt.PK_SEQ as nvarchar(20))) as PK_SEQ, ('HD DN '+ CAST (dnkh.PK_SEQ as nvarchar(20)))  as HOADONNPP_FK,	tt.NGAYCHUNGTU as NGAYXUATHD, \n"+ 
		"					N'Thu tien hoa don du no' + CAST(dnkh.PK_SEQ as nvarchar(20)) DIENGIAI, round(SUM(tt.sotienTT),0) as SOTIENTT \n"+ 
		"			FROM   DUNO_KHACHHANG dnkh INNER JOIN NHAPHANPHOI npp on dnkh.NPP_DAT_FK=npp.PK_SEQ \n"+ 
		"	 			   INNER JOIN   \n"+
		"	 				(   \n"+
		"						SELECT	tt.NGAYCHUNGTU,tt.PK_SEQ,tthd.NPP_FK NPP_DAT_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+ 
		"						FROM	ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ=tthd.THUTIENNPP_FK \n"+
		"						WHERE	tt.TRANGTHAI = 1  and tt.NGAYCHUNGTU >='"+this.tuNgay+"' and tt.NGAYCHUNGTU <='"+this.denNgay+"' and tthd.NPP_FK is not null ";
		if(this.nppId.trim().length()>0)
			query+=	"  and tt.NPP_FK = '"+this.nppId+"' ";		
		if(this.doitacId.trim().length()>0){
			query+="		 and tthd.NPP_FK = '"+this.doitacId+"'";
		}
		query+=		
		"						GROUP BY tt.NGAYCHUNGTU,tt.PK_SEQ,tthd.NPP_FK , tthd.HOADONNPP_FK  \n"+
		"		 			)  \n"+
		"		 			tt on dnkh.NPP_DAT_FK = tt.NPP_DAT_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+ 
		"			 WHERE 1=1   and dnkh.NPP_DAT_FK is not null \n";
		if(this.nppId.trim().length()>0)
			query+=	" and dnkh.NPP_FK ='"+this.nppId+"' \n";
		if(this.doitacId.trim().length()>0){
			query+="					and dnkh.NPP_DAT_FK = '"+this.doitacId+"' \n";
		}
		
		query+=
		"			 GROUP BY tt.NGAYCHUNGTU,tt.PK_SEQ, dnkh.PK_SEQ, tt.NGAYCHUNGTU \n"+
		
		" 			UNION ALL \n"+
		
		"			SELECT  tttl.ngaytra NGAYCHUNGTU,(N'Phiếu nhập hàng ' + CAST(tttl.PK_SEQ as nvarchar(20))) as PK_SEQ , (N'Phiếu nhập hàng trả lại '+ CAST (tttl.pk_seq as nvarchar(20))) as HOADONNPP_FK, tttl.ngaytra as NGAYXUATHD,( N'Nhập lại hàng do đối tác trả lại ' + CAST (tttl.pk_seq as nvarchar(20))) DienGiai, \n"+ 
		" 					tttl.TienSauThue as SOTIENTT \n"+ 
		"			FROM  	Erp_HangTraLaiNpp tttl INNER JOIN NHAPHANPHOI npp on tttl.npp_fk = npp.PK_SEQ \n"+ 
		"			WHERE 	1=1 and tttl.TRANGTHAI = 1 AND tttl.NGAYTRA <= '"+this.denNgay+"' AND tttl.NGAYTRA >='"+this.tuNgay+"' and tttl.NppTra_FK is not null ";
		if(this.nppId.trim().length()>0)
			query+= "and tttl.NPP_FK = '"+this.nppId+"' \n";		
		if(this.doitacId.trim().length()>0){
			query+="					and tttl.NppTra_FK ='"+this.doitacId+"' \n";
		}
		
		query+=
		"		 	\n"+
		"	)tt \n"+
		"	) 	ORDER BY HOADONNPP_FK,sott \n";
		
		System.out.println(query);
		return query;
	}

	
	public String getDuNoDauKyDoiTac() {
		
		String querydnhk = 
		"SELECT npp.PK_SEQ, npp.TEN, isnull(npp.MaFAST,'') MAFAST, ISNULL(dndk.tongtienAVAT,0) as dunodauky, ISNULL (dcdk.SOTIENTT,0) as ducodauky \n"+
		"FROM NHAPHANPHOI npp LEFT JOIN \n"+
		"( \n"+
		"	SELECT psn_dk.NPP_DAT_FK , SUM(ISNULL(psn_dk.tongtienAVAT,0)) as tongtienAVAT   \n"+
		"	FROM   \n"+
		"		(	\n"+
		"			SELECT psn.NPP_DAT_FK, sum(tongtienAVAT) as tongtienAVAT \n"+   
		"			FROM \n"+
		"				(  \n"+
		"					SELECT	hd.NPP_DAT_FK , SUM(tongtienavat) tongtienAVAT \n"+   
		"					FROM	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK = npp.PK_SEQ \n"+   
		"					WHERE	1=1 and  hd.LOAIXUATHD = 0 and hd.TRANGTHAI  not in ( 1, 3, 5 ) \n"+
		"							and hd.NGAYXUATHD < '"+this.tuNgay+"'  and hd.NPP_DAT_FK is not null \n";
		if(this.nppId.trim().length()>0)
			querydnhk +=	" and hd.NPP_FK ='"+this.nppId+"'";
		querydnhk+=
		"					GROUP BY hd.NPP_DAT_FK   \n"+
		"				    \n"+
		"					UNION ALL \n"+ 
		"				    \n"+
		"					SELECT  btcn_hd.NPP_DAT_FK , SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
		"					FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+    
		"					WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+this.tuNgay+"' \n"+  
		"							and btcn_hd.NPP_DAT_FK is not null \n";
		if(this.nppId.trim().length()>0)
			querydnhk += "  and btcn.NPP_FK = '"+this.nppId+"'"; 
		querydnhk+=
		"					GROUP BY btcn_hd.NPP_DAT_FK \n"+
		"					\n"+
		"					UNION ALL \n"+	  
		"					\n"+
		"					SELECT 	hd.NPP_DAT_FK, round(bt.tongtienavat,0) tongtienavat   \n"+
		"					FROM    ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK=npp.PK_SEQ   \n"+
		"							inner join (  \n"+
		"											SELECT bthd.NPP_DAT_FK, bthd.HOADON_FK, SUM(ROUND(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
		"											FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
		"											WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1  and bthd.NPP_DAT_FK is not null " ;
		if(this.nppId.trim().length()>0)
			querydnhk += "	and bt.NPP_FK = '"+this.nppId+"' \n";
		querydnhk +=
		"											GROUP BY bthd.NPP_DAT_FK, bthd.HOADON_FK   \n"+
		"										)   \n"+
		"										bt on hd.NPP_DAT_FK = bt.NPP_DAT_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
		"					WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIXUATHD = 0 and hd.NGAYXUATHD <'"+this.tuNgay+"' \n"+ 
		"								and hd.NPP_DAT_FK is not null ";
		if(this.nppId.trim().length()>0)
			querydnhk += "	and hd.NPP_FK = '"+this.nppId+"' \n";
		querydnhk+=
		"				) psn \n"+
		"				GROUP BY psn.NPP_DAT_FK \n"+ 
		"				\n"+
		"				UNION ALL \n"+
		"				\n"+
		"				SELECT 	dnkh.NPP_DAT_FK, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT \n"+   
		"				FROM   	DUNO_KHACHHANG dnkh LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_DAT_FK = npp.PK_SEQ \n"+
		"				WHERE 	1=1 and dnkh.NgayDuNo < '"+this.tuNgay+"' and dnkh.SONO >= 0  ";
		if(this.nppId.trim().length()>0)
				querydnhk += " and  dnkh.NPP_FK='"+this.nppId+"' \n";
		querydnhk+=
		"						and dnkh.NPP_DAT_FK is not null \n"+	
		"				GROUP BY dnkh.NPP_DAT_FK   \n"+
		"	) psn_dk   \n"+
		"	GROUP BY psn_dk.NPP_DAT_FK  \n"+
		")dndk ON npp.PK_SEQ = dndk.NPP_DAT_FK LEFT JOIN \n"+ 
		"( \n"+
		"	SELECT 	psc.NPP_DAT_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT \n"+   
		"	FROM ( \n"+
		"			SELECT 	hd.NPP_DAT_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
		"			FROM 	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK = npp.PK_SEQ \n"+   
		"					INNER JOIN (    \n"+
		"									SELECT tthd.NPP_FK NPP_DAT_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+     								
		"									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK			\n"+							     								
		"									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '"+this.tuNgay+"'  and tthd.NPP_FK is not null ";
		if(this.nppId.trim().length()>0)
				querydnhk += "and tt.NPP_FK ='"+this.nppId+"' \n";
		querydnhk +=
		"  									GROUP BY tthd.NPP_FK, tthd.HOADONNPP_FK   \n"+
		" 								) 	  \n"+
		" 								tt on hd.NPP_DAT_FK = tt.NPP_DAT_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
		"			WHERE 	1=1  and hd.NPP_DAT_FK is not null \n";
		if(this.nppId.trim().length()>0)
				querydnhk += " and hd.NPP_FK = '"+this.nppId+"'";
		querydnhk+=
		"			GROUP BY hd.NPP_DAT_FK   \n"+
		"			\n"+
		"			UNION ALL \n"+
		"			\n"+
		"			SELECT  btcn_hd.NPP_DAT_FK PK_SEQ, SUM(btcn_hd.GHICO) as tongtienAVAT \n"+   
		"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+    
		"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+this.tuNgay+"' \n"+  
		"					and btcn_hd.NPP_DAT_FK is not null \n";
		if(this.nppId.trim().length()>0)
			querydnhk+= "	and btcn.NPP_FK = '"+this.nppId+"'";
		querydnhk+=
		"			GROUP BY btcn_hd.NPP_DAT_FK \n"+
		"			\n"+
		"			UNION ALL \n"+
		"			\n"+
		"			SELECT dnkh.NPP_DAT_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
		"			FROM   DUNO_KHACHHANG dnkh INNER JOIN NHAPHANPHOI npp on dnkh.NPP_DAT_FK = npp.PK_SEQ \n"+
		"	 			   INNER JOIN   \n"+
		"	 				(   \n"+
		"						SELECT	tthd.NPP_FK NPP_DAT_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+     								
		"						FROM	ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK		\n"+								     								
		"						WHERE	tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '"+this.tuNgay+"'  and tthd.NPP_FK is not null ";
		if(this.nppId.trim().length()>0)
			querydnhk+=" 	and tt.NPP_FK ='"+this.nppId+"' \n";
		querydnhk+=
		"						GROUP BY tthd.NPP_FK, tthd.HOADONNPP_FK   \n"+
		"		 			)  \n"+
		"		 			tt on dnkh.NPP_DAT_FK = tt.NPP_DAT_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+   
		"			 WHERE 1=1   and dnkh.NPP_DAT_FK is not null   \n";
		if(this.nppId.trim().length()>0)
			querydnhk+="	and dnkh.NPP_FK ='"+this.nppId+"'";
		querydnhk+=
		"			 GROUP BY dnkh.NPP_DAT_FK   \n"+
		"			\n"+
		"			UNION ALL \n"+	 
		"		  \n"+
		"			SELECT	xnkh_hd.NPP_DAT_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+   
		"			FROM	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
		"			WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+this.tuNgay+"'  and xnkh_hd.NPP_DAT_FK is not null " ;
		if(this.nppId.trim().length()>0)
			querydnhk += "	and xnkh.NPP_FK = '"+this.nppId+"' \n";		  
		querydnhk +=
		"			GROUP BY xnkh_hd.NPP_DAT_FK \n"+
		"			\n"+
		"			UNION ALL \n"+
		"			\n"+
		"			SELECT  hd.NPP_DAT_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
		"  			FROM 	ERP_HOADONNPP hd INNER JOIN NHAPHANPHOI npp on hd.NPP_DAT_FK = npp.PK_SEQ \n"+
		"   					INNER JOIN (   \n"+
		"	  							SELECT bthd.NPP_DAT_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   \n"+
		"	  							FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+   
		"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1 and bthd.NPP_DAT_FK is not null ";
		if(this.nppId.trim().length()>0)
			querydnhk += "and bt.NPP_FK = '"+this.nppId+"' \n";
		querydnhk+=
		"	  							GROUP BY bthd.NPP_DAT_FK, bthd.HOADON_FK   \n"+
		"	 						   )   \n"+
		"	 							bt on hd.KHACHHANG_FK = bt.NPP_DAT_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
		" 			WHERE 	1=1 and hd.NGAYXUATHD < '"+this.tuNgay+"' and hd.NPP_DAT_FK is not null ";
		if(this.nppId.trim().length()>0)
			querydnhk += "and hd.NPP_FK = '"+this.nppId+"' \n";
		
		querydnhk+=		
		" 			GROUP BY hd.NPP_DAT_FK  \n"+
		"					\n "+
		"			UNION ALL \n"+
		"					\n "+
		"			SELECT	tttl.NppTra_FK NPP_DAT_FK, SUM(tttl.TienSauThue) tongtienTT \n"+
		"			FROM	Erp_HangTraLaiNpp tttl INNER JOIN NHAPHANPHOI npp on tttl.NPP_FK = npp.PK_SEQ \n"+
		"			WHERE	1=1 and tttl.TRANGTHAI = 1 AND tttl.NGAYTRA < '"+this.tuNgay+"' ";
		if(this.nppId.trim().length()>0)
			querydnhk += "  AND tttl.NPP_FK ='"+this.nppId+"' \n";
		querydnhk+=
		"					and tttl.NppTra_FK is not null \n"+
		"			GROUP BY tttl.NppTra_FK \n"+
		"	)  psc \n"+
		"	GROUP BY psc.NPP_DAT_FK \n"+ 
		")dcdk ON dcdk.NPP_DAT_FK = npp.PK_SEQ \n"+
		"WHERE 1 = 1 ";
		if(this.nppId.trim().length() > 0)
			querydnhk+= " and npp.TRUCTHUOC_FK='"+this.nppId+"'";
		if(this.doitacId.trim().length()>0){
			querydnhk+= " and npp.PK_SEQ ='"+this.doitacId+"'";
		}
		if(this.nppId.trim().length()<=0){
			querydnhk+="  and npp.iskhachhang = 0	";
		}
		
		System.out.println(querydnhk);
		return querydnhk;
	}
	
	
	public String getDuNoDauKyKH() {
		
		String condition0="";
     	String condition="";
     		if(nppID.trim().length()>0){     			
     			if(KHid.trim().length()>0){
    	    		condition=KHid;	    		
    	    	}
     			else{
     				dbutils db = new dbutils();
     				String pr="";
     				pr ="SELECT PK_SEQ, isnull(maFAST,'')+ ' - '+ TEN as TEN FROM KHACHHANG where 1=1 and npp_fk ='"+ nppID +"' ";
     				ResultSet khrs =db.get(pr);
     				condition0 = "";
     				String s="";
					 if(khrs!=null)
					 {
						 try {
							while(khrs.next())
							 {
								 s+=khrs.getString("PK_SEQ")+",";
							 }
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
						 
						 s = s.substring(0, s.length()-1);
						 
					 }
					 condition0 += s;
     			}     				
     		}
     		
     		String dnkh ="";
     		String thangdn="";
			dbutils db = new dbutils();
			dnkh= "SELECT MONTH('"+tuNgay+"')-1 as Month";	
	    	ResultSet month = db.get(dnkh);
	    	if(month!=null){
	    		try {
					while(month.next()){
						thangdn= month.getString("Month");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    	
		String khdn ="";
    	String nppdn="";
    	String Search_kh_dndk="";
    	String Search_npp_dndk="";
    	String Search_vung_mien="";
    	String ddkd="";
    	
    	if(this.KHid.trim().length()>0)
    	{	
    		khdn ="   and kh.PK_SEQ='"+this.KHid+"'";
    		Search_kh_dndk =  "   and hd.KHACHHANG_FK in ("+this.KHid+") \n";
    	}
    	
    	if(nppId.trim().length()>0)
    	{
    		nppdn=" and kh.NPP_FK='"+nppId+"'";
    		Search_npp_dndk=  "   and hd.NPP_FK ='"+nppId+"' \n";
    	}
    	 
    	if(this.vungId.trim().length()>0)
    		Search_vung_mien+="  and v.PK_SEQ='"+this.vungId+"' \n";
    	if(this.khuvucId.trim().length()>0)
    		Search_vung_mien+="  and kv.PK_SEQ='"+this.khuvucId+"' \n";
    	 
    	if(this.ttId.trim().length()>0)
    	{
    		khdn+=" and kh.npp_fk in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+ttId+"' ) ";
    		Search_npp_dndk+=" and hd.npp_fk in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+ttId+"' ) ";
    	}
    	
    	// CÂU LẤY DƯ NỢ ĐẦU KỲ
 		String queryddk = "" +
 		"	SELECT	kh.maFAST, kh.TEN, isnull(psn_dk.tongtienAVAT, 0) as dunodauky, isnull(psc_dk.SOTIENTT, 0) as ducodauky \n"+ 
 		"	FROM 	KHACHHANG kh \n"+ 
 		"	 		LEFT JOIN \n"+ 
 		"	 				( \n"+ 
 		"						SELECT psn_dk.PK_SEQ KHACHHANG_FK, SUM(ISNULL(psn_dk.tongtienAVAT,0)) as tongtienAVAT \n"+ 
 		"						FROM \n"+ 
 		"							(	\n " +
 		"								SELECT  hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) as tongtienAVAT \n"+
		"								FROM 	HOADON hd \n"+
		"										LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
		"									 	LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
		"										LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
		"								WHERE 	hd.LOAIHOADON=0 and hd.TRANGTHAI in (2,4) and hd.NGAYXUATHD < '"+tuNgay+"' \n" +Search_npp_dndk+Search_kh_dndk+Search_vung_mien +
		"                               GROUP BY hd.KHACHHANG_FK \n "+
 		"\n 					UNION ALL \n"+	
 		
 		"	 					SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+
 		"	  					FROM  	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+ 
 		"	   							INNER JOIN ( \n"+ 
 		"		  										SELECT 	bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+ 
 		"		  										FROM   	BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+ 
 		"		  										WHERE  	bt.TRANGTHAI = 1 and bt.LOAIBUTRU=0 \n"+ 
 		"		  										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+ 
 		"		 									) \n"+     		
 		"		 									 bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+      		
 		"								LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
		"								LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
		"								LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"	 					WHERE 	1=1 and hd.TRANGTHAI in (2,4) and hd.LOAIHOADON =0 and hd.NGAYXUATHD <'"+tuNgay+"'" + Search_kh_dndk+Search_npp_dndk+Search_vung_mien+	
		
		
 		" \n 					UNION ALL \n"+	
 		
 		"	 					SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+
 		"	  					FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+ 
 		"	   							INNER JOIN ( \n"+ 
 		"		  										SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+ 
 		"		  										FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+ 
 		"		  										WHERE  bt.TRANGTHAI = 1 and bt.LOAIBUTRU=0 \n"+
 		"		 									 	GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+
 		"		 									) \n"+
 		"		 									bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
 		"								LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
		"								LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
		"								LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"	 					WHERE 	1=1 and hd.TRANGTHAI in (2,4) and hd.NGAYXUATHD <'"+tuNgay+"' " + Search_kh_dndk+Search_npp_dndk+Search_vung_mien;	     		
 		queryddk+=
 		"\n	 					UNION ALL \n"+    			 
 			 
 		"						SELECT kh.PK_SEQ, sum(round((hdETC.AVAT - hdETC.AVAT_CK),0)) as tongtienavat \n"+
 		"						FROM \n"+
 		"							( \n"+
 		"								SELECT  ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK,ETC.ddkd_fk,ETC.PK_SEQ as HOADONNPP_FK,npp_fk,\n"+
 		"										sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,  \n  "+ 
		"										sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n "+ 
		"										sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT, \n"+ 
		"										round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,        \n   "+ 
		"										sum(isnull(thuexuat,0)) as BVAT_CK      \n  "+ 
		"								FROM 	(   \n "+ 
		"							 				SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau,c.vat as thuexuat, \n"+ 
		"													(   \n"+ 
		"														SELECT top(1) bb.DDKD_FK   \n "+ 
		"														FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK  \n   "+ 
		"														WHERE  aa.HOADONNPP_FK=c.HOADON_FK  \n  "+ 
		"													) as ddkd_fk , \n "+ 
		"													case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,  \n "+ 
		"													case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia \n "+ 
		"											FROM 	ERP_HOADONNPP a  \n "+ 
		"													INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   \n "+ 
		"													INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq  \n "+ 
		"													INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  \n "+ 
		"											WHERE 	1=1 and c.SOLUONG > 0 and a.trangthai in ( 2 , 4 ) \n "+ 		 
 		"													and a.NgayXuatHD <'"+tuNgay+ "') ETC \n"+		
 		"											GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK,ETC.ddkd_fk,ETC.PK_SEQ,npp_fk \n"+ 
 		"										  )as hdETC \n"+
 		"											  INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ=hdETC.HOADONNPP_FK\n"+
 		"											  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=hdETC.DDKD_FK \n"+
 		"											  INNER JOIN KHACHHANG kh on kh.PK_SEQ=hdETC.KHACHHANG_FK \n"+
 		"											  INNER JOIN NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK \n"+
		"											  LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
		"											  LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"								WHERE 1=1 " +Search_kh_dndk+Search_npp_dndk+Search_vung_mien+
 		"								GROUP BY kh.PK_SEQ \n"+
 		" union all \n"+
 		" SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+ 
 		"								FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+
 		"										LEFT JOIN NHAPHANPHOI npp on btcn.NPP_FK = npp.PK_SEQ \n"+ 
 		"									 	LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+ 
 		"										LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+ 
 		"								WHERE 	btcn.LoaiBuTru=1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"' \n"; 
     	if(this.nppId.trim().length()>0)
			queryddk+=" 							and btcn.NPP_FK='"+nppId+"' \n";
		if(this.KHid.trim().length()>0)
				queryddk+=" 							and btcn_hd.KHACHHANG_FK='"+this.KHid+"' \n";
		if(ttId.trim().length()>0)
    	{
			queryddk+="  and btcn.npp_fk in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+ttId+"' ) ";
    	}
	queryddk+="                              GROUP BY btcn_hd.KHACHHANG_FK \n"+ 
 		                               
 		"\n						UNION ALL \n"+
 		
 		"						SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT \n"+
 		"						FROM 	DUNO_KHACHHANG dnkh \n"+
 		"								LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ \n"+ 
		"								LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
		"								LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"						WHERE 	1=1 and dnkh.NgayDuNo < '"+tuNgay+"'	and dnkh.SONO >=0 \n"+ Search_vung_mien;
					     		if(this.nppId.trim().length()>0)
										queryddk+=" 				and dnkh.NPP_FK='"+nppId+"' \n";
								if(this.KHid.trim().length()>0)
										queryddk+=" 				and dnkh.KHACHHANG_FK='"+this.KHid+"' \n";
								if(ttId.trim().length()>0)
						    	{
									queryddk+="  and dnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
						    	}
		queryddk+=
 		"						GROUP BY dnkh.KHACHHANG_FK \n"+ 
 		" \n"+
    	"						UNION ALL \n"+
    	"   					SELECT a.khachhang_fk PK_SEQ, sum(round(isnull(a.avat,0),0)) as tongtienAVAT   \n"+
    	"   					FROM ERP_HoaDonPheLieu a \n"+
    	"   					WHERE 1 = 1 AND trangthai = 1 AND a.ngayhoadon < '"+this.tuNgay+"' ";
    							if(this.nppId.trim().length()>0)
    								queryddk+=	" and a.npp_fk = '"+this.nppId+"' \n";
    							if(this.KHid.trim().length()>0)
									queryddk+=" 				and a.KHACHHANG_FK='"+this.KHid+"' \n";
    							if(ttId.trim().length()>0)
    							{
    								queryddk+="  and a.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
    							}
    	queryddk+=
    	"   					GROUP BY a.khachhang_fk \n"+ 
    	
 		"				) psn_dk \n"+
 		"			GROUP BY psn_dk.PK_SEQ \n"+
 				
 		"	 ) psn_dk on kh.PK_SEQ = psn_dk.KHACHHANG_FK\n"+
 			 
 		"	 LEFT JOIN \n"+
 		"	 ( \n"+
 		"	 	SELECT psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT \n"+
 		"		FROM \n"+
 		"			 ( \n"+
 		"			  	SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+
 		"			  	FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
 		"			  			INNER JOIN ( \n"+
 		"				 					 SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+
 		"				  					 FROM   ERP_THUTIENNPP tt INNER JOIN ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK \n"+
 		"				  					 WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU <'"+tuNgay+"' and tthd.LOAIHD = 0 \n"+ 
 		"				  					 GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+
 		"				 					) \n"+
 		"				 						tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+
 		"								LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
		"								LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
		"								LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"			 	WHERE 	1=1 \n" +Search_kh_dndk+Search_npp_dndk+Search_vung_mien+
 		"			  	GROUP BY hd.KHACHHANG_FK \n"+
 		
 		"\n			 	UNION ALL \n"+ 
 		
 		"				SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull((-1)*dnkh.SONO,0),0)) as SOTIENTT \n"+
 		"				FROM 	DUNO_KHACHHANG dnkh \n"+
 		"						LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ \n"+ 
		"						LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
		"						LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"				WHERE 	1=1 and dnkh.NgayDuNo < '"+thangdn+"'	and dnkh.SONO <0 \n"+Search_vung_mien;
				     	if(this.nppId.trim().length()>0)
								queryddk+=" 							and dnkh.NPP_FK='"+nppId+"' \n";	
						if(this.KHid.trim().length()>0)
								queryddk+=" 							and dnkh.KHACHHANG_FK='"+this.KHid+"' \n";
						if(ttId.trim().length()>0)
				    	{
							queryddk+="  and dnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
				    	}
		queryddk+=
 		"				GROUP BY dnkh.KHACHHANG_FK \n"+ 
 		
 		"\n				UNION ALL \n"+
 		
 		"			 	SELECT dnkh.KHACHHANG_FK, round(SUM(isnull(tt.sotienTT,0)),0) as SOTIENTT \n"+
 		"			 	FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG k on dnkh.KHACHHANG_FK=k.PK_SEQ \n"+
 		"			 			INNER JOIN \n"+
 		"			 			( \n"+
 		"							SELECT 	tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+
 		"							FROM 	ERP_THUTIENNPP tt INNER JOIN ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK \n"+
 		"							WHERE 	tt.TRANGTHAI=1 and tt.NGAYCHUNGTU <'"+tuNgay+"' and tthd.LOAIHD = 2 \n"+
 		"							GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+
 		"				 		)tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+
 		"						LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ \n"+ 
		"						LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
		"						LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"			 	WHERE 1=1 "+Search_vung_mien;
			     		if(this.nppId.trim().length()>0)
							queryddk+=" 						and dnkh.NPP_FK='"+nppId+"' \n";	
			     		if(this.KHid.trim().length()>0)
							queryddk+=" 						and dnkh.KHACHHANG_FK='"+this.KHid+"' \n";
			     		if(this.ttId.trim().length()>0)
				    	{
							queryddk+="  and dnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
				    	}
	    queryddk +=
 		"				GROUP BY dnkh.KHACHHANG_FK \n"+
 		
 		"\n				UNION ALL	\n"+
 		
 		"	 			SELECT tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT \n" +
 		"				FROM   Erp_HangTraLaiNpp  tttl " +
	    "					   LEFT JOIN NHAPHANPHOI npp on tttl.NPP_FK = npp.PK_SEQ \n"+ 
		"					   LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
		"					   LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"				WHERE  tttl.trangthai=1 and tttl.NGAYTRA <'"+tuNgay+"' and tttl.TRANGTHAI=1 \n" +Search_vung_mien;
			     		if(this.KHid.trim().length()>0)
				    		queryddk+=" 						and tttl.KHACHHANG_FK='"+this.KHid+"'";
				    	if(this.nppId.trim().length()>0)
				    		queryddk+=" 						and tttl.NPP_FK='"+this.nppId+"'";
				    	if(this.ttId.trim().length()>0)
				    	{
							queryddk+="  and tttl.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
				    	}
    	
    	queryddk+=
 		"				GROUP BY tttl.KHACHHANG_FK \n"+     
 		
 		"\n				UNION ALL	\n"+
 		
 		"			  	SELECT hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+
 		"			  	FROM   ERP_HOADONNPP hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK = k.PK_SEQ \n"+
 		"			  		   INNER JOIN ( \n"+
 		"				 						SELECT 	tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+
 		"				 						FROM 	ERP_THUTIENNPP tt INNER JOIN ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK \n"+
 		"				 						WHERE 	tt.TRANGTHAI=1 and tt.NGAYCHUNGTU <'"+tuNgay+"' and tthd.LOAIHD = 0 \n"+ 
 		"				 						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+
 		"				  					) \n"+
 		"				  					tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+
 		"					   LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
			"					   LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
			"					   LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"			 	WHERE 1=1 "+ Search_kh_dndk+Search_npp_dndk+ Search_vung_mien+
 		"			  	GROUP BY  hd.KHACHHANG_FK \n"+
 		
 		"\n				UNION ALL \n"+
 		
 		"				SELECT 	xnkh_hd.KHACHHANG_FK, sum(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+
 		"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+
 		"											INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0) \n"+
 		"					   	LEFT JOIN NHAPHANPHOI npp on xnkh.NPP_FK = npp.PK_SEQ \n"+ 
			"					   	LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
			"					   	LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and xnkh.NGAYCHUNGTU <'"+tuNgay+"' \n"+ Search_vung_mien;
 						if(this.nppId.trim().length()>0)
 								queryddk+=" 			and xnkh.NPP_FK='"+nppId+"' \n";	
    					if(this.KHid.trim().length()>0)
    							queryddk+=" 			and xnkh_hd.KHACHHANG_FK='"+this.KHid+"' \n";
    					if(ttId.trim().length()>0)
				    	{
							queryddk+="  and xnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
				    	}
    	queryddk+=
 		"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+
 		
 		"\n				UNION ALL \n"+
 		
 		"				SELECT 	xnkh_hd.KHACHHANG_FK, sum(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+
 		"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+
 		"											INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+
 		"					   	LEFT JOIN NHAPHANPHOI npp on xnkh.NPP_FK = npp.PK_SEQ \n"+ 
			"					   	LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
			"					   	LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and xnkh.NGAYCHUNGTU <'"+tuNgay+"' \n"+ Search_vung_mien;
 						if(this.nppId.trim().length()>0)
 								queryddk+=" 			and xnkh.NPP_FK='"+nppId+"' \n";	
    					if(this.KHid.trim().length()>0)
    							queryddk+=" 			and xnkh_hd.KHACHHANG_FK='"+this.KHid+"' \n";
    					if(this.ttId.trim().length()>0)
				    	{
							queryddk+="  and xnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
				    	}
    	queryddk+=
 		"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+
 		
 		"				UNION ALL \n"+
 		"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+
 		"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+
 		"											INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0) \n"+
 		"					   	LEFT JOIN NHAPHANPHOI npp on xnkh.NPP_FK = npp.PK_SEQ \n"+ 
			"					   	LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
			"					   	LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and xnkh.NGAYCHUNGTU <'"+tuNgay+"' \n"+Search_vung_mien;
 						if(nppId.trim().length()>0)
 								queryddk+=" 				and xnkh.NPP_FK='"+nppId+"' \n";	
    					if(this.KHid.trim().length()>0)
    							queryddk+=" 				and xnkh_hd.KHACHHANG_FK='"+this.KHid+"' \n";
    					if(this.ttId.trim().length()>0)
				    	{
							queryddk+="  and xnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+ttId+"' ) ";
				    	}
    	queryddk+=
 		"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+
 		
 		"				UNION ALL \n"+
 		"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+
 		"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+
 		"											INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1) \n"+
 		"					   	LEFT JOIN NHAPHANPHOI npp on xnkh.NPP_FK = npp.PK_SEQ \n"+ 
		"					   	LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
		"					   	LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and xnkh.NGAYCHUNGTU <'"+tuNgay+"' \n"+Search_vung_mien;
 						if(nppId.trim().length()>0)
 								queryddk+=" 				and xnkh.NPP_FK='"+nppId+"' \n";	
    					if(this.KHid.trim().length()>0)
    							queryddk+=" 				and xnkh_hd.KHACHHANG_FK='"+this.KHid+"' \n";
    					if(this.ttId.trim().length()>0)
				    	{
							queryddk+="  and xnkh.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+ttId+"' ) ";
				    	}
    	queryddk+=
 		"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+
 		
 		"               UNION ALL \n"+
 		"		 		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+
 		"		  		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
 		"		   				INNER JOIN ( \n"+
 		"			  							SELECT 	bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+
 		"			  							FROM 	BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
 		"			  							WHERE 	bt.TRANGTHAI = 1 and bt.LOAIBUTRU=0 \n"+
 		"			  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+
 		"			 						) \n"+
 		"			 						 bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+
 		"					   	LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
			"					   	LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
			"					   	LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"		 		WHERE 	1=1 and hd.NGAYXUATHD <'"+tuNgay+"' \n"+Search_vung_mien;
 							if(nppId.trim().length()>0)
 									queryddk+=" 				and hd.NPP_FK='"+nppId+"' \n";
    						if(this.KHid.trim().length()>0)
    								queryddk+=" 				and hd.KHACHHANG_FK='"+this.KHid+"' \n";
    						if(this.ttId.trim().length()>0)
					    	{
								queryddk+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
					    	}
    	queryddk+=
 		"		 		GROUP BY hd.KHACHHANG_FK \n"+
 		
 		
 		"				UNION ALL \n"+
 		"				SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+
 		"		  		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
 		"		   				INNER JOIN ( \n"+
 		"			  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+ 
 		"			  							FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+ 
 		"			  							WHERE  bt.TRANGTHAI = 1  and bt.LOAIBUTRU=0 \n"+
 		"			  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+ 
 		"			 						) \n"+
 		"			 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+ 
 		"					   	LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
			"					   	LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
			"					   	LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"		 		WHERE 	1=1 and hd.NGAYXUATHD <'"+tuNgay+"' \n"+Search_vung_mien;
 							if(this.nppId.trim().length()>0)
 									queryddk+=" 				and hd.NPP_FK='"+nppId+"' \n";	
    						if(this.KHid.trim().length()>0)
    								queryddk+=" 				and hd.KHACHHANG_FK='"+this.KHid+"' \n";
    						if(this.ttId.trim().length()>0)
					    	{
								queryddk+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
					    	}
    	queryddk+=
 		"		 		GROUP BY hd.KHACHHANG_FK \n"+
 		
 		"  				UNION ALL \n"+
		" 				SELECT 	ttna.KHACHHANG_FK, SUM(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT \n"+
		"   			FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ \n"+
		"					   	LEFT JOIN NHAPHANPHOI npp on ttna.NPP_FK = npp.PK_SEQ \n"+ 
			"					   	LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
			"					   	LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
		"   			WHERE 	1 =1 and tt.NGAYCHUNGTU <='"+tuNgay+"' \n"+Search_vung_mien;	
						if(nppId.trim().length()>0)
								queryddk+=" 					and ttna.NPP_FK='"+nppId+"' \n";	
    					if(this.KHid.trim().length()>0)
    							queryddk+=" 					and ttna.KHACHHANG_FK='"+this.KHid+"' \n";
    					if(this.ttId.trim().length()>0)
				    	{
							queryddk+="  and ttna.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
				    	}
    	queryddk+=
		"   			GROUP BY ttna.KHACHHANG_FK \n"+
	  	" UNION ALL \n"+
	  	" 				SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as SOTIENTT \n"+ 
	  	"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+
	  	"						LEFT JOIN NHAPHANPHOI npp on btcn.NPP_FK = npp.PK_SEQ \n"+ 
	  	"						LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+ 
	  	"						LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+ 
	  	"				WHERE 	btcn.LoaiBuTru=1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"' \n"+ Search_vung_mien;
    	if(nppId.trim().length()>0)
			queryddk+=" 					and btcn.NPP_FK='"+nppId+"' \n";
		if(this.KHid.trim().length()>0)
				queryddk+=" 					and btcn_hd.KHACHHANG_FK='"+this.KHid+"' \n";
		if(this.ttId.trim().length()>0)
    	{
			queryddk+="  and btcn.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
    	}
	queryddk+=
	  	"                               GROUP BY btcn_hd.KHACHHANG_FK \n"+
		"				UNION ALL \n"+		
		"				SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT \n"+ 
		"		  		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+ 
		"		   				INNER JOIN ( \n"+
		"			  							SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+ 
		"			  							FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+
		"			  							WHERE  ct.TRANGTHAI = 1 \n"+
		"			  							GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK \n"+ 
		"			 						) \n"+
		"			 						ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+
		"					   	LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+ 
			"					   	LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
			"					   	LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
		"		 		WHERE 	1=1 and hd.NGAYXUATHD <'"+tuNgay+"' \n"+Search_vung_mien;
		if(this.nppId.trim().length()>0)
					queryddk+=" 			and hd.NPP_FK='"+this.nppId+"' \n";	
		if(this.KHid.trim().length()>0)
				queryddk+=" 				and hd.KHACHHANG_FK='"+this.KHid+"' \n";
		if(ttId.trim().length()>0)
    	{
			queryddk+="  and hd.NPP_FK in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+ttId+"' ) ";
    	}
		queryddk+=
		"		 		GROUP BY hd.KHACHHANG_FK \n"+
 		"			 ) \n"+
 		"			 psc " +
 		"		GROUP BY psc.KHACHHANG_FK \n"+  
 		"	 ) psc_dk on kh.PK_SEQ = psc_dk.KHACHHANG_FK \n"+
 		"  LEFT JOIN NHAPHANPHOI npp on npp.PK_SEQ = kh.NPP_FK \n"+
 		" LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+
 		" LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+
 		"	 WHERE 1=1 ";
 		if(this.KHid.trim().length() >0){
     		queryddk += " 					and kh.PK_SEQ in ("+this.KHid+") \n";
		}
		if(this.nppId.trim().length()>0)
		{
			queryddk+= " 					and kh.NPP_FK ='"+this.nppId+"' \n";
		}
		if(this.vungId.trim().length()>0)
		{
			queryddk += " 					and v.PK_SEQ in ("+this.vungId+") \n";
		}
		if(this.khuvucId.trim().length()>0)
		{
			queryddk += " 					and kv.PK_SEQ in ("+this.khuvucId+") \n";
		}
		if(this.ttId.trim().length()>0)
    	{
			queryddk+="  and npp.PK_SEQ in (select pk_seq from nhaphanphoi  where tinhthanh_fk='"+this.ttId+"' ) ";
    	}
		queryddk+=
 		"	 ORDER BY kh.PK_SEQ \n";	
 		
     	System.out.println("DNDK: "+ queryddk);
 		// HẾT CÂU LẤY DƯ NỢ ĐẦU KỲ
     	
		return queryddk;
	}

	
	public void settype(String type) {
		
		this.type = type;
	}

	
	public String gettype() {
		
		return this.type;
	}

	
	public ResultSet getDoiTacRs() {
		
		return this.doitacRs;
	}

	
	public void setDoiTacRs(ResultSet DoiTacRs) {
		
		this.doitacRs = DoiTacRs;
	}

	
	public String getDoiTacId() {
		
		return this.doitacId;
	}

	
	public void setDoiTacId(String doitacId) {
		
		this.doitacId = doitacId;
	}

	
	 

}

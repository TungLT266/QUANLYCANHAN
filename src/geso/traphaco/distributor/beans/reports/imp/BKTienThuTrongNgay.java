package geso.traphaco.distributor.beans.reports.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.net.nl.NVNavigator;

import geso.traphaco.center.util.Utility;

import geso.traphaco.distributor.beans.reports.IBKTienThuTrongNgay;
import geso.traphaco.distributor.db.sql.dbutils;

public class BKTienThuTrongNgay implements IBKTienThuTrongNgay, Serializable {

	private String tuNgay;
	private String denNgay;
	private String userId;
	private ResultSet rsBKTienThuTrongNgay;
	private ResultSet rsBKTienThuTrongNgay_KT;
	private String nppID;
	private String ngayKS;
	String nvbhIds, nvgnIds, khIds, phieunoptienIds;
	ResultSet nvgnRs, nvbhRs, khRs, phieunoptienRs;
	
	dbutils db;
	
	public BKTienThuTrongNgay()
	{
		this.tuNgay = "";
		this.denNgay = "";
		this.userId = "";
		this.nppID = "";
		this.nvbhIds = "";
		this.nvgnIds = "";
		this.phieunoptienIds="";
		this.khIds = "";
		
		db = new dbutils();
		
	}

	
	
	public void init() 
	{
		Utility ut = new Utility();
		this.nppID = ut.getIdNhapp(userId);
		String sql="";
		String pr="";
		String pr1="";
		
		if(this.tuNgay.trim().length() > 0)
		{
			pr += " AND TT.NGAYCHUNGTU >= '"+ this.tuNgay +"' ";
		}
		if(this.denNgay.trim().length() > 0)
		{
			pr += " AND TT.NGAYCHUNGTU <= '"+ this.denNgay +"' ";
		}
		if(this.nvbhIds.trim().length() > 0)
		{
			pr +=  " AND ( TT.PK_SEQ in (SELECT THUTIENNPP_FK from ERP_THUTIENNPP_HOADON where KHACHHANG_FK in \n" +
			"    							(\n"+
			"                                  SELECT c.KHACHHANG_FK \n"+
			"                                  FROM   DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+
			"                                            	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
			"                                  WHERE  a.PK_SEQ in ("+this.nvbhIds+") \n"+
			"                                ) \n " +
			"							 ) \n" +
			"			 )\n";
			 		/*"                           ( SELECT C.PK_SEQ \n" +
			 		"                             FROM ERP_DONDATHANGNPP A INNER JOIN ERP_HOADONNPP_DDH B ON A.PK_SEQ = B.DDH_FK \n" +
			 		"                                                      INNER JOIN ERP_HOADONNPP C ON B.HOADONNPP_FK= C.PK_SEQ \n" +
			 		"                             WHERE C.TRANGTHAI in (2,4) AND A.DDKD_FK IN (" + this.nvbhIds + ") ) ) \n" +
			 		" OR TT.PK_SEQ in (select THUTIENNPP_FK from ERP_THUTIENNPP_HOADON where HOADONNPP_FK in \n" +
			 		"                           ( SELECT C.PK_SEQ \n" +
					"                      		  FROM DONHANG A INNER JOIN HOADON_DDH B ON A.PK_SEQ = B.DDH_FK \n" +
					"                                            INNER JOIN HOADON C ON B.HOADON_FK= C.PK_SEQ \n" +
					"                             WHERE C.TRANGTHAI in (2,4) AND A.DDKD_FK IN (" + this.nvbhIds + ") ))  ) \n";*/
			 
		}
		if(this.nvgnIds.trim().length() > 0)
		{
			pr += 
				 "  AND TT.PK_SEQ  in ( \n" +
				 "						SELECT THUTIENNPP_FK FROM ERP_THUTIENNPP_HOADON where KHACHHANG_FK in \n"+
				 "							   ( \n " +
				 "								  SELECT KHACHHANG_FK FROM NVGN_KH where nvgn_fk in ("+nvgnIds+") " +
				 "								) \n" +
				 "					  ) \n";
				 /*"           (SELECT D.PK_SEQ     \n" +                 
                 "             FROM  PHIEUXUATKHO K INNER JOIN PHIEUXUATKHO_DONHANG A ON K.PK_SEQ= A.PXK_FK   \n" +                         
                 "                   INNER JOIN HOADON_DDH B ON A.DONHANG_FK = B.DDH_FK             \n" +              
                 "                   INNER JOIN HOADON C ON B.HOADON_FK= C.PK_SEQ               \n" +
                 "					 INNER JOIN ERP_THUTIENNPP_HOADON D ON C.PK_SEQ = D.HOADON_FK \n" +       
                 "             WHERE C.TRANGTHAI in (2,4) AND K.NVGN_FK IN ("+ this.nvgnIds +") ) \n"; */
			
		}
		if(this.khIds.trim().length() > 0)
		{
			pr += " AND TT.PK_SEQ in ( select THUTIENNPP_FK from ERP_THUTIENNPP_HOADON where KHACHHANG_FK in ("+ this.khIds +") ) \n";
		}
		if(this.phieunoptienIds.length() > 0)
		{
			pr1 += " AND NT.PK_SEQ in ("+ this.phieunoptienIds +") \n";
		}
		
		
		/*sql = " SELECT CASE WHEN NOPTIENIDS is not null and NOPTIENIDS != '' THEN NOPTIENIDS "+
			  "             ELSE TT.PK_SEQ END as ID," +
			  "        TT.NGAYCHUNGTU, ISNULL(TT.HINHTHUCTT,N'Tiền mặt') as HINHTHUCTT  , TT.SOTIENTHU, " +
			  "        CASE WHEN TENNGUOINOP is not null and TENNGUOINOP != '' THEN isnull(tennguoinop,'')  "+ 
			  "             ELSE (Select distinct KH.TEN  + ',' AS [text()]  "+
			  "                   From ERP_THUTIENNPP_HOADON K1 inner join KHACHHANG KH on K1.KHACHHANG_FK = Kh.PK_SEQ "+
			  "                   Where K1.THUTIENNPP_FK = TT.PK_SEQ  "+
			  "                   For XML PATH ('') )  " +
			  "             END  as nguoinoptien ," +
			  "         CASE WHEN NOPTIENIDS is not null and NOPTIENIDS != ''  THEN  ISNULL((select SUM(SOTIEN) " +
			  "                                                        from ERP_THUTIENNPP_NOPTIEN a inner join NOPTIEN b on a.NOPTIEN_FK = b.PK_SEQ" +
			  "														   where THUTIENNPP_FK = TT.PK_SEQ	),0) " +
			  "              ELSE TT.SOTIENTHU  END as SOTIENTT," +
			  "         CASE WHEN NOPTIENIDS is not null and NOPTIENIDS != '' THEN 1 "+
			  "             ELSE 0 END as ISNOPTIEN " +
			  " FROM ERP_THUTIENNPP TT    "+																				
			  " WHERE TRANGTHAI !=2 and TT.NPP_FK = '"+ this.nppID +"' and TT.SOTIENTHU > 0   ";*/
		
		sql = 
			  " SELECT DISTINCT NT.PK_SEQ AS ID, NT.NGAYNOP AS NGAYCT,  \n"+
		      "        CASE WHEN NT.NVGN_FK IS NOT NULL THEN (SELECT TEN FROM NHANVIENGIAONHAN WHERE PK_SEQ= NT.NVGN_FK) \n"+
		      "        WHEN NT.NVBH_FK IS NOT NULL THEN (SELECT TEN FROM DAIDIENKINHDOANH WHERE PK_SEQ= NT.NVBH_FK)  \n"+
		      "        ELSE ( SELECT TEN FROM KHACHHANG WHERE PK_SEQ= NT.KHACHHANG_FK) END NGUOINOPTIEN," +
		      "        				 NT.SOTIEN AS SOTIENNOP,  \n"+
		      " 	  				( \n" +
		      "							SELECT SUM(A.SOTIENDATT)  \n"+
		      "  	  	 			 	FROM   ERP_THUTIENNPP_NOPTIEN A INNER JOIN ERP_THUTIENNPP B ON A.THUTIENNPP_FK = B.PK_SEQ \n"+
		      "  	   					WHERE  A.NOPTIEN_FK = NT.PK_SEQ AND B.TRANGTHAI != 2 ) AS SOTIENTT, \n"+
		      "        						   NT.SOTIEN - (SELECT SUM(A.SOTIENDATT)  \n"+
			  "				        						FROM   ERP_THUTIENNPP_NOPTIEN A INNER JOIN ERP_THUTIENNPP B ON A.THUTIENNPP_FK = B.PK_SEQ \n"+
			  "				        						WHERE A.NOPTIEN_FK = NT.PK_SEQ AND B.TRANGTHAI != 2 ) AS SOTIENDU, \n"+
			  "	      										(SELECT distinct B.HINHTHUCTT  + ',' AS [text()]  \n"+
			  "	       										 FROM ERP_THUTIENNPP_NOPTIEN A inner join ERP_THUTIENNPP B on A.THUTIENNPP_FK = B.PK_SEQ \n"+
			  "	       										 WHERE A.NOPTIEN_FK = NT.PK_SEQ AND B.TRANGTHAI != 2  \n"+
			  "	       										 For XML PATH ('') )  AS HINHTHUCTT, 0 as ISTHUTT	\n"+ 		 
		      " 											 FROM NOPTIEN NT  \n"+
			  " 											 WHERE NT.PK_SEQ IN (SELECT  TTNT.NOPTIEN_FK \n"+
		      "                    												 FROM ERP_THUTIENNPP TT INNER JOIN ERP_THUTIENNPP_NOPTIEN  TTNT ON TT.PK_SEQ = TTNT.THUTIENNPP_FK \n"+
		      "                     											 WHERE TT.TRANGTHAI != 2 AND NT.NPP_FK = '"+ this.nppID +"' " +
		      "                           "+ pr +" " +
		      "                     ) " +
		      "       AND NT.TRANGTHAI != 2  \n"+
		      "       AND NT.NPP_FK = '"+ this.nppID +"' "+ pr1 +"  "; 		
		
		sql += 
			  "	UNION ALL \n"+
			  " SELECT DISTINCT TT.PK_SEQ AS ID, TT.NGAYCHUNGTU AS NGAYCT,  \n"+
	          "       (SELECT distinct KH.TEN  + ',' AS [text()]   \n"+
			  "	       From ERP_THUTIENNPP_HOADON K1 inner join KHACHHANG KH on K1.KHACHHANG_FK = Kh.PK_SEQ  \n"+
			  "	       Where K1.THUTIENNPP_FK = TT.PK_SEQ   \n"+
			  "	       For XML PATH ('') ) AS NGUOINOPTIEN ,  \n"+
			  "       TT.SOTIENTHU AS SOTIENNOP, TT.SOTIENTHU AS SOTIENTT, 0 AS SOTIENDU, TT.HINHTHUCTT , 1 as ISTHUTT \n"+
			  " FROM ERP_THUTIENNPP TT INNER JOIN ERP_THUTIENNPP_HOADON TTHD ON TT.PK_SEQ = TTHD.THUTIENNPP_FK  \n"+
		  	  " WHERE TT.TRANGTHAI != 2 AND (TT.NOPTIENIDS IS NULL OR TT.NOPTIENIDS = '' )  \n"+
			  "       AND TT.NPP_FK = '"+ this.nppID +"'" +
			  "        "+ pr +" \n";
		   
				
		sql += " ORDER BY NGAYCT "; 
		
		dbutils db = new dbutils();
		System.out.println("Tien Thu Trong Ngay  :"+sql);
		this.rsBKTienThuTrongNgay = db.get(sql);
		
		this.rsBKTienThuTrongNgay_KT = db.get(sql);
	}
	
	

	
	public void DBclose() {
		
		
		try {
			if(rsBKTienThuTrongNgay != null)
				rsBKTienThuTrongNgay.close();
			if(rsBKTienThuTrongNgay_KT != null)
				rsBKTienThuTrongNgay_KT.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	
	public void setTuNgay(String tuNgay) {
		
		this.tuNgay = tuNgay;
		
	}

	
	public String getTuNgay() {
		
		return this.tuNgay;
	}

	
	public void setDenNgay(String denNgay) {
		
		this.denNgay = denNgay;
	}

	
	public String getDenNgay() {
		
		return this.denNgay;
	}

	
	public ResultSet getBKTienThuTrongNgay() {
		
		return this.rsBKTienThuTrongNgay;
		
	}

	public ResultSet getBKTienThuTrongNgay_KT() {
		
		return this.rsBKTienThuTrongNgay_KT;
		
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getUserId() {
		return userId;
	}

	
	public String getNPPID() {
		
		return this.nppID;
	}

	
	public String getNgayKS() {
		
		Utility ut = new Utility();
		this.nppID = ut.getIdNhapp(userId);
		dbutils db = new dbutils();
		String query ="";
		query+="select * from khoasongay where npp_fk='"+this.nppID+"'";
		
		System.out.println("asasgasdg: "+query);
		
		ResultSet rs = db.get("select * from khoasongay where npp_fk='"+this.nppID+"'");
		if(rs != null)
		{
			try {
				rs.next();
				this.ngayKS = rs.getString("ngayks");
			} catch(Exception e) {
				e.printStackTrace();
			}
			finally{try {
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}}
			
			
		}
		if(db != null)
			db.shutDown();
		return this.ngayKS;
	}


	public void setKhIds(String khIds) 
	{
		this.khIds= khIds;
	}


	public String getKhIds() 
	{
		return this.khIds;
	}

	public void setKhRs(ResultSet khRs) 
	{
		this.khRs = khRs;
	}

	public ResultSet getKhRs()
	{
		return this.khRs;
	}


	public void setNvbhIds(String nvbhIds)
	{
		this.nvbhIds = nvbhIds;
	}

	public String getNvbhIds() 
	{
		return this.nvbhIds;
	}

	public void setNvbhRs(ResultSet nvbhRs) 
	{
		this.nvbhRs = nvbhRs;
	}

	public ResultSet getNvbhRs()
	{
		return this.nvbhRs;
	}


	public void setNvgnIds(String nvgnIds)
	{
		this.nvgnIds = nvgnIds;
	}

	public String getNvgnIds() 
	{
		return this.nvgnIds;
	}


	public void setNvgnRs(ResultSet nvgnRs) 
	{
		this.nvgnRs = nvgnRs;
	}
	
	
	public ResultSet getNvgnRs() 
	{		
		return this.nvgnRs;
	}


	public void createRs()
	{

		Utility ut = new Utility();
		this.nppID = ut.getIdNhapp(userId);
		
		String sql = "";
		
		 sql = "select pk_seq, isnull(maFAST,'') + '-' + ten as khTen from KHACHHANG where trangthai = '1' and npp_fk ='"+ this.nppID +"' ";
		this.khRs = db.get(sql);
		
		 sql = "select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from NHANVIENGIAONHAN where trangthai = '1' and npp_fk ='"+ this.nppID +"' ";
			this.nvgnRs = db.get(sql);
			
		//sql = "select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from DAIDIENKINHDOANH where trangthai = '1' and npp_fk ='"+ this.nppID +"' ";
		
		sql = " select B.PK_SEQ , CAST(B.pk_seq as nvarchar(20)) + ' - ' + B.ten as Ten " +
		  " from DAIDIENKINHDOANH_NPP A INNER JOIN DAIDIENKINHDOANH B ON A.DDKD_FK= B.PK_SEQ " +
		  " where  A.npp_fk ='"+ this.nppID +"' AND B.TRANGTHAI = '1' ";
			this.nvbhRs = db.get(sql);
		
		sql =" select distinct nt.PK_SEQ from NOPTIEN nt inner join ERP_THUTIENNPP_NOPTIEN ttnt on ttnt.NOPTIEN_FK=nt.PK_SEQ "+
			" where nt.NPP_FK='"+this.nppID+"' ";
			if(this.khIds.trim().length()>0)
			{
				sql = " and nt.KHACHHANG_FK in ("+this.khIds+")";
			}
		this.phieunoptienRs=db.get(sql);
		
	}
	
	public String getIdNhapp(String userid)
	{
		String sql="select npp.pk_seq,npp.sitecode,npp.ten from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode=sitecode where nv.pk_seq='"+userid+"' and nv.trangthai='1'";
		dbutils db=new dbutils();
		ResultSet rs= db.get(sql);
		try{
			if(rs.next()){
			 this.nppID=rs.getString("pk_seq");
			
			 rs.close();
			}
		}catch(Exception er){
			
		}
		db.shutDown();
		return this.nppID;
	}

	
	public void setPhieuNopTienRs(ResultSet phieunoptienRs) {
		
		this.phieunoptienRs = phieunoptienRs;
	}

	
	public ResultSet getPhieuNopTienRs() {
		
		return phieunoptienRs;
	}



	public void setPhieuNopTienIds(String PhieuNopTienIds) {
		
		this.phieunoptienIds=phieunoptienIds;
	}



	public String getPhieuNopTienIds() {
		
		return this.phieunoptienIds;
	}

}

package geso.traphaco.center.beans.dutrungansach.imp;

import geso.traphaco.center.beans.dutrungansach.IDutrungansach;
import geso.traphaco.center.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Dutrungansach implements IDutrungansach, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String schemeId;
	String scheme;
	String msg;
	ResultSet schemeRS;
	ResultSet kv;
	String kvId;
	ResultSet vung;
	String vungId;
	ResultSet nppRS;
	dbutils db ;
	
	String tungay;
	String denngay;
	ResultSet schemeKhongGhRS; //scheme khong gioi han ngan sach
	ResultSet phanboRs;
	ResultSet phanboNVBHRs;
	ResultSet dutruRS;
	String nppId,phanbo,loaingansach;
	



	public String getLoaingansach()
	{
		return loaingansach;
	}

	public void setLoaingansach(String loaingansach)
	{
		this.loaingansach = loaingansach;
	}

	//xet ctkm co phai la ngan sach thoai mai, hoac phai phan bo.  0 = ko gioi han ngan sach, 1 = phan bo
	String flag;
	
	Hashtable<String, String> usedPro;
	public Dutrungansach()
	{
		this.schemeId = "";
		this.scheme = "";
		this.msg = "";
		this.kvId = "";
		this.vungId = "";
		this.tungay = "";
		this.denngay = "";
		this.flag = "1"; //mac dinh la chuong trinh phan bo khuyen mai ( gioi han ngan sach )
		this.phanbo="";
		this.nppRS = null;
		this.loaingansach="";
		this.db = new dbutils();
	}

	public String getSchemeId()
	{
		return this.schemeId;
	}

	public void setSchemeId(String schemeId)
	{
		this.schemeId = schemeId;
	}
		
	public Hashtable<String, String> getusedPro()
	{
		return this.usedPro;
	}

	public void setusedPro(Hashtable<String, String> usedPro)
	{
		this.usedPro = usedPro;
	}

	public String getScheme()
	{
		ResultSet rs = this.db.get("select scheme from ctkhuyenmai where pk_seq='" + this.schemeId + "'");
		try
		{
			rs.next();
			this.scheme = rs.getString("scheme");
		}
		catch(Exception e){}
		return this.scheme;
	}

	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public ResultSet getSchemeRS() 
	{
		return this.schemeRS;
	}

	public void setSchemeRS(ResultSet schemeRS) 
	{
		this.schemeRS = schemeRS;
	}

	public ResultSet getVung() 
	{
		return this.vung;
	}

	public void setVung(ResultSet vung) 
	{
		this.vung = vung;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId = vungId;
	}
	
	public ResultSet getKv() 
	{
		return this.kv;
	}

	public void setKv(ResultSet khuvuc) 
	{
		this.kv = khuvuc;
	}

	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}

	public ResultSet getNpp() 
	{
		return this.nppRS;
	}

	public void setNpp(ResultSet nppRS) 
	{
		this.nppRS = nppRS;
	}
	
	public void createSchemeRS()
	{
//		String query=
//		"SELECT  DISTINCT PK_SEQ, SCHEME, DIENGIAI, ISNULL(LOAINGANSACH, '1') AS LOAINGANSACH,TUNGAY,DENNGAY ,CASE WHEN A.PK_SEQ IN (SELECT TOP(1) CTKM_FK FROM PHANBOKHUYENMAI WHERE CTKM_FK=A.PK_SEQ ) THEN 1 ELSE 0 END AS DAPHANBO FROM CTKHUYENMAI A  ";
//		if(vungId.length()>0 |kvId.length()>0 ||nppId.length()>0||phanbo.length()>0)
//    	{
//	    	query+=
//	    	"		inner join "+
//	    	"		( "+
//	    	"			select CTKM_FK "+
//	    	"			from CTKM_NPP km  "+
//	    	"				inner join NHAPHANPHOI npp on npp.PK_SEQ=km.NPP_FK	 "+
//	    	"			where  1=1 and isnull(chon,0)=1  " ;
//	    	if(nppId.length()>0)
//	    	query+=
//	    	" and  km.NPP_FK in ( "+nppId+" ) ";
//	    	if(kvId.length()>0)
//	    	query+=" and npp.KHUVUC_FK ="+kvId+"  ";
//	    	if(vungId.length()>0)
//	    	query+=" and npp.KHUVUC_FK in ( select pk_seq from KHUVUC where VUNG_FK="+vungId+" ) ";
//	    	
//	    	if(phanbo.equals("0"))
//	    	{
//		    	query+=
//		    	"	and km.CTKM_FK not in ( select ctkm_fk from PHANBOKHUYENMAI where 1=1 " ;
//		    	if(nppId.length()>0)
//		    		query+=" and npp_fk ="+nppId+"  ";
//		    	query+=" ) ";
//	    	}else if(phanbo.equals("1"))
//	    	{
//	    		query+=
//		    	"	and km.CTKM_FK  in ( select ctkm_fk from PHANBOKHUYENMAI where 1=1 " ;
//		    	if(nppId.length()>0)
//		    		query+=" and npp_fk ="+nppId+"  ";
//		    	query+=" ) ";
//	    	}
//	    	query+=
//	    	" ) pb on pb.CTKM_FK=a.PK_SEQ ";
//    	}	
//    	query+="	WHERE 1=1 ";
//    	
//    	if (tungay.length()>0)
//    	{
//			query = query + " and a.tungay >= '" + tungay + " '";			
//    	}
//    	if (denngay.length()>0){
//			query = query + " and a.denngay <= '" + denngay + " '";		
//    	}
//    	if(this.loaingansach.length()>0)
//    		query+= " and loaingansach="+this.loaingansach+" ";
//    	System.out.println("[CTKM]"+query);
//		this.schemeRS = this.db.get(query);		
	/*	query="SELECT  DISTINCT PK_SEQ, SCHEME, DIENGIAI, ISNULL(LOAINGANSACH, '1') AS LOAINGANSACH,TUNGAY,DENNGAY ,CASE WHEN A.PK_SEQ IN (SELECT TOP(1) CTKM_FK FROM PHANBOKHUYENMAI WHERE CTKM_FK=A.PK_SEQ ) THEN 1 ELSE 0 END AS DAPHANBO FROM CTKHUYENMAI A ";
		if(vungId.length()>0 |kvId.length()>0 ||nppId.length()>0||phanbo.length()>0)
    	{
	    	query+=
	    	"		inner join "+
	    	"		( "+
	    	"			select CTKM_FK "+
	    	"			from CTKM_NPP km  "+
	    	"				inner join NHAPHANPHOI npp on npp.PK_SEQ=km.NPP_FK	 "+
	    	"			where  1=1 and isnull(chon,0)=1  " ;
	    	if(nppId.length()>0)
	    	query+=
	    	" and  km.NPP_FK in ( "+nppId+" ) ";
	    	if(kvId.length()>0)
	    	query+=" and npp.KHUVUC_FK ="+kvId+"  ";
	    	if(vungId.length()>0)
	    	query+=" and npp.KHUVUC_FK in ( select pk_seq from KHUVUC where VUNG_FK="+vungId+" ) ";
	    	if(phanbo.equals("0"))
	    	{
		    	query+=
		    	"	and km.CTKM_FK not in ( select ctkm_fk from PHANBOKHUYENMAI where 1=1 " ;
		    	if(nppId.length()>0)
		    		query+=" and npp_fk ="+nppId+"  ";
		    	query+=" ) ";
	    	}else if(phanbo.equals("1"))
	    	{
	    		query+=
		    	"	and km.CTKM_FK  in ( select ctkm_fk from PHANBOKHUYENMAI where 1=1 " ;
		    	if(nppId.length()>0)
		    		query+=" and npp_fk ="+nppId+"  ";
		    	query+=" ) ";
	    	}
	    	query+=
	    	" ) pb on pb.CTKM_FK=a.PK_SEQ ";
    	}	
    	query+="	WHERE LOAINGANSACH=0  ";
    	
    	if (tungay.length()>0)
    	{
			query = query + " and a.tungay >= '" + tungay + " '";			
    	}
    	if (denngay.length()>0){
			query = query + " and a.denngay <= '" + denngay + " '";		
    	}*/
    	
//		this.schemeKhongGhRS = this.db.get(query);			
	}
	
	private ResultSet createVungRS()
	{  
		ResultSet vungRS =  this.db.get("select pk_seq as vungId, diengiai as vungTen from vung where trangthai='1'");
		return vungRS;
	}
	
	
	private ResultSet createKvRS()
	{
		ResultSet kvRS;
		String query="select pk_seq as khuvucId, diengiai as khuvucTen from khuvuc where trangthai='1' ";
		if(this.vungId.length()>0)
			query+= "and vung_fk = '" + this.vungId + "'";
		
		kvRS=this.db.get(query);
		return kvRS;
	}

	private ResultSet createNppRS()
	{
		ResultSet nppRS = null;
		String query = "";
		System.out.println("chuoi co :"+ flag.trim());
		
		return nppRS;
	}
	
	public void init()
	{
//		this.vung = this.createVungRS();
//		this.kv = this.createKvRS();
//		this.nppRS = this.createNppRS();
		
//		if(this.schemeRS == null)
//		{
			String query= "SELECT c.SCHEME, b.ten as vung, a.ngansach " +
						 "FROM dutrungansach a inner join vung b on a.vung_fk = b.pk_seq inner join ctkhuyenmai c on a.ctkm_fk = c.pk_seq where 1=1";  	
				    	
	    	System.out.println("[1.INIT DUTRUNGANSACH]: "+query);
			this.dutruRS = this.db.get(query);	
//		}
		
	}
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public ResultSet getSchemeKoGioiHanRs()
	{
		return this.schemeKhongGhRS;
	}

	public void setSchemeKoGioiHanRS(ResultSet schemeKoghRS)
	{
		this.schemeKhongGhRS = schemeKoghRS;
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

	public String getFlag()
	{
		return this.flag;
	}

	public void setFlag(String flag) 
	{
		this.flag = flag;
	}
	public ResultSet getPhanboRs()
	{
		return phanboRs;
	}

	public void setPhanboRs(ResultSet phanboRs)
	{
		this.phanboRs = phanboRs;
	}
	
	public String getNppId()
	{
		return nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	@Override
	public void DBClose()
	{
		
			try
			{
				if(this.nppRS!=null)this.nppRS.close();
				if(this.schemeRS!=null)this.schemeRS.close();
				if(this.schemeKhongGhRS!=null)this.schemeKhongGhRS.close();
				if(this.kv!=null)this.kv.close();
				if(this.vung!=null)this.vung.close();
				if(this.nppRS!=null)this.nppRS.close();
				if(this.phanboRs!=null)this.phanboRs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		
	}
	
	public String getPhanbo()
	{
		return phanbo;
	}

	public void setPhanbo(String phanbo)
	{
		this.phanbo = phanbo;
	}

	@Override
	public void createPhanBoRs()
	{
		if(this.schemeId.length()>0)
		{
			String query=
			"	SELECT CTKM.SCHEME,CTKM.DIENGIAI,NPP.PK_SEQ as NPPID,NPP.MA AS NPPMA,NPP.TEN AS NPPTEN, "+
			"		PBKM.NGANSACH , "+
			"		CASE WHEN KM.PHANBOTHEODONHANG =0 THEN ISNULL((SELECT SUM(TRAKM.TONGGIATRI)FROM DONHANG_CTKM_TRAKM  TRAKM INNER JOIN DONHANG DH ON DH.PK_SEQ=TRAKM.DONHANGID WHERE DH.NPP_FK=PBKM.NPP_FK AND TRAKM.CTKMID = PBKM.CTKM_FK AND DH.TRANGTHAI!=2 ),0) "+
			"		ELSE ISNULL((SELECT SUM(TRAKM.SOLUONG)FROM DONHANG_CTKM_TRAKM  TRAKM INNER JOIN DONHANG DH ON DH.PK_SEQ=TRAKM.DONHANGID WHERE DH.NPP_FK=PBKM.NPP_FK AND TRAKM.CTKMID = PBKM.CTKM_FK AND SPMA IS NOT NULL AND DH.TRANGTHAI !=2 ),0	)END "+  
			"		AS DASUDUNG,0 AS CONLAI  "+
			"	FROM PHANBOKHUYENMAI PBKM INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=PBKM.NPP_FK "+ 
			"	INNER JOIN CTKHUYENMAI KM ON KM.PK_SEQ=PBKM.CTKM_FK "+
			"	INNER JOIN  CTKHUYENMAI CTKM ON CTKM.PK_SEQ=PBKM.CTKM_FK " +
			"   WHERE 1=1    " ; 
				 
			if(this.kvId.length()>0)
			{
				query += " and npp.khuvuc_fk in ("+this.kvId+")";
			}
			if(this.vungId.length()>0)
			{
				query+=" and npp.khuvuc_fk in (select pk_seq from khuvuc where vung_fk="+this.vungId+" )";
			}
			if(this.schemeId.length()>0)
			{
				query += " and ctkm.pk_seq in ("+this.schemeId+") ";
			}
			if(this.loaingansach.length()>0)
				query+=" and ctkm.loaingansach='"+this.loaingansach+"' ";
			this.phanboRs=this.db.get(query);
			System.out.println("[PHANBOKHUYENMAI]"+query);
		}
	}

	@Override
	public void createPhanBoNVBHRs() {
		// TODO Auto-generated method stub
		if(this.schemeId.length()>0)
		{
			String query=
			"	SELECT CTKM.SCHEME,CTKM.DIENGIAI,ddkd.PK_SEQ as ddkdID,ddkd.maFAST AS ddkdMA,ddkd.TEN AS ddkdTEN,NPP.PK_SEQ as NPPID,NPP.MA AS NPPMA,NPP.TEN AS NPPTEN, "+
			"		PBKM.NGANSACH , "+
			"		CASE WHEN KM.PHANBOTHEODONHANG =0 THEN ISNULL((SELECT SUM(TRAKM.TONGGIATRI)FROM DONHANG_CTKM_TRAKM  TRAKM INNER JOIN DONHANG DH ON DH.PK_SEQ=TRAKM.DONHANGID WHERE DH.NPP_FK=PBKM.NPP_FK AND TRAKM.CTKMID = PBKM.CTKM_FK AND DH.TRANGTHAI!=2 ),0) "+
			"		ELSE ISNULL((SELECT SUM(TRAKM.SOLUONG)FROM DONHANG_CTKM_TRAKM  TRAKM INNER JOIN DONHANG DH ON DH.PK_SEQ=TRAKM.DONHANGID WHERE DH.NPP_FK=PBKM.NPP_FK AND TRAKM.CTKMID = PBKM.CTKM_FK AND SPMA IS NOT NULL AND DH.TRANGTHAI !=2 ),0	)END "+  
			"		AS DASUDUNG,0 AS CONLAI  "+
			"	FROM PHANBOKHUYENMAI PBKM inner join daidienkinhdoanh ddkd on pbkm.nvbh_fk = ddkd.PK_SEQ INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=PBKM.NPP_FK "+ 
			"	INNER JOIN CTKHUYENMAI KM ON KM.PK_SEQ=PBKM.CTKM_FK "+
			"	INNER JOIN  CTKHUYENMAI CTKM ON CTKM.PK_SEQ=PBKM.CTKM_FK " +
			"   WHERE 1=1    " ; 
				 
			if(this.kvId.length()>0)
			{
				query += " and npp.khuvuc_fk in ("+this.kvId+")";
			}
			if(this.vungId.length()>0)
			{
				query+=" and npp.khuvuc_fk in (select pk_seq from khuvuc where vung_fk="+this.vungId+" )";
			}
			if(this.schemeId.length()>0)
			{
				query += " and ctkm.pk_seq in ("+this.schemeId+") ";
			}
			if(this.loaingansach.length()>0)
				query+=" and ctkm.loaingansach='"+this.loaingansach+"' ";
			this.phanboNVBHRs=this.db.get(query);
			System.out.println("[PHANBOKHUYENMAI]"+query);
		}
	}

	@Override
	public ResultSet getPhanboNVBHRs() {
		// TODO Auto-generated method stub
		return this.phanboNVBHRs;
	}

	@Override
	public void setPhanboNVBHRs(ResultSet phanboNVBHRs) {
		// TODO Auto-generated method stub
		this.phanboNVBHRs = phanboNVBHRs;
	}

	@Override
	public ResultSet getDutruRs() {
		// TODO Auto-generated method stub
		return this.dutruRS;
	}

	@Override
	public void setDutruRs(ResultSet dutruRs) {
		// TODO Auto-generated method stub
		this.dutruRS = dutruRs;
	}
	
	
}

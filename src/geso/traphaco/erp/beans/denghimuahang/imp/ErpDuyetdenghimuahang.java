package geso.traphaco.erp.beans.denghimuahang.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.denghimuahang.IErpDuyetdenghimuahang;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public class ErpDuyetdenghimuahang implements IErpDuyetdenghimuahang {
	
	String congtyId;
	String userId;
	String ctyId;
	
	String nppId;
	
	String dvthId;
	ResultSet dvth;
	
	String lspId;
	String ngaymua;
	String maDMH;
	String nccId;
	ResultSet nccList;
	ResultSet lsp;
	
	ResultSet polist;
	
	HttpServletRequest request;	
	String msg;
	dbutils db;
    Utility util;

	public ErpDuyetdenghimuahang(){
		this.userId = "";
		this.nppId = "";
		this.ctyId="";
		this.dvthId = "";
		this.lspId = "";
		this.ngaymua="";
		this.maDMH = "";
		this.nccId ="";
		this.msg = "";
		this.db = new dbutils();
		this.util=new Utility();
	}

	public void setNccList(ResultSet nccList)
	{
		this.nccList = nccList;
	}
	
	public ResultSet getNccList() 
	{
		return nccList;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}
	
	public String getNccId() 
	{
		return nccId;
	}
	
	public void setMaDMH(String maDMH) 
	{
		this.maDMH = maDMH;
	}
	
	public String getMaDMH() 
	{
		return maDMH;
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
		this.nppId = util.getIdNhapp(userId);
	}

	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	public String getNgaymua(){
		return this.ngaymua;
	}
	
	public void setNgaymua(String ngaymua){
		this.ngaymua = ngaymua;
	}
	
	public String getDvthId(){
		return this.dvthId;
	}
	
	public void setDvthId(String dvthId){
		this.dvthId = dvthId;
	}
	
	public String getLspId(){
		return this.lspId;
	}
	
	public void setLspId(String lspId){
		this.lspId = lspId;
	}
	
	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getLspList(){
		return this.lsp ;
	}
	
	public void setLspList(ResultSet lsp){
		this.lsp = lsp;
	}

	public ResultSet getDvthList(){
		return this.dvth ;
	}
	
	public void setDvthList(ResultSet dvth){
		this.dvth = dvth;
	}

	public ResultSet getPoList(){
		return this.polist ;
	}
	
	public void setPoList(ResultSet polist){
		this.polist = polist;
	}

	public void setRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public void init()
	{		
		String query = "SELECT distinct NV.TEN as NGUOITAO, DNMH.PK_SEQ AS DNID, DNMH.NGAYDENGHI AS NGAY, DVTH.TEN AS DVTH,  \n"+
					   "       case DNMH.LOAIHANGHOA_FK when '0' then SP.MA when '1' then TS.ma else TKKT.SOHIEUTAIKHOAN+' - ' + TKKT.TENTAIKHOAN + '-' + CP.TEN end as MA, \n"+  
				       "	   case DNMH.LOAIHANGHOA_FK when '0' then SP.TEN else DNMH_SP.diengiai end AS SP, DNMH_SP.SOLUONG, dnmh.tooltip,\n"+
					   "       (SELECT count(*) FROM ERP_CHUCDANH WHERE NHANVIEN_FK = "+ this.userId +" AND DVTH_FK = DNMH.DONVITHUCHIEN_FK)  ISTBP "+
					   "FROM ERP_DENGHIMUAHANG DNMH \n"+
					   "	INNER JOIN NHANVIEN NV ON NV.PK_SEQ = DNMH.NGUOITAO   \n"+
					   "	INNER JOIN ERP_DENGHIMUAHANG_SP DNMH_SP ON DNMH_SP.DENGHI_FK = DNMH.PK_SEQ    \n"+
					   "	INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = DNMH.DONVITHUCHIEN_FK    \n"+
					   "	LEFT JOIN ERP_NHANVIEN NV1 ON NV1.PK_SEQ = DNMH.NHANVIEN_FK   \n"+
					   "	LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DNMH_SP.SANPHAM_FK  \n"+
					   "	LEFT join ERP_MASCLON TS on TS.pk_seq = DNMH_SP.TAISAN_FK  \n"+
					   "	LEFT JOIN ERP_NHOMCHIPHI CP on CP.PK_SEQ = DNMH_SP.CHIPHI_FK   \n"+
					   "	LEFT JOIN ERP_TAIKHOANKT TKKT ON TKKT.PK_SEQ=CP.TAIKHOAN_FK   \n"+
					   "WHERE DNMH.TRANGTHAI = 0 AND ISNULL(DNMH.DACHOT,0) = 1 AND ISNULL(DNMH.ISTRUONGBPDUYET,0) = 0 AND DNMH.CONGTY_FK = '"+ this.congtyId + "' "+
					   "    AND "+this.userId+" IN (SELECT NHANVIEN_FK FROM ERP_CHUCDANH WHERE DVTH_FK = DNMH.DONVITHUCHIEN_FK and trangthai = 1) ";
         		         
		 		
		if(this.dvthId!=null&&this.dvthId.trim().length() > 0)
		{
			query += " AND DNMH.DONVITHUCHIEN_FK = '" + this.dvthId + "' ";
		}
		if(this.ngaymua!=null && this.ngaymua.trim().length() > 0)
		{
			query += " AND DNMH.NGAYDENGHI = '" + this.ngaymua + "' ";
		}
		if(this.maDMH !=null && this.maDMH.length() > 0)
		{
			query += " AND DNMH.PK_SEQ LIKE '%"+ this.maDMH +"%'  ";
		}
		
		query += "ORDER BY DNID ASC , NGAY ASC";

		System.out.println(" 1. init duyet :" + query);
		
		this.polist = this.db.get(query);
		this.dvth = this.db.get("SELECT PK_SEQ AS DVTHID, TEN AS DVTH FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI = '1' ") ;
		this.lsp = this.db.get("SELECT PK_SEQ AS LSPID, TEN AS LSP FROM ERP_LOAISANPHAM WHERE TRANGTHAI = '1' ");

		 // and pk_seq in "+util.quyen_donvithuchien(this.userId);
	}
	
	public boolean Duyetdnmuahang(String Id){
		 System.out.println("Da vao day");
		
		try
		{
			db = new dbutils();
						
			db.getConnection().setAutoCommit(false);
			
			String query = "";
									 			
			query =  " UPDATE ERP_DENGHIMUAHANG SET ISTRUONGBPDUYET = '1', NGUOIDUYET = "+ this.userId +" " +
					 " WHERE PK_SEQ = '" + Id + "' where ISTRUONGBPDUYET!=1 ";
			System.out.println("Duyet "+query);
			if(this.db.updateReturnInt(query)!=1) 
			{
				db.getConnection().rollback();
				return false;
			}
					
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return false;
		}
		
		return true;
	}
	
	public String getDaduyet(String dnId){
		String tmp = "";
		String query =  "SELECT DN.PK_SEQ AS DNID, NV.PK_SEQ AS NVID, NV.DANGNHAP AS NVTEN " +
						"FROM  ERP_DENGHIMUAHANG DN " +
						"INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.DVTH_FK = DN.DONVITHUCHIEN_FK " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = CHUCDANH.NHANVIEN_FK " +
						"WHERE ISNULL(DN.ISTRUONGBPDUYET,0) = '1' AND DN.PK_SEQ = " + dnId + "  ";
		ResultSet rs = this.db.get(query);
		try{
			if(rs != null){
				while(rs.next()){
					tmp = tmp  + rs.getString("NVTEN") + " ; " ;
				}
				if(tmp.length() > 0)
					tmp = tmp.substring(0, tmp.length()-2);
				rs.close();
				return tmp;
			}else{
				return tmp;
			}
		}catch(java.sql.SQLException e){return tmp;}

	}
	
	public void DBclose(){
		try{
			if(this.dvth != null) this.dvth.close();
			if(this.lsp != null) this.lsp.close();
			if(this.polist != null) this.lsp.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}




}

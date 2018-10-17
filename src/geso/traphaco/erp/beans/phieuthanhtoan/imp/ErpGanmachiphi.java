package geso.traphaco.erp.beans.phieuthanhtoan.imp;

import geso.traphaco.erp.beans.phieuthanhtoan.IErpGanmachiphi;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class ErpGanmachiphi implements IErpGanmachiphi {
	
	String congtyId;
	String userId;
	String ctyId;
	
	String dvthId;
	ResultSet dvth;
	
	String lspId;
	String ngaymua;
	String nppdangnhap;
	String maDMH;
	String nccId;
	ResultSet nccList;
	ResultSet lsp;
	
	ResultSet polist;
	
	HttpServletRequest request;	
	String msg;
	dbutils db;
    Utility util;

	public ErpGanmachiphi(){
		this.userId = "";
		//this.ctyId = "100001";
		this.ctyId="";
		this.dvthId = "";
		this.lspId = "";
		this.ngaymua="";
		this.maDMH = "";
		this.nccId ="";
		this.msg = "";
		this.nppdangnhap="";
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
 
		this.getNppInfo();
		
		String query =      " SELECT distinct NV.TEN as NGUOITAO, MUAHANG.PK_SEQ AS MHID, NGAYMUA AS NGAY, DVTH.TEN AS DVTH, "+
				            " CASE WHEN MUAHANG.NHACUNGCAP_FK IS NOT NULL THEN NCC.TEN WHEN MUAHANG.NHANVIEN_FK IS NOT NULL THEN NV.TEN ELSE KH.TEN END AS NCC, MUAHANG.TONGTIENAVAT, \n" +
							" case MUAHANG.LOAIHANGHOA_FK when '0' then SP.MA when '1' then TS.ma else TKKT.SOHIEUTAIKHOAN+' - ' + TKKT.TENTAIKHOAN + '-' + CP.TEN end as MA,   \n" +
							" case MUAHANG.LOAIHANGHOA_FK when '0' then SP.TEN else MUAHANG_SP.diengiai end AS SP,  \n" +
							" MUAHANG_SP.SOLUONG, isnull(MUAHANG_SP.DONGIA, 0) as DONGIA, isnull(MUAHANG_SP.THANHTIEN, 0) as THANHTIEN, isnull(MUAHANG.VUOTNGANSACH, 0) as vuotNganSach, \n" +
							" MUAHANG.SOPO as SOCHUNGTU, ISNULL(MUAHANG.ISGANMACP,0) ISGANMACP \n" +
						    " FROM ERP_MUAHANG MUAHANG \n" +
							" INNER JOIN NHANVIEN NV ON NV.PK_SEQ = MUAHANG.NGUOITAO   \n" +
							" INNER JOIN ERP_MUAHANG_SP MUAHANG_SP ON MUAHANG_SP.MUAHANG_FK = MUAHANG.PK_SEQ   \n" +
							" INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = MUAHANG.DONVITHUCHIEN_FK  \n" +
							" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MUAHANG.NHACUNGCAP_FK   \n" +
							
							" LEFT JOIN ERP_NHANVIEN NV1 ON NV1.PK_SEQ = MUAHANG.NHANVIEN_FK   \n" +
							" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = MUAHANG.KHACHHANG_FK   " +
							" LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MUAHANG_SP.SANPHAM_FK  \n" +
							" LEFT join ERP_TAISANCODINH TS on TS.pk_seq = MUAHANG_SP.TAISAN_FK  \n" +
							" LEFT JOIN ERP_NHOMCHIPHI CP on CP.PK_SEQ = MUAHANG_SP.CHIPHI_FK  \n" + 
							" LEFT JOIN ERP_TAIKHOANKT TKKT ON TKKT.SOHIEUTAIKHOAN=CP.TAIKHOAN_FK  \n" +
							" LEFT JOIN ERP_DUYETMUAHANG DUYETMUAHANG ON DUYETMUAHANG.MUAHANG_FK = MUAHANG.PK_SEQ  \n" +
							" LEFT JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETMUAHANG.CHUCDANH_FK    \n" +
						    " WHERE MUAHANG.TRANGTHAI = 0 AND isnull(MUAHANG.DACHOT, 0) = '1' AND MUAHANG.ISDNTT = 1 \n" +
						    " AND ISNULL(MUAHANG.ISTRUONGBPDUYET,0) = '1' AND ISNULL(MUAHANG.ISCHOTGANMACP,0) = 0 \n" +						   					    
						    " AND MUAHANG.PK_SEQ NOT IN (SELECT MUAHANG_FK  FROM ERP_DUYETMUAHANG WHERE TRANGTHAI=1 AND CHUCDANH_FK = (select pk_seq from ERP_CHUCDANH where NHANVIEN_FK = "+ this.userId +" ) ) \n" +
						    " AND MUAHANG.congty_fk = '" + this.congtyId + "' \n" +
							//" AND dvth.pk_seq IN  "+this.util.quyen_donvithuchien(this.userId) +
							"  AND MUAHANG.LOAIHANGHOA_FK = 2  and MUAHANG.TYPE = '1' "+
							" AND MUAHANG.DONVITHUCHIEN_FK NOT IN (100083,100091) AND MUAHANG.CONGTY_FK = "+this.congtyId;
		
		String sql = "";
		if(query.trim().length() > 0) sql += " UNION ALL ";
		
		       sql +=       " SELECT distinct NV.TEN as NGUOITAO, MUAHANG.PK_SEQ AS MHID, NGAYMUA AS NGAY, DVTH.TEN AS DVTH, "+
		       		        " CASE WHEN MUAHANG.NHACUNGCAP_FK IS NOT NULL THEN NCC.TEN WHEN MUAHANG.NHANVIEN_FK IS NOT NULL THEN NV.TEN ELSE KH.TEN END AS NCC, MUAHANG.TONGTIENAVAT, \n" +
							" case MUAHANG.LOAIHANGHOA_FK when '0' then SP.MA when '1' then TS.ma else TKKT.SOHIEUTAIKHOAN+' - ' + TKKT.TENTAIKHOAN + '-' + CP.TEN end as MA,   \n" +
							" case MUAHANG.LOAIHANGHOA_FK when '0' then SP.TEN else MUAHANG_SP.diengiai end AS SP,  \n" +
							" MUAHANG_SP.SOLUONG, isnull(MUAHANG_SP.DONGIA, 0) as DONGIA, isnull(MUAHANG_SP.THANHTIEN, 0) as THANHTIEN, isnull(MUAHANG.VUOTNGANSACH, 0) as vuotNganSach, \n" +
							" MUAHANG.SOPO as SOCHUNGTU, ISNULL(MUAHANG.ISGANMACP,0) ISGANMACP \n" +
						    " FROM ERP_MUAHANG MUAHANG \n" +
							" INNER JOIN NHANVIEN NV ON NV.PK_SEQ = MUAHANG.NGUOITAO   \n" +
							" INNER JOIN ERP_MUAHANG_SP MUAHANG_SP ON MUAHANG_SP.MUAHANG_FK = MUAHANG.PK_SEQ   \n" +
							" INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = MUAHANG.DONVITHUCHIEN_FK  \n" +
							" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MUAHANG.NHACUNGCAP_FK   \n" +
							
							" LEFT JOIN ERP_NHANVIEN NV1 ON NV1.PK_SEQ = MUAHANG.NHANVIEN_FK   \n" +
							" LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = MUAHANG.KHACHHANG_FK   " +
							" LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MUAHANG_SP.SANPHAM_FK  \n" +
							" LEFT join ERP_TAISANCODINH TS on TS.pk_seq = MUAHANG_SP.TAISAN_FK  \n" +
							" LEFT JOIN ERP_NHOMCHIPHI CP on CP.PK_SEQ = MUAHANG_SP.CHIPHI_FK  \n" + 
							" LEFT JOIN ERP_TAIKHOANKT TKKT ON TKKT.SOHIEUTAIKHOAN=CP.TAIKHOAN_FK  \n" +
							" LEFT JOIN ERP_DUYETMUAHANG DUYETMUAHANG ON DUYETMUAHANG.MUAHANG_FK = MUAHANG.PK_SEQ  \n" +
							" LEFT JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETMUAHANG.CHUCDANH_FK    \n" +
						    " WHERE MUAHANG.TRANGTHAI = 0 AND isnull(MUAHANG.DACHOT, 0) = '1' AND MUAHANG.ISDNTT = 1  \n" +
						    " AND ISNULL(MUAHANG.ISCHOTGANMACP,0) = 0 \n" +						   					    
						    " AND MUAHANG.PK_SEQ NOT IN (SELECT MUAHANG_FK  FROM ERP_DUYETMUAHANG WHERE TRANGTHAI=1 AND CHUCDANH_FK = (select pk_seq from ERP_CHUCDANH where NHANVIEN_FK = "+ this.userId +" ) ) \n" +
						    " AND MUAHANG.congty_fk = '" + this.congtyId + "' \n" +
							//" AND dvth.pk_seq IN  "+this.util.quyen_donvithuchien(this.userId) +
							"  AND MUAHANG.LOAIHANGHOA_FK = 2  and MUAHANG.TYPE = '1' "+
							" AND MUAHANG.DONVITHUCHIEN_FK IN (100083,100091) AND MUAHANG.CONGTY_FK = "+this.congtyId;		
		

		if(this.dvthId!=null&&this.dvthId.trim().length() > 0)
		{
			query += " AND MUAHANG.DONVITHUCHIEN_FK = '" + this.dvthId + "' ";
			sql += " AND MUAHANG.DONVITHUCHIEN_FK = '" + this.dvthId + "' ";
		}
		if(this.ngaymua!=null && this.ngaymua.trim().length() > 0)
		{
			query += " AND MUAHANG.NGAYMUA = '" + this.ngaymua + "' ";
			sql += " AND MUAHANG.NGAYMUA = '" + this.ngaymua + "' ";
		}
		if(this.nccId !=null && this.nccId.length() > 0)
		{
			query += " AND MUAHANG.NHACUNGCAP_FK = '"+ this.nccId +"' ";
			sql += " AND MUAHANG.NHACUNGCAP_FK = '"+ this.nccId +"' ";
		}
		if(this.maDMH !=null && this.maDMH.length() > 0)
		{
			query += " AND MUAHANG.SOPO LIKE '%"+ this.maDMH +"%' OR MUAHANG.PK_SEQ LIKE '%"+ this.maDMH +"%' ";
			sql += " AND MUAHANG.SOPO LIKE '%"+ this.maDMH +"%' OR MUAHANG.PK_SEQ LIKE '%"+ this.maDMH +"%' ";
		}

		query = query + sql ;
		
		query += "ORDER BY MHID ASC , NGAY ASC";
 
		System.out.println(" 1. init gán mã chi phí :" + query);
		
		this.polist = this.db.get(query);
		 query="SELECT PK_SEQ AS DVTHID, TEN AS DVTH FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI = '1' and congty_fk = '" + this.congtyId ;
		this.dvth = this.db.get(query) ;
		this.lsp = this.db.get("SELECT PK_SEQ AS LSPID, TEN AS LSP FROM ERP_LOAISANPHAM WHERE TRANGTHAI = '1' ");
		this.nccList = this.db.get("SELECT PK_SEQ, MA + '-' + TEN AS TENNCC FROM ERP_NHACUNGCAP WHERE TRANGTHAI = '1' and duyet = '1' AND CONGTY_FK = "+this.congtyId+" ");
	}
	
	public boolean DuyetganMCP(String Id){
		 		
		try
		{
			db = new dbutils();
						
			db.getConnection().setAutoCommit(false);
			
			String query = " UPDATE ERP_MUAHANG SET ISCHOTGANMACP = 1 WHERE PK_SEQ = "+ Id +" ";
	        
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
														
			db.getConnection().commit();
			
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
	
	public String getDaduyet(String mhId){
		String tmp = "";
		String query =  "SELECT DUYETMUAHANG.MUAHANG_FK AS MHID, NV.PK_SEQ AS NVID, NV.DANGNHAP AS NVTEN " +
						"FROM ERP_DUYETMUAHANG DUYETMUAHANG " +
						"INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETMUAHANG.CHUCDANH_FK " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = CHUCDANH.NHANVIEN_FK " +
						"WHERE DUYETMUAHANG.TRANGTHAI = '1' AND MUAHANG_FK = " + mhId + "  ";
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

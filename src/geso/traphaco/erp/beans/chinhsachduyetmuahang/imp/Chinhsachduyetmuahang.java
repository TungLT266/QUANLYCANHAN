package geso.traphaco.erp.beans.chinhsachduyetmuahang.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.chinhsachduyetmuahang.IChinhsachduyetmuahang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Chinhsachduyetmuahang implements IChinhsachduyetmuahang {

	private String ctyId;
		
	private String dvthId;
	private String csId;
	private String kcpId;
	private String diengiai;
	private String[] spIds;
	private String[] nccIds;
	
	private ResultSet cs;	
	private ResultSet dvth;
	private ResultSet kcp;
	private ResultSet ncc;
	private ResultSet sp;
	private String[] kcpIds;
	
	private String msg;
	private dbutils db;
	private String[] chon;
	private String[] thutu;
	private String[] quyetdinh;
	
	private HttpServletRequest request;
	private String Userid;
	private   Utility util;

	private String trangthai;

	private String[] NccQuyetdinh;

	private String[] NccChon;

	private String nccId;

	private String[] spChon;

	private String[] spQuyetdinh;

	private String spId;
	private String tab;
	
	public Chinhsachduyetmuahang()
	{
		util=new Utility();
		this.ctyId = "";
		this.csId = "";
		this.dvthId = "";
		this.diengiai = "";
		this.msg = "";
		this.tab = "1";
		this.trangthai = "1";
		this.db = new dbutils();
	}

	public String getCtyId(){
		return this.ctyId;	
	}

	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	
	public String getCsId(){
		return this.csId;	
	}

	public void setCsId(String csId){
		this.csId = csId;
	}


	public String getDvthId(){
		return this.dvthId;	
	}

	public void setDvthId(String dvthId){
		this.dvthId = dvthId;
	}

	public String getDiengiai(){
		return this.diengiai;	
	}

	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}

	public void setDvth(ResultSet dvth)
	{
		this.dvth = dvth;
	}

	
	public ResultSet getDvth()
	{ 		
		return this.dvth;
		
	}

	public String getKcpId(){
		return this.kcpId;	
	}

	public void setKcpId(String kcpId){
		this.kcpId = kcpId;
	}
	
	public String[] getKcpIds(){
		return this.kcpIds;	
	}

	public void setKcpIds(String[] kcpIds){
		this.kcpIds = kcpIds;
	}

	public void setKcp(ResultSet kcp)
	{
		this.kcp = kcp;
	}

	public ResultSet getKcp()
	{ 		
		return this.kcp;
		
	}

	public void setCS(ResultSet cs)
	{
		this.cs = cs;
	}

	public ResultSet getCS()
	{ 		
		return this.cs;
		
	}

	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public void setChon(String[] chon){
		this.chon = chon;
	}
	
	public void setThutu(String[] thutu){
		this.thutu = thutu;
	}
	
	public void setQuyetdinh(String[] quyetdinh){
		this.quyetdinh = quyetdinh;
	}
	
	public void setTab(String tab){
		this.tab = tab;
	} 
	
	public String getTab(){
		return this.tab;
	} 
	
	public void setRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public void createCsList()
	{
		String sql=	"SELECT PK_SEQ AS CSID, DIENGIAI AS CSTEN FROM ERP_CHINHSACHDUYET " +
					"WHERE TRANGTHAI=1 AND CONGTY_FK = " + this.ctyId + " ";
//		            "AND pk_seq in "+util.quyen_donvithuchien(this.Userid);
		this.cs = this.db.get(sql);
	}
	
	public void init_new(){
		String query = "SELECT PK_SEQ AS DVTHID, TEN AS DVTH FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI = 1 AND CONGTY_FK = '" + this.ctyId + "' " ;
		System.out.println(query);
		
		this.dvth = this.db.get(query);
		
		if(this.dvthId.length() > 0){
			query = "SELECT SOTIENTU, SOTIENDEN, KHOANGCHIPHI_FK AS KHOANGCHIPHIID " +
					"FROM ERP_KHOANGCHIPHI KCP " +
					"INNER JOIN ERP_KHOANGCHIPHI_DONVITHUCHIEN KCP_DVTH ON KCP_DVTH.KHOANGCHIPHI_FK = KCP.PK_SEQ " +
					"WHERE TRANGTHAI = 1 AND CONGTY_FK = '" + this.ctyId + "'  " +
					"AND DONVITHUCHIEN_FK = '" + this.dvthId + "'  AND CHON = 1 ORDER BY SOTIENTU ";
			System.out.println(query);
			this.kcp = this.db.get(query);
		}
		
		query = "SELECT PK_SEQ, MA + ' - ' + TEN AS TEN " +
				"FROM ERP_NHACUNGCAP " +
				"WHERE TRANGTHAI = 1  and duyet = '1' AND CONGTY_FK = '" + this.ctyId + "' " +
				" ORDER BY TEN ";
		System.out.println(query);
		this.ncc = this.db.getScrol(query);

		query = "SELECT sp.PK_SEQ, sp.MA + ' - ' + sp.TEN AS TEN " +
				"FROM ERP_SANPHAM sp " +
				"WHERE sp.LOAISANPHAM_FK in (100000, 100002, 100003, 100007) AND sp.TRANGTHAI = 1 ORDER BY sp.TEN asc ";
		System.out.println(query);
		this.sp = this.db.getScrol(query);
	}
	
	public void init(){
		String query = 	""; 
		this.getChinhsach();
		query = 	"SELECT  DISTINCT CS.DONVITHUCHIEN_FK, CS.PK_SEQ AS CSID, " +
					"KHOANGCHIPHI.PK_SEQ AS KHOANGCHIPHIID, SOTIENTU, ISNULL(SOTIENDEN, -1) AS SOTIENDEN " + 
					"FROM ERP_CHINHSACHDUYET_CHIPHI CSCT	" +
					"INNER JOIN ERP_CHINHSACHDUYET CS ON CS.PK_SEQ = CSCT.CHINHSACHDUYET_FK " +
					"INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = CSCT.KHOANGCHIPHI_FK " + 
					"INNER JOIN ERP_KHOANGCHIPHI_DONVITHUCHIEN KCP_DVTH ON KCP_DVTH.DONVITHUCHIEN_FK = CS.DONVITHUCHIEN_FK " + 
					"AND KCP_DVTH.KHOANGCHIPHI_FK = KHOANGCHIPHI.PK_SEQ " +
					"WHERE CS.PK_SEQ = " + this.csId + " AND KCP_DVTH.CHON = 1 " +

					"UNION " +
					"SELECT  KCP_DVTH.DONVITHUCHIEN_FK, " + this.csId + " AS CSID, KCP_DVTH.KHOANGCHIPHI_FK, KCP.SOTIENTU, ISNULL(SOTIENDEN, -1) AS SOTIENDEN " + 
					"FROM ERP_KHOANGCHIPHI_DONVITHUCHIEN KCP_DVTH " +
					"INNER JOIN ERP_KHOANGCHIPHI KCP ON KCP.PK_SEQ = KCP_DVTH.KHOANGCHIPHI_FK " + 
					"WHERE  KCP_DVTH.CHON = 1 AND KCP_DVTH.DONVITHUCHIEN_FK = " + this.dvthId + " " +
					"AND KCP_DVTH.KHOANGCHIPHI_FK NOT IN ( 	" +	
					"SELECT DISTINCT KHOANGCHIPHI_FK " +	
					"FROM ERP_CHINHSACHDUYET_CHIPHI " + 		
					"WHERE CHINHSACHDUYET_FK = " + this.csId + " " +
					") AND KCP_DVTH.CHON = '1' " +						
					"ORDER BY SOTIENTU, SOTIENDEN  " ;	
			
	
		System.out.println("Query = " + query);
		this.cs = this.db.get(query);

		query = "SELECT PK_SEQ AS DVTHID, TEN AS DVTH FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI = 1 AND CONGTY_FK = '" + this.ctyId + "' " ;
		
		this.dvth = this.db.get(query);

		query = "SELECT PK_SEQ, MA + ' - ' + TEN AS TEN " +
				"FROM ERP_NHACUNGCAP " +
				"WHERE TRANGTHAI = 1 and duyet = '1' AND CONGTY_FK = '" + this.ctyId + "' " +
				"ORDER BY TEN ";
		System.out.println(query);
		this.ncc = this.db.getScrol(query);

		query = "SELECT sp.PK_SEQ, sp.MA + ' - ' + sp.TEN AS TEN " +
				"FROM ERP_SANPHAM sp " +
				"WHERE sp.LOAISANPHAM_FK in (100000,100003,100002,100007) AND sp.TRANGTHAI = 1 ORDER BY sp.TEN asc ";
		System.out.println(query);
		this.sp = this.db.getScrol(query);
	}

	private void getChinhsach(){
		String query = "SELECT DIENGIAI, DONVITHUCHIEN_FK AS DVTHID ,TRANGTHAI FROM ERP_CHINHSACHDUYET WHERE PK_SEQ = " + this.csId + "";
		System.out.println("CHINHSACHDUYETCHIPHI: "+query);
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.diengiai = rs.getString("DIENGIAI");
			this.dvthId = rs.getString("DVTHID");
			this.trangthai = rs.getString("TRANGTHAI");
			rs.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}		
	}
	
	public ResultSet Chinhsachduyet(String khoangchiphiId)
	{
		String query = 	"";
		
		if(this.csId.length() > 0){
			query = "SELECT DISTINCT CHUCDANH.PK_SEQ AS CDID, CHUCDANH.DIENGIAI AS CHUCDANH, " + 
					"CSCT.KHOANGCHIPHI_FK, KHOANGCHIPHI.SOTIENTU, KHOANGCHIPHI.SOTIENDEN, " +
					"CSCT.THUTU, CSCT.QUYETDINH " +
					"FROM ERP_CHINHSACHDUYET_CHIPHI CSCT " + 
					"INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = CSCT.CHUCDANH_FK " + 
					"INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = CSCT.KHOANGCHIPHI_FK " + 
					"WHERE KHOANGCHIPHI_FK = '"+ khoangchiphiId + "' AND CSCT.CHINHSACHDUYET_FK = '" + this.csId + "' " +
					"ORDER BY CSCT.THUTU";
		}
		
		//System.out.println(query);
		
		return this.db.get(query);
	}
	
	public ResultSet Chucdanhconlai(String khoangchiphiId)
	{
		String query = "";
		if(this.csId.length() > 0){
			query = 	"SELECT PK_SEQ AS CDID, DIENGIAI AS CHUCDANH " +
						"FROM ERP_CHUCDANH " +
						"WHERE PK_SEQ NOT IN " +
						"( " +
						"	SELECT DISTINCT CHUCDANH.PK_SEQ " +
						"	FROM ERP_CHINHSACHDUYET_CHIPHI CSCT " + 
						"	INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = CSCT.CHUCDANH_FK " + 
						"	INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = CSCT.KHOANGCHIPHI_FK " + 
						"	WHERE KHOANGCHIPHI_FK = '"+ khoangchiphiId + "' AND CSCT.CHINHSACHDUYET_FK = '" + this.csId + "' " +						
						") AND CONGTY_FK = " + this.ctyId + " ";
		}else{
			query = 	"SELECT PK_SEQ AS CDID, DIENGIAI AS CHUCDANH " +
						"FROM ERP_CHUCDANH " +
						"WHERE CONGTY_FK = " + this.ctyId + " ";			
		}
		
		//System.out.println(query);
		return this.db.get(query);
	}
	
	public void Save(){
		
	}
	
	public void CpSave()
	{
		String query = 	"";
		try{
			this.db.getConnection().setAutoCommit(false);
			

			if(this.csId.length() > 0){
				query 	= 	"DELETE FROM ERP_CHINHSACHDUYET_CHIPHI WHERE KHOANGCHIPHI_FK = '" + this.kcpId + "' " +
							"AND CHINHSACHDUYET_FK ='" + this.csId + "'";
			
				System.out.println(query);
				if(!this.db.update(query)){
					this.msg="Lỗi dòng lệnh : "+query;
					this.db.update("ROLLBACK");		
					return;
				}
				query = "UPDATE ERP_CHINHSACHDUYET SET DIENGIAI = N'" + this.diengiai + "' ," +
						" TRANGTHAI = "+this.trangthai+
						" WHERE PK_SEQ = " + this.csId + "";
				
				System.out.println(query);
				if(!this.db.update(query)){
					this.msg="Lỗi dòng lệnh : "+query;
					this.db.update("ROLLBACK");		
					return;
				}
				
				if(this.trangthai.equals("1")){
					query = "UPDATE ERP_CHINHSACHDUYET SET TRANGTHAI = '0' " +
							"WHERE PK_SEQ != " + this.csId + " AND DONVITHUCHIEN_FK = " + this.dvthId + "";
					System.out.println(query);
					this.db.update(query);
				}
			}else{
				boolean existing = false;
				query = "SELECT PK_SEQ FROM ERP_CHINHSACHDUYET " +
						"WHERE CONGTY_FK = " + this.ctyId + " AND DONVITHUCHIEN_FK = " + this.dvthId + " AND TRANGTHAI = 1";
				ResultSet rs = this.db.get(query);
				if(rs != null){
					if(rs.next()){
						this.csId = rs.getString("PK_SEQ");
						existing = true;
						this.msg = "Chính sách duyệt mua hàng cho Đơn vị kinh doanh đã tồn tại. Dữ liệu đã được cập nhật vào chính sách đã tồn tại";
					}
				}
				
				if(!existing){
					query = "INSERT INTO ERP_CHINHSACHDUYET(DIENGIAI, DONVITHUCHIEN_FK, TRANGTHAI, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, CONGTY_FK) " +
							"VALUES(N'" + this.diengiai + "', " + this.dvthId + ", "+ this.trangthai+", " + this.Userid + ", " + this.Userid + ", " +
							"'" + this.getDateTime() + "', '" + this.getDateTime() + "', " + this.ctyId + ") ";
			
					System.out.println(query);
			
					if(!this.db.update(query)){
						this.msg="Lỗi dòng lệnh : "+query;
						this.db.update("ROLLBACK");		
						return;
					}
			
					query = "SELECT SCOPE_IDENTITY() AS ID ";
					rs = this.db.get(query);
					try{
						rs.next();
						this.csId = rs.getString("ID");
						rs.close();
					}catch(Exception e){ 
						e.printStackTrace();
						this.msg="Lỗi dòng lệnh : "+e.getMessage();
						this.db.update("ROLLBACK");		
						return;								
					}
				}
				System.out.println("CsId: " + this.csId);
			
			}
		
			System.out.println("__CHON: " + this.chon);
			System.out.println("__QUYET DINH: " + this.quyetdinh);
		
			if(this.chon != null)
			{
				for(int i = 0; i < this.chon.length; i++)
				{
					query = "INSERT INTO ERP_CHINHSACHDUYET_CHIPHI(CHINHSACHDUYET_FK, KHOANGCHIPHI_FK, CHUCDANH_FK, THUTU, QUYETDINH) " +
							"VALUES('" + this.csId + "', '" + this.kcpId + "', '" + this.chon[i] + "','0', '0')";
				
					System.out.println("1.Insert: " + query);
				
					if(!this.db.update(query)){
						this.msg="Lỗi dòng lệnh : "+query;
						this.db.update("ROLLBACK");		
						return;
					}
				}
			}
		
			if(this.quyetdinh != null)
			{
				for(int i = 0; i < this.quyetdinh.length; i++)
				{
					query = "UPDATE ERP_CHINHSACHDUYET_CHIPHI SET QUYETDINH = '1' " +
							"WHERE KHOANGCHIPHI_FK = '" + this.kcpId + "' AND CHINHSACHDUYET_FK = '" + this.csId + "' " +
							"AND CHUCDANH_FK = '" + this.quyetdinh[i] + "'";
			
					System.out.println("1-" + query);
					if(!this.db.update(query)){
						this.msg="Lỗi dòng lệnh : "+query;
						this.db.update("ROLLBACK");		
						return;				
					}
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception   er){
			this.db.update("ROLLBACK");	
			this.msg=er.getMessage();
			er.printStackTrace();
		}
	}
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBClose(){
		try{
			if(this.dvth != null) this.dvth.close();
			if(this.cs != null) this.cs.close();
			if(this.kcp != null) this.kcp.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		this.db.shutDown();
	}

	@Override
	public void SetUserId(String userid) {
		this.Userid=userid;
	}

	@Override
	public boolean isHoatDong() {
		return "1".equals(this.trangthai.trim());
	}

	@Override
	public void setHoatDong(String hoatdong) {
		this.trangthai = hoatdong;
	}

	@Override
	public ResultSet getNcc() {
		return this.ncc;
	}

	@Override
	public ResultSet getSp() {
		return this.sp;
	}

	@Override
	public ResultSet ChucdanhconlaiNcc(String sodong) {
		String query = "";
		if(this.csId.length() == 0){
			query = "Select PK_SEQ AS CDID, DIENGIAI AS CHUCDANH from ERP_CHUCDANH ";
			
		}else{
			 
			query =	"Select cd.PK_SEQ AS CDID, cd.DIENGIAI AS CHUCDANH, " +
					"ISNULL((select THUTU from ERP_CHINHSACHDUYET_NCC " +
					"		 WHERE CHUCDANH_FK = cd.PK_SEQ and SODONG = '" + sodong + "' and CHINHSACHDUYET_FK = '"+this.csId+"'),-1) as CHON, " +
					"ISNULL((select QUYETDINH from ERP_CHINHSACHDUYET_NCC " +
					"	     WHERE CHUCDANH_FK = cd.PK_SEQ and SODONG = '" + sodong + "' and CHINHSACHDUYET_FK = '"+this.csId+"'),-1) as QUYETDINH " +
					"from ERP_CHUCDANH cd ORDER BY CHON DESC ";
		}
		//System.out.println(query);
		
		return this.db.get(query);

	}

	@Override
	public ResultSet ChucdanhconlaiSp(String sodong) {
		String query = "";
		if(this.csId.length() == 0)
			query = "Select PK_SEQ AS CDID, DIENGIAI AS CHUCDANH from ERP_CHUCDANH ";
			
		else
			query = "Select cd.PK_SEQ AS CDID, cd.DIENGIAI AS CHUCDANH, " +
					"ISNULL((select THUTU from ERP_CHINHSACHDUYET_SANPHAM " +
					"		 WHERE CHUCDANH_FK = cd.PK_SEQ and SODONG = '" + sodong + "' and CHINHSACHDUYET_FK = '"+this.csId+"'),-1) as CHON, " +
					"ISNULL((select QUYETDINH from ERP_CHINHSACHDUYET_SANPHAM " +
					"		 WHERE CHUCDANH_FK = cd.PK_SEQ and SODONG = '" + sodong + "' and CHINHSACHDUYET_FK = '"+this.csId+"'),-1) as QUYETDINH " +
					"from ERP_CHUCDANH cd ORDER BY CHON DESC ";
		//System.out.println(query);
		
		return this.db.get(query);
	}

	@Override
	public void setNccChon(String[] nccchon) {
		this.NccChon = nccchon;
	}

	@Override
	public void setNccQuyetdinh(String[] nccquyetdinh) {
		this.NccQuyetdinh = nccquyetdinh;
	}

	@Override
	public void setNccId(String nccId) {
		this.nccId = nccId;
	}
	
	public int getNumberofNcc(){
		String query = "SELECT COUNT(DISTINCT NCC_FK) AS NUM FROM ERP_CHINHSACHDUYET_NCC  WHERE CHINHSACHDUYET_FK ='" + this.csId + "' ";
		ResultSet rs = this.db.get(query);
		int tmp = 0;
		try{
			rs.next();
			tmp = rs.getInt("NUM");
			rs.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		return tmp;
	}
	
	public int getNumberofSp(){
		String query = "SELECT COUNT(DISTINCT SANPHAM_FK) AS NUM FROM ERP_CHINHSACHDUYET_SANPHAM  WHERE CHINHSACHDUYET_FK ='" + this.csId + "' ";
		ResultSet rs = this.db.get(query);
		int tmp = 0;
		try{
			if (rs != null)
			{
				if (rs.next())
					tmp = rs.getInt("NUM");
				rs.close();
			}
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		return tmp;
	}
	
	public void NccSave() {
		String query = 	"";
		try{
			this.db.getConnection().setAutoCommit(false);
			
			if(this.csId.length() > 0){
				query 	= 	"DELETE FROM ERP_CHINHSACHDUYET_NCC WHERE SODONG = '" + this.kcpId + "' " +
							"AND CHINHSACHDUYET_FK ='" + this.csId + "'";
			
				System.out.println(query);
				if(!this.db.update(query)){
					this.msg="Lỗi dòng lệnh : "+query;
					this.db.update("ROLLBACK");		
					return;
				}
				query = "UPDATE ERP_CHINHSACHDUYET SET DIENGIAI = N'" + this.diengiai + "' ," +
						" TRANGTHAI = "+this.trangthai+
						" WHERE PK_SEQ = " + this.csId + "";
				
				System.out.println(query);
				if(!this.db.update(query)){
					this.msg="Lỗi dòng lệnh : "+query;
					this.db.update("ROLLBACK");		
					return;
				}

		
				if(this.trangthai.equals("1")){
					query = "UPDATE ERP_CHINHSACHDUYET SET TRANGTHAI = '0' " +
							"WHERE PK_SEQ != " + this.csId + " AND DONVITHUCHIEN_FK = " + this.dvthId + "";
					System.out.println("Cau: "+ query);
					this.db.update(query);
				}
			
			}else{
				query = "INSERT INTO ERP_CHINHSACHDUYET(DIENGIAI, DONVITHUCHIEN_FK, TRANGTHAI, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, CONGTY_FK) " +
						"VALUES(N'" + this.diengiai + "', " + this.dvthId + ", "+ this.trangthai+", " + this.Userid + ", " + this.Userid + ", " +
								"'" + this.getDateTime() + "', '" + this.getDateTime() + "', " + this.ctyId + ") ";
			
				System.out.println(query);
			
				if(!this.db.update(query)){
					this.msg="Lỗi dòng lệnh : "+query;
					this.db.update("ROLLBACK");		
					return;
				}
			
				query = "SELECT SCOPE_IDENTITY() AS ID ";
				ResultSet rs = this.db.get(query);
				try{
					rs.next();
					this.csId = rs.getString("ID");
					rs.close();
				}catch(Exception e){ 
					e.printStackTrace();
					this.msg="Lỗi dòng lệnh : "+e.getMessage();
					this.db.update("ROLLBACK");		
					return;								
				}
			
				System.out.println("CsId: " + this.csId);
			
			}
		
			System.out.println("__NCCCHON: " + this.NccChon);
			System.out.println("__NCCQUYET DINH: " + this.NccQuyetdinh);
		
			if(this.NccChon != null)
			{
				for(int i = 0; i < this.NccChon.length; i++)
				{

					query = "INSERT INTO ERP_CHINHSACHDUYET_NCC(CHINHSACHDUYET_FK, NCC_FK, CHUCDANH_FK, THUTU, QUYETDINH, SODONG) " +
								"VALUES('" + this.csId + "', '" + this.nccId + "', '" + this.NccChon[i] + "','0', '0', '" + this.kcpId + "' )";
				
					System.out.println("1.Insert: " + query);
				
					if(!this.db.update(query)){
						this.msg="Lỗi dòng lệnh : "+query;
						this.db.update("ROLLBACK");		
						return;
					}
				}
			}
		
			if(this.NccQuyetdinh != null)
			{
				for(int i = 0; i < this.NccQuyetdinh.length; i++)
				{
					query = "UPDATE ERP_CHINHSACHDUYET_NCC SET QUYETDINH = '1' " +
							"WHERE NCC_FK = '" + this.nccId + "' AND CHINHSACHDUYET_FK = '" + this.csId + "' AND SODONG = '" + this.kcpId + "' " +
							"AND CHUCDANH_FK = '" + this.NccQuyetdinh[i] + "'";
			
					System.out.println("1-" + query);
					if(!this.db.update(query)){
						this.msg="Lỗi dòng lệnh : "+query;
						this.db.update("ROLLBACK");		
						return;				
					}
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception   er){
			this.db.update("ROLLBACK");	
			this.msg=er.getMessage();
			er.printStackTrace();
		}
	}

	@Override
	public String getThutuDuyetNhaCC(String sodong) {
		String query =  "select CONVERT(VARCHAR, cs.NCC_FK) + ';' + ISNULL(cd.DIENGIAI, '') AS DIENGIAI " +
						"from ERP_CHINHSACHDUYET_NCC cs " +
						"INNER JOIN ERP_CHUCDANH cd on cs.CHUCDANH_FK = cd.PK_SEQ " +
				        "where cs.SODONG = '" + sodong + "' and cs.CHINHSACHDUYET_FK = '"+this.csId+"' order by cs.THUTU asc";
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		String kq = "";
		if(rs != null)
			try {
				while(rs.next()){
					String[] tmp = rs.getString("DIENGIAI").split(";");
					if(kq.indexOf(tmp[0]) >= 0){
						kq += " , " + tmp[1];
					}else{
						kq += tmp[0] + ";" + tmp[1];
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return kq;
	}
	@Override
	public String getThutuDuyetSp(String sodong) {
		String query = 	"select CONVERT(VARCHAR, cs.SANPHAM_FK) + ';' + cd.DIENGIAI AS DIENGIAI " +
						"from ERP_CHINHSACHDUYET_SANPHAM cs join ERP_CHUCDANH cd on cs.CHUCDANH_FK = cd.PK_SEQ " +
						"where cs.SODONG = '" + sodong + "' and cs.CHINHSACHDUYET_FK = '"+this.csId+"' order by cs.THUTU asc";
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		String kq = "";
		if(rs != null)
			try {
				while(rs.next()){
					String[] tmp = rs.getString("DIENGIAI").split(";");
					if(kq.indexOf(tmp[0]) >= 0){
						kq += " , " + tmp[1];
					}else{
						kq += tmp[0] + ";" + tmp[1];
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return kq;
	}
	@Override
	public void setSpChon(String[] spchon) {
		this.spChon = spchon;
	}

	@Override
	public void setSpQuyetdinh(String[] spquyetdinh) {
		this.spQuyetdinh = spquyetdinh;
	}

	@Override
	public void setSpId(String spId) {
		this.spId = spId;
	}

	@Override
	public void SpSave() {
		String query = 	"";
		try{
			this.db.getConnection().setAutoCommit(false);
			

			if(this.csId.length() > 0){
				query 	= 	"DELETE FROM ERP_CHINHSACHDUYET_SANPHAM WHERE SODONG = '" + this.kcpId + "' " +
							"AND CHINHSACHDUYET_FK ='" + this.csId + "'";
			
				System.out.println(query);
				if(!this.db.update(query)){
					this.msg="Lỗi dòng lệnh : "+query;
					this.db.update("ROLLBACK");		
					return;
				}
				query = "UPDATE ERP_CHINHSACHDUYET SET DIENGIAI = N'" + this.diengiai + "' ," +
						" TRANGTHAI = "+this.trangthai+
						" WHERE PK_SEQ = " + this.csId + "";
				
				System.out.println(query);
				if(!this.db.update(query)){
					this.msg="Lỗi dòng lệnh : "+query;
					this.db.update("ROLLBACK");		
					return;
				}

				if(this.trangthai.equals("1")){
					query = "UPDATE ERP_CHINHSACHDUYET SET TRANGTHAI = '0' " +
							"WHERE PK_SEQ != " + this.csId + " AND DONVITHUCHIEN_FK = " + this.dvthId + "";
					this.db.update(query);
				}
				
			}else{
				query = "INSERT INTO ERP_CHINHSACHDUYET(DIENGIAI, DONVITHUCHIEN_FK, TRANGTHAI, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, CONGTY_FK) " +
						"VALUES(N'" + this.diengiai + "', " + this.dvthId + ", "+ this.trangthai+", " + this.Userid + ", " + this.Userid + ", " +
						"'" + this.getDateTime() + "', '" + this.getDateTime() + "', " + this.ctyId + ") ";
			
				System.out.println(query);
			
				if(!this.db.update(query)){
					this.msg="Lỗi dòng lệnh : "+query;
					this.db.update("ROLLBACK");		
					return;
				}
			
				query = "SELECT SCOPE_IDENTITY() AS ID ";
				ResultSet rs = this.db.get(query);
				try{
					rs.next();
					this.csId = rs.getString("ID");
					rs.close();
				}catch(Exception e){ 
					e.printStackTrace();
					this.msg="Lỗi dòng lệnh : "+e.getMessage();
					this.db.update("ROLLBACK");		
					return;								
				}
			
				System.out.println("CsId: " + this.csId);
			
			}
		
			System.out.println("__SPCHON: " + this.spChon);
			System.out.println("__SPQUYET DINH: " + this.spQuyetdinh);
		
			if(this.spChon != null)
			{
				for(int i = 0; i < this.spChon.length; i++)
				{
					query = "INSERT INTO ERP_CHINHSACHDUYET_SANPHAM(CHINHSACHDUYET_FK, SANPHAM_FK, CHUCDANH_FK, THUTU, QUYETDINH, SODONG) " +
							"VALUES('" + this.csId + "', '" + this.spId + "', '" + this.spChon[i] + "','0', '0', '" + this.kcpId + "')";
					
					System.out.println("1.Insert: " + query);
				
					if(!this.db.update(query)){
						this.msg="Lỗi dòng lệnh : "+query;
						this.db.update("ROLLBACK");		
						return;
					}
				}
			}
		
			if(this.spQuyetdinh != null)
			{
				for(int i = 0; i < this.spQuyetdinh.length; i++)
				{
					query = "UPDATE ERP_CHINHSACHDUYET_SANPHAM SET QUYETDINH = '1' " +
							"WHERE SANPHAM_FK = '" + this.spId + "' AND CHINHSACHDUYET_FK = '" + this.csId + "' AND SODONG = '" + this.kcpId + "' " +
							"AND CHUCDANH_FK = '" + this.spQuyetdinh[i] + "'";
			
					System.out.println("1-" + query);
					if(!this.db.update(query)){
						this.msg="Lỗi dòng lệnh : "+query;
						this.db.update("ROLLBACK");		
						return;				
					}
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception   er){
			this.db.update("ROLLBACK");	
			this.msg=er.getMessage();
			er.printStackTrace();
		}
	}
}
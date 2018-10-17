package geso.traphaco.center.beans.asm.imp;
 
import java.sql.ResultSet;

import geso.traphaco.center.beans.asm.IAsmList;
import geso.traphaco.center.db.sql.dbutils;

public class AsmList implements IAsmList {
	public String asmTen;
	public String dienthoai;
	public String email;
	public String diachi;
	public String kbhId;
	public String dvkdId;
	public String kvId;
	public String trangthai;
	public String msg;
	public ResultSet kbh;
	public ResultSet kv;
	public ResultSet dvkd;
	public ResultSet asmlist;
	public dbutils db;
	
	String maFAST;
	
	public AsmList(){
		this.asmTen = "";
		this.dienthoai = "";
		this.email = "";
		this.diachi = "";
		this.kbhId = "";
		this.dvkdId = "";
		this.kvId = "";
		this.trangthai = "";
		this.msg = "";
		this.maFAST = "";
		this.db = new dbutils();
	}
	
	public String getMaFAST() {
		return maFAST;
	}

	public void setMaFAST(String maFAST) {
		this.maFAST = maFAST;
	}
	
	public String getTen(){
		return this.asmTen;
	}
	
	public void setTen(String asmTen){
		this.asmTen = asmTen;
	}
	
	public String getDienthoai(){
		return this.dienthoai;
	}
	
	public void setDienthoai(String dienthoai){
		this.dienthoai = dienthoai;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}

	public String getDiachi(){
		return this.diachi;
	}
	
	public void setDiachi(String diachi){
		this.diachi = diachi;
	}

	public String getKbhId(){
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId){
		this.kbhId = kbhId;
	}

	public String getDvkdId(){
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId){
		this.dvkdId = dvkdId;
	}

	public String getKvId(){
		return this.kvId;
	}
	
	public void setKvId(String kvId){
		this.kvId = kvId;
	}

	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}

	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getKbh(){
		return this.kbh;
	}
	
	public void setKbh(ResultSet kbh){
		this.kbh = kbh;
	}


	public ResultSet getDvkd(){
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd){
		this.dvkd = dvkd;
	}

	public ResultSet getKv(){
		return this.kv;
	}
	
	public void setKv(ResultSet kv){
		this.kv = kv;
	}

	public ResultSet getAsmlist(){
		return this.asmlist;
	}
	
	public void setAsmlist(ResultSet asmlist){
		this.asmlist = asmlist;
	}
	
	public void init(){
		/*String sql = 	"SELECT PK_SEQ AS KBHID, TEN AS KBH FROM KENHBANHANG WHERE TRANGTHAI='1' "; //AND PK_SEQ='100000' OR PK_SEQ = '100002'";
		this.kbh = this.db.get(sql);*/
		
		String sql  	=		"SELECT PK_SEQ AS KVID, TEN AS KV FROM KHUVUC WHERE TRANGTHAI='1'";
		this.kv = this.db.get(sql);
		
		sql 	= 		"SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH WHERE TRANGTHAI='1'";
		this.dvkd = this.db.get(sql);
		
		sql 	=		"SELECT isnull(ASM.maFast,'') as maFast ,  ASM.PK_SEQ AS ASMID,ASM.PK_SEQ AS MANHANVIEN , ASM.TEN AS ASMTEN, ASM.DIENTHOAI, ASM.TRANGTHAI, " + 
						"ASM.NGAYTAO, NV1.TEN AS NGUOITAO, ASM.NGAYSUA, NV2.TEN AS NGUOISUA,DVKD.DONVIKINHDOANH AS DVKD , " +
						"		(     "+
						"				STUFF   "+ 
						"			(      "+
						"					(     "+
						"					SELECT  DISTINCT TOP 100 PERCENT ' , '+ B.TEN "+
						"					FROM    ASM_KHUVUC A INNER JOIN KHUVUC B ON B.PK_SEQ=A.KHUVUC_FK "+ 
						"					where A.ASM_FK=ASM.PK_SEQ  "+  
						"					ORDER BY ' , '+  B.TEN  "+  
						"					FOR XML PATH('')     "+
						"					), 1, 2, '') + ' ' "+ 
						"		) as KV, isnull(asm.manhansu,'') as manhansu "+ 
						"FROM ASM ASM " +
						"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = ASM.NGUOITAO " +
						"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = ASM.NGUOISUA " +
						"Left JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = ASM.DVKD_FK " +
						"WHERE ASM.TRANGTHAI >= '0' ";
		
		//if (this.kbhId.length() > 0) sql = sql + "AND KBH.PK_SEQ = '" + this.kbhId + "' ";
		if (this.dvkdId.length() > 0) sql = sql + "AND DVKD.PK_SEQ = '" + this.dvkdId + "' ";
		if (this.kvId.length() > 0) sql = sql + " AND ASM.PK_sEQ IN ( SELECT  ASM_FK from  ASM_KHUVUC WHERE KHUVUC_fK  = '" + this.kvId + "'  ) ";
		if (this.trangthai.length() > 0 & !this.trangthai.equals("2")) sql = sql + "AND ASM.TRANGTHAI = '" + this.trangthai + "' ";
		if (this.asmTen.length() > 0) sql = sql + "AND ASM.TEN LIKE '%" + this.asmTen + "%' ";
		if (this.dienthoai.length() > 0) sql = sql + "AND ASM.DIENTHOAI LIKE '%" + this.dienthoai + "%' ";
		if (this.maFAST.length() > 0) sql = sql + "AND ASM.maFAST LIKE '%" + this.maFAST + "%' ";
		
		sql = sql + " ORDER BY  DVKD ASC, KV ASC, ASMTEN ASC, TRANGTHAI DESC";
		
		System.out.println(sql);
		
		this.asmlist = this.db.get(sql);
		
		
	}
	
	public void Delete(String asmid){
		try{
			 this.db.getConnection().setAutoCommit(false);
			String sql = "DELETE FROM ASM_KHUVUC WHERE ASM_FK = '" + asmid + "' ";
			if(!this.db.update(sql))
			{
				this.db.update("rollback");
				this.msg="Khong The Thuc Hien Xoa ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
				return ;
			}
		
			sql = "SELECT COUNT(*) AS NUM FROM ASM_KHUVUC WHERE ASM_FK = '" + asmid + "'";
			ResultSet rs = this.db.get(sql);
			rs.next();
		
			if(rs.getString("NUM").equals("0")){
				sql = "DELETE FROM ASM WHERE PK_SEQ ='" + asmid + "'";
				if(!this.db.update(sql))
				{
					this.db.update("rollback");
					this.msg="Khong The Thuc Hien Xoa ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
					return ;
				}
			}
			if(rs!=null)
				rs.close();
			 this.db.getConnection().commit();
			 this.db.getConnection().setAutoCommit(true);
		}catch(Exception e)
		{
			this.db.update("rollback");
			this.msg="Khong The Thuc Hien Xoa ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
			return ;
		}
	}
	
	public void DBClose(){
		try{
			if(this.asmlist != null) this.asmlist.close();
			if(this.dvkd != null) this.dvkd.close();
			if(this.kbh != null) this.kbh.close();
			if(this.kv != null) this.kv.close();
			if(this.db != null) this.db.shutDown();
		}catch(Exception e){}
	}
}

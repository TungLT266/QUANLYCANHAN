package geso.traphaco.erp.beans.khoasothang.imp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.khoasothang.IErptiendien;
import geso.traphaco.erp.beans.khoasothang.IErptinhgiadongluc;
import geso.traphaco.erp.beans.khoasothang.IErptinhgiadongluclist;
import geso.traphaco.erp.db.sql.dbutils;

public class Erptinhgiadongluclist implements IErptinhgiadongluclist 
{
	
	private String Id;
	private String msg;
	private String UserId;
	private ResultSet rsdongluclist;
	private int thang;
	private int nam;
	private String trangthai;
	dbutils db;
	public Erptinhgiadongluclist(){
		trangthai="";
		this.msg="";
		db=new dbutils();
	}
	@Override
	public int getThang() {
		// TODO Auto-generated method stub
		return this.thang;
	}

	@Override
	public void setThang(int _thang) {
		// TODO Auto-generated method stub
		this.thang=_thang;
	}

	@Override
	public int getNam() {
		// TODO Auto-generated method stub
		return this.nam;
	}

	@Override
	public void setNam(int _nam) {
		// TODO Auto-generated method stub
		this.nam=_nam;
	}

 
	@Override
	public void Init() {
		// TODO Auto-generated method stub
		try{
			String query=	" SELECT DL.PK_SEQ as SOPHIEU,DL.NGAYSUA,DL.NGAYTAO,DL.THANG,DL.NAM,NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA, "+
							" isnull(DL.TRANGTHAI,'0') as TRANGTHAI "+  
							" FROM ERP_TINHGIADONGLUC DL LEFT JOIN NHANVIEN NS ON NS.PK_SEQ=DL.NGUOISUA "+
							" LEFT JOIN NHANVIEN NT ON NT.PK_SEQ=DL.NGUOITAO where 1=1 ";
			this.rsdongluclist=db.get(query);
			
			if(this.thang >0 ){
				query=query+" and dl.thang= "+this.thang;
			}
			if(this.nam >0 ){
				query=query+" and dl.nam= "+this.nam;
			}
			
			
			
				
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.UserId;
	}

	@Override
	public void setUserId(String UserId) {
		// TODO Auto-generated method stub
		this.UserId=UserId;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return  this.msg;
	}

	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.msg=msg;
	}

	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
		try{
			db.shutDown();
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String IdDongHoDien) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String gettrangthai() {
		// TODO Auto-generated method stub
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		// TODO Auto-generated method stub
		this.trangthai=trangthai;
	}

	@Override
	public ResultSet GetRsDongluc() {
		// TODO Auto-generated method stub
		return this.rsdongluclist;
	}

	@Override
	public void setRsDongluc(ResultSet RsDongluc) {
		// TODO Auto-generated method stub
		this.rsdongluclist=RsDongluc;
	}
	@Override
	public String Duyet(String id) {
		// TODO Auto-generated method stub
		try{
			String query="UPDATE  ERP_TINHGIADONGLUC SET TRANGTHAI= 1 WHERE PK_SEQ="+id ;
			if(!db.update(query)){
				return "Không thể thực hiện câu lệnh duyệt";
			}
			
			
		}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
		}
		return "";
	}
	@Override
	public String huybo(String id) {
		// TODO Auto-generated method stub
		try{
			String query="UPDATE  ERP_TINHGIADONGLUC SET TRANGTHAI= 2 WHERE PK_SEQ="+id ;
			if(!db.update(query)){
				return "Không thể thực hiện câu lệnh duyệt";
			}
			
			
		}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
		}
		return "";
	}
	@Override
	public String Boduyet(String id) {
		try{
			//NẾU ĐÃ CÓ TÍNH GIÁ THÀNH RỒI THÌ KHÔNG ĐƯỢC BỎ DUYỆT
			String query="SELECT PK_sEQ FROM ERP_TINHGIAVON WHERE THANG= (SELECT THANG FROM ERP_TINHGIADONGLUC WHERE PK_sEQ="+id+") AND NAM= (SELECT NAM FROM ERP_TINHGIADONGLUC where pk_Seq="+id+") ";
			
			ResultSet rs=db.get(query);
			if(rs.next()){
				rs.close();
				return "Tính giá động lực tháng này đã dùng để tính giá thành, không thể hủy chứng từ này";
			}
 
			query="UPDATE  ERP_TINHGIADONGLUC SET TRANGTHAI= 0 WHERE PK_SEQ="+id ;
			if(!db.update(query)){
				return "Không thể thực hiện câu lệnh duyệt";
			}
			
			
		}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
		}
		return "";
	}
	 
	
}

package geso.traphaco.erp.beans.khoasothang.imp;
import java.sql.ResultSet;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.khoasothang.IErpkhoasoketoanlist;
 

public class  ErpKhoasoketoanlist implements IErpkhoasoketoanlist 
{
	
	private String Id;
	private String msg;
	private String UserId;
	private ResultSet rsdongluclist;
	private int thang;
	private int nam;
	private String trangthai;
	dbutils db;
	String nppid="";
	
	ResultSet rslist;
	public ErpKhoasoketoanlist(){
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
			Utility util=new Utility();
		 
			
			String query=	" SELECT DL.PK_SEQ as SOPHIEU,DL.NGAYSUA,DL.NGAYTAO,DL.THANGKS as thang,DL.NAM,NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA, "+
							" isnull(DL.TRANGTHAI,'0') as TRANGTHAI  "+
							" FROM  ERP_KHOASOKETOAN_LIST DL LEFT JOIN NHANVIEN NS ON NS.PK_SEQ=DL.NGUOISUA"+  
							" LEFT JOIN NHANVIEN NT ON NT.PK_SEQ=DL.NGUOITAO where 1=1 ";
							  
			if(this.thang >0 ){
				query=query+" and dl.thang= "+this.thang;
			}
			if(this.nam >0 ){
				query=query+" and dl.nam= "+this.nam;
			}
			
			
			this.rslist=db.get(query);
				
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
	public void setTrangthai(String trangthai) {
		// TODO Auto-generated method stub
		this.trangthai=trangthai;
	}
	@Override
	public String getTrangthai() {
		// TODO Auto-generated method stub
		return this.trangthai;
	}
	@Override
	public boolean Save() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean Edit() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public ResultSet getRsList() {
		// TODO Auto-generated method stub
		return rslist;
	}

	 
	
}

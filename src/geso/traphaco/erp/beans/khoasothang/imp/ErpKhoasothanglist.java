package geso.traphaco.erp.beans.khoasothang.imp;
import java.sql.ResultSet;
 
 
import geso.traphaco.erp.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.khoasothang.IErpkhoasothanglist;

public class  ErpKhoasothanglist implements IErpkhoasothanglist 
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
	public ErpKhoasothanglist(){
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
		 
			String query=	" SELECT DL.PK_SEQ AS SOPHIEU, DL.NGAYTAO,DL.THANGKS AS THANG,DL.NAM,NT.TEN AS NGUOITAO, "+ 
							" ISNULL(DL.TRANGTHAI,'0') AS TRANGTHAI   FROM  ERP_KHOASOTHANG DL "+ 
							" LEFT JOIN NHANVIEN NT ON NT.PK_SEQ=DL.NGUOITAO WHERE 1=1 ";
						
			
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
		return this.Id;
	}

	@Override
	public void setId(String IdDongHoDien) {
		// TODO Auto-generated method stub
		this.Id=IdDongHoDien;
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
		try{
			
			
			return true;
		}catch(Exception err){
			err.printStackTrace();
			return false;
		}
	}
	@Override
	public ResultSet getRsList() {
		// TODO Auto-generated method stub
		return rslist;
	}
	@Override
	public boolean MoKhoaSoKho() {
		// TODO Auto-generated method stub
		try{
			
			Utility util=new Utility();
			  this.nppid=util.getIdNhapp(this.UserId);
			  db.getConnection().setAutoCommit(false);
			
			String query="select top 1  thangks  thang , (nam) as nam  from ERP_KHOASOTHANG order by nam desc,THANGKS desc ";
			System.out.println(query);
			ResultSet rs=db.get(query);
			
			if(rs.next()){
				String thang=rs.getString("thang");
				String nam=rs.getString("nam");
				 
				query="SELECT PK_SEQ FROM ERP_KHOASOTHANG WHERE thangks="+thang+" and nam="+nam+" and PK_SEQ="+this.Id;
				System.out.println(query);
				ResultSet rs1=db.get(query);
				if(rs1.next()){ 
					
					 boolean bien =this.Revert_KeToan("Bút toán chênh lệch khi khóa sổ kho", this.Id, "", thang, nam);
					 if(!bien){
						 	 
							db.getConnection().rollback();
							return false;
					 }
					
					query="DELETE  ERP_KHOASOTHANG  WHERE PK_SEQ="+this.Id;
					if(!db.update(query)){
						this.msg="Không thể cập nhật mở khóa sổ kho "+query;
						db.getConnection().rollback();
						return false;
						
					}
					
					query="DELETE ERP_KHOASOTHANG WHERE THANGKS="+ thang+" AND NAM="+ nam ;
					if(!db.update(query)){
				  		db.update("rollback");
						this.msg="không thể cập nhật tồn kho tháng, vui lòng báo admin để được trợ giúp : "+query;
						return false;
				  	}
					
					query=" DELETE ERP_TONKHOTHANG_CHITIET WHERE THANG="+ thang+" AND NAM="+ nam ;
					if(!db.update(query)){
				  		db.update("rollback");
						this.msg="không thể cập nhật tồn kho tháng, vui lòng báo admin để được trợ giúp : "+query;
						return false;
						
				  	}
					query="DELETE ERP_TONKHOTHANG  WHERE THANG="+ thang+" AND NAM="+ nam ;
					if(!db.update(query)){
				  		db.update("rollback");
						this.msg="không thể cập nhật tồn kho tháng, vui lòng báo admin để được trợ giúp : "+query;
						return false;
						
				  	}
					
					 
						
					
				}else{
					this.msg="Chỉ mở khóa sổ tháng gần nhất";
					return false;
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		}catch(Exception err){
			db.update("rollback");
			
			err.printStackTrace();
			return false;
		}
	}

	private boolean Revert_KeToan(String loaichungtu, String sochungtu, String idlog,String thang,String nam)
	{
		try 
		{
			
		//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
		String query =  " select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
					    " from ERP_PHATSINHKETOAN " +
					    " where LOAICHUNGTU = N'" + loaichungtu.trim() + "' and SOCHUNGTU = '" + sochungtu + "' ";
		 
			ResultSet rsPSKT = db.get(query);
			while(rsPSKT.next())
			{
				String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
				String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
				double NO = rsPSKT.getDouble("NO");
				double CO = rsPSKT.getDouble("CO");
				double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
				
				//NEU LA CO THI BAY GIO GHI GIAM CO LAI
				if( NO > 0 )
				{
					query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
							" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
				}
				else
				{
					query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
							" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
				}
				
				//System.out.println("1.REVERT NO-CO: " + query);
				
				if(db.updateReturnInt(query)<0)
				{
					this.msg = "KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
					return false;
				}
				
			}
			rsPSKT.close();
			
	 
			
			//HỦY KẾ TOÁN ĐÃ GHI NHẬN
			query = " DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'"+loaichungtu+"' and SOCHUNGTU = '"+sochungtu+"'";	
			if(!db.update(query))
			{
				msg = "Không thể hủy ERP_PHATSINHKETOAN " + query;
				return false;
			}			
			return true;
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
			
		}
	 
	
		 
	}
 
 
 	
	
}

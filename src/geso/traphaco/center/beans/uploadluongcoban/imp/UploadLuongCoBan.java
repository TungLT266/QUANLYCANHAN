

package geso.traphaco.center.beans.uploadluongcoban.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import geso.traphaco.center.beans.uploadluongcoban.IUploadLuongCoBan;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

public class UploadLuongCoBan implements IUploadLuongCoBan {

	
	public String isDisplay = "0";

	String UserID;
	String Id;
	int Thang;
	int Nam;
	String NgayTao;
	String NguoiTao;
	String NguoiSua;
	String NgaySua;
	String DienGiai;
	String Message;
	String TrangThai;
	ResultSet uploadluongcobanRs ;
	ResultSet uploadluongcobanChiTietRs ;
	dbutils db;
	public UploadLuongCoBan(){
		this.isDisplay = "0";
		this.Id = "";
		this.NguoiTao = "";
		this.NguoiSua = "";
		this.NgayTao ="";
		this.NgaySua ="";
		this.Message="";
		this.DienGiai="";
		this.TrangThai = "1";
		db=new dbutils();
	}

	
	public UploadLuongCoBan(String id)
	{
		db=new dbutils();
		String  sql_getdata="";

		sql_getdata="SELECT   c.trangthai,C.PK_SEQ, C.THANG, C.NAM, C.DIENGIAI,  C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
		"NS.TEN AS NGUOISUA FROM uploadluongcoban AS C " +
		"INNER JOIN "+
		"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ where c.trangthai!='2' and c.pk_seq="+ id;

		System.out.println("sql_getdata"+sql_getdata);

		try{
			ResultSet rsUploadLuongCoBan=	db.get(sql_getdata);
			if(rsUploadLuongCoBan!=null)
			{
				while(rsUploadLuongCoBan.next()){
					this.setID(rsUploadLuongCoBan.getString("PK_SEQ"));
					this.setThang(rsUploadLuongCoBan.getInt("THANG"));
					this.setNam(rsUploadLuongCoBan.getInt("NAM"));
					this.setNgayTao(rsUploadLuongCoBan.getString("NGAYTAO"));
					this.setNgaySua(rsUploadLuongCoBan.getString("NGAYSUA"));
					this.setDienGiai(rsUploadLuongCoBan.getString("DIENGIAI"));
					this.setNguoiTao(rsUploadLuongCoBan.getString("NGUOITAO"));
					this.setNguoiSua(rsUploadLuongCoBan.getString("NGUOISUA"));
					this.setTrangThai(rsUploadLuongCoBan.getString("trangthai"));
				}
			}
			rsUploadLuongCoBan.close();													
		
			String query = "select b.pk_seq as manv , b.ten as nhanvien,luongcoban " +
					" from uploadluongcoban_SR a inner join daidienkinhdoanh b on a.nhanvien_fk = b.pk_seq where a.uploadluongcoban_fk = '"+this.Id+"'" ;

		
			uploadluongcobanChiTietRs = db.get(query);
		}catch(Exception er){
			this.Message="Error rsUploadLuongCoBan.java - line : 88- detail error :"+ er.toString();
			System.out.println("Error Class name : rsUploadLuongCoBan.JAVA- LINE 216 :STRING SQL" + er.toString());
		}
		this.Message="";
		
		
		
	}
	
	public String getDisplay() {
		return isDisplay;
	}
	public void setDisplay(String loai) {
		this.isDisplay = loai;
	}
	
	public ResultSet getLuongkhacChiTietRs() {
		return uploadluongcobanChiTietRs;
	}
	public void setLuongkhacChiTietRs(String uploadluongcobanChiTietRs) {
		this.uploadluongcobanChiTietRs =db.get(uploadluongcobanChiTietRs);
	}


	public void setLuongkhacRs(String uploadluongcobanRs) {
		if(uploadluongcobanRs.equals(""))
		{
			uploadluongcobanRs = "SELECT   c.trangthai,C.PK_SEQ, C.THANG, C.NAM, C.DIENGIAI, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
			"NS.TEN AS NGUOISUA FROM uploadluongcoban AS C INNER JOIN "+
			"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
			"where 1=1 ";
			uploadluongcobanRs += "\n order by  C.PK_SEQ desc";
		}

		this.uploadluongcobanRs = db.get(uploadluongcobanRs);
	}
	public ResultSet getLuongkhacRs() {
		return uploadluongcobanRs;
	}


	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub
		this.Id=id;	
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return Id;
	}

	@Override
	public void setThang(int thang) {
		// TODO Auto-generated method stub
		this.Thang=thang;
	}

	@Override
	public int getThang() {
		// TODO Auto-generated method stub
		return this.Thang;
	}

	@Override
	public void setNam(int nam) {
		// TODO Auto-generated method stub
		this.Nam=nam;
	}

	@Override
	public int getNam() {
		// TODO Auto-generated method stub
		return this.Nam;
	}



	@Override
	public void setNgayTao(String ngaytao) {
		// TODO Auto-generated method stub
		this.NgayTao =ngaytao;
	}

	@Override
	public String getNgayTao() {
		// TODO Auto-generated method stub
		return this.NgayTao;
	}

	@Override
	public void setNgaySua(String ngaysua) {
		// TODO Auto-generated method stub
		this.NgaySua=ngaysua;
	}

	@Override
	public String getNgaySua() {
		// TODO Auto-generated method stub
		return this.NgaySua;
	}

	@Override
	public void setDienGiai(String diengiai) {
		// TODO Auto-generated method stub
		this.DienGiai=diengiai;
	}

	@Override
	public String getDienGiai() {
		// TODO Auto-generated method stub
		return this.DienGiai;
	}
	public boolean KiemTraHopLe(){

		if(this.NguoiTao.toString().equals("null") || this.NguoiTao.equals("")){
			this.Message="Ten Nguoi Dung Khong Hop Le,Vui Long Dang Xuat De Thu Lai!";
			return false;
		}

		if(this.Thang==0){
			this.Message="Vui Long Chon Thang";
			return false;
		}
		if(this.Nam==0){
			this.Message="Vui Long Chon Nam";
			return false;
		}

		return true;
	}





	public void setUserId(String userid) {
		// TODO Auto-generated method stub
		this.UserID=userid;
	}


	public String getUserId() {
		// TODO Auto-generated method stub
		return this.UserID;
	}





	@Override
	public void setNguoiTao(String nguoitao) {
		// TODO Auto-generated method stub
		this.NguoiTao=nguoitao;		
	}

	@Override
	public String getNguoiTao() {
		// TODO Auto-generated method stub
		return this.NguoiTao;
	}

	@Override
	public void setNguoiSua(String nguoisua) {
		// TODO Auto-generated method stub
		this.NguoiSua=nguoisua;
	}

	@Override
	public String getNguoiSua() {
		// TODO Auto-generated method stub
		return this.NguoiSua;
	}

	@Override
	public void setMessage(String strmessage) {
		// TODO Auto-generated method stub
		this.Message=strmessage;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.Message;
	}


	@Override
	public void setTrangThai(String trangthai) {
		// TODO Auto-generated method stub
		this.TrangThai=trangthai;
	}
	@Override
	public String getTrangThai() {
		// TODO Auto-generated method stub
		return this.TrangThai;
	}

	public boolean CreateUploadLuongCoBan(String values) 
	{
		try
		{
			// TODO Auto-generated method stub
			if(!KiemTraHopLe()){
				return false;
			}
			db.getConnection().setAutoCommit(false);

			//kiem tra xenm chi tieu trong thoi gian nay da co chua
			String sql="select pk_seq from UploadLuongCoBan where thang= " +this.Thang+" and nam="+ this.Nam +"  and trangthai!=2";
			System.out.println("sql : "+sql);
			ResultSet rs_check=db.get(sql);
			if(rs_check!=null){
				if(rs_check.next()){//co du lieu
					this.setMessage("Lương - giảm trừ Trong Thang Da Thiet Lap");
					rs_check.close();
					return false;
				}

			}	

			String error="";


			sql="select ma from ("+values+")data  where  ma not in (select pk_seq from daidienkinhdoanh where TRANGTHAI= 1)";
			System.out.println("chucvu='1' " +sql);
			rs_check=db.get(sql);
			error="";
			if(rs_check!=null){
				while (rs_check.next()){//co du lieu

					error =error+","+rs_check.getString("ma");
				}
				rs_check.close();
			}
			if(error.length()>0){
				this.setMessage("Vui Lòng kiểm tra file Upload,Có một số mã nhân viên bán hàng không tồn tại :" + error);
				return false;
			}
			sql="select ma from ("+values+")data  where  group by ma having  COUNT(ma) >1 ";
			System.out.println("chucvu='1' " +sql);
			rs_check=db.get(sql);
			error="";
			if(rs_check!=null){
				while (rs_check.next()){//co du lieu

					error =error+","+rs_check.getString("ma");
				}
				rs_check.close();
			}
			if(error.length()>0){
				this.setMessage("Vui Lòng kiểm tra file Upload,Có một số mã nhân viên bán hàng bị trùng:" + error);
				return false;
			}
			

			// thuc hien insert
			sql="insert into UploadLuongCoBan(diengiai,THANG,NAM,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TRANGTHAI) " +
			"values (N'"+this.DienGiai+"',"+this.Thang+","+this.Nam+",'"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"','0')";
			System.out.println("save chi tieu luong thuong " +sql);
			if(!db.update(sql)){

				this.Message="Loi :"+sql;		
				db.update("rollback");
				return false;
			}		
			//thuc hien insert chi tiet
			String query = "select SCOPE_IDENTITY() as dhId";
			ResultSet rsDh = db.get(query);	
			try
			{
				rsDh.next();
				this.setID(rsDh.getString("dhId"));
			}catch(Exception ex){
				ex.printStackTrace();
				return false;
			}

			// chen SR
			sql="  insert uploadluongcoban_SR(uploadluongcoban_fk,thang,nam,NhanVien_FK,luongcoban) " +
			"select '"+this.Id+"','"+this.Thang+"','"+this.Nam+"',ma,luongcoban from  ("+values+")data ";
			if(!db.update(sql))
			{
				System.out.println("save SR: " +sql);
				this.Message="Loi :"+sql;			
				db.update("rollback");
				return false;
			}	
	
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){	
			er.printStackTrace();
			db.update("rollback");
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;			
		}
		return true;
	}

	public boolean UpdateUploadLuongCoBan(String values)
	{
		try
		{
			// TODO Auto-generated method stub
			if(!KiemTraHopLe()){
				return false;
			}
			db.getConnection().setAutoCommit(false);

			//kiem tra xenm chi tieu trong thoi gian nay da co chua
			String sql="select pk_seq from UploadLuongCoBan where thang= " +this.Thang+" and nam="+ this.Nam +"  and trangthai!=2 and pk_seq !='"+this.Id+"' ";
			System.out.println("sql : "+sql);
			ResultSet rs_check=db.get(sql);
			if(rs_check!=null){
				if(rs_check.next()){//co du lieu
					this.setMessage("Lương - giảm trừ Trong Thang Da Thiet Lap");
					rs_check.close();
					return false;
				}

			}	

			String error="";


			sql="select ma from ("+values+")data  where  ma not in (select pk_seq from daidienkinhdoanh where TRANGTHAI= 1)";
			System.out.println("chucvu='1' " +sql);
			rs_check=db.get(sql);
			error="";
			if(rs_check!=null){
				while (rs_check.next()){//co du lieu

					error =error+","+rs_check.getString("ma");
				}
				rs_check.close();
			}
			if(error.length()>0){
				this.setMessage("Vui Lòng kiểm tra file Upload,Có một số mã nhân viên bán hàng không tồn tại :" + error);
				return false;
			}
			
			sql="select ma from ("+values+")data  where  group by ma having  COUNT(ma) >1 ";
			System.out.println("chucvu='1' " +sql);
			rs_check=db.get(sql);
			error="";
			if(rs_check!=null){
				while (rs_check.next()){//co du lieu

					error =error+","+rs_check.getString("ma");
				}
				rs_check.close();
			}
			if(error.length()>0){
				this.setMessage("Vui Lòng kiểm tra file Upload,Có một số mã nhân viên bán hàng bị trùng:" + error);
				return false;
			}
			

			// thuc hien insert
			sql="update UploadLuongCoBan set diengiai = N'"+this.DienGiai+"'" +
			",THANG ='"+this.Thang+"'" +
			",NAM = '"+this.Nam+"'" +
			",NGAYSUA = '"+this.NgaySua+"',NGUOISUA = '"+this.NguoiSua+"'" +
			" where trangthai = 0 and pk_seq = '"+this.Id+"'" ;
			System.out.println("save chi tieu luong thuong " +sql);
			if(db.updateReturnInt(sql)!= 1){

				this.Message="Loi :"+sql;		
				db.update("rollback");
				return false;
			}		

			sql = "delete uploadluongcoban_SR where uploadluongcoban_fk ='"+this.Id+"'";
			db.update(sql);


			// chen SR
			sql="  insert uploadluongcoban_SR(uploadluongcoban_fk,thang,nam,NhanVien_FK,luongcoban) " +
			"select '"+this.Id+"','"+this.Thang+"','"+this.Nam+"',ma,luongcoban from  ("+values+")data ";
			if(!db.update(sql))
			{
				System.out.println("save SR: " +sql);
				this.Message="Loi :"+sql;			
				db.update("rollback");
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){	
			er.printStackTrace();
			db.update("rollback");
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;			
		}
		return true;
	}
	
	public boolean chotUploadLuongCoBan()
	{
		String  query = "update UploadLuongCoBan set trangthai =1 where  pk_seq = '"+this.Id+"' and trangthai = 0 ";
		
		return db.updateReturnInt(query) > 0 ? true  : false;
	}

	public boolean UnchotUploadLuongCoBan()
	{
		String  query = "update UploadLuongCoBan set trangthai =0 where  pk_seq = '"+this.Id+"' and trangthai = 1 ";		
		return db.updateReturnInt(query) > 0 ? true  : false;
	}
	public boolean DeleteUploadLuongCoBan()
	{
		String  query = "update UploadLuongCoBan set trangthai =2 where  pk_seq = '"+this.Id+"' and trangthai = 0";		
		return db.updateReturnInt(query) > 0 ? true  : false;
	}
	
	
	@Override
	public void closeDB() {
		// TODO Auto-generated method stub
		try{
			this.uploadluongcobanRs.close();
			if(this.db!=null){
				db.shutDown();
			}
		}catch(Exception er){

		}
	}




}

package geso.traphaco.erp.beans.marquette.imp;
import java.sql.ResultSet;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.marquette.IMarquetteUpdate;
import geso.traphaco.erp.db.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class MarquetteUpdate extends Phan_Trang implements IMarquetteUpdate  {
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String ten;
	String masp;
	String tungay;
	String denngay;
	String trangthai;
	String diengiai;
	String nguoitao;
	String nguoisua;
	String ngaytao;
	String ngaysua;
	String msg;
	String spId;
	String spTen;
	String sodangky;
	String quycach;
	ResultSet spList;
	dbutils db;
	public MarquetteUpdate(String[] param) {
		this.db = new dbutils();
		this.id = param[0];
		this.ten = param[1];
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.spId = param[8];
		this.spTen = param[9];
		this.tungay = param[10];
		this.denngay = param[11];
		this.sodangky=param[12];
		this.quycach=param[13];
		this.msg = "";
		this.spList = createspList();
		this.db = new dbutils();
	}
	public MarquetteUpdate(String id) {
		this.db = new dbutils();
		this.id = id;
		this.ten = "";
		this.diengiai = "";
		this.trangthai = "1";
		this.ngaytao =  "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua ="";
		this.spId = "";
		this.spTen = "";
		this.msg = "";
		this.tungay="";
		this.denngay="";
		this.sodangky="";
		this.quycach="";
		this.spList = createspList();
		if(id.length() > 0)
			this.init();
	
	}
	
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.userId;
	}

	@Override
	public void setUserId(String _userId) {
		// TODO Auto-generated method stub
		this.userId=_userId;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(String _id) {
		// TODO Auto-generated method stub
		this.id=_id;
	}

	@Override
	public String getTungay() {
		// TODO Auto-generated method stub
		return this.tungay;
	}

	@Override
	public void setTungay(String _tungay) {
		// TODO Auto-generated method stub
		this.tungay=_tungay;
	}

	@Override
	public String getDenngay() {
		// TODO Auto-generated method stub
		return this.denngay;
	}

	@Override
	public void setDenngay(String _denngay) {
		// TODO Auto-generated method stub
		this.denngay=_denngay;
	}

	@Override
	public String getDiengiai() {
		// TODO Auto-generated method stub
		return this.diengiai;
	}

	@Override
	public void setDiengiai(String _diengiai) {
		// TODO Auto-generated method stub
		this.diengiai=_diengiai;
	}

	@Override
	public String getTrangthai() {
		// TODO Auto-generated method stub
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String _trangthai) {
		// TODO Auto-generated method stub
		this.trangthai=_trangthai;
	}
	
	@Override
	public String getTen() {
		// TODO Auto-generated method stub
		return this.ten;
	}

	@Override
	public void setTen(String _mamk) {
		// TODO Auto-generated method stub
		this.ten=_mamk;
	}

	@Override
	public String getspId() {
		// TODO Auto-generated method stub
		return this.spId;
	}

	@Override
	public void setspId(String _spId) {
		// TODO Auto-generated method stub
		this.spId=_spId;
	}

	@Override
	public void setMessage(String _msg) {
		// TODO Auto-generated method stub
		this.msg=_msg;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.msg;
	}

	@Override
	public ResultSet getspList() {
		// TODO Auto-generated method stub
		return this.spList;
	}

	@Override
	public void setspList(ResultSet _dmhlist) {
		// TODO Auto-generated method stub
		this.spList=_dmhlist;
	}

	private void init() {
		// TODO Auto-generated method stub
		String query = " select a.pk_seq as id, a.ma as ten,a.tungay,a.denngay,a.diengiai,a.sodangky,a.quycach,a.trangthai, a.sanpham_fk, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from marquette a, nhanvien b, nhanvien c ";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq= '"+ this.id + "' "; 
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.ten = rs.getString("ten");
        	this.diengiai = rs.getString("diengiai");
        	this.spId = rs.getString("sanpham_fk");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	this.tungay=rs.getString("tungay");
        	this.denngay=rs.getString("denngay");
        	this.sodangky=rs.getString("sodangky");
        	this.quycach=rs.getString("quycach");
       	}catch(Exception e){ e.printStackTrace();}
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	

	private boolean CheckExistName() {
		try {
			if(this.id.trim().length() >0){
				String sql2 = "select count(*) as s2 from marquette where ma = N'" + this.ten + "' "
						+ " and pk_seq <> " + this.id.trim();
				ResultSet rs2 = this.db.get(sql2);
				int s2 = 0;
				if (rs2 != null) {
					if (rs2.next()) {
						s2 = rs2.getInt("s2");
					}
				}
				if(s2 > 0){
					return false;
				}
			} else{
				String sql = "select count(*) as sl from marquette where ma = N'" + this.ten + "'";
				ResultSet rs = this.db.get(sql);
				int sl = 0;
				if (rs != null) {
					if (rs.next()) {
						sl = rs.getInt("sl");
					}
				}
				if(sl >0){
					return false;
				}
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean create() {
		// TODO Auto-generated method stub
		try
		{
			//kiểm tra đã tồn tại chưa?
			boolean check = CheckExistName();
			if(check == false){
				this.msg =" Đã trùng mã marquette, vui lòng chọn mã khác.";
				return false;
			}
			
			this.db.getConnection().setAutoCommit(false);
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			
			String command = "insert into marquette(ma, diengiai, trangthai, sanpham_fk,tungay,denngay, ngaytao, ngaysua,sodangky,quycach, nguoitao, nguoisua)" +
							 " values("+"N'" + this.ten + "',N'" + this.diengiai + "','" + this.trangthai + "','" + this.spId+"','" +this.tungay + "','"+this.denngay+ "','" + this.ngaytao + "','" + this.ngaytao + "','"+this.sodangky + "',N'"+this.quycach +"','"  + this.userId + "','" + this.userId + "')"; 
			
			if (!db.update(command))
			{
				this.msg = "Khong the tao moi maket: " + command;		
				this.db.update("rollback");
				return false;
			}
			
			ResultSet rs = db.get("select SCOPE_IDENTITY() as id");
		
			rs.next();
			rs.close();
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		}
		catch(Exception er)
		{
			this.msg="Khong The Them marquette Nay , Loi Dong Lenh Sau :" + er.toString();
			this.db.update("rollback");
			return false;
		}
		return true;
		
}
	

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		if (this.db != null) 
			this.db.shutDown();
		
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		try {
			// kiểm tra đã tồn tại chưa?
			boolean check = CheckExistName();
			if (check == false) {
				this.msg = " Đã trùng mã marquette, vui lòng chọn mã khác.";
				return false;
			}
			this.db.getConnection().setAutoCommit(false);
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;

			String command = "update marquette set ma='" + this.ten + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai
					+ "', ngaysua = '" + this.ngaysua + "',sanpham_fk = '" + this.spId + "',  tungay = '" + this.tungay + "', denngay = '" + this.denngay + "', sodangky = '"
					+ this.sodangky + "', quycach = N'" + this.quycach + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";

			if (!this.db.update(command)) {
				this.msg = "Khong the cap nhat: " + command;
				this.db.update("rollback");
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	@Override
	public String getNgaytao() {
		// TODO Auto-generated method stub
		return this.ngaytao;
	}
	@Override
	public void setNgaytao(String ngaytao) {
		// TODO Auto-generated method stub
		this.ngaytao=ngaytao;
	}
	@Override
	public String getNguoitao() {
		// TODO Auto-generated method stub
		return nguoitao;
	}
	@Override
	public void setNguoitao(String nguoitao) {
		// TODO Auto-generated method stub
		this.nguoitao=nguoitao;
	}
	@Override
	public String getNgaysua() {
		// TODO Auto-generated method stub
		return this.ngaysua;
	}
	@Override
	public void setNgaysua(String ngaysua) {
		// TODO Auto-generated method stub
		this.ngaysua=ngaysua;
	}
	@Override
	public String getNguoisua() {
		// TODO Auto-generated method stub
		return nguoisua;
	}
	@Override
	public void setNguoisua(String nguoisua) {
		// TODO Auto-generated method stub
		this.nguoisua=nguoisua;
	}
	@Override
	public String getspTen() {
		// TODO Auto-generated method stub
		return this.spTen;
	}
	@Override
	public void setspTen(String _spten) {
		// TODO Auto-generated method stub
		this.spTen=_spten;
	}
	@Override
	public ResultSet createspList() {
		// TODO Auto-generated method stub
		String query = " select distinct a.pk_seq, a.ma + '-' + a.ten as ten " +
					   " from erp_sanpham a left join ERP_LOAISANPHAM b on a.LOAISANPHAM_FK = b.PK_SEQ" +
					   " where a.trangthai='1' and ((b.MA = 'BB')  or (b.MA  like '%NL%')) ";
		return this.db.get(query);
	}
	@Override
	public String getSodangky() {
		// TODO Auto-generated method stub
		return this.sodangky;
	}
	@Override
	public void setSodangky(String _sodangky) {
		// TODO Auto-generated method stub
		this.sodangky=_sodangky;
	}
	@Override
	public String getquycach() {
		// TODO Auto-generated method stub
		return this.quycach;
	}
	@Override
	public void setquycach(String _quycach) {
		// TODO Auto-generated method stub
		this.quycach=_quycach;
	}
	
	
}

package geso.traphaco.center.beans.khott.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;

import geso.traphaco.center.beans.khott.IKhoTT;
import geso.traphaco.distributor.db.sql.dbutils;

public class KhoTT implements IKhoTT{
	
	String id;
	String TenKho="";
	String DienGiai="";
	String TrangThai="";
	String NgayTao="";
	String NgaySua="";
	String NguoiTao="";
	String NguoiSua="";
	String Message="";
	List<KhoTT> listkho=new ArrayList<KhoTT>();
	public KhoTT(){
	
	}
	public KhoTT(String id){
		
		String sql_getdata="SELECT     K.PK_SEQ, K.TEN, K.NGAYTAO, K.NGAYSUA, K.TRANGTHAI, K.MA, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA "+
						   " FROM         ERP_KHOTT K INNER JOIN  dbo.NHANVIEN AS NT ON K.NGUOITAO = NT.PK_SEQ INNER JOIN "+
                           " dbo.NHANVIEN AS NS ON K.NGUOISUA = NS.PK_SEQ WHERE    K.PK_SEQ= "+id;
		
		System.out.println(sql_getdata);
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql_getdata);
		if(rs!=null){
			try{
				while(rs.next()){
				
				this.DienGiai=rs.getString("MA");
				this.id= rs.getString("pk_seq");
			    this.Message="";
				this.NgaySua= rs.getString("ngaysua");
			 	this.NgayTao= rs.getString("ngaytao");
				this.NguoiSua= rs.getString("nguoisua");
				this.NguoiTao= rs.getString("nguoitao");
				this.TenKho= rs.getString("ten");
			    this.TrangThai=	rs.getString("trangthai");

				}
			}catch(Exception er){
				
			}
		}
	}
	
	public String getId() {
		
		return this.id;
	}

	
	public void setId(String id) {
		
		this.id=id;
	}

	
	public String getTen() {
		
		return this.TenKho;
	}

	
	public void setTen(String ten) {
		
		this.TenKho=ten;
	}

	
	public String getDiengiai() {
		

	return this.DienGiai;
	}

	
	public void setDiengiai(String _diengiai) {
		
		this.DienGiai=_diengiai;
	}

	
	public String getTrangthai() {
		
		return this.TrangThai;
	}

	
	public void setTrangthai(String trangthai) {
		
		this.TrangThai=trangthai;
	}

	
	public String getNgaytao() {
		
		return this.NgayTao;
	}

	
	public void setNgaytao(String ngaytao) {
		
		this.NgayTao=ngaytao;
	}

	
	public String getNgaysua() {
		
		return this.NgaySua;
	}

	
	public void setNgaysua(String ngaysua) {
		
		this.NgaySua=ngaysua;
	}

	
	public String getNguoitao() {
		
		return this.NguoiTao;
	}

	
	public void setNguoitao(String _nguoitao) {
		
		this.NguoiTao =_nguoitao;
	}

	
	public String getNguoisua() {
		
		return this.NguoiTao;
	}

	
	public void setNguoisua(String nguoisua) {
		
		this.NguoiSua=nguoisua;
	}

	
	public String getMessage() {
		
		return this.Message;
	}

	
	public void setMessage(String msg) {
		
		this.Message=msg;
	}

	
	/*public boolean saveNewKho(HttpServletRequest request) 
	{

		dbutils db=new dbutils();
		try
		{
			this.TenKho  = request.getParameter("ten");
			this.NgayTao = getDateTime();
			this.NgaySua = request.getParameter("ten");
			this.NguoiTao = request.getParameter("ten");
			this.TrangThai = request.getParameter("ten");
			this.DienGiai = request.getParameter("ten");
			
			
			db.getConnection().setAutoCommit(false);
			String query =  "insert into ERP_KHOTT(ten, ngaytao, ngaysua, nguoitao, nguoisua, trangthai, ma) " +
							"values(N'" + this.TenKho + "','"+ this.NgayTao + "','" + this.NgaySua + "','" + this.NguoiTao + "','" + this.NguoiSua + "','" + this.TrangThai + "',N'" + this.DienGiai + "')";
			if(!db.update(query)){//cap nhat khong dc
				this.Message=("Khong The tao moi kho trung tam nay, vui long kiem tra lai" + query);
				db.getConnection().rollback();
				return false;
			}*/

			/*query = "select IDENT_CURRENT('khott')as khottId";
			ResultSet rs = db.get(query);
			try{
				rs.next();
				String khoId = rs.getString("khottId");
				
				query = "select pk_seq,ma from sanpham where trangthai!='2'";
				rs = db.get(query);
			
				while(rs.next()){
					query = "insert into tonkhoicp(kho,masp,stock,booked,AVAILABLE) values("+ khoId +",'" + rs.getString("ma") + "',0,0,0)";
					if(!db.update(query)){
						db.get("rollback");
						this.Message="San pham dua vao kho khong the thuc hien";
						return false;
					}
					//System.out.println(query);
				}
			}catch(Exception e){}*/
		
			/*query = "insert ERP_KHOTT_SANPHAM(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, GIAGOC) " +
					"select IDENT_CURRENT('ERP_KHOTT'), pk_seq, 0, 0, 0, 0 from SANPHAM ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.Message = ("San pham dua vao kho khong the thuc hien");
				return false;
			}
		
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
	
		}
		catch(Exception er)
		{
			db.get("rollback");
			this.Message=er.toString();
			return false;
		}
		return true;
	}*/

	
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
		
	}
	public boolean UpdateKho()
	{
		dbutils db = new dbutils();
		
		String query ="update ERP_KHOTT set Ten = N'" + this.TenKho + "',Ngaysua= '" + this.NgaySua + "',nguoisua= '" + this.NguoiSua + "',trangthai= '" + this.TrangThai + "',MA= N'" + this.DienGiai + "'  where pk_seq=" + this.id;
		System.out.println("---CAP NHAT KHO: " + query);
		if(!db.update(query)){//cap nhat khong dc
			System.out.println("Khong sua duoc :" + query);
			return false;
		}
		
		query = "insert ERP_KHOTT_SANPHAM(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, GIATON, THANHTIEN) " +
				"select '" + this.id + "', pk_seq, 0, 0, 0, 0, 0 from SANPHAM where pk_seq not in ( select sanpham_fk from ERP_KHOTT_SANPHAM where khott_fk = '" + this.id + "' ) ";
		if(!db.update(query))
		{
			this.Message = "San pham dua vao kho khong the thuc hien";
			return false;
		}
		
		return true;
	}

	
	public boolean Delete() {
		
		dbutils db=new dbutils();
		String query ="Delete  ERP_KHOTT where pk_seq=" + this.id;
		if(!db.update(query)){//cap nhat khong dc
			System.out.println("Khong sua duoc :" + query);
			return false;
		}
		return true;
	}


	public void setListkho(String sql) {
		
		String sql_getdata="SELECT     K.PK_SEQ, K.TEN, K.NGAYTAO, K.NGAYSUA, K.TRANGTHAI, K.MA, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA "+
						   " FROM         ERP_KHOTT K INNER JOIN  dbo.NHANVIEN NT ON K.NGUOITAO = NT.PK_SEQ INNER JOIN "+
                           " dbo.NHANVIEN AS NS ON K.NGUOISUA = NS.PK_SEQ WHERE  K.PK_SEQ > 0 ";
		if(!sql.equals("")){
			sql_getdata=sql;
		}
		System.out.println("INIT: " + sql_getdata);
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql_getdata);
		if(rs!=null){
			try{
				while(rs.next()){
				KhoTT kho=new KhoTT();
				kho.setDiengiai(rs.getString("ma"));
				kho.setId(rs.getString("pk_seq"));
				kho.setMessage("");
				kho.setNgaysua(rs.getString("ngaysua"));
				kho.setNgaytao(rs.getString("ngaytao"));
				kho.setNguoisua(rs.getString("nguoisua"));
				kho.setNguoitao(rs.getString("nguoitao"));
				kho.setTen(rs.getString("ten"));
				kho.setTrangthai(rs.getString("trangthai"));
				this.listkho.add(kho);
				}
			}catch(Exception er){
				
			}
		}
	}

	
	public List<KhoTT> getListKho() {
		
		return this.listkho;
	}
	@Override
	public boolean saveNewKho() {
		dbutils db=new dbutils();
		try
		{
			
			
			db.getConnection().setAutoCommit(false);
			String query =  "insert into ERP_KHOTT(ten, ngaytao, ngaysua, nguoitao, nguoisua, trangthai, ma) " +
							"values(N'" + this.TenKho + "','"+ this.NgayTao + "','" + this.NgaySua + "','" + this.NguoiTao + "','" + this.NguoiSua + "','" + this.TrangThai + "',N'" + this.DienGiai + "')";
			if(!db.update(query)){//cap nhat khong dc
				this.Message=("Khong The tao moi kho trung tam nay, vui long kiem tra lai" + query);
				db.getConnection().rollback();
				return false;
			}
			query = "insert ERP_KHOTT_SANPHAM(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, GIAGOC) " +
					"select IDENT_CURRENT('ERP_KHOTT'), pk_seq, 0, 0, 0, 0 from SANPHAM ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.Message = ("San pham dua vao kho khong the thuc hien");
				return false;
			}
		
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
	
		}
		catch(Exception er)
		{
			db.get("rollback");
			this.Message=er.toString();
			return false;
		}
		return true;
	}


	
	
}

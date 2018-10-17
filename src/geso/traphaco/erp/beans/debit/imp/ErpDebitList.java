package geso.traphaco.erp.beans.debit.imp;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.debit.IErpDebitList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ErpDebitList implements IErpDebitList{
	private String ngayGhiNhan;
	private String nguoiTao;
	private String doiTuong;
	private dbutils db;
	private String dienGiai;
	private String ma;
	private String userId;
	private String trangThai;
	private ResultSet rsDebit;
	private String msg;
	
	public ErpDebitList() {
		super();
		this.ngayGhiNhan = "";
		this.nguoiTao = "";
		this.doiTuong = "";
		this.db = new dbutils();
		this.dienGiai = "";
		this.trangThai = "";
		this.ma = "";
		this.userId = "";
		this.msg = "";
		
	}
	
	public ErpDebitList(String ngayGhiNhan, String nguoiTao, String doiTuong,
			dbutils db, String dienGiai, String trangThai) {
		super();
		this.ngayGhiNhan = ngayGhiNhan;
		this.nguoiTao = nguoiTao;
		this.doiTuong = doiTuong;
		this.db = db;
		this.dienGiai = dienGiai;
		this.trangThai = trangThai;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResultSet getRsDebit() {
		return rsDebit;
	}
	public void setRsDebit(ResultSet rsDebit) {
		this.rsDebit = rsDebit;
	}
	public String getNgayGhiNhan() {
		return ngayGhiNhan;
	}
	public void setNgayGhiNhan(String ngayGhiNhan) {
		this.ngayGhiNhan = ngayGhiNhan;
	}
	public String getNguoiTao() {
		return nguoiTao;
	}
	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}
	public String getDoiTuong() {
		return doiTuong;
	}
	public void setDoiTuong(String doiTuong) {
		this.doiTuong = doiTuong;
	}
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}
	public String getDienGiai() {
		return dienGiai;
	}
	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	//phương thức lấy các record trong erp_debit
	@Override
	public void init(String query) {
		String sql ="";
		if( query.trim().length() >0){
			sql = query;
		}else{
			sql=  " select de.PK_SEQ, de.DOITUONG, isnull(nv.TEN,'') as NGUOITAO, " +
	        " isnull(nv2.TEN,'') as NGUOISUA, de.NGAYTAO, de.NGAYSUA," +
	 		" de.DIENGIAI, de.TRANGTHAI from ERP_DEBIT  de inner join NHANVIEN nv on de.NGUOITAO= nv.PK_SEQ " +
	 		" inner join NHANVIEN nv2 on nv2.PK_SEQ = de.NGUOISUA  order by de.PK_SEQ desc ";
		}
		
		this.rsDebit = db.get(sql);
	}

	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
		this.db.shutDown();
	}

	@Override
	public boolean delete(String id) {
		try{
		this.db.getConnection().setAutoCommit(false);
		String sql ="update ERP_DEBIT set TRANGTHAI ='2' where PK_SEQ ="+ id;
		int k = this.db.updateReturnInt(sql);
		if( k !=1){
			this.db.getConnection().rollback();
			return false;
		}
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		return true;
		} catch ( Exception ex){
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}

	@Override
	public boolean chot(String id) {
		try {
			this.db.getConnection().setAutoCommit(false);
			String sql =   " select de.PK_SEQ, debit.TIENTE_FK, de.DIENGIAI,debit.DOITUONG, NONGUYENTE, NOVND,   " +
							" de.TAIKHOAN_FK, debit.NGAYGHINHAN," +
							" CASE WHEN debit.LOAIDOITUONG = 1 THEN ncc.TAIKHOAN_FK " +
							" WHEN  debit.LOAIDOITUONG = 2 THEN kh.TaiKhoan_FK " +
							" END as taiKhoanDoiTuong, debit.LOAIDOITUONG, debit.TIGIA " +
							" from ERP_DEBIT_DETAILS de inner join ERP_DEBIT debit on debit.PK_SEQ = de.DEBIT_FK  " +
							" left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = debit.DOITUONG  " +
							" left join ERP_KHACHHANG kh on kh.PK_SEQ = debit.DOITUONG " +
							" where DEBIT_FK = "+ id;
			ResultSet rs = this.db.get(sql);
			System.out.println(sql);
			List<String> taiKhoans = new ArrayList<String>();
			List<Double> noNguyenTes = new ArrayList<Double>();
			List<Double> noVNDs  = new ArrayList<Double>();
			List<String> moTas = new ArrayList<String>();
			List<String> soChungTus = new ArrayList<String>();
			List<String> doiTuongs = new ArrayList<String>();
			List<String> taiKhoanDoiTuongs = new ArrayList<String>();
			List<String> tienTes = new ArrayList<String>();
			List<String> loaiDoiTuongs = new ArrayList<String>();
			List<Double> tiGias = new ArrayList<Double>();
			
			String ngayghinhan = "";
			if( rs !=null){
				try{
				while( rs.next()){
					taiKhoans.add(rs.getString("TAIKHOAN_FK"));
					noNguyenTes.add(rs.getDouble("NONGUYENTE"));
					noVNDs.add(rs.getDouble("NOVND"));
					moTas.add(rs.getString("DIENGIAI"));
					ngayghinhan = rs.getString("NGAYGHINHAN");
					soChungTus.add(rs.getString("PK_SEQ"));
					doiTuongs.add(rs.getString("DOITUONG"));
					taiKhoanDoiTuongs.add(rs.getString("taiKhoanDoiTuong"));
					loaiDoiTuongs.add(rs.getString("LOAIDOITUONG"));
					tienTes.add(rs.getString("TIENTE_FK"));
					tiGias.add(rs.getDouble("TIGIA"));
				}
				} catch ( Exception ex){
					ex.printStackTrace();
					this.db.getConnection().rollback();
				}
			}
			
			for(int i=0; i< taiKhoans.size(); i++){
				Utility util = new Utility();
				String thang = getMonth(ngayghinhan);
				System.out.println("ngaythang"+ ngayghinhan);
				System.out.println("Thang"+ thang);
				String nam = getYear(ngayghinhan);
				System.out.println("Nam"+ nam);
				
				String ngayghinhan1 = ngayghinhan;
				String ngaychungtu = ngayghinhan;
				String loaichungtu ="debit";
				String sochungtu = soChungTus.get(i);// mã pk_seq bảng debit_details
				// tài khoản nợ là NCC, nhân viên
				String taikhoanNO_fk = taiKhoanDoiTuongs.get(i);
				// tài khoản có là tài khoản 
				String taikhoanCO_fk = taiKhoans.get(i);
				String NOIDUNGNHAPXUAT_FK = "";
				String NO = noVNDs.get(i).toString();
				String CO = noVNDs.get(i).toString();
				
				String DOITUONG_NO = "";
				if( loaiDoiTuongs.get(i).equals("1")){
					DOITUONG_NO ="Nhà cung cấp";
				} else{
					DOITUONG_NO ="Khách hàng";
				}
				String MADOITUONG_NO = doiTuongs.get(i);
				String DOITUONG_CO ="";
				String MADOITUONG_CO = "";
				
				String LOAIDOITUONG ="0";
				
				String SOLUONG = "1";
				String DONGIA = noVNDs.get(i).toString();
				String TIENTEGOC_FK = tienTes.get(i); // USD
				String DONGIANT = noNguyenTes.get(i).toString();
				String TIGIA_FK = tiGias.get(i).toString();
				String TONGGIATRI = noVNDs.get(i).toString(); // 
			    String TONGGIATRINT =noNguyenTes.get(i).toString();
			    String khoanmuc ="debit";
			    
				String msg = util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoanNO_fk,
						taikhoanCO_fk, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG_NO, MADOITUONG_NO, 
						DOITUONG_CO, MADOITUONG_CO, LOAIDOITUONG, SOLUONG, DONGIA, TIENTEGOC_FK, 
						DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, khoanmuc);
				
				if( msg.trim().length() >0){
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			sql = "update ERP_DEBIT set TRANGTHAI ='1' where PK_SEQ ="
				+ id;
			int k = this.db.updateReturnInt(sql);
			if (k != 1) {
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}
	private String getMonth(String s){
		Date date= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		month++;
		return String.valueOf(month);
	}
	private String getYear(String s){
		Date date= new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		return String.valueOf(year);
	}
}

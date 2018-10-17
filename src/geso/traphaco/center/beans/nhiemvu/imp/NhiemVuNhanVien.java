package geso.traphaco.center.beans.nhiemvu.imp;


import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.beans.nhiemvu.*;
import geso.traphaco.distributor.db.sql.dbutils;

public class NhiemVuNhanVien implements INhiemVuNhanVien {

	String userId;
	String id;
	String ngayTao;
	String ngaySua;
	String nguoiTao;
	String nguoiSua;

	String doiTuong;
	int thang;
	int nam;
	String dvkdId;
	String kbhId;
	String dienGiai;
	String msg;
	
	ResultSet dvkdList;
	ResultSet kbhList;
	
	List<INhiemVuNhanVienChiTiet> nvList = new ArrayList<INhiemVuNhanVienChiTiet>();
	
	String dvkdTen;
	String kbhTen;
	
	dbutils db;
	 
	public NhiemVuNhanVien() {
		db = new dbutils();
		
		this.id = "";
		this.doiTuong = "";
		this.thang = Integer.parseInt(getMonth());
		this.nam = Integer.parseInt(getYear());
		this.dvkdId = "";
		this.kbhId = "";
		this.dienGiai = "";
		this.ngayTao = "";
		this.ngaySua = "";
		this.nguoiTao = "";
		this.nguoiSua = "";
		this.msg = "";
		
		this.dvkdTen = "";
		this.kbhTen = "";
		
		this.kbhList = createKbhRS();
		this.dvkdList = createDvkdList();
	}
	
	public NhiemVuNhanVien(String id) {
		db = new dbutils();
		this.id = id;
		this.doiTuong = "";
		this.thang = Integer.parseInt(getMonth());
		this.nam = Integer.parseInt(getYear());
		this.dvkdId = "";
		this.kbhId = "";
		this.dienGiai = "";
		this.ngayTao = "";
		this.ngaySua = "";
		this.nguoiTao = "";
		this.nguoiSua = "";
		this.msg = "";
		
		this.dvkdTen = "";
		this.kbhTen = "";
		
		this.kbhList = createKbhRS();
		this.dvkdList = createDvkdList();
	}
	 
	public NhiemVuNhanVien(String[] param) {
		db = new dbutils();
		this.id = param[0].trim();
		this.doiTuong = param[1].trim();
		this.thang = Integer.parseInt(param[2].trim());
		this.nam = Integer.parseInt(param[3].trim());
		this.dvkdId = param[4].trim();
		this.kbhId = param[5].trim();
		this.dienGiai = param[6].trim();
		this.ngayTao = param[7].trim();
		this.ngaySua = param[8].trim();
		this.nguoiTao = param[9].trim();
		this.nguoiSua = param[10].trim();
		this.dvkdTen = param[11].trim();
		this.kbhTen = param[12].trim();
		
		this.msg = "";
		
		//this.kbhList = createKbhRS();
		//this.dvkdList = createDvkdList();
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setDoiTuong(String value) {
		this.doiTuong = value;
	}

	@Override
	public String getDoiTuong() {
		return this.doiTuong;
	}

	@Override
	public void setThang(int value) {
		this.thang = value;
		
	}

	@Override
	public int getThang() {
		return this.thang;
	}

	@Override
	public void setNam(int value) {
		this.nam = value;
	}

	@Override
	public int getNam() {
		return this.nam;
	}

	@Override
	public String getDvkdId() {
		return this.dvkdId;
	}

	@Override
	public void setDvkdId(String value) {
		this.dvkdId = value;
	}

	@Override
	public String getKbhId() {
		return this.kbhId;
	}

	@Override
	public void setKbhId(String value) {
		this.kbhId = value;
	}

	@Override
	public void setDienGiai(String value) {
		this.dienGiai = value;
	}

	@Override
	public String getDienGiai() {
		return this.dienGiai;
	}

	@Override
	public void setKbhList(ResultSet kbhList) {
		this.kbhList = kbhList;
	}

	@Override
	public ResultSet getKbhList() {
		return this.kbhList;
	}

	@Override
	public ResultSet getDvkdList() {
		return this.dvkdList;
	}

	@Override
	public void setDvkdList(ResultSet dvkdlist) {
		this.dvkdList = dvkdlist;
	}

	@Override
	public String getDvkdTen() {
		return this.dvkdTen;
	}

	@Override
	public void setDvkdTen(String value) {
		this.dvkdTen = value;
	}

	@Override
	public String getKbhTen() {
		return this.kbhTen;
	}

	@Override
	public void setKbhTen(String value) {
		this.kbhTen = value;
	}

	@Override
	public void setNguoiTao(String nguoitao) {
		this.nguoiTao = nguoitao;
	}

	@Override
	public String getNguoiTao() {
		return this.nguoiTao;
	}

	@Override
	public void setNguoiSua(String nguoisua) {
		this.nguoiSua = nguoisua;
	}

	@Override
	public String getNguoiSua() {
		return this.nguoiSua;
	}

	@Override
	public void setNgayTao(String ngaytao) {
		this.ngayTao = ngaytao;
	}

	@Override
	public String getNgayTao() {
		return this.ngayTao;
	}

	@Override
	public void setNgaySua(String ngaySua) {
		this.ngaySua = ngaySua;
	}

	@Override
	public String getNgaySua() {
		return ngaySua;
	}

	@Override
	public void setUserId(String userid) {
		this.userId = userid;
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public void setNvList(List<INhiemVuNhanVienChiTiet> value) {
		this.nvList = value;
	}

	@Override
	public List<INhiemVuNhanVienChiTiet> getNvList() {
		return this.nvList;
	}

	@Override
	public String getMessage() 
	{
		return this.msg;
	}

	@Override
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	public void init(){
		String query = "SELECT * FROM NHIEMVUNHANVIEN WHERE pk_seq='" + this.id + "'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.doiTuong = rs.getString("DOITUONG");
			this.dienGiai = rs.getString("DIENGIAI");
			this.thang = rs.getInt("THANG");
			this.nam = rs.getInt("NAM");
			this.dvkdId = rs.getString("DVKD_FK");
			this.kbhId = rs.getString("KBH_FK");
			this.ngayTao = rs.getString("NGAYTAO");
			this.ngaySua = rs.getString("NGAYSUA");
			this.nguoiTao = rs.getString("NGUOITAO");
			this.nguoiSua = rs.getString("NGUOISUA");
			rs.close();
			
			System.out.println("[NhiemVuNhanVien.init] " + this.toString());
			
		} catch(Exception e) {
			System.out.println("[NhiemVuNhanVien.init] Exception Message: " + e.getMessage());
		}
	}

	@Override
	public boolean create() {
		try{
			this.db.getConnection().setAutoCommit(false);
			
			this.ngayTao = getDateTime();
			this.nguoiTao = this.userId;
			
			String command = "insert into NHIEMVUNHANVIEN(DOITUONG, DIENGIAI, THANG, NAM, DVKD_FK, KBH_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA) "+
			 "values('" + this.doiTuong + "',N'" + this.dienGiai + "','" + this.thang + "','" + this.nam + "','" + this.dvkdId + "','" + this.kbhId + "','" + 
			 this.ngayTao + "','" + this.ngayTao + "','"+this.userId+"','"+this.userId+"')";
			System.out.println("[NhiemVuNhanVien.create] query = " + command);
		
			if (!db.update(command)){
				this.msg = "Không thể tạo mới nhiệm vụ.";
				db.update("rollback");
				return false;
			}
			
			String query_getid = "select IDENT_CURRENT('NHIEMVUNHANVIEN') as id";
			ResultSet rs_getpk_seq = this.db.get(query_getid);
			rs_getpk_seq.next();
			String nvnvId = rs_getpk_seq.getString("id");
			System.out.println("[NhiemVuNhanVien.create] nvnvId = " + nvnvId);
			
			System.out.println("[NhiemVuNhanVien.create] nvList.size = " + this.nvList.size());
			
			//Thêm nhiệm vụ nhân viên chi tiết
			for(int i = 0; i < this.nvList.size(); i++) {
				INhiemVuNhanVienChiTiet ct = this.nvList.get(i);
				if(ct.getDoiTuong().trim().equals(this.doiTuong.trim())) {
					if(ct.getThuong() > 0) {
						String _query = " insert into NHIEMVUNHANVIEN_CHITIET(NHIEMVUNHANVIEN_FK, NHIEMVU_FK, THUONG,SOLUONG) " +
						 " values('" + nvnvId + "','" + ct.getNvId() + "','" + ct.getThuong() + "','"+ct.getSoluong()+"')";
						System.out.println("[NhiemVuNhanVien.create] query = " + command);
						
						if (!this.db.update(_query)){
							System.out.println("[NhiemVuNhanVient.create] Khong the tao moi nhiem vu nhan vien chi tiet " + this.toString());
							this.db.update("rollback");
							return false;
						}
					}
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception e) {
			this.msg = "Xảy ra lỗi khi tạo mới nhiệm vụ (Lỗi: " + e.getMessage() + ")";
			System.out.println("[NhiemVuNhanVien.create] Exception Message = " + e.getMessage());
			this.db.update("rollback");
			return false;
		}
		
		System.out.println("[NhiemVuNhanVien.create] -> Created successfully!");
		return true;
		
	}

	@Override
	public boolean update() {
		
		try{
			this.db.getConnection().setAutoCommit(false);
			this.ngaySua = getDateTime();
			this.nguoiSua = this.userId;
			
			String command = " UPDATE NHIEMVUNHANVIEN SET DOITUONG = '" + this.doiTuong + "', " +
								" DIENGIAI = N'" + this.dienGiai + "', "+
								" THANG = '" + this.thang + "', "+
								" NAM = '" + this.nam + "', "+
								" DVKD_FK = '" + this.dvkdId + "', "+ 
								" KBH_FK = '" + this.kbhId + "', "+
								" NGAYSUA = '" + this.ngaySua + "', "+
								" NGUOISUA = '"+this.nguoiSua+"' "+
							 " WHERE PK_SEQ = '"+this.id+"' ";
			
			System.out.println("[NhiemVuNhanVien.update] query = " + command);
		
			if (!db.update(command)){
				this.msg = "Không thể cập nhật nhiệm vụ.";
				db.update("rollback");
				return false;
			}
			
			System.out.println("[NhiemVuNhanVien.update] nvList.size = " + this.nvList.size());
			
			//Xóa nhiệm vụ nhân viên chi tiết cũ
			command = "DELETE NHIEMVUNHANVIEN_CHITIET WHERE NHIEMVUNHANVIEN_FK = '"+this.id+"'";
			
			if (!db.update(command)){
				this.msg = "Không thể cập nhật nhiệm vụ.";
				db.update("rollback");
				return false;
			}
			
			
			//Thêm nhiệm vụ nhân viên chi tiết mới
			for(int i = 0; i < this.nvList.size(); i++) {
				INhiemVuNhanVienChiTiet ct = this.nvList.get(i);
				if(ct.getDoiTuong().trim().equals(this.doiTuong.trim())) {
					if(ct.getThuong() > 0) {
						String _query = " insert into NHIEMVUNHANVIEN_CHITIET(NHIEMVUNHANVIEN_FK, NHIEMVU_FK, THUONG,SOLUONG) " +
						 " values('" + this.id + "','" + ct.getNvId() + "','" + ct.getThuong() + "','"+ct.getSoluong()+"')";
						System.out.println("[NhiemVuNhanVien.update] query = " + command);
						
						if (!this.db.update(_query)){
							System.out.println("[NhiemVuNhanVient.update] Khong the tao moi nhiem vu nhan vien chi tiet " + this.toString());
							this.db.update("rollback");
							return false;
						}
					}
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception e) {
			this.msg = "Xảy ra lỗi khi cập nhật nhiệm vụ (Lỗi: " + e.getMessage() + ")";
			System.out.println("[NhiemVuNhanVien.update] Exception Message = " + e.getMessage());
			this.db.update("rollback");
			return false;
		}
		
		System.out.println("[NhiemVuNhanVien.update] -> Updated successfully!");
		return true;
		
		/*
		
		String _isTuDong = this.isTuDong ? "1" : "0";
		
		String command;
		if(this.isTuDong) {
			command ="update nhiemvu set tieuchi = N'" + this.tieuChi + 
										"', diengiai = N'" + this.dienGiai + 
										"', istudong = '" + _isTuDong + 
										"', loaitieuchi = '" + this.loaiTieuChi + 
										"', doituong = '" + this.doiTuong + 
										"', ngaysua = '" + this.ngaySua + 
										"', nguoisua = '" + this.userId + 
										"', trangthai= '" + this.trangThai + 
					  "' where pk_seq = '" + this.id + "'";
		} else {
			command ="update nhiemvu set tieuchi = N'" + this.tieuChi + 
										"', diengiai = N'" + this.dienGiai + 
										"', istudong = '" + _isTuDong + 
										"', loaitieuchi = NULL " + 
										", doituong = '" + this.doiTuong + 
										"', ngaysua = '" + this.ngaySua + 
										"', nguoisua = '" + this.nguoiSua + 
										"', trangthai= '" + this.trangThai + 
										"' where pk_seq = '" + this.id + "'";
		}
		
		if (!db.update(command)){
			this.msg = "Không thể cập nhật nhiệm vụ.";
			db.update("rollback");
			return false;
		}

		this.db.update("commit");*/
		
	}
	
	public ResultSet createDvkdList(){//chi cho load don vi kinh doanh nao co checked=1
		return db.get("select distinct a.pk_seq, a.donvikinhdoanh from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
	}
	
	public ResultSet createKbhRS(){
		return db.get("select pk_seq, diengiai from kenhbanhang where trangthai='1' order by diengiai");
	}
	
	public List<INhiemVuNhanVienChiTiet> createNhiemVuNhanVienChiTietList() {
		
		this.nvList.clear();
		ResultSet rs = db.get("select pk_seq, tieuchi, doituong from nhiemvu order by pk_seq");
		
		try{											
            while (rs.next()){
            	String nvId = rs.getString("pk_seq");
            	String nvTen = rs.getString("tieuchi");
            	String doituong = rs.getString("doituong");
            	INhiemVuNhanVienChiTiet ct = new NhiemVuNhanVienChiTiet(nvId, nvTen, doituong);
            	
            	this.nvList.add(ct);
            }
        } catch(java.sql.SQLException e){
            System.out.println("[NhiemVuNhanVien.createNhiemVuNhanVienChiTietList] Exception Message = " + e.getMessage());
        }
        
        System.out.println("[NhiemVuNhanVien.createNhiemVuNhanVienChiTietList] nvList.size = " + nvList.size());
        
        return this.nvList;
	}
	
	public List<INhiemVuNhanVienChiTiet> loadNhiemVuNhanVienChiTietList(String nvnvId) {
		
		this.nvList.clear();
		ResultSet rs = db.get(" select nv.pk_seq, nv.tieuchi, nv.doituong, isnull(chitiet.thuong, 0) as thuong,chitiet.soluong from nhiemvu nv "+
							  "	left join ( "+
							  "	select a.pk_seq, b.thuong as thuong,isnull(b.soluong,0) as soluong  from nhiemvu a left join nhiemvunhanvien_chitiet b on a.pk_seq = b.nhiemvu_fk  where b.nhiemvunhanvien_fk = '"+nvnvId+"' "+ 
							  "	) chitiet "+
							  "	on nv.pk_seq = chitiet.PK_SEQ "+
							  "	order by nv.pk_seq");
		try{											
            while (rs.next()){
            	String nvId = rs.getString("pk_seq");
            	String nvTen = rs.getString("tieuchi");
            	String doituong = rs.getString("doituong");
            	float thuong = rs.getFloat("thuong");
            	float soluong=rs.getFloat("soluong");
            	INhiemVuNhanVienChiTiet ct = new NhiemVuNhanVienChiTiet(nvId, nvTen, doituong);
            	ct.setThuong(thuong);
            	ct.setSoluong(soluong);
            	
            	this.nvList.add(ct);
            }
        } catch(java.sql.SQLException e){
            System.out.println("[NhiemVuNhanVien.loadNhiemVuNhanVienChiTietList] Exception Message = " + e.getMessage());
        }
        
        System.out.println("[NhiemVuNhanVien.loadNhiemVuNhanVienChiTietList] nvList.size = " + nvList.size());
        
        return this.nvList;
	}

	@Override
	public void closeDB() {
		if(this.db != null) {
			this.db.shutDown();
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getMonth() 
	{
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getYear() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String toString() {
		return "NhiemVuNhanVien {}";//return "NhiemVuNhanVien {id: "+this.id+", tieuchi: "+this.tieuChi+", diengiai: "+this.dienGiai+", istudong: "+this.isTuDong+", loaitieuchi: "+this.loaiTieuChi+", doituong: "+this.doiTuong+", ngaysua: "+this.ngaySua+", nguoisua: "+this.nguoiSua+", trangthai: "+this.trangThai+"}";
	}
}

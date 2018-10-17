

package geso.traphaco.center.beans.nhiemvu.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.beans.nhiemvu.INhiemVuNhanVien;
import geso.traphaco.center.beans.nhiemvu.INhiemVuNhanVienList;
import geso.traphaco.distributor.db.sql.dbutils;

public class NhiemVuNhanVienList implements INhiemVuNhanVienList {

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
	
	private HttpServletRequest request;
	List<INhiemVuNhanVien> list; 
	
	dbutils db;
	 
	public NhiemVuNhanVienList() {
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
	}
	
	public NhiemVuNhanVienList(String id) {
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
	}
	 
	public NhiemVuNhanVienList(String[] param) {
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
		this.msg = "";
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
	public String getMessage() 
	{
		return this.msg;
	}

	@Override
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	@Override
	public List<INhiemVuNhanVien> getList() {
		return list;
	}

	@Override
	public void setList(List<INhiemVuNhanVien> value) {
		this.list = value;
	}

	@Override
	public HttpServletRequest getRequestObj() {
		return this.request;
	}

	@Override
	public void setRequestObj(HttpServletRequest request) {
		this.request = request;
	}

	@Override	
	public void init(String search){
		String query;
		
		if (search.length() == 0)
		{
	    	query = " select a.pk_seq as id, a.doituong, a.diengiai, a.thang, a.nam, a.dvkd_fk, a.kbh_fk, d.DONVIKINHDOANH as dvkdTen, e.TEN as kbhTen, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua " + 
	    			" from nhiemvunhanvien a, nhanvien b, nhanvien c, DONVIKINHDOANH d, KENHBANHANG e " +
	    			" where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.dvkd_fk = d.PK_SEQ  and a.KBH_FK = e.PK_SEQ order by a.pk_seq ";
		}
		else
		{
			query = search;
		}
		System.out.println("[NhiemVuNhanVienList.init] query = " + query);
		
		createList(query);
	}
	
	private void createList(String query) {
		ResultSet rs = db.get(query);
		List<INhiemVuNhanVien> _list = new ArrayList<INhiemVuNhanVien>();
		if (rs != null){		
			INhiemVuNhanVien nvnvBean;
			try{
				while(rs.next()){
					
					nvnvBean = new NhiemVuNhanVien();
					nvnvBean.setId(rs.getString("id"));
					nvnvBean.setDoiTuong(rs.getString("doituong"));
					nvnvBean.setDienGiai(rs.getString("diengiai"));
					nvnvBean.setThang(rs.getInt("thang"));
					nvnvBean.setNam(rs.getInt("nam"));
					nvnvBean.setDvkdId(rs.getString("dvkd_fk"));
					nvnvBean.setKbhId(rs.getString("kbh_fk"));
					nvnvBean.setDvkdTen(rs.getString("dvkdTen"));
					nvnvBean.setKbhTen(rs.getString("kbhTen"));
					nvnvBean.setNguoiTao(rs.getString("nguoitao"));
					nvnvBean.setNguoiSua(rs.getString("nguoisua"));
					nvnvBean.setNgayTao(rs.getString("ngaytao"));
					nvnvBean.setNgaySua(rs.getString("ngaysua"));
					
					_list.add(nvnvBean);
				}
			} catch(Exception e){
				System.out.println("[NhiemVuNhanVienList.init] Exception Message = " + e.getMessage());
			}
		}
		
		this.list = _list;
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
		return "NhiemVuNhanVienList {}";//return "NhiemVuNhanVien {id: "+this.id+", tieuchi: "+this.tieuChi+", diengiai: "+this.dienGiai+", istudong: "+this.isTuDong+", loaitieuchi: "+this.loaiTieuChi+", doituong: "+this.doiTuong+", ngaysua: "+this.ngaySua+", nguoisua: "+this.nguoiSua+", trangthai: "+this.trangThai+"}";
	}
}

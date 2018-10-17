

package geso.traphaco.center.beans.nhiemvu.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.beans.hangcuahang.IHangcuahang;
import geso.traphaco.center.beans.hangcuahang.imp.Hangcuahang;
import geso.traphaco.center.beans.nhiemvu.INhiemVu;
import geso.traphaco.center.beans.nhiemvu.INhiemVuList;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

public class NhiemVuList implements INhiemVuList {

	private static final long serialVersionUID = -9217977546733610214L;
	
	String userId;
	double id;
	int thang;
	int nam;
	String ngayTao;
	String nguoiTao;
	String nguoiSua;
	String ngaySua;
	String tieuChi;
	String dienGiai;
	String doiTuong;
	String loaiTieuChi;
	boolean isTuDong;
	String trangThai;
	String msg;

	private HttpServletRequest request;
	
	List<INhiemVu> list; 
	
	dbutils db;
	 
	public NhiemVuList() {
		
		this.dienGiai = "";
		this.tieuChi = "";
		this.doiTuong = "";
		this.tieuChi = "";
		this.loaiTieuChi = "";
		this.isTuDong = true;
		this.ngaySua = "";
		this.ngayTao = "";
		this.trangThai = "";
		this.msg = "";
		 
		db = new dbutils();
	}
	
	@Override
	public void setId(double id) {
		this.id = id;
	}

	@Override
	public double getId() {
		return this.id;
	}

	@Override
	public void setTieuChi(String value) {
		this.tieuChi = value;
	}

	@Override
	public String getTieuChi() {
		return this.tieuChi;
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
	public void setIsTuDong(boolean value) {
		this.isTuDong = value;		
	}

	@Override
	public boolean getIsTuDong() {
		return this.isTuDong;
	}

	@Override
	public void setLoaiTieuChi(String value) {
		this.loaiTieuChi = value;
	}

	@Override
	public String getLoaiTieuChi() {
		return this.loaiTieuChi;
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
	public void setNgaySua(String ngaysua) {
		this.ngaySua = ngaysua;
	}

	@Override
	public String getNgaySua() {
		return ngaySua;
	}

	@Override
	public void setTrangThai(String trangthai) {
		this.trangThai = trangthai;
	}

	@Override
	public String getTrangThai() {
		return this.trangThai;
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
	public List<INhiemVu> getList() {
		return list;
	}

	@Override
	public void setList(List<INhiemVu> value) {
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
	    	query = "select a.pk_seq as id, a.tieuchi, a.diengiai, a.loaitieuchi, a.doituong, a.istudong, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua "; 
			query += " from nhiemvu a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq order by a.pk_seq";
		}
		else
		{
			query = search;
		}
		System.out.println("[NhiemVuList.init] query = " + query);
		
		createList(query);
	}
	
	private void createList(String query) {
		ResultSet rs = db.get(query);
		List<INhiemVu> _list = new ArrayList<INhiemVu>();
		if (rs != null){		
			INhiemVu nvBean;
			String[] param = new String[11];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("tieuchi");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getString("loaitieuchi");
					param[4]= rs.getString("doituong");
					param[5]= rs.getString("istudong");
					param[6]= rs.getString("trangthai");
					param[7]= rs.getString("ngaytao");
					param[8]= rs.getString("nguoitao");
					param[9]= rs.getString("ngaysua");
					param[10]= rs.getString("nguoisua");
					
					nvBean = new NhiemVu(param);
					
					//Setup tên loại tiêu chí
					String ltc = "Không có";
                    if(nvBean.getIsTuDong()) {
                    	String dt = nvBean.getDoiTuong().trim();
                    	ltc = nvBean.getLoaiTieuChi().trim();
                    	if(dt.equals("SR")) {
                    		ltc = ltc.equals("0") ? "Viếng thăm" : ltc.equals("1") ? "6 đh/ngày" : ltc.equals("2") ? "Mở 5 điểm bán 1 tháng" : ltc.equals("3") ? "Bán Ra cao hơn tháng trước" : "";
                    	} else if(dt.equals("SS")) {
                    		ltc = ltc.equals("0") ? "Báo cáo" : ltc.equals("1") ? "Giải phóng hàng cận date" : ltc.equals("2") ? "Hoàn thành chỉ tiêu SKU In" : ltc.equals("3") ? "Chỉ tiêu trọng tâm tháng" : ltc.equals("4") ? "Bán Ra cao hơn tháng trước" : ltc.equals("5") ? "Mua Vào cao hơn tháng trước" : "";
                    	} else if(dt.equals("ASM")) {
                    		ltc = ltc.equals("0") ? "Báo cáo" : ltc.equals("1") ? "Giải phóng hàng cận date" : ltc.equals("2") ? "Hỗ trợ" : ltc.equals("3") ? "Hoàn thành chỉ tiêu SKU In" : ltc.equals("4") ? "Mở thêm nhà phân phối" : ltc.equals("5") ? "Bán Ra cao hơn tháng trước" : ltc.equals("6") ? "Mua Vào cao hơn tháng trước" : "";
                    	} else {
                    		ltc = "";
                    	}
                    }
                    nvBean.setTenLoaiTieuChi(ltc);
					
					_list.add(nvBean);
				}
			} catch(Exception e){
				System.out.println("[NhiemVuList.init] Exception Message = " + e.getMessage());
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
	
	
}

package geso.traphaco.center.beans.kehoachnhanvien.imp;

import geso.traphaco.center.beans.kehoachnhanvien.IKeHoachNhanVien;
import geso.traphaco.center.beans.kehoachnhanvien.IKeHoachNhanVienChiTiet;
import geso.traphaco.center.beans.kehoachnhanvien.IKeHoachNhanVienNgay;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class KeHoachNhanVien implements IKeHoachNhanVien
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String tennhanvien;
	String thang;
	String nam;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String trangthai = "0";
	String msg;
	
	IKeHoachNhanVienNgay[] ngayList;
	
	ResultSet nppRs, tinhRs, quanRs;
	
	dbutils db;
	
	public KeHoachNhanVien(String userId, String id)
	{
		int nam = Integer.parseInt(getDateTime("yyyy"));
		int thang = Integer.parseInt(getDateTime("MM"));
		
		this.db = new dbutils();
		this.userId = userId;
		this.id = id;
		this.tennhanvien = "";
		this.thang = "" + thang;
		this.nam = "" + nam;
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.ngayList = new IKeHoachNhanVienNgay[0];
		
		//this.init();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getTenNhanVien() 
	{
		return this.tennhanvien;
	}

	public void setTenNhanVien(String tennhanvien) 
	{
		this.tennhanvien = tennhanvien;
	}
	
	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;
	}

	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNguoitao() 
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;
	}

	public String getNgaysua() 
	{
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;
	}

	public String getNguoisua() 
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;
	}

	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public boolean create()
	{
		try {
			String query;
			
			int thang = 0, nam = 0;
			try { 
				thang = Integer.parseInt(this.thang); 
			} catch(Exception e) {
				this.msg = "Tháng nhập vào không đúng định dạng (" + this.thang + ")";
				return false;
			}
			if(thang < 1 || thang > 12) {
				this.msg = "Tháng nhập vào không đúng định dạng (" + thang + ")";
				return false;
			}
			
			try { 
				nam = Integer.parseInt(this.nam); 
			} catch(Exception e) {
				this.msg = "Năm nhập vào không đúng định dạng (" + nam + ")";
				return false;
			}
			
			if(this.ngayList == null || this.ngayList.length <= 0) {
				this.msg = "Thông tin kế hoạch chi tiết đang rỗng";
				return false;
			}
			
			String tgkehoach = thang < 10 ? nam + "-0" + thang : nam + "-" + thang; 
			String tghientai = getDateTime("yyyy-MM");
			
			//Kiểm tra xem nhân viên đã từng tạo kế hoạch của năm-tháng đó chưa
			query = " SELECT PK_SEQ FROM KEHOACHNV WHERE NHANVIEN_FK = " + this.userId + " AND THANG = " + thang + " AND NAM = " + nam;
			ResultSet rs = db.get(query);
			if(rs != null) {
				try {
					if(rs.next()) {
						rs.close();
						this.msg = "Kế hoạch tháng " + thang + " năm " + nam + " của nhân viên "+this.tennhanvien+" đã được tạo trước đó!";
						return false;
					}

					rs.close();
				} catch(Exception e) {
					this.msg = "Xảy ra lỗi khi kiểm tra thông tin kế hoạch (" + e.getMessage() + ")";
					return false;
				}
			}
			
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			
			this.db.getConnection().setAutoCommit(false);
			
			//Insert kehoachnv
			query = " INSERT KEHOACHNV(NHANVIEN_FK, THANG, NAM, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA) " +
					" SELECT '" + this.userId + "', " + thang + ", " + nam + ", '" + this.ngaytao + "', '" + this.ngaytao + "', '" + this.nguoitao + "', " + this.nguoitao + " ";
			if (!db.update(query)) {
				this.msg = "Không thể thêm mới kế hoạch tháng " + thang + " năm " + nam + " của nhân viên " + this.tennhanvien + "(" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			//Lấy id vừa tạo
			query = " SELECT IDENT_CURRENT('KEHOACHNV') AS ID";
			rs = this.db.get(query);
			if(rs != null) {
				try {
					rs.next();
					this.id = rs.getString("ID");
					rs.close();
				} catch(Exception e) {
					this.msg = "Xảy ra lỗi khi lấy thông tin mã kế hoạch vừa tạo (" + e.getMessage() + ")";
					db.getConnection().rollback();
					return false;
				}
			} else {
				this.msg = "Không thể lấy mã kế hoạch vừa tạo !";
				db.getConnection().rollback();
				return false;
			}
			
			//Insert chi tiết
			IKeHoachNhanVienNgay ngay;
			List<IKeHoachNhanVienChiTiet> nppList, thitruongList;
			IKeHoachNhanVienChiTiet chitiet;
			for(int i = 0; i < this.ngayList.length; i++) {
				ngay = this.ngayList[i];
				if(ngay != null) {
					//Insert nhà phân phối
					nppList = ngay.getNppList();
					if(nppList!=null) {
						for(int j = 0; j < nppList.size(); j++) {
							chitiet = nppList.get(j);
							query = " INSERT KEHOACHNV_NPP(KEHOACHNV_FK, NGAY, NPP_FK, GHICHU) " +
									" SELECT '" + this.id + "', '" + ngay.getNgay() + "', '" + chitiet.getNppId() + "', N'" + chitiet.getGhiChu() + "' ";
							if (!db.update(query)) {
								this.msg = "Không thể thêm nhà phân phối '" + chitiet.getNppId() + "' vào kế hoạch ngày " + chitiet.getNgay() + " tháng " + thang + " năm " + nam + " của nhân viên " + this.tennhanvien + "(" + query + ")";
								db.getConnection().rollback();
								return false;
							}
						}
					}
					
					//Insert thị trường
					thitruongList = ngay.getThiTruongList();
					if(thitruongList!=null) {
						for(int j = 0; j < thitruongList.size(); j++) {
							chitiet = thitruongList.get(j);
							query = " INSERT KEHOACHNV_THITRUONG(KEHOACHNV_FK, NGAY, TINH_FK, QUANHUYEN_FK, GHICHU) " +
									" SELECT '" + this.id + "', '" + ngay.getNgay() + "', '" + chitiet.getTinhId() + "', '" + chitiet.getQuanHuyenId() + "', N'" + chitiet.getGhiChu() + "' ";
							if (!db.update(query)) {
								this.msg = "Không thể thêm thị trường '" + chitiet.getTinhId() + "', '" + chitiet.getQuanHuyenId() + "' vào kế hoạch ngày " + chitiet.getNgay() + " tháng " + thang + " năm " + nam + " của nhân viên " + this.tennhanvien + "(" + query + ")";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception er){
			this.msg = "Xảy ra lỗi khi thêm mới kế hoạch nhân viên (" + er.getMessage() + ")";
			try {
				db.getConnection().rollback();
			} catch (Exception e) { }
			return false;
		}
		return true;
	}

	public boolean update() 
	{
		try {
			String query;
			
			int thangHienTai = Integer.parseInt(getDateTime("MM")),
				namHienTai = Integer.parseInt(getDateTime("yyyy")),
				ngayHienTai = Integer.parseInt(getDateTime("dd"));
			int thang = 0, nam = 0;
			try { 
				thang = Integer.parseInt(this.thang); 
			} catch(Exception e) {
				this.msg = "Tháng nhập vào không đúng định dạng (" + this.thang + ")";
				return false;
			}
			if(thang < 1 || thang > 12) {
				this.msg = "Tháng nhập vào không đúng định dạng (" + thang + ")";
				return false;
			}
			
			try { 
				nam = Integer.parseInt(this.nam); 
			} catch(Exception e) {
				this.msg = "Năm nhập vào không đúng định dạng (" + nam + ")";
				return false;
			}
			
			if(this.ngayList == null || this.ngayList.length <= 0) {
				this.msg = "Thông tin kế hoạch chi tiết đang rỗng";
				return false;
			}
			
			String tgkehoach = thang < 10 ? nam + "-0" + thang : nam + "-" + thang; 
			String tghientai = getDateTime("yyyy-MM");
			
			//Kiểm tra xem nhân viên đã từng tạo kế hoạch của năm-tháng đó chưa
			query = " SELECT PK_SEQ FROM KEHOACHNV WHERE PK_SEQ != " + this.id + " AND NHANVIEN_FK = " + this.userId + " AND THANG = " + thang + " AND NAM = " + nam;
			ResultSet rs = db.get(query);
			if(rs != null) {
				try {
					if(rs.next()) {
						rs.close();
						this.msg = "Kế hoạch tháng " + thang + " năm " + nam + " của nhân viên "+this.tennhanvien+" đã được tạo trước đó!";
						return false;
					}

					rs.close();
				} catch(Exception e) {
					this.msg = "Xảy ra lỗi khi kiểm tra thông tin kế hoạch (" + e.getMessage() + ")";
					return false;
				}
			}
			
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			
			this.db.getConnection().setAutoCommit(false);
			
			//Update kehoachnv
			query = " UPDATE KEHOACHNV " +
					" SET THANG = " + thang + ", NAM = " + nam + ", NGAYSUA = '" + this.ngaysua + "', NGUOISUA = " + this.nguoisua + " " +
					" WHERE PK_SEQ = " + this.id;
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật kế hoạch tháng " + thang + " năm " + nam + " của nhân viên " + this.tennhanvien + "(" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			/*String queryNgay = 
				" SELECT DISTINCT NGAY FROM KEHOACHNV_NPP WHERE LAT IS NOT NULL AND LONG IS NOT NULL AND KEHOACHNV_FK = " + this.id +
				" UNION " +
				" SELECT DISTINCT NGAY FROM KEHOACHNV_THITRUONG WHERE LAT IS NOT NULL AND LONG IS NOT NULL AND KEHOACHNV_FK = " + this.id;
			List<String> ngays = new ArrayList<String>();
			rs = db.get(queryNgay);
			try {
				while(rs.next()) {
					ngays.add(rs.getString("NGAY"));
				}
				rs.close();
			} catch(Exception e) {
				this.msg = "Xảy ra lỗi khi lấy thông tin kế hoạch (" + e.getMessage() + ")";
				return false;
			}*/
			
			String queryNgay = "";
			if(namHienTai == nam && thangHienTai == thang) {
				queryNgay = " AND NGAY >= " + ngayHienTai;
			}
			
			//Xóa chi tiết cũ
			query = " DELETE KEHOACHNV_NPP WHERE KEHOACHNV_FK = " + this.id + queryNgay;
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật kế hoạch tháng " + thang + " năm " + nam + " của nhân viên " + this.tennhanvien + "(" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			query = " DELETE KEHOACHNV_THITRUONG WHERE KEHOACHNV_FK = " + this.id + queryNgay;
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật kế hoạch tháng " + thang + " năm " + nam + " của nhân viên " + this.tennhanvien + "(" + query + ")";
				db.getConnection().rollback();
				return false;
			}
			
			//Thêm chi tiết mới
			IKeHoachNhanVienNgay ngay;
			List<IKeHoachNhanVienChiTiet> nppList, thitruongList;
			IKeHoachNhanVienChiTiet chitiet;
			for(int i = 0; i < this.ngayList.length; i++) {
				ngay = this.ngayList[i];
				//Nếu sửa kế hoạch tháng hiện tại, năm hiện tại thì chỉ được sửa ngày >= ngày hiện tại
				boolean condition = thang != thangHienTai || nam != namHienTai || Integer.parseInt(ngay.getNgay()) >= ngayHienTai;
				//System.out.println(thang + ", " + thangHienTai + ", " + nam + ", " + namHienTai + ", " + ngay.getNgay() + ", " + ngay.getNgay() + ", " + (Integer.parseInt(ngay.getNgay()) >= ngayHienTai));
				if(ngay != null && condition) {
					//Insert nhà phân phối
					nppList = ngay.getNppList();
					if(nppList!=null) {
						for(int j = 0; j < nppList.size(); j++) {
							chitiet = nppList.get(j);
							query = " INSERT KEHOACHNV_NPP(KEHOACHNV_FK, NGAY, NPP_FK, GHICHU) " +
									" SELECT '" + this.id + "', '" + ngay.getNgay() + "', '" + chitiet.getNppId() + "', N'" + chitiet.getGhiChu() + "' ";
							if (!db.update(query)) {
								this.msg = "Không thể thêm nhà phân phối '" + chitiet.getNppId() + "' vào kế hoạch ngày " + chitiet.getNgay() + " tháng " + thang + " năm " + nam + " của nhân viên " + this.tennhanvien + "(" + query + ")";
								db.getConnection().rollback();
								return false;
							}
						}
					}
					
					//Insert thị trường
					thitruongList = ngay.getThiTruongList();
					if(thitruongList!=null) {
						for(int j = 0; j < thitruongList.size(); j++) {
							chitiet = thitruongList.get(j);
							query = " INSERT KEHOACHNV_THITRUONG(KEHOACHNV_FK, NGAY, TINH_FK, QUANHUYEN_FK, GHICHU) " +
									" SELECT '" + this.id + "', '" + ngay.getNgay() + "', '" + chitiet.getTinhId() + "', '" + chitiet.getQuanHuyenId() + "', N'" + chitiet.getGhiChu() + "' ";
							if (!db.update(query)) {
								this.msg = "Không thể thêm thị trường '" + chitiet.getTinhId() + "', '" + chitiet.getQuanHuyenId() + "' vào kế hoạch ngày " + chitiet.getNgay() + " tháng " + thang + " năm " + nam + " của nhân viên " + this.tennhanvien + "(" + query + ")";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception er){
			er.printStackTrace();
			this.msg = "Xảy ra lỗi khi cập nhật kế hoạch nhân viên (" + er.getMessage() + ")";
			try {
				db.getConnection().rollback();
			} catch (Exception e) { }
			return false;
		}
		return true;
	}
	
	public void init(boolean display) 
	{
		if(id != null && id.length() > 0) 
		{
			String 
			query = " SELECT KH.PK_SEQ, KH.NHANVIEN_FK, NV.TEN AS NHANVIEN, KH.THANG, KH.NAM, ISNULL(KH.TRANGTHAI, 0) AS TRANGTHAI " +
					" FROM KEHOACHNV KH " +
					" INNER JOIN NHANVIEN NV ON NV.PK_SEQ = KH.NHANVIEN_FK WHERE KH.PK_SEQ = " + id;
			
	        ResultSet rs =  db.get(query);
	        try
	        {
	        	if(rs.next())
	        	{
	        		this.id = rs.getString("PK_SEQ");
	        		this.tennhanvien = rs.getString("NHANVIEN");
	        		this.thang = rs.getString("thang");
	        		this.nam = rs.getString("nam");
	        	}
	        	rs.close();
	        	
	        	if(this.thang != null && this.thang.trim().length() > 0 && this.nam != null && this.nam.trim().length() > 0)
				{
					try
					{
						int nam = Integer.parseInt(this.thang);
						int thang = Integer.parseInt(this.nam);
						
						Calendar mycal = new GregorianCalendar(nam, thang-1, 1);
						int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
						
						ngayList = new IKeHoachNhanVienNgay[daysInMonth];
						
						for(int i = 0; i < daysInMonth; i++) 
						{
							IKeHoachNhanVienNgay khNgay = new KeHoachNhanVienNgay("");
							khNgay.setNgay(String.valueOf(i+1));
							ngayList[i] = khNgay;
						}
					}
					catch(Exception e) 
					{
						 e.printStackTrace();
					}
				}
	        	
	        	query = " SELECT KHNPP.KEHOACHNV_FK, KHNPP.NGAY, KHNPP.NPP_FK, ISNULL(KHNPP.LAT, '') AS LAT, ISNULL(KHNPP.LONG, '') AS LONG, ISNULL(KHNPP.GHICHU, '') AS GHICHU, ISNULL(KHNPP.GHICHU2, '') AS GHICHU2, NPP.TEN AS NPP_TEN " +
						" FROM KEHOACHNV_NPP KHNPP" +
						" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = KHNPP.NPP_FK " +
						" WHERE KEHOACHNV_FK = " + this.id +
						" ORDER BY NGAY ";
	        	rs =  db.get(query);
	        	try 
        		{
        			while(rs.next()) {
        				int ngay = rs.getInt("NGAY");
        				IKeHoachNhanVienNgay khNgay = ngayList[ngay-1];
        				
        				IKeHoachNhanVienChiTiet chitiet = new KeHoachNhanVienChiTiet(this.id);
						chitiet.setNgay(rs.getString("NGAY"));
						chitiet.setNppId(display ? rs.getString("NPP_TEN") : rs.getString("NPP_FK"));
						chitiet.setLat(rs.getString("LAT"));
						chitiet.setLon(rs.getString("LONG"));
						chitiet.setGhiChu(rs.getString("GHICHU"));
						
						khNgay.getNppList().add(chitiet);
        			}
        			rs.close();
        		}
        		catch(Exception e) 
        		{
        			e.printStackTrace();
        		}
	        	
	        	query = " SELECT KHTT.KEHOACHNV_FK, KHTT.NGAY, KHTT.TINH_FK, ISNULL(TT.TEN, '') AS TINH_TEN, KHTT.QUANHUYEN_FK, ISNULL(QH.TEN, '') AS QUANHUYEN_TEN, ISNULL(KHTT.LAT, '') AS LAT, ISNULL(KHTT.LONG, '') AS LONG, ISNULL(KHTT.DIACHI, '') AS DIACHI, ISNULL(KHTT.GHICHU, '') AS GHICHU, ISNULL(KHTT.GHICHU2, '') AS GHICHU2 " +
						" FROM KEHOACHNV_THITRUONG KHTT " +
						" INNER JOIN TINHTHANH TT ON TT.PK_SEQ = KHTT.TINH_FK " +
						" INNER JOIN QUANHUYEN QH ON QH.PK_SEQ = KHTT.QUANHUYEN_FK " +
						" WHERE KEHOACHNV_FK = " + this.id +
						" ORDER BY NGAY ";
	        	rs =  db.get(query);
	        	try 
        		{
        			while(rs.next()) {
        				int ngay = rs.getInt("NGAY");
        				IKeHoachNhanVienNgay khNgay = ngayList[ngay-1];
        				
        				IKeHoachNhanVienChiTiet chitiet = new KeHoachNhanVienChiTiet(this.id);
						chitiet.setNgay(rs.getString("NGAY"));
						chitiet.setTinhId(display ? rs.getString("TINH_TEN") : rs.getString("TINH_FK"));
						chitiet.setQuanHuyenId(display ? rs.getString("QUANHUYEN_TEN") : rs.getString("QUANHUYEN_FK"));
						chitiet.setLat(rs.getString("LAT"));
						chitiet.setLon(rs.getString("LONG"));
						chitiet.setDiaChi(rs.getString("DIACHI"));
						chitiet.setGhiChu(rs.getString("GHICHU"));
						chitiet.setGhiChu2(rs.getString("GHICHU2"));
						
						khNgay.getThiTruongList().add(chitiet);
        			}
        			rs.close();
        		}
        		catch(Exception e) 
        		{
        			e.printStackTrace();
        		}
	       	}
	        catch(Exception e) {
	        	e.printStackTrace();
	        }
		}
		else 
		{
			String query = " select ten from nhanvien where pk_seq = " + this.userId;
			ResultSet rs =  db.get(query);
    		try 
    		{
    			if(rs.next()) 
    			{
    				this.tennhanvien = rs.getString("ten");
    			}
    		}
	        catch(Exception e) 
	        {
	        	e.printStackTrace();
	        }
	        
			if(this.thang != null && this.thang.trim().length() > 0 && this.nam != null && this.nam.trim().length() > 0)
			{
				try
				{
					int nam = Integer.parseInt(this.thang);
					int thang = Integer.parseInt(this.nam);
					
					Calendar mycal = new GregorianCalendar(nam, thang-1, 1);
					int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
					
					ngayList = new IKeHoachNhanVienNgay[daysInMonth];
					
					for(int i = 0; i < daysInMonth; i++) 
					{
						IKeHoachNhanVienNgay khNgay = new KeHoachNhanVienNgay("");
						khNgay.setNgay(String.valueOf(i+1));
						ngayList[i] = khNgay;
					}
				}
				catch(Exception e) 
				{
					 e.printStackTrace();
				}
			}
		}
	}

	public void init()
	{
		init(false);
	}

	@Override
	public ResultSet getNppRs() {
		return this.nppRs;
	}

	@Override
	public void setNppRs(ResultSet nppRs) {
		this.nppRs = nppRs;
	}

	@Override
	public ResultSet getTinhRs() {
		return this.tinhRs;
	}

	@Override
	public void setTinhRs(ResultSet tinhRs) {
		this.tinhRs = tinhRs;
	}

	@Override
	public ResultSet getQuanRs() {
		return this.quanRs;
	}

	@Override
	public void setQuanRs(ResultSet quanRs) {
		this.quanRs = quanRs;
	}

	@Override
	public IKeHoachNhanVienNgay[] getNgayList() {
		return this.ngayList;
	}

	@Override
	public void setNgayList(IKeHoachNhanVienNgay[] ngayList) {
		this.ngayList = ngayList;
	}

	@Override
	public void createRs() {
		Utility util = new Utility();
		
		String query = " SELECT PK_SEQ, TEN FROM NHAPHANPHOI WHERE TRANGTHAI = 1 and PK_SEQ IN " + util.quyen_npp(this.userId) + " and PK_SEQ IN ( SELECT NPP_FK FROM NHAPP_KBH WHERE KBH_FK IN " + util.quyen_kenh(this.userId) + " ) ";
		this.nppRs = this.db.get(query);
		
		query = " SELECT PK_SEQ, TEN FROM TINHTHANH ";
		this.tinhRs = this.db.get(query);
		
		query = " SELECT TINHTHANH_FK, PK_SEQ, TEN FROM QUANHUYEN ";
		this.quanRs = this.db.get(query);
	}

	public void closeDB()
	{
		try 
		{
			if(this.nppRs!=null) {
				nppRs.close();
			}
			if(this.tinhRs!=null) {
				tinhRs.close();
			}
			if(this.quanRs!=null) {
				quanRs.close();
			}
		} 
		catch(Exception e) 
		{
			
		}
		
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
	
	private String getDateTime(String pattern) 
	{
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
      
        return dateFormat.format(date);
	}

	@Override
	public String getTrangthai() {
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
}

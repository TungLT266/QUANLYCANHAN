package geso.traphaco.center.beans.kehoachnhanvien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.beans.kehoachnhanvien.IKeHoachNhanVienList;
import geso.traphaco.center.db.sql.dbutils;

public class KeHoachNhanVienList implements IKeHoachNhanVienList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String nhanvienId = "";
	String nhanvienTen = "";
	String loai = "";
	String thang;
	String nam;
	String Msg;
	ResultSet khnvRs; 
	
	dbutils db;
	
	public KeHoachNhanVienList(String[] param)
	{
		this.db = new dbutils();
		this.nhanvienTen = param[0];
		this.thang = param[1];
		this.nam = param[2];
		this.userId = "";
	}
	
	public KeHoachNhanVienList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nhanvienTen = "";
		this.thang = "";
		this.nam = "";
		this.Msg ="";
	}
	
	public KeHoachNhanVienList(boolean abc)
	{
		this.db = new dbutils();
		this.userId = "";
		this.nhanvienTen = "";
		this.thang = "";
		this.nam = "";
		this.Msg ="";
	}
	
	public ResultSet getKhnvRs() 
	{
		return this.khnvRs;
	}

	public void setKhnvRs(ResultSet khnvRs)
	{
		this.khnvRs = khnvRs;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTenNhanVien() 
	{
		return this.nhanvienTen;
	}

	public void setTenNhanVien(String nhanvienTen) 
	{
		this.nhanvienTen = nhanvienTen;
	}

	public String getLoai() 
	{
		return this.loai;
	}

	public void setLoai(String loai) 
	{
		this.loai = loai;
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
	
	public void init(String search) 
	{
		String query = "";
		
		if (search == null || search.trim().length() == 0)
		{
			String query2 = getIdNhanVienCapDuoiQuery();
			
			query = " select * from (" +
					" select a.pk_seq, nv.ten as nhanvien, a.thang, a.nam, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, isnull(a.trangthai, 0) as trangthai, '1' as loaikehoach " + 
					" from kehoachnv a " + 
					" inner join nhanvien nv on nv.pk_seq = a.nhanvien_fk " + 
					" inner join nhanvien b on a.nguoitao = b.pk_seq " + 
					" inner join nhanvien c on a.nguoisua = c.pk_seq " +
					" where a.nhanvien_fk = " + this.userId + 
					
					" union " +
					
					" select a.pk_seq, nv.ten as nhanvien, a.thang, a.nam, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, isnull(a.trangthai, 0) as trangthai, '0' as loaikehoach " +
					" from kehoachnv a " + 
					" inner join nhanvien nv on nv.pk_seq = a.nhanvien_fk " + 
					" inner join nhanvien b on a.nguoitao = b.pk_seq " + 
					" inner join nhanvien c on a.nguoisua = c.pk_seq " +
					" where a.nhanvien_fk in (" + query2 + ") " + 
					
					" ) a order by loaikehoach, a.trangthai, a.nam desc, a.thang desc ";
		}
		else
		{
			query = search;
		}
		System.out.println("[KeHoachNhanVienList.init] query = " + query);
		
		khnvRs = this.db.get(query);  
	}
	
	/**
	 * Lấy câu sql trả về các pk_seq nhân viên cấp dưới của nhân viên này
	 * @return String result
	 */
	public static String getIdNhanVienCapDuoiQuery(dbutils db, String userId) {
		String query = "";
		query = " SELECT LOAI FROM NHANVIEN WHERE PK_SEQ = " + userId;
		//System.out.println("[KeHoachNhanVienList.init] query = " + query);
		ResultSet rs = db.get(query);
		String loai = "";
		try {
			rs.next();
			loai = rs.getString("LOAI");
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		query = "null";
		if(loai == null) loai = "";
		if(loai.equals("4")) 
		{
			//Nhân viên trung tâm: Lấy tất cả nhân viên là rsm, asm, gsbh
			query = " SELECT PK_SEQ FROM NHANVIEN WHERE LOAI in (1,2,3)";
		} 
		else if(loai.equals("1")) 
		{
			//RSM: lấy tất cả nhân viên là asm và gsbh
			query = " SELECT PK_SEQ FROM NHANVIEN WHERE LOAI = 2 AND ASM_FK IN ( " +
					" 	SELECT DISTINCT(ASM_FK) FROM ASM_KHUVUC Y INNER JOIN ASM Z ON Z.PK_SEQ = Y.ASM_FK WHERE Y.KHUVUC_FK IN ( " +
					" 		SELECT PK_SEQ FROM KHUVUC WHERE VUNG_FK IN ( " +
					" 			SELECT VUNG_FK FROM BM_CHINHANH A INNER JOIN VUNG B ON B.PK_SEQ = A.VUNG_FK WHERE A.BM_FK IN ( " +
					" 				SELECT BM_FK FROM NHANVIEN WHERE PK_SEQ = "+userId+" " +
					" 			) AND B.TRANGTHAI = 1 " +
					" 		) AND TRANGTHAI = 1 " +
					" 	) AND Z.TRANGTHAI = 1 " +
					" ) AND TRANGTHAI = 1 " +
					" UNION " +
					" SELECT PK_SEQ FROM NHANVIEN WHERE LOAI = 3 AND GSBH_FK IN ( " +
					" 	SELECT DISTINCT(GSBH_FK) FROM GSBH_KHUVUC Q INNER JOIN GIAMSATBANHANG P ON Q.GSBH_FK = P.PK_SEQ WHERE Q.KHUVUC_FK IN ( " +
					" 		SELECT PK_SEQ FROM KHUVUC WHERE VUNG_FK IN ( " +
					" 			SELECT VUNG_FK FROM BM_CHINHANH A INNER JOIN VUNG B ON B.PK_SEQ = A.VUNG_FK WHERE A.BM_FK IN ( " +
					" 				SELECT BM_FK FROM NHANVIEN WHERE PK_SEQ = "+userId+" " +
					" 			) AND B.TRANGTHAI = 1 " +
					" 		) AND TRANGTHAI = 1 " +
					" 	) AND P.TRANGTHAI = 1 " +
					" ) AND TRANGTHAI = 1 ";
		} 
		else if(loai.equals("2")) 
		{
			//ASM: lấy tất cả PTT / GĐ CN 2
			query = " SELECT PK_SEQ FROM NHANVIEN WHERE LOAI = 3 AND GSBH_FK IN ( " +
					" 	SELECT DISTINCT(GSBH_FK) FROM GSBH_KHUVUC Q INNER JOIN GIAMSATBANHANG P ON Q.GSBH_FK = P.PK_SEQ WHERE Q.KHUVUC_FK IN ( " +
					" 		SELECT KHUVUC_FK FROM ASM_KHUVUC WHERE ASM_FK IN ( " +
					" 			SELECT ASM_FK FROM NHANVIEN WHERE PK_SEQ = "+userId+" " +
					" 		) AND NGAYBATDAU < GETDATE() AND GETDATE() < NGAYKETTHUC " +
					" 	) AND P.TRANGTHAI = 1 " +
					" ) AND TRANGTHAI = 1 ";
		}
		return query;
	}
	
	/**
	 * Lấy câu sql trả về các pk_seq nhân viên cấp dưới của nhân viên này
	 * @return String result
	 */
	private String getIdNhanVienCapDuoiQuery() {
		return KeHoachNhanVienList.getIdNhanVienCapDuoiQuery(this.db, this.userId);
	}
	
	public String getSearchQuery() 
	{
		String condition = "";
		if (loai.length() > 0) {
			condition = condition + " and nhanvien_fk in (select pk_seq from nhanvien where loai = " + loai + ") ";
		}
    	if (thang.length() > 0) {
    		condition = condition + " and thang = " + thang;
    	}
    	if (thang.length() > 0) {
    		condition = condition + " and nam = " + nam;
    	}
		
    	String query;
    	String query2 = getIdNhanVienCapDuoiQuery();
    	
		query = " select * from (" +
				" 	select a.pk_seq, nv.ten as nhanvien, a.thang, a.nam, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, isnull(a.trangthai, 0) as trangthai, '1' as loaikehoach " + 
				" 	from kehoachnv a " + 
				" 	inner join nhanvien nv on nv.pk_seq = a.nhanvien_fk " + 
				" 	inner join nhanvien b on a.nguoitao = b.pk_seq " + 
				" 	inner join nhanvien c on a.nguoisua = c.pk_seq " +
				" 	where a.nhanvien_fk = " + this.userId + " " + condition +
				
				" 	union " +
				
				" 	select a.pk_seq, nv.ten as nhanvien, a.thang, a.nam, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, isnull(a.trangthai, 0) as trangthai, '0' as loaikehoach " +
				" 	from kehoachnv a " + 
				" 	inner join nhanvien nv on nv.pk_seq = a.nhanvien_fk " + 
				" 	inner join nhanvien b on a.nguoitao = b.pk_seq " + 
				" 	inner join nhanvien c on a.nguoisua = c.pk_seq " +
				" 	where a.nhanvien_fk in (" + query2 + ") " + condition +
				
				" ) a order by loaikehoach, a.trangthai, a.nam desc, a.thang desc ";

    	//System.out.println("Tim kiem : "+query);
    	return query;
	}
	
	public void closeDB(){
		try {
			if(khnvRs != null) {
				khnvRs.close();
			}
		} catch(Exception e) { }
		if(this.db != null)
			this.db.shutDown();
	}


	public void setMsg(String Msg) {
	   this.Msg = Msg;
	}

	
	public String getMsg() {
		return this.Msg;
	}

	@Override
	public String getNhanVienId() {
		return nhanvienId;
	}

	@Override
	public void setNhanVienId(String nhanvienId) {
		this.nhanvienId = nhanvienId;
	}

	@Override
	public boolean delete(String id) 
	{
		String query = " SELECT count(*) as num from KEHOACHNV where pk_seq = " + id + " and nhanvien_fk = " + this.userId + " and trangthai = 1 ";
		System.out.println(query);
		ResultSet rs1 = db.get(query);
		try 
		{
			rs1.next();
			int count = rs1.getInt("num");
			if( count > 0) 
			{
				rs1.close();
				this.Msg = "Không thể xóa kế hoạch: Kế hoạch đã chốt hoặc không phải do bạn tạo!";
				return false;
			} 
			else 
			{
				db.getConnection().setAutoCommit(false);
				query = "DELETE KEHOACHNV_NPP WHERE KEHOACHNV_FK = '" + id + "'";
				if(!db.update(query)) {
					db.getConnection().rollback();
					this.Msg = "Không thể xóa kế hoạch nhân viên (" + query + ")";
					return false;
				}
				query = "DELETE KEHOACHNV_THITRUONG WHERE KEHOACHNV_FK = '" + id + "'";
				if(!db.update(query)) {
					db.getConnection().rollback();
					this.Msg = "Không thể xóa kế hoạch nhân viên (" + query + ")";
					return false;
				}
				query = "delete kehoachnv where pk_seq = '" + id + "'";
				if(!db.update(query)) {
					this.Msg = "Không thể xóa kế hoạch " + id;
					return false;
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
		} 
		catch(Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {	}
			this.Msg = "Xảy ra lỗi khi xóa kế hoạch " + id + " ("+e.getMessage()+")";
			return false;
		}
		return true;
	}

	@Override
	public boolean duyet(String id) {
		String ngaysua = getDateTime();
		String nguoisua = this.userId;
		
		//Cập nhật trạng thái kế hoạch nhân viên có trạng thái chưa duyệt của cấp dưới nhân viên này 
		String query = " UPDATE KEHOACHNV SET TRANGTHAI = 1, NGUOISUA = '"+nguoisua+"', NGAYSUA = '"+ngaysua+"' WHERE PK_SEQ = " + id + " AND TRANGTHAI = 0 AND NHANVIEN_FK IN (" + this.getIdNhanVienCapDuoiQuery() + ")";
		if(!db.update(query)) {
			this.Msg = "Không thể duyệt kế hoạch " + id;
			return false;
		}
		return true;
	}

	@Override
	public boolean moduyet(String id) {
		String query = "";
		String ngaysua = getDateTime();
		String nguoisua = this.userId;
		
		int thang = Integer.parseInt(getDateTime("MM"));
		int nam = Integer.parseInt(getDateTime("yyyy"));
		
		query = " SELECT THANG, NAM FROM KEHOACHNV WHERE PK_SEQ = " + id + " ";
		ResultSet rs = db.get(query);
		try {
			rs.next();
			int thangKh = rs.getInt("thang");
			int namKh = rs.getInt("nam");
			
			if(namKh < nam || (namKh == nam && thangKh < thang)) {
				this.Msg = "Chỉ được bỏ duyệt những kế hoạch có tháng lớn hơn hoặc bằng tháng hiện tại!";
				return false;
			}
			
		} catch(Exception e) {
			this.Msg = "Xảy ra lỗi khi duyệt kế hoạch " + id + "(" + e.getMessage() + ")";
			return false;
		}
		
		//Cập nhật trạng thái kế hoạch nhân viên có trạng thái chưa duyệt của cấp dưới nhân viên này 
		query = " UPDATE KEHOACHNV SET TRANGTHAI = 0, NGUOISUA = '"+nguoisua+"', NGAYSUA = '"+ngaysua+"' WHERE PK_SEQ = " + id + " AND TRANGTHAI = 1 AND NHANVIEN_FK IN (" + this.getIdNhanVienCapDuoiQuery() + ")";
		if(!db.update(query)) {
			this.Msg = "Không thể duyệt kế hoạch " + id;
			return false;
		}
		return true;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
      
        return dateFormat.format(date);
	}
	

	
	private String getDateTime(String pattern) 
	{
		Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
	}

}


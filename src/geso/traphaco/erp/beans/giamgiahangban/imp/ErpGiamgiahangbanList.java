package geso.traphaco.erp.beans.giamgiahangban.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.giamgiahangban.IErpGiamgiahangbanList;

public class ErpGiamgiahangbanList implements IErpGiamgiahangbanList 
{
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	String khachhang;
	
	ResultSet giamgiaRs;
	
	dbutils db;
	
	List<IThongTinHienThi> hienthiList;
	
	public ErpGiamgiahangbanList()
	{
		this.userId = "";

		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		this.khachhang = "";
		this.msg = "";
		
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
		this.db = new dbutils();
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getKhachhang() 
	{
		return this.khachhang;
	}

	public void setKhachhang(String khachhang) 
	{
		this.khachhang = khachhang;
	}

	public String getSohoadon(String Id) 
	{
		String soHD = "";
		String query = "SELECT HOADON_FK FROM  ERP_GIAMGIAHANGBAN_HOADON WHERE GIAMGIA_FK = " + Id + " ";
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				while(rs.next()){
					soHD = soHD + rs.getString("HOADON_FK") + ", ";
				}
				rs.close();
			}catch(java.sql.SQLException e){}
		}
		if(soHD.length() > 0){
			soHD = soHD.substring(0, soHD.length() - 2);
		}
		return soHD;
	}
	
	private String LayDuLieu(String id) {
		String laytk = "";
		if(laytk.trim().length()<=0){
			laytk = "SELECT '' NO_CO, '' PK_SEQ, '' NGAYHOADON, '' SOTIEN, '' SOHIEUTAIKHOAN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n " +
					"FROM ERP_GiamGiaHangBan \n " +
					"WHERE PK_SEQ = '"+id+"'";
		}
		
		return laytk;
	}

	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = " select a.SOHOADON , a.pk_seq, d.ten as nccTen, '' as poTen,  " +
						"a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua      " +
				  "from ERP_GiamGiaHangBan a inner join NhanVien b on a.nguoitao = b.pk_seq      " +
				  	"inner join nhanvien c on a.nguoisua = c.pk_seq inner join ERP_KhachHang d on a.khachhang_fk = d.pk_seq   " +
				  "order by a.pk_seq desc ";
		}
		
		System.out.println("__Nha may : " + sql);
		
		ResultSet rs = db.get(sql);
		
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{			
					ht = new ThongTinHienThi();
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("pk_seq"));						
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{
								kt = new DinhKhoanKeToan(rsKT.getString("PK_SEQ"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),
											rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
											rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
								ktList.add(kt);
							}
							
							rsKT.close();
						}
												
					// INIT					
						
						ht.setId(rs.getString("pk_seq"));
						ht.setKhachhang(rs.getString("nccTen"));
						ht.setSohoadon(rs.getString("SOHOADON"));
						ht.setTRANGTHAI(rs.getString("TRANGTHAI"));
						ht.setNGAYTAO(rs.getString("NGAYTAO"));
						ht.setNGUOITAO(rs.getString("NGUOITAO"));
						ht.setNGUOISUA(rs.getString("NGUOISUA"));
						ht.setNGAYSUA(rs.getString("NGAYSUA"));
						
						ht.setLayDinhkhoanKT(ktList);
					
					htList.add(ht);																	
				}
				rs.close();
			}
			
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		this.hienthiList = htList;
		
		//this.giamgiaRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if (this.hienthiList != null)
				this.hienthiList.clear();
			if(this.giamgiaRs != null)
				this.giamgiaRs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.db.shutDown();
		}
	}

	public ResultSet getGiamgiaRs() 
	{
		return this.giamgiaRs;
	}

	public void setGiamgiaRs(ResultSet giamgiaRs) 
	{
		this.giamgiaRs = giamgiaRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}


	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}


}

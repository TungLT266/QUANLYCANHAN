package geso.traphaco.center.beans.hoadonphelieu.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import geso.traphaco.center.beans.hoadonphelieu.imp.ErpHoaDonPL_SP;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.hoadonphelieu.IErpHoaDonPL_SP;
import geso.traphaco.center.beans.hoadonphelieu.IErpXuatkmkhonghd;

public class ErpXuatkmkhonghd implements IErpXuatkmkhonghd
{
	String userId;
	String congtyId;
	String id;

	String diengiai;
	String khId;
	ResultSet khRs;
	
	String ngayghinhan;
	String ngayhoadon;
	String kyhieuhoadon;
	String sohieuhoadon;
	
	String thuevat;
	String vat;
	String Bvat;
	String Avat;
	
	String[] tenSanpham;
	String[] donvitinh;
	String[] quydoi;
	String[] soluong;
	String[] dongia;
	String[] thanhtien;
	
	String msg;
	
	List<IErpHoaDonPL_SP> sanphamlist = new ArrayList<IErpHoaDonPL_SP>();
	Hashtable<String, String> sanpham_ghichu;
	
	Hashtable<String, String> sp_chitiet;
	
	String xuatcho;
	String mavuviec;
	
	dbutils db;
	
	public ErpXuatkmkhonghd()
	{
		this.userId = "";
		this.id = "";
		this.diengiai = "";
		this.khId = "";
		this.msg = "";
		
		this.ngayghinhan = "";
		this.ngayhoadon = "";
	
		this.sohieuhoadon = "";
		
		this.thuevat="10";
		this.vat = "0";
		this.Bvat = "0";
		this.Avat = "0";

		this.xuatcho = "0";
		this.mavuviec = "";
		
		this.sanpham_ghichu = new Hashtable<String, String>();	
		this.sp_chitiet = new Hashtable<String, String>();
		this.db = new dbutils();
	}
		
	public ErpXuatkmkhonghd(String id)
	{
		this.userId = "";
		this.id = id;
		this.diengiai = "";
		this.khId = "";
		this.msg = "";
		
		this.ngayghinhan = "";
		this.ngayhoadon = "";
		this.kyhieuhoadon = "";
		this.sohieuhoadon = "";
		
		this.thuevat="10";
		this.vat = "0";
		this.Bvat = "0";
		this.Avat = "0";
		
		this.xuatcho = "0";
		this.mavuviec = "";
		
		this.sanpham_ghichu = new Hashtable<String, String>();	
		this.sp_chitiet = new Hashtable<String, String>();
		
		this.db = new dbutils();
	}

	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
	
	public void init() 
	{
		
		String query =  " select a.ngayghinhan,  " +
						"      a.xuatcho, a.doituongId, isnull(a.mavuviec, '') as mavuviec, a.diengiai, isnull(a.bvat,0) as bvat, isnull(a.avat,0) avat, isnull(a.vat,0) as vat " +
						" from ERP_XUATKMKHONGHD a where a.pk_seq = '" + this.id + "'  " ;
		
		System.out.println(":: INIT:  " + query);
		ResultSet rs = db.get(query);
		NumberFormat formater = new DecimalFormat("##,###,###.#####");
		NumberFormat formater1 = new DecimalFormat("##,###,###");
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayghinhan = rs.getString("ngayghinhan");
					
					this.Bvat = formater1.format(rs.getDouble("bvat"));
					this.Avat = formater1.format(rs.getDouble("avat"));
					this.vat = formater1.format(rs.getDouble("vat"));
					
					this.xuatcho = rs.getString("xuatcho");
					this.khId = rs.getString("doituongId");
					this.mavuviec = rs.getString("mavuviec");
					this.diengiai = rs.getString("diengiai");
					
					this.sanpham_ghichu = new Hashtable<String, String>();
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		this.createRS();
			
		query = "select b.ma, b.ten, c.donvi as donvitinh, dongia, thuevat, vat, "+
				"        soluong, thanhtien, isnull(ghichu,'') as ghichu " +
				"from ERP_XUATKMKHONGHD_SANPHAM a inner join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq inner join DONVIDOLUONG c on b.dvdl_fk = c.pk_seq  "+
				"where xuatkm_fk = '" + this.id + "' ";
		System.out.println("Init SP: " + query);
		
		ResultSet rsSp = db.getScrol(query);

		try
		{
			if(rsSp!= null)
			{
				while(rsSp.next())
				{
					IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
					
					String idSanPham = "";
					String maSanpham = rsSp.getString("ma");
					String tenSanpham = rsSp.getString("ten");
					String donvitinh = rsSp.getString("donvitinh");
					String soluong = formater.format(rsSp.getDouble("soluong"));
					String dongia = formater.format(rsSp.getDouble("dongia"));
					String thuevat = formater.format(rsSp.getDouble("thuevat"));
					String vat = formater.format(rsSp.getDouble("vat"));
					String thanhtien = formater.format(rsSp.getDouble("thanhtien"));
					String ghichu = rsSp.getString("ghichu");
					
					if(soluong.equals("0") && dongia.equals("0") && thuevat.equals("0") && vat.equals("0"))
					{
						soluong = "";
						dongia = "";
						thuevat = "";
						vat = "";
					}
										
					sanpham.setId(idSanPham);
					sanpham.setMaSanPham(maSanpham);
					sanpham.setTenSanPham(tenSanpham);
					sanpham.setDonViTinh(donvitinh);
					sanpham.setSoLuong(soluong);
					sanpham.setDonGia(dongia);
					sanpham.setThuevat(thuevat);
					sanpham.setVat(vat);
					sanpham.setThanhTien(thanhtien);
					sanpham.setGhiChu1(ghichu);
			
					this.sanphamlist.add(sanpham);
				}
				rsSp.close();
			}
			
			//INIT SAN PHAM CHI TIET
			query = "select b.MA, a.SOLO, a.ngayhethan "+
					"from ERP_XUATKMKHONGHD_SANPHAM_CHITIET a inner join ERP_SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
					"where a.xuatkm_fk = '" + this.id + "' ";
			
			System.out.println("::: INIT CHI TIET: " + query);
			rsSp = db.get(query);
			Hashtable<String, String> sp_chitiet = new Hashtable<String, String>();
			if( rsSp != null )
			{
				while( rsSp.next() )
				{
					String key = rsSp.getString("MA") + "__" + rsSp.getString("SOLO") + "__" + rsSp.getString("ngayhethan");
					sp_chitiet.put(key, "1");
				}
				rsSp.close();
			}
			
			this.sp_chitiet = sp_chitiet;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	public boolean create()
	{	
		try 
		{
			if(this.ngayghinhan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày ghi nhận.";
				return false;
			}

			if(this.khId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng.";
				return false;
			}

			if(this.sanphamlist.size() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm.";
				return false;
			}
			
			for(int i = 0; i < this.sanphamlist.size(); i++ )
			{
				IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
				sanpham = sanphamlist.get(i);

				if( ( sanpham.getTenSanPham() == null ) || ( sanpham.getTenSanPham()!= null && sanpham.getTenSanPham().length() <= 0 )  )
				{
					this.msg = "Vui lòng kiểm tra lại thông tin chi tiết";
					return false;
				}

				if( sanpham.getTenSanPham().trim().length() > 0 &&  sanpham.getThanhTien().trim().length() <= 0 )
				{
					this.msg = "Vui lòng kiểm tra lại thành tiền";
					return false;					
				}
			}

			db.getConnection().setAutoCommit(false);

			String query = " insert ERP_XUATKMKHONGHD(ngayghinhan, bvat, vat, avat, xuatcho, doituongId, mavuviec, diengiai,  trangthai, nguoitao, ngaytao, nguoisua, ngaysua )  " +
						   "values('" + this.ngayghinhan + "', '" + this.Bvat.replaceAll(",", "") + "', '" + this.vat.replaceAll(",", "") + "', '" + this.Avat.replaceAll(",", "") + "', '" + this.xuatcho + "', '" + this.khId + "', N'" + this.mavuviec + "',  N'" + this.diengiai + "',  '0'," +
						   " '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "' ) ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_XUATKMKHONGHD " + query;
				db.getConnection().rollback();
				return false;
			}

			query = " select IDENT_CURRENT('ERP_XUATKMKHONGHD') as giamgiaId ";
			ResultSet rs_ = db.get(query);
			rs_.next();
			this.id = rs_.getString("giamgiaId");
			rs_.close();

			int count= 0;	
			while(count < this.sanphamlist.size() )
			{
				IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
				sanpham = sanphamlist.get(count);
				
				if(sanpham.getTenSanPham().trim().length() > 0)
				{
					query = "insert ERP_XUATKMKHONGHD_SANPHAM(xuatkm_fk, sanpham_fk, soluong, dongia, thuevat, vat, thanhtien, ghichu)  " +
							"select '" + this.id + "', pk_seq, '" + sanpham.getSoLuong().replaceAll(",", "") + "'," +
							" '" + sanpham.getDonGia().replaceAll(",", "") + "', '" + sanpham.getThuevat().replaceAll(",", "") + "', '" + sanpham.getVat().replaceAll(",", "") + "', '" + sanpham.getThanhTien().trim().replaceAll(",", "") + "' , N'"+ sanpham.getGhiChu1() +"'  " +
							"from ERP_SANPHAM where ma = '" + sanpham.getMaSanPham() + "'	";

					System.out.println("CHEN SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_XUATKMKHONGHD_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				}

				count ++;
			}
			
			if( this.sp_chitiet != null )
			{
				Enumeration<String> keys = this.sp_chitiet.keys(); 
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					String[] arr = key.split("__");
					
					query = "insert ERP_XUATKMKHONGHD_SANPHAM_CHITIET( xuatkm_fk, SANPHAM_FK, solo, ngayhethan ) " +
							"select '" + this.id + "', PK_SEQ, N'" + arr[1] + "', '" + arr[2] + "' " +
							"from ERP_SANPHAM where MA = '" + arr[0] + "' ";
					
					System.out.println("1.Insert PB - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_XUATKMKHONGHD_SANPHAM_CHITIET: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}

		return true;
	}
	
	public boolean update() 
	{
		try 
		{
			if(this.ngayghinhan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày ghi nhận.";
				return false;
			}
			
			if(this.khId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng.";
				return false;
			}
			
			if(this.sanphamlist.size() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm.";
				return false;
			}

			for(int i = 0; i < this.sanphamlist.size(); i++ )
			{
				IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
				sanpham = sanphamlist.get(i);
				
				if( ( sanpham.getTenSanPham() == null ) || ( sanpham.getTenSanPham()!= null && sanpham.getTenSanPham().length() <= 0 )  )
				{
					this.msg = "Vui lòng kiểm tra lại thông tin ";
					return false;
				}
				
				if( sanpham.getTenSanPham().trim().length() > 0 &&  sanpham.getThanhTien().trim().length() < 0 )
				{
					this.msg = "Vui lòng kiểm tra lại thành tiền.";
					return false;
				}
			}

			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_XUATKMKHONGHD set ngayghinhan = '" + this.ngayghinhan + "',  " +
							"bvat = '" + this.Bvat.replaceAll(",", "") + "', vat = '" + this.vat.replaceAll(",", "") + "', avat = '" + this.Avat.replaceAll(",", "") + "', " +
							"xuatcho = '" + this.xuatcho + "', doituongId = '" + this.khId + "', mavuviec = N'" + this.mavuviec + "', diengiai = N'" + this.diengiai + "', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "'   where pk_seq = '" + this.id + "' ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_XUATKMKHONGHD " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_XUATKMKHONGHD_SANPHAM where xuatkm_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_XUATKMKHONGHD_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_XUATKMKHONGHD_SANPHAM_CHITIET where xuatkm_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_XUATKMKHONGHD_SANPHAM_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
						
			int count= 0;	
			while(count < this.sanphamlist.size() )
			{
				IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
				sanpham = sanphamlist.get(count);
				if(sanpham.getTenSanPham().trim().length() > 0)
				{
					query = "insert ERP_XUATKMKHONGHD_SANPHAM(xuatkm_fk, sanpham_fk, soluong, dongia, thuevat, vat, thanhtien, ghichu)  " +
							"select '" + this.id + "', pk_seq, '" + sanpham.getSoLuong().replaceAll(",", "") + "'," +
							" '" + sanpham.getDonGia().replaceAll(",", "") + "', '" + sanpham.getThuevat().replaceAll(",", "") + "', '" + sanpham.getVat().replaceAll(",", "") + "', '" + sanpham.getThanhTien().trim().replaceAll(",", "") + "' , N'"+ sanpham.getGhiChu1() +"'  " +
							"from ERP_SANPHAM where ma = '" + sanpham.getMaSanPham() + "'	";

					System.out.println("CHEN SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_XUATKMKHONGHD_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				}

				count ++;
			}
			
			if( this.sp_chitiet != null )
			{
				Enumeration<String> keys = this.sp_chitiet.keys(); 
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					String[] arr = key.split("__");
					
					query = "insert ERP_XUATKMKHONGHD_SANPHAM_CHITIET( xuatkm_fk, SANPHAM_FK, solo, ngayhethan ) " +
							"select '" + this.id + "', PK_SEQ, N'" + arr[1] + "', '" + arr[2] + "' " +
							"from ERP_SANPHAM where MA = '" + arr[0] + "' ";
					
					System.out.println("1.Insert PB - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_XUATKMKHONGHD_SANPHAM_CHITIET: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	
	public void DbClose() 
	{
		try 
		{
			sanphamlist.clear();
			this.db.shutDown();
		} 
		catch (Exception e) {}
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}


	
	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}



	public String[] getTensansham() {
		
		return this.tenSanpham;
	}

	
	public void setTensanpham(String[] tensanpham) {
		
		this.tenSanpham = tensanpham;
	}

	
	public String[] getSoluong() {
		
		return this.soluong;
	}

	
	public void setSoluong(String[] soluong) {
		
		this.soluong = soluong;
	}
	
	public void createRS() 
	{
		//Lấy hết NPP
		String query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and pk_seq != '1' ";
		if(this.xuatcho.equals("1"))
			query = " select pk_seq, TEN from NHAPHANPHOI where trangthai = '1' and isKhachhang = '1' ";
		this.khRs = db.get(query);
	}

	
	public String[] getDongia() {
		
		return this.dongia;
	}

	
	public void setDongia(String[] dongia) {
		
		this.dongia = dongia;
	}

	
	public String[] getTongtien() {
		
		return this.thanhtien;
	}

	
	public void setTongtien(String[] tongtien) {
		
		this.thanhtien = tongtien;
	}
	
   public String getNgayghinhan() {
		
		return this.ngayghinhan;
	}

	
	public void setNgayghinhan(String ngayghinhan) {
		
		this.ngayghinhan = ngayghinhan;
	}

	
	public String getNgayhoadon() {
		
		return this.ngayhoadon;
	}

	
	public void setNgayhoadon(String ngayhoadon) {
		
		this.ngayhoadon = ngayhoadon;
	}

	
	public String getKyhieuhoadon() {
		
		return this.kyhieuhoadon;
	}

	
	public void setKyhieuhoadon(String kyhieuhd) {
		
		this.kyhieuhoadon = kyhieuhd;
	}

	
	public String getSohoadon() {
		
		return this.sohieuhoadon;
	}

	
	public void setSohoadon(String sohoadon) {
		
		this.sohieuhoadon = sohoadon;
	}

	public String getThuevat() {
		
		return this.thuevat;
	}

	
	public void setThuevat(String thuevat) {
		
		this.thuevat = thuevat;
	}
	
	public String getVat() {
		
		return this.vat;
	}

	
	public void setVat(String vat) {
		
		this.vat = vat;
	}

	
	public String getBvat() {
		
		return this.Bvat;
	}

	
	public void setBvat(String bvat) {
		
		this.Bvat = bvat;
	}

	
	public String getAvat() {
		
		return this.Avat;
	}
	
	public void setAvat(String avat) {
		
		this.Avat = avat;
	}

	public String[] getDvt() {
		
		return this.donvitinh;
	}

	public void setDvt(String[] donvi) {
		
		this.donvitinh = donvi;
	}

	public String[] getQuyDoi() {
		return this.quydoi;
	}

	public void setQuyDoi(String[] quyDoi) {
		this.quydoi = quyDoi;
	}

	


	public String CreateLSIN(String hdId, String loaihd ) 
	{


		String msg= "" ;
		String query= "" ;
		
		try
		{
			// 0.Tính số lần in
			query = " SELECT count(*) as dem FROM LICHSUIN WHERE SOCHUNGTU = '"+ hdId +"' AND LOAIHD = '2' ";
			int solanin = 0;
			ResultSet rs = db.get(query);
			if(rs!= null)
			{
				while(rs.next())
				{
					solanin = rs.getInt("dem");
				}
				rs.close();
			}
			
			solanin = solanin + 1;
			
			
			db.getConnection().setAutoCommit(false);
			
			// 1.Lưu vào bảng tổng LICHSUIN
			query = " INSERT INTO LICHSUIN " +
					" SELECT PK_SEQ, SOHOADON, TRANGTHAI, '"+ this.getDate() +"', '"+ this.userId +"', "+ solanin +" , '', "+ loaihd +" " +
					" FROM ERP_HOADONPHELIEU " +
					" WHERE PK_SEQ = '"+ hdId +"' ";
			System.out.println("Câu insert LICHSUIN "+query);
			if(!db.update(query))
			{
				msg= "Không thể lưu vào bảng LICHSUIN " + query ;
				db.getConnection().rollback();
			}
			
			String lsId = "";
			query = "select IDENT_CURRENT('LICHSUIN') as lsId";
			
			ResultSet rsLs = db.get(query);						
			if(rsLs.next())
			{
				lsId = rsLs.getString("lsId");
				rsLs.close();
			}
			
			// 2.Lưu vào bảng LICHSUIN_HOADONPHELIEU
			query ="INSERT INTO LICHSUIN_HOADONPHELIEU \n"+
				   " SELECT " + lsId + ", a.ngayghinhan, a.ngayhoadon, a.kyhieuhoadon, a.sohoadon, a.vat, a.doanhthu_fk, " +
				   "       a.khachhang_fk, a.trungtamdoanhthu_fk, a.diengiai, a.tungay, a.denngay , a.loaick," +
				   "      (select sum(thanhtien)" +
				   "       from erp_hoadonphelieu_sanpham" +
				   "       where hoadonphelieu_fk = '" + hdId + "'" +
				   "       group by hoadonphelieu_fk  ) as bvat " +
				   " FROM erp_hoadonphelieu a  " +
				   " WHERE a.pk_seq = '" + hdId+ "'";
			
			System.out.println("Câu insert LICHSUIN_HOADONPHELIEU "+query);
			if(db.updateReturnInt(query) <= 0)
			{
				msg= "Không thể lưu vào bảng LICHSUIN_HOADONPHELIEU " + query ;
				db.getConnection().rollback();
			}
			
			
			// 4.Lưu vào bảng LICHSUIN_SANPHAM
			query = "INSERT INTO LICHSUIN_SANPHAMHDPL  \n"+
					" select " + lsId + ",  diengiai, isnull(donvitinh, '') as donvitinh, dongia, soluong, thanhtien, ghichu " +
					" from ERP_HoaDonPheLieu_SanPham" +
					" where hoadonphelieu_fk = '" + hdId + "' ";
			
			System.out.println("Câu insert LICHSUIN_SANPHAMHDPL "+query);
			if(db.updateReturnInt(query) <= 0)
			{
				msg= "Không thể lưu vào bảng LICHSUIN_SANPHAMHDPL " + query ;
				db.getConnection().rollback();
			}
						
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);			
			
		}catch(Exception e)
		{
			msg = " Lỗi phát sinh trong quá trình lưu. ";
			e.printStackTrace();
		}
		
		System.out.println("Lưu vào LỊCH SỬ IN thành công");
		return msg;
	
	
		
	}
	
	private String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);

	}

	public void initInLS(String lsinId) 
	{

		String query = " select * " +
					   " from LICHSUIN_HOADONPHELIEU " +
					   " where LICHSUIN_FK = '" + lsinId + "'";
		System.out.println("CAU QUERY "+query);
		ResultSet rs = db.get(query);
		NumberFormat formater = new DecimalFormat("##,###,###.#####");
		NumberFormat formater1 = new DecimalFormat("##,###,###");
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayghinhan = rs.getString("ngayghinhan");
					this.ngayhoadon = rs.getString("ngayhoadon");
					this.kyhieuhoadon = rs.getString("kyhieuhoadon");
					this.sohieuhoadon = rs.getString("sohoadon");
					this.Bvat = formater.format(rs.getDouble("bvat"));
					this.vat = rs.getString("vat") ;
					this.Avat = formater1.format(rs.getDouble("bvat")+ (rs.getDouble("bvat")*rs.getDouble("vat")/100));
					
					this.khId = rs.getString("khachhang_fk");

					this.diengiai = rs.getString("diengiai");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		this.createRS();
		

		
		query = " select * " +
				" from LICHSUIN_SANPHAMHDPL " +
				" where LICHSUIN_FK = '" + lsinId + "' ";
		System.out.println("Init SP: " + query);
		
		ResultSet rsSp = db.get(query);
		List<IErpHoaDonPL_SP> hdList = new ArrayList<IErpHoaDonPL_SP>();
		
		if(rsSp!= null)
		{
			try 
			{
				IErpHoaDonPL_SP sanpham = null;
				while(rsSp.next())
				{
					sanpham = new ErpHoaDonPL_SP();
					sanpham.setTenSanPham(rsSp.getString("diengiai"));
					sanpham.setDonViTinh(rsSp.getString("donvitinh"));				
					sanpham.setDonGia(rsSp.getString("dongia"));
					sanpham.setSoLuong(rsSp.getString("soluong"));
					sanpham.setThanhTien(rsSp.getString("thanhtien"));
					sanpham.setGhiChu1(rsSp.getString("ghichu"));
					hdList.add(sanpham);	
			
				}
				rsSp.close();
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("____EXception SanPham: " + e.getMessage());
			}
		}
		this.sanphamlist = hdList;

		
	
	}

	public List<IErpHoaDonPL_SP> GetSanPhamList() 
	{
		return this.sanphamlist;
	}

	public void setSanPhamList(List<IErpHoaDonPL_SP> SanPhamList) 
	{
		this.sanphamlist = SanPhamList;
		
	}
	
	public void setSanphamGhiChu(Hashtable<String, String> sanpham_ghichu) {
		
		this.sanpham_ghichu = sanpham_ghichu;
	}

	
	public Hashtable<String, String> getSanphamGhiChu() {
		
		return this.sanpham_ghichu;
	}


	public ResultSet getKhRs() {
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		this.khRs = khRs;
		
	}
	
	public String getXuatcho() {

		return this.xuatcho;
	}

	public void setXuatcho(String xuatcho) {
		
		this.xuatcho = xuatcho;
	}

	
	public String getMavuviec() {

		return this.mavuviec;
	}


	public void setMavuviec(String mavuviec) {
		
		this.mavuviec = mavuviec;
	}
	
	public Hashtable<String, String> getSp_Chitiet() {
		
		return this.sp_chitiet;
	}

	
	public void setSp_Chitiet(Hashtable<String, String> sp_chitiet) {
		
		this.sp_chitiet = sp_chitiet;
	}
	
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		//Mặc định lấy kho khuyến mại
		String khott_fk = "100067";
		
		String query = "select distinct SOLO, NGAYHETHAN " + 
					   " from  ERP_KHOTT_SP_CHITIET " + 
					   " where KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) ";
	
		System.out.println("::: LAY SO LO: " + query);
		return db.get(query);
	}

	
}

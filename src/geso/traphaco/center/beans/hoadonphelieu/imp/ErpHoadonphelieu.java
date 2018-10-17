package geso.traphaco.center.beans.hoadonphelieu.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import geso.traphaco.center.beans.hoadonphelieu.imp.ErpHoaDonPL_SP;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.beans.hoadonphelieu.IErpHoaDonPL_SP;
import geso.traphaco.center.beans.hoadonphelieu.IErpHoadonphelieu;


public class ErpHoadonphelieu implements IErpHoadonphelieu
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
	String nppId;
	
	List<IErpHoaDonPL_SP> sanphamlist = new ArrayList<IErpHoaDonPL_SP>();
	Hashtable<String, String> sanpham_ghichu;
	
	dbutils db;
	
	public ErpHoadonphelieu()
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
		
		this.nppId = "";
		
		this.db = new dbutils();
		this.kyhieuhoadon = getkyhieuhd_moinhat();
		tenSanpham = new String[0];
		donvitinh = new String[0];
		quydoi = new String[0];
		soluong = new String[0];
		dongia = new String[0];
		thanhtien = new String[0];
		
		this.sanpham_ghichu = new Hashtable<String, String>();	
	}
		
	
	private String getkyhieuhd_moinhat() {
		// TODO Auto-generated method stub
		try{
			String query=" SELECT isnull(KYHIEUHOADON,'') as KYHIEUHOADON FROM erp_hoadonphelieu WHERE PK_SEQ = (SELECT MAX(PK_SEQ) FROM erp_hoadonphelieu ) ";
			ResultSet rs=db.get(query);
			if(rs.next()){
			
				return rs.getString("KYHIEUHOADON");
			}
			rs.close();
			
		}catch(Exception er){
			er.printStackTrace();
			return "";
		}
		
		return "";
	}

	public ErpHoadonphelieu(String id)
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
		
		this.nppId = "";
		
		this.db = new dbutils();
		
		tenSanpham = new String[0];
		donvitinh = new String[0];
		quydoi = new String[0];
		soluong = new String[0];
		dongia = new String[0];
		thanhtien = new String[0];
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
		
		String query =  " select a.ngayghinhan, a.ngayhoadon, a.kyhieuhoadon, a.sohoadon, " +
						"      a.khachhang_fk,  a.diengiai, isnull(a.bvat,0) as bvat, isnull(a.avat,0) avat, isnull(a.vat,0) as vat " +
						" from erp_hoadonphelieuTT a inner join nhaphanphoi b on a.khachhang_fk = b.pk_seq " ;
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
					this.Bvat = formater1.format(rs.getDouble("bvat"));
					this.Avat = formater1.format(rs.getDouble("avat"));
					this.vat = formater1.format(rs.getDouble("vat"));
					this.khId = rs.getString("khachhang_fk");
					this.diengiai = rs.getString("diengiai");
					
					this.sanpham_ghichu = new Hashtable<String, String>();
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		this.createRS();
		

		
		query = "select hoadonphelieu_fk, diengiai, isnull(donvitinh, '') as donvitinh, dongia, thuevat, vat, "+
				"        soluong, thanhtien, isnull(ghichu,'') as ghichu " +
				"from ERP_HoaDonPheLieu_SanPhamTT "+
				"where hoadonphelieu_fk = '" + this.id + "' ";
		System.out.println("Init SP: " + query);
		
		ResultSet rsSp = db.getScrol(query);
		
		int count = 0;
		
		try
		{
			if(rsSp!= null)
			{
				while(rsSp.next())
				{
					IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
					
					String idSanPham = rsSp.getString("hoadonphelieu_fk");
					String tenSanpham = rsSp.getString("diengiai");
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
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	

		
	}
	
	public void initExcel() 
		{
			String query = " select a.ngayghinhan, a.ngayhoadon, a.kyhieuhoadon, a.sohoadon, a.vat, " +
						   "       a.khachhang_fk, b.ten as khTen, a.diengiai, a.tungay, a.denngay , " +
						   "      (select sum(thanhtien)" +
						   "       from erp_hoadonphelieu_sanphamTT" +
						   "       where hoadonphelieu_fk = '" + this.id + "'" +
						   "       group by hoadonphelieu_fk  ) as bvat " +
						   " from erp_hoadonphelieuTT a inner join erp_khachhang b on a.khachhang_fk = b.pk_seq " +
						   " where a.pk_seq = '" + this.id + "'";
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
			
	
			
			query = " select diengiai, isnull(donvitinh, '') as donvitinh, dongia, soluong, thanhtien, isnull(ghichu,'') as ghichu " +
					" from ERP_HoaDonPheLieu_SanPham" +
					" where hoadonphelieu_fk = '" + this.id + "' ";
			System.out.println("Init SP1: " + query);
			
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
	
	public boolean createGiamgia()
	{	
		this.getNppInfo();
		try 
		{
			
			
			if(this.ngayghinhan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày ghi nhận.";
				return false;
			}
			
			if(this.ngayhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày hóa đơn.";
				return false;
			}
			
			if(this.sohieuhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số hóa đơn.";
				return false;
			}
			
			if(this.kyhieuhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập ký hiệu hóa đơn.";
				return false;
			}
			
			if(this.khId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng.";
				return false;
			}

			String query = "";			
			
			// check so hoa don && ky hieu hoa don 
			int sodong=0;
			int sodong_OTC =0;
			int sodong_ETC =0;
			
			String sql="select count(*) as sodong from erp_hoadonphelieuTT where sohoadon='"+this.sohieuhoadon+"' and kyhieuhoadon ='"+this.kyhieuhoadon+"' and trangthai != 2 ";
			ResultSet checkHD = db.get(sql);
			while(checkHD.next())
			{
				sodong= checkHD.getInt("sodong");
			}
			if(sodong > 0 )
			{
				this.msg = "Số hóa đơn: '"+this.sohieuhoadon+"' và ký hiệu hóa đơn: '"+this.kyhieuhoadon+"' đã tồn tại trong Hóa đơn khác, vui lòng nhập lại ";
				return false;
			}
			
			// OTC
			/* sql="select count(*) as sodong from ERP_hoadon where sohoadon='"+this.sohieuhoadon+"' and kyhieu ='"+this.kyhieuhoadon+"' and trangthai != 3  ";
				ResultSet checkHD_OTC = db.get(sql);
				while(checkHD_OTC.next())
				{
					sodong_OTC= checkHD_OTC.getInt("sodong");
				}
				
			if(sodong_OTC > 0 )
			{
				this.msg = "Số hóa đơn: '"+this.sohieuhoadon+"' và ký hiệu hóa đơn: '"+this.kyhieuhoadon+"' đã tồn tại trong Hóa đơn , vui lòng nhập lại ";
				return false;
			}*/
			
			//ETC
		/*	 sql="select count(*) as sodong from hoadon where sohoadon='"+this.sohieuhoadon+"' and kyhieu ='"+this.kyhieuhoadon+"' and trangthai != 3 and npp_fk = "+ this.nppId +" ";
				ResultSet checkHD_ETC = db.get(sql);
				while(checkHD_OTC.next())
				{
					sodong_ETC= checkHD_ETC.getInt("sodong");
				}
				
			if(sodong_ETC > 0 )
			{
				this.msg = "Số hóa đơn: '"+this.sohieuhoadon+"' và ký hiệu hóa đơn: '"+this.kyhieuhoadon+"' đã tồn tại trong Hóa đơn ETC, vui lòng nhập lại ";
				return false;
			}
			*/
			
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
			
			double vat1=0;
			try{
			vat1=Double.parseDouble(vat);
			}catch(Exception er){
				this.msg = "Vui lòng kiểm tra lại vat";
				return false;
			}
			
			
			String tmp="0000000"+this.sohieuhoadon;
			this.sohieuhoadon = ( tmp).substring(tmp.length()-7 ,tmp.length());
			
			
			
			db.getConnection().setAutoCommit(false);
	
			
			 query = " insert erp_hoadonphelieuTT(ngayghinhan, ngayhoadon, kyhieuhoadon, sohoadon, bvat, vat, avat, khachhang_fk, diengiai,  trangthai, nguoitao, ngaytao, nguoisua, ngaysua, npp_fk)  " +
							"values('" + this.ngayghinhan + "', '" + this.ngayhoadon + "', '" + this.kyhieuhoadon + "', '" + this.sohieuhoadon + "', '" + this.Bvat.replaceAll(",", "") + "', '" + this.vat.replaceAll(",", "") + "', '" + this.Avat.replaceAll(",", "") + "', '" + this.khId + "',  N'" + this.diengiai + "',  '0'," +
									" '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '1') ";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới erp_hoadonphelieu " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " select IDENT_CURRENT('ERP_HoaDonPheLieu') as giamgiaId ";
			ResultSet rs_ = db.get(query);
			if(rs_.next())
				this.id = rs_.getString("giamgiaId");
			
			
			int count= 0;
			
			while(count < this.sanphamlist.size() )
			{
				IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
				sanpham = sanphamlist.get(count);
				if(sanpham.getTenSanPham().trim().length() > 0)
				{
					query = "insert ERP_HoaDonPheLieu_SanPhamTT(hoadonphelieu_fk, diengiai, donvitinh, soluong, dongia, thuevat, vat, thanhtien, ghichu)  " +
					        "values('" + this.id + "', N'" + sanpham.getTenSanPham() + "', N'" + sanpham.getDonViTinh()+ "', '" + sanpham.getSoLuong().replaceAll(",", "") + "'," +
							" '" + sanpham.getDonGia().replaceAll(",", "") + "', '" + sanpham.getThuevat().replaceAll(",", "") + "', '" + sanpham.getVat().replaceAll(",", "") + "', '" + sanpham.getThanhTien().trim().replaceAll(",", "") + "' , N'"+ sanpham.getGhiChu1() +"' ) ";
			
					System.out.println("Câu chèn bảng chi tiết "+query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_HoaDonPheLieu_SanPham " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				count ++;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
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
	
	public boolean updateGiamgia() 
	{
		this.getNppInfo();
		try 
		{
			
			if(this.ngayghinhan.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày ghi nhận.";
				return false;
			}
			
			if(this.ngayhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày hóa đơn.";
				return false;
			}
			
			if(this.sohieuhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập số hóa đơn.";
				return false;
			}
			
			if(this.kyhieuhoadon.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập ký hiệu hóa đơn.";
				return false;
			}
			
			if(this.khId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng.";
				return false;
			}
			
			
			// check so hoa don && ky hieu hoa don 
				int sodong=0;
				int sodong_OTC =0;
				int sodong_ETC =0;
				
				String sql=" select count(*) as sodong"
						 + " from erp_hoadonphelieuTT"
						 + " where sohoadon='"+this.sohieuhoadon+"' and kyhieuhoadon ='"+this.kyhieuhoadon+"'"
						 + "       and trangthai != 2  and pk_seq != "+ this.id +" ";
				ResultSet checkHD = db.get(sql);
				while(checkHD.next())
				{
					sodong= checkHD.getInt("sodong");
				}
				if(sodong > 0 )
				{
					this.msg = "Số hóa đơn: '"+this.sohieuhoadon+"' và ký hiệu hóa đơn: '"+this.kyhieuhoadon+"' đã tồn tại trong Hóa đơn khác, vui lòng nhập lại ";
					return false;
				}
				
				// OTC
				 sql="select count(*) as sodong from ERP_hoadon where sohoadon='"+this.sohieuhoadon+"' and kyhieu ='"+this.kyhieuhoadon+"' and trangthai != 3  ";
					ResultSet checkHD_OTC = db.get(sql);
					while(checkHD_OTC.next())
					{
						sodong_OTC= checkHD_OTC.getInt("sodong");
					}
					
				if(sodong_OTC > 0 )
				{
					this.msg = "Số hóa đơn: '"+this.sohieuhoadon+"' và ký hiệu hóa đơn: '"+this.kyhieuhoadon+"' đã tồn tại trong Hóa đơn OTC, vui lòng nhập lại ";
					return false;
				}
				
				//ETC
				/* sql="select count(*) as sodong from hoadon where sohoadon='"+this.sohieuhoadon+"' and kyhieu ='"+this.kyhieuhoadon+"' and trangthai != 3 and npp_fk = "+ this.nppId +" ";
					ResultSet checkHD_ETC = db.get(sql);
					while(checkHD_OTC.next())
					{
						sodong_ETC= checkHD_ETC.getInt("sodong");
					}
					
				if(sodong_ETC > 0 )
				{
					this.msg = "Số hóa đơn: '"+this.sohieuhoadon+"' và ký hiệu hóa đơn: '"+this.kyhieuhoadon+"' đã tồn tại trong Hóa đơn ETC, vui lòng nhập lại ";
					return false;
				}
			*/
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
			double vat1=0;
			try{
			vat1=Double.parseDouble(thuevat);
			}catch(Exception er){
				this.msg = "Vui lòng kiểm tra lại vat";
				return false;
			}

			db.getConnection().setAutoCommit(false);
			
			String query = "update erp_hoadonphelieuTT set ngayghinhan = '" + this.ngayghinhan + "', ngayhoadon = '" + this.ngayhoadon + "', kyhieuhoadon = '" + this.kyhieuhoadon + "', sohoadon = '" + this.sohieuhoadon + "', " +
							"bvat = '" + this.Bvat.replaceAll(",", "") + "', vat = '" + this.vat.replaceAll(",", "") + "', avat = '" + this.Avat.replaceAll(",", "") + "', " +
							"khachhang_fk = '" + this.khId + "', diengiai = N'" + this.diengiai + "', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "'   where pk_seq = '" + this.id + "' ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật erp_hoadonphelieu " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_HoaDonPheLieu_SanPham where hoadonphelieu_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_HoaDonPheLieu_SanPham " + query;
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
					query = "insert ERP_HoaDonPheLieu_SanPhamTT(hoadonphelieu_fk, diengiai, donvitinh, soluong, dongia, thuevat, vat, thanhtien, ghichu)  " +
					        "values('" + this.id + "', N'" + sanpham.getTenSanPham() + "', N'" + sanpham.getDonViTinh()+ "', '" + sanpham.getSoLuong().replaceAll(",", "") + "'," +
							" '" + sanpham.getDonGia().replaceAll(",", "") + "', '" + sanpham.getThuevat().replaceAll(",", "") + "', '" + sanpham.getVat().replaceAll(",", "") + "', '" + sanpham.getThanhTien().trim().replaceAll(",", "") + "' , N'"+ sanpham.getGhiChu1() +"' ) ";
			
					System.out.println("Câu chèn bảng chi tiết "+query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_HoaDonPheLieu_SanPham " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				count ++;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
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
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
	}
	
	public void createRS() 
	{
		this.getNppInfo();
	/*	String query = " select PK_SEQ, MAFAST + '-' + TEN as TEN" +
			       	   " from KHACHHANG" +
			       	   " where TRANGTHAI = '1' and NPP_FK= "+ this.nppId +"  ";*/
		String query="select  PK_SEQ, MAFAST + '-' + TEN AS TEN from nhaphanphoi where TRANGTHAI = '1' and tructhuoc_fk=1 and loainpp=4 ";
		
		System.out.println("Lấy khách hàng: "+query);
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

	
}

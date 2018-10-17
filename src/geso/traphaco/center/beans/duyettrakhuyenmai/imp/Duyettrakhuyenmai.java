package geso.traphaco.center.beans.duyettrakhuyenmai.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspose.cells.Cell;

import geso.traphaco.center.beans.duyettrakhuyenmai.IDuyettrakhuyenmai;
import geso.traphaco.center.db.sql.dbutils;

public class Duyettrakhuyenmai implements IDuyettrakhuyenmai
{
	String userId;
	String id;
	
	ResultSet ctkmRs;
	String ctkmId;
	
	String ngayduyet;
	String diengiai;
	
	ResultSet khachhangRs;
	String khIds;
	
	String[] nppId;
	String[] nppTen;
	String[] khId;
	String[] khTen;
	String[] doanhso;
	String[] dat;
	String[] soxuat;
	String[] tongtien;
	String[] donvithuong;

	String msg;

	dbutils db;
	
	public Duyettrakhuyenmai()
	{
		this.id = "";
		this.ngayduyet = "";
		this.diengiai = "";
		this.msg = "";
		this.ctkmId = "";
		this.khIds = "";
		
		this.db = new dbutils();
	}
	
	public Duyettrakhuyenmai(String id)
	{
		this.id = id;
		this.ngayduyet = "";
		this.diengiai = "";
		this.msg = "";
		this.ctkmId = "";
		this.khIds = "";
		
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

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
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

	public void setKhachhangRs(ResultSet khRs) {
		
		this.khachhangRs = khRs;
	}

	
	public ResultSet getKhachhangRs() {
		
		return this.khachhangRs;
	}
	
	public void createRs() 
	{	
		String query = "select PK_SEQ as ctkmId, SCHEME + ', ' + DIENGIAI as SCHEME  " +
					   "from TIEUCHITHUONGTL  where trangthai = '1' and  PK_SEQ not in ( select CTKM_FK from DUYETTRAKHUYENMAI where TRANGTHAI = '1' )";
		
		if(this.id.trim().length() > 0)
		{
			query = "select PK_SEQ as ctkmId, SCHEME + ', ' + DIENGIAI as SCHEME from TIEUCHITHUONGTL  where pk_seq = '" + this.ctkmId + "' ";
		}
		this.ctkmRs = db.get(query);
		
		if(this.id.trim().length() <= 0)
		{
			if(this.ctkmId.trim().length() > 0)
			{
				//Tinh toan khuyen mai, giong nhu khi xuat bao cao
				String[] tumuc = null;
			    String[] denmuc = null;
			    String[] chietkhau = null;
			    String[] donvi = null;
			    
			    double MucVuot = 0;
			    double ckMucVuot = 0;
			    int dvMucVuot = 0;
			    
			    String sql = "select a.PK_SEQ as tctId, a.SCHEME, b.tumuc, b.denmuc, b.chietkhau, b.donvi, ISNULL(a.mucvuot, 0) as mucvuot, isnull(a.chietkhauMucVuot, 0) as ckMucVuot, a.donviMucVuot  " +
			    			 "from TIEUCHITHUONGTL a inner join TIEUCHITHUONGTL_TIEUCHI b on a.PK_SEQ = b.thuongtl_fk  " +
			    			 "where a.PK_SEQ = '" + this.ctkmId + "' and a.TRANGTHAI = '1'";
			    
			    //System.out.println("__Init MUC: " + sql);
			    
			    ResultSet rsThuong = db.get(sql);
			    if(rsThuong != null)
			    {
			    	try 
			    	{
			    		String tu_muc = "";
			    		String den_muc = "";
			    		String chiet_khau = "";
			    		String don_vi = "";
			    		
						while(rsThuong.next())
						{
							tu_muc += rsThuong.getString("tumuc") + "__";
							den_muc += rsThuong.getString("denmuc") + "__";
							chiet_khau += rsThuong.getString("chietkhau") + "__";
							don_vi += rsThuong.getString("donvi") + "__";
							
							MucVuot = rsThuong.getDouble("mucvuot");
							ckMucVuot = rsThuong.getDouble("ckMucVuot");
							dvMucVuot = rsThuong.getInt("donviMucVuot");
						}
						rsThuong.close();
	
						if(tu_muc.trim().length() > 0)
						{
							tu_muc = tu_muc.substring(0, tu_muc.length() - 2);
							tumuc = tu_muc.split("__");
							
							den_muc = den_muc.substring(0, den_muc.length() - 2);
							denmuc = den_muc.split("__");
							
							chiet_khau = chiet_khau.substring(0, chiet_khau.length() - 2);
							chietkhau = chiet_khau.split("__");
							
							don_vi = don_vi.substring(0, don_vi.length() - 2);
							donvi = don_vi.split("__");
						}
						
						
					} 
			    	catch (Exception e) 
			    	{
			    		System.out.println("___EXCEPTION LAY THUONG: " + e.getMessage());
			    	}	
			    }
			    
			    
			    
			    query = "select NhaPhanPhoi.pk_seq as nppId, NhaPhanPhoi.TEN as nppTen, " +		    				
							" KHACHHANG.pk_seq as khId, KHACHHANG.ten + ', ' + isnull(KHACHHANG.diachi, '') as khTen, dophu.*  " +
						"from  " +
						"(  " +
							"select c.thuongtl_fk, a.NPP_FK, a.ASM, a.GSBH_FK, a.DDKD_FK, a.KHACHHANG_FK, " +
								"isnull(thuongSR, 0) as thuongSR, isnull(thuongTDSR, 0) as thuongTDSR, isnull(thuongSS, 0) as thuongSS, isnull(thuongTDSS, 0) as thuongTDSS, isnull(thuongASM, 0) as thuongASM, isnull(thuongTDASM, 0) as thuongTDASM, " +
								"case when isnull(tc.doanhsotheothung, 0) = 0 then  SUM(b.soluong * b.GIAMUA ) else " +
																				 "  SUM(b.soluong * isnull( e.SOLUONG1, 0) / ISNULL(e.SOLUONG2, 1)) end as DoanhSo  " +
							"from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK  " +
								"inner join TIEUCHITHUONGTL_SANPHAM c on b.SANPHAM_FK = c.sanpham_fk  " +
								"left join TIEUCHITHUONGTL_MUCTHUONG d on d.thuongtl_fk = c.thuongtl_fk " +
								"left join QUYCACH e on b.SANPHAM_FK = e.SANPHAM_FK and e.DVDL1_FK = '100018' " +
								"inner join TIEUCHITHUONGTL tc on c.thuongtl_fk = tc.PK_SEQ " +
							"where a.TRANGTHAI in (1, 3) and c.thuongtl_fk = '" + this.ctkmId + "' and a.ngaynhap >= tc.thang and a.ngaynhap <= tc.nam  " +
									"and a.khachhang_fk in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG  " +
															"where DKKMTICHLUY_FK in ( select PK_SEQ from DANGKYKM_TICHLUY where TIEUCHITL_FK = '" + this.ctkmId + "' ) ) " +
							"group by c.thuongtl_fk, tc.doanhsotheothung, a.NPP_FK, a.ASM, a.GSBH_FK, a.DDKD_FK, a.KHACHHANG_FK, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM   " +
						")   " +
						"dophu " +
						"inner join NHAPHANPHOI on dophu.npp_fk = NHAPHANPHOI.pk_seq " +
						"inner join KHACHHANG on dophu.KHACHHANG_FK = KHACHHANG.PK_SEQ   ";
				
				System.out.println("1.Chi tieu tich luy tap trung KH: " + query);
				ResultSet rs = db.get(query);
				
				String npp_Ten = "";
				String npp_Id = "";
				String kh_Ten = "";
				String kh_Id = "";
				String doanh_so = "";
				String tong_tien = "";
				String donvi_thuong = "";
				
				NumberFormat format = new DecimalFormat("#,###,###");
				if(rs != null)
				{
					try 
					{
						while(rs.next())
						{
							double doanhso = rs.getDouble("doanhso");
							
							//String donviThuong = "";
							//double thuongKH = find_thuong_khachhang(doanhso, tumuc, denmuc, chietkhau, donvi, MucVuot, ckMucVuot, dvMucVuot, donviThuong);
							//System.out.println("___Don vi thuong tai day: " + donviThuong);
							
							String arr = find_thuong_khachhang(doanhso, tumuc, denmuc, chietkhau, donvi, MucVuot, ckMucVuot, dvMucVuot);
							//System.out.println("----Ket qua tinh thuong: " + arr);
							double thuongKH = Double.parseDouble(arr.split("_")[0]);
							String donviThuong = arr.split("_")[1];
							
							
							if(thuongKH > 0)
							{
								npp_Id += rs.getString("nppId") + "__";	
								npp_Ten += rs.getString("nppTen") + "__";
								kh_Id += rs.getString("khId") + "__";	
								kh_Ten += rs.getString("khTen") + "__";
								doanh_so += format.format(doanhso) + "__";
								tong_tien += format.format(thuongKH) + "__";
								donvi_thuong += donviThuong + "__";
							}
							
						}
						rs.close();
					} 
					catch (Exception e) 
					{
						System.out.println("__Loi khi DUyet Exception: " + e.getMessage());
					}
					
					if(npp_Id.trim().length() > 0)
					{
						npp_Id = npp_Id.substring(0, npp_Id.length() - 2);
						this.nppId = npp_Id.split("__");
						
						npp_Ten = npp_Ten.substring(0, npp_Ten.length() - 2);
						this.nppTen = npp_Ten.split("__");
						
						kh_Id = kh_Id.substring(0, kh_Id.length() - 2);
						this.khId = kh_Id.split("__");
						
						kh_Ten = kh_Ten.substring(0, kh_Ten.length() - 2);
						this.khTen = kh_Ten.split("__");
						
						doanh_so = doanh_so.substring(0, doanh_so.length() - 2);
						this.doanhso = doanh_so.split("__");
						
						tong_tien = tong_tien.substring(0, tong_tien.length() - 2);
						this.tongtien = tong_tien.split("__");
						
						donvi_thuong = donvi_thuong.substring(0, donvi_thuong.length() - 2);
						this.donvithuong = donvi_thuong.split("__");
						
						//System.out.println("__Don VI THUONG: " + donvi_thuong);
					}
				} 
			}
		}
		else
		{
			query = " select NhaPhanPhoi.pk_seq as nppId, NhaPhanPhoi.TEN as nppTen, " +
						"KHACHHANG.pk_seq as khId, KHACHHANG.ten + ', ' + isnull(KHACHHANG.diachi, '') as khTen, duyet.doanhso, duyet.thuong, duyet.donvi " +
					"from DUYETTRAKHUYENMAI_KHACHHANG duyet " +
					"	inner join NHAPHANPHOI on duyet.nppId = NHAPHANPHOI.pk_seq " +
					"	inner join KHACHHANG on duyet.khId = KHACHHANG.PK_SEQ " +
					"where duyet.duyetkm_fk = '" + this.id + "'  ";
			
			System.out.println("1.Chi tieu tich luy tap trung KH: " + query);
			ResultSet rs = db.get(query);
			
			String npp_Ten = "";
			String npp_Id = "";
			String kh_Ten = "";
			String kh_Id = "";
			String doanh_so = "";
			String tong_tien = "";
			String donvi_thuong = "";
			
			NumberFormat format = new DecimalFormat("#,###,###");
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						double doanhso = rs.getDouble("doanhso");
						
						double thuongKH = rs.getDouble("thuong");
						
						if(thuongKH > 0)
						{
							npp_Id += rs.getString("nppId") + "__";	
							npp_Ten += rs.getString("nppTen") + "__";
							kh_Id += rs.getString("khId") + "__";	
							kh_Ten += rs.getString("khTen") + "__";
							doanh_so += format.format(doanhso) + "__";
							tong_tien += format.format(thuongKH) + "__";
							donvi_thuong += rs.getString("donvi") + "__";
						}
						
					}
					rs.close();
				} 
				catch (Exception e) 
				{
					System.out.println("Exception: " + e.getMessage());
				}
				
				if(npp_Id.trim().length() > 0)
				{
					npp_Id = npp_Id.substring(0, npp_Id.length() - 2);
					this.nppId = npp_Id.split("__");
					
					npp_Ten = npp_Ten.substring(0, npp_Ten.length() - 2);
					this.nppTen = npp_Ten.split("__");
					
					kh_Id = kh_Id.substring(0, kh_Id.length() - 2);
					this.khId = kh_Id.split("__");
					
					kh_Ten = kh_Ten.substring(0, kh_Ten.length() - 2);
					this.khTen = kh_Ten.split("__");
					
					doanh_so = doanh_so.substring(0, doanh_so.length() - 2);
					this.doanhso = doanh_so.split("__");
					
					tong_tien = tong_tien.substring(0, tong_tien.length() - 2);
					this.tongtien = tong_tien.split("__");
					
					donvi_thuong = donvi_thuong.substring(0, donvi_thuong.length() - 2);
					this.donvithuong = donvi_thuong.split("__");
				}
				
			} 
		}
	}
	

	public double find_thuong_khachhang(double doanhso, String[] tumuc, String[] denmuc, String[] chietkhau, String[] donvi, double MucVuot, double ckMucVuot, int dvMucVuot, String donviThuong) 
	{
		if(tumuc == null || denmuc == null || chietkhau == null || donvi == null)
		{
			System.out.println("___CO NULL RUI>>>>");
			return 0;
		}
		
		if( ( ckMucVuot > 0 ) && ( doanhso >= MucVuot ) )
		{
			if(dvMucVuot == 0)
			{
				//System.out.println("___Vuot muc: " + ckMucVuot * doanhso / 100);
				donviThuong = "0";
				return ckMucVuot * doanhso / 100;
			}
			else
			{
				//System.out.println("___Vuot muc: " + ckMucVuot);
				if(dvMucVuot == 1)
				{
					donviThuong = "1";
					return ckMucVuot;
				}
				else
				{
					donviThuong = "2";
					return ckMucVuot;
				}
			}
		}
		
		double thuong = 0;
		for(int i = 0; i < tumuc.length; i++)
		{
			//System.out.println("___Tu muc: " + tumuc[i] + " ___ Den muc: " + denmuc[i] + "  ___ Doanh so: " + doanhso);
			if( ( Double.parseDouble(tumuc[i]) <= doanhso ) && ( doanhso < Double.parseDouble(denmuc[i]) ) )
			{
				// 0 -- Chiet khau , 1 -- Tien, 2 -- SP
				if(Double.parseDouble(donvi[i]) == 0 )
				{
					donviThuong = "0";
					thuong = Double.parseDouble(chietkhau[i]) * doanhso / 100;
				}
				else
				{
					if(Double.parseDouble(donvi[i]) == 1 )
					{
						donviThuong = "1";
						thuong = Double.parseDouble(chietkhau[i]);
					}
					else
					{
						donviThuong = "2";
						thuong = Double.parseDouble(chietkhau[i]);
					}
				}
			}
		}
		
		//System.out.println("__Thuong toi day: " + thuong);
		if(thuong == 0) //Doanh so khong nam trong tu muc - den muc, tim muc gan nhat neu co
		{
			int pos = -1;
			double max = -1;
			for(int i = 0; i < denmuc.length; i++)
			{
				if( doanhso > Double.parseDouble(denmuc[i]) )
				{
					if( Double.parseDouble(denmuc[i]) >= max )
					{
						max = Double.parseDouble(denmuc[i]);
						pos = i;
					}
				}
			}
			
			//System.out.println("___MUC GAN NHAT TIM DUOC: " + denmuc[pos]);
			//Lay duoc Den muc gan nhat
			if(pos != -1)
			{
				if(Double.parseDouble(donvi[pos]) == 0 )
				{
					donviThuong = "0";
					thuong = Double.parseDouble(chietkhau[pos]) * doanhso / 100;
				}
				else
				{
					if(Double.parseDouble(donvi[pos]) == 1 )
					{
						donviThuong = "1";
						thuong = Double.parseDouble(chietkhau[pos]);
					}
					else
					{
						donviThuong = "2";
						thuong = Double.parseDouble(chietkhau[pos]);
					}
				}
			}
		}
		
		//System.out.println("Doanh so: " + doanhso + "___Tien thuong: " + thuong);
		//System.out.println("___Don vi thuong trong ham: " + donviThuong);
		return thuong;
	}

	
	public String find_thuong_khachhang(double doanhso, String[] tumuc, String[] denmuc, String[] chietkhau, String[] donvi, double MucVuot, double ckMucVuot, int dvMucVuot) 
	{
		String donviThuong = "0";
		
		if(tumuc == null || denmuc == null || chietkhau == null || donvi == null)
		{
			System.out.println("___CO NULL RUI>>>>");
			return "0_0";
		}
		
		if( ( ckMucVuot > 0 ) && ( doanhso >= MucVuot ) )
		{
			if(dvMucVuot == 0)
			{
				//System.out.println("___Vuot muc: " + ckMucVuot * doanhso / 100);
				donviThuong = "0";
				return Double.toString(ckMucVuot * doanhso / 100) + "_" + donviThuong;
			}
			else
			{
				//System.out.println("___Vuot muc: " + ckMucVuot);
				if(dvMucVuot == 1)
				{
					donviThuong = "1";
					return Double.toString(ckMucVuot) + "_" + donviThuong;
				}
				else
				{
					donviThuong = "2";
					return Double.toString(ckMucVuot) + "_" + donviThuong;
				}
			}
		}
		
		double thuong = 0;
		for(int i = 0; i < tumuc.length; i++)
		{
			//System.out.println("___Tu muc: " + tumuc[i] + " ___ Den muc: " + denmuc[i] + "  ___ Doanh so: " + doanhso);
			if( ( Double.parseDouble(tumuc[i]) <= doanhso ) && ( doanhso < Double.parseDouble(denmuc[i]) ) )
			{
				// 0 -- Chiet khau , 1 -- Tien, 2 -- SP
				if(Double.parseDouble(donvi[i]) == 0 )
				{
					donviThuong = "0";
					thuong = Double.parseDouble(chietkhau[i]) * doanhso / 100;
				}
				else
				{
					if(Double.parseDouble(donvi[i]) == 1 )
					{
						donviThuong = "1";
						thuong = Double.parseDouble(chietkhau[i]);
					}
					else
					{
						donviThuong = "2";
						thuong = Double.parseDouble(chietkhau[i]);
					}
				}
			}
		}
		
		//System.out.println("__Thuong toi day: " + thuong);
		if(thuong == 0) //Doanh so khong nam trong tu muc - den muc, tim muc gan nhat neu co
		{
			int pos = -1;
			double max = -1;
			for(int i = 0; i < denmuc.length; i++)
			{
				if( doanhso > Double.parseDouble(denmuc[i]) )
				{
					if( Double.parseDouble(denmuc[i]) >= max )
					{
						max = Double.parseDouble(denmuc[i]);
						pos = i;
					}
				}
			}
			
			//System.out.println("___MUC GAN NHAT TIM DUOC: " + denmuc[pos]);
			//Lay duoc Den muc gan nhat
			if(pos != -1)
			{
				if(Double.parseDouble(donvi[pos]) == 0 )
				{
					donviThuong = "0";
					thuong = Double.parseDouble(chietkhau[pos]) * doanhso / 100;
				}
				else
				{
					if(Double.parseDouble(donvi[pos]) == 1 )
					{
						donviThuong = "1";
						thuong = Double.parseDouble(chietkhau[pos]);
					}
					else
					{
						donviThuong = "2";
						thuong = Double.parseDouble(chietkhau[pos]);
					}
				}
			}
		}
		
		//System.out.println("Doanh so: " + doanhso + "___Tien thuong: " + thuong);
		//System.out.println("___Don vi thuong trong ham: " + donviThuong);
		return Double.toString(thuong) + "_" + donviThuong;
	}
	
	
	public boolean createTctSKU( ) 
	{
		if(nppId == null)
		{
			this.msg = "Không có khách hàng nào được chọn";
			return false;
		}
		
		if(this.ngayduyet.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn ngày duyệt";
			return false;
		}
		
		if(this.ctkmId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn chương trình khuyến mại";
			return false;
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "insert DUYETTRAKHUYENMAI(ngayduyet, ctkm_fk, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   "values('" + this.ngayduyet + "', '" + this.ctkmId + "', N'" + this.diengiai + "', '1', '" + this.getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới DUYETTRAKHUYENMAI " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(khId != null)
			{
				String sqlInsert = "";
				for(int i = 0; i < khId.length; i++)
				{
					sqlInsert += "select '" +  nppId[i].trim() + "' as nppId, '" + khId[i].trim() + "' as khId, '" + doanhso[i].trim().replaceAll(",", "") + "' as doanhso, '" + tongtien[i].trim().replaceAll(",", "") + "' as tongtien, '" + donvithuong[i] + "' as donvi ";
					sqlInsert += " union ";
				}
				
				if(sqlInsert.trim().length() > 0)
				{
					sqlInsert = sqlInsert.substring(0, sqlInsert.length() - 7);
					
					query = " insert DUYETTRAKHUYENMAI_KHACHHANG(duyetkm_fk, nppId, khId, doanhso, thuong, donvi)" +
							" select IDENT_CURRENT('DUYETTRAKHUYENMAI'), duyet.* from ( " + sqlInsert + " ) duyet ";
					
					System.out.println("Chen Khach Hang: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới DUYETTRAKHUYENMAI_KHACHHANG " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
		} 
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.msg = "Không thể duyệt khuyến mại tích lũy.";
			return false;
		}
		
		return true;
	}

	public boolean updateTctSKU()
	{
		
		return true;
	}

	public void init() 
	{
		String query = "select ngayduyet, diengiai, ctkm_fk  " +
					   "from DuyetTraKhuyenMai where pk_seq = '" + this.id + "'";
		
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ctkmId = rs.getString("ctkm_fk");
					this.ngayduyet = rs.getString("ngayduyet");				
					this.diengiai= rs.getString("diengiai");
				}
				rs.close();
			} 
			catch (Exception e)
			{
				System.out.println("115.Error Meg: " + e.getMessage());
			}
		}
		
		this.createRs();
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public void setCtkmRs(ResultSet ctkmRs) {
		
		this.ctkmRs = ctkmRs;
	}

	
	public ResultSet getCtkmRs() {
		
		return this.ctkmRs;
	}

	
	public String getCtkmId() {
		
		return this.ctkmId;
	}

	
	public void setCtkmId(String ctkmId) {
		
		this.ctkmId = ctkmId;
	}

	
	public String getNgayduyet() {
		
		return this.ngayduyet;
	}

	
	public void setNgayduyet(String ngayduyet) {
		
		this.ngayduyet = ngayduyet;
	}

	
	public String[] getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String[] nppId) {
		
		this.nppId = nppId;
	}

	
	public String[] getNppTen() {
		
		return this.nppTen;
	}

	
	public void setNppTen(String[] nppTen) {
		
		this.nppTen = nppTen;
	}

	
	public void setKhId(String[] khId) {
		
		this.khId = khId;
	}

	
	public String[] getKhTen() {
		
		return this.khTen;
	}

	
	public void setKhTen(String[] khTen) {
		
		this.khTen = khTen;
	}

	
	public String[] getDoanhso() {
		
		return this.doanhso;
	}

	
	public void setDoanhso(String[] doanhso) {
		
		this.doanhso = doanhso;
	}

	
	public String[] getDat() {
		
		return this.dat;
	}

	
	public void setDat(String[] dat) {
		
		this.dat = dat;
	}

	
	public String[] getSoxuat() {
		
		return this.soxuat;
	}

	
	public void setSoxuat(String[] soxuat) {
		
		this.soxuat = soxuat;
	}

	
	public String[] getTongtien() {
		
		return this.tongtien;
	}

	
	public void setTongtien(String[] tongtien) {
		
		this.tongtien = tongtien;
	}

	public String[] getKhId() {
		
		return this.khId;
	}


	public String[] getDonvithuong() {
		
		return this.donvithuong;
	}


	public void setDonvithuong(String[] donvithuong) {
		
		this.donvithuong = donvithuong;
	}
	
	
	

	
	
}

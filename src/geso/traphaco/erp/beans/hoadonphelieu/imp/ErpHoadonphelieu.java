package geso.traphaco.erp.beans.hoadonphelieu.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.hoadon.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadonphelieu.IErpHoaDonPL_SP;
import geso.traphaco.erp.beans.hoadonphelieu.IErpHoadonphelieu;

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

public class ErpHoadonphelieu implements IErpHoadonphelieu
{
	String userId;
	String congtyId;
	String id;

	String diengiai;
	String nccId;
	String nccTen;
	String tungay;
	String denngay;
	String thanhlyId;
	ResultSet tltsRs;
	String ngayghinhan;
	String ngayhoadon;
	String kyhieuhoadon;
	String sohieuhoadon;
	String vat;
	String Bvat;
	String Avat;
	
	String poId;
	ResultSet poRs;
	
	String loaiCK;
	
	String khoanmucDTId;
	ResultSet khoanmucDTRs;
	
	String khId;
	ResultSet khRs;
	
	String hdId;
	ResultSet hdRs;
	
	String taikhoandoanhthuId;
	ResultSet taikhoandoanhthuRs;
	
	String taikhoanghinoId;
	ResultSet taikhoanghinoRs;
	
	String tenhanghoadichvu;
	String hinhthucthanhtoan;
	
	String[] tenSanpham;
	String[] donvitinh;
	String[] quydoi;
	String[] soluong;
	String[] dongia;
	String[] thanhtien;
	
	String msg;
	
	String nguoimuahang;
	String donvi;
	String diachi;
	String masothue;
	
	ResultSet kbhRs ;
	String kbhId;
	String kbhTen;
	
	String trangthai ;
	
	List<IErpHoaDon_SP> listsanpham = new ArrayList<IErpHoaDon_SP>();
	
	List<IErpHoaDonPL_SP> sanphamlist = new ArrayList<IErpHoaDonPL_SP>();
	Hashtable<String, String> sanpham_ghichu;
	
	dbutils db;
	
	String hoadonId;
	String timhoadonId;
	ResultSet hoadonRs;
	
	public ErpHoadonphelieu()
	{
		this.userId = "";
		this.id = "";
		this.diengiai = "";
		this.tungay = "";
		this.denngay = "";
		this.nccId = "";
		this.nccTen = "";
		this.poId = "";
		this.msg = "";
		this.khoanmucDTId="";
		this.thanhlyId ="";
		this.ngayghinhan = "";
		this.ngayhoadon = "";
	
		this.sohieuhoadon = "";
		this.vat = "10";
		this.Bvat = "0";
		this.Avat = "0";
		this.hdId = "";
		this.khId = "";
		
		this.tungay = "";
		this.denngay = "";
		this.tenhanghoadichvu = "";
		this.db = new dbutils();
		this.kyhieuhoadon = "HL/14P";
		this.hinhthucthanhtoan = "";
		this.nguoimuahang = "";
		this.donvi = "";
		this.diachi = "";
		this.masothue = "";
		
		this.kbhId = "";
		this.kbhTen = "";
		
		this.trangthai = "";
		this.taikhoandoanhthuId = "";
		this.taikhoanghinoId  = "";
		
		this.hoadonId = "";
		this.timhoadonId = "";
		
		tenSanpham = new String[0];
		donvitinh = new String[0];
		quydoi = new String[0];
		soluong = new String[0];
		dongia = new String[0];
		thanhtien = new String[0];
		
		this.sanpham_ghichu = new Hashtable<String, String>();	
	}
	
	private String getkyhieuhd_moinhat() {
		
		try{
			String query=" SELECT isnull(KYHIEUHOADON,'') as KYHIEUHOADON FROM erp_hoadonphelieu WHERE PK_SEQ = (SELECT MAX(PK_SEQ) FROM erp_hoadonphelieu ) ";
			ResultSet rs=db.get(query);
			if (rs != null)
			{
				if(rs.next()){
				
					return rs.getString("KYHIEUHOADON");
				}
				rs.close();
			}			
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
		this.tungay = "";
		this.denngay = "";
		this.nccId = "";
		this.nccTen = "";
		this.poId = "";
		this.msg = "";
		
		this.khId ="";
		this.hdId = "";
		this.thanhlyId ="";
		this.ngayghinhan = "";
		this.ngayhoadon = "";
		this.kyhieuhoadon = "";
		this.sohieuhoadon = "";
		this.vat = "10";
		this.Bvat = "0";
		this.Avat = "0";
		this.khoanmucDTId="";
		this.loaiCK="";
		
		this.db = new dbutils();
		
		tenSanpham = new String[0];
		donvitinh = new String[0];
		quydoi = new String[0];
		soluong = new String[0];
		dongia = new String[0];
		thanhtien = new String[0];
		this.tungay = "";
		this.denngay = "";
		this.tenhanghoadichvu = "";
		this.hinhthucthanhtoan = "";
		this.nguoimuahang = "";
		this.donvi = "";
		this.diachi = "";
		this.masothue = "";
		
		this.kbhId = "";
		this.kbhTen = "";
		
		this.trangthai = "";
		this.taikhoanghinoId = "";
		this.timhoadonId = "";
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

	public String getKhoanmucDTId() 
	{
		return this.khoanmucDTId;
	}

	public void setKhoanmucDTId(String khoanmucDTId) 
	{
		this.khoanmucDTId = khoanmucDTId;
	}
	
	public void setKhoanmucDTRs(ResultSet khoanmucDTRs) 
	{
		this.khoanmucDTRs = khoanmucDTRs;
	}
	
	public ResultSet getKhoanmucDTRs()
	{
		return khoanmucDTRs;
	}
	
	public void init() 
	{
		String query = "select a.ngayghinhan, a.ngayhoadon, a.kyhieuhoadon,(select pk_seq from erp_hoadon where sohoadon=a.sohoadon and a.sohoadon != 'SDÐK30042015') hdid ,a.sohoadon, a.vat, a.doanhthu_fk, \n"+
				       " 	   a.KBH_FK, CASE WHEN a.KBH_FK is not null THEN (select ten + ' - ' + diengiai from KENHBANHANG where PK_SEQ = a.KBH_FK  ) ELSE '' END kbhTen, a.trangthai, " +
				       "       a.khachhang_fk, b.ten as khTen, isnull(a.trungtamdoanhthu_fk,0) trungtamdoanhthu_fk , a.diengiai, a.tungay, a.denngay , a.loaick," +
					   "       case a.doanhthu_fk when 400004 then a.avat when 400005 then a.avat  else (select sum(thanhtien) from erp_hoadonphelieu_sanpham where hoadonphelieu_fk = '" + this.id + "' group by hoadonphelieu_fk  ) end as bvat, isnull(a.HANGHOADICHVU,'') HANGHOADICHVU, ISNULL(a.HINHTHUCTHANHTOAN,'') HINHTHUCTHANHTOAN, " +
					   "       ISNULL(a.DONVI,'') DONVI,ISNULL(a.DIACHI,'') DIACHI, ISNULL(a.NGUOIMUAHANG,'') NGUOIMUAHANG, ISNULL(a.MASOTHUE,'') MASOTHUE, a.TAIKHOANDOANHTHU_FK, a.doanhthu_fk, a.taikhoanghino_fk "+
					   " from   erp_hoadonphelieu a inner join NHAPHANPHOI b on a.khachhang_fk = b.pk_seq " +
					   "where  a.pk_seq = '" + this.id + "'";
		
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
					this.khoanmucDTId = rs.getString("doanhthu_fk");
					this.Bvat = formater.format(rs.getDouble("bvat"));
					this.hdId = rs.getString("hdid");
					this.vat = rs.getString("vat") ;
					if(!this.khoanmucDTId.equals("400004")&&!this.khoanmucDTId.equals("400005")){
						this.Avat = formater1.format(rs.getDouble("bvat")+ (rs.getDouble("bvat")*rs.getDouble("vat")/100));
					}
					else{
						this.Avat = formater1.format(rs.getDouble("bvat"));
					}
					
					//this.nccId = rs.getString("khachhang_fk");
					//this.nccTen = rs.getString("khTen");
					this.poId = rs.getString("trungtamdoanhthu_fk")== null ? "" :rs.getString("trungtamdoanhthu_fk") ;
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.diengiai = rs.getString("diengiai");
					this.loaiCK = rs.getString("loaick")== null ? "":rs.getString("loaick") ;
					
					this.hdId = rs.getString("hdid") == null ? "": rs.getString ("hdid");
					this.khId = rs.getString("khachhang_fk") == null ? "": rs.getString("khachhang_fk");
					this.taikhoanghinoId  = rs.getString("taikhoanghino_fk") == null ? "": rs.getString("taikhoanghino_fk");
					this.tenhanghoadichvu = rs.getString("HANGHOADICHVU");
					this.hinhthucthanhtoan = rs.getString("HINHTHUCTHANHTOAN");
					this.donvi = rs.getString("DONVI");
					this.diachi = rs.getString("DIACHI");
					this.nguoimuahang = rs.getString("NGUOIMUAHANG");
					this.masothue = rs.getString("MASOTHUE");
					this.sanpham_ghichu = new Hashtable<String, String>();
					
					this.kbhId = rs.getString("KBH_FK") == null ? "": rs.getString("KBH_FK");
					
					
					this.trangthai =  rs.getString("trangthai");
					this.taikhoandoanhthuId = rs.getString("TAIKHOANDOANHTHU_FK");
					this.timhoadonId = "";
					
				}
				rs.close();
				
			
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
		this.createRS();
		
		if(this.khoanmucDTId.equals("100011"))
		{
			try
			{
			query = "select thanhlytaisan_fk from erp_hoadonphelieu_thanhly where hoadonphelieu_fk = '" + this.id + "' ";
			ResultSet tlrs = db.get(query);
			this.thanhlyId = "0";
			while (tlrs.next())
			{
				this.thanhlyId += "," + tlrs.getString("thanhlytaisan_fk");
			}
		
			System.out.println("ffffff" + this.thanhlyId);
			tlrs.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		query = "select pk_seq, diengiai, isnull(donvitinh, '') as donvitinh, dongia,dongiack, soluong, thanhtien, isnull(ghichu,'') as ghichu " +
				"from ERP_HoaDonPheLieu_SanPham where hoadonphelieu_fk = '" + this.id + "' ";
		System.out.println("Init SP: " + query);
		
		ResultSet rsSp = db.getScrol(query);
		
//		int count = 0;
		
		try
		{
			if(rsSp!= null)
			{
				while(rsSp.next())
				{
					IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
					
					String idSanPham = rsSp.getString("pk_seq");
					String tenSanpham = rsSp.getString("diengiai");
					String donvitinh = rsSp.getString("donvitinh");
					String soluong = formater.format(rsSp.getDouble("soluong"));
					String dongia = formater.format(rsSp.getDouble("dongia"));
					String dongiadagiam = formater.format(rsSp.getDouble("dongiack"));
					String thanhtien = formater.format(rsSp.getDouble("thanhtien"));
					String ghichu = rsSp.getString("ghichu");
					
					if(this.khoanmucDTId.equals("400000") && soluong.equals("0") && dongia.equals("0")) 
					{
						soluong = "";
						dongia = "";
					}else if(soluong.equals("0") && dongia.equals("0") && thanhtien.equals("0"))
					{
						soluong = "";
						dongia = "";
						thanhtien = "";
					}
										
					sanpham.setId(idSanPham);
					sanpham.setTenSanPham(tenSanpham);
					sanpham.setDonViTinh(donvitinh);
					sanpham.setSoLuong(soluong);
					sanpham.setDonGia(dongia);
					sanpham.setDonGiaDaGiam(dongiadagiam);
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
		
		/*try 
		{
			rsSp.beforeFirst();
			while(rsSp.next())
			{
				count ++;
			}

			tenSanpham = new String[count];
			donvitinh = new String[count];
			quydoi = new String[count];
			soluong = new String[count];
			dongia = new String[count];
			thanhtien = new String[count];
			
			rsSp.beforeFirst();
			int pos = 0;
			while(rsSp.next())
			{
				tenSanpham[pos] = rsSp.getString("diengiai");
				donvitinh[pos] = rsSp.getString("donvitinh");
				soluong[pos] = formater.format(rsSp.getDouble("soluong"));
				dongia[pos] = formater.format(rsSp.getDouble("dongia"));
				if(this.khoanmucDTId.equals("400000") && soluong[pos].equals("0") && dongia[pos].equals("0")) 
				{
					soluong[pos] = "";
					dongia[pos] = "";
					
				}
				thanhtien[pos] = formater.format(rsSp.getDouble("thanhtien"));
				//thanhtien[pos] = formater.format(rsSp.getDouble("soluong") * rsSp.getDouble("dongia"));
				if(soluong[pos].equals("0") && dongia[pos].equals("0")&&thanhtien[pos].equals("0"))
				{
					soluong[pos] = "";
					dongia[pos] = "";
					thanhtien[pos]= "";
					
				}
				
				
				
				pos++;
			}
			rsSp.close();
			
		} 
		catch (Exception e) 
		{
			System.out.println("____EXception SanPham: " + e.getMessage());
		}*/

		
	}
	
	public void initExcel() 
		{
			String query = " select a.ngayghinhan, a.ngayhoadon, a.kyhieuhoadon, a.sohoadon, a.vat, a.doanhthu_fk, " +
						   "       a.khachhang_fk, b.ten as khTen, a.trungtamdoanhthu_fk, a.diengiai, a.tungay, a.denngay , a.loaick," +
						   "      (select sum(thanhtien)" +
						   "       from erp_hoadonphelieu_sanpham" +
						   "       where hoadonphelieu_fk = '" + this.id + "'" +
						   "       group by hoadonphelieu_fk  ) as bvat " +
						   " from erp_hoadonphelieu a inner join NHAPHANPHOI b on a.khachhang_fk = b.pk_seq " +
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
						this.khoanmucDTId = rs.getString("doanhthu_fk");
						this.Bvat = formater.format(rs.getDouble("bvat"));
						this.vat = rs.getString("vat") ;
						this.Avat = formater1.format(rs.getDouble("bvat")+ (rs.getDouble("bvat")*rs.getDouble("vat")/100));
						
						this.khId = rs.getString("khachhang_fk");
						//this.nccTen = rs.getString("khTen");
						this.poId = rs.getString("trungtamdoanhthu_fk")== null ? "" :rs.getString("trungtamdoanhthu_fk") ;
						this.tungay = rs.getString("tungay");
						this.denngay = rs.getString("denngay");
						this.diengiai = rs.getString("diengiai");
						this.loaiCK = rs.getString("loaick")== null ? "":rs.getString("loaick") ;
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
			
			if(!this.khoanmucDTId.trim().equals("400001")){
				if(this.khId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn khách hàng.";
					return false;
				}
			}
			
			if(this.khoanmucDTId.equals("400002")||this.khoanmucDTId.equals("400003"))
			{							
				if(this.taikhoandoanhthuId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn tài khoản doanh thu. ";
					return false;
				}
			}
			
			
			if(this.khoanmucDTId.equals("400000"))
			{							
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}
				this.poId= "NULL";
			}
			else if(this.khoanmucDTId.equals("400002"))
			{							
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}
				this.poId= "NULL";
			}
			else if(this.khoanmucDTId.equals("400003"))
			{							
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}
				this.poId= "NULL";
			}
			else if(this.khoanmucDTId.equals("400001"))
			{								
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}
				
				if(this.hdId.length()<=0)
				{
					this.msg = "Vui lòng chọn hóa đơn.";
					return false;
				}
				this.poId = "NULL";
				
			}
			else if(this.khoanmucDTId.equals("400004"))
			{								
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}
				this.poId = "NULL";
				
			}
			else if(this.khoanmucDTId.equals("400005"))
			{								
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}				
				
				if(this.taikhoanghinoId.trim().length()<=0)
				{
					this.msg = "Vui lòng chọn tài khoản ghi nợ";
					return false;
				}
				
				this.poId = "NULL";
			} 	else if(this.khoanmucDTId.equals("100011"))
			{			
				if(this.thanhlyId.trim().length() <= 1)
				{
					this.msg = "Vui lòng chọn phiếu thanh lý. ";
					return false;
				}				
				
				if(this.sanphamlist.size()<=0)
				{
					this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
					return false;
				}
				this.loaiCK= "NULL" ;
				this.kbhId = "NULL";
			}
			else {
				if(this.poId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn trung tâm doanh thu.";
					return false;
				}
				this.loaiCK= "NULL" ;
				this.kbhId = "NULL";
			}
			
			// check so hoa don && ky hieu hoa don 
			int sodong=0;
			String sql="select count(*) as sodong from erp_hoadonphelieu where sohoadon='"+this.sohieuhoadon+"' and kyhieuhoadon ='"+this.kyhieuhoadon+"' and trangthai != 2 ";
			ResultSet checkHD = db.get(sql);
			if (checkHD != null)
			{
				while(checkHD.next())
				{
					sodong= checkHD.getInt("sodong");
				}
				checkHD.close();
			}
			if(sodong > 0 )
			{
				this.msg = "Số hóa đơn: '"+this.sohieuhoadon+"' và ký hiệu hóa đơn: '"+this.kyhieuhoadon+"' đã tồn tại, vui lòng nhập lại ";
				return false;
			}
			
			
			/*else
			{
				if(!this.khoanmucDTId.equals("400000"))
				{
					for(int i = 0; i < this.tenSanpham.length; i++)
					{
						if( ( this.tenSanpham[i].trim().length() > 0 ) &&  ( this.soluong[i].trim().length() <= 0 || this.dongia[i].trim().length() <= 0 ) )
						{
 
							this.msg = "Vui lòng kiểm tra lại số lượng và đơn giá của phế liệu";
							return false;
						}
					 }
				}
			}*/
			
			double tongtienbvat=0;
			double tongtienavat=0;
			if(!this.khoanmucDTId.equals("400004")&&!this.khoanmucDTId.equals("400005")){
				if(this.sanphamlist.size()<=0){
					this.msg = "Vui lòng kiểm tra lại thông tin phế liệu";
					return false;
				}
			}
			
			
			for(int i = 0; i < this.sanphamlist.size(); i++ )
			{
				IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
				sanpham = sanphamlist.get(i);
				
				if( ( sanpham.getTenSanPham() == null ) || ( sanpham.getTenSanPham()!= null && sanpham.getTenSanPham().length() <= 0 )  )
				{
					this.msg = "Vui lòng kiểm tra lại thông tin phế liệu";
					return false;
				}
				
				if( sanpham.getTenSanPham().trim().length() > 0 &&  sanpham.getThanhTien().trim().length() > 0 )
				{
					try{
					 tongtienbvat=tongtienbvat+ Double.parseDouble( sanpham.getThanhTien().trim().replaceAll(",", ""));
					}catch(Exception er){
						this.msg = "Vui lòng kiểm tra lại số lượng và đơn giá của phế liệu";
						return false;
					}
					
				}
			}
			
			double vat1=0;
			try{
			vat1=Double.parseDouble(vat);
			}catch(Exception er){
				this.msg = "Vui lòng kiểm tra lại vat";
				return false;
			}
			tongtienavat=Math.round(tongtienbvat)+ Math.round(tongtienbvat)*vat1/100;
			
			
			
//			String tmp="0000000"+this.sohieuhoadon;
			//Tạm thời bỏ đi
			//this.sohieuhoadon = ( tmp).substring(tmp.length()-7 ,tmp.length());
					
			
			db.getConnection().setAutoCommit(false);
					
			String khachhang_fk ="NULL";
			if(khId.length()>0) {
				khachhang_fk = khId;
				
			}
			if(nccId.length()>0) {
				khachhang_fk = nccId ;
			}
			
			if(this.khoanmucDTId.equals("400004")||this.khoanmucDTId.equals("400005"))
				tongtienavat = Double.parseDouble(this.Avat.trim().replaceAll(",", ""));
				
			String query = 	"insert erp_hoadonphelieu(ngayghinhan, ngayhoadon, kyhieuhoadon, sohoadon, bvat, vat, avat, khachhang_fk, " +
							"trungtamdoanhthu_fk, diengiai, congty_fk, trangthai, nguoitao, ngaytao, nguoisua, ngaysua, doanhthu_fk,  " +
							"tungay, denngay, HANGHOADICHVU, HINHTHUCTHANHTOAN, DONVI, DIACHI, NGUOIMUAHANG, MASOTHUE, KBH_FK, " +
							"TAIKHOANDOANHTHU_FK, TAIKHOANGHINO_FK)  " +
							" values('" + this.ngayghinhan + "', '" + this.ngayhoadon + "', '" + this.kyhieuhoadon + "', '" + this.sohieuhoadon + "', " +
									"'" + Math.round(tongtienbvat) + "', '" + this.vat.replaceAll(",", "") + "', '" + Math.round(tongtienavat)+ "', " +
									"" +khachhang_fk+ ", " + this.poId + ", N'" + this.diengiai + "', '" + this.congtyId + "', '0'," +
									" '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', " +
									"" + khoanmucDTId + ",'','',N'"+this.tenhanghoadichvu+"',N'"+hinhthucthanhtoan+"',N'"+this.donvi+"', " +
									"N'"+this.diachi+"', N'"+this.nguoimuahang+"',N'"+this.masothue+"', "+ this.kbhId +", " +
									""+(this.taikhoandoanhthuId.length()<=0 ? null :this.taikhoandoanhthuId)+", " +
									""+(this.taikhoanghinoId.length()<=0 ? null :this.taikhoanghinoId)+")";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới erp_hoadonphelieu " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " select IDENT_CURRENT('ERP_HoaDonPheLieu') as giamgiaId ";
			ResultSet rs = db.get(query);
			if(rs.next())
				this.id = rs.getString("giamgiaId");
			
			/*for(int i = 0; i < this.tenSanpham.length; i++ )
			{
				if( this.tenSanpham[i].trim().length() > 0 &&  this.thanhtien[i].trim().length() > 0 )
				{
					query = "insert ERP_HoaDonPheLieu_SanPham(hoadonphelieu_fk, diengiai, donvitinh, soluong, dongia, thanhtien)  " +
							"values('" + this.id + "', N'" + this.tenSanpham[i] + "', N'" + this.donvitinh[i] + "', '" + this.soluong[i].replaceAll(",", "") + "', '" + this.dongia[i].trim().replaceAll(",", "") + "', '" + this.thanhtien[i].trim().replaceAll(",", "") + "' ) ";
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_HoaDonPheLieu_SanPham " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}*/
			if(this.khoanmucDTId.equals("100011"))
			{
				String [] idtl = this.thanhlyId.split(",");
				for(String idsss : idtl){
					if(!idsss.equals("0")){
					    query = "INSERT INTO ERP_HOADONPHELIEU_THANHLY   " +
						" values('"+this.id+"','"+idsss+"')";
			
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới erp_hoadonphelieu_thanhly " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			int count= 0;
			
			System.out.println("dem:"+ this.sanphamlist.size());
			if(!this.khoanmucDTId.equals("400004")&&!this.khoanmucDTId.equals("400005")){
			while(count < this.sanphamlist.size() )
			{
				IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
				sanpham = sanphamlist.get(count);
				if(sanpham.getTenSanPham().trim().length() > 0)
				{
					//NẾU LÀ LOẠI HÓA ĐƠN ĐIỀU CHỈNH TĂNG ---> KIỂM TRA ĐƠN GIÁ PHẢI >= 0 HAY KHÔNG, ĐƠN GIÁ ĐÃ GIẢM >=0, THANHTIEN >=0
					if(this.khoanmucDTId.equals("400002"))
					{
						if(Double.parseDouble(sanpham.getDonGia().replaceAll(",", ""))<0){
							this.msg = "Đơn giá của sản phẩm không được phép âm " ;
							db.getConnection().rollback();
							return false;
						}
						
						if(Double.parseDouble(sanpham.getDonGiaDaGiam().replaceAll(",", ""))<0){
							this.msg = "Đơn giá đã giảm của sản phẩm không được phép âm " ;
							db.getConnection().rollback();
							return false;
						}
						
						if(Double.parseDouble(sanpham.getThanhTien().trim().replaceAll(",", ""))<0){
							this.msg = "Thành tiền của sản phẩm không được phép âm " ;
							db.getConnection().rollback();
							return false;
						}
					}
					
					//NẾU LÀ LOẠI HÓA ĐƠN ĐIỀU CHỈNH GIẢM ---> KIỂM TRA ĐƠN GIÁ PHẢI <= 0 HAY KHÔNG, ĐƠN GIÁ ĐÃ GIẢM <=0, THANHTIEN <=0
					
					if(this.khoanmucDTId.equals("400003"))
					{
						
						if(Double.parseDouble(sanpham.getDonGia().replaceAll(",", ""))>0){
							this.msg = "Đơn giá của sản phẩm không được phép dương " ;
							db.getConnection().rollback();
							return false;
						}
						
						if(Double.parseDouble(sanpham.getDonGiaDaGiam().replaceAll(",", ""))>0){
							this.msg = "Đơn giá đã giảm của sản phẩm không được phép dương " ;
							db.getConnection().rollback();
							return false;
						}
						
						if(Double.parseDouble(sanpham.getThanhTien().trim().replaceAll(",", ""))>0){
							this.msg = "Thành tiền của sản phẩm không được phép dương " ;
							db.getConnection().rollback();
							return false;
						}
					}
					
					
					query = "insert ERP_HoaDonPheLieu_SanPham(hoadonphelieu_fk, diengiai, donvitinh, soluong, dongia,dongiack, thanhtien, ghichu)  " +
					        "values('" + this.id + "', N'" + sanpham.getTenSanPham() + "', N'" + sanpham.getDonViTinh()+ "', '" + sanpham.getSoLuong().replaceAll(",", "") + "'," +
							" '" + sanpham.getDonGia().replaceAll(",", "") + "', '"+sanpham.getDonGiaDaGiam().replaceAll(",", "") +"', '" + sanpham.getThanhTien().trim().replaceAll(",", "") + "' , N'"+ sanpham.getGhiChu1() +"' ) ";
			
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
		 }
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi ";
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
			
			if(this.khoanmucDTId.equals("400002")||this.khoanmucDTId.equals("400003"))
			{							
				if(this.taikhoandoanhthuId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn tài khoản doanh thu. ";
					return false;
				}
			}
			
			if(this.khoanmucDTId.trim().equals("400000"))
			{								
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}
				this.poId= "NULL";
			
			}
			else if(this.khoanmucDTId.trim().equals("400002"))
			{								
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}
				this.poId= "NULL";
			
			}		
			else if(this.khoanmucDTId.trim().equals("400003"))
			{								
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}
				this.poId= "NULL";
			
			}	
			else if(this.khoanmucDTId.equals("400001")){
											
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}
				/*if(this.hdId.length()<=0)
				{
					this.msg = "Vui lòng chọn hóa đơn.";
					return false;
				}*/
				this.poId = "NULL";
			
			}
			else if(this.khoanmucDTId.equals("400004"))
			{								
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}
				this.poId = "NULL";
				
			}
			else if(this.khoanmucDTId.equals("400005"))
			{								
				if(this.kbhId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn kênh bán hàng cho khách hàng. ";
					return false;
				}
				
				if(this.taikhoanghinoId.trim().length()<=0)
				{
					this.msg = "Vui lòng chọn tài khoản ghi nợ.";
					return false;
				}
				this.poId = "NULL";
				
			}else if(this.khoanmucDTId.equals("100011"))
			{			
				if(this.thanhlyId.trim().length() <= 1)
				{
					this.msg = "Vui lòng chọn phiếu thanh lý. ";
					return false;
				}				
				
				if(this.sanphamlist.size()<=0)
				{
					this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
					return false;
				}
				this.loaiCK= "NULL" ;
				this.kbhId = "NULL";
			}
			else {
				if(this.poId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn trung tâm doanh thu.";
					return false;
				}
				this.loaiCK= "NULL" ;
				this.kbhId = "NULL";
			}
			
			
			// check so hoa don && ky hieu hoa don 
			int sodong=0;
			String sql="select count(*) as sodong from erp_hoadonphelieu where sohoadon='"+this.sohieuhoadon+"' and kyhieuhoadon ='"+this.kyhieuhoadon+"' and trangthai !=2  and pk_seq <> "+ this.id ;
			ResultSet checkHD = db.get(sql);
			if (checkHD != null)
			{
				while(checkHD.next())
				{
					sodong= checkHD.getInt("sodong");
				}
				checkHD.close();
			}
			if(sodong > 0 )
			{
				this.msg = "Số hóa đơn: '"+this.sohieuhoadon+"' và ký hiệu hóa đơn: '"+this.kyhieuhoadon+"' đã tồn tại, vui lòng nhập lại ";
				return false;
			}
			
			/*if( ( this.tenSanpham == null ) && ( this.tenSanpham != null && this.tenSanpham.length <= 0 )  )
			{
				this.msg = "Vui lòng kiểm tra lại thông tin phế liệu";
				return false;
			}*/
			/*else
			{
				if(!this.khoanmucDTId.equals("400000"))
				{
					for(int i = 0; i < this.tenSanpham.length; i++)
					{
						if( ( this.tenSanpham[i].trim().length() > 0 ) &&  ( this.soluong[i].trim().length() <= 0 || this.dongia[i].trim().length() <= 0 ) )
						{
							this.msg = "Vui lòng kiểm tra lại số lượng và đơn giá của phế liệu";
							return false;
						}
					 }
				}
			}*/
			double tongtienbvat=0;
			double tongtienavat=0;
			
			if(!this.khoanmucDTId.equals("400004")&&!this.khoanmucDTId.equals("400005")){
				if(this.sanphamlist.size()<=0){
					this.msg = "Vui lòng kiểm tra lại thông tin phế liệu";
					return false;
				}
			}
			
			for(int i = 0; i < this.sanphamlist.size(); i++ )
			{
				IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
				sanpham = sanphamlist.get(i);
				
				if( ( sanpham.getTenSanPham() == null ) || ( sanpham.getTenSanPham()!= null && sanpham.getTenSanPham().length() <= 0 )  )
				{
					this.msg = "Vui lòng kiểm tra lại thông tin phế liệu";
					return false;
				}
				
				if( sanpham.getTenSanPham().trim().length() > 0 &&  sanpham.getThanhTien().trim().length() > 0 )
				{
					try{
					 tongtienbvat=tongtienbvat+ Double.parseDouble( sanpham.getThanhTien().trim().replaceAll(",", ""));
					}catch(Exception er){
						this.msg = "Vui lòng kiểm tra lại số lượng và đơn giá của phế liệu";
						return false;
					}
					
				}
			}
			double vat1=0;
			try{
			vat1=Double.parseDouble(vat);
			}catch(Exception er){
				this.msg = "Vui lòng kiểm tra lại vat";
				return false;
			}
			
			if(this.khoanmucDTId.equals("400004")||this.khoanmucDTId.equals("400005"))
				tongtienavat = Double.parseDouble(this.Avat.trim().replaceAll(",", ""));
			else
				tongtienavat=Math.round(tongtienbvat)+ Math.round(tongtienbvat)*vat1/100;
			
			
			db.getConnection().setAutoCommit(false);
			
			String khachhang_fk ="NULL";
			if(khId.length()>0) {
				khachhang_fk = khId;
				
			}
			if(nccId.length()>0) {
				khachhang_fk = nccId ;
			}
			
			if(this.khoanmucDTId.equals("400004")||this.khoanmucDTId.equals("400005")) this.taikhoandoanhthuId = null;
			
			String query = "update erp_hoadonphelieu set ngayghinhan = '" + this.ngayghinhan + "', ngayhoadon = '" + this.ngayhoadon + "', kyhieuhoadon = '" + this.kyhieuhoadon + "', sohoadon = '" + this.sohieuhoadon + "', " +
							"bvat = '" + Math.round(tongtienbvat)+ "', vat = '" + this.vat.replaceAll(",", "") + "', avat = '" +Math.round(tongtienavat) + "', " +
							"khachhang_fk = " + khachhang_fk + ", diengiai = N'" + this.diengiai + "', congty_fk = '" + this.congtyId + "', trungtamdoanhthu_fk = " + this.poId + ", tungay = '', denngay = '', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' , doanhthu_fk = " + this.khoanmucDTId + ",  HANGHOADICHVU = N'" +this.tenhanghoadichvu+"', HINHTHUCTHANHTOAN = N'" +hinhthucthanhtoan+"'," +
							"donvi =N'"+this.donvi+"', diachi = N'"+this.diachi+"', nguoimuahang = N'"+this.nguoimuahang+"', MASOTHUE = N'"+this.masothue+"', KBH_FK = "+ this.kbhId +", TAIKHOANDOANHTHU_FK = "+(this.taikhoandoanhthuId.length()<=0 ? null :this.taikhoandoanhthuId)+", taikhoanghino_fk = "+
							(this.taikhoanghinoId.length()<=0 ? null :this.taikhoanghinoId)+
							" where pk_seq = '" + this.id + "' and trangthai=0 ";

			if(db.updateReturnInt(query)!=1)
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
			
			query = "delete ERP_HOADONPHELIEU_THANHLY where hoadonphelieu_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_HoaDonPheLieu_SanPham " + query;
				db.getConnection().rollback();
				return false;
			}
			
			/*for(int i = 0; i < this.tenSanpham.length; i++ )
			{
				if( this.tenSanpham[i].trim().length() > 0 && this.thanhtien[i].trim().length() > 0 )
				{
					query = "insert ERP_HoaDonPheLieu_SanPham(hoadonphelieu_fk, diengiai, donvitinh, soluong, dongia, thanhtien)  " +
							"values('" + this.id + "', N'" + this.tenSanpham[i] + "', N'" + this.donvitinh[i] + "', '" + this.soluong[i].replaceAll(",", "") + "', '" + this.dongia[i].trim().replaceAll(",", "") + "', '" + this.thanhtien[i].trim().replaceAll(",", "") + "' ) ";
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_HoaDonPheLieu_SanPham " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}*/
			if(this.khoanmucDTId.equals("100011"))
			{
				String [] idtl = this.thanhlyId.split(",");
				for(String idsss : idtl){
					if(!idsss.equals("0")){
					    query = "INSERT INTO ERP_HOADONPHELIEU_THANHLY   " +
						" values('"+this.id+"','"+idsss+"')";
			
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới erp_hoadonphelieu_thanhly " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			int count= 0;
			
			
			while(count < this.sanphamlist.size() )
			{
				IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
				sanpham = sanphamlist.get(count);
				if(sanpham.getTenSanPham().trim().length() > 0)
				{
					//NẾU LÀ LOẠI HÓA ĐƠN ĐIỀU CHỈNH TĂNG ---> KIỂM TRA ĐƠN GIÁ PHẢI >= 0 HAY KHÔNG, ĐƠN GIÁ ĐÃ GIẢM >=0, THANHTIEN >=0
					if(this.khoanmucDTId.equals("400002"))
					{
						
						if(Double.parseDouble(sanpham.getDonGia().replaceAll(",", ""))<0){
							this.msg = "Đơn giá của sản phẩm không được phép âm " ;
							db.getConnection().rollback();
							return false;
						}
						
						if(Double.parseDouble(sanpham.getDonGiaDaGiam().replaceAll(",", ""))<0){
							this.msg = "Đơn giá đã giảm của sản phẩm không được phép âm " ;
							db.getConnection().rollback();
							return false;
						}
						
						if(Double.parseDouble(sanpham.getThanhTien().trim().replaceAll(",", ""))<0){
							this.msg = "Thành tiền của sản phẩm không được phép âm " ;
							db.getConnection().rollback();
							return false;
						}
					}
					
					//NẾU LÀ LOẠI HÓA ĐƠN ĐIỀU CHỈNH GIẢM ---> KIỂM TRA ĐƠN GIÁ PHẢI <= 0 HAY KHÔNG, ĐƠN GIÁ ĐÃ GIẢM <=0, THANHTIEN <=0
					
					if(this.khoanmucDTId.equals("400003"))
					{
						
						if(Double.parseDouble(sanpham.getDonGia().replaceAll(",", ""))>0){
							this.msg = "Đơn giá của sản phẩm không được phép dương " ;
							db.getConnection().rollback();
							return false;
						}
						
						if(Double.parseDouble(sanpham.getDonGiaDaGiam().replaceAll(",", ""))>0){
							this.msg = "Đơn giá đã giảm của sản phẩm không được phép dương " ;
							db.getConnection().rollback();
							return false;
						}
						
						if(Double.parseDouble(sanpham.getThanhTien().trim().replaceAll(",", ""))>0){
							this.msg = "Thành tiền của sản phẩm không được phép dương " ;
							db.getConnection().rollback();
							return false;
						}
					}
					
					query = "insert ERP_HoaDonPheLieu_SanPham(hoadonphelieu_fk, diengiai, donvitinh, soluong, dongia, dongiack,thanhtien, ghichu)  " +
					        "values('" + this.id + "', N'" + sanpham.getTenSanPham() + "', N'" + sanpham.getDonViTinh()+ "', '" + sanpham.getSoLuong().replaceAll(",", "") + "'," +
							" '" + sanpham.getDonGia().replaceAll(",", "") + "', '" + sanpham.getDonGiaDaGiam().replaceAll(",", "")+"', '"+ sanpham.getThanhTien().trim().replaceAll(",", "") + "' , N'"+ sanpham.getGhiChu1() +"' ) ";
			
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
			if (this.sanpham_ghichu != null)
				this.sanpham_ghichu.clear(); 
			if (this.poRs != null)
				this.poRs.close();
			if (this.khoanmucDTRs != null)
				this.khoanmucDTRs.close();
			if (this.khRs != null)
				this.khRs.close();
			if (this.hdRs != null)
				this.hdRs.close();
			if (this.taikhoandoanhthuRs != null)
				this.taikhoandoanhthuRs.close();
			if (this.taikhoanghinoRs != null)
				this.taikhoanghinoRs.close();
			if (this.kbhRs != null)
				this.kbhRs.close();
			if (this.hoadonRs != null)
				this.hoadonRs.close();
			if(listsanpham!=null){
				listsanpham.clear();
			}
			if(sanphamlist!=null){
				sanphamlist.clear();
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.db.shutDown();
		}
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

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getNccId() {
		
		return this.nccId;
	}

	
	public void setNccId(String nccId) {
		
		this.nccId = nccId;
	}

	
	public String getNccTen() {
		
		return this.nccTen;
	}

	
	public void setNccTen(String nccTen) {
		
		this.nccTen = nccTen;
	}

	
	public ResultSet getPORs() {
		
		return this.poRs;
	}

	
	public void setPORs(ResultSet poRs) {
		
		this.poRs = poRs;
	}

	
	public String getPOId() {
		
		return this.poId;
	}

	
	public void setPOId(String poId) {
		
		this.poId = poId;
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
		NumberFormat formater = new DecimalFormat("##,###,###.#####");
		NumberFormat formater1 = new DecimalFormat("##,###,###");
		String query = "";
		
		if(this.ngayghinhan.length()>0)
			this.ngayhoadon = this.ngayghinhan;
				
		if(this.khoanmucDTId.equals("400005")){
			this.taikhoanghinoRs = db.get("select PK_SEQ, ('['+SOHIEUTAIKHOAN+'] - ['+TENTAIKHOAN+']') as SOHIEUTAIKHOAN from ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33540000' OR SOHIEUTAIKHOAN LIKE '641%'");
		}
		
		
		
		
					query = " SELECT pk_seq as doanhthuId, diengiai as doanhthuTen FROM ERP_DOANHTHU where  TRANGTHAI = '1'  and PK_SEQ =100011 " ;
////				"  UNION \n " +
////				" SELECT '400000' as doanhthuId, N'Hóa đơn chiết khấu' as doanhthuTen from ERP_DOANHTHU \n " +
////				"  UNION \n "+
////				" SELECT '400001' as doanhthuId, N'Thu hồi CK' as doanhthuTen from ERP_DOANHTHU \n"+
////				"  UNION \n "+
////				" SELECT '400002' as doanhthuId, N'Hóa đơn điều chỉnh tăng ' as doanhthuTen from ERP_DOANHTHU \n"+
////				"  UNION \n "+
////				" SELECT '400003' as doanhthuId, N'Hóa đơn điều chỉnh giảm ' as doanhthuTen from ERP_DOANHTHU \n"+
////				"  UNION \n"+
////				" SELECT '400004' as doanhthuId, N'Hóa đơn điều chỉnh thuế suất hóa đơn bán hàng ' as doanhthuTen from ERP_DOANHTHU \n"+
////				"  UNION \n"+
////				" SELECT '400005' as doanhthuId, N'Hóa đơn điều chỉnh thuế suất hóa đơn khuyến mãi ' as doanhthuTen from ERP_DOANHTHU \n";
//		
//		query = " SELECT '400006' as doanhthuId, N'Hóa đơn thanh lý TSCĐ/ CCDC' as doanhthuTen \n";

		
		this.khoanmucDTRs = db.get(query);
		
		query = " 	SELECT distinct ttdt.PK_SEQ as poId , ttdt.DIENGIAI as poTen " +
				" 	FROM ERP_DOANHTHU dt inner join ERP_TRUNGTAMDOANHTHU  ttdt on dt.TTDOANHTHU_FK=ttdt.PK_SEQ "+
				" 	WHERE ttdt.TRANGTHAI ='1' and dt.trangthai = 1  ";

		if( this.khoanmucDTId.length() > 0 && !this.khoanmucDTId.equals("400000")&& !this.khoanmucDTId.equals("400002")&&!this.khoanmucDTId.equals("400003")&&!this.khoanmucDTId.equals("400004"))
		{
			query += " and dt.PK_SEQ = '" + this.khoanmucDTId + "' ";
		}		
		System.out.println("query: "+query);
		this.poRs = db.get(query);	
		
		this.khRs = db.get("SELECT PK_SEQ, (ISNULL(MAFAST,ISNULL(MA,''))+' - ' + isnull(TEN,'')) TEN from NHAPHANPHOI WHERE TRANGTHAI = '1' ORDER BY TEN");		
		
		if(this.timhoadonId.trim().length()>0){
			query = 
			" SELECT case when a.LOAIHOADON = 0 then N'Hóa đơn tài chính' when a.LOAIHOADON = 1 then N'Hóa đơn khuyến mãi' end TENHD ,a.PK_SEQ, a.SOHOADON, a.NGAYXUATHD, b.TEN KHACHHANG, a.LOAIHOADON loaict " +
			" FROM ERP_HOADON a INNER JOIN NHAPHANPHOI b ON a.KHACHHANG_FK = b.PK_SEQ " +
			" WHERE  a.TRANGTHAI NOT IN (0,2) and a.SOHOADON NOT IN (SELECT (SOHOADON) FROM ERP_HOADONPHELIEU WHERE DOANHTHU_FK = "+this.khoanmucDTId+" and SOHOADON NOT IN ('"+(this.timhoadonId.length() <=0 ? "0": this.timhoadonId)+"')) ";
			if(this.timhoadonId.trim().length()>0)
				query += " and a.SOHOADON LIKE '%"+this.timhoadonId+"%'";
			
			if(tungay.length()>0){
				query += " and a.NGAYXUATHD >='"+tungay+"'";
			}
			
			if(denngay.length()>0){
				query += " and a.NGAYXUATHD <='"+denngay+"'";
			}
			System.out.println(query);
			this.hdRs = db.get(query);
		}
		
		if(hdId.trim().length()>0&&this.timhoadonId.trim().length()>0){
			query = "SELECT PK_SEQ, SOHOADON, NGAYXUATHD, KYHIEU, KHACHHANG_FK FROM ERP_HOADON WHERE PK_SEQ ='"+hdId+"' AND SOHOADON = '"+this.timhoadonId+"'";
			ResultSet layhd = db.get(query);
			try{
				if(layhd!=null){
					while(layhd.next()){
						this.ngayhoadon= layhd.getString("NGAYXUATHD");
						this.hdId = layhd.getString("PK_SEQ");
						this.kyhieuhoadon = layhd.getString("KYHIEU");
						//this.sohieuhoadon = layhd.getString("SOHOADON");
						this.khId = layhd.getString("KHACHHANG_FK");
					}
					layhd.close();
				}
			}
			catch(Exception e)
			{				
				e.printStackTrace();
			}
		}
		
		if(this.khId.trim().length() > 0)
		{
			query = " SELECT PK_SEQ, TEN + ', ' + DIENGIAI as TEN FROM KENHBANHANG WHERE TRANGTHAI = 1 ";
			query +=" AND PK_SEQ IN ( select kenhbanhang_fk from NHAPHANPHOI_KENHBANHANG where khachhang_fk = "+ this.khId +" ) ";
			System.out.println("KBH " +query);
			this.kbhRs = db.getScrol(query);
			if(this.khoanmucDTId.equals("100011"))
			{
				query = " SELECT PK_SEQ, NGAY  FROM ERP_THANHLYTAISAN WHERE TRANGTHAI = 1  AND KHACHHANG_FK ="+this.khId + " ";
				if(this.id.length()>0)
				{
				query+=	" AND PK_SEQ NOT IN (SELECT THANHLYTAISAN_FK FROM ERP_HOADONPHELIEU_THANHLY WHERE HOADONPHELIEU_FK ="+this.id +") ";
				}
				if(this.id.length()>0)
				{
				     query+= " UNION ALL SELECT PK_SEQ,NGAY FROM ERP_THANHLYTAISAN WHERE PK_SEQ IN (SELECT THANHLYTAISAN_FK FROM ERP_HOADONPHELIEU_THANHLY WHERE HOADONPHELIEU_FK ="+this.id +")" ;
				}
				        System.out.println("aa" +query);
				this.tltsRs =db.get(query);
			}
		}
		
		if(hdId.trim().length()<=0)
			this.listsanpham = null;
		
		// Nếu loại hd: Hóa đơn CK và khId > 0 >> kênh bán hàng của KH
		if( !this.trangthai.equals("2")  && ( this.khoanmucDTId.equals("400000") || this.khoanmucDTId.equals("400001")) )
		{			
			String khachhangId = this.khId;
			if(this.khoanmucDTId.equals("400001")) khachhangId = this.khId;
			
			if(khachhangId.trim().length() > 0)
			{
				query = "select kh.KenhBanHang_FK as ID, (kbh.TEN + ' - ' + kbh.DIENGIAI) TEN  "+
						"from NHAPHANPHOI kh INNER JOIN KENHBANHANG kbh ON kh.KenhBanHang_fk = kbh.PK_SEQ "+
						"where kh.pk_seq = "+ khachhangId +" ";
				
				System.out.println("Lấy KBH "+query);
				ResultSet LayKBH = db.get(query);
					try{
						if(LayKBH!=null){
							while(LayKBH.next()){
								this.kbhId= LayKBH.getString("ID") == null ? "": LayKBH.getString("ID") ;
								this.kbhTen = LayKBH.getString("TEN");
							}
							LayKBH.close();
						}
					}
					catch(Exception e)
					{				
						e.printStackTrace();
					}
			}
			
		}
		
		if( (this.khoanmucDTId.length() > 0 && this.khoanmucDTId.equals("400002"))||(this.khoanmucDTId.length() > 0 
			&& this.khoanmucDTId.equals("400003"))||(this.khoanmucDTId.length() > 0&&this.khoanmucDTId.equals("400004")) )
		{
			query = " select PK_SEQ, (SOHIEUTAIKHOAN +' - '+TENTAIKHOAN) as tentk \n"+
		 			" from ERP_TAIKHOANKT where (SOHIEUTAIKHOAN like '511%' or SOHIEUTAIKHOAN like '521%') and TRANGTHAI = 1 \n";
			
			this.taikhoandoanhthuRs = db.get(query);
		}	
		
		if(this.thanhlyId.length()>1)
		{

			query = "select pk_seq, diengiai, isnull(donvi, '') as donvitinh, dongia,chietkhau, soluong, thanhtien, '' as ghichu " +
					"from ERP_THANHLYTAISAN_TAISAN where thanhlytaisan_fk in  (" + this.thanhlyId + ") ";
			System.out.println("Init SP: " + query);
			
			ResultSet rsSp = db.getScrol(query);
			this.sanphamlist.clear();
//			int count = 0;
			
			try
			{
				if(rsSp!= null)
				{
					double totalbvat=0;
					double totalavat=0;
					while(rsSp.next())
					{
						IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
						
						String idSanPham = rsSp.getString("pk_seq");
						String tenSanpham = rsSp.getString("diengiai");
						String donvitinh = rsSp.getString("donvitinh");
						String soluong = formater.format(rsSp.getDouble("soluong"));
						String dongia = formater.format(rsSp.getDouble("dongia"));
						String dongiadagiam = formater.format(rsSp.getDouble("chietkhau")==0?rsSp.getDouble("dongia"):rsSp.getDouble("chietkhau") * rsSp.getDouble("dongia"));
						String thanhtien = formater.format(rsSp.getDouble("thanhtien"));
						String ghichu = rsSp.getString("ghichu");
											
						sanpham.setId(idSanPham);
						sanpham.setTenSanPham(tenSanpham);
						sanpham.setDonViTinh(donvitinh);
						sanpham.setSoLuong(soluong);
						sanpham.setDonGia(dongia);
						sanpham.setDonGiaDaGiam(dongiadagiam);
						sanpham.setThanhTien(thanhtien);
						sanpham.setGhiChu1(ghichu);
						totalbvat+=rsSp.getDouble("thanhtien");
						totalavat+=rsSp.getDouble("thanhtien") + rsSp.getDouble("thanhtien")*Double.parseDouble(this.vat.replaceAll(",", ""))/100;
						 
		
						
						this.sanphamlist.add(sanpham);
					}
					this.Bvat=formater.format(totalbvat);
					this.Avat=formater.format(totalavat);
					rsSp.close();
				}
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
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


	public String getLoaiCK() {
		return this.loaiCK;
	}

	public void setLoaiCK(String loaiCK) {
		this.loaiCK= loaiCK;
		
	}
	
	public List<IErpHoaDon_SP> GetListSanPham()
	{		
		return this.listsanpham;
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
			if (rsLs != null)
			{
				if(rsLs.next())
				{
					lsId = rsLs.getString("lsId");
				}
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
					this.khoanmucDTId = rs.getString("doanhthu_fk");
					this.Bvat = formater.format(rs.getDouble("bvat"));
					this.vat = rs.getString("vat") ;
					this.Avat = formater1.format(rs.getDouble("bvat")+ (rs.getDouble("bvat")*rs.getDouble("vat")/100));
					
					this.khId = rs.getString("khachhang_fk");
					this.poId = rs.getString("trungtamdoanhthu_fk")== null ? "" :rs.getString("trungtamdoanhthu_fk") ;
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.diengiai = rs.getString("diengiai");
					this.loaiCK = rs.getString("loaick")== null ? "":rs.getString("loaick") ;
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

	
	public String getkhId() {
		
		return this.khId;
	}

	
	public void setkhId(String khId) {
		
		this.khId = khId;
	}

	
	public ResultSet getkhRs() {
		
		return this.khRs;
	}

	
	public void setkhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public String getHdId() {
		
		return this.hdId;
	}

	
	public void setHdId(String HdId) {
		
		this.hdId = HdId;
	}

	
	public ResultSet getHdRs() {
		
		return this.hdRs;
	}

	
	public void setHdRs(ResultSet HdRs) {
		
		this.hdRs = HdRs;
	}


	public String gettenhanghoadichvu() {
		
		return this.tenhanghoadichvu;
	}


	public void settenhanghoadichvu(String tenhanghoadichvu) {
		
		this.tenhanghoadichvu = tenhanghoadichvu;
	}

	
	public String gethinhthucthanhtoan() {
		
		return this.hinhthucthanhtoan;
	}

	
	public void sethinhthucthanhtoan(String hinhthucthanhtoan) {
		
		this.hinhthucthanhtoan = hinhthucthanhtoan;
	}

	
	public String getNguoiMuaHang() {
		
		return this.nguoimuahang;
	}

	
	public void setNguoiMuaHang(String nguoimuahang) {
		
		this.nguoimuahang = nguoimuahang;
	}

	
	public String getDonVi() {
		
		return this.donvi;
	}

	
	public void setDonVi(String donvi) {
		
		this.donvi = donvi;
	}

	
	public String getDiaChi() {
		
		return this.diachi;
	}

	
	public void setDiaChi(String diachi) {
		
		this.diachi = diachi;
	}

	
	public String getMST() {
		
		return this.masothue;
	}

	
	public void setMST(String masothue) {
		
		this.masothue = masothue;
	}


	public String getKbhId() 
	{
		return this.kbhId;
	}


	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}


	public String getKbhTen() 
	{
		return this.kbhTen;
	}


	public void setKbhTen(String kbhTen) 
	{
		this.kbhTen = kbhTen;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public ResultSet getKbhRs() 
	{
		return this.kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs) 
	{
		this.kbhRs = kbhRs;
	}


	public ResultSet getTaikhoandoanhthuRs() {
		
		return this.taikhoandoanhthuRs;
	}


	public void setTaikhoandoanhthuRs(ResultSet taikhoandoanhthuRs) {
		
		this.taikhoandoanhthuRs = taikhoandoanhthuRs;
	}


	public String getTaikhoandoanhthuId() {
		
		return this.taikhoandoanhthuId;
	}


	public void setTaikhoandoanhthuId(String taikhoandoanhthuId) {
		
		this.taikhoandoanhthuId = taikhoandoanhthuId;
	}

	
	public ResultSet getTaikhoanghinoRs() {
		
		return this.taikhoanghinoRs;
	}

	
	public void setTaikhoanghinoRs(ResultSet taikhoanghinoRs) {
		
		this.taikhoanghinoRs = taikhoanghinoRs;
	}

	
	public String getTaikhoanghinoId() {
		
		return this.taikhoanghinoId;
	}

	
	public void setTaikhoanghinoId(String taikhoanghinoId) {
		
		this.taikhoanghinoId = taikhoanghinoId;
	}

	
	public boolean updateGiamgiaSohoadon() {
		
		try {
			
			db.getConnection().setAutoCommit(false);
			
			
			// check so hoa don && ky hieu hoa don 
			int sodong=0;
			String sql="select count(*) as sodong from erp_hoadonphelieu where sohoadon='"+this.sohieuhoadon+"' and kyhieuhoadon ='"+this.kyhieuhoadon+"' and trangthai !=2  and pk_seq <> "+ this.id ;
			ResultSet checkHD = db.get(sql);
			if (checkHD != null)
			{
				while(checkHD.next())
				{
					sodong= checkHD.getInt("sodong");
				}
				checkHD.close();
			}
			if(sodong > 0 )
			{
				this.msg = "Số hóa đơn: '"+this.sohieuhoadon+"' và ký hiệu hóa đơn: '"+this.kyhieuhoadon+"' đã tồn tại, vui lòng nhập lại ";
				return false;
			}
			
			String query = "update erp_hoadonphelieu set sohoadon = '" + this.sohieuhoadon + "'  where pk_seq = '" + this.id + "' ";
			
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật số hóa đơn  " + query;
				db.getConnection().rollback();
				return false;
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

	
	public String getHoadonId() {
		
		return this.hoadonId;
	}

	
	public void setHoadonId(String hdId) {
		
		this.hoadonId = hdId;
	}

	
	public ResultSet getHoadonRs() {
		
		return this.hoadonRs;
	}

	
	public void setHoadonRs(ResultSet hoadonRs) {
		
		this.hoadonRs = hoadonRs;
	}
	
	public void loadhd() 
	{
		NumberFormat formater = new DecimalFormat("##,###,###.#####");
//		NumberFormat formater1 = new DecimalFormat("##,###,###");
		
		if(hdId.trim().length()>0)
		{
			String query =
				" SELECT A.PK_SEQ, C.TEN as DIENGIAI, B.DONVITINH, DONGIA, 0 as DONGIACK, SOLUONG, 0 THANHTIEN, ISNULL(A.ghichu, '') as ghichu \n"+ 
				" FROM ERP_HOADON A INNER JOIN ERP_HOADON_SP B ON A.PK_SEQ = B.HOADON_FK \n"+
				"	   INNER JOIN ERP_SANPHAM C ON B.SANPHAM_FK = C.PK_SEQ \n"+ 
				" WHERE A.PK_SEQ = '"+this.hdId+"' \n";
			System.out.println("Init SP: " + query);
		
			ResultSet rsSp = db.get(query);
			List<IErpHoaDonPL_SP> hdList = new ArrayList<IErpHoaDonPL_SP>();
			
			try
			{
				if(rsSp!= null)
				{
					IErpHoaDonPL_SP sanpham = null;
					
					while(rsSp.next())
					{
						sanpham = new ErpHoaDonPL_SP();						
						String idSanPham = rsSp.getString("pk_seq");
						String tenSanpham = rsSp.getString("diengiai");
						String donvitinh = rsSp.getString("donvitinh");
						String soluong = formater.format(rsSp.getDouble("soluong"));
						String dongia = formater.format(rsSp.getDouble("dongia"));
						String dongiadagiam = formater.format(rsSp.getDouble("dongiack"));
						String thanhtien = formater.format(rsSp.getDouble("thanhtien"));
						String ghichu = rsSp.getString("ghichu");
																	
						sanpham.setId(idSanPham);
						sanpham.setTenSanPham(tenSanpham);
						sanpham.setDonViTinh(donvitinh);
						sanpham.setSoLuong(soluong);
						sanpham.setDonGia(dongia);
						sanpham.setDonGiaDaGiam(dongiadagiam);
						sanpham.setThanhTien(thanhtien);
						sanpham.setGhiChu1(ghichu);
						
						hdList.add(sanpham);
					}
					rsSp.close();
				}
				this.sanphamlist = hdList;
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	
	public String gettimHoadon() {
		
		return this.timhoadonId;
	}

	
	public void settimHoadon(String timhdId) {
		
		this.timhoadonId = timhdId;
	}

	public String getThanhlyId() {
		return thanhlyId;
	}

	public void setThanhlyId(String thanhlyId) {
		this.thanhlyId = thanhlyId;
	}

	public ResultSet getTltsRs() {
		return tltsRs;
	}

	public void setTltsRs(ResultSet tltsRs) {
		this.tltsRs = tltsRs;
	}
	
}

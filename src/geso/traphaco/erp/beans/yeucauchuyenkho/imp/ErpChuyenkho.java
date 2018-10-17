package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.nhapkho.IKhu_Vitri;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.ILenhsanxuat;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
 
public class ErpChuyenkho implements IErpChuyenkho
{
	String userId;
	String id;
	String ctyId;
	String ngayyeucau;
	String lydoyeucau;
	String ghichu;
	String Nguoinhan;
	String msg;
	String isnhanHang;
	String trangthai;
	String IsChuyenHangSX;
	String khoXuatId, khoXuatTen;
	String kyhieu;
	String sochungtu;
	String lenhdieudong;
	String ngaydieudong;
	String nguoidieudong;
	String veviec;
	String nguoivanchuyen;
	String phuongtien;
	String hopdong;
	String tennguoinhan="";
	String ngayxuatthucte="";
	String ngaytao="";
	String sokien;
	
	String CdsxId="";
	ResultSet CdsxRs;
	
	public String getNgaytao() {
		return ngaytao;
	}

	public void setNgaytao(String ngaytao) {
		this.ngaytao = ngaytao;
	}

	public String getNgayxuatthucte() {
		return ngayxuatthucte;
	}

	public void setNgayxuatthucte(String ngayxuatthucte) {
		this.ngayxuatthucte = ngayxuatthucte;
	}

	public String getTennguoinhan() {
		return tennguoinhan;
	}

	public void setTennguoinhan(String tennguoinhan) {
		this.tennguoinhan = tennguoinhan;
	}

	ResultSet khoXuatRs;
	ResultSet tsddRs;
	String tsddId;
	String IsChuyenhangkhongdat;
	boolean ChoPhepchuyenhangdat;
	
	String task;
	String khoNhanId, khoNhanTen;
	ResultSet khoNhanRs;
	
	String nccXuatId;
	ResultSet nccXuatRs;
	
	String nccNhanId;
	ResultSet nccNhanRs;
	
	String lsxIds;
	ResultSet lsxRs;
	
	String tongSLYC ;
	
	List<IYeucau> spList;
	List<IYeucau> spChoNhapList;
	
	List<ILenhsanxuat> lsxList;
	String lenhsxId;
	
	List<IKhu_Vitri> khuList;
	List<IKhu_Vitri> vitriList;
	
	List<String> Listkey =new ArrayList<String>() ;
	
	
	String ndxId;
	ResultSet ndxRs;
	
	String trangthaisp;  // -1 khong dat, 1 dat
	dbutils db;
	Utility_Kho util_kho = new Utility_Kho();
	
	String codoituong;
	String loaidoituong;
	String doituongId;
	ResultSet doituongRs;
	
	String cokhonhan;
	String codoituongNhan;
	String loaidoituongNhan;
	String doituongNhanId;
	ResultSet doituongNhanRs;
	
	Hashtable<String, String> sanpham_soluong;
	Hashtable<String, String> sanpham_soluongDAXUAT;
	LinkedHashMap <String, String> sanpham_soluong_ordered;
	
	String ycckId;
	ResultSet vitriNhanRs;
	
	String coChiphi;
	String chiphiId;
	ResultSet chiphiRs;
	
	String muahang_fk;
	ResultSet muahangList;
	String ngaychotCK = "";
	public ErpChuyenkho()
	{
		IsChuyenhangkhongdat="0";
		
		this.id = "";
		this.ctyId = "";
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.ghichu = "";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.task="";
		this.lsxIds = "";
		this.msg = "";
		this.Nguoinhan="";
		this.IsChuyenHangSX="";
		this.isnhanHang = "0";
		this.trangthai = "0";
		this.trangthaisp = "";
		this.kyhieu="";
		this.sochungtu="";
		this.lenhdieudong="";
		this.ngaydieudong="";
		this.nguoidieudong="";
		this.veviec="";
		this.nguoivanchuyen="";
		this.phuongtien="";
		this.hopdong="";
		this.sokien = "";

		this.spList = new ArrayList<IYeucau>();
		this.spChoNhapList = new ArrayList<IYeucau>();
		
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();
		
		this.lsxList = new ArrayList<ILenhsanxuat>();
		this.lenhsxId = "";
		
		this.nccXuatId = "";
		this.nccNhanId = "";
		this.ndxId = "";
		this.tongSLYC ="0";
		this.IsChuyenhangkhongdat="";
		this.tsddId = "";
		
		this.loaidoituong = "";
		this.doituongId = "";
		this.codoituong = "";
		
		this.cokhonhan = "";
		this.loaidoituongNhan = "";
		this.doituongNhanId = "";
		this.codoituongNhan = "";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		this.ycckId = "";
		
		this.coChiphi = "";
		this.chiphiId = "";
		
		this.muahang_fk = "";
		this.ngaychotCK = "";
		this.CdsxId="";
		this.db = new dbutils();
	}
	
	public ErpChuyenkho(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.ghichu = "";
		this.khoXuatId = "";
		this.khoNhanId = "";
		this.IsChuyenHangSX="";
		this.lsxIds = "";
		this.msg = "";
		this.task="";
		this.isnhanHang = "0";
		this.IsChuyenhangkhongdat="";
		this.trangthai = "0";
		this.trangthaisp = "";
		this.Nguoinhan="";
		this.sokien ="";
		this.spList = new ArrayList<IYeucau>();
		this.spChoNhapList = new ArrayList<IYeucau>();
		
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();
		
		this.lsxList = new ArrayList<ILenhsanxuat>();
		this.lenhsxId = "";
		this.kyhieu="";
		this.sochungtu="";
		this.lenhdieudong="";
		this.ngaydieudong="";
		this.nguoidieudong="";
		this.veviec="";
		this.nguoivanchuyen="";
		this.phuongtien="";
		this.hopdong="";
		this.CdsxId="";
		this.ndxId = "";
		this.nccXuatId = "";
		this.nccNhanId = "";
		this.tongSLYC ="0";
		this.tsddId = "";
		
		this.cokhonhan = "";
		this.loaidoituong = "";
		this.doituongId = "";
		this.codoituong = "";
		
		this.cokhonhan = "";
		this.loaidoituongNhan = "";
		this.doituongNhanId = "";
		this.codoituongNhan = "";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		this.ycckId = "";
		
		this.coChiphi = "";
		this.chiphiId = "";
		this.muahang_fk = "";
		this.ngaychotCK = "";
		this.db = new dbutils();
	}

	
	public LinkedHashMap<String, String> getSanpham_soluong_ordered() {
		return sanpham_soluong_ordered;
	}

	public void setSanpham_soluong_ordered(
			LinkedHashMap<String, String> sanpham_soluong_ordered) {
		this.sanpham_soluong_ordered = sanpham_soluong_ordered;
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

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getNgayyeucau() 
	{
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) 
	{
		this.ngayyeucau = ngayyeucau;
	}

	public String getLydoyeucau() 
	{
		return this.lydoyeucau;
	}

	public void setLydoyeucau(String lydoyeucau) 
	{
		this.lydoyeucau = lydoyeucau;
	}

	public String getKhoXuatId() 
	{
		return this.khoXuatId;
	}

	public void setKhoXuatId(String khoxuattt) 
	{
		this.khoXuatId = khoxuattt;
	}

	public ResultSet getKhoXuatRs()
	{
		return this.khoXuatRs;
	}

	public void setKhoXuatRs(ResultSet khoxuatRs) 
	{
		this.khoXuatRs = khoxuatRs;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
	}

	public List<IYeucau> getSpList()
	{
		return this.spList;
	}

	public void setSpList(List<IYeucau> spList) 
	{
		this.spList = spList;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getTsddId() 
	{
		return this.tsddId;
	}

	public void setTsddId(String tsddId) 
	{
		this.tsddId = tsddId;
	}
	
	public boolean createCK() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}
		
		if(this.ngayxuatthucte.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày xuất thực tế";
			return false;
		}
		
		if(this.ndxId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung xuất";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}
		
		if( this.cokhonhan.equals("1") && this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho nhận";
			return false;
		}
		
		if( this.codoituong.equals("1") && this.doituongId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng xuất";
			return false;
		}
		
		if( this.codoituongNhan.equals("1") && this.doituongNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng nhận";
			return false;
		}
		
		if( this.coChiphi.equals("1") && this.chiphiId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn chi phí";
			return false;
		}
				
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
		else
		{
			//Check trung san pham + 1
			for(int i = 0; i < this.spList.size() - 1; i++)
			{
				IYeucau yc = this.spList.get(i);
				for(int j = i + 1; j < this.spList.size(); j++)
				{
					IYeucau yc2 = this.spList.get(j);
					
					if( yc.getId().trim().equals(yc2.getId().trim()) && yc.getLsxId().trim().equals(yc2.getLsxId().trim()) )
					{
						this.msg = "Sản phẩm ( " + yc.getTen() + " ) đã trùng  . Vui lòng kiểm tra lại.";
						return false;
					}
					
				}
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String lsx_fk = this.lsxIds.trim().length() <= 0 ? "null" : this.lsxIds.trim();
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			
			String _loaidoituongId = this.loaidoituong.trim().length() <= 0 ? "null" : this.loaidoituong.trim();
			String _doituongId = this.doituongId.trim().length() <= 0 ? "null" : this.doituongId.trim();
			
			String _loaidoituongNhanId = this.loaidoituongNhan.trim().length() <= 0 ? "null" : this.loaidoituongNhan.trim();
			String _doituongNhanId = this.doituongNhanId.trim().length() <= 0 ? "null" : this.doituongNhanId.trim();
			
			String _chiphiId = this.chiphiId.trim().length() <= 0 ? "null" : this.chiphiId.trim();
			
			String query = 	" insert ERP_CHUYENKHO(Yeucauchuyenkho_fk, lenhsanxuat_fk, IsChuyenHangSX, NGUOINHAN, noidungxuat_fk, NgayChuyen, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua, loaidoituong, DOITUONG_FK, loaidoituongNHAN, DOITUONGNHAN_FK, KYHIEU, SOCHUNGTU, LENHDIEUDONG, NGAYDIEUDONG, NGUOIDIEUDONG, VEVIEC, NGUOIVANCHUYEN, PHUONGTIEN, HOPDONG, chiphi_fk, Ngayxuatthucte ) " +
				   			" values('" + this.ycckId + "', " + lsx_fk + ", " + this.IsChuyenHangSX + ", N'" + this.Nguoinhan + "', '" + this.ndxId + "', '" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" + this.khoXuatId + "', " + khonhan_fk + ",  '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', " + _loaidoituongId + ", " + _doituongId + ", " + _loaidoituongNhanId + ", " + _doituongNhanId + ", "+
				   			"		'" + this.kyhieu + "', '" + this.sochungtu + "', N'" + this.lenhdieudong + "', '" 
				   			+ this.ngaydieudong + "', N'" + this.nguoidieudong + "', N'" + this.veviec + "', N'" + 
				   			this.nguoivanchuyen + "', N'" + this.phuongtien + "', N'" + this.hopdong + "', " + _chiphiId + ",'"+ this.ngayxuatthucte+"') ";
			
			System.out.println("::: CHEN CHUYEN KHO: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_CHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = "";
			query = "select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
			
			ResultSet rsPxk = db.get(query);						
			if(rsPxk.next())
			{
				ycnlCurrent = rsPxk.getString("ckId");
				 
			}
			rsPxk.close();
			
			Utility util = new Utility();
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					if(sp.getSoluongYC().trim().length() > 0)
					{
						query = " insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK,  soluongyeucau, soluongxuat, ghichu_chuyenkho, lenhsanxuat_fk  ) " +
								" values( '" + ycnlCurrent + "', '" + sp.getId() + "', " + sp.getSoluongYC() + ", " + sp.getSoluongYC() + ", N'" + sp.getghichu_CK() + "' , " + (sp.getLsxId().trim().length() > 0 ? sp.getLsxId() : null) + "   ) ";
						
						System.out.println("::: CHEN SAN PHAM: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						//INSERT CHI TIET
						if(this.sanpham_soluong != null)
						{
							Enumeration<String> keys = this.sanpham_soluong.keys();
							double totalCT = 0;
							
							while(keys.hasMoreElements())
							{
								String key = keys.nextElement();
								
								System.out.println("::: KEY UPDATE: " + key );
								if(key.startsWith( sp.getMa() + "__ "  ) )
								{
									String[] _sp = key.split("__");
									
									String _soluongCT = "0"; 
									if(this.sanpham_soluong.get(key) != null)
									{
										_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
									}
									
									System.out.println("::: KEY UPDATE: " + key + " -- SO LUONG CT: " + _soluongCT );
									String solo = _sp[2];
									String ngayhethan = _sp[3];
									String ngaynhapkho = _sp[4];
									String MAME = _sp[5];
									String MATHUNG = _sp[6];
									String bin_fk = _sp[7];
									String MAPHIEU = _sp[8];
									String phieudt = _sp[9];
									String phieueo = _sp[10];
									String MARQ = _sp[11];
									String HAMLUONG = _sp[12];
									String HAMAM = _sp[13];
									String nsx_fk = _sp[17];
									
									if(!_soluongCT.equals("0"))
									{
										totalCT += util.Round(Double.parseDouble(_soluongCT), 4);
										System.out.println("::: TOTAL BUOC NAY: " + totalCT + " -- SO LAM TRON: " + util.Round(Double.parseDouble(_soluongCT), 4));
										
										query = "insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, nsx_fk ) " +
												"select '" + ycnlCurrent + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + bin_fk + "' ), " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "', '" + nsx_fk + "' " +
												"from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										//System.out.println("1.2.Insert ERP_CHUYENKHO_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										//CẬP NHẬT KHO
										this.msg = util.Update_KhoTT_MOI(this.ngayyeucau, "Chuyển kho " + ycnlCurrent, db, this.khoXuatId, sp.getId(), solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, " isnull( ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + bin_fk + "' ) , 0) ",
														MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, this.loaidoituong, this.doituongId, 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")), nsx_fk);
										if( this.msg.trim().length() > 0 )
										{
											db.getConnection().rollback();
											return false;
										}
									}
								}
							}
							
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							//LAM TRONG TUNG SO ROI, MA XUONG DUOI JAVA NO VAN DE LE CA CHUC SO DANG SAU, NEN PHAI LAM TRONG LAN NUA
							if(util.Round(totalCT, 4) != Double.parseDouble(sp.getSoluongYC().replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getMa() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + sp.getSoluongYC() + " ) ";
								db.getConnection().rollback();
								return false;
							}
						}
				 
					}
				}
			}
			
			if(this.ycckId.length() > 0 )
			{
				query = " update erp_yeucauchuyenkho set trangthai=2 where pk_seq = " + this.ycckId;
				System.out.println("::: CAP NHAT YCXK: " + query);
				
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật erp_yeucauchuyenkho " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//Cập nhật tooltip
			db.execProceduce2("CapNhatTooltip_CK", new String[] { this.id } );
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public boolean updateCK() 
	{ 
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}
		
		if(this.ngayxuatthucte.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày xuất thực tế";
			return false;
		}

		if(this.ndxId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung xuất";
			return false;
		}

		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}

		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}
		
		if(this.ndxId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung xuất";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}
		
		if( this.cokhonhan.equals("1") && this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho nhận";
			return false;
		}
		
		if( this.codoituong.equals("1") && this.doituongId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng xuất";
			return false;
		}
		
		if( this.codoituongNhan.equals("1") && this.doituongNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng nhận";
			return false;
		}
		
		if( this.coChiphi.equals("1") && this.chiphiId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn chi phí";
			return false;
		}
			
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
		else
		{
			//Check trung san pham + 1
			for(int i = 0; i < this.spList.size() - 1; i++)
			{
				IYeucau yc = this.spList.get(i);
				for(int j = i + 1; j < this.spList.size(); j++)
				{
					IYeucau yc2 = this.spList.get(j);

					if( yc.getId().trim().equals(yc2.getId().trim()) && yc.getLsxId().trim().equals(yc2.getLsxId().trim()) )
					{
						this.msg = "Sản phẩm ( " + yc.getTen() + " ) đã trùng  . Vui lòng kiểm tra lại.";
						return false;
					}

				}
			}
		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String _loaidoituongId = this.loaidoituong.trim().length() <= 0 ? "null" : this.loaidoituong.trim();
			String _doituongId = this.doituongId.trim().length() <= 0 ? "null" : this.doituongId.trim();
			
			String _loaidoituongNhanId = this.loaidoituongNhan.trim().length() <= 0 ? "null" : this.loaidoituongNhan.trim();
			String _doituongNhanId = this.doituongNhanId.trim().length() <= 0 ? "null" : this.doituongNhanId.trim();

			String _chiphiId = this.chiphiId.trim().length() <= 0 ? "null" : this.chiphiId.trim();
			String cd_fk = this.CdsxId.trim().length() <= 0 ? "null" : this.CdsxId.trim();
			String query =  " update  ERP_CHUYENKHO set IsChuyenHangSX='" + this.IsChuyenHangSX + "', NGUOINHAN=N'" + this.Nguoinhan + "', noidungxuat_fk = '" + this.ndxId + "', NGAYCHUYEN = '" + this.ngayyeucau + "', lydo = N'" + this.lydoyeucau + "' " +
							"\n ,ghichu =N'"+this.ghichu+"', khoxuat_fk="+this.khoXuatId+", khonhan_fk=" + khonhan_fk + ",ngaysua='"+this.getDateTime()+"', nguoisua="+this.userId+"," +
							"\n  loaidoituong = " + _loaidoituongId + ", DOITUONG_FK = " + _doituongId + ", loaidoituongNHAN = " + _loaidoituongNhanId + ", DOITUONGNHAN_FK = " + _doituongNhanId + ", "+
							"\n kyhieu='"+this.kyhieu+"', sochungtu='"+this.sochungtu+"',lenhdieudong=N'"+this.lenhdieudong+"',ngaydieudong='"+this.ngaydieudong+"',nguoidieudong=N'"+this.nguoidieudong+"',veviec=N'"+this.veviec+"',"+
							"\n nguoivanchuyen=N'"+this.nguoivanchuyen+"',phuongtien=N'"+this.phuongtien+"',hopdong=N'"+this.hopdong+"', chiphi_fk = "+ _chiphiId +
							"\n ,Ngayxuatthucte='"+this.ngayxuatthucte + "',congdoan_fk="+cd_fk+", Lenhsanxuat_fk = "+ (this.lsxIds.trim().length() > 0 ? this.lsxIds: null) +"  "+
							"\n  where pk_seq="+this.id;
			
			System.out.println("::: CAP NHAT CHUYEN KHO: "  + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ER_CHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			// nếu là gia công, sản xuất thì không xóa 
			
			boolean isCktudong=IsChuyenkhoTuDong();
			
		 
				query = " DELETE ERP_CHUYENKHO_SANPHAM WHERE CHUYENKHO_FK = "+this.id;
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_CHUYENKHO_SANPHAM " + query;
					db.getConnection().rollback();
					return false;
				}
			 
			
			Utility util = new Utility();
			//Tăng kho ngược lại trước khi xóa
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					"  a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, isnull(a.nsx_fk, 0) as nsx_fk, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + this.id + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo, a.nsx_fk ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			 	String spid_old="0";
				while( rs.next() )
				{
					String khoId = rs.getString("KhoXuat_FK");
					String spId = rs.getString("SanPham_fk");
					spid_old+=","+spId;
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
					String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong = rs.getDouble("soluong");
					
					String nsx_fk = rs.getString("nsx_fk");
					
					msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Cập nhật CK - Tăng kho ngược lại trước khi xóa " + this.id, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong, nsx_fk);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			 
			
			query = "delete ERP_CHUYENKHO_SANPHAM_CHITIET where chuyenkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			

			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);

					if(sp.getSoluongYC().trim().length() > 0)
					{ 
						query = "insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK,  soluongyeucau, soluongxuat, ghichu_chuyenkho ,lenhsanxuat_fk  ) " +
								"values( '" +this.id+ "', '" + sp.getId() + "', " + sp.getSoluongYC() + ", " + sp.getSoluongYC() + ", N'"+sp.getghichu_CK()+"' , "+ (sp.getLsxId().trim().length() > 0 ? sp.getLsxId() : null) +"   ) ";
						System.out.println("::: ERP_CHUYENKHO_SANPHAM: " + query );
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_CHUYENKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						 
						//INSERT CHI TIET
						if(this.sanpham_soluong != null)
						{
							Enumeration<String> keys = this.sanpham_soluong.keys();
							double totalCT = 0;
							
							while(keys.hasMoreElements())
							{
								String key = keys.nextElement();
								
								System.out.println("::: KEY UPDATE: " + key );
								if(key.startsWith( sp.getMa() + "__ "  ) )
								{
									String[] _sp = key.split("__");
									
									String _soluongCT = "0"; 
									if(this.sanpham_soluong.get(key) != null)
									{
										_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
									}
									
									System.out.println("::: KEY UPDATE: " + key + " -- SO LUONG CT: " + _soluongCT );
									String solo = _sp[2];
									String ngayhethan = _sp[3];
									String ngaynhapkho = _sp[4];
									String MAME = _sp[5];
									String MATHUNG = _sp[6];
									String bin_fk = _sp[7];
									String MAPHIEU = _sp[8];
									String phieudt = _sp[9];
									String phieueo = _sp[10];
									String MARQ = _sp[11];
									String HAMLUONG = _sp[12];
									String HAMAM = _sp[13];
									String nsx_fk = _sp[17];
									
									if(!_soluongCT.equals("0"))
									{
										totalCT += util.Round(Double.parseDouble(_soluongCT), 4);
										System.out.println("::: TOTAL BUOC NAY: " + totalCT + " -- SO LAM TRON: " + util.Round(Double.parseDouble(_soluongCT), 4));
										
										query = "insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM,  soluong, nsx_fk ) " +
												"select '" + this.id + "', pk_seq, ' ', N'" + solo + "', N'" + ngayhethan + "', '" + ngaynhapkho + "', '" + MAME + "', '" + MATHUNG + "', ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + bin_fk + "' ), " + 
												" 	'" + MAPHIEU + "', '" + phieudt + "', '" + phieueo + "', '" + MARQ + "', '" + HAMLUONG + "', '" + HAMAM + "', '" + _soluongCT.replaceAll(",", "") + "', '" + nsx_fk + "' " +
												"from ERP_SANPHAM where MA = '" + sp.getMa() + "' ";
										
										//System.out.println("1.2.Insert ERP_CHUYENKHO_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_CHUYENKHO_SANPHAM_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										//CẬP NHẬT KHO
										this.msg = util.Update_KhoTT_MOI(this.ngayyeucau, "Cập nhật chuyển kho " + this.id, db, this.khoXuatId, sp.getId(), solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, " isnull( ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + bin_fk + "' ) , 0) ",
														MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, this.loaidoituong, this.doituongId, 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")), nsx_fk);
										if( this.msg.trim().length() > 0 )
										{
											db.getConnection().rollback();
											return false;
										}
									}
								}
							}
							
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							//LAM TRONG TUNG SO ROI, MA XUONG DUOI JAVA NO VAN DE LE CA CHUC SO DANG SAU, NEN PHAI LAM TRONG LAN NUA
							if(util.Round(totalCT, 4) != Double.parseDouble(sp.getSoluongYC().replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + sp.getMa() + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + sp.getSoluongYC() + " ) ";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			if(this.ycckId.length() > 0 )
			{
				query = " update erp_yeucauchuyenkho set trangthai=2 where pk_seq = " + this.ycckId;
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật erp_yeucauchuyenkho " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			// kiểm tra kho chi tiết và kho tổng có sản phẩm ,ko có trong ch tiết,mà có trong tổng
			if(isCktudong){
				String sploi=Check_Hople(spid_old);
				if(sploi.length() >0){
					this.msg = "Không thể cập nhật chuyển kho này,sản phẩm:  " +sploi+" không được cập nhật  " +
							" trong phiếu chuyển kho này,vui lòng báo Admin để được trợ giúp" ;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//Cập nhật tooltip
			db.execProceduce2("CapNhatTooltip_CK", new String[] { this.id } );
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	private String Check_Hople(String spid_old) {
		try{
			String query=" SELECT PK_SEQ,MA+' - '+TEN AS TEN FROM ERP_SANPHAM WHERE PK_SEQ IN ("+spid_old+") " +
						 " AND PK_SEQ NOT IN (SELECT SANPHAM_FK FROM ERP_CHUYENKHO_SANPHAM WHERE CHUYENKHO_FK= "+this.id+")";
			ResultSet rs=db.get(query);
			boolean bien=false;
			String spten="";
			while (rs.next()){
				spten+=","+rs.getString("TEN");
			}
			rs.close();
			
			return spten;
			
		}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
		}
		
	}

	private boolean IsChuyenkhoTuDong() {
		// TODO Auto-generated method stub
		try{
			String query="SELECT pk_seq FROM ERP_CHUYENKHO WHERE LENHSANXUAT_FK IS NOT NULL OR MUAHANG_FK IS NOT NULL AND PK_SEQ="+this.id;
			ResultSet rs=db.get(query);
			boolean bien=false;
			if(rs.next()){
				bien=true;
			}
			rs.close();
			return bien;
			
		}catch(Exception er){
			er.printStackTrace();
		}
		return false;
	}

	public boolean updateSoHoaDon() 
	{ 
		try
		{
			db.getConnection().setAutoCommit(false);

			if(this.ndxId.equals("100025"))
			{
				if(this.sochungtu.trim().length()>0){
					if(this.checkSoHoaDon())
					{
						this.msg = "Số hóa đơn : "+this.sochungtu+" đã có, vui lòng chọn lại.";
						System.out.println(this.msg);
						return false;
					}
				}
			}

			String query =  " update  ERP_YEUCAUCHUYENKHO set   NGUOINHAN=N'"+this.Nguoinhan+"'   " +
					" ,ghichu =N'"+this.ghichu+"', "+
					" ngaysua='"+this.getDateTime()+"', nguoisua="+this.userId+"," +
					" kyhieu='"+this.kyhieu+"', sochungtu='"+this.sochungtu+"',lenhdieudong=N'"+this.lenhdieudong+"',ngaydieudong='"+this.ngaydieudong+"',nguoidieudong=N'"+this.nguoidieudong+"',veviec=N'"+this.veviec+"',"+
					" nguoivanchuyen=N'"+this.nguoivanchuyen+"',phuongtien=N'"+this.phuongtien+"',hopdong=N'"+this.hopdong+"'"+
					" where pk_seq="+this.id;

			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}

		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	private boolean CheckExistMarq(String sanpham_fk, String marq) {
		try {
			if (marq.equals("")) {
				return true;
			}
			String sql = "select count(*) as s from marquette where ma = N'" + marq + "' "
					+ " and sanpham_fk = " + sanpham_fk;
			System.out.println("Check Marquette: " + sql);
			ResultSet rs = this.db.get(sql);
			int s = 0;
			if (rs != null) {
				if (rs.next()) {
					s = rs.getInt("s");
				}
			}
			if(s > 0){
				return true;
			}
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean nhanHang() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày nhận hàng";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
			return false;
		}
		
		try
		{
			Utility util = new Utility();
			db.getConnection().setAutoCommit(false);
			 
			msg = util.CheckKhoaSoKho(db, "", "ERP_CHUYENKHO", "NGAYCHUYEN", this.id);
			if( msg.trim().length() > 0 )
			{
				db.getConnection().rollback();
				return false;
			}
			
			String query="select pk_seq from erp_chuyenkho where trangthai=1 and pk_seq= "+this.id;
			
			ResultSet rsck=db.get(query);
			if(!rsck.next()){
				this.msg = "Trạng thái chốt chuyển kho không hợp lệ, vui lòng thử lại  hoặc trong trường hợp phiếu đã được chốt nhận hàng trong 1 phiên giao dịch khác ";
				db.getConnection().rollback();
				return false;
			}
			
			  query = 		" Update ERP_CHUYENKHO set sokien=N'"+this.sokien+"', sochungtu='"+this.sochungtu+"', ngaynhan = '" + this.ngayyeucau + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId +
							" ', trangthai = '3' , nguoinhan=N'" + this.Nguoinhan +"' " +
							" where pk_seq = '" + this.id + "' ";
			
			System.out.println("::: CAP NHAT CK: " + query);	
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "update ERP_CHUYENKHO_SANPHAM set SOLUONGNHAN = SOLUONGYEUCAU where CHUYENKHO_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			/*query = "delete ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG where chuyenkho_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			//CẬP NHẬT LẠI VỊ TRÍ NHẬN ==> 1 VỊ TRÍ XUẤT CÓ THỂ SẼ CÓ NHIỀU VỊ TRÍ NHẬN
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					String spId = sp.getId();

					if(sp.getSoluongYC().trim().length() > 0)
					{
						//INSERT CHI TIET
						if(this.sanpham_soluong != null)
						{
							Enumeration<String> keys = this.sanpham_soluong.keys();
							while(keys.hasMoreElements())
							{
								String key = keys.nextElement();
								
								if(key.startsWith( sp.getMa() + "__ "  ) )
								{
									String[] _sp = key.split("__");
									
									String solo = _sp[2];
									String ngayhethan = _sp[3];
									String ngaynhapkho = _sp[4];
									String MAME = _sp[5];
									String MATHUNG = _sp[6];
									
									String bin_fk = _sp[7];
									if( bin_fk.contains(" [") )
										bin_fk = bin_fk.substring(bin_fk.indexOf(" [") + 2 , bin_fk.indexOf("]"));
									
									String MAPHIEU = _sp[8];
									String phieudt = _sp[9];
									String phieueo = _sp[10];
									String MARQ = _sp[11];
									String HAMLUONG = _sp[12];
									String HAMAM = _sp[13];
									String pk_seq = _sp[14];
									
									String nsx_fk = _sp[17];
									
									//KHO KHÔNG QUẢN LÝ BIN
									if( bin_fk.trim().length() <= 5 )
										bin_fk = "0";
									
									//rang marquette
									if(!CheckExistMarq(spId, MARQ)) {
										this.msg = "Ma marquette ("+MARQ+") cua san pham ("+sp.getMa()+") khong hop le";
										db.getConnection().rollback();
										return false;
									}
									
									query = " update ERP_CHUYENKHO_SANPHAM_CHITIET set binNhan_fk = '" + bin_fk + "' " + 
											" where chuyenkho_fk = '" + this.id + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where MA = '" + sp.getMa() + "' ) " + 
											" 	and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "' " + 
											"   and MAME = '" + MAME + "' and MATHUNG = '" + MATHUNG + "' and MAPHIEU = '" + MAPHIEU + "' and phieudt = '" + phieudt + "' and phieueo = '" + phieueo + "' " + 
											"   and MARQ = '" + MARQ + "' and HAMLUONG = '" + HAMLUONG + "' and HAMAM = '" + HAMAM + "' and NSX_FK = " + nsx_fk + " ";
									
									System.out.println("1.2.Update ERP_CHUYENKHO_SANPHAM_CHITIET: " + query);
									if(!db.update(query) )
									{
										this.msg = "Khong the cap nhat ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									
									String soluong = this.sanpham_soluong.get(key);
									/*query = "  insert ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG( chuyenkho_fk, binnhan_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam,  " + 
											"  	MAME, MATHUNG, MAPHIEU, phieudt, phieueo, soluong		 ) " + 
											"  select '" + this.id + "' chuyenkho_fk, '" + bin_fk + "', ( select pk_seq from ERP_SANPHAM where MA = '" + sp.getMa() + "' ) SANPHAM_FK, '" + solo + "' solo, '" + ngayhethan + "' ngayhethan, 0 as LOAI, '' scheme, '" + ngaynhapkho + "' ngaynhapkho, " + 
											" 	'" + MARQ + "' MARQ, '" + HAMLUONG + "' hamluong, '" + HAMAM + "' hamam, " + 
											"  	'" + MAME + "' MAME, '" + MATHUNG + "' MATHUNG, '" + MAPHIEU + "' MAPHIEU, '" + phieudt + "' phieudt, '" + phieueo + "' phieueo, '" + soluong.replaceAll(",", "") + "' as soluong ";*/
									
									query = "   update ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG " + 
											" 	set ngaynhapkho='"+this.ngayyeucau+"', binnhan_fk = '" + bin_fk + "', soluong = '" + soluong.replaceAll(",", "") +
											"', marq = '" + MARQ + "', nsx_fk = " + nsx_fk + " " + 
											"   where chuyenkho_fk = '" + this.id + "' and pk_seq = '" + pk_seq + "' ";
									System.out.println("::: CHEN CHI TIET: " + query);
									if(!db.update(query) )
									{
										this.msg = "Khong the cap nhat ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG: " + query;
										db.getConnection().rollback();
										return false;
									}
									
								}
							}
						}
					}
				}
			}
			
			//TĂNG KHO NHẬN
			query = "  select b.loaidoituongNhan, b.doituongnhan_fk as doituongNhanId, b.NGAYCHUYEN, b.KHONHAN_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.binNhan_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong, isnull(a.nsx_fk, 0) as nsx_fk  " + 
					"  from ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + this.id + "' and a.soluong > 0 " + 
					"  group by b.loaidoituongNhan, b.doituongnhan_fk, b.NGAYCHUYEN, b.KHONHAN_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, binNhan_fk, a.phieudt, a.phieueo, a.nsx_fk ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			 
				while( rs.next() )
				{
					String khoId = rs.getString("KHONHAN_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = rs.getString("loaidoituongNhan") == null ? "" : rs.getString("loaidoituongNhan");
					String doituongId = rs.getString("doituongNhanId") == null ? "" : rs.getString("doituongNhanId");
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");
					
					double soluong = rs.getDouble("soluong");
					
					String nsx_fk = rs.getString("nsx_fk");

					msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Chốt nhận hàng erpchuyenkho.java 1218 "+this.id, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong, nsx_fk);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			 
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}
	
	public boolean xuatHang() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày xuất hàng";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
		else
		{
			boolean flag = false;
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau yc = this.spList.get(i);
				if(yc.getSoluongNhan().trim().length() > 0)
				{
					flag = true;
				
					String soluongNhan = yc.getSoluongNhan().trim().length() > 0 ? yc.getSoluongNhan().trim() : "0" ;
					String soluongChuyen = yc.getSoluongYC().trim().length() > 0 ? yc.getSoluongYC().trim() : "0" ;
					
					if( Float.parseFloat(soluongNhan) >  Float.parseFloat(soluongChuyen) )
					{
						this.msg = "Số lượng xuất không được phép vượt quá số lượng yêu cầu.";
						return false;
					}
				}
			}
			
			//
			if(!flag)
			{
				this.msg = "Không có sản phẩm nào được nhập số lượng.";
				return false;
			}
			
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "Update ERP_CHUYENKHO set ngaynhan = '" + this.ngayyeucau + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', trangthai = '2' " +
							"where pk_seq = '" + this.id + "' ";
							
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			query = "update ERP_CHUYENKHO_SANPHAM set SOLUONGNHAN = '0' where CHUYENKHO_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			/*query = "delete ERP_CHUYENKHO_SP_XUATHANG where chuyenkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SP_XUATHANG " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					//Update tong nhap
					if(sp.getSoluongNhan().trim().length() > 0)
					{
						query = "update ERP_CHUYENKHO_SANPHAM set SOLUONGNHAN = SOLUONGNHAN + '" + sp.getSoluongNhan() + "' " +
								"where SANPHAM_FK = '" + sp.getId() + "' and SOLO = '" + sp.getSolo() + "' and CHUYENKHO_FK = '" + this.id + "'";
						
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}
	
	public boolean chuyenNL() 
	{
		if(this.msg.trim().length() > 0)
		{
			this.msg = "Vui lòng kiểm tra lại các thông tin: \n" + this.msg;
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}
		
		if(this.khoNhanId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho nhận";
			return false;
		}
	
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
		else
		{
			boolean flag = false;
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau yc = this.spList.get(i);
				
				String soluongYC = yc.getSoluongYC().trim().length() > 0 ? yc.getSoluongYC().trim() : "0" ;
				String soluongDN = yc.getSoluongDaNhan().trim().length() > 0 ? yc.getSoluongDaNhan().trim() : "0";
				String soluongN = yc.getSoluongNhan().trim().length() > 0 ? yc.getSoluongNhan().trim() : "0" ;
				
				if(Integer.parseInt(soluongN) > ( Integer.parseInt(soluongYC) - Integer.parseInt(soluongDN) ) )
				{
					this.msg = "Số lượng chuyển không được phép vượt quá số lượng yêu cầu và số lượng đã chuyển";
					return false;
				}
				
				if(yc.getSoluongNhan().trim().length() > 0)
				{
					if(Integer.parseInt(yc.getSoluongNhan().trim()) > 0)
					{
						flag = true;
					}
					
					List<ISpDetail> detail = yc.getSpDetailList();
					
					int sum = 0;
					for(int j = 0; j < detail.size(); j++)
					{
						if(detail.get(j).getSoluong().trim().length() > 0)
							sum += Integer.parseInt(detail.get(j).getSoluong().trim());
					}
					
					if( Integer.parseInt(yc.getSoluongNhan()) != sum )
					{
						this.msg = "Vui lòng kiểm tra Bin / Lô hàng xuất trước khi thực hiện thao tác.";
						flag = false;
						return false;
						
					}
				}
			}
			
			if(!flag)
			{
				this.msg = "Vui lòng kiểm tra Bin / Lô hàng xuất trước khi thực hiện thao tác.";
				return false;
			}
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IYeucau sp = this.spList.get(i);
				String query =  "Update ERP_YEUCAUNGUYENLIEU_SANPHAM set soluongnhan = soluongnhan + '" + sp.getSoluongNhan() + "' " +
								"where  yeucaunguyenlieu_fk = '" + this.id + "' and  SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "') ";
				
				if(!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
				}
				else
				{
					List<ISpDetail> spCon = sp.getSpDetailList();
					for(int j = 0; j < spCon.size(); j++)
					{
						ISpDetail detail = spCon.get(j);
						
						if( ! detail.equals("0"))
						{
							
							//Luu lai luong xuatkho
							query =  "insert ERP_YEUCAUNGUYENLIEU_SP_XUATKHO(yeucaunguyenlieu_fk, sanpham_fk, khott_fk, solo, soluong, bean) " +
									 "select '" + this.id + "', pk_seq, '" + this.khoXuatId + "', '" + detail.getSolo() + "', '" + detail.getSoluong() + "', '" + detail.getVitriId() + "' " +
									 "from ERP_SanPham where ma = '" + sp.getMa() + "' ";
							
							if(!db.update(query))
							{
								this.msg = "2.Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SP_XUATKHO: " + query;
								db.getConnection().rollback();
								return false;
							}
							
							
							query = "select count(*) as sodong from ERP_YEUCAUNGUYENLIEU_SP_CHITIET " +
									"where yeucaunguyenlieu_fk = '" + this.id + "' and SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + detail.getMa() + "' ) and SOLO = '" + detail.getSolo() + "' and BEAN = '" + detail.getVitriId() + "'";
							
							ResultSet rsCheck = db.get(query);
							int sodong = 0;
							if(rsCheck != null)
							{
								if(rsCheck.next())
								{
									sodong = rsCheck.getInt("sodong");
								}
								rsCheck.close();
							}
							
							if(sodong <= 0)
							{
								query = "insert ERP_YEUCAUNGUYENLIEU_SP_CHITIET(yeucaunguyenlieu_fk, SANPHAM_FK, SOLO, SOLUONG, BEAN) " +
										"select '" + this.id + "', pk_seq, '" + detail.getSolo() + "', '" + detail.getSoluong() + "', '" + detail.getVitriId() + "' " +
										"from ERP_SanPham where ma = '" + detail.getMa() + "' ";
							}
							else
							{
								query = "update ERP_YEUCAUNGUYENLIEU_SP_CHITIET set soluong = soluong + '" + detail.getSoluong() + "' " + 
										"where yeucaunguyenlieu_fk = '" + this.id + "' and SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + detail.getMa() + "' ) and SOLO = '" + detail.getSolo() + "' and BEAN = '" + detail.getVitriId() + "'";
							}
							
							if(!db.update(query))
							{
								this.msg = "Khong the cap nhat ERP_YEUCAUNGUYENLIEU_SP_CHITIET: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) {}
		
		return true;
		
	}
	
	
	
	public void createRs() 
	{
		Utility util = new Utility();
		
		String query = "select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP  " +
					   "where trangthai = '1' and MA in ( 'XK03', 'XK04', 'XK07', 'XK08', 'XK09', 'XK10', 'XK12', 'XK17','XK19' ) " ;
		this.ndxRs = db.get(query);

		/*pk_seq	MA	TEN	LOAIKHO_XUAT
		100059	XK03	Xuất tiêu dùng nội bộ (Không xuất hóa đơn)	1,2,4
		100060	XK04	Xuất hủy	3
		100063	XK07	Xuất chuyển đến kho ký gửi	1,2,4,5
		100064	XK08	Xuất chuyển kho nội bộ	1,2,3,4,5,6,99
		100065	XK10	Xuất chuyển cho nhà gia công	1,2,4,5
		100066	XK12	Xuất chuyển sản xuất	2,3,4,10
		100068	XK14	Xuất đổi cho KH/NCC	1,2,4,5,8
		100073	XK17	Xuất chuyển từ HO-> CN	1*/
		
		/*  CAC LOAI KHO
		1	Kho hàng bán
		2	Kho nguyên vật liệu
		3	Kho hàng hỏng(kho biệt trữ)
		4	Kho vật tư
		5	Kho dữ trữ khách hàng
		6	Kho trình duyệt viên
		7	Kho  nhà gia công
		8	Kho hàng ký gửi tại KH
		9	Kho khách hàng ký gửi tại công ty
		11	Kho CCDC, Phụ tùng thay thế*/

		
		if( this.ndxId.trim().length() > 0 )
		{
			query = "select LOAIKHO_XUAT, coChiphi from ERP_NOIDUNGNHAP where pk_seq = '" + this.ndxId + "'";
			ResultSet rs = db.get(query);
			
			String LOAIKHO_XUAT = "";
			if( rs != null )
			{
				try 
				{
					if( rs.next() )
					{
						LOAIKHO_XUAT = rs.getString("LOAIKHO_XUAT");
						this.coChiphi = rs.getString("coChiphi");
					}
					rs.close();
				} 
				catch (Exception e) { 
					e.printStackTrace();
				}
			}
			
			query = "select PK_SEQ, TEN, LOAI from ERP_KHOTT where TrangThai = '1' ";
			if( LOAIKHO_XUAT.trim().length() > 0 )
				query += " and loaiKHO in ( " + LOAIKHO_XUAT + " ) ";
			
			if( this.isnhanHang.equals("0") )
				query += " and pk_seq in " + util.quyen_khott(this.userId);
			query += " order by loai asc, TEN asc ";
			
			System.out.println("::: LAY KHO XUAT: " + query);
			this.khoXuatRs = db.get(query);
			
			if( this.coChiphi.equals("1") )
			{
				if(this.getMaNDX( this.ndxId ).equals("XK19"))
				{
					query = "select PK_SEQ, TEN + ', ' + DIENGIAI as TEN from ERP_NHOMCHIPHI where TRANGTHAI = '1' and taikhoan_fk like '632%' order by TEN asc";
					this.chiphiRs = db.get(query);
				}else{
					query = "select PK_SEQ, TEN + ', ' + DIENGIAI as TEN from ERP_NHOMCHIPHI where TRANGTHAI = '1' order by TEN asc";
					this.chiphiRs = db.get(query);
				}
			}
		}
		
		if( this.khoXuatId.trim().length() > 0 )
		{  
			query = "select loaiKHO from ERP_KHOTT where PK_SEQ = '" + this.khoXuatId + "' ";
			ResultSet rs = db.get(query);
			String loaikho = "";
			if( rs != null )
			{
				try 
				{
					if( rs.next() )
					{
						loaikho = rs.getString("loaiKHO");
					}
					rs.close();
				} 
				catch (Exception e) { 
					e.printStackTrace();
				}
			}

			System.out.println( " loai kho : "+ loaikho);

			if( loaikho.equals("7") || loaikho.equals("8") || loaikho.equals("9") || loaikho.equals("77") || loaikho.equals("13")|| loaikho.equals("14")|| loaikho.equals("10"))
			{
				this.codoituong = "1";

				//LOAIDOITUONG 0 NHÀ CUNG CẤP, 1 KHÁCH HÀNG, 3 phòng ban,2 nhà phân phối, 4 là nhân viên
				if( loaikho.equals("7") )
				{
					this.loaidoituong = "0";
					this.doituongRs = db.get("select pk_seq, ten from ERP_NHACUNGCAP where TRANGTHAI = 1 and duyet = '1' and congty_fk="+this.ctyId+" order by TEN asc");
				}
				else if(loaikho.equals("77"))
				{
					this.loaidoituong = "3";
					this.doituongRs = db.get("select pk_seq, ten from ERP_DONVITHUCHIEN where TRANGTHAI = 1 and congty_fk="+this.ctyId+" order by TEN asc");
				}
				else if( loaikho.equals("8") || loaikho.equals("9") || loaikho.equals("13") )
				{
					this.loaidoituong = "1";
					this.doituongRs = db.get("select pk_seq, ten from ERP_KHACHHANG where   TRANGTHAI = 1 order by TEN asc");
				}else if(loaikho.equals("14")){
					this.loaidoituong = "4";
					this.doituongRs = db.get("select pk_seq, ten from erp_nhanvien where   TRANGTHAI = 1 order by TEN asc");
				}else if(loaikho.equals("10")){
				 
					this.loaidoituong = "5";
					if(this.lsxIds!=null && this.CdsxId !=null && this.CdsxId.length() > 0 && this.lsxIds.length() >0  ) {
					  query="select lsx_cd.PK_SEQ , N'Số LSX:' + cast(lsx_cd.LENHSANXUAT_FK as nvarchar(20)) +', CĐ: ' + cd.DienGiai as TEN "
							+ "from ERP_LENHSANXUAT_CONGDOAN_GIAY lsx_cd inner join Erp_CongDoanSanXuat_Giay cd on cd.PK_SEQ=lsx_cd.CONGDOAN_FK"
							+ " where lsx_cd.LENHSANXUAT_FK= "+lsxIds+" ";
									//+ " and CONGDOAN_FK="+CdsxId;
					  this.doituongRs = db.get(query);
					}
					

					//System.out.println( " doi tuong kho:  "+ "select pk_seq, ten from NHAPHANPHOI where isCongty = 0 and TRANGTHAI = 1 and congty_fk = "+this.ctyId+" and pk_seq in ( select CHINHANH_FK from ERP_KHOTT_CHINHANH where KHOTT_FK = '" + this.khoXuatId + "' ) order by TEN asc");

				}	
				else
				{
					this.loaidoituong = "2";
					this.doituongRs = db.get("select pk_seq, ten from NHAPHANPHOI where isCongty = 0 and TRANGTHAI = 1 and congty_fk = "+this.ctyId+" and pk_seq in ( select CHINHANH_FK from ERP_KHOTT_CHINHANH where KHOTT_FK = '" + this.khoXuatId + "' ) order by TEN asc");

					System.out.println( " doi tuong kho:  "+ "select pk_seq, ten from NHAPHANPHOI where isCongty = 0 and TRANGTHAI = 1 and congty_fk = "+this.ctyId+" and pk_seq in ( select CHINHANH_FK from ERP_KHOTT_CHINHANH where KHOTT_FK = '" + this.khoXuatId + "' ) order by TEN asc");

				}	
				
				
			}
			else
			{
				this.codoituong = "";
				this.loaidoituong = "";
			}
		
			
		}
		
		if( this.getMaNDX( this.ndxId ).equals("XK07") || this.getMaNDX( this.ndxId ).equals("XK08") || this.getMaNDX( this.ndxId ).equals("XK09")
				|| this.getMaNDX( this.ndxId ).equals("XK10") || this.getMaNDX( this.ndxId ).equals("XK12") || this.getMaNDX( this.ndxId ).equals("XK17")	)
		{
			query = "select LOAIKHO_NHAP from ERP_NOIDUNGNHAP where pk_seq = '" + this.ndxId + "'";
			System.out.println("::: SETTING: " + query);
			ResultSet rs = db.get(query);
			
			String LOAIKHO_NHAP = "";
			if( rs != null )
			{
				try 
				{
					if( rs.next() )
					{
						LOAIKHO_NHAP = rs.getString("LOAIKHO_NHAP");
					}
					rs.close();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			query = " select PK_SEQ, TEN, LOAI from ERP_KHOTT where TrangThai = '1' ";
			// xuất kho sản xuất thì có thể chuyển kho khác công đoạn
			/*if(this.khoXuatId.trim().length() > 0 && !this.getMaNDX( this.ndxId ).equals("XK10")){
				query += " and pk_seq != '" + this.khoXuatId + "' ";
			}*/
			if( LOAIKHO_NHAP.trim().length() > 0 )
				query += " and loaiKHO in ( " + LOAIKHO_NHAP + " )  ";
			
			if( this.isnhanHang.equals("1") )
				query += " and pk_seq in " + util.quyen_khott(this.userId);
			
			query += " order by loai asc ";
			
			System.out.println("::: LAY KHO NHAN: " + query);
			this.khoNhanRs = db.get(query);
			
			this.cokhonhan = "1";
		}
		else
			this.cokhonhan = "0";
		
		if( this.khoNhanId.trim().length() > 0 )
		{
			query = "select loaiKHO from ERP_KHOTT where PK_SEQ = '" + this.khoNhanId + "' ";
			ResultSet rs = db.get(query);
			String loaikhonhan = "";
			if( rs != null )
			{
				try 
				{
					if( rs.next() )
					{
						loaikhonhan = rs.getString("loaiKHO");
					}
					rs.close();
				} 
				catch (Exception e) { 
					e.printStackTrace();
				}
			}


			System.out.println("loai kho nhan: "+loaikhonhan);


			if( loaikhonhan.trim().equals("7") || loaikhonhan.trim().equals("8")  || loaikhonhan.trim().equals("13") || loaikhonhan.trim().equals("14")  || loaikhonhan.trim().equals("9")  )
			{
				this.codoituongNhan = "1";

				//LOAIDOITUONG 0 NHÀ CUNG CẤP, 1 KHÁCH HÀNG
				if( loaikhonhan.equals("8")  || loaikhonhan.trim().equals("13")  || loaikhonhan.trim().equals("9")  )
				{
					this.loaidoituongNhan = "1";
					this.doituongNhanRs = db.get("select pk_seq, ten from erp_khachhang where TRANGTHAI = 1   order by TEN asc");
				}else if( loaikhonhan.trim().equals("14")){
					this.loaidoituongNhan = "4";
					this.doituongNhanRs = db.get("select pk_seq, ten from erp_nhanvien where TRANGTHAI = 1  order by TEN asc");
				}
				else
				{
					this.loaidoituongNhan = "0";
					this.doituongNhanRs = db.get("select pk_seq, ten from ERP_NHACUNGCAP where TRANGTHAI = 1 and duyet = '1' and congty_fk="+this.ctyId+" order by TEN asc");
				}			
			}
			else if( loaikhonhan.trim().equals("12") )
			{
				 
				
				//bên Tuelinh không còn đối tượng chi nhánh nữa 
			}else if(loaikhonhan.equals("10")){
					this.codoituongNhan = "1";
					this.loaidoituongNhan = "5";
					if(this.lsxIds!=null   && this.lsxIds.length() >0  ) {
					  query="select lsx_cd.PK_SEQ , N'Số LSX:' + cast(lsx_cd.LENHSANXUAT_FK as nvarchar(20)) +', CĐ: ' + cd.DienGiai as TEN "
							+ "from ERP_LENHSANXUAT_CONGDOAN_GIAY lsx_cd inner join Erp_CongDoanSanXuat_Giay cd on cd.PK_SEQ=lsx_cd.CONGDOAN_FK"
							+ " where lsx_cd.LENHSANXUAT_FK= "+lsxIds ;
					  this.doituongNhanRs = db.get(query);
					}
					

					//System.out.println( " doi tuong kho:  "+ "select pk_seq, ten from NHAPHANPHOI where isCongty = 0 and TRANGTHAI = 1 and congty_fk = "+this.ctyId+" and pk_seq in ( select CHINHANH_FK from ERP_KHOTT_CHINHANH where KHOTT_FK = '" + this.khoXuatId + "' ) order by TEN asc");

				}
			else
			{
				this.codoituongNhan = "";
				this.loaidoituongNhan = "";
			}
		}
	 
		if( this.getMaNDX( this.ndxId ).equals("XK10") )
		{
			//Chọn được lệnh sản xuất
			//query = "select PK_SEQ, cast(PK_SEQ as varchar(10)) + ' - [' + NGAYBATDAU + '][' + NGAYDUKIENHT + '] ' + diengiai as diengiai from ERP_LENHSANXUAT_GIAY where TRANGTHAI != 7 ";
			query = "select PK_SEQ,ISNULL(SOLENHSANXUAT,'')+ ' - ' + cast(PK_SEQ as varchar(10)) + ' - ' + diengiai as diengiai from ERP_LENHSANXUAT_GIAY where TRANGTHAI != 7 ";
			
			this.lsxRs = db.get(query);
			if(this.lsxIds!=null && this.lsxIds.length() >0){
				query="SELECT PK_SEQ,DIENGIAI FROM ERP_CONGDOANSANXUAT_GIAY CD WHERE CD.PK_SEQ IN (SELECT CONGDOAN_FK FROM ERP_LENHSANXUAT_CONGDOAN_GIAY where LENHSANXUAT_FK= "+this.lsxIds+") ";
				this.CdsxRs=db.get(query);
			}
			
		}
		

		if( ( this.khoXuatId.trim().length() > 0 || this.khoNhanId.trim().length() > 0 ) && this.spList.size() <= 0 && this.id.trim().length() > 0 )
		{
			this.createChuyenKho_SanPham();
		}

		if( this.isnhanHang.equals("1") && this.khoNhanId.trim().length() > 0 )
		{
			query = "select PK_SEQ, MA + ', ' + TEN as TEN from ERP_BIN where KHOTT_FK = '" + this.khoNhanId + "' and trangthai = '1' order by TEN asc";
			
			System.out.println("::: LAY VI TRI NHAN: " + query);
			this.vitriNhanRs = db.getScrol(query);
		}

		if( this.getMaNDX( this.ndxId ).equals("XK09") || this.getMaNDX( this.ndxId ).equals("XK12") )
		{
			query = "select PK_SEQ, SOPO+'-'+NGAYMUA as sopo from ERP_MUAHANG WHERE ISGIACONG='1' ";
			System.out.println("QUERY MUAHANG :"+ query);
			this.muahangList = db.get(query);
		}
	}

	public String getMaNDX(String ndxId) 
	{
		//Không nối database, lấy ID luôn, bảng này là không đổi
		if( ndxId.equals("100057") )
			return "XK01";
		if( ndxId.equals("100058") )
			return "XK02";
		if( ndxId.equals("100059") )
			return "XK03";
		if( ndxId.equals("100060") )
			return "XK04";
		if( ndxId.equals("100061") )
			return "XK05";
		if( ndxId.equals("100062") )
			return "XK06";
		if( ndxId.equals("100063") )
			return "XK07";
		if( ndxId.equals("100064") )
			return "XK08";
		if( ndxId.equals("100065") )
			return "XK09";
		if( ndxId.equals("100066") )
			return "XK10";
		if( ndxId.equals("100067") )
			return "XK11";
		if( ndxId.equals("100068") )
			return "XK12";
		if( ndxId.equals("100069") )
			return "XK13";
		if( ndxId.equals("100070") )
			return "XK14";
		if( ndxId.equals("100071") )
			return "XK15";
		if( ndxId.equals("100072") )
			return "XK16";
		if( ndxId.equals("100073") )
			return "XK17";
		if( ndxId.equals("100076") )
			return "XK19";
		return "";
	}

	private void createChuyenKho_SanPham() 
	{
		//kiem tra loai kho san xuat
		String query="select count (PK_SEQ) dem from erp_khott where pk_seq='"+this.khoXuatId+"' and loaiKHO=10";
		System.out.println("kho xuat "+query);
		int dem=0;
		ResultSet spRs=db.get(query);
		
		try {
			if(spRs.next())
			dem=spRs.getInt("dem");
			spRs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		 query = " select  b.DVDL_FK  as  DVDL_ID,   isnull(ghichu_chuyenkho,'') as ghichu_chuyenkho , b.pk_seq as spId, isnull( b.MA,b.ma) as spMa, b.Ten    as spTen, " +
				" DONVIDOLUONG.DIENGIAI AS DVT, a.LENHSANXUAT_FK as lsxId,  " +
				" ISNULL(a.SOLUONGYEUCAU, 0) as SOLUONGYEUCAU , isnull( ( select AVAILABLE from dbo.ufn_tonhientai_ngaynhapkho_full( ycck.KhoXuat_FK, a.sanpham_fk, ycck.NgayChuyen ) ), 0) as soluongton    " +
				" from ERP_CHUYENKHO_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
				" inner join erp_chuyenkho ycck on ycck.pk_seq= a.CHUYENKHO_FK  " +
				//" left join dbo.ufn_tonhientai_full( ) kho on kho.khott_fk=ycck.khoxuat_fk and kho.sanpham_fk= a.sanpham_fk       " +
				" LEFT JOIN DONVIDOLUONG ON DONVIDOLUONG.PK_SEQ = b.DVDL_FK  " +
				" where a.CHUYENKHO_FK = '" + this.id + "'   ORDER BY A.SOTT";

		 System.out.println("lenh  "+ this.lsxIds +" ma " +this.ndxId+" va ma "+ this.getMaNDX(this.ndxId) +" dem  "+ dem );
		if(this.lsxIds.trim().length()>0 && this.getMaNDX(this.ndxId).equals("XK10") && dem>0 ){
			 query = " select  b.DVDL_FK  as  DVDL_ID,   isnull(ghichu_chuyenkho,'') as ghichu_chuyenkho , b.pk_seq as spId, isnull( b.MA,b.ma) as spMa, b.Ten    as spTen, " +
						" DONVIDOLUONG.DIENGIAI AS DVT, a.LENHSANXUAT_FK as lsxId,  " +
						" ISNULL(a.SOLUONGYEUCAU, 0) as SOLUONGYEUCAU , isnull( ( select sum(AVAILABLE) from dbo.UFN_GETLSX_TON_NGAYNHAP( ycck.KhoXuat_FK,ycck.LENHSANXUAT_FK, a.sanpham_fk,ycck.NgayChuyen  ) ), 0) as soluongton   " +
						" from ERP_CHUYENKHO_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
						" inner join erp_chuyenkho ycck on ycck.pk_seq= a.CHUYENKHO_FK  " +
						" LEFT JOIN DONVIDOLUONG ON DONVIDOLUONG.PK_SEQ = b.DVDL_FK  " +
						" where a.CHUYENKHO_FK = '" + this.id + "'   ORDER BY A.SOTT";
		}

		System.out.println("__2.init SP: " + query );

		 spRs = db.get(query);
		List<IYeucau> spList = new ArrayList<IYeucau>();
		if(spRs != null)
		{
			try 
			{
				IYeucau sp = null;
				while(spRs.next())
				{
					String spId = spRs.getString("spId");
					String spMa = spRs.getString("spMa");
					String spTen = spRs.getString("spTen");

					String donvi = spRs.getString("DVT");
					String donvitinh_id = spRs.getString("DVDL_ID");

					sp = new Yeucau();
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setghichu_CK(spRs.getString("ghichu_chuyenkho"));
					sp.setTen(spTen);
					sp.setDonViTinh(donvi);
					sp.setSoluongTon(spRs.getString("soluongton"));
					sp.setSoluongYC(spRs.getString("soluongyeucau"));
					sp.setLsxId(spRs.getString("lsxId")== null ? "" :spRs.getString("lsxId") );

					
					sp.setDvtinh_id(donvitinh_id);	
					spList.add(sp);
				}
				spRs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			this.spList = spList;
		}
	}

	public void init() 
	{
		String query =  "select isnull(CONGDOAN_FK,0) as congdoan_fk , isnull(sokien,'') as sokien, isnull(Ngayxuatthucte,'') as Ngayxuatthucte,  ISNULL(TRANGTHAISP,1) AS TRANGTHAISP, isnull(ISCHUYENHANGSX,'0') as ISCHUYENHANGSX ,isnull(NGUOINHAN,'') as NGUOINHAN , isnull(Lenhsanxuat_fk, 0 ) as lsxid, noidungxuat_fk, NGAYCHUYEN, lydo, isnull(ghichu, '') as ghichu, khoxuat_fk, khonhan_fk, trangthai,  " + 
						" 	loaidoituong, doituong_fk, loaidoituongNhan, doituongNhan_fk, Yeucauchuyenkho_fk, isnull(NGAYCHOT,NGAYCHUYEN) as NGAYCHOTCK, " +
						" ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, "+
						" ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC, ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG, chiphi_fk, muahang_fk , ngaytao "+
						"from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		
		System.out.println("INIT CHUYENKHO da vào đay : : "+query);
		ResultSet rs = db.get(query);

		try 
		{
			if(rs.next())
			{
				this.ycckId = rs.getString("Yeucauchuyenkho_fk");
				this.sokien = rs.getString("sokien");
				this.ndxId = rs.getString("noidungxuat_fk");
				this.ngayyeucau = rs.getString("NGAYCHUYEN");
				this.lydoyeucau = rs.getString("lydo");
				this.Nguoinhan = rs.getString("nguoinhan");
				this.ghichu = rs.getString("ghichu");
				this.khoXuatId = rs.getString("khoxuat_fk");
				this.khoNhanId = rs.getString("khonhan_fk");
				this.trangthai = rs.getString("trangthai");

				this.loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				this.doituongId = rs.getString("doituong_fk") == null ? "" : rs.getString("doituong_fk");
				this.loaidoituongNhan = rs.getString("loaidoituongNhan") == null ? "" : rs.getString("loaidoituongNhan");
				this.doituongNhanId = rs.getString("doituongNhan_fk") == null ? "" : rs.getString("doituongNhan_fk");

				this.IsChuyenHangSX=rs.getString("ISCHUYENHANGSX");
				this.IsChuyenhangkhongdat=rs.getString("TRANGTHAISP");
				this.kyhieu = rs.getString("KYHIEU");
				this.sochungtu = rs.getString("SOCHUNGTU");
				this.lenhdieudong = rs.getString("LENHDIEUDONG");
				this.ngaydieudong = rs.getString("NGAYDIEUDONG");
				this.nguoidieudong = rs.getString("NGUOIDIEUDONG");
				this.veviec = rs.getString("VEVIEC");
				this.nguoivanchuyen = rs.getString("NGUOIVANCHUYEN");
				this.phuongtien = rs.getString("PHUONGTIEN");
				this.hopdong = rs.getString("HOPDONG");
				
				this.chiphiId = rs.getString("chiphi_fk") == null ? "" : rs.getString("chiphi_fk");
				this.lsxIds=rs.getString("lsxid");
				
				this.muahang_fk = rs.getString("muahang_fk") == null ? "" : rs.getString("muahang_fk");
/*				this.ngayxuatthucte=rs.getString("Ngayxuatthucte")==""?this.ngayyeucau:rs.getString("Ngayxuatthucte"); */
				this.ngayxuatthucte=rs.getString("Ngayxuatthucte");
				if(this.ngayxuatthucte.trim().length()<=0){
					this.ngayxuatthucte=this.ngayyeucau;
				}
				this.ngaychotCK = rs.getString("NGAYCHOTCK");
				System.out.println(" ngay xuat thuc te: "+rs.getString("Ngayxuatthucte"));
				
				this.ngaytao= rs.getString("ngaytao");
				this.CdsxId=rs.getString("congdoan_fk");
				
				
			}
			rs.close();
			
			this.init_sp_soluong("");
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		this.createChuyenKho_SanPham();
		this.createRs();
			
	}

	public void init_sp_soluong(String ngayyeucau_thaydoi) {
		// TODO Auto-generated method stub
		try{
			String query="";
			if(ngayyeucau_thaydoi==null ||ngayyeucau_thaydoi.equals("")){
				ngayyeucau_thaydoi =this.ngayyeucau;
			}
			//INIT SO LUONG CHI TIET
			if( this.isnhanHang.equals("0") )
			{
				query = "select  a.sanpham_fk ,(select MA from ERP_SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, ngayhethan, ngaynhapkho, a.MAME, a.MATHUNG, a.MAPHIEU, marq, hamluong, hamam, soluong, ' ' as scheme, " +
						"\n 	isnull( ( select MA from ERP_BIN where pk_seq = a.bin_fk ) , '' ) as vitriTEN, "+
						"\n 	isnull( ( select MA from ERP_BIN where pk_seq = a.bin_fk ) , '' ) as vitriMA, "+
						"\n 	isnull(a.phieudt, '') as phieudt, isnull(a.phieueo, '') as phieueo,	" +
						"\n 	isnull( (select isnull(MA + ' - ' + Ten, '') as NSXMA from ERP_NHASANXUAT where PK_SEQ = a.NSX_FK), '') as NSXMA, isnull(a.NSX_FK, 0) as NSX_FK    " +
						"\n 	from ERP_CHUYENKHO_SANPHAM_CHITIET a where chuyenkho_fk = '" + this.id + "' and ngaynhapkho<='"+ngayyeucau_thaydoi+"'  "
						 + "   	order by A.SOLO,A.MAPHIEU,  dbo.ftConvertToNumber(a.MAME) ,  " +
						 "  dbo.ftConvertToNumber(A.MATHUNG)  , a.pk_seq asc ";
			}
			else
			{
				 
				
				query = "select a.pk_seq, (select MA from ERP_SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, ngayhethan, ngaynhapkho, a.MAME, a.MATHUNG, a.MAPHIEU, marq, hamluong, hamam, soluong, ' ' as scheme, " +
						"	isnull( ( select ma from ERP_BIN where pk_seq = a.binnhan_fk ), '' ) as vitriMA,	" +
						"	isnull( ( select ma + ', ' + ten + ' [' + cast(pk_seq as varchar(10)) + ']' from ERP_BIN where pk_seq = a.binnhan_fk ), '' ) as vitriTEN,	" +
						" 	isnull(a.phieudt, '') as phieudt, isnull(a.phieueo, '') as phieueo,	" +
						"	isnull( (select isnull(MA +' - ' + Ten, '') as NSXMA from ERP_NHASANXUAT where PK_SEQ = a.NSX_FK), '') as NSXMA, isnull(a.NSX_FK, 0) as NSX_FK    " +
						"from ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG a " + 
						"where chuyenkho_fk = '" + this.id + "' " +
								" order by A.SOLO,A.MAPHIEU,  dbo.ftConvertToNumber(a.MAME) ,  " +
						 "  dbo.ftConvertToNumber(A.MATHUNG)  , a.pk_seq asc ";
			}
			
			System.out.println("---INIT SP CHI TIET: " + query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
				LinkedHashMap<String, String> sp_soluong_ordered = new LinkedHashMap<String, String>();
				while(rs.next())
				{
					String key = "";
					
					if( this.isnhanHang.equals("0"))
					{
						key = rs.getString("spMA") + "__ " + "__" + rs.getString("solo").trim()+ "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho") 
							+ "__" + rs.getString("MAME") + "__" + rs.getString("MATHUNG") + "__" + rs.getString("vitriTEN")
							+ "__" + rs.getString("MAPHIEU").trim() + "__" + rs.getString("phieudt").trim() + "__" + rs.getString("phieueo").trim()
							+ "__" + rs.getString("marq").trim() + "__" + rs.getString("hamluong") + "__" + rs.getString("hamam")
							+ "__" + rs.getString("sanpham_fk") + "__" + rs.getString("vitriMA")
							+ "__" + rs.getString("NSXMA") + "__" + rs.getString("NSX_FK");
					}
					else
					{
						key = rs.getString("spMA") + "__ " + "__" + rs.getString("solo").trim()+ "__" + rs.getString("ngayhethan") + "__" + rs.getString("ngaynhapkho") 
								+ "__" + rs.getString("MAME") + "__" + rs.getString("MATHUNG") + "__" + rs.getString("vitriTEN")
								+ "__" + rs.getString("MAPHIEU").trim() + "__" + rs.getString("phieudt").trim() + "__" + rs.getString("phieueo").trim()
								+ "__" + rs.getString("marq").trim() + "__" + rs.getString("hamluong") + "__" + rs.getString("hamam") 
								+ "__" + rs.getString("pk_seq") + "__" + rs.getString("vitriMA")
								+ "__" + rs.getString("NSXMA") + "__" + rs.getString("NSX_FK");
					}
					
					System.out.println("---KEY BEAN: " + key );
					sp_soluong.put(key, rs.getString("soluong") );
					sp_soluong_ordered.put(key, rs.getString("soluong") );
					Listkey.add(key);
				}
				rs.close();
				
				this.sanpham_soluong = sp_soluong;
				this.sanpham_soluong_ordered = sp_soluong_ordered;
				this.sanpham_soluongDAXUAT = sp_soluong;
			}
			
			
		}catch(Exception er){
			
		}
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			if(lsxRs!=null){
				lsxRs.close();
			}
			if(spList!=null){
				spList.clear();
			}
			if(spChoNhapList!=null){
				spChoNhapList.clear();
			}
			if(khuList!=null){
				khuList.clear();
			}
			
			if(vitriList!=null){
				vitriList.clear();
			}
			
			if(khoXuatRs!=null){
				khoXuatRs.close();
			}
			this.db.shutDown();
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
	public String getLsxIds()
	{
		return this.lsxIds;
	}

	public void setLsxIds(String lsxIds) 
	{
		this.lsxIds = lsxIds;
	}

	public ResultSet getLsxRs()
	{
		return this.lsxRs;
	}

	public void setLsxRs(ResultSet lsxRs) 
	{
		this.lsxRs = lsxRs;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getIsnhanHang() 
	{
		return this.isnhanHang;
	}

	public void setIsnhanHang(String isnhanHang)
	{
		this.isnhanHang = isnhanHang;
	}

	
	public List<IKhu_Vitri> getKhuList()
	{
		return this.khuList;
	}

	public void setKhuList(List<IKhu_Vitri> khuList) 
	{
		this.khuList = khuList;
	}

	public List<IKhu_Vitri> getVitriList() 
	{
		return this.vitriList;
	}

	public void setVitriList(List<IKhu_Vitri> vitriList)
	{
		this.vitriList = vitriList;
	}

	public List<IYeucau> getSpChoNhapList()
	{
		return this.spChoNhapList;
	}

	public void setSpChoNhapList(List<IYeucau> spchoNhapList) 
	{
		this.spChoNhapList = spchoNhapList;
	}


	public void initYeucauNLPdf() 
	{
		String query =  "select a.SANPHAM_FK, b.MA AS MA, b.TEN, a.SOLUONGYEUCAU as SoLuong " +
						"from ERP_YEUCAUNGUYENLIEU_SANPHAM a " +
						"inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ where yeucaunguyenlieu_fk = '" + this.id + "'";
		
		System.out.println("1.Query khoi tao sp: " + query);
		ResultSet rs = db.get(query);
		
		if(rs != null)
		{
			List<IYeucau> spList = new ArrayList<IYeucau>();
			
			try 
			{
				IYeucau yeucau;

				while(rs.next())
				{
					String spId = rs.getString("SANPHAM_FK");
					String spMa = rs.getString("MA");
					String spTen = rs.getString("TEN");
					String soluong = rs.getString("SOLUONG");

					yeucau = new Yeucau();
					yeucau.setId(spId);
					yeucau.setMa(spMa);
					yeucau.setTen(spTen);
					yeucau.setSoluongYC(soluong);
			
					query = " select b.MA AS MA, b.TEN, c.MA AS VITRI, d.DIENGIAI AS KHUTEN, e.DONVI as DONVIDOLUONG, nhapkho.solo, nhapkho.tongNhap, f.ma as khoTen  " +
						"from " +
						"( " +
							"select sanpham_fk, khott_fk, solo, bean, SUM(soluong) as tongNhap  " +
							"from ERP_YEUCAUNGUYENLIEU_SP_NHAPKHO   " +
							"where yeucaunguyenlieu_fk = '" + this.id + "' and sanpham_fk = '" + spId + "'  " +
							"group by sanpham_fk, khott_fk, solo, bean  " +
						")  " +
						"nhapkho inner Join ERP_SanPham b on nhapkho.sanpham_fk = b.PK_SEQ " +
						"INNER JOIN ERP_VITRIKHO c ON nhapkho.BEAN = c.PK_SEQ  " +
						"inner join ERP_KHUVUCKHO d on c.KHU_FK = d.PK_SEQ  " +
						"inner join DONVIDOLUONG e on b.DVDL_FK = e.PK_SEQ inner join ERP_KHOTT f on d.khott_fk = f.pk_seq ";
					
					System.out.println("1.San pham lay hang Detail: " + query);
					ResultSet rsSpDetail = db.get(query);
					
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					
					boolean flag = false;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							String idhangmua = rsSpDetail.getString("DONVIDOLUONG"); //luu donvidoluong
							String solo = rsSpDetail.getString("solo");
							
							int conlai = Integer.parseInt(soluong) - rsSpDetail.getInt("tongNhap");
							String slg = soluong + " - " + rsSpDetail.getString("tongNhap") + " - " + Integer.toString(conlai);
							String khu = rsSpDetail.getString("KHUTEN");
							String vitri = rsSpDetail.getString("VITRI");
							String vitriId = rsSpDetail.getString("khoTen");
							
							spCon = new SpDetail(idhangmua, solo, slg, khu, vitri, vitriId);
							spConList.add(spCon);
							
							flag = true;
						}
						rsSpDetail.close();
					}
					
					if(!flag)
					{
						String slg = soluong + " - 0 - " + soluong;
						spCon = new SpDetail(" ", " ", slg, " ", " ", " ");
						spConList.add(spCon);
					}
					
					yeucau.setSpDetailList(spConList);	
					spList.add(yeucau);
				}
			
				rs.close();
			} 
			catch (Exception e) 
			{ 
				e.printStackTrace();
			}
			
			this.spList = spList;
		}
	}

	public void initChuyenNLPdf() 
	{
		String query =  "select a.SANPHAM_FK, b.MA AS MA, b.TEN, a.SOLUONGYEUCAU as SoLuong " +
				"from ERP_YEUCAUNGUYENLIEU_SANPHAM a " +
				"inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ where yeucaunguyenlieu_fk = '" + this.id + "'";
		
		System.out.println("1.Query khoi tao sp: " + query);
		ResultSet rs = db.get(query);
		
		if(rs != null)
		{
		List<IYeucau> spList = new ArrayList<IYeucau>();
		
		try 
		{
		IYeucau yeucau;
		
		while(rs.next())
		{
			String spId = rs.getString("SANPHAM_FK");
			String spMa = rs.getString("MA");
			String spTen = rs.getString("TEN");
			String soluong = rs.getString("SOLUONG");
		
			yeucau = new Yeucau();
			yeucau.setId(spId);
			yeucau.setMa(spMa);
			yeucau.setTen(spTen);
			yeucau.setSoluongYC(soluong);
		
			query = " select b.MA AS MA, b.TEN, c.MA AS VITRI, d.DIENGIAI AS KHUTEN, e.DONVI as DONVIDOLUONG, nhapkho.solo, nhapkho.tongNhap, f.ma as khoTen  " +
				"from " +
				"( " +
					"select sanpham_fk, khott_fk, solo, bean, SUM(soluong) as tongNhap  " +
					"from ERP_YEUCAUNGUYENLIEU_SP_XUATKHO   " +
					"where yeucaunguyenlieu_fk = '" + this.id + "' and sanpham_fk = '" + spId + "'  " +
					"group by sanpham_fk, khott_fk, solo, bean  " +
				")  " +
				"nhapkho inner Join ERP_SanPham b on nhapkho.sanpham_fk = b.PK_SEQ " +
				"INNER JOIN ERP_VITRIKHO c ON nhapkho.BEAN = c.PK_SEQ  " +
				"inner join ERP_KHUVUCKHO d on c.KHU_FK = d.PK_SEQ  " +
				"inner join DONVIDOLUONG e on b.DVDL_FK = e.PK_SEQ inner join ERP_KHOTT f on d.khott_fk = f.pk_seq ";
			
			System.out.println("1.San pham lay hang Detail: " + query);
			ResultSet rsSpDetail = db.get(query);
			
			List<ISpDetail> spConList = new ArrayList<ISpDetail>();
			ISpDetail spCon = null;
			
			boolean flag = false;
			if(rsSpDetail != null)
			{
				while(rsSpDetail.next())
				{
					String idhangmua = rsSpDetail.getString("DONVIDOLUONG"); //luu donvidoluong
					String solo = rsSpDetail.getString("solo");
					
					int conlai = Integer.parseInt(soluong) - rsSpDetail.getInt("tongNhap");
					String slg = soluong + " - " + rsSpDetail.getString("tongNhap") + " - " + Integer.toString(conlai);
					String khu = rsSpDetail.getString("KHUTEN");
					String vitri = rsSpDetail.getString("VITRI");
					String vitriId = rsSpDetail.getString("khoTen");
					
					spCon = new SpDetail(idhangmua, solo, slg, khu, vitri, vitriId);
					spConList.add(spCon);
					
					flag = true;
				}
				rsSpDetail.close();
			}
			
			if(!flag)
			{
				String slg = soluong + " - 0 - " + soluong;
				spCon = new SpDetail(" ", " ", slg, " ", " ", " ");
				spConList.add(spCon);
			}
			
			yeucau.setSpDetailList(spConList);	
			spList.add(yeucau);
		}
		
		rs.close();
		} 
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
		
		this.spList = spList;
		}
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}


	public void initNhanhang() 
	{
		String query = "select noidungxuat_fk, ngaychuyen, lydo, khoxuat_fk, khonhan_fk, trangthai, isnull(trangthaisp, 0) as trangthaisp, NCC_CHUYEN_FK, NCC_NHAN_FK " +
						"from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ndxId = rs.getString("noidungxuat_fk");
					this.ngayyeucau = rs.getString("ngaychuyen");
					this.lydoyeucau = rs.getString("lydo");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk") == null ? "" : rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
					this.trangthaisp = rs.getString("trangthaisp");
					
					this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
					this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
				
	 
		
		if( this.spList.size() <= 0 )
		{
			this.createChuyenKho_SanPham_NhanHang();
		}
		createRs();
		
		
	}
	
	public void initXuathang() 
	{
		String query = "select noidungxuat_fk, ngaychuyen, lydo, khoxuat_fk, khonhan_fk, trangthai, isnull(trangthaisp, 0) as trangthaisp, NCC_CHUYEN_FK, NCC_NHAN_FK " +
						"from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ndxId = rs.getString("noidungxuat_fk");
					this.ngayyeucau = rs.getString("ngaychuyen");
					this.lydoyeucau = rs.getString("lydo");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk") == null ? "" : rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
					this.trangthaisp = rs.getString("trangthaisp");
					
					this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
					this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	 
		if( this.spList.size() <= 0 )
		{
			this.createChuyenKho_SanPham_XuatHang();
		}
		
		createRs();
		
		
	}

	private void createChuyenKho_SanPham_NhanHang() 
	{
		String query = " select b.PK_SEQ as spId, b.MA as spMa,  b.Ten   as spTen, dvdl.donvi as spDvdl, a.SOLO, SUM(a.SOLUONGXUAT) as tongXuat, isnull(Sum(a.SOLUONGNHAN), 0) as tongNhan  " +
				" from ERP_CHUYENKHO_SANPHAM a " +
				" inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ  " +
				" left join donvidoluong dvdl on dvdl.pk_seq = b.dvdl_fk " +
				" where a.CHUYENKHO_FK = '" + this.id + "'  " +
				" group by b.PK_SEQ, b.MA, b.Ten,   dvdl.donvi, a.SOLO  " +
				" having SUM(a.SOLUONGXUAT) > 0 ";
		
		System.out.println("1.Khoi tao SP: " + query);
		ResultSet spRs = db.get(query);
		
		List<IYeucau> spList = new ArrayList<IYeucau>();
		
		if(spRs != null)
		{
			try 
			{
				IYeucau sp = null;
				while(spRs.next())
				{
					String spId = spRs.getString("spId");
					String spMa = spRs.getString("spMa");
					String spTen = spRs.getString("spTen");
					String solo = spRs.getString("solo");
					
					sp = new Yeucau();
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setTen(spTen);
					sp.setSolo(solo);
					sp.setDonViTinh(spRs.getString("spDvdl"));
					
					if(spRs.getString("tongXuat").trim().length() > 0)
						sp.setSoluongYC(spRs.getString("tongXuat"));
					
					if(spRs.getString("tongNhan").trim().length() > 0)
						sp.setSoluongNhan(spRs.getString("tongNhan"));
					
					
					//Create kho nhan
					query = "select vitri, soluong, khu from ERP_CHUYENKHO_SP_NHANHANG   " +
							"where chuyenkho_fk = '" + this.id + "' and sanpham_fk = '" + spId + "' and solo = '" + solo + "'";
					
					System.out.println("__Khoi tao kho nhan: " + query);
					
					ResultSet rsSpDetail = db.get(query);
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							String slg = rsSpDetail.getString("soluong");
							String khu = rsSpDetail.getString("khu");
							String vitriId = khu + " - " + rsSpDetail.getString("vitri");
							
							spCon = new SpDetail();
							spCon.setSoluong(slg);
							spCon.setKhu(khu);
							spCon.setVitriId(vitriId);
							
							spConList.add(spCon);
						}
						rsSpDetail.close();
					}
					
					sp.setSpDetailList(spConList);	
					
					spList.add(sp);
				}
				spRs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			this.spList = spList;
		}
	}

	private void createChuyenKho_SanPham_XuatHang() 
	{
		String query = " select b.PK_SEQ as spId, b.MA as spMa,  b.Ten   as spTen, dvdl.donvi as spDvdl, a.SOLO, SUM(a.SOLUONGXUAT) as tongXuat, isnull(Sum(a.SOLUONGNHAN), 0) as tongNhan  " +
				" from ERP_CHUYENKHO_SANPHAM a " +
				" inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ  " +
				" left join donvidoluong dvdl on dvdl.pk_seq = b.dvdl_fk " +
				" where a.CHUYENKHO_FK = '" + this.id + "'  " +
				" group by b.PK_SEQ, b.MA, b.Ten , dvdl.donvi, a.SOLO  " +
				" having SUM(a.SOLUONGXUAT) > 0 ";
		
		System.out.println("1.Khoi tao SP: " + query);
		ResultSet spRs = db.get(query);
		
		List<IYeucau> spList = new ArrayList<IYeucau>();
		
		if(spRs != null)
		{
			try 
			{
				IYeucau sp = null;
				while(spRs.next())
				{
					String spId = spRs.getString("spId");
					String spMa = spRs.getString("spMa");
					String spTen = spRs.getString("spTen");
					String solo = spRs.getString("solo");
					
					sp = new Yeucau();
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setTen(spTen);
					sp.setSolo(solo);
					sp.setDonViTinh(spRs.getString("spDvdl"));
					
					if(spRs.getString("tongXuat").trim().length() > 0)
						sp.setSoluongYC(spRs.getString("tongXuat"));
					
					if(spRs.getString("tongNhan").trim().length() > 0)
						sp.setSoluongNhan(spRs.getString("tongNhan"));
					
					
					//Create kho nhan
					query = "select vitri, soluong, khu from ERP_CHUYENKHO_SP_XUATHANG   " +
							"where chuyenkho_fk = '" + this.id + "' and sanpham_fk = '" + spId + "' and solo = '" + solo + "'";
					
					System.out.println("__Khoi tao kho xuat: " + query);
					
					ResultSet rsSpDetail = db.get(query);
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							String slg = rsSpDetail.getString("soluong");
							String khu = rsSpDetail.getString("khu");
							String vitriId = khu + " - " + rsSpDetail.getString("vitri");
							
							spCon = new SpDetail();
							spCon.setSoluong(slg);
							spCon.setKhu(khu);
							spCon.setVitriId(vitriId);
							
							spConList.add(spCon);
						}
						rsSpDetail.close();
					}
					
					sp.setSpDetailList(spConList);	
					
					spList.add(sp);
				}
				spRs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			this.spList = spList;
		}
	}

	public void initView() 
	{
		String query = "select ISNULL(TRANGTHAISP,1) AS TRANGTHAISP ,isnull(ISCHUYENHANGSX,'0') as ISCHUYENHANGSX ,isnull(NGUOINHAN,'') as NGUOINHAN , isnull(Lenhsanxuat_fk,0 ) as lsxid ,noidungxuat_fk, NGAYYEUCAU, lydo, isnull(ghichu, '') as ghichu, khoxuat_fk, khonhan_fk, trangthai,   NCC_CHUYEN_FK, NCC_NHAN_FK, " +
				" ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, "+
				" ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC, ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG "+
					"from ERP_YEUCAUCHUYENKHO where pk_seq = '" + this.id + "'";
		System.out.println("INIT YEUCAUCHUYENKHO: "+query);
		ResultSet rs = db.get(query);
		 
			try 
			{
				if(rs.next())
				{
					this.lsxIds=rs.getString("lsxid");
					this.ndxId = rs.getString("noidungxuat_fk");
					this.ngayyeucau = rs.getString("NGAYYEUCAU");
					this.lydoyeucau = rs.getString("lydo");
					this.Nguoinhan = rs.getString("nguoinhan");
					this.ghichu = rs.getString("ghichu");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
					this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
					this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
					this.IsChuyenHangSX=rs.getString("ISCHUYENHANGSX");
					this.IsChuyenhangkhongdat=rs.getString("TRANGTHAISP");
					this.kyhieu = rs.getString("KYHIEU");
					this.sochungtu = rs.getString("SOCHUNGTU");
					this.lenhdieudong = rs.getString("LENHDIEUDONG");
					this.ngaydieudong = rs.getString("NGAYDIEUDONG");
					this.nguoidieudong = rs.getString("NGUOIDIEUDONG");
					this.veviec = rs.getString("VEVIEC");
					this.nguoivanchuyen = rs.getString("NGUOIVANCHUYEN");
					this.phuongtien = rs.getString("PHUONGTIEN");
					this.hopdong = rs.getString("HOPDONG");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		
		if( this.spList.size() <= 0 )
		{
			System.out.println(" Size : "+this.spList.size());
			
			this.createChuyenKho_SanPham_View();
		}
		 createRs();
		 
	}

//-----------------------------------------PDF-----------------------------------------------//
	public void initPdf() 
	{
		String query = 
			" select ndn.ten as ndx, isnull(ck.ngaychuyen, '') as ngaychuyen, isnull(ck.lydo, '') as lydo, " +
			"     ck.khoxuat_fk as khoxuat_fk, isnull(kx.ten, '') as khoxuat, ck.khonhan_fk as khonhan_fk, isnull(kn.ten, '') as khonhan, " +
			"     isnull(nccnhan.ten, '') as nccnhan, isnull(nccchuyen.ten, '') as nccchuyen" +
			" from ERP_CHUYENKHO ck " +
			" left join ERP_NOIDUNGNHAP ndn on ndn.pk_seq = ck.noidungxuat_fk " +
			" left join erp_khott kx on kx.pk_seq = ck.khoxuat_fk " +
			" left join erp_khott kn on kn.pk_seq = ck.khonhan_fk " +
			" left join erp_nhacungcap nccnhan on nccnhan.pk_seq = ck.ncc_nhan_fk " +
			" left join erp_nhacungcap nccchuyen on nccchuyen.pk_seq = ck.ncc_chuyen_fk " +
			" where ck.pk_seq = '" + this.id + "'";
		System.out.println("[ErpChuyenkho] query = " + query);
		ResultSet rs = db.get(query);
		String khoxuat_fk = ""; 
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ndxId = rs.getString("ndx");
					this.lydoyeucau = rs.getString("lydo");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk");
					this.khoXuatTen = rs.getString("khoxuat");
					this.khoNhanTen = rs.getString("khonhan");
					this.nccNhanId = rs.getString("nccnhan");
					this.nccXuatId = rs.getString("nccchuyen");
					khoxuat_fk = rs.getString("khoxuat_fk");
					
					//Ngày yêu cầu
					try {
						String[] nyc = rs.getString("ngaychuyen").split("-");
						this.ngayyeucau = nyc[2]+"/"+nyc[1]+"/"+nyc[0];
					} catch(Exception e) { 
						e.printStackTrace();
						this.ngayyeucau = rs.getString("ngaychuyen");
					}
				}
				rs.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(khoxuat_fk == null) khoxuat_fk = "";
		
		//Lấy sản phẩm
		if(khoxuat_fk.trim().equals("100016") || khoxuat_fk.trim().equals("100010") || khoxuat_fk.trim().equals("100011") ) 
		{
			//Kho xuất là kho vật tư (Spide Part) - 100016
			//Kho nhờ gia công, kho nhận gia công - 100010, 100011
			this.spList = layDanhSachSanPhamVatTuGiaCong();
		}
		else 
		{
			//Lấy sản phẩm phụ liệu
			String lspStr = "100015,100016,100017"; //loại sản phẩm phụ liệu
			this.spChoNhapList = this.layDanhSachSanPhamPhuLieu(lspStr, false);
			
			//Lấy các sản phẩm khác phụ liệu
			this.spList = this.layDanhSachSanPhamKhac(lspStr, true);
			
			if(this.spList.size() > 0) {
				int sospthieu = 3 - this.spList.size() % 3;
				for(int i = 0; i < sospthieu; i++) {
					this.spList.add(new Yeucau());
				}
			}
		}
		
	}
	
	/**
	 * Lấy danh sách sản phẩm của chuyển kho "this.id" và kho là kho vật tư (spide part) hoặc kho gia công
	 * @return List<IYeucau>
	 */
	private List<IYeucau> layDanhSachSanPhamVatTuGiaCong() {
		//Kho xuất là kho vật tư (Spide Part) - 100016
		//Kho nhờ gia công, kho nhận gia công - 100010, 100011
		String query = " SELECT B.PK_SEQ as SPID, B.MA, B.MA, B.TEN as SPTEN, ISNULL(C.DONVI, '') AS DVDL, B.DAI, B.DVDL_DAI, B.RONG, B.DVDL_RONG, B.DINHLUONG, B.DVDL_DINHLUONG, A.SOLUONGXUAT " + 
				" FROM ERP_CHUYENKHO_SANPHAM A " + 
				" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ " +
				" LEFT JOIN DONVIDOLUONG C ON B.DVDL_FK = C.PK_SEQ " +
				" WHERE A.CHUYENKHO_FK = " + this.id + " AND A.SOLUONGXUAT > 0 ";
		System.out.println("[ErpChuyenkho] query = " + query);
		ResultSet spRs = db.get(query);
		
		List<IYeucau> spList = new ArrayList<IYeucau>();
		
		if(spRs != null)
		{
			try 
			{
				IYeucau sp = null;
				while(spRs.next())
				{
					sp = new Yeucau();
					sp.setId(spRs.getString("SPID"));
					sp.setMa(spRs.getString("MA"));
					sp.setTen(spRs.getString("SPTEN"));
					sp.setDonViTinh(spRs.getString("DVDL"));
					
					if(spRs.getString("SOLUONGXUAT").trim().length() > 0)
						sp.setSoluongYC(spRs.getString("SOLUONGXUAT"));
					
					double dai = 0, rong = 0, dinhluong = 0;
					try { dai = Double.parseDouble(spRs.getString("DAI")); } catch(Exception e) {e.printStackTrace(); }
					try { rong = Double.parseDouble(spRs.getString("RONG")); } catch(Exception e) {e.printStackTrace(); }
					try { dinhluong = Double.parseDouble(spRs.getString("DINHLUONG")); } catch(Exception e) {e.printStackTrace(); }
					String quycach = "";
					if(dai > 0) {
						quycach = dai + " " + spRs.getString("DVDL_DAI");
					}
					if(rong > 0) {
						if(quycach.length() > 0) { quycach += " x "; }
						quycach += rong + " " + spRs.getString("DVDL_RONG");
					}
					if(dinhluong > 0) {
						if(quycach.length() > 0) { quycach += " x "; }
						quycach += dinhluong + " " + spRs.getString("DVDL_DINHLUONG");
					}
					sp.setSolo(quycach);
					
					spList.add(sp);
				}
				spRs.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("[ErpChuyenkho.layDanhSachSanPhamPhuLieu] Exception: " + e.getMessage());
			}
		}
		
		return spList;
	}
	
	/**
	 * Lấy danh sách sản phẩm của chuyển kho "this.id" và có loại sản phẩm nằm trong "loaisanphamStr"
	 * @param loaisanphamStr String chuỗi loại sản phẩm cách nhau bởi dấu ,
	 * @param notIn boolean nếu notIn = true thì lấy những sản phẩm không nằm trong chuỗi loaisanphamStr
	 * @return List<IYeucau>
	 */
	private List<IYeucau> layDanhSachSanPhamPhuLieu(String loaisanphamStr, boolean notIn) {
		ResultSet spRs;
		
		String lsp = loaisanphamStr == null || loaisanphamStr.trim().length() <= 0 ? "null" : loaisanphamStr.trim();
		String not = notIn ? " not " : "";
		
		String 
		query = " SELECT B.PK_SEQ as SPID, B.MA as SPMA, B.MA, B.TEN as SPTEN, ISNULL(C.DONVI, '') AS DVDL, B.DAI, B.DVDL_DAI, B.RONG, B.DVDL_RONG, B.DINHLUONG, B.DVDL_DINHLUONG, A.SOLUONGXUAT " + 
				" FROM ERP_CHUYENKHO_SANPHAM A " + 
				" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ " +
				" left join ERP_LOAISANPHAM lsp on b.LOAISANPHAM_FK = lsp.PK_SEQ " +
				" LEFT JOIN DONVIDOLUONG C ON B.DVDL_FK = C.PK_SEQ " +
				" WHERE A.CHUYENKHO_FK = " + this.id + " AND A.SOLUONGXUAT > 0 and B.LOAISANPHAM_FK " + not + " in ( " + lsp + " ) ";
		System.out.println("[ErpChuyenkho] query = " + query);
		spRs = db.get(query);
		List<IYeucau> spList = new ArrayList<IYeucau>();
		try 
		{
			IYeucau sp = null;
			while(spRs.next())
			{
				sp = new Yeucau();
				//sp.setId(spRs.getString("spId"));
				sp.setMa(spRs.getString("MA"));
				sp.setTen(spRs.getString("SPTEN"));
				sp.setDonViTinh(spRs.getString("DVDL"));
				
				double dai = 0, rong = 0, dinhluong = 0;
				try { dai = Double.parseDouble(spRs.getString("DAI")); } catch(Exception e) {e.printStackTrace(); }
				try { rong = Double.parseDouble(spRs.getString("RONG")); } catch(Exception e) {e.printStackTrace(); }
				try { dinhluong = Double.parseDouble(spRs.getString("DINHLUONG")); } catch(Exception e) {e.printStackTrace(); }
				String quycach = "";
				if(dai > 0) {
					quycach = dai + " " + spRs.getString("DVDL_DAI");
				}
				if(rong > 0) {
					if(quycach.length() > 0) { quycach += " x "; }
					quycach += rong + " " + spRs.getString("DVDL_RONG");
				}
				if(dinhluong > 0) {
					if(quycach.length() > 0) { quycach += " x "; }
					quycach += dinhluong + " " + spRs.getString("DVDL_DINHLUONG");
				}
				sp.setSolo(quycach);
				
				if(spRs.getString("SOLUONGXUAT").trim().length() > 0)
					sp.setSoluongYC(spRs.getString("SOLUONGXUAT"));
				
				spList.add(sp);
			}
			spRs.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("1.Exception: " + e.getMessage());
		}

		return spList;
	}
	
	/**
	 * Lấy danh sách sản phẩm của chuyển kho "this.id" và có loại sản phẩm nằm trong "loaisanphamStr" (chi tiết sp nhóm theo mã chi tiết và định lượng)
	 * @param loaisanphamStr String chuỗi loại sản phẩm cách nhau bởi dấu ,
	 * @param notIn boolean nếu notIn = true thì lấy những sản phẩm không nằm trong chuỗi loaisanphamStr
	 * @return List<IYeucau>
	 */
	private List<IYeucau> layDanhSachSanPhamKhac(String loaisanphamStr, boolean notIn) {
		ResultSet spRs, spCtRs;
		
		String lsp = loaisanphamStr == null || loaisanphamStr.trim().length() <= 0 ? "null" : loaisanphamStr.trim();
		String not = notIn ? " not " : "";
		
		String 
		query = " select b.MA as spMa, b.Ten as spTen,  SUM(a.SOLUONGXUAT) as tongXuat, isnull(Sum(a.SOLUONGNHAN), 0) as tongNhan, c.ten as lspTen  " +
				" from ERP_CHUYENKHO_SANPHAM a " +
				" inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ  " +
				" left join ERP_LOAISANPHAM c on b.LOAISANPHAM_FK = C.PK_SEQ " +
				" where a.CHUYENKHO_FK = '" + this.id + "' and B.LOAISANPHAM_FK " + not + " in ( " + lsp + " ) " +
				" group by b.MA, b.TEN, c.ten  " +
				" having SUM(a.SOLUONGXUAT) > 0 ";
		System.out.println("[ErpChuyenkho] query = " + query);
		spRs = db.get(query);
		List<IYeucau> spList = new ArrayList<IYeucau>();
		try 
		{
			IYeucau sp = null;
			ISpDetail spct = null;
			while(spRs.next())
			{
				sp = new Yeucau();
				//sp.setId(spRs.getString("spId"));
				sp.setMa(spRs.getString("spMa"));
				sp.setTen(spRs.getString("spTen"));
				sp.setSolo(spRs.getString("lspTen"));
				
				if(spRs.getString("tongXuat").trim().length() > 0)
					sp.setSoluongYC(spRs.getString("tongXuat"));
				
				if(spRs.getString("tongNhan").trim().length() > 0)
					sp.setSoluongNhan(spRs.getString("tongNhan"));
				spList.add(sp);
				
				//Lấy chi tiết
				query = " SELECT B.MA, B.TEN, B.DINHLUONG, B.DVDL_DINHLUONG, SUM(A.SOLUONGXUAT) AS SOLUONGXUAT " + 
						" FROM ERP_CHUYENKHO_SANPHAM A " + 
						" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ   " + 
						" WHERE B.MA = N'" + sp.getMa() + "' AND A.SOLUONGXUAT > 0 AND A.CHUYENKHO_FK = " + this.id + " " + 
						" GROUP BY B.MA, B.TEN, B.DINHLUONG, B.DVDL_DINHLUONG " + 
						" HAVING SUM(A.SOLUONGXUAT) > 0 ";
				//System.out.println("[ErpChuyenkho] query = " + query);
				spCtRs = db.get(query);
				while(spCtRs.next())
				{
					spct = new SpDetail();
					spct.setMa(spCtRs.getString("MA"));
					spct.setTen(spCtRs.getString("TEN"));
					spct.setSoluong(spCtRs.getString("SOLUONGXUAT"));
					
					double dinhluong = 0;
					try { dinhluong = Double.parseDouble(spCtRs.getString("DINHLUONG")); } catch(Exception e) {e.printStackTrace(); }
					spct.setDvt(dinhluong > 0 ? dinhluong + " " + spCtRs.getString("DVDL_DINHLUONG") : "");
					spct.setVitri(dinhluong+"");
					
					sp.getSpDetailList().add(spct);
				}
				spCtRs.close();
			}
			spRs.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("1.Exception: " + e.getMessage());
		}

		return spList;
	}
//-----------------------------------------END PDF-----------------------------------------------//
	
	
	
	private void createChuyenKho_SanPham_View() 
	{
	 
		String query=" SELECT  ISNULL(a.ghichu_chuyenkho,'') as ghichu , B.PK_SEQ AS SPID, ISNULL(B.MA,B.MA) AS SPMA, B.TEN   AS SPTEN, DVDL.DONVI AS SPDVDL, SOLUONGYEUCAU, "+
					 " ( SELECT ISNULL(SUM (SOLUONGXUAT),0)  " +
					 "   FROM ERP_CHUYENKHO CK1  INNER JOIN ERP_CHUYENKHO_SANPHAM CKSP1 ON CK1.PK_SEQ=CKSP1.CHUYENKHO_FK  "+
					 "        AND CK1.YEUCAUCHUYENKHO_FK=CK.PK_SEQ AND CKSP1.SANPHAM_FK=A.SANPHAM_FK  and CK1.trangthai <>4  ) AS TONGSOLUONG," +
					 "  A.LENHSANXUAT_FK AS LSXID, kho.AVAILABLE as tonkhoxuat "+
					 " FROM ERP_YEUCAUCHUYENKHO_SANPHAM A "+
					 " INNER JOIN ERP_YEUCAUCHUYENKHO CK ON CK.PK_SEQ=A.YEUCAUCHUYENKHO_FK "+ 
					 " INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ   "+
					 " LEFT JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = B.DVDL_FK "+ 
					 " LEFT JOIN erp_khott_sanpham kho on CK.khoxuat_fk=kho.khott_fk and A.sanpham_fk=kho.sanpham_fk "+ 
					 " WHERE A.YEUCAUCHUYENKHO_FK =  '" + this.id + "' ";
		
		///System.out.println("221.Khoi tao SP: " + query);
		ResultSet spRs = db.get(query);
		
		List<IYeucau> spList = new ArrayList<IYeucau>();
		
		if(spRs != null)
		{
			try 
			{
				IYeucau sp = null;
				while(spRs.next())
				{ 
					String spId = spRs.getString("spId");
					String spMa = spRs.getString("spMa");
					String spTen = spRs.getString("spTen");
					sp = new Yeucau();
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setTen(spTen);
					sp.setSolo("");
					sp.setDonViTinh(spRs.getString("spDvdl"));
					sp.setSoluongYC(spRs.getString("soluongyeucau"));
					sp.setghichu_CK(spRs.getString("ghichu"));
					sp.setSoluongNhan(spRs.getString("tongsoluong"));
					sp.setSoluongTon(spRs.getString("tonkhoxuat"));
					sp.setLsxId(spRs.getString("lsxId")== null ? "":spRs.getString("lsxId"));
					spList.add(sp);
				}
				spRs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			this.spList = spList;
		}
	}

	public String getTrangthaiSP() 
	{
		return this.trangthaisp;
	}

	public void setTrangthaiSP(String trangthaisp) 
	{
		this.trangthaisp = trangthaisp;
	}
	
	public String getNdxId()
	{
		return this.ndxId;
	}

	public void setNdxId(String ndxId) 
	{
		this.ndxId = ndxId;
	}

	public ResultSet getNdxList()
	{
		return this.ndxRs;
	}

	public void setNdxList(ResultSet ndxList) 
	{
		this.ndxRs = ndxList;	
	}

	
	public String getNccChuyenIds() {
		
		return this.nccXuatId;
	}

	
	public void setNccChuyenIds(String nccChuyenIds) {
		
		this.nccXuatId = nccChuyenIds;
	}

	
	public ResultSet getNccChuyenRs() {
		
		return this.nccXuatRs;
	}

	
	public void setNccChuyenRs(ResultSet nccChuyenRs) {
		
		this.nccXuatRs = nccChuyenRs;
	}

	
	public String getNccNhanIds() {
		
		return this.nccNhanId;
	}

	
	public void setNccNhanIds(String nccNhanIds) {
		
		this.nccNhanId = nccNhanIds;
	}

	
	public ResultSet getNccNhanRs() {
		
		return this.nccNhanRs;
	}

	
	public void setNccNhanRs(ResultSet nccNhanRs) {
		
		this.nccNhanRs = nccNhanRs;
	}

	
	public String getKhoXuatTen() {
		return this.khoXuatTen;
	}

	
	public void setKhoXuatTen(String khoxuattt) {
		this.khoXuatTen = khoxuattt;
	}

	
	public String getKhoNhapTen() {
		return this.khoNhanTen;
	}

	
	public void setKhoNhapTen(String khonhaptt) {
		this.khoNhanTen = khonhaptt;
	}

	
	public String getGhichu() {
		
		return this.ghichu;
	}

	
	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	public ResultSet getTsddRs() 
	{
		return this.tsddRs;
	}

	public void setTsddRS(ResultSet tsddRs) 
	{
		this.tsddRs = tsddRs;
	}
	
	public boolean createChuyenKho_LSX() {
		
		try{
			this.db.getConnection().setAutoCommit(false);
			
			String query=" select khoxuat_fk, khonhan_fk from erp_phieuyeucau a  " +
						 " inner join ERP_PHIEUYEUCAU_LSX  b on a.pk_seq=b.phieuyeucau_fk " +
					     " where  b.lenhsanxuat_fk= "+this.lsxIds;
			
			ResultSet rs=db.get(query);
			
			if (rs.next()){
				    query = " insert ERP_YEUCAUCHUYENKHO(LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua) " +
				            " values("+this.lsxIds+",'" + this.ndxId + "', '" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" +rs.getString("khoxuat_fk") + "', " + rs.getString("khonhan_fk") + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
					
					
			}else{
				query=  " SELECT PK_SEQ as KHONHAN_FK ,KHONHANNGUYENLIEU_FK AS KHOXUAT_FK " +
						" FROM ERP_KHOTT WHERE PK_SEQ= (SELECT A.KHOSANXUAT_FK FROM ERP_LENHSANXUAT_GIAY " +
						" A WHERE A.PK_SEQ="+this.lsxIds+")";
				
				ResultSet rskho=db.get(query);
				rskho.next();
				 query = " insert ERP_YEUCAUCHUYENKHO(LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua) " +
		            " values("+this.lsxIds+",'" + this.ndxId + "', '" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" +rskho.getString("khoxuat_fk") + "', " + rskho.getString("khonhan_fk") + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";

				 rskho.close();
			}
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "select IDENT_CURRENT('ERP_YEUCAUCHUYENKHO') as ckId";
			ResultSet rsPxk = db.get(query);						
			rsPxk.next();
			this.id = rsPxk.getString("ckId");
			rsPxk.close();
			
			rs.close();
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			return false;
			
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
		
			return false;
		}
	}
	
	public boolean createChuyenKho_LSX_DANH() {
		
		try{
			this.db.getConnection().setAutoCommit(false);
		 
			String query =  " select   sp.ma+ ' ' +sp.ten as tensp, bom.VATTU_FK,bom.SOLUONG -ISNULL(kho.AVAILABLE,0)    as SOLUONGYC, ISNULL(kho.AVAILABLE,0) as AVAI" +
							" ,  bom.LENHSANXUAT_FK  from ERP_LENHSANXUAT_BOM_GIAY bom  " +
							" left join ERP_KHOTT_SANPHAM kho on kho.KHOTT_FK = bom.KHOTT_FK " +
							" and bom.VATTU_FK = kho.SANPHAM_FK INNER join ERP_SANPHAM sp on sp.PK_SEQ = bom.VATTU_FK " +
							" where ISNULL(kho.AVAILABLE,0) - bom.SOLUONG < 0  and  bom.LENHSANXUAT_FK = '"+this.lenhsxId+"' ";
			
				
			ResultSet rsspyc=db.getScrol(query);
			
				if(!rsspyc.next()){
					//chuyển trạng thái sang đã yêu cầu nguyên liệu
					query = "update ERP_LENHSANXUAT_GIAY set TRANGTHAI = 1 where PK_SEQ = "+this.lenhsxId;
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật trạng thái ERP_LENHSANXUAT_GIAY";
						db.getConnection().rollback();
						rsspyc.close();
						return false;
					}
					this.db.getConnection().commit();
					this.db.getConnection().setAutoCommit(true);
					rsspyc.close();
					return true;
				}
				//kiểm tra những hàng đang còn thiếu trong kho sản xuất thì mới yêu cầu chuyển thêm 
				query=  " SELECT A.KHOSANXUAT_FK FROM ERP_LENHSANXUAT_GIAY  A WHERE A.PK_SEQ="+this.lenhsxId;
				
				 ResultSet rskho=db.get(query);
				 if(rskho.next()){
					 this.khoNhanId=rskho.getString("KHOSANXUAT_FK");
				 }
				 rskho.close();
				 List<IYeucau>  listyc = new ArrayList<IYeucau>();
				 rsspyc.beforeFirst();
				 while (rsspyc.next()){
					 
					 String tensp=rsspyc.getString("tensp");
					 String sanpham_fk =rsspyc.getString("VATTU_FK");
					 double SOLUONGYC=rsspyc.getDouble("SOLUONGYC");
					 
					 query=" SELECT KHO.PK_SEQ AS KHOID ,KHO_SP.AVAILABLE as soluong "+
					  	   " FROM ERP_KHOTT_SANPHAM KHO_SP  "+
					  	   " INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=KHO_SP.KHOTT_FK "+
					  	   " WHERE KHO.PK_SEQ  IN ( SELECT KHOTT_NL_FK FROM ERP_KHOSX_KHONHANNL  WHERE KHOTT_SX_FK ="+this.khoNhanId +" )"+
					  	   " AND SANPHAM_FK ="+sanpham_fk+
					  	   "   order by  KHO_SP.AVAILABLE  desc"; 
					 ResultSet rstonkho=db.get(query);
					 
					 while(rstonkho.next() && SOLUONGYC >0 ) {
						 
						 
						 	double soluongyeucau=0;
						 	if(rstonkho.getDouble("soluong") >SOLUONGYC ){
						 		soluongyeucau=SOLUONGYC;
						 		SOLUONGYC=0;
						 	}else{
						 		soluongyeucau=rstonkho.getDouble("soluong");
						 		SOLUONGYC=SOLUONGYC - rstonkho.getDouble("soluong");
						 	}
						  
							IYeucau sp =new Yeucau();
							sp.setId(sanpham_fk);
							sp.setSoluongTon(soluongyeucau+"");
							sp.setKhoid(rstonkho.getString("KHOID"));
							listyc.add(sp);
							  
					 }
					 
					 rstonkho.close();
	 
					 if(SOLUONGYC >0){
						 	this.msg = "Vui lòng kiểm tra lại nguyên liệu : ["+tensp+"],không đủ tồn kho nguyên liệu để yêu cầu.";
							return false;
					 }
				 
				 }
				 
				 // sau khi kiểm tra nguyên liệu đủ thì tạo phiếu yêu cầu với các kho tương ứng.
				 
//				 		String khobk="";
				 		// lấy kho ra được 1 mảng 
				 		
				 		String mangkho[]=new String[listyc.size()];
				 		int k=0;
				 		for(int i=0;i< listyc.size();i++ )
						{
				 			
							IYeucau yc1 =	listyc.get(i);
							if(this.kiemtrachuacotrongmang(mangkho,yc1.getKhoid()) ){
								 
								mangkho[k]=yc1.getKhoid();
								k++;
							}
						}
				 		//tạo các yeu cầu theo kho lấy được
				 		for(int i=0;i< mangkho.length;i++ )
						{
				 			if(mangkho[i]!=null && mangkho[i].length() >0){
				 				this.TaoPhieuYeuCau(listyc,mangkho[i]);
				 			}
						}
				 			
				 	/*	//booked những sản phẩm nào đang có trong kho
						query=  " SELECT  BOM.SOTT, BOM.VATTU_FK, CASE WHEN  (BOM.SOLUONG  -ISNULL(BOM.BOOKED_LSX,0)) >  KHO.AVAILABLE THEN  KHO.AVAILABLE "+
								" ELSE (BOM.SOLUONG  -ISNULL(BOM.BOOKED_LSX,0)) END    SOLUONG,  "+
								" BOM.LENHSANXUAT_FK ,KHO.KHOTT_FK FROM ERP_LENHSANXUAT_BOM_GIAY BOM  "+ 
								" LEFT JOIN ERP_KHOTT_SANPHAM KHO ON KHO.KHOTT_FK = BOM.KHOTT_FK   "+
								" AND BOM.VATTU_FK = KHO.SANPHAM_FK INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BOM.VATTU_FK "+  
								" WHERE  ISNULL(KHO.AVAILABLE,0) > 0 AND BOM.LENHSANXUAT_FK ="+this.lenhsxId;
						  rskho=db.get(query);
						while (rskho.next()){
							String khoid=rskho.getString("KHOTT_FK");
							String spid=rskho.getString("VATTU_FK");
							double avai=(-1)*rskho.getDouble("SOLUONG");
							double booked=rskho.getDouble("SOLUONG");
							double soluong=0;
							String msg1 =util_kho.Update_Kho_Sp(db, khoid, spid, soluong, booked, avai, 0);
							if(msg1.length() >0){
								this.msg = msg1;
								db.getConnection().rollback();
								return false;
							}
							// cập nhật booked đối với những sản phẩm trong kho
							query=" update ERP_LENHSANXUAT_BOM_GIAY set BOOKED_LSX=isnull(BOOKED_LSX,0)+"+rskho.getDouble("SOLUONG")+" where sott="+rskho.getString("SOTT")+" and  vattu_fk="+spid+" and lenhsanxuat_fk= "+ this.lenhsxId;
							if(db.updateReturnInt(query) != 1)
							{
								this.msg = "Không thể cập nhật: "+query;
								db.getConnection().rollback();
								return false;
							}
						}
						rskho.close();*/
						
				 		
				query = "update ERP_LENHSANXUAT_GIAY set TRANGTHAI = 1 where PK_SEQ = "+this.lenhsxId;
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật trạng thái ERP_LENHSANXUAT_GIAY";
					db.getConnection().rollback();
					return false;
				}
			 
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			return true;
			
		}catch(Exception er){
			
			db.update("rollback");
			er.printStackTrace();
			this.msg=er.getMessage();
		
			return false;
		}
	}

	private boolean kiemtrachuacotrongmang(String[] mangkho, String khoid) {
		
		for(int i=0;i< mangkho.length;i++ )
		{
 			if(mangkho[i]!=null && mangkho[i].length() >0){
 				if(mangkho[i].trim().equals(khoid.trim())){ 
 					return false;
 				}
 				 
 			}
		}
		return true;
	}

	private boolean TaoPhieuYeuCau( List<IYeucau>  listyc , String khoxuat ) {
		
		try{
		 
			 String query="";
			 	this.ndxId="100066";
				 query = " insert ERP_YEUCAUCHUYENKHO (LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua) " +
		 		 " values("+this.lenhsxId+",'" + this.ndxId + "', '" + getDateTime() + "', N'Chuyển kho cho LSX: " + this.lenhsxId + "', '0', '" +khoxuat + "', " + this.khoNhanId + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
				 
				 if(!db.update(query))
				 {
					this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO " + query;
					db.getConnection().rollback();
					return false;
				 } 
			 
				query = "select IDENT_CURRENT('ERP_YEUCAUCHUYENKHO') as ckId ";
				ResultSet rsPxk = db.get(query);						
				rsPxk.next();
				this.id = rsPxk.getString("ckId");
				rsPxk.close();
				
				
				
				for(int i=0;i< listyc.size();i++ )
				 
				{
					IYeucau yc1 =	listyc.get(i);
				
					if(yc1.getKhoid().equals(khoxuat)){
				
						query = " INSERT INTO ERP_YEUCAUCHUYENKHO_SANPHAM(YEUCAUCHUYENKHO_FK, SANPHAM_FK, SOLUONGYEUCAU, LENHSANXUAT_FK) " +
								" values("+this.id+","+yc1.getId()+","+yc1.getSoluongTon()+","+this.lenhsxId+")";
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
		 
		}catch(Exception err){
			err.printStackTrace();
			this.msg= err.getMessage();
			return false;
		}
		return true;
	}

	public String getTongSLYC() 
	{
		return this.tongSLYC;
	}

	public void setTongSLYC(String tongSLYC) 
	{
		this.tongSLYC = tongSLYC;
		
	}

	
	public String getNguoinhan() {
		
		return this.Nguoinhan;
	}

	
	public void setNguoinhan(String Nguoinhan) {
		
		this.Nguoinhan=Nguoinhan;
	}

	
	public String getIsChuyenHangSX() {
		
		return this.IsChuyenHangSX;
	}


	public void setIsChuyenHangSX(String _IsChuyenHangSX) {
		
		this.IsChuyenHangSX=_IsChuyenHangSX;
	}


	public List<ILenhsanxuat> getLenhSXList() 
	{
		return this.lsxList;
	}


	public void setLenhSXList(List<ILenhsanxuat> lenhSXList) 
	{
		this.lsxList = lenhSXList;
	}

	public String getLenhSXId() 
	{
    	return this.lenhsxId;
	}

	public void setLenhSXId(String LenhSXId) 
	{
		this.lenhsxId = LenhSXId;
	}

 

 

	
	public boolean getChoPhepChuyenhangdat() {
		
		//phương thức trả về biến cho phép chuyển hàng đạt và không đạt,nếu trong kho có quản lý hàng theo trạng thái thì mới chuyển được hàng này và được phép nhận hàng này
		//Kho chuyển và kho nhận đều cho phép xử lý hàng này
		//hoặc là chuyển hàng nội dung chuyển là xuất kho phế liệu
		
		return this.ChoPhepchuyenhangdat;
	}

	
	public String getIsChuyenhangkhongdat() {
		
		return this.IsChuyenhangkhongdat;
	}

	
	public void setIsChuyenhangkhongdat(String chuyenhangkhongdat) {
		
		this.IsChuyenhangkhongdat= chuyenhangkhongdat;
	}

	
	public String getCtyId() {
		return this.ctyId;
	}

	
	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	
	public String gettask() {
		
		return this.task;
	}

	
	public void settask(String task) {
		
		this.task=task;
	}

	
	public boolean createChuyenKhoThem(String khoxuat) {
		try{
			
			String query=  " SELECT A.KHOSANXUAT_FK FROM ERP_LENHSANXUAT_GIAY "+
			" A WHERE A.PK_SEQ="+this.lenhsxId;
	
			 ResultSet rskho=db.get(query);
			 if(rskho.next()){
				 this.khoNhanId=rskho.getString("KHOSANXUAT_FK");
			 }
			 rskho.close();
			 
			 query = " insert ERP_YEUCAUCHUYENKHO (LENHSANXUAT_FK,noidungxuat_fk, ngayyeucau, lydo, trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua) " +
	 		 " values("+this.lenhsxId+",'" + this.ndxId + "', '" + getDateTime() + "', N'Chuyển kho cho LSX: " + this.lenhsxId + "', '0', '" +khoxuat + "', " + this.khoNhanId + ",   '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
			 
			 System.out.println("cau lenh them phieu yeu cau chuyen kho:\n" + query + "\n==============================");
			 if(!db.update(query))
			 {
				this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			 } 
		 
			query = "select IDENT_CURRENT('ERP_YEUCAUCHUYENKHO') as ckId ";
			ResultSet rsPxk = db.get(query);						
			rsPxk.next();
			this.id = rsPxk.getString("ckId");
			rsPxk.close();
 
			  query=  " SELECT   BOM.VATTU_FK,  (BOM.SOLUONG - isnull(KHO.available,0) -ISNULL(bom.booked_lsx,0)) as soluongthieu , BOM.LENHSANXUAT_FK  FROM ERP_LENHSANXUAT_BOM_GIAY BOM "+  
			  		  " LEFT JOIN ERP_KHOTT_SANPHAM KHO ON KHO.KHOTT_FK = BOM.KHOTT_FK  AND BOM.VATTU_FK = KHO.SANPHAM_FK "+
			  		  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BOM.VATTU_FK   "+
			  		  " WHERE BOM.LENHSANXUAT_FK = "+this.lenhsxId+	 
			  		  " and  ( BOM.SOLUONG - isnull(KHO.available,0) -ISNULL(bom.booked_lsx,0)) > 0  " +
			  		  " and sp.pk_seq in (select sanpham_fk from erp_khott_sanpham where khott_fk="+khoxuat+")";

			  ResultSet  rscheckkho=db.getScrol(query);
 
				rscheckkho.beforeFirst();
				while(rscheckkho.next()){
					query = " INSERT INTO ERP_YEUCAUCHUYENKHO_SANPHAM(YEUCAUCHUYENKHO_FK, SANPHAM_FK, SOLUONGYEUCAU, LENHSANXUAT_FK) " +
							" values("+this.id+","+rscheckkho.getString("VATTU_FK")+","+rscheckkho.getString("soluongthieu")+","+rscheckkho.getString("LENHSANXUAT_FK")+")";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_YEUCAUCHUYENKHO_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			rscheckkho.close();
			
			//booked những sản phẩm nào đang có trong kho
			query=  " SELECT  BOM.SOTT, BOM.VATTU_FK, CASE WHEN  (BOM.SOLUONG  -ISNULL(BOM.BOOKED_LSX,0)) >  KHO.AVAILABLE THEN  KHO.AVAILABLE "+
					" ELSE (BOM.SOLUONG  -ISNULL(BOM.BOOKED_LSX,0)) END    SOLUONG,  "+
					" BOM.LENHSANXUAT_FK ,KHO.KHOTT_FK FROM ERP_LENHSANXUAT_BOM_GIAY BOM  "+ 
					" LEFT JOIN ERP_KHOTT_SANPHAM KHO ON KHO.KHOTT_FK = BOM.KHOTT_FK   "+
					" AND BOM.VATTU_FK = KHO.SANPHAM_FK INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BOM.VATTU_FK "+  
					" WHERE  ISNULL(KHO.AVAILABLE,0) > 0 AND BOM.LENHSANXUAT_FK ="+this.lenhsxId;
			  rskho=db.get(query);
			while (rskho.next()){
				String khoid=rskho.getString("KHOTT_FK");
				String spid=rskho.getString("VATTU_FK");
				double avai=(-1)*rskho.getDouble("SOLUONG");
				double booked=rskho.getDouble("SOLUONG");
				double soluong=0;
				String msg1 =util_kho.Update_Kho_Sp(db, khoid, spid, soluong, booked, avai, 0);
				if(msg1.length() >0){
					this.msg = msg1;
					db.getConnection().rollback();
					return false;
				}
				// cập nhật booked đối với những sản phẩm trong kho
				query=" update ERP_LENHSANXUAT_BOM_GIAY set BOOKED_LSX=isnull(BOOKED_LSX,0)+"+rskho.getDouble("SOLUONG")+" where sott="+rskho.getString("SOTT")+" and  vattu_fk="+spid+" and lenhsanxuat_fk= "+ this.lenhsxId;
				if(db.updateReturnInt(query) != 1)
				{
					this.msg = "Không thể cập nhật: "+query;
					db.getConnection().rollback();
					return false;
				}
			}
			rskho.close();
	   
		}catch(Exception err){
			err.printStackTrace();
			this.msg= err.getMessage();
			return false;
		}
		return true;
	}

	
	public String getKyHieu() {
		
		return this.kyhieu;
	}

	
	public void setKyHieu(String kyhieu) {
		
		this.kyhieu=kyhieu;
	}

	
	public String getSochungtu() {
		
		return this.sochungtu;
	}

	
	public void setSochungtu(String sochungtu) {
		
		this.sochungtu=sochungtu;
	}

	
	public String getLenhdieudong() {
		
		return lenhdieudong;
	}

	
	public void setLenhdieudong(String lenhdieudong) {
		
		this.lenhdieudong=lenhdieudong;
	}

	
	public String getNgaydieudong() {
		
		return ngaydieudong;
	}

	
	public void setNgaydieudong(String ngaydieudong) {
		
		this.ngaydieudong=ngaydieudong;
	}

	
	public String getNguoidieudong() {
		
		return this.nguoidieudong;
	}

	
	public void setNguoidieudong(String nguoidieudong) {
		
		this.nguoidieudong=nguoidieudong;
	}

	
	public String getVeviec() {
		
		return this.veviec;
	}

	
	public void setVeviec(String veviec) {
		
		this.veviec=veviec;
	}

	
	public String getNguoivanchuyen() {
		
		return this.nguoivanchuyen;
	}

	
	public void setNguoivanchuyen(String nguoivanchuyen) {
		
		this.nguoivanchuyen=nguoivanchuyen;
	}

	
	public String getPhuongtien() {
		
		return this.phuongtien;
	}

	
	public void setPhuongtien(String phuongtien) {
		
		this.phuongtien=phuongtien;
	}

	
	public String getHopdong() {
		
		return this.hopdong;
	}

	
	public void setHopdong(String hopdong) {
		
		this.hopdong=hopdong;
	}
	
	private boolean checkSoHoaDon() {
		
		try{
			String sql= 
			" SELECT COUNT (*) NUM" +
			" FROM ("+
			"		SELECT * FROM ERP_YEUCAUCHUYENKHO WHERE SOCHUNGTU = '"+this.sochungtu+"' AND KYHIEU = '"+this.kyhieu+"' and TRANGTHAI NOT IN (4) and NOIDUNGXUAT_FK = 100025 ";
					if(this.id .length() > 0){
						sql=sql+" and pk_seq NOT IN ("+this.id +")";
					}			
			sql+= ") a";
			
			System.out.println(sql);
			ResultSet rs = db.get(sql);
			try {
					rs.next();
					if(rs.getInt("NUM") > 0) {
						return true;
					}
					rs.close();
			} catch(Exception e) { 
				e.printStackTrace();
			}
			
			rs.close();
		
		}catch(Exception er){
			er.printStackTrace();
			return false;
		}
		return false;
	}

	
	public String getCoKhonhan() {
		
		return this.cokhonhan;
	}

	
	public void setCoKhonhan(String cokhoNHAN) {
		
		this.cokhonhan = cokhoNHAN;
	}

	
	public String getLoaidoituongId() {
		
		return this.loaidoituong;
	}

	
	public void setLoaidoituongId(String loaidoituong) {
		
		this.loaidoituong = loaidoituong;
	}

	
	public ResultSet getDoituongRs() {
		
		return this.doituongRs;
	}

	
	public void setDoituongRs(ResultSet doituongRs) {
		
		this.doituongRs = doituongRs;
	}


	public String getCoDoituong() {

		return this.codoituong;
	}


	public void setCoDoituong(String codoituong) {
		
		this.codoituong = codoituong;
	}

	
	public String getCoDoituongNhan() {
		
		return this.codoituongNhan;
	}

	
	public void setCoDoituongNhan(String codoituongNhan) {
		
		this.codoituongNhan = codoituongNhan;
	}

	
	public String getLoaidoituongNhanId() {
		
		return this.loaidoituongNhan;
	}

	
	public void setLoaidoituongNhanId(String loaidoituongNhan) {
		
		this.loaidoituongNhan = loaidoituongNhan;
	}

	
	public ResultSet getDoituongNhanRs() {
		
		return this.doituongNhanRs;
	}

	
	public void setDoituongNhanRs(ResultSet doituongNhanRs) {
		
		this.doituongNhanRs = doituongNhanRs;
	}

	
	public String getDoituongId() {
		
		return this.doituongId;
	}

	
	public void setDoituongId(String doituongId) {
		
		this.doituongId = doituongId;
	}

	
	public String getDoituongNhanId() {
		
		return this.doituongNhanId;
	}

	
	public void setDoituongNhanId(String doituongNhanId) {
		
		this.doituongNhanId = doituongNhanId;
	}

	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		//Nếu vào cập nhật, thì chỉ lấy danh sách đã lưu trước đó, không đề xuất lại nữa
		if( this.id.trim().length() > 0 )
			tongluong = "0";
		String dk="0";
		if(this.isnhanHang.equals("1"))
			 dk=" isnull(DAXUAT.soluongDACHON, 0)";

			
		
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		Utility util = new Utility();
		String soloDACHON = "";
		if( this.sanpham_soluongDAXUAT != null )
		{
			//KEY: MA - SOLO; 
			Enumeration<String> keys = this.sanpham_soluongDAXUAT.keys();
			while( keys.hasMoreElements() )
			{
				String key = keys.nextElement();
				 System.out.println("key: "+key);
				if(key.startsWith(spMa))
				{
					/*String solo = key.split("__")[1];
					String ngayhethan = key.split("__")[2];
					String ngaynhapkho = key.split("__")[3];
					
					String mame = key.split("__")[4];
					String mathung = key.split("__")[5];
					String vitri = key.split("__")[6];
					
					String maphieu = key.split("__")[7];
					String phieudt = key.split("__")[8];
					String phieueo = key.split("__")[9];
					
					String marq = key.split("__")[10];
					String hamluong = key.split("__")[11];
					String hamam = key.split("__")[12];*/
					String solo = key.split("__")[2];
					String ngayhethan = key.split("__")[3];
					String ngaynhapkho = key.split("__")[4];
					
					String mame = key.split("__")[5];
					String mathung = key.split("__")[6];
					String vitri = key.split("__")[7];
					
					String maphieu = key.split("__")[8];
					String phieudt = key.split("__")[9];
					String phieueo = key.split("__")[10];
					
					String marq = key.split("__")[11];
					String hamluong = key.split("__")[12];
					String hamam = key.split("__")[13];
					
					String nsxMa = key.split("__")[16];
					String nsx_fk = key.split("__")[17];
					
					String daxuat =  this.sanpham_soluongDAXUAT.get(key);
					
					if( daxuat.trim().length() > 0 )
					{
						soloDACHON += "select '" + solo + "' as soloCHON, '" + ngayhethan + "' as ngayhethanCHON, '" + ngaynhapkho + "' as ngaynhapkhoCHON, " + daxuat + " as soluongDACHON, '" + mame + "' as mame, '" + mathung + "' as mathung, '" + maphieu + "' as maphieu, " + 
									  " '" + marq + "' as marq, '" + hamluong + "' as hamluong, '" + hamam + "' as hamam, " + 
									  " isnull( ( select PK_SEQ from ERP_BIN where KHOTT_FK = '" + this.khoXuatId + "' and MA = N'" + vitri + "' ), 0 ) as vitri, '" + phieudt + "' as phieudt, '" + phieueo + "' as phieueo, '" + 
									  nsx_fk + "' as nsx_fk union ";
					}
				}
			}
		}
		
		if( soloDACHON.trim().length() > 0 )
		{
			soloDACHON = soloDACHON.substring(0, soloDACHON.length() - 7 );
			soloDACHON = " select soloCHON, ngayhethanCHON, ngaynhapkhoCHON, mame, mathung, maphieu, marq, hamluong, hamam, isnull(vitri, 0) as vitri, phieudt, phieueo, isnull(nsx_fk, 0) as nsx_fk, SUM( soluongDACHON ) as soluongDACHON " +
						 " from ( " + soloDACHON + " ) DT group by soloCHON, ngayhethanCHON, ngaynhapkhoCHON, mame, mathung, maphieu, marq, hamluong, hamam, vitri, phieudt, phieueo, nsx_fk ";
		}
		else
			soloDACHON = " select '1' as soloCHON, '' as ngayhethanCHON, '' as ngaynhapkhoCHON, '' as mame, '' as mathung, '' as maphieu, '' as marq, '' as hamluong, '' as hamam, '0' vitri, '' phieudt, '' phieueo, '0' as nsx_fk, 0 as soluongDACHON ";
		
		String sqlDONHANG = "";
		sqlDONHANG = " select SUM(soluong) from ERP_CHUYENKHO_SANPHAM_CHITIET " + 
					 " where chuyenkho_fk = '" + ( this.id.trim().length() > 0 ? this.id : "1" ) + "' and SANPHAM_FK = DT.sanpham_fk " + 
					 		" and solo = DT.solo and ngayhethan = DT.ngayhethan and rtrim(ltrim(ngaynhapkho)) =  rtrim(ltrim(dt.ngaynhapkho)) " + 
					 		" and isnull(mame, '') = isnull(DT.mame, '') and isnull(mathung, '') = isnull(DT.mathung, '') and isnull(maphieu, '') = isnull(DT.maphieu, '')  " +		
							" and isnull(marq, '') = isnull(DT.marq, '') and isnull(hamluong, 100) = isnull(DT.hamluong, 100) and isnull(hamam, 0) = isnull(DT.hamam, 0)  " + 
							" and isnull(bin_fk, 0) = isnull(DT.vitri, 0) and isnull(phieudt, '') = isnull(DT.phieudt, '') and isnull(phieueo, '') = isnull(DT.phieueo, '')  " +
							" and isnull(nsx_fk, 0) = isnull(DT.nsx_fk, 0) ";
		
		String condition = "";
		if( this.loaidoituong.trim().length() > 0 )
			condition += " and isnull(ct.loaidoituong, -1) = '" + this.loaidoituong + "' ";
		if( this.doituongId.trim().length() > 0 )
			condition += " and isnull(ct.doituongId, 0) = '" + this.doituongId + "' ";
		
		
		//kiem tra kho san xuat
				String query="select count (PK_SEQ) dem from erp_khott where pk_seq='"+this.khoXuatId+"' and loaiKHO=10";
				int dem=0;
				ResultSet rs=db.get(query);
				
				try {
					if(rs.next())
					dem=rs.getInt("dem");
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// het kiem tra kho san xuat
				
				String sql="\n 	select ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, MAPHIEU, MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( bin_fk, 0 ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo, isnull( nsx_fk, 0 ) as nsx_fk   "+
						 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
						 "\n 	where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
						 		"  and ngaynhapkho <= '" + this.ngayyeucau + "' "+ condition +
						 		"  and ( isnull(sp.batbuockiemdinh, 0) = 0 or KHOTT_FK in (100023,100058,100067,100069 )  or ( sp.batbuockiemdinh = 1 and isnull( maphieu, '' ) != ''  ) )		" ;
				
				if(this.lsxIds.trim().length()>0 && this.getMaNDX(this.ndxId).equals("XK10") && dem>0 ){
					 sql = " \n select ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, MAPHIEU, MARQ,  HAMLUONG, HAMAM,  vitri,  phieudt,phieueo, isnull( nsx_fk, 0 ) as nsx_fk   "+
						   " \n	 from UFN_GETLSX_TONCHITIET('"+this.khoXuatId+"','"+this.lsxIds+"',( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ))ct ";
				}

				
		 query = "";
		 query =  "\n select dt.sanpham_fk , DT.NGAYNHAPKHO, DT.NGAYHETHAN, DT.SOLO, isnull(DT.MAME, '') as MAME, isnull(DT.MATHUNG, '') as MATHUNG, isnull(DT.MAPHIEU, '') as MAPHIEU, " + 
				 "\n  		isnull(DT.MARQ, '') as MARQ, DT.hamluong, DT.hamam, isnull(bin.MA, '') as vitriTEN, DT.vitri, DT.phieudt, DT.phieueo, DT.nsx_fk, isnull(nsx.MA + ' - ' + nsx.Ten, '') as nsxMa, " +
				 " 		DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - "+dk+" as AVAILABLE  "+
				 "\n from "+
				 "\n ( "+ sql+ "\n ) "+
				 "\n DT left join ERP_BIN bin on DT.vitri = bin.pk_seq "
				 + "left join ERP_NHASANXUAT nsx on DT.nsx_fk = nsx.pk_seq "
				 + " left join  "+
				 "\n ( "+
				 	soloDACHON +
				 "\n ) "+
				 "\n DAXUAT on DT.SOLO = DAXUAT.soloCHON  and rtrim(ltrim( DT.NGAYHETHAN)) = rtrim(ltrim(DAXUAT.ngayhethanCHON)) and DT.NGAYNHAPKHO = DAXUAT.ngaynhapkhoCHON  "+
				 "\n and isnull(DT.MAME, '') = isnull(DAXUAT.MAME, '') and isnull(DT.MATHUNG, '') = isnull(DAXUAT.MATHUNG, '') and isnull(DT.MAPHIEU, '') = isnull(DAXUAT.MAPHIEU, '')  " +
				 "\n and isnull(DT.MARQ, '') = isnull(DAXUAT.MARQ, '') and isnull(DT.HAMLUONG, 100) = isnull(DAXUAT.HAMLUONG, 100) and isnull(DT.HAMAM, 0) = isnull(DAXUAT.HAMAM, 0)  " +
				 "\n and isnull(DT.vitri, '0') = isnull(DAXUAT.vitri, '0') and isnull(DT.phieudt, '') = isnull(DAXUAT.phieudt, '') and isnull(DT.phieueo, '') = isnull(DAXUAT.phieueo, '' )  " +
				 "\n and isnull(DT.nsx_fk, '0') = isnull(DAXUAT.nsx_fk, '0') " +
				 "\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - "+dk+" > 0 ";
		
		
		
		if( this.ndxId.equals("100073") ) //Xuất chuyển nội bộ
		{
			String conditionKHONGDUOCPB = "     select b.solo from LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG a inner join LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) " +
  					  					  " 	where a.TRANGTHAI = '1' and a.tungay <= '" + this.ngayyeucau + "' and '" + this.ngayyeucau + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG_DOITUONG where doituong_fk != '" + this.doituongNhanId + "' ) ";

			String conditionDUOCPB = "  select b.solo from LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG a inner join LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) " +
									 " 	where a.TRANGTHAI = '1' and a.tungay <= '" + this.ngayyeucau + "' and '" + this.ngayyeucau + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from LINK_DMS_THAT.DataCenter.dbo.ERP_PHANBODONHANG_DOITUONG where doituong_fk = '" + this.doituongNhanId + "' ) ";

			query += "\n			and DT.SOLO not in ( select solo from LINK_DMS_THAT.DataCenter.dbo.ERP_HANGCHOPHANBO where sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) )	" +
					 "\n 			and ( DT.SOLO not in ( " + conditionKHONGDUOCPB + " ) or DT.SOLO in ( " + conditionDUOCPB + " ) )	";
		}
		
		/*query += "\n order by  DT.NGAYHETHAN      ";*/

		query += "\n order by  DT.NGAYHETHAN  ,DT.NGAYNHAPKHO , DT.MAPHIEU, DT.SOLO, dbo.ftConvertToNumber(DT.MAME), dbo.ftConvertToNumber(DT.MATHUNG)   ";
		
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
		String query2 = "";
		 rs = db.get(query);
		try 
		{
			double total = 0;
			while(rs.next())
			{
				double slg = 0;
				double avai = rs.getDouble("AVAILABLE");
				
				total += avai;
				
				if(total < Double.parseDouble(tongluong))
				{
					slg = avai;
				}
				else
				{
					slg =  Double.parseDouble(tongluong) - ( total - avai );
				}
					
				slg = util.Round(slg, 4);
				if(slg > 0)
				{
					query2 += "select   '"+rs.getString("SANPHAM_FK") + "'  as SPID ,'" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("NGAYNHAPKHO") + "' as NGAYNHAPKHO, " + 
							" '" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MATHUNG") + "' as MATHUNG, '" + rs.getString("MAPHIEU") + "' as MAPHIEU, " + 
							" '" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("HAMLUONG") + "' as HAMLUONG, '" + rs.getString("HAMAM") + "' as HAMAM, " + 
							" '" + rs.getString("vitriTEN") + "' as vitri, '" + rs.getString("PHIEUDT") + "' as PHIEUDT, '" + rs.getString("PHIEUEO") + "' as PHIEUEO, " + 
							" N'" + rs.getString("nsxMa") + "' as nsxMa, '" + rs.getString("nsx_fk") + "' as nsx_fk, " +
							" '" + rs.getString("SOLO") + "' as SOLO, '" + slg + "' as tuDEXUAT union ALL ";
					
				}
				else
				{
					query2 += "select '"+rs.getString("SANPHAM_FK") + "'  as SPID , '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("NGAYNHAPKHO") + "' as NGAYNHAPKHO, " + 
							" '" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MATHUNG") + "' as MATHUNG, '" + rs.getString("MAPHIEU") + "' as MAPHIEU, "+ 
							" '" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("HAMLUONG") + "' as HAMLUONG, '" + rs.getString("HAMAM") + "' as HAMAM, " + 
							" '" + rs.getString("vitriTEN") + "' as vitri, '" + rs.getString("PHIEUDT") + "' as PHIEUDT, '" + rs.getString("PHIEUEO") + "' as PHIEUEO, " + 
							" N'" + rs.getString("nsxMa") + "' as nsxMa, '" + rs.getString("nsx_fk") + "' as nsx_fk, " +
							" '" + rs.getString("SOLO") + "' as SOLO, '' as tuDEXUAT union ALL ";
				}
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
		}
		
		if(query2.trim().length() > 0)
		{
			query2 = query2.substring(0, query2.length() - 10);
			
			//SORT LAI THEO YEU CAU Lô - PKN - Mẻ - Thùng
			//query2 = " select * from ( " + query2 + " ) DT order by SOLO, MAPHIEU, dbo.ftConvertToNumber(MAME), dbo.ftConvertToNumber(MATHUNG) ";
			
			query2 = " select * from ( " + query2 + " ) DT order by NGAYHETHAN, SOLO, MAPHIEU, dbo.ftConvertToNumber(MAME), dbo.ftConvertToNumber(MATHUNG), NGAYNHAPKHO ";
			
			System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
			return db.get(query2);
		}
		
		return null;
	}
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public Hashtable<String, String> getSanpham_SoluongDAXUAT_THEODN() 
	{
		return this.sanpham_soluongDAXUAT;
	}


	public void setSanpham_SoluongDAXUAT_THEODN(Hashtable<String, String> sp_soluong) 
	{
		this.sanpham_soluongDAXUAT = sp_soluong;
	}

	public boolean initFromYCXK(String ycxkId) 
	{
		String query = " select pk_seq from erp_chuyenkho where trangthai in (0) and  yeucauchuyenkho_fk=  " + ycxkId;
		ResultSet rscheck = db.get(query);
		if (rscheck != null)
		{
			try
			{
			if(rscheck.next())
			{
				this.msg="Bạn phải hoàn tất phiếu chuyển kho :"+rscheck.getString("pk_seq")+" trước khi muốn chuyển tiếp" ;
				rscheck.close();
				return false;
			}
			}
			catch(Exception ex )
			{
				ex.printStackTrace();
				this.msg = ex.getMessage();
				return false;
			}
		}	
		
		query = "select ISNULL(TRANGTHAISP,1) AS TRANGTHAISP ,isnull(ISCHUYENHANGSX,'0') as ISCHUYENHANGSX ,isnull(NGUOINHAN,'') as NGUOINHAN , Lenhsanxuat_fk, noidungxuat_fk, NGAYYEUCAU, lydo, isnull(ghichu, '') as ghichu, khoxuat_fk, khonhan_fk, trangthai,  " + 
				" 	loaidoituong, doituong_fk, loaidoituongNhan, doituongNhan_fk, " +
				" ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, "+
				" ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC, ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG, chiphi_fk,muahang_fk "+
				"from ERP_YEUCAUCHUYENKHO where pk_seq = '" + ycxkId + "'";
		
		System.out.println("INIT FROM YEUCAUCHUYENKHO: " + query);
		ResultSet rs = db.get(query);

		try 
		{
			if(rs.next())
			{
				this.lsxIds = rs.getString("Lenhsanxuat_fk") == null ? "" : rs.getString("Lenhsanxuat_fk");
				this.ndxId = rs.getString("noidungxuat_fk");
				this.ngayyeucau = rs.getString("NGAYYEUCAU");
				this.lydoyeucau = rs.getString("lydo");
				this.Nguoinhan = rs.getString("nguoinhan");
				this.ghichu = rs.getString("ghichu");
				this.khoXuatId = rs.getString("khoxuat_fk");
				this.khoNhanId = rs.getString("khonhan_fk");
				this.trangthai = rs.getString("trangthai");

				this.loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				this.doituongId = rs.getString("doituong_fk") == null ? "" : rs.getString("doituong_fk");
				this.loaidoituongNhan = rs.getString("loaidoituongNhan") == null ? "" : rs.getString("loaidoituongNhan");
				this.doituongNhanId = rs.getString("doituongNhan_fk") == null ? "" : rs.getString("doituongNhan_fk");

				this.IsChuyenHangSX=rs.getString("ISCHUYENHANGSX");
				this.IsChuyenhangkhongdat=rs.getString("TRANGTHAISP");
				this.kyhieu = rs.getString("KYHIEU");
				this.sochungtu = rs.getString("SOCHUNGTU");
				this.lenhdieudong = rs.getString("LENHDIEUDONG");
				this.ngaydieudong = rs.getString("NGAYDIEUDONG");
				this.nguoidieudong = rs.getString("NGUOIDIEUDONG");
				this.veviec = rs.getString("VEVIEC");
				this.nguoivanchuyen = rs.getString("NGUOIVANCHUYEN");
				this.phuongtien = rs.getString("PHUONGTIEN");
				this.hopdong = rs.getString("HOPDONG");
				
				this.chiphiId = rs.getString("chiphi_fk") == null ? "" : rs.getString("chiphi_fk");
				this.muahang_fk = rs.getString("muahang_fk") == null ? "" : rs.getString("muahang_fk");
			}
			rs.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			this.msg = e.getMessage();
			return false;
		}

		query =  " select   b.DVDL_FK  as  DVDL_ID,   isnull(ghichu_chuyenkho,'') as ghichu_chuyenkho , b.pk_seq as spId, isnull( b.MA,b.ma) as spMa, b.Ten    as spTen, " +
				" DONVIDOLUONG.DIENGIAI AS DVT, a.LENHSANXUAT_FK as lsxId,  " +
				" ISNULL(a.SOLUONGYEUCAU, 0) as SOLUONGYEUCAU ,isnull(kho.available ,0) as soluongton   " +
				" from ERP_YEUCAUCHUYENKHO_SANPHAM a inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ " +
				" inner join erp_yeucauchuyenkho ycck on ycck.pk_seq= a.YEUCAUCHUYENKHO_FK  " +
				" left join erp_khott_sanpham kho on kho.khott_fk=ycck.khoxuat_fk and kho.sanpham_fk= a.sanpham_fk       " +
				" LEFT JOIN DONVIDOLUONG ON DONVIDOLUONG.PK_SEQ = b.DVDL_FK  " +
				" where a.YEUCAUCHUYENKHO_FK = '" + ycxkId + "' ";
		
		System.out.println("__2.init SP: " + query );
		
		ResultSet spRs = db.get(query);
		List<IYeucau> spList = new ArrayList<IYeucau>();
		if(spRs != null)
		{
			try 
			{
				IYeucau sp = null;
				while(spRs.next())
				{
					String spId = spRs.getString("spId");
					String spMa = spRs.getString("spMa");
					String spTen = spRs.getString("spTen");
					 
					String donvi = spRs.getString("DVT");
					String donvitinh_id = spRs.getString("DVDL_ID");
					
					
					sp = new Yeucau();
					
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setghichu_CK(spRs.getString("ghichu_chuyenkho"));
					sp.setTen(spTen);
					sp.setDonViTinh(donvi);
					sp.setSoluongTon(spRs.getString("soluongton"));
					sp.setSoluongYC(spRs.getString("soluongyeucau"));
					sp.setLsxId(spRs.getString("lsxId")== null ? "" :spRs.getString("lsxId") );
					sp.setDvtinh_id(donvitinh_id);			
					spList.add(sp);
				}
				spRs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				this.msg = e.getMessage();
				return false;
			}
			
			this.spList = spList;
		}

		this.createRs();
		return true;
	}


	public String getYcckId()
	{
		return this.ycckId;
	}

	public void setYcckId(String ycckId) 
	{
		this.ycckId = ycckId;
	}
	
	public static void main(String[] arg)
	{
		String str = "1SATN__ __NA__2016-12-31__2016-06-01____100__0__M1.1____";
		
		String[] arr = str.split("__");
		for( int i = 0; i < arr.length; i++ )
		{
			System.out.println(" -- PHAN TU: " + i + " Gia tri: " + arr[i]);
		}
	
	}

	public ResultSet getVitriNhanRs()
	{
		return this.vitriNhanRs;
	}

	public void setVitriNhanRs(ResultSet vitriNhanRs) 
	{
		this.vitriNhanRs = vitriNhanRs;
	}
	
	public String getCochiphi() {
		
		return this.coChiphi;
	}

	
	public void setCochiphi(String coChiphi) {
		
		this.coChiphi = coChiphi;
	}

	
	public String getChiphiId() {
		
		return this.chiphiId;
	}

	
	public void setChiphiId(String chiphiId) {
		
		this.chiphiId = chiphiId;
	}

	
	public ResultSet getChiphiRs() {
		
		return this.chiphiRs;
	}

	
	public void setChiphiRs(ResultSet chiphiRs) {
		
		this.chiphiRs = chiphiRs;
	}

	@Override
	public List<String> getKey() {
		// TODO Auto-generated method stub
		return Listkey;
	}

	@Override
	public void setKey(List<String> listkey) {
		// TODO Auto-generated method stub
		Listkey=listkey;
	}

	public String getMuahang_fk() {
		return muahang_fk;
	}

	public void setMuahang_fk(String muahang_fk) {
		this.muahang_fk = muahang_fk;
	}

	public ResultSet getMuahangList() {
		return muahangList;
	}

	public void setMuahangList(ResultSet muahangList) {
		this.muahangList = muahangList;
	}

	public String getNgaychotCK() {
		return ngaychotCK;
	}

	public void setNgaychotCK(String ngaychotCK) {
		this.ngaychotCK = ngaychotCK;
	}

	@Override
	public String getCDSXId() {
		// TODO Auto-generated method stub
		return this.CdsxId;
	}

	@Override
	public void setCDSXId(String CDSXId) {
		// TODO Auto-generated method stub
		this.CdsxId= CDSXId;
	}

	@Override
	public ResultSet getCDSXRs() {
		// TODO Auto-generated method stub
		return this.CdsxRs;
	}

	@Override
	public void setCDSXRs(ResultSet CDSXRs) {
		// TODO Auto-generated method stub
		this.CdsxRs= CDSXRs;
	}

	public String getSokien() {
		return sokien;
	}

	public void setSokien(String sokien) {
		this.sokien = sokien;
	}

	
	
	
}

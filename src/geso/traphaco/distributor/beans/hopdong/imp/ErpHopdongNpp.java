package geso.traphaco.distributor.beans.hopdong.imp;

import geso.traphaco.distributor.beans.hopdong.IErpHopdongNpp;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpHopdongNpp implements IErpHopdongNpp
{
	String userId;
	String id;
	
	String ma;
	String hopdongchung;
	String tungay;
	String denngay;
	String ghichu;
	
	String hieuLuc;

	String msg;
	String trangthai;
	
	String loaidonhang; 
	String chietkhau;
	String vat;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String khId;
	ResultSet khRs;
	
	String khApdungId;
	ResultSet khApdungRs;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	String gsbhId;
	ResultSet gsbhRs;
	
	String ddkdId;
	ResultSet ddkdRs;
	
	String hopdongId;
	ResultSet hopdongRs;
	
	String donhangmuonId;
	ResultSet donhangmuonRs;
	
	ResultSet dvtRs;

	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spChietkhau;
	String[] spVAT;
	String[] spTungay;
	String[] spDenngay;
	String[] spTrongluong;
	String[] spThetich;
	String[] spQuyDoi;
	String[] spTDV;
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	String ngaytrungthau; 
	String chiphibaolanh;
	String capnhatTDV;
	
	Utility util;
	dbutils db;
	
	Object loainhanvien;
	Object doituongId;
	
	public ErpHopdongNpp()
	{
		this.id = "";
		this.ma = "";
		this.tungay = "";
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.khApdungId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.hopdongId = "";
		this.hopdongchung = "0";
		this.hieuLuc = "";
		
		this.dhCk_diengiai = new String[]{"", "", "", ""};
		this.dhCk_giatri = new String[]{"0", "0", "0", "0"};
		this.dhCk_loai = new String[]{"0", "0", "0", "0"};
		
		this.ngaytrungthau = "";
		this.chiphibaolanh = "0";
		
		this.donhangmuonId = "";
		this.capnhatTDV = "0";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.db = new dbutils();
		this.util = new Utility();
	}
	
	public ErpHopdongNpp(String id)
	{
		this.id = id;
		this.ma = "";
		this.tungay = "";
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.khApdungId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.hopdongId = "";
		this.hopdongchung = "0";
		this.hieuLuc = "";

		this.dhCk_diengiai = new String[]{"", "", "", ""};
		this.dhCk_giatri = new String[]{"0", "0", "0", "0"};
		this.dhCk_loai = new String[]{"0", "0", "0", "0"};
		
		this.ngaytrungthau = "";
		this.chiphibaolanh = "0";
		
		this.donhangmuonId = "";
		this.capnhatTDV = "0";
		
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.db = new dbutils();
		this.util = new Utility();
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
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean createNK() 
	{
		if(this.ma.trim().length() <= 0 && !this.kbhId.equals("100052") && !this.loaidonhang.equals("1") )
		{
			this.msg = "Vui lòng nhập mã hợp đồng";
			return false;
		}
		
		if(this.tungay.trim().length() < 10  )
		{
			this.msg = "Vui lòng nhập ngày bắt đầu hợp đồng";
			return false;
		}
		
		if(this.denngay.trim().length() < 10  )
		{
			this.msg = "Vui lòng nhập ngày kết thúc hợp đồng";
			return false;
		}

		if( this.gsbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn Giám sát";
			return false;
		}
		
		if( this.ddkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn trình dược viên";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 && !this.loaidonhang.equals("3") )
		{
			this.msg = "Vui lòng chọn khách hàng ETC";
			return false;
		}
		else
		{
			if(this.loaidonhang.equals("3") && this.khApdungId.trim().length() <= 0 )
			{
				this.msg = "Vui lòng chọn sở y tế áp dụng";
				return false;
			}
		}
		
		//NEU PHU LUC CUA HOP DONG NGUYEN TAC THI KHONG CAN NHAP SO LUONG
		String loaihopdongPL = "";
		if(this.loaidonhang.equals("1"))
		{
			if(this.hopdongId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn phụ lục của hợp đồng";
				return false;
			}
			else
			{
				String query = " select loaidonhang, mahopdong, ISNULL( ( select count(*) from ERP_HOPDONGNPP where loaidonhang = '1' and hopdong_fk = '" + this.hopdongId + "' ), 0 ) as sophuluc " + 
						" from ERP_HOPDONGNPP where pk_seq = '" + this.hopdongId + "'";
				ResultSet rsLOAI = db.get(query);
				if(rsLOAI != null)
				{
					try 
					{
						if(rsLOAI.next())
						{
							loaihopdongPL = rsLOAI.getString("loaidonhang");
							this.ma = rsLOAI.getString("mahopdong") + "\\PL" + ( rsLOAI.getInt("sophuluc") + 1 );  //Mặc định là mã hợp đồng + \PL1
						}
						rsLOAI.close();
					} 
					catch (Exception e) {}
				}
			}
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm hợp đồng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(this.loaidonhang.equals("1"))  //HỢP ĐỒNG PHỤ LỤC CỦA HỢP ĐỒNG NGUYÊN TẮC THÌ KHÔNG CẦN NHẬP SỐ LƯỢNG
					{
						if(spSoluong[i].trim().length() <= 0 && !loaihopdongPL.equals("2") )
						{
							this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
							return false;
						}
					}
					else
					{
						if(spSoluong[i].trim().length() <= 0 && !( this.loaidonhang.equals("2") || this.loaidonhang.equals("3") ) )  //Hợp đồng nguyên tắc thì không cần nhập số lượng
						{
							this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
							return false;
						}
					}
					
					if(spGianhap[i].trim().length() <= 0 || spGianhap[i].trim().equals("0") )
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					/*if(spTungay[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập từ ngày giao của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					if(spDenngay[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đến ngày giao của sản phẩm ( " + spTen[i] + " )";
						return false;
					}*/
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			//CHECK TRUNG MA HOP DONG
			String query = "select count(*) as soDONG from ERP_HopDongNpp where mahopdong = '" + this.ma + "' ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				if(rs.getInt("soDONG") > 0 )
				{
					this.msg = "Mã hợp đồng đã bị trùng. Vui lòng kiểm tra lại";
					rs.close();
					db.getConnection().rollback();
					return false;
				}
			}
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();
			String hopdong_fk = this.hopdongId.trim().length() <= 0 ? "null" : this.hopdongId.trim();
			String khachhang_fk = this.khId.trim().length() <= 0 ? "null" : this.khId.trim();
			String dvkd_fk = this.dvkdId.trim().length() <= 0 ? "null" : this.dvkdId;
			String kbh_fk = this.kbhId.trim().length() <= 0 ? "null" : this.kbhId;
			String cpbaolanh = this.chiphibaolanh.trim().length() <= 0 ? "0" : this.chiphibaolanh.replaceAll(",", "");
			
			query = " insert ERP_HopDongNpp(mahopdong, tungay, denngay, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, khachhang_fk, npp_fk, kho_fk, chietkhau, vat, HOPDONG_FK, ngaytao, nguoitao, ngaysua, nguoisua, ngaytrungthau, chiphibaolanh) " +
					" values(N'" + this.ma + "', '" + this.tungay + "', '" + this.denngay + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', " + dvkd_fk + ", " + kbh_fk + ", '" + this.gsbhId + "', '" + this.ddkdId + "', " + khachhang_fk + ", '" + this.nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', " + hopdong_fk + ", '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + this.ngaytrungthau + "', '" + cpbaolanh + "' )";
			
			System.out.println("1.Insert HOPDONG: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_HopDong " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_HopDongNpp') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			//KENH CLC TU XAC DINH MA HOP DONG
			if(  this.ma.trim().length() <= 0 && this.kbhId.equals("100052") )
			{
				//Số HĐ: XXXX/HĐNT 2015/PN  
				this.ma = this.id + "/HĐNT " + this.tungay.substring(0, 4) + "/PN";
				
				query = "Update ERP_HOPDONGNPP set mahopdong = N'" + this.ma + "' where pk_seq = '" + this.id + "' ";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_HopDong " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spGianhap[i].trim().length() > 0  )
				{
					String ck = "0";
					if(spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");
					
					String thueVAT = spVAT[i].trim().replaceAll(",", "");
					if(thueVAT.trim().length() < 0)
						thueVAT = "0";
					
					String slg = spSoluong[i].trim().length() <= 0 ? "NULL" : spSoluong[i].replaceAll(",", "");
					String ddkd_fk = spTDV[i].trim().length() <= 0 ? this.ddkdId : spTDV[i];
					
					query = "insert ERP_HOPDONGNPP_SANPHAM( hopdong_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay, ddkd_fk ) " +
							"select '" + this.id + "', pk_seq, " + slg + ", '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), '" + spTungay[i].trim() + "', '" + spDenngay[i].trim() + "', " + ddkd_fk + " " +
							"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
					
					System.out.println("1.Insert HD - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_HOPDONGNPP_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			
			//INSERT CHIET KHAU BO SUNG
			if(this.dhCk_diengiai != null)
			{
				for(int i = 0; i < this.dhCk_diengiai.length; i++)
				{
					if(this.dhCk_giatri[i].trim().length() > 0)
					{
						query = "insert ERP_HOPDONGNPP_CHIETKHAU(HOPDONG_FK, DIENGIAI, GIATRI, LOAI) " +
								"values( '" + this.id + "', N'" + this.dhCk_diengiai[i].trim() + "', '" + this.dhCk_giatri[i].replaceAll(",", "") + "', '" + this.dhCk_loai[i] + "' ) ";
						
						System.out.println("1.Insert HD - CK: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_HOPDONGNPP_CHIETKHAU: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			//INSERT DON HANG MUON
			if(this.donhangmuonId.trim().length() > 0)
			{
				query = "insert ERP_HOPDONGNPP_DDH(HOPDONG_FK, DDH_FK) " +
						"select '" + this.id + "', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.donhangmuonId + " ) ";
				
				System.out.println("1.Insert HD - DH MUON: " + query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_HOPDONGNPP_DDH: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			//HOP DONG CHUNG AP DUNG CHO NHUGN ETC NAO
			if(loaidonhang.equals("3") && this.khApdungId.trim().length() > 0)
			{
				query = "Insert ERP_HOPDONGNPP_APDUNG(hopdong_fk, khachhang_fk) " +
						"select '" + this.id + "', pk_seq from KHACHHANG where NPP_FK = '" + this.nppId + "' and pk_seq in ( " + this.khApdungId + " ) ";
				System.out.println("1.Insert HD - AP: " + query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_HOPDONGNPP_APDUNG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean updateNK(String checkKM, String trangthai) 
	{
		if(this.ma.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập mã hợp đồng";
			return false;
		}
		
		if(this.tungay.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày bắt đầu hợp đồng";
			return false;
		}
		
		if(this.denngay.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày kết thúc hợp đồng";
			return false;
		}

		if( this.gsbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn Giám sát";
			return false;
		}
		
		if( this.ddkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn trình dược viên";
			return false;
		}

		if( this.khId.trim().length() <= 0 && !this.loaidonhang.equals("3") )
		{
			this.msg = "Vui lòng chọn khách hàng ETC";
			return false;
		}
		else
		{
			if(this.loaidonhang.equals("3") && this.khApdungId.trim().length() <= 0 )
			{
				this.msg = "Vui lòng chọn sở y tế áp dụng";
				return false;
			}
		}
		
		//NEU PHU LUC CUA HOP DONG NGUYEN TAC THI KHONG CAN NHAP SO LUONG
		String loaihopdongPL = "";
		if(this.loaidonhang.equals("1"))
		{
			if(this.hopdongId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn phụ lục của hợp đồng";
				return false;
			}
			else
			{
				String query = " select loaidonhang, mahopdong, ISNULL( ( select count(*) from ERP_HOPDONGNPP where loaidonhang = '1' and hopdong_fk = '" + this.hopdongId + "' and pk_seq != '" + this.id + "' ), 0 ) as sophuluc " + 
							   " from ERP_HOPDONGNPP where pk_seq = '" + this.hopdongId + "'";
				ResultSet rsLOAI = db.get(query);
				if(rsLOAI != null)
				{
					try 
					{
						if(rsLOAI.next())
						{
							loaihopdongPL = rsLOAI.getString("loaidonhang");
							this.ma = rsLOAI.getString("mahopdong") + "\\PL" + ( rsLOAI.getInt("sophuluc") + 1 );  //Mặc định là mã hợp đồng + \PL1
						}
						rsLOAI.close();
					} 
					catch (Exception e) {}
				}
			}
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(this.loaidonhang.equals("1"))  //HỢP ĐỒNG PHỤ LỤC CỦA HỢP ĐỒNG NGUYÊN TẮC THÌ KHÔNG CẦN NHẬP SỐ LƯỢNG
					{
						if(spSoluong[i].trim().length() <= 0 && !loaihopdongPL.equals("2") )
						{
							this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
							return false;
						}
					}
					else
					{
						if(spSoluong[i].trim().length() <= 0 && !( this.loaidonhang.equals("2") || this.loaidonhang.equals("3") ) )  //Hợp đồng nguyên tắc thì không cần nhập số lượng
						{
							this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
							return false;
						}
					}
					
					if(spGianhap[i].trim().length() <= 0 || spGianhap[i].trim().equals("0") )
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					/*if(spTungay[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập từ ngày giao của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					if(spDenngay[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đến ngày giao của sản phẩm ( " + spTen[i] + " )";
						return false;
					}*/
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0  )
					{
						if( spMa[i].trim().equals(spMa[j].trim())  )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}
		}	
		
		
		//NẾU CHỐT RỒI THÌ CHỈ ĐƯỢC CẬP NHẬT TDV
		if(trangthai.equals("1"))
		{
			try 
			{
				db.getConnection().setAutoCommit(false);
				
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0 && spGianhap[i].trim().length() > 0  )
					{
						
						String ddkd_fk = spTDV[i].trim().length() <= 0 ? this.ddkdId : spTDV[i];
						
						String query = " update ERP_HOPDONGNPP_SANPHAM set ddkd_fk = " + ddkd_fk + 
									   " where hopdong_fk = '" + this.id + "' and SANPHAM_FK in ( select pk_seq from SANPHAM where MA = '" + spMa[i].trim() + "' )";
						
						System.out.println("1.update HD - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the cap nhat ERP_HOPDONGNPP_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
				
				db.getConnection().commit();
			} 
			catch (Exception e) 
			{
				db.update("rollback");
				this.msg = "1.Exception: " + e.getMessage();
				return false;
			}
		}
		else
		{
			try
			{
				db.getConnection().setAutoCommit(false);
				
				//CHECK TRUNG MA HOP DONG
				String query = "select count(*) as soDONG from ERP_HopDongNpp where mahopdong = '" + this.ma + "' and pk_seq != '" + this.id + "' ";
				ResultSet rs = db.get(query);
				if(rs.next())
				{
					if(rs.getInt("soDONG") > 0 )
					{
						this.msg = "Mã hợp đồng đã bị trùng. Vui lòng kiểm tra lại";
						rs.close();
						db.getConnection().rollback();
						return false;
					}
				}
				
				String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
				String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
				String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();	
				String hopdong_fk = this.hopdongId.trim().length() <= 0 ? "null" : this.hopdongId.trim();
				String khachhang_fk = this.khId.trim().length() <= 0 ? "null" : this.khId.trim();
				String dvkd_fk = this.dvkdId.trim().length() <= 0 ? "null" : this.dvkdId;
				String kbh_fk = this.kbhId.trim().length() <= 0 ? "null" : this.kbhId;
				String cpbaolanh = this.chiphibaolanh.trim().length() <= 0 ? "0" : this.chiphibaolanh.replaceAll(",", "");
				
				query =	" Update ERP_HopDongNPP set mahopdong = N'" + this.ma + "', loaidonhang = '" + this.loaidonhang + "', tungay = '" + this.tungay + "', denngay = '" + this.denngay + "', ghichu = N'" + this.ghichu + "', " +
						" 	dvkd_fk = " + dvkd_fk + ", kbh_fk = " + kbh_fk + ", gsbh_fk = '" + this.gsbhId + "', ddkd_fk = '" + this.ddkdId + "', khachhang_fk = " + khachhang_fk + ", npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", hopdong_fk = " + hopdong_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "', ngaytrungthau = '" + ngaytrungthau + "', chiphibaolanh = '" + cpbaolanh + "' " + 
						" where pk_seq = '" + this.id + "' ";
			
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_HopDongNPP " + query;
					db.getConnection().rollback();
					return false;
				}
							
				query = "delete ERP_HOPDONGNPP_SANPHAM where hopdong_fk = '" + this.id + "'";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_HOPDONGNPP_SANPHAM " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "delete ERP_HOPDONGNPP_SANPHAM where hopdong_fk = '" + this.id + "' ";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_HOPDONGNPP_SANPHAM " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "delete ERP_HOPDONGNPP_APDUNG where hopdong_fk = '" + this.id + "' ";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_HOPDONGNPP_APDUNG " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "delete ERP_HOPDONGNPP_DDH where hopdong_fk = '" + this.id + "' ";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_HOPDONGNPP_DDH " + query;
					db.getConnection().rollback();
					return false;
				}
				
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0 && spGianhap[i].trim().length() > 0  )
					{
						String ck = "0";
						if(spChietkhau[i].trim().length() > 0)
							ck = spChietkhau[i].trim().replaceAll(",", "");
						
						String thueVAT = spVAT[i].trim().replaceAll(",", "");
						if(thueVAT.trim().length() < 0)
							thueVAT = "0";
						
						String slg = spSoluong[i].trim().length() <= 0 ? "NULL" : spSoluong[i].replaceAll(",", "");
						String ddkd_fk = spTDV[i].trim().length() <= 0 ? this.ddkdId : spTDV[i];
						
						query = "insert ERP_HOPDONGNPP_SANPHAM( hopdong_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay, ddkd_fk ) " +
								"select '" + this.id + "', pk_seq, " + slg + ", '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), '" + spTungay[i].trim() + "', '" + spDenngay[i].trim() + "', " + ddkd_fk + " " +
								"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
						
						System.out.println("1.Insert HD - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_HOPDONGNPP_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
				
				//INSERT DON HANG MUON
				if(this.donhangmuonId.trim().length() > 0)
				{
					query = "insert ERP_HOPDONGNPP_DDH(HOPDONG_FK, DDH_FK) " +
							"select '" + this.id + "', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.donhangmuonId + " ) ";
					
					System.out.println("1.Insert HD - DH MUON: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_HOPDONGNPP_DDH: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				//INSERT CHIET KHAU BO SUNG
				if(this.dhCk_diengiai != null)
				{
					for(int i = 0; i < this.dhCk_diengiai.length; i++)
					{
						if(this.dhCk_giatri[i].trim().length() > 0)
						{
							query = "insert ERP_HOPDONGNPP_CHIETKHAU(HOPDONG_FK, DIENGIAI, GIATRI, LOAI) " +
									"values( '" + this.id + "', N'" + this.dhCk_diengiai[i].trim() + "', '" + this.dhCk_giatri[i].replaceAll(",", "") + "', '" + this.dhCk_loai[i] + "' ) ";
							
							System.out.println("1.Insert HD - CK: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi ERP_HOPDONGNPP_CHIETKHAU: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
				
				//HOP DONG CHUNG AP DUNG CHO NHUGN ETC NAO
				if(loaidonhang.equals("3") && this.khApdungId.trim().length() > 0)
				{
					query = "Insert ERP_HOPDONGNPP_APDUNG(hopdong_fk, khachhang_fk) " +
							"select '" + this.id + "', pk_seq from KHACHHANG where NPP_FK = '" + this.nppId + "' and pk_seq in ( " + this.khApdungId + " ) ";
					System.out.println("1.Insert HD - AP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_HOPDONGNPP_APDUNG: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} 
			catch (Exception e) 
			{
				db.update("rollback");
				this.msg = "Exception: " + e.getMessage();
				return false;
			}
		}
		
		return true;
	}


	public void createRs() 
	{
		this.getNppInfo();
				
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId) );
		if( this.khoNhanId.trim().length() <= 0 )
			this.khoNhanId = "100002";
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1'");
		
		//this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and pk_seq in ( select kbh_fk from nhomkenh_kenhbanhang where nk_fk = '100000' ) ");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and pk_seq in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) ");
		
		String query = "select PK_SEQ, TEN from GIAMSATBANHANG a where trangthai = '1' ";
		
		//CHI LAY GSBH THONG HE THONG ETC
		query += " and a.pk_seq in ( select GSBH_FK from GIAMSATBANHANG_KBH where KBH_FK in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) ) ";
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		query += util.getPhanQuyen_TheoNhanVien("GIAMSATBANHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
				
		this.gsbhRs = db.get(query);
		
		//String query = "select pk_seq, TEN from DAIDIENKINHDOANH where tructhuoc_fk = '" + this.nppId + "' ";
		query = "select pk_seq, TEN from DAIDIENKINHDOANH a where pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
		//if(this.gsbhId.trim().length() > 0)
			//query += " and pk_seq in ( select ddkd_fk from DDKD_GSBH where GSBH_FK = '" + this.gsbhId + "' ) ";
		query += " and pk_seq in ( select DDKD_FK from DAIDIENKINHDOANH_KBH where KBH_FK in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) ) ";
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		this.ddkdRs = db.getScrol(query);
		System.out.println("DDKD: "+query);
		//DOI LAI TRONG NHOM KENH
		if(this.trangthai.equals("0") || !this.loaidonhang.equals("3") )
		{
			query = "select PK_SEQ, MAFAST + ', ' + TEN as TEN from KHACHHANG a where TRANGTHAI = '1'  AND PK_SEQ in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) ) AND NPP_FK = '" + this.nppId + "' ";
			
			//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
			query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
			
			this.khRs = db.get(query);
			System.out.println("Khach hang: "+query);
			this.khApdungRs = db.get(query);
		}
		else
		{
			if(this.khId.trim().length() > 0)
			{
				query = "select PK_SEQ, MAFAST + ', ' + TEN as TEN " +
						"from KHACHHANG a where TRANGTHAI = '1'  AND PK_SEQ = '" + this.khId + "' AND NPP_FK = '" + this.nppId + "' ";
				
				//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
				query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
				
				System.out.println("Khach hang "+query);
				this.khRs = db.get(query);
				this.khApdungRs = db.get(query);
			}
		}
		
		System.out.println("---NPP ID: " + this.nppId);
		if(this.nppId.trim().length() > 0 && this.gsbhId.trim().length() <= 0  )
		{
			query = "select GSBH_FK from NHAPP_GIAMSATBH  " +
				    "where NPP_FK = '" + this.nppId + "' ";
			ResultSet rsInfo = db.get(query);
			if(rsInfo != null)
			{
				try 
				{
					if(rsInfo.next())
					{
						this.gsbhId = rsInfo.getString("gsbh_fk");
					}
					rsInfo.close();
				} 
				catch (Exception e) {}
			}
		}
		
		if(this.khId.trim().length() > 0 && this.ddkdId.trim().length() <= 0 )
		{
			query = "select b.ddkd_fk from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on a.TBH_FK = b.pk_seq where a.khachhang_fk = '" + this.khId + "'";
			System.out.println("--LAY DDKD: " + query );
			ResultSet rsDDKD = db.get(query);
			if(rsDDKD != null)
			{
				try 
				{
					if(rsDDKD.next())
					{
						this.ddkdId = rsDDKD.getString("ddkd_fk");
					}
					rsDDKD.close();
				}
				catch (Exception e) { }
			}
		}
		
		//if(this.loaidonhang.equals("1") && this.khId.trim().length() > 0 )
		if( this.khId.trim().length() > 0 )
		{
			query = "select PK_SEQ, cast(pk_seq as varchar(10)) + ' [' + mahopdong + ']' as diengiai " +
					"from ERP_HOPDONGNPP where khachhang_fk = '" + this.khId + "' and hieuluc = '1'  ";
			if( this.id.trim().length() > 0 )
			{
				query += "union select PK_SEQ, cast(pk_seq as varchar(10)) + ' [' + mahopdong + ']' as diengiai " +
						"from ERP_HOPDONGNPP where pk_seq in ( select hopdong_fk from ERP_HOPDONG where pk_seq = '" + this.id + "' )  ";
			}
			query += " order by pk_seq desc ";
			
			System.out.println(":::LAY HOP DONG: " + query);
			this.hopdongRs = db.get(query);
			
			if(this.spMa == null && this.hopdongId.trim().length() > 0 )
			{
				//INIT SAN PHAM LUC DAU SE GIONG SP TRONG HOP DONG CU, NHUNG DUOC PHEP SUA LAI SO LUONG
				
				String loaihopdongPL = "";
				String gsbh_fk = "";
				String ddkd_fk = "";
				//if(this.loaidonhang.equals("1") && this.hopdongId.trim().length() > 0 )
				if( this.hopdongId.trim().length() > 0 )
				{
					query = "select loaidonhang, gsbh_fk, ddkd_fk from ERP_HOPDONGNPP where pk_seq = '" + this.hopdongId + "'";
					System.out.println("::: LAY THONG TIN THEO HOP DONG: " + query);
					ResultSet rsLOAI = db.get(query);
					if(rsLOAI != null)
					{
						try 
						{
							if(rsLOAI.next())
							{
								loaihopdongPL = rsLOAI.getString("loaidonhang");
								gsbh_fk = rsLOAI.getString("gsbh_fk") == null ? "" : rsLOAI.getString("gsbh_fk");
								ddkd_fk = rsLOAI.getString("ddkd_fk") == null ? "" : rsLOAI.getString("ddkd_fk");
							}
							rsLOAI.close();
						} 
						catch (Exception e) {}
					}
				}
				
				//if( !(this.loaidonhang.equals("1") && loaihopdongPL.equals("2")) )
				//{
					this.ddkdId = ddkd_fk;
					this.gsbhId = gsbh_fk;
					
					initSANPHAM_THEOHD();
				//}
			}
		}
		
		if(this.khId.trim().length() > 0  )
		{
			query = "select PK_SEQ, CAST(pk_seq as varchar(10)) + ' / ' + NgayDonHang as ten " +
					"from ERP_DONDATHANGNPP where TRANGTHAI in ( 1, 4 ) and NPP_FK = '" + this.nppId + "' and donhangMUON = '1'  ";
			
			query += " and KHACHHANG_FK = '" + this.khId + "' ";
			
			query += " AND pk_seq not in ( select ddh_fk from ERP_HOPDONGNPP_DDH where hopdong_fk in ( select pk_seq from ERP_HOPDONGNPP where npp_fk = '" + this.nppId + "' and trangthai in ( 0, 1, 2 ) ) ) ";
			
			if(this.id.trim().length() > 0)
			{
				query += " union  " +
						 "select PK_SEQ, CAST(pk_seq as varchar(10)) + ' / ' + NgayDonHang as ten " +
						 "from ERP_DONDATHANGNPP where pk_seq in ( select ddh_fk from ERP_HOPDONGNPP_DDH where hopdong_fk = '" + this.id + "'  )   ";
			}
			
			System.out.println("----LAY DON DAT HANG MUON: " + query );
			this.donhangmuonRs = db.get(query);
			
			//CHECK XEM KHACH HANG CO THUOC KENH CLC KHONG -> CLC HOP DONG NGUYEN TAC
			query = "select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + this.khId + "'";
			ResultSet rsKENH = db.get(query);
			if(rsKENH != null)
			{
				try 
				{
					if(rsKENH.next())
					{
						this.kbhId = rsKENH.getString("kbh_fk");
					}
					rsKENH.close();
				} 
				catch (Exception e) { }
			}
			
		}
		else
		{
			this.donhangmuonId = "";
			this.donhangmuonRs = null;
		}
		
		//INIT SAN PHAM THEO DON HANG MUON
		if( this.donhangmuonId.trim().length() > 0 && this.spMa == null )
		{
			initSANPHAM_THEO_DONHANGMUON();
		}
		else
		{
			//Nếu là kênh CLC thì khởi tạo sẵn sản phẩm
			boolean dacoSP = false;
			if( this.spMa != null )
			{
				for(int i = 0; i < this.spMa.length; i++)
				{
					if( this.spMa[i].trim().length() > 0 )
						dacoSP = true;
				}
			}
			
			System.out.println("::::: DA CO SP: " + dacoSP);
			if(this.khId.trim().length() > 0 && !dacoSP )
			{
				/*query = "select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + this.khId + "'";
				ResultSet rsKENH = db.get(query);
				if(rsKENH != null)
				{
					try 
					{
						if(rsKENH.next())
						{
							this.kbhId = rsKENH.getString("kbh_fk");
						}
						rsKENH.close();
						
						if(kbhId.equals("100052")) //KÊNH CLC load sẵn hết SP theo bảng giá
						{
							initSANPHAM_THEO_KENHCLC();
						}
					} 
					catch (Exception e) { }
				}*/
				
				if(this.kbhId.equals("100052")) //KÊNH CLC load sẵn hết SP theo bảng giá
				{
					initSANPHAM_THEO_KENHCLC();
				}
			}
		}
		
		
		//NẾU KÊNH CLC THÌ MẶC ĐỊNH NGÀY HỢP ĐỒNG, MÀ HỢP ĐỒNG
		//System.out.println(":::: KHACH HANG ID: " + this.khId + " -- KENH BAN HANG: " + this.kbhId );
		if( this.khId.trim().length() > 0 && this.kbhId.equals("100052") )
		{
			if( this.denngay.trim().length() <= 0 )
				this.denngay = this.getDateTime();
			if( this.tungay.trim().length() <= 0 )
			{
				//Lấy bằng ngày tạo của khách hàng
				query = "select ngaytao from KHACHHANG where pk_seq = '" + this.khId + "' ";
				ResultSet rsNGAYTAO = db.get(query);
				if( rsNGAYTAO != null )
				{
					try 
					{
						if( rsNGAYTAO.next() )
							this.tungay = rsNGAYTAO.getString("ngaytao");
						rsNGAYTAO.close();
					} 
					catch (Exception e) { }
				}
			}
		}
		
	}

	private void initSANPHAM_THEO_KENHCLC() 
	{
		String query =   "select SP.MA, SP.TEN, DV.donvi, 0 as soluong,0 as chietkhau, ISNULL(SP.trongluong, 0) as trongluong, ISNULL(SP.thetich, 0) as thetich, ' ' tungay, ' ' as denngay, SP.thuexuat as thueVAT      "+
						 "	,( select soluong1 / soluong2 from QUYCACH where sanpham_fk = SP.PK_SEQ and DVDL1_FK = SP.DVDL_FK and DVDL2_FK = 100018 )   as spQuyDoi, ' ' as DDKD_FK, "+
						 "	ISNULL( (  "+
						 "		select case when d.dongia is not null then d.dongia else 	 "+
						 "				ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + this.nppId + "' ) ) ), 0) end as giamua   "+
						 "		from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq  "+
						 "		inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq  "+
						 "		left join BANGGIABANLEKH_SANPHAM d on a.PK_SEQ = d.SANPHAM_FK and d.KHACHHANG_FK = '" + this.khId + "' "+
						 "		where a.DVKD_FK = '100001' and a.PK_SEQ = SP.PK_SEQ "+
						 "	), 0 ) as dongia "+
						 "from SanPham SP  "+
						 " 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = SP.DVDL_FK         "+
						 "where SP.trangthai = '1' and SP.LOAISANPHAM_FK in ( 10045 ) ";

		System.out.println("---INIT SP KENH CLC: " + query);
		ResultSet spRs = db.get(query);

		NumberFormat formater = new DecimalFormat("##,###,###");
		NumberFormat formater_2 = new DecimalFormat("##,###,###.####");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spTUNGAY = "";
				String spDENNGAY = "";

				String spTRONGLUONG = "";
				String spTHETICH = "";

				String spQuyDoi = "";
				String spTDV = "";

				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater_2.format(spRs.getDouble("DONGIA")) + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(spRs.getDouble("thueVAT")) + "__";

					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += " __";

					if(spRs.getString("denngay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += " __";

					if(spRs.getString("DDKD_FK") != null )
						spTDV += spRs.getString("DDKD_FK") + "__";
					else
						spTDV += " __";

					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
				}
				spRs.close();

				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");

					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");

					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");

					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");

					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");

					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");

					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");

					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");

					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");

					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");

					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");

					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");

					spTDV = spTDV.substring(0, spTDV.length() - 2);
					this.spTDV = spTDV.split("__");
				}

			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
	}

	private void initSANPHAM() 
	{
		String query =  
					"select b.MA, b.TEN, DV.donvi, isnull(a.soluong, 0) as soluong, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, a.tungay, a.denngay, a.thueVAT    " +	
					"	,(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018)   as spQuyDoi, cast(a.ddkd_fk as varchar(10) ) as ddkd_fk, a.dongia "+
					" from ERP_HopDongNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					"where a.HOPDONG_FK = '" + this.id + "' and b.trangthai = '1' ";
		
		//NẾU KÊNH CLC THÌ TỰ ADD THÊM NHỮNG SP MỚI VÀO
		if( this.kbhId == null )
			this.kbhId = "";
		if(this.loaidonhang.equals("2")) //LOAI HOP DONG CLC
		{
		query += "	UNION	" + 
				"select SP.MA, SP.TEN, DV.donvi, 0 as soluong, 0 as chietkhau, ISNULL(SP.trongluong, 0) as trongluong, ISNULL(SP.thetich, 0) as thetich, ' ' tungay, ' ' as denngay, SP.thuexuat as thueVAT      "+
				 "	,( select soluong1 / soluong2 from QUYCACH where sanpham_fk = SP.PK_SEQ and DVDL1_FK = SP.DVDL_FK and DVDL2_FK = 100018 )   as spQuyDoi, ' ' as DDKD_FK, "+
				 "	ISNULL( (  "+
				 "		select case when d.dongia is not null then d.dongia else 	 "+
				 "				ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + this.nppId + "' ) ) ), 0) end as giamua   "+
				 "		from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq  "+
				 "		inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq  "+
				 "		left join BANGGIABANLEKH_SANPHAM d on a.PK_SEQ = d.SANPHAM_FK and d.KHACHHANG_FK = '" + this.khId + "' "+
				 "		where a.DVKD_FK = '100001' and a.PK_SEQ = SP.PK_SEQ "+
				 "	), 0 ) as dongia "+
				 "from SanPham SP  "+
				 " 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = SP.DVDL_FK         "+
				 "where SP.trangthai = '1' and SP.LOAISANPHAM_FK in ( 10045 ) and SP.pk_seq not in ( select sanpham_fk from ERP_HopDongNPP_SANPHAM where HOPDONG_FK = '" + this.id + "' )  ";
		}
		
		
		System.out.println("---INIT SP 2 : " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spTUNGAY = "";
				String spDENNGAY = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi = "";
				String spTDV = "";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += spRs.getDouble("DONGIA") + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					
					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += " __";
					
					if(spRs.getString("tungay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += " __";
					
					if(spRs.getString("ddkd_fk") != null )
						spTDV += spRs.getString("ddkd_fk") + "__";
					else
						spTDV += ( this.ddkdId.trim().length() <= 0 ? " " : this.ddkdId ) + "__";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");
					
					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");
					
					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
					
					spTDV = spTDV.substring(0, spTDV.length() - 2);
					this.spTDV = spTDV.split("__");
					
				}
				
				//INIT CHIET KHAU
				query = "select DIENGIAI, GIATRI, LOAI from ERP_HOPDONGNPP_CHIETKHAU where HOPDONG_FK = '" + this.id + "'";
				System.out.println("[INIT_CK]"+query);
				ResultSet rsCK = db.get(query);
				if(rsCK != null)
				{
					String dkCK_diengiai = "";
					String dkCK_giatri = "";
					String dkCK_loai = "";
					
					while(rsCK.next())
					{
						dkCK_diengiai += rsCK.getString("DIENGIAI") + "__";
						dkCK_giatri += formater.format(rsCK.getDouble("GIATRI")) + "__";
						dkCK_loai += rsCK.getString("LOAI") + "__";
					}
					rsCK.close();
					
					if(dkCK_diengiai.trim().length() > 0)
					{
						dkCK_diengiai = dkCK_diengiai.substring(0, dkCK_diengiai.length() - 2);
						this.dhCk_diengiai = dkCK_diengiai.split("__");
						
						dkCK_giatri = dkCK_giatri.substring(0, dkCK_giatri.length() - 2);
						this.dhCk_giatri = dkCK_giatri.split("__");
						
						dkCK_loai = dkCK_loai.substring(0, dkCK_loai.length() - 2);
						this.dhCk_loai = dkCK_loai.split("__");
					}
				}
				
				//INIT DDH
				query = "select ddh_fk from ERP_HOPDONGNPP_DDH where hopdong_fk = '" + this.id + "' ";
				ResultSet rs = db.get(query);
				String ddhID = "";
				while(rs.next())
				{
					ddhID += rs.getString("ddh_fk") + ",";
				}
				rs.close();
				
				if(ddhID.trim().length() > 0)
				{
					this.donhangmuonId = ddhID.substring(0, ddhID.length() - 1);
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
		System.out.println("::: DDKD TOI DAY: " + this.ddkdId);
		
	}
	
	private void initSANPHAM_THEOHD() 
	{
		String query =  
					"select b.MA, b.TEN, DV.donvi, isnull(a.soluong, 0) as soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, a.tungay, a.denngay, a.thueVAT    " +	
					"	,(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018)   as spQuyDoi, a.ddkd_fk "+
					" from ERP_HopDongNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					"where a.HOPDONG_FK = '" + this.hopdongId + "' and b.trangthai = '1' ";
		
		System.out.println("---INIT SP 3: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		NumberFormat formater_2 = new DecimalFormat("##,###,###.####");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spTUNGAY = "";
				String spDENNGAY = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi ="";
				String spTDV ="";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater_2.format(spRs.getDouble("DONGIA")) + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					
					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += " __";
					
					if(spRs.getString("denngay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += " __";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
					spTDV += ( spRs.getString("DDKD_FK") == null ? " " : spRs.getString("DDKD_FK") ) + "__";
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");
					
					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");
					
					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
					
					spTDV = spTDV.substring(0, spTDV.length() - 2);
					this.spTDV = spTDV.split("__");
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
	}
	
	
	private void initSANPHAM_THEO_DONHANGMUON() 
	{
		String query =   "select b.MA, b.TEN, DV.donvi, isnull(a.soluong, 0) as soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, a.tungay, a.denngay, a.thueVAT     " + 
						 "	,(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018)   as spQuyDoi, a.DDKD_FK  " + 
						 "from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ     " + 
						 " 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK        " + 
						 "where a.dondathang_fk in ( " + this.donhangmuonId + " ) and b.trangthai = '1' ";
		
		System.out.println("---INIT SP 4: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		NumberFormat formater_2 = new DecimalFormat("##,###,###.####");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spTUNGAY = "";
				String spDENNGAY = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi = "";
				String spTDV = "";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater_2.format(spRs.getDouble("DONGIA")) + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					
					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += " __";
					
					if(spRs.getString("denngay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += " __";
					
					if(spRs.getString("DDKD_FK") != null )
						spTDV += spRs.getString("DDKD_FK") + "__";
					else
						spTDV += " __";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");
					
					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");
					
					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
					
					spTDV = spTDV.substring(0, spTDV.length() - 2);
					this.spTDV = spTDV.split("__");
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
	}

	public void init() 
	{
		String query = "select mahopdong, trangthai, tungay, denngay, ISNULL(ghichu, '') as ghichu, npp_fk, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, khachhang_fk, kho_fk, isnull(chietkhau, 0) as chietkhau, vat, loaidonhang, hopdong_fk, ngaytrungthau, chiphibaolanh " +
						"from ERP_HopDongNPP where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ma = rs.getString("mahopdong");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.khId = rs.getString("khachhang_fk") == null ? "" : rs.getString("khachhang_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.gsbhId = rs.getString("gsbh_fk") == null ? "" : rs.getString("gsbh_fk") ;
					this.ddkdId = rs.getString("ddkd_fk") == null ? "" : rs.getString("ddkd_fk") ;
					this.trangthai = rs.getString("trangthai");
					this.nppId = rs.getString("npp_fk");
					
					this.ngaytrungthau = rs.getString("ngaytrungthau")== null?"":rs.getString("ngaytrungthau");
					this.chiphibaolanh = rs.getString("chiphibaolanh") == null?"":rs.getString("chiphibaolanh");
					
					if(rs.getString("hopdong_fk") != null)
						this.hopdongId = rs.getString("hopdong_fk");

				}
				rs.close();
				
				//
				query = "select khachhang_fk from ERP_HOPDONGNPP_APDUNG where hopdong_fk = '" + this.id + "'";
				System.out.println("=================== AP DUNG: " + query );
				rs = db.get(query);
				String khApdungId = "";
				while(rs.next())
				{
					khApdungId += rs.getString("khachhang_fk") + ",";
				}
				rs.close();
				
				if(khApdungId.trim().length() > 0)
				{
					khApdungId = khApdungId.substring(0, khApdungId.length() - 1);
					this.khApdungId = khApdungId;
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		System.out.println("::: TDV: " + this.ddkdId );
		this.initSANPHAM();
		
		this.createRs();
		
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			if(kbhRs!=null)
				kbhRs.close();
			if(khApdungRs!=null)
				khApdungRs.close();
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String[] getSpId() {
		
		return this.spId;
	}

	
	public void setSpId(String[] spId) {
		
		this.spId = spId;
	}

	
	public String[] getSpMa() {
		
		return this.spMa;
	}

	
	public void setSpMa(String[] spMa) {
		
		this.spMa = spMa;
	}

	
	public String[] getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String[] spTen) {
		
		this.spTen = spTen;
	}

	
	public String[] getSpDonvi() {
		
		return this.spDonvi;
	}

	
	public void setSpDonvi(String[] spDonvi) {
		
		this.spDonvi = spDonvi;
	}

	
	public String[] getSpSoluong() {
		
		return this.spSoluong;
	}

	
	public void setSpSoluong(String[] spSoluong) {
		
		this.spSoluong = spSoluong;
	}

	
	public String[] getSpGianhap() {
		
		return this.spGianhap;
	}

	
	public void setSpGianhap(String[] spGianhap) {
		
		this.spGianhap = spGianhap;
	}
	
	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getChietkhau() {
		
		return this.chietkhau;
	}

	
	public void setChietkhau(String chietkhau) {
		
		this.chietkhau = chietkhau;
	}

	
	public String getVat() {
		
		System.out.println("---VAT LA: " + this.vat);
		return this.vat;
	}

	
	public void setVat(String vat) {
		
		this.vat = vat;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {
		
		this.dvtRs = dvtRs;
	}

	public String[] getDhck_diengiai() {
		
		return this.dhCk_diengiai;
	}

	
	public void setDhck_Diengiai(String[] obj) {
		
		this.dhCk_diengiai = obj;
	}

	
	public String[] getDhck_giatri() {
		
		return this.dhCk_giatri;
	}

	
	public void setDhck_giatri(String[] obj) {
		
		this.dhCk_giatri = obj;
	}

	
	public String[] getDhck_loai() {
		
		return this.dhCk_loai;
	}

	
	public void setDhck_loai(String[] obj) {
		
		this.dhCk_loai = obj;
	}

	
	public String[] getSpTrongluong() {
		
		return this.spTrongluong;
	}

	
	public void setSpTrongluong(String[] spTrongluong) {
		
		this.spTrongluong = spTrongluong;
	}

	
	public String[] getSpThetich() {
		
		return this.spThetich;
	}

	
	public void setSpThetich(String[] spThetich) {
		
		this.spThetich = spThetich;
	}

	public String[] getSpQuyDoi()
	{
		return spQuyDoi;
	}
	
	public void setSpQuyDoi(String[] spQuyDoi)
	{
		this.spQuyDoi =spQuyDoi;
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

	
	public String[] getSpChietkhau() {
		
		return this.spChietkhau;
	}

	
	public void setSpChietkhau(String[] spChietkhau) {
		
		this.spChietkhau = spChietkhau;
	}

	
	public String[] getSpTungay() {
		
		return this.spTungay;
	}

	
	public void setSpTungay(String[] spTungay) {
		
		this.spTungay = spTungay;
	}

	
	public String[] getSpDenngay() {
		
		return this.spDenngay;
	}

	
	public void setSpDenngay(String[] spDenngay) {
		
		this.spDenngay = spDenngay;
	}

	
	public String getMahopdong() {
		
		return this.ma;
	}

	
	public void setMahopdong(String ma) {
		
		this.ma = ma;
	}

	
	public String getGsbhId() {
		
		return this.gsbhId;
	}

	
	public void setGsbhId(String gsbhId) {
		
		this.gsbhId = gsbhId;
	}

	
	public ResultSet getGsbhRs() {
		
		return this.gsbhRs;
	}

	
	public void setGsbhRs(ResultSet gsbhRs) {
		
		this.gsbhRs = gsbhRs;
	}

	
	public String getDdkdId() {
		
		return this.ddkdId;
	}

	
	public void setDdkdId(String ddkdId) {
		
		this.ddkdId = ddkdId;
	}

	
	public ResultSet getDdkdRs() {
		
		return this.ddkdRs;
	}

	
	public void setDddkdRs(ResultSet ddkdRs) {
		
		this.ddkdRs = ddkdRs;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppId = util.getIdNhapp(this.userId);
			this.nppTen = util.getTenNhaPP();
			this.sitecode = util.getSitecode();
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
	}

	
	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}
	
	public String[] getSpVat() {
		
		return this.spVAT;
	}

	
	public void setSpVat(String[] spVat) {
		
		this.spVAT = spVat;
	}

	
	public String getHopdongId() {
		
		return this.hopdongId;
	}

	
	public void setHopdongId(String hopdongId) {
		
		this.hopdongId = hopdongId;
	}

	
	public ResultSet getHopdongRs() {
		
		return this.hopdongRs;
	}

	
	public void setHopdongRs(ResultSet hopdongRs) {
		
		this.hopdongRs = hopdongRs;
	}


	public String getHopdongchung() {

		return this.hopdongchung;
	}


	public void setHopdongchung(String hopdongchung) {
		
		this.hopdongchung = hopdongchung;
	}

	
	public String getKhApdungId() {
		
		return this.khApdungId;
	}

	
	public void setKhApdungId(String khApdungId) {
		
		this.khApdungId = khApdungId;
	}

	
	public ResultSet getKhApdungRs() {
		
		return this.khApdungRs;
	}

	
	public void setKhApdungRs(ResultSet khApdungRs) {
		
		this.khApdungRs = khApdungRs;
	}

	
	public boolean convertSO() 
	{
		if(this.khId.trim().length() <= 0)
		{
			this.msg = "Bạn phải chọn khách hàng ETC muốn chuyển SO";
			return false;
		}
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " select loaidonhang from ERP_HOPDONGNPP where pk_seq = '" + this.id + "' ";
			ResultSet rs = db.get(query);
			String loaidonhang = "";
			if(rs.next())
			{
				loaidonhang = rs.getString("loaidonhang");
				rs.close();
			}
			
			if(loaidonhang.equals("0")) //Hóa đơn bình thường, chỉ được phép đặt bằng số còn lại
			{
				query = "select count(*) as soDONG  " +
						"from " +
						"( " +
						"	select sanpham_fk, dvdl_fk, sum(soluong) as soluong, avg(dongia) as dongia, avg(chietkhau) as chietkhau, avg(thuevat) as thuevat, tungay, denngay " +
						"	from " +
						"	( " +
						"		select sanpham_fk,  " +
						"			case when a.dvdl_fk IS null then a.soluong       " +
						"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
						"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
						"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.pk_seq as dvdl_fk, tungay, denngay  " +
						"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq  " +
						"		where HOPDONG_FK = '" + this.id + "'  " +
						"	union ALL " +
						"		select sanpham_fk,  " +
						"			case when a.dvdl_fk IS null then a.soluong       " +
						"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
						"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
						"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.pk_seq as dvdl_fk, tungay, denngay  " +
						"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq   " +
						"		where HOPDONG_FK in ( select hopdong_fk from ERP_HOPDONGNPP where hopdong_fk = '" + this.id + "' and trangthai in (1, 2) ) " +
						"	) " +
						"	hopdong group by sanpham_fk, dvdl_fk, tungay, denngay " +
						") " +
						"hd left join " +
						"( " +
						"	select sanpham_fk, sum(soluong) as daDAT " +
						"	from " +
						"	( " +
						"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " +
						"				case when a.dvdl_fk IS null then a.soluong       " +
						"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
						"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
						"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk ) end as soluong  " +
						"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ      " +
						"		where a.dondathang_fk in (   select pk_seq from ERP_DondathangNPP where trangthai != '3' and hopdong_fk = '" + this.id + "'  )     " +
						"	) " +
						"	dathang group by sanpham_fk " +
						") " +
						"dh on hd.sanpham_fk = dh.sanpham_fk " +
						"where hd.soluong > isnull(dh.daDAT, 0) ";  //KHONG CON SP NAO
				
				System.out.println("----CHECK SANPHAM: " + query );
				rs = db.get(query);
				int soDONG = 0;
				if(rs.next())
				{
					soDONG = rs.getInt("soDONG");
				}
				rs.close();
				
				if(soDONG <= 0)
				{
					msg = "Hợp đồng đã chuyển hết thành SO. Bạn không thể chuyển tiếp.";
					db.getConnection().rollback();
					return false;
				}
			}
				
			query = "update ERP_HOPDONGNPP set trangthai = '2' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " insert ERP_DondathangNPP(ngaydonhang, ngaydenghi, loaidonhang, npp_dachot, ghichu, trangthai, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, khachhang_fk, npp_fk, kho_fk, hopdong_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
					" select tungay, denngay, 0, 1 as npp_dachot, ghichu, 0 as trangthai, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, '" + this.khId + "' as khachhang_fk, npp_fk, kho_fk, pk_seq, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua " +
					" from ERP_HOPDONGNPP where pk_seq = '" + this.id + "' ";
			System.out.println("-- INSERT DDH: " + query );
			if(!db.update(query))
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_DondathangNPP') as ID ");
			if(rsDDH.next())
			{
				msg = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			/*query = "insert ERP_DondathangNPP_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay ) " +
					"select '" + msg + "', sanpham_fk, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay " +
					"from ERP_HOPDONGNPP_SANPHAM where HOPDONG_FK = '" + lsxId + "' ";*/
			
			String sqlSOLUONG = " hd.soluong - isnull(dh.daDAT, 0) ";
			if(loaidonhang.equals("2")) //Hợp đồng nguyên tắc
				sqlSOLUONG = " isnull(hd.soluong, 0) ";
			
			query = "insert ERP_DondathangNPP_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay ) " +
					"select '" + msg + "', hd.sanpham_fk, " + sqlSOLUONG + ", hd.dongia, hd.chietkhau, hd.thueVAT, hd.dvdl_fk, hd.tungay, hd.denngay " +
					"from " +
					"( " +
					"	select sanpham_fk, dvdl_fk, sum(soluong) as soluong, avg(dongia) as dongia, avg(chietkhau) as chietkhau, avg(thuevat) as thuevat, tungay, denngay " +
					"	from " +
					"	( " +
					"		select sanpham_fk,  " +
					"			case when a.dvdl_fk IS null then a.soluong       " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
					"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.dvdl_fk as dvdl_fk, tungay, denngay  " +
					"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq  " +
					"		where HOPDONG_FK = '" + this.id + "'  " +
					"	union ALL " +
					"		select sanpham_fk,  " +
					"			case when a.dvdl_fk IS null then a.soluong       " +
					"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
					"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.dvdl_fk as dvdl_fk, tungay, denngay  " +
					"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq   " +
					"		where HOPDONG_FK in ( select hopdong_fk from ERP_HOPDONGNPP where hopdong_fk = '" + this.id + "' and trangthai in (1, 2) ) " +
					"	) " +
					"	hopdong group by sanpham_fk, dvdl_fk, tungay, denngay " +
					") " +
					"hd left join " +
					"( " +
					"	select sanpham_fk, sum(soluong) as daDAT " +
					"	from " +
					"	( " +
					"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " +
					"				case when a.dvdl_fk IS null then a.soluong       " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
					"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk ) end as soluong  " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ      " +
					"		where a.dondathang_fk in (   select pk_seq from ERP_DondathangNPP where trangthai != '3' and hopdong_fk = '" + this.id + "'  )     " +
					"	) " +
					"	dathang group by sanpham_fk " +
					") " +
					"dh on hd.sanpham_fk = dh.sanpham_fk ";
			
			if(!loaidonhang.equals("2"))
					query += "where hd.soluong > isnull(dh.daDAT, 0) ";
			
			System.out.println("--CHEN SP: " + query);
			if(!db.update(query))
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert ERP_DONDATHANGNPP_CHIETKHAU(DONDATHANG_FK, DIENGIAI, GIATRI, LOAI) " +
					"select '" + msg + "', DIENGIAI, GIATRI, LOAI from ERP_HOPDONGNPP_CHIETKHAU where hopdong_fk = '" + this.id + "' ";
			System.out.println("--CHEN SP 2: " + query);
			if(!db.update(query))
			{
				msg = "Lỗi khi chuyển sang đơn đặt hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Lỗi: " + e.getMessage();
		}
		
		return true;
	}

	
	public String getNgaytrungthau() {
		
		return this.ngaytrungthau;
	}

	
	public void setNgaytrungthau(String ngaytrungthau) {
		
		this.ngaytrungthau = ngaytrungthau;
	}

	
	public String getChiphibaolanh() {
		
		return this.chiphibaolanh;
	}

	
	public void setChiphibaolanh(String chiphibaolanh) {
		
		this.chiphibaolanh = chiphibaolanh;
	}


	public void initDisplay() {
		
		String query = "select mahopdong, trangthai, tungay, denngay, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, khachhang_fk, kho_fk, isnull(chietkhau, 0) as chietkhau, vat, loaidonhang, hopdong_fk, ngaytrungthau, chiphibaolanh " +
						"from ERP_HopDongNPP where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ma = rs.getString("mahopdong");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.khId = rs.getString("khachhang_fk") == null ? "" : rs.getString("khachhang_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.gsbhId = rs.getString("gsbh_fk") == null ? "" : rs.getString("gsbh_fk");
					this.ddkdId = rs.getString("ddkd_fk") == null ? "" : rs.getString("ddkd_fk");
					this.trangthai = rs.getString("trangthai");
					
					this.ngaytrungthau = rs.getString("ngaytrungthau");
					this.chiphibaolanh = rs.getString("chiphibaolanh");
					
					if(rs.getString("hopdong_fk") != null)
						this.hopdongId = rs.getString("hopdong_fk");
		
				}
				rs.close();
				
				//
				query = "select khachhang_fk from ERP_HOPDONGNPP_APDUNG where hopdong_fk = '" + this.id + "'";
				System.out.println("=================== AP DUNG: " + query );
				rs = db.get(query);
				String khApdungId = "";
				while(rs.next())
				{
					khApdungId += rs.getString("khachhang_fk") + ",";
				}
				rs.close();
				
				if(khApdungId.trim().length() > 0)
				{
					khApdungId = khApdungId.substring(0, khApdungId.length() - 1);
					this.khApdungId = khApdungId;
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		
		this.initSANPHAM_Display();
		
		this.createRs();
	}
	
	private void initSANPHAM_Display() 
	{
		String query =   "select b.MA, b.TEN, DV.donvi,  " + 
						 "	isnull(a.soluong, 0) as soluong,  " + 
						 "	isnull( ( " + 
						 "		select SUM(ddh_sp.soluong) as soluong  " + 
						 "		from ERP_DONDATHANGNPP ddh inner join ERP_DONDATHANGNPP_SANPHAM ddh_sp on ddh.pk_seq = ddh_sp.dondathang_fk " + 
						 "		where HOPDONG_FK = a.HOPDONG_FK and ddh.TRANGTHAI != 3 and ddh_sp.sanpham_fk = b.pk_seq " + 
						 "	), 0 ) as dagiao, " + 
						 "	a.dongia, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, a.tungay, a.denngay, a.thueVAT      " + 
						 "	,(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018)   as spQuyDoi, a.ddkd_fk  " + 
						 "from ERP_HopDongNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ      " + 
						 " 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK         " + 
						 "where a.HOPDONG_FK = '" + this.id + "' and b.trangthai = '1' ";
		
		System.out.println("---INIT SP 1: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spTUNGAY = "";
				String spDENNGAY = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi ="";
				String spTDV ="";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "-" + formater.format(spRs.getDouble("dagiao")) + "__";
					spGIANHAP += spRs.getDouble("DONGIA") + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					
					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += " __";
					
					if(spRs.getString("tungay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += " __";
					
					if(spRs.getString("ddkd_fk") != null )
						spTDV += spRs.getString("ddkd_fk") + "__";
					else
						spTDV += ( this.ddkdId.trim().length() <= 0 ? " " : this.ddkdId ) + "__";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");
					
					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");
					
					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
					
					spTDV = spTDV.substring(0, spTDV.length() - 2);
					this.spTDV = spTDV.split("__");
				}
				
				//INIT CHIET KHAU
				query = "select DIENGIAI, GIATRI, LOAI from ERP_HOPDONGNPP_CHIETKHAU where HOPDONG_FK = '" + this.id + "'";
				System.out.println("[INIT_CK]"+query);
				ResultSet rsCK = db.get(query);
				if(rsCK != null)
				{
					String dkCK_diengiai = "";
					String dkCK_giatri = "";
					String dkCK_loai = "";
					
					while(rsCK.next())
					{
						dkCK_diengiai += rsCK.getString("DIENGIAI") + "__";
						dkCK_giatri += formater.format(rsCK.getDouble("GIATRI")) + "__";
						dkCK_loai += rsCK.getString("LOAI") + "__";
					}
					rsCK.close();
					
					if(dkCK_diengiai.trim().length() > 0)
					{
						dkCK_diengiai = dkCK_diengiai.substring(0, dkCK_diengiai.length() - 2);
						this.dhCk_diengiai = dkCK_diengiai.split("__");
						
						dkCK_giatri = dkCK_giatri.substring(0, dkCK_giatri.length() - 2);
						this.dhCk_giatri = dkCK_giatri.split("__");
						
						dkCK_loai = dkCK_loai.substring(0, dkCK_loai.length() - 2);
						this.dhCk_loai = dkCK_loai.split("__");
					}
					
				}
				
				//INIT DDH
				query = "select ddh_fk from ERP_HOPDONGNPP_DDH where hopdong_fk = '" + this.id + "' ";
				ResultSet rs = db.get(query);
				String ddhID = "";
				while(rs.next())
				{
					ddhID += rs.getString("ddh_fk") + ",";
				}
				rs.close();
				
				if(ddhID.trim().length() > 0)
				{
					this.donhangmuonId = ddhID.substring(0, ddhID.length() - 1);
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
	}

	
	public String[] getSpTDV() {
		
		return this.spTDV;
	}

	
	public void setSpTDV(String[] spTDV) {
		
		this.spTDV = spTDV;
	}

	
	public String getDonhangmuonIds() {
		
		return this.donhangmuonId;
	}

	
	public void setDonangmuonIds(String dhmuonIds) {
		
		this.donhangmuonId = dhmuonIds;
	}

	
	public ResultSet getDonhangmuonRs() {
		
		return this.donhangmuonRs;
	}

	
	public void setDonhangmuonRs(ResultSet donhangmuonRs) {
		
		this.donhangmuonRs = donhangmuonRs;
	}

	@Override
	public String getHieuLuc() {		
		return this.hieuLuc;
	}

	@Override
	public void setHieuLuc(String hieuLuc) {
		this.hieuLuc = hieuLuc;
	}
	
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}

	
	public String getCapnhatTDV() 
	{
		return this.capnhatTDV;
	}


	public void setCapnhatTDV(String capnhatTDV) 
	{
		this.capnhatTDV = capnhatTDV;
	}
	
	public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}

}

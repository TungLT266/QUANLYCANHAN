package geso.traphaco.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTL;
import geso.traphaco.center.db.sql.dbutils;

public class TieuchithuongTL implements ITieuchithuongTL 
{
	String userId;
	String id;
	String scheme;
	String thang;
	String nam;
	String diengiai;
	String kt = "0";
	ResultSet sanphamRs;
	String spIds;
	ResultSet nppRs;
	String nppIds;
	String nppIds1 = "";
	String nppIds2 = "";
	String nppIds3 = "";
	String nppIds4 = "";
	String nppIds5 = "";
	String active = "0";
	ResultSet kenhRs;
	String kenhIds;
	ResultSet vungRs;
	String vungIds;
	ResultSet kvRs;
	String kvIds;
	String mucnpp = "0";
	ResultSet nhomsanphamRs;
	String nhomspIds;
	
	String[] diengiaiMuc;
	String ghighu = "";
	String [] httt1;
	String[] tumuc;
	String[] denmuc;
	String[] thuongSR;
	String[] thuongTDSR;
	String[] thuongSS;
	String[] thuongTDSS;
	String[] thuongASM;
	String[] thuongTDASM;
	
	String[] diengiaiMuc3;
	String[] tumuc3;
	String[] denmuc3;
	String[] thuongSR3;
	String[] thuongTDSR3;
	String[] thuongSS3;
	String[] thuongTDSS3;
	String[] thuongASM3;
	String[] thuongTDASM3;
	
	String mucvuot;
	String ckMucvuot;
	String dvMucvuot;
	
	
	String hinhthucTra;
	String[] maspTra;
	String[] tenspTra;
	String[] isspTra;
	String[] soluongspTra;
	String[] dongiaspTra;
	
	String doanhsotheoThung;
	String msg;

	String httt;
	String ptchietkhau;
	
	Hashtable<String, String> muc_tiendo;
	Hashtable<String, String> muc_spTRA;
	Hashtable<String, String> phanbo;
	Hashtable<String, String> phanbo1 = new Hashtable<String, String>();
	Hashtable<String, String> phanbo2 = new Hashtable<String, String>();
	Hashtable<String, String> phanbo3 = new Hashtable<String, String>();
	Hashtable<String, String> phanbo4 = new Hashtable<String, String>();
	Hashtable<String, String> phanbo5 = new Hashtable<String, String>();
	
	Hashtable<String, String> dieukien;
	Hashtable<String, String> quydoi;
	
	String tungay;
	String dengay;
	String khoId;
	ResultSet khoRs;
	
	String phanloai;
	
	dbutils db;
	
	String[] DKKMTICHLUY_KHACHHANG_Id;	
	String[] khDcDuyet;
	String[] khDcDuyetDisplay;
	ResultSet khDangKyRs;
	
	String ngaytb_tungay;
	String ngaytb_dengay;
	
	String phaidangky;
	String chiavaodongia;
	
	String khoanmuccpId;
	ResultSet khoanmuccpRs;
	
	String khongcanduyet;
	int giobatdau;
	int gioketthuc;
	String soxuattoida;
	
	String kbhIds;
	
	ResultSet loaiKhRs;
	String loaiKhIds;
	
	public TieuchithuongTL()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		
		this.mucvuot = "";
		this.ckMucvuot = "";
		this.dvMucvuot = "";
		
		this.vungIds = "";
		this.kvIds = "";
		this.kenhIds = "";
		this.hinhthucTra = "0";		
		
		this.doanhsotheoThung = "0";
		this.httt = "0";
		this.ptchietkhau = "0";
	
		this.muc_tiendo = new Hashtable<String, String>();
		this.muc_spTRA = new Hashtable<String, String>();
		this.phanbo = new Hashtable<String, String>();
		this.phanbo1 = new Hashtable<String, String>();
		this.phanbo2 = new Hashtable<String, String>();
		this.phanbo3 = new Hashtable<String, String>();
		this.phanbo4 = new Hashtable<String, String>();
		this.phanbo5 = new Hashtable<String, String>();
		
		this.dieukien = new Hashtable<String, String>();
		this.quydoi = new Hashtable<String, String>();
		 nppIds1 = "";
		 nppIds2 = "";
		 nppIds3 = "";
		 nppIds4 = "";
		 nppIds5 = "";
		this.tungay = "";
		this.dengay = "";
		this.khoId = "";
		this.phanloai = "0";
		this.nhomspIds = "";
		this.mucnpp = "0";
		this.phaidangky = "0";
		
		this.ngaytb_tungay = "";
		this.ngaytb_dengay = "";
		this.chiavaodongia = "1";
		this.khoanmuccpId = "";
		
		this.khongcanduyet = "0";
		this.giobatdau = 0;
		this.gioketthuc = 24;
		this.soxuattoida = "";
		
		this.kbhIds = "";
		this.loaiKhIds = "";
		
		this.db = new dbutils();
	}
	
	public TieuchithuongTL(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		nppIds1 = "";
		 nppIds2 = "";
		 nppIds3 = "";
		 nppIds4 = "";
		 nppIds5 = "";
		this.mucvuot = "";
		this.ckMucvuot = "";
		this.dvMucvuot = "";
		
		this.vungIds = "";
		this.kvIds = "";
		this.kenhIds = "";
		this.hinhthucTra = "0";	
		
		this.doanhsotheoThung = "0";
		this.httt = "0";
		this.ptchietkhau = "0";
		
		this.muc_tiendo = new Hashtable<String, String>();
		this.muc_spTRA = new Hashtable<String, String>();
		this.phanbo = new Hashtable<String, String>();
		this.phanbo1 = new Hashtable<String, String>();
		this.phanbo2 = new Hashtable<String, String>();
		this.phanbo3 = new Hashtable<String, String>();
		this.phanbo4 = new Hashtable<String, String>();
		this.phanbo5 = new Hashtable<String, String>();
		this.mucnpp = "0";
		this.dieukien = new Hashtable<String, String>();
		this.quydoi = new Hashtable<String, String>();
		
		this.tungay = "";
		this.dengay = "";
		this.khoId = "";
		this.phanloai = "0";
		this.nhomspIds = "";
		this.phaidangky = "0";
		
		this.ngaytb_tungay = "";
		this.ngaytb_dengay = "";
		this.chiavaodongia = "1";
		this.khoanmuccpId = "";
		
		this.khongcanduyet = "0";
		this.giobatdau = 0;
		this.gioketthuc = 24;
		this.soxuattoida = "";
		
		this.kbhIds = "";
		this.loaiKhIds = "";
		
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

	
	public boolean createTctSKU( ) 
	{
		try
		{
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày bắt đầu";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc";
				return false;
			}
			
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}
			
			//Check Scheme
			String query = "select count(*) as sodong from TIEUCHITHUONGTL where scheme = N'" + this.scheme + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			//Check tieu chi
			if(this.diengiaiMuc == null)
			{
				this.msg = "Vui lòng nhập tiêu chí ";
				return false;
			}
			else
			{
				boolean flag = false;
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						flag = true;
					}
				}
				
				if(!flag)
				{
					this.msg = "Vui lòng kiểm tra lại các tiêu chí";
					return false;
				}
			}
			
			/*if(this.phanloai.equals("0"))
			{*/
				if(this.maspTra.length <= 0)
				{
					this.msg = "Vui lòng chọn sản phẩm trong điều kiện";
					return false;
				}
			/*}
			else
			{
				if(this.spIds.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn sản phẩm trong điều kiện";
					return false;
				}
			}*/
			
			/*if(this.khoId.trim().length() <= 0 && !this.phanloai.equals("3") )
			{
				this.msg = "Vui lòng chọn kho áp dụng";
				return false;
			}*/
			
			db.getConnection().setAutoCommit(false);
			
			String mucvuot = this.mucvuot.trim().length() <= 0 ? "null" : this.mucvuot.replaceAll(",", "");
			String ckMucvuot = this.ckMucvuot.trim().length() <= 0 ? "null" : this.ckMucvuot.replaceAll(",", "") ;
			String kmcp_fk = this.khoanmuccpId.trim().length() <= 0 ? "NULL" : this.khoanmuccpId;
			String soxuatToiDa = this.soxuattoida.trim().length() <= 0 ? "NULL" : this.soxuattoida;
			String kho_fk = this.khoId.trim().length() <= 0 ? "null" : this.khoId;
			
			query = "insert TieuchithuongTL(scheme, thang, nam, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, mucvuot, chietkhauMucVuot, donviMucVuot, hinhthuctra, DOANHSOTHEOTHUNG, HTTT, PT_TRATL, ngayds_tungay, ngayds_denngay, khoId, phanloai ,ghichu, ngaytb_tungay, ngaytb_denngay, batbuocdangky, chiavaodongia, kmcp_fk, giobatdau, gioketthuc, khongcanduyettra, soxuattoida ) " +
					"values(N'" + this.scheme + "', '" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', " +
							"'" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', " + mucvuot + ", " + ckMucvuot + ", '" + this.dvMucvuot + "', '1', '" + this.doanhsotheoThung + "', '" + this.httt + "', '" + this.ptchietkhau + "', '" + this.tungay + "', '" + this.dengay + "', " + kho_fk + ", '" + this.phanloai + "',N'" + this.ghighu + "', '" + this.ngaytb_tungay + "', '" + this.ngaytb_dengay + "', '" + this.phaidangky + "', '" + this.chiavaodongia + "', " + kmcp_fk + ", '" + this.giobatdau + "', '" + this.gioketthuc + "', '" + this.khongcanduyet + "', " + soxuatToiDa + " )";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi TieuchithuongTL: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select IDENT_CURRENT('TieuchithuongTL') as tctId";
			rs = db.get(query);
			rs.next();
			this.id = rs.getString("tctId");
			rs.close();
			
			//NẾU LÀ TÍCH LŨY GIAI ĐOẠN THÌ CHÈN THÊM 1 SCHEME, ĐỂ BÊN DƯỚI ÁP KM KHÔNG ẢNH HƯỞNG GÌ
			if( this.phanloai.equals("3") )
			{
				query = " Insert CTKHUYENMAI( scheme, tungay, denngay, thuongtl_fk ) " + 
						" select scheme, thang, nam, pk_seq from TieuchithuongTL where pk_seq = '" + this.id + "' ";
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi CTKHUYENMAI: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.diengiaiMuc != null)
			{
				String sql = "";
				
				if( !this.phanloai.equals("3") )
				{
					for(int i = 0; i < this.diengiaiMuc.length; i++)
					{
						//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
						if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0 )
						{
							/*String ck = this.thuongSR[i].replaceAll(",", "");
							if(this.thuongTDSR[i].trim().equals("2"))
								ck = "null";*/
							
							sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiai, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
									" '" + this.thuongSR[i].replaceAll(",", "") + "' as chietkhau, N'" + this.thuongTDSR[i] + "' as donvi, '" + i + "' as muc ,'"+this.httt1[i]+"' as mucphanbo union ";
							
							//LUU VAO BANG TIEN DO
							if(this.muc_tiendo.get(Integer.toString(i)) != null )
							{
								String infoDETAIL = this.muc_tiendo.get( Integer.toString(i) );
								if(infoDETAIL.trim().length() > 0)
	                			{ 
	                				String[] info = infoDETAIL.split("__");
	                				for(int k = 0; k < info.length; k++ )
	                				{
	                					query = "insert TIEUCHITHUONGTL_TIENDO( thuongtl_fk, muc, tiendo, tungay, denngay, phaidat ) "
	                							+ " values ( '" + this.id + "', '" + i + "', '" + k + "', '" + info[k].split("_")[0] + "', '" + info[k].split("_")[1] + "', '" + info[k].split("_")[2].replaceAll(",", "") + "' )";
	                					if(!db.update(query))
	                					{
	                						db.getConnection().rollback();
	                						this.msg = "Khong the tao moi 'TIEUCHITHUONGTL_TIENDO', " + query;
	                						return false; 
	                					}
	                				}
	                			}
							}
						}
					}
					
					if(sql.trim().length() > 0)
					{
						sql = sql.substring(0, sql.length() - 6);
						
						query = "insert TieuchithuongTL_TIEUCHI(thuongtl_fk, ghichu, tumuc, denmuc, chietkhau, donvi, muc,mucphanbo) " +
								"select '" + this.id + "', tieuchi.* from (" + sql + ") tieuchi ";
						
						System.out.println("2.Insert TieuchithuongTL_TIEUCHI: " + query);
						if(!db.update(query))
						{
							this.msg = "2.Khong the tao moi TieuchithuongTL_TIEUCHI: " + query;
							db.getConnection().rollback();
							return false;
						}
					}	
				}
				else  //TICH LUY THEO GIAI DOAN
				{
					for(int i = 0; i < this.diengiaiMuc.length; i++)
					{
						//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
						if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0
								&& this.thuongSR[i].trim().length() > 0 && this.thuongASM[i].trim().length() > 0 && this.thuongSS[i].trim().length() > 0 && this.thuongTDSS[i].trim().length() > 0  )
						{
							sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiai, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
									" '" + this.thuongSR[i].replaceAll(",", "") + "' as chietkhau, N'" + this.thuongTDSR[i] + "' as donvi, '" + i + "' as muc, '1' as mucphanbo, '" + this.thuongASM[i] + "' as soxuattoida, '" + this.thuongSS[i] + "' as tungay, '" + this.thuongTDSS[i] + "' as denngay union ";
						}
					}
					
					if(sql.trim().length() > 0)
					{
						sql = sql.substring(0, sql.length() - 6);
						
						query = "insert TieuchithuongTL_TIEUCHI(thuongtl_fk, ghichu, tumuc, denmuc, chietkhau, donvi, muc, mucphanbo, soxuatToiDa, tungay, denngay ) " +
								"select '" + this.id + "', tieuchi.* from (" + sql + ") tieuchi ";
						
						System.out.println("2.Insert TieuchithuongTL_TIEUCHI: " + query);
						if(!db.update(query))
						{
							this.msg = "2.Khong the tao moi TieuchithuongTL_TIEUCHI: " + query;
							db.getConnection().rollback();
							return false;
						}
					}	
				}
			}
			
			/*if(this.spIds.trim().length() > 0)
			{*/
				if( !this.phanloai.equals("1")  )
				{
					for(int i = 0; i < this.maspTra.length; i ++)
					{
						/*query = "Insert TIEUCHITHUONGTL_SANPHAM(thuongtl_fk, sanpham_fk) " +
								"select '" + this.id + "', pk_seq from SanPham where pk_seq in (" + this.spIds + ") ";*/
						
						if(this.maspTra[i].trim().length() > 0)
						{
							query = "Insert TIEUCHITHUONGTL_SANPHAM(thuongtl_fk, sanpham_fk) " +
									"select '" + this.id + "', pk_seq from SanPham where ma = N'" + this.maspTra[i] + "' ";
							
							System.out.println("3.Insert: " + query);
							if(!db.update(query))
							{
								this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
				else
				{
					for(int i = 0; i < this.maspTra.length; i++)
					{
						String _dieukien = "NULL";
						String _dvDieukien = "NULL";
						if(this.dieukien.get(this.maspTra[i]) != null)
						{
							_dieukien = this.dieukien.get(this.maspTra[i]).split("__")[0].replaceAll(",", "");
							_dvDieukien = this.dieukien.get(this.maspTra[i]).split("__")[1];
						}
						
						String _quydoi = "NULL";
						String _dvQuydoi = "NULL";
						if(this.quydoi.get(this.maspTra[i]) != null)
						{
							_quydoi = this.quydoi.get(this.maspTra[i]).split("__")[0].replaceAll(",", "");
							_dvQuydoi = this.quydoi.get(this.maspTra[i]).split("__")[1];
						}
					
						if(!_dieukien.equals("NULL") || !_quydoi.equals("NULL"))
						{
							query = "Insert TieuchithuongTL_SANPHAM(thuongtl_fk, sanpham_fk, dieukien, donviDIEUKIEN, quydoi, donviQUYDOI) " +
									"select '" + this.id + "', pk_seq, " + _dieukien + ", " + _dvDieukien + ", " + _quydoi + ", " + _dvQuydoi + " from SanPham where ma = N'" + this.maspTra[i] + "' ";
							
							System.out.println("3.Insert SP: " + query);
							if(!db.update(query))
							{
								this.msg = "3.Khong the tao moi TieuchithuongTL_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			//}
				
			if(this.kbhIds.trim().length() > 0)
			{
				query = "Insert TieuchithuongTL_KBH(thuongtl_fk, kbh_fk)  " +
						"select '" + this.id + "', pk_seq from KenhBanHang where pk_seq > 0  ";
		    	if(this.kbhIds.trim().length() > 0)
		    		query += " and pk_seq in ( " + this.kbhIds + " ) ";
		    	
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "4.Khong the tao moi 'TieuchithuongTL_KBH', " + query;
					return false; 
				}
			}
			
			if(this.loaiKhIds.trim().length() > 0)
			{
				query = "Insert TieuchithuongTL_LOAIKH(thuongtl_fk, LOAIKH_Fk)  " +
						"select '" + this.id + "', pk_seq from LOAICUAHANG where pk_seq >= 0  ";
		    	if(this.loaiKhIds.trim().length() > 0)
		    		query += " and pk_seq in ( " + this.loaiKhIds + " ) ";
		    	
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "4.Khong the tao moi 'TieuchithuongTL_LOAIKH', " + query;
					return false; 
				}
			}
				
				
			// chen phan bo nha phan phoi theo muc
			if(this.nppIds1.trim().length() > 0)
			{
				query = "Insert TIEUCHITHUONGTL_NPP(thuongtl_fk, npp_fk,muc) " +
						"select '" + this.id + "', pk_seq,'0' from NHAPHANPHOI where pk_seq in (" + this.nppIds1 + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				//CAP NHAT PHAN BO
				if(this.phanbo1.size() > 0)
				{
					Enumeration<String> keys = this.phanbo1.keys();
					while(keys.hasMoreElements())
					{
						String key = keys.nextElement();
						
						query = "update TIEUCHITHUONGTL_NPP set soluong = '" + phanbo1.get(key).replaceAll(",", "") + "' " + 
								" where thuongtl_fk = '" + this.id + "' and npp_fk = '" + key + "' ";
						if(!db.update(query))
						{
							this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			if(this.nppIds2.trim().length() > 0)
			{
				query = "Insert TIEUCHITHUONGTL_NPP(thuongtl_fk, npp_fk,muc) " +
						"select '" + this.id + "', pk_seq,'1' from NHAPHANPHOI where pk_seq in (" + this.nppIds2 + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				//CAP NHAT PHAN BO
				if(this.phanbo2.size() > 0)
				{
					Enumeration<String> keys = this.phanbo2.keys();
					while(keys.hasMoreElements())
					{
						String key = keys.nextElement();
						
						query = "update TIEUCHITHUONGTL_NPP set soluong = '" + phanbo2.get(key).replaceAll(",", "") + "' " + 
								" where thuongtl_fk = '" + this.id + "' and npp_fk = '" + key + "' ";
						if(!db.update(query))
						{
							this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			if(this.nppIds3.trim().length() > 0)
			{
				query = "Insert TIEUCHITHUONGTL_NPP(thuongtl_fk, npp_fk,muc) " +
						"select '" + this.id + "', pk_seq,'2' from NHAPHANPHOI where pk_seq in (" + this.nppIds3 + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				//CAP NHAT PHAN BO
				if(this.phanbo3.size() > 0)
				{
					Enumeration<String> keys = this.phanbo3.keys();
					while(keys.hasMoreElements())
					{
						String key = keys.nextElement();
						
						query = "update TIEUCHITHUONGTL_NPP set soluong = '" + phanbo3.get(key).replaceAll(",", "") + "' " + 
								" where thuongtl_fk = '" + this.id + "' and npp_fk = '" + key + "' ";
						if(!db.update(query))
						{
							this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			if(this.nppIds4.trim().length() > 0)
			{
				query = "Insert TIEUCHITHUONGTL_NPP(thuongtl_fk, npp_fk,muc) " +
						"select '" + this.id + "', pk_seq,'3' from NHAPHANPHOI where pk_seq in (" + this.nppIds4 + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				//CAP NHAT PHAN BO
				if(this.phanbo4.size() > 0)
				{
					Enumeration<String> keys = this.phanbo4.keys();
					while(keys.hasMoreElements())
					{
						String key = keys.nextElement();
						
						query = "update TIEUCHITHUONGTL_NPP set soluong = '" + phanbo4.get(key).replaceAll(",", "") + "' " + 
								" where thuongtl_fk = '" + this.id + "' and npp_fk = '" + key + "' ";
						if(!db.update(query))
						{
							this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			if(this.nppIds5.trim().length() > 0)
			{
				query = "Insert TIEUCHITHUONGTL_NPP(thuongtl_fk, npp_fk,muc) " +
						"select '" + this.id + "', pk_seq,'4' from NHAPHANPHOI where pk_seq in (" + this.nppIds5 + ") ";
				
				System.out.println("4.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				//CAP NHAT PHAN BO
				if(this.phanbo5.size() > 0)
				{
					Enumeration<String> keys = this.phanbo5.keys();
					while(keys.hasMoreElements())
					{
						String key = keys.nextElement();
						
						query = "update TIEUCHITHUONGTL_NPP set soluong = '" + phanbo5.get(key).replaceAll(",", "") + "' " + 
								" where thuongtl_fk = '" + this.id + "' and npp_fk = '" + key + "' ";
						if(!db.update(query))
						{
							this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			//CHEN TICH LUY THEO MUC
			for(int i = 0; i < 5; i++)
			{
				if( this.muc_spTRA.get( Integer.toString(i) ) != null )
    			{
    				String infoDETAIL_SPTRA = this.muc_spTRA.get( Integer.toString(i) );
    				String infoDETAIL_SPTRA_HT = infoDETAIL_SPTRA.split("__")[0];
    				
    				if(infoDETAIL_SPTRA.trim().length() > 0)
        			{ 
        				String[] info = infoDETAIL_SPTRA.split("__")[1].split(";;");
        				for(int k = 0; k < info.length; k++ )
        				{
        					String[] info2 = info[k].split("_");
        					
        					query = "insert TIEUCHITHUONGTL_SPTRA( thuongtl_fk, sanpham_fk, soluong, dongia, muctra, hinhthuctra ) " + 
        							" select '" + this.id + "', pk_seq, '" + info2[2] + "', 0, '" + i + "', '" + infoDETAIL_SPTRA_HT + "' from SANPHAM where MA = '" + info2[0] + "' ";
        					if(!db.update(query))
        					{
        						this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_SPTRA: " + query;
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
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
				e.printStackTrace();
				this.msg = "Lỗi khi tạo mới CT tích lũy: " + e.getMessage();
			} 
			catch (SQLException e1) {}
			
			return false;
		}
		
		return true;
	}

	public boolean updateTctSKU()
	{
		try
		{
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày bắt đầu";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc";
				return false;
			}
			
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}
			
			//Check Scheme
			String query = "select count(*) as sodong from TIEUCHITHUONGTL where scheme = N'" + this.scheme + "' and pk_seq != '" + this.id + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			//Check tieu chi
			if(this.diengiaiMuc == null)
			{
				this.msg = "Vui lòng nhập tiêu chí ";
				return false;
			}
			else
			{
				boolean flag = false;
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						flag = true;
					}
				}
				
				if(!flag)
				{
					this.msg = "Vui lòng kiểm tra lại các tiêu chí";
					return false;
				}
			}
			
			/*if(this.phanloai.equals("0"))
			{*/
				if(this.maspTra.length <= 0)
				{
					this.msg = "Vui lòng chọn sản phẩm trong điều kiện";
					return false;
				}
			/*}
			else
			{
				if(this.spIds.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn sản phẩm trong điều kiện";
					return false;
				}
			}*/
			
			/*if( this.khoId.trim().length() <= 0 && !this.phanloai.equals("3") )
			{
				this.msg = "Vui lòng chọn kho áp dụng";
				return false;
			}*/
			
			db.getConnection().setAutoCommit(false);
			
			String mucvuot = this.mucvuot.trim().length() <= 0 ? "null" : this.mucvuot.replaceAll(",", "");
			String ckMucvuot = this.ckMucvuot.trim().length() <= 0 ? "null" : this.ckMucvuot.replaceAll(",", "") ;
			String kmcp_fk = this.khoanmuccpId.trim().length() <= 0 ? "NULL" : this.khoanmuccpId;
			String soxuatToiDa = this.soxuattoida.trim().length() <= 0 ? "NULL" : this.soxuattoida;
			String kho_fk = this.khoId.trim().length() <= 0 ? "null" : this.khoId;
			
			query = "update TieuchithuongTL set scheme = N'" + this.scheme + "', thang = '" + this.thang + "', nam = '" + this.nam + "', diengiai = N'" + this.diengiai + "', " +
						" ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', mucvuot = " + mucvuot + ", chietkhauMucVuot = " + ckMucvuot + ", donviMucVuot = '" + this.dvMucvuot + "', hinhthuctra = '0', DOANHSOTHEOTHUNG = '" + this.doanhsotheoThung + "', HTTT = '" + this.httt + "', PT_TRATL = '" + this.ptchietkhau + "', " + 
						" ngayds_tungay = '" + this.tungay + "', ngayds_denngay = '" + this.dengay + "', khoId = " + kho_fk + ", ghichu = N'"+this.ghighu+"', ngaytb_tungay = '" + this.ngaytb_tungay + "', ngaytb_denngay = '" + this.ngaytb_dengay + "', batbuocdangky = '" + this.phaidangky + "', chiavaodongia = '" + this.chiavaodongia + "', kmcp_fk = " + kmcp_fk + ", giobatdau = '" + this.giobatdau + "', gioketthuc = '" + this.gioketthuc + "', khongcanduyettra = '" + this.khongcanduyet + "', soxuatToiDa = " + soxuatToiDa + " " +
					"where pk_seq = '" + this.id + "'";
					
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTL: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( this.phanloai.equals("3") )
			{
				query = " update CTKHUYENMAI set scheme = N'" + scheme + "', tungay = '" + this.tungay + "', denngay = '" + this.dengay + "' where thuongtl_fk = '" + this.id + "' "; 
				if(!db.update(query))
				{
					this.msg = "Khong the cap nhat CTKHUYENMAI: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			query = "delete TieuchithuongTL_TIEUCHI where thuongtl_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTL_TIEUCHI: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TIEUCHITHUONGTL_MUCTHUONG where thuongtl_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGTL_MUCTHUONG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGTL_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGTL_NPP: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TIEUCHITHUONGTL_TIENDO where thuongtl_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGTL_TIENDO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TieuchithuongTL_KBH where thuongtl_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTL_KBH: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TieuchithuongTL_LOAIKH where thuongtl_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTL_LOAIKH: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.kbhIds.trim().length() > 0)
			{
				query = "Insert TieuchithuongTL_KBH(thuongtl_fk, kbh_fk)  " +
						"select '" + this.id + "', pk_seq from KenhBanHang where pk_seq > 0  ";
		    	if(this.kbhIds.trim().length() > 0)
		    		query += " and pk_seq in ( " + this.kbhIds + " ) ";
		    	
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "4.Khong the tao moi 'TieuchithuongTL_KBH', " + query;
					return false; 
				}
			}
			
			if(this.loaiKhIds.trim().length() > 0)
			{
				query = "Insert TieuchithuongTL_LOAIKH(thuongtl_fk, LOAIKH_Fk)  " +
						"select '" + this.id + "', pk_seq from LOAICUAHANG where pk_seq >= 0  ";
		    	if(this.loaiKhIds.trim().length() > 0)
		    		query += " and pk_seq in ( " + this.loaiKhIds + " ) ";
		    	
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "4.Khong the tao moi 'TieuchithuongTL_LOAIKH', " + query;
					return false; 
				}
			}
			
			if(this.diengiaiMuc != null)
			{
				String sql = "";
				if( !this.phanloai.equals("3") )
				{
					for(int i = 0; i < this.diengiaiMuc.length; i++)
					{
						//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
						if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
						{	
							sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiai, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
								" '" + this.thuongSR[i].replaceAll(",", "") + "' as chietkhau, N'" + this.thuongTDSR[i] + "' as donvi, '" + i + "' as muc ,'"+this.httt1[i]+"' as mucphanbo union ";
							
							//LUU VAO BANG TIEN DO
							if(this.muc_tiendo.get(Integer.toString(i)) != null )
							{
								String infoDETAIL = this.muc_tiendo.get( Integer.toString(i) );
								if(infoDETAIL.trim().length() > 0)
	                			{ 
	                				String[] info = infoDETAIL.split("__");
	                				for(int k = 0; k < info.length; k++ )
	                				{
	                					query = "insert TIEUCHITHUONGTL_TIENDO( thuongtl_fk, muc, tiendo, tungay, denngay, phaidat ) "
	                							+ " values ( '" + this.id + "', '" + i + "', '" + k + "', '" + info[k].split("_")[0] + "', '" + info[k].split("_")[1] + "', '" + info[k].split("_")[2].replaceAll(",", "") + "' )";
	                					if(!db.update(query))
	                					{
	                						db.getConnection().rollback();
	                						this.msg = "Khong the tao moi 'TIEUCHITHUONGTL_TIENDO', " + query;
	                						return false; 
	                					}
	                				}
	                			}
							}
						}
					}
					
					if(sql.trim().length() > 0)
					{
						sql = sql.substring(0, sql.length() - 6);
						
						query = "insert TieuchithuongTL_TIEUCHI(thuongtl_fk, ghichu, tumuc, denmuc, chietkhau, donvi, muc,mucphanbo) " +
								"select '" + this.id + "' as tctId, tieuchi.* from (" + sql + ") tieuchi ";
						
						System.out.println("2.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "2.Khong the tao moi TieuchithuongTL_TIEUCHI: " + query;
							db.getConnection().rollback();
							return false;
						}
					}	
				}
				else  //TICH LUY THEO GIAI DOAN
				{
					for(int i = 0; i < this.diengiaiMuc.length; i++)
					{
						//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
						if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0
								&& this.thuongSR[i].trim().length() > 0 && this.thuongASM[i].trim().length() > 0 && this.thuongSS[i].trim().length() > 0 && this.thuongTDSS[i].trim().length() > 0  )
						{
							sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiai, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
									" '" + this.thuongSR[i].replaceAll(",", "") + "' as chietkhau, N'" + this.thuongTDSR[i] + "' as donvi, '" + i + "' as muc, '1' as mucphanbo, '" + this.thuongASM[i] + "' as soxuattoida, '" + this.thuongSS[i] + "' as tungay, '" + this.thuongTDSS[i] + "' as denngay union ";
						}
					}
					
					if(sql.trim().length() > 0)
					{
						sql = sql.substring(0, sql.length() - 6);
						
						query = "insert TieuchithuongTL_TIEUCHI(thuongtl_fk, ghichu, tumuc, denmuc, chietkhau, donvi, muc, mucphanbo, soxuatToiDa, tungay, denngay ) " +
								"select '" + this.id + "', tieuchi.* from (" + sql + ") tieuchi ";
						
						System.out.println("2.Insert TieuchithuongTL_TIEUCHI: " + query);
						if(!db.update(query))
						{
							this.msg = "2.Khong the tao moi TieuchithuongTL_TIEUCHI: " + query;
							db.getConnection().rollback();
							return false;
						}
					}	
				}
				
			}
			
			/*if(this.spIds.trim().length() > 0)
			{*/
				if(!this.phanloai.equals("1"))
				{
					for(int i = 0; i < this.maspTra.length; i ++)
					{
						/*query = "Insert TIEUCHITHUONGTL_SANPHAM(thuongtl_fk, sanpham_fk) " +
								"select '" + this.id + "', pk_seq from SanPham where pk_seq in (" + this.spIds + ") ";*/
						
						if(this.maspTra[i].trim().length() > 0)
						{
							query = "Insert TIEUCHITHUONGTL_SANPHAM(thuongtl_fk, sanpham_fk) " +
									"select '" + this.id + "', pk_seq from SanPham where ma = N'" + this.maspTra[i] + "' ";
							
							System.out.println("3.Insert: " + query);
							if(!db.update(query))
							{
								this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
				else
				{
					for(int i = 0; i < this.maspTra.length; i++)
					{
						if(this.maspTra[i].trim().length() > 0)
						{
							String _dieukien = "NULL";
							String _dvDieukien = "NULL";
							if(this.dieukien.get(this.maspTra[i]) != null)
							{
								_dieukien = this.dieukien.get(this.maspTra[i]).split("__")[0].replaceAll(",", "");
								_dvDieukien = this.dieukien.get(this.maspTra[i]).split("__")[1];
							}

							String _quydoi = "NULL";
							String _dvQuydoi = "NULL";
							if(this.quydoi.get(this.maspTra[i]) != null)
							{
								_quydoi = this.quydoi.get(this.maspTra[i]).split("__")[0].replaceAll(",", "");
								_dvQuydoi = this.quydoi.get(this.maspTra[i]).split("__")[1];
							}

							System.out.println("3.0.Dieu kien: " + _dieukien + " --- QUY DOI: " + _quydoi + " -- MA SP: " + this.maspTra[i] );
							if(!_dieukien.equals("NULL") || !_quydoi.equals("NULL"))
							{
								query = "Insert TieuchithuongTL_SANPHAM(thuongtl_fk, sanpham_fk, dieukien, donviDIEUKIEN, quydoi, donviQUYDOI) " +
										"select '" + this.id + "', pk_seq, " + _dieukien + ", " + _dvDieukien + ", " + _quydoi + ", " + _dvQuydoi + " from SanPham where ma = N'" + this.maspTra[i] + "' ";

								System.out.println("3.Insert SP: " + query);
								if(!db.update(query))
								{
									this.msg = "3.Khong the tao moi TieuchithuongTL_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			//}
			
				// chen phan bo nha phan phoi theo muc
				if(this.nppIds1.trim().length() > 0)
				{
					query = "Insert TIEUCHITHUONGTL_NPP(thuongtl_fk, npp_fk,muc) " +
							"select '" + this.id + "', pk_seq,'0' from NHAPHANPHOI where pk_seq in (" + this.nppIds1 + ") ";
					
					System.out.println("4.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					//CAP NHAT PHAN BO
					if(this.phanbo1.size() > 0)
					{
						Enumeration<String> keys = this.phanbo1.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							query = "update TIEUCHITHUONGTL_NPP set soluong = '" + phanbo1.get(key).replaceAll(",", "") + "' " + 
									" where thuongtl_fk = '" + this.id + "' and npp_fk = '" + key + "' ";
							if(!db.update(query))
							{
								this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
				if(this.nppIds2.trim().length() > 0)
				{
					query = "Insert TIEUCHITHUONGTL_NPP(thuongtl_fk, npp_fk,muc) " +
							"select '" + this.id + "', pk_seq,'1' from NHAPHANPHOI where pk_seq in (" + this.nppIds2 + ") ";
					
					System.out.println("4.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					//CAP NHAT PHAN BO
					if(this.phanbo2.size() > 0)
					{
						Enumeration<String> keys = this.phanbo2.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							query = "update TIEUCHITHUONGTL_NPP set soluong = '" + phanbo2.get(key).replaceAll(",", "") + "' " + 
									" where thuongtl_fk = '" + this.id + "' and npp_fk = '" + key + "' ";
							if(!db.update(query))
							{
								this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
				if(this.nppIds3.trim().length() > 0)
				{
					query = "Insert TIEUCHITHUONGTL_NPP(thuongtl_fk, npp_fk,muc) " +
							"select '" + this.id + "', pk_seq,'2' from NHAPHANPHOI where pk_seq in (" + this.nppIds3 + ") ";
					
					System.out.println("4.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					//CAP NHAT PHAN BO
					if(this.phanbo3.size() > 0)
					{
						Enumeration<String> keys = this.phanbo3.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							query = "update TIEUCHITHUONGTL_NPP set soluong = '" + phanbo3.get(key).replaceAll(",", "") + "' " + 
									" where thuongtl_fk = '" + this.id + "' and npp_fk = '" + key + "' ";
							if(!db.update(query))
							{
								this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
				if(this.nppIds4.trim().length() > 0)
				{
					query = "Insert TIEUCHITHUONGTL_NPP(thuongtl_fk, npp_fk,muc) " +
							"select '" + this.id + "', pk_seq,'3' from NHAPHANPHOI where pk_seq in (" + this.nppIds4 + ") ";
					
					System.out.println("4.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					//CAP NHAT PHAN BO
					if(this.phanbo4.size() > 0)
					{
						Enumeration<String> keys = this.phanbo4.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							query = "update TIEUCHITHUONGTL_NPP set soluong = '" + phanbo4.get(key).replaceAll(",", "") + "' " + 
									" where thuongtl_fk = '" + this.id + "' and npp_fk = '" + key + "' ";
							if(!db.update(query))
							{
								this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
				if(this.nppIds5.trim().length() > 0)
				{
					query = "Insert TIEUCHITHUONGTL_NPP(thuongtl_fk, npp_fk,muc) " +
							"select '" + this.id + "', pk_seq,'4' from NHAPHANPHOI where pk_seq in (" + this.nppIds5 + ") ";
					
					System.out.println("4.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					//CAP NHAT PHAN BO
					if(this.phanbo5.size() > 0)
					{
						Enumeration<String> keys = this.phanbo5.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							query = "update TIEUCHITHUONGTL_NPP set soluong = '" + phanbo5.get(key).replaceAll(",", "") + "' " + 
									" where thuongtl_fk = '" + this.id + "' and npp_fk = '" + key + "' ";
							if(!db.update(query))
							{
								this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_NPP: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			
			query = "delete TIEUCHITHUONGTL_SPTRA where thuongtl_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_SPTRA: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//CHEN TICH LUY THEO MUC
			for(int i = 0; i < 5; i++)
			{
				if( this.muc_spTRA.get( Integer.toString(i) ) != null )
    			{
    				String infoDETAIL_SPTRA = this.muc_spTRA.get( Integer.toString(i) );
    				String infoDETAIL_SPTRA_HT = infoDETAIL_SPTRA.split("__")[0];
    				
    				if(infoDETAIL_SPTRA.trim().length() > 0)
        			{ 
        				String[] info = infoDETAIL_SPTRA.split("__")[1].split(";;");
        				for(int k = 0; k < info.length; k++ )
        				{
        					String[] info2 = info[k].split("_");
        					
        					query = "insert TIEUCHITHUONGTL_SPTRA( thuongtl_fk, sanpham_fk, soluong, dongia, muctra, hinhthuctra ) " + 
        							" select '" + this.id + "', pk_seq, '" + info2[2] + "', 0, '" + i + "', '" + infoDETAIL_SPTRA_HT + "' from SANPHAM where MA = '" + info2[0] + "' ";
        					if(!db.update(query))
        					{
        						this.msg = "3.Khong the tao moi TIEUCHITHUONGTL_SPTRA: " + query;
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
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
				e.printStackTrace();
				
				this.msg = "Lỗi cập nhật: " + e.getMessage();
			} 
			catch (SQLException e1) {}
			
			return false;
		}
		
		return true;
	}

	public void init() 
	{
		String query = "select scheme, thang, nam,isnull(ghichu,'') as ghichu, diengiai, mucvuot, chietkhauMucVuot, donviMucVuot, hinhthuctra, DOANHSOTHEOTHUNG, HTTT, PT_TRATL, ngayds_tungay, ngayds_denngay, khoId, phanloai, ngaytb_tungay, ngaytb_denngay, batbuocdangky, chiavaodongia, kmcp_fk, giobatdau, gioketthuc, khongcanduyettra, soxuatToiDa  " +
					   "from TieuchithuongTL where pk_seq = '" + this.id + "'";
		
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					this.scheme = rs.getString("scheme");
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");					
					this.diengiai= rs.getString("diengiai");
					
					this.mucvuot = rs.getString("mucvuot") != null ? format.format(rs.getDouble("mucvuot")) : "";
					this.ckMucvuot = rs.getString("chietkhauMucVuot") != null ? format.format(rs.getDouble("chietkhauMucVuot")) : "";
					this.dvMucvuot = rs.getString("donviMucVuot");
					this.hinhthucTra = rs.getString("hinhthuctra");
					this.doanhsotheoThung = rs.getString("DOANHSOTHEOTHUNG");
					
					this.httt = rs.getString("HTTT");
					this.ptchietkhau = rs.getString("PT_TRATL");
					this.ghighu = rs.getString("ghichu");
					this.tungay = rs.getString("ngayds_tungay");
					this.dengay = rs.getString("ngayds_denngay");
					this.khoId = rs.getString("khoId");
					
					this.phanloai = rs.getString("phanloai");

					this.ngaytb_tungay = rs.getString("ngaytb_tungay");
					this.ngaytb_dengay = rs.getString("ngaytb_denngay");
					this.phaidangky = rs.getString("batbuocdangky");
					this.chiavaodongia = rs.getString("chiavaodongia");
					this.khoanmuccpId = rs.getString("kmcp_fk") != null ? rs.getString("kmcp_fk") : "";
					
					this.setGiobatdau( rs.getString("giobatdau") );
					this.setGioketthuc( rs.getString("gioketthuc") );
					this.khongcanduyet = rs.getString("khongcanduyettra");
					this.soxuattoida = rs.getString("soxuattoida") != null ? rs.getString("soxuattoida") : "";
				}
				rs.close();
			} 
			catch (Exception e)
			{
				System.out.println("115.Error Meg: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		//Init kenh ban hang
        ResultSet checkDh = db.get("select KBH_Fk from TieuchithuongTL_KBH where thuongtl_fk ='" + this.id + "'");
        if(checkDh != null)
        {
        	try 
        	{
        		String kbhIds = "";
				while(checkDh.next())
				{
					kbhIds += checkDh.getString("kbh_fk") + ",";
				}
				checkDh.close();
				
				if(kbhIds.trim().length() > 0)
				{
					kbhIds = kbhIds.substring(0, kbhIds.length() - 1);
					this.kbhIds = kbhIds;
				}
			} 
        	catch (Exception e) 
        	{
        		System.out.println("Exception Kenh Ban Hang: " + e.getMessage());
        	}
        }
        
        
        //Init loai khach hang
        checkDh = db.get("select LOAIKH_Fk from TieuchithuongTL_LOAIKH where thuongtl_fk ='" + this.id + "'");
        if(checkDh != null)
        {
        	try 
        	{
        		String kbhIds = "";
				while(checkDh.next())
				{
					kbhIds += checkDh.getString("LOAIKH_Fk") + ",";
				}
				checkDh.close();
				
				if(kbhIds.trim().length() > 0)
				{
					kbhIds = kbhIds.substring(0, kbhIds.length() - 1);
					this.loaiKhIds = kbhIds;
				}
			} 
        	catch (Exception e) 
        	{
        		System.out.println("Exception Loai Khach Hang: " + e.getMessage());
        	}
        }
        System.out.println(":::: LOAI KHACH HANG: " + this.loaiKhIds );
		
		
		this.createNdk();
		this.createRs();
		
		this.createKhachHangDK();
	}
	
	private void createKhachHangDK()
	{
		try
		{

			String DKKMTICHLUY_KHACHHANG_IdTmp ="";
			String khDcDuyetTmp ="";
			
			String query = "\n select khdk.DKKMTICHLUY_FK, khdk.KHACHHANG_FK, npp.TEN as nhaphanphoi, kh.TEN as khachhang, isnull(khdk.DANGKY, 0) as dangky, nppdk.Muc, khdk.IsDuyet  " + 
			 "\n from DANGKYKM_TICHLUY_KHACHHANG khdk  " + 
			 "\n inner join DANGKYKM_TICHLUY nppdk on khdk.DKKMTICHLUY_FK = nppdk.PK_SEQ " + 
			 "\n inner join TIEUCHITHUONGTL thuong on thuong.PK_SEQ = nppdk.tieuchitl_fk " + 
			 "\n inner join KHACHHANG kh on kh.PK_SEQ = khdk.KHACHHANG_FK " + 
			 "\n inner join NHAPHANPHOI npp on npp.PK_SEQ = nppdk.npp_fk " + 
			 "\n where  thuong.PK_SEQ = "+ this.id ;
			System.out.println(":::: lay khach hang dang ky: " + query);
			ResultSet rs = db.get(query); 
			this.khDangKyRs = db.get(query); 
			while(rs.next())
			{
				DKKMTICHLUY_KHACHHANG_IdTmp += rs.getString("DKKMTICHLUY_FK") + "," + rs.getString("KHACHHANG_FK")+"__" ;
				khDcDuyetTmp += rs.getString("IsDuyet") + "__" ;
			}
			
			if(DKKMTICHLUY_KHACHHANG_IdTmp.trim().length() >2)
			{
				DKKMTICHLUY_KHACHHANG_IdTmp =DKKMTICHLUY_KHACHHANG_IdTmp.substring(0, DKKMTICHLUY_KHACHHANG_IdTmp.length() - 2);
				khDcDuyetTmp =khDcDuyetTmp.substring(0, khDcDuyetTmp.length() - 2);
				DKKMTICHLUY_KHACHHANG_Id = DKKMTICHLUY_KHACHHANG_IdTmp.split("__");
				khDcDuyet = khDcDuyetTmp.split("__");
				
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void createNdk() 
	{
		String query = "select ghichu, tumuc, denmuc, chietkhau, donvi, muc, isnull(mucphanbo, '0') as mucphanbo, " + 
					   " 	isnull(soxuatToiDa, 0) as soxuatToiDa, isnull(tungay, ' ') as tungay, isnull(denngay, ' ') as denngay   " +
					   "from TieuchithuongTL_TIEUCHI " +
					   "where thuongtl_fk = '" + this.id + "'  ";
		
		System.out.println("___Khoi tao tieu chi: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			NumberFormat format = new DecimalFormat("##,###,###");
			NumberFormat format2 = new DecimalFormat("##,###,###.##");
			try 
			{
				String tieu_chi = "";
				String tu_muc = "";
				String den_muc = "";
				String chiet_khau = "";
				String don_vi = "";
				String so_xuat_td = "";
				String tu_ngay = "";
				String den_ngay = "";
				
				Hashtable<String, String> muc_tiendo = new Hashtable<String, String>();
				Hashtable<String, String> muc_spTRA = new Hashtable<String, String>();
				String htpb ="";
				while(rs.next())
				{
					tieu_chi += rs.getString("ghichu") + ",,";
					tu_muc += format.format(rs.getDouble("tumuc")) + ",,";
					den_muc += format.format(rs.getDouble("denmuc")) + ",,";
					chiet_khau += format2.format(rs.getDouble("chietkhau")) + ",,";
					don_vi += format.format(rs.getDouble("donvi")) + ",,";
					
					so_xuat_td += rs.getString("soxuatToiDa") + ",,";
					tu_ngay += rs.getString("tungay") + ",,";
					den_ngay += rs.getString("denngay") + ",,";
					 
					String muc = rs.getString("muc");
					String tiendoCHITIET = "";
					String spTRACHITIET = "";
					htpb +=rs.getString("mucphanbo") + ",";
					
					//INIT TIEN DO THEO MUC
					if( !this.phanloai.equals("3") )
					{
						query = "select tungay, denngay, phaidat from TIEUCHITHUONGTL_TIENDO where thuongtl_fk = '" + this.id + "' and muc = '" + muc + "' ";
						ResultSet rsDETAIL = db.get(query);
						if(rsDETAIL != null)
						{
							while(rsDETAIL.next())
							{
								tiendoCHITIET += rsDETAIL.getString("tungay") + "_" + rsDETAIL.getString("denngay") + "_" + rsDETAIL.getString("phaidat") + "__";
							}
							rsDETAIL.close();
						}
						
						if(tiendoCHITIET.trim().length() > 0)
						{
							tiendoCHITIET = tiendoCHITIET.substring(0, tiendoCHITIET.length() - 2);
							muc_tiendo.put(muc, tiendoCHITIET);	
						}
					}
					
					//INIT SAN PHAM TRA THEO MUC
					query = "select b.MA, b.TEN, isnull(a.soluong, 0) as soluong, isnull(a.dongia, 0) as dongia, a.muctra, hinhthuctra " +
							"from TIEUCHITHUONGTL_SPTRA a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ " +
							"where a.thuongtl_fk = '" + this.id + "' and a.muctra = '" + muc + "' ";
					System.out.println("--- INIT SP TRA:: " + query);
					ResultSet rsDETAIL = db.get(query);
					String hinhthucTRA = "";
					if(rsDETAIL != null)
					{
						while(rsDETAIL.next())
						{
							hinhthucTRA = rsDETAIL.getString("hinhthuctra");
							String SLG = rsDETAIL.getDouble("soluong") <= 0 ? " " : rsDETAIL.getString("soluong");

							spTRACHITIET += rsDETAIL.getString("MA") + "_" + rsDETAIL.getString("TEN") + "_" + SLG + ";;";
						}
						rsDETAIL.close();
					}

					if(spTRACHITIET.trim().length() > 0)
					{
						spTRACHITIET = spTRACHITIET.substring(0, spTRACHITIET.length() - 2);
						muc_spTRA.put(muc, hinhthucTRA + "__" + spTRACHITIET);	
					}
				}
				
				
				rs.close();
				
				if(tieu_chi.trim().length() > 0)
				{
					tieu_chi = tieu_chi.substring(0, tieu_chi.length() - 2);
					this.diengiaiMuc = tieu_chi.split(",,");
					
					tu_muc = tu_muc.substring(0, tu_muc.length() - 2);
					this.tumuc = tu_muc.split(",,");
					
					den_muc = den_muc.substring(0, den_muc.length() - 2);
					this.denmuc = den_muc.split(",,");
					
					chiet_khau = chiet_khau.substring(0, chiet_khau.length() - 2);
					this.thuongSR = chiet_khau.split(",,");
					
					don_vi = don_vi.substring(0, don_vi.length() - 2);
					this.thuongTDSR = don_vi.split(",,");
					
					so_xuat_td = so_xuat_td.substring(0, so_xuat_td.length() - 2);
					this.thuongASM = so_xuat_td.split(",,");
					
					tu_ngay = tu_ngay.substring(0, tu_ngay.length() - 2);
					this.thuongSS = tu_ngay.split(",,");
					
					den_ngay = den_ngay.substring(0, den_ngay.length() - 2);
					this.thuongTDSS = den_ngay.split(",,");
					
					
					this.muc_tiendo = muc_tiendo;
					this.muc_spTRA = muc_spTRA;
				}
				if(htpb.length() > 0)
				{
					System.out.println("htpb "+htpb);
					htpb = htpb.substring(0,htpb.length() - 1);
					this.httt1 = htpb.split(",");
				}
			} 
			catch (Exception e) {
				
				System.out.println("Loi khoi tao: " + e.toString());
				e.printStackTrace();
			}
		}
		
		if( !this.phanloai.equals("1") )
		{
			/*query = "select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = '" + this.id + "' ";
			System.out.println("___KHOI TAO SP: " + query);
			
			rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					String spId = "";
					while(rs.next())
					{
						spId += rs.getString("sanpham_fk") + ",";
					}
					rs.close();
					
					if(spId.trim().length() > 0)
						this.spIds = spId.substring(0, spId.length() - 1);
				} 
				catch (Exception e) 
				{
					System.out.println("33.Loi khoi tao: " + e.toString());
				}
			}*/
			
			//Init San Pham
			query = "select b.MA, b.TEN " +
					"from TIEUCHITHUONGTL_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ " +
					"where a.thuongtl_fk = '" + this.id + "'";
			
			System.out.println("__LAY SP TRA: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					String spMa = "";
					String spTen = "";
					
					while(rs.next())
					{
						spMa += rs.getString("MA") + ",,";
						spTen += rs.getString("TEN") + ",,";
					}
					rs.close();
					
					System.out.println("---TEN SP: " + spTen + "  -- MA SP: " + spMa);
					if(spMa.trim().length() > 0)
					{
						spMa = spMa.substring(0, spMa.length() - 2);
						this.maspTra = spMa.split(",,");
						
						spTen = spTen.substring(0, spTen.length() - 2);
						this.tenspTra = spTen.split(",,");
					}
				} 
				catch (Exception e) { System.out.println("__EXCEPTION INIT: " + e.getMessage());  }		
			}
		}
		else
		{
			/*query = "select sanpham_fk, dieukien, donviDIEUKIEN, quydoi, donviQUYDOI " + 
						   " from TieuchithuongTL_SANPHAM where thuongtl_fk = '" + this.id + "' ";
			System.out.println("___KHOI TAO SP: " + query);
			
			rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					String spId = "";
					Hashtable<String, String> htdieukien = new Hashtable<String, String>();
					Hashtable<String, String> htquydoi = new Hashtable<String, String>();
					
					while(rs.next())
					{
						spId += rs.getString("sanpham_fk") + ",";
						
						if(rs.getString("dieukien") != null)
						{
							htdieukien.put(rs.getString("sanpham_fk"), rs.getString("dieukien") + "__" + rs.getString("donviDIEUKIEN"));
						}
						
						if(rs.getString("quydoi") != null)
						{
							htquydoi.put(rs.getString("sanpham_fk"), rs.getString("quydoi") + "__" + rs.getString("donviQUYDOI"));
						}
					}
					rs.close();
					
					if(spId.trim().length() > 0)
					{
						this.spIds = spId.substring(0, spId.length() - 1);
						
						this.dieukien = htdieukien;
						this.quydoi = htquydoi;
					}
				} 
				catch (Exception e) 
				{
					System.out.println("33.Loi khoi tao: " + e.toString());
					e.printStackTrace();
				}
			}*/
			
			query = "select b.MA, b.TEN, dieukien, donviDIEUKIEN, quydoi, donviQUYDOI " +
					"from TIEUCHITHUONGTL_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ " +
					"where a.thuongtl_fk = '" + this.id + "'";
			
			System.out.println("__LAY SP TRA: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					String spMa = "";
					String spTen = "";
					
					Hashtable<String, String> htdieukien = new Hashtable<String, String>();
					Hashtable<String, String> htquydoi = new Hashtable<String, String>();
					
					while(rs.next())
					{
						spMa += rs.getString("MA") + ",,";
						spTen += rs.getString("TEN") + ",,";
						
						if(rs.getString("dieukien") != null)
						{
							htdieukien.put(rs.getString("MA"), rs.getString("dieukien") + "__" + rs.getString("donviDIEUKIEN"));
						}
						
						if(rs.getString("quydoi") != null)
						{
							htquydoi.put(rs.getString("MA"), rs.getString("quydoi") + "__" + rs.getString("donviQUYDOI"));
						}
					}
					rs.close();
					
					System.out.println("---TEN SP: " + spTen + "  -- MA SP: " + spMa);
					if(spMa.trim().length() > 0)
					{
						spMa = spMa.substring(0, spMa.length() - 2);
						this.maspTra = spMa.split(",,");
						
						spTen = spTen.substring(0, spTen.length() - 2);
						this.tenspTra = spTen.split(",,");
						
						this.dieukien = htdieukien;
						this.quydoi = htquydoi;
					}
				} 
				catch (Exception e) { System.out.println("__EXCEPTION INIT: " + e.getMessage());  }		
			}
			
		}
		
		query = "select npp_fk, isnull(soluong, 0) as soluong from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' ";
		System.out.println("___KHOI TAO NPP: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("npp_fk") + ",";
					
					String SLG = rs.getDouble("soluong") <= 0 ? "" : rs.getString("soluong");
					this.phanbo.put(rs.getString("npp_fk"), SLG);
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.nppIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
				e.printStackTrace();
			}
		}
		query = "select npp_fk, isnull(soluong, 0) as soluong from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "'and muc = '0' ";
		System.out.println("___KHOI TAO NPP: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("npp_fk") + ",";
					
					String SLG = rs.getDouble("soluong") <= 0 ? "" : rs.getString("soluong");
					this.phanbo1.put(rs.getString("npp_fk"), SLG);
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.nppIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
				e.printStackTrace();
			}
		}
		System.out.println("__NPP ID: " + this.nppIds);
		
		query = "select npp_fk, isnull(soluong, 0) as soluong from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "'and muc = '1' ";
		System.out.println("___KHOI TAO NPP: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("npp_fk") + ",";
					
					String SLG = rs.getDouble("soluong") <= 0 ? "" : rs.getString("soluong");
					this.phanbo2.put(rs.getString("npp_fk"), SLG);
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.nppIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
				e.printStackTrace();
			}
		}
		System.out.println("__NPP ID: " + this.nppIds);
		
		query = "select npp_fk, isnull(soluong, 0) as soluong from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "'and muc = '2' ";
		System.out.println("___KHOI TAO NPP: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("npp_fk") + ",";
					
					String SLG = rs.getDouble("soluong") <= 0 ? "" : rs.getString("soluong");
					this.phanbo3.put(rs.getString("npp_fk"), SLG);
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.nppIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
				e.printStackTrace();
			}
		}
		System.out.println("__NPP ID: " + this.nppIds);
		query = "select npp_fk, isnull(soluong, 0) as soluong from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "'and muc = '3' ";
		System.out.println("___KHOI TAO NPP: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("npp_fk") + ",";
					
					String SLG = rs.getDouble("soluong") <= 0 ? "" : rs.getString("soluong");
					this.phanbo4.put(rs.getString("npp_fk"), SLG);
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.nppIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
				e.printStackTrace();
			}
		}
		System.out.println("__NPP ID: " + this.nppIds);
		
		query = "select npp_fk, isnull(soluong, 0) as soluong from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "'and muc = '4' ";
		System.out.println("___KHOI TAO NPP: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String nppId = "";
				while(rs.next())
				{
					nppId += rs.getString("npp_fk") + ",";
					
					String SLG = rs.getDouble("soluong") <= 0 ? "" : rs.getString("soluong");
					this.phanbo5.put(rs.getString("npp_fk"), SLG);
				}
				rs.close();
				
				if(nppId.trim().length() > 0)
					this.nppIds = nppId.substring(0, nppId.length() - 1);
			} 
			catch (Exception e) 
			{
				System.out.println("33.Loi khoi tao: " + e.toString());
				e.printStackTrace();
			}
		}
		System.out.println("__NPP ID: " + this.nppIds);
		
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getScheme()
	{
		return this.scheme;
	}

	public void setScheme(String scheme) 
	{
		this.scheme = scheme;
	}
	
	public String[] getDiengiaiMuc() {
		
		return this.diengiaiMuc;
	}

	
	public void setDiengiaiMuc(String[] diengiai) {
		
		this.diengiaiMuc = diengiai;
	}

	
	public String[] getTumuc() {
		
		return this.tumuc;
	}

	
	public void setTumuc(String[] tumuc) {
		
		this.tumuc = tumuc;
	}

	
	public String[] getDenmuc() {
		
		return this.denmuc;
	}

	
	public void setDenmuc(String[] denmuc) {
		
		this.denmuc = denmuc;
	}

	
	public String[] getThuongSR() {
		
		return this.thuongSR;
	}

	
	public void setThuongSR(String[] thuongSR) {
		
		this.thuongSR = thuongSR;
	}

	
	public String[] getThuongTDSR() {
		
		return this.thuongTDSR;
	}

	
	public void setThuongTDSR(String[] thuongTDSR) {
		
		this.thuongTDSR = thuongTDSR;
	}

	
	public String[] getThuongSS() {
		
		return this.thuongSS;
	}

	
	public void setThuongSS(String[] thuongSS) {
		
		this.thuongSS = thuongSS;
	}

	
	public String[] getThuongTDSS() {
		
		return this.thuongTDSS;
	}

	
	public void setThuongTDSS(String[] thuongTDSS) {
		
		this.thuongTDSS = thuongTDSS;
	}

	
	public String[] getThuongASM() {
		
		return this.thuongASM;
	}

	
	public void setThuongASM(String[] thuongASM) {
		
		this.thuongASM = thuongASM;
	}

	
	public String[] getThuongTDASM() {
		
		return this.thuongTDASM;
	}

	
	public void setThuongTDASM(String[] thuongTDASM) {
		
		this.thuongTDASM = thuongTDASM;
	}

	public void createRs() {
		
		String query = "select PK_SEQ, MA, TEN, TRANGTHAI, NHANHANG_FK, CHUNGLOAI_FK  from SanPham where trangthai = '1' ";
		
		if(this.id.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN, TRANGTHAI, NHANHANG_FK, CHUNGLOAI_FK from SanPham where pk_seq in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = '" + this.id + "' ) ";
		}
		
		query += " order by TRANGTHAI desc, NHANHANG_FK asc, CHUNGLOAI_FK asc ";
		this.sanphamRs = db.getScrol(query);
		
		
		this.vungRs = db.get("select pk_seq, ten from VUNG where trangthai = '1'");
		
		query = "select pk_seq, ten from KHUVUC where trangthai = '1'";
		if(this.vungIds.trim().length() > 1)
			query += " and vung_fk in ( " + this.vungIds + " ) "; 
		this.kvRs = db.get(query);
		
		
		query = "select pk_seq, ten from KENHBANHANG where trangthai = '1'";
		this.kenhRs = db.get(query);
		
		query = "select PK_SEQ as loaiId, DIENGIAI as loaiTEN from LOAICUAHANG  ";
		this.loaiKhRs = db.get(query);
		
		query = "select PK_SEQ, MA, TEN  from NhaPhanPhoi where trangthai = '1'  ";
		if(this.kvIds.trim().length() > 0)
		{
			System.out.println("1111111");
			if(this.kvIds.trim().length() > 1)
				query += " and khuvuc_fk in ( " +  this.kvIds + " ) ";
			this.kt = "1";
			
		}
		if(this.vungIds.trim().length() > 0)
		{
			this.kt = "1";
			if(this.vungIds.trim().length() > 1)
				query += " and khuvuc_fk in ( select pk_seq from KhuVuc where trangthai = '1' and vung_fk in ( " + this.vungIds + " ) ) ";
		}
		if(this.kenhIds.trim().length() > 0)
		{
			this.kt = "1";
			if(this.kenhIds.trim().length() > 1)
				query += " and pk_seq in ( select NPP_FK from NHAPP_KBH where KBH_FK in ( " + this.kenhIds + " ) ) ";
			
		}
		System.out.println("Muc npp "+this.mucnpp );
		if(this.id.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' ) ";
			if( this.mucnpp.equals("0") )
			{
			
				try {
					ResultSet rs  = db.get("select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' and muc = '0'  )");
					System.out.println("query nppIds "+"select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' and muc = '0'  ");
					if(rs != null)
					while(rs.next())
					{
						nppIds1+= rs.getString("PK_SEQ")+",";
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			if( this.mucnpp.equals("1"))
			{
				System.out.println("nppIds2 "+nppIds2);
				try {
					ResultSet rs  = db.get("select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' and muc = '1'  )");
					System.out.println("query nppIds "+"select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' and muc = '1'  ");
					if(rs != null)
					while(rs.next())
					{
						nppIds2+= rs.getString("PK_SEQ")+",";
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			
			}
			if(this.mucnpp.equals("2"))
			{
				
				try {
					ResultSet rs  = db.get("select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' and muc = '2' ) ");
					System.out.println("query nppIds "+"select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' and muc = '2'  ");
					if(rs != null)
					while(rs.next())
					{
						nppIds3+= rs.getString("PK_SEQ")+",";
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			
			}
			if( this.mucnpp.equals("3"))
			{
				
				try {
					ResultSet rs  = db.get("select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' and muc = '3' ) ");
					System.out.println("query nppIds "+"select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' and muc = '3'  ");
					if(rs != null)
					while(rs.next())
					{
						nppIds4+= rs.getString("PK_SEQ")+",";
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			
			}
			if(this.nppIds5.trim().length() > 0 && this.mucnpp.equals("4"))
			{
			try {
				ResultSet rs  = db.get("select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' and muc = '4' ) ");
				System.out.println("query nppIds "+"select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' and muc = '4'  ");
				if(rs != null)
				while(rs.next())
				{
					nppIds5+= rs.getString("PK_SEQ")+",";
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			}
		}
		else
		{
		System.out.println("nppIds1 "+nppIds1);
		if(this.nppIds1.trim().length() > 0 && this.mucnpp.equals("0"))
		{
			System.out.println("nppIds1");
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( " + this.nppIds1 + " ) ";
			
		}
		if(this.nppIds2.trim().length() > 0 && this.mucnpp.equals("1"))
		{
			System.out.println("nppIds2");
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( " + this.nppIds2 + " ) ";
		
		}
		if(this.nppIds3.trim().length() > 0 && this.mucnpp.equals("2"))
		{
			System.out.println("nppIds3");
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( " + this.nppIds3 + " ) ";
		
		}
		if(this.nppIds4.trim().length() > 0 && this.mucnpp.equals("3"))
		{
			System.out.println("nppIds4");
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( " + this.nppIds4 + " ) ";
		
		}
		if(this.nppIds5.trim().length() > 0 && this.mucnpp.equals("4"))
		{
			System.out.println("nppIds5");
			query += " union select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( " + this.nppIds5 + " ) ";
		
		}
		}
		query += " order by PK_SEQ desc ";
		this.nppRs = db.getScrol(query);
//		if(id.length() > 0 && kt.equals("0") && mucnpp.equals(""))
//		{
//			
//			try {
//				ResultSet rs  = db.get("select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' ) ");
//				System.out.println("query nppIds "+"select PK_SEQ, MA, TEN from NhaPhanPhoi where pk_seq in ( select npp_fk from TIEUCHITHUONGTL_NPP where thuongtl_fk = '" + this.id + "' ) ");
//				if(rs != null)
//				while(rs.next())
//				{
//					nppIds+= nppRs.getString("PK_SEQ")+",";
//				}
//				nppIds1 = nppIds;
//			} catch (SQLException e) {
//				
//				e.printStackTrace();
//			}
//		}
		System.out.println("get NPP "+query);
		query = "select pk_seq, ten from KHO where trangthai = '1'";
		this.khoRs = db.get(query);
		
		query = "select pk_seq, ten from NHOMSANPHAM where trangthai = '1'";
		this.nhomsanphamRs = db.get(query);
		
		query = "select PK_SEQ, TEN + ', ' + DIENGIAI as diengiai from ERP_NHOMCHIPHI where TRANGTHAI = '1' order by DIENGIAI asc";
		this.khoanmuccpRs = db.get(query);
		
	}
	
	public String[] getDiengiaiMuc3() {
		
		return this.diengiaiMuc3;
	}

	
	public void setDiengiaiMuc3(String[] diengiai) {
		
		this.diengiaiMuc3 = diengiai;
	}

	
	public String[] getTumuc3() {
		
		return this.tumuc3;
	}

	
	public void setTumuc3(String[] tumuc) {
		
		this.tumuc3 = tumuc;
	}

	
	public String[] getDenmuc3() {
		
		return this.denmuc3;
	}

	
	public void setDenmuc3(String[] denmuc) {
		
		this.denmuc3 = denmuc;
	}
	
	
	public String[] getThuongSR3() {
		
		return this.thuongSR3;
	}

	
	public void setThuongSR3(String[] thuongSR) {
		
		this.thuongSR3 = thuongSR;
	}

	
	public String[] getThuongTDSR3() {
		
		return this.thuongTDSR3;
	}

	
	public void setThuongTDSR3(String[] thuongTDSR) {
		
		this.thuongTDSR3 = thuongTDSR;
	}

	
	public String[] getThuongSS3() {
		
		return this.thuongSS3;
	}

	
	public void setThuongSS3(String[] thuongSS) {
		
		this.thuongSS3 = thuongSS;
	}

	
	public String[] getThuongTDSS3() {
		
		return this.thuongTDSS3;
	}

	
	public void setThuongTDSS3(String[] thuongTDSS) {
		
		this.thuongTDSS3 = thuongTDSS;
	}

	
	public String[] getThuongASM3() {
		
		return this.thuongASM3;
	}

	
	public void setThuongASM3(String[] thuongASM) {
		
		this.thuongASM3 = thuongASM;
	}

	
	public String[] getThuongTDASM3() {
		
		return this.thuongTDASM3;
	}

	
	public void setThuongTDASM3(String[] thuongTDASM) {
		
		this.thuongTDASM3 = thuongTDASM;
	}

	
	public void setSanphamRs(ResultSet spRs) {
		
		this.sanphamRs = spRs;
	}

	
	public ResultSet getSanphamRs() {
		
		return this.sanphamRs;
	}

	
	public String getSpIds() {
		
		return this.spIds;
	}

	
	public void setSpIds(String spIds) {
		
		this.spIds = spIds;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public String getNppIds() {
		
		return this.nppIds;
	}

	
	public void setNppIds1(String nppIds1) {
		
		this.nppIds1 = nppIds1;
	}

	public String getNppIds1() {
		
		return this.nppIds1;
	}


	public String getNppIds2() {
		
		return this.nppIds2;
	}

	
	public void setNppIds2(String nppIds2) {
		
		this.nppIds2 = nppIds2;
	}

	public String getNppIds3() {
		
		return this.nppIds3;
	}

	
	public void setNppIds3(String nppIds3) {
		
		this.nppIds3 = nppIds3;
	}

	public String getNppIds4() {
		
		return this.nppIds4;
	}

	
	public void setNppIds4(String nppIds4) {
		
		this.nppIds4 = nppIds4;
	}

	public String getNppIds5() {
		
		return this.nppIds5;
	}

	
	public void setNppIds5(String nppIds5) {
		
		this.nppIds5 = nppIds5;
	}

	public void setNppIds(String nppIds) {
		
		this.nppIds = nppIds;
	}

	
	public String getMucvuot() {
		
		return this.mucvuot;
	}

	
	public void setMucvuot(String mucvuot) {
		
		this.mucvuot = mucvuot;
	}

	
	public String getChietkhauMucvuot() {
		
		return this.ckMucvuot;
	}

	
	public void setChietkhauMucvuot(String ckMv) {
		
		this.ckMucvuot = ckMv;
	}

	
	public String getDonviMucvuot() {
		
		return this.dvMucvuot;
	}

	
	public void setDonviMucvuot(String dvMv) {
		
		this.dvMucvuot = dvMv;
	}

	
	public void setVungRs(ResultSet vungRs) {
		
		this.vungRs = vungRs;
	}

	
	public ResultSet getVungRs() {
		
		return this.vungRs;
	}

	
	public String getVungIds() {
		
		return this.vungIds;
	}

	
	public void setVungIds(String vungIds) {
		
		this.vungIds = vungIds;
	}

	
	public void setKhuvucRs(ResultSet kvRs) {
		
		this.kvRs = kvRs;
	}

	
	public ResultSet getKhuvucRs() {
		
		return this.kvRs;
	}

	
	public String getKhuvucIds() {
		
		return this.kvIds;
	}

	
	public void setKhuvucIds(String kvIds) {
		
		this.kvIds = kvIds;
	}

	
	public String getHinhthuctra() {
		
		return this.hinhthucTra;
	}

	
	public void setHinhthuctra(String htTra) {
		
		this.hinhthucTra = htTra;
	}

	
	public String[] getMaspTra() {
		
		return this.maspTra;
	}

	
	public void setMaspTra(String[] maspTra) {
		
		this.maspTra = maspTra;
	}

	
	public String[] getTenspTra() {
		
		return this.tenspTra;
	}

	
	public void setTenspTra(String[] tenspTra) {
		
		this.tenspTra = tenspTra;
	}

	
	public String[] getSoluongspTra() {
		
		return this.soluongspTra;
	}

	
	public void setSoluongspTra(String[] soluongspTra) {
		
		this.soluongspTra = soluongspTra;
	}

	
	public String[] getIdspTra() {
		
		return this.isspTra;
	}

	
	public void setIdspTra(String[] idspTra) {
		
		this.isspTra = idspTra;
	}

	
	public String getDoanhsotheoThung() {
		
		return this.doanhsotheoThung;
	}

	
	public void setDoanhsotheoThung(String isThung) {
		
		this.doanhsotheoThung = isThung;
	}

	
	public String getHTTT() {
		
		return this.httt;
	}

	
	public void setHTTT(String httt) {
		
		this.httt = httt;
	}

	
	public String getPT_TRACK() {
		
		return this.ptchietkhau;
	}

	
	public void setPT_TRACK(String ptTRACK) {
		
		this.ptchietkhau = ptTRACK;
	}


	public String[] getDongiaspTra() {

		return this.dongiaspTra;
	}


	public void setDongiaspTra(String[] dongiaspTra) {
		
		this.dongiaspTra = dongiaspTra;
	}

	
	public Hashtable<String, String> getMuc_Tiendo() {
		
		return this.muc_tiendo;
	}

	
	public void setMuc_Tiendo(Hashtable<String, String> tiendo) {
		
		this.muc_tiendo = tiendo;
	}

	
	public Hashtable<String, String> getMuc_SpTra() {
		
		return this.muc_spTRA;
	}

	
	public void setMuc_SpTra(Hashtable<String, String> spTRA) {
		
		this.muc_spTRA = spTRA;
	}

	
	public String getNgayDS_Tungay() {
		
		return this.tungay;
	}

	
	public void setNgayDS_Tungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getNgayDS_Denngay() {
		
		return this.dengay;
	}

	
	public void setNgayDS_Denngay(String denngay) {
		
		this.dengay = denngay;
	}

	
	public void setKhoRs(ResultSet khoRs) {
		
		this.khoRs = khoRs;
	}

	
	public ResultSet getKhoRs() {
		
		return this.khoRs;
	}

	
	public String getKhoIds() {
		
		return this.khoId;
	}

	
	public void setKhoIds(String khoIds) {
		
		this.khoId = khoIds;
	}

	
	public Hashtable<String, String> getPhanbo() {

		return this.phanbo;
	}


	public void setPhanbo(Hashtable<String, String> phanbo) {

		this.phanbo = phanbo;
	}


	public void initDisplay() 
	{
		String query = "select scheme, thang, nam,isnull(ghichu,'') as ghichu, diengiai, mucvuot, chietkhauMucVuot, donviMucVuot, hinhthuctra, DOANHSOTHEOTHUNG, HTTT, PT_TRATL, ngayds_tungay, ngayds_denngay, khoId, phanloai, ngaytb_tungay, ngaytb_denngay, batbuocdangky, kmcp_fk  " +
				   "from TieuchithuongTL where pk_seq = '" + this.id + "'";
		
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					this.scheme = rs.getString("scheme");
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");					
					this.diengiai= rs.getString("diengiai");
					
					this.mucvuot = rs.getString("mucvuot") != null ? format.format(rs.getDouble("mucvuot")) : "";
					this.ckMucvuot = rs.getString("chietkhauMucVuot") != null ? format.format(rs.getDouble("chietkhauMucVuot")) : "";
					this.dvMucvuot = rs.getString("donviMucVuot");
					this.hinhthucTra = rs.getString("hinhthuctra");
					this.doanhsotheoThung = rs.getString("DOANHSOTHEOTHUNG");
					
					this.httt = rs.getString("HTTT");
					this.ptchietkhau = rs.getString("PT_TRATL");
					this.ghighu = rs.getString("ghichu");
					this.tungay = rs.getString("ngayds_tungay");
					this.dengay = rs.getString("ngayds_denngay");
					this.khoId = rs.getString("khoId");
					
					this.phanloai = rs.getString("phanloai");
					
					this.ngaytb_tungay = rs.getString("ngaytb_tungay");
					this.ngaytb_dengay = rs.getString("ngaytb_denngay");
					this.phaidangky = rs.getString("batbuocdangky");
					this.khoanmuccpId = rs.getString("kmcp_fk") != null ? rs.getString("kmcp_fk") : "";
				}
				rs.close();
			} 
			catch (Exception e)
			{
				System.out.println("115.Error Meg: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		//Init kenh ban hang
        ResultSet checkDh = db.get("select KBH_Fk from TieuchithuongTL_KBH where thuongtl_fk ='" + this.id + "'");
        if(checkDh != null)
        {
        	try 
        	{
        		String kbhIds = "";
				while(checkDh.next())
				{
					kbhIds += checkDh.getString("kbh_fk") + ",";
				}
				checkDh.close();
				
				if(kbhIds.trim().length() > 0)
				{
					kbhIds = kbhIds.substring(0, kbhIds.length() - 1);
					this.kbhIds = kbhIds;
				}
			} 
        	catch (Exception e) 
        	{
        		System.out.println("Exception Kenh Ban Hang: " + e.getMessage());
        	}
        }
        
        
        //Init loai khach hang
        checkDh = db.get("select LOAIKH_Fk from TieuchithuongTL_LOAIKH where thuongtl_fk ='" + this.id + "'");
        if(checkDh != null)
        {
        	try 
        	{
        		String kbhIds = "";
				while(checkDh.next())
				{
					kbhIds += checkDh.getString("LOAIKH_Fk") + ",";
				}
				checkDh.close();
				
				if(kbhIds.trim().length() > 0)
				{
					kbhIds = kbhIds.substring(0, kbhIds.length() - 1);
					this.loaiKhIds = kbhIds;
				}
			} 
        	catch (Exception e) 
        	{
        		System.out.println("Exception Loai Khach Hang: " + e.getMessage());
        	}
        }
        System.out.println(":::: LOAI KHACH HANG: " + this.loaiKhIds );
		
		this.createNdk();
		this.createRs();
		this.createKhachHangDK();
	}

	public String getPhanloai() {

		return this.phanloai;
	}


	public void setPhanloai(String phanloai) {

		this.phanloai = phanloai;
	}
	
	public Hashtable<String, String> getDieukien() {
		
		return this.dieukien;
	}

	
	public void setDieukien(Hashtable<String, String> dieukien) {
		
		this.dieukien = dieukien;
	}

	
	public Hashtable<String, String> getQuydoi() {
		
		return this.quydoi;
	}

	
	public void setQuydoi(Hashtable<String, String> quydoi) {
		
		this.quydoi = quydoi;
	}
	
	public void setNhomsanphamRs(ResultSet spRs) {
		
		this.nhomsanphamRs = spRs;
	}
	
	public ResultSet getNhomsanphamRs() {
		
		return this.nhomsanphamRs;
	}
	
	public String getNhomsanphamIds() {
		
		return this.nhomspIds;
	}
	
	public void setNhomsanphamIds(String nhomspIds) {
		
		this.nhomspIds = nhomspIds;
	}
	
	public void loadSP_NHOM() 
	{
		if(this.nhomspIds.trim().length() > 0)
		{
			String query = "select b.MA, b.TEN " + 
						   " from NHOMSANPHAM_SANPHAM a inner join SANPHAM b on a.SP_FK = b.PK_SEQ " +
						   " where a.NSP_FK = '" + this.nhomspIds + "' ";
			ResultSet rs = db.get(query);
			if(rs != null )
			{
				try 
				{
					String spMa = "";
					String spTen = "";

					while(rs.next())
					{
						spMa += rs.getString("MA") + ",,";
						spTen += rs.getString("TEN") + ",,";	
					}
					rs.close();
					
					System.out.println("---TEN SP: " + spTen + "  -- MA SP: " + spMa);
					if(spMa.trim().length() > 0)
					{
						spMa = spMa.substring(0, spMa.length() - 2);
						this.maspTra = spMa.split(",,");
						
						spTen = spTen.substring(0, spTen.length() - 2);
						this.tenspTra = spTen.split(",,");
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setKenhRs(ResultSet kenhRs) {
		
		this.kenhRs = kenhRs;
	}
	
	public ResultSet getKenhRs() {
		
		return this.kenhRs;
	}
	
	public String getKenhIds() {
		
		return this.kenhIds;
	}
	
	public void setKenhIds(String kenhIds) {
		
		this.kenhIds = kenhIds;
	}
	
	public String[] gethttt1() {
		
		return this.httt1;
	}
	
	public void sethttt1(String[] httt1) {
		
		this.httt1 = httt1;
	}
	
	public String getGhichu() {
		
		return this.ghighu;
	}
	
	public void setGhichu(String ghichu) {
		
		this.ghighu = ghichu;
	}
	
	public String getKT() {
		
		return this.kt;
	}
	
	public void setKT(String kt) {
		
		this.kt = kt;
	}
	
	public String getActiveTab() {
		
		return this.active;
	}
	
	public void setActiveTab(String active) {
		
		this.active = active;
	}
	
	public String getMucNPP() {
		
		return this.mucnpp;
	}
	
	public void setMucNPP(String MucNpp) {
		
		this.mucnpp = MucNpp;
	}
	
	public Hashtable<String, String> getPhanboTheoMucNPP1() {
		
		return this.phanbo1;
	}
	
	public void setPhanboTheoMucNPP1(Hashtable<String, String> phanbo1) {
		
		this.phanbo1 = phanbo1;
	}
	
	public Hashtable<String, String> getPhanboTheoMucNPP2() {
		
		return this.phanbo2;
	}
	
	public void setPhanboTheoMucNPP2(Hashtable<String, String> phanbo2) {
		
		this.phanbo2 = phanbo2;
	}
	
	public Hashtable<String, String> getPhanboTheoMucNPP3() {
		
		return this.phanbo3;
	}
	
	public void setPhanboTheoMucNPP3(Hashtable<String, String> phanbo3) {
		
		this.phanbo3 = phanbo3;
	}
	
	public Hashtable<String, String> getPhanboTheoMucNPP4() {
		
		return this.phanbo4;
	}
	
	public void setPhanboTheoMucNPP4(Hashtable<String, String> phanbo4) {
		
		this.phanbo4 = phanbo4;
	}
	
	public Hashtable<String, String> getPhanboTheoMucNPP5() {
		
		return this.phanbo5;
	}
	
	public void setPhanboTheoMucNPP5(Hashtable<String, String> phanbo5) {
		
		this.phanbo5 = phanbo5;
	}
	
	public String[] getDKKMTICHLUY_KHACHHANG_Id() {
		return DKKMTICHLUY_KHACHHANG_Id;
	}
	public void setDKKMTICHLUY_KHACHHANG_Id(String[] dKKMTICHLUY_KHACHHANG_Id) {
		DKKMTICHLUY_KHACHHANG_Id = dKKMTICHLUY_KHACHHANG_Id;
	}
	public ResultSet getKhDangKyRs() {
		return khDangKyRs;
	}
	public String[] getKhDcDuyet() {
		return khDcDuyet;
	}
	public void setKhDcDuyet(String[] khDcDuyet) {
		this.khDcDuyet = khDcDuyet;
	}
	public String[] getKhDcDuyetDisplay() {
		return khDcDuyetDisplay;
	}
	public void setKhDcDuyetDisplay(String[] khDcDuyetDisplay) {
		this.khDcDuyetDisplay = khDcDuyetDisplay;
	}

	
	public String getNgayTB_Tungay() {
		
		return this.ngaytb_tungay;
	}

	
	public void setNgayTB_Tungay(String tungay) {
		
		this.ngaytb_tungay = tungay;
	}

	
	public String getNgayTB_Denngay() {
		
		return this.ngaytb_dengay;
	}

	
	public void setNgayTB_Denngay(String denngay) {
		
		this.ngaytb_dengay = denngay;
	}

	
	public String getPhaidangky() {
		
		return this.phaidangky;
	}

	
	public void setPhandangky(String phaidangky) {
		
		this.phaidangky = phaidangky;
	}

	
	public String getChiavaodongia() {

		return this.chiavaodongia;
	}


	public void setChiavaodongia(String chiavaodongia) {
		
		this.chiavaodongia = chiavaodongia;
	}
	
	
	public void setKhoanmuccpRs(ResultSet khoanmucRs) {
		
		this.khoanmuccpRs = khoanmucRs;
	}

	
	public ResultSet getKhoanmuccpRs() {
		
		return this.khoanmuccpRs;
	}
	
	public void setKhoanmuccpId(String khoanmuccpId) {
		
		this.khoanmuccpId = khoanmuccpId;
	}

	
	public String getKhoanmuccpId() {
		
		return this.khoanmuccpId;
	}

	
	public String getKhongcanduyettraTL() {
		
		return this.khongcanduyet;
	}

	
	public void setKhongcanduyettraTL(String khongcanduyet) {
		
		this.khongcanduyet = khongcanduyet;
	}

	
	public void setGiobatdau(String giobatdau) {
		
		try
		{
			this.giobatdau = Integer.parseInt( giobatdau );
		}
		catch(Exception e)
		{
			this.giobatdau = 0;
		}
	}

	
	public int getGiobatdau() {
		
		return this.giobatdau;
	}

	
	public void setGioketthuc(String gioketthuc) {
		
		try
		{
			this.gioketthuc = Integer.parseInt( gioketthuc );
		}
		catch(Exception e)
		{
			this.gioketthuc = 24;
		}
	}

	
	public int getGioketthuc() {
		
		return this.gioketthuc;
	}


	public void setSoxuattoida(String soxuattoida) {

		this.soxuattoida = soxuattoida;
	}


	public String getSoxuattoida() {

		return this.soxuattoida;
	}
	
	public void setKbhRs(ResultSet kbh)
	{
		this.kenhRs = kbh;
	}

	public ResultSet getKbhRs()
	{
		return this.kenhRs;
	}

	public void setKbhIds(String kbhIds) 
	{
		this.kbhIds = kbhIds;
	}

	public String getKbhIds() 
	{
		return this.kbhIds;
	}
	
	public void setLoaikhRs(ResultSet loaiKh) {
		
		this.loaiKhRs = loaiKh;
	}

	
	public ResultSet getLoaikhRs() {
		
		return this.loaiKhRs;
	}

	
	public void setLoaikhIds(String lkhIds) {
		
		this.loaiKhIds = lkhIds;
	}

	
	public String getLoaikhIds() {
		
		return this.loaiKhIds;
	}

}

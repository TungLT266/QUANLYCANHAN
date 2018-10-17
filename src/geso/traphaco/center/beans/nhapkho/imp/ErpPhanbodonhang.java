package geso.traphaco.center.beans.nhapkho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.nhapkho.IErpPhanbodonhang;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpPhanbodonhang implements IErpPhanbodonhang
{
	String userId;
	String id;
	
	String tungay;
	String denngay;
	String ghichu;

	String msg;
	String trangthai;
	
	String khoNhanId;
	ResultSet khoNhanRs;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spSolo;
	String[] spHansudung;
	String[] spNgaysanxuat;
	String[] spNgayhethan;
	
	Hashtable<String, String> sp_chitiet;
	
	String xuatcho;
	String khId;
	ResultSet khRs;
	
	dbutils db;
	
	public ErpPhanbodonhang()
	{
		this.id = "";
		this.tungay = getDateTime();
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.msg = "";
		this.trangthai = "0";
		this.xuatcho = "0";
		this.khId = "";
		
		this.sp_chitiet = new Hashtable<String, String>();
		this.db = new dbutils();
	}
	
	public ErpPhanbodonhang(String id)
	{
		this.id = id;
		this.tungay = getDateTime();
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.msg = "";
		this.trangthai = "0";
		this.xuatcho = "0";
		this.khId = "";

		this.sp_chitiet = new Hashtable<String, String>();
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
		if(this.tungay.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn từ ngày";
			return false;
		}
		
		if( this.denngay.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đến ngày";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối / khách hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm phân bổ";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA + TRUNG SO LO
			/*for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					String ct = sp_chitiet.get(spMa[i]);
					if(ct != null)
                	{
                		String[] ctARR = ct.substring(0, ct.length() - 3).split("___");
                		for(int j = 0; j <  ctARR.length - 1; j++ )
                		{
                			for(int k = j + 1; k < ctARR.length; k++ )
                			{
                				if( ctARR[j].contains("__") && ctARR[j].contains("__") )
                				{
                					String[] _ct = ctARR[j].split("__"); 
                					String[] _ct2 = ctARR[k].split("__"); 
                    				
                    				if( _ct[1].trim().equals(_ct2[1].trim()) )
                        			{
    									this.msg = "Sản phẩm : " + spTen[i] + " , Với số lô (" + _ct2[1].trim() + ") đã bị trùng. Vui lòng kiểm tra lại ";
    									return false;
                        			}
                				}
                			}
                		}
                	}
				}
			}*/
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = " insert ERP_PHANBODONHANG(tungay, denngay, ghichu, trangthai, xuatcho,  ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values('" + this.tungay + "', '" + this.denngay + "', N'" + this.ghichu + "', '0', " + this.xuatcho + ", '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert NK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_PHANBODONHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select IDENT_CURRENT('ERP_PHANBODONHANG') as id ";
			ResultSet rs = db.get(query);
			rs.next();
			this.id = rs.getString("id");
			rs.close();
			
			query = "Insert ERP_PHANBODONHANG_DOITUONG( phanbo_fk, doituong_fk ) " + 
					" select '" + this.id + "', pk_seq from NHAPHANPHOI where pk_seq in ( " + this.khId + " ) ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_PHANBODONHANG_DOITUONG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( this.sp_chitiet != null )
			{
				Enumeration<String> keys = this.sp_chitiet.keys(); 
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					String[] arr = key.split("__");
					
					query = "insert ERP_PHANBODONHANG_SANPHAM( phanbo_fk, SANPHAM_FK, DVDL_FK, soluong, gianhap, solo, ngaysanxuat, ngayhethan ) " +
							"select '" + this.id + "', PK_SEQ, DVDL_FK, '0', '0', N'" + arr[1] + "', '', '' " +
							"from SANPHAM where MA = '" + arr[0] + "' ";
					
					System.out.println("1.Insert PB - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_PHANBODONHANG_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			/*for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					String ct = sp_chitiet.get(spMa[i]);
					if(ct != null)
                	{
                		String[] ctARR = ct.substring(0, ct.length() - 3).split("___");
                		for(int j = 0; j <  ctARR.length; j++ )
                		{
                			String[] _ct = ctARR[j].split("__"); 
                			
                			if(_ct[0].trim().length() > 0 && _ct[1].trim().length() > 0  )
                			{
	                			query = "insert ERP_PHANBODONHANG_SANPHAM( phanbo_fk, SANPHAM_FK, DVDL_FK, soluong, gianhap, solo, ngaysanxuat, ngayhethan ) " +
										"select '" + this.id + "', pk_seq, ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), '" + _ct[0].trim().replaceAll(",", "") + "', '0', N'" + _ct[1].trim() + "', '', '' " +
										"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
								
								System.out.println("1.Insert PB - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_PHANBODONHANG_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
                			}
                			
                		}
                	}
				}
			}*/
			
			//CHECK XEM CÓ SỐ LÔ NẰM TRONG DANH SÁCH KHÔNG ĐƯỢC PHÂN BỔ
			query = "select sp.TEN, pb.solo " +
					"from ERP_PHANBODONHANG_SANPHAM pb inner join ERP_HANGCHOPHANBO cpb on pb.sanpham_fk = cpb.sanpham_fk and pb.solo = cpb.solo " +
					"		inner join SANPHAM sp on pb.sanpham_fk = sp.PK_SEQ " +
					"where pb.phanbo_fk = '" + this.id + "'";
			System.out.println("::: CHECK CHO PB: " + query);
			rs = db.get(query);
			if( rs != null )
			{
				String _msg = "";
				while( rs.next() )
				{
					_msg += rs.getString("TEN") + " - " + rs.getString("solo") + " ; ";
				}
				rs.close();
				
				if( _msg.trim().length() > 0 )
				{
					this.msg = "Các sản phẩm với số lô tương ứng đang nằm trong danh sách chờ phân bổ: \n" + _msg;
					db.getConnection().rollback();
					return false;
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

	public boolean updateNK() 
	{
		if(this.tungay.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn từ ngày";
			return false;
		}
		
		if( this.denngay.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đến ngày";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối / khách hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm phân bổ";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA + TRUNG SO LO
			/*for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					String ct = sp_chitiet.get(spMa[i]);
					if(ct != null)
                	{
                		String[] ctARR = ct.substring(0, ct.length() - 3).split("___");
                		for(int j = 0; j <  ctARR.length - 1; j++ )
                		{
                			for(int k = j + 1; k < ctARR.length; k++ )
                			{
                				if( ctARR[j].contains("__") && ctARR[j].contains("__") )
                				{
                					String[] _ct = ctARR[j].split("__"); 
                					String[] _ct2 = ctARR[k].split("__"); 
                    				
                    				if( _ct[1].trim().equals(_ct2[1].trim()) )
                        			{
    									this.msg = "Sản phẩm : " + spTen[i] + " , Với số lô (" + _ct2[1].trim() + ") đã bị trùng. Vui lòng kiểm tra lại ";
    									return false;
                        			}
                				}
                			}
                		}
                	}
				}
			}*/
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query =	" Update ERP_PHANBODONHANG set tungay = '" + this.tungay + "', denngay = '" + this.denngay + "', ghichu = N'" + this.ghichu + "', " +
							" xuatcho = " + this.xuatcho + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' " + 
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_PHANBODONHANG " + query;
				db.getConnection().rollback();
				return false;
			}
				
			query = "delete ERP_PHANBODONHANG_DOITUONG where phanbo_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_PHANBODONHANG_DOITUONG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "Insert ERP_PHANBODONHANG_DOITUONG( phanbo_fk, doituong_fk ) " + 
					" select '" + this.id + "', pk_seq from NHAPHANPHOI where pk_seq in ( " + this.khId + " ) ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_PHANBODONHANG_DOITUONG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_PHANBODONHANG_SANPHAM where phanbo_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_PHANBODONHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( this.sp_chitiet != null )
			{
				Enumeration<String> keys = this.sp_chitiet.keys(); 
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					String[] arr = key.split("__");
					
					query = "insert ERP_PHANBODONHANG_SANPHAM( phanbo_fk, SANPHAM_FK, DVDL_FK, soluong, gianhap, solo, ngaysanxuat, ngayhethan ) " +
							"select '" + this.id + "', PK_SEQ, DVDL_FK, '0', '0', N'" + arr[1] + "', '', '' " +
							"from SANPHAM where MA = '" + arr[0] + "' ";
					
					System.out.println("1.Insert PB - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_PHANBODONHANG_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			/*for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					String ct = sp_chitiet.get(spMa[i]);
					if(ct != null)
                	{
                		String[] ctARR = ct.substring(0, ct.length() - 3).split("___");
                		for(int j = 0; j <  ctARR.length; j++ )
                		{
                			String[] _ct = ctARR[j].split("__"); 
                			
                			if(_ct[0].trim().length() > 0 && _ct[1].trim().length() > 0  )
                			{
	                			query = "insert ERP_PHANBODONHANG_SANPHAM( phanbo_fk, SANPHAM_FK, DVDL_FK, soluong, gianhap, solo, ngaysanxuat, ngayhethan ) " +
										"select '" + this.id + "', pk_seq, ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), '" + _ct[0].trim().replaceAll(",", "") + "', '0', N'" + _ct[1].trim() + "', '', '' " +
										"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
								
								System.out.println("1.Insert PB - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_PHANBODONHANG_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
                			}
                		}
                	}
				}
			}*/
			
			//CHECK XEM CÓ SỐ LÔ NẰM TRONG DANH SÁCH KHÔNG ĐƯỢC PHÂN BỔ
			query = "select sp.TEN, pb.solo " +
					"from ERP_PHANBODONHANG_SANPHAM pb inner join ERP_HANGCHOPHANBO cpb on pb.sanpham_fk = cpb.sanpham_fk and pb.solo = cpb.solo " +
					"		inner join SANPHAM sp on pb.sanpham_fk = sp.PK_SEQ " +
					"where pb.phanbo_fk = '" + this.id + "'";
			System.out.println("::: CHECK CHO PB: " + query);
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				String _msg = "";
				while( rs.next() )
				{
					_msg += rs.getString("TEN") + " - " + rs.getString("solo") + " ; ";
				}
				rs.close();
				
				if( _msg.trim().length() > 0 )
				{
					this.msg = "Các sản phẩm với số lô tương ứng đang nằm trong danh sách chờ phân bổ: \n" + _msg;
					db.getConnection().rollback();
					return false;
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


	public void createRs() 
	{
		Utility util = new Utility(); 
		String query = "select PK_SEQ, TEN from ERP_KHOTT  WHERE trangthai = '1' AND PK_SEQ IN "+util.quyen_khoTT(userId)+" ";
		
		this.khoNhanRs = db.get(query);
		
		//this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' AND isKHACHHANG = '0'  AND PK_SEQ != '1' order by MA + ' - ' + TEN asc ";
		if(this.xuatcho.equals("1"))
			query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' AND isKHACHHANG = '1'  AND PK_SEQ != '1' order by MA + ' - ' + TEN asc ";
		this.khRs = db.get(query);
		
	}

	private void createChuyenKho_SanPham() 
	{
		String query =  "select b.MA, b.TEN, DV.donvi, a.soluong, a.gianhap, a.solo, isnull(a.ngaysanxuat, ' ') as ngaysanxuat, isnull(a.ngayhethan, ' ') as ngayhethan, isnull(b.hansudung, 0) as hansudung    " +
						" from ERP_PHANBODONHANG_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
						"where a.PHANBO_FK = '" + this.id + "' ";
		
		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###.##");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spHANSUDUNG = "";
				
				Hashtable<String, String> sp_chitiet = new Hashtable<String, String>();
				while(spRs.next())
				{
					if(!spMA.contains(spRs.getString("MA")) && !spTEN.contains(spRs.getString("TEN")) )
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spHANSUDUNG += spRs.getString("hansudung") + "__";
					}
					
					System.out.println("::: KEY BEAN: " + ( spRs.getString("MA") + "__" + spRs.getString("SOLO") ) );
					sp_chitiet.put(spRs.getString("MA") + "__" + spRs.getString("SOLO"), "1");
					/*String ct = sp_chitiet.get(spRs.getString("MA"));
					if(ct == null)
						ct = "";
					
					if(ct.trim().length() <= 0)
					{
						ct = formater.format(spRs.getDouble("SOLUONG")) + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYSANXUAT") + "___";
						sp_chitiet.put(spRs.getString("MA"), ct);
					}
					else
					{
						ct += formater.format(spRs.getDouble("SOLUONG")) + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYSANXUAT") + "___";
						sp_chitiet.put(spRs.getString("MA"), ct);
					}*/
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
					
					spHANSUDUNG = spHANSUDUNG.substring(0, spHANSUDUNG.length() - 2);
					this.spHansudung = spHANSUDUNG.split("__");
					
					this.sp_chitiet = sp_chitiet;
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
		//System.out.println("---MA SP: " + this.spMa.length);
		
	}

	public void init() 
	{
		String query = "select tungay, denngay, ISNULL(ghichu, '') as ghichu, xuatcho, doituongId, trangthai " +
						"from ERP_PHANBODONHANG where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.ghichu = rs.getString("ghichu");
					this.xuatcho = rs.getString("xuatcho");
					this.khId = rs.getString("doituongId");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
				
				//INIT DOITUONG
				query = "select doituong_fk from ERP_PHANBODONHANG_DOITUONG where phanbo_fk = '" + this.id + "' ";
				rs = db.get(query);
				if( rs != null )
				{
					while( rs.next() )
					{
						this.khId += rs.getString("doituong_fk") + ",";
					}
					
					if( this.khId.trim().length() >= 0 )
						this.khId = this.khId.substring(0, this.khId.length() - 1);
					rs.close();
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		
		this.createRs();
		
		this.createChuyenKho_SanPham();
		
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	public String getDateTime() 
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

	
	public String[] getSpSolo() {
		
		return this.spSolo;
	}

	
	public void setSpSolo(String[] spSolo) {
		
		this.spSolo = spSolo;
	}

	
	public String[] getSpNgaysanxuat() {
		
		return this.spNgaysanxuat;
	}

	
	public void setSpNgaysanxuat(String[] spNgaysanxuat) {
		
		this.spNgaysanxuat = spNgaysanxuat;
	}

	
	public String[] getSpNgayhethan() {
		
		return this.spNgayhethan;
	}

	
	public void setSpNgayhethan(String[] spNgayhethan) {
		
		this.spNgayhethan = spNgayhethan;
	}

	
	public String[] getSpHansudung() {
		
		return this.spHansudung;
	}


	public void setSpHansudung(String[] spHansudung) {
		
		this.spHansudung = spHansudung;
	}

	
	public Hashtable<String, String> getSp_Chitiet() {
		
		return this.sp_chitiet;
	}

	
	public void setSp_Chitiet(Hashtable<String, String> sp_chitiet) {
		
		this.sp_chitiet = sp_chitiet;
	}
	
	ResultSet dvtRs;

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}
	
	public void setDvtRs(ResultSet dvtRs) {
		
		this.dvtRs = dvtRs;
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
	
	public String getXuatcho() {

		return this.xuatcho;
	}

	public void setXuatcho(String xuatcho) {
		
		this.xuatcho = xuatcho;
	}
	
	public String getKhId() 
	{
		return this.khId;
	}

	public void setKhId(String khId) 
	{
		this.khId = khId;
	}
	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	public void initChoPhanBo() 
	{
		String query =  "select b.MA, b.TEN, DV.donvi, a.soluong, a.gianhap, a.solo, isnull(a.ngaysanxuat, ' ') as ngaysanxuat, isnull(a.ngayhethan, ' ') as ngayhethan, isnull(b.hansudung, 0) as hansudung    " +
						" from ERP_HANGCHOPHANBO a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
						"where 1 = 1 ";

		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);

		NumberFormat formater = new DecimalFormat("##,###,###.##");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spHANSUDUNG = "";

				Hashtable<String, String> sp_chitiet = new Hashtable<String, String>();
				while(spRs.next())
				{
					if(!spMA.contains(spRs.getString("MA")) && !spTEN.contains(spRs.getString("TEN")) )
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spHANSUDUNG += spRs.getString("hansudung") + "__";
					}

					System.out.println("::: KEY BEAN: " + ( spRs.getString("MA") + "__" + spRs.getString("solo") ) );
					sp_chitiet.put(spRs.getString("MA") + "__" + spRs.getString("solo"), "1");
					
					/*String ct = sp_chitiet.get(spRs.getString("MA"));
					if(ct == null)
						ct = "";

					if(ct.trim().length() <= 0)
					{
						ct = formater.format(spRs.getDouble("SOLUONG")) + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYSANXUAT") + "___";
						sp_chitiet.put(spRs.getString("MA"), ct);
					}
					else
					{
						ct += formater.format(spRs.getDouble("SOLUONG")) + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYSANXUAT") + "___";
						sp_chitiet.put(spRs.getString("MA"), ct);
					}*/
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

					spHANSUDUNG = spHANSUDUNG.substring(0, spHANSUDUNG.length() - 2);
					this.spHansudung = spHANSUDUNG.split("__");

					this.sp_chitiet = sp_chitiet;
				}

			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	public boolean updateCHOPB() 
	{
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm chờ phân bổ";
			return false;
		}
		else
		{
			//CHECK TRUNG MA + TRUNG SO LO
			/*for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					String ct = sp_chitiet.get(spMa[i]);
					if(ct != null)
                	{
                		String[] ctARR = ct.substring(0, ct.length() - 3).split("___");
                		for(int j = 0; j <  ctARR.length - 1; j++ )
                		{
                			for(int k = j + 1; k < ctARR.length; k++ )
                			{
                				if( ctARR[j].contains("__") && ctARR[j].contains("__") )
                				{
                					String[] _ct = ctARR[j].split("__"); 
                					String[] _ct2 = ctARR[k].split("__"); 
                    				
                    				if( _ct[1].trim().equals(_ct2[1].trim()) )
                        			{
    									this.msg = "Sản phẩm : " + spTen[i] + " , Với số lô (" + _ct2[1].trim() + ") đã bị trùng. Vui lòng kiểm tra lại ";
    									return false;
                        			}
                				}
                			}
                		}
                	}
				}
			}*/
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
					
			String query = "delete ERP_HANGCHOPHANBO ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_HANGCHOPHANBO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if( this.sp_chitiet != null )
			{
				Enumeration<String> keys = this.sp_chitiet.keys(); 
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					String[] arr = key.split("__");
					
					query = "insert ERP_HANGCHOPHANBO( SANPHAM_FK, DVDL_FK, soluong, gianhap, solo, ngaysanxuat, ngayhethan ) " +
							"select pk_seq, DVDL_FK, '1', '0', N'" + arr[1] + "', '', '' " +
							"from SANPHAM where MA = '" + arr[0] + "' ";
					
					System.out.println("1.Insert PB - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_HANGCHOPHANBO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			/*for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					String ct = sp_chitiet.get(spMa[i]);
					if(ct != null)
                	{
                		String[] ctARR = ct.substring(0, ct.length() - 3).split("___");
                		for(int j = 0; j <  ctARR.length; j++ )
                		{
                			String[] _ct = ctARR[j].split("__"); 
                			
                			if(_ct[0].trim().length() > 0 && _ct[1].trim().length() > 0  )
                			{
	                			query = "insert ERP_HANGCHOPHANBO( SANPHAM_FK, DVDL_FK, soluong, gianhap, solo, ngaysanxuat, ngayhethan ) " +
										"select pk_seq, ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), '" + _ct[0].trim().replaceAll(",", "") + "', '0', N'" + _ct[1].trim() + "', '', '' " +
										"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
								
								System.out.println("1.Insert PB - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_HANGCHOPHANBO: " + query;
									db.getConnection().rollback();
									return false;
								}
                			}
                		}
                	}
				}
			}*/
			
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

	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		String query = "select distinct SOLO, sum(AVAILABLE) as AVAILABLE " + 
				   " from ERP_KHOTT_SP_CHITIET " + 
				   " where   AVAILABLE >0  and KHOTT_FK = '" + Utility.khoERPMapping + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " + 
				   " group by SOLO order by SOLO ";
	
		System.out.println("::: LAY SO LO: " + query);
		return db.get(query);
	}

	
	
}

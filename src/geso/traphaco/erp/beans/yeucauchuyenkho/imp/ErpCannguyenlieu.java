package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpCannguyenlieu;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
 
public class ErpCannguyenlieu implements IErpCannguyenlieu
{
	String userId;
	String id;
	String ctyId;
	String ngayyeucau;
	String lydoyeucau;
	String ghichu;
	String msg;
	String trangthai;
	
	String kyhieu;
	String sochungtu;

	ResultSet khoXuatRs;
	String khoXuatId, khoXuatTen;

	String tongSLYC;
	dbutils db;
	
	String codoituong;
	String loaidoituong;
	String doituongId;
	ResultSet doituongRs;
	
	String vtcId;
	ResultSet vitriChuyenRs;
	ResultSet vitriNhanRs;
	
	String coChiphi;
	String chiphiId;
	ResultSet chiphiRs;
	
	String spId;
	ResultSet spRs;
	
	String soloId;
	ResultSet soloRs;
	
	String[] khott_sp_ctId;
	String[] vitri;
	String[] mame;
	String[] mathung;
	String[] maphieu;
	String[] phieudt;
	String[] phieueo;
	String[] marq;
	String[] hamluong;
	String[] hamam;
	String[] tonkho;
	String[] soluong;
	
	public ErpCannguyenlieu()
	{
		this.id = "";
		this.ctyId = "";
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.ghichu = "";
		this.khoXuatId = "";
		this.msg = "";
		this.trangthai = "0";
		this.kyhieu="";
		this.sochungtu="";
		this.tongSLYC ="0";

		this.loaidoituong = "";
		this.doituongId = "";
		this.codoituong = "";
		this.coChiphi = "";
		this.chiphiId = "";
		this.vtcId = "";
		
		this.spId = "";
		this.soloId = "";
		
		this.db = new dbutils();
	}
	
	public ErpCannguyenlieu(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.ghichu = "";
		this.khoXuatId = "";
		this.msg = "";
		this.trangthai = "0";
		this.kyhieu="";
		this.sochungtu="";
		this.tongSLYC ="0";

		this.loaidoituong = "";
		this.doituongId = "";
		this.codoituong = "";
		
		this.coChiphi = "";
		this.chiphiId = "";
		this.vtcId = "";
		
		this.spId = "";
		this.soloId = "";
		
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

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
	
	public boolean createCK() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày cân";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho cân";
			return false;
		}
		
		if(this.spId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		}
		
		if(this.soloId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn số lô";
			return false;
		}
		
		if( this.codoituong.equals("1") && this.doituongId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng điều chỉnh";
			return false;
		}
				
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String _vitriId = this.vtcId.trim().length() <= 0 ? "null" : this.vtcId.trim();
			String _loaidoituongId = this.loaidoituong.trim().length() <= 0 ? "null" : this.loaidoituong.trim();
			String _doituongId = this.doituongId.trim().length() <= 0 ? "null" : this.doituongId.trim();
			
			String query = 	" insert ERP_CANNGUYENLIEU(ngaycan, lydo, trangthai, khott_fk, bin_fk, sanpham_fk, solo, ngaytao, nguoitao, ngaysua, nguoisua, loaidoituong, DOITUONG_FK ) " +
				   			" values('" + this.ngayyeucau + "',   N'" + this.lydoyeucau + "', '0', '" + this.khoXuatId + "', " + _vitriId + ", '" + this.spId + "', '" + this.soloId + "',  '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', " + _loaidoituongId + ", " + _doituongId + " ) ";
			
			System.out.println("::: CHEN CNL: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_CANNGUYENLIEU " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = "";
			query = "select IDENT_CURRENT('ERP_CANNGUYENLIEU') as ckId";
			
			ResultSet rsPxk = db.get(query);						
			if(rsPxk.next())
			{
				ycnlCurrent = rsPxk.getString("ckId");
				 
			}
			rsPxk.close();
			
			for( int i = 0; i < this.mame.length; i++ )
			{
				query = "insert ERP_CANNGUYENLIEU_SANPHAM_CHITIET( cannguyenlieu_fk, khott_sp_ct_fk, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, soluong ) " +
						"select '" + ycnlCurrent + "', '" + this.khott_sp_ctId[i] + "', ngayhethan, ngaynhapkho, '" + this.mame[i] + "', '" + this.mathung[i] + "', ISNULL( ( select pk_seq from ERP_BIN where ma = N'" + this.vitri[i] + "' and khott_fk = '" + this.khoXuatId + "' ), 0 ), " + 
						" 	'" + this.maphieu[i] + "', '" + this.phieudt[i] + "', '" + this.phieueo[i] + "', '" + this.marq[i] + "', '" + this.hamluong[i] + "', '" + this.hamam[i] + "', '" + this.soluong[i].replaceAll(",", "") + "' " + 
						"from ERP_KHOTT_SP_CHITIET where pk_seq = '" + this.khott_sp_ctId[i] + "' ";
				
				System.out.println("1.2.Insert ERP_CANNGUYENLIEU_SANPHAM_CHITIET: " + query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_CHUYENVITRI_SANPHAM_CHITIET: " + query;
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
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public boolean updateCK() 
	{ 
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày cân";
			return false;
		}
		
		if(this.khoXuatId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho cân";
			return false;
		}
		
		if(this.spId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		}
		
		if(this.soloId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn số lô";
			return false;
		}
		
		if( this.codoituong.equals("1") && this.doituongId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng điều chỉnh";
			return false;
		}
				
		try
		{
			db.getConnection().setAutoCommit(false);

			String _vitriId = this.vtcId.trim().length() <= 0 ? "null" : this.vtcId.trim();
			String _loaidoituongId = this.loaidoituong.trim().length() <= 0 ? "null" : this.loaidoituong.trim();
			String _doituongId = this.doituongId.trim().length() <= 0 ? "null" : this.doituongId.trim();
			
			String query =  " update  ERP_CANNGUYENLIEU set NGAYCAN = '" + this.ngayyeucau + "', lydo = N'" + this.lydoyeucau + "', " +
							"  khott_fk = " + this.khoXuatId + ", bin_fk = " + _vitriId + ", sanpham_fk = '" + this.spId + "', solo = N'" + this.soloId + "', ngaysua ='" + this.getDateTime() + "', nguoisua="+this.userId+"," +
							"  loaidoituong = " + _loaidoituongId + ", DOITUONG_FK = " + _doituongId +
							" where pk_seq="+this.id;
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CANNGUYENLIEU " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_CANNGUYENLIEU_SANPHAM_CHITIET where CANNGUYENLIEU_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CANNGUYENLIEU_SANPHAM_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for( int i = 0; i < this.mame.length; i++ )
			{
				query = "insert ERP_CANNGUYENLIEU_SANPHAM_CHITIET( cannguyenlieu_fk, khott_sp_ct_fk, ngayhethan, ngaynhapkho, MAME, MATHUNG, bin_fk, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, soluong ) " +
						"select '" + this.id + "', '" + this.khott_sp_ctId[i] + "', ngayhethan, ngaynhapkho, '" + this.mame[i] + "', '" + this.mathung[i] + "', ISNULL( ( select pk_seq from ERP_BIN where ma = N'" + this.vitri[i] + "' and khott_fk = '" + this.khoXuatId + "' ), 0 ), " + 
						" 	'" + this.maphieu[i] + "', '" + this.phieudt[i] + "', '" + this.phieueo[i] + "', '" + this.marq[i] + "', '" + this.hamluong[i] + "', '" + this.hamam[i] + "', '" + this.soluong[i].replaceAll(",", "") + "' " + 
						"from ERP_KHOTT_SP_CHITIET where pk_seq = '" + this.khott_sp_ctId[i] + "' ";
				
				System.out.println("1.2.Insert ERP_CANNGUYENLIEU_SANPHAM_CHITIET: " + query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_CHUYENVITRI_SANPHAM_CHITIET: " + query;
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
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public void createRs() 
	{	
		Utility util = new Utility();
		
		String query = "select PK_SEQ, MA + ', ' + TEN as TEN, LOAI from ERP_KHOTT where TrangThai = '1' and pk_seq in ( select khott_fk from ERP_KHOTT_LOAISANPHAM where loaisanpham_fk in ( 100000, 100001, 100008, 100013 ) ) ";
		query += " and pk_seq in " + util.quyen_khott(this.userId) ;
		query += " order by loai asc, TEN asc ";
			
		System.out.println("::: LAY KHO XUAT: " + query);
		this.khoXuatRs = db.get(query);

		if( this.khoXuatId.trim().length() > 0  && this.khoXuatId.trim().length() > 0 )
		{
			query = "select pk_seq, ma + ', ' + ten as ten from ERP_SANPHAM " + 
					"where loaisanpham_fk in ( 100000, 100001, 100008, 100013 ) and pk_seq in ( select SANPHAM_FK from ERP_KHOTT_SP_CHITIET where KHOTT_FK = '" + this.khoXuatId + "'  )  ";
			
			System.out.println("::: LAY SAN PHAM: " + query);
			this.spRs = db.get(query);
		}
		
		if( this.spId.trim().length() > 0 )
		{
			query = "select distinct solo from ERP_KHOTT_SP_CHITIET where KHOTT_FK = '" + this.khoXuatId + "' and sanpham_fk = '" + this.spId + "' order by solo asc ";

			System.out.println("::: LAY SOLO: " + query);
			this.soloRs = db.get(query);
		}
		
		if( this.khoXuatId.trim().length() > 0  && this.spId.trim().length() > 0 && this.soloId.trim().length() > 0  )
		{
			this.createCANNL_SanPham();
		}

	}

	private void createCANNL_SanPham() 
	{
		String query =  "select PK_SEQ as khott_sp_ct_fk, MAME, MATHUNG, isnull((select ma from ERP_BIN where pk_seq = BIN_FK ), '') as VIRI, MAPHIEU, " + 
						" 	MAPHIEUDINHTINH, PHIEUEO, MARQ, HAMLUONG, HAMAM, AVAILABLE, BOOKED, SOLUONG " +
						"from ERP_KHOTT_SP_CHITIET where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = '" + this.spId + "' and SOLO = '" + this.soloId + "' " + 
						" order by MAME, MATHUNG  ";

		System.out.println("::: init SP: " + query );

		NumberFormat formater = new DecimalFormat("#,###,###.##");
		ResultSet spRs = db.get(query);
		if(spRs != null)
		{
			try 
			{
				String _khott_sp_ct_fk = "";
				String _mame = "";
				String _mathung = "";
				String _vitri = "";
				String _maphieu = "";
				String _phieudt = "";
				String _phieueo = "";
				String _marq = "";
				String _hamluong = "";
				String _hamam = "";
				String _tonkho = "";
				String _soluong = "";
				
				while(spRs.next())
				{
					_khott_sp_ct_fk += spRs.getString("khott_sp_ct_fk") + "__";
					
					_mame += spRs.getString("MAME") + "__";
					_mathung += spRs.getString("MATHUNG") + "__";
					_vitri += spRs.getString("VIRI") + "__";
					
					_maphieu += spRs.getString("MAPHIEU") + "__";
					_phieudt += spRs.getString("MAPHIEUDINHTINH") + "__";
					_phieueo += spRs.getString("PHIEUEO") + "__";
					
					_marq += spRs.getString("MARQ") + "__";
					_hamluong += spRs.getString("HAMLUONG") + "__";
					_hamam += spRs.getString("HAMAM") + "__";
					
					_tonkho += formater.format(spRs.getDouble("SOLUONG")) + "__";
					_soluong += formater.format(spRs.getDouble("SOLUONG")) + "__";

				}
				spRs.close();
				
				System.out.println("::: MA ME: " + _mame);
				if( _mame.trim().length() > 0 )
				{
					_khott_sp_ct_fk = _khott_sp_ct_fk.substring(0, _khott_sp_ct_fk.length() - 2);
					this.khott_sp_ctId = this.mySplit(_khott_sp_ct_fk, "__");
					
					_mame = _mame.substring(0, _mame.length() - 2);
					this.mame = this.mySplit(_mame, "__");
					
					_mathung = _mathung.substring(0, _mathung.length() - 2);
					this.mathung = this.mySplit(_mathung, "__");
					
					_vitri = _vitri.substring(0, _vitri.length() - 2);
					this.vitri = this.mySplit(_vitri, "__");
					
					_maphieu = _maphieu.substring(0, _maphieu.length() - 2);
					this.maphieu = this.mySplit(_maphieu, "__");
					
					_phieudt = _phieudt.substring(0, _phieudt.length() - 2);
					this.phieudt = this.mySplit(_phieudt, "__");
					
					_phieueo = _phieueo.substring(0, _phieueo.length() - 2);
					this.phieueo = this.mySplit(_phieueo, "__");
					
					_marq = _marq.substring(0, _marq.length() - 2);
					this.marq = this.mySplit(_marq, "__");
					
					_hamluong = _hamluong.substring(0, _hamluong.length() - 2);
					this.hamluong = this.mySplit(_hamluong, "__");
					
					_hamam = _hamam.substring(0, _hamam.length() - 2);
					this.hamam = this.mySplit(_hamam, "__");
					
					_tonkho = _tonkho.substring(0, _tonkho.length() - 2);
					this.tonkho = this.mySplit(_tonkho, "__");
					
					_soluong = _tonkho.substring(0, _soluong.length() - 2);
					this.soluong = this.mySplit(_soluong, "__");
					
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void initCANNL_SanPham( boolean display ) 
	{
		String query =  "";
		
		if( !display )
		{
			query = "select a.khott_sp_ct_fk, a.ngayhethan, a.ngaynhapkho, a.MAME, a.MATHUNG, a.MAPHIEU, a.marq, a.hamluong, a.hamam, a.soluong, b.soluong as tonkho, ' ' as scheme, " +
					"	isnull( ( select ma from ERP_BIN where pk_seq = a.bin_fk ) , '' ) as vitri, isnull(a.phieudt, '') as phieudt, isnull(a.phieueo, '') as phieueo	" +
					"from ERP_CANNGUYENLIEU_SANPHAM_CHITIET a inner join ERP_KHOTT_SP_CHITIET b on a.khott_sp_ct_fk = b.pk_seq " + 
					"where cannguyenlieu_fk = '" + this.id + "' " + 
					"order by a.MAME, a.MATHUNG  ";
		}
		else
		{
			query = "select a.khott_sp_ct_fk, a.ngayhethan, a.ngaynhapkho, a.MAME, a.MATHUNG, a.MAPHIEU, a.marq, a.hamluong, a.hamam, a.soluong, a.soluongHT as tonkho, ' ' as scheme, " +
					"	isnull( ( select ma from ERP_BIN where pk_seq = a.bin_fk ) , '' ) as vitri, isnull(a.phieudt, '') as phieudt, isnull(a.phieueo, '') as phieueo	" +
					"from ERP_CANNGUYENLIEU_SANPHAM_CHITIET a  " + 
					"where cannguyenlieu_fk = '" + this.id + "' " + 
					"order by a.MAME, a.MATHUNG  ";
		}

		System.out.println("::: init SP: " + query );

		NumberFormat formater = new DecimalFormat("#,###,###.##");
		ResultSet spRs = db.get(query);
		if(spRs != null)
		{
			try 
			{
				String _khott_sp_ct_fk = "";
				String _mame = "";
				String _mathung = "";
				String _vitri = "";
				String _maphieu = "";
				String _phieudt = "";
				String _phieueo = "";
				String _marq = "";
				String _hamluong = "";
				String _hamam = "";
				String _tonkho = "";
				String _soluong = "";
				
				while(spRs.next())
				{
					_khott_sp_ct_fk += spRs.getString("khott_sp_ct_fk") + "__";
					
					_mame += spRs.getString("MAME") + "__";
					_mathung += spRs.getString("MATHUNG") + "__";
					_vitri += spRs.getString("vitri") + "__";
					
					_maphieu += spRs.getString("MAPHIEU") + "__";
					_phieudt += spRs.getString("phieudt") + "__";
					_phieueo += spRs.getString("PHIEUEO") + "__";
					
					_marq += spRs.getString("MARQ") + "__";
					_hamluong += spRs.getString("HAMLUONG") + "__";
					_hamam += spRs.getString("HAMAM") + "__";
					
					_tonkho += formater.format(spRs.getDouble("tonkho")) + "__";
					_soluong += formater.format(spRs.getDouble("soluong")) + "__";

				}
				spRs.close();
				
				if( _mame.trim().length() > 0 )
				{
					_khott_sp_ct_fk = _khott_sp_ct_fk.substring(0, _khott_sp_ct_fk.length() - 2);
					this.khott_sp_ctId = this.mySplit(_khott_sp_ct_fk, "__");
					
					_mame = _mame.substring(0, _mame.length() - 2);
					this.mame = this.mySplit(_mame, "__");
					
					_mathung = _mathung.substring(0, _mathung.length() - 2);
					this.mathung = this.mySplit(_mathung, "__");
					
					_vitri = _vitri.substring(0, _vitri.length() - 2);
					this.vitri = this.mySplit(_vitri, "__");
					
					_maphieu = _maphieu.substring(0, _maphieu.length() - 2);
					this.maphieu = this.mySplit(_maphieu, "__");
					
					_phieudt = _phieudt.substring(0, _phieudt.length() - 2);
					this.phieudt = this.mySplit(_phieudt, "__");
					
					_phieueo = _phieueo.substring(0, _phieueo.length() - 2);
					this.phieueo = this.mySplit(_phieueo, "__");
					
					_marq = _marq.substring(0, _marq.length() - 2);
					this.marq = this.mySplit(_marq, "__");
					
					_hamluong = _hamluong.substring(0, _hamluong.length() - 2);
					this.hamluong = this.mySplit(_hamluong, "__");
					
					_hamam = _hamam.substring(0, _hamam.length() - 2);
					this.hamam = this.mySplit(_hamam, "__");
					
					_tonkho = _tonkho.substring(0, _tonkho.length() - 2);
					this.tonkho = this.mySplit(_tonkho, "__");
					
					_soluong = _soluong.substring(0, _soluong.length() - 2);
					this.soluong = this.mySplit(_soluong, "__");
					
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void init( boolean display ) 
	{
		String query =  "select NGAYCAN, lydo, khott_fk, bin_fk, trangthai, sanpham_fk, solo,  " + 
						" 	loaidoituong, doituong_fk "+
						"from ERP_CANNGUYENLIEU where pk_seq = '" + this.id + "'";
		
		System.out.println("INIT CHUYENVITRI: "+query);
		ResultSet rs = db.get(query);

		try 
		{
			if(rs.next())
			{
				this.ngayyeucau = rs.getString("NGAYCAN");
				this.lydoyeucau = rs.getString("lydo");
				this.khoXuatId = rs.getString("khott_fk");
				this.spId = rs.getString("sanpham_fk");
				this.soloId = rs.getString("solo");
				this.vtcId = rs.getString("bin_fk") == null ? "" : rs.getString("bin_fk");
				this.trangthai = rs.getString("trangthai");

				this.loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				this.doituongId = rs.getString("doituong_fk") == null ? "" : rs.getString("doituong_fk");
			}
			rs.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		this.initCANNL_SanPham( display );
		//this.createRs();
		
		query = "select PK_SEQ, MA + ', ' + TEN as TEN, LOAI from ERP_KHOTT where TrangThai = '1' and pk_seq in ( select khott_fk from ERP_KHOTT_LOAISANPHAM where loaisanpham_fk in ( 100000, 100001, 100008, 100013 ) ) ";
		query += " order by loai asc, TEN asc ";
			
		System.out.println("::: LAY KHO XUAT: " + query);
		this.khoXuatRs = db.get(query);

		if( this.khoXuatId.trim().length() > 0  && this.khoXuatId.trim().length() > 0 )
		{
			query = "select pk_seq, ma + ', ' + ten as ten from ERP_SANPHAM " + 
					"where loaisanpham_fk in ( 100000, 100001, 100008, 100013 ) and pk_seq in ( select SANPHAM_FK from ERP_KHOTT_SP_CHITIET where KHOTT_FK = '" + this.khoXuatId + "'  )  ";
			
			System.out.println("::: LAY SAN PHAM: " + query);
			this.spRs = db.get(query);
		}
		
		if( this.spId.trim().length() > 0 )
		{
			query = "select distinct solo from ERP_KHOTT_SP_CHITIET where KHOTT_FK = '" + this.khoXuatId + "' and sanpham_fk = '" + this.spId + "' order by solo asc ";

			System.out.println("::: LAY SOLO: " + query);
			this.soloRs = db.get(query);
		}
		
	}
	
	public void DBclose() {
		
		try{
			
			if(khoXuatRs!=null){
				khoXuatRs.close();
			}
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

	public String getKhoXuatTen() {
		return this.khoXuatTen;
	}

	
	public void setKhoXuatTen(String khoxuattt) {
		this.khoXuatTen = khoxuattt;
	}

	public String getGhichu() {
		
		return this.ghichu;
	}

	
	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	public String getTongSLYC() 
	{
		return this.tongSLYC;
	}

	public void setTongSLYC(String tongSLYC) 
	{
		this.tongSLYC = tongSLYC;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}

	
	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
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

	public String getDoituongId() {
		
		return this.doituongId;
	}

	
	public void setDoituongId(String doituongId) {
		
		this.doituongId = doituongId;
	}

	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String query = "";
		query =  " 	select ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, MAME, MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( ( select ma from ERP_BIN where pk_seq = ct.bin_fk ), '' ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo   "+
				 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
				 "\n 	where KHOTT_FK = '" + this.khoXuatId + "' and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
				 		"  and ngaynhapkho <= '" + this.ngayyeucau + "'  ";
		
		if( this.vtcId.trim().length() > 0 )
			query += " and ct.bin_fk = '" + this.vtcId + "' ";
		
		query += "\n order by NGAYHETHAN asc  ";
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
		return db.get(query);
		
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

	
	public String getVitrichuyenId() {
		
		return this.vtcId;
	}

	
	public void setVitrichuyenId(String vitriChuyenId) {
		
		this.vtcId = vitriChuyenId;
	}

	
	public ResultSet getVitriChuyenRs() {
		
		return this.vitriChuyenRs;
	}

	
	public void setVitriChuyenRs(ResultSet vitriChuyenRs) {
		
		this.vitriChuyenRs = vitriChuyenRs;
	}

	
	public String getSanphamId() {
		
		return this.spId;
	}

	
	public void setSanphamId(String spId) {
		
		this.spId = spId;
	}

	
	public ResultSet getSanphamRs() {
		
		return this.spRs;
	}

	
	public void setSanphamRs(ResultSet spRs) {
		
		this.spRs = spRs;
	}

	
	public String getSoloId() {
		
		return this.soloId;
	}

	
	public void setSoloId(String soloId) {
		
		this.soloId = soloId;
	}

	
	public ResultSet getSoloRs() {
		
		return this.soloRs;
	}

	
	public void setSoloRs(ResultSet soloRs) {
		
		this.soloRs = soloRs;
	}

	
	public String[] getSpVitri() {
		
		return this.vitri;
	}

	
	public void setSpVitri(String[] vitri) {
		
		this.vitri = vitri;
	}

	
	public String[] getSpMame() {
		
		return this.mame;
	}

	
	public void setSpMame(String[] mame) {
		
		this.mame = mame;
	}

	
	public String[] getSpMathung() {
		
		return this.mathung;
	}

	
	public void setSpMathung(String[] mathung) {
		
		this.mathung = mathung;
	}

	
	public String[] getSpMaphieu() {
		
		return this.maphieu;
	}

	
	public void setSpMaphieu(String[] maphieu) {
		
		this.maphieu = maphieu;
	}

	
	public String[] getSpPhieuDT() {
		
		return this.phieudt;
	}

	
	public void setSpPhieuDT(String[] phieudt) {
		
		this.phieudt = phieudt;
	}

	
	public String[] getSpPhieuEO() {
		
		return this.phieueo;
	}

	
	public void setSpPhieuEO(String[] phieuEO) {
		
		this.phieueo = phieuEO;
	}

	
	public String[] getSpMARQ() {
		
		return this.marq;
	}

	
	public void setSpMARQ(String[] marq) {
		
		this.marq = marq;
	}

	
	public String[] getSpHamluong() {
		
		return this.hamluong;
	}

	
	public void setSpHamluong(String[] hamluong) {
		
		this.hamluong = hamluong;
	}

	
	public String[] getSpHamam() {
		
		return this.hamam;
	}

	
	public void setSpHamam(String[] hamam) {
		
		this.hamam = hamam;
	}

	
	public String[] getSpTonkho() {
		
		return this.tonkho;
	}

	
	public void setSpTonkho(String[] spTonkho) {
		
		this.tonkho = spTonkho;
	}

	
	public String[] getSpSoluong() {
		
		return this.soluong;
	}

	
	public void setSpSoluong(String[] spSoluong) {
		
		this.soluong = spSoluong;
	}
	
	public String[] mySplit(String key, String operator) 
	{
		boolean exit = false;
		if( key.endsWith(operator) )
		{
			key = key + " ";
			exit = true;
		}
		
		String[] arr = key.split(operator);
		if( arr.length > 0 && exit )
			arr[ arr.length - 1 ] = arr[ arr.length - 1 ].trim();
		
		return arr;
	}

	public String[] getSpId()
	{
		return this.khott_sp_ctId;
	}

	public void setSpId(String[] id)
	{
		this.khott_sp_ctId = id;
	}

	 
}

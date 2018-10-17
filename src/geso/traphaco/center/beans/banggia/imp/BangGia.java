package geso.traphaco.center.beans.banggia.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.beans.banggia.IBangGia;
import geso.traphaco.center.beans.banggia.IBangGia_Sp;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.util.UtilitySyn;

public class BangGia implements IBangGia
{
	String userId;
	String id;
	
	String ten;
	
	String chietKhau;
	
	String[] nppIdCks = new String[0];
	String[] nppChietKhaus = new String[0];

	public String getChietKhau() 
	{
		return chietKhau;
	}


	public void setChietKhau(String chietKhau) 
	{
		this.chietKhau = chietKhau;
	}


	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	String nppId;
	ResultSet nppRs;
	
	String trangthai;
	String msg;
	
	List<IBangGia_Sp> spList;
	dbutils db;
	
	NumberFormat formater = new DecimalFormat("##,###,###");
	NumberFormat formater2 = new DecimalFormat("##,###,###.####");
	public BangGia()
	{
		this.userId = "";
		this.id = "";
		this.ten = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.trangthai = "1";
		this.msg = "";
		this.nppId = "";
		this.tungay="";
		this.chietKhau ="";
		this.spList = new ArrayList<IBangGia_Sp>();
		this.db = new dbutils();
	}
	
	
	public BangGia(String id)
	{
		this.userId = "";
		this.id = id;
		this.kbhId = "";
		this.dvkdId = "";
		this.trangthai = "1";
		this.msg = "";
		this.nppId = "";
		this.tungay ="";
		this.chietKhau ="";
		this.spList = new ArrayList<IBangGia_Sp>();
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
		String query = "select TEN, DVKD_FK, KENH_FK, TRANGTHAI,TuNgay,ChietKhau from BANGGIAMUANPP where PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
	
			try 
			{
				while(rs.next())
				{
					this.ten = rs.getString("TEN");
					this.dvkdId = rs.getString("DVKD_FK");
					this.kbhId = rs.getString("KENH_FK");
					this.trangthai = rs.getString("TRANGTHAI");
					
					this.tungay = rs.getString("TuNgay");
					this.chietKhau = rs.getFloat("ChietKhau")==0?"": formater.format(rs.getDouble("ChietKhau") ) ;
					
				}
				rs.close();
			}
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
				e.printStackTrace();
			}
		//init KhachHangApDung
		query = "select NPP_FK from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK = '" + this.id + "' ";
		System.out.println("Lay khach hang: " + query);
		ResultSet rsCheck = db.get(query);
		try 
		{
			String khApDung = "";
			int count = 0;
			while(rsCheck.next())
			{
				khApDung += rsCheck.getString("NPP_FK") + ",";
				count++;
				
			}
			rsCheck.close();
			
			if(khApDung.trim().length() > 0)
			{
					khApDung = khApDung.substring(0, khApDung.length() - 1);
					this.nppId = khApDung;
			}
		} 
		catch (Exception e) {e.printStackTrace();}
	//	System.out.println("Khach hang nppId: " + this.nppId);
		this.createRs();
	}
	
	public boolean createBanggia()
	{	
		try 
		{
			
			if(checkExits(db))
			{
				this.msg = "Ngày bắt đầu áp dụng bảng giá không được phép nhỏ hơn từ ngày áp dụng của bảng giá đang có";
				return false;
			}
			
			
			if(this.ten.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}
			
			if(this.dvkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			
			/*if(this.kbhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kênh bán hàng";
				return false;
			}*/
			
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong bảng giá";
				return false;
			}
			
			if(this.nppId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng áp dụng bảng giá";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into BANGGIAMUANPP(ten, ngaytao, ngaysua, nguoitao, nguoisua, dvkd_fk, KENH_FK, trangthai,TuNgay) " +
							"values(N'" + this.ten + "','" + getDateTime() + "','" + getDateTime() + "','" + this.userId + "', " +
								"'" + this.userId + "','" + this.dvkdId + "'," + (this.kbhId.trim().length() ==0?"NULL":this.kbhId) + ", '" + this.trangthai + "','"+tungay+"')";
			
			System.out.println("__Tao moi ERP_BANGGIABAN: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới BANGGIA " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rsBg = this.db.get("select IDENT_CURRENT('BANGGIAMUANPP') as bgbanId");
			rsBg.next();
			
			String bgbanId = rsBg.getString("bgbanId");		
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IBangGia_Sp sp = this.spList.get(i);
				String gia = sp.getGiaban().trim().length() <= 0 ? "0" : sp.getGiaban().trim().replaceAll(",", "");
				String chonban = sp.getChonban();
				if(gia.equals("0"))
					chonban = "0";
				
				query = "insert into bgmuanpp_sanpham (bgmuanpp_fk,sanpham_fk,giamuanpp,trangthai,chietkhau)" +
						"values('" + bgbanId + "', '" + sp.getIdsp() + "','" +gia + "','"+chonban+"',"+sp.getSpchietkhau()+")";
					if(!this.db.update(query))
					{				
						this.msg = "Không thể tạo mới BANGGIA_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
			}
				
			if(this.nppId.trim().length() > 0)
			{
				query = "insert into BANGGIAMUANPP_NPP(BANGGIAMUANPP_FK, NPP_FK) " +
						"select '" + bgbanId + "', pk_seq from NhaPhanPhoi where pk_seq in ( " + this.nppId  + " ) ";
				
				if(!this.db.update(query))
				{
					this.msg = "Không thể tạo mới BANGGIAMUANPP_NPP " + query;
					this.db.getConnection().rollback();
					return false;
				}
				if( this.nppIdCks!=null && this.nppChietKhaus!=null )
				{
					for( int k=0;k< this.nppIdCks.length;k++)
					{
						query=	"	update 	BANGGIAMUANPP_NPP " +
								"	set chietkhau="+this.nppChietKhaus[k] +" " +
								"	where 	BANGGIAMUANPP_FK="+bgbanId+" and " +
								"			NPP_FK = "+this.nppIdCks[k]+" ";
						if(!this.db.update(query))
						{
							this.msg = "Không thể tạo mới BANGGIAMUANPP_NPP ( cap nhat chiet khau)  " + query;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
				
			}			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "BANGGIAMUANPP", "BANGGIAMUANPP", "PK_SEQ", id, "0", false);
			UtilitySyn.SynData(db, "BGMUANPP_SANPHAM", "BGMUANPP_SANPHAM", "BGMUANPP_FK", id, "0", false);
			UtilitySyn.SynData(db, "BANGGIAMUANPP_NPP", "BANGGIAMUANPP_NPP", "BANGGIAMUANPP_FK", id, "0", false);
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
	
	
	private boolean checkHOPLE()
	{
		String query = " select count(*) as soDong " +
				       " from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.pk_seq = b.dondathang_fk " +
				       " where a.tungay >= '" + this.tungay + "'" +
				       		" and sanpham_fk in ( select SANPHAM_FK from BGMUANPP_SANPHAM where BGMUANPP_FK = '" + this.id + "' and GIAMUANPP != 0 ) " +
				       		" and a.npp_fk in ( select NPP_FK from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK = '" + this.id + "'  )  ";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				if(rs.next())
				{
					if( rs.getInt("soDong") >= 1 )
						return false;
				}
				rs.close();
			} 
			catch (Exception e) {e.printStackTrace(); }
		}
		
		return true;
		
	}
	
	public boolean updateBanggia() 
	{
		try 
		{
			
			if(!checkHOPLE())
			{
				this.msg = "Bảng giá đã được áp dụng. Bạn không thể thay đổi.";
				return false;
			}
			
			//CHECK NPP DA CO NGAY AP DUNG TU NGAY ROI THI KHONG DUOC THAY DOI TU NGAY
			//CHECK XEM CO NPP NAO DUOC AP DUNG BANG GIA TRUNG TU NGAY CHUA
			String query = "select a.TUNGAY, c.MA, COUNT(a.PK_SEQ) as soBANGGIA  " +
							"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
								"inner join NHAPHANPHOI c on b.NPP_FK = c.PK_SEQ  " +
							"where a.PK_SEQ != '" + this.id + "' and a.TUNGAY = ( select tungay from BANGGIAMUANPP where pk_seq = '" + this.id + "' ) " +
									"and a.DVKD_FK = '" + dvkdId + "' AND B.NPP_FK IN (SELECT NPP_FK FROM BANGGIAMUANPP_NPP WHERE BANGGIAMUANPP_FK='"+this.id+"' )  " +
							"group by a.TUNGAY, c.MA " +
							"having COUNT(a.PK_SEQ) >= 1 ";
			
			System.out.println("___CHECK NPP SELECTED: " + query);
			ResultSet rsNPP = db.get(query);
			String nppKhongHopLe = "";
			String tungay = "";
			
			if(rsNPP != null)
			{
				while(rsNPP.next())
				{
					nppKhongHopLe += rsNPP.getString("MA") + ",";
					System.out.println("____SO BG KHONG HOP LE: " + nppKhongHopLe);
					
					tungay = rsNPP.getString("TUNGAY");
				}
				rsNPP.close();
			}
			
			System.out.println("____NPP KHONG HOP LE: " + nppKhongHopLe);
			if(nppKhongHopLe.trim().length() > 0)
			{
				this.msg = "Các nhà phân phối ( " + nppKhongHopLe + " ) đã tồn tại trong bảng giá có từ ngày ( " + tungay + " ) ";
				System.out.println("ERROR: " + this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			query = 
				"	 SELECT COUNT(*) AS SoDong "+    
				"	 FROM DONDATHANG DH "+ 
				"	 WHERE EXISTS "+
				"	 ( "+
				"		SELECT * FROM "+
				"		BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
				"		B ON A.PK_SEQ=B.BANGGIAMUANPP_FK  "+
				"		WHERE  B.NPP_FK=DH.NPP_FK "+
				//"		AND A.KENH_FK=DH.KBH_FK AND A.DVKD_FK=DH.DVKD_FK "+
				"		AND  A.DVKD_FK=DH.DVKD_FK "+
				"		AND A.TUNGAY<=DH.NGAYDAT AND DH.TRANGTHAI!=6 "+
				"		AND A.PK_SEQ='"+id+"' AND A.TUNGAY!='"+this.tungay+"' "+
				"	 ) " ;
			System.out.println("[DONDATHANG]" + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				this.msg = "Bảng giá đã được sử dụng trong đơn đặt hàng ,vui lòng kiểm tra lại !";
				return false;
			}
			query = 
			" SELECT COUNT(*) AS SoDong "+    
			"  FROM DONHANG DH "+
			" WHERE EXISTS "+
			"  ( "+
			"	SELECT * FROM  "+
			"	BANGGIAMUANPP A INNER JOIN BANGGIAMUANPP_NPP "+ 
			"	B ON A.PK_SEQ=B.BANGGIAMUANPP_FK "+
			"	WHERE  B.NPP_FK=DH.NPP_FK "+
			//"	AND A.KENH_FK=DH.KBH_FK "+
			"	AND A.TUNGAY<=DH.NGAYNHAP AND DH.TRANGTHAI!=2 "+
			"	AND A.PK_SEQ='"+id+"' AND A.TUNGAY!='"+this.tungay+"' "+
			"  ) ";
			
			rs = db.get(query);
			while (rs.next())
			{
				sodong = rs.getInt("sodong");
			}
			if (rs != null)
				rs.close();
			if (sodong > 0)
			{
				this.msg = "Bảng giá đã được sử dụng trong đơn hàng ,vui lòng kiểm tra lại !";
				return false;
			}
			
			
			
			if(this.ten.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tên bảng giá";
				return false;
			}
			
			if(this.dvkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			
			if(this.kbhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kênh bán hàng";
				return false;
			}
			
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong bảng giá";
				return false;
			}
			
			if(this.nppId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng áp dụng bảng giá";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "update BANGGIAMUANPP set   TuNgay='"+this.tungay+"', ten = N'" + this.ten + "', ngaysua = '" + getDateTime() + "', " +
					"nguoisua = '" + this.userId + "', trangthai = '" + this.trangthai + "',ChietKhau="+0+" where pk_seq = '" + this.id + "'";
			
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BANGGIA: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			String bgbanId=this.id;
			
			query="delete from BGMUANPP_SANPHAM where BGMUANPP_FK="+bgbanId +" ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BGMUANPP_SANPHAM: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query="delete from BANGGIAMUANPP_NPP where BANGGIAMUANPP_FK="+bgbanId +" ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BANGGIAMUANPP_NPP: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < this.spList.size(); i++)
			{
				IBangGia_Sp sp = this.spList.get(i);
				String gia = sp.getGiaban().trim().length() <= 0 ? "0" : sp.getGiaban().trim().replaceAll(",", "");
				String chonban = sp.getChonban();
				if(gia.equals("0"))
					chonban = "0";
				
				query = "insert into BGMUANPP_SANPHAM(BGMUANPP_FK, sanpham_fk, GiaMuaNPP,trangthai,chietkhau) " +
						"values('" + bgbanId + "', '" + sp.getIdsp() + "','" +gia + "','"+chonban+"',"+sp.getSpchietkhau()+")";
				if(chonban.equals("1"))
					System.out.println("2.Update: " + query);
				if(!this.db.update(query))
					{				
						this.msg = "Không thể tạo mới BGMUANPP_SANPHAM " + query;
						db.getConnection().rollback();
						return false;
					}
			}
				
			if(this.nppId.trim().length() > 0)
			{
				query = "insert into BANGGIAMUANPP_NPP(BANGGIAMUANPP_FK, NPP_FK) " +
						"select '" + bgbanId + "', pk_seq from NhaPhanPhoi where pk_seq in ( " + this.nppId  + " ) ";
				System.out.println("3.Update: " + query);
				if(!this.db.update(query))
				{
					this.msg = "Không thể tạo mới BANGGIAMUANPP_NPP " + query;
					this.db.getConnection().rollback();
					return false;
				}
				//System.out.println("CHuan bi vo update xhiet khau");
			//	System.out.println("CHuan bi vo update xhiet khau"+ this.nppIdCks.length + " " + this.nppChietKhaus);
				if( this.nppIdCks!=null && this.nppChietKhaus!=null )
				{
					for( int k=0;k< this.nppIdCks.length;k++)
					{	
						query=	"	update 	BANGGIAMUANPP_NPP " +
								"	set chietkhau="+this.nppChietKhaus[k] +" " +
								"	where 	BANGGIAMUANPP_FK="+bgbanId+" and " +
								"			NPP_FK = "+this.nppIdCks[k]+" ";
						
						System.out.println("cau chiet khau " + query);
						if(!this.db.update(query))
						{
							this.msg = "Không thể tạo mới BANGGIAMUANPP_NPP ( cap nhat chiet khau)  " + query;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			/*UtilitySyn.SynData(db, "BANGGIAMUANPP", "BANGGIAMUANPP", "PK_SEQ", id, "1", false);
			UtilitySyn.SynData(db, "BGMUANPP_SANPHAM", "BGMUANPP_SANPHAM", "BGMUANPP_FK", id, "2", true);
			UtilitySyn.SynData(db, "BGMUANPP_SANPHAM", "BGMUANPP_SANPHAM", "BGMUANPP_FK", id, "0", false);
			UtilitySyn.SynData(db, "BANGGIAMUANPP_NPP", "BANGGIAMUANPP_NPP", "BANGGIAMUANPP_FK", id, "2", true);
			UtilitySyn.SynData(db, "BANGGIAMUANPP_NPP", "BANGGIAMUANPP_NPP", "BANGGIAMUANPP_FK", id, "0", false);
			*/
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
	
	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
			if(nppRs!=null)nppRs.close();
			if(kbhRs!=null)kbhRs.close();
			if(dvkdRs!=null)dvkdRs.close();
		} 
		catch (Exception e) {e.printStackTrace();}
		
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
	
	public String getTen() {
		
		return this.ten;
	}

	
	public void setTen(String ten) {
		
		this.ten = ten;
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

	public List<IBangGia_Sp> getSpList() {
		
		return this.spList;
	}


	public void setSpList(List<IBangGia_Sp> spList) {
		
		this.spList = spList;
	}
	
	public void createRs()
	{
		String query = 
			"	select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId  from donvikinhdoanh a,nhacungcap_dvkd b  where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' "+ 
			"	order by a.donvikinhdoanh ";
		this.dvkdRs  =  this.db.get(query);

		this.kbhRs  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
		
		//if(this.kbhId.trim().length() > 0 && this.dvkdId.length()>0&&this.tungay.length()>0)	// BỎ KÊNH BÁN HÀNG
		if(this.dvkdId.length()>0&&this.tungay.length()>0)
		{	
			// lấy những nhà NPP chưa được chia trong bảng giá có hiệu lực từ ngày
			query = " SELECT distinct 0 as chietkhaunpp, a.PK_SEQ, a.Ma , a.Ten, isnull(a.diachi, 'NA') as diachi, isnull(a.dienthoai, 'NA') as dienthoai  " +
					"from NhaPhanPhoi a LEFT join NhaPP_KBH b on b.npp_fk=a.pk_seq  " +
					" LEFT join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK "+
					" LEFT join nhacungcap_dvkd d on d.PK_SEQ = c.NCC_DVKD_FK " +	
					" where a.TrangThai = '1'  ";
					//" where a.TrangThai = '1' and b.KBH_FK = '" + this.kbhId + "' ";					// BỎ KÊNH BÁN HÀNG
					//"and d.CHECKED=1 and d.dvkd_fk='"+this.dvkdId+"'    ";
			
			query += " and a.pk_seq not in ( select NPP_FK from BANGGIAMUANPP_NPP " +
										"  where BANGGIAMUANPP_FK in ( select pk_seq from BANGGIAMUANPP where  TuNgay='"+this.tungay+"' and  TRANGTHAI = '1' ))";
			if(this.id.trim().length() > 0)
			{
				// lấy những nhà NPP đã được chia trong bảng giá có hiệu lực từ ngày
				query += " union " +
							" SELECT isnull((select CHIETKHAU from BANGGIAMUANPP_NPP where NPP_FK=a.pk_seq and BANGGIAMUANPP_FK="+this.id+ "),0) as chietkhaunpp,a.PK_SEQ,  a.Ma, a.Ten, isnull(a.diachi, 'NA') as diachi, isnull(a.dienthoai, 'NA') as dienthoai   " +
							" from NhaPhanPhoi a  " +							
							" where  a. pk_seq in ( select NPP_FK from BANGGIAMUANPP_NPP   where BANGGIAMUANPP_FK = '" + this.id + "' )  ";
			}
			query += " order by a.ten asc ";
			
			System.out.println("___Khoi tao khach hang: " + query);
			this.nppRs = db.get(query);
		}
		
		String querySP = "";
		//if(this.dvkdId.trim().length() > 0 && this.kbhId.trim().length() > 0 && this.nppId.trim().length() > 0 ) // BỎ KÊNH BÁN HÀNG
		if(this.dvkdId.trim().length() > 0  && this.nppId.trim().length() > 0 ) 
		{
			String banggia_fk = this.id.trim().length() <= 0 ? "0" : this.id.trim();
			
			querySP = 
				"	select  isnull(c.chietkhau,0) as chietkhausp,a.PK_SEQ as spId, a.MA as spMa, isnull(a.TEN, '') as spTen , ISNULL(b.donvi, 'NA') as donvi, isnull(c.giaMuaNPP, 0) as giaban,isnull(c.trangthai, 0) as trangthai "+
				"	from ERP_SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ "+   
				"		left join BGMUANPP_SANPHAM c on a.pk_seq = c.sanpham_fk  "+
				"		and c.BGMUANPP_FK = '"+banggia_fk+"'     "+
				"	where a.DVKD_FK = '"+this.dvkdId+"' and a.trangthai = '1' "  ;
			querySP +=	" order by spMa asc, spTen asc  ";
			
			System.out.println("--- Lay SP: " + querySP);
			
			
			if(this.spList.size() <= 0)
			{
				ResultSet rsSp = db.get(querySP);
				if(rsSp != null)
				{
					try 
					{
						List<IBangGia_Sp> spList = new ArrayList<IBangGia_Sp>();
						
						while(rsSp.next())
						{
							IBangGia_Sp sp = new BangGia_Sp();
							sp.setIdsp(rsSp.getString("spId"));
							sp.setMasp(rsSp.getString("spMa"));
							sp.setTensp(rsSp.getString("spTen"));
							sp.setDonvi(rsSp.getString("donvi"));
							sp.setGiaban( rsSp.getDouble("giaban")==0?  "" : formater2.format(rsSp.getDouble("giaban"))  );	
							sp.setChonban(rsSp.getString("trangthai"));
							sp.setSpchietkhau(rsSp.getString("chietkhausp"));
							spList.add(sp);
						}
						rsSp.close();
						
						this.spList = spList;
					} 
					catch (Exception e) 
					{
						System.out.println("Exception Khoi Tao: " + e.getMessage());
						e.printStackTrace();
					}
					
				}
			}
			
		}
		
	}
	
	private boolean checkExits(dbutils db) 
	{
		String query=
				//"select count(*) as sodong from BANGGIAMUANPP bg where DVKD_FK="+this.dvkdId+" and KENH_FK="+this.kbhId+" " +		//BỎ KÊNH BÁN HÀNG
				"select count(*) as sodong from BANGGIAMUANPP bg where DVKD_FK="+this.dvkdId+"" +
				" AND TUNGAY > '" + this.tungay + "'    ";
		
		if(this.id.length() > 0)
			query+= " and pk_seq != " + this.id +" ";
		
		if(this.nppId.length()>0)
		{
			query+= 
			 " and exists "+
			 " ( "+
			 "	select * from BANGGIAMUANPP_NPP bgnpp "+
			 "	where bg.PK_SEQ=bgnpp.BANGGIAMUANPP_FK "+
			 "	and NPP_FK in ("+this.nppId+") "+
			 "  ) "; 
		}
		
		ResultSet rs = db.get(query);
		int sodong = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			} catch(Exception e) {e.printStackTrace(); sodong = 0; }
		}
		if(sodong > 0)
			return true;
		return false;
	}


	String tungay;
	
	public String getTuNgay() 
	{
		return tungay;
	}


	
	public void setTuNgay(String tungay) 
	{
		this.tungay =tungay;
	}
	
	
	public ResultSet getNppRs() 
	{
		return nppRs;
	}


	
	public void setNppRs(ResultSet NppRs) 
	{
		this.nppRs	= NppRs;
	}


	
	public String getNppId() 
	{
		return this.nppId;
	}


	
	public void setNppId(String nppId) 
	{
		this.nppId =nppId;
	}


	
	public String[] getNppIdCks() {
		return this.nppIdCks;
	}
	
	public void setNppIdCks(String[] ids) {
		this.nppIdCks = ids;
	}
	
	public String[] getNppChietKhaus() {
		return this.nppChietKhaus;
	}


	
	public void setNppChietKhaus(String[] cks) {
		this.nppChietKhaus = cks;
	}
	
}

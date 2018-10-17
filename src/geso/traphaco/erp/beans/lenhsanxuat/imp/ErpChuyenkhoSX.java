package geso.traphaco.erp.beans.lenhsanxuat.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuat.IErpChuyenkhoSX;
import geso.traphaco.erp.beans.lenhsanxuat.IYeucau;
import geso.traphaco.erp.beans.nhapkho.IKhu_Vitri;
import geso.traphaco.erp.beans.phieuxuatkho.ISanpham;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.Sanpham;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Utility_Kho;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
public class ErpChuyenkhoSX implements IErpChuyenkhoSX
{
	String userId;
	String id;
	String ngayyeucau;
	String lydoyeucau;
	String ghichu;
	String noidungxuat;
	String msg;
	String diachi;
	String isnhanHang;
	String trangthai;
	String Nguoinhanhang;
	String Khoxuat;
	String Nguoinhan;
	String IsChuyenHangSX;
	String kyhieu;
	String sochungtu;
	String lenhdieudong;
	String ngaydieudong;
	String nguoidieudong;
	String veviec;
	String nguoivanchuyen;
	String phuongtien;
	String hopdong;
	
	String task;
	ResultSet tsddRs;
	String tsddId;
	String khoXuatId, khoXuatTen;
	ResultSet khoXuatRs;
	ResultSet RsKhuKhoNhan;
	
	String khoNhanId, khoNhanTen;
	ResultSet khoNhanRs;
	
	String nccXuatId;
	ResultSet nccXuatRs;
	
	String nccNhanId;
	ResultSet nccNhanRs;
	
	String lsxIds;
	String DvkdId;
	
	ResultSet lsxRs;
	
	List<ISanpham> SPList;
	List<IYeucau> spList;
	List<IYeucau> spChoNhapList;
	
	List<IKhu_Vitri> khuList;
	List<IKhu_Vitri> vitriList;
	NumberFormat formatter1 = new DecimalFormat("#,###,###.######");
	String ndxId;
	ResultSet ndxRs;
	
	String tongSLYC;
	String tongSLnhan;
	
	String trangthaisp;  // -1 khong dat, 1 dat
	dbutils db;
	Utility_Kho util_kho=new Utility_Kho();
	String YeucauchuyenkhoId;
	String IsChuyenhangkhongdat;
	boolean ChoPhepchuyenhangdat;
	
	String IsQuanlykhuvucKhoxuat;
	String IsQuanlykhuvucKhonhap;
	Utility  util=new Utility();
	public ErpChuyenkhoSX()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.ghichu = "";
		this.Nguoinhan="";
		this.YeucauchuyenkhoId="";
		this.khoXuatId = "";
		this.IsChuyenHangSX="";
		this.task="";
		this.khoNhanId = "";
		this.lsxIds = "";
		this.msg = "";
		this.isnhanHang = "0";
		this.trangthai = "0";
		this.trangthaisp = "";
		this.DvkdId="";
		this.diachi="";
		this.Nguoinhanhang="";
		this.Khoxuat="";
		
		this.spList = new ArrayList<IYeucau>();
		this.SPList = new ArrayList<ISanpham>();
		this.spChoNhapList = new ArrayList<IYeucau>();
		
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();
		
		this.nccXuatId = "";
		this.nccNhanId = "";
		this.ndxId = "";
		
		this.tongSLYC ="0.000";
		this.tongSLnhan ="0.000";
		this.noidungxuat ="";
		this.IsQuanlykhuvucKhoxuat="";
		this.IsQuanlykhuvucKhonhap="";
		this.kyhieu="";
		this.sochungtu="";
		this.lenhdieudong="";
		this.ngaydieudong="";
		this.nguoidieudong="";
		this.veviec="";
		this.nguoivanchuyen="";
		this.phuongtien="";
		this.hopdong="";
		this.tsddId = "";
		this.db = new dbutils();
	}
	
	public ErpChuyenkhoSX(String id)
	{
		this.id = id;
		this.IsQuanlykhuvucKhoxuat="";
		this.IsQuanlykhuvucKhonhap="";
		this.ngayyeucau = getDateTime();
		this.lydoyeucau = "";
		this.ghichu = "";
		this.khoXuatId = "";
		this.IsChuyenHangSX="";
		this.khoNhanId = "";
		this.lsxIds = "";
		this.DvkdId="";
		this.Nguoinhan="";
		this.task="";
		this.diachi="";
		this.Nguoinhanhang="";
		this.Khoxuat="";
		this.msg = "";
		this.YeucauchuyenkhoId="";
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

		this.spList = new ArrayList<IYeucau>();
		this.spChoNhapList = new ArrayList<IYeucau>();
		
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();
		
		this.ndxId = "";
		this.nccXuatId = "";
		this.nccNhanId = "";
		
		this.tongSLYC ="0.000";
		this.tongSLnhan ="0.000";
		this.noidungxuat="";
		this.tsddId = "";
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

	
	public boolean createCK() 
	{
		 Utility_Kho util_kho=new Utility_Kho();
		 
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
		String msg1=util.checkNgayHopLe(this.db, this.ngayyeucau);
		
		if(msg1.length() > 0)
		{
			this.msg =msg1;
			return false;
		}
		if( this.ndxId.equals("100006") || this.ndxId.equals("100010") || this.ndxId.equals("100011")  || this.ndxId.equals("100025"))
		{
			if(this.khoNhanId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kho nhận";
				return false;
			}
		}
		
		if( util_kho.getIsKhoGiaCong(db,this.khoXuatId))
		{
			if(this.nccXuatId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhà cung cấp chuyển";
				return false;
			}
		}
		
		if( util_kho.getIsKhoGiaCong(db,this.khoNhanId))
		{
			if(this.nccNhanId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhà cung cấp nhận";
				return false;
			}
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
	 
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String ncc_xuat_fk = this.nccXuatId.trim().length() <= 0 ? "null" : this.nccXuatId.trim();
			String ncc_nhan_fk = this.nccNhanId.trim().length() <= 0 ? "null" : this.nccNhanId.trim();
			
			String query = " insert ERP_CHUYENKHO(TSDD_FK, ISCHUYENHANGSX,NGUOINHAN,Yeucauchuyenkho_fk ,noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK ,LENHSANXUAT_FK,KYHIEU, SOCHUNGTU, LENHDIEUDONG, NGAYDIEUDONG, NGUOIDIEUDONG, VEVIEC, NGUOIVANCHUYEN, PHUONGTIEN, HOPDONG) " +
						   " values(" + (this.tsddId==null|| tsddId.length()==0? "NULL":this.tsddId ) + ", '"+this.IsChuyenHangSX+"',N'"+this.Nguoinhan+"',"+(this.YeucauchuyenkhoId.equals("")?"NULL":this.YeucauchuyenkhoId)+",'" + this.ndxId + "', '" + this.ngayyeucau + "', '" + this.ngayyeucau + "', '" + this.ngayyeucau + "', N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" + this.khoXuatId + "', " + khonhan_fk + ", '" + this.trangthaisp + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', " + ncc_xuat_fk + ", " + ncc_nhan_fk + ","+(this.lsxIds.length() >0? this.lsxIds:"NULL")+
						   ",'"+this.kyhieu+"','"+this.sochungtu+"',N'"+this.lenhdieudong+"','"+this.ngaydieudong+"',N'"+this.nguoidieudong+"',N'"+this.veviec+"',N'"+this.nguoivanchuyen+"',N'"+this.phuongtien+"',N'"+this.hopdong+"')";
 
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_CHUYENKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String ycnlCurrent = "";
			query = " select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
			
			ResultSet rsPxk = db.get(query);						
			if(rsPxk.next())
			{
				ycnlCurrent = rsPxk.getString("ckId");
				rsPxk.close();
			}
			
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					if(sp.getSoluongchuyen().trim().length() > 0)
					{
					 
							query="SELECT ISNULL(GIATON,0) AS GIATON  FROM  ERP_KHOTT_SANPHAM WHERE KHOTT_FK="+this.khoXuatId+" AND SANPHAM_FK="+ sp.getId();
							ResultSet rsgia=db.get(query);
							double dongiaton=0;
							if (rsgia != null)
							{
								if(rsgia.next()){
									dongiaton=rsgia.getDouble("GIATON");
								}
								rsgia.close();
							}
							
							query = " INSERT ERP_CHUYENKHO_SANPHAM( CHUYENKHO_FK, SANPHAM_FK ,SOLUONGYEUCAU , SOLUONGXUAT , SOLUONGNHAN,DONGIA ) " +
									" values( '" + ycnlCurrent + "', '" + sp.getId() + "',"+sp.getSoluongYC().replaceAll(",", "")+" ,  " + sp.getSoluongchuyen().replaceAll(",", "")+ " , 0,"+dongiaton+" ) ";
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới : " + query;
								db.getConnection().rollback();
								return false;
							}
 
							List<ISpDetail> spConList =sp.getSpDetailList();
							
							for(int j=0;j< spConList.size();j++){
								ISpDetail spcon=spConList.get(j);
								double soluongct=0;
								try{
								  soluongct= Double.parseDouble(spcon.getSoluong().replaceAll(",", ""));
									
								}catch(Exception err){
									
								}
								if(soluongct >0){
									
									query="SELECT ISNULL( DONGIAMUA,0) AS DONGIAMUA  FROM  ERP_KHOTT_SP_CHITIET " +
											" WHERE KHOTT_FK="+this.khoXuatId+" AND SANPHAM_FK="+ sp.getId() +" AND LTRIM(RTRIM(SOLO))='"+spcon.getSolo()+"' AND NGAYBATDAU='"+spcon.getNgaybatdau()+"' " +(spcon.getKhuId() !=null && spcon.getKhuId().length()>0? " AND KHUVUCKHO_FK="+spcon.getKhuId():"");
									  rsgia=db.get(query);
									double DONGIAMUA=0;
									if(rsgia.next()){
										DONGIAMUA=rsgia.getDouble("DONGIAMUA");
									}
									rsgia.close();
									// cập nhật kho chi tiết
									  msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay(this.db,khoXuatId,sp.getId(),0, soluongct,(-1)*soluongct,0,this.ngayyeucau);
									if(msg1.length() >0){
										this.msg = msg1;
										this.db.getConnection().rollback();
										return false;
									}
								 
									
									query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA ) " +
										  " VALUES ("+ycnlCurrent+","+sp.getId()+",'"+spcon.getSolo()+"' ,"+(spcon.getKhuId().length() >0? spcon.getKhuId():"NULL")+",'"+spcon.getNgaybatdau()+"',"+soluongct+","+DONGIAMUA+")";
									
									if(!db.update(query))
									{
										this.msg = "Không thể tạo mới : " + query;
										db.getConnection().rollback();
										return false;
									}
									
								
									msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,khoXuatId,sp.getId(),0, soluongct,(-1)*soluongct,0,spcon.getSolo(),spcon.getVitriId(),spcon.getKhuId(),spcon.getNgaybatdau());
									if(msg1.length() >0){
										this.msg = msg1;
										this.db.getConnection().rollback();
										return false;
									}
									
									if(util_kho.IsKhoQuanLyTrangThai(this.khoXuatId,this.db)){
										//neu la kho quan ly trang thai thi them kho trang thai
										msg1=util_kho.Update_Kho_Sp_Chitiet_TrangThai(this.db,khoXuatId,sp.getId(),0, soluongct,(-1)*soluongct,0,spcon.getSolo(),spcon.getVitriId(),spcon.getKhuId(),spcon.getNgaybatdau(),this.trangthaisp);
										if(msg1.length() >0){
											this.msg = msg1;
											this.db.getConnection().rollback();
											return false;
										}
										
									}
									//XET THEM TRUONG HOP NCC GIA CONG
									 
										if(this.nccXuatId!=null && this.nccXuatId.length() > 0){
											msg1=util_kho.Update_Kho_Sp_Chitiet_NCC( db,khoXuatId,sp.getId(),0, (-1)*soluongct,soluongct,0,spcon.getSolo(),spcon.getVitriId(),spcon.getKhuId(),spcon.getNgaybatdau(),this.nccXuatId);
											if(msg1.length() >0){
												this.msg = msg1;
												this.db.getConnection().rollback();
												return false;
											}
										}
			  
									
									
									
								} 
								
							}
				 
					 
					}
				}
			}
			if(this.YeucauchuyenkhoId.length() >0){
				query=" update erp_yeucauchuyenkho set trangthai=2 where pk_seq= "+this.YeucauchuyenkhoId;
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật erp_yeucauchuyenkho " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			// cập nhật giá
			query=" UPDATE YCSP "+
				  " SET YCSP.DONGIA=ISNULL((CASE WHEN SP.DVDL_FK = GIA.DVDL_FK THEN (GIA.GIAMOI)  "+
				  " ELSE GIA.GIAMOI*QC.SOLUONG2/QC.SOLUONG1  END ),0)  "+
				  " FROM	  ERP_CHUYENKHO_SANPHAM YCSP INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=YCSP.SANPHAM_FK  "+
				  " INNER JOIN  ERP_CHUYENKHO YC ON YC.PK_SEQ=YCSP.CHUYENKHO_FK  "+
				  " LEFT JOIN (   "+
				  " SELECT   SANPHAM_FK,DVDL_FK,ISNULL(GIAMOI,0) GIAMOI  "+
				  " FROM ERP_BGBAN_SANPHAM A INNER JOIN   "+
				  " (   "+
				  " SELECT top(1) * FROM ERP_BANGGIABAN B   "+
				  " WHERE B.KENH_FK = '100000' AND B.TRANGTHAI NOT IN (0)  ORDER BY B.DENNGAY DESC ) B	  "+
				  " ON A.BGBAN_FK = B.PK_SEQ   "+
				  " WHERE A.DVDL_FK IS NOT NULL  "+
				  " )GIA ON GIA.SANPHAM_FK=SP.PK_SEQ    "+
				  " LEFT JOIN QUYCACH QC ON QC.DVDL2_FK=GIA.DVDL_FK AND QC.SANPHAM_FK=GIA.SANPHAM_FK  "+
				  " LEFT JOIN DONVIDOLUONG DV ON SP.DVDL_FK = DV.PK_SEQ  "+
				  " where  YC.NOIDUNGXUAT_FK = 100025 AND YC.PK_SEQ="+ycnlCurrent;
			
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật erp_yeucauchuyenkho " + query;
				db.getConnection().rollback();
				return false;
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

	public boolean updateCK() 
	{
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
		 
	
		String msg1=util.checkNgayHopLe(this.db, this.ngayyeucau);
		
		if(msg1.length() > 0)
		{
			this.msg = msg1;
			return false;
		}
		
		
		if( this.ndxId.equals("100006")  || this.ndxId.equals("100010") || this.ndxId.equals("100011") || this.ndxId.equals("100025") )
		{
			if(this.khoNhanId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kho nhận";
				return false;
			}
		}
		
		if(this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào được chọn";
			return false;
		}
 
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String ncc_xuat_fk = this.nccXuatId.trim().length() <= 0 ? "null" : this.nccXuatId.trim();
			String ncc_nhan_fk = this.nccNhanId.trim().length() <= 0 ? "null" : this.nccNhanId.trim();

			String	query=" SELECT CK.NCC_CHUYEN_FK,CK.KHOXUAT_FK ,CHUYENKHO_FK,SANPHAM_FK,SOLO,VITRI,KHU,NGAYBATDAU,SOLUONG, ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP " +
						  " FROM ERP_CHUYENKHO CK "+ 
						  " INNER JOIN   ERP_CHUYENKHO_SP_XUATHANG  CKSP ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
						  " WHERE CK.PK_SEQ="+this.id;
				ResultSet rs=db.get(query);
				
				while(rs.next()){
					// giam booked_tang_avai
					// cập nhật kho tổng
					double soluongct=rs.getDouble("SOLUONG");
					String sanpham_fk=rs.getString("sanpham_fk");
					String solo=rs.getString("SOLO");
					String KHU=rs.getString("KHU");
					String khoxuat_fk=rs.getString("KHOXUAT_FK");
					if(KHU==null){
						KHU="";
					}
					String NGAYBATDAU=rs.getString("NGAYBATDAU");
					String VITRI=rs.getString("VITRI");
					
					  msg1= util_kho.Update_Kho_Sp (this.db,khoxuat_fk,sanpham_fk,0, (-1)*soluongct,soluongct,0);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}
					
					msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,khoxuat_fk,sanpham_fk,0, (-1)*soluongct,soluongct,0,solo,VITRI,KHU,NGAYBATDAU);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}
					
					if(util_kho.IsKhoQuanLyTrangThai(khoxuat_fk,this.db)){
						//neu la kho quan ly trang thai thi them kho trang thai
						msg1=util_kho.Update_Kho_Sp_Chitiet_TrangThai(this.db,khoxuat_fk,sanpham_fk,0, (-1)*soluongct, soluongct,0,solo,VITRI,KHU,NGAYBATDAU,this.trangthaisp);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}
						
					}
					 
					
					if(rs.getString("NCC_CHUYEN_FK")!=null){
						
						msg1=util_kho.Update_Kho_Sp_Chitiet_NCC(this.db,khoxuat_fk,sanpham_fk,0, (-1)*soluongct,soluongct,0,solo,VITRI,KHU,NGAYBATDAU,rs.getString("NCC_CHUYEN_FK"));
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}
 
					}
					
				}
				rs.close();
				
				 query =" Update ERP_CHUYENKHO set ISCHUYENHANGSX='"+this.IsChuyenHangSX+"', nguoinhan=N'"+this.Nguoinhan+"',ngaychuyen = '" + this.ngayyeucau + "', ngaynhan = '" + this.ngayyeucau + "', lydo = N'" + this.lydoyeucau + "', ghichu = N'" + this.ghichu + "', khoxuat_fk = '" + this.khoXuatId + "'," +
						" khonhan_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', trangthaisp = '" + this.trangthaisp + "', NCC_CHUYEN_FK = " + ncc_xuat_fk + ", NCC_NHAN_FK = " + ncc_nhan_fk + 
						", kyhieu='"+this.kyhieu+"', sochungtu='"+this.sochungtu+"',lenhdieudong=N'"+this.lenhdieudong+"',ngaydieudong='"+this.ngaydieudong+"',nguoidieudong=N'"+this.nguoidieudong+"',veviec=N'"+this.veviec+"',"+
						" nguoivanchuyen=N'"+this.nguoivanchuyen+"',phuongtien=N'"+this.phuongtien+"',hopdong=N'"+this.hopdong+"'"+
						" where pk_seq = '" + this.id + "' ";
			
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_CHUYENKHO " + query;
					db.getConnection().rollback();
					return false;
				}
							
				query = "delete ERP_CHUYENKHO_SANPHAM where chuyenkho_fk = '" + this.id + "'";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
					db.getConnection().rollback();
					return false;
				}
				query = "delete ERP_CHUYENKHO_SP_XUATHANG where chuyenkho_fk = '" + this.id + "'";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
					db.getConnection().rollback();
					return false;
				}
				
				if(this.spList.size() > 0)
				{
					for(int i = 0; i < this.spList.size(); i++)
					{
						IYeucau sp = this.spList.get(i);
						
						if(sp.getSoluongchuyen().trim().length() > 0)
						{
							
							query="SELECT ISNULL(GIATON,0) AS GIATON  FROM  ERP_KHOTT_SANPHAM WHERE KHOTT_FK="+this.khoXuatId+" AND SANPHAM_FK="+ sp.getId();
							ResultSet rsgia=db.get(query);
							double dongiaton=0;
							if (rsgia != null)
							{
								if(rsgia.next()){
									dongiaton=rsgia.getDouble("GIATON");
								}
								rsgia.close();
							}							
							
							query = " INSERT ERP_CHUYENKHO_SANPHAM( CHUYENKHO_FK, SANPHAM_FK ,SOLUONGYEUCAU , SOLUONGXUAT , SOLUONGNHAN,DONGIA) " +
							" values( '" + this.id + "', '" + sp.getId() + "',"+sp.getSoluongYC()+" ,  " + sp.getSoluongchuyen()+ " , 0 ,"+dongiaton+") ";
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới : " + query;
								db.getConnection().rollback();
								return false;
							}
								List<ISpDetail> spConList =sp.getSpDetailList();
								
								for(int j=0;j< spConList.size();j++){
									ISpDetail spcon=spConList.get(j);
									double soluongct=0;
									try{
									  soluongct= Double.parseDouble(spcon.getSoluong());
										
									}catch(Exception err){
										
									}
									query="SELECT ISNULL( DONGIAMUA,0) AS DONGIAMUA  FROM  ERP_KHOTT_SP_CHITIET " +
									" WHERE KHOTT_FK="+this.khoXuatId+" AND SANPHAM_FK="+ sp.getId() +" AND LTRIM(RTRIM(SOLO))='"+spcon.getSolo()+"' AND NGAYBATDAU='"+spcon.getNgaybatdau()+"' " +(spcon.getKhuId().length()>0? " AND KHUVUCKHO_FK="+spcon.getKhuId():"");
									  rsgia=db.get(query);
									double DONGIAMUA=0;
									if(rsgia.next()){
										DONGIAMUA=rsgia.getDouble("DONGIAMUA");
									}
									rsgia.close();
							
									if(soluongct >0){
									
										
										
									// cập nhật kho chi tiết
										 
										  msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay(this.db,khoXuatId,sp.getId(),0, soluongct,(-1)*soluongct,0,this.ngayyeucau);
										if(msg1.length() >0){
											this.msg = msg1;
											this.db.getConnection().rollback();
											return false;
										}
									 
										query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG ,DONGIA) " +
										  " VALUES ("+this.id+","+sp.getId()+",'"+spcon.getSolo()+"' ,"+(spcon.getKhuId().length() >0? spcon.getKhuId():"NULL")+",'"+spcon.getNgaybatdau()+"',"+soluongct+","+DONGIAMUA+")";
									
										if(!db.update(query))
										{
											this.msg = "Không thể tạo mới : " + query;
											db.getConnection().rollback();
											return false;
										}
									
										msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,khoXuatId,sp.getId(),0, soluongct,(-1)*soluongct,0,spcon.getSolo(),spcon.getVitriId(),spcon.getKhuId(),spcon.getNgaybatdau());
										if(msg1.length() >0){
											this.msg = msg1;
											this.db.getConnection().rollback();
											return false;
										}
										
										if(util_kho.IsKhoQuanLyTrangThai(this.khoXuatId,this.db)){
											//neu la kho quan ly trang thai thi them kho trang thai
											msg1=util_kho.Update_Kho_Sp_Chitiet_TrangThai(this.db,khoXuatId,sp.getId(),0, soluongct,(-1)*soluongct,0,spcon.getSolo(),spcon.getVitriId(),spcon.getKhuId(),spcon.getNgaybatdau(),this.trangthaisp);
											if(msg1.length() >0){
												this.msg = msg1;
												this.db.getConnection().rollback();
												return false;
											}
										}
										
										//XET THEM TRUONG HOP NCC GIA CONG
										 
											if(this.nccXuatId!=null && this.nccXuatId.length() > 0){
												msg1=util_kho.Update_Kho_Sp_Chitiet_NCC( db,khoXuatId,sp.getId(),0, (-1)*soluongct,soluongct,0,spcon.getSolo(),spcon.getVitriId(),spcon.getKhuId(),spcon.getNgaybatdau(),this.nccXuatId);
												if(msg1.length() >0){
													this.msg = msg1;
													this.db.getConnection().rollback();
													return false;
												}
											}
				   
									} 
									
								}
					 
								
							 	
						}
					}
				}
				
				// cập nhật giá
				query=" UPDATE YCSP "+
					  " SET YCSP.DONGIA=ISNULL((CASE WHEN SP.DVDL_FK = GIA.DVDL_FK THEN (GIA.GIAMOI)  "+
					  " ELSE GIA.GIAMOI*QC.SOLUONG2/QC.SOLUONG1  END ),0)  "+
					  " FROM	  ERP_CHUYENKHO_SANPHAM YCSP INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=YCSP.SANPHAM_FK  "+
					  " INNER JOIN  ERP_CHUYENKHO YC ON YC.PK_SEQ=YCSP.CHUYENKHO_FK  "+
					  " LEFT JOIN (   "+
					  " SELECT   SANPHAM_FK,DVDL_FK,ISNULL(GIAMOI,0) GIAMOI  "+
					  " FROM ERP_BGBAN_SANPHAM A INNER JOIN   "+
					  " (   "+
					  " SELECT top(1) * FROM ERP_BANGGIABAN B   "+
					  " WHERE B.KENH_FK = '100000' AND B.TRANGTHAI NOT IN (0)  ORDER BY B.DENNGAY DESC ) B	  "+
					  " ON A.BGBAN_FK = B.PK_SEQ   "+
					  " WHERE A.DVDL_FK IS NOT NULL  "+
					  " )GIA ON GIA.SANPHAM_FK=SP.PK_SEQ    "+
					  " LEFT JOIN QUYCACH QC ON QC.DVDL2_FK=GIA.DVDL_FK AND QC.SANPHAM_FK=GIA.SANPHAM_FK  "+
					  " LEFT JOIN DONVIDOLUONG DV ON SP.DVDL_FK = DV.PK_SEQ  "+
					  " where  YC.NOIDUNGXUAT_FK = 100025 AND YC.PK_SEQ="+this.id;
				
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật erp_yeucauchuyenkho " + query;
					db.getConnection().rollback();
					return false;
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

	public boolean nhanHang() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày nhận hàng";
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
					String soluongChuyen = yc.getSoluongchuyen().trim().length() > 0 ? yc.getSoluongchuyen().trim() : "0" ;
 
					if( Float.parseFloat(soluongNhan) >  Float.parseFloat(soluongChuyen) )
					{
						this.msg = "Số lượng nhận không được phép vượt quá số lượng chuyển.";
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
			
			
			query = " update ERP_CHUYENKHO_SANPHAM set SOLUONGNHAN = '0' where CHUYENKHO_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			query=" delete ERP_CHUYENKHO_SP_NHANHANG  where CHUYENKHO_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					//Update tong nhap
					if(sp.getSoluongNhan().trim().length() > 0)
					{
						query = " update ERP_CHUYENKHO_SANPHAM set SOLUONGNHAN = SOLUONGNHAN +  " + sp.getSoluongNhan() + " " +
								" where SANPHAM_FK =  " + sp.getId() + "  and CHUYENKHO_FK =  " + this.id + " ";
						
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_CHUYENKHO_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
					 	
					}
					List<ISpDetail> spConList =sp.getSpDetailList();
					
					for(int j=0;j< spConList.size();j++){
						ISpDetail spcon=spConList.get(j);
						double soluongct=0;
						try{
						  soluongct= Double.parseDouble(spcon.getSoluong());
							
						}catch(Exception err){
							
						}
						
						if(util_kho.getIsQuanLyKhuVuc(this.khoNhanId, db).equals("1")){
							if(spcon.getKhuId().equals("")){
								this.msg = "Vui lòng chọn khu vực lưu kho cho sản phẩm :" +sp.getTen();
								db.getConnection().rollback();
								return false;
							}
						}
						if(soluongct >0){
							
							query="SELECT ISNULL(DONGIA,0) AS DONGIA FROM ERP_CHUYENKHO_SP_XUATHANG XH WHERE XH.PK_SEQ="+spcon.getXk_Id();
							ResultSet rsgia=db.get(query);
							double dongiamua=0;
							if (rsgia != null)
							{
								if(rsgia.next()){
									dongiamua=rsgia.getDouble("DONGIA");
								}
								rsgia.close();
							}
							
							// ngày bắt đầu trong nhận hàng sẽ là ngày ghi nhận chứng từ,ngày ghi nhận chứng từ sẽ được đưa vào bảng khott_sp_chitiet (để tính chi phí lưu kho )
							query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK,CK_SP_XH_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA ) " +
							  " VALUES ("+this.id+","+spcon.getXk_Id()+","+sp.getId()+",'"+spcon.getSolo()+"' ,"+(spcon.getKhuId().length() >0? spcon.getKhuId():"NULL")+",'"+this.ngayyeucau+"',"+soluongct+","+dongiamua+")";
						
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới : " + query;
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
		 
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query =  " Update ERP_CHUYENKHO set ngaynhan = '" + this.ngayyeucau + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', trangthai = '2' " +
							" where pk_seq = '" + this.id + "' ";
							
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
 
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					IYeucau sp = this.spList.get(i);
					
					//Update tong nhap
					if(sp.getSoluongNhan().trim().length() > 0)
					{
						query = " update ERP_CHUYENKHO_SANPHAM set SOLUONGNHAN = SOLUONGNHAN + '" + sp.getSoluongNhan() + "' " +
								" where SANPHAM_FK = '" + sp.getId() + "'  and CHUYENKHO_FK = '" + this.id + "'";
						
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
				String query =  " Update ERP_YEUCAUNGUYENLIEU_SANPHAM set soluongnhan = soluongnhan + '" + sp.getSoluongNhan() + "' " +
								" where  yeucaunguyenlieu_fk = '" + this.id + "' and  SANPHAM_FK = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "') ";
				
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_YEUCAUNGUYENLIEU_SANPHAM: " + query;
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
								this.msg = "2.Không thể cập nhật ERP_YEUCAUNGUYENLIEU_SP_XUATKHO: " + query;
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
								this.msg = "Không thể cập nhật ERP_YEUCAUNGUYENLIEU_SP_CHITIET: " + query;
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
		catch (Exception e) {
			this.msg = "Lỗi :" + e.getMessage();
			db.update("rollback");
			return false;
			
		}
		
		return true;
		
	}
	
	
	
	public void createRs() 
	{
 
		String query = "SELECT PK_SEQ AS TSDDID, MA + ' - ' + DIENGIAI AS TSDD FROM ERP_TAISANCODINH WHERE PHANLOAI = 2 AND TRANGTHAI = 1 " ;
		this.tsddRs = this.db.get(query);
		
		this.ChoPhepchuyenhangdat=false;
		if(util_kho.IsKhoQuanLyTrangThai(this.khoXuatId,this.db) ){
			if(this.ndxId.equals("100015")){
				//nếu là xuất hủy 
				this.ChoPhepchuyenhangdat=true;
			}else{
				//nếu không phải xuất hủy thì kho nhận có quản lý trạng thái không, 
				//nếu có quản lý trạng thái thì mới nhận được hàng chờ xử lý ,VD: chuyển từ kho sản xuât hàng không đạt sang kho chờ xử lý
				if(util_kho.IsKhoQuanLyTrangThai(this.khoNhanId, this.db)){
					this.ChoPhepchuyenhangdat=true;
				}
			}
		}
		
		
		this.IsQuanlykhuvucKhonhap= util_kho.getIsQuanLyKhuVuc(this.khoNhanId,this.db);
		System.out.println("IsQuanlykhuvucKhonhap : "+IsQuanlykhuvucKhonhap);
		
		this.IsQuanlykhuvucKhoxuat= util_kho.getIsQuanLyKhuVuc(this.khoXuatId,this.db);
		
		query = " select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP  " +
				" where trangthai = '1' and loaixuat='1' ";
 
		this.ndxRs = db.get(query);
		Utility util=new Utility();
		
	 		if(!this.ndxId.equals("100015")) {
	 			
	 			 	query="select PK_SEQ, TEN,LOAI from ERP_KHOTT where TrangThai = '1' " ; //and pk_seq in "+util.quyen_khott(this.userId) +"   ";
				  
				     if(this.id.length() >0){
						 query=query + " union  select PK_SEQ, TEN,LOAI from ERP_KHOTT where pk_seq in (select khoxuat_fk from erp_CHUYENKHO where pk_seq="+this.id+" )" ;
					 }
					 query=query+" order by loai asc ";
					 
					this.khoXuatRs = db.get(query);
					
	 			this.khoXuatRs = db.get(query);
	 		}else{
	 			query=" select PK_SEQ, TEN,LOAI from ERP_KHOTT where TrangThai = '1'  and pk_seq in "+util.quyen_khott(this.userId) +"   ";
				  
			     if(this.id.length() >0){
					 query=query + " union  select PK_SEQ, TEN,LOAI from ERP_KHOTT where pk_seq in (select khoxuat_fk from erp_CHUYENKHO where pk_seq="+this.id+" )" ;
				 }
				 query=query+" order by loai asc ";
	 			this.khoXuatRs = db.get(query);
	 		}
		
	 		
 
	 		
	 		query = " select PK_SEQ, TEN ,LOAI from ERP_KHOTT where TrangThai = '1' ";
			if(this.khoXuatId.trim().length() > 0){
				query += " and pk_seq != '" + this.khoXuatId + "' ";
			}
			if(this.id.length() > 0){
				query=query+ " union  select PK_SEQ, TEN ,LOAI from ERP_KHOTT where pk_seq in (select khonhan_fk from erp_yeucauchuyenkho where pk_seq="+this.id+"  )";
			}
			query += " order by loai asc ";
		 
		this.khoNhanRs = db.get(query);
		
		
		if(util_kho.IsKhoNhoGiaCong(this.khoXuatId,this.db))
		{
				this.nccXuatRs = db.get("select PK_SEQ, TEN from ERP_NHACUNGCAP where KhoNL_Nho_GC = '" + this.khoXuatId + "'");
		}
		
		if(util_kho.IsKhoNhoGiaCong(this.khoNhanId,this.db))
		{
				this.nccNhanRs = db.get("select PK_SEQ, TEN from ERP_NHACUNGCAP where KHONL_NHAN_GC = '" + this.khoNhanId + "'");
		}
		
 		
		if( ( this.khoXuatId.trim().length() > 0 || this.khoNhanId.trim().length() > 0 ) && this.spList.size() <= 0 && this.id.trim().length() > 0 )
		{
			 
			this.createChuyenKho_SanPham();
		}
		query=" SELECT TEN,PK_SEQ FROM ERP_KHUVUCKHO     WHERE KHOTT_FK="+this.khoNhanId;
		this.RsKhuKhoNhan=this.db.getScrol(query);
		
	}

	private void createChuyenKho_SanPham() 
	{
		try{
		String query = "";
		
		List<IYeucau> spList = new ArrayList<IYeucau>();
		IYeucau sp = null;
		
		query = " SELECT   SP.PK_SEQ AS SPID, SP.MA AS SPMA, SP.TEN  AS SPTEN , isnull(kho.available ,0) +CKSP.SOLUONGXUAT as tonhientai  , "+
				" DVDL.DIENGIAI AS DVT,CKSP.SOLUONGYEUCAU,CKSP.SOLUONGXUAT  FROM ERP_CHUYENKHO_SANPHAM CKSP "+
				" INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
				" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=CKSP.SANPHAM_FK "+
				" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK  " +
				" left join erp_khott_sanpham kho on kho.khott_fk =ck.khoxuat_fk and kho.sanpham_fk= cksp.sanpham_fk "+
				" WHERE CK.PK_SEQ= "+this.id;
		System.out.println("LIST SP: "+query);
		ResultSet rssp=db.get(query);
		while (rssp.next()){
				
					String spId = rssp.getString("spId");
					String spMa = rssp.getString("spMa");
					String spTen = rssp.getString("spTen");
				 
					String donvi = rssp.getString("DVT");
					
					sp = new Yeucau();
					sp.setId(spId);
					sp.setMa(spMa);
					sp.setTen(spTen);
					sp.setDonViTinh(donvi);
					double tongsoluongxuat=rssp.getDouble("SOLUONGXUAT");
					sp.setSoluongYC(formatter1.format(rssp.getDouble("SOLUONGYEUCAU")));
					sp.setTonhientai(""+rssp.getDouble("tonhientai"));
					
					double total_ton=0;
					
					query=  "  SELECT SOLO,NGAYHETHAN,NGAYBATDAU,KHUVUCKHO_FK,BIN,TENKHU,isnull(SOLUONGDAXUAT,0) + SOLUONG  as soluong,isnull(SOLUONGDAXUAT,0) AS SOLUONGXUAT "+
							"  FROM ( "+
							"  SELECT AVAILABLE AS SOLUONG,SOLO,NGAYHETHAN,ISNULL(NGAYBATDAU,'') AS NGAYBATDAU , "+ 
							"  KHUVUCKHO_FK, KHO.BIN  ,KHU.TEN AS TENKHU ,  "+
							"  ISNULL((SELECT SUM(SOLUONG)   FROM ERP_CHUYENKHO_SP_XUATHANG  CKXH   "+
							"  WHERE SANPHAM_FK=KHO.SANPHAM_FK   AND CHUYENKHO_FK= "+this.id+" AND SOLO=KHO.SOLO  AND KHO.NGAYBATDAU=CKXH.NGAYBATDAU "+
								(util_kho.getIsQuanLyKhuVuc(this.khoXuatId, db).equals("1")? "  AND KHO.KHUVUCKHO_FK=CKXH.KHU ":"" )+ 
							"  ),0) AS SOLUONGDAXUAT  "+
							"  FROM ERP_KHOTT_SP_CHITIET KHO   "+
							"  LEFT JOIN ERP_KHUVUCKHO KHU ON KHU.PK_SEQ=KHO.KHUVUCKHO_FK  "+
							"  WHERE  KHO.KHOTT_FK="+this.khoXuatId+"  AND  SANPHAM_FK= "+spId+
							"  ) DATA WHERE (isnull(SOLUONGDAXUAT,0) +DATA.SOLUONG) >0 "+
							"  ORDER BY NGAYHETHAN  " ;
					
					ResultSet rsSpDetail = db.get(query);
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					 
						while(rsSpDetail.next())
						{	
							spCon = new SpDetail();
							
							double slgton = rsSpDetail.getDouble("soluong")  ;
							total_ton=total_ton+ slgton;
							String solo = rsSpDetail.getString("solo");
							spCon.setSoluong(rsSpDetail.getString("SOLUONGXUAT"));
							spCon.setSoluongton(formatter1.format(slgton));
							spCon.setSolo(solo);
							spConList.add(spCon);
							spCon.setKhu(rsSpDetail.getString("TENKHU"));
							spCon.setKhuId(rsSpDetail.getString("KHUVUCKHO_FK"));
							spCon.setNgaybatdau(rsSpDetail.getString("NGAYBATDAU"));
						}
						rsSpDetail.close();
						sp.setSoluongchuyen(formatter1.format(tongsoluongxuat));
						sp.setSpDetailList(spConList);
						spList.add(sp);
			}
		rssp.close();
		this.spList =spList;
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
	private void createChuyenKho_SanPhamPDF() 
	{
		try{
		String query = "";
		
		List<IYeucau> spList = new ArrayList<IYeucau>();
		IYeucau sp = null;
		
		query =  " SELECT   SP.PK_SEQ AS SPID, SP.MA AS SPMA, SP.TEN  AS SPTEN ,  DVDL.DIENGIAI AS DVT,CKSP.SOLUONGYEUCAU,CKSP.SOLUONGXUAT, "+
				"	ISNULL((CASE WHEN SP.DVDL_FK = 100003 THEN CKSP.SOLUONGXUAT ELSE (CKSP.SOLUONGXUAT*QC.SOLUONG2/QC.SOLUONG1)  END ),0) as KHOILUONG "+
				"	FROM ERP_CHUYENKHO_SANPHAM CKSP  "+ 
				"	INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CKSP.CHUYENKHO_FK  "+
				"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=CKSP.SANPHAM_FK  "+
				"	INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK "+
				"	LEFT JOIN QUYCACH QC ON QC.DVDL1_FK=SP.DVDL_FK AND QC.SANPHAM_FK=SP.PK_SEQ   and QC.DVDL2_FK=100003"+
				"	WHERE CK.PK_SEQ="+this.id;
		//System.out.println("LIST SP: "+query);
		ResultSet rssp=db.get(query);
		while (rssp.next()){
					String spId = rssp.getString("spId");
					String spMa = rssp.getString("spMa");
					String spTen = rssp.getString("spTen");
					String spTL = rssp.getString("KHOILUONG");
					String donvi = rssp.getString("DVT");
					
					sp = new Yeucau();
					sp.setId(spId);
					sp.setTrongLuong(spTL);
					sp.setMa(spMa);
					sp.setTen(spTen);
					sp.setDonViTinh(donvi);
					double tongsoluongxuat=rssp.getDouble("SOLUONGXUAT");
					sp.setSoluongYC(formatter1.format(rssp.getDouble("SOLUONGYEUCAU")));
					double total_ton=0;
					
					query=  "  SELECT SOLO,NGAYHETHAN,NGAYBATDAU,KHUVUCKHO_FK,BIN,TENKHU,isnull(SOLUONGDAXUAT,0) + SOLUONG  as soluong,isnull(SOLUONGDAXUAT,0) AS SOLUONGXUAT "+
							"  FROM ( "+
							"  SELECT AVAILABLE AS SOLUONG,SOLO,NGAYHETHAN,ISNULL(NGAYBATDAU,'') AS NGAYBATDAU , "+ 
							"  KHUVUCKHO_FK, KHO.BIN  ,KHU.TEN AS TENKHU ,  "+
							"  ISNULL((SELECT SUM(SOLUONG)   FROM ERP_CHUYENKHO_SP_XUATHANG  CKXH   "+
							"  WHERE SANPHAM_FK=KHO.SANPHAM_FK   AND CHUYENKHO_FK= "+this.id+" AND SOLO=KHO.SOLO  AND KHO.NGAYBATDAU=CKXH.NGAYBATDAU "+
								(util_kho.getIsQuanLyKhuVuc(this.khoXuatId, db).equals("1")? "  AND KHO.KHUVUCKHO_FK=CKXH.KHU ":"" )+ 
							"  ),0) AS SOLUONGDAXUAT  "+
							"  FROM ERP_KHOTT_SP_CHITIET KHO   "+
							"  LEFT JOIN ERP_KHUVUCKHO KHU ON KHU.PK_SEQ=KHO.KHUVUCKHO_FK  "+
							"  WHERE  KHO.KHOTT_FK="+this.khoXuatId+"  AND  SANPHAM_FK= "+spId+
							"  ) DATA WHERE (isnull(SOLUONGDAXUAT,0) +DATA.SOLUONG) >0 "+
							"  ORDER BY NGAYHETHAN  " ;
					
					ResultSet rsSpDetail = db.get(query);
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					 
						while(rsSpDetail.next())
						{	
							spCon = new SpDetail();
							
							double slgton = rsSpDetail.getDouble("soluong")  ;
							total_ton=total_ton+ slgton;
							String solo = rsSpDetail.getString("solo");
							spCon.setSoluong(rsSpDetail.getString("SOLUONGXUAT"));
							spCon.setSoluongton(formatter1.format(slgton));
							spCon.setSolo(solo);
							spConList.add(spCon);
							spCon.setKhu(rsSpDetail.getString("TENKHU"));
							spCon.setKhuId(rsSpDetail.getString("KHUVUCKHO_FK"));
							spCon.setNgaybatdau(rsSpDetail.getString("NGAYBATDAU"));
							
							
						}
						rsSpDetail.close();
						sp.setSoluongchuyen(formatter1.format(tongsoluongxuat));
						sp.setSpDetailList(spConList);
						spList.add(sp);
			
			}
		rssp.close();
		this.spList =spList;
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
	public void init() 
	{
		String query = " select  TSDD_FK AS TSDDID, isnull(ISCHUYENHANGSX,'') as ISCHUYENHANGSX, isnull(Lenhsanxuat_fk,0 ) as lsxid ,noidungxuat_fk, ngaychuyen, lydo, isnull(ghichu, '') as ghichu, khoxuat_fk, khonhan_fk, " +
				       " trangthai, isnull(trangthaisp, 0) as trangthaisp, NCC_CHUYEN_FK, NCC_NHAN_FK, ISNULL(NGUOINHAN, '') AS NGUOINHAN, " +
				       " ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, "+
				       " ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC, ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG, "+						
				       " (select SUM(SOLUONGXUAT) " +
				       "   from ERP_CHUYENKHO_SANPHAM where CHUYENKHO_FK = '" + this.id + "' group by CHUYENKHO_FK  ) as TongSLchuyen " +
					   " from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		System.out.println("INIT UPDATE: "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.tsddId = rs.getString("TSDDID");
					this.IsChuyenHangSX=rs.getString("ISCHUYENHANGSX");
					this.lsxIds=rs.getString("lsxid");
					this.ndxId = rs.getString("noidungxuat_fk");
					this.ngayyeucau = rs.getString("ngaychuyen");
					this.lydoyeucau = rs.getString("lydo");
					this.ghichu = rs.getString("ghichu");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
					this.trangthaisp = rs.getString("trangthaisp");
					this.tongSLYC = rs.getString("TongSLchuyen")==null ? "0.000": rs.getString("TongSLchuyen");
					this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
					this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
					this.kyhieu = rs.getString("KYHIEU");
					this.sochungtu = rs.getString("SOCHUNGTU");
					this.lenhdieudong = rs.getString("LENHDIEUDONG");
					this.ngaydieudong = rs.getString("NGAYDIEUDONG");
					this.nguoidieudong = rs.getString("NGUOIDIEUDONG");
					this.veviec = rs.getString("VEVIEC");
					this.nguoivanchuyen = rs.getString("NGUOIVANCHUYEN");
					this.phuongtien = rs.getString("PHUONGTIEN");
					this.hopdong = rs.getString("HOPDONG");
					this.Nguoinhan = rs.getString("NGUOINHAN");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
 
		this.createChuyenKho_SanPham();
		this.createRs();
	}	

	public void DBclose() {
		try{
			if (this.SPList != null)
				this.spList.clear(); 
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
		}catch(Exception er){
			er.printStackTrace();
		}finally{
			this.db.shutDown();
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
		
		System.out.println("1.Query khoi tao sp 1: " + query);
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
				System.out.println("1.Exception: " + e.getMessage() + "\n");
			}
			
			this.spList = spList;
		}
	}

	public void initChuyenNLPdf() 
	{
		String query =  "select a.SANPHAM_FK, b.ma AS MA, b.TEN, a.SOLUONGYEUCAU as SoLuong " +
				"from ERP_YEUCAUNGUYENLIEU_SANPHAM a " +
				"inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ where yeucaunguyenlieu_fk = '" + this.id + "'";
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
		
			query = " select b.ma AS MA, b.TEN, c.MA AS VITRI, d.DIENGIAI AS KHUTEN, e.DONVI as DONVIDOLUONG, nhapkho.solo, nhapkho.tongNhap, f.ma as khoTen  " +
				"from " +
				"( " +
					"select sanpham_fk, khott_fk, solo, bean, SUM(soluong) as tongNhap  " +
					"from ERP_YEUCAUNGUYENLIEU_SP_XUATKHO   " +
					"where yeucaunguyenlieu_fk = '" + this.id + "' and sanpham_fk = '" + spId + "'  " +
					"group by sanpham_fk, khott_fk, solo, bean  " +
				")  " +
				" nhapkho inner Join ERP_SanPham b on nhapkho.sanpham_fk = b.PK_SEQ " +
				" INNER JOIN ERP_VITRIKHO c ON nhapkho.BEAN = c.PK_SEQ  " +
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
		String query =  " select TSDD_FK AS TSDDID, isnull(ghichu,'') ghichu, isnull(nguoinhan,'') as nguoinhan, isnull(IsChuyenHangSX,'') as IsChuyenHangSX  ,noidungxuat_fk, ngaychuyen, lydo, khoxuat_fk, khonhan_fk, trangthai, isnull(trangthaisp, 0) as trangthaisp, NCC_CHUYEN_FK, NCC_NHAN_FK," +
						" ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, "+
						" ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC, ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG, "+						
						" (select SUM(SOLUONGXUAT) from ERP_CHUYENKHO_SANPHAM where CHUYENKHO_FK ='" + this.id + "' group by CHUYENKHO_FK )as TongYC, " +	
						" (select SUM(SOLUONGNHAN) from ERP_CHUYENKHO_SANPHAM where CHUYENKHO_FK ='" + this.id + "' group by CHUYENKHO_FK )as Tongnhan " +
						" from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		
		System.out.println(query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.tsddId = rs.getString("TSDDID");
					this.ndxId = rs.getString("noidungxuat_fk");
					this.ngayyeucau = rs.getString("ngaychuyen");
					this.lydoyeucau = rs.getString("lydo");
					this.Nguoinhan=rs.getString("nguoinhan");
					this.IsChuyenHangSX=rs.getString("IsChuyenHangSX");
					
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk") == null ? "" : rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
					this.trangthaisp = rs.getString("trangthaisp");
					
					this.tongSLYC = rs.getString("TongYC")== null ? "0.000": rs.getString("TongYC");
					this.tongSLnhan = rs.getString("Tongnhan")== null ? "0.000": rs.getString("Tongnhan");
					
					this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
					this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
					this.kyhieu = rs.getString("KYHIEU");
					this.sochungtu = rs.getString("SOCHUNGTU");
					this.lenhdieudong = rs.getString("LENHDIEUDONG");
					this.ngaydieudong = rs.getString("NGAYDIEUDONG");
					this.nguoidieudong = rs.getString("NGUOIDIEUDONG");
					this.veviec = rs.getString("VEVIEC");
					this.nguoivanchuyen = rs.getString("NGUOIVANCHUYEN");
					this.phuongtien = rs.getString("PHUONGTIEN");
					this.hopdong = rs.getString("HOPDONG");
					this.ghichu = rs.getString("ghichu");
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
		this.createRs();
	}
	
	public void initXuathang() 
	{
		String query =  " select isnull(nguoinhan,'') as nguoinhan, isnull(IsChuyenHangSX,'') as IsChuyenHangSX , " +
						" ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, "+
						" ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC, ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG, "+						
						" noidungxuat_fk, ngaychuyen, lydo, khoxuat_fk, khonhan_fk, trangthai, isnull(trangthaisp, 0) as trangthaisp, NCC_CHUYEN_FK, NCC_NHAN_FK " +
						" from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
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
					this.Nguoinhan=rs.getString("nguoinhan");
					this.IsChuyenHangSX=rs.getString("IsChuyenHangSX");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk") == null ? "" : rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
					this.trangthaisp = rs.getString("trangthaisp");
					
					this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
					this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
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
		}
		
		 
		
		if( this.spList.size() <= 0 )
		{
			this.createChuyenKho_SanPham_XuatHang();
		}
		this.createRs();
	}

	private void createChuyenKho_SanPham_NhanHang() 
	{
		 try{
				String query = "";
				List<IYeucau> spList = new ArrayList<IYeucau>();
				IYeucau sp = null;
				
				query = " SELECT   SP.PK_SEQ AS SPID, SP.MA AS SPMA, SP.TEN  AS SPTEN , "+
						" DVDL.DIENGIAI AS DVT,CKSP.SOLUONGYEUCAU,CKSP.SOLUONGXUAT  FROM ERP_CHUYENKHO_SANPHAM CKSP "+
						" INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=CKSP.SANPHAM_FK "+
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK "+
						" WHERE CK.PK_SEQ= "+this.id;
		 
				ResultSet rssp=db.get(query);
				while (rssp.next()){
						
							String spId = rssp.getString("spId");
							String spMa = rssp.getString("spMa");
							String spTen = rssp.getString("spTen");
							String donvi = rssp.getString("DVT");
							sp = new Yeucau();
							sp.setId(spId); 
							sp.setMa(spMa); 
							sp.setTen(spTen);
							sp.setDonViTinh(donvi);
							double tongsoluongxuat=rssp.getDouble("SOLUONGXUAT");
							//nhận bằng với  xuất
							sp.setSoluongNhan(formatter1.format(tongsoluongxuat));
							sp.setSoluongYC(formatter1.format(rssp.getDouble("SOLUONGYEUCAU")));
							query=  "SELECT * FROM ERP_CHUYENKHO_SP_NHANHANG WHERE SANPHAM_FK="+spId+" AND  CHUYENKHO_FK="+this.id;
							ResultSet rscheck=db.get(query);
							if(rscheck.next()){
									query=  " SELECT   CK_SP_XH_FK  ,ISNULL(CK_XH.KHU ,0) AS KHU,CK_XH.CHUYENKHO_FK,CK_XH.SOLO,CK_XH.SOLUONG ,CK_XH.NGAYBATDAU "+ 
									" FROM ERP_CHUYENKHO_SP_NHANHANG CK_XH  "+
								    " INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CK_XH.CHUYENKHO_FK "+
									" WHERE CK_XH.SANPHAM_FK="+spId+" AND CK.PK_SEQ="+this.id ;
							}else{
							
							query=  " SELECT CK_XH.PK_SEQ AS CK_SP_XH_FK,0 AS KHU, CK_XH.CHUYENKHO_FK,CK_XH.SOLO,CK_XH.SOLUONG ,CK_XH.NGAYBATDAU "+ 
									" FROM ERP_CHUYENKHO_SP_XUATHANG CK_XH  "+
								    " INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CK_XH.CHUYENKHO_FK "+
									" WHERE CK_XH.SANPHAM_FK="+spId+" AND CK.PK_SEQ="+this.id ;
							}
							
							ResultSet rsSpDetail = db.get(query);
							List<ISpDetail> spConList = new ArrayList<ISpDetail>();
							ISpDetail spCon = null;
							 
								while(rsSpDetail.next())
								{	
									spCon = new SpDetail();
									String solo = rsSpDetail.getString("solo");
									spCon.setSoluong(rsSpDetail.getString("SOLUONG"));
									spCon.setSolo(solo);
									spConList.add(spCon);
									spCon.setKhuId(rsSpDetail.getString("KHU"));
									spCon.setNgaybatdau(rsSpDetail.getString("NGAYBATDAU"));
									spCon.setXk_Id(rsSpDetail.getString("CK_SP_XH_FK"));
									
								}
								rsSpDetail.close();
								sp.setSoluongchuyen(formatter1.format(tongsoluongxuat));
								sp.setSpDetailList(spConList);
								spList.add(sp);
								rscheck.close();
					}
				rssp.close();
				this.spList =spList;
		 }catch(Exception er){
			 er.printStackTrace();
		 }
	}

	private void createChuyenKho_SanPham_XuatHang() 
	{
		 try{
				String query = "";
				
				List<IYeucau> spList = new ArrayList<IYeucau>();
				IYeucau sp = null;
				
				query = " SELECT   SP.PK_SEQ AS SPID, SP.MA AS SPMA, SP.TEN  AS SPTEN , "+
						" DVDL.DIENGIAI AS DVT,CKSP.SOLUONGYEUCAU,CKSP.SOLUONGXUAT  FROM ERP_CHUYENKHO_SANPHAM CKSP "+
						" INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=CKSP.SANPHAM_FK "+
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK "+
						" WHERE CK.PK_SEQ= "+this.id;
		 
				ResultSet rssp=db.get(query);
				while (rssp.next()){
						
							String spId = rssp.getString("spId");
							String spMa = rssp.getString("spMa");
							String spTen = rssp.getString("spTen");
						 
							String donvi = rssp.getString("DVT");
							
							sp = new Yeucau();
							sp.setId(spId);
							sp.setMa(spMa);
							sp.setTen(spTen);
							sp.setDonViTinh(donvi);
							double tongsoluongxuat=rssp.getDouble("SOLUONGXUAT");
							sp.setSoluongYC(formatter1.format(rssp.getDouble("SOLUONGYEUCAU")));
							
							query=  " SELECT ISNULL(KV.PK_SEQ,0)  AS KHUID,KV.TEN AS TENKHU ,CK_XH.CHUYENKHO_FK,CK_XH.SOLO,CK_XH.SOLUONG ,CK_XH.NGAYBATDAU "+ 
									" FROM ERP_CHUYENKHO_SP_XUATHANG CK_XH  "+
								    " INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CK_XH.CHUYENKHO_FK " +
								    " LEFT JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ= CK_XH.KHU "+
									" WHERE CKXH.SANPHAM_FK="+spId+" and  CK.PK_SEQ="+this.id ;
							
							ResultSet rsSpDetail = db.get(query);
							List<ISpDetail> spConList = new ArrayList<ISpDetail>();
							ISpDetail spCon = null;
							 
								while(rsSpDetail.next())
								{	
									spCon = new SpDetail();
									
								 
									String solo = rsSpDetail.getString("solo");
									spCon.setSoluong(rsSpDetail.getString("SOLUONG"));
									spCon.setSolo(solo);
									spConList.add(spCon);
									spCon.setKhuId(rsSpDetail.getString("KHUID"));
									spCon.setKhu(rsSpDetail.getString("TENKHU"));
									spCon.setNgaybatdau(rsSpDetail.getString("NGAYBATDAU"));
									
								}
								rsSpDetail.close();
								sp.setSoluongchuyen(formatter1.format(tongsoluongxuat));
								sp.setSpDetailList(spConList);
								spList.add(sp);
					
					}
				rssp.close();
				this.spList =spList;
				
		 }catch(Exception er){
			 er.printStackTrace();
		 }
	}

	public void initView() 
	{
		String query = "select ISNULL(NGUOINHAN,'') AS  NGUOINHAN , isnull(IsChuyenHangSX,'') as IsChuyenHangSX, isnull(lenhsanxuat_fk ,0) as lsxid , noidungxuat_fk, ngaychuyen, lydo, isnull(ghichu, '') as ghichu, khoxuat_fk, khonhan_fk," +
				       " trangthai, isnull(trangthaisp, 0) as trangthaisp, NCC_CHUYEN_FK, NCC_NHAN_FK ," +
				       " ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, "+
				       " ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC, ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG, "+						
				       " (select SUM(SOLUONGXUAT) from ERP_CHUYENKHO_SANPHAM where CHUYENKHO_FK = '" + this.id + "' group by CHUYENKHO_FK  ) as TongSLchuyen, " +
				       " (select SUM(SOLUONGNHAN) from ERP_CHUYENKHO_SANPHAM where CHUYENKHO_FK = '" + this.id + "' group by CHUYENKHO_FK  ) as TongSLnhan " +
						"from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		
		System.out.println("Query: "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.lsxIds=rs.getString("lsxid");
					this.ndxId = rs.getString("noidungxuat_fk");
					this.ngayyeucau = rs.getString("ngaychuyen");
					this.Nguoinhan=rs.getString("NGUOINHAN");
					this.lydoyeucau = rs.getString("lydo");
					this.ghichu = rs.getString("ghichu");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoNhanId = rs.getString("khonhan_fk") == null ? "" : rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
					this.trangthaisp = rs.getString("trangthaisp");
					this.IsChuyenHangSX=rs.getString("IsChuyenHangSX");
					
					this.tongSLYC = rs.getString("TongSLchuyen")==null ? "0.000": rs.getString("TongSLchuyen");
					this.tongSLnhan = rs.getString("TongSLnhan")==null ? "0.000": rs.getString("TongSLnhan");
 
					this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
					this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
					this.kyhieu = rs.getString("KYHIEU");
					this.sochungtu = rs.getString("SOCHUNGTU");
					this.lenhdieudong = rs.getString("LENHDIEUDONG");
					this.ngaydieudong = rs.getString("NGAYDIEUDONG");
					this.nguoidieudong = rs.getString("NGUOIDIEUDONG");
					this.veviec = rs.getString("VEVIEC");
					this.nguoivanchuyen = rs.getString("NGUOIVANCHUYEN");
					this.phuongtien = rs.getString("PHUONGTIEN");
					this.hopdong = rs.getString("HOPDONG");
					this.noidungxuat = rs.getString("noidungxuat_fk");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	 
		if( this.spList.size() <= 0 )
		{
			this.createChuyenKho_SanPham_View();
		}
		this.createRs();
	}

//-----------------------------------------PDF-----------------------------------------------//
	
	
	
	public void initPdf() 
	{
		String query = 
			" select ndn.PK_SEQ as ndx,  ndn.TEN as noidungxuat, isnull(ck.ngaychuyen, '') as ngaychuyen, isnull(ck.lydo, '') as lydo, " +
			"     ck.khoxuat_fk as khoxuat_fk, isnull(kx.ten, '') as khoxuat, ck.khonhan_fk as khonhan_fk, isnull(kn.ten, '') as khonhan, " +
			"     isnull(nccnhan.ten, '') as nccnhan, isnull(nccchuyen.ten, '') as nccchuyen" +
			" from ERP_CHUYENKHO ck " +
			" left join ERP_NOIDUNGNHAP ndn on ndn.pk_seq = ck.noidungxuat_fk " +
			" left join erp_khott kx on kx.pk_seq = ck.khoxuat_fk " +
			" left join erp_khott kn on kn.pk_seq = ck.khonhan_fk " +
			" left join erp_nhacungcap nccnhan on nccnhan.pk_seq = ck.ncc_nhan_fk " +
			" left join erp_nhacungcap nccchuyen on nccchuyen.pk_seq = ck.ncc_chuyen_fk " +
			" where ck.pk_seq = '" + this.id + "'";
		System.out.println("[ErpChuyenkhoSX] query = " + query);
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
					this.noidungxuat = rs.getString("noidungxuat");
					khoxuat_fk = rs.getString("khoxuat_fk");
					
					//Ngày yêu cầu
					try {
						String[] nyc = rs.getString("ngaychuyen").split("-");
						this.ngayyeucau = nyc[2]+"/"+nyc[1]+"/"+nyc[0];
					} catch(Exception e) { 
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
		String query = " SELECT B.PK_SEQ as SPID, B.MA  , B.TEN as SPTEN, ISNULL(C.DONVI, '') AS DVDL, B.DAI, B.DVDL_DAI, B.RONG, B.DVDL_RONG, B.DINHLUONG, B.DVDL_DINHLUONG, A.SOLUONGXUAT ," +
				" isnull( A.SOLUONGXUAT *  CASE WHEN b.DVDL_FK=100003  THEN 1 ELSE CAST( QC.SOLUONG2 AS FLOAT)  / CAST( QC.SOLUONG1 AS FLOAT)  END ,0) as TRONGLUONG ," +
				" B.TEN + ISNULL(B.QUYCACH,'') AS DIENGIAI " + 
				" FROM ERP_CHUYENKHO_SANPHAM A " + 
				" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ " +
				" LEFT JOIN DONVIDOLUONG C ON B.DVDL_FK = C.PK_SEQ  " +
				" LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=B.PK_SEQ   AND QC.DVDL1_FK=b.DVDL_FK AND QC.DVDL2_FK=100003  " +
				" WHERE A.CHUYENKHO_FK = " + this.id + " AND A.SOLUONGXUAT > 0 ";
		//System.out.println("[ErpChuyenkhoSX] layDanhSachSanPhamVatTuGiaCong = " + query);
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
					sp.setMavattu(spRs.getString("MA"));
					sp.setId(spRs.getString("SPID"));
					sp.setMa(spRs.getString("ma"));
					sp.setTen(spRs.getString("SPTEN"));
					sp.setDonViTinh(spRs.getString("DVDL"));
					sp.setTrongLuong(spRs.getString("trongluong"));
					sp.setDiengiai(spRs.getString("DIENGIAI"));
					if(spRs.getString("SOLUONGXUAT").trim().length() > 0) {
						sp.setSoluongYC(spRs.getString("SOLUONGXUAT"));
						 
					}
					
					
					double dai = 0, rong = 0, dinhluong = 0;
					try { dai = Double.parseDouble(spRs.getString("DAI")); } catch(Exception e) { }
					try { rong = Double.parseDouble(spRs.getString("RONG")); } catch(Exception e) { }
					try { dinhluong = Double.parseDouble(spRs.getString("DINHLUONG")); } catch(Exception e) { }
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
				System.out.println("[layDanhSachSanPhamVatTuGiaCong] Exception: " + e.getMessage());
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
		query = " SELECT B.PK_SEQ as SPID, B.MA as SPMA , B.TEN as SPTEN, ISNULL(C.DONVI, '') AS DVDL, B.DAI, B.DVDL_DAI, B.RONG, B.DVDL_RONG, B.DINHLUONG, B.DVDL_DINHLUONG, A.SOLUONGXUAT " + 
				" FROM ERP_CHUYENKHO_SANPHAM A " + 
				" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ " +
				" left join ERP_LOAISANPHAM lsp on b.LOAISANPHAM_FK = lsp.PK_SEQ " +
				" LEFT JOIN DONVIDOLUONG C ON B.DVDL_FK = C.PK_SEQ " +
				" WHERE A.CHUYENKHO_FK = " + this.id + " AND A.SOLUONGXUAT > 0 and B.LOAISANPHAM_FK " + not + " in ( " + lsp + " ) ";
		System.out.println("[layDanhSachSanPhamPhuLieu] query = " + query);
		spRs = db.get(query);
		List<IYeucau> spList = new ArrayList<IYeucau>();
		try 
		{
			IYeucau sp = null;
			while(spRs.next())
			{
				sp = new Yeucau();
				//sp.setId(spRs.getString("spId"));
				sp.setMa(spRs.getString("SPMA"));
				sp.setTen(spRs.getString("SPTEN"));
				sp.setDonViTinh(spRs.getString("DVDL"));
				
				double dai = 0, rong = 0, dinhluong = 0;
				try { dai = Double.parseDouble(spRs.getString("DAI")); } catch(Exception e) { }
				try { rong = Double.parseDouble(spRs.getString("RONG")); } catch(Exception e) { }
				try { dinhluong = Double.parseDouble(spRs.getString("DINHLUONG")); } catch(Exception e) { }
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
		query = " select dvdl.donvi, b.ten +isnull(b.quycach,'') as diengiai, b.MA as spMa, b.Ten as spTen,  SUM(a.SOLUONGXUAT) as tongXuat, isnull(Sum(a.SOLUONGNHAN), 0) as tongNhan, c.ten as lspTen ," +
				" sum( a.SOLUONGXUAT *  CASE WHEN b.DVDL_FK=100003 THEN 1 ELSE CAST( QC.SOLUONG2 AS FLOAT)  / CAST( QC.SOLUONG1 AS FLOAT)  END )  as TRONGLUONG, " + 
				" ISNULL(b.DAI, 0) AS DAI, ISNULL(b.RONG, 0) AS RONG, ISNULL(b.DINHLUONG, 0) AS DINHLUONG, "+
				" ISNULL(b.TRONGLUONG, 0) AS TRONGLUONG2, ISNULL(b.DUONGKINHTRONG, 0) AS DUONGKINHTRONG, "+
				" ISNULL(b.DODAY, 0) AS DODAY, ISNULL(b.DAULON, 0) AS DAULON, ISNULL(b.DAUNHO, 0) AS DAUNHO, "+
				" ISNULL(b.DAIDAY, 0) AS DAIDAY,      ISNULL(b.DVDL_DAI, '') AS DVDL_DAI, ISNULL(b.DVDL_RONG, '') AS DVDL_RONG, "+
				" ISNULL(b.DVDL_DINHLUONG, '') AS DVDL_DINHLUONG, ISNULL(b.DVDL_TRONGLUONG, '') AS DVDL_TRONGLUONG, "+
				" ISNULL(b.DVDL_DKTRONG, '') AS DVDL_DKTRONG, ISNULL(b.DVDL_DODAY, '') AS DVDL_DODAY, "+
				" ISNULL(b.DVDL_DAULON, '') AS DVDL_DAULON, ISNULL(b.DVDL_DAUNHO, '') AS DVDL_DAUNHO, "+
				" ISNULL(b.DVDL_DAIDAY, '') AS DVDL_DAIDAY,  ISNULL(b.MAUIN, '') AS MAUIN, ISNULL(b.MAU, '') AS MAU,  ISNULL(b.CHUANNEN,'') AS CHUANNEN" +
				" from ERP_CHUYENKHO_SANPHAM a " +
				" inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ  " +
				" LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=B.PK_SEQ   AND QC.DVDL1_FK=b.DVDL_FK AND QC.DVDL2_FK=100003 " + 
				" left join ERP_LOAISANPHAM c on b.LOAISANPHAM_FK = C.PK_SEQ " +
				"left join donvidoluong dvdl on dvdl.pk_seq=b.dvdl_fk  " +
				" where a.CHUYENKHO_FK = '" + this.id + "' and B.LOAISANPHAM_FK " + not + " in ( " + lsp + " ) " +
				" group by b.MA, b.TEN, c.ten ,b.quycach,dvdl.donvi, " +
				" ISNULL(b.DAI, 0) , ISNULL(b.RONG, 0) , ISNULL(b.DINHLUONG, 0), "+
				" ISNULL(b.TRONGLUONG, 0) , ISNULL(b.DUONGKINHTRONG, 0), "+
				" ISNULL(b.DODAY, 0) , ISNULL(b.DAULON, 0) , ISNULL(b.DAUNHO, 0), "+
				" ISNULL(b.DAIDAY, 0) , ISNULL(b.DVDL_DAI, ''), ISNULL(b.DVDL_RONG, ''), "+
				" ISNULL(b.DVDL_DINHLUONG, '') , ISNULL(b.DVDL_TRONGLUONG, ''), "+
				" ISNULL(b.DVDL_DKTRONG, '') , ISNULL(b.DVDL_DODAY, ''), "+
				" ISNULL(b.DVDL_DAULON, '') , ISNULL(b.DVDL_DAUNHO, ''), "+
				" ISNULL(b.DVDL_DAIDAY, ''),  ISNULL(b.MAUIN, ''), ISNULL(b.MAU, '') ,  ISNULL(b.CHUANNEN,'') " +
				" having SUM(a.SOLUONGXUAT) > 0 ";
		System.out.println("1. [ErpChuyenkhoSX] Sp layDanhSachSanPhamKhac  = " + query);
		spRs = db.get(query);
		List<IYeucau> spList = new ArrayList<IYeucau>();
		try 
		{
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			IYeucau sp = null;
			ISpDetail spct = null;
			String quycach_sp ="";
			while(spRs.next())
			{
				sp = new Yeucau();
				//sp.setId(spRs.getString("spId"));
				sp.setMa(spRs.getString("spMa"));
				sp.setTen(spRs.getString("spTen"));
				sp.setSolo(spRs.getString("lspTen"));
				sp.setDonViTinh(spRs.getString("donvi"));
				sp.setTrongLuong(spRs.getString("trongluong"));
				
				if(spRs.getString("tongXuat").trim().length() > 0)
					sp.setSoluongYC(spRs.getString("tongXuat"));
				
				if(spRs.getString("tongNhan").trim().length() > 0)
					sp.setSoluongNhan(spRs.getString("tongNhan"));
				
				quycach_sp = spRs.getString("spTen");
			
				//***** LẤY NHỮNG QUY CÁCH  > 0 *******//
				
				
				if(spRs.getDouble("RONG") >0){
					quycach_sp = quycach_sp+" x "+formatter.format(spRs.getDouble("RONG"))+spRs.getString("DVDL_RONG")   ;
		
				}
				if(spRs.getDouble("DAI") >0){
					quycach_sp = quycach_sp+" x "+formatter.format(spRs.getDouble("DAI"))+spRs.getString("DVDL_DAI")   ;
				}
				
				if(spRs.getDouble("DINHLUONG") >0){
					quycach_sp = quycach_sp+" x "+formatter.format(spRs.getDouble("DINHLUONG"))+spRs.getString("DVDL_DINHLUONG")   ;
				}
				if(spRs.getDouble("TRONGLUONG2") >0){
					quycach_sp = quycach_sp+" x "+formatter.format(spRs.getDouble("TRONGLUONG2"))+spRs.getString("DVDL_TRONGLUONG")   ;
				}
				
				if(spRs.getDouble("DUONGKINHTRONG") >0){
				
					quycach_sp = quycach_sp+" x "+formatter.format(spRs.getDouble("DUONGKINHTRONG"))+spRs.getString("DVDL_DKTRONG")   ;
				}
				if(spRs.getDouble("DODAY") >0){
					quycach_sp = quycach_sp+" x "+formatter.format(spRs.getDouble("DODAY"))+spRs.getString("DVDL_DODAY")   ;
				}
				if(spRs.getDouble("DAULON") >0){
					quycach_sp = quycach_sp+" x "+formatter.format(spRs.getDouble("DAULON"))+spRs.getString("DVDL_DAULON")   ;
				}
				if(spRs.getDouble("DAUNHO") >0){
					quycach_sp = quycach_sp+" x "+formatter.format(spRs.getDouble("DAUNHO"))+spRs.getString("DVDL_DAUNHO")   ;
				}
				
				if(spRs.getDouble("DAIDAY") >0){
					quycach_sp = quycach_sp+" x "+formatter.format(spRs.getDouble("DAIDAY"))+spRs.getString("DVDL_DAIDAY")   ;
				}
				if(!(spRs.getString("MAUIN").trim().equals("Không") || spRs.getString("MAUIN").trim().equals(""))){
					quycach_sp = quycach_sp+" x "+spRs.getString("MAUIN")   ;
				}
				if(!(spRs.getString("MAU").trim().equals("Không màu") || spRs.getString("MAU").trim().equals(""))){
					quycach_sp = quycach_sp+" x "+spRs.getString("MAU")   ;
				}
				if(!(spRs.getString("CHUANNEN").equals("") )){
					System.out.println(" chuan nen :" + spRs.getString("CHUANNEN"));
					quycach_sp = quycach_sp+" x "+spRs.getString("CHUANNEN")   ;
				}
				System.out.println(" quycach_sp : " + quycach_sp);
				
				sp.setDiengiai(quycach_sp);

				spList.add(sp);
				quycach_sp= "";
				
				//Lấy chi tiết
				query = " SELECT B.MA, B.TEN, B.DINHLUONG, B.DVDL_DINHLUONG, SUM(A.SOLUONGXUAT) AS SOLUONGXUAT  " +
						
						" FROM ERP_CHUYENKHO_SANPHAM A " + 
						" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ    " +
						
						" WHERE B.MA = N'" + sp.getMa() + "' AND A.SOLUONGXUAT > 0 AND A.CHUYENKHO_FK = " + this.id + " " + 
						" GROUP BY B.MA , B.TEN, B.DINHLUONG, B.DVDL_DINHLUONG " + 
						" HAVING SUM(A.SOLUONGXUAT) > 0 ";
				//System.out.println("[ErpChuyenkhoSX] query = " + query);
				spCtRs = db.get(query);
				while(spCtRs.next())
				{
					spct = new SpDetail();
					spct.setMa(spCtRs.getString("ma"));
					spct.setTen(spCtRs.getString("TEN"));
					spct.setSoluong(spCtRs.getString("SOLUONGXUAT"));
					
					double dinhluong = 0;
					try { dinhluong = Double.parseDouble(spCtRs.getString("DINHLUONG")); } catch(Exception e) { }
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
		}

		return spList;
	}
//-----------------------------------------END PDF-----------------------------------------------//
	
	
	
	private void createChuyenKho_SanPham_View() 
	{
		 try{
				String query = "";
				List<IYeucau> spList = new ArrayList<IYeucau>();
				IYeucau sp = null;
				
				query = " SELECT   SP.PK_SEQ AS SPID, SP.MA AS SPMA, SP.TEN  AS SPTEN , "+
						" DVDL.DIENGIAI AS DVT,CKSP.SOLUONGYEUCAU,CKSP.SOLUONGXUAT,CKSP.SOLUONGNHAN    FROM ERP_CHUYENKHO_SANPHAM CKSP "+
						" INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=CKSP.SANPHAM_FK "+
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK "+
						" WHERE CK.PK_SEQ= "+this.id;
				
				System.out.println(" san pham view :" + query);
				ResultSet rssp=db.get(query);
				while (rssp.next()){
						
							String spId = rssp.getString("spId");
							String spMa = rssp.getString("spMa");
							String spTen = rssp.getString("spTen");
							String donvi = rssp.getString("DVT");
							sp = new Yeucau();
							sp.setId(spId);
							sp.setMa(spMa);
							sp.setTen(spTen);
							sp.setDonViTinh(donvi);
							double tongsoluongxuat=rssp.getDouble("SOLUONGXUAT");
							//nhận bằng với  xuất
							sp.setSoluongNhan(tongsoluongxuat+"");
							sp.setSoluongYC(formatter1.format(rssp.getDouble("SOLUONGYEUCAU")));
							 
								
							//list xuất hàng 
							query=  " SELECT CK_XH.PK_SEQ AS CK_SP_XH_FK, CK_XH.KHU AS KHU,KV.TEN AS TENKHU, CK_XH.CHUYENKHO_FK,CK_XH.SOLO,CK_XH.SOLUONG ,CK_XH.NGAYBATDAU "+ 
									" FROM ERP_CHUYENKHO_SP_XUATHANG CK_XH  "+
								    " INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CK_XH.CHUYENKHO_FK  " +
								    " LEFT JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ= CK_XH.KHU "+
									" WHERE CK_XH.SANPHAM_FK="+spId+" AND  CK.PK_SEQ="+this.id ;
							 
							
							ResultSet rsSpDetail = db.get(query);
							List<ISpDetail> spConList = new ArrayList<ISpDetail>();
							ISpDetail spCon = null;
							 
								while(rsSpDetail.next())
								{	
									spCon = new SpDetail();
									String solo = rsSpDetail.getString("solo");
									spCon.setSoluong(rsSpDetail.getString("SOLUONG"));
									spCon.setKhu(rsSpDetail.getString("TENKHU"));
									spCon.setSolo(solo);
									spConList.add(spCon);
									spCon.setKhuId(rsSpDetail.getString("KHU"));
									spCon.setNgaybatdau(rsSpDetail.getString("NGAYBATDAU"));
									spCon.setXk_Id(rsSpDetail.getString("CK_SP_XH_FK"));
									
								}
								rsSpDetail.close();
								sp.setSoluongchuyen(formatter1.format(tongsoluongxuat));
								sp.setSpDetailList(spConList);
								
								query=  " SELECT   CK_SP_XH_FK  ,ISNULL(CK_XH.KHU ,0) AS KHU ,KV.TEN AS TENKHU, CK_XH.CHUYENKHO_FK,CK_XH.SOLO,CK_XH.SOLUONG ,CK_XH.NGAYBATDAU "+ 
								" FROM ERP_CHUYENKHO_SP_NHANHANG CK_XH  "+
							    " INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CK_XH.CHUYENKHO_FK "+
							    " LEFT JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ= CK_XH.KHU "+
								" WHERE CK_XH.SANPHAM_FK="+spId+" AND CK.PK_SEQ="+this.id ;
							 
								rsSpDetail = db.get(query);
								List<ISpDetail> spConList1 = new ArrayList<ISpDetail>();
								  spCon = null;
								  	
									while(rsSpDetail.next())
									{	
										spCon = new SpDetail();
										String solo = rsSpDetail.getString("solo");
										spCon.setSoluong(rsSpDetail.getString("SOLUONG"));
										spCon.setSolo(solo);
										spCon.setKhu(rsSpDetail.getString("TENKHU"));
										spCon.setKhuId(rsSpDetail.getString("KHU"));
										spCon.setNgaybatdau(rsSpDetail.getString("NGAYBATDAU"));
										spCon.setXk_Id(rsSpDetail.getString("CK_SP_XH_FK"));
										spConList1.add(spCon);
										
									}
									rsSpDetail.close();
									sp.setSoluongNhan(rssp.getString("SOLUONGNHAN"));
									sp.setSpDetail2List(spConList1);
								
								
								spList.add(sp);
					
					}
				rssp.close();
				this.spList =spList;
				
		 }catch(Exception er){
			 er.printStackTrace();
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

	
	public boolean createChuyenKho_LSX() {
		
		try{
			this.db.getConnection().setAutoCommit(false);
			
			String query=" select khoxuat_fk, khonhan_fk from erp_phieuyeucau a  " +
					" inner join ERP_PHIEUYEUCAU_LSX  b on a.pk_seq=b.phieuyeucau_fk " +
					" where  b.lenhsanxuat_fk= "+this.lsxIds;
			
			ResultSet rs=db.get(query);
			
			while (rs.next()){
				    query = " insert ERP_CHUYENKHO(LENHSANXUAT_FK,noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, ghichu, trangthai, khoxuat_fk, khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua) " +
				            " values("+this.lsxIds+",'" + this.ndxId + "', '" + this.ngayyeucau + "', '" + this.ngayyeucau + "', '" + this.ngayyeucau + "', N'" + this.lydoyeucau + "', N'" + this.ghichu + "', '0', '" +rs.getString("khoxuat_fk") + "', " + rs.getString("khonhan_fk") + ", '" + this.trangthaisp + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_CHUYENKHO " + query;
						db.getConnection().rollback();
						return false;
					}
					query = "select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
					ResultSet rsPxk = db.get(query);	
					if (rsPxk != null)
					{
						if (rsPxk.next())
							this.id = rsPxk.getString("ckId");
						rsPxk.close();
					}					
			}
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

	
	public boolean createChuyenKho(String idyeucau) 
	{
		try
		{
			String query =" select pk_seq from erp_chuyenkho where trangthai in (0) and  yeucauchuyenkho_fk=  "+idyeucau;

			ResultSet rscheck=db.get(query);
			if (rscheck != null)
			{
				if(rscheck.next()){
					this.msg="Bạn phải hoàn tất phiếu chuyển kho :"+rscheck.getString("pk_seq")+" trước khi muốn chuyển tiếp" ;
					rscheck.close();
					return false;
				}
			}			
			query = " select TSDD_FK AS TSDDID, ISNULL(TRANGTHAISP,'0') AS TRANGTHAISP ,isnull(IsChuyenHangSX,'') as IsChuyenHangSX , pk_seq ,isnull(nguoinhan,'') as nguoinhan , isnull(Lenhsanxuat_fk,0 ) as lsxid ,noidungxuat_fk, NGAYYEUCAU, lydo, isnull(ghichu, '') as ghichu, khoxuat_fk, khonhan_fk, trangthai,   NCC_CHUYEN_FK, NCC_NHAN_FK, " +
					" ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, "+
					" ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC, ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG "+
					" from ERP_YEUCAUCHUYENKHO where pk_seq = '" + idyeucau + "'";

			ResultSet rs = db.get(query);

			if(rs.next())
			{
				this.tsddId = rs.getString("TSDDID");
				this.lsxIds=rs.getString("lsxid");
				this.IsChuyenHangSX=rs.getString("IsChuyenHangSX");
				this.ndxId = rs.getString("noidungxuat_fk");
				this.ngayyeucau = rs.getString("NGAYYEUCAU");
				this.lydoyeucau = rs.getString("lydo");
				this.ghichu = rs.getString("ghichu");
				this.khoXuatId = rs.getString("khoxuat_fk");
				this.khoNhanId =  (rs.getString("khonhan_fk")==null?"":rs.getString("khonhan_fk"));
				this.trangthai = rs.getString("trangthai");
				this.Nguoinhan=rs.getString("nguoinhan");
				this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
				this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
				this.YeucauchuyenkhoId=rs.getString("pk_seq");
				this.trangthaisp=rs.getString("TRANGTHAISP");
				this.lsxIds=(rs.getString("lsxid").equals("0")? "":rs.getString("lsxid"));
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

			List<IYeucau> spList = new ArrayList<IYeucau>();
			IYeucau sp = null;

			query = " SELECT    B.PK_SEQ AS SPID,  B.MA AS SPMA, B.TEN  + ' ' + ISNULL(B.QUYCACH,'')  AS SPTEN,isnull(kho.available,0) as tonhientai  "+ 
					" , DVDL.DIENGIAI AS DVT , ( ISNULL(A.SOLUONGYEUCAU, 0)- ( "+ 
					" SELECT isnull(SUM(SOLUONGXUAT),0) FROM ERP_CHUYENKHO CK  "+
					" INNER JOIN  ERP_CHUYENKHO_SANPHAM B ON b.CHUYENKHO_FK=CK.PK_SEQ AND CK.YEUCAUCHUYENKHO_FK=A.YEUCAUCHUYENKHO_FK "+
					" and A.SANPHAM_FK=B.SANPHAM_FK "+
					" AND CK.TRANGTHAI <>4 "+
					"  )) AS SOLUONGXUAT     "+
					" FROM    ERP_YEUCAUCHUYENKHO_SANPHAM A   inner join erp_yeucauchuyenkho yc on yc.pk_seq=a.yeucauchuyenkho_fk  "+
					" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=B.DVDL_FK  " +
					" left join erp_khott_Sanpham kho on kho.khott_fk= yc.khoxuat_fk and a.sanpham_fk= kho.sanpham_fk  "+    
					" WHERE A.YEUCAUCHUYENKHO_FK = "+idyeucau+"  and ( ISNULL(A.SOLUONGYEUCAU, 0)-  ( "+ 
					" SELECT  isnull(SUM(SOLUONGXUAT),0) FROM ERP_CHUYENKHO CK  "+
					" INNER JOIN  ERP_CHUYENKHO_SANPHAM B ON b.CHUYENKHO_FK=CK.PK_SEQ AND CK.YEUCAUCHUYENKHO_FK=A.YEUCAUCHUYENKHO_FK  "+
					" and A.SANPHAM_FK=B.SANPHAM_FK "+
					" AND CK.TRANGTHAI <>4 ) ) >0 ";

			System.out.println("QUery ry: "+query);
			ResultSet rssp=db.get(query);
			while (rssp.next())
			{
				String spId = rssp.getString("spId");
				String spMa = rssp.getString("spMa");
				String spTen = rssp.getString("spTen");

				String donvi = rssp.getString("DVT");

				sp = new Yeucau();
				sp.setId(spId);
				sp.setTonhientai(rssp.getDouble("tonhientai")+"");
				sp.setMa(spMa);
				sp.setTen(spTen);
				sp.setDonViTinh(donvi);
				double tongsoluongxuat=rssp.getDouble("SOLUONGXUAT");
				sp.setSoluongYC(formatter1.format(tongsoluongxuat));
				double total_ton=0;
				double total_soluongxuat=0;

				if(this.trangthaisp.equals("0")){
					query=" SELECT AVAILABLE as soluong,SOLO,NGAYHETHAN,isnull(NGAYBATDAU,'') as NGAYBATDAU , KHUVUCKHO_FK, KHO.BIN  ,KHU.TEN AS TENKHU  FROM ERP_KHOTT_SP_CHITIET KHO " +
							" LEFT JOIN ERP_KHUVUCKHO KHU ON KHU.PK_SEQ=KHO.KHUVUCKHO_FK " +
							" WHERE available >0 and kho.khott_fk="+this.khoXuatId+"  and  SANPHAM_FK="+rssp.getString("spid") +" order by NGAYHETHAN" ;
				}else{
					query=" SELECT AVAILABLE as soluong,SOLO,NGAYHETHAN,isnull(NGAYBATDAU,'') as NGAYBATDAU , KHUVUCKHO_FK, KHO.BIN  ,KHU.TEN AS TENKHU  FROM ERP_KHOTT_SP_CHITIET_TRANGTHAI KHO " +
							" LEFT JOIN ERP_KHUVUCKHO KHU ON KHU.PK_SEQ=KHO.KHUVUCKHO_FK " +
							" WHERE available >0 and kho.khott_fk="+this.khoXuatId+"  and  SANPHAM_FK="+rssp.getString("spid") +" AND KHO.TRANGTHAI='"+this.trangthaisp+"' order by NGAYHETHAN" ;
				}

				System.out.println("LAY KHO CHI "+query);


				ResultSet rsSpDetail = db.get(query);
				List<ISpDetail> spConList = new ArrayList<ISpDetail>();
				ISpDetail spCon = null;

				while(rsSpDetail.next())
				{	
					spCon = new SpDetail();

					double slgton = rsSpDetail.getDouble("soluong")  ;
					total_ton=total_ton+ slgton;

					String solo = rsSpDetail.getString("solo");

					if( tongsoluongxuat >0) {
						if(tongsoluongxuat <rsSpDetail.getDouble("soluong")){
							spCon.setSoluong(formatter1.format(tongsoluongxuat) );
							total_soluongxuat=total_soluongxuat+ tongsoluongxuat;
							tongsoluongxuat=0;
						}else{
							tongsoluongxuat=tongsoluongxuat-rsSpDetail.getDouble("soluong");
							spCon.setSoluong(formatter1.format(slgton));
							total_soluongxuat=total_soluongxuat+ slgton;
						}
					}else{
						spCon.setSoluong("");
					}

					spCon.setSoluongton(formatter1.format(slgton));
					spCon.setSolo(solo);
					spConList.add(spCon);
					spCon.setKhu(rsSpDetail.getString("TENKHU"));
					spCon.setKhuId(rsSpDetail.getString("KHUVUCKHO_FK"));
					spCon.setNgaybatdau(rsSpDetail.getString("NGAYBATDAU"));
				}

				if(tongsoluongxuat >0 ){
					this.msg="Số lượng trong kho của sản phẩm : "+spMa +"-" +spTen+".Không còn đủ hàng để xuất.Vui lòng kiểm tra lại";
				}
				rsSpDetail.close();
				sp.setSoluongchuyen(formatter1.format(total_soluongxuat));
				sp.setSpDetailList(spConList);
				spList.add(sp);

			}
			rssp.close();
			this.spList =spList;
			return true;
		}
		catch(Exception er)
		{
			er.printStackTrace();
			this.msg=er.getMessage();
			return false;
		}
	}

	
	public String getYeucauchuyenkhoId() {
		
		return YeucauchuyenkhoId;
	}

	
	public void setYeucauchuyenkhoId(String _YeucauchuyenkhoId) {
		
		YeucauchuyenkhoId= _YeucauchuyenkhoId;
	}


	public String getTongSLYC() 
	{
		return this.tongSLYC;
	}

	public void setTongSLYC(String tongSLYC)
	{
		this.tongSLYC = tongSLYC;
	}
	
	public String getTongSLnhan() 
	{
		return this.tongSLnhan;
	}

	public void setTongSLnhan(String tongSLnhan)
	{
		this.tongSLnhan = tongSLnhan;
	}


	public String getnoiDungXuat() {
		
		return this.noidungxuat;
	}


	public void setnoiDungXuat(String noidungxuat) {
		this.noidungxuat = noidungxuat;
		
	}

	
	//------------------- IN PDF MẪU MỚI-------------//
	
	public void inPdf() {

		String query = " select b.PK_SEQ as spId,  b.MA  as spMa, b.Ten +' '+ isnull(b.quycach,'') as spTen, dvdl.donvi as spDvdl, a.SOLO, SUM(a.SOLUONGXUAT) as tongXuat, " +
					"isnull(Sum(a.SOLUONGNHAN), 0) as tongNhan  " +
				" from ERP_CHUYENKHO_SANPHAM a " +
				" inner Join ERP_SanPham b on a.SANPHAM_FK = b.PK_SEQ  " +
				" left join donvidoluong dvdl on dvdl.pk_seq = b.dvdl_fk " +
				" where a.CHUYENKHO_FK = '" + this.id + "'  " +
				" group by b.PK_SEQ,  b.MA , b.TEN, dvdl.donvi, a.SOLO,isnull(b.quycach,'')  " +
				" having SUM(a.SOLUONGXUAT) > 0 ";
		
		System.out.println("1.Khoi tao SP xem thu: " + query);
		ResultSet SPRs = db.get(query);
		
		List<ISanpham> SPList = new ArrayList<ISanpham>();
		
		if(SPRs != null)
		{
			try 
			{
				ISanpham SP = null;
				while(SPRs.next())
				{
					String SPId = SPRs.getString("spId");
					String SPMa = SPRs.getString("spMa");
					String SPTen = SPRs.getString("spTen");
					String SPlo = SPRs.getString("solo"); 
					String SPTrongluong = SPRs.getString("spDvdl");
					
					SP = new Sanpham();
					SP.setId(SPId);
					SP.setMa(SPMa);
					SP.setTen(SPTen);
					SP.setSolo(SPlo);
					SP.setDonViTinh(SPTrongluong);
					
					if(SPRs.getString("tongXuat").trim().length() > 0)
						SP.setSoluongYC(SPRs.getString("tongXuat"));
					
					if(SPRs.getString("tongNhan").trim().length() > 0)
						SP.setSoluongNhan(SPRs.getString("tongNhan"));
					
				  
					SPList.add(SP);
				}
				SPRs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("1.Exception: " + e.getMessage());
			}
			
			this.SPList = SPList;
		}
	}

	
	public List<ISanpham> getSPList() {
		
		return this.SPList;
	}

	
	public void setSPList(List<ISanpham> SPList) {
		this.SPList = SPList;
	}

	
	public String getDvkd() {
		
		return this.DvkdId;
	}

	
	public String getNguoinhanhang() {
		
		return this.Nguoinhanhang;
	}

	
	public String getXuattaikho() {
		
		return this.Khoxuat;
	}

	
	public void initXuatkhoPdf() {
		
		
		String query = " select  isnull(ISCHUYENHANGSX,'') as ISCHUYENHANGSX, isnull(Lenhsanxuat_fk,0 ) as lsxid ,noidungxuat_fk, ngaychuyen, lydo, isnull(ghichu, '') as ghichu, khoxuat_fk, khonhan_fk, " +
			       " trangthai, isnull(trangthaisp, 0) as trangthaisp, NCC_CHUYEN_FK, NCC_NHAN_FK," +
			       " ISNULL(KYHIEU,'') as KYHIEU, ISNULL(SOCHUNGTU,'') as SOCHUNGTU, ISNULL(LENHDIEUDONG,'') as LENHDIEUDONG, ISNULL(NGAYDIEUDONG,'') AS NGAYDIEUDONG, "+
			       " ISNULL(NGUOIDIEUDONG,'') AS NGUOIDIEUDONG, ISNULL(VEVIEC,'') AS VEVIEC, ISNULL(NGUOIVANCHUYEN,'') AS NGUOIVANCHUYEN, ISNULL(PHUONGTIEN,'') AS PHUONGTIEN, ISNULL(HOPDONG,'') AS HOPDONG, "+						
			       " (select SUM(SOLUONGXUAT) from ERP_CHUYENKHO_SANPHAM where CHUYENKHO_FK = '" + this.id + "' group by CHUYENKHO_FK  ) as TongSLchuyen, " +
			       " ( select TEN from erp_khott where pk_seq = ERP_CHUYENKHO.KHOXUAT_FK ) as KHOXUAT, ( select TEN from erp_khott where pk_seq = ERP_CHUYENKHO.khonhan_fk ) as KHONHAN "+
				   " from ERP_CHUYENKHO where pk_seq = '" + this.id + "'";
		System.out.println("INIT UPDATE: "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.IsChuyenHangSX=rs.getString("ISCHUYENHANGSX");
					this.lsxIds=rs.getString("lsxid");
					this.ndxId = rs.getString("noidungxuat_fk");
					this.ngayyeucau = rs.getString("ngaychuyen");
					this.lydoyeucau = rs.getString("lydo");
					this.ghichu = rs.getString("ghichu");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khoXuatTen = rs.getString("khoxuat");
					this.khoNhanTen = rs.getString("khonhan");
					this.khoNhanId = rs.getString("khonhan_fk");
					this.trangthai = rs.getString("trangthai");
					this.trangthaisp = rs.getString("trangthaisp");
					this.tongSLYC = rs.getString("TongSLchuyen")==null ? "0.000": rs.getString("TongSLchuyen");
					this.nccXuatId = rs.getString("NCC_CHUYEN_FK") == null ? "" : rs.getString("NCC_CHUYEN_FK");
					this.nccNhanId = rs.getString("NCC_NHAN_FK") == null ? "" : rs.getString("NCC_NHAN_FK");
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
		}
	
		this.createChuyenKho_SanPhamPDF();
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

	
	public String getIsQuanlykhuvuc_khoxuat() {
		
		return this.IsQuanlykhuvucKhoxuat;
	}

	
	public String getIsQuanlykhuvuc_khonhap() {
		
		return this.IsQuanlykhuvucKhonhap;
	}
 

	
	public boolean getChoPhepChuyenhangdat() {
		
		return false;
	}

	
	public ResultSet getRsKhukhonhan() {
		
		return RsKhuKhoNhan;
	}

	
	public void setRsKhukhonhan(ResultSet Rskhu) {
		
		RsKhuKhoNhan=Rskhu;
	}

	
	public void settask(String task) {
		
		this.task=task;
	}

	
	public String gettask() {
		
		return this.task;
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


	public String getDiachi() {

		return this.diachi;
	}

	
	public void setDiachi(String diachi) {
		
		this.diachi=diachi;
	}
	public ResultSet getTsddRs() 
	{
		return this.tsddRs;
	}

	public void setTsddRS(ResultSet tsddRs) 
	{
		this.tsddRs = tsddRs;
	}
	
	public String getTsddId() 
	{
		return this.tsddId;
	}

	public void setTsddId(String tsddId) 
	{
		this.tsddId = tsddId;
	}
}

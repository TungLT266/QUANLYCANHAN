package geso.traphaco.erp.beans.kiemdinhchatluong.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.kiemdinhchatluong.IErpKiemdinhchatluong_kho;
import geso.traphaco.erp.beans.kiemdinhchatluong.ISpDetail;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Utility_Kho;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
 
 
public class ErpKiemdinhchatluong_kho  extends Phan_Trang implements IErpKiemdinhchatluong_kho
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userId;
	String Id;
	String tungay;
	String denngay;

	String spId;
	String spTen;
	
	String nhId;
	String nccId;
	String solo;
	String SoLuongKhongDat;
	String congtyId, trangthai, ngaykiem ;
	String msg;
	String KhoQL_Khuvuc="0";
	
	ResultSet kdclRs, soloRs, khoRs, rsSanPham, rsLoaiSanPham, khochoxulyRs;
	
	dbutils db;
	Utility_Kho util_kho = new Utility_Kho();
	String ghichu, khoId, LOAISPID, loai, khochoxulyId;
	List<ISpDetail> listdetail;
	
	
	public ErpKiemdinhchatluong_kho()
	{
		this.Id = "";
		this.userId = "";
		this.spId = "";
		this.spTen = "";
		this.nhId = "";
		this.nccId="";
		this.solo = "";
		this.msg = "";
		this.congtyId="";
		this.trangthai="";
		this.ngaykiem="";
		this.db = new dbutils();
		this.khoId = "";
		this.khochoxulyId = "";
		this.ghichu = "";
		this.LOAISPID = "";

		this.tungay = "";
		this.denngay = "";
		this.listdetail=  new ArrayList<ISpDetail>();	
	}
	
	public ErpKiemdinhchatluong_kho(String id)
	{
		this.Id = id;
		this.userId = "";
		this.spId = "";
		this.spTen = "";
		this.nhId = "";
		this.nccId="";
		this.solo = "";
		this.msg = "";
		this.congtyId="";
		this.trangthai="";
		this.ngaykiem="";
		this.db = new dbutils();
		this.khoId = "";
		this.ghichu = "";
		this.LOAISPID = "";
		this.khochoxulyId = "";
		this.tungay = "";
		this.denngay = "";
		this.listdetail=  new ArrayList<ISpDetail>();	
		
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
		return this.Id;
	}

	public void setId(String Id) 
	{
		this.Id = Id;
	}

	public String getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String spId) 
	{
		this.spId = spId;
	}

	public String getSpTen() 
	{
		return this.spTen;
	}

	public void setSpTen(String spTen)
	{
		this.spTen = spTen;
	}

	public String getSolo() 
	{
		return this.solo;
	}

	public void setSolo(String solo) 
	{
		this.solo = solo;
	}


	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public String getKhoChoXuLyId() 
	{
		return this.khochoxulyId;
	}

	public void setKhoChoXuLyId(String khochoxulyId)
	{
		this.khochoxulyId = khochoxulyId;
	}
	
	public String getGhichu() 
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public String getTungay() 
	{
		return this.tungay;
	}

	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}

	public String getDenngay() 
	{
		return this.denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public boolean updateKiemdinh(HttpServletRequest request)
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query =  "";
			String id_current="";
			
			if(this.Id.length() > 0){
				query = " UPDATE ERP_KIEMDINHCHATLUONG_KHO SET NGAYKD = '" + this.ngaykiem + "', SANPHAM_FK = " + this.spId + ", KHO_FK = " + this.khoId + ", KHOCHOXULY_FK = " + this.khochoxulyId + ", " +
						" SOLO = N'" + this.solo + "', SOLUONGKHONGDAT = " +  this.SoLuongKhongDat + ", GHICHU = N'" + this.ghichu.trim() + "', " +
						" NGAYCHOT =  '" + this.getDateTime() + "', NGUOICHOT = " + this.userId + " WHERE PK_SEQ = " + this.Id;
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật " + query;
					db.update("rollback");
					return false;
				}
				id_current=this.Id;
			}else{
				query = " INSERT INTO ERP_KIEMDINHCHATLUONG_KHO(NGAYKD, SANPHAM_FK, KHO_FK, KHOCHOXULY_FK, SOLO, SOLUONGKHONGDAT, GHICHU, TRANGTHAI, NGAYCHOT, NGUOICHOT) \n" +
						" VALUES('" + this.ngaykiem + "', " + this.spId + ", " + this.khoId + ", " + this.khochoxulyId + ", N'" + this.solo + "', " + this.SoLuongKhongDat + ", \n" +
								"N'" + this.ghichu.trim() + "', '0', '" + this.getDateTime() + "', " + this.userId + ")\n";
				
				System.out.println("cau lenh insert kiem dinh:\n" + query  + "\n=====================================");
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật " + query;
						db.update("rollback");
						return false;
					}
					
					query = "SELECT SCOPE_IDENTITY() AS ID";
					ResultSet rs = this.db.get(query); 
					rs.next();
					
					id_current= rs.getString("ID");
					
					rs.close();
			}
			 
			
		 
			query="delete ERP_KIEMDINHCHATLUONG_KHO_CHITIET where  KIEMDINHCHATLUONGKHO_FK="+id_current;
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật " + query;
				db.update("rollback");
				return false;
			}
			
			
			if(this.listdetail.size() >0){
				for(int i=0;i<this.listdetail.size();i++){
					ISpDetail sp=this.listdetail.get(i);
					 
					double soluongct_=0;
					try{
						soluongct_=Double.parseDouble(sp.getSoluong().replaceAll(",",""));
					}catch(Exception er){
						er.printStackTrace();
					}
					
					
					query="INSERT INTO ERP_KIEMDINHCHATLUONG_KHO_CHITIET(KIEMDINHCHATLUONGKHO_FK,SANPHAM_FK,KHUVUCKHO_FK,SOLO,SOLUONG) " +
							"VALUES("+id_current+","+sp.getId()+","+(sp.getKhuId().length() >0? sp.getKhuId():"NULL")+",'"+sp.getSolo()+"',"+soluongct_+") ";
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật " + query;
						db.update("rollback");
						return false;
					}
					
				}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
		} 
		catch (Exception e) 
		{
			this.msg = "Không thể cập nhật ";
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
		
		return true;
	}

	public void init(String search) 
	{
		String query = "" ;
		
		if(search.length() > 0){
			query = search;
		}else{
			if(this.Id.length() > 0){
				query = "SELECT SP.PK_SEQ AS SPID, SP.TEN AS SPTEN, KD.SOLO, ISNULL(KD.SOLUONGKHONGDAT, 0) AS SOLUONGKHONGDAT, " +
						"KD.TRANGTHAI, KD.NGAYCHOT, LSP.PK_SEQ AS LOAISPID, KD.KHO_FK AS KHOID, KD.KHOCHOXULY_FK AS KHOCHOXULYID, " +
						"NV.TEN AS NGUOICHOT, KD.NGAYKD, RTRIM(LTRIM(KD.GHICHU)) AS GHICHU " +
						"FROM ERP_KIEMDINHCHATLUONG_KHO KD " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KD.SANPHAM_FK " +
						"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = KD.NGUOICHOT " +
						"WHERE KD.PK_SEQ = " + this.Id + "";
				
				System.out.println(query);
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						if(rs.next())
						{
							this.spTen = rs.getString("SPTEN");
							this.spId = rs.getString("SPID");
							this.LOAISPID = rs.getString("LOAISPID");
							this.solo = rs.getString("SOLO");
							this.SoLuongKhongDat = rs.getString("SOLUONGKHONGDAT"); 
							this.trangthai = rs.getString("TRANGTHAI").trim();
							this.ngaykiem = rs.getString("NGAYKD").trim().length() <= 0 ? getDateTime() : rs.getString("NGAYKD").trim();
							this.khoId =  rs.getString("KHOID");
							this.khochoxulyId = rs.getString("KHOCHOXULYID");
							this.ghichu = rs.getString("GHICHU").trim();
						}
						rs.close();
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				if(this.Id.length()>0){
					
					if(this.khoId!=null && this.khoId.length()>0 && this.spId!=null && this.spId.length()>0){
						
						query = " SELECT   A.SANPHAM_FK, E.MA AS MASP, E.TEN AS TENSP, E.LOAISANPHAM_FK, A.SOLO, A.KHOTT_FK, A.AVAILABLE  ,isnull( A.KHUVUCKHO_FK,0) as KVKHOID, isnull(khu.TEN,'') as KVKHO "+
						" ,A.NGAYNHAPKHO,A.NGAYHETHAN,a.ngaysanxuat,isnull(kd.soluong,0) as soluong "+
						" FROM ERP_KHOTT_SP_CHITIET A "+ 
						" LEFT JOIN ERP_SANPHAM E ON E.PK_SEQ = A.SANPHAM_FK "+  
						" LEFT JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = E.LOAISANPHAM_FK "+ 
						" left join ERP_KHUVUCKHO khu on khu.PK_SEQ=A.KHUVUCKHO_FK " +
						" left join ERP_KIEMDINHCHATLUONG_KHO_CHITIET KD ON KD.KIEMDINHCHATLUONGKHO_FK= "+this.Id+" AND KD.SANPHAM_FK=A.SANPHAM_FK AND KD.SOLO=A.SOLO AND isnull( A.KHUVUCKHO_FK,0) =isnull( KD.KHUVUCKHO_FK,0)  "+
						" WHERE A.SANPHAM_FK = "+this.spId+" AND A.AVAILABLE > 0 AND A.KHOTT_FK ="+this.khoId +
						" ORDER BY A.SOLO ";

					 
					try {
						ResultSet rsSpDetail = this.db.get(query);
						List<ISpDetail> spDetail = new ArrayList<ISpDetail>();
						if(rsSpDetail!=null){
							while(rsSpDetail.next()){
								ISpDetail  splo =new SpDetail();
								splo.setSolo(rsSpDetail.getString("solo"));
								splo.setKhuId(rsSpDetail.getString("KVKHOID"));
								splo.setKhu(rsSpDetail.getString("KVKHO"));
								splo.setNgaysanxuat(rsSpDetail.getString("ngaysanxuat"));
								splo.setNgayhethan(rsSpDetail.getString("ngayhethan"));
								double avai =  rsSpDetail.getDouble("AVAILABLE")  ;
								splo.setSoluong(""+rsSpDetail.getDouble("soluong"));
								splo.setSoluongton(""+avai);
								spDetail.add(splo);
								
							}
				
							rsSpDetail.close();
						}
						this.listdetail=spDetail;
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
					}
					
				}
				
				query = "";
				
			
				
			}else{
				query = " SELECT KD.PK_SEQ, SP.TEN AS SPTEN, KD.SOLO,  isnull((select SUM(soluong) from ERP_KIEMDINHCHATLUONG_KHO_CHITIET a "+ 
						" where a.KIEMDINHCHATLUONGKHO_FK=KD.PK_SEQ  ),0)  AS SOLUONGKHONGDAT, KD.TRANGTHAI, " +
						" KD.NGAYKD, NV.TEN AS NGUOICHOT " +
						" FROM ERP_KIEMDINHCHATLUONG_KHO KD " +
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KD.SANPHAM_FK " +
						" INNER JOIN NHANVIEN NV ON NV.PK_SEQ = KD.NGUOICHOT " ;
			}
			
		}
		
		if(query.length() > 0){
			System.out.println(query);
			this.kdclRs = this.db.get(query);
		}
		
		createRs();
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
		this.congtyId=congtyId;
		
	}

	
	public boolean duyetKiemDinh()
	{

		try
		{
 			this.solo = this.solo.trim();
//			Utility util = new Utility();
//			Utility_Kho util_kho = new Utility_Kho();
				
			this.db.getConnection().setAutoCommit(false);
				 
//			double dongia = 0;
				 
			// đạt thì chuyển hàng qua kho thành phẩm,không đạt thì giữ nguyên trạng thái
			    
//			String query = "";
			   
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch (Exception e)
		{
			db.update("rollback");
			this.msg = "Không thể cập nhật ERP_YeuCauKiemDinh " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String Hoantatkiemdinh(String id, String userId) {
		String msg = "";
		try{
			db.getConnection().setAutoCommit(false);
			
			String  query = "";
			this.Id=id;
			
			
			
			query = " SELECT * FROM ERP_KIEMDINHCHATLUONG_KHO KD_KHO   "+
					" WHERE KD_KHO.PK_SEQ =  "+id;
			
			ResultSet rs = db.get(query);
			
			if(rs.next()){
				
//				double CPCAPDONG = 0;
//				double CPLUUKHO = 0;
				this.ngaykiem=rs.getString("NGAYKD");
				String khonhan="";
				String khochoxulyId = rs.getString("KHOCHOXULY_FK");
				khonhan=khochoxulyId;
				String spId = rs.getString("SANPHAM_FK");
				String khoId = rs.getString("KHO_FK");
				
				  List<ISpDetail> spdetail = new ArrayList<ISpDetail>();
				  List<ISpDetail> spdetail_xuat = new ArrayList<ISpDetail>();
				  
				  
				  	query = " SELECT   A.SANPHAM_FK, E.MA AS MASP, E.TEN AS TENSP, E.LOAISANPHAM_FK, A.SOLO, A.KHOTT_FK, A.AVAILABLE  ,isnull( A.KHUVUCKHO_FK,0) as KVKHOID, isnull(khu.TEN,'') as KVKHO "+
					" ,A.NGAYNHAPKHO,A.NGAYHETHAN,a.ngaysanxuat,isnull(kd.soluong,0) as soluong "+
					" FROM ERP_KHOTT_SP_CHITIET A "+ 
					" LEFT JOIN ERP_SANPHAM E ON E.PK_SEQ = A.SANPHAM_FK "+  
					" LEFT JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = E.LOAISANPHAM_FK "+ 
					" left join ERP_KHUVUCKHO khu on khu.PK_SEQ=A.KHUVUCKHO_FK " +
					" inner join ERP_KIEMDINHCHATLUONG_KHO_CHITIET KD ON KD.KIEMDINHCHATLUONGKHO_FK= "+id+" AND KD.SANPHAM_FK=A.SANPHAM_FK AND KD.SOLO=A.SOLO AND isnull( A.KHUVUCKHO_FK,0) =isnull( KD.KHUVUCKHO_FK,0)  "+
					" WHERE  A.AVAILABLE > 0 AND A.KHOTT_FK ="+khoId+
					" ORDER BY A.SOLO "; 
							 ResultSet rsdetail=db.get(query);
					 while(rsdetail.next()){
							
							
							String solo = rsdetail.getString("SOLO");
							double slkodat = rsdetail.getDouble("soluong");
							 
							String khuvuckhoId = rsdetail.getString("KVKHOID");
							 
							String NgayNhapKho = rsdetail.getString("NGAYNHAPKHO");
							String NgaySanXuat = rsdetail.getString("NGAYSANXUAT");
							String NGAYHETHAN = rsdetail.getString("NGAYHETHAN");
							double dongia = 0;
							query  = "SELECT isnull(GIATON,0) as GIATON  FROM ERP_KHOTT_SANPHAM " +
									 "WHERE KHOTT_FK = " + khoId + " AND SANPHAM_FK = " + spId ;
							
							rs = this.db.get(query);
							if(rs.next()){
								dongia = rs.getDouble("GIATON");
							}else{
								db.getConnection().rollback();
								this.msg= "Không xác định được giá tồn kho của sản phẩm  : " + this.spId;
								return this.msg;
							}
						
						
					
							rs.close();
			 
						// 	Chỉnh sửa: Hàng không đạt sau khi kiểm định  sẽ chuyển sang kho chờ xử lý đã nhận,kho xuất là kho chọn kiểm định
			
							   ISpDetail sp=new SpDetail();
							   sp.setSolo(solo);
							   sp.setSoluong(""+slkodat);
							   sp.setMa(spId);
							   sp.setNgayhethan(NGAYHETHAN);
							   sp.setNgaysanxuat(NgaySanXuat);
							   sp.setNgayNhapKho(NgayNhapKho);
							   
							   sp.setDongia(dongia);
							   
							   spdetail.add(sp);
							     
							   ISpDetail spxuat =new SpDetail();
							   spxuat.setSolo(solo);
							   spxuat.setSoluong(""+slkodat);
							   spxuat.setMa(spId);
							   spxuat.setKhuId(khuvuckhoId==null?"":khuvuckhoId);
							 
							   spdetail_xuat.add(spxuat);
				   
				 	}
					rsdetail.close();
				    if(util_kho.getIsQuanLyKhuVuc(khonhan, db).equals("1")){
				    	   db.update("rollback");
						   this.msg="Không xác định kho chờ tái chế không quản lý khu vực,vui lòng liên hệ admin để xử lý trường hợp này";
						   return this.msg;
						   
				    }
				    String trangthaisp="1";
				    if(util_kho.IsKhoQuanLyTrangThai(khoId, db)==true){
				    		db.update("rollback");
						    this.msg="Chưa xác định được tình trạng của sản phẩm chuyển.Vui lòng báo với admin để được xử lý ";
						    return this.msg;
				    }
				    
				   
				    if(this.ngaykiem==null || this.ngaykiem.length() ==0){
				    	db.update("rollback");
						return "Không thể hoàn tất,lỗi ngày kiểm định không xác định";
				    }
				   String msg1=this.createChuyenkho(this.ngaykiem ,khoId, khonhan, spdetail,spdetail_xuat, "Chuyển kho hàng không đạt của YCKD_KHO:"+id,trangthaisp,false);
				   if(msg1.length() >0){
					   db.update("rollback");
					   this.msg=msg1;
					   return this.msg;
				   }
			 
			 	
					query = " UPDATE ERP_KIEMDINHCHATLUONG_KHO SET TRANGTHAI = '1' WHERE PK_SEQ = " + id;
					if(!db.update(query)){
						db.update("rollback");
						return "Không thể hoàn tất vui lòng thử lại";
					}
					
					
			}else{
				
				db.getConnection().rollback();
				this.msg= "Không xác định được lô hàng kiểm định";
				return this.msg;
			}
					
					this.db.getConnection().commit();
					this.db.getConnection().setAutoCommit(true);
			
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			return er.getMessage();
		}
		
		return (msg); 
		
		
	}

	private String createChuyenkho(String ngaychotnv,String khochuyen, String khonhan,
			List<ISpDetail> spdetail,List<ISpDetail> spdetail_xuat, String lydo,String trangthaisp,boolean ishangmau ) {
	 
		 try{
			 //100006	XC06	Chuyển kho nội bộ
			 
//			 String  iskhonhan_qlkhuvuc=util_kho.getIsQuanLyKhuVuc(khonhan, this.db);
			 
			String query = " insert ERP_CHUYENKHO( noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, khoxuat_fk, khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK,YCKD_FK ,ISHANGDIDUONG,trangthai) " +
				" values(100006, '" + ngaychotnv + "', '" + ngaychotnv + "', '" + ngaychotnv + "', N'"+lydo+"',  '" + khochuyen + "', " + khonhan + ", '"+trangthaisp+"', " +
						" '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', NULL, NULL,  "+this.Id+",'1','0')";
			 
			if(!db.update(query))
			{
				return  "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
			}
		
			
			String ckId = "";
			query = "select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
			ResultSet rsCk = db.get(query);
			if (rsCk.next())
			{
				ckId = rsCk.getString("ckId");
			} else {
				return "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
			 
			}
			rsCk.close();
			
			String Xk_Id="";
			
			
			for(int i = 0; i < spdetail_xuat.size(); i++) {
				
				ISpDetail sp = spdetail_xuat.get(i);
				 
				query = " insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK, SOLUONGYEUCAU,SOLUONGXUAT,SOLUONGNHAN) " +
				"values( '" + ckId + "', '" + sp.getMa() + "', " + sp.getSoluong()+ "," + sp.getSoluong() + ","+sp.getSoluong()+" ) ";
		
				if(!db.update(query))
				{
					return  "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
				}
				query="INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG ) " +
				  " VALUES ("+ckId+", "+sp.getMa()+",'"+sp.getSolo()+"',"+(sp.getKhuId().length() > 0 ?sp.getKhuId():"NULL")+",'"+sp.getNgayNhapkho()+"',"+sp.getSoluong()+")";
				if(db.updateReturnInt(query)< 1)
				{
					return "Không thể cập nhật : " + query;
				}
				
				query="SELECT PK_SEQ FROM  ERP_CHUYENKHO_SP_XUATHANG WHERE  CHUYENKHO_FK="+ckId;
				ResultSet RsCk=db.get(query);
				
				if(RsCk.next()){
					Xk_Id=RsCk.getString("PK_SEQ");
				}
				RsCk.close();
			    
			    query=	"  SELECT AVAILABLE,sp.pk_seq,sp.ma+ ' - ' + sp.ten    as tensp FROM  ERP_KHOTT_SANPHAM  kho inner join erp_sanpham sp on sp.pk_seq=sanpham_fk " +
			    		"  WHERE KHOTT_FK="+khochuyen+" AND SANPHAM_FK="+sp.getMa()+"" ;
			    	
			    ResultSet rscheckkho=db.get(query);
			    double soluongton_=0;
			    String tensp="";
			   
			    if(rscheckkho.next()){
			    	soluongton_=rscheckkho.getDouble("AVAILABLE");
			    	tensp=rscheckkho.getString("pk_seq")+ "-" +rscheckkho.getString("tensp");
			    }
			    rscheckkho.close();
			    
			    if(soluongton_< Double.parseDouble(sp.getSoluong()) ){
			    	return " Không thể tạo chuyển kho cho sản phẩm [ "+tensp+" ] của phiếu kiểm định này ,do hàng ở kho sản xuất không còn để chuyển, " +
			    		   " vui lòng kiểm tra báo cáo xuất nhập tồn chi tiết để biết rõ hàng đã chuyển từ chức năng nào để theo dõi ";
			    }
			    double soluongct=(-1)*Double.parseDouble(sp.getSoluong());
			    double booked=0;
			    double available=(-1)*Double.parseDouble(sp.getSoluong());
			  
			    double dongia=0;
			    
			    String msg1 =util_kho.Update_Kho_Sp_Check_TonKhoNgay( db, khochuyen,sp.getMa(), soluongct, booked, available, dongia,ngaychotnv);
				if(msg1.length() >0)
				{
					return msg1;
				}
				String vitri="";
				msg1 =util_kho.Update_Kho_Sp_Chitiet(db, khochuyen,sp.getMa(), soluongct, booked, available, dongia,sp.getSolo(), vitri, sp.getKhuId(), sp.getNgayNhapkho());
				if(msg1.length() >0)
				{
					return msg1;
				}

			 }
			 
			query= " update erp_chuyenkho set trangthai=1  where pk_Seq="+ckId;
			
			if(!db.update(query))
			{
				return  "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
			}

		 }catch(Exception er){
			 er.printStackTrace();
			 return er.getMessage();
		 }
		 return "";
	}
 
 
	public String getTrangThai()
	{

		return this.trangthai;
	}

	
	public void setTrangThai(String trangthai)
	{
		this.trangthai=trangthai;
	}

	
	public void createRs()
	{
		String query = "";
		
		this.rsLoaiSanPham = this.db.get("SELECT PK_SEQ AS LOAISPID, TEN FROM ERP_LOAISANPHAM WHERE TRANGTHAI = 1");
		
		if(this.LOAISPID.trim().length() > 0){
			query = "SELECT PK_SEQ AS SPID, MA + ' - ' + TEN AS TEN " +
					"FROM ERP_SANPHAM " +
					"WHERE TRANGTHAI = 1 AND LOAISANPHAM_FK = " + this.LOAISPID + "";
			query += "ORDER BY MA + ' - ' + TEN ";
				
			this.rsSanPham = this.db.get(query);
		}
			
		query = "SELECT PK_SEQ AS KHOID, TEN " +
				"FROM ERP_KHOTT WHERE PK_SEQ " +
				"NOT IN (100003, 100004, 100012, 100013, 100014, 100015, 100016, 100017, 100018, 100019, 100020, 100021, 100022)";
		this.khoRs = this.db.get(query);
		
		query = "SELECT PK_SEQ AS KHOID, MA + ' - ' + TEN AS TEN " +
				"FROM ERP_KHOTT WHERE PK_SEQ " +
				"IN (100015, 100016, 100017, 100018, 100019, 100020, 100021, 100022)";
		
		if(this.khoId!=null && this.khoId.length() >0){
			this.KhoQL_Khuvuc=util_kho.getIsQuanLyKhuVuc(this.khoId, db);
		}
		
		this.khochoxulyRs = this.db.get(query);

		if(this.spId.length() > 0 & this.khoId.length() > 0){
			query = "SELECT DISTINCT A.SANPHAM_FK, E.MA AS MASP, E.TEN AS TENSP, E.LOAISANPHAM_FK, A.SOLO, A.KHOTT_FK, A.AVAILABLE " +
					"FROM ERP_KHOTT_SP_CHITIET A  " +
					"LEFT JOIN ERP_SANPHAM E ON E.PK_SEQ = A.SANPHAM_FK " + 
					"LEFT JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = E.LOAISANPHAM_FK " + 
					"WHERE A.SANPHAM_FK = '" + this.spId + "' AND A.AVAILABLE > 0 AND A.KHOTT_FK = " + this.khoId + " " +
					"ORDER BY A.SOLO ";
 
			this.soloRs = this.db.get(query);
		}
		
		
	}
	public void init_sanpham(){
		if(this.khoId!=null && this.khoId.length()>0 && this.spId!=null && this.spId.length()>0){
				
			String	query = " SELECT   A.SANPHAM_FK, E.MA AS MASP, E.TEN AS TENSP, E.LOAISANPHAM_FK, A.SOLO, A.KHOTT_FK, A.AVAILABLE  ,isnull( A.KHUVUCKHO_FK,0) as KVKHOID, isnull(khu.TEN,'') as KVKHO "+
				" ,A.NGAYNHAPKHO,A.NGAYHETHAN,a.ngaysanxuat "+
				" FROM ERP_KHOTT_SP_CHITIET A "+ 
				" LEFT JOIN ERP_SANPHAM E ON E.PK_SEQ = A.SANPHAM_FK "+  
				" LEFT JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = E.LOAISANPHAM_FK "+ 
				" left join ERP_KHUVUCKHO khu on khu.PK_SEQ=A.KHUVUCKHO_FK "+
				" WHERE A.SANPHAM_FK = "+this.spId+" AND A.AVAILABLE > 0 AND A.KHOTT_FK ="+this.khoId +
				" ORDER BY A.SOLO";
	
			 
			try {
				ResultSet rsSpDetail = this.db.get(query);
				List<ISpDetail> spDetail = new ArrayList<ISpDetail>();
				if(rsSpDetail!=null){
					while(rsSpDetail.next()){
						ISpDetail  splo =new SpDetail();
						splo.setSolo(rsSpDetail.getString("solo"));
						splo.setKhuId(rsSpDetail.getString("KVKHOID"));
						splo.setKhu(rsSpDetail.getString("KVKHO"));
						splo.setNgaysanxuat(rsSpDetail.getString("ngaysanxuat"));
						splo.setNgayhethan(rsSpDetail.getString("ngayhethan"));
						double avai =  rsSpDetail.getDouble("AVAILABLE")  ;
						splo.setSoluong("");
						splo.setSoluongton(""+avai);
						spDetail.add(splo);
						
					}
		
					rsSpDetail.close();
				}
				this.listdetail=spDetail;
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		}
	}
	
	public String getsoluongkhongDat()
	{
		return this.SoLuongKhongDat;
	}

	
	public void setsoluongkhongDat(String soluongkhongDat)
	{
		this.SoLuongKhongDat = soluongkhongDat;
	}

	
	public String getNgayKiem()
	{
		return this.ngaykiem;
	}

	public void setNgayKiem(String ngaykiem)
	{
		this.ngaykiem=ngaykiem;
	}

	public void setRsSolo(ResultSet soloRs)
	{
		this.soloRs = soloRs;
	}

	
	public ResultSet getRsSolo()
	{
		return this.soloRs;
	}
	
	public void setRsKdcl(ResultSet kdclRs)
	{
		this.kdclRs = kdclRs;
	}

	
	public ResultSet getRsKdcl()
	{
		return this.kdclRs;
	}
	
	public void setRsKho(ResultSet khoRs)
	{
		this.khoRs = khoRs;
	}

	
	public ResultSet getRsKho()
	{
		return this.khoRs;
	}

	public void setRsKhoChoXuLy(ResultSet khochoxulyRs)
	{
		this.khochoxulyRs = khochoxulyRs;
	}

	
	public ResultSet getRsKhoChoXuLy()
	{
		return this.khochoxulyRs;
	}
	
	public ResultSet getRsSanPham()
	{
		return this.rsSanPham;
	}
	
	public void setRsSanPham(ResultSet rsSanPham)
	{
		this.rsSanPham = rsSanPham;
	}
	
	public ResultSet getRsLoaiSanPham()
	{
		return this.rsLoaiSanPham;
	}
	
	public void setRsLoaiSanPham(ResultSet rsLoaiSanPham)
	{
		this.rsLoaiSanPham = rsLoaiSanPham;
	}

	public String getLOAISPID()
	{
		return this.LOAISPID;
	}
	
	public void setLOAISPID(String loaispId)
	{
		this.LOAISPID = loaispId;
	}

	public void DbClose()
	{
		try
		{
			if (this.listdetail != null)
				this.listdetail.clear(); 
			if(soloRs != null) soloRs.close();
			if(khoRs != null) khoRs.close();
			if(rsSanPham != null) rsSanPham.close();
			if(rsLoaiSanPham != null) rsLoaiSanPham.close();
			if(this.kdclRs != null) this.kdclRs.close();
		} catch (java.sql.SQLException e)
		{	
			e.printStackTrace();
		}finally{
			if(db!=null) db.shutDown();
		}
	}

	@Override
	public String Xoakiemdinh(String id, String userId) {
		String msg = "";
		try{
			db.getConnection().setAutoCommit(false);
			
		 
			 
	 
			String query = " UPDATE ERP_KIEMDINHCHATLUONG_KHO SET TRANGTHAI = '2' WHERE PK_SEQ = " + id;
			if(!db.update(query)){
				db.update("rollback");
				return "Không thể hoàn tất vui lòng thử lại";
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			return er.getMessage();
		}
		
		return (msg); 
		
	}

	@Override
	public String getIsKhuvuc() {
		// TODO Auto-generated method stub
		return this.KhoQL_Khuvuc;
	}

	@Override
	public void setIsKhuvuc(String iskhuvuc) {
		// TODO Auto-generated method stub
		this.KhoQL_Khuvuc=iskhuvuc;
	}

	@Override
	public List<ISpDetail> getSpDetailList() {
		// TODO Auto-generated method stub
		return listdetail;
	}

	@Override
	public void setSpDetailList(List<ISpDetail> list) {
		// TODO Auto-generated method stub
		listdetail=list;
	}
	
}

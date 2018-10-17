package geso.traphaco.erp.beans.phieuxuatkho.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.phieuxuatkho.IErpPhieuxuatkho;
import geso.traphaco.erp.beans.phieuxuatkho.ISanpham;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpPhieuxuatkho implements IErpPhieuxuatkho 
{
	String userId;
	String id;
	String ngayxuatkho;
	String ngaychotNV;
	
	String nppId,nppTen;
	ResultSet nppRs;
	
	ResultSet nppRs2;
	//String nppIds;
	
	String nccId;
	ResultSet nccRs;
	
	//String ddhId;
	ResultSet ddhRs, ddhRs2;
	String trahangNccId;
	ResultSet trahangNccRs;
	String inddhId="";
	//String inddhIds="";
	String khoId;
	ResultSet khoRs;
	
	String ndxId;
	ResultSet ndxRs;
	
	String lydoxuat;
	String ghichu;
	String trangthai;
	boolean quanlybean;
	boolean quanlylo;
	float soluongxuat;
	List<ISanpham> spList;
	
	//pdf
	String nguoinhanhang;
	String diachinhan;
	String xuattaikho;
	String Loaixuatkho;
	String DdhId;
	ResultSet RsDondathang;
	
	String msg;

	dbutils db;
	
	String maphieu ="";
	//String ddhIds;
	private ResultSet hdtcList;
	private String hdtcId;
	 String IsKhoXuatQL_Khuvuc="";
	 Utility_Kho util_kho=new Utility_Kho();
	 Utility util=new Utility();
	 NumberFormat formatter_6le = new DecimalFormat("#,###,###.######");  
	public String getMaphieu() {
		return maphieu;
	}
	
	
	public ErpPhieuxuatkho()
	{
		this.userId = "";
		this.id = "";
		this.ngayxuatkho = "";
		this.ngaychotNV = "";
		this.nppId = "";
		this.nppTen="";
		this.hdtcId = "";
		this.khoId = "";
		this.ndxId = "";
		this.lydoxuat = "";
		this.ghichu = "";
		this.trangthai = "";
		this.msg = "";
		this.nccId = "";
		this.DdhId="";
		this.trahangNccId = "";
		this.quanlybean = false;
		this.quanlylo = false;
		this.Loaixuatkho="";
		this.nguoinhanhang = "";
		this.diachinhan = "";
		this.xuattaikho = "";
		//this.nppIds = "";
		//this.ddhIds = "";
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
	}
	
	public ErpPhieuxuatkho(String id)
	{
		this.userId = "";
		this.id = id;
		this.ngayxuatkho = "";
		this.ngaychotNV = "";
		this.nppId = "";
		this.nppTen="";
		this.khoId = "";
		this.ndxId = "";
		this.lydoxuat = "";
		this.ghichu = "";
		this.trangthai = "";
		this.DdhId="";
		this.msg = "";
		this.quanlybean = false;
		this.quanlylo = false;
		this.Loaixuatkho="";
		this.nguoinhanhang = "";
		this.diachinhan = "";
		this.xuattaikho = "";
		this.nccId = "";
		this.trahangNccId = "";
		//this.nppIds = "";
		this.hdtcId = "";
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
	}
	public void setInddhId(String inddhId) {
		this.inddhId = inddhId;
	}
	public String getInddhId() {
		return inddhId;
	}
	public void setInddhIds(String inddhIds) {
		//this.inddhIds = inddhIds;
	}
	public String getInddhIds() {
		return "";
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
	
	public String getngayxuatkho()
	{
		return this.ngayxuatkho;
	}

	public void setngayxuatkho(String ngayxuatkho) 
	{
		this.ngayxuatkho = ngayxuatkho;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public String getNgayxuatkho() 
	{
		return this.ngayxuatkho;
	}

	public void setNgayxuatkho(String ngayxuatkho) 
	{
		this.ngayxuatkho = ngayxuatkho;
	}

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppid)
	{
		this.nppId = nppid;
	}

	public ResultSet getNppList() 
	{
		return this.nppRs;
	}

	public void setNppList(ResultSet nppList)
	{
		this.nppRs = nppList;
	}

	public String getNppIds() 
	{
		return "";
	}

	public void setNppIds(String nppids)
	{
		//this.nppIds = nppids;
	}

	public ResultSet getNppList2() 
	{
		return this.nppRs2;
	}

	public void setNppList2(ResultSet nppList2)
	{
		this.nppRs2 = nppList2;
	}

	/*public String getDondathangId()
	{
		return this.ddhId;
	}

	public void setDondathangId(String ddhid)
	{
		this.ddhId = ddhid;
	}*/

	/*public String getDdhIds()
	{
		return this.ddhIds;
	}

	public void setDdhIds(String ddhids)
	{
		this.ddhIds = ddhids;
	}*/

	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId) 
	{
		this.khoId = khoId;
	}

	public ResultSet getKhoList()
	{
		return this.khoRs;
	}

	public void setKhoList(ResultSet kholist) 
	{
		this.khoRs = kholist;
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

	public boolean createPxk()
	{
		
		
		String msg1=util.checkNgayHopLe(this.db, this.ngayxuatkho);
		if(msg1.length() > 0)
		{
			this.msg = msg1;
			return false;
		}
 
		if(this.spList.size() > 0)
		{
			for(int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				List<ISpDetail> spDetail = sp.getSpDetailList();
				
				double sum = 0;
				for(int j = 0; j < spDetail.size(); j++)
				{
					sum += Double.parseDouble(spDetail.get(j).getSoluong());
				}
				if(sum > Double.parseDouble(sp.getSoluongTotal()) - Double.parseDouble(sp.getSoluongDaXuat()))
				{
					this.msg += "+ Sản phẩm " + sp.getMa() + " - " + sp.getDiengiai() + ", không  được vượt số lượng của đơn hàng/ trả hàng, vui lòng kiểm tra lại \n";
				}
			}
		}
			
		if(this.msg.length() > 0)
		{
			this.msg = "Vui lòng kiểm tra lại các thông tin sau trước khi xuất kho: \n" + this.msg;
			System.out.println(this.msg);
			return false;
		}	 
		else
		{
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong đơn mua hàng trước khi xuất kho: \n" + this.msg;
				System.out.println(this.msg);
				return false;
			}
		}
		if(this.Loaixuatkho.equals("DH") && this.DdhId.length() >0){
			  msg1=this.check_daxuatkho();
				if(msg1.length() >0){
					this.msg=msg1;
					return false;
				}
		}
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			if(this.ndxId.trim().length()>0&& this.ndxId.equals("100007")){//XUẤT KHO CHO ĐƠN TRẢ HÀNG NCC
				String query = 	"";
				if(this.trahangNccId != null && this.trahangNccId.length()>0){
					query = 	    
						" SELECT a.KHOTT_FK,KHO.MA \n" +
						" FROM 	 ERP_MUAHANG_SP a  \n"+  
		  				" 		 INNER JOIN ERP_KHOTT KHO ON  a.KHOTT_FK = KHO.PK_SEQ   \n"+
		  				" WHERE  a.MUAHANG_FK =  "+this.trahangNccId+" AND a.KHOTT_FK= "+this.khoId;
				 
					ResultSet rsCheck = db.get(query);
				 
					if(!rsCheck.next())
					{
						db.getConnection().rollback();
						this.msg = "Kho xuất phải là kho trả của nhà phân phối khi làm đơn trả hàng.  ";
						return false;
					}
					
				}
				else
				{
					 
					this.msg = "Không có đơn trả hàng NCC nào được chọn";
					return false;
				}
				String ngaytao = this.getDateTime();
				
				query = "insert ERP_XUATKHO(NGAYXUAT, NGAYCHOT, TRAHANGNCC_FK, NOIDUNGXUAT, KHO_FK, LYDOXUAT, GHICHU, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI, NPP_FK ) " +
						" values('" + this.ngayxuatkho + "', '" + this.ngayxuatkho + "', " + this.trahangNccId + ", '" + this.ndxId + "', '" + this.khoId + "', N'" + this.lydoxuat + "', N'" + this.ghichu + "', " +
						"'" + this.userId + "', '" + this.userId + "', '" + ngaytao + "', '" + ngaytao + "', '0', " +this.nccId + ")";
				
			 
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi phieu xuat kho: " + query;
					System.out.println("Loi: "  + query);
					db.getConnection().rollback();
					return false;
				}
				
				String xkCurrent = "";
				query = "select IDENT_CURRENT('Erp_XUATKHO') as xkId";
				ResultSet rsPxk = db.get(query);						
				if(rsPxk.next())
				{
					xkCurrent = rsPxk.getString("xkId");
					rsPxk.close();
				}
				
				
				boolean QlKhuvucKho = false;
				query= "select * from ERP_KHOTT WHERE QUANLYBIN = 1 AND PK_SEQ = '"+this.khoId+"'" ;
				
				ResultSet rs = db.get(query);
				try{
				if(rs.next()){
					QlKhuvucKho = true;
				}
				}catch(Exception err){
					this.msg=err.getMessage();
					err.printStackTrace();
				}
				
				query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
				if(QlKhuvucKho){
					boolean tmp = false;
					rs = db.get(query);
					try{
					if(rs.next()){
						tmp = true;
					}
					}catch(Exception err){
						this.msg=err.getMessage();
						err.printStackTrace();
					}
					if(!tmp){
						this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
						QlKhuvucKho = false;
					}
				}
				
				int i=0;
				if(this.spList.size() > 0)
				{
					for(i = 0; i < this.spList.size(); i++)
					{
						ISanpham sp = this.spList.get(i);
						query = "insert ERP_XUATKHO_SANPHAM(XUATKHO_FK, SANPHAM_FK, SOLUONG) " +
								"values('" + xkCurrent + "', '" + sp.getId() + "', '" + sp.getSoluong() + "')";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_XUATKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
		 
						List<ISpDetail> spCon = sp.getSpDetailList();
						for(int j = 0; j < spCon.size(); j++)
						{
							ISpDetail detail = spCon.get(j);
							if(!QlKhuvucKho){
								detail.setKhuId("NULL");
							}
							double soluongct=0;
							try{
								soluongct= Double.parseDouble(detail.getSoluong());
							}catch(Exception er){
								er.printStackTrace();
								this.msg="Số lượng trong lô chi tiết của sản phẩm : "+sp.getTen() +" không hợp lệ";
								db.getConnection().rollback();
								return false;
							}
							if(soluongct >0){
							
									query = " insert ERP_XUATKHO_SP_CHITIET(XUATKHO_FK, SANPHAM_FK, KHUVUCKHO_FK, SOLO, SOLUONG, BEAN,NGAYBATDAU,NGAYSANXUAT,NGAYHETHAN) " +
											" values('" + xkCurrent + "', '" + detail.getId() + "', " + (detail.getKhuId().length()>0?detail.getKhuId():"NULL") + ", N'" + detail.getSolo() + "', '" + detail.getSoluong() + "', 100000,'"+detail.getNgaybatdau()+"','"+detail.getNgaysanxuat()+"','"+detail.getNgayhethan()+"')";
									
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi ERP_XUATKHO_SP_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									// KHO TỔNG ĐÃ BOOKED LÚC TẠO ĐƠN TRẢ HÀNG RỒI, GIỜ CHỈ GIẢM KHO CHI TIẾT.
									double soluong=0;
									double booked=soluongct;
									double available=(-1)* soluongct;
															
									  msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,this.khoId,sp.getId(),soluong, booked,available,0,detail.getSolo(),detail.getVitriId(),detail.getKhuId(),detail.getNgaybatdau());
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
			else  { //XUẤT BÁN CHO KHÁCH HÀNG (HDTC) || XUẤT KHO CHO HÓA ĐƠN TC KHUYẾN MÃI
				String query="";
				String Hoadon_fk="";
				String Dondathang_fk="";
				 if(this.Loaixuatkho.equals("HD")){
					 Hoadon_fk=this.hdtcId;
					 Dondathang_fk="NULL";
					 	query=" SELECT ISNULL(DDH.MAKETOSTOCK,'0')  AS MAKETOSTOCK "+
					  		  " FROM ERP_DONDATHANG DDH INNER JOIN ERP_HOADON_DDH HD ON HD.DDH_FK=DDH.PK_SEQ WHERE HD.HOADON_FK ="+this.hdtcId;
				 }else if(this.Loaixuatkho.equals("DH")){
					 Hoadon_fk="NULL";
					 Dondathang_fk=this.DdhId;
					  query=" SELECT ISNULL(DDH.MAKETOSTOCK,'0')  AS MAKETOSTOCK "+
					  		" FROM ERP_DONDATHANG DDH  WHERE DDH.PK_SEQ ="+this.DdhId;

				 }
				 
				ResultSet Rsch=db.get(query);
				String maketostock="";
				
				if(Rsch.next()){
					maketostock=Rsch.getString("MAKETOSTOCK").trim();
				}
				Rsch.close();
				
				
				
				String ngaytao = this.getDateTime();
					 
				if(this.hdtcId != null && this.hdtcId.length()>0)
				{
					  query = 	    " SELECT HD.KHOTT_FK, KHO.MA FROM ERP_HOADON_SP HD "+  
					  				" INNER JOIN ERP_KHOTT KHO ON  HD.KHOTT_FK = KHO.PK_SEQ   "+
					  				" WHERE HD.HOADON_FK =  "+this.hdtcId+" AND HD.KHOTT_FK= "+this.khoId;
					
					System.out.println("Kiem tra kho xuat: " + query);
					ResultSet rsCheck = db.get(query);
				 
					if(!rsCheck.next())
					{
						db.getConnection().rollback();
						this.msg = "Kho xuất phải là kho đặt hàng của nhà phân phối  khi đặt hàng.  ";
						return false;
					}
					 
				}
				 
				  query = "insert ERP_XUATKHO(NGAYXUAT, NGAYCHOT, HOADON_FK,DONDATHANG_FK, NOIDUNGXUAT, KHO_FK, LYDOXUAT, GHICHU, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI, NPP_FK ) " +
								" values('" + this.ngayxuatkho + "', '" + this.ngayxuatkho + "', " + Hoadon_fk + ","+Dondathang_fk+", '" + this.ndxId + "', '" + this.khoId + "', N'" + this.lydoxuat + "', N'" + this.ghichu + "', " +
								"'" + this.userId + "', '" + this.userId + "', '" + ngaytao + "', '" + ngaytao + "', '0', " +this.nppId + ")";
				
				System.out.println( "insert ERP_XUATKHO: "+query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi phieu xuat kho: " + query;
					System.out.println("Loi: "  + query);
					db.getConnection().rollback();
					return false;
				}
				String xkCurrent = "";
				query = "select IDENT_CURRENT('Erp_XUATKHO') as xkId";
				
				ResultSet rsPxk = db.get(query);						
				if(rsPxk.next())
				{
					xkCurrent = rsPxk.getString("xkId");
					rsPxk.close();
				}
				
				boolean QlKhuvucKho = false;
				query= "select * from ERP_KHOTT WHERE QUANLYBIN = 1 AND PK_SEQ = '"+this.khoId+"'" ;
				
				ResultSet rs = db.get(query);
				try{
				if(rs.next()){
					QlKhuvucKho = true;
				}
				}catch(Exception err){
					this.msg=err.getMessage();
					err.printStackTrace();
				}
				
				query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
				if(QlKhuvucKho){
					boolean tmp = false;
					rs = db.get(query);
					try{
					if(rs.next()){
						tmp = true;
					}
					}catch(Exception err){
						this.msg=err.getMessage();
						err.printStackTrace();
					}
					if(!tmp){
						this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
						QlKhuvucKho = false;
					}
				}
				
				int i=0;
				if(this.spList.size() > 0)
				{
					for(i = 0; i < this.spList.size(); i++)
					{
						ISanpham sp = this.spList.get(i);
						query = "insert ERP_XUATKHO_SANPHAM(XUATKHO_FK, SANPHAM_FK, SOLUONG) " +
								"values('" + xkCurrent + "', '" + sp.getId() + "', '" + sp.getSoluong() + "')";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_XUATKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
		 
						List<ISpDetail> spCon = sp.getSpDetailList();
						for(int j = 0; j < spCon.size(); j++)
						{
							ISpDetail detail = spCon.get(j);
							if(!QlKhuvucKho){
								detail.setKhuId("NULL");
							}
							 
							double soluongct=0;
							try{
								soluongct= Double.parseDouble(detail.getSoluong());
							}catch(Exception er){
								this.msg="Số lượng trong lô chi tiết của sản phẩm : "+sp.getTen() +" không hợp lệ";
								db.getConnection().rollback();
								return false;
							}
							if(soluongct>0){
							
									query = " insert ERP_XUATKHO_SP_CHITIET(XUATKHO_FK, SANPHAM_FK, KHUVUCKHO_FK, SOLO, SOLUONG, BEAN,NGAYBATDAU,NGAYSANXUAT,NGAYHETHAN) " +
											" values('" + xkCurrent + "', '" + detail.getId() + "', " + (detail.getKhuId().length() >0?detail.getKhuId():"NULL" ) + ", " +
											" N'" + detail.getSolo() + "', '" + detail.getSoluong() + "', 100000,'"+detail.getNgaybatdau()+"','"+detail.getNgaysanxuat()+"','"+detail.getNgayhethan()+"')";
									
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi ERP_XUATKHO_SP_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									
									double soluong=0;
									double booked=soluongct;
									double available=(-1)* soluongct;
									
									if(!maketostock.equals("1")){
										  msg1= util_kho.Update_Kho_Sp (this.db,this.khoId,sp.getId(),soluong, booked,available,0);
										if(msg1.length() >0){
											this.msg = msg1;
											this.db.getConnection().rollback();
											return false;
										}
									}
									
								
									 msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,this.khoId,sp.getId(),soluong, booked,available,0,detail.getSolo(),detail.getVitriId(),detail.getKhuId(),detail.getNgaybatdau());
									if(msg1.length() >0){
										this.msg = msg1;
										this.db.getConnection().rollback();
										return false;
									}
							}
	  
						}
						 
					}
				}
				
				if(this.hdtcId != null && this.hdtcId.length()>0)
				{
					query = "update ERP_HOADON set trangthai = '4' where pk_seq = '" + this.hdtcId + "'";
					if(!this.db.update(query))
					{
						this.msg = "1.Khong the cap nhat trang thai Hoa don: " + query;
						db.getConnection().rollback();
						return false;
					}
					
				}
				
				 
			}
				if(this.checkXuatkhovuotHd()){
					db.getConnection().rollback();
					return false;
					
				}
			
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				return true;
			} 
			catch (Exception  e)
			{ 
				db.update("rollback");

				e.printStackTrace();
				this.msg="Loi khi luu phieu XK";
				return false;
			}
		
	}

	private String check_daxuatkho() {
		// TODO Auto-generated method stub
		String msg1="";
		try{
			
		String	query=" SELECT PK_SEQ FROM ERP_XUATKHO WHERE  trangthai <> 2  and DONDATHANG_FK="+this.DdhId+" " +
					  "  AND NOIDUNGXUAT="+this.ndxId+" AND KHO_FK ="+this.khoId+" AND PK_SEQ <> "+(this.id.length()>0?this.id:"0" )+"      AND TRANGTHAI <> 2";
			ResultSet rs=db.get(query);
			
			if(rs.next()){
				msg1=" Đã tồn tại xuất kho số : "+rs.getString("PK_SEQ") +". Cho đơn đặt hàng "+this.DdhId+" theo kho và nội dung xuất bạn đang thực hiện";
			}
			
			rs.close();
			
		 
		}catch(Exception er){
			er.printStackTrace();
			msg1= er.getMessage();
		}
		
		return msg1;
	}


	private boolean checkXuatkhovuotHd() {
		try{
		// TODO Auto-generated method stub
			if(this.trahangNccId!=null && this.trahangNccId.length()>0){
				// trường hợp trả hàng nhà cung cấp
				
			}
			
			boolean bien=false;
			if(hdtcId!=null && hdtcId.length() > 0){
				
		String sql= "  SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD, " +  
					"  ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT       " +  
					"  FROM     " +  
					"  (     " +  
					"  SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     " +  
					"  CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG     " +  
					"  FROM ERP_HOADON_SP HDSP      " +  
					"  INNER JOIN ERP_HOADON HD ON HD.PK_SEQ =HDSP.HOADON_FK       " +  
					"  INNER JOIN ERP_SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ       " +  
					"  LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK    " +  
					"  WHERE HDSP.HOADON_FK =   " +this.hdtcId+   
					"  AND HDSP.SOLUONG > 0     " +  
					"  GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK    " +  
					"   " +  
					"  )     " +  
					"  HOA_DON LEFT JOIN     " +  
					"  (     " +  
					"  SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT     " +  
					"  FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ     " +  
					"  WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.HOADON_FK IN ( "+this.hdtcId+")      " +  
					"  GROUP BY A.SANPHAM_FK     " +  
					"  ) XUATKHO on XUATKHO.SANPHAM_FK=HOA_DON.SPID " +  
					"  where XUATKHO.TOTALXUAT >HOA_DON.SOLUONG ";
			ResultSet rs=db.get(sql);
			
			if(rs.next()){
				bien=true;
				this.msg="Tổng số lượng xuất trong các phiếu xuất kho đã vượt số lượng trong hóa đơn, bạn không thể lưu phiếu này. ";
			}
			rs.close();
			
			}else if(this.DdhId!=null && this.DdhId.length() >0){
				String sql="";
				if(!this.ndxId.equals("100008")){
					// xuất hàng bán
					sql=" SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD "+
					   "  FROM     " +  
					   "  (     " +  
					   "  SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     " +  
					   "  CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG     " +  
					   "  FROM ERP_DONDATHANG_SP HDSP      " +  
					   "  INNER JOIN ERP_DONDATHANG HD ON HD.PK_SEQ =HDSP.DONDATHANG_FK       " +  
					   "  INNER JOIN ERP_SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ       " +  
					   "  LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK    " +  
					   "  WHERE HDSP.DONDATHANG_FK =  "+this.DdhId+"   " +  
					   "  AND HDSP.SOLUONG > 0 AND HDSP.KHOTT_FK = "+this.khoId+"  " +  
					   "  GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK    " +  
					   "   " +  
					   "  )     " +  
					   "  HOA_DON LEFT JOIN     " +  
					   "  (     " +  
					   "  SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT     " +  
					   "  FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ     " +  
					   "  WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.DONDATHANG_FK IN ("+DdhId+" ) AND B.NOIDUNGXUAT="+this.ndxId+"   " +  
					   "  GROUP BY A.SANPHAM_FK     " +  
					   "  )     " +  
					   "  XUATKHO ON HOA_DON.SPID = XUATKHO.SANPHAM_FK     " +  
					   
					   "  where  XUATKHO.TOTALXUAT <> ISNULL( HOA_DON.SOLUONG,0) ";
					
				}else if(this.ndxId.equals("100008")){
					// Hàng khuyên mãi
					sql=" SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD "+
					   "  FROM     " +  
					   "  (     " +  
					 
					   "  SELECT SP.PK_SEQ AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     " +
					   "  CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG " +    
					   "  FROM ERP_DONDATHANG_CTKM_TRAKM HDSP  " +    
					   "  INNER JOIN ERP_DONDATHANG HD ON HD.PK_SEQ =HDSP.DONDATHANGID " +      
					   "  INNER JOIN ERP_SANPHAM SP ON HDSP.SPMA = SP.MA  " +    
					   "  LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK= SP.PK_SEQ  " +   
					   "  WHERE HDSP.DONDATHANGID =  " +this.DdhId+ 
					   "  AND HDSP.SOLUONG > 0 AND HDSP.KHOTT_FK = " +this.khoId +
					   "  GROUP BY SP.PK_SEQ, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK       " +  
					   " union all "+
					   "  SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     " +  
					   "  CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG     " +  
					   "  FROM ERP_DONDATHANG_SP HDSP      " +  
					   "  INNER JOIN ERP_DONDATHANG HD ON HD.PK_SEQ =HDSP.DONDATHANG_FK       " +  
					   "  INNER JOIN ERP_SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ       " +  
					   "  LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK    " +  
					   "  WHERE HDSP.DONDATHANG_FK =  "+this.DdhId+"   " +  
					   "  AND HDSP.SOLUONG > 0 AND HDSP.KHOTT_FK = "+this.khoId+"  AND HD.LOAIDONHANG='6' " +  
					   "  GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK    " +  
					   
					   "   " +  
					   "  )     " +  
					   "  HOA_DON LEFT JOIN     " +  
					   "  (     " +  
					   "  SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT     " +  
					   "  FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ     " +  
					   "  WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.DONDATHANG_FK IN ("+DdhId+" ) AND B.NOIDUNGXUAT="+this.ndxId+"  " +  
					   "  GROUP BY A.SANPHAM_FK     " +  
					   "  )     " +  
					   "  XUATKHO ON HOA_DON.SPID = XUATKHO.SANPHAM_FK     " +  
					   
					   "  where XUATKHO.TOTALXUAT <> ISNULL(HOA_DON.SOLUONG,0) ";
					
				}
				ResultSet rs=db.get(sql);
				
				if(rs.next()){
					bien=true;
					 if(this.DdhId!=null && this.DdhId.length() >0){
						 this.msg="Số lượng xuất trong phiếu xuất kho phải bằng số lượng bên đơn bán hàng.";
					 }else{
						 this.msg="Tổng số lượng xuất trong các phiếu xuất kho đã vượt số lượng trong đơn hàng, bạn không thể lưu phiếu này. ";
					 }
				}
			}
			return bien;
			
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
			return true;
		}
	}


	public boolean updatePxk() 
	{
			String msg1=util.checkNgayHopLe(this.db, this.ngayxuatkho);
			if(msg1.length() > 0)
			{
				this.msg = msg1;
				return false;
			}
		
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
					List<ISpDetail> spDetail = sp.getSpDetailList();
					
					double sum = 0;
					for(int j = 0; j < spDetail.size(); j++)
					{
						sum += Double.parseDouble(spDetail.get(j).getSoluong());
					}
					
					if(sum != Double.parseDouble(sp.getSoluong()))
					{
						this.msg += " + Sản phẩm " + sp.getMa() + " - " + sp.getDiengiai() + ", không đủ số lượng để xuất kho, vui lòng kiểm tra lại \n";
					}
				}
			}
			
			if(this.msg.length() > 0)
			{
				this.msg = "Vui lòng kiểm tra lại các thông tin sau trước khi xuất kho: \n" + this.msg;
				System.out.println(this.msg);
				return false;
			}
	 
		else
		{
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong đơn mua hàng trước khi xuất kho: \n" + this.msg;
				return false;
			}
		}
			if(this.Loaixuatkho.equals("DH") && this.DdhId.length() >0){
				  msg1=this.check_daxuatkho();
					if(msg1.length() >0){
						this.msg=msg1;
						return false;
					}
			}
		try 
		{
			db.getConnection().setAutoCommit(false);
			if(this.ndxId.trim().length()>0&& this.ndxId.equals("100007")){//XUẤT KHO CHO ĐƠN TRẢ HÀNG NCC
				String query = 	"";
				if(this.trahangNccId != null && this.trahangNccId.length()>0){
					query = " SELECT a.KHOTT_FK,KHO.MA \n" +
							" FROM 	 ERP_MUAHANG_SP a  \n"+  
			  				" INNER JOIN ERP_KHOTT KHO ON  a.KHOTT_FK = KHO.PK_SEQ   \n"+
			  				" WHERE  a.MUAHANG_FK =  "+this.trahangNccId+" AND a.KHOTT_FK= "+this.khoId;
 
					ResultSet rsCheck = db.get(query);
					if(!rsCheck.next())
					{
						db.getConnection().rollback();
						this.msg = "Kho xuất phải là kho trả của nhà phân phối khi làm đơn trả hàng.  ";
						return false;
					}
					
				}
				else
				{	 
					this.msg = "Không có đơn trả hàng NCC nào được chọn";
					return false;
				}
				
//				String ngaytao = this.getDateTime();
				
				boolean QlKhuvucKho = false;
				query= "select * from ERP_KHOTT WHERE QUANLYBIN = 1 AND PK_SEQ = '"+this.khoId+"'" ;
				
				ResultSet rs = db.get(query);
				try{
				if(rs.next()){
					QlKhuvucKho = true;
				}
				}catch(Exception err){
					this.msg=err.getMessage();
					err.printStackTrace();
				}
				
				query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
				
				if(QlKhuvucKho){
					boolean tmp = false;
					rs = db.get(query);
					try{
						if(rs.next()){
							tmp = true;
						}
					}catch(Exception err){
						this.msg=err.getMessage();
						err.printStackTrace();
					}
					if(!tmp){
						this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
						QlKhuvucKho = false;
					}
				}
				
				
				//TRẢ LẠI BOOK
				query = " SELECT xk.npp_fk, xk.KHO_FK, a.SANPHAM_FK, a.SOLO, a.SOLUONG, a.KHUVUCKHO_FK ,A.NGAYBATDAU  "+
						" FROM 	 ERP_XUATKHO_SP_CHITIET a " +
						" 		 INNER JOIN ERP_XUATKHO xk  on xk.PK_SEQ=a.XUATKHO_FK "+
						" WHERE  xk.PK_SEQ = " + this.id;

				rs	=	db.get(query);

				while (rs.next())
				{				
					String khuvuc_old = rs.getString("KHUVUCKHO_FK");
					String spid_old = rs.getString("SANPHAM_FK");
					String idkhott_old = rs.getString("KHO_FK");
					String ngaybatdau= rs.getString("NGAYBATDAU");
					String solo_old = rs.getString("SOLO");
 
					double soluongct= rs.getDouble("SOLUONG");
					double soluong=0;
					double booked=(-1)*soluongct;
					double available= soluongct;

					  msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,idkhott_old,spid_old,soluong, booked,available,0,solo_old,"",khuvuc_old,ngaybatdau);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
				
				//CẬP NHẬT LẠI THÔNG TIN
				query = 
				" UPDATE ERP_XUATKHO set NGAYXUAT = '" + this.ngayxuatkho + "', " +
				" NGAYCHOT = '" + this.ngaychotNV +"', " +
				" TRAHANGNCC_FK = " + this.trahangNccId + ", NOIDUNGXUAT = '" + this.ndxId + "', " +
				" LYDOXUAT = N'" + this.lydoxuat + "', GHICHU = N'" + this.ghichu + "', NGUOISUA = '" + this.userId + "', " +
				" NGAYSUA = '" + this.getDateTime() + "', TRANGTHAI = '0', " +
				" NPP_FK = '"+this.nccId+"'"+
				" WHERE pk_seq = '" + this.id + "'";
	 				 
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật phiếu xuất kho : " + query;
					db.getConnection().rollback();
					return false;
				}
				
				//XÓA 
				db.update("delete ERP_XUATKHO_SANPHAM where xuatkho_fk = '" + this.id + "'");
				db.update("delete ERP_XUATKHO_SP_CHITIET where xuatkho_fk = '" + this.id + "'");
 
				int i=0;
				if(this.spList.size() > 0)
				{
					for(i = 0; i < this.spList.size(); i++)
					{
						ISanpham sp = this.spList.get(i);
						query = "insert ERP_XUATKHO_SANPHAM(XUATKHO_FK, SANPHAM_FK, SOLUONG) " +
								"values('" + this.id + "', '" + sp.getId() + "', '" + sp.getSoluong() + "')";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_XUATKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
		 
						List<ISpDetail> spCon = sp.getSpDetailList();
						for(int j = 0; j < spCon.size(); j++)
						{
							ISpDetail detail = spCon.get(j);
							if(!QlKhuvucKho){
								detail.setKhuId("NULL");
							}
							double soluongct=0;
							try{
								soluongct= Double.parseDouble(detail.getSoluong());
							}catch(Exception er){
								this.msg="Số lượng trong lô chi tiết của sản phẩm : "+sp.getTen() +" không hợp lệ";
								db.getConnection().rollback();
								return false;
							}
							if(soluongct >0){
									query = " insert ERP_XUATKHO_SP_CHITIET(XUATKHO_FK, SANPHAM_FK, KHUVUCKHO_FK, SOLO, SOLUONG, BEAN,NGAYBATDAU,NGAYSANXUAT,NGAYHETHAN) " +
											" values('" + this.id + "', '" + detail.getId() + "', " + (detail.getKhuId().length()>0?detail.getKhuId():"NULL") + ", N'" + detail.getSolo() + "', '" + detail.getSoluong() + "', 100000,'"+detail.getNgaybatdau()+"','"+detail.getNgaysanxuat()+"','"+detail.getNgayhethan()+"')";
									
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi ERP_XUATKHO_SP_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									
									double soluong=0;
									double booked=soluongct;
									double available=(-1)* soluongct;
															
									  msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,this.khoId,sp.getId(),soluong, booked,available,0,detail.getSolo(),detail.getVitriId(),detail.getKhuId(),detail.getNgaybatdau());
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
			else{//XUẤT BÁN CHO KH (HĐTC) - XUẤT KHO CHO HĐKM
				
				
				String query="";
				String Hoadon_fk="";
				String Dondathang_fk="";
				 if(this.Loaixuatkho.equals("HD")){
					 Hoadon_fk=this.hdtcId;
					 Dondathang_fk="NULL";
				  query=" SELECT ISNULL(DDH.MAKETOSTOCK,'0')  AS MAKETOSTOCK "+
					  		 " FROM ERP_DONDATHANG DDH INNER JOIN ERP_HOADON_DDH HD ON HD.DDH_FK=DDH.PK_SEQ WHERE HD.HOADON_FK ="+this.hdtcId;
				 }else if(this.Loaixuatkho.equals("DH")){
					 Hoadon_fk="NULL";
					 Dondathang_fk=this.DdhId;
					  query=" SELECT ISNULL(DDH.MAKETOSTOCK,'0')  AS MAKETOSTOCK "+
				  		 " FROM ERP_DONDATHANG DDH  WHERE DDH.PK_SEQ ="+this.DdhId;

				 }
				
				
				ResultSet Rsch=db.get(query);
				String maketostock="";
				
				if(Rsch.next()){
					maketostock=Rsch.getString("MAKETOSTOCK").trim();
				}
				Rsch.close();
			
				if(this.hdtcId != null && this.hdtcId.length()>0)
				{
					query=" SELECT PK_SEQ, TEN, DIACHI, QUANLYBIN FROM ERP_KHOTT WHERE PK_SEQ IN "+
						  " ( SELECT  KHOTT_FK FROM ERP_HOADON_SP WHERE HOADON_FK ="+this.hdtcId+") and pk_seq="+this.khoId;
					
					ResultSet rsCheck = db.get(query);
					  
					if(!rsCheck.next())
					{
	
						db.getConnection().rollback();
						this.msg = "Kho xuất phải là kho  khi đặt hàng.";
						return false;	 
					}
					rsCheck.close();
					 
				}
				 
			
				this.ngaychotNV = this.ngayxuatkho;
			
				boolean QlKhuvucKho = false;
				query = "select * from ERP_KHOTT WHERE QUANLYBIN = 1 AND PK_SEQ = '"+this.khoId+"'" ;
			
				ResultSet rs = db.get(query);
				try{
				if(rs.next()){
					QlKhuvucKho = true;
				}
				}catch(Exception err){
					this.msg=err.getMessage();
					err.printStackTrace();
				}
				query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
				
				if(QlKhuvucKho){
					boolean tmp = false;
					rs = db.get(query);
					try{
					if(rs.next()){
						tmp = true;
					}
					}catch(Exception err){
						this.msg=err.getMessage();
						err.printStackTrace();
					}
					if(!tmp){
						this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
						QlKhuvucKho = false;
					}
				}
						
				query=" SELECT ISNULL(DDH.MAKETOSTOCK,'0')  AS MAKETOSTOCK "+
					" FROM ERP_DONDATHANG DDH  "+
					" INNER JOIN ERP_HOADON_DDH HD ON HD.DDH_FK=DDH.PK_SEQ "+
					" WHERE  HD.HOADON_FK = (SELECT HOADON_FK FROM ERP_XUATKHO XK WHERE XK.PK_SEQ="+this.id+") ";
				if(this.Loaixuatkho.equals("DH")){
					query=  "SELECT ISNULL(DDH.MAKETOSTOCK,'0')  AS MAKETOSTOCK "+
							" FROM ERP_DONDATHANG DDH WHERE PK_SEQ= (SELECT DONDATHANG_FK FROM ERP_XUATKHO XK WHERE XK.PK_SEQ="+this.id+")";
				}
				ResultSet rsck=db.get(query);
				String maketostock_old="";
				if(rsck.next()){
					maketostock_old=rsck.getString("MAKETOSTOCK");
				}
				rsck.close();
			
				query	=	" select xk.npp_fk, xk.KHO_FK, a.SANPHAM_FK, a.SOLO, a.SOLUONG, a.KHUVUCKHO_FK ,A.NGAYBATDAU  "+
							" from ERP_XUATKHO_SP_CHITIET a " +
							" inner join ERP_XUATKHO xk  on xk.PK_SEQ=a.XUATKHO_FK "+
							" where xk.PK_SEQ = " + this.id;
			
				rs	=	db.get(query);
				
				while (rs.next()){				
					String khuvuc_old = rs.getString("KHUVUCKHO_FK");
					String spid_old = rs.getString("SANPHAM_FK");
					String idkhott_old = rs.getString("KHO_FK");
					String ngaybatdau= rs.getString("NGAYBATDAU");
					//String npp_fk_old = rs.getString("npp_fk");
					//String nhanvien_fk_old = rs.getString("nhanvien_fk");
					String solo_old = rs.getString("SOLO");
					 
					double soluongct= rs.getDouble("SOLUONG");
					double soluong=0;
					double booked=(-1)*soluongct;
					double available= soluongct;
					
					if(!maketostock_old.equals("1")){
						  msg1= util_kho.Update_Kho_Sp (this.db,idkhott_old,spid_old,soluong, booked,available,0);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}
					}
			 
					  msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,idkhott_old,spid_old,soluong, booked,available,0,solo_old,"",khuvuc_old,ngaybatdau);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
	 
				this.trahangNccId = "NULL";
				query = " update ERP_XUATKHO set NGAYXUAT = '" + this.ngayxuatkho + "', HOADON_FK="+Hoadon_fk+",DONDATHANG_FK="+Dondathang_fk+" , " +
						" NGAYCHOT = '" + this.ngaychotNV +"', " +
						" TRAHANGNCC_FK = " + this.trahangNccId + ", NOIDUNGXUAT = '" + this.ndxId + "', " +
						" LYDOXUAT = N'" + this.lydoxuat + "', GHICHU = N'" + this.ghichu + "', NGUOISUA = '" + this.userId + "', " +
						" NGAYSUA = '" + this.getDateTime() + "', TRANGTHAI = '0' " +
						" where pk_seq = '" + this.id + "'";
			
				System.out.println(query);
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật phiếu xuất kho : " + query;
					db.getConnection().rollback();
					return false;
				}
			
				db.update(" delete ERP_XUATKHO_SANPHAM where xuatkho_fk = '" + this.id + "'");
				db.update(" delete ERP_XUATKHO_SP_CHITIET where xuatkho_fk = '" + this.id + "'");
				
				int i = 0;
				if(this.spList.size() > 0)
				{
					for(  i = 0; i < this.spList.size(); i++)
					{
						ISanpham sp = this.spList.get(i);
						query = "insert ERP_XUATKHO_SANPHAM(XUATKHO_FK, SANPHAM_FK, SOLUONG) " +
								"values('" + this.id + "', '" + sp.getId() + "', '" + sp.getSoluong() + "')";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_NHAPKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						else
						{
							 
							List<ISpDetail> spCon = sp.getSpDetailList();
							for(int j = 0; j < spCon.size(); j++)
							{
								
								ISpDetail detail = spCon.get(j);
								if(!QlKhuvucKho){
									detail.setKhuId("NULL");
								}
								double soluongct=0;
								try{
									soluongct= Double.parseDouble(detail.getSoluong());
								}catch(Exception er){
									this.msg="Số lượng trong lô chi tiết của sản phẩm : "+sp.getTen() +" không hợp lệ";
									db.getConnection().rollback();
									return false;
								}
								if(soluongct >0){
								
									query = " insert ERP_XUATKHO_SP_CHITIET(XUATKHO_FK, SANPHAM_FK, KHUVUCKHO_FK, SOLO, SOLUONG, BEAN,NGAYBATDAU,NGAYSANXUAT,NGAYHETHAN) " +
									" values('" + this.id + "', '" + detail.getId() + "', " +(detail.getKhuId().length()>0?detail.getKhuId():"NULL")+ ", N'" + detail.getSolo() + "', '" + detail.getSoluong() + "', 100000,'"+detail.getNgaybatdau()+"','"+detail.getNgaysanxuat()+"','"+detail.getNgayhethan()+"')";
					
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi ERP_XUATKHO_SP_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
								
									double soluong=0;
									double booked=soluongct;
									double available=(-1)* soluongct;
									
									if(!maketostock.equals("1")){
									
										msg1= util_kho.Update_Kho_Sp (this.db,this.khoId,sp.getId(),soluong, booked,available,0);
										if(msg1.length() >0){
											this.msg = msg1;
											this.db.getConnection().rollback();
											return false;
										}
									}
							  
								 	msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,this.khoId,sp.getId(),soluong, booked,available,0,detail.getSolo(),detail.getVitriId(),detail.getKhuId(),detail.getNgaybatdau());
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
			 
			if(this.checkXuatkhovuotHd()){
				db.getConnection().rollback();
				return false;
				
			}	
			
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				return true;
		} 
		catch (Exception  e)
		{ 
			e.printStackTrace();
			db.update("rollback");
			this.msg = e.getMessage();
			return false;
		}
	}
	
	public void createRs()
	{
		String kbhId = "";
		String query ;
		
		 
		query = "SELECT PK_SEQ, MA, TEN, 0 AS CHON FROM ERP_KHACHHANG " ;			
 
	 
		if(kbhId.length() > 0){
			query = query + " where KenhBanHang_Fk = " + kbhId + " ";
		}
		
		query += "order by TEN asc";
	 
		this.nppRs = this.db.get(query);
		

		query = "select PK_SEQ as nccId, ma + ', ' + ten as nccTen from ERP_NHACUNGCAP where trangthai = '1' and duyet = '1' order by ma asc";
		//System.out.println("Khoi tao NCC: " + query);
		this.nccRs = this.db.get(query);
		
		this.ndxRs = db.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('XK') and PK_SEQ != 100029 ");
		
		if(this.Loaixuatkho.equals("HD")){
		
		if(this.hdtcId.length() >0){
 
			query=	" select PK_SEQ, TEN, DIACHI, QUANLYBIN from ERP_KHOTT where pk_seq in " +
					" ( SELECT  KHOTT_FK FROM ERP_HOADON_SP where hoadon_fk ="+this.hdtcId+" ) and  pk_seq in (select khott_fk from NHANVIEN_KHOTT where NHANVIEN_FK="+this.userId+") ";
 
			this.khoRs = db.get(query);
			if(this.khoId.equals("")){
				ResultSet rskho=db.get(query);
				try{
					if(rskho.next()){
						this.khoId=rskho.getString("PK_SEQ");
					}
					rskho.close();
				}catch(Exception er){
					
				}
			}
			
		}
		
		if(nppId.length() > 0  ){
			//query = "select PK_SEQ, SOHOADON from ERP_HOADON where TRANGTHAI = '1' and NPP_FK = '"+this.nppId+"' " + 
				//"and PK_SEQ not in (select HOADON_FK FROM ERP_XUATKHO)";
			if(this.ndxId.equals("100008"))
				query = "select PK_SEQ, SOHOADON from ERP_HOADON where TRANGTHAI in ( '1','4') and KHACHHANG_FK = '"+this.nppId+"' " + 
				"  AND LOAIHOADON = 1";
			else if(this.ndxId.equals("100002"))
				query = "select PK_SEQ, SOHOADON from ERP_HOADON where TRANGTHAI in  ( '1','4') and KHACHHANG_FK = '"+this.nppId+"' " + 
				"   AND LOAIHOADON = 0";
			this.hdtcList = db.get(query);
	 
		}
		}else if(this.Loaixuatkho.equals("DH")){
			
			
			if(this.DdhId.length() >0){
				 
				query=	" select PK_SEQ, TEN, DIACHI, QUANLYBIN from ERP_KHOTT where pk_seq in " +
						" ( SELECT  KHOTT_FK FROM ERP_DONDATHANG_SP where DONDATHANG_FK ="+this.DdhId+" ) and  pk_seq in (select khott_fk from NHANVIEN_KHOTT where NHANVIEN_FK="+this.userId+") ";
	 
				this.khoRs = db.get(query);
				if(this.khoId.equals("")){
					ResultSet rskho=db.get(query);
					try{
						if(rskho.next()){
							this.khoId=rskho.getString("PK_SEQ");
						}
						rskho.close();
					}catch(Exception er){
						
					}
				}
				
			}
			
			if(nppId.length() > 0  ){
				//query = "select PK_SEQ, SOHOADON from ERP_HOADON where TRANGTHAI = '1' and NPP_FK = '"+this.nppId+"' " + 
					//"and PK_SEQ not in (select HOADON_FK FROM ERP_XUATKHO)";
				if(this.ndxId.equals("100002")){
					//IS_HOANTATXK =0 chưa hoàn tất xuất kho (trong trường hợp có khuyến mãi ),đơn hàng có thể xuất hàng bán nhưng chưa xuất khuyến mãi
					
					query = " SELECT  PK_SEQ,  A.PREFIX + CAST(PK_SEQ AS NVARCHAR(10)) + ' _ ' + NGAYDAT AS SOHOADON  " +
							" from ERP_DONDATHANG  A where TRANGTHAI >=4 and isnull(IS_HOANTATXK,'0')='0'  " +
							"  and KHACHHANG_FK = '"+this.nppId+"' AND NGAYDAT >='2015-04-01' and LOAIDONHANG = 1   and a.trangthai <>7" ; 
				}
				
				else if(this.ndxId.equals("100008")){
					
					query = " SELECT distinct  A.PK_SEQ,  A.PREFIX + CAST(A.PK_SEQ AS NVARCHAR(10)) + ' _ ' + NGAYDAT AS SOHOADON  from ERP_DONDATHANG A "+
							" INNER JOIN ERP_DONDATHANG_CTKM_TRAKM DH_KM ON DH_KM.DONDATHANGID=A.PK_SEQ  "+
							" where TRANGTHAI >=4 and isnull(IS_HOANTATXK,'0')='0'  and KHACHHANG_FK ="+this.nppId+" AND DH_KM.SPMA IS NOT NULL  AND NGAYDAT >='2015-04-01'  and a.trangthai <>7 " +
							" union all " +
							" SELECT  PK_SEQ,  A.PREFIX + CAST(PK_SEQ AS NVARCHAR(10)) + ' _ ' + NGAYDAT AS SOHOADON  " +
							" from ERP_DONDATHANG  A where TRANGTHAI >=4 and isnull(IS_HOANTATXK,'0')='0'  " +
							" and KHACHHANG_FK = '"+this.nppId+"' AND NGAYDAT >='2015-04-01' and LOAIDONHANG = 6 and a.trangthai <>7 " ; 
				}else if (this.ndxId.equals("100027")){
					query = " SELECT  PK_SEQ,  A.PREFIX + CAST(PK_SEQ AS NVARCHAR(10)) + ' _ ' + NGAYDAT AS SOHOADON  " +
							" from ERP_DONDATHANG  A where TRANGTHAI >=4 and isnull(IS_HOANTATXK,'0')='0'  " +
							"  and KHACHHANG_FK = '"+this.nppId+"' AND NGAYDAT >='2015-04-01' and LOAIDONHANG = 2   and a.trangthai <>7" ; 
				}else if(this.ndxId.equals("100028")){
					query = " SELECT  PK_SEQ,  A.PREFIX + CAST(PK_SEQ AS NVARCHAR(10)) + ' _ ' + NGAYDAT AS SOHOADON  " +
							" from ERP_DONDATHANG  A where TRANGTHAI >=4 and isnull(IS_HOANTATXK,'0')='0'  " +
							"  and KHACHHANG_FK = '"+this.nppId+"' AND NGAYDAT >='2015-04-01' and LOAIDONHANG = 3   and a.trangthai <>7" ; 
				}else if(this.ndxId.equals("100029")){
					query = " SELECT  PK_SEQ,  A.PREFIX + CAST(PK_SEQ AS NVARCHAR(10)) + ' _ ' + NGAYDAT AS SOHOADON  " +
							" from ERP_DONDATHANG  A where TRANGTHAI >=4 and isnull(IS_HOANTATXK,'0')='0'  " +
							"  and KHACHHANG_FK = '"+this.nppId+"' AND NGAYDAT >='2015-04-01' and LOAIDONHANG = 4   and a.trangthai <>7" ; 
				}
				this.RsDondathang = db.get(query);
				
			}
			
		}
 
		if(this.nccId!=null&& this.nccId.length()>0)
		{
			query = "select PK_SEQ as dmhId, cast(PK_SEQ as nvarchar(10)) + ' _ ngay tra ' + NGAYMUA as dmhTen " +
					"from ERP_MUAHANG where TYPE = '2' and TRANGTHAI = '1' and NHACUNGCAP_FK = '" + this.nccId + "'";
	
			if(this.id!=null&& this.id.length()>0)
				query += " and PK_SEQ not in (select trahangncc_fk from ERP_XUATKHO where trahangncc_fk is not null and TRANGTHAI in (0, 1) and pk_seq != '" + this.id + "' )";
			else
				query += " and PK_SEQ not in (select trahangncc_fk from ERP_XUATKHO where trahangncc_fk is not null and TRANGTHAI in (0, 1))";
						
			this.trahangNccRs = db.get(query);
			
			System.out.println("query:"+query);
			
			if(this.trahangNccId.length()>0){
				query = "select distinct a.PK_SEQ, a.TEN from ERP_KHOTT a inner join erp_muahang_sp b on a.PK_SEQ = b.KHOTT_FK where b.muahang_fk ="+this.trahangNccId+"";
				this.khoRs = db.get(query);
			}
				
		}
		this.IsKhoXuatQL_Khuvuc=util_kho.getIsQuanLyKhuVuc(this.khoId, this.db);
 
		System.out.println("trahangNCC:"+this.trahangNccId +"Khoa id:"+this.khoId+"this.ndxId:"+this.ndxId+"this.spList.size()"+this.spList.size());
		
		if( ((this.trahangNccId!=null&&this.trahangNccId.length()>0)) && (this.ndxId!=null&&this.ndxId.length()>0) && (this.khoId!=null&&this.khoId.length()>0) && this.spList.size() <= 0)
		{
			this.createSanpham();
			System.out.println("vao day Tram pa pa");
		}
		
		if(this.Loaixuatkho.equals("HD")){
			if( (this.hdtcId!=null && this.hdtcId.length()>0)  && (this.ndxId!=null&&this.ndxId.length()>0) && (this.khoId!=null&&this.khoId.length()>0) && this.spList.size() <= 0)
			{
				this.createSanpham();
			}
		}else if(this.Loaixuatkho.equals("DH")) {
			if( (this.DdhId!=null && this.DdhId.length()>0)  && (this.ndxId!=null&&this.ndxId.length()>0) && (this.khoId!=null&&this.khoId.length()>0) && this.spList.size() <= 0)
			{
				this.createSanpham_ddh();
			}
		}
	}

	private void createSanpham_ddh() {
		try{
			 
			String query = "";
			boolean dasualo = false;
			boolean QlKhuvucKho = false;
			query= "SELECT * FROM ERP_XUATKHO_SP_CHITIET WHERE XUATKHO_FK="+(this.id.length() >0?this.id:"0") ;
			ResultSet rs=db.get(query);
			try{
			if(rs.next()){
				dasualo=true;
			}
			}catch(Exception err){
				this.msg=err.getMessage();
				err.printStackTrace();
			}
			
			query= "select * from ERP_KHOTT WHERE QUANLYBIN = 1 AND PK_SEQ = '"+this.khoId+"'" ;
			
			rs = db.get(query);
			try{
			if(rs.next()){
				QlKhuvucKho = true;
			}
			}catch(Exception err){
				this.msg=err.getMessage();
				err.printStackTrace();
			}
			
			query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
			if(QlKhuvucKho){
				boolean tmp = false;
				rs = db.get(query);
				try{
				if(rs.next()){
					tmp = true;
				}
				}catch(Exception err){
					this.msg=err.getMessage();
					err.printStackTrace();
				}
				if(!tmp){
					this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
					QlKhuvucKho = false;
				}
			}
			 
			if(this.ngayxuatkho.length()==0){
				query="select NGAYDAT from ERP_DONDATHANG where PK_SEQ="+this.DdhId;
				 rs=db.get(query);
				if(rs.next()){
					this.ngayxuatkho=rs.getString("NGAYDAT");
				}
				rs.close();
			}
			
			
			
			if(this.DdhId != null && this.DdhId.length() > 0 && this.khoId.length() > 0){
 
				if(!this.ndxId.equals("100008")){
					// xuất hàng bán
					query=" SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD, ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT,       " +  
					   "  ISNULL(THUCTEXUAT.TOTALXUAT, 0) AS THUCTEXUAT     " +  
					   "  FROM     " +  
					   "  (     " +  
					   "  SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     " +  
					   "  CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG     " +  
					   "  FROM ERP_DONDATHANG_SP HDSP      " +  
					   "  INNER JOIN ERP_DONDATHANG HD ON HD.PK_SEQ =HDSP.DONDATHANG_FK       " +  
					   "  INNER JOIN ERP_SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ       " +  
					   "  LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK    " +  
					   "  WHERE HDSP.DONDATHANG_FK =  "+this.DdhId+"   " +  
					   "  AND HDSP.SOLUONG > 0 AND HDSP.KHOTT_FK = "+this.khoId+"  " +  
					   "  GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK    " +  
					   "   " +  
					   "  )     " +  
					   "  HOA_DON LEFT JOIN     " +  
					   "  (     " +  
					   "  SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT     " +  
					   "  FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ     " +  
					   "  WHERE B.TRANGTHAI IN (0, 3,1,4,5) AND B.DONDATHANG_FK IN ("+DdhId+" ) AND B.NOIDUNGXUAT="+this.ndxId+" AND A.XUATKHO_FK != "+(this.id.length() >0 ?this.id:"0")+"  " +  
					   "  GROUP BY A.SANPHAM_FK     " +  
					   "  )     " +  
					   "  XUATKHO ON HOA_DON.SPID = XUATKHO.SANPHAM_FK     " +  
					   "  LEFT JOIN     " +  
					   "  (     " +  
					   "  SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT     " +  
					   "  FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ     " +  
					   "  WHERE B.TRANGTHAI IN (0, 3,1,4,5) AND B.DONDATHANG_FK IN ( "+DdhId+"  ) AND B.NOIDUNGXUAT="+this.ndxId+" AND A.XUATKHO_FK  = "+(this.id.length() >0 ?this.id:"0")+"     " +  
					   "  GROUP BY A.SANPHAM_FK     " +  
					   "  ) THUCTEXUAT ON THUCTEXUAT.SANPHAM_FK=HOA_DON.SPID  " +  
					   "  where  HOA_DON.SOLUONG - ISNULL(XUATKHO.TOTALXUAT,0)  > 0";
					
					
				}else if(this.ndxId.equals("100008")){
					// Hàng khuyên mãi
					query=" SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD, ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT,       " +  
					   "  ISNULL(THUCTEXUAT.TOTALXUAT, 0) AS THUCTEXUAT     " +  
					   "  FROM     " +  
					   "  (     " +  
					   "  SELECT SP.PK_SEQ AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     " +
					   "  CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG " +    
					   "  FROM ERP_DONDATHANG_CTKM_TRAKM HDSP  " +    
					   "  INNER JOIN ERP_DONDATHANG HD ON HD.PK_SEQ =HDSP.DONDATHANGID " +      
					   "  INNER JOIN ERP_SANPHAM SP ON HDSP.SPMA = SP.MA  " +    
					   "  LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK= SP.PK_SEQ  " +   
					   "  WHERE HDSP.DONDATHANGID =  " +this.DdhId+ 
					   "  AND HDSP.SOLUONG > 0 AND HDSP.KHOTT_FK = " +this.khoId +
					   "  GROUP BY SP.PK_SEQ, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK       " +  
					   " union all "+
					   "  SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     " +  
					   "  CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG     " +  
					   "  FROM ERP_DONDATHANG_SP HDSP      " +  
					   "  INNER JOIN ERP_DONDATHANG HD ON HD.PK_SEQ =HDSP.DONDATHANG_FK       " +  
					   "  INNER JOIN ERP_SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ       " +  
					   "  LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK    " +  
					   "  WHERE HDSP.DONDATHANG_FK =  "+this.DdhId+"   " +  
					   "  AND HDSP.SOLUONG > 0 AND HDSP.KHOTT_FK = "+this.khoId+"  AND  HD.LOAIDONHANG='6'  " +  
					   "  GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK    " +  
					   "   " +  
					   "  )     " +  
					   "  HOA_DON LEFT JOIN     " +  
					   "  (     " +  
					   "  SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT     " +  
					   "  FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ     " +  
					   "  WHERE B.TRANGTHAI IN (0, 3,1,4,5) AND B.DONDATHANG_FK IN ("+DdhId+" ) AND B.NOIDUNGXUAT="+this.ndxId+" AND A.XUATKHO_FK != "+(this.id.length() >0 ?this.id:"0")+"  " +  
					   "  GROUP BY A.SANPHAM_FK     " +  
					   "  )     " +  
					   "  XUATKHO ON HOA_DON.SPID = XUATKHO.SANPHAM_FK     " +  
					   "  LEFT JOIN     " +  
					   "  (     " +  
					   "  SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT     " +  
					   "  FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ     " +  
					   "  WHERE B.TRANGTHAI IN (0, 3,1,4,5) AND B.DONDATHANG_FK IN ( "+DdhId+"  ) AND B.NOIDUNGXUAT="+this.ndxId+"  AND A.XUATKHO_FK  = "+(this.id.length() >0 ?this.id:"0")+"     " +  
					   "  GROUP BY A.SANPHAM_FK     " +  
					   "  ) THUCTEXUAT ON THUCTEXUAT.SANPHAM_FK=HOA_DON.SPID  " +  
					   "  where  HOA_DON.SOLUONG - ISNULL(XUATKHO.TOTALXUAT,0)  > 0";
					
				}
				
			 
			ResultSet spRs = db.get(query);

			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			 
				try 
				{
					ISanpham sp = null;
					while(spRs.next())
					{
						String spId = spRs.getString("spId");
						String spMa = spRs.getString("spMa");
						String spTen = spRs.getString("spTen");
						String soluong="0";
						if(this.id.length() >0){
							soluong=formatter_6le.format(spRs.getDouble("THUCTEXUAT"));
							
						}else{
						  soluong = formatter_6le.format(spRs.getDouble("SOLUONGHD")-spRs.getDouble("soluongdaxuat"));
						}
						
						sp = new Sanpham(spId, spMa, spTen, "", soluong);
						sp.setIsBean(QlKhuvucKho);
						List<ISpDetail> spDetail = new ArrayList<ISpDetail>();	
						sp.setSoluongDaXuat(spRs.getString("soluongdaxuat"));	
						
						
						 
						
						sp.setSoluongTotal(formatter_6le.format(spRs.getDouble("SOLUONGHD")));
					 
						query= " SELECT NGAYHETHAN , XK.NGAYBATDAU, NGAYSANXUAT, SANPHAM_FK,AVAILABLE,SOLO,KVKHOID,SOLUONG,KVKHO "+
							 " FROM( "+
							 " SELECT  NGAYHETHAN , A.NGAYBATDAU, NGAYSANXUAT, SANPHAM_FK, ISNULL(AVAILABLE, 0) AS AVAILABLE, SOLO, "+ 
							 " KV.TEN AS KVKHO, KV.PK_SEQ AS KVKHOID,   ISNULL((     		SELECT SUM(B.SOLUONG)  		 "+
							 " FROM ERP_XUATKHO_SP_CHITIET B 		INNER JOIN ERP_XUATKHO XK ON XK.PK_SEQ=B.XUATKHO_FK "+    
							 " WHERE XK.KHO_FK = A.KHOTT_FK AND B.SANPHAM_FK = A.SANPHAM_FK  AND B.NGAYBATDAU=A.NGAYBATDAU  AND RTRIM(LTRIM(A.SOLO)) "+
							 " = RTRIM(LTRIM(B.SOLO))  " +
							 ( QlKhuvucKho==true? " AND KV.PK_SEQ = B.KHUVUCKHO_FK   	" :"")+
							 "	AND  XK.PK_SEQ=  "+(this.id.length()>0?this.id:"0")+"  ) ,0) AS SOLUONG "+ 
							 " FROM ERP_KHOTT_SP_CHITIET A  "+
							 " LEFT  JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ = A.KHUVUCKHO_FK "+ 
							 " WHERE A.SANPHAM_FK = "+spId+"   AND A.KHOTT_FK = "+this.khoId+""+ 
							 " ) XK WHERE XK.AVAILABLE + ISNULL(XK.SOLUONG ,0)>0 "+
							 " ORDER BY XK.NGAYHETHAN ASC, XK.AVAILABLE + ISNULL(XK.SOLUONG ,0) ASC";
					  
						double soluongtong=0;
								
						try{
						  soluongtong = Double.parseDouble(soluong.replace(",", ""));
						}catch(Exception err){
								
				}
			/*				
				System.out.println("Check soluongtong : " + soluongtong);
				System.out.println("Check soluong san pham: " + query);*/
							
					ResultSet rsSpDetail = db.get(query);
					if(rsSpDetail != null)
					{
								 
						while(rsSpDetail.next())
						{
						ISpDetail  splo =new SpDetail();
						splo.setSolo(rsSpDetail.getString("solo"));
						splo.setKhuId(rsSpDetail.getString("KVKHOID"));
						splo.setKhu(rsSpDetail.getString("KVKHO"));
						splo.setNgaysanxuat(rsSpDetail.getString("ngaysanxuat"));
						splo.setNgayhethan(rsSpDetail.getString("ngayhethan"));
						splo.setNgaybatdau(rsSpDetail.getString("ngaybatdau"));
						double avai = rsSpDetail.getDouble("SOLUONG") + rsSpDetail.getDouble("AVAILABLE");
				 		if(dasualo == false){
					 		if( soluongtong >0) {
								if(soluongtong < avai){
									splo.setSoluong(""+soluongtong);
									soluongtong=0;
								}else{
									soluongtong=soluongtong-avai;
									splo.setSoluong(""+avai);
								}
							}else{
								splo.setSoluong("0");
							}
				 		} else{
							splo.setSoluong(""+rsSpDetail.getDouble("SOLUONG"));
						}
							 		
						splo.setSoluongton(""+avai);
						spDetail.add(splo);
					}
								
					rsSpDetail.close();
				}
							
				sp.setSpDetailList(spDetail);
					 					
				spList.add(sp);
				}
				spRs.close();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			this.spList = spList;
			}
				
		//	check San pham xuat kho
			 
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
					List<ISpDetail> spDetail = sp.getSpDetailList();
					
					double sum = 0;
					for(int j = 0; j < spDetail.size(); j++)
					{
						sum += Double.parseDouble(spDetail.get(j).getSoluong().replaceAll(",", ""));
					}
						
					if(sum < Double.parseDouble(sp.getSoluong().replaceAll(",","")))
					{
						this.msg += "+ Sản phẩm " + sp.getMa() + " - " + sp.getDiengiai() + ", không đủ số lượng để xuất kho, vui lòng kiểm tra lại \n";
					}
				}
			}
 
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
		}
	}


	public void createRs_Display()
	{
		String query = "SELECT PK_SEQ, MA, TEN , 0 AS CHON FROM ERP_KHACHHANG " ;	
		//System.out.println("Khoi tao NPP: " + query);
		
		this.nppRs = this.db.get(query);
 
		query = "select PK_SEQ as nccId, ma + ', ' + ten as nccTen from ERP_NHACUNGCAP where trangthai = '1' and duyet = '1' order by ma asc";
		System.out.println("Khoi tao NCC: " + query);
		this.nccRs = this.db.get(query);
		
		this.ndxRs = db.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('XK')");
		
	 
			
			query=	" select PK_SEQ, TEN, DIACHI, QUANLYBIN from ERP_KHOTT where pk_seq in " +
			" (  select KHO_FK from ERP_XUATKHO where PK_SEQ="+this.id+" )";
			this.khoRs = db.get(query);
			
			if(this.khoId.equals("")){
				ResultSet rskho=db.get(query);
				try{
					if(rskho.next()){
						this.khoId=rskho.getString("PK_SEQ");
					}
					rskho.close();
				}catch(Exception er){
					
				}
			}
		 if(this.Loaixuatkho.equals("HD")){
		if(nppId.length() > 0 && this.hdtcId.length() > 0){
			
			if(this.ndxId.equals("100008"))
				query = "select PK_SEQ, SOHOADON from ERP_HOADON where (TRANGTHAI = '1' or TRANGTHAI = '4' or TRANGTHAI = '5') and KHACHHANG_FK = '"+this.nppId+"' " + 
				"and PK_SEQ not in (select HOADON_FK FROM ERP_XUATKHO where HOADON_FK != '"+this.hdtcId+"') AND LOAIHOADON = 1";
			else if(this.ndxId.equals("100002"))
				query = "select PK_SEQ, SOHOADON from ERP_HOADON where (TRANGTHAI = '1' or TRANGTHAI = '4' or TRANGTHAI = '5') and KHACHHANG_FK = '"+this.nppId+"' " + 
				"and PK_SEQ not in (select HOADON_FK FROM ERP_XUATKHO where HOADON_FK != '"+this.hdtcId+"') AND LOAIHOADON = 0";
			this.hdtcList = db.get(query);
			
		} 
		}else if(this.Loaixuatkho.equals("DH")) {
			query = " SELECT  PK_SEQ,  A.PREFIX + CAST(PK_SEQ AS NVARCHAR(10)) + ' _ ' + NGAYDAT AS SOHOADON  from ERP_DONDATHANG  A where  PK_SEQ ="+this.DdhId ; 
			this.RsDondathang=db.get(query);
		}
		
		if(this.nccId!=null&& this.nccId.length()>0)
		{
			query = "select PK_SEQ as dmhId, cast(PK_SEQ as nvarchar(10)) + ' _ ngay tra ' + NGAYMUA as dmhTen " +
					"from ERP_MUAHANG where TYPE = '2' and TRANGTHAI in (1,2) and NHACUNGCAP_FK = '" + this.nccId + "'";
	
			if(this.id!=null&& this.id.length()>0)
				query += " and PK_SEQ not in (select trahangncc_fk from ERP_XUATKHO where trahangncc_fk is not null and TRANGTHAI in (0, 1) and pk_seq != '" + this.id + "' )";
			else
				query += " and PK_SEQ not in (select trahangncc_fk from ERP_XUATKHO where trahangncc_fk is not null and TRANGTHAI in (0, 1))";
			
			System.out.println("Tra hang "+query);
			this.trahangNccRs = db.get(query);
		}
 
	}
	
	private void createSanpham()
	{	 
		String query = "";
		boolean dasualo = false;
		boolean QlKhuvucKho = false;
		query= "SELECT * FROM ERP_XUATKHO_SP_CHITIET WHERE XUATKHO_FK="+(this.id.length() >0?this.id:"0") ;
		ResultSet rs=db.get(query);
		try{
		if(rs.next()){
			dasualo=true;
		}
		}catch(Exception err){
			this.msg=err.getMessage();
			err.printStackTrace();
		}
		
		query= "select * from ERP_KHOTT WHERE QUANLYBIN = 1 AND PK_SEQ = '"+this.khoId+"'" ;
		
		rs = db.get(query);
		try{
		if(rs.next()){
			QlKhuvucKho = true;
		}
		}catch(Exception err){
			this.msg=err.getMessage();
			err.printStackTrace();
		}
		
		query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
		if(QlKhuvucKho){
			boolean tmp = false;
			rs = db.get(query);
			try{
			if(rs.next()){
				tmp = true;
			}
			}catch(Exception err){
				this.msg=err.getMessage();
				err.printStackTrace();
			}
			if(!tmp){
				this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
				QlKhuvucKho = false;
			}
		}
		
		if(this.trahangNccId != null && this.trahangNccId.length()>0) // TRẢ HÀNG CHO NCC
		{
			query =
			" SELECT  isnull(b.pk_seq,0) as spid, isnull(b.ma, '') as spMa, isnull(b.dvkd_fk,0) as spDvkd, \n"+  
	        " 		  isnull(b.ten1, b.ten)  as spTen, \n"+ 
	        " 		  isnull(tscd.pk_seq,0) as tscdid ,isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, isnull(nts.ma, 'NA') as nstNh, \n"+  
	        " 		  isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh, \n"+
			" 		  isnull(a.donvi, '') as donvi, a.soluong SOLUONGHD, isnull(a.dongia, '0') as dongia, \n"+ 
	 		" 		  isnull(a.thanhtien, '0') as thanhtien, isnull(a.phantramthue, '0') as phantramthue, isnull(a.thuenhapkhau, '0') as thuenhapkhau, ngaynhan, a.khott_fk, dungsai, \n"+  
	 	    "  		  isnull(muanguyenlieudukien_fk, 0) as mnlId , '1' as inraHd \n"+ 
	 	    " 		  ,isnull(a.tenhd, '') as tenhd, ISNULL(THUCTEXUAT.TOTALXUAT,0) THUCTEXUAT, ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT  \n"+  
	 	    "		FROM	 erp_muahang_sp a left join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq \n"+   
	 	    "	   	LEFT JOIN erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq \n"+  
	 	    "	   	LEFT JOIN erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq \n"+   
	 	    "	   	LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq \n"+ 
	 	    "	   	LEFT JOIN erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq \n"+ 
	 	    "	   	LEFT JOIN  " +  
		    "  		(  " +  
		    "  			SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  \n" +  
		    "  			FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  \n" +  
		    "  			WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.TRAHANGNCC_FK IN (   "+this.trahangNccId+") AND A.XUATKHO_FK != "+(this.id.length() >0 ?this.id:"0")+"  " +  
		    "  			GROUP BY A.SANPHAM_FK  " +  
		    "  		) \n " +  
		    "  		XUATKHO ON a.SANPHAM_FK = XUATKHO.SANPHAM_FK  \n " +  
	 	   	" 		LEFT JOIN  \n" +  
	 	   	"  		(  " +  
	 	   	"  			SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  \n" +  
	 	   	"  			FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  \n" +  
	 	   	"  			WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.TRAHANGNCC_FK IN (  "+this.trahangNccId+") AND A.XUATKHO_FK  = "+(this.id.length() >0 ?this.id:"0")+"  \n" +  
	 	   	"  			GROUP BY A.SANPHAM_FK  \n" +  
	 	   	"  		) THUCTEXUAT ON THUCTEXUAT.SANPHAM_FK = a.SANPHAM_FK \n" +
	 	    " WHERE muahang_fk = '"+this.trahangNccId+"' \n";
		 
			
			System.out.println("query:"+query);
			
			ResultSet spRs = db.get(query);

			List<ISanpham> spList = new ArrayList<ISanpham>();
				try 
				{
					ISanpham sp = null;
					while(spRs.next())
					{
						String spId = spRs.getString("spId");
						String spMa = spRs.getString("spMa");
						String spTen = spRs.getString("spTen");
						String soluong="0";
						if(this.id.length() >0){
							soluong=""+spRs.getDouble("THUCTEXUAT");
							
						}else{
						  soluong = ""+(spRs.getDouble("SOLUONGHD")-spRs.getDouble("soluongdaxuat"));
						}
						
						sp = new Sanpham(spId, spMa, spTen, "", soluong);
						sp.setIsBean(QlKhuvucKho);
						List<ISpDetail> spDetail = new ArrayList<ISpDetail>();	
						sp.setSoluongDaXuat(spRs.getString("soluongdaxuat"));		
						sp.setSoluongTotal(spRs.getString("SOLUONGHD"));
						
						query= 
							 " SELECT NGAYHETHAN , XK.NGAYBATDAU, NGAYSANXUAT, SANPHAM_FK,AVAILABLE,SOLO,KVKHOID,SOLUONG,KVKHO "+
							 " FROM( "+
							 " 		SELECT  NGAYHETHAN , A.NGAYBATDAU, NGAYSANXUAT, SANPHAM_FK, ISNULL(AVAILABLE, 0) AS AVAILABLE, SOLO, "+ 
							 " 				KV.TEN AS KVKHO, KV.PK_SEQ AS KVKHOID,   ISNULL((     		SELECT SUM(B.SOLUONG)  		 "+
							 " 		FROM 	ERP_XUATKHO_SP_CHITIET B 		INNER JOIN ERP_XUATKHO XK ON XK.PK_SEQ=B.XUATKHO_FK "+    
							 " 		WHERE 	XK.KHO_FK = A.KHOTT_FK AND B.SANPHAM_FK = A.SANPHAM_FK AND RTRIM(LTRIM(A.SOLO)) "+
							 " 				= RTRIM(LTRIM(B.SOLO))  " +
							 ( QlKhuvucKho==true? " AND KV.PK_SEQ = B.KHUVUCKHO_FK   	" :"")+
							 "				AND  XK.PK_SEQ=  "+(this.id.length()>0?this.id:"0")+"  ) ,0) AS SOLUONG "+ 
							 " FROM ERP_KHOTT_SP_CHITIET A  "+
							 " LEFT  JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ = A.KHUVUCKHO_FK "+ 
							 " WHERE A.SANPHAM_FK = "+spId+"   AND A.KHOTT_FK = "+this.khoId+""+ 
							 " ) XK WHERE XK.AVAILABLE + ISNULL(XK.SOLUONG ,0)>0 "+
							 " ORDER BY XK.NGAYHETHAN ASC, XK.AVAILABLE + ISNULL(XK.SOLUONG ,0) ASC";
					 
						System.out.println("Kho:"+ query +" \n");
						double soluongtong=0;
								
						try{
						  soluongtong = Double.parseDouble(soluong);
						}catch(Exception err){
								err.printStackTrace();
						}
													
					ResultSet rsSpDetail = db.get(query);
					if(rsSpDetail != null)
					{		 
						while(rsSpDetail.next())
						{
							ISpDetail  splo = new SpDetail();
							splo.setSolo(rsSpDetail.getString("solo"));
							splo.setKhuId(rsSpDetail.getString("KVKHOID"));
							splo.setKhu(rsSpDetail.getString("KVKHO"));
							splo.setNgaysanxuat(rsSpDetail.getString("ngaysanxuat"));
							splo.setNgayhethan(rsSpDetail.getString("ngayhethan"));
							splo.setNgaybatdau(rsSpDetail.getString("ngaybatdau"));
							double avai = rsSpDetail.getDouble("SOLUONG") + rsSpDetail.getDouble("AVAILABLE");
					 		if(dasualo == false){
						 		if( soluongtong >0) {
									if(soluongtong < avai){
										splo.setSoluong(""+soluongtong);
										soluongtong=0;
									}else{
										soluongtong=soluongtong-avai;
										splo.setSoluong(""+avai);
									}
								}else{
									splo.setSoluong("0");
								}
					 		} else{
								splo.setSoluong(""+rsSpDetail.getDouble("SOLUONG"));
							}
								 		
							splo.setSoluongton(""+avai);
							spDetail.add(splo);
					}
								
					rsSpDetail.close();
				}
							
				sp.setSpDetailList(spDetail);
					 					
				spList.add(sp);
			}
			spRs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			this.spList = spList;
		}
		
		if(this.hdtcId != null && this.hdtcId.length() > 0 && this.khoId.length() > 0){
			 
			query=" SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD, ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT,    " +  
				   "  ISNULL(THUCTEXUAT.TOTALXUAT, 0) AS THUCTEXUAT  " +  
				   "  FROM  " +  
				   "  (  " +  
				   "  	SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,  " +  
				   "  	CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG  " +  
				   "  	FROM ERP_HOADON_SP HDSP   " +  
				   "  	INNER JOIN ERP_HOADON HD ON HD.PK_SEQ =HDSP.HOADON_FK    " +  
				   "  	INNER JOIN ERP_SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ    " +  
				   "  	LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK " +  
				   "  	WHERE HDSP.HOADON_FK =   " + this.hdtcId+  
				   "  	AND HDSP.SOLUONG > 0 AND HDSP.KHOTT_FK = "+this.khoId+" " +  
				   "  	GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK " +  
				   "   " +  
				   "  )  " +  
				   "  HOA_DON LEFT JOIN  " +  
				   "  (  " +  
				   "  	SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  " +  
				   "  	FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  " +  
				   "  	WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.HOADON_FK IN (  "+this.hdtcId+") AND A.XUATKHO_FK != "+(this.id.length() >0 ?this.id:"0")+"  " +  
				   "  	GROUP BY A.SANPHAM_FK  " +  
				   "  )  " +  
				   "  XUATKHO ON HOA_DON.SPID = XUATKHO.SANPHAM_FK  " +  
				   "  LEFT JOIN  " +  
				   "  (  " +  
				   "  	SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  " +  
				   "  	FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  " +  
				   "  	WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.HOADON_FK IN (  "+this.hdtcId+") AND A.XUATKHO_FK  = "+(this.id.length() >0 ?this.id:"0")+"  " +  
				   "  	GROUP BY A.SANPHAM_FK  " +  
				   "  ) THUCTEXUAT ON THUCTEXUAT.SANPHAM_FK=HOA_DON.SPID " +
				   " where  HOA_DON.SOLUONG - ISNULL(XUATKHO.TOTALXUAT,0)  > 0 ";
			
		
		System.out.println("query LAY SP: " + query);
		ResultSet spRs = db.get(query);

		List<ISanpham> spList = new ArrayList<ISanpham>();
		
		 
			try 
			{
				ISanpham sp = null;
				while(spRs.next())
				{
					String spId = spRs.getString("spId");
					String spMa = spRs.getString("spMa");
					String spTen = spRs.getString("spTen");
					String soluong="0";
					if(this.id.length() >0){
						soluong=""+spRs.getDouble("THUCTEXUAT");
						
					}else{
					  soluong = ""+(spRs.getDouble("SOLUONGHD")-spRs.getDouble("soluongdaxuat"));
					}
					
					sp = new Sanpham(spId, spMa, spTen, "", soluong);
					sp.setIsBean(QlKhuvucKho);
					List<ISpDetail> spDetail = new ArrayList<ISpDetail>();	
					sp.setSoluongDaXuat(spRs.getString("soluongdaxuat"));		
					sp.setSoluongTotal(spRs.getString("SOLUONGHD"));
				 
					query= " SELECT NGAYHETHAN , XK.NGAYBATDAU, NGAYSANXUAT, SANPHAM_FK,AVAILABLE,SOLO,KVKHOID,SOLUONG,KVKHO "+
						 " FROM( "+
						 " SELECT  NGAYHETHAN , A.NGAYBATDAU, NGAYSANXUAT, SANPHAM_FK, ISNULL(AVAILABLE, 0) AS AVAILABLE, SOLO, "+ 
						 " KV.TEN AS KVKHO, KV.PK_SEQ AS KVKHOID,   ISNULL((     		SELECT SUM(B.SOLUONG)  		 "+
						 " FROM ERP_XUATKHO_SP_CHITIET B 		INNER JOIN ERP_XUATKHO XK ON XK.PK_SEQ=B.XUATKHO_FK "+    
						 " WHERE XK.KHO_FK = A.KHOTT_FK AND B.SANPHAM_FK = A.SANPHAM_FK  AND B.NGAYBATDAU=A.NGAYBATDAU  AND RTRIM(LTRIM(A.SOLO)) "+
						 " = RTRIM(LTRIM(B.SOLO))  " +
						 ( QlKhuvucKho==true? " AND KV.PK_SEQ = B.KHUVUCKHO_FK   	" :"")+
						 "	AND  XK.PK_SEQ=  "+(this.id.length()>0?this.id:"0")+"  ) ,0) AS SOLUONG "+ 
						 " FROM ERP_KHOTT_SP_CHITIET A  "+
						 " LEFT  JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ = A.KHUVUCKHO_FK "+ 
						 " WHERE A.SANPHAM_FK = "+spId+"   AND A.KHOTT_FK = "+this.khoId+""+ 
						 " ) XK WHERE XK.AVAILABLE + ISNULL(XK.SOLUONG ,0)>0 "+
						 " ORDER BY XK.NGAYHETHAN ASC, XK.AVAILABLE + ISNULL(XK.SOLUONG ,0) ASC";
				 
			 
					
					
					
					
					double soluongtong=0;
							
					try{
					  soluongtong = Double.parseDouble(soluong);
					}catch(Exception err){
							
			}
		/*				
			System.out.println("Check soluongtong : " + soluongtong);
			System.out.println("Check soluong san pham: " + query);*/
						
				ResultSet rsSpDetail = db.get(query);
				if(rsSpDetail != null)
				{
							 
					while(rsSpDetail.next())
					{
					ISpDetail  splo =new SpDetail();
					splo.setSolo(rsSpDetail.getString("solo"));
					splo.setKhuId(rsSpDetail.getString("KVKHOID"));
					splo.setKhu(rsSpDetail.getString("KVKHO"));
					splo.setNgaysanxuat(rsSpDetail.getString("ngaysanxuat"));
					splo.setNgayhethan(rsSpDetail.getString("ngayhethan"));
					splo.setNgaybatdau(rsSpDetail.getString("ngaybatdau"));
					double avai = rsSpDetail.getDouble("SOLUONG") + rsSpDetail.getDouble("AVAILABLE");
			 		if(dasualo == false){
				 		if( soluongtong >0) {
							if(soluongtong < avai){
								splo.setSoluong(""+soluongtong);
								soluongtong=0;
							}else{
								soluongtong=soluongtong-avai;
								splo.setSoluong(""+avai);
							}
						}else{
							splo.setSoluong("0");
						}
			 		} else{
						splo.setSoluong(""+rsSpDetail.getDouble("SOLUONG"));
					}
						 		
					splo.setSoluongton(""+avai);
					spDetail.add(splo);
				}
							
				rsSpDetail.close();
			}
						
			sp.setSpDetailList(spDetail);
				 					
			spList.add(sp);
			}
			spRs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		this.spList = spList;
		}
			
	//	check San pham xuat kho
		 
		if(this.spList.size() > 0)
		{
			for(int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				List<ISpDetail> spDetail = sp.getSpDetailList();
				
				double sum = 0;
				for(int j = 0; j < spDetail.size(); j++)
				{
					sum += Double.parseDouble(spDetail.get(j).getSoluong());
				}
					
				if(sum < Double.parseDouble(sp.getSoluong()))
				{
					this.msg += "+ Sản phẩm " + sp.getMa() + " - " + sp.getDiengiai() + ", không đủ số lượng để xuất kho, vui lòng kiểm tra lại \n";
				}
			}
		}
		 
	}
	
	public void changeDdh() 
	{
		try{
			if(this.DdhId.length() > 0){
				String query="select NGAYDAT from ERP_DONDATHANG where PK_SEQ="+this.DdhId;
				ResultSet rs=db.get(query);
				if(rs.next()){
					this.ngayxuatkho=rs.getString("NGAYDAT");
				}
				rs.close();
			}
			
	 
		
		//if( ((this.ddhIds != null && this.ddhIds.length()>0) || (this.trahangNccId!=null&&this.trahangNccId.length()>0)) && (this.ndxId!=null&&this.ndxId.length()>0) && (this.khoId!=null&&this.khoId.length()>0) && this.spList.size() <= 0)
		if(this.hdtcId != null && this.hdtcId.length() > 0 && this.khoId.length() > 0)
		{
			this.createSanpham();
		}
		else{
			this.spList = new ArrayList<ISanpham>();
		}
		
		}catch(Exception er){
			this.msg=er.getMessage();
			er.printStackTrace();
		}
	}
	
	public void init() 
	{
		String query = 
			" select 	a.PK_SEQ as pxkId,  a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as maphieu , a.NGAYXUAT, a.NGAYCHOT, \n" +
			" 			a.HOADON_FK, A.DONDATHANG_FK , a.NPP_FK as nppId, \n" +
			" 			a.TRAHANGNCC_FK, a.KHO_FK, a.GHICHU, a.NOIDUNGXUAT, \n" +
			" 			a.LYDOXUAT,   \n" +
			"			a.TRANGTHAI \n" +
/*			" 			case when a.TRAHANGNCC_FK is null " +
			"				then (b.PREFIX + cast(a.DONDATHANG_FK as varchar(20))) " +
			"				else (g.PREFIX + c.PREFIX + cast(a.TRAHANGNCC_fk as varchar(20)) ) " +
			" 			end as SOCHUNGTU, isnull(a.NPPIDS,'') as NPPIDS, isnull(a.DDHIDS,'') as DDHIDS  " +*/
			" from ERP_XUATKHO a \n" +
/*			" left join DONDATHANG b on a.DONDATHANG_FK = b.PK_SEQ " +
			" left join ERP_MUAHANG c on a.TRAHANGNCC_FK = c.PK_SEQ " + 
			" left join ERP_DONVITHUCHIEN g on c.donvithuchien_fk = g.pk_seq " +*/
			" where a.pk_seq = '" + this.id + "'";
		
		System.out.println("PXK111: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayxuatkho = rs.getString("ngayxuat");
					this.ngaychotNV = rs.getString("NGAYCHOT");
					
					this.nppId = "";
					if(rs.getString("nppId") != null)
						this.nppId = rs.getString("nppId");
					
					//this.nppIds = rs.getString("nppIds");
						
					//this.nccId = "";
					//if(rs.getString("nccId") != null)
						//this.nccId = rs.getString("nccId");
					
					this.khoId = rs.getString("kho_fk");
					
					this.hdtcId = "";
					if(rs.getString("HOADON_FK") != null){
						this.hdtcId = rs.getString("HOADON_FK");
						this.Loaixuatkho="HD";
					}else if(rs.getString("dondathang_fk")!=null) {
						this.DdhId=rs.getString("dondathang_fk");
						this.Loaixuatkho="DH";
					}
					
					//this.ddhIds = rs.getString("ddhIds");
					
					this.trahangNccId = "";
					if(rs.getString("TRAHANGNCC_FK") != null)
						{
							this.trahangNccId = rs.getString("TRAHANGNCC_FK");
							this.nccId = rs.getString("nppId");
						}
					
					this.ndxId = rs.getString("NOIDUNGXUAT");
					this.lydoxuat = rs.getString("LYDOXUAT");
					this.ghichu = rs.getString("GHICHU");					
					this.trangthai = rs.getString("trangthai");
					
					this.maphieu = rs.getString("maphieu");
					
					//this.nguoinhanhang = rs.getString("SOCHUNGTU"); //Dùng tạm cho số chứng từ
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
				this.msg=e.getMessage();
				
			}
		}
	
		if(Double.parseDouble(this.trangthai) >= 1){
			this.initSanphamDisplay();
			this.createRs_Display();
		}else{
			if(this.Loaixuatkho.equals("DH")){
				this.createSanpham_ddh();
			}else {
				this.createSanpham();
			}
			this.createRs();
		}
		
		
	}

	public void initPdf()
	{
		String query =  "select a.PK_SEQ as pxkId, a.NGAYXUAT, a.KHO_FK, a.GHICHU, e.MA + '; ' + e.TEN as NOIDUNGXUAT, " +
						"a.LYDOXUAT, b.KHACHHANG_FK as nppId, g.pk_seq as nccId, a.TRANGTHAI, " +
						"a.HOADON_FK, a.TRAHANGNCC_FK, " +
						"c.ma + ', ' + c.ten as nppTen, isnull(c.DIACHI, 'na') as diachiNpp, " +
						"g.ma + ', ' + g.ten as nccTen, isnull(g.DIACHI, 'na') as diachiNcc, " +
						"d.TEN + '; ' + d.DIACHI as diachiKho  " +
						"from ERP_XUATKHO a " +
						"left join ERP_HOADON b on a.HOADON_FK = b.PK_SEQ " +
						"left join ERP_KHACHHANG c on b.KHACHHANG_FK = c.PK_SEQ " +
						"left join ERP_MUAHANG f on a.trahangncc_fk = f.pk_seq " +
						"left join ERP_NHACUNGCAP g on f.nhacungcap_fk = g.pk_seq " +
						"inner join ERP_KHOTT d on a.KHO_FK = d.PK_SEQ " +
						"inner join ERP_NOIDUNGNHAP e on a.NOIDUNGXUAT = e.PK_SEQ " +
						"where a.pk_seq = '" + this.id + "'";
		
		//if(this.inddhIds!=null && this.inddhIds.trim().length()>0)
		//	query+=" and b.pk_seq in ("+this.inddhIds+")";
		
		System.out.println("PXK PDF: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayxuatkho = rs.getString("ngayxuat");
					this.khoId = rs.getString("kho_fk");
					
					if(rs.getString("trahangncc_fk") != null)
					{
						this.nppId = rs.getString("nccId");
						//this.ddhId = " - Số trả hàng: " + rs.getString("TRAHANGNCC_FK");
						this.diachinhan = rs.getString("diachiNcc");
						this.nguoinhanhang = rs.getString("nccTen");
					}
					else
					{
						this.nppId = rs.getString("nppId");
						//this.ddhId =  " - Số đơn đặt hàng: " + rs.getString("dondathang_fk");
						this.diachinhan = rs.getString("diachiNpp");
						this.nguoinhanhang = rs.getString("nppTen");
					}
					//this.nppIds = rs.getString("nppIds");
					this.hdtcId = rs.getString("HOADON_FK");
					this.ndxId = rs.getString("NOIDUNGXUAT");
					this.lydoxuat = rs.getString("LYDOXUAT");
					this.ghichu = rs.getString("GHICHU");
					this.trangthai = rs.getString("trangthai");
					this.xuattaikho = rs.getString("diachiKho");
				}
				rs.close();
			} 
			catch (SQLException e) {}
		}
		
		this.initSanPhamPdf();
	}
	
	private void initSanphamDisplay() {
		System.out.println("DISPLAY");
		String query = " select a.SANPHAM_FK, b.MA, b.TEN, a.SOLUONG from ERP_XUATKHO_SANPHAM a " +
					   " inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where XUATKHO_FK = '" + this.id + "'";
 
			ResultSet rs = db.get(query);
			
			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			try 
			{
				ISanpham sanpham;
				String[] param = new String[6];
				
				while(rs.next())
				{
					param[0] = rs.getString("SANPHAM_FK");
					param[1] = rs.getString("MA");
					param[2] = rs.getString("TEN");
					param[3] = rs.getString("SOLUONG");
					param[4] = "";
					
					sanpham = new Sanpham(param);
 
					List<ISpDetail> spDetail = new ArrayList<ISpDetail>();	
					query = " select sp.*, kv.TEN AS KVKHO from ERP_XUATKHO_SP_CHITIET sp " +
							" left join ERP_KHUVUCKHO kv on sp.KHUVUCKHO_FK = kv.PK_SEQ " +
							" WHERE sanpham_fk="+rs.getString("SANPHAM_FK")+" and XUATKHO_FK="+this.id;
					ResultSet rsSpDetail = db.get(query);
				 
						while(rsSpDetail.next())
						{
							ISpDetail  splo =new SpDetail();
							splo.setSolo(rsSpDetail.getString("solo"));
							splo.setKhu(rsSpDetail.getString("KVKHO"));
					 		splo.setSoluong(""+rsSpDetail.getDouble("SOLUONG"));
					 		
					 		splo.setNgaybatdau(rsSpDetail.getString("NGAYBATDAU"));
					 		splo.setNgayhethan(rsSpDetail.getString("NGAYHETHAN"));
					 		splo.setNgaysanxuat(rsSpDetail.getString("Ngaysanxuat"));
					 		if(splo.getKhu() == null || splo.getKhu().length() == 0)
					 			sanpham.setIsBean(false);
					 		else
					 			sanpham.setIsBean(true);
					 		spDetail.add(splo);
						}
						rsSpDetail.close();
				  
					sanpham.setSpDetailList(spDetail);	
					this.soluongxuat=Float.parseFloat(sanpham.getSoluong());
					spList.add(sanpham);
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("SỐ LƯỢNG XUẤT: "+this.soluongxuat);
			this.spList = spList;
	}


	public void initLayHang() 
	{
		
		
		String query = 
			" select a.NPP_FK, a.PK_SEQ as pxkId, a.NGAYXUAT, a.HOADON_FK, a.KHO_FK, c.ten as khoten, a.GHICHU, a.NOIDUNGXUAT, " +
			" a.LYDOXUAT, b.MA as ndxId , a.TRANGTHAI " +
			" from ERP_XUATKHO a " +
			" inner join ERP_NOIDUNGNHAP b on a.NOIDUNGXUAT = b.PK_SEQ " +
			" inner join ERP_KHOTT c on a.kho_fk = c.pk_seq " +			
			" where a.pk_seq = '"  + this.id + "'";
		
		//System.out.println("Lay hang: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
		try 
		{
			while(rs.next())
			{
				this.ngayxuatkho = rs.getString("ngayxuat");
				this.khoId = rs.getString("khoten");
				this.hdtcId = rs.getString("HOADON_FK");
				this.ndxId = rs.getString("ndxId");
				this.lydoxuat = rs.getString("LYDOXUAT");
				this.ghichu = rs.getString("GHICHU");
				this.trangthai = rs.getString("trangthai");
				this.nppId=rs.getString("NPP_FK");
				//this.nppIds=rs.getString("NPPIDS");
			}
			rs.close();
		} 
		catch (SQLException e) {}
		}
		
		this.initSanPhamLayHang();
 
	}
	
 
	private void initSanPhamPdf()
	{
		String query =  " SELECT A.SANPHAM_FK, B.MA, B.TEN,  C.DONVI AS DVT, ISNULL(D.SOLUONG1,0) AS QUYCACH,  A.SOLUONG, ISNULL(D.SOLUONG2,0) AS SOLUONG2 , "+ 
						" CAST(ROUND(B.TRONGLUONG, 5) AS NUMERIC(10, 5)) AS TRONGLUONG, CAST(ROUND(B.THETICH, 5) AS NUMERIC(10, 5)) AS THETICH  "+
						" FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ  "+
						" INNER JOIN DONVIDOLUONG C ON B.DVDL_FK = C.PK_SEQ  "+
						" LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK AND  D.DVDL2_FK=100004 "+
						" WHERE XUATKHO_FK = "+this.id;
	
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			try 
			{				
				while(rs.next())
				{
					ISanpham sanpham = new Sanpham();
					 
					int soluong = rs.getInt("SOLUONG");
					float quycach = rs.getFloat("quycach");
					float soluong2 = rs.getFloat("SOLUONG2");
					
					float thung = 0;
					float le = 0;
					if(quycach >0){
						thung = soluong * soluong2 / quycach;
						
						le = (soluong * soluong2) % quycach;
					}
					
					sanpham.setId(rs.getString("SANPHAM_FK"));
					sanpham.setMa(rs.getString("MA"));
					sanpham.setDiengiai(rs.getString("TEN"));
					sanpham.setDVT(rs.getString("DVT"));
					
					sanpham.setSoluong(Integer.toString(soluong));
					sanpham.setQuycach(Float.toString(quycach));
						String sole_1=formatter_6le.format(thung);
						 
						 int idex=sole_1.indexOf(".");
						  
						 if(idex >0){
							 thung=Float.parseFloat(sole_1.substring(0,idex) );
							 
						 }
						 
						 sanpham.setThung(""+thung);
						  
						 
					sanpham.setLe(Float.toString(le));
					 
					spList.add(sanpham);
				}
			
				rs.close();
			} 
			catch (Exception e) {e.printStackTrace();}
			
			this.spList = spList;
		}
	}

	private void initSanPhamLayHang()
	{
		List<ISanpham> spList = new ArrayList<ISanpham>();
		ISanpham sanpham = new Sanpham();
		spList.add(sanpham);
		List<ISpDetail> spctList = sanpham.getSpDetailList();
		
		String query =  " SELECT SP.MA as SPMA, SP.TEN AS SPTEN, A.SOLO AS SOLO, kv.TEN AS KVKHO, "+  
						" ISNULL(A.NGAYSANXUAT, '') AS NGAYSANXUAT, "+  	 
						" DVDL.DONVI AS DVDL,   "+
						" ISNULL(QC.SOLUONG1,0) AS SL1, ISNULL(QC.SOLUONG2,0) AS SL2, A.SOLUONG AS SL, "+   
						" CAST(ROUND(SP.TRONGLUONG, 5) as numeric(10, 5)) AS TRONGLUONG, "+  
						" CAST(ROUND(SP.THETICH, 5) AS numeric(10, 5)) AS THETICH,a.SOLUONG "+ 
						" FROM ERP_XUATKHO_SP_CHITIET  A   "+ 
						" INNER JOIN ERP_XUATKHO XK ON A.XUATKHO_FK = XK.PK_SEQ  "+  
						" LEFT JOIN ERP_KHUVUCKHO kv on kv.PK_SEQ = A.KHUVUCKHO_FK   "+ 
						" INNER JOIN ERP_SANPHAM SP ON A.SANPHAM_FK = SP.PK_SEQ    "+
						" INNER JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK = DVDL.PK_SEQ    "+
						" LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = A.SANPHAM_FK  AND QC.DVDL2_FK=100012 "+  
						" WHERE XUATKHO_FK ="+this.id;
		
		System.out.println("initSanPhamLayHang: " + query);
		ResultSet rs = db.get(query);
		
		if(rs != null)
		{
			try 
			{	
				while(rs.next())
				{
 
					int soluong = rs.getInt("SOLUONG");
					float SL1 = rs.getFloat("SL1");
					float SL2 = rs.getFloat("SL2");
					
					float thung = 0;
					float le = 0;
					if(SL2 >0){
						thung = soluong * SL1 / SL2;
						le = (soluong * SL1) % SL2;
					}
					
					double trongluong = rs.getDouble("trongluong");
					double thetich = rs.getDouble("thetich");
					
					ISpDetail sp = new SpDetail();
					sp.setTen(rs.getString("SPMA") + " - " + rs.getString("SPTEN"));
					sp.setDvt(rs.getString("DVDL"));
					sp.setKhu(rs.getString("KVKHO"));
					sp.setSolo(rs.getString("SOLO"));
					sp.setSoluong(rs.getString("SL"));
					sp.setSoluongton(rs.getString("NGAYSANXUAT"));
					sp.setQuycach(SL1+"");
					sp.setVitri(thung+"");
					sp.setVitriId(le+"" );
					sp.setTrongLuong(trongluong);
					sp.setTheTich(thetich);
					
					spctList.add(sp);
				}
			
				rs.close();
			} 
			catch (Exception e) { e.printStackTrace();}
			
			this.spList = spList;
		}
	}

	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public List<ISanpham> getSpList()
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}

	public String getLydoxuat() 
	{
		return this.lydoxuat;
	}

	public void setLydoxuat(String lydoxuat) 
	{
		this.lydoxuat = lydoxuat;
	}

	public String getGhichu() 
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public ResultSet getDdhList() 
	{
		return this.ddhRs;
	}

	public void setDdhList(ResultSet ddhList)
	{
		this.ddhRs = ddhList;
	}

	public ResultSet getDdhList2() 
	{
		return this.ddhRs2;
	}

	public void setDdhList2(ResultSet ddhList2)
	{
		this.ddhRs2 = ddhList2;
	}

	public String getNguoinhanhang()
	{
		String tmp = "";
		String query = "select TEN from ERP_KHACHHANG WHERE PK_SEQ IN (" + this.nppId + ") ";
		System.out.println("Nguoi nhan hang " + query);
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				while(rs.next()){
					tmp = tmp + rs.getString("TEN") + ", ";
				}
				rs.close();
				
			}catch(java.sql.SQLException e){}
		}
		if(tmp.length() > 0) tmp = tmp.substring(0, tmp.length() - 2);
		
		this.nguoinhanhang = tmp;
		return this.nguoinhanhang ;
	}

	public void setNguoinhanhang(String nguoinhanhang) 
	{
		this.nguoinhanhang = nguoinhanhang;
	}

	public String getDiachi()
	{
		return this.diachinhan;
	}

	public void setDiachi(String diachi)
	{
		this.diachinhan = diachi;
	}

	public String getXuattaikho() 
	{
		return this.xuattaikho;
	}

	public void setXuattaikho(String xuattaikho) 
	{
		this.xuattaikho = xuattaikho;
	}

	public boolean chotXuatKho(String userId) 
	{
		String query = "";
		try 
		{
			this.db.getConnection().setAutoCommit(false);
			Utility util = new Utility();
			query = " SELECT NGAYCHOT,HOADON_FK,DONDATHANG_FK, TRAHANGNCC_FK   FROM ERP_XUATKHO WHERE TRANGTHAI = 0 AND PK_SEQ = " + this.id;
			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String idkhott="";
			if(rs.next()){
				  ngaychotnv=rs.getString("ngaychot");
				  this.trahangNccId=rs.getString("TRAHANGNCC_FK");
				  this.hdtcId=rs.getString("HOADON_FK");
				  this.DdhId=rs.getString("DONDATHANG_FK");
			}
			rs.close();
			int thangtruoc = Integer.parseInt(ngaychotNV.substring(5, 7));
			int namtruoc = Integer.parseInt(ngaychotNV.substring(0, 4));
			
			if(thangtruoc == 1){
				namtruoc = namtruoc-1;
				thangtruoc = 12;
				
			}else{
				thangtruoc = thangtruoc-1;
			}
 
			  
			String kbhId = "";
			// Lấy kênh bán hàng
			if(this.hdtcId !=null &&  this.hdtcId.trim().length() > 0)
			{								
				query = "SELECT KBH_FK FROM ERP_HOADON WHERE PK_SEQ = '" + this.hdtcId + "'";
			 
				rs = db.get(query);
				while(rs.next())
				{
					kbhId = rs.getString("KBH_FK");
				}
				
				rs.close();
			}
			
			boolean QlKhuvucKho = false;
			query = "select * from ERP_KHOTT WHERE QUANLYBIN = 1 AND PK_SEQ = '"+this.khoId+"'" ;
			
			rs = db.get(query);
			try{
			if(rs.next()){
				QlKhuvucKho = true;
			}
			}catch(Exception err){
				this.msg=err.getMessage();
				err.printStackTrace();
			}
			query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
			if(QlKhuvucKho){
				boolean tmp = false;
				rs = db.get(query);
				try{
				if(rs.next()){
					tmp = true;
				}
				}catch(Exception err){
					this.msg=err.getMessage();
					err.printStackTrace();
				}
				if(!tmp){
					this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
					QlKhuvucKho = false;
				}
			}
			
			query	=	" SELECT SP.LOAISANPHAM_FK, NPP_FK," +
						" XK.KHO_FK, A.SANPHAM_FK, A.SOLO, A.SOLUONG, A.KHUVUCKHO_FK ,A.NGAYBATDAU  "+
						" FROM ERP_XUATKHO_SP_CHITIET A " +
						" INNER JOIN ERP_XUATKHO XK ON XK.PK_SEQ = A.XUATKHO_FK  " +
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = A.SANPHAM_FK  " +
						" WHERE XK.PK_SEQ = " + this.id;
			
			//System.out.println(query);
			rs = this.db.get(query);
			while (rs.next()){
					String loaisanpham = rs.getString("LOAISANPHAM_FK");
					//String loaidh = rs.getString("LOAIDH");
					String spid = rs.getString("SANPHAM_FK");
					 idkhott = rs.getString("KHO_FK");
					//String nhanvien_fk = rs.getString("NHANVIEN_FK");
					//String npp_fk = rs.getString("NPP_FK");
					String ngaybatdau= rs.getString("ngaybatdau");
					String khuvuckhoId = rs.getString("KHUVUCKHO_FK");
					String solo = rs.getString("SOLO");
					double soluongct = rs.getDouble("SOLUONG");
								
					query = " SELECT SANPHAM_FK, round(GIATON,0) GIATON  from ERP_TONKHOTHANG " +
							" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
							" AND NAM = '" + namtruoc + "' AND KHOTT_FK = '" + idkhott + "'";
					
					System.out.println(query);
					ResultSet rsgia = db.get(query);
					double dongia = 0;
					if(rsgia.next()){
						dongia = rsgia.getDouble("GIATON");
					}
					rsgia.close();
					double thanhtien = dongia*soluongct;
				
					query = " UPDATE ERP_XUATKHO_SANPHAM " +
							" SET DONGIA = " + dongia + ", THANHTIEN = " + dongia + "*SOLUONG   " +
							" WHERE SANPHAM_FK = " + spid + " AND XUATKHO_FK = " + this.id;
 
					if(!this.db.update(query))
					{
						this.msg = "2.Khong the cap nhat ERP_XUATKHO_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
					 
					//soluong GIẢM ,booked giảm lại
					double soluong=(-1)*soluongct;
					double booked= (-1)*soluongct;
					double available=  0;
					
					String msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay (db,this.khoId,spid,soluong, booked,available,0,ngaychotnv);
					if(msg1.length() >0){
						this.msg =msg1;
						db.getConnection().rollback();
						return false;
					}
					
					
					msg1=util_kho.Update_Kho_Sp_Chitiet(db,this.khoId,spid,soluong, booked,available,0,solo,"",khuvuckhoId,ngaybatdau);
					if(msg1.length() >0){
						this.msg =msg1;
						db.getConnection().rollback();
						return false;
					}
					
								
					//String query1 = "";
				
					/*if(loaidh.trim().equals("3")){
					
						query	=  	" update  ERP_KHOTT_SP_CHITIET_KYGUINPP set BOOKED = BOOKED - " + soluong + ",SOLUONG=SOLUONG - " + soluong + "  " +
									" where npp_fk = " + npp_fk + "  and  SANPHAM_FK = " + spid + " " +
									" and KHOTT_FK = " + idkhott + " and  rtrim(LTRIM(solo)) = '" + solo + "'";
						
						System.out.println(query);
						if(db.updateReturnInt(query) < 1 ){
						 
							this.msg = "Lỗi dòng lệnh : "+query;
							db.update("rollback");
							return false;
						}
						
						query1 = " update  ERP_KHOTT_SANPHAM_KYGUINPP set BOOKED=BOOKED - " + soluong + ", SOLUONG=SOLUONG - " + soluong + "  " +
								 " where npp_fk = " + this.nppId + "  and  SANPHAM_FK = " + spid + " and KHOTT_FK = " + idkhott ;

						System.out.println(query);
						if(db.updateReturnInt(query1) <1 ){						 
							this.msg = "Lỗi dòng lệnh : "+query;
							db.update("rollback");
							return false;
						}
					
					}else if(loaidh.trim().equals("4")){
					
						query	= 	" update  ERP_KHOTT_SP_CHITIET_KYGUINHANVIEN set BOOKED = BOOKED - " + soluong + ", SOLUONG = SOLUONG - " + soluong + "  " +
									" where nhanvien_fk = " + nhanvien_fk + "  and  SANPHAM_FK = " + spid + " " +
									" and KHOTT_FK = " + idkhott + " and  rtrim(LTRIM(solo)) = '" + solo + "'";
						
						System.out.println(query);
						if(db.updateReturnInt(query) <1 ){
						 
							this.msg = "Lỗi dòng lệnh : "+query;
							db.update("rollback");
							return false;
						}
					
					
						query1= " update  ERP_KHOTT_SANPHAM_KYGUINHANVIEN set BOOKED = BOOKED - " + soluong + ",SOLUONG = SOLUONG - " + soluong + "  " +
								" where NHANVIEN_FK = " + npp_fk + "  and  SANPHAM_FK = " + spid + " and KHOTT_FK = " + idkhott ;

						System.out.println(query);
						if(db.updateReturnInt(query1) <1 ){
						 
							this.msg	=	"Lỗi dòng lệnh : "+query;
							db.update("rollback");
							return false;
						}
					
					} 
 */
				//**********
			
					String masanpham = spid;
				
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
				
					//System.out.println("NOI DUNG XUAT: " + this.ndxId);
					String queryTK = "";
					if( this.ndxId.equals("100007") || this.ndxId.equals("100008") || this.ndxId.equals("100011") || this.ndxId.equals("100002") || this.ndxId.equals("100003")  || this.ndxId.equals("100030")||this.ndxId.equals("100022")||this.ndxId.equals("100027")||this.ndxId.equals("100028")) //DOI LAI, CACH MOI KO DUNG BANG CONFIG....
					{
						String taikhoanktCo = "";
						String taikhoanktNo = "";
					
						String doituong_no = "";
						String madoituong_no = "";
						String doituong_co = "";
						String madoituong_co = "";
					
						if(this.ndxId.equals("100007")) //Xuất trả NCC
						{
							String queryncc = " SELECT NCC_FK from ERP_HOADON where pk_seq in (select HOADON_FK from ERP_XUATKHO where pk_seq = "+ this.id +")  \n ";
							System.out.println(queryncc);
							
							ResultSet ncc = db.get(queryncc);
							
							String ncc_fk = "";
							while (ncc.next()){
								ncc_fk = ncc.getString("NCC_FK");								
							}
							ncc.close();		
							
							queryTK = 	" SELECT a.TaiKhoanKt_fk as TAIKHOANKTCO, (SELECT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE PK_SEQ = "+ncc_fk+" ) as TAIKHOANKTNO, \n " +										
										" FROM erp_loaisanpham a  \n " +
										" WHERE a.pk_seq = '" + loaisanpham + "' \n ";									
						
							System.out.println(queryTK);
							
												
							
							doituong_no = "Nhà cung cấp";
							madoituong_no = ncc_fk;
							doituong_co = "Sản phẩm";
							madoituong_co = masanpham;
						}
						else
						{
							if(this.ndxId.equals("100008")) //Xuất khuyến mại
							{
								queryTK = 	" select a.TaiKhoanKt_fk as TAIKHOANKTCO, b.pk_seq as TAIKHOANKTNO " +
											" from erp_loaisanpham a, erp_taikhoanKT b  " +
											" where a.pk_seq = '" + loaisanpham + "' and b.SOHIEUTAIKHOAN = '63230000' ";
							
								//System.out.println(queryTK);
								doituong_no = "Sản phẩm";
								madoituong_no = masanpham;
								doituong_co = "Sản phẩm";
								madoituong_co = masanpham;
							}
							else
							{
									if(this.ndxId.equals("100002")) //Xuất bán hàng theo đơn hàng
									{ 									
									
										queryTK = 	"\n select a.TaiKhoanKt_fk as TAIKHOANKTCO,  " +
													"\n( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = " +
													"\n( case when a.PK_SEQ IN (100041,100042) and (SELECT KBH_FK FROM ERP_DONDATHANG WHERE PK_SEQ = (SELECT DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ = "+this.id+")) != 100007  then '63211000'  " +
													"\n when a.PK_SEQ IN (100041,100042) and (SELECT KBH_FK FROM ERP_DONDATHANG WHERE PK_SEQ = (SELECT DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ = "+this.id+")) = 100007 then ' 63220000' "+
													"\n       when a.PK_SEQ = 100043  then '63212000' " +
													"\n       else '63280000' "+
													"\n  end ) )  " +
													"\n as TAIKHOANKTNO   " +
													"\n from ERP_LOAISANPHAM a  " +
													"\n where a.pk_seq = '" + loaisanpham + "'  ";
							
										//System.out.println(queryTK);
										doituong_no = "Sản phẩm";
										madoituong_no = masanpham;
										doituong_co = "Sản phẩm";
										madoituong_co = masanpham;
									}
									else
									{
										if(this.ndxId.equals("100003")) //Xuất bán hàng nội bộ
										{
											queryTK = " select a.TaiKhoanKt_fk as TAIKHOANKTCO,  " +
													  "( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '63260000' )   as TAIKHOANKTNO   " +
													  " from ERP_LOAISANPHAM a  " +
													  " where a.pk_seq = '" + loaisanpham + "'  ";
								
											//System.out.println(queryTK);
											doituong_no = "Sản phẩm";
											madoituong_no = masanpham;
											doituong_co = "Sản phẩm";
											madoituong_co = masanpham;
										}
										else
										{
											if(this.ndxId.equals("100030")) // Xuất nhượng bán NVL, bao bì
											{
												queryTK = " select a.TaiKhoanKt_fk as TAIKHOANKTCO,  " +
												  "( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '63280000' )   as TAIKHOANKTNO   " +
												  " from ERP_LOAISANPHAM a  " +
												  " where a.pk_seq = '" + loaisanpham + "'  ";
				
												//System.out.println(queryTK);
												doituong_no = "Sản phẩm";
												madoituong_no = masanpham;
												doituong_co = "Sản phẩm";
												madoituong_co = masanpham;
											}
											else if(this.ndxId.equals("100022"))  // Xuất bán hàng ký gửi >> Xuất biếu tặng
											{
												queryTK = " select a.TaiKhoanKt_fk as TAIKHOANKTCO,  " +
														  "( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '63230000' )   as TAIKHOANKTNO   " +
														  " from ERP_LOAISANPHAM a  " +
														  " where a.pk_seq = '" + loaisanpham + "'  ";
						
														//System.out.println(queryTK);
														doituong_no = "Sản phẩm";
														madoituong_no = masanpham;
														doituong_co = "Sản phẩm";
														madoituong_co = masanpham;
											}
											else if(this.ndxId.equals("100027")||this.ndxId.equals("100028")){
												queryTK = 
												  " select a.TaiKhoanKt_fk as TAIKHOANKTCO,  " +
												  "( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '63230000' )   as TAIKHOANKTNO   " +
												  " from ERP_LOAISANPHAM a  " +
												  " where a.pk_seq = '" + loaisanpham + "'  ";
				
												//System.out.println(queryTK);
												doituong_no = "Sản phẩm";
												madoituong_no = masanpham;
												doituong_co = "Sản phẩm";
												madoituong_co = masanpham;
											}
										}
									}
								
							}
						
						}
					
						//System.out.println("5.Query lay tai khoan: " + queryTK);
						if(queryTK.trim().length()>0)
						{
							//System.out.println("chay vao day ");
							ResultSet tkRs = db.get(queryTK);
							if(tkRs != null)
							{
								if(tkRs.next())
								{
									taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
									taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
									tkRs.close();
								}
							
								if(taikhoanktCo == null || taikhoanktCo.trim().length() <= 0 || taikhoanktNo == null || taikhoanktNo.trim().length() <= 0)
								{
									this.msg = "Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
									rs.close();
									db.getConnection().rollback();
									return false;
								}
							
							//	Nguyen te khi xuat kho chinh la VND
								String tiente_fk = "100000";
								double  dongiaViet = dongia;
							
							
							//UPDATE NO-CO NEW
								thanhtien = dongia *  soluongct;
							  
								msg = util.Update_TaiKhoan( db, thang, nam, this.ngayxuatkho, ngaychotNV, "Xuất kho", this.id, taikhoanktNo, taikhoanktCo, this.ndxId, 
															Double.toString(thanhtien), Double.toString(thanhtien), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", (""+soluong), Double.toString(dongiaViet), tiente_fk, Double.toString(dongia), "1", dongiaViet + "*" + soluong, dongia + "*" + soluong, "" );
								if(msg.trim().length()>0)
								{
									rs.close();
									tkRs.close();
									this.msg = "Loi update: " + msg;
									System.out.println("Loi khi chot: " + this.msg);
									db.getConnection().rollback();
									return false;
								}
							}
						}
					
					} 
					
				//*********
			}
			
			query = "update ERP_XUATKHO set trangthai = '1', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + userId + "' where pk_seq = '" + this.id + "'";
			//System.out.println(query);
			if(!db.update(query))
			{
				this.msg = "8.Khong the cap nhat NHAPKHO: " + query;
				db.getConnection().rollback();
				
				return false;
			}
			
			
			//System.out.println("HOADON: "+this.hdtcId);
			
			//PHIẾU XUẤT KHO CHO HÓA ĐƠN TÀI CHÍNH || HÓA ĐƠN KHUYẾN MÃI
			if(this.hdtcId != null && this.hdtcId.length()>0)
			{
				
		     query=" SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD, ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT     " +  
				   "  FROM  " +  
				   "  (  " +  
				   "  	SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,  " +  
				   "  	CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG2/QC.SOLUONG1)  END AS SOLUONG  " +  
				   "  	FROM ERP_HOADON_SP HDSP   " +  
				   "  	INNER JOIN ERP_HOADON HD ON HD.PK_SEQ =HDSP.HOADON_FK    " +  
				   "  	INNER JOIN ERP_SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ    " +  
				   "  	LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK " +  
				   "  	WHERE HDSP.HOADON_FK =   " + this.hdtcId+  
				   "  	AND HDSP.SOLUONG > 0 " +  
				   "  	GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK " +  
				   "  )  " +  
				   "  HOA_DON LEFT JOIN  " +  
				   "  (  " +  
				   "  	SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  " +  
				   "  	FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  " +  
				   "  	WHERE B.TRANGTHAI IN ( 1 ) AND B.HOADON_FK IN (  "+this.hdtcId+")   " +  
				   "  	GROUP BY A.SANPHAM_FK  " +  
				   "  )  " +  
				   "  XUATKHO ON HOA_DON.SPID = XUATKHO.SANPHAM_FK  " +  
				   " where  HOA_DON.SOLUONG - ISNULL(XUATKHO.TOTALXUAT,0)  > 0 ";
		     
		     	//System.out.println("query:"+query);
		     	
				     ResultSet rscheck=db.get(query);
				     if(!rscheck.next()){
						query = "update ERP_HOADON set trangthai = '5' where pk_seq = '" + this.hdtcId + "'";
						if(!this.db.update(query))
						{
							this.msg = "1.Khong the cap nhat trang thai Hoa don: " + query;
							db.getConnection().rollback();
							return false;
						}
				     }
				     rscheck.close();
				
			}
			
			//NẾU LÀ PHIẾU XUẤT TRẢ HÀNG NCC
			if(this.trahangNccId != null && this.trahangNccId.trim().length()>0  )
			{
				//TRẠNG THÁI ĐÃ XUẤT KHO
				query = "update ERP_MUAHANG set trangthai = '2' where pk_seq = '" + this.trahangNccId + "'";
				if(!this.db.update(query))
				{
					this.msg = "1.Khong the cap nhat trang thai ERP_MUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				
			}else{
				if(this.hdtcId!=null&& this.hdtcId.length() >0){
						query=  " update ERP_DONDATHANG set TRANGTHAI='6' where PK_SEQ in  " +
								" ( select hd_dh.DDH_FK from ERP_XUATKHO xk inner join ERP_HOADON_DDH hd_dh on xk.HOADON_FK=hd_dh.HOADON_FK where xk.PK_SEQ= "+this.id+")";
						
						if(!this.db.update(query))
						{
							this.msg = "1.Khong the cap nhat trang thai ERP_MUAHANG: " + query;
							db.getConnection().rollback();
							return false;
						}
				}else{
						// phân biệt trường hợp xuất hàng bán và hàng khuyến mãi,xuât hàng bán thì xem đơn hàng có khuyến mãi không,và nếu có khuyến mãi thì xuất hóa đơn chưa?
					
					if(this.checkHoanTatPXK(this.id)){
							//nếu có thì hoàn tất xuất kho cho đơn đặt hàng
							query=  " update ERP_DONDATHANG set TRANGTHAI='6', IS_HOANTATXK='1' where PK_SEQ in  " +
									" ( select xk.dondathang_fk from ERP_XUATKHO xk   where xk.PK_SEQ= "+this.id+")";
						}else{
							query=  " update ERP_DONDATHANG set TRANGTHAI='6', IS_HOANTATXK='0' where PK_SEQ in  " +
									" ( select xk.dondathang_fk from ERP_XUATKHO xk   where xk.PK_SEQ= "+this.id+")";
						}
						 
						if(!this.db.update(query))
						{
							this.msg = "1.Không thể cập nhật" + query;
							db.getConnection().rollback();
							return false;
						}
					}
					
			}
			
			 
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		 
			return true;
		} 
		catch (Exception e)
		{ 	
			e.printStackTrace();
			db.update("rollback");
			this.msg = e.getMessage(); 
			return false;
		}
	}
	
	 

	private boolean checkHoanTatPXK(String id2) {
		// TODO Auto-generated method stub
		try{
			// kiểm tra hàng khuyến mãi đã xuất kho hết chưa
			String query=" SELECT DH.DONDATHANG_FK,DH.COUNT -ISNULL(XK.COUNT,0) FROM ( "+
				" SELECT   DONDATHANGID AS DONDATHANG_FK,COUNT( DISTINCT KHOTT_FK)  AS COUNT "+ 
				" FROM ERP_DONDATHANG_CTKM_TRAKM  "+
				" WHERE DONDATHANGID =(SELECT DONDATHANG_FK FROM ERP_XUATKHO XK WHERE XK.PK_SEQ="+this.id+" ) "+ 
				" AND SPMA IS NOT NULL "+
				" GROUP BY DONDATHANGID "+
				" ) DH LEFT JOIN "+ 
				" ( "+
				" SELECT DONDATHANG_FK,COUNT(XK.KHO_FK)  AS COUNT "+
				" FROM ERP_XUATKHO XK WHERE XK.DONDATHANG_FK= (SELECT DONDATHANG_FK FROM ERP_XUATKHO XK WHERE XK.PK_SEQ="+this.id+" ) "+
				" AND XK.TRANGTHAI NOT IN ('2','0') and XK.NOIDUNGXUAT=100008 "+
				" GROUP BY XK.DONDATHANG_FK "+
				" )XK ON DH.DONDATHANG_FK=XK.DONDATHANG_FK "+
				" WHERE DH.COUNT -  ISNULL(XK.COUNT,0)> 0"; 
			ResultSet rs=db.get(query);
			if(rs.next()){
				return false;
			}
			
			// kiểm tra hàng bán đã xuất kho hết chưa
			
			query=" SELECT DH.DONDATHANG_FK,DH.COUNT -ISNULL(XK.COUNT,0) FROM ( "+
				" SELECT   DONDATHANG_FK,COUNT( DISTINCT KHOTT_FK)  AS COUNT FROM ERP_DONDATHANG_SP  "+
				" WHERE DONDATHANG_FK =(SELECT DONDATHANG_FK FROM ERP_XUATKHO XK WHERE XK.PK_SEQ="+this.id+" ) "+
				" GROUP BY DONDATHANG_FK "+
				" ) DH LEFT JOIN "+ 
				" ( "+
				" SELECT DONDATHANG_FK,COUNT(XK.KHO_FK)  AS COUNT "+
				" FROM ERP_XUATKHO XK WHERE XK.DONDATHANG_FK= (SELECT DONDATHANG_FK FROM ERP_XUATKHO XK WHERE XK.PK_SEQ="+this.id+" ) "+
				" AND XK.TRANGTHAI NOT IN ('2','0') and XK.NOIDUNGXUAT in (100008,100002,100027,100028,100029) "+
				" GROUP BY XK.DONDATHANG_FK "+
				" )XK ON DH.DONDATHANG_FK=XK.DONDATHANG_FK "+
				" WHERE DH.COUNT -  ISNULL(XK.COUNT,0)> 0 ";
			
			rs=db.get(query);
			if(rs.next()){
				return false;
			}
			rs.close();
			
			return true;
			 
		}catch(Exception er){
			
			er.printStackTrace();
			return false;
		}
	}


	public String getNPP(String soId){
		String npp = "";
		String query = 	"SELECT NPP.TEN " +
						"FROM NHAPHANPHOI NPP " +
						"INNER JOIN DONDATHANG DDH ON DDH.NPP_FK = NPP.PK_SEQ " +
						"WHERE DDH.PK_SEQ = " + soId + " ";
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				npp = rs.getString("TEN");
				return npp;
			}catch(java.sql.SQLException e){}
		}
		return npp;
	}
	
	public boolean isQuanlylo() 
	{
		return this.quanlylo;
	}

	public void setQuanlylo(boolean quanlylo)
	{
		this.quanlylo = quanlylo;
	}

	public boolean isQuanlybean() 
	{
		return this.quanlybean;
	}

	public void setQuanlybean(boolean quanlybean) 
	{
		this.quanlybean = quanlybean;
	}

	
	public String getNccId() 
	{
		return this.nccId;
	}

	
	public void setNccId(String nccid) 
	{
		this.nccId = nccid;	
	}

	public ResultSet getNccList() 
	{
		return this.nccRs;
	}

	public void setNccList(ResultSet nccList) 
	{
		this.nccRs = nccList;
	}

	public String getTrahangNccId() 
	{
		return this.trahangNccId;
	}
	
	public void setTrahangNccId(String trahangid)
	{
		this.trahangNccId = trahangid;
	}

	public ResultSet getTrahangList()
	{
		return this.trahangNccRs;
	}

	public void setTrahangList(ResultSet trahangList) 
	{
		this.trahangNccRs = trahangList;
	}
	
	public String getNgaychotNV() 
	{
		return this.ngaychotNV;
	}

	public void setNgaychotNV(String ngaychotNV) 
	{
		this.ngaychotNV = ngaychotNV;
	}

	
	public void setNppTen(String nppTen) {
		this.nppTen=nppTen;
		
	}

	
	public String getNppTen() {
	
		return this.nppTen;
	}

	public void DBClose(){
		try{
			if(this.ddhRs != null) this.ddhRs.close();
			if(this.ddhRs2 != null) this.ddhRs2.close();
			if(this.khoRs != null) this.khoRs.close();
			if(this.nccRs != null) this.nccRs.close();
			if(this.ndxRs != null) this.ndxRs.close();
			if(this.nppRs != null) this.nppRs.close();
			if(this.hdtcList != null) this.hdtcList.close();
			db.shutDown();
		}catch(java.sql.SQLException e){}
	}



	public ResultSet getHDTCList() {
		return this.hdtcList;
	}



	public String getHDTCId() {
		return this.hdtcId;
	}



	public void setHDTCId(String hdtcId) {
		this.hdtcId = hdtcId;
	}



	public void setNppIdKhoId(String hdId) {
		String query = 	"select KHACHHANG_FK, KHO_FK from ERP_HOADON Where PK_SEQ = '" + hdId + "' ";
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				this.nppId = rs.getString("KHACHHANG_FK");
				this.khoId = rs.getString("KHO_FK");
			}catch(java.sql.SQLException e){}
		}
	}



	public boolean isDataoPXK() {
		
		
		if(this.hdtcId != null && this.hdtcId.length()>0)
		{
			
 String  query=" SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD, ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT     " +  
			   "  FROM  " +  
			   "  (  " +  
			   "  	SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,  " +  
			   "  	CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG2/QC.SOLUONG1)  END AS SOLUONG  " +  
			   "  	FROM ERP_HOADON_SP HDSP   " +  
			   "  	INNER JOIN ERP_HOADON HD ON HD.PK_SEQ =HDSP.HOADON_FK    " +  
			   "  	INNER JOIN ERP_SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ    " +  
			   "  	LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK " +  
			   "  	WHERE HDSP.HOADON_FK =   " + this.hdtcId+  
			   "  	AND HDSP.SOLUONG > 0 " +  
			   "  	GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK " +  
			   "  )  " +  
			   "  HOA_DON LEFT JOIN  " +  
			   "  (  " +  
			   "  	SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  " +  
			   "  	FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  " +  
			   "  	WHERE B.TRANGTHAI IN ( 0,1 ) AND B.HOADON_FK IN (  "+this.hdtcId+")   " +  
			   "  	GROUP BY A.SANPHAM_FK  " +  
			   "  )  " +  
			   "  XUATKHO ON HOA_DON.SPID = XUATKHO.SANPHAM_FK  " +  
			   " where  HOA_DON.SOLUONG - ISNULL(XUATKHO.TOTALXUAT,0)  > 0 ";
			
			ResultSet rsCheck = db.get(query);
			 
			
			try {
				if(!rsCheck.next())
				{
					rsCheck.close();
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}



	public String getIsKhoXuatQuanLyKV() {
		
		return this.IsKhoXuatQL_Khuvuc;
	}



	public float getSoLuongXuat() {
		
		return this.soluongxuat;
	}



	public void setSoLuongXuat(float soluongxuat) {
		
		this.soluongxuat=soluongxuat;
	}


	@Override
	public String getLoaixuatkho() {
		// TODO Auto-generated method stub
		return this.Loaixuatkho;
	}


	@Override
	public void setLoaixuatkho(String loaixuatkho) {
		// TODO Auto-generated method stub
		this.Loaixuatkho=loaixuatkho;
	}


	@Override
	public ResultSet getRsDonhang() {
		// TODO Auto-generated method stub
		return this.RsDondathang;
	}


	@Override
	public void setRsRsDonhang(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsDondathang=rs;
	}


	@Override
	public String getDDHId() {
		// TODO Auto-generated method stub
		return this.DdhId;
	}


	@Override
	public void setDDHId(String dhid) {
		// TODO Auto-generated method stub
		this.DdhId=dhid;
	}


	@Override
	public void InitXuatKho_From_DDH() {
		// TODO Auto-generated method stub
		try{
			
			String query="select pk_seq,khachhang_fk from erp_dondathang where pk_seq="+this.DdhId;
			this.Loaixuatkho="DH";
			ResultSet rs=db.get(query);
			if(rs.next()){
				this.nppId=rs.getString("khachhang_fk");
			}
			rs.close();
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}
}

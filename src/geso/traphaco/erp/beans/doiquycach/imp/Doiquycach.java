package geso.traphaco.erp.beans.doiquycach.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doiquycach.IDoiquycach;
import geso.traphaco.erp.beans.doiquycach.ISpDoiquycach;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Utility_Kho;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Doiquycach implements IDoiquycach {
	String Id;
	String ctyId;
	String ngay;
	String khoId;
	String spId1;
	String loaisp;
	String spTen1;
	String donvitinh1;
	String SpMa;
	
	String soluong1;
	String dongia1;
	String chiphi1;
	String tonggiatri1;
	String tongsoluong2;
	String userId;
	String lspId;
	String[] dvt2;
	String[] spId2;
	String[] spMa2;
	String[] spTen2;
	String[] soluong2;
	String[] dongia2;
	String[] phanbo2;
	String[] tonggiatri2;
	
	String tongsoluong;
	String tongsotien;
	String doiquycach;
	String xuatkhoId;
	
	String msg;
	String Ghichu;
	dbutils db;
	ResultSet khoRs;
	private String iskhuvuc;
	private ResultSet khuvuc_lo;
	String solo1;
	String khuvucid;
	String chiphikho="0";
	
	List<ISpDetail> spDetailList;
	List<ISpDoiquycach> spDoiquycachlist;
 
	Utility_Kho util_kho=new Utility_Kho();
	
	public Doiquycach()
	{   
		this.ctyId = "";
		this.Id ="";
		this.Ghichu="";
		this.ngay = "";
		this.khoId = "";
		this.spId1 = "";
		this.spTen1 = "";
		this.SpMa="";
		this.donvitinh1="";
		this.lspId = "";
		this.soluong1 = "0";
		this.dongia1 = "0";
		this.tonggiatri1 = "0";
		this.chiphi1 = "0";
		chiphikho="0";
		this.spId2 = new String[15];
		this.spMa2 = new String[15];
		this.spTen2 = new String[15];
		this.tonggiatri2 = new String[15];
		this.soluong2 = new String[15];
		this.dongia2 = new String[15];
		this.phanbo2 = new String[15];
		this.dvt2 = new String[15];
		this.iskhuvuc = "0";
		this.tongsoluong = "";
		this.tongsotien = "";
		this.msg="";
		this.solo1 = "";
		this.khuvucid = "";
		this.db = new dbutils();
		this.spDetailList = new ArrayList<ISpDetail>();	
		this.spDoiquycachlist=new ArrayList<ISpDoiquycach>();
		this.doiquycach="";
		this.xuatkhoId="";
	}
	public Doiquycach(String Id)
	{   
		this.Id = Id;
		this.ctyId = "";
		this.ngay = "";
		this.khoId = "";
		this.spId1 = "";
		this.spTen1 = "";
		this.soluong1 = "0";
		this.dongia1 = "0";
		chiphikho="0";
		this.lspId = "";
		this.chiphi1 = "0";
		this.tonggiatri1 = "0";		
		this.spId2 = new String[15];
		this.dvt2 = new String[15];
		this.spMa2 = new String[15];
		this.spTen2 = new String[15];
		this.tonggiatri2 = new String[15];
		this.soluong2 = new String[15];
		this.dongia2 = new String[15];
		this.phanbo2 = new String[15];
		
		this.tongsoluong = "";
		this.tongsotien = "";
		this.msg="";
		this.db = new dbutils();
		this.doiquycach="";
		this.xuatkhoId="";
	}
	
	
	public void set_xuatkhoId_DQC(String xuatkhoId){
		this.xuatkhoId = xuatkhoId;
	}
	public String get_xuatkhoId_DQC(){
		return this.xuatkhoId;
	}
	
	
	public void set_xuatkho_doiquycach(String doiquycach){
		this.doiquycach= doiquycach;
	}
	public String get_xuatkho_doiquycach(){
		return this.doiquycach;
	}
	

	public void setId(String Id) {
		this.Id = Id;
		
	}
	
	public String getId() {
		
		return this.Id;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
		
	}

	public String getNgay() {
		return this.ngay;
	}
	
	public void setKhoId(String khoId) {
		this.khoId = khoId;
		
	}

	public String getKhoId() {
		
		return this.khoId;
	}

	public void setLspId(String lspId) {
		this.lspId = lspId;
		
	}

	public String getLspId() {
		return this.lspId;
	}

	public void setSpId1(String spId1)
	{
		System.out.println("___ID S{: " + spId1);
		if(spId1.length() > 0)
		{
			try
			{
				String query = 	"SELECT SP.PK_SEQ AS SPID, LSP.MA AS LSP, LSP.PK_SEQ AS LSPID " +
								"FROM ERP_SANPHAM SP " +
								"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
								"WHERE SP.PK_SEQ = '" + spId1 + "' ";
				
				System.out.println("___LAY ID: " + query);
				ResultSet rs = this.db.get(query);
				if (rs != null)
				{
					if(rs.next())
					{
						this.spId1 = rs.getString("SPID");
						this.loaisp = rs.getString("LSP");
						this.lspId = rs.getString("LSPID");
					}
					rs.close();
				}
			}
			catch(Exception e)
			{
				System.out.println("__Exception lay SP ID: " + e.getMessage());
			}
		}
	}

	public String getSpId1() {
		return this.spId1;
	}
	
	public void setSpTen1(String spTen1) {
		this.spTen1 = spTen1;
		
	}

	public String getSpTen1() {
		return this.spTen1;
	}

	public void setSoluong1(String soluong1) {
		this.soluong1 = soluong1;
	}

	public String getSoluong1() {
		return this.soluong1;
	}

	
	public void setTonggiatri1(String tonggiatri1) {
		this.tonggiatri1 = tonggiatri1;
		
	}

	public String getTonggiatri1() {
		return this.tonggiatri1;
	}
	
	public void setSpId2(String[] spId2) {
		this.spId2 = spId2;
		
	}

	public String[] getSpId2() {
		return this.spId2;
	}
	
	public void setSpTen2(String[] spTen2) {
		this.spTen2 = spTen2;
		
	}

	public String[] getSpTen2() {
		return this.spTen2;
	}

	public void setSoluong2(String[] soluong2) {
		this.soluong2 = soluong2;
		
	}

	public String[] getSoluong2() {
		return this.soluong2;
	}

	
	public void setTonggiatri2(String[] tonggiatri2) {
		this.tonggiatri2 = tonggiatri2;
		
	}

	public String[] getTonggiatri2() {
		return this.tonggiatri2;
	}

	public void setKhoRs(ResultSet khoRs) {
		
		this.khoRs = khoRs;
	}
	
	public ResultSet getKhoRs() {
		
		return this.khoRs;
	}
	

	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	
	public String getUserId() {
		
		return this.userId;
	}

	public void init() {
		
		if(this.Id.length()>0)
		{
			try{
				String query = 	" SELECT  ISNULL(XDQC.GHICHU,'') AS GHICHU ,XDQC.NGAY, XDQC.KHO_FK,XDQC.TRANGTHAI ,  "+
								"  XDQC.CHIPHIKHAC, XDQC.CHIPHIKHO   " +
								" FROM ERP_XUATDOIQUYCACH XDQC " +
								" WHERE XDQC.PK_SEQ = " + this.Id + "";
				
				//System.out.println(query);
				ResultSet rs = this.db.get(query);
				rs.next();
				this.ngay = rs.getString("NGAY");
				this.khoId = rs.getString("KHO_FK");
				this.Ghichu=rs.getString("GHICHU");
				 
				String trangthai=rs.getString("trangthai");
				this.chiphi1 = rs.getString("CHIPHIKHAC");
				this.chiphikho=rs.getString("CHIPHIKHO");
				rs.close();
				query = " SELECT isnull(DQC.khuvuc_fk,0) as khuvuc_fk, DQC.SANPHAM_FK, SP.ten   AS TEN,SP.ma,dvdl.donvi , DQC.SOLUONG, DQC.DONGIA, DQC.TONGGIATRI, DQC.PHANBOCP, " +
						" DVDL.donvi FROM ERP_NHANDOIQUYCACH DQC " +
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DQC.SANPHAM_FK " +
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						" WHERE DOIQUYCACH_FK = " + Id + " order by DQC.PK_SEQ";
				rs = this.db.get(query);
				System.out.println("Query CT: \n" + query);
				int i = 0;
				
				while(rs.next()){
					this.spId2[i] = rs.getString("SANPHAM_FK");
					this.spMa2[i] = rs.getString("ma");
					this.spTen2[i] = rs.getString("TEN");
					this.dvt2[i] = rs.getString("donvi");
					this.tonggiatri2[i] = rs.getString("TONGGIATRI"); 
					this.soluong2[i] = rs.getString("SOLUONG");
					this.dongia2[i] = rs.getString("DONGIA");
					this.phanbo2[i] = rs.getString("PHANBOCP");
					this.khuvucid=rs.getString("khuvuc_fk");
					i++;
				}
				
				if(i < 15){
					for (int n = i; n < 15; n++){
						this.spId2[n] = "";
						this.spMa2[n]="";
						this.dvt2[n]="";
						this.spTen2[n] = "";
						this.tonggiatri2[n] = "0"; 
						this.soluong2[n] = "0";
						this.phanbo2[n] = "0";
						this.dongia2[n] = "0";
					}
				}
				
				
				this.iskhuvuc = util_kho.getIsQuanLyKhuVuc(this.khoId, this.db);
				
				query=" SELECT DOIQUYCACH_FK,SANPHAM_FK,SOLUONG,DONGIA,TONGGIATRI ,sp.ma,sp.ten ,isnull(dvdl.donvi,'') as donvi " +
					  " FROM ERP_XUATDOIQUYCACH_SANPHAM dqcsp inner join erp_sanpham sp on sp.pk_seq=dqcsp.sanpham_fk " +
					  " left join donvidoluong dvdl on dvdl.pk_seq=sp.dvdl_fk  " +
					  " WHERE DOIQUYCACH_FK = "+this.Id;
				ResultSet rssp=db.get(query);
				this.spDoiquycachlist=new ArrayList<ISpDoiquycach>();
				while (rssp.next()){
					ISpDoiquycach spdqc=new SpDoiquycach();
					spdqc.setSpId1(rssp.getString("SANPHAM_FK"));
					spdqc.setMa(rssp.getString("ma"));
					spdqc.setSpTen1(rssp.getString("ten"));
					spdqc.setdonvitinh(rssp.getString("donvi"));
					spdqc.setDongia1(rssp.getDouble("dongia"));
					spdqc.setChiphi1(rssp.getDouble("TONGGIATRI"));
					spdqc.setSoluong1(rssp.getDouble("soluong"));
					
					
					if(trangthai.equals("0")){
				query=  " SELECT   NGAYBATDAU,NGAYSANXUAT,NGAYHETHAN, AVAILABLE , SOLO, KV.PK_SEQ AS KVKHOID, KV.TEN AS KVKHO, \n" +
						" isnull( ( SELECT XCT.SOLUONG FROM ERP_XUATDOIQUYCACH_CHITIET XCT   \n" +
						"  INNER JOIN ERP_XUATDOIQUYCACH DQC ON DQC.PK_SEQ=XCT.DOIQUYCACH_FK  \n" +
						"  WHERE XCT.NGAYBATDAU=a.ngaybatdau AND  rtrim(ltrim(XCT.SOLO)) = a.solo  AND XCT.SANPHAM_FK= a.sanpham_fk AND  DQC.KHO_FK="+this.khoId+" AND DQC.PK_SEQ="+this.Id+
						" ),0) AS SOLUONG  \n" + 
						" FROM ERP_KHOTT_SP_CHITIET A   \n" +
						" LEFT  JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ = A.KHUVUCKHO_FK  \n" + 
						" WHERE A.SANPHAM_FK = "+rssp.getString("SANPHAM_FK")+"   AND A.KHOTT_FK = "+this.khoId+"  and A.SOLUONG >0  \n" + 
						" ORDER BY NGAYHETHAN asc";
					}else{
				
				query  ="  select  NGAYBATDAU,'' as NGAYSANXUAT , NGAYHETHAN,  0 as AVAILABLE ,  \n" +
						" XCT.SOLO, XCT.KHUVUC_FK  AS KVKHOID,  (select a.DIENGIAI from ERP_KHUVUCKHO a where a.PK_SEQ=XCT.KHUVUC_FK ) as KVKHO,   \n" +
						" XCT.SOLUONG   \n" +
						" FROM ERP_XUATDOIQUYCACH_CHITIET XCT    \n" +
						"  INNER JOIN ERP_XUATDOIQUYCACH DQC ON DQC.PK_SEQ=XCT.DOIQUYCACH_FK    \n" +
						" where DQC.KHO_FK="+this.khoId+"   AND DQC.PK_SEQ="+this.Id+  " and  XCT.SANPHAM_FK="+rssp.getString("SANPHAM_FK") ;  
				
					}
				
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
								splo.setNgaybatdau(rsSpDetail.getString("ngaybatdau"));
								double avai =  rsSpDetail.getDouble("AVAILABLE") +  rsSpDetail.getDouble("soluong");
								splo.setSoluong(rsSpDetail.getString("soluong"));
								splo.setSoluongton(""+avai);
								spDetail.add(splo);
							}
				
							rsSpDetail.close();
						}
						spdqc.setSpDetailList(spDetail);
						this.spDoiquycachlist.add(spdqc);
						
				}
				rssp.close();
			}catch(Exception e){ 
				e.printStackTrace();
			}
		}else{
			for (int n = 0; n < 15; n++){
				this.spId2[n] = "";
				this.spMa2[n] = "";
				this.spTen2[n] = "";
				this.dvt2[n]="";
				this.tonggiatri2[n] = "0"; 
				this.soluong2[n] = "0";
				this.phanbo2[n] = "0";
				this.dongia2[n] = "0";
				
			}
		
		}
		 
		this.createRs();
	}
   
	
	
	public void setmsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getmsg() {
		
		return this.msg;
	}
	
	public void Xoa()
	{
		
		try{
			db.getConnection().setAutoCommit(false);
			
			String	query = " SELECT DQC_CT.NGAYBATDAU ,DQC_CT.KHUVUC_FK,DQC_CT.SANPHAM_FK, DQC_CT.SOLUONG, DQC_CT.SOLO, DQC.KHO_FK " +
			" FROM ERP_XUATDOIQUYCACH_CHITIET DQC_CT " +
			" INNER JOIN ERP_XUATDOIQUYCACH DQC ON DQC.PK_SEQ = DQC_CT.DOIQUYCACH_FK " +
			" WHERE DOIQUYCACH_FK = " + this.Id + " and trangthai='0' ";
 
				ResultSet rs = this.db.get(query);
	//double tmp = 0;
				if (rs != null)
				{
					while(rs.next())
					{								  
						String khott_fk=rs.getString("KHO_FK");
						double soluongct=0;
						double booked=rs.getDouble("SOLUONG")*(-1);
						double available=rs.getDouble("SOLUONG");;
						String sanpham_fk=rs.getString("SANPHAM_FK");
						String solo1=rs.getString("SOLO");
						String khuid=(rs.getString("KHUVUC_FK")==null?"":rs.getString("KHUVUC_FK"));
						if(util_kho.getIsQuanLyKhuVuc(khott_fk, db).equals("1")){
							if(khuid.equals("")){
								this.msg = "Không thể thực hiện nghiệp vụ này,không thể xác định khu vực của các sản phẩm đã đem rã trong phiếu đem sửa";
								this.db.getConnection().rollback();
								return ;
							}
						}else{
							khuid="";
						}
						
						String ngaybatdau_=rs.getString("NGAYBATDAU");
						// cập nhật kho chi tiết
						String msg1= util_kho.Update_Kho_Sp (this.db,khott_fk,sanpham_fk,soluongct, booked,available,0);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return ;
						}
					 
						String vitri="100000";
						msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,khott_fk,sanpham_fk,soluongct, booked,available,0,solo1,vitri,khuid,ngaybatdau_);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return;
						}
						
						if(util_kho.IsKhoQuanLyTrangThai(khott_fk, db)){
							this.msg = "Vui lòng báo admin để được xử lý trong trường hợp này,kho xử lý không được rã đổi quy cách";
							this.db.getConnection().rollback();
							return;
						}
	 
					}
					rs.close();
				}						 
			 
				query = " UPDATE ERP_XUATDOIQUYCACH SET TRANGTHAI=2,NGUOISUA="+this.userId+",ngaysua=getdate() WHERE PK_SEQ = " + this.Id + " ";
				if(!this.db.update(query))
				{
					this.msg = "3.Không thể cập nhật: " + query;
					db.getConnection().rollback();
					return;
				}	
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception err){
			db.update("rollback");
			return ;
		}
		
	}
	
	public boolean Hoantat()
	{
		try{
			Utility util=new Utility();
			
			String msg1="";
			
			this.db.getConnection().setAutoCommit(false);
			String query;
			String ngayhethan="";
			 	query = " SELECT  DQC.NGAY,DQC_CT.NGAYHETHAN , DQC_CT.NGAYBATDAU ,DQC_CT.KHUVUC_FK,DQC_CT.SANPHAM_FK, DQC_CT.SOLUONG, DQC_CT.SOLO, DQC.KHO_FK " +
						" FROM ERP_XUATDOIQUYCACH_CHITIET DQC_CT " +
						" INNER JOIN ERP_XUATDOIQUYCACH DQC ON DQC.PK_SEQ = DQC_CT.DOIQUYCACH_FK " +
						" WHERE DOIQUYCACH_FK = " + this.Id + " and DQC.trangthai='0' ";
 
				ResultSet rs = this.db.get(query);
				if (rs != null)
				{
					while(rs.next())
					{				
						this.ngay=rs.getString("NGAY");
						String khott_fk=rs.getString("KHO_FK");
						double soluongct=rs.getDouble("SOLUONG")*(-1);
						double booked=rs.getDouble("SOLUONG")*(-1); ;
						double available=0;;
						String sanpham_fk=rs.getString("SANPHAM_FK");
						String solo1=rs.getString("SOLO");
						String khuid=(rs.getString("KHUVUC_FK")==null?"":rs.getString("KHUVUC_FK"));
						if(util_kho.getIsQuanLyKhuVuc(khott_fk, db).equals("1")){
							if(khuid.equals("")){
								this.msg = "Không thể thực hiện nghiệp vụ này,không thể xác định khu vực của các sản phẩm đã đem rã trong phiếu đem sửa";
								this.db.getConnection().rollback();
							}
						}else{
								khuid="";
						}
						ngayhethan=rs.getString("NGAYHETHAN");
						String ngaybatdau_=rs.getString("NGAYBATDAU");
						// cập nhật kho chi tiết
						msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay(this.db,khott_fk,sanpham_fk,soluongct, booked,available,0,this.ngay);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}
						
						String vitri="100000";
						msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,khott_fk,sanpham_fk,soluongct, booked,available,0,solo1,vitri,khuid,ngaybatdau_);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}
						
						if(util_kho.IsKhoQuanLyTrangThai(khott_fk, db)){
							this.msg = "Vui lòng báo admin để được xử lý trong trường hợp này,kho xử lý không được rã đổi quy cách";
							this.db.getConnection().rollback();
							return false;
						}
	 
					}
					rs.close();
				}			
				
				  msg1=util.checkNgayHopLe(this.db, this.ngay);
				
				if(msg1.length() >0){
					this.db.getConnection().rollback();
					this.msg =msg1;
					return false;
				}
				
				
				
				
			query = " SELECT  NQC.KHUVUC_FK , NQC.TONGGIATRI/XQC.SOLUONG AS DONGIAMUA,NQC.TONGGIATRI  , XQC.KHO_FK,NQC.SANPHAM_FK, " +
					" NQC.SOLUONG, '0', NQC.SOLUONG, NQC.SOLO, '100000', XQC.NGAY as NGAYBATDAU, '" + this.getDateTime() + "' " +
					" FROM ERP_NHANDOIQUYCACH NQC " +
					" INNER JOIN ERP_XUATDOIQUYCACH XQC ON XQC.PK_SEQ = NQC.DOIQUYCACH_FK " +
					" WHERE NQC.DOIQUYCACH_FK = " + this.Id + "  AND XQC.TRANGTHAI=0 ";
			rs=this.db.get(query);
//			int i=0;
			
			//tinh giá bình quân của các sản phẩm nhập vào
			
			
			while (rs.next()){
					 //************************************
				double soluongct=rs.getDouble("SOLUONG");
				String sanpham_fk=rs.getString("sanpham_fk");
				String solo=rs.getString("SOLO");
				String KHU=rs.getString("KHUVUC_FK");
				String ngaybatdau=rs.getString("NGAYBATDAU");
				String KHONHAN_FK=rs.getString("KHO_FK");
				 
				if(KHU==null){
					KHU="";
				}
				
				 double CphiCapDong=0;
				 double CphiLuuKho=0;
				 double CphiNhapHang=0;
				 double ThueNhapkhau=0;
				 double dongiamua =rs.getDouble("DONGIAMUA");
				 String ngaysanxuat=ngaybatdau;
				 
				 if(util_kho.getIsQuanLyKhuVuc(KHONHAN_FK,db).equals("1")){
					 if(KHU.equals("")){
						 this.msg="kho nhận có quản lý theo khu.vui lòng xác nhận khu của những sản phẩm rã quy cách";
					 }
				 }
				   
				   msg1= util_kho.Update_Kho_Sp( db, KHONHAN_FK,sanpham_fk,soluongct,0,soluongct,0);
				 if(msg1.length() >0){
					 this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
				 }
			 
				 msg1= util_kho.Update_Kho_Sp_Chitiet( db,KHONHAN_FK,sanpham_fk,soluongct,0,soluongct,dongiamua,solo,"",KHU,ngaybatdau,ngaybatdau,ngaysanxuat,ngayhethan,CphiCapDong,CphiLuuKho,CphiNhapHang,ThueNhapkhau);
				 if(msg1.length() >0){
					 	this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
				 }
				 
				 // số lượng đạt
				 //trangthai=0 là hàng đạt
				 if(util_kho.IsKhoQuanLyTrangThai(KHONHAN_FK, db)) {
					 this.msg = "Không xử lý kho quản lý trạng thái trong trường hợp này, vui lòng báo Admin để được xử lý";
						this.db.getConnection().rollback();
						return false;
				 }
						///**************
					
			}
			rs.close();
			query = "UPDATE ERP_XUATDOIQUYCACH SET TRANGTHAI = 1,nguoiduyet="+this.userId+",ngayduyet=getdate() WHERE PK_SEQ = " + this.Id + "";

			if(!this.db.update(query)){
				this.msg="Error :"+query;
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
			
		}catch(Exception e){
			this.db.update("rollback");
			this.msg=e.getMessage();
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void setSpId2(HttpServletRequest request)
	{
		 
	}
	
	public void setSoluong2(HttpServletRequest request){
	 
	}

	public void Tinhgiatri1()
	{
//		String solo = "";
		String query = "";
		ResultSet rs;
		try
		{
			
			if(this.ngay==null||  this.ngay.equals("")){
				this.msg="Vui lòng chọn ngày chứng từ ";
				return ;
			}
			
			
			db.getConnection().setAutoCommit(false);
			 
			if(this.spDoiquycachlist.size()>0 && this.khoId.length() > 0)
			{
				if(this.Id.length() > 0)
				{
					
					
					query = " SELECT DQC_CT.NGAYBATDAU ,DQC_CT.KHUVUC_FK,DQC_CT.SANPHAM_FK, DQC_CT.SOLUONG, DQC_CT.SOLO, DQC.KHO_FK " +
							" FROM ERP_XUATDOIQUYCACH_CHITIET DQC_CT " +
							" INNER JOIN ERP_XUATDOIQUYCACH DQC ON DQC.PK_SEQ = DQC_CT.DOIQUYCACH_FK " +
							" WHERE DOIQUYCACH_FK = " + this.Id + " ";
				 
					rs = this.db.get(query);
					//double tmp = 0;
				 
						while(rs.next())
						{								  
							String khott_fk=rs.getString("KHO_FK");
							double soluongct=0;
							double booked=rs.getDouble("SOLUONG")*(-1);
							double available=rs.getDouble("SOLUONG");;
							String sanpham_fk=rs.getString("SANPHAM_FK");
							String solo1=rs.getString("SOLO");
							String khuid=(rs.getString("KHUVUC_FK")==null?"":rs.getString("KHUVUC_FK"));
							if(util_kho.getIsQuanLyKhuVuc(khott_fk, db).equals("1")){
								if(khuid.equals("")){
									this.msg = "Không thể thực hiện nghiệp vụ này,không thể xác định khu vực của các sản phẩm đã đem rã trong phiếu đem sửa";
									this.db.getConnection().rollback();
									return ;
								}
							}else{
								khuid="";
							}
							
							String ngaybatdau_=rs.getString("NGAYBATDAU");
							// cập nhật kho chi tiết
							String msg1= util_kho.Update_Kho_Sp (this.db,khott_fk,sanpham_fk,soluongct, booked,available,0);
							if(msg1.length() >0){
								this.msg = msg1;
								this.db.getConnection().rollback();
								return ;
							}
						 
							String vitri="100000";
							msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,khott_fk,sanpham_fk,soluongct, booked,available,0,solo1,vitri,khuid,ngaybatdau_);
							if(msg1.length() >0){
								this.msg = msg1;
								this.db.getConnection().rollback();
								return;
							}
							
							if(util_kho.IsKhoQuanLyTrangThai(khott_fk, db)){
								this.msg = "Vui lòng báo admin để được xử lý trong trường hợp này,kho xử lý không được rã đổi quy cách";
								this.db.getConnection().rollback();
								return;
							}
							
							 
						 
							
						}
						rs.close();
					
					
					query = "DELETE ERP_XUATDOIQUYCACH_CHITIET WHERE DOIQUYCACH_FK = " + this.Id + " ";
					if(!this.db.update(query))
					{
						this.msg = "3.Không thể cập nhật: " + query;
						db.getConnection().rollback();
						return;
					}
					query="delete ERP_XUATDOIQUYCACH_SANPHAM where DOIQUYCACH_FK = " + this.Id + " ";
					if(!this.db.update(query))
					{
						this.msg = "3.Không thể cập nhật: " + query;
						db.getConnection().rollback();
						return;
					}
				}
				 
//						String[] dqc = solo.split(";");
						if(this.Id.length() >0){
							query="select pk_seq from ERP_XUATDOIQUYCACH where pk_Seq="+this.Id;
							ResultSet rscheck = db.get(query);
							if (rscheck != null)
							{
								if(!rscheck.next()){
									this.Id="";
								}
								rscheck.close();
							}
						}
						
						if(this.Id.length() == 0)
						{	
							query = "INSERT INTO ERP_XUATDOIQUYCACH(NGAY, KHO_FK,   CHIPHIKHAC, TRANGTHAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,GHICHU,CHIPHIKHO) " +
									"VALUES ('" + this.ngay + "', '" + this.khoId + "',    "+this.chiphi1+" ,'0',GETDATE(),GETDATE(),"+this.userId+","+this.userId+",N'"+this.Ghichu+"',"+this.chiphikho+")" ;
							
							
						//	System.out.println("Insert doi QC: " + query);
							
							if(!this.db.update(query))
							{
								this.msg = "4.Không thể cập nhật: " + query;
								db.getConnection().rollback();
								return;
							}
							
							query = "SELECT SCOPE_IDENTITY() AS ID";
							rs = this.db.get(query); 
							rs.next();
							
							this.Id = rs.getString("ID");
							
						}else{
							query = " UPDATE ERP_XUATDOIQUYCACH SET chiphikho="+this.chiphikho+",GHICHU=N'"+this.Ghichu+"', NGAYSUA=GETDATE(),NGUOISUA="+this.userId+", NGAY = '" + this.ngay + "', KHO_FK = '" + this.khoId + "',   " +
									"   CHIPHIKHAC = " + this.chiphi1 +
									" WHERE PK_SEQ = " + this.Id + " ";
							
							//System.out.println("Cap nhat doi QC: " + query);
							if(!this.db.update(query))
							{
								this.msg = "5.Không thể cập nhật: " + query;
								db.getConnection().rollback();
								return;
							}
						
						} 
						for(int k =0;k<this.spDoiquycachlist.size();k++)
						{
							ISpDoiquycach spdqc=this.spDoiquycachlist.get(k);
							List<ISpDetail> spDetailList_ =spdqc.getSpDetailList();
							
							query="INSERT INTO ERP_XUATDOIQUYCACH_SANPHAM (DOIQUYCACH_FK,SANPHAM_FK,SOLUONG,DONGIA,TONGGIATRI )VALUES " +
								  " ("+this.Id+","+spdqc.getSpId1()+","+spdqc.getSoluong1()+","+spdqc.getDongia1()+","+spdqc.getChiphi1()+") ";
							if(!this.db.update(query))
							{
								this.msg = "5.Không thể cập nhật: " + query;
								db.getConnection().rollback();
								return;
							}
							
							for(int i =0;i<spDetailList_.size();i++)
							{
								 
								ISpDetail spdetail=spDetailList_.get(i);
								
								double soluongct=0;
								try{
								soluongct =Double.parseDouble(spdetail.getSoluong());
								}catch(Exception er){ 					
								}
								double donggia_=0;
								try{
								donggia_ =Double.parseDouble(this.dongia1.replaceAll(",",""));
								}catch(Exception er){ 					
								}
								double thanhtien=donggia_*soluongct;
								if(soluongct >0){
								String khott_fk=this.khoId;
								String khuid=(spdetail.getKhuId()==null?"":spdetail.getKhuId());
								boolean isquanlykhu=false;
								if(util_kho.getIsQuanLyKhuVuc(khott_fk, db).equals("1")){
									isquanlykhu=true;
									if(khuid.equals("")){
										this.msg = "Không thể thực hiện nghiệp vụ này,không thể xác định khu vực của các sản phẩm đã đem rã ";
										this.db.getConnection().rollback();
										return ;
									}
								}else{
									khuid="";
								}
								
								
								query = "INSERT INTO ERP_XUATDOIQUYCACH_CHITIET(DOIQUYCACH_FK, SANPHAM_FK, SOLO, SOLUONG,DONGIA, TONGGIATRI, NGAYHETHAN,NGAYBATDAU,KHUVUC_FK) " +
										"VALUES (" + this.Id + ", " + spdetail.getId() + ", '" + spdetail.getSolo() + "', " + spdetail.getSoluong() + ", " +this.dongia1+ ","+thanhtien+" , '" +spdetail.getNgayhethan() + "','"+spdetail.getNgaybatdau()+"',"+(isquanlykhu ? spdetail.getKhuId():"NULL")+")";
								  
								if(!this.db.update(query))
								{
									this.msg = "6.Không thể cập nhật: " + query;
									db.getConnection().rollback();
									return;
								}
						
								double soluongcapnhat_kho=0;
								double booked=soluongct;
								double available=soluongct*(-1);
								
								String sanpham_fk=spdetail.getId();
								String solo1=spdetail.getSolo();
							
							
								String ngaybatdau_=spdetail.getNgaybatdau();
								// cập nhật kho chi tiết
								String msg1= util_kho.Update_Kho_Sp (this.db,khott_fk,sanpham_fk,soluongcapnhat_kho, booked,available,0);
								if(msg1.length() >0){
									this.msg = msg1;
									this.db.getConnection().rollback();
									return ;
								}
							 
								String vitri="100000";
								msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,khott_fk,sanpham_fk,soluongcapnhat_kho, booked,available,0,solo1,vitri,khuid,ngaybatdau_);
								if(msg1.length() >0){
									this.msg = msg1;
									this.db.getConnection().rollback();
									return;
								}
							
								if(util_kho.IsKhoQuanLyTrangThai(khott_fk, db)){
									
									this.msg = "Vui lòng báo admin để được xử lý trong trường hợp này,kho xử lý không được rã đổi quy cách";
									this.db.getConnection().rollback();
									return;
								}
							}
							 
						}
						}

						query = " DELETE ERP_NHANDOIQUYCACH WHERE DOIQUYCACH_FK = " + this.Id + " ";
						if(!this.db.update(query))
						{
							this.msg = "9.Không thể cập nhật: " + query;
							db.getConnection().rollback();
							return;
						}
						
					
						boolean flag=false;
						boolean isQlKhu=false;
						if(this.util_kho.getIsQuanLyKhuVuc(this.khoId,this.db).equals("1")){
							isQlKhu=true;
							if(this.khuvucid.length()==0){
								this.msg = "Vui lòng chọn khu vực lưu kho cho sản phẩm rã quy cách ";
								db.getConnection().rollback();
								return;
							}
						}
						
						
						for(int i = 0; i < 15; i++){
							if(this.spId2[i].length() > 0 && !this.soluong2[i].equals("0") ){
								if(this.phanbo2[i].equals("0")){

									this.msg = "2. Sản phẩm nhận đổi quy cách không được mang giá trị 0  " + i ;
									db.getConnection().rollback();
									return;
								}
								query = " INSERT INTO ERP_NHANDOIQUYCACH(DOIQUYCACH_FK, SANPHAM_FK, SOLO, SOLUONG, DONGIA, PHANBOCP, TONGGIATRI,KHUVUC_FK) " +
										" VALUES (" + this.Id + ", " + this.spId2[i] + ", '" + "DQC_" + this.spId1 + "_" + this.getDateTime() + "', " +
										" '" + this.soluong2[i] + "', "+this.dongia2[i]+" , "+this.phanbo2[i]+",  " + this.tonggiatri2[i] + ","+(isQlKhu?this.khuvucid:"NULL")+" )";
								 
								if(!this.db.update(query))
								{
									this.msg = "9.Không thể cập nhật: " + query;
									db.getConnection().rollback();
									return;
								}
								
								flag=true;
								
							}else{
								this.tonggiatri2[i] = "0";
							}
						}
						// Không có sản phẩm nhận đổi quy cách
						if(!flag){
							this.msg = "Vui lòng nhập sản phẩm nhận đổi quy cách";
							db.getConnection().rollback();
							return;
						}
						
					}else{
						this.msg = "Vui lòng nhập sản phẩm  đổi quy cách";
						db.getConnection().rollback();
						return;
					}
			  
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception e)
		{
			this.msg="Lỗi : "+e.getMessage();
			db.update("rollback");
			e.printStackTrace();
			 
		}
 
	}
	public void Reload_Sp()
	{
//		String solo = "";
		String query = "";
//		ResultSet rs;
		try
		{
			for(int i=0;i<spDoiquycachlist.size();i++){
				
				ISpDoiquycach spdqc=spDoiquycachlist.get(i);
				
				if(spdqc.getreload_sp().equals("1")){
					 
					
						this.iskhuvuc = util_kho.getIsQuanLyKhuVuc(this.khoId, this.db);
						
						if(spdqc.getSpId1().length()> 0 && this.khoId.length() >0){
							
							query= "SELECT NGAYBATDAU,NGAYSANXUAT,NGAYHETHAN, AVAILABLE, SOLO, KV.PK_SEQ AS KVKHOID, KV.TEN AS KVKHO "+ 
								 " FROM ERP_KHOTT_SP_CHITIET A  "+
								 " LEFT  JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ = A.KHUVUCKHO_FK "+ 
								 " WHERE A.SANPHAM_FK = "+spdqc.getSpId1()+"   AND A.KHOTT_FK = "+this.khoId+"  and A.SOLUONG  > 0 "+ 
								 " ORDER BY NGAYHETHAN asc";
								ResultSet rsSpDetail = this.db.get(query);
								List<ISpDetail> spDetail = new ArrayList<ISpDetail>();	
								while(rsSpDetail.next()){
									ISpDetail  splo =new SpDetail();
									splo.setSolo(rsSpDetail.getString("solo"));
									splo.setKhuId(rsSpDetail.getString("KVKHOID"));
									splo.setKhu(rsSpDetail.getString("KVKHO"));
									splo.setNgaysanxuat(rsSpDetail.getString("ngaysanxuat"));
									splo.setNgayhethan(rsSpDetail.getString("ngayhethan"));
									splo.setNgaybatdau(rsSpDetail.getString("ngaybatdau"));
									double avai =  rsSpDetail.getDouble("AVAILABLE");
									splo.setSoluong("");
									splo.setSoluongton(""+avai);
									spDetail.add(splo);
								}
								rsSpDetail.close();
								spdqc.setSpDetailList(spDetail);
						}
						
				}
			
			}
		}
		catch(Exception e)
		{
			this.msg="Lỗi :" +e.getMessage();
			e.printStackTrace();
			System.out.println("___Exception: " + e.getMessage());
		}
		
		
	}

	
	public boolean save() {
		
		return true;
	}
	
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
	
	public void DbClose() {
		
		try{
			if (this.khuvuc_lo != null)
				this.khuvuc_lo.close();
			if (this.spDetailList != null)
				this.spDetailList.clear();
			if (this.spDoiquycachlist != null)
				this.spDoiquycachlist.clear();
			if(this.khoRs != null) this.khoRs.close();
			
			db.shutDown();
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
	public String getTotalLuong() {
		
		return this.tongsoluong;
	}
	
	public void setTotalLuong(String totalLuong) {
		
		this.tongsoluong = totalLuong;
	}
	
	public String getTotalTien() {
		
		return this.tongsotien;
	}
	
	public void setTotalTien(String totalTien) {
		
		this.tongsotien = totalTien;
	}
	@Override
	public void setghichu(String ghichu) {
		this.Ghichu=ghichu;
	}
	@Override
	public String getghichu() {
		return this.Ghichu;
	}
	@Override
	public String[] getDongia2() {
		return this.dongia2;
	}
	@Override
	public void setDongia2(String[] dongia2) {
		this.dongia2 = dongia2;
	}
	@Override
	public void setDongia2(HttpServletRequest request) {
		
	}
	@Override
	public String getChiphi1() {
		return this.chiphi1;
	}
	@Override
	public void setChiphi1(String value) {
		this.chiphi1 = value;
	}
	@Override
	public String[] getPhanbo2() {
		return this.phanbo2;
	}
	@Override
	public void setPhanbo2(HttpServletRequest request) {
		Utility util = new Utility();
		for(int i = 1; i < 8; i++){
			phanbo2[i-1] = util.antiSQLInspection(request.getParameter("phanbo" + (i + 1))).trim();
			if(phanbo2[i-1] == null || phanbo2[i-1].trim().length() == 0) 
				phanbo2[i-1] = "0";
		}
	}
	@Override
	public String getDongia1() {
		return this.dongia1;
	}
	@Override
	public void setDongia1(String dongia1) {
		this.dongia1 = dongia1;
	}
	@Override
	public void setTonggiatri2(HttpServletRequest request) {
		Utility util = new Utility();
		for(int i = 1; i < 8; i++){
			tonggiatri2[i-1] = util.antiSQLInspection(request.getParameter("giatri" + (i + 1))).trim();
			if(tonggiatri2[i-1] == null || tonggiatri2[i-1].trim().length() == 0) 
				tonggiatri2[i-1] = "0";
		}
	}
	@Override
	public String getIsKhuvuc() {
		return this.iskhuvuc;
	}
	@Override
	public ResultSet getKhuvuc_Lo() {
		return this.khuvuc_lo;
	}
	@Override
	public void setSolo1(String value) {
		this.solo1 = value;
	}
	@Override
	public String getSolo1() {
		return this.solo1;
	}
	@Override
	public void setKhuvucId(String value) {
		this.khuvucid = value;
	}
	@Override
	public String getKhuvucId() {
		return this.khuvucid;
	}
	@Override
	public void setMa(String Ma) {
		// TODO Auto-generated method stub
		this.SpMa=Ma;
	}
	@Override
	public String getMa() {
		// TODO Auto-generated method stub
		return this.SpMa;
	}
	@Override
	public void setdonvitinh(String donvitinh) {
		// TODO Auto-generated method stub
		this.donvitinh1=donvitinh;
	}
	@Override
	public String getdonvitinh() {
		// TODO Auto-generated method stub
		return this.donvitinh1;
	}
	public List<ISpDetail> getSpDetailList() 
	{
		return this.spDetailList;
	}

	public void setSpDetailList(List<ISpDetail> spDetailList) 
	{
		this.spDetailList = spDetailList;
	}
	@Override
	public void createRs() {
		// TODO Auto-generated method stub
		this.khoRs = this.db.get("SELECT PK_SEQ AS KHOID, TEN AS KHO FROM ERP_KHOTT WHERE TRANGTHAI = 1");
		this.iskhuvuc = util_kho.getIsQuanLyKhuVuc(this.khoId, this.db);
		if(this.khoId!=null &&  this.khoId.length() >0){
			this.khuvuc_lo=db.get("SELECT PK_SEQ,TEN FROM ERP_KHUVUCKHO WHERE KHOTT_FK= "+this.khoId);
		}
	}
	@Override
	public void ReLoad_DonGia() {
		// TODO Auto-generated method stub
		try{
			//load chi phí lưu kho
			
			
			
			double totolchiphikhac=0;
			double tonggiatridemra=0;
			for(int k=0;k< this.spDoiquycachlist.size();k++){
				
				
				
				ISpDoiquycach spdqc=spDoiquycachlist.get(k);
				List<ISpDetail> spDetaillist = spdqc.getSpDetailList();
					
					for(int i =0;i< spDetaillist.size();i++)
					{
						 
						ISpDetail spdetail= spDetaillist.get(i);
					
						
						double soluongct=0;
						try{
						soluongct =Double.parseDouble(spdetail.getSoluong());
						}catch(Exception er){ 					
						}
						if(soluongct>0){
							 
							String query=" SELECT NGAYBATDAU,NGAYSANXUAT,NGAYHETHAN,ISNULL(CPCAPDONG,0) + "+ 
							  " ISNULL(CPLUUKHO,0)  + "+
							  " ISNULL(CPNHANHANG,0)  + "+
							  " ISNULL(THUENHAPKHAU,0) AS CHIPHIKHAC"+ 
		
						 	  " FROM ERP_KHOTT_SP_CHITIET WHERE KHOTT_FK="+this.khoId+" AND SANPHAM_FK="+spdetail.getId()+" AND SOLO='"+spdetail.getSolo()+"' AND NGAYBATDAU='"+spdetail.getNgaybatdau()+"' ";
								if(util_kho.getIsQuanLyKhuVuc(this.khoId, db).equals("1")){
									query=query + " AND  KHUVUCKHO_FK ="+ spdetail.getKhuId();
								}
								
								ResultSet rschiphi=db.get(query);
								if(rschiphi!=null){
									if(rschiphi.next()){
										totolchiphikhac=totolchiphikhac+rschiphi.getDouble("CHIPHIKHAC");
									}
									rschiphi.close();
								}
						
							}
						
						}
			
					this.chiphikho= totolchiphikhac+"";
					
					String query="SELECT GIATON FROM ERP_KHOTT_SANPHAM WHERE KHOTT_FK= "+this.khoId+" AND SANPHAM_FK= "+spdqc.getSpId1();
					ResultSet rskho=db.get(query);
					if (rskho != null)
					{
						if(rskho.next()){
							spdqc.setDongia1(rskho.getDouble("GIATON"));
							double soluongct=0;
							try{
								soluongct= spdqc.getSoluong1() ;
							}catch(Exception err){}
							tonggiatridemra=tonggiatridemra + rskho.getDouble("GIATON") *  soluongct; 
							 
							spdqc.setChiphi1(rskho.getDouble("GIATON") *  soluongct);
							
						}
						rskho.close();
					}
			}
			double chiphitmp=0;
			try{
				chiphitmp= Double.parseDouble(this.chiphi1.replace(",",""));
			}catch(Exception er){
				
			}
		    
			 
//			double total_giatridemra=tonggiatridemra +chiphitmp+totolchiphikhac;
			
			//phân bổ lại giá trị còn dư
			double tongtt2=0;
			double totalsoluong=0;
			// lưu dòng cuối là dòng nào,để cập nhật chương trình vào hết dòng đó.
			int bientam=0;
			
			for(int i=0; i <15;i++ ){
				 double soluongct=0;
				 double dongiact=0;
				 try{
					 soluongct=Double.parseDouble(soluong2[i].replaceAll(",",""));
				 }catch(Exception er){}
				 try{
					 dongiact=Double.parseDouble(dongia2[i].replaceAll(",",""));
				 }catch(Exception er){}
				 if(soluongct>0){
				 tongtt2+=soluongct*dongiact;
				 	bientam=i;
				 }
				 totalsoluong=totalsoluong+soluongct;
			}
			
		 
			
			if(this.khuvucid.length()==0){
				for(int k=0;k<this.spDetailList.size();k++){
					ISpDetail sp=this.spDetailList.get(k);
					
					double soluongct=0;
					try{
						soluongct=Double.parseDouble(sp.getSoluong());
					}catch(Exception er){}
					if(soluongct >0){
						this.khuvucid=sp.getKhuId();
					}
					
				}
			}
			
			
		}catch(Exception err){
			err.printStackTrace();
		}
	}
	@Override
	public void setSpMa2(String[] SpMa2) {
		// TODO Auto-generated method stub
		this.spMa2=SpMa2 ;
	}
	@Override
	public String[] getSpMa2() {
		// TODO Auto-generated method stub
		return this.spMa2;
	}
	@Override
	public String[] getDVT2() {
		// TODO Auto-generated method stub
		return this.dvt2;
	}
	@Override
	public void setDVT2(String[] dvt2) {
		// TODO Auto-generated method stub
		this.dvt2=dvt2;
	}
	@Override
	public void setSpNhanDoiQuycach(HttpServletRequest request) {
		// TODO Auto-generated method stub
		try{
		Utility util = new Utility();
		for(int i = 1; i < 16; i++){
			dongia2[i-1] = util.antiSQLInspection(request.getParameter("dongia" + (i + 1)).replaceAll(",","")).trim();
			if(dongia2[i-1] == null || dongia2[i-1].trim().length() == 0) { 
				dongia2[i-1] = "0";
			}
			
			spId2[i-1] = util.antiSQLInspection(request.getParameter("spid" + (i + 1))).trim();
			spMa2[i-1] = util.antiSQLInspection(request.getParameter("spma" + (i + 1))).trim(); 
			spTen2[i-1] = util.antiSQLInspection(request.getParameter("spTen" + (i + 1))).trim();
			
			this.dvt2[i-1] = util.antiSQLInspection(request.getParameter("dvt" + (i + 1))).trim();
			this.soluong2[i-1] = util.antiSQLInspection(request.getParameter("soluong" + (i + 1)).replaceAll(",","")).trim();
			if(soluong2[i-1] == null || soluong2[i-1].trim().length() == 0) { 
				soluong2[i-1] = "0";
			}
			this.dongia2[i-1] = util.antiSQLInspection(request.getParameter("dongia" + (i + 1)).replaceAll(",","")).trim();
			if(dongia2[i-1] == null || dongia2[i-1].trim().length() == 0) { 
				dongia2[i-1] = "0";
			}
			
			System.out.println(" don gia dqc ne :" +  this.dongia2[i-1]);
			
			
			this.phanbo2[i-1] = util.antiSQLInspection(request.getParameter("phanbo" + (i + 1)).replaceAll(",", "")).trim();
			if(phanbo2[i-1] == null || phanbo2[i-1].trim().length() == 0) { 
				phanbo2[i-1] = "0";	
			}
			
			System.out.println(" phanbo dqc ne :" +  this.phanbo2[i-1]);
			
			
			this.tonggiatri2[i-1] = util.antiSQLInspection(request.getParameter("tonggiatri" + (i + 1)).replaceAll(",","")).trim();
			if(tonggiatri2[i-1] == null || tonggiatri2[i-1].trim().length() == 0) { 
				tonggiatri2[i-1] = "0";
			}
			
		}
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	@Override
	public String getChiphikho() {
		// TODO Auto-generated method stub
		return this.chiphikho;
	}
	@Override
	public void setChiphikho(String value) {
		// TODO Auto-generated method stub
		this.chiphikho=value;
	}
	@Override
	public List<ISpDoiquycach> getSpDoiquycachlist() {
		// TODO Auto-generated method stub
		return this.spDoiquycachlist;
	}
	@Override
	public void setSpDoiquycachlist(List<ISpDoiquycach> SpDoiquycachlist) {
		// TODO Auto-generated method stub
		this.spDoiquycachlist=SpDoiquycachlist;
	}
}

 package geso.traphaco.erp.beans.nhanhangkhac.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extentech.formats.XLS.Array;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IDonvi;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.Donvi;
import geso.traphaco.erp.beans.nhanhangkhac.*;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.imp.DetailMeSp;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.imp.KhuVucKho;
import geso.traphaco.erp.util.DinhDang;
import geso.traphaco.erp.util.Library;

public class ErpNhanhang_khac implements IErpNhanhang_khac
{
	String congtyId;
	String userId;
	String id;
	String ngaynhanhang;
	String ngaychot;
	String sohoadon;
	String loaispId="";
	String dvthId;
	String lydo;
	
	int is_saungayKS;
	String ghichu;
	
	ResultSet dvthRs;

	String poId;
	ResultSet poRs;
	String nhomkenhid="100000";
	String ndnId;
	ResultSet ndnRs;
	
	String ldnId;
	ResultSet ldnRs;
	
	String nccId;
	ResultSet nccRs;
	
	ResultSet nhomchiphiRs;
	String nhomchiphiId;




	String mahangmuaId;
	int ngayhethan;
	ResultSet mahangmuaRs;
	
	String diengiai;
	String trangthai;
	String soluongPO;
	String soluongDaNhan;
	String loaihanghoa;
	String tigia;
	String sopoId;
	String muahang_fk1;
	String is_createRs="0";
	
	List<ISanpham> spList;

	String isPONK;
	
	String isNCCNK;
	
	String hdNccId;
	ResultSet hdNccRs;
	
	String loaimh;
	String isTudong; // Phiếu nhận hàng tự động : 0 , ngược lại : 1
	
	String msg;
	String pb;
	boolean IsKhoNhanQL_khuvuc=false;
	dbutils db;
	private Utility util;
	private Utility_Kho util_kho =new Utility_Kho();
	String KHoCxlId;
	ResultSet RsKhoCXL;
	ResultSet hoadonNCCList;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	String nppTen;
	String sitecode;
	String tongtien="";
	String sochungtu;
	String khonhanId;
	 
	List<KhuVucKho> listKhuVucKho = new ArrayList<KhuVucKho>();
	List<KhuVucKho> listKhuVucKhoCXL = new ArrayList<KhuVucKho>();
	

	public String getKhonhanId() {
		return khonhanId;
	}

	public void setKhonhanId(String khonhanId) {
		this.khonhanId = khonhanId;
	}

	public ErpNhanhang_khac()
	{
		this.userId = "";
		this.id = "";
		this.ngaynhanhang = "";
		this.KHoCxlId="";
		this.ngaychot = "";
		this.sohoadon = "";
		this.diengiai = "";
		this.dvthId = "";
		this.poId = "";
		this.mahangmuaId = "";
		this.diengiai = "";
		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		this.loaihanghoa = "0";
		this.loaimh = "";
		this.nhomchiphiId="";
		this.msg = "";
		this.ngayhethan = 0;
		this.ndnId = "";
		this.ldnId = "";
		this.nccId = "";
		this.lydo="";
		
		this.isPONK = "0";
		this.hdNccId = "";
		
		this.isNCCNK = "0";
		this.pb="";
		this.tigia="1";
		this.muahang_fk1="";
		this.sopoId ="";
		this.is_createRs="0";
		this.sochungtu="";
		
		this.is_saungayKS = 0;
		this.ghichu = "";
		this.isTudong = "0";
		
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util=new Utility();
		
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		this.sitecode = "";
		
		this.khonhanId="";

	}
	
	public ErpNhanhang_khac(String id)
	{
		this.userId = "";
		this.id = id;
		this.ngaynhanhang = "";
		this.ngaychot = "";
		this.sohoadon = "";
		this.dvthId = "";
		this.poId = "";
		this.mahangmuaId = "";
		this.diengiai = "";
		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		this.ngayhethan = 0;
		this.loaihanghoa = "0";
		this.loaimh = "";

		this.msg = "";
		this.ndnId = "";
		this.ldnId = "";
		this.nccId = "";
		
		this.isPONK = "0";
		this.hdNccId = "";
		
		this.isNCCNK = "0";
		this.pb="";
		this.tigia="1";
		this.muahang_fk1="";
		this.sopoId ="";
		this.is_createRs="0";
		this.nhomchiphiId="";
		this.is_saungayKS = 0;
		this.ghichu = "";
		this.isTudong = "0";
		
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util=new Utility();
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		this.sitecode = "";
		
		this.khonhanId="";
	}
	
	
	
	public String getSochungtu() {
		return sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}

	public String getTongtien() {
		return tongtien;
	}

	public void setTongtien(String tongtien) {
		this.tongtien = tongtien;
	}

	public String  getSopo_Id(){
		return this.sopoId;
	}
	public void setSopo_Id(String sopo_Id){
		this.sopoId = sopo_Id;
	}
	
	public String  getmuahang_fk(){
		return this.muahang_fk1;
	}
	public void setmuahang_fk(String muahang_fk){
		this.muahang_fk1= muahang_fk;
	}
	
	
	
	public String  getTigia(){
		return this.tigia;
	}
	public void setTiia(String tigia){
		this.tigia = tigia;
	}
	public ResultSet getNhomchiphiRs() {
		return nhomchiphiRs;
	}

	public void setNhomchiphiRs(ResultSet nhomchiphiRs) {
		this.nhomchiphiRs = nhomchiphiRs;
	}

	public String getNhomchiphiId() {
		return nhomchiphiId;
	}

	public void setNhomchiphiId(String nhomchiphiId) {
		this.nhomchiphiId = nhomchiphiId;
	}
	
	public ResultSet getHoadonNCCList(){
		return this.hoadonNCCList;
	}
	public void setHoadonNCCList(ResultSet hoadonnccList){
		this.hoadonNCCList = hoadonnccList;
	}
	
	
	public void setLoaispId(String loaispId) {
		this.loaispId = loaispId;
	}
	public String getLoaispId() {
		return loaispId;
	}
	
	public String getIsNCCNK() {
		return isNCCNK;
	}

	public void setIsNCCNK(String isNCCNK) {
		this.isNCCNK = isNCCNK;
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
	
	public String getNgaynhanhang()
	{
		return this.ngaynhanhang;
	}
	
	public void setNgaynhanhang(String ngaynhanhang)
	{
		this.ngaynhanhang = ngaynhanhang;
	}
	
	public String getDvthId()
	{
		return this.dvthId;
	}
	
	public void setDvthId(String dvthid)
	{
		this.dvthId = dvthid;
	}
	
	public ResultSet getDvthList()
	{
		return this.dvthRs;
	}
	
	public void setDvthList(ResultSet dvthlist)
	{
		this.dvthRs = dvthlist;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getSohoadon()
	{
		return this.sohoadon;
	}
	
	public void setSohoadon(String sohoadon)
	{
		this.sohoadon = sohoadon;
	}
	
	public String getDonmuahangId()
	{
		return this.poId;
	}
	
	public void setDonmuahangId(String dmhid)
	{
		this.poId = dmhid;
	}
	
	public ResultSet getDmhList()
	{
		return this.poRs;
	}
	
	public void setDmhList(ResultSet dmhlist)
	{
		this.poRs = dmhlist;
	}
	
	public String getMahangmuaId()
	{
		return this.mahangmuaId;
	}
	
	public void setMahangmuaId(String mhmId)
	{
		this.mahangmuaId = mhmId;
	}
	
	public ResultSet getMahangmuaList()
	{
		return this.mahangmuaRs;
	}
	
	public void setMahangmuaList(ResultSet mhmlist)
	{
		this.mahangmuaRs = mhmlist;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}
	
	public String getTongSoluongPO()
	{
		return this.soluongPO;
	}
	
	public void setTongSoluongPO(String tongslgPO)
	{
		this.soluongPO = tongslgPO;
	}
	
	public String getTongSoluongDN()
	{
		return this.soluongDaNhan;
	}
	
	public void setTongSoluongDN(String tongslgDN)
	{
		this.soluongDaNhan = tongslgDN;
	}
	
	public List<ISanpham> getSpList()
	{
		return this.spList;
	}
	
	public void setSpList(List<ISanpham> spList)
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
	
	public void createRs()
	{
		
		System.out.println("Loai11: "+this.loaimh); 
		String sql= "select pk_seq, ten FROM ERP_DONVITHUCHIEN where congty_fk="+this.congtyId;
		this.dvthRs = db.get(sql);
	
		
		this.ldnRs = db.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where  MA in ('NK06','NK12')  ");
		
 
		System.out.println("tao moi"+this.is_createRs);
		
		
		if(this.dvthId.trim().length()>0)
		{	
			String query1="";
			System.out.println("hoa don chon "+this.hdNccId);
			
			// ---lấy danh sách số hoá đơn ncc
			
				query1 = " select   KHONHAN_FK,pk_seq,  pk_seq as sohoadon,NHOMCHIPHI_FK   \n"+
						 " from ERP_YEUCAUNHAPHANG  where trangthai in ('1','2') " +
						 " and  congty_fk="+this.congtyId+" and DVTH_FK="+this.dvthId+" ";
				if( this.trangthai.equals("1") || this.trangthai.equals("3"))
				{
					query1 = " select   KHONHAN_FK,pk_seq,  pk_seq as sohoadon,NHOMCHIPHI_FK   \n"+
							 " from ERP_YEUCAUNHAPHANG  where " +
							 "   congty_fk="+this.congtyId+" and pk_seq="+this.hdNccId+" ";
				}
				
				
			System.out.println("ewewe:"+query1);	
			
			this.hdNccRs =  db.get(query1);
			
			
			System.out.println("kho nhan:"+this.khonhanId);	
			this.nhomchiphiRs=db.get("select PK_SEQ,TEN + ' -  '+DIENGIAI AS TEN from ERP_NHOMCHIPHI where DONVITHUCHIEN_FK='"+this.dvthId+"'");

			
			if(this.is_createRs.equals("0"))
			{
				//----- chọn hoá đơn thì load thông tin dơn hàng
				if(this.hdNccId.length() >0){

					ResultSet rshd=db.get("select  noidungxuat_fk, KHONHAN_FK from ERP_YEUCAUNHAPHANG  where   congty_fk="+this.congtyId+" and pk_seq="+this.hdNccId+"");
					try {
						if(rshd.next())
						{
							this.khonhanId=rshd.getString("KHONHAN_FK");
							this.ndnId=rshd.getString("noidungxuat_fk");
						}
						rshd.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					
					
					
					query1 = 
							"SELECT  YC.PK_SEQ, '' AS SOPO, SANPHAM_FK, SP.MA AS SPMA, SP.TEN AS SPTEN, \n" +   
							"	DV.DONVI AS DVDLTEN, 0 AS DUNGSAI, 1 AS TIGIAQUYDOI, 100000  AS TIENTEID, ISNULL(SP.HANSUDUNG,365) AS HANSUDUNG, \n" +  
							"	isnull((select top 1 DONGIA from ERP_BANGGIA_THANHPHAM_CUOIKY where  cast(THANG as CHAR(2))+'-'+ cast(NAM as CHAR(4))=(SELECT top 1 cast(THANG as CHAR(2))+'-'+ cast(NAM as CHAR(4)) FROM ERP_BANGGIA_THANHPHAM_CUOIKY ORDER BY   NAM desc , THANG desc) and SANPHAM_FK=YCSP.SANPHAM_FK),0)AS DONGIA, ISNULL(YCSP.NGAYNHANDK,'') NGAYNHAN, YCSP.SOLUONGYEUCAU AS SOLUONGHD, \n" +  
							"	isnull( \n" +  
							"		(	select SUM(nhsp.SOLUONGNHAN) \n" +      
							"			from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ = nhsp.NHANHANG_FK \n" +   
							"			where nh.TRANGTHAI not in (3,4) and nh.YEUCAUNHAPHANG_FK = YC.PK_SEQ AND NHSP.SANPHAM_FK = YCSP.SANPHAM_FK \n" +   
							"		), 0) as soluongdanhan, KHO.PK_SEQ khonhan, KHO.TEN AS khoten \n" +   
							"FROM ERP_YEUCAUNHAPHANG YC INNER JOIN ERP_YEUCAUNHAPHANG_SANPHAM YCSP ON YC.PK_SEQ = YCSP.YEUCAUNHAPHANG_FK \n" +  
							"	INNER join ERP_SANPHAM SP ON SP.PK_SEQ=YCSP.SANPHAM_FK \n" +  
							"	LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK \n" +  
							"	LEFT JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=YC.KHONHAN_FK \n" +   
							"WHERE YC.PK_SEQ = '" + this.hdNccId + "' AND YCSP.SOLUONGYEUCAU - \n" + 
							"isnull( \n" +  
							"	(	select SUM(nhsp.SOLUONGNHAN) \n" +       
							"		from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ = nhsp.NHANHANG_FK \n" +   
							"		where nh.TRANGTHAI not in (3,4) and nh.YEUCAUNHAPHANG_FK = YC.PK_SEQ  AND NHSP.SANPHAM_FK = YCSP.SANPHAM_FK \n" +   
							"	), 0) > 0  order by SANPHAM_FK";
			
				
			System.out.println("ds sanpham nhan hang san pham :"+ query1);
					
					ResultSet rs = db.get(query1);
					List<ISanpham> spList = new ArrayList<ISanpham>();
					
					if(rs!=null){
						ISanpham sp = null;
						try{
						while(rs.next()){
							sp= new Sanpham();
					
							sp.setMuahang_fk(rs.getString("PK_SEQ"));
							sp.setSoPO(rs.getString("SOPO"));
							sp.setMa(rs.getString("spma"));
							sp.setId(rs.getString("SANPHAM_FK"));
							sp.setDiengiai(rs.getString("spten"));
							sp.setSoluongdat(DinhDang.dinhdangkho(rs.getDouble("soluonghd")));
							sp.setKhonhanId(rs.getString("khonhan"));
							sp.setDvdl(rs.getString("dvdlTen"));
							
							
							sp.setSoluongMaxNhan(DinhDang.dinhdangkho( rs.getDouble("soluonghd") - rs.getDouble("soluongdanhan")));
							
							sp.setSoluongDaNhan(DinhDang.dinhdangkho(rs.getDouble("soluongdanhan")));
							
							System.out.println(" so luong max nhan: " + sp.getSoluongMaxNhan());
							System.out.println(" so luong da nhan: " + sp.getSoluongDaNhan());
							
							sp.setHansudung(rs.getString("hansudung"));
							sp.setNgaynhandukien(rs.getString("ngaynhan"));
							sp.setKhonhanTen(rs.getString("khoten"));
							sp.setDongia(DinhDang.dinhdangkho(rs.getDouble("dongia")));
							sp.setDongiaViet(DinhDang.dinhdangkho(rs.getDouble("dongia")));
							sp.setTigiaquydoi(rs.getString("tigiaquydoi"));
							sp.setTiente(rs.getString("tienteId"));
							sp.setSoluongnhan("");
							sp.setthanhtien("0");
							spList.add(sp);
							
						}rs.close();}catch(Exception e){
							e.printStackTrace();
						}	
					}
					
					this.spList = spList;
					
					
				}	
			}
		}
		
		
		if(this.hdNccId.trim().length() > 0 )
		{
			
			sql="select   NHOMCHIPHI_FK   \n"+
			 " from ERP_YEUCAUNHAPHANG  where " +
			 "   congty_fk="+this.congtyId+" and pk_seq="+this.hdNccId;
			
			ResultSet rs=db.get(sql);
			
			try {
				if(rs.next())
				{
					this.nhomchiphiId=rs.getString("NHOMCHIPHI_FK");
				}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			
		}
		
				
		// Kiểm tra kho nhận có phải Kho ký gửi KH/Dự trữ KH không
		System.out.println("khonhanId "+khonhanId);
				if (this.khonhanId.trim().length() > 0) {
					String command = "select PK_SEQ, MA+'-'+ TEN as TEN from ERP_BIN where KHOTT_FK = "+ this.khonhanId;
					ResultSet rss = this.db.get(command);
					List<KhuVucKho> listK = new ArrayList<KhuVucKho>();
					try{
						if(rss !=null){
							while (rss.next()){
								KhuVucKho temp = new KhuVucKho();
								temp.setId(rss.getString("PK_SEQ"));
								temp.setTen(rss.getString("TEN"));
								listK.add(temp);
							}
							rss.close();
						}
						this.listKhuVucKho = listK;
					} catch (Exception ex){
						ex.printStackTrace();
					}
				}
	}
	
	// Dành cho trường hợp tạo ra số mẻ và số lô
		public void createRs1() {
			System.out.println("Loai11: "+this.loaimh); 
			String sql= "select pk_seq, ten FROM ERP_DONVITHUCHIEN where congty_fk="+this.congtyId;
			this.dvthRs = db.get(sql);
		
			
			this.ldnRs = db.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where  MA in ('NK06','NK12')  ");
			
	 
			System.out.println("tao moi"+this.is_createRs);
			
			
			if(this.dvthId.trim().length()>0)
			{	
				String query1="";
				System.out.println("hoa don chon "+this.hdNccId);
				
				// ---lấy danh sách số hoá đơn ncc
				
					query1 = " select   KHONHAN_FK,pk_seq,  pk_seq as sohoadon,NHOMCHIPHI_FK   \n"+
							 " from ERP_YEUCAUNHAPHANG  where trangthai in ('1','2') " +
							 " and  congty_fk="+this.congtyId+" and DVTH_FK="+this.dvthId+" ";
					if( this.trangthai.equals("1") || this.trangthai.equals("3"))
					{
						query1 = " select   KHONHAN_FK,pk_seq,  pk_seq as sohoadon,NHOMCHIPHI_FK   \n"+
								 " from ERP_YEUCAUNHAPHANG  where " +
								 "   congty_fk="+this.congtyId+" and pk_seq="+this.hdNccId+" ";
					}
					
					
				System.out.println("ewewe:"+query1);	
				
				this.hdNccRs =  db.get(query1);
				
				
				System.out.println("kho nhan:"+this.khonhanId);	
				this.nhomchiphiRs=db.get("select PK_SEQ,TEN + ' -  '+DIENGIAI AS TEN from ERP_NHOMCHIPHI where DONVITHUCHIEN_FK='"+this.dvthId+"'");
			}
			if(this.hdNccId.trim().length() > 0 )
			{
				
				sql="select   NHOMCHIPHI_FK   \n"+
				 " from ERP_YEUCAUNHAPHANG  where " +
				 "   congty_fk="+this.congtyId+" and pk_seq="+this.hdNccId;
				
				ResultSet rs=db.get(sql);
				
				try {
					if(rs.next())
					{
						this.nhomchiphiId=rs.getString("NHOMCHIPHI_FK");
					}
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
				
			}
			
			// Kiểm tra kho nhận có phải Kho ký gửi KH/Dự trữ KH không
			if (this.khonhanId.trim().length() > 0) {
			/*	String command = "SELECT ISNULL(LOAI,0) LOAI FROM ERP_KHOTT WHERE PK_SEQ = " + this.khonhanId + " ";
				ResultSet rss = db.get(command);
				try {
					if (rss.next()) {
						this.loaikho = rss.getString("LOAI");
					}
					rss.close();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				
				String command = "select PK_SEQ, MA+'-'+ TEN as TEN from ERP_BIN where KHOTT_FK = "+ this.khonhanId;
				ResultSet rss = this.db.get(command);
				List<KhuVucKho> listK = new ArrayList<KhuVucKho>();
				try{
					if(rss !=null){
						while (rss.next()){
							KhuVucKho temp = new KhuVucKho();
							temp.setId(rss.getString("PK_SEQ"));
							temp.setTen(rss.getString("TEN"));
							listK.add(temp);
						}
						rss.close();
					}
					this.listKhuVucKho = listK;
				} catch (Exception ex){
					ex.printStackTrace();
				}
			}
		}
	
	public String getIs_createRs() {
		return is_createRs;
	}

	public void setIs_createRs(String is_createRs) {
		this.is_createRs = is_createRs;
	}

	public void init()
	{
		String query =   " 	SELECT ISNULL(A.TONGTIEN,0) as TONGTIEN,A.nhomchiphi_fk,YEUCAUNHAPHANG_FK,ISNULL(A.ISTUDONG,0) AS  ISTUDONG   ,A.PK_SEQ AS NHID, A.TRANGTHAI, A.LOAIHANGHOA_FK, A.NOIDUNGNHAN_FK, "+  
						 " A.SOHOADON, A.NGAYNHAN, A.NGAYCHOT, A.DIENGIAI, B.PK_SEQ AS DVTHID, B.TEN AS DVTHTEN, "+   
						 " A.TRANGTHAI, A.NoiDungNhap_fk , A.NCC_KH_FK, '' AS LOAIMH,DOITUONG_FK, "+ 
						 " HDNCC_FK,   "+
						 " A.MUAHANG_FK AS MUAHANG_FK, ISNULL(A.TIGIA, '1') AS TIGIA, '' AS SOPO , ISNULL(A.SOCHUNGTU,0) as SOCHUNGTU ,c.KHONHAN_FK"+
						 " FROM ERP_NHANHANG A INNER JOIN ERP_DONVITHUCHIEN B ON A.DONVITHUCHIEN_FK = B.PK_SEQ "+ 
						 " INNER JOIN ERP_YEUCAUNHAPHANG C ON A.YEUCAUNHAPHANG_FK = C.PK_SEQ "+
						 " WHERE A.PK_SEQ = '" + this.id + "' ";
		
		System.out.println("Init : " + query);
		
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.ngaynhanhang = rs.getString("ngaynhan");
					this.hdNccId=rs.getString("YEUCAUNHAPHANG_FK");
					this.ngaychot = rs.getString("ngaychot");
					this.sohoadon = rs.getString("sohoadon");
					this.dvthId = rs.getString("dvthId");
					this.poId = rs.getString("muahang_fk")== null ? "" : rs.getString("muahang_fk");
					this.diengiai = rs.getString("diengiai");
					this.ndnId = rs.getString("NoiDungNhap_fk");
					this.loaihanghoa = rs.getString("loaihanghoa_fk");
					this.ldnId = rs.getString("NoiDungNhap_fk");
					System.out.println("Ly do nhan :"+this.ldnId);
					this.trangthai = rs.getString("trangthai");
					this.tongtien=DinhDang.dinhdanglamtron(rs.getDouble("TONGTIEN"));
				
					this.loaimh = rs.getString("loaimh");
					 this.nhomchiphiId= rs.getString("DOITUONG_FK");
					this.isTudong =rs.getString("ISTUDONG");
					this.nhomchiphiId=rs.getString("nhomchiphi_fk");
					 this.sochungtu=rs.getString("SOCHUNGTU");
					this.tigia = rs.getString("tigia");
					this.khonhanId=rs.getString("KHONHAN_FK");
					 
					
				}
				rs.close();
			}
			catch (Exception e)
			{
				System.out.println("1.Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}		
		

		this.initSanPham();
		
		this.is_createRs="1";
		
		this.createRs();
	}
	 
	private void initSanPham()
	{
		

		
		String query = 	" SELECT  isnull(A.THANHTIEN,0) THANHTIEN, A.SANPHAM_FK   as spId,  isnull(sp.MA, isnull(sp.ma, ''))   spMa, " +
						" isnull(sp.Ten1, sp.Ten)         AS spTen, mh.PK_SEQ as MUAHANG_FK,    " +
						" a.NGAYNHANDUKIEN, a.DUNGSAI, a.DONGIA, a.SOLUONGDAT, a.SOLUONGNHAN, isnull(sp.HANSUDUNG, '0') as HanSuDung, " +
						" isnull(a.DonVi, 'NA') as donvi, a.TienTe_Fk, a.TyGiaQuyDoi, a.DonGiaViet, " +
						" khott.pk_seq as khottId,   khott.ten as khottTen     " +
						" FROM ERP_NHANHANG_SANPHAM a inner join ERP_NHANHANG nh on a.nhanhang_fk = nh.pk_seq   " +
						" left JOIN ERP_YEUCAUNHAPHANG mh on a.MUAHANG_FK = mh.PK_SEQ "+
						" LEFT join ERP_SANPHAM sp on a.SANPHAM_FK = sp.PK_SEQ   " +
						" LEFT JOIN ERP_KHOTT khott on a.khonhan = khott.pk_seq  " +
						" WHERE a.NHANHANG_FK = '" + this.id + "' order by A.SANPHAM_FK";
		
		System.out.println("[ErpNhanhang_Giay.initSanPham] query = " + query);
		ResultSet rsSp = db.get(query);
		
		if (rsSp != null)
		{
			
			
			try
			{
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next())
				{
					String spId = rsSp.getString("spId");
					String spMa = rsSp.getString("spMa");
					String spTen = rsSp.getString("spTen");
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");
					
					String soluongdat = DinhDang.dinhdangkho(rsSp.getDouble("SOLUONGDAT"));
					String soluongnhan = DinhDang.dinhdangkho(rsSp.getDouble("SOLUONGNHAN"));
					
					String hansudung = rsSp.getString("HANSUDUNG");
					String dvdl = rsSp.getString("DonVi");
					String dongia = DinhDang.dinhdangkho(rsSp.getDouble("DONGIA"));
					
					String muahang_fk = rsSp.getString("MUAHANG_FK"); 
					
										
					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan, hansudung, ngaynhandk, soluongdat, dvdl);
					if (soluongdat != "" && soluongnhan != "")
						sanpham.setCOnlai(Float.toString(Float.parseFloat(soluongdat.replaceAll(",", "") ) - Float.parseFloat(soluongnhan.replaceAll(",", ""))));
					String khottId= rsSp.getString("khottId") == null ? "" : rsSp.getString("khottId");
					sanpham.setKhonhanId(khottId);
					if(this.loaihanghoa.equals("0"))
					{
						/*String comand = " select SANPHAM_FK, isnull(SOLO,'') SOLO, SOLUONG, NGAYSANXUAT, NGAYHETHAN,NGAYNHAPKHO,(select isnull( MA + ', ' + TEN,'')  as TEN  from ERP_BIN where KHOTT_FK="+khottId+" and  pk_seq=BIN_FK) VITRI,BIN_FK,isnull(MATHUNG,'') MATHUNG, \n "
								+ "isnull(MAME,'') MAME,isnull(PHIEUDT,'') PHIEUDT,isnull(PHIEUEO,'') PHIEUEO,isnull(HAMAM,0) as HAMAM, \n "
								+ "isnull(HAMLUONG,'100') HAMLUONG,isnull(MARQ,'') MARQ, isnull(MAPHIEU,'') MAPHIEU,NSX_FK, ISNULL((SELECT MA +'-' + ten  FROM ERP_NHASANXUAT WHERE PK_sEQ = NSX_FK),'') AS NSXTEN from ERP_NHANHANG_SP_CHITIET " +
										" where NGAYNHANDUKIEN='" + ngaynhandk + "' AND nhanhang_fk = '" + this.id + "' and sanpham_fk = '" + spId + "' order by lannhan asc ";
						*/
						String comand="select count(sanpham_fk) as sothung, SANPHAM_FK, isnull(SOLO,'') SOLO, sum(SOLUONG) as soluong, NGAYSANXUAT, NGAYHETHAN,NGAYNHAPKHO,isnull(PHIEUDT,'') PHIEUDT,isnull(PHIEUEO,'') PHIEUEO,isnull(HAMAM,0) as HAMAM,  isnull(HAMLUONG,'100') HAMLUONG,\n "+ 
								"isnull(MARQ,'') MARQ, isnull(MAPHIEU,'') MAPHIEU,NSX_FK, ISNULL((SELECT MA +'-' + ten  FROM ERP_NHASANXUAT WHERE PK_sEQ = NSX_FK),'') AS NSXTEN \n "+ 
								"from ERP_NHANHANG_SP_CHITIET  where NGAYNHANDUKIEN='" + ngaynhandk + "' AND nhanhang_fk ='" + this.id + "' and sanpham_fk = '" + spId + "' "
										+ " group by SANPHAM_FK, isnull(SOLO,'') ,  NGAYSANXUAT, NGAYHETHAN,NGAYNHAPKHO,isnull(PHIEUDT,'') ,isnull(PHIEUEO,'') ,isnull(HAMAM,0),\n "+ 
										"   isnull(HAMLUONG,'100') ,\n "+ 
										" isnull(MARQ,'') , isnull(MAPHIEU,''),NSX_FK ";
						System.out.println("Khoi tao san pham con: " + comand);
						ResultSet spConRs = db.get(comand);
						
						List<ISpDetail> spConList = new ArrayList<ISpDetail>();
						ISpDetail spCon = null;
						if (spConRs != null)
						{
							while (spConRs.next())
							{
							
								//spCon.setKhuId(spConRs.getString("khu_fk"));
								

								spCon=new SpDetail(spConRs.getString("SANPHAM_FK"), spConRs.getString("SOLO"), spConRs.getString("SOLUONG"), spConRs.getString("NGAYSANXUAT"), spConRs.getString("NGAYHETHAN"),"","","","",spConRs.getString("PHIEUDT"),spConRs.getString("PHIEUEO"),spConRs.getString("MARQ"),spConRs.getString("HAMLUONG"),spConRs.getString("HAMAM"),"");
								spCon.setNsxId(spConRs.getString("NSX_FK")==null?"":spConRs.getString("NSX_FK"));
								spCon.setNsxTen(spConRs.getString("NSXTEN"));
								spCon.setMarquette(spConRs.getString("marq"));
								spCon.setSothung(spConRs.getString("sothung"));
								
								spConList.add(spCon);
							}
							spConRs.close();
						}
						
						sanpham.setSpDetail(spConList);
					}
					//muc 3 solo - tu7ng2 thung
					String comand = " select SANPHAM_FK, isnull(SOLO,'') SOLO, SOLUONG, NGAYSANXUAT, NGAYHETHAN,NGAYNHAPKHO,(select isnull( MA + ', ' + TEN,'')  as TEN  from ERP_BIN where KHOTT_FK="+khottId+" and  pk_seq=BIN_FK) VITRI,BIN_FK,isnull(MATHUNG,'') MATHUNG, \n "
							+ "isnull(MAME,'') MAME,isnull(PHIEUDT,'') PHIEUDT,isnull(PHIEUEO,'') PHIEUEO,isnull(HAMAM,0) as HAMAM, \n "
							+ "isnull(HAMLUONG,'100') HAMLUONG,isnull(MARQ,'') MARQ, isnull(MAPHIEU,'') MAPHIEU,NSX_FK, ISNULL((SELECT MA +'-' + ten  FROM ERP_NHASANXUAT WHERE PK_sEQ = NSX_FK),'') AS NSXTEN from ERP_NHANHANG_SP_CHITIET " +
									" where NGAYNHANDUKIEN='" + ngaynhandk + "' AND nhanhang_fk = '" + this.id + "' and sanpham_fk = '" + spId + "' order by lannhan asc ";
					ResultSet spConRs = db.get(comand);
					System.out.println("comman "+comand);
					List<DetailMeSp> detailList = new ArrayList<DetailMeSp>();
					DetailMeSp temp=null;
					if (spConRs != null)
					{
						while (spConRs.next())
						{
					
							temp=new DetailMeSp(spConRs.getString("mathung"), spConRs.getString("bin_fk"), spConRs.getString("soluong"), spConRs.getString("solo"), spConRs.getString("mame"),spConRs.getString("ngaysanxuat"), spConRs.getString("ngayhethan"));
							temp.setNsxId(spConRs.getString("NSX_FK")==null?"":spConRs.getString("NSX_FK"));
							temp.setNsxTen(spConRs.getString("NSXTEN"));
							temp.setMarrquet(spConRs.getString("marq"));
							detailList.add(temp);
						}
						spConRs.close();
					}
					
					sanpham.setListDetailMeSp(detailList);
					
					
					//	--------------------------------------------------------------		
					sanpham.setMuahang_fk(muahang_fk);
				
					sanpham.setDongia(dongia);
					sanpham.setTiente(rsSp.getString("TienTe_Fk"));
					sanpham.setTigiaquydoi(rsSp.getString("TyGiaQuyDoi"));
					sanpham.setDongiaViet(rsSp.getString("DonGiaViet"));
				
					sanpham.setKhonhanTen( rsSp.getString("khottTen") == null ? "" : rsSp.getString("khottTen") );
					sanpham.setthanhtien(DinhDang.dinhdangkho(Double.parseDouble(rsSp.getString("THANHTIEN"))));
 
					double soluongDat = rsSp.getDouble("SOLUONGDAT");
					
					//Tinh so luong MAX + dung sai co the nhan
					double soluongPONhan = 0;
					double soluongMax = soluongDat;
					
					if(this.hdNccId.trim().length() > 0  )
					{
						query = "select sum(b.SOLUONGNHAN)  as soluongDaNhan  " +
								"from ERP_NHANHANG  a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  " +
								"where a.YEUCAUNHAPHANG_FK = '" + this.hdNccId + "' and NGAYNHANDUKIEN = '" + ngaynhandk + "' and SANPHAM_FK = '" + spId + "' and a.TRANGTHAI not in (3, 4) ";
						
						if(this.id.trim().length() > 0)
						{
							query += " and a.pk_seq != '" + this.id + "' ";
						}
						System.out.println("soluongdanhan=" + query);
						ResultSet rsNhanTD = db.get(query);
						if(rsNhanTD != null)
						{
							if(rsNhanTD.next())
							{
								//double soluongPODat = rsNhanTD.getDouble("soluongDat");
								double soluongPODat = soluongDat;
								
								soluongPONhan = rsNhanTD.getDouble("soluongDaNhan");
								soluongMax = ( soluongPODat  ) - soluongPONhan;
							}
							rsNhanTD.close();
						}
					}
										
					
					sanpham.setSoluongDaNhan(DinhDang.dinhdangkho(soluongPONhan));
					sanpham.setSoluongMaxNhan(Double.toString(soluongMax));
					
					
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			
			}
			catch (Exception e)
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
				
			}
		}
	}
	
	
	
	public void initPdf(String spId)
	{
		String query =  " select isnull(c.LOAISANPHAM_FK,0) as LOAISANPHAM_FK, a.PK_SEQ as nhId, a.trangthai, a.loaihanghoa_fk, a.noidungnhan_fk, a.SOHOADON, a.NGAYNHAN, a.NGAYTAO, a.diengiai, b.pk_seq as dvthId, b.TEN as dvthTen, a.TRANGTHAI, a.NoiDungNhap_fk , a.NCC_KH_FK, " +
						" 	case when a.noidungnhan_fk = 100000 then  SOPO else d.Prefix + cast(d.PK_SEQ as varchar(10)) end as sochungtu,  " +
						"   case when a.noidungnhan_fk = 100000 then ncc.TEN else kh.TEN end as donviban " +
						" from ERP_NHANHANGKHAC a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ " +
						" left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ " +
						" left join DonTraHang d on a.TRAHANG_FK = d.PK_SEQ " +
						" left join Erp_Nhacungcap ncc on ncc.PK_SEQ=c.NHACUNGCAP_FK " +
						" left join ERP_KHACHHANG kh on kh.PK_SEQ=d.KHACHHANG_FK " +
						" where a.pk_seq = '" + this.id + "' and b.pk_seq in "+this.util.quyen_donvithuchien(this.userId);
		
		System.out.println("[ErpNhanhang_Giay.initPdf] Khoi tao nhan hang: " + query );
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{				
					this.loaispId=rs.getString("LOAISANPHAM_FK");
					this.ngaychot = rs.getString("ngaytao"); //NGAY TAO
					this.ngaynhanhang = rs.getString("ngaynhan");
					this.dvthId = rs.getString("donviban");
					this.poId = rs.getString("sochungtu");
					this.diengiai = rs.getString("diengiai");
					this.ndnId =  rs.getString("noidungnhan_fk");
					this.sohoadon = rs.getString("sohoadon");
				}
				rs.close();
			}
			catch (SQLException e)
			{
			}
		}
		
		this.initSanPhamPdf(spId);
	}
	
	
	private void initSanPhamPdf(String _spId)
	{	
 
		String query = "  SELECT isnull(sp.loaisanpham_fk,0) as  loaisanpham_fk, CASE NH.LOAIHANGHOA_FK WHEN 0 THEN A.SANPHAM_FK WHEN 1 THEN TSCD.PK_SEQ ELSE NCP.PK_SEQ END AS SPID,  " +  
					   "  CASE NH.LOAIHANGHOA_FK WHEN 0 THEN ISNULL( CASE WHEN ( LEN(LTRIM(RTRIM(SP.MA))) <= 0 OR ( SP.MA IS NULL ) ) THEN SP.MA ELSE SP.MA END, '' )  WHEN 1 THEN TSCD.MA ELSE NCP.TEN END AS SPMA,   " +  
					   "  CASE NH.LOAIHANGHOA_FK WHEN 0 THEN ISNULL(SP.TEN1, ISNULL(SP.TEN, ''))  ELSE ISNULL(A.DIENGIAI, '') END AS SPTEN,  " +  
					   "  A.NGAYNHANDUKIEN, A.DUNGSAI, A.DONGIA, A.SOLUONGDAT, A.SOLUONGNHAN, ISNULL(SP.HANSUDUNG, '0') AS HANSUDUNG,  " +  
					   "  ISNULL(A.DONVI, 'NA') AS DONVI, A.TIENTE_FK, A.TYGIAQUYDOI, A.DONGIAVIET,  " +  
					   "  KHOTT.PK_SEQ AS KHOTTID, KHOTT.MA + ', ' + KHOTT.TEN AS KHOTEN, " +  
					   "  CASE WHEN ISNULL(QC.SOLUONG1,'0') = '0' THEN   " +  
					   "  A.SOLUONGNHAN ELSE A.SOLUONGNHAN *  ISNULL(QC.SOLUONG2,'0') /QC.SOLUONG1 END AS TRONGLUONG , " +  
					   "  CAST(ROUND(ISNULL(SP.THETICH, 0), 5) AS NUMERIC(10, 5)) AS THETICH  " +  
					   "  FROM ERP_NHANHANGKHAC_SANPHAM A INNER JOIN ERP_NHANHANGKHAC NH ON A.NHANHANGkhac_FK = NH.PK_SEQ    " +  
					   "  LEFT join ERP_SANPHAM SP ON A.SANPHAM_FK = SP.PK_SEQ    " +  
					   "  LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND SP.DVDL_FK=QC.DVDL1_FK AND QC.DVDL2_FK=100003   " +  
					   "  LEFT JOIN ERP_TAISANCODINH TSCD ON A.TAISAN_FK = TSCD.PK_SEQ         " +  
					   "  LEFT JOIN ERP_NHOMCHIPHI NCP ON A.CHIPHI_FK = NCP.PK_SEQ   " +  
					   "  LEFT JOIN ERP_KHOTT KHOTT ON A.KHONHAN = KHOTT.PK_SEQ   " +  
					   "  WHERE A.NHANHANGkhac_FK = "+this.id+" AND A.SOLUONGNHAN >0 " ;
 
		if(_spId.length() > 0) {
			query += " and a.SANPHAM_FK in (" + _spId + ")";
		}
 
		ResultSet rsSp = db.get(query);
		
		if (rsSp != null)
		{
			try
			{
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next())
				{
					String spId = rsSp.getString("spId");
					//System.out.println("[ErpNhanhang_Giay.initSanPhamPdf] spId = " + spId);
					String spMa = rsSp.getString("spMa");
					
					
					//--- nếu sản phẩm là GLUE, ko in quy cách -----//
					String loaisp = rsSp.getString("loaisanpham_fk");
				 
					String spTen="";
					if(loaisp.equals("100015") || loaisp.equals("100016")){
						spTen = rsSp.getString("spten1");
					}
					else{
					 spTen = rsSp.getString("spTen");
					}
					
					
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");
					String soluongdat = rsSp.getString("SOLUONGDAT");
					String soluongnhan = rsSp.getString("SOLUONGNHAN");
					String hansudung = rsSp.getString("khoTen"); // luu ten kho
					String dvdl = rsSp.getString("DonVi");
					
					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan, hansudung, ngaynhandk, soluongdat, dvdl);
					sanpham.setTrongluong( rsSp.getString("trongluong")  );
					sanpham.setThetich( rsSp.getString("THETICH") == null ? "0" : rsSp.getString("THETICH") );
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			}
			catch (Exception e)
			{
				e.printStackTrace();
 
			}
		}
	}
	
	
	public boolean createNhanHang()
	{
		String ngaytao = this.getDateTime();
		this.nhomkenhid="100000";
		
		boolean check=false;
		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		}
		else
		{
			for (int i = 0; i < spList.size(); i++)
			{
				ISanpham sp = spList.get(i);
				System.out.println("sp.getSpDetail() "+sp.getSpDetail().size());
				if(sp.getSpDetail().size() > 0)
				{
					check=true;
				}
			}
		}
		
		if(check==false){
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		}
 
		if(this.ndnId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung nhận hàng";
			return false;
		}
		

		
		if(this.hdNccId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn Số chứng từ ";
			return false;
		}
		
		if(this.nhomchiphiId.length()<=0) this.nhomchiphiId="0";
		try
		{
			db.getConnection().setAutoCommit(false);			
			String query = "insert ERP_NHANHANG( NGAYNHAN, YEUCAUNHAPHANG_FK, NGAYCHOT, LOAIHANGHOA_FK, DIENGIAI, DONVITHUCHIEN_FK,  NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, NoiDungNhap_fk, tigia , GHICHU ,nhomchiphi_fk,TONGTIEN,SOCHUNGTU) " +
					" values( '" +this.ngaynhanhang +"', "+ this.hdNccId +", '" + this.ngaynhanhang + "', '" + this.loaihanghoa + "', N'" +this.diengiai +"', '" +this.dvthId +"',  " + 
					" '" + ngaytao +"', '" +ngaytao +"', '" +this.userId + "', '" +this.userId +"', '0', '" + this.congtyId + "', " + this.ndnId + ",  '"+this.tigia+"', "+
				    " N'"+ this.ghichu +"','"+this.nhomchiphiId+"','"+this.tongtien+"','"+this.sochungtu+"')";
			 
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi Nhan hang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String nhCurrent = "";
			query = "select IDENT_CURRENT('ERP_NHANHANG') as nhId";
			
			ResultSet rs1=  db.get(query);
			
			if(rs1!=null){
				System.out.println(" vao dc day");
				try{
					while(rs1.next()){
						this.id = rs1.getString("nhId");
					}
				}catch(Exception e){
					e.printStackTrace();
				}	
			}
			
			ResultSet rsNh = db.get(query);
			if (rsNh.next())
			{
				nhCurrent = rsNh.getString("nhId");
				rsNh.close();
			}
			
			if (this.spList.size() > 0)
			{
				for (int i = 0; i < spList.size(); i++)
				{
					ISanpham sp = spList.get(i);
					
					if (sp.getSoluongnhan() != "") // chi luu nhung san pham nguoi dung nhap so luong
					{
						 
						
						double soluongnhan = 0;
						if(sp.getSoluongnhan().trim().length() <= 0)
						{
							this.msg = "Bạn phải nhập số lượng nhận cho sản phẩm: " + sp.getMa();
							db.getConnection().rollback();
							return false;
						}
						else
						{
							soluongnhan = Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""));
							System.out.println("so luong nhan " + soluongnhan);
							if(sp.getDungsai().trim().length() <= 0)
								sp.setDungsai("0");
							
							
							double slgMax = Double.parseDouble(sp.getSoluongMaxNhan().replaceAll(",", ""));
							System.out.println("so luong max nhan " + slgMax);
							if(soluongnhan > slgMax)
							{
								this.msg = "Tổng số lượng nhận của sản phẩm: " + sp.getMa() + " không được phép vượt quá tổng đặt (" + sp.getSoluongdat() + ") và dung sai cho phép ( " + sp.getDungsai() + "%). Vì thế bạn chỉ có thể nhận tối đa là ( " + slgMax + " )  ";
								db.getConnection().rollback();
								return false;
							}
						}
						
						String khonhan = sp.getKhonhanId().trim().length() <= 0 ? "null" : sp.getKhonhanId().trim();
						if(this.loaihanghoa.equals("0") && sp.getKhonhanId().trim().length() <= 0)
						{
							this.msg = "Vui lòng kiểm tra lại kho nhận của sản phẩm ( " + sp.getMa() + " ) ";
							db.getConnection().rollback();
							return false;
						}
					 
						double songayvuotmuc=0;
						double soluongvuotmuc=0;
						
							query = "insert ERP_NHANHANG_SANPHAM(NHANHANG_FK, MUAHANG_FK ,SANPHAM_FK,   DIENGIAI, DONVI, NGAYNHANDUKIEN, KHONHAN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, SONGAYVUOTMUC, SOLUONGVUOTMUC,THANHTIEN) " +
								"values('" + nhCurrent + "', "+ sp.getMuahang_fk() +", " + sp.getId() + ",    N'" + sp.getDiengiai() + "', N'" + sp.getDvdl() + "', '" + sp.getNgaynhandukien() + "', " + khonhan + ", " +
									"'" + sp.getSoluongdat().replaceAll(",", "") + "',  '" + sp.getSoluongnhan().replaceAll(",", "") + "', '" + sp.getDungsai() + "', " + Double.parseDouble(sp.getDongia().replaceAll(",", "")) + ", '" + 
									sp.getTiente() + "', '" + sp.getTigiaquydoi() + "', '" + sp.getDongiaViet().replaceAll(",", "") + "' , "+ songayvuotmuc +", "+soluongvuotmuc+" ,'"+sp.getthanhtien().replace(",","")+"')";
	
						System.out.println("ERP_NHANHANG_SANPHAM  "+query);
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
						
						double tongchitiet=0;
						
						//List<ISpDetail> detailList = sp.getSpDetail();
						List<DetailMeSp> detailList = sp.getListDetailMeSp();
						for (int j = 0; j < detailList.size(); j++)
						{
							//ISpDetail detail = detailList.get(j);
							DetailMeSp detail = detailList.get(j);
							
							if (detail.getSoLuong().trim().length() > 0 && !detail.getSoLuong().equals("0") && detail.getSoLo() != "" && detail.getNgaySanXuat() != "" )
							{
									query = " INSERT INTO  ERP_NHANHANG_SP_CHITIET ( NHANHANG_FK, MUAHANG_FK,SANPHAM_FK, LANNHAN, SOLO, SOLUONG, GIATHEOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHANDUKIEN ,KHU_FK,"
											+ "NGAYNHAPKHO,BIN_FK,MATHUNG,MAME,MARQ,NSX_FK) " +
											"values('" + nhCurrent + "',"+ sp.getMuahang_fk() +", '" + sp.getId() + "', '" + Integer.toString(j + 1) + "', '" + detail.getSoLo() + "', " +
											"'" + detail.getSoLuong().replaceAll(",", "") + "', "+ Double.parseDouble(sp.getDongia().replaceAll(",", "")) +", '" +detail.getNgaySanXuat() +"', '" + detail.getNgayHetHan() + "','" + sp.getNgaynhandukien() + "',NULL,'"+this.ngaynhanhang+"',"
													+ ""+detail.getKhuVuc()+",'"+detail.getMaThung()+"','"+detail.getMe()+"',"
															+"'"+detail.getMarrquet()+"',"
																	+(detail.getNsxId().trim().length()==0?"NULL":detail.getNsxId())+")";
							
									
									tongchitiet = DinhDang.dinhdangso(tongchitiet) +DinhDang.dinhdangso(Double.parseDouble(detail.getSoLuong().replaceAll(",", "")));
																
								System.out.println("tong tien chi tiet  "+j + tongchitiet);
								
								if (!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: " + query;
									System.out.println(this.msg);
									db.getConnection().rollback();
									return false;
								}
							}
						}
						
						if(this.loaihanghoa.equals("0")){
							
							if( Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""))-DinhDang.dinhdangso(tongchitiet)  != 0 ){
								this.msg = "Vui lòng kiểm tra số lo chi tiết của sản phẩm :"+sp.getMa();
								db.getConnection().rollback();
								return false;
							}
							
						}

					}
				}
			}
			query = "update ERP_NHANHANG set	KHONHAN_FK = (SELECT DISTINCT KHONHAN FROM ERP_NHANHANG_SANPHAM WHERE NHANHANG_FK = PK_SEQ) "+
					"where pk_seq = '" +nhCurrent + "'";
				
				//System.out.println("Query update: " + query);
				
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Nhan hang: " + query;
					db.getConnection().rollback();
					return false;
				}
			query = "update erp_yeucaunhaphang set conhanhang = 1 where pk_seq = " + this.hdNccId;
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi erp_yeucaunhaphang: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			this.msg="Lỗi tạo nhận hàng: "+e.getMessage();
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
		
	}
	
	
	public boolean updateNhanHang()
	{
		 
		boolean check=false;
		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		}
		else
		{
			for (int i = 0; i < spList.size(); i++)
			{
				ISanpham sp = spList.get(i);
				
				if(sp.getSpDetail().size() > 0)
				{
					check=true;
				}
			}
		}
		
		if(check==false){
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		}
 
		
		
		if(this.ndnId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nội dung nhận hàng";
			return false;
		}
		

	 

		if(this.hdNccId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn Số hóa đơn NCC ";
			return false;
		}
			

		Library lib =new Library();
		if(!lib.check_trangthaihople(this.id, "ERP_NHANHANG", "0", db)){
			// khac trang thai 0 thì hk cho chot
			this.msg= "Không thể chỉnh đơn hàng này: phiếu này đã được chốt hoặc trạng thái không hợp lệ! ";
			return false;
		}
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = ""; 
			String muahang_fk = "null";
			String trahang_fk = "null";
			
			if(this.ndnId.equals("100000"))
				muahang_fk = this.poId;
			else
				trahang_fk = this.poId;
			
			String ldn_fk = "null";
			if(this.ldnId.trim().length() > 0)
				ldn_fk = this.ldnId;
			
			String NCC_KH_FK = "null";
			if(this.nccId.trim().length() > 0)
				NCC_KH_FK = this.nccId;
			 																	
				// END

				query = "update ERP_NHANHANG set  NGAYNHAN = '" + this.ngaynhanhang + "',SOCHUNGTU=N'"+this.sochungtu+"', NGAYCHOT = '" + this.ngaychot + "'," +
				"DIENGIAI = N'" + this.diengiai + "', " + "DONVITHUCHIEN_FK = '" + this.dvthId + "'," +
				" NGAYSUA = '" + this.getDateTime() + "', " + "NGUOISUA = '" + this.userId + "', NoiDungNhap_fk = " + this.ndnId + ","+
			    " hdNCC_fk = " + this.hdNccId + ",YEUCAUNHAPHANG_FK='"+this.hdNccId+"', tigia = '" +this.tigia + "', GHICHU = N'"+ this.ghichu +"',nhomchiphi_fk='"+this.nhomchiphiId+"',TONGTIEN='"+this.tongtien+"' " +
				"where pk_seq = '" + this.id + "'";
			
			//System.out.println("Query update: " + query);
			
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi Nhan hang: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHANHANG_SANPHAM where nhanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHANHANGKHAC_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHANHANG_SP_CHITIET where nhanhang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHANHANG_SP_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if (this.spList.size() > 0)
			{
				for (int i = 0; i < spList.size(); i++)
				{
					ISanpham sp = spList.get(i);
					
					if (sp.getSoluongnhan() != "") // chi luu nhung san pham nguoi dung nhap so luong
					{
						String 	SanPham_FK = sp.getId();
						 
						double soluongnhan = 0;
						if(sp.getSoluongnhan().trim().length() <= 0)
						{
							this.msg = "Bạn phải nhập số lượng nhận cho sản phẩm: " + sp.getMa();
							db.getConnection().rollback();
							return false;
						}
						else
						{
							soluongnhan = Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""));
							System.out.println("so luong nhan " + soluongnhan);
							if(sp.getDungsai().trim().length() <= 0)
								sp.setDungsai("0");
							
							
							double slgMax = Double.parseDouble(sp.getSoluongMaxNhan().replaceAll(",", ""));
							System.out.println("so luong max nhan " + slgMax);
							if(soluongnhan > slgMax)
							{
								this.msg = "Tổng số lượng nhận của sản phẩm: " + sp.getMa() + " không được phép vượt quá tổng đặt (" + sp.getSoluongdat() + ") và dung sai cho phép ( " + sp.getDungsai() + "%). Vì thế bạn chỉ có thể nhận tối đa là ( " + slgMax + " )  ";
								db.getConnection().rollback();
								return false;
							}
						}
						
						String khonhan = sp.getKhonhanId().trim().length() <= 0 ? "null" : sp.getKhonhanId().trim();
						if(this.loaihanghoa.equals("0") && sp.getKhonhanId().trim().length() <= 0)
						{
							this.msg = "Vui lòng kiểm tra lại kho nhận của sản phẩm ( " + sp.getMa() + " ) ";
							db.getConnection().rollback();
							return false;
						}
						
						
						//Với Mua hàng nhập khẩu : lưu thêm số ngày vượt mức miễn phí , số lượng >> sau này tính chi phí
						 				
						double songayvuotmuc=0;
						double soluongvuotmuc=0;
						query = "insert ERP_NHANHANG_SANPHAM(NHANHANG_FK, MUAHANG_FK ,SANPHAM_FK,   DIENGIAI, DONVI, NGAYNHANDUKIEN, KHONHAN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, SONGAYVUOTMUC, SOLUONGVUOTMUC,THANHTIEN) " +
								"values('" + this.id + "', "+ sp.getMuahang_fk() +", " + sp.getId() + ",    N'" + sp.getDiengiai() + "', N'" + sp.getDvdl() + "', '" + sp.getNgaynhandukien() + "', " + khonhan + ", " +
									"'" + sp.getSoluongdat().replaceAll(",", "") + "',  '" + sp.getSoluongnhan().replaceAll(",", "") + "', '" + sp.getDungsai() + "', " + Double.parseDouble(sp.getDongia().replaceAll(",", "")) + ", '" + 
									sp.getTiente() + "', '" + sp.getTigiaquydoi() + "', '" + sp.getDongiaViet().replaceAll(",", "") + "' , "+ songayvuotmuc +", "+soluongvuotmuc+" ,'"+sp.getthanhtien().replace(",","")+"')";


						
						if (!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_NHANHANGKHAC_SANPHAM: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
						
						 
						
						double tongchitiet=0;
						//List<ISpDetail> detailList = sp.getSpDetail();
						List<DetailMeSp> detailList = sp.getListDetailMeSp();
						for (int j = 0; j < detailList.size(); j++)
						{
						//	ISpDetail detail = detailList.get(j);
							DetailMeSp detail = detailList.get(j);
							
							if (detail.getSoLuong().trim().length() > 0 && !detail.getSoLuong().equals("0") && detail.getSoLo() != "" && detail.getNgaySanXuat() != "" )
							{
								query = " INSERT INTO  ERP_NHANHANG_SP_CHITIET ( NHANHANG_FK, MUAHANG_FK,SANPHAM_FK, LANNHAN, SOLO, SOLUONG, GIATHEOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHANDUKIEN ,KHU_FK,"
										+ "NGAYNHAPKHO,BIN_FK,MATHUNG,MAME,MARQ,NSX_FK) " +
										"values('" + this.id + "',"+ sp.getMuahang_fk() +", '" + sp.getId() + "', '" + Integer.toString(j + 1) + "', '" + detail.getSoLo() + "', " +
										"'" + detail.getSoLuong().replaceAll(",", "") + "', "+ Double.parseDouble(sp.getDongia().replaceAll(",", "")) +", '" +detail.getNgaySanXuat() +"', '" + detail.getNgayHetHan() + "','" + sp.getNgaynhandukien() + "',NULL,'"+this.ngaynhanhang+"',"
												+ ""+detail.getKhuVuc()+",'"+detail.getMaThung()+"','"+detail.getMe()+"',"
														+"'"+detail.getMarrquet()+"',"
																+(detail.getNsxId().trim().length()==0?"NULL":detail.getNsxId())+")";
						
								
								tongchitiet = DinhDang.dinhdangso(tongchitiet) +DinhDang.dinhdangso(Double.parseDouble(detail.getSoLuong().replaceAll(",", "")));
											
								//System.out.println("ERP_NHANHANGKHAC_SP_CHITIET: " + query);
								if (!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: " + query;
									System.out.println(this.msg);
									db.getConnection().rollback();
									return false;
								}
							}
						}
						

						if(this.loaihanghoa.equals("0")){
							
							if( Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""))-DinhDang.dinhdangso(tongchitiet)  != 0 ){
								this.msg = "Vui lòng kiểm tra số lo chi tiết của sản phẩm :"+sp.getMa();
								db.getConnection().rollback();
								return false;
							}
							
						}
					}
				}
			}
			query = "update ERP_NHANHANG set	KHONHAN_FK = (SELECT DISTINCT KHONHAN FROM ERP_NHANHANG_SANPHAM WHERE NHANHANG_FK = PK_SEQ) "+
					"where pk_seq = '" + this.id + "'";
				
				//System.out.println("Query update: " + query);
				
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi Nhan hang: " + query;
					db.getConnection().rollback();
					return false;
				}
			query = "update erp_yeucaunhaphang set conhanhang = 1 where pk_seq = " + this.hdNccId;
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi erp_yeucaunhaphang: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e)
		{
			this.msg="Lỗi không thể cập nhật nhận hàng: "+e.getMessage();
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
	}
	
	
	private boolean IsCoKiemdinh() {

		try{
			 
			if(this.loaihanghoa.equals("0"))
			{
				String str_sp="";
				for (int i = 0; i < spList.size(); i++)
				{
					ISanpham sp = spList.get(i);
					
					if (sp.getSoluongnhan() != "") // chi luu nhung san pham nguoi dung nhap so luong
					{
						
						
							if(Double.parseDouble(sp.getSoluongnhan())>0){
								
								str_sp = (str_sp.length() >0? str_sp+",":"")+sp.getId();
							
							}
							 
					 
					}
				}
				if(str_sp.length()>0){
					String query="SELECT PK_SEQ,KIEMTRADINHLUONG, KIEMTRADINHTINH from ERP_SANPHAM SP WHERE (SP.KIEMTRADINHLUONG=1 OR SP.KIEMTRADINHTINH='1') AND  PK_SEQ IN ("+str_sp+")";
					ResultSet rs=db.get(query);
					if(rs.next()){
						rs.close();
						return true;
					} 
					rs.close();
					
				}
			}else{
				return false;
			}
					
			
		}catch(Exception err){
			err.printStackTrace();
		}
		return false;
	}

	public void updateDonmuahang(String poId)
	{
		this.poId = poId;
		
		// Cap nhat trang thai PO la hoan tat neu tong so luong nhan >= tong so luong dat
		if(this.id.length() > 0){
			String query = "select noidungnhan_fk from ERP_NHANHANGKHAC where pk_seq = '" + this.id + "'";
			ResultSet rsNoiDungNhan = db.get(query);
			try 
			{
				if(rsNoiDungNhan.next())
				{
					this.ndnId = rsNoiDungNhan.getString("noidungnhan_fk");
				}
				rsNoiDungNhan.close();
			} 
			catch (Exception e1) {}
		}
		
		if(this.ndnId.equals("100000"))
		{
			 
		}
		else
		{ }
	}
	
	public void close()
	{
		try{
		
			if(spList!=null)
			{
				spList.clear();
			}
			
			if(ndnRs!=null){
				ndnRs.close();
			}
			
			if(mahangmuaRs!=null){
				mahangmuaRs.close();
			}
			if(dvthRs!=null){
				dvthRs.close();
			}
			if(poRs!=null){
				poRs.close();
			}
			db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public int getNgayhethan()
	{
		return this.ngayhethan;
	}
	
	public void setNgayhethan(int ngayhethan)
	{
		this.ngayhethan = ngayhethan;
	}
	
	public String getNdnId() {
		
		return this.ndnId;
	}

	
	public void setNdnId(String mhmId) {
		
		this.ndnId = mhmId;
	}

	
	public ResultSet getNdnList() {
		
		return this.ndnRs;
	}

	
	public void setNdnList(ResultSet ndnlist) {
		
		this.ndnRs = ndnlist;
	}

	public String getLoaihanghoa() 
	{
		return this.loaihanghoa;
	}

	public void setLoaihanghoa(String loaihh) 
	{
		this.loaihanghoa = loaihh;
	}

	public String getNgaychot() 
	{
		return this.ngaychot;
	}

	public void setNgaychot(String ngaychot)
	{
		this.ngaychot = ngaychot;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public void setLdnId(String ndnId) {
		
		this.ldnId = ndnId;
	}

	
	public String getLdnId() {
		
		return this.ldnId;
	}

	
	public ResultSet getLdnList() {
		
		return this.ldnRs;
	}

	
	public void setLdnList(ResultSet ldnList) {
		
		this.ldnRs = ldnList;
	}

	
	public void setNccId(String ndnId) {
		
		this.nccId = ndnId;
	}

	
	public String getNccId() {
		
		return this.nccId;
	}

	
	public ResultSet getNccList() {
		
		return this.nccRs;
	}

	
	public void setNccList(ResultSet nccList) {
		
		this.nccRs = nccList;
	}

	
	public String getIsPONK() {
		
		return this.isPONK;
	}

	
	public void setIsPONK(String poNK) {
		
		this.isPONK = poNK;
	}

	
	public void setHdNccId(String hdNccId) {
		
		this.hdNccId = hdNccId;
	}

	
	public String getHdNccId() {
		
		return this.hdNccId;
	}

	
	public ResultSet getHdNccList() {
		
		return this.hdNccRs;
	}

	
	public void setHdNccList(ResultSet hdnccList) {
		
		this.hdNccRs = hdnccList;
	}


	
	public boolean kt_nhanhang_theoHDNCC_bivuot(String hoadonncc, String ncc_fk)
	{
		String query="";
		 query =  " select top(1) hd.pk_seq as hdId, hd_dmh.SANPHAM_FK,   \n"+
						" isnull((select SUM(nhsp.SOLUONGNHAN)      \n"+
						"from ERP_NHANHANGKHAC nh      \n"+
						"inner join ERP_NHANHANGKHAC_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANGKHAC_FK      \n"+
						"where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK  and nh.trangthai not in (3,4)     \n"+
						"group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0) - SUM(hd_dmh.SOLUONG)  as soluongconlai      \n"+
						"from ERP_HOADONNCC hd      \n"+
						"inner join ERP_PARK p on hd.park_fk = p.pk_seq      \n"+
						"inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK      \n"+
						"where p.ncc_fk="+ncc_fk+" and hd.sohoadon='"+hoadonncc.trim()+"'  and hd_dmh.SOLUONG >0      \n"+
						"group by hd.pk_seq, hd_dmh.SANPHAM_FK    \n"+
		 				"having  (   isnull((select SUM(nhsp.SOLUONGNHAN) \n"+
				 		"from ERP_NHANHANGKHAC nh  \n"+
		 				"inner join ERP_NHANHANGKHAC_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANGKHAC_FK  \n"+
				 		"where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK  \n"+
				 		"group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0)  - SUM(hd_dmh.SOLUONG)  ) > 0   ";	
					
		 
		 System.out.println(" Kt nhan hang vuot :" + query);
		 ResultSet rs = db.get(query);
		 
		try{
			String hd ="";
			double soluongconlai;
			while(rs.next()){
				 hd= rs.getString("hdId");
				 soluongconlai=  rs.getDouble("soluongconlai");
			}
			rs.close();
			if(hd.length() >0){
				this.msg = "Nhận hàng của hoá đơn "+ hd + " đang có sản phẩm bị quá số lượng so với hoá đơn  ";
				return true;
			}else{
				return false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	
	
	public void kt_capnhattrangthai_hoadonNCC(String hoadonncc, String ncc_fk, String nhanhang_ID)
	{
		String query="";
		 query =  " select top(1) hd.pk_seq as hdId, hd_dmh.SANPHAM_FK,    \n"+
						" isnull((select SUM(nhsp.SOLUONGNHAN)      \n"+
						"from ERP_NHANHANGKHAC nh      \n"+
						"inner join ERP_NHANHANGKHAC_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANGKHAC_FK      \n"+
						"where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK   and nh.trangthai not in (3,4)     \n"+
						"group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0) -  SUM(hd_dmh.SOLUONG) as soluongconlai      \n"+
						"from ERP_HOADONNCC hd      \n"+
						"inner join ERP_PARK p on hd.park_fk = p.pk_seq      \n"+
						"inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK      \n"+
						"where p.ncc_fk="+ncc_fk+" and hd.sohoadon='"+hoadonncc.trim()+"'  and hd_dmh.SOLUONG >0      \n"+
						"group by hd.pk_seq, hd_dmh.SANPHAM_FK    \n"+
		 				"having  (  isnull((select SUM(nhsp.SOLUONGNHAN) \n"+
				 		"from ERP_NHANHANGKHAC nh  \n"+
		 				"inner join ERP_NHANHANGKHAC_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANGKHAC_FK  \n"+
				 		"where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK  \n"+
				 		"group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0)  -  SUM(hd_dmh.SOLUONG) ) <>  0   ";	
					
		 System.out.println(" Kt nhan hang du HD chua :" + query);
		 ResultSet rs = db.get(query);
		 
		try{
			if(!rs.next()){
				query = " update erp_hoadonncc_donmuahang set is_nhanhang=1 where hoadonncc_fk=(select hdNCC_fk from ERP_NHANHANGKHAC where pk_seq ="+nhanhang_ID+") " ;
				db.update(query);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		
		}	
	}
	
	
	
	
	
	
	
	public boolean suaSoHoaDon()
	{
		
		try
		{
			this.db.getConnection().setAutoCommit(false);
			String query="UpDate ERP_NHANHANGKHAC set SoHoaDon='"+this.sohoadon+"',DienGiai=N'"+this.diengiai+"' where pk_Seq='"+this.id+"'";
			if(!this.db.update(query))
			{
				this.msg="Lỗi khi cập nhật số hóa đơn "+query;
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			this.msg="Lỗi khi cập nhật số hóa đơn(Exception) "+e.getMessage();
			e.printStackTrace();
			try
			{
				this.db.getConnection().rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}


	public String getLydonhan() {
	
		return this.lydo;
	}


	public void setLydo(String lydo) {
		this.lydo= lydo;
	}

	
	public String getPhongBan() {
		
		return this.pb;
	}

	
	public void setPhongBan(String phongban) {
		
		this.pb=phongban;
	}

	
	public boolean getIsKhoNhanQL_Khuvuc() {
		
		return this.IsKhoNhanQL_khuvuc;
	}

	
	public void setIsKhoNhanQL_Khuvuc(boolean bien) {
		
		this.IsKhoNhanQL_khuvuc=bien;
	}

	
	public ResultSet getrskhoCxl() {
		
		return this.RsKhoCXL;
	}

	
	public void setrskhoCxl(ResultSet rskhoCxl) {
		
		this.RsKhoCXL=rskhoCxl;
	}

	
	public String getKhoCxlId() {
		
		return this.KHoCxlId;
	}

	
	public void setKhoCxlId(String _KhoCxlId) {
		
		this.KHoCxlId=_KhoCxlId;
	}
	
	public Integer  getIs_saungayKS()
	{
		return this.is_saungayKS;
	}
	
	public void setIs_saungayKS(Integer is_saungayKS)
	{
		this.is_saungayKS= is_saungayKS;
	}
	
	public String getGhichu() {
		
		return this.ghichu;
	}
	
	public void setGhichu(String ghichu) {
		
		this.ghichu=ghichu;
	}

	public String getLoaimh() 
	{
		return this.loaimh;
	}

	public void setLoaimh(String loaimh) 
	{
		this.loaimh = loaimh;
	}
	
	public String getIsTudong() 
	{
		return this.isTudong;
	}

	public void setIsTudong(String isTudong) 
	{
		this.isTudong = isTudong;
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
	

	public List<KhuVucKho> getListKhuVucKho() {
		return listKhuVucKho;
	}

	public void setListKhuVucKho(List<KhuVucKho> listKhuVucKho) {
		this.listKhuVucKho = listKhuVucKho;
	}
	
	public List<KhuVucKho> getListKhuVucKhoCXL() {
		return listKhuVucKhoCXL;
	}

	public void setListKhuVucKhoCXL(List<KhuVucKho> listKhuVucKhoCXL) {
		this.listKhuVucKhoCXL = listKhuVucKhoCXL;
	}
}

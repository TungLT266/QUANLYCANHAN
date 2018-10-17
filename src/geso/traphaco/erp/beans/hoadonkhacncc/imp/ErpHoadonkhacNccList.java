package geso.traphaco.erp.beans.hoadonkhacncc.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadonkhacncc.IErpHoadonkhacNccList;

public class ErpHoadonkhacNccList extends Phan_Trang implements IErpHoadonkhacNccList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	String loaiDonMuaHang;
	
	String dvthId;
	ResultSet dvthRs;
	
	String nccTen;
	String tongtien;
	String msg;
	String task;
	String sodonmuahang;
	String loaisanphamid;
	String loaihanghoa = "";
	String sothamchieu="";
	
	ResultSet loaisanphamRs;
	
	ResultSet dmhRs;
	
	ResultSet nccRs;    
	String nccIds;
	ResultSet nspRs;
	String nspIds;
	
	String isdontrahang;
	String trangthai;
	
	ResultSet nguoitaoRs;    
	String nguotaoIds;
	
	String mactsp = "";
	String loai = "";
	
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	String npp_duocchon_id;
	String nppdangnhap;
	String tdv_dangnhap_id;
	
	
	dbutils db;
	private Utility util;
	
	public ErpHoadonkhacNccList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.dvthId = "";
		this.nccTen = "";
		this.tongtien = "";
		this.task = "";
		this.loaisanphamid="";
		this.msg = "";
		this.nccIds = "";
		this.nspIds = "";
		this.sodonmuahang = "";
		// 0 - DONMUA HANG, 1 phieu thanh toan
		this.isdontrahang = "0";
		this.trangthai = "";
		this.nguotaoIds = "";
		this.sothamchieu="";
		this.loaiDonMuaHang = "";
		this.npp_duocchon_id ="";
		this.loai = "";
		this.soItems = 25;
		currentPages = 1;
		num = 1;
		this.db = new dbutils();
		 util=new Utility();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
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

	public String getNCC() 
	{
		return this.nccTen;
	}

	public void setNCC(String ncc) 
	{
		this.nccTen = ncc;
	}

	public String getTongtiensauVat() 
	{
		return this.tongtien;
	}

	public void setTongtiensauVat(String ttsauvat)
	{
		this.tongtien = ttsauvat;
	}

	public void setmsg(String msg) 
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getDmhList() 
	{
		return this.dmhRs;
	}

	public void setDmhList(ResultSet dmhlist) 
	{
		this.dmhRs = dmhlist;
	}

	public void init(String search)
	{
		
		this.getNppInfo();
		String query = "";
		query = " SELECT HD.PK_SEQ AS dmhId ,HD.NGAYGHINHAN,HD.SOCHUNGTU,HD.MAUHOADON " +
					" ,HD.NGAYHOADON,HD.SOHOADON ,NCC.TEN,HD.MASOTHUE,HD.DIACHI  "+
					" ,HD.TONGTIENAVAT_VND  as TONGTIENAVAT,HD.NGAYTAO,HD.NGAYSUA ,tt.MA as TIENTE,  " +
					" hd.trangthai ,NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA "+
					" FROM ERP_HOADONKHACNCC HD "+
					" LEFT JOIN ERP_NHACUNGCAP NCC ON HD.NHACUNGCAP_FK=NCC.PK_SEQ "+ 
					" LEFT JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO "+
					" LEFT JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA " +
					"left join ERP_TIENTE tt on tt.PK_SEQ= HD.TIENTE_FK   " +
					" WHERE 1=1 AND HD.CONGTY_FK="+this.congtyId;
		if(this.tungay.length() >0 )
			query += " AND HD.NGAYHOADON >='" + this.tungay +"'" ; 
		if(this.denngay.length() >0)
			query += " AND HD.NGAYHOADON <='" + this.denngay+ "'" ;
		if(this.nccTen.length() >0)
			query += " AND ( NCC.TEN like N'%"+this.nccTen+"%'" + " OR NCC.MA LIKE N'%"+this.nccTen+"%' ) ";
		if(this.nguotaoIds.length() >0)
			query += " AND HD.NGUOITAO =" + this.nguotaoIds;
		if(this.trangthai.length() >0)
			query += " AND HD.TRANGTHAI =" + this.trangthai;
		
		System.out.println("query " + query);
		String query_init = createSplittingData_ListNew(this.db, soItems, 10, "dmhId desc, NGAYGHINHAN desc", query);
		this.dmhRs = db.get(query_init);
		
	 
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_HOADONKHACNCC ) ";
		this.nguoitaoRs = db.get(query);
		 
	}

	 
		
	public void DBclose() 
	{
		try 
		{
			if(this.dvthRs != null) 
				this.dvthRs.close();
			
			if(this.dmhRs != null) 
				this.dmhRs.close(); 
			
			if(this.nccRs != null) 
				this.nccRs.close(); 
			
			if(this.nspRs != null) 
				this.nspRs.close(); 
		}
		catch (SQLException e) {}
		this.db.shutDown();
	}

	public String getTask()
	{
		return this.task;
	}

	public void setTask(String task)
	{
		this.task = task;
	}
	
	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

	}

	
	public int getCurrentPage() {
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		this.currentPages = current;
	}

	
	public int[] getListPages() {
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		ResultSet rs = db.get("select count(*) as c from ERP_MUAHANG");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public ResultSet getNccRs() 
	{
		return this.nccRs;
	}

	public void setNccRs(ResultSet nccRs) 
	{
		this.nccRs = nccRs;
	}

	public void setNccIds(String nccIds) 
	{
		this.nccIds = nccIds;
	}

	public String getNccIds() 
	{
		return this.nccIds;
	}

	public ResultSet getNspRs() 
	{
		return this.nspRs;
	}

	public void setNspRs(ResultSet nspRs) 
	{
		this.nspRs = nspRs;
	}

	public void setNspIds(String nspIds)
	{
		this.nspIds = nspIds;
	}

	public String getNspIds()
	{
		return this.nspIds;
	}

	public void initBaoCao() 
	{
		this.dvthRs = db.get("select pk_seq, ten from ERP_DONVITHUCHIEN where pk_seq in  "+ util.quyen_donvithuchien(this.userId));
		this.nccRs = db.get("select pk_seq, ma as nccMa, ten as nccTen from erp_nhacungcap");
		this.nspRs = db.get("select PK_SEQ, TEN, DIENGIAI from NHOMSANPHAM where loainhom = '1'");
	}

	public String getSodonmuahang()
	{
		return this.sodonmuahang;
	}

	public void setSodonmuahang(String sodonmuahang) 
	{
		this.sodonmuahang = sodonmuahang;
	}

	
	public ResultSet getLoaisanpham() 
	{
		
		return this.loaisanphamRs;
	}

	
	public void setLoaisanpham(ResultSet loaisanpham) 
	{
		
		this.loaisanphamRs=loaisanpham;
	}

	
	public String getLoaisanphamid() {
		
		return this.loaisanphamid;
	}

	
	public void setLoaisanphamid(String loaisanpham) {
		
		this.loaisanphamid=loaisanpham;
	}

	public String getIsdontrahang() 
	{
		return this.isdontrahang;
	}

	public void setIsdontrahang(String dontrahang) 
	{
		this.isdontrahang = dontrahang;
	}

	public String getCongtyId()
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getCtyId() {
		
		return null;
	}

	
	public void setCtyId(String ctyId) {
		
		
	}

	
	public String getCty() {
		
		return null;
	}

	
	public void setCty(String cty) {
		
		
	}

	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		
		this.trangthai = trangthai;
	}

	
	public ResultSet getNguoitaoRs() {
		
		return this.nguoitaoRs;
	}

	
	public void setNguoitaoRs(ResultSet nguoitaoRs) {
		
		this.nguoitaoRs = nguoitaoRs;
	}

	
	public void setNguoitaoIds(String nspIds) {
		
		this.nguotaoIds = nspIds;
	}

	
	public String getNguoitaoIds() {
		
		return this.nguotaoIds;
	}

	@Override
	public String getMaCtSp() {
		return this.mactsp;
	}

	@Override
	public void setMaCtSp(String mact) {
		this.mactsp = mact;
	}

	@Override
	public String getLoaihanghoa() {
		return this.loaihanghoa;
	}

	@Override
	public void setLoaihanghoa(String loaihh) {
		this.loaihanghoa = loaihh;
	}


	public String getsothamchieu() {
		
		return this.sothamchieu;
	}


	public void setsothamchieu(String sothamchieu) {
		this.sothamchieu= sothamchieu;
	}


	public String getLoai() 
	{
		return this.loai;
	}

	public void setLoai(String loai) 
	{
		this.loai = loai;
	}@Override
	public void setSoItems(int soItems) {
		
		this.soItems = soItems;
	}
	@Override
	public int getSoItems() {
		
		return this.soItems;
	}

	@Override
	public String getLoaiDonMuaHang() {		
		return this.loaiDonMuaHang;
	}

	@Override
	public void setLoaiDonMuaHang(String loaiDonMuaHang) {
		this.loaiDonMuaHang = loaiDonMuaHang;
	}

	public void setNpp_duocchon_id(String npp_duocchon_id) {
		// TODO Auto-generated method stub
		this.npp_duocchon_id=npp_duocchon_id;
	}

	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		// TODO Auto-generated method stub
		this.tdv_dangnhap_id= tdv_dangnhap_id;
	}
	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppdangnhap = util.getIdNhapp(this.userId);
		}
		else
		{
			this.nppdangnhap = this.npp_duocchon_id;
		}
	}

	@Override
	public String getNpp_duocchon_id() {
		// TODO Auto-generated method stub
		return npp_duocchon_id;
	}

	public boolean ChotHoadonNcc(String dmhId) {
		// TODO Auto-generated method stub
		try{
			db.getConnection().setAutoCommit(false);
			String query=	" SELECT  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN like '13311000%' and CONGTY_FK= 100000 ) as TAIKHOANTHUE,ISNULL(HD.LOAIHANGHOA,0) AS LOAIHANGHOA " +
					", isnull(DV.DONVI,'') as DONVI  , isnull(SP.MA,'')  AS MASP,  HDSP.THANHTIEN,HD.NGAYGHINHAN,HD.SOHOADON,HD.NGAYHOADON,  " +
							" HD.DIENGIAI, HD.TIENTE_FK,HD.TYGIA, NCC.PK_SEQ AS NCCID , NCC.TAIKHOAN_FK ,HDSP.THANHTIENVND " +
							" ,HDSP.TIENVATVND,HDSP.TAIKHOANKT_FK ,HDSP.masclon_fk as masclon_fk, " + 
							" HDSP.SANPHAM_FK,HDSP.TENHANG,HDSP.SOLUONG,HDSP.DONGIA FROM ERP_HOADONKHACNCC HD " +  
							" INNER JOIN ERP_HOADONKHACNCC_SANPHAM HDSP ON HD.PK_SEQ= HDSP.HOADONKHACNCC_FK  " + 
							" INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ= HD.NHACUNGCAP_FK " +
							" LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=HDSP.SANPHAM_FK " +
							"  LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ =HDSP.DVT " + 
							" WHERE HD.PK_SEQ="+dmhId;
			ResultSet rs=db.get(query);
			System.out.println("aaaaaaaaaaaaaaaa"+query);
			while(rs.next()){
				
				String masp = rs.getString("MASP");
				String tensp = rs.getString("TENHANG"); 
				String donvitinh= rs.getString("DONVI");
				String taikhoanno="";
				String taikhoanco="";
				
				double THANHTIENVND_=rs.getDouble("THANHTIENVND");
				double THANHTIEN_NT=rs.getDouble("THANHTIEN");
				double TIENVATVND_=rs.getDouble("TIENVATVND");
				String doituongno="";
				String doituongco="";
				String loaidoituongno="";
				String loaidoituongco="";
				String masclon_fk=rs.getString("masclon_fk");
				String ngayghinhan=rs.getString("NGAYGHINHAN");
				
				
				String namNV = rs.getString("NGAYGHINHAN").substring(0, 4);
				String thangNV = rs.getString("NGAYGHINHAN").substring(5, 7);
				
				 
				
				if(THANHTIENVND_< 0){
					THANHTIENVND_=THANHTIENVND_*(-1);
					THANHTIEN_NT=THANHTIEN_NT*(-1);
					taikhoanno= rs.getString("TAIKHOAN_FK");
					taikhoanco= rs.getString("TAIKHOANKT_FK");
					
					loaidoituongno="Nhà cung cấp";
					doituongno=rs.getString("NCCID");
					
					if(rs.getString("LOAIHANGHOA").equals("1"))
					{
						loaidoituongno="Nhà cung cấp";
						loaidoituongco="Mã sữa chửa lớn";
						doituongno=rs.getString("NCCID");
						doituongco=rs.getString("MASCLON_FK");
						taikhoanco= rs.getString("TAIKHOANKT_FK");
						taikhoanno=rs.getString("TAIKHOAN_FK");
					}
					
				}else{
					
				 
					
					taikhoanco= rs.getString("TAIKHOAN_FK");
					taikhoanno= rs.getString("TAIKHOANKT_FK");
					
					loaidoituongco="Nhà cung cấp";
					doituongco=rs.getString("NCCID");
					
					if(rs.getString("LOAIHANGHOA").equals("1"))
					{
						loaidoituongco="Nhà cung cấp";
						loaidoituongno="Mã sữa chửa lớn";
						doituongco=rs.getString("NCCID");
						doituongno=rs.getString("MASCLON_FK");
						taikhoanno= rs.getString("TAIKHOANKT_FK");
						taikhoanco=rs.getString("TAIKHOAN_FK");
					}
					 
				}
				
			
				
	
				double soluong=rs.getDouble("SOLUONG");
				String dienGiai=rs.getString("DIENGIAI");
				String sohoadon=rs.getString("SOHOADON");
				double dongia_VND=Math.round(rs.getDouble("DONGIA")* rs.getDouble("TYGIA"));
				String tiente_fk=rs.getString("TIENTE_FK");
				
				double dongia_NT=rs.getDouble("DONGIA");
				
				double tygia= rs.getDouble("TYGIA");
				
				String msg1= util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, ngayghinhan, ngayghinhan, "Hóa đơn điều chỉnh của NCC",dmhId, taikhoanno, taikhoanco, "", 
						Double.toString(THANHTIENVND_), Double.toString(THANHTIENVND_), loaidoituongno, doituongno, loaidoituongco, doituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), 
						tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(THANHTIENVND_), 
						Double.toString(THANHTIEN_NT), "Thành tiền chưa VAT", "0" , dienGiai, sohoadon,"0" ,masp , tensp, donvitinh, "NULL");

				if(msg1.trim().length() > 0)
				{
				 
					db.getConnection().rollback();
					this.msg=msg1;
					return false;
				}
				
				
				
			if(TIENVATVND_<0){

					TIENVATVND_=TIENVATVND_*(-1);
					taikhoanno= rs.getString("TAIKHOAN_FK");
					taikhoanco= rs.getString("TAIKHOANTHUE");
					
					loaidoituongno="Nhà cung cấp";
					doituongno=rs.getString("NCCID");
					
					
					
				}else{
				 
					
					taikhoanno= rs.getString("TAIKHOANTHUE");
					taikhoanco= rs.getString("TAIKHOAN_FK");
					
					loaidoituongco="Nhà cung cấp";
					doituongco=rs.getString("NCCID");
					 
				}
			
			if(this.loaihanghoa.equals("1"))
			{
				loaidoituongno="";
				doituongno=rs.getString("NCCID");
				taikhoanco= rs.getString("TAIKHOANKT_FK");
				taikhoanno=rs.getString("TAIKHOAN_FK");
			}
				
			THANHTIENVND_=TIENVATVND_;

				
				  msg1= util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, ngayghinhan, ngayghinhan, "Hóa đơn điều chỉnh của NCC",dmhId, taikhoanno, taikhoanco, "", 
						Double.toString(THANHTIENVND_), Double.toString(THANHTIENVND_), loaidoituongno, doituongno, loaidoituongco, doituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), 
						tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(THANHTIENVND_), 
						Double.toString(THANHTIENVND_), "Tiền VAT", "0" , dienGiai, sohoadon,"0" ,masp , tensp, donvitinh, "NULL");

				if(msg1.trim().length() > 0)
				{
				 
					db.getConnection().rollback();
					this.msg=msg1;
					return false;
				}
				
				
			}
			rs.close();
			String masclon_fk="";
			String thanhtienvnd="";
			String loaichungtu="Hóa đơn điều chỉnh NCC";
			query="select B.MASCLON_FK,SUM(B.THANHTIENVND) AS THANHTIENVND from ERP_HOADONKHACNCC A \n" +
				  "LEFT JOIN ERP_HOADONKHACNCC_SANPHAM B ON A.PK_SEQ=B.HOADONKHACNCC_FK \n" +
				  "where a.loaihanghoa=1 and a.trangthai!=2 AND A.PK_SEQ="+dmhId+" \n" +
				  "group by B.MASCLON_FK";
			System.out.println("bbbbbbbbbbbbb"+query);
			ResultSet rs2 = db.get(query);
				if(rs2!=null)
				{
					while(rs2.next())
					{
						masclon_fk=rs2.getString("MASCLON_FK");
						thanhtienvnd=rs2.getString("thanhtienvnd");
					}
				}rs2.close();
				
			if(masclon_fk!=null&&masclon_fk!="")
			{
				query="INSERT INTO ERP_MASCLON_DIEUCHINH(MASCLON_FK,GIATRI,LOAICHUNGTU,SOCHUNGTU,BANGTHAMCHIEU,NGAYDIEUCHINH) VALUES ("+masclon_fk+","+thanhtienvnd+",N'"+loaichungtu+"',"+dmhId+",'ERP_HOADONKHACNCC_SANPHAM'"+",getdate())";
				System.out.println("asdsaasdasdsasd"+query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg="Không thể thêm giá trị mã sửa chữa :  "+query;
					return false;
				}
			}
			
			long soTTDuyet = 1;
			query = "SELECT ISNULL(MAX(SOTTDUYET), 0)+1 AS SOTTDUYET\r\n" + 
					"FROM ERP_HOADONKHACNCC \r\n" + 
					"WHERE MONTH(NGAYGHINHAN) = (SELECT MONTH(NGAYGHINHAN) FROM ERP_HOADONKHACNCC WHERE PK_SEQ = "+dmhId+")  AND SOTTDUYET IS NOT NULL\n";
			System.out.println("Query lay sott:"+ query);
			ResultSet rs1 = db.get(query);
			soTTDuyet = (rs1.next()) ? rs1.getLong("SOTTDUYET") : soTTDuyet;
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String ngayDuyet = "";
			ngayDuyet = dateFormat.format(cal.getTime());
			
			query="update ERP_HOADONKHACNCC SET TRANGTHAI='1',SOTTDUYET = "+soTTDuyet+", NGAYDUYET = '"+ngayDuyet+"'  where pk_seq="+dmhId +" and TRANGTHAI='0'";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				this.msg="Không thể cập nhật hóa đơn :  "+query;
				return false;
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
			
			
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			this.msg=er.getMessage();
			return false;
		}
	}
	
}

package geso.traphaco.erp.beans.dutoanvattu.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dutoanvattu.IErpDutoanvattuList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpDutoanvattuList extends Phan_Trang implements IErpDutoanvattuList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String nppId;
	String tungay;
	String denngay;
	
	String dvthId;
	ResultSet dvthRs;
	
	String nccTen;
	String tongtien;
	String msg;
	String task;
	String madutoanvt;
	String loaisanphamid;
	String loaihanghoa = "";
	String madnmh="";
	
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
	
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	private Utility util;
	List<IThongTinHienThi> hienthiList;
	
	public ErpDutoanvattuList()
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
		this.madutoanvt = "";
		// 0 - DONMUA HANG, 1 phieu thanh toan
		this.isdontrahang = "0";
		this.trangthai = "";
		this.nguotaoIds = "";
		this.madnmh="";
		
		this.soItems = 25;
		currentPages = 1;
		num = 1;
		this.db = new dbutils();
		 util=new Utility();
		 this.hienthiList = new ArrayList<IThongTinHienThi>();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
		this.nppId = util.getIdNhapp(userId);
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
		String query = "";
		
		this.dvthRs = db.get("select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = 1 ");		
		this.nguoitaoRs = db.get("select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_DUTOANVATTU ) ");
		
		if(search.length() <= 0)
		{
			query = "SELECT DT.PK_SEQ AS DTID, DT.DENGHI_FK, DT.NGAY, DT.TRANGTHAI, NV1.TEN AS NGUOITAO, NV2.TEN AS NGUOISUA, DT.LOAIHANGHOA_FK, \n"+
					"   ISNULL(DT.DACHOT,'') DACHOT, DT.NGAYTAO, DT.NGAYSUA, \n"+
					"	(SELECT COUNT(*) FROM ERP_CHUCDANH WHERE NHANVIEN_FK = "+ this.userId +" AND DVTH_FK = DT.DONVITHUCHIEN_FK and trangthai = 1 and DUYETDTVT = 1) DUYET \n"+
					"FROM ERP_DUTOANVATTU DT INNER JOIN NHANVIEN NV1 ON DT.NGUOITAO = NV1.PK_SEQ \n"+
				    "     INNER JOIN NHANVIEN NV2 ON DT.NGUOISUA = NV2.PK_SEQ    \n"+
				    " WHERE DT.PK_SEQ > 0 AND DT.CONGTY_FK = "+ this.congtyId ;
		}
		else
		{
			query = search + " AND DT.CONGTY_FK = "+ this.congtyId;
		}
		
		if(madnmh.trim().length() > 0)
			query += " and DT.DENGHI_FK like '%" + madnmh + "%'";
		
		if(tungay.length() > 0)
			query += " and DT.NGAY >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and DT.NGAY <= '" + denngay + "'";
		
		if(dvthId.length() > 0)
			query += " and dt.DONVITHUCHIEN_FK = '" + dvthId + "'";
		
		if(this.loaihanghoa.length() > 0)
			query += " and dt.LOAIHANGHOA_FK = '" + this.loaihanghoa + "'";
		
		if(this.nguotaoIds.trim().length() > 0)
			query += " and DT.nguoitao = '" + this.nguotaoIds + "' ";
		
		if(this.loaihanghoa.trim().length()> 0){
			query += " and dt.LOAIHANGHOA_FK = '" + this.loaihanghoa+ "'";
		}
			
		if(mactsp.trim().length()> 0){
			query += " and dt.PK_SEQ in ( SELECT DTVT_FK FROM ERP_DUTOANVATTU_SANPHAM WHERE SANPHAM_FK = '"+ mactsp +"' )";
		}
			
		if(this.madutoanvt.length() > 0)
		{
			query += " and DT.PK_SEQ like '%" + this.madutoanvt + "%' ";
		}
		
		if(trangthai.trim().length() > 0)
		{
			if(trangthai.equals("-1")){  // Chưa chốt
				query += " and ISNULL(DT.DACHOT,0) = 0 and dt.trangthai = 0 ";
			}	
			else if(trangthai.equals("0")){ // Chưa duyệt
				query += " and ISNULL(DT.DACHOT,0) = 1  and dt.trangthai = 0 ";
			}
			else if(trangthai.equals("1")){  // Đã duyệt
				query += " and dt.trangthai = 1  ";
			}
			else if(trangthai.equals("2")){  // Hoàn tất
				query += " and dt.trangthai = 2  ";
			}
			else if(trangthai.equals("3")){  // Đã xóa
				query += " and dt.trangthai = 3  ";
			}	
		}

		
		if(mactsp.trim().length() > 0)
		{
			query +=" and a.pk_seq in (" +
					"     select distinct muahang_fk from erp_muahang_sp where sanpham_fk in ( select distinct pk_seq from erp_Sanpham where MA like N'%" + mactsp + "%' ) " +
					" ) ";
		}
		

		
		
		// Phân quyền nhân viên theo phòng ban, mặc định admin được nhìn thấy
		if( !this.userId.equals("100002")  && util.GetquyenNew("ErpDutoanvattuListSvl", "",userId)[9]==0) {
			query += " and NV1.PHONGBAN_FK in ( select  " +
					" distinct PHONGBAN_FK from NHANVIEN where PK_SEQ ="+this.userId+" )";
		}
		
		
		if( !this.userId.equals("100002") && checkTruongPhong() == false && util.GetquyenNew("ErpDutoanvattuListSvl", "",userId)[9]==0){
			query += " and DT.NGUOITAO = "+ this.userId;
		}
		
		
		String sql = createSplittingData_ListNew(this.db, soItems, 10, "DTID desc", query);
		ResultSet rs = db.get(sql);
		
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		if(rs!= null)
		{
			try
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{
					//LAY DINH KHOAN KE TOAN
					String nccRs = LayNCCList(rs.getString("DTID"));
					ResultSet rsTT = db.getScrol(nccRs);
						
						ht = new ThongTinHienThi();	
						
						ht.setId(rs.getString("DTID"));
						ht.setSochungtu(rs.getString("DENGHI_FK"));
						ht.setNgaymua(rs.getString("NGAY"));
						ht.setTrangthai(rs.getString("TRANGTHAI"));
						ht.setDuyet(rs.getString("DUYET"));
						ht.setDachot(rs.getString("DACHOT")); 
						ht.setNgaytao(rs.getString("NGAYTAO"));
						ht.setNgaysua(rs.getString("NGAYSUA"));
						ht.setNguoitao(rs.getString("NGUOITAO"));
						ht.setNguoisua(rs.getString("NGUOISUA"));
						ht.setloaihanghoa(rs.getString("loaihanghoa_fk"));
						
						ht.setNhaCCList(rsTT);
						htList.add(ht);				
				}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		this.hienthiList = htList;
	}

	// kiem tra co phai truong phong hay khong?
	private boolean checkTruongPhong(){
		try{
			String sql = " select NHANVIEN_FK from ERP_CHUCDANH where isTP =1";
			ResultSet rs = this.db.get(sql);
			List<String> list = new ArrayList<String>();
			if(rs!=null){
				while(rs.next()){
					list.add(rs.getString("NHANVIEN_FK"));
					
				}
				rs.close();
			}
			
			for(int i=0; i< list.size(); i++){
				if(this.userId.equals(list.get(i))){
					return true;
				}
			}
			return false;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
		
	
	private String LayNCCList(String dtId) 
	{
		String query = " SELECT NCC.PK_SEQ , NCC.MA + ' - ' + NCC.TEN AS TEN "
				     + " FROM ERP_DUTOANVATTU_NCC DT INNER JOIN ERP_NHACUNGCAP NCC ON DT.NCC_FK = NCC.PK_SEQ  "
				     + " WHERE DT.DTVT_FK = "+ dtId +" " ;
		return query;
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
		catch (SQLException e) {
			e.printStackTrace();
		}
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
		ResultSet rs = db.get("select count(*) as c from ERP_DUTOANVATTU");
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
		this.dvthRs = db.get("select pk_seq, ten from ERP_DONVITHUCHIEN where 1=1");
		this.nccRs = db.get("select pk_seq, ma as nccMa, ten as nccTen from erp_nhacungcap where trangthai = '1' and duyet = '1' and congty_fk = "+ this.congtyId +" ");
		this.nspRs = db.get("select PK_SEQ, TEN, DIENGIAI from NHOMSANPHAM where loainhom = '1'");
	}

	public String getMadutoanvt()
	{
		return this.madutoanvt;
	}

	public void setMadutoanvt(String madutoanvt) 
	{
		this.madutoanvt = madutoanvt;
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


	public String getMadnmh() 
	{	
		return this.madnmh;
	}


	public void setMadnmh(String madnmh) 
	{
		this.madnmh= madnmh;
	}


	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}
	
	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
	}

	public String ConvertPO(String dtId, String nccId)
	{
		String msg = "";
		String query = "";
		
		dbutils db = new dbutils();

		try 
		{
			db.getConnection().setAutoCommit(false);
			
			// Kiểm tra NCC chọn có nằm trong ds NCC dự toán của tất cả SP không
			
			query =
					" SELECT DISTINCT \n"+
					"  ( CASE WHEN COUNT(DISTINCT TAISAN_FK)> 0 THEN COUNT(DISTINCT TAISAN_FK)  \n"+
					"         WHEN COUNT(DISTINCT SANPHAM_FK)> 0 THEN COUNT(DISTINCT SANPHAM_FK)  \n"+
					"         WHEN COUNT(DISTINCT CCDC_FK)> 0 THEN COUNT(DISTINCT CCDC_FK)  \n"+
					"         ELSE COUNT(DISTINCT CHIPHI_FK) END   \n"+
					"  ) -        \n"+
					"  (select case when COUNT(DISTINCT TAISAN_FK)> 0 then COUNT(DISTINCT TAISAN_FK)  \n"+
					"		  when COUNT(DISTINCT SANPHAM_FK)> 0 then COUNT(DISTINCT SANPHAM_FK)  \n"+
					"		  when COUNT(DISTINCT CCDC_FK)> 0 then COUNT(DISTINCT CCDC_FK)  \n"+
					"		  else COUNT(DISTINCT CHIPHI_FK) end          \n"+
					"  from ERP_DUTOANVATTU_SANPHAM           \n"+
					"   where DTVT_FK = DT.DTVT_FK AND NCC_FK = "+ nccId +"  \n"+
					"   group by DTVT_FK  \n"+
					"   ) ktra,         \n"+
					"   (select MA + ' - ' + TEN  from ERP_NHACUNGCAP where PK_SEQ = "+ nccId +" ) TENNCC   \n"+
					" FROM ERP_DUTOANVATTU_SANPHAM DT   \n"+
					" WHERE DT.DTVT_FK = "+ dtId +" and DT.soluong > 0 \n"+
					" GROUP BY DTVT_FK  \n";
			
			System.out.println("Câu kiểm tra "+query);
			ResultSet rs = db.get(query);
			int dem = 0;
			String nccTen = "";
			
			if(rs!= null)
			{
				while(rs.next())
				{
					nccTen = rs.getString("TENNCC");
					dem = rs.getInt("ktra");
				}rs.close();
			}
			
			if(dem > 0)
			{
				msg = "Nhà cung cấp " + nccTen  +" không có trong dự toán của tất cả sản phẩm. Vui lòng kiểm tra lại";
				db.getConnection().rollback();
				return msg;
			}
			
			// Cập nhật NCC 
			
			query = "UPDATE ERP_DUTOANVATTU SET NCCCHON_FK = "+ nccId +" WHERE PK_SEQ = "+ dtId +" ";
			if(!db.update(query))
			{
				msg = "Khong the cap nhat Du toan " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			// Chèn bảng tổng 
					
//			String ngaymuahang = "";
		    String loaihh = "";
		    String dvthId = "";
			double tongtienBvat = 0;
			double Vat = 0;
			double tongtienAvat = 0;
			
						
			query = "SELECT DT.NCCCHON_FK, DT.DONVITHUCHIEN_FK, DT.LOAIHANGHOA_FK, LOAISANPHAM_FK, NGUONGOCHH , DT.TIENTE_FK, DT.TYGIAQUYDOI \n"+
					"FROM ERP_DUTOANVATTU DT \n"+
					"WHERE DT.PK_SEQ = "+ dtId +" ";
			System.out.println("Câu lấy TT "+query);
			ResultSet rsMH = db.get(query);

		    
			if(rsMH != null)
			{
				while(rsMH.next())
				{
//					ngaymuahang = getDateTime();
					loaihh = rsMH.getString("LOAIHANGHOA_FK");
					dvthId = rsMH.getString("DONVITHUCHIEN_FK");
					
					// SOPO
					query=	" SELECT ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT, (SELECT PREFIX FROM ERP_DONVITHUCHIEN WHERE PK_SEQ ="+dvthId+" ) AS PREFIX "+
							" FROM ERP_MUAHANG  DMH WHERE SUBSTRING(NGAYMUA, 0, 5) = "+getDateTime().substring(0, 4)+ 
							" and     DMH.DONVITHUCHIEN_FK="+dvthId;
					String soPO = "";
					int sotutang_theonam = 0;
					ResultSet rsPO = db.get(query);  
					if(rsPO.next())
					{
						sotutang_theonam =  (rsPO.getInt("maxSTT") + 1 );
						String prefix = rsPO.getString("PREFIX");
						String namPO = getDateTime().substring(2, 4);
						String chuoiso= ("0000"+ Integer.toString(sotutang_theonam)).substring(("0000"+ Integer.toString(sotutang_theonam)).length()-4,("0000"+ Integer.toString(sotutang_theonam)).length());
						
						soPO = prefix + "-" +   chuoiso+ "/" + getDateTime().substring(5, 7) + "/" + namPO;
				 
					}
					rsPO.close();
					
					// Chèn PO
					query = "Insert into Erp_MuaHang(LOAI, DTVT_FK, SOPO, NgayMua, DonViThucHien_FK, NhaCungCap_FK, LoaiHangHoa_FK, LoaiSanPham_FK,  TrangThai, NgayTao, NgaySua, NguoiTao, NguoiSua, NguonGocHH, TienTe_FK, TyGiaQuyDoi,  congty_fk)" +
							" Values('2', "+ dtId +", '"+ soPO +"', '"+ getDateTime() +"', '" + rsMH.getString("DONVITHUCHIEN_FK") + "'," + nccId + "," + rsMH.getString("LOAIHANGHOA_FK") + ", " + rsMH.getString("LOAISANPHAM_FK") + ", '0', " +
						    " '" + getDateTime() + "', '" + getDateTime() + "', " + this.userId + ", "+ this.userId +", '" + rsMH.getString("NGUONGOCHH") + "', '" + rsMH.getString("TIENTE_FK") + "'," + rsMH.getString("TYGIAQUYDOI") + "," + this.congtyId + ")";
					
					System.out.println("Insert into Erp_MuaHang " + query);
					if (!db.update(query))
					{
						msg = "Khong the tao moi Mua hang: " + query;
						db.getConnection().rollback();
						return msg;
					}
					
				}rsMH.close();
			}
			
			String dmhIdCurrent = "";
			query = "select SCOPE_IDENTITY() as dtId";
			ResultSet rsDmh = db.get(query);
			if (rsDmh.next())
			{
				dmhIdCurrent = rsDmh.getString("dtId");
				rsDmh.close();
			}
			
			// Chèn SP
			query = " SELECT DT.PK_SEQ, ISNULL(DT.TYGIAQUYDOI,1) AS TYGIA, SP.SANPHAM_FK, isnull(SP.TAISAN_FK,0) TAISAN_FK, isnull(SP.CCDC_FK,0) CCDC_FK, SP.CHIPHI_FK, SP.SOLUONG, SP.DONGIA, SP.VAT, SP.DONVI, SP.THANHTIEN, isnull(SP.DIENGIAI,'') DIENGIAI, isnull(SP.DONVI, '') DONVI \n"+
					" FROM ERP_DUTOANVATTU DT INNER JOIN ERP_DUTOANVATTU_SANPHAM SP ON DT.PK_SEQ = SP.DTVT_FK AND DT.NCCCHON_FK = SP.NCC_FK \n"+
					" WHERE DT.PK_SEQ = "+ dtId +" ";
					System.out.println("LAY SP "+query);
					ResultSet rsMH_CT = db.get(query);
					
					if(rsMH_CT != null)
					{
						while(rsMH_CT.next())
						{
							double dongiaviet = rsMH_CT.getDouble("DONGIA")*rsMH_CT.getDouble("TYGIA");
							double thanhtienviet =  Math.round(rsMH_CT.getDouble("SOLUONG") * dongiaviet);
							
							String taisan_fk = "";
							taisan_fk = rsMH_CT.getString("TAISAN_FK");
							
							String CCDC_FK = "";
							CCDC_FK = rsMH_CT.getString("CCDC_FK");
							
							if(loaihh.equals("1"))// TÀI SẢN CỐ ĐỊNH
							{					
								if(taisan_fk.equals("0")) // TÀI SẢN NÀY CHƯA CÓ MÃ TRONG DỮ LIỆU NỀN - TỰ ĐỘNG TẠO MÃ TÀI SẢN
								{
									query =	
									"INSERT INTO ERP_TAISANCODINH(ma, ten, diengiai, congty_fk, soluong ,dongia, thanhtien, sothangKH, thangbatdauKH, trangthai, " +
									"nguoitao, nguoisua, ngaytao, ngaysua, donvi) \n" +
									"SELECT (SELECT 'TS'+ cast((SELECT MAX(pk_seq)+1 FROM ERP_TAISANCODINH ) as nvarchar(50))) , N'" +rsMH_CT.getString("DIENGIAI") +"' , N'" +rsMH_CT.getString("DIENGIAI") +"', '" +this.congtyId+"'," + 
									""+rsMH_CT.getDouble("SOLUONG")+", "+dongiaviet+", "+thanhtienviet+", " +
									"'0', '0', '0' ," + this.userId + ", " + this.userId + ", '" + this.getDateTime() + "', " +
									"'" + this.getDateTime() + "', N'"+rsMH_CT.getString("DONVI")+"' \n "+
									"FROM ERP_DUTOANVATTU WHERE PK_SEQ = " + dtId + " ";
									
									System.out.println(query);
									if (!db.update(query))
									{
										msg = "Khong the tao moi Mua hang - San pham: " + query;
										db.getConnection().rollback();
										return msg;
									}
																		
									query = "select SCOPE_IDENTITY() as tsId";
									
									System.out.println(query);
									ResultSet rsTs = db.get(query);
									if (rsTs.next())
									{
										taisan_fk = rsTs.getString("tsId");
										rsTs.close();
									}
									
									System.out.println("taisan_fk:"+taisan_fk);
								}
							}
							else if(loaihh.equals("3")) // CÔNG CỤ DỤNG CỤ
							{
								if(CCDC_FK.equals("0")) // CCDC NÀY CHƯA CÓ MÃ TRONG DỮ LIỆU NỀN - TỰ ĐỘNG TẠO MÃ TÀI SẢN
								{
									query =	
									"INSERT INTO ERP_CONGCUDUNGCU(ma, diengiai, congty_fk, soluong ,dongia, thanhtien, sothangKH, thangbatdauKH, trangthai, " +
									"nguoitao, nguoisua, ngaytao, ngaysua, donvi) \n" +
									"SELECT (SELECT 'CCDC'+ cast((SELECT MAX(pk_seq)+1 FROM ERP_CONGCUDUNGCU ) as nvarchar(50))), N'" +rsMH_CT.getString("DIENGIAI") +"', '" +this.congtyId+"'," + 
									""+rsMH_CT.getDouble("SOLUONG")+", "+dongiaviet+", "+thanhtienviet+", " +
									"'0', '0', '0' ," + this.userId + ", " + this.userId + ", '" + this.getDateTime() + "', " +
									"'" + this.getDateTime() + "', N'"+rsMH_CT.getString("DONVI")+"' \n "+
									"FROM ERP_DUTOANVATTU WHERE PK_SEQ = " + dtId + " ";
									
									if (!db.update(query))
									{
										msg = "Khong the tao moi Mua hang - San pham: " + query;
										db.getConnection().rollback();
										return msg;
									}
																		
									query = "select SCOPE_IDENTITY() as tsId";
									ResultSet rsTs = db.get(query);
									if (rsTs.next())
									{
										CCDC_FK = rsTs.getString("tsId");
										rsTs.close();
									}
									
								}
							}
							
							if(taisan_fk.equals("0")) taisan_fk = "null";
							if(CCDC_FK.equals("0")) CCDC_FK = "null";
							
							query = " insert into ERP_MUAHANG_SP ( muahang_fk, sanpham_fk, taisan_fk, chiphi_fk, ccdc_fk, donvi, soluong, dongia, vat, thanhtien, dongiaviet, thanhtienviet, soluong_new, dongia_new ) " +
									" values(" + dmhIdCurrent + ", " + rsMH_CT.getString("SANPHAM_FK") + ", " + taisan_fk + ", " + rsMH_CT.getString("CHIPHI_FK") + ", " + CCDC_FK + ", N'" + rsMH_CT.getString("DONVI") + "', "+
											 " " + rsMH_CT.getString("SOLUONG") + "," + rsMH_CT.getString("DONGIA") + "," + rsMH_CT.getDouble("VAT") + ", " + rsMH_CT.getString("THANHTIEN") + ", "+ dongiaviet +", "+ thanhtienviet +", " + rsMH_CT.getString("SOLUONG") + ", " + rsMH_CT.getString("DONGIA")+" )";
							
							System.out.println("2.Insert Into Erp_MuaHang_SP :" + query);
							
							if (!db.update(query))
							{
								msg = "Khong the tao moi Mua hang - San pham: " + query;
								db.getConnection().rollback();
								return msg;
							}
							
							Vat = rsMH_CT.getDouble("VAT");
							tongtienBvat += rsMH_CT.getDouble("THANHTIEN");
							
						}rsMH_CT.close();
					}
			
			// Cập nhật lại tiền cho PO
			tongtienAvat = tongtienBvat + Math.round(tongtienBvat*Vat/100) ;
			
			query = "UPDATE ERP_MUAHANG SET SOPO = '"+ dmhIdCurrent +"', TONGTIENBVAT = "+ tongtienBvat +", VAT = "+ Vat +", TONGTIENAVAT = "+ tongtienAvat +" WHERE PK_SEQ = "+ dmhIdCurrent +" ";
			if(!db.update(query))
			{
				msg = "Khong the cap nhat Du toan " + query;
				db.getConnection().rollback();
			}		
					
			// Kiểm tra có vượt ngân sách không (Chi phí/ TSCĐ)
				boolean vuotNganSach = false;
			    String nam = getDateTime().substring(0, 4);
				
				if(loaihh.equals("2")) //chi phi, dich vu
				{
					
					query=" INSERT INTO ERP_MUAHANG_SP_HOADON   (	MUAHANG_FK ,MUAHANG_SP_FK  ,MAHOADON  ,	MAUSOHOADON  ,	KYHIEU  ,SOHOADON  ,NGAYHOADON , "+
						  " TENNHACUNGCAP  ,MASOTHUE  ,TIENHANG  ,	THUESUAT  ,	TIENTHUE  ,	TONGCONG   ,GHICHU   )    "+
						  " select mhsp.MUAHANG_FK,mhsp.PK_SEQ,'','','','','','','',mhsp.THANHTIEN,mh.VAT,mhsp.THANHTIEN*mh.VAT/100,mhsp.THANHTIEN+mhsp.THANHTIEN*mh.VAT/100,'' "+
						  " from ERP_MUAHANG_SP mhsp " +
						  " inner join ERP_MUAHANG mh on mh.PK_SEQ=mhsp.MUAHANG_FK "+
						  " where MUAHANG_FK not in (select MUAHANG_FK from ERP_MUAHANG_SP_HOADON) "+
						  " and mh.LOAIHANGHOA_FK='2' and mh.pk_seq= "+dmhIdCurrent;
					if (!db.update(query))
					{
						this.msg = "Khong the tao moi Mua hang - San pham: " + query;
						db.getConnection().rollback();
						return msg;
					}
					
					
					query = " select isnull(ngansachConLai.conLai, 0) - muahang.tongGiaTri as cotheSuDung  " +
							"from " +
							"( " +
								"select CHIPHI_FK, SUM(SOLUONG * DONGIA) as tongGiaTri  " +
								"from ERP_MUAHANG_SP where MUAHANG_FK = '" + dmhIdCurrent + "' group by CHIPHI_FK  " +
							")  " +
							"muahang left join " +
							"( " +
								"select nganSach.CHIPHI_FK, isnull(nganSach.DUTOAN, 0) - ISNULL( dukienChi.tongduKien, 0) as conLai  " +
								"from " +
								"( " +
									"select CHIPHI_FK, DUTOAN, THUCCHI   " +
									"from ERP_LAPNGANSACH_CHIPHI   " +
									"where LAPNGANSACH_FK in ( select pk_seq from ERP_LAPNGANSACH where NAM = '" + nam + "' and TRANGTHAI = '1' )   " +
										"and DONVITHUCHIEN_FK = '" + dvthId + "'   " +
								")  " +
								"nganSach left join " +
								"(  " +
									"select CHIPHI_FK, SUM(b.SOLUONG * b.DONGIAVIET) as tongduKien  " +
									"from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK  " +
									"where a.LOAIHANGHOA_FK = '2' and a.DONVITHUCHIEN_FK = '" + dvthId + "'   " +
										"and SUBSTRING(ngaymua, 0, 5) = '" + nam + "' and a.pk_seq != '" + dmhIdCurrent + "'  " +
									"group by CHIPHI_FK  " +
								") " +
								"dukienChi on nganSach.CHIPHI_FK = dukienChi.CHIPHI_FK   " +
							")  " +
							"ngansachConLai on muahang.CHIPHI_FK = ngansachConLai.CHIPHI_FK";
					
					System.out.println("___Check ngan sach chi phi: " + query);
					
					ResultSet rsCheckNS = db.get(query);
					while(rsCheckNS.next())
					{
						if(rsCheckNS.getDouble("cotheSuDung") < 0)
						{
							vuotNganSach = true;
						}
					}
					rsCheckNS.close();
				}
				else    //Tai san co dinh
				{
					if(loaihh.equals("1"))
					{
						query = "select b.pk_seq as taisan_fk, a.SoLuong, a.DonGia, a.THANHTIEN as tongNganSach, a.SOTHANGKH, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12   " +
								"from ERP_LAPNGANSACH_TAISAN a inner join ERP_TAISANCODINH b on a.PK_SEQ = b.LAPNGANSACH_TAISAN_FK  " +
								"where DONVITHUCHIEN_FK = '" + dvthId + "' and b.pk_seq in ( select TAISAN_FK from ERP_MUAHANG_SP where MUAHANG_FK = '" + dmhIdCurrent + "' )";
						
						System.out.println("1.Check ngan sach tai san: " + query);
						
						ResultSet rsNgansach = db.get(query);
						while(rsNgansach.next())
						{
							String taisanId = rsNgansach.getString("taisan_fk");
							double ngansachTong = rsNgansach.getDouble("tongNganSach");
							double tongKhauhao = 0;
							
							int sothangKH = rsNgansach.getInt("SOTHANGKH");
							double dongia = rsNgansach.getDouble("DonGia");
							
							int thangthu = 0;
							for(int i = 1; i <= 12; i++)
							{
								int T1 = rsNgansach.getInt("T" + Integer.toString(i));
								if(T1 > 0)
								{
									double khaukhao_thang = ( ( T1 * dongia ) / sothangKH ) * (12 - thangthu);
									thangthu ++;
									
									tongKhauhao += khaukhao_thang;
								}
							}
							
							
							//Lay tat cac cac mua hang cua tai san nay, tinh tong Ngansach da su dung va tong khau hao (du kien)
							query = "select a.NGAYMUA, b.SOLUONG, b.DONGIAVIET   " +
									"from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK " +
									"where b.TAISAN_FK = '" + taisanId + "' and a.DONVITHUCHIEN_FK = '" + dvthId + "'";
							
							System.out.println("2.Check ngan sach tai san da su dung: " + query);
							ResultSet rsTaisan = db.get(query);
							
							double tongNganSach_Mua = 0;
							double tongKhauHao_Mua = 0;
							
							if(rsTaisan != null)
							{
								while(rsTaisan.next())
								{
									String thangbdKhauHao_DuKien = rsTaisan.getString("NGAYMUA").split("-")[1];
									
									int soluongMua = rsTaisan.getInt("SOLUONG");
									double dongiaMua = rsTaisan.getDouble("DONGIAVIET");
									
									tongNganSach_Mua += soluongMua * dongiaMua;
									
									tongKhauHao_Mua +=  ( soluongMua * dongiaMua / sothangKH ) * ( 12 - Integer.parseInt(thangbdKhauHao_DuKien) );
								}
								rsTaisan.close();
							}
							
							
							if( ( tongNganSach_Mua > ngansachTong ) || ( tongKhauHao_Mua > tongKhauhao ) )
							{
								vuotNganSach = true;
								rsNgansach.close();
								break;
							}
							
						}
						rsNgansach.close();
						
						System.out.println("3.Check tai san vuot ngan sach: " + vuotNganSach);
					}
				}
			
			
					
				// insert nguoi duyet PO 
				// NẾU CÓ CHÍNH SÁCH DUYỆT DÀNH CHO NCC CỦA ĐƠN MUA HÀNG, THÌ LẤY CHÍNH SÁCH DUYỆT ĐÓ
		query = "SELECT	NCC.CHUCDANH_FK, NCC.QUYETDINH " +
				"FROM ERP_MUAHANG MUAHANG  " +
				"INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " + 
				"INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK " + 
				"WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "' " +

				"UNION ALL " +
				// NẾU KO CÓ CHÍNH SÁCH DUYỆT DÀNH CHO SẢN PHẨM CỦA ĐƠN MUA HÀNG VÀ KO CÓ CHÍNH SÁCH DUYỆT CHO NCC CỦA ĐƠN MUA HÀNG
				"SELECT	SP.CHUCDANH_FK, SP.QUYETDINH " + 
				"FROM ERP_MUAHANG MUAHANG  " +  
				"INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +  
				"INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   " + 
				"AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + dmhIdCurrent + "') " +  
				"LEFT JOIN " + 
				"(  " + 
				"	SELECT	COUNT(*) AS NUM " + 
				"	FROM ERP_MUAHANG MUAHANG   " + 
				"	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +  
				"	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK " +  
				"	AND NCC.NCC_FK IN (SELECT NCC_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + dmhIdCurrent + "')  " + 
				"	WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "'   " + 
				")DUYET_NCC ON 1 = 1 " + 
				"LEFT JOIN " + 
				"( " + 
				"	SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM " + 
				"	FROM ERP_MUAHANG_SP MH_SP  " + 
				"	INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK " + 
				"	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 " + 
				"	WHERE MH.PK_SEQ = '" + dmhIdCurrent + "'  AND MH_SP.SANPHAM_FK NOT IN  " + 
				"	(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) " +  
				")KTRA_SP ON 1 = 1 " + 
				"WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 " + 

				"UNION ALL " + 
				"SELECT	CP.CHUCDANH_FK, CP.QUYETDINH " +  
				"FROM ERP_MUAHANG MUAHANG   " + 
				"INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +   
				"INNER JOIN ERP_CHINHSACHDUYET_CHIPHI CP ON CP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   " + 
				"INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = CP.KHOANGCHIPHI_FK  " +  
				"LEFT JOIN( " + 
				"	SELECT	COUNT(SP.CHUCDANH_FK) AS NUM " + 
				"	FROM ERP_MUAHANG MUAHANG   " + 
				"	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +  
				"	INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   " + 
				"	AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + dmhIdCurrent + "') " +  
				"	LEFT JOIN " + 
				"	( " + 
				"		SELECT	COUNT(*) AS NUM " + 
				"		FROM ERP_MUAHANG MUAHANG   " + 
				"		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +  
				"		INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK " +  
				"		AND NCC.NCC_FK IN (SELECT NCC_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + dmhIdCurrent + "')  " + 
				"		WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "'   " + 
				"	)DUYET_NCC ON 1 = 1 " + 
				"	LEFT JOIN " + 
				"	( " + 
				"		SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM " + 
				"		FROM ERP_MUAHANG_SP MH_SP  " + 
				"		INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK " + 
				"		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 " + 
				"		WHERE MH.PK_SEQ = '" + dmhIdCurrent + "'  AND MH_SP.SANPHAM_FK NOT IN  " + 
				"		(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) " +  
				"	)KTRA_SP ON 1 = 1 " + 
				"	WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 " + 

				")DUYET_SP ON 1 = 1 " + 
				"LEFT JOIN( " + 
				"	SELECT	COUNT(NCC.CHUCDANH_FK) AS NUM " +  
				"	FROM ERP_MUAHANG MUAHANG   " + 
				"	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +  
				"	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK " +  
				"	WHERE MUAHANG.PK_SEQ = '" + dmhIdCurrent + "'" + 
				")DUYET_NCC ON 1 = 1 " + 
				"WHERE KHOANGCHIPHI.SOTIENTU < MUAHANG.TONGTIENBVAT*MUAHANG.TYGIAQUYDOI " +   
				"AND (KHOANGCHIPHI.SOTIENDEN >= MUAHANG.TONGTIENBVAT*MUAHANG.TYGIAQUYDOI OR KHOANGCHIPHI.SOTIENDEN IS NULL) " +    
				"AND MUAHANG.PK_SEQ = '" + dmhIdCurrent + "' AND DUYET_SP.NUM = 0 AND DUYET_NCC.NUM = 0 "; 

			System.out.println("Câu duyệt PO "+ query);		
			
					ResultSet rsDuyet = db.get(query);					
					if (rsDuyet.next() )
					{							
							query = "if not exists (select * from ERP_DUYETMUAHANG where CHUCDANH_FK = "+rsDuyet.getString("CHUCDANH_FK")+" AND MUAHANG_FK = "+dmhIdCurrent+")" +
									"INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH) " +
									"VALUES('"+ dmhIdCurrent +"', '" + rsDuyet.getString("CHUCDANH_FK") + "', '0','" + rsDuyet.getString("QUYETDINH")+ "') ";
							
							System.out.println("4. Insert Duyet PO:" + query);
							if (!db.update(query))
							{
								msg = "Khong the them nguoi duyet cho PO: " + query;
								db.getConnection().rollback();
								return msg;
							}					
			
		            }
					else  
		            {
						// Nếu không có cơ chế duyệt >> Cập nhật sang trạng thái đã duyệt // TẠM THỜI CHO TỰ ĐỘNG DUYỆT TRƯỚC						
						
							query = "Update ERP_MUAHANG set trangthai = 1 where pk_seq = '" + dmhIdCurrent + "' ";
							if ( !db.update(query) )
							{
								msg = "Khong the cap nhat ERP_MUAHANG: " + query;
								db.getConnection().rollback();
								return msg;
							}
						
						rsDuyet.close();
		            }
						
					
					query = "Update ERP_MUAHANG set VUOTNGANSACH = '" + (vuotNganSach == true ? "1" : "0") + "' where pk_seq = '" + dmhIdCurrent + "' ";
					if (!db.update(query))
					{
						msg = "Khong the cap nhat ERP_MUAHANG: " + query;
						db.getConnection().rollback();
						return msg;
					}
			
					
			//Cập nhật trạng thái Dự toán : Hoàn tất
					query = "Update ERP_DUTOANVATTU set TRANGTHAI = '2' where pk_seq = '" + dtId + "' ";
					if (!db.update(query))
					{
						msg = "Khong the cap nhat ERP_DUTOANVATTU: " + query;
						db.getConnection().rollback();
						return msg;
					}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			msg = "Xảy ra lỗi trong quá trình chuyển thành PO ";
			try {
				db.getConnection().rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
				
		return msg;
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	@Override
	public void setSoItems(int soItems) {
		
		this.soItems = soItems;
	}
	@Override
	public int getSoItems() {
		
		return this.soItems;
	}
}

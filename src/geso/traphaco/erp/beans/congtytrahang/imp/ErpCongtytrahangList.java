package geso.traphaco.erp.beans.congtytrahang.imp;

import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.congtytrahang.IErpCongtytrahangList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErpCongtytrahangList extends Phan_Trang implements IErpCongtytrahangList
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
	
	dbutils db;
	private Utility util;
	
	public ErpCongtytrahangList()
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
		String query = "";
		String sql = "";
		if(search.length() <= 0)
		{
			 query =  "  select f.MA AS TIENTE, a.PK_SEQ as dmhId, a.NGAYMUA, b.TEN, c.TEN as nccTen, c.MA as nccMa,  \n" + 
					  "  SOPO as SOCHUNGTU,  \n" + 
					  "  ISNULL (a.TONGTIENAVATNGUYENTE,0) as TONGTIENAVAT, ISNULL (a.TONGTIENAVATNGUYENTE ,0) as VAT,   \n" + 
					  "  ISNULL ( a.TONGTIENBVATNGUYENTE,0) as TONGTIENBVAT,  \n" + 
					  "  a.TRANGTHAI, a.DACHOT,  a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua  \n" + 
					  "  from erp_muahang a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ   \n" + 
					  "  inner join ERP_NHACUNGCAP c on a.NHACUNGCAP_FK = c.PK_SEQ  \n" + 
					  "  inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ   \n" + 
					  "  inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n" + 
					  "  left join ERP_TIENTE f on f.pk_seq =a.TIENTE_FK \n" + 
					  "  where a.congty_fk ="+ this.congtyId +"  and a.type = '2' \n " ;
			
			 if(tungay.length() > 0)
				query += " and a.ngaymua >= '" + tungay + "'";
			
			if(denngay.length() > 0)
				query += " and a.ngaymua <= '" + denngay + "'";
			
			if(dvthId.length() > 0)
				query += " and a.DONVITHUCHIEN_FK = '" + dvthId + "'";
			
			if(this.loaihanghoa.length() > 0)
				query += " and a.LOAIHANGHOA_FK = '" + this.loaihanghoa + "'";
			
			if(this.nccTen.length() > 0)
				query += " and (dbo.ftBoDau(c.ma) like '%" + util.replaceAEIOU(this.nccTen) + "%' or dbo.ftBoDau(c.ten) like N'%" + util.replaceAEIOU(this.nccTen) + "%') ";
			
			if(this.nguotaoIds.trim().length() > 0)
				query += " and a.nguoitao = '" + this.nguotaoIds.trim() + "' ";
			
			if(this.loaihanghoa.trim().length()> 0){
				query += " and A.LOAIHANGHOA_FK = '" + this.loaihanghoa + "'";
			}
				
			if(sodonmuahang.length() > 0)
			{
				query += " and a.soPO like '%" + sodonmuahang + "%' ";
			}
			
			if(trangthai.trim().length() > 0)
			{
					if(trangthai.trim().equals("-1")){
						
					}
					else
						if(trangthai.trim().equals("0")){
							
						}
						else
							if(trangthai.trim().equals("1")){
								
							}
							else
								if(trangthai.trim().equals("2")){
									
								}
								else
									if(trangthai.trim().equals("3")){
										
									}
									else
										if(trangthai.trim().equals("4")){
											
										}
										else
										{
											
										}
					
			}
		
			System.out.println("Vuna_searchQuery: " + query);
			if(this.isdontrahang.equals("0"))
			{
				query += " and a.LOAI = "+ this.loai + " ";
			}						
		
		}
		else
			query = search;

		
		
		
		// Phân quyền nhân viên theo phòng ban, mặc định admin được nhìn thấy
		if( !this.userId.equals("100002")  && util.GetquyenNew("ErpCongtytrahangSvl", "",userId)[9]==0){
			query += " and a.DONVITHUCHIEN_FK in ( select  " +
					" distinct PHONGBAN_FK from NHANVIEN where PK_SEQ ="+this.userId+" )";
		}
		
		if( !this.userId.equals("100002") && checkTruongPhong() == false  && util.GetquyenNew("ErpCongtytrahangSvl", "",userId)[9]==0){
			query += " and a.NGUOITAO = "+ this.userId;
		}
		System.out.println("Query init 111 : " + query);
		String query_init = createSplittingData_ListNew(this.db, soItems, 10, "dmhId desc, NGAYMUA desc", query);
		this.dmhRs = db.get(query_init);
		
		
		query = "select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = 1 ";
		this.dvthRs = db.get(query);
		this.loaisanphamRs=db.get("select pk_seq, ten from ERP_LOAISANPHAM where trangthai = '1' ");
		
		
		
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_MUAHANG ) ";
		this.nguoitaoRs = db.get(query);
		
		// tạo ra resultset NCC
		this.nccRs = db.get(" select PK_SEQ, MA +'-'+ TEN as TEN from ERP_NHACUNGCAP " +
				            " where PK_SEQ in ( select distinct NHACUNGCAP_FK from " +
				            " ERP_DUYETNHACUNGCAP where TRANGTHAI = 1)");
	}

	
	
	
	// kiem tra co phai truong phong hay khong
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
}
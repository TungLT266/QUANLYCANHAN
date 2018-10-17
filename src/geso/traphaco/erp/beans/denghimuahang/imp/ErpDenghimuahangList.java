package geso.traphaco.erp.beans.denghimuahang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.denghimuahang.IErpDenghimuahangList;

public class ErpDenghimuahangList extends Phan_Trang implements IErpDenghimuahangList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	
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
	
	private int num;
	private int soItems;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	private Utility util;
	
	public ErpDenghimuahangList()
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
		if(search.length() <= 0)
		{
			query = " select a.PK_SEQ as dmhId,ISNULL(( select SUM(soluong) from ERP_denghiMUAHANG_SP where denghi_FK = a.PK_SEQ ), 0) AS tongluong, a.NGAYdenghi , a.sodenghi,\n " +
					" isnull((select TEN from ERP_DONVITHUCHIEN where PK_SEQ = a.DONVITHUCHIEN_FK), '') as ten, \n " +
					" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, \n " +
					" ISNULL(tt.ma, 'NA') as tiente, a.dachot, a.ISTRUONGBPDUYET, a.tooltip \n " +
					" from erp_denghimuahang a  \n " +
					" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n " +
					" left join ERP_TIENTE tt on a.tiente_fk = tt.pk_seq  \n " +
 					" where  a.congty_fk = '" + this.congtyId + "' \n ";
					 					
			
			if(this.task.length() > 0)
					query += " and a.trangthai = 1  \n ";
		}
		else
			query = search;
		
		
		if(this.tungay.length() > 0)
			query += " and a.ngaydenghi >= '" + this.tungay + "'";
		
		if(this.denngay.length() > 0)
			query += " and a.ngaydenghi <= '" + this.denngay + "'";
		
		if(this.dvthId.length() > 0)
			query += " and a.DONVITHUCHIEN_FK = '" + this.dvthId + "'";
		
		if(this.loaihanghoa.length() > 0)
			query += " and a.LOAIHANGHOA_FK = '" + this.loaihanghoa + "'";
		
		if(this.loaisanphamid.length() > 0)
			query += " and a.loaisanpham_fk = '" + this.loaisanphamid + "'";
		
		if(this.nguotaoIds.trim().length() > 0)
			query += " and a.nguoitao = '" + this.nguotaoIds.trim() + "' ";
			
		if(sodonmuahang.length() > 0)
		{
			query += " and a.sodenghi like '%" + sodonmuahang + "%' ";
			
		}
		
		String[] t;
		if(trangthai.trim().length() > 0){
			
			t = trangthai.split(";");
			
			query += " and a.trangthai = 0 ";
			
			if((t[1].trim()).length() > 0){
				query += "and a.dachot = '" + t[1] + "'";
			}
			
			if((t[0].trim()).length() > 0){
				query += "and a.ISTRUONGBPDUYET = '" + t[0] + "'";
			}

		}
	
	
		
		
		// Phân quyền nhân viên theo phòng ban, mặc định admin được nhìn thấy
		if( !this.userId.equals("100002")  && util.GetquyenNew("ErpDenghimuahangSvl", "",userId)[9]==0){
			query += " and a.DONVITHUCHIEN_FK in ( select  " +
					" distinct PHONGBAN_FK from NHANVIEN where PK_SEQ ="+this.userId+" )";
		}
	
		if( !this.userId.equals("100002") && checkTruongPhong() == false && util.GetquyenNew("ErpDenghimuahangSvl", "",userId)[9]==0){
			query += " and a.NGUOITAO = "+ this.userId;
		}
		
		
		System.out.println("Query init 111 232 : " + query);
		
		String query_init = createSplittingData_ListNew(this.db, soItems, 10, "NGAYdenghi desc, dmhId desc", query);
		System.out.println(query_init);
		
		this.dmhRs = db.get(query_init);
		
		//this.dmhRs = createSplittingData(50, 10, "dmhId desc", query);
		query = "select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1' ";// pk_seq in " + util.quyen_donvithuchien(this.userId);
		this.dvthRs = db.get(query);
		
		System.out.println("select pk_seq, ten from ERP_LOAISANPHAM where trangthai = '1' and congty_fk = '" + this.congtyId + "' ");
		this.loaisanphamRs=db.get("select pk_seq, ten from ERP_LOAISANPHAM where trangthai = '1'");
		
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_denghiMUAHANG ) ";
		this.nguoitaoRs = db.get(query);
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
		ResultSet rs = db.get("select count(*) as c from ERP_denghiMUAHANG");
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
	@Override
	public void setSoItems(int soItems) {
		
		this.soItems = soItems;
	}
	@Override
	public int getSoItems() {
		
		return this.soItems;
	}
}

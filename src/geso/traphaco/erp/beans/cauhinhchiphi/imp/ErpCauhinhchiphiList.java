package geso.traphaco.erp.beans.cauhinhchiphi.imp;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.cauhinhchiphi.IErpCauhinhchiphiList;

public class ErpCauhinhchiphiList extends Phan_Trang implements IErpCauhinhchiphiList
{
	private static final long serialVersionUID = -9217977556733610214L;

	// Tieu chi tim kiem
	private String userId;
	private String diengiai;
	private String trangthai;		 
	private String ctyId;
	private ResultSet TTDTlist;
	private String msg;
	private boolean search = false;
	private dbutils db;
	private Utility util;

	private String group;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	String chixem;

	public ErpCauhinhchiphiList()
	{
		this.diengiai = "";	
		this.trangthai = "";
		this.group = "";
		this.ctyId = "";
		this.chixem = "0";
		
		currentPages = 1;
		num = 1;
		
		this.db = new dbutils();
		this.util=new Utility();
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}
	
	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}
	
	
	public ResultSet getTTDTList(){
		return this.TTDTlist;
	}
	
	public void setTTDTList(ResultSet TTDTlist){
		this.TTDTlist = TTDTlist;
	}
	

	public boolean getSearch()
	{
		return this.search;
	}

	public void setSearch(boolean search)
	{
		this.search = search;
	}

	
	public void init()
	{
		String query = 	"select a.pk_seq, a.ma, a.diengiai, a.trangthai, a.ngaysua, a.ngaytao, c.TEN as nguoitao, d.TEN as nguoisua " +
						"from GROUP_TAIKHOAN_NHOM a  " +
							"inner join NHANVIEN c on a.nguoitao = c.PK_SEQ " +
							"inner join NHANVIEN d on a.nguoisua = d.PK_SEQ ";

		System.out.println("Init data: " + query);
		
		this.TTDTlist = createSplittingDataNew(this.db, 50, 10," pk_seq desc ", query) ;
		

	}
	
	
	public String getData(String userId){
		String html = "";
		int n = 0;
		return (init0(html, "" , n, this.db, userId));
	}
	
	private String init0(String html, String id, int m, dbutils db, String userId){
		
		String query = ""; 	
		query = getSearchQuery(id);
			
		System.out.println(query);
		
		ResultSet rs = this.db.get(query);
						
		String lightrow = "tblightrow";
		String darkrow = "tbdarkrow";
		try{
			if(rs != null){
				while (rs.next()){
					if (m % 2 != 0) {						
						html = html + "<TR class= '" + lightrow + "' >" ;
					}else{ 
						html = html + "<TR class= '" + darkrow + "' > " ;
					} 							
					
					if(!rs.getString("BOSSID").equals("0")){
			
						html = html + "<TD align=center >" + rs.getString("BOSSID") + "  ";
					
					}else{
						html = html + "<TD align=center >&nbsp; ";
						
					}
					
					html = html + "<TD align=left >" + rs.getString("TTDTID") + " - " + rs.getString("TTDTTEN") + " - " + rs.getString("DIENGIAI");
					
					if(rs.getString("LOAI").equals("1")){ 
						html = html + "<TD align=center ><div align= center> Nhóm trung tâm chi phí </div></TD> " ;
					}else {
						html = html + "<TD align=center><div align=center >Trung tâm chi phí</div></TD>";
					} 
				
					if(rs.getString("TRANGTHAI").equals("1")) {
						html = html + "<TD align=center >Hoạt động</TD>" ;
					}else {
						html = html + "<TD align=center>Ngưng hoạt động</TD>";
					} 
					
					html = html + "<TD align=center>" +  rs.getString("DVTHTEN") + "</TD>";
					html = html + "<TD align=center>" ;
					html = html + "<TABLE>" ;
					html = html + "<TR><TD>";
					html = html + "<A href = ../../Erp_TrungTamDoanhThuUpdateSvl?userId=" + userId + "&update=" +rs.getString("TTDTID") + "><img src='../images/Edit20.png' alt='Cap nhat' width='20' height='20' longdesc='Cap nhat' border = 0></A>";
					html = html + "</TD>" ;
					html = html + "<TD>&nbsp;</TD>" ;
					html = html + "<TD>" ;
					html = html + "<A href = ../../Erp_TrungTamDoanhThuSvl?userId=" +  userId + "&delete=" + rs.getString("TTDTID") + "><img src='../images/Delete20.png' alt='Xoa' width='20' height='20' longdesc='Xoa' border=0 onclick='if(!confirm('Bạn có muốn xóa trung tâm chi phí " + rs.getString("TTDTTEN") + "này không?')) return false;></A></TD>" ;
					html = html + "</TR>" ;												
					html = html + "</TABLE>" ;									
					html = html + "</TD>" ;
					html = html + "</TR>" ;
					m++;
					html = init0(html, rs.getString("TTDTID"), m, db, userId);
				}
			}
		}catch(java.sql.SQLException e){}
		return html;
	}
	

	public void getTTDTBeanList(HttpServletRequest request) {
		String query = this.getSearchQuery("");
		this.TTDTlist =  this.db.get(query);
		
	}

	public void getReqParam(HttpServletRequest request) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Utility util = new Utility();
		
		this.diengiai = util.antiSQLInspection(request.getParameter("diengiai"));	 	
		this.trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 
		this.group = util.antiSQLInspection(request.getParameter("Group")); 		
		this.getSearchQuery("");

	}
	
	
	
	private String getSearchQuery(String id)
	{

		/**
		 * sửa lỗi không tìm kiếm được
		 */
		String query = 	
			" select a.pk_seq, a.ma, a.diengiai, a.trangthai, a.ngaysua, a.ngaytao, c.TEN as nguoitao, d.TEN as nguoisua " +
			" from GROUP_TAIKHOAN_NHOM a  " +
			"      inner join NHANVIEN c on a.nguoitao = c.PK_SEQ " +
			"      inner join NHANVIEN d on a.nguoisua = d.PK_SEQ "+
	        " where 1 =1  ";// +
	

		if(this.trangthai.length()>0)
		{
			query+= "AND a.trangthai = "+ this.trangthai +" ";
		}
		if (this.diengiai.length()>0)
		{
			query = query + " AND (UPPER(a.ma) LIKE UPPER(N'%" + this.diengiai + "%') OR UPPER(a.diengiai) LIKE UPPER(N'%" + this.diengiai + "%')) ";
		}
		
		
	
		System.out.println("Search: " + query);
		this.TTDTlist = createSplittingData(50, 10, " pk_seq desc ", query);
		
		return query;
	}		
	
	public void DBClose(){
		try{
			if(this.TTDTlist != null) this.TTDTlist.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}

	public void setMsg(String msg) {
		this.msg =msg;
		
	}

	
	public String getMsg() {
		return this.msg;
	}

	@Override
	public ResultSet getNhanphanbo(String TTDTId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getChophanbo(String TTDTId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	public int getCurrentPage() 
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages() 
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages)
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as c from GROUP_TAIKHOAN_NHOM ");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) 
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}
	
}
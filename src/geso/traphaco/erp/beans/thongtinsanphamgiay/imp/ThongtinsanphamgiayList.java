package geso.traphaco.erp.beans.thongtinsanphamgiay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinsanphamgiayList;
import geso.dms.center.util.Phan_Trang;

public class ThongtinsanphamgiayList extends Phan_Trang implements IThongtinsanphamgiayList
{
	private static final long serialVersionUID = -9217977556733610214L;
	
	String ctyId;
	private String userId;
	private String masp;
	private String tensp;
	private String tennoibo;
	private String dvkdId;
	private String nhId;		 
	private String clId;
	private String lspId = "";
	private String trangthai;
	private String Msg;
	private String query;
	private ResultSet dvkd;
	private ResultSet nh;
	private ResultSet cl;
	private ResultSet splist;
	private ResultSet lspRs;
	
	private dbutils db;
	
	int currentPages;
	int[] listPages;
	
	//newtoyo cu
	private String ctyTen;
	private String loaispId;
	private ResultSet loaisanpham;
	
	private String MAsp;
	
	private String maketoan;
	private String nguoitao;
	private String nguoisua;

	private String machitietsp;
	
	public String getMaketoan() {
		return maketoan;
	}

	public void setMaketoan(String maketoan) {
		this.maketoan = maketoan;
	}

	public String getNguoitao() {
		return nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}

	public String getNguoisua() {
		return nguoisua;
	}

	public void setNguoisua(String nguoisua) {
		this.nguoisua = nguoisua;
	}

	public ThongtinsanphamgiayList(String[] param)
	{
		this.tensp = param[0];
		this.dvkdId = param[1];
		this.nhId = param[2];		 
		this.clId = param[3];
		this.trangthai = param[4];
		this.masp = param[5];
		this.tennoibo = param[6];
		this.query = "";
		this.ctyId = "";
		this.machitietsp = "";
		
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		
		this.db = new dbutils();
		
	}
	
	public ThongtinsanphamgiayList()
	{
		this.loaispId="";
		this.masp = "";
		this.tensp = "";
		this.tennoibo = "";
		this.dvkdId = "";
		this.nhId = "";		 
		this.clId = "";
		this.trangthai = "";
		this.query = "";
		this.Msg ="";
		this.MAsp = "";
		
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		
		this.db = new dbutils();
		
		this.maketoan = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.machitietsp = "";
	}

	public String getCtyTen()
	{
		return this.ctyTen;
	}
	
	public void setCtyTen(String ctyTen)
	{
		this.ctyTen = ctyTen;
	}

	/*public ResultSet getThongtinsanphamList(){
		return this.splist;
	}
	
	public void setThongtinsanphamList(ResultSet splist){
		this.splist = splist;
	}

	private ResultSet createDvkdRS(){
		
		ResultSet dvkdRS =  this.db.get("select pk_seq, donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1' and congty_fk = " + this.ctyId );
		
		return dvkdRS;
	}
	
	private ResultSet createNhRS(){
		
		ResultSet nhRS;
		if (dvkdId.length()>0){
			nhRS =  this.db.get("select pk_seq, ten from nhanhang where trangthai='1' and dvkd_fk='" + this.dvkdId + "'");
		}else{
			nhRS =  this.db.get("select pk_seq, ten from nhanhang where trangthai='1' and congty_fk = " + this.ctyId );
		}
		return nhRS;
		
	}

	private ResultSet createClRS() {  	
		
		String query = "select distinct a.pk_seq, a.ten from chungloai a, nhanhang_chungloai b where a.trangthai='1' and a.pk_seq = b.cl_fk and a.congty_fk = " + this.ctyId ;
		
		if (this.nhId.length()>0){
			query = query + "  and b.nh_fk = '" + this.nhId + "'";
		}
		 
		return this.db.get(query);
			
	}
			
	
	public void CreateRS(){
		
		this.dvkd = createDvkdRS();
		this.nh = createNhRS();
		this.cl= createClRS();
		this.loaisanpham=db.get("select pk_seq,ten from erp_loaisanpham where trangthai = '1' ");
	}

	public void init()
	{	
		if (this.query.length() == 0)
		{
		 
			
			query = " select  ROW_NUMBER() OVER(ORDER BY sanpham.ma DESC) AS 'stt', sanpham.*  " + 
					" from  " + 
					" (  " + 
					" 	 select distinct  a.ma, a.ten, max(a.trangthai) as trangthai , b.ten as lspTen  " + 
					" 		,(select top(1) NGAYSUA from ERP_SANPHAM where ma = a.MA order by NGAYSUA desc) as ngaysua " + 
					" 		,(select top(1) NGAYTAO from ERP_SANPHAM where ma = a.MA order by NGAYTAO asc) as ngaytao " + 
					" 		,(select top(1) d.TEN from ERP_SANPHAM sp inner join NHANVIEN d on sp.NGUOISUA = d.PK_SEQ where ma = a.MA order by sp.NGAYSUA desc) as nguoisua " + 
					" 		,(select top(1) d.TEN from ERP_SANPHAM sp inner join NHANVIEN d on sp.NGUOITAO = d.PK_SEQ where ma = a.MA order by sp.NGUOITAO asc) as nguoitao " +
					" 	 from ERP_SANPHAM a left join ERP_LOAISANPHAM b on a.loaisanpham_fk = b.pk_seq " +
					" 	 where a.congty_fk = '" + this.ctyId + "' " +
				    "    group by a.ma, a.ten,  b.ten " + 
					" ) as sanpham ";
			System.out.println("Init SP: " + query);
		}	
	    
		this.splist = super.createSplittingData(super.getItems(), super.getSplittings(), "ma desc", query);
	    
	    CreateRS();
	}
	
	public void DBClose()
	{
		try
		{				
			if(this.cl != null) this.cl.close();
			if(this.nh != null) this.nh.close();
			if(this.splist != null) this.splist.close();
			if(this.dvkd != null) this.dvkd.close();
			this.db.shutDown();
		}
		catch(java.sql.SQLException e){
			this.db.shutDown();
		}
	}
	
	public void setMsg(String Msg) 
	{
		this.Msg = Msg;	
	}

	public String getMsg() 
	{
		return this.Msg;
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
		ResultSet rs = db.get("select count(*) as ssp from erp_sanpham");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("ssp"));
			rs.close();
			return count;
		}
		catch (SQLException e) {}
		
		return 0;
	}*/

	@Override
	public ResultSet getLoaisanpham() {
		// TODO Auto-generated method stub
		return this.loaisanpham;
	}

	@Override
	public void setLoaisanpham(ResultSet rsLoaisanpham) {
		// TODO Auto-generated method stub
		this.loaisanpham=rsLoaisanpham;
	}

	@Override
	public String getloaisanphamid() {
		// TODO Auto-generated method stub
		return this.loaispId;
	}
	
	@Override
	public void setloaisanphamid(String loaisanphamid) {
		// TODO Auto-generated method stub
		this.loaispId=loaisanphamid;
	}

	public void setMAsp(String MAsp) {
		this.MAsp = MAsp;
	}

	public String getMAsp() {
		return MAsp;
	}
	
	
	//newtoyo cu ---
	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getMasp(){
		return this.masp;
	}
	
	public void setMasp(String masp){
		this.masp = masp;
	}

	public String getTensp(){
		return this.tensp;
	}
	
	public void setTensp(String tensp){
		this.tensp = tensp;
	}
	
	public String getTennoibo(){
		return this.tennoibo;
	}
	
	public void setTennoibo(String tennoibo){
		this.tennoibo = tennoibo;
	}
	
	public String getQuery(){
		return this.query;
	}
	
	public void setQuery(String query){
		this.query = query;
	}

	public String getDvkdId(){
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId){
		this.dvkdId = dvkdId;
	}

	public String getNhId(){
		return this.nhId;
	}
	
	public void setNhId(String nhId){
		this.nhId = nhId;
	}

	public String getClId(){
		return this.clId;
	}
	
	public void setClId(String clId){
		this.clId = clId;
	}
			
	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}				

	public ResultSet getDvkd(){
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd){
		this.dvkd = dvkd;
	}
	
	public ResultSet getNh(){
		return this.nh;
	}
	
	public void setNh(ResultSet nh){
		this.nh = nh;
	}

	public ResultSet getCl(){
		return this.cl;
	}
	
	public void setCl(ResultSet cl){
		this.cl = cl;
	}
	
	public ResultSet getThongtinsanphamList(){
		return this.splist;
	}
	
	public void setThongtinsanphamList(ResultSet splist){
		this.splist = splist;
	}

	private ResultSet createDvkdRS(){
		
		ResultSet dvkdRS =  this.db.get("select pk_seq, donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1' and congty_fk = " + this.ctyId + " ");
		//ResultSet dvkdRS =  this.db.get("select a.pk_seq, a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId  from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai = '1' order by a.donvikinhdoanh");
		
		return dvkdRS;
	}
	
	private ResultSet createNhRS(){
		
		ResultSet nhRS;
		if (dvkdId.length()>0){
			nhRS =  this.db.get("select pk_seq, ten from nhanhang where trangthai='1' and dvkd_fk='" + this.dvkdId + "' and congty_fk = " + this.ctyId + "");
		}else{
			nhRS =  this.db.get("select pk_seq, ten from nhanhang where trangthai='1' and congty_fk = " + this.ctyId + "");
		}
		return nhRS;
		
	}

	private ResultSet createClRS() {  	
		
		String query = "select distinct a.pk_seq, a.ten from chungloai a, nhanhang_chungloai b where a.trangthai='1' and a.pk_seq = b.cl_fk and a.congty_fk = " + this.ctyId + " ";
		
		if (this.nhId.length()>0){
			query = query + "  and b.nh_fk = '" + this.nhId + "'";
		}
		 
		return this.db.get(query);
			
	}

	private ResultSet createLspRS() {  	
		
		String query = "select distinct a.pk_seq, a.ma + ', ' + a.ten as ten from erp_loaisanpham a where a.trangthai='1' and a.congty_fk = " + this.ctyId + " ";
		System.out.println("createLspRS: " + query);
		return this.db.get(query);
			
	}
			
	
	public void CreateRS(){
		
		this.dvkd = createDvkdRS();
		this.nh = createNhRS();
		this.cl= createClRS();
		this.lspRs= createLspRS();

	}

	public void init()
	{	
		
		if(this.query.length()==0){
	        query = " select ROW_NUMBER() OVER(ORDER BY a.ma DESC) AS 'stt', a.pk_seq, a.ma, a.ten1 as tennoibo, " +
	        		" \n a.ngaytao as ngaytao, a.ngaysua as ngaysua, isnull(nt.ten,'') as nguoitao, isnull(ns.ten,'')  as nguoisua , "+
	        		"\n a.ten, lsp.ten as lspten, a.trongluong, a.thetich, b.donvikinhdoanh as dvkd,b.pk_seq as dvkdId, " + 
	        		"\n isnull(c.ten,'') as chungloai, e.pk_seq as nhId, d.donvi, isnull(e.ten,'') as nhanhang, " + 
	        		"\n d.pk_seq as clId, a.trangthai, isnull(a.kiemtradinhtinh, 0) as kiemtradinhtinh, " +
	        		"\n isnull(a.kiemtradinhluong, 0) as kiemtradinhluong " +
					"\n from erp_sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq " +
					"\n left join erp_loaisanpham lsp on lsp.pk_seq = a.loaisanpham_fk " +
					"\n left join chungloai c on a.chungloai_fk = c.pk_seq " +
					"\n left join donvidoluong d on a.dvdl_fk = d.pk_seq " +
					"\n left join nhanhang e on a.nhanhang_fk = e.pk_seq " +
					"\n LEFT JOIN NHANVIEN nt \n"+
					"\n on nt.PK_SEQ=a.NGUOITAO " + " LEFT JOIN NHANVIEN ns " + " on ns.PK_SEQ=a.NGUOISUA  "+
					"\n where a.congty_fk = " + this.ctyId + " ";
		}
		
		System.out.println("[ThongtinsanphamList.init] query = " + query);
	    
		this.splist =super.createSplittingDataNew(this.db, super.getItems(), super.getSplittings(), "pk_seq desc", query);
	    
	    CreateRS();
	}
	
	public void DBClose()
	{
		try
		{				
			if(this.cl != null) this.cl.close();
			if(this.nh != null) this.nh.close();
			if(this.lspRs != null) this.lspRs.close();
			if(this.splist != null) this.splist.close();
			if(this.dvkd != null) this.dvkd.close();
			if (this.loaisanpham != null) this.loaisanpham.close();
		
		}
		catch(java.sql.SQLException e){
			
			e.printStackTrace();
		}
		finally
		{
			if(this.db!=null )
				this.db.shutDown();
			this.db = null;
		}
	}
	public void NewDbUtil()
	{
		  System.out.println(" vo dey thoi");
		  if(this.db == null )
		  {
			   System.out.println("new dbbbbbbb roi");
			   this.db  = new dbutils();
		  }
	}
	
	public void setMsg(String Msg) 
	{
		this.Msg = Msg;	
	}

	public String getMsg() 
	{
		return this.Msg;
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
		ResultSet rs = db.get("select count(*) as skh from khachhang");
		if (rs != null)
		{
			try 
			{
				rs.next();
				int count = Integer.parseInt(rs.getString("skh"));
				rs.close();
				return count;
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return 0;
	}

	@Override
	public String getLspId() {
		return this.lspId;
	}

	@Override
	public void setLspId(String lspId) {
		this.lspId = lspId;
	}

	@Override
	public ResultSet getLspRs() {
		return this.lspRs;
	}

	@Override
	public void setLspRs(ResultSet lspRs) {
		this.lspRs = lspRs;
	}

	public void setMachitietsp(String machitietsp) {
		this.machitietsp = machitietsp;
	}

	public String getMachitietsp() {
		return machitietsp;
	}
	

}

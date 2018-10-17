package geso.traphaco.distributor.beans.quanlykhuyenmai.imp;
import geso.traphaco.distributor.beans.quanlykhuyenmai.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class Chuongtrinhkhuyenmai extends Phan_Trang implements IChuongtrinhkhuyenmai, Serializable
{
		String userId;
		String nppId;
		String nppTen;
		String sitecode;

		String msg;
		String diengiai;
		String trangthai;
		String tungay;
		String denngay;
		String loaict;
		ResultSet schemeRS;
		Hashtable<String, String> usedPro;
		Hashtable<String, String> budget;
		dbutils db ;
		private int num;
		private int[] listPages;
		private int currentPages;
		private int items;
		private HttpServletRequest request;
		
		public Chuongtrinhkhuyenmai()
		{
			this.nppId = "0";
			this.msg = "";
			this.tungay = "";
			this.denngay = "";
			this.loaict = "";
			this.trangthai = "";
			this.diengiai = "";
			
			currentPages = 1;
					num = 10;
					this.items = 10;

			this.db = new dbutils();
		}
		
		public HttpServletRequest getRequestObj() 
		{
			return this.request;
		}

		public void setRequestObj(HttpServletRequest request) 
		{
			this.request = request;
		}
		
		public void setItems(int items){
			this.items = items;
		}
		public int getItems(){
			
			return this.items;
		}

		public String getUserId()
		{
			return this.userId;
		}

		public void setUserId(String userId) 
		{
			this.userId = userId;
		}
		
		public String getNppId()
		{
			return this.nppId;
		}

		public void setNppId(String nppId)
		{
			this.nppId = nppId;
		}
			
		public String getNppTen() 
		{
			return this.nppTen;
		}

		public void setNppTen(String nppTen) 
		{
			this.nppTen = nppTen;
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

		public String getDiengiai() 
		{
			return this.diengiai;
		}

		public void setDiengiai(String diengiai) 
		{
			this.diengiai = diengiai;
		}

		public String getTrangthai() 
		{
			return this.trangthai;
		}

		public void setTrangthai(String trangthai) 
		{
			this.trangthai = trangthai;
		}

		public String getSitecode() 
		{
			return this.sitecode;
		}

		public void setSitecode(String sitecode) 
		{
			this.sitecode = sitecode;
		}

		public String getMessage()
		{
			return this.msg;
		}

		public void setMessage(String msg)
		{
			this.msg = msg;
		}

		public ResultSet getSchemeRS() 
		{
			return this.schemeRS;
		}

		public void setSchemeRS(ResultSet schemeRS) 
		{
			this.schemeRS = schemeRS;
		}

		public Hashtable<String, String> getusedPro()
		{
			return this.usedPro;
		}

		public void setusedPro(Hashtable<String, String> usedPro)
		{
			this.usedPro = usedPro;
		}
	
		public Hashtable<String, String> getBudget()
		{
			return this.budget;
		}

		public void setBudget(Hashtable<String, String> budget)
		{
			this.budget = budget;
		}
		
		private String getMainSql(){
			getNppInfo();
			String query = 
			"	select  row_number() over(order by ct.pk_seq desc, denngay desc, ct.loaict desc, ct.scheme) as stt, "+ 
			"		ct.pk_seq, ct.scheme, ct.diengiai, isnull(ct.loaict,'1') as loaict, ct.tungay, ct.denngay, isnull(pbkm.ngansach,'0') as ngansach, "+
			"		case when ct.phanbotheodonhang =0 then isnull ((select sum(km.tonggiatri) as sudung from donhang_ctkm_trakm km inner join donhang dh on dh.pk_seq=km.donhangid where dh.npp_fk='"+this.nppId+"' and km.ctkmid=ct.pk_seq and dh.trangthai!=2   ),0) else "+ 
			"		isnull((select sum(km.soluong) as sudung from donhang_ctkm_trakm km inner join donhang dh on dh.pk_seq=km.donhangid where dh.npp_fk='"+this.nppId+"' and km.ctkmid=ct.pk_seq and dh.trangthai!=2 ),0) end as sudung "+
			 "	from ctkhuyenmai ct inner join phanbokhuyenmai pbkm on ct.pk_seq=pbkm.ctkm_fk "+ 
			"	where npp_fk= '"+this.nppId+"' ";

			if(this.diengiai.length() > 0){
				query = query + " and scheme like '%" + this.diengiai + "%'";
			}
			
			if(this.tungay.length() > 0){
				query = query + " and tungay >='" + this.tungay+"'";
				
			}
			
			if(this.denngay.length() > 0){
				query = query + " and denngay <='" + this.denngay+"'";
			}
			
			if(this.trangthai.equals("1")){
				query = query + " and tungay <= (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,GETDATE()),120))" +
								" and denngay >=( SELECT CONVERT(VARCHAR(10),DATEADD(day,0,GETDATE()),120))";

			}
			
			if(this.trangthai.equals("2")){
				query = query + " and tungay >= (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,GETDATE()),120))" +
								" or denngay <=( SELECT CONVERT(VARCHAR(10),DATEADD(day,0,GETDATE()),120))";
				
			}
			
			System.out.println("Search query: " + query);
			
			return query;
			
		}

		private ResultSet createSchemeRS(){
			//Khoa sua load ra nhu ban nha phan phoi
			//String sql_getsheme="select a.pk_seq, a.scheme, a.diengiai, a.loaict, a.tungay, a.denngay,a.ngansach,dasudung from ctkhuyenmai a, ctkm_npp b where a.pk_seq = b.ctkm_fk and npp_fk ='"+ this.nppId +"' order by denngay DESC, a.loaict DESC, a.scheme";
			
			//String sql_getsheme ="select ct.pk_seq, ct.scheme, ct.diengiai, isnull(ct.loaict,'1') as loaict, ct.tungay, ct.denngay, isnull(pbkm.ngansach,'0') as ngansach, isnull(pbkm.dasudung,'0') as dasudung  from ctkhuyenmai ct inner join phanbokhuyenmai pbkm on ct.pk_seq=pbkm.ctkm_fk where npp_fk= "+ this.nppId + " order by denngay DESC, ct.loaict DESC, ct.scheme" ;
			
			String query = getMainSql();
			System.out.println("ctkm: " + query);
			
			return  createSplittingData(50, 10, "pk_seq desc", query);//PhanTrang.getSplitData(query, currentPages,items, num);

		}
				
		
		private Hashtable<String, String> collectUsedPromotion(){
			Hashtable<String, String> ht = new Hashtable<String, String>();
			ht.put("0", "0");
			if(this.db.getConnection() != null){
				ResultSet rs = this.db.get("select a.ctkmid as schemeId, sum(a.tonggiatri) as amount from donhang_ctkm_trakm a, donhang b where a.donhangid=b.pk_seq and b.trangthai=1 and b.npp_fk='" + this.nppId + "' group by a.ctkmId");
				System.out.print("select a.ctkmid as schemeId, sum(a.tonggiatri) as amount from donhang_ctkm_trakm a, donhang b where a.donhangid=b.pk_seq and b.trangthai=1 and b.npp_fk='" + this.nppId + "' group by a.ctkmId");
				if(rs != null){
					try{
						while(rs.next()){
							if(rs.getString("schemeId") != null){
								ht.put(rs.getString("schemeId"), rs.getString("amount"));
							}
						}
						
					}catch(Exception e){}
					finally{try {
						if(rs != null)
							rs.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}}
				}else{
					System.out.print("Error in getUsedPromotion/rs==null");
				}
					
			}else{
				System.out.print("Error in getUsedPromotion/rs==null");
			}
				
			return ht;
			
		}
		
		private Hashtable<String, String> collectBudgets(){
			Hashtable<String, String> ht = new Hashtable<String, String>();
			ht.put("0", "0");
			if(this.db.getConnection() != null){
				ResultSet rs = this.db.get("select ctkm_fk as schemeId, ngansach from phanbokhuyenmai where npp_fk='" + this.nppId + "'");
				System.out.print("select ctkm_fk as schemeId, ngansach from phanbokhuyenmai where npp_fk='" + this.nppId + "'");
				if(rs != null){
					try{
						while(rs.next()){
							if(rs.getString("schemeId") != null){
								ht.put(rs.getString("schemeId"), rs.getString("ngansach"));
							}
						}
						
					}catch(Exception e){}
					finally{try {
						if(rs != null)
							rs.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}}
				}else{
					System.out.print("Error in getUsedPromotion/rs==null");
				}
					
			}else{
				System.out.print("Error in getUsedPromotion/rs==null");
			}
				
			return ht;
			
		}

		
		public void init(){
			getNppInfo();
			this.schemeRS = this.createSchemeRS();
			this.budget = this.collectBudgets();
			this.usedPro = this.collectUsedPromotion();
			
		}
		
		public String getDateTime() {
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        return dateFormat.format(date);	
		}
		
		private void getNppInfo()
		{		
			/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
			try{
				if (!(rs == null)){
					rs.next();
					this.nppId = rs.getString("pk_seq");
					this.nppTen = rs.getString("ten");
					this.sitecode = rs.getString("sitecode");
					
				}else
				{
					this.nppId = "";
					this.nppTen = "";
					this.sitecode = "";				
				}
				
			}catch(Exception e){}
			*/
			//Phien ban moi
			geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
			this.nppId=util.getIdNhapp(this.userId);
			this.nppTen=util.getTenNhaPP();
			//this.dangnhap = util.getDangNhap();
			this.sitecode=util.getSitecode();
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

			
			return PhanTrang.getTheLastPage(getMainSql());


		}

		
		public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {

			IPhanTrang pt = new PhanTrang();
			return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
		}

		@Override
		public void DBclose() {

			
			try {

				if(this.schemeRS != null)
					this.schemeRS.close();
				if(this.db != null)
					this.db.shutDown();
				if(this.usedPro!=null){
					this.usedPro.clear();
				}
				if(this.budget!=null){
					this.budget.clear();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}		

		
	}

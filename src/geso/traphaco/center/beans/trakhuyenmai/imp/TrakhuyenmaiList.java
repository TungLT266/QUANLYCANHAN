package geso.traphaco.center.beans.trakhuyenmai.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.beans.trakhuyenmai.ITrakhuyenmai;
import geso.traphaco.center.beans.trakhuyenmai.ITrakhuyenmaiList;
import geso.traphaco.center.beans.trakhuyenmai.imp.Trakhuyenmai;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.db.sql.dbutils;

public class TrakhuyenmaiList extends Phan_Trang implements ITrakhuyenmaiList
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String diengiai;
	String tungay;
	String denngay;
	String msg;
	String maTrakm;
	String maSp;
	String tenSp;
		
	//List<ITrakhuyenmai> trakmList;
	ResultSet trakmList;
	dbutils db;
	private int[] listPages;
	private int num;

	private int currentPages;

	private HttpServletRequest request;
	
	public TrakhuyenmaiList(String[] param)
	{
		this.diengiai = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		db = new dbutils();
		this.msg ="";
		this.tenSp="";
		this.maSp="";
		this.maTrakm="";
	}
	
	public TrakhuyenmaiList()
	{
		this.diengiai = "";
		this.denngay = "";
		this.tungay = "";
		this.msg ="";
		currentPages = 1;
		num = 1;
		db = new dbutils();
		this.tenSp="";
		this.maSp="";
		this.maTrakm="";
	}
	
	public HttpServletRequest getRequestObj() 
	{
		return this.request;
	}

	public void setRequestObj(HttpServletRequest request) 
	{
		this.request = request;
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getDiengiai() 
	{		
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
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
	public ResultSet getTrakmList() 
	{		
		return this.trakmList;
	}
	public void setTrakmList(ResultSet trakmList) 
	{
		this.trakmList = trakmList;	
	}
/*	public List<ITrakhuyenmai> getTrakmList() 
	{		
		return this.trakmList;
	}
	*/
/*	public void setTrakmList(List<ITrakhuyenmai> trakmList) 
	{
		this.trakmList = trakmList;	
	}
	*/
	public void init(String search) 
	{
		db = new dbutils();
		
		String query = "";	
		if (search.length() == 0)
		{
			query = "select a.PK_SEQ as trakmId, a.diengiai, isnull(a.TONGLUONG, 0) as tongluong, isnull(a.TONGTIEN, 0) as tongtien, isnull(a.CHIETKHAU, 0) as chietkhau, a.loai, a.hinhthuc, a.ngaytao, a.ngaysua, b.TEN as nguoitao, c.TEN as nguoisua ";
			query = query + " from trakhuyenmai a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ ";
			//query = query + " order by a.ngaytao DESC, a.pk_seq DESC";
		}
		else
		{
			query = search;
		}				
		this.createtrakmBeanList(query);	
	}
	
	private void createtrakmBeanList(String query) 
	{
		this.trakmList = createSplittingDataNew(this.db, 50, 10, "trakmId desc", query);//  createSplittingData(request, "trakmId desc", query);//db.get(query);
		
		/*if(rs != null)
		{
			String[] param = new String[11];
			ITrakhuyenmai trakmBean = null;
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("trakmId");
					param[1]= rs.getString("diengiai");
					param[2]= rs.getString("tongtien");
					param[3] = rs.getString("tongluong");
					param[4]= rs.getString("chietkhau");
					param[5]= rs.getString("loai");
					param[6]= rs.getString("hinhthuc");
					param[7]= rs.getString("ngaytao");
					param[8]= rs.getString("nguoitao");
					param[9]= rs.getString("ngaysua");
					param[10]= rs.getString("nguoisua");
					
					trakmBean = new Trakhuyenmai(param);
					trakmList.add(trakmBean);
				}
				rs.close();
			}
			catch (SQLException e) {}		
		}
		this.trakmList = trakmList;	
		*/
	}
	
	public void DBclose() 
	{
				
	}

	
	public void setmsg(String msg) {
		this.msg = msg;
		
	}


	public String getmsg() {
	
		return this.msg;
	}

	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = new int[num];
		for(int i = 0; i < this.num; i++)
			listPages[i]=i+1;
	}

	@Override
	public String getMatrakm() {
		return this.maTrakm;
	}

	@Override
	public void setMatrakm(String matrakm) {
		this.maTrakm=matrakm;
		
	}

	@Override
	public String getTensp() {
		return this.tenSp;
	}

	@Override
	public void setTensp(String tensp) {
		this.tenSp=tensp;
	}

	@Override
	public String getMasp() {
		return maSp;
	}

	@Override
	public void setMasp(String masp) {
		this.maSp=masp;
		
	}

	
	
}

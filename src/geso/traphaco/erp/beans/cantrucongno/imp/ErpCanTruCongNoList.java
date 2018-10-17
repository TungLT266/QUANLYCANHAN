package geso.traphaco.erp.beans.cantrucongno.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.erp.beans.cantrucongno.IErpCanTruCongNoList;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ErpCanTruCongNoList extends Phan_Trang  implements IErpCanTruCongNoList {
	String nccId = "";
	String kh = "";
	String khId = "";
	String userId = "";

	String ncc = "";

	String trangthai = "";
	String tongtien = "";
	String ngaytao = "";
	String id = "";

	String congtyId = "";
	String nppdangnhap = "";
	String msg = "";
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db = new dbutils();
	Utility util;
	
	List<IThongTinHienThi> hienthiList = new ArrayList<IThongTinHienThi>();

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNcc() {
		return ncc;
	}

	public void setNcc(String ncc) {
		this.ncc = ncc;
	}

	public String getKh() {
		return kh;
	}

	public void setKh(String kh) {
		this.kh = kh;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getTongtien() {
		return tongtien;
	}

	public void setTongtien(String tongtien) {
		this.tongtien = tongtien;
	}

	public String getNgaytao() {
		return ngaytao;
	}

	public void setNgaytao(String ngaytao) {
		this.ngaytao = ngaytao;
	}

	public String getNccId() {
		return nccId;
	}

	public void setNccId(String nccId) {
		this.nccId = nccId;
	}

	public String getKhId() {
		return khId;
	}

	public void setKhId(String khId) {
		this.khId = khId;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	

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
		ResultSet rs = db.get("select count(*) as c from ERP_thutien");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public void init() 
	{
//		this.getNppInfo();
		String query =    " select ERP_CANTRUCONGNO.PK_SEQ, ERP_NHACUNGCAP.TEN AS NCC, ISNULL(KHACHHANG.Ten,NPP.TEN) AS KH, ERP_CANTRUCONGNO.NGAYCANTRU , ERP_CANTRUCONGNO.TONGTIEN, ERP_CANTRUCONGNO.TRANGTHAI \n"
						+ " from ERP_CANTRUCONGNO \n"
						+ "      LEFT JOIN ERP_NHACUNGCAP ON ERP_NHACUNGCAP.PK_SEQ = ERP_CANTRUCONGNO.NCC_FK \n"
						+ "      LEFT JOIN KHACHHANG ON KHACHHANG.PK_SEQ = ERP_CANTRUCONGNO.KH_FK  AND ERP_CANTRUCONGNO.ISNPP=0" 
						+ "      LEFT JOIN NHAPHANPHOI  NPP ON NPP.PK_SEQ = ERP_CANTRUCONGNO.KH_FK AND  ERP_CANTRUCONGNO.ISNPP=1 " 
						+ " WHERE 1=1 AND ERP_CANTRUCONGNO.CONGTY_FK = "+this.congtyId;
			
			if(this.nccId.length()>0)
			{
				query+= "AND ERP_CANTRUCONGNO.NCC_FK LIKE '%" + this.nccId+"%'";
			}
			if(this.trangthai.length()>0)
			{
				query+= " AND ERP_CANTRUCONGNO.TRANGTHAI = '" + this.trangthai+	"'";
			}
			if(this.id.length()>0)
			{
				query+= " AND ERP_CANTRUCONGNO.PK_SEQ LIKE '%" + this.id+	"%'";
			}
			
			System.out.println("Câu query "+query);
			
			String query_init = createSplittingData_ListNew(this.db, 30, 10, " NGAYCANTRU desc, trangthai asc, PK_SEQ desc", query) ;
			
			ResultSet rs = db.get(query_init);
			List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
			NumberFormat formatterVND = new DecimalFormat("#,###,###"); 
			
			try
			{
				if(rs!= null)
				{
					IThongTinHienThi ht = null;
					while(rs.next())
					{					
						//LAY DINH KHOAN KE TOAN
						String sql = LayDuLieu(rs.getString("PK_SEQ"));
						
						ResultSet rsKT = db.get(sql);
						List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
							if(rsKT!= null)
							{
								IDinhKhoanKeToan kt = null;
								while(rsKT.next())
								{
									String sotien = rsKT.getString("SOTIEN");
									if(sotien.trim().length() > 0)  sotien = formatterVND.format(Double.parseDouble(rsKT.getString("SOTIEN")));
										
									kt = new DinhKhoanKeToan(rsKT.getString("ID"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),sotien,rsKT.getString("DOITUONG"),
											 rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
									ktList.add(kt);
								}
								rsKT.close();
							}
													
						// INIT
						
						ht = new ThongTinHienThi();
						ht.setId(rs.getString("PK_SEQ"));
						ht.setNgaytao(rs.getString("NGAYCANTRU"));
						ht.setTongtien(rs.getString("TONGTIEN"));
						ht.setTrangthai(rs.getString("TRANGTHAI"));
						ht.setTenKH(rs.getString("KH"));
						ht.setTenNCC(rs.getString("NCC"));
						
						ht.setLayDinhkhoanKT(ktList);
						
						htList.add(ht);																	
					}
					rs.close();
				}
				
			
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			this.hienthiList = htList;
	}

	private String LayDuLieu(String id) 
	{
		String layTK = "";
		try
		{
			 layTK =   " select N'NỢ' as NO_CO, ct.PK_SEQ as ID, tk.SOHIEUTAIKHOAN , ct.TONGTIEN as SOTIEN,"
			 		 + "       ncc.MA + '-' + ncc.TEN as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU , 1 STT, 1 SAPXEP "
				     + " from ERP_CANTRUCONGNO ct inner join ERP_NHACUNGCAP ncc on ct.NCC_FK = ncc.PK_SEQ inner join ERP_TAIKHOANKT tk on ncc.TAIKHOAN_FK = tk.PK_SEQ  "
				     + " where ct.PK_SEQ = "+ id +" AND tk.CONGTY_FK ="+this.congtyId
		    + " UNION ALL "
				     + " select N'CÓ' as NO_CO, ct.PK_SEQ as ID, tk.SOHIEUTAIKHOAN , ct.TONGTIEN as SOTIEN,"
			 		 + "       kh.MA + '-' + kh.TEN as DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU , 1 STT, 2 SAPXEP "
				     + " from ERP_CANTRUCONGNO ct inner join ERP_KHACHHANG kh on ct.KH_FK = kh.PK_SEQ inner join ERP_TAIKHOANKT tk on kh.TAIKHOAN_FK = tk.PK_SEQ  "
				     + " where ct.PK_SEQ = "+ id +" AND tk.CONGTY_FK ="+this.congtyId;
				     
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		if(layTK.trim().length() <=0){
			layTK += " SELECT '' NO_CO, '' ID, '' SOHIEUTAIKHOAN, '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP " +
					 " FROM ERP_CANTRUCONGNO " +
					 " WHERE PK_SEQ ='"+id+"'";
		}
		if(layTK.trim().length()>0) layTK += " ORDER BY ID, STT, SAPXEP \n";
		
		return layTK;
	}

	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
	}

	
	public String getCongtyId() {
	
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
	
		this.congtyId = congtyId;
	}

	
	public String getnppdangnhap() {
	
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
	
		this.nppdangnhap = nppdangnhap;
	}

	
	public String getUserId() {
		
		return this.userId;
	}

	
	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}

	@Override
	public void DBClose() {
		try
		{
			this.db.shutDown();
			this.db = null;
		}catch (Exception e) {
		 
		}
	}
	public void NewDbUtil()
	{
		  if(this.db == null )
		  {
			   this.db  = new dbutils();
		  }
	}

}

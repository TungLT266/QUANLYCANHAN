package geso.traphaco.erp.beans.baocaosudungccdc.imp;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.baocaosudungccdc.IErp_BaoCaoSuDungCCDC;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Erp_BaoCaoSuDungCCDC  implements IErp_BaoCaoSuDungCCDC  {
	String userId;
	String ctyId;	
	String nppId;
	String ctyTen;
	String year;
	String month;
	String diachi;
	String masothue;
	ResultSet rs;
	ResultSet rsBaoCao;
	int [] nam; 
	
	ResultSet dvthRs;
	String dvthId;
	
	String loaiBaoCao;
	String nhomCCDCId;
	String msg;
	dbutils db;
	ResultSet nhomCCDCRs;
	
	ResultSet congtyRs;
	String[] ctyIds;
	String ErpCongTyId;
	String view;
	
	public Erp_BaoCaoSuDungCCDC () {
		this.userId = "";
		this.ctyId = "";
		this.ctyTen = "";
		this.nppId="";
		
		//this.month = Integer.toString(Integer.parseInt(getDate().substring(5, 7)));
		this.month = "";
		
		this.year = getDate().substring(0, 4);

		this.msg = "";
		this.db = new dbutils();
		this.loaiBaoCao="1";
		this.nhomCCDCId="";
		this.dvthId="";
	}


	public void setuserId(String userId) {

		this.userId = userId;
	}

	public String getuserId() {

		return this.userId;
	}

	public void setCtyId(String ctyId) {

		this.ctyId = ctyId;
	}

	public String getCtyId() {

		return this.ctyId;
	}

	public void setCtyRs(ResultSet ctyRs) {

		this.congtyRs = ctyRs;
	}

	public ResultSet getCtyRs() {

		return this.congtyRs;
	}
	
	public void setCtyIds(String[] ctyIds) {

		this.ctyIds = ctyIds;
	}

	public String[] getCtyIds() {

		return this.ctyIds;
	}
	
	public void setView(String view) {
		this.view = view;
	}

	public String getView() {
		return this.view;
	}
	public String getErpCongtyId() {
		
		return this.ErpCongTyId;
	}

	
	public void setErpCongtyId(String id) {
		
		this.ErpCongTyId=id;
	}

	
	public String getCtyTen() {
		return this.ctyTen;
	}

	public String getDiachi() {

		return this.diachi;
	}

	public String getMasothue() {

		return this.masothue;
	}

	public void setMonth(String month) {

		this.month = month;
	}

	public String getMonth() {
		return this.month;
		
	}
	
	public void setYear(String year) {

		this.year = year;
	}

	public String getYear() {
		if(this.year.length()>0){
			return this.year;	
		}else{
			return this.getDate().substring(0, 4);
		}
	}


	public void setMsg(String msg) {

		this.msg = msg;
	}

	public String getMsg() {

		return this.msg;
	}

	public ResultSet getData(){
		return this.rs;
	}
	

	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void DBClose(){
		if(db != null) db.shutDown();
	}


	public void setMasothue(String masothue) {
		this.masothue = masothue;
	}


	public ResultSet getNhomCCDCRs() {
		return nhomCCDCRs;
	}


	public void setNhomCCDCRs(ResultSet nhomCCDCRs) {
		this.nhomCCDCRs = nhomCCDCRs;
	}


	public String getLoaiBaoCao() {
		return loaiBaoCao;
	}


	public void setLoaiBaoCao(String loaiBaoCao) {
		this.loaiBaoCao = loaiBaoCao;
	}


	public String getNhomCCDCId() {
		return nhomCCDCId;
	}


	public void setNhomCCDCId(String nhomCCDCId) {
		this.nhomCCDCId = nhomCCDCId;
	}


	@Override
	public void createRs() {
		String query= " SELECT PK_SEQ,MA +'-' + DIENGIAI AS TEN FROM erp_loaiccdc WHERE TRANGTHAI=1" ;
		this.nhomCCDCRs=db.get(query);
		System.out.println("wewerwe"+query);
		
		query= " SELECT PK_SEQ,MA +'-' + ten AS TEN FROM erp_donvithuchien WHERE TRANGTHAI=1" ;
		this.dvthRs=db.get(query);
		System.out.println("đơn vị thực hiện :"+query);
		String year = getDateTime();
		year=year.substring(0,4);
		int dem=Integer.parseInt(year)+5-2014;
		System.out.println("dem :"+dem);
		this.nam = new int[dem];
		for (int i=0 ;i < dem;i++)
		{
			this.nam[i]= 2014+i;
			System.out.println("NĂM :" +this.nam[i]);
		}
		
	}


	public int[] getNam() {
		return nam;
	}


	public void setNam(int[] nam) {
		this.nam = nam;
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}


	@Override
	public void createRsBaoCao(String sqlBaoCao) {
		// TODO Auto-generated method stub
		try {
			this.rsBaoCao = this.db.get(sqlBaoCao);
		}catch(Exception ex){
			System.out.println("[Lỗi lấy dữ liệu báo cáo]: " + ex.toString()) ;
		}
		
	}
	public void getDataCongty(){
		String query="select TEN,DIACHI,MASOTHUE from ERP_CONGTY WHERE PK_SEQ =" +this.ctyId;
		ResultSet rs= db.get(query);
		try
		{
			while (rs.next())
			{
				this.ctyTen=rs.getString("TEN");
				this.diachi=rs.getString("DIACHI");
				this.masothue=rs.getString("MASOTHUE");
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}


	@Override
	public ResultSet getRsBaoCao() {
		// TODO Auto-generated method stub
		return this.rsBaoCao;
	}


	public ResultSet getDvthRs() {
		return dvthRs;
	}


	public void setDvthRs(ResultSet dvthRs) {
		this.dvthRs = dvthRs;
	}


	public String getDvthId() {
		return dvthId;
	}


	public void setDvthId(String dvthId) {
		this.dvthId = dvthId;
	}
	
	public String getDonViTen()
	{
		String donvi="";
		String query="SELECT DIENGIAI FROM ERP_TRUNGTAMCHIPHI WHERE PK_SEQ =" +this.dvthId ;
		ResultSet rs =db.get(query);
		try {
			if(rs.next())
			{
				donvi= rs.getString("DIENGIAI");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			return donvi;
		}
		return donvi;
	}


	public String getNppId() {
		return nppId;
	}


	public void setNppId(String nppId) {
		this.nppId = nppId;
	}
}

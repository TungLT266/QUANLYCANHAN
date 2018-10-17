package geso.traphaco.erp.beans.bcthekho.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.bcthekho.IBaocao;
import geso.traphaco.erp.beans.bcthekho.ISanpham;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Baocao implements  IBaocao
{
	String userId;
	String userTen;
	
	ResultSet loaiSpRs;
	ResultSet spRs;
	ResultSet khoRs;
	ResultSet chungloaiRs;
	ResultSet dvkdRs;
	ResultSet tondau;
	
	ResultSet Rsbooked;
	
	String loaisanphamIds;
	String Check_SpCoPhatSinh;
	String spIds;
	String khoIds;
	String chungloaiIds;
	String khoTen;
	String dvkdIds;
	String lspId;
	
	String tungay;
	String denngay;
	String msg;
	String flag;
	String laychokiem;
	
	ResultSet ndnhapRs;
	String ndnhapIds;
	ResultSet ndxuatRs;
	ResultSet SP_detailRs;
	String ndxuatIds;
	String spId;
	ResultSet dvtRs;
	String dvtId;
	double quydoi_dvt;
	
	String pivot = "0";
	
	String Xemtheolo;
	String solo;
	
	
	ResultSet maspRS;
	String maspIds;
	
	List<ISanpham> chitiet_thekho = new ArrayList<ISanpham>();
	dbutils db;
	
	public Baocao()
	{
		this.userId = "";
		this.userTen = "";
		this.loaisanphamIds = "";
		this.dvkdIds = "";
		this.chungloaiIds = "";
		this.spIds = "";
		this.khoIds = "";
		this.khoTen = "";
		this.tungay = "";
		this.denngay = "";
		this.msg = "";
		this.flag = "1";
		this.Xemtheolo="0";
		this.laychokiem = "0";
		this.maspIds = "";
		this.ndnhapIds = "";
		this.ndxuatIds= "";
		this.Check_SpCoPhatSinh="";
		this.spId="";
		this.lspId="";
		this.dvtId="";
		  quydoi_dvt=1;
		  this.solo = "";
		
		this.db = new dbutils();
	}
	
	public List<ISanpham> get_chitiet_thekho(){
		return this.chitiet_thekho;
	}
	public void set_chitiet_thekho(List<ISanpham> chitiet_thekho){
		this.chitiet_thekho = chitiet_thekho;
	
	}
	
	public Double  get_quydoi_dvt(){
		return this.quydoi_dvt;
	}
	public void set_quydoi_dvt(double  quydoi_dvt){
		this.quydoi_dvt = quydoi_dvt;
	}
	
	public ResultSet get_dvtRs(){
		return this.dvtRs;
	}
	public void set_dvtRs(ResultSet dvtRs){
		this.dvtRs = dvtRs;
	}
	
	public String  get_dvtId(){
		return this.dvtId;
	}
	public void set_dvtId(String  dvtId){
		this.dvtId = dvtId;
	}
	
	
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;	
	}

	public String getUserTen() 
	{
		return this.userTen;
	}

	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
	}
	
	public ResultSet getLoaiSanPhamRs() 
	{
		return this.loaiSpRs;
	}
	
	public void setLoaiSanPhamRs(ResultSet loaisp) 
	{
		this.loaiSpRs = loaisp;
	}

	public ResultSet getDvkdRs() 
	{
		return this.dvkdRs;
	}
	
	public void setDvkdRs(ResultSet dvkdRs) 
	{
		this.dvkdRs = dvkdRs;
	}

	public String getLoaiSanPhamIds() 
	{
		return this.loaisanphamIds;
	}

	public void setLoaiSanPhamIds(String loaispIds)
	{
		this.loaisanphamIds = loaispIds; 
	}

	public String getDvkdIds() 
	{
		return this.dvkdIds;
	}

	public void setDvkdIds(String dvkdIds)
	{
		this.dvkdIds = dvkdIds; 
	}

	public String getTuNgay()
	{
		return this.tungay;
	}

	public void setTuNgay(String tungay)
	{
		this.tungay = tungay;
	}

	public String getDenNgay() 
	{
		return this.denngay;
	}

	public void setDenNgay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	public ResultSet getSanPhamRs() 
	{
		return this.spRs;
	}

	public void setSanPhamRs(ResultSet sanpham) 
	{
		this.spRs = sanpham;	
	}

	public String getSanPhamIds() 
	{	
		return this.spIds;
	}

	public void setSanPhamIds(String spIds) 
	{
		this.spIds = spIds;
	}
	
	public void createRsBCKHO() 
	{
		Utility util = new Utility();
		String query = "select pk_seq, ten + ', ' + diachi as khoTen from erp_khott where trangthai = '1' and pk_seq in "+util.quyen_khott(this.userId);
		System.out.println("::: LAY KHO : "+ query);
		this.khoRs = db.get(query);
		
		query = "SELECT DISTINCT LSP.PK_SEQ, LSP.MA, LSP.MA + ', ' + LSP.TEN AS TEN " +
				"FROM ERP_LOAISANPHAM LSP " +
				"WHERE LSP.TRANGTHAI = 1 ";
		
		if(this.khoIds.trim().length() > 0 )
			  query+=" AND LSP.PK_SEQ  in ( select loaisanpham_fk from ERP_KHOTT_LOAISANPHAM where khott_fk in ( " + this.khoIds + " ) )";
		
		this.loaiSpRs = this.db.get(query);
		System.out.println("::: LAY LSP : "+ query);
		
		if(this.khoIds.length() > 0)
		{
			query = " select pk_seq, ma, ten " +
					" from ERP_SanPham " +
					" where trangthai = '1' and pk_seq in ( select sanpham_fk from ERP_KhoTT_SanPham where khott_fk  in (" + this.khoIds + ") )" ;
			
			if(this.loaisanphamIds.length() > 0)
			{
				query += " and loaisanpham_fk in (" + this.loaisanphamIds + ")";
			}
			
			if(this.maspIds.trim().length() > 0)
			{
				query += " and MA in (" + this.maspIds + ") ";
			}
			
			System.out.println("::: LAY SP : "+ query);
			this.spRs = db.get(query);
		}
	}

	public void createRs() 
	{
		Utility util = new Utility();
		String query = "select pk_seq, ten + ', ' + diachi as khoTen from erp_khott where trangthai = '1' and pk_seq in ("+util.quyen_khott(this.userId) + ")";
		System.out.println("cau lenh lay kho:\n" + query + "\n=======================================");
		this.khoRs = db.get(query);
		
		query = "SELECT DISTINCT LSP.PK_SEQ, LSP.MA, LSP.MA + ', ' + LSP.TEN AS TEN " +
				"FROM ERP_LOAISANPHAM LSP " +
				"INNER JOIN ERP_SANPHAM SP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK " +
				"INNER JOIN ERP_KHOTT_SANPHAM KHOSP ON KHOSP.SANPHAM_FK = SP.PK_SEQ " +
				"WHERE LSP.TRANGTHAI = 1 ";
		
		if(this.khoIds.trim().length() > 0 )
		{
			  query+=" AND KHOSP.KHOTT_FK  in ( " + this.khoIds + ")";
		}
		this.loaiSpRs = this.db.get(query);
		System.out.println(" lsp ne : "+ query);
		
		
		query = "SELECT DISTINCT DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH AS DVKD, DVKD.DIENGIAI  " +
				"FROM DONVIKINHDOANH DVKD " +
				"INNER JOIN ERP_SANPHAM SP ON DVKD.PK_SEQ = SP.DVKD_FK " +
				"INNER JOIN ERP_KHOTT_SANPHAM KHOSP ON KHOSP.SANPHAM_FK = SP.PK_SEQ " ;
		
		if(this.khoIds.trim().length() > 0 )
		{
			  query+=" AND KHOSP.KHOTT_FK in ( " + this.khoIds + ")";
		}

		if(this.loaisanphamIds.trim().length() > 0)
			query += " and SP.loaisanpham_fk in (" + this.loaisanphamIds + ") ";
		
		this.dvkdRs = this.db.get(query);
		
	// LẤY MÃ LỚN SP
		query = " select distinct MA, MA as TEN from ERP_SANPHAM where pk_seq > 0 ";
		
		/*if(this.khoIds.trim().length() > 0)
			query += " and pk_seq in ( select sanpham_fk from ERP_KHOTT_SANPHAM where khott_fk  in (" + this.khoIds + " )  ) ";*/
		
		if(this.loaisanphamIds.trim().length() > 0)
			query += " and loaisanpham_fk in (" + this.loaisanphamIds + ") ";
		
		if(this.dvkdIds.trim().length() > 0)
			query += " and DVKD_FK in (" + this.dvkdIds + ") ";
		
		
		query += "order by MA ASC";
		
		//System.out.println("---LAY MA LOn SP: " + query);
		this.maspRS = db.get(query);
	
		
		if(this.khoIds.length() > 0)
		{
			query = " select pk_seq, isnull(MA, ma) as ma, ten +'-'+ isnull(quycach, '') as ten " +
					" from ERP_SanPham " +
					" where trangthai = '1' " ;
			
			if(this.loaisanphamIds.length() > 0)
			{
				query += " and loaisanpham_fk in (" + this.loaisanphamIds + ")";
			}
			
			if(this.chungloaiIds.length() > 0)
			{
				query += " and chungloai_fk in (" + this.chungloaiIds + ") ";
			}
			
			if(this.maspIds.trim().length() > 0)
			{
				query += " and MA in (" + this.maspIds + ") ";
			}
			
			if(this.dvkdIds.length() >0 ){
				query += " and DVKD_FK in (" + this.dvkdIds + ") ";
			}
			System.out.println(" list sanpham search :  " + query);
			this.spRs = db.get(query);
			
		}
		
		query="select pk_seq, isnull(ma, ten) as ma, ten from chungloai where trangthai = '1' ";
		this.chungloaiRs = this.db.get(query);
		
		// Lay Noi dung nhap
		 query =" select pk_seq, TEN \n"+
				" from ERP_NOIDUNGNHAP \n"+
				" where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('NK') 	\n"+			
				"union all \n"+
				" select distinct '300000' as pk_seq, N'Nhập nguyên liệu' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300001' as pk_seq, N'Nhập chuyển kho' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300002' as pk_seq, N'Điều chỉnh tồn kho' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300003' as pk_seq, N'Kiểm định chất lượng từ LSX' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '300004' as pk_seq, N'Kiểm định chất lượng từ nhận hàng' as TEN  from ERP_NOIDUNGNHAP ";
		 
		 
		this.ndnhapRs = db.get(query);
		
		// Lay Noi dung xuat
		 query =" select pk_seq, TEN \n"+
				" from ERP_NOIDUNGNHAP \n"+
				" where  trangthai = '1' and upper(substring(ma, 0, 2)) = upper('X')  \n"+			
				"union all \n"+
				" select distinct '400000' as pk_seq, N'Xuất nguyên liệu' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '400001' as pk_seq, N'Điều chỉnh tồn kho' as TEN  from ERP_NOIDUNGNHAP \n"+
				"union all \n"+
				" select distinct '400002' as pk_seq, N'Tiêu hao nguyên liệu' as TEN  from ERP_NOIDUNGNHAP ";
		 
		 
		this.ndxuatRs = db.get(query);
		
		
		
		//******* dùng cho đơn vị tính ******//
		if(this.spId.length() > 0){
			String query1 =	"select dvdl.DIENGIAI, sp.DVDL_FK   \n"+
							"from ERP_SANPHAM sp  \n"+
							"inner join DONVIDOLUONG dvdl on sp.DVDL_FK= dvdl.PK_SEQ  \n"+
							"where sp.PK_SEQ="+this.spId+"  \n"+
							"union all   \n"+ 
							"select dvdl.DIENGIAI, qc.DVDL2_FK \n"+
							"from quycach qc  \n"+
							"inner join DONVIDOLUONG dvdl on qc.DVDL2_FK=dvdl.PK_SEQ   \n"+
							"where DVDL2_FK !=(select DVDL_FK from ERP_SANPHAM where pk_seq="+this.spId+" )  and qc.SANPHAM_FK ="+this.spId+" ";
			//System.out.println("sp dvt :"+ query1);
			this.dvtRs= db.get(query1);
			
			query1= " select dvdl_fk  from erp_sanpham where pk_seq="+this.spId;
			String tmp="";
			ResultSet rs1 = db.get(query1);
			if(rs1!=null){
				try{
					while(rs1.next()){
						tmp= rs1.getString("dvdl_fk");
					}
					rs1.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			this.quydoi_dvt =1;
			
			//------ lấy tỉ lệ quy đổi
			if(this.dvtId.length() >0){
				System.out.println(dvtId+ " ----" +tmp);
				
				if(!dvtId.trim().equals(tmp.trim())){
				
					
					query1="select * from quycach where dvdl2_fk="+dvtId+"  and sanpham_fk="+this.spId;
					System.out.println("qc ne:" + query1);
					ResultSet rs2 = db.get(query1);
					if(rs2!=null){
						try{
							while(rs2.next()){
								this.quydoi_dvt = rs2.getDouble("soluong2")/rs2.getDouble("soluong1") ;
							}
							rs2.close();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				
				}
			}
			
		}
		//****************************************************************/
	}

	public void close()
	{
		this.db.shutDown();
		
		try 
		{
			if (this.chitiet_thekho != null)
				this.chitiet_thekho.clear();
			if (this.khoRs != null)
				this.khoRs.close();
			if (this.dvkdRs != null)
				this.dvkdRs.close();
			if (this.tondau != null)
				this.tondau.close();
			if (this.Rsbooked != null)
				this.Rsbooked.close();
			if (this.ndnhapRs != null)
				this.ndnhapRs.close();
			if (this.ndxuatRs != null)
				this.ndxuatRs.close();
			if (this.SP_detailRs != null)
				this.SP_detailRs.close();
			if (this.dvtRs != null)
				this.dvtRs.close();
			if (this.maspRS != null)
				this.maspRS.close();
			if(this.loaiSpRs != null)
				this.loaiSpRs.close();
			if(this.chungloaiRs != null)
				this.chungloaiRs.close();
			if(this.spRs != null)
				this.spRs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getKhoRs() 
	{
		return this.khoRs;
	}

	public void setKhoRs(ResultSet khoRs) 
	{
		this.khoRs = khoRs;
	}

	public String getKhoIds() 
	{
		return this.khoIds;
	}

	public void setKhoIds(String khoId) 
	{
		this.khoIds = khoId;
	}

	public String getKhoTen() 
	{
		return this.khoTen;
	}

	public void setKhoTen(String khoTen) 
	{
		this.khoTen = khoTen;
	}

	
	public ResultSet getChungloaiRs() 
	{
		return this.chungloaiRs;
	}

	public void setChungloaiRs(ResultSet clRs)
	{
		this.chungloaiRs = clRs;
	}

	
	public String getChungloaiIds() {
		
		return this.chungloaiIds;
	}

	
	public void setChungloaiIds(String loaispIds) {
		
		this.chungloaiIds = loaispIds;
	}

	public String getMsg() {

		return this.msg;
	}

	public void setMsg(String msg) {
		
		this.msg = msg;
	}

	public String getFlag() {

		return this.flag;
	}

	public void setFlag(String flag) {
	
		this.flag = flag;
	}

	public String getHangchokiem() {

		return this.laychokiem;
	}

	public void setHangchokiem(String chokiem) {
		
		this.laychokiem = chokiem;
	}
 
	public String getXemtheolo() {
		 
		return this.Xemtheolo;
	}

 
	public void setXemtheolo(String Xemtheolo) {
		this.Xemtheolo=Xemtheolo;
	}

	
	public String getPivot() {
		return this.pivot;
	}

	
	public void setPivot(String pivot) {
		this.pivot = pivot;
	}

	
	public ResultSet getMaSanPhamRs() {
		
		return this.maspRS;
	}

	
	public void setMaSanPhamRs(ResultSet loaisp) {
		
		this.maspRS = loaisp;
	}

	
	public String getMaSanPhamIds() {
		
		return this.maspIds;
	}

	
	public void setMaSanPhamIds(String loaispIds) {
		
		this.maspIds = loaispIds;
	}

	public ResultSet getNdnhapRs()
	{
		return ndnhapRs;
	}
	
	public void setNdnhapRs(ResultSet ndnhapRs) 
	{
		this.ndnhapRs = ndnhapRs;
	}
	
	public String getNdnhapIds()
	{
		return ndnhapIds;
	}
	
	public void setNdnhapIds(String ndnhapIds) 
	{
		this.ndnhapIds = ndnhapIds;
	}
	
	public ResultSet getNdxuatRs()
	{
		return ndxuatRs;
	}
	
	public void setNdxuatRs(ResultSet ndxuatRs) 
	{
		this.ndxuatRs = ndxuatRs;
	}
	
	public String getNdxuatIds()
	{
		return ndxuatIds;
	}
	
	public void setNdxuatIds(String ndxuatIds) 
	{
		this.ndxuatIds = ndxuatIds;
	}



	@Override
	public String getCheck_SpCoPhatSinh() {
		// TODO Auto-generated method stub
		return this.Check_SpCoPhatSinh;
	}



	@Override
	public void setCheck_SpCoPhatSinh(String sp_cophatsinh) {
		// TODO Auto-generated method stub
		this.Check_SpCoPhatSinh=sp_cophatsinh;
	}




	public ResultSet getSP_detailRs() {

		return this.SP_detailRs;
	}




	public void setSP_detailRs(ResultSet sp_detail) {
		this.SP_detailRs = sp_detail;
		
	}




	public String getspId() {

		return this.spId;
	}




	public void setspId(String spId) {
		this.spId= spId;
		
	}




	public void initview() {
		  	String[] param = new String[4];
		 	param[0] =this.khoIds;
		    param[1] =this.tungay;
		    param[2] =this.denngay;
		    param[3] =this.spId;
		    this.tondau= db.getRsByPro("REPORT_XUATNHAPTON_THEKHO_DAUKY", param);
		  
		    this.SP_detailRs= db.getRsByPro("REPORT_XUATNHAPTON_THEKHO", param);
	}




	public ResultSet getTondau() {
	
		return this.tondau;
	}




	public void setTondau(ResultSet tondau) {
		this.tondau= tondau;
		
	}




	public String getLspId() {

		return this.lspId;
	}




	public void setLspId(String lspId) {
		this.lspId =lspId;
		
	}

	@Override
	public ResultSet getSP_booked_detail() {
		// TODO Auto-generated method stub
		return Rsbooked;
	}

	@Override
	public void setSP_booked_detail(ResultSet sp_detail) {
		// TODO Auto-generated method stub
		Rsbooked =sp_detail;
	}



	public void initview_BOOKED(String khott_fk, String sanphamId, String tungay, String denngay) {
//		String query="";
		String[] param = new String[2];
	 	param[0] =khott_fk;
	 	param[1] =sanphamId;
 
		this.Rsbooked= db.getRsByPro("REPORT_GET_BOOKED", param);
	}

	
	public String getSolo() {

		return this.solo;
	}


	public void setSolo(String solo) {

		this.solo = solo;
	}
}

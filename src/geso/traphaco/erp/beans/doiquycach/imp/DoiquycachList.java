package geso.traphaco.erp.beans.doiquycach.imp;

import java.sql.ResultSet;

import geso.traphaco.erp.beans.doiquycach.IDoiquycachList;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.db.sql.dbutils;

public class DoiquycachList extends Phan_Trang implements IDoiquycachList {
   
	String spId;
	String spTen;
	String ngay;
	String soluong;
	ResultSet dqcRS;
	ResultSet spnhanRS;
	String userId;
	String msg;
	String ctyId;
	dbutils db;
	String Trangthai;
	String spnTen="";
	
	String tungay, denngay, sochungtu, nguoitao;
	
	public DoiquycachList()
	{   
		this.spnTen="";
		this.spId = "";
		this.spTen = "";
		this.Trangthai="";
		this.ngay = "";
	    this.soluong ="";
	    this.ctyId = "";
	    this.msg ="";
	    this.tungay ="";
	    this.denngay ="";
	    this.sochungtu = "";
	    this.nguoitao = "";
	    db = new dbutils();
	}
	
	
	public String getSpnTen() {
		return spnTen;
	}
	public void setSpnTen(String spnTen) {
		this.spnTen = spnTen;
	}
	
	public String getNguoitao() {
		return nguoitao;
	}

	public void setNguoitao(String nguoitao) {
		this.nguoitao = nguoitao;
	}

	public String getSochungtu() {
		return sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}

	public String getTungay() {
		return tungay;
	}

	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

	public String getDenngay() {
		return denngay;
	}

	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}


	public void setSpId(String spId) {
		this.spId = spId;
		
	}

	public String getSpId() {
		return this.spId;
	}
	
	public void setSpTen(String spTen) {
		this.spTen = spTen;
		
	}

	public String getSpTen() {
		return this.spTen;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
		
	}

	public String getNgay() {
		return this.ngay;
	}

	public void setSoluong(String soluong) {
		this.soluong = soluong;
		
	}

	public String getSoluong() {
			return this.soluong;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}
	
	public ResultSet getDqcRS() {
		
		return this.dqcRS;
	}

	
	public void init(String st) {
		
		String sql="";
		if(st.length()>0)
		{
			sql =	" SELECT  isnull(NT.TEN ,'') AS NGUOITAO, isnull(NS.TEN,'') AS NGUOISUA , isnull(convert(varchar, DQC.ngaytao,111),'') as ngaytao, isnull(convert(varchar, DQC.ngaysua, 111),'') as ngaysua " +
					", DQC.PK_SEQ AS DQCID, DQC.NGAY, KHO.TEN AS KHO, DQC.TRANGTHAI " +
					" FROM ERP_XUATDOIQUYCACH DQC " +
					" INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = DQC.KHO_FK " +
					 
					" LEFT JOIN NHANVIEN NT ON NT.PK_SEQ=DQC.NGUOITAO " +
					" LEFT JOIN NHANVIEN NS ON NS.PK_SEQ=DQC.NGUOISUA " +
					" WHERE 1 = 1 " + st + " " ;
					//" ORDER BY DQC.NGAY, KHO.TEN, SP.MA" ;
		}else
			sql =	" SELECT isnull(NT.TEN ,'') AS NGUOITAO, isnull(NS.TEN,'') AS NGUOISUA , isnull(convert(varchar, DQC.ngaytao,111),'') as ngaytao, isnull(convert(varchar, DQC.ngaysua, 111),'') as ngaysua " +
					" , DQC.PK_SEQ AS DQCID, DQC.NGAY, KHO.TEN AS KHO,   DQC.TRANGTHAI " +
					" FROM ERP_XUATDOIQUYCACH DQC " +
					" INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = DQC.KHO_FK " +
					 
					" LEFT JOIN NHANVIEN NT ON NT.PK_SEQ=DQC.NGUOITAO " +
					" LEFT JOIN NHANVIEN NS ON NS.PK_SEQ=DQC.NGUOISUA " ;
					//" ORDER BY DQC.NGAY, KHO.TEN, SP.MA" ;
		
		System.out.print("chuoi: "+ sql);
		//this.dqcRS = db.get(sql);
		this.dqcRS = createSplittingDataNew(this.db, 50, 10, "NGAY DESC, KHO desc   ", sql);
	}
	
	public ResultSet getNdqcRs(String Id){
		String query = 	"SELECT SP.MA + ' - ' + SP.TEN + ' [' + ISNULL(DVDL.DONVI, '') + ']' AS SP, DQC.SOLUONG " +
						"FROM ERP_NHANDOIQUYCACH DQC " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DQC.SANPHAM_FK " +
						"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						"WHERE DOIQUYCACH_FK = " + Id + "";

		return this.db.get(query);
	}
	public void Xoa(String Id){
		
	}
	
	public void setUserId(String userId) {
	    this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}
		
	public void setMsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getMsg() {
		
		return this.msg;
	}
	
	public void DbClose() {
		// TODO Auto-generated method stub
		try{
			if(dqcRS !=null){
				dqcRS.close();
			}
			
		
		}catch(Exception err){
			
		}
		finally
		{
			if(this.db!=null)
			this.db.shutDown();
		}
	}

	@Override
	public void settrangthai(String trangthai) {
		// TODO Auto-generated method stub
		this.Trangthai=trangthai;
	}

	@Override
	public String gettrangthai() {
		// TODO Auto-generated method stub
		return this.Trangthai;
	}
	
}

package geso.traphaco.erp.beans.thuenhapkhau.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.thuenhapkhau.IErpThuenhapkhauList;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.db.sql.dbutils;

public class ErpThuenhapkhauList implements IErpThuenhapkhauList 
{
	String userId;
	String congtyId;
	String Id;
	String poId;
	String ncc;
	String nccId;
	String diengiai;
	String trangthai; 
	String msg;
	String tungay="";
	String denngay="";
	String sotokhai="";
	String sohoadon="";
	String nppdangnhap ="";	
	String loaiMh;
	
	List<IThongTinHienThi> hienthiList;
	
	ResultSet tnkRs;
	
	dbutils db;
	
	public ErpThuenhapkhauList()
	{
		this.tungay="";
		this.denngay="";
		this.sotokhai="";
		this.sohoadon="";
		this.userId = "";
		this.poId = "";
		this.ncc = "";
		this.nccId = "";

		this.Id = "";
		this.trangthai = "";
		this.diengiai = "";
				
		this.msg = "";
		this.nppdangnhap = "";
		
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
		this.db = new dbutils();
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	
	public String getTungay() {
		return tungay;
	}
	public void setTungay(String tungay) {
		this.tungay = tungay;
	}
	public String getSohoadon() {
		return sohoadon;
	}
	public void setSohoadon(String sohoadon) {
		this.sohoadon = sohoadon;
	}
	public String getSotokhai() {
		return sotokhai;
	}
	public void setSotokhai(String sotokhai) {
		this.sotokhai = sotokhai;
	}
	public String getDenngay() {
		return denngay;
	}
	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}
	
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getChungtu() 
	{
		return this.Id;
	}

	public void setChungtu(String Id) 
	{
		this.Id = Id;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void setNcc(String ncc) 
	{
		
		this.ncc = ncc;
	}

	
	public String getNcc()
	{
		
		return this.ncc;
	}
	
	public void setNccId(String nccId) 
	{
		
		this.nccId = nccId;
	}

	
	public String getNccId()
	{
		
		return this.nccId;
	}

	public void setPoId(String poId) 
	{
		
		this.poId = poId;
	}

	
	public String getPoId()
	{
		
		return this.poId;
	}
	
	public ResultSet getNccList(){
		String query = "select pk_seq as nccId, cast(pk_seq as nvarchar(10)) + ' - ' + ma + ', ' + ten as nccTen from ERP_NHACUNGCAP where trangthai = '1' AND CONGTY_FK = "+this.congtyId+" ";
		return this.db.get(query);
	}
	
	private String LayDuLieu(String id, String trangthai) {
		String query = "";
		if(trangthai.equals("0") || trangthai.equals("1")){ //TRẠNG THÁI: CHƯA CHỐT || ĐÃ CHỐT THUẾ NHẬP KHẨU
			query = " SELECT N'NỢ' NO_CO, CQT.PK_SEQ id, CQT.NGAY AS NGAYHOADON, CQT.THUENK SOTIEN, ( SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '15110000' and trangThai = 1 and congTy_FK = " + this.congtyId + ") AS SOHIEUTAIKHOAN, \n"+ 
					" '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+
					" FROM ERP_THUENHAPKHAU CQT \n"+
					" WHERE PK_SEQ = '"+id+"' \n" +				
					
					" UNION ALL \n"+
	
					" SELECT  N'CÓ' NO_CO, CQT.PK_SEQ id, CQT.NGAY AS NGAYHOADON, CQT.THUENK SOTIEN, ( SELECT SOHIEUTAIKHOAN FROM ERP_NHACUNGCAP A INNER JOIN ERP_TAIKHOANKT B ON A.TAIKHOAN_FK= B.PK_SEQ  WHERE a.PK_SEQ = CQT.COQUANTHUE_FK) AS SOHIEUTAIKHOAN,  \n"+
					"		( SELECT A.MA +' - ' + A.TEN FROM ERP_NHACUNGCAP A INNER JOIN ERP_TAIKHOANKT B ON A.TAIKHOAN_FK= B.PK_SEQ  WHERE a.PK_SEQ = CQT.COQUANTHUE_FK) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+
					" FROM ERP_THUENHAPKHAU CQT \n"+
					" WHERE PK_SEQ = '" + id + "' \n"+
					
					" ORDER BY PK_SEQ, STT";
		}
		
		else { //ĐÃ CHỐT THUẾ VAT
			
		query = 
		" SELECT N'NỢ' NO_CO, CQT.PK_SEQ id, CQT.NGAY AS NGAYHOADON, CQT.THUENK SOTIEN, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '15110000' and trangThai = 1 and congTy_FK = " + this.congtyId + ") AS SOHIEUTAIKHOAN, \n"+ 
		" '' DOITUONG,'' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU,	1 STT, 1 SAPXEP \n"+
		" FROM ERP_THUENHAPKHAU CQT \n"+
		" WHERE PK_SEQ = '"+id+"' \n"+

		" UNION ALL \n"+

		" SELECT N'CÓ' NO_CO, CQT.PK_SEQ, CQT.NGAY AS NGAYHOADON,CQT.THUENK  SOTIEN, (SELECT SOHIEUTAIKHOAN FROM ERP_NHACUNGCAP A INNER JOIN ERP_TAIKHOANKT B ON A.TAIKHOAN_FK= B.PK_SEQ  WHERE a.PK_SEQ = CQT.COQUANTHUE_FK) AS SOHIEUTAIKHOAN,  \n"+ 
		"	(SELECT A.MA+' - '+A.TEN FROM ERP_NHACUNGCAP A INNER JOIN ERP_TAIKHOANKT B ON A.TAIKHOAN_FK= B.PK_SEQ  WHERE a.PK_SEQ = CQT.COQUANTHUE_FK) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU,2 STT, 1 SAPXEP \n"+
		" FROM ERP_THUENHAPKHAU CQT \n"+
		" WHERE PK_SEQ = '"+id+"' \n"+
		
		" UNION ALL \n"+

		" SELECT N'NỢ' NO_CO, CQT.PK_SEQ, CQT.NGAY AS NGAYHOADON, CQT.VAT SOTIEN, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13312000' and trangThai = 1 and congTy_FK = " + this.congtyId + ") AS SOHIEUTAIKHOAN, \n"+ 
		" '' DOITUONG,'' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+
		" FROM ERP_THUENHAPKHAU CQT \n"+
		" WHERE PK_SEQ = '"+id+"' \n"+

		" UNION ALL \n"+
		
		" SELECT N'CÓ' NO_CO, CQT.PK_SEQ, CQT.NGAY AS NGAYHOADON,CQT.VAT SOTIEN, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33312000' and trangThai = 1 and congTy_FK = " + this.congtyId + ") AS SOHIEUTAIKHOAN, \n"+ 
		"	(SELECT A.MA+' - '+A.TEN FROM ERP_NHACUNGCAP A INNER JOIN ERP_TAIKHOANKT B ON A.TAIKHOAN_FK= B.PK_SEQ  WHERE a.PK_SEQ = CQT.COQUANTHUE_FK) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU,2 STT, 2 SAPXEP \n"+
		" FROM ERP_THUENHAPKHAU CQT \n"+
		" WHERE PK_SEQ = '"+id+"' \n"+

		" ORDER BY PK_SEQ, STT, SAPXEP \n";
			
		}
		
		if(query.trim().length()<=0){
			query = " SELECT '' NO_CO, '' PK_SEQ, '' NGAYHOADON, '' SOTIEN, '' SOHIEUTAIKHOAN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n " +
					" FROM ERP_THUENHAPKHAU \n " +
					" WHERE PK_SEQ = '"+id+"'";
		}
//		System.out.println("cau lenh lay dinh khoan theu nhap khau:\n" + query + "\n--------------------------------------------");
		return query;
	}	
	
	public void init(String query) 
	{
		String query_init = "";
		
		this.getNppInfo();

			query_init = 
					"SELECT TNK.PK_SEQ AS TNKID, TNK.DIENGIAI, TNK.NGAYCHUNGTU , TNK.SOCHUNGTU , TNK.TRANGTHAI, \n" +
					"NV1.TEN AS NGUOITAO, TNK.NGAYTAO, NV2.TEN AS NGUOISUA, TNK.NGAYSUA \n " +
					"FROM ERP_THUENHAPKHAU TNK \n " +
					"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TNK.NCC_FK \n " +
					"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = TNK.NGUOITAO \n " +
					"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = TNK.NGUOISUA \n " +
					"WHERE TNK.CONGTY_FK = "+this.congtyId+" " ;
//					"AND TNK.NPP_FK = "+this.nppdangnhap+ 

		
		System.out.println("__Thue nhap khau : \n" + query_init + "\n----------------------------------------");
//		if(this.nppdangnhap!=null){
//			query_init+="\n AND TNK.NPP_FK = "+this.nppdangnhap;
//		}
		
		if(this.tungay.length() > 0)
			query_init += "\n and TNK.NGAYCHUNGTU >= '" + this.tungay + "' ";
		if(this.denngay.length() > 0)
			query_init += "\n and TNK.NGAYCHUNGTU <= '" + this.denngay + "' ";
		if(this.sotokhai.length() > 0)
			query_init += "\n and cast(TNK.SOCHUNGTU as varchar(20))  like N'%" + sotokhai + "%'";
		if(this.Id.length() > 0)
			query_init += "\n and cast(TNK.PK_SEQ as varchar(20))  like N'%" + this.Id+ "%'";
		if(sohoadon.length() > 0)
			query_init += "\n and (isnull(TNK.hoadonncc,-1) in (select pk_seq from ERP_HOADONNCC where sohoadon  like N'%" + sohoadon + "%')" +
					"\n OR ISNULL(TNK.SOHOADON,'') like N'%"+ sohoadon + "%')";
		
		if(this.nccId.length() > 0)
			query_init += "\n and NCC.PK_SEQ = '" + this.nccId + "' ";
		
		if(this.poId.length() > 0)
			query_init += "\n and MH.SOPO like N'%" + this.poId + "%'";
		
		if(this.diengiai.length() > 0)
			query_init += "\n and TNK.DIENGIAI like N'%" + this.diengiai + "%' ";
		
		if(this.trangthai.length() > 0)
			query_init += "\n and TNK.TRANGTHAI = '" + this.trangthai + "' ";
	
		query_init+= " AND TNK.PK_SEQ IN  (SELECT THUENHAPKHAU_FK FROM ERP_THUENHAPKHAU_HOADONNCC A  " +
					 " INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK=B.pk_seq  " +
					 " inner join ERP_HOADONNCC_DONMUAHANG HD_MH ON HD_MH.HOADONNCC_FK=B.PK_SEQ " +
					 " INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ=HD_MH.MUAHANG_FK  where MH.LOAI='"+this.loaiMh+"')";
		 query_init+= "\n ORDER BY TNK.PK_SEQ DESC ";
		
		 
		 
		
		ResultSet rs = db.get(query_init);
		
		System.out.println("CAU QUERY LA ' :"+query_init);
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		//this.tnkRs = db.get(sql);
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{			
					ht = new ThongTinHienThi();
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("TNKID"),rs.getString("TRANGTHAI") );					
					
					
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{
								kt = new DinhKhoanKeToan(rsKT.getString("id"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),
											rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
											rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
								System.out.println("Số tiền "+kt.getSotien());
								ktList.add(kt);
							}
							
							rsKT.close();
						}
												
					// INIT					
						
						ht.setTNKID(rs.getString("TNKID"));
						ht.setDIENGIAI(rs.getString("DIENGIAI"));
						ht.setSOCHUNGTU(rs.getString("SOCHUNGTU"));
						ht.setNGAYCHUNGTU(rs.getString("NGAYCHUNGTU"));
						ht.setTRANGTHAI(rs.getString("TRANGTHAI"));
						ht.setNGAYTAO(rs.getString("NGAYTAO"));
						ht.setNGUOITAO(rs.getString("NGUOITAO"));
						ht.setNGUOISUA(rs.getString("NGUOISUA"));
						ht.setNGAYSUA(rs.getString("NGAYSUA"));
						
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

	public void DbClose() 
	{
		try 
		{
			if(this.tnkRs != null)
				this.tnkRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getThuenhapkhauRs() 
	{
		return this.tnkRs;
	}

	public void setThuenhapkhauRs(ResultSet tnkRs) 
	{
		this.tnkRs = tnkRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public void DBClose(){
		try{
			if(this.tnkRs != null) tnkRs.close();
			if(this.db != null) this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
	
	public List<IThongTinHienThi> getHienthiList() {
			
			return this.hienthiList;
		}

	
	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}
	
	public String getnppdangnhap() {
	
		return this.nppdangnhap;
	}
	
	public void setnppdangnhap(String nppdangnhap) {
	
		this.nppdangnhap = nppdangnhap;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}
	@Override
	public void setLoaiMh(String loaimh) {
		// TODO Auto-generated method stub
		this.loaiMh=loaimh;
		
	}
	@Override
	public String getLoaiMh() {
		// TODO Auto-generated method stub
		return this.loaiMh;
	}

}

package geso.traphaco.erp.beans.duyetdonmuahang.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.duyetdonmuahang.IDuyetdonmuahang;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Duyetdonmuahang implements IDuyetdonmuahang {
	
	

	String congtyId;
	String userId;
	String ctyId;
	
	String dvthId;
	ResultSet dvth;
	
	String lspId;
	String ngaymua;
	String maDMH;
	String nccId;
	ResultSet nccList;
	ResultSet lsp;
	
	ResultSet polist;
	
	String loaidh;
	String[] ghichuArr;
	String[] mhidArr;
	String[] spidArr;
	String[] soluongduyetArr;
	String[] dongiaArr;
	String[] donviArr;
	String[] ngaynhanArr;
	String[] thuexuatArr;
	
	
	public String[] getThuexuatArr() {
		return thuexuatArr;
	}

	public void setThuexuatArr(String[] thuexuatArr) {
		this.thuexuatArr = thuexuatArr;
	}

	HttpServletRequest request;	
	String msg;
	dbutils db;
	private Utility util;

	public Duyetdonmuahang(){
		this.userId = "";
		//this.ctyId = "100001";
		this.ctyId="";
		this.dvthId = "";
		this.lspId = "";
		this.ngaymua="";
		this.maDMH = "";
		this.nccId ="";
		this.msg = "";
		this.loaidh = "";
		this.db = new dbutils();
		this.util=new Utility();
	}

	public String[] getDonviArr() {
		return donviArr;
	}


	public void setDonviArr(String[] donviArr) {
		this.donviArr = donviArr;
	}


	public String[] getNgaynhanArr() {
		return ngaynhanArr;
	}


	public void setNgaynhanArr(String[] ngaynhanArr) {
		this.ngaynhanArr = ngaynhanArr;
	}
	public String[] getDongiaArr() {
		return dongiaArr;
	}

	public void setDongiaArr(String[] dongiaArr) {
		this.dongiaArr = dongiaArr;
	}


	public void setNccList(ResultSet nccList)
	{
		this.nccList = nccList;
	}
	
	public ResultSet getNccList() 
	{
		return nccList;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}
	
	public String getNccId() 
	{
		return nccId;
	}
	
	public void setMaDMH(String maDMH) 
	{
		this.maDMH = maDMH;
	}
	
	public String getMaDMH() 
	{
		return maDMH;
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
	
	public String getNgaymua(){
		return this.ngaymua;
	}
	
	public void setNgaymua(String ngaymua){
		this.ngaymua = ngaymua;
	}
	
	public String getDvthId(){
		return this.dvthId;
	}
	
	public void setDvthId(String dvthId){
		this.dvthId = dvthId;
	}
	
	public String getLspId(){
		return this.lspId;
	}
	
	public void setLspId(String lspId){
		this.lspId = lspId;
	}
	
	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getLspList(){
		return this.lsp ;
	}
	
	public void setLspList(ResultSet lsp){
		this.lsp = lsp;
	}

	public ResultSet getDvthList(){
		return this.dvth ;
	}
	
	public void setDvthList(ResultSet dvth){
		this.dvth = dvth;
	}

	public ResultSet getPoList(){
		return this.polist ;
	}
	
	public void setPoList(ResultSet polist){
		this.polist = polist;
	}

	public void setRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public void init()
	{
		
		
		//--sua 11_10_2016 sum soluong cac sp giong nhau
		String query = /* "\n  select NGUOITAO ,MHID,NGAY,DVTH,NCC,TONGTIENAVAT,spid,MA, SP, SUM(SOLUONG) AS SOLUONG, DONGIA, SUM (THANHTIEN) AS THANHTIEN ,vuotNganSach,SOCHUNGTU,  GHICHU  "+
						"\n from ("+	*/
			
							"\n SELECT	distinct NV.TEN as NGUOITAO, MUAHANG.PK_SEQ AS MHID, NGAYMUA AS NGAY, DVTH.TEN AS DVTH, NCC.TEN AS NCC, MUAHANG.TONGTIENAVAT, isnull(sp.pk_Seq,0) as spid, " +
							"\n case MUAHANG.LOAIHANGHOA_FK when '0' then SP.MA when '1' then TS.ma else TKKT.SOHIEUTAIKHOAN+' - ' + TKKT.TENTAIKHOAN + '-' + CP.TEN end as MA,   " +
							"\n case MUAHANG.LOAIHANGHOA_FK when '0' then SP.TEN else MUAHANG_SP.diengiai end AS SP,  " +
							"\n MUAHANG_SP.SOLUONG, isnull(MUAHANG_SP.DONGIA, 0) as DONGIA, isnull(MUAHANG_SP.THANHTIEN, 0) as THANHTIEN, isnull(MUAHANG.VUOTNGANSACH, 0) as vuotNganSach, " +
							"\n CASE WHEN MUAHANG.ISGIACONG=1 THEN  (MUAHANG.SOPO + ' - '+  MUAHANG.GHICHUGC)  ELSE MUAHANG.SOPO  END as SOCHUNGTU, ISNULL(MUAHANG.GHICHU,'') GHICHU , MUAHANG_SP.NGAYNHAN as ngaynhan, MUAHANG_SP.DONVI AS DONVI , MUAHANG_SP.thuexuat as thuexuat  " +
						    "\n FROM ERP_MUAHANG MUAHANG " +
							"\n INNER JOIN NHANVIEN NV ON NV.PK_SEQ = MUAHANG.NGUOITAO   " +
							"\n INNER JOIN ERP_MUAHANG_SP MUAHANG_SP ON MUAHANG_SP.MUAHANG_FK = MUAHANG.PK_SEQ   " +
							"\n INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = MUAHANG.DONVITHUCHIEN_FK "+
							"\n LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MUAHANG.NHACUNGCAP_FK   " +
							"\n LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MUAHANG_SP.SANPHAM_FK  " +
							"\n LEFT join ERP_TAISANCODINH TS on TS.pk_seq = MUAHANG_SP.TAISAN_FK  " +
							"\n LEFT JOIN ERP_NHOMCHIPHI CP on CP.PK_SEQ = MUAHANG_SP.CHIPHI_FK  " + 
							"\n LEFT JOIN ERP_TAIKHOANKT TKKT ON TKKT.PK_SEQ=CP.TAIKHOAN_FK "+
							" LEFT JOIN ERP_DUYETMUAHANG DUYETMUAHANG ON DUYETMUAHANG.MUAHANG_FK = MUAHANG.PK_SEQ  " +
							"\n LEFT JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETMUAHANG.CHUCDANH_FK    "+
						    "\n WHERE MUAHANG.TRANGTHAI NOT IN ('3','4')  AND CHUCDANH.NHANVIEN_FK = '" + this.userId + "' AND isnull(MUAHANG.DACHOT, 0) = '1' " +
						    "\n AND MUAHANG.PK_SEQ NOT IN (SELECT MUAHANG_FK  FROM ERP_DUYETMUAHANG WHERE TRANGTHAI=1 AND QUYETDINH=1)"+ 
						    "\n AND DUYETMUAHANG.TRANGTHAI = 0 AND MUAHANG.congty_fk = '" + this.congtyId + "' and MUAHANG.LOAI = "+ this.loaidh +" " ;
							//"\n AND dvth.pk_seq IN  "+this.util.quyen_donvithuchien(this.userId) + " " +
							//"and MUAHANG.NHACUNGCAP_FK in  " + this.util.quyen_nhacungcap(this.userId) + "  " +
							/*"\n AND MUAHANG.PK_SEQ NOT IN (" + //Loại những đơn mua chi phí chưa gõ mã
							"\n SELECT DISTINCT MH.PK_SEQ " +
							"\n FROM ERP_MUAHANG MH " +
							"\n INNER JOIN ERP_MUAHANG_SP SP ON SP.MUAHANG_FK = MH.PK_SEQ  " +
							"\n WHERE  MH.LOAIHANGHOA_FK = 2 AND ISNULL(SP.CHIPHI_FK, 0) = 0  " +
							" )";*/
		
		
		if(this.loaidh.equals("3")) // DUYET CHO DON HANG TRA VE TYPE=2
		{
			 query = "\n SELECT	distinct NV.TEN as NGUOITAO, MUAHANG.PK_SEQ AS MHID, NGAYMUA AS NGAY, DVTH.TEN AS DVTH, NCC.TEN AS NCC, MUAHANG.TONGTIENAVAT, isnull(sp.pk_Seq,0) as spid, " +
						"\n case MUAHANG.LOAIHANGHOA_FK when '0' then SP.MA when '1' then TS.ma else TKKT.SOHIEUTAIKHOAN+' - ' + TKKT.TENTAIKHOAN + '-' + CP.TEN end as MA,   " +
						"\n case MUAHANG.LOAIHANGHOA_FK when '0' then SP.TEN else MUAHANG_SP.diengiai end AS SP,  " +
						"\n MUAHANG_SP.SOLUONG, isnull(MUAHANG_SP.DONGIA, 0) as DONGIA, isnull(MUAHANG_SP.THANHTIEN, 0) as THANHTIEN, isnull(MUAHANG.VUOTNGANSACH, 0) as vuotNganSach, " +
						"\n CASE WHEN MUAHANG.ISGIACONG=1 THEN  (MUAHANG.SOPO + ' - '+  MUAHANG.GHICHUGC)  ELSE MUAHANG.SOPO  END as SOCHUNGTU, ISNULL(MUAHANG.GHICHU,'') GHICHU , MUAHANG_SP.NGAYNHAN as ngaynhan, MUAHANG_SP.DONVI AS DONVI , MUAHANG_SP.thuexuat as thuexuat  " +
					    "\n FROM ERP_MUAHANG MUAHANG " +
						"\n INNER JOIN NHANVIEN NV ON NV.PK_SEQ = MUAHANG.NGUOITAO   " +
						"\n INNER JOIN ERP_MUAHANG_SP MUAHANG_SP ON MUAHANG_SP.MUAHANG_FK = MUAHANG.PK_SEQ   " +
						"\n INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = MUAHANG.DONVITHUCHIEN_FK "+
						"\n LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MUAHANG.NHACUNGCAP_FK   " +
						"\n LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MUAHANG_SP.SANPHAM_FK  " +
						"\n LEFT join ERP_TAISANCODINH TS on TS.pk_seq = MUAHANG_SP.TAISAN_FK  " +
						"\n LEFT JOIN ERP_NHOMCHIPHI CP on CP.PK_SEQ = MUAHANG_SP.CHIPHI_FK  " + 
						"\n LEFT JOIN ERP_TAIKHOANKT TKKT ON TKKT.PK_SEQ=CP.TAIKHOAN_FK "+
						" LEFT JOIN ERP_DUYETMUAHANG DUYETMUAHANG ON DUYETMUAHANG.MUAHANG_FK = MUAHANG.PK_SEQ  " +
						"\n LEFT JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETMUAHANG.CHUCDANH_FK    "+
					    "\n WHERE MUAHANG.TRANGTHAI NOT IN ('3','4')  AND CHUCDANH.NHANVIEN_FK = '" + this.userId + "' AND isnull(MUAHANG.DACHOT, 0) = '1' " +
					    "\n AND MUAHANG.PK_SEQ NOT IN (SELECT MUAHANG_FK  FROM ERP_DUYETMUAHANG WHERE TRANGTHAI=1 AND QUYETDINH=1)"+ 
					    "\n AND DUYETMUAHANG.TRANGTHAI = 0 AND MUAHANG.congty_fk = '" + this.congtyId + "' and MUAHANG.TYPE = 2" ;
						
		}
		
		if(this.dvthId!=null&&this.dvthId.trim().length() > 0)
		{
			query += "\n AND MUAHANG.DONVITHUCHIEN_FK = '" + this.dvthId + "' ";
		}
		if(this.ngaymua!=null && this.ngaymua.trim().length() > 0)
		{
			query += "\n AND MUAHANG.NGAYMUA = '" + this.ngaymua + "' ";
		}
		if(this.nccId !=null && this.nccId.length() > 0)
		{
			query += "\n AND MUAHANG.NHACUNGCAP_FK = '"+ this.nccId +"' ";
		}
		if(this.maDMH !=null && this.maDMH.length() > 0)
		{
			query += "\n AND MUAHANG.SOPO LIKE '%"+ this.maDMH +"%' OR MUAHANG.PK_SEQ LIKE '%"+ this.maDMH +"%' ";
		}
		
	/*	query+= " \n ) data   \n  group by  NGUOITAO,MHID,NGAY,DVTH,NCC,TONGTIENAVAT,spid, MA,SP, DONGIA,vuotNganSach,SOCHUNGTU,  GHICHU ";*/
		
		query += "\n ORDER BY MHID ASC , NGAY ASC";
 
		System.out.println("1.Khoi tao duyet mua hang: " + query);
		
		
		
		this.polist = this.db.get(query);
		
		query="SELECT PK_SEQ AS DVTHID, TEN AS DVTH FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI = '1' ";
		this.dvth = this.db.get(query) ;
		this.lsp = this.db.get("SELECT PK_SEQ AS LSPID, TEN AS LSP FROM ERP_LOAISANPHAM WHERE TRANGTHAI = '1' AND CONGTY_FK = "+ congtyId +" ");
		System.out.println("SELECT PK_SEQ AS LSPID, TEN AS LSP FROM ERP_LOAISANPHAM WHERE TRANGTHAI = '1' AND CONGTY_FK = "+ congtyId +" ");
		this.nccList = this.db.get("SELECT PK_SEQ, MA + '-' + TEN AS TENNCC FROM ERP_NHACUNGCAP WHERE TRANGTHAI = '1' AND CONGTY_FK = "+ congtyId +"  and duyet = '1'");
	}
	
	public boolean Duyetmuahang(String Id)
	{	
		String my = "";
		try
		{
			db = new dbutils();
			String query=" SELECT * "+
					     " FROM ERP_MUAHANG_SP  "+
						 " WHERE MUAHANG_FK= "+Id+" AND SANPHAM_FK IS NULL AND CHIPHI_FK IS NULL AND TAISAN_FK IS NULL AND CCDC_FK IS NULL ";
			System.out.println("Câu query "+query);
			ResultSet rs =db.get(query);
			
			if(rs.next()){
				this.msg="DDMH1.1 Không thể duyệt mua hàng này, đơn hàng này chưa được chọn mã chi phí hoặc sản phẩm chi tiết . " +
						 " Vui lòng báo bộ phận kế toán  để được giải quyết";
			    return false;
			}
			my += "1";
			// KTRA NGUOI QUYET DINH
			query = " select count(*) isQuyetDinh \n"+
			 		" from ERP_DUYETMUAHANG D inner join ERP_CHUCDANH C on D.CHUCDANH_FK = C.PK_SEQ \n"+
			 		" where D.MUAHANG_FK ='" + Id + "' and D.QUYETDINH = '1' and C.NHANVIEN_FK = "+ this.userId +"   \n" ;
			ResultSet rsTT = db.get(query);
			boolean isQD = false;
			my += "2";
			if(rsTT!= null)
			{
				while(rsTT.next())
				{
					if(rsTT.getInt("isQuyetDinh") > 0){
						isQD = true;
					}
				}rsTT.close();
			}
			
			my += "3";
			// KIEM TRA PO CO VUOT NO: CO > CAP TREN DUYET MOI DC
			query = " SELECT isnull(HANMUCNO.TONGTIEN, 0) - ISNULL(MUAHANG.TONGGIATRI,0) - ISNULL(DATHANHTOAN.SOTIEN,0) as COTHESUDUNG, \n"+
					"       (select TONGTIENAVAT*ISNULL(TYGIAQUYDOI,1) from ERP_MUAHANG where PK_SEQ = '"+ Id +"') TGTPO  \n"+
					" FROM  \n"+
					" ( select PK_SEQ, ISNULL(HANMUCNO,0) AS TONGTIEN  \n"+
					"  from ERP_NHACUNGCAP \n"+
					"  where PK_SEQ = (select NHACUNGCAP_FK from ERP_MUAHANG where PK_SEQ = '"+ Id +"') \n"+
					" )HANMUCNO LEFT JOIN \n"+
					" ( select NHACUNGCAP_FK, SUM(TONGTIENAVAT*ISNULL(TYGIAQUYDOI,1)) as TONGGIATRI   \n"+
					"  from ERP_MUAHANG \n"+
					"  where PK_SEQ != '"+ Id +"' AND TRANGTHAI not in (3,4) \n"+
					"  group by NHACUNGCAP_FK \n"+
					" )MUAHANG ON MUAHANG.NHACUNGCAP_FK = HANMUCNO.PK_SEQ  LEFT JOIN \n"+
					" (select TT.NCC_FK, SUM(TT.SOTIENTT) AS SOTIEN \n"+
					"  from ERP_THANHTOANHOADON TT \n"+
					"  where TT.TRANGTHAI = 1 \n"+
					"  group by TT.NCC_FK \n"+
					" ) DATHANHTOAN ON HANMUCNO.PK_SEQ =DATHANHTOAN.NCC_FK  ";
			
			System.out.println("Câu init "+query);
			ResultSet rsKT = db.get(query);
			boolean isVuotNo = false;
			my += "4";
			if(rsKT!= null)
			{
				while(rsKT.next())
				{
					if(rsKT.getDouble("COTHESUDUNG") < rsKT.getDouble("TGTPO"))
						isVuotNo = true;

				}rsKT.close();
			}
			
			db.getConnection().setAutoCommit(false);
			my += "5";
			//check noi bo
//			String NoiBo = "";
//			String ngaymuahang = "";
//			String nccId = "";
			String loai = "";
			//String idNPP = "";
//			String maNCC = "";
			
			 query = " select NOIBO, NGAYMUA, NHACUNGCAP_FK, ncc.ma " +
			 		 //", ( select PK_SEQ from NHAPHANPHOI where MaFAST = ncc.MA and trangthai = 1 ) as idNPP " +
			 		 ", loai \n"+
			 		 " from ERP_MUAHANG mh left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK " +
					 " where mh.PK_SEQ = '" + Id + "' ";
			 my += "6";
				System.out.println("Cau KT "+query);
			  rs = this.db.get(query);
			if(rs.next())
			{
//				NoiBo = rs.getString("noibo");
//				ngaymuahang = rs.getString("NGAYMUA");
			//	nccId = rs.getString("NHACUNGCAP_FK");
				loai = rs.getString("loai");
				//idNPP = rs.getString("idNPP") == null ? "" : rs.getString("idNPP");
//				maNCC = rs.getString("ma");
				rs.close();
			}
			my += "7";
			if( this.loaidh.equals("2") && isVuotNo )
			{
				if(!isQD) // Khong phai nguoi Quyet Dinh >> Bao loi
				{
					this.msg = "Đơn mua hàng này đã vượt hạn mức nợ, bạn không được phép duyệt. Vui lòng thông báo cấp trên duyệt.";
					db.getConnection().rollback();
					return false;
				}else
				{
					query =  " UPDATE ERP_DUYETMUAHANG SET TRANGTHAI = '1' " +
							 " WHERE MUAHANG_FK = '" + Id + "' AND " +
							 " CHUCDANH_FK IN (SELECT PK_SEQ FROM ERP_CHUCDANH WHERE NHANVIEN_FK = '" + this.userId + "')";
			 
					if(!this.db.update(query)) 
					{
						this.msg="DDMH1.2 Lỗi trong quá trình duyệt";
						db.getConnection().rollback();
						return false;
					}
				}
			}else
			{
				query =  " UPDATE ERP_DUYETMUAHANG SET TRANGTHAI = '1' " +
						 " WHERE MUAHANG_FK = '" + Id + "' AND " +
						 " CHUCDANH_FK IN (SELECT PK_SEQ FROM ERP_CHUCDANH WHERE NHANVIEN_FK = '" + this.userId + "')";
		 
				if(!this.db.update(query)) 
				{
					this.msg = "DDMH1.3 Lỗi trong quá trình duyệt";
					db.getConnection().rollback();
					return false;
				}
			}
			my += "-11";	
			// Nếu là nguoi QD: cập nhật trang thai = 1
			if(isQD)
			{
				query =  " UPDATE ERP_MUAHANG SET TRANGTHAI = '1' WHERE PK_SEQ = '" + Id + "' ";
				my += "10";
				if(!this.db.update(query)) 
				{
					this.msg = "DDMH1.4 Lỗi trong quá trình duyệt";
					db.getConnection().rollback();
					return false;
				}
			}
			my += "-12";
			if(this.loaidh.equals("1"))
			{
				if(this.mhidArr.length > 0)
				{
					for(int i = 0; i < this.mhidArr.length; i++)
					{
						if(this.mhidArr[i].equals(Id))
						{
							/*query =  " UPDATE ERP_MUAHANG_SP SET soluong = '"+this.soluongduyetArr[i]+"', soluong_new = '"+this.soluongduyetArr[i]+"' " +
									 " WHERE MUAHANG_FK = '" + Id + "' AND sanpham_fk = "+this.spidArr[i] +" AND DONGIA ="+ this.dongiaArr[i];*/
							
							query =  " UPDATE ERP_MUAHANG_SP SET soluong = '"+this.soluongduyetArr[i]+"', soluong_new = '"+this.soluongduyetArr[i]+"' " +
							 " WHERE MUAHANG_FK = '" + Id + "' AND sanpham_fk = "+this.spidArr[i] +" AND DONGIA ="+ this.dongiaArr[i] +
							 " AND DONVI='"+ this.donviArr[i] +"' AND NGAYNHAN='"+this.ngaynhanArr[i] +"' AND THUEXUAT="+this.thuexuatArr[i];
							
							
							System.out.println("cap nhat so luong duyet "+query);
							my += "--13";
							if(!this.db.update(query)) 
							{
								this.msg = "DDMH1.5 Lỗi trong quá trình duyệt";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			my += "8";
			/*	if( loai.equals("1") )
			{
				if( idNPP.trim().length() <= 0 )
				{
					this.msg = "Không tìm thấy công ty có mã (" + maNCC + ") để tạo SO tự động ";
					db.getConnection().rollback();
					return false;
				}*/
				/*if( idNPP.trim().length() > 0 )
				{
					this.msg = this.createDONDATHANG( db, Id, idNPP );
					System.out.println(":::: LOI KHI TAO DDH TU DONG: " + msg );
					if( msg.trim().length() > 0 )
					{
						this.msg = "DDMH1.6 Lỗi trong quá trình duyệt\n\n\n\n\n" + this.msg;
						db.getConnection().rollback();
						return false;
					}
				}
			}*/
			my += "9";
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			this.msg = "DDMH1.7 Lỗi trong quá trình duyệt\n" + e.getMessage() + e.toString() + my;
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return false;
		}
		
		return true;
	}
	
	private String createDONDATHANG(dbutils db, String id, String nppId ) 
	{
		String msg = "";
		try
		{
			//CHECK NGÀY HIỆN TẠI, XEM CÓ LƠN HƠN NGÀY KHÓA SỔ KINH DOANH KHÔNG
			/*int checkKS_KINHDOANH = util.CheckKhoaSoKinhDoanh(db, nppId, this.getDateTime(), "", "", "");
			String chuyenSALES = "0";
			if(checkKS_KINHDOANH == 1 )
			{
				chuyenSALES = "1";
			}*/
			
			String query =  "  insert ERP_DondathangNPP(ngaydonhang, ngaydenghi, loaidonhang, npp_dachot, ghichu, trangthai, dvkd_fk, kbh_fk, nhomkenh_fk, gsbh_fk, ddkd_fk, npp_dat_fk, khachhang_fk, nhanvien_fk, " + 
							"  	npp_fk, kho_fk, ngaytao, nguoitao, ngaysua, nguoisua )  " + 
							"  select '" + this.getDateTime() + "' as ngaydonhang, '" + this.getDateTime() + "' as ngaydenghi, 0 as loaidonhang, 0 as npp_dachot, N'Đơn hàng từ duyệt PO ' + CAST( a.PK_SEQ as varchar(10)  ) as ghichu, 0 as trangthai, 100001 as dvkd_fk, " + 
							"  		( 100078 ) as kbh_fk, " +
							" 		( select top(1) NK_FK from NHAPP_NHOMKENH where npp_fk = '" + nppId + "' ) as nhomkenh_fk, " + 
							"		( select top(1) GSBH_FK from DDKD_GSBH where DDKD_FK = ( select top(1) ddkd_fk from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk = b.pk_seq where b.trangthai = 1 and a.npp_fk = '" + nppId + "' ) ) as gsbh_fk, " + 
							" 		( select top(1) ddkd_fk from DAIDIENKINHDOANH_NPP a inner join DAIDIENKINHDOANH b on a.ddkd_fk = b.pk_seq where b.trangthai = 1 and a.npp_fk = '" + nppId + "' ) as ddkd_fk, a.npp_fk as  npp_dat_fk, null, null, " + 
							"  		'" + nppId + "' as npp_fk, 100000 as kho_fk, " + 
							"  		'', null as nguoitao, '', null as nguoisua " + 
							"  from ERP_MUAHANG a inner join ERP_NHACUNGCAP b on a.NHACUNGCAP_FK = b.PK_SEQ " + 
							"  where a.PK_SEQ = '"+id+"' ";
			System.out.println("-- INSERT DDH: " + query );
			if(!db.update(query))
			{
				msg = "CDDH1.1 Lỗi khi tạo mới đơn hàng: " + query;
				return msg;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_DondathangNPP') as ID ");
			String dhId = "";
			if(rsDDH.next())
			{
				dhId = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			//CHEN SAN PHAM
			query = "  insert ERP_DONDATHANGNPP_SANPHAM( dondathang_fk, SANPHAM_FK, sanphamTEN, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay, dongiaGOC, ddkd_fk )  " + 
					"  select '" + dhId + "' dondathang_fk, a.SANPHAM_FK, b.TEN as sanphamTEN, a.soluong, a.DONGIA / ( 1 + b.thuexuat / 100.0  ) as dongia, 0 chietkhau, b.thuexuat as thueVAT,  " + 
					"  b.dvdl_fk, '' tungay, '' denngay, a.DONGIA / ( 1 + b.thuexuat / 100.0  ) as dongiaGOC, null as ddkd_fk  " + 
					"  from ERP_MUAHANG_SP a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " + 
					"  where MUAHANG_FK = '" + id + "' ";
			System.out.println("-- INSERT DDH: " + query );
			if(!db.update(query))
			{
				msg = "CDDH1.2 Lỗi khi tạo mới đơn hàng: " + query;
				return msg;
			}
		}
		
		catch(Exception ex)
		{
			msg = "CDDH1.3 Lỗi khi tạo đơn hàng";
			ex.printStackTrace();
			msg = ex.getMessage();
		}
		
		return msg;
	}

	public String getDaduyet(String mhId)
	{
		String tmp = "";
		String query =  "SELECT DUYETMUAHANG.MUAHANG_FK AS MHID, NV.PK_SEQ AS NVID, NV.DANGNHAP AS NVTEN " +
						"FROM ERP_DUYETMUAHANG DUYETMUAHANG " +
						"INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETMUAHANG.CHUCDANH_FK " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = CHUCDANH.NHANVIEN_FK " +
						"WHERE DUYETMUAHANG.TRANGTHAI = '1' AND MUAHANG_FK = " + mhId + "  ";
		ResultSet rs = this.db.get(query);
		try{
			if(rs != null){
				while(rs.next()){
					tmp = tmp  + rs.getString("NVTEN") + " ; " ;
				}
				if(tmp.length() > 0)
					tmp = tmp.substring(0, tmp.length()-2);
				rs.close();
				return tmp;
			}else{
				return tmp;
			}
		}catch(java.sql.SQLException e){
			e.printStackTrace();
			return tmp;
		}
	}
	
	public void DBclose(){
		try{
			if(this.dvth != null) this.dvth.close();
			if(this.lsp != null) this.lsp.close();
			if(this.polist != null) this.lsp.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		this.db.shutDown();
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public String getLoaidh() 
	{
		return this.loaidh;
	}

	public void setLoaidh(String loaidh) 
	{
		 this.loaidh = loaidh;
	}

	public String[] getGhiChuArr() {
		return this.ghichuArr;
	}

	public void setGhiChuArr(String[] ghichuArr) {
		this.ghichuArr = ghichuArr;
	}

	public boolean Capnhatmuahang(String id) 
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String ghichu = "";
			if(this.ghichuArr != null)
			{
				for(int i = 0; i < this.ghichuArr.length; i++)
				{
					ghichu += this.ghichuArr[i] + "__";
				}
			}
			
			String query =  " UPDATE ERP_MUAHANG SET GHICHU = N'" + ghichu.replaceAll("'", "''") + "' WHERE PK_SEQ = '" + id + "' ";
			System.out.println("Câu cập nhật Ghi chú" +query);
			if(!this.db.update(query)) 
			{
				this.msg = "Không thể cập nhật ghi chú.";
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	public String[] getMhIdArr() {
		return this.mhidArr;
	}

	public void setMhIdArr(String[] mhidArr) {
		this.mhidArr = mhidArr;
	}
	
	public String[] getSpIdArr() {
		return this.spidArr;
	}
	
	public void setSpIdArr(String[] spidArr) {
		this.spidArr = spidArr;
	}

	public String[] getSoluongduyetArr() {
		return this.soluongduyetArr;
	}

	public void setSoluongduyetArr(String[] soluongduyetArr) {
		this.soluongduyetArr = soluongduyetArr;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
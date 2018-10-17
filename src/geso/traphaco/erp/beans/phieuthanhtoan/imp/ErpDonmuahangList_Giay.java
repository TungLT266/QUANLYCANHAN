package geso.traphaco.erp.beans.phieuthanhtoan.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.erp.beans.phieuthanhtoan.IErpDonmuahangList_Giay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpDonmuahangList_Giay extends Phan_Trang implements IErpDonmuahangList_Giay 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	String dvthId;
	ResultSet dvthRs;
	
	String nccTen;
	String tongtien;
	String msg;
	String task;
	String sodonmuahang;
	private String soChungTu;
	private String maChungTu;
	String loaisanphamid;
	String loaihanghoa = "";
	ResultSet loaisanphamRs;
	
	ResultSet dmhRs;
	
	ResultSet nccRs;    
	String nccIds;
	ResultSet nspRs;
	String nspIds;
	
	String isdontrahang;
	String trangthai;
	
	ResultSet nguoitaoRs;    
	String nguotaoIds;
	
	
	String mactsp = "";
	String ngoaiKhoan = "";
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	private Utility util;
	
	List<IThongTinHienThi> hienthiList;
	
	public ErpDonmuahangList_Giay()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.dvthId = "";
		this.nccTen = "";
		this.tongtien = "";
		this.task = "";
		this.loaisanphamid="";
		this.msg = "";
		 
		this.nccIds = "";
		this.nspIds = "";
		this.sodonmuahang = "";
		this.soChungTu = "";
		this.maChungTu = "";
		// phieu thanh toan type=1
		this.isdontrahang = "1";
		this.trangthai = "";
		this.nguotaoIds = "";
		
		currentPages = 1;
		num = 1;
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
		this.db = new dbutils();
		 util=new Utility();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
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

	public String getDvthId() 
	{
		return this.dvthId;
	}

	public void setDvthId(String dvthid) 
	{
		this.dvthId = dvthid;
	}

	public ResultSet getDvthList() 
	{
		return this.dvthRs;
	}

	
	public void setDvthList(ResultSet dvthlist) 
	{
		this.dvthRs = dvthlist;
	}

	public String getNCC() 
	{
		return this.nccTen;
	}

	public void setNCC(String ncc) 
	{
		this.nccTen = ncc;
	}

	public String getTongtiensauVat() 
	{
		return this.tongtien;
	}

	public void setTongtiensauVat(String ttsauvat)
	{
		this.tongtien = ttsauvat;
	}

	public void setmsg(String msg) 
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getDmhList() 
	{
		return this.dmhRs;
	}

	public void setDmhList(ResultSet dmhlist) 
	{
		this.dmhRs = dmhlist;
	}
	
	public String getSoChungTu() {
		return soChungTu;
	}

	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}
	@Override
	public String getMaChungTu() {
		return maChungTu;
	}

	@Override
	public void setMaChungTu(String maChungTu) {
		this.maChungTu = maChungTu;
	}

	private String LayDuLieu(String Id) {
		String query = "";
		
		String laytk = "";
		String taikhoanCO_DS = "";
		String taikhoanNO_DS = "";
		
		String taikhoanCO_VAT = "";
		String taikhoanNO_VAT = "";
		
//		String loaidoituongCO = "";
//		String madoituongCO = "";
//		
//		String loaidoituongNO = "";
		String madoituongNO = "";
		query= " SELECT DISTINCT N'CHI PHÍ ' AS LOAIDOITUONGNO, E.PK_SEQ AS MADOITUONGNO,    " +  
			   "  CASE  WHEN A.NHACUNGCAP_FK IS NOT NULL THEN N'NHÀ CUNG CẤP' WHEN A.NHANVIEN_FK  IS NOT NULL THEN N'NHÂN VIÊN' ELSE N'KHÁCH HÀNG' END AS LOAIDOITUONGCO,   " +  
			   "  CASE  WHEN  A.NHACUNGCAP_FK IS NOT NULL THEN NCC.PK_SEQ WHEN A.NHANVIEN_FK  IS NOT NULL THEN NV.PK_SEQ ELSE KH.PK_SEQ END AS MADOITUONGCO, " +  
			   "  A.NGAYMUA AS NGAYHOADON, (D.SOLUONG* D.DONGIA)  AS DOANHSO,(D.SOLUONG* D.DONGIA)*  D.PHANTRAMTHUE/100 AS THUE   ,    " +  
			   "  CASE  WHEN A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.TAIKHOAN_FK WHEN A.NHANVIEN_FK  IS NOT NULL THEN NV.TAIKHOAN_FK ELSE KH.TAIKHOAN_FK END AS TAIKHOANCO_DS,   " +  
			   "  CASE  WHEN A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.TAIKHOAN_FK WHEN A.NHANVIEN_FK  IS NOT NULL THEN NV.TAIKHOAN_FK ELSE KH.TAIKHOAN_FK  END AS TAIKHOANCO_VAT,   " +  
			   "  ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = E.TAIKHOAN_FK AND CONGTY_FK = A.CONGTY_FK) AS TAIKHOANNO_DS,   " +  
			   "  ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13310000' AND CONGTY_FK = A.CONGTY_FK) AS TAIKHOANNO_VAT   " +  
			   "  FROM ERP_MUAHANG A  " +  
			   "  LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = A.NHANVIEN_FK    " +  
			   "  LEFT JOIN ERP_NHACUNGCAP NCC ON A.NHACUNGCAP_FK = NCC.PK_SEQ   " +  
			   "  LEFT JOIN KHACHHANG KH ON A.KHACHHANG_FK = KH.PK_SEQ   " + 
			   "  INNER JOIN ERP_MUAHANG_SP D ON A.PK_SEQ = D.MUAHANG_FK   " +  
			   "  LEFT JOIN ERP_NHOMCHIPHI E ON D.CHIPHI_FK = E.PK_SEQ   " +  
			   "  WHERE   A.PK_SEQ ="+Id;
		
	//System.out.println(query);
		ResultSet rsTk = db.get(query);
		
		int i = 0;
//		int j = 1;
		if(rsTk!= null)
			{ try{
				while(rsTk.next())
				{
					
					double totalDS = Math.round(rsTk.getDouble("DOANHSO"));
					double totalVAT = Math.round(rsTk.getDouble("THUE"));
					
					taikhoanCO_DS = rsTk.getString("taikhoanCO_DS");
					taikhoanNO_DS = rsTk.getString("taikhoanNO_DS");
					
					taikhoanCO_VAT = rsTk.getString("taikhoanCO_VAT");
					taikhoanNO_VAT = rsTk.getString("taikhoanNO_VAT");
					
//					loaidoituongCO = rsTk.getString("loaidoituongCO");
//					madoituongCO = rsTk.getString("madoituongCO");
//					loaidoituongNO = rsTk.getString("loaidoituongNO");
					madoituongNO = rsTk.getString("madoituongNO");
	
//					String ngayghinhan = rsTk.getString("ngayhoadon");
//					String nam = ngayghinhan.substring(0, 4);
//					String thang = ngayghinhan.substring(5, 7);
//					
//					String tiente_fk = "100000";
				
				
				
					if(totalDS > 0)
					{
						if(laytk.trim().length()>0) laytk += " UNION ALL \n";
						
						laytk +=
							"	SELECT DISTINCT 	N'NỢ' NO_CO, a.pk_seq id, (select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where PK_SEQ = '"+taikhoanNO_DS+"') as SOHIEUTAIKHOAN, " +
							" 			"+totalDS+" as SOTIEN, \n"+
							"	   		(SELECT DIENGIAI FROM ERP_NHOMCHIPHI WHERE PK_SEQ = "+madoituongNO+") AS DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+ 
							"	FROM 	ERP_MUAHANG a   \n" +
							"   WHERE   A.PK_SEQ ="+Id+
		
							" 	UNION ALL \n"+
		
							" 	SELECT DISTINCT N'CÓ' NO_CO, a.pk_seq id, (select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where pk_seq = "+taikhoanCO_DS+" AND CONGTY_FK ="+this.congtyId+" ) as SOHIEUTAIKHOAN, " +
							"			"+totalDS+" as SOTIEN, \n"+
							"	   	   (CASE  WHEN  A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.TEN WHEN  A.KHACHHANG_FK  IS NOT NULL THEN KH.TEN  ELSE NV.TEN END) AS DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+ i +" STT, 2 SAPXEP \n"+ 
							"  FROM ERP_MUAHANG A  " +  
							"  		LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = A.NHANVIEN_FK    " +  
							"  		LEFT JOIN ERP_NHACUNGCAP NCC ON A.NHACUNGCAP_FK = NCC.PK_SEQ   " +  
							"  		LEFT JOIN KHACHHANG KH ON A.KHACHHANG_FK = KH.PK_SEQ   " +  
							"  		INNER JOIN ERP_MUAHANG_SP D ON A.PK_SEQ = D.MUAHANG_FK   " +  
							"  		INNER JOIN ERP_NHOMCHIPHI E ON D.CHIPHI_FK = E.PK_SEQ   " +  
							"  WHERE   A.PK_SEQ ="+Id;
						i++;
					}
					
					if(totalVAT > 0)
					{
						if(laytk.trim().length()>0) laytk += " UNION ALL \n";
						
						laytk +=
							"	SELECT DISTINCT	N'NỢ' NO_CO, a.pk_seq id, ( select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where pk_seq = "+taikhoanNO_VAT+" ) as SOHIEUTAIKHOAN, " +
							" 			"+totalVAT+" as SOTIEN, \n"+
							"	   		(SELECT DIENGIAI FROM ERP_NHOMCHIPHI WHERE PK_SEQ = "+madoituongNO+") AS DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+ i +" STT,1 SAPXEP \n"+ 
							"	FROM 	ERP_MUAHANG a   \n" +
							"   WHERE   A.PK_SEQ ="+Id+
		
							" 	UNION ALL \n"+
		
							" 	SELECT DISTINCT N'CÓ' NO_CO, a.pk_seq id, (select SOHIEUTAIKHOAN from ERP_TAIKHOANKT WHERE PK_SEQ = "+taikhoanCO_VAT+") as SOHIEUTAIKHOAN, "+totalVAT+" as SOTIEN, \n"+
							"	   	   (CASE  WHEN  A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.TEN WHEN  A.KHACHHANG_FK  IS NOT NULL THEN KH.TEN ELSE NV.TEN END) AS DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+ i +" STT, 2 SAPXEP \n"+ 
							"  FROM ERP_MUAHANG A  " +  
							"  		LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = A.NHANVIEN_FK    " +  
							"  		LEFT JOIN ERP_NHACUNGCAP NCC ON A.NHACUNGCAP_FK = NCC.PK_SEQ   " + 
							"  		LEFT JOIN KHACHHANG KH ON A.KHACHHANG_FK = KH.PK_SEQ   " +  
							"  		INNER JOIN ERP_MUAHANG_SP D ON A.PK_SEQ = D.MUAHANG_FK   " +  
							"  		INNER JOIN ERP_NHOMCHIPHI E ON D.CHIPHI_FK = E.PK_SEQ   " +  
							"  WHERE   A.PK_SEQ ="+Id;					
						i++;
					}
					
					
				}
				rsTk.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if(laytk.trim().length()>0) laytk += " ORDER BY ID, STT, SAPXEP ";
		
		if(laytk.trim().length()<=0) 
			laytk = 
				" SELECT '' NO_CO, '' id , ''  SOHIEUTAIKHOAN, '' SOTIEN, \n"+
				"	   '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+ 
				" FROM ERP_MUAHANG "+
				" WHERE pk_seq = '"+Id+"' \n"; 
		
		//System.out.println("Câu tk1"+ laytk);
		return laytk;
	}

	public void init(String search)
	{
		String query = "";
		if(search.length() <= 0)
		{
			String query1 = "\n select a.PK_SEQ as dmhId,( select SUM(soluong) from ERP_MUAHANG_SP where MUAHANG_FK = a.PK_SEQ ) AS tongluong, a.NGAYMUA, isnull((select TEN from ERP_DONVITHUCHIEN where PK_SEQ = a.DONVITHUCHIEN_FK), '') as ten, isnull(c.TEN, '') as nccTen, c.MA as nccMa, " +
			"\n SOPO as SOCHUNGTU, " +
			"\n a.TONGTIENAVAT, a.VAT, " +
			"\n a.TONGTIENBVAT, " +
			"\n a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, isnull(a.DaInPdf, 0) as DaInPdf,  " +
			"\n ISNULL(DUYET.DUYET,0) AS DUYET, ISNULL(tt.ma, 'NA') as tiente, isnull(a.NOTE, '') as NOTE, isnull(a.ISDACHOT, 0) as ISDACHOT, " +
			"\n isnull(c.noibo, 0) as noibo, isnull(a.ISTRUONGBPDUYET,0) ISTRUONGBPDUYET, isnull(a.ISKTTDUYET,0) ISKTTDUYET,  ISNULL(a.ISCHOTGANMACP,0) ISCHOTGANMACP, " +
			"\n (select count(*) from ERP_HOADONNCC_PHIEUNHAP where MUAHANG_FK = a.pk_seq )as ktPO, isnull(a.ISTHANHTOAN, 0) ISTHANHTOAN, " +
			"\n  ISNULL(a.ISTP,0) ISTP, ISNULL(a.ISKTV,0) ISKTV, ISNULL(a.ISKTT,0) ISKTT, ISNULL(ISHOANTAT, 0) ISHOANTAT \n"+
			"\n  , ISNULL(a.sochungtu_chu+a.sochungtu_so, '') SODNTT,a.httt_fk\n"+
			"\n from erp_muahang a " +
			
			"\n inner join ERP_NHACUNGCAP c on a.NHACUNGCAP_FK = c.PK_SEQ " +
			"\n left join NHANVIEN d on a.NGUOITAO = d.PK_SEQ left join NHANVIEN e on a.NGUOISUA = e.PK_SEQ " +
			"\n left join ERP_TIENTE tt on a.tiente_fk = tt.pk_seq " +
		 
			"\n left join( " +
			"\n	SELECT 	MUAHANG_FK AS DMHID, " +
			"\n			CASE WHEN SUM(QUYETDINH) > 0 THEN " +
			"\n			(CASE WHEN " +
			"\n			( SELECT SUM(TRANGTHAI) " +
			"\n			FROM ERP_DUYETMUAHANG  " +				
			"\n			WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1) > 0 THEN 0 " +
			"\n			ELSE 1 " +
			"\n			END)	" +
			"\n			ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	END AS DUYET " + 
			"\n	FROM ERP_DUYETMUAHANG DUYETMUAHANG " +
			"\n	GROUP BY MUAHANG_FK " +
			"\n  )DUYET ON DUYET.DMHID = A.PK_SEQ " +					
			"\n where   a.congty_fk = '" + this.congtyId + "' and a.type = '1' AND A.ISDNTT = 1 AND (A.NGUOITAO = "+this.userId + " OR A.KETOANVIEN_FK = "+this.userId+" OR '100002' = "+this.userId+" OR (( "+this.userId+" in (SELECT NHANVIEN_FK FROM ERP_CHUCDANH WHERE ISKTV=1 AND TRANGTHAI=1 )"+
			"\n OR  "+this.userId+" in (SELECT NHANVIEN_FK FROM ERP_CHUCDANH WHERE ISKTT=1 AND TRANGTHAI=1 ) ) AND ISNULL(A.ISKTV,0)=1 ) )\n";	
			
			
	String query2 = 
			"\n  union all  " +
			"\n select a.PK_SEQ as dmhId,( select SUM(soluong) from ERP_MUAHANG_SP where MUAHANG_FK = a.PK_SEQ ) AS tongluong, " +
			"\n a.NGAYMUA, isnull((select TEN from ERP_DONVITHUCHIEN where PK_SEQ = a.DONVITHUCHIEN_FK), '') as ten,  " +
			"\n case when a.NHANVIEN_FK is not null then isnull(c.TEN, '') when a.khachHang_NPP_FK is NOt null then npp.TEN when a.doiTuongKhac_Fk is not null then dtk.tenDoiTuong else kh.TEN end as nccTen, " +
			"\n case when a.NHANVIEN_FK is not null then c.MA when a.khachHang_NPP_FK is NOt null then npp.Ma when a.doiTuongKhac_Fk is not null then dtk.maDoiTuong else kh.MA end as nccMa, SOPO as SOCHUNGTU, " +
			"\n a.TONGTIENAVAT, a.VAT, " +
			"\n a.TONGTIENBVAT, " +
			"\n a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, isnull(a.DaInPdf, 0) as DaInPdf,  " +
			"\n ISNULL(DUYET.DUYET,0) AS DUYET, ISNULL(tt.ma, 'NA') as tiente, isnull(a.NOTE, '') as NOTE, isnull(a.ISDACHOT, 0) as ISDACHOT, " +
			"\n  0 as noibo,  isnull(a.ISTRUONGBPDUYET,0) ISTRUONGBPDUYET, isnull(a.ISKTTDUYET,0) ISKTTDUYET, ISNULL(a.ISCHOTGANMACP,0) ISCHOTGANMACP, " +
			"\n (select count(*) from ERP_HOADONNCC_PHIEUNHAP where MUAHANG_FK = a.pk_seq )as ktPO, isnull(a.ISTHANHTOAN, 0) ISTHANHTOAN, " +
			"\n ISNULL(a.ISTP,0) ISTP, ISNULL(a.ISKTV,0) ISKTV, ISNULL(a.ISKTT,0) ISKTT, ISNULL(ISHOANTAT, 0) ISHOANTAT \n"+
			"\n  , ISNULL(a.sochungtu_chu+a.sochungtu_so, '') SODNTT,a.httt_fk \n"+
			"\n from erp_muahang a " +
		 
			"\n left join ERP_NHANVIEN c on a.NHANVIEN_FK = c.PK_SEQ " +
			"\n left join nhaPhanPhoi kh on a.KHACHHANG_FK = kh.PK_SEQ " +
			"\n left join nhaPhanPhoi npp on a.KHACHHANG_npp_FK = npp.PK_SEQ " +
			"\n left join NHANVIEN d on a.NGUOITAO = d.PK_SEQ left join NHANVIEN e on a.NGUOISUA = e.PK_SEQ " +
			"\n left join erp_doiTuongKhac dtk on dtk.pk_seq = a.doiTuongKhac_FK " +
			"\n left join ERP_TIENTE tt on a.tiente_fk = tt.pk_seq " +
		 
			"\n left join( " +
			"\n	SELECT 	MUAHANG_FK AS DMHID, " +
			"\n			CASE WHEN SUM(QUYETDINH) > 0 THEN " +
			"\n			(CASE WHEN \n" +
			"\n			( SELECT SUM(TRANGTHAI) " +
			"\n			FROM ERP_DUYETMUAHANG  " +				
			"\n			WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1) > 0 THEN 0 " +
			"\n			ELSE 1 " +
			"\n			END) " +
			"\n			ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	END AS DUYET " + 
			"\n	FROM ERP_DUYETMUAHANG DUYETMUAHANG " +
			"\n	GROUP BY MUAHANG_FK " +
			"\n  )DUYET ON DUYET.DMHID = A.PK_SEQ " +
			"\n where a.congty_fk = '" + this.congtyId + "' and a.type = '1' AND A.ISDNTT = 1 AND A.NHACUNGCAP_FK IS NULL AND (A.NGUOITAO = "+this.userId + " OR A.KETOANVIEN_FK = "+this.userId+" OR (( "+this.userId+" in (SELECT NHANVIEN_FK FROM ERP_CHUCDANH WHERE ISKTV=1 AND TRANGTHAI=1 ) \n"+					
			"\n OR  "+this.userId+" in (SELECT NHANVIEN_FK FROM ERP_CHUCDANH WHERE ISKTT=1 AND TRANGTHAI=1 ) ) AND ISNULL(A.ISKTV,0)=1  ))\n";	
	
			//" and a.DONVITHUCHIEN_FK in  " + util.quyen_donvithuchien(this.userId) ;
	
	if(tungay.length() > 0) {
		query1 += " and a.ngaymua >= '" + tungay + "'\n";
		query2+= " and a.ngaymua >= '" + tungay + "'\n";
	}
	
	if(denngay.length() > 0) {
		query1 += " and a.ngaymua <= '" + denngay + "'\n";
		query2 += " and a.ngaymua <= '" + denngay + "'\n";
	}
	
	if(dvthId.length() > 0){
		query1 += " and a.DONVITHUCHIEN_FK = '" + dvthId + "'\n";
		query2 += " and a.DONVITHUCHIEN_FK = '" + dvthId + "'\n"; 
	}
	
	if(this.loaihanghoa.length() > 0) {
		query1 += " and a.LOAIHANGHOA_FK = '" + this.loaihanghoa + "'\n";
		query2 += " and a.LOAIHANGHOA_FK = '" + this.loaihanghoa + "'\n";
	}
	
	if(this.loaisanphamid.length() > 0) {
		query2 += " and a.loaisanpham_fk = '" + this.loaisanphamid + "'\n";
		query1 += " and a.loaisanpham_fk = '" + this.loaisanphamid + "'\n";
	}
	
//	if(tusotien.length()>0){
//		query1 += " and (a.TONGTIENAVAT >= "+tusotien+" ) \n";
//		query2 += " and (a.TONGTIENAVAT >= "+tusotien+" ) \n";
//	}
//	if(densotien.length()>0){
//		query1 += " and (a.TONGTIENAVAT <= "+densotien+" ) \n";
//		query2 += " and (a.TONGTIENAVAT <= "+densotien+" ) \n";
//	}
	
	if(this.ngoaiKhoan.length() > 0) {
		query2 += " and a.ISNGOAIKHOAN = '" + this.ngoaiKhoan + "'\n";
		query1 += " and a.ISNGOAIKHOAN = '" + this.ngoaiKhoan + "'\n";
	}
	
	if(this.nccTen.length() > 0)
	{
		query1 += 
			" and (dbo.ftBoDau(c.ma) like '%" + util.replaceAEIOUN(this.nccTen) + "%' " +
			"or dbo.ftBoDau(c.ten) like N'%" + util.replaceAEIOUN(this.nccTen) + "%') \n";
		query2 += 
			" and ( (dbo.ftBoDau(c.ma) like '%" + util.replaceAEIOUN(this.nccTen) + "%' " +
			"or dbo.ftBoDau(c.ten) like N'%" + util.replaceAEIOUN(this.nccTen) + "%')  \n" + 
			"   or (dbo.ftBoDau(kh.ma) like '%" + util.replaceAEIOUN(this.nccTen) + "%' or dbo.ftBoDau(kh.ten) like N'%" + util.replaceAEIOUN(this.nccTen) + "%') \n" + 
			"   or (dbo.ftBoDau(npp.ma) like '%" + util.replaceAEIOUN(this.nccTen) + "%' or dbo.ftBoDau(npp.ten) like N'%" + util.replaceAEIOUN(this.nccTen) + "%') )\n";
	}
	System.out.println("replaceAEIOUN");
	if(this.nguotaoIds.trim().length() > 0) {
		query2 += " and a.nguoitao = '" + nguotaoIds.trim() + "' \n";
		query1 += " and a.nguoitao = '" + nguotaoIds.trim() + "' \n";
	}
	
	if(sodonmuahang.length() > 0)
	{
		query2 += " and a.soPO like '%" + sodonmuahang + "%' \n";
		query1 += " and a.soPO like '%" + sodonmuahang + "%' \n";
	}
	if(soChungTu.length() > 0)
	{
		query2 += " and ISNULL(a.sochungtu_chu+a.sochungtu_so, '') like '%" + soChungTu + "%' \n";
		query1 += " and ISNULL(a.sochungtu_chu+a.sochungtu_so, '')  like '%" + soChungTu + "%' \n";
	}
	if(maChungTu.length() > 0)
	{
		query2 += " and CONVERT(VARCHAR, a.PK_SEQ) like '%" + maChungTu + "%' \n";
		query1 += " and CONVERT(VARCHAR, a.PK_SEQ)  like '%" + maChungTu + "%' \n";
	}
	if(tongtien.length() > 0)
	{
		query2 += " and a.TONGTIENAVAT = " + tongtien + "\n";
		query1 += " and a.TONGTIENAVAT = " + tongtien + "\n";
	}
//	if(this.nguoidenghithanhtoan.trim().length() > 0) {
//		query1 += " and a.NguoiDeNghi = " + this.nguoidenghithanhtoan + "\n";
//		query2 += " and a.NguoiDeNghi = " + this.nguoidenghithanhtoan + "\n";
//	}
	if(trangthai.trim().length() > 0)
	{
		if(trangthai.equals("0")) // Chưa chốt
		{
			query2 += " and a.TRANGTHAI = 0 AND ISNULL(a.ISDACHOT,0) = 0 \n";
			query1 += " and a.TRANGTHAI = 0 AND ISNULL(a.ISDACHOT,0) = 0 \n";
		}
		else if(trangthai.equals("-1")) // Chưa duyệt = đã chốt
		{
			query2 += " and a.TRANGTHAI = 0 AND ISNULL(a.ISDACHOT,0) = 1 AND ISNULL(a.ISTP,0) = 0 AND ISNULL(a.ISKTV,0) = 0 AND ISNULL(a.ISKTT,0) = 0 \n";
			query1 += " and a.TRANGTHAI = 0 AND ISNULL(a.ISDACHOT,0) = 1 AND ISNULL(a.ISTP,0) = 0 AND ISNULL(a.ISKTV,0) = 0  AND ISNULL(a.ISKTT,0) = 0 \n";
		}
		else if(trangthai.equals("-2")) // Đã duyệt (Trưởng BP)
		{
			query2 += " and a.TRANGTHAI = 0 AND ISNULL(a.ISTP,0) = 1 AND ISNULL(a.ISKTV,0) = 0 AND ISNULL(a.ISKTT,0) = 0 \n";
			query1 += " and a.TRANGTHAI = 0 AND ISNULL(a.ISTP,0) = 1 AND ISNULL(a.ISKTV,0) = 0 AND ISNULL(a.ISKTT,0) = 0 \n";
		}
		else if(trangthai.equals("-3")) // Đã duyệt (KTT)
		{
			query2 += " and a.TRANGTHAI = 0 AND ISNULL(a.ISKTT,0) = 1 \n";
			query1 += " and a.TRANGTHAI = 0 AND ISNULL(a.ISKTT,0) = 1 \n";
		}
		else if(trangthai.equals("1"))  // Hoàn tất
		{
			query2 += " and a.TRANGTHAI = 1 \n";
			query1 += " and a.TRANGTHAI = 1 \n";
		}
		else if(trangthai.equals("3"))  // Đã hủy
		{
			query2 += " and a.TRANGTHAI = 3 \n";
			query1 += " and a.TRANGTHAI = 3 \n";
		}
		else if(trangthai.equals("5"))//Hoàn thanh toán
		{
			query2 += " and isnull(a.isthanhtoan,0) = 1 \n";
			query1 += " and isnull(a.isthanhtoan,0) = 1 \n";
		}
		else  // Đã xóa!!!
		{
			query2 += " and a.TRANGTHAI = 2 \n";
			query1 += " and a.TRANGTHAI = 2 \n";
		}
	}

	if(mactsp.trim().length() > 0)
	{
		query2 +=" and a.pk_seq in (\n" +
				"     select distinct muahang_fk from erp_muahang_sp where sanpham_fk in ( select distinct pk_seq from erp_Sanpham where MA like N'%" + mactsp + "%' ) \n" +
				" ) \n";
	}

	if(this.task.length() > 0)
			query2 += "\n and a.trangthai = 1 \n";
	query = query1 + query2;
	}
		else
			query = search;
	
		 
		System.out.println("Câu init: \n" + query + "\n------------------------------------------------");
		this.dmhRs = createSplittingDataNew(db,50, 10, "NGAYMUA desc, dmhId desc", query);
//		String sql1 = createSplittingData_ListNew(this.db, 40, 10, "NGAYMUA desc, dmhId desc", query);
		
//		this.dmhRs = db.get(sql1);		
				 
		query = "select pk_seq, ten from ERP_DONVITHUCHIEN ";
		this.dvthRs = db.get(query);
		this.loaisanphamRs=db.get("select pk_seq, ten from ERP_LOAISANPHAM where trangthai = '1' ");
		
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_MUAHANG ) ";
		this.nguoitaoRs = db.get(query);
	}

	
	public void DBclose() 
	{
		try 
		{
			if(this.dvthRs != null) 
				this.dvthRs.close();
			
			if(this.dmhRs != null) 
				this.dmhRs.close(); 
			
			if(this.nccRs != null) 
				this.nccRs.close(); 
			
			if(this.nspRs != null) 
				this.nspRs.close(); 
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		this.db.shutDown();
		this.db = null;
	}
	public void NewDbUtil()
	{
		  if(this.db == null )
		  {
			   this.db  = new dbutils();
		  }
	}

	public String getTask()
	{
		return this.task;
	}

	public void setTask(String task)
	{
		this.task = task;
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
		ResultSet rs = db.get("select count(*) as c from ERP_MUAHANG");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public ResultSet getNccRs() 
	{
		return this.nccRs;
	}

	public void setNccRs(ResultSet nccRs) 
	{
		this.nccRs = nccRs;
	}

	public void setNccIds(String nccIds) 
	{
		this.nccIds = nccIds;
	}

	public String getNccIds() 
	{
		return this.nccIds;
	}

	public ResultSet getNspRs() 
	{
		return this.nspRs;
	}

	public void setNspRs(ResultSet nspRs) 
	{
		this.nspRs = nspRs;
	}

	public void setNspIds(String nspIds)
	{
		this.nspIds = nspIds;
	}

	public String getNspIds()
	{
		return this.nspIds;
	}

	public void initBaoCao() 
	{
		this.dvthRs = db.get("select pk_seq, ten from ERP_DONVITHUCHIEN where pk_seq in  "+ util.quyen_donvithuchien(this.userId));
		this.nccRs = db.get("select pk_seq, ma as nccMa, ten as nccTen from erp_nhacungcap");
		this.nspRs = db.get("select PK_SEQ, TEN, DIENGIAI from NHOMSANPHAM where loainhom = '1'");
	}

	public String getSodonmuahang()
	{
		return this.sodonmuahang;
	}

	public void setSodonmuahang(String sodonmuahang) 
	{
		this.sodonmuahang = sodonmuahang;
	}

	
	public ResultSet getLoaisanpham() 
	{
		
		return this.loaisanphamRs;
	}

	
	public void setLoaisanpham(ResultSet loaisanpham) 
	{
		
		this.loaisanphamRs=loaisanpham;
	}

	
	public String getLoaisanphamid() {
		
		return this.loaisanphamid;
	}

	
	public void setLoaisanphamid(String loaisanpham) {
		
		this.loaisanphamid=loaisanpham;
	}

	public String getIsdontrahang() 
	{
		return this.isdontrahang;
	}

	public void setIsdontrahang(String dontrahang) 
	{
		this.isdontrahang = dontrahang;
	}

	public String getCongtyId()
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getCtyId() {
		
		return null;
	}

	
	public void setCtyId(String ctyId) {
		
		
	}

	
	public String getCty() {
		
		return null;
	}

	
	public void setCty(String cty) {
		
		
	}

	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		
		this.trangthai = trangthai;
	}

	
	public ResultSet getNguoitaoRs() {
		
		return this.nguoitaoRs;
	}

	
	public void setNguoitaoRs(ResultSet nguoitaoRs) {
		
		this.nguoitaoRs = nguoitaoRs;
	}

	
	public void setNguoitaoIds(String nspIds) {
		
		this.nguotaoIds = nspIds;
	}

	
	public String getNguoitaoIds() {
		
		return this.nguotaoIds;
	}

	
	public String getMaCtSp() {
		return this.mactsp;
	}

	
	public void setMaCtSp(String mact) {
		this.mactsp = mact;
	}

	
	public String getLoaihanghoa() {
		return this.loaihanghoa;
	}

	
	public void setLoaihanghoa(String loaihh) {
		this.loaihanghoa = loaihh;
	}

	
	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
	}

	
	public String Chotmuahang(String dmhId) {
		 		
		try
		{
			db = new dbutils();
						
			db.getConnection().setAutoCommit(false);
			
			
			String query = "update ERP_MUAHANG SET ISDACHOT = 1, NGUOISUA = '"+this.userId+"', NGUOICHOT_FK = "+this.userId+" where pk_seq = '" + dmhId + "'" ;
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể chốt đơn mua hàng này: " + query;
			}
			
		/*	
			// KIỂM TRA LOẠI CẤP CỦA NHÂN VIÊN NÀY TRONG DUYET MUA HÀNG
			
			query = "SELECT loaicap_fk FROM ERP_DUYETMUAHANG WHERE MUAHANG_FK = "+dmhId + " AND NHANVIEN_FK = "+userId;
			ResultSet rs = db.get(query);
			
			String loaicap_fk = "";
			rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					loaicap_fk = rs.getString("loaicap_fk");
					rs.close();
				}
			}
						
			if(loaicap_fk.equals("10000")) // CẤP QUẢN LÝ TRỰC TIẾP
			{
				query = " UPDATE ERP_MUAHANG SET ISQLTT = 1 WHERE PK_SEQ = "+dmhId;	
				
				System.out.println("3.quanlycap : " + query);
				
				if (!db.update(query))
				{
					msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return msg;
				}
				
			}
			else if(loaicap_fk.equals("10001")) // QUẢN LÝ CS
			{
				query = " UPDATE ERP_MUAHANG SET ISCS = 1 WHERE PK_SEQ = "+dmhId;		
				
				System.out.println("4.quanlycap : " + query);
				
				if (!db.update(query))
				{
					msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			else if(loaicap_fk.equals("10002")) // QUẢN LÝ DUYET ĐNTT/ĐNTU
			{
				query = " UPDATE ERP_MUAHANG SET ISDUYETCHI = 1 WHERE PK_SEQ = "+dmhId;		
				
				System.out.println("5.quanlycap : " + query);
				
				if (!db.update(query))
				{
					msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			else if(loaicap_fk.equals("10003")) // QUẢN LÝ KTTH
			{
				query = " UPDATE ERP_MUAHANG SET ISKTTH = 1 WHERE PK_SEQ = "+dmhId;	
				
				System.out.println("6.quanlycap : " + query);
				
				if (!db.update(query))
				{
					msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			else if(loaicap_fk.equals("10004")) // KẾ TOÁN TRƯỞNG
			{
				query = " UPDATE ERP_MUAHANG SET ISKTT = 1, TRANGTHAI = 1, isDaChi = 1 WHERE PK_SEQ = "+dmhId;		
				
				System.out.println("7.quanlycap : " + query);
				
				if (!db.update(query))
				{
					msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return msg;
				}
				
			}				
			
			// QUẢN LÝ CS || DUYỆT ĐNTT / ĐNTU || KẾ TOÁN TỔNG HỢP || KẾ TOÁN TRƯỞNG => BẮT BUỘC PHẢI KIỂM TRA ĐỊNH KHOẢN
			if(loaicap_fk.equals("10004") || loaicap_fk.equals("10001")|| loaicap_fk.equals("10002")|| loaicap_fk.equals("10003")){
				// KIỂM TRA ĐỊNH KHOẢN ĐÚNG HAY KHÔNG
				
				// chạy định khoản
				
				String taikhoanCO_DS = "";
				String taikhoanNO_DS = "";
				
				String taikhoanCO_VAT = "";
				String taikhoanNO_VAT = "";
				
//				String loaidoituongCO = "";
//				String madoituongCO = "";
//				
//				String loaidoituongNO = "";
//				String madoituongNO = "";
				query= "  SELECT	N'CHI PHÍ ' AS LOAIDOITUONGNO, E.PK_SEQ AS MADOITUONGNO,    " +  
					   "  CASE  WHEN A.NHACUNGCAP_FK  IS NOT NULL THEN N'NHÀ CUNG CẤP'  WHEN A.NHANVIEN_FK  IS NOT NULL THEN N'NHÂN VIÊN' ELSE N'KHÁCH HÀNG' END AS LOAIDOITUONGCO,   " +  
					   "  CASE  WHEN  A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.PK_SEQ WHEN A.NHANVIEN_FK  IS NOT NULL THEN NV.PK_SEQ ELSE KH.PK_SEQ END AS MADOITUONGCO, " +  
					   "  A.NGAYMUA AS NGAYHOADON, (D.SOLUONG* D.DONGIA)  AS DOANHSO,(D.SOLUONG* D.DONGIA)*  D.PHANTRAMTHUE/100 AS THUE   ,    " +  
					   "  CASE  WHEN A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.TAIKHOAN_FK WHEN A.NHANVIEN_FK  IS NOT NULL THEN NV.TAIKHOAN_FK ELSE KH.TAIKHOAN_FK END AS TAIKHOANCO_DS,   " +  
					   "  CASE  WHEN A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.TAIKHOAN_FK WHEN A.NHANVIEN_FK  IS NOT NULL THEN NV.TAIKHOAN_FK ELSE KH.TAIKHOAN_FK  END AS TAIKHOANCO_VAT,   " +  
					   "  (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = E.TAIKHOAN_FK AND CONGTY_FK = A.CONGTY_FK) AS TAIKHOANNO_DS,   " +  
					   "  ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13310000' AND CONGTY_FK = A.CONGTY_FK  ) AS TAIKHOANNO_VAT   " +  
					   "  FROM ERP_MUAHANG A  " +  
					   "  LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = A.NHANVIEN_FK    " +  
					   "  LEFT JOIN ERP_NHACUNGCAP NCC ON A.NHACUNGCAP_FK = NCC.PK_SEQ   " + 
					   "  LEFT JOIN KHACHHANG KH ON A.KHACHHANG_FK = KH.PK_SEQ   " +  
					   "  LEFT JOIN ERP_MUAHANG_SP D ON A.PK_SEQ = D.MUAHANG_FK   " +  
					   "  LEFT JOIN ERP_NHOMCHIPHI E ON D.CHIPHI_FK = E.PK_SEQ   " +  
					   "  WHERE   A.PK_SEQ ="+dmhId;
				
				System.out.println("Câu tk1"+ query);
				ResultSet rsTk = db.get(query);
				if(rsTk!= null)
				{
					while(rsTk.next())
					{
						
						double totalDS = Math.round(rsTk.getDouble("DOANHSO"));
						double totalVAT = Math.round(rsTk.getDouble("THUE"));
						
						taikhoanCO_DS = rsTk.getString("taikhoanCO_DS") == null ? "": rsTk.getString("taikhoanCO_DS") ;
						taikhoanNO_DS = rsTk.getString("taikhoanNO_DS") == null ? "": rsTk.getString("taikhoanNO_DS")  ;
						
						taikhoanCO_VAT = rsTk.getString("taikhoanCO_VAT") == null ? "": rsTk.getString("taikhoanCO_VAT")  ;
						taikhoanNO_VAT = rsTk.getString("taikhoanNO_VAT") == null ? "": rsTk.getString("taikhoanNO_VAT") ;
						
//						loaidoituongCO = rsTk.getString("loaidoituongCO");
//						madoituongCO = rsTk.getString("madoituongCO");
//						loaidoituongNO = rsTk.getString("loaidoituongNO");
//						madoituongNO = rsTk.getString("madoituongNO");
	
//						String ngayghinhan = rsTk.getString("ngayhoadon");
//						String nam = ngayghinhan.substring(0, 4);
//						String thang = ngayghinhan.substring(5, 7);
//						
//						String tiente_fk = "100000";					
					
						if(totalDS > 0)
						{
						
							if(taikhoanCO_DS.trim().length() <= 0 || taikhoanNO_DS.trim().length() <= 0 )
							{
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin mã chi phí trước khi chốt.";
								db.getConnection().rollback();
							 
								return msg;
							}
						}
						
						if(totalVAT > 0)
						{
							if(taikhoanCO_VAT.trim().length() <= 0 || taikhoanNO_VAT.trim().length() <= 0 )
							{
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin mã chi phí trước khi chốt.";
								db.getConnection().rollback();
								return msg;
							}	
							
						}
						
					}
					rsTk.close();
				}
				
				if(loaicap_fk.equals("10004")) // KẾ TOÁN TRƯỞNG TẠO THÌ TỰ RA PHIẾU CHI || UNC
				{
					//TỰ TẠO PHIẾU CHI || ỦY NHIỆM CHI
					
					query = "\n SELECT MH.NGAYMUA, MH.NHACUNGCAP_FK, MH.NHANVIEN_FK, MH.KHACHHANG_FK, MH.TONGTIENAVAT, MH.TIENTE_FK, MH.HTTT_FK HTTTID, MH.SOPO, MH.NGUOITAO "+
							"\n FROM ERP_MUAHANG MH "+
							"\n LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ =  MH.NHACUNGCAP_FK "+
							"\n LEFT JOIN ERP_NHANVIEN NV ON MH.NHANVIEN_FK = NV.PK_SEQ "+
							"\n LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = MH.KHACHHANG_FK "+
							"\n WHERE MH.PK_SEQ = "+dmhId+" ";
					
					System.out.println(query);
					ResultSet RsDNTT = db.get(query);
					
//					String ngaytamung = "";
					String ncc_fk = "";
					String nhanvienfk = "";
					String khachhang_fk = "";
					double sotienavat = 0;
					String htttId = "";
					String tiente_fk = "";
//					String PO = "";
					String nguoitao = "";
					
					if(RsDNTT!=null)
					{
						try 
						{
							while (RsDNTT.next())
							{
//								ngaytamung = RsDNTT.getString("NGAYMUA");
								ncc_fk = RsDNTT.getString("NHACUNGCAP_FK");
								nhanvienfk =  RsDNTT.getString("NHANVIEN_FK");
								khachhang_fk = RsDNTT.getString("KHACHHANG_FK");
								sotienavat = RsDNTT.getDouble("TONGTIENAVAT");
								htttId = RsDNTT.getString("HTTTID");
								tiente_fk = RsDNTT.getString("tiente_fk");
//								PO = RsDNTT.getString("SOPO");
								nguoitao = RsDNTT.getString("NGUOITAO");
							}
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
					}
									
					String prefix = "";
					if(htttId.equals("100000"))
						prefix = "DNPC";
					
					if(htttId.equals("100001"))
						prefix = "DNBN";
					
					query = 
						" Insert ERP_THANHTOANHOADON " +
						"( DVTH_FK, NGAYGHINHAN, NCC_FK ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, " +
						"  SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, " +
						"  TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, CHINHANH_TP_FK, " +
						"  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA , THANHTOANTUTIENVAY, KHACHHANG_FK, CTKEMTHEO, CONGTY_FK, PREFIX " +
						") " +
						"values(NULL, '" + getDateTime() + "', " + ncc_fk + "," + " "+nhanvienfk +", '" + htttId + "', " +
						" NULL, NULL , NULL , N'Thanh toán cho đề nghị thanh toán số "+dmhId+"', " +
						"" + sotienavat + ", "+ sotienavat + ", " + sotienavat  + ", " + sotienavat + " , " +
						" 0 , 0 , 0 ,0, 0, 0, '', null , null , '"  + getDateTime() + "', '" + nguoitao + "', '" + getDateTime() + "', '" 
						+ nguoitao + "',0, " + tiente_fk + ", 1 , '0', "+khachhang_fk+" , N'', "+this.congtyId+", '"+prefix+ "' )";
									
					System.out.println(query);
					
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi Phiếu Chi/UNC: " + query;
						System.out.println(this.msg);
						db.getConnection().rollback();
						return msg;
					}
					
					query = "select IDENT_CURRENT('ERP_THANHTOANHOADON') as tthdId";
					
					ResultSet rsTthd = db.get(query);	
					String tthdCurrent = "";
					if(rsTthd.next())
					{
						tthdCurrent = rsTthd.getString("tthdId");
						rsTthd.close();
					}
					
					// CẬP NHẬT MÃ CHỨNG TỪ
					query = " update ERP_THANHTOANHOADON set machungtu =  Prefix + SUBSTRING(NGAYGHINHAN, 6, 2) + SUBSTRING(NGAYGHINHAN, 0, 5) + '-' + dbo.LaySoChungTu( " + tthdCurrent + " ) " + 
							" where pk_seq = '" + tthdCurrent + "' ";
					
					System.out.println("[ERP_THANHTOANHOADON] error message:" + query);
					
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_THUTIEN: " + query;
						System.out.println("[ErpThutien.createTTHD] error message:" + this.msg);
						db.getConnection().rollback();
						return msg;
					}
					
					//TRONG BẢNG ERP_THANHTOANHOADON_HOADON LOAIHD = 6 LÀ ĐỂ NGHỊ THANH TOÁN
					
					query = "Insert ERP_THANHTOANHOADON_HOADON( THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON ) " +
							"values('" + tthdCurrent + "', '" + dmhId + "', '" + sotienavat + "', '" + sotienavat + "'," +
							" 0, 0 , '6', '"+ dmhId +"')";			
										
					System.out.println(query);
					
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
						System.out.println(this.msg);
						db.getConnection().rollback();
						return msg;
					}
				}
				
			}*/			
						
			db.getConnection().commit();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return msg;
		}
		
		return msg;
	}
	
	
	public String Unchotmuahang(String dmhId) {
		try {
			String ngaysua = getDateTime();
			UtilityKeToan util = new UtilityKeToan();

			db.getConnection().setAutoCommit(false);
			boolean resultUpdate = Kiemtragiatri(dmhId);
			if (resultUpdate == false)
			{
				db.getConnection().rollback();
				if (this.msg.trim().length() == 0)
					this.msg = "Không thể mở chốt";
				
				return msg;
			}
			 String query = "update ERP_MUAHANG SET ISKTT =0,ISKTV=0,TRANGTHAI=0,ISDACHI=0,NGUOISUA = '"+this.userId+"', NGUOIMO_FK = "+this.userId+",NGAYMO=getdate() where pk_seq = '" + dmhId + "'" ;
			System.out.println("1.Cap nhat ERP_MUAHANG: " + query);

			if (!db.update(query)) 
			{
				this.msg = "CTTHD1.1 Khong the xoa ERP_MUAHANG: " + query;
				db.getConnection().rollback();
				return "Không thể mở chốt đơn mua hàng này: " + query;
			}

			
			query = "select month(NGAYMUA) thang, year(NGAYMUA) nam,congty_fk,npp_fk from ERP_MUAHANG WHERE PK_SEQ = " + dmhId + "";
			System.out.println("ngay thang " +query);
			ResultSet rs = this.db.get(query);
			if (rs != null)
			{
				if (rs.next())
				{
					util.setThang(rs.getString("thang"));
					util.setNam(rs.getString("nam"));
				}
				rs.close();
			}
			String loaiChungTu="N'Duyệt đề nghị thanh toán',N'Thanh toán tạm ứng'";
			this.msg=util.HuyUpdate_TaiKhoan(db, dmhId, loaiChungTu);
			if(this.msg.length()>0)
			{
				db.getConnection().rollback();
				return "Không thể xóa định khoản";
			}
			
			  query = "delete ERP_MASCLON_DIEUCHINH where sochungtu= '"+dmhId+"' and bangthamchieu=N'ERP_MUAHANG' and masclon_fk in (select pk_seq from erp_masclon where trangthai!=3)" ;
				System.out.println("1.Cap nhat ERP_MASCLON_DIEUCHINH: " + query);
				

				if (!db.update(query)) 
				{
					this.msg = "CTTHD1.2 Khong the xoa ERP_MASCLON_DIEUCHINH: " + query;
					db.getConnection().rollback();
					return query;
				}
				
				  query = "delete erp_congcudungcu_dieuchinh where sochungtu= '"+dmhId+"' and bangthamchieu=N'ERP_MUAHANG'" ;
				  if (!db.update(query)) 
					{
						this.msg = "CTTHD1.2 Khong the xoa ERP_MASCLON_DIEUCHINH: " + query;
						db.getConnection().rollback();
						return query;
					}

				  query = "update erp_congcudungcu set dongia=0,thanhtien=0,trangthai=0 where pk_seq in (select CPTRATRUOC_FK from ERP_MUAHANG_SP where muahang_fk="+dmhId+")";
				  if (!db.update(query)) 
					{
						this.msg = "CTTHD1.2 Khong the xoa ERP_MASCLON_DIEUCHINH: " + query;
						db.getConnection().rollback();
						return query;
					}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return "Không thể mở chốt";
		}

		return "";
		
		
	}
	
	
	private boolean Kiemtragiatri(String Id) {
		try
		{
		String 	 MASCLON="";
		String query="SELECT MASCLON_FK FROM ERP_MASCLON_DIEUCHINH WHERE SOCHUNGTU IN ( SELECT PK_SEQ FROM ERP_MUAHANG WHERE PK_SEQ= "+Id+" ) AND BANGTHAMCHIEU='ERP_MUAHANG'" ;
		ResultSet rs= db.get(query);
		while (rs.next())
		{
			MASCLON = rs.getString("MASCLON_FK");
			int trangthai=0;
			query="SELECT TRANGTHAI  FROM  ERP_MASCLON WHERE PK_SEQ ="+MASCLON+" ";
			ResultSet rsKH= db.get(query);
			if(rsKH.next())
			{
				trangthai= rsKH.getInt("TRANGTHAI");
			}rsKH.close();
			if(trangthai==3 )
			{
				msg =  "2.Mã XDCB/ SC Lớn đã được kết chuyển, không thể mở chốt ";
				return false;
			}
		}
		rs.close();
		
		String CCDC_FK="";
				query="SELECT CCDC_FK FROM ERP_CONGCUDUNGCU_DIEUCHINH WHERE SOCHUNGTU IN ( SELECT PK_SEQ FROM ERP_MUAHANG WHERE PK_SEQ= "+Id+" ) AND BANGTHAMCHIEU='ERP_MUAHANG'" ;
				 rs= db.get(query);
				while (rs.next())
				{
					CCDC_FK = rs.getString("CCDC_FK");
					int kh=0;
					query="SELECT COUNT (KHAUHAO)  AS NUM FROM  ERP_KHAUHAOCCDC WHERE CCDC_FK ="+CCDC_FK+" ";
					ResultSet rsKH= db.get(query);
					if(rsKH.next())
					{
						kh= rsKH.getInt("NUM");
					}rsKH.close();
					if(kh>0 )
					{
						msg =  "2.Chi phí trả trước đã được phân bổ, không thể mở chốt: ";
						return false;
					}
				}
				rs.close();
				
				
				String CPTRATRUOC_FK="";
				query="SELECT CPTRATRUOC_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK IN ( SELECT PK_SEQ FROM ERP_MUAHANG WHERE PK_SEQ= "+Id+" )" ;
				 rs= db.get(query);
				while (rs.next())
				{
					CPTRATRUOC_FK = rs.getString("CPTRATRUOC_FK");
					int kh=0;
					query="SELECT COUNT (KHAUHAO)  AS NUM FROM  ERP_KHAUHAOCCDC WHERE CCDC_FK ="+CPTRATRUOC_FK+" ";
					ResultSet rsKH= db.get(query);
					if(rsKH.next())
					{
						kh= rsKH.getInt("NUM");
					}rsKH.close();
					if(kh>0 )
					{
						msg =  "2.Chi phí trả trước đã được phân bổ, không thể mở chốt: ";
						return false;
					}
				}
				rs.close();
				
				
		}
	
		catch (Exception e) {
			this.msg="Lỗi khi chốt. Vui lòng liên hệ admin để xử lý";
			return false;
		}
		return true;
	}
	
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getNgoaiKhoan() {
		return ngoaiKhoan;
	}

	public void setNgoaiKhoan(String ngoaiKhoan) {
		this.ngoaiKhoan = ngoaiKhoan;
	}
}
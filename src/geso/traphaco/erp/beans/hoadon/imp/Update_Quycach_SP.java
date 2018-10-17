package geso.traphaco.erp.beans.hoadon.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class Update_Quycach_SP 
{
	private static final long serialVersionUID = 1L;
	
	dbutils db;
	Utility util=new Utility();
			
	public static void main ( String args [  ]  )   {
		dbutils db = new dbutils();
		String query = " select * from ERP_SANPHAM where DVKD_FK=100004 ";
		ResultSet rs = db.get(query);
		NumberFormat format = new DecimalFormat("#,###,###.###");
		
		try {
			while(rs.next()) {
				String qc = "";
				double fDai = rs.getFloat("Dai"), fRong = rs.getFloat("RONG"), fDinhLuong = rs.getFloat("DinhLuong"), fTrongluong = rs.getFloat("TrongLuong"), fDuongKinhTrong = rs.getFloat("DuongKinhTrong"), fDoDay = rs.getFloat("DoDay"), fDauLon = rs.getFloat("DauLon"), fDauNho = rs.getFloat("DauNho"), fDaiDay = rs.getFloat("DaiDay");
				String sDai = format.format(fDai), sRong = format.format(fRong), sDinhluong = format.format(fDinhLuong), sTrongluong = format.format(fTrongluong), sDuongKinhTrong = format.format(fDuongKinhTrong), sDoDay = format.format(fDoDay), sDauLon = format.format(fDauLon), sDauNho = format.format(fDauNho), sDaiDay = format.format(fDaiDay);
				String mau = rs.getString("MAU").trim(); if(mau.toUpperCase().indexOf("NO") >= 0 || mau.toUpperCase().indexOf("KHONG") >= 0 || mau.toUpperCase().indexOf("KHÔNG") >= 0) mau = "";
				String mauin = rs.getString("MAUIN").trim(); if(mauin.toUpperCase().indexOf("NO") >= 0 || mauin.toUpperCase().indexOf("KHONG") >= 0 || mauin.toUpperCase().indexOf("KHÔNG") >= 0) mauin = "";
				
				//Lõi: Đường kính trong x Dài x Độ Dày
				if(fDuongKinhTrong > 0) { qc += "" + sDuongKinhTrong + rs.getString("DVDL_DKTRONG"); }
				if(fDauLon > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDauLon + rs.getString("DVDL_DAULON"); }
				if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai + rs.getString("DVDL_DAI"); }
				if(fDauNho > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDauNho + rs.getString("DVDL_DAUNHO"); }
				if(fDoDay > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDoDay + rs.getString("DVDL_DODAY"); }
				if(mauin.length() > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + mauin; }
				if(mau.length() > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + mau; }
				
				String sql = "UPDATE ERP_SANPHAM SET QUYCACH = N'"+qc+"' WHERE PK_SEQ = " + rs.getString("PK_SEQ") ;
				db.update(sql);
				db.shutDown();
			}
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

}

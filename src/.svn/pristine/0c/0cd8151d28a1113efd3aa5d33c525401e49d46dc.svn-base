package app;

import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;

public class MochotPGH {
	public static void main(String[] args) 
	{
		dbutils db = new dbutils();
		// Mở chốt phiếu giao hàng
		try 
		{
			db.getConnection().setAutoCommit(false);
			// thay câu truy vấn bằng số phiếu hoặc hóa đơn
			String query = "select pk_seq, npp_fk from ERP_YCXUATKHONPP where pk_seq in (  110610 ) ";
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				String pghId = rs.getString("pk_seq");
				String nppId = rs.getString("npp_fk");
				
				//Mở lại trạng thái PHIẾU GIAO HÀNG ( làm trươc trường hợp đơn giản, thực giao = xuát, chưa có nhận hàng )
				query =  "update kho set kho.SOLUONG = kho.SOLUONG + CT.tongxuat,  " + 
						 "			   kho.BOOKED = kho.BOOKED + CT.tongxuat  " + 
						 "from  " + 
						 "(  " + 
						 "	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, b.solo, b.ngayhethan, SUM(b.soluong) as tongxuat   " + 
						 "	from ERP_YCXUATKHONPP a  " + 
						 "	inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk  " + 
						 "	where ycxk_fk = '" + pghId + "'  " + 
						 "	group by a.kho_fk, a.nhomkenh_fk, b.solo, b.ngayhethan, b.sanpham_fk  " + 
						 ")  " + 
						 "CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK   " + 
						 "and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO  " + 
						 "and CT.ngayhethan = kho.ngayhethan and CT.nhomkenh_fk = kho.nhomkenh_fk and kho.NPP_FK = '" + nppId + "'  ";
				if(!db.update(query))
				{
					System.out.println("::: 1.LOI TAI DON: " + pghId + " : " + query);
					db.getConnection().rollback();
					return;
				}
				
				query =  "update kho set kho.SOLUONG = kho.SOLUONG + CT.tongxuat,  " + 
						 "			   kho.BOOKED = kho.BOOKED + CT.tongxuat  " + 
						 "from  " + 
						 "(  " + 
						 "	select a.kho_fk, a.nhomkenh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat   " + 
						 "	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk  " + 
						 "	where ycxk_fk = '" + pghId + "'  " + 
						 "	group by a.kho_fk, a.nhomkenh_fk, b.sanpham_fk  " + 
						 ")  " + 
						 "CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK   " + 
						 "	and CT.sanpham_fk = kho.SANPHAM_FK and CT.nhomkenh_fk = kho.nhomkenh_fk and kho.NPP_FK = '" + nppId + "' ";
				if(!db.update(query))
				{
					System.out.println("::: 2.LOI TAI DON: " + pghId + " : " + query);
					db.getConnection().rollback();
					return;
				}
				
				query =  "update  ERP_YCXUATKHONPP set TRANGTHAI = 0 where PK_SEQ = '" + pghId + "' ";
				if(!db.update(query))
				{
					System.out.println("::: 3.LOI TAI DON: " + pghId + " : " + query);
					db.getConnection().rollback();
					return;
				}
				
			}
			rs.close();
			
			db.getConnection().commit();
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			
			System.out.println("::: CHAY LOI.... ");
			return;
		}
		
		System.out.println("::: CHAY XONG.... ");
		
	}

}

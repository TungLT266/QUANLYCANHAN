package app;

import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;

public class CapnhatNgaygiaoPGH {
	public static void main(String[] args) {
		 
		try
		{
			dbutils db = new dbutils();
			db.getConnection().setAutoCommit(false);
			
			ResultSet rs;
			String query = "select PK_SEQ, NgayDonHang from ERP_DONDATHANGNPP";

			rs = db.get(query);
			while(rs.next())
			{
				String ddh_fk = rs.getString("pk_seq");
				String ngaydonhang = rs.getString("ngaydonhang");
				
				query = "update ERP_YCXUATKHONPP set ngayyeucau = '"+ngaydonhang+"' where ddh_fk = '"+ddh_fk+"' and trangthai != 2";
				System.out.println(query);
				if (!db.update(query)) {
					System.out.println("loi update ERP_YCXUATKHONPP: "+query);
					db.getConnection().rollback();
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
			System.out.println("Hoan tat!");
		}
		catch(Exception ex)
		{
			System.out.println("Vui Long Kiem Tra Lai Du Lieu .Xay Ra Loi Sau :" + ex.toString());
		}
	}
}

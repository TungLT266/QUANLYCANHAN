package app;

import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;

public class CapnhatthuexuatDMH {
	public static void main(String[] args) {
		try
		{
			dbutils db = new dbutils();
			db.getConnection().setAutoCommit(false);
			
			ResultSet rs;
			String query = "select pk_seq, vat from erp_muahang";
			
			rs = db.get(query);
			while(rs.next())
			{
				String mhid = rs.getString("pk_seq");
				String vat = rs.getString("vat");
				query = "update erp_muahang_sp set thuexuat = '"+vat+"' where muahang_fk = '"+mhid+"'";
				System.out.println(query);
				if (!db.update(query)) {
					System.out.println("loi update erp_muahang_sp: "+query);
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

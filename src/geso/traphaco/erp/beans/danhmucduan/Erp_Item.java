package geso.traphaco.erp.beans.danhmucduan;

import geso.traphaco.center.db.sql.Idbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Erp_Item
{
	private String value;
	private String name;
	private String difField;
	public Erp_Item()
	{
		this.value = "";
		this.name = "";
		this.difField = "";
	}

	public Erp_Item(String value, String name)
	{
		this.value = value;
		this.name = name;
		this.difField = ""; 
	}
	
	public static String getListFromQuery(Idbutils db, String query, List<Erp_Item> list)
	{
		String msg = "";
		
		if (list == null)
			list = new ArrayList<Erp_Item>();
		else
			list.clear();	

		ResultSet rs = null;
		
		try {
			rs = db.get(query);
			
			if (rs != null)
				while (rs.next())
				{
					Erp_Item item = new Erp_Item(rs.getString("PK_SEQ"), rs.getString("TEN"));
					list.add(item);
				}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "GLFQ1.0 Lỗi đổ list";
		}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return msg;
	}

	public static String getListFromQuery(Idbutils db, String query, List<Erp_Item> list, boolean difFeild)
	{
		String msg = "";
		
		if (list == null)
			list = new ArrayList<Erp_Item>();
		else
			list.clear();	

		ResultSet rs = null;
		
		try {
			rs = db.get(query);
			
			if (rs != null)
				while (rs.next())
				{
					Erp_Item item = new Erp_Item(rs.getString("PK_SEQ"), rs.getString("TEN"));
					if (difFeild == true)
						item.setDifField(rs.getString("difField"));
					list.add(item);
				}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "GLFQD1.0 Lỗi đổ list";
		}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return msg;
	}
	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDifField(String difField) {
		this.difField = difField;
	}

	public String getDifField() {
		return difField;
	}
}
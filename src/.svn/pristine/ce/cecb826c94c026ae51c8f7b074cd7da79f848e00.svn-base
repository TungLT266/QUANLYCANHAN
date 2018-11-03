package geso.traphaco.center.util;

import geso.traphaco.center.db.sql.Idbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Erp_Item
{
	private String spId;
	private String value;
	private String name;
	private String maFast;
	private String spMa;
	private String pt;
	private String difField;
	public Erp_Item()
	{
		this.value = "";
		this.name = "";
		this.difField = "";
		this.pt = "";
		this.spId = "";
	}

	public Erp_Item(String value, String name)
	{
		this.value = value;
		this.name = name;
		this.difField = ""; 
		this.pt = "";
		this.spId = "";
	}
	
	public Erp_Item(String spId, String value, String name, String pt)
	{
		this.spId = spId;
		this.value = value;
		this.name = name;
		this.difField = ""; 
		this.pt = pt;
	}
	public Erp_Item(String spId,String spMa, String value, String name, String pt)
	{
		this.spId = spId;
		System.out.println("spID" + this.spId);
		this.value = value;
		this.name = name;
		this.difField = ""; 
		this.pt = pt;
		this.spMa = spMa;
	}
	
	public static boolean isContainValue(List<Erp_Item> list, String value)
	{
		for (Erp_Item item : list)
		{
			if (item.getValue().trim().equals(value.trim()))
				return true;
		}
		
		return false;
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

	public static String getListFromQuery_2(Idbutils db, String query, List<Erp_Item> list)
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
					Erp_Item item = new Erp_Item(rs.getString("PK_SEQ"),rs.getString("MA"), rs.getString("TONGTIEN"), rs.getString("TEN"), "0" );
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

	public static String getListFromQuery_2(Idbutils db, String query, List<Erp_Item> list, boolean difFeild)
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
					Erp_Item item = new Erp_Item(rs.getString("SPID"), rs.getString("MA"), rs.getString("TEN"), rs.getString("PTPHANBO"));
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
	
	public static String getListFromQuery_3(Idbutils db, String query, List<Erp_Item> list, String[] columnNames)
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
					Erp_Item item = new Erp_Item();
					item.setValue(rs.getString(columnNames[0]));
					item.setName(rs.getString(columnNames[1]));
					item.setMaFast(rs.getString(columnNames[2]));
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
	
	public String getSpId() {
		return spId;
	}


	public void setSpId(String spId) {
		this.spId = spId;
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

	public String getPhantram() {
		return pt;
	}

	public void setPhantram(String pt) {
		this.pt = pt;
	}

	public void setDifField(String difField) {
		this.difField = difField;
	}

	public String getDifField() {
		return difField;
	}
	public void setSPMa(String spMa){
		this.spMa = spMa;
	}
	public String getSPMa(){
		return spMa;
	}

	public String getMaFast() {
		return maFast;
	}

	public void setMaFast(String maFast) {
		this.maFast = maFast;
	}
	
}
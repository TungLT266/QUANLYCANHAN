package geso.traphaco.erp.util;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.util.WebService;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;

public class UtilitySyn implements Serializable
{ 
	private static final long serialVersionUID = 1L;

	public static String SynData(Idbutils db, String fromTable, String toTable, String columnName, String value, 
			String Insert_Update_Delete, boolean isDelete )
	{
		//Insert_Update_Delete: 0 -- Insert, 1 -- Update, 2 -- Delete.
		//Trường hợp Delete, mà không phải xóa dòng khỏi db, thì sử dụng như update, biến isDelete = false 
		
		boolean sudungDBNGOAI = true;
		if( db == null )
		{
			db = new geso.traphaco.erp.db.sql.dbutils();
			sudungDBNGOAI = false;
		}
		
		String query = "select synQuery from SynConfig where fromTable = '" + fromTable + "' and toTable = '" + toTable + "' ";
		ResultSet rs = db.get(query);
		String synQuery = "";
		if( rs != null )
		{
			try 
			{
				if( rs.next() )
				{
					synQuery = rs.getString("synQuery");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				if( !sudungDBNGOAI )
					db.shutDown();
				
				return "1. Lỗi khi xử lý dữ liệu: " + e.getMessage();
			}
		}
		
		if( synQuery.trim().length() <= 0 )
		{
			/*if( !sudungDBNGOAI )
				db.shutDown();
			return "2. Lỗi khi xử lý dữ liệu";*/
			
			//Nếu chưa khai báo thì tự hiểu là SYN tất cả các cột
			synQuery = "*";
		}
		
		//LINK SANG DMS
		//toTable = dmsLinked + toTable;
		
		if( Insert_Update_Delete.equals("0") )
		{
			String synQueryNEW = "";
			if( synQuery.equals("*") ) //Đồng bộ nguyên bảng
			{
				query = "SELECT COLUMN_NAME " +
						"FROM INFORMATION_SCHEMA.COLUMNS  " +
						"WHERE TABLE_NAME = '" + fromTable + "' ";
				rs = db.get(query);
				try 
				{
					while( rs.next() )
					{
						synQueryNEW += rs.getString("COLUMN_NAME") + ", ";
					}
					rs.close();
					
					synQueryNEW = synQueryNEW.substring(0, synQueryNEW.length() - 2);
				} 
				catch (Exception e) { }
			}
			else
				synQueryNEW = synQuery;
			
			synQueryNEW = synQueryNEW.toLowerCase();
			synQueryNEW = synQueryNEW.replace("nguoitao", "100002 as nguoitao");
			synQueryNEW = synQueryNEW.replace("nguoisua", "100002 as nguoisua");
			
			if( synQuery.equals("*") ) //Đồng bộ nguyên bảng
				query = "insert " + toTable + " select " + synQueryNEW + " from LINK_DB_22.TraphacoERP.dbo." + fromTable + " where " + columnName + " = '" + value + "' ";
			else
				query = "insert " + toTable + " ( " + synQuery + " ) select " + synQueryNEW + " from LINK_DB_22.TraphacoERP.dbo." + fromTable + " where " + columnName + " = '" + value + "' ";
		}
		else if( Insert_Update_Delete.equals("1") || ( Insert_Update_Delete.equals("2") && !isDelete ) )
		{
			String synQueryNEW = "";
			if( synQuery.equals("*") ) //Đồng bộ nguyên bảng
			{
				query = "SELECT COLUMN_NAME " +
						"FROM INFORMATION_SCHEMA.COLUMNS  " +
						"WHERE TABLE_NAME = '" + fromTable + "' ";
				rs = db.get(query);
				try 
				{
					while( rs.next() )
					{
						synQueryNEW += rs.getString("COLUMN_NAME") + ", ";
					}
					rs.close();
					
					synQueryNEW = synQueryNEW.substring(0, synQueryNEW.length() - 2);
				} 
				catch (Exception e) { }
			}
			else
				synQueryNEW = synQuery;
			
			synQueryNEW = synQueryNEW.toLowerCase();
			System.out.println("::: synQueryNEW: " + synQueryNEW);
			
			String[] arr = synQueryNEW.split(", ");
			String sqlUPDATE = "";
			for( int i = 0; i < arr.length; i++ )
			{
				if( arr[i].trim().equals("nguoitao") || arr[i].trim().equals("nguoisua") )
					sqlUPDATE += "b." + arr[i] + " = 100002, ";
				else
					sqlUPDATE += "b." + arr[i] + " = " + "a." + arr[i] + ", ";
			}
			
			sqlUPDATE = sqlUPDATE.substring(0, sqlUPDATE.length() - 2);
			query = " update b set " + sqlUPDATE + 
					" from LINK_DB_22.TraphacoERP.dbo." + fromTable + " a inner join " + toTable + " b on a.pk_seq = b.pk_seq " + 
					" where a." + columnName + " = '" + value + "' ";
			
		}
		else if( isDelete )
		{
			query = "delete " + toTable + " where " + columnName + " = '" + value + "' ";
		}
		
		System.out.println("::: THUC HIEN SYN: " + query);
		WebService.SynDataFromERP(query);
		/*if( !db.update(query) )
		{
			if( !sudungDBNGOAI )
				db.shutDown();
			return "3. Lỗi khi xử lý dữ liệu";
		}*/
		
		//Nếu trường hợp tạo mới, thì bên SYN phải cập nhật lại người tạo, người sửa là ADMIN
		/*if( Insert_Update_Delete.equals("0") && query.contains("nguoitao") )
		{
			query = " update " + toTable + " set nguoitao = '100002', nguoisua = '100002' " + 
					" where " + columnName + " = '" + value + "' ";
			//db.update(query);
			
			WebService.SynDataFromERP(query);
		}*/
		
		if( !sudungDBNGOAI )
			db.shutDown();
		
		return "";
	}
	
	public static String SynData(geso.traphaco.center.db.sql.dbutils db, String fromTable, String toTable, String columnName, String value, 
			String Insert_Update_Delete, boolean isDelete )
	{
		//Insert_Update_Delete: 0 -- Insert, 1 -- Update, 2 -- Delete.
		//Trường hợp Delete, mà không phải xóa dòng khỏi db, thì sử dụng như update, biến isDelete = false 
		
		boolean sudungDBNGOAI = true;
		if( db == null )
		{
			db = new geso.traphaco.center.db.sql.dbutils();
			sudungDBNGOAI = false;
		}
		
		String query = "select synQuery from SynConfig where fromTable = '" + fromTable + "' and toTable = '" + toTable + "' ";
		ResultSet rs = db.get(query);
		String synQuery = "";
		if( rs != null )
		{
			try 
			{
				if( rs.next() )
				{
					synQuery = rs.getString("synQuery");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				if( !sudungDBNGOAI )
					db.shutDown();
				
				return "1. Lỗi khi xử lý dữ liệu: " + e.getMessage();
			}
		}
		
		if( synQuery.trim().length() <= 0 )
		{
			/*if( !sudungDBNGOAI )
				db.shutDown();
			return "2. Lỗi khi xử lý dữ liệu";*/
			
			//Nếu chưa khai báo thì tự hiểu là SYN tất cả các cột
			synQuery = "*";
		}
		
		//LINK SANG DMS
		//toTable = dmsLinked + toTable;
		
		//Tạm thời giữ lại LINK để chạy ổn trươc
		
		if( Insert_Update_Delete.equals("0") )
		{
			String synQueryNEW = "";
			if( synQuery.equals("*") ) //Đồng bộ nguyên bảng
			{
				query = "SELECT COLUMN_NAME " +
						"FROM INFORMATION_SCHEMA.COLUMNS  " +
						"WHERE TABLE_NAME = '" + fromTable + "' ";
				rs = db.get(query);
				try 
				{
					while( rs.next() )
					{
						synQueryNEW += rs.getString("COLUMN_NAME") + ", ";
					}
					rs.close();
					
					synQueryNEW = synQueryNEW.substring(0, synQueryNEW.length() - 2);
				} 
				catch (Exception e) { }
			}
			else
				synQueryNEW = synQuery;
			
			synQueryNEW = synQueryNEW.toLowerCase();
			synQueryNEW = synQueryNEW.replace("nguoitao", "100002 as nguoitao");
			synQueryNEW = synQueryNEW.replace("nguoisua", "100002 as nguoisua");
			
			if( synQuery.equals("*") ) //Đồng bộ nguyên bảng
				query = "insert " + toTable + " select " + synQueryNEW + " from LINK_DB_22.TraphacoERP.dbo." + fromTable + " where " + columnName + " = '" + value + "' ";
			else
				query = "insert " + toTable + " ( " + synQuery + " ) select " + synQueryNEW + " from LINK_DB_22.TraphacoERP.dbo." + fromTable + " where " + columnName + " = '" + value + "' ";
		}
		else if( Insert_Update_Delete.equals("1") || ( Insert_Update_Delete.equals("2") && !isDelete ) )
		{
			String synQueryNEW = "";
			if( synQuery.equals("*") ) //Đồng bộ nguyên bảng
			{
				query = "SELECT COLUMN_NAME " +
						"FROM INFORMATION_SCHEMA.COLUMNS  " +
						"WHERE TABLE_NAME = '" + fromTable + "' ";
				rs = db.get(query);
				try 
				{
					while( rs.next() )
					{
						//Nếu update, không update cột làm khóa
						if (!(Insert_Update_Delete.equals("1") 
								&& columnName.toLowerCase().equals(rs.getString("COLUMN_NAME").toLowerCase())))
							synQueryNEW += rs.getString("COLUMN_NAME") + ", ";
					}
					rs.close();
					
					synQueryNEW = synQueryNEW.substring(0, synQueryNEW.length() - 2);
				} 
				catch (Exception e) { 
					e.printStackTrace();
				}
			}
			else
				synQueryNEW = synQuery;
			
			synQueryNEW = synQueryNEW.toLowerCase();
			System.out.println("::: synQueryNEW: " + synQueryNEW);
			
			String[] arr = synQueryNEW.split(", ");
			String sqlUPDATE = "";
			for( int i = 0; i < arr.length; i++ )
			{
				//System.out.println(":::::: i " + arr[i] + "  -- to loew: " + arr[i].trim().toLowerCase() );
				if( arr[i].trim().toLowerCase().equals("nguoitao") || arr[i].trim().toLowerCase().equals("nguoisua") )
					sqlUPDATE += "b." + arr[i] + " = 100002, ";
				else
					sqlUPDATE += "b." + arr[i] + " = " + "a." + arr[i] + ", ";
				
				//System.out.println("---- SQLUPDATE: " + sqlUPDATE);
			}
			
			sqlUPDATE = sqlUPDATE.substring(0, sqlUPDATE.length() - 2);
			query = " update b set " + sqlUPDATE + 
					" from LINK_DB_22.TraphacoERP.dbo." + fromTable + " a inner join " + toTable + " b on a.pk_seq = b.pk_seq " + 
					" where a." + columnName + " = '" + value + "' ";
		}
		else if( isDelete )
		{
			query = "delete " + toTable + " where " + columnName + " = '" + value + "' ";
		}
		
		System.out.println("::: THUC HIEN SYN: " + query);
		WebService.SynDataFromERP(query);
		/*if( !db.update(query) )
		{
			if( !sudungDBNGOAI )
				db.shutDown();
			return "3. Lỗi khi xử lý dữ liệu";
		}*/
		
		//Nếu trường hợp tạo mới, thì bên SYN phải cập nhật lại người tạo, người sửa là ADMIN
		/*if( Insert_Update_Delete.equals("0") && query.contains("nguoitao") )
		{
			query = " update " + toTable + " set nguoitao = '100002', nguoisua = '100002' " + 
					" where " + columnName + " = '" + value + "' ";
			
			//db.update(query);
			WebService.SynDataFromERP(query);
		}*/
		
		if( !sudungDBNGOAI )
			db.shutDown();
		
		return "";
	}
	
	public static void main(String[] arg)
	{
		UtilitySyn util = new UtilitySyn();
		geso.traphaco.erp.db.sql.dbutils db = new dbutils();
		String msg = util.SynData(db, "TEST01", "TEST01", "PK_SEQ", "1", "1", false);
		
		System.out.println("::: MSG SYN: " + msg);
	}
	
}


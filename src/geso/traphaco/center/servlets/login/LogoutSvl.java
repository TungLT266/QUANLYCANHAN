package geso.traphaco.center.servlets.login;

import geso.traphaco.center.db.sql.dbutils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public LogoutSvl()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		dbutils db = new dbutils();
		try
		{
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);
			String query = "update NHANVIEN set ISLOGIN='0' where PK_SEQ=" + userId + "";
			if(!db.update(query))
			{
				db.getConnection().rollback();
			}
			query = "update dangnhap_nhanvien set logout=getdate() where nhanvien_fk=" + userId + " and ngay='" + getDate() + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		session.removeAttribute("userId");
		session.removeAttribute("userTen");
		session.removeAttribute("tdv_dangnhap_id");
		session.removeAttribute("npp_duocchon_id");

		response.sendRedirect("index.jsp");
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		
		dbutils db = new dbutils();
		try
		{
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);
			String query = "update NHANVIEN set ISLOGIN='0' where PK_SEQ='" + userId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
			}
			query = "update dangnhap_nhanvien set logout=getdate() where nhanvien_fk=" + userId + " and ngay='" + getDate() + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		session.invalidate();
		session.removeAttribute("userId");
		session.removeAttribute("userTen");
		session.removeAttribute("tdv_dangnhap_id");
		session.removeAttribute("npp_duocchon_id");

		response.sendRedirect("index.jsp");
	}
	private String getDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}

package geso.traphaco.center.servlets.count;
import geso.traphaco.distributor.db.sql.dbutils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
public class SessionCounter implements HttpSessionListener 
{

	public static int activeSessions = 0;

	public void sessionCreated(HttpSessionEvent se) 
	{
		HttpSession session = se.getSession();
		System.out.println("Tao session "+ session.getId());
		//activeSessions++;
	}

	public void sessionDestroyed(HttpSessionEvent se) 
	{
		HttpSession session = se.getSession();
		String userId=(String)session.getAttribute("userId");
		String query ="update NHANVIEN set ISLOGIN='0' where PK_SEQ='"+userId+"'";
		dbutils db=new dbutils();
		db.update(query);
		System.out.println("session duoc huy "+session.getId());
		if(activeSessions > 0)
			activeSessions--;
	}

	public static int getActiveSessions() {
		
		return activeSessions;
	}
	
	

}

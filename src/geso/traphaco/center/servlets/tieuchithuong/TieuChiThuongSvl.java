package geso.traphaco.center.servlets.tieuchithuong;
import geso.traphaco.center.beans.tieuchithuong.imp.TieuchithuongList;
import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TieuChiThuongSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TieuChiThuongSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();	    
      
	    Utility util = new Utility();

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);

	    	    
	    ITieuchithuongList obj = new TieuchithuongList();
	    obj.setUserId(userId);

	    
	    String action = util.antiSQLInspection(util.getAction(querystring));
	    
	    String id = util.antiSQLInspection(util.getId(querystring));
	    String loai = util.antiSQLInspection(util.getId(querystring));
	    	    
	    if(action.equals("chot")){
		    id = util.antiSQLInspection(util.getId(querystring)).split(";")[0];
		    loai = util.antiSQLInspection(util.getId(querystring)).split(";")[1];
		    obj.setLoai(loai);

	    	Chot(id);
	    }
	    
	    if(action.equals("mochot")){
		    id = util.antiSQLInspection(util.getId(querystring));
		    MoChot(id);
	    }
	    
	    if(action.equals("xoa")){
		    id = util.antiSQLInspection(util.getId(querystring)).split(";")[0];
		    loai = util.antiSQLInspection(util.getId(querystring)).split(";")[1];
		    obj.setLoai(loai);

	    	Xoa(id);
	    }
	    
	    obj.init();
	    
	    session.setAttribute("obj", obj);
	    
    	session.setAttribute("userId", userId);
		
    	response.sendRedirect("/TraphacoHYERP/pages/Center/TieuChiThuong.jsp");
	    
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();	    
	    Utility util = new Utility();
	    ITieuchithuongList obj = new TieuchithuongList();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
	    String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
	    String month = util.antiSQLInspection(request.getParameter("month"));
	    String year = util.antiSQLInspection(request.getParameter("year"));
	    String action = util.antiSQLInspection(request.getParameter("action"));
	    String loai = util.antiSQLInspection(request.getParameter("loai"));
	    
	    String htbh = util.antiSQLInspection(request.getParameter("htbh"));
	    
	    obj.setDvkdId(dvkdId);
	    obj.setKbhId(kbhId);
	    obj.setUserId(userId);
	    obj.setMonth(month);
	    obj.setYear(year);
	    obj.setLoai(loai);
	    obj.sethethongBH(htbh);
	    
	    System.out.println(action);
	    
	    if(action.equals("timkiem")){
		    obj.init();
		    
		    session.setAttribute("obj", obj);
		    
	    	session.setAttribute("userId", userId);
			
	    	response.sendRedirect("/TraphacoHYERP/pages/Center/TieuChiThuong.jsp");
	    	return;
	    }	    
	    
	}
	private void Chot(String id){
		String query = "UPDATE TIEUCHITINHTHUONG SET TRANGTHAI ='1' WHERE PK_SEQ = '" + id + "'";
		System.out.println(query);
		
		dbutils db = new dbutils();
		db.update(query);
		db.shutDown();
	}
	
	private void MoChot(String id){
		String query = "UPDATE TIEUCHITINHTHUONG SET TRANGTHAI ='0' WHERE PK_SEQ = '" + id + "'";
		System.out.println(query);
		
		dbutils db = new dbutils();
		db.update(query);
		db.shutDown();
	}
	
	private void Xoa(String id){
		dbutils db = new dbutils();
		String query;
		try{
			query = "SELECT LOAI FROM TIEUCHITINHTHUONG WHERE PK_SEQ = '" + id + "'";
			ResultSet rs = db.get(query);
			rs.next();
			String loai = rs.getString("LOAI");
			rs.close();
			
			query = "SELECT COUNT(*) AS NUM FROM TIEUCHITINHTHUONG WHERE LOAI='" + loai + "'";
			rs = db.get(query);
			rs.next();
			if(!rs.getString("num").equals("1")){
				query = "DELETE TIEUCHITHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK = '" + id + "'";
				db.update(query);
				System.out.println(query);
			
				query = "DELETE TIEUCHITINHTHUONG WHERE PK_SEQ = '" + id + "'";
				db.update(query);
			
				System.out.println(query);
			}

			db.shutDown();
		}catch(Exception e){}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}

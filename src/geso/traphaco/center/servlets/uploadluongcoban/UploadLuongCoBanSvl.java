package geso.traphaco.center.servlets.uploadluongcoban;

import geso.traphaco.center.beans.uploadluongcoban.IUploadLuongCoBan;
import geso.traphaco.center.beans.uploadluongcoban.imp.UploadLuongCoBan;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UploadLuongCoBanSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadLuongCoBanSvl() {
		super();
		// TODO Auto-generated constructor stub
	}
	private String gettenuser(String userId_){
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq="+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
				while(rs_tenuser.next()){
					return rs_tenuser.getString("ten");
				}
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		IUploadLuongCoBan obj = new UploadLuongCoBan();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);  
		obj.setUserId(userId);
		if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		session.setAttribute("userId", userId); 
		session.setAttribute("userTen", gettenuser(userId));
		String action = util.getAction(querystring);
		//out.println(action);   
		String idlist = util.getId(querystring); 
		try{
			obj.setID(idlist.trim());
		}catch(Exception er){};

		String nextJSP = "/TraphacoHYERP/pages/Center/UploadLuongCoBan.jsp";//default

		String loaict="1";
		if(action.equals("delete")){
			obj.DeleteUploadLuongCoBan();
			obj.setLuongkhacRs("");
			session.setAttribute("obj", obj);
		}else if(action.equals("chot")){

			obj.chotUploadLuongCoBan();
			obj.setLuongkhacRs("");
			session.setAttribute("obj", obj);
		}
		else if(action.equals("new")){// circumstance add new 
			obj.setID("");
			nextJSP= "/TraphacoHYERP/pages/Center/UploadLuongCoBanUpdate.jsp";
		}
		else if(action.equals("unchot")){// circumstance add new 
			obj.setID(idlist.trim());			
			//obj.UnChotChiTieu_Sec();	
			obj.UnchotUploadLuongCoBan();
			obj.setLuongkhacRs("");
			session.setAttribute("obj", obj);
		}
		else{
			session.setAttribute("thang",0);
			session.setAttribute("nam",0);
			obj.setLuongkhacRs("");
			session.setAttribute("obj", obj);
		}
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		String username=util.antiSQLInspection(request.getParameter("userTen"));

		String thangtk=util.antiSQLInspection(request.getParameter("thang"));
		String namtk=util.antiSQLInspection(request.getParameter("nam"));
		String action =request.getParameter("action");

		IUploadLuongCoBan uploadluongcoban =new UploadLuongCoBan();
		uploadluongcoban.setUserId(userId);
		HttpSession session=request.getSession();

		session.setAttribute("nam",Integer.parseInt(namtk));
		session.setAttribute("thang",Integer.parseInt(thangtk));

		Utility Ult = new Utility(); 
		if(action.equals("search"))
		{
			String  sql_getdata="";

			sql_getdata="SELECT   c.trangthai,C.PK_SEQ, C.THANG, C.NAM, C.DIENGIAI, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
			"NS.TEN AS NGUOISUA FROM uploadluongcoban AS C INNER JOIN "+
			"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
			"where 1=1 ";

			String where ="";
			if(!thangtk.equals("0")){
				where=" and C.THANG="+ thangtk + " ";	
			}
			if(!namtk.equals("0")){
				where=where + "and C.NAM="+namtk +" ";
			}

			if(where !=""){//if have search condition
				sql_getdata=sql_getdata+ where;	
			}
			sql_getdata += "\n order by  C.PK_SEQ desc";

			uploadluongcoban.setLuongkhacRs(sql_getdata);
			session.setAttribute("obj", uploadluongcoban);
			String nextJSP = "/TraphacoHYERP/pages/Center/UploadLuongCoBan.jsp";//default
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("new"))
		{


			IUploadLuongCoBan obj = new UploadLuongCoBan();
			obj.setUserId(userId);
			obj.setUserId("");
			obj.setDisplay("0");
			session.setAttribute("userId",userId);
			session.setAttribute("userTen", username);
			session.setAttribute("obj", uploadluongcoban);
			session.setAttribute("check", "0");
			String	nextJSP= "/TraphacoHYERP/pages/Center/UploadLuongCoBanUpdate.jsp";
			response.sendRedirect(nextJSP);

		}
	}

	
}

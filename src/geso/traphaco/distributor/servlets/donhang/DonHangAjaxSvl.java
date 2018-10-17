package geso.traphaco.distributor.servlets.donhang;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import AI.S;

public class DonHangAjaxSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public DonHangAjaxSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		Utility util = new Utility();

		String id = util.antiSQLInspection(request.getParameter("id"));
		String scheme = util.antiSQLInspection(request.getParameter("scheme"));
		
		System.out.println("SCheme: " + scheme + "  -- DON HANG: " + id + "\n");
		if(id != null && scheme != null)
		{
			dbutils db = new dbutils();
			String query = "delete DUYETTRAKHUYENMAI_DONHANG where donhang_fk = '" + id + "' and diengiai = '" + scheme + "' ";
			System.out.println("--XOA CHIET KHAU: " + query );
			
			if(db.update(query))
			{
				db.shutDown();
				out.write("OK");
			}
			else
			{
				db.shutDown();
				out.write("Lỗi khi xóa chiết khấu. Vui lòng thử lại sau.");
			}
		}
		
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String q = util.antiSQLInspection(request.getParameter("q"));
		String id = util.antiSQLInspection(request.getParameter("id"));
		String nppId = (String)session.getAttribute("nppId");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(id.equals("ddkdTen")) //loc dai dien kinh doanh
		{
			//System.out.println("Giam sat ban hang Id la: " + q + "\n");
			if(q != null)
			{
				dbutils db = new dbutils();
				String query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai = '1' and pk_seq in  ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + nppId + "' ) " +
						" and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk from nhapp_giamsatbh where ngaybatdau <='"+this.getDateTime()+"' and ngayketthuc >='"+getDateTime()+"' and  gsbh_fk ='" + q + "') )";
				
				//System.out.println("Query lay dai dien kinh doanh la: " + query + "\n");
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						out.write("<option value=''></option>");
						while(rs.next())
						{
							out.write("<option value='" + rs.getString("ddkdId") + "'>" + rs.getString("ddkdTen") + "</option>");
						}
					} 
					catch(Exception e) {}
				}
			}
		}
		else
		{
			if(id.equals("gsbhId"))
			{
				
				dbutils db = new dbutils();
				String query = 
					"select a.pk_seq,a.ten,isnull((select top(1) gsbh_fk from  ddkd_gsbh where ddkd_fk='"+q+"'),0) as gsId from giamsatbanhang a where a.pk_Seq in (select gsbh_fk from  ddkd_gsbh where ddkd_fk='"+q+"') and a.trangthai=1  union " +
				  "select PK_SEQ,TEN,isnull((select top(1) gsbh_fk from  ddkd_gsbh where ddkd_fk='"+q+"'),0) as gsId  from GIAMSATBANHANG a inner join NHAPP_GIAMSATBH b on b.GSBH_FK=a.PK_SEQ  where b.NPP_FK in (select NPP_FK from DAIDIENKINHDOANH_NPP where ddkd_fk='"+q+"'  )";
				
				System.out.println("Query lay dai dien kinh doanh la: " + query + "\n");
				ResultSet rs = db.get(query);
					try 
					{
						out.write("<option value=''></option>");
						while(rs.next())
						{
							if(rs.getString("gsId").equals(rs.getString("pk_seq") ))
							{
								out.write("<option value='" + rs.getString("pk_seq") + "' selected>" + rs.getString("ten") + "</option>");
							}else 
							{
								out.write("<option value='" + rs.getString("pk_seq") + "' >" + rs.getString("ten") + "</option>");
							}
						}
					} 
					catch(Exception e) {e.printStackTrace();}
					db.shutDown();
				//System.out.println("Dai dien kinh doanh Id la: " + q + "\n");
				session.setAttribute("ddkdId", q);
			}
		}
	}

}

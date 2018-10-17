package geso.traphaco.center.servlets.thongbao;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThongbaoAjax extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ThongbaoAjax() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
		/*HttpSession session = request.getSession();
		String userId=(String)session.getAttribute("userId");
		String query ="update NHANVIEN set ISLOGIN='0' where PK_SEQ='"+userId+"'";
		dbutils db=new dbutils();
		db.update(query);
		db.shutDown();
		session.invalidate();*/
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String task = request.getParameter("action");
		if(task==null)
			task="";
		//System.out.print("task...."+task);
		if(task.equals("download"))
		{
			dbutils db=new dbutils();
		    String id= request.getParameter("id");
		    String query = "select filename from thongbao where pk_seq='"+id+"'";
			System.out.println("cau select: "+query);
			ResultSet rs = db.get(query);
			String filename="";
			try {
				rs.next();
				filename = rs.getString("filename");
			} catch (SQLException e) {
				
			}
			String[] spli=new String[3];
  			spli= filename.split(",");
		    FileInputStream fileToDownload = new FileInputStream("C:\\java-tomcat\\dinhkem\\"+filename);
		    ServletOutputStream output = response.getOutputStream();
		    response.setContentType("application/octet-stream");
		    String tenfile=spli[0]+spli[2];
		    //System.out.println(tenfile);
		    response.setHeader("Content-Disposition","attachment; filename=\"" + tenfile + "\"");
		    response.setContentLength(fileToDownload.available());
		    int c;
		    while ((c = fileToDownload.read()) != -1)
		    {
		    output.write(c);
		    }
		    output.flush();
		    output.close();
		    fileToDownload.close();
		}
		else 
		{
		    PrintWriter out = response.getWriter();
			Utility util = new Utility();
			String q = util.antiSQLInspection(request.getParameter("q"));
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			if(q != null)
			{
				dbutils db = new dbutils();
				String query =  "select count(thongbao_fk) as soluong from thongbao_nhanvien " +
						   		"where trangthai!='2' and nhanvien_fk='"+id+"' and trangthai='0'";
				ResultSet rs = db.get(query);
				int soTB = 0;
				try 
				{
					if(rs.next())
					{
						soTB = rs.getInt("soluong");
					}
					rs.close();
				} 
				catch(Exception e) 
				{
					e.printStackTrace();
				}
				
				//out.write("<p style=' color: red; font-weight: bold;' >Hộp thư đến (" + soTB +  ")</p>");
				String chuoi = "<p style=' color: red; font-weight: bold;' >Hộp thư mới chưa đọc (" + soTB +  ")</p>";

				query = "select top 10 pk_seq,tieude from THONGBAO a inner join THONGBAO_NHANVIEN b on a.pk_seq=b.thongbao_fk " +
						"where nhanvien_fk ='"+id+"' and b.trangthai='0' order by ngaybatdau desc";
				/*System.out.println("thong bao NEW: " + query);*/

				rs = db.get(query);
				if(rs != null)
				{
					chuoi += "<ul id=\"thongbao\" style=\"display:none\"><li><p style=\"color:red \">Các thư đến chưa đọc:</p></li>";
					try 
					{
						while(rs.next())
						{
							chuoi += "<li><A href=\"../../ThongbaoUpdateSvl?task=capnhatnv&id="+ rs.getString("PK_SEQ") +"\"><strong>"+rs.getString("tieude") +"</A></strong></li> ";
						}
						rs.close();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					finally
					{ 
						if(db!=null)db.shutDown();
					}
					chuoi += "</ul>";
				}
				
				out.write(chuoi);
				
			}
			
		}
	}
}

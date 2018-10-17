package geso.traphaco.erp.servlets.lenhsanxuatgiay;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class LenhsanxuatAjaxSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public LenhsanxuatAjaxSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String spid = util.antiSQLInspection(request.getParameter("spid"));
		String trongluong=  util.antiSQLInspection(request.getParameter("trongluong"));
		String congtyId= (String) session.getAttribute("congtyId") ;
		dbutils db = new dbutils();
		 
			if (spid != null)
			{
 				try{
					String   sql=      " select  isnull(trongluong,0) as trongluong ,ma,isnull(chuannen,'0') as chuannen ,dvkd_fk ,isnull(chungloai_fk,0) as chungloai_fk " +
							         "  from erp_sanpham sp where pk_seq='"+spid+"'";
					ResultSet rs=db.get(sql);
					rs.next();
					 	sql=" select pk_seq,diengiai from ERP_DANHMUCVATTU   where trangthai=1 and mavattu='"+rs.getString("ma")+"' and congty_fk="+congtyId;
						if(rs.getString("dvkd_fk").equals("100004")){
							if(rs.getString("chungloai_fk").equals("100040")){
								 
								 sql=    " select pk_seq,diengiai from ERP_DANHMUCVATTU  " +
								 		 "  where trangthai=1 and trongluong="+rs.getString("trongluong")+" and mavattu='"+rs.getString("ma")+"' and congty_fk="+congtyId;
								
							}else if(rs.getString("chungloai_fk").equals("100031")){
								 sql=    " select pk_seq,diengiai from ERP_DANHMUCVATTU  a  where trangthai=1  " +
										 " and congty_fk="+congtyId +" and a.loaichungloai=1 and CHUANNEN=N'"+rs.getString("chuannen")+"'" ;
							}else{
								 sql=    " select pk_seq,diengiai from ERP_DANHMUCVATTU  a  where trangthai=1  " +
								         " and congty_fk="+congtyId +" and a.loaichungloai=2  and CHUANNEN=N'"+rs.getString("chuannen")+"'" ;
							}
						}
						System.out.println("Lay Du Lieu : "+sql);
						 
						rs=db.get(sql);
						out.write("<option value=''></option>");
						while (rs.next())
						{
							out.write("<option value='" + rs.getString("pk_seq") + "'>" + rs.getString("diengiai") +
								"</option>");
						}
					
						rs.close();
						db.shutDown();
					 
					}catch(Exception er){
						db.shutDown();
						er.printStackTrace();
						 
					}
			}
		 
	}

}

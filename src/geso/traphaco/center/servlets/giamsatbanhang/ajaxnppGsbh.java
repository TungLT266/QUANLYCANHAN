package geso.traphaco.center.servlets.giamsatbanhang;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ajaxnppGsbh extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
  
    public ajaxnppGsbh() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
					
		String kvIds = request.getParameter("kvIds");							
		String kenhId = request.getParameter("kenhId");
		String nppIds = request.getParameter("nppIds");
		String gsbhId = request.getParameter("gsbhId");
		
		System.out.println("NPP : "+nppIds);		
		dbutils db = new dbutils();
		String query="";			

		String table = "<TABLE width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"6\"> " + 
		 		"<tbody id=\"tb_npp\"> " +
		 		"<TR class=\"tbheader\"> " +
		 		"<TH width=\"10%\">Mã NPP</TH> " +
		 		"<TH width=\"30%\">Tên NPP</TH> " +	
		 		"<TH width=\"20%\">Ngày bắt đầu</TH> " +	
		 		"<TH width=\"20%\">Ngày kết thúc</TH> " +			 		
		 		"<TH align=\"center\" width=\"10%\"> Chọn tất cả <input type =\"checkbox\" id =\"cbNpp\" onClick=\"sellectAll('cbNpp','npp')\"></TH> ";
		
			if(kvIds.length() > 0)
			{
				kvIds = kvIds.substring(0, kvIds.length() - 1);
				query = 
				"select pk_seq as manpp, ten as tennpp, '' as ngaybatdau, '' as ngayketthuc from nhaphanphoi npp " +						
				"where trangthai = '1'    and npp.khuvuc_fk in ("+ kvIds +") ";
		/*		if(kenhId.length()>0)
					query+=" and npp.pk_Seq in (select npp_fk from nhapp_kbh  where kbh_fk='"+kenhId+"' ) ";*/
				if(gsbhId!=null && gsbhId.length()>0)
				{
					query+=" and npp.pk_seq not in (select NPP_FK from NHAPP_GIAMSATBH WHERE GSBH_FK='"+gsbhId+"') ";
					query+=" union select npp.pk_seq as manpp,npp.ten tennpp,gs.ngaybatdau,gs.ngayketthuc "+
						   " from nhapp_giamsatbh  gs inner join nhaphanphoi npp on npp.pk_seq=gs.npp_fk "+
						   "	where gs.gsbh_fk='"+gsbhId+"' ";
				}				
					
		System.out.print("[Ajax]"+query);
		if(nppIds.length() > 0)
		{
			nppIds = nppIds.substring(0, nppIds.length() - 1);		
		}
			
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				/*out.write("<option value=''></option>");*/
				int i = 0;
				while(rs.next())
				{
					if(i % 2 != 0)
						table += "<TR class= 'tblightrow' >";
					else
						table += "<TR class= 'tbdarkrow' >";
					table += "<TD align=\"center\" class=\"textfont\">" + rs.getString("manpp") + "</TD>";
					table += "<TD align=\"left\" class=\"textfont\">" + rs.getString("tennpp") + "</TD>";
					table += "<TD align=\"center\"><input class=\"days\" name=\"ngaybatdau" + rs.getString("manpp") + "\" value='"+rs.getString("ngaybatdau")+"'></TD>";
					table += "<TD align=\"center\"><input class=\"days\" name=\"ngayketthuc" + rs.getString("manpp") + "\" value='"+rs.getString("ngayketthuc")+"'></TD>";
					table += "<TD align=\"center\">" +
							"<input name=\"npp\" type=\"checkbox\" value ='" + rs.getString("manpp") + "'</TD></tr>";					
					i++;
				}
			} 
			catch(Exception e) {e.printStackTrace();}
		}
		table += "<TR>";
		table += "<TD align=\"center\" colspan=\"10\" class=\"plainlabel\">&nbsp;</TD>";
		table += "</TR>";
		table += "</table>";		
			}
		
			
			
			out.write(table);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}

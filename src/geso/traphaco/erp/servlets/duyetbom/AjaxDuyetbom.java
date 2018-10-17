package geso.traphaco.erp.servlets.duyetbom;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AjaxDuyetbom")
public class AjaxDuyetbom extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public AjaxDuyetbom()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("AjaxDuyet");
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();	
		String type = request.getParameter("action") == null ? "" : request.getParameter("action");
		String iddmvt = util.antiSQLInspection(request.getParameter("iddmvt")==null?"":request.getParameter("iddmvt"));
		
		
//		String vt = util.antiSQLInspection(request.getParameter("vt")==null?"":request.getParameter("vt"));
		NumberFormat formatter = new DecimalFormat("#,###,###");
		
		int thangHienTai = Integer.parseInt(getDateTime("MM")),
		namHienTai = Integer.parseInt(getDateTime("yyyy")),
		thangtruoc = 0, namtruoc = 0;
		
		if(thangHienTai == 1){
			thangtruoc = 12;
			namtruoc = namHienTai - 1;
		}
		else{
			thangtruoc = thangHienTai - 1;
			namtruoc = namHienTai;
		}
		
		if(type.equals("AjaxDuyet") && iddmvt.length() > 0)
		{	
			dbutils db = new dbutils();
			
			String query = "SELECT distinct bom.MASANPHAM AS  mavattu ,bom.PK_SEQ, bom.DIENGIAI, ISNULL(bom.UUTIEN,0) AS UUTIEN, SUM(vt.GIATHANH) as GIATHANH FROM ERP_DANHMUCVATTU bom \n" + 
							"inner join \n" + 
							"( \n" + 
							"	select vt.DANHMUCVT_FK, isnull(sp.GIATON,0)* vt.SOLUONG as GIATHANH \n" + 
							"	from ERP_DANHMUCVATTU_VATTU vt LEFT join  \n" + 
							"	(select distinct SANPHAM_FK, GIATON from ERP_KHOTT_SANPHAM) AS sp on sp.SANPHAM_FK = vt.VATTU_FK \n" + 
							"	union all \n" + 
							"	select dm_cp.DANHMUCVT_FK,  case when cp.LOAI = 0 then ISNULL(dm_cp.CHIPHI,0) * ISNULL(th.DONGIA,0) else dm_cp.CHIPHI end as GIATHANH \n" + 
							"	from ERP_DANHMUCVATTU_DINHMUCCHIPHI dm_cp inner join ERP_DINHMUCCHIPHI cp on cp.PK_SEQ = dm_cp.DINHMUCCHIPHI_FK \n" + 
							"	left join ERP_DINHMUCCHIPHI_THANG th on th.DINHMUCCHIPHI_FK = dm_cp.DINHMUCCHIPHI_FK \n" +
							"   AND th.THANG = '"+thangtruoc+"' AND th.NAM = '"+namtruoc+"'\n" + 
							"  \n" + 
							") as vt on bom.PK_SEQ = vt.DANHMUCVT_FK \n" + 
							"where bom.MASANPHAM in (select MASANPHAM from erp_danhmucvattu where pk_seq="+iddmvt+")  and bom.TRANGTHAI = 1\n" + 
							"GROUP BY bom.PK_SEQ, bom.DIENGIAI, bom.UUTIEN,bom.MASANPHAM\n";
			System.out.println("cau lenh lay bom: \n" + query + "\n=================================================");
			ResultSet rs = db.get(query);
			if(rs != null)
			{
			}
			String spma="";
			String table="<h4 align='left'>Duyệt ưu tiên</h4>" +
						"<table border='1' style='padding:0' align='center' width='100%'> " + 
						"	<tr>		                                 " + 
						"		<th width='70%'>BOM</th> " + 
						"		<th width='20%'>Chi phí</th>		                                 " + 
						"		<th width='10%'>Ưu tiên</th> " + 
						"	</tr> ";
			if(rs != null)
			{
				try {
					while(rs.next()){
						
						  spma= rs.getString("mavattu");
						table += "	<tr> " + 
								"		<td><input type='text' readonly='readonly' value = '"+rs.getString("DIENGIAI")+"' style='width: 100%'></td> " + 
								"		<td><input type='text' readonly='readonly' value = '"+formatter.format(rs.getDouble("GIATHANH"))+"' style='text-align: right; width: 100%'></td> ";
						if(rs.getString("UUTIEN").equals("0"))
							table +=
								"  		<td><input type='radio' name='sp_"+spma+"' value='"+rs.getString("PK_SEQ")+"' onclick = 'radioClick(this);'></td> " + 
								"	</tr>  ";
						else
							table +=
								"  		<td><input type='radio' name='sp_"+spma+"' value='"+rs.getString("PK_SEQ")+"' onclick = 'radioClick(this);' checked = 'checked'></td> " + 
								"	</tr>  ";
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
						
			table +=
						"</table>		                     " +
						"<br/>" + 
						"<div align='right'> " + 
						"<label style='color: red' ></label> " + 
						"<a class='button' href='#' onclick=\"javascript:boduyet('"+spma+"');\">Bỏ ưu tiên</a> " +
						"&nbsp;&nbsp;&nbsp;" + 
						"<a class='button' href='#' onclick=\"javascript:save('"+spma+"');\">Lưu lại</a> " + 
						"</div>";
			//System.out.println(table);
			db.shutDown();
			out.write(table);
		}
		
	}
	private String getDateTime(String pattern) 
	{
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
      
        return dateFormat.format(date);
	}
}

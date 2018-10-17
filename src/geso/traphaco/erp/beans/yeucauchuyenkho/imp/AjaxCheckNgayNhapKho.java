package geso.traphaco.erp.beans.yeucauchuyenkho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AjaxCheckNgayNhapKho")
public class AjaxCheckNgayNhapKho extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public AjaxCheckNgayNhapKho()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();	
		
		String xkId = util.antiSQLInspection(request.getParameter("xkId")==null?"":request.getParameter("xkId"));
		dbutils db = new dbutils();
		String query = "", kq = "", sp = "";
		query = "select b.ma \n" +
				"from erp_chuyenkho_sanpham_chitiet a inner join erp_sanpham b on a.sanpham_fk = b.pk_seq \n" + 
				"where chuyenkho_fk = " + xkId + " and datediff(dd, getdate(), convert(datetime, ngayhethan, 120)) < 0";
		System.out.println("QUERY: " + query);
		ResultSet rs = db.get(query);
		try
		{
			while(rs.next())
			{
				sp += rs.getString("ma") + ",";
			}
			if(sp.trim().length() > 0)
			{
				sp = sp.substring(0, sp.length() - 1);
				kq = "Các sản phẩm sau đã hết han: " + sp;
			}
			rs.close();
			db.shutDown();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		out.write(kq);
	}
}

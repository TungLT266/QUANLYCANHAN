package qlcn.center.top.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.Dbutils;

/**
 * Servlet implementation class AjaxTop
 */
@WebServlet("/AjaxTop")
public class AjaxTop extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxTop() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		String userId = request.getParameter("userId");
		Dbutils db = new Dbutils();
		NumberFormat formatter = new DecimalFormat("#,###,###,###.##");
		String query = "";
		String output = "";
		
		try {
			query = " select (select isnull(sum(no),0) - isnull(sum(co),0) from PHATSINHKETOAN"
					+ " where tkkt_fk in (select ID from TAIKHOAN where trangthai in (1) and istktindung != 1 and USERID="+userId+")) as tongtien,"
					+ " isnull((select SUM(sotien) as tongthu from THUCHI where loai=1 and trangthai in (1) and MONTH(ngay)=MONTH(getdate()) and YEAR(ngay)=YEAR(getdate()) and USERID="+userId+"),0) as tongthu,"
					+ " isnull((select SUM(sotien) as tongchi from THUCHI where loai=2 and trangthai in (1) and MONTH(ngay)=MONTH(getdate()) and YEAR(ngay)=YEAR(getdate()) and USERID="+userId+"),0) as tongchi";
			ResultSet rs = db.get(query);
			rs.next();
			output = formatter.format(Double.parseDouble(rs.getString("tongtien"))) + "[==]";
			output += formatter.format(Double.parseDouble(rs.getString("tongthu"))) + "[==]";
			output += formatter.format(Double.parseDouble(rs.getString("tongchi")));
			rs.close();
			
			query = "select tk.ID, dv.ten as donvi,"
					+ " (select isnull(sum(no),0) - isnull(sum(co),0) from PHATSINHKETOAN where tkkt_fk = tk.ID) as sotien"
					+ " from TAIKHOAN tk inner join DONVI dv on dv.ID=tk.donvi_fk where tk.trangthai=1 and tk.USERID = " + userId;
			rs = db.get(query);
			while(rs.next()){
				output += "[==]" + rs.getString("ID") + ",," + formatter.format(Double.parseDouble(rs.getString("sotien"))) + " " + rs.getString("donvi");
			}
			rs.close();
//			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		out.write(output);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

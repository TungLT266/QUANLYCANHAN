package geso.traphaco.center.servlets.tieuchithuong;

import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;

public class AjaxTaoTCMoi extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public AjaxTaoTCMoi() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();

		String query = (String)request.getQueryString();

		String diengiai = new String(query.substring(query.indexOf("diengiaitc=") + 11, query.indexOf("&nhomtc=")).getBytes("UTF-8"), "UTF-8");
		diengiai = URLDecoder.decode(diengiai, "UTF-8").replace("+", " ");
		String nhomtc = request.getParameter("nhomtc");
		String tctId = request.getParameter("id");
		String view = request.getParameter("view");
		if(view == null) view = "NV";

		HttpSession session = request.getSession();
		String nguoitao = (String) session.getAttribute("userId");
		String msg ="";
		System.out.println("view = " + view);
		if(view.equals("NV"))
			msg = this.createTCMoi(tctId, diengiai, nhomtc, nguoitao);
		else
			msg = this.createTCMoiNpp(tctId, diengiai, nhomtc, nguoitao);
		out.write(msg);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{}

	private String createTCMoi(String tctId, String diengiai, String nhomtc, String nguoitao)
	{
		dbutils db = new dbutils();
		String msg = "";
		try 
		{
			db.getConnection().setAutoCommit(false);
			String noidung = "";
			String query = "SELECT TIEUCHI FROM TIEUCHI WHERE PK_SEQ = '"+ nhomtc +"'";
			ResultSet rs = db.get(query);
			rs.next();
			String tieuchi = rs.getString("TIEUCHI");



			for(int i = 1; i <= 8; i++)
			{
				noidung += tieuchi+","+ i +",0,0,0;";
			}
			query = 
				"INSERT INTO TIEUCHITHUONG_CHITIET (TIEUCHITINHTHUONG_FK, DIENGIAI, TIEUCHI, NOIDUNG, NGUOISUA, NGAYSUA, TOITHIEU, TOIDA, NHOMSP_FK, THUONG, DSTOITHIEUDH, LOAI, LOAIDS, LOAINVBH) " +
				"SELECT '"+ tctId +"' AS TIEUCHITINHTHUONG_FK, N'"+ diengiai +"' AS DIENGIAI, '"+ tieuchi +"' AS TIEUCHI, '"+ noidung.substring(0, noidung.length()-1) +"' AS NOIDUNG, '"+ nguoitao +"' AS NGUOISUA, '"+ this.getDateTime() +"' AS NGAYSUA, " +
				"0 AS TOITHIEU, 0 AS TOIDA, NULL AS NHOMSP_FK, 0 AS THUONG, 0 AS DSTOITHIEUDH, 0 AS LOAI, 0 AS LOAIDS, NULL AS LOAINVBH ";



			System.out.println("Insert  : "+query);
			if(!db.update(query))
			{
				db.update("rollback");
				msg = "Không thể tạo tiêu chí mới.";
				return msg; 
			}


			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			db.shutDown();
			return e.getMessage();
		}

		db.shutDown();
		return msg;
	}


	private String createTCMoiNpp(String tctId, String diengiai, String nhomtc, String nguoitao)
	{
		dbutils db = new dbutils();
		String msg = "";
		try 
		{
			db.getConnection().setAutoCommit(false);
			String noidung = "";
			String query = "SELECT TIEUCHI FROM TIEUCHI WHERE PK_SEQ = '"+ nhomtc +"'";
			ResultSet rs = db.get(query);
			rs.next();
			String tieuchi = rs.getString("TIEUCHI");

			/*query = " select COUNT(*) as dong from CongThucThuongNpp_ChiTiet where CongThucThuongNpp_FK = '"+tctId+"' and TIEUCHI in (1) and TIEUCHI = '"+tieuchi+"'";
			rs = db.get(query);
			rs.next();
			if(rs.getInt("dong") > 0)
			{
				System.out.println("Loi : "+query);
				db.update("rollback");
				msg = "Tiêu chí đã được tạo trước đó!";
				return msg; 
			}*/

			for(int i = 1; i <= 8; i++)
			{
				noidung += tieuchi+","+ i +",0,0,0;";
			}
			//String ngaytao = getDateTime();


			query = "SELECT LOAI FROM CongThucThuongNpp WHERE PK_SEQ = '"+ tctId +"'";
			rs = db.get(query);
			rs.next();
			String loai = rs.getString("LOAI");
			if( tieuchi.equals("1") || tieuchi.equals("2")  )
			{
				query = "DELETE CongThucThuongNpp_ChiTiet WHERE CongThucThuongNpp_FK = '"+ tctId +"' AND TIEUCHI = '"+ tieuchi +"'";
				if(!db.update(query))
				{
					db.update("rollback");
					msg = "Không thể xóa tiêu chí cũ.";
					return msg; 
				}

				query = 
					"\n INSERT INTO CongThucThuongNpp_ChiTiet (CongThucThuongNpp_FK, DIENGIAI, TIEUCHI, NOIDUNG, NGUOISUA, NGAYSUA, TOITHIEU, TOIDA, NHOMSP_FK, THUONG, DSTOITHIEUDH, LOAI, LOAIDS, loaiNpp) " +
					"\n SELECT '"+ tctId +"' AS CongThucThuongNpp_FK, N'"+ diengiai +" (SalesIn - Thanh Toán ngay)' AS DIENGIAI, '"+ tieuchi +"' AS TIEUCHI, '"+ noidung.substring(0, noidung.length()-1) +"' AS NOIDUNG, '"+ nguoitao +"' AS NGUOISUA, '"+ this.getDateTime() +"' AS NGAYSUA, " +
					"\n 0 AS TOITHIEU, 0 AS TOIDA, NULL AS NHOMSP_FK, 0 AS THUONG, 0 AS DSTOITHIEUDH, 0 AS LOAI, 0 AS LOAIDS, 'TTN' AS loaiNpp " +
					"\n where not exists ( select 1 from  CongThucThuongNpp_ChiTiet where CongThucThuongNpp_FK ="+tctId+" and   tieuchi ="+tieuchi+" and loaiNpp = 'TTN' )  " +
					"\n UNION ALL " +
					"\n SELECT '"+ tctId +"' AS CongThucThuongNpp_FK, N'"+ diengiai +" (SalesOut - Đảm bảo thanh toán)' AS DIENGIAI, '"+ tieuchi +"' AS TIEUCHI, '"+ noidung.substring(0, noidung.length()-1) +"' AS NOIDUNG, '"+ nguoitao +"' AS NGUOISUA, '"+ this.getDateTime() +"' AS NGAYSUA, " +
					"\n 0 AS TOITHIEU, 0 AS TOIDA, NULL AS NHOMSP_FK, 0 AS THUONG, 0 AS DSTOITHIEUDH, 0 AS LOAI, 0 AS LOAIDS, 'DBTT' AS loaiNpp " +
					"\n where not exists ( select 1 from  CongThucThuongNpp_ChiTiet where CongThucThuongNpp_FK ="+tctId+" and   tieuchi ="+tieuchi+" and loaiNpp = 'DBTT' ) " ;
				System.out.println("query = " + query);
			}
			else if(tieuchi.equals("3")||tieuchi.equals("4"))
			{
				query = "DELETE CongThucThuongNpp_ChiTiet WHERE CongThucThuongNpp_FK = '"+ tctId +"' AND TIEUCHI = '"+ tieuchi +"'";
				if(!db.update(query))
				{
					db.update("rollback");
					msg = "Không thể xóa tiêu chí cũ.";
					return msg; 
				}

				query = 
					"\n INSERT INTO CongThucThuongNpp_ChiTiet (CongThucThuongNpp_FK, DIENGIAI, TIEUCHI, NOIDUNG, NGUOISUA, NGAYSUA, TOITHIEU, TOIDA, NHOMSP_FK, THUONG, DSTOITHIEUDH, LOAI, LOAIDS, loaiNpp) " +
					"\n SELECT '"+ tctId +"' AS CongThucThuongNpp_FK, N'"+ diengiai +" (Thanh Toán ngay)' AS DIENGIAI, '"+ tieuchi +"' AS TIEUCHI, '"+ noidung.substring(0, noidung.length()-1) +"' AS NOIDUNG, '"+ nguoitao +"' AS NGUOISUA, '"+ this.getDateTime() +"' AS NGAYSUA, " +
					"\n 0 AS TOITHIEU, 0 AS TOIDA, NULL AS NHOMSP_FK, 0 AS THUONG, 0 AS DSTOITHIEUDH, 0 AS LOAI, 0 AS LOAIDS, 'TTN' AS loaiNpp " +
					"\n where not exists ( select 1 from  CongThucThuongNpp_ChiTiet where CongThucThuongNpp_FK ="+tctId+" and   tieuchi ="+tieuchi+" and loaiNpp = 'TTN' )  " ;

			}
			else
			{
				query = 
					"\n INSERT INTO CongThucThuongNpp_ChiTiet (CongThucThuongNpp_FK, DIENGIAI, TIEUCHI, NOIDUNG, NGUOISUA, NGAYSUA, TOITHIEU, TOIDA, NHOMSP_FK, THUONG, DSTOITHIEUDH, LOAI, LOAIDS, loaiNpp) " +
					"\n SELECT '"+ tctId +"' AS CongThucThuongNpp_FK, N'"+ diengiai +"' AS DIENGIAI, '"+ tieuchi +"' AS TIEUCHI, '"+ noidung.substring(0, noidung.length()-1) +"' AS NOIDUNG, '"+ nguoitao +"' AS NGUOISUA, '"+ this.getDateTime() +"' AS NGAYSUA, " +
					"\n 0 AS TOITHIEU, 0 AS TOIDA, NULL AS NHOMSP_FK, 0 AS THUONG, 0 AS DSTOITHIEUDH, 0 AS LOAI, 0 AS LOAIDS, NULL AS loaiNpp " +
					"\n where not exists ( select 1 from  CongThucThuongNpp_ChiTiet where CongThucThuongNpp_FK ="+tctId+"   and tieuchi ="+tieuchi+"  )  ";
			}

			System.out.println("Insert  : "+query);
			if(!db.update(query))
			{
				db.update("rollback");
				msg = "Không thể tạo tiêu chí mới.";
				return msg; 
			}


			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			db.shutDown();
			return e.getMessage();
		}

		db.shutDown();
		return msg;
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

}

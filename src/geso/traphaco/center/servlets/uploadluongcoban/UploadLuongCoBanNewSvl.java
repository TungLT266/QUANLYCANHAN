package geso.traphaco.center.servlets.uploadluongcoban;

import geso.traphaco.center.beans.uploadluongcoban.IUploadLuongCoBan;
import geso.traphaco.center.beans.uploadluongcoban.imp.UploadLuongCoBan;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import com.oreilly.servlet.MultipartRequest;

public class UploadLuongCoBanNewSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadLuongCoBanNewSvl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	private String gettenuser(String userId_)
	{
		dbutils db = new dbutils();
		String sql_getnam = "select ten from nhanvien where pk_seq=" + userId_;
		ResultSet rs_tenuser = db.get(sql_getnam);
		if (rs_tenuser != null)
		{
			try
			{
				while (rs_tenuser.next())
				{
					return rs_tenuser.getString("ten");
				}
			} catch (Exception er)
			{
				return "";
			}
		}
		return "";
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		// System.out.println("Ten user:  "+ userId);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String id = util.getId(querystring);

		IUploadLuongCoBan obj = new UploadLuongCoBan(id);
		obj.setUserId(userId);
		Utility Ult = new Utility();
		session.setAttribute("userId", userId);
		String tenuser = gettenuser(userId);
		session.setAttribute("userTen", tenuser);
		session.setAttribute("obj", obj);
		String action = util.getAction(querystring);
		session.setAttribute("check", "0");
		if (action.equals("display"))
			obj.setDisplay("1");
		String nextJSP = "/TraphacoHYERP/pages/Center/UploadLuongCoBanUpdate.jsp";// default
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		IUploadLuongCoBan uploadluongcoban = new UploadLuongCoBan();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();

		Utility util = new Utility();



		System.out.println("contentType " + contentType);
		System.out.println("contentType.indexOf(multipart/form-data;charset=utf-8)    " + contentType);
		//
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");

			String userId = util.antiSQLInspection(multi.getParameter("userId"));
			uploadluongcoban.setUserId(userId);
			Enumeration files = multi.getFileNames();
			String filenameu = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filenameu = multi.getFilesystemName(name);
				System.out.println("name  " + name);
				;
			}

			// ///////////////////////////////////
			try
			{
				int thang = Integer.parseInt(util.antiSQLInspection(multi.getParameter("thang")));
				uploadluongcoban.setThang(thang);
			} catch (Exception er)
			{

			}
			try
			{
				int nam = Integer.parseInt(util.antiSQLInspection(multi.getParameter("nam")));
				uploadluongcoban.setNam(nam);
			} catch (Exception er)
			{

			}
			String id = util.antiSQLInspection(multi.getParameter("id"));
			try
			{

				uploadluongcoban.setID(id);

			} catch (Exception err)
			{
			}


			String diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
			uploadluongcoban.setDienGiai(diengiai);
			uploadluongcoban.setNguoiSua(userId);
			uploadluongcoban.setNguoiTao(userId);
			uploadluongcoban.setNgayTao(this.getDateTime());
			uploadluongcoban.setNgaySua(this.getDateTime());

			String filename = "C:\\java-tomcat\\data\\" + filenameu;
			if (filename.length() > 0)
			{
				// doc file excel
				File file = new File(filename);
				System.out.println("filename  " + file);
				Workbook workbook;
				ResultSet rs = null;

				int indexRow = 6;
				try
				{

					System.out.println(file);
					workbook = Workbook.getWorkbook(file);
					Sheet sheet = workbook.getSheet(0);
					Cell[] cells = sheet.getRow(indexRow);

					String values = "";
					for (int i = indexRow ; i < sheet.getRows(); i++)
					{
						System.out.println("Vao Day: " + i);
						cells = sheet.getRow(i);
						if(cells.length > 0 )
						{
							int tangdan = 0;

							String ma = getStringValue(cells, tangdan);
							tangdan += 2;
							double luongcoban = getDoubleValue(cells, tangdan);
							
							if(ma.trim().length() >0 )
								values += "\n select  "+ma+" as ma, "+luongcoban+" as [luongcoban]  union all  " ;
						}

					}
					if (values.length() > 0)
					{
						values = values.substring(0,values.lastIndexOf("]") + 1);
					}
					else
					{
						System.out.println("Khong Thanh Cong");
						String nextJSP ="";
						nextJSP = "/TraphacoHYERP/pages/Center/UploadLuongCoBanUpdate.jsp";// default						
						session.setAttribute("obj", uploadluongcoban);
						response.sendRedirect(nextJSP);
						return;
					}
					if (id.trim().length() <= 0 ? uploadluongcoban.CreateUploadLuongCoBan(values): uploadluongcoban.UpdateUploadLuongCoBan(values))
					{
						
						
						// Thanh cong
						session.setAttribute("nam", 0);
						session.setAttribute("thang", 0);
						uploadluongcoban.setLuongkhacRs("");
						session.setAttribute("obj", uploadluongcoban);
						String nextJSP = "/TraphacoHYERP/pages/Center/UploadLuongCoBan.jsp";
						response.sendRedirect(nextJSP);

					} 
					else
					{
						if(id.trim().length() <= 0) uploadluongcoban.setID("");
						System.out.println("Khong Thanh Cong");
						String nextJSP = "/TraphacoHYERP/pages/Center/UploadLuongCoBanUpdate.jsp";// default
						// Xoa het chi tieu cu di
						session.setAttribute("obj", uploadluongcoban);
						response.sendRedirect(nextJSP);
						System.out.println(uploadluongcoban.getMessage());
					}

				} catch (Exception er)
				{
					er.printStackTrace();
					uploadluongcoban.setMessage("Thong bao loi : " + er.toString());
					System.out.println(er.toString());

				}

			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
	}

	/*public String kiemtraformat(String format)
	{	


		format = format.trim();
		if(format.equals("")) return "";
		int ngoactronmo = 0 ;
		int ngoactrondong = 0;
		int ngoacvuongmo = 0;
		int ngoacvuongdong= 0;
		for( int i = 0 ; i < format.length(); i++)
		{
			char c = format.charAt(i);
			if(c =='{')
				ngoactronmo++;
			else if(c== '}')
				ngoactrondong ++;
			else if(c=='[')
				ngoacvuongmo ++;
			else if(c ==']')
				ngoacvuongdong ++;

		}
		if(ngoactronmo != ngoactrondong)
		{
			return "Lỗi đinh dạng  mã sản phẩm ";
		}
		if(ngoacvuongmo != ngoacvuongdong)
		{
			return "Lỗi đinh dạng chọn mã nhóm sản phẩm ";
		}
		if(ngoactronmo ==0 && ngoacvuongmo == 0)
		{

			return "Lỗi đinh dạng chọn mã sản phẩm ";
		}
		String [] nhomchitieu = format.split(";");
		for(int i = 0; i < nhomchitieu.length;i ++)
		{
			nhomchitieu[i] = nhomchitieu[i].replace("{","").replace("}","").replace("[","").replace("]","").replace(" ","");
			if(nhomchitieu[i].indexOf(",")<0)
				return "sản phẩm chưa có chỉ tiêu";
			String[] chitiet = nhomchitieu[i].split(",");
			System.out.println("nsp = "+ chitiet[0]);
			System.out.println("chitieu = "+ chitiet[1]);
			try { Long.parseLong(chitiet[0]); }catch (Exception e) { return "Ma sp/nsp không hợp lệ";}

			try { Double.parseDouble(chitiet[1]); }catch (Exception e) { return "Doanh số không hợp lệ";}

		}

		return "";
	}*/

	public String kiemtraformat(String format)
	{	


		format = format.trim().replace(" ","");
		if(format.equals("")) return "";
		int ngoacvuongmo = 0;
		int ngoacvuongdong= 0;
		for( int i = 0 ; i < format.length(); i++)
		{
			char c = format.charAt(i);
			if(c=='[')
				ngoacvuongmo ++;
			else if(c ==']')
				ngoacvuongdong ++;

		}

		if(ngoacvuongmo != ngoacvuongdong)
		{
			return "Lỗi đinh dạng chọn mã nhóm sản phẩm ";
		}
		if( ngoacvuongmo == 0)
		{
			return "Lỗi đinh dạng chọn mã nhóm phẩm ";
		}
		if(format.indexOf("][")>=0)
		{
			String [] nhomchitieu = format.split("][");
			for(int i = 0; i < nhomchitieu.length;i ++)
			{
				nhomchitieu[i] = nhomchitieu[i].replace("[","").replace("]","").replace(" ", "");
				if(nhomchitieu[i].indexOf(";")<0)
					return "sản phẩm chưa có chỉ tiêu";
				String[] chitiet = nhomchitieu[i].split(";");
				try { Long.parseLong(chitiet[0]); }catch (Exception e) { return "Ma sp/nsp không hợp lệ";}

				try { Double.parseDouble(chitiet[1]); }catch (Exception e) { return "Doanh số không hợp lệ";}

			}
		}
		else
		{
			format= format.replace("[","").replace("]","").replace(" ", "");
			if(format.indexOf(";")<0)
				return "sản phẩm chưa có chỉ tiêu";
			String[] chitiet = format.split(";");
			System.out.println("chitiet[0]="+ chitiet[0]);
			System.out.println("chitiet[1]="+ chitiet[1]);
			try {Long so= Long.parseLong(chitiet[0]); }catch (Exception e) { return "Ma sp/nsp không hợp lệ";}

			try { Double so = Double.parseDouble(chitiet[1]); }catch (Exception e) { return "Doanh số không hợp lệ";}
		}
		return "";
	}

	public String getStringValue(Cell[] cells,int vitri)
	{
		try
		{
			return cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();
		}
		catch (Exception e) {
			return "";
		}
	}

	public double getDoubleValue(Cell[] cells,int vitri)
	{
		try
		{
			return Double.parseDouble(cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim());
		}
		catch (Exception e) {
			return 0.0;
		}
	}

}

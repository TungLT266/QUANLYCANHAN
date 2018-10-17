package geso.traphaco.center.servlets.chitieu;

import geso.traphaco.center.beans.chitieu.IChiTieu;
import geso.traphaco.center.beans.chitieu.imp.ChiTieu;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;
import com.oreilly.servlet.MultipartRequest;

public class ChiTieuNewSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ChiTieuNewSvl()
	{
		super();

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
				rs_tenuser.close();
			} catch (Exception er)
			{
				return "";
			}
		}
		return "";
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String id = util.getId(querystring);
		String loaict = "";
		loaict = (String) session.getAttribute("loaichitieu");
		if (loaict == null)
		{
			loaict = "";
			try
			{
				String tmp;

				if (querystring != null)
				{
					if (querystring.contains("&"))
					{
						tmp = querystring.split("&")[2];
					} else
					{
						tmp = querystring;
					}
					loaict = tmp.split("=")[1];
				}
			} catch (Exception er)
			{

			}
		}
		IChiTieu obj = new ChiTieu(id, "1");
		obj.setUserId(userId);
		session.setAttribute("loaichitieu", loaict);
		String nhomkenh="";
		 nhomkenh=request.getParameter("nhomkenh");
		 if(nhomkenh!=null)
			 obj.setNhomkenh(nhomkenh);
		obj.CreateRs();
		session.setAttribute("userId", userId);
		String tenuser = gettenuser(userId);
		session.setAttribute("userTen", tenuser);
		session.setAttribute("obj", obj);
		String action = util.getAction(querystring);
		session.setAttribute("check", "0");
		if (action.equals("update"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuUpdate.jsp";// default
			response.sendRedirect(nextJSP);
		} else if (action.equals("display"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuDisplay.jsp";// default
			response.sendRedirect(nextJSP);
		} else if (action.equals("excel"))
		{
			XuatFileExcel(response, id);
		} else if (action.equals("excelSR"))
		{
			XuatFileExcelSR(response, id);
		} else
		{

		}

	}

	private void XuatFileExcelSR(HttpServletResponse response, String id) throws IOException
	{

		OutputStream out1 = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=sampleName.xls");
			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Demo", 0);
			dbutils db = new dbutils();
			String sql = "select thang,nam,dvkd.diengiai,kbh.ten from chitieu_sec  ct inner join donvikinhdoanh dvkd on dvkd.pk_seq=dvkd_fk " + " inner join kenhbanhang kbh on kbh.pk_seq=kenh_fk " + " where ct.pk_seq=" + id;

			ResultSet rs = db.get(sql);
			if (rs.next())
			{

				sheet.addCell(new Label(0, 0, "CHI TIEU  THANG" + rs.getString("thang") + "-" + rs.getString("nam")));
				sheet.addCell(new Label(0, 2, "DVKD:" + rs.getString("diengiai")));
				sheet.addCell(new Label(0, 3, "KENH:" + rs.getString("ten")));

				sheet.addCell(new Label(0, 5, "MA DDKD"));
				sheet.addCell(new Label(1, 5, "TEN DDKD"));
				sheet.addCell(new Label(2, 5, "CHUC VU"));
				sheet.addCell(new Label(3, 5, "SELLS OUT"));
				sheet.addCell(new Label(4, 5, " DO PHU"));
				sheet.addCell(new Label(5, 5, " DONHANG"));
				sheet.addCell(new Label(6, 5, " SANLUONG DONHANG"));

				sql = "select distinct nhomsp_fk from CHITIEUSEC_NHAPP_NHOMSP where chitieusec_fk=" + id;

				rs = db.get(sql);
				String chuoingoac = "";
				String[] chuoi = new String[20];
				int i = 0;
				while (rs.next())
				{

					//
					// sheet.addCell(new Label(6+i, 4,
					// rs.getString("tennhom")));

					sheet.addCell(new Label(7 + i, 5, rs.getString("nhomsp_fk")));
					chuoi[i] = rs.getString("nhomsp_fk");

					if (chuoingoac.equals(""))
					{
						chuoingoac = "[" + rs.getString("nhomsp_fk") + "]";

					} else
					{
						chuoingoac = chuoingoac + ",[" + rs.getString("nhomsp_fk") + "]";

					}
					i++;

				}
				rs.close();

				sql = " select * from  ( " + " select  ct.kenh_fk,ct.dvkd_fk,ct.thang,ct.nam,ctnpp.sodonhang ,ctnpp.sku,ctnpp.dophu, ctnpp.chitieu as sellsout , "
						+ " ctnhom.nhomsanpham_fk,ctnhom.chitieu,  ddkd.pk_seq as nppid ,ddkd.ten as nppten,Isnull(ctnpp.SanLuongTrenDh,0) as  SanLuongTrenDh " + " from chitieunpp ct  inner join chitieunpp_ddkd ctnpp "
						+ " on ct.pk_seq=ctnpp.chitieunpp_fk  inner join CHITIEUNPP_DDKD_NHOMSP ctnhom on ctnpp.chitieunpp_fk=ctnhom.chitieunpp_fk "
						+ " and ctnpp.ddkd_fk=ctnhom.ddkd_fk  inner join daidienkinhdoanh ddkd on ddkd.pk_seq=ctnpp.ddkd_fk  "
						+ " inner join chitieu_sec a on a.dvkd_fk=ct.dvkd_fk and ct.thang=a.thang and ct.nam=a.nam and ct.kenh_fk=a.kenh_fk " + " where ct.trangthai<>2 and a.pk_seq= " + id
						+ " ) p  pivot  (  sum(chitieu)  for nhomsanpham_fk in (" + chuoingoac + ") " + " ) as chitieu ";

				System.out.println(sql);

				ResultSet rs1 = db.get(sql);
				Label label;
				Number number;
				int j = 6;
				if (rs1 != null)
					while (rs1.next())
					{

						label = new Label(0, j, rs1.getString("nppid"));
						sheet.addCell(label);

						label = new Label(1, j, rs1.getString("nppten"));
						sheet.addCell(label);
						label = new Label(2, j, "SR");
						sheet.addCell(label);

						number = new Number(3, j, rs1.getDouble("sellsout"));
						sheet.addCell(number);

						number = new Number(4, j, rs1.getDouble("dophu"));
						sheet.addCell(number);

						number = new Number(5, j, rs1.getDouble("sodonhang"));
						sheet.addCell(number);

						number = new Number(6, j, rs1.getDouble("SanLuongTrenDh"));
						sheet.addCell(number);

						for (int k = 0; k < i; k++)
						{
							number = new Number(7 + k, j, rs1.getDouble(chuoi[k]));
							sheet.addCell(number);
						}

						j++;
					}

				w.write();
				w.close();
			}
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.toString());
		} finally
		{
			if (out1 != null)
				out1.close();
		}
	}

	private void XuatFileExcel(HttpServletResponse response, String id) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=sampleName.xls");
			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Demo", 0);
			dbutils db = new dbutils();
			String sql = "select thang,nam,dvkd.diengiai,kbh.ten from chitieu_sec  ct inner join donvikinhdoanh dvkd on dvkd.pk_seq=dvkd_fk " + " inner join kenhbanhang kbh on kbh.pk_seq=kenh_fk " + " where ct.pk_seq=" + id;

			ResultSet rs = db.get(sql);
			if (rs.next())
			{

				sheet.addCell(new Label(0, 0, "CHI TIEU  THANG" + rs.getString("thang") + "-" + rs.getString("nam")));
				sheet.addCell(new Label(0, 2, "DVKD:" + rs.getString("diengiai")));
				sheet.addCell(new Label(0, 3, "KENH:" + rs.getString("ten")));

				sheet.addCell(new Label(0, 5, "MA NPP"));
				sheet.addCell(new Label(1, 5, "TEN NPP"));
				sheet.addCell(new Label(2, 5, "CHUC VU"));
				sheet.addCell(new Label(3, 5, "SELLS OUT"));
				sheet.addCell(new Label(4, 5, " DO PHU"));
				sheet.addCell(new Label(5, 5, " DONHANG"));
				sheet.addCell(new Label(6, 5, " SANLUONG DONHANG"));
				sql = "select distinct nhomsp_fk from CHITIEUSEC_NHAPP_NHOMSP where chitieusec_fk=" + id;

				rs = db.get(sql);
				String chuoingoac = "";
				String[] chuoi = new String[20];
				int i = 0;
				while (rs.next())
				{
					sheet.addCell(new Label(7 + i, 5, rs.getString("nhomsp_fk")));
					chuoi[i] = rs.getString("nhomsp_fk");

					if (chuoingoac.equals(""))
					{
						chuoingoac = "[" + rs.getString("nhomsp_fk") + "]";

					} else
					{
						chuoingoac = chuoingoac + ",[" + rs.getString("nhomsp_fk") + "]";

					}
					i++;

				}
				rs.close();

				sql = " select * from " + " ( " + " select  ct.kenh_fk,ct.dvkd_fk,ct.thang,ct.nam,ctnpp.sodonhang,isnull(ctnpp.sanluongtrendh,0) as sanluongtrendh ,ctnpp.sku,ctnpp.dophu, " + "ctnpp.chitieu as sellsout , "
						+ " ctnhom.nhomsp_fk,ctnhom.chitieu, " + " npp.pk_seq as nppid ,npp.ten as nppten " + " from chitieu_Sec ct " + " inner join chitieu_nhapp_sec ctnpp on ct.pk_seq=ctnpp.chitieu_sec_fk "
						+ " inner join CHITIEUSEC_NHAPP_NHOMSP ctnhom on ctnpp.chitieu_sec_fk=ctnhom.chitieusec_fk and ctnpp.nhapp_fk=ctnhom.npp_fk " + " inner join nhaphanphoi npp on npp.pk_seq=ctnpp.nhapp_fk "
						+ " where ct.pk_seq= " + id + " ) p " + " pivot " + " ( " + " sum(chitieu) " + " for nhomsp_fk in (" + chuoingoac + ") " + " ) as chitieu";

				System.out.println(sql);

				ResultSet rs1 = db.get(sql);
				Label label;
				Number number;
				int j = 6;
				if (rs1 != null)
					while (rs1.next())
					{

						label = new Label(0, j, rs1.getString("nppid"));
						sheet.addCell(label);

						label = new Label(1, j, rs1.getString("nppten"));
						sheet.addCell(label);
						label = new Label(2, j, "NPP");
						sheet.addCell(label);

						number = new Number(3, j, rs1.getDouble("sellsout"));
						sheet.addCell(number);

						number = new Number(4, j, rs1.getDouble("dophu"));
						sheet.addCell(number);

						number = new Number(5, j, rs1.getDouble("sodonhang"));
						sheet.addCell(number);

						number = new Number(6, j, rs1.getDouble("SanLuongTrenDh"));
						sheet.addCell(number);

						for (int k = 0; k < i; k++)
						{
							number = new Number(7 + k, j, rs1.getDouble(chuoi[k]));
							sheet.addCell(number);
						}

						j++;
					}

				w.write();
				w.close();
			}
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.toString());
		} finally
		{
			if (out1 != null)
				out1.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		dbutils db = new dbutils();
		IChiTieu chitieu = new ChiTieu();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();

		Utility util = new Utility();

		String action = util.antiSQLInspection(request.getParameter("action") == null ? "" : request.getParameter("action"));

		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");

			String userId = util.antiSQLInspection(multi.getParameter("userId"));
			chitieu.setUserId(userId);
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
				chitieu.setThang(thang);
			} catch (Exception er)
			{

			}
			try
			{
				int nam = Integer.parseInt(util.antiSQLInspection(multi.getParameter("nam")));
				chitieu.setNam(nam);
			} catch (Exception er)
			{

			}
			String id = util.antiSQLInspection(multi.getParameter("id"));
			try
			{

				chitieu.setID(Double.parseDouble(id));

			} catch (Exception err)
			{
				err.printStackTrace();
			}
			String dvkdid = util.antiSQLInspection(multi.getParameter("dvkdid"));
			chitieu.setDVKDID(dvkdid);
			String kbhid = util.antiSQLInspection(multi.getParameter("kbhid"));
			chitieu.setKenhId(kbhid);
			String songaylamviec = util.antiSQLInspection(multi.getParameter("songaylamviec"));
			chitieu.setSoNgayLamViec(songaylamviec);
			String ngayketthuc = util.antiSQLInspection(multi.getParameter("ngayketthuc"));
			chitieu.setNgayKetThuc(ngayketthuc);

			String diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
			chitieu.setDienGiai(diengiai);

			String loaichitieu = util.antiSQLInspection(multi.getParameter("loaichitieu"));
			chitieu.setLoaiChiTieu(loaichitieu);
			
			String nhomkenh = util.antiSQLInspection(multi.getParameter("nhomkenh"));
			chitieu.setNhomkenh(nhomkenh);


			chitieu.setKhuVucID("");
			chitieu.setNguoiSua(userId);
			chitieu.setNguoiTao(userId);
			chitieu.setNgayTao(this.getDateTime());
			chitieu.setNgaySua(this.getDateTime());

			String sql = "delete chitieutmp";
			db.update(sql);

			String filename = "C:\\java-tomcat\\data\\" + filenameu;
			if (filename.length() > 0)
			{
				// doc file excel
				File file = new File(filename);
				System.out.println("filename  " + file);
				Workbook workbook;
				int indexRow = 6;
				int j = 9;
				try
				{

					System.out.println(file);
					workbook = Workbook.getWorkbook(file);
					Sheet sheet = workbook.getSheet("npp");
					Cell[] cells = sheet.getRow(indexRow);

					String nhomspid = "";
					int dodai = 9;
					while (dodai < cells.length)
					{
						if (cells[dodai].getContents().trim().length() > 0)
						{
							dodai = dodai + 1;
						} else
						{
							break;
						}
					}
					System.out.println("Do Dai :" + dodai);

					while (j < dodai)
					{
						if (j == 9)
						{
							nhomspid = cells[j].getContents();
						} else
						{
							nhomspid = nhomspid + ";" + cells[j].getContents();
						}
						j++;
						System.out.println("nhomSpID : " + nhomspid);
					}
					chitieu.setChuoiNhomSp(nhomspid);
					String chuoi = "";
					for (j = 1; j < dodai - 8; j++)
					{
						chuoi = chuoi + "," + "manhom" + j;
					}
					System.out.println(chuoi);
					chitieu.setKhuVucID(chuoi);

					System.out.println("So Dong : " + sheet.getRows());
					for (int i = indexRow + 1; i < sheet.getRows(); i++)
					{
						cells = sheet.getRow(i);
						if (cells.length > 0)
						{
							if (cells[0].getContents().toString().length() > 0)
							{
								String values = "";
								for (j = 0; j < dodai; j++)
								{
									if (j != 1)
									{
										if (j == 0)
										{

											values = "'" + cells[j].getContents() + "'";
										} else if (j == 2)
										{
											values = values + ",'" + cells[j].getContents() + "'";
										} else
										{
											// truong hop cac so chi tieu thi
											// parse ra neu null(error),thi cho
											// ve 0;
											float sotmp = 0;
											try
											{
												sotmp = Float.parseFloat(cells[j].getContents().toString().replace(",", ""));
											} catch (Exception er)
											{
												er.printStackTrace();
											}
											values = values + ",'" + sotmp + "'";
										}
									}
								}
								sql = " insert into chitieutmp (ma,chucvu,chitieumuavao,chitieubanra,songaytonkhoquydinh,donhang,tylegiaohangthanhcong,tylechinhxactonkho" + chuoi + ")values(" + values + ")";
								
								if (!db.update(sql))
								{
									System.out.println(" Khong In sert Duoc : " + sql);
								}
								
							}
						}

					}
				
					// sau khi doc vao bang tam,se thuc hien lay du lieu ra

					if (id.equals("0"))
					{

						if (chitieu.SaveChiTieu_Sec())
						{
							// Thanh cong
							session.setAttribute("nam", 0);
							session.setAttribute("thang", 0);
							session.setAttribute("tumuc", "");
							session.setAttribute("toimuc", "");
							session.setAttribute("loaichitieu", "1");
							chitieu.setListChiTieu("", "1");
							session.setAttribute("obj", chitieu);
							String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieu.jsp";
							response.sendRedirect(nextJSP);

							System.out.println(chitieu.getMessage());
						} else
						{
							chitieu.CreateRs();
							System.out.println("Khong Thanh Cong");
							String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuNew.jsp";// default
							session.setAttribute("obj", chitieu);
							response.sendRedirect(nextJSP);
						}

					} else
					{
						if (chitieu.EditChiTieu_Sec())
						{
							// Thanh cong
							session.setAttribute("nam", 0);
							session.setAttribute("thang", 0);
							session.setAttribute("tumuc", "");
							session.setAttribute("toimuc", "");
							session.setAttribute("loaichitieu", "1");
							chitieu.setListChiTieu("", "1");
							session.setAttribute("obj", chitieu);
							String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieu.jsp";
							response.sendRedirect(nextJSP);

						} else
						{
							chitieu.CreateRs();
							String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuUpdate.jsp";// default
							session.setAttribute("obj", chitieu);
							response.sendRedirect(nextJSP);
						}
					}
				} catch (Exception er)
				{
					chitieu.setMessage("Exception:" + er.toString());
					er.printStackTrace();
				}
			}
		} else
		{
			String userId = util.antiSQLInspection(request.getParameter("userId"));
			chitieu.setUserId(userId);
			try
			{
				int thang = Integer.parseInt(util.antiSQLInspection(request.getParameter("thang")));
				chitieu.setThang(thang);
			} catch (Exception er)
			{

			}
			try
			{
				int nam = Integer.parseInt(util.antiSQLInspection(request.getParameter("nam")));
				chitieu.setNam(nam);
			} catch (Exception er)
			{

			}
			String id = util.antiSQLInspection(request.getParameter("id"));
			try
			{

				chitieu.setID(Double.parseDouble(id));

			} catch (Exception err)
			{
			}
			String dvkdid = util.antiSQLInspection(request.getParameter("dvkdid"));
			chitieu.setDVKDID(dvkdid);
			
			String kbhid = util.antiSQLInspection(request.getParameter("kbhid"));
			chitieu.setKenhId(kbhid);
			
			String songaylamviec = util.antiSQLInspection(request.getParameter("songaylamviec"));
			chitieu.setSoNgayLamViec(songaylamviec);
			
			String ngayketthuc = util.antiSQLInspection(request.getParameter("ngayketthuc"));
			chitieu.setNgayKetThuc(ngayketthuc);

			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			chitieu.setDienGiai(diengiai);

			String loaichitieu = util.antiSQLInspection(request.getParameter("loaichitieu"));
			chitieu.setLoaiChiTieu(loaichitieu);

			userId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
			chitieu.setUserId(userId);

			System.out.println("[KbhId]"+kbhid+"[DvkdId]"+dvkdid);
			
			chitieu.setKhuVucID("");
			chitieu.setNguoiSua(userId);
			chitieu.setNguoiTao(userId);
			chitieu.setNgayTao(this.getDateTime());
			chitieu.setNgaySua(this.getDateTime());

			/* Chi tieu Second */

			String loaict = "1";

			System.out.println("Action : " + action);
			if (action.equals("capnhatForm"))
			{
				if (chitieu.SaveChiTieu_Sec(request))
				{
					chitieu.setListChiTieu("", "1");
					session.setAttribute("obj", chitieu);
					String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieu.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					System.out.print("[Msg]"+chitieu.getMessage());
					String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuUpdate.jsp";// default
					chitieu.CreateRs();
					chitieu = new ChiTieu(id, loaict);
					session.setAttribute("obj", chitieu);
					response.sendRedirect(nextJSP);
				}
			} else
			{
				chitieu = new ChiTieu(id, loaict);
				session.setAttribute("userId", userId);
				session.setAttribute("check", "0");
				session.setAttribute("obj", chitieu);
				String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieuDisplay.jsp";// default
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
	}
}

package geso.traphaco.center.servlets.chitieu;

import geso.traphaco.center.beans.chitieu.IChiTieu;
import geso.traphaco.center.beans.chitieu.imp.ChiTieu;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class ChiTieuNewnppSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ChiTieuNewnppSvl()
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
		System.out.println("da vao day :--------------------------");
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
		IChiTieu obj = new ChiTieu(id, "2");
		obj.setUserId(userId);
		System.out.println("USERID"+userId);
		session.setAttribute("loaichitieu", loaict);
		obj.CreateRs();
		session.setAttribute("userId", userId);
		String tenuser = gettenuser(userId);
		session.setAttribute("userTen", tenuser);
		session.setAttribute("obj", obj);
		String action = util.getAction(querystring);
		session.setAttribute("check", "0");
		if (action.equals("update"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieunppUpdate.jsp";// default
			response.sendRedirect(nextJSP);
		} else if (action.equals("display"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieunppDisplay.jsp";// default
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
				int quy = Integer.parseInt(util.antiSQLInspection(multi.getParameter("Quy")));
				chitieu.setQuy(quy);

				System.out.println(":::::::::::::: quy "+quy);
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
			System.out.println("nhom kenh ::::::::::::::::::::::::::::::::::::"+nhomkenh);

			String nhapp = util.antiSQLInspection(multi.getParameter("nhapp"));
			chitieu.setNhapp(nhapp);
			
			chitieu.setKhuVucID("");
			chitieu.setNguoiSua(userId);
			chitieu.setNguoiTao(userId);
			chitieu.setNgayTao(this.getDateTime());
			chitieu.setNgaySua(this.getDateTime());

		

			String filename = "C:\\java-tomcat\\data\\" + filenameu;
			if (filename.length() > 0)
			{
				// doc file excel
				File file = new File(filename);
				System.out.println("filename  " + file);
				Workbook workbook;
				
					
				int flag=0;
				for(int v=0;v<3;v++)
				{
						String sql = "delete  from chitieutmp";
					db.update(sql);
				int indexRow = 5;
				int j = 11;
				try
				{

					System.out.println(file);
					workbook = Workbook.getWorkbook(file);
					Sheet sheet = null;
					if(v==0)
					 sheet = workbook.getSheet("T1");
					if(v==1)
						 sheet = workbook.getSheet("T2");
					if(v==2)
						sheet = workbook.getSheet("T3");
					
					Cell[] cells = sheet.getRow(indexRow);

					String nhomspid = "";
					int dodai = 11;
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
					String chuoi_ct="";
					while (j < dodai)
					{
						if (j == 11)
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
					for (j = 1; j < dodai - 10; j++)
					{
						chuoi = chuoi + "," + "manhom" + j;
						chuoi_ct = chuoi_ct + "," + "chitieu_manhom" + j;
					}
					System.out.println(chuoi);
					chitieu.setKhuVucID(chuoi+chuoi_ct);

					System.out.println("So Dong : " + sheet.getRows());
					
					
					// lay laoi chi tieu
					
				
					
					
					ArrayList<Integer> mang=new ArrayList<Integer>();
					int head=0;
					String valueschitieu="";
					cells = sheet.getRow(indexRow+1);
					if (cells.length > 0)
					{	
							for (j = 0; j < dodai; j++)
							{
								
								 if(j>2)
									{
										// truong hop cac so chi tieu thi
										// parse ra neu null(error),thi cho
										// ve 0;
										float sotmp = 0;
										try
										{
											//System.out.println("oaoaoaoaoao"+Integer.parseInt(cells[j].getContents().toString().replace(",", "")));
											valueschitieu+=","+Integer.parseInt(cells[j].getContents().toString().replace(",", ""));
											
										} catch (Exception er)
										{
											er.printStackTrace();
										}
										
									}
								
							}	
							
						
					}
					System.out.println("chuoi valueschitieu "+valueschitieu);
					//
					
					
					
					indexRow++;
					System.out.println("chuoi indexRow "+indexRow);
					for (int i = indexRow + 1; i < sheet.getRows(); i++)
					{
						cells = sheet.getRow(i);
						System.out.println("---------------------------------------"+indexRow);
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
											double sotmp = 0;
											try
											{
												System.out.println(":::::::::::::::::::::::::::::::"+cells[j].getContents().toString());
												sotmp = Double.parseDouble(cells[j].getContents().toString().replace(",", ""));
											} catch (Exception er)
											{
												er.printStackTrace();
											}
											values = values + "," + sotmp + "";
										}
									}
								}
								sql = " insert into chitieutmp (ma,chucvu,chitieubanra,chitieubanraSL,solanviengtham,socuahangmuahang,sodonhangtrongngay,giatridonhangtoithieu,SKU,giaohangthanhcong" + chuoi + ",chitieu_banra_FK,chitieu_barasl_FK,chitieu_solanviengtham,chitieu_socuahangmuahang,chitieu_sodonhangtrongngay,chitieu_giatridonhangtoithieu,chitieu_SKU,chitieu_tylegiaohangthanhcong"+chuoi_ct+" )values(" + values + valueschitieu +")";
								
								if (!db.update(sql))
								{
									System.out.println(" Khong In sert Duoc : " + sql);
								}
								System.out.println("  In sert Duoc : " + sql);
								
								
							}
						}

					}
				
					// sau khi doc vao bang tam,se thuc hien lay du lieu ra
				System.out.println("ID::::::::::::::"+id);
					if (id.equals("0"))
					{
						System.out.println("IDsave::::::::::::::"+id);
						
						if(!chitieu.SaveChiTieuNhanVien_Sec(v))
						{
							
							chitieu.CreateRs();
							System.out.println("Khong Thanh Cong");
							String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieunppNew.jsp";// default
							session.setAttribute("obj", chitieu);
							response.sendRedirect(nextJSP);
							return ;
						}
						else
							flag++;
						
						

					}
					else
					{
						System.out.println("IDEdit::::::::::::::"+id);
						
						if(!chitieu.EditChiTieu_NV_Sec(v))
						{
							
							chitieu.CreateRs();
							System.out.println("Khong Thanh Cong");
							String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieunppNew.jsp";// default
							session.setAttribute("obj", chitieu);
							response.sendRedirect(nextJSP);
							return ;
						}
						else
							flag++;
					}
				} catch (Exception er)
				{
					chitieu.setMessage("Exception:" + er.toString());
					er.printStackTrace();
				}
			}
				System.out.println("flag:::::::::::::::::::::::::::::::::::::"+flag);
			
				if (flag==3)
				{
					
					// Thanh cong
					session.setAttribute("nam", 0);
					session.setAttribute("thang", 0);
					session.setAttribute("tumuc", "");
					session.setAttribute("toimuc", "");
					session.setAttribute("loaichitieu", "2");
					chitieu.setListChiTieu("", "2");
					session.setAttribute("obj", chitieu);
					String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieunpp.jsp";
					response.sendRedirect(nextJSP);
					return;
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
				int Quy = Integer.parseInt(util.antiSQLInspection(request.getParameter("Quy")));
				System.out.println(":::::::::::::: quy "+Quy);
				chitieu.setQuy(Quy);
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
			
			
			String nhomkenh = request.getParameter("nhomkenh") == null ? "" : request.getParameter("nhomkenh");
			chitieu.setNhomkenh(nhomkenh);
			
			String nhapp = request.getParameter("nhapp") == null ? "" : request.getParameter("nhapp");
			chitieu.setNhapp(nhapp);
			System.out.println();

			System.out.println("[KbhId]"+kbhid+"[DvkdId]"+dvkdid);
			
			chitieu.setKhuVucID("");
			chitieu.setNguoiSua(userId);
			chitieu.setNguoiTao(userId);
			chitieu.setNgayTao(this.getDateTime());
			chitieu.setNgaySua(this.getDateTime());

			/* Chi tieu Second */

			String loaict = "1";

			if(action.equals("chotthang1"))
			{
				chitieu.chotthang(1);
				String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieunppUpdate.jsp";// default
				chitieu.CreateRs();
				chitieu = new ChiTieu(id, "2");
				session.setAttribute("obj", chitieu);
				response.sendRedirect(nextJSP);
				return;
			}
			if(action.equals("chotthang2"))
			{
				chitieu.chotthang(2);
				String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieunppUpdate.jsp";// default
				chitieu.CreateRs();
				chitieu = new ChiTieu(id, "2");
				session.setAttribute("obj", chitieu);
				response.sendRedirect(nextJSP);
				return;
			}
			if(action.equals("chotthang3"))
			{
				chitieu.chotthang(3);
				String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieunppUpdate.jsp";// default
				chitieu.CreateRs();
				chitieu = new ChiTieu(id, "2");
				session.setAttribute("obj", chitieu);
				response.sendRedirect(nextJSP);
				return;
			}
			System.out.println("Action : " + action);
			if (action.equals("capnhatForm"))
			{
				if (chitieu.SaveChiTieu_nhanvien(request))
				{
					chitieu.setListChiTieu("", "2");
					session.setAttribute("obj", chitieu);
					String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieunpp.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					System.out.print("[Msg]"+chitieu.getMessage());
					String nextJSP = "/TraphacoHYERP/pages/Center/ChiTieunppUpdate.jsp";// default
					chitieu.CreateRs();
					chitieu = new ChiTieu(id, "2");
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

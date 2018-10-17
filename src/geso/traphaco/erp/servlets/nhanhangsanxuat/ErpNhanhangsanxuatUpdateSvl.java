package geso.traphaco.erp.servlets.nhanhangsanxuat;
//
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.nhanhangsanxuat.IErpNhanhangList_Giay;
import geso.traphaco.erp.beans.nhanhangsanxuat.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhangsanxuat.ISanpham;
import geso.traphaco.erp.beans.nhanhangsanxuat.ISpDetail;
import geso.traphaco.erp.beans.nhanhangsanxuat.imp.DetailMeSp;
import geso.traphaco.erp.beans.nhanhangsanxuat.imp.ErpNhanhangList_Giay;
import geso.traphaco.erp.beans.nhanhangsanxuat.imp.ErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhangsanxuat.imp.Sanpham;
import geso.traphaco.erp.beans.nhanhangsanxuat.imp.SpDetail;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.log.SysoLogger;
import com.oreilly.servlet.MultipartRequest;

public class ErpNhanhangsanxuatUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private PrintWriter out;

	public ErpNhanhangsanxuatUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay(id);
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setNppId((String)session.getAttribute("nppId"));
			nhBean.setUserId(userId); // phai co UserId truoc khi Init

			String loaimh = util.antiSQLInspection(request.getParameter("loai"));
			if(loaimh == null){ 
				loaimh = "1";
			}
			nhBean.setLoaimh(loaimh);
			System.out.println("Loai: " + loaimh);

			String task = request.getParameter("task");
			if(task == null)
				task = "";
			System.out.println("TASK: " + task);
			if(task.equals("xoafilenew"))
			{
				PrintWriter out = response.getWriter();
				dbutils db = new dbutils();
				System.out.println("Xoa file");
				String query = "SELECT ISNULL(FILENAME,'') AS FILENAME FROM ERP_NHANHANG where pk_seq='" + id + "'";
				System.out.println("cau select: " + query);
				ResultSet rs = db.get(query);
				String filename = "";
				try
				{
					rs.next();
					filename = rs.getString("FILENAME");
				} catch (SQLException e)
				{
					out.write("0");
				}
				System.out.println("file name :" + filename);
				if (!this.deletefile("C:\\java-tomcat\\chungtunhanhangsanxuat\\" + filename))
				{
					out.write("LOI XOA FILE");
				} else
				{
					query = "update ERP_NHANHANG set FILENAME='' where pk_seq='" + id + "'";
					System.out.println(query);
					if (!db.update(query))
					{
						out.write("LOI CAP NHAT FILE =''" + query);
					}
					out.write("");
				}
			}
			else{
				nhBean.init();
				String nextJSP;

				if (request.getQueryString().indexOf("display") >= 0)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
				}

				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";	
				}

				session.setAttribute("nhBean", nhBean);			
				response.sendRedirect(nextJSP);
			}




		}
	}

	private boolean deletefile(String file)
	{
		System.out.println(file);
		File f1 = new File(file);
		boolean success = f1.delete();
		if (!success)
		{
			return false;
		} else
		{
			return true;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");

		String sum = (String) session.getAttribute("sum");
		Utility_Kho util_kho=new Utility_Kho();
		dbutils db=new dbutils();
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");

		String contentType = request.getContentType();
		String folderupload="C:\\java-tomcat\\chungtunhanhangsanxuat\\";
		

		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			session.setMaxInactiveInterval(30000);
			
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{
			
			//this.out = response.getWriter();
			IErpNhanhang_Giay nhBean;

			Utility util = new Utility();
			MultipartRequest multi = new MultipartRequest(request,folderupload, 20000000, "UTF-8");
			String id = util.antiSQLInspection(multi.getParameter("id"));

			if (id == null)
			{
				nhBean = new ErpNhanhang_Giay("");
			}
			else
			{
				nhBean = new ErpNhanhang_Giay(id);
			}

			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setNppId((String)session.getAttribute("nppId"));
			nhBean.setUserId(userId);



			String ngaygd = util.antiSQLInspection(multi.getParameter("ngaynhanhang"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();			
			nhBean.setNgaynhanhang(ngaygd);

			String khonhanhangdatid = util.antiSQLInspection(multi.getParameter("khonhanhangdatid"));
			if (khonhanhangdatid == null || khonhanhangdatid == "")
				khonhanhangdatid ="";
			nhBean.setKhonhanhangdat(khonhanhangdatid);
			
			String doituongnhanid = util.antiSQLInspection(multi.getParameter("congdoannhanhangId"));
			if (doituongnhanid == null || doituongnhanid == "")
				doituongnhanid ="";
			nhBean.setIdCongdoannhan(doituongnhanid);

			
			String istudong = util.antiSQLInspection(multi.getParameter("istudong"));
			if (istudong == null || istudong == "")
				istudong = "0";			
			nhBean.setIsTudong(istudong);

			/*String ngaychot = util.antiSQLInspection(request.getParameter("ngaychot"));
			if (ngaychot == null || ngaychot == "")
				ngaychot = this.getDateTime();			
			nhBean.setNgaychot(ngaychot);*/

			String khonhanId = util.antiSQLInspection(multi.getParameter("khonhanId"));
			if (khonhanId == null )
				khonhanId = "";			
			nhBean.setKhoNhanId(khonhanId);
			// kho chờ xử lý
			/*String khochoxulyId = util.antiSQLInspection(request.getParameter("khochoxulyId"));
			if (khochoxulyId == null )
				khochoxulyId = "";	*/		
			nhBean.setKhoChoXuLy(khonhanId);


			String loaikho = util.antiSQLInspection(multi.getParameter("loaikho"));
			if (loaikho == null )
				loaikho = "";			
			nhBean.setLoaikho(loaikho);

			String khachhangId = util.antiSQLInspection(multi.getParameter("khId"));
			if (khachhangId == null )
				khachhangId = "";			
			nhBean.setKhachhangId(khachhangId);


			String loaihh = util.antiSQLInspection(multi.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "";
			nhBean.setLoaihanghoa(loaihh);

			String sohoadon = util.antiSQLInspection(multi.getParameter("sohoadon"));
			if (sohoadon == null)
				sohoadon = "";
			nhBean.setSohoadon(sohoadon);

			String diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			nhBean.setDiengiai(diengiai);

			String soPO = util.antiSQLInspection(multi.getParameter("sopo"));
			if (soPO == null)
				soPO = "";
			nhBean.setDonmuahangId(soPO);

			String mahangmua = util.antiSQLInspection(multi.getParameter("mahangmua"));
			if (mahangmua == null)
				mahangmua = "";
			nhBean.setMahangmuaId(mahangmua);

			String dvth = util.antiSQLInspection(multi.getParameter("dvthId"));
			if (dvth == null)
				dvth = "";
			nhBean.setDvthId(dvth);

			String ndnId = util.antiSQLInspection(multi.getParameter("ndnId"));
			if (ndnId == null)
				ndnId = "";
			nhBean.setNdnId(ndnId);

			String ldnId = util.antiSQLInspection(multi.getParameter("ldnId"));
			if (ldnId == null)
				ldnId = "";
			nhBean.setLdnId(ldnId);

			String nccId = util.antiSQLInspection(multi.getParameter("nccId"));
			if (nccId == null)
				nccId = "";
			System.out.println("ma nha cung cap :"+nccId);
			nhBean.setNccId(nccId);


			String isNCCNK = util.antiSQLInspection(multi.getParameter("nccNK"));
			if (isNCCNK == null)
				isNCCNK = "";
			nhBean.setIsNCCNK(isNCCNK);

			String poId = util.antiSQLInspection(multi.getParameter("muahangfk_Id"));
			if (poId == null)
				poId = "";
			nhBean.setmuahang_fk(poId);


			String hoadon_Id = util.antiSQLInspection(multi.getParameter("sohoadonNCC"));
			if(hoadon_Id==null){
				hoadon_Id="";
			}
			nhBean.setHdNccId(hoadon_Id);

			String ghichu = util.antiSQLInspection(multi.getParameter("ghichu"));
			if(ghichu==null){
				ghichu="";
			}
			nhBean.setGhichu(ghichu);

			String tigia  = util.antiSQLInspection(multi.getParameter("tigia"));
			if(tigia==null){
				tigia="1";
			}
			nhBean.setTiia(tigia);

			String loaimh = util.antiSQLInspection(multi.getParameter("loaimh"));
			System.out.println("updatesvl loaimh "+loaimh);
			if (loaimh != null && loaimh != "")			
				nhBean.setLoaimh(loaimh);

			String loaisp = multi.getParameter("loaisanpham");
			if(loaisp==null)
				loaisp = "";
			nhBean.setLoaisanpham(loaisp);
			String idphepham_dupham = multi.getParameter("phepham_duphamid");
			if(idphepham_dupham==null)
				idphepham_dupham="";
			nhBean.setIdphepham_dupham(idphepham_dupham);
			String idcongdoan = multi.getParameter("congdoanId");
			if(idcongdoan==null)
				idcongdoan="";
			nhBean.setIdcongdoan(idcongdoan);

			// Luu lai san pham
			String[] muahang_fk = multi.getParameterValues("muahang_fk");
			String[] po = multi.getParameterValues("po");
			String[] spId = multi.getParameterValues("idhangmua");
			String[] spMa = multi.getParameterValues("mahangmua");
			String[] spTen = multi.getParameterValues("tenhangmua");
			String[] spDonvi = multi.getParameterValues("dvdl");
			String[] tilequydoi = multi.getParameterValues("tilequydoiDV");
			String[] loaispct = multi.getParameterValues("loaispct");


			String[] soluongnhan = multi.getParameterValues("soluongnhan");
			String[] hansudung = multi.getParameterValues("hansudung");
			String[] soluongdat = multi.getParameterValues("soluongdat");
			// thêm idmarquette
			String[] idmarquette = multi.getParameterValues("idmarquette");
			// thêm phần kiểm định để xác định sản phẩm nào là kiểm định
			String[] kiemdinhsp = multi.getParameterValues("kiemdinhsp");

			String[] dungsai = multi.getParameterValues("dungsai");
			String[] dongia = multi.getParameterValues("dongiaMua");

			String[] tiente = multi.getParameterValues("tiente");
			String[] tygiaquydoi = multi.getParameterValues("tygiaquydoi");
			String[] dongiaViet = multi.getParameterValues("dongiaViet");

			String[] soluongMaxNhan = multi.getParameterValues("soluongMaxNhan");
			String[] soluongdanhan = multi.getParameterValues("soluongdanhan");

			String action = multi.getParameter("action");
			System.out.println("action: "+action);


			
				Enumeration files = multi.getFileNames();
				String filename = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filename = multi.getFilesystemName(name);
				}

				if (filename == null || filename.trim().length() <= 0)
				{
					filename = multi.getParameter("congvan");
				}

				nhBean.setFilename(filename);
				System.out.println("1____READ EXCEL TOI DAY, FILE NAME......" + filename);

			





			if (action.equals("save")){

				String changeSoMe = multi.getParameter("theloai");
				if(changeSoMe  == null){
					changeSoMe = "";
				}


				if(changeSoMe.equals("changeSoMe")){
					List<ISanpham> spList = new ArrayList<ISanpham>();
					if (spMa != null)
					{
						ISanpham sanpham = null;
						int m = 0;
						while (m < spMa.length)
						{
							sanpham = new Sanpham(spId[m], spMa[m], spTen[m], soluongnhan[m].replaceAll(",", ""),
									hansudung[m], "", soluongdat[m].replaceAll(",", ""), spDonvi[m]);

							double tile_temp =1;
							try{
								tile_temp = Double.parseDouble(tilequydoi[m]);
							}catch(Exception e){
								tile_temp = 1;
							}
							sanpham.setTiLeQuyDoiDv(tile_temp);

							String tem = spId[m] + "." + muahang_fk[m]+m;

							// Phần này dành cho bảng thứ nhất trong popup
							String[] solo = multi.getParameterValues(tem + ".solo");
							String[] dongialo = multi.getParameterValues(tem + ".dongialo");
							String[] sothung = multi.getParameterValues(tem + ".sothung"); 
							String[] soluongmax = multi.getParameterValues(tem + ".soluongmax");
							String[] soluongtong = multi.getParameterValues(tem + ".soluongtong");
							String[] ngaysanxuat = multi.getParameterValues(tem + ".ngaysanxuat");
							String[] ngayhethan = multi.getParameterValues(tem + ".ngayhethan");


							List<DetailMeSp> listDetailMeSp = new ArrayList<DetailMeSp>();
							// Phần dành cho bảng thứ 2						
							if(loaihh.equals("0"))
							{
								if(solo != null)
								{
									List<ISpDetail> spConList = new ArrayList<ISpDetail>();
									ISpDetail spCon = null;
									int n = 0;

									while (n < sothung.length) // doi soluong thanh sothung
									{
										if (sothung[n] != "" && solo[n].trim() != "" )
										{

											spCon = new SpDetail(spMa[m], solo[n].trim(), sothung[n].replaceAll(",", ""),"", ngaysanxuat[n], ngayhethan[n]);
											if(dongialo != null)
												spCon.setDongiaLo(dongialo[n].replaceAll(",", ""));
											else
												spCon.setDongiaLo(dongia[m].replaceAll(",", ""));

											// set số lượng tổng
											spCon.setSoluong(soluongtong[n].replaceAll(",", ""));

											if(soluongmax != null)
												spCon.setSoluongmax(soluongmax[n].replaceAll(",", ""));
											spConList.add(spCon);
										}

										// chỗ này tạo ra N dòng chi tiết dựa trên số thùng

										// tạo 1 cái mặc định là 1 thùng.
										if(sothung[n].trim().length() == 0){
											sothung[n]= "1";
										}

										int sothung_int = Integer.parseInt(sothung[n].replaceAll(",", ""));

										// tạo sẵn 1 thùng có đặc điểm như vậy
										// theo nguyên tắc các thùng đầu tiên sẽ được làm tròn nếu lẻ,
										// thùng cuối thì
										double soluong_theothung  = Double.parseDouble(soluongtong[n].replaceAll(",", ""))/ (double) sothung_int ;
										long a = (long) Math.floor(soluong_theothung);

										DecimalFormat formatter = new DecimalFormat("###,###,###.###");
										for(int i=0; i< sothung_int ; i++){
											DetailMeSp temp  = new DetailMeSp();

											// đây là vị trí nhé
											temp.setKhuVuc("");
											temp.setMaThung(""+(i+1));
											temp.setMe("");
											temp.setSoLo(solo[n].trim());
											temp.setSoLuong(formatter.format(a));
											temp.setNgayHetHan(ngayhethan[n]);
											temp.setNgaySanXuat(ngaysanxuat[n]);

											listDetailMeSp.add(temp);
										}

										// tính lại thùng cuối cùng
										DetailMeSp temp  = listDetailMeSp.get(listDetailMeSp.size()-1);
										double last_thung  = Double.parseDouble(soluongtong[n].replaceAll(",", "")) - a*(sothung_int-1);
										temp.setSoLuong(formatter.format(last_thung));
										listDetailMeSp.set(listDetailMeSp.size()-1,temp);

										n++;
									}
									sanpham.setSpDetail(spConList);
									System.out.println("list san pham con l; "+spConList.size());
								}
							}

							// Tạo list số mẻ cách từ động
							sanpham.setListDetailMeSp(listDetailMeSp);

							sanpham.setDungsai(dungsai[m]);
							sanpham.setDongia(dongia[m].replaceAll(",", ""));
							sanpham.setSoPO(po[m]);
							sanpham.setTiente(tiente[m]);
							sanpham.setTigiaquydoi(tygiaquydoi[m].replaceAll(",", ""));
							sanpham.setDongiaViet(dongiaViet[m]);
							sanpham.setSoluongDaNhan(soluongdanhan[m]);
							sanpham.setSoluongMaxNhan(soluongMaxNhan[m]);
							sanpham.setloaisp(loaispct[m]);
							sanpham.setIdmarquette(idmarquette[m]);
							sanpham.setIsKiemDinh(kiemdinhsp[m]);
							if(muahang_fk!=null){
								sanpham.setMuahang_fk(muahang_fk[m]);
							}
							try{
								sanpham.setMuahang_fk(muahang_fk[m]);
							}catch(Exception er){

							}
							if(khonhanId != null)
							{
								sanpham.setKhonhanId(khonhanId);
							}
							spList.add(sanpham);

							m++; 
						}
					}
					nhBean.setSpList(spList);


					// sau đó redirect về trang cũ luôn
					nhBean.setLoaimh(loaimh);
					nhBean.createRs1();

					session.setAttribute("nhBean", nhBean);
					String nextJSP = "";

					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
					}

					response.sendRedirect(nextJSP);
					return;
				} else { // dùng để lưu lại hoặc update
					// thực sự là lưu cái này vào trong csdl nè
					List<ISanpham> spList = new ArrayList<ISanpham>();
					if (spMa != null)
					{
						ISanpham sanpham = null;
						int m = 0;
						while (m < spMa.length)
						{
							sanpham = new Sanpham(spId[m], spMa[m], spTen[m], soluongnhan[m].replaceAll(",", ""),
									hansudung[m], "", soluongdat[m].replaceAll(",", ""), spDonvi[m]);

							double tile_temp =1;
							try{
								tile_temp = Double.parseDouble(tilequydoi[m]);
							}catch(Exception e){
								tile_temp = 1;
							}
							sanpham.setTiLeQuyDoiDv(tile_temp);
							sanpham.setloaisp(loaispct[m]);

							String tem = spId[m] + "." + muahang_fk[m]+m;
							// Phần này dành cho bảng thứ nhất trong popup
							String[] solo = multi.getParameterValues(tem + ".solo");
							String[] dongialo = multi.getParameterValues(tem + ".dongialo");
							String[] sothung = multi.getParameterValues(tem + ".sothung"); 
							String[] soluongmax = multi.getParameterValues(tem + ".soluongmax");
							String[] soluongtong = multi.getParameterValues(tem + ".soluongtong");
							String[] ngaysanxuat = multi.getParameterValues(tem + ".ngaysanxuat");
							String[] ngayhethan = multi.getParameterValues(tem + ".ngayhethan");
							List<ISpDetail> spConList = new ArrayList<ISpDetail>();
							List<DetailMeSp> listDetailMeSp = new ArrayList<DetailMeSp>();

							if(solo != null)
							{

								ISpDetail spCon = null;
								int n = 0;

								while (n < sothung.length) // doi soluong thanh sothung
								{
									if ( solo[n].trim() != ""  )
									{

										if(!sanpham.getloaisp().equals("100000")){
											DetailMeSp temp  = new DetailMeSp();
											temp.setKhuVuc("0");
											temp.setMaThung("");
											temp.setMe("");
											temp.setSoLo(solo[n]);
											temp.setSoLuong(soluongtong[n].replaceAll(",", ""));
											temp.setNgayHetHan(ngayhethan[n]);
											temp.setNgaySanXuat(ngaysanxuat[n]);
											listDetailMeSp.add(temp);

										}


										spCon = new SpDetail(spMa[m], solo[n].trim(), sothung[n].replaceAll(",", ""),"", ngaysanxuat[n], ngayhethan[n]);
										if(dongialo != null)
											spCon.setDongiaLo(dongialo[n].replaceAll(",", ""));
										else
											spCon.setDongiaLo(dongia[m].replaceAll(",", ""));

										// set số lượng tổng
										spCon.setSoluong(soluongtong[n].replaceAll(",", ""));

										if(soluongmax != null)
											spCon.setSoluongmax(soluongmax[n].replaceAll(",", ""));
										spConList.add(spCon);
									}

									// chỗ này tạo ra N dòng chi tiết dựa trên số thùng

									// tạo 1 cái mặc định là 1 thùng.
									if(sothung[n].trim().length() == 0){
										sothung[n]= "1";
									}
									n++;

								}

							}


							sanpham.setSpDetail(spConList);


							tem = spId[m] + "." + muahang_fk[m]+m;



							// Phần dành cho bảng thứ 2						

							if(sanpham.getloaisp().equals("100000")){
								// Tạo list số mẻ bằng cách đọc từ web
								String[] mathungX = multi.getParameterValues(tem + ".mathungX");
								String[] khuvucX = multi.getParameterValues(tem+ ".khuvucX");
								String[] meX = multi.getParameterValues(tem + ".meX");
								String[] soluongX = multi.getParameterValues(tem+ ".soluongX");
								String[] soloX = multi.getParameterValues(tem+ ".soloX");
								String[] ngayhethanX = multi.getParameterValues(tem+ ".ngayhethanX");
								String[] ngaysanxuatX = multi.getParameterValues(tem+ ".ngaysanxuatX");


								// xử lý trường hợp kho mà không có khu
								if( khuvucX == null){
									khuvucX = new String[mathungX.length];
								}
								if(mathungX !=null){

									for(int i=0; i< mathungX.length; i++){
										DetailMeSp temp  = new DetailMeSp();
										// đây là vị trí
										// set mặc định cho nó
										if(khuvucX[i] == null){
											khuvucX[i] = "0";
										}
										temp.setKhuVuc(khuvucX[i]);
										temp.setMaThung(mathungX[i]);
										temp.setMe(meX[i]);
										temp.setSoLo(soloX[i]);
										temp.setSoLuong(soluongX[i].replaceAll(",", ""));
										temp.setNgayHetHan(ngayhethanX[i]);
										temp.setNgaySanXuat(ngaysanxuatX[i]);

										listDetailMeSp.add(temp);
									}
								}
							}
							sanpham.setListDetailMeSp(listDetailMeSp);

							sanpham.setDungsai(dungsai[m].replaceAll(",", ""));
							sanpham.setDongia(dongia[m].replaceAll(",", ""));

							sanpham.setTiente(tiente[m]);
							sanpham.setTigiaquydoi(tygiaquydoi[m].replaceAll(",", ""));
							sanpham.setDongiaViet(dongiaViet[m].replaceAll(",", ""));
							sanpham.setSoluongDaNhan(soluongdanhan[m].replaceAll(",", ""));
							sanpham.setSoluongMaxNhan(soluongMaxNhan[m].replaceAll(",", ""));

							sanpham.setMuahang_fk(muahang_fk[m]);
							sanpham.setSoPO(po[m]);
							sanpham.setIdmarquette(idmarquette[m]);
							sanpham.setIsKiemDinh(kiemdinhsp[m]);

							if(muahang_fk!=null){
								sanpham.setMuahang_fk(muahang_fk[m]);
							}
							try{
								sanpham.setMuahang_fk(muahang_fk[m]);
							}catch(Exception er){

							}
							if(khonhanId != null)
							{
								sanpham.setKhonhanId(khonhanId);
							}
							spList.add(sanpham);

							m++; 
						}
					}
					nhBean.setSpList(spList);



					if (id.length()==0 ) // tao moi
					{
						if (!nhBean.createNhanHang()) {
							nhBean.setLoaimh(loaimh);
							nhBean.createRs1();

							session.setAttribute("nhBean", nhBean);
							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
							response.sendRedirect(nextJSP);
						} else {



							IErpNhanhangList_Giay obj = new ErpNhanhangList_Giay();

							obj.setCongtyId((String) session.getAttribute("congtyId"));
							obj.setNppId((String)session.getAttribute("nppId"));
							obj.setUserId(userId);
							obj.setLoaimh(loaimh);
							obj.init("");
							nhBean.close();
							session.setAttribute("obj", obj);

							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuat.jsp";
							response.sendRedirect(nextJSP);
						}
					} else {
						if (!nhBean.updateNhanHang()) {
							nhBean.setLoaimh(loaimh);
							nhBean.createRs1();

							session.setAttribute("nhBean", nhBean);
							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
							response.sendRedirect(nextJSP);
						} else {

							IErpNhanhangList_Giay obj = new ErpNhanhangList_Giay();
							obj.setCongtyId((String) session.getAttribute("congtyId"));
							obj.setNppId((String)session.getAttribute("nppId"));
							obj.setUserId(userId);
							obj.setLoaimh(loaimh);
							obj.init("");
							nhBean.close();
							session.setAttribute("obj", obj);

							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuat.jsp";
							response.sendRedirect(nextJSP);
						}
					}
				}
			}else if(action.equals("suaSoHd"))
			{
				if (!nhBean.suaSoHoaDon())
				{
					nhBean.createRs();
					session.setAttribute("nhBean", nhBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSuaSoHoaDon_Giay.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpNhanhangList_Giay obj = new ErpNhanhangList_Giay();
					obj.setCongtyId((String)session.getAttribute("congtyId"));
					obj.setNppId((String)session.getAttribute("nppId"));
					obj.setUserId(userId);
					obj.setLoaimh(loaimh);
					obj.init("");
					nhBean.close();
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangtrongnuoc_Giay.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				if ( action.equals("changePO") || action.equals("changeLHH") )
				{
					nhBean.setSpList(new ArrayList<ISanpham>());
					if(action.equals("changeLHH"))
					{
						nhBean.setDonmuahangId("");
					}

					String nextJSP;

					nhBean.setLoaimh(loaimh);

					nhBean.createRs(); // khoi tao lai san pham

					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
					}

					session.setAttribute("nhBean", nhBean);
					response.sendRedirect(nextJSP);
				}
				else if(action.equals("changeKhoNhan"))
				{
					String nextJSP;
					if (id == null)
					{
						nhBean.setLoaimh(loaimh);
						nhBean.createRs();
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
					}else
					{
						nhBean.setKhoNhanId(khonhanId);
						nhBean.init();						
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
					}

					session.setAttribute("nhBean", nhBean);
					response.sendRedirect(nextJSP);

				}
				else if(action.equals("download"))
				{
						while (files.hasMoreElements())
						{
							String name = (String) files.nextElement();
							filename = multi.getFilesystemName(name);
						}

						if (filename == null || filename.trim().length() <= 0)
						{
							filename = multi.getParameter("congvan");
						}

						nhBean.setFilename(filename);
						System.out.println("1____READ EXCEL TOI DAY, FILE NAME......" + filename);
						
					// tai file chung tu ve
					String fileName1=filename;
					System.out.println("___Vao DOWNLOAD FILE...."+fileName1);
					String sms="";
					if (fileName1.trim().length() > 0)
					{
						try
						{
							FileInputStream fileToDownload = new FileInputStream(folderupload+ fileName1);
							ServletOutputStream output1 = response.getOutputStream();
							response.setContentType("application/octet-stream");

							System.out.println(fileName1);
							response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName1 + "\"");
							response.setContentLength(fileToDownload.available());
							int c;
							while ((c = fileToDownload.read()) != -1)
							{
								output1.write(c);
							}
							output1.flush();
							output1.close();
							fileToDownload.close();
							sms="Download thành công!";
						} catch (Exception e)
						{
							System.out.println("___Loi DOWNLOAD file: " + e.getMessage());
							sms="Lỗi download!";
						}
					  } 
					nhBean.setLoaimh(loaimh);
					nhBean.createRs1();
					nhBean.setMsg(sms);

					session.setAttribute("nhBean", nhBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
					response.sendRedirect(nextJSP);
					
					
				}
				else
				{
					String nextJSP;
					nhBean.setLoaimh(loaimh);
					nhBean.createRs();

					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
					}

					session.setAttribute("nhBean", nhBean);
					response.sendRedirect(nextJSP);
				}

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
}

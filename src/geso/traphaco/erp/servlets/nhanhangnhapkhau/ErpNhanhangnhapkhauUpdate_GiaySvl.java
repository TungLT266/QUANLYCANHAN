package geso.traphaco.erp.servlets.nhanhangnhapkhau;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.nhanhangnhapkhau.*;
import geso.traphaco.erp.beans.nhanhangnhapkhau.imp.*;
import geso.traphaco.erp.util.DinhDang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility_Kho;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhanhangnhapkhauUpdate_GiaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
	public ErpNhanhangnhapkhauUpdate_GiaySvl()
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
			
			out.println(userId);	
			
			if (userId.length() == 0){
				userId = util.antiSQLInspection(request.getParameter("userId"));
			}
			
			String id = util.getId(querystring);
			IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay(id);
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setUserId(userId); // phai co UserId truoc khi Init
			
			String loaimh = util.antiSQLInspection(request.getParameter("loai"));
			if(loaimh == null) loaimh = "1";
			nhBean.setLoaimh(loaimh);
			System.out.println("Loai: " + loaimh);
			
			nhBean.init();
			String nextJSP;
			
			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauDisplay_Giay.jsp";
			}
			else if(request.getQueryString().indexOf("hoantat") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHTDonMuaHangDisplay.jsp";
			}else if(request.getQueryString().indexOf("suaSoHd") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSuaSoHoaDon_Giay.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauUpdate_Giay.jsp";	
			}
			
			session.setAttribute("nhBean", nhBean);			
			response.sendRedirect(nextJSP);
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
			
			this.out = response.getWriter();
			IErpNhanhang_Giay nhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			if (id == null)
			{
				nhBean = new ErpNhanhang_Giay("");
			}
			else
			{
				nhBean = new ErpNhanhang_Giay(id);
			}
			
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setUserId(userId);
			
			
			
			String ngaygd = util.antiSQLInspection(request.getParameter("ngaynhanhang"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();			
			nhBean.setNgaynhanhang(ngaygd);
			

			String istudong = util.antiSQLInspection(request.getParameter("istudong"));
			if (istudong == null || istudong == "")
				istudong = "0";			
			nhBean.setIsTudong(istudong);
			
			/*String ngaychot = util.antiSQLInspection(request.getParameter("ngaychot"));
			if (ngaychot == null || ngaychot == "")
				ngaychot = this.getDateTime();			
			nhBean.setNgaychot(ngaychot);*/
			
			String khonhanId = util.antiSQLInspection(request.getParameter("khonhanId"));
			if (khonhanId == null )
				khonhanId = "";			
			nhBean.setKhoNhanId(khonhanId);
			
			// kho chờ xử lý
			String khochoxulyId = util.antiSQLInspection(request.getParameter("khochoxulyId"));
			if (khochoxulyId == null )
				khochoxulyId = "";			
			nhBean.setKhoChoXuLy(khochoxulyId);
			
			String loaikho = util.antiSQLInspection(request.getParameter("loaikho"));
			if (loaikho == null )
				loaikho = "";			
			nhBean.setLoaikho(loaikho);
			
			String khachhangId = util.antiSQLInspection(request.getParameter("khId"));
			if (khachhangId == null )
				khachhangId = "";			
			nhBean.setKhachhangId(khachhangId);
			
			String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "";
			nhBean.setLoaihanghoa(loaihh);
			
			String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
			if (sohoadon == null)
				sohoadon = "";
			nhBean.setSohoadon(sohoadon);
			
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			nhBean.setDiengiai(diengiai);
			
			String soPO = util.antiSQLInspection(request.getParameter("sopo"));
			if (soPO == null)
				soPO = "";
			nhBean.setDonmuahangId(soPO);
			
			String mahangmua = util.antiSQLInspection(request.getParameter("mahangmua"));
			if (mahangmua == null)
				mahangmua = "";
			nhBean.setMahangmuaId(mahangmua);
			
			String dvth = util.antiSQLInspection(request.getParameter("dvthId"));
			if (dvth == null)
				dvth = "";
			nhBean.setDvthId(dvth);
			
			String ndnId = util.antiSQLInspection(request.getParameter("ndnId"));
			if (ndnId == null)
				ndnId = "";
			nhBean.setNdnId(ndnId);
			
			String ldnId = util.antiSQLInspection(request.getParameter("ldnId"));
			if (ldnId == null)
				ldnId = "";
			nhBean.setLdnId(ldnId);
			
			String nccId = util.antiSQLInspection(request.getParameter("nccId"));
			if (nccId == null)
				nccId = "";
			System.out.println("ma nha cung cap :"+nccId);
			nhBean.setNccId(nccId);
			
			
			String isNCCNK = util.antiSQLInspection(request.getParameter("nccNK"));
			if (isNCCNK == null)
				isNCCNK = "";
			nhBean.setIsNCCNK(isNCCNK);
					
			String poId = util.antiSQLInspection(request.getParameter("muahangfk_Id"));
			if (poId == null)
				poId = "";
			nhBean.setmuahang_fk(poId);
								
			
			String hoadon_Id = util.antiSQLInspection(request.getParameter("sohoadonNCC"));
			if(hoadon_Id==null){
				hoadon_Id="";
			}
			nhBean.setHdNccId(hoadon_Id);
			
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if(ghichu==null){
				ghichu="";
			}
			nhBean.setGhichu(ghichu);
			
			String tigia  = util.antiSQLInspection(request.getParameter("tigia"));
			if(tigia==null){
				tigia="1";
			}
			nhBean.setTiia(tigia);
			
			String loaimh = util.antiSQLInspection(request.getParameter("loaimh"));
			System.out.println("updatesvl loaimh "+loaimh);
			if (loaimh != null && loaimh != "")			
			nhBean.setLoaimh(loaimh);
	
						
			// Luu lai san pham
			String[] muahang_fk = request.getParameterValues("muahang_fk");
			String[] po = request.getParameterValues("po");
			String[] spId = request.getParameterValues("idhangmua");
			String[] spMa = request.getParameterValues("mahangmua");
			String[] spTen = request.getParameterValues("tenhangmua");
			String[] spDonvi = request.getParameterValues("dvdl");
			String[] tilequydoi = request.getParameterValues("tilequydoiDV");
			
			String[] soluongnhan = request.getParameterValues("soluongnhan");
			String[] hansudung = request.getParameterValues("hansudung");
			String[] soluongdat = request.getParameterValues("soluongdat");
			// thêm idmarquette
			String[] idmarquette = request.getParameterValues("idmarquette");
			// thêm phần kiểm định để xác định sản phẩm nào là kiểm định
			String[] kiemdinhsp = request.getParameterValues("kiemdinhsp");
			
			String[] dungsai = request.getParameterValues("dungsai");
			String[] dongia = request.getParameterValues("dongiaMua");
			
			String[] tiente = request.getParameterValues("tiente");
			String[] tygiaquydoi = request.getParameterValues("tygiaquydoi");
			String[] dongiaViet = request.getParameterValues("dongiaViet");
			
			String[] soluongMaxNhan = request.getParameterValues("soluongMaxNhan");
			String[] soluongdanhan = request.getParameterValues("soluongdanhan");
			
			String action = request.getParameter("action");
			if (action.equals("save")){
				
				String changeSoMe = request.getParameter("theloai");
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
							
							float tile_temp =1;
							try{
								tile_temp = Float.parseFloat(tilequydoi[m]);
							}catch(Exception e){
								tile_temp = 1;
							}
							sanpham.setTiLeQuyDoiDv(tile_temp);
							
							String tem = spId[m] + "." + muahang_fk[m]+m;
							
							// Phần này dành cho bảng thứ nhất trong popup
							String[] solo = request.getParameterValues(tem + ".solo");
							String[] dongialo = request.getParameterValues(tem + ".dongialo");
							String[] sothung = request.getParameterValues(tem + ".sothung"); 
							String[] soluongmax = request.getParameterValues(tem + ".soluongmax");
							String[] soluongtong = request.getParameterValues(tem + ".soluongtong");
							String[] ngaysanxuat = request.getParameterValues(tem + ".ngaysanxuat");
							String[] ngayhethan = request.getParameterValues(tem + ".ngayhethan");
							String[] nsxten = request.getParameterValues(tem + ".nsxten");
							String[] nsxid = request.getParameterValues(tem + ".nsxid");
							String[] marrquet = request.getParameterValues(tem + ".marrquet");
							
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
										if (sothung[n] != "" && solo[n].trim() != "" && ngayhethan[n] != "")
										{
											spCon = new SpDetail(spMa[m], solo[n].trim(), sothung[n].replaceAll(",", ""),"", ngaysanxuat[n], ngayhethan[n]);
											if(dongialo != null)
												spCon.setDongiaLo(dongialo[n].replaceAll(",", ""));
											else
												spCon.setDongiaLo(dongia[m].replaceAll(",", ""));
											
											// set số lượng tổng
											spCon.setSoluong(soluongtong[n].replaceAll(",", ""));
											spCon.setMarrquet(marrquet[n]);
											spCon.setNSXid(nsxid[n]);
											spCon.setNSXTen(nsxten[n]);
											if(soluongmax != null)
												spCon.setSoluongmax(soluongmax[n].replaceAll(",", ""));
											spConList.add(spCon);
										}
										
										// tạo 1 cái mặc định là 1 thùng.
										if(sothung[n].trim().length() == 0){
											sothung[n]= "1";
 										}
										
										// chỗ này tạo ra N dòng chi tiết dựa trên số thùng
										int sothung_int = Integer.parseInt(sothung[n].replaceAll(",", ""));
										
										// tạo sẵn 1 thùng có đặc điểm như vậy
										// theo nguyên tắc các thùng đầu tiên sẽ được làm tròn nếu lẻ,
										// thùng cuối thì
										double soluong_theothung  = Double.parseDouble(soluongtong[n].replaceAll(",", ""))/ (double) sothung_int ;
										long a = (long) Math.floor(soluong_theothung);
										
										//DecimalFormat formatter = new DecimalFormat("###,###,###.###");
										for(int i=0; i< sothung_int; i++){
											DetailMeSp temp  = new DetailMeSp();
											// Khu vực bây giờ thực sự là bin.
											temp.setKhuVuc("");
											temp.setMaThung(""+(i+1));
										
											
											// mac dinh me=0 va cho sua ---YEU CAU:28-10-2017
											temp.setMe("0");
											/*temp.setMe("");*/
											
											
											temp.setSoLo(solo[n].trim());
											temp.setSoLuong(DinhDang.dinhdangkho(a));
											temp.setNgayHetHan(ngayhethan[n]);
											temp.setNgaySanXuat(ngaysanxuat[n]);
											temp.setMarrquet(marrquet[n]);
											temp.setNSXid(nsxid[n]);
											temp.setNSXTen(nsxten[n]);
											listDetailMeSp.add(temp);
										}
										
										// tính lại thùng cuối cùng
										DetailMeSp temp  = listDetailMeSp.get(listDetailMeSp.size()-1);
										double last_thung  = Double.parseDouble(soluongtong[n].replaceAll(",", "")) - a*(sothung_int-1);
										temp.setSoLuong(DinhDang.dinhdangkho(last_thung));
										listDetailMeSp.set(listDetailMeSp.size()-1,temp);
										
										n++;
									}
									sanpham.setSpDetail(spConList);
									
								}
							}
							
							// Tạo list số mẻ cách từ động
							sanpham.setListDetailMeSp(listDetailMeSp);
							
							sanpham.setDungsai(dungsai[m].replaceAll(",", ""));
							sanpham.setDongia(dongia[m].replaceAll(",", ""));
							sanpham.setSoPO(po[m]);
							sanpham.setTiente(tiente[m]);
							sanpham.setTigiaquydoi(tygiaquydoi[m]);
							sanpham.setDongiaViet(dongiaViet[m]);
							sanpham.setSoluongDaNhan(soluongdanhan[m].replaceAll(",", ""));
							sanpham.setSoluongMaxNhan(soluongMaxNhan[m].replaceAll(",", ""));
							
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
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauNew_Giay.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauUpdate_Giay.jsp";
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
							
							float tile_temp =1;
							try{
								tile_temp = Float.parseFloat(tilequydoi[m]);
							}catch(Exception e){
								tile_temp = 1;
							}
							sanpham.setTiLeQuyDoiDv(tile_temp);
							
							String tem = spId[m] + "." + muahang_fk[m]+m;
							
							List<DetailMeSp> listDetailMeSp = new ArrayList<DetailMeSp>();
							// Phần dành cho bảng thứ 2						
							
							// Tạo list số mẻ bằng cách đọc từ web
							String[] mathungX = request.getParameterValues(tem + ".mathungX");
							String[] khuvucX = request.getParameterValues(tem+ ".khuvucX");
							String[] meX = request.getParameterValues(tem + ".meX");
							String[] soluongX = request.getParameterValues(tem+ ".soluongX");
							String[] soloX = request.getParameterValues(tem+ ".soloX");
							String[] ngayhethanX = request.getParameterValues(tem+ ".ngayhethanX");
							String[] ngaysanxuatX = request.getParameterValues(tem+ ".ngaysanxuatX");
							String[] nsxtenX = request.getParameterValues(tem + ".nsxtenX");
							String[] nsxidX = request.getParameterValues(tem + ".nsxidX");
							String[] marrquetX = request.getParameterValues(tem + ".marrquetX");
							
							// xử lý trường hợp kho mà không có khu
							if( khuvucX == null){
								khuvucX = new String[mathungX.length];
							}
							if(mathungX !=null){
								for(int i=0; i< mathungX.length; i++){
									DetailMeSp temp  = new DetailMeSp();
									// set mặc định cho nó
									// thực ra khu vực khai báo chỗ này là BIN nhé.
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
									temp.setMarrquet(marrquetX[i]);
									temp.setNSXid(nsxidX[i]);
									temp.setNSXTen(nsxtenX[i]);
									listDetailMeSp.add(temp);
								}
							}
							
							sanpham.setListDetailMeSp(listDetailMeSp);
							
							sanpham.setDungsai(dungsai[m].replaceAll(",", ""));
							sanpham.setDongia(dongia[m].replaceAll(",", ""));
							
							sanpham.setTiente(tiente[m]);
							sanpham.setTigiaquydoi(tygiaquydoi[m].replaceAll(",", ""));
							sanpham.setDongiaViet(dongiaViet[m]);
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
					
					
					
					if (id == null) // tao moi
					{
						if (!nhBean.createNhanHang()) {
							nhBean.setLoaimh(loaimh);
							nhBean.createRs();

							session.setAttribute("nhBean", nhBean);
							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauNew_Giay.jsp";
							response.sendRedirect(nextJSP);
						} else {

							// Do co dung sai nen ko the hoan tat don mua hang
							// tai cho nay ????????
							//nhBean.updateDonmuahang(nhBean.getDonmuahangId());

							IErpNhanhangList_Giay obj = new ErpNhanhangList_Giay();

							obj.setCongtyId((String) session.getAttribute("congtyId"));
							obj.setUserId(userId);
							obj.setLoaimh(loaimh);
							obj.init("");

							session.setAttribute("obj", obj);

							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhau_Giay.jsp";
							response.sendRedirect(nextJSP);
						}
					} else {
						if (!nhBean.updateNhanHang()) {
							nhBean.setLoaimh(loaimh);
							nhBean.createRs();

							session.setAttribute("nhBean", nhBean);
							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauUpdate_Giay.jsp";
							response.sendRedirect(nextJSP);
						} else {
							// Do co dung sai nen ko the hoan tat don mua hang
							// tai cho nay ???????
							nhBean.updateDonmuahang(nhBean.getDonmuahangId());

							IErpNhanhangList_Giay obj = new ErpNhanhangList_Giay();
							obj.setCongtyId((String) session.getAttribute("congtyId"));
							obj.setUserId(userId);
							obj.setLoaimh(loaimh);
							GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
							obj.init("");

							session.setAttribute("obj", obj);

							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhau_Giay.jsp";
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
					obj.setUserId(userId);
					obj.setLoaimh(loaimh);
					GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhau_Giay.jsp";
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
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauNew_Giay.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauUpdate_Giay.jsp";
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
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauNew_Giay.jsp";
					}else
					{
						nhBean.setKhoNhanId(khonhanId);
						nhBean.init();						
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauUpdate_Giay.jsp";
					}
					
					session.setAttribute("nhBean", nhBean);
					response.sendRedirect(nextJSP);
					
				}
				else
				{
					String nextJSP;
					nhBean.setLoaimh(loaimh);
					nhBean.createRs();
					
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauNew_Giay.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangnhapkhauUpdate_Giay.jsp";
					}
					
					session.setAttribute("nhBean", nhBean);
					response.sendRedirect(nextJSP);
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

package geso.traphaco.erp.servlets.nhanhangkhac;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.nhanhangkhac.*;
import geso.traphaco.erp.beans.nhanhangkhac.imp.*;
import geso.traphaco.erp.beans.nhanhangkhac.ISanpham;
import geso.traphaco.erp.beans.nhanhangkhac.ISpDetail;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.imp.DetailMeSp;
import geso.traphaco.erp.beans.nhanhangkhac.imp.Sanpham;
import geso.traphaco.erp.beans.nhanhangkhac.imp.SpDetail;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;

import java.io.IOException;
import java.io.PrintWriter;
 
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhanhangUpdate_khacSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
	public ErpNhanhangUpdate_khacSvl()
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
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpNhanhang_khac nhBean = new ErpNhanhang_khac(id);
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setUserId(userId); // phai co UserId truoc khi Init
		 
			nhBean.init();
			String nextJSP;
			
			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangDisplay_khac.jsp";
			}
		/*	else if(request.getQueryString().indexOf("hoantat") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHTDonMuaHangDisplay.jsp";
			}else if(request.getQueryString().indexOf("suaSoHd") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSuaSoHoaDon_khac.jsp";
			}*/
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_khac.jsp";	
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
			IErpNhanhang_khac nhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			if (id == null)
			{
				nhBean = new ErpNhanhang_khac("");
			}
			else
			{
				nhBean = new ErpNhanhang_khac(id);
			}
			 
			nhBean.setUserId(userId);
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
		
			
			
			String ngaygd = util.antiSQLInspection(request.getParameter("ngaynhanhang"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();			
			nhBean.setNgaynhanhang(ngaygd);
			
			
			String istudong = util.antiSQLInspection(request.getParameter("istudong"));
			if (istudong == null || istudong == "")
				istudong = "0";			
			nhBean.setIsTudong(istudong);
			
			String ngaychot = util.antiSQLInspection(request.getParameter("ngaychot"));
			if (ngaychot == null || ngaychot == "")
				ngaychot = this.getDateTime();
			
			nhBean.setNgaychot(ngaychot);
			
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
			System.out.println("Dien giai: "+diengiai);
			
			String soPO = request.getParameter("soPO");
			if (soPO == null)
				soPO = "";
			nhBean.setDonmuahangId(soPO);
			
			String mahangmua = request.getParameter("mahangmua");
			if (mahangmua == null)
				mahangmua = "";
			nhBean.setMahangmuaId(mahangmua);
			
			String dvth = request.getParameter("dvthId");
			if (dvth == null)
				dvth = "";
			nhBean.setDvthId(dvth);
			
			String ndnId = request.getParameter("ldnId");
			if (ndnId == null)
				ndnId = "";
			nhBean.setNdnId(ndnId);
			
			
			
			
			String nccId = request.getParameter("nccId");
			if (nccId == null)
				nccId = "";
			nhBean.setNccId(nccId);
			
			String ncpId = request.getParameter("ncpId");
			if (ncpId == null)
				ncpId = "";
			nhBean.setNhomchiphiId(ncpId);
			
			String isNCCNK = request.getParameter("nccNK");
			if (isNCCNK == null)
				isNCCNK = "";
			nhBean.setIsNCCNK(isNCCNK);
					
			String poId = request.getParameter("muahangfk_Id");
			if (poId == null)
				poId = "";
			nhBean.setmuahang_fk(poId);
					
			
			
			String hoadon_Id = request.getParameter("sohoadonNCC");
			if(hoadon_Id==null){
				hoadon_Id="";
			}
			nhBean.setHdNccId(hoadon_Id);
			
			String ghichu = request.getParameter("ghichu");
			if(ghichu==null){
				ghichu="";
			}
			nhBean.setGhichu(ghichu);
			
			String tigia  = request.getParameter("tigia");
			if(tigia==null){
				tigia="1";
			}
			nhBean.setTiia(tigia);
			
			String khonhanId  = request.getParameter("khonhanId");
			if (khonhanId == null ) khonhanId="";
			nhBean.setKhonhanId(khonhanId);
			String loaimh = util.antiSQLInspection(request.getParameter("loaimh"));
			if (loaimh == null || loaimh == "")			
			nhBean.setLoaimh(loaimh);
			
			String tongtien = util.antiSQLInspection(request.getParameter("tongthanhtien"));
			if (tongtien == null)
				tongtien="0";
			nhBean.setTongtien(tongtien.replace(",",""));
			
			
			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu="";
			nhBean.setSochungtu(sochungtu);
			
			
						
			// Luu lai san pham
			String[] muahang_fk = request.getParameterValues("muahang_fk");
			String[] spId = request.getParameterValues("idhangmua");
			String[] spMa = request.getParameterValues("mahangmua");
			String[] spTen = request.getParameterValues("tenhangmua");
			String[] spDonvi = request.getParameterValues("dvdl");
			String[] soluongnhan = request.getParameterValues("soluongnhan");
			String[] hansudung = request.getParameterValues("hansudung");
			String[] soluongdat = request.getParameterValues("soluongdat");
			String[] ngaynhandukien = request.getParameterValues("ngaynhandukien");
			String[] dungsai = request.getParameterValues("dungsai");
			String[] dongia = request.getParameterValues("dongia");
			
			String[] tiente = request.getParameterValues("tiente");
			String[] tygiaquydoi = request.getParameterValues("tygiaquydoi");
			String[] dongiaViet = request.getParameterValues("dongiaViet");
			
			String[] spKhoId = request.getParameterValues("khonhanId");
			String[] spKhoTen = request.getParameterValues("khonhanTen");
			String[] soluongMaxNhan = request.getParameterValues("soluongMaxNhan");
			String[] soluongdanhan = request.getParameterValues("soluongdanhan");
			String [] thanhtien=request.getParameterValues("thanhtien");
			
			
			String action = request.getParameter("action");
			if (action.equals("save"))
			{
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
														
							String tem = spId[m] + "." + muahang_fk[m]+m;
							
							// Phần này dành cho bảng thứ nhất trong popup
							String[] solo = request.getParameterValues(tem + ".solo");
							
							String[] sothung = request.getParameterValues(tem + ".sothung"); 
							String[] soluongmax = request.getParameterValues(tem + ".soluongmax");
							String[] soluongtong = request.getParameterValues(tem + ".soluongtong");
							String[] ngaysanxuat = request.getParameterValues(tem + ".ngaysanxuat");
							String[] ngayhethan = request.getParameterValues(tem + ".ngayhethan");
							
							String[] nsxId = request.getParameterValues(tem + ".nsxid");
							String[] nsxTen = request.getParameterValues(tem + ".nsx");
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
											
											spCon.setNsxId(nsxId[n]);
											spCon.setNsxTen(nsxTen[n]);
											spCon.setMarquette(marrquet[n]);
											spCon.setSothung(sothung[n]);
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
										
										DecimalFormat formatter = new DecimalFormat("###,###,###.####");
										for(int i=0; i< sothung_int ; i++){
											DetailMeSp temp  = new DetailMeSp();
											
											// đây là vị trí nhé
											temp.setKhuVuc("");
											temp.setMaThung(""+(i+1));
											// mac dinh me=0 va cho sua ---YEU CAU:28-10-2017
											temp.setMe("0");
											/*temp.setMe("");*/
											temp.setSoLo(solo[n].trim());
											temp.setSoLuong(formatter.format(a));
											temp.setNgayHetHan(ngayhethan[n]);
											temp.setNgaySanXuat(ngaysanxuat[n]);
											
											temp.setNsxId(nsxId[n]);
											temp.setNsxTen(nsxTen[n]);
											temp.setMarrquet(marrquet[n]);
											
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
									
								}
							}
							
							// Tạo list số mẻ cách từ động
							sanpham.setListDetailMeSp(listDetailMeSp);
							
							sanpham.setDungsai(dungsai[m]);
							sanpham.setDongia(dongia[m].replaceAll(",", ""));
							/*sanpham.setSoPO(po[m]);*/
							sanpham.setTiente(tiente[m]);
							sanpham.setTigiaquydoi(tygiaquydoi[m].replaceAll(",", ""));
							sanpham.setDongiaViet(dongiaViet[m]);
							sanpham.setSoluongDaNhan(soluongdanhan[m]);
							sanpham.setSoluongMaxNhan(soluongMaxNhan[m]);
							sanpham.setthanhtien(thanhtien[m]);
							
						/*	sanpham.setIdmarquette(idmarquette[m]);
							sanpham.setIsKiemDinh(kiemdinhsp[m]);*/
							if(muahang_fk!=null){
								sanpham.setMuahang_fk(muahang_fk[m]);
							}
							try{
								sanpham.setMuahang_fk(muahang_fk[m]);
							}catch(Exception er){
								
							}
							
													
							if(spKhoId != null)
							{
								sanpham.setKhonhanId(spKhoId[m]);
								sanpham.setKhonhanTen(spKhoTen[m]);
	 
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
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_khac.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_khac.jsp";
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
						/*sanpham = new Sanpham(spId[m], spMa[m], spTen[m], soluongnhan[m].replaceAll(",", ""), hansudung[m], ngaynhandukien[m], soluongdat[m].replaceAll(",", ""), spDonvi[m]);*/
						
						sanpham = new Sanpham(spId[m], spMa[m], spTen[m], soluongnhan[m].replaceAll(",", ""),
								hansudung[m], "", soluongdat[m].replaceAll(",", ""), spDonvi[m]);
						
						/*String tem = spId[m] + "." + ngaynhandukien[m] + "." + muahang_fk[m];
						
						String[] solo = request.getParameterValues(tem + ".solo");
						String[] soluong = request.getParameterValues(tem + ".soluong");
						String[] ngaysanxuat = request.getParameterValues(tem + ".ngaysanxuat");
						String[] ngayhethan = request.getParameterValues(tem + ".ngayhethan");
						
						String[] mame = request.getParameterValues(tem + ".mame");
						String[] mathung = request.getParameterValues(tem + ".mathung");
						String[] vitri = request.getParameterValues(tem + ".vitri");
						String[] maphieu = request.getParameterValues(tem + ".maphieu");
						String[] phieudt = request.getParameterValues(tem + ".phieudt");
						String[] phieueo = request.getParameterValues(tem + ".phieueo");
						String[] marquette = request.getParameterValues(tem + ".marquette");
						String[] hamluong = request.getParameterValues(tem + ".hamluong");
						String[] hamam = request.getParameterValues(tem + ".hamam");
						String[] vitriid = request.getParameterValues(tem + ".vitriid");
						String[] nsxid = request.getParameterValues(tem + ".NSXID");
						String[] nsxten = request.getParameterValues(tem + ".NSXTEN");
						
						
						
						
						String[] khuvuc_id = request.getParameterValues(tem + "khuvuc_id");
						if(loaihh.equals("0"))
						{
							List<ISpDetail> spConList = new ArrayList<ISpDetail>();
							ISpDetail spCon = null;
							int n = 0;
							while (n < soluong.length)
							{
								if (soluong[n] != "" && solo[n] != "" && ngaysanxuat[n] != "")
								{
									if(hamam[n]==null || hamam[n].trim().length()<=0)
										hamam[n]="0";
									if(hamluong[n]==null || hamluong[n].trim().length()<=0)
										hamluong[n]="100";
									spCon = new SpDetail(spMa[m], solo[n], soluong[n].replaceAll(",", ""), ngaysanxuat[n], ngayhethan[n],mame[n],mathung[n],vitri[n],maphieu[n],phieudt[n],phieueo[n],marquette[n],hamluong[n],hamam[n],vitriid[n].trim().length()<=0?null:vitriid[n]);
									spCon.setKhuId("");
									spCon.setNsxId(nsxid[n]);
									spCon.setNsxTen(nsxten[n]);
									if(khuvuc_id!=null){
										spCon.setKhuId(khuvuc_id[n]);
										sanpham.setKhoKhuRs(util_kho.getRsKhu(spKhoId[m], db));
										if(util_kho.getIsQuanLyKhuVuc(spKhoId[m], db).equals("1")){
											sanpham.setIskhoQL_khuvuc(true);
											nhBean.setIsKhoNhanQL_Khuvuc(true);
										}
									}
									spConList.add(spCon);
								}
								n++;
							}
							sanpham.setSpDetail(spConList);
						}
						
						sanpham.setDungsai(dungsai[m]);
						sanpham.setDongia(dongia[m]);
						
						sanpham.setTiente(tiente[m]);
						sanpham.setTigiaquydoi(tygiaquydoi[m]);
						sanpham.setDongiaViet(dongiaViet[m]);
						sanpham.setSoluongDaNhan(soluongdanhan[m]);
						sanpham.setSoluongMaxNhan(soluongMaxNhan[m]);
						sanpham.setthanhtien(thanhtien[m]==null?"0":thanhtien[m].replace(",",""));
					
						if(muahang_fk!=null){
							sanpham.setMuahang_fk(muahang_fk[m]);
						}
						
						try{
							sanpham.setMuahang_fk(muahang_fk[m]);
						}catch(Exception er){
							
						}
						
						if(spKhoId != null)
						{
							sanpham.setKhonhanId(spKhoId[m]);
							sanpham.setKhonhanTen(spKhoTen[m]);
 
						}
						
						spList.add(sanpham);
						
						m++; 
					}
				}

				nhBean.setSpList(spList);*/
						
						
														
							String tem = spId[m] + "." + muahang_fk[m]+m;
							
							// Phần này dành cho bảng thứ nhất trong popup
							String[] solo = request.getParameterValues(tem + ".solo");
							
							String[] sothung = request.getParameterValues(tem + ".sothung"); 
							String[] soluongmax = request.getParameterValues(tem + ".soluongmax");
							String[] soluongtong = request.getParameterValues(tem + ".soluongtong");
							String[] ngaysanxuat = request.getParameterValues(tem + ".ngaysanxuat");
							String[] ngayhethan = request.getParameterValues(tem + ".ngayhethan");
							
							String[] nsxId = request.getParameterValues(tem + ".nsxid");
							String[] nsxTen = request.getParameterValues(tem + ".nsx");
							String[] marrquet = request.getParameterValues(tem + ".marrquet");
							
							
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
											
											spCon.setNsxId(nsxId[n]);
											spCon.setNsxTen(nsxTen[n]);
											spCon.setMarquette(marrquet[n]);
											spCon.setSothung(sothung[n]);
											// set số lượng tổng
											spCon.setSoluong(soluongtong[n].replaceAll(",", ""));
											
											if(soluongmax != null)
												spCon.setSoluongmax(soluongmax[n].replaceAll(",", ""));
											spConList.add(spCon);
										}
										
					
										n++;
									}
									sanpham.setSpDetail(spConList);
									
								}
							}
						 tem = spId[m] + "." + muahang_fk[m]+m;
						
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
						String[] nsxtenX = request.getParameterValues(tem + ".nsxX");
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
								temp.setNsxId(nsxidX[i]);
								temp.setNsxTen(nsxtenX[i]);
								listDetailMeSp.add(temp);
							}
						}
						
						sanpham.setListDetailMeSp(listDetailMeSp);
						
						sanpham.setDungsai(dungsai[m]);
						sanpham.setDongia(dongia[m].replaceAll(",", ""));
						sanpham.setTiente(tiente[m]);
						sanpham.setTigiaquydoi(tygiaquydoi[m].replaceAll(",", ""));
						sanpham.setDongiaViet(dongiaViet[m]);
						sanpham.setSoluongDaNhan(soluongdanhan[m]);
						sanpham.setSoluongMaxNhan(soluongMaxNhan[m]);
						sanpham.setthanhtien(thanhtien[m]);
					
						if(muahang_fk!=null){
							sanpham.setMuahang_fk(muahang_fk[m]);
						}
						try{
							sanpham.setMuahang_fk(muahang_fk[m]);
						}catch(Exception er){
							
						}
						
												
						if(spKhoId != null)
						{
							sanpham.setKhonhanId(spKhoId[m]);
							sanpham.setKhonhanTen(spKhoTen[m]);
 
						}
						
						if(khonhanId != null)
						{
							sanpham.setKhonhanId(khonhanId);
						}
						spList.add(sanpham);
						
						m++; 
					}
					System.out.println("sp.getSpDetail().size() "+sanpham.getSpDetail().size());
				}
				nhBean.setSpList(spList);
				
				if (id == null) // tao moi
				{
					if (!nhBean.createNhanHang())
					{
						nhBean.setLoaimh(loaimh);
						nhBean.setIs_createRs("1");
						nhBean.createRs();		
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_khac.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{ 
						
						//Do co dung sai nen ko the hoan tat don mua hang tai cho nay ????????
						nhBean.updateDonmuahang(nhBean.getDonmuahangId());
						
						IErpNhanhangList_khac obj = new ErpNhanhangList_khac();
						 
						obj.setUserId(userId);
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setLoaimh(loaimh);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_khac.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if (!nhBean.updateNhanHang())
					{
						
						nhBean.setLoaimh(loaimh);
						nhBean.setIs_createRs("1");
						nhBean.createRs();
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_khac.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						//Do co dung sai nen ko the hoan tat don mua hang tai cho nay ???????
						nhBean.updateDonmuahang(nhBean.getDonmuahangId());
						
						IErpNhanhangList_khac obj = new ErpNhanhangList_khac();
					 
						obj.setUserId(userId);
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setLoaimh(loaimh);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_khac.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				}
			}
			else
			{
				String nextJSP;
				nhBean.setLoaimh(loaimh);
				
				nhBean.createRs();
				session.setAttribute("khonhanid", nhBean.getKhonhanId());
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_khac.jsp";
				}
				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_khac.jsp";
				}
				
				session.setAttribute("nhBean", nhBean);
				response.sendRedirect(nextJSP);
			}
			/*else if(action.equals("suaSoHd"))
			{
				if (!nhBean.suaSoHoaDon())
				{
					nhBean.createRs();
					session.setAttribute("nhBean", nhBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSuaSoHoaDon_khac.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpNhanhangList_khac obj = new ErpNhanhangList_khac();
					 
					obj.setUserId(userId);
					obj.setCongtyId((String)session.getAttribute("congtyId"));
					obj.setLoaimh(loaimh);
					
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_khac.jsp";
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
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_khac.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_khac.jsp";
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
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_khac.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_khac.jsp";
					}
					
					session.setAttribute("nhBean", nhBean);
					response.sendRedirect(nextJSP);
				}
				
			}*/
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}

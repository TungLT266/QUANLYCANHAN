package geso.traphaco.erp.servlets.nhanhang;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.nhanhang.*;
import geso.traphaco.erp.beans.nhanhang.imp.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility_Kho;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhanhangUpdate_GiaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
	public ErpNhanhangUpdate_GiaySvl()
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
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangDisplay_Giay.jsp";
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
				System.out.print("dang  day nedhfjgfd");
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_Giay.jsp";	
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
			String[] spId = request.getParameterValues("idhangmua");
			String[] spMa = request.getParameterValues("mahangmua");
			String[] spTen = request.getParameterValues("tenhangmua");
			String[] spDonvi = request.getParameterValues("dvdl");
			String[] soluongnhan = request.getParameterValues("soluongnhan");
			String[] hansudung = request.getParameterValues("hansudung");
			String[] soluongdat = request.getParameterValues("soluongdat");
			String[] ngaynhandukien = request.getParameterValues("ngaynhandukien");
			String[] dungsai = request.getParameterValues("dungsai");
			String[] dongia = request.getParameterValues("dongiaMua");
			
			String[] tiente = request.getParameterValues("tiente");
			String[] tygiaquydoi = request.getParameterValues("tygiaquydoi");
			String[] dongiaViet = request.getParameterValues("dongiaViet");
			
			String[] spKhoId = request.getParameterValues("khonhanIds");
			//String[] spKhoTen = request.getParameterValues("khonhanTen");
			String[] soluongMaxNhan = request.getParameterValues("soluongMaxNhan");
			String[] soluongdanhan = request.getParameterValues("soluongdanhan");
			
			String action = request.getParameter("action");
			if (action.equals("save"))
			{
				
				List<ISanpham> spList = new ArrayList<ISanpham>();
				
				if (spMa != null)
				{
					ISanpham sanpham = null;
					int m = 0;
					while (m < spMa.length)
					{
						sanpham = new Sanpham(spId[m], spMa[m], spTen[m], soluongnhan[m].replaceAll(",", ""), hansudung[m], ngaynhandukien[m], soluongdat[m].replaceAll(",", ""), spDonvi[m]);
						
						String tem = spId[m] + "." + ngaynhandukien[m] + "." + muahang_fk[m];
						
						String[] solo = request.getParameterValues(tem + ".solo");
						String[] sothung = request.getParameterValues(tem + ".sothung"); 
						String[] soluongthung = request.getParameterValues(tem + ".soluongthung"); 
						String[] dongialo = request.getParameterValues(tem + ".dongialo");
						String[] soluongmax = request.getParameterValues(tem + ".soluongmax");
						String[] ngaysanxuat = request.getParameterValues(tem + ".ngaysanxuat");
						String[] ngayhethan = request.getParameterValues(tem + ".ngayhethan");
						String[] khuvuc_id = request.getParameterValues(tem + "khuvuc_id");
						
						System.out.println("soluongmax " + soluongmax);
						if(loaihh.equals("0"))
						{
							if(solo != null)
							{
								List<ISpDetail> spConList = new ArrayList<ISpDetail>();
								ISpDetail spCon = null;
								int n = 0;
								
								
								while (n < sothung.length) // doi soluong thanh sothung
								{
									if (sothung[n] != "" && solo[n] != "" && ngaysanxuat[n] != "")
									{
										// so luong tong cua tat cac cac thung: soluong/thung * sothung
										Double[] sltong = new Double[5];
										System.out.println("so lo="+solo[n]);
										System.out.println("so  thung="+sothung[n]);
										System.out.println("so luong thung="+soluongthung[n]);
										Double soth=Double.parseDouble(sothung[n]);
										Double soluongth=Double.parseDouble(soluongthung[n]);
										sltong[n]=soth*soluongth;
										System.out.println("so  luong tong="+sltong[n]);
										String[]sltongs=new String[5];
										sltongs[n]=Double.toString(sltong[n]);
										System.out.println("so  luong tong s="+sltongs[n]);
										
										spCon = new SpDetail(spMa[m], solo[n], sothung[n].replaceAll(",", ""),sltongs[n], ngaysanxuat[n], ngayhethan[n]);
										if(dongialo != null)
											spCon.setDongiaLo(dongialo[n].replaceAll(",", ""));
										else
											spCon.setDongiaLo(dongia[m].replaceAll(",", ""));
										if(soluongmax != null)
											spCon.setSoluongmax(soluongmax[n]);
										spCon.setkhuid("");
										if(khuvuc_id!=null){
											spCon.setkhuid(khuvuc_id[n]);
											sanpham.setKhoKhuRs(util_kho.getRsKhu(khonhanId, db));
											if(util_kho.getIsQuanLyKhuVuc(khonhanId, db).equals("1")){
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
						}
						
						sanpham.setDungsai(dungsai[m]);
						sanpham.setDongia(dongia[m]);
						
						sanpham.setTiente(tiente[m]);
						sanpham.setTigiaquydoi(tygiaquydoi[m]);
						sanpham.setDongiaViet(dongiaViet[m]);
						sanpham.setSoluongDaNhan(soluongdanhan[m]);
						sanpham.setSoluongMaxNhan(soluongMaxNhan[m]);
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
							//sanpham.setKhonhanTen(spKhoTen[m]);
						}
						
						spList.add(sanpham);
						
						m++; 
					}
				}
				
				nhBean.setSpList(spList);
				
				if (id == null) // tao moi
				{
					if (!nhBean.createNhanHang())
					{
						nhBean.setLoaimh(loaimh);
						nhBean.createRs();
						
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_Giay.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{ 
						
						//Do co dung sai nen ko the hoan tat don mua hang tai cho nay ????????
						nhBean.updateDonmuahang(nhBean.getDonmuahangId());
						
						IErpNhanhangList_Giay obj = new ErpNhanhangList_Giay();
						
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setLoaimh(loaimh);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_Giay.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if (!nhBean.updateNhanHang())
					{
						nhBean.setLoaimh(loaimh);
						nhBean.createRs();
						
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_Giay.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						//Do co dung sai nen ko the hoan tat don mua hang tai cho nay ???????
						nhBean.updateDonmuahang(nhBean.getDonmuahangId());
						
						IErpNhanhangList_Giay obj = new ErpNhanhangList_Giay();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setLoaimh(loaimh);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_Giay.jsp";
						response.sendRedirect(nextJSP);
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
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_Giay.jsp";
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
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_Giay.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_Giay.jsp";
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
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_Giay.jsp";
					}else
					{
						nhBean.setKhoNhanId(khonhanId);
						nhBean.init();						
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_Giay.jsp";
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
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_Giay.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangUpdate_Giay.jsp";
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

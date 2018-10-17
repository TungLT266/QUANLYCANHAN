package geso.traphaco.distributor.servlets.dontrahang;

import geso.traphaco.distributor.beans.dontrahang.IErpNhaphangtrave;
import geso.traphaco.distributor.beans.dontrahang.IErpNhaphangtraveList;
import geso.traphaco.distributor.beans.dontrahang.imp.ErpNhaphangtrave;
import geso.traphaco.distributor.beans.dontrahang.imp.ErpNhaphangtraveList;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhaphangtraveUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
	public ErpNhaphangtraveUpdateSvl()
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
		} else
		{
			session.setMaxInactiveInterval(30000);
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
			Utility util = new Utility();
			
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpNhaphangtrave lsxBean = new ErpNhaphangtrave(id);
			lsxBean.setUserId(userId);
			lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
			lsxBean.setNpp_duocchon_id(npp_duocchon_id);
			
			lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			lsxBean.setDoituongId(session.getAttribute("doituongId"));
			
			String nextJSP = "";
			
			
			
			if (!querystring.contains("display")){
				lsxBean.init("update");
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVeUpdate.jsp";
			}else{
				lsxBean.init("display");
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVeDisplay.jsp";
			}
			session.setAttribute("lsxBean", lsxBean);
			
			session.setAttribute("kenhId", lsxBean.getKbhId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("khoId", lsxBean.getKhoXuatId());
			session.setAttribute("nppId",lsxBean.getNppId() );
			
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
			
			this.out = response.getWriter();
			IErpNhaphangtrave lsxBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
			{
				lsxBean = new ErpNhaphangtrave("");
			} else
			{
				
				lsxBean = new ErpNhaphangtrave(id);
			}
			
			lsxBean.setUserId(userId);
			lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
			lsxBean.setNpp_duocchon_id(npp_duocchon_id);
			lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			lsxBean.setDoituongId(session.getAttribute("doituongId"));
			
			String loaidonhang = request.getParameter("loaidonhang");
		    if(loaidonhang == null)
		    	loaidonhang = "";
		    lsxBean.setLoaidonhang(loaidonhang);
		    
			String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
			if (ngayyeucau == null || ngayyeucau.trim().length() <= 0)
				ngayyeucau = getDateTime();
			lsxBean.setNgayyeucau(ngayyeucau);
			
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
			lsxBean.setGhichu(ghichu);
			
			
			String sochungtu = util.antiSQLInspection( request.getParameter("sochungtu")==null? "":request.getParameter("sochungtu") );
			lsxBean.setSoChungTu(sochungtu);

			
			String khoxuatId = util.antiSQLInspection(request.getParameter("khoxuatId"));
			if (khoxuatId == null)
				khoxuatId = "";
			lsxBean.setKhoXuatId(khoxuatId);
			
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			lsxBean.setNppId(nppId);
			
			String khId = util.antiSQLInspection(request.getParameter("khId"));
			if (khId == null)
				khId = "";
			lsxBean.setKhId(khId);
			
			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";
			lsxBean.setKbhId(kbhId); 
			
			String trahangId = util.antiSQLInspection(request.getParameter("trahangId"));
			if (trahangId == null)
				trahangId = "";
			lsxBean.setTrahangId(trahangId);
			
			session.setAttribute("kenhId", lsxBean.getKbhId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("khoId", lsxBean.getKhoXuatId());
			session.setAttribute("nppId", request.getParameter("nppId"));
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] tonkho = request.getParameterValues("tonkho");
			lsxBean.setSpTonkho(tonkho);
			
			String[] dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);
			
			String[] spVat = request.getParameterValues("spVat");
			lsxBean.setSpVat(spVat);
			
			String action = request.getParameter("action");
			if(spMa != null )  //LUU LAI THONG TIN NGUOI DUNG NHAP
			{	
				HashMap<String, List<String>> sanpham_solo = new HashMap<String, List<String>>();
				HashMap<String, List<String>> sanpham_solo_ngayhethan = new HashMap<String, List<String>>();
				HashMap<String, List<String>> sanpham_sologoc = new HashMap<String, List<String>>();
				for(int i = 0; i < spMa.length; i++ )
				{
					String temID = spMa[i];
					
					// lưu lại số lô và ngày hết hạn 
					String[] spSOLO = request.getParameterValues("solo_"+temID);
					String[] spNgayHetHan = request.getParameterValues("ngayhethan_" +temID);
					String[] spSOLOGOC = request.getParameterValues("sologoc_"+temID);
					// thêm vào list số lô
					List<String> listsolo = new ArrayList<String>();
					
					if( spSOLO !=null){
						for(int j=0; j< spSOLO.length; j++ ){
							listsolo.add(spSOLO[j]);
						}
					}
					// thêm vào list ngày hết hạn
					List<String> listngayhethan = new ArrayList<String>();
					if(spNgayHetHan !=null){
						for(int j=0; j< spNgayHetHan.length; j++ ){
							listngayhethan.add(spNgayHetHan[j]);
						}
					}
					// thêm vào list số lô gốc
					List<String> listsologoc = new ArrayList<String>();
					if(spSOLOGOC !=null){
						for(int j= 0; j< spSOLOGOC.length; j++){
							listsologoc.add(spSOLOGOC[j]);
						}
						
					}
					sanpham_sologoc.put(temID, listsologoc);
					sanpham_solo.put(temID, listsolo);
					sanpham_solo_ngayhethan.put(temID, listngayhethan);
				}
				lsxBean.setSanpham_solo(sanpham_solo);
				lsxBean.setSanpham_solo_ngayhethan(sanpham_solo_ngayhethan);
				lsxBean.setSanpham_sologoc(sanpham_sologoc);
			}
		    
			
			// update lại số lô và ngày hết hạn
			if(trahangId.trim().length() >0 && spMa!= null){
				lsxBean.updateSoLo_NgayHetHan();
			}
			// mặc định số lượng đạt spSOlUONGNHAN_DAT = 100% so với số lượng
			// số lượng không đạt là mảng 0;
			
			lsxBean.setSpSoluongNHAN_DAT(soluong);
			
			if( soluong != null){
				String[] soluongkhongdat = new String[soluong.length];
				for(int i=0; i< soluongkhongdat.length; i++){
					soluongkhongdat[i] = "";
				}
				lsxBean.setSpSoluongNHAN_KHONGDAT(soluongkhongdat);
			}
			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!lsxBean.createNK( session.getAttribute("congtyId").toString() ))
					{
						lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
						lsxBean.setNpp_duocchon_id(npp_duocchon_id);
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVeNew.jsp";
						response.sendRedirect(nextJSP);
					} 
					else
					{
						IErpNhaphangtraveList obj = new ErpNhaphangtraveList();
						obj.setUserId(userId);
						obj.setLoaidonhang(loaidonhang);
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);
						
						obj.setLoainhanvien(session.getAttribute("loainhanvien"));
					    obj.setDoituongId(session.getAttribute("doituongId"));
						
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);
						
						response.sendRedirect("/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVe.jsp");
					}
				} 
				else
				{
					if (!lsxBean.updateNK( session.getAttribute("congtyId").toString() ))
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVeUpdate.jsp";
						response.sendRedirect(nextJSP);
					} 
					else
					{
						IErpNhaphangtraveList obj = new ErpNhaphangtraveList();
						obj.setUserId(userId);
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);
						obj.setLoaidonhang(loaidonhang);
						
						obj.setLoainhanvien(session.getAttribute("loainhanvien"));
					    obj.setDoituongId(session.getAttribute("doituongId"));
						
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVe.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} 
			else
			{
				if(action.equals("changePO"))
				{
					lsxBean.setSpMa(spMa);
				}
				
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVeNew.jsp";
				if (id != null)
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpNhapHangTraVeUpdate.jsp";
				
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

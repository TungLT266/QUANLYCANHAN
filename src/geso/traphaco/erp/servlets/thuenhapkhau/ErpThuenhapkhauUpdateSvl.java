package geso.traphaco.erp.servlets.thuenhapkhau;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thuenhapkhau.IErpHoadon;
import geso.traphaco.erp.beans.thuenhapkhau.IErpThuenhapkhau;
import geso.traphaco.erp.beans.thuenhapkhau.IErpThuenhapkhauList;
import geso.traphaco.erp.beans.thuenhapkhau.imp.ErpHoadon;
import geso.traphaco.erp.beans.thuenhapkhau.imp.ErpSanPhamNhapKhau;
import geso.traphaco.erp.beans.thuenhapkhau.imp.ErpThuenhapkhau;
import geso.traphaco.erp.beans.thuenhapkhau.imp.ErpThuenhapkhauList;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpThuenhapkhauUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpThuenhapkhauUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpThuenhapkhau tnkBean;
		  String loaimh = request.getParameter("loaimh");
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    tnkBean = new ErpThuenhapkhau(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    tnkBean.setLoaiMh(loaimh);
	    
	    tnkBean.setCongtyId(ctyId);
	    tnkBean.setId(id);
	    tnkBean.setUserId(userId);
	    tnkBean.setnppdangnhap(util.getIdNhapp(userId));
	    tnkBean.init();
        session.setAttribute("tnkBean", tnkBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauDisplay.jsp";
        }else if(querystring.indexOf("updateVAT") >= 0){
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauUpdateVAT.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpThuenhapkhau tnkBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("Id"));	
	    System.out.println("Id: " + id);
	    
	    if(id == null){  	
	    	tnkBean = new ErpThuenhapkhau();
	    }else{
	    	tnkBean = new ErpThuenhapkhau(id);
	    }
	    
	    //Lấy thông tin
	    getParameters(tnkBean, request, util, session);
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		String ctyId = (String)session.getAttribute("congtyId");
		String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if (nccId == null)
			nccId = "";

		//Lấy danh sách hóa đơn
		getHoaDon(tnkBean, request, util);
    	
		//Lấy danh sách hàng hóa
		getHangHoa(tnkBean, request);
		
		String action = request.getParameter("action");
		String loaimh = request.getParameter("loaimh");
		tnkBean.setLoaiMh(loaimh);
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(tnkBean.Create()))
// 				if (1==1)
				{
	 				tnkBean.createRs();
	 				session.setAttribute("tnkBean", tnkBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpThuenhapkhauList obj = new ErpThuenhapkhauList();
					
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.setLoaiMh(loaimh);
					
					
					obj.setnppdangnhap(util.getIdNhapp(userId));
					GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(tnkBean.Update()))
				{
 					tnkBean.createRs();
	 				session.setAttribute("tnkBean", tnkBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpThuenhapkhauList obj = new ErpThuenhapkhauList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.setLoaiMh(loaimh);
					
					obj.setnppdangnhap(util.getIdNhapp(userId));
					GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }else if(action.equals("saveVAT")){
			if (!(tnkBean.UpdateVAT()))
			{
				tnkBean.createRs();
 				session.setAttribute("tnkBean", tnkBean);  	
 	    		session.setAttribute("userId", userId);
 			
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauUpdateVAT.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				IErpThuenhapkhauList obj = new ErpThuenhapkhauList();
				obj.setCongtyId(ctyId);
				obj.setUserId(userId);
				obj.setLoaiMh(loaimh);
				obj.setnppdangnhap(util.getIdNhapp(userId));
				GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
				obj.init("");

				session.setAttribute("obj", obj);
			    
			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp";
				response.sendRedirect(nextJSP);
			}	    	
	    }
		else
		{
			//changeHoaDonNcc
			GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, tnkBean);
			tnkBean.setLoaiMh(loaimh);
			tnkBean.init();
			
			session.setAttribute("nccId", nccId);
			session.setAttribute("userId", userId);
			session.setAttribute("tnkBean", tnkBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}

	private void getParameters(IErpThuenhapkhau tnkBean, HttpServletRequest request, Utility util, HttpSession session) {
		tnkBean.setRequest(request);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tnkBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		tnkBean.setCongtyId(ctyId);
		
		tnkBean.setnppdangnhap(util.getIdNhapp(userId));
		 
		String cqt = util.antiSQLInspection(request.getParameter("cqt"));
		if (cqt == null)
			cqt = "";
		tnkBean.setCqt(cqt);

		String cqtId = util.antiSQLInspection(request.getParameter("cqtId"));
		if (cqtId == null)
			cqtId = "";
		tnkBean.setCqtId(cqtId);

		String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
		if (ngaychungtu == null)
			ngaychungtu = "";
		tnkBean.setNgaychungtu(ngaychungtu);

		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
		if (sochungtu == null)
			sochungtu = "";
		tnkBean.setSochungtu(sochungtu);
				
		String ncc = util.antiSQLInspection(request.getParameter("ncc"));
		if (ncc == null)
			ncc = "";
		tnkBean.setNcc(ncc);

		String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if (nccId == null)
			nccId = "";
		tnkBean.setNccId(nccId);

		String tigia = util.antiSQLInspection(request.getParameter("tigia"));
		if (tigia == null)
			tigia = "";
		tnkBean.setTigia(tigia);
		
		String po = util.antiSQLInspection(request.getParameter("po"));
		if (po == null)
			po = "";
		tnkBean.setPO(po);

		String poId = util.antiSQLInspection(request.getParameter("poId"));
		if (poId == null)
			poId = "";
		tnkBean.setPOId(poId);

		System.out.println("po: " + poId);
		
		String soHD = util.antiSQLInspection(request.getParameter("sohd"));
		if (soHD == null)
			soHD = "";
		tnkBean.setSoHD(soHD);

		String loaihinh = util.antiSQLInspection(request.getParameter("loaihinh"));
		System.out.println("Loai hinh: " + loaihinh);
		if (loaihinh == null)
			loaihinh = "";
		tnkBean.setLoaihinh(loaihinh);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tnkBean.setDiengiai(diengiai);
		
		String maHS = util.antiSQLInspection(request.getParameter("maHS"));
		if (maHS == null)
			maHS = "";
		tnkBean.setMaHS(maHS);
		
		String tienTeId = util.antiSQLInspection(request.getParameter("tienTeId"));
		if (tienTeId == null)
			tienTeId = "";
		tnkBean.setTienteId(tienTeId);
		
		String maTienTe = util.antiSQLInspection(request.getParameter("maTienTe"));
		if (maTienTe == null)
			maTienTe = "";
		tnkBean.setMaTienTe(maTienTe);
		
		System.out.println("tienTeId: " + tienTeId);
		System.out.println("maTienTe: " + maTienTe);
		
		String tienhang = util.antiSQLInspection(request.getParameter("tienhang").replace(",", ""));
		if (tienhang == null || tienhang.trim().length() == 0)
			tienhang = "0";
		else tienhang = tienhang.replace(",", "");
		tnkBean.setTienhang(tienhang);

		String ptThueNK = util.antiSQLInspection(request.getParameter("ptThueNK"));
		if (ptThueNK == null || ptThueNK.trim().length() == 0)
			ptThueNK = "0";
		else ptThueNK = ptThueNK.replace(",", "");
		
		tnkBean.setPtThueNK(ptThueNK);

		String thueNKTong = util.antiSQLInspection(request.getParameter("thueNKTong"));
		if (thueNKTong == null || thueNKTong.trim().length() == 0)
			thueNKTong = "0";
		else
			thueNKTong = thueNKTong.replace(",", "");
		tnkBean.setThueNK(thueNKTong);
		
		String ptVAT = util.antiSQLInspection(request.getParameter("ptVAT"));
		if (ptVAT == null || ptVAT.trim().length() == 0)
			ptVAT = "0";
		else
			ptVAT = ptVAT.replace(",", "");
		tnkBean.setPtVAT(ptVAT);

		String VAT = util.antiSQLInspection(request.getParameter("VAT"));
		if (VAT == null || VAT.trim().length() == 0)
			VAT = "0";
		else
			VAT = VAT.replace(",", "");
		tnkBean.setVAT(VAT);
	}

	private void getHoaDon(IErpThuenhapkhau tnkBean, HttpServletRequest request, Utility util) {
		String hoadonID = "";
		
		String[] pkseqsohoadon = request.getParameterValues("pkseqsohoadon");
		String[] sohoadon = request.getParameterValues("sohoadon");
		String[] ngayhoadon = request.getParameterValues("ngayhoadon");
		String[] soluongtra = request.getParameterValues("tongsoluong");
		String[] check = request.getParameterValues("hoadonID");
		String hoaDonNccId = util.antiSQLInspection(request.getParameter("hoadonID"));
		System.out.println("hoaDonNccId: " + hoaDonNccId);
		String checkID = "";
		if (check != null )
		{
			for(int i = 0; i < check.length; i++)
				checkID += check[i] + ",";
			
			if(checkID.trim().length() > 0)
			{
				checkID = checkID.substring(0, checkID.length() - 1);
			}
		}	

		System.out.println("checkID:" + checkID);
    	
		if(pkseqsohoadon != null)
		{		
	    	List<IErpHoadon> hdList = new ArrayList<IErpHoadon>();
	    	IErpHoadon hoadon = null;
	    	for(int i = 0 ; i < pkseqsohoadon.length ; i ++)
	    	{
	    		System.out.println("SO " + i);
	    		System.out.println("pkseqsohoadon:" + pkseqsohoadon[i]);
	    		System.out.println("checkID:" + checkID);
	    		System.out.println("sohoadon:" + sohoadon[i]);
	    		System.out.println("ngayhoadon:" + ngayhoadon[i]);
	    		System.out.println("soluongtra:" + soluongtra[i]);
	    		if(pkseqsohoadon[i] != null && checkID.contains(pkseqsohoadon[i])  && sohoadon[i] != "" && ngayhoadon[i] != "")
	    		{
					hoadon = new ErpHoadon();
					hoadon.setID(pkseqsohoadon[i]);
					hoadon.setSOHOADON(sohoadon[i]);
					hoadon.setNGAYHOADON(ngayhoadon[i]);
					hoadon.setSOLUONGTRA(soluongtra[i].replaceAll(",", ""));
				
					hdList.add(hoadon);
					
					hoadonID += pkseqsohoadon[i] + ",";						
				}
	    	}
	    	tnkBean.setHdList(hdList);
		}
		
		System.out.println("hoadonID: " + hoadonID);
		if(hoadonID.trim().length() > 0)
		{
			hoadonID = hoadonID.substring(0, hoadonID.length() - 1);
		}
		
		tnkBean.setHoadonIds(hoadonID);
	}

	private void getHangHoa(IErpThuenhapkhau tnkBean, HttpServletRequest request) {
		String[] maHangHoa = request.getParameterValues("maHangHoa");
		String[] idHangHoa = request.getParameterValues("idHangHoa");
		String[] loaiHangHoa = request.getParameterValues("loaiHangHoa");
		String[] hoaDonNccHHId = request.getParameterValues("hoaDonNccId");
		String[] dvt = request.getParameterValues("dvt");
		String[] soLo = request.getParameterValues("soLo");
		String[] soLuong = request.getParameterValues("soLuong");
		String[] donGiaVND = request.getParameterValues("donGiaVND");
		String[] donGiaNT = request.getParameterValues("donGiaNT");
		String[] thueSuat = request.getParameterValues("thueSuat");
		String[] thanhTien = request.getParameterValues("thanhTien");
		String[] tienTinhThueNhapKhau = request.getParameterValues("tienTinhThueNhapKhau");
		String[] thueSuatNK = request.getParameterValues("thueSuatNK");
		String[] thueHHNK = request.getParameterValues("thueNK");
		String[] phanTramVATNK = request.getParameterValues("phanTramVATNK");
		String[] VATNK = request.getParameterValues("VATNK");

		if(idHangHoa != null)
		{		
	    	List<ErpSanPhamNhapKhau> sanPhamList = new ArrayList<ErpSanPhamNhapKhau>();
	    	ErpSanPhamNhapKhau sp = null;
	    	for(int i = 0 ; i < idHangHoa.length ; i ++)
	    	{	    		
				sp = new ErpSanPhamNhapKhau();
				sp.setId(idHangHoa[i]);
				sp.setLoaiHangHoa(Integer.parseInt(loaiHangHoa[i].replaceAll(",", "")));
				sp.setTen(maHangHoa[i]);
				sp.setHoaDonNCCId(hoaDonNccHHId[i]);
				sp.setDonViTinh(dvt[i]);
				sp.setSoLo(soLo[i]);
				sp.setSoLuong(Double.parseDouble(soLuong[i].replaceAll(",", "")));
				sp.setDonGiaVND(Double.parseDouble(donGiaVND[i].replaceAll(",", "")));
				sp.setDonGiaNT(Double.parseDouble(donGiaNT[i].replaceAll(",", "")));
				sp.setThueSuat(Double.parseDouble(thueSuat[i].replaceAll(",", "")));
				sp.setThanhTien(Double.parseDouble(thanhTien[i].replaceAll(",", "")));
				sp.setTienTinhThueNhapKhau(Double.parseDouble(tienTinhThueNhapKhau[i].replaceAll(",", "")));
				sp.setThueSuatNK(Double.parseDouble(thueSuatNK[i].replaceAll(",", "")));
				sp.setThueNK(Double.parseDouble(thueHHNK[i].replaceAll(",", "")));
				sp.setPhanTramVATNK(Double.parseDouble(phanTramVATNK[i].replaceAll(",", "")));
				sp.setVATNK(Double.parseDouble(VATNK[i].replaceAll(",", "")));
			
				sanPhamList.add(sp);
	    	}
	    	tnkBean.setSanPhamList(sanPhamList);
		}
	}
}
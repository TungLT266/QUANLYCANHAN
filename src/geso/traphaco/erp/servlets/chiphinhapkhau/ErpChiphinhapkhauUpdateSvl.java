package geso.traphaco.erp.servlets.chiphinhapkhau;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.chiphinhapkhau.IErpChiphinhapkhau;
import geso.traphaco.erp.beans.chiphinhapkhau.IErpChiphinhapkhauList;
import geso.traphaco.erp.beans.chiphinhapkhau.ISanPhamPhanBo;
import geso.traphaco.erp.beans.chiphinhapkhau.imp.ErpChiphinhapkhau;
import geso.traphaco.erp.beans.chiphinhapkhau.imp.ErpChiphinhapkhauList;
import geso.traphaco.erp.beans.chiphinhapkhau.imp.SanPhamPhanBo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpChiphinhapkhauUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpChiphinhapkhauUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpChiphinhapkhau cpnkBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    cpnkBean = new ErpChiphinhapkhau(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    cpnkBean.setCongtyId(ctyId);
	    cpnkBean.setId(id);
	    cpnkBean.setUserId(userId);
	    
	    cpnkBean.init();
	    cpnkBean.createRs();
        session.setAttribute("cpnkBean", cpnkBean);
        
        String nextJSP ="";
       
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiNhapKhauDisplay.jsp";
        }
        else
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiNhapKhauUpdate.jsp";
        }
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpChiphinhapkhau cpnkBean;
		String ServerletName = this.getServletName();
		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	cpnkBean = new ErpChiphinhapkhau();
	    }else{
	    	cpnkBean = new ErpChiphinhapkhau(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		cpnkBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		cpnkBean.setCongtyId(ctyId);
		
		
		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if (ghichu == null)
			ghichu = "";
		cpnkBean.setGhichu(ghichu);
		
		String ngaynhap = util.antiSQLInspection(request.getParameter("ngaynhap"));
		if (ngaynhap == null)
			ngaynhap = "";
		cpnkBean.setNgaynhap(ngaynhap);
		
		String soChungTu_Chu = util.antiSQLInspection(request.getParameter("soChungTu_Chu"));
		if (soChungTu_Chu == null)
			soChungTu_Chu = "";
		cpnkBean.setSoChungTu_Chu(soChungTu_Chu);
		
		String soChungTu_So = util.antiSQLInspection(request.getParameter("soChungTu_So"));
		if (soChungTu_So == null)
			soChungTu_So = "";
		cpnkBean.setSoChungTu_So(soChungTu_So);
		
		String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if (nccId == null)
			nccId = "";
		cpnkBean.setNccId(nccId);

		String nccId_cn = util.antiSQLInspection(request.getParameter("nccId_cn"));
		if (nccId_cn == null)
			nccId_cn = "";

		cpnkBean.setNccId_cn(nccId_cn);
		
		String tienteId = util.antiSQLInspection(request.getParameter("tienteId"));
		if (tienteId == null)
			tienteId = "";
		cpnkBean.setTienteId(tienteId);
		
		String tigia = util.antiSQLInspection(request.getParameter("tigia"));
		if (tigia == null)
			tigia = "";
		cpnkBean.setTigia(tigia);
		
		String[] pnkIds = request.getParameterValues("pnkIds");
		String tmp = "";
		if (pnkIds != null){
			for(int i = 0; i < pnkIds.length; i++){
				tmp += pnkIds[i] + ",";
			}
		}
		if(tmp.length() > 0) tmp = tmp.substring(0, tmp.length() - 1);
		
		cpnkBean.setPnkIds(tmp);
		
		cpnkBean.loadSanPhamPhanBo();
		
		String tongtienhang = util.antiSQLInspection(request.getParameter("tongtienhang").replaceAll(",", ""));
		if (tongtienhang == null)
			tongtienhang = "";
		cpnkBean.setTongtienhang(tongtienhang);
		
		String tongtienthue = util.antiSQLInspection(request.getParameter("tongtienthue").replaceAll(",", ""));
		if (tongtienthue == null)
			tongtienthue = "";
		cpnkBean.setTongtienthue(tongtienthue);
		
		String tongtien = util.antiSQLInspection(request.getParameter("tongtien").replaceAll(",", ""));
		if (tongtien == null)
			tongtien = "";
		cpnkBean.setTongtien_AVAT(tongtien);
		
		String[] checknhanhang = request.getParameterValues("checknhanhang");
		//cpnkBean.setSochungtu(checknhanhang);
		String pnkIdstr="0";
		if(checknhanhang!=null){
			for(int i=0;i< checknhanhang.length;i++){
				pnkIdstr=pnkIdstr+","+checknhanhang[i] ;
			}
		}
		cpnkBean.setPnkId(pnkIdstr);
		String[] diengiai = request.getParameterValues("diengiai");
		cpnkBean.setDiengiai(diengiai);
		
		String[] mahoadon = request.getParameterValues("mahoadon");
		cpnkBean.setMaHD(mahoadon);
		
		String[] mausohoadon = request.getParameterValues("mausohoadon");
		cpnkBean.setMausoHD(mausohoadon);
		
		String[] kyhieu = request.getParameterValues("kyhieu");
		cpnkBean.setKyhieu(kyhieu);
		
		String[] sochungtu = request.getParameterValues("sochungtu");
		cpnkBean.setSochungtu(sochungtu);
		
		String[] ngaychungtu = request.getParameterValues("ngaychungtu");
		cpnkBean.setNgaychungtu(ngaychungtu);
		
		String[] nhacungcap = request.getParameterValues("nhacungcap");
		cpnkBean.setNhacungcap(nhacungcap);
		
		String[] diaChiNCC = request.getParameterValues("diaChiNCC");
		cpnkBean.setDiaChiNCC(diaChiNCC);
		
		String[] mst = request.getParameterValues("mst");
		cpnkBean.setMst(mst);

		String[] tienhang = request.getParameterValues("tienhang");
		cpnkBean.setTienhang(tienhang);
		
		String[] thuesuat = request.getParameterValues("thuesuat");
		cpnkBean.setThuesuat(thuesuat);
		
		String[] tienthue = request.getParameterValues("tienthue");
		cpnkBean.setTienthue(tienthue);
		
		String[] cong = request.getParameterValues("cong");
		cpnkBean.setTongtien(cong);
		
		//lấy số tiền phân bổ
		String[] masp = request.getParameterValues("masp");
		String[] nhanhangId = request.getParameterValues("nhanhangId");
//		String[] loaisp = request.getParameterValues("loaisp");
//		String[] tenSanPham = request.getParameterValues("tenSanPham");
//		String[] tienSanPham = request.getParameterValues("tienSanPham");
		String[] tienphanbo =  request.getParameterValues("phanboSanPham");
		String[] phantramSanPham = request.getParameterValues("phantramSanPham");
		String[] soloSanPham = request.getParameterValues("soloSanPham");
//		String[] idSP = request.getParameterValues("idSP");
		
		List<ISanPhamPhanBo> spList = cpnkBean.getSpList();
		spList.clear();
		
		double pb_total = 0;
		boolean error = false;
		String action = request.getParameter("action");
		if(tienphanbo != null){
			 for(int i = 0; i< tienphanbo.length; i++ ){
				 if(tienphanbo[i] != null ){
					 if(tienphanbo[i].trim() != "" && tienphanbo[i].trim() != "0"){
						 ISanPhamPhanBo sp = new SanPhamPhanBo();
						 sp.setManhanHang(nhanhangId[i]);
						 sp.setMaSp(masp[i]);
						 sp.setLoai("1");
						 sp.setPhanBo(Double.parseDouble(tienphanbo[i].replaceAll(",", "")));
						 sp.setSoLo(soloSanPham[i]);
						 sp.setPhanTram(Double.parseDouble(phantramSanPham[i].replaceAll(",", "")));
						 spList.add(sp);
						 
						 pb_total += Double.parseDouble(tienphanbo[i].replaceAll(",", ""));
					 }
				 }
			 }

			if(pb_total != Double.parseDouble(tongtienhang.replaceAll(",", ""))){
				if (action.equals("save")) 
					cpnkBean.setMsg("Tổng số tiền phân bổ vào sản phẩm phải bằng với Tổng tiền (chưa VAT). Vui lòng kiểm tra lại.");
				error = true;
			}
			 
		 }
		
		cpnkBean.setSpList(spList);
		
 		if(action.equals("save") & error == false)
 		{
 			if(id == null)
 			{
	 			if (!(cpnkBean.Create()))
				{
	 				cpnkBean.createRs();
	 				session.setAttribute("cpnkBean", cpnkBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiNhapKhauNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpChiphinhapkhauList obj = new ErpChiphinhapkhauList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiNhapKhau.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(cpnkBean.Update()))
				{
 					cpnkBean.createRs();
	 				session.setAttribute("cpnkBean", cpnkBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiNhapKhauUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpChiphinhapkhauList obj = new ErpChiphinhapkhauList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
					GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
					obj.init("");
	
					
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiNhapKhau.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			cpnkBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("cpnkBean", cpnkBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiNhapKhauNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiNhapKhauUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
}
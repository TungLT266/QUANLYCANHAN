package geso.traphaco.erp.servlets.phieuphache;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phieuphache.IErpPhieuPhaChe;
import geso.traphaco.erp.beans.phieuphache.IErpPhieuPhaCheList;
import geso.traphaco.erp.beans.phieuphache.IErpPhieuPhaChe_SanPham;
import geso.traphaco.erp.beans.phieuphache.imp.ErpPhieuPhaChe;
import geso.traphaco.erp.beans.phieuphache.imp.ErpPhieuPhaCheList;
import geso.traphaco.erp.beans.phieuphache.imp.ErpPhieuPhaChe_SP_ChiTiet;
import geso.traphaco.erp.beans.phieuphache.imp.ErpPhieuPhaChe_SanPham;
import geso.traphaco.erp.beans.phieuphache.imp.ThongTinNhapKho;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpPhieuPhaCheUpdateSvl
 */
@WebServlet("/ErpPhieuPhaCheUpdateSvl")
public class ErpPhieuPhaCheUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpPhieuPhaCheUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId == null || userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IErpPhieuPhaChe obj = new ErpPhieuPhaChe();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String Id = util.getId(querystring);
    	obj.setId(Id);
    	
    	obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuphacheNew.jsp";
	    if(querystring.indexOf("display") >= 0) {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuphacheDisplay.jsp";
        }
	    
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IErpPhieuPhaChe obj = new ErpPhieuPhaChe();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
	    
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null)
	    	id = "";
	    obj.setId(id);
	    
	    String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
		if (ngaychungtu == null)
			ngaychungtu = "";
		obj.setNgaychungtu(ngaychungtu);
		
		String thuocloai = util.antiSQLInspection(request.getParameter("thuocloai"));
		if (thuocloai == null)
			thuocloai = "";
		obj.setThuocloai(thuocloai);
	    
//	    String sanpham = util.antiSQLInspection(request.getParameter("sanpham"));
//		if (sanpham == null)
//			sanpham = "";
//		obj.setSanpham(sanpham);
		
		String sanphamid = util.antiSQLInspection(request.getParameter("sanphamid"));
		if (sanphamid == null)
			sanphamid = "";
		obj.setSanphamId(sanphamid);
		
		String spiskqlsl = util.antiSQLInspection(request.getParameter("spiskqlsl"));
		if (spiskqlsl == null)
			spiskqlsl = "";
		obj.setSpIsKqlsl(spiskqlsl);
		
		if(thuocloai.length() > 0){
			String kehoach = util.antiSQLInspection(request.getParameter("kehoach"));
			if (kehoach == null)
				kehoach = "";
			obj.setKehoach(kehoach);
			
			if(kehoach.length() > 0){
				String[] kehoachCt = request.getParameterValues("kehoach_ct");
				String kehoach_ct = "";
				
				if(kehoachCt != null && kehoachCt[0] != null && kehoachCt[0].length() > 0)
					kehoach_ct = kehoachCt[0];
				obj.setKehoachCt(kehoach_ct);
			}
		}
		
		String nguoiphache = util.antiSQLInspection(request.getParameter("nguoiphache"));
		if (nguoiphache == null)
			nguoiphache = "";
		obj.setNguoiphache(nguoiphache);
		
		String tailieuphache = util.antiSQLInspection(request.getParameter("tailieuphache"));
		if (tailieuphache == null)
			tailieuphache = "";
		obj.setTailieuphache(tailieuphache);
		
		String khonhap = util.antiSQLInspection(request.getParameter("khonhap"));
		if (khonhap == null)
			khonhap = "";
		obj.setKhonhap(khonhap);
		
		String soluongnhap = util.antiSQLInspection(request.getParameter("soluongnhap"));
		if (soluongnhap == null)
			soluongnhap = "";
		obj.setSoluongnhap(soluongnhap);
		
		List<ThongTinNhapKho> ThongTinNhapKhoList = new ArrayList<ThongTinNhapKho>();
		String[] solo = request.getParameterValues("solo");
		if(solo != null){
			String[] vitri = request.getParameterValues("vitri");
			String[] sothung = request.getParameterValues("sothung");
			String[] some = request.getParameterValues("some");
			String[] soluongchitiet = request.getParameterValues("soluongchitiet");
			ThongTinNhapKho ThongTinNhapKho;
			for(int i = 0; i < solo.length; i++){
				ThongTinNhapKho = new ThongTinNhapKho();
				
				ThongTinNhapKho.setSolo(solo[i]);
				ThongTinNhapKho.setVitri(vitri[i]);
				ThongTinNhapKho.setSothung(sothung[i]);
				ThongTinNhapKho.setSome(some[i]);
				ThongTinNhapKho.setSoluongchitiet(soluongchitiet[i]);
				
				ThongTinNhapKhoList.add(ThongTinNhapKho);
			}
		}
		obj.setThongTinNhapKhoList(ThongTinNhapKhoList);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		if(tailieuphache.length() > 0){
			if(tailieuphache.equals(util.antiSQLInspection(request.getParameter("tailieuphachecu")))){
				String[] masp = request.getParameterValues("masp");
				
				if(masp != null){
					String[] tlpctt = request.getParameterValues("tlpctt");
					String[] idsp = request.getParameterValues("idsp");
					String[] tensp = request.getParameterValues("tensp");
					String[] dvt = request.getParameterValues("dvt");
					String[] khoxuat = request.getParameterValues("khoxuat");
					String[] khoxuatcu;
					String[] sllythuyet = request.getParameterValues("sllythuyet");
					String[] tongxuat = request.getParameterValues("tongxuat");
					
					List<IErpPhieuPhaChe_SanPham> SanphamList = new ArrayList<IErpPhieuPhaChe_SanPham>();
					IErpPhieuPhaChe_SanPham Sanpham;
					for(int i = 0; i < masp.length; i++){
						Sanpham = new ErpPhieuPhaChe_SanPham();
						
						Sanpham.setTlpcTtId(tlpctt[i]);
						Sanpham.setIdsp(idsp[i]);
						Sanpham.setMasp(masp[i]);
						Sanpham.setTensp(tensp[i]);
						Sanpham.setDvt(dvt[i]);
						Sanpham.setKhoxuat(khoxuat[i]);
						Sanpham.setSoluonglythuyet(sllythuyet[i]);
						Sanpham.setTongxuat(tongxuat[i]);
						
						if(khoxuat[i].length() > 0){
							khoxuatcu = request.getParameterValues("khoxuatcu");
							
							if(khoxuat[i].equals(khoxuatcu[i])){
								List<ErpPhieuPhaChe_SP_ChiTiet> SpChitietList = new ArrayList<ErpPhieuPhaChe_SP_ChiTiet>();
								String[] solo_ = request.getParameterValues("solo_"+i);
								
								if(solo_ != null){
									String[] khoSpCt_ = request.getParameterValues("khott_sp_ct_"+i);
									String[] ngayhethan_ = request.getParameterValues("ngayhethan_"+i);
									String[] ngaynhapkho_ = request.getParameterValues("ngaynhapkho_"+i);
									String[] mame_ = request.getParameterValues("mame_"+i);
									String[] mathung_ = request.getParameterValues("mathung_"+i);
									String[] binfk_ = request.getParameterValues("binfk_"+i);
									String[] vitri_ = request.getParameterValues("vitri_"+i);
									String[] maphieu_ = request.getParameterValues("maphieu_"+i);
									String[] phieudt_ = request.getParameterValues("phieudt_"+i);
									String[] phieueo_ = request.getParameterValues("phieueo_"+i);
									String[] marq_ = request.getParameterValues("marq_"+i);
									String[] hamluong_ = request.getParameterValues("hamluong_"+i);
									String[] hamam_ = request.getParameterValues("hamam_"+i);
									String[] loaidoituong_ = request.getParameterValues("loaidoituong_"+i);
									String[] doituongid_ = request.getParameterValues("doituongid_"+i);
									String[] nhasanxuat_ = request.getParameterValues("nhasanxuat_"+i);
									String[] nsxid_ = request.getParameterValues("nsxid_"+i);
									String[] soluongton_ = request.getParameterValues("soluongton_"+i);
									String[] soluongchitiet_ = request.getParameterValues("soluongchitiet_"+i);
									
									ErpPhieuPhaChe_SP_ChiTiet SpChitiet;
									for(int j = 0; j < solo_.length; j++){
										SpChitiet = new ErpPhieuPhaChe_SP_ChiTiet();
										
										SpChitiet.setKhoSpCtId(khoSpCt_[j]);
										SpChitiet.setSolo(solo_[j]);
										SpChitiet.setNgayhethan(ngayhethan_[j]);
										SpChitiet.setNgaynhapkho(ngaynhapkho_[j]);
										SpChitiet.setMame(mame_[j]);
										SpChitiet.setMathung(mathung_[j]);
										SpChitiet.setBinFk(binfk_[j]);
										SpChitiet.setVitri(vitri_[j]);
										SpChitiet.setMaphieu(maphieu_[j]);
										SpChitiet.setMaphieudinhtinh(phieudt_[j]);
										SpChitiet.setPhieueo(phieueo_[j]);
										SpChitiet.setMarq(marq_[j]);
										SpChitiet.setHamluong(hamluong_[j]);
										SpChitiet.setHamam(hamam_[j]);
										SpChitiet.setLoaidoituong(loaidoituong_[j]);
										SpChitiet.setDoituongid(doituongid_[j]);
										SpChitiet.setNhasanxuat(nhasanxuat_[j]);
										SpChitiet.setNsxId(nsxid_[j]);
										SpChitiet.setSoluongton(soluongton_[j]);
										SpChitiet.setSoluongchitiet(soluongchitiet_[j]);
										
										SpChitietList.add(SpChitiet);
									}
								}
								Sanpham.setSpChitietList(SpChitietList);
							} else {
								Sanpham.createSpChitietList();
							}
						}
						
						SanphamList.add(Sanpham);
					}
					obj.setSanphamList(SanphamList);
				}
			} else {
				obj.createSanphamList();
			}
		}
		
		
	    if(action.equals("save")) {
	    	if(id.length() > 0){
	    		if(obj.update()){
	    			obj.DBClose();
	    			IErpPhieuPhaCheList objList = new ErpPhieuPhaCheList();
	    			
	    			objList.setUserId(userId);
	    			objList.setCongtyId(ctyId);
	    			objList.init();
	    		    
	    			session.setAttribute("obj", objList);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuphache.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			obj.createRs();
	    			
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuphacheNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	} else {
	    		if(obj.create()){
	    			obj.DBClose();
	    			IErpPhieuPhaCheList objList = new ErpPhieuPhaCheList();
	    			
	    			objList.setUserId(userId);
	    			objList.setCongtyId(ctyId);
	    			objList.init();
	    		    
	    			session.setAttribute("obj", objList);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuphache.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			obj.createRs();
	    			
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuphacheNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	}
	    } else {
	    	obj.createRs();
			
			session.setAttribute("obj", obj);
		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpPhieuphacheNew.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

}

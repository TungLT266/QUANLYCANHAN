package geso.traphaco.erp.servlets.dinhtinheo;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dinhtinheo.IDinhTinhEO;
import geso.traphaco.erp.beans.dinhtinheo.IDinhTinhEOList;
import geso.traphaco.erp.beans.dinhtinheo.imp.DinhTinhEO;
import geso.traphaco.erp.beans.dinhtinheo.imp.DinhTinhEODetail;
import geso.traphaco.erp.beans.dinhtinheo.imp.DinhTinhEOList;

import java.io.IOException;
import java.io.PrintWriter;
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

public class ErpDinhTinhEOUpdateSvl extends HttpServlet{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpDinhTinhEOUpdateSvl() 
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
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);  	
		    IDinhTinhEO obj = new DinhTinhEO();
		    obj.setId(id);
		    obj.setUserId(userId); 
		    
	        String nextJSP = "";
			if(request.getQueryString().indexOf("display") >= 0 ) {
				obj.initView();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDinhTinhEODisplay.jsp";
			}
			else{
				session.setAttribute("action", "update");
				obj.init("");
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDinhTinhEONew.jsp";
			}
	       
			 
	        session.setAttribute("obj", obj);
	        response.sendRedirect(nextJSP);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("Đã vào doPost của DinhTinhSvl");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IDinhTinhEO obj;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("::: ID: " + id );
		    	
			obj = new DinhTinhEO();
			obj.setId(id);
		
			obj.setUserId(userId);
		    
		    String loai = util.antiSQLInspection(request.getParameter("loai"));
		    if(loai == null || loai.trim().length() <= 0){
		    	loai = "0";
		    }
		    obj.setLoai(loai);
		    
		    // chỗ này được get nhiều
		   String[] tempj = request.getParameterValues("sanpham");
		   String sanPham = "";
		   if (tempj != null ){
				for(int i = 0; i < tempj.length; i++)
					sanPham += tempj[i] + ",";
				
				if(sanPham.trim().length() > 0)
				{
					sanPham = sanPham.substring(0, sanPham.length() - 1);
				}
			}
		    obj.setSanPham(sanPham);
		   
		    String khoId = request.getParameter("khoId");
		    if(khoId == null){
		    	khoId = "";
		    }
		    obj.setKhoId(khoId);
		    
		    String dienGiai = request.getParameter("diengiai");
		    if(dienGiai == null){
		    	dienGiai = "";
		    }
		    obj.setDienGiai(dienGiai);
		    
		    String ngayChungTu = request.getParameter("ngaychungtu");
		    if(ngayChungTu == null){
		    	ngayChungTu = "";
		    }
		    obj.setNgayChungTu(ngayChungTu);
		    
		    obj.setNgaySua(this.getDateTime());
		    
		    
			String[] sanpham_fk = request.getParameterValues("sanpham_fk");
			String[] solo = request.getParameterValues("solo");
			String[] mame = request.getParameterValues("mame");
			String[] mathung = request.getParameterValues("mathung");
			String[] bin_fk = request.getParameterValues("bin_fk");
			
			String[] ngaysanxuat = request.getParameterValues("ngaysanxuat");
			String[] ngayhethan = request.getParameterValues("ngayhethan");
			String[] ngaynhapkho = request.getParameterValues("ngaynhapkho");
			String[] hamam = request.getParameterValues("hamam");
			String[] hamluong = request.getParameterValues("hamluong");
			
			String[] marq = request.getParameterValues("marq");
			String[] khott_fk = request.getParameterValues("khott_fk");
			String[] ngaybatdau = request.getParameterValues("ngaybatdau");
			String[] maphieueo = request.getParameterValues("maphieueo");
			String[] maphieudinhtinh = request.getParameterValues("maphieudinhtinh");
			String[] maphieueoNew = request.getParameterValues("maphieueoNew");
			String[] maphieudinhtinhNew = request.getParameterValues("maphieudinhtinhNew");
			
			String[] maphieu = request.getParameterValues("maphieu");
			String[] khochitiet_fk = request.getParameterValues("khochitiet_fk");
			
			String[] nsxma = request.getParameterValues("nsxma");
			String[] nsx_fk = request.getParameterValues("nsx_fk");
			
		    // ghi nhận chi tiết
		    List<DinhTinhEODetail> list = new ArrayList<DinhTinhEODetail>();
		    
		    if(sanpham_fk !=null){
			    for(int i=0; i< sanpham_fk.length; i++){
			    	DinhTinhEODetail detail = new DinhTinhEODetail();
			    	detail.setKhott_fk(khott_fk[i]);
					detail.setSanPham_fk(sanpham_fk[i]);
					detail.setNgayHetHan(ngayhethan[i]);
					detail.setSoLo(solo[i]);
					detail.setBin_fk(bin_fk[i]);
					detail.setNgaySanXuat(ngaysanxuat[i]);
					detail.setNgayNhapKho(ngaynhapkho[i]);
					detail.setMaRQ(marq[i]);
					detail.setHamLuong(Double.parseDouble(hamluong[i].replaceAll(",", "")));
					detail.setHamAm(Double.parseDouble(hamam[i].replaceAll(",", "")));
					detail.setMaMe(mame[i]);
					detail.setMaThung(mathung[i].trim());
					detail.setMaPhieu(maphieu[i].trim());
					detail.setMaPhieuEO(maphieueo[i].trim());
					detail.setMaPhieuDinhTinh(maphieudinhtinh[i].trim());
					detail.setMaSanPham("");
					detail.setTenSanPham("");
					detail.setNgayBatDau(ngaybatdau[i]);
					detail.setKhoChiTiet_fk(khochitiet_fk[i]);
					
					if( maphieueoNew == null){
						detail.setMaPhieuEONew("");
						detail.setMaPhieuDinhTinhNew(maphieudinhtinhNew[i].trim());
					} else{
						detail.setMaPhieuDinhTinhNew("");
						detail.setMaPhieuEONew(maphieueoNew[i].trim());
					}
					
					detail.setNsxMa(nsxma[i].trim());
					detail.setNsx_fk(nsx_fk[i].trim());
					
					
					list.add(detail);
			    }
			    
		    	obj.setListDetail(list);
		    }
		    String action = request.getParameter("action");
		    
		    System.out.println("action: " + action + " - ID: " + id );
		    if (action.equals("getProduct"))
		    {
		    	
		    	// trường hợp không có sản phẩm nào thì xóa list cũ đi.
		    	List<DinhTinhEODetail> listT = new ArrayList<DinhTinhEODetail>();
		    	if(obj.getSanPham().trim().length() == 0){
		    		obj.setListDetail(listT);
		    	}
		    		
		    	obj.createRs();
		    	session.setAttribute("obj", obj);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDinhTinhEONew.jsp";
				response.sendRedirect(nextJSP);
		    	
		    }
		    else if(action.equals("save"))
		    {	
		    	obj.setNgayTao(this.getDateTime());
				if(id.length() ==0 )
				{
					if(!obj.create())
					{
						obj.createRs();
	    		    	session.setAttribute("obj", obj);
	    		    	session.setAttribute("action", "save");

	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDinhTinhEONew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IDinhTinhEOList obj1 = new DinhTinhEOList();
						obj1.setUserId(userId);

						obj1.init("");  
				    	session.setAttribute("obj", obj1);  	
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDinhTinhEO.jsp");
					}
				}
				else
				{
					if(!obj.update())
					{
						obj.createRs();
						session.setAttribute("action", "update");
						session.setAttribute("obj", obj);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDinhTinhEONew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IDinhTinhEOList obj1 = new DinhTinhEOList();
						obj1.setUserId(userId);

						obj1.init("");  
				    	session.setAttribute("obj", obj1);  	
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDinhTinhEO.jsp");
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

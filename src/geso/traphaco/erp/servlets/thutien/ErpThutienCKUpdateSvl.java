
package geso.traphaco.erp.servlets.thutien;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.thutien.IDinhkhoanco;
import geso.traphaco.erp.beans.thutien.IErpThutienCK;
import geso.traphaco.erp.beans.thutien.IErpThutienCKList;
import geso.traphaco.erp.beans.thutien.IHoadon;
import geso.traphaco.erp.beans.thutien.imp.Dinhkhoanco;
import geso.traphaco.erp.beans.thutien.imp.ErpThutienCK;
import geso.traphaco.erp.beans.thutien.imp.ErpThutienCKList;
import geso.traphaco.erp.beans.thutien.imp.Hoadon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpThutienCKUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	Runtime rt = Runtime.getRuntime();
	PrintWriter out;

    public ErpThutienCKUpdateSvl() {
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
		
			this.out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    String ctyId = (String)session.getAttribute("congtyId");
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    
		    String id = util.getId(querystring);  	
			IErpThutienCK tthdBean = new ErpThutienCK(id);
	        tthdBean.setCtyId(ctyId);
			tthdBean.setUserId(userId);			
			tthdBean.setnppdangnhap(util.getIdNhapp(userId));
			tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			tthdBean.setDoituongIddn(session.getAttribute("doituongId"));
	        
	        String nextJSP;
	        
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	tthdBean.initDisplay();
	        	//tthdBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCKDisplay.jsp";
	        }
	        else
	        {
	        	tthdBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCKUpdate.jsp";
	        }
	        System.out.println("tthdBean.getQueryNpp() :" +tthdBean.getQueryNpp());
	        session.setAttribute("queryNpp", tthdBean.getQueryNpp());
	        session.setAttribute("tthdBean", tthdBean);
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
			String ctyId = (String)session.getAttribute("congtyId");
			IErpThutienCK tthdBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
	       if(id == null)
		    {  	
		    	tthdBean = new ErpThutienCK("");
		    }
		    else
		    {
		    	tthdBean = new ErpThutienCK(id);
		    }

			//tthdBean = new ErpThutien(id);
			tthdBean.setCtyId(ctyId);
		    tthdBean.setUserId(userId);
		    tthdBean.setnppdangnhap(util.getIdNhapp(userId));
			
		    String nhomkhtt = util.antiSQLInspection(request.getParameter("nhomkhtt"));
		    if (nhomkhtt == null)
		    	nhomkhtt = "0";
			else
				nhomkhtt = "1";
		    tthdBean.setNhomkhtt(nhomkhtt);
		    
		    String nhomkhttId = util.antiSQLInspection(request.getParameter("nhomkhttId"));
			if (nhomkhttId == null)
				nhomkhttId = "";				
	    	tthdBean.setNhomkhttId(nhomkhttId);
	    	
		    String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
			if (ngaychungtu == null || ngaychungtu == "")
				ngaychungtu = geso.traphaco.center.util.Utility.getCurrentDate();	
	    	tthdBean.setNgaychungtu(ngaychungtu);
	    	
	    	String ngayghiso = util.antiSQLInspection(request.getParameter("ngayghiso"));
			if (ngayghiso == null || ngayghiso == "")
				ngaychungtu = geso.traphaco.center.util.Utility.getCurrentDate();			
	    	tthdBean.setNgayghiso(ngayghiso);
	    	
	    	String ischuyenCN = util.antiSQLInspection(request.getParameter("ischuyenCN"));
			if (ischuyenCN == null)
				ischuyenCN = "0";	
			else
				ischuyenCN = "1";	
			
			System.out.println("ischuyenCN:"+ischuyenCN);
	    	tthdBean.setIsChuyenCN(ischuyenCN);
	    	
	    	String isthuhoCN = util.antiSQLInspection(request.getParameter("isthuhoCN"));
			if (isthuhoCN == null)
				isthuhoCN = "0";	
			else
				isthuhoCN = "1";	
			
	    	tthdBean.setIsthuhoCN(isthuhoCN);
	    	System.out.println("setIsthuhoCN" +isthuhoCN);
	    	
	    	String bangkeId = util.antiSQLInspection(request.getParameter("bangkeId"));
			if (bangkeId == null)
				bangkeId = "";				
	    	tthdBean.setBangkeId(bangkeId);	    	
				    	
	    	String htttId = util.antiSQLInspection(request.getParameter("htttId"));
			if (htttId == null)
				htttId = "";				
	    	tthdBean.setHtttId(htttId);
	    	
	    	String noidungId = util.antiSQLInspection(request.getParameter("noidungId"));
			if (noidungId == null)
				noidungId = "";				
	    	tthdBean.setNoidungId(noidungId);
	    	
	    	String nganhangId = util.antiSQLInspection(request.getParameter("nganhangId"));
			if (nganhangId == null)
				nganhangId = "";				
	    	tthdBean.setNganhangId(nganhangId);
	    	
	    	String chinhanhId = util.antiSQLInspection(request.getParameter("chinhanhId"));
			if (chinhanhId == null)
				chinhanhId = "";				
	    	tthdBean.setNPPChinhanhId(chinhanhId);
	    	
	    	String sotaikhoan = util.antiSQLInspection(request.getParameter("sotaikhoan"));
	    	if(sotaikhoan == null)
	    		sotaikhoan = "";
	    	tthdBean.setSotaikhoan(sotaikhoan);
	    	
	    	String noidungthanhtoan = util.antiSQLInspection(request.getParameter("ghichu"));
	    	if(noidungthanhtoan == null)
	    		noidungthanhtoan = "";
	    	tthdBean.setNoidungtt(noidungthanhtoan);
	    	
	    	String ctkemtheo = util.antiSQLInspection(request.getParameter("ctkemtheo"));
	    	if(ctkemtheo == null)
	    		ctkemtheo = "";
	    	tthdBean.setChungTuKemTheo(ctkemtheo);
	    	
	    	String thuduoc = util.antiSQLInspection(request.getParameter("thuduoc"));
	    	if(thuduoc == null)
	    		thuduoc = "0";
	    	tthdBean.setThuduoc(thuduoc.replaceAll(",", ""));

	    	String thuduocNT = util.antiSQLInspection(request.getParameter("thuduocNT"));
	    	if(thuduocNT == null)
	    		thuduocNT = "0";
	    	tthdBean.setThuduocNT(thuduocNT.replaceAll(",", ""));

	    	String cpnganhang = util.antiSQLInspection(request.getParameter("cpnganhang"));
	    	if(cpnganhang == null)
	    		cpnganhang = "0";
	    	tthdBean.setChiphinganhang(cpnganhang.replaceAll(",", ""));

	    	String cpnganhangNT = util.antiSQLInspection(request.getParameter("cpnganhangNT"));
	    	if(cpnganhangNT == null)
	    		cpnganhangNT = "0";
	    	tthdBean.setChiphinganhangNT(cpnganhangNT.replaceAll(",", ""));

	    	String chenhlech = util.antiSQLInspection(request.getParameter("chenhlech"));
	    	if(chenhlech == null)
	    		chenhlech = "0";
	    	tthdBean.setChenhlech(chenhlech.replaceAll(",", ""));

	    	if(noidungId.equals("100001")){
	    		String tongVND = "" + (Double.parseDouble(thuduoc.replaceAll(",", "")) + Double.parseDouble(cpnganhang.replaceAll(",", "")));
	    		tthdBean.setTongVND(tongVND);
	    		
	    		String tongNT = "" + (Double.parseDouble(thuduocNT.replaceAll(",", "")) + Double.parseDouble(cpnganhangNT.replaceAll(",", "")));
	    		tthdBean.setTongNT(tongNT);
	    	}
	    	
	    	String sotienthanhtoan = util.antiSQLInspection(request.getParameter("sotienthanhtoan"));
	    	if(sotienthanhtoan == null)
	    		sotienthanhtoan = "0";
	    	tthdBean.setSotientt(sotienthanhtoan.replaceAll(",", ""));

	    	String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
	    	if(sochungtu == null)
	    		sochungtu = "";
	    	tthdBean.setSochungtu(sochungtu.toUpperCase());
	    	
	    	String soChungTu_Chu = util.antiSQLInspection(request.getParameter("soChungTu_Chu"));
	    	if(soChungTu_Chu == null)
	    		soChungTu_Chu = "";
	    	tthdBean.setSoChungTu_Chu(soChungTu_Chu);
	    	
	    	String soChungTu_So = util.antiSQLInspection(request.getParameter("soChungTu_So"));
	    	if(soChungTu_So == null)
	    		soChungTu_So = "";
	    	tthdBean.setSoChungTu_So(soChungTu_So);
	    	
	    	String tienteId = util.antiSQLInspection(request.getParameter("tienteId"));
	    	if(tienteId == null)
	    		tienteId = "";
	    	tthdBean.setTienteId(tienteId);
	    	
	    	String tigia = util.antiSQLInspection(request.getParameter("tigia"));
	    	
	    	if(tigia == null)
	    		tigia = "";
	    	tthdBean.setTigia(tigia.replaceAll("",""));

	    	String chietkhau = util.antiSQLInspection(request.getParameter("chietkhau"));
	    	if(chietkhau == null)
	    		chietkhau = "0";
	    	tthdBean.setChietkhau(chietkhau);
	    	
	    	String kenhbanhang = util.antiSQLInspection(request.getParameter("kbhId"));
	    	if(kenhbanhang == null)
	    		kenhbanhang = "0";
	    	tthdBean.setKbhId(kenhbanhang);
	    	
	    	String chietkhauNT = util.antiSQLInspection(request.getParameter("chietkhauNT"));
	    	if(chietkhauNT == null)
	    		chietkhauNT = "0";
	    	tthdBean.setChietkhauNT(chietkhauNT);
	    	
	    	String nguoinoptien = util.antiSQLInspection(request.getParameter("nguoinoptien"));
	    	if(nguoinoptien == null)
	    		nguoinoptien = "";
	    	tthdBean.setNguoinoptien(nguoinoptien);
	    	
	    	String diachi = util.antiSQLInspection(request.getParameter("diachi"));
	    	if(diachi == null)
	    		diachi = "";
	    	tthdBean.setDiachi(diachi);
	    	
	    	String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";				
	    	tthdBean.setKbhId(kbhId);
	    		    	
	    	String matendoituongdinhkhoan = request.getParameter("doituongdinhkhoan");
	    	if (matendoituongdinhkhoan == null)
	    		matendoituongdinhkhoan = "";
	    	tthdBean.setMaTenDoiTuongDinhKhoan(matendoituongdinhkhoan);
	    	
	    	matendoituongdinhkhoan = matendoituongdinhkhoan.split("--")[0];
	    	tthdBean.setDoituongdinhkhoanId(matendoituongdinhkhoan);
	    	
	    	String doituongtamung = request.getParameter("doituongtamung");
	    	if(doituongtamung == null)
	    		doituongtamung = "1";
	    	tthdBean.setDoiTuongTamUng(doituongtamung);
	    	
	    	String nccId = util.antiSQLInspection(request.getParameter("nccId"));
			if (nccId == null)
				nccId = "";				
	    	tthdBean.setNccId(nccId);
	    	
	    	String nvtuId = util.antiSQLInspection(request.getParameter("nvtuId"));
			if (nvtuId == null)
				nvtuId = "";				
	    	tthdBean.setNvtuId(nvtuId);
	    		    	
	    	session.setAttribute("doituongdinhkhoan",tthdBean.getDoiTuongDinhKhoan());
	    	
    		String sotienttNT = util.antiSQLInspection(request.getParameter("sotienthanhtoanNT"));
    		if(sotienttNT == null) sotienttNT = "0";
	    	tthdBean.setSotienttNT(sotienttNT.replaceAll("",""));
	    	
	    	String xoakhttId = util.antiSQLInspection(request.getParameter("xoakhttId"));
			if (xoakhttId == null)
				xoakhttId = "";				
	    	tthdBean.setXoakhtratruocId(xoakhttId);
	    	
	    	String nppIdgoc = request.getParameter("nppId");
			if (nppIdgoc == null)
				nppIdgoc = "";				
	    	tthdBean.setnppIdgoc(nppIdgoc);
	    
	    	if(nppIdgoc.length() > 0)
	    	{
	    		String[] nppId = nppIdgoc.split(" -- ");
	    		
	    		tthdBean.setNppId(nppId[0]);
	    		tthdBean.setisNPP(nppId[1]);
	    	}

	    	String npp = request.getParameter("npp");
			if (npp == null)
				npp = "";				
	    	tthdBean.setKh_npp_Ten(npp);
	    	
	    	// Lưu lại Định khoản có
	    	String[] taikhoanId = request.getParameterValues("taikhoanId");
//	    	String[] doituongdinhkhoan = request.getParameterValues("doituongdinhkhoan");
	    	String[] doituongId = request.getParameterValues("doituongId");
	    	String[] sotienco = request.getParameterValues("sotienco");
	    	
	    	List<IDinhkhoanco> dinhkhoanc =  new ArrayList<IDinhkhoanco>();
	    	if(taikhoanId != null)
	    	{
	    		IDinhkhoanco dkc = null ;
	    		int n = 0;
	    		
	    		while(n < taikhoanId.length )
	    		{		    
	    			if (taikhoanId[n].trim().length() > 0)
	    			{
		    			dkc = new Dinhkhoanco();	    			
		    			dkc.setTaikhoanId(taikhoanId[n]);
		    			
		    		    dkc.setDoituongdinhkhoan(dkc.CheckDoiTuongDinhKhoan(taikhoanId[n]));
		    				
		    			dkc.setDoituongId(doituongId[n]); 			    			
		    			
		    			if(sotienco[n] == null) sotienco[n] = "0" ;			    			
		    			dkc.setSotien(sotienco[n]);
		    			
		    			if(taikhoanId[n].trim().length() > 0)
		    			{
		    				dinhkhoanc.add(dkc);			    				
		    			}
	    			}	    			
	    			n ++;
	    		}
	    	}
	    	
	    	tthdBean.setDinhkhoancoRs(dinhkhoanc);
	    	boolean error = false;
	    	//Luu lai hoa don
	    	String[] idHd = request.getParameterValues("idHd");
	    	String[] mahd = request.getParameterValues("mahd");
	    	String[] kyhieuhd = request.getParameterValues("kyhieuhd");
			String[] sohd = request.getParameterValues("sohd");
			String[] ngayhd = request.getParameterValues("ngayhd");
			String[] avat = request.getParameterValues("avat");
			String[] sotienNT = request.getParameterValues("sotienNT");
			String[] thanhtoan = request.getParameterValues("thanhtoan");
			String[] loaihd = request.getParameterValues("loaihd");
			String[] tigiahd = request.getParameterValues("tigiaHd");
			if(loaihd==null && kyhieuhd!=null){
				loaihd=new String[kyhieuhd.length];
			}
			
	//		String[] conlai = request.getParameterValues("conlai");
	//		String[] tygiahd = request.getParameterValues("tygiahd");
			List<IHoadon> hdList =  new ArrayList<IHoadon>();
			
				if(kyhieuhd != null)
				{		
					IHoadon hoadon = null;
					int m = 0;
					while(m < kyhieuhd.length)
					{
						//String tmp = "" + (Double.parseDouble(avat[m].replaceAll(",",""))/Double.parseDouble(tigia.replaceAll(",","")));
						if(!tienteId.equals("100000"))
						{
							if(sotienNT != null){
								if(avat != null){
									if(tthdBean.getNoidungId().equals("100000"))// THU TIEN BAN HANG
									{
										hoadon = new Hoadon(idHd[m], mahd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m], sotienNT[m], thanhtoan[m], tienteId,"",tigiahd[m]);
										hoadon.setLoaihd(loaihd[m]);
									}else{ // THU HOI TAM UNG
										hoadon = new Hoadon(idHd[m], "", kyhieuhd[m], sohd[m], ngayhd[m], avat[m], sotienNT[m], thanhtoan[m], tienteId,"","");
										hoadon.setLoaihd(loaihd[m]);
									}
								}else {
									if(tthdBean.getNoidungId().equals("100000"))// THU TIEN BAN HANG
									{
										hoadon = new Hoadon(idHd[m], mahd[m], kyhieuhd[m], sohd[m], ngayhd[m], "0", sotienNT[m], thanhtoan[m], tienteId,"",tigiahd[m]);
										hoadon.setLoaihd(loaihd[m]);
									}
									else{ // THU HOI TAM UNG
										hoadon = new Hoadon(idHd[m], "", kyhieuhd[m], sohd[m], ngayhd[m], "0", sotienNT[m], thanhtoan[m], tienteId,"","");
										hoadon.setLoaihd(loaihd[m]);
									}
								}
							}else{
								if(tthdBean.getNoidungId().equals("100000"))// THU TIEN BAN HANG
								{
									hoadon = new Hoadon(idHd[m], mahd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m], "0", thanhtoan[m], tienteId, "", tigiahd[m]);
									hoadon.setLoaihd(loaihd[m]);
								}else{
									hoadon = new Hoadon(idHd[m], "", kyhieuhd[m], sohd[m], ngayhd[m], avat[m], "0", thanhtoan[m], tienteId, "", "");
									hoadon.setLoaihd(loaihd[m]);
								}
							}
						}
						else{
							
							if(avat != null){
								if(tthdBean.getNoidungId().equals("100000"))// THU TIEN BAN HANG
								{
									hoadon = new Hoadon(idHd[m], mahd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m], "0", thanhtoan[m], tienteId, "", tigiahd[m]);
									hoadon.setLoaihd(loaihd[m]);
								}else{// THU HOI TAM UNG									
									hoadon = new Hoadon(idHd[m], "", kyhieuhd[m], sohd[m], ngayhd[m], avat[m], "0", thanhtoan[m], tienteId,"","");
									hoadon.setLoaihd(loaihd[m]);
								}
							}
						}	
						hdList.add(hoadon);
					
						m++;
					}	
				}
				tthdBean.setHoadonRs(hdList);
			
			String action = request.getParameter("action");
			
			if(noidungId.trim().equals("100001") || noidungId.trim().equals("100002") ){
				tthdBean.setSotientt(tthdBean.getThuduoc());
				tthdBean.setSotienttNT(tthdBean.getThuduocNT());
			}
			
			System.out.println("action: " + action);
			if(action.equals("save") & error == false)
			{	
				if(id == null) //tao moi
				{
					if(!tthdBean.createTTHD())
					{
						tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
						tthdBean.setDoituongIddn(session.getAttribute("doituongId"));
	    		    	tthdBean.createRs();
	    		    	session.setAttribute("queryNpp", tthdBean.getQueryNpp());
	    		    	session.setAttribute("tthdBean", tthdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCKNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
						
						IErpThutienCKList obj = new ErpThutienCKList();
						obj.setUserId(userId);
					    obj.setCtyId(ctyId);		
					    obj.setnppdangnhap(util.getIdNhapp(userId));
					    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
					    obj.setDoituongId(session.getAttribute("doituongId"));
					    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);

					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCK.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!tthdBean.updateTTHD())
					{
						tthdBean.createRs();
						session.setAttribute("queryNpp", tthdBean.getQueryNpp());
						tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
						tthdBean.setDoituongIddn(session.getAttribute("doituongId"));
						session.setAttribute("tthdBean", tthdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCKUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						//tthdBean.updateDonmuahang(tthdBean.getDonmuahangId());
						
						IErpThutienCKList obj = new ErpThutienCKList();
					    obj.setUserId(userId);
					    obj.setCtyId(ctyId);		
					    obj.setnppdangnhap(util.getIdNhapp(userId));
					    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
					    obj.setDoituongId(session.getAttribute("doituongId"));
					    
					    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);

					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCK.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if (action.equals("changeNgayChungTu")){
				tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
				tthdBean.setDoituongIddn(session.getAttribute("doituongId"));
				if (util.antiSQLInspection(request.getParameter("checkThuHoChiNhanh")).trim().equals("1"))
					tthdBean.setIsthuhoCN("1");
				tthdBean.taoMoiSoChungTu();
				tthdBean.createRs();
				
				String nextJSP;
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCKNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCKUpdate.jsp";   						
				}
				session.setAttribute("queryNpp", tthdBean.getQueryNpp());
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
			}
			else if(action.equals("changeTT")){				
				hdList.clear();
				tthdBean.setHoadonRs(hdList);
				
				dinhkhoanc.clear();
				tthdBean.setDinhkhoancoRs(dinhkhoanc);	
				tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
				tthdBean.setDoituongIddn(session.getAttribute("doituongId"));
				
				tthdBean.createRs();
				
				String nextJSP;
				System.out.println("So tien:"+ id);
				if (id== null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCKNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCKUpdate.jsp";   						
				}
				session.setAttribute("queryNpp", tthdBean.getQueryNpp());
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
			}else
			{
				tthdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
				tthdBean.setDoituongIddn(session.getAttribute("doituongId"));
				if (util.antiSQLInspection(request.getParameter("checkThuHoChiNhanh")).trim().equals("1"))
					tthdBean.setIsthuhoCN("1");
				tthdBean.createRs();
				
				String nextJSP;
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCKNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpThuTienCKUpdate.jsp";   						
				}
				session.setAttribute("queryNpp", tthdBean.getQueryNpp());
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
}
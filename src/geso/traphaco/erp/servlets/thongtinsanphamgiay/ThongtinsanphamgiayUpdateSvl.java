
package geso.traphaco.erp.servlets.thongtinsanphamgiay;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IErp_TaiSanCoDinh;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IHoaChat_SanPham;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IMayMoc_SanPham;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinNCC;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinsanphamgiay;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinsanphamgiayList;
import geso.traphaco.erp.beans.thongtinsanphamgiay.ITieuchikiemdinh;
import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.Erp_TaiSanCoDinh;
import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.HoaChat_SanPham;
import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.MayMoc_SanPham;
import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.ThongtinNCC;
import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.Thongtinsanphamgiay;
import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.ThongtinsanphamgiayList;
import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.Tieuchikiemdinh;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.oreilly.servlet.MultipartRequest;

public class ThongtinsanphamgiayUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;	
	
	public ThongtinsanphamgiayUpdateSvl( )
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String ctyId = (String)session.getAttribute("congtyId");
		String ctyTen = (String)session.getAttribute("congtyTen");
		Utility util = new Utility();
		String querystring = request.getQueryString();
		
		String userId = util.getUserId(querystring);

		if (userId.length() == 0) 
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		IThongtinsanphamgiay spBean = new Thongtinsanphamgiay();
		spBean.setCtyId(ctyId);
	    spBean.setCtyTen(ctyTen);
		
		spBean.setUserId(userId);

		if(querystring.contains("print"))
		{
			 
			String fn = getFilename(querystring);
	 
			String filePath = getServletContext().getInitParameter("path") + "\\images\\" + fn;
			
			String ft = "";
			if (fn!=null && fn.contains(".")) 
				ft = fn.substring(fn.lastIndexOf(".")+1, fn.length());
			if (ft.length()>0)
			{
			if (ft.toLowerCase().equals("pdf"))
			{
				FileInputStream fstream = new FileInputStream(filePath);
		         
		        response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", " inline; filename=Hosocongbo.pdf");
					
		        ServletOutputStream os = response.getOutputStream();
		        byte[] bufferData = new byte[1024];
		        int read=0;
		        while((read = fstream.read(bufferData))!= -1){
		             os.write(bufferData, 0, read);
		        }
		        os.flush();
		        os.close();
		     	fstream.close();
			}else{
	            
				FileInputStream fstream = new FileInputStream(filePath);
	            response.setContentType("image/gif");
	            ServletOutputStream os = response.getOutputStream();
		        byte[] bufferData = new byte[1024];
		        int read=0;
		        while((read = fstream.read(bufferData))!= -1){
		             os.write(bufferData, 0, read);
		        }
		        os.flush();
		        os.close();
		     	fstream.close();
			}}
			
		}else{
			if (querystring.equals("display"))
			{
				String id = getId(querystring);
				spBean.setId(id);
				spBean.init2();
				
			}
			else
			{
				String id = getId(querystring);
				spBean.setId(id);
				spBean.init();
			}
			session.setAttribute("spBean", spBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiayUpdate.jsp";
			if (querystring.indexOf("display") > 0) 
				nextJSP = "/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiayDisplay.jsp";
			
			response.sendRedirect(nextJSP);
		}
	}
	
	public  void VeTrangTong (HttpSession session, HttpServletResponse response,String userId,String ctyId) throws IOException
	{
		IThongtinsanphamgiayList obj ;
		if(session.getAttribute("backAttribute")!= null)
		{
			obj = (IThongtinsanphamgiayList)session.getAttribute("backAttribute");
			obj.NewDbUtil();
			ThongtinsanphamgiaySvl x = new ThongtinsanphamgiaySvl();
			obj.setQuery( x.getSearchQuery(null,obj));
			x.destroy();
			session.removeAttribute("backAttribute");
			session.setAttribute("backAttribute", null);
		}
		else
		{
		   obj = new ThongtinsanphamgiayList();
		}
		
		
		obj.setCtyId(ctyId);
		obj.setUserId(userId);
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiay.jsp");

	 }
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId"); 
	    String ctyTen = (String)session.getAttribute("congtyTen");
		Utility util = new Utility();
		String contentType = request.getContentType();
		
		IThongtinsanphamgiay spBean = new Thongtinsanphamgiay();
		
		spBean.setCtyId(ctyId);
	    spBean.setCtyTen(ctyTen);
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id != null) spBean.setId(id);
		
		String[] khovung = new String[40];
		String[] bantp = new String[20];
		String hancongboTP ="";
		String hinhcongboTP = "";
		String filenamecbTP = "";
		String action = "";
		String packsize = "";
		boolean error = false;
		String userId = "";
		String masp = "";
		String trangthai = "";
		String tennoibo = "";
		String tenthuongmai = "";
		String tensp = "";
		String dvdlId = "";
		String dvkdId = "";
		String nhId = "";
		String clId = "";
		String giablc = "";
		String kl = "";
		String giamua = "";
		String loaigiaton = "";
		String hansudung = "";
		String tt = "";
		String loaispid = "";
		String loaispma = "";
		String check_vothoihan="";
		String[] nhacungcap ;
		String[] hancongbo ;
		String[] thoihangiaohang ;
		String[] luongdattoithieu;
		String[] hinhcongbo ;
		String[] filenamecb;
		String tonkhoantoan = "0";
		String cpnc = "";
		String cpvc = "";
		String kiemdinhtinh = "";
		String kiemdinhluong = "";
		String kiemtraoe = "";
		String thueSuat = "";
		
		//Lay tham so cho hoa chat
		String[] PK_SEQHC;
		String[] maHoaChat;
		String[] tenHoaChat;
		String soLuongChuan;
		String[] soLuongChatKiemDinh;
		String[] dvtHoaChatKiemDinh;
		String dvChatKiemDinh;
		
		//Lay tham so cho may moc
		String[] PK_SEQMM;
		String[] maMayMoc;
		String[] tenMayMoc;
		
		//Lay tham so cho hoa chat cua nha cung cap
		String[] PK_SEQHC_NCC;
		String[] maHoaChat_NCC;
		String[] tenHoaChat_NCC;
		String soLuongChuan_NCC;
		String[] soLuongChatKiemDinh_NCC;
		String[] dvtHoaChatKiemDinh_NCC;
		String dvChatKiemDinh_NCC;
		//Lay tham so cho maymoc cua nha cung cap
		String[] PK_SEQMM_NCC;
		String[] maMayMoc_NCC;
		String[] tenMayMoc_NCC;
		
		String[] qc_dvdl_1;
		String[] sl1;
		String[] sl2;
		String[] dvdl1;
		String[] dvdl2;
		String[] spIds;
		String[] nspIds;
		String[] tieuchi;
		String[] giatrichuan;
		String[] toantu;
		String doiloai;
		
		String nameHinhanhSp = "";
		String pathHinhanhSp = "";
		String hangbo = "";
		String batbuockiemdinh="";
		String Songayhancanhbao="0";
		String ngayhoanthanh="";
		
		String ycnlsx;
		String motaSp;
		String yeucaudonggoi;
		String thietbican;
		String thietbicankhac;
		String dactinhkythuat;
		String congthuchoahoc;
		String nhomluutru;
		String mucdonguyhiem;
		String khuvucbaoquan;
		String khongqlsl;
		
		int i;
		String is_khongthue="0";
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			
			String filePath = getServletContext().getInitParameter("path") + "\\\\images\\\\";
			MultipartRequest multi = new MultipartRequest(request, filePath, 20000000, "UTF-8");										
			Enumeration files = multi.getFileNames();
			int k = Integer.parseInt(multi.getParameter("sott"));
			//System.out.println("gia tri k : "+k);
			
			nhacungcap = multi.getParameterValues("nhacungcap");
			hinhcongbo = multi.getParameterValues("hinhcongbo");
			filenamecb = multi.getParameterValues("filenamecb");
			thoihangiaohang= multi.getParameterValues("thoihangiaohang");
			luongdattoithieu= multi.getParameterValues("luongdattoithieu");
			hancongbo= multi.getParameterValues("hancongbo");
			
			if(k>=0)
			{
				String filename;
				filename = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filename = multi.getFilesystemName(name);
					if (filename!=null && filename.length()>0) 
					{
						String ft = "";
						if (filename!=null && filename.contains(".")) 
							ft = filename.substring(filename.lastIndexOf("."), filename.length());
						
						filenamecb[k] = "hosocongbo" + "_" + System.currentTimeMillis() + ft;
						hinhcongbo[k]=filePath+filenamecb[k];
						new File(filePath+filename).renameTo(new File(hinhcongbo[k]));
					}
				}		

				String fhinh;
				
				for (i=0;i<5;i++) 
				{
					fhinh = multi.getParameter("filehinhcb"+i);
					spBean.setFilehinhcb(fhinh, i);
				}
				
			} 
			 
			else if (k==-1){
				String filename;
				filename = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filename = multi.getFilesystemName(name);
					if (filename!=null && filename.length()>0) 
					{
						String ft = "";
						if (filename!=null && filename.contains(".")) 
							ft = filename.substring(filename.lastIndexOf("."), filename.length());
						
						filenamecbTP = "hosocongbo" + "_" + System.currentTimeMillis() + ft;
						hinhcongboTP=filePath+filenamecbTP;
						new File(filePath+filename).renameTo(new File(hinhcongboTP));
					}
				}		
				
				spBean.setFilenamecbTP(filenamecbTP);
				spBean.setHinhcongboTP(hinhcongboTP);
				
			}else if (k==-2){
				String filename;
				filename = "";
				while (files.hasMoreElements())
				{
					String name = (String) files.nextElement();
					filename = multi.getFilesystemName(name);
					if (filename!=null && filename.length()>0) 
					{
						String ft = "";
						if (filename!=null && filename.contains(".")) 
							ft = filename.substring(filename.lastIndexOf("."), filename.length());
						
						nameHinhanhSp = "hinhsanpham" + "_" + System.currentTimeMillis() + ft;
						pathHinhanhSp=filePath+nameHinhanhSp;
						new File(filePath+filename).renameTo(new File(pathHinhanhSp));
					}
				}		
				spBean.setNameHinhanhSp(nameHinhanhSp);
				spBean.setPathHinhanhSp(pathHinhanhSp);
			}
			
			if (k<0)
			{
				nhacungcap = multi.getParameterValues("nhacungcap");
				if ((nhacungcap != null))
					spBean.setNhacungcap(nhacungcap);
				
				hinhcongbo = multi.getParameterValues("hinhcongbo");
				if (hinhcongbo != null)
					spBean.setHinhcongbo(hinhcongbo);
				
				filenamecb = multi.getParameterValues("filenamecb");
				if (filenamecb != null)
					spBean.setFilenamecb(filenamecb);
			}
			
			if (k!=-1)
			{
				hinhcongboTP = util.antiSQLInspection(multi.getParameter("hinhcongboTP"));
				if (hinhcongboTP != null)
					spBean.setHinhcongboTP(hinhcongboTP);
				
				filenamecbTP = util.antiSQLInspection(multi.getParameter("filenamecbTP"));
				if (filenamecbTP != null)
					spBean.setFilenamecbTP(filenamecbTP);
			}
			
			if (k!=-2)
			{
				pathHinhanhSp = util.antiSQLInspection(multi.getParameter("imagesp_path"));
				if (pathHinhanhSp != null)
					spBean.setPathHinhanhSp(pathHinhanhSp);
				
				nameHinhanhSp = util.antiSQLInspection(multi.getParameter("imagesp_name"));
				if (nameHinhanhSp != null)
					spBean.setNameHinhanhSp(nameHinhanhSp);
			}
			// luu lai cai da
			List<IThongtinNCC> ThongtinNCCList =new  ArrayList<IThongtinNCC>();
			if (nhacungcap!=null)
			{
				for(int j=0;j<nhacungcap.length;j++){
					 if(nhacungcap[j].length() >0){
						 
							IThongtinNCC ttncc=new ThongtinNCC();
								
							double thgh =0;
							try{
								thgh=Double.parseDouble(thoihangiaohang[j]);
							}catch(Exception er){}
							double sldhtt =0;
							try{
								sldhtt=Double.parseDouble(luongdattoithieu[j]);
							}catch(Exception er){}
						
							ttncc.setthoihangiaohang(""+Math.round(thgh));
							ttncc.setluongdattoithieu(""+Math.round(sldhtt));
							ttncc.setnhacungcap(nhacungcap[j]);
							ttncc.sethancongbo(hancongbo[j]);
							ttncc.sethinhcongbo(hinhcongbo[j]);
							ttncc.setfilenamecb(filenamecb[j]);
							ThongtinNCCList.add(ttncc);
							
					 }
				}
			}
		   spBean.SetThongtinNCClist(ThongtinNCCList);
		   
		   
			id = util.antiSQLInspection(multi.getParameter("id"));
			if (id != null) spBean.setId(id);
			is_khongthue = util.antiSQLInspection(multi.getParameter("is_khongthue"));
			if (is_khongthue == null)
				is_khongthue = "0";
			spBean.setIs_khongthue(is_khongthue);
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			spBean.setUserId(userId);
			
			ycnlsx = util.antiSQLInspection(multi.getParameter("ycnlsx"));
			if (ycnlsx != null)
				spBean.setYcnlsx(ycnlsx);
			
			motaSp = util.antiSQLInspection(multi.getParameter("motasp"));
			if (motaSp != null)
				spBean.setMotaSp(motaSp);
			
			yeucaudonggoi = util.antiSQLInspection(multi.getParameter("yeucaudonggoi"));
			if (yeucaudonggoi != null)
				spBean.setYeucaudonggoi(yeucaudonggoi);
			
			thietbican = util.antiSQLInspection(multi.getParameter("thietbicanid"));
			if (thietbican != null)
				spBean.setThietBiCan(thietbican);
			
			thietbicankhac = util.antiSQLInspection(multi.getParameter("thietbicankhacid"));
			if (thietbicankhac != null)
				spBean.setThietBiCanKhac(thietbicankhac);
			
			dactinhkythuat = util.antiSQLInspection(multi.getParameter("dactinhkythuat"));
			if (dactinhkythuat != null)
				spBean.setDactinhkythuat(dactinhkythuat);
			
			congthuchoahoc = util.antiSQLInspection(multi.getParameter("congthuchoahoc"));
			if (congthuchoahoc != null)
				spBean.setCongthuchoahoc(congthuchoahoc);
			
			nhomluutru = util.antiSQLInspection(multi.getParameter("nhomluutru"));
			if (nhomluutru != null)
				spBean.setNhomluutru(nhomluutru);
			
			mucdonguyhiem = util.antiSQLInspection(multi.getParameter("mucdonguyhiem"));
			if (mucdonguyhiem != null)
				spBean.setMucdonguyhiem(mucdonguyhiem);
			
			khuvucbaoquan = util.antiSQLInspection(multi.getParameter("khuvucbaoquan"));
			if (khuvucbaoquan != null)
				spBean.setKhuvucbaoquan(khuvucbaoquan);
			
			khongqlsl = util.antiSQLInspection(multi.getParameter("khongqlsl"));
			if (khongqlsl != null)
				spBean.setKhongqlsl(khongqlsl);
	
			masp = util.antiSQLInspection(multi.getParameter("masp"));
			if (masp == null)
				masp = "";
			spBean.setMasp(masp.trim());
			
			Songayhancanhbao = util.antiSQLInspection(multi.getParameter("songayhancanhbao"));
			if (Songayhancanhbao == null || Songayhancanhbao.equals("") )
				Songayhancanhbao = "0";
			spBean.setSongayhancanhbao(Songayhancanhbao);
			
			ngayhoanthanh = util.antiSQLInspection(multi.getParameter("ngayhoanthanh"));
			if (ngayhoanthanh == null || ngayhoanthanh.equals("") )
				ngayhoanthanh = "0";
			spBean.setNgayhoanthanh(ngayhoanthanh);
			
			
			
			check_vothoihan = util.antiSQLInspection(multi.getParameter("check_vothoihan"));
			if (check_vothoihan == null)
				check_vothoihan = "";
			spBean.setcheck_VoThoiHan(check_vothoihan);
			
	
			trangthai = util.antiSQLInspection(multi.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			spBean.setTrangthai(trangthai);
	
			tennoibo = util.antiSQLInspection(multi.getParameter("tennoibo"));
			if (tennoibo == null)
				tennoibo = "";
			spBean.setTennoibo(tennoibo);
			
			
			tenthuongmai = util.antiSQLInspection(multi.getParameter("tenthuongmai"));
			if (tenthuongmai == null)
				tenthuongmai = "";
			spBean.setTenthuongmai(tenthuongmai);
			System.out.println(" ten thuong mai :" + tenthuongmai);
			
			 	batbuockiemdinh = util.antiSQLInspection(multi.getParameter("batbuockiemdinh"));
			if (batbuockiemdinh == null)
				batbuockiemdinh = "";
			spBean.setBatbuockiemdinh(batbuockiemdinh);
			
			
			hancongboTP = util.antiSQLInspection(multi.getParameter("hancongboTP"));
			if (hancongboTP == null)
				hancongboTP = "";
			spBean.setHancongboTP(hancongboTP);
			
			tensp = util.antiSQLInspection(multi.getParameter("tensp"));
			if (tensp == null)
				tensp = "";
			spBean.setTen(tensp);
	
			dvdlId = util.antiSQLInspection(multi.getParameter("dvdlId"));
			if (dvdlId == null) dvdlId = "";
			spBean.setDvdlId(dvdlId);
	
			dvkdId = util.antiSQLInspection(multi.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";
			spBean.setDvkdId(dvkdId);
	
			nhId = util.antiSQLInspection(multi.getParameter("nhId"));
			if (nhId == null) nhId = "";
			spBean.setNhId(nhId);
	
			clId = util.antiSQLInspection(multi.getParameter("clId"));
			if (clId == null) clId = "";
			spBean.setClId(clId);
	
			giablc = util.antiSQLInspection(multi.getParameter("giablc"));
			if (giablc == null)
				giablc = "";
			//System.out.println("\nGIÁ BÁN LẺ CHUẨN: "+giablc);
			spBean.setGiablc(giablc);
	
			kl = util.antiSQLInspection(multi.getParameter("kl"));
			if (kl == null)
				kl = "";
			spBean.setKL(kl);
	
			giamua = util.antiSQLInspection(multi.getParameter("giamua"));
			if (giamua == null || giamua.trim().length() <= 0 )
				giamua = "0";
			spBean.setGiaMua(giamua);
			//System.out.println("GIÁ MUA: "+giamua);
			loaigiaton = util.antiSQLInspection(multi.getParameter("loaigiaton"));
			if (loaigiaton == null)
				loaigiaton = "";
			spBean.setLoaiGiaTon(loaigiaton);
	
			hansudung = util.antiSQLInspection(multi.getParameter("hansudung"));
			if (hansudung == null)
				hansudung = "";
			spBean.setHanSuDung(hansudung);
	
			tt = util.antiSQLInspection(multi.getParameter("tt"));
			if (tt == null)
				tt = "";
			spBean.setTT(tt);
	
			loaispid = multi.getParameter("loaisp");
			if (loaispid == null)
			{	
				loaispid = "";
			}
			spBean.setLoaiSpId(loaispid);
			
			loaispma = multi.getParameter("loaispma");
			if (loaispma == null)
				loaispma = "";
			spBean.setLoaiSpMa(loaispma);
			
			khovung = multi.getParameterValues("khovung");
			if (!(khovung == null))
				spBean.setKhovung(khovung);
			
			bantp = multi.getParameterValues("bantp");
			if (!(bantp == null))
				spBean.setBantp(bantp);
			
			hancongbo = multi.getParameterValues("hancongbo");
			if (!(hancongbo == null))
				spBean.setHancongbo(hancongbo);
			
			thoihangiaohang = multi.getParameterValues("thoihangiaohang");
			if (!(thoihangiaohang == null))
				spBean.setThoihangiaohang(thoihangiaohang);
			
			luongdattoithieu = multi.getParameterValues("luongdattoithieu");
			if (!(luongdattoithieu == null))
				spBean.setLuongdattoithieu(luongdattoithieu);
			
			tonkhoantoan = util.antiSQLInspection(multi.getParameter("tonkhoantoan"));
			if (tonkhoantoan == null) tonkhoantoan = "0"; else tonkhoantoan = tonkhoantoan.trim();
			spBean.setTonKhoAnToan(tonkhoantoan);
			
			cpnc = util.antiSQLInspection(multi.getParameter("cpnc"));
			if (cpnc == null) cpnc = "0"; else cpnc = cpnc.trim();
			spBean.setCPNC(cpnc);
			
			cpvc = util.antiSQLInspection(multi.getParameter("cpvc"));
			if (cpvc == null) cpvc = "0"; else cpvc = cpvc.trim();
			spBean.setCPVC(cpvc);
	
			qc_dvdl_1 = multi.getParameterValues("dvdl1");
			if (qc_dvdl_1 != null)
				spBean.setDvdlTen(qc_dvdl_1[0]);
	
			sl1 = multi.getParameterValues("sl1");
			if (!(sl1 == null))
				spBean.setSl1(sl1);
	
			sl2 = multi.getParameterValues("sl2");
			if (!(sl2 == null))
				spBean.setSl2(sl2);
	
			dvdl1 = multi.getParameterValues("dvdl1");
			if (!(dvdl1 == null))
				spBean.setDvdl1(dvdl1);
			
			dvdl2 = multi.getParameterValues("dvdl2");
			if (!(dvdl2 == null))
				spBean.setDvdl2(dvdl2);
			
			kiemdinhluong = util.antiSQLInspection(multi.getParameter("kiemdinh_dinhluong"));
			if (kiemdinhluong == null) 
				kiemdinhluong = "0";
			else kiemdinhluong = "1";
			spBean.setKiemTraDinhLuong(kiemdinhluong);
	 
			
			kiemdinhtinh = util.antiSQLInspection(multi.getParameter("kiemdinh_dinhtinh"));
			if (kiemdinhtinh == null) 
				kiemdinhtinh = "0";
			else kiemdinhtinh = "1";
			spBean.setKiemTraDinhTinh(kiemdinhtinh);

			List<ITieuchikiemdinh> tckdList = new ArrayList<ITieuchikiemdinh>();
			if( kiemdinhluong.trim().equals("1") )
			{
				tieuchi = multi.getParameterValues("tieuchi");
				toantu = multi.getParameterValues("toantu");
				giatrichuan = multi.getParameterValues("giatrichuan");
				//String[] dungsai = multi.getParameterValues("dungsai");
				
				if(tieuchi != null)
				{
					for(i = 0; i < tieuchi.length; i++)
					{
						if(tieuchi[i].trim().length() > 0)
						{
							ITieuchikiemdinh tckd = new Tieuchikiemdinh();
							tckd.setTieuchi(tieuchi[i]);
							tckd.setToantu(toantu[i]);
							tckd.setGiatrichuan(giatrichuan[i]);
							tckd.setDungsai("0");
							tckdList.add(tckd);
						}
					}
				}
			}
			spBean.setTieuchikiemdinhDinhluongList(tckdList);
			
			tckdList = new ArrayList<ITieuchikiemdinh>();
			if( kiemdinhtinh.trim().equals("1") )
			{
				tieuchi = multi.getParameterValues("tieuchi_dinhtinh");
				
				if(tieuchi != null)
				{
					for(i = 0; i < tieuchi.length; i++)
					{
						if(tieuchi[i].trim().length() > 0)
						{
							ITieuchikiemdinh tckd = new Tieuchikiemdinh();
							tckd.setTieuchi(tieuchi[i]);
							tckdList.add(tckd);
						}
					}
				}
			}
			spBean.setTieuchikiemdinhDinhtinhList(tckdList);
			
			/*String type = util.antiSQLInspection(multi.getParameter("type"));
			if (type == null)
				type = "0";
			else
				type = "1";*/
			spBean.setType("0");
	
			nspIds = multi.getParameterValues("nspIds");
			if(nspIds != null)
			{
				String nspId = "";
				for(i = 0; i < nspIds.length; i++)
				{
					nspId += nspIds[i] + ",";
				}
				
				if(nspId.trim().length() > 0)
				{
					nspId = nspId.substring(0, nspId.length() - 1);
					spBean.setNspIds(nspId);
				}
			}
			spIds = multi.getParameterValues("spIds");
			if (spIds != null)
			{
				String spId = "";
				for(i = 0; i < spIds.length; i++)
				{
					spId += spIds[i] + ",";
				}
				if(spId.trim().length() > 0)
				{
					spId = spId.substring(0, spId.length() - 1);
					spBean.setSpIds(spId);
				}
				//System.out.println("So san pham la: " + spIds.length + "\n");
			}
	
			packsize = util.antiSQLInspection(multi.getParameter("packsize"));
			if (packsize != null) { packsize = packsize.trim(); }
			spBean.setPacksizeId(packsize);
			
			hangbo = util.antiSQLInspection(multi.getParameter("hangbo"));
			if (hangbo != null)
				hangbo = "1";
			else
				hangbo = "0";
			spBean.setHangbo(hangbo);
			/*error = false;
			 
	
			if (masp.trim().length() == 0)
			{
				error = true;
				spBean.setMessage("Vui lÃ²ng nháº­p vÃ o mÃ£ cá»§a sáº£n pháº©m");
			} 
			else if (tennoibo.trim().length() == 0)
			{
				error = true;
				spBean.setMessage("Vui lÃ²ng nháº­p tÃªn ná»™i bá»™ cá»§a sáº£n pháº©m");
			} 
			else if (tensp.trim().length() == 0)
			{
				error = true;
				spBean.setMessage("Vui lÃ²ng nháº­p tÃªn cá»§a sáº£n pháº©m");
			} 
			
			if(id == null)
			{
				if ( Double.parseDouble(giamua) <= 0 )
				{
					error = true;
					spBean.setMessage("Vui lÃ²ng nháº­p giÃ¡ mua sáº£n pháº©m");
				}
			}*/
	 
			action = multi.getParameter("action");
			
		}
		else{
			
			 action = request.getParameter("action");
			 userId = util.antiSQLInspection(request.getParameter("userId"));
			 
			 if(action.equals("back"))
			 {
				 spBean.DBClose();
				 VeTrangTong(session, response, userId, ctyId);
				 return;
				
			 }
			
			//System.out.println("Come here: ");
			
			id = util.antiSQLInspection(request.getParameter("id"));
			if (id != null) spBean.setId(id);
			is_khongthue = util.antiSQLInspection(request.getParameter("is_khongthue"));
			if (is_khongthue == null)
				is_khongthue = "0";
			spBean.setIs_khongthue(is_khongthue);
			hancongboTP = util.antiSQLInspection(request.getParameter("hancongboTP"));
			if (hancongboTP == null)
				hancongboTP = "";
			spBean.setHancongboTP(hancongboTP);
			
			ycnlsx = util.antiSQLInspection(request.getParameter("ycnlsx"));
			if (ycnlsx != null)
				spBean.setYcnlsx(ycnlsx);
			
			motaSp = util.antiSQLInspection(request.getParameter("motasp"));
			if (motaSp != null)
				spBean.setMotaSp(motaSp);
			
			yeucaudonggoi = util.antiSQLInspection(request.getParameter("yeucaudonggoi"));
			if (yeucaudonggoi != null)
				spBean.setYeucaudonggoi(yeucaudonggoi);
			
			thietbican = util.antiSQLInspection(request.getParameter("thietbicanid"));
			if (thietbican != null)
				spBean.setThietBiCan(thietbican);
			
			thietbicankhac = util.antiSQLInspection(request.getParameter("thietbicankhacid"));
			if (thietbicankhac != null)
				spBean.setThietBiCanKhac(thietbicankhac);
			
			dactinhkythuat = util.antiSQLInspection(request.getParameter("dactinhkythuat"));
			if (dactinhkythuat != null)
				spBean.setDactinhkythuat(dactinhkythuat);
			
			congthuchoahoc = util.antiSQLInspection(request.getParameter("congthuchoahoc"));
			if (congthuchoahoc != null)
				spBean.setCongthuchoahoc(congthuchoahoc);
			
			nhomluutru = util.antiSQLInspection(request.getParameter("nhomluutru"));
			if (nhomluutru != null)
				spBean.setNhomluutru(nhomluutru);
			
			mucdonguyhiem = util.antiSQLInspection(request.getParameter("mucdonguyhiem"));
			if (mucdonguyhiem != null)
				spBean.setMucdonguyhiem(mucdonguyhiem);
			
			khuvucbaoquan = util.antiSQLInspection(request.getParameter("khuvucbaoquan"));
			if (khuvucbaoquan != null)
				spBean.setKhuvucbaoquan(khuvucbaoquan);
			
			khongqlsl = util.antiSQLInspection(request.getParameter("khongqlsl"));
			if (khongqlsl != null)
				spBean.setKhongqlsl(khongqlsl);
			
			hinhcongboTP = util.antiSQLInspection(request.getParameter("hinhcongboTP"));
			if (hinhcongboTP == null)
				hinhcongboTP = "";
			spBean.setHinhcongboTP(hinhcongboTP);
			
			filenamecbTP = util.antiSQLInspection(request.getParameter("filenamecbTP"));
			if (filenamecbTP == null)
				filenamecbTP = "";
			spBean.setFilenamecbTP(filenamecbTP);
			
			
			check_vothoihan = util.antiSQLInspection(request.getParameter("check_vothoihan"));
			if (check_vothoihan == null)
				check_vothoihan = "";
			spBean.setcheck_VoThoiHan(check_vothoihan);

			filenamecb = request.getParameterValues("filenamecb");
			if (filenamecb!=null)
				spBean.setFilenamecb(filenamecb);
			
			hinhcongbo = request.getParameterValues("hinhcongbo");
			if (hinhcongbo!=null)
				spBean.setHinhcongbo(hinhcongbo);

			
			spBean.setUserId(userId);
	
			masp = util.antiSQLInspection(request.getParameter("masp"));
			if (masp == null)
				masp = "";
			spBean.setMasp(masp);
	
			trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			spBean.setTrangthai(trangthai);
			

			thueSuat = util.antiSQLInspection(request.getParameter("thueSuat"));
			thueSuat = (thueSuat == null) ? "0" : thueSuat;
			spBean.setThueSuat(thueSuat);
			
			// Thêm dạng bào chế
			String dangBaoChe = request.getParameter("dangbaoche");
			if(dangBaoChe  == null){
				dangBaoChe = "";
			}
			spBean.setDangBaoChe(dangBaoChe);
			
			// Thêm dạng tiêu chuẩn kỹ thuật
			String tckt = request.getParameter("tckt");
			if(tckt  == null){
				tckt = "";
			}
			spBean.setTckt(tckt);
			
			// Thêm loại hàng hóa
			String loaiHangHoa = request.getParameter("loaihanghoa");
			if(loaiHangHoa  == null){
				loaiHangHoa = "";
			}
			spBean.setLoaiHangHoa(loaiHangHoa);
			
			// Thêm kiểm tra OE
			kiemtraoe = util.antiSQLInspection(request.getParameter("kiemdinh_oe"));
			if (kiemtraoe == null) 
				kiemtraoe = "0";
			else kiemtraoe = "1";
			spBean.setKiemtraoe(kiemtraoe);
			
			
			// Thêm kiểm tra OE
			String ischietkhau = util.antiSQLInspection(request.getParameter("ischietkhau"));
			if (ischietkhau == null) 
				ischietkhau = "0";
			else ischietkhau = "1";
			spBean.setIschietkhau(ischietkhau);
			
			batbuockiemdinh=  util.antiSQLInspection(request.getParameter("batbuockiemdinh"));
			if (batbuockiemdinh == null)
				batbuockiemdinh = "0";
			else
				batbuockiemdinh = "1";
			spBean.setBatbuockiemdinh(batbuockiemdinh);
	
			tennoibo = util.antiSQLInspection(request.getParameter("tennoibo"));
			if (tennoibo == null)
				tennoibo = "";
			spBean.setTennoibo(tennoibo);
			
			tenthuongmai = util.antiSQLInspection(request.getParameter("tenthuongmai"));
			if (tenthuongmai == null)
				tenthuongmai = "";
			spBean.setTenthuongmai(tenthuongmai);
			
			tensp = util.antiSQLInspection(request.getParameter("tensp"));
			if (tensp == null)
				tensp = "";
			spBean.setTen(tensp);
			
			dvdlId = util.antiSQLInspection(request.getParameter("dvdlId"));
			if (dvdlId == null) dvdlId = "";
			spBean.setDvdlId(dvdlId);
	
			dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";
			spBean.setDvkdId(dvkdId);
	
			nhId = util.antiSQLInspection(request.getParameter("nhId"));
			if (nhId == null) nhId = "";
			spBean.setNhId(nhId);
	
			clId = util.antiSQLInspection(request.getParameter("clId"));
			if (clId == null) clId = "";
			spBean.setClId(clId);
			
			giablc = util.antiSQLInspection(request.getParameter("giablc"));
			if (giablc == null)
				giablc = "";
			//System.out.println("\nGIÁ BÁN LẺ CHUẨN: "+giablc);
			spBean.setGiablc(giablc);
	
			kl = util.antiSQLInspection(request.getParameter("kl"));
			if (kl == null)
				kl = "";
			spBean.setKL(kl);
	
			giamua = util.antiSQLInspection(request.getParameter("giamua"));
			if (giamua == null || giamua.trim().length() <= 0 )
				giamua = "0";
			//System.out.println("GIÁ MUA: "+giamua);
			
			spBean.setGiaMua(giamua);
	
			loaigiaton = util.antiSQLInspection(request.getParameter("loaigiaton"));
			if (loaigiaton == null)
				loaigiaton = "";
			spBean.setLoaiGiaTon(loaigiaton);
	
			hansudung = util.antiSQLInspection(request.getParameter("hansudung"));
			if (hansudung == null)
				hansudung = "";
			spBean.setHanSuDung(hansudung);
	
			tt = util.antiSQLInspection(request.getParameter("tt"));
			if (tt == null)
				tt = "";
			spBean.setTT(tt);
	
			loaispid = request.getParameter("loaisp");
			if (loaispid == null)
				loaispid = "";
			spBean.setLoaiSpId(loaispid);
			
			doiloai = request.getParameter("doiloai");
			if (doiloai.equals("0"))
			{
				loaispma = request.getParameter("loaispma");
				if (loaispma == null)
					loaispma = "";
				spBean.setLoaiSpMa(loaispma);
				
			}else{
				loaispma = spBean.getLoaiSpMa(loaispid);
				spBean.setLoaiSpMa(loaispma);
			}
			
			khovung = request.getParameterValues("khovung");
			if (!(khovung == null))
				spBean.setKhovung(khovung);
			
			bantp = request.getParameterValues("bantp");
			if (!(bantp == null))
				spBean.setBantp(bantp);
			
			nhacungcap = request.getParameterValues("nhacungcap");
			if (!(nhacungcap == null))
				spBean.setNhacungcap(nhacungcap);
			
			String btpid  = request.getParameter("btpid");
			if ( (btpid == null)){
				btpid="NULL";
			}
			 spBean.setBTPId(btpid);
			 String phephamid  = request.getParameter("phephamid");
				if ( (phephamid == null)){
					phephamid="NULL";
				}
				 spBean.setPhephamId(phephamid);
			
			
			hancongbo = request.getParameterValues("hancongbo");
			if (!(hancongbo == null))
				spBean.setHancongbo(hancongbo);
			
			thoihangiaohang = request.getParameterValues("thoihangiaohang");
			if (!(thoihangiaohang == null))
				spBean.setThoihangiaohang(thoihangiaohang);
			
			luongdattoithieu = request.getParameterValues("luongdattoithieu");
			if (!(luongdattoithieu == null))
				spBean.setLuongdattoithieu(luongdattoithieu);
			
			tonkhoantoan = util.antiSQLInspection(request.getParameter("tonkhoantoan"));
			if (tonkhoantoan == null) tonkhoantoan = ""; else tonkhoantoan = tonkhoantoan.trim();
			spBean.setTonKhoAnToan(tonkhoantoan);
			
			cpnc = util.antiSQLInspection(request.getParameter("cpnc"));
			if (cpnc == null) cpnc = "0"; else cpnc = cpnc.trim();
			spBean.setCPNC(cpnc);
			
			cpvc = util.antiSQLInspection(request.getParameter("cpvc"));
			if (cpvc == null) cpvc = "0"; else cpvc = cpvc.trim();
			spBean.setCPVC(cpvc);
	
			qc_dvdl_1 = request.getParameterValues("dvdl1");
			if (qc_dvdl_1 != null)
				spBean.setDvdlTen(qc_dvdl_1[0]);
	
			sl1 = request.getParameterValues("sl1");
			if (!(sl1 == null))
				spBean.setSl1(sl1);
	
			sl2 = request.getParameterValues("sl2");
			if (!(sl2 == null))
				spBean.setSl2(sl2);
	
			dvdl1 = request.getParameterValues("dvdl1");
			if (!(dvdl1 == null))
				spBean.setDvdl1(dvdl1);
			
			dvdl2 = request.getParameterValues("dvdl2");
			if (!(dvdl2 == null))
				spBean.setDvdl2(dvdl2);
			
	
			
			kiemdinhluong = util.antiSQLInspection(request.getParameter("kiemdinh_dinhluong"));
			if (kiemdinhluong == null){ 
				kiemdinhluong = "0";
			}
			else{
				kiemdinhluong = "1";
				
				}
			spBean.setKiemTraDinhLuong(kiemdinhluong);
			System.out.println("kiemdinhluong  trong update : "+kiemdinhluong);
			
			kiemdinhtinh = util.antiSQLInspection(request.getParameter("kiemdinh_dinhtinh"));
			if (kiemdinhtinh == null) 
				kiemdinhtinh = "0";
			else kiemdinhtinh = "1";
			spBean.setKiemTraDinhTinh(kiemdinhtinh);
		 
			List<IThongtinNCC> ThongtinNCCList =new  ArrayList<IThongtinNCC>();
			if(nhacungcap!=null){
			for(int j=0;j<nhacungcap.length;j++){
				 if(nhacungcap[j].length() >0){
					 
					IThongtinNCC ttncc=new ThongtinNCC();
 
						ttncc.setnhacungcap(nhacungcap[j]);
						
						double thgh =0;
						try{
							thgh=Double.parseDouble(thoihangiaohang[j]);
						}catch(Exception er){}
						double sldhtt =0;
						try{
							sldhtt=Double.parseDouble(luongdattoithieu[j]);
						}catch(Exception er){}
						
						ttncc.setthoihangiaohang(""+Math.round(thgh));
						ttncc.setluongdattoithieu(""+Math.round(sldhtt));
						ttncc.sethancongbo(hancongbo[j]);
						ttncc.sethinhcongbo(hinhcongbo[j]);
						ttncc.setfilenamecb(filenamecb[j]);
						ThongtinNCCList.add(ttncc);
						
						// lấy thông tin kiểm định của từng NCC
						
						List<ITieuchikiemdinh> tckdList_ncc = new ArrayList<ITieuchikiemdinh>();
						if( kiemdinhluong.trim().equals("1") )
						{
							tieuchi = request.getParameterValues("tieuchi"+nhacungcap[j]);
							toantu = request.getParameterValues("toantu"+nhacungcap[j]);
							giatrichuan = request.getParameterValues("giatrichuan"+nhacungcap[j]);
							
							if(tieuchi != null)
							{
								for(i = 0; i < tieuchi.length; i++)
								{
									if(tieuchi[i] != "")
									{
										ITieuchikiemdinh tckd = new Tieuchikiemdinh();
										tckd.setTieuchi(tieuchi[i]);
										//System.out.println(" tieuchi : "+tieuchi[i]);
										tckd.setToantu(toantu[i]);
										tckd.setGiatrichuan(giatrichuan[i]);
										tckd.setDungsai("0");
										
										tckdList_ncc.add(tckd);
									}
								}
							}
						}
						
						ttncc.setTieuchikiemdinhDinhluongList(tckdList_ncc);
						tckdList_ncc = new ArrayList<ITieuchikiemdinh>();
						if( kiemdinhtinh.trim().equals("1") )
						{
							tieuchi = request.getParameterValues("tieuchi_dinhtinh"+nhacungcap[j]);
							if(tieuchi != null)
							{
								for(i = 0; i < tieuchi.length; i++)
								{
									if(tieuchi[i] != "")
									{
										ITieuchikiemdinh tckd = new Tieuchikiemdinh();
										tckd.setTieuchi(tieuchi[i]);
										
										tckdList_ncc.add(tckd);
									}
								}
							}
						}
						
						ttncc.setTieuchikiemdinhDinhtinhList(tckdList_ncc);
						
						//Lay tham so hoa chat kiem dinh cua nha cung cap
						if( kiemdinhluong.trim().equals("1") || kiemdinhtinh.trim().equals("1") ) {
							ttncc.getHoaChatKiemDinhList().clear();
							maHoaChat_NCC = request.getParameterValues("maHoaChat"+nhacungcap[j]);
							
							if (maHoaChat_NCC != null) {
								PK_SEQHC_NCC = request.getParameterValues("PK_SEQHC"+nhacungcap[j]);
								tenHoaChat_NCC = request.getParameterValues("tenHoaChat"+nhacungcap[j]);
								soLuongChuan_NCC = request.getParameter("soLuongChuan"+nhacungcap[j]);
								soLuongChatKiemDinh_NCC = request.getParameterValues("soLuongChatKiemDinh"+nhacungcap[j]);
								dvChatKiemDinh_NCC = request.getParameter("donViChuan");
								dvtHoaChatKiemDinh_NCC = request.getParameterValues("dvChatKiemDinh"+nhacungcap[j]);
								for (int countHoaChat_NCC = 0; countHoaChat_NCC < maHoaChat_NCC.length; countHoaChat_NCC++) {
									if (maHoaChat_NCC[countHoaChat_NCC].trim().length() > 0 || tenHoaChat_NCC[countHoaChat_NCC].trim().length() > 0) {
										IHoaChat_SanPham hoaChat_NCC = new HoaChat_SanPham(spBean.getId(), dvChatKiemDinh_NCC, 
												PK_SEQHC_NCC[countHoaChat_NCC], maHoaChat_NCC[countHoaChat_NCC], 
												tenHoaChat_NCC[countHoaChat_NCC], dvtHoaChatKiemDinh_NCC[countHoaChat_NCC], 
												Integer.parseInt(soLuongChuan_NCC), Integer.parseInt(soLuongChatKiemDinh_NCC[countHoaChat_NCC]));

										ttncc.addHoaChatKiemDinh(hoaChat_NCC);
									}
								}
							}
						}//Ket thuc lay tham so hoa chat kiem dinh cua nha cung cap
						
						//Lay tham so may moc kiem dinh cua nha cung cap
						if( kiemdinhluong.trim().equals("1") || kiemdinhtinh.trim().equals("1") ) {
							ttncc.getMayMocKiemDinhList().clear();
							maMayMoc_NCC = request.getParameterValues("maMayMoc"+nhacungcap[j]);
							
							if (maMayMoc_NCC != null) {
								PK_SEQMM_NCC = request.getParameterValues("PK_SEQMM"+nhacungcap[j]);
								tenMayMoc_NCC = request.getParameterValues("tenMayMoc"+nhacungcap[j]);
								for (int countMayMoc = 0; countMayMoc < maMayMoc_NCC.length; countMayMoc++) {
									if (tenMayMoc_NCC[countMayMoc].trim().length() > 0 || maMayMoc_NCC[countMayMoc].trim().length() > 0) {
										IErp_TaiSanCoDinh mayMoc_NCC = new Erp_TaiSanCoDinh();
										mayMoc_NCC.setId(PK_SEQMM_NCC[countMayMoc]);
										mayMoc_NCC.setDiengiai(tenMayMoc_NCC[countMayMoc]);
										mayMoc_NCC.setMa(maMayMoc_NCC[countMayMoc]);
										IMayMoc_SanPham mayMocKiemDinh_NCC = new MayMoc_SanPham(spBean.getId(), mayMoc_NCC);
										ttncc.addMayMocKiemDinh(mayMocKiemDinh_NCC);
									}
								}
							}
						}// Ket thuc lay tham so may moc kiem dinh cua nha cung cap
						
				 }
			}
			}
			spBean.SetThongtinNCClist(ThongtinNCCList);
 
			List<ITieuchikiemdinh> tckdList = new ArrayList<ITieuchikiemdinh>();
			if( kiemdinhluong.trim().equals("1") )
			{
				tieuchi = request.getParameterValues("tieuchi");
				toantu = request.getParameterValues("toantu");
				giatrichuan = request.getParameterValues("giatrichuan");
				
				if(tieuchi != null)
				{
					for(i = 0; i < tieuchi.length; i++)
					{
						if(tieuchi[i] != "")
						{
							ITieuchikiemdinh tckd = new Tieuchikiemdinh();
							tckd.setTieuchi(tieuchi[i]);
							tckd.setToantu(toantu[i]);
							tckd.setGiatrichuan(giatrichuan[i]);
							tckd.setDungsai("0");
							
							tckdList.add(tckd);
						}
					}
				}
			}
			spBean.setTieuchikiemdinhDinhluongList(tckdList);
			
			tckdList = new ArrayList<ITieuchikiemdinh>();
			if( kiemdinhtinh.trim().equals("1") )
			{
				tieuchi = request.getParameterValues("tieuchi_dinhtinh");
				if(tieuchi != null)
				{
					for(i = 0; i < tieuchi.length; i++)
					{
						if(tieuchi[i] != "")
						{
							ITieuchikiemdinh tckd = new Tieuchikiemdinh();
							tckd.setTieuchi(tieuchi[i]);
							
							tckdList.add(tckd);
						}
					}
				}
			}
			spBean.setTieuchikiemdinhDinhtinhList(tckdList);
			
			
			nspIds = request.getParameterValues("nspIds");
			if(nspIds != null)
			{
				String nspId = "";
				for(i = 0; i < nspIds.length; i++)
				{
					nspId += nspIds[i] + ",";
				}
				
				if(nspId.trim().length() > 0)
				{
					nspId = nspId.substring(0, nspId.length() - 1);
					spBean.setNspIds(nspId);
				}
			}
			spIds = request.getParameterValues("spIds");
			if (spIds != null)
			{
				String spId = "";
				for(i = 0; i < spIds.length; i++)
				{
					spId += spIds[i] + ",";
				}
				if(spId.trim().length() > 0)
				{
					spId = spId.substring(0, spId.length() - 1);
					spBean.setSpIds(spId);
				}
				//System.out.println("So san pham la: " + spIds.length + "\n");
			}
	
			packsize = util.antiSQLInspection(request.getParameter("packsize"));
			if (packsize != null) { packsize = packsize.trim(); }
			spBean.setPacksizeId(packsize);
			
			hangbo = util.antiSQLInspection(request.getParameter("hangbo"));
			//System.out.println("HANG BO = " + hangbo);
			if (hangbo == null)  
				hangbo = "0";
			else
				hangbo = "1";
			spBean.setHangbo(hangbo);
			//System.out.println("HANG BO = " + spBean.getHangbo());
			error = false;
			
			pathHinhanhSp = util.antiSQLInspection(request.getParameter("imagesp_path"));
			if (pathHinhanhSp == null)
				pathHinhanhSp = "";
			spBean.setPathHinhanhSp(pathHinhanhSp);
			
			nameHinhanhSp = util.antiSQLInspection(request.getParameter("imagesp_name"));
			if (nameHinhanhSp == null)
				nameHinhanhSp = "";
			spBean.setNameHinhanhSp(nameHinhanhSp);
			
			
			Songayhancanhbao = util.antiSQLInspection(request.getParameter("songayhancanhbao"));
			if (Songayhancanhbao == null || Songayhancanhbao.equals(""))
				Songayhancanhbao = "0";
			spBean.setSongayhancanhbao(Songayhancanhbao);
			
			
			   ngayhoanthanh = util.antiSQLInspection(request.getParameter("ngayhoanthanh"));
			if (ngayhoanthanh == null || ngayhoanthanh.equals(""))
				ngayhoanthanh = "0";
			spBean.setNgayhoanthanh(ngayhoanthanh);
			
			
			tckdList = new ArrayList<ITieuchikiemdinh>();
			if( kiemdinhtinh.trim().equals("1") )
			{
				tieuchi = request.getParameterValues("tieuchi_dinhtinh");
				if(tieuchi != null)
				{
					for(i = 0; i < tieuchi.length; i++)
					{
						if(tieuchi[i] != "")
						{
							ITieuchikiemdinh tckd = new Tieuchikiemdinh();
							tckd.setTieuchi(tieuchi[i]);
							
							tckdList.add(tckd);
						}
					}
				}
			}
			spBean.setTieuchikiemdinhDinhtinhList(tckdList);

			List<IHoaChat_SanPham> hoaChatKiemDinhList = new ArrayList<IHoaChat_SanPham>();
			if( kiemdinhluong.trim().equals("1") || kiemdinhtinh.trim().equals("1") ) {
				maHoaChat = request.getParameterValues("maHoaChat");
				
				if (maHoaChat != null) {
					PK_SEQHC = request.getParameterValues("PK_SEQHC");
					tenHoaChat = request.getParameterValues("tenHoaChat");
					soLuongChuan = request.getParameter("soLuongChuan");
					soLuongChatKiemDinh = request.getParameterValues("soLuongChatKiemDinh");
					dvChatKiemDinh = request.getParameter("donViChuan");
					dvtHoaChatKiemDinh = request.getParameterValues("dvChatKiemDinh");
					for (int countHoaChat = 0; countHoaChat < maHoaChat.length; countHoaChat++) {
						if (maHoaChat[countHoaChat].trim().length() > 0 || tenHoaChat[countHoaChat].trim().length() > 0) {
							IHoaChat_SanPham hoaChat = new HoaChat_SanPham(
																				spBean.getId(),
																				dvChatKiemDinh,
																				PK_SEQHC[countHoaChat],
																				maHoaChat[countHoaChat],
																				tenHoaChat[countHoaChat],
																				dvtHoaChatKiemDinh[countHoaChat],
																				Integer.parseInt(soLuongChuan),
																				Integer.parseInt(soLuongChatKiemDinh[countHoaChat]));
							hoaChatKiemDinhList.add(hoaChat);
						}
					}
					spBean.setHoaChatKiemDinhList(hoaChatKiemDinhList);
				}
				maMayMoc = request.getParameterValues("maMayMoc");
				if (maMayMoc != null) {
					List<IMayMoc_SanPham> mayMocKiemDinhList = new ArrayList<IMayMoc_SanPham>();
					PK_SEQMM = request.getParameterValues("PK_SEQMM");
					tenMayMoc = request.getParameterValues("tenMayMoc");
					
					for (int countMayMoc = 0; countMayMoc < maMayMoc.length; countMayMoc++) {
						//Neu mang ma asn pham co ki tu thi moi lay gia tri
						if (tenMayMoc[countMayMoc].trim().length() > 0 || maMayMoc[countMayMoc].trim().length() > 0) {
							IErp_TaiSanCoDinh mayMoc = new Erp_TaiSanCoDinh();
							mayMoc.setId(PK_SEQMM[countMayMoc]);
							mayMoc.setDiengiai(tenMayMoc[countMayMoc]);
							mayMoc.setMa(maMayMoc[countMayMoc]);
							IMayMoc_SanPham mayMocKiemDinh = new MayMoc_SanPham(spBean.getId(), mayMoc);
							mayMocKiemDinhList.add(mayMocKiemDinh);
						}
					}
					spBean.setMayMocKiemDinhList(mayMocKiemDinhList);
				}
				
		
			}
	 
			
		}
		
		spBean.CreateRS();

		spBean.setUserId(userId);
		session.setAttribute("userId", userId);
		session.setAttribute("spBean", spBean);
		String nextJSP;
		
		if (action.equals("save") )
		{
			
		if (dvdl1!=null && dvdl2!=null && sl1!=null && sl2!=null)
			for (i=0;i<5;i++)
				for (int j=i+1;j<4;j++)
					if (dvdl1[i].length() > 0 && dvdl2[i].length() > 0 && sl1[i].length() > 0 && sl2[i].length() > 0 && dvdl1[j].length() > 0 && dvdl2[j].length() > 0 && sl1[j].length() > 0 && sl2[j].length() > 0)
						if (dvdl2[i]==dvdl2[j])
						{
							error = true;
							spBean.setMessage("Các đơn vị đo lường 2 phải khác nhau (dòng thứ " + (i+1) + " và dòng thứ " + (j+1) + " bị trùng, vui lòng chọn lại)");
							break;
						}

		if (masp.trim().length() <= 0)
		{
			error = true;
			spBean.setMessage("Vui lòng nhập mã sản phẩm");
		} 
		else if (tennoibo.trim().length() <= 0)
		{
			error = true;
			spBean.setMessage("Vui lòng nhập tên nội bộ của sản phẩm");
		} 
		else if (tensp.trim().length() <= 0)
		{
			error = true;
			spBean.setMessage("Vui lòng nhập tên của sản phẩm");
		} 
		/*else if (tenthuongmai.trim().length() <= 0)
		{
			error = true;
			spBean.setMessage("Vui lòng nhập thương mại của sản phẩm");
		}*/
		else 
			if ( loaispma.contains("TP") && Double.parseDouble(giamua) <= 0 )
			{
				//error = true;
				//spBean.setMessage("Vui lòng nhập giá mua sản phẩm");
			}
		} 
		
		
		
		if (action.equals("save")  && !error)
		{
			//System.out.println("id ne: " + id);
			if (id == null)
			{
				if (!(spBean.CreateSp()))
				{
					//System.out.println("CreateSp error!!!!!!!");
					nextJSP = "/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiayNew.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					
					spBean.DBClose();
					VeTrangTong(session, response, userId, ctyId);
					return;
				}
			} else
			{
				if (!(spBean.UpdateSp()))
				{
					session.setAttribute("obj", spBean);
					nextJSP = "/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiayUpdate.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					spBean.DBClose();
					VeTrangTong(session, response, userId, ctyId);
					return;
				}
			}
		} else if (id == null)
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiayNew.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiayUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	private String getId(String str) 
	{
		String id = "";
		String tmp = "";
		if (str!=null) if (str.length()>0 && str.contains("&")) tmp = str.split("&")[1];
		if (tmp!=null) if (tmp.length()>0 && tmp.contains("=")) id = tmp.split("=")[1];
		return id;
	}
	
	private String getFilename(String str) 
	{
		String fn = "";
		String tmp = "";
		if (str!=null) if (str.length()>0 && str.contains("&")) tmp = str.split("&")[1];
		if (tmp!=null) if (tmp.length()>0 && tmp.contains("=")) fn = tmp.split("=")[1];
		return fn;
	}
}

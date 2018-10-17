package geso.traphaco.erp.servlets.kichbansanxuatgiay;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kichbansanxuatgiay.IErpKichBanSanXuatGiay;
import geso.traphaco.erp.beans.kichbansanxuatgiay.IErpKichBanSanXuatGiayList;
import geso.traphaco.erp.beans.kichbansanxuatgiay.IKichBan_CongDoanSx;
import geso.traphaco.erp.beans.kichbansanxuatgiay.imp.ErpKichBanSanXuatGiay;
import geso.traphaco.erp.beans.kichbansanxuatgiay.imp.ErpKichBanSanXuatGiayList;
import geso.traphaco.erp.beans.kichbansanxuatgiay.imp.KichBan_CongDoanSx;
import geso.traphaco.erp.beans.kichbansanxuatgiay.imp.KichbanSX_CongdoanSX_ChiTiet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

@WebServlet("/ErpKichBanSanXuatGiayUpdateSvl")
public class ErpKichBanSanXuatGiayUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public ErpKichBanSanXuatGiayUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpKichBanSanXuatGiay kbsxBean = new ErpKichBanSanXuatGiay();

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String ctyId = (String) session.getAttribute("congtyId");
		String id = util.getId(querystring);
		
		kbsxBean.setCtyId(ctyId);
		kbsxBean.setId(id);
		kbsxBean.setUserId(userId);
		kbsxBean.init();
		
		session.setAttribute("kbsxBean", kbsxBean);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiayNew.jsp";
		if(querystring.contains("display"))
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiayDisplay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		Utility util = new Utility();
		IErpKichBanSanXuatGiay kbsxBean = new ErpKichBanSanXuatGiay();
		
		String ctyId = (String)session.getAttribute("congtyId");
		kbsxBean.setCtyId(ctyId);
		
		String contentType = request.getContentType();
		if (contentType != null && contentType.indexOf("multipart/form-data") >= 0) {
			String filePath = getServletContext().getInitParameter("pathPage") +  "\\upload\\";
	    	
	    	MultipartRequest multi = new MultipartRequest(request, filePath, 20000000, "UTF-8");
			
			Enumeration files = multi.getFileNames();
	    	
	    	String filename = "";
	    	String filenameNew = "";
			while (files.hasMoreElements()) {
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				if (filename != null && filename.length() > 0) {
					String ft = "";
					if (filename!=null && filename.contains(".")) 
						ft = filename.substring(filename.lastIndexOf("."), filename.length());
					
					filenameNew = "KbsxCdsxChitiet" + "_" + System.currentTimeMillis() + ft;
					new File(filePath + filename).renameTo(new File(filePath + filenameNew));
					
					break;
				}
			}
			
			System.out.println(filePath + filenameNew + ":"+ filename);
	    	
	    	int uploadi = Integer.parseInt(multi.getParameter("uploadi"));
	    	kbsxBean.setUploadi("" + uploadi);
	    	int uploadj = Integer.parseInt(multi.getParameter("uploadj"));
	    	
	    	String id = multi.getParameter("id");
			if (id == null)
				id = "";
			
			if(id.length() > 0)
				kbsxBean.setId(id);
			
			String userId = multi.getParameter("userId");
			kbsxBean.setUserId(userId);
			
			String sanphamId = multi.getParameter("sanpham");
			if(sanphamId==null)
				sanphamId="";
			kbsxBean.setSpSelected(sanphamId);
			
			String diengiai = multi.getParameter("diengiai");
			if (diengiai == null)
				diengiai = "";
			kbsxBean.setDiengiai(diengiai);
			
			String nhamayId = multi.getParameter("nhamayId");
			if (nhamayId == null)
				nhamayId = "";
			kbsxBean.setNhaMayId(nhamayId);
			
			String soluongchuan = multi.getParameter("soluongchuan");
			if (soluongchuan == null)
				soluongchuan = "";
			kbsxBean.setSoluongchuan(soluongchuan.replace(",", ""));
		 
			String songaysanxuat = multi.getParameter("songaysanxuat");
			if (songaysanxuat == null)
				songaysanxuat = "";
			kbsxBean.setSongaysanxuat( Integer.parseInt(songaysanxuat.replace(",", "")));
	
			String trangthai = multi.getParameter("trangthai");
			if (trangthai == null)
				trangthai = "0";
			kbsxBean.setTrangThai(trangthai);
			
			String hosolosx = multi.getParameter("hosolosx");
			if (hosolosx == null)
				hosolosx = "";
			kbsxBean.setHosolosx(hosolosx);
			
			String ngaybanhanhhsl = multi.getParameter("ngaybanhanhhsl");
			if (ngaybanhanhhsl == null)
				ngaybanhanhhsl = "";
			kbsxBean.setNgaybanhanhhsl(ngaybanhanhhsl);
			
			String lanbanhanhhsl = multi.getParameter("lanbanhanhhsl");
			if (lanbanhanhhsl == null)
				lanbanhanhhsl = "";
			kbsxBean.setLanbanhanhhsl(lanbanhanhhsl);
			 
			String[] congdoanId = multi.getParameterValues("congdoanId");
//			String[] congdoanIdcu = multi.getParameterValues("congdoanIdcu");
			String[] thoigian = multi.getParameterValues("thoigian");
			String[] thutu = multi.getParameterValues("thutu");
			String[] vattuIds = multi.getParameterValues("vattuId");
//			String[] countThongso = multi.getParameterValues("countthongso");
			String[] soluong = multi.getParameterValues("soluong");
			
			List<IKichBan_CongDoanSx> lstCdsx = new ArrayList<IKichBan_CongDoanSx>();
			IKichBan_CongDoanSx cdsx;
			String nhapkho;
			
			for(int i = 0; i < congdoanId.length; i++){
				cdsx = new KichBan_CongDoanSx();
				
				cdsx.setId(congdoanId[i]);
				cdsx.setThoiGian(thoigian[i]);
				cdsx.setThuTu(thutu[i]);
				cdsx.setVattuId(vattuIds[i]);
				cdsx.setSoluong(soluong[i]);
				
				nhapkho = multi.getParameter("nhapkho_"+i);
				if(nhapkho == null)
					nhapkho = "0";
				else
					nhapkho = "1";
				
				cdsx.setNhapkho(nhapkho);
				
				if(congdoanId[i].length() > 0){
					List<KichbanSX_CongdoanSX_ChiTiet> cdsxChitietList = new ArrayList<KichbanSX_CongdoanSX_ChiTiet>();
					
					String[] khuvucsxid_ = multi.getParameterValues("khuvucsxid_"+i);
					String[] khuvucsx_ = multi.getParameterValues("khuvucsx_"+i);
					String[] giaidoansxid_ = multi.getParameterValues("giaidoansxid_"+i);
					String[] giaidoansx_ = multi.getParameterValues("giaidoansx_"+i);
					String[] tscd_cptt_id_ = multi.getParameterValues("tscd_cptt_id_"+i);
					String[] tscd_cptt_ = multi.getParameterValues("tscd_cptt_"+i);
					String[] thietbiid_ = multi.getParameterValues("thietbiid_"+i);
					String[] thietbi_ = multi.getParameterValues("thietbi_"+i);
					String[] thongsochung_ = multi.getParameterValues("thongsochung_"+i);
					String[] thongso_ = multi.getParameterValues("thongso_"+i);
					String[] yeucau_ = multi.getParameterValues("yeucau_"+i);
					String[] thongsotu_ = multi.getParameterValues("thongsotu_"+i);
					String[] thongsoden_ = multi.getParameterValues("thongsoden_"+i);
					String[] dvtid_ = multi.getParameterValues("dvtid_"+i);
					String[] dvt_ = multi.getParameterValues("dvt_"+i);
//					String[] id_cdsx_chitiet_ = multi.getParameterValues("id_cdsx_chitiet_"+i);
					String[] dat_ = multi.getParameterValues("dat_"+i);
					String[] upload_path_ = multi.getParameterValues("upload_path_"+i);
					String[] upload_name_ = multi.getParameterValues("upload_name_"+i);
					
					KichbanSX_CongdoanSX_ChiTiet cdsxChitiet;
					for(int j = 0; j < khuvucsxid_.length; j++){
						cdsxChitiet = new KichbanSX_CongdoanSX_ChiTiet();
						
//						cdsxChitiet.setId(id_cdsx_chitiet_[j]);
						cdsxChitiet.setKhuvucsxId(khuvucsxid_[j]);
						cdsxChitiet.setKhuvucsx(khuvucsx_[j]);
						cdsxChitiet.setGiaidoansxId(giaidoansxid_[j]);
						cdsxChitiet.setGiaidoansx(giaidoansx_[j]);
						cdsxChitiet.setTscdCpttId(tscd_cptt_id_[j]);
						cdsxChitiet.setTscdCptt(tscd_cptt_[j]);
						cdsxChitiet.setThietbiId(thietbiid_[j]);
						cdsxChitiet.setThietbi(thietbi_[j]);
						cdsxChitiet.setThongsochung(thongsochung_[j]);
						cdsxChitiet.setThongso(thongso_[j]);
						cdsxChitiet.setYeucau(yeucau_[j]);
						cdsxChitiet.setThongsotu(thongsotu_[j]);
						cdsxChitiet.setThongsoden(thongsoden_[j]);
						cdsxChitiet.setDvtId(dvtid_[j]);
						cdsxChitiet.setDvt(dvt_[j]);
						cdsxChitiet.setDat(dat_[j]);
						
						if(uploadi == i && uploadj == j){
							cdsxChitiet.setUploadPath(filePath + filenameNew);
							cdsxChitiet.setUploadName(filename);
						} else {
							cdsxChitiet.setUploadPath(upload_path_[j]);
							cdsxChitiet.setUploadName(upload_name_[j]);
						}
						
						String chonchitiet = multi.getParameter("chon_"+i+"_"+j);
						if(chonchitiet == null)
							chonchitiet = "0";
						else
							chonchitiet = "1";
						
						cdsxChitiet.setChon(chonchitiet);
						
						cdsxChitietList.add(cdsxChitiet);
					}
					
					cdsx.setCdsxChitietList(cdsxChitietList);
				}
				
				lstCdsx.add(cdsx);
			}
			
			kbsxBean.setCongDoanSxList(lstCdsx);
			
			kbsxBean.createRs();
			session.setAttribute("kbsxBean", kbsxBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiayNew.jsp";
			response.sendRedirect(nextJSP);
		} else {
			String action = request.getParameter("action");
			if(action.equals("download")) {
				try {
		    		// reads input file from an absolute path
			        String filePath = request.getParameter("pathdownload");
			        File downloadFile = new File(filePath);
			        FileInputStream inStream = new FileInputStream(downloadFile);
			        
			        // if you want to use a relative path to context root:
			        String relativePath = getServletContext().getRealPath("");
			        System.out.println("relativePath = " + relativePath);
			        
			        // obtains ServletContext
			        ServletContext context = getServletContext();
			        
			        // gets MIME type of the file
			        String mimeType = context.getMimeType(filePath);
			        if (mimeType == null) {        
			            // set to binary type if MIME mapping not found
			            mimeType = "application/octet-stream";
			        }
			        System.out.println("MIME type: " + mimeType);
			        
			        // modifies response
			        response.setContentType(mimeType);
			        response.setContentLength((int) downloadFile.length());
			        
			        // forces download
			        String headerKey = "Content-Disposition";
			        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
			        response.setHeader(headerKey, headerValue);
			        
			        // obtains response's output stream
			        OutputStream outStream = response.getOutputStream();
			        
			        byte[] buffer = new byte[4096];
			        int bytesRead = -1;
			         
			        while ((bytesRead = inStream.read(buffer)) != -1) {
			            outStream.write(buffer, 0, bytesRead);
			        }
			        
			        inStream.close();
			        outStream.close();
			        return;
				} catch (Exception e) {
					// TODO: handle exception
					kbsxBean.setMsg("File này đã bị xóa không thể download.");
				}
		    }
			
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
				id = "";
			
			if(id.length() > 0)
				kbsxBean.setId(id);
			
			String userId = util.antiSQLInspection(request.getParameter("userId"));
			kbsxBean.setUserId(userId);
			
			String sanphamId = util.antiSQLInspection(request.getParameter("sanpham"));
			if(sanphamId==null)
				sanphamId="";
			kbsxBean.setSpSelected(sanphamId);
			
			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			kbsxBean.setDiengiai(diengiai);
			
			String nhamayId = util.antiSQLInspection(request.getParameter("nhamayId"));
			if (nhamayId == null)
				nhamayId = "";
			kbsxBean.setNhaMayId(nhamayId);
			
			String soluongchuan = util.antiSQLInspection(request.getParameter("soluongchuan"));
			if (soluongchuan == null)
				soluongchuan = "";
			kbsxBean.setSoluongchuan(soluongchuan.replace(",", ""));
		 
			String songaysanxuat = util.antiSQLInspection(request.getParameter("songaysanxuat"));
			if (songaysanxuat == null)
				songaysanxuat = "";
			kbsxBean.setSongaysanxuat( Integer.parseInt(songaysanxuat.replace(",", "")));
	
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null)
				trangthai = "0";
			kbsxBean.setTrangThai(trangthai);
			
			String hosolosx = util.antiSQLInspection(request.getParameter("hosolosx"));
			if (hosolosx == null)
				hosolosx = "";
			kbsxBean.setHosolosx(hosolosx);
			
			String ngaybanhanhhsl = util.antiSQLInspection(request.getParameter("ngaybanhanhhsl"));
			if (ngaybanhanhhsl == null)
				ngaybanhanhhsl = "";
			kbsxBean.setNgaybanhanhhsl(ngaybanhanhhsl);
			
			String lanbanhanhhsl = util.antiSQLInspection(request.getParameter("lanbanhanhhsl"));
			if (lanbanhanhhsl == null)
				lanbanhanhhsl = "";
			kbsxBean.setLanbanhanhhsl(lanbanhanhhsl);
			 
			String[] congdoanId = request.getParameterValues("congdoanId");
			String[] congdoanIdcu = request.getParameterValues("congdoanIdcu");
			String[] thoigian = request.getParameterValues("thoigian");
			String[] thutu = request.getParameterValues("thutu");
			String[] vattuIds = request.getParameterValues("vattuId");
//			String[] countThongso = request.getParameterValues("countthongso");
			String[] soluong = request.getParameterValues("soluong");
			
			List<IKichBan_CongDoanSx> lstCdsx = new ArrayList<IKichBan_CongDoanSx>();
			IKichBan_CongDoanSx cdsx;
			String nhapkho;
			
			System.out.println("fiejffiej:"+congdoanId.length);
			for(int i = 0; i < congdoanId.length; i++){
				cdsx = new KichBan_CongDoanSx();
				
				cdsx.setId(congdoanId[i]);
				cdsx.setThoiGian(thoigian[i]);
				cdsx.setThuTu(thutu[i]);
				cdsx.setVattuId(vattuIds[i]);
				
				nhapkho = request.getParameter("nhapkho_"+i);
				if(nhapkho == null)
					nhapkho = "0";
				else
					nhapkho = "1";
				
				cdsx.setNhapkho(nhapkho);
				
				if(congdoanId[i].length() > 0){
					if(soluong[i].length() > 0){
						cdsx.setSoluong(soluong[i]);
					} else {
						cdsx.setSoluong("0");
					}
					System.out.println("dfjeifjie:"+i);
					if(congdoanIdcu != null && congdoanIdcu[i] != null && congdoanId[i].equals(congdoanIdcu[i])){
						List<KichbanSX_CongdoanSX_ChiTiet> cdsxChitietList = new ArrayList<KichbanSX_CongdoanSX_ChiTiet>();
						
						String[] khuvucsxid_ = request.getParameterValues("khuvucsxid_"+i);
						String[] khuvucsx_ = request.getParameterValues("khuvucsx_"+i);
						String[] giaidoansxid_ = request.getParameterValues("giaidoansxid_"+i);
						String[] giaidoansx_ = request.getParameterValues("giaidoansx_"+i);
						String[] tscd_cptt_id_ = request.getParameterValues("tscd_cptt_id_"+i);
						String[] tscd_cptt_ = request.getParameterValues("tscd_cptt_"+i);
						String[] thietbiid_ = request.getParameterValues("thietbiid_"+i);
						String[] thietbi_ = request.getParameterValues("thietbi_"+i);
						String[] thongsochung_ = request.getParameterValues("thongsochung_"+i);
						String[] thongso_ = request.getParameterValues("thongso_"+i);
						String[] yeucau_ = request.getParameterValues("yeucau_"+i);
						String[] thongsotu_ = request.getParameterValues("thongsotu_"+i);
						String[] thongsoden_ = request.getParameterValues("thongsoden_"+i);
						String[] dvtid_ = request.getParameterValues("dvtid_"+i);
						String[] dvt_ = request.getParameterValues("dvt_"+i);
//						String[] id_cdsx_chitiet_ = request.getParameterValues("id_cdsx_chitiet_"+i);
						String[] dat_ = request.getParameterValues("dat_"+i);
						String[] upload_path_ = request.getParameterValues("upload_path_"+i);
						String[] upload_name_ = request.getParameterValues("upload_name_"+i);
						
						KichbanSX_CongdoanSX_ChiTiet cdsxChitiet;
						if(khuvucsxid_ != null){
							for(int j = 0; j < khuvucsxid_.length; j++){
								cdsxChitiet = new KichbanSX_CongdoanSX_ChiTiet();
								
	//							cdsxChitiet.setId(id_cdsx_chitiet_[j]);
								cdsxChitiet.setKhuvucsxId(khuvucsxid_[j]);
								cdsxChitiet.setKhuvucsx(khuvucsx_[j]);
								cdsxChitiet.setGiaidoansxId(giaidoansxid_[j]);
								cdsxChitiet.setGiaidoansx(giaidoansx_[j]);
								cdsxChitiet.setTscdCpttId(tscd_cptt_id_[j]);
								cdsxChitiet.setTscdCptt(tscd_cptt_[j]);
								cdsxChitiet.setThietbiId(thietbiid_[j]);
								cdsxChitiet.setThietbi(thietbi_[j]);
								cdsxChitiet.setThongsochung(thongsochung_[j]);
								cdsxChitiet.setThongso(thongso_[j]);
								cdsxChitiet.setYeucau(yeucau_[j]);
								cdsxChitiet.setThongsotu(thongsotu_[j]);
								cdsxChitiet.setThongsoden(thongsoden_[j]);
								cdsxChitiet.setDvtId(dvtid_[j]);
								cdsxChitiet.setDvt(dvt_[j]);
								cdsxChitiet.setDat(dat_[j]);
								cdsxChitiet.setUploadPath(upload_path_[j]);
								cdsxChitiet.setUploadName(upload_name_[j]);
								
								String chonchitiet = request.getParameter("chon_"+i+"_"+j);
								if(chonchitiet == null)
									chonchitiet = "0";
								else
									chonchitiet = "1";
								
								cdsxChitiet.setChon(chonchitiet);
								
								cdsxChitietList.add(cdsxChitiet);
							}
						}
						
						cdsx.setCdsxChitietList(cdsxChitietList);
					} else {
						cdsx.createRs();
					}
				}
				
				lstCdsx.add(cdsx);
			}
			
			kbsxBean.setCongDoanSxList(lstCdsx);
			
			if (action.equals("save")) {
				if(id.length() > 0){
					if (!(kbsxBean.edit())) {
						kbsxBean.createRs();
						session.setAttribute("kbsxBean", kbsxBean);
						session.setAttribute("userId", userId);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiayNew.jsp";
						response.sendRedirect(nextJSP);
					} else {
						IErpKichBanSanXuatGiayList obj = new ErpKichBanSanXuatGiayList();
						obj.setUserId(userId);
						obj.setCtyId(ctyId);
						obj.init("");
						kbsxBean.DbClose();
						
						session.setAttribute("kbsxList", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiay.jsp";
						response.sendRedirect(nextJSP);
					}
				} else {
					if (!kbsxBean.save()) {
						kbsxBean.createRs();
						session.setAttribute("kbsxBean", kbsxBean);
						session.setAttribute("userId", userId);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiayNew.jsp";
						response.sendRedirect(nextJSP);
					} else {
						IErpKichBanSanXuatGiayList obj = new ErpKichBanSanXuatGiayList();
						obj.setCtyId(ctyId);
						obj.setUserId(userId);
						obj.init("");
						kbsxBean.DbClose();
						session.setAttribute("kbsxList", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiay.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} else {
				kbsxBean.createRs();
				session.setAttribute("userId", userId);
				session.setAttribute("kbsxBean", kbsxBean);
				
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatGiayNew.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
}

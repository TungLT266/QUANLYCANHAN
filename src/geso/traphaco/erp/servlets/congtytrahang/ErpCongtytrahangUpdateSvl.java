package geso.traphaco.erp.servlets.congtytrahang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.congtytrahang.IErpCongtytrahang;
import geso.traphaco.erp.beans.congtytrahang.IErpCongtytrahangList;
import geso.traphaco.erp.beans.congtytrahang.imp.ErpCongtytrahang;
import geso.traphaco.erp.beans.congtytrahang.imp.ErpCongtytrahangList;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahangList_Giay;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.donmuahang.IKho;
import geso.traphaco.erp.beans.donmuahang.ISanpham;
import geso.traphaco.erp.beans.donmuahang.imp.ErpDonmuahangList_Giay;
import geso.traphaco.erp.beans.donmuahang.imp.ErpDonmuahang_Giay;
import geso.traphaco.erp.beans.donmuahang.imp.Sanpham;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpCongtytrahangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpCongtytrahangUpdateSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String nextJSP;
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

			Utility util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpCongtytrahang dmhBean = new ErpCongtytrahang(id);
			dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
			dmhBean.setUserId(userId); // phai co UserId truoc khi Init
			
			String task = request.getParameter("task");
			if(task == null)
				task = "";
			
			String canduyet = request.getParameter("duyet");
			if(canduyet == null)
				canduyet = "1";
			dmhBean.setCanDuyet(canduyet);
			
			if(task.equals("print"))
			{
				Create_PO_PDF(response, request);
			}
			else
			{
				dmhBean.init();
				
				if (request.getQueryString().indexOf("display") >= 0)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongTyTraHangDisplay.jsp";
				}
				else
				{
					if (request.getQueryString().indexOf("hoantat") >= 0)
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongTyTraHangDisplay.jsp";
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongTyTraHangUpdate.jsp";
					}
				}
				
				session.setAttribute("lhhId", dmhBean.getLoaihanghoa());
				session.setAttribute("lspId", dmhBean.getLoaispId());
				session.setAttribute("dmhBean", dmhBean);
				response.sendRedirect(nextJSP);
			}
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
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			session.setMaxInactiveInterval(30000);
			
			IErpCongtytrahang dmhBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			System.out.println("ID" + id);
			if (id == null)
			{
				dmhBean = new ErpCongtytrahang("");
			}
			else
			{
				dmhBean = new ErpCongtytrahang(id);
			}
			
			dmhBean.setCongtyId((String)session.getAttribute("congtyId"));
			session.setAttribute("ctyId", (String)session.getAttribute("congtyId"));
			dmhBean.setUserId(userId);
			
			String canduyet = request.getParameter("duyet");
			if(canduyet == null)
				canduyet = "1";
			dmhBean.setCanDuyet(canduyet);
			
			String ngaygd = util.antiSQLInspection(request.getParameter("ngaymuahang"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();
			dmhBean.setNgaymuahang(ngaygd);
			
			String dvth = util.antiSQLInspection(request.getParameter("dvthId"));
			if (dvth == null)
				dvth = "";
			dmhBean.setDvthId(dvth);

			String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
			if (loaihh == null)
				loaihh = "";
			session.setAttribute("lhhId", loaihh);
			dmhBean.setLoaihanghoa(loaihh);
			
			String loaisp = util.antiSQLInspection(request.getParameter("loaisp"));
			if (loaisp == null)
				loaisp = "";
			session.setAttribute("lspId", loaisp);
			dmhBean.setLoaispId(loaisp);
			
			String NguonGocHH = util.antiSQLInspection(request.getParameter("nguongoc"));
			if (NguonGocHH == null)
				NguonGocHH = "";
			dmhBean.setNguonGocHH(NguonGocHH);
			
			String nccId = util.antiSQLInspection(request.getParameter("nccId"));
			if (nccId == null)
				nccId = "";
			session.setAttribute("nccId", nccId);
			dmhBean.setNCC(nccId);
			
			String nccLoai = util.antiSQLInspection(request.getParameter("nccLoai"));
			if (nccLoai == null)
				nccLoai = "";
			session.setAttribute("nccLoai", nccLoai);
			dmhBean.setNccLOai(nccLoai);
			
			String nccTen = util.antiSQLInspection(request.getParameter("nccTen"));
			if (nccTen == null)
				nccTen = "";
			dmhBean.setNccTen(nccTen);
			
			String lydotrahang = util.antiSQLInspection(request.getParameter("lydotrahang"));
			if (lydotrahang == null)
				lydotrahang = "";
			dmhBean.setLydotrahang(lydotrahang);
			
			String tiente_fk = util.antiSQLInspection(request.getParameter("tiente_fk"));
			if (tiente_fk == null)
			{
				tiente_fk = "";
			}
			
			if (tiente_fk.trim().length() > 0)
			{
				String[] arr = tiente_fk.split(" - ");
				
				dmhBean.setTyGiaQuyDoi(Float.parseFloat(arr[1]));
				dmhBean.setTienTe_FK(arr[0]);
			}
			
			
			
			String BVAT = request.getParameter("BVAT");
			if (BVAT == null)
				BVAT = "0";
			else
				BVAT = BVAT.replaceAll(",", "");
			dmhBean.setTongtienchuaVat(BVAT);
			
			
			String AVAT = request.getParameter("AVAT");
			if (AVAT == null)
				AVAT = "0";
			else
				AVAT = AVAT.replaceAll(",", "");
			dmhBean.setTongtiensauVat(AVAT);
			
			
			double VAT =0;
			if(dmhBean.getTongtiensauVat()!= null && dmhBean.getTongtiensauVat()!= "" && dmhBean.getTongtienchuaVat()!= null && dmhBean.getTongtienchuaVat()!= "")
			{
				VAT =Double.parseDouble(dmhBean.getTongtiensauVat()) -Double.parseDouble(dmhBean.getTongtienchuaVat());
			}
			
			if (AVAT == null)
				AVAT = "0";
			else
				AVAT = AVAT.replaceAll(",", "");
			dmhBean.setVat(String.valueOf (VAT));
			
			
			
			
			String AUSDVAT = request.getParameter("AUSDVAT");
			if (AUSDVAT == null)
				AUSDVAT = AVAT;
			else
				AUSDVAT = AUSDVAT.replaceAll(",", "");
			dmhBean.setAVATNGUYENTE(AUSDVAT);
			
			
			String BUSDVAT = request.getParameter("BUSDVAT");
			if (BUSDVAT == null)
				BUSDVAT = BVAT;
			else
				BUSDVAT = BUSDVAT.replaceAll(",", "");
			dmhBean.setBVATNGUYENTE(BUSDVAT);
			
			
			double VATNGUYENTE  =0;
			if(dmhBean.getAVATNGUYENTE()!= null && dmhBean.getAVATNGUYENTE()!= "" && dmhBean.getBVATNGUYENTE()!=null && dmhBean.getBVATNGUYENTE()!= "")
			{
				VATNGUYENTE =Double.parseDouble(dmhBean.getAVATNGUYENTE()) -Double.parseDouble(dmhBean.getBVATNGUYENTE());
			}
			
			
			dmhBean.setVATNGUYENTE(String.valueOf(VATNGUYENTE));
			
			
			
			String dungsaiTong = util.antiSQLInspection(request.getParameter("dungsai"));
			if (dungsaiTong == null)
				dungsaiTong = "";
			dmhBean.setDungsai(dungsaiTong);
			
			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "NULL";
			dmhBean.setGhiChu(ghichu);
			
			String nguongoc = util.antiSQLInspection(request.getParameter("nguongoc"));
			dmhBean.setNguonGocHH(nguongoc);
			
			String[] duyetIds = request.getParameterValues("duyetIds");
			dmhBean.setDuyetIds(duyetIds);
			
			String[] idsp = request.getParameterValues("idsp");
			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] soluong = request.getParameterValues("soluong");
			String[] donvitinh = request.getParameterValues("donvitinh");
			String[] dongia = request.getParameterValues("dongia");
			String[] thanhtien = request.getParameterValues("thanhtien");
			String[] thuenhapkhau = request.getParameterValues("thuenhapkhau");
			String[] phantramthue = request.getParameterValues("phantramthue");
			String[] ngaynhan = request.getParameterValues("ngaynhan");
			String[] khonhan = request.getParameterValues("khonhan");
			String[] nhomhang = request.getParameterValues("nhomhang");
			String[] tenhoadon = request.getParameterValues("tenhoadon");
			
			String[] thuexuat = request.getParameterValues("thuexuat");
			String[] tienvat = request.getParameterValues("tienvat");
			String[] dongiaViet= request.getParameterValues("donGiaUSD");
			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			ISanpham sp = null;
			
			for(int i = 0; i < masp.length; i++)
			{
				if(masp[i].trim().length() > 0 )
				{
					sp = new Sanpham();
					sp.setPK_SEQ(idsp[i]);
					sp.setMasanpham(masp[i]);
					sp.setTensanpham(tensp[i]);
					sp.setSoluong(soluong[i].replaceAll(",", ""));
					sp.setDonvitinh(donvitinh[i]);
					sp.setDongia(dongia[i].replaceAll(",", ""));
					sp.setThanhtien(thanhtien[i].replaceAll(",", ""));
					sp.setThueNhapKhau("0");
					sp.setPhanTramThue("0");
					sp.setNgaynhanstr(ngaynhan[i]);
					sp.setKhonhan(khonhan[i]);
					sp.setNhomhang(nhomhang[i]);
					sp.setTenHD("");
					
					if(nguongoc.trim().equals("TN")){
						sp.setThuexuat(thuexuat[i].replaceAll(",", ""));
						sp.setTienVAT(tienvat[i].replaceAll(",", ""));
					}
					
					if(nguongoc.trim().equals("NN")){
						if(dongiaViet!= null){
						if(dongiaViet[i]!= null){
							sp.setDongiaViet(dongiaViet[i].replaceAll(",", ""));
						}
						else
						{
							sp.setDongiaViet("0");
						}
						}
					}
					else
					{
						if(dongia[i]!= null){
							
							sp.setDongiaViet(dongia[i].replaceAll(",", ""));
						}
						else{
							sp.setDongiaViet("0");
						}
						
					}
					
					spList.add(sp);
				}
			}
			dmhBean.setSpList(spList);
			 if(masp != null  )  //LUU LAI THONG TIN NGUOI DUNG NHAP
				{
					Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
					for(int i = 0; i < masp.length; i++ )
					{
						String temID = masp[i] ;
						//System.out.println("---TEMP ID: " + temID);
						
						String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
						String[] spNGAYHETHAN = request.getParameterValues(temID + "_spNGAYHETHAN");
						String[] spVITRI = request.getParameterValues(temID + "_spVITRI");
						String[] spMATHUNG = request.getParameterValues(temID + "_spMATHUNG");
						String[] spMAME = request.getParameterValues(temID + "_spMAME");
						String[] spMAPHIEU = request.getParameterValues(temID + "_spMAPHIEU");
						String[] spHAMLUONG = request.getParameterValues(temID + "_spHAMLUONG");
						String[] spHAMAM = request.getParameterValues(temID + "_spHAMAM");
						String[] spNGAYNHAPKHO = request.getParameterValues(temID + "_spNGAYNHAPKHO");
						String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
						String[] spMARQ = request.getParameterValues(temID + "_spMARQ");
						String[] spNSXID = request.getParameterValues(temID + "_spNSXID");
						if(soLUONGXUAT != null)
						{
							for(int j = 0; j < soLUONGXUAT.length; j++ )
							{
								//1 số sp không có lô
								//if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0 && spSOLO[j].trim().length() > 0 )
								if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0  && Double.parseDouble(soLUONGXUAT[j].replaceAll(",", "")) != 0 )
								{
									System.out.println("---KEY SVL: " + ( masp[i] + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spVITRI[j]  + "__" + spMATHUNG[j] + "__" + spMAME[j] ) + "__" + spMAPHIEU[j] + "   --- GIA TRI: " + soLUONGXUAT[j].replaceAll(",", "") );
									sanpham_soluong.put(masp[i]+ "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spVITRI[j] + "__" + spMATHUNG[j] + "__" + spMAME[j] + "__" + spMAPHIEU[j]+ "__" + spHAMLUONG[j] + "__" + spHAMAM[j]+ "__" + spNGAYNHAPKHO[j]+ "__" + spMARQ[j]+ "__" + spNSXID[j], soLUONGXUAT[j].replaceAll(",", "") );
								}
							}
						}
					}
					
					dmhBean.setSanpham_Soluong(sanpham_soluong);
					dmhBean.setSanpham_SoluongDAXUAT_THEODN(sanpham_soluong);
				}
			String action = request.getParameter("action");
			if (action.equals("save"))
			{
				if (id == null) // tao moi
				{
					if (!dmhBean.createDmh())
					{
						dmhBean.createRs();
						session.setAttribute("dmhBean", dmhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongTyTraHangNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						dmhBean.close();
						IErpCongtytrahangList obj = new ErpCongtytrahangList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setIsdontrahang("2");
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongTyTraHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if (!dmhBean.updateDmh())
					{
						dmhBean.createRs();
						session.setAttribute("dmhBean", dmhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongTyTraHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						IErpCongtytrahangList obj = new ErpCongtytrahangList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setIsdontrahang("2");
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongTyTraHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("changeSP"))
				{
					//Thay doi loai hang hoa, phai xoa het SP da chon
					dmhBean.setSpList(new ArrayList<ISanpham>());
				}
				
				dmhBean.createRs();
				
				String nextJSP;
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongTyTraHangNew.jsp";
				}
				else
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CongTyTraHangUpdate.jsp";
				}
				session.setAttribute("dmhBean", dmhBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private void Create_PO_PDF(HttpServletResponse response, HttpServletRequest request) 
	{
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=DonDatHang.pdf");
		
		Document document = new Document(PageSize.LETTER);
		ServletOutputStream outstream;
		try 
		{
			outstream = response.getOutputStream();
			
			String id = request.getParameter("dmhId");
			
			String nguongocHH = "TN";
			dbutils db = new dbutils();
			ResultSet rsCheck = db.get("select ISNULL(nguongocHH, 'TN') as nguongoc from ERP_MUAHANG where PK_SEQ = '" + id + "'");
			if(rsCheck != null)
			{
				try 
				{
					if(rsCheck.next())
					{
						nguongocHH = rsCheck.getString("nguongoc");
					}
					rsCheck.close();
				} 
				catch (Exception e) {}
			}
			
			if(nguongocHH.equals("TN"))
			{
				this.CreatePO_VietNam(document, outstream, response, request, db);
			}
			else
			{
				this.CreatePO_English(document, outstream, response, request, db);
			}
			
			db.update("Update ERP_MuaHang set DaInPdf = 1 where pk_seq = '" + id + "'");
			db.shutDown();
			
		} 
		catch (IOException e) 
		{
			System.out.println("___Exception PO Print: " + e.getMessage());
		}
	}
	
	
	private void CreatePO_VietNam(Document document, ServletOutputStream outstream, HttpServletResponse response, HttpServletRequest request, dbutils db) 
	{
		HttpSession session = request.getSession();
		
		String id = request.getParameter("dmhId");
		String ctyId = (String)session.getAttribute("congtyId");
		
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			
			/********************** INFO CONGTY *******************************/
			
			PdfPTable tbHeader = new PdfPTable(3);
			tbHeader.setWidthPercentage(100);
			tbHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crHeader = {40.0f, 200.0f, 100.0f};
			tbHeader.setWidths(crHeader);
			tbHeader.getDefaultCell().setBorder(0);
			
			PdfPCell cell = null;
			
			try
			{
				//Image pic = Image.getInstance("D:\\project\\TraphacoERP\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png");
				
				Image pic = Image.getInstance("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png");
				if(pic != null)
				{
					System.out.println("______Load Images Thanh Cong....");
					pic.setBorder(0);
					pic.setAlignment(Element.ALIGN_CENTER);
					tbHeader.addCell(pic);
				}
				else
				{
					cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
					cell.setBorder(0);
					tbHeader.addCell(cell);
				}
			}
			catch (Exception e) 
			{
				System.out.println("Exception load Images: " + e.getMessage());
				cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbHeader.addCell(cell);
				
			}
			

			String query = "select ma, isnull(ten, 'NA') as ten, isnull(diachi, 'NA') as diachi, isnull(masothue, 'NA') as masothue, " +
								"isnull(dienthoai, 'NA') as dienthoai, isnull(fax, 'NA') as fax " +
							"from erp_congty where pk_seq = '" + ctyId + "'";
			ResultSet infoCty = db.get(query);
			
			if(infoCty.next())
			{
				PdfPTable tbSubHeader = new PdfPTable(1);
				tbSubHeader.setWidthPercentage(100);
				tbSubHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubHeader.getDefaultCell().setBorder(0);
				float[] crSubHeader = {200.0f};
				tbSubHeader.setWidths(crSubHeader);
				
				cell = new PdfPCell(new Paragraph(infoCty.getString("ten").toUpperCase(), new Font(bf, 8, Font.BOLD)));
				cell.setBorder(0);
				tbSubHeader.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(infoCty.getString("diachi"), new Font(bf, 8, Font.BOLD)));
				cell.setBorder(0);
				tbSubHeader.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("MST: " + infoCty.getString("masothue"), new Font(bf, 8, Font.BOLD)));
				cell.setBorder(0);
				tbSubHeader.addCell(cell);
				
				tbHeader.addCell(tbSubHeader);
				
				
				PdfPTable tbSubHeader2 = new PdfPTable(1);
				tbSubHeader2.setWidthPercentage(100);
				tbSubHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
				float[] crSubHeader2 = {100.0f};
				tbSubHeader2.setWidths(crSubHeader2);
				

				cell = new PdfPCell(new Paragraph("[T]: " + infoCty.getString("dienthoai"), new Font(bf, 7, Font.BOLD)));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("[F]: " + infoCty.getString("fax"), new Font(bf, 7, Font.BOLD)));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("[E]: purchasing@newtoyovn.com", new Font(bf, 7, Font.BOLD)));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("[W]: www.newtoyovn.com", new Font(bf, 7, Font.BOLD)));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);
				
				tbHeader.addCell(tbSubHeader2);
				
			}
			infoCty.close();
			
			
			document.add(tbHeader);
			
			/********************** END INFO CONGTY *******************************/
			
			
			/********************** INFO NHA CUNG CAP *******************************/
			
			PdfPTable tbNCC = new PdfPTable(2);
			tbNCC.setWidthPercentage(100);
			tbNCC.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crNcc = {200.0f, 150.0f};
			tbNCC.setWidths(crNcc);
			tbNCC.getDefaultCell().setBorder(0);
			tbNCC.setSpacingBefore(10.0f);
			
			
			query = "select c.prefix + a.prefix + cast(a.pk_seq as varchar(10)) as poId, a.ngaymua, isnull(a.dungsai, 0) as dungsai,  " +
						"isnull(b.ten, 'NA') as nccTen, isnull(b.diachi, 'NA') as diachi, isnull(b.dienthoai, 'NA') as dienthoai, isnull(b.fax, 'NA') as fax, isnull(b.ten_nguoilienhe, 'NA') as nguoilienhe, b.hanmucno  " +
					"from erp_muahang a inner join ERP_NhaCungCap b on a.nhacungcap_fk = b.pk_seq  " +
						"inner join ERP_DonViThucHien c on a.donvithuchien_fk = c.pk_seq  " +
					"where a.pk_seq = '" + id + "'";
			
			System.out.println("___Init NCC: " + query);
			
			ResultSet infoNcc = db.get(query);
			String dungsai = "0";
			if(infoNcc.next())
			{
				PdfPTable tbSubNcc = new PdfPTable(1);
				tbSubNcc.setWidthPercentage(100);
				tbSubNcc.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNcc.getDefaultCell().setBorder(1);
				float[] crSubNcc = {200.0f};
				tbSubNcc.setWidths(crSubNcc);
				
				cell = new PdfPCell(new Paragraph("SUPPLIER", new Font(bf, 8, Font.UNDERLINE)));
				cell.setBorderWidth(1);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);
				
				dungsai = infoNcc.getString("dungsai");
				cell = new PdfPCell(new Paragraph("   " + infoNcc.getString("nccTen"), new Font(bf, 8, Font.BOLD)));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   " + infoNcc.getString("diachi"), new Font(bf, 8, Font.NORMAL)));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   Fax: " + infoNcc.getString("fax") + "         Tel: " + infoNcc.getString("dienthoai"), new Font(bf, 8, Font.NORMAL)));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   Người liên hệ: " + infoNcc.getString("nguoilienhe"), new Font(bf, 8, Font.NORMAL)));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				tbSubNcc.addCell(cell);
				
	
				tbNCC.addCell(tbSubNcc);
				
				
				
				PdfPTable tbSubNcc2 = new PdfPTable(1);
				tbSubNcc2.setWidthPercentage(100);
				tbSubNcc2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNcc2.getDefaultCell().setBorder(0);
				float[] crSubNcc2 = {200.0f};
				tbSubNcc2.setWidths(crSubNcc2);
				
				cell = new PdfPCell(new Paragraph("ĐƠN ĐẶT HÀNG", new Font(bf, 12, Font.BOLD)));
				cell.setBorder(0);
				cell.setPadding(3.0f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tbSubNcc2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Số đơn hàng: " + infoNcc.getString("poId"), new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNcc2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Chiếu theo số: ", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNcc2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Ngày: " + infoNcc.getString("ngaymua"), new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNcc2.addCell(cell);
				
	
				tbNCC.addCell(tbSubNcc2);
				
				document.add(tbNCC);
				
				
				
				/********************INFO NCC **********************/
				
				PdfPTable tbNCCInfo = new PdfPTable(2);
				tbNCCInfo.setWidthPercentage(100);
				tbNCCInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
				float[] crNccInfo = {200.0f, 150.0f};
				tbNCCInfo.setWidths(crNccInfo);
				tbNCCInfo.getDefaultCell().setBorder(0);
				tbNCCInfo.setSpacingBefore(8.0f);
				tbNCCInfo.setSpacingAfter(8.0f);
				
				
				PdfPTable tbSubNccInfo = new PdfPTable(1);
				tbSubNccInfo.setWidthPercentage(100);
				tbSubNccInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNccInfo.getDefaultCell().setBorder(0);
				float[] crSubNccInfo = {200.0f};
				tbSubNccInfo.setWidths(crSubNccInfo);
				
	
				cell = new PdfPCell(new Paragraph("   Hình thức thanh toán: " + infoNcc.getString("hanmucno") + " ngày kể từ ngày nhận hóa đơn", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNccInfo.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   Địa điểm giao hàng: Kho Công ty New Toyo", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNccInfo.addCell(cell);
				
				tbNCCInfo.addCell(tbSubNccInfo);
				
				
				PdfPTable tbSubNccInfo2 = new PdfPTable(1);
				tbSubNccInfo2.setWidthPercentage(100);
				tbSubNccInfo2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNccInfo2.getDefaultCell().setBorder(0);
				float[] crSubNccInfo2 = {150.0f};
				tbSubNccInfo2.setWidths(crSubNccInfo2);
				
	
				cell = new PdfPCell(new Paragraph("  ", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNccInfo2.addCell(cell);
				
				/*cell = new PdfPCell(new Paragraph("Ngày giao hàng: " + infoNcc.getString("ngaymua"), new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNccInfo2.addCell(cell);*/
				
				cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNccInfo2.addCell(cell);
				
				tbNCCInfo.addCell(tbSubNccInfo2);
				
				
				document.add(tbNCCInfo);
			}
			infoNcc.close();
			
			/********************END INFO NCC ***********************/
			
			
			
			/********************INFO SAN PHAM **********************/
			
			PdfPTable tbSanPham = new PdfPTable(7);
			tbSanPham.setWidthPercentage(100);
			tbSanPham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crSanpham = {30.0f, 250.0f, 60.0f, 60.0f, 60.0f, 60.0f, 60.0f};
			tbSanPham.setWidths(crSanpham);
			tbSanPham.getDefaultCell().setBorder(0);
			tbSanPham.setSpacingAfter(8.0f);
			
			
			cell = new PdfPCell(new Paragraph("STT", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tên hàng hóa", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Đơn vị", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Ngày giao", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Số lượng", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Đơn giá", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Thành tiền", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			double tongtien = 0;
			query = "select b.TEN + ' ' + b.QUYCACH as spTen, ISNULL(c.donvi, 'NA') as donvi, a.SOLUONG, a.NGAYNHAN, a.SOLUONG, a.DONGIA  " +
					"from ERP_MUAHANG_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    " +
					"inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
					"where a.MUAHANG_FK = '" + id + "'";
			
			int soSP = 0;
			ResultSet infoSanPham = db.getScrol(query);
			if(infoSanPham != null)
			{
				infoSanPham.beforeFirst();
				while(infoSanPham.next())
				{
					soSP++;
				}
				
				int stt = 0;
				infoSanPham.beforeFirst();
				while(infoSanPham.next())
				{
					cell = new PdfPCell(new Paragraph(Integer.toString(stt + 1), new Font(bf, 7, Font.NORMAL)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(infoSanPham.getString("spTen") , new Font(bf, 7, Font.BOLD)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(infoSanPham.getString("donvi") , new Font(bf, 7, Font.BOLD)));
					if(stt == soSP - 1)
					{
						cell.setRowspan(2);
					}
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(infoSanPham.getString("NgayNhan"), new Font(bf, 7, Font.NORMAL)));
					if(stt == soSP - 1)
					{
						cell.setRowspan(2);
					}
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(formatter.format(infoSanPham.getDouble("SoLuong")), new Font(bf, 7, Font.NORMAL)));
					if(stt == soSP - 1)
					{
						cell.setRowspan(2);
					}
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(formatter.format(infoSanPham.getDouble("DonGia")), new Font(bf, 7, Font.NORMAL)));
					if(stt == soSP - 1)
					{
						cell.setRowspan(2);
					}
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(formatter.format(infoSanPham.getDouble("SoLuong") * infoSanPham.getDouble("DonGia") ), new Font(bf, 7, Font.NORMAL)));
					if(stt == soSP - 1)
					{
						cell.setRowspan(2);
					}
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					tongtien += infoSanPham.getDouble("SoLuong") * infoSanPham.getDouble("DonGia");
					stt++;
				}
				infoSanPham.close();
				
			}
			
			
			cell = new PdfPCell();
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell.addElement(new Paragraph("Ghi chú: ", new Font(bf, 7, Font.NORMAL)));
			cell.addElement(new Paragraph("1.Giao hàng đúng thời gian yêu cầu, nếu có thay đổi phải báo trước 15 ngày", new Font(bf, 7, Font.NORMAL)));
			cell.addElement(new Paragraph("2.Chất lượng hàng hóa phải đảm bảo đúng các đặc tính kỹ thuật như đã thỏa thuận hoặc theo mẫu (nếu có)", new Font(bf, 7, Font.NORMAL)));
			cell.addElement(new Paragraph("3.Số lượng dao động " + dungsai + "% trên tổng số lượng đặt hàng", new Font(bf, 7, Font.NORMAL)));
			
			tbSanPham.addCell(cell);
			
			
			
			cell = new PdfPCell(new Paragraph("Cộng tiền hàng", new Font(bf, 7, Font.BOLD)));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(formatter.format(tongtien), new Font(bf, 7, Font.BOLD)));
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tiền thuế GTGT 10%", new Font(bf, 7, Font.BOLD)));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(formatter.format(tongtien * 0.1), new Font(bf, 7, Font.BOLD)));
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tổng cộng", new Font(bf, 7, Font.BOLD)));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(formatter.format(tongtien * 1.1), new Font(bf, 7, Font.BOLD)));
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbSanPham.addCell(cell);
			
			document.add(tbSanPham);
			
			/********************END INFO NCC ***********************/
			
			
			
			
			
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(5);
			tableFooter.setWidthPercentage(100);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.getDefaultCell().setBorder(0);
			tbSanPham.setSpacingBefore(5.0f);
			tableFooter.setWidths(new float[]{30.0f, 30.0f, 30.0f, 30.0f, 30.0f});
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Xác nhận nhà cung cấp", new Font(bf, 7, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cell12 = new PdfPCell(new Paragraph("Phòng cung ứng", new Font(bf, 7, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12.setColspan(2);
			
			PdfPCell cell13 = new PdfPCell(new Paragraph("Phòng kế toán", new Font(bf, 7, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cell14 = new PdfPCell(new Paragraph("Ban tổng giám đốc", new Font(bf, 7, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			
			cell11.setBorderWidth(0);
			cell11.setBorderWidthLeft(1);
			cell11.setBorderWidthRight(1);
			cell11.setBorderWidthTop(1);
			cell11.setBorderWidthBottom(1);
			
			cell12.setBorderWidth(1);
			cell12.setBorderWidthLeft(0);
			
			cell13.setBorderWidth(1);
			cell13.setBorderWidthLeft(0);
			
			cell14.setBorderWidth(1);
			cell14.setBorderWidthLeft(0);

			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			
			
			cell11 = new PdfPCell(new Paragraph("", new Font(bf, 7, Font.NORMAL)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell12 = new PdfPCell(new Paragraph("Người lập", new Font(bf, 7, Font.BOLD)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell13 = new PdfPCell(new Paragraph("Giám đốc cung ứng", new Font(bf, 7, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell14 = new PdfPCell(new Paragraph(" ", new Font(bf, 7, Font.NORMAL)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cell15 = new PdfPCell(new Paragraph(" ", new Font(bf, 7, Font.NORMAL)));
			cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell11.setBorder(0);
			cell12.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);
			
			cell11.setBorderWidthLeft(1);
			cell11.setBorderWidthRight(1);
			
			cell12.setBorderWidthRight(1);
			cell12.setBorderWidthBottom(1);
			
			cell13.setBorderWidthRight(1);
			cell13.setBorderWidthBottom(1);
			
			cell14.setBorderWidthRight(1);
			cell15.setBorderWidthRight(1);
			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			//Them khoang trang
			for(int i = 0; i <= 4; i++)
			{
				for(int j = 0; j < 5; j++)
				{
					PdfPCell cellFooter = new PdfPCell(new Paragraph(" ", new Font(bf, 7, Font.NORMAL)));
					//cellFooter.setColspan(5);
					cellFooter.setBorder(0);
					
					if(j == 0)
					{
						cellFooter.setBorderWidthLeft(1);
					}
					
					cellFooter.setBorderWidthRight(1);
					
					if(i == 4)
					{
						cellFooter.setBorderWidthBottom(1);
					}
					
					tableFooter.addCell(cellFooter);
				}
			}
			
			document.add(tableFooter);
			document.close(); 
		}
		catch(Exception e)
		{
			System.out.println("Exception In PDF: " + e.getMessage());
		}
		
	}
		

	private void CreatePO_English(Document document, ServletOutputStream outstream, HttpServletResponse response, HttpServletRequest request, dbutils db) 
	{
		HttpSession session = request.getSession();
		
		String id = request.getParameter("dmhId");
		String ctyId = (String)session.getAttribute("congtyId");
		
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			
			/********************** INFO CONGTY *******************************/
			
			PdfPTable tbHeader = new PdfPTable(3);
			tbHeader.setWidthPercentage(100);
			tbHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crHeader = {40.0f, 200.0f, 100.0f};
			tbHeader.setWidths(crHeader);
			tbHeader.getDefaultCell().setBorder(0);
			
			PdfPCell cell = null;
			
			try
			{
				//Image pic = Image.getInstance("D:\\project\\TraphacoERP\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png");
				
				Image pic = Image.getInstance("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png");
				if(pic != null)
				{
					System.out.println("______Load Images Thanh Cong....");
					pic.setBorder(0);
					pic.setAlignment(Element.ALIGN_CENTER);
					tbHeader.addCell(pic);
				}
				else
				{
					cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
					cell.setBorder(0);
					tbHeader.addCell(cell);
				}
			}
			catch (Exception e) 
			{
				System.out.println("Exception load Images: " + e.getMessage());
				cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbHeader.addCell(cell);
				
			}
			

			String query = "select ma, N' NEW TOYO (VIETNAM) ALUMINIUM PAPER PACKAGING CO., LTD' as ten, N' Road No. 3, The Industrial of Linh Trung Ex- Processing Zone 2' as diachi, " +
								"N' Thu Duc District, HCMC, Vietnam' as masothue, " +
								"isnull(dienthoai, 'NA') as dienthoai, isnull(fax, 'NA') as fax " +
							"from erp_congty where pk_seq = '" + ctyId + "'";
			ResultSet infoCty = db.get(query);
			
			if(infoCty.next())
			{
				PdfPTable tbSubHeader = new PdfPTable(1);
				tbSubHeader.setWidthPercentage(100);
				tbSubHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubHeader.getDefaultCell().setBorder(0);
				float[] crSubHeader = {200.0f};
				tbSubHeader.setWidths(crSubHeader);
				
				cell = new PdfPCell(new Paragraph(infoCty.getString("ten").toUpperCase(), new Font(bf, 8, Font.BOLD)));
				cell.setBorder(0);
				tbSubHeader.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(infoCty.getString("diachi"), new Font(bf, 8, Font.BOLD)));
				cell.setBorder(0);
				tbSubHeader.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(infoCty.getString("masothue"), new Font(bf, 8, Font.BOLD)));
				cell.setBorder(0);
				tbSubHeader.addCell(cell);
				
				tbHeader.addCell(tbSubHeader);
				
				
				PdfPTable tbSubHeader2 = new PdfPTable(1);
				tbSubHeader2.setWidthPercentage(100);
				tbSubHeader2.setHorizontalAlignment(Element.ALIGN_LEFT);
				float[] crSubHeader2 = {100.0f};
				tbSubHeader2.setWidths(crSubHeader2);
				

				cell = new PdfPCell(new Paragraph("[T]: " + infoCty.getString("dienthoai"), new Font(bf, 7, Font.BOLD)));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("[F]: " + infoCty.getString("fax"), new Font(bf, 7, Font.BOLD)));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("[E]: purchasing@newtoyovn.com", new Font(bf, 7, Font.BOLD)));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("[W]: www.newtoyovn.com", new Font(bf, 7, Font.BOLD)));
				cell.setBorderWidth(0);
				cell.setBorderWidthLeft(1);
				tbSubHeader2.addCell(cell);
				
				tbHeader.addCell(tbSubHeader2);
				
			}
			infoCty.close();
			
			
			document.add(tbHeader);
			
			/********************** END INFO CONGTY *******************************/
			
			
			/********************** INFO NHA CUNG CAP *******************************/
			
			PdfPTable tbNCC = new PdfPTable(2);
			tbNCC.setWidthPercentage(100);
			tbNCC.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crNcc = {200.0f, 150.0f};
			tbNCC.setWidths(crNcc);
			tbNCC.getDefaultCell().setBorder(0);
			tbNCC.setSpacingBefore(10.0f);
			
			
			query = "select c.prefix + a.prefix + cast(a.pk_seq as varchar(10)) as poId, a.ngaymua, isnull(a.dungsai, 0) as dungsai,  " +
						"isnull(b.ten, 'NA') as nccTen, isnull(b.diachi, 'NA') as diachi, isnull(b.dienthoai, 'NA') as dienthoai, isnull(b.fax, 'NA') as fax, isnull(b.ten_nguoilienhe, 'NA') as nguoilienhe, b.hanmucno  " +
					"from erp_muahang a inner join ERP_NhaCungCap b on a.nhacungcap_fk = b.pk_seq  " +
						"inner join ERP_DonViThucHien c on a.donvithuchien_fk = c.pk_seq  " +
					"where a.pk_seq = '" + id + "'";
			
			System.out.println("___Init NCC: " + query);
			
			ResultSet infoNcc = db.get(query);
			String dungsai = "0";
			if(infoNcc.next())
			{
				PdfPTable tbSubNcc = new PdfPTable(1);
				tbSubNcc.setWidthPercentage(100);
				tbSubNcc.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNcc.getDefaultCell().setBorder(1);
				float[] crSubNcc = {200.0f};
				tbSubNcc.setWidths(crSubNcc);
				
				cell = new PdfPCell(new Paragraph("SUPPLIER", new Font(bf, 8, Font.UNDERLINE)));
				cell.setBorderWidth(1);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);
				
				dungsai = infoNcc.getString("dungsai");
				cell = new PdfPCell(new Paragraph("   " + infoNcc.getString("nccTen"), new Font(bf, 8, Font.BOLD)));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   " + infoNcc.getString("diachi"), new Font(bf, 8, Font.NORMAL)));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   Fax: " + infoNcc.getString("fax") + "         Tel: " + infoNcc.getString("dienthoai"), new Font(bf, 8, Font.NORMAL)));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				tbSubNcc.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   Attn: " + infoNcc.getString("nguoilienhe"), new Font(bf, 8, Font.NORMAL)));
				cell.setBorderWidth(1);
				cell.setBorderWidthTop(0);
				tbSubNcc.addCell(cell);
				
	
				tbNCC.addCell(tbSubNcc);
				
				
				
				PdfPTable tbSubNcc2 = new PdfPTable(1);
				tbSubNcc2.setWidthPercentage(100);
				tbSubNcc2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNcc2.getDefaultCell().setBorder(0);
				float[] crSubNcc2 = {200.0f};
				tbSubNcc2.setWidths(crSubNcc2);
				
				cell = new PdfPCell(new Paragraph("PURCHASE ORDER", new Font(bf, 12, Font.BOLD)));
				cell.setBorder(0);
				cell.setPadding(3.0f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
				tbSubNcc2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("P/O No.: " + infoNcc.getString("poId"), new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNcc2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Based on Ref: ", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNcc2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("Date: " + infoNcc.getString("ngaymua"), new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNcc2.addCell(cell);
				
	
				tbNCC.addCell(tbSubNcc2);
				
				document.add(tbNCC);
				
				
				
				/********************INFO NCC **********************/
				
				PdfPTable tbNCCInfo = new PdfPTable(2);
				tbNCCInfo.setWidthPercentage(100);
				tbNCCInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
				float[] crNccInfo = {200.0f, 150.0f};
				tbNCCInfo.setWidths(crNccInfo);
				tbNCCInfo.getDefaultCell().setBorder(0);
				tbNCCInfo.setSpacingBefore(8.0f);
				tbNCCInfo.setSpacingAfter(8.0f);
				
				
				PdfPTable tbSubNccInfo = new PdfPTable(1);
				tbSubNccInfo.setWidthPercentage(100);
				tbSubNccInfo.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNccInfo.getDefaultCell().setBorder(0);
				float[] crSubNccInfo = {200.0f};
				tbSubNccInfo.setWidths(crSubNccInfo);
				
	
				cell = new PdfPCell(new Paragraph("   PAYMENT TERMS: " + "TT " + infoNcc.getString("hanmucno") + " days after B/L date", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNccInfo.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   SHIP TO: HoChiMinh City Port", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNccInfo.addCell(cell);
				
				tbNCCInfo.addCell(tbSubNccInfo);
				
				
				PdfPTable tbSubNccInfo2 = new PdfPTable(1);
				tbSubNccInfo2.setWidthPercentage(100);
				tbSubNccInfo2.setHorizontalAlignment(Element.ALIGN_LEFT);
				tbSubNccInfo2.getDefaultCell().setBorder(0);
				float[] crSubNccInfo2 = {150.0f};
				tbSubNccInfo2.setWidths(crSubNccInfo2);
				
	
				cell = new PdfPCell(new Paragraph("ETD: ", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNccInfo2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("ETA:", new Font(bf, 8, Font.NORMAL)));
				cell.setBorder(0);
				tbSubNccInfo2.addCell(cell);
				
				tbNCCInfo.addCell(tbSubNccInfo2);
				
				
				document.add(tbNCCInfo);
			}
			infoNcc.close();
			
			/********************END INFO NCC ***********************/
			
			
			
			/********************INFO SAN PHAM **********************/
			
			PdfPTable tbSanPham = new PdfPTable(7);
			tbSanPham.setWidthPercentage(100);
			tbSanPham.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] crSanpham = {30.0f, 250.0f, 60.0f, 60.0f, 60.0f, 60.0f, 60.0f};
			tbSanPham.setWidths(crSanpham);
			tbSanPham.getDefaultCell().setBorder(0);
			tbSanPham.setSpacingAfter(8.0f);
			
			
			cell = new PdfPCell(new Paragraph("Item No.", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Description", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Uom", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Date", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Quantity", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Unit Price", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Amount", new Font(bf, 7, Font.BOLD)));
			cell.setPadding(3.0f);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			double tongtien = 0;
			query = "select isnull(b.TEN1, b.Ten) + ' ' + b.QUYCACH as spTen, ISNULL(c.donvi, 'NA') as donvi, a.SOLUONG, a.NGAYNHAN, a.SOLUONG, a.DONGIA  " +
					"from ERP_MUAHANG_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    " +
					"inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
					"where a.MUAHANG_FK = '" + id + "'";
			
			int soSP = 0;
			ResultSet infoSanPham = db.getScrol(query);
			if(infoSanPham != null)
			{
				infoSanPham.beforeFirst();
				while(infoSanPham.next())
				{
					soSP++;
				}
				
				int stt = 0;
				infoSanPham.beforeFirst();
				while(infoSanPham.next())
				{
					cell = new PdfPCell(new Paragraph(Integer.toString(stt + 1), new Font(bf, 7, Font.NORMAL)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(infoSanPham.getString("spTen") , new Font(bf, 7, Font.BOLD)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(infoSanPham.getString("donvi") , new Font(bf, 7, Font.BOLD)));
					if(stt == soSP - 1)
					{
						cell.setRowspan(2);
					}
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(infoSanPham.getString("NgayNhan"), new Font(bf, 7, Font.NORMAL)));
					if(stt == soSP - 1)
					{
						cell.setRowspan(2);
					}
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(formatter.format(infoSanPham.getDouble("SoLuong")), new Font(bf, 7, Font.NORMAL)));
					if(stt == soSP - 1)
					{
						cell.setRowspan(2);
					}
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(formatter.format(infoSanPham.getDouble("DonGia")), new Font(bf, 7, Font.NORMAL)));
					if(stt == soSP - 1)
					{
						cell.setRowspan(2);
					}
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(formatter.format(infoSanPham.getDouble("SoLuong") * infoSanPham.getDouble("DonGia") ), new Font(bf, 7, Font.NORMAL)));
					if(stt == soSP - 1)
					{
						cell.setRowspan(2);
					}
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					tbSanPham.addCell(cell);
					
					tongtien += infoSanPham.getDouble("SoLuong") * infoSanPham.getDouble("DonGia");
					stt++;
				}
				infoSanPham.close();
				
			}
			
			
			cell = new PdfPCell();
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell.addElement(new Paragraph("Remarks: ", new Font(bf, 7, Font.UNDERLINE)));
			cell.addElement(new Paragraph("1.PLEASE SHIP AS OUR REQUIRED SCHEDULE.", new Font(bf, 7, Font.NORMAL)));
			cell.addElement(new Paragraph("2.ADVISE IF UNABLE TO SHIP ON SCHEDULE.", new Font(bf, 7, Font.NORMAL)));
			cell.addElement(new Paragraph("3.  " + dungsai + "%  TORELANT OF QUANTITIES IS ACCEPTABLE.", new Font(bf, 7, Font.NORMAL)));
			cell.addElement(new Paragraph("4.SHOW ORDER NO. ON ALL PACKING LIST & INVOICE.", new Font(bf, 7, Font.NORMAL)));
			cell.addElement(new Paragraph("5.ENCLOSE DETAILS PACKING LIST.", new Font(bf, 7, Font.NORMAL)));
			cell.addElement(new Paragraph("6.INVOICE IN TRIPLICATE.", new Font(bf, 7, Font.NORMAL)));
			cell.addElement(new Paragraph("  Pls confirm receiving to us by fax/email. Thank you!", new Font(bf, 7, Font.BOLD)));
			cell.addElement(new Paragraph("  ", new Font(bf, 7, Font.BOLD)));
			
			tbSanPham.addCell(cell);
			
			
			
			cell = new PdfPCell(new Paragraph("TOTAL", new Font(bf, 7, Font.BOLD)));
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(formatter.format(tongtien), new Font(bf, 7, Font.BOLD)));
			cell.setColspan(5);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbSanPham.addCell(cell);
			
			
			document.add(tbSanPham);
			
			/********************END INFO NCC ***********************/
			
			
			
			
			
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(4);
			tableFooter.setWidthPercentage(100);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbSanPham.getDefaultCell().setBorder(0);
			tbSanPham.setSpacingBefore(5.0f);
			tableFooter.setWidths(new float[]{50.0f, 50.0f, 50.0f, 50.0f});
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Supplier's Confirmation", new Font(bf, 7, Font.BOLDITALIC)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cell12 = new PdfPCell(new Paragraph("Prepared by", new Font(bf, 7, Font.BOLDITALIC)));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cell13 = new PdfPCell(new Paragraph("Financial Control", new Font(bf, 7, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell cell14 = new PdfPCell(new Paragraph("Approved by", new Font(bf, 7, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			
			cell11.setBorderWidth(0);
			cell11.setBorderWidthLeft(1);
			cell11.setBorderWidthRight(1);
			cell11.setBorderWidthTop(1);
			cell11.setBorderWidthBottom(1);
			
			cell12.setBorderWidth(1);
			cell12.setBorderWidthLeft(0);
			
			cell13.setBorderWidth(1);
			cell13.setBorderWidthLeft(0);
			
			cell14.setBorderWidth(1);
			cell14.setBorderWidthLeft(0);

			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell12);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			
			
			//Them khoang trang
			for(int i = 0; i <= 4; i++)
			{
				for(int j = 0; j < 4; j++)
				{
					PdfPCell cellFooter = new PdfPCell(new Paragraph(" ", new Font(bf, 7, Font.NORMAL)));
					cellFooter.setBorder(0);
					
					if(j == 0)
					{
						cellFooter.setBorderWidthLeft(1);
					}
					
					cellFooter.setBorderWidthRight(1);
					
					if(i == 4)
					{
						cellFooter.setBorderWidthBottom(1);
					}
					
					tableFooter.addCell(cellFooter);
				}
			}
			
			document.add(tableFooter);
			document.close(); 
		}
		catch(Exception e)
		{
			System.out.println("Exception In PDF: " + e.getMessage());
		}
		
	}
	
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}

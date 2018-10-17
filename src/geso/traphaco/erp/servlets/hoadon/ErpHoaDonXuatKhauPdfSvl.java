package geso.traphaco.erp.servlets.hoadon;

import geso.traphaco.center.beans.doctien.DocTien;
 
import geso.traphaco.center.beans.doctien.DocTienEN;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
 
import geso.traphaco.erp.beans.hoadon.IErpHoaDon;
import geso.traphaco.erp.beans.hoadon.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadon.imp.ErpHoaDon;
 
 

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
 

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
 
import com.aspose.cells.FileFormatType;
 
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
 
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;


import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
 
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
 

public class ErpHoaDonXuatKhauPdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final float CM = 28.3464f;

	public ErpHoaDonXuatKhauPdfSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		IErpHoaDon obj;
		String userId;
		Utility util = new Utility();

		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String ddhId = util.antiSQLInspection(request.getParameter("ddhId"));
		if (ddhId == null)
			ddhId = "";
		
		// HD tai chinh : 0 ; hoa don tra hang ncc :1
		String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
		if (loaihd == null)
			loaihd = "";
		
		String inHd = util.antiSQLInspection(request.getParameter("inHd"));
		if (inHd == null)
			inHd = "";

		obj = new ErpHoaDon();
		obj.setUserId(userId);

		
		
		if(loaihd.trim().equals("XK")){  //**************  DÙNG CHO HÓA ĐƠN XUẤT KHẨU = TIẾNG ANH  ************//
			
			obj.hoadon_pdf_XK(ddhId);
			try{
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition"," inline; filename=HoaDonXuatKhau.pdf");
				
				float CONVERT = 28.346457f;
				float PAGE_LEFT = 1f*CONVERT, PAGE_RIGHT = 1f*CONVERT, PAGE_TOP = 1.0f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
				Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
			
				
				ServletOutputStream outstream = response.getOutputStream();
				this.Hoadon_XK(document, outstream, obj);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
		}
		
		
	
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	/**
	 * Xá»­ lÃ½ Ä‘á»ƒ in sáº£n pháº©m trong pháº§n in hÃ³a Ä‘Æ¡n trong nÆ°á»›c
	 * @param hdBean
	 * @param _tenList
	 * @param sanpham
	 * @param spIndex
	 * @param tenSp
	 * @param qcSp
	 * @param sokytu1sp
	 * @param prev_tensp
	 * @param temp_tensp
	 * @param changeSpCore
	 * @return
	 */
	
	protected boolean xuLyTenList(IErpHoaDon hdBean, List<String> _tenList, IErpHoaDon_SP sanpham, int spIndex, String tenSp, String qcSp, int sokytu1sp, String prev_tensp, String temp_tensp, boolean changeSpCore) 
	{
		boolean isSpCone = sanpham.getDvkdId() != null && sanpham.getDvkdId().equals("100004") && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0 ||  sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >=0 );
		//System.out.println("xuLyTenList .isSpCone : "+sanpham.getMaSanPham());
		String[] words = new String[0];
		String _ten = "", _ten2 = "";
		if(!isSpCone || changeSpCore) {
			words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
			_ten = "";
			_ten2 = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xá»­ lÃ½ khi 1 tá»« > sá»‘ kÃ½ tá»± 1 dÃ²ng
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
					_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng má»›i
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
			
			//Insert quy cÃ¡ch group náº¿u lÃ  CONE
			if(isSpCone) {
				_tenList.add(sanpham.getQuyCachGroup());
			}
			
		} else {
			changeSpCore = false;
		}
		String sanpham_ghichu = hdBean.getSanphamGhiChu().get(sanpham.getIdSanPham());
				
		int countGhiChu = 0;
		String[] arrGhiChu = new String[] {};
		if (sanpham_ghichu != null && sanpham_ghichu.trim().length() > 0) {
			arrGhiChu = sanpham_ghichu.split("__");
			countGhiChu = arrGhiChu.length;
			
			for (int j = 0; j < arrGhiChu.length; j++) {
				if (arrGhiChu[j].equals("NA"))
					arrGhiChu[j] = "";
			}

			if (countGhiChu > 3)
				countGhiChu = 3;
		}
		


		// Xu ly quy cach
		int countSoDongQuyCach = 0;
		if(sanpham.getDvkdId().equals("100004")){
			// ná»‘i cÃ¡i ghi chÃº liÃªn tiáº¿p vÃ o,náº¿u lÃ  á»‘ng cone
			String ghichu_=(arrGhiChu.length >0?arrGhiChu[0]:"");
			qcSp=qcSp.trim()  +(ghichu_.trim().length() >0?": " +ghichu_:"");
		}
		words = qcSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong quy cach
		_ten = "";
		for (int _i = 0; _i < words.length; _i++) {
			// Xá»­ lÃ½ khi 1 tá»« > 40 kÃ½ tá»±
			if (words[_i].length() > sokytu1sp) {
				if (_ten.trim().length() > 0) {
					_tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
					countSoDongQuyCach++;
					
				}
				_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng má»›i
				countSoDongQuyCach++;
				_ten = ""; // reset _ten
			} else {
				_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
				if (_ten2.length() > sokytu1sp) {
					_tenList.add(_ten);
					countSoDongQuyCach++;
					_ten = words[_i];
				} else {
					_ten = _ten2;
				}
			}
		}
		if (_ten.trim().length() > 0) {
			_tenList.add(_ten);
			countSoDongQuyCach++;
		}
		if(countSoDongQuyCach == 0 && !sanpham.getDvkdId().equals("100005")) {
			_tenList.add("KhÃ´ng mÃ u");
		}

		
		for (int _j = (isSpCone || sanpham.getDvkdId().equals("100004")?1:0); _j < arrGhiChu.length; _j++) {
			
			words = arrGhiChu[_j].trim().replaceAll("  ", " ") .split(" "); // Tat ca cac tu trong ghi chu
			_ten = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xá»­ lÃ½ khi 1 tá»« > 45 kÃ½ tá»±
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
					_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng má»›i
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0)
				_tenList.add(_ten);
		}
		
		return changeSpCore;
	}
	
	protected boolean xuLyTenList_NuocNgoai(IErpHoaDon hdBean, List<String> _tenList, IErpHoaDon_SP sanpham, int spIndex, String tenSp, String qcSp, int sokytu1sp, String prev_tensp, String temp_tensp, boolean changeSpCore) 
	{
		boolean isSpCone = sanpham.getDvkdId() != null && sanpham.getDvkdId().equals("100004") && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0 ||  sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0);

		String[] words = new String[0];
		String _ten = "", _ten2 = "";
		if(!isSpCone || changeSpCore) {
			words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
			_ten = "";
			_ten2 = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xá»­ lÃ½ khi 1 tá»« > sá»‘ kÃ½ tá»± 1 dÃ²ng
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
					_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng má»›i
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
			
			//Insert quy cÃ¡ch group náº¿u lÃ  CONE
			if(isSpCone) {
				_tenList.add(sanpham.getQuyCachGroup());
			}
			
		} else {
			changeSpCore = false;
		}
		// int soDongDanhChoTenSanPham = _tenList.size();

		// Xu ly quy cach
		int countSoDongQuyCach = 0;
		words = qcSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong quy cach
		_ten = "";
		for (int _i = 0; _i < words.length; _i++) {
			// Xá»­ lÃ½ khi 1 tá»« > 40 kÃ½ tá»±
			if (words[_i].length() > sokytu1sp) {
				if (_ten.trim().length() > 0) {
					_tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
					countSoDongQuyCach++;
					
				}
				_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng má»›i
				countSoDongQuyCach++;
				_ten = ""; // reset _ten
			} else {
				_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
				if (_ten2.length() > sokytu1sp) {
					_tenList.add(_ten);
					countSoDongQuyCach++;
					_ten = words[_i];
				} else {
					_ten = _ten2;
				}
			}
		}
		if (_ten.trim().length() > 0) {
			_tenList.add(_ten);
			countSoDongQuyCach++;
		}
		if(countSoDongQuyCach == 0 && !sanpham.getDvkdId().equals("100005")) {
			_tenList.add("KhÃ´ng mÃ u");
		}

		
		return changeSpCore;
	}
	
	protected boolean xuLyGhiChuList(IErpHoaDon hdBean, List<String> _tenList, IErpHoaDon_SP sanpham, int sokytu1sp, boolean changeSpCore) 
	{
		String[] words = new String[0];
		String _ten = "", _ten2 = "";

		String sanpham_ghichu = hdBean.getSanphamGhiChu().get(sanpham.getIdSanPham());
		int countGhiChu = 0;
		String[] arrGhiChu = new String[] {};
		if (sanpham_ghichu != null && sanpham_ghichu.trim().length() > 0) {
			arrGhiChu = sanpham_ghichu.split("__");
			countGhiChu = arrGhiChu.length;
			
			for (int j = 0; j < arrGhiChu.length; j++) {
				if (arrGhiChu[j].equals("NA"))
					arrGhiChu[j] = "";
			}

			if (countGhiChu > 3)
				countGhiChu = 3;
		}

		for (int _j = 0; _j < arrGhiChu.length; _j++) {
			words = arrGhiChu[_j].trim().replaceAll("  ", " ") .split(" "); // Tat ca cac tu trong ghi chu
			_ten = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xá»­ lÃ½ khi 1 tá»« > 45 kÃ½ tá»±
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
					_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng má»›i
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0)
				_tenList.add(_ten);
		}
		
		return changeSpCore;
	}
	
	protected boolean xuLyGhiChu_HoaDon(IErpHoaDon hdBean, List<String> _ghichuList, String ghichu, int sokytu1sp, boolean ktra) 
	{
		String[] words = new String[0];
		String _ten = "", _ten2 = "";

		words = ghichu.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong quy cach
		_ten = "";
		for (int _i = 0; _i < words.length; _i++) {
			// Xá»­ lÃ½ khi 1 tá»« > 40 kÃ½ tá»±
			if (words[_i].length() > sokytu1sp)
			{
				if (_ten.trim().length() > 0) {
					_ghichuList.add(_ten); // ThÃªm dÃ²ng cÅ©
					
				}
				_ghichuList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng má»›i

				_ten = ""; // reset _ten
			} else {
				_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
				if (_ten2.length() > sokytu1sp) {
					_ghichuList.add(_ten);						
					_ten = words[_i];
				} else {
					_ten = _ten2;
				}
			}
		}
		if (_ten.trim().length() > 0) {
			_ghichuList.add(_ten);
			
		}
		
		return ktra;
	}
	
	
	/**
	 * Xá»­ lÃ½ Ä‘á»ƒ in sáº£n pháº©m trong pháº§n in hÃ³a Ä‘Æ¡n nÆ°á»›c ngoÃ i
	 * @param hdBean
	 * @param _tenList
	 * @param sanpham
	 * @param spIndex
	 * @param tenSp
	 * @param qcSp
	 * @param sokytu1sp
	 * @param prev_tensp
	 * @param temp_tensp
	 * @param changeSpCore
	 * @return
	 */
	protected boolean xuLyTenNNList(IErpHoaDon hdBean, List<String> _tenList, IErpHoaDon_SP sanpham, int spIndex, String tenSp, String qcSp, int sokytu1sp, String prev_tensp, String temp_tensp, boolean changeSpCore) 
	{
		boolean isSpCone = sanpham.getDvkdId() != null && sanpham.getDvkdId().equals("100004") && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0 || sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0);

		String[] words = new String[0];
		String _ten = "", _ten2 = "";
		if(!isSpCone || changeSpCore) {
			words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
			_ten = "";
			_ten2 = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xá»­ lÃ½ khi 1 tá»« > sá»‘ kÃ½ tá»± 1 dÃ²ng
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
					_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng má»›i
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
			
		} else {
			changeSpCore = false;
		}
		// int soDongDanhChoTenSanPham = _tenList.size();

		// Xu ly quy cach: cone ko cáº§n hiá»‡n
		if(!isSpCone) {
			words = qcSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong quy cach
			_ten = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xá»­ lÃ½ khi 1 tá»« > 40 kÃ½ tá»±
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) {
						_tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
						
					}
					_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng má»›i
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
		}

		String sanpham_ghichu = hdBean.getSanphamGhiChu().get(sanpham.getIdSanPham());
		int countGhiChu = 0;
		String[] arrGhiChu = new String[] {};
		if (sanpham_ghichu != null && sanpham_ghichu.trim().length() > 0) {
			arrGhiChu = sanpham_ghichu.split("__");
			countGhiChu = arrGhiChu.length;

			for (int j = 0; j < arrGhiChu.length; j++) {
				if (arrGhiChu[j].equals("NA"))
					arrGhiChu[j] = "";
			}

			if (countGhiChu > 3)
				countGhiChu = 3;
		}

		for (int _j = 0; _j < arrGhiChu.length; _j++) {
			words = arrGhiChu[_j].trim().replaceAll("  ", " ") .split(" "); // Tat ca cac tu trong ghi chu
			_ten = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xá»­ lÃ½ khi 1 tá»« > 45 kÃ½ tá»±
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
					_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng má»›i
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0)
				_tenList.add(_ten);
		}
		
		return changeSpCore;
	}

	/**
	 * In hÃ³a Ä‘Æ¡n trong nÆ°á»›c excel
	 * @param out
	 * @param hdBean
	 */

	protected boolean GetListStrDaXuLy(List<String> _tenList ,String tenSp,  int sokytu1sp) 
	{
		String[] words = new String[0];
		String _ten = "", _ten2 = "";
		
			words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
			_ten = "";
			_ten2 = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xá»­ lÃ½ khi 1 tá»« > sá»‘ kÃ½ tá»± 1 dÃ²ng
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
					_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng má»›i
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
			
			return true;
	}
/*	
	private void HoaDonTrongNuocPdf(Document document,
			ServletOutputStream outstream, IErpHoaDon hdBean)
			throws IOException {
		try {
			
			 * IErpCauHinhInHoaDon soNumber = new ErpCauHinhInHoaDon();
			 * soNumber.initWithName("SO");
			 

			IErpCauHinhInHoaDon khachhang_config = new ErpCauHinhInHoaDon();
			khachhang_config.initWithName("CUSTOMMER");
			khachhang_config.dbClose();

			IErpCauHinhInHoaDon dh_config = new ErpCauHinhInHoaDon();
			dh_config.initWithName("DETAILS");
			dh_config.dbClose();

			IErpCauHinhInHoaDon footer_config = new ErpCauHinhInHoaDon();
			footer_config.initWithName("FOOTER");
			footer_config.dbClose();

			// KÃ­ch thÆ°á»›c theo Ä‘Æ¡n vá»‹ cm
			float CONVERT = 28.346457f; // = 1cm
			// Header
			float[] TABLE_HEADER_WIDTHS = { 18.0f * CONVERT };
			float TABLE_HEADER_LEFT = khachhang_config.getMarginLeft() * CONVERT, TABLE_HEADER_TOP = khachhang_config
					.getMarginTop() * CONVERT, TABLE_HEADER_BOTTOM = khachhang_config
					.getMarginBottom() * CONVERT;
			// float TABLE_HEADER_LEFT = 7.0f*CONVERT, TABLE_HEADER_TOP =
			// 0.0f*CONVERT, TABLE_HEADER_BOTTOM = 1.6f*CONVERT;
			int BORDER_WIDTH = Rectangle.NO_BORDER;

			// Products
			int TABLE_NUM_ROWS = 16;// dh_config.getNumberOfRow(); //6
			float TABLE_LEFT = 0.0f * CONVERT, TABLE_RIGHT = 0.0f * CONVERT, TABLE_TOP = 0.0f * CONVERT, TABLE_BOTTOM = 0.0f * CONVERT;
			float TABLE_HEIGHT = dh_config.getTableHeight() * CONVERT, TABLE_ROW_HEIGHT = TABLE_HEIGHT
					/ TABLE_NUM_ROWS;
			// float TABLE_HEIGHT = 9.0f*CONVERT, TABLE_ROW_HEIGHT =
			// TABLE_HEIGHT/TABLE_NUM_ROWS;
			float[] TABLE_COLUMN_WIDTHS = { dh_config.getNoColumn() * CONVERT,
					dh_config.getProductColumn() * CONVERT,
					dh_config.getUnitColumn() * CONVERT,
					dh_config.getQuantityColumn() * CONVERT,
					dh_config.getUniPriceColumn() * CONVERT,
					dh_config.getAmoutColumn() * CONVERT };
			// float[] TABLE_COLUMN_WIDTHS = {1.0f*CONVERT, 6.7f*CONVERT,
			// 2.3f*CONVERT, 2.1f*CONVERT, 2.1f*CONVERT, 3.8f*CONVERT};
			float[] TABLE_COLUMN_PADDING_LEFTS = { 0.1f * CONVERT,
					0.0f * CONVERT, 0.0f * CONVERT, 0.0f * CONVERT,
					0.0f * CONVERT, 0.0f * CONVERT };
			int[] TABLE_COLUMN_ALIGNS = { Element.ALIGN_LEFT,
					Element.ALIGN_LEFT, Element.ALIGN_CENTER,
					Element.ALIGN_RIGHT, Element.ALIGN_RIGHT,
					Element.ALIGN_RIGHT };

			// Amount
			float TABLE_AMOUNT_LEFT_WIDTH = 7.0f * CONVERT;
			float TABLE_AMOUNT_RIGHT_WIDTH = 11.0f * CONVERT;
			float[] TABLE_AMOUNT_ROW_HEIGHTS = {
					footer_config.getMarginLeft() * CONVERT,
					footer_config.getMarginRight() * CONVERT,
					footer_config.getMarginTop() * CONVERT,
					footer_config.getMarginBottom() * CONVERT,
					footer_config.getPaddingLeft() * CONVERT };
			float[] TABLE_AMOUNT_LEFT_PADDING_LEFTS = { 0.0f * CONVERT,
					4.8f * CONVERT, 0.0f * CONVERT, 0.0f * CONVERT };
			float[] TABLE_AMOUNT_RIGHT_PADDING_RIGHTS = { 0.5f * CONVERT,
					0.5f * CONVERT, 0.5f * CONVERT, 0.0f * CONVERT,
					0.0f * CONVERT };

			NumberFormat formatter = new DecimalFormat("#,###,###.00");
			NumberFormat formatter2 = new DecimalFormat("#,###,###.##");
			NumberFormat formatter3 = new DecimalFormat("#,###,###");
			PdfWriter.getInstance(document, outstream);
			document.open();

			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font headerFont = new Font(bf, khachhang_config.getFontSize(),
					Font.BOLD);
			Font donhangFont = new Font(bf, 11, Font.NORMAL);
			Font productFont = new Font(bf, dh_config.getLineSpacing(),
					Font.NORMAL);
			// Font font10bold = new Font(bf, 10, Font.BOLD);

			
			 * Font font = new Font(bf, 13, Font.BOLD); Font font2 = new
			 * Font(bf, 8, Font.BOLD); Font font9bold = new Font(bf, 9,
			 * Font.BOLD); Font font11bold = new Font(bf, 11, Font.BOLD); Font
			 * font12bold = new Font(bf, 12, Font.BOLD); Font font13bold = new
			 * Font(bf, 13, Font.BOLD); Font font14bold = new Font(bf, 14,
			 * Font.BOLD); Font font15bold = new Font(bf, 15, Font.BOLD); Font
			 * font16bold = new Font(bf, 16, Font.BOLD); Font font9 = new
			 * Font(bf, 9, Font.NORMAL); Font font10 = new Font(bf, 10,
			 * Font.NORMAL); Font font11 = new Font(bf, 11, Font.NORMAL); Font
			 * font12 = new Font(bf, 12, Font.NORMAL); Font font13 = new
			 * Font(bf, 13, Font.NORMAL); Font font14 = new Font(bf, 14,
			 * Font.NORMAL);
			 

			dbutils db = new dbutils();
			String sql = "select * from Erp_KhachHang where pk_seq = '"
					+ hdBean.getNppId() + "'";
			System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] sql = "
					+ sql);
			String address = "";
			String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";

			try {
				ResultSet rs = db.get(sql);
				if (rs.next()) {
					if (rs.getString("MST") != null)
						taxCode = rs.getString("MST");
					address = rs.getString("DiaChi");
					if (rs.getString("NguoiLienhe") != null)
						name_of_buyer = rs.getString("NguoiLienhe");
					name_of_company = rs.getString("Ten");
				}
				rs.close();
				db.shutDown();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			String ghichu = hdBean.getGhiChu();
			if (ghichu == null || ghichu.trim().length() == 0) {
				ResultSet rs_hopdong = hdBean.getHopdongRs();
				if (rs_hopdong != null) {
					try {
						while (rs_hopdong.next()) {
							if (rs_hopdong.getString("pk_seq").trim()
									.equals(hdBean.getHopdongId())) {
								ghichu = rs_hopdong.getString("mahopdong");
							}
						}
					} catch (Exception e) {
					}
				}

				if (ghichu == null || ghichu.trim().length() == 0) {
					ghichu = hdBean.gethinhthuctt();
				}

				if (ghichu == null)
					ghichu = " ";
			}

			String thue = "" + hdBean.getVAT();
			System.out.println("Thue la: " + thue);

			// Váº½ HEADER
			PdfPTable table = new PdfPTable(TABLE_HEADER_WIDTHS.length);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidths(TABLE_HEADER_WIDTHS);
			table.setWidthPercentage(100.0f);

			// Há»� vÃ  tÃªn ngÆ°á»�i mua hÃ ng
			PdfPCell cell;
			cell = new PdfPCell(new Paragraph(hdBean.getNguoiMuaHang(),
					headerFont));
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f * CONVERT);
			table.addCell(cell);

			// name_of_company =
			// "CÃ”NG TY TNHH Sáº¢N XUáº¤T THÆ¯Æ NG Máº I VÃ€ Dá»ŠCH Vá»¤ BAO BÃŒ TÄ‚NG PHÃš TÃ‚N";//

			// TÃªn Ä‘Æ¡n vá»‹
			float _fontSize = khachhang_config.getFontSize()
					- (name_of_company.length() > 50 ? (float) (name_of_company
							.length() - 50) / 6 : 0);
			Font headerFontKh = new Font(bf, _fontSize, Font.BOLD);

			// System.out.println("FONT SIZE = " + _fontSize);

			cell = new PdfPCell(new Paragraph(name_of_company.toUpperCase(),
					headerFontKh));
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f * CONVERT);
			table.addCell(cell);

			// address =
			// "Sá»‘ 10 Ä‘Æ°á»�ng Nguyá»…n Sinh Sáº¯c, áº¥p PhÃº Long, xÃ£ TÃ¢n PhÃº Ä�Ã´ng,ThÃ nh phá»‘ SaÄ�Ã©c, tá»‰nh Ä�á»“ng ThÃ¡p";
			_fontSize = address.length() > 100 ? 8.0f
					: address.length() > 95 ? 8.1f
							: address.length() > 90 ? 8.2f
									: address.length() > 80 ? 8.3f : 10.0f;
			Font headerFontDc = new Font(bf, _fontSize, Font.BOLD);

			// Ä�á»‹a chá»‰
			cell = new PdfPCell(new Paragraph(address, headerFontDc));
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f * CONVERT);
			table.addCell(cell);

			// MÃ£ sá»‘ thuáº¿
			cell = new PdfPCell(new Paragraph(taxCode, headerFont));
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f * CONVERT);
			table.addCell(cell);

			// HÃ¬nh thá»©c thanh toÃ¡n
			cell = new PdfPCell(new Paragraph(ghichu, headerFont));
			cell.setPaddingLeft(TABLE_HEADER_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.6f * CONVERT);
			table.addCell(cell);

			// Khoáº£ng trá»‘ng
			cell = new PdfPCell(new Paragraph("    ", headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_HEADER_BOTTOM);
			table.addCell(cell);

			document.add(table);

			// IN Sáº¢N PHáº¨M
			table = new PdfPTable(TABLE_COLUMN_WIDTHS.length);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidths(TABLE_COLUMN_WIDTHS);
			table.setWidthPercentage(100.0f);

			List<IErpHoaDon_SP> spList = hdBean.GetListSanPham();
			double total_amount = 0;
			int spIndex = 0;
			int rowIndex = 0;
			int soDongSp = 0;

			System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] TABLE_NUM_ROWS = " + TABLE_NUM_ROWS);
			System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] TABLE_ROW_HEIGHT = " + TABLE_ROW_HEIGHT);

			int sokytu1sp = hdBean.getSoKyTu1DongSanPham();
			if (sokytu1sp <= 0)
				sokytu1sp = 30;

			// Bá»� nhá»¯ng sáº£n pháº©m khÃ´ng in (trong trÆ°á»�ng há»£p hÃ³a Ä‘Æ¡n chÆ°a chá»‘t -
			// chÆ°a Ä‘Æ°á»£c bá»� nhá»¯ng sáº£n pháº©m ko in)
			for (int i = 0; i < spList.size(); i++) {
				if (!spList.get(i).getIn().equals("1")) {
					spList.remove(i);
					i--;
				}
			}

			while (spIndex < spList.size() && rowIndex < TABLE_NUM_ROWS) {
				IErpHoaDon_SP sanpham = (IErpHoaDon_SP) spList.get(spIndex);

				// TÃªn + quy cÃ¡ch
				String[] ttsp = sanpham.getTenXuatHoaDon().split("}");
				// System.out.println("TEN XUAT HOA DON = " +
				// sanpham.getTenXuatHoaDon());

				String tenSp = ttsp[0];
				String qcSp = ttsp.length > 1 ? ttsp[1].trim() : "";

				// Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				String[] words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat
																				// ca
																				// cac
																				// tu
																				// trong
																				// ten
																				// san
																				// pham
				String _ten = "", _ten2 = "";
				for (int _i = 0; _i < words.length; _i++) {
					// Xá»­ lÃ½ khi 1 tá»« > 30 kÃ½ tá»±
					if (words[_i].length() > sokytu1sp) {
						if (_ten.trim().length() > 0)
							_tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
						_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng
													// má»›i
						_ten = ""; // reset _ten
					} else {
						_ten2 = _ten
								+ (_ten.length() == 0 ? words[_i] : " "
										+ words[_i]);
						if (_ten2.length() > sokytu1sp) {
							_tenList.add(_ten);
							_ten = words[_i];
						} else {
							_ten = _ten2;
						}
					}
				}
				if (_ten.trim().length() > 0)
					_tenList.add(_ten);

				// Xu ly quy cach
				words = qcSp.trim().replaceAll("  ", " ").split(" "); // Tat ca
																		// cac
																		// tu
																		// trong
																		// quy
																		// cach
				_ten = "";
				for (int _i = 0; _i < words.length; _i++) {
					// Xá»­ lÃ½ khi 1 tá»« > 30 kÃ½ tá»±
					if (words[_i].length() > sokytu1sp) {
						if (_ten.trim().length() > 0)
							_tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
						_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng
													// má»›i
						_ten = ""; // reset _ten
					} else {
						_ten2 = _ten
								+ (_ten.length() == 0 ? words[_i] : " "
										+ words[_i]);
						if (_ten2.length() > sokytu1sp) {
							_tenList.add(_ten);
							_ten = words[_i];
						} else {
							_ten = _ten2;
						}
					}
				}
				if (_ten.trim().length() > 0)
					_tenList.add(_ten);

				// Xu ly ghi chu
				String sanpham_ghichu = hdBean.getSanphamGhiChu().get(
						sanpham.getIdSanPham());
				int countGhiChu = 0;
				String[] arrGhiChu = new String[] {};
				if (sanpham_ghichu != null
						&& sanpham_ghichu.trim().length() > 0) {
					arrGhiChu = sanpham_ghichu.split("__");
					countGhiChu = arrGhiChu.length;

					for (int j = 0; j < arrGhiChu.length; j++) {
						if (arrGhiChu[j].equals("NA"))
							arrGhiChu[j] = "";
					}

					if (countGhiChu > 3)
						countGhiChu = 3;
				}

				for (int _j = 0; _j < arrGhiChu.length; _j++) {
					words = arrGhiChu[_j].trim().replaceAll("  ", " ")
							.split(" "); // Tat ca cac tu trong ghi chu
					_ten = "";
					for (int _i = 0; _i < words.length; _i++) {
						// Xá»­ lÃ½ khi 1 tá»« > 30 kÃ½ tá»±
						if (words[_i].length() > sokytu1sp) {
							if (_ten.trim().length() > 0)
								_tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
							_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1
														// dÃ²ng má»›i
							_ten = ""; // reset _ten
						} else {
							_ten2 = _ten
									+ (_ten.length() == 0 ? words[_i] : " "
											+ words[_i]);
							if (_ten2.length() > sokytu1sp) {
								_tenList.add(_ten);
								_ten = words[_i];
							} else {
								_ten = _ten2;
							}
						}
					}
					if (_ten.trim().length() > 0)
						_tenList.add(_ten);
				}
				soDongSp += _tenList.size();

				
				 * for(int _i = 0; _i < _tenList.size(); _i++) {
				 * System.out.println("ErpHoaDonPdf.in DÃ²ng [" + _i + "] " +
				 * _tenList.get(_i)); }
				 

				double thanhtien = 0;

				try {
					thanhtien = Math.round(sanpham.getDonGia()
							* sanpham.getSoLuong());
					total_amount += sanpham.getDonGia() * sanpham.getSoLuong();
				} catch (Exception ex) {
				}

				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] Thanh tien["
				// + spIndex + "] = " + total_amount);
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] Quy doi["
				// + spIndex + "] = " + sanpham.getQuyDoiStr());

				String dg = " ";
				if (sanpham.getDonGia() > 0)
					dg = formatter.format(sanpham.getDonGia());

				String tt = " ";
				if (thanhtien > 0)
					tt = formatter.format(thanhtien);

				// In khi cÃ³ thá»ƒ hiá»‡n toÃ n bá»™ thÃ´ng tin cá»§a sáº£n pháº©m (1 dÃ²ng tÃªn
				// sp, 1 dÃ²ng quy cÃ¡ch, cÃ¡c dÃ²ng ghi chÃº)

				// In dong 1
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// rowIndex +", In dÃ²ng 1 sp " + spIndex);
				String[] arr = new String[] { Integer.toString(spIndex + 1),
						_tenList.size() > 0 ? _tenList.get(0) : "",
						sanpham.getDonViTinh(),
						formatter2.format(sanpham.getSoLuong()), dg, tt };

				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
					cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					table.addCell(cell);
				}
				rowIndex++;

				// In dong2
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// rowIndex +", In dÃ²ng 2 sp " + spIndex);
				arr = new String[] { " ",
						_tenList.size() > 1 ? _tenList.get(1) : "",
						"(" + sanpham.getQuyDoiStr() + ")", " ", " " };

				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length - 1; j++) {
					cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					if (j == 2) {
						cell.setColspan(2);
					}
					table.addCell(cell);
				}
				rowIndex++;

				// In cac dong ghi chu con lai
				for (int z = 2; z < _tenList.size(); z++) {
					arr = new String[] { " ", _tenList.get(z), " ", " ", " ",
							" " };
					for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
						cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
						cell.setFixedHeight(TABLE_ROW_HEIGHT);
						cell.setVerticalAlignment(Element.ALIGN_TOP);
						cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
						cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
						cell.setBorder(BORDER_WIDTH);
						table.addCell(cell);
					}
					rowIndex++;
				}

				spIndex++;
			}

			// In dong rong

			for (int i = rowIndex; i < TABLE_NUM_ROWS; i++) {
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// i +", In dÃ²ng rá»—ng");
				String[] arr = new String[] { " ", " ", " ", " ", " ", " " };
				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
					cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					table.addCell(cell);
				}
			}

			
			 * for (int i = 0; i < TABLE_NUM_ROWS; i++) { if(i < spList.size())
			 * { IErpHoaDon_SP sanpham = (IErpHoaDon_SP) spList.get(i); long
			 * thanhtien = 0;
			 * 
			 * try { thanhtien = Math.round(sanpham.getDonGia() *
			 * sanpham.getSoLuong()); } catch (Exception ex){ }
			 * 
			 * total_amount = total_amount + thanhtien;
			 * System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] Thanh tien["
			 * + count + "] = " + total_amount);
			 * System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] Quy doi["
			 * + count + "] = " + sanpham.getQuyDoiStr());
			 * 
			 * String dg = " "; if (sanpham.getDonGia() > 0) dg =
			 * FormatNumber(sanpham.getDonGia(), 0);
			 * 
			 * String tt = " "; if (thanhtien > 0) tt = FormatNumber(thanhtien,
			 * 0);
			 * 
			 * String[] arr = new String[] { Integer.toString(count),
			 * sanpham.getTenXuatHoaDon(), sanpham.getDonViTinh() + "\n(" +
			 * sanpham.getQuyDoiStr() + ")", FormatNumber((double)
			 * sanpham.getSoLuong(), 0), dg, tt };
			 * 
			 * for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) { cell = new
			 * PdfPCell(new Paragraph(arr[j], donhangFont));
			 * cell.setFixedHeight(TABLE_ROW_HEIGHT);
			 * cell.setVerticalAlignment(Element.ALIGN_TOP);
			 * cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
			 * cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
			 * cell.setBorder(BORDER_WIDTH); table.addCell(cell); } count++; }
			 * else { for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
			 * cell = new PdfPCell(new Paragraph("  ", donhangFont));
			 * cell.setBorder(BORDER_WIDTH);
			 * cell.setFixedHeight(TABLE_ROW_HEIGHT); table.addCell(cell); } } }
			 
			document.add(table);

			double tienCK = hdBean.getTienChietKhau() > 0 ? hdBean.getTienChietKhau() : total_amount * hdBean.getChietkhau() / 100;
			double tienKM = hdBean.getTienkhuyenmai();
			double tienSauCKKM = total_amount - tienCK - tienKM;

			// AMOUNTS TABLE
			table = new PdfPTable(2);
			table.setWidthPercentage(100);
			table.setWidths(new float[] { TABLE_AMOUNT_LEFT_WIDTH,
					TABLE_AMOUNT_RIGHT_WIDTH });

			// Left0: Trá»‘ng
			cell = new PdfPCell(new Paragraph(" ", donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[0]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[0]);
			table.addCell(cell);
			// Right0: Cá»™ng tiá»�n hÃ ng
			cell = new PdfPCell(new Paragraph(formatter3.format(tienSauCKKM),
					donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[0]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[0]);
			table.addCell(cell);

			// Left1: VAT
			double vat = Math.round(hdBean.getVAT());
			double tienVAT = Math.round(tienSauCKKM * vat / 100);
			double tienSauVAT = Math.round(tienSauCKKM + tienVAT);
			String tienBangChu = DocTien.docTien(Math.round(tienSauVAT))
					+ "./.";

			cell = new PdfPCell(new Paragraph(formatter3.format(vat),
					donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[1]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[1]);
			table.addCell(cell);
			// Right1: Tiá»�n thuáº¿ GTGT
			cell = new PdfPCell(new Paragraph(formatter3.format(tienVAT),
					donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[1]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[1]);
			table.addCell(cell);

			// Left2: Trá»‘ng
			cell = new PdfPCell(new Paragraph(" ", donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[2]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[2]);
			table.addCell(cell);
			// Right2: Tá»•ng cá»™ng tiá»�n thanh toÃ¡n
			cell = new PdfPCell(new Paragraph(formatter3.format(tienSauVAT),
					donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[2]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[2]);
			table.addCell(cell);

			// Left3: Trá»‘ng
			cell = new PdfPCell(new Paragraph(" ", donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingLeft(TABLE_AMOUNT_LEFT_PADDING_LEFTS[3]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[3]);
			table.addCell(cell);
			// Right3: Sá»‘ tiá»�n viáº¿t báº±ng chá»¯
			cell = new PdfPCell(new Paragraph(tienBangChu, donhangFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPaddingRight(TABLE_AMOUNT_RIGHT_PADDING_RIGHTS[3]);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(TABLE_AMOUNT_ROW_HEIGHTS[3]);
			table.addCell(cell);

			document.add(table);

			String ngaythangnam = hdBean.getNgayxuathd();
			String ngay = ngaythangnam.substring(8, 10);
			String thang = ngaythangnam.substring(5, 7);
			String nam = ngaythangnam.substring(0, 4);

			Paragraph p = new Paragraph(ngay + "                  " + thang
					+ "               " + nam, donhangFont);
			p.setAlignment(Element.ALIGN_RIGHT);
			p.setSpacingBefore(TABLE_AMOUNT_ROW_HEIGHTS[4]);
			document.add(p);

			// CLOSE DOCUMENT
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] Loi Trong Qua Trinh In: "
							+ e.getMessage());
		}
	}
*/
	/**
	 * In hÃ³a Ä‘Æ¡n nÆ°á»›c ngoÃ i excel
	 * @param out
	 * @param hdBean
	 */
	

/*	private void HoaDonNuocNgoaiPdf(Document document,
			ServletOutputStream outstream, IErpHoaDon hdBean)
			throws IOException {
		try {
			IErpCauHinhInHoaDon soNumber = new ErpCauHinhInHoaDon();
			soNumber.initWithName("SO_NN");
			soNumber.dbClose();

			IErpCauHinhInHoaDon khachhang_config = new ErpCauHinhInHoaDon();
			khachhang_config.initWithName("CUSTOMER_NN");
			khachhang_config.dbClose();

			IErpCauHinhInHoaDon dh_config = new ErpCauHinhInHoaDon();
			dh_config.initWithName("DETAILS_NN");
			dh_config.dbClose();

			float CONVERT = 28.346457f; // = 1cm
			int BORDER_WIDTH = 0;
			float[] TOP_WIDTHS = { 13f, 6.5f };
			float CUSTOMER_LEFT = khachhang_config.getMarginLeft() * CONVERT, CUSTOMER_RIGHT = khachhang_config
					.getMarginRight() * CONVERT, CUSTOMER_TOP = khachhang_config
					.getMarginTop() * CONVERT, CUSTOMER_BOTTOM = khachhang_config
					.getMarginBottom() * CONVERT;
			// float CUSTOMER_LEFT = 0.9f*CONVERT, CUSTOMER_RIGHT =
			// 0.1f*CONVERT, CUSTOMER_TOP = 0.0f*CONVERT, CUSTOMER_BOTTOM =
			// 1.6f*CONVERT;
			float CUSTOMER_NAME_LEFT = 3.0f * CONVERT + CUSTOMER_LEFT;
			float CUSTOMER_ADDRESS_LEFT = 0.5f * CONVERT + CUSTOMER_LEFT;
			float CUSTOMER_FAX_LEFT = 0.5f * CONVERT + CUSTOMER_LEFT;
			float SO_LEFT = soNumber.getMarginLeft() * CONVERT;
			// float SO_LEFT = 2.7f*CONVERT;

			int TABLE_NUM_ROWS = 16; // 6
			// int TABLE_NUM_ROWS = 5;
			float TABLE_LEFT = 0.0f * CONVERT, TABLE_RIGHT = 0.0f * CONVERT, TABLE_TOP = 0.0f * CONVERT, TABLE_BOTTOM = 0.0f * CONVERT;
			float TABLE_HEIGHT = dh_config.getTableHeight() * CONVERT, TABLE_ROW_HEIGHT = TABLE_HEIGHT
					/ TABLE_NUM_ROWS;
			// float TABLE_HEIGHT = 8.7f*CONVERT, TABLE_ROW_HEIGHT =
			// TABLE_HEIGHT/TABLE_NUM_ROWS;
			float[] TABLE_COLUMN_WIDTHS = { 1.0f * CONVERT, 8.7f * CONVERT,
					2.1f * CONVERT, 2.1f * CONVERT, 2.1f * CONVERT,
					3.5f * CONVERT };
			// float[] TABLE_COLUMN_WIDTHS = {dh_config.getNoColumn()*CONVERT,
			// dh_config.getProductColumn()*CONVERT,
			// dh_config.getUnitColumn()*CONVERT,
			// dh_config.getQuantityColumn()*CONVERT,
			// dh_config.getUniPriceColumn()*CONVERT,
			// dh_config.getAmoutColumn()*CONVERT};
			float[] TABLE_COLUMN_PADDING_LEFTS = { 0.1f * CONVERT,
					0.0f * CONVERT, 0.0f * CONVERT, 0.0f * CONVERT,
					0.0f * CONVERT, 0.0f * CONVERT };
			int[] TABLE_COLUMN_ALIGNS = { Element.ALIGN_LEFT,
					Element.ALIGN_LEFT, Element.ALIGN_CENTER,
					Element.ALIGN_RIGHT, Element.ALIGN_RIGHT,
					Element.ALIGN_RIGHT };

			float TOTAL_HEIGHT = 1.9f * CONVERT;
			float TOTAL_IN_WORDS_HEIGHT = 0.8f * CONVERT;
			float REMARKS_HEIGHT = 3.3f * CONVERT;
			// KÃ­ch thÆ°á»›c theo Ä‘Æ¡n vá»‹ cm

			System.out .println("[ErpHoaDonPdfSvl.HoaDonNuocNgoaiPdf] TABLE_ROW_HEIGHT = " + TABLE_ROW_HEIGHT / CONVERT + "cm");

			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font headerFont = new Font(bf, khachhang_config.getFontSize(),
					Font.BOLD);
			Font donhangFont = new Font(bf, 11, Font.NORMAL);
			Font donhangFontB = new Font(bf, 11, Font.BOLD);
			Font productFont = new Font(bf, dh_config.getLineSpacing(),
					Font.NORMAL);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font10 = new Font(bf, 10, Font.NORMAL);

			
			 * Font font = new Font(bf, 13, Font.BOLD); Font font2 = new
			 * Font(bf, 8, Font.BOLD); Font font9bold = new Font(bf, 9,
			 * Font.BOLD); Font font11bold = new Font(bf, 11, Font.BOLD); Font
			 * font12bold = new Font(bf, 12, Font.BOLD); Font font13bold = new
			 * Font(bf, 13, Font.BOLD); Font font14bold = new Font(bf, 14,
			 * Font.BOLD); Font font15bold = new Font(bf, 15, Font.BOLD); Font
			 * font16bold = new Font(bf, 16, Font.BOLD); Font font9 = new
			 * Font(bf, 9, Font.NORMAL); Font font11 = new Font(bf, 11,
			 * Font.NORMAL); Font font12 = new Font(bf, 12, Font.NORMAL); Font
			 * font13 = new Font(bf, 13, Font.NORMAL); Font font14 = new
			 * Font(bf, 14, Font.NORMAL);
			 

			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00");
			PdfWriter.getInstance(document, outstream);
			document.open();

			dbutils db = new dbutils();
			String sql = " select kh.PK_SEQ as khId , kh.MA as khMa, kh.TEN as khTen, isnull(kh.DIACHI,'') as khDiachi, isnull(kh.DIENTHOAI, '') as khDienthoai, isnull(kh.FAX,'') as khFax, isnull(kh.NguoiLienHe,'') as khNguoiLienHe, isnull(kh.Ten,'') as khTen, isnull(hd.HINHTHUCTT,'') as hinhthuctt, isnull(tt.MA,'') as tiente, isnull(e.mahopdong, '') as sohopdong, isnull(hd.ghichu, '') as ghichu, "
					+ "     ISNULL(E.PAYMENTTERMS,'') AS PAYMENTTERMS, ISNULL(E.DELIVERYTERMS, '') AS DELIVERYTERMS, ISNULL(E.ETD, '') AS ETD, ISNULL(E.REMARKS, '') AS REMARKS "
					+ " from ERP_HOADON hd "
					+ " inner join ERP_TIENTE tt on hd.tiente_fk = tt.PK_SEQ "
					+ " inner join ERP_KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ "
					+ " inner join ERP_HOADON_XUATKHO a on a.hoadon_fk = hd.PK_SEQ "
					+ " inner join ERP_XUATKHO c on a.xuatkho_fk = c.PK_SEQ "
					+ " inner join ERP_DONDATHANG d on c.DONDATHANG_FK = d.PK_SEQ "
					+ " left join ERP_HOPDONG e on hd.hopdong_fk = e.pk_seq "
					+ " WHERE hd.PK_SEQ = '" + hdBean.getId() + "'";
			System.out.println("[ErpHoaDonPdfSvl.HoaDonNuocNgoaiPdf] sql = "
					+ sql);
			String address = "";
			// String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";
			String fax = "";
			String phone_number = "";
			String tiente = "";
			String ghichu = "";
			String sohopdong = "";

			ResultSet rs = db.get(sql);
			try {
				if (rs.next()) {
					name_of_buyer = rs.getString("khNguoiLienHe");
					name_of_company = rs.getString("khTen");
					address = rs.getString("khDiachi");
					fax = rs.getString("khFax");
					phone_number = rs.getString("khDienthoai");
					tiente = rs.getString("tiente");
					ghichu = rs.getString("ghichu");
					sohopdong = rs.getString("sohopdong");

					if (ghichu == null || ghichu.trim().length() == 0) {
						ghichu = rs.getString("hinhthuctt");
					}
				}
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			db.shutDown();

			// Váº½ HEADER gá»“m CUSTOMER vÃ  SO
			PdfPTable table = new PdfPTable(2);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidths(TOP_WIDTHS);
			table.setWidthPercentage(100.0f);

			PdfPCell cell;
			cell = new PdfPCell(new Paragraph(name_of_company.toUpperCase(),
					headerFont)); // CUSTOMER
			cell.setPaddingLeft(CUSTOMER_NAME_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.7f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph( getEnDateTime(hdBean.getNgayxuathd()), headerFont)); // Inv date
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.7f * CONVERT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(address, headerFont));
			cell.setPaddingLeft(CUSTOMER_ADDRESS_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph("", headerFont)); // Customer's PO
																// //hdBean.getDonDatHang()
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph("Tel: " + phone_number + "   " + "Fax: " + fax, headerFont));
			cell.setPaddingLeft(CUSTOMER_FAX_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(sohopdong, headerFont)); // Contract
																		// No.
																		// (Sá»‘
																		// Há»£p
																		// Ä‘á»“ng)
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph("    ", headerFont));
			cell.setPaddingLeft(CUSTOMER_FAX_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(tiente, headerFont)); // Curency
																	// used
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);

			// HÃ¬nh thá»©c thanh toÃ¡n: láº¥y 1 trong 3 Ã´ ghi chÃº, há»£p Ä‘á»“ng hoáº·c hÃ¬nh
			// thá»©c thanh toÃ¡n
			cell = new PdfPCell(new Paragraph(" ", headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(hdBean.getPaymentTerms(),
					headerFont)); // Payment terms
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph("                         "
					+ hdBean.getETD(), headerFont)); // ETD
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(hdBean.getDeliveryTerms(),
					headerFont)); // Delivery terms
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(0.5f * CONVERT);
			table.addCell(cell);

			// Khoáº£ng trá»‘ng HEADER TABLE BÃŠN DÆ¯á»šI
			cell = new PdfPCell(new Paragraph("    ", headerFont));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(CUSTOMER_BOTTOM);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(" ", headerFont));
			cell.setPaddingLeft(SO_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setBorder(BORDER_WIDTH);
			cell.setFixedHeight(CUSTOMER_BOTTOM);
			table.addCell(cell);

			// table.setSpacingBefore(7f * CONVERT);
			document.add(table);

			List<IErpHoaDon_SP> spList = hdBean.GetListSanPham();
			float lineSpacing = 0;

			String noidungck = "";
			String thue = "";

			noidungck = hdBean.getNoidungCK();
			thue = "" + hdBean.getVAT();
			System.out.println("Thue la: " + thue);

			// Table Content
			table = new PdfPTable(6);
			// table.setSpacingBefore(1.78f * CONVERT);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);
			table.setWidths(TABLE_COLUMN_WIDTHS);

			PdfPCell cells = new PdfPCell();
			cells.setBorder(0);

			double total_amount = 0;
			double total_quantity = 0;
			int spIndex = 0;
			int rowIndex = 0;
			int soDongSp = 0;

			System.out
					.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] TABLE_NUM_ROWS = "
							+ TABLE_NUM_ROWS);
			System.out
					.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] TABLE_ROW_HEIGHT = "
							+ TABLE_ROW_HEIGHT);

			int sokytu1sp = hdBean.getSoKyTu1DongSanPham();
			if (sokytu1sp <= 0)
				sokytu1sp = 30;

			// Bá»� nhá»¯ng sáº£n pháº©m khÃ´ng in (trong trÆ°á»�ng há»£p hÃ³a Ä‘Æ¡n chÆ°a chá»‘t -
			// chÆ°a Ä‘Æ°á»£c bá»� nhá»¯ng sáº£n pháº©m ko in)
			for (int i = 0; i < spList.size(); i++) {
				if (!spList.get(i).getIn().equals("1")) {
					spList.remove(i);
					i--;
				}
			}

			while (spIndex < spList.size() && rowIndex < TABLE_NUM_ROWS) {
				IErpHoaDon_SP sanpham = (IErpHoaDon_SP) spList.get(spIndex);

				// TÃªn + quy cÃ¡ch
				String[] ttsp = sanpham.getTenXuatHoaDon().split("}");

				String tenSp = ttsp[0];
				String qcSp = ttsp.length > 1 ? ttsp[1].trim() : "";

				// Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				String[] words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat
																				// ca
																				// cac
																				// tu
																				// trong
																				// ten
																				// san
																				// pham
				String _ten = "", _ten2 = "";
				for (int _i = 0; _i < words.length; _i++) {
					// Xá»­ lÃ½ khi 1 tá»« > 30 kÃ½ tá»±
					if (words[_i].length() > sokytu1sp) {
						if (_ten.trim().length() > 0)
							_tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
						_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng
													// má»›i
						_ten = ""; // reset _ten
					} else {
						_ten2 = _ten
								+ (_ten.length() == 0 ? words[_i] : " "
										+ words[_i]);
						if (_ten2.length() > sokytu1sp) {
							_tenList.add(_ten);
							_ten = words[_i];
						} else {
							_ten = _ten2;
						}
					}
				}
				if (_ten.trim().length() > 0)
					_tenList.add(_ten);
				int soDongDanhChoTenSanPham = _tenList.size();

				// Xu ly quy cach
				words = qcSp.trim().replaceAll("  ", " ").split(" "); // Tat ca
																		// cac
																		// tu
																		// trong
																		// quy
																		// cach
				_ten = "";
				for (int _i = 0; _i < words.length; _i++) {
					// Xá»­ lÃ½ khi 1 tá»« > 30 kÃ½ tá»±
					if (words[_i].length() > sokytu1sp) {
						if (_ten.trim().length() > 0)
							_tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
						_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1 dÃ²ng
													// má»›i
						_ten = ""; // reset _ten
					} else {
						_ten2 = _ten
								+ (_ten.length() == 0 ? words[_i] : " "
										+ words[_i]);
						if (_ten2.length() > sokytu1sp) {
							_tenList.add(_ten);
							_ten = words[_i];
						} else {
							_ten = _ten2;
						}
					}
				}
				if (_ten.trim().length() > 0)
					_tenList.add(_ten);

				String sanpham_ghichu = hdBean.getSanphamGhiChu().get(
						sanpham.getIdSanPham());
				int countGhiChu = 0;
				String[] arrGhiChu = new String[] {};
				if (sanpham_ghichu != null
						&& sanpham_ghichu.trim().length() > 0) {
					arrGhiChu = sanpham_ghichu.split("__");
					countGhiChu = arrGhiChu.length;

					for (int j = 0; j < arrGhiChu.length; j++) {
						if (arrGhiChu[j].equals("NA"))
							arrGhiChu[j] = "";
					}

					if (countGhiChu > 3)
						countGhiChu = 3;
				}

				for (int _j = 0; _j < arrGhiChu.length; _j++) {
					words = arrGhiChu[_j].trim().replaceAll("  ", " ")
							.split(" "); // Tat ca cac tu trong ghi chu
					_ten = "";
					for (int _i = 0; _i < words.length; _i++) {
						// Xá»­ lÃ½ khi 1 tá»« > 30 kÃ½ tá»±
						if (words[_i].length() > sokytu1sp) {
							if (_ten.trim().length() > 0)
								_tenList.add(_ten); // ThÃªm dÃ²ng cÅ©
							_tenList.add(words[_i]); // ThÃªm tá»« dÃ i Ä‘Ã³ vÃ o 1
														// dÃ²ng má»›i
							_ten = ""; // reset _ten
						} else {
							_ten2 = _ten
									+ (_ten.length() == 0 ? words[_i] : " "
											+ words[_i]);
							if (_ten2.length() > sokytu1sp) {
								_tenList.add(_ten);
								_ten = words[_i];
							} else {
								_ten = _ten2;
							}
						}
					}
					if (_ten.trim().length() > 0)
						_tenList.add(_ten);
				}
				soDongSp += _tenList.size();

				double thanhtien = 0;
				try {
					thanhtien = Math.round(sanpham.getDonGia()
							* sanpham.getSoLuong());
					total_amount = total_amount + sanpham.getDonGia()
							* sanpham.getSoLuong();
				} catch (Exception ex) {
				}

				// System.out.println("Thanh tien: " + total_amount);
				total_quantity += sanpham.getSoLuong();

				String dg = " ";
				if (sanpham.getDonGia() > 0)
					dg = formatter2.format(sanpham.getDonGia());

				String tt = " ";
				if (thanhtien > 0)
					tt = formatter2.format(thanhtien);

				// In khi cÃ³ thá»ƒ hiá»‡n toÃ n bá»™ thÃ´ng tin cá»§a sáº£n pháº©m (1 dÃ²ng tÃªn
				// sp, 1 dÃ²ng quy cÃ¡ch, cÃ¡c dÃ²ng ghi chÃº)

				// In dong 1
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// rowIndex +", In dÃ²ng 1 sp " + spIndex);
				String[] arr = new String[] { Integer.toString(spIndex + 1),
						_tenList.size() > 0 ? _tenList.get(0) : "",
						sanpham.getDonViTinhEng(),
						formatter.format((double) sanpham.getSoLuong()), dg, tt };

				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
					Font font = j == 1 ? donhangFontB : donhangFont;

					cell = new PdfPCell(new Paragraph(arr[j], font));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					table.addCell(cell);
				}
				rowIndex++;

				// In dong2
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// rowIndex +", In dÃ²ng 2 sp " + spIndex);
				arr = new String[] { " ",
						_tenList.size() > 1 ? _tenList.get(1) : " ",
						"(" + sanpham.getQuyDoiStr() + ")", " ", " " };
				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length - 1; j++) {
					cell = new PdfPCell( new Paragraph(arr[j], soDongDanhChoTenSanPham > 1 && j == 1 ? donhangFontB : donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					if (j == 2) {
						cell.setColspan(2);
					}
					table.addCell(cell);
				}
				rowIndex++;

				// In cac dong ghi chu con lai
				for (int z = 2; z < _tenList.size(); z++) {
					// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
					// rowIndex +", In cÃ¡c dÃ²ng ghi chÃº sp " + spIndex);
					arr = new String[] { " ", _tenList.get(z), " ", " ", " ",
							" " };
					for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
						cell = new PdfPCell(
								new Paragraph(
										arr[j],
										soDongDanhChoTenSanPham > z && j == 1 ? donhangFontB
												: donhangFont));
						cell.setFixedHeight(TABLE_ROW_HEIGHT);
						cell.setVerticalAlignment(Element.ALIGN_TOP);
						cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
						cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
						cell.setBorder(BORDER_WIDTH);
						table.addCell(cell);
					}
					rowIndex++;
				}

				spIndex++;
			}

			for (int i = rowIndex; i < TABLE_NUM_ROWS; i++) {
				// System.out.println("[ErpHoaDonPdfSvl.HoaDonTrongNuocPdf] rowIndex = "+
				// i +", In dÃ²ng rá»—ng");
				String[] arr = new String[] { " ", " ", " ", " ", " ", " " };
				for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
					cell = new PdfPCell(new Paragraph(arr[j], donhangFont));
					cell.setFixedHeight(TABLE_ROW_HEIGHT);
					cell.setVerticalAlignment(Element.ALIGN_TOP);
					cell.setPaddingLeft(TABLE_COLUMN_PADDING_LEFTS[j]);
					cell.setHorizontalAlignment(TABLE_COLUMN_ALIGNS[j]);
					cell.setBorder(BORDER_WIDTH);
					table.addCell(cell);
				}
			}

			
			 * double total_amount = 0; double total_quantity = 0; int count =
			 * 0; for (int i = 0; i < TABLE_NUM_ROWS; i++) { if(i <
			 * spList.size()) { ++count; IErpHoaDon_SP sanpham = (IErpHoaDon_SP)
			 * spList.get(i); long thanhtien = 0; try { thanhtien =
			 * Math.round(sanpham.getDonGia() * sanpham.getSoLuong()); } catch
			 * (Exception ex) {}
			 * 
			 * total_amount = total_amount + sanpham.getDonGia() *
			 * sanpham.getSoLuong(); System.out.println("Thanh tien: " +
			 * total_amount); total_quantity += sanpham.getSoLuong();
			 * 
			 * String dg = " "; if (sanpham.getDonGia() > 0) dg =
			 * formatter.format(sanpham.getDonGia());
			 * 
			 * String tt = " "; if (thanhtien > 0) tt =
			 * formatter.format(thanhtien); String[] arr = new String[] {
			 * Integer.toString(count), //0 sanpham.getTenXuatHoaDon(), //1
			 * sanpham.getDonViTinhEng(), //2 formatter.format((double)
			 * sanpham.getSoLuong()), //3 dg, //4 tt //5 };
			 * 
			 * for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) { cells =
			 * new PdfPCell(); Paragraph para; if(j==1) { para = new
			 * Paragraph(arr[j], productFont);
			 * para.setAlignment(Element.ALIGN_LEFT);
			 * cells.setPaddingLeft(0.2f*CONVERT); } else { para = new
			 * Paragraph(arr[j], donhangFont);
			 * para.setAlignment(Element.ALIGN_CENTER); }
			 * cells.addElement(para);
			 * 
			 * cells.setFixedHeight(TABLE_ROW_HEIGHT); cells.setPadding(0);
			 * cells.setBorder(BORDER_WIDTH);
			 * cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * 
			 * table.addCell(cells); } } else { for (int j = 0; j <
			 * TABLE_COLUMN_WIDTHS.length; j++) { cells = new PdfPCell();
			 * Paragraph para = new Paragraph(" ", donhangFont);
			 * cells.addElement(para); cells.setFixedHeight(TABLE_ROW_HEIGHT);
			 * cells.setPadding(0); cells.setBorder(BORDER_WIDTH);
			 * cells.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * 
			 * table.addCell(cells); } } }
			 * 
			 * System.out.println("[ErpHoaDonPdfSvl.HoaDonNuocNgoaiPdf] count = "
			 * + count);
			 

			double tienCK = hdBean.getTienChietKhau() > 0 ? hdBean
					.getTienChietKhau() : total_amount * hdBean.getChietkhau()
					/ 100;
			double tienKM = hdBean.getTienkhuyenmai();
			double tienSauCKKM = Math.round(total_amount - tienCK - tienKM);

			// TOTAL
			for (int j = 0; j < TABLE_COLUMN_WIDTHS.length; j++) {
				if (j == 3) {
					cells = new PdfPCell(new Paragraph(""
							+ formatter.format(total_quantity), donhangFont));
				} else if (j == 5) {
					cells = new PdfPCell(new Paragraph(
							formatter2.format(tienSauCKKM), donhangFont));
				} else {
					cells = new PdfPCell(new Paragraph("  ", donhangFont));
				}
				if (j == 1) {
					cells.setHorizontalAlignment(Element.ALIGN_CENTER);
				} else {
					cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setBorder(BORDER_WIDTH);
				cells.setFixedHeight(TOTAL_HEIGHT);
				table.addCell(cells);
			}
			document.add(table);

			// TOTAL IN WORDS
			String tien = DocTienEN.convert(Math.round(tienSauCKKM));

			table = new PdfPTable(1);
			table.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.setWidthPercentage(100);
			float[] totalInWordsWidths = { 18.0f * CONVERT };
			table.setWidths(totalInWordsWidths);

			cells = new PdfPCell(
					new Paragraph("US Dollars " + tien, font10bold));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setPaddingLeft(3.5f * CONVERT);
			cells.setFixedHeight(TOTAL_IN_WORDS_HEIGHT);
			cells.setBorder(BORDER_WIDTH);
			table.addCell(cells);

			// REMARKS (3 ghi chÃº)
			cells = new PdfPCell(new Paragraph("                            "
					+ hdBean.getRemarks().replaceAll("\n", "\n"), font10));
			cells.setHorizontalAlignment(Element.ALIGN_LEFT);
			cells.setPaddingLeft(1.0f * CONVERT);
			cells.setBorder(BORDER_WIDTH);
			table.addCell(cells);

			document.add(table);

			// CLOSE DOCUMENT
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("[ErpHoaDonPdfSvl.HoaDonNuocNgoaiPdf] Loi Trong Qua Trinh In :"
							+ e.getMessage());
		}
	}

	*/
	private static String FormatNumber(double number, int round) {
		// System.out.println("Truoc kho Format: " + number);
		String format = "#,###,###";
		if (round >= 1)
			format += ".";

		for (int i = 0; i < round; i++)
			format += "0";

		// System.out.println("Chuoi Format: " + format);

		DecimalFormat df = new DecimalFormat(format);
		String result = df.format(number);

		if (number > 999) {
			// result = result.replaceAll(".", "+");
			result = result.replaceAll(",", ".");
			if (round > 0)
				result = result.substring(0, result.length() - (round + 1))
						+ "," + result.substring(result.length() - round);
			// result = result.replaceFirst("-", ",");
		} else
			result = result.replaceAll(",", ".");
		
		// System.out.println("ket qua: " + result);
		return result;
	}

	public static String formatVN(String so) {

		String result = so.replaceAll(",", "@");
		result = result.replaceAll("[.]", ",");
		result = result.replaceAll("@", ".");

		return result;

	}



	private String getEnDateTime(String date) {
		if (date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);

			thang = thang.equals("01") ? "Jan" : thang.equals("02") ? "Feb"
					: thang.equals("03") ? "Mar" : thang.equals("04") ? "Apr"
							: thang.equals("05") ? "May"
									: thang.equals("06") ? "Jun" : thang
											.equals("07") ? "Jul" : thang
											.equals("08") ? "Aug" : thang
											.equals("09") ? "Sep" : thang
											.equals("10") ? "Oct" : thang
											.equals("11") ? "Nov" : thang
											.equals("12") ? "Dec" : " ";
			return thang + " " + ngay + ", " + nam;
		} else {
			return "";
		}
	}

	private String getEnNewDateTime(String date) {
		if (date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + "/" + thang + "/" + nam;
		} else {
			return date;
		}
	}

	private String getVnDateTime(String date) {
		if (date.length() == 10) {
			String ngay = date.substring(8, 10);
			String thang = date.substring(5, 7);
			String nam = date.substring(0, 4);
			return ngay + "-" + thang + "-" + nam;
		} else {
			return "";
		}
	}
	
	private void HoaDonNuocNgoaiExcel_BK(OutputStream out, IErpHoaDon hdBean) 
	{
		NumberFormat formatter = new DecimalFormat("###,###,###,###,###");
		NumberFormat formatter2 = new DecimalFormat("###################");
		NumberFormat formatter3 = new DecimalFormat("###,###,###,###,##0.000");
		NumberFormat formatter4 = new DecimalFormat("############0.#####");
		int TABLE_NUM_ROWS = 18; // 6
		int S2_START_INDEX = 20;
		int S1_START_INDEX = 2;
		int REMARK_START_INDEX = 48;

		try {
			dbutils db = new dbutils();
			String sql = " select kh.PK_SEQ as khId , isnull(kh.MA, '') as khMa, isnull(kh.TEN, '') as khTen, isnull(kh.DIACHI,'') as khDiachi, isnull(kh.DIENTHOAI, '') as khDienthoai, isnull(kh.FAX,'') as khFax, isnull(kh.NguoiLienHe,'') as khNguoiLienHe, isnull(kh.Ten,'') as khTen, isnull(hd.HINHTHUCTT,'') as hinhthuctt, isnull(tt.MA,'') as tiente, isnull(e.mahopdong, '') as sohopdong, isnull(hd.ghichu, '') as ghichu, "
					+ "     ISNULL(E.PAYMENTTERMS,'') AS PAYMENTTERMS, ISNULL(E.DELIVERYTERMS, '') AS DELIVERYTERMS, ISNULL(E.ETD, '') AS ETD, ISNULL(E.REMARKS, '') AS REMARKS "
					+ " from ERP_HOADON hd "
					+ " inner join ERP_TIENTE tt on hd.tiente_fk = tt.PK_SEQ "
					+ " inner join ERP_KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ "
					+ " left join ERP_HOADON_XUATKHO a on a.hoadon_fk = hd.PK_SEQ "
					+ " left join ERP_XUATKHO c on a.xuatkho_fk = c.PK_SEQ "
					+ " left join ERP_DONDATHANG d on c.DONDATHANG_FK = d.PK_SEQ "
					+ " left join ERP_HOPDONG e on hd.hopdong_fk = e.pk_seq "
					+ " WHERE hd.PK_SEQ = '" + hdBean.getId() + "'";
			System.out.println("[ErpHoaDonPdfSvl.HoaDonNuocNgoaiPdf] sql = "
					+ sql);
			String address = "";
			// String taxCode = "";
			String name_of_buyer = "";
			String name_of_company = "";
			String fax = "";
			String phone_number = "";
			String tiente = "";
			String ghichu = "";
			String sohopdong = "";

			ResultSet rs = db.get(sql);
			try {
				if (rs.next()) {
					name_of_buyer = rs.getString("khNguoiLienHe").trim();
					name_of_company = rs.getString("khTen").trim().toUpperCase();
					address = rs.getString("khDiachi").trim();
					fax = rs.getString("khFax").trim();
					phone_number = rs.getString("khDienthoai").trim();
					tiente = rs.getString("tiente").trim();
					ghichu = rs.getString("ghichu").trim();
					sohopdong = rs.getString("sohopdong").trim();

					if (ghichu == null || ghichu.trim().length() == 0) {
						ghichu = rs.getString("hinhthuctt").trim();
					}
				}
				rs.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			db.shutDown();

			System.out.println("Khach Hang: " + name_of_company);
			System.out.println("Dia Chi: " + address);
			System.out.println("So Dien Thoai: " + phone_number);
			System.out.println("Fax: " + fax);

			FileInputStream fstream;
			Cell cell = null;

			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\HoaDonTaiChinhNuocNgoai.xlsm");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			workbook.open(fstream);

			Worksheets worksheets = workbook.getWorksheets();

			// Sheet 1
			Worksheet worksheet1 = worksheets.getSheet("DS Khach Hang");
			Cells cells1 = worksheet1.getCells();

			cell = cells1.getCell("A2");
			cell.setValue(name_of_company); // CUSTOMER
			cell = cells1.getCell("B2");
			cell.setValue(address); //
			cell = cells1.getCell("C2");
			cell.setValue(""); //
			cell = cells1.getCell("D2");
			cell.setValue(phone_number); //
			cell = cells1.getCell("E2");
			cell.setValue(fax); //
			cell = cells1.getCell("K2");
			cell.setValue(tiente); //
			cell = cells1.getCell("M2");
			cell.setValue(hdBean.gethinhthuctt()); // Payment terms

			cell = cells1.getCell("Z1");
			cell.setValue(getEnNewDateTime(hdBean.getNgayxuathd())); // NgÃ y xuáº¥t hÃ³a Ä‘Æ¡n

			// Sheet 2
			Worksheet worksheet2 = worksheets.getSheet("HoaDon");
			Cells cells2 = worksheet2.getCells();

			cell = cells2.getCell("D10");
			cell.setValue(name_of_company); // CUSTOMER
			cell = cells2.getCell("D16");
			cell.setValue(hdBean.getETD()); // ETD
			cell = cells2.getCell("N10");
			cell.setValue(hdBean.getNgayxuathd()); // Inv date
			cell = cells2.getCell("N12");
			cell.setValue(hdBean.getCustomersPO()); // Customer's PO
			cell = cells2.getCell("N13");
			cell.setValue(sohopdong); // Constract No.
			cell = cells2.getCell("N14");
			cell.setValue(tiente); // Currency used
			cell = cells2.getCell("N15");
			cell.setValue(hdBean.gethinhthuctt()); // Payment terms
			cell = cells2.getCell("N16");
			cell.setValue(hdBean.getDeliveryTerms()); // Delivery terms

			double ck = hdBean.getTienChietKhau() * 100 / hdBean.getTongtienchuaVat(); // TÃ­nh láº¡i chiáº¿t kháº¥u Ä‘á»ƒ ra
																						// Ä‘Æ°á»£c tiá»�n chiáº¿t kháº¥u Ä‘Ãºng
																						// khi in hÃ³a Ä‘Æ¡n
			cell = cells2.getCell("O41");
			cell.setValue(ck); // Chiáº¿t kháº¥u
			cell = cells2.getCell("O42");
			cell.setValue(hdBean.getTienBaoHiem()); // Báº£o hiá»ƒm

			// Sáº£n pháº©m
			List<IErpHoaDon_SP> spList = hdBean.GetListSanPham();

			// Bá»� nhá»¯ng sáº£n pháº©m khÃ´ng in (trong trÆ°á»�ng há»£p hÃ³a Ä‘Æ¡n chÆ°a chá»‘t -
			// chÆ°a Ä‘Æ°á»£c bá»� nhá»¯ng sáº£n pháº©m ko in)
			for (int i = 0; i < spList.size(); i++) {
				if (!spList.get(i).getIn().equals("1")) {
					spList.remove(i);
					i--;
				}
			}

			int spIndex = 0;
			int rowIndex = 0, rowDvIndex = 0;
			int soDongSp = 0;

			int sokytu1sp = hdBean.getSoKyTu1DongSanPham();
			if (sokytu1sp <= 0) {
				sokytu1sp = 40;
			}

			String prev_tensp = "", temp_tensp = "";
			boolean changeSpCore = false;
			int stt = 0;
			while (spIndex < spList.size() && rowIndex < TABLE_NUM_ROWS) {
				IErpHoaDon_SP sanpham = (IErpHoaDon_SP) spList.get(spIndex);
				temp_tensp = sanpham.getMaSanPham() + sanpham.getQuyCachGroup();

				// TÃªn + quy cÃ¡ch
				String[] ttsp = sanpham.getTenXuatHoaDon().split("}");

				String tenSp = ttsp[0];
				String qcSp = ttsp.length > 1 ? ttsp[1].trim() : "";

				// Xu ly ten san pham
				List<String> _tenList = new ArrayList<String>();
				String[] words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
				String _ten = "", _ten2 = "";
				
				boolean isSpCone = sanpham.getDvkdId() != null && sanpham.getDvkdId().equals("100004") && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0|| sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0);
				
				if(isSpCone) {
					tenSp += " " + sanpham.getQuyCachGroup().trim();
				}
				
				System.out.println("[ErpHoaDonPdfSvl.xuLyTenList]"+spIndex+" prev_tensp = " + prev_tensp);
				System.out.println("[ErpHoaDonPdfSvl.xuLyTenList]"+spIndex+" temp_tensp = " + temp_tensp);
				changeSpCore = isSpCone && !prev_tensp.equals(temp_tensp);
				prev_tensp = temp_tensp;
				System.out.println("[ErpHoaDonPdfSvl.xuLyTenList]"+spIndex+" changeSpCore = " + changeSpCore);
				
				//Táº¡o _tenList
				xuLyTenNNList(hdBean, _tenList, sanpham, spIndex, tenSp, qcSp, sokytu1sp, prev_tensp, temp_tensp, changeSpCore);

				if (isSpCone) {
					// IN Sáº¢N PHáº¨M CONE

					int beginIndex = 0;
					if (changeSpCore) {
						//TÃ¬m thÃ´ng tin sp cone trong listsanphamCone
						double sl = sanpham.getSoLuong();
						String key = sanpham.getMaSanPham() + sanpham.getQuyCachGroup();
						IErpHoaDon_SP _spTemp;
						for(int z = 0; z < hdBean.GetListSanPhamCone().size(); z++) {
							_spTemp =  hdBean.GetListSanPhamCone().get(z);
							if(_spTemp != null && _spTemp.getIdSanPham().equals(key)) {
								sl = _spTemp.getSoLuong();
							}
						}
						//double _thanhtien = sl * sanpham.getDonGia();
						
						stt++;
						// In dong 1
						// Cá»™t tÃªn sáº£n pháº©m
						cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
						// Cá»™t Ä�Æ¡n vá»‹ tÃ­nh
						cell = cells2.getCell("I" + (S2_START_INDEX + rowIndex));
						cell.setValue(sanpham.getDonViTinhEng());
						// Cá»™t Sá»‘ lÆ°á»£ng
						cell = cells2.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue(sl);
						// Cá»™t Ä�Æ¡n giÃ¡
						cell = cells2.getCell("K" + (S2_START_INDEX + rowIndex));
						cell.setValue(sanpham.getDonGia());
						rowIndex++;
						beginIndex++;

						// In cá»™t quy cÃ¡ch
						/*if(_tenList.size() >= beginIndex) {
							cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
							cell.setValue(_tenList.get(beginIndex).trim());
							rowIndex++;
							beginIndex++;
						}*/
					}
					
					// In máº«u in + mÃ u
					/*if(_tenList.size() >= beginIndex) {
						cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
						cell.setValue("-" + _tenList.get(beginIndex));
						rowIndex++;
						beginIndex++;
					}*/

					// In cac dong con lai
					for (int z = beginIndex; z < _tenList.size(); z++) {
						// Cá»™t tÃªn sáº£n pháº©m
						cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.get(z));
						// Cá»™t Ä�Æ¡n vá»‹ tÃ­nh
						cell = cells2.getCell("I" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cá»™t Sá»‘ lÆ°á»£ng
						cell = cells2.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cá»™t Ä�Æ¡n giÃ¡
						cell = cells2.getCell("K" + (S2_START_INDEX + rowIndex));
						cell.setValue("");

						rowIndex++;
					}

				} else {
					// In dong 1
					// Cá»™t tÃªn sáº£n pháº©m
					cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
					cell.setValue(_tenList.size() > 0 ? _tenList.get(0) : "");
					// Cá»™t Ä�Æ¡n vá»‹ tÃ­nh
					cell = cells2.getCell("I" + (S2_START_INDEX + rowIndex));
					cell.setValue(sanpham.getDonViTinhEng());
					// Cá»™t Sá»‘ lÆ°á»£ng
					cell = cells2.getCell("J" + (S2_START_INDEX + rowIndex));
					cell.setValue(sanpham.getSoLuong());
					// Cá»™t Ä�Æ¡n giÃ¡
					cell = cells2.getCell("K" + (S2_START_INDEX + rowIndex));
					cell.setValue(sanpham.getDonGia());

					rowIndex++;

					cell = cells1.getCell("I" + (S1_START_INDEX + rowDvIndex));
					cell.setValue(sanpham.getDonViTinhEng());
					rowDvIndex++;

					// In dong 2
					// Cá»™t tÃªn sáº£n pháº©m
					cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
					cell.setValue(_tenList.size() > 1 ? _tenList.get(1) : " ");
					// Cá»™t Ä�Æ¡n vá»‹ tÃ­nh
					cell = cells2.getCell("I" + (S2_START_INDEX + rowIndex));
					cell.setValue("");
					// Cá»™t Sá»‘ lÆ°á»£ng
					cell = cells2.getCell("J" + (S2_START_INDEX + rowIndex));
					cell.setValue("");
					// Cá»™t Ä�Æ¡n giÃ¡
					cell = cells2.getCell("K" + (S2_START_INDEX + rowIndex));
					cell.setValue("");

					rowIndex++;

					// In cac dong ghi chu con lai
					for (int z = 2; z < _tenList.size(); z++) {

						// Cá»™t tÃªn sáº£n pháº©m
						cell = cells2.getCell("C" + (S2_START_INDEX + rowIndex));
						cell.setValue(_tenList.get(z));
						// Cá»™t Ä�Æ¡n vá»‹ tÃ­nh
						cell = cells2.getCell("I" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cá»™t Sá»‘ lÆ°á»£ng
						cell = cells2.getCell("J" + (S2_START_INDEX + rowIndex));
						cell.setValue("");
						// Cá»™t Ä�Æ¡n giÃ¡
						cell = cells2.getCell("K" + (S2_START_INDEX + rowIndex));
						cell.setValue("");

						rowIndex++;
					}
				}

				spIndex++;
			}

			// Remarks
			String[] remarks = hdBean.getRemarks().split("\n");
			if (remarks.length > 0) {
				cell = cells2.getCell("D48");
				cell.setValue(remarks[0].trim());
			}
			for (int i = 1; i < remarks.length; i++) {
				cell = cells2.getCell("C" + (REMARK_START_INDEX + i));
				cell.setValue(remarks[i].trim());
			}

			workbook.save(out);
			fstream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static  void main(String[] arg) throws DocumentException, IOException {
		List<String> _tenList = new ArrayList<String>();
		xuLyTenList(_tenList,"nguyen hoang quoc oaii  ",12);
		
			int beginIndex =0;
			while(_tenList.size() > beginIndex) {
				 
				System.out.println("Ten list:"+beginIndex+": " +_tenList.get(beginIndex).trim());
					 
					beginIndex++;
				 
			}
			
		
	}
	
	protected static boolean xuLyTenList(List<String> _tenList ,String tenSp,  int sokytu1sp) 
	{
		String[] words = new String[0];
		String _ten = "", _ten2 = "";
		
			words = tenSp.trim().replaceAll("  ", " ").split(" "); // Tat ca cac tu trong ten san pham
			_ten = "";
			_ten2 = "";
			for (int _i = 0; _i < words.length; _i++) {
				// Xử lý khi 1 từ > số ký tự 1 dòng
				if (words[_i].length() > sokytu1sp) {
					if (_ten.trim().length() > 0) _tenList.add(_ten); // Thêm dòng cũ
					_tenList.add(words[_i]); // Thêm từ dài đó vào 1 dòng mới
					_ten = ""; // reset _ten
				} else {
					_ten2 = _ten + (_ten.length() == 0 ? words[_i] : " " + words[_i]);
					if (_ten2.length() > sokytu1sp) {
						_tenList.add(_ten);
						_ten = words[_i];
					} else {
						_ten = _ten2;
					}
				}
			}
			if (_ten.trim().length() > 0) {
				_tenList.add(_ten);
			}
			
			return true;
	}
	 
	
	private void Hoadon_XK(Document document, ServletOutputStream outstream, IErpHoaDon obj) throws IOException {
		
		
		NumberFormat format = new DecimalFormat("#,###,###.##");
	
		try{
		PdfWriter.getInstance(document, outstream);
		document.open();
		
		BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bf, 13, Font.BOLD);
		Font font2 = new Font(bf, 8, Font.BOLD);
		Font font8 = new Font(bf, 8, Font.NORMAL);
		Font font9bold = new Font(bf, 9, Font.BOLD);
		Font font10bold = new Font(bf, 10, Font.BOLD);
		Font font10underline = new Font(bf, 10, Font.UNDERLINE);
		Font font10italic = new Font(bf, 10, Font.ITALIC);
		Font font11bold = new Font(bf, 11, Font.BOLD);
		Font font12bold = new Font(bf, 12, Font.BOLD);
		Font font13bold = new Font(bf, 13, Font.BOLD);
		Font font14bold = new Font(bf, 14, Font.BOLD);
		Font font15bold = new Font(bf, 15, Font.BOLD);
		Font font9 = new Font(bf, 9, Font.NORMAL);
		Font font10 = new Font(bf, 10, Font.NORMAL);
		Font font11 = new Font(bf, 11, Font.NORMAL);
		Font font12 = new Font(bf, 12, Font.NORMAL);
		Font font13 = new Font(bf, 13, Font.NORMAL);
		Font font14 = new Font(bf, 14, Font.NORMAL);
		
		
		float CONVERT = 28.346457f; // = 1cm
		float[] TABLE_HEADER_WIDTHS = {1f* CONVERT, 3f * CONVERT, 13.6f * CONVERT};
		float[] TABLE_MIDDLE_WIDTHS = {1f* CONVERT, 12.6f * CONVERT, 8.5f * CONVERT};
		float[] TABLE_MIDDLE_2_WIDTHS = {1f * CONVERT, 10.6f* CONVERT, 1.5f* CONVERT, 2.5f* CONVERT, 2f* CONVERT , 3f*CONVERT };
		float[] TABLE_FOOTER_WIDTHS = {13f * CONVERT, 5.6f * CONVERT};	

		PdfPCell cell;
		

		
		List<IErpHoaDon_SP> splist = obj.GetListSanPham();
		
		int sosanpham = obj.GetListSanPham().size();
		int currentIDsp=0; 	 //----- dùng để lấy sản phâm trong list
		int sosp_1trang=5;   // ---- 1 trang in dc 8 sản phẩm
		int sotrangin= (int) Math.ceil((float)sosanpham/sosp_1trang);
		
		
		
		int sodong= 24;
		int dave=0;
		int chuave=0;
		
		
		double total_soluong =0;
		double total_thanhtien=0;
		double tienchietkhau= 0;
		double tienkhuyenmai= 0;
		double tienvanchuyen= 0;
		double tienbaohiem= 0;
		double grand_total= 0;
		
		
		for(int a=0; a< sotrangin; a ++){
		// **************** VẼ HEADER  (LOGO, THÔNG TIN NTVN ) ********************//
		

		
		
		PdfPTable headerTable = new PdfPTable(TABLE_HEADER_WIDTHS.length);
		headerTable.setWidths(TABLE_HEADER_WIDTHS);
		headerTable.setWidthPercentage(100);
		headerTable.getDefaultCell().setBorder(0);
		
		cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
		cell.setBorder(0);
		cell.setFixedHeight(1.8f * CONVERT);
		headerTable.addCell(cell);
		
		String[] imageSources = {
				"C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
				"C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TraphacoERP\\pages\\images\\logoNewToYo.png",
				"D:\\Project\\NewToYo\\TraphacoERP\\WebContent\\pages\\images\\logoNewToYo.png"}; 
			Image logoImage = null;
			
			for(int i = 0; i < imageSources.length; i++) {
				try {
					if(logoImage == null) {
						logoImage = Image.getInstance(imageSources[i]);
						System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] imgSrc = " + imageSources[i]);
						break;
					}
				} catch (Exception e) {	}
			}
			if(logoImage != null) {
				System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Load Images Logo Thanh Cong....");
				logoImage.setBorder(0);
				logoImage.setAlignment(Element.ALIGN_CENTER);
				logoImage.scaleToFit(2f * CONVERT, 2f * CONVERT);
				//headerLeftTable.addCell(logoImage);
				logoImage.setAbsolutePosition(2f * CONVERT, PageSize.A4.getHeight() - 3.2f*CONVERT);
				document.add(logoImage);
				/*headerTable.addCell(logoImage);*/
			} else {
				System.out.println("[ErpPhieuxuatkhoPdfSvl.CreatePxk] Khong load duoc hinh anh logo");
			}
			cell = new PdfPCell(new Paragraph(" ", new Font(bf, 8, Font.NORMAL)));
			cell.setBorder(0);
			cell.setFixedHeight(1.8f * CONVERT);
			headerTable.addCell(cell);
		
		
		

		
		
		
		PdfPTable headerRightTable = new PdfPTable(1);
		headerRightTable.setWidths(new float[] {13.1f * CONVERT});
		headerRightTable.setWidthPercentage(100);
		
			cell = new PdfPCell(new Paragraph("NEW TOYO (VIETNAM) ALUMINIUM PAPER PACKAGING CO.,LTD", font10bold));
			cell.setBorder(0);
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerRightTable.addCell(cell);
		
		
			cell = new PdfPCell(new Paragraph("Lot 15, 17, 19 and 21 , The Industrial Zone of Linh Trung Ex-Processing Zone 2, Thu Duc Dist, HCMC", font9));
			cell.setBorder(0);
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Tax code: 0 3 0 2 4 7 6 6 8 4           Tel: 37291768 - Fax:  37281767   ", font10));
			cell.setBorder(0);
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerRightTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Email: ntvn@newtoyovn.com   Website: www.newtoyovn.com  ", font10));
			cell.setBorder(0);
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerRightTable.addCell(cell);

		headerTable.addCell(headerRightTable);
		document.add(headerTable);
		
		
		// **************** VẼ COMMERCIAL INVOICE    ********************//
		
		PdfPTable middleTable = new PdfPTable(TABLE_MIDDLE_WIDTHS.length);
		middleTable.setWidths(TABLE_MIDDLE_WIDTHS);
		middleTable.setWidthPercentage(100);
		middleTable.getDefaultCell().setBorder(0);
		
		cell = new PdfPCell(new Paragraph("", font10)); //---------- khoảng trắng
		cell.setBorder(0);
		cell.setFixedHeight(0.3f * CONVERT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		middleTable.addCell(cell);
		
		
					//----- left----//
		PdfPTable middleTable_left = new PdfPTable(1);
		middleTable_left.setWidths( new float[] {TABLE_MIDDLE_WIDTHS[1]});
		middleTable_left.setWidthPercentage(100);
		
					cell = new PdfPCell(new Paragraph("", font10)); //---------- khoảng trắng
					cell.setBorder(0);
					cell.setFixedHeight(0.3f * CONVERT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					middleTable_left.addCell(cell);
		
				cell = new PdfPCell(new Paragraph("	   COMMERCIAL INVOICE ", font12bold));
				cell.setBorder(0);
				cell.setFixedHeight(0.8f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_left.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("     	     Original 4: For Buyer ", font10italic));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_left.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10));  // ---------- khoảng trắng
				cell.setBorder(0);
				cell.setFixedHeight(0.2f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_left.addCell(cell);
				
				
							//---- khung customer ----//
				PdfPTable middleTable_left_1 = new PdfPTable(1);
				middleTable_left_1.setWidths( new float[] {TABLE_MIDDLE_WIDTHS[0]});
				middleTable_left_1.setWidthPercentage(100);
				middleTable_left_1.getDefaultCell().setBorder(0);
				
					PdfPTable customer = new PdfPTable(2);
					customer.setWidths( new float[] {3f* CONVERT, 9f* CONVERT});
					customer.setWidthPercentage(100);
					customer.getDefaultCell().setBorder(0);
					
						cell = new PdfPCell(new Paragraph("CUSTOMER : ", font10underline));
						cell.setBorder(0);
						cell.setFixedHeight(0.6f * CONVERT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						customer.addCell(cell);
						
						cell = new PdfPCell(new Paragraph("  " + obj.getNppTen(), font10bold));
						cell.setBorder(0);
						cell.setFixedHeight(0.6f * CONVERT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						customer.addCell(cell);
					
					middleTable_left_1.addCell(customer);
					
					cell = new PdfPCell(new Paragraph(" ", font10));
					cell.setBorder(0);
					cell.setFixedHeight(0.3f * CONVERT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					middleTable_left_1.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("   "+obj.getNppDiachi(), font10));
					cell.setBorder(0);
					cell.setFixedHeight(0.5f * CONVERT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					middleTable_left_1.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("", font10));
					cell.setBorder(0);
					cell.setFixedHeight(0.5f * CONVERT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					middleTable_left_1.addCell(cell);
				
					cell = new PdfPCell(new Paragraph("  Tel : "+obj.getDienthoai()   +"                  - Fax : " + obj.getFax(), font10));
					cell.setBorder(0);
					cell.setFixedHeight(0.5f * CONVERT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					middleTable_left_1.addCell(cell);
					
					middleTable_left.addCell(middleTable_left_1);
					//------------------------------------------------//
					
					
				cell = new PdfPCell(new Paragraph("ETD:  " + obj.getETD(), font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.6f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_left.addCell(cell);		
				
		middleTable.addCell(middleTable_left);
		
		
		
		
		
					//----- right----//
		PdfPTable middleTable_right = new PdfPTable(1);
		middleTable_right.setWidths( new float[] {TABLE_MIDDLE_WIDTHS[1]});
		middleTable_right.setWidthPercentage(100);
		middleTable_right.getDefaultCell().setBorder(0);
		
			cell = new PdfPCell(new Paragraph("", font10));
			cell.setBorder(0);
			cell.setFixedHeight(0.3f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable_right.addCell(cell);
		
			cell = new PdfPCell(new Paragraph(" Inv Form : 06HDXK8/001 ", font10));
			cell.setBorder(0);
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable_right.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(" Series : NT/11P", font10));
			cell.setBorder(0);
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable_right.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(" Inv No : "+ obj.getinvoice(), font10));
			cell.setBorder(0);
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			middleTable_right.addCell(cell);
			
			
			PdfPTable middleTable_right_1 = new PdfPTable(2);
			middleTable_right_1.setWidths( new float[] {2.7f* CONVERT, 3.9f *CONVERT});
			middleTable_right_1.setWidthPercentage(100);
			middleTable_right_1.getDefaultCell().setBorder(0);
			
				
				cell = new PdfPCell(new Paragraph(" Inv date   " , font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
			
				cell = new PdfPCell(new Paragraph(": "+ obj.getNgayxuathd(), font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
				
				
			
			
				cell = new PdfPCell(new Paragraph(" Customer's PO  ", font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": "+obj.getSoPO(), font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
				
				
				
			
				cell = new PdfPCell(new Paragraph(" Contract No  ", font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": "+obj.getSohopdong(), font10));
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
				
				
				
				
				cell = new PdfPCell(new Paragraph(" Currency used   ", font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": "+obj.getTienteTen(), font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
				
				
				
				
				cell = new PdfPCell(new Paragraph(" Payment terms  ", font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": "+obj.gethinhthuctt(), font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
				
				
				
				
				cell = new PdfPCell(new Paragraph(" Delivery terms  ", font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(": "+obj.getDeliveryTerms(), font10));
				cell.setBorder(0);
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				middleTable_right_1.addCell(cell);
				
				
			middleTable_right.addCell(middleTable_right_1);		
			middleTable.addCell(middleTable_right);
		document.add(middleTable);
		
		
		
		//********* VẼ BẢNG ********//
		
		PdfPTable middleTable_2 = new PdfPTable(TABLE_MIDDLE_2_WIDTHS.length);
		middleTable_2.setWidths(TABLE_MIDDLE_2_WIDTHS);
		middleTable_2.setWidthPercentage(100);

			
			
			//--- dòng thông tin hóa dơn ---//
			
			cell = new PdfPCell(new Paragraph("No.", font10bold));
			cell.setFixedHeight(1f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("Descriptions", font10bold));
			cell.setFixedHeight(1f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("UOM", font10bold));
			cell.setFixedHeight(1f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("Quantity", font10bold));
			cell.setFixedHeight(1f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("Unit Price", font10bold));
		
			cell.setFixedHeight(1f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("Amount", font10bold));
			cell.setFixedHeight(1f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
		
			
			
			//----- dòng các con số nhỏ ----//
			cell = new PdfPCell(new Paragraph("1", font9bold));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("2", font9bold));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("3", font9bold));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("4", font9bold));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("5", font9bold));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("6 = 4 x 5", font9bold));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
		
			//---- vẽ 5 dòng dành cho sản phẩm (nếu lơn qua trang tiếp) ----//
			
			int maxLocalNumSp = sosanpham - currentIDsp >= sosp_1trang ? sosp_1trang : sosanpham - currentIDsp;
			System.out.println(" maxLocalNumSp : "  + maxLocalNumSp );
			
			
			
			for(int i=0; i< maxLocalNumSp ; i ++){
			IErpHoaDon_SP sanpham = splist.get(currentIDsp);
			double weight=0;
			
			
			total_soluong= total_soluong +sanpham.getSoLuong();
			total_thanhtien = total_thanhtien + sanpham.getThanhTien();

			weight=  Double.parseDouble(sanpham.getWeight());
			
		
			

			cell = new PdfPCell(new Paragraph(""+(i+1), font10bold));
			cell.setFixedHeight(0.5f * CONVERT);
			if(i==0){
				cell.setBorderWidthBottom(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(""+sanpham.getTen3(), font10bold));
			cell.setFixedHeight(0.5f * CONVERT);
			if(i==0){
				cell.setBorderWidthBottom(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			middleTable_2.addCell(cell);
			
			
			
			cell = new PdfPCell(new Paragraph(""+sanpham.getDonViTinhEng(), font10bold));
			cell.setFixedHeight(0.5f * CONVERT);
			if(i==0){
				cell.setBorderWidthBottom(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
	
			
			
			cell = new PdfPCell(new Paragraph(""+format.format(sanpham.getSoLuong()), font10bold));
			cell.setFixedHeight(0.5f*CONVERT);
			if(i==0){
				cell.setBorderWidthBottom(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setPaddingRight(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			middleTable_2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(""+sanpham.getDonGia(), font10bold));
			cell.setFixedHeight(0.5f * CONVERT);
			if(i==0){
				cell.setBorderWidthBottom(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setPaddingRight(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph(""+format.format(sanpham.getThanhTien()), font10bold));
			cell.setFixedHeight(0.5f * CONVERT);
			if(i==0){
				cell.setBorderWidthBottom(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setPaddingRight(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			middleTable_2.addCell(cell);
			
			dave++;
			

			
			//------ dong 2 - quy cach
			
			cell = new PdfPCell(new Paragraph("", font9));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setBorderWidthTop(0);
			cell.setBorderWidthBottom(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("Spec :"+sanpham.getQuycach(), font9));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setBorderWidthTop(0);
			cell.setBorderWidthBottom(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("", font9));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setBorderWidthTop(0);
			cell.setBorderWidthBottom(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			if(sanpham.getDonViTinhEng().toUpperCase().equals("KG")){
				cell = new PdfPCell(new Paragraph("", font9));
				cell.setFixedHeight(0.5f*CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setPaddingRight(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				middleTable_2.addCell(cell);	
			}else{
				cell = new PdfPCell(new Paragraph("("+format.format(Double.parseDouble(sanpham.getWeightQuydoi()))+" kgs)", font9));
				cell.setFixedHeight(0.5f*CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setPaddingRight(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				middleTable_2.addCell(cell);
			}
			
			cell = new PdfPCell(new Paragraph("", font9));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setBorderWidthTop(0);
			cell.setBorderWidthBottom(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("", font9));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setBorderWidthTop(0);
			cell.setBorderWidthBottom(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			dave++;
			
			
			//------ dòng 3 - quy cách đổi
			
			cell = new PdfPCell(new Paragraph("", font9));
			cell.setFixedHeight(0.5f * CONVERT);
			if((i-5)==-1){
				cell.setBorderWidthTop(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
		
			cell = new PdfPCell(new Paragraph("Weight : "+ format.format(Double.parseDouble(sanpham.getWeight())) +" kg/"+sanpham.getDonvitinhSP(), font9));
			cell.setFixedHeight(0.5f * CONVERT);
			if((i-5)==-1){
				cell.setBorderWidthTop(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			middleTable_2.addCell(cell);
		
			
			
			cell = new PdfPCell(new Paragraph("", font9));
			cell.setFixedHeight(0.5f * CONVERT);
			if((i-5)==-1){
				cell.setBorderWidthTop(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font9));
			cell.setFixedHeight(0.5f*CONVERT);
			if((i-5)==-1){
				cell.setBorderWidthTop(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font9));
			cell.setFixedHeight(0.5f * CONVERT);
			if((i-5)==-1){
				cell.setBorderWidthTop(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("", font9));
			cell.setFixedHeight(0.5f * CONVERT);
			if((i-5)==-1){
				cell.setBorderWidthTop(0);
			}
			else{
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			dave++;
			
			//----- dòng ghi  chú 1 sản phẩm
			
			
			
			String sanpham_ghichu =  obj.getSanphamGhiChu().get(sanpham.getIdSanPham());
			String chuoi="";
			if(sanpham_ghichu != null && sanpham_ghichu.trim().length() > 0 )
			{
			
	        	chuoi =sanpham_ghichu.replaceAll("__NA", "");
			}
			
			if(chuoi.length() > 0){
				cell = new PdfPCell(new Paragraph("", font9));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("Note : "+chuoi, font9));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				middleTable_2.addCell(cell);
				
				
				
				cell = new PdfPCell(new Paragraph("", font9));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				
				cell = new PdfPCell(new Paragraph("", font9));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font9));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font9));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				dave++;
	        }
			
			
			
			

			currentIDsp++;
		}
		
		
		
		// ------- vẽ những ô trống của 1 sp ----//
		
		chuave= sodong - dave;
		System.out.println("So dong max :" +sodong+"    dave :"+dave + "  chua ve "+chuave);
			
			
		if( chuave > 0){
			for(int i=0; i< chuave; i ++ ){

				cell = new PdfPCell(new Paragraph("", font10bold));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10bold));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10bold));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("", font10bold));
				cell.setFixedHeight(0.5f*CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);

				
				cell = new PdfPCell(new Paragraph("", font10bold));
				cell.setFixedHeight(0.5f*CONVERT);
				cell.setBorderWidthTop(0);
				cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				
				cell = new PdfPCell(new Paragraph("", font9));
				cell.setFixedHeight(0.5f * CONVERT);
				if( i- chuave==-1){
					cell.setBorderWidthTop(0);
				}
				else{
					cell.setBorderWidthTop(0);
					cell.setBorderWidthBottom(0);
				}
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
			
				
			}
		}
		
		
		
		// ------- vẽ dòng TOTAL ----//
		
		if(a== sotrangin -1){

		
			
			
			tienchietkhau= obj.getChietkhau() > 0 ? (obj.getChietkhau()* total_thanhtien/100) : obj.getTienChietKhau();
			
		
			tienkhuyenmai= obj.getTienkhuyenmai();
			tienvanchuyen= obj.getTienVanChuyen();
			tienbaohiem=obj.getTienBaoHiem();
			
			grand_total= total_thanhtien - tienchietkhau -tienkhuyenmai + tienvanchuyen +tienbaohiem;
			
		
			
			
			//--- dòng total --//
			cell = new PdfPCell(new Paragraph("", font10));
			cell.setFixedHeight(0.5f * CONVERT);
			if(total_soluong != grand_total){
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("   TOTAL", font10));
			cell.setFixedHeight(0.5f * CONVERT);
			if(total_soluong != grand_total){
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			middleTable_2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font10));
			cell.setFixedHeight(0.5f * CONVERT);
			if(total_soluong != grand_total){
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph(""+format.format(total_soluong), font10));
			cell.setFixedHeight(0.5f * CONVERT);
			if(total_soluong != grand_total){
				cell.setBorderWidthBottom(0);
			}
			cell.setPaddingRight(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			middleTable_2.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("", font10));
			cell.setFixedHeight(0.5f * CONVERT);
			if(total_soluong != grand_total){
				cell.setBorderWidthBottom(0);
			}
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			middleTable_2.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(""+format.format(total_thanhtien), font10));
			cell.setFixedHeight(0.5f * CONVERT);
			if(total_soluong != grand_total){
				cell.setBorderWidthBottom(0);
			}
			cell.setPaddingRight(5);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			middleTable_2.addCell(cell);
			
			
			//--- dòng CK --//
			
			if(tienchietkhau >0){
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
		
					cell.setBorderWidthBottom(0);
					cell.setBorderWidthTop(0);
			
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   Discount " + (obj.getChietkhau() >0 ? obj.getChietkhau()+"%": "") , font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(""+format.format(tienchietkhau), font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setPaddingRight(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				middleTable_2.addCell(cell);
			}
			
			

			//--- dòng KM --//
			
			if(tienkhuyenmai >0){
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
			
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   Promotion", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(""+ format.format(tienkhuyenmai), font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setPaddingRight(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				middleTable_2.addCell(cell);
			}
			
			
				//--- dòng Vận chuyển --//
			
			if(tienvanchuyen >0){
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
		
					cell.setBorderWidthBottom(0);
					cell.setBorderWidthTop(0);
			
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   Delivery", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(""+format.format(tienvanchuyen), font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setPaddingRight(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				middleTable_2.addCell(cell);
			}
			
			
				//--- dòng Bảo hiểm --//
			
				if(tienbaohiem >0){
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
		
					cell.setBorderWidthBottom(0);
					cell.setBorderWidthTop(0);
			
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   Insurance", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(""+format.format(tienbaohiem), font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setPaddingRight(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				middleTable_2.addCell(cell);
			
			}
				
				
				//--- dòng Grand_total --//
				
				if(grand_total != total_thanhtien){
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
		
					cell.setBorderWidthTop(0);
			
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("   Grand Total", font10bold));
				cell.setFixedHeight(0.5f * CONVERT);
	
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthBottom(0);
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
	
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(0.5f * CONVERT);
	
				cell.setBorderWidthTop(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				middleTable_2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(""+format.format(grand_total), font10bold));
				cell.setFixedHeight(0.5f * CONVERT);
				cell.setBorderWidthTop(0);
				cell.setPaddingRight(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				middleTable_2.addCell(cell);
			
			}	
				
				
					
		}
		
		
		
	
		
		
		document.add(middleTable_2);


		
		// ---- total in words ------//
		if(a== sotrangin -1){
		
		PdfPTable total_in_word = new PdfPTable(1);
		total_in_word.setWidths(new float[] { 18.6f* CONVERT });
		total_in_word.setWidthPercentage(100);

		 
				String tiencuoi=format.format(grand_total >0 ? grand_total: total_thanhtien);
				String doc_cents="";
				System.out.println("Tien cuoi : "+tiencuoi);
				if(tiencuoi.indexOf(".")>=0){
					String tien_le="0"+tiencuoi.substring( tiencuoi.indexOf(".") ,tiencuoi.length());
					doc_cents=DocTienEN.convert(Math.round( Double.parseDouble(tien_le)*100 ));
				}
				
				
				String doc_dollar = tiencuoi.substring(0, tiencuoi.indexOf("."));
				
			
				
				String chuoitienchan= DocTienEN.convert(Long.parseLong(doc_dollar.replaceAll(",","")))  ;
				if(doc_cents.length() >0){
					chuoitienchan=chuoitienchan+" and cents "+doc_cents;
				}
			cell = new PdfPCell(new Paragraph("  Total in words :    "+ chuoitienchan, font10));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			total_in_word.addCell(cell);
			
		document.add(total_in_word);
		}
		
		
		//---- REMARKS ----//
		
		PdfPTable remarks = new PdfPTable(1);
		remarks.setWidths( new float[] {22.1f* CONVERT});
		remarks.setWidthPercentage(100);
		remarks.getDefaultCell().setBorderWidthTop(0);
	
		
			cell = new PdfPCell(new Paragraph(" REMARKS: ", font10underline));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setBorderWidthBottom(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			remarks.addCell(cell);
			
			
			PdfPTable remarks_inra = new PdfPTable(2);
			remarks_inra.setWidths( new float[] {2f*CONVERT,20.1f* CONVERT});
			remarks_inra.setWidthPercentage(100);
		
			
				cell = new PdfPCell(new Paragraph("", font10));
				cell.setFixedHeight(4f * CONVERT);
				cell.setBorder(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				remarks_inra.addCell(cell);
			
				cell = new PdfPCell(new Paragraph(""+obj.getRemarks().trim(), font10));
				cell.setFixedHeight(4f * CONVERT);
				cell.setBorder(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				remarks_inra.addCell(cell);
			 remarks.addCell(remarks_inra);	
				
		document.add(remarks);
		
		
		
		PdfPTable footer = new PdfPTable(TABLE_FOOTER_WIDTHS.length);
		footer.setWidths(TABLE_FOOTER_WIDTHS);
		footer.setWidthPercentage(100);
			cell = new PdfPCell(new Paragraph("", font10bold));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setBorder(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			footer.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("SELLER", font10bold));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setBorder(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			footer.addCell(cell);
			
			
			cell = new PdfPCell(new Paragraph("", font10bold));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setBorder(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			footer.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Sign, stamp, full name", font9));
			cell.setFixedHeight(0.5f * CONVERT);
			cell.setBorder(0);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			footer.addCell(cell);
			
		document.add(footer);	
		
			//Them trang moi neu con san pham chua in
			if(a < sotrangin-1) {
				document.newPage();
			
		}
		
		}
		document.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

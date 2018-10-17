package geso.traphaco.erp.servlets.tieuhao;

//import geso.dms.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.tieuhao.*;
import geso.traphaco.erp.beans.tieuhao.imp.*;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tieuhao.IErpTieuHaoList;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpTieuHaoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpTieuHaoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//System.out.println("\n[ErpTieuHaoSvl.doGet] begin...");
		
		IErpTieuHaoList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	  
	    String ctyId = (String)session.getAttribute("congtyId");  

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String thId = util.getId(querystring);
	    
	    obj = new ErpTieuHaoList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    
    	if(action.equals("chot"))
    	{
    		String msg = ChotTieuHao(thId, userId,ctyId);
    		obj.setMsg(msg);
    	}
    	else if(action.equals("print"))
    	{
    		IErpTieuHao tieuhaoBean = new ErpTieuHao(thId);
    		tieuhaoBean.setCongtyId(ctyId);
    		tieuhaoBean.setUserId(userId); // phai co UserId truoc khi Init
    		tieuhaoBean.init();
    		tieuhaoBean.close();
    		
    		response.setContentType("application/pdf");
    		response.setHeader("Content-Disposition"," inline; filename=PhieuNhapKhoTT.pdf");
    		
    		//Rectangle a4Landscape = a4.rotate();
    		Document document = new Document(PageSize.A4.rotate());
    		ServletOutputStream outstream = response.getOutputStream();
    		
    		this.CreatePhieuTieuHao(document, outstream, tieuhaoBean);
    		
    		return;
    	}
	    
	    obj.setUserId(userId);
	    String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
 		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    obj.init("");
	    util.setSearchToHM(userId, session, this.getServletName(), "");
		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuHao.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		System.out.println("\n[ErpNhanhang_GiaySvl.doPost] begin...");
		
		IErpTieuHaoList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
       	if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
    		obj = new ErpTieuHaoList();
    		obj.setUserId(userId);
    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	
	    	obj.init(search);
	    	
	    	String querySearch = GiuDieuKienLoc.createParams(obj);
			util.setSearchToHM(userId, session,this.getServletName(), querySearch);
			
			
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpTieuHao.jsp");	
	    }else if(action.equals("tieuhaothem")){
	 
			String id =  request.getParameter("id");
			IErpTieuHao tieuhaoBean = new ErpTieuHao(id);
		
			tieuhaoBean.setCongtyId((String)session.getAttribute("congtyId"));
			tieuhaoBean.setUserId(userId); // phai co UserId truoc khi Init
			 
			session.setAttribute("nccId", tieuhaoBean.getnccid());			
			
			String nextJSP;
			
			 if(tieuhaoBean.CreateTieuhaoThem(id )){
				 
				 tieuhaoBean.init();
				 
				  nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuHaoUpdate.jsp";
				
			 }
			 else
			 {
				 tieuhaoBean.init();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuHaoDisplay.jsp";
			 }
			 
			 session.setAttribute("tieuhaoBean", tieuhaoBean);			
			 response.sendRedirect(nextJSP);
	    }
    	else
    	{
	    	obj = new ErpTieuHaoList();
	    	obj.setUserId(userId);
	    	obj.setCongtyId((String)session.getAttribute("congtyId"));
	    	
	    	String search = getSearchQuery(request, obj);
			
	    	obj.init(search);
		
	    	String querySearch = GiuDieuKienLoc.createParams(obj);
			util.setSearchToHM(userId, session,this.getServletName(), querySearch);
			
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpTieuHao.jsp");
    	}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpTieuHaoList obj)
	{
		String query = 
			" SELECT ISNULL(ISBOOKTIEUHAO,0 ) AS  ISBOOKTIEUHAO, A.PK_SEQ, A.NHANHANG_FK, A.SANPHAM_FK, B.MA AS SPMA, B.TEN AS SPTEN, ISNULL(A.SOLUONG, 0) AS SOLUONG, C.TEN AS NGUOITAO, D.TEN AS NGUOISUA, A.NGAYTAO, A.NGAYSUA, ISNULL(A.TRANGTHAI, '0') AS TRANGTHAI, A.PREFIX " +
			" FROM ERP_TIEUHAO A " +
			" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ " +
			" INNER JOIN NHANVIEN C ON C.PK_SEQ = A.NGUOITAO " +
			" INNER JOIN NHANVIEN D ON D.PK_SEQ = A.NGUOISUA " +
			" WHERE A.CONGTY_FK = '" + obj.getCongtyId() + "' ";
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String manhanhang = request.getParameter("nhanhangId");
		if(manhanhang == null)
			manhanhang = "";
		obj.setNhanHangId(manhanhang);
		
		String tieuhaoid = request.getParameter("tieuhaoid");
		if(tieuhaoid == null)
			tieuhaoid = "";
		obj.setId(tieuhaoid);
		
		
		String SoPo = request.getParameter("SoPo");
		if(SoPo == null)
			SoPo = "";
		obj.setSoPo(SoPo);
		
		
		
		
		
		if(tungay.length() > 0)
			query += " and a.NGAYTAO >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.NGAYTAO <= '" + denngay + "'";
		
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(manhanhang.length() > 0)
			query += " and '120' + CAST(a.NHANHANG_FK as varchar(20)) like N'%" + manhanhang + "%'  ";
		
		if(tieuhaoid.length() > 0)
			query += " and '210' + CAST(a.PK_SEQ as varchar(20)) like N'%" + tieuhaoid + "%'  ";
		if(SoPo.length()>0){
			query += "  and  a.PK_SEQ  in (select nh.pk_seq from ERP_TIEUHAO nh inner join erp_muahang mh on mh.pk_seq=nh.muahang_fk where mh.isgiacong='1' and mh.sopo   like '%"+SoPo+"%') ";
		}
		
		return query;
	}
	

	private String ChotTieuHao(String thId, String userId,String ctyId) 
	{
		IErpTieuHao tieuhao = new ErpTieuHao(thId);
		tieuhao.setUserId(userId);
		tieuhao.setCongtyId(ctyId);
		String msg = tieuhao.chot();
		tieuhao.close();
		
		return msg;
	}
	
	private void CreatePhieuTieuHao(Document document, ServletOutputStream outstream, IErpTieuHao tieuhao) throws IOException {
		dbutils db = new dbutils();
		try
		{			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			PdfWriter.getInstance(document, outstream);
			document.open();

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			//font2.setColor(BaseColor.GREEN);
			
			String 
			query = " SELECT ten, diachi, masothue, dienthoai, fax FROM ERP_CONGTY WHERE PK_SEQ = 100005 ";
			ResultSet rsCt = db.get(query);
			String ctyTen = "";
			String ctyDiachi = "";
			String ctyMasothue = "";
			String ctyDienthoai = "";
			String ctyFax = "";
			try {
				rsCt.next();
				ctyTen = rsCt.getString("ten");
				ctyDiachi = rsCt.getString("diachi");
				ctyMasothue = rsCt.getString("masothue");
				ctyDienthoai = rsCt.getString("dienthoai");
				ctyFax = rsCt.getString("fax");
				rsCt.close();
			} catch(Exception e) {
				ctyTen = "CÔNG TY CỔ PHẦN HÀNG TIÊU DÙNG PROVENCE";
				ctyDiachi = "Lầu 8 161 Võ Văn Tần, Phường 6, Quận 3, Tp.Hồ Chí Minh";
				ctyMasothue = "0 3 1 0 7 7 6 0 7 1";
				ctyDienthoai = "(08) 62905560";
				ctyFax = "(08) 62905104";
			}
			 
			Paragraph pxk = new Paragraph("Đơn vị: " + ctyTen, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("Địa chỉ: " + ctyDiachi, new Font(bf, 8, Font.NORMAL));
			pxk.setSpacingAfter(2);
			pxk.setSpacingBefore(-25);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			pxk = new Paragraph("PHIẾU TIÊU HAO", font);
			pxk.setSpacingAfter(3);
			pxk.setSpacingBefore(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			pxk = new Paragraph("Số: " + tieuhao.getId(), new Font(bf, 7, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_RIGHT);
			document.add(pxk);
			
			pxk = new Paragraph(getDate(tieuhao.getNgaytieuhao()),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);
			
			String khoTieuHao = tieuhao.getKhotieuhao();
			
			pxk = new Paragraph(("Kho: "+khoTieuHao),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			
			String dtth = tieuhao.getSanphamTen();
			
			pxk = new Paragraph(("Đối tượng tiêu hao: "+dtth),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			String ghichu = tieuhao.getGhichu();
			
			pxk = new Paragraph(("Ghi chú: "+ghichu),  new Font(bf, 9, Font.NORMAL));
			pxk.setSpacingAfter(5);
			pxk.setAlignment(Element.ALIGN_LEFT);
			document.add(pxk);
			
			
			//Table Content
			PdfPTable root = new PdfPTable(2);
			root.setKeepTogether(false);
			root.setSplitLate(false);
			root.setWidthPercentage(100);
			root.setHorizontalAlignment(Element.ALIGN_LEFT);
			root.getDefaultCell().setBorder(0);
			float[] cr = {95.0f, 100.0f};
			root.setWidths(cr);
			
			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {7.0f, 22.0f, 40.0f, 13.0f, 13.0f};
	        table.setWidths(withs);
	        
	        Font font4 = new Font(bf, 9, Font.BOLD);
		
	        float[] withsKM = {15.0f, 70.0f, 40.0f, 30.0f, 30.0f, 30.0f };
			PdfPTable sanpham = new PdfPTable(withsKM.length);
			sanpham.setWidthPercentage(100);
			sanpham.setHorizontalAlignment(Element.ALIGN_LEFT);
			sanpham.setWidths(withsKM);
		    
		    String[] th = new String[]{"STT", "Tên, nhãn hiệu, quy cách, phẩm chất vật tư (sản phẩm, hàng hóa)", "Mã số", "ĐVT", "Định mức", "Thực tế"};
			PdfPCell[] cell = new PdfPCell[10];
			for(int i= 0; i < th.length ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font4));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				if(i == 1)
					cell[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				
				cell[i].setPadding(5);
				sanpham.addCell(cell[i]);			
			}
			
			
			List<ISanpham> spList = tieuhao.getSpList();
			PdfPCell cells = new PdfPCell();
			float totalTrongLuong = 0;
			float totalTheTich = 0;
			double totalSoluong=0;
			double totalSoluongTT=0;
			double totalthung=0;
			double totalle=0;
			
			
			for(int i = 0; i < spList.size(); i++)
			{
				ISanpham sp = (ISanpham)spList.get(i);
				String[] arr = new String[]{Integer.toString(i+1), sp.getTen(), sp.getMa(), sp.getDonViTinh(), 
						formatter.format(Math.round(sp.getSoLuongChuan())), formatter.format(Math.round(sp.getSoLuongThucTe()))
				};
				
				totalSoluong += Math.round(sp.getSoLuongChuan());
				totalSoluongTT += Math.round(sp.getSoLuongThucTe());
				
				for(int j = 0; j < th.length; j++)
				{
					cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 9, Font.NORMAL)));
					if(j == 1)
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					else
					{
						cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						if( j >= 5)
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(3.0f);
					
					sanpham.addCell(cells);
				}
			}
			document.add(sanpham);
			
			//Table Footer			
			PdfPTable tableFooter = new PdfPTable(4);
			tableFooter.setWidthPercentage(90);
			tableFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableFooter.setWidths(new float[]{38.0f, 37.0f, 38.0f, 37.0f});
			
			PdfPCell cell11 = new PdfPCell(new Paragraph("Người lập phiếu", new Font(bf, 9, Font.BOLD)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell13 = new PdfPCell(new Paragraph("Thủ kho", new Font(bf, 9, Font.BOLD)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell14 = new PdfPCell(new Paragraph("Người nhận", new Font(bf, 9, Font.BOLD)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell15 = new PdfPCell(new Paragraph("Kế toán", new Font(bf, 9, Font.BOLD)));
			cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell11.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);
			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			cell11 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell13 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell14 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell14.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell15 = new PdfPCell(new Paragraph("(Ký, họ tên)", new Font(bf, 9, Font.ITALIC)));
			cell15.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			cell11.setBorder(0);
			cell13.setBorder(0);
			cell14.setBorder(0);
			cell15.setBorder(0);
			
			tableFooter.addCell(cell11);
			tableFooter.addCell(cell13);
			tableFooter.addCell(cell14);
			tableFooter.addCell(cell15);
			
			document.add(tableFooter);
			document.close();
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		db.shutDown();
	}



	private String getDate(String date)
	{
		String arr[] = date.split("-");
		String nam = arr[0];
		String thang = arr[1];
		String ngay = arr[2];
		
		return "Ngày  " + ngay + "  tháng  " + thang + "  Năm  " + nam;
	}
}

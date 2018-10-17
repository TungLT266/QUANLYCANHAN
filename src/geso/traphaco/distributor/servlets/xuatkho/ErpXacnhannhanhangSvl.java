package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpXacnhannhanhang;
import geso.traphaco.distributor.beans.xuatkho.IErpXacnhannhanhangList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpXacnhannhanhang;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpXacnhannhanhangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpXacnhannhanhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpXacnhannhanhangSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpXacnhannhanhangList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	    
	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
    	String lsxId = util.getId(querystring);
	    obj = new ErpXacnhannhanhangList();
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));

	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    String type = request.getParameter("type");
	    if( type == null )
	    	type = "";
	    
	    if( type.equals("inPDF") )
	    { 
	    	/*response.setContentType("application/pdf");

			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			try 
			{
				String id = request.getParameter("id");
				CreatePdf(document, outstream, id, userId);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}*/
	    	
	    	String id = request.getParameter("id"); 	
		    IErpXacnhannhanhang lsxBean = new ErpXacnhannhanhang(id);
		    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
		    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
		    lsxBean.setUserId(userId); 

			session.setAttribute("lsxBean", lsxBean);
				
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXacNhanNhanHangPrint.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		String msg = this.Chot(lsxId, userId, nppId);
				obj.setMsg(msg);
	    	}
	    	else if(action.equals("delete"))
	    	{
	    		String msg = this.Delete(lsxId, nppId);
				obj.setMsg(msg);
	    	}
		    
	    	obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	obj.setNpp_duocchon_id(npp_duocchon_id);
		    obj.setUserId(userId);
		    obj.init("");
		    
			session.setAttribute("obj", obj);
				
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXacNhanNhanHang.jsp";
			response.sendRedirect(nextJSP);
	    }
	    
	}

	private String Delete(String lsxId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			query = "delete ERP_XACNHANNHANHANG_DDH where soxuathang_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_XACNHANNHANHANG_DDH " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_XACNHANNHANHANG_SANPHAM where soxuathang_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_XACNHANNHANHANG_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_XACNHANNHANHANG_SANPHAM_CHITIET where soxuathang_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_XACNHANNHANHANG  where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_XACNHANNHANHANG " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}
	
	private String Chot(String lsxId, String userId, String nppId) 
	{
		dbutils db = new dbutils();
		
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			query = "update ERP_XACNHANNHANHANG set trangthai = '1'  where pk_seq = '" + lsxId + "' ";
			System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_XACNHANNHANHANG " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//Chèn thêm NVGN tại thời điểm đó
			query =  "Insert ERP_XACNHANNHANHANG_NHANVIEN(xacnhannhanhang_fk, nhanvien_fk, isNVGN)  "+
					 "select distinct '" + lsxId + "' as xacnhannhanhang_fk, e.PK_SEQ, '1' as isNVGN "+
					 "from ERP_XACNHANNHANHANG_DDH a inner join ERP_HOADONNPP b on a.hoadon_fk = b.PK_SEQ "+
					 "	inner join KHACHHANG c on b.KHACHHANG_FK = c.PK_SEQ "+
					 "	inner join NVGN_KH d on c.PK_SEQ = d.KHACHHANG_FK "+
					 "	inner join NHANVIENGIAONHAN e on d.NVGN_FK = e.PK_SEQ "+
					 "where a.xacnhannhanhang_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_XACNHANNHANHANG " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();	  
	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
		IErpXacnhannhanhangList obj = new ErpXacnhannhanhangList();
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		obj.setDoituongId(session.getAttribute("doituongId"));
		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		obj.setNpp_duocchon_id(npp_duocchon_id);
		    
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    obj.setUserId(userId);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setNppTen(khId);
	    
	   
	    
	    System.out.println("---NPP ID: " + nppId);
	    if(action.equals("Tao moi"))
	    {
	    	IErpXacnhannhanhang lsxBean = new ErpXacnhannhanhang();
	    	lsxBean.setUserId(userId);
	    	
	    	lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	lsxBean.setDoituongId(session.getAttribute("doituongId"));
			    
	    	//lsxBean.setXuatcho(phanloai);
	    	lsxBean.createRs();
	    	
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXacNhanNhanHangNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXacNhanNhanHang.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	System.out.println();
		    	
		    	System.out.println("câu truy vấn searchhhhh:"+ search);
		    	
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpXacNhanNhanHang.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpXacnhannhanhangList obj)
	{
		Utility util = new Utility();
		String query =
				"select  distinct a.PK_SEQ, a.machungtu, a.trangthai, a.ngayyeucau, isnull(isnull(c.ten, d.ten),'') as nppTEN, isnull(b.ten, '') as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
				"	 (	Select hd.sohoadon + ', ' AS [text()]  " +
				"		From ERP_XACNHANNHANHANG_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadon_fk = hd.pk_seq  " +
				"		Where YCXK1.xacnhannhanhang_fk = a.pk_seq  " +
				"		For XML PATH ('') )  as ddhIds    " +
						"from ERP_XACNHANNHANHANG a left join KHO b on a.kho_fk = b.pk_seq " +
						"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
						"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
						"   left join NVGN_KH nvgn on a.KHACHHANG_FK=nvgn.KHACHHANG_FK   "+
						"   left join ddkd_khachhang ddkd on a.PK_SEQ=ddkd.khachhang_fk "+
						"   left join NHANVIENGIAONHAN nvgnn on nvgnn.PK_SEQ=nvgn.NVGN_FK  "+	
						"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + obj.getNppId() + "'"; 
		
		// mã chứng từ
		String machungtu = request.getParameter("machungtu");
		if(machungtu == null){
			machungtu = "";
		}
		obj.setMachungtu(machungtu);
		// người xacs nhận
	    String nguoixacnhan = request.getParameter("nguoixacnhan");
	    if( nguoixacnhan == null){
	    	nguoixacnhan = "";
	    }
	    obj.setNguoixacnhan(nguoixacnhan);
	    
	    // ghi chú
	    String ghichu = request.getParameter("ghichu");
	    if(ghichu ==null){
	    	ghichu = "";
	    }
	    obj.setGhichu(ghichu);
	    
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String nvbanhang=request.getParameter("nvbanhang");
		if(nvbanhang==null)
			nvbanhang="";
		obj.setNvbanhang(nvbanhang);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
	
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		
		String khohh = request.getParameter("khohhid");
		if(khohh == null)
			khohh = "";
		obj.setKhohh(khohh);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String nguoigiao = request.getParameter("nguoigiao");
		if(nguoigiao == null)
			nguoigiao = "";
		obj.setNguoigiao(nguoigiao);
		
		// mã chứng từ
		if(machungtu.trim().length() >0){
			query += " and a.machungtu like '%" +machungtu+"%'";
		}
		// ghi chú
		if(ghichu.length() >0){
			query += " and a.GHICHU =N'%"+ ghichu +"%'";
		}
		// người xác nhận
		if(nguoixacnhan.trim().length() >0){
			query += " and ddkd.ddkd_fk ="+ nguoixacnhan;
		}
		if(tungay.length() > 0)
			query += " and a.ngayyeucau >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayyeucau <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
		{
			if(trangthai.equals("0"))
				query += " and a.TrangThai = '" + trangthai + "'";
			else
				query += " and a.TrangThai >= '" + trangthai + "'";
		}
		
		if(khId.length() > 0)
		{
			//query += " and isnull(a.npp_Dat_fk, a.khachhang_Fk) = '" + khId + "'";
			//query += " and isnull(d.timkiem, c.timkiem) like N'%" + khId + "%'";
			query += " and (isnull(d.ten, c.ten) like N'%" + khId + "%' or isnull(d.maFAST,c.maFAST) like '"+ khId +"')";
		}
		
		if(khohh.length()>0)
		{
			//query+=" and a.kho_fk="+khohh;
			query += " and a.PK_SEQ in (select YC.xacnhannhanhang_fk from  ERP_XACNHANNHANHANG_DDH YC " +
					 " inner join ERP_XACNHANNHANHANG XN on XN.PK_SEQ = YC.xacnhannhanhang_fk  " +
					 " inner join ERP_HOADONNPP ddh on ddh.PK_SEQ = yc.hoadon_fk and ddh.Kho_FK =" + khohh+")";
		}
		
		if(nguoitao.length()>0)
		{
			query+=" and dbo.ftBoDau( nv.ten ) like N'%" + util.replaceAEIOU( nguoitao ) + "%'";
		}
		if(nguoigiao.length()>0)
		{
			query+=" and dbo.ftBoDau( nvgnn.ten ) like N'%" + util.replaceAEIOU( nguoigiao ) + "%'";
		}
		
		if(nvbanhang.length()>0)
		{
			query+=" and ddkd.ddkd_fk="+nvbanhang;
		}
		System.out.print("Câu truy vấn tìm kiếm :"+query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
	private void CreatePdf(Document document, ServletOutputStream outstream, String id, String userId) 
	{
		dbutils db = new dbutils();

		NumberFormat formatter2 = new DecimalFormat("#,###,###");
		try
		{
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 9, Font.BOLD);
			Font font8normal = new Font(bf, 8, Font.NORMAL);
			Font font8bold = new Font(bf, 8, Font.BOLD);
			Font font4 = new Font(bf, 7, Font.BOLD);
			Font font4normal = new Font(bf, 7, Font.NORMAL);
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			String tencongty = "";
			String diachi = "", dienthoainpp = "", fax="";
			String query = "select ten, diachi, dienthoai, fax from nhaphanphoi where pk_seq = ( select npp_fk from ERP_XACNHANNHANHANG where pk_seq = '" + id + "' ) ";
			ResultSet rs = db.get(query);
			try 
			{
				rs.next();
				tencongty = rs.getString("ten");
				diachi = rs.getString("diachi");
				dienthoainpp = rs.getString("dienthoai");
				fax = rs.getString("fax");
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			query = " select b.ten as nguoitao, a.machungtu, CONVERT(VARCHAR(20), a.Created_Date, 120) as ngaylap " + 
					" from ERP_XACNHANNHANHANG a inner join NHANVIEN b on a.nguoitao = b.pk_seq " +
					"	inner join NHAPHANPHOI c on a.npp_fk = c.pk_seq	" +
					" where a.PK_SEQ = "+id;
			
			ResultSet khors = db.get(query);
			
			String khohanghoa = "";
			String nguoilap = "";
			String machungtu = "";
			String ngaylap = "";
			try 
			{
				if(khors.next())
				{
					khohanghoa = "Kho hàng bán";
					nguoilap = khors.getString("nguoitao");
					machungtu = khors.getString("machungtu");
					ngaylap = khors.getString("ngaylap");
				}
				khors.close();
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
			}

			Paragraph tieude1 = new Paragraph(tencongty, font4);
			tieude1.setIndentationRight(0);
			document.add(tieude1);

			Paragraph tieude2 = new Paragraph(khohanghoa, font4normal);
			tieude2.setIndentationRight(0);
			document.add(tieude2);

			Paragraph tieude3 = new Paragraph(diachi.trim(), font4normal);
			tieude3.setIndentationRight(0);
			document.add(tieude3);
			
			Paragraph tieude4 = new Paragraph("Tel: " + dienthoainpp.trim() + " - Fax: " + fax, font4normal);
			tieude4.setIndentationRight(0);
			document.add(tieude4);

			
			String str_tieude = "BIÊN BẢN NHẬN HÀNG";
			Paragraph tieude = new Paragraph(str_tieude, font);
			tieude.setAlignment(Element.ALIGN_CENTER);
			tieude.setSpacingAfter(5);
			document.add(tieude);
			
			str_tieude = "( Vui lòng fax về công ty theo số fax sau: " + fax + " )";
			tieude = new Paragraph(str_tieude, font8normal);
			tieude.setAlignment(Element.ALIGN_CENTER);
			tieude.setSpacingAfter(10);
			document.add(tieude);


			String makh = "", tenkh = "", diachiKH = "", sodonhang = "", sohoadon = "";
			query = "select b.maFAST, b.TEN, isnull(b.diachiGiaohang, b.diachi) as diachi,  "+
					 "			 (	Select hd.sohoadon + ', ' AS [text()]   "+
					 "				From ERP_XACNHANNHANHANG_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadon_fk = hd.pk_seq   "+
					 "				Where YCXK1.xacnhannhanhang_fk = a.pk_seq   "+
					 "				For XML PATH ('') )  as sohoadon, "+
					 "			(	select distinct dh.machungtu + ', ' as [text()]   "+
					 "				from ERP_DONDATHANGNPP dh inner join ERP_HOADONNPP_DDH hd_dh on dh.PK_SEQ = hd_dh.DDH_FK "+
					 "					inner join  ERP_XACNHANNHANHANG_DDH YCXK1 on hd_dh.HOADONNPP_FK = YCXK1.hoadon_fk "+
					 "				where YCXK1.xacnhannhanhang_fk = a.pk_seq     "+
					 "				For XML PATH ('') )  as sodonhang      "+
					 "from ERP_XACNHANNHANHANG a inner join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ  "+
					 "where a.PK_SEQ = '" + id + "' ";
			ResultSet ttkhrs = db.get(query);
			try 
			{
				if(ttkhrs.next())
				{
					makh = ttkhrs.getString("maFAST");
					tenkh = ttkhrs.getString("TEN");
					diachiKH = ttkhrs.getString("diachi");
					sodonhang = ttkhrs.getString("sodonhang");
					sohoadon = ttkhrs.getString("sohoadon");
				}
				ttkhrs.close();
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
			

			float[] withs = { 50f, 50f };
			PdfPTable table = new PdfPTable(withs);
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell cell = new PdfPCell();
			Paragraph p1 = new Paragraph();
			
			p1 = new Paragraph();
			p1.add(new Phrase( "THÔNG TIN CHỨNG TỪ: ", font8bold));
			cell = new PdfPCell(p1);
			cell.setColspan(2);
			cell.setPadding(5.0f);
			cell.setBorder(1);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "Mã chứng từ: ", font8bold));
			p1.add(new Phrase( machungtu , font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "Người lập: ", font8bold));
			p1.add(new Phrase( nguoilap, font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "CÔNG TY CỔ PHẦN DƯỢC PHANAM XÁC NHẬN VIỆC XUẤT HÀNG CHO: ", font8bold));
			cell = new PdfPCell(p1);
			cell.setColspan(2);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "Mã khách hàng: ", font8bold));
			p1.add(new Phrase( makh, font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "Khách hàng: ", font8bold));
			p1.add(new Phrase( tenkh, font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase("Địa chỉ: ", font8bold));
			p1.add(new Phrase( diachiKH, font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "Số đơn hàng: ", font8bold));
			p1.add(new Phrase( sodonhang, font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "CHÚNG TÔI GỒM CÓ: ", font8bold));
			cell = new PdfPCell(p1);
			cell.setColspan(2);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			//TABLE DANH SÁCH NHÂN VIÊN
			cell = new PdfPCell(p1);
			cell.setColspan(2);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			
			float[] withs2 = { 50f, 50f, 50f };
			PdfPTable table2 = new PdfPTable(withs2);
			table2.setWidthPercentage(100);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell cellINFO = new PdfPCell(new Paragraph("Họ và tên", font2));
			cellINFO.setPadding(2.0f);
			cellINFO.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellINFO.setBorder(0);
			table2.addCell(cellINFO);
			
			cellINFO = new PdfPCell(new Paragraph("Chức vụ", font2));
			cellINFO.setPadding(2.0f);
			cellINFO.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellINFO.setBorder(0);
			table2.addCell(cellINFO);
			
			cellINFO = new PdfPCell(new Paragraph("Ký tên", font2));
			cellINFO.setPadding(2.0f);
			cellINFO.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellINFO.setBorder(0);
			table2.addCell(cellINFO);
			
			//LẤY DANH SÁCH NHÂN VIÊN
			query = "select b.TEN, '' as chucvu, stt  "+
					 "from ERP_XACNHANNHANHANG_NHANVIEN a inner join NHANVIEN b on a.nhanvien_fk = b.PK_SEQ where xacnhannhanhang_fk = '" + id + "' and isNVGN = '0' "+
					 "union ALL		 "+
					 "select b.TEN, N'NV Cung ứng' as chucvu, stt "+
					 "from ERP_XACNHANNHANHANG_NHANVIEN a inner join NHANVIENGIAONHAN b on a.nhanvien_fk = b.PK_SEQ where xacnhannhanhang_fk = '" + id + "' and isNVGN = '1' "+
					 "order by stt asc ";
			
			System.out.println("::: LAY NHAN VIEN: " + query );
			rs = db.get(query);
			if( rs != null )
			{
				while(rs.next())
				{
					String[] th = new String[] { rs.getString("TEN"), rs.getString("chucvu"), " " };
					PdfPCell[] cell4 = new PdfPCell[3];
					for (int i = 0; i < th.length; i++)
					{
						cell4[i] = new PdfPCell(new Paragraph(th[i], font8normal));
						
						if( i == 0 )
							cell4[i].setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cell4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						
						cell4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell4[i].setBorder(0);
						cell4[i].setPadding(3.0f);
						table2.addCell(cell4[i]);
					}
				}
				rs.close();
			}
			
			cell.addElement(table2);
			table.addCell(cell);
			//END TABLE DANH SÁCH NHÂN VIÊN
			
	
			p1 = new Paragraph();
			p1.add(new Phrase( "VỚI CÁC SẢN PHẨM KÈM THEO NHƯ SAU: ", font8bold));
			cell = new PdfPCell(p1);
			cell.setColspan(2);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			//TABLE SẢN PHẨM
			cell = new PdfPCell(p1);
			cell.setColspan(2);
			cell.setPadding(2.0f);
			
			float[] withs3 = { 10f, 30f, 15f, 15f, 30f, 50f };
			PdfPTable table3 = new PdfPTable(withs3);
			table3.setWidthPercentage(100);
			table3.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			String[] th = new String[] { "STT", "Tên sản phẩm", "ĐVT", "Số lượng", "Ghi chú", "Ghi chú chi tiết" };
			PdfPCell[] cell4 = new PdfPCell[6];
			for (int i = 0; i < th.length; i++)
			{
				cell4[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4[i].setBorder(0);
				cell4[i].setPadding(5.0f);
				table3.addCell(cell4[i]);
			}
			
			//LẤY DANH SÁCH SẢN PHẨM
			query = "select b.TEN, sum(a.soluongXUAT) as soluong, c.donvi  "+
					 "from ERP_XACNHANNHANHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
					 "	inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ "+
					 "where a.xacnhannhanhang_fk = '" + id + "' "+
					 "group by b.TEN, c.donvi ";
			
			System.out.println("::: LAY SAN PHAM: " + query );
			rs = db.get(query);
			if( rs != null )
			{
				int stt = 1;
				while(rs.next())
				{
					th = new String[] { Integer.toString(stt), rs.getString("TEN"), rs.getString("soluong"), rs.getString("donvi"), " ", " " };
					cell4 = new PdfPCell[6];
					for (int i = 0; i < th.length; i++)
					{
						cell4[i] = new PdfPCell(new Paragraph(th[i], font8normal));
						
						if( i == 1 )
							cell4[i].setHorizontalAlignment(Element.ALIGN_LEFT);
						else
							cell4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
						
						cell4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell4[i].setBorder(0);
						cell4[i].setPadding(10.0f);
						
						table3.addCell(cell4[i]);
					}
					
					stt++;
				}
				rs.close();
				
				//KHI NAO CUNG CHEN 8 SP
				if( stt <= 8 )
				{
					while( stt <= 8 )
					{
						th = new String[] { " ", " ", " ", " ", " ", " " };
						cell4 = new PdfPCell[6];
						for (int i = 0; i < th.length; i++)
						{
							cell4[i] = new PdfPCell(new Paragraph(th[i], font8normal));
							
							if( i == 1 )
								cell4[i].setHorizontalAlignment(Element.ALIGN_LEFT);
							else
								cell4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
							
							cell4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell4[i].setBorder(0);
							cell4[i].setPadding(10.0f);
							
							table3.addCell(cell4[i]);
						}
						
						stt++;
					}
				}
				
			}
			
			cell.setBorder(0);
			cell.addElement(table3);
			table.addCell(cell);
			
			//END TABLE SẢN PHẨM
			
			
			p1 = new Paragraph();
			p1.add(new Phrase( "CÁC CHỨNG TỪ KÈM THEO NHƯ SAU: ", font8bold));
			cell = new PdfPCell(p1);
			cell.setColspan(2);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "Số lượng thùng hàng: ", font8bold));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "Bằng chữ: ", font8bold));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setBorder(0);
			table.addCell(cell);
			
			
			cell = new PdfPCell();
			cell.setPadding(5.0f);
			cell.setBorder(0);
			cell.setColspan(2);
			
			//TABLE THÔNG TIN HÓA ĐƠN
			
			float[] withs4 = { 80f, 30f, 15f, 25f };
			PdfPTable table4 = new PdfPTable(withs4);
			table4.setWidthPercentage(100);
			table4.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			th = new String[] { "Số hóa đơn(thuế GTGT): ", "Trong thùng hàng số: ", "Gửi chủ xe: ", "Biên bản nhận hàng: " };
			cell4 = new PdfPCell[4];
			for (int i = 0; i < th.length; i++)
			{
				cell4[i] = new PdfPCell(new Paragraph(th[i], font8bold));
				cell4[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4[i].setBorder(0);
				cell4[i].setPadding(5.0f);
				table4.addCell(cell4[i]);
			}
			
			th = new String[] { sohoadon, " ", " ", " " };
			cell4 = new PdfPCell[4];
			for (int i = 0; i < th.length; i++)
			{
				cell4[i] = new PdfPCell(new Paragraph(th[i], font8bold));
				cell4[i].setHorizontalAlignment(Element.ALIGN_LEFT);
				cell4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4[i].setBorder(0);
				cell4[i].setPadding(5.0f);
				table4.addCell(cell4[i]);
			}
			
			cell.addElement(table4);
			table.addCell(cell);
			
			//END TABLE THÔNG TIN HÓA ĐƠN
			
			
			//THÔNG TIN BÊN DƯỚI
			p1 = new Paragraph();
			p1.add(new Phrase( " ", font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setColspan(2);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( " ", font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "Ngày   Tháng   Năm 2015", font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "Nhân viên bán hàng", font8bold));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "Đã nhận đủ hàng", font8bold));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "(Ký tên - Họ và tên)", font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "Xác nhận khách hàng", font8bold));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( " ", font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			p1 = new Paragraph();
			p1.add(new Phrase( "(Ký tên - Họ và tên)", font8normal));
			cell = new PdfPCell(p1);
			cell.setPadding(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			table.addCell(cell);
			
			
			document.add(table);
			document.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			db.shutDown();
		}
		db.shutDown();
		
	}
	

}

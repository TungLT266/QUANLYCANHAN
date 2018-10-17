package geso.traphaco.distributor.servlets.donhang;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.servlets.reports.TinhHinhThucHienCTNPPSvl;
import geso.traphaco.distributor.beans.donhang.IDonhang;
import geso.traphaco.distributor.beans.donhang.ISanpham;
import geso.traphaco.distributor.beans.donhang.imp.Donhang;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class InBangKepdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public InBangKepdfSvl() {
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
		}else{
			IDonhang dhBean;
			dbutils db;
		
			Utility util=new Utility();
			String querystring=request.getQueryString();
			String dhid=util.getId(querystring);
			System.out.println(dhid);
			userId=util.getUserId(querystring);
		
			dhBean=new Donhang(dhid); 
			dhBean.setUserId(userId); //phai co UserId truoc khi Init
			dhBean.init();	
			dhBean.setKhId(dhBean.getKhId());
			List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList();
			List<ISanpham> spkmlist = (List<ISanpham>)dhBean.getScheme_SpList();
			Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); 
			Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); 
			db = new dbutils();
		

			//Đổi trạng thái Đã in đơn hàng
			String msg = "";
			msg = DoiTTDH(db,dhBean);
			dhBean.setMessage(msg);
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition"," inline; filename=HoaDonGTGT.pdf");
		
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			this.CreatePxk(document, outstream, splist,spkmlist,scheme_tongtien,scheme_chietkhau, dhBean, db);
			db.shutDown();
			splist.clear();
			spkmlist.clear();
			scheme_tongtien.clear();
			scheme_chietkhau.clear();
			dhBean.DBclose();
			util=null;
			
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
		}else{
			IDonhang dhBean;
			dbutils db;
			userId = cutil.antiSQLInspection(request.getParameter("userId"));
			String id=cutil.antiSQLInspection(request.getParameter("id"));
		
			dhBean=new Donhang(id);
			dhBean.setUserId(userId); //phai co UserId truoc khi Init
			dhBean.init();
			dhBean.setKhId(dhBean.getKhId());
	
			//dhBean.CreateSpDisplay();
		
			List<ISanpham> splist = (List<ISanpham>)dhBean.getSpList();
			List<ISanpham> spkmlist = (List<ISanpham>)dhBean.getScheme_SpList();
			Hashtable<String, Float> scheme_tongtien = dhBean.getScheme_Tongtien(); //khuyen mai tien
			Hashtable<String, Float> scheme_chietkhau = dhBean.getScheme_Chietkhau(); //khuyen mai chiet khau
			db = new dbutils();
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition"," inline; filename=HoaDonGTGT.pdf");
		
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			this.CreatePxk(document, outstream, splist,spkmlist,scheme_tongtien,scheme_chietkhau, dhBean, db);
			db.shutDown();
			splist.clear();
			spkmlist.clear();
			scheme_tongtien.clear();
			scheme_chietkhau.clear();
			dhBean.DBclose();
			cutil=null;
			
		}

	}
	private String DoiTTDH(dbutils db, IDonhang dhBean) 
	{
		String msg= "";
		
		try
		{
			
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);
			String sql = "update DONHANG set DAIN = '1' where pk_seq = "+ dhBean.getId() +" ";
			if(!db.update(sql))
			{
				msg = "Không thể cập nhật trạng thái cho đơn hàng";
				db.getConnection().rollback();
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch (Exception e) 
		{			
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) { }
			e.printStackTrace();
			msg = "Lỗi xảy ra trong quá trình cập nhật trạng thái"+e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return msg;
	}

	private void CreatePxk(Document document, ServletOutputStream outstream, List<ISanpham> spList, List<ISanpham> spkmlist,Hashtable<String, Float> scheme_tongtien,Hashtable<String, Float> scheme_chietkhau, IDonhang dhBean, dbutils db) throws IOException
	{
		try
		{		
			db = new dbutils();
			
			String tennpp="";
			String diachi="";
			String dienthoai="";
			String masothue="";
			
			String sql_getinfodis="select ten,isnull(diachi,'Chua xac dinh') as diachi,isnull( masothue,'Chua xac dinh') as masothue ,isnull( dienthoai,'Chua xac dinh') as dienthoai from nhaphanphoi where pk_seq='"+dhBean.getNppId()+"'";
			ResultSet rs=db.get(sql_getinfodis);
			try{
				if(rs.next()){
					tennpp=rs.getString("ten");
					diachi=rs.getString("diachi");
					dienthoai=rs.getString("dienthoai");
					masothue=rs.getString("masothue");
				}
				rs.close();
			}catch(Exception er){
				
			}
			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter1 = new DecimalFormat("#,###,###.####"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//lay doi tuong khach hang
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font fontnomar=new Font(bf, 9,Font.NORMAL);
			//font2.setColor(BaseColor.GREEN);
			 //KHAI BAO 1 BANG CO BAO NHIEU COT
			
			PdfPTable tableheader=new PdfPTable(3);
			tableheader.setWidthPercentage(100);
			tableheader.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withsheader = {30.0f,50.0f,30.0f};
			tableheader.setWidths(withsheader); 
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("path")+"/logomerap.gif");	
			hinhanh.scalePercent(70);
			hinhanh.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cellslogo = new PdfPCell(hinhanh);
			
			cellslogo.setPadding(10);
			
			tableheader.addCell(cellslogo);
			
			Paragraph pxk = new Paragraph("CHI TIẾT ĐƠN HÀNG BÁN", font);
			pxk.setSpacingAfter(10);
			pxk.setAlignment(Element.ALIGN_CENTER);
			
			PdfPCell celltieude=new PdfPCell();
			celltieude.addElement(pxk);
			Paragraph dvbh = new Paragraph("Đơn vị bán hàng : "+ tennpp+"\n "+"Số ĐT "+dienthoai , fontnomar);
			dvbh.setSpacingAfter(3);
			dvbh.setAlignment(Element.ALIGN_CENTER);
			celltieude.addElement(dvbh);
			
			tableheader.addCell(celltieude);
			
			PdfPCell cellinfo = new PdfPCell();
			
			cellinfo.addElement(new Paragraph("Số chứng từ     : "+dhBean.getId(),fontnomar));
						 
			String ngay=dhBean.getNgaygiaodich().substring(8,10)+"-" +dhBean.getNgaygiaodich().substring(5,7)+"-"+dhBean.getNgaygiaodich().substring(0,4);
			cellinfo.addElement(new Paragraph("Ngày chứng từ : " + ngay,fontnomar));
			tableheader.addCell(cellinfo);
						
			document.add(tableheader);						
			
			PdfPTable table_info=new PdfPTable(1);
			float[] with3 = {50.0f};//SET DO RONG CAC COT
			table_info.setWidthPercentage(100);
			table_info.setWidths(with3);
			PdfPCell cell111= new PdfPCell();
			
			String tenkh="";
			String diachikh="";
			String dienthoaikh="";
			String masothuekh="";
			String maFAST = "";
			
			String sql_getinfokh = "";
			 sql_getinfokh = "select ten,isnull(dienthoai,'Chua xac dinh') as dienthoai,isnull(diachi,'Chua xac dinh') as diachi,isnull(masothue,'Chua xac dinh') as masothue, maFAST from khachhang where pk_seq=" +dhBean.getKhId();
				try{
			ResultSet rs_kh  = db.get(sql_getinfokh);
			
		
	
				if(rs_kh!=null){
						
					while(rs_kh.next()){
						tenkh= rs_kh.getString("ten");
						diachikh= rs_kh.getString("diachi");
						dienthoaikh= rs_kh.getString("dienthoai");
						masothuekh= rs_kh.getString("masothue");
						maFAST = rs_kh.getString("maFAST");
					}
				}
			
			}catch(Exception er ){
				er.printStackTrace();
			
			}
			//lay dai dien kinh doanh
			String ddkdten="";
			String ddkddt= "";
			String sql_getddkd= "select pk_seq,ten, dienthoai from daidienkinhdoanh where pk_seq="+dhBean.getDdkdId();
			ResultSet rs_getddkd=db.get(sql_getddkd);
			System.out.println(sql_getddkd);
			try{
				if(rs_getddkd!=null){
				
					while(rs_getddkd.next()){
						ddkdten=rs_getddkd.getString("ten");
						ddkddt= rs_getddkd.getString("dienthoai");
					}
				}
			}catch(Exception er ){
				er.printStackTrace();
			}
			
			
			cell111.addElement(new Paragraph("Nhân viên bán hàng : "+ ddkdten  + "      Điện thoại : " +  ddkddt,fontnomar));
			
			cell111.addElement(new Paragraph("Mã khách hàng : " + maFAST + "   Tên khách hàng : "+ tenkh ,fontnomar));
			cell111.addElement(new Paragraph("Địa chỉ : " + diachikh ,fontnomar));

			cell111.setPaddingBottom(10);
			table_info.addCell(cell111);
			
			
			document.add(table_info);
			//Table Content
			PdfPTable table = new PdfPTable(11);//Chu y nhe 6 cot o day, thi xuong duoi nho set withs la 6 cot
			table.setWidthPercentage(100);//chieu dai cua báº£ng 
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs = {5.0f, 27.0f, 11.0f, 10.0f, 10.0f, 8.0f, 17.0f, 6.0f, 6.0f, 13.0f, 13.0f}; 	
			System.out.println("0");
	        table.setWidths(withs);
			String[] th = new String[]{"STT", "Tên hàng", "Số lượng", "Số lô", "Ngày hết hạn", "ĐVT", "Đơn giá", "VAT", "CK", "Tiền VAT", "Thành tiền"};
			PdfPCell[] cell = new PdfPCell[11];
			for(int i=0; i < 11 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setBackgroundColor(BaseColor.LIGHT_GRAY);		
				table.addCell(cell[i]);			
			}
			
			float size= 9.0f;
			Font font4 = new Font(bf, size );
			PdfPCell cells_detail = new PdfPCell();
			double totalle=0;
			for(int i = 0; i < spList.size(); i++)
			{
				ISanpham sanpham = (ISanpham)spList.get(i);
				double dongia=Double.parseDouble(sanpham.getDongia());
				
				double tienvat = Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia()) /100 * Double.parseDouble(sanpham.getKhoNVBH());

				String[] arr = new String[]{Integer.toString(i+1), sanpham.getTensanpham(),sanpham.getSoluong(), sanpham.getSOLo(), sanpham.getNgayHetHan(), sanpham.getDonvitinh() ,formatter1.format(dongia),
						formatter1.format(Double.parseDouble(sanpham.getKhoNVBH())), "0", formatter.format( tienvat ), formatter.format(Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia()) + tienvat )};

				totalle = totalle + Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia()) + tienvat;
				for(int j=0; j < 11; j++)
				{
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if(j==1){
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
					
					} 
					else if(j <= 4 && j != 1) {
						cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					else
						cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
					
					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					//cells_detail.setBorderWidthBottom(0);
					//cells_detail.setBorderWidthTop(0);
					table.addCell(cells_detail);
					
				}
				
				
				
			}	
			if(spkmlist.size()>0){
				for(int k= 0; k < spkmlist.size(); k++)
				{
					PdfPCell cellkhuyenmai = new PdfPCell();
					cellkhuyenmai.setHorizontalAlignment(Element.ALIGN_CENTER);
					Paragraph km1=new Paragraph("Hàng khuyến mại", font2);
					km1.setAlignment(Element.ALIGN_LEFT);
					cellkhuyenmai.addElement(km1);
					cellkhuyenmai.setColspan(11);
					table.addCell(cellkhuyenmai);
					
					
				}
				int demtt=0;
				for(int i = 0; i < spkmlist.size(); i++)
				{
					ISanpham sanpham = (ISanpham)spkmlist.get(i);
					double dongia=Double.parseDouble(sanpham.getDongia());
					
					double chietkhau=Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia()) /100 * Double.parseDouble(sanpham.getChietkhau());
					
					demtt=demtt+1;
					String[] arr = new String[]{"" + demtt, sanpham.getTensanpham() ,sanpham.getSoluong(), sanpham.getSOLo(), sanpham.getNgayHetHan(), sanpham.getDonvitinh(),formatter1.format(dongia),
							formatter1.format(Double.parseDouble(sanpham.getChietkhau())),formatter.format(chietkhau),formatter.format(Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia())-chietkhau ),"-"};
					
					for(int j=0; j < 11; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==1){
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						
						} 
						else if(j <= 4 && j != 1) {
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else
							cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
						
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);
						
					}
				}
				
				
			}

		
			/*//hang khuyen mai
			int demtt=0;
			for(int i = 0; i < spkmlist.size(); i++)
			{
				ISanpham sanpham = (ISanpham)spkmlist.get(i);
				double dongia=Double.parseDouble(sanpham.getDongia());
				
				double chietkhau=Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia()) /100 * Double.parseDouble(sanpham.getChietkhau());
				String sql_getkho="select kho_fk,kho.ten  from ctkhuyenmai inner join kho on  kho.pk_Seq=kho_fk  where scheme ='"+ sanpham.getCTKM()+"'";
				//System.out.println(sql_getkho);
				
				rs = db.get(sql_getkho);
				String TenKho="Khuyến mại";
				try{
					if(rs.next())
					{
						TenKho=rs.getString("ten");
					}
					rs.close();
				}catch(Exception er){
					System.out.println("Loi tai day :"+er.toString());
				}
				demtt=demtt+1;
				String[] arr = new String[]{""+demtt,sanpham.getMasanpham(), sanpham.getTensanpham(),TenKho ,sanpham.getSoluong(),sanpham.getDonvitinh(),formatter1.format(dongia),formatter1.format(Double.parseDouble(sanpham.getChietkhau())),formatter.format(chietkhau),formatter.format(Double.parseDouble(sanpham.getSoluong())* Double.parseDouble(sanpham.getDongia())-chietkhau )};
				
				for(int j=0; j < 10; j++)
				{
					cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
					if(j==2){
						cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
					}else{
						cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					
					cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cells_detail);
					
				}
			}	*/
			
			/*//Khai  bao 1 bien luu tien khuyen mai
			float tongtienkhuyenmai=0;
			// Xuat ra khuyen mai tra tien
			if(scheme_tongtien.size() > 0)
			{
				Enumeration<String> keys = scheme_tongtien.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					tongtienkhuyenmai=tongtienkhuyenmai+scheme_tongtien.get(key);
					String tongtien= Float.toString(scheme_tongtien.get(key));
					String sql_tenschem="select diengiai from ctkhuyenmai where scheme='"+key+"'";
					rs	=	db.get(sql_tenschem);
					String Tenschem="";
					try{
						if(rs.next()){					
							Tenschem=rs.getString("diengiai");						
						}
						rs.close();
					}catch(Exception er){
						System.out.println("Loi tai day :"+er.toString());
					}
					demtt=demtt+1;
					String[] arr = new String[]{""+demtt,key,Tenschem, "", "" ,"" ,"","","","-"+tongtien };
					for(int j=0; j < 10; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2){
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}else{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);
						
					}
				}
				
			}

			if(scheme_chietkhau.size() > 0)
			{
				
				Enumeration<String> keys = scheme_chietkhau.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					String sql_tenschem="select diengiai from ctkhuyenmai where scheme='"+key+"'";
					rs	=	db.get(sql_tenschem);
					String Tenschem="";
					try{
						if(rs.next()){						
							Tenschem=rs.getString("diengiai");					
						}
						rs.close();
					}catch(Exception er){
						System.out.println("Loi tai day :"+er.toString());
					}
					
					tongtienkhuyenmai=tongtienkhuyenmai+scheme_chietkhau.get(key);
					String tienchietkhau= Float.toString(scheme_chietkhau.get(key));
					demtt=demtt+1;
					String[] arr = new String[]{""+demtt,key,Tenschem, "", "" ,"" ,"","","-"+tienchietkhau,"" };
					for(int j=0; j < 10; j++)
					{
						cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
						if(j==2){
							cells_detail.setHorizontalAlignment(Element.ALIGN_LEFT);
						}else{
							cells_detail.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);

						table.addCell(cells_detail);
						
					}
				}
				
			}*/
			
			//CHIET KHAU
			double tongtienCK = 0;
			String[] tichluy_scheme = dhBean.getTichLuy_Scheme();
			String[] tichluy_vat = dhBean.getTichLuy_VAT();
			String[] tichluy_tongtien = dhBean.getTichLuy_Tongtien();
			
			if(tichluy_scheme != null)
			{
				for(int i = 0; i < tichluy_scheme.length; i++)
				{
					double _vat = Double.parseDouble(tichluy_vat[i].replaceAll(",", ""));
					double _chietkhau = Double.parseDouble(tichluy_tongtien[i].replaceAll(",", ""));
					
					double _tienthue = _vat * _chietkhau / 100;
					double _thanhtien = _chietkhau + _tienthue;
					
					tongtienCK += _thanhtien;
					String[] arr = new String[]{ tichluy_scheme[i], Double.toString(_vat), formatter.format(_chietkhau), formatter.format(_tienthue), formatter.format(_thanhtien) };
					for(int j = 0; j < arr.length; j++)
					{
						if(j == 0)
						{
							cells_detail = new PdfPCell(new Paragraph(arr[j], font2));
							cells_detail.setColspan(7);
							cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						else
						{
							cells_detail = new PdfPCell(new Paragraph(arr[j], font4));
							cells_detail.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						
						cells_detail.setVerticalAlignment(Element.ALIGN_MIDDLE);
						table.addCell(cells_detail);
						
					}
				}
			}
			
			
			PdfPCell cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph km=new Paragraph("Cộng tiền hàng ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(10);
			table.addCell(cell11);
			
			PdfPCell cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			Paragraph tongtien =new Paragraph( formatter.format(totalle), fontnomar);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			
			
			// tong tien khuyen mai
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			km=new Paragraph("Tổng tiền chiết khấu ", font2);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(10);
			table.addCell(cell11);
			//
			
			tongtienCK = Math.abs(tongtienCK);
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien =new Paragraph(formatter1.format(tongtienCK) , font2);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			//
			cell11 = new PdfPCell();
			cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
			km=new Paragraph("Tổng tiền thanh toán(đã làm tròn) ", fontnomar);
			km.setAlignment(Element.ALIGN_RIGHT);
			cell11.addElement(km);
			cell11.setColspan(10);
			table.addCell(cell11);
			//
			//double tongtiensauthue=totalle/100 *10 +totalle;			
			cell12 = new PdfPCell();
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			tongtien =new Paragraph(formatter.format(totalle - tongtienCK) , fontnomar);
			tongtien.setAlignment(Element.ALIGN_RIGHT);
			cell12.addElement(tongtien);
			table.addCell(cell12);
			
			//doc tien bang chu
			doctienrachu doctien=new doctienrachu();
			 cell11 = new PdfPCell();
			 cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			 //Long tongtienst= Math.round(tongtiensauthue-tongtienkhuyenmai);
			 Long tongtienst= Math.round(totalle - tongtienCK);
			km=new Paragraph("Số tiền bằng chữ : " + doctien.tranlate(tongtienst+""),  font2);
			km.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(km);
			cell11.setColspan(10);
			table.addCell(cell11);
			
			document.add(table);
			
		
			PdfPTable tablefooter=new PdfPTable(4);
			tablefooter.setWidthPercentage(100);//chieu dai cua báº£ng 
			tablefooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] withfooterder = {35.0f,35.0f,35.0f,35.0f};//SET DO RONG CAC COT
			tablefooter.setWidths(withfooterder); 
			
			//nguoimua hang 
			Paragraph para = new Paragraph("Người lập phiếu", fontnomar);
			
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);
			//o giua
			 para = new Paragraph("Thủ kho", fontnomar);
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setBorderWidthRight(0);
			tablefooter.addCell(para);
			//Káº¿ toÃ¡n
				para = new Paragraph("Nhân viên giao nhận", fontnomar);
				para.setAlignment(Element.ALIGN_CENTER);
				cell11.addElement(para);
				cell11.setBorderWidthLeft(0);
				cell11.setBorderWidthRight(0);
				cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
				tablefooter.addCell(para);
			//nguoi nhan hang
			para = new Paragraph("Khách hàng", fontnomar);
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			para.add("\n");
			 
			para.setAlignment(Element.ALIGN_CENTER);
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.addElement(para);
			cell11.setBorderWidthLeft(0);
			cell11.setBorder(0);
			
			tablefooter.addCell(para);
			
			document.add(tablefooter);
			
			
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}
		db.shutDown();
		
	}

}

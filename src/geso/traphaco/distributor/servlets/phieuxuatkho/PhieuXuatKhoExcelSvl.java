package geso.traphaco.distributor.servlets.phieuxuatkho;


import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.donhang.ISanpham;
import geso.traphaco.distributor.beans.donhang.imp.Sanpham;
import geso.traphaco.distributor.beans.phieuxuatkho.IPhieuxuatkho;
import geso.traphaco.distributor.beans.phieuxuatkho.imp.Phieuxuatkho;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
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

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PaperSize;
import org.apache.poi.ss.usermodel.PrintOrientation;
import org.apache.poi.ss.util.CellRangeAddress;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@WebServlet("/PhieuXuatKhoExcelSvl")
public class PhieuXuatKhoExcelSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public PhieuXuatKhoExcelSvl()
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
		}else{
		
		IPhieuxuatkho pxkBean;
		dbutils db=null;

		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		
		if(querystring != null)
		{
			userId = util.getUserId(querystring);
		    
		    if (userId.length() == 0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    if(querystring.indexOf("excel") > 0)
		    {
				String id = util.antiSQLInspection(request.getParameter("excel"));
				pxkBean = new Phieuxuatkho(id);
			    pxkBean.setUserId(userId);
			    
			    pxkBean.init2();
			    db = new dbutils();
			    List<ISanpham> sanphamList = this.createPxk_SpList(id, db);
				List<ISanpham> sanphamKMList = this.createPxk_SpkmList(id, db);
				List<ISanpham> tienKMList = this.createPxk_TienkmList(id, db);
				XuatFileExcel(response,sanphamList,sanphamKMList,tienKMList,pxkBean);
				pxkBean.DBclose();
				pxkBean=null;
				sanphamList=null;
				sanphamKMList=null;
				tienKMList=null;
				util=null;
		    }
		}
	}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}
	private String XuatFileExcel(HttpServletResponse response,List<ISanpham> spList, List<ISanpham> sanphamKMList, List<ISanpham> tienKMList, IPhieuxuatkho pxkBean) throws IOException
	{
		NumberFormat formatter = new DecimalFormat("#,###,###");
		try 
		{
		   response.setContentType("application/vnd.ms-excel");
		   response.setHeader("Content-Disposition", "attachment; filename=PhieuXuatKho_" +getDateTime()+".xlsx");
		   dbutils db=new dbutils();
			   XSSFWorkbook workbook = new XSSFWorkbook();
			   
			  
			   XSSFSheet sheet=null;
			  
			   
			   Font f = workbook.createFont();
			   f.setFontHeightInPoints((short) 12);
			   f.setColor( (short)Font.COLOR_NORMAL );
			   f.setBoldweight(Font.BOLDWEIGHT_BOLD);
			   
			   Font f2 = workbook.createFont();
			   f2.setFontHeightInPoints((short) 12);
			   f2.setColor( (short)Font.COLOR_NORMAL );
			   f2.setBoldweight(Font.BOLDWEIGHT_BOLD);
			   
			   Font f3 = workbook.createFont();
			   f.setFontHeightInPoints((short) 12);
			   f.setColor( (short)Font.COLOR_NORMAL );
			   f.setBoldweight(Font.COLOR_NORMAL);
			   
			   		   
			   
			   CellStyle cs = workbook.createCellStyle();
			   cs.setFont(f);
			   cs.setBorderBottom((short) 1);
			   cs.setBorderLeft((short) 1);
			   cs.setBorderRight((short) 1);
			   cs.setBorderTop((short) 1);
			   cs.setAlignment(CellStyle.ALIGN_CENTER);
			   cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			   
			
			   CellStyle cs2 = workbook.createCellStyle();
			   cs2.setBorderBottom((short) 1);
			   cs2.setBorderLeft((short) 1);
			   cs2.setBorderRight((short) 1);
			   cs2.setBorderTop((short) 1);
			   cs2.setFillPattern((short) CellStyle.NO_FILL);
			   cs2.setFont(f2);
			   cs2.setAlignment(CellStyle.ALIGN_CENTER);
			   cs2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
               
			   
			   
			   CellStyle cs3 = workbook.createCellStyle();
			   cs3.setBorderBottom((short) 1);
			   cs3.setBorderLeft((short) 1);
			   cs3.setBorderRight((short) 1);
			   cs3.setBorderTop((short) 1);
			   cs3.setFont(f3);
			   Cell cell;
			   
			   sheet = workbook.createSheet("PhieuXuatKho");
			   sheet.getPrintSetup().setPaperSize(PaperSize.A4_PAPER);
			   sheet.getPrintSetup().setOrientation(PrintOrientation.DEFAULT);
			   
			   
			   sheet.createRow(0);
			   sheet.createRow(1);
			   sheet.createRow(2);
			   sheet.createRow(3);
			   sheet.createRow(4);
			   sheet.createRow(5);
			   sheet.createRow(6);
			   

			   cell=sheet.getRow(0).createCell(1);cell.setCellStyle(cs2);cell.setCellValue("PHIẾU XUẤT KHO");
			   sheet.addMergedRegion(CellRangeAddress.valueOf("B1:G1"));			   
			   
			   cell=sheet.getRow(1).createCell(1);cell.setCellStyle(cs);
			   cell.setCellValue("Ngày lập phiếu :"+pxkBean.getNgaylap() + "  Số phiếu : "+pxkBean.getId()+"");
			   sheet.addMergedRegion(CellRangeAddress.valueOf("B2:G2"));
			   
			   
			   cell=sheet.getRow(2).createCell(1);cell.setCellStyle(cs);cell.setCellValue("NVGN :"+pxkBean.getNvgnTen());
			   sheet.addMergedRegion(CellRangeAddress.valueOf("B3:G3"));
			   
			   cell=sheet.getRow(3).createCell(1);cell.setCellStyle(cs);cell.setCellValue("NPP :"+pxkBean.getNppTen());
			   sheet.addMergedRegion(CellRangeAddress.valueOf("B4:G4"));
			   
			   cell=sheet.getRow(5).createCell(1);cell.setCellStyle(cs2);cell.setCellValue("Hàng Bán");
			   sheet.addMergedRegion(CellRangeAddress.valueOf("B6:G6"));
			   
			   cell=sheet.getRow(6).createCell(1);cell.setCellStyle(cs2);cell.setCellValue("STT");
			   cell=sheet.getRow(6).createCell(2);cell.setCellStyle(cs2);cell.setCellValue("Tên sản phẩm");
			   cell=sheet.getRow(6).createCell(3);cell.setCellStyle(cs2);cell.setCellValue("Thùng");
			   cell=sheet.getRow(6).createCell(4);cell.setCellStyle(cs2);cell.setCellValue("Lẻ");
			   
			   int rowIndex=7;
			   if(spList.size() > 0)
		        {
					int soluongthung = 0;
					int soluongle = 0;
					for(int i = 0; i < spList.size(); i++)
					{
						Sanpham sanpham = (Sanpham)spList.get(i);
						int quicach=Integer.parseInt(sanpham.getQuicach().replaceAll(" ", ""));
						int sl=Integer.parseInt(sanpham.getSoluong().replaceAll(" ", ""));
						String  thung="";
						String le="";
						if(quicach!=0)
						{
							int thg = sl / quicach;
							if(thg > 0)
								thung = Integer.toString(thg);
							int sole = sl % quicach;
							if(sole > 0)
								le = Integer.toString(sole);
							
						}
						sheet.createRow(rowIndex);
						 cell=sheet.getRow(rowIndex).createCell(1);cell.setCellStyle(cs);cell.setCellValue( Integer.toString(i+1)   );
						 cell=sheet.getRow(rowIndex).createCell(2);cell.setCellStyle(cs);cell.setCellValue( sanpham.getTensanpham()  );
						 cell=sheet.getRow(rowIndex).createCell(3);cell.setCellStyle(cs);cell.setCellValue( thung );
						 cell=sheet.getRow(rowIndex).createCell(4);cell.setCellStyle(cs);cell.setCellValue( le );
						soluongle+= Integer.parseInt(le.length()<=0?"0":le);
						soluongthung+=Integer.parseInt(thung.length()<=0?"0":thung);
						rowIndex++;
					}	
					sheet.createRow(rowIndex);
					cell=sheet.getRow(rowIndex).createCell(1);cell.setCellStyle(cs);cell.setCellValue("Tổng cộng  " );
					int col=rowIndex+1;
					sheet.addMergedRegion(CellRangeAddress.valueOf("B"+col+":C"+col+""));
					cell=sheet.getRow(rowIndex).createCell(3);cell.setCellStyle(cs);cell.setCellValue(soluongthung );
					cell=sheet.getRow(rowIndex).createCell(4);cell.setCellStyle(cs);cell.setCellValue(soluongle );
					rowIndex++;
		        }
			   sheet.createRow(rowIndex);
			  
			   cell=sheet.getRow(rowIndex).createCell(1);cell.setCellStyle(cs2);cell.setCellValue("Hàng Khuyến Mại");
			   int col=rowIndex+1;
				sheet.addMergedRegion(CellRangeAddress.valueOf("B"+col+":C"+col+""));
				
			   rowIndex++;
			   sheet.createRow(rowIndex);
			   cell=sheet.getRow(rowIndex).createCell(1);cell.setCellStyle(cs2);cell.setCellValue("STT");
			   cell=sheet.getRow(rowIndex).createCell(2);cell.setCellStyle(cs2);cell.setCellValue("Tên sản phẩm");
			   cell=sheet.getRow(rowIndex).createCell(3);cell.setCellStyle(cs2);cell.setCellValue("Thùng");
			   cell=sheet.getRow(rowIndex).createCell(4);cell.setCellStyle(cs2);cell.setCellValue("Lẻ");
			   
			   rowIndex++;
			   int m = 0;
			   if(sanphamKMList.size()>0)
			   {
				   int soluong = 0;
				   int soluongthung = 0;
				   int soluongle = 0;
				while(m < sanphamKMList.size())
				{
					Sanpham sanpham = (Sanpham)sanphamKMList.get(m);
					String masanpham=sanpham.getMasanpham();
					int quicach=Integer.parseInt(sanpham.getQuicach().replaceAll(" ", ""));
					int sl=Integer.parseInt(sanpham.getSoluong().replaceAll(" ", ""));
					String  thung="";
					String le="";
					if(quicach!=0)
					{
						int thg = sl / quicach;
						if(thg > 0)
							thung = Integer.toString(thg);
						int sole = sl % quicach;
						if(sole > 0)
							le = Integer.toString(sole);
					}
					sheet.createRow(rowIndex);
					sheet.setColumnWidth(0, 10);
					
					 cell=sheet.getRow(rowIndex).createCell(1);cell.setCellStyle(cs);cell.setCellValue( Integer.toString(m+1)   );
					 cell=sheet.getRow(rowIndex).createCell(2);cell.setCellStyle(cs);cell.setCellValue( sanpham.getTensanpham()  );
					 cell=sheet.getRow(rowIndex).createCell(3);cell.setCellStyle(cs);cell.setCellValue( thung );
					 cell=sheet.getRow(rowIndex).createCell(4);cell.setCellStyle(cs);cell.setCellValue( le );
					
					soluong += Integer.parseInt(sanpham.getSoluong());
					soluongle+= Integer.parseInt(le.length()<=0?"0":le);
					soluongthung+=Integer.parseInt(thung.length()<=0?"0":thung);
					rowIndex++;
					
					m++;
				}
				sheet.createRow(rowIndex);
				cell=sheet.getRow(rowIndex).createCell(1);cell.setCellStyle(cs);cell.setCellValue("Tổng cộng  " );
				col=rowIndex+1;
				sheet.addMergedRegion(CellRangeAddress.valueOf("B"+col+":C"+col+""));
				cell=sheet.getRow(rowIndex).createCell(3);cell.setCellStyle(cs);cell.setCellValue(soluongthung );
				cell=sheet.getRow(rowIndex).createCell(4);cell.setCellStyle(cs);cell.setCellValue(soluongle );
				rowIndex++;
			   }
				
			
				sheet.createRow(rowIndex);
				 cell=sheet.getRow(rowIndex).createCell(1);cell.setCellStyle(cs2);cell.setCellValue("Khách hàng");
				 col=rowIndex+1;
				 sheet.addMergedRegion(CellRangeAddress.valueOf("B"+col+":C"+col+""));
				 rowIndex++;
				ResultSet rs = pxkBean.getDhIdsList();
				Hashtable<String, Long> credit = pxkBean.getCredits();
				
				sheet.createRow(rowIndex);
				 cell=sheet.getRow(rowIndex).createCell(1);cell.setCellStyle(cs2);cell.setCellValue("Mã");
				 cell=sheet.getRow(rowIndex).createCell(2);cell.setCellStyle(cs2);cell.setCellValue("Tên khách hàng");
				 cell=sheet.getRow(rowIndex).createCell(3);cell.setCellStyle(cs2);cell.setCellValue("Hóa đơn");
				 cell=sheet.getRow(rowIndex).createCell(4);cell.setCellStyle(cs2);cell.setCellValue("Số tiền");
				 cell=sheet.getRow(rowIndex).createCell(5);cell.setCellStyle(cs2);cell.setCellValue("Nợ cũ");
				 rowIndex++;
				if (rs != null)
				{
					try
					{
						int i = 1;
						float total = 0;
						long nocu=0;
						while(rs.next())
						{								
							sheet.createRow(rowIndex);
							 cell=sheet.getRow(rowIndex).createCell(1);cell.setCellStyle(cs);cell.setCellValue(rs.getString("smartid")  );
							 cell=sheet.getRow(rowIndex).createCell(2);cell.setCellStyle(cs);cell.setCellValue(rs.getString("khTen")+ " - " + rs.getString("dc") );
							 cell=sheet.getRow(rowIndex).createCell(3);cell.setCellStyle(cs);cell.setCellValue(rs.getString("dhId") );
							 float tonggtri = 0;
								if(rs.getString("tonggiatri") != null)
								{
									tonggtri = Float.parseFloat(rs.getString("tonggiatri"));
								}
								total += tonggtri;
							 cell=sheet.getRow(rowIndex).createCell(4);cell.setCellStyle(cs);cell.setCellValue(formatter.format(tonggtri));
							
							if(rs.getString("khId") != null)
							{
								if(credit.containsKey(rs.getString("khId")))
								{
									nocu = nocu + (Long)credit.get(rs.getString("khId")).longValue();
									cell=sheet.getRow(rowIndex).createCell(5);cell.setCellStyle(cs);cell.setCellValue(formatter.format((Long)credit.get(rs.getString("khId")).longValue()));
								}
								else
								{
									cell=sheet.getRow(rowIndex).createCell(5);cell.setCellStyle(cs);cell.setCellValue("");
								}
							}
							rowIndex++;
							i++;
						}
						sheet.createRow(rowIndex);
						cell=sheet.getRow(rowIndex).createCell(1);cell.setCellStyle(cs);cell.setCellValue("Tổng cộng");
						col=rowIndex+1;
						sheet.addMergedRegion(CellRangeAddress.valueOf("B"+col+":C"+col+""));
						cell=sheet.getRow(rowIndex).createCell(3);cell.setCellStyle(cs);cell.setCellValue(Integer.toString(i-1));
						cell=sheet.getRow(rowIndex).createCell(4);cell.setCellStyle(cs);cell.setCellValue(formatter.format(Math.round(total)).toString());
						cell=sheet.getRow(rowIndex).createCell(5);cell.setCellStyle(cs);cell.setCellValue(formatter.format(nocu).toString());
				
						rowIndex++;
					}
					
					
					catch(Exception e){}
				}
				 for(int columnIndex = 0; columnIndex < 10; columnIndex++) 
				 {
					   sheet.autoSizeColumn(columnIndex);
				}
				
				
			   OutputStream out = response.getOutputStream();
			   workbook.write(out);
			   out.close();
		   }
		  catch (Exception e) 
		  {
			  e.printStackTrace();
		  }
		return "";
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private List<ISanpham> createPxk_SpList(String pxkId, dbutils db)
	{
		List<ISanpham> pxk_splist = new ArrayList<ISanpham>();
		
		String query ="select a.sanpham_fk as spId, b.ma as spMa, sum(splo.soluong) as soluong, " +
				  "b.ten as spTen, c.ten as khoTen, d.ten as kbhTen,isnull(b.barcode,'') " +
				  "as barcode, soluong1 as quicach from " +
				  "phieuxuatkho_sanpham a " +
				  "inner join PHIEUXUATKHO_SANPHAM splo on a.SANPHAM_FK=splo.SANPHAM_FK and a.PXK_FK=splo.PXK_FK " +
				  "inner join sanpham b on a.sanpham_fk = b.pk_seq " +
				  "left join kho c on a.kho_fk = c.pk_seq left join kenhbanhang d on a.kbh_fk = d.pk_seq " +
				  "left join quycach q on q.sanpham_fk = a.sanpham_fk and q.dvdl2_fk=100018 " +
				  "left join donvidoluong  d2 on d2.pk_Seq = dvdl2_fk " +
				  "left join donvidoluong d1 on d1.pk_seq = dvdl1_fk where a.pxk_fk = "+pxkId +
				  " group by a.sanpham_fk, b.ma, b.ten , c.ten , d.ten, b.barcode, soluong1 ";
		System.out.print("\nQuery truy van du lieu la:.... " + query + "\n");
		
		ResultSet rsPxk_sp = db.get(query);
		if(rsPxk_sp != null)
		{
			String[] param = new String[10];
			ISanpham sp = null;	
			try 
			{
				while(rsPxk_sp.next())
				{
					  sp=new Sanpham();
					  sp.setMasanpham(rsPxk_sp.getString("spMa"));
					  sp.setTensanpham(rsPxk_sp.getString("spTen"));
					  sp.setSoluong(rsPxk_sp.getString("soluong"));
					  sp.setSPId(rsPxk_sp.getString("SPID"));
					  
					  //sp.setSolo(rsPxk_sp.getString("solo"));
					 // sp.setNgayHetHan(rsPxk_sp.getString("ngayhethan"));
					  sp.setQuicach(rsPxk_sp.getInt("quicach")+"");
					  sp.setBarcode(rsPxk_sp.getString("barcode"));
					  sp.setKhoTen(rsPxk_sp.getString("khoTen"));
					pxk_splist.add(sp);
				}
				rsPxk_sp.close();
			} 
			catch(Exception e) {System.out.println(e.toString());}
			finally{try {
				if(rsPxk_sp != null)
					rsPxk_sp.close();
			} catch (Exception e2) {
				System.out.println(e2.toString());
			}}
		}
		return pxk_splist;
	}
	
	private List<ISanpham> createPxk_SpkmList(String pxkId, dbutils db)
	{
		List<ISanpham> pxk_spkmlist = new ArrayList<ISanpham>();
		String query=
		" select c.ten as khoTen, d.ten as kbhTen, e.scheme + '-' + e.diengiai as ctkmTen   " +
		  "    , a.sanpham_fk as spId, isnull(b.barcode,'') as barcode  " +
		  "    ,soluong1 as quicach, b.ma as spMa, b.ten as spTen, sum(spkmlo.soluong) as soluong   " +
		  "     from phieuxuatkho_spkm a    " +
		  "     inner join PHIEUXUATKHO_SPKM_CHITIET spkmlo on spkmlo.SANPHAM_FK=a.SANPHAM_FK and spkmlo.PXK_FK=a.PXK_FK and spkmlo.scheme=a.scheme  " +
		  "     inner  join sanpham b   " +
		  "     on a.sanpham_fk = b.pk_seq inner join kho c on a.kho_fk = c.pk_seq inner join kenhbanhang d on a.kbh_fk = d.pk_seq   " +
		  "     inner join ctkhuyenmai e  on a.scheme = e.pk_seq   " +
		  "		left join quycach q on q.sanpham_fk = a.sanpham_fk and q.dvdl2_fk=100018  " +
		  "     left join donvidoluong  d2 on d2.pk_Seq = dvdl2_fk  left join donvidoluong d1 on d1.pk_seq = dvdl1_fk  " +
		  "     where a.pxk_fk ="+ pxkId +
		  " 	group by c.ten, d.ten, e.scheme + '-' + e.diengiai, a.sanpham_fk, b.barcode, soluong1, b.ma, b.ten ";
		System.out.println("Get San Pham Khuyen Mai : "+query);
		ResultSet rsPxk_spkm = db.get(query);
		if(rsPxk_spkm != null)
		{	
			try
			{
				while(rsPxk_spkm.next())
				{					
					ISanpham sp = null;
					sp=new Sanpham();
					sp.setMasanpham(rsPxk_spkm.getString("spMa"));
					  sp.setTensanpham(rsPxk_spkm.getString("spTen"));
					  sp.setSoluong(rsPxk_spkm.getString("soluong"));
					  sp.setSPId(rsPxk_spkm.getString("SPID"));
					  
					  //sp.setSolo(rsPxk_spkm.getString("solo"));
					 // sp.setNgayHetHan(rsPxk_spkm.getString("ngayhethan"));
					  sp.setQuicach(rsPxk_spkm.getInt("quicach")+"");
					  sp.setBarcode(rsPxk_spkm.getString("barcode"));
					  sp.setKhoTen(rsPxk_spkm.getString("khoTen"));
					  sp.setCTKM(rsPxk_spkm.getString("ctkmTen"));
					pxk_spkmlist.add(sp);
				}
				rsPxk_spkm.close();
			} 
			catch(Exception e) {}
			finally{try {
				if( rsPxk_spkm != null)
					rsPxk_spkm.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		return pxk_spkmlist;
	}
	
	private List<ISanpham> createPxk_TienkmList(String pxkId, dbutils db) 
	{
		List<ISanpham> pxk_tienkmlist = new ArrayList<ISanpham>();
		String query = "select b.scheme + '-' + b.diengiai as ctkmTen, sum(a.tonggiatri) as tonggiatri from phieuxuatkho_tienkm a inner join ctkhuyenmai b on a.scheme = b.pk_seq ";
		query += " where a.pxk_fk = '" + pxkId + "' group by b.scheme + '-' + b.diengiai";
		
		ResultSet rsPxk_spkm = db.get(query);
		if(rsPxk_spkm != null)
		{	
			try
			{
				while(rsPxk_spkm.next())
				{
					String[] param = new String[8];
					ISanpham sp = null;
					
					param[0] = "";					
					param[1] = "";		
					param[2] = "";
					param[3] = "";		
					param[4] = "";
					param[5] = "";
					
					//luu ten ctkm
					param[6] = "";
					if(rsPxk_spkm.getString("ctkmTen") != null)
						param[6] = rsPxk_spkm.getString("ctkmTen");
					
					param[7] = "";
					if(rsPxk_spkm.getString("tonggiatri") != null)
						param[7] = rsPxk_spkm.getString("tonggiatri");
					
					sp = new Sanpham(param);
					pxk_tienkmlist.add(sp);
				}
				rsPxk_spkm.close();
			} 
			catch(Exception e) {}
			finally{try {
				if(rsPxk_spkm != null)
					rsPxk_spkm.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		return pxk_tienkmlist;
	}
	
}


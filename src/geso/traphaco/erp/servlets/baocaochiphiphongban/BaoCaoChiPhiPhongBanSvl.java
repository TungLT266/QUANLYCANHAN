package geso.traphaco.erp.servlets.baocaochiphiphongban;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.VerticalAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.dms.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocaochiphiphongban.IBaoCaoChiPhiPhongBan;
import geso.traphaco.erp.beans.baocaochiphiphongban.imp.BaoCaoChiPhiPhongBan;


/**
 * Servlet implementation class BoBaoCaoSuDungTSCD
 */
@WebServlet("/BoBaoCaoSuDungTSCD")
public class BaoCaoChiPhiPhongBanSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BaoCaoChiPhiPhongBanSvl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utility Ult = new Utility();
		HttpSession session = request.getSession();
		session.setAttribute("util", Ult);
		String querystring = request.getQueryString();
		String userId = (String) session.getAttribute("userId");
		IBaoCaoChiPhiPhongBan obj = new BaoCaoChiPhiPhongBan();
		String ctyId =(String) session.getAttribute("congtyId");
		obj.setUserId(userId);
		obj.setCtyId(ctyId);
		String nextJSP = "/TraphacoHYERP/pages/Erp/BaoCaoChiPhiPhongBan.jsp";
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Utility util = new Utility();

		HttpSession session = request.getSession();

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility cutil = (Utility) session.getAttribute("util");

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		OutputStream out = response.getOutputStream();
		IBaoCaoChiPhiPhongBan obj = new BaoCaoChiPhiPhongBan();

		String ctyId = (String) session.getAttribute("congtyId");
		obj.setUserId(userId);
		obj.setCtyId(ctyId);
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		/*String dvthId = util.antiSQLInspection(request.getParameter("phongBanId"));
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);*/
		System.out.println("balabala");
		
		String[] phongBanIds = request.getParameterValues("phongBanId");
	    obj.setPhongBanIds(phongBanIds);
		

		String action = request.getParameter("action");

		String nextJSP = "/TraphacoHYERP/pages/Erp/BaoCaoChiPhiPhongBan.jsp";

		if (action.equals("exportExcel")) {
			try {
				response.setContentType("application/xlsm");

				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoChiPhiPhongBan.xlsm");
				CreateReport(out, obj);


			} catch (Exception ex) {
				obj.setMsg(ex.getMessage());
				session.setAttribute("obj", obj);
				response.sendRedirect(nextJSP);
				return;
			}
		}

		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		

	}

	private void CreateReport(OutputStream out, IBaoCaoChiPhiPhongBan obj) throws Exception {

		try {
			String file = getServletContext().getInitParameter("path") + "\\BaoCaoChiPhiPhongBan.xlsx";
			dbutils db = new dbutils();
			System.out.println(file);

			FileInputStream fstream = new FileInputStream(file);

			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			Worksheets worksheets = workbook.getWorksheets();

			//TỔNG CÔNG
			Worksheet worksheet = worksheets.getSheet("Sheet1");
			worksheet.setName("Tổng hợp");
			Cells cells = worksheet.getCells();

			Cell cell;
		

			Font font = new Font();
			font.setColor(Color.NAVY);
			font.setSize(13);
			font.setBold(true);

			cell = cells.getCell("A5");
			ReportAPI.getCellStyle(cell, Color.RED, false, 11,  "Từ ngày: " + obj.getTungay() +" đến ngày "+ obj.getDenngay());

			cell = cells.getCell("A6");
			ReportAPI.getCellStyle(cell,  Color.RED, false, 11, "Phòng ban: "+obj.getDvthTen());
			db.getConnection().setAutoCommit(false);
			obj.init();
			//------------------------------------------------------------------- XUẤT THÂN DỮ LIỆU
			String query_bophan =" select ROW_NUMBER() OVER(ORDER BY MA ASC) AS STT, MA "  
					+" \n from ( \n";
			query_bophan += obj.getQueryTH();
			query_bophan +=" )a \n group by  MA ";
			System.out.println("query_bophan : "+query_bophan);
			//ResultSet rstonghop = obj.getRsBaoCaoTongHop();
			ResultSet rstonghop=db.get(query_bophan);
			double tongtientamung = 0;
			double tongtienthanhtoan = 0;
			int stt=1;
			
			int countRow =  9;

			Color color_=Color.BLACK;
			while(rstonghop.next())
			{
				int dem=0;
				//Dem PB co1 bao nhieu6 Loại chứng từ
				/*String query_soPB="select isnull(count(PHONGBAN  ),0) as dem "
						+ " \n from  uft_REPORT_CHIPHI_PHONGBAN("+obj.getCtyId()+","+(obj.getDvthId().trim().length() >0?obj.getDvthId():"0")+",'"+obj.getTungay()+"','"+obj.getDenngay()+"')"
						+ " \n where PHONGBAN like N'%"+rstonghop.getString("PHONGBAN")+"%' "
						+ " \n group by  PHONGBAN ";*/
				String query_soPB="select isnull(count(a.MA  ),0) as dem "
						+ " \n from  (\n ";
				query_soPB += obj.getQueryTH();
				query_soPB	+= " \n ) a where a.MA like N'%"+rstonghop.getString("MA")+"%' "
						+ " \n group by  a.MA ";
				System.out.println("query_soPB : "+query_soPB);
				ResultSet rsSoPB=db.get(query_soPB);
				try {
					if(rsSoPB!=null)
					{ 
						while(rsSoPB.next())
						{
							dem=rsSoPB.getInt("dem");
						}
						rsSoPB.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}


				//1. Xuất STT

				for(int j=1; j<=dem;j++){
					cell = cells.getCell(countRow,0);
					int thu=countRow-1+j;
					ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 11, stt+"");
					setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);
					System.out.println("xuat:1 "+countRow+" "+thu  +" dd1 "+j+" stt "+stt);
					stt=stt+1;
					countRow++;
				}

				countRow=countRow-dem;


				//merge PB  -- 2. XUất Phân bổ
				cells.merge(countRow, 1, countRow+dem-1, 1);

				for (int j = 1; j < 2; j++) {
					cell = cells.getCell(countRow,j);
					System.out.println("xuat: "+countRow+" dd "+j);
					ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 11, rstonghop.getString("MA"));
					setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
				}



				/*String query_chitiet="select PHONGBAN,LOAICHUNGTU,TONGTIEN "
						+ " \n from  uft_REPORT_CHIPHI_PHONGBAN("+obj.getCtyId()+","+(obj.getDvthId().trim().length() >0?obj.getDvthId():"0")+",'"+obj.getTungay()+"','"+obj.getDenngay()+"')"
						+ " \n where PHONGBAN like N'%"+rstonghop.getString("PHONGBAN")+"%' ";*/
				String query_chitiet="select a.MA,a.LOAICHUNGTU,a.TONGTIEN "
						+ " \n from ( \n";
				query_chitiet += obj.getQueryTH();
				query_chitiet += " )a where a.MA like N'%"+rstonghop.getString("MA")+"%' ";
				System.out.println("query_chitiet : "+query_chitiet);
				ResultSet rsChitiet=db.get(query_chitiet);
				try {
					if(rsChitiet!=null)
					{ 
						while(rsChitiet.next())
						{
							//3.Xuất chi tiết
							for(int i = 1; i <= 2; i ++)
							{
								cell = cells.getCell(countRow,i+1);
								System.out.println("xuat: "+countRow+" dd "+i);
								if(i==1)
								{
									ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 11, rsChitiet.getString("LOAICHUNGTU"));
									
								}
								else
								{
									this.getCellStyle(cell, "Times New Roman", color_, false, 11, rsChitiet.getDouble("TONGTIEN"));
									if(rsChitiet.getString("LOAICHUNGTU").equals("Tạm ứng"))
									{
										tongtientamung += rsChitiet.getDouble("TONGTIEN");
									}
									if(rsChitiet.getString("LOAICHUNGTU").equals("Đề nghị thanh toán"))
									{
										tongtienthanhtoan += rsChitiet.getDouble("TONGTIEN");
									}
								}
								setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);

								
							}
							countRow++;
						}
						rsChitiet.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}


			if(rstonghop!=null)rstonghop.close();
			cells.merge(countRow, 0, countRow, 2);
			for (int j = 0; j < 3; j++) {
				cell = cells.getCell(countRow,j);
				ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 11, "Tổng tiền tạm ứng");
				setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
			}
			countRow=countRow+1;
			cell = cells.getCell("D"+countRow);
			ReportAPI.getCellStyle_double(cell, "Times New Roman", color_, false, 11,  tongtientamung);


			cells.merge(countRow, 0, countRow, 2);
			for (int j = 0; j < 3; j++) {
				cell = cells.getCell(countRow,j);
				ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 11, "Tổng tiền thanh toán");
				setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);	
			}

			countRow=countRow+1;
			cell = cells.getCell("D"+countRow);
			ReportAPI.getCellStyle_double(cell, "Times New Roman", color_, false, 11,  tongtienthanhtoan);

			//END TỔNG 
			//--------------------------------------------------------------------------------

				//SHEET 2
			
			//CHIT TIẾT
			
			
			
			Worksheet worksheet_sheets2 = worksheets.getSheet("Sheet2");
			worksheet_sheets2.setName("Chi tiết");
			Cells cells_sheets2 = worksheet_sheets2.getCells();

			Cell cell_sheets2;
		

			cell_sheets2 = cells_sheets2.getCell("A6");
				
			ReportAPI.getCellStyle(cell_sheets2, Color.RED, false, 11,  "Từ ngày: " + obj.getTungay() +" đến ngày "+ obj.getDenngay());

			cell_sheets2 = cells_sheets2.getCell("A7");
			ReportAPI.getCellStyle(cell_sheets2,  Color.RED, false, 11, "Phòng ban: "+obj.getDvthTen());

			//------------------------------------------------------------------- XUẤT THÂN DỮ LIỆU
			
			
			int countmerPB=0;
			
			
			String query = " select DISTINCT  MA "  
					+" \n from ERP_BAOCAO_CHIPHI_PHONGBAN  order by  MA \n";
			System.out.println("query_bophan : "+query);
			ResultSet rschitiet = db.get(query);
			int countPB=0;
			countRow =  10;
			while(rschitiet.next())
			{
				String query_bophan_sheets2 =" select ROW_NUMBER() OVER(ORDER BY MA ASC) AS STT, MA,TENNGUOIDENGHI,LOAICHUNGTU "  
						+" \n from ERP_BAOCAO_CHIPHI_PHONGBAN a WHERE A.MA LIKE '"+rschitiet.getString("MA")+"' \n group by  MA,TENNGUOIDENGHI,LOAICHUNGTU ";
				System.out.println("query_bophan : "+query_bophan_sheets2);
				//ResultSet rstonghop = obj.getRsBaoCaoTongHop();
				ResultSet rstonghop_sheets2=db.get(query_bophan_sheets2);
				double ttDNTT =0;
				double ttDNTU =0;
				stt=1;
				int coutmerNguoidenghi =0;
				countPB = countRow;
				int dempb=0;

				color_=Color.BLACK;
				while(rstonghop_sheets2.next())
				{
					
					int dem=0;
					coutmerNguoidenghi =countRow;
					countmerPB=countRow;
					String query_soPB_sheets2="select isnull(count(a.LOAICHUNGTU  ),0) as dem "
							+ " \n from  ERP_BAOCAO_CHIPHI_PHONGBAN a where a.LOAICHUNGTU like N'%"+rstonghop_sheets2.getString("LOAICHUNGTU")+"%' AND A.MA LIKE '%"+rstonghop_sheets2.getString("MA")+"%' AND A.TENNGUOIDENGHI LIKE N'"+rstonghop_sheets2.getString("TENNGUOIDENGHI")+"' "
							+ " \n group by  a.MA,A.TENNGUOIDENGHI,a.LOAICHUNGTU ";
					System.out.println("query_soPB2 : "+query_soPB_sheets2);
					ResultSet rsSoPB_sheets2=db.get(query_soPB_sheets2);
					try {
						if(rsSoPB_sheets2!=null)
						{ 
							while(rsSoPB_sheets2.next())
							{
								dem=rsSoPB_sheets2.getInt("dem");
							}
							rsSoPB_sheets2.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("dem: "+dem);
					//merge PB  -- 2. XUất Phân bổ
					int coldem=countRow+dem-1;
					cells_sheets2.merge(countRow, 2,coldem , 2);
					System.out.println("coldem: "+coldem);
					for (int j = 1; j <= dem; j++) {
						cell_sheets2 = cells_sheets2.getCell(countRow,2);
						System.out.println("xuat: "+countRow+" dd "+j);
						ReportAPI.getCellStyle(cell_sheets2, "Times New Roman", color_, false, 11, rstonghop_sheets2.getString("LOAICHUNGTU"));
						setCellBorderStyle_v(cell_sheets2, HorizontalAlignmentType.CENTRED,VerticalAlignmentType.CENTRED,false);	
						countRow++;					
					}

					countRow=countRow-dem;
					
					/*String query_chitiet="select PHONGBAN,LOAICHUNGTU,TONGTIEN "
							+ " \n from  uft_REPORT_CHIPHI_PHONGBAN("+obj.getCtyId()+","+(obj.getDvthId().trim().length() >0?obj.getDvthId():"0")+",'"+obj.getTungay()+"','"+obj.getDenngay()+"')"
							+ " \n where PHONGBAN like N'%"+rstonghop.getString("PHONGBAN")+"%' ";*/
					String query_chitiet="select a.MA,A.TENNGUOIDENGHI,a.LOAICHUNGTU,a.TONGTIEN,a.SOCHUNGTU "
							+ " \n from ERP_BAOCAO_CHIPHI_PHONGBAN a where a.LOAICHUNGTU like N'%"+rstonghop_sheets2.getString("LOAICHUNGTU")+"%'  AND A.MA LIKE '%"+rstonghop_sheets2.getString("MA")+"%' AND A.TENNGUOIDENGHI LIKE N'"+rstonghop_sheets2.getString("TENNGUOIDENGHI")+"'";
					System.out.println("query_chitiet 2: "+query_chitiet);
					ttDNTU= 0;
					ttDNTT=0;
					ResultSet rsChitiet=db.get(query_chitiet);
					try {
						if(rsChitiet!=null)
						{ 
							while(rsChitiet.next())
							{
								
								//3.Xuất chi tiết
								for(int i = 2; i <= 3; i ++)
								{
									cell_sheets2 = cells_sheets2.getCell(countRow,i+1);
									System.out.println("xuat: "+countRow+" dd "+i);
									if(i==2)
									{
										ReportAPI.getCellStyle(cell_sheets2, "Times New Roman", color_, false, 11, rsChitiet.getString("SOCHUNGTU"));
										
									}
									else
									{
										this.getCellStyle(cell_sheets2, "Times New Roman", color_, false, 11, rsChitiet.getDouble("TONGTIEN"));
										if(rsChitiet.getString("LOAICHUNGTU").equals("Tạm ứng"))
										{
											ttDNTU += rsChitiet.getDouble("TONGTIEN");
										}
										if(rsChitiet.getString("LOAICHUNGTU").equals("Đề nghị thanh toán"))
										{
											ttDNTT += rsChitiet.getDouble("TONGTIEN");
										}
										dempb++;
									}
									setCellBorderStyle(cell_sheets2, HorizontalAlignmentType.CENTRED,false);
									
									
								}
								countRow++;
							}
							rsChitiet.close();
						}
						
						
						
							cells_sheets2.merge(countRow, 2, countRow, 3);
							for (int j = 2; j <= 3; j++) {
							cell_sheets2 = cells_sheets2.getCell(countRow,j);
							if(rstonghop_sheets2.getString("LOAICHUNGTU").equals("Tạm ứng"))
							{
								ReportAPI.getCellStyle(cell_sheets2, "Times New Roman", color_, false, 11, "Tổng đề nghị tạm ứng");
							}
							else
							{
								ReportAPI.getCellStyle(cell_sheets2, "Times New Roman", color_, false, 11, "Tổng đề nghị thanh toán");
							}
							}
							cell_sheets2 = cells_sheets2.getCell(countRow,4);
							setCellBorderStyle_v(cell_sheets2, HorizontalAlignmentType.CENTRED,VerticalAlignmentType.CENTRED,false);
							if(rstonghop_sheets2.getString("LOAICHUNGTU").equals("Tạm ứng"))
							{
								this.getCellStyle(cell_sheets2, "Times New Roman", color_, false, 11, ttDNTU);
								dempb++;
							}
							else
							{
								this.getCellStyle(cell_sheets2, "Times New Roman", color_, false, 11, ttDNTT);
								dempb++;
							}
							
						countRow++;
						cells_sheets2.merge(coutmerNguoidenghi, 1, countRow-1, 1);
						for (int j = 1; j <=dem+1; j++) {
							cell_sheets2 = cells_sheets2.getCell(coutmerNguoidenghi,1);
							setCellBorderStyle_v(cell_sheets2, HorizontalAlignmentType.CENTRED,VerticalAlignmentType.CENTRED,false);	
							ReportAPI.getCellStyle(cell_sheets2, "Times New Roman", color_, false, 11,rstonghop_sheets2.getString("TENNGUOIDENGHI"));	
							coutmerNguoidenghi++;					
						}
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				if(rstonghop_sheets2!=null)rstonghop_sheets2.close();
				cells_sheets2.merge(countPB, 0, countRow-1, 0);
				System.out.println("dem pn:" +dempb);
				for (int j = 1; j <=dempb; j++) {
					cell_sheets2 = cells_sheets2.getCell(countPB,0);
					setCellBorderStyle_v(cell_sheets2, HorizontalAlignmentType.CENTRED,VerticalAlignmentType.CENTRED,false);	
					ReportAPI.getCellStyle(cell_sheets2, "Times New Roman", color_, false, 11,rschitiet.getString("MA"));
					countPB++;			
				}
				
				
			}
			if(rschitiet !=null) rschitiet.close();
			
			//END CHI TIẾT 
			//------------------------------------------------
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			workbook.save(out);
			fstream.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
			ex.printStackTrace();
			throw new Exception("Không tạo được báo cáo");
		}

	}
	private void setCellBorderStyle(Cell cell, short align,boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		if(kt)
		{
			com.aspose.cells.Font font2 = new com.aspose.cells.Font(); 
			font2.setName("Calibri");
			font2.setColor(Color.BLACK);
			font2.setSize(11);
			style.setFont(font2);
			style.setColor(Color.SILVER);
		}
		else
			style.setColor(Color.WHITE);

		cell.setStyle(style);
	}
	private void setCellBorderStyle_v(Cell cell, short align,short align2,boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setVAlignment(align2);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		
		if(kt)
		{
			com.aspose.cells.Font font2 = new com.aspose.cells.Font(); 
			font2.setName("Calibri");
			font2.setColor(Color.BLACK);
			font2.setSize(11);
			style.setFont(font2);
			style.setColor(Color.SILVER);
		}
		else
			style.setColor(Color.WHITE);

		cell.setStyle(style);
	}
	public Cell CreateBorderSetting2(Cell cell, int number) throws IOException {

		Style style;
		style = cell.getStyle();

		// Set border color
		style.setBorderColor(BorderType.TOP, Color.BLACK);
		style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		style.setBorderColor(BorderType.LEFT, Color.BLACK);
		style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		// Set the cell border type
		style.setBorderLine(BorderType.TOP, BorderLineType.DOTTED);
		style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		// set number = 41 format currency
		style.setNumber(number);
		cell.setStyle(style);

		return cell;
	}

	public Cell CreateBorderSetting(Cell cell, int number) throws IOException {

		Style style;
		style = cell.getStyle();

		// Set border color
		style.setBorderColor(BorderType.TOP, Color.BLACK);
		style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		style.setBorderColor(BorderType.LEFT, Color.BLACK);
		style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		// Set the cell border type
		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		// set number =41 format currency
		style.setNumber(number);
		cell.setStyle(style);
		return cell;
	}

	private String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static void getCellStyle(Cell cell, String fontName, Color color, Boolean bold, int size, Double cellValue)
	{     
		Style style;
		style = cell.getStyle();
		//style.setTextWrapped(true);

		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);

		Font font = new Font();
		font.setName(fontName);
		font.setColor(color);
		font.setBold(bold);
		font.setSize(size);
		style.setFont(font);
		cell.setStyle(style);
		cell.setValue(cellValue);
	}

}

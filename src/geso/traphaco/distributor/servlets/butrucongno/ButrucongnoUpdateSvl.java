package geso.traphaco.distributor.servlets.butrucongno;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.distributor.beans.butrucongno.IButrucongno;
import geso.traphaco.distributor.beans.butrucongno.IButrucongnoList;
import geso.traphaco.distributor.beans.butrucongno.imp.Butrucongno;
import geso.traphaco.distributor.beans.butrucongno.imp.ButrucongnoList;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class ButrucongnoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ButrucongnoUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("vad");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{

			IButrucongno nvgnBean;
			PrintWriter out;

			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			String action = util.getAction(querystring);
			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);
			String nextJSP = "";
			nvgnBean = new Butrucongno(id);
			nvgnBean.setUserId(userId);
			nvgnBean.init();

			session.setAttribute("nvgnBean", nvgnBean);
			if(action.equals("update"))
			{
			   nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKHUpdate.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKHDisplay.jsp";
			}
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
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			
			IButrucongno nvgnBean;
			PrintWriter out;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			//out = response.getWriter();
			Utility util = new Utility();

			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
			{
				id = "";
				nvgnBean = new Butrucongno("");
			} else
			{
				nvgnBean = new Butrucongno(id);
			}

			userId = util.antiSQLInspection(request.getParameter("userId"));
			nvgnBean.setUserId(userId);

			String ngay = util.antiSQLInspection(request.getParameter("ngay"));
			if (ngay == null)
				ngay = getDateTime();
			nvgnBean.setNgay(ngay);

			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
			nvgnBean.setGhichu(ghichu);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			nvgnBean.setNppId(nppId);


			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";
			nvgnBean.setTungay(tungay);

			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";
			nvgnBean.setDenngay(denngay);
			
			String sohoadontu = util.antiSQLInspection(request.getParameter("sohoadontu"));
			if (sohoadontu == null)
				sohoadontu = "";
			nvgnBean.setSohoadontu(sohoadontu);

			String sohoadonden = util.antiSQLInspection(request.getParameter("sohoadonden"));
			if (sohoadonden == null)
				sohoadonden = "";
			nvgnBean.setSohoadonden(sohoadonden);
			
			String sotientu = util.antiSQLInspection(request.getParameter("sotientu"));
			if (sotientu == null)
				sotientu = "0";
			nvgnBean.setSotientu(sotientu);			

			String sotienden = util.antiSQLInspection(request.getParameter("sotienden"));
			if (sotienden == null)
				sotienden = "0";
			nvgnBean.setSotienden(sotienden);

			String KHId = util.antiSQLInspection(request.getParameter("khIdLoc"));
			if (KHId == null)
				KHId = "";
			nvgnBean.setKhId(KHId);
			
			String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
			if (ddkdId == null)
				ddkdId = "";
			nvgnBean.setDdkdId(ddkdId);
			
			String ckKh = util.antiSQLInspection(request.getParameter("ckKh"));
			if (ckKh == null)
				ckKh = "";
			nvgnBean.setckKh(ckKh);
			System.out.println("ckKh :"+ ckKh);
			
						
			String ngaysua = getDateTime();
			nvgnBean.setNgaysua(ngaysua);
			
			String[] hdId = request.getParameterValues("hdId");			
			nvgnBean.setHdId(hdId);
			
			String[] khId = request.getParameterValues("khId");
			nvgnBean.setHdKhid(khId);
			
			String[] hdduno = request.getParameterValues("hdduno");
			nvgnBean.setHdDuno(hdduno);
			
			String[] hdduco = request.getParameterValues("hdduco");
			nvgnBean.setHdDuco(hdduco);
			
			String[] hdghino = request.getParameterValues("hdghino1");
			nvgnBean.setHdGhino(hdghino);
			
			String[] hdghico = request.getParameterValues("hdghico1");
			nvgnBean.setHdGhico(hdghico);
			
			String[] hdkhMa = request.getParameterValues("khMa");
			nvgnBean.setHdMakh(hdkhMa);
					
			String[] hdNgayhd = request.getParameterValues("hdNgay");
			nvgnBean.setHdNgayhd(hdNgayhd);
			
			String[] hdsohoadon = request.getParameterValues("hdsohoadon");
			nvgnBean.setHdSohd(hdsohoadon);
			
			if(ckKh=="")//khi check bù trừ theo hóa đơn
			{
				if (hdId != null)
				{
					String[] hdids = new String[hdId.length];
					int i = 0;
					while (i < hdId.length)
					{
						String chon=request.getParameter(hdId[i]);
						if(chon==null)
						{
							hdId[i]="";
							hdids[i]="";
						}else
						{
							hdids[i] =hdId[i];
							System.out.println("HD"+hdids[i]);
						}
						i++;
					}
					nvgnBean.setHdIds(hdids);
				}							
			}
			else //check  bù trừ theo khách hàng
			{
				if(khId!=null)
				{
					String[] khIds = new String[khId.length];
					int i = 0;
					while (i < khId.length)
					{
						String chon=request.getParameter(khId[i]);
						if(chon==null)
						{
							khId[i]="";
							khIds[i]="";
						}else
						{
							khIds[i] =khId[i];
							System.out.println("KH: "+khIds[i]);
						}
						i++;
					}
					nvgnBean.setKhIds(khIds);					
				}
			}
			
			boolean error = false;


			String action = request.getParameter("action");
			
			if (!error)
			{
				if (action.equals("save"))
				{
					if (id.length() == 0)
					{
						if (!(nvgnBean.CreateNvgn()))//TAO MOI
						{
							nvgnBean.createRS();
							session.setAttribute("nvgnBean", nvgnBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKHNew.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							IButrucongnoList obj = new ButrucongnoList();
							obj.setUserId(userId);
							String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
							GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
							obj.init("");
							session.setAttribute("obj", obj);
							
							String nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKH.jsp";
							response.sendRedirect(nextJSP);
						}

					} else
					{
						if (!(nvgnBean.UpdateNvgn()))//CAP NHAT
						{
							nvgnBean.init();
							session.setAttribute("nvgnBean", nvgnBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKHUpdate.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							IButrucongnoList obj = new ButrucongnoList();
							obj.setUserId(userId);
							String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
							GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
							obj.init("");
							session.setAttribute("obj", obj);

							String nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKH.jsp";
							response.sendRedirect(nextJSP);
						}
					}
				} else
					//SEARCH 
				{
					//
					if (action.equals("search"))
					{
						nvgnBean.setHdId(null);
						nvgnBean.setHdKhid(null);
						nvgnBean.createKHRS();
						
					}
					
					//xuatexcel
					if (action.equals("xuatexcel"))
					{
						System.out.println("--ACTION: xuatexcel.");
						
						OutputStream outstream = null;
						try
						{
							WorkbookSettings wbSettings = new WorkbookSettings();
							wbSettings.setLocale(new Locale("en", "EN"));			
							response.setContentType("application/vnd.ms-excel");
						    response.setHeader("Content-Disposition", "attachment; filename=BuTruCongNo.xls");
						    outstream = response.getOutputStream();//su dung cai nay thi ko dung reponse.getWriter();
							WritableWorkbook workbook = Workbook.createWorkbook(outstream, wbSettings);
						    
							workbook.createSheet("BuTruCongNo", 0);
							WritableSheet Sheet = workbook.getSheet(0);
							workbook.setColourRGB(Colour.RED, 0xff, 0, 0);
							
							this.CreateHeader(Sheet,nvgnBean);
							this.CreateData2(Sheet,nvgnBean);
						
							workbook.write();		
							workbook.close();
						}
						catch(Exception ex)
						{
							//System.out.print("Exception..." + ex.getMessage());
							ex.printStackTrace();
						}
						finally
					    {
					     if (outstream != null)
					    	 outstream.close();
					    }
						
						return;
					}
					//
					if(action.equals("submitForm"))
					{
						if (id.length() > 0)
						{
							nvgnBean.init();
						} else
						{
							nvgnBean.setHdId(null);
							nvgnBean.createRS();
						}
					}
					session.setAttribute("nvgnBean", nvgnBean);

					String nextJSP;
					if (id.length() == 0)
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKHNew.jsp";
					} else
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKHUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
			} else
			{
				if (id.length() > 0)
				{
					nvgnBean.init();
				} else
				{
					nvgnBean.createRS();
				}
				session.setAttribute("nvgnBean", nvgnBean);
				String nextJSP;
				if (id.length() == 0)
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKHNew.jsp";
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/BuTruCongNoKHUpdate.jsp";
				}
				response.sendRedirect(nextJSP);
			}
		}

	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private void CreateHeader(WritableSheet ws,IButrucongno nvgnBean)
	{
		WritableFont fontTieude = new WritableFont(WritableFont.ARIAL, 13, WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);
	    WritableCellFormat tieude = new WritableCellFormat(fontTieude);// xet cell
	    try {
			tieude.setWrap(false);
		    tieude.setAlignment(Alignment.CENTRE);
		    tieude.setBorder(Border.NONE, BorderLineStyle.NONE);
		    tieude.setVerticalAlignment(VerticalAlignment.CENTRE);
		    //tieude.setBackground(Colour.YELLOW );
		    
		    Label txt = new Label(2,2, "BẢNG BÙ TRỪ CÔNG NỢ", tieude);
		    txt.setCellFormat(tieude);
		    ws.addCell(txt);	
		    
			fontTieude = new WritableFont(WritableFont.ARIAL, 13, WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLUE);
		    tieude = new WritableCellFormat(fontTieude);// xet cell
		    tieude.setWrap(false);
		    tieude.setAlignment(Alignment.CENTRE);
		    tieude.setBorder(Border.NONE, BorderLineStyle.NONE);
		    tieude.setVerticalAlignment(VerticalAlignment.CENTRE);
		    if(nvgnBean.getNgay()!=null)
		    {
			    txt = new Label(2,3, "Ngày chứng từ: " + nvgnBean.getNgay(), tieude);
			    txt.setCellFormat(tieude);
			    ws.addCell(txt);
		    }
		    else
		    {
		    	txt = new Label(2,3, "Ngày chứng từ: N/A", tieude);
		    }

		    
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	}
	private void CreateData(WritableSheet sheet,IButrucongno nvgnBean)
	{
		try {
		    // Create cell font and format
			//Table header
		    WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		    cellFont.setColour(Colour.BLACK);		    
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.YELLOW);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    
		    //Table body
		    WritableFont cellFont2 = new WritableFont(WritableFont.TIMES, 10);
		    cellFont2.setColour(Colour.BLACK);		    
		    WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont2);
		    //cellFormat2.setBackground(Colour.YELLOW);
		    cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
		    
		    //Set cell width in CHARS
		    int col = 1;
		    int widthInChars = 5;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 5, "STT", cellFormat));    
		    
		    col = 2;
		    widthInChars = 25;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 5, "Tên khách hàng OTC/ETC", cellFormat));  
		    
		    col = 3;
		    widthInChars = 16;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 5, "Số chứng từ", cellFormat));  
		    
		    col = 4;
		    widthInChars = 16;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 5, "Ngày hóa đơn", cellFormat)); 
		    
		    col = 5;
		    widthInChars = 16;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 5, "Số hóa đơn", cellFormat)); 
		    
		    col = 6;
		    widthInChars = 16;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 5, "Dư nợ", cellFormat)); 
		    
		    col = 7;
		    widthInChars = 16;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 5, "Dư có", cellFormat)); 
		    
		    col = 8;
		    widthInChars = 16;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 5, "Ghi nợ", cellFormat)); 
	    
		    col = 9;
		    widthInChars = 16;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 5, "Ghi có", cellFormat)); 
		    
		    //Xuat cac con so

/*		    WritableCell titleCell = new Label(0, 0, "");//col & row
		    sheet.addCell(titleCell);
		    sheet.mergeCells(0, 0, 1, 1);//col & row with col & row
*/		    
		    
		    
		    if(!nvgnBean.getckKh().equals("1")) {
		    	sheet.mergeCells(1, 6, 5, 6);
		    	
		    }
		    if(nvgnBean.getckKh().equals("1")) {
		    	sheet.mergeCells(1, 6, 2, 6);			    
		    }
		    sheet.addCell(new Label(1,6,"",cellFormat2));//cellFormat2 set border cho o  duoc mergecells.


	    	NumberFormat formatter = new DecimalFormat("#,###,###"); 
		    double tongduno = 0;
		    double tongduco = 0;
		    double tongghino = 0;
		    double tongghico = 0;
		    
		    
		    if(nvgnBean!=null)
		    {
		    	   //ResultSet hoadonList = nvgnBean.getHoadonList() ;
		    	   
		    	   String[] hdId = nvgnBean.getHdId();
		    	   String[] hdNgayhd = nvgnBean.getHdNgayhd();
		    	   //String[] hdKhid = nvgnBean.getHdKhid();
		    	   String[] hdMakh = nvgnBean.getHdMakh();
		    	   //String[] hdMadt = nvgnBean.getHdMadt();
		    	   //String[] hdKyhieu = nvgnBean.getHdKyhieu();
		    	   String[] hdSohd = nvgnBean.getHdSohd();
		    	   //String[] hdSotien = nvgnBean.getHdSotien();
		    	   //String[] hdChon = nvgnBean.getHdChon();
		    	   String[] HdDuno = nvgnBean.getHdDuno();
		    	   String[] HdDuco = nvgnBean.getHdDuco();
		    	   String[] HdGhino = nvgnBean.getHdGhino();
		    	   String[] HdGhico = nvgnBean.getHdGhico();
		    	   
		    	   
		    	   
		    	   if(!nvgnBean.getckKh().equals("1"))
                   {
	                   //int n = 0;
	                  	int stt=1;
	                   if(hdId != null)
	                   {	
		                   	int k =  hdId.length;
		                   	String[] _NO=new String[k];
							String[] _CO=new String[k];
							String[] _GHINO = new String[k];
							String[] _GHICO = new String[k];
								for(int i = 0; i < hdId.length ; i++)
								{
									if(HdDuno[i]=="")
									{
										HdDuno[i]="0";
									}
									else
									{
										HdDuno[i]=HdDuno[i].replace(",", "");
									}
									if(HdDuco[i]=="")
									{
										HdDuco[i]="0";
									}
									else
									{
										HdDuco[i]=HdDuco[i].replace(",", "");
									}
									if(HdGhino[i]=="")
									{
										HdGhino[i]="0";
									}									
									else
									{
										HdGhino[i]=HdGhino[i].replace(",", "");
									}
									if(HdGhico[i]=="")
									{
										HdGhico[i]="0";
									}									
									else
									{
										HdGhico[i]=HdGhico[i].replace(",", "");
									}
									
									double duno = Double.parseDouble(HdDuno[i].toString());
									double duco = Double.parseDouble(HdDuco[i].toString());
									double ghino = Double.parseDouble(HdGhino[i].toString());
									double ghico = Double.parseDouble(HdGhico[i].toString());
									
									tongduno=tongduno+duno;
									tongduco=tongduco+duco;
									tongghino=tongghino+ghino;
									tongghico=tongghico+ghico;
									
									System.out.println("duno:'" + duno + "', duco:'"+duco + "' , ghino:'" +ghino+ "' ,ghico:'" + ghico + "' "
											+ "");
									if(duno > 0 )
									{
										_NO[i] = formatter.format(duno);
										_CO[i] = "";
									}
									else
									{
										duno=duno*(-1);
										_NO[i] = "";										
										_CO[i] = formatter.format(duno);
									}
									
									if(duco != 0)
									{
										_NO[i] = "";
										_CO[i] = formatter.format(duco);
									}								
																		
									if(ghino >0)
										_GHINO[i]=formatter.format(ghino);	
									else									
										_GHINO[i] ="";
									
									if(ghico >0)
										_GHICO[i]=formatter.format(ghico);
									else
										_GHICO[i]="";
									
									//Cot STT
								    col = 1;
								    widthInChars = 5;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(stt), cellFormat2)); 
						    	   //Cot Ten khach hang OTC/ETC
								    col = 2;
								    widthInChars = 25;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(hdMakh[i]), cellFormat2)); 								    
						    	   //So chung tu
								    col = 3;
								    widthInChars = 16;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(hdId[i]), cellFormat2)); 						    	   
						    	   //Ngay hoa don
								    col = 4;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(hdNgayhd[i]), cellFormat2)); 								    
						    	   //So hoa don
								    col = 5;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(hdSohd[i]), cellFormat2)); 
						    	   //Du no
								    col = 6;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_NO[i]), cellFormat2));
						    	   //Du co
								    col = 7;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_CO[i]), cellFormat2));
						    	   //Ghi no
								    col = 8;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_GHINO[i]), cellFormat2));
						    	   //Ghi co
								    col = 9;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_GHICO[i]), cellFormat2));

									stt++;
								}
						}
                   }
		    	   
		    }
		    
		    nvgnBean.setTc_duno(formatter.format(tongduno));
		    nvgnBean.setTc_duco(formatter.format(tongduco));
		    nvgnBean.setTc_ghino(formatter.format(tongghino));
		    nvgnBean.setTc_ghico(formatter.format(tongghico));
		    
		    col = 6;
		    widthInChars = 6;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 6, nvgnBean.getTc_duno(), cellFormat2)); 
		    //System.out.println("Dư nợ tổng " +nvgnBean.getTc_duno());
		    
		    col = 7;
		    widthInChars = 6;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 6, nvgnBean.getTc_duco(), cellFormat2)); 
		    //System.out.println("Dư có tổng: " +nvgnBean.getTc_duco());
		    
		    col = 8;
		    widthInChars = 6;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 6, nvgnBean.getTc_ghino(), cellFormat2)); 
		    //System.out.println("Ghi nợ tổng: " + nvgnBean.getTc_ghino());
		    
		    col = 9;
		    widthInChars = 6;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 6, nvgnBean.getTc_ghico(), cellFormat2)); 
		    //System.out.println("Ghi có tổng: " + nvgnBean.getTc_ghico());
		    
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	private void CreateData2(WritableSheet sheet,IButrucongno nvgnBean)
	{
		try {
		    // Create cell font and format
			//Table header
		    WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		    cellFont.setColour(Colour.BLACK);		    
		    WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		    cellFormat.setBackground(Colour.YELLOW);
		    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    
		    //Table body
		    WritableFont cellFont2 = new WritableFont(WritableFont.TIMES, 10);
		    cellFont2.setColour(Colour.BLACK);		    
		    WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont2);
		    //cellFormat2.setBackground(Colour.YELLOW);
		    cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
		    
		    //Set cell width in CHARS
		    int col = 1;
		    int widthInChars = 5;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 5, "STT", cellFormat));    
		    
		    col = 2;
		    widthInChars = 25;
		    sheet.setColumnView(col, widthInChars);
		    sheet.addCell(new Label(col, 5, "Tên khách hàng OTC/ETC", cellFormat));  
		    
		    
		    
		    //Xuat cac con so

/*		    WritableCell titleCell = new Label(0, 0, "");//col & row
		    sheet.addCell(titleCell);
		    sheet.mergeCells(0, 0, 1, 1);//col & row with col & row
*/		    
		    
		    
		    if(!nvgnBean.getckKh().equals("1")) {
		    	sheet.mergeCells(1, 6, 5, 6);
		    	
		    	col = 3;
			    widthInChars = 16;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 5, "Số chứng từ", cellFormat));  
			    
			    col = 4;
			    widthInChars = 16;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 5, "Ngày hóa đơn", cellFormat)); 
			    
			    col = 5;
			    widthInChars = 16;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 5, "Số hóa đơn", cellFormat)); 
			    
			    col = 6;
			    widthInChars = 16;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 5, "Dư nợ", cellFormat)); 
			    
			    col = 7;
			    widthInChars = 16;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 5, "Dư có", cellFormat)); 
			    
			    col = 8;
			    widthInChars = 16;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 5, "Ghi nợ", cellFormat)); 
		    
			    col = 9;
			    widthInChars = 16;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 5, "Ghi có", cellFormat)); 
		    	
		    }
		    if(nvgnBean.getckKh().equals("1")) {
		    	sheet.mergeCells(1, 6, 2, 6);	
			    
			    col = 3;
			    widthInChars = 16;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 5, "Dư nợ", cellFormat)); 
			    
			    col = 4;
			    widthInChars = 16;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 5, "Dư có", cellFormat)); 
			    
			    col = 5;
			    widthInChars = 16;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 5, "Ghi nợ", cellFormat)); 
		    
			    col = 6;
			    widthInChars = 16;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 5, "Ghi có", cellFormat)); 
		    }
		    
		    sheet.addCell(new Label(1,6,"",cellFormat2));//cellFormat2 set border cho o  duoc mergecells.


	    	NumberFormat formatter = new DecimalFormat("#,###,###"); 
		    double tongduno = 0;
		    double tongduco = 0;
		    double tongghino = 0;
		    double tongghico = 0;
		    
		    
		    if(nvgnBean!=null)
		    {
		    	   //ResultSet hoadonList = nvgnBean.getHoadonList() ;
		    	   
		    	   String[] hdId = nvgnBean.getHdId();
		    	   String[] hdNgayhd = nvgnBean.getHdNgayhd();
		    	   String[] hdKhid = nvgnBean.getHdKhid();
		    	   String[] hdMakh = nvgnBean.getHdMakh();
		    	   //String[] hdMadt = nvgnBean.getHdMadt();
		    	   //String[] hdKyhieu = nvgnBean.getHdKyhieu();
		    	   String[] hdSohd = nvgnBean.getHdSohd();
		    	   //String[] hdSotien = nvgnBean.getHdSotien();
		    	   //String[] hdChon = nvgnBean.getHdChon();
		    	   String[] HdDuno = nvgnBean.getHdDuno();
		    	   String[] HdDuco = nvgnBean.getHdDuco();
		    	   String[] HdGhino = nvgnBean.getHdGhino();
		    	   String[] HdGhico = nvgnBean.getHdGhico();
		    	   
		    	   
		    	   System.out.println("nvgnBean.getckKh: '" + nvgnBean.getckKh() + "' ");
		    	   if(!nvgnBean.getckKh().equals("1")) //theo Hoa Don
                   {
		    		   System.out.println("*Theo Hoa Don");
	                   //int n = 0;
	                  	int stt=1;
	                   if(hdId != null)
	                   {	
		                   	int k =  hdId.length;
		                   	String[] _NO=new String[k];
							String[] _CO=new String[k];
							String[] _GHINO = new String[k];
							String[] _GHICO = new String[k];
								for(int i = 0; i < hdId.length ; i++)
								{
									if(HdDuno[i]=="")
									{
										HdDuno[i]="0";
									}
									else
									{
										HdDuno[i]=HdDuno[i].replace(",", "");
									}
									if(HdDuco[i]=="")
									{
										HdDuco[i]="0";
									}
									else
									{
										HdDuco[i]=HdDuco[i].replace(",", "");
									}
									if(HdGhino[i]=="")
									{
										HdGhino[i]="0";
									}									
									else
									{
										HdGhino[i]=HdGhino[i].replace(",", "");
									}
									if(HdGhico[i]=="")
									{
										HdGhico[i]="0";
									}									
									else
									{
										HdGhico[i]=HdGhico[i].replace(",", "");
									}
									
									double duno = Double.parseDouble(HdDuno[i].toString());
									double duco = Double.parseDouble(HdDuco[i].toString());
									double ghino = Double.parseDouble(HdGhino[i].toString());
									double ghico = Double.parseDouble(HdGhico[i].toString());
									
									tongduno=tongduno+duno;
									tongduco=tongduco+duco;
									tongghino=tongghino+ghino;
									tongghico=tongghico+ghico;
									
									System.out.println("duno:'" + duno + "', duco:'"+duco + "' , ghino:'" +ghino+ "' ,ghico:'" + ghico + "' "
											+ "");
									if(duno > 0 )
									{
										_NO[i] = formatter.format(duno);
										_CO[i] = "";
									}
									else
									{
										duno=duno*(-1);
										_NO[i] = "";										
										_CO[i] = formatter.format(duno);
									}
									
									if(duco != 0)
									{
										_NO[i] = "";
										_CO[i] = formatter.format(duco);
									}								
																		
									if(ghino >0)
										_GHINO[i]=formatter.format(ghino);	
									else									
										_GHINO[i] ="";
									
									if(ghico >0)
										_GHICO[i]=formatter.format(ghico);
									else
										_GHICO[i]="";
									
									//Cot STT
								    col = 1;
								    widthInChars = 5;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(stt), cellFormat2)); 
						    	   //Cot Ten khach hang OTC/ETC
								    col = 2;
								    widthInChars = 25;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(hdMakh[i]), cellFormat2)); 								    
						    	   //So chung tu
								    col = 3;
								    widthInChars = 16;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(hdId[i]), cellFormat2)); 						    	   
						    	   //Ngay hoa don
								    col = 4;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(hdNgayhd[i]), cellFormat2)); 								    
						    	   //So hoa don
								    col = 5;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(hdSohd[i]), cellFormat2)); 
						    	   //Du no
								    col = 6;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_NO[i]), cellFormat2));
						    	   //Du co
								    col = 7;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_CO[i]), cellFormat2));
						    	   //Ghi no
								    col = 8;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_GHINO[i]), cellFormat2));
						    	   //Ghi co
								    col = 9;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_GHICO[i]), cellFormat2));

									stt++;
								}
						}
                   }		    	   
		    	   else if(nvgnBean.getckKh().equals("1")) // Theo Khach Hang
		    	   {
		    		   System.out.println("*Theo Khach Hang");
	                   //int n = 0;
	                  	int stt=1;
	                   if(hdKhid != null)
	                   {	
		                   	int k =  hdKhid.length;
		                   	String[] _NO=new String[k];
							String[] _CO=new String[k];
							String[] _GHINO = new String[k];
							String[] _GHICO = new String[k];
								for(int i = 0; i < hdKhid.length ; i++)
								{
									if(HdDuno[i]=="")
									{
										HdDuno[i]="0";
									}
									else
									{
										HdDuno[i]=HdDuno[i].replace(",", "");
									}
									if(HdDuco[i]=="")
									{
										HdDuco[i]="0";
									}
									else
									{
										HdDuco[i]=HdDuco[i].replace(",", "");
									}
									if(HdGhino[i]=="")
									{
										HdGhino[i]="0";
									}									
									else
									{
										HdGhino[i]=HdGhino[i].replace(",", "");
									}
									if(HdGhico[i]=="")
									{
										HdGhico[i]="0";
									}									
									else
									{
										HdGhico[i]=HdGhico[i].replace(",", "");
									}
									
									double duno = Double.parseDouble(HdDuno[i].toString());
									double duco = Double.parseDouble(HdDuco[i].toString());
									double ghino = Double.parseDouble(HdGhino[i].toString());
									double ghico = Double.parseDouble(HdGhico[i].toString());
									
									tongduno=tongduno+duno;
									tongduco=tongduco+duco;
									tongghino=tongghino+ghino;
									tongghico=tongghico+ghico;
									
									System.out.println("duno:'" + duno + "', duco:'"+duco + "' , ghino:'" +ghino+ "' ,ghico:'" + ghico + "' "
											+ "");
									if(duno > 0 )
									{
										_NO[i] = formatter.format(duno);
										_CO[i] = "";
									}
									else
									{
										duno=duno*(-1);
										_NO[i] = "";										
										_CO[i] = formatter.format(duno);
									}
									
									if(duco != 0)
									{
										_NO[i] = "";
										_CO[i] = formatter.format(duco);
									}								
																		
									if(ghino >0)
										_GHINO[i]=formatter.format(ghino);	
									else									
										_GHINO[i] ="";
									
									if(ghico >0)
										_GHICO[i]=formatter.format(ghico);
									else
										_GHICO[i]="";
									
									//Cot STT
								    col = 1;
								    widthInChars = 5;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(stt), cellFormat2)); 
						    	   //Cot Ten khach hang OTC/ETC
								    col = 2;
								    widthInChars = 25;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(hdMakh[i]), cellFormat2)); 
						    	   //Du no
								    col = 3;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_NO[i]), cellFormat2));
						    	   //Du co
								    col = 4;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_CO[i]), cellFormat2));
						    	   //Ghi no
								    col = 5;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_GHINO[i]), cellFormat2));
						    	   //Ghi co
								    col = 6;
								    sheet.setColumnView(col, widthInChars);
								    sheet.addCell(new Label(col, i+7, String.valueOf(_GHICO[i]), cellFormat2));

									stt++;
								}
						}
                   
		    	   }
		    	   else
		    	   {
		    		   System.out.println("*Khong theo gi ca.");
		    	   }
		    	   
		    }
		    
		    nvgnBean.setTc_duno(formatter.format(tongduno));
		    nvgnBean.setTc_duco(formatter.format(tongduco));
		    nvgnBean.setTc_ghino(formatter.format(tongghino));
		    nvgnBean.setTc_ghico(formatter.format(tongghico));
		    
		    if(!nvgnBean.getckKh().equals("1"))
		    {
		    	col = 6;
			    widthInChars = 10;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 6, nvgnBean.getTc_duno(), cellFormat2)); 
			    //System.out.println("Dư nợ tổng " +nvgnBean.getTc_duno());
			    
			    col = 7;
			    widthInChars = 10;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 6, nvgnBean.getTc_duco(), cellFormat2)); 
			    //System.out.println("Dư có tổng: " +nvgnBean.getTc_duco());
			    
			    col = 8;
			    widthInChars = 10;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 6, nvgnBean.getTc_ghino(), cellFormat2)); 
			    //System.out.println("Ghi nợ tổng: " + nvgnBean.getTc_ghino());
			    
			    col = 9;
			    widthInChars = 10;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 6, nvgnBean.getTc_ghico(), cellFormat2)); 
			    //System.out.println("Ghi có tổng: " + nvgnBean.getTc_ghico());
		    }
		    if(nvgnBean.getckKh().equals("1"))
		    {
		    	col = 3;
			    widthInChars = 10;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 6, nvgnBean.getTc_duno(), cellFormat2)); 
			    //System.out.println("Dư nợ tổng " +nvgnBean.getTc_duno());
			    
			    col = 4;
			    widthInChars = 10;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 6, nvgnBean.getTc_duco(), cellFormat2)); 
			    //System.out.println("Dư có tổng: " +nvgnBean.getTc_duco());
			    
			    col = 5;
			    widthInChars = 10;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 6, nvgnBean.getTc_ghino(), cellFormat2)); 
			    //System.out.println("Ghi nợ tổng: " + nvgnBean.getTc_ghino());
			    
			    col = 6;
			    widthInChars = 10;
			    sheet.setColumnView(col, widthInChars);
			    sheet.addCell(new Label(col, 6, nvgnBean.getTc_ghico(), cellFormat2)); 
			    //System.out.println("Ghi có tổng: " + nvgnBean.getTc_ghico());
		    }
		    
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
}

package geso.traphaco.erp.servlets.kiemkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kiemkho.IErpKiemKho;
import geso.traphaco.erp.beans.kiemkho.IErpKiemKhoList;
import geso.traphaco.erp.beans.kiemkho.IErpKiemKho_SanPham;
import geso.traphaco.erp.beans.kiemkho.imp.ErpKiemKho;
import geso.traphaco.erp.beans.kiemkho.imp.ErpKiemKhoList;
import geso.traphaco.erp.beans.kiemkho.imp.ErpKiemKho_SanPham;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.oreilly.servlet.MultipartRequest;
import java.io.File;
import java.util.Enumeration;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
 
@WebServlet("/ErpKiemKhoUpdateSvl") 
public class ErpKiemKhoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	
	public ErpKiemKhoUpdateSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility util;
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
			String nextJSP = "";
			util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			IErpKiemKho bean = new ErpKiemKho(id);
			bean.setNguoiTao(userId);
			bean.setNguoiSua(userId);
			if (request.getQueryString().indexOf("display") >= 0)
			{   
				bean.display();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKhoDisplay.jsp";
			}
			else if (request.getQueryString().indexOf("update") >= 0)
			{
				bean.ViewToUpdate();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKhoUpdate.jsp";
			}
			session.setAttribute("bean", bean);
			System.out.println("CHUONG TRINH VAO day");
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		util = (Utility) session.getAttribute("util");
		List<IErpKiemKho_SanPham> listSP = new ArrayList<IErpKiemKho_SanPham>();
		String id="";
		String action="";
		 String contentType = request.getContentType();
			
			IErpKiemKho bean = new ErpKiemKho();
			
			
			System.out.println("contentType "+contentType);
			System.out.println("contentType.indexOf(multipart/form-data;charset=utf-8)    " + contentType);
			//
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
				{	
			     	MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8"); 

			 		 userId = util.antiSQLInspection(multi.getParameter("userId"));
					 id=util.antiSQLInspection(multi.getParameter("id"));
			 		 bean.setNguoiTao(userId);
			 		 bean.setNguoiSua(userId);
					 bean.setID(id);
					 
					 String khoid=util.antiSQLInspection(multi.getParameter("khott_fk"));
					 
					 bean.setKhoTT_FK(khoid);
					 String thang=util.antiSQLInspection(multi.getParameter("thang"));
					 bean.setthang(thang);
					 String nam=util.antiSQLInspection(multi.getParameter("nam"));
					 
					 bean.setNam(nam);
					 
					 String lydodieuchinh=util.antiSQLInspection(multi.getParameter("lydodieuchinh"));
					 bean.setLyDoDieuChinh(lydodieuchinh);
					 
					 String ngaydieuchinh=util.antiSQLInspection(multi.getParameter("ngaydieuchinh"));
					 bean.setNgayDieuChinh(ngaydieuchinh);
					 
					 
					
				  	 Enumeration files = multi.getFileNames(); 
				  	 String filenameu  ="";
				  	 
				  	 String spid_str=" '' ";
				  	while (files.hasMoreElements())
	                {
	                     String name = (String)files.nextElement();
	                     filenameu = multi.getFilesystemName(name); 
	                     System.out.println("name  "+name);;
	                }
					String filename=    "C:\\java-tomcat\\data\\"+ filenameu;
					if (filename.length() > 0)
					{
						//doc file excel
						File file = new File(filename);
						Workbook workbook;
						int indexRow=5;
						int j=2;
						try 
						{
							
							//System.out.println(file);
							workbook = Workbook.getWorkbook(file);
							Sheet sheet = workbook.getSheet(0);
							for(int i= indexRow; i < sheet.getRows();i++)
							{
								Cell[] cells = sheet.getRow(i);
								if (cells.length>0){
										IErpKiemKho_SanPham sp = null;
										sp = new ErpKiemKho_SanPham();
										sp.setTenSanPham(cells[1].getContents());	
										sp.setSanPham_FK(cells[6].getContents());
										sp.setMaSanPham(cells[0].getContents());
										sp.setLoaisanpham(cells[2].getContents());
										spid_str=spid_str+",'"+cells[0].getContents().trim()+"'";
										sp.setTonKhoMoi(cells[8].getContents());
										listSP.add(sp);
									
									
								}
							}
							
							bean.setSanPhamKho(listSP);
							
							 Hashtable<String, String> htpkho=bean.getSanphamKho(spid_str);
							String masp_thieu="";
							 for(int i=0;i<listSP.size();i++){
								 IErpKiemKho_SanPham sp=listSP.get(i);
								 if(htpkho.containsKey(sp.getMaSanPham())){
									 sp.setTonKhoCu(htpkho.get(sp.getMaSanPham()));
									 
								 }else{
									 masp_thieu=masp_thieu+","+sp.getMaSanPham();
								 }
							 }
							 if(masp_thieu.length() >2){
								 bean.setMessage(masp_thieu);
							 }
							System.out.println("Chieu dai chuoi  : "+listSP.size());
							
							
						}catch(Exception er){
							er.printStackTrace();
						}
					}
				  	
				}else{
						
							 action = request.getParameter("action");
							 id = util.antiSQLInspection(request.getParameter("id"));
							if(id != null){
								bean.setID(id);
							}
									
							String khott_fk = util.antiSQLInspection(request.getParameter("khott_fk"));
							
							if(khott_fk== null){
								khott_fk="";
							}
							bean.setKhoTT_FK(khott_fk);
									
						 
							String lydodieuchinh = util.antiSQLInspection(request.getParameter("lydodieuchinh"));
							if (lydodieuchinh == null)
								lydodieuchinh = "";
							bean.setLyDoDieuChinh(lydodieuchinh);
							
							String ngaydieuchinh = util.antiSQLInspection(request.getParameter("ngaydieuchinh"));
							if (ngaydieuchinh == null)
								ngaydieuchinh = "";
							bean.setNgayDieuChinh(ngaydieuchinh);
							
							
							bean.setNguoiTao(userId);
							bean.setNguoiSua(userId);
							
							String[] stt = request.getParameterValues("stt");
							String[] MaSP = request.getParameterValues("MaSP");
							String[] TenSP = request.getParameterValues("TenSP");
							String[] SanPham_FK = request.getParameterValues("SanPham_FK");
							String[] Tonkhocu = request.getParameterValues("Tonkhocu");
							
							String[] Tonkhomoi = request.getParameterValues("Tonkhomoi");
							
							int n = stt == null ? 0 : stt.length;
							
							
							for (int i = 0; i < n; i++)
							{
								
								Tonkhocu[i] = Tonkhocu[i].replaceAll(",", "");
								IErpKiemKho_SanPham o = null;
								o = new ErpKiemKho_SanPham();
								o.setMaSanPham(MaSP[i]);
								o.setTenSanPham(TenSP[i]);	
								o.setSanPham_FK(SanPham_FK[i]);
								
								double tonkhocu=0;
								double tonkhomoi=0;
							 
								try{
									tonkhocu=Double.parseDouble(Tonkhocu[i].replaceAll(",", ""));
								}catch(Exception er){
									er.printStackTrace();
								}
								try{
									tonkhomoi=Double.parseDouble(Tonkhomoi[i].replaceAll(",", ""));
								}catch(Exception er){
									er.printStackTrace();
								}
									
								o.setTonKhoCu(""+tonkhocu);
								o.setTonKhoMoi(""+tonkhomoi);
								 
								o.setSoLuongDieuChinh(""+(tonkhomoi-tonkhocu));
								
								listSP.add(o);
							}
							
				}
			bean.setSanPhamKho(listSP);
			bean.CreateRsKho();
			String nextJSP = "";
			if(id.length() >0){
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKhoUpdate.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKhoNew.jsp";
			}
			//System.out.println("Action : "+action);
			
			if(id.length() ==0){
				bean.initThangNam();
			}
			if (action.equals("Save"))
			{
				if (!bean.SaveNew())
				{
					bean.CreateRsKho();
				
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKhoNew.jsp";
				}
				else
				{
					IErpKiemKhoList obj = new ErpKiemKhoList();
					obj.setUsedId(userId);
					obj.init("");
					bean.close();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					session.setAttribute("userTen", userTen);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKho.jsp";
				}
			}
			
			else	if (action.equals("Update"))
			{
				
				if (bean.Update() == true)
				{
					IErpKiemKhoList obj = new ErpKiemKhoList();
					obj.setUsedId(userId);
					obj.init("");
					bean.close();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					session.setAttribute("userTen", userTen);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKho.jsp";
				}
				else
				{
					bean.CreateRsKho();
					
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKhoUpdate.jsp";
				}
			}else if(action.equals("loadsanphamkho")){
				bean.CreateRsSanPham();
			}
			session.setAttribute("bean", bean);
			response.sendRedirect(nextJSP);
		}
	
}

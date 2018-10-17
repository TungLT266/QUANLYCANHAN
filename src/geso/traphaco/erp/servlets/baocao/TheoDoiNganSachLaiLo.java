package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class TheoDoiNganSachLaiLo extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public TheoDoiNganSachLaiLo() {
        super();
    }
    private Hashtable< Integer, String > htbValueCell (){
		Hashtable<Integer, String> htb=new Hashtable<Integer, String>();
		htb.put(1,"A");htb.put(2,"B");htb.put(3,"C");htb.put(4,"D");htb.put(5,"E");
		htb.put(6,"F");htb.put(7,"G");htb.put(8,"H");htb.put(9,"I");htb.put(10,"J");
		htb.put(11,"K");htb.put(12,"L");htb.put(13,"M");htb.put(14,"N");htb.put(15,"O");
		htb.put(16,"P");htb.put(17,"Q");htb.put(18,"R");htb.put(19,"S");htb.put(20,"T");
		htb.put(21,"U");htb.put(22,"V");htb.put(23,"W");htb.put(24,"X");htb.put(25,"Y");
		htb.put(26,"Z");
		
		
		return htb; 
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		obj.setErpCongtyId((String)session.getAttribute("congtyId"));
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.InitErp();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTheoDoiNganSachLaiLo.jsp";
		response.sendRedirect(nextJSP);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		obj.setdiscount("1");
		obj.setvat("1");
		obj.setErpCongtyId((String)session.getAttribute("congtyId"));
//		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
			
		obj.setToYear(util.antiSQLInspection(request.getParameter("nam")));
		
		obj.setFromMonth(util.antiSQLInspection(request.getParameter("tuthang")));
		obj.setToMonth(util.antiSQLInspection(request.getParameter("denthang")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		
		obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId")));
		
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTheoDoiNganSachLaiLo.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{		
			System.out.println("TAO BAO CAO:....");
			
			String URL="../TraphacoHYERP/Erp_PLExportSvl?Id=100009&type=theodoi";
			response.sendRedirect(URL);
			return;
			
		}
		
		
		
		obj.InitErp();
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;
	}
	

	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TheoDoiNganSachLaiLo.xlsm");
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		isFillData = FillData(workbook, obj, query);
   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	private void setStyleColorNormar(Cells cells ,Cell cell,String type)
	{	Cell cell1;
		if(type.equals("0")){
		 cell1 = cells.getCell("X1");
		}else{
			 cell1 = cells.getCell("Y1");
		}
		Style style;	
		 style = cell1.getStyle();
		 style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}

	
	private void setStyleColorHeader(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("M1");
		Style style;	
		 style = cell1.getStyle();
       style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Hashtable<Integer, String> htb=this.htbValueCell();
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		Cell cell = null;
		
		cell = cells.getCell("B3");		cell.setValue(obj.getToYear());	
		String sql="select ten,masothue  from erp_congty where pk_seq="+obj.getErpCongtyId();
		 ResultSet rs=db.get(sql);
		 
		if(rs!=null){
			if(rs.next()){
				cell = cells.getCell("B2");		cell.setValue(rs.getString("ten"));
			
			}
			rs.close();
		}
		int tuthang=Integer.parseInt( obj.getFromMonth());
		int toithang=Integer.parseInt( obj.getToMonth());
		String[] mang=new String[toithang-tuthang+1];
		int nampre=Integer.parseInt( obj.getToYear())-1;
		//mang luu cac bien tong VE DOANH SO
		double[] mangDS=new double[toithang-tuthang+3];
		double[] mangTRAVE=new double[toithang-tuthang+3];
		double[] mangGIAVON=new double[toithang-tuthang+3];
		double[] mangDTTHUAN=new double[toithang-tuthang+3];
		double[] mangLAIGOP=new double[toithang-tuthang+3];
		double[] mangLoiNhuan=new double[toithang-tuthang+3];
		double[] mangChiPhi=new double[toithang-tuthang+3];
		double[] mangChiPhiTotal=new double[toithang-tuthang+3];
		 String chuoingoac="";
			int j=0;
			for(int i=tuthang;i<=toithang;i++){
				String thang=((i+"").length() >1 ?i+"":"0"+i);
				if(i==tuthang){
				chuoingoac="["+obj.getToYear() +"-"+ thang +"]";
				}else{
					chuoingoac=chuoingoac +" ,["+obj.getToYear() +"-"+ thang +"]";
				}
				mang[j]=obj.getToYear() +"-"+ thang ;
				j++;
			}
			System.out.println("Chuoi Ngoac : "+chuoingoac);
		
			String tungay=obj.getToYear() +"-"+ (obj.getFromMonth().length() > 1?obj.getFromMonth():"0"+obj.getFromMonth())+"-01";
			String toingay=obj.getToYear() +"-"+ (obj.getToMonth().length() > 1?obj.getToMonth():"0"+obj.getToMonth())+"-31";
			
			String tungaypre=nampre +"-"+ (obj.getFromMonth().length() > 1?obj.getFromMonth():"0"+obj.getFromMonth())+"-01";
			String toingaypre=nampre +"-"+ (obj.getToMonth().length() > 1?obj.getToMonth():"0"+obj.getToMonth())+"-31";

		
		
			 sql=this.getquery(obj,chuoingoac);
			
				
		 System.out.println(sql);
		 rs=db.get(sql);
		 int index = 7;
		 // set header
		 for(int i=0;i<mang.length;i++){
			 cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
			 cell.setValue(mang[i]);	
			 this.setStyleColorHeader(cells, cell);
		 }
	

		 j=mang.length+3;
	
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("Lũy kế");	
		 this.setStyleColorHeader(cells, cell);
		 j++;
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("%/ Doanh số");	
		 this.setStyleColorHeader(cells, cell);
		 j++;
		
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("% Ngân sách");	
		 this.setStyleColorHeader(cells, cell);
		 
		 j++;
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("%/ Doanh số");	
		 this.setStyleColorHeader(cells, cell);
		 
		 j++;
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("% Đạt");	
		 this.setStyleColorHeader(cells, cell);
		 
		 j++;
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("Cùng kỳ");	
		 this.setStyleColorHeader(cells, cell);
		 
		 j++;
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("%/ Doanh số");	
		 this.setStyleColorHeader(cells, cell);
		 j++;
		 cell = cells.getCell(htb.get(j)+String.valueOf(index));
		 cell.setValue("Tăng/giảm");	
		 this.setStyleColorHeader(cells, cell);
		 
		 index++;
//		 double totalngansach=0;
//		 double totalnamtruoc=0;
		 if (rs != null) 
		 {
			try 
			{
				
				while (rs.next())
				{		
					if(rs.getInt("stt")==1){
						index=8;
						//truyen vao mang doanh so
						 for(int i=0;i<mang.length;i++){
							 mangDS[i]=rs.getDouble(mang[i]);
							 
						 }
						 j=mang.length;
						 mangDS[j]=rs.getDouble("doanhthudutoan");
						 mangDS[j+1]=rs.getDouble("dspre");
						
						 this.setValues(mangDS,cells,index,"","0");
						 
					}else if(rs.getInt("stt")==2){
						index=9;
						//truyen vao mang doanh so
						for(int i=0;i<mang.length;i++){
							 mangTRAVE[i]=rs.getDouble(mang[i]);
							
						 }
						 j=mang.length;
						 mangTRAVE[j]=rs.getDouble("doanhthudutoan");
						 mangTRAVE[j+1]=rs.getDouble("dspre");
						 this.setValues(mangTRAVE,cells,index,"","0");
				 
					}else if(rs.getInt("stt")==3){
						index=11;
						//truyen vao mang doanh so
						for(int i=0;i<mang.length;i++){
							 mangGIAVON[i]=rs.getDouble(mang[i]);
						
						 }
						 j=mang.length;
						 mangGIAVON[j]=rs.getDouble("doanhthudutoan");
						 mangGIAVON[j+1]=rs.getDouble("dspre");
						
					}
					
									
				}
				// total
				
				//truyen vao mang dtthuan
				for(int k=0;k<mangDS.length;k++){
					mangDTTHUAN[k]=mangDS[k]-mangTRAVE[k];
					mangLAIGOP[k]=mangDTTHUAN[k]-mangGIAVON[k];
					
				}
				 this.setValues(mangDTTHUAN,cells,10,"","1");
				 this.setValues(mangGIAVON,cells,index,"","0");
				 this.setValues(mangLAIGOP,cells,12,"","1");
				 
				// lay phan chi phi
				 
					sql="SELECT	LNS.PK_SEQ AS LNSID, LNS_CHIPHI.DONVITHUCHIEN_FK AS DVTHID, "+										
	                " LNS.NAM, NHOMCHIPHI.PK_SEQ AS CPID, NHOMCHIPHI.TEN AS CPTEN ,NAMTRUOC.CHIPHI AS CPNAMTRUOC "+ 							 			
	                " ,CHIPHI.*, "+										
	                " DUTOAN AS NGANSACH "+										
	                " FROM ERP_LAPNGANSACH_CHIPHI LNS_CHIPHI "+											
	                " INNER JOIN ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = LNS_CHIPHI.LAPNGANSACH_FK "+											
	                " INNER JOIN ERP_NHOMCHIPHI NHOMCHIPHI ON NHOMCHIPHI.PK_SEQ = LNS_CHIPHI.CHIPHI_FK "+
	                " LEFT JOIN (" +
	                " 	SELECT CHIPHI_FK AS CPID, SUM(SOLUONGNHAN*DONGIA) AS CHIPHI "+										
	                " FROM ERP_NHANHANG_SANPHAM NH_SP "+										
	                " INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = NH_SP.NHANHANG_FK "+										
	                " WHERE NH_SP.CHIPHI_FK IS NOT NULL AND NH.TRANGTHAI IN (1,2)   "+
	                " AND  NH.NGAYNHAN >='"+tungaypre+"' AND NH.NGAYNHAN <='"+toingaypre+"' "+	
	                " GROUP BY NH_SP.CHIPHI_FK	 " +
	                ") AS NAMTRUOC ON NAMTRUOC.CPID= NHOMCHIPHI.PK_SEQ" +
	                " LEFT JOIN ( "+
	                " SELECT  CHIPHI_FK AS CPID, SUM(SOLUONGNHAN*DONGIA) AS CHIPHI ,SUBSTRING(NH.NGAYNHAN, 0, 8) AS THANG "+ 										
	                " FROM ERP_NHANHANG_SANPHAM NH_SP										 "+
	                " INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = NH_SP.NHANHANG_FK "+										
	                " WHERE NH_SP.CHIPHI_FK IS NOT NULL AND NH.TRANGTHAI IN( 1,2) 		 "+
	                " AND  NH.NGAYNHAN >='"+tungay+"' AND NH.NGAYNHAN <='"+toingay+"' "+							
	                " GROUP BY NH_SP.CHIPHI_FK,SUBSTRING(NH.NGAYNHAN, 0, 8) "+ 
	                " ) P "+
	                " PIVOT  "+
	                " (  "+
	                " sum(CHIPHI) "+ 
	                " FOR THANG IN  "+
	                " ("+chuoingoac+")  "+
	                " ) CHIPHI ON CHIPHI.CPID= NHOMCHIPHI.PK_SEQ "+ 
	                " WHERE 1=1 AND LNS.NAM = '"+obj.getToYear()+"'";
					if(obj.getErpCongtyId().length()>0){
						sql=sql +" and LNS.congty_fk="+ obj.getErpCongtyId();
					}
					if(obj.getErpDonViTHId().length()>0){
						sql=sql +" and	LNS_CHIPHI.DONVITHUCHIEN_FK = '"+obj.getErpDonViTHId()+"'";
					}
					
					System.out.println("SQL : "+sql);
					
					index=14;
					rs=db.get(sql);
					if(rs!=null){
						while (rs.next())
						{
							
							//truyen vao mang doanh so
							 for(int i=0;i<mang.length;i++){
								 mangChiPhi[i]=rs.getDouble(mang[i]);
								 mangChiPhiTotal[i]=	 mangChiPhiTotal[i]+rs.getDouble(mang[i]);
							 }
							 j=mang.length;
							 mangChiPhi[j]=rs.getDouble("NGANSACH");
							 mangChiPhiTotal[j]=	 mangChiPhiTotal[j]+rs.getDouble("NGANSACH");
							 mangChiPhi[j+1]=rs.getDouble("CPNAMTRUOC");
							 mangChiPhiTotal[j+1]=	 mangChiPhiTotal[j+1]+rs.getDouble("CPNAMTRUOC");
							 
							 
							 this.setValues(mangChiPhi,cells,index,"-"+rs.getString("CPTEN"),"0");
							 
							 index++;
						}
					}
					 this.setValues(mangChiPhiTotal,cells,13,"Tổng chi phí","1");
					 
					 for(int k=0;k<mangDS.length;k++){
						 mangLoiNhuan[k]=mangLAIGOP[k]-mangChiPhiTotal[k];	
						}
					 this.setValues(mangLoiNhuan,cells,index,"Tổng lợi nhuận kế toán trước thuế ","1");
					 
				if (rs != null){
					rs.close();
				}
				
			}
			catch(Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		}
		else
		{
			return false;
		}	
		if(db != null)
			db.shutDown();
		return true;
	}
	private void setValues(double[] mangDS, Cells cells,int index,String diengiai,String type) {
		Hashtable<Integer, String> htb=this.htbValueCell();
		Cell	cell = cells.getCell("B"+String.valueOf(index));
		if(!diengiai.equals("")){
		
		cell.setValue(diengiai);	
		this.setStyleColorNormar(cells, cell,type);
		}
			cell = cells.getCell("A"+String.valueOf(index));
		cell.setValue(index-7);	
		this.setStyleColorNormar(cells, cell,type);
	
		double total=0;
		for(int i=0;i<mangDS.length-2;i++){
				cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
			cell.setValue(mangDS[i]);	
			this.setStyleColorNormar(cells, cell,type);
			total=total+mangDS[i];
		}
		//luy ke
		int i=mangDS.length-2;
			cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
		cell.setValue(total);	
		this.setStyleColorNormar(cells, cell,type);
		//nam truoc
		i=mangDS.length+3;
		
		cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
		double namtruoc=mangDS[mangDS.length-1];
		cell.setValue(namtruoc);	
		this.setStyleColorNormar(cells, cell,type);
		
		//ngan sach
		
		i=mangDS.length;
		
		cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
		double ngansach=mangDS[mangDS.length-2];
		cell.setValue(ngansach);	
		this.setStyleColorNormar(cells, cell,type);
		// %dat
		double dat=0;
		if(mangDS[mangDS.length-2] >0){
		 dat=total/mangDS[mangDS.length-2];
		}
		i=mangDS.length+2;
		cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
		cell.setValue(dat);	
		this.setStyleColorNormar(cells, cell,type);
		
		//tang giam
		double tanggiam=0;
		if(namtruoc >0){
			tanggiam=total*100 /namtruoc;
		}
		i=mangDS.length+5;
		cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
		cell.setValue(tanggiam);	
		this.setStyleColorNormar(cells, cell,type);
		if(index >=11){
			//thuc hien tinh phan tram doanh so lan 1
			 i=mangDS.length-2;
		
			cell = cells.getCell(htb.get(i+3)+String.valueOf(10));
			System.out.println("Ngan Sach Thuan"+cell.getValue());
			
			double dsthuan=0;
			try{
			dsthuan	=Double.parseDouble(cell.getValue().toString());
			}catch(Exception er){
				
			}
			double phantram=0;
			if(dsthuan>0){
				phantram=100*total /dsthuan;
			}
			i=mangDS.length-1;
			cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
			cell.setValue(phantram);
			this.setStyleColorNormar(cells, cell,type);
			//lan 2
			
			 i=mangDS.length;
				
				cell = cells.getCell(htb.get(i+3)+String.valueOf(10));
				System.out.println("Ngan Sach Thuan"+cell.getValue());
				
				 dsthuan=0;
				try{
				dsthuan	=Double.parseDouble(cell.getValue().toString());
				}catch(Exception er){
					
				}
				 phantram=0;
				if(dsthuan>0){
					phantram=100*ngansach /dsthuan;
				}
				i=i+1;
				cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
				cell.setValue(phantram);
				this.setStyleColorNormar(cells, cell,type);
			//lan 3
				 i=mangDS.length+3;
					
					cell = cells.getCell(htb.get(i+3)+String.valueOf(10));
					System.out.println("Ngan Sach Thuan"+cell.getValue());
					
					 dsthuan=0;
					try{
					dsthuan	=Double.parseDouble(cell.getValue().toString());
					}catch(Exception er){
						
					}
					 phantram=0;
					if(dsthuan>0){
						phantram=100*namtruoc /dsthuan;
					}
					i=i+1;
					cell = cells.getCell(htb.get(3+i)+String.valueOf(index));
					cell.setValue(phantram);
					this.setStyleColorNormar(cells, cell,type);
		}
		
	}
	private String getquery(IStockintransit obj,String chuoingoac) {
		String tungay=obj.getToYear() +"-"+ (obj.getFromMonth().length() > 1?obj.getFromMonth():"0"+obj.getFromMonth())+"-01";
		String toingay=obj.getToYear() +"-"+ (obj.getToMonth().length() > 1?obj.getToMonth():"0"+obj.getToMonth())+"-31";
		int nampre=Integer.parseInt( obj.getToYear())-1;
		String tungaypre=nampre +"-"+ (obj.getFromMonth().length() > 1?obj.getFromMonth():"0"+obj.getFromMonth())+"-01";
		String toingaypre=nampre +"-"+ (obj.getToMonth().length() > 1?obj.getToMonth():"0"+obj.getToMonth())+"-31";

		String sql=" SELECT 1 AS STT,A.TYPE,DOANHSO.*,NS.DOANHTHUDUTOAN,DSPRE.DOANHSO AS DSPRE "+
			" FROM  "+
			" (SELECT N'Doanh thu bán hàng và cung cấp dịch vụ' AS TYPE ,"+obj.getErpCongtyId()+" AS CONGTY_FK ) "+
			" A  "+
			" LEFT JOIN  "+
			" ( "+
			" SELECT CONGTY_FK,SUM(TONGTIENBVAT) AS DOANHSO ,SUBSTRING(NGAYXUATHD,1,7) AS THANG FROM ERP_HOADON HD  "+
			" WHERE TRANGTHAI=1 AND HD.CONGTY_FK="+obj.getErpCongtyId()+" AND NGAYXUATHD >='"+tungay+"' AND NGAYXUATHD <='"+toingay+"' "+
			" AND ISNULL(LOAIHD,'1')=1 "+
			"  GROUP BY SUBSTRING(NGAYXUATHD,1,7),CONGTY_FK "+
			" ) P  PIVOT    "+
			" (   sum(DOANHSO)  "+
			" FOR THANG IN    "+
			" ("+chuoingoac+")   "+
			" )  "+
			" DOANHSO ON A.CONGTY_FK=DOANHSO.CONGTY_FK "+
			" LEFT JOIN ERP_LAPNGANSACH NS ON NS.CONGTY_FK=DOANHSO.CONGTY_FK "+
			" LEFT JOIN "+ 
			" ( "+
			" SELECT CONGTY_FK,SUM(TONGTIENBVAT) AS DOANHSO  FROM ERP_HOADON HD  "+
			" WHERE TRANGTHAI=1 AND HD.CONGTY_FK="+obj.getErpCongtyId()+" AND NGAYXUATHD >='"+tungaypre+"' AND NGAYXUATHD <='"+toingaypre+"' "+
			" GROUP BY CONGTY_FK "+
			" ) DSPRE ON DSPRE.CONGTY_FK =DOANHSO.CONGTY_FK "+
			" WHERE NS.NAM="+obj.getToYear()+" AND NS.CONGTY_FK="+obj.getErpCongtyId()+
			" UNION ALL "+
			" SELECT 2 AS STT, A.TYPE ,DOANHSO.*,0 AS DOANHTHUDUTOAN,0  AS DSPRE FROM ( "+
			" SELECT  N'Các khoản giảm trừ doanh thu' AS TYPE ,"+obj.getErpCongtyId()+" AS CONGTY_FK "+
			" ) A  "+
			" LEFT JOIN  ( "+
			" SELECT CONGTY_FK,SUM(TONGTIENBVAT) AS DOANHSO ,SUBSTRING(NGAYXUATHD,1,7) AS THANG FROM ERP_HOADON HD  "+
			" WHERE TRANGTHAI=1 AND HD.CONGTY_FK="+obj.getErpCongtyId()+" AND NGAYXUATHD >='"+tungay+"' AND NGAYXUATHD <='"+toingay+"' "+
			" AND ISNULL(LOAIHD,'1')=2 "+
			"  GROUP BY SUBSTRING(NGAYXUATHD,1,7),CONGTY_FK "+
			" ) P  PIVOT    "+
			" (   sum(DOANHSO)  "+
			"  FOR THANG IN    "+
			" ("+chuoingoac+")   "+
			" )  "+
			" DOANHSO ON DOANHSO.CONGTY_FK=A.CONGTY_FK "+
			" UNION ALL  "+
			" SELECT 3 AS STT, A.TYPE,VON.*, NS.GIAVONDUTOAN,NAMTRUOC.DOANHSO FROM ( "+
			" SELECT N'Giá vốn hàng bán' AS TYPE ,"+obj.getErpCongtyId()+" AS CONGTY_FK "+
			" ) A LEFT JOIN ERP_LAPNGANSACH NS ON NS.CONGTY_FK= A.CONGTY_FK "+
			" LEFT JOIN "+ 
			" ( "+
			" select CONGTY_FK,SUM(SOLUONG* ISNULL(GV.GIAVON,HDSP.DONGIA)) AS DOANHSO,SUBSTRING(NGAYXUATHD,1,7) AS THANG "+
			" from ERP_HOADON HD "+
			" inner join ERP_HOADON_SP HDSP ON HD.PK_SEQ=HDSP.HOADON_FK "+
			" LEFT JOIN "+ 
			" ( "+
			" select THANG,NAM,SANPHAM_FK,GIAVON from ERP_TINHGIAVON GV  "+
			" INNER JOIN ERP_TINHGIAVON_SANPHAM GVSP ON GV.PK_SEQ= GVSP.TINHGIAVON_FK "+
			" ) GV ON GV.NAM=SUBSTRING(HD.NGAYXUATHD,1,4) AND GV.THANG = SUBSTRING(HD.NGAYXUATHD,6,2) "+
			" AND GV.SANPHAM_FK=HDSP.SANPHAM_FK "+
			" WHERE HD.TRANGTHAI=1 AND HD.CONGTY_FK="+obj.getErpCongtyId()+" AND  "+
			" HD.NGAYXUATHD >='"+tungay+"' AND HD.NGAYXUATHD <='"+toingay+"' "+
			" GROUP BY SUBSTRING(NGAYXUATHD,1,7),CONGTY_FK "+
			" ) "+
			" P  PIVOT "+   
			" (   sum(DOANHSO)  "+
			" FOR THANG IN    "+
			" ("+chuoingoac+") "+
			" ) AS VON ON VON.CONGTY_FK=A.CONGTY_FK "+
			" LEFT JOIN "+ 
			" ( "+
			" select CONGTY_FK,SUM(SOLUONG* ISNULL(GV.GIAVON,HDSP.DONGIA)) AS DOANHSO "+
			" from ERP_HOADON HD "+
			" inner join ERP_HOADON_SP HDSP ON HD.PK_SEQ=HDSP.HOADON_FK "+
			" LEFT JOIN  "+
			" ( "+
			" select THANG,NAM,SANPHAM_FK,GIAVON from ERP_TINHGIAVON GV  "+
			" INNER JOIN ERP_TINHGIAVON_SANPHAM GVSP ON GV.PK_SEQ= GVSP.TINHGIAVON_FK "+
			" ) GV ON GV.NAM=SUBSTRING(HD.NGAYXUATHD,1,4) AND GV.THANG = SUBSTRING(HD.NGAYXUATHD,6,2) "+
			" AND GV.SANPHAM_FK=HDSP.SANPHAM_FK "+
			" WHERE HD.TRANGTHAI=1 AND HD.CONGTY_FK="+obj.getErpCongtyId()+" AND  "+
			" HD.NGAYXUATHD >='"+tungaypre+"' AND HD.NGAYXUATHD <='"+toingaypre+"' "+
			" GROUP BY CONGTY_FK "+
			" ) NAMTRUOC ON NAMTRUOC.CONGTY_FK=A.CONGTY_FK  "+
			" WHERE NS.NAM="+obj.getToYear()+" AND NS.CONGTY_FK="+obj.getErpCongtyId();
		return sql;
	}	


}

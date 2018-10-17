package geso.traphaco.erp.beans.buttoantonghopupload.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahang_Giay;
import geso.traphaco.erp.beans.donmuahang.INgaynhan;
import geso.traphaco.erp.beans.donmuahang.ISanpham;
import geso.traphaco.erp.beans.donmuahang.imp.*;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ButToanTongHopUpload
{
	private String id;
	private String userId;
	private String nppId;
	private String congTyId;
	private String maChiNhanh;
	private String chiNhanhId;
	private String dienGiai;
	private String trangThai;
	
	private String msg;

	private List<ButToanTongHopUploadItem> itemList;
	private List<String> sheetNames;
	private dbutils db;
	
	public ButToanTongHopUpload()
	{
		this.id = "";
		this.userId = "";
		this.nppId = "1";
		this.congTyId = "";
		this.maChiNhanh = "";
		this.chiNhanhId = "1";
		this.dienGiai = "";
		this.trangThai = "0";
		
		this.msg = "";

		this.setItemList(new ArrayList<ButToanTongHopUploadItem>());
		this.sheetNames = new ArrayList<String>();
		this.db = new dbutils();
	}
	
	public void init()
	{
	}
	
	public int readExcelFile(ServletOutputStream out, String fileName)
	{
		int result = 0;
		try {
			 result= this.readXLSXFile(fileName,out);
			 
			
		} catch (IOException e) {
			 
			e.printStackTrace();
			if (this.msg.trim().length() <= 0)
				this.msg = "REF1.1 Lỗi đọc file";
		}
		
		return result;
	}
	
	public boolean createBTTH()
	{ 
		return true;
	}
	
	public boolean delete(String id)
	{ 
		return true;
	}
	int[] mang_doc =new int[40];
	
	private int readXLSXFile(String fileName, ServletOutputStream out) throws IOException
	{
			FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		System.out.println("fileName: " + fileName);
 
		System.out.println("fileName: " + fileName);
		fstream = new FileInputStream(new File(fileName));
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007);
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet_out = worksheets.getSheet(0);
		
		String query="delete TBL_MUAHANG_UPLOAD ";
		db.update(query);
		int[] mang=new int[40];
		for(int i=0;i<40;i++){
			mang[i]=i;
		}
		
		this.itemList.clear();
		this.sheetNames.clear();
		InputStream ExcelFileToRead = null;
		try {
			
			boolean Error_=false;
			
			ExcelFileToRead = new FileInputStream(fileName);
			
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			//int sosheet=wb.getNumberOfSheets();
			int sosheet=1;
			for(int ii = 0; ii < sosheet; ii++)
			{
				XSSFSheet sheet = wb.getSheetAt(ii);
				
				String sheetName = sheet.getSheetName();
				
				if(!wb.isSheetHidden(ii))
				{
					org.apache.poi.ss.usermodel.Row row;
					Iterator<?> rows = sheet.rowIterator();
					int rowIndex = 1;
					boolean isContinue = false;
					this.sheetNames.add(sheetName);
					
					
					while (rows.hasNext()) 
					{
						this.reset_mang();
						
						row = (org.apache.poi.ss.usermodel.Row)rows.next();
						if(rowIndex>=5){
							String soChungTu = getContent(row, 3);
							if (soChungTu.trim().length() <= 0){
								isContinue = false;
							}else{
								String LOAI = getContent(row, 0);
								String NGAYMUAHANG= getDateString(row, 1);
								String SOCHUNGTU = getContent(row, 2);
								String MANCC = getContent(row, 3);
								String TENNCC = getContent(row, 4);
								String DONVITHUCHIEN= getContent(row, 5);
								String LOAIHANGHOA = getContent(row, 6).equals("CPDV")? "CP": getContent(row, 6);
								String MASP = getContent(row, 7);
								String TENSP = getContent(row, 8);
								String DONVITINH = getContent(row, 9);
								double SOLUONG  =getNumberContent(row, 10,worksheet_out);
								double DONGIA =getNumberContent(row, 11,worksheet_out);
								String NGAYNHAN =getDateString(row, 12);
								String THOIHANTT =getDateString(row, 13);
								String GHINHANCONGNO  =getContent(row, 14);
								String TIENTE  =getContent(row, 15);
								double TIGIA =getNumberContent(row, 16,worksheet_out);
								double DUNGSAI =getNumberContent(row, 17,worksheet_out);
								String  HINHTHUCTHANHTOAN	=getContent(row, 18);
								String DIADIEMGIAOHANG =getContent(row, 19);
								String SOTHAMCHIEU =getContent(row, 20);
								String DIENGIAI=getContent(row,21);
								String cangdi =getContent(row, 22);
								String cangden =getContent(row, 23);
								String TRANS=getContent(row, 24);
								String PART=getContent(row, 25);
								String Delivery =getContent(row, 26); 
								String Paymet =getContent(row, 27);
								
								double VAT=getNumberContent(row, 28,worksheet_out);
								String IS_GIACONG=getContent(row, 29);
								String Ishopdong=getContent(row, 30);
								String SoHopDong=getContent(row, 31);
								String TENNHANHAPKHAU=getContent(row, 32);
								String TENNHASANXUAT=getContent(row, 33);
								String NGAYSHIP=getDateString(row, 34);
								String NGAYNHAPKHO=getDateString(row, 35);
								String LoadPack=getContent(row, 36);
								
								System.out.println("IS_GIACONG: "+IS_GIACONG);
								
								if(IS_GIACONG==null){
									IS_GIACONG="";
								}
								if(IS_GIACONG.equals("1")){
									IS_GIACONG="1";
								}else{
									IS_GIACONG="0";
								}
								
								if(Ishopdong==null){
									Ishopdong="";
								}
								
								if(Ishopdong.equals("HD")){
									Ishopdong="0";
								}else{
									Ishopdong="1";
								}
								
								  query=		" INSERT INTO TBL_MUAHANG_UPLOAD (LOAI,	NGAYMUAHANG ,	SOCHUNGTU  ,	MANCC ,	TENNCC,	DONVITHUCHIEN ,	LOAIHANGHOA,	MASP ,	TENSP , "+
												" DONVITINH ,	SOLUONG,	DONGIA ,	NGAYNHAN,	THOIHANTT ,	GHINHANCONGNO,	TIENTE ,	TIGIA ,	DUNGSAI ,	HINHTHUCTHANHTOAN	,	DIADIEMGIAOHANG, "+
												" SOTHAMCHIEU,	DIENGIAI,	cangdi ,	cangden ,	TRANS,	PART ,	Delivery ,	 	Paymet ,VAT ,IS_GIACONG,ishopdong,TENHOPDONG,TENNHANHAPKHAU,TENNHASANXUAT,NGAYSHIP,NGAYNHAPKHO,LoadPack,row_num ) VALUES "+
												" ('"+LOAI+"',	'"+NGAYMUAHANG+"' ,	'"+SOCHUNGTU+"'  ,	'"+MANCC+"' ,	N'"+TENNCC+"',	N'"+DONVITHUCHIEN+"' ,	N'"+LOAIHANGHOA+"',	'"+MASP+"' ,	N'"+TENSP+"' , "+
												"N'"+ DONVITINH+"' ,	'"+SOLUONG+"',	'"+DONGIA +"',	'"+NGAYNHAN+"',	'"+THOIHANTT +"',	'"+GHINHANCONGNO+"',	'"+TIENTE+"' ,	'"+TIGIA +"','"+DUNGSAI+"' ,	'"+HINHTHUCTHANHTOAN+"'	,	'"+DIADIEMGIAOHANG+"', "+
												"'"+ SOTHAMCHIEU+"',	N'"+DIENGIAI+"',	N'"+cangdi +"',	N'"+cangden +"',	'"+TRANS+"',	'"+PART+"' ,	'"+Delivery+"' ,	 	'"+Paymet+"',"+VAT+",'"+IS_GIACONG+"','"+Ishopdong+"',N'"+SoHopDong+"',N'"+TENNHANHAPKHAU+"',"
												+ "N'"+TENNHASANXUAT+"',N'"+NGAYSHIP+"','"+NGAYNHAPKHO+"','"+LoadPack+"','"+rowIndex+"')";
								  System.out.println(query);
								if(!db.update(query)){
									Error_=true;
									 
									this.Capnhat_FileError(worksheet_out,rowIndex-1,mang,"Không insert được cả dòng hàng ");
									
								}
								
								// kiểm tra lúc đọc dữ liệu vào mà bị lôi 
								 this.Capnhat_DongLoi(rowIndex);
								
							}
						}
						rowIndex++;
					}
					//
					 boolean bien_= this.KiemTraLoi_Excel(worksheet_out);
					
					 boolean bien1_=this.ImportDonmuahang(worksheet_out); 
					
					query="select COUNT(*) as count from TBL_MUAHANG_UPLOAD WHERE LOI_DONG=1 ";
					ResultSet rs=db.get(query);
					int bien=0;
					if(rs.next()){
						bien=rs.getInt("count");
					}rs.close();
					
					if(bien >0){
						workbook.save(out);
						this.msg = "Nhận file lỗi ,những ô lỗi tô màu vàng ";
						return 1;
						
					}
					
				}
			}
			fstream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "RXF1.1 Lỗi đọc file";
			return 1;
		} finally {
			if (ExcelFileToRead != null) {
				ExcelFileToRead.close();
			}
		}
		
		return 0;
	}
	

	private void Capnhat_DongLoi(int row) {
		// TODO Auto-generated method stub
		for(int i=0;i<mang_doc.length;i++){
			if(mang_doc[i]>0){
				String query="update TBL_MUAHANG_UPLOAD set LOI_DONG=1  WHERE row_num="+row;
				System.out.println("vao day nek "+query);
				db.update(query);
			}
			
		}
	}

	private void reset_mang() {
		// TODO Auto-generated method stub
		for(int i=0;i<mang_doc.length;i++){
			mang_doc[i]=0;
			
		}
	}

	private boolean ImportDonmuahang(Worksheet worksheet_out) {
		// TODO Auto-generated method stub
		 boolean insert_khongthanhcong=false;
		 
		try{
			NumberFormat formatter = new DecimalFormat("#######");
			int[] mang=new int[40];
			for(int i=0;i<40;i++){
				mang[i]=i;
			}
			
			
			String query=	  " SELECT  isnull(IS_GIACONG,'0') as IS_GIACONG ,NCC.DVKD_FK ,A.VAT ,case A.LOAIHANGHOA WHEN 'TS' THEN 1 WHEN 'CP' THEN 2   ELSE 0 END AS LOAIHH," +
								" CASE WHEN A.LOAI='TN' THEN 1 WHEN A.LOAI='NK' THEN 0 ELSE 2 END AS LOAI , TT.PK_SEQ AS TIENTE_FK, TS.PK_SEQ AS TAISAN_FK,SP.PK_SEQ AS SANPHAM_FK, NCP.PK_SEQ AS CHIPHI_FK,DV.PK_SEQ AS DVDL_FK, NCC.PK_SEQ AS NCC_FK , DVTH.PK_SEQ AS DONVITHUCHIEN_FK  " + 
							  " ,A.LOAI,A.TENHOPDONG,A.TENNHANHAPKHAU,A.TENNHASANXUAT,A.NGAYSHIP,A.NGAYNHAPKHO,A.LoadPack,	NGAYMUAHANG ,	SOCHUNGTU  ,	MANCC ,	TENNCC,	DONVITHUCHIEN ,	A.LOAIHANGHOA,	MASP ,	TENSP ,    " + 
							  "  dv.pk_seq  as DONVITINH ,dv.DIENGIAI as DDDVT,	A.SOLUONG,	A.DONGIA ,	NGAYNHAN,	THOIHANTT ,	GHINHANCONGNO,	TIENTE ,	TIGIA ,	DUNGSAI ,	HINHTHUCTHANHTOAN	,	DIADIEMGIAOHANG,   " + 
							  " SOTHAMCHIEU,A.ishopdong,	A.DIENGIAI,	 CANGDI.PK_SEQ AS CANGDI ,CANGDEN.PK_SEQ AS	CANGDEN ,	TRANS,	PART ,	DELIVERY ,	 	isnull(PAYMET,'') as PAYMET,	ROW_NUM FROM TBL_MUAHANG_UPLOAD A   " + 
							  " LEFT JOIN  ERP_SANPHAM SP ON SP.CONGTY_FK= "+this.congTyId+" AND SP.MA=A.MASP  " + 
							  " LEFT JOIN  ERP_NHACUNGCAP NCC ON NCC.CONGTY_FK= "+this.congTyId+" AND NCC.MA=A.MANCC  " + 
							  " LEFT JOIN DONVIDOLUONG DV ON DV.DONVI=A.DONVITINH  " + 
							  " LEFT JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.CONGTY_FK="+this.congTyId+" AND DVTH.MA=A.DONVITHUCHIEN  " + 
							  " LEFT JOIN ERP_MASCLON TS ON TS.CONGTY_FK="+this.congTyId+" AND  TS.MA=A.MASP  " + 
							  " LEFT JOIN ERP_NHOMCHIPHI NCP ON NCP.NPP_FK='"+this.nppId+"' AND  NCP.TEN=A.MASP   " +
							  " LEFT JOIN ERP_DANHMUCCANG CANGDEN ON  CANGDEN.CONGTY_FK="+this.congTyId+" AND  CANGDEN.MA=A.CANGDEN " +
							  " LEFT JOIN ERP_DANHMUCCANG CANGDI ON  CANGDI.CONGTY_FK="+this.congTyId+" AND  CANGDI.MA=A.CANGDI " +
							  		" LEFT JOIN ERP_TIENTE TT ON TT.MA=A.TIENTE  " + 
							  " WHERE    " + 
							  " SOCHUNGTU NOT IN (SELECT SOCHUNGTU FROM TBL_MUAHANG_UPLOAD WHERE LOI_DONG='1') order by ROW_NUM  ";
			
			 System.out.println("query:  "+query);
			 
			// các chứng từ không bị lỗi thì lấy ra hết
			ResultSet rsdata=db.get(query);
			String sochungtu="";
			IErpDonmuahang_Giay dmhBean= new  ErpDonmuahang_Giay();
			int i=0;
			List<ISanpham> spList = new ArrayList<ISanpham>();
			 String NCC_FK ="";
			 String TENNCC ="";
			 
			 String DONVITHUCHIEN_FK="";
			 String NGAYMUAHANG ="";
			 String GHINHANCONGNO ="";
			 String HINHTHUCTHANHTOAN="";
			 double TIGIA=0;
			 String DIADIEMGIAOHANG="";
			 String SOTHAMCHIEU="";
			 String DIENGIAI ="";
			 String cangden="";
			 String cangdi="";
			 String TIENTE_FK="";
			 String THOIHANTT="";
			 String LOAIHH="";
			 String TRANS="";
			 String PART=""; 
			 String delivery="";
			 String DVKD_FK="";
			 double tongtienavat=0;
			 double tongtienbvat=0;
			 double tienvat=0;
			 String IS_GIACONG="0";
			 String Ishopdong="0";
			 String SoHopDong="";
			 String PAYMET= "";
			 String TENNHANHAPKHAU="";
			 String TENNHASANXUAT="";
			 String NGAYSHIP="";
			 String NGAYNHAPKHO="";
			 String LoadPack="";
			 int row_index=0;
			
			while(rsdata.next()){
				
				if(!rsdata.getString("SOCHUNGTU").equals(sochungtu)){
					// KHỞI TẠO ĐỐI TƯỢNG MỚI
					 
					if(i!=0){
						System.out.println("so dong san pham "+spList.size());
						 dmhBean.setSpList(spList);
						 dmhBean.setTongtienchuaVat(formatter.format(tongtienbvat));
						 dmhBean.setTongtiensauVat(formatter.format(tongtienbvat+tienvat));
						 dmhBean.setVat(formatter.format(tienvat));
						 boolean bien_= dmhBean.createDmh();
						 System.out.println("Loi khi insert don hang: "+dmhBean.getMsg());
						if(!bien_  ){
							insert_khongthanhcong=true;
							this.Capnhat_FileError(worksheet_out, row_index-1, mang, "Không Import được số chứng từ:"+sochungtu +" . Lỗi: "+dmhBean.getMsg());
						}
						
						tongtienbvat=0;
						tienvat=0;
						spList = new ArrayList<ISanpham>();
						
					}
					
					   dmhBean= new  ErpDonmuahang_Giay();
					   dmhBean.setNppId(this.nppId);
					   Ishopdong =rsdata.getString("Ishopdong");
					   sochungtu=rsdata.getString("SOCHUNGTU");
					   row_index=rsdata.getInt("ROW_NUM");
					   DVKD_FK=rsdata.getString("DVKD_FK");
					   NCC_FK =rsdata.getString("NCC_FK");
					   TENNCC=rsdata.getString("TENNCC");
					   DONVITHUCHIEN_FK =rsdata.getString("DONVITHUCHIEN_FK");
					   NGAYMUAHANG =rsdata.getString("NGAYMUAHANG");
					   GHINHANCONGNO =rsdata.getString("GHINHANCONGNO");
					   HINHTHUCTHANHTOAN =rsdata.getString("HINHTHUCTHANHTOAN");
					   TIGIA=rsdata.getDouble("TIGIA");
					   DIADIEMGIAOHANG =rsdata.getString("DIADIEMGIAOHANG");
					   SOTHAMCHIEU =rsdata.getString("SOTHAMCHIEU");
					   DIENGIAI =rsdata.getString("DIENGIAI");
					   cangden=rsdata.getString("cangden");
					   cangdi=rsdata.getString("cangdi");
					   TIENTE_FK=rsdata.getString("TIENTE_FK");
					   THOIHANTT=rsdata.getString("THOIHANTT");
					   
					   LOAIHH=rsdata.getString("LOAIHH");
					   
					   IS_GIACONG=rsdata.getString("IS_GIACONG");
					   SoHopDong=rsdata.getString("TENHOPDONG");
					   TENNHANHAPKHAU=rsdata.getString("TENNHANHAPKHAU");
					   TENNHASANXUAT=rsdata.getString("TENNHASANXUAT");
					   NGAYSHIP=rsdata.getString("NGAYSHIP");
					   NGAYNHAPKHO=rsdata.getString("NGAYNHAPKHO");
					   LoadPack=rsdata.getString("LoadPack");
					   TRANS= (rsdata.getString("TRANS").equals("1")?"1":"0");
					   PART=(rsdata.getString("PART").equals("1")?"1":"0");  
					     delivery= (rsdata.getString("DELIVERY").trim().equals("Vessel")?"0":"1");;
					   
					     if(rsdata.getString("TIENTE").equals("VND")){
					    	 dmhBean.setNguonGocHH("TN");
					     }else{
					    	 dmhBean.setNguonGocHH("NN");
					     }
					     String Paymet=rsdata.getString("Paymet");
					    String[] mangPaymet= Paymet.split(";");
					    String strpaymet="0__0__0__";
					    if(mangPaymet!=null){
					    	String str1="0";
					    	String str2="0";
					    	String str3="0";
					    	for(int p=0;p<mangPaymet.length;p++ ){
					    		if(mangPaymet[p].trim().equals("1")){
					    			str1="1";
					    		}
					    		if(mangPaymet[p].trim().equals("2")){
					    			str2="1";
					    		}
					    		if(mangPaymet[p].trim().equals("3")){
					    			str3="1";
					    		}
					    	}
					    	strpaymet=str1+"__"+str2+"__"+str3+"__";
					    }
					    
					    System.out.println("strpaymet: "+strpaymet);
					 dmhBean.setPaymet(strpaymet);
					 dmhBean.setPaymentArr(strpaymet.split("__"));
					 dmhBean.setLoaiDMH_NK(Ishopdong);
					 
					 dmhBean.setNCC(NCC_FK);
					 dmhBean.setNhacungcapNK(NCC_FK);
					 dmhBean.setNccTen(TENNCC);
					 
					 dmhBean.setDvthId(DONVITHUCHIEN_FK);
					 dmhBean.setNgaymuahang(NGAYMUAHANG);
					 dmhBean.setCongtyId(this.congTyId);
					 dmhBean.setUserId(this.userId);
					 dmhBean.setTienTe_FK(TIENTE_FK);
					 dmhBean.SetTiGiaNguyenTe(Math.round(TIGIA));
					 dmhBean.setTyGiaQuyDoi(Math.round(TIGIA));
					 dmhBean.setDiaDiemGiaoHang(DIADIEMGIAOHANG);
					 dmhBean.setGhiChuGC(DIENGIAI);
					 dmhBean.setThoihanno("0");
					 dmhBean.setHinhThucTT(HINHTHUCTHANHTOAN);
					 dmhBean.setSoThamChieu(SOTHAMCHIEU);
					 dmhBean.setSochungtu(sochungtu);
					 dmhBean.setCangdenId(cangden);
					 dmhBean.setCangdiId(cangdi);
					 String [] thoihanthanhtoan=new String[]{THOIHANTT};
					 dmhBean.setNgayThanhToanArr(thoihanthanhtoan);
					 dmhBean.setSoTienThanhToanArr(new String[]{"0"});
					 dmhBean.setPhanTramThanhToanArr(new String[]{"100"});
					 // mac dinh =1
					 dmhBean.setQuanlycongno("1");
					 
					 dmhBean.setLoai(rsdata.getString("LOAI"));
					 dmhBean.setLoaihanghoa(LOAIHH);
					 dmhBean.setTrangthai("0");
					 dmhBean.setTrans(TRANS);
					 dmhBean.setPart(PART);
					 dmhBean.setDelivery(delivery);
					 
					 dmhBean.setDvkd(DVKD_FK);
					 dmhBean.setIsdontrahang("0");
					 dmhBean.setIsGiaCong(IS_GIACONG);
					 dmhBean.setSOCHUNGTU_UPLOAD(sochungtu);
					 dmhBean.setDungsai(""+rsdata.getString("dungsai"));
					 dmhBean.setSohopdong(SoHopDong);
					 
					 dmhBean.setTennhanhapkhau(TENNHANHAPKHAU);
					 dmhBean.setTennhasanxuat(TENNHASANXUAT);
					 dmhBean.setNgayship(NGAYSHIP);
					 dmhBean.setNgaynhapkho(NGAYNHAPKHO);
					 dmhBean.setLoadpack(LoadPack);	  
				}
				
				 String SANPHAM_FK=rsdata.getString("SANPHAM_FK");
				 String CHIPHI_FK=rsdata.getString("CHIPHI_FK");
				 String TAISAN_FK=rsdata.getString("TAISAN_FK");
				 String DVDL_FK=rsdata.getString("DVDL_FK");
				 String DONVITINH=rsdata.getString("DONVITINH");
				 String NGAYNHAN=rsdata.getString("NGAYNHAN");
				 double soluong=rsdata.getDouble("soluong");
				 double dongia=rsdata.getDouble("dongia");
				 double vat=rsdata.getDouble("vat");
				 
				 tongtienbvat+=soluong*dongia;
				 tienvat+=(soluong*dongia)*vat/100 ;
				 
				 
				 double dungsai=rsdata.getDouble("dungsai");
				 ISanpham sp=new Sanpham();
				 sp.setDongia(""+dongia);
				 sp.setSoluong(""+soluong);
				 if(rsdata.getString("LOAI").equals("2"))
						 sp.setDonvitinh(rsdata.getString("DDDVT"));
				 else
					 	sp.setDonvitinh(DONVITINH);
				 
				 sp.setDungsai(""+dungsai);
				 sp.setPhanTramThue(vat+"");
				 sp.setNgaynhandukien(NGAYNHAN);
				 sp.setThuexuat(vat+"");
				 INgaynhan ngay=new Ngaynhan();
				 ngay.setNgay(NGAYNHAN);
				 ngay.setSoluong(""+soluong);
				 
				 List<INgaynhan> nn = new ArrayList<INgaynhan>();
				 nn.add(ngay);
				 sp.setNgaynhan(nn);
				 
				 String pk_seq=SANPHAM_FK;
				 
				 if(LOAIHH.equals("1")){
					 pk_seq=TAISAN_FK;
				 }else if(LOAIHH.equals("2")){
					 pk_seq=CHIPHI_FK;
				 }
				 
				 sp.setPK_SEQ(pk_seq);
				 sp.setTensanpham(rsdata.getString("tensp"));
				 spList.add(sp);
				 sp.setIdmarquette("NULL");
				 
				i++;
			}
			//sau khi ra ngoài dòng cuối thì insert vào dòng cuối cùng
			
			 dmhBean.setSpList(spList);
			 dmhBean.setTongtienchuaVat(formatter.format(tongtienbvat));
			 dmhBean.setTongtiensauVat(formatter.format(tongtienbvat+tienvat));
			 dmhBean.setVat(formatter.format(tienvat));
			 boolean bien_= dmhBean.createDmh();
			 System.out.println("Loi khi insert don hang: "+dmhBean.getMsg());
			 if(sochungtu.length()>0){
				if(!bien_  ){
					insert_khongthanhcong=true;
					this.Capnhat_FileError(worksheet_out, row_index-1, mang, "Không Import được số chứng từ:"+sochungtu +" . Lỗi: "+dmhBean.getMsg());
				}
			 }
			
			
			rsdata.close();
			
			
		}catch(Exception er){
			er.printStackTrace();
			
		}
		if(insert_khongthanhcong){
			return true;
		}else{
			return false;
		}
		
	}

	private boolean KiemTraLoi_Excel(Worksheet worksheet_out) {
		// TODO Auto-generated method stub
		boolean bien=false;
		
		try{
			// kiểm tra NCC
			String query=" SELECT  row_num -1 as row_num FROM TBL_MUAHANG_UPLOAD  a "+
						 "	WHERE  NOT EXISTS "+
						 " (SELECT PK_SEQ FROM ERP_NHACUNGCAP ncc WHERE ncc.CONGTY_FK="+this.congTyId+" and ncc.MA=a.MANCC)";
			System.out.println(query);
			ResultSet rs=db.get(query);
			while (rs.next()){
				int rowIndex=rs.getInt("row_num");
				//int col_arr=3;
				int[]  col_arr =new int[1];
				col_arr[0]=3;
				this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Mã nhà cung cấp không có  ");
				bien=true;
			}rs.close();
			
			//Kiểm tra NCC
			query=" SELECT  row_num -1 as row_num FROM TBL_MUAHANG_UPLOAD where SOCHUNGTU in  (select  SOCHUNGTU from TBL_MUAHANG_UPLOAD  group by SOCHUNGTU having COUNT(MANCC)>1)";
			System.out.println(query);
			 rs=db.get(query);
			while (rs.next()){
				int rowIndex=rs.getInt("row_num");
				//int col_arr=3;
				int[]  col_arr =new int[1];
				col_arr[0]=3;
				this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Số chứng từ có 2 nhà cung cấp ");
				bien=true;
			}rs.close();
			
			
			
			
			// kiểm tra sản phẩm
			  query=" SELECT  row_num -1 as row_num FROM TBL_MUAHANG_UPLOAD  a "+
			 "	WHERE  NOT EXISTS "+
			 " (SELECT PK_SEQ FROM ERP_sanpham ncc WHERE ncc.CONGTY_FK="+this.congTyId+" and ncc.MA=a.masp) "
			 		+ " AND A.LOAIHANGHOA IN ('SP','VT','CCDC')";
			  System.out.println(query);
			  rs=db.get(query);
			while (rs.next()){
				int rowIndex=rs.getInt("row_num");
				//int col_arr=3;
				int[]  col_arr =new int[1];
				
				col_arr[0]=7;
				this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Mã SP theo loại hàng hóa không tồn tại  ");
				bien=true;
				
			}rs.close();
			// KIỂM TRA TÀI SẢN
			 
			  query= " SELECT  row_num -1 as row_num FROM TBL_MUAHANG_UPLOAD  a "+
					 "	WHERE  NOT EXISTS "+
					 " (SELECT PK_SEQ FROM ERP_MASCLON ncc WHERE ncc.CONGTY_FK="+this.congTyId+" and ncc.MA=a.masp) AND A.LOAIHANGHOA IN ('TS')";
			  
			  System.out.println(query);
			  rs=db.get(query);
			while (rs.next()){
				int rowIndex=rs.getInt("row_num");
				//int col_arr=3;
				int[]  col_arr =new int[1];
				
				col_arr[0]=7;
				this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Lỗi không có mã tài sản ");
				bien=true;
				
			}rs.close();
			// KIỂM TRA CHI PHIS 
			 
			  query= " SELECT  row_num -1 as row_num FROM TBL_MUAHANG_UPLOAD  a "+
					 "	WHERE  NOT EXISTS "+
					 " (SELECT PK_SEQ FROM ERP_NHOMCHIPHI ncc WHERE ncc.CONGTY_FK="+this.congTyId+" and ncc.TEN = a.masp) AND A.LOAIHANGHOA IN ('CPDV')";
			  
			  System.out.println("Chi phi dich vu "+query);
			  rs=db.get(query);
			while (rs.next()){
				int rowIndex=rs.getInt("row_num");
				//int col_arr=3;
				int[]  col_arr =new int[1];
				
				col_arr[0]=7;
				this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Lỗi không có mã chi phí ");
				bien=true;
				
			}rs.close();

			// kiểm tra tiền tệ
			  query=" SELECT  row_num -1 as row_num FROM TBL_MUAHANG_UPLOAD  a WHERE  " +
			  		" NOT EXISTS (SELECT PK_SEQ FROM ERP_TIENTE  tt WHERE    tt.MA=a.tiente)";
			  System.out.println(query);
			  rs=db.get(query);
			while (rs.next()){
				int rowIndex=rs.getInt("row_num");
				//int col_arr=3;
				int[]  col_arr =new int[1];
				
				col_arr[0]=15;
				this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Tiền tệ không tồn tại");
				bien=true;
				
			}rs.close();
			
			// kiểm tra đơn vị tính
			 query= "  SELECT  row_num -1 as row_num FROM TBL_MUAHANG_UPLOAD  a WHERE  " +
			 		"  NOT EXISTS (SELECT PK_SEQ FROM DONVIDOLUONG  ncc WHERE ncc.DONVI= a.donvitinh) AND A.LOAIHANGHOA IN ('SP','VT','CCDC') ";
			 
		  System.out.println(query);
		  rs=db.get(query);
		while (rs.next()){
			int rowIndex=rs.getInt("row_num");
			//int col_arr=3;
			int[]  col_arr =new int[1];
			
			col_arr[0]=9;
			this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Đơn vị đo lường không tồn tại");
			bien=true;
			
		}rs.close();
		
		// kiểm tra loại đơn  
		
		 query= "  SELECT  row_num -1 as row_num FROM TBL_MUAHANG_UPLOAD  a WHERE  " +
		 		"  LOAI NOT IN ('TN','NK','VTCP') ";
		 
		 
	  System.out.println(query);
	  rs=db.get(query);
	  while (rs.next()){
		int rowIndex=rs.getInt("row_num");
		//int col_arr=3;
		int[]  col_arr =new int[1];
		
		col_arr[0]=0;
		this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Loại đơn hàng không đúng ");
		bien=true;
		
		
	  }rs.close();
		
	  // đơn vị thực hiện
		 
		 query= "  SELECT  row_num -1 as row_num FROM TBL_MUAHANG_UPLOAD  a WHERE  " +
		 		"  NOT EXISTS (SELECT PK_SEQ FROM erp_DONVITHUCHIEN  ncc WHERE ncc.MA= a.donvitHUCHIEN AND ncc.CONGTY_FK="+this.congTyId+")   ";
		 
	  System.out.println(query);
	  rs=db.get(query);
		while (rs.next()){
			int rowIndex=rs.getInt("row_num");
			//int col_arr=3;
			int[]  col_arr =new int[1];
			
			col_arr[0]=5;
			this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Đơn vị thực hiện không tồn tại ");
			bien=true;
			
		}rs.close();
		
		// kiểm tra dvkd của sp va ncc
		 
		 query= " SELECT     row_num -1 as row_num "+
				 " FROM TBL_MUAHANG_UPLOAD A  "+
				 " LEFT JOIN  ERP_SANPHAM SP ON SP.CONGTY_FK= "+this.congTyId+" AND SP.MA=A.MASP "+
				 " LEFT JOIN  ERP_NHACUNGCAP NCC ON NCC.CONGTY_FK= "+this.congTyId+" AND NCC.MA=A.MANCC "+
				 " WHERE   A.LOAIHANGHOA IN ('SP','VT','CCDC') AND   "+
				 " SOCHUNGTU NOT IN (SELECT SOCHUNGTU FROM TBL_MUAHANG_UPLOAD WHERE LOI_DONG='1') "+
				 " AND ISNULL(NCC.DVKD_FK,0) <>  ISNULL(  SP.DVKD_FK,0)    ";
		 
		 
	  System.out.println(query);
	  rs=db.get(query);
		while (rs.next()){
			int rowIndex=rs.getInt("row_num");
			//int col_arr=3;
			int[]  col_arr =new int[1];
			
			col_arr[0]=3;
			this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Đơn vị kinh doanh của nhà cung cấp và sản phẩm không giống nhau");
			bien=true;
			
		}rs.close();
	  
	  
		
		 // taif san 
		 query= " SELECT     row_num -1 as row_num "+
				 " FROM TBL_MUAHANG_UPLOAD A  "+
				 " LEFT JOIN  ERP_MASCLON   SP ON SP.CONGTY_FK= "+this.congTyId+" AND SP.MA=A.MASP "+
				 " LEFT JOIN  ERP_NHACUNGCAP NCC ON NCC.CONGTY_FK= "+this.congTyId+" AND NCC.MA=A.MANCC "+
				 " WHERE   A.LOAIHANGHOA='TS'    AND   "+
				 " SOCHUNGTU NOT IN (SELECT SOCHUNGTU FROM TBL_MUAHANG_UPLOAD WHERE LOI_DONG='1') "+
				 " AND ISNULL(NCC.DVKD_FK,0) <>  ISNULL(  SP.DVKD_FK,0)    ";
		 
		 
	  System.out.println(query);
	  rs=db.get(query);
		while (rs.next()){
			int rowIndex=rs.getInt("row_num");
			//int col_arr=3;
			int[]  col_arr =new int[1];
			
			col_arr[0]=3;
			this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Đơn vị kinh doanh của nhà cung cấp và tài sản không giống nhau");
			bien=true;
			
		}rs.close();
	  

		 // chi phi dich
		 query= " SELECT     row_num -1 as row_num "+
				 " FROM TBL_MUAHANG_UPLOAD A  "+
				 " LEFT JOIN  ERP_NHOMCHIPHI   SP ON SP.CONGTY_FK= "+this.congTyId+" AND SP.TEN=A.MASP "+
				 " LEFT JOIN  ERP_NHACUNGCAP NCC ON NCC.CONGTY_FK= "+this.congTyId+" AND NCC.MA=A.MANCC "+
				 " WHERE   A.LOAIHANGHOA='CPDV'    AND   "+
				 " SOCHUNGTU NOT IN (SELECT SOCHUNGTU FROM TBL_MUAHANG_UPLOAD WHERE LOI_DONG='1') "+
				 " AND ISNULL(NCC.DVKD_FK,0) <>  ISNULL(  SP.DVKD_FK,0)    ";
		 
		 
	  System.out.println(query);
	  rs=db.get(query);
		while (rs.next()){
			int rowIndex=rs.getInt("row_num");
			//int col_arr=3;
			int[]  col_arr =new int[1];
			
			col_arr[0]=3;
			this.Capnhat_FileError(worksheet_out, rowIndex, col_arr,"Đơn vị kinh doanh của nhà cung cấp và chi phí không giống nhau");
			bien=true;
			
		}rs.close();
	  
		// kiểm tra nhà cung cấp trong nước tài khoản phải là trong nước
		
		query= " SELECT     row_num -1 as row_num  "+
		" FROM TBL_MUAHANG_UPLOAD A    "+
		" LEFT JOIN  ERP_NHACUNGCAP NCC ON NCC.CONGTY_FK= "+this.congTyId+" AND NCC.MA=A.MANCC "+  
		" WHERE    "+
		" SOCHUNGTU NOT IN (SELECT SOCHUNGTU FROM TBL_MUAHANG_UPLOAD WHERE LOI_DONG='1') "+
		" and NCC.TAIKHOAN_FK not  in (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN ='33111000') "+
		" AND A.LOAI='TN'   ";


		System.out.println(query);
		rs=db.get(query);
		while (rs.next()){
			int rowIndex=rs.getInt("row_num");
			//int col_arr=3;
			int[]  col_arr =new int[1];
			
			col_arr[0]=3;
			this.Capnhat_FileError(worksheet_out, rowIndex, col_arr, "Loại đơn mua hàng trong nước, nhà cung cấp phải là nhà cung cấp trong nước");
			bien=true;
			
		}rs.close();
		
// kiểm tra nhà cung cấp nhập khẩu tài khoản phải là nhập khẩu
		
		query= " SELECT     row_num -1 as row_num  "+
		" FROM TBL_MUAHANG_UPLOAD A    "+
		" LEFT JOIN  ERP_NHACUNGCAP NCC ON NCC.CONGTY_FK= "+this.congTyId+" AND NCC.MA=A.MANCC "+  
		" WHERE    "+
		" SOCHUNGTU NOT IN (SELECT SOCHUNGTU FROM TBL_MUAHANG_UPLOAD WHERE LOI_DONG='1') "+
		" and NCC.TAIKHOAN_FK not  in (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN ='33112000') "+
		" AND A.LOAI='NK'   ";


		System.out.println(query);
		rs=db.get(query);
		while (rs.next()){
			int rowIndex=rs.getInt("row_num");
			//int col_arr=3;
			int[]  col_arr =new int[1];
			
			col_arr[0]=3;
			this.Capnhat_FileError(worksheet_out, rowIndex, col_arr, "Loại đơn mua hàng nhập khẩu, nhà cung cấp phải là nhà cung cấp nhập khẩu");
			bien=true;
			
		}rs.close();
		
		
// kiểm tra nhà cung cấp nhập khẩu tài khoản phải là nhập khẩu ,đon vật tư
		
		query= " SELECT     row_num -1 as row_num  "+
		" FROM TBL_MUAHANG_UPLOAD A    "+
		" LEFT JOIN  ERP_NHACUNGCAP NCC ON NCC.CONGTY_FK= "+this.congTyId+" AND NCC.MA=A.MANCC "+  
		" WHERE    "+
		" SOCHUNGTU NOT IN (SELECT SOCHUNGTU FROM TBL_MUAHANG_UPLOAD WHERE LOI_DONG='1') "+
		" and NCC.TAIKHOAN_FK not  in (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN ='33112000') "+
		" AND A.LOAI='VTCP' and TIENTE!='VND'  ";


		System.out.println(query);
		rs=db.get(query);
		while (rs.next()){
			int rowIndex=rs.getInt("row_num");
			//int col_arr=3;
			int[]  col_arr =new int[1];
			
			col_arr[0]=3;
			this.Capnhat_FileError(worksheet_out, rowIndex, col_arr, "Loại đơn mua hàng VTCP nhập khẩu, nhà cung cấp phải là nhà cung cấp nhập khẩu");
			bien=true;
			
		}rs.close();
		
// kiểm tra nhà cung cấp trong nước tài khoản phải là trong nươc ,đon vật tư
		
		query= " SELECT     row_num -1 as row_num  "+
		" FROM TBL_MUAHANG_UPLOAD A    "+
		" LEFT JOIN  ERP_NHACUNGCAP NCC ON NCC.CONGTY_FK= "+this.congTyId+" AND NCC.MA=A.MANCC "+  
		" WHERE    "+
		" SOCHUNGTU NOT IN (SELECT SOCHUNGTU FROM TBL_MUAHANG_UPLOAD WHERE LOI_DONG='1') "+
		" and NCC.TAIKHOAN_FK not  in (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN ='33111000') "+
		" AND A.LOAI='VTCP' and TIENTE='VND'  ";


		System.out.println(query);
		rs=db.get(query);
		while (rs.next()){
			int rowIndex=rs.getInt("row_num");
			//int col_arr=3;
			int[]  col_arr =new int[1];
			
			col_arr[0]=3;
			this.Capnhat_FileError(worksheet_out, rowIndex, col_arr, "Loại đơn mua hàng VTCP trong nước, nhà cung cấp phải là nhà cung cấp trong nước");
			bien=true;
			
		}rs.close();
		
// kiểm tra số chứng từ đã import rồi
		
		query=  " SELECT     row_num -1 as row_num  "+
				" FROM TBL_MUAHANG_UPLOAD A   "+
				" WHERE   "+
				" SOCHUNGTU NOT IN (SELECT SOCHUNGTU FROM TBL_MUAHANG_UPLOAD WHERE LOI_DONG='1')"+ 
				" AND   EXISTS (SELECT PK_SEQ FROM ERP_MUAHANG B WHERE TRANGTHAI NOT IN (3,4) AND npp_fk="+this.nppId+" AND ISNULL(B.SOCHUNGTU_UPLOAD,'') = A.SOCHUNGTU) ";
	 
		System.out.println(query);
		rs=db.get(query);
		while (rs.next()){
			int rowIndex=rs.getInt("row_num");
			//int col_arr=3;
			int[]  col_arr =new int[1];
			
			col_arr[0]=2;
			this.Capnhat_FileError(worksheet_out, rowIndex, col_arr, "Số chứng từ mua hàng đã tồn tại , vui lòng kiểm tra lại, nếu muốn upload chứng từ phải xóa hoặc hủy chứng từ cũ");
			bien=true;
			
		}rs.close();
		
		
		
		}catch(Exception er){
			er.printStackTrace();
		}
		return bien;
	}

	private void Capnhat_FileError(Worksheet worksheet_out, int rowIndex,int[] col_arr,String diengiailoi) {
		// TODO Auto-generated method stub
		try{
					Cells cells = worksheet_out.getCells();
			 
					for (int j=0; j < col_arr.length; j++) {
						int column=col_arr[j];
						com.aspose.cells.Cell cell = cells.getCell(rowIndex, column);
						
						Style style = cell.getStyle();
						style.setPatternStyle((short) 0);
						style.setColor(Color.YELLOW);
						cell.setStyle(style);
					}
					com.aspose.cells.Cell cell = cells.getCell(rowIndex, 33);
					String diengiai_cu= cell.getStringValue();
					Style style = cell.getStyle();
					style.setPatternStyle((short) 0);
					style.setColor(Color.RED);
					
					cell.setStyle(style);
					cell.setValue(diengiai_cu+ " ; "+diengiailoi);
					String query="update TBL_MUAHANG_UPLOAD  set loi_dong ='1' where row_num="+(rowIndex+1);
					System.out.println("Query : "+query);
					db.update(query);
					
						
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	private boolean layDuLieuIds()
	{ 
		
		return true;
	}

	private boolean kiemTraChungTuTrung(ButToanTongHopUploadItem item) { 
		return true;
	}
	
	private boolean layCongTyChiNhanh(ButToanTongHopUploadItem item) { 
		return true;
	}


	public int kiemTraDuLieu()
	{ 
		return 0;
	}
	
	private String kiemTraPopupHD(ButToanTongHopUploadItem item) { 
		
		return "";
	}

	private String kiemTraNguyenTe(ButToanTongHopUploadItem item) { 
		return "";
	}

	private String kiemTraTaiKhoan(ButToanTongHopUploadItem item) { 
		return "";
	}

	 
	public void DbClose()
	{
		if (this.db != null)
			this.db.shutDown();
	}
	
	private static String getContent(org.apache.poi.ss.usermodel.Row row, int column)
	{
		Cell cell = (Cell) row.getCell(column, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
		String cellContent = "";
		switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            cellContent = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            cellContent = Long.toString((long) cell.getNumericCellValue());
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            cellContent = String.valueOf(cell.getBooleanCellValue());
            break;
		}
		
		return cellContent;
	}
	
	private static String getDateString(org.apache.poi.ss.usermodel.Row row, int column)
	{
		try {
			Cell cell = (Cell) row.getCell(column, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			double d = cell.getNumericCellValue();
			Date date = DateUtil.getJavaDate(d);
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        return df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
//	private static double getNumberContent(org.apache.poi.ss.usermodel.Row row, int column)
//	{
//		double result = 0;
//		try {
//			String str = getContent(row, column);
//			if (str.trim().length() > 0)
//				result = Double.parseDouble(getContent(row, column).replaceAll(",", ""));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
	
	
	
	private   double getNumberContent(org.apache.poi.ss.usermodel.Row row, int column, Worksheet worksheet_out)
	{
		double result = 0;
		int row_index=row.getRowNum();
		int[] col_arr= new int[1];
		col_arr[0]=column;
		try{
			Cell cell = (Cell) row.getCell(column, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
		
			System.out.println("cell.getCellType(): " + cell.getCellType());
			
			if(cell.getCellType() ==1){
				System.out.println("vào đây   :loi :  " + cell.getCellType());
				 this.Capnhat_FileError(worksheet_out, row_index, col_arr, "Định dạng số ô:"+(column+1)+" không đúng ");
				 //nếu có lỗi
				 mang_doc[column]=column;
				 
			}else{
				
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				switch (cell.getCellType()) {
		        case Cell.CELL_TYPE_STRING:
		        	result = Double.parseDouble(cell.getStringCellValue());
		            break;
		        case Cell.CELL_TYPE_NUMERIC:
		        	result = cell.getNumericCellValue();
		        case Cell.CELL_TYPE_FORMULA:
		        	//this.Capnhat_FileError(worksheet_out, row_index, col_arr, "Định dạng số ô:"+(column+1)+" đang là kiểu[CELL_TYPE_FORMULA] không đúng định dạng số");
		            break;
				}
			}
		}catch(Exception er){
			 this.Capnhat_FileError(worksheet_out, row_index, col_arr, "Định dạng số ô:"+(column+1)+" không đúng :"+er.getMessage());
			 mang_doc[column]=column;
		}
		
		return result;
	}
	
	public static boolean isValidDateFormat(String format, String value) {
		if (value == null || value.trim().length() == 0) {
			 return false;
		 }
	        Date date = null;
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat(format);
	            try {
					date = sdf.parse(value);
				} catch (Exception e) {
					e.printStackTrace();
				}
	            if (!value.equals(sdf.format(date))) {
	                date = null;
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return date != null;
	}
	
	public static int getNumberOfDecimalPlaces(double value) {
		 String valueString = Double.toString(value);
		 String arr[] = valueString.split(".");;
		 int result = arr.length;
		 if (result == 1 && arr[1].trim().equals("0"))
			 return 0;
		 return result;
	}
	 
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getMaChiNhanh() {
		return maChiNhanh;
	}

	public void setMaChiNhanh(String maChiNhanh) {
		this.maChiNhanh = maChiNhanh;
	}

	public String getChiNhanhId() {
		return chiNhanhId;
	}

	public void setChiNhanhId(String chiNhanhId) {
		this.chiNhanhId = chiNhanhId;
	}

	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}

	public void setItemList(List<ButToanTongHopUploadItem> itemList) {
		this.itemList = itemList;
	}

	public List<ButToanTongHopUploadItem> getItemList() {
		return itemList;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}

	public String getDienGiai() {
		return dienGiai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setSheetNames(List<String> sheetNames) {
		this.sheetNames = sheetNames;
	}

	public List<String> getSheetNames() {
		return sheetNames;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
package geso.traphaco.erp.servlets.nhanhangsanxuat;
//
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.erp.beans.nhanhang.ISpDetail;
import geso.traphaco.erp.beans.nhanhang.imp.SpDetail;
import geso.traphaco.erp.beans.nhanhangsanxuat.IErpNhanhangList_Giay;
import geso.traphaco.erp.beans.nhanhangsanxuat.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhangsanxuat.imp.ErpNhanhangList_Giay;
import geso.traphaco.erp.beans.nhanhangsanxuat.imp.ErpNhanhang_Giay;
import geso.traphaco.erp.beans.tieuhao.IErpTieuHao;
import geso.traphaco.erp.beans.tieuhao.ISanpham;
import geso.traphaco.erp.beans.tieuhao.imp.ErpTieuHao;
import geso.traphaco.erp.beans.tieuhao.imp.Sanpham;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.erp.util.Library;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhanhangsanxuatSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpNhanhangsanxuatSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpNhanhangList_Giay obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String loaidh = util.antiSQLInspection(request.getParameter("loai"));
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
 
	    String action = util.getAction(querystring);
	    
	    String nhId = util.getId(querystring);
	    
	    String msg = "";
	    String ctyId = "";
	    
	    
	    
	    
	    obj = new ErpNhanhangList_Giay();
	    ctyId = (String)session.getAttribute("congtyId") ;
	    String nppId=(String)session.getAttribute("nppId");
	    obj.setCongtyId(ctyId);
	    obj.setNppId(nppId);
	   // obj.setTrangthai("0");
	    obj.setUserId(userId);
	    obj.setLoaimh(loaidh);
	    
	    if (action.equals("delete"))
	    {	
	    	try
	    	{
		    	dbutils db = new dbutils();
		    	db.getConnection().setAutoCommit(false);
		    	msg = Delete(nhId, db);
		    	if(msg.length() > 0)
		    	{
		    		obj.setmsg(msg);
		    		db.getConnection().rollback();
		    	}
		    	else //xoa thanh cong
		    	{
		    		System.out.println("xoa thanh cong........");
		    		String poId = request.getParameter("poId");
		    		IErpNhanhang_Giay nhanhang = new ErpNhanhang_Giay(nhId);
		    		
		    		//nhanhang.init();
		    		if(!poId.equals("null")){
		    			nhanhang.updateDonmuahang(poId.substring(5, poId.length()));
		    		}
		    	}
		    	db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	    else if(action.equals("chot"))
	    {
						
			String lhhId = request.getParameter("lhhId");
    		if(lhhId == null)
    			lhhId = "";
    		String loai = "2";
    		  
    		msg = ChotNhanHang(nhId, userId, ctyId, lhhId, loai,nppId);
    		session.setAttribute("userId", userId);
    		//obj.setTrangthai("0");	
    		obj.setLoaimh(loai);
			obj.init("");	
			obj.setmsg(msg);
			
	    	session.setAttribute("obj", obj);  	
    		
	
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuat.jsp");
			
    		return;
		     
	    }
	    else if(action.equals("convert"))
	    {
	    	IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nhBean.setNppId((String)session.getAttribute("nppId"));
	    	String nccId = util.antiSQLInspection(request.getParameter("NCCId")); 	    	
	    	nhBean.setNccId(nccId); 
	    	
	    	String hoadonnccId = util.antiSQLInspection(request.getParameter("hoadonnccId"));
	    	nhBean.setHdNccId(hoadonnccId);
	    	
	    	String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
	    	nhBean.setLoaimh(loaihd);
	    	
	    	String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
	    	nhBean.setLoaihanghoa(loaihh);
	    	
	    	nhBean.setNdnId("100000");
	    	nhBean.setLdnId("100046");
	    			    
	    	nhBean.init_convert(hoadonnccId, loaihd);
	    	nhBean.createRs();
    		
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
	    	System.out.println("NCCId gét:"+nhBean.getNccId());
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
    		response.sendRedirect(nextJSP);
    		
    		return;
	    }
     
	    //obj.setTrangthai("0");
	    obj.setmsg(msg);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setNppId((String)session.getAttribute("nppId"));
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());
		session.setAttribute("nppId", obj.getNppId());		
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuat.jsp";
		response.sendRedirect(nextJSP);
	}
	
	Hashtable<String, Hashtable<String, Long> > nhan_form_choSelected = null; 
	
	private String ChotNhanHang (String nhId, String userId, String ctyId, String lhhId, String loaimh,String nppId) 
 {
		dbutils db = new dbutils();

		try {
			db.getConnection().setAutoCommit(false);

			Utility util = new Utility();
			
			String query1 = " select ngaynhan ,ISNULL(ISTUDONG,'0') AS ISTUDONG ,NPP_FK  from erp_nhanhang where pk_seq=" + nhId + " ";

			ResultSet rs1 = db.get(query1);
			String ngaychotNV = "";
			String istudong = "0";
			if (rs1.next()) {
				ngaychotNV = rs1.getString("ngaynhan");
				istudong = rs1.getString("ISTUDONG");
				nppId=rs1.getString("NPP_FK");

			}
			rs1.close();

		/*	String msg1 = util.checkNgayHopLe(db, ngaychotNV);
			if (msg1.length() > 0) {
				db.update("rollback");
				return msg1;
			}*/

			// Sau nay phai them buoc check xem taikhoan do da co khoasothang
			// chua
			String ngaychot = "";
 
			String sql =  " SELECT A.NGAYCHOT, A.KHONHANTP_DAT_FK \n"
						+ " FROM ERP_NHANHANG A  \n" 
						+ " WHERE A.PK_SEQ =  " + nhId;
			 
			String KHONHANTP_DAT_FK="";
			ResultSet rs = db.get(sql);
			if (rs.next()) {
				ngaychot = rs.getString("ngaychot");
				KHONHANTP_DAT_FK= rs.getString("KHONHANTP_DAT_FK");

			}
			rs.close();
			
//			String nam = ngaychot.substring(0, 4);
//			String thang = ngaychot.substring(5, 7);

			String query = "";
			String IdChuyenKho = "";

			Library lib=new Library();
			
			String  msg1=lib.Capnhat_Ngaynhapkho_Nhanhang(userId, db, nhId);
			if(msg1.length()>0){
				db.getConnection().rollback();
				return msg1;
			}
			
			// Nhan hang se chuyen sang hoan tat
			// cap nhat them chuyenkho_hoachat_fk trong bang erp_nhanhang
			IdChuyenKho = "NULL";
			query =   " Update ERP_NhanHang set trangthai = '1', giochot = '" + getTime() + "',chuyenkho_hoachat_fk=" + IdChuyenKho + " "
					+ " where pk_seq = '" + nhId + "' and trangthai = 0 ";
			System.out.println("capnhatrangthai="+query);
			if (db.updateReturnInt(query)!= 1 ) {
				db.getConnection().rollback();
				db.shutDown();
				return "88.Không thể chôt nhận hàng: " + query;
			}
			if (istudong.equals("0")) {

				// Lay tai khoan No trong bang config
				// sua them cac truong de insert xuong bang erp_yeucaukiemdinh
				query = " SELECT  NH.KHONHAN," +
						" sp.kiemtradinhluong,sp.kiemtradinhtinh, sp.BATBUOCKIEMDINH"
						+ " ,NH.MUAHANG_FK,nh.SANPHAM_FK," +
						  " DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, ISNULL(NH.DONVI, '') AS DONVI, "
						+ " SP.MA AS MASP  ,SP.TEN AS SPTEN  "
						+ " FROM ERP_NHANHANG nh1 inner join "
						+ " ERP_NHANHANG_SANPHAM NH on nh1.PK_SEQ = NH.NHANHANG_FK "
						+ " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NH.SANPHAM_FK   "
						+ " INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK "
						+ " WHERE NH.NHANHANG_FK ='" + nhId + "' and  nh.SOLUONGNHAN > 0";

				System.out.println("_____LAY TK: " + query);
				ResultSet rsSp = db.get(query);
//				double total_soluongNhan = 0;

//				String NCC = "";
				while (rsSp.next()) {
					// bổ sung NCC
//					NCC = rsSp.getString("NCC");
					String sanphamId = rsSp.getString("SANPHAM_FK");
					String khoNhanId = rsSp.getString("KHONHAN");
					String muaHangId = "";
					// loc san pham nao co chon kiem dinh dinh luong,kiem dinh
					// dinh tinh,bat buoc kiem dinh
					String dinhluong = rsSp.getString("kiemtradinhluong");
					String dinhtinh = rsSp.getString("kiemtradinhtinh");
					String batbuockiemdinh = rsSp.getString("BATBUOCKIEMDINH");
					
					String dongia = rsSp.getString("DONGIA");
					
					if (dinhluong == null) {
						dinhluong = "";
					}
					if (dinhtinh == null) {
						dinhtinh = "";
					}
					
					if(batbuockiemdinh == null){
						batbuockiemdinh = "0";
					}
					
					// xem xét sản phẩm có kiểm định, thì tăng kho chờ xử lý
					// sản phẩm nào không có kiểm định thì tăng kho được chọn
					if ( batbuockiemdinh.equals("1")) {
						// tạo phiếu kiểm định 
						// đang tạo cho từng sản phẩm
						List<String> maPhieuL = new ArrayList<String>();
						
						String s = CreatePhieuKiemDinh( db,sanphamId, nhId, "", dinhluong, dinhtinh,khoNhanId,userId,loaimh, maPhieuL,dongia,nppId);
						
						if(s.trim().length() > 0){
							db.getConnection().rollback();
							db.shutDown();
							return "88.Không thể chôt nhận hàng, vui lòng báo Admin: không tạo được phiếu kiểm định : "+s;
						}
						
					} else {
						// tăng kho nhận, khonog qua kiem dinh, sau do taoj chuyen kho tuj dong qua
						
						// đang tạo cho từng sản phẩm
						String khoId = khoNhanId;
						String check  = TangKhoKiemDinh(db, khoId ,nhId,sanphamId,muaHangId,"",dongia,userId,ctyId,nppId);
						if(check.length()>0 ){
							db.getConnection().rollback();
							db.shutDown();
							return "99.Không thể chôt nhận hàng, lỗi: "+check;
						}
					 
					}
					// end tao phieu yeu cau kiem dinh
				}
				rsSp.close();

				// tạo phiếu xuất kho hoá chất
				// tạm thời bỏ qua check chuyển kho hóa chất
				/*IdChuyenKho = this.CreatePhieuYeuCauHoaChat(db, NCC, nhId, userId,khoChoXuLy);*/
				
			}
			
			/*// Nhan hang se chuyen sang hoan tat
			// cap nhat them chuyenkho_hoachat_fk trong bang erp_nhanhang
			IdChuyenKho = "NULL";
			query =   " Update ERP_NhanHang set trangthai = '1', giochot = '" + getTime() + "',chuyenkho_hoachat_fk=" + IdChuyenKho
					+ " where pk_seq = '" + nhId + "'";
			if (!db.update(query)) {
				db.getConnection().rollback();
				db.shutDown();
				return "88.Không thể chôt nhận hàng: " + query;
			}*/

			// Chot nhanhang trong nhapkho
		 
			
			// cập nhật kế toán
			msg1=lib.CapNhatKeToanKho_NhanhangSanXuat(userId, db, nhId, false, ctyId);
			if(msg1.length()>0){
				db.getConnection().rollback();
				return msg1;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			db.shutDown();

			return "Không thể chôt nhận hàng: " + e.getMessage();
		}
	}

	private String createChuyenkho(dbutils db,String userid ,String ngaychotnv,String khochuyen, String khonhan,
			  List<ISpDetail> spdetail_xuat, String lydo,String trangthaisp, boolean isxacnhan,String NHANHANG_FK,String congtyId,String nppId ) {
	 
		 try{
			 //100064	XC06	Chuyển kho nội bộ
			Utility_Kho  util_kho =new Utility_Kho(); 
			String KhGiaCongId="";
			String lsxId ="",doituongchuyenid="",loaidoituongchuyen="";
			/*String query	="SELECT DISTINCT A.PK_SEQ as lsxId, ddh.khachhang_fk  as khachhang_fk "+ 
							" FROM ERP_LENHSANXUAT_GIAY A   "+
							" inner join erp_dondathang ddh on ddh.pk_seq= a.dondathang_fk "+   
							" where A.PK_SEQ= (select LENHSANXUAT_FK FROM ERP_NHANHANG WHERE PK_SEQ="+NHANHANG_FK+")";
				ResultSet rs=db.get(query);
				if(rs.next()){
					 KhGiaCongId =rs.getString("khachhang_fk");
					 
				}
				rs.close();
				*/
				
				String query = "select  (select isnull(DOITUONGNHAN_FK,0) as DOITUONGNHAN_FK   from erp_nhanhang where pk_seq="+NHANHANG_FK+")  as  DOITUONGNHAN_FK ,  PK_SEQ as DOITUONGID, '5' as LOAIDOITUONGID,LENHSANXUAT_FK "
						+ " ,CONGDOAN_FK from ERP_LENHSANXUAT_CONGDOAN_GIAY where congdoan_fk=(select congdoan_fk  FROM ERP_NHANHANG WHERE PK_SEQ="+NHANHANG_FK+")  and LENHSANXUAT_FK in (select LENHSANXUAT_FK FROM ERP_NHANHANG WHERE PK_SEQ="+NHANHANG_FK+")";
				ResultSet rs1=db.get(query);
				String DOITUONGNHAN_FK="NULL";
				String cdid="";
				if(rs1.next()){
					lsxId =rs1.getString("LENHSANXUAT_FK");
					doituongchuyenid =rs1.getString("DOITUONGID");
					loaidoituongchuyen =rs1.getString("LOAIDOITUONGID");
					cdid =rs1.getString("CONGDOAN_FK");
					DOITUONGNHAN_FK=rs1.getString("DOITUONGNHAN_FK");
					
					 
				}else{
					return "Không thể xác định được đối tượng xuất của kho sản xuất "; 
					
				}
				rs1.close();
				String KH_XUAT_FK="",DOITUONG="",LOAIDOITUONG="";
				
				if(Library.getLoaiKho(khonhan,db).equals("13")){
				 	
					if(KhGiaCongId.equals("")){
						return  "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: kho nhận là kho gia công nhưng không xác định được đối tượng để chuyển kho ";
					}
					DOITUONG=KhGiaCongId;
					LOAIDOITUONG="1";	
					KH_XUAT_FK=KhGiaCongId;
				}else if(Library.getLoaiKho(khonhan,db).equals("10")){
					DOITUONG=DOITUONGNHAN_FK;
					LOAIDOITUONG="5";	
				}
				else{
					 
					DOITUONG="NULL";
					LOAIDOITUONG="NULL";
					KH_XUAT_FK="NULL";
				}
				
				
			  query = " insert ERP_CHUYENKHO(LOAIDOITUONG,DOITUONG_FK, doituongNHAN_fk,LOAIDOITUONGNHAN, noidungxuat_fk, ngaychuyen, ngaynhan, "
			  		+ " ngaychot, lydo, trangthai, khoxuat_fk, khonhan_fk, trangthaisp, ngaytao,  "
			  		+ " nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK,NHANHANG_FK,ISHANGDIDUONG,congty_fk,npp_fk, sochungtu ,lenhsanxuat_fk,congdoan_fk) " +
				" values("+loaidoituongchuyen+","+doituongchuyenid+","+(DOITUONG.length()>0?DOITUONG:"NULL")+"  ,"+LOAIDOITUONG+",100066, '" + ngaychotnv + "', '" + ngaychotnv + "', '" + ngaychotnv + "', N'"+lydo+"', 1 , '" + khochuyen + "', " + khonhan + ", '"+trangthaisp+"', " +
						" '" + getDateTime() + "', '" +userid+ "', '" + getDateTime() + "', '" + userid + "', NULL, NULL,  "+NHANHANG_FK+",'1',"+congtyId+","+nppId+","+lsxId+","+lsxId+","+cdid+"  )";
			System.out.println("--taochuyenkho="+query);
			if(!db.update(query))
			{
				return  "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
			}
		
		 
			String ckId = "";
			query = "select SCOPE_IDENTITY() as ckId";
			ResultSet rsCk = db.get(query);
			if (rsCk.next())
			{
				ckId = rsCk.getString("ckId");
			} else {
				return "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
			 
			}
			rsCk.close();
		 
			for(int i = 0; i < spdetail_xuat.size(); i++) {
				
				ISpDetail sp = spdetail_xuat.get(i);
				 
				 
				query=	" insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK,  solo, ngayhethan,  " +
						" ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, soluong,BIN_FK,PHIEUEO,MAPHIEUDINHTINH ) " +
						" VALUES ("+ckId+", "+sp.getMa()+",'"+sp.getSolo()+"','"+sp.getNgayHethan()+"','"+sp.getNgaynhapkho()+"','"+sp.getMame()+"' " +
				  		",'"+sp.getSothung()+"','"+sp.getMaphieu()+"' ,'"+sp.getMarq()+"','"+sp.getHamluong()+"','"+sp.getHamAm()+"' " +
				  		" ,"+sp.getSoluong()+",'"+sp.getkhuid()+"','"+sp.getPhieuEO()+"','"+sp.getMaphieudinhtinh()+"')";
				System.out.println("--taochuyenkho="+query);
				if(db.updateReturnInt(query)< 1)
				{
					return "Không thể cập nhật : " + query;
				}
				  
			    double soluongct=(-1)*Double.parseDouble(sp.getSoluong());
			    double booked=0;
			    double available=(-1)*Double.parseDouble(sp.getSoluong());
			     
			    
				Kho_Lib kholib=new Kho_Lib();
				kholib.setLoaichungtu("erpnhanhangsanxuatsvl 411 ERP_CHUYENKHO : "+ckId);
				kholib.setNgaychungtu(ngaychotnv);
				kholib.setKhottId(khochuyen);
				kholib.setBinId("0");
				kholib.setSolo(sp.getSolo());
				kholib.setSanphamId(sp.getMa());
				kholib.setMame(sp.getMame());
				kholib.setMathung(sp.getSothung());
				kholib.setMaphieu(sp.getMaphieu());
				kholib.setMaphieudinhtinh(sp.getMaphieudinhtinh());
				kholib.setPhieuEo(sp.getPhieuEO());
				kholib.setNgayhethan(sp.getNgayHethan());
				kholib.setNgaysanxuat(sp.getNgaySx());
				kholib.setNgaynhapkho(sp.getNgaynhapkho());
				
				kholib.setBooked(booked);
				kholib.setSoluong(soluongct);
				kholib.setAvailable(available);
				kholib.setMARQ(sp.getMarq());
				kholib.setDoituongId(doituongchuyenid);
				kholib.setLoaidoituong(loaidoituongchuyen);
				kholib.setBinId(sp.getkhuid());
				kholib.setDongialo(0);
				kholib.setHamluong(sp.getHamluong());
				kholib.setHamam(sp.getHamAm());
				kholib.setNppid(nppId);
				kholib.setNsxId(sp.getNsxId());
				String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					db.getConnection().rollback();
					return msg1;
				}
		 
			 }
			
			query = "  insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK,  soluongyeucau, soluongxuat) " +
			"  select chuyenkho_fk, SANPHAM_FK, sum(soluong) as soluongyeucau  , sum(soluong) as soluongxuat   from  " +
			"  ERP_CHUYENKHO_SANPHAM_CHITIET where chuyenkho_fk= "+ckId +" group by   chuyenkho_fk, SANPHAM_FK  " ;
			System.out.println("--taochuyenkho="+query);
			if(!db.update(query))
			{
				return "Không thể tạo mới ERP_CHUYENKHO " + query;
			} 
			
			//GHI NHAN NHAN HANG
			/*query = "  insert ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG( chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  	MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, soluong		 )  " + 
					"  select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  		MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, " + 
					"  		  DT.soluong as soluong  " + 
					"  from " + 
					"  ( " + 
					"  	select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  		MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, SUM(soluong) as soluong " + 
					"  	from ERP_CHUYENKHO_SANPHAM_CHITIET  " + 
					"  	where chuyenkho_fk = '" + ckId + "' and chuyenkho_fk in ( select pk_seq from ERP_CHUYENKHO where pk_seq = '" + ckId + "' and khonhan_fk is not null )  " + 
					"  	group by chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  				MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH " + 
					"  ) " + 
					"  DT " + 
					"  order by DT.SANPHAM_FK asc, DT.soluong desc ";*/
			
			//GHI NHAN NHAN HANG
			query = "  insert ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG( chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  	MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, soluong	, NGAYNHAPKHO_CHUYENKHO	 )  " + 
					"  select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  		MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, " + 
					"  		  DT.soluong  as soluong  , DT.NGAYNHAPKHO_CHUYENKHO" + 
					"  from " + 
					"  ( " + 
					"  	select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ck.NgayChuyen as ngaynhapkho, MARQ, hamluong, hamam, " +
					"   MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, SUM(soluong) as soluong , a.ngaynhapkho as  NGAYNHAPKHO_CHUYENKHO " + 
					"  	from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join erp_chuyenkho ck on ck.pk_seq=a.chuyenkho_fk  " + 
					"  	where chuyenkho_fk = '" + ckId + "' and chuyenkho_fk in ( select pk_seq from ERP_CHUYENKHO where pk_seq = '" + ckId + "' and khonhan_fk is not null )  " + 
					"  	group by chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ck.NgayChuyen , MARQ, hamluong, hamam, " + 
					"  				MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH , a.ngaynhapkho" + 
					"  ) " + 
					"  DT " +
					//", ( select 1 as col1 union select 2 union select 3 union select 4 union select 5 ) DT2 " + 
					"  order by DT.SANPHAM_FK asc, DT.soluong desc ";
			System.out.println("--taochuyenkho="+query);
			if( !db.update(query) )
			{
				db.getConnection().rollback();
				return "Không thể cập nhật nhận hàng: "+query; 
			}
			
			 
		 }catch(Exception er){
			 er.printStackTrace();
			 return er.getMessage();
		 }
		 return "";
	}
 
	
	
	
	private String  TangKhoKiemDinh(dbutils db, String khoId, String nhanhang_fk, String sanpham, String muahang_fk,String maphieu, String dongiaT,String userid,String congtyId,String nppId){
		try{
			// tăng kho chi tiết
			String sql = " select c.LOAIDOITUONG,c.DOITUONG_FK,c.KHONHANTP_DAT_FK,  a.SOLO, a.MATHUNG, a.MAME, a.BIN_FK, a.SOLUONG,a.NGAYHETHAN, a.NGAYSANXUAT, c.NGAYNHAN," +
				  " a.SANPHAM_FK,b.DONGIA , b.idmarquette, isnull((select MA from MARQUETTE m where m.PK_SEQ = b.idmarquette),'') as mamarquette,c.NPP_FK,c.Congty_fk " +
				  " from ERP_NHANHANG_SP_CHITIET a inner join ERP_NHANHANG_SANPHAM b " +
				  " on a.NHANHANG_FK = b.NHANHANG_FK and a.SANPHAM_FK = b.SANPHAM_FK   and a.GIATHEOLO = b.DONGIA " +
				  " inner join ERP_NHANHANG c on c.PK_SEQ = b.NHANHANG_FK "+
				  " where a.SANPHAM_FK = "+sanpham+" and a.NHANHANG_FK = "+nhanhang_fk 
				  + " and b.DONGIA = "+ dongiaT ;
			 System.out.println("get kho : "+sql);
			String date = "";		
			Utility_Kho util_kho=new Utility_Kho();
			   List<ISpDetail> spdetail_xuat = new ArrayList<ISpDetail>();
			   
			   	
			ResultSet rs = db.get(sql);
			 String KhoNhanId="";
				while(rs.next()){
					KhoNhanId=rs.getString("KHONHANTP_DAT_FK");
					
					String sanpham_fk = rs.getString("SANPHAM_FK");
					String solo = rs.getString("SOLO");
					String khu_fk = rs.getString("BIN_FK");
					double soluong= rs.getDouble("SOLUONG");
					String ngayhethan= rs.getString("NGAYHETHAN");
					String ngaysanxuat = rs.getString("NGAYSANXUAT");
					double dongia = rs.getDouble("DONGIA");
					String idmarquette = rs.getString("idmarquette");
					String mamarquette = rs.getString("mamarquette");
					String mathung = rs.getString("MATHUNG");
					String mame = rs.getString("MAME");
					date = rs.getString("NGAYNHAN");
					
					Kho_Lib kholib=new Kho_Lib();
					kholib.setNgaychungtu(date);
					kholib.setLoaichungtu("erpnhanhangsanxuatsvl 498: ERP_NHANHANG"+nhanhang_fk);
					kholib.setKhottId(khoId);
					
					kholib.setBinId(khu_fk);
					
					kholib.setSolo(solo);
					kholib.setSanphamId(sanpham_fk);
					
					kholib.setMame(mame);
					kholib.setMathung(mathung);
					kholib.setMaphieu("");
					String maphieudinhtinh="";
					String phieueo="";
					kholib.setMaphieudinhtinh(maphieudinhtinh);
					kholib.setPhieuEo(phieueo);
					
					kholib.setNgayhethan(ngayhethan);
					kholib.setNgaysanxuat(ngaysanxuat);
					
					kholib.setNgaynhapkho(date);
					 
					kholib.setMARQ(mamarquette);
					kholib.setDoituongId(rs.getString("DOITUONG_FK")==null?"":rs.getString("DOITUONG_FK"));
					kholib.setLoaidoituong(rs.getString("LOAIDOITUONG")==null?"":rs.getString("LOAIDOITUONG"));
					String hamluong="100";
					String hamam="0";
					kholib.setHamluong(hamluong);
					kholib.setHamam(hamam);
					 
			    	kholib.setBooked(0);
					kholib.setSoluong(soluong);
					kholib.setAvailable(soluong);
					kholib.setDongialo(0);
					kholib.setNppid(nppId);
					 
					String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
				    if( msg1.length() >0)
					{
				    	return msg1;
					}
				    
				    ISpDetail spxuat =new SpDetail();
					   spxuat.setSolo(rs.getString("SoLo"));
					    
					   spxuat.setSoluong((soluong)+"");
					   spxuat.setMa(sanpham_fk);
					   spxuat.setNgaynhapkho(date);
					   spxuat.setSothung(mathung);
					   spxuat.setMame(mame);
					   spxuat.setMaphieu("");
					   spxuat.setMaphieudinhtinh(maphieudinhtinh);
					   spxuat.setPhieuEO(phieueo);
					   spxuat.setMarq(mamarquette);
					   spxuat.setHamAm(hamam);
					   spxuat.setHamluong(hamluong);
					   spxuat.setkhuid(khu_fk); 
					   spxuat.setNgayHethan(ngayhethan);
					   spxuat.setNgaySx(ngaysanxuat);
					   spdetail_xuat.add(spxuat);
				}
				rs.close();
			 
				
				
				   
				 System.out.println("TAO CHUYEN KHO---------------------------- : "+sql);
				   
				    String  msg1=this.createChuyenkho(db,userid,date , khoId, KhoNhanId, spdetail_xuat, "Chuyển kho hàng đạt của nhập kho "+nhanhang_fk+" không qua kiểm định:" ,"1",false,nhanhang_fk,congtyId,nppId);
				     if(msg1.length() >0){
					    
					   return msg1;
				    }
				     
				     
			return "";
		} catch(Exception ex){
			ex.printStackTrace();
			return "Lỗi : "+ex.getMessage();
		}
		
	}
 
	
	private String createMaPhieuTuDong(String sanphamId, String idKiemDinh, dbutils db){
		 /*001-16 (năm)-TP/NL/BTP (loại sản phẩm)*/
		try{
		// truy vấn thông tin loại sản phẩm
		String query = "  select l.MA from ERP_SANPHAM sp left join ERP_LOAISANPHAM l " +
					   "  on sp.LOAISANPHAM_FK = l.PK_SEQ where sp.PK_SEQ ="+ sanphamId;
		
		ResultSet rs = db.get(query);
		String loaiSanPham = "";
		if(rs !=null){
			if(rs.next()){
				loaiSanPham = rs.getString("MA");
			}
			rs.close();
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(d);
		String year = date.substring(2, 4);
		
		return idKiemDinh +"-"+year+ "-"+loaiSanPham;
		} catch (Exception ex){
			ex.printStackTrace();
			return "";
		}
		
	}
	private String CreatePhieuKiemDinh(dbutils db, String sanphamId, String nhanhangId, String muaHangId, String dinhluong, String dinhtinh,
			String khoId, String userId, String loaimh, List<String> maphieuL, String dongia,String NppId){
		
		// tính toán số lượng phiếu kiểm định sẽ được tạo ra
		// số phiếu kiểm định tạo ra sẽ theo mẻ.
		// 1 lô sẽ có 10 thùng, 10 thùng thuộc 2 mẻ
		// tạo ra 2 phiếu kiểm định tương ứng với 2 mẻ.
		try{
		int i;
		String query ="";
//		int soluongMe =0;
		
		query = " select nh.NGAYNHAN ,a.MAME , SUM(a.SOLUONG) as soluong,a.NGAYHETHAN, a.NGAYSANXUAT, a.solo ,b.IDMARQUETTE," +
				" isnull((select MA from MARQUETTE m where m.PK_SEQ = b.IDMARQUETTE),'' ) as mamarquette, b.DONGIAVIET as DONGIA " +
				" from ERP_NHANHANG_SP_CHITIET a  " +
				" inner join erp_nhanhang nh on nh.pk_seq= a.nhanhang_fk  " +
				"  inner join " +
				" ERP_NHANHANG_SANPHAM b on a.SANPHAM_FK = b.SANPHAM_FK and b.NHANHANG_FK = a.NHANHANG_FK  "
				+ " and a.GIATHEOLO = b.DONGIA " +
				" where a.SANPHAM_FK = "+sanphamId+" and a.NHANHANG_FK = "+nhanhangId+" and    b.DONGIA ="+dongia +
				" group by  nh.NGAYNHAN ,a.MAME, a.SOLO,a.NGAYHETHAN, a.NGAYSANXUAT, b.IDMARQUETTE, b.DONGIAVIET";
		
		
		ResultSet rs = db.get(query);
		List<String> maMe = new ArrayList<String>();
		List<Double> soluong = new ArrayList<Double>();
		List<String> ngaySanXuat = new ArrayList<String>();
		List<String> ngayHetHan = new ArrayList<String>();
		List<String> soLo = new ArrayList<String>();
		List<String> idmarquette = new ArrayList<String>();
		List<String> mamarquette = new ArrayList<String>();
		List<String> dongiaViet = new ArrayList<String>();
		
		String ngaynhanhang="";
		
		 
			while(rs.next()){
				ngaynhanhang=rs.getString("NGAYNHAN");
				maMe.add(rs.getString("MAME"));
				soluong.add(rs.getDouble("soluong"));
				ngaySanXuat.add(rs.getString("NGAYSANXUAT"));
				ngayHetHan.add(rs.getString("NGAYHETHAN"));
				soLo.add(rs.getString("SOLO"));
				idmarquette.add(rs.getString("IDMARQUETTE"));
				mamarquette.add(rs.getString("MAMARQUETTE"));
				dongiaViet.add(rs.getString("DONGIA"));
			}
		 
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
		String s = timeFormat.format(today.getTime());
		for (i = 0; i < maMe.size(); i++) {
			
			// tạo phiếu kiểm định
			query = "insert into ERP_YeuCauKiemDinh(ngaykiem,nhanhang_fk,sanpham_fk,khuvuckho_fk,solo,trangthai,ngayhethan,nguoitao,"
					+ "ngaytao,nguoisua,ngaysua,soluong,ngaysanxuat,dinhluong,dinhtinh," + "ngaynhan,KHONHAN_FK,NGAYHETHONG,"
					+ "DonGiaViet,nhapkho_fk,loaimuahang,MAME, IDMARQUETTE, HAMAM, HAMLUONG, MAMARQUETTE)" + " values( '"+ngaynhanhang+"',"+ nhanhangId+ ","+ sanphamId+ ",null,'"
					+ soLo.get(i)+ "','0','"+ngayHetHan.get(i)+"',"+ userId+ ",'"+ s+ "',"+ ""+ userId+ ",'"+ s+ "',"
					+ soluong.get(i)+ ",'"+ngaySanXuat.get(i)+"','"
					+ dinhluong+ "','"+ dinhtinh+ "','',"+ khoId+ ",'"+ s+ "',"
					+ dongiaViet.get(i)+ ",null,'"+ loaimh + "','" + maMe.get(i)+"',"+idmarquette.get(i)+",0,100,'"+mamarquette.get(i)+"')";
			System.out.println("--taokiemdinh="+query);
			if (db.updateReturnInt(query) != 1) {
				 
				return "Lỗi dòng lệnh :" + query;
			}
			 
			// Lấy phiếu kiểm định vừa tạo ra.
			query = "select IDENT_CURRENT('ERP_YeuCauKiemDinh') as nhId";
			String idKiemDinh = "";
			ResultSet rs1 = db.get(query);

			if (rs1 != null) {
				System.out.println(" vao dc day");
				try {
					if (rs1.next()) {
						idKiemDinh = rs1.getString("nhId");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				rs1.close();
			}
			
			// Cập nhật mã phiếu tự động cho phiếu kiểm định
			String maphieu = createMaPhieuTuDong(sanphamId,idKiemDinh,db);
			
			query = "Update ERP_YeuCauKiemDinh set MAPHIEU ='"+maphieu+"' where PK_SEQ = "+ idKiemDinh ;
			System.out.println("--taokiemdinh="+query);
			int check = db.updateReturnInt(query);
			if(check !=1){
				return " Không cập nhật được mã phiếu";
			}
			maphieuL.add(maphieu);
			
			
			
			// Lấy dữ liệu về mã thùng và số lượng để insert vào bảng ERP_YEUCAUKIEMDINH_THUNG
			query = " select MATHUNG,sum(SOLUONG) as soluong , BIN_FK from ERP_NHANHANG_SP_CHITIET a where NHANHANG_FK = "+nhanhangId
					+" and SANPHAM_FK = "+sanphamId+" " +"   and SOLO= '"+soLo.get(i)
					+"' and GIATHEOLO ="+ dongia +" group by  MATHUNG,BIN_FK " ;
			System.out.println(query);
			List<String> maThung = new ArrayList<String>();
			List<Double> soLuongThung = new ArrayList<Double>();
			List<String> khuVuc = new ArrayList<String>();
			rs1 = db.get(query);
		 
				while(rs1.next()){
					maThung.add(rs1.getString("MATHUNG"));
					soLuongThung.add(rs1.getDouble("SOLUONG"));
					khuVuc.add(rs1.getString("BIN_FK"));
				}
				rs1.close();
				
				if(maThung.size()==0){
					return "Lỗi lưu chi tiết số thùng trong kiểm định, không xác định được chi tiết";
				}
				
				
			// tạo bảng lưu chi tiết số thùng lưu vào bảng
			for(int j=0; j< maThung.size(); j++){
				String maLoThung= soLo.get(i);
				
				query = " insert into ERP_YEUCAUKIEMDINH_THUNG(YEUCAUKIEMDINH_FK, MATHUNG, SOLUONG, SOLUONGMAU, SOLUONGDAT, " +
						" SOLUONGKHONGDAT, MALO, BIN_FK,NGAYNHAPKHO,ngaynhapkho_kiemdinh)" +
					" values("+idKiemDinh+",'"+ maThung.get(j)+"',"+soLuongThung.get(j)+",0,"+soLuongThung.get(j)+",0,'"+maLoThung+"',"+khuVuc.get(j)+",'"+ngaynhanhang+"','"+ngaynhanhang+"')";
				System.out.println("--taokiemdinh="+query);
				int k = db.updateReturnInt(query);
				if( k!=1){
					return "Lỗi lưu chi tiết số thùng trong kiểm định";
				}
			}
			
			// chỗ này tăng kho kiểm định lun
			// tăng kho chờ xử lý
			// đang tạo cho từng sản phẩm
			
			String check1  = TangKhoKiemDinhMaMe(db, khoId, nhanhangId,sanphamId, muaHangId, maphieu,maMe.get(i), soLo.get(i), dongia);
			if(check1.length()>0 ){
				 
				return "88.Không thể chôt nhận hàng lỗi trong qua trình tăng kho :  "+check1;
			}
		}
		return "";
		} catch(Exception ex){
			 
			ex.printStackTrace();
			return "Lỗi "+ex.getMessage();
		}
		
		
	}
	
	
	public static void main ( String args [  ]  )   {
		dbutils db=new dbutils();
		try{ 
	 	 
		}catch(Exception err){
			err.printStackTrace();
		}
	 
   }
 
     
	private String TangKhoKiemDinhMaMe(dbutils db, String khoId, String nhanhang_fk, String sanpham,
			String muahang_fk, String maphieu, String MaMe, String SoLo, String dongiaT){

		try{
			// tăng kho chi tiết
			String sql =  " select c.LOAIDOITUONG,c.DOITUONG_FK,a.SOLO, a.MATHUNG, a.MAME, a.BIN_FK, a.SOLUONG,a.NGAYHETHAN, a.NGAYSANXUAT, c.NGAYNHAN," +
						  " a.SANPHAM_FK,b.DONGIA , b.idmarquette, isnull((select MA from MARQUETTE m where m.PK_SEQ = b.idmarquette),'') as mamarquette ,c.NPP_FK" +
						  " from ERP_NHANHANG_SP_CHITIET a inner join ERP_NHANHANG_SANPHAM b " +
						  " on a.NHANHANG_FK = b.NHANHANG_FK and a.SANPHAM_FK = b.SANPHAM_FK  and a.GIATHEOLO = b.DONGIA  " +
						  " inner join ERP_NHANHANG c on c.PK_SEQ = b.NHANHANG_FK "+
						  " where a.SANPHAM_FK = "+sanpham+" and a.NHANHANG_FK = "+nhanhang_fk+" " +
						  "  and a.SOLO ='"+SoLo+"'";
			
			 
			String date = "";
			
			ResultSet rs = db.get(sql);
	 
				while(rs.next()){
					String sanpham_fk = rs.getString("SANPHAM_FK");
					String solo = rs.getString("SOLO");
					String khu_fk = rs.getString("BIN_FK");
					double soluong= rs.getDouble("SOLUONG");
					String ngayhethan= rs.getString("NGAYHETHAN");
					String ngaysanxuat = rs.getString("NGAYSANXUAT");
					double dongia = rs.getDouble("DONGIA");
					String idmarquette = rs.getString("idmarquette");
					String mamarquette = rs.getString("mamarquette");
					String mathung = rs.getString("MATHUNG");
					String mame = rs.getString("MAME");
					date = rs.getString("NGAYNHAN");
					String nppId=rs.getString("NPP_FK");
					
					Kho_Lib kholib=new Kho_Lib();
					kholib.setNgaychungtu(date);
					kholib.setLoaichungtu("erpnhanhangsanxuatsvl 1292 :  ERP_NHANHANG"+nhanhang_fk);
					kholib.setKhottId(khoId);
					
					kholib.setBinId(khu_fk);
					
					kholib.setSolo(solo);
					kholib.setSanphamId(sanpham_fk);
					
					kholib.setMame(mame);
					kholib.setMathung(mathung);
					// Mã phiếu phải bằng "", khi kiểm định mới có mã phiếu 
					kholib.setMaphieu("");
					
					kholib.setMaphieudinhtinh("");
					kholib.setPhieuEo("");
					
					kholib.setNgayhethan(ngayhethan);
					kholib.setNgaysanxuat(ngaysanxuat);
					
					kholib.setNgaynhapkho(date);
					 
					kholib.setMARQ(mamarquette);
					kholib.setDoituongId(rs.getString("DOITUONG_FK")==null?"":rs.getString("DOITUONG_FK"));
					kholib.setLoaidoituong(rs.getString("LOAIDOITUONG")==null?"":rs.getString("LOAIDOITUONG"));

				 
					kholib.setHamluong("100");
					kholib.setHamam("0");
					 
			    	kholib.setBooked(0);
					kholib.setSoluong(soluong);
					kholib.setAvailable(soluong);
					kholib.setNppid(nppId);
				     
					Utility_Kho util_kho=new Utility_Kho();
					
					String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
				    if( msg1.length() >0)
					{
						// this.msg = msg1;
						db.getConnection().rollback();
						return msg1;
						
					}
				  
				}
				rs.close();
			  
			return "";
		} catch(Exception ex){
			ex.printStackTrace();
			return "Lỗi : "+ex.getMessage();
		}
		
	}
 
	public boolean CheckDahoantat(dbutils db, String idnhanhang )
	{		
		String query=
			 " SELECT DATA.SANPHAM_FK,DATA.SOLUONG,DATA.SOLUONGDANHAN FROM ( "+ 
			 " SELECT MHSP.SANPHAM_FK,MHSP.SOLUONG ,(SELECT SUM( NHSP.SOLUONGNHAN ) FROM ERP_NHANHANG NH  "+ 
			 " INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ=NHSP.NHANHANG_FK "+ 
			 " WHERE NH.MUAHANG_FK=MH.PK_SEQ AND NHSP.SANPHAM_FK=MHSP.SANPHAM_FK AND NH.TRANGTHAI  IN (1,2) )  AS SOLUONGDANHAN "+ 
			 " FROM ERP_MUAHANG MH  "+ 
			 " INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK "+ 
			 " WHERE MH.PK_SEQ= (select MUAHANG_FK from ERP_NHANHANG where PK_SEQ= "+idnhanhang+")  "+ 
			 " ) DATA WHERE DATA.SOLUONG-DATA.SOLUONGDANHAN >0";
		
		
		ResultSet  kt = db.get(query);
		try{
			 
			if(kt.next()){
				//dang con du lieu co the nhan
				kt.close();
				return false;
			}
			kt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
			
		}
		return true;
	}
			
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhanhangList_Giay obj;
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
	    String loaimh = util.antiSQLInspection(request.getParameter("loaimh")); 
	    System.out.println("Loai "+loaimh);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nhBean.setNppId((String)session.getAttribute("nppId"));
	    	nhBean.setLoaimh(loaimh);
	    	nhBean.setLdnId("100000");
	    	nhBean.createRs();
    		
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuatNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpNhanhangList_Giay();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setNppId((String)session.getAttribute("nppId"));

	    		obj.setLoaimh(loaimh);
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuat.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpNhanhangList_Giay();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setNppId((String)session.getAttribute("nppId"));
		    	obj.setLoaimh(loaimh);
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
			
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHangSanXuat.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpNhanhangList_Giay obj)
	{
//		Utility util=new Utility();

		String query =   "\n  SELECT   A.PK_SEQ AS NHID, '' AS NCCTEN,   " + 
				  "\n ''  AS SOHOADON,   " + 
				  "\n   A.NGAYNHAN, B.TEN AS DVTHTEN,   " + 
				  "\n LSX.PK_SEQ AS POID,   " + 
				  "\n   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU,   " + 
				  "\n A.TRANGTHAI, A.NGAYSUA, A.NGAYTAO, D.TEN AS NGUOITAO, E.TEN AS NGUOISUA, A.LOAIHANGHOA_FK,   " + 
				  "\n CASE WHEN A.HDNCC_FK IS NULL THEN 0  WHEN A.HDNCC_FK IS NOT NULL THEN 1  ELSE -1 END DARAHD, A.TOOLTIP  ,SP.MA AS MASANPHAM, SP.TEN AS TENSANPHAM   " + 
				  "\n FROM ERP_NHANHANG A    " + 
				  "\n INNER JOIN ERP_DONVITHUCHIEN B ON A.DONVITHUCHIEN_FK = B.PK_SEQ   " + 
				  "\n LEFT JOIN ERP_LENHSANXUAT_GIAY  LSX ON A.LENHSANXUAT_FK = LSX.PK_SEQ   " + 
				  "\n LEFT JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.NHANHANG_FK= A.PK_SEQ" + 
				  "\n LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK  " + 
				  "\n INNER JOIN NHANVIEN D ON A.NGUOITAO = D.PK_SEQ    " + 
				  "\n INNER JOIN NHANVIEN E ON A.NGUOISUA = E.PK_SEQ    " + 
				  	"\n where a.congty_fk = '" + obj.getCongtyId() + "' and a.LENHSANXUAT_FK IS NOT NULL  and a.npp_fk = "+obj.getNppId();
		 
			String khonhanId = request.getParameter("khonhanId");
			if( khonhanId == null){
				khonhanId = "";
			}
			obj.setKhonhanId(khonhanId);
			
			String tungay = request.getParameter("tungay");
			if(tungay == null)
				tungay = "";
			obj.setTungay(tungay);
			
				
			
		String lsxId = request.getParameter("lsxid");
		if(lsxId == null)
			lsxId = "";
		obj.setLsxId(lsxId);
			
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String dvthId = request.getParameter("dvth");
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);
		
		String soPo = request.getParameter("sopo");
		if(soPo == null)
			soPo = "";
		obj.setSoPO(soPo);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sonhanhang = request.getParameter("sonhanhang");
		if(sonhanhang == null)
			sonhanhang = "";
		obj.setSoNhanhang(sonhanhang);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSoHoadon(sohoadon);
		
		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNCC(ncc);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitaoIds(nguoitao);
		
		String mactsp = request.getParameter("mactsp");
		if(mactsp == null)
			mactsp = "";
		obj.setMaCtSp(mactsp);
		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		
		if( khonhanId.length() >0){
			query +="\n and a.KHONHANTP_DAT_FK ='"+ khonhanId+"'";
		}
		if(tungay.length() > 0)
			query += "\n and a.ngaynhan >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += "\n and a.ngaynhan <= '" + denngay + "'";
		
		if(dvthId.length() > 0)
			query += "\n and b.pk_seq = '" + dvthId + "'";
		
		if(nguoitao.trim().length() > 0)
			query += "\n AND a.nguoitao = '" + nguoitao + "' ";
		
		//change to LSX PK_SEQ
		if(lsxId.length() > 0)
		{
			//query += " and ( CAST(c.PK_SEQ as varchar(10)) + '/' + SUBSTRING(c.NGAYMUA, 6, 2) + '/' + SUBSTRING(c.NGAYMUA, 0, 5) like '%" + soPo + "%' ) ";
			
			/*query += "  AND ( c.sopo like '%" + soPo.trim() + "%' ) ";*/
			query += "\n  AND (ISNULL(LSX.SOLENHSANXUAT,'') like '%"+lsxId.trim()+ "%' OR  CAST( LSX.PK_SEQ AS nvarchar( 200 ))  like N'%"+lsxId.trim()+ "%') ";
		}
		
		if(trangthai.length() > 0)
			query += "\n and a.trangthai = '" + trangthai + "'";
		
		if(sonhanhang.trim().length() > 0)
		{
			query += "\n and b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) like N'%" + sonhanhang + "%'  ";
		}
		
		if(sohoadon.trim().length() > 0)
		{
			query += " and ( select isnull(kyhieu,'') + ' ' + CAST(sohoadon as varchar(50)) as ten  from ERP_HOADONNCC where pk_seq = a.hdNCC_fk ) like N'%" + sohoadon + "%' ";
		}
		
		if(ncc.trim().length() > 0)
		{
			query += " and (  ( ( c.pk_seq is not null ) and ( c.NHACUNGCAP_FK in ( select pk_seq from ERP_NHACUNGCAP where ma like N'%" + ncc + "%' or ten like N'%" + ncc + "%' ) ) )              " +
					 "     or ( ( th.pk_seq is not null ) and ( th.KHACHHANG_FK in  ( select pk_seq from ERP_KHACHHANG where ma like N'%" + ncc + "%' and ten like N'%" + ncc + "%'  )  ) ) )   ";
		}
		
		if(mactsp.trim().length() > 0)
		{
			/*query +=" and a.pk_seq in (" +
					"     select distinct nhanhang_fk from erp_nhanhang_sanpham where sanpham_fk in ( select distinct pk_seq from SANPHAM where MA like N'%" + mactsp + "%' ) " +
					" ) ";*/
			
			query +=" and a.pk_seq in (" +
			"     select distinct nhanhang_fk from erp_nhanhang_sanpham where sanpham_fk ="+ mactsp +" ) ";
			
		}
		
		//query += " order by a.NGAYNHAN desc, a.trangthai asc, a.pk_seq desc;";

		System.out.println( " seach query : \n " + query +"\n \n ");
		return query;
	}
	
	private String Delete(String nhId, dbutils db)
	{
		try 
		{
  	
			String query = "update ERP_NHANHANG set trangthai = '3' where pk_seq = '" + nhId + "' and trangthai=0  ";
			
			if(db.updateReturnInt(query)!=1 )
			{
				return "Không thể cập nhật nhận hàng";
			}
		 
			return "";
		} 
		catch (Exception e)
		{ 
			e.printStackTrace();
			return "Không thể cập nhật nhận hàng";
		}
	}
	
	// thay doi trang thai da hoan tat hoa don chua?
	private boolean UpdateHoanTatNhanHang(boolean flag, String id, dbutils db) {
		try {
			int index = 0;
			if (flag == true) {
				index = 1;
			}
			String query = "";
			query = "update ERP_PARK set HOANTAT_NHANHANG = '" + index + "' where PK_SEQ in ("
					+ "		select park_fk from ERP_HOADONNCC where pk_seq in (" 
					+ "			 select hdNCC_fk from ERP_NHANHANG where PK_SEQ = " + id + "))";

			int k = db.updateReturnInt(query);
			if (k != 1) {
				return false;
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String getTime()
	{
		Date date = new Date();
	    SimpleDateFormat simpDate;

	    //format 24h
	    simpDate = new SimpleDateFormat("kk:mm:ss");
	    
	    return simpDate.format(date);
	}
	
	 
}

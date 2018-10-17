package geso.traphaco.erp.servlets.nhanhangtrongnuoc;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.IErpNhanhangList_Giay;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.imp.ErpNhanhangList_Giay;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.imp.ErpNhanhang_Giay;
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

import Z.DB;

import com.itextpdf.text.log.SysoLogger;

public class ErpNhanhangtrongnuoc_GiaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpNhanhangtrongnuoc_GiaySvl() {
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
	    obj.setCongtyId(ctyId);
	    obj.setTrangthai("0");
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
		    		session.setAttribute("userId", userId);
		    		obj.setTrangthai("0");	
		    		GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
					obj.init("");	
					obj.setmsg(msg);
					
			    	session.setAttribute("obj", obj);  	
		    		
			
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHangtrongnuoc_Giay.jsp");
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
    		String loai = request.getParameter("loai");
    		if(loai == null)
    			loai = "";
    		
    		msg = ChotNhanHang(nhId, userId, ctyId, lhhId, loai);
    		session.setAttribute("userId", userId);
    		obj.setTrangthai("0");	
    		obj.setLoaimh(loai);
    		GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			obj.init("");	
			obj.setmsg(msg);
			
	    	session.setAttribute("obj", obj);  	
    		
	
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHangtrongnuoc_Giay.jsp");
			
    		return;
		     
	    }
	    else if(action.equals("convert"))
	    {
	    	IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    		    	
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
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangtrongnuocNew_Giay.jsp";
    		response.sendRedirect(nextJSP);
    		
    		return;
	    }
	    else{
     
	    obj.setTrangthai("0");
	    obj.setmsg(msg);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setUserId(userId);
		GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);

	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangtrongnuoc_Giay.jsp";
		response.sendRedirect(nextJSP);
	    }
	}
	
	Hashtable<String, Hashtable<String, Long> > nhan_form_choSelected = null; 
	
	private String ChotNhanHang (String nhId, String userId, String ctyId, String lhhId, String loaimh) 
	{
		dbutils db = new dbutils();

		try {
			db.getConnection().setAutoCommit(false);

			Utility util = new Utility();

			String query1 = " select ngaynhan ,ISNULL(ISTUDONG,'0') AS ISTUDONG  from erp_nhanhang where trangthai='0' and  pk_seq=" + nhId + " ";

			ResultSet rs1 = db.get(query1);
			String ngaychotNV = "";
			String istudong = "0";
			if (rs1.next()) {
				ngaychotNV = rs1.getString("ngaynhan");
				istudong = rs1.getString("ISTUDONG");
				
			}else{
				db.update("rollback");
				return "Vui lòng kiểm tra lại trạng thái của chứng từ chốt nhận hàng";
			}
			rs1.close();

			String msg1 = util.checkNgayHopLe(db, ngaychotNV);
			if (msg1.length() > 0) {
				db.update("rollback");
				return msg1;
			}

			// Sau nay phai them buoc check xem taikhoan do da co khoasothang
			// chua
			String ngaychot = "";
//			String ngaychungtu = "";
//			String ttId = "";
			String sql =  " SELECT A.NGAYCHOT, A.NGAYNHAN, A.NOIDUNGNHAN_FK, C.TAIKHOAN_FK, ISNULL(NGUONGOCHH, 'TN') AS NGUONGOCHH, A.KHOCHOXULY_FK \n"
						+ " FROM ERP_NHANHANG A  \n" + " LEFT JOIN  \n" + " (   	 \n"
						+ " 	SELECT DISTINCT TNK.PK_SEQ,HD_MH.MUAHANG_FK FROM ERP_THUENHAPKHAU TNK  	 \n"
						+ " 	INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNKHD ON TNK.PK_SEQ=TNKHD.THUENHAPKHAU_FK   \n"
						+ " 	INNER JOIN ERP_HOADONNCC_DONMUAHANG HD_MH ON TNKHD.HOADONNCC_FK=HD_MH.HOADONNCC_FK \n"
						+ " )TNK_MH ON TNK_MH.PK_SEQ = A.SOTOKHAI_FK  \n"
						+ " LEFT JOIN ERP_MUAHANG B ON ISNULL(A.MUAHANG_FK,TNK_MH.MUAHANG_FK) = B.PK_SEQ  \n"
						+ " LEFT JOIN ERP_NHACUNGCAP C ON A.NCC_KH_FK = C.PK_SEQ WHERE A.PK_SEQ =  " + nhId;
			// sua b.nhacungcap_fk thanh a.ncc_kh_fk

			System.out.println(sql);
 
			String khoChoXuLy = "";
			ResultSet rs = db.get(sql);
			if (rs.next()) {
				ngaychot = rs.getString("ngaychot");
				khoChoXuLy = rs.getString("KHOCHOXULY_FK");

			}
			rs.close();
			
//			String nam = ngaychot.substring(0, 4);
//			String thang = ngaychot.substring(5, 7);

			String query = "";
			String IdChuyenKho = "";
			
			if (istudong.equals("0")) {

				// Lay tai khoan No trong bang config
				// sua them cac truong de insert xuong bang erp_yeucaukiemdinh
				query = " SELECT nh1.NCC_KH_FK as NCC , NH.KHONHAN," +
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
					String muaHangId = rsSp.getString("MUAHANG_FK");
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
						
						String s = CreatePhieuKiemDinh( db,sanphamId, nhId, muaHangId, dinhluong, dinhtinh,khoChoXuLy,userId,loaimh, maPhieuL,dongia);
						
						if(s.trim().length() > 0){
							db.getConnection().rollback();
							db.shutDown();
							return "88.Không thể chôt nhận hàng, vui lòng báo Admin: không tạo được phiếu kiểm định : "+s;
						}
						
					} else {
						// tăng kho nhận
						// đang tạo cho từng sản phẩm
						String khoId = khoNhanId;
						 String  check  = TangKhoKiemDinh(db, khoId ,nhId,sanphamId,muaHangId,"",dongia);
						if(check.length() >0 ){
							db.getConnection().rollback();
							db.shutDown();
							return "88.Không thể chôt nhận hàng, vui lòng báo Admin: không tạo được phiếu kiểm định : "+check;
						}
					}
					// end tao phieu yeu cau kiem dinh
				}
				rsSp.close();

			 
				
			}

			// Nhan hang se chuyen sang hoan tat
			// cap nhat them chuyenkho_hoachat_fk trong bang erp_nhanhang
			IdChuyenKho = "NULL";
			query =   " Update ERP_NhanHang set trangthai = '1', giochot = '" + getTime() + "',chuyenkho_hoachat_fk=" + IdChuyenKho
					+ " where pk_seq = '" + nhId + "' and trangthai=0 ";
			if (db.updateReturnInt(query)!=1) {
				db.getConnection().rollback();
				db.shutDown();
				return "88.Không thể chôt nhận hàng: " + query;
			}

			// Chot nhanhang trong nhapkho
			String msg = "";

			if (msg.trim().length() > 0) {
				db.getConnection().rollback();
				// db.shutDown();
				return "Không thể chôt nhận hàng - chi phí lưu kho: " + msg;
			}
			
		 
			Library lib=new Library();
			msg1=lib.Capnhat_Ngaynhapkho_Nhanhang(userId, db, nhId);
			if(msg1.length()>0){
				db.getConnection().rollback();
				return msg1;
			}
			// cập nhật kế toán
			msg1=lib.CapNhatKeToanKho_NhanhangMua_SPHH(userId, db, nhId, false, ctyId);
			if(msg1.length()>0){
				db.getConnection().rollback();
				return msg1;
			}
			
			msg1=this.createTieuHaoGiaCong(db,nhId,userId,ngaychot);
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
	
	private String createTieuHaoGiaCong(dbutils db, String nhId, String userId,String ngaychot) {
		// TODO Auto-generated method stub]
		try{
			String query="SELECT DISTINCT MUAHANG_FK FROM ERP_NHANHANG_SP_CHITIET WHERE NHANHANG_FK="+nhId;
			ResultSet rsmh=db.get(query);
			while (rsmh.next()){
				
					String MUAHANG_FK=rsmh.getString("MUAHANG_FK");
					
					query=	" UPDATE A SET A.DMVT_FK= (SELECT   distinct (DANHMUCVATTU_FK) FROM ERP_MUAHANG_BOM b "+  
							" WHERE MUAHANG_FK = MH.PK_SEQ and b.SANPHAM_FK =A.SANPHAM_FK ) "+ 
							" FROM ERP_MUAHANG_SP A "+
							" INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ= A.MUAHANG_FK "+
							" WHERE MH.ISGIACONG=1 "+ 
							" AND DMVT_FK IS NULL AND MH.PK_SEQ="+MUAHANG_FK;
					
					if(!db.update(query)){
						return "không thể cập nhật dòng lệnh :"+query;
					}
					
			  		query="SELECT PK_SEQ, ISNULL(ISGIACONG,'0') AS ISGIACONG ,congty_fk FROM ERP_MUAHANG WHERE PK_SEQ= "+MUAHANG_FK+"";
			  		//System.out.println("query:"+query);
			  		
					ResultSet rs=db.get(query);
					String isgiacong="0";
					String congtyid="";
					String muahang_fk="";
					if(rs.next()){
						isgiacong=rs.getString("ISGIACONG");
						congtyid=rs.getString("congty_fk");
						muahang_fk=rs.getString("PK_SEQ");
						
					}
					rs.close();
					if(isgiacong.equals("1")){

						/*query=  " select   SANPHAM_FK,  sum(SOLUONG)   as SOLUONG     " +
								" from ERP_NHANHANG_SP_CHITIET  " +
								" where    muahang_fk="+muahang_fk+" and  NHANHANG_FK = '" + nhId + "' and SOLUONG> 0  " +
								" group by SANPHAM_FK "; */
						query=" SELECT (SELECT DMVT_FK FROM ERP_MUAHANG_SP MHSP WHERE MUAHANG_FK="+muahang_fk+" AND SP.PK_SEQ=MHSP.SANPHAM_FK ) AS DMVT_FK "+ 
				" , SP.DVDL_FK,SANPHAM_FK,  SUM(SOLUONG)   AS SOLUONG     "+
				" FROM ERP_NHANHANG_SP_CHITIET A "+
				" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK "+ 
				" WHERE      A.NHANHANG_FK = "+nhId+" AND SOLUONG> 0  AND A.MUAHANG_FK="+muahang_fk+" "+
				" GROUP BY SANPHAM_FK , SP.DVDL_FK,SP.PK_SEQ ";
						System.out.println(query);
						ResultSet rssp=db.get(query);
						
						 while(rssp.next()){
							 	String masanpham= rssp.getString("sanpham_fk");
							 	double soluong=rssp.getDouble("SOLUONG");
							 	
							 	String dmvt_fk=rssp.getString("DMVT_FK");
							 	
							 	soluong =getQuydoisoluong_veBom(soluong,dmvt_fk,masanpham,db,rssp.getString("DVDL_FK"));
							 	
							 	if(soluong==0){
							 		return "Không xác định được quy đối ra đơn vị của BOM,vui lòng báo Admin để được trợ giúp";
							 	}
								//Tao phieu tieu hao
								IErpTieuHao phieutieuhao = new ErpTieuHao(true);
								phieutieuhao.setCongtyId(congtyid);
								phieutieuhao.setNhanHangId(nhId);
								phieutieuhao.setSanphamId(masanpham);
								phieutieuhao.setSoLuong(String.valueOf(soluong));
								phieutieuhao.setNgayTao(getDateTime());
								phieutieuhao.setNgaySua(getDateTime());
								phieutieuhao.setNguoiTao(userId);
								phieutieuhao.setNguoiSua(userId);
								phieutieuhao.setTrangthai("0");
								phieutieuhao.setNgaytieuhao(ngaychot);
								phieutieuhao.setNgaychot(ngaychot);
								phieutieuhao.setMuahangID(MUAHANG_FK);
								query=  " SELECT MHSP.SOLUONG AS SOLUONGCHUAN,MHBOM.VATTU_FK, MHBOM.SOLUONG   FROM ERP_MUAHANG_BOM  MHBOM   "+
										" INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.SANPHAM_FK=MHBOM.SANPHAM_FK   AND MHBOM.MUAHANG_FK=MHSP.MUAHANG_FK "+ 
										" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=MHBOM.VATTU_FK "+
										" WHERE    MHBOM.MUAHANG_FK = "+muahang_fk+" AND MHBOM.SANPHAM_FK="+masanpham;
								 
								ResultSet rsBom = db.get(query);
									while(rsBom.next())
									{
										
										//Phiếu tiêu hao
										ISanpham vattu = new Sanpham();
										vattu.setId(rsBom.getString("VatTu_FK"));
										double soluongchuan=rsBom.getDouble("SoLuong")*soluong / rsBom.getDouble("SOLUONGCHUAN");
										vattu.setSoLuongChuan(soluongchuan) ;
										vattu.setSoLuongThucTe(rsBom.getDouble("SoLuong") * soluong / rsBom.getDouble("SOLUONGCHUAN"));
										phieutieuhao.getSpList().add(vattu);
										//End phiếu tiêu hao
										
									}
									
									if(phieutieuhao.getSpList().size()==0){
										return "Sản phẩm không xác định được BOM của mua hàng,vui lòng cập nhật BOM trong mua hàng";
									}
								 
									rsBom.close();
									
									//Lưu phiếu tiêu hao cho sản phẩm vào database
									if(!phieutieuhao.create(db)) { 
										String msg1 = phieutieuhao.getMsg();
										return msg1;
									}
									
									
							 }
			
					}
			}
			
		}catch(Exception err){
			err.printStackTrace();
			return err.getMessage();
		}
		return "";
	}

	private double getQuydoisoluong_veBom(double soluong, String dmvt_fk,
			String spid, dbutils db,String DVDL_FK_chuan) {
		// TODO Auto-generated method stub
		try{
			String query="select dvdl_fk from ERP_DANHMUCVATTU where PK_SEQ= "+dmvt_fk;
			System.out.println("query: "+query);
			ResultSet rs=db.get(query);
			String dvdl_bom="";
			if(rs.next()){
				dvdl_bom=rs.getString("dvdl_fk");
			}
			rs.close();
			
			if(dvdl_bom.equals(DVDL_FK_chuan)){
				return soluong;
			}else{
				
				query="select SOLUONG2,SOLUONG1 from QUYCACH where SANPHAM_FK=  "+spid+" and DVDL1_FK="+DVDL_FK_chuan+" and DVDL2_FK="+dvdl_bom;
				
				System.out.println("query : "+query); 
				
				ResultSet rsqd=db.get(query);
				double soluongmoi=0;
				if(rsqd.next()){
					  soluongmoi=soluong* rsqd.getDouble("soluong2")/rsqd.getDouble("soluong1");
					
				}else{
					soluongmoi=0;
				}
				
				rsqd.close();
				
				return soluongmoi;
				
			}
			
		}catch(Exception err){
			
		}
		return 0;
	}

	private String TangKhoKiemDinh(dbutils db, String khoId, String nhanhang_fk, String sanpham, String muahang_fk,String maphieu, String dongiaT){
		try{
			// tăng kho chi tiết
			String sql = " select isnull( a.nsx_fk,0) nsx_fk , a.MARQ as marrquet, ISNULL(A.NGAYNHAPKHO,'') AS NGAYNHAPKHO  ,a.SOLO, a.MATHUNG, a.MAME, a.BIN_FK, a.SOLUONG,a.NGAYHETHAN, a.NGAYSANXUAT, c.NGAYNHAN," +
				  " a.SANPHAM_FK,b.DONGIA , b.idmarquette, isnull((select MA from MARQUETTE m where m.PK_SEQ = b.idmarquette),'') as mamarquette " +
				  " from ERP_NHANHANG_SP_CHITIET a inner join ERP_NHANHANG_SANPHAM b " +
				  " on a.NHANHANG_FK = b.NHANHANG_FK and a.SANPHAM_FK = b.SANPHAM_FK  and a.muahang_fk = b.muahang_fk  and a.GIATHEOLO = b.DONGIA " +
				  " inner join ERP_NHANHANG c on c.PK_SEQ = b.NHANHANG_FK "+
				  " where a.SANPHAM_FK = "+sanpham+" and a.NHANHANG_FK = "+nhanhang_fk+" and a.MUAHANG_FK = "+ muahang_fk
				  + " and b.DONGIA = "+ dongiaT ;
			
			System.out.println("thong tin cap nhat kho: "+ sql); 
			String date = "";			
			Utility_Kho util_kho=new Utility_Kho();

			ResultSet rs = db.get(sql);
			 
				while(rs.next()){
					String sanpham_fk = rs.getString("SANPHAM_FK");
					String solo = rs.getString("SOLO");
					String khu_fk = rs.getString("BIN_FK");
					float soluong= rs.getFloat("SOLUONG");
					String ngayhethan= rs.getString("NGAYHETHAN");
					String ngaysanxuat = rs.getString("NGAYSANXUAT");
					double dongia = rs.getDouble("DONGIA");
					String idmarquette = rs.getString("idmarquette");
					String mamarquette = rs.getString("marrquet");
					String mathung = rs.getString("MATHUNG");
					String nsx_fk = rs.getString("nsx_fk");
					String mame = rs.getString("MAME");
					date = rs.getString("NGAYNHAPKHO");
					 
					Kho_Lib kholib=new Kho_Lib();
					kholib.setNgaychungtu(date);
					kholib.setLoaichungtu("erpnhanhangtrongnuoc_giaysvl 498: ERP_NHANHANG"+nhanhang_fk);
					kholib.setKhottId(khoId);
					
					kholib.setBinId(khu_fk);
					
					kholib.setSolo(solo);
					kholib.setSanphamId(sanpham_fk);
					
					kholib.setMame(mame);
					kholib.setMathung(mathung);
					kholib.setMaphieu("");
					
					kholib.setMaphieudinhtinh("");
					kholib.setPhieuEo("");
					
					kholib.setNgayhethan(ngayhethan);
					kholib.setNgaysanxuat(ngaysanxuat);
					
					kholib.setNgaynhapkho(date);
					 
					kholib.setMARQ(mamarquette);
					kholib.setDoituongId("");
					kholib.setLoaidoituong("");
					
					kholib.setNsxId(nsx_fk);
					
					kholib.setHamluong("100");
					kholib.setHamam("0");
					 
			    	kholib.setBooked(0);
					kholib.setSoluong(soluong);
					kholib.setAvailable(soluong);
					kholib.setDongialo(0);
					 
					String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
				    if( msg1.length() >0)
					{
				    	return msg1;
					}
					 
				}
				rs.close();
			 
			return "";
		} catch(Exception ex){
			ex.printStackTrace();
			return "Loi :"+ex.getMessage();
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
			String khoId, String userId, String loaimh, List<String> maphieuL, String dongia){
		
		// tính toán số lượng phiếu kiểm định sẽ được tạo ra
		// số phiếu kiểm định tạo ra sẽ theo mẻ.
		// 1 lô sẽ có 10 thùng, 10 thùng thuộc 2 mẻ
		// tạo ra 2 phiếu kiểm định tương ứng với 2 mẻ.
		try{
		int i;
		String query ="";
//		int soluongMe =0;
		
		query = " select a.nsx_fk, a.MARQ marrquet, nh.NGAYNHAN ,a.MAME , SUM(a.SOLUONG) as soluong,a.NGAYHETHAN, a.NGAYSANXUAT, a.solo ,b.IDMARQUETTE," +
				" isnull((select MA from MARQUETTE m where m.PK_SEQ = b.IDMARQUETTE),'' ) as mamarquette, b.DONGIAVIET as DONGIA " +
				" from ERP_NHANHANG_SP_CHITIET a  " +
				" inner join erp_nhanhang nh on nh.pk_seq= a.nhanhang_fk  " +
				"  inner join " +
				" ERP_NHANHANG_SANPHAM b on a.SANPHAM_FK = b.SANPHAM_FK and b.NHANHANG_FK = a.NHANHANG_FK and b.muahang_fk=a.muahang_fk "
				+ " and a.GIATHEOLO = b.DONGIA " +
				" where a.SANPHAM_FK = "+sanphamId+" and a.NHANHANG_FK = "+nhanhangId+" and a.MUAHANG_FK = "+ muaHangId+ " and b.DONGIA ="+dongia +
				" group by  nh.NGAYNHAN ,a.MAME, a.SOLO,a.NGAYHETHAN, a.NGAYSANXUAT, b.IDMARQUETTE, b.DONGIAVIET, a.nsx_fk, a.MARQ";
		
		
		ResultSet rs = db.get(query);
		List<String> maMe = new ArrayList<String>();
		List<Double> soluong = new ArrayList<Double>();
		List<String> ngaySanXuat = new ArrayList<String>();
		List<String> ngayHetHan = new ArrayList<String>();
		List<String> soLo = new ArrayList<String>();
		List<String> idmarquette = new ArrayList<String>();
		List<String> mamarquette = new ArrayList<String>();
		List<String> dongiaViet = new ArrayList<String>();
		List<String> nsx_fk = new ArrayList<String>();
		
		String ngaynhanhang="";
		
		 
			while(rs.next()){
				ngaynhanhang=rs.getString("NGAYNHAN");
				maMe.add(rs.getString("MAME"));
				soluong.add(rs.getDouble("soluong"));
				ngaySanXuat.add(rs.getString("NGAYSANXUAT"));
				ngayHetHan.add(rs.getString("NGAYHETHAN"));
				soLo.add(rs.getString("SOLO"));
				idmarquette.add(rs.getString("IDMARQUETTE"));
				mamarquette.add(rs.getString("marrquet"));
				dongiaViet.add(rs.getString("DONGIA"));
				nsx_fk.add(rs.getString("nsx_fk"));
			}
		 
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
		String s = timeFormat.format(today.getTime());
		for (i = 0; i < maMe.size(); i++) {
			
			// tạo phiếu kiểm định
			query =   " insert into ERP_YeuCauKiemDinh(ngaykiem,nhanhang_fk,sanpham_fk,khuvuckho_fk,solo,trangthai,ngayhethan,nguoitao,"
					+ " ngaytao,nguoisua,ngaysua,soluong,ngaysanxuat,dinhluong,dinhtinh," + "ngaynhan,KHONHAN_FK,NGAYHETHONG,"
					+ " DonGiaViet,nhapkho_fk,loaimuahang,MAME, IDMARQUETTE, HAMAM, HAMLUONG, MAMARQUETTE,nsx_fk)" + " values('"+ngaynhanhang+"',"+ nhanhangId+ ","+ sanphamId+ ",null,'"
					+ soLo.get(i)+ "','0','"+ngayHetHan.get(i)+"',"+ userId+ ",'"+ s+ "',"+ ""+ userId+ ",'"+ s+ "',"
					+ soluong.get(i)+ ",'"+ngaySanXuat.get(i)+"','"
					+ dinhluong+ "','"+ dinhtinh+ "','',"+ khoId+ ",'"+ s+ "',"
					+ dongiaViet.get(i)+ ",null,'"+ loaimh + "','" + maMe.get(i)+"',"+idmarquette.get(i)+",0,100,'"+mamarquette.get(i)+"',"+nsx_fk.get(i)+")";

			if (db.updateReturnInt(query) != 1) {
				db.getConnection().rollback();
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
			
			int check = db.updateReturnInt(query);
			if(check !=1){
				return " Không cập nhật được mã phiếu";
			}
			maphieuL.add(maphieu);
			
			
			
			// Lấy dữ liệu về mã thùng và số lượng để insert vào bảng ERP_YEUCAUKIEMDINH_THUNG
			query = " select MATHUNG,sum(SOLUONG) as soluong , BIN_FK from ERP_NHANHANG_SP_CHITIET a where NHANHANG_FK = "+nhanhangId
					+" and SANPHAM_FK = "+sanphamId+" " +" and MUAHANG_FK = "+muaHangId+" and SOLO= '"+soLo.get(i)
					+"' and MAME= '"+maMe.get(i)+"' and GIATHEOLO ="+ dongia +" group by  MATHUNG,BIN_FK " ;
			
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
						" SOLUONGKHONGDAT, MALO, BIN_FK)" +
					" values("+idKiemDinh+",'"+ maThung.get(j)+"',"+soLuongThung.get(j)+",0,"+soLuongThung.get(j)+",0,'"+maLoThung+"',"+khuVuc.get(j)+")";
				int k = db.updateReturnInt(query);
				if( k!=1){
					return "Lỗi lưu chi tiết số thùng trong kiểm định";
				}
			}
			
			// chỗ này tăng kho kiểm định lun
			// tăng kho chờ xử lý
			// đang tạo cho từng sản phẩm
			
			String msg1  = TangKhoKiemDinhMaMe(db, khoId, nhanhangId,sanphamId, muaHangId, maphieu,maMe.get(i), soLo.get(i), dongia);
			if(msg1.length() >0){
				db.getConnection().rollback();
				return "88.Không thể chôt nhận hàng, vui lòng báo Admin: không tăng kho    được phiếu kiểm định :"+msg1;
			}
		}
		return "";
		} catch(Exception ex){
			db.update("rollback");
			ex.printStackTrace();
			return "Lỗi "+ex.getMessage();
		}
		
		
	}
	
	
	public static void main ( String args [  ]  )   {
		dbutils db=new dbutils();
		try{ 
			
			 int n=10;
			 System.out.println(n%10);
			 System.out.println(n/10);
			 
			 
			
			 
			 
		}catch(Exception err){
			err.printStackTrace();
		}
	 
   }
	private static String Revert_Nhanhang(dbutils db, String nhId) {
		// TODO Auto-generated method stub
		try{
			
			db.getConnection().setAutoCommit(false);
			
			String  query="SELECT pk_seq FROM ERP_YEUCAUKIEMDINH where trangthai in (1,2) and nhanhang_fk="+nhId;

			ResultSet rscheck=db.get(query);
			while(rscheck.next()){
				String msg1=  Revert_yckD(db,rscheck.getString("pk_seq"));
				
				if(msg1.length() > 0 ){
					db.getConnection().rollback();
					return msg1;
					
				}
			}
			rscheck.close();
	 
			
			Utility_Kho util_kho=new Utility_Kho();
			
			
			  query=" 	select  bin.pk_seq AS BIN_FK ,a.KHOCHOXULY_FK as kho_fk, b.SANPHAM_FK, N'Nhận hàng ' as loaichungtu, cast(a.PK_SEQ as varchar(10)) as machungtu, a.NGAYNHAN as ngaychungtu, a.NGAYCHOT, "+
						" bin.ma as vitri, b.solo, b.ngayhethan, a.NGAYNHAN as ngaynhapkho, b.mame, b.mathung, '' as maphieu, '' as phieudt, '' as phieueo, ISNULL( e.MA, '' ) as MARQ, 0 as hamam, 100 as hamluong,	b.soluong as NHAP, 0 as XUAT "+ 
						" from ERP_NHANHANG a inner join  ERP_NHANHANG_SP_CHITIET b on a.pk_seq = b.NHANHANG_FK "+
						" left join ERP_BIN bin on b.bin_fk = bin.pk_seq "+
						" inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ  "+
						" inner join ERP_NHANHANG_SANPHAM d on c.PK_SEQ = d.SANPHAM_FK and b.NHANHANG_FK = d.NHANHANG_FK "+
						" left join MARQUETTE e on d.IDMARQUETTE = e.PK_SEQ "+
						" where a.trangthai = '1' and ( c.BATBUOCKIEMDINH =0 ) and a.pk_seq="+nhId+
						" union all "+
						" select bin.pk_seq AS BIN_FK , a.KHONHAN_FK as kho_fk, b.SANPHAM_FK, N'Nhận hàng ' as loaichungtu, cast(a.PK_SEQ as varchar(10)) as machungtu, a.NGAYNHAN as ngaychungtu, a.NGAYCHOT, "+
						" bin.ma as vitri, b.solo, b.ngayhethan, a.NGAYNHAN as ngaynhapkho, b.mame, b.mathung, '' as maphieu, '' as phieudt, '' as phieueo,  ISNULL( e.MA, '' ) as MERQ, 0 as hamam, 100 as hamluong,	b.soluong as NHAP, 0 as XUAT "+
						" from ERP_NHANHANG a inner join  ERP_NHANHANG_SP_CHITIET b on a.pk_seq = b.NHANHANG_FK "+
						" left join ERP_BIN bin on b.bin_fk = bin.pk_seq "+
						" inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ  "+
						" inner join ERP_NHANHANG_SANPHAM d on c.PK_SEQ = d.SANPHAM_FK and b.NHANHANG_FK = d.NHANHANG_FK "+
						" left join MARQUETTE e on d.IDMARQUETTE = e.PK_SEQ "+
						" where a.trangthai = '1' and ( c.BATBUOCKIEMDINH = 1) and a.pk_seq="+ nhId; 
			
			System.out.println("du lieu  : "+query);
			ResultSet rs=db.get(query);
			while(rs.next()){
				
				
				Kho_Lib kholib=new Kho_Lib();
				String ngaychungtu=rs.getString("ngaychungtu");
				kholib.setLoaichungtu("erpnhanhangtrongnuoc_giaysvl 791 :revert ERP_NHANHANG"+nhId);
				
				kholib.setNgaychungtu(ngaychungtu);
				String khochuyen= rs.getString("kho_fk");
				kholib.setKhottId(khochuyen);
				
				kholib.setBinId("0");
				String solo= rs.getString("solo");
				
				kholib.setSolo(solo);
				String spid= rs.getString("SANPHAM_FK");
				kholib.setSanphamId(spid);
				
				
				kholib.setMame(rs.getString("MAME"));
				kholib.setMathung(rs.getString("mathung"));
				kholib.setMaphieu(rs.getString("maPHIEU"));
				
				kholib.setMaphieudinhtinh(rs.getString("phieudt"));
				kholib.setPhieuEo(rs.getString("phieueo"));
				
				kholib.setNgayhethan(rs.getString("ngayhethan"));
				kholib.setNgaysanxuat("");
				
				kholib.setNgaynhapkho(rs.getString("ngaynhapkho"));
				 
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				kholib.setBinId(rs.getString("BIN_FK"));
				
				kholib.setHamluong(rs.getString("hamluong"));
				kholib.setHamam(rs.getString("hamam"));
				 
		    	kholib.setBooked(0);
				kholib.setSoluong((-1)*rs.getFloat("NHAP"));
				kholib.setAvailable((-1)*rs.getFloat("NHAP"));
			     
				
				
				String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					// this.msg = msg1;
					db.getConnection().rollback();
					return msg1;
					
				}

			}
			rs.close();
			
			query=" UPDATE ERP_YEUCAUKIEMDINH   SET TRANGTHAI=3  where nhanhang_fk="+nhId;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			// sửa luôn, khi hủy nhận hàng thì hủy luôn 
			
			query=" UPDATE ERP_nhanhang   SET TRANGTHAI=3   where pk_seq="+nhId;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			query="update ERP_PARK set HOANTAT_NHANHANG='0' where pk_seq=(select park_fk from erp_hoadonncc where pk_Seq= (select hdncc_fk from erp_nhanhang where pk_Seq="+nhId+")) ";
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			
			 db.getConnection().commit();
			 db.getConnection().setAutoCommit(true);
			 
			return "";
		}catch(Exception err){
			err.printStackTrace();
			return err.getMessage();
		}
	
	}
	

	private static String XoaChuyenKho(String lsxId, dbutils db) 
	{
		String msg = "";
		 
		
		try
		{ 
			
			//TRỪ KHO
			Utility util = new Utility();
		String	query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' and b.trangthai=0 " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			 
				while( rs.next() )
				{
					String khoId = rs.getString("KhoXuat_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
					String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong = rs.getDouble("soluong");
					
					msg = util.Update_KhoTT(rs.getString("NGAYCHUYEN"), "Cập nhật CK - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong);
					if( msg.trim().length() > 0 )
					{
						msg = "Lỗi khi xóa: " + msg;
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
				}
				rs.close();
			 

				query = "update ERP_CHUYENKHO set trangthai = '4' where pk_seq = '" + lsxId + "' ";
				if( !db.update(query) )
				{
					msg = "Lỗi khi xóa: " + query;
					db.getConnection().rollback();
				 
					return msg;
				}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
		}
		
		return msg;
	}


	private static String Huychungtu_Chuyenkho(String ckid, String trangthai, dbutils db) {
		// TODO Auto-generated method stub
		// trạng thái ,1,2,3 là đã xuất kho 
		String msg1="";
		try{
		
			if(trangthai.equals("0")){
				  msg1=	XoaChuyenKho(ckid,db);
				 
			}else{
				  msg1=	Revert_ChuyenKho(ckid,db);	
				 
			}
			
		}catch(Exception er){
			er.printStackTrace();
			msg1=er.getMessage();
		}
		return msg1;
		
	}
	
	private static String Revert_ChuyenKho(String lsxId ,dbutils db) 
	{
		String msg = "";
	 
		
		try
		{
		 
			 
			//TRỪ KHO
			Utility util = new Utility();
			String query =  "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
							" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
							"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
							"  where b.PK_SEQ = '" + lsxId + "' and b.trangthai in (1,2,3)  " + 
							"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, " +
							" a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, " +
							" a.bin_fk, a.phieudt, a.phieueo  ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			 
				while( rs.next() )
				{
					String khoId = rs.getString("KhoXuat_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
					String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong =   rs.getDouble("soluong");
					
					msg = util.Update_KhoTT(rs.getString("NGAYCHUYEN"), "Cập nhật CK - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong);
					System.out.println("Lõi 1 : " +msg);
					
					if( msg.trim().length() > 0 )
					{
						msg = "Lỗi khi xóa: " + msg;
						db.getConnection().rollback();
					 
						return msg;
					}
					 
				}
				rs.close();
					
	// giam  kho nhan
	
	  query =   " select b.khonhan_fk ,b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, " +
		  		" a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
				" a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.binNhan_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
				" from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
				" where b.PK_SEQ = '" + lsxId + "' and b.trangthai=3 " + 
				" group by b.khonhan_fk, b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, " +
				" a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, " +
				" a.binNhan_fk, a.phieudt, a.phieueo ";
	
		  //System.out.println("::: CAP NHAT KHO: " + query);
		    rs = db.get(query);
			while( rs.next() )
			{
				String khoId = rs.getString("khonhan_fk");
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				
				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
				
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk");
				
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");
				
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");

				double soluong = (-1)* rs.getDouble("soluong");
				
				msg = util.Update_KhoTT(rs.getString("NGAYCHUYEN"), "Hủy CK - Trừ kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong);
				
				System.out.println("Lõi: " +msg);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi xóa: " + msg;
					db.getConnection().rollback();
				 
					return msg;
				}
				 
			}
			rs.close();
			
			
		 	query = "update ERP_CHUYENKHO set trangthai = '4' where pk_seq = '" + lsxId + "' ";
			if( !db.update(query) )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				 
				return msg;
			}
					
			 
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			 
		}
		
		return msg;
	}


	private static String Revert_yckD (dbutils db, String nhId) {
		// TODO Auto-generated method stub
		try{
			
			String  query="SELECT * FROM ERP_CHUYENKHO where trangthai  <>4  and YCKD_FK ="+nhId;

			ResultSet rscheck=db.get(query);
			while  (rscheck.next()){
				 String msg1=Huychungtu_Chuyenkho(rscheck.getString("pk_seq"), rscheck.getString("trangthai"),db);
				 if(msg1.length() >0){
					 return msg1;
				 }
			}
			rscheck.close();
		 
			
			Utility_Kho util_kho=new Utility_Kho();
			
	  
			  query=" select  bin.pk_seq AS BIN_FK ,a.KHONHAN_FK as kho_fk, a.SANPHAM_FK,  N'Yêu cầu kiểm định' as loaichungtu, isnull(a.MAPHIEU, '') as machungtu, a.ngaykiem as ngaychungtu, a.ngaykiem as ngaychot, " + 
				  "			bin.ma as vitri, a.solo, a.ngayhethan, b.ngaynhapkho, a.mame, b.mathung, b.maphieu, '' as phieudt, '' as phieueo, isnull(a.MAMARQUETTE, '') as MARQ, isnull(b.hamam, 0) as hamam, isnull(b.hamluong, 100) as hamluong,  " + 
				  "			case when b.soluongDAT > 0 then b.soluongDAT else 0 end as NHAP,  " + 
				  "			case when b.soluongDAT < 0 then -1 * b.soluongDAT else 0 end as XUAT   " + 
				  "	from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG_CHITIET b on a.pk_seq = b.YEUCAUKIEMDINH_FK " + 
				  "			left join ERP_BIN bin on b.bin_fk = bin.pk_seq " + 
				  "	where a.trangthai = '2' AND  A.PK_SEQ= "+nhId+ 
				  " " + 
				  "	union all " + 
				  "	select   bin.pk_seq AS BIN_FK , a.KHONHAN_FK as kho_fk, a.SANPHAM_FK,  N'Yêu cầu kiểm định' as loaichungtu, isnull(a.MAPHIEU, '') as machungtu, a.ngaykiem as ngaychungtu, a.ngaykiem as ngaychot, " + 
				  "			bin.ma as vitri, a.solo, a.ngayhethan, a.ngaykiem, a.mame, b.mathung, isnull(a.MAPHIEU, '') as maphieu, '' as phieudt, '' as phieueo, isnull(a.MAMARQUETTE, '') as MERQ, isnull(b.hamam, 0) as hamam, isnull(b.hamluong, 100) as hamluong, b.soluongMAU as NHAP, 0 as XUAT   " + 
				  "	from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG b on a.pk_seq = b.YEUCAUKIEMDINH_FK " + 
				  "			left join ERP_BIN bin on b.bin_fk = bin.pk_seq " + 
				  "	where a.trangthai = '2'  AND  A.PK_SEQ= "+nhId+ 
				  "	 " + 
				  "	union all " + 
				  "	select  bin.pk_seq AS BIN_FK , a.KHONHAN_FK as kho_fk, a.SANPHAM_FK,  N'Yêu cầu kiểm định' as loaichungtu, isnull(a.MAPHIEU, '') as machungtu, a.ngaykiem as ngaychungtu, a.ngaykiem as ngaychot, " + 
				  "			bin.ma as vitri, a.solo, a.ngayhethan, a.ngaykiem, a.mame, b.mathung,'' as maphieu, '' as phieudt, '' as phieueo, isnull(a.MAMARQUETTE, '') as MERQ, isnull(b.hamam, 0) as hamam, isnull(b.hamluong, 100) as hamluong, 0 as NHAP, b.soluongMAU as XUAT   " + 
				  "	from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG b on a.pk_seq = b.YEUCAUKIEMDINH_FK " + 
				  "			left join ERP_BIN bin on b.bin_fk = bin.pk_seq " + 
				  "	where a.trangthai = '2' AND  A.PK_SEQ= "+nhId ;
			
			
		 
			
			
			 System.out.println("du lieu  : "+query);
			ResultSet rs=db.get(query);
			while(rs.next()){
				
				
				Kho_Lib kholib=new Kho_Lib();
				String ngaychungtu=rs.getString("ngaychungtu");
				kholib.setLoaichungtu("erpnhanhangtrongnuoc_giaysvl 1160 :revert ERP_YEUCAUKIEMDINH"+nhId);
				
				kholib.setNgaychungtu(ngaychungtu);
				String khochuyen= rs.getString("kho_fk");
				kholib.setKhottId(khochuyen);
				
				kholib.setBinId("0");
				String solo= rs.getString("solo");
				
				kholib.setSolo(solo);
				String spid= rs.getString("SANPHAM_FK");
				kholib.setSanphamId(spid);
				
				
				kholib.setMame(rs.getString("MAME"));
				kholib.setMathung(rs.getString("mathung"));
				kholib.setMaphieu(rs.getString("maPHIEU"));
				
				kholib.setMaphieudinhtinh(rs.getString("phieudt"));
				kholib.setPhieuEo(rs.getString("phieueo"));
				
				kholib.setNgayhethan(rs.getString("ngayhethan"));
				kholib.setNgaysanxuat("");
				
				kholib.setNgaynhapkho(rs.getString("ngaynhapkho"));
				 
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				kholib.setBinId(rs.getString("BIN_FK"));
				
				kholib.setHamluong(rs.getString("hamluong"));
				kholib.setHamam(rs.getString("hamam"));
				 
			    kholib.setBooked(0);
			    	
		    	if(rs.getFloat("NHAP") >0){
		    		kholib.setSoluong((-1)*rs.getFloat("NHAP"));
					kholib.setAvailable((-1)*rs.getFloat("NHAP"));
		    	}else{
		    		kholib.setSoluong( rs.getFloat("xuat"));
					kholib.setAvailable( rs.getFloat("xuat"));
					kholib.setHamluong("100");
					kholib.setHamam("0");
					
					
		    	}
					
		    	String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					// this.msg = msg1;
					db.getConnection().rollback();
					return msg1;
					
				}
				
			

			}
			rs.close();
			
			query=" UPDATE ERP_YEUCAUKIEMDINH   SET TRANGTHAI=0 where pk_seq ="+nhId;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			query="delete ERP_KIEMDINHCHATLUONG_LANDUYET WHERE YEUCAUKIEMDINH_FK="+nhId;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			 
			query="delete ERP_YEUCAUKIEMDINH_THUNG_CHITIET WHERE YEUCAUKIEMDINH_FK="+nhId;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			
		 
			return "";
		}catch(Exception err){
			err.printStackTrace();
			return err.getMessage();
		}
	 
	}

	private String TangKhoKiemDinhMaMe(dbutils db, String khoId, String nhanhang_fk, String sanpham,
			String muahang_fk, String maphieu, String MaMe, String SoLo, String dongiaT){

		try{
			// tăng kho chi tiết
			String sql = " select a.nsx_fk, a.MARQ marrquet, a.SOLO, a.MATHUNG, a.MAME, a.BIN_FK, a.SOLUONG,a.NGAYHETHAN, a.NGAYSANXUAT, ISNULL(a.NGAYNHAPKHO,'') AS NGAYNHAPKHO  , c.NGAYNHAN," +
				  " a.SANPHAM_FK,b.DONGIA , b.idmarquette, isnull((select MA from MARQUETTE m where m.PK_SEQ = b.idmarquette),'') as mamarquette " +
				  " from ERP_NHANHANG_SP_CHITIET a inner join ERP_NHANHANG_SANPHAM b " +
				  " on a.NHANHANG_FK = b.NHANHANG_FK and a.SANPHAM_FK = b.SANPHAM_FK and a.muahang_fk = b.muahang_fk and a.GIATHEOLO = b.DONGIA  " +
				  " inner join ERP_NHANHANG c on c.PK_SEQ = b.NHANHANG_FK "+
				  " where a.SANPHAM_FK = "+sanpham+" and a.NHANHANG_FK = "+nhanhang_fk+" " +
				  " and a.MUAHANG_FK = "+ muahang_fk +" and a.MAME ='" +MaMe +"' and a.SOLO ='"+SoLo+"'and b.DONGIA = "+ dongiaT;
			 
			 
			
			ResultSet rs = db.get(sql);
	 
				while(rs.next()){
					String sanpham_fk = rs.getString("SANPHAM_FK");
					String solo = rs.getString("SOLO");
					String khu_fk = rs.getString("BIN_FK");
					float soluong= rs.getFloat("SOLUONG");
					String ngayhethan= rs.getString("NGAYHETHAN");
					String ngaysanxuat = rs.getString("NGAYSANXUAT");
					double dongia = rs.getDouble("DONGIA");
					String idmarquette = rs.getString("idmarquette");
					String mamarquette = rs.getString("marrquet");
					String mathung = rs.getString("MATHUNG");
					String mame = rs.getString("MAME");
					String nsx_fk = rs.getString("nsx_fk");
					
					String date = rs.getString("NGAYNHAPKHO");
					  
					Kho_Lib kholib=new Kho_Lib();
					kholib.setNgaychungtu(date);
					kholib.setLoaichungtu("erpnhanhangtrongnuoc_giaysvl 1292 :  ERP_NHANHANG"+nhanhang_fk);
					kholib.setKhottId(khoId);
					
					kholib.setBinId(khu_fk);
					
					kholib.setSolo(solo);
					kholib.setSanphamId(sanpham_fk);
					
					kholib.setMame(mame);
					kholib.setMathung(mathung);
					// Mã phiếu phải bằng "", khi kiểm định mới có mã phiếu 
					kholib.setMaphieu("");
					kholib.setNsxId(nsx_fk);
					
					kholib.setMaphieudinhtinh("");
					kholib.setPhieuEo("");
					
					kholib.setNgayhethan(ngayhethan);
					kholib.setNgaysanxuat(ngaysanxuat);
					
					kholib.setNgaynhapkho(date);
					 
					kholib.setMARQ(mamarquette);
					kholib.setDoituongId("");
					kholib.setLoaidoituong("");
				 
					kholib.setHamluong("100");
					kholib.setHamam("0");
					 
			    	kholib.setBooked(0);
					kholib.setSoluong(soluong);
					kholib.setAvailable(soluong);
				     
					Utility_Kho util_kho=new Utility_Kho();
					
					String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
				    if( msg1.length() >0)
					{
				    	 
						db.getConnection().rollback();
						return msg1;
						
					}
				  
				}
				rs.close();
			  
			return "";
		} catch(Exception ex){
			ex.printStackTrace();
			return "Lỗi trong quá trình cập nhật kho : "+ex.getMessage();
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
	    	nhBean.setLoaimh(loaimh);
	    	nhBean.setLdnId("100000");
	    	nhBean.createRs();
    		
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangtrongnuocNew_Giay.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpNhanhangList_Giay();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setLoaimh(loaimh);
		    	this.getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHangtrongnuoc_Giay.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpNhanhangList_Giay();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setLoaimh(loaimh);
		    	this.getSearchQuery(request, obj);
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHangtrongnuoc_Giay.jsp");
	    	}
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpNhanhangList_Giay obj)
	{
//		Utility util=new Utility();
//		String query = " SELECT distinct a.PK_SEQ as nhId, ncc.Ten as nccTen, \n"+
//				" isnull(case when a.hdNCC_fk is null then isnull(a.SOHOADON,'') else ( select isnull(kyhieu,'') + ' ' + CAST(sohoadon as varchar(50)) as ten  from ERP_HOADONNCC where pk_seq = a.hdNCC_fk ) end, '') as SOHOADON, a.NGAYNHAN, b.TEN as dvthTen, \n"+
//				" c.sopo as PoId, \n"+
//				" b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, \n"+
//				" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, a.loaihanghoa_fk, \n"+
//				" case when a.hdNCC_fk IS null then 0  when a.hdNCC_fk IS NOt null then 1  else -1 end DaRaHd, a.tooltip \n"+
//				" FROM erp_nhanhang a "+
//				" inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ \n"+
//				" left join DonTraHang th on a.TRAHANG_FK = th.PK_SEQ \n"+
//				" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  \n"+
//				" inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n"+
//				" inner join ERP_MUAHANG c on  a.muahang_fk= c.pk_seq \n"+  
//				" INNER JOIN ERP_HOADONNCC f ON A.HDNCC_FK = f.PK_SEQ \n" +
//				" INNER JOIN ERP_PARK g ON f.PARK_FK = g.PK_SEQ  \n" +  
//				" left join ERP_NHACUNGCAP ncc on g.NCC_FK = ncc.pk_seq   \n"+
//		 " where c.LOAI = "+ obj.getLoaimh() +" and a.congty_fk = '" + obj.getCongtyId() + "' and c.SOPO is not null  ";
//		
		 		//" and b.pk_seq in  "+ util.quyen_donvithuchien(obj.getUserId()) ; 		
		
		String khonhanId = request.getParameter("khonhanId");
		if( khonhanId == null){
			khonhanId = "";
		}
		obj.setKhonhanId(khonhanId);
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
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
		
//		if( khonhanId.length() >0){
//			query +=" and a.khonhan_fk ='"+ khonhanId+"'";
//		}
//		if(tungay.length() > 0)
//			query += " and a.ngaynhan >= '" + tungay + "'";
//		
//		if(denngay.length() > 0)
//			query += " and a.ngaynhan <= '" + denngay + "'";
//		
//		if(dvthId.length() > 0)
//			query += " and b.pk_seq = '" + dvthId + "'";
//		
//		if(nguoitao.trim().length() > 0)
//			query += " AND a.nguoitao = '" + nguoitao + "' ";
//		
//		
//		if(soPo.length() > 0)
//		{
//			//query += " and ( CAST(c.PK_SEQ as varchar(10)) + '/' + SUBSTRING(c.NGAYMUA, 6, 2) + '/' + SUBSTRING(c.NGAYMUA, 0, 5) like '%" + soPo + "%' ) ";
//		
//			query += "  AND ( c.sopo like '%" + soPo.trim() + "%' ) ";
//		}
//		
//		if(trangthai.length() > 0)
//			query += " and a.trangthai = '" + trangthai + "'";
//		
//		if(sonhanhang.trim().length() > 0)
//		{
//			query += " and b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) like N'%" + sonhanhang + "%'  ";
//		}
//		
//		if(sohoadon.trim().length() > 0)
//		{
//			query += " and ( select isnull(kyhieu,'') + ' ' + CAST(sohoadon as varchar(50)) as ten  from ERP_HOADONNCC where pk_seq = a.hdNCC_fk ) like N'%" + sohoadon + "%' ";
//		}
//		
//		if(ncc.trim().length() > 0)
//		{
//			query += " and (  ( ( c.pk_seq is not null ) and ( c.NHACUNGCAP_FK in ( select pk_seq from ERP_NHACUNGCAP where ma like N'%" + ncc + "%' or ten like N'%" + ncc + "%' ) ) )              " +
//					 "     or ( ( th.pk_seq is not null ) and ( th.KHACHHANG_FK in  ( select pk_seq from ERP_KHACHHANG where ma like N'%" + ncc + "%' and ten like N'%" + ncc + "%'  )  ) ) )   ";
//		}
//		
//		
//		geso.dms.center.util.Utility  util2= new geso.dms.center.util.Utility();
//		if(mactsp.trim().length() > 0)
//		{
//			/*query +="\n and a.pk_seq in (" +
//			"\n     select distinct nhanhang_fk from erp_nhanhang_sanpham where sanpham_fk in "+
//			" \n ( select distinct pk_seq from ERP_SANPHAM where upper(TIMKIEM) like upper(N'%"+  util2.replaceAEIOU( mactsp) + "%') ) " +
//			" ) ";*/
//			
//			
//			query +="\n and a.pk_seq in (" +
//			"\n     select distinct nhanhang_fk from erp_nhanhang_sanpham where sanpham_fk= "+  mactsp+  ") " ;
//		}
//		
//		//query += " order by a.NGAYNHAN desc, a.trangthai asc, a.pk_seq desc;";
//
//		System.out.println(query);
//		return query;
	}
	
	private String Delete(String nhId, dbutils db)
	{
		try 
		{
//			String msg = "";
//			String query = "";
//			ResultSet rs;
			
//			//Xóa nhừng nhận hàng tự động được sinh ra từ nhận hàng đang xóa
//			query = "select pk_seq from ERP_NHANHANG where nhanhanggoc_fk = '" + nhId + "'";
//			System.out.println("0. " + query);
//			rs = db.get(query);
//			if(rs.next())
//			{
//				msg = Delete(rs.getString("pk_seq"), db);
//				if(msg.length() > 0)
//					return msg;
//			}
//			
//			// Lấy thông tin nhận hàng cần xóa
//			query = "select isnull(istudong,0) istudong, trangthai, nhanhanggoc_fk, khonhan_fk, khachhangkygui_fk, NHAPHANPHOI_FK, NCC_KH_FK, NHOMKENH_FK "+
//					"from ERP_NHANHANG where pk_seq = '" + nhId + "'";
//			System.out.println("1. " + query);
//			rs = db.get(query);
//			int istudong = 0;
//			String trangthai = "", nhgoc = "", khonhan = "", khachhangkygui = "", npp = "", ncc = "", nhomkenh = "";
//			
//			if(rs!= null){
//				while(rs.next()){
//					istudong = rs.getInt("istudong");
//					trangthai = rs.getString("trangthai");
//					nhgoc = rs.getString("nhanhanggoc_fk");
//					khonhan = rs.getString("nhanhanggoc_fk");
//					khachhangkygui = rs.getString("khachhangkygui_fk")==null?"":rs.getString("khachhangkygui_fk");
//					npp = rs.getString("NHAPHANPHOI_FK")==null?"":rs.getString("NHAPHANPHOI_FK");
//					ncc = rs.getString("NCC_KH_FK")==null?"":rs.getString("NCC_KH_FK");
//					nhomkenh = rs.getString("NHOMKENH_FK")==null?"100000":rs.getString("NHOMKENH_FK");
//				}rs.close();
//					
//			}
			
			// Nhận hàng tự động khi xóa phải tăng kho Ký gửi ncc, trừ kho nhận
//			if(istudong > 0)
//			{
//				if(trangthai.equals("1"))
//				{
//					String spid = "", solo = "", soluong = "", ngayhethan = ""; 
//					query = "select SANPHAM_FK, SOLO, SOLUONG, NGAYHETHAN from ERP_NHANHANG_SP_CHITIET where NHANHANG_FK = "+nhId;
//					System.out.println("2. " + query);
//					rs = db.get(query);
//					if(rs!= null){
//						while(rs.next()){
//							spid = rs.getString("sanpham_fk");
//							solo = rs.getString("solo");
//							soluong = rs.getString("soluong");
//							ngayhethan = rs.getString("ngayhethan");
//							
//							//kiểm tra kho nhận xem còn hàng để trả lại k?
//							if(khachhangkygui.trim().length() > 0)
//							{
//								query = 
//								"select soluong, booked, available from nhapp_kho_kygui_chitiet "+
//								"where kho_fk = "+khonhan+" and npp_fk = "+npp+" and nhomkenh_fk = "+nhomkenh+" "+
//								"and sanpham_fk = "+spid+" and solo = '"+solo+"' and ngayhethan = '"+ngayhethan+"' and khachhang_fk = "+khachhangkygui;
//							}
//							else
//							{
//								query = 
//								"select soluong, booked, available from nhapp_kho_chitiet "+
//								"where kho_fk = "+khonhan+" and npp_fk = "+npp+" and nhomkenh_fk = "+nhomkenh+" "+
//								"and sanpham_fk = "+spid+" and solo = '"+solo+"' and ngayhethan = '"+ngayhethan+"'";
//							}
//							
//						}rs.close();
//							
//					}
//				}
//				
//				return "Phiếu nhận hàng này tự động phát sinh. Bạn không được xóa ";
//			}
				
			String query = "update ERP_NHANHANG set trangthai = '3' where pk_seq = '" + nhId + "' and trangthai=0";
			
			if(db.updateReturnInt(query)!=1)
			{
				return "Không thể cập nhật nhận hàng";
			}
		
			
			
			// bo sung dum voi
			boolean check = this.UpdateHoanTatNhanHang(false, nhId, db) ;
			if(check == false){
				return "Không thể cập nhật trang thai hoan tat nhận hàng";
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
	
	private String Tinh_ChiPhiLuuKho(String nhId, String userId, String ctyId, dbutils db)
	{
		String msg = "";
		try 
		{
			// KIỂM TRA XEM NHẬN HÀNG LÀ NHẬP KHẨU HAY TRONG NƯỚC
			
			String query = "SELECT distinct HD.LOAIHD "
						+  "FROM ERP_NHANHANG NH INNER JOIN ERP_HOADONNCC HD ON NH.hdNCC_fk = HD.pk_seq "
						+  "WHERE NH.PK_SEQ = "+nhId;
			
			System.out.println(query);
			ResultSet rs = db.get(query);
			
			String loaid = "";
			try 
			{
				if(rs.next())
				{
					loaid = rs.getString("LOAIHD");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();			
			}
			
			String sql ="";
			if(loaid.equals("0"))// NHẬP KHẨU
			{
				sql = "select distinct NH.PK_SEQ NHANHANG_FK,NH.NGAYNHAN NGAYNHANHANG \n"+
					  "		, NK.NGAYCHOT NGAYNHAPKHO, NHSP.SANPHAM_FK, \n"+ 
					  "	   NHSP.SOLO, NHSP.SOLUONG, NKSP.SONGAYLUUKHO, (DATEDIFF (day, NK.NGAYNHAP, NH.NGAYNHAN) - NKSP.SONGAYLUUKHO) SONGAYLUUKHOTINH, 1 DONGIA, NHSP.MUAHANG_FK \n"+
					  "	from ERP_NHANHANG NH INNER JOIN ERP_HOADONNCC HD ON NH.hdNCC_fk = HD.pk_seq \n"+
					  "	INNER JOIN ( SELECT distinct A.MUAHANG_FK, A.HOADONNCC_FK FROM ERP_HOADONNCC_DONMUAHANG A ) HD_NK ON HD.pk_seq = HD_NK.HOADONNCC_FK \n"+
					  "	INNER JOIN ERP_NHAPKHONHAPKHAU NK ON HD_NK.MUAHANG_FK = NK.PK_SEQ \n"+
					  " INNER JOIN ERP_NHAPKHONHAPKHAU_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHAPKHO_FK \n"+
					  "	INNER JOIN ERP_NHANHANG_SP_CHITIET NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK AND NKSP.SANPHAM_FK = NHSP.SANPHAM_FK \n"+
					  "	WHERE NH.PK_SEQ = "+nhId+" ";
				
				System.out.println(sql);
				
				ResultSet rs1 = db.get(sql);
				
				String ngaynhanhang = "";
				String ngaynhapkho = "";
				String sanphamId = "";
				String solo = "";
				String muahangId = "";
				
				double soluong = 0;
				double dongia = 0;
				String songayluukho = "";
				double songayluukhotinh = 0;
				
				double thanhtien = 0;
				try 
				{
					if(rs1.next())
					{
						ngaynhanhang = rs1.getString("NGAYNHANHANG");
						ngaynhapkho = rs1.getString("NGAYNHAPKHO");
						sanphamId = rs1.getString("SANPHAM_FK");
						solo = rs1.getString("SOLO");
						soluong = rs1.getDouble("SOLUONG");
						songayluukho = rs1.getString("SONGAYLUUKHO");
						songayluukhotinh = rs1.getDouble("SONGAYLUUKHOTINH");
						dongia = rs1.getDouble("DONGIA");
						muahangId = rs1.getString("MUAHANG_FK");
						thanhtien = soluong*songayluukhotinh*dongia;
						
						if(thanhtien>0)
						{
							String add = " INSERT ERP_CHIPHILUUKHO_SP_CHITIET (NHANHANG_FK, SANPHAM_FK, SOLO, SOLUONG, DONGIA, NGAYNHANHANG, NGAYNHAPKHO, SONGAYLUUKHO, THANHTIEN, MUAHANG_FK)  "	
										+" values ("+nhId+", "+sanphamId+",'"+solo+"', "+soluong+" , "+dongia+", '"+ngaynhanhang+"', '"+ngaynhapkho+"', '"+songayluukho+"', '"+thanhtien+"', "+muahangId+")";
							
							System.out.println("" + add);
	
							if (!db.update(add)) {
								msg = "Khong the tao moi Nhan hang: " + query;
								db.getConnection().rollback();
								return msg;
							}
							
						}
					}
					rs1.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();			
				}
				
				
			}
			
			if(loaid.equals("1"))// TRONG NƯỚC
			{
				sql =     "select A.PK_SEQ NHANHANG_FK,A.NGAYNHAN NGAYNHANHANG,D.NGAYNHAP NGAYNHAPKHO, \n"+
						  " NHSP.SANPHAM_FK, NHSP.SOLO, NHSP.SOLUONG, 0 SONGAYLUUKHO, (DATEDIFF (day,D.NGAYNHAP, A.NGAYNHAN)) SONGAYLUUKHOTINH, 1 DONGIA, NHSP.MUAHANG_FK \n"+
						  " from erp_nhanhang A INNER JOIN erp_phanbomuahang_po B ON A.MUAHANG_FK = B.poduocpb \n"+
						  " INNER JOIN erp_phanbomuahang C ON B.PHANBO_FK = C.PK_SEQ INNER JOIN ERP_NHAPKHONHAMAY D ON C.MUAHANG_FK = D.MUAHANG_FK \n"+
						  " INNER JOIN ERP_NHANHANG_SP_CHITIET NHSP ON A.PK_SEQ = NHSP.NHANHANG_FK \n"+ 
						  " WHERE A.PK_SEQ = "+nhId+" \n";
				System.out.println(sql);
				
				ResultSet rs2 = db.get(sql);	
				
				String ngaynhanhang = "";
				String ngaynhapkho = "";
				String sanphamId = "";
				String solo = "";
				String muahangId = "";
				double soluong = 0;
				double dongia = 0;
				String songayluukho = "";
				double songayluukhotinh = 0;
				
				double thanhtien = 0;
				try 
				{
					if(rs2.next())
					{
						ngaynhanhang = rs2.getString("NGAYNHANHANG");
						ngaynhapkho = rs2.getString("NGAYNHAPKHO");
						sanphamId = rs2.getString("SANPHAM_FK");
						solo = rs2.getString("SOLO");
						soluong = rs2.getDouble("SOLUONG");
						songayluukho = rs2.getString("SONGAYLUUKHO");
						songayluukhotinh = rs2.getDouble("SONGAYLUUKHOTINH");
						dongia = rs2.getDouble("DONGIA");
						muahangId = rs2.getString("MUAHANG_FK");
						thanhtien = soluong*songayluukhotinh*dongia;
						
						if(thanhtien>0)
						{
							String add = " INSERT ERP_CHIPHILUUKHO_SP_CHITIET (NHANHANG_FK, SANPHAM_FK, SOLO, SOLUONG, DONGIA, NGAYNHANHANG, NGAYNHAPKHO, SONGAYLUUKHO, THANHTIEN, MUAHANG_FK)  "	
										+" values ("+nhId+", "+sanphamId+",'"+solo+"', "+soluong+" , "+dongia+", '"+ngaynhanhang+"', '"+ngaynhapkho+"', '"+songayluukho+"', "+thanhtien+", "+muahangId+")";
							
							System.out.println("" + add);
	
							if (!db.update(add)) {
								msg = "Khong the tao moi Nhan hang: " + add;
								db.getConnection().rollback();
								return msg;
							}
							
						}
					}
					rs2.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();			
				}
				
			}
						
			return "";
				
		} 
		catch (Exception e)
		{ 
			db.shutDown(); 
			return "Khong the insert "; 
		}
		
	}
	/*
	private void taophieuyeucaukiemdinh(int sothung1,dbutils db,String manhanhang,String sanpham_fk,String khuvuc,String solo,String ngayhethan,String userId,String slthung,String ngaysx,String ngaynhandk,String khonhan_fk,String dongiaViet)
	{
		int i;
		String query="";
		for(i=1;i<=sothung1;i++)
		{
			System.out.println("muo luu loo ");
			query="insert into ERP_YeuCauKiemDinh(nhanhang_fk,sanpham_fk,khuvuckho_fk,solo,ngayhethan,trangthai,nguoitao,"
					+ "ngaytao,nguoisua,ngaysua,soluong,ngaysanxuat,"
					+ "ngaynhan,KHONHAN_FK,NGAYHETHONG,"
					+ "DonGiaViet)"
					+ "values('"+manhanhang+"','"+sanpham_fk+"','"+khuvuc+"','"+solo+"/"+i+"','"+ngayhethan+"','"+userId+"','"+s+"',"
					+ "'"+userId+"','"+s+"','"+slthung+"','"+ngaysx+"','"+ngaynhandukien+"','"+khonhan_fk_+"','"+s+"','"+dongiaViet+"',"
					+ ")";

			if(db.updateReturnInt(query)!=1){
				db.getConnection().rollback();
				return "Lỗi dòng lệnh :"+query;
			}	
		}
	}
	*/
}

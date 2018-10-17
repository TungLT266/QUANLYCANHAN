package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.center.beans.dondathang.imp.XLkhuyenmaiTT;
import geso.traphaco.distributor.beans.ctkhuyenmai.*;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETC;
import geso.traphaco.distributor.beans.hopdong.imp.ErpDonhangNppETC;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhuyenmaiNppTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
    public KhuyenmaiNppTTSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
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
		} else {

			XLkhuyenmaiTT xlkm;
			IErpDonhangNppETC dhBean;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			Utility util = new Utility();
			userId = util.antiSQLInspection(request.getParameter("userId"));

			String khachhang = util.antiSQLInspection(request.getParameter("khachhang"));
			String dhId = util.antiSQLInspection(request.getParameter("dhId"));

			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if(nppId == null)
				nppId = "";

			String ngaygiaodich = util.antiSQLInspection(request.getParameter("ngaygiaodich"));
			dhBean = new ErpDonhangNppETC(dhId);
			dhBean.setUserId(userId);
			dhBean.setNppId(nppId);
			dhBean.setKhId(khachhang);

			String loaidonhang = request.getParameter("loaidonhang");
			if(loaidonhang == null)
				loaidonhang = "1";

			String tonggiatridh = util.antiSQLInspection(request.getParameter("tonggiatri"));
			String[] schemeList = request.getParameterValues("schemeList");
			String[] schemeDiengiai = request.getParameterValues("schemeDiengiai");

			String[] masp = request.getParameterValues("spMa");
			String[] soluong = request.getParameterValues("spSoluong");
			String[] dongia = request.getParameterValues("spDongia");
			String[] quycach = request.getParameterValues("spQuycach");

			String[] schemeSTT = request.getParameterValues("schemeSTT");  //Những scheme có check chọn
			String[] schemeList_DUNGCHUNG = request.getParameterValues("schemeList_DUNGCHUNG");

			String showAll = util.antiSQLInspection(request.getParameter("ShowAll"));

			String action = request.getParameter("action");
			if (action == null){
				action = "";
			}

			String error = "";
			String schemeCHON = "";
			if( schemeSTT != null )
			{
				for( int i = 0; i < schemeList.length; i++ )
				{
					for( int j = 0; j < schemeSTT.length; j++ )
					{
						if( schemeList[i].trim().equals( schemeSTT[j].trim() ) ) //SCHEME THỨ I được chọn
						{
							if( schemeList_DUNGCHUNG[i].trim().length() > 0 )
							{
								for( int k = 0; k < schemeSTT.length; k++ )
								{
									System.out.println(" --- SCHEME STT: " + i + " : " + schemeList_DUNGCHUNG[i] );
									if( schemeList_DUNGCHUNG[i].trim().contains( schemeSTT[k] ) && ( schemeList[i] != schemeSTT[k] ) )
									{
										error += "Scheme " + schemeDiengiai[i] + " đang dùng chung sản phẩm với scheme khác. Vui lòng kiểm tra lại.";
										break;
									}
								}
							}
						}
					}
				}

				//Chỉ lưu lại những scheme được chọn thôi
				for( int j = 0; j < schemeSTT.length; j++ )
				{
					schemeCHON += schemeSTT[j] + ",";
				}
			}
			else if( showAll != null ) //Hiển tất cả
			{
				if( showAll.equals("1") && action.equals("save") )
					error = "Vui lòng chọn SCHEME bạn muốn lấy";
			}
			else if( showAll == null && action.equals("save") ) //Không bị đụng mà không có scheme nào được chọn. thì mặc định lấy hết
			{
				for( int j = 0; j < schemeList.length; j++ )
				{
					schemeCHON += schemeList[j] + ",";
				}
			}
			
			System.out.println("::: SCHEME CHON: " + schemeCHON);

			//CHECK XEM CÓ CHỌN 2 SCHEME NÀO MÀ CÓ DÙNG CHUNG VỚI NHAU KHÔNG
			if(action.equals("save") && error.trim().length() <= 0 )
			{
				error = this.LuuKhuyenMai( request, dhBean, util, schemeCHON );
				if( error.trim().length() <= 0 )  //Lưu thành công
				{
					//AP LAI KM CHIET KHAU + CSBH
					dhBean.setLoaidonhang("1");
					dhBean.setKhId(khachhang);
					dhBean.setCtyId(session.getAttribute("congtyId").toString());

					dhBean.ApChietKhau(dhId, new geso.traphaco.distributor.db.sql.dbutils(), "1", "1" );
					dhBean.init( session.getAttribute("tdv_dangnhap_id").toString() );

					if( dhBean.getMsg().trim().length() <= 0 )
						dhBean.setMsg("Số đơn hàng bạn vừa lưu: " + dhId);

					//dhBean.setAplaikhuyenmai(false);
					session.setAttribute("lsxBean", dhBean);

					String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
					response.sendRedirect(nextJSP);	
					
					return;
				}
			}
			
			//SUBMIT HOẶC LƯU BỊ LỖI
			xlkm = new XLkhuyenmaiTT(userId, ngaygiaodich, nppId, khachhang, dhId);

			Hashtable<String,Float> sp_sl = new Hashtable<String,Float>();
			Hashtable<String,Float> sp_dg = new Hashtable<String,Float>();
			Hashtable<String,Float> sp_qc = new Hashtable<String,Float>();

			int m = 0; 
			while(m < masp.length)
			{
				sp_sl.put(masp[m], Float.parseFloat(soluong[m].replaceAll(",", "")));
				sp_dg.put(masp[m], Float.parseFloat(dongia[m].replaceAll(",", "")));
				sp_qc.put(masp[m], Float.parseFloat(quycach[m].replaceAll(",", "")));

				m++;
			}

			//Bên này dùng 2 cách: nếu có check thì lấy check, không thì kéo lên kéo xuống để chọn
			String[] scheme = request.getParameterValues("Scheme");
			if(scheme != null)
			{
				if( schemeSTT != null )
				{
					List<ICtkhuyenmai> listCTKM_HIENHUU = new ArrayList<ICtkhuyenmai>();

					//REMOVE NHUNG SCHEME KHONG DUOC CHON
					for(int i = 0; i < xlkm.getCtkmList().size(); i++ )
					{
						//boolean cotrongSELECTED = false;
						for(int j = 0; j < schemeSTT.length; j++ )
						{
							if( xlkm.getCtkmList().get(i).getId().equals( schemeSTT[j] ) )
							{
								//cotrongSELECTED = true;
								listCTKM_HIENHUU.add( xlkm.getCtkmList().get(i) );
								break;
							}
						}
					}
					xlkm.setCtkmList(listCTKM_HIENHUU); //Sort lai thu tu
				}
				else
				{
					List<ICtkhuyenmai> listCTKM = xlkm.SortList(xlkm.getCtkmList(), scheme);
					xlkm.setCtkmList(listCTKM); //Sort lai thu tu
				}
			}

			if(showAll == null)
				xlkm.setDieuchinh(true);
			else
				xlkm.setDieuchinh(false);

			xlkm.setMasp(masp);
			xlkm.setSoluong(soluong);
			xlkm.setDongia(dongia);
			xlkm.setQuycach(quycach);
			xlkm.setIdDonhang(dhId);
			xlkm.setTonggiatriDh(Float.parseFloat(tonggiatridh));
			xlkm.setNgaygiaodich(ngaygiaodich);

			xlkm.setHashA(sp_sl);
			xlkm.setHashB(sp_dg);
			xlkm.setHash_QuyCach(sp_qc);

			xlkm.ApKhuyenMai();
			xlkm.checkConfirm();
			xlkm.setMsg(error);

			session.setAttribute("xlkm", xlkm);

			String nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMaiNppTT.jsp";
			response.sendRedirect(nextJSP);
			
		} 
	}
	
	private String LuuKhuyenMai(HttpServletRequest request, IErpDonhangNppETC dhBean, Utility util, String schemeCHON) 
	{
		//PARAMETER
		String dhId = util.antiSQLInspection(request.getParameter("dhId"));
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if(nppId == null)
			nppId = "";

		String loaidonhang = request.getParameter("loaidonhang");
		if(loaidonhang == null)
			loaidonhang = "1";
		
		String[] schemeList = request.getParameterValues("schemeList");
		String[] schemeOR = request.getParameterValues("schemeOR");
		String[] soxuatkm = request.getParameterValues("soxuatKM");
		String[] schemeDiengiai = request.getParameterValues("schemeDiengiai");

		dbutils db = new dbutils();
		String query = "";
		try 
		{
			db.getConnection().setAutoCommit(false);

			int i = 0;
			if(schemeList.length > 0)
			{
				while(i < schemeList.length)
				{
					System.out.println("::: SCHEME CHON: " + schemeCHON + " -- SHCME I: " + schemeList[i] + " -- OR: " + schemeOR[i]  );
					if( schemeCHON.contains( schemeList[i]) )
					{
						String[] trakm = request.getParameterValues(schemeList[i] + ".trakmId");
						String[] trakmType = request.getParameterValues(schemeList[i] + ".trakmType");
						String[] trakmHinhThuc = request.getParameterValues(schemeList[i] + ".trakmHinhThuc");
						String[] trakmSOXUAT = request.getParameterValues(schemeList[i] + ".soxuat");
						
						System.out.println("Scheme OR cua CTKM: " + schemeList[i] + ", la: " + schemeOR[i] + " -- SO XUAT TRAKM: " + trakmSOXUAT.length );
						
						//TỚI ĐÂY PHÂN RA 2 TRƯỜNG HỢP, 1 là cho gõ số xuất, 2 là chạy như mặc định
						if( Boolean.parseBoolean( schemeOR[i] ) == true )
						{
							double totalXUAT = 0;
							for( int j = 0; j < trakmSOXUAT.length; j++ )
							{
								if( trakmSOXUAT[j].trim().length() > 0 && !trakmSOXUAT[j].equals("0") )
								{
									totalXUAT += Double.parseDouble( trakmSOXUAT[j] );
									
									//String ttTrakm = request.getParameter(schemeList[i] + "." + trakm[j] + ".tonggiatriTKM");
									if( !trakmType[j].trim().equals("3") )  // TRẢ TIỀN HOẶC CHIẾT KHẤU
									{
										//tonggiatriTKM_CK
										String tonggiatriTKM_CK = request.getParameter(schemeList[i] + "." + trakm[j] + ".tonggiatriTKM_CK");
										String msg = CheckNghanSach(schemeList[i], nppId, tonggiatriTKM_CK, "0", db);
										if(msg.trim().length() > 0)
										{
											db.getConnection().rollback();
											return msg;
										}

										query = " Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) " + 
												" values('" + dhId + "','" + schemeList[i] + "','" + trakm[j] + "','" + soxuatkm[i].replaceAll(",", "") + "','" + tonggiatriTKM_CK.replaceAll(",", "") + "')";
										System.out.println("1. OR Chen khuyen mai tien: " + query);
										if(!db.update(query))
										{
											db.getConnection().rollback();
											return query;
										}
									}
									else  // HIỆN TẠI CHỈ XỬ LÝ TRƯỜNG HỢP LOẠI TRẢ CỐ ĐỊNH
									{
										String[] masanphamFIX;
										String[] slsanphamFIX;
										if( trakmHinhThuc[j].equals("2") )  //BẤT KỲ TRONG
										{
											masanphamFIX = request.getParameterValues( schemeList[i] + "." + trakm[j] + ".maspTraKm" );
											slsanphamFIX = request.getParameterValues( schemeList[i] + "." + trakm[j] + ".soluong" );
										}
										else  //CỐ ĐỊNH
										{
											masanphamFIX = request.getParameterValues( schemeList[i] + "." + trakm[j] + ".masanphamFIX" );
											slsanphamFIX = request.getParameterValues( schemeList[i] + "." + trakm[j] + ".slsanphamFIX" );
										}
										
										if( masanphamFIX != null )
										{
											String sql = "";
											for( int k = 0; k < masanphamFIX.length; k++ )
											{
												System.out.println("::: J LA: " + j );
												int soluongTRA = 0;
												if( trakmHinhThuc[j].equals("2") )
													soluongTRA = Integer.parseInt( slsanphamFIX[k] );
												else
													soluongTRA = ( Integer.parseInt(trakmSOXUAT[j]) * Integer.parseInt( slsanphamFIX[k] ) );
												
												sql += "select '" + dhId + "' as DONDATHANGID, '" + schemeList[i] + "' as ctkmId, '" + trakm[j] + "' as trakmId, '" + soxuatkm[i].replaceAll(",", "") + "' as soxuat, '" + masanphamFIX[k] + "' as masp, " + soluongTRA + " as soluong, '" + trakmSOXUAT[j] + "' as soxuatTRA ";
												sql += " union ";
											}
											
											if( sql.trim().length() > 0 )
											{
												sql = sql.substring(0, sql.length() - 7);
												sql =  " Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong, soxuatTRA) "+
														 " select DT.DONDATHANGID, DT.ctkmId, DT.trakmId, DT.soxuat, DT.soluong * isnull( dbo.LayDonGia(dh.loaidonhang, dh.pk_seq, dh.hopdong_fk, sp.pk_seq), 0 ) as tonggiatri, DT.masp, DT.soluong, DT.soxuatTRA"+
														 " from "+
														 " ("+
														 	sql +
														 " )"+
														 " DT inner join SANPHAM sp on DT.masp = sp.MA"+
														 "    inner join ERP_DONDATHANGNPP dh on DT.DONDATHANGID = dh.PK_SEQ";
												System.out.println("2. OR Chen khuyen mai hang: " + sql);
												if(!db.update(sql))
												{
													db.getConnection().rollback();
													return query;
												}
											}
										}
									}
								}
							}
							
							//CHECK TONG XUAT TRA KHONG DUOC VUOT QUA XUAT DUOC HUONG
							if( totalXUAT > Double.parseDouble(soxuatkm[i].replaceAll(",", "")) )
							{
								db.getConnection().rollback();
								return "Tổng xuất trả ( " + totalXUAT + " ) của SCHEME ( " + schemeDiengiai[i] + " ) không được vượt quá số xuất KM ( " + soxuatkm[i] + " ) ";
							}
						}
						else // ĐỔI LẠI CÁCH MỚI
						{
							for(int j = 0; j < trakm.length; j++)
							{
								//String ttTrakm = request.getParameter(schemeList[i] + "." + trakm[j] + ".tonggiatriTKM");
								if( !trakmType[j].trim().equals("3") )  // TRẢ TIỀN HOẶC CHIẾT KHẤU
								{
									//tonggiatriTKM_CK
									String tonggiatriTKM_CK = request.getParameter(schemeList[i] + "." + trakm[j] + ".tonggiatriTKM_CK");
									
									String msg = CheckNghanSach(schemeList[i], nppId, tonggiatriTKM_CK, "0", db);
									if(msg.trim().length() > 0)
									{
										db.getConnection().rollback();
										return msg;
									}

									query = " Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) " + 
											" values('" + dhId + "','" + schemeList[i] + "','" + trakm[j] + "','" + soxuatkm[i].replaceAll(",", "") + "','" + tonggiatriTKM_CK.replaceAll(",", "") + "')";
									System.out.println("3. NOT OR Chen khuyen mai tien: " + query);
									if(!db.update(query))
									{
										db.getConnection().rollback();
										return query;
									}
								}
								else  // TRƯỜNG HỢP SẢN PHẨM BẤT KỲ TRONG, XỬ LÝ TƯƠNG TỰ CỐ ĐỊNH
								{
									String[] masanphamFIX;
									String[] slsanphamFIX;
									if( trakmHinhThuc[j].equals("2") )  //BẤT KỲ TRONG
									{
										masanphamFIX = request.getParameterValues( schemeList[i] + "." + trakm[j] + ".maspTraKm" );
										slsanphamFIX = request.getParameterValues( schemeList[i] + "." + trakm[j] + ".soluong" );
									}
									else  //CỐ ĐỊNH
									{
										masanphamFIX = request.getParameterValues( schemeList[i] + "." + trakm[j] + ".masanphamFIX" );
										slsanphamFIX = request.getParameterValues( schemeList[i] + "." + trakm[j] + ".slsanphamFIX" );
									}
									
									if( masanphamFIX != null )
									{
										String sql = "";
										for( int k = 0; k < masanphamFIX.length; k++ )
										{
											if( slsanphamFIX[k].trim().length() > 0 )
											{
												System.out.println(":::2. J LA: " + j + " -- SO XUAT: " + trakmSOXUAT[j] );
												int soluongTRA = 0;
												if( trakmHinhThuc[j].equals("2") )
													soluongTRA = Integer.parseInt( slsanphamFIX[k] );
												else
													soluongTRA = ( Integer.parseInt(trakmSOXUAT[j]) * Integer.parseInt( slsanphamFIX[k] ) );
												
												sql += "select '" + dhId + "' as DONDATHANGID, '" + schemeList[i] + "' as ctkmId, '" + trakm[j] + "' as trakmId, '" + trakmSOXUAT[j] + "' as soxuat, '" + masanphamFIX[k] + "' as masp, " + soluongTRA + " as soluong, '" + trakmSOXUAT[j] + "' as soxuatTRA ";
												sql += " union ";
											}
										}
										
										if( sql.trim().length() > 0 )
										{
											sql = sql.substring(0, sql.length() - 7);
											sql =  " Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong, soxuatTRA) "+
													 " select DT.DONDATHANGID, DT.ctkmId, DT.trakmId, DT.soxuat, DT.soluong * isnull( dbo.LayDonGia(dh.loaidonhang, dh.pk_seq, dh.hopdong_fk, sp.pk_seq), 0 ) as tonggiatri, DT.masp, DT.soluong, DT.soxuatTRA"+
													 " from "+
													 " ("+
													 	sql +
													 " )"+
													 " DT inner join SANPHAM sp on DT.masp = sp.MA"+
													 "    inner join ERP_DONDATHANGNPP dh on DT.DONDATHANGID = dh.PK_SEQ";
											System.out.println("4. NOT OR Chen khuyen mai hang: " + sql);
											if(!db.update(sql))
											{
												db.getConnection().rollback();
												return query;
											}
										}
									}
								}
							}
						}
					}

					//LƯU LẠI LƯỢNG HÀNG TRONG ĐIỀU KIỆN KHUYẾN MẠI SỬ DỤNG BAO NHIÊU
					String[] schemeDKKM = request.getParameterValues("schemeDKKM");
					String sqlCHON = "";
					if( schemeDKKM != null )
					{
						for( int j = 0; j < schemeDKKM.length; j++ )
						{
							String[] masanphamCHON = request.getParameterValues( schemeList[i] + "." + schemeDKKM[j] + ".masanpham" );
							if( masanphamCHON != null )
							{
								for( int k = 0; k < masanphamCHON.length; k++ )
								{
									String key = schemeList[i] + "." + schemeDKKM[j] + "." + masanphamCHON[k] + ".sudung";
									String sudung = request.getParameter( key );

									if( sudung != null )
									{
										sqlCHON += "select '" + schemeList[i] + "' as ctkmId, '" + schemeDKKM[j] + "' as dkkmId, N'" + masanphamCHON[k] + "' as masanpham, '" + sudung + "' as sudung, ( select pk_seq from SANPHAM where ma = N'" + masanphamCHON[k] + "' ) as spId ";
										sqlCHON += " union ";
									}
								}
							}
						}
					}

					System.out.println(":::: SP CHON SAN: " + sqlCHON);
					if( sqlCHON.trim().length() > 0 )
					{
						sqlCHON = sqlCHON.substring(0, sqlCHON.length() - 7);
						query = "insert ERP_DONDATHANGNPP_CTKM_SUDUNG(dondathangId, ctkmId, dkkmId, spMa, sudung, sanpham_fk  ) " + 
								" select '" + dhId + "', * from ( " + sqlCHON + " ) DATA ";

						if(!db.update(query))
						{
							db.getConnection().rollback();
							return query;
						}
					}

					i++;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
			try
			{
				System.out.println("8.Exception tai day: " + e1.getMessage());
				db.getConnection().rollback();
			}
			catch(Exception err){}

			return "Loi khi tao moi chuong trinh khuyen mai: " + e1.getMessage();
		}
		db.shutDown();

		return "";
	}
	

	private String checkTonKhoKhuyenMai(String kho_fk, String nppId, String[] spIdss, String khachhang, String nhomkenh_fk, dbutils db)
	{
		//kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi thoat
		/*String query = " select available from nhapp_kho " + 
					   " where kho_fk = '" + kho_fk + "' and npp_fk = '" + nppId + "' and sanpham_fk = ( select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') and nhomkenh_fk = '" + nhomkenh_fk + "' ";
		System.out.println("2.Query check ton kho khuyen mai: " + query);
		ResultSet rsCheck = db.get(query);
		
		int avai = 0;
		if(rsCheck != null)
		{
			try 
			{
				boolean flag = false;
				
				while(rsCheck.next())
				{
					if(rsCheck.getString("available") != null)
					{
						flag = true;
						avai = rsCheck.getInt("available");
						if(avai < Integer.parseInt(spIdss[1]))
						{
							return "Sản phẩm khuyến mại  " + spIdss[0] + " - Còn tối đa " + avai;	
						}
					}
				}
				
				rsCheck.close();
				if(flag == false) //khong co dong du lieu nao, Resualset van khac null
				{
					return "San pham khuyen mai " + spIdss[0] + " - Con toi da 0";
				}
			}
			catch (SQLException e) 
			{ 
				return e.getMessage();
			}
		}
		else{
			return "error";
		}
		
		if(avai < Integer.parseInt(spIdss[1]))
		{
			return "Sản phẩm khuyến mại  " + spIdss[0] + " - Còn tối đa " + avai;	
		}*/
		
		return "";
	}
	
	private String checkTonkho(String khoId, String nppId, String ctkmId, String khId, String nhomkenh_fk, String spId, String spMa, int slg, dbutils db) 
	{
		//kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi thoat
		/*String query = " select available from nhapp_kho " + 
					   " where kho_fk = '" + khoId + "' and npp_fk = '" + nppId + "' and sanpham_fk = '" + spId + "' and nhomkenh_fk = '" + nhomkenh_fk + "' ";
		System.out.println("2.Query check ton kho khuyen mai: " + query);
		ResultSet rsCheck = db.get(query);
		
		int avai = 0;
		if(rsCheck != null)
		{
			try 
			{
				boolean flag = false;
				
				while(rsCheck.next())
				{
					if(rsCheck.getString("available") != null)
					{
						flag = true;
						avai = rsCheck.getInt("available");
						if(avai < slg)
						{
							return "Sản phẩm khuyến mại  " + spMa + " - Còn tối đa " + avai;	
						}
					}
				}
				
				rsCheck.close();
				if(flag == false) //khong co dong du lieu nao, Resualset van khac null
				{
					return "San pham khuyen mai " + spMa + " - Con toi da 0";
				}
			}
			catch (SQLException e) 
			{ 
				return e.getMessage();
			}
		}
		else{
			return "error";
		}
		
		if(avai < slg)
		{
			return "Sản phẩm khuyến mại  " + spMa + " - Còn tối đa " + avai;	
		}*/
		
		return "";
	}
	
	private String CreateKhuyenmai(String ctkmId, String phanbotheoDH, String trakmId, String id, String nppId, long soxuat, String khId, String nhomkenh_fk, String khoId, dbutils db)
	{
		String str = "";

		String spKhuyenmaiDacbiet = "";
		if( ctkmId.contains("_") )
		{
			String[] arr = ctkmId.split("_");
			ctkmId = arr[0];
			spKhuyenmaiDacbiet = arr[1];
		}
		
		try 
		{ 	
			String sql = "";
			
			if(spKhuyenmaiDacbiet.trim().length() <= 0)
			{
				sql = "select a.sanpham_fk as spId, a.soluong, b.ma as spMa from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq " + 
					  " where a.trakhuyenmai_fk = '" + trakmId + "'";
			}
			else
			{
				sql = "select b.PK_SEQ as spId, b.ma as spMa, "+
					  "	( select tra.TONGLUONG from  CTKM_TRAKM ct inner join TRAKHUYENMAI tra on ct.TRAKHUYENMAI_FK = tra.PK_SEQ and ct.CTKHUYENMAI_FK = '" + ctkmId + "' ) as soluong "+
					  "from SANPHAM b  " +
					  "where PK_SEQ = '" + spKhuyenmaiDacbiet + "'  ";
			}
			
			//String sql = "select a.sanpham_fk as spId, a.soluong, b.ma as spMa, a.dongia from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = (select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK = '" + ctkmId + "')";
			System.out.println("10.Query truy van san pham khuyen mai: " + sql + "\n");
			ResultSet rsSQl = db.get(sql);
			long tongtien = 0;
			if(rsSQl != null)
			{
				while(rsSQl.next())
				{
					int slg = Integer.parseInt(rsSQl.getString("soluong")) * (int)soxuat;
					
					//lay don gia
					/*String query =  "select GIAMUANPP  as dongia  " +
									"from BGMUANPP_SANPHAM  " +
									"where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
															"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
															"where a.TUNGAY <= ( select ngaydonhang from ERP_DONDATHANG where pk_seq='" + id + "' ) and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select kbh_fk from ERP_DONDATHANG where pk_seq='" + id + "' ) and a.DVKD_FK = ( select dvkd_fk from ERP_DONDATHANG where pk_seq='" + id + "' )  " +
															"order by a.TUNGAY desc  ) and SANPHAM_FK = '" + rsSQl.getString("spId") + "' ";*/
					
					String query =  "select dongia  " + 
									 "from BANGGIABANLENPP_SANPHAM  " + 
									 "where sanpham_fk = '" + rsSQl.getString("spId") + "' " + 
									 " 					and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP  " + 
									 "											where DVKD_FK = ( select DVKD_FK from SANPHAM where pk_seq = '" + rsSQl.getString("spId") + "' )  " + 
									 "													and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId + "' ) ) ";
					
					System.out.println("11.Lay don gia: " + query);
					
					ResultSet rsDg = db.get(query);
					if(rsDg != null)
					{
						if(rsDg.next())  
						{
							if(rsDg.getString("dongia") != null)
								tongtien = Math.round(slg * rsDg.getFloat("dongia"));
							rsDg.close();
						}
					}
					
					
					/*********************************************************************************/
					String msg = CheckNghanSach(ctkmId, nppId, Integer.toString(slg), "1", db);
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
					/*********************************************************************************/
					
					String error = checkTonkho(khoId, nppId, ctkmId, khId, nhomkenh_fk, rsSQl.getString("spId"), rsSQl.getString("spMa"), slg, db);
					if(error.length() > 0)
					{
						db.getConnection().rollback();
						return error;
					}
					
					//luu tong gia tri o moi dong sanpham
					query = "Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkmId + "','" + trakmId + "','" + soxuat + "', '" + Long.toString(tongtien) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
					System.out.println("12.Chen khuyen mai: " + query);
					if(!db.update(query))
					{
						db.getConnection().rollback();
						str = query;
						return str;
					}
					
					//cap nhat kho
					/*query = "Update nhapp_kho set available = available - '" + Integer.toString(slg) + "', booked = booked + '" + Integer.toString(slg) + "' where kho_fk = '" + khoId + "' and npp_fk = '" + nppId + "' and sanpham_fk = '" + rsSQl.getString("spId") + "' and nhomkenh_fk = '" + nhomkenh_fk + "' ";   							
					if(!db.update(query))
					{
						db.getConnection().rollback(); 
						str = query;
						return str;
					}*/
				}
				
				/*String query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + Long.toString(tongtien) + "' where ctkm_fk = '" + ctkmId + "' and npp_fk = '" + nppId + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback(); 
					str = query;
					return str;
				}*/
			}
			rsSQl.close();
			
			
			//LUU LAI LUONG DA DUNG
			if( spKhuyenmaiDacbiet.trim().length() <= 0 )
			{
				String query =  " insert ERP_DONDATHANGNPP_CTKM_SUDUNG(dondathangId, ctkmId, dkkmId, spMa, sudung, sanpham_fk  ) "+
						 " select b.dondathang_fk, '" + ctkmId + "' as ctkmId, a.DIEUKIENKHUYENMAI_FK, c.MA, b.soluong, b.sanpham_fk "+
						 " from DIEUKIENKM_SANPHAM a inner join ERP_DONDATHANGNPP_SANPHAM b on a.SANPHAM_FK = b.sanpham_fk"+
						 " 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ"+
						 " where b.dondathang_fk = '" + id + "' and a.DIEUKIENKHUYENMAI_FK in ( select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = '" + ctkmId + "' )";
				
				System.out.println(":::: Chen so su dung KMBT: " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					str = query;
					return str;
				}
			}
			else
			{
				String query =  " insert ERP_DONDATHANGNPP_CTKM_SUDUNG(dondathangId, ctkmId, dkkmId, spMa, sudung, sanpham_fk  ) "+
						 " select b.dondathang_fk, '" + ctkmId + "' as ctkmId, a.DIEUKIENKHUYENMAI_FK, c.MA, b.soluong, b.sanpham_fk "+
						 " from DIEUKIENKM_SANPHAM a inner join ERP_DONDATHANGNPP_SANPHAM b on a.SANPHAM_FK = b.sanpham_fk"+
						 " 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ"+
						 " where b.dondathang_fk = '" + id + "' and c.pk_seq = '" + spKhuyenmaiDacbiet + "' and a.DIEUKIENKHUYENMAI_FK in ( select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = '" + ctkmId + "' )";
				
				System.out.println(":::: Chen so su dung KM DAC BIET: " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					str = query;
					return str;
				}
			}
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
			System.out.println("3.Loi KM: " + e1.toString());
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e) {}
			
			return "Loi khi tao ctkm: " + ctkmId + ", " + e1.toString();
		}
		return str;
	}
	
	private String CheckNghanSach(String ctkmId, String nppId, String ngansach, String loai, dbutils db)
	{
		/*String sql = "";
		
		if(loai.equals("0"))  //PHAN BO KHUYEN MAI THEO SO TIEN
		{
			sql = "select a.CTKM_FK, b.scheme, b.PHANBOTHEODONHANG, a.NGANSACH,  " + 
					"	  ISNULL( 	( select SUM(tonggiatri)  " + 
					"	  from DONHANG_CTKM_TRAKM  " + 
					"	  where CTKMID = a.CTKM_FK and DONHANGID in ( select PK_SEQ from DONHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 2 ) and DONHANGID not in ( select DONHANG_FK from DONHANGTRAVE where DONHANG_FK is not null and NPP_FK = '" + nppId + "' and TRANGTHAI in  ( 1, 3 ) )  ), 0 ) as DASUDUNG  " + 
					"from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ  " + 
					"where npp_fk = '" + nppId + "' and ctkm_fk = '" + ctkmId + "'  ";
		}
		else  //PHAN BO KHUYEN MAI THEO SO LUONG
		{
			sql = "select a.CTKM_FK, b.scheme, b.PHANBOTHEODONHANG, a.NGANSACH,  " + 
					"	  ISNULL( 	( select SUM(SOLUONG)  " + 
					"	  from DONHANG_CTKM_TRAKM  " + 
					"	  where CTKMID = a.CTKM_FK AND SPMA IS NOT NULL and DONHANGID in ( select PK_SEQ from DONHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 2 ) and DONHANGID not in ( select DONHANG_FK from DONHANGTRAVE where DONHANG_FK is not null and NPP_FK = '" + nppId + "' and TRANGTHAI in  ( 1, 3 ) )  ), 0 ) as DASUDUNG  " + 
					"from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ  " + 
					"where npp_fk = '" + nppId + "' and ctkm_fk = '" + ctkmId + "'  ";
		}
				
		
		System.out.println("1.Cau lenh check ngan sach: " + sql + " --- Ngan sach de check: " + ngansach);
		ResultSet rs = db.get(sql);
		String scheme = "";
		
		try 
		{
			float conlai = 0.0f;
			if(rs.next())
			{
				scheme = rs.getString("scheme");
				conlai = Float.parseFloat(rs.getString("ngansach")) - Float.parseFloat(rs.getString("DASUDUNG"));
				rs.close();	
			}
			
			//System.out.println("---NGAN SACH ( SOXUAT / TONG GIA TRI ): " + soXUAT + " / " + ngansach + " -- CON LAI: " + conlai);
			
			if(Float.parseFloat(ngansach) > conlai)
			{
				return "1.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ";
			}
			
		} 
		catch (Exception e) 
		{ 
			e.printStackTrace();
			System.out.println("__EXCEPTION CHECK NGAN SACH: " + e.getMessage());
			return "2.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ";
		}*/
		
		return "";
	}
	

}

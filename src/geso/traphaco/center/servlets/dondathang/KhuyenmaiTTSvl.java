package geso.traphaco.center.servlets.dondathang;

import geso.traphaco.center.beans.dondathang.IErpDondathang;
import geso.traphaco.center.beans.dondathang.imp.ErpDondathang;
import geso.traphaco.center.beans.dondathang.imp.ErpThongtinkho;
import geso.traphaco.center.beans.dondathang.imp.XLkhuyenmaiTT;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.distributor.beans.ctkhuyenmai.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
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

public class KhuyenmaiTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
    public KhuyenmaiTTSvl() 
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
			response.sendRedirect("/Traphaco/index.jsp");
		}else{

			XLkhuyenmaiTT xlkm;
			IErpDondathang dhBean;
			dbutils db;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();

			Utility util = new Utility();
			userId = util.antiSQLInspection(request.getParameter("userId"));

			String khachhang = util.antiSQLInspection(request.getParameter("khachhang"));
			String ETC = util.antiSQLInspection(request.getParameter("ETC"));

			String dhId = util.antiSQLInspection(request.getParameter("dhId"));

			/*String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if(nppId == null)
				nppId = "";*/
			String nppId = (String) session.getAttribute("nppId");

			String ngaygiaodich = util.antiSQLInspection(request.getParameter("ngaygiaodich"));
			dhBean = new ErpDondathang(dhId);
			dhBean.setUserId(userId);
			
			/*String hinhthuc = request.getParameter("hinhthuc");
			if(hinhthuc == null)
				hinhthuc = "0";
			System.out.println("4000..Hinh thuc khuyen mai: " + hinhthuc);*/

			String loaidonhang = request.getParameter("loaidonhang");
			if(loaidonhang == null)
				loaidonhang = "0";

			String tonggiatridh = util.antiSQLInspection(request.getParameter("tonggiatri"));
			String[] schemeList = request.getParameterValues("schemeList");
			String[] schemeOR = request.getParameterValues("schemeOR");
			String[] soxuatkm = request.getParameterValues("soxuatKM");
			String[] schemePhanBo = request.getParameterValues("schemePhanBo");

			String[] schemeDiengiai = request.getParameterValues("schemeDiengiai");

			String[] masp = request.getParameterValues("spMa");
			String[] soluong = request.getParameterValues("spSoluong");
			String[] dongia = request.getParameterValues("spDongia");
			String[] quycach = request.getParameterValues("spQuycach");

			String action = request.getParameter("action");
			if (action == null){
				action = "";
			}

			System.out.println("11.So CTKM: " + schemeList.length);

			String error = "";
			if(action.equals("save"))
			{
				db = new dbutils();
				String kho_fk = "";
				String query = "";
				try 
				{
					db.getConnection().setAutoCommit(false);

					int i = 0;

					if(schemeList.length > 0)
					{
						while(i < schemeList.length)
						{
							String[] trakm = request.getParameterValues(schemeList[i] + ".trakmId");
							String[] trakmType = request.getParameterValues(schemeList[i] + ".trakmType");
							String[] trakmHinhThuc = request.getParameterValues(schemeList[i] + ".trakmHinhThuc");
							String[] trakmPrimary = request.getParameterValues(schemeList[i] + ".trakmPrimary");

							System.out.println("Scheme OR cua CTKM: " + schemeList[i] + ", la: " + schemeOR[i]);
							if(Boolean.parseBoolean(schemeOR[i]))
							{
								String trakmSelected = request.getParameter(schemeList[i] + ".chon");
								System.out.println("1145. Ban chon TKM: " + trakmSelected);

								if(trakmSelected == null)
									trakmSelected = trakm[0];

								int index_OR = -1;
								for(int kk = 0; kk < trakm.length; kk++)
								{
									if(trakm[kk].trim().equals(trakmSelected))
									{
										index_OR = kk;
										break;
									}
								}

								String trakmTypeSelected = trakmType[index_OR];
								String trakmHinhthucSelected = trakmHinhThuc[index_OR];
								String trakmPrimarySelected = trakmPrimary[index_OR];

								//thay tra khuyen mai OR duoc chon trong List
								trakm = new String[]{trakmSelected};
								trakmType = new String[]{trakmTypeSelected};
								trakmHinhThuc = new String[]{trakmHinhthucSelected};
								trakmPrimary = new String[]{trakmPrimarySelected};

							}

							//query = "select kho_fk from ctkhuyenmai where pk_seq = '" + schemeList[i] + "'";
							query = "select kho_fk from ERP_DONDATHANG where pk_seq = '" + dhId + "'";
							ResultSet KhoRS = db.get(query);
							if(KhoRS != null)
							{
								KhoRS.next();
								kho_fk = KhoRS.getString("kho_fk");
								KhoRS.close();
							}

							for(int count = 0; count < trakm.length; count++)
							{
								//System.out.println("i la: " + i + " --Tra Km size:  " + trakm.length + " -- Count la: " + count);

								String ttTrakm = request.getParameter(schemeList[i] + "." + trakm[count] + ".tonggiatriTKM");
								String spId = request.getParameter(schemeList[i] + "." + trakm[count] + ".spSelected");
								if(spId == null)
									spId = "";

								//System.out.println("San pham Id la: " + schemeList[i] + "." + trakm[count] + ".spSelected" + "  --- " + spId);
								//System.out.println("Tra khuyen mai Type: " + trakmType[count] + ".Hinh thuc" + "  --- " + trakmHinhThuc[count]);

								if( !(trakmType[count].trim().equals("3") && trakmHinhThuc[count].trim().equals("1") ) ) //tra khuyen mai voi san pham tuy chon
								{
									String flag = "";

									System.out.println("[Theo Tien]");

									if(schemePhanBo[i].equals("0"))
									{
										flag = CheckNghanSach(schemeList[i], nppId, khachhang, ttTrakm, "0", db);
									}

									if(flag.length() > 0) //ngan sach khong du
									{
										db.getConnection().rollback();

										xlkm = new XLkhuyenmaiTT(userId, ngaygiaodich, khachhang, loaidonhang);

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

										String[] scheme = request.getParameterValues("Scheme");
										if(scheme != null)
										{
											List<ICtkhuyenmai> listCTKM = xlkm.SortList(xlkm.getCtkmList(), scheme);
											xlkm.setCtkmList(listCTKM); //Sort lai thu tu
										}

										String showAll = util.antiSQLInspection(request.getParameter("ShowAll"));
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
										xlkm.setMsg("Ngan sach khong du, vui long dieu chinh ngan sach chuong trinh: " + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + " - Con toi da " + flag);
										xlkm.setETC(ETC);
										
										xlkm.setHashA(sp_sl);
										xlkm.setHashB(sp_dg);
										xlkm.setHash_QuyCach(sp_qc);

										xlkm.ApKhuyenMai();
										session.setAttribute("xlkm", xlkm);

										String nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMai.jsp";
										response.sendRedirect(nextJSP);

										return;
									}
									System.out.println("aa - " + trakmPrimary[count] + "," + spId);
									//Thoa ngan sach
									if(trakmPrimary[count].equals("0"))
									{
										if(spId.length() > 0 ) //masp1-soluong1;masp2-soluong2...
										{
											if(spId.indexOf(";") > 0) //nhieu san pham
											{
												String[] spIds = spId.split(";");

												int toalTRA = 0;
												for(int j = 0; j < spIds.length; j++)
												{
													String[] spIdss = spIds[j].split("-");

													String msg = checkTonKhoKhuyenMai(kho_fk, nppId, spIdss, khachhang, db);

													if(msg.equals(""))
													{
														long tongtri = 0;
															//query = "select dongia from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = '" + trakm[i].trim() + "' and b.ma = '" + spIdss[0].trim() + "'";
															/*query = "select c.GIAMUANPP as dongia from NHAPHANPHOI a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.NPP_FK inner join BGMUANPP_SANPHAM c on b.BANGGIAMUANPP_FK = c.BGMUANPP_FK  " +
					    										" inner join  BANGGIAMUANPP bgmnpp on bgmnpp.pk_seq=c.BGMUANPP_FK  " +
					    								"where a.PK_SEQ = '" + nppId + "' and  c.trangthai ='1'  and  bgmnpp.kenh_fk=(select kbh_fk from donhang where pk_seq='"+dhId+"')  and c.SANPHAM_FK = (select PK_SEQ from SANPHAM where ma = '" + spIdss[0].trim() + "')";*/

														query = "select GIAMUANPP as dongia  " +
																"from BGMUANPP_SANPHAM bg inner join SANPHAM sp on bg.SANPHAM_FK = sp.PK_SEQ  " +
																"where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
																"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
																"where a.TUNGAY <= ( select ngaydonhang from ERP_DONDATHANG where pk_seq='" + dhId + "' ) and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select kbh_fk from ERP_DONDATHANG where pk_seq='" + dhId + "' ) and a.DVKD_FK = sp.DVKD_FK  " +
																"order by a.TUNGAY desc  ) and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spIdss[0].trim() + "' )";

														ResultSet rsKm = db.get(query);
														if(rsKm != null)
														{
															if(rsKm.next())
																tongtri = Math.round(rsKm.getFloat("dongia"));
															rsKm.close();
														}
														long gtriKm = tongtri * Integer.parseInt(spIdss[1].trim());

														query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, spMa, soluong, tonggiatri, inchung, kho_fk) " + 
																"select '" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + spIdss[0].trim() + "','" + spIdss[1].trim().replaceAll(",", "") + "','" + Long.toString(gtriKm) + "', inchung, kho_fk from ctkhuyenmai where pk_seq = " + schemeList[i] + "";
														System.out.println("1.Cau lenh chen du lieu: " + query);
														if(!db.update(query))
														{
															db.getConnection().rollback(); 
															error = "Loi khi tao moi 'ERP_DONDATHANG_CTKM_TRAKM': " + query;
															return;
														}
														
														//KIỂM TRA VÀ ĐỀ XUẤT SỐ LÔ MẶC ĐỊNH
														error = this.KiemTraVSDeXuatLo( db, dhId, ngaygiaodich, kho_fk, schemeList[i], spIdss[0].trim(), Double.parseDouble( spIdss[1].trim() ) );
														if( error.trim().length() > 0 )
														{
															db.getConnection().rollback(); 
						    								return;
														}
	
														toalTRA += Integer.parseInt(spIdss[1].trim().replaceAll(",", ""));
													}
													else
													{
														db.getConnection().rollback();
														System.out.println("11.RockBack tai day roi....");
														error += msg + " nên bạn không được hưởng ctkm '" + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + "'. Vui lòng điều chỉnh lại.\n";
													}
												}

												if(toalTRA > 0)
												{
													//NEU PHAN BO THEO SOLUONG THI PHAI CHECK TONG LONG TRA SO VOI TONG LUONG PHAN BO
													flag = "";
													if(schemePhanBo[i].equals("1"))
													{
														System.out.println("[323 Check Ngan Sach ]");
														flag = CheckNghanSach(schemeList[i], nppId, khachhang, Integer.toString(toalTRA), "1", db);

														if(flag.trim().length() > 0)
														{
															db.getConnection().rollback();

															dhBean.init();
															dhBean.setMsg(flag);

															//dhBean.setAplaikhuyenmai(false);
															session.setAttribute("lsxBean", dhBean);

															String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKUpdate.jsp";
															response.sendRedirect(nextJSP);
															return;
														}
													}
												}
											}
											else  //mot san pham
											{
												String[] spIdss = spId.split("-");

												//NEU PHAN BO THEO SOLUONG THI PHAI CHECK TONG LONG TRA SO VOI TONG LUONG PHAN BO
												flag = "";
												System.out.println ("[SpId]"+spIdss[1].trim().replace(",", ""));
												if(schemePhanBo[i].equals("1"))
												{
													flag = CheckNghanSach(schemeList[i], nppId, khachhang, spIdss[1].trim().replace(",", ""), "1", db);

													if(flag.trim().length() > 0)
													{
														db.getConnection().rollback();

														dhBean.init();
														dhBean.setMsg(flag);

														//dhBean.setAplaikhuyenmai(false);
														session.setAttribute("lsxBean", dhBean);

														String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKUpdate.jsp";
														response.sendRedirect(nextJSP);
														return;
													}
												}
												
												long tongtri = 0;
												/*query = "select GIAMUANPP  as dongia  " +
														"from BGMUANPP_SANPHAM bg inner join SANPHAM sp on bg.SANPHAM_FK = sp.PK_SEQ  " +
														"where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
														"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
														"where a.TUNGAY <= ( select ngaydonhang from ERP_DONDATHANG where pk_seq='" + dhId + "' ) and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select kbh_fk from ERP_DONDATHANG where pk_seq='" + dhId + "' ) and a.DVKD_FK = bg.DVKD_FK  " +
														"order by a.TUNGAY desc  ) and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spIdss[0].trim() + "' )";
												
												ResultSet rsKm = db.get(query);
												if(rsKm != null)
												{
													if(rsKm.next())
														tongtri = Math.round(rsKm.getFloat("dongia"));
													rsKm.close();
												}*/
												long gtriKm = tongtri * Integer.parseInt(spIdss[1].trim());

												query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, spMa, soluong, tonggiatri, inchung, kho_fk) select '" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + spIdss[0].trim() + "','" + spIdss[1].trim().replaceAll(",", "") + "','" + Long.toString(gtriKm) + "', inchung, kho_fk from ctkhuyenmai where pk_seq = " + schemeList[i] + "";	
												System.out.println("3.Cau lenh insert du lieu: " + query);
												if(!db.update(query))
												{
													db.getConnection().rollback(); 
													error += "Loi khi tao moi 'ERP_DONDATHANG_CTKM_TRAKM'";
													return;
												}
												
												//KIỂM TRA VÀ ĐỀ XUẤT SỐ LÔ MẶC ĐỊNH
												error = this.KiemTraVSDeXuatLo( db, dhId, ngaygiaodich, kho_fk, schemeList[i], spIdss[0].trim(),  Double.parseDouble( spIdss[1].trim() ) );
												if( error.trim().length() > 0 )
												{
													db.getConnection().rollback(); 
				    								return;
												}
											}
										}
										else
										{
											query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm.replaceAll(",", "") + "')";
											System.out.println("5.Cau lenh tao moi ERP_DONDATHANG_CTKM_TRAKM: " + query);
											if(!db.update(query))
											{
												db.getConnection().rollback(); 
												error += "Loi khi tao moi 'ERP_DONDATHANG_CTKM_TRAKM': " + query;
												return;
											}
										}										
									}
									else
									{
										///Chon hinh thuc la tra tien
										System.out.println("123.Hinh thuc la tra tien.....");
										query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm.replaceAll(",", "") + "')";
										if(!db.update(query))
										{
											db.getConnection().rollback();
											error = query;
											return;
										}
									}
								}
								else
								{
									System.out.println("10.Toi da vao trong nay....");
									if(trakmPrimary[count].equals("0"))
									{
										String msg = CreateKhuyenmai(schemeList[i], schemePhanBo[i], trakm[count], dhId, nppId, Math.round(Double.parseDouble(soxuatkm[i])), khachhang, db);
										if(msg.length() > 0)
										{
											db.getConnection().rollback();
											
											xlkm = new XLkhuyenmaiTT(userId, ngaygiaodich, khachhang, loaidonhang);

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

											String[] scheme = request.getParameterValues("Scheme");
											if(scheme != null)
											{
												List<ICtkhuyenmai> listCTKM = xlkm.SortList(xlkm.getCtkmList(), scheme);
												xlkm.setCtkmList(listCTKM); //Sort lai thu tu
											}

											String showAll = util.antiSQLInspection(request.getParameter("ShowAll"));
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
											xlkm.setMsg("Ngan sach khong du, vui long dieu chinh ngan sach chuong trinh: " + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + " - Con toi da " );
											xlkm.setETC(ETC);
											
											xlkm.setHashA(sp_sl);
											xlkm.setHashB(sp_dg);
											xlkm.setHash_QuyCach(sp_qc);

											xlkm.ApKhuyenMai();
											session.setAttribute("xlkm", xlkm);

											String nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMai.jsp";
											response.sendRedirect(nextJSP);
											return;
										}
									}
									else
									{
										System.out.println("12.Vao trong luu tien trong san pham");

										/*********************************************************************************/
										String msg = CheckNghanSach(schemeList[i], nppId, khachhang, ttTrakm, "0", db);
										if(msg.trim().length() > 0)
										{
											db.getConnection().rollback();
											error = msg;
											return;
										}
										
										/*********************************************************************************/
										query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i].replaceAll(",", ""))) + "','" + ttTrakm.replaceAll(",", "") + "')";
										System.out.println("13.Chen khuyen mai: " + query);
										if(!db.update(query))
										{
											db.getConnection().rollback();
											error = query;
											return;
										}

									}
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

					error += "Loi khi tao moi chuong trinh khuyen mai: " + e1.getMessage();
				}
				db.shutDown();

				dhBean.init();
				if(!error.equals(""))
					dhBean.setMsg(error);

				//dhBean.setAplaikhuyenmai(false);
				session.setAttribute("lsxBean", dhBean);

				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKUpdate.jsp";
				response.sendRedirect(nextJSP);	
			}
			else //submit
			{
				xlkm = new XLkhuyenmaiTT(userId, ngaygiaodich, nppId, dhId);

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

				String[] scheme = request.getParameterValues("Scheme");
				if(scheme != null)
				{
					List<ICtkhuyenmai> listCTKM = xlkm.SortList(xlkm.getCtkmList(), scheme);
					xlkm.setCtkmList(listCTKM); //Sort lai thu tu
				}

				String showAll = util.antiSQLInspection(request.getParameter("ShowAll"));
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
				xlkm.setETC(ETC);

				xlkm.setHashA(sp_sl);
				xlkm.setHashB(sp_dg);
				xlkm.setHash_QuyCach(sp_qc);

				xlkm.ApKhuyenMai();
				xlkm.checkConfirm();
				session.setAttribute("xlkm", xlkm);

				String nextJSP = "/TraphacoHYERP/pages/Center/KhuyenMaiTT.jsp";
				response.sendRedirect(nextJSP);
			}
		} 
	}
     
	private String KiemTraVSDeXuatLo(Idbutils db, String dhId, String ngaygiaodich, String kho_fk, String scheme, String masanpham, double soluong) 
	{
		String msg = "";
		geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
		List<ErpThongtinkho> kq = new ArrayList<ErpThongtinkho>();
		try
		{
			String query = " 	select ct.KHOTT_FK, ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, isnull(MAME, '') as MAME, isnull(MATHUNG, '') as MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( bin_fk, 0 ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo  "+
							"\n 	from ERP_KHOTT_SP_CHITIET ct left join ERP_BIN bin on ct.bin_fk = bin.pk_seq  "+
							"\n 	where ct.KHOTT_FK = (select kho_fk from ctkhuyenmai where pk_seq = " + scheme + ") and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + masanpham + "' )   " +
							"\n 			and ngaynhapkho <= '" + ngaygiaodich + "' and AVAILABLE > 0 " + 
							"\n order by ngaynhapkho asc  ";
			
			System.out.println("::: DE XUAT LO: " + query);
			ResultSet rs = db.get(query);
			
			double total = 0;
			double ttotalXUAT = 0;
			while(rs.next())
			{
				//Element element = (Element) rs.item(i);
				
				double slg = 0;
				double avai = rs.getDouble("AVAILABLE");
				//double avai = WebService.getDouble(element, "AVAILABLE");
				total += avai;
				
				//System.out.println("---- TOTAL: " + total + " -- TONG LUONG: " + tongluong + " -- AVAI: " + avai );
				if(total < soluong)
				{
					slg = avai;
				}
				else
				{
					slg = soluong - ( total - avai );
				}
					
				//System.out.println("---- TOTAL: " + total + " -- TONG LUONG: " + tongluong + " -- SOLUONG: " + slg );
				if(slg > 0)
				{
					ttotalXUAT += slg;
					
					//query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '" + slg + "' as tuDEXUAT union ALL ";
					ErpThongtinkho ttKho = new ErpThongtinkho();
					
					ttKho.khoId = rs.getString("KHOTT_FK");;
					ttKho.spId = rs.getString("sanpham_fk");
					ttKho.solo = rs.getString("solo");
					ttKho.ngayhethan = rs.getString("ngayhethan");
					ttKho.ngaynhapkho = rs.getString("ngaynhapkho");
					
					ttKho.MARQ = rs.getString("MARQ");
					ttKho.hamluong = rs.getString("hamluong");
					ttKho.hamam = rs.getString("hamam");
					
					ttKho.maphieu = rs.getString("MAPHIEU");
					ttKho.phieudt = rs.getString("phieudt");
					ttKho.phieueo = rs.getString("phieueo");
					
					ttKho.vitriId = rs.getString("vitri");
					ttKho.mame = rs.getString("mame");
					ttKho.mathung = rs.getString("mathung");
					ttKho.maphieu = rs.getString("MAPHIEU");
					ttKho.soluong = slg;						
					
					kq.add(ttKho);
				}
				else
				{
					break;
				}
			}
			rs.close();
			
			if( ttotalXUAT != soluong )
			{
				return "Lỗi đề xuất mã hàng ( " + masanpham + " ) số chi tiết " + ttotalXUAT + " đang khác số tổng " + soluong;
			}
			
			for( int j = 0; j < kq.size(); j++  )
			{
				ErpThongtinkho tt = kq.get(j);
				
				/*query = "insert ERP_DONDATHANG_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, mame, mathung, soluong ) " +
						"select '" + this.id + "', pk_seq, N'" + _sp[1] + "', N'" + _sp[2] + "', '" + _sp[3] + "', '" + _sp[4] + "', '" + _sp[5] + "', '" + _sp[6] + "', '" + _soluongCT.replaceAll(",", "") + "' " +
						"from SANPHAM where MA = '" + spMa[i] + "' ";*/
				
				query = "insert ERP_DONDATHANG_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo, soluong, soluongCHUAN,IS_KHONGTHUE,TIENHANG,TIENVAT,TIENHANGSAUVAT, inchung, kho_fk ) " +
						"select '" + dhId + "', '" + tt.spId + "', scheme, N'" + tt.solo + "', '" + tt.ngayhethan + "', '" + tt.ngaynhapkho + "', '" + tt.mame + "', '" + tt.mathung + "', N'" + tt.maphieu + "', '" + tt.MARQ + "', '" + tt.hamluong + "', '" + tt.hamam + "', '" + tt.vitriId + "', '" + tt.phieudt + "', '" + tt.phieueo + "', " + tt.soluong + ", '" + tt.soluong + "','0' "+
						", 0 AS TIENHANG, 0 AS TIENVAT, 0 AS TIENHANGSAUVAT, inchung, kho_fk from ctkhuyenmai where pk_seq = '" + scheme + "'";
				
				System.out.println("1.2.Insert ERP_DONDATHANG_SANPHAM_CHITIET: " + query);
				if(!db.update(query))
				{
					return "Khong the tao moi ERP_DONDATHANG_SANPHAM_CHITIET: " + query;
				}
				
				//CẬP NHẬT KHO
				//this.msg = util.Update_KhoTT(this.ngaydenghi, "HO / Bán đối tác", db, this.khoNhanId, "( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' )", _sp[2], _sp[3], _sp[4], _sp[5], _sp[6], 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")));
				msg = util.Update_KhoTT(ngaygiaodich, "Áp khuyến mãi", db, tt.khoId, tt.spId, tt.solo, tt.ngayhethan, tt.ngaynhapkho, 
								tt.mame, tt.mathung, tt.vitriId, tt.maphieu, tt.phieudt, tt.phieueo, tt.MARQ, tt.hamluong, tt.hamam, "", "", 0, tt.soluong, -1 * tt.soluong, 
								"", "", "");
				
				if( msg.trim().length() > 0 )
				{
					return msg;
				}
				String querycheck = "SELECT ISNULL(BOOKED,0) as BOOKED FROM ERP_KHOTT_SP_CHITIET WHERE KHOTT_FK = '"+ tt.khoId+"' and SANPHAM_FK ='"+ tt.spId+"' and isnull(SOLO,'') = '"+ tt.solo+"' \n"+
				"   and NGAYNHAPKHO = '"+ tt.ngaynhapkho+"' and NGAYHETHAN = '"+ tt.ngayhethan+"' and isnull(MAME,'') = '"+ tt.mame+"'\n"+
				"   and isnull(MAPHIEU,'') = '"+ tt.maphieu+"' and isnull(MATHUNG,'') = '"+ tt.mathung+"' and isnull(MAPHIEUDINHTINH,'') = '"+ tt.phieudt+"' \n"+
				"   and isnull(PHIEUEO,'') = '"+ tt.phieueo+"' and isnull(HAMAM,0) ='"+ tt.hamam+"' and isnull(HAMLUONG,100)  ='"+ tt.hamluong+"' and isnull(BIN_FK,0) = '"+ tt.vitriId+"' \n"+
				" \n";
				ResultSet rscheck = db.get(querycheck);
				if(rscheck.next())
				{
					if(rscheck.getDouble("BOOKED") <tt.soluong)
					{
						return "Lỗi trong quá trình booked kho. Vui lòng kiểm tra lại hoặc liên hệ admin để xửa lý";
					}
				}
				rscheck.close();
			}
			
		}
		catch(Exception ex )
		{
			ex.printStackTrace();
			msg = ex.getMessage();
		}
		
		return msg;
	}

	private String checkTonKhoKhuyenMai(String kho_fk, String nppId, String[] spIdss, String khachhang, dbutils db)
	{
		//kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi thoat
		/*String query = "select available from nhapp_kho where kho_fk = '" + kho_fk + "' and npp_fk = '" + nppId + "' and sanpham_fk = (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khachhang + "')";
		System.out.println("2.Query check ton kho khuyen mai: " + query);
		ResultSet rsCheck = db.get(query);
		
		int avai = 0;
		if(rsCheck != null)
		{
			try 
			{
				//boolean flag = false;
				
				while(rsCheck.next())
				{
					if(rsCheck.getString("available") != null)
					{
						flag = true;
						String avai = rsCheck.getString("available");
						if(Integer.parseInt(avai) < Integer.parseInt(spIdss[1]))
						{
							return "Sản phẩm khuyến mại  " + spIdss[0] + " - Còn tối đa " + avai;	
						}
					}
					avai = rsCheck.getInt("available");
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
	
	private String checkTonkho(String nppId, String ctkmId, String khId, String spId, String spMa, int slg, dbutils db) 
	{
		/*String query = "select available from nhapp_kho where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkmId + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + spId + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId + "')";
		//System.out.println("Check ton kho: " + query + "\n");
		ResultSet rsCheck = db.get(query);
		
		int avai = 0;
		if(rsCheck != null)
		{
			try 
			{
				//boolean flag = false;
				
				while(rsCheck.next())
				{
					if(rsCheck.getString("available") != null)
					{
						flag = true;
						String avai = rsCheck.getString("available");
						if(Integer.parseInt(avai) < slg)
						{
							return "Sản phẩm khuyến mại " + spMa + " - Còn tối đa " + avai;
						}
					}
					
					avai = rsCheck.getInt("available");
				}
				rsCheck.close();
				if(flag == false) //khong co dong du lieu nao, Resualset van khac null
				{
					//System.out.println("San pham khuyen mai " + spMa + " --- Con toi da 0 \n");
					return "San pham khuyen mai " + spMa + " - Con toi da 0";
				}
			}
			catch (SQLException e) { return "error"; }
		}else{
			return "error";
		}
		
		if(avai < slg)
		{
			return "Sản phẩm khuyến mại  " + spMa + " - Còn tối đa " + avai;	
		}*/
		
		return "";
	}
	
	private String CreateKhuyenmai(String ctkmId, String phanbotheoDH, String trakmId, String id, String nppId, long soxuat, String khId, dbutils db)
	{
		String str = "";

		try 
		{ 	
			String sql = "select a.sanpham_fk as spId, a.soluong, b.ma as spMa from trakhuyenmai_sanpham a inner join erp_sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = '" + trakmId + "'";
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
					/*String query = "select c.GIAMUANPP as dongia from NHAPHANPHOI a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.NPP_FK inner join BGMUANPP_SANPHAM c on b.BANGGIAMUANPP_FK = c.BGMUANPP_FK " +
					" inner join  BANGGIAMUANPP bgmnpp on bgmnpp.pk_seq=c.BGMUANPP_FK  " +
					"where a.PK_SEQ = '" + nppId + "' and  c.trangthai ='1'  and  bgmnpp.kenh_fk=(select kbh_fk from donhang where  " +
							" pk_seq = '" + id + "') "+
					" and c.SANPHAM_FK = '" + rsSQl.getString("spId") + "'";*/
					
					String query =  "select GIAMUANPP  as dongia  " +
									"from BGMUANPP_SANPHAM bg inner join SANPHAM sp on bg.SANPHAM_FK = sp.PK_SEQ  " +
									"where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
															"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
															"where a.TUNGAY <= ( select ngaydonhang from ERP_DONDATHANG where pk_seq='" + id + "' ) and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select kbh_fk from ERP_DONDATHANG where pk_seq='" + id + "' ) and a.DVKD_FK = ( select dvkd_fk from ERP_DONDATHANG where pk_seq='" + id + "' )  " +
															"order by a.TUNGAY desc  ) and SANPHAM_FK = '" + rsSQl.getString("spId") + "' ";
					
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
					if(phanbotheoDH.equals("1"))
					{
						String msg = CheckNghanSach(ctkmId, nppId, khId, Integer.toString(slg), "1", db);
						if(msg.trim().length() > 0)
						{
							db.getConnection().rollback();
							return msg;
						}
					}
					/*********************************************************************************/
					
					String error = checkTonkho(nppId, ctkmId, khId, rsSQl.getString("spId"), rsSQl.getString("spMa"), slg, db);
					if(error.length() > 0)
					{
						db.getConnection().rollback();
						return error;
					}
					
					//luu tong gia tri o moi dong sanpham
					query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong, inchung, kho_fk) select '" + id + "','" + ctkmId + "','" + trakmId + "','" + soxuat + "', '" + Long.toString(tongtien) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "', inchung, kho_fk from ctkhuyenmai where pk_seq = '" + ctkmId + "'";
					System.out.println("12.Chen khuyen mai: " + query);
					if(!db.update(query))
					{
						db.getConnection().rollback();
						str = query;
						return str;
					}
					
					//cap nhat kho
					/*query = "Update nhapp_kho set available = available - '" + Integer.toString(slg) + "', booked = booked + '" + Integer.toString(slg) + "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkmId + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + rsSQl.getString("spId") + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId+ "')";   							
					if(!db.update(query))
					{
						db.getConnection().rollback(); 
						str = query;
						return str;
					}*/
				}
			}
			rsSQl.close();
			
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
	
	
	private String CheckNghanSach(String ctkmId, String nppId, String khId, String ngansach, String loai, dbutils db)
	{
		String sql = "";
		
		if(loai.equals("0"))  //PHAN BO KHUYEN MAI THEO SO TIEN
		{
			sql = "select a.CTKM_FK, b.scheme, b.PHANBOTHEODONHANG, a.NGANSACH,  " + 
				  "		ISNULL( 	( select SUM(tonggiatri)  " + 
				  "	  	from ERP_DONDATHANG_CTKM_TRAKM  " + 
				  "	  	where CTKMID = a.CTKM_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 2 ) ), 0 ) as DASUDUNG  " + 
				  "from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ  " + 
				  "where npp_fk = '" + nppId + "' and ctkm_fk = '" + ctkmId + "'  ";
		}
		else  //PHAN BO KHUYEN MAI THEO SO LUONG
		{
			sql = "select a.CTKM_FK, b.scheme, b.PHANBOTHEODONHANG, a.SOXUATTOIDA as NGANSACH, \n" +
				  "		ISNULL( ( select SUM(SOXUAT) \n" +
				  "				  from ( \n" +
				  "	  					select distinct SOXUAT, donhangid from DONHANG_CTKM_TRAKM \n" +
				  "	  					where CTKMID = a.CTKM_FK AND SPMA IS NOT NULL and DONHANGID in ( select PK_SEQ from DONHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 2 and KHACHHANG_FK = '" + khId + "' ) \n" +
				  "						and DONHANGID not in ( select DONHANG_FK from DONHANGTRAVE where DONHANG_FK is not null and NPP_FK = '" + nppId + "' and TRANGTHAI in  ( 1, 3 ) ) \n" +
				  "				) d \n" +
				  "		), 0 ) as DASUDUNG \n" +
				  "from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ \n" +
				  "where npp_fk = '" + nppId + "' and a.ctkm_fk = '" + ctkmId + "'  ";
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
		}
		
		return "";
	}
	

}

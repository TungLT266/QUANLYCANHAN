package geso.traphaco.erp.beans.khauhaotaisancodinh.imp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.khauhaotaisancodinh.IGiaTriKhauHao;
import geso.traphaco.erp.beans.khauhaotaisancodinh.IKhauhaoCCDC;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.util.Utility;

public class KhauhaoCCDC implements IKhauhaoCCDC {
	String userId;
	String Id;
	
	String ctyId;
	String ctyTen;
	
	String nppId;
	String DvkdId;
	
	ResultSet thangRs;
	String thang;
	
	ResultSet namRs;
	String nam;
	String matsstr;
	ResultSet CCDCRs;
	
	String nccdcId;
	ResultSet nhomRs;
	
	String soChungTu;
	
	String msg;
	String[] ma;
	String[] diengiai;
	String[] nhom;
	String[] thangthu;
	String[] thangthutt;
	String[] khauhao;
	String[] khauhaoluyke;
	String[] giatriconlai;
	private String[] thangCuoiHopLe;
	String dienGiaiCT ;
	String flag;
	boolean isKhauhao;
	dbutils db;
	
	List<IGiaTriKhauHao> khauhaoList = new ArrayList<IGiaTriKhauHao>();
	
	public KhauhaoCCDC()
	{
		this.Id = "";
		this.thang = "" + Integer.parseInt(this.getDateTime().substring(5, 7));
		this.nam = this.getDateTime().substring(0, 4);
		this.nccdcId = "";
		this.matsstr = "";
		this.msg = "";
		this.dienGiaiCT= "";
		this.db = new dbutils();
		this.soChungTu="";
		this.flag = "";
		this.isKhauhao=false;
		this.nppId="";
		
	}

	public String getKhId() 
	{
		return this.Id;
	}

	public void setKhId(String Id) 
	{
		this.Id = Id;
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getCongty() 
	{
		return this.ctyTen;
	}

	public void setCongty(String cty) 
	{
		this.ctyTen = cty;
	}

	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
	}


	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public ResultSet getThangRs() 
	{
		return this.thangRs;
	}

	public void setThangRs(ResultSet thangRs) 
	{
		this.thangRs = thangRs;
	}

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;
	}

	public ResultSet getNamRs() 
	{
		return this.namRs;
	}

	public void setNamRs(ResultSet namRs) 
	{
		this.namRs = namRs;
	}

	public ResultSet getNhomRs() 
	{
		return this.nhomRs;
	}

	public void setNhomRs(ResultSet nhomRs) 
	{
		this.nhomRs = nhomRs;
	}

	public String getNccdcId() 
	{
		return this.nccdcId;
	}

	public void setNccdcId(String nccdcId) 
	{
		this.nccdcId = nccdcId;
	}

	public ResultSet getCCDCRs() 
	{
		return this.CCDCRs;
	}

	public void setCCDCRs(ResultSet CCDCRs) 
	{
		this.CCDCRs = CCDCRs;
	}

	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}
	
	public String getMatsstr() 
	{
		return this.matsstr;
	}

	public void setMatsstr(String matsstr) 
	{
		this.matsstr = matsstr;
	}

	public String[] getMa() 
	{
		return this.ma;
	}

	public void setMa(String[] ma) 
	{
		this.ma = ma;
	}

	public String[] getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String[] diengiai) 
	{
		this.diengiai = diengiai;
	}
	
	public String[] getNhomTS() 
	{
		return this.nhom;
	}

	public void setNhomTS(String[] nhom) 
	{
		this.nhom = nhom;
	}

	public String[] getThangthu() 
	{
		return this.thangthu;
	}

	public void setThangthu(String[] thangthu) 
	{
		this.thangthu = thangthu;
	}
	
	public String[] getKhauhao() 
	{
		return this.khauhao;
	}

	public void setKhauhao(String[] khauhao) 
	{
		this.khauhao = khauhao;
	}

	public String[] getKhauhaoluyke() 
	{
		return this.khauhaoluyke;
	}

	public void setKhauhaoluyke(String[] khauhaoluyke) 
	{
		this.khauhaoluyke = khauhaoluyke;
	}

	public String[] getGiatriconlai() 
	{
		return this.giatriconlai;
	}

	public void setGiatriconlai(String[] giatriconlai) 
	{
		this.giatriconlai = giatriconlai;
	}

	
	public void createRs() 
	{
		String query = "";
		
		for( int i = 1; i <= 12; i++ )
		{
			query += "select " + i + " as thang, N'Tháng " + i + "' as thangTen  ";
			if(i < 12)
			{
				query += " union all ";
			}
		}
		
		this.thangRs = db.get(query);
		
		int nam = Integer.parseInt(this.getDateTime().substring(0, 4));
		
		query = "";
		for(int i = nam-2; i <= nam + 4; i++)
		{
			query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
			if(i < nam + 1)
			{
				query += " union all ";
			}
		}
		
		this.namRs = this.db.get(query);
		init();
		
	}

	private void init(){
		try{
			String query;
			ResultSet rs;
			query = "SELECT DISTINCT NCCDC.PK_SEQ AS NCCDCID, NCCDC.DIENGIAI " + 
					"FROM ERP_NHOMCCDC NCCDC " +
					"INNER JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.NHOMCCDC_FK = NCCDC.PK_SEQ " +
  					"WHERE CCDC.TRANGTHAI = 1 ORDER BY NCCDC.DIENGIAI";
			
			this.nhomRs = this.db.get(query);
			System.out.println("ta day"+query);
			
//			if(this.nccdcId.length() == 0){
////				if(this.nhomRs.next()) this.nccdcId = this.nhomRs.getString("NCCDCID");
//			
//				this.nhomRs.close();
//			}
			
			this.nhomRs = this.db.get(query); 
			System.out.println("id pha bo ccdc: " + this.Id);
			if(this.Id.length() > 0){

				query ="SELECT SOCHUNGTU,DIENGIAI,NAM,THANG FROM CHUNGTUKHAUHAOCCDC WHERE PK_SEQ= "+this.Id ;
				ResultSet rsCt= db.get(query);
				if(rsCt.next())
				{
					this.soChungTu=rsCt.getString("SOCHUNGTU");
					this.dienGiaiCT=rsCt.getString("DIENGIAI");
					this.nam=rsCt.getString("NAM");
					this.thang=rsCt.getString("THANG");
				}
				rsCt.close();
				query=" select CCDC.ma +'-' + CCDC.DIENGIAI AS MA,LCCDC.DIENGIAI AS LCCDC,THANG,NAM,KHAUHAO,THANGTHU + ISNULL(CCDC.SOTHANGDAKHAUHAO,0) AS THANGTHU,KHAUHAO,KHAUHAOLUYKE,GIATRICONLAI,GIATRICONLAI+KHAUHAOLUYKE AS TONGGIATRI " +
					  " FROM ERP_KHAUHAOCCDC KHCCDC \n" +
					  " INNER JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ=KHCCDC.CCDC_FK \n" +
					  " INNER JOIN ERP_LOAICCDc lccdc on lccdc.pk_seq= CCDC.loaiccdc_fk \n" +
					  " where CTKHAUHAO_FK =" +this.Id;
				ResultSet khRs=db.get(query);
				System.out.println("query"+query);
				while(khRs.next())
				{	
					IGiaTriKhauHao khauhao = new GiaTriKhauHao();
					khauhao.setDiengiai(khRs.getString("MA"));
					khauhao.setNhomTaiSan(khRs.getString("LCCDC"));
					khauhao.setThangKhauHaoThucTe(khRs.getString("THANGTHU"));
					khauhao.setKhauhaodukien(khRs.getString("KHAUHAO"));
					khauhao.setKhauhaoluykedukien(khRs.getString("khauHaoLuyKe"));
					khauhao.setGiatriconlaidukien(khRs.getString("giaTriConlai"));
					khauhao.setTonggiatri(khRs.getString("tongGiaTri"));
					khauhaoList.add(khauhao);
					
				}
				khRs.close();

			}else{//////////////////////
			
				query =		"SELECT	COUNT( DISTINCT CCDC.MA) as NUM \n" +
							"FROM erp_CONGCUDUNGCU CCDC \n" +
							"INNER JOIN ERP_CONGCUDUNGCU_CHITIET CCDC_CT on CCDC_CT.CCDC_FK = CCDC.PK_SEQ AND CCDC_CT.KHAUHAOTHUCTE = 0 \n" + 
							"INNER JOIN ERP_NHOMCCDC NCCDC on NCCDC.pk_seq = CCDC.nhomCCDC_fk \n" +
							"WHERE CCDC.CONGTY_FK = " + this.ctyId + " and CCDC_CT.KHAUHAODUKIEN > 0 AND LEN(CCDC.THANGBATDAUKH) > 1 \n"	;
				
				if(this.nam.length() > 0 & this.thang.length() > 0){
					query += "AND CCDC.PK_SEQ NOT IN (SELECT CCDC_FK FROM ERP_KHAUHAOCCDC WHERE TRANGTHAI = 1 AND THANG = " + this.thang + " AND NAM = " + this.nam + ") \n";
				}
							

				if(this.nccdcId.length() > 0){
					query = query + " and nccdc.pk_seq = " + this.nccdcId + "\n";
				}
				
				System.out.println("lenh 1:\n" + query + "\n-------------------------------------");
			
				String[] param = new String[3];
				param[0] = this.ctyId;
//				param[1] = (this.nccdcId.trim().length() > 0 ? this.nccdcId : null);
//				param[1]=this.nppId;
				param[1] = this.nam;
				param[2] = this.thang;
				
				rs = this.db.getRsByPro("GetNewPhanBoCCDC_List", param);

				
				String ma = "";
				int i = 0;
				while(rs.next()){
					if(!rs.getString("ma").equals(ma)){
						ma = rs.getString("ma");

					

						if (this.matsstr.length()==0){
							this.matsstr = "'" +  rs.getString("ma") + "'";
						}else{
							this.matsstr = this.matsstr + ",'" +  rs.getString("ma") + "'";
						}
					
					

						IGiaTriKhauHao khauhao = new GiaTriKhauHao();
						khauhao.setDiengiai(rs.getString("diengiai"));
						khauhao.setMataisan(rs.getString("ma"));
						khauhao.setNhomTaiSan(rs.getString("LOAI"));
						khauhao.setThangKhauHaoTrenHeThong(rs.getString("THANGTHU"));
						khauhao.setKhauhaodukien(rs.getString("khauhaodukien"));
						khauhao.setKhauhaoluykedukien(rs.getString("khauhaoluykedukien"));
						khauhao.setGiatriconlaidukien(rs.getString("giatriconlaidukien"));
						khauhao.setThangKhauHaoThucTe(rs.getString("THANGKHAUHAOTT"));
						khauhao.setTonggiatri(rs.getString("tongGiaTri"));
						khauhaoList.add(khauhao);
						
						//Tao danh sach cac san pham, chi dung 1 thang dau de tao danh sach
						if(i == 0){
							if (this.matsstr.length()==0){
								this.matsstr = "'" + rs.getString("ma") + "'";
							}else{
								this.matsstr = this.matsstr + ",'" + rs.getString("ma") + "'";
							}				
						}
					
						
						i++;
					}
				}
			
				System.out.println("query: " + query);
				rs.close();
			
			}
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
	}
	
	public boolean save(HttpServletRequest request)throws ServletException, IOException
	{	
		String query;
		try
		{
			db.getConnection().setAutoCommit(false);
			String matsstr = "(";
			this.ma = request.getParameterValues("maccdc");
			String khauhao;
			String thangthu;
			String khauhaoluyke;
			String giatriconlai;
			String tonggiatri;
			
			
			
			this.CheckKhauHao(this.nam, this.thang);
			/// NẾU ĐÃ TỒN TẠI KHẤU HAO THÌ XÓA HỦY KẾ TOÁN + GIẢM GIÁ TRỊ PHÂN BỔ TRUNG TÂM CHI PHÍ
			if(isKhauhao)
			{
				
				query = " DELETE ERP_PHATSINHKETOAN WHERE LOAICHUNGTU = N'Phân bổ chi phí trả trước' and sochungtu in (Select pk_Seq from CHUNGTUKHAUHAOCCDC where  thang=" +this.thang +" AND nam= "+this.nam +") ";		
				
				System.out.println(query);
				if(!db.update(query))
				{
					msg = "MC1.2 Không thể hủy ERP_PHATSINHKETOAN " + query;
					db.getConnection().rollback();
					return false;
				}
				query="select nam,thang,CCDC_fk,thangthu,CTKHAUHAO_FK from erp_khauhaoccdc where thang=" +this.thang +" AND nam= "+this.nam ;
				ResultSet rs= db.get(query);
				while(rs.next())
				{
					String id=rs.getString("nam") + ";" + rs.getString("thang") + ";" + rs.getString("CCDC_fk") + ";" + rs.getString("thangthu")+ ";" + rs.getString("CTKHAUHAO_FK") ;
					
					if(!this.Cancel(db,id))
					{
						this.db.getConnection().rollback();
						return false;
					}
					query="DELETE ERP_KHAUHAOCCDC WHERE THANG ="+thang + "AND  NAM ="+nam ;
					if(!this.db.update(query))
					{
							this.msg="Không thể cập nhật trạng thái "+query;
							this.db.getConnection().rollback();
							return false;
					}
					
					query="DELETE CHUNGTUKHAUHAOCCDC WHERE THANG ="+thang + "AND NAM ="+nam ;
					if(!this.db.update(query))
					{
							this.msg="Không thể cập nhật trạng thái "+query;
							this.db.getConnection().rollback();
							return false;
					}
					
				}
					
			}
			query = "INSERT INTO CHUNGTUKHAUHAOCCDC(THANG, NAM,TRANGTHAI,DIENGIAI,SOCHUNGTU,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA) \n" +
			"VALUES(" + this.thang.trim() + "," + this.nam.trim() + ",1,N'" + this.dienGiaiCT + "', N'" + this.soChungTu + "', \n" +
			"'" + getDateTime() + "','" + getDateTime() + "',"+this.userId+","+this.userId+") \n";

			if(!this.db.update(query))
			{
					this.msg="Không thể cập nhật CHUNGTUKHAUHAOCCDC "+query;
					this.db.getConnection().rollback();
					return false;
		
			}
			String id="";
			ResultSet rsCTKH = db.get("select IDENT_CURRENT('CHUNGTUKHAUHAOCCDC') as ID ");
			if(rsCTKH.next())
			{
				id = rsCTKH.getString("ID");
			}
			rsCTKH.close();
			
			
			Utility util = new Utility();
			if(this.ma != null)
			{
				for(int i = 0; i < this.ma.length ; i++)
				{
					khauhao = util.antiSQLInspection(request.getParameter("khauhao_" + ma[i])).replace(",", "");
					thangthu = util.antiSQLInspection(request.getParameter("thangthu_" + ma[i]));
					khauhaoluyke = util.antiSQLInspection(request.getParameter("khauhaoluyke_" + ma[i])).replace(",", "");
					giatriconlai = util.antiSQLInspection(request.getParameter("giatriconlai_" + ma[i])).replace(",", "");
					tonggiatri = util.antiSQLInspection(request.getParameter("tonggiatri_" + ma[i])).replace(",", "");
					
					query = "SELECT COUNT(*) AS NUM " +
							"FROM ERP_KHAUHAOCCDC WHERE THANG = "  + this.thang.trim() + " " +
							"AND NAM = " + this.nam.trim() + " AND CCDC_FK = " + this.ma[i].trim() + " AND TRANGTHAI = 1 ";
					System.out.println(query);
					
					ResultSet rs = this.db.get(query);
					rs.next();
					if(rs.getString("NUM").equals("0")){
					
						query = "INSERT INTO ERP_KHAUHAOCCDC(THANG, NAM, CCDC_FK, KHAUHAO, khauHaoLuyke, giaTriConLai, THANGTHU,CTKHAUHAO_FK) \n" +
								"VALUES(" + this.thang.trim() + "," + this.nam.trim() + "," + this.ma[i].trim() + ", " + khauhao + "," + khauhaoluyke + "," + giatriconlai + ", \n" +
								"'" + thangthu + "',"+id+")\n";
				
						System.out.println("1.Khau hao tai san: " + query);
						if(!this.db.update(query))
						{							
							this.msg="Không thể cập nhật ERP_CONGCUDUNGCU";
							this.db.getConnection().rollback();
							return false;
					
						}	
					}
					else
					{
						this.msg="Tháng được chọn đã khấu hao tài sản. Vui lòng chọn tháng khác";
						this.db.getConnection().rollback();
						return false;						
					}
					matsstr = matsstr + this.ma[i] + ",";

				}
			
				if(matsstr.length() > 1)
				{
					matsstr = matsstr.substring(0, matsstr.length() - 1) + ")\n";
					String ngayghinhan = this.nam + "-" + ( this.thang.trim().length() < 2 ? "0" + this.thang : this.thang );
					ngayghinhan = this.nam + "-" + ( this.thang.trim().length() < 2 ? "0" + this.thang : this.thang )+ "-01";	
					ngayghinhan = getLastDayOfMonth(ngayghinhan);
					System.out.println("aaaaaaaaaaaa"+ngayghinhan);
		
					
					query = "SELECT CCDC.PK_SEQ as ccdcId, NCP.TAIKHOAN_FK as SOHIEUTAIKHOAN,CCDC.MA as MACCDC\n" +
					",  ROUND(SUM(ISNULL(DIEUCHUYEN.PHANTRAM, 0) * KH.KHAUHAO / 100 ),0)AS TOTALKHAUHAO, \n" +  
					"TK2.SOHIEUTAIKHOAN AS SOHIEUTAIKHOANCO, \n" +
					"DIEUCHUYEN.KHOANMUCCHIPHI_FK AS KHOANMUCPHI_FK  \n" +
					"FROM ERP_CONGCUDUNGCU CCDC \n" +
//					" INNER JOIN ERP_CONGDUNGCCDC CD ON CCDC_CD.CONGDUNG_FK = CD.PK_SEQ \n" + 
//					" INNER JOIN ERP_TAIKHOANKT TK1 ON CD.TAIKHOAN_FK = TK1.SOHIEUTAIKHOAN \n" + 
//					" INNER JOIN ERP_CONGCUDUNGCU CCDC ON CCDCKMCP.CCDC_FK = CCDC.PK_SEQ \n"  
					
					 "LEFT JOIN \n" 
					+ " ("  
					+  "select DISTINCT  ISNULL(dcccdc.khoanmucchiphi_fk,kmcp.NHOMCHIPHI_FK) as KHOANMUCCHIPHI_FK,\n" 
										+ "ISNULL(DCCCDC.CCDC_FK,KMCP.CCDC_FK) as CCDC_FK,\n"
										+ "ISNULL(dcccdc.phantram,kmcp.phantram) as phantram,dc.NGAYCHUNGTU \n" 
										+ "from ERP_CONGCUDUNGCU ccdc \n"
										+ "left join ERP_CONGCUDUNGCU_KHOANMUCCHIPHI kmcp on ccdc.pk_seq=kmcp.CCDC_FK \n"
										+ " LEFT JOIN  \n" 
										+ "( \n"
										+ "SELECT ROW_NUMBER() OVER (PARTITION BY DC.CCDC_FK \n"
										+ "order by DC.NGAYCHUNGTU desc) AS STT,CCDC_FK,DC.NGAYCHUNGTU,PK_SEQ \n"
										+ " FROM  ERP_DIEUCHUYENCONGCUDUNGCU DC  \n"
										+ " ) dc ON dc.CCDC_FK= ccdc.PK_SEQ AND dc.STT=1 and month(dc.NGAYCHUNGTU)<='"+ngayghinhan.substring(5,7)+"'  \n"
										+ "left join ERP_DIEUCHUYENCONGCUDUNGCU_KHOANMUCCHIPHI dcccdc on dcccdc.DIEUCHUYENCONGCUDUNGCU_FK=dc.PK_SEQ \n"+

					")DIEUCHUYEN ON DIEUCHUYEN.CCDC_FK=CCDC.PK_SEQ \n"+
					"LEFT JOIN \n" + 
					"( \n" +
					"SELECT ROW_NUMBER() OVER \n"+ 
							  "(PARTITION BY DANHGIA.CCDC_FK order by DANHGIA.NGAYCHUNGTU desc) AS STT, \n"+
							  "CCDC_FK,DANHGIA.NGAYCHUNGTU \n"+
							  "FROM  ERP_DANHGIALAICHIPHITRATRUOC DANHGIA \n"+
							  "where DANHGIA.NGAYCHUNGTU<='"+ngayghinhan+"'  \n"+
					") DANHGIA_NEW ON DANHGIA_NEW.CCDC_FK= CCDC.PK_SEQ AND DANHGIA_NEW.STT=1"+
					" LEFT JOIN ERP_DANHGIALAICHIPHITRATRUOC DANHGIA ON DANHGIA.CCDC_FK=DANHGIA_NEW.CCDC_FK \n"+
					" INNER JOIN Erp_LOAICCDC LCCDC ON LCCDC.PK_SEQ = ISNULL(DANHGIA.LOAICCDC_FK_NEW,CCDC.LOAICCDC_FK) \n" +
					" INNER JOIN ERP_TAIKHOANKT TK2 ON LCCDC.TAIKHOAN_FK = TK2.pk_seq  \n" +
					"INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = DIEUCHUYEN.KHOANMUCCHIPHI_FK \n"+ 
					" INNER JOIN ERP_KHAUHAOCCDC KH ON CCDC.PK_SEQ = KH.CCDC_FK   \n" +
					" WHERE CCDC.PK_SEQ IN  " + matsstr + " AND KH.THANG = '" + this.thang + "' AND KH.NAM = '" + this.nam + "' \n" +  
					" GROUP BY NCP.TAIKHOAN_FK, CCDC.MA, CCDC.LOAICCDC_FK, TK2.SOHIEUTAIKHOAN,CCDC.PK_SEQ,CCDC.TTCP_FK,DIEUCHUYEN.KHOANMUCCHIPHI_FK\n";					

					
					System.out.println("____LAY TAI KHOAN KHAU HAO: \n" + query  + "\n================================");
					ResultSet rsTk = this.db.get(query);
		
					if (rsTk != null)
					{
						while(rsTk.next())
						{
							double totalKhauHao = rsTk.getDouble("totalKhauHao");
							
							String taikhoanCO_SoHieu = rsTk.getString("sohieutaikhoanCO");
							String taikhoanNO_SoHieu = rsTk.getString("sohieutaikhoan");
							
							String khoanmucphi_fk = rsTk.getString("KHOANMUCPHI_FK")==null?"":rsTk.getString("KHOANMUCPHI_FK");
//							if(khoanmucphi_fk==null||khoanmucphi_fk.length()==0)
//							{
//								msg = "Lỗi xác định khoản mục chi phí của mã chi phí phân bổ "+rsTk.getString("MACCDC");
//								db.getConnection().rollback();
//								return false;
//							}
							
						
							if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
							{
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								db.getConnection().rollback();
								return false;
							}
							
							String tiente_fk = "100000";
							
							String soChungTu = nam + "" + (thang.trim().length() < 2 ? ("0" + thang) : thang);
							if(totalKhauHao>0)
							{
								
								this.msg = util.Update_TaiKhoan_TheoSoHieu_DienGiai_KhoanMuc_NO_CO(db, thang, nam, ngayghinhan, ngayghinhan, "Phân bổ chi phí trả trước", id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
															Double.toString(totalKhauHao), Double.toString(totalKhauHao),"","","0", "Công cụ dụng cụ", rsTk.getString("ccdcId"), "0", "", "", tiente_fk, "", "1", Double.toString(totalKhauHao), Double.toString(totalKhauHao), "",this.dienGiaiCT,khoanmucphi_fk,"",this.soChungTu,"1" );
								
//								this.msg = util.Update_TaiKhoan_TheoSoHieu_DienGiai_KhoanMuc_NO_CO(db, thang, nam, ngayghinhan, ngayghinhan, "Khấu hao công cụ dụng cụ", id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
//										Double.toString(totalKhauHao), Double.toString(totalKhauHao),"","","0", "Công cụ dụng cụ", rsTk.getString("ccdcId"), "0",rsTk.getString("ccdcId"),"", "", tiente_fk, "", "1", Double.toString(totalKhauHao), Double.toString(totalKhauHao), "",this.dienGiaiCT,khoanmucphi_fk,"",this.soChungTu,this.ctyId,this.nppId,DVKDID );
								if(this.msg.trim().length() > 0)
								{
									db.getConnection().rollback();
									return false;
								}
							}
	
						}
						rsTk.close();
					}
					
					
//					query =  "SELECT CCDC.TTCP_FK AS TTCPID, PB.TTCPNHAN_FK AS TTCPNHANID, PB.PHANTRAM, KHCCDC.KHAUHAO   \n" +
//							 "FROM ERP_CONGCUDUNGCU CCDC \n" +
//							 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = CCDC.TTCP_FK  \n" +
//							 "INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TTCPCHO_FK = TTCP.PK_SEQ AND CCDC.PK_SEQ = PB.CCDC_FK \n" +
//							 "INNER JOIN ERP_KHAUHAOCCDC KHCCDC ON KHCCDC.CCDC_FK = CCDC.PK_SEQ  " +
//							 "WHERE CCDC.PK_SEQ IN " + matsstr + " AND KHCCDC.THANG = " + this.thang + " AND KHCCDC.NAM = " + this.nam + "  \n" +
//							 "union ALL \n" +
//							 "SELECT CCDC.TTCP_FK AS TTCPID, TTCP.PK_SEQ AS TTCPNHANID, 100 as PHANTRAM, KHCCDC.KHAUHAO   \n" +
//							 "FROM ERP_CONGCUDUNGCU CCDC \n" +
//							 "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = CCDC.TTCP_FK  \n" +
//							 "INNER JOIN ERP_KHAUHAOCCDC KHCCDC ON KHCCDC.CCDC_FK = CCDC.PK_SEQ  \n" +
//							 "WHERE CCDC.PK_SEQ IN " + matsstr + " AND KHCCDC.THANG = " + this.thang + " AND KHCCDC.NAM = " + this.nam + " \n" ;
//					
//					System.out.println(query);
//					
//					ResultSet rs = this.db.get(query);
//					
//					if (rs != null)
//					{
//						while(rs.next()){
//	
//							query = "INSERT INTO ERP_TRUNGTAMCHIPHI_THUCCHI(TTCP_FK, THANG, NAM, THUCCHI) VALUES" +
//									"(" + rs.getString("TTCPNHANID") + ", '" + this.thang + "','" + this.nam + "', CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100 )";
//							
//							System.out.println(query);
//							
//							if(!this.db.update(query)){
//								query = "UPDATE ERP_TRUNGTAMCHIPHI_THUCCHI SET THUCCHI = THUCCHI + CONVERT(float, " + rs.getString("KHAUHAO") + ")*CONVERT(float," + rs.getString("PHANTRAM") +")/100  " +
//										"WHERE TTCP_FK = " + rs.getString("TTCPNHANID") + " AND NAM = '" + this.nam + "' AND THANG = '" + this.thang + "'";
//								
//								System.out.println(query);
//								this.db.update(query);
//							}
//							
//						}
//						rs.close();
//					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Không thể thực hiện khấu hao. " + e.getMessage();
			return false;
		}

	}

	

	public boolean Cancel(Idbutils myDb, String Id)
	{	
		String query;
		try
		{
			Utility util= new Utility();
			List<Integer> thangNamKs= new ArrayList<Integer>(2);
			Utility.GetThangKhoaSoMax(myDb, thangNamKs);
			
			
			
			String[] tmp = Id.split(";");				
			String nam = tmp[0];
			String thang = tmp[1];
			String ccdcId = tmp[2];
			String lanthu = tmp[3];
			String sct = tmp[4]; // SỐ PK_SEQ
			
			if((Integer.parseInt(nam)<thangNamKs.get(1))||(Integer.parseInt(nam)==thangNamKs.get(1) && Integer.parseInt(thang)<thangNamKs.get(0)))
			{
				msg = "Dữ liệu phát sinh kế toán tháng "+thang +" , năm  "+nam +" đã có khóa sổ! không thể hủy";
				myDb.getConnection().rollback();
				return false;
			}
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'Phân bổ chi phí trả trước' and SOCHUNGTU = "+sct+" and maDoiTuong = " + ccdcId;
			System.out.println(query);
			ResultSet rsPSKT = myDb.get(query);
			try 
			{
				while(rsPSKT.next())
				{
					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
					double NO = rsPSKT.getDouble("NO");
					double CO = rsPSKT.getDouble("CO");
					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
					
					//NEU LA CO THI BAY GIO GHI GIAM CO LAI
					if( NO > 0 )
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					else
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					
					System.out.println("1.REVERT NO-CO: " + query);
					
					if(myDb.updateReturnInt(query)<0)
					{
						msg = "MC1.1 KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
						myDb.getConnection().rollback();
						return false;
					}
					
				}
				rsPSKT.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			//HỦY KẾ TOÁN ĐÃ GHI NHẬN
			
			
							
			
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			myDb.update("rollback");
			this.msg = "MC1.3 Không thể thực hiện khấu hao. " + e.getMessage();
			return false;
		}

	}

	public boolean chayKeHoachPhanBoCCDC(dbutils myDb, String ccdcId)
	{
		String[] param = new String[1];
		param[0] = ccdcId;
		try{
			String result = myDb.execProceduce2("KeHoachPhanBo", param);
			System.out.println("param: " + param[0]);
			if (result.trim().length() > 0)
			{
				this.setMsg("CKHPBCCDC1.2 Không thể tạo mới tài sản cố định \n" + result);
				myDb.getConnection().rollback();
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMsg("CKHPBCCDC1.3 Không thể tạo mới tài sản cố định \n");
			try {
				myDb.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	public boolean Tinhkhauhao_Update(String Id, double tonggiatri, double giatriconlai){
		double thanhtien = tonggiatri;
		double sotienconlai = giatriconlai;
		double luykebandau = 0;
		double conlaibandau = 0;
		
		int month = 1;
		int n = 0;
		
		// Tinh so thang khau hao
		String query = "SELECT COUNT(THANG)  AS NUM FROM ERP_CONGCUDUNGCU_CHITIET WHERE CCDC_FK = '" + Id + "'";
		System.out.println("khauhao_update: " + query);
		ResultSet rs = this.db.get(query);
		
		try{
			if(rs != null) {
				if (rs.next())			
					n = rs.getInt("NUM");
				rs.close();
								
				query = "SELECT THANG, KHAUHAOLUYKETHUCTE, GIATRICONLAITHUCTE FROM ERP_CONGCUDUNGCU_CHITIET WHERE KHAUHAOTHUCTE > 0  AND CCDC_FK = " + Id + " ORDER BY THANG DESC";
					
				rs = this.db.get(query);
				if (rs != null)
				{
					if(rs.next()){					
						month = rs.getInt("THANG") + 1;
						luykebandau = rs.getDouble("KHAUHAOLUYKETHUCTE");
						conlaibandau = rs.getDouble("GIATRICONLAITHUCTE");
						
					}
					rs.close();
				}
			}
			double[] khdkVal = new double[n];
			double[] lkdkVal = new double[n];
			double[] gtdkVal = new double[n];
		
			lkdkVal[0] = 0;
			
			int m = n - month + 1;
			System.out.println("m: " + m);
			
			for(int i = 0 ; i < m; i++)
			{
										
				if(i == (m-1))  // thang cuoi cung
				{		
					if(i > 0)
						khdkVal[i] = thanhtien - lkdkVal[i-1];
					else
						khdkVal[i] = sotienconlai;
					
					if(i > 0) 
						lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i] ;

					gtdkVal[i] = thanhtien - lkdkVal[i];
				}
				else
				{
					khdkVal[i] = Math.round(sotienconlai/(m));
					
					if(i > 0) 
						lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i] ;
					
					gtdkVal[i] = thanhtien - lkdkVal[i];
				}					
			 
				query = " INSERT INTO ERP_CONGCUDUNGCU_CHITIET (CCDC_FK, THANG, KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, " +
						" KHAUHAOTHUCTE, KHAUHAOLUYKETHUCTE, GIATRICONLAITHUCTE) " +
						" VALUES( '" + Id + "', '" + month + "', "+ khdkVal[i] + ", " + lkdkVal[i] + ", " + gtdkVal[i] + ", '0', " + luykebandau + ", " + conlaibandau + " )";
				
				System.out.println("khauhao_update 1: " + query);
				if(!this.db.update(query)){
					query = "UPDATE ERP_CONGCUDUNGCU_CHITIET SET KHAUHAODUKIEN = "+ khdkVal[i] + ", KHAUHAOLUYKEDUKIEN = " + lkdkVal[i] + ", " +
							"GIATRICONLAIDUKIEN = " + gtdkVal[i] + ", KHAUHAOTHUCTE = '0', KHAUHAOLUYKETHUCTE = " + luykebandau + ", GIATRICONLAITHUCTE = " + conlaibandau + " " +
							"WHERE CCDC_FK = '" + Id + "' AND THANG = '" + month + "'";
					
					System.out.println("khauhao_update 2: " + query);
					this.db.update(query);
				}
				month++;
			}
				
		}catch(java.sql.SQLException e){
			System.out.println("khauhao_update: " + e.toString());
			return false;
		}
		return true;
	}
	
	public boolean Tinhkhauhao_Update(dbutils myDb, String Id, double tonggiatri, double giatriconlai){
		double thanhtien = tonggiatri;
		double sotienconlai = giatriconlai;
		double luykebandau = 0;
		double conlaibandau = 0;
		
		int month = 1;
		int n = 0;
		
		// Tinh so thang khau hao
		String query = "SELECT COUNT(THANG)  AS NUM FROM ERP_CONGCUDUNGCU_CHITIET WHERE CCDC_FK = '" + Id + "'";
		System.out.println("khauhao_update: " + query);
		ResultSet rs = myDb.get(query);
		
		try{
			if(rs != null) {
				if (rs.next())			
					n = rs.getInt("NUM");
				rs.close();
								
				query = "SELECT THANG, KHAUHAOLUYKETHUCTE, GIATRICONLAITHUCTE FROM ERP_CONGCUDUNGCU_CHITIET WHERE KHAUHAOTHUCTE > 0  AND CCDC_FK = " + Id + " ORDER BY THANG DESC";
					
				rs = myDb.get(query);
				if (rs != null)
				{
					if(rs.next()){					
						month = rs.getInt("THANG") + 1;
						luykebandau = rs.getDouble("KHAUHAOLUYKETHUCTE");
						conlaibandau = rs.getDouble("GIATRICONLAITHUCTE");
						
					}
					rs.close();
				}
			}
			double[] khdkVal = new double[n];
			double[] lkdkVal = new double[n];
			double[] gtdkVal = new double[n];
		
			lkdkVal[0] = 0;
			
			int m = n - month + 1;
			System.out.println("m: " + m);
			
			for(int i = 0 ; i < m; i++)
			{
										
				if(i == (m-1))  // thang cuoi cung
				{		
					if(i > 0)
						khdkVal[i] = thanhtien - lkdkVal[i-1];
					else
						khdkVal[i] = sotienconlai;
					
					if(i > 0) 
						lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i] ;

					gtdkVal[i] = thanhtien - lkdkVal[i];
				}
				else
				{
					khdkVal[i] = Math.round(sotienconlai/(m));
					
					if(i > 0) 
						lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i] ;
					
					gtdkVal[i] = thanhtien - lkdkVal[i];
				}					
			 
				query = " INSERT INTO ERP_CONGCUDUNGCU_CHITIET (CCDC_FK, THANG, KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, " +
						" KHAUHAOTHUCTE, KHAUHAOLUYKETHUCTE, GIATRICONLAITHUCTE) " +
						" VALUES( '" + Id + "', '" + month + "', "+ khdkVal[i] + ", " + lkdkVal[i] + ", " + gtdkVal[i] + ", '0', " + luykebandau + ", " + conlaibandau + " )";
				
				System.out.println("khauhao_update 1: " + query);
				if(!myDb.update(query)){
					query = "UPDATE ERP_CONGCUDUNGCU_CHITIET SET KHAUHAODUKIEN = "+ khdkVal[i] + ", KHAUHAOLUYKEDUKIEN = " + lkdkVal[i] + ", " +
							"GIATRICONLAIDUKIEN = " + gtdkVal[i] + ", KHAUHAOTHUCTE = '0', KHAUHAOLUYKETHUCTE = " + luykebandau + ", GIATRICONLAITHUCTE = " + conlaibandau + " " +
							"WHERE CCDC_FK = '" + Id + "' AND THANG = '" + month + "'";
					
					System.out.println("khauhao_update 2: " + query);
					myDb.update(query);
				}
				month++;
			}
				
		}catch(java.sql.SQLException e){
			e.printStackTrace();
			System.out.println("khauhao_update: " + e.toString());
			return false;
		}
		return true;
	}
	

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void DBClose(){
		try{
			if (this.CCDCRs != null)
				this.CCDCRs.close();
			if(thangRs != null) thangRs.close();
			if(namRs != null) namRs.close();
			if(nhomRs != null) nhomRs.close();
			db.shutDown();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
	}
	
	public static String getLastDayOfMonth(String date)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedDate;
		Calendar c = null;
		try {
			convertedDate = dateFormat.parse(date);
			c = Calendar.getInstance();
			c.setTime(convertedDate);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		Date lastDayOfMonth = c.getTime();  
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		return sdf.format(lastDayOfMonth);
	}
	public void CheckKhauHao(String nam,String thang)
	{
		String query="Select count(*) as NUM FROM ERP_KHAUHAOCCDC WHERE THANG ="+thang +" AND NAM ="+nam ;
		System.out.println("check khau hao "+query);
		ResultSet rs= db.get(query);
		try
		{
			rs.next();
			int num = rs.getInt("NUM");
			
			if(num>0)
			{
				this.isKhauhao=true;
				return;
			}
			else
			{
				this.isKhauhao=false;
				return;
			}
		}
		catch (Exception e) {;
			this.isKhauhao=false;
			return;
		}
	}
	public void setThangCuoiHopLe(String[] thangCuoiHopLe) {
		this.thangCuoiHopLe = thangCuoiHopLe;
	}

	public String[] getThangCuoiHopLe() {
		return thangCuoiHopLe;
	}

	public String[] getThangthutt() {
		return thangthutt;
	}

	public void setThangthutt(String[] thangthutt) {
		this.thangthutt = thangthutt;
	}
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
	}
	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT = dienGiaiCT;
	}

	public List<IGiaTriKhauHao> getKhauhaoList() {
		return khauhaoList;
	}

	public void setKhauhaoList(List<IGiaTriKhauHao> khauhaoList) {
		this.khauhaoList = khauhaoList;
	}

	public String getSoChungTu() {
		return soChungTu;
	}

	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public boolean isKhauhao() {
		return isKhauhao;
	}

	public void setKhauhao(boolean isKhauhao) {
		this.isKhauhao = isKhauhao;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getDvkdId() {
		return DvkdId;
	}

	public void setDvkdId(String dvkdId) {
		DvkdId = dvkdId;
	}
}
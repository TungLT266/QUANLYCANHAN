package geso.traphaco.center.servlets.chuongtrinhkhuyenmai;

import geso.traphaco.center.beans.ctkhuyenmai.IDieukienDetail;
import geso.traphaco.center.beans.ctkhuyenmai.IDieukienkm;
import geso.traphaco.center.beans.ctkhuyenmai.ITrakm;
import geso.traphaco.center.beans.ctkhuyenmai.ITrakmDetail;
import geso.traphaco.center.beans.ctkhuyenmai.imp.DieukienDetail;
import geso.traphaco.center.beans.ctkhuyenmai.imp.Dieukienkm;
import geso.traphaco.center.beans.ctkhuyenmai.imp.Trakm;
import geso.traphaco.center.beans.ctkhuyenmai.imp.TrakmDetail;
import geso.traphaco.center.beans.dieukienkhuyenmai.ISanpham;
import geso.traphaco.center.beans.dieukienkhuyenmai.imp.Sanpham;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

@WebServlet("/AjaxChuongTrinhKhuyenMai")
public class AjaxChuongTrinhKhuyenMai extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public AjaxChuongTrinhKhuyenMai()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		String action = request.getParameter("action") == null ? "" : request.getParameter("action");
		String KvId = request.getParameter("kvId") == null ? "" : request.getParameter("kvId");
		String vungId = request.getParameter("vungId") == null ? "" : request.getParameter("vungId");
		String kenhId = request.getParameter("kenhId") == null ? "" : request.getParameter("kenhId");
		String loaicnId = request.getParameter("loaicnId") == null ? "" : request.getParameter("loaicnId");
		String nppSelected = request.getParameter("nppSelected") == null ? "" : request.getParameter("nppSelected");
		String chucvu = request.getParameter("chucvu") == null ? "" : request.getParameter("chucvu");
		System.out.println("____action____" + action + "____KvId____" + KvId + "___nppSelected__" + nppSelected + "__ChucVu_" + chucvu);
		String query = "";
		if (action.equals("ajaxNpp"))
		{
			dbutils db = new dbutils();
			query="SELECT PK_SEQ as nppId ,TEN as nppTen,MA as nppMa FROM NHAPHANPHOI WHERE TRANGTHAI=1 and isKHACHHANG = '0' ";
			if(KvId.length()>0)
			{
				query+=" and khuvuc_fk in ("+KvId+")";
			}
			if(vungId.length()>0)
			{
				query+=" and khuvuc_fk in ( select pk_seq from khuvuc where vung_fk in ("+vungId+")   )";
			}
			if(kenhId.length()>0)
			{
				query+=" and pk_Seq in ( select npp_fk from nhapp_kbh where kbh_fk in ("+kenhId+")  )";
			}
			
			if(loaicnId.length()>0)
			{
				query += " and loaiNPP in ("+ loaicnId +") ";
			}
			
			if(nppSelected.length()>0)
			{
				query+=" and pk_Seq   not in ( "+nppSelected+"  )";
				query+=" union all " +
						" SELECT PK_SEQ as nppId ,TEN as nppTen,MA as nppMa FROM NHAPHANPHOI WHERE TRANGTHAI=1 and pk_seq in ("+nppSelected+")  ";
			}
			System.out.println("___________JSON NPP_________________" + query);
			ResultSet rs = db.get(query);
			List<INhaPhanPhoi> nppList = new ArrayList<INhaPhanPhoi>();
			if (rs != null)
			{
				try
				{
					INhaPhanPhoi npp = null;
					while (rs.next())
					{
						npp = new NhaPhanPhoi();
						npp.setNppTen(rs.getString("nppTen"));
						npp.setNppId(rs.getString("nppId"));
						npp.setNppMa(rs.getString("nppMa"));
						nppList.add(npp);
					}
					rs.close();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			db.shutDown();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toJson(nppList));
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();	
		String type = request.getParameter("action") == null ? "" : request.getParameter("action");
		String dkkmId = util.antiSQLInspection(request.getParameter("dkkmId")==null?"":request.getParameter("dkkmId"));
		String trakmId = util.antiSQLInspection(request.getParameter("trakmId")==null?"":request.getParameter("trakmId"));
		String ctkmId = util.antiSQLInspection(request.getParameter("ctkmId")==null?"":request.getParameter("ctkmId"));
		int i=Integer.parseInt(util.antiSQLInspection(request.getParameter("pos")==null?"":request.getParameter("pos")));
		
		if(type.equals("AjaxDKKM")&&dkkmId.length()>0)
		{	
			dbutils db = new dbutils();
			String query =
			"select a.pk_seq as dkkmId, a.diengiai, cast(isnull(a.tongluong, 0) as numeric(18, 0)) as tongluong, " +
					"cast(isnull(a.tongtien, 0) as numeric(18, 0)) as tongtien, " +
					"isnull(b.pheptoan, '2') as pheptoan, b.thutudieukien, a.loai, a.isThung " +
				"from dieukienkhuyenmai a left join ctkm_dkkm b on a.pk_seq = b.dkkhuyenmai_fk   and b.ctkhuyenmai_fk="+ctkmId+" " +
				"where  a.pk_seq='"+dkkmId+"' order by b.thutudieukien asc";
			ResultSet rs = db.get(query);
			List<IDieukienkm> dkkmList = new ArrayList<IDieukienkm>();
			if(rs != null)
			{
				try 
				{
					IDieukienkm dkkm = null;
					while(rs.next())
					{
						String diengiai = rs.getString("diengiai");
						String tongluong = rs.getString("tongluong");
						String tongtien = rs.getString("tongtien");
						String pheptoan = rs.getString("pheptoan");
						String thutudk = rs.getString("thutudieukien");
						dkkm = new Dieukienkm(dkkmId, diengiai, tongluong, tongtien, pheptoan, thutudk);
						if(rs.getString("isThung").equals("1"))
						{
							dkkm.setTheothung(true);
						}
						else
						{
							dkkm.setTheothung(false);
						}
						
						IDieukienDetail dkDetail;
						dkDetail = new DieukienDetail();
						dkDetail.setDiengiai(diengiai);
						dkDetail.setLoaidieukien(rs.getString("loai"));
						
						if(Double.parseDouble(tongluong) > 0)
						{
							dkDetail.setSotong(tongluong);
							dkDetail.setHinhthuc("1");
						}
						else
						{
							dkDetail.setSotong(tongtien);
							dkDetail.setHinhthuc("2");
						}
						
						//Khoi tao SP
						query = "select b.PK_SEQ as spId, b.MA as spMa, b.TEN as spTen, ISNULL(a.soluong, 0) as soluong  " +
								"from DIEUKIENKM_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where a.DIEUKIENKHUYENMAI_FK = '" + dkkmId + "'";
						System.out.println("Khoi tao sp TKM: "+query);
						ResultSet spDetail = db.get(query);
						
						List<ISanpham> sp_dkkmList = new ArrayList<ISanpham>();
						while(spDetail.next())
						{
							ISanpham sp_dkkm = new Sanpham();
							sp_dkkm.setId(spDetail.getString("spId"));
							sp_dkkm.setMasanpham(spDetail.getString("spMa"));
							sp_dkkm.setTensanpham(spDetail.getString("spTen"));
							if(spDetail.getDouble("soluong") > 0)
								sp_dkkm.setSoluong(spDetail.getString("soluong"));
							
							sp_dkkmList.add(sp_dkkm);
						}
						spDetail.close();
						
						dkDetail.setSpList(sp_dkkmList);
						
						dkkm.setDieukineDetail(dkDetail);
						dkkmList.add(dkkm);
						
					}
					if(rs!=null){
						rs.close();
					}
				} 
				catch (Exception e) {e.printStackTrace();}
			}
			Dieukienkm dkkm = (Dieukienkm)dkkmList.get(0);	
			IDieukienDetail dkkmDetai = dkkm.getDieukienDetail();
			List<ISanpham> spList = dkkmDetai.getSpList();
			ResultSet nhomspRs;
			query = "select pk_seq as nspId, diengiai as nspTen from nhomsanpham where nguoitao is not null and type = '1' and trangthai='1'";
			nhomspRs = db.getScrol(query);	
			String table=
			"<div id='dieukienkhuyenmai"+i+"' style='padding:0px 5px; background:#fff;'>"+
        	"<h4 align=\"left\">Tạo mới điều kiện khuyến mại</h4>"+
			"<table cellpadding=\"4px\" cellspacing=\"2px\" width=\"100%\" align=\"center\">"+
            "	<tr>"+
            "    	<td width=\"40%\" valign=\"top\" align=\"left\">Diễn giải</td>"+
            "        <td valign=\"top\" align=\"left\">"+
            "            <input type=\"text\" name=\"dieukienkhuyenmai"+i+".diengiai\" id=\"dieukienkhuyenmai"+i+".diengiai\" value=\""+dkkmDetai.getDiengiai()+"\" />"+
            "        </td>"+
            "    </tr>"+
            "    <tr>"+
            "    	<td valign=\"top\" align=\"left\">Loại điều kiện</td>"+
            "        <td valign=\"top\" align=\"left\">"+
            "        	<select name=\"dieukienkhuyenmai"+i+".loaidieukien\" id=\"dieukienkhuyenmai"+i+".loaidieukien\">";
                    		 if(dkkmDetai.getLoaidieukien().equals("1")){
              table+="          		<option value=\"2\">Bất kỳ trong</option>"+
                        		"<option value=\"1\" selected=\"selected\">Bắt buộc nhập số lượng</option>";
                    		} else { 
               table+=     		"<option value=\"2\" selected=\"selected\">Bất kỳ trong</option>"+
                        		"<option value=\"1\">Bắt buộc nhập số lượng</option>";
                    		} 
               table+=     	"</select>"+
                    "</td>"+
                "</tr>"+
                "<tr>"+
                	"<td valign=\"top\" align=\"left\">Hình thức</td>"+
                    "<td valign=\"top\" align=\"left\">"+
                    "	<select name = \"dieukienkhuyenmai"+i+" .hinhthuc\" id = \"dieukienkhuyenmai"+i+".hinhthuc\" >";
                    	 if(dkkmDetai.getHinhthuc().equals("2")){ 
                    	table+=	"<option value=\"1\">Nhập tổng lượng</option>"+
                    		"<option value=\"2\" selected=\"selected\">Nhập tổng tiền</option>";
                    	} else { 
                    		table+="<option value=\"1\" selected=\"selected\">Nhập tổng lượng</option>"+
                    		"<option value=\"2\">Nhập tổng tiền</option>";
                    	} 
                    table+="</select>"+
                    "</td>"+
                "</tr>"+
                "<tr>"+
                	"<td valign=\"top\" align=\"left\">Tổng lượng / Tổng tiền</td>"+
                    "<td valign=\"top\" align=\"left\">"+
                    "	<input type=\"text\" name=\"dieukienkhuyenmai"+i+".sotong\" id=\"dieukienkhuyenmai"+i+".sotong\" value=\""+dkkmDetai.getSotong()+"\" style=\"text-align: right;\"/>"+
                    "</td>"+
                "</tr>"+
                "<tr>"+
                	"<td valign=\"top\" align=\"left\">Nhóm sản phẩm</td>"+
                    "<td valign=\"top\" align=\"left\">"+		                                    	
                    "	<select name=\"dieukienkhuyenmai"+i+".nhomsanpham\" id=\"dieukienkhuyenmai"+i+".nhomsanpham\" onChange = \"ajaxOption(this.id, this.value, "+ i+")\"> "+
                    "		<option value=\"\"> </option>";
        		 if(nhomspRs != null)
        		{ 
        			try
					{
						nhomspRs.beforeFirst();
						while(nhomspRs.next())
						{ 
							if(dkkmDetai.getNhomspId().equals(nhomspRs.getString("nspId")))
							{
								table+= 				"<option value=\""+nhomspRs.getString("nspId") +"\">"+nhomspRs.getString("nspTen")+"</option>";
							} else 
							{  
								table+=				"<option value=\""+ nhomspRs.getString("nspId") +"\">"+ nhomspRs.getString("nspTen") +"</option>";
							}
						}
					}catch (SQLException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
                    table+="</select>"+
                    "</td>"+
                "</tr>"+
                "<tr>"+
                	"<td valign=\"top\" align=\"left\" colspan=\"2\">";
                	 if(dkkm.isTheothung() ) { 
                	table+="<input type=\"checkbox\" name=\"dieukienkhuyenmai"+i+".tinhtheothung\" id=\"dieukienkhuyenmai"+ i +".tinhtheothung\" value='1' checked=\"checked\" > <span style=\"font-style: italic;\">Số lượng tính theo thùng</span>"; 
                	} else {
                	table+="	<input type=\"checkbox\" name=\"dieukienkhuyenmai"+i+".tinhtheothung\" id=\"dieukienkhuyenmai"+i+".tinhtheothung\" value='1' > <span style=\"font-style: italic;\">Số lượng tính theo thùng</span> ";
                	} 
              table+=
            	"	</td>"+
                "</tr>"+
                "<tr>"+
                "	<td colspan=\"2\">"+
                "		<table align=\"left\" cellpadding=\"2px\" cellspacing=\"2px\">"+
                "    		<tr>"+
                "    			<th width=\"100px\" align=\"center\">Mã sản phẩm</th>"+
                "    			<th width=\"250px\" align=\"left\">Tên sản phẩm</th> "+
                "   			<th width=\"60px\" align=\"left\">Số lượng</th> "+
                "    		</tr> "+
                "    	</table> "+
                "   	<div id=\"dieukienkhuyenmai"+i+".tbsanpham\" style=\"width: 100%; max-height: 150px; overflow: auto\">"+
                "    	<table align=\"left\" cellpadding=\"2px\" cellspacing=\"2px\">";
                    	int count = 0;
                    	while(count < spList.size())
                    	{
                    		ISanpham sp = spList.get(count);
          table+="   		<tr> "+
                 "   			<td width=\"100px\" align=\"center\"> "+
                 "   				<input type=\"text\" value="+sp.getMasanpham()+" style=\"width: 100px\" name=\"dieukienkhuyenmai"+i+".masanpham\" "+ 
                 "   						onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE=\"off\">"+
                 "   			</td>"+
                 "   			<td width=\"250px\" align=\"left\">"+
                 "   				<input type=\"text\" value=\""+sp.getTensanpham()+"\" name=\"dieukienkhuyenmai"+ i+".tensanpham\" style=\"width: 250px\" readonly=\"readonly\">"+
                 "   			</td>"+
                 "   			<td width=\"60px\" align=\"center\">"+
                 "   				<input type=\"text\" value=\""+sp.getSoluong()+"\" name=\"dieukienkhuyenmai"+i+".soluong\" style=\"width: 60px; text-align: right;\"> "+
                 "  			</td>"+
                 "   		</tr>";
                    	 count++; }
                    	 for(int pos=count; pos < 50; pos++){ 
             table+=
            	 "   		<tr>"+
                 "   			<td width=\"100px\" align=\"center\"> "+
                 "   				<input type=\"text\" value=\"\" style=\"width: 100px\" name=\"dieukienkhuyenmai"+i+".masanpham\""+ 
                 "   						onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE=\"off\"  >"+
                 "   			</td>"+
                 "   			<td width=\"250px\" align=\"left\">"+
                 " 				<input type=\"text\" value=\"\" name=\"dieukienkhuyenmai"+i+".tensanpham\" style=\"width: 250px\" readonly=\"readonly\">"+
                 "   			</td> "+
                 "   			<td width=\"60px\" align=\"center\"> "+
                 "  				<input type=\"text\" value=\"\" name=\"dieukienkhuyenmai"+i+".soluong\" style=\"width: 60px; text-align: right;\">"+
                 "   			</td>"+
                 "   		</tr>";
                    	}
               table+=
            	 "		</table>"+
                 "		</div> "+
                 "	</td> "+
                 "</tr>"+
                 "<tr>"+
                 "	<td valign=\"top\" align=\"left\" colspan=\"2\">"+
				 "		<a class=\"button\" href=\"javascript:submitform();\"> "+
				 "		<img style=\"top: -4px;\" src=\"../images/button.png\" alt=\"\"> Nhập lại  </a> "+
				 "	 	<a class=\"button\" href=\"javascript:submitform2("+i+");\"> "+
				 "		<img style=\"top: -4px;\" src=\"../images/button.png\" alt=\"\"> Lưu điều kiện  </a> "+
				 "	</td>"+
                 " </tr>"+
               "</table> "+
		"</div>";
               db.shutDown();
		    	out.write(table);
		}else if(type.equals("AjaxTraKM"))
		{
			dbutils db = new dbutils();
			String query = 
			"		 select a.pk_seq as trakmId, a.diengiai, cast(isnull(a.tongluong, 0) as float) as tongluong, "+ 
			"	 cast(isnull(a.tongtien, 0) as float) as tongtien, a.hinhthuc, cast(isnull(a.chietkhau, 0) as float) as chietkhau, "+ 
			"		 isnull(b.pheptoan, '1') as pheptoan, a.loai, isnull(b.thutu,0)as thutu "+
			"		  from trakhuyenmai a left  join ctkm_trakm b on a.pk_seq = b.trakhuyenmai_fk and b.ctkhuyenmai_fk = '"+ctkmId+"' "+
			"		 where   a.pk_seq='"+trakmId+"' order by b.thutu asc ";

			System.out.println("Khoi tao tra khuyen mai: " + query);
			ResultSet rs = db.get(query);
			List<ITrakm> listTrakm = new ArrayList<ITrakm>();
			if(rs != null)
			{
				try 
				{
					ITrakm trakm = null;
					while(rs.next())
					{
						String diengiai = rs.getString("diengiai");
						String tongluong = rs.getString("tongluong");
						String tongtien = rs.getString("tongtien");
						String chietkhau = rs.getString("chietkhau");
						String pheptoan = rs.getString("pheptoan");
						String thutudk = rs.getString("thutu");
						trakm = new Trakm(trakmId, diengiai, tongluong, tongtien, chietkhau, pheptoan, thutudk);
						
						
						ITrakmDetail tkmDetail;
						tkmDetail = new TrakmDetail();
						tkmDetail.setDiengiai(diengiai);
						tkmDetail.setLoaitra(rs.getString("loai"));
						tkmDetail.setHinhthuc(rs.getString("hinhthuc"));
						
						if(Double.parseDouble(tongluong) > 0)
						{
							tkmDetail.setSotong(tongluong);
						}
						else
						{
							tkmDetail.setSotong(tongtien);
						}
						
						//Khoi tao SP
						query = "select b.PK_SEQ as spId, b.MA as spMa, b.TEN as spTen, ISNULL(a.soluong, 0) as soluong  " +
								"from TRAKHUYENMAI_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where a.TRAKHUYENMAI_FK = '" + trakmId + "'";
						System.out.println("Khoi tao sp TKM: "+query);
						ResultSet spDetail = db.get(query);
						
						List<ISanpham> sp_dkkmList = new ArrayList<ISanpham>();
						while(spDetail.next())
						{
							ISanpham sp_dkkm = new Sanpham();
							sp_dkkm.setId(spDetail.getString("spId"));
							sp_dkkm.setMasanpham(spDetail.getString("spMa"));
							sp_dkkm.setTensanpham(spDetail.getString("spTen"));
							if(spDetail.getDouble("soluong") > 0)
								sp_dkkm.setSoluong(spDetail.getString("soluong"));
							
							sp_dkkmList.add(sp_dkkm);
						}
						spDetail.close();
						
						tkmDetail.setSpList(sp_dkkmList);
						
						trakm.setTraDetail(tkmDetail);
						listTrakm.add(trakm);
						
					}
					if(rs!=null){
						rs.close();
					}
				} 
				catch (Exception e) 
				{
					System.out.println("____Exception: " + e.getMessage());
				}
			}
			
			Trakm tkm = (Trakm)listTrakm.get(0);	
			ITrakmDetail tkmDetai = tkm.getTraDetail();
			List<ISanpham> spTraList = tkmDetai.getSpList();
			ResultSet nhomspRs;
			query = "select pk_seq as nspId, diengiai as nspTen from nhomsanpham where nguoitao is not null and type = '1' and trangthai='1'";
			nhomspRs = db.getScrol(query);	
			
			int pos=i;
			String table=
			"<div id='trakhuyenmai"+ pos+"' style='padding:0px 5px; background:#fff;'>"+
        	"<h4 align=\"left\">Tạo mới trả khuyến mại</h4>"+
			"<table cellpadding=\"4px\" cellspacing=\"2px\" width=\"100%\" align=\"center\">"+
            "	<tr>"+
            "    	<td width=\"40%\" valign=\"top\" align=\"left\">Diễn giải</td>"+
            "        <td valign=\"top\" align=\"left\">"+
            "            <input type=\"text\" name=\"trakhuyenmai"+ pos+".diengiai\" id=\"trakhuyenmai"+ pos+".diengiai\" value=\""+ tkmDetai.getDiengiai()+"\" />"+
            "        </td>"+
            "    </tr>"+
            "    <tr>"+
            "    	<td valign=\"top\" align=\"left\">Loại trả</td>"+
            "        <td valign=\"top\" align=\"left\">"+
            "        	<select name=\"trakhuyenmai"+ pos+".loaitra\" id = \"trakhuyenmai"+ pos+".loaitra\">";
                    	 if(tkmDetai.getLoaitra().equals("1")){
          table+=
            "        			<option value=\"1\" selected=\"selected\">Trả tiền</option> "+
            "         			<option value=\"3\">Trả sản phẩm</option>  "+
            "        			<option value=\"2\">Trả chiết khấu</option> ";
                    		} else { if (tkmDetai.getLoaitra().equals("2")){
            table+=
            "        			<option value=\"2\" selected=\"selected\">Trả chiết khấu</option> "+
            "        			<option value=\"3\">Trả sản phẩm</option> "+
            "        			<option value=\"1\">Trả tiền</option> ";
                    		}else {
             table+=      			
             "      			<option value=\"3\" selected=\"selected\">Trả sản phẩm</option> "+
             "       			<option value=\"1\">Trả tiền</option> "+
             "      			<option value=\"2\">Trả chiết khấu</option> ";
                    		 } }
             table+=         	 
             "       	</select> "+
             "       </td>  "+
             "   </tr> "+
             "   <tr> "+
             "   	<td valign=\"top\" align=\"left\">Hình thức</td> "+
             "       <td valign=\"top\" align=\"left\"> "+
             "       	<select name = \"trakhuyenmai"+ pos+".hinhthuc\" id = \"trakhuyenmai"+ pos+".hinhthuc\" > ";
                    	 if(tkmDetai.getHinhthuc().equals("1")){
             table+=
             "		 <option value=\"2\">Bất kỳ trong</option> "+
             "       		<option value=\"1\" selected=\"selected\" >Bắt buộc nhập số lượng</option> ";
                    	} else { 
             table+=      		
             "		 <option value=\"2\" selected=\"selected\">Bất kỳ trong</option> "+
             "       		<option value=\"1\">Bắt buộc nhập số lượng</option> ";
                    	}
             table+=
             "       	</select> "+
             "       </td> "+
             "   </tr> "+
             "   <tr> "+
             "   	<td valign=\"top\" align=\"left\">Tổng lượng / Tổng tiền</td> "+
             "       <td valign=\"top\" align=\"left\"> "+
             "      	<input type=\"text\" name=\"trakhuyenmai"+ pos+".sotong\" id=\"trakhuyenmai"+ pos+".sotong\" "+
             "       			value=\""+ tkmDetai.getSotong()+"\" style=\"text-align: right;\"/> "+
             "       </td> "+
             "   </tr> "+
             "   <tr> "+
             "   	<td valign=\"top\" align=\"left\">Nhóm sản phẩm</td>"+
             "        <td valign=\"top\" align=\"left\"> "+		                                    	
             "       	<select name=\"trakhuyenmai"+ pos+".nhomsanpham\" id=\"trakhuyenmai"+ pos+".nhomsanpham\" onChange = \"ajaxOption2(this.id, this.value, "+ pos+")\"> "+
             "       		<option value=\"\"> </option> ";
		 if(nhomspRs != null)
		{ 
			try
			{
				nhomspRs.beforeFirst();
				while(nhomspRs.next()){ if(tkmDetai.getNhomspId().equals(nhomspRs.getString("nspId"))){
		               table+=                 		
		               "     				<option value=\""+ nhomspRs.getString("nspId")+"\">"+ nhomspRs.getString("nspTen")+"</option> ";
		                    		 } else { 
		                table+=     
		                "    				<option value=\""+ nhomspRs.getString("nspId")+"\">"+ nhomspRs.getString("nspTen")+"</option> "; 
		                    		 } } 
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
                table+=  
                "    	</select> "+
                "    </td> "+
                "</tr> "+
                "  <tr> "+
                "	<td valign=\"top\" align=\"left\" colspan=\"2\"> ";                
                	 if(tkm.isTheothung() ) {
               table+=  
               " 		<input type=\"checkbox\" name=\"trakhuyenmai"+ pos+".tinhtheothung\" id=\"trakhuyenmai"+ pos+".tinhtheothung\"  value='1' checked=\"checked\" > <span style=\"font-style: italic;\">Số lượng tính theo thùng</span> ";
                	} else {
               table+=   		
                	"	<input type=\"checkbox\" name=\"trakhuyenmai"+ pos+".tinhtheothung\" id=\"trakhuyenmai"+ pos+".tinhtheothung\"  value='1' > <span style=\"font-style: italic;\">Số lượng tính theo thùng</span> ";
                	}
               table+=
                "	</td> "+
                "</tr> "+
                "  <tr> "+
               " 	<td colspan=\"2\">  "+
               "		<table align=\"left\" cellpadding=\"2px\" cellspacing=\"2px\"> "+
               "     		<tr> "+
               "     			<th width=\"100px\" align=\"center\">Mã sản phẩm</th> "+
               "     			<th width=\"250px\" align=\"left\">Tên sản phẩm</th> "+
               "     			<th width=\"60px\" align=\"left\">Số lượng</th> "+
               "     		</tr> "+
               "     	</table> "+
               "    	<div id=\"trakhuyenmai"+ pos+".tbsanpham\" style=\"width: 100%; max-height: 150px; overflow: auto\"> "+
               "     	<table align=\"left\" cellpadding=\"2px\" cellspacing=\"2px\"> ";
                    	int count = 0;
                    	while(count < spTraList.size())
                    	{
                    		ISanpham sp = spTraList.get(count);
                    		table+= 		
               "     		<tr> "+
               "     			<td width=\"100px\" align=\"center\"> "+
               "     				<input type=\"text\" value=\""+ sp.getMasanpham()+"\" style=\"width: 100px\" name=\"trakhuyenmai"+ pos+".masanpham\" "+ 
               "     						onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE=\"off\"> "+
               "     			</td> "+
               "     			<td width=\"250px\" align=\"left\"> "+
               "      				<input type=\"text\" value=\""+ sp.getTensanpham()+"\" name=\"trakhuyenmai"+ pos+".tensanpham\" style=\"width: 250px\" readonly=\"readonly\"> "+
               "     			</td> "+
               "     			<td width=\"60px\" align=\"center\"> "+
               "     				<input type=\"text\" value=\""+ sp.getSoluong()+"\" name=\"trakhuyenmai"+ pos+".soluong\" style=\"width: 60px; text-align: right;\"> "+
               "     			</td>"+
               "     		</tr> ";
                    	 count++; }
                    	 for(int sopt=count; sopt < 50; sopt++){
                 table+= 
               "     		<tr> "+  
               "     			<td width=\"100px\" align=\"center\"> "+
               "     				<input type=\"text\" value=\"\" style=\"width: 100px\" name=\"trakhuyenmai"+ pos+".masanpham\"= "+ 
               "     						onkeyup=\"ajax_showOptions(this,'sanpham',event)\" AUTOCOMPLETE=\"off\"> "+
               "     			</td>  "+
               "     			<td width=\"250px\" align=\"left\"> "+
               "     				<input type=\"text\" value=\"\" name=\"trakhuyenmai"+ pos+".tensanpham\" style=\"width: 250px\" readonly=\"readonly\"> " + 
               "     			</td> "+
               "     			<td width=\"60px\" align=\"center\"> "+
               "     				<input type=\"text\" value=\"\" name=\"trakhuyenmai"+ pos+".soluong\" style=\"width: 60px; text-align: right;\"> "+
               "     			</td>  "+
               "     		</tr> ";
                    	}
               table+= 
               " 		</table> "+
               " 		</div> "+
               "	</td> "+
               " </tr> "+
               " <tr> "+
               " 	<td valign=\"top\" align=\"left\" colspan=\"2\"> "+
               "			<a class=\"button\" href=\"javascript:submitform();\"> "+
			   "			<img style=\"top: -4px;\" src=\"../images/button.png\" alt=\"\"> Nhập lại  </a> "+
			   "	 		<a class=\"button\" href=\"javascript:submitform3("+ pos+");\"> "+
			   "			<img style=\"top: -4px;\" src=\"../images/button.png\" alt=\"\"> Lưu trả khuyến mại  </a> "+
               " 	</td> "+
               "  </tr> "+
              " </table> "+
		    " </div> ";
               db.shutDown();
		    	out.write(table);
		}
	}

	interface INhaPhanPhoi
	{
		String getNppId();

		void setNppId(String nppId);

		String getNppMa();

		void setNppMa(String nppMa);

		String getNppTen();

		void setNppTen(String npTen);

	}

	class NhaPhanPhoi implements INhaPhanPhoi
	{

		String nppId, nppMa, nppTen;

		public String getNppId()
		{
			return nppId;
		}

		public void setNppId(String nppId)
		{
			this.nppId = nppId;
		}

		public String getNppMa()
		{
			return nppMa;
		}

		public void setNppMa(String nppMa)
		{
			this.nppMa = nppMa;
		}

		public String getNppTen()
		{
			return nppTen;
		}

		public void setNppTen(String nppTen)
		{
			this.nppTen = nppTen;
		}

	}

}

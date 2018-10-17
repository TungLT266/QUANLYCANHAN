<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.kichbansanxuatgiay.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.kichbansanxuatgiay.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@page import="java.sql.SQLException"%>
<%
String userId = (String) session.getAttribute("userId");
String userTen = (String) session.getAttribute("userTen");
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if (!util.check(userId, userTen, sum)) {
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else {
	IErpKichBanSanXuatGiay obj = (IErpKichBanSanXuatGiay) session.getAttribute("kbsxBean");
	ResultSet spRs = obj.getSpRs();
	ResultSet rsNhaMay = obj.getRsNhaMay();
	ResultSet rsVattu = obj.getRsVattu();
	ResultSet rsCongdoan = obj.getRsCongdoan();
	List<IKichBan_CongDoanSx> lstCdsx = obj.getCongdoanSxList();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<%-- <link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script>
    $(document).ready(function()
    {
     <% for( int i = 0; i < 100; i++ ) { %>
     $(".chitiet<%= i %>").colorbox({width:"75%", inline:true, href:"#chitiet<%= i %>"});
        //Example of preserving a JavaScript event for inline calls.
        $("#click").click(function(){ 
            $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Viethung - Project.");
            return false;
        });
        <% } %>
    });
</script> --%>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>
<script>
	//perform JavaScript after the document is scriptable.
	$(function() {
		// setup ul.tabs to work as tabs for each div directly under div.panes
		$("ul.tabs").tabs("div.panes > div");
	});
</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function download(path){
		if(path.trim() == ""){
			return false;
		} else {
			document.forms["khtt"].pathdownload.value = path;
			document.forms["khtt"].action.value = "download";
		  	document.forms["khtt"].submit();
		}
	}
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="khtt" method="post" action="../../ErpKichBanSanXuatGiayUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="0">
		<input type="hidden" name="id" value="<%=obj.getId() %>">
		<input type="hidden" name="uploadi" value="">
		<input type="hidden" name="uploadj" value="">
		<input type="hidden" name="pathdownload" value="">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Sản xuất &gt; Kịch bản sản xuất &gt; Hiển thị</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%></TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left">
											<A href="../../ErpKichBanSanXuatGiaySvl?userId=<%=userId%>">
												<img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset">
											</A>
										</TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"></TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông tin kịch bản sản xuất </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD class="plainlabel" valign="middle" width="125px">Sản phẩm</TD>
											<TD class="plainlabel">
												<select style="width: 300px;" disabled="disabled" class="select2">
													<option value=""></option>
													<%if(spRs != null){
														while (spRs.next()){
															if (obj.getSpSelected().equals(spRs.getString("pk_seq"))){ %>
																<option value="<%=spRs.getString("pk_seq")%>" selected><%=spRs.getString("ten")%></option>
															<%} else { %>
																<option value="<%=spRs.getString("pk_seq")%>"><%=spRs.getString("ten")%></option>
															<%}
														}
														spRs.close();
													} %>
												</select>
											</TD>
											
											<TD class="plainlabel" valign="middle">Hồ sơ lô SX</TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" name="hosolosx" value="<%=obj.getHosolosx() %>" readonly="readonly">
											</TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" valign="middle">Diễn giải</TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" readonly value="<%=obj.getDiengiai() %>">
											</TD>
											
											<TD class="plainlabel" valign="middle">Ngày ban hành HSL</TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" readonly="readonly" name="ngaybanhanhhsl" value="<%=obj.getNgaybanhanhhsl() %>">
											</TD>
										</TR>
										
										<TR style="display: none;">
											<TD class="plainlabel" valign="middle">Phân xưởng </TD>
											<TD class="plainlabel" valign="middle">
												<select style="width: 350px">
													<option disabled value=""></option>
													<%if(rsNhaMay != null){
														while (rsNhaMay.next()){
															if (rsNhaMay.getString("pk_seq").equals(obj.getNhaMayId())){%>
																<option value="<%=rsNhaMay.getString("pk_seq")%>" selected="selected"><%=rsNhaMay.getString("Ten")%></option>
															<%} else { %>
																<option value="<%=rsNhaMay.getString("pk_seq")%>" disabled><%=rsNhaMay.getString("Ten")%></option>
															<%}
														}
														rsNhaMay.close();
													} %>
												</select>
											</TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" valign="middle">Số lượng chuẩn</TD>
											<TD class="plainlabel" valign="middle">
												<input readonly type="text" value="<%=obj.getSoluongchuan()%>" style="text-align: right;">
											</TD>
											
											<TD class="plainlabel" valign="middle">Lần ban hành HSL</TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" name="lanbanhanhhsl" value="<%=obj.getLanbanhanhhsl() %>" style="text-align: right;" readonly="readonly">
											</TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" valign="middle" width="120px" >Số ngày sản xuất</TD>
											<TD class="plainlabel" valign="middle" colspan="3">
												<input type="text" value="<%=obj.getSongaysanxuat()%>" style="text-align: right;" readonly>
											</TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" valign="middle" width="120px" >Hoạt động</TD>
											<TD class="plainlabel" valign="middle" colspan="3">
												<%if (obj.getTrangThai().equals("1")) { %>
													<input type="checkbox" checked="checked" disabled value="<%=obj.getTrangThai()%>" style="text-align: right;">
												<%} else { %>
													<input type="checkbox" disabled value="<%=obj.getTrangThai()%>" style="text-align: right;">
												<%} %>
											</TD>
										</TR>
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
						
						<TR>
							<TD colspan="6">
								<ul class="tabs">
									<li><a href="#" class="legendtitle">Công đoạn sản xuất</a></li>
								</ul>
								<div>
									<table cellpadding="0px" cellspacing="1px" width="100%">
										<tr class="tbheader">
											<th width="25%">Công đoạn sản xuất</th>
											<th width="7%">Thời gian</th>
											<th width="7%">Thứ tự</th>
											<th width="15%">BOM</th>
											<th width="13%">Số lượng</th>
											<th width="13%">Nhập kho công đoạn</th>
											<th width="5%">Chi tiết</th>
										</tr>
										
										<%int count = 0;
										String chon;
										for (int i=0; i<lstCdsx.size(); i++) {
											IKichBan_CongDoanSx congdoan = lstCdsx.get(i); %>
											<tr>
												<td>
													<select name ="congdoanId" style="width: 410px" class="select2" id="congdoanId_<%=i %>" onchange="submitform();" disabled="disabled">
														<option value=""></option>
													  	<%if(rsCongdoan!=null){
													  		rsCongdoan.beforeFirst();
													  		while(rsCongdoan.next()){
													  			if(congdoan.getId().equals(rsCongdoan.getString("PK_SEQ"))){ %>
													  				<option value="<%=rsCongdoan.getString("PK_SEQ")%>" selected="selected"><%=rsCongdoan.getString("DienGiai") %></option>
													  			<%} else { %>
																	<option value="<%=rsCongdoan.getString("PK_SEQ")%>"><%=rsCongdoan.getString("DienGiai") %></option>
													  			<%}
												  			}
												  		} %>
										  			</select>
												</td>
												<td><input type="text" value="<%=congdoan.getThoiGian()%>" name="thoigian" style="width: 100%;text-align:right;" readonly="readonly"></td>
												<td><input type="text" value="<%=congdoan.getThuTu()%>" name="thutu" style="width:100%;text-align:right;" readonly="readonly"></td> 
												<td>
													<select name ="vattuId" id="vattuId_<%=i %>" style="width: 244px" class="select2" disabled="disabled">
														<option></option>	
													  	<%if(rsVattu!=null){
													  		rsVattu.beforeFirst();
													  		while(rsVattu.next()){
													  			if(congdoan.getVattuId().equals(rsVattu.getString("PK_SEQ"))){ %>
													 	 			<option value="<%=rsVattu.getString("PK_SEQ")%>" selected="selected"><%=rsVattu.getString("DienGiai") %></option>
													  			<%} else { %>
													  				<option value="<%=rsVattu.getString("PK_SEQ")%>"><%=rsVattu.getString("DienGiai") %></option>
													  			<%}
												  			}
												  		} %>
											  		</select>
										  		</td>
										  		<td><input type="text" value="<%= congdoan.getSoluong() %>" name="soluong" style="width:100%;text-align:right;" readonly></td>
												<td align="center">
													<input type="checkbox" class="nhapkho" name="nhapkho_<%=i %>" <%= congdoan.getNhapkho().equals("1") ? " checked " : "" %> value="1" disabled="disabled">
												</td>
												<td align="center">
													<%if(congdoan.getId().length() > 0){ %>
							           	 				<a href="" id="sanpham_<%=i%>" rel="chitiet<%=i%>"> 
							           	 					&nbsp;<img alt="Chọn số lô" src="../images/vitriluu.png">
							           	 				</a>
							           	 				
							           	 				<DIV id="chitiet<%=i%>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 90%; max-height:320px; overflow:auto; padding: 4px;">
							           	 					<table width="100%" align="center">
							           	 						<%List<KichbanSX_CongdoanSX_ChiTiet> cdsxChitietList = congdoan.getCdsxChitietList();%>
										                        <%KichbanSX_CongdoanSX_ChiTiet cdsxChitiet;%>
							           	 						<tr>
										                        	<th width="12%" style="font-size: 11px">Khu vực SX</th>
										                            <th width="12%" style="font-size: 11px">Giai đoạn SX</th>
										                            <th width="10%" style="font-size: 11px">TSCĐ/CPTT</th>
										                            <th width="10%" style="font-size: 11px">Thiết bị</th>
										                            <th width="13%" style="font-size: 11px">Thông số chung</th>
										                            <th width="13%" style="font-size: 11px">Thông số</th>
										                            <th width="12%" style="font-size: 11px">Yêu cầu</th>
										                            <th width="8%" style="font-size: 11px">Thông số từ</th>
										                            <th width="8%" style="font-size: 11px">Thông số đến</th>
										                            <th width="5%" style="font-size: 11px">DVT</th>
										                            <th width="3%" style="font-size: 11px">Đạt</th>
										                            <th width="7%" style="font-size: 11px">Chọn</th>
										                            <th width="7%" style="font-size: 11px">Upload chi tiết</th>
										                        </tr>
										                        
										                        <%for(int j = 0; j < cdsxChitietList.size(); j++){ %>
										                        	<%cdsxChitiet = cdsxChitietList.get(j); %>
										                        	
										                        	<tr>
										                        		<td><input type="text" name="khuvucsx_<%=i %>" style="width: 100%" value="<%=cdsxChitiet.getKhuvucsx() %>"></td>
										                        		<td><input type="text" name="giaidoansx_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getGiaidoansx() %>"></td>
										                        		<td><input type="text" name="tscd_cptt_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getTscdCptt() %>"></td>
										                        		<td><input type="text" name="thietbi_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getThietbi() %>"></td>
										                        		<td><input type="text" name="thongsochung_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getThongsochung() %>"></td>
										                        		<td><input type="text" name="thongso_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getThongso() %>"></td>
										                        		<td><input type="text" name="yeucau_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getYeucau() %>"></td>
										                        		<td><input type="text" name="thongsotu_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getThongsotu() %>"></td>
										                        		<td><input type="text" name="thongsoden_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getThongsoden() %>"></td>
										                        		<td><input type="text" name="dvt_<%=i %>" readonly style="width: 100%" value="<%=cdsxChitiet.getDvt() %>"></td>
										                        		<td style="text-align: center;">
										                        			<%-- <input type="hidden" name="id_cdsx_chitiet_<%=i %>" value="<%=cdsxChitiet.getId() %>"> --%>
										                        			<input type="hidden" name="dat_<%=i %>" value="<%=cdsxChitiet.getDat() %>">
										                        			<%if(cdsxChitiet.getDat().equals("1")) {%>
																				<input disabled type="checkbox" checked>
																			<%} else {%>
																				<input disabled type="checkbox">
																			<%} %>
										                        		</td>
										                        		<td style="text-align: center;">
										                        			<%if(cdsxChitiet.getChon().equals("1")) {%>
																				<input name="chon_<%=i %>_<%=j %>" id="chon_<%=i %>_<%=j %>" value="1" type="checkbox" checked disabled="disabled">
																			<%} else {%>
																				<input name="chon_<%=i %>_<%=j %>" id="chon_<%=i %>_<%=j %>" value="1" type="checkbox" disabled="disabled">
																			<%} %>
										                        		</td>
										                        		<td align="center">
										                        			<%-- <div <%=cdsxChitiet.getChon().equals("1") ? "" : "style=\"display: none;\"" %> id="upload_<%=i %>_<%=j %>">
											                        			<input type="hidden" name="upload_path_<%=i %>" id="upload_path_<%=i %>_<%=j %>" value="<%=cdsxChitiet.getUploadPath() %>">
													                        	<input type="text" style="width:130px" readonly name="upload_name_<%=i %>" id="upload_name_<%=i %>_<%=j %>" value="<%=cdsxChitiet.getUploadName() %>">
													                        	<input type="file" name="file_upload_<%=i %>_<%=j %>" title="" style="width: 70px;" value='' disabled="disabled">
													                        	
												                        		<div style="float: right;" <%=cdsxChitiet.getUploadPath().trim().length() > 0 ? "" : "style=\"display: none;\"" %>>
													                        		<a class="button2" id="a_<%=i %>_<%=j %>" href="javascript:download('<%=cdsxChitiet.getUploadPath().replace("\\", "\\\\") %>');">
													                        			<img style="top: -4px;" src="../images/download.png" alt="Download" title="Download">
													                        		</a>
												                        			<img style="top: -4px;" src="../images/Delete20.png" alt="Delete" title="Delete">
												                        		</div>
													                        </div> --%>
													                        
													                        <%if(cdsxChitiet.getUploadPath().trim().length() > 0){ %>
											                        			<a class="button2" id="a_<%=i %>_<%=j %>" href="javascript:download('<%=cdsxChitiet.getUploadPath().replace("\\", "\\\\") %>');">
												                        			<img style="top: -4px;" src="../images/download.png" alt="Download" title="Download">
												                        		</a>
											                        		<%} %>
										                        		</td>
										                        	</tr>
										                        <%} %>
							           	 					</table>
							           	 					
							           	 					<div align="right">
										                     	<label style="color: red" ></label>
										                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										                     	<a href="javascript:dropdowncontent.hidediv('chitiet<%=i%>')" > Đóng lại </a>
									                     	</div>
							           	 				</div>
							           	 				
							           	 				<script type="text/javascript">
										            		dropdowncontent.init('sanpham_<%=i %>', "left-top", 300, "click");
										            	</script>
													<%} %>
												</td>
											</tr>
										<%} %>
									</TABLE>
								</div>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</FORM>
</BODY>
</HTML>
<%
	if (spRs != null)
		spRs.close();
	if (rsNhaMay != null)
		rsNhaMay.close();
	if (rsCongdoan != null)
		rsCongdoan.close();
	if (lstCdsx != null)
		lstCdsx.clear();
	if (rsVattu != null)
		rsVattu.close();
	obj.DbClose();
	session.setAttribute("kbsxBean", null) ; 
}
%>

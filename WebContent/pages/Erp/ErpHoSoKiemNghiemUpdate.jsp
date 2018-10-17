
<%@page
	import="geso.traphaco.erp.beans.hosokiemnghiemthietbi.IHoSoKiemNghiemThietBi"%>
<%@page
	import="geso.traphaco.erp.beans.hosokiemnghiemchitiet.IHoSoKiemNghiemChiTiet"%>
<%@page import="geso.traphaco.erp.beans.hosokiemnghiem.IHoSoKiemNghiem"%>
<%@page import="java.sql.ResultSet"%>

<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	IHoSoKiemNghiem obj=(IHoSoKiemNghiem)session.getAttribute("tcknBean");
	String userId=(String)session.getAttribute("userId");
	String userTen=(String)session.getAttribute("userName");
	ResultSet RsPhongBan=obj.getRsPhongBan();
	ResultSet RsNhanVien=obj.getRsNhanVien();
	ResultSet RsYCLM=obj.getRsYCLayMau();
	ResultSet RsTCKM=obj.getRsTCKiemNghiem();
	ResultSet RsSP=obj.getRsSanPham();
	ResultSet RsLMKN=obj.getRsLoaiMauKN();
	List<IHoSoKiemNghiemChiTiet> listct=obj.getListCT();
	List<IHoSoKiemNghiemThietBi> listtb=obj.getListTB();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>

<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link rel="stylesheet" type="text/css" href="../css/style.css" />


<link rel="stylesheet" href="../css/cdtab.css" type="text/css">
<link rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">

<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100200;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
</style>

<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var size = document.getElementById("listCTSize").value;

		for (var r = 0; r < (size + 1); r++) {
			var s = "dieukienkhuyenmai_" + r;
			$('.' + s).colorbox({
				width : "46%",
				inline : true,
				href : "#" + s
			});
			//Example of preserving a JavaScript event for inline calls.
			$("#click").click(function() {
				$('#click').css({
					"background-color" : "#f00",
					"color" : "#fff",
					"cursor" : "inherit",
					"height" : "500px"
				}).text("");
				return false;
			});
		}

		$(".titleTab a").click(function() {

			var activeTab = $(this).attr("href");
			//var activeTab =
			$(".titleTab a").removeClass("active");
			$(this).addClass("active");
			$(".tabContents").hide();
			$(activeTab).fadeIn();

		});

	});
</script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});

	});
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function() {
		$(".select2").select2();
	});
</script>
<script type="text/javascript">
	function submitform() {
		document.forms["hstnForm"].action.value = "timtaomoi";
		document.forms["hstnForm"].submit();
	}
	function update() {
		document.forms["hstnForm"].action.value = "update";
		document.forms["hstnForm"].submit();
	}
</script>



</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="hstnForm" method="post"
		action="../../ErpHoSoKiemNghiemUpdateSvl">

		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="action" value=""> <input type="hidden"
			id="listCTSize" value="<%=listct.size()%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Hồ
											Sơ Kiểm Nghiệm > Chức Năng > Hồ Sơ Kiểm Nghiệm > Cập Nhật</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen%></TD>
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
										<TD width="30" align="left"><A
											href="../../ErpHoSoKiemNghiemSvl?userId=<%=userId%>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript:taomoi()"><IMG
													src="../images/Save30.png" title="Luu lai" alt="Luu lai"
													border="1" style="border-style: outset"></A>
											</div>
										</TD>

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
									<LEGEND class="legendtitle" style="color: black">Thông
										tin hồ sơ thử nghiệm </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<input type="hidden" name="pk_seq" id="pk_seq"
											value="<%=obj.getPk_seq()%>" />
										<TR>
											<TD class="plainlabel" valign="middle">Ngày chứng từ</TD>
											<TD class="plainlabel" ><input type="text" class="days" readonly="readonly"
												name="ngaychungtu" id="ngaychungtu" value="<%=obj.getNgayChungTu()%>"></TD>
											<TD class="plainlabel" valign="middle">Phòng Ban TH</TD>
											<TD class="plainlabel"><select name="phongbanth"
												id="phongbanth" class="select2" onChange="submitform()"
												style="width: 200px">
													<option></option>
													<%
														if (RsPhongBan != null) {
																											while (RsPhongBan.next()) {
																												if (RsPhongBan.getString("PK_SEQ").equals(obj.getMaPhongBan())) {
													%>

													<option value="<%=RsPhongBan.getString("PK_SEQ")%>"
														selected="selected"><%=RsPhongBan.getString("ma")%>-<%=RsPhongBan.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=RsPhongBan.getString("pk_seq")%>"><%=RsPhongBan.getString("ma")%>-<%=RsPhongBan.getString("ten")%></option>
													<%
														}
																											}
																											
																										}
													%>
											</select></TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle">Mã Số Kiểm Nghiệm</TD>
											<TD class="plainlabel"><input type="text"
												name="makiemnghiem" id="makiemnghiem" value="<%=obj.getMaSoKN()%>"></TD>
											<TD class="plainlabel" valign="middle">Số Phiếu Kiểm
												Nghiệm</TD>
											<TD class="plainlabel"><input type="text"
												name="sophieukiemnghiem" id="sophieukiemnghiem" value="<%=obj.getSoPhieuKN()%>"></TD>

										</TR>
										<TR>
										<TD class="plainlabel" valign="middle" width="15%">Phiếu
												Yêu Cầu Lấy Mẫu</TD>
											<TD class="plainlabel" valign="middle"><select
												name="yeucaulaymau" id="yeucaulaymau" class="select2"
												onChange="submitform()" style="width: 200px">
													<option></option>
													<%
														if (RsYCLM != null) {
															while (RsYCLM.next()) {
																if (RsYCLM.getString("PK_SEQ_YCLM").equals(obj.getMaPhieuYeuCauLayMau())) {
													%>
													<option value="<%=RsYCLM.getString("PK_SEQ_YCLM")%>"
														selected="selected"><%=RsYCLM.getString("PK_SEQ_YCLM")%>-<%=RsYCLM.getString("NGAYCHUNGTUYCLM")%>-<%=RsYCLM.getString("DIENGIAIYCLM")%></option>
													<%
														} else {
													%>
													<option value="<%=RsYCLM.getString("PK_SEQ_YCLM")%>"><%=RsYCLM.getString("PK_SEQ_YCLM")%>-<%=RsYCLM.getString("NGAYCHUNGTUYCLM")%>-<%=RsYCLM.getString("DIENGIAIYCLM")%></option>
													<%
														}
																											}
																											
																											
																										}
													%>
											</select></TD>
											<%-- <TD class="plainlabel" valign="middle">Thời gian giao
												mẫu</TD>
											<TD class="plainlabel"><input type="time"
												name="thoigiangiaomau" id="thoigiangiaomau" value="<%=obj.getThoiGianGiaoMau()%>"></TD> --%>
											<TD class="plainlabel" valign="middle">Ngày giao hàng</TD>
											<TD class="plainlabel"><input type="text" class="days" readonly="readonly"
												name="ngaygiaohang" id="ngaygiaohang" value="<%=obj.getNgaygiaohang()%>"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="15%">Sản
												Phẩm Kiểm Nghiệm</TD>
											<TD class="plainlabel" valign="middle" >
												<input type="hidden" name="sanphamkn" id="sanphamkn" value="<%=obj.getMaSanPham()%>"/>
												<select class="select2" 
												style="width: 200px" disabled="disabled" >
													<option></option>
													<%
														if (RsSP != null) {
															while (RsSP.next()) {
																if (RsSP.getString("PK_SEQ").equals(obj.getMaSanPham())) {
													%>

													<option value="<%=RsSP.getString("PK_SEQ")%>"
														selected="selected"><%=RsSP.getString("ma")%>-<%=RsSP.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=RsSP.getString("pk_seq")%>"><%=RsSP.getString("ma")%>-<%=RsSP.getString("ten")%></option>
													<%
														}
																											}
																											
																										}
													%>
											</select>
											<%-- <input
												type="text" name="tensanphamkiemnghiem"
												id="tensanphamkiemnghiem" value="<%=obj.getTenSanPham()%>">
												<input type="hidden" name="masanphamkiemnghiem"
												id="masanphamkiemnghiem" value="<%=obj.getMaSanPham()%>"> --%>
											</TD>
											
											<TD class="plainlabel" valign="middle" width="15%">Loại
												Mẫu Kiểm Nghiệm</TD>
											<TD class="plainlabel" valign="middle">
											<select name="loaimaukiemnghiem" onChange="submitformloaimau()" id="loaimaukiemnghiem"
												class="select2"  style="width: 200px">
													<option></option>
													<%
														if (RsLMKN != null) {
																while (RsLMKN.next()) {
																	if (RsLMKN.getString("PK_SEQ").equals(obj.getMaLoaiMauKN())) {
													%>

													<option value="<%=RsLMKN.getString("PK_SEQ")%>"
														selected="selected"><%=RsLMKN.getString("ma")%>-<%=RsLMKN.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=RsLMKN.getString("pk_seq")%>"><%=RsLMKN.getString("ma")%>-<%=RsLMKN.getString("ten")%></option>
													<%
														}
																											}
																										
																											
																										}
													%>
											</select>

										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="15%">Tiêu
												chuẩn kiểm nghiệm</TD>
											<TD class="plainlabel" valign="middle"><select
												name="tieuchuankiemnghiem" id="tieuchuankiemnghiem"
												class="select2" onChange="submitform()" style="width: 200px">
													<option></option>
													<%
													if (RsTCKM != null) {
														while (RsTCKM.next()) {
															if (RsTCKM.getString("PK_SEQ").equals(obj.getMaTieuChuanKiemNghiem())) {
													%>

													<option value="<%=RsTCKM.getString("PK_SEQ")%>"
														selected="selected"><%=RsTCKM.getString("ma")%>-<%=RsTCKM.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=RsTCKM.getString("pk_seq")%>"><%=RsTCKM.getString("ma")%>-<%=RsTCKM.getString("ten")%></option>
													<%
														}
																											}
																										
																											
																										}
													%>
											</select></TD>
											<TD class="plainlabel" valign="middle" width="15%">Loại
												Kiểm Tra</TD>
											<TD class="plainlabel" valign="middle"><select
												name="loaikiemtra" id="loaikiemtra" class="select2"
												onChange="" style="width: 200px">
													<option></option>
													<option value="1"
														<%if(obj.getLoaiKiemTra().equals("1")){%> selected <%}%>>Thẩm
														Định</option>
													<option value="2"
														<%if(obj.getLoaiKiemTra().equals("2")){%> selected <%}%>>Định
														Kỳ</option>
													<option value="3"
														<%if(obj.getLoaiKiemTra().equals("3")){%> selected <%}%>>Theo
														Yêu Cầu</option>
											</select></TD>

										</TR>
										<TR>
										<TD class="plainlabel" valign="middle">Thời gian giao
												mẫu</TD>
											<TD class="plainlabel"><input type="time"
												name="thoigiangiaomau" id="thoigiangiaomau" value="<%=obj.getThoiGianGiaoMau()%>"></TD>
											
											<TD class="plainlabel" valign="middle" width="15%">Thời
												Điểm Lấy Mẫu</TD>
											<TD class="plainlabel" valign="middle"><select
												name="thoidiemlaymau" id="thoidiemlaymau" class="select2"
												onChange="" style="width: 200px">
													<option></option>
													<option value="1"
														<%if(obj.getLoaiKiemTra().equals("1")){%> selected <%}%>>Trước
														Sản Xuất</option>
													<option value="2"
														<%if(obj.getLoaiKiemTra().equals("2")){%> selected <%}%>>Sau
														Sản Xuất</option>
													<option value="3"
														<%if(obj.getLoaiKiemTra().equals("3")){%> selected <%}%>>Sau
														Vệ Sinh</option>
													<option value="4"
														<%if(obj.getLoaiKiemTra().equals("3")){%> selected <%}%>>Vệ
														Sinh Định Kỳ</option>
											</select></TD>

										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="15%">Diễn
												Giải Chứng Từ</TD>
											<TD class="plainlabel" valign="middle" ><textarea
													rows="5" cols="5" name="diengiaichungtu"
													id="diengiaichungtu" value="<%=obj.getDienGiai()%>"></textarea></TD>
											<!-- <td class="plainlabel" valign="middle" colspan="2"> </td> -->
											<TD class="plainlabel" valign="middle" width="15%" style="display: none;">Hoạt
												Động</TD>
											<TD class="plainlabel" valign="middle" style="display: none;"><input
												<%if(obj.getTrangThai().equals("1")){%> checked <%}%>
												type="checkbox" name="trangthai" id="trangthai"></TD>
												
												<TD class="plainlabel" valign="middle">Người gửi mẫu</TD>
											<TD class="plainlabel"><input type="text"
												name="nguoiguimau" id="nguoiguimau" value="<%=obj.getNguoiGuiMau()%>"></TD>
										

										</TR>
										<TR>
											<td colspan="4">
												<div id="tabContaier">
													<div class="titleTab">
														<a class="active" href="#tab_1">Kiểm Nghiệm</a>
													</div>
													<div class="titleTab">
														<a href="#tab_2">Thiết Bị Kiểm Nghiệm</a>
													</div>

												</div>

												<div class="tabDetails">
													<div id="tab_1" class="tabContents" style="display: block">
														<table width="100%" cellpadding="4" cellspacing="2">
															<tr class="tbheader">
																<th align="center" width="5%">STT</th>
																<th align="center" width="10%">Kiểm Nghiệm Viên</th>
																<th align="center" width="10%">Yêu Cầu Kỹ Thuật</th>
																<th align="center" width="5%">Thông Số Yêu Cầu</th>
																<th align="center" width="8%">Thời Gian Bắt Đầu TN</th>

																<th align="center" width="8%">Thời Gian Kết Thúc TN</th>
																<th align="center" width="8%">Thời Lượng TN(h)</th>
																<th align="center" width="10%">Đánh Giá</th>
																<th align="center" width="10%">Popup(Kiểm nghiệm
																	chi tiết)</th>
															</tr>
															<%
																if(listct!=null){
																													for(int y=0;y<listct.size();y++){
																														IHoSoKiemNghiemChiTiet ct=listct.get(y);
															%>
															<tr>
																<td align="center" style="font-size: 8pt"><input type="hidden" name="sottctkn" value="<%=ct.getSoTT()%>"> <%=ct.getSoTT()%></td>
																<td><select name="kiemnghiemvien_<%=y %>" class="select2"
																	style="width: 200px">
																		<option></option>
																		<%
																			if (RsNhanVien != null) {
																				RsNhanVien.beforeFirst();
																					while (RsNhanVien.next()) {
																						if (RsNhanVien.getString("PK_SEQ").equals(ct.getMaKiemNghiemVien())) {
																		%>

																		<option value="<%=RsNhanVien.getString("PK_SEQ")%>"
																			selected><%=RsNhanVien.getString("ma")%>-<%=RsNhanVien.getString("ten")%></option>
																		<%
																			} else {
																		%>
																		<option value="<%=RsNhanVien.getString("pk_seq")%>"><%=RsNhanVien.getString("ma")%>-<%=RsNhanVien.getString("ten")%></option>
																		<%
																			}
																																								}
																																								
																																							}
																		%>
																</select></td>
																<td><input type="text" name="tenyeucaukythuatct"
																	value="<%=ct.getTenYCKT()%>" /> <input type="hidden"
																	name="mayeucaukythuatct" value="<%=ct.getMaYCKT()%>" />
																</td>
																<td><input type="text" name="thongsoyeucaukythuat"
																	style="width: 100px"
																	<%if(ct.getThongSoYeuCau().equals("") && ct.getThongSoYeuCauDen().equals("")){%>
																	value="" <%}else{%>
																	value="<%=ct.getThongSoYeuCau()%>-<%=ct.getThongSoYeuCauDen()%>"
																	<%}%> />
																	<input type="hidden" name="tsyc" value="<%=ct.getThongSoYeuCau()%>">
																	<input type="hidden" name="tsycd" value="<%=ct.getThongSoYeuCauDen()%>">
																	</td>
																<td><input style="width: 150px" type="text"
																	class="days" name="thoigianbatdau"
																	value="<%=ct.getThoiGianBD()%>"></td>
																<td><input style="width: 150px" type="text"
																	class="days" name="thoigianketthuc"
																	value="<%=ct.getThoiGianKT()%>"></td>
																<td><input style="width: 150px" type="text"
																	name="thoiluongtn" value="<%=ct.getThoiLuong()%>"></td>
																<td><input style="width: 100px" type="text"
																	name="danhgia" value="<%=ct.getDanhGia()%>"></td>
																<td><a href="" class="dieukienkhuyenmai_<%=y%>">&nbsp;
																		<img alt="Popup Kiem Nghiem Chi Tiet"
																		src="../images/vitriluu.png">
																</a> &nbsp;
																	<div style='display: none'>
																		<div id="dieukienkhuyenmai_<%=y%>"
																			style='padding: 0px 5px; background: #fff; overflow: auto'>

																			<table align="center" id="tblThamSo" width="100%">
																				<tr>
																					<th width="80px" style="font-size: 11px"></th>
																					<th width="100px" style="font-size: 11px"></th>
																					<th width="100px" style="font-size: 11px"></th>
																					<th width="250px" style="font-size: 11px"></th>

																				</tr>
																				<%
																					for(int t=0;t< 5;t++)
																																								{
																				%>
																				<tr>
																					<td><input type="text" name=""></td>
																					<td><input type="text" name=""></td>
																					<td><input type="text" name=""></td>
																					<td><input type="text" name=""></td>
																				</tr>
																				<%
																					}
																				%>
																			</table>
																		</div>
																	</div></td>
															</tr>
															<%
																}
																												}
															%>

														</table>

													</div>

												</div>
												<div id="tab_2" class="tabContents" style="display: none">
													<table width="100%" cellpadding="0" cellspacing="2">
														<tr class="tbheader">
															<th align="center" width="3%">STT</th>
															<th align="center" width="6%">Mã thiết bị
																(TSCĐ/CCDC)</th>
															<th align="center" width="6%">Tên thiết bị
																(TSCĐ/CCDC)</th>
															<th align="center" width="7%">Ngày đánh giá/hiệu
																chuẩn/bảo trì</th>
															<th align="center" width="7%">Ngày kiểm định/hiệu
																chuẩn/bảo trì kế tiếp</th>
															<th align="center" width="30%">Ghi Chú</th>
														</tr>
														<%
															if(listtb!=null)
																												for(int o=0;o<listtb.size();o++)
																												{
																													IHoSoKiemNghiemThietBi tb=listtb.get(o);
														%>
														<tr>
															<td align="center" style="font-size: 8pt"><input type="hidden" name="sotttb" value="<%=tb.getSoTT()%>" ><%=tb.getSoTT()%></td>
															<td align="center"><input type="text"
																name="mathietbi" value="<%=tb.getMaThietBi()%>" /></td>
															<td align="center"><input type="text"
																name="tenthietbi" value="<%=tb.getTenThietBi()%>" /></td>
															<td align="center"><input type="text" readonly="readonly"
																name="ngaydanhgia" /></td>
															<td align="center"><input type="text"  readonly="readonly"
																name="ngaykiemdinh" /></td>
															<td align="center"> <input style="width: 320px" type="text" name="ghichu" value="<%=tb.getGhiChu()%>" /></td>
														</tr>
														<%
															}
														%>
													</table>
												</div>

											</TD>
										</TR>


									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
		<script type="text/javascript" src="../scripts/loadingv2.js"></script>
		<script type="text/javascript">
			/* 	$('body').delegate('#sl', 'change', function(event) {
					var sl=$(this).val();
				
					for(var i=0;i<sl;i++){
						var s='<tr> <td width="20%"> <input type="text" style="width: 100%; text-align: right;" name="popupThamSo"> </td>';
						s+='<td width="25%"><select name="dvdl_'+(i*1+$("#y").val()*1)+'" style="width: 100%"><option></option>';
						
						$.each($('#tem option'),function(index,val){
							s+='<option value='+val.value+'>'+val.text+'</option>';
						})
						s+='</select></td>';
						s+='<td width="20%"> <input type="checkbox" style="width: 100%; text-align: right;" name="loaitick_'+(i*1+$("#y").val()*1)+'"> </td> ';
						s+='<td width="20%"> <input type="checkbox" style="width: 100%; text-align: right;" name="thamsogroup_'+(i*1+$("#y").val()*1)+'"> </td> </tr>';
						s+=$('#tblThamSo:not(:first)').html();
						$('#tblThamSo').append(s);
					}
					
					
				}); */
		</script>
	</form>



</BODY>
</HTML>


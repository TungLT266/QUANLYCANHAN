<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="geso.traphaco.distributor.beans.chuyentuyen.IChuyenTuyen"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else {
%>
<%
	IChuyenTuyen bean = (IChuyenTuyen) session.getAttribute("ctuyenBean");
	ResultSet ddkdFrom = (ResultSet) bean.getDkdFromRs();
	ResultSet ddkdTo = (ResultSet) bean.getDdkdToRs();

	ResultSet kh_tbh_dpt = (ResultSet) bean.getKh_Tbh_DptList();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<style type="text/css">
.plainlabelNew {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10pt;
	color: #000000;
	line-height: 15pt;
}

.plainlabelNew2 {
	display: none;
}
</style>
<SCRIPT language="javascript" type="text/javascript">

 function saveform()
 {
	document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
 	document.forms['tbhForm'].action.value='saveNVGN';
    document.forms['tbhForm'].submit();
 }
 
 function CheckALL()
 {
	var chkAll = document.getElementById("chkALL");
	var ddhIds = document.getElementsByName("khIds");
	
	for( i = 0; i < ddhIds.length; i++ )
	{
		if( chkAll.checked == true )
			ddhIds.item(i).checked = true;
		else
			ddhIds.item(i).checked = false;
	}
 }
 
 	function submitform()
	{
		document.forms['tbhForm'].action.value='locNVGN';
 		document.forms['tbhForm'].submit();
	}

 </SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		//$("select:not(.notuseselect2)").select2({ width: 'resolve' }); 
		$(".select2").select2( );
	});
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="tbhForm" method="post" action="../../ChuyenTuyenSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="nppId" id="nppId" value='<%=bean.getNppId()%>'>
		<input type="hidden" name="action" value='1'>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<!--begin body Dossier-->
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Dữ liệu nền kinh doanh &gt; Chuyển khách hàng NVGN
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng <%= userTen %></TD>
									</TR>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="2">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript:saveform()">
													<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1" style="border-style: outset"></A>
											</div>
										</TD>
										<TD align="left">&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>

					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
									<textarea name="dataerror"
										style="width: 100%; font-weight: bold"
										cols="139%" rows="1"><%=bean.getMessage()%></textarea>
								</FIELDSET>
							</TD>
						</tr>
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle">&nbsp;Thông tin chuyển khách hàng &nbsp;</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">

										<TR>
											<TD style="width: 15%" class="plainlabel">NVGN Cũ</TD>
											<TD class="plainlabel"><SELECT name="ddkdFromId"
												id="ddkdFromId" onChange="submitform();" class="select2" style="width: 400px;" >
													<option value=""></option>
													<%
														if (ddkdFrom != null)
																while (ddkdFrom.next()) {
																	if (ddkdFrom.getString("pk_seq").equals(
																			bean.getDddkdFromId())) {
													%>
													<option value='<%=ddkdFrom.getString("pk_seq")%>' selected><%=ddkdFrom.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=ddkdFrom.getString("pk_seq")%>'><%=ddkdFrom.getString("ten")%></option>
													<%
														}
																}
													%>
											</SELECT></TD>
										</TR>

										<TR>
											<TD style="width: 15%" class="plainlabel">NVGN Mới</TD>
											<TD class="plainlabel"><SELECT name="ddkdToId"
												id="ddkdToId" onChange="submitform();" class="select2" style="width: 400px;"  >
													<option value=""></option>
													<%
														if (ddkdTo != null)
																while (ddkdTo.next()) {
																	if (ddkdTo.getString("pk_seq").equals(
																			bean.getDdkdToId())) {
													%>
													<option value='<%=ddkdTo.getString("pk_seq")%>' selected><%=ddkdTo.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=ddkdTo.getString("pk_seq")%>'><%=ddkdTo.getString("ten")%></option>
													<%
														}
																}
													%>
											</SELECT></TD>
										</TR>
									</TABLE>

								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					
					<TABLE width=100% border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" valign="top">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%">
											<FIELDSET>
												<LEGEND class="legendtitle">Khách hàng của NVGN cũ</LEGEND>
												<TABLE width="100%" border="0" cellspacing="1" cellpadding="4" id="tb_kh_tbh_dpt">
													<tbody id="kh_tbh_dpt">
														<TR class="tbheader">
															<TH width="5%">STT</TH>
															<TH width="15%">Mã KH</TH>
															<TH width="20%">Họ tên</TH>
															<TH width="40%">Địa chỉ</TH>
															<TH width="10%">Điện thoại</TH>
															<TH align="center" style="width: 10%" >Chọn ALL <input type="checkbox"  id='chkALL' name='chkALL' value="1" onchange="CheckALL();"   > </TH>
														</TR>
															<%
															int i = 0;
																String lightrow = "tblightrow";
																String darkrow = "tbdarkrow";
																if (kh_tbh_dpt != null)
																	while (kh_tbh_dpt.next()) {
																		if (i % 2 != 0) {
															%>
																<TR class=<%=lightrow%>>
															<% } else { %>
																<TR class=<%=darkrow%>>
															<% } %>
															<TD ><%= i+1 %></TD>
															<TD ><%=kh_tbh_dpt.getString("maFAST")%></TD>
															<TD ><%=kh_tbh_dpt.getString("ten")%></TD>
															<TD ><%=kh_tbh_dpt.getString("diachi")%></TD>
															<TD ><%=kh_tbh_dpt.getString("dienthoai")%></TD>
															<td align="center" >
								                				<% if( bean.getKhachhangIds().contains( kh_tbh_dpt.getString("khId") ) ) { %>
								                					<input type="checkbox" name = "khIds" value="<%= kh_tbh_dpt.getString("khId") %>"  checked="checked" >
								                				<% } else { %>
								                					<input type="checkbox" name = "khIds" value="<%= kh_tbh_dpt.getString("khId") %>" >
								                				<% } %> 
								                			</td>
														</TR>
														<%
															i++;
																	}
														%>
														<tr class="tbfooter">
															<td colspan="6">&nbsp;</td>
														</tr>
													</tbody>
												</TABLE>

											</FIELDSET>
										</TD>
									</TR>
								</TABLE>
							</TD>

						</TR>
					</TABLE> 

				</TD>
			</TR>

		</TABLE>
	</form>
</BODY>


<%
	if(bean!=null)bean.DBClose();
	session.setAttribute("ctuyenBean", null);
	}
%>
</HTML>

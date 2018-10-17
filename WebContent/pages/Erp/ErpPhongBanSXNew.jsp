<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.phongbansx.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.phongbansx.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@page import="java.sql.SQLException"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IErpPhongbanSX obj = (IErpPhongbanSX)session.getAttribute("csxBean");
	ResultSet dvtRs = obj.getDvtRs();
	String[] spDiengiai = obj.getSpDiengiai(); 
	String[] spDinhmuctu = obj.getSpDinhmuctu();
	String[] spDinhmucden = obj.getSpDinhmucden();
	String[] spDonvi = obj.getSpDonvi();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/jquery.tools.min.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["khtt"].submit();
	}
	
	function keypress(e) {    
		var keypressed = null;
		if(window.event)
			keypressed = window.event.keyCode;
		else
			keypressed = e.which;
		
		if((keypressed>31 && keypressed<45) || keypressed == 47 || keypressed>57) {
	        return false;
	    }
	    return true;
	}
	
	function save()
	{
		if(document.getElementById("ma").value.trim() == ""){
			document.getElementById("dataerror").value = "Mã khu vực sản xuất không được để trống.";
			return false;
		}
		
		if(document.getElementById("diengiai").value.trim() == ""){
			document.getElementById("dataerror").value = "Tên khu vực sản xuất không được để trống.";
			return false;
		}
		
		var diengiaiList = document.getElementsByName("spDiengiai");
		var thongsoTuList = document.getElementsByName("spDinhmuctu");
		var thongsoDenList = document.getElementsByName("spDinhmucden");
		
		var i;
		for(i = 0; i < thongsoTuList.length; i++){
			if(thongsoTuList.item(i).value != "" || thongsoDenList.item(i).value != ""){
				if(thongsoTuList.item(i).value != "" && thongsoDenList.item(i).value != ""){
					var thongsoTu = parseFloat(thongsoTuList.item(i).value);
					if(isNaN(thongsoTu)){
						document.getElementById("dataerror").value = "STT "+ (i+1) +": Thông số yêu cầu từ không hợp lệ.";
						return false;
					}
					
					//thongsoTuList.item(i).value = thongsoTu;
					
					var thongsoDen = parseFloat(thongsoDenList.item(i).value);
					if(isNaN(thongsoDen)){
						document.getElementById("dataerror").value = "STT "+ (i+1) +": Thông số yêu cầu đến không hợp lệ.";
						return false;
					}
					
					//thongsoDenList.item(i).value = thongsoDen;
					
					if(thongsoTu > thongsoDen){
						document.getElementById("dataerror").value = "STT "+ (i+1) +": Thông số yêu cầu từ phải nhỏ hơn hoặc bằng thông số yêu cầu đến.";
						return false;
					}
					
					if(diengiaiList.item(i).value.trim() == ""){
						document.getElementById("dataerror").value = "STT "+ (i+1) +": Bạn chưa nhập diễn giải cho thông số.";
						return false;
					}
				} else {
					if(thongsoTuList.item(i).value.trim() != ""){
						document.getElementById("dataerror").value = "STT "+ (i+1) +": Bạn chưa nhập thông số yêu cầu đến.";
						return false;
					} else {
						document.getElementById("dataerror").value = "STT "+ (i+1) +": Bạn chưa nhập thông số yêu cầu từ.";
						return false;
					}
				}
			}
		}
		
		document.forms["khtt"].action.value = "save";
		document.forms["khtt"].submit(); 
    }
	
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="khtt" method="post" action="../../ErpPhongbanSXUpdateSvl">
		<input type="hidden" name="userId" value='<%= userId %>'> <input
			type="hidden" name="id" value='<%= obj.getId() %>'> <input
			type="hidden" name="action" value="0">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">
											&nbsp;Dữ liệu nền > Sản xuất > Khu vực sản xuất > Tạo mới
										</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
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
											href="../../ErpPhongbanSXSvl?userId=<%= userId%>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript: save()"><IMG
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
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>

									<textarea name="dataerror" id="dataerror" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông tin khu vực </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD class="plainlabel" valign="middle" width="120px">Mã khu vực SX <FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel" valign="middle">
												<%if(obj.getIsChangeMa().equals("1")){ %>
					                        		<input type="hidden" value="1" name="ischangema">
					                        		<input readonly type="text" style="width: 350px" id="ma" name="ma" value="<%= obj.getMa() %>">
					                        	<%} else { %>
					                        		<input type="text" style="width: 350px" id="ma" name="ma" value="<%= obj.getMa() %>">
					                        	<%} %>
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle" width="120px">Tên khu vực SX <FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel" valign="middle">
												<input type="text" style="width: 350px" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>">
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel" valign="middle">Trạng thái</TD>
											<TD class="plainlabel" valign="middle">
												<% if(obj.getTrangthai().equals("1")) { %>
													<input type="checkbox" name="trangthai" value="1" checked="checked"><i> Hoạt động</i>
												<%} else { %>
													<input type="checkbox" name="trangthai" value="1"><i> Hoạt động</i>
												<%}%>
											</TD>
										</TR>
									</TABLE>

									<table cellpadding="0px" cellspacing="1px" width="100%">
										<tr class="tbheader">
											<th align="center" width="5%">STT</th>
											<th align="center" width="30%">Diễn giải</th>
											<th align="center" width="25%">Thông số yêu cầu từ</th>
											<th align="center" width="25%">Thông số yêu cầu đến</th>
											<th align="center" width="15%">Đơn vị</th>
										</tr>

										<%
								int count = 0;
								if(spDiengiai != null)
								{
									for(int i = 0; i < spDiengiai.length; i++)
									{
									%>

										<tr>
											<td style="text-align: center;"><%= i + 1 %></td>
											<td><input type="text" name="spDiengiai" value="<%= spDiengiai[i] %>" style="width: 100%"></td>
											<td><input type="text" name="spDinhmuctu" value="<%= spDinhmuctu[i] %>" style="width: 100%;text-align: right;" onkeypress="return keypress(event);"></td>
											<td><input type="text" name="spDinhmucden" value="<%= spDinhmucden[i] %>" style="width: 100%;text-align: right;" onkeypress="return keypress(event);"></td>
											<td><select name="spDonvi" style="width: 100%"
												class="select2">
													<option value="0"></option>
													<% if(dvtRs != null) 
												{ 
													dvtRs.beforeFirst();
													while(dvtRs.next())
													{
														if(spDonvi[i].equals(dvtRs.getString("pk_seq")))
														{ %>
													<option value="<%= dvtRs.getString("pk_seq") %>"
														selected="selected"><%= dvtRs.getString("DONVI") %></option>
													<% } else { %>
													<option value="<%= dvtRs.getString("pk_seq") %>"><%= dvtRs.getString("DONVI") %></option>
													<% } }
												} %>
											</select></td>
										</tr>

										<% count ++; } } %>

										<% for(int i = count; i < 10; i++) { %>

										<tr>
											<td style="text-align: center;"><%= i + 1 %></td>
											<td><input type="text" name="spDiengiai" value="" style="width: 100%"></td>
											<td><input type="text" name="spDinhmuctu" value="" style="width: 100%;text-align: right;" onkeypress="return keypress(event);"></td>
											<td><input type="text" name="spDinhmucden" value="" style="width: 100%;text-align: right;" onkeypress="return keypress(event);"></td>
											<td><select name="spDonvi" style="width: 100%"
												class="select2">
													<option value="0"></option>
													<% if(dvtRs != null) 
											{ 
												dvtRs.beforeFirst();
												while(dvtRs.next())
												{ %>
													<option value="<%= dvtRs.getString("pk_seq") %>"><%= dvtRs.getString("DONVI") %></option>
													<% 	} 
											} %>
											</select></td>
										</tr>

										<% } %>

									</table>

								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>

</BODY>
</HTML>
<%
if (obj != null)
{
	obj.DbClose();
	obj = null;
}
session.removeAttribute("csxBean");
%>
<%}%>

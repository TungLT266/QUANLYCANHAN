<%@page import="java.util.Hashtable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.traphaco.erp.beans.thongtindathang.*" %>
<%@ page import="geso.traphaco.center.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>


<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IThongtindathang obj =(IThongtindathang)session.getAttribute("obj");
	ResultSet spRs = obj.getSpRs();
	ResultSet nccRs = obj.getNccRs();
	ResultSet dtsxRs = obj.getDtsxRs();
	ResultSet dtsxRs_ss = obj.getDtsxRs_ss();
	ResultSet nmRs = obj.getNhamayRs();
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
	<script language="javascript" src="../scripts/datepicker.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	$(document).ready(function() { $("select").select2(); });
	</script>
	<script type="text/javascript">
	$(document).ready(function() {
		$( ".days" ).datepicker({
			changeMonth: true,
			changeYear: true
		});
	});
	</script>
	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript">
		$(document).ready(function(){
			$(".button").hover(function(){
				$(".button img")
				.animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
				.animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
				.animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
			});
		});
		$(document).ready(function(){
			$(".button2").hover(function(){
				$(".button2 img")
				.animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
				.animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
				.animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
			});
		});
		$(document).ready(function(){
			$(".button3").hover(function(){
				$(".button3 img")
				.animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
				.animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
				.animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
			});
		});

	</script>

	<SCRIPT language="javascript" type="text/javascript">

	function keypress(e) //Hàm dùng để ngăn người dùng nhập các ký tự khác ký tự số vào TextBox
	{
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		if (keypressed < 48 || keypressed > 57)
		{
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}

	function save()
	{
		document.forms["form"].action.value = "save";
		document.forms["form"].submit();
	}

	function submitform()
	{
		document.forms["form"].action.value= "submit";
		document.forms["form"].submit();
	}

	function clearform()
	{
		document.forms["form"].submit();
	}
	
	function upload() // nhan nut cap nhat
	 {
		document.forms["form"].setAttribute('enctype', "multipart/form-data", 0);
		document.forms["form"].submit();	
	 }

	function selectAll(id1, id2)
	{
		var chkAll = document.getElementById(id1);
		var spIds = document.getElementsByName(id2);
		if(chkAll.checked)
		{
			for(i = 0; i < spIds.length; i++)
			{
				spIds.item(i).checked = true;
			}
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
			{
				spIds.item(i).checked = false;
			}
		}
	}
	
	function viewFile(url) {
	   	newWindow = window.open(url, '', 'height=800,width=1000, scrollbars=1');
	}

	</SCRIPT>
	<script>
	//perform JavaScript after the document is scriptable.
		$(function() {
		// setup ul.tabs to work as tabs for each div directly under div.panes
			$("ul.tabs").tabs("div.panes > div");
		});
	</script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="form" method="post" action="../../ThongtindathangUpdateSvl" >
<input type="hidden" name="userId" value="<%= userId %>" >

<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="1"
	height="100%">
	<TR>
		<TD colspan="4" align="left" valign="top" bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR height="22">
								<TD align="left" colspan="2" class="tbnavigation">Dữ liệu nền > Kho vận > Thông tin lập kế hoạch > Tạo mới</TD>
								<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR >
					<TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left">
									<A href="../../ThongtindathangSvl?userId=<%= userId%>" >
										<img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset">
									</A>
								</TD>
								<TD width="2" align="left" ></TD>
								<TD width="30" align="left" >
									<div id="btnSave">
										<A href="javascript: save()" >
											<IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border = "1"  style="border-style:outset"></A>
									</div>
								</TD>
								<TD >&nbsp; </TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
							<LEGEND class="legendtitle">Thông báo </LEGEND>
							<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"
								style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="3"><%= obj.getMsg() %>
							</textarea>
						</FIELDSET>
					</TD>
				</tr>
				<TR>
					<TD height="100%" width="100%">
						<FIELDSET >
							<LEGEND class="legendtitle" style="color:black">Thông tin đặt hàng</LEGEND>
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">


							<TR>
								<TD class="plainlabel" valign="middle" width="20%" >Sản phẩm</TD>
								<TD class="plainlabel" valign="middle" width="25%" >
									<select name="spId" style="width: 250px">
									<option value=""></option>
								<% while(spRs.next()){ 
										if(spRs.getString("ID").equals(obj.getSpId())){
								%>	
									<option value="<%= spRs.getString("ID")%>" SELECTED><%= spRs.getString("SPTEN")%></option>
								
								<%		}else{ %>	
									<option value="<%= spRs.getString("ID")%>" ><%= spRs.getString("SPTEN")%></option>
								<% 		}
									}%>
									</select>
								</TD>
								
								<TD class="plainlabel" align="left" valign = "bottom" colspan = "2" >
							<% if(obj.getIsMua().equals("1")){ %>
							
								<input type="checkbox" name="isMua" value="1" checked onChange = submitform(); >&nbsp; Mua bên ngoài
							
							<% }else{ %>
							
								<input type="checkbox" name="isMua" value="1" onChange = submitform(); >&nbsp; Mua bên ngoài
								
							<% } %>
							
								</TD>
								
									
							</TR>
					<% if(obj.getIsMua().equals("1")){ %>				
							<TR>
								<TD class="plainlabel" valign="middle">Nhà cung cấp</TD>
								<TD class="plainlabel" valign="middle" width="25%" colspan = 3 >
									<select name="nccId"  style="width: 250px">
										<option value=""></option>
								<% while(nccRs.next()){ 
										if(nccRs.getString("ID").equals(obj.getNccId())){
							%>	
										<option value="<%= nccRs.getString("ID")%>" SELECTED><%= nccRs.getString("NCCTEN")%></option>
								
							<%			}else{ %>	
										<option value="<%= nccRs.getString("ID")%>" ><%= nccRs.getString("NCCTEN")%></option>
							<% 			}
								   }%>

									</select>
								</TD>
							</TR>
								
							<TR>
								<TD class="plainlabel" valign="middle" >Số lượng đặt tối thiểu</TD>
								<TD class="plainlabel" valign="middle" >
									<input type="text" name="mou" value="<%= formatter.format(Double.parseDouble(obj.getMOU())) %>" >
								</TD>

								<TD class="plainlabel" valign="middle" width="120px">Thời gian giao hàng (ngày)</TD>
								<TD class="plainlabel" valign="middle" >
									<input type="text" name="leadtime" value="<%= obj.getLeadtime() %>" >
								</TD>
									

							</TR>
					<% }else{ %>		
							<TR>
								<TD class="plainlabel" valign="middle" >Số lượng mẻ sản xuất</TD>
								<TD class="plainlabel" valign="middle" colspan = 3>
									<input type="text" name="lotsize" value="<%= formatter.format(Double.parseDouble(obj.getLotsize())) %>" >
								</TD>

							</TR>

							<TR>
								<TD class="plainlabel" valign="middle">Nhà máy</TD>
								<TD class="plainlabel" valign="middle" width="25%" colspan = 3 >
									<select name="nmId"  style="width: 250px" onChange = submitform();>
										<option value=""></option>
						<% if(nmRs != null){
								try{

									while(nmRs.next()){ 
										if(nmRs.getString("ID").equals(obj.getNhamayId())){
							%>	
										<option value="<%= nmRs.getString("ID")%>" SELECTED><%= nmRs.getString("TEN")%></option>
								
							<%			}else{ %>	
										<option value="<%= nmRs.getString("ID")%>" ><%= nmRs.getString("TEN")%></option>
							<% 			}
								   }
								}catch(java.sql.SQLException e){}  
							}		   
								   %>

									</select>
								</TD>
							</TR>
							
							<TR>
							<TD colspan = "4">
							<FIELDSET>
							<LEGEND class="legendtitle">
							Sản xuất trên một dây truyền
							</LEGEND>

								<TABLE border = "0" cellspacing = "2" cellpadding = "6" width = "60%">
								<TR class = "tbheader">
								<TH > Dây truyền sản xuất</TH>
								<TH> Thời gian (giờ) </TH>
								</TR>
		<%		try{
					int m = 0;
					if(dtsxRs_ss != null)
					while(dtsxRs.next()){ %>
					 	<% if (m%2 == 0){ %>	
									 
						<TR class = "tbdarkrow">
								
					 	<% }else{ %>
								
						<TR class = "tblightrow">
							
						<%  } %>

						<% if(dtsxRs.getInt("CHON") > 0 & dtsxRs.getInt("LOAI") == 1){ %>
								<TD  valign="middle" >
									<input type="hidden" name="DtsxIds" value="<%= dtsxRs.getString("ID")%>"  >
									<input type="checkbox" name="<%= "dt_" + dtsxRs.getString("ID") %>" value="1" checked ><%= dtsxRs.getString("TEN")%>

								</TD>				
								<TD align = "center"><input type = "text" name = "<%= "tgsx_" + dtsxRs.getString("ID") %>" value = "<%= dtsxRs.getString("THOIGIAN")%>" style = "width:100;text-align:right" onKeyPress="return keypress(this);"></TD>
						<%}else{ %>							
									
								<TD>
									<input type="hidden" name="DtsxIds" value="<%= dtsxRs.getString("ID")%>"  >
									<input type="checkbox" name="<%= "dt_" + dtsxRs.getString("ID") %>" value="1" ><%= dtsxRs.getString("TEN")%>						
								</TD>	
								<TD align = "center"><input type = "text" name = "<%= "tgsx_" + dtsxRs.getString("ID") %>" value = "" style = "width:100;text-align:right" onKeyPress="return keypress(this);"></TD>
						<%} %>
								</TR>		 
								
						<% 			m++;	
								} %>									 
						<%		
								}catch(java.sql.SQLException e){} %>
						 		
							</TABLE>
						</FIELDSET>
						<BR>
						
						<FIELDSET>
						<LEGEND class="legendtitle" style="color:black">
						Sản xuất trên nhiều dây truyền
						</LEGEND>
							
							<TABLE border = "0" cellspacing = "2" cellpadding = "6" width = "60%">
							<TR class = "tbheader">
								<TH > Dây truyền sản xuất</TH>
								<TH> Thời gian (giờ) </TH>
								</TR>
				<%		try{
							int m = 0;
							if(dtsxRs_ss != null)
							while(dtsxRs_ss.next()){ %>
							 <% if (m%2 == 0){ %>	
									 
								<TR class = "tbdarkrow">
								
									 <% }else{ %>
								
								<TR class = "tblightrow">
									
									<%  } %>
			<%				if(dtsxRs_ss.getInt("CHON") > 0 & dtsxRs_ss.getInt("LOAI") == 2){ %>
							
								<TD  valign="middle" >
									<input type="hidden" name="DtsxIds_ss" value="<%= dtsxRs_ss.getString("ID")%>"  >
									
									<input type="checkbox" name="<%= "dt_ss_" + dtsxRs_ss.getString("ID") %>" value="1" checked ><%= dtsxRs_ss.getString("TEN")%>
								</TD>	
								<TD align = "center"><input type = "text" name = "<%= "tgsx_ss_" + dtsxRs_ss.getString("ID") %>" value = "<%= dtsxRs_ss.getString("THOIGIAN")%>" style = "width:100;text-align:right" onKeyPress="return keypress(this);"></TD>
						
			<%				}else{ %>
								<TD>		
									<input type="hidden" name="DtsxIds_ss" value="<%= dtsxRs_ss.getString("ID")%>"  >				
									<input type="checkbox" name="<%= "dt_ss_" + dtsxRs_ss.getString("ID") %>" value="1" ><%= dtsxRs_ss.getString("TEN")%>
								</TD>
								</TD>	
								<TD align = "center"><input type = "text" name = "<%= "tgsx_ss_" + dtsxRs_ss.getString("ID") %>" value = "" style = "width:100;text-align:right" onKeyPress="return keypress(this);"></TD>
			<%				} %>

								</TR>		 
								
						<% 			m++;	
								} %>									 
						<%		
								}catch(java.sql.SQLException e){} %>
						 		
								</TABLE>
							</FIELDSET>
						</TD>
						</TR>


						<TR>
							<TD class="plainlabel" valign="middle" >Thời gian vệ sinh sau sản xuất (giờ)</TD>
							<TD class="plainlabel" valign="middle" colspan = 3 >
								<input type="text" name="cleanup" value="<%= obj.getCleanUp() %>" >
							</TD>


						</TR>
						
				<% } %>


							</TABLE>
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


try{

	session.setAttribute("obj", null);
	if(spRs != null) spRs.close();
	if(nccRs != null) nccRs.close();
	if(dtsxRs != null) dtsxRs.close();
	if(dtsxRs_ss != null) dtsxRs_ss.close();
	if(nmRs != null) nmRs.close();
	obj.DbClose();
}catch(Exception er){

	
}
finally{
	
}

	}%>

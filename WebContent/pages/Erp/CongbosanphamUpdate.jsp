<%@page import="java.util.Hashtable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.traphaco.erp.beans.congbosanpham.*" %>
<%@ page import="geso.traphaco.center.util.*"%>

<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	//String msg = (String) session.getAttribute("Msg");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	ICongbosanpham obj =(ICongbosanpham)session.getAttribute("obj");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
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
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	$(document).ready(function() { $("select").select2(); });
	</script>
	<script type="text/javascript">
	</script>
	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
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
<form name ="form" method="post" action="../../CongbosanphamUpdateSvl" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="id" value="<%= obj.getId() %>" >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align="left" valign="top" bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR height="22">
								<TD align="left" colspan="2" class="tbnavigation">Dữ liệu nền > Sản phẩm > Công bố sản phẩm > Cập nhập</TD>
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
									<A href="../../CongbosanphamSvl?userId=<%= userId%>" >
										<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset">
									</A>
								</TD>
								<TD width="2" align="left" ></TD>
								<TD width="30" align="left" >
									<div id="btnSave">
										<A href="javascript: save()" >
											<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
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
							<textarea name="dataerror"  style="width: 100% ; background-color:white; font-weight:bold ;color:red;"
								readonly="readonly" rows="1"><%= obj.getMsg() %>
							</textarea>
						</FIELDSET>
					</TD>
				</tr>
				<TR>
					<TD height="100%" width="100%">
						<FIELDSET >
							<LEGEND class="legendtitle" style="color:black">Thông tin Công bố sản phẩm</LEGEND>
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">


								<TR>
									<TD class="plainlabel" valign="middle" width="15%" >Mã sản phẩm</TD>
									<TD class="plainlabel" valign="middle" width="25%" >
										<input type="text" name="masp" value="<%= obj.getMasp() %>" readonly>
									</TD>
									<TD class="plainlabel" valign="middle" width="15%" >Tên sản phẩm</TD>
									<TD class="plainlabel" valign="middle" >
										<input type="text" name="tensp" value="<%= obj.getTensp() %>" readonly style="width:350px">
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel" valign="middle">Mã nhà cung cấp</TD>
									<TD class="plainlabel" valign="middle">
										<input type="text" name="mancc" value="<%= obj.getMancc() %>" readonly>
									</TD>
									<TD class="plainlabel" valign="middle">Tên nhà cung cấp</TD>
									<TD class="plainlabel" valign="middle" >
										<input type="text" name="tenncc" value="<%= obj.getTenncc() %>" readonly style="width:350px">
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel" valign="middle" >Ngày hết hạn công bố</TD>
									<TD class="plainlabel" valign="middle" >
										<input type="text" name="hancongbo" value="<%= obj.getHancongbo() %>"  class="days">
									</TD>

									<TD class="plainlabel" valign="middle" width="120px">Hồ sơ công bố</TD>
									<%-- <TD class="plainlabel" valign="middle" width="240px">
										<input type="text" name="hinhcongbo" value="<%= obj.getHinhcongbo() %>"  >
									</TD> --%>
									
									<TD style="font-size: 9pt;" class="plainlabel">
  										<INPUT type="file" name="filename" title="Click để thay đổi hồ sơ công bố" style="width: 70px;" value="" onChange="upload()">
	  									<input type="text" name="hinhcongbo" value = "<%=obj.getHinhcongbo() %>" style="width: 22%; height:22px" readonly >	
	  									<input type="hidden" name="filepath" value = "<%=obj.getFilepath() %>" >
									<%
										 if(!obj.getHinhcongbo().equals(""))
										 {%>
											<button onclick="javascript:viewFile('../../CongbosanphamUpdateSvl?userId=<%=userId %>&print=<%=obj.getHinhcongbo() %>')">
											    <code>Xem hồ sơ công bố</code>
											</button>
										<%} %>
									</TD>

								</TR>
								
								<%-- <tr>
									<TD class="plainlabel" valign="middle" colspan=4 align=center>
										<img alt="" src="<%="../Templates/images/"+obj.getHinhcongbo()%>" style="max-width:100%">
									</TD>
								</tr> --%>
								
								
								<TR>
									<TD class="plainlabel" valign="middle">Số đăng kí</TD>
									<TD class="plainlabel" valign="middle">
										<input type="text" name="sodangki" value="<%= obj.getSodangki()%>" >
									</TD>
									<TD class="plainlabel" valign="middle">Dạng bào chế</TD>
									<TD class="plainlabel" valign="middle" >
										<%-- <input type="text" name="dangbaoche" value="<%= obj.getTenncc() %>" readonly style="width:350px"> --%>
									   	<select name="dangbaoche" style="width: 200px;"  disabled="disabled">
				                   		<option value=""></option>
				                   			<%  ResultSet rs= obj.getDangbaocheRs();  if( rs != null ) { %>
				                    		<% while(rs.next()) 
				                    		{ 
				                    			if(obj.getDangbaocheId().equals(rs.getString("PK_SEQ"))) 
				                    			{ %>
				                    				<option value="<%= rs.getString("PK_SEQ") %>" selected="selected"><%= rs.getString("ten") %></option>
				                    			<% } 
				                    			else { %> 
				                    				<option value="<%= rs.getString("PK_Seq") %>" ><%= rs.getString("ten") %></option>
				                    			<% }  %> 
				                    		<% } rs.close(); }  %>
				                    		
				                    	</select>
				                    	
				                    	
									</TD>
								</TR>
								
								
								<%-- <TR>
								
									<TD class="plainlabel" valign="middle">  Hiệu lực:   Từ ngày</TD>
									<TD class="plainlabel" valign="middle">
										<input type="text" class="days"  name="ngaybatdau" value="<%= obj.getNgaybatdauhieuluc() %>" readonly>
									</TD>
									<TD class="plainlabel" valign="middle">Đến ngày</TD>
									<TD class="plainlabel" valign="middle" >
										<input type="text"  class="days"  name="ngayketthuc" value="<%= obj.getNgayketthuchieuluc() %>" readonly style="width:350px">
									</TD>
								</TR> --%>
								
								
								<TR>
								
									<TD class="plainlabel" valign="middle">  Hiệu lực:</TD>
									
									<TD class="plainlabel" valign="middle" >
										<input type="text"  class="days"  name="ngayketthuc" value="<%= obj.getNgayketthuchieuluc() %>" readonly style="width:200px">
									</TD>
									
									
									<TD class="plainlabel" valign="middle" style="display: none">
										<input type="text" class="days"  name="ngaybatdau" value="<%= obj.getNgaybatdauhieuluc() %>" readonly>
									</TD>
									
									
									<TD class="plainlabel" valign="middle" style="display: none">Đến ngày</TD>
									
									
									<TD class="plainlabel" valign="middle" colspan="2"></TD>
									
								</TR>

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
	 obj.DbClose();
}catch(Exception er){

	
}
finally{
	
}

	}%>

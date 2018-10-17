<%@page import="geso.traphaco.center.beans.bccharttonkhomien.IBccharttonkhomien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>

<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.traphaco.center.beans.bccharttonkhomien.*" %>
<%@ page import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	String url="";
	url = util.getUrl("BCCharttonkhomienSvl","");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Trafaco/index.jsp");
	}else{ 
	IBccharttonkhomien obj =(IBccharttonkhomien)session.getAttribute("obj");
	ResultSet rs=obj.getRs();
	
	String[] arrIdMien=obj.getArrIDMien();
	String[] arrTenMien=obj.getArrTenMien();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$( ".days" ).datepicker({
				changeMonth: true,
				changeYear: true
			});
		});
	</script>
<script type="text/javascript" src="../scripts/Chart/jsapi"></script>
<script src="../scripts/Chart/jquery.gvChart-0.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	gvChartInit();
		jQuery(document).ready(function(){
			jQuery('#myTable').gvChart({
				chartType: 'PieChart',
				gvSettings: {
					vAxis: {title: 'Tồn kho theo Miền (Đơn vị tính: sản phẩm)'},
					hAxis: {title: 'Miền'},
					width: 900,
					height:700
					}
			});
		});
</script>
<style>
	body{
		text-align: center;
		font-family: Arial, sans-serif;
		font-size: 12px;
	}
	
	a{
		text-decoration: none;
		font-weight: bold;
		color: #555;
	}
	
	a:hover{
		color: #000;
	}
	
	div.main{
		/* margin: auto;
		*/
		text-align: left;
		width: 1200px;
	}
	
	.gvChart{
		/*border: 2px solid #850000;
		border-radius: 5px;
		-moz-border-radius: 10px;
		
			margin: auto;*/
		width: 500px;
		
	
		margin-left:0px;
		margin-top: 0px;
	}

</style>
	<SCRIPT language="javascript" type="text/javascript">


	function submitform()
	{
		document.forms["khtt"].submit();
	}


	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../BCCharttonkhomienSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align="left" valign="top" bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR height="22">
								<TD align="left" colspan="2" class="tbnavigation">&nbsp;<%=url %> </TD>
								<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left" >
									<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Phạm vi báo cáo &nbsp;</LEGEND>

										<TABLE width="100%" cellpadding="6" cellspacing="0">
								<TR>
								  	<TD class="plainlabel" valign="middle">Trạng thái </TD>
                        			<TD class="plainlabel" valign="middle">
							  	  	<select name="trangthai" class="select2" style="width: 200px"  onchange="submitform();"  >
									<% if (obj.getTrangThai().equals("0")){%>
									  	<option value="0" selected>Tồn kho</option>
									  	<option value="1">Xuất nhập tồn</option>
									<%}else if(obj.getTrangThai().equals("1")) {%>
									 	<option value="0" >Tồn kho</option>
									  	<option value="1" selected>Xuất nhập tồn</option>
									  		<%} %>
                         	  		</select>
							  	</TR>
								 
							
								<TR>
									<TD class="plainlabel" colspan="2">																			
									<a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo
									</a>
									</td>
								</TR>
							</TABLE>
									</FIELDSET>
								</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>

				<TR>
					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Báo cáo tồn kho theo Miền &nbsp;&nbsp;
								
							</LEGEND>

							<TABLE class="" width="100%">
								
								<table align="left" id='myTable' >
								
									<caption align="left" >Tồn kho theo Miền (Đơn vị tính: sản phẩm)</caption>
								
									<thead>
										<tr>
											<th></th>
											<%  //In ra List địa bàn
												for(int i=0;i<=arrTenMien.length-1;i++)
												{
													if( arrTenMien[i]!=null){
													%>
													<th><%= arrTenMien[i] %></th>
											<% 		}
												}
											%>             
										</tr>
									</thead>
									<tbody>
									<%while (rs.next())
									{ %>
										<tr>
											<th><%=rs.getString("type") %></th>
											<%  
											for(int j=0;j<=arrIdMien.length-1;j++)
											{
												if( arrIdMien[j]!=null){
													%>
													<td><%= rs.getString(arrIdMien[j])%></td>
											<%
												}
											}%>
										</tr>	
								<%} %>            
									</tbody>
								</table>
							
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
</html>

<%
		 if(rs != null)
			rs.close(); 

		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}	
%>

<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="javax.naming.spi.DirStateFactory.Result"%>
<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@page import="geso.traphaco.erp.beans.tigia.*"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Formattable"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% ITigia tigia = (ITigia)session.getAttribute("tgBean"); %>

<% NumberFormat formatter=new DecimalFormat("#,###,###.####"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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
<SCRIPT language="javascript" type="text/javascript">
function DinhDangDonGia(num) 
{
	num = num.toString().replace(/\$|\,/g,'');
		
	//num = (Math.round( num * 1000 ) / 1000).toString();
		
	var sole = '';
	if(num.indexOf(".") >= 0)
	{
		sole = num.substring(num.indexOf('.'));
	}
		
	if(isNaN(num))
		num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100);
	num = Math.floor(num/100).toString();
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

	var kq;
	if(sole.length >= 0)
	{
		kq = (((sign)?'':'-') + num) + sole;
	}
	else
		kq = (((sign)?'':'-') + num);
		
		//alert(kq);
	return kq;
}

function DinhDangTien(num) 
{
   num = num.toString().replace(/\$|\,/g,'');
   if(isNaN(num))
   num = "0";
   sign = (num == (num = Math.abs(num)));
   num = Math.floor(num*100+0.50000000001);
   num = Math.floor(num/100).toString();
   for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
   num = num.substring(0,num.length-(4*i+3))+','+
   num.substring(num.length-(4*i+3));
   return (((sign)?'':'-') + num);
}
function Dinhdangvat(element)
{
	element.value=DinhDangDonGia(element.value);
	if(element.value== '' ||element.value=='0' )
	{
		element.value= '';
	}
	
}
function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
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
function goBack() {
    window.history.back();
} 
 function saveform()
 {
	 var tiente = document.getElementById('tiente');
	 var tygia= document.getElementById('tygia');
	var TuNgay= document.getElementById('TuNgay');
	var DenNgay= document.getElementById('DenNgay');
	var SoLuongGoc= document.getElementById('SoLuongGoc');
	
	
	 if(SoLuongGoc.value == "")
	 {
		 alert("Vui lòng nhập vào số lượng gốc");
		 SoLuongGoc.focus();
		 return;
	 }
	 
	 if(tygia.value == "")
	 {
		 alert("Vui lòng nhập vào tỉ giá qui đổi");
		 tygia.focus();
		 return;
	 }
	 
	 if(TuNgay.value == "")
	 {
		 alert("Vui lòng chọn ngày bắt đầu hiệu lực");
		 TuNgay.focus();
		 return;
	 }
	 if(DenNgay.value == "")
	 {
		 alert("Vui lòng chọn ngày kết thúc hiệu lực");
		 DenNgay.focus();
		 return;
	 }
	
	
	 
	
	 document.forms['tigiaForm'].action.value='save';
     document.forms['tigiaForm'].submit();
 }
 </SCRIPT>
</head>
<body>
	<form name="tigiaForm" method="post" action="../../TigiaUpdateSvl">
	<input type="hidden" name="action" value=''>
	<input type="hidden" name="id" value='<%=tigia.getId()%>'>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<TABLE width="100%">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Mua hàng > Tỉ giá > Cập nhật</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>
										</TD>
									</tr>
									<tr>
										<TD align="left" height="5" colspan="4" class=""></td>
									</tr>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A href="javascript:goBack();"><img src="../images/Back30.png" alt="Quay ve"
												title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A href="javascript: saveform()"><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai"
												border="1" style="border-style: outset"></A></TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width=100% cellpadding="3" cellspacing="0" border="0">
						<TR>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
									<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%= tigia.getMessage() %></textarea>
								</FIELDSET>
							</TD>
						</TR>
					
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông tin tỷ giá </LEGEND>
									<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
																		
										<TR>
											<TD width="20%" class="plainlabel">Mã tiền tệ<FONT class="erroralert">*</FONT></TD>
											<TD width="80%" class="plainlabel"> <input type="text" value="<%=tigia.getMaTienTe() %> " name="MaTienTe" id="MaTienTe" readonly="readonly">
										</TD>
										</TR>
										
										<TR>
											<TD width="20%" class="plainlabel">Tên tiền tệ<FONT class="erroralert">*</FONT></TD>
											<TD width="80%" class="plainlabel"> <input type="text" value="<%=tigia.getTenTienTe() %> " name="TenTienTe" id="TenTienTe" readonly="readonly">
											
										</TR>
										
										
										<TR>
											<TD width="20%" class="plainlabel">Số lượng gốc<FONT class="erroralert">*</FONT></TD>
											<TD width="80%" class="plainlabel">
												<input type="text" value="<%=tigia.getSoLuongGoc() %>" name="SoLuongGoc" id="SoLuongGoc">
											</TD>
										</TR>
										
										
										<TR>
											<TD class="plainlabel">Tỉ giá quy đổi<FONT class="erroralert">*</FONT></TD>
											<%
									  	String tg ="";
									  if(tigia.getTiGiaQuyDoi().length()>0)
									  {
										  tg = formatter.format(Double.parseDouble(tigia.getTiGiaQuyDoi())); 
									  }
									  %>
											<TD class="plainlabel"><input type="text" class="txtsearch" id="tygia" name="TiGiaQuyDoi" onKeyPress = "return keypress(event);" onkeyup="Dinhdangvat(this)" size="20"
												value="<%=  tg %>" onchange="Submitform()"></TD>
										</TR>
									
									
										<TR>
											<TD class="plainlabel">Từ ngày<FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><input type="text" class="days" id="TuNgay" name="TuNgay" size="10" value="<%= tigia.getTuNgay()%>"
												onchange="Submitform()" readonly="readonly"></TD>
										</TR>
										<TR>
											<TD class="plainlabel">Đến ngày<FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><input type="text" class="days" id="DenNgay" name="DenNgay" size="10" value="<%= tigia.getDenNgay()%>"
												onchange="Submitform()" readonly="readonly"></TD>
										</TR>
											<TR>
											<TD width="15%" class="plainlabel"><label> <%  if (tigia.getTrangThai().equals("1")){%> <input name="TrangThai" type="checkbox"
													value="1" checked> <%} else {%> <input name="TrangThai" type="checkbox" value="0"> <%} %> Hoạt động
											</label></TD>
											<TD class="plainlabel">&nbsp;</TD>
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
</body>
</html>
<% 
	tigia.closeDB(); 
}
%>

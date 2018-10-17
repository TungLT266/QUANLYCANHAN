<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.mucchietkhau.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IChietkhau ckBean = (IChietkhau)session.getAttribute("ckBean"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<HEAD>
<TITLE>Acecook - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout(){
    if(confirm("Ban co muon dang xuat?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 {
     document.forms['ckForm'].submit();
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
 function saveform()
 {
	 
 	 document.forms['ckForm'].action.value= 'save';
     document.forms['ckForm'].submit();
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
 function Dinhdang(element)
	{
		element.value=DinhDangTien(element.value);
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
	} 
</SCRIPT>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name='ckForm' method="post" action="../../ChietkhauUpdateSvl">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= ckBean.getId() %>'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Dữ liệu nền > Kinh doanh > Công nợ đối tác > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href="../../ChietkhauSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset"></A>
        <A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border = "1"  style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  rows="1" readonly="readonly" style ="width:100%%"><%= ckBean.getMsg() %></textarea>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Thông tin công nợ </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
				<TR>
				  <TD width="120px" class="plainlabel" >Loại</TD>
				  <TD class="plainlabel" >
				  	<INPUT name="loai" id="loai" type="text" value='<%= ckBean.getLoai() %>' readonly="readonly" ></TD>
			  	</TR>
			  	<TR>
				  <TD class="plainlabel" >Diễn giải</TD>
				  <TD class="plainlabel" >
				  	<INPUT name="diengiai" type="text" value='<%= ckBean.getDiengiai() %>' style="width: 600px" ></TD>
			  	</TR>
			  	
			  	<% if(ckBean.getLoai().equals("LOAI1")) { %>
				  	<TR>
					  <TD class="plainlabel" >PT doanh thu</TD>
					  <TD class="plainlabel" >
					  	<INPUT name="ptDoanhthu" type="text" value='<%= ckBean.getPhantramDS() %>' onkeypress="return keypress(event);" ></TD>
				  	</TR>
				  	<TR>
					  <TD class="plainlabel" >Giới hạn</TD>
					  <TD class="plainlabel" >
					  	<INPUT name="gioihan" type="text" value='<%= ckBean.getGioihan() %>' onkeypress="return keypress(event);" ></TD>
				  	</TR>
			  	<% } %>
			  	
			  	<% if(ckBean.getLoai().equals("LOAI2")) { %>
				  	<TR>
					  <TD class="plainlabel" >Tổng số tiền</TD>
					  <TD class="plainlabel" >
					  	<INPUT name="tongsotien" type="text" value='<%= ckBean.getTongsotien() %>' onkeypress="return keypress(event);" ></TD>
				  	</TR>
			  	<% } %>
			  	
			  	<% if(ckBean.getLoai().equals("LOAI3")) { %>
				  	<TR>
					  <TD class="plainlabel" >Hạn mức nợ</TD>
					  <TD class="plainlabel" >
					  	<INPUT name="hanmucno" type="text" value='<%= ckBean.getHanmucno() %>' onkeypress="return keypress(event);" ></TD>
				  	</TR>
				  	<TR>
					  <TD class="plainlabel" >Số ngày nợ</TD>
					  <TD class="plainlabel" >
					  	<INPUT name="songayno" type="text" value='<%= ckBean.getSongayno() %>' onkeypress="return keypress(event);" ></TD>
				  	</TR>
				  	<TR>
					  <TD class="plainlabel" >Số tiền nợ</TD>
					  <TD class="plainlabel" >
					  	<INPUT name="sotienno" type="text" value='<%= ckBean.getSotien() %>' onkeypress="return keypress(event);" ></TD>
				  	</TR>
			  	<% } %>
			  	
			</TABLE>
            </div>      
     </fieldset>	
    </div>
</div>
</form>
</BODY>
</html>
<% ckBean.DBClose();
}%>
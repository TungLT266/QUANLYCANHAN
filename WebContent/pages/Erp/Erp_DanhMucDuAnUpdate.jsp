<%-- <%@page import="geso.dms.center.util.Utility"%> --%>
<%@ page import="geso.traphaco.center.util.Utility"%>
<%-- <%@page import="geso.dms.center.util.DinhKhoanKeToan"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.danhmucduan.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<!-- geso.traphaco.center.util.DinhKhoanKeToan -->
<%@ page import = "geso.traphaco.center.util.DinhKhoanKeToan" %>
<%@ page import = "geso.traphaco.center.util.IDinhKhoanKeToan" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	System.out.println("");
	Erp_DanhMucDuAn obj = (Erp_DanhMucDuAn)session.getAttribute("obj");
	String action = (String)session.getAttribute("action");
	int[] quyen = new  int[5];
	int[] quyen1 = new  int[5];
	/* //quyen = util.Getquyen("57",userId); */
	//nếu có quyền sửa duyệt mua hàng thì được phép ghõ mã chi phí.
	quyen1 = util.Getquyen("58",userId);
	
%>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>   
<%	NumberFormat formatter1 = new DecimalFormat("#,###,###.###"); %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
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
	z-index: 100;
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
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
}
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();});
</script>
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
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spXuatDungVatTuList.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

<script language="javascript" type="text/javascript">
	function FormatNumber(e)
	{
		e.value = DinhDangDonGia(e.value);
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
		 
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
		 var ma = document.getElementById("ma");
		 var ten = document.getElementById("ten");
		 var error = document.getElementById("msg");
		 
		 if(ma.value == "")
		 {
			 ma.focus();
			 error.value = 'Vui lòng nhập "Mã"!';
			 return;
		 }
		 
		 if(ten.value == "")
		 {
			 ten.focus();
			 error.value = 'Vui lòng nhập "Tên"!';
			 return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
// 	 	 document.forms['dmhForm'].action.value='new';
	     document.forms['dmhForm'].submit();
	 }
	 
	 function TinhTien()
	 {
		var soluong = document.getElementsByName("soLuong");
		var dongia = document.getElementsByName("donGia");
		var thanhtien = document.getElementsByName("thanhTien");	
		var tongTien = 0;
		
		var tongluong = 0;
		
		for(var index =0;index< soluong.length; index++)
		{
			if(dongia.item(index).value!="")
				dongia.item(index).value = DinhDangDonGia(parseFloat(dongia.item(index).value.replace(/,/g,"")));				
			if(soluong.item(index).value!="")
			{
				try{
					soluong.item(index).value = DinhDangDonGia(parseFloat(soluong.item(index).value.replace(/,/g,"")));
					tongluong += parseFloat(soluong.item(index).value.replace(/,/g,"")) ;
					thanhtien.item(index).value = DinhDangDonGia(parseFloat(soluong.item(index).value.replace(/,/g,"")) * parseFloat(dongia.item(index).value.replace(/,/g,"")));
					tongTien += parseFloat(soluong.item(index).value.replace(/,/g,"")) * parseFloat(dongia.item(index).value.replace(/,/g,""));
				}catch (e)
				{
					console.log("Error is: " + e);
				}
			}
		}
		document.getElementById("tongLuong").value = tongluong;
		document.getElementById("tongTien").value = DinhDangDonGia(tongTien);
// 		console.log("tongTien: " + tongTien + "\n");
// 		tongtien = parseFloat(tongtien) + parseFloat(document.getElementById("cpKhac").value.replace(/,/g,""));
	}
	 
	 function submit()
	 { 		
		
		 document.forms['dmhForm'].action.value='submit';
	     document.forms["dmhForm"].submit();
	 }
	 
	 function goBack()
	 {
	  	window.history.back();
	 }

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" action="../../Erp_DanhMucDuAnUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="xoaKhauHao" id="xoaKhauHao" value='false'> 
		<input type="hidden" name="action" value="<%=action %>" />
		<input type="hidden" name="id" value="<%= obj.getId() %>" />
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
			<%if (action.trim().equals("display")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Dữ liệu nền &gt; Kế toán &gt; Hiển thị</div>
			<%}else if (action.trim().equals("update")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Dữ liệu nền &gt; Kế toán &gt; Cập nhật</div>
			<%}else{ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Dữ liệu nền &gt; Kế toán &gt; Tạo mới</div>
			<%} %>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="javascript:goBack();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> <span id="btnSave">
				<%if (!action.trim().equals("display")) {%> 
				<A href="javascript:saveform();"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A>
				<%} %>
				</span>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="msg" rows="1" readonly="readonly" style="width: 100%%"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Danh mục dự án</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
						
							<TR>
								<TD class="plainlabel" style="width: 20%;">Mã</TD>
								<TD class="plainlabel" style="width: 80%;">
									<input type="text" id="ma" name="ma" style="width: 400px;"
											value="<%= obj.getMa()%>" maxlength="99" />
								</TD>	
							</TR>
							
							<TR>
								<TD class="plainlabel" style="width: 20%;">Tên</TD>
								<TD class="plainlabel" style="width: 80%;">
									<input type="text" id="ten" name="ten" style="width: 400px;"
											value="<%= obj.getTen()%>" maxlength="99" />
								</TD>	
							</TR>
						</TABLE>
						<hr>
					</div>
				</fieldset>
			</div>
		</div>
</form>
</BODY>
</html>
<% } %>
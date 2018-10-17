<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.kehoachkinhdoanh.*"%>
<%@ page import="geso.traphaco.erp.beans.kehoachkinhdoanh.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%
	IErpKehoachkinhdoanh dmhBean = (IErpKehoachkinhdoanh) session.getAttribute("dmhBean");
	List<IKenhbanhang> kbhListMB = dmhBean.getKbhListMB();
	List<IKenhbanhang> kbhListMN = dmhBean.getKbhListMN();
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	
	int[] quyen = new  int[5];
	Utility util = (Utility) session.getAttribute("util");
	quyen = util.Getquyen("","58",userId);
	
%>
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
	height: 97%;
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

#ajax_listOfOptions div {re
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

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script language="javascript" type="text/javascript">
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
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
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
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dmhForm'].action.value='save';
	     document.forms['dmhForm'].submit();
	 }

	 function submitform()
	 { 		
		 document.forms['dmhForm'].action.value='submit';
	     document.forms["dmhForm"].submit();
	 }
	 
	 function goBack()
	 {
	  	window.history.back();
	 }
	
	function Xoafile()
	 {
	 	var xmlhttp;
	 	if (window.XMLHttpRequest)
	 	{// code for IE7+, Firefox, Chrome, Opera, Safari
	 	   xmlhttp = new XMLHttpRequest();
	 	}
	 	else
	 	{
	 	   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	 	}
	 	xmlhttp.onreadystatechange=function()
	 	{
	 	   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	 	   {
	 		   if(xmlhttp.responseText=='')
	 			   {
	 	       			document.getElementById('tenfilea').innerHTML = xmlhttp.responseText;
	 	       			document.getElementById("valueten").value='0';
	 	       			alert('Đã xóa thành công');
	 			   }
	 		   else
	 			   {
	 			   alert(xmlhttp.responseText);
	 			   }
	 	   }
	 	}
	 	if(confirm("Bạn muốn xóa file đính kèm"))
	 	{
	 		xmlhttp.open("GET","../../ErpDenghimuahangUpdateSvl?task=" + "xoafilenew" + "&id=" + document.getElementById('valueten').value,true);
	 		xmlhttp.send();
	 	}
	 	else
	 		return false;
	 }
	function upload()
	{
		   if(document.forms["dmhForm"].filename.value=="")
		   {
			   document.forms["dmhForm"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
		   }else
		   {
			 document.forms["dmhForm"].setAttribute('enctype', "multipart/form-data", 0);
			 document.forms["dmhForm"].submit();	
		   }
	}
</script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>
<script>
	$(document).ready(function() {

	    //When page loads...
	    $(".tab_content").hide(); //Hide all content
	    var index = $("ul.tabs li.current").show().index(); 
	    $(".tab_content").eq(index).show();
	    //On Click Event
	    $("ul.tabs li").click(function() {
	  	
	        $("ul.tabs li").removeClass("current"); //Remove any "active" class
	        $(this).addClass("current"); //Add "active" class to selected tab
	        $(".tab_content").hide(); //Hide all tab content 
	        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
	        $(activeTab).show(); //Fade in the active ID content
	        return false;
	    });

	});
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" action="../../ErpKehoachkinhdoanhUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="id" value='<%=dmhBean.getId()%>'>
		<input type="hidden" name="loai" id="loai" value='<%=dmhBean.getLoai()%>'> 
		<input type="hidden" name="action" value="1" />
		
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
			<%if(dmhBean.getLoai().equals("1")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation"> Quản lý mua hàng > Mua hàng trong nước > Kế hoạch kinh doanh > Tạo mới</div>
			<%}else{ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation"> Quản lý mua hàng > Mua hàng nhập khẩu > Kế hoạch kinh doanh > Tạo mới</div>
			<%}%>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="javascript:goBack();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> <span id="btnSave"> <A href="javascript:saveform()"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A>
				</span>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="Msg" rows="1" readonly="readonly" style="width: 100%"><%=dmhBean.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Kế hoạch kinh doanh</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD class="plainlabel" width="150px"> Diễn giải</TD>
								<TD class="plainlabel" >
									<input type="text" id="diengiai" name="diengiai" 
											value="<%= dmhBean.getDiengiai() %>" maxlength="10"  />
								</TD>
							</TR>
							<tr class="plainlabel">
						  	  	<TD class="plainlabel" width="150px"> Kế hoạch
						  	  	</td> 
						  	  	<TD class="plainlabel" >
						  	  		<INPUT type="file" name="filename" size="40" value=''>
						  			&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:upload()">
									<img style="top: -4px;" src="../images/button.png" alt="">UpLoad File</a>							
						  	 	</td>
						  	</tr>
							<TR >
						</TABLE>
						<hr>
					</div>
				</fieldset>
			</div>					
			<div align="left" style="width: 100%; float: none; clear: none;">
				<fieldset>
					<legend class="legendtitle"> Miền Bắc</legend>
					<div style="float: none; width: 100%" align="left">
					
					<ul class="tabs">
		            <%  
					if(kbhListMB.size() > 0) { 
						for(int i = 0; i < kbhListMB.size(); i++) { 
							IKenhbanhang kbh = kbhListMB.get(i);
							if(i==0){%>      			
         			<li class='current' ><a href="#tabKbh<%=i%>">Kênh <%= kbh.getTenkenh() %> </a>
         			<input value=" <%=kbh.getId() %> " name= "kenhbanhang" type='hidden' >
         			<input value=" <%=kbh.getTenkenh() %> " name= "tenkbh" type='hidden' ></li>
         			<% }else{ %>
         			<li ><a href="#tabKbh<%=i%>">Kênh <%= kbh.getTenkenh() %> </a>
         			<input value=" <%=kbh.getId() %> " name= "kenhbanhang" type='hidden' >
         			<input value=" <%=kbh.getTenkenh() %> " name= "tenkbh" type='hidden' ></li>
         			<%}}} %>
		          	</ul>
		            
		            <div class="panes">
		            <% int m = 0; 
					if(kbhListMB.size() > 0) { 
						for(int i = 0; i < kbhListMB.size(); i++) { 
							IKenhbanhang kbh = kbhListMB.get(i);%>  										
					<div id="tabKbh<%=i %>" class="tab_content">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1">
							<TR class="tbheader">
								
								<TH align="center" width="7%">Mã sản phẩm</TH>
								<TH align="center" width="10%">Tên sản phẩm</TH>
								<TH align="center" width="5%"> Tháng 1</TH>
								<TH align="center" width="5%"> Tháng 2</TH>
								<TH align="center" width="5%"> Tháng 3</TH>
								<TH align="center" width="5%"> Tháng 4</TH>
								<TH align="center" width="5%"> Tháng 5</TH>
								<TH align="center" width="5%"> Tháng 6</TH>
								<TH align="center" width="5%"> Tháng 7</TH>
								<TH align="center" width="5%"> Tháng 8</TH>
								<TH align="center" width="5%"> Tháng 9</TH>
								<TH align="center" width="5%"> Tháng 10</TH>
								<TH align="center" width="5%"> Tháng 11</TH>
								<TH align="center" width="5%"> Tháng 12</TH>
								
							</TR>
							<% int n = 0;
								List<ISanpham> spList =  kbh.getSanpham();
								if(spList.size() > 0) { 
									for(int j = 0; j < spList.size(); j++) { 
										ISanpham sp = spList.get(j);
										%>
										<tr>
											<TD align="center" >
												<input type="hidden" value="<%= sp.getId() %>" name="idsp<%=i %>" style="width: 97%" readonly="readonly" > 
												<input value="<%= sp.getMasanpham() %>" name="masp<%=i %>" style="width: 97%" readonly="readonly" > 
											</TD>
											<TD align="center" > 
												<input value="<%= sp.getTensanpham() %>" name="tensp<%=i %>" style="width: 97%" readonly="readonly" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang1() %>" name="thang1<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang2() %>" name="thang2<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang3() %>" name="thang3<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang4() %>" name="thang4<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang5() %>" name="thang5<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang6() %>" name="thang6<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang7() %>" name="thang7<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang8() %>" name="thang8<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang9() %>" name="thang9<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang10() %>" name="thang10<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang11() %>" name="thang11<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang12() %>" name="thang12<%=i %>" style="width: 97%; text-align: right;" > 
											</TD>
										</tr>
										
									<% }
								} %>
							<tr class="tbfooter">
								<td colspan="14">&nbsp;</td>
							</tr>
						</TABLE>
					</div>
					<%} %>
					</div>	
					<% } %>
            	</div>
            
				</fieldset>
			</div>	
			<div align="left" style="width: 100%; float: none; clear: none;">
				<fieldset>
					<legend class="legendtitle"> Miền Nam</legend>
					<div style="float: none; width: 100%" align="left">
					
					<ul class="tabs">
		            <%  
					if(kbhListMN.size() > 0) { 
						for(int i = 0; i < kbhListMN.size(); i++) { 
							IKenhbanhang kbh = kbhListMN.get(i);%>
					
         			<li ><a href="#tabKbh<%=i+5%>">Kênh <%= kbh.getTenkenh() %> </a>
         			<input value=" <%=kbh.getId() %> " name= "kenhbanhang" type='hidden' >
         			<input value=" <%=kbh.getTenkenh() %> " name= "tenkbh" type='hidden' ></li></li>
         			
         			<%}} %>
		          	</ul>
		            
		            <div class="panes">
		            <%  
					if(kbhListMN.size() > 0) { 
						for(int i = 0; i < kbhListMN.size(); i++) { 
							IKenhbanhang kbh = kbhListMN.get(i);%>  										
					<div id="tabKbh<%=i+5 %>" class="tab_content">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1">
							<TR class="tbheader">
								
								<TH align="center" width="7%" >Mã sản phẩm</TH>
								<TH align="center" width="10%">Tên sản phẩm</TH>
								<TH align="center" width="5%"> Tháng 1</TH>
								<TH align="center" width="5%"> Tháng 2</TH>
								<TH align="center" width="5%"> Tháng 3</TH>
								<TH align="center" width="5%"> Tháng 4</TH>
								<TH align="center" width="5%"> Tháng 5</TH>
								<TH align="center" width="5%"> Tháng 6</TH>
								<TH align="center" width="5%"> Tháng 7</TH>
								<TH align="center" width="5%"> Tháng 8</TH>
								<TH align="center" width="5%"> Tháng 9</TH>
								<TH align="center" width="5%"> Tháng 10</TH>
								<TH align="center" width="5%"> Tháng 11</TH>
								<TH align="center" width="5%"> Tháng 12</TH>
								
							</TR>
							<% int n = 0;
								List<ISanpham> spList =  kbh.getSanpham();
								if(spList.size() > 0) { 
									for(int j = 0; j < spList.size(); j++) { 
										ISanpham sp = spList.get(j);
										%>
										<tr>
											<TD align="center" >
												<input type="hidden" value="<%= sp.getId() %>" name="idsp<%=i+5 %>" style="width: 97%" readonly="readonly" > 
												<input value="<%= sp.getMasanpham() %>" name="masp<%=i+5 %>" style="width: 97%" readonly="readonly" > 
											</TD>
											<TD align="center" > 
												<input value="<%= sp.getTensanpham() %>" name="tensp<%=i+5 %>" style="width: 97%" readonly="readonly" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang1() %>" name="thang1<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang2() %>" name="thang2<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang3() %>" name="thang3<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang4() %>" name="thang4<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang5() %>" name="thang5<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang6() %>" name="thang6<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang7() %>" name="thang7<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang8() %>" name="thang8<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang9() %>" name="thang9<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang10() %>" name="thang10<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang11() %>" name="thang11<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
											<TD align="center" >
												<input type="text" value="<%= sp.getThang12() %>" name="thang12<%=i+5 %>" style="width: 97%; text-align: right;" > 
											</TD>
										</tr>
										
									<% }
								} %>
							<tr class="tbfooter">
								<td colspan="14">&nbsp;</td>
							</tr>
						</TABLE>
					</div>
					<%} %>
					</div>	
					<% } %>
            	</div>
            
				</fieldset>
			</div>	
		</div>


	<%
		if(kbhListMB!=null){
			kbhListMB.clear();
		}
		if(kbhListMN!=null){
			kbhListMN.clear();
		}
 		session.setAttribute("dmhBean",null);
		dmhBean.close();
	%>
</form>
</BODY>
</html>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.phanbomuahang.*"%>
<%@ page import="geso.traphaco.erp.beans.phanbomuahang.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%
	IErpPhanbomuahang timnccBean = (IErpPhanbomuahang) session.getAttribute("timnccBean");
	ResultSet dnmhList = (ResultSet)timnccBean.getDnmhRs();
	List<ISanpham> spList = (List<ISanpham>)timnccBean.getSpList(); 
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	ResultSet ctRs = (ResultSet)timnccBean.getCtRs();
	//nếu có quyền sửa duyệt mua hàng thì được phép ghõ mã chi phí.
	int[] quyen = new  int[5];
	Utility util = (Utility) session.getAttribute("util");
	//quyen = util.Getquyen("","58",userId);
	quyen = util.Getquyen("ErpPhanbomuahangUpdateSvl","",userId);
	
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

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

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
		 var idsp = document.getElementsByName("idsp");
		 var masp = document.getElementsByName("masp");
		 var solo = document.getElementsByName("solo");
		 var soluong = document.getElementsByName("soluong");

		 var i;
		 var kt = 1;
		 for(i = 0; i < idsp.length; i++)
		 {
			var soluongpb = document.getElementsByName("soluongpb"+i);
			var tong = 0;
			for(j = 0; j < soluongpb.length; j++)
			{
				tong = tong + parseInt(soluongpb.item(j).value);
			}
			if(tong > soluong.item(i).value)
			{
				kt = 0;
				alert("Không thể phân bổ vượt quá số lượng tối đa: "+masp.item(i).value+" - số lô "+solo.item(i).value);
			}
		 }
		 
		 if(kt == 1)
		 {
		 	document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 	document.forms['timnccForm'].action.value='save';
	     	document.forms['timnccForm'].submit();
		 }
	 }

	 function submitform()
	 { 		
		 //var active = $( "#accordion" ).accordion( "option", "active" );
		 document.forms['timnccForm'].action.value='submit';
	     document.forms["timnccForm"].submit();
	 }

	 function goBack()
	 {
	  	window.history.back();
	 }
	 
	
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="timnccForm" method="post" action="../../ErpPhanbomuahangUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="1" />
		
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation"> Quản lý mua hàng > Mua hàng trong nước > Phân bổ cho Pha Nam MTV > Tạo mới</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="javascript:goBack();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> 
				<span id="btnSave"> 
					<A href="javascript:saveform();"> 
						<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset">
					</A>
				</span>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="Msg" rows="1" readonly="readonly" style="width: 100%"><%=timnccBean.getMessage()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Phân bổ</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
							<TD class="plainlabel" valign="top" width="150px"> Số PO</TD>
								<TD class="plainlabel" valign="top"  >
								<select name="dmhId" onchange="submitform()">
                            	<OPTION value="" selected></OPTION>
									<% try{while(dnmhList.next()){ 
								    	if(dnmhList.getString("pk_seq").equals(timnccBean.getDnmhId())){ %>
								      		<option value='<%= dnmhList.getString("pk_seq") %>' selected><%=dnmhList.getString("sopo") %></option>
								      	<%}else{ %>
								     		<option value='<%= dnmhList.getString("pk_seq") %>'><%= dnmhList.getString("sopo") %></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
                            	</select>
								</TD>
							</TR>
							<tr>
								<TD class="plainlabel">Diễn giải</TD>
								<TD class="plainlabel">
									<INPUT type="text" name="diengiai" style="width: 200px" value='<%=timnccBean.getDiengiai()%>'>
									<INPUT name="vat" style="width: 200px" type='hidden' value='<%=timnccBean.getVat()%>'>
									<INPUT name="dungsai" style="width: 200px" type='hidden' value='<%=timnccBean.getDungsai()%>'>
									<INPUT name="ngaymua" style="width: 200px" type='hidden' value='<%=timnccBean.getNgaymua()%>'>
									<INPUT name="dvth" style="width: 200px" type='hidden' value='<%=timnccBean.getDvth()%>'>
									<INPUT name="ncc" style="width: 200px" type='hidden' value='<%=timnccBean.getNcc()%>'>
								</TD>
							</tr>
						</TABLE>
						<hr>
					</div>
					<div >
					
			
                <TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                        <TH align="center" width="10%"> Mã sản phẩm</TH>
                        <TH align="left" width="20%"> Tên sản phẩm </TH>
                        <TH align="center" width="10%"> Số lô</TH>
                        <TH align="center" width="10%"> Số lượng tổng</TH>
                        <%
	                    if(ctRs != null)
						{
	                    	int m = 0;
							while (ctRs.next()){%>
	                        <TH align="center" width="10%"> <%= ctRs.getString("ten") %></TH>
	                        <%m++;}}%>
                        
                    </TR>
                    <%
					if(spList != null)
					{
						int size = spList.size();
						int n = 0;
						while (n < size){
						ISanpham spdetail = spList.get(n);%>
                    <%if((n % 2 ) == 0) { %>
                     	<TR class='tbdarkrow'   >
                    <%}else{ %>
                      	<TR class='tblightrow'  >
                    <%} %>
		                    <td class="plainlabel">	
								<input value=" <%=spdetail.getId() %> " name= "idsp" type='hidden' >
								<input name= "masp" type="text" value="<%=spdetail.getMasanpham() %>" readonly style="width:100%">
							</td>
							<td class="plainlabel"><input name= "tensp" type="text" value="<%=spdetail.getTensanpham() %>" readonly style="width:100%"></td>
							<td class="plainlabel"><input name= "donvi" type="hidden" value="<%=spdetail.getDonvi() %>" readonly style="width:100%">
								<input name= "solo" type="text" value="<%=spdetail.getSolo() %>" readonly style="width:100%">
							</td>
							<td class="plainlabel">
								<input name= "soluong" type="text" value="<%=spdetail.getSoluong() %>" readonly style="width:100%">
								<input name= "dongia" value="<%=spdetail.getDongia() %>" type='hidden' style="width:100%">
              					<input name= "nhomkenh" value="<%=spdetail.getNhomkenh() %>" type = 'hidden' style="width:100%">
              					<input name= "thuexuat" value="<%=spdetail.getThuexuat() %>" type = 'hidden' style="width:100%">
							</td>
		                    <%
		                    List<ICongty> ctList = spdetail.getCongty();
		                    if(ctList != null)
							{
		                		int m = 0;
								while (m < ctList.size()){
								ICongty ctdetail = ctList.get(m);%>
              				<td class="plainlabel">
              					<input name= "soluongpb<%= n %>" type="text" value="<%=ctdetail.getSoluongpb() %>" style="width:100%">
								<input name= "idct<%= n %>" value="<%=ctdetail.getId() %>" type='hidden' style="width:100%">
              					<input name= "mact<%= n %>" value="<%=ctdetail.getMacongty() %>" type = 'hidden' style="width:100%">
              					<input name= "tenct<%= n %>" value="<%=ctdetail.getTencongty() %>" type='hidden' style="width:100%">
              				</td>
						
							<%m++;}} %>
					</tr>
					<%n++;}} %>
                </TABLE>
                
                </div>
			
				
			</fieldset>
			</div>
				
		</div>


	<%
		timnccBean.close();
	%>
</form>
</BODY>
</html>
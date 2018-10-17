<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.timnhacc.*"%>
<%@ page import="geso.traphaco.erp.beans.timnhacc.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%
	ITimnhacc timnccBean = (ITimnhacc) session.getAttribute("timnccBean");
	ResultSet dnmhList = (ResultSet)timnccBean.getDnmhRs();
	List<ISanpham> spList = (List<ISanpham>)timnccBean.getSpList();
	ResultSet nccRs = (ResultSet)timnccBean.getNccRs();
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	
	//nếu có quyền sửa duyệt mua hàng thì được phép ghõ mã chi phí.
	int[] quyen = new  int[5];
	Utility util = (Utility) session.getAttribute("util");
	//quyen = util.Getquyen("","58",userId);
	quyen = util.Getquyen("TimnhaccUpdateSvl","",userId);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Phanam - Project</TITLE>
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
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
<script>
	  $(document).ready(function() {
			$("#accordion").accordion({autoHeight: false}); //autoHeight content set false
			$( "#accordion" ).accordion( "option", "icons", { 'header': 'ui-icon-plus', 'headerSelected': 'ui-icon-minus' } );
			$( "#accordion" ).accordion({ active: <%= timnccBean.getActive() %> });
	  });
  </script>
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
// 		 var dnmhId = document.getElementById("dnmhId");
// 		 var error = document.getElementById("Msg");
		 
// 		 if(dnmhId.value == "")
// 		 {
// 			 alert(dnmhId);
// 			 error.value="Vui lòng chọn đề nghị mua hàng";
// 			 dnmhId.focus();
// 			 return;
// 		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['timnccForm'].action.value='save';
	     document.forms['timnccForm'].submit();
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
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script>
    $(document).ready(function() { 
							
      	 $(".ajax").colorbox();
       	 	
	}); 
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="timnccForm" method="post" action="../../TimnhaccUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="1" />
		<input type="hidden" name="loaihh" value='<%=timnccBean.getLoaihh()%>'>
		<input type="hidden" name="nguongochh" value='<%=timnccBean.getNguongochh()%>'>  
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation"> Quản lý mua hàng > Mua VT/ CPDV/ TSCĐ/ CCDC/ > Tìm nhà cung cấp > Tạo mới</div>
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
					<legend class="legendtitle"> Tìm nhà phân phối</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
							<TD class="plainlabel" valign="top" width="200px"> Mã đề nghị mua hàng</TD>
								<TD class="plainlabel" valign="top"  >
								<select name="dnmhId" onchange="submitform()">
                            	<OPTION value="" selected></OPTION>
									<% try{while(dnmhList.next()){ 
								    	if(dnmhList.getString("pk_seq").equals(timnccBean.getDnmhId())){ %>
								      		<option value='<%= dnmhList.getString("pk_seq") %>' selected><%=dnmhList.getString("sodenghi") %></option>
								      	<%}else{ %>
								     		<option value='<%= dnmhList.getString("pk_seq") %>'><%= dnmhList.getString("sodenghi") %></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
                            	</select>
								</TD>
							</TR>
							<tr>
								<TD class="plainlabel">Diễn giải</TD>
								<TD class="plainlabel"><INPUT type="text" name="diengiai" style="width: 200px" value='<%=timnccBean.getDiengiai()%>'></TD>
								
							</tr>
							<tr>
								<TD class="plainlabel">Tìm theo đề nghị mua hàng</TD>
								<TD class="plainlabel">
								<% if(timnccBean.getHinhthuc().equals("1")){ %>
									<INPUT type="checkbox" name="hinhthuc" value='1' checked onchange="submitform()">
								<% }else{ %>
									<INPUT type="checkbox" name="hinhthuc" value='1' onchange="submitform()">
								<% } %>
								</TD>
								
							</tr>
						</TABLE>
						<hr>
					</div>
					<% if(timnccBean.getHinhthuc().equals("0")){ %>
					<div id="accordion" style="width:100%; height:auto; float:none; font-size:1.0em" align="left">
					<% 
					  if(spList != null)
					{
						int size = spList.size();
						int n = 0;
						while (n < size){
						ISanpham spdetail = spList.get(n);%>
			
                     
       			<h1 style="font-size:1.8em"><a href="#ncc<%=n %>"><%=spdetail.getTensanpham()  %> </a>
       			<input value=" <%=spdetail.getId() %> " name= "sanpham" type='hidden' >
       			<input value=" <%=spdetail.getTensanpham() %> " name= "tensanpham" type='hidden' >
       			<input value=" <%=spdetail.getDiengiai() %> " name= "diengiaisanpham" type='hidden' >
				</h1>
       			<div id="ncc<%=n %>" style="height:auto">
			
                <TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                        <TH align="center" width="10%"> Mã NCC</TH>
                        <TH align="left" width="50%"> Tên NCC </TH>
                        <TH align="center" width="10%"> Tổng lượng</TH>
                        <TH align="center" width="10%"> Đơn giá</TH>
                        <TH align="center" width="10%"> Chọn</TH>
                        <TH align="center" width="10%"> Đánh giá NCC</TH>
                    </TR>
                  <%--   <%List<INhacungcap> nccList = spdetail.getNhacungcap();
                    if(nccList != null)
					{
                    	int m = 0;
						while (m < nccList.size()){
							INhacungcap nccdetail = nccList.get(m);
							if((m % 2 ) == 0) { %>
	                         	<TR class='tbdarkrow'   >
	                        <%}else{ %>
	                          	<TR class='tblightrow'  >
	                        <%} %>
							<td class="plainlabel">
							
							<input value=" <%=nccdetail.getId() %> " name= "idncc<%=n %>" type='hidden' >
							<input name= "mancc<%= n %>" type="text" value="<%=nccdetail.getMa() %>" readonly style="width:100%"></td>
							<td class="plainlabel"><input name= "tenncc<%= n %>" type="text" value="<%=nccdetail.getTen() %>" readonly style="width:100%"></td>
							<td class="plainlabel"><input name= "tongluong<%= n %>" type="text" value="<%=nccdetail.getTongluong() %>" readonly style="width:100%"></td>
							<td class="plainlabel"><input name= "sopo<%= n %>" type="text" value="<%=nccdetail.getDongia() %>" readonly style="width:100%"></td>
							<td align="center">
              					<% if(nccdetail.getId().equals(nccdetail.getChon())) { %>
              						<input type="checkbox" name="chonncc<%= n %>" value="<%= nccdetail.getId() %>" checked="checked" >
              					<% } else { %>
              						<input type="checkbox" name="chonncc<%= n %>" value="<%= nccdetail.getId() %>"  >
              					<% } %>
              				</td>
              				<td align=center>
              				<a class='ajax' href="../../TimnhaccSvl?userId=<%=userId%>&type=ajax&spId=<%= spdetail.getId()%>&nccId=<%= nccdetail.getId() %>" title="Đánh giá nhà cung cấp" > 
								    			<img src="../images/vitriluu.png" height="16px" width="16px" title="Đánh giá nhà cung cấp" style="cursor:pointer;" /></a>
              				</td>
						</tr>
						<%m++;}
					} %> --%>
					
					
					 <%ResultSet nccList = spdetail.getRsNcc();
                    if(nccList != null)
					{
                    	int m = 0;
						while (nccList.next()){
							NumberFormat formatter = new DecimalFormat("#,###,###");
							String dongia = nccList.getString("dongia")==null?"":nccList.getString("dongia");
							String[] dgarr = dongia.trim().split(",");
							//System.out.println("dongia "+dongia+", dongia length " + dgarr.length);
							dongia = "";
							for (int i = 0; i < dgarr.length; i++) {
								if(dgarr[i].trim().length() > 0)
								{
									Double dg = Double.parseDouble(dgarr[i]);
									dongia += formatter.format(dg) + ", ";
								}
							}
							if (dongia.length() > 0)
							{
								dongia = dongia.substring(0, dongia.length() - 2);
							}
					 
							
							
							if((m % 2 ) == 0) { %>
	                         	<TR class='tbdarkrow'   >
	                        <%}else{ %>
	                          	<TR class='tblightrow'  >
	                        <%}  
	                        
	                        %>
							<td class="plainlabel">
							
							<input value=" <%=nccList.getString("pk_seq") %> " name= "idncc<%=n %>" type='hidden' >
							<input name= "mancc<%= n %>" type="text" value="<%=nccList.getString("ma")  %>" readonly style="width:100%"></td>
							<td class="plainlabel"><input name= "tenncc<%= n %>" type="text" value="<%=nccList.getString("ten")%>" readonly style="width:100%"></td>
							<td class="plainlabel"><input name= "tongluong<%= n %>" type="text" value="<%=nccList.getString("tongluong")%>" readonly style="width:100%"></td>
							<td class="plainlabel"><input name= "sopo<%= n %>" type="text" value="<%=dongia %>" readonly style="width:100%"></td>
							<td align="center">
								
              					<%-- <% if(nccList.getString("pk_seq") .equals(nccList.getString("ncc_fk"))) { %>
              						<input type="checkbox" name="chonncc<%= n %>" value="<%= nccList.getString("pk_seq")  %>" checked="checked" >
              					<% } else { %> --%>
              						<input type="checkbox" name="chonncc<%= n %>" value="<%= nccList.getString("pk_seq")  %>"  >
              					<%-- <% } %> --%>
              				</td>
              				<td align=center>
              				<a class='ajax' href="../../TimnhaccSvl?userId=<%=userId%>&type=ajax&spId=<%= spdetail.getId()%>&nccId=<%= nccList.getString("pk_seq") %>" title="Đánh giá nhà cung cấp" > 
								    			<img src="../images/vitriluu.png" height="16px" width="16px" title="Đánh giá nhà cung cấp" style="cursor:pointer;" /></a>
              				</td>
						</tr>
						<%m++;}
					} %>
					
                </TABLE>
                
                </div>
			
			<%n++;}} %>
			</div>
			<% }else{ %>
			<div >
			
                <TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                        <TH align="center" width="10%"> Mã NCC</TH>
                        <TH align="left" width="50%"> Tên NCC </TH>
                        <TH align="center" width="10%"> Chọn</TH>
                    </TR>
                    <%
                    if(nccRs != null)
					{
                    	int m = 0;
						while (nccRs.next()){
							if((m % 2 ) == 0) { %>
	                         	<TR class='tbdarkrow'   >
	                        <%}else{ %>
	                          	<TR class='tblightrow'  >
	                        <%} %>
							<td ><%=nccRs.getString("ma") %></td>
							<td ><%=nccRs.getString("ten") %></td>
							<td align="center">
              					<% if(timnccBean.getNccIds().indexOf(nccRs.getString("pk_seq")) >= 0) { %>
              						<input type="checkbox" name="nccid" value="<%= nccRs.getString("pk_seq") %>" checked="checked" >
              					<% } else { %>
              						<input type="checkbox" name="nccid" value="<%= nccRs.getString("pk_seq") %>"  >
              					<% } %>
              				</td>
              				</tr>
						<%m++;}
					} %>
				</TABLE>
			</div>
			<% } %>
				</fieldset>
			</div>
		</div>
<!-- <script type="text/javascript">
		var sp = document.getElementsByName("sanpham");
		var i = 0;
		while (i < sp.length){
			var ncc = document.getElementsByName("idncc"+i);
            var j = 0;
				while (j < ncc.length){
					dropdowncontent.init("noidungDgncc" + i + j, "left-bottom", 500, "click");
					j++;
				}
				i++;
			}

</script> -->


	<%
		timnccBean.close();
	%>
</form>
</BODY>
</html>
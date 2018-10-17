<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.danhgianhacc.*"%>
<%@ page import="geso.traphaco.erp.beans.danhgianhacc.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%
	Erp_Danhgiancc dgnccBean = (Erp_Danhgiancc) session.getAttribute("dgnccBean");
	ResultSet dvthList = (ResultSet)dgnccBean.getDvthRs();
	/* ResultSet spList = dgnccBean.getSpRs();  */
	ResultSet nccList = dgnccBean.getNccRs();
	ResultSet poList = dgnccBean.getPORs();
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	
	//nếu có quyền sửa duyệt mua hàng thì được phép ghõ mã chi phí.
	int[] quyen = new  int[5];
	Utility util = (Utility) session.getAttribute("util");
	//quyen = util.Getquyen("","58",userId);
	quyen = util.Getquyen("Erp_DanhgianccUpdateSvl","",userId);
	
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
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['timnccForm'].action.value='save';
	     document.forms['timnccForm'].submit();
	 }

	 function submitform()
	 { 		
		 document.forms['timnccForm'].action.value='submit';
	     document.forms["timnccForm"].submit();
	 }

	 function goBack()
	 {
	  	window.history.back();
	 }
	 
	
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="timnccForm" method="post" action="../../Erp_DanhgianccUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="1" />
		
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation"> Quản lý mua hàng > Nghiệp vụ khác > Đánh giá nhà cung cấp > Tạo mới</div>
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
					<textarea name="dataerror" id="Msg" rows="1" readonly="readonly" style="width: 100%"><%=dgnccBean.getMessage()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: center">
				<fieldset>
					<legend class="legendtitle"> Đánh giá nhà cung cấp</legend>
					<div style="float: none; width: 100%" align="center">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
							<TD class="plainlabel" valign="top" width="150px"> Nhà cung cấp</TD>
								<TD class="plainlabel" valign="top"  >
								<select name="nccId" onchange="submitform()">
                            	<OPTION value="" selected></OPTION>
									<% try{while(nccList.next()){ 
								    	if(nccList.getString("pk_seq").equals(dgnccBean.getNccId())){ %>
								      		<option value='<%= nccList.getString("pk_seq") %>' selected><%=nccList.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= nccList.getString("pk_seq") %>'><%= nccList.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
                            	</select>
								</TD>
							</TR>
							<tr>
								<TD class="plainlabel">Đơn vị thực hiện</TD>
								<TD class="plainlabel">
									<select name="dvthId" onchange="submitform()">
	                            	<OPTION value="" selected></OPTION>
										<% try{while(dvthList.next()){ 
									    	if(dvthList.getString("pk_seq").equals(dgnccBean.getDvthId())){ %>
									      		<option value='<%= dvthList.getString("pk_seq") %>' selected><%=dvthList.getString("ten") %></option>
									      	<%}else{ %>
									     		<option value='<%= dvthList.getString("pk_seq") %>'><%= dvthList.getString("ten") %></option>
									     	<%}}}catch(java.sql.SQLException e){} 
									     %>
	                            	</select>
								</TD>
								
							</tr>
							
							<!-- tạm thời không dùng -->
							<%-- <TR>
							<TD class="plainlabel" valign="top" width="150px"> Sản phẩm</TD>
								<TD class="plainlabel" valign="top"  >
								<select name="spId" class="select2" style="width: 200px;" onchange="submitform()">
                            	<OPTION value="" selected></OPTION>
									<% try{while(spList.next()){ 
								    	if(spList.getString("pk_seq").equals(dgnccBean.getSpId())){ %>
								      		<option value='<%= spList.getString("pk_seq") %>' selected><%=spList.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= spList.getString("pk_seq") %>'><%= spList.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
                            	</select>
								</TD>
							</TR> --%>
							<TR>
							<TD class="plainlabel" valign="top" width="150px"> Đơn mua hàng</TD>
								<TD class="plainlabel" valign="top"  >
								<select name="sopo" onchange="submitform()">
                            	<OPTION value="null" selected></OPTION>
									<% try{
										if(poList != null)
										while(poList.next()){ 
								    	if(poList.getString("pk_seq").equals(dgnccBean.getSoPO())){ %>
								      		<option value='<%= poList.getString("pk_seq") %>' selected><%=poList.getString("sopo") %></option>
								      	<%}else{ %>
								     		<option value='<%= poList.getString("pk_seq") %>'><%= poList.getString("sopo") %></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
                            	</select>
								</TD>
							</TR>
						</TABLE>
						<hr>
					</div>
					</fieldset>
					</div>
				<div align="center" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Tiêu chí bắt buộc</legend>	
       			<div style="float: none; width: 100%" align="center">
                <TABLE class="tabledetail" width="70%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                    	<TH align="center" width="5%"> STT</TH>
                        <TH align="center" width="50%"> Tiêu chí </TH>
                        <TH align="center" width="10%"> Đạt</TH>
                        
                    </TR>	
                    <TR class='tbdarkrow'   >
                    	<TD align="center">1</TD>
                    	<TD align="center">Tư cách pháp nhân</TD>
                    	<TD align="center">
							<% if(dgnccBean.getTucach().equals("1")) { %>
              						<input type="checkbox" name="tucach" value="1" checked="checked" >
              					<% } else { %>
              						<input type="checkbox" name="tucach" value="1"  >
              					<% } %>
						</TD>
                    </tr>
                    <TR class='tblightrow'  >
                    	<TD align="center">2</TD>
                    	<TD align="center">Uy tín</TD>
                    	<TD align="center">
							<% if(dgnccBean.getUytin().equals("1")) { %>
              						<input type="checkbox" name="uytin" value="1" checked="checked" >
              					<% } else { %>
              						<input type="checkbox" name="uytin" value="1"  >
              					<% } %>
						</TD>
                    </TR>
                    <TR class='tbdarkrow'   >
                    	<TD align="center">3</TD>
                    	<TD align="center">Chất lượng sản phẩm</TD>
                    	<TD align="center">
							<% if(dgnccBean.getChatluong().equals("1")) { %>
              						<input type="checkbox" name="chatluongsp" value="1" checked="checked" >
              					<% } else { %>
              						<input type="checkbox" name="chatluongsp" value="1"  >
              					<% } %>
						</TD>
                    </tr>
                    <TR class='tblightrow'  >
                    	<TD align="center">4</TD>
                    	<TD align="center">Thời gian giao hàng</TD>
                    	<TD align="center">
							<% if(dgnccBean.getTggiaohang().equals("1")) { %>
              						<input type="checkbox" name="tggiaohang" value="1" checked="checked" >
              					<% } else { %>
              						<input type="checkbox" name="tggiaohang" value="1"  >
              					<% } %>
						</TD>
                    </TR>
                </TABLE>
                
                </div>
				</fieldset>
				</div>
				
				<div align="center" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Tiêu chí không bắt buộc</legend>	
       			<div style="float: none; width: 100%" align="center">
                <TABLE class="tabledetail" width="70%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                    	<TH align="center" width="5%"> STT</TH>
                        <TH align="center" width="50%"> Tiêu chí</TH>
                        <TH align="center" width="10%"> Điểm </TH>
                    </TR>	
                    <TR class='tbdarkrow'   >
                    	<TD align="center">1</TD>
                    	<TD align="center">Giá cả</TD>
                    	<TD align="center"><INPUT type="text" name="giaca" style="width: 97%" value='<%=dgnccBean.getGiaca()%>' ></TD>
                    </tr>
                    <TR class='tblightrow'  >
                    	<TD align="center">2</TD>
                    	<TD align="center">Phương tiện thanh toán</TD>
                    	<TD align="center"><INPUT type="text" name="ptthanhtoan" style="width: 97%" value='<%=dgnccBean.getPtthanhtoan()%>' ></TD>
                    </TR>
                    <TR class='tbdarkrow'   >
                    	<TD align="center">3</TD>
                    	<TD align="center">Phương tiện vận chuyển</TD>
                    	<TD align="center"><INPUT type="text" name="ptvanchuyen" style="width: 97%" value='<%=dgnccBean.getPtvanchuyen()%>' ></TD>
                    </tr>
                    <TR class='tblightrow'  >
                    	<TD align="center">4</TD>
                    	<TD align="center">Chế độ hậu mãi</TD>
                    	<TD align="center"><INPUT type="text" name="haumai" style="width: 97%" value='<%=dgnccBean.getHaumai()%>' ></TD>
                    </TR>
                    <TR class='tbdarkrow'   >
                    	<TD align="center">5</TD>
                    	<TD align="center">Chính sách khác</TD>
                    	<TD align="center"><INPUT type="text" name="chinhsachkhac" style="width: 97%" value='<%=dgnccBean.getChinhsachkhac()%>' ></TD>
                    </tr>
                </TABLE>
                
                </div>
				</fieldset>
				</div>
		</div>


	<%
		dgnccBean.close();
	%>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>
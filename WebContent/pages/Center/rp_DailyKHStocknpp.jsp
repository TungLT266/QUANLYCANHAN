<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	String userId = obj.getuserId();  
	String userTen = obj.getuserTen();  	
	String sum = (String) session.getAttribute("sum");
	String loi= obj.getMsg();
	String tungay= obj.gettungay();
	String denngay= obj.getdenngay();
	%>	


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="javascript" type="text/javascript">

function submitform()
{
	/* var tungay = document.getElementById('tungay');
	var denngay = document.getElementById('denngay');
	if(tungay.value == "")
	 {
		 alert('Vui lòng nhập đầy đủ khoảng thời gian lấy báo cáo');
		 return;
	 }
	
	if(denngay.value == "")
	 {
		 alert('Vui lòng nhập đầy đủ khoảng thời gian lấy báo cáo');
		 return;
	 } */
	
	var tungay = document.forms['rpForm'].tungay.value;
	if(tungay == "")                        
	{
		document.forms['rpForm'].dataerror.value = "Vui lòng nhập đầy đủ khoảng thời gian lấy báo cáo";
		return;
	}
	
	var denngay = document.forms['rpForm'].denngay.value;
	if(denngay == "")
	{
		document.forms['rpForm'].dataerror.value = "Vui lòng nhập đầy đủ khoảng thời gian lấy báo cáo";
		return;
	}
	//document.getElementById("btnTaoBC").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

	document.forms['rpForm'].dataerror.value="";
	document.forms['rpForm'].submit();
}


	

</SCRIPT>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});		
	});
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../DailyKHStocknpp">
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="userTen" value= <%= userTen %> >
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tồn kho &gt; Báo Cáo &gt; Tồn kho theo ngày của khách hàng </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=loi%></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle">Thời gian xuất báo cáo</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
								  	<TD width="19%" class="plainlabel" >Từ ngày</TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<input type="text" class="days" name="tungay" id="tungay" size="20" value = "<%=tungay%>" >
												</TD>
												
                                    		</TR>
										</TABLE>									</TD>
								<TR>
									<TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input type="text" name="denngay" id="denngay" class="days" size="20" value = "<%=denngay%>" >												</TD>
												
                                     		</TR>
										</TABLE>									</TD>
								</TR>
							    <TR>
									<TD colspan="2" class="plainlabel">
									<div id="btnTaoBC">
									<a class="button2" href="javascript:submitform()" >
	<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo</a>
	</div>
	&nbsp;&nbsp;&nbsp;&nbsp;                                    </TD>
								</TR>
							</TABLE>
							</FIELDSET>						</TD>
						</TR>
					</TABLE>					</TD>
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
	if(obj!=null){
		obj.DBclose();
		obj=null;
		
	}
	session.setAttribute("obj",null);
	
}catch(Exception er){
	
}
%>

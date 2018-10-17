<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
	
<%
	
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet rsKenh = obj.getkenh();
	ResultSet rsKhuVuc = obj.getkhuvuc();
	ResultSet rsVung = obj.getvung();
	ResultSet rstinhthanh=obj.getTinhthanh();
	ResultSet rsNpp = obj.getnpp();
	ResultSet rsGsbh = obj.getgsbh();
	ResultSet rsDdkd = obj.getRsddkd();
	ResultSet rsNhans = obj.getnhanhang();
	ResultSet rsChungLoai = obj.getchungloai();
	ResultSet rsDVKD = obj.getdvkd();
	ResultSet rsProgram = obj.getRsPrograms();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
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
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function search(){
		document.forms["frm"]["action"].value = "search";
		document.forms["frm"].submit();
	}
	function submitform() {
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return ;
		}
		document.forms["frm"]["action"].value = "Taomoi";
		document.forms["frm"].submit();
		reset();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		$("#Sdays").val("");
		$("#Edays").val("");	
	};
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	//$("#nppId").select2();
});
</script>


</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../BaoCaoDangNhapTTSvl">
		<input type="hidden" value="1" name="action"  >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị &#62; Báo cáo khác &#62; Theo dõi đăng nhập</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn &nbsp; <%= obj.getuserTen() %> </div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="#"> <img src="../images/Back30.png" alt="Quay ve"
					title="Quay ve" border="1px" longdesc="Quay ve"
					style="border-style: outset"></A> <A href="javascript:saveform()">
					<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai"
					border="1px" style="border-style: outset">
				</A>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" 	name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMsg() %>
					</textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Báo cáo theo dõi đăng nhập</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel"><input type="text" name="Sdays" id="Sdays""
										class="days" value="<%=obj.gettungay() %>" /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<TD class="plainlabel"><input type="text" name="Edays" id="Edays"
										class="days" value="<%=obj.getdenngay() %>" /></TD>
								</TR>
								<TR>
								<TD class="plainlabel" >Miền </TD>
									<TD class="plainlabel" >
					                  
					                    <select name="mien"   id="vungId"  style="width:200px" class="select2" > 
					                    <option value="" selected></option>
					                    <%if(rsVung!=null){
					                    while(rsVung.next()){ %>
					                            <option value="<%=rsVung.getString("PK_SEQ") %>" ><%=rsVung.getString("ten") %></option>	                           
					                    <%} }%>
					                     </select>    
               					 </TD>
									<TD class="plainlabel" >Địa bàn </TD>
									<TD class="plainlabel" >
					                    <select name="tinh"   id="tinhId"  style="width:200px" class="select2" > 
					                            <option value="" selected></option>
					                           <%if(rstinhthanh!=null){
					                           while(rstinhthanh.next()){ %>
					                            <option value="<%=rstinhthanh.getString("pk_seq") %>" ><%=rstinhthanh.getString("ten") %></option>
					                           <%}} %>
					                    </select>    
               					 </TD>
								</TR>
								
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo
									</a></td>
								</TR>
							</TABLE>
						</div>						
					</div>
				</fieldset>
			</div>
		</div>
	</form>
</body>
</html>

<%
	try
	{
		if(rsKenh != null) rsKenh.close();		
		if(rsKhuVuc != null) rsKhuVuc.close();
		if(rsVung != null) rsVung.close();
		if(rsNpp != null) rsNpp.close();
		if(rsGsbh != null) rsGsbh.close();
		if(rsDdkd != null) rsDdkd.close();
		if(rsDVKD != null) rsDVKD.close();
		
		if(obj != null) {obj.DBclose();
		obj = null;}
		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
}
%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@page import="geso.traphaco.erp.beans.baocaosudungtscd.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IErp_BaoCaoSuDungTSCD obj = (IErp_BaoCaoSuDungTSCD) session.getAttribute("obj");
	
	ResultSet loaiTaiSanRs= obj.getLoaiTaiSanRs();
	ResultSet dvthRs = obj.getDvthRs();
		

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Canfoco - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
	
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>
	
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
function seach()
{
	document.forms['frm'].action.value= 'seach';
	document.forms["frm"].submit();
}
	function submitform() {
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
	}
	function changeLoai() {
		document.forms['frm'].action.value= 'change';
		document.forms["frm"].submit();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../BoBaoCaoSuDungTSCD">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Quản lý tài sản >  Bộ báo cáo sử dụng TSCĐ </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng Bạn <%=userTen%> &nbsp;</div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Bộ báo cáo sử dụng TSCD
					</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">

							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
								<TD class="plainlabel">Loại báo cáo</TD>
								<TD class="plainlabel" colspan="3">

									<select name="loaiBC" id="loaiBC" onchange="changeLoai();" style="width: 200px" >
									<% if(obj.getLoaiBaoCao().equals("1")) { %>
										<option value="1" selected="selected">Báo cáo tăng TSCD</option>
										<option value="2">Báo cáo giảm TSCD</option>
										<option value="3">Báo cáo KH TSCD theo đơn vị</option>
										<option value="4">Báo cáo KH TSCD chi tiết</option>
										<option value="5">Báo cáo phân bổ KH</option>
									<% } else if(obj.getLoaiBaoCao().equals("2")){ %>
										<option value="1">Báo cáo tăng TSCD</option>
										<option value="2"  selected="selected">Báo cáo giảm TSCD</option>
										<option value="3">Báo cáo KH TSCD theo đơn vị</option>
										<option value="4">Báo cáo KH TSCD chi tiết</option>
										<option value="5">Báo cáo phân bổ KH</option>
									<% } else  if(obj.getLoaiBaoCao().equals("3")){  %> 
										<option value="1">Báo cáo tăng TSCD</option>
										<option value="2">Báo cáo giảm TSCD</option>
										<option value="3" selected="selected">Báo cáo KH TSCD theo đơn vị</option>
										<option value="4">Báo cáo KH TSCD chi tiết</option>
										<option value="5">Báo cáo phân bổ KH</option>
									<% } else if(obj.getLoaiBaoCao().equals("4")){ %>
										<option value="1">Báo cáo tăng TSCD</option>
										<option value="2">Báo cáo giảm TSCD</option>
										<option value="3">Báo cáo KH TSCD theo đơn vị</option>
										<option value="4"  selected="selected">Báo cáo KH TSCD chi tiết</option>
										<option value="5">Báo cáo phân bổ KH</option>
									<% } else if(obj.getLoaiBaoCao().equals("5")){ %>
										<option value="1">Báo cáo tăng TSCD</option>
										<option value="2">Báo cáo giảm TSCD</option>
										<option value="3">Báo cáo KH TSCD theo đơn vị</option>
										<option value="4">Báo cáo KH TSCD chi tiết</option>
										<option value="5" selected="selected">Báo cáo phân bổ KH</option>
								
								<%}%>
									</select>
								</TR>
								
								<% if(obj.getLoaiBaoCao().equals("3")){ %>	
								<TR>
								<TD class="plainlabel"> Đơn vị</TD>
									<TD class="plainlabel" colspan="3">
										<select name="dvth" id="dvth" style="width: 200px" >
											<option value=""></option>
											<% if (dvthRs != null)
											   {
												while (dvthRs.next())
												{
														if (dvthRs.getString("pk_seq").equals(obj.getDvthId()))
														{ %>
														<option value="<%=dvthRs.getString("pk_seq")%>" selected="selected"><%=dvthRs.getString("ten")%></option>
											<% } else { %>
												<option value="<%=dvthRs.getString("pk_seq")%>"><%=dvthRs.getString("ten")%></option>
											<% } } dvthRs.close(); } %>
										</select>			
									</TD>
								</TR>
								<% } %>
								<TR>
								<TD class="plainlabel"> Loại tài sản</TD>
									<TD class="plainlabel" colspan="3">
										<select name="loaiTaiSan" id="loaiTaiSan" style="width: 200px" >
											<option value=""></option>
											<% if (loaiTaiSanRs != null)
											   {
												while (loaiTaiSanRs.next())
												{
														if (loaiTaiSanRs.getString("pk_seq").equals(obj.getLoaiTaiSanId()))
														{ %>
														<option value="<%=loaiTaiSanRs.getString("pk_seq")%>" selected="selected"><%=loaiTaiSanRs.getString("ten")%></option>
											<% } else { %>
												<option value="<%=loaiTaiSanRs.getString("pk_seq")%>"><%=loaiTaiSanRs.getString("ten")%></option>
											<% } } loaiTaiSanRs.close(); } %>
										</select>			
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel">Chọn năm</TD>
									
									<TD class="plainlabel">
										<SELECT name = "year" style="width: 100px" >
										<option value=0> </option>  
										<%
									  
  										int n;
										int [] m = obj.getNam();
  										for(int i=0;i<m.length;i++){
  										if(obj.getYear().equals(""+m[i])){
	  									%>
										<option value=<%=m[i]%> selected="selected" > <%=m[i]%></option> 
										<%
	 										}else{
	 									%>
										<option value=<%=m[i]%> ><%=m[i]%></option> 
										<%
	 										}
	 									 }
 										%>

										</SELECT>										
									</TD>
									
									<TD class="plainlabel">Chọn tháng</TD>
									<TD>
										<SELECT name = "month" style="width: 100px" >
											<option value =""></option>
										<%if(obj.getMonth().equals("1")){ %>
											<option value = "1" selected>T.1</option>
										<%}else{ %>
											<option value = "1">T.1</option>
										<%} %>

										<%if(obj.getMonth().equals("2")){ %>
											<option value = "2" selected>T.2</option>
										<%}else{ %>
											<option value = "2">T.2</option>
										<%} %>

										<%if(obj.getMonth().equals("3")){ %>
											<option value = "3" selected>T.3</option>
										<%}else{ %>
											<option value = "3">T.3</option>
										<%} %>

										<%if(obj.getMonth().equals("4")){ %>
											<option value = "4" selected>T.4</option>
										<%}else{ %>
											<option value = "4">T.4</option>
										<%} %>

										<%if(obj.getMonth().equals("5")){ %>
											<option value = "5" selected>T.5</option>
										<%}else{ %>
											<option value = "5">T.5</option>
										<%} %>

										<%if(obj.getMonth().equals("6")){ %>
											<option value = "6" selected>T.6</option>
										<%}else{ %>
											<option value = "6">T.6</option>
										<%} %>

										<%if(obj.getMonth().equals("7")){ %>
											<option value = "7" selected>T.7</option>
										<%}else{ %>
											<option value = "7">T.7</option>
										<%} %>

										<%if(obj.getMonth().equals("8")){ %>
											<option value = "8" selected>T.8</option>
										<%}else{ %>
											<option value = "8">T.8</option>
										<%} %>
										
										<%if(obj.getMonth().equals("9")){ %>
											<option value = "9" selected>T.9</option>
										<%}else{ %>
											<option value = "9">T.9</option>
										<%} %>
										
										<%if(obj.getMonth().equals("10")){ %>
											<option value = "10" selected>T.10</option>
										<%}else{ %>
											<option value = "10">T.10</option>
										<%} %>
										
										<%if(obj.getMonth().equals("11")){ %>
											<option value = "11" selected>T.11</option>
										<%}else{ %>
											<option value = "11">T.11</option>
										<%} %>
										
										<%if(obj.getMonth().equals("12")){ %>
											<option value = "12" selected>T.12</option>
										<%}else{ %>
											<option value = "12">T.12</option>
										<%} %>

										</SELECT>
									
									</TD>
								</TR>
								
								<TR>
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a>
									</td>
								</TR>
							</TABLE>
						</div>
						</div>
					</div>
			</div>
	</form>
</body>
</html>
<%
	obj.DBClose();
session.setAttribute("obj", null) ; 

%>
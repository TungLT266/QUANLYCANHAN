do<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.baocaocandoiketoan.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%
	System.out.println("");
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else {
%>
<%
	IErp_BaoCaoCanDoiKeToanList obj = (IErp_BaoCaoCanDoiKeToanList) session.getAttribute("obj");
	NumberFormat formatter = new DecimalFormat("#,###,###.##");
%>
<%
int[] quyen = new  int[5];
quyen = util.Getquyen("Erp_BaoCaoCanDoiKeToanSvl","",userId);
%>
<% obj.setNextSplittings(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});
	jQuery(document).ready(function()
	{
		$("select:not(.notuseselect2)").chosen();     
		
	});
</script>

  <style type="text/css">
   ul { height: 100px; overflow:auto; width: 100px; border: 1px solid #000; }
   ul { list-style-type: none; margin: 0; padding: 0; overflow-x: hidden; width: 200px }
   li { margin: 0; padding: 0; }
   label { display: block; color: WindowText; background-color: Window; margin: 0; padding: 0; width: 100%; }
   label:hover { background-color: Highlight; color: HighlightText; }
  </style>

	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="JavaScript" type="text/javascript">

	function exportExcel()
	{
		document.forms['xdForm'].action.value= 'exportExcel';
		document.forms['xdForm'].submit();
	}
	
	function thongbao() {
		console.log("thong bao");
		var tt = document.forms['xdForm'].msg.value;
		if (tt.length > 0)
			alert(document.forms['xdForm'].msg.value);
}
</SCRIPT>
 <link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$(document).ready(function()
	{
		$(".select2").select2();
	});
});
</script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="xdForm" method="post" action="../../Erp_BaoCaoCanDoiKeToanSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<%System.out.println("obj.getMsg(): " + obj.getMsg()); %>
		<input type="hidden" name="action" value='1'> <input type="hidden" id="msg" name="msg" value='<%=obj.getMsg()%>'> 
		<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

		<script language="JavaScript" type="text/javascript">
    	thongbao();
		</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý kế toán &gt; Báo cáo &gt; Bảng cân đối kế toán (TT200)</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="left">
											<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
												<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
													<TR style="display: none;">
														<TD width="19%" class="plainlabel">Mã chi phí trả trước</TD>
														<TD class="plainlabel" colspan=5><INPUT name="maCCDC" type="text" size="30" value='<%=""%>' onChange="submitform();"></TD>
														
														<TD width="19%" class="plainlabel">Số chứng từ</TD>
														<TD class="plainlabel" colspan=5><INPUT name="soChungTu" type="text" size="30" value='<%=""%>' onChange="submitform();"></TD>
													</TR>
													
											<TR>
													
													<TD class="plainlabel">Chọn năm đầu kỳ</TD>
													
													<TD class="plainlabel">
														<SELECT name = "yearDK" style="width:200px">
														<option value=0> </option>  
														<%
													  int n = 0;
				  										for(n = 2015; n < 2020; n++){
				  										if(obj.getYear().equals(""+n)){
					  									%>
														<option value=<%=n%> selected="selected" > <%=n%></option> 
														<%
					 										}else{
					 									%>
														<option value=<%=n%> ><%=n%></option> 
														<%
					 										}
					 									 }
				 										%>
				
														</SELECT>										
													</TD>
									
													<TD class="plainlabel">Chọn tháng đầu kỳ</TD>
													<TD class="plainlabel">
													<SELECT name = "monthDK" style="width:200px">
													<%if(obj.getMonthDK().equals("1")){ %>
														<option value = "1" selected>T.1</option>
													<%}else{ %>
														<option value = "1">T.1</option>
													<%} %>
			
													<%if(obj.getMonthDK().equals("2")){ %>
														<option value = "2" selected>T.2</option>
													<%}else{ %>
														<option value = "2">T.2</option>
													<%} %>
			
													<%if(obj.getMonthDK().equals("3")){ %>
														<option value = "3" selected>T.3</option>
													<%}else{ %>
														<option value = "3">T.3</option>
													<%} %>
			
													<%if(obj.getMonthDK().equals("4")){ %>
														<option value = "4" selected>T.4</option>
													<%}else{ %>
														<option value = "4">T.4</option>
													<%} %>
			
													<%if(obj.getMonthDK().equals("5")){ %>
														<option value = "5" selected>T.5</option>
													<%}else{ %>
														<option value = "5">T.5</option>
													<%} %>
			
													<%if(obj.getMonthDK().equals("6")){ %>
														<option value = "6" selected>T.6</option>
													<%}else{ %>
														<option value = "6">T.6</option>
													<%} %>
			
													<%if(obj.getMonthDK().equals("7")){ %>
														<option value = "7" selected>T.7</option>
													<%}else{ %>
														<option value = "7">T.7</option>
													<%} %>
			
													<%if(obj.getMonthDK().equals("8")){ %>
														<option value = "8" selected>T.8</option>
													<%}else{ %>
														<option value = "8">T.8</option>
													<%} %>
													
													<%if(obj.getMonthDK().equals("9")){ %>
														<option value = "9" selected>T.9</option>
													<%}else{ %>
														<option value = "9">T.9</option>
													<%} %>
													
													<%if(obj.getMonthDK().equals("10")){ %>
														<option value = "10" selected>T.10</option>
													<%}else{ %>
														<option value = "10">T.10</option>
													<%} %>
													
													<%if(obj.getMonthDK().equals("11")){ %>
														<option value = "11" selected>T.11</option>
													<%}else{ %>
														<option value = "11">T.11</option>
													<%} %>
													
													<%if(obj.getMonthDK().equals("12")){ %>
														<option value = "12" selected>T.12</option>
													<%}else{ %>
														<option value = "12">T.12</option>
													<%} %>
			
													</SELECT>
												
												</TD>
											</TR>
											
											<TR>
													
													<TD class="plainlabel">Chọn năm trong kỳ</TD>
													
													<TD class="plainlabel">
														<SELECT name = "yearCK" style="width:200px">
														<option value=0> </option>  
														<%
													  
				  										for(n = 2015; n < 2020; n++){
				  										if(obj.getYearDK().equals(""+n)){
					  									%>
														<option value=<%=n%> selected="selected" > <%=n%></option> 
														<%
					 										}else{
					 									%>
														<option value=<%=n%> ><%=n%></option> 
														<%
					 										}
					 									 }
				 										%>
				
														</SELECT>										
													</TD>
									
													<TD class="plainlabel">Chọn tháng trong kỳ</TD>
													<TD class="plainlabel">
													<SELECT name = "monthCK" style="width:200px">
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
									<TD class="plainlabel">Công ty</TD>
									<TD class="plainlabel">
										<select name="congTyLId">	
											<option value="0" selected>Hợp nhất</option>										
										<%
												for (Erp_Item item: obj.getDieuKienLoc().getCongTyList())
													if (obj.getDieuKienLoc().getCongTyId().trim().equals(item.getValue())){
											%>
												<option value="<%=item.getValue() %>" selected><%=item.getName() %></option>
											<%
													} else {
											%>
												<option value="<%=item.getValue() %>"><%=item.getName() %></option>
											<%
												}
											%>
										</select>
									</TD>
									<TD colspan="6" class="plainlabel"></TD>
								</TR>
													
								<TR>
									<TD class="plainlabel" colspan="2">
																						
									<a class="button3" href="javascript:exportExcel()"><img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
									
									</TD>
									<TD colspan="6" class="plainlabel"></TD>
								</TR>
												</TABLE>
											</FIELDSET>
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
</BODY>
</HTML>
<% if(obj!=null)obj.DBClose(); 
	session.setAttribute("obj",null);
	obj=nu%>
<%
	}
	
%>


<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.distributor.beans.reports.IBangKeTTThang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String nppId = (String) session.getAttribute("nppId");  	
	String sum = (String) session.getAttribute("sum");
	String loi=(String)session.getAttribute("loi");
	IBangKeTTThang obj = (IBangKeTTThang) session.getAttribute("obj");
	String thang=(String)session.getAttribute("thang");
	String nam=(String)session.getAttribute("nam");
	String loai=(String)session.getAttribute("loai");	
	String tt = (String)session.getAttribute("tt");
	
	ResultSet rsTDV = obj.getRsTDV();
	ResultSet rsChiNhanh = obj.getRsChiNhanh();	
	
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
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
           
        }); 
		
		
    </script>

<script type="text/javascript">
function submitform()
{
	var loai = document.getElementById("loai").value;
	var tt = document.getElementById("tt").value;
	
	if(loai=="0" || loai=="3" || loai=="5")
	{
		var thang = document.getElementById("thang").value;
		if(thang == 0)
		{
			document.getElementById("dataerror").value="Vui lòng chọn tháng";
			return;
		}
	}
	
	if(loai=="1" || loai=="2" || loai=="4"|| loai=="6")
	{
		var quy = document.getElementById("quy").value;
		if(thang == 0)
		{
			document.getElementById("dataerror").value="Vui lòng chọn quý";
			return;
		}
	}
	
	var nam = document.getElementById("nam").value;
	if(nam == 0)
	{
		document.getElementById("dataerror").value="Vui lòng chọn năm";
		return;
	}
	
	if(loai=="0" || loai=="1" )
	{
		var tdvId = document.getElementById("tdvId").value;
		if(tdvId == "")
		{
			document.getElementById("dataerror").value="Vui lòng chọn trình dược viên";
			return;
		} 
	} 
	
	if(tt=="1")
	{
		var chinhanhId = document.getElementById("chinhanhId").value;
		if(chinhanhId == "")
		{
			document.getElementById("dataerror").value="Vui lòng chọn chi nhánh/đối tác";
			return;
		} 
	}
		
	
	document.forms['rpForm'].dataerror.value="";
	document.forms['rpForm'].action.value= 'taomoi';
	document.forms['rpForm'].submit();
}

function seach()
{
	document.forms['rpForm'].action.value= 'seach';
	document.forms["rpForm"].submit();
}
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
		$(document).ready(function()
		{
			$(".select2").select2();
		});
	</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../BangKeTTThangSvl">
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>
<input type="hidden" name="loai" id="loai" value= <%= loai %>>
<input type="hidden" name="tt" id="tt" value= <%= tt %>>


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						  <% if(loai.equals("0")){%>
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Biểu mẫu thanh toán &gt; Chức năng &gt; Bảng kê thanh toán trong tháng CN </TD>
   						<% }else if(loai.equals("1")) { %>
   							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Biểu mẫu thanh toán &gt; Chức năng &gt; Bảng kê CK theo quý CN </TD>
   						<% }else if(loai.equals("2")) { %>
   							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Biểu mẫu thanh toán &gt; Chức năng &gt; Bảng kê quý ủng hộ CN </TD>
   						<% }else if(loai.equals("3")) { %>
   							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Biểu mẫu thanh toán &gt;  Chức năng &gt; Bảng kê thanh toán trong tháng DT </TD>
   						<% }else if(loai.equals("4")) { %>
   							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Biểu mẫu thanh toán &gt; Chức năng &gt; Bảng kê thanh toán trong quý DT</TD>
   						<% }else if(loai.equals("7")) { %>
   							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Biểu mẫu thanh toán &gt; Chức năng &gt; Bảng kê chiết khấu khách hàng thân thiết  </TD>
   						<%}else{ %>
   							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Biểu mẫu thanh toán &gt; Chức năng &gt; Xác nhận doanh số qúy và nhóm DT</TD>
   						<%} %>
   						
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror" id="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=loi%></textarea>
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
							
							<%if(loai.equals("0") || loai.equals("3") || loai.equals("5") ) { %>
							<TR>
								<TD class="plainlabel" width="10%">Tháng</TD>
									<TD class="plainlabel">
									 <select name="thang" id="thang"  style="width :70px" >
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getThang().equals(chuoi)){
  									%>
									<option value=<%=chuoi%> selected="selected" > <%=chuoi%></option> 
									<%
 										}else{
 									%>
									<option value=<%=chuoi%> ><%=chuoi%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									<select name="nam" id="nam"  style="width: 70px;" >
									<option value=0> </option>  
									<%
									  
  										int n;
  										for(n=2014;n<2020;n++){
  										if(obj.getNam().equals(""+n)){
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
 									</select>
									</TD>
								</TR>
								<%}else if (!loai.equals("7")){%>
										<TR>
									<TD class="plainlabel" width="10%">Qúy</TD>
									<TD class="plainlabel">
									<% 	String[] trangthai = new  String[] {"Qúy 1","Qúy 2","Qúy 3","Qúy 4"  } ;
										String[] idTrangThai = new  String[] {"1","2","3","4"} ;
									%> 
									<SELECT name="quy" id="quy" style="width :70px" >
									<option> </option>
					      		 	<% for( int i=0;i<trangthai.length;i++) { 
					    				if(idTrangThai[i].equals(obj.getQuy()) ){ %>
					      					<option value='<%=idTrangThai[i]%>' selected><%=trangthai[i] %></option>
					      		 		<%}else{ %>
					     					<option value='<%=idTrangThai[i]%>'><%=trangthai[i] %></option>
					     				<%} 
					      		 		}
					      		 	%>
								       	</SELECT>
								       	
								       	<select name="nam" id="nam"  style="width: 70px;" >
										<option value=0> </option>  
										<%
										  
	  										int n;
	  										for(n=2014;n<2020;n++){
	  										if(obj.getNam().equals(""+n)){
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
	 									</select>
	 									</TD>
								   </TR>
								<%} %>
								
								<%if(tt.equals("1")){ %>
								<TR>
									<%if (loai.equals("0") || loai.equals("1") || loai.equals("2") ) { %>
										<TD class="plainlabel">Chi nhánh</TD>
									<%}else{ %>
										<TD class="plainlabel">Đối tác</TD>
									<%} %>
										<TD class="plainlabel">
											<select name="chinhanhId" id="chinhanhId" style="width:200px;" onchange="seach();" class="select2"  >
												<option value="" selected></option>
												<%
													if (rsChiNhanh != null)
														while (rsChiNhanh.next()) {
															if (rsChiNhanh.getString("pk_seq").equals(obj.getChinhanhId())) {
												%>
												   <option value="<%=rsChiNhanh.getString("pk_seq")%>" selected><%=rsChiNhanh.getString("ten")%></option>
												   <%
												   	} else {
												   %>
												   <option value="<%=rsChiNhanh.getString("pk_seq")%>"><%=rsChiNhanh.getString("ten")%></option>
												<%
													}
														}
												%>
										</select></TD>									
									</TR>								
								<%} %>
								
								
								<%if(loai.equals("0") || loai.equals("1") || loai.equals("2")){ %>
								<TR>
								<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel"><select name="tdvId" id="tdvId" style="width:200px;" >
											<option value="" selected></option>
											<%
												if (rsTDV != null)
													while (rsTDV.next()) {
														if (rsTDV.getString("pk_seq").equals(obj.getTdvId())) {
											%>
											   <option value="<%=rsTDV.getString("pk_seq")%>" selected><%=rsTDV.getString("ten")%></option>
											   <%
											   	} else {
											   %>
											   <option value="<%=rsTDV.getString("pk_seq")%>"><%=rsTDV.getString("ten")%></option>
											<%
												}
													}
											%>
									</select></TD>
								
								</TR>
								<%} if(loai.equals("7")) { %>
								
								<TR>
								<TD class="plainlabel" >Tháng</TD>
								<TD class="plainlabel">
									
									<SELECT name="khtt" id="khtt" style="width :200px" >
										<option> 6 tháng đầu năm</option>
						      			 <option> 6 tháng cuối năm</option>
								 	</SELECT>
								 	
							 	 
								       	
	 							</TD>
	 							
	 							
								</TR>
								
								<TR>
								
									<TD class="plainlabel" >Năm </TD>
	 								<TD class="plainlabel">
	 								<select name="nam" id="nam"  style="width: 70px;" >
										
										<option value=0> </option>  
										<%
										  
	  										int n;
	  										for(n=2014;n<2020;n++){
	  									if(obj.getNam().equals(""+n)){
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
 									</select>
 									</td>
									
								</TR>
								<TR>
								<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel"><select name="tdvId" id="tdvId" style="width:200px;" >
											<option value="" selected></option>
											<%
												if (rsTDV != null)
													while (rsTDV.next()) {
														if (rsTDV.getString("pk_seq").equals(obj.getTdvId())) {
											%>
											   <option value="<%=rsTDV.getString("pk_seq")%>" selected><%=rsTDV.getString("ten")%></option>
											   <%
											   	} else {
											   %>
											   <option value="<%=rsTDV.getString("pk_seq")%>"><%=rsTDV.getString("ten")%></option>
											<%
												}
													}
											%>
									</select></TD>
								
								</TR>
								<%} %>
							    <TR>
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:submitform()" >
	<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo</a>&nbsp;&nbsp;&nbsp;&nbsp;                                    </TD>
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
	}%>
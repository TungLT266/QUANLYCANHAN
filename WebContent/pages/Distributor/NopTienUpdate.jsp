<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.noptien.INoptien" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% INoptien khBean = (INoptien)session.getAttribute("khBean"); %>
<% ResultSet nvgn = (ResultSet)khBean.getNvgnRs() ;  %>
<% ResultSet nvbh = (ResultSet)khBean.getNvbhRs() ;  %>
<% ResultSet kh = (ResultSet)khBean.getKhRs(); %>
<% ResultSet dt = (ResultSet)khBean.getDtRs(); %>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<SCRIPT language="javascript" type="text/javascript">

function submitform()
 {   
    document.forms["khForm"].submit();
 }

 function saveform()
 { 
	 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

 	 document.forms['khForm'].action.value='save';
     document.forms['khForm'].submit();
 }
 function showElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "";
 }

 function hideElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "none";
 }

 function SubmitAgain(){
	 var type= document.forms['khForm'].type.value;
	 if(type=="1")
	 {
		 if(!confirm('Địa chỉ khách hàng này đã bị trùng,Bạn muốn tiếp tục lưu khách hàng này?')) 
		 {
			 document.forms['khForm'].type.value="0";
			 return false;
		 }else
		 {
		 	saveform();
		 }
	 }
 }
 function DinhDang()
 {
 	var sotien = document.getElementById("sotien").value.replace(/,/g,"");
 	document.getElementById("sotien").value= DinhDangTien(sotien);
 }

 function DinhDangTien(num) 
  {
     num = num.toString().replace(/\$|\,/g,'');
     if(isNaN(num))
     num = "0";
     sign = (num == (num = Math.abs(num)));
     num = Math.floor(num*100+0.50000000001);
     num = Math.floor(num/100).toString();
     for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
     num = num.substring(0,num.length-(4*i+3))+','+
     num.substring(num.length-(4*i+3));
     return (((sign)?'':'-') + num);
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
 		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46 || keypressed == 45)
 		{//Phím Delete và Phím Back
 			return;
 		}
 		return false;
 	}
 } 
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khForm" method="post" action="../../NoptienUpdateSvl">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%= khBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%=khBean.getId()%>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ > Nộp tiền > Cập nhật
	   						 <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= khBean.getNppTen() %>&nbsp; </tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="2">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="../../NoptienSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
					    </TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>		
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width:100%" rows="1"><%= khBean.getMessage() %></textarea>
							<%khBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET><LEGEND class="legendtitle" style="color:black">Thông tin khách hàng</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="120" class="plainlabel" > Ngày nộp</TD>
								<TD width="250" class="plainlabel" colspan="3">
									<INPUT type="text" class= "days" name="ngaynop" id="ngaynop" value="<%= khBean.getNgaynop() %>" >
								</TD>							
							  
							</TR>

						 <TR>
						<TR>
						<TD width="15%" class="plainlabel" valign="top">Đối tượng</TD>
						<TD class="plainlabel" colspan="3" >
							<select id="doituongId" name= "doituongId" onChange="submitform();" style="width: 200px">
							
							<%if(khBean.getDoituongId().equals("0")) {%>
							 <option value=""></option>
							 <option value="0" selected="selected">Nhân viên giao nhận</option> 
							 <option value="1" >Trình dược viên</option> 
							 <option value="2" >Khách hàng</option>
							 <option value="3" >Đối tác</option>
							<%}else if(khBean.getDoituongId().equals("1")){ %>
							<option value=""></option>
							 <option value="0" >Nhân viên giao nhận</option> 
							 <option value="1" selected="selected">Trình dược viên</option> 
							 <option value="2" >Khách hàng</option>
							 <option value="3" >Đối tác</option>
							 <%}else if(khBean.getDoituongId().equals("2")){ %>
							<option value=""></option>
							 <option value="0" >Nhân viên giao nhận</option> 
							 <option value="1" >Trình dược viên</option> 
							 <option value="2" selected="selected">Khách hàng</option>
							 <option value="3" >Đối tác</option>
							 <%}else if(khBean.getDoituongId().equals("3")){ %>
							<option value=""></option>
							 <option value="0" >Nhân viên giao nhận</option> 
							 <option value="1" >Trình dược viên</option> 
							 <option value="2" >Khách hàng</option>
							 <option value="3" selected="selected">Đối tác</option>							 
							  <%}else if(khBean.getDoituongId().equals("")){ %>
							<option value="" selected="selected"></option>
							 <option value="0" >Nhân viên giao nhận</option> 
							 <option value="1" >Trình dược viên</option> 
							 <option value="2" >Khách hàng</option>
							 <option value="3" >Đối tác</option>
							<%} %>
							</select>
						</TD>					
					</TR>
					<%if(khBean.getDoituongId().equals("0")){ %>
						  <TR>
							   	 <TD class="plainlabel">Nhân viên giao nhận</TD>
								 <TD class="plainlabel" colspan="3">
								    <SELECT name="nvgnId" id="nvgnId" onChange="submitform();" style="width: 200px">
								    	<option value=""></option>
								      <% try{
								    	  if(nvgn!= null)
								    	  {
								    	  while(nvgn.next()){ 
								    	if(nvgn.getString("pk_seq").equals(khBean.getNvgnId())){ %>
								      		<option value='<%=nvgn.getString("pk_seq")%>' selected><%=nvgn.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=nvgn.getString("pk_seq")%>'><%=nvgn.getString("ten") %></option>
								     	<%}}
								    	  nvgn.close();
								    	  }}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	
                        			</TD>							   	
                        			
						  </TR>
					<%}else if(khBean.getDoituongId().equals("1")){ %>  
							 <TR>
							   	 <TD class="plainlabel">Trình dược viên</TD>
								 <TD class="plainlabel" colspan="3">
								    <SELECT name="nvbhId" id="nvbhId" onChange="submitform();" style="width: 200px">
								    	<option value=""></option>
								      <% try{
								    	  if(nvbh!=null)
								    	  {
								    	  while(nvbh.next()){ 
								    	if(nvbh.getString("pk_seq").equals(khBean.getNvbhId())){ %>
								      		<option value='<%=nvbh.getString("pk_seq")%>' selected><%=nvbh.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=nvbh.getString("pk_seq")%>'><%=nvbh.getString("ten") %></option>
								     	<%}}
								    	  nvbh.close();
								    	  }}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	
                        			</TD>							   	
                        			
						  </TR>
						  <%} else if (khBean.getDoituongId().equals("2")) {%>
							 <TR>
							   	 <TD class="plainlabel">Khách hàng</TD>
								 <TD class="plainlabel" colspan="3">
								    <SELECT name="khId" id="khId" onChange="submitform();" style="width: 200px">
								    	<option value=""></option>
								      <% try{
								    	  if(kh!= null)
								    	  {
								    	  while(kh.next()){ 
								    	if(kh.getString("pk_seq").equals(khBean.getKhId())){ %>
								      		<option value='<%=kh.getString("pk_seq")%>' selected><%=kh.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=kh.getString("pk_seq")%>'><%=kh.getString("ten") %></option>
								     	<%}}
								    	  kh.close();
								    	  }}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	
                        			</TD>							   	                       			
						  </TR>
						<%}else if (khBean.getDoituongId().equals("3")) {%>
							 <TR>
							   	 <TD class="plainlabel">Đối tác</TD>
								 <TD class="plainlabel" colspan="3">
								    <SELECT name="dtId" id="dtId" onChange="submitform();" style="width: 200px">
								    	<option value=""></option>
								      <% try{
								    	  if(dt!= null)
								    	  {
								    	  while(kh.next()){ 
								    	if(dt.getString("pk_seq").equals(khBean.getDtId())){ %>
								      		<option value='<%=dt.getString("pk_seq")%>' selected><%=dt.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=dt.getString("pk_seq")%>'><%=dt.getString("ten") %></option>
								     	<%}}
								    	  dt.close();
								    	  }}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	
                        			</TD>							   	                       			
						  </TR>
						<%} %>
							
							<TR>
								<TD width="120" class="plainlabel" > Số tiền </TD>
								<TD width="250" class="plainlabel" colspan="3">
									<INPUT type="text" name="sotien" id="sotien" value="<%= formatter.format(khBean.getSotien())%>" onKeyPress = "return keypress(event);"  onchange="DinhDang();">(VNĐ)
								</TD>							
							  
							</TR>
							<TR>
								<TD width="120" class="plainlabel" >Số in </TD>
								<TD width="250" class="plainlabel" colspan="3">
									<INPUT type="text" name="soin" id="soin" value="<%= khBean.getSoin() %>"  >
								</TD>							
							  
							</TR>
							<TR>
								<TD width="120" class="plainlabel" > Về khoản </TD>
								<TD width="250" class="plainlabel" colspan="3">
									<INPUT type="text"  name="vekhoan" id="vekhoan" value="<%= khBean.getVekhoan() %>"  >
								</TD>							
							  
							</TR>
							<TR>
								<TD width="120" class="plainlabel" > Kèm theo </TD>
								<TD width="250" class="plainlabel" colspan="3">
									<INPUT type="text"  name="soCTgoc" id="soCTgoc" value="<%= khBean.getSoCTgoc()%>"  > chứng từ gốc
								</TD>							
							  
							</TR>
							
							
						</TABLE>
						</FIELDSET>
						
					    
					</TR>
			  	</TABLE>
			
	</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<script type="text/javascript">
	SubmitAgain();
</script>
<% 	

if(khBean != null){
	khBean.DBclose();
	khBean = null;
}

	try{
	if(nvgn != null)
		nvgn.close();
	if(nvbh != null)
		nvbh.close();
	if(kh != null)
		kh.close();


	
	session.setAttribute("khBean",null);
	}
	catch (SQLException e) {}
	
%>
<%}%>

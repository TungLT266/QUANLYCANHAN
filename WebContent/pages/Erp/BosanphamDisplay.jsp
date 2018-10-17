<%@page import="geso.traphaco.erp.beans.bosanpham.IBosanpham"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
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
  <%
  	IBosanpham obj = (IBosanpham)session.getAttribute("obj") ;
  	ResultSet khoRs = obj.getKhoRs();
  	String[] spTen2 = obj.getSpTen2();
  	String[] soluong2 = obj.getSoluong2();
  %>
<%  NumberFormat formatter = new DecimalFormat("#,###,###"); %>   
<%  NumberFormat formatter1 = new DecimalFormat("###.###"); %> 
 
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
	
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>


<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>	

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
	

 <SCRIPT language="javascript" type="text/javascript">
						
	function saveform()
	{ 	
		document.forms['bspForm'].action.value= 'save';
		document.forms['bspForm'].submit();
	}

	function submitform()
	{ 	
		document.forms['bspForm'].submit();
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
			return false;}
			
	}	
	function isNumberKey2(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) )
	      return false;
	
	   return true;
	}
	
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

	 function Dinhdang(element)
	 {
	 
	 	element.value = DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	
	 }
	 
	 function replaces()
	 {
		 for(var i = 1; i < 5; i++) {
		 
			var spTen = document.getElementById("spTen" + i);
			var dvt = document.getElementById("dvt" + i);
			
			var tem = spTen.value;
			if(tem == "")
			{
				spTen.value = "";
				dvt.value = "";
			}
			else
			{
				if(parseInt(tem.indexOf(" - ")) > 0)
				{					
					tem = tem.substr(parseInt(tem.indexOf(" [")) + 2);
					dvt.value = tem.substring(0, tem.indexOf("][") );
				}
			}
			
		 }
		
		 setTimeout(replaces, 250);
	 }
	
</SCRIPT>
 
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">

<form name="bspForm" method="post" action="../../BosanphamUpdateSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="Id" value='<%= obj.getId()%>'>

<div id="main" style="width:100%">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý cung ứng &gt; Sản xuất  &gt; Bó sản phẩm  &gt; Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%=userTen%>
        </div>
    </div>
    <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <div style="width:100%; float:none" align="left">
             			<TABLE border="0" width="100%" >
							<TR>
						  		<TR ><TD >
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
												<TR class = "tbdarkrow">
													<TD width="30" align="left"><A href="../../BosanphamSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
												    <TD width="2" align="left" ></TD>
												</TR>
										</TABLE>
								</TD></TR>
								<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông báo </LEGEND>
				
			    				<textarea name="dataerror"  cols="100%" rows="1" ><%=obj.getmsg() %> </textarea>
								
								</FIELDSET>
						   </TD>
					     </tr>
					     <tr>
					     <td>
					     <FIELDSET>
								<LEGEND class="legendtitle">Bó sản phẩm</LEGEND>
									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0" border = "0">
					                    <TR>
										  <TD  class="plainlabel" width = "120px">Ngày</TD>
										  <TD  class="plainlabel" colspan = "7"><INPUT class = "days" name="ngay" type="text" size="20" value="<%= obj.getNgay() %>" readonly = readonly></TD>
										</TR> 

										<TR>
											<TD  class="plainlabel" >Chọn kho</TD>
											<TD class="plainlabel" colspan = "7">
											<SELECT NAME = "khoId" >
														<OPTION VALUE = "">&nbsp;</OPTION>
										<% if (khoRs != null){
												while(khoRs.next()){
													if(khoRs.getString("KHOID").equals(obj.getKhoId())){
											%>																	
														<OPTION VALUE = "<%= khoRs.getString("KHOID") %>" SELECTED><%= khoRs.getString("KHO")%></OPTION>
														
										<%			}else{ %>
													
														<OPTION VALUE = "<%= khoRs.getString("KHOID") %>" ><%= khoRs.getString("KHO")%></OPTION>
																
										<% 			}
												}
											}%>
											
											</SELECT>
											</TD>											
										</TR>
										
										<TR>
											<TD class="plainlabel" width="120px" >Sản phẩm </TD>
										  	<TD width="420px" class="plainlabel" ><INPUT id = "spTen1" name="spTen1" type="text"  value="<%=obj.getSpTen1() %>" style="width:400px" readonly = readonly></TD>
										  	
										  	<TD class="plainlabel" width="100px" >Đơn vị tính </TD>
										  	<TD width="100px" class="plainlabel" ><INPUT readonly="readonly" id = "dvt1" name="dvt1" type="text"  value="" style="width:100px" readonly = readonly></TD>
										  	
											<TD width="100px" class="plainlabel" >Số lượng </TD>
											<TD width="105px" class="plainlabel" ><INPUT name="soluong1" type="text" value="<%=formatter1.format(Double.parseDouble(obj.getSoluong1())) %>" style = "width:100px" onkeypress = "return keypress(event);" readonly = readonly></TD>
											
										</TR>

									</TABLE>
						</FIELDSET>										
					     <FIELDSET>
								<LEGEND class="legendtitle">Sản phẩm dùng để bó</LEGEND>
									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0" border = "0">
					               <% for(int i = 1; i < 5; i++){ %>
									
										<TR>
											<TD class="plainlabel" width = "120px">Sản phẩm  <%= i %></TD>
										  	<TD  width="420px" class="plainlabel" ><INPUT Id = <%= "spTen" + (i+1)%> name=<%= "spTen" + (i+1)%> type="text" size="40" value="<%= spTen2[i-1]  %>" style="width:400px" readonly = readonly></TD>
										  	
										  	<TD class="plainlabel" width="100px" >Đơn vị tính </TD>
										  	<TD width="100px" class="plainlabel" ><INPUT readonly="readonly" id = <%= "dvt" + (i+1)%> name="dvt1" type="text"  value="" style="width:100px" readonly = readonly></TD>
										  	
											<TD  width="100px" class="plainlabel">Số lượng </TD>
											<TD  width="105px"  class="plainlabel" ><INPUT name=<%= "soluong" + (i+1)%> id=<%= "soluong" + (i+1)%> type="text" value="<%= formatter1.format(Double.parseDouble(soluong2[i - 1])) %>" style = "width:100px" onkeypress= "return keypress(event);" readonly = readonly ></TD>

										</TR>

									<%} %>
									
									
									</TABLE>

								</FIELDSET>
								
								</TD>	
							</TR>
						</TABLE>
         </div>
		 
				
				
</form>
<script type="text/javascript">

	 replaces();
</script>

</BODY>
</html>
<%
	try{
		if(khoRs != null) khoRs.close();
		
		if(obj != null){
			obj.DbClose();
		}
		session.setAttribute("obj",null);
		
	}catch(Exception err){
			
	}
}%>
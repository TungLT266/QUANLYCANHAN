<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.tonkhoantoan.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.tonkhoantoan.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>
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
 	IErpTonkhoantoan obj =(IErpTonkhoantoan)session.getAttribute("tkatBean");
	ResultSet clRs = obj.getClRs();
	ResultSet tkatRs_1 = obj.getTkatRs_1();
	ResultSet tkatRs_2 = obj.getTkatRs_2();
	ResultSet tkatRs_3 = obj.getTkatRs_3();
	//ResultSet tkatRs_4 = obj.getTkatRs_4();	
%>
<% NumberFormat formatter = new DecimalFormat("#,###,###.##"); %>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

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

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $(".select2").select2(); });
     
 </script>


<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
		document.forms["tkat"].action.value = "";
	    document.forms["tkat"].submit();
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
	
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value);
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

	 function Dinhdang(element)
	 {
	 
	 	element.value = DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	
	 }

	function isNumberKey2(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) )
	      return false;
		
	   return true;
	}
	
	function save()
	{
	  document.forms["tkat"].action.value = "save";
	  document.forms["tkat"].submit(); 
    }
	

	
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="tkat" method="post" action="../../ErpTonkhoantoanUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">

<TABLE width="100%" border="0" cellspacing="1" cellpadding="2"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Kho vận > Tồn kho an toàn</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Tồn kho an toàn </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Chọn kho </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                        	
		                        	<select name = "khoIds" onchange="submitform();" class="select2" style="width: 700px;" multiple="multiple" >
										<option value=""></option>
										<% if (clRs != null)
										{
											while (clRs.next())
											{
												if ( obj.getClId().contains( clRs.getString("pk_seq") ) )
												{
												%>
													<option value="<%=clRs.getString("pk_seq")%>" selected="selected"><%=clRs.getString("TEN")%></option>
												<% } else { %>
													<option value="<%=clRs.getString("pk_seq")%>"><%=clRs.getString("TEN")%></option>
										<% } } clRs.close(); } %>
									</select>
		                        </TD>          
		                  </TR> 
		                  
		                  <TR>
		                  		<td colspan="2">
		                  		
		                  			<TABLE width="100%" border="0" cellspacing="2" cellpadding="4">
										<TR class="tbheader">
											<TH width="20%">Mã </TH>
											<TH width="45%">Tên sản phẩm</TH>
											<TH width="15%">Đơn vị</TH>								
											<TH width="20%">Tồn kho an toàn</TH>
										</TR>
										
										<% if(tkatRs_1 != null){
											String lightrow = "tblightrow";
											String darkrow = "tbdarkrow";
											int i = 0;
											while(tkatRs_1.next()){		
												if(i % 2 == 0){
												
												%>
												<TR class = "<%= lightrow %>">
												
									<% 			}else{	%>
												<TR class = "<%= darkrow %>">
									<% 			}	%>
													<TD align = "center"><%= tkatRs_1.getString("MA") %></TD>
													<TD align = "left"><%= tkatRs_1.getString("TEN") %></TD>
													<TD align = "center"><%= tkatRs_1.getString("DONVI") %></TD>
													<TD align = "center">
														<INPUT TYPE = "hidden" NAME = "spId_1" VALUE = "<%= tkatRs_1.getString("PK_SEQ") %>" >
														<INPUT TYPE = "TEXT" onkeyup="Dinhdang(this)"  onkeypress="return isNumberKey2(event)" NAME = "tkat_1" VALUE = "<%= formatter.format(tkatRs_1.getDouble("tonantoan")) %>" style = "text-align:right; size:90%"  >
													</TD>
													
												</TR>
										
									<%			i++;
											}
										}	%>
										
									</TABLE>
		                  		
		                  		</td>
		                  </TR>
		                  
		                  
		                  <%-- 
		                  <TR>
		                  		<td colspan="2">
		                  		
		                  			<ul class="tabs">
		                  			
			                  			<li><a href="#">Kho miền Bắc</a></li>
			                  			<li><a href="#">Kho miền Trung</a></li>
			                  			<li><a href="#">Kho miền Nam</a></li>
			                  		</ul>
		                  			
		                  			<div class="panes">
		                  			
										<div>
										
											<TABLE width="80%" border="0" cellspacing="2" cellpadding="4">
												<TR class="tbheader">
													<TH width="10%">Mã </TH>
													<TH width="25%">Tên sản phẩm</TH>
													<TH width="5%">Đơn vị</TH>								
													<TH width="15%">DS trung bình 3 tháng</TH>
													<TH width="15%">DS cùng kỳ năm trước </TH>
													<TH width="10%">Tồn kho an toàn</TH>
													
													
												</TR>
									<% if(tkatRs_1 != null){
											String lightrow = "tblightrow";
											String darkrow = "tbdarkrow";
											int i = 0;
											while(tkatRs_1.next()){		
												if(i % 2 == 0){
												
												%>
												<TR class = "<%= lightrow %>">
												
									<% 			}else{	%>
												<TR class = "<%= darkrow %>">
									<% 			}	%>
													<TD align = "center"><%= tkatRs_1.getString("MA") %></TD>
													<TD align = "left"><%= tkatRs_1.getString("TEN") %></TD>
													<TD align = "center"><%= tkatRs_1.getString("DONVI") %></TD>
													<TD align = "center"><%= tkatRs_1.getString("AVG3M") %></TD>
													<TD align = "center"><%= tkatRs_1.getString("LASTYEAR") %></TD>
													<TD align = "center">
														<INPUT TYPE = "hidden" NAME = "spId_1" VALUE = "<%= tkatRs_1.getString("SPID") %>" >
														<INPUT TYPE = "TEXT" onkeyup="Dinhdang(this)"  onkeypress="return isNumberKey2(event)" NAME = "tkat_1" VALUE = "<%= formatter.format(tkatRs_1.getDouble("TKAT")) %>" style = "text-align:right; size:90%"  >
													</TD>
													
												</TR>
										
									<%			i++;
											}
										}	%>
											</TABLE>
												
										</div>
										
										<div>
			                  			
											<TABLE width="80%" border="0" cellspacing="2" cellpadding="4">
												<TR class="tbheader">
													<TH width="10%">Mã </TH>
													<TH width="25%">Tên sản phẩm</TH>
													<TH width="5%">Đơn vị</TH>								
													<TH width="15%">DS trung bình 3 tháng</TH>
													<TH width="15%">DS cùng kỳ năm trước </TH>
													<TH width="10%">Tồn kho an toàn</TH>
												</TR>
									<% if(tkatRs_2 != null){
											String lightrow = "tblightrow";
											String darkrow = "tbdarkrow";
											int i = 0;
											while(tkatRs_2.next()){		
												if(i % 2 == 0){
												
												%>
												<TR class = "<%= lightrow %>">
												
									<% 			}else{	%>
												<TR class = "<%= darkrow %>">
									<% 			}	%>
													<TD align = "center"><%= tkatRs_2.getString("MA") %></TD>
													<TD align = "left"><%= tkatRs_2.getString("TEN") %></TD>
													<TD align = "center"><%= tkatRs_2.getString("DONVI") %></TD>
													<TD align = "center"><%= tkatRs_2.getString("AVG3M") %></TD>
													<TD align = "center"><%= tkatRs_2.getString("LASTYEAR") %></TD>
													<TD align = "center">
														<INPUT TYPE = "hidden" NAME = "spId_2" VALUE = "<%= tkatRs_2.getString("SPID") %>" >
														<INPUT TYPE = "TEXT" onkeyup="Dinhdang(this)"  onkeypress="return isNumberKey2(event)" NAME = "tkat_2" VALUE = "<%= formatter.format(tkatRs_2.getDouble("TKAT")) %>" style = "text-align:right; size:90%"  >
													</TD>
													
												</TR>
										
									<%			i++;
											}
										}	%>
												
											</TABLE>
										</div>
										
										<div>
			                  			
											<TABLE width="80%" border="0" cellspacing="2" cellpadding="4">
												<TR class="tbheader">
													<TH width="10%">Mã </TH>
													<TH width="25%">Tên sản phẩm</TH>
													<TH width="5%">Đơn vị</TH>								
													<TH width="15%">DS trung bình 3 tháng</TH>
													<TH width="15%">DS cùng kỳ năm trước </TH>
													<TH width="10%">Tồn kho an toàn</TH>
												</TR>
											
									<% if(tkatRs_3 != null){
											String lightrow = "tblightrow";
											String darkrow = "tbdarkrow";
											int i = 0;
											while(tkatRs_3.next()){		
												if(i % 2 == 0){
												
												%>
												<TR class = "<%= lightrow %>">
												
									<% 			}else{	%>
												<TR class = "<%= darkrow %>">
									<% 			}	%>
													<TD align = "center"><%= tkatRs_3.getString("MA") %></TD>
													<TD align = "left"><%= tkatRs_3.getString("TEN") %></TD>
													<TD align = "center"><%= tkatRs_3.getString("DONVI") %></TD>
													<TD align = "center"><%= tkatRs_3.getString("AVG3M") %></TD>
													<TD align = "center"><%= tkatRs_3.getString("LASTYEAR") %></TD>
													<TD align = "center">
														<INPUT TYPE = "hidden" NAME = "spId_3" VALUE = "<%= tkatRs_3.getString("SPID") %>" >
														<INPUT TYPE = "TEXT" onkeyup="Dinhdang(this)"  onkeypress="return isNumberKey2(event)" NAME = "tkat_3" VALUE = "<%= formatter.format(tkatRs_3.getDouble("TKAT")) %>" style = "text-align:right; size:90%"  >
													</TD>
													
												</TR>
										
									<%			i++;
											}
										}	%>
												
											</TABLE>
										</div>


									</div>
		                  		</td>
		                  </TR> 
		                   --%>
						</TABLE>
							
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>

</BODY>
</HTML>

<%
	if( clRs != null) clRs.close();
	if( tkatRs_1 != null) tkatRs_1.close();
	if( tkatRs_2 != null) tkatRs_2.close();
	if( tkatRs_3 != null) tkatRs_3.close();

	obj.DbClose();
	session.setAttribute("tkatBean", null) ; 
	
%>

<%}%>

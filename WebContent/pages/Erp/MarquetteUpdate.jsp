<%@page import="geso.traphaco.erp.beans.marquette.IMarquetteUpdate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IMarquetteUpdate obj = (IMarquetteUpdate)session.getAttribute("obj"); %>
<% ResultSet spList = (ResultSet)obj.getspList(); %>


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
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<SCRIPT language="javascript" type="text/javascript">

 function confirmLogout(){
    if(confirm("Ban co muon dang xuat?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 {
     document.forms['nhForm'].submit();
 }
 function saveform()
 {
	var mamaket = document.getElementById('kbh');
	var diengiai = document.getElementById('diengiai');
	var tungay = document.getElementById('tungay');
	var denngay = document.getElementById('denngay');
	var splist = $("#masp").val().trim();
	if(mamaket.value== "") {
		alert("Bạn chưa điền mã maket!");
		mamaket.focus();
		return;
	}
	if (diengiai.value.trim().length == 0) {
		alert("Nhập diễn giải");
		diengiai.focus();
		return ;
	}
	if(splist === "0") {
		alert("Bạn chưa chọn sản phẩm!");
		splist.focus();
		return;
	}
	
	if(tungay.value == "") {
		alert("Bạn chưa chọn từ ngày!");
		tungay.focus();
		return;
		
	}
	if(denngay.value == "")
	{
		alert('Bạn chưa chọn đến ngày');
		denngay.focus();
		return;
	}
	
 	document.forms['nhForm'].action.value = 'save';
    document.forms['nhForm'].submit();
 }	

</SCRIPT>
<script type="text/javascript">
	$(document).ready(function() {		
		$( "select" ).select2(); 
	});	
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name='nhForm' method="post" action="../../MarquetteUpdateSvl">
<input type="hidden" name="action" value='1'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<input type="hidden" name="id" value='<%= obj.getId() %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   	&nbsp; Dữ liệu nền > Sản phẩm > Marquette > Tạo mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=userTen %>&nbsp;  </TD> 
						    </tr>
   
						   	<tr>
						   		<TD align="left" height="5" colspan="4" class=""></td>
   
  							</tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../MarquetteSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
		    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= obj.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
				
				  	<tr>
					   <TD align="left" colspan="4" class="legendtitle">
							<FIELDSET>
							<LEGEND class="legendtitle">Thông tin marquette
							</LEGEND>
							<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
								<TR>
								  <TD width="15%" class="plainlabel" >Mã maket <FONT class="erroralert">*</FONT></TD>
								  <TD  class="plainlabel" ><INPUT name="ten" id="kbh" type="text" value='<%= obj.getTen() %>' size="20"></TD>
								  <TD width="15%" class="plainlabel" >Số đăng ký <FONT class="erroralert">*</FONT></TD>
								  <TD  class="plainlabel" ><INPUT name="sodangky" id="sodangky" type="text" value='<%= obj.getSodangky() %>' size="20"></TD>
							 	  
							  </TR>
								
								<TR>
								 <TD class="plainlabel">Sản phẩm</TD>
				                  <TD class="plainlabel">
				                      <select name="masp" id="masp" style="width: 250px;">
				                        	<option value="0"  ></option>
											<% 
				                                try{											
				                                    while (spList.next()){%>													
				                                        <%	if(spList.getString("pk_seq").equals(obj.getspId())){ %>											
				                                                <option value= <%=spList.getString("pk_seq")%> selected><%= spList.getString("ten") %></option>															
				                                            <%}else{%>
				                                                <option value= <%=spList.getString("pk_seq")%> ><%= spList.getString("ten") %></option>																																		
				                                        <%	}
				                                    }
				                                } catch(java.sql.SQLException e){
				                                    
				                                }
				                            %>
				                        </select>
				                  </TD>
								   <TD width="15%" class="plainlabel" >Quy cách <FONT class="erroralert">*</FONT></TD>
								  <TD  class="plainlabel" ><INPUT name="quycach" id="quycach" type="text" value='<%= obj.getquycach() %>' size="20"></TD>
								</TR>
								
								<tr>
								<TD class="plainlabel" valign="middle" >Từ ngày </TD>   
		                        <TD class="plainlabel">
								  <input name="tungay" id="tungay" type="text"  class="days"  value="<%=obj.getTungay() %>" readonly>
							  	</TD>
		                       
		                        <TD class="plainlabel" valign="middle" >Đến ngày </TD>   
		                        <TD class="plainlabel">
								  <input name="denngay" id="denngay" type="text"  class="days"  value="<%=obj.getDenngay() %>" readonly>
							  	</TD> 
								</tr>
							  	<TR>
									<TD class="plainlabel" width="15%">Diễn giải</TD>
									<TD class="plainlabel"><textarea style="width: 500px;background-color: white;color: black;" rows="3" cols="50" name="diengiai" id="diengiai"> <%= obj.getDiengiai().trim() %></textarea>
									</TD>
									<td class="plainlabel" colspan="4"></td>
								</TR>
								<TR>
								  <TD  class="plainlabel" colspan="4"><label>
									<%  if (obj.getTrangthai().equals("1")){%>
									  	<input name="trangthai" type="checkbox" value ="1" checked="checked">
									<%} else {%>
										<input name="trangthai" type="checkbox" value ="1">
									<%} %>
								    Hoạt động</label></TD>
								
							  </TR>
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
	obj.DBClose();
	}%>
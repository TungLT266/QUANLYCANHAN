<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.tieuchikiemnghiem.IErpTieuChiKiemNghiem"%>
<%@page import="geso.traphaco.center.util.*"%>

<%String userId = (String) session.getAttribute("userId");  
String userTen = (String) session.getAttribute("userTen");  	
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else {
	IErpTieuChiKiemNghiem obj = (IErpTieuChiKiemNghiem)session.getAttribute("obj");%>
	
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
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<SCRIPT language="JavaScript" type="text/javascript">
	function save() {
		if(document.getElementById("ma").value.trim() == ""){
			document.getElementById("dataerror").value = "Bạn chưa nhập mã loại tiêu chí kiểm nghiệm.";
			return false;
		}
		
		if(document.getElementById("ten").value.trim() == ""){
			document.getElementById("dataerror").value = "Bạn chưa nhập tên loại tiêu chí kiểm nghiệm.";
			return false;
		}
		
		document.forms["tieuchikiemnghiem"].action.value = "save";
		document.forms["tieuchikiemnghiem"].submit(); 
    }
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="tieuchikiemnghiem" method="post" action="../../ErpTieuChiKiemNghiemUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value='<%= obj.getId() %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr height="22">
							  	<%if(obj.getId().length()>0){%>
							  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Hồ sơ kiểm nghiệm > Loại tiêu chí kiểm nghiệm > Cập nhật</TD>
							  	<%} else { %>
							  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Hồ sơ kiểm nghiệm > Loại tiêu chí kiểm nghiệm > Tạo mới</TD>
						  		<%} %>
								<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
							</tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left">
									<A href="../../ErpTieuChiKiemNghiemSvl?userId=<%=userId %>" >
										<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset">
									</A>
								</TD>
							    <TD width="2" align="left" ></TD>
						    	<TD width="30" align="left" >
						    		<div id="btnSave">
						    			<A href="javascript: save()" >
						    				<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset">
					    				</A>
						    		</div>
							    </TD>
								<TD >&nbsp; </TD>						
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
							<LEGEND class="legendtitle">Thông báo </LEGEND>
		    				<textarea name="dataerror" id="dataerror" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  	<TD height="100%" width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle" style="color:black">Loại tiêu chí kiểm nghiệm </LEGEND>
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          		<TR>
	                          		<TD class="plainlabel" valign="middle" width="250px" >Mã loại tiêu chí kiểm nghiệm <FONT class="erroralert">*</FONT></TD>   
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text" style="width:350px" name="ma" id="ma" value="<%=obj.getMa() %>">
			                        </TD>
                  				</TR>
                  			
	                          	<TR>
	                          		<TD class="plainlabel" valign="middle" width="250px">Tên loại tiêu chí kiểm nghiệm <FONT class="erroralert">*</FONT></TD>
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text" style="width:350px" name="ten" id="ten" value="<%= obj.getTen() %>"  > 
			                        </TD>          
			                  	</TR>
		                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="250px">Trạng thái </TD>   
			                        <TD class="plainlabel" valign="middle" >
			                            <%if(obj.getTrangthai().equals("1")){ %>
			                            	<input type="checkbox" name="trangthai" value="1" checked><i> Hoạt động</i>
			                            <%} else { %>
			                            	<input type="checkbox" name="trangthai" value="1" ><i> Hoạt động</i>
			                            <%} %>
			                        </TD>                
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
	if (obj != null) {
		obj.DBClose();
		obj = null;
	}
	session.removeAttribute("obj");
} %>

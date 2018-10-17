<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhomhang.imp.NhomHang" %>
<%@ page  import = "geso.traphaco.center.beans.nhomhang.INhomHang" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% INhomHang nhBean = (INhomHang)session.getAttribute("nhBean"); %>
<% 	
	ResultSet spRs =(ResultSet) nhBean.getSpRs();
	ResultSet nhanhangRs =(ResultSet) nhBean.getNganhHangRs();
	ResultSet nganhhangRs =(ResultSet) nhBean.getNganhHangRs();
	ResultSet rsnhomhang =(ResultSet) nhBean.getRsnhomhang();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OneOne - Project</TITLE>
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
<script src="../scripts/ui/jquery.ui.datepicker.js"type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<style type="text/css" >
    .black_overlay{
        display: none;
        position: absolute;
        top: 0%;
        left: 0%;
        width: 100%;
        height: 900%;
        background-color: black;
        z-index:1001;
        -moz-opacity: 0.8;
        opacity:.80;
        filter: alpha(opacity=80);
    }
    .white_content {
        display: none;
        position: absolute;
        top: 25%;
        left: 25%;
        width: 50%;
        height: 50%;
        padding: 16px;
        /*border: 10px solid #CCC;
        background-color: white;*/
        z-index:1002;
        overflow: auto;
    }
 
 </style>
<script>
$(document).ready(function()
{
	$("#kenhId").select2();
	$("#vungId").select2();
	$("#kvId").select2();	
	$("#tinhId").select2();
	$("#quanhuyenId").select2();
});
</script>



<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["nspForm"].submit();
}

function save()
{
	document.nspForm.action.value="save";
    document.forms["nspForm"].submit();       
}


function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nspForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}




</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="nspForm" method="post" action="../../NhomHangUpdateSvl" >
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="0">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản phẩm > Nhóm hàng > Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../NhomHangSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: save();" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=nhBean.getMsg()%></textarea>
						<% nhBean.getMsg(); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin nhóm hàng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="12%" class="plainlabel" >Mã nhóm<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
                                <INPUT type="text" name="ma" style="width:300px" value='<%= nhBean.getMa() %>'></TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Tên nhóm</TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="ten" style="width:300px" value='<%= nhBean.getTen() %>'>
                                </TD>
						  </TR>
						  
						  <%-- <TR>
						  	<TD class="plainlabel">Ngành hàng</TD>
						  	  	<TD class="plainlabel">
								  <SELECT name="nhId" id="nhId"   onChange = "ajaxOption('tbNpp')"  style="width:150px" multiple>
						  	  		<OPTION value="0" ></OPTION>	
						  	  		<% if(nganhhangRs!= null)
						  	  		{						  	  			
						   					while (nganhhangRs.next())
						   					{
						  	  					if (nhBean.getNganhHangId().indexOf(nganhhangRs.getString("pk_seq"))  >=0)
						  	  					{%>
						  	  						<OPTION value=<%= nganhhangRs.getString("pk_seq")%> selected><%= nganhhangRs.getString("ten")%></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value=<%= nganhhangRs.getString("pk_seq")%> ><%= nganhhangRs.getString("ten") %></OPTION>
						  	  	<%				  }
						  	  				
						  	  				}
						  	  			
						  	  		}%>						  	  			
						  	  	</SELECT>
						  	  	</TD>
						  	 </TR> --%>
						  
						  					  	  								  
						</TABLE>
						<div id="tbNpp">
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4" >							
								<TR class="tbheader">
								<TH width="15%">Mã sản phẩm</TH>
								<TH width="55%">Tên sản phẩm</TH>
								<TH width="10%">Đơn vị tính</TH>
								<TH width="10%">Chọn
									<input type="checkbox" name="chon" onClick="checkedAll(document.nspForm.spId)">
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
								   rs = spRs;
								   if (!(rsnhomhang == null)){			
									    
								   		while (rsnhomhang.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
																												
												
												<TD align="left" class="textfont"><%= rsnhomhang.getString("spMa") %></TD>
												<TD align="center"><div align="left"><%= rsnhomhang.getString("spTEN") %></div></TD>
												<TD align="center"><div align="left"><%= rsnhomhang.getString("spDONvI") %></div></TD>
												
												<TD align="center">
												<input type="checkbox" name="spId" value='<%= rsnhomhang.getString("spId") %>' checked>
												
												</TD>
											
												</TR>
												
							<% 			m++;} 
								   		rsnhomhang.close();
									}	
							   	      
								   if (!(rs == null)){			
									    
								   		while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
																												
												
												<TD align="left" class="textfont"><%= rs.getString("spMa") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("spTEN") %></div></TD>
												<TD align="center"><div align="left"><%= rs.getString("spDONvI") %></div></TD>
												
												<TD align="center">
												<input type="checkbox" name="spId" value='<%= rs.getString("spId") %>'>
												<%-- <% if (nhBean.getSpId().indexOf(rs.getString("spId")) >=0  ) {%>
														<input type="checkbox" name="spId" value='<%= rs.getString("spId") %>' checked>
												<%}else{ %>
														<input type="checkbox" name="spId" value='<%= rs.getString("spId") %>'>
												<%} %> --%>
												</TD>
											
												</TR>
												
							<% 			m++;} 
								   		rs.close();
									}	
							   	      %>
						</TABLE>			
						</div>				
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
<%}%>
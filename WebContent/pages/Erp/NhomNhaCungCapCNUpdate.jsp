<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.nhomnhacungcapcn.INhomnhacungcapcn" %>
<%@ page  import = "geso.traphaco.erp.beans.nhomnhacungcapcn.imp.Nhomnhacungcapcn" %>
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
		response.sendRedirect("/Phanam/index.jsp");
	}else{ %>

<%  INhomnhacungcapcn nNccBean = (INhomnhacungcapcn)session.getAttribute("nNccBean"); 
	ResultSet nccList = (ResultSet)nNccBean.geNccList();
	ResultSet nccSelected = (ResultSet)nNccBean.getNccSelected();
	
	  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>SalesUp - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="JavaScript" type="text/javascript">
function goBack(){
	window.history.back();
}
function submitform()
{
    document.forms['nkhForm'].submit();
}

function filterVung()
{
    document.nkhForm.action.value='filter';
    document.nkhForm.kvId.value='0';
    document.nkhForm.nppId.value='0';
    document.forms['nkhForm'].submit();       
}

function filterKv()
{
    document.nkhForm.action.value='filter';
    document.nkhForm.nppId.value='0';
    document.forms['nkhForm'].submit();   
    
}

function filterNpp()
{
    document.nkhForm.action.value='filter';
    document.forms['nkhForm'].submit();       
}

function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nkhForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="nkhForm" method="post" action="../../NhomnhacungcapcnUpdateSvl" >
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="nkhId" value='<%= nNccBean.getId()  %>'>
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; Nhóm nhà cung cấp &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:goBack()" ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo</LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=nNccBean.getMessage()%></textarea>
						<% nNccBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Nhóm nhà cung cấp </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
							  	<TD class="plainlabel">Tên nhóm</TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="ten" style="width:400px" value='<%= nNccBean.getTen() %>'></TD>
						    </TR>
							<TR>
							  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="diengiai" style="width:400px" value='<%= nNccBean.getDiengiai() %>'></TD>
						  </TR>
						  
				  		 
						  	
						  	<TR>
						  		
							  <TD colspan="2" class="plainlabel" ><label>
							  	<% if(nNccBean.getTrangthai().equals("1")){ %>
							    	<input name="trangthai" type="checkbox" value="1" checked >
							    <%}else{ %>
							    	<input name="trangthai" type="checkbox" value="0" >
							    <%} %>
							   Hoạt động</label></TD>
						  </TR>
						  
						</TABLE>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">							
								<TR class="tbheader">
									<TH width="28%">Mã nhà cung cấp </TH>
									<TH width="60%">Tên nhà cung cấp </TH>
									<TH width="12%">Chọn
										<input type="checkbox" name="chon" onClick="checkedAll(document.nkhForm.khachhang)">								
									
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
						   	       rs = nccSelected;
								   
								   if (!(rs == null)){			
								   		while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
												<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
												<TD align="center">
													<input type="checkbox" name="ncc" value='<%= rs.getString("pk_seq") %>' checked>									
												</TD>

												</TR>
													
									<% 			m++;}
									}	
			
							   	    rs = nccList;
									   
									if (!(rs == null)){													    
										while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
												<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
												<TD align="center">
													<input type="checkbox" name="ncc" value='<%= rs.getString("pk_seq") %>'>									
												</TD>

												</TR>
													
								<% 			m++;}
										}	
								  
									%>	
								   
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
	if(nccList != null) nccList.close(); 
	if(nccSelected != null) nccSelected.close();
	
	nNccBean.DBClose();
%>
<%}%>
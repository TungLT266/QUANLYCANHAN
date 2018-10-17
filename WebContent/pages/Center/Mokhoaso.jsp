<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.mokhoaso.IMokhoaso" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>


<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	String url="";
	url = util.getUrl("MokhoasoSvl","");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IMokhoaso mksBean = (IMokhoaso)session.getAttribute("mksBean"); %>
<% ResultSet vung = (ResultSet)mksBean.getVung(); %>
<% ResultSet kv = (ResultSet)mksBean.getKhuvuc(); %>
<% ResultSet npp = (ResultSet)mksBean.getNpp(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	document.forms['mksForm'].submit();
}

function Mokhoaso()
{
	document.forms['mksForm'].action.value= 'open';
	document.forms['mksForm'].submit();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="mksForm"  method="post" action="../../MokhoasoSvl" >
<input type="hidden" name="action" value="0">
<input name="userId" type="hidden" value='<%=userId %>' size="30">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%=url %> </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width :100%" rows="1"><%= mksBean.getMsg() %></textarea>

						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%" border = '0'>
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Chọn Chi nhánh / Đối tác</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
						    	<TD class="plainlabel"><B></b>Khu Vực </TD>
							    <TD class="plainlabel"><SELECT name="vungId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{
								    	  	if(vung != null){
								    	  		while(vung.next()){ 
								    	  			System.out.println(vung.getString("TEN"));
								    				if(vung.getString("VUNGID").equals(mksBean.getVungId())){ %>
								      				<option value='<%=vung.getString("VUNGID")%>' selected><%=vung.getString("TEN") %></option>
								      			  <%}else{ %>
								     				<option value='<%=vung.getString("VUNGID")%>'><%=vung.getString("TEN") %></option>
								     		<%		}
								    				
								    	  		}								    			
								    	  	}
								    	  }catch(java.sql.SQLException e){} 
								      
								     		%>	  
                        				</SELECT>			
                        		</TD>
							    
							</TR>  
							<TR>  
							    <TD class="plainlabel"><B></B>Khu Vực </TD>
							    <TD class="plainlabel"><SELECT name="kvId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{
								    	  	if(kv != null){
								    	  		while(kv.next()){ 
								    				if(kv.getString("KVID").equals(mksBean.getKhuvucId())){ %>
								      					<option value='<%=kv.getString("KVID")%>' selected><%=kv.getString("TEN") %></option>
								      			  <%}else{ %>
								     					<option value='<%=kv.getString("KVID")%>'><%=kv.getString("TEN") %></option>
								     			  <%}
								    				
								    	  		}
								    	  	}
								    	  }catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
                        	</TR>
                        	<TR>	
							    <TD class="plainlabel"><B></B>Chi nhánh / Đối tác </TD>
							    <TD class="plainlabel"><SELECT name="nppId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{
								    	  	if(npp != null){	
								    	  		while(npp.next()){ 
								    				if(npp.getString("NPPID").equals(mksBean.getNppId())){ %>
								      					<option value='<%=npp.getString("NPPID")%>' selected><%=npp.getString("TEN") %></option>
								      			  <%}else{ %>
								     					<option value='<%=npp.getString("NPPID")%>'><%=npp.getString("TEN") %></option>
								     			  <%}
								    			}
								    	  	}
								    	  }catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
                        		
					      </TR>
					      <TR>
					      		<TD class="legendtitle" colspan=3>
					      		<a class="button3" href="javascript:Mokhoaso()">
					      		 	<img style="top: -4px;" src="../images/New.png" alt="">Mở khóa sổ 
					      		 </a>   
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
try{
	if( mksBean != null){
		mksBean.DBClose();
		mksBean=null;
	}
	if(vung!=null){
		vung.close();
	}
	if(kv!=null){
		kv.close();
	}
	if(npp!=null){
		npp.close();
	}
	
	
}
catch (Exception e) {}

}%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.phanbodathang.IPhanbodathang" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IPhanbodathang pbdhBean = (IPhanbodathang)session.getAttribute("pbdh"); %>
<% ResultSet sp = pbdhBean.getSp(); %>
<% ResultSet vung = pbdhBean.getVung(); %>
<% ResultSet kv = pbdhBean.getKv(); %>
<% ResultSet pb = pbdhBean.getPhanbo(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
    
    <script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
    
     <script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
    
<SCRIPT language="JavaScript" type="text/javascript">

	function submitform()
	{
		document.forms['pbdhForm'].setAttribute('enctype', '', 0);
	    document.forms['pbdhForm'].submit();
	}
	
	function upload()
	{
		document.forms['pbdhForm'].setAttribute('enctype', "multipart/form-data", 0);
	    document.forms['pbdhForm'].submit();
	}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="pbdhForm" ENCTYPE="multipart/form-data" method="post" action="../../PhanbodathangSvl" >
<input type="hidden" name="enctype" value="">
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý bán hàng  &gt; Phân bổ đặt hàng </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:upload();"  ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A></TD>

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
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width:100%" rows="1" readonly="readonly" ><%= pbdhBean.getMsg() %></textarea>
    					
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Phân bổ đặt hàng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
					  		 <TR>
									  <TD class="plainlabel" width="10%">Từ ngày </TD>
									  <TD colspan="3" class="plainlabel">
									  <table border=0 cellpadding="0" cellspacing="0">
                                              <TR><TD>
									    <input class="days" type="text" name="tungay" id ="tungay" style="width:100%" value="<%= pbdhBean.getTungay() %>" >
									</TD></TR>
                                        </table>
                             </TR>
                             <TR>
									  <TD class="plainlabel">Đến ngày </TD>
									  <TD colspan="3" class="plainlabel">
									  <table border=0 cellpadding="0" cellspacing="0">
                                              <TR><TD>
									    <input class="days" type="text" name="denngay" id ="denngay" style="width:100%" value="<%= pbdhBean.getDenngay() %>" >
									</TD></TR>
                                    </table>	
                             </TR>

						     <TR>
							  	<TD class="plainlabel">Sản phẩm</TD>
						  	  	<TD class="plainlabel">
							  	  	<input type="text" name="spId"  style="width:30%" value="<%= pbdhBean.getSpId() %>" onChange = submitform();>  	  	
						  	  	
						  	  	</TD>
						  </TR>
						
						  <TR>
							  	<TD class="plainlabel">Chọn tập tin</TD>
						  	  	<TD class="plainlabel"><INPUT type="file" name="filename" size="40" value=''></TD>
						  </TR>
						  
 						  <TR>							  	
						  	  	<TD class="plainlabel" colspan="2">
						  	  	<a class="button2"  href="javascript:upload()">
									<img style="top: -4px;" src="../images/button.png" alt="">Cập nhật</a>&nbsp;&nbsp;&nbsp;&nbsp;	
						  	  	</TD>
						  </TR>

						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Chi nhánh / Đối tác </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="1">
						  <TR>
						    	<TD class="legendtitle" width="30%" colspan="2"> Miền
							    <SELECT name="vungId" onChange = "submitform();">
								    <option value=""></option>
								      <% try{
								    	  if(vung != null){
								    	  	while(vung.next()){ 
								    			if(vung.getString("pk_seq").equals(pbdhBean.getVungId())){ %>
								      				<option value='<%=vung.getString("pk_seq")%>' selected><%=vung.getString("diengiai") %></option>
								      		 <%}else{ %>
								     				<option value='<%=vung.getString("pk_seq")%>'><%=vung.getString("diengiai") %></option>
								     		 <%}
								    		}
								    	  }	
								    	 }catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
							    
							    
							    <TD class="legendtitle" width="30%" colspan="2"> Khu Vực 
							    <SELECT name="kvId" onChange = "submitform();">
								    <option value=""></option>
								      <% try{
								    	  if(kv != null){
								    	  	while(kv.next()){ 
								    			if(kv.getString("pk_seq").equals(pbdhBean.getKvId())){ %>
								      				<option value='<%=kv.getString("pk_seq")%>' selected><%=kv.getString("diengiai") %></option>
								      		  <%}else{ %>
								     				<option value='<%=kv.getString("pk_seq")%>'><%=kv.getString("diengiai") %></option>
								     		  <%}
								    		}
								    	  }	
								    	 }catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
                        		<TD class="legendtitle" width="40%" colspan="2" align="left">&nbsp;</TD>
                        				
					      </TR>

						  <TH width="5%" class="plainlabel">ID NPP</TH>
						  <TH width="5%" class="plainlabel">Mã NPP</TH>
						  <TH width="40%" class="plainlabel">Tên Chi nhánh / Đối tác</TH>
						  <TH width="10%" class="plainlabel">Sản phẩm</TH>
						  <TH width="7%" class="plainlabel">Kênh</TH>
						  <TH width="7%" class="plainlabel">Phân bổ</TH>
						  <TH width="7%" class="plainlabel">Đã đặt hàng</TH>
						  <TH width="7%" class="plainlabel">Còn lại</TH>
						  <TH width="7%" class="plainlabel">Từ ngày</TH>
						  <TH width="7%" class="plainlabel">Đến ngày</TH>
						  						  
						  <%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
						  <% try{
							    String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int m = 0;
								if(pb != null){
									while(pb.next()){ 
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
									
									<TD align="left"><div align="center"><%= pb.getString("PK_SEQ")%></div></TD>
									
									<TD align="left"><div align="center"><%= pb.getString("MA")%></div></TD>
									                                   
									<TD align="left"><div align="left"><%=pb.getString("TEN") %></div></TD>
									    
									<TD align="left"><div align="center"><%=pb.getString("MASP") %></div></TD>
									
									<TD align="left"><div align="center"><%=pb.getString("KBH") %></div></TD>

									<TD align="left"><div align="right"><%=pb.getInt("SOLUONG") %></div></TD>

									<TD align="left"><div align="right"><%=pb.getInt("DADAT") %></div></TD>  

				  					<TD align="left"><div align="right"><%=pb.getInt("CONLAI") %></div></TD>
				  					
				  					<TD align="left"><div align="center"><%= pb.getString("TUNGAY")%></div></TD>
				  					
				  					<TD align="left"><div align="center"><%= pb.getString("DENNGAY")%></div></TD>
					  				
					  			</TR>
							  
						  		<% m++ ;} 
						  		
						  		}%>		
						  		
						  <%}catch(java.sql.SQLException e){}%>

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
	if(pb!=null){
		pb.close();
		
	}
	if(kv!=null){
		kv.close();
	}
	if(sp!=null){
		sp.close();
	}
	if(vung!=null){
		vung.close();
		
	}
	pbdhBean.DBClose();
}catch(Exception er){
	
}
}%>
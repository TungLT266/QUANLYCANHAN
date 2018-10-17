<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.ILNSBanggiaban" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% ILNSBanggiaban bgbanBean = (ILNSBanggiaban)session.getAttribute("bgbanBean"); %>
<% ResultSet dvkd = (ResultSet)bgbanBean.getDvkdIds(); %>
<% ResultSet kenh = (ResultSet)bgbanBean.getKenhIds(); %>
<% String[] spIds = bgbanBean.getSpIds() ; %>
<% String[] masp = bgbanBean.getMasp() ; %>
<% String[] tensp = bgbanBean.getTensp() ; %>
<% String[] tensp2 = bgbanBean.getTensp2() ; %>
<% String[] giamua = bgbanBean.getGiaban() ; %>
<% String[] giamoi = bgbanBean.getGiamoi() ; %>
<% String[] tthai = bgbanBean.getTthai(); %>
<% String[] dv = bgbanBean.getDonvi(); %>
<% String block = bgbanBean.getBlock(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
   document.forms["bgbanForm"].submit();
}

function saveform()
{
	document.forms['bgbanForm'].action.value='display';
    document.forms["bgbanForm"].submit();
}

function inExcel()
{
	document.forms['bgbanForm'].action.value='excel';
    document.forms["bgbanForm"].submit();
}

</SCRIPT>
 <script type="text/javascript">
 
 function checkedAll() {
		var spIds = new Array(<%= bgbanBean.getMaspstr() %>);	
		for (var i =0; i < spIds.length; i++) 
	 	{
		 	var cb = "chbox" + spIds[i];
		 	var objCheckBoxes = document.forms["bgbanForm"].elements[cb];
			if (document.forms["bgbanForm"].elements["chon"].checked == false){
				objCheckBoxes.checked = false;
			}else{
				objCheckBoxes.checked = true;
			}
	 	}
 }
 </script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgbanForm" method="post" action="../../ErpLNSBanggiabanUpdateSvl">
<input type="hidden" name="userId" value='<%=bgbanBean.getUserId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= bgbanBean.getId() %>'>

<TABLE width="100%" border="0" cellspacing="1" cellpadding="1"	>
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						   <tr height="22">
 							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý ngân sách &gt; Bảng giá bán &gt; Hiển thị</TD>
 							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%= bgbanBean.getUserTen() %>&nbsp;  </TD>
					     </tr>
						</table>
					 </TD>
				  </TR>	
		  	</TABLE>


			<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="../../ErpLNSBanggiabanSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
				    	<TD width="2" align="left" >&nbsp;</TD>
				    	<TD width="30" height="30" align="left" ><A href="javascript: inExcel()" ><IMG src="../images/excel.gif" title="In file Excel" alt="In file Excel" border = "1"  style="border-style:outset" width="30" height="30"></A></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>

		<TABLE width="100%"  border = "0" cellspacing="0" cellpadding="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="3" ><%=bgbanBean.getMessage()%></textarea>
						<% bgbanBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

		 	<TR>
				<TD >
			        <FIELDSET>
			        <LEGEND class="legendtitle">&nbsp;Bảng giá bán </LEGEND>
					<TABLE width="100%"cellpadding="0" cellspacing="1">
						<TR>
							<TD>				
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="center">
											<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
												<TR>
													<TD width="15%" class="plainlabel">Tên bảng giá <FONT class="erroralert">*</FONT></TD>
													<TD  class="plainlabel"><INPUT name="bgTen" value="<%= bgbanBean.getTen() %>"
										type="text"  style="width:300px"></TD>
												</TR>
												<TR>
								    				<TD class="plainlabel">Đơn vị kinh doanh</TD>
								      				<TD class="plainlabel">
								      				<SELECT name="dvkdId" disabled="disabled"  style="width:300px">								      
								  	 					<option value =""></option>
								  	 					
								  	 					<%
								  	 					//System.out.println("DVKD :"+bgbanBean.getDvkdId());
								  	 					try{ while(dvkd.next()){ 
								  	 						//System.out.println("DVKD select :"+dvkd.getString("dvkdId"));
								  	 							if(dvkd.getString("dvkdId").trim().equals(bgbanBean.getDvkdId().trim())){ %>
								      								<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkd") %></option>
								      						   <%}else{ %>
								     								<option value='<%=dvkd.getString("dvkdId")%>' ><%=dvkd.getString("dvkd")  %></option>
								     							<%}}}catch(java.sql.SQLException e){} %>	
								     	
													</SELECT></TD>
												</TR>
												<TR>
								  					<TD class="plainlabel">Kênh bán hàng </TD>
								  					<TD class="plainlabel">
								      					<SELECT name="kenhId" disabled="disabled" " style="width:300px">								      
								  	 						<option value =""></option>
								  	 					<% try{ while(kenh.next()){ 
								    							if((kenh.getString("kenhId").trim()).equals(bgbanBean.getKenhId().trim())){ %>
								      								<option value='<%=kenh.getString("kenhId")%>' selected><%=kenh.getString("kenh") %></option>
								      						  <%}else{ %>
								     								<option value='<%=kenh.getString("kenhId")%>'><%=kenh.getString("kenh") %></option>
								     						<%}}}catch(java.sql.SQLException e){} %>	
								     	
                                  						</SELECT></TD>
									  			</TR>
												<TR>							
							    					<TD  class="plainlabel" colspan=2 align=left>  									
							    						<% if (bgbanBean.getTrangthai().equals("1")){ %>
															<input name="trangthai" type="checkbox" value = "1" checked >
														<%}else{ %>
															<input name="trangthai" type="checkbox" value = "0"  >
														<%} %>
														Hoạt động</TD>
												</TR>	

											</TABLE>								
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
				</TABLE>
				</FIELDSET>
				<TABLE class="tabledetail" cellpadding="0" cellspacing="1" width="100%">
					<TR>
						<TD >
												<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR class="tbheader">
									<TH width="15%">Mã sản phẩm </TH>
									<TH width="30%">Tên sản phẩm </TH>								
									<TH width="30%">Tên mới</TH>
									<TH width="9%">Giá bán </TH>
									<TH width="9%">Giá mới </TH>
									<TH width="10%">Đơn vị </TH>
									<TH width="5%">Chọn bán 
									<input type="checkbox" name="chon" onclick="checkedAll();">
									</TH>
								</TR>
								<%
								int j = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								for(int i = 0; i < spIds.length; i++){
									if (j % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
								    <%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
										<TD align="center"><div align="center"><%= masp[i] %> </div></TD>
										<TD align="center"><div align="left"><%= tensp[i] %></div></TD>		
										
										<TD align="center">
										<input type='text' name=<%= "ten" +  spIds[i] %> value="<%= tensp2[i] %>" style="text-align: left"/>
										</TD>
												
										<TD align="center"><input type='text' name=<%= "gia" +  spIds[i] %> value="<%= giamua[i] %>" style="text-align: right" readonly="readonly"/></TD>
										<input type='hidden' name=<%= "giacu" +  spIds[i] %> value="<%= giamua[i] %>" style="text-align: right"/>
										
										<%if(block.equals("1")){ %>
											<TD align="center"><input type='text' name=<%= "giamoi" +  spIds[i] %> value="<%= giamoi[i] %>" style="text-align: right" readonly="readonly" /></TD>
										<%}else{ %>
											<TD align="center"><input type='text' name=<%= "giamoi" +  spIds[i] %> value="<%= giamoi[i] %>" style="text-align: right" /></TD>
										<%} %>
		
										<TD align="center"><%= dv[i] %></TD>

										<TD align="center">
										<% if (tthai[i].equals("1")){ %>
											<input type="checkbox" name=<%= "chbox" +  spIds[i] %>  value='<%= spIds[i] %>' checked>
										<%}else{ %>											
											<input type="checkbox" name=<%= "chbox" +  spIds[i] %>  value='<%= spIds[i] %>' >
										<%} %>
										</TD>
											
						     		<%j++;
									 
							}%>

							</TABLE>

						</TD>
					</TR>
				</TABLE>
			</TD>
			</TR>
		</TABLE>
	</TR>
</TABLE>
</FORM>
</BODY>
</HTML>
<% dvkd.close(); %>
<% kenh.close(); %>

<%bgbanBean.closeDB();  
session.setAttribute("bgbanBean", null) ; 

%>
<%}%>
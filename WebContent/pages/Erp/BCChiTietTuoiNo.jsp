<%@page import="geso.traphaco.distributor.beans.reports.IBCChiTietTuoiNo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="geso.traphaco.center.util.Erp_Item"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.reports.IBCChiTietCongNoHD" %>
<%@ page  import = "geso.traphaco.distributor.beans.reports.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	IBCChiTietTuoiNo bccn = (IBCChiTietTuoiNo)session.getAttribute("bccn");
    ResultSet nvbhRs = bccn.getNvbhRs();
    ResultSet nvgnRs = bccn.getNvgnRs();
    ResultSet khRs = bccn.getKhRs();
    ResultSet dtRs = bccn.getDtRs();
    ResultSet dvkdList = bccn.getDvkdRs();
    ResultSet nhanHangList = bccn.getNhanhangRs();
    ResultSet kbhList = bccn.getKbhRs();
    ResultSet nkhList = bccn.getNkhRs();
    String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String loi=(String)session.getAttribute("loi");
	Utility util = (Utility) session.getAttribute("util");
	List<Erp_Item> loaiDoiTuongList = bccn.getLoaiDoiTuongList();
	List<Erp_Item> doiTuongList = bccn.getDoiTuongList();
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SGF/index.jsp");
	}else{ %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

<script type="text/javascript">
function submitform()
{
	if(document.forms['rpForm'].loaiDoiTuong.value.trim() === "") {
		alert("Vui lòng chọn loại đối tượng");
		return;
	}

	if(document.forms['rpForm'].denNgay.value.trim() === "") {
		alert("Vui lòng chọn đến ngày");
		return;
	}
	
	if(document.forms['rpForm'].tuNgay.value.trim() === "") {
		alert("Vui lòng chọn từ ngày");
		return;
	}
	document.forms['rpForm'].action.value="excel";
	document.forms['rpForm'].submit();
}
function reload()
{
	document.forms['rpForm'].action.value="reload";
	document.forms['rpForm'].submit();
}
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../BCChiTietTuoiNoSvl">
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Báo cáo quản trị &gt; Công nợ &gt; Báo cáo tuổi nợ </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=loi%></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle">Thời gian xuất báo cáo</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								 <TR>
			                        <TD class="plainlabel" valign="middle" >Đơn vị kinh doanh </TD>
			                        <TD class="plainlabel" valign="middle" colspan = "3" >
			                            <select name="dvkdId"  style="width: 200px" onchange="reload();" disabled="disabled">
			                            	<option value="-1">Tất cả</option>
			                            	<%
				                        		if(dvkdList != null)
				                        		{
				                        			while(dvkdList.next())
				                        			{  
				                        			if( dvkdList.getString("pk_seq").equals(bccn.getDvkdId())){ %>
				                        				<option value="<%= dvkdList.getString("pk_seq") %>" selected="selected" ><%= dvkdList.getString("ten") %></option>
				                        			<%}else { %>
				                        				<option value="<%= dvkdList.getString("pk_seq") %>" selected="selected" ><%= dvkdList.getString("ten") %></option>
				                        		 <% } } dvkdList.close();
				                        		}
				                        	%>
			                            </select>
			                        </TD>                        
			                    </TR>
			                    
			                    <%-- <TR>	
									<TD class="plainlabel">Loại báo cáo</TD>
									<TD class="plainlabel">
										<SELECT name = "loaiBC" style="width: 300px" onChange = "reload();">
										
										<% if(bccn.getLoaiBC().equals("0"))  {%>
										
											<option value = "0" selected="selected"> Sổ chi tiết công nợ (nguyên tệ)</option>
											<option value = "1" >Báo cáo chi tiết tuổi nợ (nguyên tệ)</option>
											<option value = "2" >Báo cáo tổng hợp tuổi nợ (nguyên tệ)</option>
										 <%}else if(bccn.getLoaiBC().equals("1")) { %>
										 
										 	<option value = "0" > Sổ chi tiết công nợ (nguyên tệ)</option>
											<option value = "1" selected="selected">Báo cáo chi tiết tuổi nợ (nguyên tệ)</option>
											<option value = "2" >Báo cáo tổng hợp tuổi nợ (nguyên tệ)</option>
										<% } else if(bccn.getLoaiBC().equals("2")) {%>
										
											<option value = "0" > Sổ chi tiết công nợ (nguyên tệ)</option>
											<option value = "1" >Báo cáo chi tiết tuổi nợ (nguyên tệ)</option>
											<option value = "2" selected="selected">Báo cáo tổng hợp tuổi nợ (nguyên tệ)</option>
										<%} %>
											
										</SELECT>
									</TD>
									</TR> --%>
								<TR>
			                    
			                    <TR>	
									<TD class="plainlabel">Loại đối tượng</TD>
									<TD class="plainlabel">
										<SELECT name = "loaiDoiTuong" style="width: 300px" onChange = "reload();">
											<option value = "" ></option>
											<% if (loaiDoiTuongList != null)
											{
												for (Erp_Item item : loaiDoiTuongList)
												{
													if (item.getValue().equals(bccn.getLoaiDoiTuong()))
													{
													%>
														<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
													<% } else { %>
														<option value="<%=item.getValue()%>"><%=item.getName()%></option>
											<% } }} %>
										</SELECT>
									</TD>
									</TR
									>
									
								<% if( 1==0 && bccn.getLoaiDoiTuong().equals("1"))  {%>
								<TR>
			                        <TD class="plainlabel" valign="middle" >Nhãn hàng </TD>
			                        <TD class="plainlabel" valign="middle" colspan = "3" >
			                            <select name="nhanhangId"  style="width: 300px" onchange="reload();">
			                            	<option value="-1">Tất cả</option>
			                            	<%
				                        		if(nhanHangList != null)
				                        		{
				                        			while(nhanHangList.next())
				                        			{  
				                        			if( nhanHangList.getString("pk_seq").equals(bccn.getNhanhangId())){ %>
				                        				<option value="<%= nhanHangList.getString("pk_seq") %>" selected="selected" ><%= nhanHangList.getString("ten") %></option>
				                        			<%}else { %>
				                        				<option value="<%= nhanHangList.getString("pk_seq") %>" ><%= nhanHangList.getString("ten") %></option>
				                        		 <% } } nhanHangList.close();
				                        		}
				                        	%>
			                            </select>
			                        </TD>                        
			                    </TR>
								<% } %>
									
								 <% if( 1==0 && bccn.getLoaiDoiTuong().equals("1")) { %>
			                    
			                    <TR >
			                        <TD class="plainlabel" valign="middle" >Nhóm khách hàng </TD>
			                        <TD class="plainlabel" valign="middle" colspan = "3" >
			                            <select name="nkh"  style="width: 300px" onChange = "reload();">
			                            	<option value="0">Tất cả</option>
			                            	<%
				                        		if(nkhList != null)
				                        		{
				                        			while(nkhList.next())
				                        			{  
				                        			if( nkhList.getString("pk_seq").equals(bccn.getnKh())){ %>
				                        				<option value="<%= nkhList.getString("pk_seq") %>" selected="selected" ><%= nkhList.getString("ten") %></option>
				                        			<%}else { %>
				                        				<option value="<%= nkhList.getString("pk_seq") %>" ><%= nkhList.getString("ten") %></option>
				                        		 <% } } nkhList.close();
				                        		}
				                        	%>
			                            </select>
			                        </TD>                        
			                    </TR>
			                    <%}; %>	
									<% 
			                    	System.out.print("Tét" + bccn.getLoaiBC()); 
			                    if(!bccn.getLoaiBC().equals("2")) {
			                    	
			                    	%>
								<TR>
								
								
								<TD class="plainlabel" 
								
								
								>Đối tượng</TD>
									<TD class="plainlabel">
										<SELECT name = "doiTuong" style="width: 300px" onChange = "reload();">
											<option value = "" ></option>
											<% if (doiTuongList != null)
											{
												for (Erp_Item item : doiTuongList)
												{
													if (item.getValue().equals(bccn.getDoiTuong()))
													{
													%>
														<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
													<% } else { %>
														<option value="<%=item.getValue()%>"><%=item.getName()%></option>
											<% } }} %>
										</SELECT>									</TD>
								</TR>
								<% } %>
								
								
			                    
			                    <TR style="display: none;">
			                        <TD class="plainlabel" valign="middle" >Kênh bán hàng </TD>
			                        <TD class="plainlabel" valign="middle" colspan = "3" >
			                            <select name="kbh"  style="width: 200px">
			                            	<option value=""></option>
			                            	<%
				                        		if(kbhList != null)
				                        		{
				                        			while(kbhList.next())
				                        			{  
				                        			if( kbhList.getString("pk_seq").equals(bccn.getkBh())){ %>
				                        				<option value="<%= kbhList.getString("pk_seq") %>" selected="selected" ><%= kbhList.getString("ten") %></option>
				                        			<%}else { %>
				                        				<option value="<%= kbhList.getString("pk_seq") %>" ><%= kbhList.getString("ten") %></option>
				                        		 <% } } kbhList.close();
				                        		}
				                        	%>
			                            </select>
			                        </TD>                        
			                    </TR>
			                    
			                    
			                    
			                   
			                    <tr>
			                    	<TD width="19%" class="plainlabel" >Số ngày/Kỳ quá hạn</TD>
								  	<TD class="plainlabel" >
								  		<input type="text" name="buocNhay" size="20" value = "<%=bccn.getBuocNhay()%>" >
								  	</TD>
			                    </tr>
			                    <% 
			                    	System.out.print("Tét" + bccn.getLoaiBC()); 
			                    //if(!bccn.getLoaiBC().equals("1")) {
			                    	
			                    	%>
			                    
								<TR>
								  	<TD width="19%" class="plainlabel" >Từ ngày</TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<input type="text" class="days" name="tuNgay" size="20" value = "<%=bccn.getTuNgay() == null ? "" : bccn.getTuNgay()%>" >
												</TD>
												<TD>		   										
												</TD>
                                    		</TR>
										</TABLE>									
									</TD>
								</TR>
								<% //} %>
								<TR>
									<TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input type="text"  class="days" name="denNgay" size="20" value = "<%=bccn.getDenNgay()%>" >												</TD>
												<TD>                                        		</TD>
                                     		</TR>
										</TABLE>									</TD>
								</TR>
							    <TR>
                      <TD class="plainlabel" style="display: none">Tìm nhân viên bán hàng</TD>
                  		<TD class="plainlabel" colspan="3" style="display: none">                        
                  		<select name="nvbhId" id="nvbhId" style="width: 500px" multiple="multiple"  >
                            <option value=""> </option>
                        	<%
                        		if(nvbhRs != null)
                        		{
                        			try
                        			{
                        			while(nvbhRs.next())
                        			{  
                        			if( bccn.getNvbhIds().contains(nvbhRs.getString("pk_seq")) ){ %>
                        				<option value="<%= nvbhRs.getString("pk_seq") %>" selected="selected" ><%= nvbhRs.getString("Ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nvbhRs.getString("pk_seq") %>" ><%= nvbhRs.getString("Ten") %></option>
                        		 <% } } nvbhRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>                     
                 </TR>   
                  <TR style="display: none">    
                       <TD class="plainlabel">Tìm khách hàng</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="khId" style="width: 500px" multiple="multiple" >
                           <option value=""> </option>
                        	<%
                        		if(khRs != null)
                        		{
                        			try
                        			{
                        			while(khRs.next())
                        			{  
                        			if( bccn.getKhIds().contains(khRs.getString("pk_seq")) ){ %>
                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("khTen") %></option>
                        			<%}else { %>
                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("khTen") %></option>
                        		 <% } } khRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                    </TR>
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:submitform()" >
	<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo</a>&nbsp;&nbsp;&nbsp;&nbsp;                                    </TD>
								</TR>
							</TABLE>
							</FIELDSET>						</TD>
						</TR>
					</TABLE>					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%

if(bccn!=null)
{
	bccn.dbClose();
}
	}%>
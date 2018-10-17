<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.khauhaotaisancodinh.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.khauhaotaisancodinh.*" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IKhauhaoCCDCList obj = (IKhauhaoCCDCList)session.getAttribute("obj");
	ResultSet namRs = obj.getNamRs();
	ResultSet thangRs = obj.getThangRs();
	ResultSet CCDCRs = obj.getCCDCRs();
	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("ErpKhauhaocongcudungcuSvl","",userId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>

<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

	
<script>

	$(function() {
	 // setup ul.tabs to work as tabs for each div directly under div.panes
	 	$("ul.tabs").tabs("div.panes > div");

	});
	
</script>



<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
		document.forms["khtt"].action.value = "search";
	    document.forms["khtt"].submit();
	}
	
	function newform()
	{
		document.forms["khtt"].action.value = "new";
	    document.forms["khtt"].submit();
	}

	function clearform()
	{
		document.forms["khtt"].nam.value = "";
		document.forms["khtt"].thang.value = "";
		document.forms["khtt"].CCDC.value = "";
		document.forms["khtt"].submit();
	}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="khtt" method="post" action="../../ErpKhauhaocongcudungcuSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <TR height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công cụ dụng cụ &gt; Phân bổ </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></TR>
						</TABLE>
			 		</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Tiêu chí tìm kiếm </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

		                  <TR>
		                        <TD class="plainlabel" valign="middle" width="100px" >Năm </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                        	<select name="nam" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(namRs != null)
			                        		{
			                        			while(namRs.next())
			                        			{  
			                        			if( namRs.getString("nam").equals(obj.getNam())){ %>
			                        				<option value="<%= namRs.getString("nam") %>" selected="selected" ><%= namRs.getString("namTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= namRs.getString("nam") %>" ><%= namRs.getString("namTen") %></option>
			                        		 <% } } namRs.close();
			                        		}
			                        	%>
		                            </select> 
		                        </TD>                
		                  </TR>

                           <TR>
		                        <TD class="plainlabel" valign="middle" >Tháng </TD>
		                        <TD class="plainlabel" valign="middle" width="220px" >
		                            <select name="thang" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(thangRs != null)
			                        		{
			                        			while(thangRs.next())
			                        			{  
			                        			if( thangRs.getString("thang").equals(obj.getThang())){ %>
			                        				<option value="<%= thangRs.getString("thang") %>" selected="selected" ><%= thangRs.getString("thangTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= thangRs.getString("thang") %>" ><%= thangRs.getString("thangTen") %></option>
			                        		 <% } } thangRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>   
		                  </TR>
						 <TR>
								<TD class="plainlabel">
								<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;

                                </TD>
                                   								
						 		<TD class="plainlabel">&nbsp;</TD>										
						</TR>
								
					</TABLE>
				</FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			
			<TABLE width="100%" border = "0" cellpading = "0" cellspacing = "0">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Phân bổ chi phí trả trước &nbsp;&nbsp;&nbsp;
					<%if(quyen[0]!=0){ %>
						<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/New.png" alt="">Phân bổ </a>
    				<%} %>                            		
					</LEGEND>
		                  
		                  
						<TABLE width="100%" align="center" cellpadding="6" cellspacing="1px">
							<TR class="tbheader">
								<TH width="7%" align="center"  >Năm</TH>
								<TH width="7%" align="center"  >Tháng</TH>
								<TH width="10%" align="center"  >Số chứng từ</TH>
								<TH width="30%" align="center"  >Diễn giải</TH>
								<TH width="10%" align="center"  >Trạng thái</TH>
								<TH width="15%" align="center" >Người tạo</TH>
								<TH width="10%" align="center" >Ngày tạo</TH>
								<TH width="5%" align="center" >Tác vụ</TH>
								
							</TR>
		<%try
		  {
		 		int m = 0;
		 		String lightrow = "tblightrow";
				String darkrow = "tbdarkrow";
		 		if(CCDCRs != null){
					while(CCDCRs.next())
					{
						if (m % 2 != 0) {%>						
							<TR class= <%=lightrow%> >
					  <%} else {%>
							<TR class= <%= darkrow%> >
					  <%} %>
			
								<TD align = "center"><%= CCDCRs.getString("nam") %>
								<TD align = "center"><%= CCDCRs.getString("thang") %>
								<TD align = "left"><%= CCDCRs.getString("sochungtu") %>
								<TD align = "center"><%= CCDCRs.getString("diengiai") %>
								<% String trangthai="";
								   if(CCDCRs.getString("trangthai").equals("1"))
								   {
									   trangthai="Đã khấu hao";
								   }else
								   {
									   trangthai="Đã hủy";
								   }
									   
								%>
								<TD align = "center"><%= trangthai %>
								<TD align = "center"><%=  CCDCRs.getString("nguoitao")%>
								<TD align = "center"><%=  CCDCRs.getString("ngaytao") %>
								<TD align = "center">
								
								
									<A href = "../../ErpKhauhaocongcudungcuSvl?userId=<%=userId%>&display=<%= CCDCRs.getString("pk_seq") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
								<A href="../../ErpKhauhaocongcudungcuSvl?userId=<%=userId%>&delete=<%= CCDCRs.getString("pk_seq") %>">
										<img src="../images/Delete20.png" alt="Xóa khấu hao" title="Xóa khấu hao" width="20" height="20" border=0
											onclick="if(!confirm('Bạn có muốn xóa phiếu khấu hao này?')) return false;">
										</A>
								</TD>		  
		<%			 	 m++; 
					}
				}
				
		 }catch(Exception e){ e.printStackTrace();}%>
													
							
						</TABLE>
									
					</FIELDSET>											
				</TD>
			</TR>
		</TABLE>
	</TD>
	</TR>
	</TABLE>
</form>
<script type="text/javascript">
	dropdowncontent.init('spId', "right-bottom", 300, "click");
</script>
</BODY>
</HTML>
<% 


try{
	if(CCDCRs!=null){
		CCDCRs.close();
	}
	if(thangRs!=null){
		thangRs.close();
	}
	if(namRs!=null){
		namRs.close();
	}
}catch(Exception er){
	er.printStackTrace();
}
finally{
	if (obj != null)
		obj.DbClose();	
	session.removeAttribute("obj");
	session.setAttribute("obj", null) ; 
}
	}%>

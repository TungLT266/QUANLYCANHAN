<%@page import="geso.traphaco.distributor.beans.chitieunpp.IChitieuSKUInList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="geso.traphaco.distributor.servlets.chitieunpp.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	IChitieuSKUInList obj=(IChitieuSKUInList)session.getAttribute("obj");
	ResultSet chitieuSKU = obj.getChitieuSKUInRs();
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ChiTieuSKUInSvl","",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);
	System.out.println(quyen[4]);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 			    		
	    document.ctsku.thang.selectedIndex = 0;
	    document.ctsku.nam.selectedIndex = 0;
	    document.ctsku.trangthai.selectedIndex = 0;
	    submitform();
	}

	function submitform()
	{
		document.forms['ctsku'].action.value= 'search';
		document.forms['ctsku'].submit();
	}
	function thongbao()
	 {
		 var tt = document.forms["ctsku"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ctsku"].msg.value);
	 }
	 
	function newform()
	{
		document.forms['ctsku'].action.value= 'new';
		document.forms['ctsku'].submit();
	}
	
	function xuatExcel()
	 {
	 	document.forms['ctsku'].action.value= 'excel';
	 	document.forms['ctsku'].submit();
	 	document.forms['ctsku'].action.value= '';
	 }
	
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="ctsku" method="post" action="../../ChiTieuSKUInSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<input type="hidden" name="action" value="1" >
<input type="hidden" name="task" value="NPP" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
        <!--begin common dossier info---> <!--End common dossier info--->
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">

                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp; Thiết lập chỉ tiêu > Chỉ tiêu SKU In</TD>  
                              <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                             <TR>
                             <TD width="15%" class="plainlabel" >Tháng </TD>
								<TD class="plainlabel">
									<select name="thang" id="thang" onChange="submitform()" style="width: 100px">
									<option value=0> </option>  
									<%
									int k=1;
									for(k=1; k <= 12; k++ ){										
									  if(obj.getThang().trim().equals(k+"") ) {
									%>
										<option value=<%= k %> selected="selected" > <%= k %></option> 
									<%  }else{  %>
										<option value=<%= k %> > <%= k %></option> 
									<% } }%>
									</select>
								</TD>
                             </TR>
                              <TR>
                             <TD width="20%" class="plainlabel" >Năm </TD>
								<TD class="plainlabel">
									<select name="nam" id="nam" onChange="submitform()" style="width :100px">
									<option value=0> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									for(int n=2008; n<year_+3; n++) {
									  if(obj.getNam().equals( Integer.toString(n)) ){									  
									%>
										<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
										<option value=<%=n %> ><%=n %></option> 
									<% } }
									%>
									</select>
								</TD>
                             </TR >
                              <TR>
								<TD class="plainlabel">Trạng thái</TD>
								<TD class="plainlabel"><select id="trangthai" name="trangthai" onChange="submitform()" >
	
										<%if (obj.getTrangthai().trim().equals("0")){ %>
										<option value=""></option>
										<option value="0" selected>Chưa chốt</option>
										<option value="1">Đã chốt</option>
										<option value="2">Đã hủy</option>
									
										<%}else{ 							
		  								if (obj.getTrangthai().trim().equals("1")){ %>
										<option value=""></option>
										<option value="0">Chưa chốt</option>
										<option value="1" selected>Đã chốt</option>																
										<option value="2">Đã hủy</option>
										<%}else{ 
										if (obj.getTrangthai().trim().equals("2")){ %>
										<option value=""></option>
										<option value="0">Chưa chốt</option>
										<option value="1">Đã chốt</option>																
										<option value="2" selected>Đã hủy</option>
										<%}
			  
										else{%>
										<option value="" selected></option>
										<option value="0">Chưa chốt</option>
										<option value="1">Đã chốt</option>																
										<option value="2">Đã hủy</option>
	
										<%}}} %>
								</select>
								</TD>
							</TR>
                             <tr class="plainlabel"> <td colspan="2" > 
                             <a class="button3" href="javascript:submitform()">
                           	<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a> &nbsp;&nbsp;&nbsp;
                           		<a class="button2" href="javascript:clearform()">
							<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                           	         	<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
                             </td> </tr>
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>

                <TR>
                    <TD width="100%">
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Quản lý chỉ tiêu &nbsp;&nbsp;	   
                             <%if(quyen[0]!=0){ %>                       
							<a class="button3" href="javascript:newform()">
                           	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
	                             <%} %> 
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH align="center" width="">Tháng </TH>
                                    <TH align="center" width="">Năm </TH>
                                    <TH align="center" width="">Diễn giải </TH>
                                    <TH align="center" width="">Trạng thái</TH>
                                    <TH align="center" width="">Ngày tạo</TH>
                                    <TH align="center" width="">Người tạo</TH>
                                    <TH align="center" width="">Ngày sửa</TH>
                                    <TH align="center" width="">Người sửa </TH>
                                    <TH align="center" width="" align="center">&nbsp;Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(chitieuSKU != null)
                                    while ( chitieuSKU.next())
                                    {
                                       
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><%= chitieuSKU.getInt("THANG") %></TD>                                   
                                                <TD><%= chitieuSKU.getInt("NAM")%></TD>
												<TD><%= chitieuSKU.getString("diengiai")%></TD>
                                                <%if( chitieuSKU.getString("trangthai").trim().equals("0") ) {%>
                                                <TD align="center">Chưa chốt</TD>
                                                <%}else if(chitieuSKU.getString("trangthai").trim().equals("1")){ %>
                                                <TD align="center">Đã chốt</TD>
                                                <%} else{%> 
                                                <TD align="center">Đã hủy</TD>
                                                <%} %>
                                                <TD align="center"><%= chitieuSKU.getString("NGAYTAO")%> </TD>
                                                <TD align="center"><%= chitieuSKU.getString("NGUOITAO")%></TD>
                                                <TD align="center"><%= chitieuSKU.getString("NGAYSUA")%> </TD>
                                                <TD align="center"><%= chitieuSKU.getString("NGUOISUA")%> </TD>
                                                <TD align="center"> 
                                                <%
      
                                                if(chitieuSKU.getInt("trangthai")==0)
                                                {%>
                                                	<%if(quyen[2]!=0) {%>
					                                <A href = "../../ChiTieuSKUInUpdateSvl?userId=<%=userId%>&update=<%= chitieuSKU.getString("pk_seq") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cap nhat" border=0></A>&nbsp;
					                               	<%}
                                                		if(quyen[1]!=0) { %>
					                                <A href = "../../ChiTieuSKUInSvl?userId=<%=userId%>&delete=<%= chitieuSKU.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa chỉ tiêu này?')) return false;"></A>									
							                    	<%} %>
							                    	<%if(quyen[4]!=0){%>
							                    	<A href = "../../ChiTieuSKUInSvl?userId=<%=userId%>&chot=<%= chitieuSKU.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Chốt" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn chốt chỉ tiêu này?')) return false;"></A>	
							                    	<%} %>
							                    <%}else{ %>
							              			<%if(quyen[3]!=0){ %>
							                       <A href = "../../ChiTieuSKUInUpdateSvl?userId=<%=userId%>&xem=<%= chitieuSKU.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Xem" width="20" height="20" longdesc="Xoa" border=0 onclick=""></A>									
							                   		<%} %>
							                    <%} %>
							                    </TD>
                                            </TR>
                                        <%m++; }%>  
                                         <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr>                        
                            </TABLE>
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

</html>
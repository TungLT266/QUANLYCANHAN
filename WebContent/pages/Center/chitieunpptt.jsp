 
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="geso.traphaco.center.beans.chitieunpp.IChiTieuNhaPP"%>
<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.center.beans.chitieunpp.imp.ChiTieuNhaPP"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>


<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IChiTieuNhaPP obj=(IChiTieuNhaPP)session.getAttribute("obj");
	ResultSet chitieunhapplist=obj.getListChiTieu();
	Integer thang=obj.getThang();
	Integer nam=obj.getNam();
	 ResultSet kvlist = (ResultSet)obj.getKhuvucRs(); 
	 ResultSet vungRs=obj.getVungRs();
	 ResultSet npp=(ResultSet)obj.getNppRs();
	 obj.setNextSplittings();
	 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>OneOne - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	
	    document.forms['bgstForm'].nam.value=0;
	    document.forms['bgstForm'].thang.value=0;
	    document.forms['bgstForm'].action.value= 'search';
		document.forms['bgstForm'].submit();
	}

	function submitform()
	{
	
		document.forms['bgstForm'].action.value= 'search';
		document.forms['bgstForm'].submit();
	}

	function newform()
	{
		document.forms['bgstForm'].action.value= 'new';
		document.forms['bgstForm'].submit();
	}
	
	function xuatExcel()
	 {
	 	document.forms['bgstForm'].action.value= 'excel';
	 	document.forms['bgstForm'].submit();
	 	document.forms['bgstForm'].action.value= '';
	 }
	
	
	</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
	$("#vungId").select2();
	$("#khuvucId").select2();
});

</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgstForm" method="post" action="../../ChiTieuNppTTSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">

                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp; Quản lý chỉ tiêu > Khai báo > Chia chỉ tiêu Trình dược viên </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;  </TD></tr>
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
                             <TD width="15%" class="plainlabel" >Tháng &nbsp;&nbsp; <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="thang" onchange="javascript:submitform()" style="width :50px">
									<option value=0> </option>  
									<%
									int k=1;
									for(k=1;k<=12;k++){
									  if(k==thang){
									  
									%>
									<option value=<%=k %> selected="selected" > <%=k %></option> 
									<%
									  }else{
									 %>
									<option value=<%=k %> ><%=k %></option> 
									<%
									  }
									  }
									%>
									</select>
								</TD>
                             </TR>
                              <TR>
                             <TD width="20%" class="plainlabel" >Năm &nbsp;&nbsp;  <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="nam"  onchange="javascript:submitform()" style="width :100px">
									<option value=0> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									for(int n=2008;n<year_+5;n++){
									  if(n==nam){									  
									%>
									<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
									<option value=<%=n %> ><%=n %></option> 
									<%
									  }
									  }
									%>
									</select>
								</TD>
                             </TR >
                             
                             
                             <TR>
						<TD class="plainlabel">Vùng </TD>
							<TD class="plainlabel">
								<select name="vungId" onchange="seach();" id="vungId" style="width:350px;">
									<option value="" selected>All</option>
									<%if (vungRs != null)
											while (vungRs.next()) {
												if (vungRs.getString("PK_sEQ").equals(obj.getVungId()   )) {%>
													<option value="<%=vungRs.getString("PK_sEQ")%>" selected><%=vungRs.getString("ten")%></option>
											<%} else {%>
												<option value="<%=vungRs.getString("PK_sEQ")%>"><%=vungRs.getString("ten")%></option>
											<%}}%>
								</select>
							</TD>
						</TR>
                             
                     
                     <TR>
														<TD class="plainlabel">Khu vực</TD>
														<TD width="81%" class="plainlabel"><SELECT
															name="khuvucId"  id="khuvucId" onChange="submitform();" style="width :250px">
																<option value="">All </option>
																<%  try{
								  		while(kvlist.next()){								  			
								  			if (obj.getKhuvucId().equals(kvlist.getString("kvId"))){ %>
																<option value="<%= kvlist.getString("kvId")%>" selected><%= kvlist.getString("kv")%></option>
																<%}else{ %>
																<option value="<%= kvlist.getString("kvId")%>"><%= kvlist.getString("kv")%></option>
																<%		}
								  		}	
								  	}catch (java.sql.SQLException e){} %>
														</SELECT>
														</TD>
													</TR>
                     <TR>
                     <td width="9%" class="plainlabel">Chi nhánh / Đối tác</td>
									<td width="33%" class="plainlabel">
									<SELECT name="nppId" id="nppId" style="width:350px" >
									<option value="">All </option>
									<% if(npp != null){
										  try
										  { 
											  String optionGroup = "";
											  String optionName = "";
											  int i = 0;
											  
											  while(npp.next())
											  { 
												 if(i == 0)
												 {
													 optionGroup = npp.getString("kvTen");
													 optionName = npp.getString("kvTen");
													 
													 %>
													 
													 <optgroup label="<%= optionName %>" >
												 <% }
												 
												 optionGroup = npp.getString("kvTen");
												 if(optionGroup.trim().equals(optionName.trim()))
												 { %>
													 <% if(npp.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 else
												 {
													 %>
													</optgroup>
													<% optionName = optionGroup; %>
													<optgroup label="<%= optionName %>" >
													<% if(npp.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 i++;
								     	 	  } 
											  %>
											  	</optgroup>
											  <%npp.close(); 
								     	 }catch(java.sql.SQLException e){}}%>	  
                                	</SELECT>
								</td>
													</TR>
                     
                             <tr class="plainlabel"> <td colspan="2" > 
                             <a class="button3" href="javascript:submitform()">
                           	<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>   &nbsp;&nbsp;&nbsp;
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
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="5%">Tháng </TH>
                                    <TH width="5%">Năm  </TH>
                                    <TH  width="10%"> Đơn vị KD </TH>
                                    <TH width="10%"> Kênh bán hàng </TH>
                                    <TH width="30%">Chi nhánh / Đối tác </TH>
                                    <TH width="10%">Trạng thái</TH>
                                    <TH width="5%">Ngày tạo</TH>
                                    <TH width="5%">Người tạo</TH>
                                    <TH width="5%">Ngày sửa</TH>
                                    <TH width="5%">Người sửa </TH>
                                    <TH width="" align="center">&nbsp;Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(chitieunhapplist!=null)
                                    while ( chitieunhapplist.next()){
                                       
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%=chitieunhapplist.getInt("THANG")%></div></TD>                                   
                                                <TD><div align="center"><%=chitieunhapplist.getInt("NAM")%></div></TD>
                                                 <TD align="center"><%=chitieunhapplist.getString("donvikinhdoanh")%></TD>
                                                 <TD align="center"><%=chitieunhapplist.getString("kenh")%></TD>
		                                          
                                                   <td align ="left"><%= chitieunhapplist.getString("TENNPP") %> </td>
                                                <%if( chitieunhapplist.getString("trangthai").trim().equals("0") ) {%>
                                                <TD align="center">Chờ xử lý</TD>
                                                <%}else{ %>
                                                <TD align="center">Đã chốt</TD>
                                                <%} %> 
                                                <TD align="center"><%= chitieunhapplist.getString("NGAYTAO")%></TD>
                                                 <TD align="center"><%= chitieunhapplist.getString("nguoitao")%></TD>
                                                  <TD align="center"><%= chitieunhapplist.getString("NGAYsua")%></TD>
                                                   <TD align="center"><%=chitieunhapplist.getString("NGuoisua")%></TD>
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
                                                     <%if( chitieunhapplist.getString("trangthai").trim().equals("0")) {%>
                                                    <TD>
                                                        <A href = "../../ChiTieuNhaPPTTNewSvl?userId=<%=userId%>&update=<%=chitieunhapplist.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" title="Cap nhat" border = 0></A>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                  		
                                                        <TD>
                                                        <A href = "../../ChiTieuNppTTSvl?userId=<%=userId%>&chot=<%=chitieunhapplist.getString("pk_seq")%>"><img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt" border=0 ></A>
                                                        </TD>
                                                    
                                                     <%}else{ %>
	                                                     <TD>
                                                        <A href = "../../ChiTieuNhaPPTTNewSvl?userId=<%=userId%>&display=<%=chitieunhapplist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" title="Hiển thị" border = 0></A>
                                                        </TD>
                                                    <%} %>
                                                    </TR></TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } %>  
                                         
                                         
                                         <tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('bgstForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('bgstForm', eval(bgstForm.nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('bgstForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('bgstForm', eval(bgstForm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('bgstForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>


												</TABLE></TD>
										</TR>
                                         
                                                         
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
<% 	

	
	try{
	 
		if(chitieunhapplist!=null){
			chitieunhapplist.close();
		}
	
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
	
	}
	catch (SQLException e) {}

%>
<%}%>
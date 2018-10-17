<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.chiphinhapkhau.IErpChiphinhapkhauList"%>
<%@ page import = "geso.traphaco.center.util.*" %>

<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>

<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoERP/index.jsp");
	}else{ %>
<%	
	IErpChiphinhapkhauList obj =(IErpChiphinhapkhauList)session.getAttribute("obj");
	int[] quyen = new  int[11];
	quyen = util.GetquyenNew("ErpChiphinhapkhauSvl","",userId);
	List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
	ResultSet csxRs = obj.getChiphinhapkhauRs();
	NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
	NumberFormat formatterVND = new DecimalFormat("#,###,###");
%>
<% ResultSet nguoitaoList = (ResultSet)obj.getNguoitaoRs(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spList.js"></script>

<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<style type="text/css">
.color
{
	background-color: red;
}
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.2em;
	z-index: 100;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
.hoaDonTable {
	width: 100%;

	min-height: 200px;
    float: none;
  	margin-top: 5px;
    padding-bottom: 1px;
}
</style>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>
	
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
		document.forms['cpnk'].poId.value= "";
		document.forms['cpnk'].ncc.value= "";
		document.forms['cpnk'].nccId.value= "";
	    document.forms['cpnk'].ghichu.value= "";
	    document.forms['cpnk'].trangthai.value = "";
	    document.forms["cpnk"].tungay.value = "";
	    document.forms["cpnk"].denngay.value = "";
		document.forms['cpnk'].submit();
	}

	function submitform()
	{
		document.forms['cpnk'].action.value= 'search';
		document.forms['cpnk'].submit();
	}

	function replaces()
	{
		var ncc = document.getElementsByName("ncc");
		var nccId = document.getElementsByName("nccId");
		var nccId_old = nccId[0].value;
		var nccId_new = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			
		if(nccId_old != nccId_new){
			nccId[0].value = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			document.forms["cpnk"].submit();
		}
		setTimeout(replaces, 300);
	}
	
	 function thongbao()
	 {
		 var tt = document.forms["cpnk"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["cpnk"].msg.value);
	 }
	
	function newform()
	{
		document.forms['cpnk'].action.value= 'new';
		document.forms['cpnk'].submit();
	}
	</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="cpnk" method="post" action="../../ErpChiphinhapkhauSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
 <input type="hidden" name="currentPage" value="<%=obj.getCurrentPage()%>">
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý mua hàng > Nghiệp vụ khác > Chi phí nhận hàng</TD>  
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

                            <TABLE  width="100%" cellpadding="6" cellspacing="0" > 
                         
                 <TR>
                        <TD class="plainlabel" width="150px">Từ ngày </TD>
                        <TD class="plainlabel" width="210px">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        <TD class="plainlabel" width="150px" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                            
		                    <TR>
        		                <TD class="plainlabel" width="15%">Số PO </TD>
                		        <TD class="plainlabel">
                        	    <input type="text" id="poId"  name="poId" value="<%= obj.getPoId() %>" maxlength="10" onchange="submitform()" />
                        		</TD>
                        		<TD class="plainlabel" valign="middle" >Người tạo </TD>
                        		<TD class="plainlabel" valign="middle">
                   				<select  name="nguoitao" id="nguoitao" style = "width:200px" onchange="submitform()">
                        			<option value="" selected="selected" ></option>
            	            	<%
	                        		if(nguoitaoList != null)
	                        		{
	                        			
	                        			while(nguoitaoList.next())
	                        			{  
	                        			if( nguoitaoList.getString("pk_seq").equals(obj.getNguoitaoId())){ %>
	                        				<option value="<%= nguoitaoList.getString("pk_seq") %>" selected="selected" ><%= nguoitaoList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nguoitaoList.getString("pk_seq") %>" ><%= nguoitaoList.getString("ten") %></option>
	                        		 <% } } nguoitaoList.close();
                        				
	                        		}
	                        	%>
                      			  </select>                        		
                         		</TD>
                        		<%-- <TD class="plainlabel" valign="middle" >Người tạo </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="nguoitao" id="nguoitao" style = "width:200px" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(nguoitaoList != null)
	                        		{
	                        			while(nguoitaoList.next())
	                        			{  
	                        			if( nguoitaoList.getString("pk_seq").equals(obj.getNguoitaoId())){ %>
	                        				<option value="<%= nguoitaoList.getString("pk_seq") %>" selected="selected" ><%= nguoitaoList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nguoitaoList.getString("pk_seq") %>" ><%= nguoitaoList.getString("ten") %></option>
	                        		 <% } } nguoitaoList.close();
	                        		}
	                        	%>
                            </select>
                        </TD> --%>
								
                    		</TR>
                    		<TR>
                        		<TD class="plainlabel" valign="middle" >Nhà cung cấp</TD>
                        		<TD class="plainlabel" valign="middle" colspan="3">
		                     		<input type="text" id="ncc" name="ncc" value = "<%= obj.getNcc() %>" style="width: 500px" onkeypress="keypress2(event);" >  
		                     		<input type="hidden" id="nccId" name="nccId" value = "<%= obj.getNccId() %>" style="width: 500px" onkeypress="keypress2(event);" >
                        		</TD>                        
                    		</TR>
                             
                             <TR>
                             	<TD width="20%" class="plainlabel" >Ghi chú </TD>
								<TD class="plainlabel" colspan="3">
									<input  type="text" name="ghichu" value="<%=obj.getDiengiai() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Số tờ khai </TD>
								<TD class="plainlabel" colspan="3">
									<input  type="text" name="sotokhai" value="<%=obj.getSotokhai() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Số chứng từ </TD>
								<TD class="plainlabel" colspan="3">
									<input  type="text" name="sochungtu" value="<%=obj.getSochungtu() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             
                             <TR >
                             	<TD width="20%" class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel" colspan="3">
									<select name="trangthai" style = "width:100px" onChange="submitform();">
										<% if(obj.getTrangthai().equals("0")){ %>
											<option value="0" selected>Chưa chốt</option>
											<option value="1">Đã chốt</option>
											<option value=""> </option>
										<%} else { if( obj.getTrangthai().equals("1") ) { %>										
											<option value="0" >Chưa chốt</option>
											<option value="1" selected>Đã chốt</option>										
											<option value=""> </option>
										<% } else { %>
											<option value="0" >Chưa chốt</option>
											<option value="1">Đã chốt</option>										
											<option value="" selected> </option>
										 <% } }  %>
										
									 </select>
								</TD>
								
								
                             </TR >
                             <TR>
                             	<TD width="20%" class="plainlabel" >Từ số tiền </TD>
								<TD class="plainlabel" >
									<input  type="text" name="tusotien" value="<%=obj.getTusotien() %>" size="20" onchange=submitform();>
								</TD>
								<TD width="20%" class="plainlabel" >Đến số tiền </TD>
								<TD class="plainlabel" >
									<input  type="text" name="densotien" value="<%=obj.getDensotien() %>" size="20" onchange=submitform();>
								</TD>
                             </TR >
                             <tr class="plainlabel"> 
                             	<td colspan="5" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             	</td> 
                             </tr>
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>

                <TR>
                    <TD width="100%" >
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Chi phí nhận hàng &nbsp;&nbsp;	
                       	 <% if(quyen[0]!=0) {	%>    
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                          <%} %>  	      
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="7%" align="center">Số chứng từ</TH>
                                    <TH width="7%" align="center">Số tờ khai</TH>
                                    <TH width="7%" align="center">Ngày nhập</TH>                                    
                                    <TH width="20%" align="center">Ghi chú</TH>
                                    <TH width="7%" align="center">Số tiền có thuế</TH>
                                    <TH width="7%" align="center">Trạng thái</TH>
                                    <TH width="7%" align="center">Ngày tạo</TH>
                                    <TH width="11%" align="center">Người tạo</TH>
                                    <TH width="7%" align="center">Ngày sửa</TH>
                                    <TH width="11%" align="center">Người sửa</TH>
                                    <TH width="10%" align="center">Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
	                                String lightrow = "tblightrow";
	                                String darkrow = "tbdarkrow";
	                              
	                              while(csxRs.next())
									{   
	                                	//IThongTinHienThi ht = htList.get(i);
	                                	
                                    	String tt =csxRs.getString("trangthai");
                                    	String sotokhai =  csxRs.getString("sotokhai");
                         				if(sotokhai.trim().length() > 0)
                         				{
                         					sotokhai = sotokhai.substring(0, sotokhai.length() - 2);
                         				}
                         				
                                        if (m % 2 != 0) { %>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><%= csxRs.getString("pk_seq") %></TD>   
                                                
                                                
                                                 <TD align="center"><%= sotokhai %></TD>   
                                                <TD align="center"><%= csxRs.getString("ngaynhap") %></TD>                                 
                                                <TD align="left"><%= csxRs.getString("ghichu")%></TD>
                                                 <TD align="right"><%= formatterVND.format( csxRs.getDouble("tongtienavat") )%></TD>
                                                <% if( tt.equals("0") ) { %>
                                                	<TD align="center">Chưa chốt</TD>
                                                <% } else { if(tt.equals("1")) { %>       
                                                	<TD align="center">Đã chốt</TD>
                                                <%} else { %> 
                                                	<TD align="center">Đã hủy</TD>
                                                <% } } %> 
                                                	
                                                <TD align="center"><%= csxRs.getString("ngaytao")%> </TD>
                                                <TD align="left"><%= csxRs.getString("nguoitao")%></TD>
                                                <TD align="center"><%=  csxRs.getString("ngaysua")%> </TD>
                                                <TD align="left"><%=  csxRs.getString("nguoisua")%> </TD>
                                                <TD align="center"> 
                                                <% if( tt.equals("0") ) { 
                                                	if (quyen[2]!=0) {
                                                %>
							                   		<A href = "../../ErpChiphinhapkhauUpdateSvl?userId=<%=userId%>&update=<%= csxRs.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												<%} if (quyen[4]!=0) {%>	
													 &nbsp;<A href = "../../ErpChiphinhapkhauSvl?userId=<%=userId%>&chot=<%=  csxRs.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border=0 onclick="if(!confirm('Bạn muốn chốt chi phí nhận hàng này?')) return false;"></A>
												<%} if (quyen[5]!=0) {%>	
													 &nbsp;<A href = "../../ErpChiphinhapkhauSvl?userId=<%=userId%>&delete=<%=  csxRs.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa chi phí nhận hàng này?')) return false;"></A>
												<%} %>	
													 
	                							
												<% } else  { 
														if (quyen[4]!=0) {
												%>
													 &nbsp;<A href = "../../ErpChiphinhapkhauUpdateSvl?userId=<%=userId%>&display=<%= csxRs.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
												<%} }%>	
													 
							                    </TD>
                                            </TR>
                                          <% m++; }%>  
                                        
                            </TABLE>
                            
                            </TD>
                            
                        </TR>
                        <tr class="tbfooter">
																	<TD align="center" valign="middle" colspan="13" class="tbfooter">
																		<%
																			obj.setNextSplittings();
																		%> <script type="text/javascript" src="../scripts/phanTrang.js"></script> <input type="hidden" name="crrApprSplitting"
																		value="<%=obj.getCrrApprSplitting()%>"> <input type="hidden" name="nxtApprSplitting" value="<%=obj.getNxtApprSplitting()%>">
																		<%
																			if (obj.getNxtApprSplitting() > 1) {
																		%> <img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')">
																		&nbsp; <%
																		 	} else {
																		 %> <img alt="Trang Dau" src="../images/first.gif"> &nbsp; <%
																		 	}
																		 %> <%
																		 	if (obj.getNxtApprSplitting() > 1) {
																		 %> <img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;"
																																				onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp; <%
																		 	} else {
																		 %> <img alt="Trang Truoc" src="../images/prev_d.gif"> &nbsp; <%
																		 	}
																		 %> <%
																		 	int[] listPage = obj.getNextSplittings();
																		 		for (int i = 0; i < listPage.length; i++) {
																		 %> <%
																		 	System.out.println("Current page:"
																		 					+ obj.getNxtApprSplitting());
																		 			System.out.println("List page:" + listPage[i]);
																		
																		 			if (listPage[i] == obj.getNxtApprSplitting()) {
																		 %> <a style="color: white;"><%=listPage[i]%>/ <%=obj.getTheLastSplitting()%></a> <%
																		 	} else {
																		 %> <a href="javascript:View(document.forms[0].name, <%=listPage[i]%>, 'view')"><%=listPage[i]%></a> <%
																		 	}
																		 %> <input type="hidden" name="list" value="<%=listPage[i]%>" /> &nbsp; <%
																		 	}
																		 %> <%
																		 	if (obj.getNxtApprSplitting() < obj.getTheLastSplitting()) {
																		 %> &nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;"
																																				onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp; <%
																		 	} else {
																		 %> &nbsp; <img alt="Trang Tiep" src="../images/next_d.gif"> &nbsp; <%
																		 	}
																		 %> <%
																		 	if (obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {
																		 %> <img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%
																		 	} else {
																		 %> <img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp; <%
																		 	}
																		 %>
																	</TD>
																</tr>
                    </TABLE>
                    </FIELDSET>
                    </TD>
                </TR>

        </TABLE>
        </TD>
    </TR>
</TABLE>
<script type="text/javascript">
	jQuery(function()
	{		
		$("#ncc").autocomplete("ErpNhaCungCapList.jsp");
		replaces();
	});
</script>

<script type="text/javascript"> 
	  <%for(int k=0; k < m; k++) {%>
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click");
	  <%}%>
</script>


</form>
</BODY>
</html>
<% 	
	try
    {
		if(csxRs != null)
			csxRs.close();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
		
		if(nguoitaoList!=null){
			nguoitaoList.close();
		}
	}
	catch (SQLException e) {
		e.printStackTrace();
	} %>
<%}%>
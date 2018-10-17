<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.traphaco.erp.beans.kichbansanxuatgiay.imp.ErpKichBanSanXuatGiayList" %>
<%@ page import = "geso.traphaco.erp.beans.kichbansanxuatgiay.IErpKichBanSanXuatGiayList" %>
<%@ page import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpKichBanSanXuatGiayList obj =(IErpKichBanSanXuatGiayList)session.getAttribute("kbsxList");
	ResultSet khttRs = obj.getRsKbsx();
	ResultSet nhamayRs = obj.getRsNhaMay();
	obj.setNextSplittings(); 
	int[] quyen = new  int[11];
	quyen = util.GetquyenNew("ErpKichBanSanXuatGiaySvl","&loaiNPP=0&isKH=0",userId);
	
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
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
    $(document).ready(function() { 
     $(".select2").select2();
 });
</script>
	
<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['khtt'].ma.value= "";
	    document.forms['khtt'].sanpham.value= "";
	    document.forms['khtt'].trangthai.value = "";
		document.forms['khtt'].nhamayId.value = "";
		document.forms['khtt'].submit();
	}

	function submitform()
	{
		document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}

	function newform()
	{
		document.forms['khtt'].action.value= 'new';
		document.forms['khtt'].submit();
	}
	
	if(<%=obj.getMsg().trim().length() %> > 0){
		alert("<%=obj.getMsg() %>");
	}
</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../ErpKichBanSanXuatGiaySvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản xuất > Kịch bản sản xuất </TD>  
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
                             	<TD width="15%" class="plainlabel" >Mã kịch bản</TD>
								<TD width="25%" class="plainlabel">
									<input type="text" name="ma" value="<%=obj.getMa() %>" size="20" onchange=submitform();>
								</TD>
                             
                             	<%-- <TD width="15%" class="plainlabel" >Sản phẩm </TD>
								<TD class="plainlabel">
									<input  type="text" name="sanpham" value="<%=obj.getSanpham() %>" size="20" onchange=submitform();>
								</TD> --%>
								
								
								
								<TD width="15%" class="plainlabel" >Sản phẩm </TD>
								<TD class="plainlabel">
									
									
									<select name="sanpham"    class="select2"  id="sanpham" style="width: 300px" onChange="submitform();">
										<option value=""> </option>
									<%  
									ResultSet SPRs=obj.getSpRs();
									while(SPRs.next()) {
										  if(SPRs.getString("PK_SEQ").equals(obj.getSanpham())) { %>
											<option value="<%=SPRs.getString("PK_SEQ") %>" selected><%=SPRs.getString("TEN") %></option>
										<%} else { %>
											<option value="<%=SPRs.getString("PK_SEQ") %>"><%=SPRs.getString("TEN") %></option>
										<%}
									   }%>
										
									    </select>
									    
								</TD>
								
								
								<TD width="15%" class="plainlabel" >Diễn giải </TD>
								<TD class="plainlabel">
									<input type="text" name="diengiai" value="<%=obj.getDiengiai() %>" size="20" onchange=submitform();>
								</TD>
                            
								
								
								
                             </TR >
                             <TR>
                             	<TD class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel">
									<select style="width:200px"  class="select2"  name="trangthai" onChange="submitform();">
									<% if(obj.getTrangthai().equals("0")){ %>
										<option value="0" selected>Ngưng hoạt động</option>
										<option value="1">Hoạt động</option>
										<option value=""> </option>
									<%} else {
										if (obj.getTrangthai().equals("1")){%>										
										<option value="0" >Ngưng hoạt động</option>
										<option value="1" selected>Hoạt động</option>										
										<option value=""> </option>
									<%}else{ %>
										<option value="0" >Ngưng hoạt động</option>
										<option value="1" >Hoạt động</option>								
										<option value="" selected> </option>
									<%	} 
								}%>
										
									    </select>
								</TD>
								<TD class="plainlabel" >Nhà máy </TD>
								<TD class="plainlabel"  colspan="3">
									<select style="width:300px"  class="select2" name="nhamayId" onChange="submitform();">
										<option value=""> </option>
										
										<% while(nhamayRs.next()) {
											  if(nhamayRs.getString("PK_SEQ").equals(obj.getNhaMayID())) { %>
												<option value="<%=nhamayRs.getString("PK_SEQ") %>" selected><%=nhamayRs.getString("TEN") %></option>
											<%} else { %>
												<option value="<%=nhamayRs.getString("PK_SEQ") %>"><%=nhamayRs.getString("TEN") %></option>
											<%}
										   }%>
										
									    </select>
								</TD>
                             </TR >
                              
                             <tr class="plainlabel"> 
                             	<td colspan="6" > 
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
                    <TD width="100%">
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Kịch bản sản xuất &nbsp;&nbsp;	
                            	<%if(quyen[Utility.THEM]!=0){ %>
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                            	     <%} %> 
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="8%" align="center">Mã kịch bản</TH>
                                    <TH align="center">Diễn giải</TH>
                                    <TH width="20%" align="center">Tên sản phẩm</TH>
                                    <TH width="8%" align="center">Trạng thái</TH>
                                    <TH width="8%" align="center">Ngày tạo</TH>
                                    <TH width="10%" align="center">Người tạo</TH>
                                    <TH width="8%" align="center">Ngày sửa</TH>
                                    <TH width="10%" align="center">Người sửa</TH>
                                    <TH width="10%" align="center">Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(khttRs != null)
                                    while ( khttRs.next()){
                                       
                                    	String tt = khttRs.getString("trangthai").trim();
                                    	
                                        if (m % 2 != 0) { %>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><%= khttRs.getString("pk_seq") %></TD>    
                                                 <TD align="center"><%= khttRs.getString("DienGiai") %></TD>                                  
                                                <TD align="left"><%= khttRs.getString("spTen")%></TD>
                                                <% if( tt.equals("0") ) { %>
                                                	<TD align="center" style="color: red;"> Ngưng hoạt động</TD>
                                                <% } else { %>
                                                	<TD align="center">Hoạt động</TD>
                                                <%} %> 
                                                <TD align="center"><%= khttRs.getString("NGAYTAO")%> </TD>
                                                <TD align="left"><%= khttRs.getString("NGUOITAO")%></TD>
                                                <TD align="center"><%= khttRs.getString("NGAYSUA")%> </TD>
                                                <TD align="left"><%= khttRs.getString("NGUOISUA")%> </TD>
                                                <TD align="center"> 
	                                               	<%if(quyen[Utility.SUA]!=0){ %>
							                   		<A href = "../../ErpKichBanSanXuatGiayUpdateSvl?userId=<%=userId%>&update=<%= khttRs.getString("pk_seq") %>"><img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
							                   		<%} %>
							                   		<%if(quyen[Utility.XOA]!=0){ %>
													<A href = "../../ErpKichBanSanXuatGiaySvl?userId=<%=userId%>&delete=<%= khttRs.getString("pk_seq") %>"><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn ngưng hoạt động kịch bản sản xuất này?')) return false;"></A>&nbsp;
													<%} %>
													<%if(quyen[Utility.XEM]!=0){ %>
							                   		<A href = "../../ErpKichBanSanXuatGiayUpdateSvl?userId=<%=userId%>&display=<%= khttRs.getString("pk_seq") %>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
							                   		<%} %>
							                    </TD>
                                            </TR>
                                          <% m++; } %>  
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
	try
    {
		if(khttRs != null)
			khttRs.close();
		if(nhamayRs != null)
			nhamayRs.close();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
		session.setAttribute("kbsxList", null) ; 
		
	}
	catch (SQLException e) {} %>
<%}%>
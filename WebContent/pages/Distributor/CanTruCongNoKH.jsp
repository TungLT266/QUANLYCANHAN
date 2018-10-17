<%@page import="geso.traphaco.distributor.beans.cantrucongno.*"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% ICantrucongnoList obj = (ICantrucongnoList)session.getAttribute("obj"); %>
<% ResultSet btcnList = obj.getBtcnList(); %>

<% int[] quyen = new  int[6];
		quyen = util.Getquyen("CantrucongnoSvl","",userId);
		%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{
		document.nvgnForm.tungay.value = "";  
		document.nvgnForm.trangthai.value= "";
		document.nvgnForm.denngay.value = "";  
		document.nvgnForm.sohoadon.value = "";  
		submitform();    
	}

	function submitform()
	{
		document.nvgnForm.action.value= 'search';
		document.forms['nvgnForm'].submit();
	}

	function newform()
	{
		document.nvgnForm.action.value= 'new';
		document.forms['nvgnForm'].submit();
	}
	</SCRIPT>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2(); });
	     
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nvgnForm" method="post" action="../../CantrucongnoSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="action" value="1" >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
                <TR>
                    <TD align="left" class="tbnavigation">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr height="22">
                                <TD align="left" colspan="2" class="tbnavigation">Quản lý công nợ > Khấu trừ chiết khấu tháng</TD>
                                <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= userTen %> &nbsp;</TD></tr>
                        </table></TD>
                </TR>
            </TABLE>
            
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                <TR>

                    <TD >
                        <FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
                            <TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
                                
                                <TR>
                                    <TD width="100px" class="plainlabel">Từ ngày  </TD>
                                    <TD width="250px" class="plainlabel">
                                        <INPUT name="tungay" type="text" size="40" class ="days" value="<%= obj.getTungay()%>"  onChange = "submitform();">
                                   </TD>
			
								    <TD width="100px" class="plainlabel">Đến ngày  </TD>
                                    <TD class="plainlabel">
                                        <INPUT name="denngay" type="text" size="40" class ="days" value="<%= obj.getDenngay()%>"  onChange = "submitform();">
                                   </TD>
                                </TR>
                                
                                <TR>
                                    <TD class="plainlabel">Trạng thái </TD>
                                    <TD  class="plainlabel" >
                                    	<SELECT name ="trangthai" onChange = "submitform();" style="width: 200px">
                                           
                                        <% if (obj.getTrangthai().equals("1")){%>
                                      		  <option value=""></option>
                                              <option value="1" selected>Đã xác nhận</option>
                                              <option value="0">Chưa xác nhận</option>
                                              <option value="2">Đã xóa</option>
                                              
                                        <%}else if(obj.getTrangthai().equals("0")) {%>
                                              <option value=""></option>
                                              <option value="0" selected>Chưa xác nhận</option>
                                              <option value="1" >Đã xác nhận</option>
                                              <option value="2">Đã xóa</option>
                                              
                                        <%}else if(obj.getTrangthai().equals("2")) {%>                                                                                        
                                              <option value=""></option>
                                              <option value="1" >Đã xác nhận</option>
                                              <option value="0" >Chưa xác nhận</option>
                                              <option value="2" selected>Đã xóa </option>
                                        <%}else {%>
                                        	  <option value="" selected></option>
                                        	  <option value="0" >Chưa xác nhận</option>
                                              <option value="1" >Đã xác nhận</option>
                                              <option value="2" >Đã xóa </option>
                                        <%} %>
                                        
                                        </SELECT>
                                      </TD>
                                      <TD  class="plainlabel">Số hóa đơn  </TD>
                                    <TD class="plainlabel">
                                        <INPUT name="sohoadon" type="text" size="40"  value="<%= obj.getSohoadon()%>"  onChange = "submitform();">
                                   </TD>
                                </TR>
                               <TR>
									<TD class="plainlabel" colspan="5">
									<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                      </TD>
								</TR>
                            </TABLE>
                      </FIELDSET>
                    </TD>   
                </TR>
            </TABLE>
            
            <TABLE width="100%" border="0" cellpadding="0" cellspacing ="0">
                <TR>
                    <TD width="100%">

                    <FIELDSET>
                    <LEGEND class="legendtitle">&nbsp;Khấu trừ chiết khấu tháng &nbsp;&nbsp;&nbsp;
                    	<%if(quyen[Utility.THEM]!=0){ %>
                    	<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a> 
    					<%} %>                           
                    </LEGEND>
                    <TABLE class="" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD>
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">

                                    <TH align="center" width="13%">Số chứng từ </TH>
                                    <TH width="20%">Ngày chứng từ </TH>
                                    <TH align="center" width="12%">Trạng thái</TH>
                                    <TH align="center" width="9%">Ngày tạo</TH>
                                    <TH align="center" width="12%">Người tạo </TH>
                                    <TH align="center" width="9%">Ngày sửa </TH>
                                    <TH align="center" width="12%">Người sửa</TH>
                                    <TH align="center" width="12%">Tác vụ</TH>
                                </TR>
                            
                                <% if(btcnList != null)
                                   {
                                	  try
                                	  {
                                		  int m = 0;
                                		  while(btcnList.next())
                                		  {
                                			if((m % 2 ) == 0) {%>
      		                         		<TR class="tbdarkrow">
      			                            <%}else{ %>
      			                          	<TR class="tblightrow">
      			                            <%} %>
                                   
                                           
                                                <TD align="left"><div align="center"><%= btcnList.getString("id") %></div></TD>                                   
                                                <TD align="center"><%=btcnList.getString("ngaychungtu")  %></TD>
                                                 <% if (btcnList.getString("trangthai") .equals("0")){ %>
                                                    <TD align="center">Chờ xác nhận </TD>
                                                <%}else if (btcnList.getString("trangthai") .equals("1")){%>
                                                    <TD align="center">Đã xác nhận</TD>
                                                <%}else{%>
                                                	 <TD align="center">Đã xóa</TD>
                                                <%} %>
                                                <TD align="center"><%=btcnList.getString("ngaytao")%></TD>
                                                <TD align="center"><%=btcnList.getString("nguoitao")%></TD>
                                                <TD align="center"><%=btcnList.getString("ngaysua")%></TD>
                                                <TD align="center"><%=btcnList.getString("nguoisua")%></TD>
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
                                                    <% 
                                                         String trangthai= btcnList.getString("trangthai");
                                                    if(trangthai.equals("0"))
                                                    { %>
	                                                    <%if(quyen[Utility.SUA]!=0){ %>
	                                                    <TD>
	                                                        <A href = "../../CantrucongnoUpdateSvl?userId=<%=userId%>&update=<%=btcnList.getString("id")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cập nhật" border ="0" ></A>
	                                                    </TD>
	                                                    <%} %>
	                                                    <TD>&nbsp;</TD>
	                                                    <%if(quyen[Utility.CHOT]!=0){ %>
	                                                     <TD>
	                                                        <A href = "../../CantrucongnoSvl?userId=<%=userId%>&chot=<%=btcnList.getString("id")%>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border="0" onclick="if(!confirm('Bạn chắc chắn muốn chốt phiếu này?')){ return false;}"></A>
	                                                    </TD>
	                                                    <%} %>
	                                                    <TD>&nbsp;</TD>
	                                                    <%if(quyen[Utility.XOA]!=0){ %>
	                                                    <TD>
	                                                        <A href = "../../CantrucongnoSvl?userId=<%=userId%>&delete=<%=btcnList.getString("id")%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border="0" onclick="if(!confirm('Bạn chắc chắn muốn xóa phiếu này?')){ return false;}"></A>
	                                                    </TD>
	                                                    <%} %>
	                                                    
	                                                    
	                                                <%  }else{%>
	                                                    <%if(quyen[Utility.XEM]!=0){ %>
	                                                    <TD>
	                                                        <A href = "../../CantrucongnoUpdateSvl?userId=<%=userId%>&display=<%=btcnList.getString("id")%>"><img src="../images/Display20.png" alt="Cap nhat" width="20" height="20" longdesc="Hiển thị" border ="0" ></A>
	                                                    </TD>
	                                                    <%}
	                                                  }%>
	                                                    
                                                    </TR>
                                                 </TABLE>
                                                </TD>
                                              </TR>
                                <%    m++;}
                                    }
          							catch(SQLException ex){}
                                }%>
                                	<tr class="tbfooter">
                                	
							<TD align="center" valign="middle" colspan="13" class="tbfooter">
							<% obj.setNextSplittings(); %>
							<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
					<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">
							
								<%if(obj.getNxtApprSplitting() >1) {%> 
									<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('nvgnForm', 1, 'view')"> &nbsp; <%}else {%>
									<img alt="Trang Dau" src="../images/first.gif">
								&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
								alt="Trang Truoc" src="../images/prev.gif"
								style="cursor: pointer;"
								onclick="Prev('nvgnForm', 'prev')"> &nbsp; <%}else{ %>
								<img alt="Trang Truoc" src="../images/prev_d.gif">
								&nbsp; <%} %> <%
						int[] listPage = obj.getNextSplittings();
						for(int i = 0; i < listPage.length; i++){
					%> <% 							
				
					if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
								style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
								<%}else{ %> <a
								href="javascript:View('nvgnForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
								<%} %> <input type="hidden" name="list"
								value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
								style="cursor: pointer;"
								onclick="Next('nvgnForm', 'next')"> &nbsp; <%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif">
								&nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
								<img alt="Trang Cuoi" src="../images/last.gif">
								&nbsp; <%}else{ %> <img alt="Trang Cuoi"
								src="../images/last.gif" style="cursor: pointer;"
								onclick="View('nvgnForm', -1, 'view')"> &nbsp; <%} %>
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

    </TR>
</TABLE>
</form>
<%
	try
	{

		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		if(btcnList!=null){
			btcnList.close();
		}
		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
	}
%>

</BODY>
</html>
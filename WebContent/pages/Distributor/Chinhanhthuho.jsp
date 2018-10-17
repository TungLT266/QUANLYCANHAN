<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="geso.traphaco.distributor.beans.chinhanhthuho.IChinhanhthuhoList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.distributor.beans.chinhanhthuho.*"%>
<%@page import="geso.traphaco.distributor.beans.chinhanhthuho.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import="geso.traphaco.center.util.Erp_Item"%>
<%@ page import="geso.traphaco.center.util.Erp_ListItem"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
	IChinhanhthuhoList obj = (IChinhanhthuhoList) session.getAttribute("obj");
	NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
	NumberFormat formatterVND = new DecimalFormat("#,###,###"); 
	ResultSet tthdRs = (ResultSet)obj.getCnthRs(); 
	List<Erp_ListItem> viewList = obj.getViewList();
  ResultSet chiNhanhRs = obj.getChiNhanhRs();
	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("ChinhanhthuhoSvl","",userId);
	 System.out.print("");
	 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Chi nhánh thu hộ</title>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
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
<script language="javascript" type="text/javascript">
	
	function xoaform() {
		//document.objForm.congty.value = "";
		document.objForm.loaiNCC.value = "";
		document.objForm.taikhoan.value = "";
		document.objForm.nganhang.value = "";
		document.objForm.chinhanh.value = "";
		document.objForm.tenNCC.value = "";
		document.objForm.submit();
	}
	function Submit() {
		document.forms['objForm'].submit();
	}
	function xuatexcel()
	{
		document.forms['objForm'].action.value= 'excel';
		document.forms['objForm'].submit();
		document.forms['objForm'].action.value= '';
	}

	function newform() {
		document.forms['objForm'].action.value = 'New';
		document.forms['objForm'].submit();
	}
	
	function thongbao()
	{
		if(document.getElementById("msg").value != '' && document.getElementById("msg").value!=null)
		{
			alert(document.getElementById("msg").value);
		}
	}
	
	</script>
  
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
    
</script> 
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="objForm" method="post" action="../../ChinhanhthuhoSvl">
	
		<input type="hidden" name="userId" value='<%=userId%>'/> 
		<input type="hidden" name="userTen" value='<%=userTen%>'/> 
		<input type="hidden" name="action" value='1'/>
		 <input type="hidden" id="msg" value="<%=obj.getMsg() %>"/>
		 
		 <script language="javascript" type="text/javascript">
    	thongbao();
		</script> 
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Chi nhánh thu hộ </td>
										<td align="right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="100%" align="center">
											<fieldset>
												<legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
												<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
													<tr>
                                                          <td class="plainlabel" >Từ ngày</td>
                                                      <td class="plainlabel" >
                                                        <input type="text" class= "days" name="tungay" id="tungay" value="<%= obj.getTungay() %>" style="width: 200px;" onchange="Submit();"  >
                                                      </td>
                                                      <td class="plainlabel" >Đến ngày</td>
                                                      <td class="plainlabel" >
                                                        <input type="text" class= "days" name="denngay" id="denngay" value="<%= obj.getDenngay() %>" style="width: 200px;" onchange="Submit();"  >
                                                      </td>
 														
												</tr>
													<tr>
                    									<td class="plainlabel" >Chi nhánh </td> 
                                                <TD class="plainlabel" colspan="3"  > 
                                                  <select name="chiNhanhId" id="chiNhanhId" style = "width:250px" onchange="Submit()"> 
                                                    <option value=""></option> 
                                                    <% if (chiNhanhRs != null) 
                                                    { 
                                                      while (chiNhanhRs.next())
                                                      { 
                                                        if (chiNhanhRs.getString("PK_SEQ").equals(obj.getChiNhanhId())) 
                                                        { 
                                                        %> 
                                                          <option value="<%=chiNhanhRs.getString("PK_SEQ")%>" selected="selected"><%=chiNhanhRs.getString("TEN")%></option> 
                                                        <% } else { %> 
                                                          <option value="<%=chiNhanhRs.getString("PK_SEQ")%>"><%=chiNhanhRs.getString("TEN")%></option> 
                                                    <% } }} %> 
                                                  </select> 
                                                </td> 
													</tr>
														
													
													<tr>
														<td class="plainlabel" >Tên khách hàng</td>
														<td class="plainlabel" colspan="3" >
															<input type="text" name="tenkhachhang" id="tenkhachhang" value="<%= obj.getTenKH() %>" style="width: 200px;" onchange="Submit();"  >
														</td>
													</tr>
<!-- 													<tr> -->
<!-- 														<td class="plainlabel">Số items -->
<!-- 								                        </td> -->
<!-- 								                        <td class="plainlabel" colspan=3> -->
<%-- 															<input type="text" name="soItems" value="<%= obj.getSoItems() %>" onchange="Submit();"> --%>
<!-- 								                        </td> -->
<!-- 													</tr> -->
													
													<tr>
														<td colspan="4" class="plainlabel">
															<a class="button2" href="javascript:xoaform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<!-- 															<a class="button2" href="javascript:xuatexcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a> -->
														</td>
													</tr>
												</table>
											</fieldset>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="100%">
								<fieldset>
									<legend class="legendtitle">Chi nhánh thu hộ&nbsp;&nbsp;&nbsp;
									
<%-- 										<%if( quyen[0] != 0 ){ %> --%>
<!-- 										<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới -->
<!-- 										</a> -->
<%-- 										<%} %> --%>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" colspan="4">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">

														<th width="5%" align="center">STT</th>
														<th width="8%" align="center">Mã Khách Hàng</th>
														<th width="15%" align="center">Tên Khách hàng</th>
														<th width="7%" align="center">Số tiền</th>
														<th width="15%" align="center">Chi nhánh</th>
														<th width="10%" align="center">Trạng thái</th>
                                                        <th width="8%" align="center">Ngày ghi nhận</th>				
														<th width="8%"  align="center">Người sửa</th>
														<th width="8%"  align="center">Ngày sửa</th>
														
														
														<th width="10%" align="center">&nbsp;Tác vụ</th>
													</tr>
																					               <% 
	                int m = 0;		
					if(tthdRs!=null)
					{
						 while(tthdRs.next())
						{ 
					
							 if((m % 2 ) == 0) { %>
                      			<TR class="tbdarkrow">
	                     	<%}else{ %>
	                          	<TR class="tblightrow">
	                        <%} %> 
	                        
	                        <TD><%= tthdRs.getString("pk_seq")%></TD>

	                        <TD><%= tthdRs.getString("mafast")%></TD>
	                        <TD><%= tthdRs.getString("KHACHHANG_FK")%></TD>
	                        <TD ><%= formatter.format(Double.parseDouble(tthdRs.getString("SOTIEN"))) %></TD>
	                         <TD ><%= tthdRs.getString("NPP_FK") %></TD>
	                         <TD ><%= tthdRs.getString("TRANGTHAI").equals("1")?"Đã xác nhận":"Chưa xác nhận" %></TD>
	                          <%-- <TD ><%= tthdRs.getString("NGUOITAO") %></TD>
	                          <TD ><%= tthdRs.getString("NGAYTAO") %></TD> --%>
                            <TD ><%= tthdRs.getString("NGAYGHINHAN") %></TD>
	                          <TD ><%= tthdRs.getString("NGUOISUA") %></TD>
	                          <TD ><%= tthdRs.getString("NGAYSUA") %></TD>
	                          <td>
	                      	<A href = "../../ChinhanhthuhoUpdateSvl?userId=<%=userId%>&display=<%= tthdRs.getString("pk_seq") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp; 
					</td>
					<% 	m++; }					 
					}	%>
					
<!-- 													<tr class="tbfooter"> -->
<!-- 								<TD align="center" valign="middle" colspan="13" class="tbfooter"> -->
<%-- 									<% obj.setNextSplittings(); %> <script type="text/javascript" --%>
<!-- 										src="../scripts/phanTrang.js"></script> <input type="hidden" -->
<!-- 									name="crrApprSplitting" -->
<%-- 									value="<%= obj.getCrrApprSplitting() %>"> <input --%>
<!-- 									type="hidden" name="nxtApprSplitting" -->
<%-- 									value="<%= obj.getNxtApprSplitting() %>"> <%if(obj.getNxtApprSplitting() >1) {%> --%>
<!-- 									<img alt="Trang Dau" src="../images/first.gif" -->
<!-- 									style="cursor: pointer;" -->
<!-- 									onclick="View(document.forms[0].name, 1, 'view')"> &nbsp; -->
<%-- 									<%}else {%> <img alt="Trang Dau" src="../images/first.gif"> --%>
<%-- 									&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img --%>
<!-- 									alt="Trang Truoc" src="../images/prev.gif" -->
<!-- 									style="cursor: pointer;" -->
<!-- 									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> -->
<%-- 									&nbsp; <%}else{ %> <img alt="Trang Truoc" --%>
<%-- 									src="../images/prev_d.gif"> &nbsp; <%} %> <% --%>
<!--  													int[] listPage = obj.getNextSplittings(); -->
<!--  													for(int i = 0; i < listPage.length; i++){ -->
<%--  												%> <%  --%>
												
<!-- 												System.out.println("Current page:" + obj.getNxtApprSplitting()); -->
<!-- 												System.out.println("List page:" + listPage[i]); -->
												
<!-- 												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a -->
<%-- 									style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a> --%>
<%-- 									<%}else{ %> <a --%>
<%-- 									href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a> --%>
<%-- 									<%} %> <input type="hidden" name="list" --%>
<%-- 									value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %> --%>
<!-- 									&nbsp; <img alt="Trang Tiep" src="../images/next.gif" -->
<!-- 									style="cursor: pointer;" -->
<!-- 									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> -->
<%-- 									&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep" --%>
<%-- 									src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%> --%>
<%-- 									<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%}else{ %> --%>
<!-- 									<img alt="Trang Cuoi" src="../images/last.gif" -->
<!-- 									style="cursor: pointer;" -->
<!-- 									onclick="View(document.forms[0].name, -1, 'view')"> -->
<%-- 									&nbsp; <%} %> --%>
<!-- 								</TD> -->
<!-- 							</tr> -->
												</table>
											</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	
</body>
</html>
<% if (chiNhanhRs != null) chiNhanhRs.close();
  obj.DBclose();
  }%>
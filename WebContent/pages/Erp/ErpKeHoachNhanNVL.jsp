<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.canhbaothieuhang.*"%>
<%@ page import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.text.DecimalFormat" %>
<%@ page import = "java.text.NumberFormat" %>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else {
%>
<%
	IErpKehoachnhanNVL obj = (IErpKehoachnhanNVL) session
				.getAttribute("obj");
		ResultSet thangRs = obj.getThangRs();
		ResultSet namRs = obj.getNamRs();
		ResultSet kehoachRs = obj.getCanhbaoRs();

		NumberFormat formater = new DecimalFormat("#,###,###");
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
	
	
	<SCRIPT language="javascript" type="text/javascript">
	
	function submitform()
	{
		document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}
	
	function clearform()
	{ 
		document.forms['khtt'].tungay.value = "";
		document.forms['khtt'].denngay.value = "";
	    document.forms['khtt'].soPO.value = "";
	    document.forms['khtt'].ncc.value="";
	    document.forms['khtt'].maSanPham.value="";
	    document.forms['khtt'].quycach.value="";
	    document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}

	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khtt" method="post" action="../../ErpKehoachnhanNVLSvl">
<input type="hidden" name="userId" value="<%=userId%>" >
<input type="hidden" name="action" value="1" >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Kế hoạch nhận nguyên vật liệu </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%></TD>
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
                              <LEGEND class="legendtitle">&nbsp;Kế hoạch nhận nguyên vật liệu &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                             <TR style="display: none">
                             	<TD width="20%" class="plainlabel" >Tháng </TD>
								<TD   class="plainlabel">
									<select name="thangId" onchange="submitform();" >
										<option value=""></option>
		                            	<%
		                            		if (thangRs != null) {
		                            				while (thangRs.next()) {
		                            					if (thangRs.getString("thangId").equals(
		                            							obj.getThangId())) {
		                            	%>
			                        				<option value="<%=thangRs.getString("thangId")%>" selected="selected" ><%=thangRs.getString("thangTen")%></option>
			                        			<%
			                        				} else {
			                        			%>
			                        				<option value="<%=thangRs.getString("thangId")%>" ><%=thangRs.getString("thangTen")%></option>
			                        		 <%
			                        		 	}
			                        		 			}
			                        		 			thangRs.close();
			                        		 		}
			                        		 %>
		                            </select>
								</TD>
                             
                             	<TD width="100px" class="plainlabel" >Năm </TD>
								<TD class="plainlabel">
									<select name="namId"  onchange="submitform();"  >
										<option value=""></option>
		                            	<%
		                            		if (namRs != null) {
		                            				while (namRs.next()) {
		                            					if (namRs.getString("namId").equals(obj.getNamId())) {
		                            	%>
			                        				<option value="<%=namRs.getString("namId")%>" selected="selected" ><%=namRs.getString("namTen")%></option>
			                        			<%
			                        				} else {
			                        			%>
			                        				<option value="<%=namRs.getString("namId")%>" ><%=namRs.getString("namTen")%></option>
			                        		 <%
			                        		 	}
			                        		 			}
			                        		 			namRs.close();
			                        		 		}
			                        		 %>
		                            </select>
								</TD>
                             </TR >
                             
                             <TR>
                             	<TD width="20%" class="plainlabel" >Từ ngày </TD>
								<TD   class="plainlabel">
									<input type="text" class="days" id="tungay" name="tungay" value="<%=obj.getTuNgay()%>" onChange="submitform();") >
								</TD>
                             
                             	
                             </TR >
                             <TR>
                             <TD width="20%" class="plainlabel" >Đến ngày </TD>
								<TD class="plainlabel">
									<input type="text" class="days" id = "denngay" name="denngay" value="<%=obj.getDenNgay()%> " onChange="submitform();" >
								</TD>
                             </TR>
								<TR>
									<TD class="plainlabel" width="20%">Số PO</TD>
									<TD class="plainlabel"><input type="text"
										id="soPO" name="soPO" value="<%=obj.getPo() %>" maxlength="10"
										onChange="submitform();" /></TD>
								</TR>
								<TR>
									<TD class="plainlabel" width="20%">Nhà cung cấp</TD>
									<TD class="plainlabel"><input type="text"
										id="ncc" name="ncc" value="<%=obj.getNcc()%>" maxlength="10"
										onChange="submitform();" /></TD>
								</TR>
								<TR>
									<TD class="plainlabel" width="20%">Mã sản phẩm</TD>
									<TD class="plainlabel"><input type="text"
										id="maSanPham" name="maSanPham" value="<%=obj.getspIds() %>" maxlength="10"
										onChange="submitform();" /></TD>
								</TR>
								<TR>
									<TD class="plainlabel" width="20%">Quy cách sản phẩm</TD>
									<TD class="plainlabel"><input type="text"
										id="quycach" name="quycach" value="<%=obj.getQuyCach() %>" maxlength="10"
										onChange="submitform();" /></TD>
								</TR>
								 	
								

								<TR>
														<TD width="120px" class="plainlabel" colspan="4"><a
															class="button2" href="javascript:clearform()"> <img
																style="top: -4px;" src="../images/button.png" alt="">
																Nhập lại </a>&nbsp;&nbsp;&nbsp;&nbsp;</TD>
													</TR>

													<TR>
                             	<TD colspan="4" >
                             		
                             		<%
                             		                             			if (kehoachRs != null) {
                             		                             				int m=0;
                             		                             				String style="";
                             		                             		%> 
			                          <TABLE width="100%" border="0" cellspacing="2" cellpadding="4">
			                              <TR class="tbheader">
			                              	  <TH width="3%" align="center">Số PO</TH>
			                                  <TH width="27%" align="center">Nhà cung cấp</TH>
			                                  <TH width="15%" align="center">Mã sản phẩm</TH>
			                                  <TH width="30%" align="center">Tên sản phẩm</TH>
			                                  <TH width="10%" align="center">Ngày nhận</TH>
			                                  <TH width="10%" align="center">Số lượng nhận</TH>
			                              </TR>
			                              
			                              <%  
			                              		while (kehoachRs.next()) {
			                              		     if(kehoachRs.getString("ktra").equals("0")){
			                                             style="color: red; ";
			                                            }
			                                            if(kehoachRs.getString("ktra").equals("1")){
			                                             style="color: black; ";
			                                            }
			                              			
			                              			if((m % 2 ) == 0) {%>
			            							<TR  style="<%=style%>" class='tbdarkrow'>
			            								<%}else{ %>
			            							
			            							<TR  style="<%=style%>" class='tblightrow'>
			            								<%} %>          		
			                              			                              
			                              
			                              	
			                              		<td><%=kehoachRs.getString("po")%></td>
			                              		<td><%=kehoachRs.getString("nccTen")%></td>
			                              		<td><%=kehoachRs.getString("spMa")%></td>
			                              		<td><%=kehoachRs.getString("spTen")%></td>
			                              		<td><%=kehoachRs.getString("ngaynhan")%></td>
			                              		<td style="text-align: right;"><%=formater.format(kehoachRs
								.getDouble("soluong"))%></td>
			                              	</TR>
			                              
			                              <%
			                              		m++;	                              	
			                              		}
			                              			                              			kehoachRs.close();
			                              			                              %>
			                              
			                              <TR class="tbfooter" >
											  <TD align="center" colspan="15"> &nbsp; </TD>
										  </TR>                                                  
			                          </TABLE>
			                          <%
			                          	}
			                          %>
                             	
                             	</TD>
                             </TR >
                              
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
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
	try {
			if (thangRs != null)
				thangRs.close();
			if (namRs != null)
				namRs.close();
			if (kehoachRs != null)
				kehoachRs.close();

			if (obj != null) {
				obj.DbClose();
				obj = null;
			}
			session.setAttribute("obj", null);
		} catch (Exception e) {
		}
%>
<%
	}
%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.bangchitiettaikhoan.IBangchitiettaikhoan"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IBangchitiettaikhoan obj = (IBangchitiettaikhoan) session.getAttribute("obj");
	ResultSet tk = obj.getTaikhoan();
	ResultSet nhomtk = obj.getTaikhoanNhom();
	String view = (String) session.getAttribute("view");  
	ResultSet ctyRs = obj.getCtyRs();
	String ctyIds = obj.getErpCongtyId();
	ResultSet chiNhanhRs = obj.getChiNhanhRs();
	String chiNhanh = obj.getChiNhanh();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Phanam - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function search()
	{
		document.forms['frm'].action.value= 'search';
		document.forms["frm"].submit();
	}

	function Change()
	{   
	
	   document.forms["frm"].action.value="change";
	   document.forms["frm"].submit();
	}
	function submitform() {
		if(!kiemTra()){
			return false;
		}
		document.forms['frm'].action.value= 'excel';
		document.forms["frm"].submit();
		return true;
	}
	function kiemTra(){
		var tuNgay = document.getElementById("tungay");
		var denNgay = document.getElementById("denngay");
		if (tuNgay.value.trim().length ==0 || denNgay.value.trim().length == 0){
			alert ('Vui lòng nhập ngày bắt đầu và ngày kết thúc lấy báo cáo .');
			return false;
		}else if(tuNgay.value.trim() <= '2016-09-30' || denNgay.value.trim() <='2016-09-30'){
			alert ( 'Ngày DKSD : 30-09-2016 . Vui lòng nhập thời gian lấy báo cáo từ ngày 01-10-2016');
			return false;
		}
		return true;
		
	}

	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}

</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../BangchitiettaikhoanSvl">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="view" value="<%= obj.getView() %>" >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị > Kế toán - Tài chính > Chi tiết tài khoản</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng Bạn <%=userTen%> &nbsp;</div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Chi tiết tài khoản
					</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">

							<TABLE width="70%" cellpadding="6" cellspacing="0">
                
               <%--  <% if(obj.getView().equals("TT")){ %>							                          
                    <TR>
                        <TD class="plainlabel" width="15%">Chọn công ty </TD>
                        <TD class="plainlabel" >
                            <SELECT NAME = "ctyIds" multiple style = "width:250px;size:5" onChange = "Change();">
                            	<OPTION VALUE = "">&nbsp;</OPTION>
					<%	if(ctyRs != null){ 
							while(ctyRs.next()){ 
								if(ctyIds.indexOf(ctyRs.getString("PK_SEQ")) >= 0){
							
							%>
								
								<OPTION VALUE = "<%= ctyRs.getString("PK_SEQ")%>" SELECTED ><%= ctyRs.getString("TEN")%></OPTION>
					<%			}else if(!ctyIds.equals("100000")){ %>
					
								<OPTION VALUE = "<%= ctyRs.getString("PK_SEQ")%>" ><%= ctyRs.getString("TEN")%></OPTION>
					
					<%			}else{ %>
					
								<OPTION VALUE = "<%= ctyRs.getString("PK_SEQ")%>" SELECTED ><%= ctyRs.getString("TEN")%></OPTION>
								
					<%			}

							} 
						}%>
                            </SELECT>
                        </TD>
                    </TR>
				<%} %> --%>
					<% if(obj.getView().equals("TT")){ %>							                          
                    <TR>
                        <TD class="plainlabel" width="15%">Chọn chi nhánh</TD>
                        <TD class="plainlabel" >
                            <SELECT NAME = "chiNhanhId"  style = "width:300px;size:5" onChange = "Change();" id = "chiNhanhId">
                         
							<%
							String selected = "";
							if(chiNhanhRs != null){ 
								while(chiNhanhRs.next()){
										selected = chiNhanhRs.getString("PK_SEQ").equals(obj.getChiNhanh()) ? "selected=\"selected\"" : "";
									%>	
										<OPTION <%=selected %> value="<%=chiNhanhRs.getString("PK_SEQ")%>"><%=chiNhanhRs.getString("TEN")  %></OPTION>
						<%
								}
							}
						%>
						
							</SELECT>
					
                        </TD>
                    </TR>		
						<%
						}
						%>	
								<TR>
			                        <TD class="plainlabel" width="100px">Từ ngày</TD>
			                        <TD class="plainlabel" width="250px" >
			                            <input type="text" class="days" name="tungay" id="tungay" value="<%= obj.getTungay() %>" maxlength="10"  />
			                        </TD>
			                    
			                        <TD class="plainlabel" width="100px">Đến ngày</TD>
			                        <TD class="plainlabel" colspan="4">
			                            <input type="text" class="days" name="denngay" id="denngay" value="<%= obj.getDenngay() %>" maxlength="10"  />
			                        </TD>
                    			</TR>
                    											
								<TR>	
									<TD class="plainlabel">Chọn nhóm tài khoản</TD>
									<TD>
										<SELECT name = "nhomtkId" style="width: 200px" onChange="search();">
										<%	if(nhomtk != null){ 
												while(nhomtk.next()){ 
													if(nhomtk.getString("SOHIEUTAIKHOAN").equals(obj.getTkNhomId())){ %>
														<option value = "<%= nhomtk.getString("SOHIEUTAIKHOAN")%>" selected><%= nhomtk.getString("SOHIEUTAIKHOAN")%></option>
										<%			}else{ %>
														<option value = "<%= nhomtk.getString("SOHIEUTAIKHOAN")%>" ><%= nhomtk.getString("SOHIEUTAIKHOAN")%></option>
										<%			} %>
										<%			}
											}
										
										%>
										</SELECT>
									</TD>
								
									<TD class="plainlabel">Chọn tài khoản</TD>
									<TD>
										<SELECT name = "tkId" style="width: 200px" >
											<option value=""> </option>
										<%	if(tk != null){ 
												while(tk.next()){ 
													if(tk.getString("TKID").equals(obj.getTkId())){ %>
														<option value = "<%= tk.getString("TKID")%>" selected><%= tk.getString("TAIKHOAN")%></option>
										<%			}else{ %>
														<option value = "<%= tk.getString("TKID")%>" ><%= tk.getString("TAIKHOAN")%></option>
										<%			} %>
										<%			}
											}
										
										%>
										</SELECT>
									</TD>
								</TR>
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a>
									</td>
								</TR>
							</TABLE>
						</div>
						</div>
					</div>
			</div>
	</form>
</body>
</html>
<%
	obj.DBClose();
%>
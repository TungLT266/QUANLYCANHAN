<%@page import="geso.traphaco.erp.beans.vayvon.INhantienvayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% 	INhantienvayList obj = (INhantienvayList)session.getAttribute("obj"); %>
<% 	ResultSet ntvlist = (ResultSet)obj.getNTRS();  
	int[] quyen = new  int[5];
	quyen = util.Getquyen("NhantienvaySvl","",userId);
%>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>   
<%	NumberFormat formatter1 = new DecimalFormat("#,###,###.###"); %>   
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">	
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
        }); 		
		
</script>
	
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>

<SCRIPT language="javascript" type="text/javascript">

function clearform()
{  
	document.forms['ntvForm'].sohd.value= '';
	document.forms['ntvForm'].ngay.value= '';
	document.forms['ntvForm'].tkvay.value= '';
	document.forms['ntvForm'].hinhthuc.value= '';
    document.forms['ntvForm'].submit();
}

function submitform()
{
	document.forms['ntvForm'].action.value= 'search';
	document.forms['ntvForm'].submit();
}

function newform()
{
	document.forms['ntvForm'].action.value= 'new';
	document.forms['ntvForm'].submit();
}
function deleteform()
{
	document.forms['ntvForm'].action.value= 'delete';
	document.forms['ntvForm'].submit();
}


</SCRIPT>

</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >

<form name="ntvForm" method="post" action="../../NhantienvaySvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;Quản lý kế toán &gt; Giải ngân
							   </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %> &nbsp;</td> 
						    </tr>
						</table>
					</TD>
				</TR>
				<TR>
					<TD>
						<TABLE border="0" width="100%" >
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
											<TD width="19%" class="plainlabel" >Số hợp đồng </TD>
										  	<TD width="81%" class="plainlabel" ><INPUT name="sohd" type="text" size="40" value="<%=obj.getSoHD() %>" onChange="submitform();" ></TD>
										</TR>
										
										<TR>
											<TD width="19%" class="plainlabel" >Tài khoản vay </TD>
										  	<TD width="81%" class="plainlabel" ><INPUT name="tkvay" type="text" size="40" value="<%=obj.getTkvay() %>" onChange="submitform();" ></TD>
										</TR>

										<TR>
											<TD width="19%" class="plainlabel" >Ngày giải ngân</TD>
										  	<TD width="81%" class="plainlabel" ><INPUT class="days" name="ngay" type="text" size="40" value="<%=obj.getNgay() %>" onChange="submitform();"></TD>
										</TR>
										
										<TR>
											<TD width="19%" class="plainlabel" >Hình thức giải ngân</TD>
										  	<TD width="81%" class="plainlabel" >
										  		<SELECT NAME = "hinhthuc" onChange = "submitform();">
										  			<OPTION VALUE = "">&nbsp;</OPTION>
										  	<% if (obj.getHinhthuc().equals("1")){ %>
										  			<OPTION VALUE = "1" SELECTED>Chuyển khoản</OPTION>
										  			<OPTION VALUE = "2" >Tiền mặt</OPTION>
										  			
										  	<% }else if (obj.getHinhthuc().equals("2")){ %>
										  			<OPTION VALUE = "1" >Chuyển khoản</OPTION>
										  			<OPTION VALUE = "2" SELECTED>Tiền mặt</OPTION>
										  	<%}else{ %>
										  			<OPTION VALUE = "1" >Chuyển khoản</OPTION>
										  			<OPTION VALUE = "2" >Tiền mặt</OPTION>
											<%} %>										  	
										  		</SELECT>	
										  	</TD>
										</TR>

										<TR>
										    <TD class="plainlabel" colspan=4>
										    <a class="button2" href="javascript:clearform()">
													<img style="top: -4px;" src="../images/button.png" alt="" onClick="clearform();">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
	
                                             </TD>
										</TR>
									</TABLE>
									</FIELDSET>
								</TD>	
							</TR>
						</TABLE>
					</TD>
				</TR>	
				
			    <TR>
					<TD align="left" >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Giải ngân &nbsp;&nbsp;&nbsp;&nbsp; 
							<%if(quyen[0]!=0){ %>  
							<a class="button3" onClick="newform();">
    							<img style="top: -4px;" src="../images/New.png"  >Tạo mới  </a>
    								<%} %>                            
							
							
						</LEGEND>
								<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
											<TH width="10%">Ngày/Chứng từ</TH>
											<TH width="10%">Số hợp đồng</TH>
											<TH width="10%">Tài khoản vay</TH>
											<TH width="10%">Số tiền</TH>
											<TH width="10%">Tiền tệ</TH>
											<TH width="10%">Lãi suất ( %/năm )</TH>
											<TH width="10%">Hình thức</TH>
											<TH width="10%">Trạng thái</TH>
											<TH width="15%">Người sửa</TH>
											<TH width="6%">Ngày sửa</TH>
											<TH width="5%">Tác vụ</TH>
										</TR>
								     <%
								        String lightrow = "tblightrow";
	                                    String darkrow = "tbdarkrow";
	                                   int i =0; 
								     while(ntvlist.next()) 
								     {
								    	 if (i % 2 != 0) {%>                     
                                         <TR align="center" align = "left" class=<%=lightrow%> >
                                     <%} else { %>
                                         <TR align="center" align = "left" class=<%= darkrow%> >
                                     <%}%>
                                     		<TD ><%=ntvlist.getString("NGAYNHAN")+"-GN"+ntvlist.getString("NTVID") %></TD>
								     		<TD align = "Left"><%= ntvlist.getString("SOHD") %></TD>		
								     		
								     		<TD align = "Left"><%= ntvlist.getString("TKVAY") %></TD>		
								     												
											<TD align = "Right"><%= formatter1.format(Double.parseDouble(ntvlist.getString("SOTIENVND"))) %></TD>
											<TD align = "Right"><%= ntvlist.getString("MA")  %></TD>

											<TD ><%= ntvlist.getString("LAISUAT")%></TD>

										<%  if(ntvlist.getString("HINHTHUC").equals("1")) {%>
										     <TD >Chuyển khoản</TD>										     
										     <%} else { %>
										     <TD >Tiền mặt</TD>
									     <%}%>
										     
										<%  if(ntvlist.getString("TRANGTHAI").equals("0")) {%>
										     <TD >Chưa chốt</TD>										     
										     <%} else { %>
										     <TD >Đã chốt</TD>
									     <%}%>
										     
											<TD ><%=ntvlist.getString("NGUOISUA") %></TD>
											<TD ><%=ntvlist.getString("NGAYSUA") %></TD>
											<TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
                                            <% if(ntvlist.getString("TRANGTHAI").equals("0")){ %>                                                    
                                                    <TD>
                                                    	<%if(quyen[2]!=0){ %>  
                                                     	<A href = "../../NhantienvayUpdateSvl?userId=<%=userId%>&update=<%=ntvlist.getString("NTVID") %>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
                                                     	<%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                    <%if(quyen[1]!=0){ %>  
                                                        <A href="../../NhantienvayUpdateSvl?userId=<%=userId%>&delete=<%=ntvlist.getString("NTVID") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa lần giải ngân này?')) return false;"></A>
                                                        <%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                    <%if(quyen[1]!=0){ %>  
                                                        <A href="../../NhantienvayUpdateSvl?userId=<%=userId%>&finish=<%=ntvlist.getString("NTVID") %>"><img src="../images/button.png" alt="Chốt" width="20" height="20" longdesc="Chốt" border=0 onclick="if(!confirm('Bạn có muốn chốt giải ngân này?')) return false;"></A>
                                                        <%} %>
                                                    </TD>
                                                    
                                             <%}else{ %>
                                                    <TD>
                                                    	<%if(quyen[2]!=0){ %>  
                                                     	<A href = "../../NhantienvayUpdateSvl?userId=<%=userId%>&display=<%=ntvlist.getString("NTVID") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
                                                     	<%} %>
                                                    </TD>                                             
                                             
                                             
                                             <%} %>
								                 </TR>
								             </TABLE>
                                           </TD>
								                
							          <% i++;
							          } %>
							          
								<TR>
									<TD align="center" colspan="11" class="tbfooter">&nbsp;</TD>
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
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<%
	String msg = obj.getMsg();
	if(msg.length() > 0 ){ 
		obj.setMsg(""); %>
	
		<SCRIPT type="text/javascript">alert('<%= msg %>');</SCRIPT>	
<%	} %>

</BODY>
</HTML>
<%
	if(ntvlist != null){
		ntvlist.close();
	}
	if(obj != null){
		obj.DbClose();
	}
	
	session.setAttribute("obj",null);
}%>
<%@page import="geso.traphaco.erp.beans.vayvon.IThanhtoannovayList"%>
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

<% 	IThanhtoannovayList obj = (IThanhtoannovayList)session.getAttribute("obj"); %>
<% 	ResultSet ttnvlist = (ResultSet)obj.getTtnvRS();  
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ThanhtoannovaySvl",userId);
%>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>    
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
	document.forms['ttnvForm'].sohd.value= '';
	document.forms['ttnvForm'].tkvay.value= '';
    document.forms['ttnvForm'].submit();
}

function submitform()
{
	document.forms['ttnvForm'].action.value= 'search';
	document.forms['ttnvForm'].submit();
}

function newform()
{
	document.forms['ttnvForm'].action.value= 'new';
	document.forms['ttnvForm'].submit();
}
function deleteform()
{
	document.forms['ttnvForm'].action.value= 'delete';
	document.forms['ttnvForm'].submit();
}


</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
<form name="ttnvForm" method="post" action="../../ThanhtoannovaySvl">
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
							   		&nbsp;Quản lý kế toán > Chức năng > Thanh toán nợ vay
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
							<LEGEND class="legendtitle">&nbsp;Thanh toán nợ vay &nbsp;&nbsp;&nbsp;&nbsp; 
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
											<TH width="4%">Số chứng từ</TH>
											<TH width="6%">Số HĐ</TH>
											<TH width="6%">Tài khoản vay</TH>
											<TH width="6%">Ngày</TH>
											<TH width="7%">Số tiền lãi</TH>
											<TH width="7%">Số tiền gốc</TH>
											<TH width="7%">Số tiền phạt</TH>
											<TH width="7%">Phí khác</TH>
											<TH width="7%">Trạng thái</TH>
											<TH width="10%">Diễn giải chứng từ</TH>
											<TH width="4%">Tác vụ</TH>
										</TR>
								     <%
								        String lightrow = "tblightrow";
	                                    String darkrow = "tbdarkrow";
	                                   int i =0; 
								     while(ttnvlist.next()) 
								     {
								    	 if (i % 2 != 0) {%>                     
                                         <TR align="center" align = "left" class=<%=lightrow%> >
                                     <%} else { %>
                                         <TR align="center" align = "left" class=<%= darkrow%> >
                                     <%}%>
                                     		<TD ><%="TV" + ttnvlist.getString("pk_seq") %></TD>
                                     		<TD ><%=ttnvlist.getString("SOHD") %></TD>
                                     		<TD ><%=ttnvlist.getString("TKVAY") %></TD>
								     		<TD align = "Center"><%=ttnvlist.getString("NGAY") %></TD>												
											<TD align = "Right"><%=formatter.format(Double.parseDouble(ttnvlist.getString("TIENLAI"))) %></TD>

											<TD align = "Right"><%=formatter.format(Double.parseDouble(ttnvlist.getString("TIENGOC"))) %></TD>
											<TD align = "Right"><%=formatter.format(Double.parseDouble(ttnvlist.getString("TIENPHAT"))) %></TD>
											<TD align = "Right"><%=formatter.format(Double.parseDouble(ttnvlist.getString("PHIKHAC"))) %></TD>
										<%  if(ttnvlist.getString("TRANGTHAI").equals("0")) {%>
										     <TD >Chưa hoàn tất</TD>										     
										     <%} else { %>
										     <TD >Đã hoàn tất</TD>
										     <%}%>
											
											<TD align = "Left"><%= ttnvlist.getString("GHICHU") %></TD>												
											<TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                <TR>
                                            <% if(ttnvlist.getString("TRANGTHAI").equals("0")){ %>
                                                    <TD>
                                                   	<%if(quyen[2]!=0){ %>  
                                                     	<A href = "../../ThanhtoannovayUpdateSvl?userId=<%=userId%>&update=<%=ttnvlist.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
                                                     	<%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                    <%if(quyen[1]!=0){ %>  
                                                        <A href="../../ThanhtoannovayUpdateSvl?userId=<%=userId%>&delete=<%=ttnvlist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa thanh toán nợ vay này?')) return false;"></A>
                                                        <%} %>
                                                    </TD>

                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                    <%if(quyen[1]!=0){ %>  
                                                        <A href="../../ThanhtoannovayUpdateSvl?userId=<%=userId%>&finish=<%=ttnvlist.getString("pk_seq") %>"><img src="../images/button.png" alt="Hoàn tất" width="20" height="20" longdesc="Hoàn tất" border=0 onclick="if(!confirm('Bạn có chốt thanh toán nợ vay này?')) return false;"></A>
                                                        <%} %>
                                                    </TD>
                                                    
                                             <%}else { %>                                             
                                                    <TD>
                                                    	<%if(quyen[2]!=0){ %>  
                                                     	<A href = "../../ThanhtoannovayUpdateSvl?userId=<%=userId%>&display=<%=ttnvlist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hienthi" border = 0></A>
                                                     	<%} %>
                                                    </TD>                                             

                                             
                                             <% } %>
                                             </TR>
                                             </TABLE>
                                           </TD>
								         </TR>
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
</BODY>
</HTML>
<%
	if(ttnvlist!=null){
		ttnvlist.close();
	}
	if(obj!=null){
		obj.DbClose();
	}
	
	session.setAttribute("obj",null);
}%>
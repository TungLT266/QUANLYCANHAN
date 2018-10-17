<%@page import="geso.traphaco.erp.beans.vayvon.IHopdongvayList"%>
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

<% IHopdongvayList obj = (IHopdongvayList)session.getAttribute("obj"); %>
<% ResultSet hdlist = (ResultSet)obj.getHDrs();  
	int[] quyen = new  int[5];
	quyen = util.Getquyen("HopdongvaySvl","",userId);
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
	document.forms['hdvForm'].sohd.value= '';
	document.forms['hdvForm'].ngaybd.value= '';
	document.forms['hdvForm'].thoihan.value= '';
    document.forms['hdvForm'].submit();
}

function submitform()
{
	document.forms['hdvForm'].action.value= 'search';
	document.forms['hdvForm'].submit();
}

function newform()
{
	document.forms['hdvForm'].action.value= 'new';
	document.forms['hdvForm'].submit();
}
function deleteform()
{
	document.forms['hdvForm'].action.value= 'delete';
	document.forms['hdvForm'].submit();
}


</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
<form name="hdvForm" method="post" action="../../HopdongvaySvl">
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
							   		&nbsp;Quản lý kế toán > Chức năng > Hợp đồng vay
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
											<TD width="19%" class="plainlabel" >Từ ngày</TD>
										  <TD width="81%" class="plainlabel" ><INPUT class="days" name="ngaybd" type="text" size="40" value="<%=obj.getNgayBD() %>" onChange="submitform();" ></TD>
										</TR>
										
										<TR>
											<TD width="19%" class="plainlabel" >Thời hạn</TD>
										  <TD width="81%" class="plainlabel" ><INPUT  name="thoihan" type="text" size="40" value="<%=obj.getThoihan() %>" onChange="submitform();"></TD>
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
							<LEGEND class="legendtitle">&nbsp;Hợp đồng vay vốn &nbsp;&nbsp;&nbsp;&nbsp; 
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
											<TH width="6%">Số HĐ</TH>
											<TH width="6%">Thay cho HĐ</TH>
											<TH width="15%">Diễn giải</TH>
											<TH width="14%">Ngân hàng</TH>
											<TH width="7%">Số tiền</TH>
											<TH width="3%">ĐV</TH>
											<TH width="7%">Ngoại tệ</TH>
											<TH width="3%">ĐV</TH>											
											<TH width="7%">Loại</TH>
											<TH width="10%">Trạng thái</TH>
											<TH width="5%">Tác vụ</TH>
										</TR>
								     <%
								        String lightrow = "tblightrow";
	                                    String darkrow = "tbdarkrow";
	                                   int i =0; 
								     while(hdlist.next()) 
								     {
								    	 if (i % 2 != 0) {%>                     
                                         <TR align="center" align = "left" class=<%=lightrow%> >
                                     <%} else { %>
                                         <TR align="center" align = "left" class=<%= darkrow%> >
                                     <%}%>
                                     		<TD ><%=hdlist.getString("SOHD") %></TD>
                                     		<TD align = "Center"><%=hdlist.getString("THAYCHO") %></TD>
								     		<TD align = "Left"><%=hdlist.getString("DIENGIAI") %></TD>												
											<TD align = "Left"><%=hdlist.getString("NGANHANG") %></TD>

											<TD ><%=formatter.format(Double.parseDouble(hdlist.getString("TONGTIEN"))) %></TD>
											<TD ><%=hdlist.getString("TIENTE") %></TD>
											
											<TD ><%=formatter.format(Double.parseDouble(hdlist.getString("TONGNGOAITE"))) %></TD>
											<TD ><%=hdlist.getString("NGOAITE") %></TD>
											
										<%  if(hdlist.getString("LOAI").equals("1")) {%>
										     <TD >Ngắn hạn</TD>										     
										     <%} else { %>
										     <TD >Dài hạn</TD>
										     <%}%>
											
												
										<% 	if(hdlist.getString("TRANGTHAI").equals("0")){ %>
											 <TD >Chưa giải ngân</TD>
										<%	}else if(hdlist.getString("TRANGTHAI").equals("1")){ %>
											 <TD >Đã giải ngân, chưa hoàn tất</TD>
										<%	}else{ %>
											 <TD >Đã hoàn tất</TD>
											<%} %>
											
											<TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                <TR>
                                            <% if(hdlist.getString("TRANGTHAI").equals("0")){ %>
                                                    <TD>
                                                   	<%if(quyen[2]!=0){ %>  
                                                     	<A href = "../../HopdongvayUpdateSvl?userId=<%=userId%>&update=<%=hdlist.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
                                                     	<%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                    <%if(quyen[1]!=0){ %>  
                                                        <A href="../../HopdongvayUpdateSvl?userId=<%=userId%>&delete=<%=hdlist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa hợp đồng vay này?')) return false;"></A>
                                                        <%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    
                                             <%}else if(hdlist.getString("TRANGTHAI").equals("1")){ %>                                             
                                                    <TD>
                                                    	<%if(quyen[2]!=0){ %>  
                                                     	<A href = "../../HopdongvayUpdateSvl?userId=<%=userId%>&display=<%=hdlist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hienthi" border = 0></A>
                                                     	<%} %>
                                                    </TD>                                             

                                                    <TD>
                                                    <%if(quyen[1]!=0){ %>  
                                                        <A href="../../HopdongvayUpdateSvl?userId=<%=userId%>&finish=<%=hdlist.getString("pk_seq") %>"><img src="../images/button.png" alt="Hoàn tất" width="20" height="20" longdesc="Hoàn tất" border=0 ></A>
                                                        <%} %>
                                                    </TD>
                                             
                                             <% }else{ %>
                                                    <TD>
                                                    	<%if(quyen[2]!=0){ %>  
                                                     	<A href = "../../HopdongvayUpdateSvl?userId=<%=userId%>&display=<%=hdlist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hienthi" border = 0></A>
                                                     	<%} %>
                                                    </TD>                                             
                                             
                                             <%} %>
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
	if(hdlist!=null){
		hdlist.close();
	}
	if(obj!=null){
		obj.DbClose();
	}
	
	session.setAttribute("obj",null);
}%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.mucchiphi.IMucchiphi" %>
<%@ page  import = "geso.traphaco.erp.beans.mucchiphi.IMucchiphiList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IMucchiphiList obj = (IMucchiphiList)session.getAttribute("obj"); %>
<%
	ResultSet mcplist = (ResultSet)obj.getMcpList();
	ResultSet ctylist = (ResultSet)obj.getCtyList();
	ResultSet dvthlist = (ResultSet)obj.getDvthList() ;
	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("Erp_mucchiphiSvl","",userId);
	 
	 NumberFormat format = new DecimalFormat("#,###,###");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">


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


<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.mcpForm.action.value = "new";
   	 document.forms["mcpForm"].submit();
}

function searchform()
{
	 document.mcpForm.action.value = "search";
   	 document.forms["mcpForm"].submit();
}

function clearform()
{
	document.mcpForm.sotientu.value = "";
	document.mcpForm.sotienden.value = "";
	//document.mcpForm.ctyId.selectedIndex = 0;
	document.mcpForm.dvthId.selectedIndex = 0;
    document.mcpForm.trangthai.selectedIndex = 0;
    document.mcpForm.action.value = "search";
    document.forms["mcpForm"].submit();
}

function formatTien(e)
{
	var giatrinhap = e.value;
	e.value = DinhDangTien(giatrinhap);
	
}

function DinhDangTien(num) 
{
   num = num.toString().replace(/\$|\,/g,'');
   if(isNaN(num))
   num = "0";
   sign = (num == (num = Math.abs(num)));
   num = Math.floor(num*100+0.50000000001);
   num = Math.floor(num/100).toString();
   for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
   num = num.substring(0,num.length-(4*i+3))+','+
   num.substring(num.length-(4*i+3));
   return (((sign)?'':'-') + num);
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="mcpForm" method="post" action="../../Erp_mucchiphiSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">
<input type="hidden" name="chixem" value='<%= obj.getChixem() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng  &gt; Mức chi phí</TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD width="19%" class="plainlabel">Số tiền từ </TD>
								    <TD width="81%" class="plainlabel">
										<INPUT name="sotientu" type="text" size="40" value='<%=obj.getSotientu() %>' onkeyup="formatTien(this)"; onChange ="searchform();">
									</TD>
																		
								</TR>
								<TR>
									<TD width="19%" class="plainlabel">Số tiền đến </TD>
								    <TD width="81%" class="plainlabel">
										<INPUT name="sotienden" type="text" size="40" value='<%=obj.getSotienden() %>' onkeyup="formatTien(this)"; onChange ="searchform();">
									</TD>
																		
								</TR>


						      	<TR>
									<TD width="19%" class="plainlabel">Đơn vị thực hiện </TD>
  								  	<TD class="plainlabel" >
												<SELECT  name="dvthId" onChange="searchform();">												
													<option value=""  ></option>
												
												<% 	if(dvthlist != null){
														try{
															while (dvthlist.next()){%>
																
																<%	if(dvthlist.getString("DVTHID").equals(obj.getDvthId())){ %>											
																		<option value= <%=dvthlist.getString("DVTHID")%> selected><%= dvthlist.getString("DVTH") %></option>															
																<%}else{%>
																		<option value= <%=dvthlist.getString("DVTHID")%> ><%= dvthlist.getString("DVTH") %></option>																																		
																<%	}
															}
															
														}catch(java.sql.SQLException e){}
													}														
														%>														                                           
                                              	</SELECT>
                                       </TD>
								</TR>

						      	<TR>
									<TD width="19%" class="plainlabel">Trạng thái </TD>
  								  	<TD class="plainlabel" >
												<SELECT  name="trangthai" onChange="searchform();">												
													<option value=""  ></option>
												<%if(obj.getTrangthai().equals("1")){ %>
													<option value="1" selected >Hoạt động</option>
													<option value="0"  >Ngưng hoạt động</option>
												<%}else if(obj.getTrangthai().equals("0")){ %>
													<option value="1"  >Hoạt động</option>
													<option value="0" selected >Ngưng hoạt động</option>												
												<%}else{ %>
													<option value="1"  >Hoạt động</option>
													<option value="0"  >Ngưng hoạt động</option>																								
												<%} %>
                                              	</SELECT>
                                       </TD>
								</TR>
								<TR>
									<TD class="plainlabel">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;

                                    </TD>
                                    								
									<TD class="plainlabel">&nbsp;</TD>										
								</TR>

							</TABLE>
						</FIELDSET>
					</TD>
				</TR>							  	
			</TABLE>
			
			<TABLE width="100%" border = "0" cellpading = "0" cellspacing = "0">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Mức chi phí&nbsp;&nbsp;&nbsp;
					
						<%if(quyen[0]!=0 && obj.getChixem().equals("0") ){ %>
						<a class="button3" href="javascript:submitform()">
				    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a> 
				    	<%} %>                           		
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									
									<TH width="10%" > Số tiền từ</TH>
									<TH width="10%">Số tiền đến </TH>
									<TH width="10%">Trạng thái </TH>
									<TH width="30%">Công ty </TH>
									<TH width="30%">Đơn vị</TH>
									<TH width="10%">Tác vụ</TH>
								</TR>
						<% 
							int m = 0;
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							try{
								if(mcplist != null){
								while (mcplist.next()){
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
								<%} else {%>
									<TR class= <%= darkrow%> >
								<%} %>										
								
									<TD align="center" ><%= format.format(mcplist.getDouble("SOTIENTU")) %></TD>
									<TD align="center" >
									<% if(mcplist.getString("SOTIENDEN") == null){ %>
										Không giới hạn
									<%}else{%>
										<%= format.format(mcplist.getDouble("SOTIENDEN")) %>
									<%} %>
									</TD>
									<TD align="center" >
										<% if(mcplist.getString("TRANGTHAI").equals("1")){%>
										Hoạt động
										<%}else{ %>
										Ngưng hoạt động
										<%}%>
									</TD>
									<TD align="center"><%= mcplist.getString("CTY") %></TD>
									<TD align="center"><%= mcplist.getString("DVTH") %></TD>
									<TD align="center">
									<TABLE>
										<TR>
										<TD>
										<%if(quyen[2]!=0 && obj.getChixem().equals("0") ){ %>
											<A href = "../../Erp_MucchiphiUpdateSvl?userId=<%=userId%>&update=<%=mcplist.getString("mcpID")%>;<%=mcplist.getString("CTYID") %>"><img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
											<%} %>
										</TD>
										<TD>
										<%if(quyen[1]!=0 && obj.getChixem().equals("0") ){ %>
											<A href = "../../Erp_mucchiphiSvl?userId=<%=userId%>&delete=<%=mcplist.getString("mcpID")%>;<%=mcplist.getString("CTYID") %>"><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa nhóm chi phí cho <%=mcplist.getString("DVTH")%> này không?')) return false;"></A>&nbsp; 
										<%} %>
										</TD>
										<!-- QUYEN VIEW DLN --> 
										<TD>
											<A href = "../../Erp_MucchiphiUpdateSvl?userId=<%=userId%>&display=<%=mcplist.getString("mcpID")%>;<%=mcplist.getString("CTYID") %>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
										</TD>
										<!-- END QUYEN VIEW DLN --> 	
										</TR>												
									</TABLE>									
								</TD>
						</TR>
							<%m++; }}
						}catch(java.sql.SQLException e){}%>
							
						<TR>
							<TD align="center" colspan="12" class="tbfooter">&nbsp;</TD>
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
</HTML>
<% 
 	if( mcplist != null) mcplist.close(); 
 	if( ctylist != null)  ctylist.close();
 	if( dvthlist != null) dvthlist.close();

 	session.setAttribute("obj",null);
	obj.DBClose(); 
	} %>
	
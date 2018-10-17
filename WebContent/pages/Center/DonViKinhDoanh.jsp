<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.donvikinhdoanh.IDonvikinhdoanh" %>
<%@ page  import = "geso.traphaco.center.beans.donvikinhdoanh.IDonvikinhdoanhList" %>
<%@ page  import = "geso.traphaco.center.beans.nhacungcap.INhacungcap" %>
<%@ page  import = "geso.traphaco.center.beans.nhacungcap.INhacungcapList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%int[] quyen = new  int[6];
quyen = util.Getquyen("DonvikinhdoanhSvl","",userId);

System.out.println(quyen[0]);
System.out.println(quyen[1]);
System.out.println(quyen[2]);
System.out.println(quyen[3]); %>
<% IDonvikinhdoanhList obj = (IDonvikinhdoanhList)session.getAttribute("obj"); %>
<% ResultSet dvkdlist = obj.getDvkdList(); %>
<% ResultSet ncclist = obj.getNccList(false) ; %>

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="javascript" type="text/javascript">
</SCRIPT>


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
<!--HPB_SCRIPT_CODE_40
//-->
function clearform()
{
	document.dvkdForm.dvkd.value = "";
	document.dvkdForm.nccId.selectedIndex = 0;
	document.dvkdForm.tungay.value = "";
	document.dvkdForm.denngay.value = "";
    document.dvkdForm.trangthai.selectedIndex = 2;
    submitform();
}

function submitform()
{
	document.forms['dvkdForm'].action.value= 'search';
	document.forms['dvkdForm'].submit();
}

function newform()
{
	document.forms['dvkdForm'].action.value= 'new';
	document.forms['dvkdForm'].submit();
}
function thongbao()
{var tt = document.forms['dvkdForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['dvkdForm'].msg.value);
	}


</SCRIPT>
</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dvkdForm" method="post" action="../../DonvikinhdoanhSvl" charset="UTF-8">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							  	<TD align="left" colspan="2" class="tbnavigation">&nbsp; Dữ liệu nền > Cơ bản > Đơn vị kinh doanh</td>
   								<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp; </td> 
   								</tr>
   
						</table>
					</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
			  <tr>
				   <TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>				   
							<LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding = "6" >
								<TR>
									<TD class="plainlabel" >Đơn vị kinh doanh </TD>
								    <TD class="plainlabel" ><input name="dvkd" type="text" size="40" value="<%=obj.getDvkd() %>" onChange="submitform();"></TD>
									<TD class="plainlabel" >Từ ngày </TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" onchange=submitform();>
											</TD>
                                   		</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel" >Nhà cung cấp </TD>
									<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0" border="0" >
										<TR>
										  	<TD><SELECT  name="nccId" onChange="submitform();">
										<% 
			
											String nccId = (String) obj.getNccId(); %>
											
											<option value="0" ></option>
										<% 	
											while (ncclist.next()){
												
												if (ncclist.getString("pk_seq").equals(nccId)){%>
												
													<option value= '<%=ncclist.getString("pk_seq")%>' selected><%= ncclist.getString("ten") %></option>
												<%}else{ %>
													<option value= '<%=ncclist.getString("pk_seq")%>' ><%= ncclist.getString("ten") %></option>										
												<%
												  }
																	
											}%>
												                                           
                                              </SELECT>
                                         	</TD>
										</TR>
										</TABLE>									
									</TD>
									<TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  			<TR>
							  			<TD>
											<input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="20" onchange=submitform();>
										</TD>
										
                                     	</TR>
										</TABLE>
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange="submitform();">
									<% if(obj.getTrangthai().equals("0")){ %>
										<option value="0" selected>Ngưng hoạt động</option>
										<option value="1">Hoạt động</option>
										<option value="2"> </option>
									<%}else{
										if (obj.getTrangthai().equals("1")){%>										
										<option value="0" >Ngưng hoạt động</option>
										<option value="1" selected>Hoạt động</option>										
										<option value="2"> </option>
										<%}else{ %>
										<option value="0" >Ngưng hoạt động</option>
										<option value="1" >Hoạt động</option>								
										<option value="2" selected> </option>
									<%	} 
										}%>
										
									    </select></TD>
									<TD class="plainlabel" >
										<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									 </TD>
									 <TD class="plainlabel"></TD>
								</TR>
								
							</TABLE>
					 </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			
		   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  <TR>
			  	   <TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>				   
							<LEGEND class="legendtitle">Đơn vị kinh doanh &nbsp;
							<%if(quyen[Utility.THEM]!=0) {%>
							<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
							<%} %>
							
							</LEGEND>
							<TABLE width="100%" cellspacing="0" cellpadding="0">
								<TR>
								<TD width="98%">
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
										<TR class="tbheader">
											<TH width="4%">STT</TH>
											<TH width="15%">Đơn vị kinh doanh </TH>
											<TH width="22%">Nhà cung cấp</TH>
											<TH width="8%">Trạng thái </TH>
											<TH width="8%">Ngày tạo</TH>
											<TH width="13%">Người tạo </TH>
											<TH width="8%">Ngày sửa </TH>
											<TH width="13%">Người sửa</TH>
											<TH width="13%">Tác vụ</TH>
										</TR>
									
								<% 
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (dvkdlist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
											<TD align="center"><%=m+1%></TD>
											<TD align="left"><%=dvkdlist.getString("donvikinhdoanh") %></TD>                                   
											<TD><%=dvkdlist.getString("nhacungcap") %></TD>
											<% if (dvkdlist.getString("trangthai").equals("1")){ %>
												<TD align="left">Hoạt động</TD>
											<%}else{%>
												<TD align="left">Ngưng hoạt động</TD>
											<%}%>
											
											<TD align="center"><%=dvkdlist.getString("ngaytao")%></TD>
											<TD align="center"><%=dvkdlist.getString("nguoitao") %></TD>
											<TD align="center"><%=dvkdlist.getString("ngaysua") %></TD>
											<TD align="center"><%=dvkdlist.getString("nguoisua") %></TD>
									
											<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[Utility.SUA]!=0) {%>
														<A href = "../../DonvikinhdoanhSvl?userId=<%=userId%>&update=<%=dvkdlist.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Chinh sua" title="Chỉnh sửa" width="20" height="20" longdesc="Chinh sua" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;&nbsp;</TD>
													<TD>
													<%if(quyen[Utility.XOA]!=0) {%>
														<A href = "../../DonvikinhdoanhSvl?userId=<%=userId%>&delete=<%=dvkdlist.getString("pk_seq")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa đơn vị kinh doanh này?')) return false;"></A></TD>
													<%} %>
													</TR>
												</TABLE>											
											</TD>
										</TR>
									<% 	m++;
									}%>
										<TR>
											<TD align="center" colspan="11" class="tbfooter">&nbsp;	</TD>
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
</FORM>
</BODY>
</HTML>
<%
	/* if (ncclist != null) ncclist.close();
	if (dvkdlist != null) dvkdlist.close();
	obj.DBClose();
	obj = null;
	session.setAttribute("obj", null); */
}
%>
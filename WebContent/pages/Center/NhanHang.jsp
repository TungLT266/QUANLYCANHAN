<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhanhang.INhanhangList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% INhanhangList obj = (INhanhangList)session.getAttribute("obj"); %>
<% ResultSet nhlist = (ResultSet)obj.getNhlist() ; %>
<% ResultSet dvkdlist = (ResultSet)obj.getDvkdList(); %>
<%int[] quyen = new  int[4];
	quyen = util.Getquyen("NhanhangSvl","",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
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

<SCRIPT language="javascript" type="text/javascript">


function clearform()
{
    document.nhanForm.nhanhang.value = "";
    document.nhanForm.dvkdId.selectedIndex = 0;
    document.nhanForm.tungay.value = "";
	document.nhanForm.denngay.value = "";       
    document.nhanForm.trangthai.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.forms['nhanForm'].action.value= 'search';
	document.forms['nhanForm'].submit();
}

function newform()
{
	document.forms['nhanForm'].action.value= 'new';
	document.forms['nhanForm'].submit();
}
function thongbao()
{var tt = document.forms['nhanForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['nhanForm'].msg.value);
	}


function xuatExcel()
{
	document.forms['nhanForm'].action.value= 'excel';
	document.forms['nhanForm'].submit();
	document.forms['nhanForm'].action.value= '';
}
</script>


</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nhanForm" method="post" action="../../NhanhangSvl" charset="utf-8">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<!--begin common dossier info---> <!--End common dossier info--->
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền sản phẩm &gt; Nhãn hàng> </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

							<TABLE  width="100%" cellpadding="4" cellspacing="0">
								<TR>
									<TD width="19%" class="plainlabel" >Nhãn hàng</TD>
								    <TD width="81%" class="plainlabel" ><INPUT name="nhanhang" size="40" type="text" value='<%= obj.getNhanhang() %>' onChange="submitform();"></TD>
								</TR>
						      	<TR>
									<TD width="19%" class="plainlabel">Đơn vị kinh doanh </TD>
  								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0" border="0">
											<TR>
										  		<TD><SELECT  name="dvkdId" onChange="submitform();">												
													<option value=""  ></option>
												
												<% 	try{
															while (dvkdlist.next()){%>
																
																<%	if(dvkdlist.getString("pk_seq").equals(obj.getDvkdId())){ %>											
																		<option value= <%=dvkdlist.getString("pk_seq")%> selected><%= dvkdlist.getString("donvikinhdoanh") %></option>															
																<%}else{%>
																		<option value= <%=dvkdlist.getString("pk_seq")%> ><%= dvkdlist.getString("donvikinhdoanh") %></option>																																		
																<%	}
															}
															
														}catch(java.sql.SQLException e){
														
														}														
														%>														                                           
                                              			</SELECT>
                                         			</TD>
											</TR>
										</TABLE>									
									</TD>

								</TR>
								
								<TR>
											<TD class="plainlabel" >Từ ngày </TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
										<TR><TD>
											<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" onchange="submitform();">
										</TD>
												
                                    	</TR>
										</TABLE>
									</TD>
										</TR>
								<TR>
                                    <TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="20" onchange="submitform();">
												</TD>
												
                	                     	</TR>
										</TABLE>
									</TD>

								</TR>
								
								<TR>
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange="submitform();">
										  <option value="2"> </option>
										  <% if (obj.getTrangthai().equals("1")){ %>										  
										  		<option value="1" selected>Hoạt động</option>
										  		<option value="0">Ngưng hoạt động</option>
										  <%}else{
										  		if (obj.getTrangthai().equals("0")){ %>										  
										  			<option value="1" >Hoạt động</option>
										  			<option value="0" selected>Ngưng hoạt động</option>
										  		<%}else{%>	
										  			<option value="1">Hoạt động</option>
										  			<option value="0">Ngưng hoạt động</option>
										  		<%}%>
										   <%}%>
										  </select>
									</TD>
                                                                                                                 
							  </TR>
								<TR>
									<TD class="plainlabel">
									<a class="button2" href="javascript:clearform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;	
									
                                  </TD>								
									<TD class="plainlabel">&nbsp;</TD>										
								</TR>
							</TABLE>

							</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>

				<TR>
					<TD >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Nhãn hàng &nbsp; &nbsp; &nbsp;
							<%if(quyen[0]!=0) {%>
							<a class="button3" href="javascript:newform()"><img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
							<%} %>
						</LEGEND>
						<TABLE width="100%" cellpadding="0" cellspacing="0">
							<TR><TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="4%">STT</TH>
									<TH width="15%">Nhãn hàng </TH>
									<TH width="15%">Đơn vị kinh doanh </TH>
									<TH width="10%">Trạng thái </TH>
									<TH width="12%">Ngày tạo</TH>
									<TH width="12%">Người tạo </TH>
									<TH width="12%">Ngày sửa </TH>
									<TH width="12%">Người sửa </TH>
									<TH width="10%" align="center">&nbsp;Tác vụ</TH>
								</TR>
								<%	 
									
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (nhlist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=m+1%></TD>
												<TD align="center"><div align="left"><%=nhlist.getString("ten")%></div></TD>
												<TD align="center"><div align="left"><%=nhlist.getString("donvikinhdoanh") %></div></TD>
									 <% if (nhlist.getString("trangthai").equals("1")){ %>
												<TD align="center">Hoạt động </TD>
									 <%}else{ %>
												<TD align="center">Ngưng hoạt động </TD>
									 <%} %>
									
									<TD align="center"><%=nhlist.getString("ngaytao").toString() %></TD>
									<TD align="center"><%=nhlist.getString("nguoitao") %> </TD>
									<TD align="center"><%=nhlist.getString("ngaysua").toString() %></TD>
									<TD align="center"><%=nhlist.getString("nguoisua") %></TD>
									<TD align="center">
										<TABLE border = 0 cellpadding="0" cellspacing="0">
											<TR><TD>
											<%if(quyen[2]!=0) {%>
												<A href = "../../NhanhangUpdateSvl?userId=<%=userId%>&nhId=<%=nhlist.getString("pk_seq")%>;<%=nhlist.getString("dvkdId") %>" ><img src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
											<%} %>
											</TD>
											<TD>&nbsp;</TD>
											<TD>
											<%if(quyen[1]!=0) {%>
												<A href = "../../NhanhangSvl?userId=<%=userId%>&delete=<%=nhlist.getString("pk_seq")%>;<%=nhlist.getString("dvkdId")%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa nhãn nhãn hàng này?')) return false;"></A>
											<%} %>
											</TD>
											</TR>
										</TABLE>												
									 </TD>
									</TR>
									<% 	m++; }%>
								
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

	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%

 if (nhlist != null) nhlist.close() ; 
 if (dvkdlist != null) dvkdlist.close(); 
 obj.DBClose();
	
	}%>
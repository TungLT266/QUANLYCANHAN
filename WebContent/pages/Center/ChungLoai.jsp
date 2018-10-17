<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.chungloai.IChungloaiList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IChungloaiList obj = (IChungloaiList)session.getAttribute("obj"); %>
<% ResultSet cllist = (ResultSet)obj.getClList() ; %>
<% ResultSet nhList = (ResultSet)obj.getNhList(); 
int[] quyen = new  int[4];
	quyen = util.Getquyen("ChungloaiSvl","",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
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

<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
    document.clForm.chungloai.value = "";
    document.clForm.nhId.selectedIndex = 0;
    document.clForm.tungay.value = "";
	document.clForm.denngay.value = "";       
    document.clForm.trangthai.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.forms['clForm'].action.value= 'search';
	document.forms['clForm'].submit();
}

function newform()
{
	document.forms['clForm'].action.value= 'new';
	document.forms['clForm'].submit();
}
function thongbao()
{var tt = document.forms['clForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['clForm'].msg.value);
	}


function xuatExcel()
{
	document.forms['clForm'].action.value= 'excel';
	document.forms['clForm'].submit();
	document.forms['clForm'].action.value= '';
}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="clForm" method="post" action="../../ChungloaiSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<!--begin common dossier info---> <!--End common dossier info--->
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền sản phẩm &gt; Chủng loại </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;</TD>
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
							  <LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<tr>
								  	<TD class="plainlabel">Chủng loại </TD>
								    <TD class="plainlabel"><input type="text" name="chungloai" value="<%=obj.getCloai() %>" onChange="submitform();"></TD>
						      	</TR>
						      	<TR>
									<TD width="19%" class="plainlabel">Nhãn hàng </TD>
  								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0" border="0">
											<TR>
										  		<TD><SELECT  name="nhId" onChange="submitform();">
										  			<option value=""  ></option>
										  			<%try{ %>
											  			<%while(nhList.next()){ %>	
												  			<%if(nhList.getString("pk_seq").equals(obj.getNhId())){ %>
												  				<option value="<%=nhList.getString("pk_seq")%>" selected="selected"><%=nhList.getString("ten")%></option>
															<%}else {%>
																<option value="<%=nhList.getString("pk_seq")%>" ><%=nhList.getString("ten")%></option>	
																<%}} %>
													<%}catch(SQLException ex){} %>										                                           
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
												<input class="days" type="text" name="tungay" size="20" value = "<%=obj.getTungay()%>" onchange="submitform();">
												</TD>
												
                                    		</TR>
										</TABLE>
									</TD>
								<TR>
								<TR>
									<TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input class="days" type="text" name="denngay" size="20" value = "<%=obj.getDenngay()%>" onchange="submitform();">
												</TD>
												
                                     		</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
								    <TD class="plainlabel">Trạng thái </TD>
									<TD class="plainlabel"><select name="trangthai" onChange="submitform();">
											<option value="2"> </option>
										<% if (obj.getTrangthai().equals("1")){ %>
										  	<option value="1" selected >Hoạt động</option>
										  	<option value="0">Ngưng hoạt động</option>
										 <%}else{ %>
											<% if (obj.getTrangthai().equals("0")){ %>
										  		<option value="1"  >Hoạt động</option>
										  		<option value="0" selected>Ngưng hoạt động</option>
										 	<%}else{ %>
												<option value="1"  >Hoạt động</option>
										  		<option value="0"  >Ngưng hoạt động</option>
										 	<%}}%>										 
										    </select>
						      	</TR>
							    <TR>
									<TD colspan="2" class="plainlabel">
										<a class="button2" href="javascript:clearform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
										<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
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
					<TD width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle">&nbsp;Chủng loại &nbsp; &nbsp;&nbsp; 
						<%if(quyen[0]!=0) {%>
						<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>    
    	<%} %>                        
																		</LEGEND>
	
		   				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<TR>
			  	   				<TD align="left" colspan="4" class="legendtitle">
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
										<TR class="tbheader">
											<TH width="4%">STT</TH>
											<TH width="13%">Chủng loại </TH>
											<TH width="15%">Nhãn hàng </TH>
											<TH width="13%">Trạng thái </TH>
											<TH width="9%">Ngày tạo </TH>
											<TH width="13%">Người tạo</TH>
											<TH width="9%">Ngày sửa </TH>
											<TH width="13%">Người sửa</TH>
											<TH width="11%" align="center">&nbsp;Tác vụ</TH>
										</TR>
									
								<% 
								int m = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								while (cllist.next()){
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
											<TD align="center"><%=m+1%></TD>
											<TD align="left"><%=cllist.getString("chungloai") %></TD>                                   
											<TD><%=cllist.getString("nhanhang") %></TD>

									<% if (cllist.getString("trangthai").equals("1")){ %>
											<TD align="left">Hoạt động</TD>
									<%}else{%>
											<TD align="left">Ngưng hoạt động</TD>
									<%}%>

											<TD align="center"><%=cllist.getString("ngaytao") %></TD>
											<TD align="center"><%=cllist.getString("nguoitao")%></TD>
											<TD align="center"><%=cllist.getString("ngaysua") %></TD>
											<TD align="center"><%=cllist.getString("nguoisua") %> </TD>
											<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[2]!=0) {%>
															<A href = "../../ChungloaiUpdateSvl?userId=<%=userId%>&chId=<%=cllist.getString("clId")%>"><img src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>
														<%} %>
														</TD>
														<TD>&nbsp;&nbsp;</TD>
														<TD>
														<%if(quyen[1]!=0) {%>
															<A href = "../../ChungloaiSvl?userId=<%=userId%>&delete=<%=cllist.getString("clId")%>;<%=cllist.getString("nhId") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa Chủng loại này không ?')) return false;"></A></TD>
														<%} %>
													</TR>
												</TABLE>											
											</TD>
										</TR>
									<% 	m++;
									}%>
							
										<TR>
											<TD height="26" colspan="11" align="center" class="tbfooter">&nbsp;	</TD>
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
<% if(nhList != null) nhList.close(); %>
<% if(cllist != null) cllist.close(); %>
<%
if(obj != null){
   obj.DBClose();
 	obj = null;  
}
	}%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhomkenh.INhomkenh" %>
<%@ page import="java.sql.ResultSet"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% INhomkenh nkBean = (INhomkenh)session.getAttribute("nkBean"); %>

<% ResultSet kbhRs = (ResultSet) nkBean.getKbhRs(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout(){
    if(confirm("Ban co muon dang xuat?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 {
     document.forms['nkForm'].submit();
 }
 function saveform()
 {
	 var nkTen = document.getElementById('nk');
	 var diengiai = document.getElementById('diengiai');
	 if(nkTen.value == "")
	 {
		 alert('Vui Long Nhap Ten He Thong Ban Hang');
		 return;
	 }
	 if(diengiai.value == "")
	 {
		 alert('Vui Long Nhap Dien Giai');
		 return;
	 }	
 	 document.forms['nkForm'].action.value= 'save';
     document.forms['nkForm'].submit();
 }
 function SelectALL2()
	{
		var checkAll = document.getElementById("checkAll2");
		var kbhIds = document.getElementsByName("kbhId");
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < kbhIds.length; i++)
				kbhIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < kbhIds.length; i++)
				kbhIds.item(i).checked = false;
		}
		
	}
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name='nkForm' method="post" action="../../NhomkenhUpdateSvl">
<input type="hidden" name="action" value='1'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   	Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Nhóm kênh > Tạo mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=userTen %>&nbsp;  </TD> 
						    </tr>
   
						   	<tr>
						   		<TD align="left" height="5" colspan="4" class=""></td>
   
  							</tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../NhomkenhSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
		    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= nkBean.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
				
				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Nhóm kênh
								</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
									<TR>
									  <TD width="15%" class="plainlabel" >Nhóm kênh <FONT class="erroralert">*</FONT></TD>
									  <TD  class="plainlabel" ><INPUT name="nhomkenh" id="nk"
										type="text" value='<%= nkBean.getNhomkenh() %>' size="20"></TD>
								  </TR>
									<TR>
									  <TD class="plainlabel" >Diễn giải </TD>
									  <TD class="plainlabel" >
									  	<INPUT name="diengiai" id="diengiai" type="text" value='<%= nkBean.getDiengiai() %>' size="80"></TD>
								  </TR>
									<TR>
									  <TD  class="plainlabel" ><label>
										<%  if (nkBean.getTrangthai().equals("1")){%>
										  	<input name="trangthai" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="trangthai" type="checkbox" value ="0">
										<%} %>
									    Hoạt động</label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>
								  </TR>
								</TABLE>
								</FIELDSET>
							</TD>	
						</TR>
					</TABLE>
				<TABLE width="100%" border="0" cellpadding="0"
											cellspacing="0">
											<TR>
												<TD>
													<FIELDSET>
														<LEGEND class="legendtitle" style="color: black">Chọn kênh bán hàng</LEGEND>
														<TABLE border="0" width="100%" cellpadding="4"
															cellspacing="1">
															<TR class="tbheader">
																<TH width="30%">Kênh</TH>
																<TH width="60%">Diễn giải</TH>
																<th align="center" width="10%">Chọn <input type="checkbox" name="checkAll2" id="checkAll2" onchange="SelectALL2();" ></TH>
															</TR>
															<%
												try
												{
													String lightrow = "tblightrow";
													String darkrow = "tbdarkrow";
													int m = 0;
													if (!(kbhRs == null))
													{
														while (kbhRs.next())
														{
															if (m % 2 != 0)
															{
															%>
															<TR class=<%=lightrow%>>
																<%
																	} else{
																%>

															<TR class=<%=darkrow%>>
																<%
																	}
																%>

																<TD><%=kbhRs.getString("ten")%></TD>
																<TD><div align="left"><%=kbhRs.getString("diengiai")%>
																	</div></TD>
																<TD>
																	<div align="center">
																		<%
																		if (nkBean.getKbhId().indexOf(kbhRs.getString("pk_Seq")) >=0  ) {%>
																		<input type="checkbox" name="kbhId" value='<%=kbhRs.getString("pk_Seq")%>' checked>
																		<%
																			} else{
																		%>
																		<input type="checkbox" name="kbhId" value='<%=kbhRs.getString("pk_Seq")%>'>
																		<%
																			}
																		%>

																	</div>
																</TD>
															</TR>
															<%
																m++;
																		}
															kbhRs.close();
																	}
																} catch (java.sql.SQLException e)
																{
																	e.printStackTrace();
																}
															%>

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
nkBean.DBClose();
	}%>
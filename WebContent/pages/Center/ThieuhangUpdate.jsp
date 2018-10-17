<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.theodoithieuhang.ITheodoithieuhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% 	ITheodoithieuhang thBean = (ITheodoithieuhang)session.getAttribute("obj");%>
<% 	ResultSet th = (ResultSet)thBean.getThlist(); 
	String Id = "";
	String tensp = "";
	String slthieu = "";
	String slbosung = "";
	String ngay = "";
	try{
		th.next();
		Id = th.getString("id");
		
		tensp = th.getString("tensp");
		slthieu = th.getString("soluongthieu");
		slbosung = th.getString("soluongdt");
		ngay = th.getString("ngaydt");
	

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>

<SCRIPT language="javascript" type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript" type="text/javascript">
<!--HPB_SCRIPT_CODE_40
//-->
function submitform()
{
    document.forms["thForm"].submit();
}


</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="thForm" method="post" action="../../TheodoithieuhangSvl" charset="utf-8">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="action" type="hidden" value='1' size="30">
<INPUT name="Id" type="hidden" value='<%= Id %>' type="text" />

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">

		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tồn kho &gt; Báo cáo &gt; Theo dõi thiếu hàng &gt; Cập nhật </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
		</TABLE>		
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../TheodoithieuhangSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
		</TABLE>
					
		<TABLE width = 100% cellpadding = "4" cellspacing = "0" border = "0">
		  	<tr>
				<TD align="left" colspan="4" class="legendtitle">
					<FIELDSET>
					<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=thBean.getMsg()%></textarea>
						<% thBean.setMsg(""); %>
						</FIELDSET>
				   </TD>
			</tr>			
			<TR>
				<TD width="100%" align="left" >
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Cập nhật thiếu hàng &nbsp;</LEGEND>

					<TABLE  width="100%" cellpadding="6" cellspacing="0">
						<TR>
							<TD width="20%" class="plainlabel">Sản phẩm <FONT class="erroralert">*</FONT></TD>
							<TD width="80%" class="plainlabel"><INPUT name="tensp" value='<%= tensp %>' type="text" size="40" readonly=readonly /></TD>
						</TR>

						<TR>
							<TD width="20%" class="plainlabel">Số lượng thiếu <FONT class="erroralert">*</FONT></TD>
							<TD width="80%" class="plainlabel"><INPUT name="thieu" value='<%= slthieu %>' type="text" size="40" readonly=readonly /></TD>
						</TR>

						<TR>
							<TD width="20%" class="plainlabel">Số lượng bổ sung <FONT class="erroralert">*</FONT></TD>
							<TD width="80%" class="plainlabel"><INPUT name="bosung" value='<%= slbosung %>' type="text" size="40"/></TD>
						</TR>
								
						<TR>
                            <TD class="plainlabel" >Ngày bổ sung </TD>
						 	<TD class="plainlabel" >
								<TABLE cellpadding="0" cellspacing="0">
									<TR>
										<TD>
											<input type="text" name="ngay" value="<%= ngay  %>" size="20" onFocus="submitform();">
										</TD>
										<TD>
<a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'thForm.ngay',false,'yyyy-mm-dd'); return false;">
		  &nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>

        		                         </TD>
                	               	</TR>
								</TABLE>
							</TD>

						</TR>
								
								
				</TABLE>

				</FIELDSET>
			</TD>
		</TR>
	</TABLE>
	</TD></TR>
</TABLE>
</form>
</BODY>
</HTML>
<%  
 
	if(th != null)
		th.close();
	if(thBean != null)
		thBean.DBClose();
	}catch(java.sql.SQLException e){}
}%>
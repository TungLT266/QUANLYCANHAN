<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page import="geso.traphaco.erp.beans.baocaotaisancodinh.IBcTaisancodinh"%>
<%@ page import="geso.traphaco.erp.beans.baocaotaisancodinh.imp.BcTaisancodinh"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
%>
<%


	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		IBcTaisancodinh obj = (IBcTaisancodinh) session.getAttribute("obj");

		ResultSet RsLts = obj.getLoaiTS() ;
	
	 	int[] quyen = new  int[5];
	 	quyen = util.Getquyen("ThetaisancodinhSvl","",userId);


%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Best - Project</title>
	
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript" src="../scripts/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
	
<script type="text/javascript">
      $(document).ready(function() {            
            $( ".days" ).datepicker({                     
                        changeMonth: true,
                        changeYear: true                    
            });            
      });   
</script>
<script language="javascript" type="text/javascript">

function submitform()
{
	document.forms['tsForm'].submit();
}


function taobaocao()
{
	document.forms['tsForm'].action.value= 'tao';
	document.forms['tsForm'].submit();
}

</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="tsForm" method="post" action="../../ThetaisancodinhSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="action" value='1'>
		<script language="javascript" type="text/javascript">
			thongbao();
		</script>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<td align="left" colspan="2" class="tbnavigation">Quản lý tài sản > Thẻ tài sản</td>
										<td align="right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen%> &nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td width="100%" align="center">
											<fieldset>
												<legend class="legendtitle">Thẻ tài sản &nbsp;</legend>
												<table width="100%" cellpadding="6" cellspacing="0">
													<tr>
														<td class="plainlabel" width="15%">Loại tài sản</td>
														<td class="plainlabel"><select name="ltsId" onchange="submitform()">
																<option value=""></option>
																<%if (RsLts != null) {
														while (RsLts.next()) {
															if (obj.getLtsId().equals(RsLts.getString("LTSID"))) {%>
																<option selected="selected" value="<%=RsLts.getString("LTSID")%>"><%=RsLts.getString("LTS")%></option>
																<%} else {%>
																<option value="<%=RsLts.getString("LTSID")%>"><%=RsLts.getString("LTS")%></option>
																<%}

															}
														}%>
														</select></td>
													</tr>
													<tr>
														<td class="plainlabel" width="15%">Tài sản</td>
														<TD class="plainlabel"><input type="text" name = "tsId" id = "tsId" value="<%= obj.getTsId() %>" style="width:300px;"></td>
													</tr>
													
								                    <tr>
								                        <td class="plainlabel" colspan = "2">
								                        <a class="button"
															href="javascript:taobaocao();"> <img style="top: -4px;"
															src="../images/button.png" alt=""> Tạo báo cáo </a>
															
                        								</td>
                    								</tr> 
                    
						                    	</TABLE>                  
       										</fieldset>            					                    
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
	jQuery(function()
		{		
			$("#tsId").autocomplete("TaiSanErpList_2.jsp");
		});	
	</script>
	
<%
	try{
		if(RsLts!=null){
			RsLts.close();
		}
		session.setAttribute("obj",null);
		obj.DBClose();
	}catch(Exception er){
		er.printStackTrace();
	}
}
%>
</body>
</html>


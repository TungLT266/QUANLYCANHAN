<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="geso.traphaco.center.beans.chitieu.imp.ChiTieuNPP"%>
<%@page import="java.util.Calendar"%>
<%@page import="geso.traphaco.center.beans.chitieuttchovung.imp.ChiTieuTTKhuVuc"%>
<%@page import="geso.traphaco.center.beans.chitieuttchovung.imp.ChiTieuTTChoVung"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="geso.traphaco.center.beans.chitieu.imp.ChiTieu"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhomkhuyenmai.INhomkhuyenmai" %>
<%@ page  import = "geso.traphaco.center.beans.nhomkhuyenmai.imp.Nhomkhuyenmai" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	//Lay doi tuong objbean
 	IStockintransit obj=(IStockintransit)session.getAttribute("obj");
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet npp = obj.getnpp();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
	
});
</script>


<SCRIPT language="JavaScript" type="text/javascript"> 
function upload()
{
	if(document.forms["ChiTieuTTForm"].filename.value==""){
 		alert("Vui lòng chọn file upload !");
 		return;
 	}
	if(document.forms["ChiTieuTTForm"].nppId.value==""){
 		alert("Vui lòng chọn Nhà phân phối !");
 		return;
 	}
	else
	{
		// Kiểm tra định dạng file có đúng là xls hay không !
		 var res_field = document.forms["ChiTieuTTForm"].filename.value;   
		  var extension = res_field.substr(res_field.lastIndexOf('.') + 1).toLowerCase();
		  var allowedExtensions = ['xls'];
		  if (res_field.length > 0)
		     {
		          if (allowedExtensions.indexOf(extension) === -1) 
		             {
		               alert('Sai Format. Chỉ được phép Upload định dạng file excel: ' + allowedExtensions.join(', ') + '');
		               return ;
		             }
		    }	
	}
	 document.forms["ChiTieuTTForm"].setAttribute('enctype', "multipart/form-data", 0);
    document.forms["ChiTieuTTForm"].submit();
  
}
function seach()
{
	
	document.forms['ChiTieuTTForm'].action.value= 'seach';
    document.forms["ChiTieuTTForm"].submit();
}
function thongbao()
{
	 var tt = document.forms["ChiTieuTTForm"].msg.value;
	 if(tt.length>0)
    	alert(document.forms["ChiTieuTTForm"].msg.value);
	document.forms["ChiTieuTTForm"].msg.value = "";
 }

</SCRIPT>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="ChiTieuTTForm" method="post" action="../../UploadTuyenBanHangSvl" >
<input type="hidden"  name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="action" value="0">
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản trị hệ thống &gt; Upload tuyến bán hàng</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin chỉ tiêu</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						
							
							
						 <%--  
				  		   <TR>
								<TD class="plainlabel">Vùng/Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(obj.getvungId())) {%>
											   				<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
											   			<%} else {%>
											   				<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
														<%}}%>
										</select>
									</TD>
						  </TR> --%>
                      <%-- <TR>									
									<TD class="plainlabel">Khu vực</TD>
									<TD class="plainlabel">
									<select name="khuvucId" id="khuvucId" onchange="seach();" >
											<option value="" selected>All</option>
											<%if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
											   <option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
											<%}}%>
									</select>
									</TD>	
									</TR> --%>
									<TR>
									<TD class="plainlabel">Chi nhánh / Đối tác</TD>
									<TD class="plainlabel">
										<select name="nppId" id="nppId"  style="width:250px">
											<option value=""></option>
												<%if(npp !=null)
												{
													while(npp.next())
													{
														if(npp.getString("pk_seq").equals(obj.getnppId()))
														{	%>
													<option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten") %></option>
														<%} 
														else
														{ %>
													<option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten") %></option>
													<%}}} %>	
										</select>
									</TD>	
									</TR>
						  	<tr class="plainlabel">
						  
						  	  <td colspan="2">
						  	  <INPUT type="file" name="filename" size="40" value=''> 
						  	  </td> 
						  	</tr>
						  	<tr class="plainlabel">
						  	<td colspan="2">
						  		&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:upload()">
								<img style="top: -4px;" src="../images/button.png" alt="">Upload</a>							
						  	</td>
						  	</tr>
						  	
						 
						  	
						</TABLE>
													
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
<script type="text/javascript">

</script>
</BODY>
<%
	try
	{
		if(vung!=null){
			vung.close();
		}
		if(khuvuc != null)
			khuvuc.close();
		if(npp != null)
		npp.close();
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
	}catch(java.sql.SQLException e){}
	
%>
</HTML>
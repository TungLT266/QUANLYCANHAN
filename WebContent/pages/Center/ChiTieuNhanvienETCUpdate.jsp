<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.center.beans.chitieunhanvienetc.imp.ChiTieuNhanvienETC"%>
<%@page import="geso.traphaco.center.beans.chitieunhanvienetc.IChiTieuNhanvienETC"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>

<% NumberFormat formatter = new DecimalFormat("#,###,##0.##"); %>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	IChiTieuNhanvienETC objbean = (ChiTieuNhanvienETC)session.getAttribute("obj");
 	
 	boolean duocSua = true;
 	if( objbean.getDisplay().equals("1") )
 		duocSua = false;
 	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Phanam - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["ChiTieuTTForm"].submit();
	}

	function xuatexcel()
	{
		document.forms['ChiTieuTTForm'].action.value= 'excel';
		document.forms['ChiTieuTTForm'].submit();
	}
	
	function save(){
		
		document.forms['ChiTieuTTForm'].action.value= 'save';
		document.forms['ChiTieuTTForm'].submit();
	}
	
	function upload(){
		
		document.forms['ChiTieuTTForm'].setAttribute('enctype', "multipart/form-data", 0);
	    document.forms['ChiTieuTTForm'].submit();
	}

 </SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="ChiTieuTTForm" method="post" action="../../ChiTieuNhanvienETCNewSvl" >
<input type="hidden"  name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value='<%= objbean.getID() %>'>
<input type="hidden" name="isDisplay" value="<%= objbean.getDisplay() %>">
<input type="hidden" name="nppId" value='<%= objbean.getNppId() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;
							 <%if(objbean.getID().length() <= 0){ %>
							 	Quản lý chỉ tiêu > Khai báo > Chỉ tiêu nhân viên > Tạo mới
							 <%} else if(objbean.getID().length() > 0 && objbean.getDisplay().equals("1")){ %>
							 	Quản lý chỉ tiêu > Khai báo > Chỉ tiêu nhân viên > Hiển thị
							 <%} else{ %>
							 	Quản lý chỉ tiêu > Khai báo > Chỉ tiêu nhân viên > Cập nhật
							 <%} %>
							 
							 </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ChiTieuNhanvienETCSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <%if(!objbean.getDisplay().equals("1") && duocSua){ %>
						    	<TD width="30" align="left" ><A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<%} %>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=objbean.getMessage()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin chỉ tiêu</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

						<TR>
							<TD width="120px" class="plainlabel" >Tháng </TD>
							
							<TD class="plainlabel">
								<select name="thang" style="width :100px"  >
								<option value = ''> </option>  
								<%
 									for(int k = 1; k <= 12; k++){
 									  if( Integer.toString(k).equals( objbean.getThang() ) ){
 									%>
									<option value='<%=k%>' selected="selected" > <%=k%></option> 
								<% } else { %>
									<option value='<%=k%>' ><%=k%></option> 
								<% } } %>
								</select>
							</TD>
						</TR>
						<TR>
						  	<TD class="plainlabel">Năm</TD>
					  	  	<TD class="plainlabel">
								<select name="nam"  style="width :100px" >
									<option value = '' > </option>  
									<%
								  		Calendar c2 = Calendar.getInstance();
 										int t=c2.get(Calendar.YEAR) + 6;
 										for(int n = 2015; n < t; n++){
 										if( Integer.toString(n).equals( objbean.getNam() ) ){
 									%>
									<option value='<%=n%>' selected="selected" > <%=n%></option> 
									<% }else{ %>
										<option value='<%=n%>' ><%=n%></option> 
									<% } } %>
								</select>
					  	  	</TD>
					    </TR>
						<TR>
					  	  	<TD class="plainlabel">Loại chỉ tiêu</TD>
					  	  	<TD class="plainlabel" >
					  	  		<select name="loaichitieu" onchange="submitform();" >
					  	  			<% if( objbean.getLoai().equals("0") ) { %>
					  	  				<option value="0" selected="selected" >OTC</option>
					  	  			<% } else { %>
					  	  				<option value="0"  >OTC</option>
					  	  			<% } %>
					  	  			<% if( objbean.getLoai().equals("1") ) { %>
					  	  				<option value="1" selected="selected" >ETC</option>
					  	  			<% } else { %>
					  	  				<option value="1"  >ETC</option>
					  	  			<% } %>
					  	  		</select> 
					  	  	</TD>
					  	</TR>		
						
					  	<TR>
					  	  	<TD class="plainlabel">Diễn giải</TD>
					  	  	<TD class="plainlabel" >
					  	  		<input  type="text"  name="diengiai" value="<%=objbean.getDienGiai()%>" style="width: 500px;"  > 
					  	  	</TD>
					  	</TR>					  	
					  	
					  	<TR>
						  	<TD class="plainlabel">Chọn tập tin</TD>
					  	  	<TD class="plainlabel"  >
					  	  		<INPUT type="file" name="filename" size="40" value=''>&nbsp;&nbsp;&nbsp;&nbsp;
						  	  		<a class="button2"  href="javascript:upload();"><img style="top: -4px;" src="../images/button.png" alt="">Import File</a>
					  	  	</TD>
					   </TR>  
						  	
					  	<tr>
					  		<td colspan="2" >
					  			<TABLE style="width: 100%" cellspacing="1" >
								
									<TR class="tbheader">
									<% if( objbean.getLoai().equals("0") ) { %>
										<TH width="10%">Code TDV</TH>
										<TH width="20%">Tên NV</TH>
										<TH width="14%">Hàng chiến lược</TH>
										<TH width="14%">Hàng đặc trị</TH>
										<TH width="14%">Tổng DS khoán</TH>
										<TH width="14%">KPI chiến lược</TH>
										<TH width="14%">KPI đặc trị</TH>
									<% } else { %>
										<TH width="10%">Code TDV</TH>
										<TH width="30%">Tên NV</TH>
										<TH width="15%">CLC</TH>
										<TH width="15%">INS</TH>
										<TH width="15%">Tổng </TH>
										<TH width="15%">DS tối thiểu PK</TH>
									<% } %>
									</TR>
									
									<%
										String[] codeTDV = objbean.getCodeTDV();
										String[] tenTDV = objbean.getTenTDV();
										String[] hangchienluoc = objbean.getHangchienluoc();
										String[] hangdactri = objbean.getHangdactri();
										String[] dskhoan = objbean.getTongdskhoan();
										String[] kpiChienluoc = objbean.getKPIChienluoc();
										String[] kpiDactri = objbean.getKPIDactri();
										
										if( codeTDV != null )
										{
											for( int i = 0; i < codeTDV.length; i++ )
											{
												%>
												
												<TR>
													<TD>
														<input type="text" readonly="readonly" name="codeTDV" value="<%= codeTDV[i] %>" style="width: 100%;" >
													</TD>
													<TD>
														<input type="text" readonly="readonly" name="tenTDV" value="<%= tenTDV[i] %>" style="width: 100%;" >
													</TD>
													<TD>
														<input type="text" readonly="readonly" name="hangchienluoc" value="<%= hangchienluoc[i] %>" style="width: 100%; text-align: right;" >
													</TD>
													<TD>
														<input type="text" readonly="readonly" name="hangdactri" value="<%= hangdactri[i] %>" style="width: 100%; text-align: right;" >
													</TD>
													<TD>
														<input type="text" readonly="readonly" name="dskhoan" value="<%= dskhoan[i] %>" style="width: 100%; text-align: right;" >
													</TD>
													<TD>
														<input type="text" readonly="readonly" name="kpiChienluoc" value="<%= kpiChienluoc[i] %>" style="width: 100%; text-align: right; " >
													</TD>
													
													<% if( objbean.getLoai().equals("0") ) {  %>
													<TD>
														<input type="text" readonly="readonly" name="kpiDactri" value="<%= kpiDactri[i] %>" style="width: 100%; text-align: right;" >
													</TD>
													<% } %>
													
												</TR>
												
											<% } } %>
								</TABLE>
					  			
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
</BODY>
</HTML>
<% if(objbean!=null)objbean.closeDB();%>
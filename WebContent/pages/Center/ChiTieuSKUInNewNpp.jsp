s<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.distributor.beans.chitieunpp.*"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.util.List" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Bibica/index.jsp");
	}else{ %>

<%
	IChitieuSKUInTT obj =(IChitieuSKUInTT)session.getAttribute("obj");
	List<IChitieusku> listddkd= (List<IChitieusku>) obj.getSpList();
	 ResultSet dvkd = (ResultSet)obj.getDvkdRs();
	 ResultSet kenh = (ResultSet)obj.getKbhRs();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
function save()
{	
		document.forms['ChitieuSKU'].removeAttribute('enctype', "multipart/form-data", 0);
	  var thang = document.forms["ChitieuSKU"].thang.value;
	  var diengiai = document.forms["ChitieuSKU"].diengiai.value;
	  var nam = document.forms["ChitieuSKU"].namxx.value;
	  var dvkd = document.forms["ChitieuSKU"].donvikinhdoanh.value;
	  var kbh = document.forms["ChitieuSKU"].kenhbanhang.value;
	 
	  if( thang == 0 )
	  {
		  document.forms["ChitieuSKU"].dataerror.value="Chọn tháng cần đặt chỉ tiêu ";
		  return;
	  }
	  if( nam == 0 )
	  {
		  document.forms["ChitieuSKU"].dataerror.value="Chọn năm cần đặt chỉ tiêu ";
		  return;
	  }
	  if( dvkd == 0 )
	  {
		  document.forms["ChitieuSKU"].dataerror.value="Chọn đơn vị kinh doanh";
		  return;
	  }
	  if( kbh == 0 )
	  {
		  document.forms["ChitieuSKU"].dataerror.value="Chọn kênh bán hàng ";
		  return;
	  }
	  if( diengiai== 0 )
	  {
		  document.forms["ChitieuSKU"].dataerror.value="Nhập vào diễn giải";
		  return;
	  }
	
	document.forms["ChitieuSKU"].action.value="save";
	document.forms["ChitieuSKU"].submit();
}

	
	function upload()
	{
		var up= document.getElementById("filename" );
		if( up.value == '' )
		  {
			  document.forms["ChitieuSKU"].dataerror.value="Không có file updload";
			  return;
		  }
		document.forms['ChitieuSKU'].setAttribute('enctype', "multipart/form-data", 0);
	    document.forms['ChitieuSKU'].submit();
	}

</SCRIPT>
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script> 
<!-- <script type="text/javascript">
$( function() {
	//Created By: Brij Mohan
	//Website: http://techbrij.com
	function groupTable($rows, startIndex, total)
	{
		if (total === 0)
		{
			return;
		}
		var i , currentIndex = startIndex, count=1, lst=[];
		var tds = $rows.find('td:eq('+ currentIndex +')');
		var ctrl = $(tds[0]);
		lst.push($rows[0]);
		for (i=1;i<=tds.length;i++){
		if (ctrl.text() ==  $(tds[i]).text()){
		count++;
		$(tds[i]).addClass('deleted');
		lst.push($rows[i]);
		}
		else{
			if (count>1){
			ctrl.attr('rowspan',count);
			groupTable($(lst),startIndex+1,total-1)
			}
			count=1;
			lst = [];
			ctrl=$(tds[i]);
			lst.push($rows[i]);
		}
		}
	}
	var rowCount = $('#sku tr').length;

	groupTable($('#sku tr:has(td)'),0,rowCount);
	$('#sku .deleted').remove();
	});
    </script> -->
<script type="text/javascript" src="../scripts/ajax.js"></script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="ChitieuSKU" ENCTYPE="multipart/form-data" method="post" action="../../ChiTieuSKUInUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu > Chỉ tiêu SKU In > Tạo mới</TD> 
							  <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ChiTieuSKUInSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
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
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin chỉ tiêu </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="20%" class="plainlabel" >Tháng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="thang" style="width: 80px">
									<option value=0> </option>  
									<%
									int k=1;
									for(k=1; k <= 12; k++ )
									{
										System.out.println(obj.getThang());
									  if(obj.getThang().equals(String.valueOf(k)) ) {
									%>
										<option value=<%= k %> selected="selected" > <%= k %></option> 
									<%  }else{  %>
										<option value=<%= k %> > <%= k %></option> 
									<% } }%>
									</select>
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Năm <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel">
									<select name="namxx" style="width :80px">
									<option value= ""> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									System.out.println(obj.getNam());
									for(int n=2008; n<year_+3; n++) {
									  if(obj.getNam().equals(String.valueOf(n)) ){									  
									%>
										<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
										<option value=<%=n %> ><%=n %></option> 
									<% } }
									%>
									</select>
						  	  	</TD>
						  </TR>
						   <TR>								  
								    <TD class="plainlabel">Kênh bán hàng<FONT class="erroralert"> *</FONT></TD>
									<TD colspan="5" class="plainlabel"> 
						 			<SELECT name="kenhbanhang" id="kenhbanhang" >
							 			 <option value=""></option>
										  <% if(kenh != null){
											  try{ while(kenh.next()){ 													 
								    			if(kenh.getString("pk_seq").equals(obj.getKbhId())){ %>
								      				<option value='<%=kenh.getString("pk_seq")%>' selected><%=kenh.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=kenh.getString("pk_seq")%>'><%=kenh.getString("ten") %></option>
								     			<%}}
											  }
											  catch(java.sql.SQLException e){}}
										  kenh.close(); %>	 
                                 			</SELECT></TD>														
								</TR>
						  	<TR>								  
								    <TD class="plainlabel">Đơn vị kinh doanh <FONT class="erroralert"> *</FONT></TD>
									<TD colspan="5" class="plainlabel"> 
						 			<SELECT name="donvikinhdoanh" id="donvikinhdoanh" >
							 			 <option value=""></option>
										  <% if(dvkd != null){
											  try{ while(dvkd.next()){ 													 
								    			if(dvkd.getString("pk_seq").equals(obj.getDvkdId())){ %>
								      				<option value='<%=dvkd.getString("pk_seq")%>' selected><%=dvkd.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=dvkd.getString("pk_seq")%>'><%=dvkd.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){}} dvkd.close(); %>	 
                                 			</SELECT></TD>														
								</TR>
						    <TR>
						  	  	<TD class="plainlabel">Diễn giải<FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel">
						  	  		<input type="text" style="width :400px" name="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  	</TD>
						  	</TR>
						  	
						  	<TR>
						  	  	<TD class="plainlabel">Chọn tập tin</TD>
						  	  	<TD class="plainlabel">
						  	  		<INPUT type="file" name="filename" id="filename"  size="40" value=''>
						  	  	</TD>
						  	</TR>
						  	 <TR>							  	
						  	  	<TD class="plainlabel" colspan="2">
						  	  	<a class="button2"  href="javascript:upload()">
									<img style="top: -4px;" src="../images/button.png" alt="">Cập nhật</a>&nbsp;&nbsp;&nbsp;&nbsp;	
						  	  	</TD>
						  	</TR>
						</TABLE>
					<%-- 	<div style="overflow:scroll;height: 800px " >
						<TABLE  ID="sku" border="1" width="100%" cellpadding="3" cellspacing="0">							
								<TR class="tbheader">
									<TH align="center" width="32%">Nhà phân phối</TH>
									<TH align="center" width="15%">Mã sản phẩm</TH>
									<TH align="center" width="38%">Tên sản phẩm</TH>
									<TH  align="center" width="15%">Số lượng</TH>
								</TR>
								<%
								 NumberFormat formatter = new DecimalFormat("#,###,###");
										int m=0;
										if(listddkd != null)
										{
											while (m < listddkd.size()){
												   IChitieusku ddkd = listddkd.get(m);
															
											%>
											<tr>
											<td align="center"><%=ddkd.getNppten() %></td>
											<td> <%=ddkd.getMasanpham() %> </td>
											<td align="left"><%=ddkd.getTensanpham() %> </td>
											<%
											String soluong="";
												try
											{
												soluong=formatter.format(Double.parseDouble(ddkd.getSoluong()));
											}
											catch(Exception e)
											{
												System.out.println(e.toString());
												soluong="0";
											}
											%>
											<td align="right"><%= soluong%>  </td>
										    </tr>
											<%
											m++;
								}
							}
							%>
						</TABLE>	
						</div>	 --%>
						<TABLE  ID="sku23"  border="1" width="100%" cellpadding="3" cellspacing="0">						
								<TR class="tbheader">
									<TH align="center" width="15%">Nhà phân phối</TH>
									<TH align="center" width="15%">Mã sản phẩm</TH>
									<TH align="center" width="40%">Tên sản phẩm</TH>
									<TH  align="center" width="15%">Số lượng</TH>
								</TR>
								<%
										
										int n=0;
										if(listddkd != null)
										{
											while (n < listddkd.size()){
												   IChitieusku ddkd = listddkd.get(n);
															
											%>
											<tr>
											<td align="center"><%=ddkd.getNppten() %><input type ="hidden" name="npp" readonly="readonly" style="width :100%;text-align: center;" value=<%=ddkd.getNpp() %>> </td>
											<td align="center"><input type ="text" name="masp" readonly="readonly" style="width :100%;text-align: center;" value=<%=ddkd.getMasanpham() %>> </td>
											<td align="center"><input type ="text" name="tensp" readonly="readonly" style="width :100%;text-align: left;" readonly="readonly" value="<%=ddkd.getTensanpham() %>"> </td>
											<td align="right"> <input type ="text" name="soluong" readonly="readonly" style="width :100%;text-align: right;" value="<%= ddkd.getSoluong()%>"> </td>
										    </tr>
											<%
											n++;
								}
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
 try{
 obj.DbClose();
 listddkd.clear();
 if(dvkd!=null){
	 dvkd.close(); 
 }
 if(kenh!=null){
	 kenh.close();
 }
 }catch(Exception er){ 
 }

}%>
<%@page import="geso.traphaco.center.beans.chitieunpp.IChiTieuNhaPP"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="geso.traphaco.center.beans.chitieunpp.imp.ChiTieuDDKD"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	
 	IChiTieuNhaPP objbean=(IChiTieuNhaPP)session.getAttribute("obj");
	List<ChiTieuDDKD> listddkd= (List<ChiTieuDDKD>)objbean.getListChiTieuDDKD();
	ResultSet rs_dvkd=objbean.getrsdvkd();
	ResultSet rs_ctddkd=objbean.getChitieuDDKd();
	ResultSet rs_kenh=objbean.getRsKenh();
	String []nhomsp=objbean.getNhomSp();
	String []nhomspid=objbean.getNhomSpId();
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	
	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	document.forms["ChiTieuTTForm"].tongchitieu.value="0";
	document.forms["ChiTieuTTForm"].action.value="loadchitieu";
    document.forms["ChiTieuTTForm"].submit();
}
function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
{    
	var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = e.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
		{//Phím Delete và Phím Back
			return;
		}
		return false;
	}
}
	function save(){
			var thanhtien = document.getElementsByName("chitieu");
			var tongtien = 0;
			for(var i=0; i < thanhtien.length; i++)
				{
				if(thanhtien.item(i).value != "")
				{
					var thanh_tien = thanhtien.item(i).value;
					while(thanh_tien.match(","))
					{
						thanh_tien=thanh_tien.replace(",","");
					}
					tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
				}
				}  
			var tongchitieu= document.forms["ChiTieuTTForm"].tongchitieu.value.replace(",","");
			while(tongchitieu.match(","))
			{
				tongchitieu=tongchitieu.replace(",","");
			}
			if(parseFloat(tongchitieu)!= tongtien)
			{
		  		document.forms["ChiTieuTTForm"].dataerror.value="Nhập tổng chỉ tiêu của Khu Vực phải bằng tổng chỉ tiêu. ";
		  		return;
		  	}
					
			  
		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		
		document.forms["ChiTieuTTForm"].action.value="update";
		document.forms["ChiTieuTTForm"].submit();


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
	 function Dinhdang(element)
	{
		element.value=DinhDangTien(element.value);
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
	}
</SCRIPT>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="ChiTieuTTForm" method="post" action="../../ChiTieuNhaPPTTNewSvl" >
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%=objbean.getID() %>">
<input type="hidden" name="tenform" value="update">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
		  					 <TD align="left" colspan="2" class="tbnavigation">&nbsp; Quản lý chỉ tiêu > khai báo >Chia chỉ tiêu DDKD> Cập nhật </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;  </TD></tr>
          						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ChiTieuNppTTSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=objbean.getMessage()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin chỉ tiêu </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						<TR>
								<TD width="20%" class="plainlabel" >Chi nhánh / Đối tác <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
								<input name="tennpp" type="text" value="<%=objbean.getTenNPP()%>">
								</TD>
							</TR>
							
							<TR>
								<TD width="20%" class="plainlabel" >Tháng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
								<input name="thang" type="text" value="<%=objbean.getThang()%>">
									
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Năm</TD>
						  	  	<TD class="plainlabel">
									<input name="nam" type="text" value="<%=objbean.getNam()%>"> 
									
						  	  	</TD>
						  </TR>
						    <tr class="plainlabel">
                             <td> Đơn vị kinh doanh </td>
                             <td>
                             <select name=selectdvkd  >
                               
                             <%
                             if (rs_dvkd!=null){
                            	 while (rs_dvkd.next()){                      				                       				
                       				 if(rs_dvkd.getString("pk_seq").equals(objbean.getDVKDId())){ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>" selected="selected"> <%=rs_dvkd.getString("donvikinhdoanh") %></option>
                       				<%}else{ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>"> <%=rs_dvkd.getString("donvikinhdoanh") %></option>
                       				<%}     		
                            	 }
                            
                             }
                             %>
                             </select>
                             </td>                           
                             </tr>
                               <tr class="plainlabel">
                             <td>Kênh bán hàng </td>
                             <td>
								 <select name=kenhid >
                             
            
                             <%
                          
                             if (rs_kenh!=null){
                            	 while (rs_kenh.next()){
                       				%>
                       				<% if(rs_kenh.getString("pk_seq").equals(objbean.getKenhId())){ %>
                       				<option value ="<%=rs_kenh.getString("pk_seq") %>" selected="selected"> <%=rs_kenh.getString("ten") %></option>
                       				<%}else{ %>
                       				<option value ="<%=rs_kenh.getString("pk_seq") %>"> <%=rs_kenh.getString("ten") %></option>
                       				<%} %>
                       				<%     		
                            	 }
                             }
                             %>
                             </select>                            
                             </td>
                             </tr>
				  		   <TR>
						  	  	<TD class="plainlabel">Số chỉ tiêu</TD>
						  	  <TD class="plainlabel">
						  	  <input onkeypress="return keypress(event);" type="text" readonly="readonly" name="tongchitieu" value="<%= formatter.format(objbean.getChitieu())%>" > 
						  	  	</TD>
						  	</TR>
						  	<TR>
						  	  	<TD class="plainlabel">Số ngày làm việc </TD>
						  	  	<TD class="plainlabel">
						  		<input type="text" name="songaylamviec" readonly="readonly" value="<%=objbean.getSoNgayLamViec()%>">  
						  							  	  	</TD>
						  	</TR>
						  	<TR>
						  	  	<TD class="plainlabel">Ngày kết thúc</TD>
						  	  	<TD class="plainlabel">
						  		<input type="text" name="ngayketthuc" readonly="readonly" value="<%=objbean.getNgayKetThuc()%>">  
						  							  	  	</TD>
						  	</TR>		 
						      <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel">
						  	  	<textarea name="diengiai"  style="width: 50%"  rows="2"><%=objbean.getDienGiai()%></textarea>	
						  	  	</TD>
						  	</TR>
						</TABLE>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<TR class="tbheader">
									<TH width="5%">STT </TH>
									<TH width="20%">Tên nhân viên </TH>
									<TH width="10%">Chỉ tiêu </TH>
									<!-- <TH width="15%">Số đơn hàng </TH>
									<TH width="15%">Số SKU </TH> -->
										<%
									if(nhomsp!=null){
										for(int i=0;i<nhomsp.length;i++){
											if(nhomsp[i]!=null){
											%>
											<TH width="10%"><%=nhomsp[i] %></TH>
											<%
										}}
										
									}
									%>
								</TR>
							<%
																int m=0;
																				if(rs_ctddkd!=null)
																				{
																					while (rs_ctddkd.next()){
																						
															%>
									<tr>
									<TD > <input type =text name="sott" readonly="readonly" style="width :100%" value=<%=m+1 %> >  <input type ="hidden" name="maddkd" readonly="readonly" style="width :100%" value=<%=rs_ctddkd.getString("ddkd_fk")%>>  </TD>
									<TD ><input type =text name="tenddkd" style="width :100%" readonly="readonly" value="<%=rs_ctddkd.getString("ten")%>"> </TD>
									<TD> <input type =text name="chitieu" onchange="Dinhdang(this)"  style="width :100%" value="<%=formatter.format(rs_ctddkd.getDouble("chitieu"))%>" onkeypress="return keypress(event);"> </TD>
								   						   
									 <%
									if(nhomspid!=null){
										for(int i=0;i<nhomspid.length;i++){
											if(nhomspid[i]!=null){
											%>
											<TH width="10%"> <input name="<%=rs_ctddkd.getString("ddkd_fk").trim()+nhomspid[i]%>" style="width :100%"  value= <%=formatter.format( rs_ctddkd.getDouble("CT"+nhomspid[i]))%> type="text"  onchange=" Dinhdang(this)"></TH>
											<%
										}}
										
									}
									%>
  </tr>
									<%
									m++;
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
		if(rs_dvkd != null)
			rs_dvkd.close();
		if(rs_kenh != null)
			rs_kenh.close();
		if(objbean != null){
			objbean.DBclose();
			objbean = null;
		}
	
	}
	catch (SQLException e) {}

%>
<%}%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
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
NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	//Lay doi tuong objbean
 	ChiTieu objbean=(ChiTieu)session.getAttribute("obj");
	//truyen qua doi tuong list vung
 
	String check1=(String)session.getAttribute("check");
	//lay resultset vung de do vao combobox
	String loaichitieu=(String)session.getAttribute("loaichitieu");
	ResultSet rs_dvkd=objbean.getRsDVKD();
	ResultSet	rs_kenh= objbean.getRsKenh();
	
	ResultSet rsChiTieuNpp =objbean.getRsChiTieuNpp();
	ResultSet rsCTGiamSat=objbean.getrsCT_GiamSat();
	
	ResultSet rsCT_ASM=objbean.getrsCT_ASM();
	
	ResultSet ctBmRs  =objbean.getCt_BmRs();
	
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

<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
 
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
<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 $("ul.tabs").tabs("div.panes > div");
});
</script>


<SCRIPT language="JavaScript" type="text/javascript"> 
function submitform()
{
    document.forms["ChiTieuTTForm"].submit();
}

function lamtrontien_phandu(){
	
	var tongtien = document.forms["ChiTieuTTForm"].tongchitieu.value;

	var chitieu = document.getElementsByName("chitieu");
	
	var index=-1;
	
	for(var i=chitieu.length-1;i >=0; i--)
	{
		if(chitieu.item(i).value >0)
		{
			index=i;
			break;
		}
	}
	
	var tongtientruoc=0;
	
	for(var i=0; i < index; i++)
	{
		
		if(chitieu.item(i).value != "")
		{
			var thanh_tien=0;
			try{
			 thanh_tien=parseFloat(chitieu.item(i).value);
			}catch(err){}
			 tongtientruoc=parseFloat(tongtientruoc) +parseFloat(thanh_tien)
		}
	}
	
	if(index!=-1 && tongtien!=0){
		chitieu.item(index).value=parseFloat(tongtien)-parseFloat(tongtientruoc);
	}
}
 function moform(value){
	 try{
	 document.forms["ChiTieuTTForm"].ngayketthuc.value="0";
	 }catch(err){
		 
	 }
	 document.forms["ChiTieuTTForm"].songaylamviec.value="";
	 document.forms["ChiTieuTTForm"].tongchitieu.value="0";
	document.forms['ChiTieuTTForm'].loaichitieu.value=value;	
	document.forms['ChiTieuTTForm'].submit();
 } 
function checkall(value){
	var checkone=document.getElementsByName("checkkhuvuc1");
	var giatricheck=document.getElementsByName("checkkhuvuc");
	var chuoi;
	if(value==true){
		chuoi="1";
	}else{
		chuoi="0";
	}
	for(var i=0;i<checkone.length;i++ ){
		checkone.item(i).checked=value;
		giatricheck.item(i).value=chuoi;
	}
}
function recheck(){
	var checkone=document.getElementsByName("checkkhuvuc1");
	var giatricheck=document.getElementsByName("checkkhuvuc");
	for(var i=0;i<checkone.length;i++ ){
		if(checkone.item(i).checked==true){
			giatricheck.item(i).value="1";
		}else {
			giatricheck.item(i).value="0";
		}
		
			
	}
}
function keypress(e) 
{    
	var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = e.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
		{//Phím Delete và Phím Back
			return;
		}
		return false;
	}
}
function loctien(){
	var checkimport=document.forms["ChiTieuTTForm"].luutam.value;
	if(checkimport=="0"){
		//ch? th?c hi?n submit khi trong tinh trang la tu dong load chi tieu
	document.forms["ChiTieuTTForm"].tongchitieu.value="0";
	document.forms["ChiTieuTTForm"].action.value="loctien";
	document.forms["ChiTieuTTForm"].submit();
	}else{
		document.forms["ChiTieuTTForm"].submit();
	}
	
}
function lockhuvuc(){
	var checkimport=document.forms["ChiTieuTTForm"].luutam.value;
	if(checkimport=="0"){
	document.forms["ChiTieuTTForm"].tongchitieu.value="0";
	document.forms["ChiTieuTTForm"].action.value="lockhuvuc";
	document.forms["ChiTieuTTForm"].submit();
	}
}
function upload(){// nhan nut cap nhat

	   if(document.forms["ChiTieuTTForm"].filename.value==""){
		   
		   document.forms["ChiTieuTTForm"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
	   }else{
		 document.forms["ChiTieuTTForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["ChiTieuTTForm"].submit();	
	   }

}
function save(){
	  document.forms["ChiTieuTTForm"].dataerror.value=" ";
 	 var thang=document.forms["ChiTieuTTForm"].thang.value;
  	var nam=document.forms["ChiTieuTTForm"].nam.value;
  	var tongchitieu=document.forms["ChiTieuTTForm"].tongchitieu.value;
 	 var loaichitieu=document.forms["ChiTieuTTForm"].loaichitieu.value;
 	
 	
 	 
	if(nam==0){
	 document.forms["ChiTieuTTForm"].dataerror.value="Chọn năm cần đạt chỉ tiêu ";
	 return;
	}
	if(thang==0){
	 document.forms["ChiTieuTTForm"].dataerror.value="Chọn tháng cần đạt chỉ tiêu ";
	 return;
	 }
 
  //kiem tra xem thang nam dat chi tieu co hop le hay khong
 
	document.forms["ChiTieuTTForm"].action.value="save";
	document.forms["ChiTieuTTForm"].submit();
}
function kiemtra(){
	if(document.forms['ChiTieuTTForm'].luutam.value=='0')
	{return false;}
	else{
		return true;
	}
}
function checkradio(value){
	var checkone=document.getElementsByName("checkkhuvuc1");
	document.forms["ChiTieuTTForm"].luutam.value=value;
	var read;

	
		
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
	if(element.value== ''  )
	{
		element.value= '0';
	}
}





</SCRIPT>

 
    <script type="text/javascript">
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
	var rowCount = $('#sku1 tr').length;

	groupTable($('#sku1 tr:has(td)'),0,rowCount);
	$('#sku1 .deleted').remove();
	});
    </script>
 <script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="ChiTieuTTForm" method="post" action="../../ChiTieuPriNewSvl" >
<input type="hidden"  name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value='<%=objbean.getID()%>'>
<input type="hidden" name="tenform" value="0">
<input type="hidden" name="luutam" value="<%=check1%>"><!--  de luu gia tri cua radio khi cho -->
<input type="hidden" name="loaichitieu" value="<%=loaichitieu%>">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu > Khai báo > Chỉ tiêu Mua Vào&gt;Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ChiTieuPriSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
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
								<TD width="20%" class="plainlabel" >Tháng <FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel">
									<select name="thang" style="width :50px" ">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  									  if(k==objbean.getThang()){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Năm</TD>
						  	  	<TD class="plainlabel">
										<select name="nam"  style="width :50px" ">
									<option value=0> </option>  
									<%
									  Calendar c2 = Calendar.getInstance();
  										int t=c2.get(Calendar.YEAR) +6;
  										int n;
  										for(n=2008;n<t;n++){
  										if(n==objbean.getNam()){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
									</select>
						  	  	</TD>
						  </TR>
						  
				  		   <TR>
						  	  	<TD class="plainlabel">Số chỉ tiêu</TD>
						  	  <TD class="plainlabel">
						  
						  	  <input onkeypress="return keypress(event);" type="text"  name="tongchitieu" value="<%=Math.round(objbean.getChitieu())%>" > 
						  	  	
						  	  		
						  	  	</TD>
						  	</TR>
						  	<tr class="plainlabel">
                             <td>Đơn vị kinh doanh </td>
                             <td>
                             <select name=dvkdid ">
                                 <option value ="" > </option>  
                             <%
                             if (rs_dvkd!=null){
                            	 while (rs_dvkd.next()){                      				                       				
                       				 if(rs_dvkd.getString("pk_seq").equals(objbean.getDVKDId())){ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>" selected="selected"> <%=rs_dvkd.getString("ten") %></option>
                       				<%}else{ %>
                       				<option value ="<%=rs_dvkd.getString("pk_seq") %>"> <%=rs_dvkd.getString("ten") %></option>
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
								 <select name=kbhid ">
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
						  	  	<TD class="plainlabel">Số ngày làm việc</TD>
						  	  <TD class="plainlabel" >
						  
						  	  <input onkeypress="return keypress(event);" type="text"  name="songaylamviec" value="<%=objbean.getSoNgayLamViec()%>" > 
						  	  		
						  	  	</TD>
						  	</TR>
						  	<TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  <TD class="plainlabel" >
						  
						  	  <input  type="text"  name="diengiai" value="<%=objbean.getDienGiai()%>" > 
						  	  		
						  	  	</TD>
						  	</TR>
						  	
						  		<TR>
						  	  	<TD class="plainlabel">Ngày bắt đầu</TD>
						  	  <TD class="plainlabel" >
						  
						  	  <input type="text" class="days" name="ngaybatdau" value="<%=objbean.getNgayBatDau()%>" > 
						  	  		
						  	  	</TD>
						  	</TR>
						  	
						  	
						  	<TR>
						  	  	<TD class="plainlabel">Ngày kết thúc</TD>
						  	  <TD class="plainlabel" >
						  
						  	  <input type="text" class="days" name="ngayketthuc" value="<%=objbean.getNgayKetThuc()%>" > 
						  	  		
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
								<img style="top: -4px;" src="../images/button.png" alt=""> Cập nhật</a>							
						  	</td>
						  	</tr>
					 			</TABLE>
					 				<ul class="tabs">
										<li><a href="#tabnpp">Đối tượng</a></li>
										<li><a href="#tabss">Phụ trách tỉnh</a></li>
										<li><a href="#tabasm">Phụ trách vùng</a></li>
										<li><a href="#tabbm">Giám đốc miền</a></li>
									</ul>
									<div class="panes">
									<div id="tabnpp">	
					 			
					 			
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<TR class="tbheader">
									<TH width="10%">Số thứ tự </TH>
									<TH width=15%>Mã Chi nhánh / Đối tác </TH>
									<TH >Tên Chi nhánh / Đối tác</TH>
								
									<TH width="10%">Chỉ tiêu </TH>
									 
								</TR>
								<%
																int m=0;
								if(rsChiTieuNpp != null)
								{
									String khuvuc_bk="";
									
									while (rsChiTieuNpp.next()){
																					 
																				
															%>
									
									<%if(! khuvuc_bk.trim().equals(rsChiTieuNpp.getString("khuvuc").trim())){ 
										
										khuvuc_bk=rsChiTieuNpp.getString("khuvuc").trim();
										%>						
										 <tr style="color:blue ;font-weight: bold;font-size:14" >
										<TD  colspan="3" style="text-align:center;" > 
										<%=khuvuc_bk %>  </TD>
										</tr>	
															
									<% } %>						
									<tr>
									<TD > <input type ="text" name="sott" readonly="readonly" style="width :100%" value=<%=m+1 %> >   </TD>
									<TD > 
									
									<input type ="hidden" name="idnpp" style="width :100%" readonly="readonly" value="<%=rsChiTieuNpp.getString("nhapp_fk") %>" >
									<input type ="text" name="idnppsitecd" style="width :100%" readonly="readonly" value="<%=rsChiTieuNpp.getString("sitecode") %>" ></TD>
									
									
									<TD ><input type ="text" name="tennpp" style="width :100%" readonly="readonly" value="<%=rsChiTieuNpp.getString("ten")%>" ></TD>
									
									<TD > <input type ="text" name="chitieunpp" style="width :100%"   value="<%=formatter.format(rsChiTieuNpp.getDouble("chitieu"))%>" onchange=" Dinhdang(this)"> </TD>
									  
								    </tr>
									<%
									 m++;
								}
							}
							%>
						</TABLE>	
						</div>
						<div id="tabss">
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<tr>
									<TH colspan="4"  class="tbheader">CHỈ TIÊU Phụ trách tỉnh </TH>
								</tr>
								<TR class="tbheader">
									<TH width="10%">Số thứ tự </TH>
									<TH width=15%>Mã  </TH>
									<TH >Tên </TH>
								
									<TH width="10%">Chỉ tiêu </TH>
								
								</TR>
								<%
								double	tongchitieu=0;
									m=0;
									
																				if(rsCTGiamSat != null)
																				{
																					while (rsCTGiamSat.next()){
																						tongchitieu=tongchitieu+rsCTGiamSat.getDouble("chitieu");
																					
															%>
									<tr>
									<TD > <input type ="text" name="sott" readonly="readonly" style="width :100%" value=<%=m+1 %> >   </TD>
									<TD >
									
									<input type ="text" name="magsbh" style="width :100%" readonly="readonly" value="<%=rsCTGiamSat.getString("manhanvien") %>" >
									<input type ="hidden" name="idgsbh" style="width :100%" readonly="readonly" value="<%=rsCTGiamSat.getString("pk_seq") %>" >
									</TD>
									<TD ><input type ="text" name="tengsbh" style="width :100%" readonly="readonly" value="<%=rsCTGiamSat.getString("ten") %>" ></TD>
									<TD > <input type ="text" name="chitieugsbh"  style="width :100%;text-align:right "   onchange=" Dinhdang(this)"   value="<%=formatter.format(rsCTGiamSat.getDouble("chitieu"))%>" > </TD>
								    </tr>
									
									<%
									m++;
								}
							}
							%>
								<tr>
									<TH colspan="3"  class="tbheader">Tổng cộng </TH>
									<TH  class="tbheader"  align="right"><%=formatter.format(tongchitieu) %> </TH>
								</tr>
								
						</TABLE>	
					</div>
					<div id="tabasm">
					
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<tr>
									<TH colspan="4"  class="tbheader">CHỈ TIÊU Phụ trách vùng </TH>
								</tr>
								<TR class="tbheader">
									<TH width="10%">Số thứ tự </TH>
									<TH width=15%>Mã  </TH>
									<TH >Tên </TH>
									<TH width="10%">Chỉ tiêu </TH>
								
								</TR>
								<%
							tongchitieu=0;
									m=0;
							if(rsCT_ASM != null)
							{
								while (rsCT_ASM.next()){
									tongchitieu=tongchitieu+rsCT_ASM.getDouble("chitieu");
																					
															%>
									<tr>
									<TD > <input type ="text" name="sott" readonly="readonly" style="width :100%" value=<%=m+1 %> >   </TD>
									<TD > 
									<input type ="hidden" name="idasm" style="width :100%" readonly="readonly" value="<%=rsCT_ASM.getString("pk_seq") %>" >
									<input type ="text" name="maasm" style="width :100%" readonly="readonly" value="<%=rsCT_ASM.getString("manhanvien") %>" ></TD>
									<TD ><input type ="text" name="tenasm" style="width :100%" readonly="readonly" value="<%=rsCT_ASM.getString("ten") %>" ></TD>
									<TD > <input type ="text" name="chitieuasm" style="width :100%;text-align:right " onchange=" Dinhdang(this)"    value="<%=formatter.format(rsCT_ASM.getDouble("chitieu"))%>" > </TD>
								    </tr>
									
									<%
									m++;
								}
							}
							%>
								<tr>
									<TH colspan="3"  class="tbheader">Tổng cộng </TH>
									<TH  class="tbheader"  align="right"><%=formatter.format(tongchitieu) %> </TH>
								</tr>
						</TABLE>	
					</div>
					
					
					<div id="tabbm">
					
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<tr>
									<TH colspan="4"  class="tbheader">CHỈ TIÊU Giám đốc miền </TH>
								</tr>
								<TR class="tbheader">
									<TH width="10%">Số thứ tự </TH>
									<TH width=15%>Mã  </TH>
									<TH >Tên </TH>
								
									<TH width="10%">Chỉ tiêu </TH>
								
								</TR>
								<%
							tongchitieu=0;
									m=0;
							if(ctBmRs != null)
							{
								while (ctBmRs.next()){
									tongchitieu=tongchitieu+ctBmRs.getDouble("chitieu");
																					
															%>
									<tr>
									<TD > <input type ="text" name="sott" readonly="readonly" style="width :100%" value=<%=m+1 %> >   </TD>
									<TD > 
									<input type ="hidden" name="idbm" style="width :100%" readonly="readonly" value="<%=ctBmRs.getString("pk_seq") %>" >
									<input type ="text" name="mabm" style="width :100%" readonly="readonly" value="<%=ctBmRs.getString("manhanvien") %>" ></TD>
									<TD ><input type ="text" name="tenbm" style="width :100%" readonly="readonly" value="<%=ctBmRs.getString("ten") %>" ></TD>
									<TD > <input type ="text" name="chitieubm" style="width :100%;text-align:right " onchange=" Dinhdang(this)"    value="<%=formatter.format(ctBmRs.getDouble("chitieu"))%>" > </TD>
								    </tr>
									
									<%
									m++;
								}
							}
							%>
								<tr>
									<TH colspan="3"  class="tbheader">Tổng cộng </TH>
									<TH  class="tbheader"  align="right"><%=formatter.format(tongchitieu) %> </TH>
								</tr>
						</TABLE>	
					</div>
					
					
						</div>
								
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
<script type="text/javascript">

//lamtrontien_phandu();
</script>
</BODY>
</HTML>
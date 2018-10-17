<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.debit.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.debit.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.Utility" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>


<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	String action = (String)session.getAttribute("action");

	IErpCredit obj =(ErpCredit)session.getAttribute("obj");
	String id = obj.getId();
	ResultSet rsDoiTuong = obj.getRsDoiTuong();
	ResultSet rsTienTe = obj.getRsTienTe();
	List<TaiKhoan> listTaiKhoan = obj.getListTaiKhoan();
	List<TrungTamChiPhi> listTrungTamChiPhi = obj.getListTrungTamChiPhi();
	List<String> TaiKhoans = obj.getTaiKhoans();
	List<String> MoTas = obj.getMoTas();
	List<Double> NoNguyenTes = obj.getNoNguyenTes();
	List<Double> NoVNDs = obj.getNoVNDs();
	List<Integer> soLuongs = obj.getSoLuongs();
	List<Double> donGias = obj.getDonGias();
	double tigia = obj.getTigia();
	DecimalFormat formatter = new DecimalFormat("###,###,###.00");
	DecimalFormat Intformatter = new DecimalFormat("###,###,###");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}

</style>

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<!-- <script>
$(document).ready(function() {		
	$(".taikhoan").click(function(){
		alert($(this).attr('id'));
	});         
});	
</script> -->

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spList.js"></script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="JavaScript" type="text/javascript">
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
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	function checkRowData(){
		var taikhoan = document.getElementsByName("taikhoan");
		var mota = document.getElementsByName("mota");
		var ngoaite = document.getElementsByName("nonguyente");
		var noVND = document.getElementsByName("noVND");
		
		var i=0;
		
		for( i=0; i< taikhoan.length-1; i++){
			
			if( taikhoan[i].value.trim().length >0){
				var j=0; 
				for( j=i+1; j< taikhoan.length; j++){
					if(taikhoan[j].value.trim().length >0){
						if(taikhoan[j].value.trim() == taikhoan[i].value.trim()){
							alert(" có 2 tài khoản trùng nhau. Vui lòng chọn lại");
							return false;
						}
					}
				}
			}	
		}
		for(i=0; i< taikhoan.length; i++){
			if( taikhoan[i].value.trim().length >0 ){
				if(mota[i].value.trim().length <=0){
					alert("Vui lòng nhập mô tả của tài khoản thứ :"+ i);
					return false;
				}
				if(ngoaite[i].value.trim().length <=0){
					alert("Vui lòng nhập số tiền ngoại tệ(USD) của tài khoản thứ :"+ i);
					return false;
				}
				if(noVND [i].value.trim().length <=0){
					alert("Vui lòng nhập số tiền VND của tài khoản thứ :"+ i);
					return false;
				}
			}
		}
		return true;
	}
	function newform(){ 
		if(checkRowData() == false){
			return false;
		} else{
			/* document.forms['btth'].action.value= 'save'; */
			document.forms['btth'].submit();
		}
		
	}

	function submitform()
	{ 	
		document.forms['btth'].submit();
	}
	
	function changeTienTe()
	{
		document.forms['btth'].action.value= 'changeTienTe';
		document.forms['btth'].submit();
	}
	function isNumberKey2(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
           return false;
	   /* if (charCode > 31 && (charCode < 48 || charCode > 57) ){
		   return false;
	   } */
	   
	   return true;
	}
	function Dinhdang(element)
	{
	 
	 	element.value = DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	TongTien(element);
	 }
	
	function TongTien(){
		var dongia = document.getElementsByName("dongia");
		var soluong = document.getElementsByName("soluong");
		
		var ngoaite = document.getElementsByName("nonguyente");
		var noVND = document.getElementsByName("noVND");
		
		var tigia = document.getElementById("tigia");
		
		console.log("tigia SAQ:"+tigia.value);
		console.log("mảng đơn giá:"+dongia.length);
		console.log("mảng số lượng:"+soluong.length);
		var tongnguyente =0;
		var tongVND = 0;
		if(dongia != null || soluong !=null){
			var i;
			for(i=0; i<dongia.length; i++){
				if( dongia[i].value.trim().length > 0 || soluong[i].value.trim().length >0){
					// điền giá trị cho từng ô VND
					console.log("tigiaNN:"+parseFloat(tigia.value.split(",").join("")));
					console.log("đơn giá gõ vào:"+ dongia[i].value);
					console.log("đơn giá chuyển đổi:"+ dongia[i].value.split(",").join(""));
					
					console.log("số lượng gõ vào:"+ soluong[i].value);
					console.log("số lượng chuyển đổi:"+ soluong[i].value.split(",").join(""));
					
					if(dongia[i].value.trim().length == 0){
						dongia[i].value = 0;
					}
					if(soluong[i].value.trim().length == 0){
						soluong[i].value = 0;
					}
					
					/* tính ngoại tệ */
					ngoaite[i].value = parseFloat(soluong[i].value.split(",").join(""))* parseFloat(dongia[i].value.split(",").join(""));
					
					/* tính tiền VND */
					noVND[i].value = parseFloat(tigia.value.split(",").join(""))* parseFloat(ngoaite[i].value.split(",").join(""));
					// tính tổng nguyên tệ
					tongnguyente += parseFloat(ngoaite[i].value.split(",").join(""));
					// tính tổng vnd
					tongVND += parseFloat(noVND[i].value);
					noVND[i].value = DinhDangTien(noVND[i].value);
					ngoaite[i].value = Math.round((ngoaite[i].value)*100)/100;
				}	
			}
		}
		var tongtiennguyente = document.getElementById("tongtiennguyente");
		var tongtienVND = document.getElementById("tongtienVND");
		tongtiennguyente.value =  Math.round(tongnguyente *100)/100;
		tongtienVND.value = DinhDangTien(tongVND);
	}
	
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
 



</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
<form name ="btth" method="post" action="../../ErpCreditUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="<%= action%>">
<input type="hidden" name="id" value="<%= id%>">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý kế toán > Bút toán tổng hợp  > Hiển thị </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ErpCreditSvl?userId=<%= userId%>" >
								<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" 
									style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ><A href="javascript: newform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
						    <TD width="30" align="left" >
						    
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
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  
	    				style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin Credit Note </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Ngày ghi nhận </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text"  class="days" name="ngayghinhan" id="ngayghinhan" value="<%= obj.getNgayGhiNhan() %>"  > 
		                        </TD>          
		                  </TR> 
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Diễn giải </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="diengiai" id="diengiai" value=" <%= obj.getDienGiai() %>"  > 
		                        </TD>          
		                  </TR> 
		                  <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Đối tượng</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<select name="doituong" style="width: 200px;">
		                        		<option></option>
		                        		<% if( rsDoiTuong !=null){
		                        				while(rsDoiTuong.next()){
		                        					if( (rsDoiTuong.getString("MA")+"-"+rsDoiTuong.getString("loai")).equals(obj.getDoiTuong())){%>
		                        				<option value="<%= rsDoiTuong.getString("MA")+"-"+rsDoiTuong.getString("loai") %>" selected > 
		                        					 <%=rsDoiTuong.getString("loai") +"-" +rsDoiTuong.getString("TEN") %></option>
		                        				<% } else {%>
		                        				<option value="<%= rsDoiTuong.getString("MA")+"-"+rsDoiTuong.getString("loai") %>" > 
		                        				 <%= rsDoiTuong.getString("loai") +"-" +rsDoiTuong.getString("TEN") %></option>
		                        			<%}%>
		                        			
		                        		<% } rsDoiTuong.close();}%>
		                        		
		                        	</select>
		                        </TD>          
		                  </TR> 
		                   <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Tiền tệ</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<select name="tiente" style="width: 200px;" onchange="changeTienTe();">
		                        		<option></option>
		                        		<% if( rsTienTe !=null){
		                        				while(rsTienTe.next()){
		                        					if( rsTienTe.getString("PK_SEQ").equals(obj.getTienTe())){%>
		                        				<option value="<%= rsTienTe.getString("PK_SEQ")%>" selected >  <%=rsTienTe.getString("TEN") %></option>
		                        				<% } else {%>
		                        				<option value="<%= rsTienTe.getString("PK_SEQ") %>" >  <%=rsTienTe.getString("TEN") %></option>
		                        			<%}%>
		                        			
		                        		<% } rsTienTe.close();}%>
		                        		
		                        	</select>
		                        </TD>          
		                  </TR> 
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" > Tỉ giá theo nguyên tệ</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="tigia" id="tigia" value="<%= tigia %>" 
		                        	onchange="TongTien(this);" > 
		                        </TD>          
		                  </TR> 
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Tổng tiền nguyên tệ</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="tongtiennguyente" id="tongtiennguyente" readonly value="<%= formatter.format(obj.getTongTienNguyenTe()) %>"  > 
		                        </TD>          
		                  </TR> 
		                  <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Tổng tiền VND</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="tongtienVND" id="tongtienVND"  readonly value="<%= formatter.format(obj.getTongTienVND()) %>"  > 
		                        </TD>          
		                  </TR> 
						</TABLE>
						
						<table cellpadding="0px" cellspacing="1px" width="100%">
							<tr class="tbheader">
								<th align="center" width ="3%">Stt</th>
								<th align="center" width = "17%">Tài khoản</th>
								<th align="center" width = "30%">Diễn giải</th>
								<th align="center" width = "10%">Đơn giá</th>
								<th align="center" width = "10%">Số lượng</th>
								<th align="center" width = "15%">Thành tiền nguyên tệ</th>
								<th align="center" width = "15%">Thành tiền VND </th>
							</tr>
							<!-- phần update -->
							<% for(int i=0; i< TaiKhoans.size(); i++){ %>
							 <% if (i % 2 != 0) { %>                     
                                            <TR class="tblightrow" >
                                        <%} else {%>
                                            <TR class= "tbdarkrow" >
                                        <%}%>
                                        
                                <td align="center">
									<input  type="text" value= "<%=(i+1) %>" readonly style="width: 50px;" />
								</td>
								
								<td align="center">
									<select name="taikhoan"  class="taikhoan" style="width: 200px;" >
										<option value = ""> </option>
									<% for(int j=0; j< listTaiKhoan.size(); j++ ){ %>
									
										<% if(TaiKhoans.get(i).equals(listTaiKhoan.get(j).getTaiKhoanId())){ %>
											<option value="<%= listTaiKhoan.get(j).getTaiKhoanId() %>" selected> 
										<%= listTaiKhoan.get(j).getDienTa()%></option>
										<%} else{ %>
											<option value="<%= listTaiKhoan.get(j).getTaiKhoanId() %>"> 
										<%= listTaiKhoan.get(j).getDienTa()%></option>
										<%} %>
										
									<%} %>
									</select>
								</td>
								<td align="center">
									<textarea rows="1" cols="9" name = "mota" > <%= MoTas.get(i)  %></textarea>
								</td>
								<td align="center">
									<input type="text" name="dongia"   style="width: 100px;" 
									 onkeypress="return isNumberKey2(event)" onchange="TongTien(this);" 
										value="<%= formatter.format( donGias.get(i))%>"/>
								</td>
								<td align="center">
									<input type="text" name="soluong" style="width: 100px;" 
									 onkeypress="return isNumberKey2(event)" onchange="TongTien(this);" 
										value="<%= Intformatter.format( soLuongs.get(i))%>"/>
								</td>
								<td align="center">
									<input type="text" name="nonguyente" readonly style="width: 100px;"  value="<%= formatter.format( NoNguyenTes.get(i))%>"/>
								</td>
								<td align="center">
									<input type="text" name="noVND"  readonly style="width: 100px;" value="<%= formatter.format(NoVNDs.get(i))%>"/>
								</td>
							</tr>
							<%} %>
							
							<!-- phần tạo mới -->
							
							<% for(int i=MoTas.size(); i< 12; i++){ %>
							 <% if (i % 2 != 0) { %>                     
                                            <TR class="tblightrow" >
                                        <%} else {%>
                                            <TR class= "tbdarkrow" >
                                        <%}%>
                                <td align="center">
									<input  type="text" value= "<%=(i+1) %>" readonly style="width: 50px;"/>
								</td>
								<td align="center">
									<select name="taikhoan" class="taikhoan" style="width: 200px;">
										<option value=""> </option>
									<% for(int j=0; j< listTaiKhoan.size(); j++ ){ %>
										<option value="<%= listTaiKhoan.get(j).getTaiKhoanId() %>"> 
										<%= listTaiKhoan.get(j).getDienTa()%></option>
									<%} %>
									</select>
								</td>
								<td align="center">
									<textarea rows="1" cols="9" name = "mota" ></textarea>
								</td>
								<td align="center">
									<input type="text" name="dongia"  style="width: 100px;" value=""
									 onkeypress="return isNumberKey2(event)" onchange="TongTien(this);" />
								</td>
								<td align="center">
									<input type="text" name="soluong"   style="width: 100px;" value=""
									 onkeypress="return isNumberKey2(event)" onchange="TongTien(this);" />
								</td>
								<td align="center">
									<input type="text" name="nonguyente" style="width: 100px;" readonly/>
								</td>
								<td align="center">
									<input type="text" name="noVND"  readonly style="width: 100px;"/>
								</td>
							</tr>
							<%} %>
							 <TR class="tbfooter" >
									<TD align="center" colspan="15"> &nbsp; </TD>
							 </TR>
						</table>
					</FIELDSET>						
				</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>

<script>
TongTien();
</script>
</BODY>
</HTML>
<% 
	obj.DbClose();
}%>

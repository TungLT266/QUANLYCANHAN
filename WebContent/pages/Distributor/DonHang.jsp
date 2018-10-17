<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="geso.traphaco.distributor.util.Utility"%>
<%@ page  import = "geso.traphaco.distributor.beans.donhang.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.sql.ResultSet" %>
<% 
NumberFormat formatter = new DecimalFormat("#,###,###");
	HttpSession s = request.getSession();
   if (s.isNew()){
	   s.invalidate();
	   System.out.println("New session");
   }else{
	   
	   System.out.println("Old session");
   }
%>

<% IDonhangList obj = (IDonhangList)s.getAttribute("obj"); %>
<% ResultSet dhlist = (ResultSet)obj.getDonhangRs(); %>
<% ResultSet ddkd = (ResultSet)obj.getDaidienkd(); %>

<% ResultSet ttRs = (ResultSet)obj.getTtRs(); %>
<% ResultSet qhRs = (ResultSet)obj.getQhRs(); %>
<% ResultSet tructhuocRs = (ResultSet)obj.getNppTructhuocRs(); %>

<% String userId = (String) s.getAttribute("userId");  %>
<% String userTen = (String) s.getAttribute("userTen");  %>
<% Utility Util = new Utility(); %>
<%
	int[] quyen = new  int[11];
	quyen = Util.GetquyenNew("DonhangSvl","",userId);

%>
<% obj.setNextSplittings(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
	
<script type="text/javascript">
	function open_popup_window(url){
		
		window.open(url, "_blank", "toolbar=yes, scrollbars=yes, resizable=yes,  width=800, height=400");
		}
	</script>

<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<style type="text/css">
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
		/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
		filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		font-size: 12px;
	}	
	#dhtmlpointer
	{
		position:absolute;
		left: -300px;
		z-index: 101;
		visibility: hidden;
		font-size: 12px;
	}
</style>	
	
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
			$(".select2").select2();
		});
</script>	

<SCRIPT language="javascript" type="text/javascript">

 function submitform()
 {
 	document.forms['dhForm'].action.value= 'search';
 	document.forms['dhForm'].submit();
 }
 function newform()
 {
 	document.forms['dhForm'].action.value= 'new';
 	document.forms['dhForm'].submit();
 }
 function clearform()
 {   
	document.forms['dhForm'].tungay.value= '';
	document.forms['dhForm'].denngay.value= '';
	document.forms['dhForm'].trangthai.value ='';
	document.forms['dhForm'].khachhang.value ='';
	document.forms['dhForm'].sodonhang.value ='';
	document.forms['dhForm'].mafast.value ='';
	document.forms['dhForm'].ddkdTen.selectedIndex = 0; 
	submitform();
 }
 
 function Next()
 {
 	document.forms['dhForm'].action.value= 'next';
 	document.forms['dhForm'].submit();
 }

 function Prev()
 {
 	document.forms['dhForm'].action.value= 'prev';
 	document.forms['dhForm'].submit();
 }

 function XemTrang(page)
 {
 	document.forms['dhForm'].action.value= 'view';
 	document.forms['dhForm'].currentPage.value = page;
 	document.forms['dhForm'].submit();
 }
 function processing(id,chuoi){
	 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";
	 document.getElementById(id).href = chuoi;
	  
}
 function thongbao()
	{
		if(document.getElementById("msg").value != '')
			alert(document.getElementById("msg").value);
	}
 function XacNhanXoa(dhId)
 {
	 var r = confirm("Bạn chắc chắn muốn xóa đơn hàng ( " + dhId + " ) ");
	 if (r == false) {
	     return;
	 }
	 
	 //alert('Ly do xoa: ' + document.getElementById("lydoxoa" + dhId).value);
	 document.forms['dhForm'].lydoxoa.value = document.getElementById("lydoxoa" + dhId).value;
	 document.forms['dhForm'].dhId.value = dhId;
	 document.forms['dhForm'].action.value = 'delete';
	 document.forms['dhForm'].submit();
 }
 
 function duyetform(Id)
 {
	 if(!confirm('Bạn có muốn duyệt đơn hàng này?')) 
	 {
		 return false ;
	 }
	 
	 document.forms['dhForm'].dhId.value = Id;
 	 document.forms['dhForm'].action.value= 'duyet';
 	 document.forms['dhForm'].submit();
 }
 
 
 function xuatExcel()
 {
 	document.forms['dhForm'].action.value= 'toExcel';
 	document.forms['dhForm'].submit();
 }
 
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dhForm" method="post" action="../../DonhangSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="dhId" id="dhId"  >
<input type="hidden" name="action" value="1">
<input type="hidden" name="lydoxoa" value="">

<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">
	<% obj.setNextSplittings(); %>
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
<script type="text/javascript">
	thongbao();
</script>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD align="left" class="tbnavigation">
					   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">Quản lý bán hàng > NPP bán hàng > Đơn hàng bán

							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= userTen %>&nbsp;</tr>
						</TABLE>
					</TD>
		  </TR>
			<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								
								<TR>
										<TD width="120px" class="plainlabel">Tỉnh thành</TD>
										<TD class="plainlabel" width="250px" >
										<SELECT name="ttId" onChange = "submitform();" class="select2" style="width: 200px">
											  <option value=""> </option>
											  <% if(ttRs != null){
											  	try{ while(ttRs.next()){ 
									    			if(ttRs.getString("pk_Seq").equals(obj.getTtId())){ %>
									      				<option value='<%=ttRs.getString("pk_Seq")%>' selected><%=ttRs.getString("ten") %></option>
									      			<%}else{ %>
									     				<option value='<%=ttRs.getString("pk_Seq")%>'><%=ttRs.getString("ten") %></option>
									     	<%}} ttRs.close(); }catch(Exception e){ e.printStackTrace(); } }%>	 
	                                    	</SELECT>
	                                    </TD>
	                                    	 <TD width="130px" class="plainlabel">Quận huyện</TD>
											<TD class="plainlabel">
												<SELECT name="qhId" onChange = "submitform();" style="width: 200px" class="select2">
												  <option value=""> </option>
												  <% if(qhRs != null){
												  	try{ while(qhRs.next()){ 
										    			if(qhRs.getString("pk_Seq").equals(obj.getQhId())){ %>
										      				<option value='<%=qhRs.getString("pk_Seq")%>' selected><%=qhRs.getString("ten") %></option>
										      			<%}else{ %>
										     				<option value='<%=qhRs.getString("pk_Seq")%>'><%=qhRs.getString("ten") %></option>
										     	<%}} qhRs.close(); }catch(Exception e){ e.printStackTrace(); } }%>	 
		                                    	</SELECT>
		                                    	</TD>
		                                    	<td class="plainlabel" colspan="4"></td>
                                    </TR>

								<TR>
									<TD width="120px" class="plainlabel">Trình dược viên </TD>
									<TD class="plainlabel" width="250px" >
									<SELECT name="ddkdTen" onChange = "submitform();" style="width: 200px" class="select2" > 
										  <option value=""> </option>
										  <% if(ddkd != null){
										  	try{ while(ddkd.next()){ 
								    			if(ddkd.getString("ddkdId").equals(obj.getDdkdId())){ %>
								      				<option value='<%=ddkd.getString("ddkdId")%>' selected><%=ddkd.getString("ddkdTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=ddkd.getString("ddkdId")%>'><%=ddkd.getString("ddkdTen") %></option>
								     	<%}} ddkd.close(); }catch(Exception e){ e.printStackTrace(); } }%>	 
                                    </SELECT></TD>
                                    <TD width="130px" class="plainlabel">Trạng thái </TD>
									<TD class="plainlabel">
										<select name="trangthai" onChange="submitform();" style="width: 200px" class="select2" >
											<% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected>Đã chốt</option>
											  	<option value="0">Chưa chốt</option>
											  	<option value="2">Đã hủy</option>
											  	<option value="3">Đã xuất kho</option>
											  	<option value="4">Đã xuất HĐ</option>
											  	<option value="7">Đã in ĐH</option>
											  	<option value=""></option>
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											 	<option value="0" selected>Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>
											 	<option value="2" >Đã hủy</option>
											 	<option value="3" >Đã xuất kho</option>
											 	<option value="4">Đã xuất HĐ</option>
											 	<option value="7">Đã in ĐH</option>
											  	<option value="" ></option>
											  	
											<%}else if(obj.getTrangthai().equals("2")){%>											
											 	<option value="2" selected>Đã hủy</option>										  	
											  	<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>
											  	<option value="3" >Đã xuất kho</option>
											  	<option value="4">Đã xuất HĐ</option>
											  	<option value="7">Đã in ĐH</option>
											  	<option value="" ></option>
											  	
											<%}else if(obj.getTrangthai().equals("3")){%>											
											 	<option value="3" selected>Đã xuất kho</option>										  	
											  	<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>
											  	<option value="2" >Đã hủy</option>
											  	<option value="4">Đã xuất HĐ</option>
											  	<option value="7">Đã in ĐH</option>
											  	<option value="" ></option>
											  
											 <%}else if(obj.getTrangthai().equals("4")){%>											
											 	<option value="4"  selected>Đã xuất HĐ</option>										 											  	
											  	<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>
											  	<option value="2" >Đã hủy</option>
											  	<option value="3" >Đã xuất kho</option>
											  	<option value="7">Đã in ĐH</option>
											  	<option value="" ></option>	  	
											 <%}else if(obj.getTrangthai().equals("7")){%>											
											 	<option value="4" >Đã xuất HĐ</option>										 											  	
											  	<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>
											  	<option value="2" >Đã hủy</option>
											  	<option value="3" >Đã xuất kho</option>
											  	<option value="7"  selected>Đã in ĐH</option>
											  	<option value="" ></option>	  	
											<%}else{%>
												<option value="" selected> </option>
												<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>											
											  	<option value="2" >Đã hủy</option>
											  	<option value="3" >Đã xuất kho</option>
											  	<option value="4">Đã xuất HĐ</option>
											  	<option value="7">Đã in ĐH</option>
											<%} %>
									          </select>
									</TD>
									<td class="plainlabel" colspan="4"></td>
									
							    </TR>	
								<TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly />
			                    	</td>
			                    	  <TD class="plainlabel" >Đến ngày</TD>
							      <td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly />
			                    	</td>
			                    	<td class="plainlabel" colspan="4"></td>
								</TR>
							  	<TR>
									<TD class="plainlabel" >Số đơn hàng</TD>
									<TD class="plainlabel">
										<input type="text" name="sodonhang" size="11" value="<%= obj.getSohoadon() %>" onChange = "submitform();">
									</TD>	
											<TD class="plainlabel" >Mã / tên khách hàng</TD>
									<TD class="plainlabel">
										<input type="text" name="khachhang" size="11" value="<%= obj.getKhachhang() %>" onChange = "submitform();">
									</TD>
									<td class="plainlabel" colspan="4"></td>
								</TR>

								<TR>
								
									<TD class="plainlabel" >Nhà phân phối</TD>
									<TD class="plainlabel">
										<SELECT name="tructhuocId" onChange = "submitform();" style="width: 200px" class="select2" >
										  <option value=""> </option>
											  <% if(tructhuocRs != null){
											  	try{ while(tructhuocRs.next()){ 
									    			if(tructhuocRs.getString("pk_seq").equals(obj.getNppTructhuocIds())){ %>
									      				<option value='<%=tructhuocRs.getString("pk_seq")%>' selected><%=tructhuocRs.getString("Ten") %></option>
									      			<%}else{ %>
									     				<option value='<%=tructhuocRs.getString("pk_seq")%>'><%=tructhuocRs.getString("Ten") %></option>
									     	<%}} tructhuocRs.close(); }catch(Exception e){ e.printStackTrace(); } }%>	 
	                                    </SELECT>
									</TD>
									
									<TD class="plainlabel" >Mã cũ</TD>
									<TD class="plainlabel">
										<input type="text" name="mafast" size="11" value="<%= obj.getMafast() %>" onChange = "submitform();">
									</TD>
									<td class="plainlabel" colspan="2"></td>
								</TR>
								
								<%-- <%if(obj.getIsSearch()){ %>
								<TR><TD class="plainlabel" colspan="6"></TD>
								<TR>
									<TD class="plainlabel" >Doanh số</TD>
									<td class="plainlabel"><input type="text" name="ds" size="6" value="<%= formatter.format(obj.getTongTruoc()) %>"></td>
									<TD class="plainlabel" >Chiết khấu</TD>
									<td class="plainlabel"><input type="text" name="ck" size="6" value="<%= formatter.format(obj.getTongCK()) %>"></td>
									<TD class="plainlabel" >Doanh thu</TD>
									<td class="plainlabel"><input type="text" name="dt" size="6" value="<%= formatter.format(obj.getTongSau()) %>"></td>
							
								</TR>
								<%} %> --%>
								
								<TR>
									<TD class="plainlabel" colspan="6">
                                   <a class="button2" href="javascript:submitform()" >
    	                           <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm  </a> &nbsp;&nbsp;&nbsp;                         
									<a class="button2" href="javascript:clearform()">
	                               <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
				
									<% if( quyen[ Utility.XUATEXCEL ] != 0 ) { %>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
									<% } %>
									
									  <!--  <INPUT name="action" type="submit" value="Tim kiem">&nbsp;
                                       <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();">
                                       -->
                                       </TD>
								</TR>
							</TABLE>
				  </FIELDSET>
			   </TD>	
				</TR>
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Đơn hàng bán &nbsp;&nbsp;&nbsp; 

					<%if(quyen[Utility.THEM]!=0){ %>
						<a class="button3"  onclick="newform()">
	    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
	    				<%} %>                            

					<!--<INPUT name="action" type="submit" value="Tao moi"> -->	
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1px" cellpadding="4px">
								
								<TR class="tbheader">
									<th width="5%" align="center">Mã ĐH</th>
									<th width="10%" align="center">Nhà phân phối</th>
									<th width="9%" align="center"> Mã cũ</th>
									<th width="10%" align="center">Khách hàng</th>
									<th width="8%" align="center"> Trạng thái</th>
									<th width="9%" align="center">Ngày đơn hàng</th>
									<!-- <th width="1%" align="center" style="display: none">Ngày tạo</th>
									<th width="1%" align="center" style="display: none">Người tạo</th> -->
									<th width="7%" align="center" >Ngày sửa</th>
									<th width="7%" align="center" >Người sửa </th>
									<th width="10%" align="center">Trình dược viên</th>
									<th width="9%" align="center">Tổng thanh toán</th>
									<th width="10%" align="center">Tác vụ</th>
								</TR>
								
								<% 
								if(dhlist != null)
								{
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (dhlist.next()){	
										
										String chuoiFORMAT = "";
										
										String msg= dhlist.getString("tooltip")==null?"":dhlist.getString("tooltip");
										
										//MÀU SẮC THEO CẤP ĐỘ GIAO HÀNG
										int CAPDOGIAOHANG = dhlist.getInt("CAPDOGIAOHANG");
		                 				
		                 				if(CAPDOGIAOHANG > 0 && CAPDOGIAOHANG <= 4)
											chuoiFORMAT = " style='color: red; '  ";
										else if(CAPDOGIAOHANG > 4 && CAPDOGIAOHANG <= 8)
											chuoiFORMAT = " style='color: yellow; font-weight: bold; '  ";
										else if(CAPDOGIAOHANG > 8 && CAPDOGIAOHANG <= 24)
											chuoiFORMAT = " style='color: blue;'  ";
										
										if (m % 2 != 0) {%>	
														
											<TR class= <%=lightrow%> <%= chuoiFORMAT %> >
									<%} else {%>
											
											<TR class= <%= darkrow%> <%= chuoiFORMAT %> > 
										<%}%>
											 	<TD align="center" onMouseover='ddrivetip("<%= msg %>" , 700)' onMouseout="hideddrivetip()" >
											 		<input name="dhIdd" type="hidden"  value="<%= dhlist.getString("dhId") %>"  />
													<%= dhlist.getString("machungtu") %>
												</TD> 
												<TD align="center">	<%= dhlist.getString("nppTen") %> </TD>   
												<TD align="center">	<%= dhlist.getString("maFast") %> </TD>       
												<TD align="left">
													<%= dhlist.getString("khTen") %>
												</TD>
												<TD align="center">
												<%
												double tonggiatri = dhlist.getDouble("tonggiatri");
												String trangthai = dhlist.getString("trangthai");
												String daxuathd= dhlist.getString("DAXUATHOADON");
												String tt_bosung = "";
												String dain_dh = dhlist.getString("DAINDH");												
																								
												if(dain_dh.equals("1"))
													tt_bosung = "  (Đã in ĐH)";
												
												if( dhlist.getInt("SS_DUYET") != 0 && dhlist.getInt("CS_DUYET") == 0 )
													tt_bosung = " (SS)";
												else if( dhlist.getInt("CS_DUYET") != 0 )
													tt_bosung = " (CS)";
													
												boolean exitPXK = dhlist.getInt("exitPXK") > 0 ? true : false;
												
												if( dhlist.getInt("SS_DUYET") != 0 ) { %>
													<span> Đã duyệt <%= tt_bosung %></span>
												<% } else {
													if (trangthai.equals("0")){ %>
														<span> Chưa chốt <%= tt_bosung %></span>
													<%}else if(trangthai.equals("1")&& daxuathd.equals("0")){ %>
														<span><b> Đã chốt <%= tt_bosung %> </b></span>
													<%}else if(trangthai.equals("2")){ %>
														<span><u> Đã hủy <%= tt_bosung %> </u></span>
													<%}else if(trangthai.equals("6")){ %>
														<span><u> Thiếu hàng km <%= tt_bosung %> </u></span>
													<%}else if(daxuathd.equals("1") && (trangthai.equals("1") || trangthai.equals("3") )){ %>
														<span style="color: DarkBlue;"><b> Đã xuất HĐ <%= tt_bosung %> </b></span>
													<%} else{ %>
														<span><i style="color:red"> Đã xuất kho <%= tt_bosung %> </i></span>
												<% } } %>
												</TD>
												<TD align="center"><%= dhlist.getString("ngaynhap") %></TD>
												<TD align="center" ><%= dhlist.getString("ngaysua") %></TD>
												<TD align="left" ><%= dhlist.getString("nguoisua") %></TD>
												<TD align="center"><%= dhlist.getString("ddkdTen") %></TD>
												<TD align="right" ><%= formatter.format(tonggiatri) %></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0" >
													<TR><TD>
													<%if(trangthai.equals("0")){ %>
														
														<%if(quyen[Utility.XEM]!=0){ %>
															<A href = "../../DonhangUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
														<%} %>
														
														<%if(quyen[Utility.SUA]!=0){ %>
															<A href = "../../DonhangUpdateSvl?userId=<%=userId%>&update=<%= dhlist.getString("dhId") %>
															&tdvId=<%=obj.getDdkdId() %>&trangthai=<%=obj.getTrangthai()%>&tungay=<%=obj.getTungay()%>&denngay=<%=obj.getDenngay()%>
															&sodonhang=<%=obj.getSohoadon()%>&khachhang=<%=obj.getKhachhang()%>&mafast=<%=obj.getMafast()%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
														<%} %>
														
														 <%if(quyen[Utility.CHOT]!=0){ %>
																<%-- <A href="javascript:duyetform(<%= dhlist.getString("dhId") %>);" >
																	<img  src="../images/Chot.png" alt="Duyệt đơn hàng" width="20" height="20"  border='0' title="Duyệt đơn hàng"	 >
																</A>&nbsp; --%>
														 <%} %>
															 	
														<%-- <%if(quyen[Utility.THEM]!=0){ %>
														<A id='<%="copydhid" + dhlist.getString("dhId") %>' href="">
															<img  src="../images/copy20.png" alt="Copy đơn hàng" width="20" height="20"  border=0	onclick="if(!confirm('Bạn có muốn copy đơn hàng ( <%= dhlist.getString("dhId") %> ) này?')) {return false ;}else{ processing('<%="copydhid" + dhlist.getString("dhId") %>' , '../../DonhangUpdateSvl?userId=<%=userId%>&copy=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
														</A>&nbsp;
														<%} %>  --%>
														
														<%if(quyen[Utility.XOA]!=0){ %>
															<A id='<%= dhlist.getString("dhId") %>' href="" rel="subcontent<%="xoadhid" + dhlist.getString("dhId") %>" >
															<img  src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng" border='0'	 >
															</A> 
														<%} %>
														
														<DIV id="subcontent<%="xoadhid" + dhlist.getString("dhId") %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
																		                             background-color: #FFF; width: 400px; padding: 4px; z-index: 100000009; " >
										                    <table width="98%" align="center" cellspacing="1" >
										                        <tr  >
										                            <td align="center" style="font-size: 10pt">Lý do xóa</td>
										                        </tr>
										                        <tr >
										                            <td align="center" style="font-size: 10pt">
										                            	<input type="text" id="<%="lydoxoa" + dhlist.getString("dhId") %>" style="width: 100%" />
										                            </td>
										                        </tr>
										                        
										                    </table>
										        					                    
										                    <div align="right">
										                    
										                     	<a href="javascript:XacNhanXoa('<%= dhlist.getString("dhId") %>');" style="color: red; font-weight: bold;">Xác nhận xóa</a>
										                     
										                     	&nbsp;&nbsp;|&nbsp;&nbsp;
										                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%="xoadhid" + dhlist.getString("dhId") %>')" style="font-weight: bold;" >Đóng lại</a>
										                    </div>
											            </DIV> 
											            <script type="text/javascript">
															dropdowncontent.init('<%= dhlist.getString("dhId") %>', "left-top", 300, "click");
														</script>
														
													<%}else { if( trangthai.equals("3")) {%>
														
														<%if(quyen[Utility.SUA]!=0){ %>
															<A href = "../../DonhangUpdateSvl?userId=<%=userId%>&update=<%= dhlist.getString("dhId") %>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
														<%} %>
														
													<% } else{ if(!trangthai.equals("2")) { %>
															<%if(dhlist.getString("sohoadon")!=null&&dhlist.getString("sohoadon").length()>0){
															String [] a=dhlist.getString("sohoadon").split(" ");
															String url="";
															if(a[2].equals("0"))
															url="../../HoadontaichinhUpdateSvl?userId="+userId+"&display="+a[1];
															else
																url="../../HoadontaichinhKMUpdateSvl?userId="+userId+"&display="+a[1];
															
															%>
																<img   src="../images/vitriluu.png" alt="Xem hóa đơn" width="20" height="20"  border=0	onclick="open_popup_window('<%=url%>')" style="cursor:pointer">
														<%} %>
														<%if(quyen[Utility.XEM]!=0){ %>
															<A href = "../../DonhangUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>&nbsp;
														<%} %>
														
													<% } else { %> 
														<%if(quyen[Utility.XEM]!=0){ %>
															<A href = "../../DonhangUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
														<%} %>
													<% } } }%>
													</TD></TR>
												</TABLE>
												</TD>
											</TR>
									<%m++; } dhlist.close(); }%>	
										 <tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('dhForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="Prev('dhForm', 'prev')"> &nbsp; <%}else{ %>
															<img alt="Trang Truoc" src="../images/prev_d.gif">
															&nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 							
											
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('dhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="Next('dhForm', 'next')"> &nbsp; <%}else{ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif">
															&nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('dhForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>				
						
							</TABLE>
							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<!--end body Dossier--></TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%
	try
	{
		if(!(ddkd == null))
			ddkd.close();
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		if(dhlist!=null){
			dhlist.close();
		}
		Util=null;
		s.setAttribute("obj",null);
		
	}catch(Exception e){e.printStackTrace();}
%>
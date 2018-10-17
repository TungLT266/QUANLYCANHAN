<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhaphanphoi.INhaphanphoi" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% INhaphanphoi nppBean = (INhaphanphoi)session.getAttribute("nppBean"); %>
<% ResultSet tp = (ResultSet)nppBean.getTp() ; %>
<% ResultSet qh = (ResultSet)nppBean.getQh() ; %>
<% ResultSet kv = (ResultSet)nppBean.getKhuvuc(); %>
<% ResultSet vung = (ResultSet)nppBean.getVung(); %>
<% ResultSet rs_khott = (ResultSet)nppBean.getrs_khott(); %>
<% ResultSet tructhuocRs = (ResultSet)nppBean.getTructhuoc(); %>
<% ResultSet rs_gsbh = (ResultSet)nppBean.getrs_gsbh(); %>
<% ResultSet rs_nvbh = (ResultSet)nppBean.getrs_nvbh(); %>
<% ResultSet gsbh_kbh = (ResultSet)nppBean.getGsbh_KbhList();  %>
<% ResultSet htbh = (ResultSet)nppBean.getHethongbh(); %>
<% ResultSet nhomkenh = (ResultSet)nppBean.getNhomkenh();  %>
<% ResultSet kenhbh = (ResultSet)nppBean.getKenhList();  %>
<% String gsbh_kbhSelected = (String)nppBean.getGsbh_KbhIdSelected();%>
<% ResultSet diabanrs = (ResultSet)nppBean.getDiabanList();  %>
<% ResultSet qg = (ResultSet)nppBean.getQuocgiaList();  %>
<% ResultSet Nhomnpp = (ResultSet)nppBean.getNhomNppList();  %>
<% ResultSet rs_loaicn = (ResultSet)nppBean.getLoaiCNRs();
   ResultSet kmcpRs = (ResultSet)nppBean.getKhoanmuccpRs();
	NumberFormat formatter=new DecimalFormat("#,###,###"); 
%>

<% ResultSet nganhangRs = (ResultSet)nppBean.getNganhangRs();%>
<% ResultSet chinhanhRs = (ResultSet)nppBean.getChinhanhRs();%>
<% ResultSet taikhoanRs = (ResultSet)nppBean.getTaikhoanRs();%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script>
	$(function() {
	 	$("ul.tabs").tabs("div.panes > div");
	});
	</script>

	<script>
	$(document).ready(function() {

	    //When page loads...
	    $(".tab_content").hide(); //Hide all content
	    var index = $("ul.tabs li.current").show().index(); 
	    $(".tab_content").eq(index).show();
	    //On Click Event
	    $("ul.tabs li").click(function() {
	  
	        $("ul.tabs li").removeClass("current"); //Remove any "active" class
	        $(this).addClass("current"); //Add "active" class to selected tab
	        $(".tab_content").hide(); //Hide all tab content  
	        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
	        $(activeTab).show(); //Fade in the active ID content
	        return false;
	    });

	});
	</script>
<SCRIPT language="javascript" type="text/javascript">

 	function DinhDang()
	{
		var sotien = document.getElementById("hanmucno").value.replace(/,/g,"");
		document.getElementById("hanmucno").value= DinhDangTien(sotien);
		
		var songayno = document.getElementById("songayno").value.replace(/,/g,"");
		document.getElementById("songayno").value= DinhDangTien(songayno);
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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>

<style type="text/css">
	input
	{
		padding: 2px 0px;
	}
</style>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nppForm" method="post" action="../../NhaphanphoiUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="isKH" id="isKH" value='<%= nppBean.getIsKhachhang() %>'>
<input type="hidden" name="id"  value='<%= nppBean.getId() %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">
							 	<% if(nppBean.getLoaiNPP().equals("0")) { %>
						   			&nbsp;Dữ liệu nền > Kinh doanh > Công ty > Hiển thị
						   		<% } else { %>
						   			&nbsp;Dữ liệu nền > Cơ bản > Nhà phân phối > Hiển thị
						   		<% } %>
							 </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
						  </tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
					<TR class = "tbdarkrow">
						<TD width="30" align="center"><A href="../../NhaphanphoiSvl?userId=<%=userId %>&loaiNPP=<%= nppBean.getLoaiNPP() %>&isKH=<%= nppBean.getIsKhachhang() %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD align="left" ></TD>
					</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
				  	<tr>
							<TD align="left" colspan="6" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
			    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width:100%" rows="1" ><%= nppBean.getMessage() %></textarea>
                                        <%nppBean.setMessage(""); %>
								</FIELDSET>
						   </TD>
					</tr>

				  <TR>
				  <TD height="150%" width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle" style="color:black"> 
							<% if(nppBean.getLoaiNPP().equals("0")) { %>
					   			Thông tin công ty 
					   		<% } else { %>
					   			Thông tin nhà phân phối 
					   		<% } %>
						</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD class="plainlabel" width="140px" > Tên <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel"  >
                                	<INPUT name="nppTen" id="nppTen" type="text" value="<%= nppBean.getTen() %>" >
                                </TD>
								<TD class="plainlabel" width="130px"  >Mã cũ</TD>
								<TD class="plainlabel" width="220px" >
									<INPUT name="maSAP" id="maSAP" type="text" value="<%= nppBean.getMaSAP() %>" >
								</TD>
								<TD class="plainlabel" width="140px" >Tên chủ tài khoản </TD>
                                <TD class="plainlabel" >
                                	<INPUT name="tenchutk" id="tenchutk" type="text" value="<%= nppBean.getTenChuTK() %>" >
                                </TD>
							</TR>
							<TR>
								<TD class="plainlabel">
								<% if(nppBean.getLoaiNPP().equals("0")) { %>
									Tên tiếng anh:	
								<% } else { %>
									Tên ký hợp đồng:
								<% } %>
								</TD>
								<TD class="plainlabel"  >
									<INPUT type="text" name="tenkyhd" id="tenkyhd" value="<%= nppBean.getTenKyHd() %>" size="40">
								</TD>	
								<TD class="plainlabel">Điện thoại </TD>
                                <TD  class="plainlabel">
                                	<INPUT name="DienThoai" id="DienThoai" type="text" value="<%= nppBean.getSodienthoai() %>" >
                                </TD>
                                <TD class="plainlabel">Số tài khoản</TD>
								<TD class="plainlabel"><INPUT type="text" name="sotaikhoan" id="sotaikhoan" value="<%= nppBean.getSoTK() %>"></TD>
							</TR>
							<TR>
								<TD class="plainlabel" width="140px" >
									<% if(nppBean.getLoaiNPP().equals("0")) { %>
										Giám đốc <FONT class="erroralert"> *</FONT>
									<% } else { %>
										Tên chủ NPP <FONT class="erroralert"> *</FONT>	
									<% } %>
									
								</TD>
                                <TD class="plainlabel" >
                                	<INPUT name="tenchunpp" id="tenchunpp" type="text" value="<%= nppBean.getTenChuNPP() %>" >
                                </TD>
                                
								<td class="plainlabel">FAX</td>
						  		<TD class="plainlabel" colspan="3" ><input name="fax" id="fax" type="text" value="<%= nppBean.getFAX() %>"></TD>
						  		
						  		<%-- <TD class="plainlabel">Tên ngân hàng</TD>
								<TD class="plainlabel"><INPUT name="nganhang" id="nganhang" value="<%= nppBean.getNganHang() %>" type="text"></TD>	 --%>
							</TR>
							    
							<TR >
								<TD class="plainlabel" width="140px" >Tên người liên hệ <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel" >
                                	<INPUT name="tenchunpp" id="tennguoilh" type="text" value="<%= nppBean.getXuattaikho() %>" >
                                </TD>
                                <TD class="plainlabel">Quốc gia</TD>
								<TD class="plainlabel" colspan = "3">
									<SELECT name="quocgialist" id="quocgialist" class="select2" style="width: 200px;" onChange="submitform();">
								    <option value=""></option>
								      <% if(qg != null) 
								      try{while(qg.next()){ 
								    	if(qg.getString("id").trim().equals(nppBean.getquocgiaid().trim())){ %>
								      		<option value='<%=qg.getString("Id")%>' selected><%=qg.getString("Ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=qg.getString("Id")%>'><%=qg.getString("Ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        			</SELECT>	
								</TD>
                                <%-- <TD class="plainlabel">Chi nhánh ngân hàng</TD>
								<TD class="plainlabel"><INPUT name="cnnganhang" id="cnnganhang" value="<%= nppBean.getCNNganHang() %>" type="text"></TD>	 --%>
								
							</TR>
							<TR>
								<TD class="plainlabel" width="140px" >Tên xuất hóa đơn</TD>
								<TD  class="plainlabel">
									<a href="" id="noidungtenxuathoadon" rel="ndTenxuathoadon">
        	 									&nbsp; <img alt="Tên xuất hóa đơn" src="../images/vitriluu.png"></a>
        	 		
                      							<DIV id="ndTenxuathoadon" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
                             		background-color: white; width: 350px; min-height:150px; overflow:auto; padding: 4px;">
                    					<table width="100%" align="center">
					                        <tr>
					                            <th width="300px">Tên xuất hóa đơn</th>
					                        </tr>
			                            	<tr>
			                            		<TD class="plainlabel"  >
													<INPUT name="tenxhd1" id="tenxhd1" type="text" value="<%= nppBean.getTenhd1() %>" style="width: 100%">
												</TD>
			                            	</tr>
			                            	<tr>
				                                <TD class="plainlabel"  >
				                                	<INPUT name="tenxhd2" id="tenxhd2" type="text" value="<%= nppBean.getTenhd2() %>" style="width: 100%">
				                                </TD>
			                            	</tr>
			                            	<tr>
			                            		<TD class="plainlabel" >
                                					<INPUT name="tenxhd3" id="tenxhd3" type="text" value="<%= nppBean.getTenhd3() %>" style="width: 100%">
                                				</TD>
			                            	</tr>
		                            		<tr>
		                            			<TD class="plainlabel" >
													<INPUT name="tenxhd4" id="tenxhd4" type="text" value="<%= nppBean.getTenhd4() %>" style="width: 100%" >
												</TD>
		                            		</tr>
				                    	</table>
				                     	<div align="left">
					                     	<label style="color:red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('ndTenxuathoadon')" >Hoàn tất</a>
				                     </div>
			            		</DIV>   
							  	</TD>
							  	<TD class="plainlabel" >Vùng</TD>
								<TD class="plainlabel" colspan = "1">
									<SELECT name="vungId" id="Vung" class="select2" style="width: 200px;" onChange="submitform();" multiple>
								    <option value=""></option>
								      <% if(vung != null) 
								      try{while(vung.next()){ 
								    	  if(nppBean.getVungId().indexOf(vung.getString("vungId"))  >=0){ %>
								    	
								      		<option value='<%=vung.getString("vungId")%>' selected><%=vung.getString("vungTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=vung.getString("vungId")%>'><%=vung.getString("vungTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        			</SELECT>
								</TD   >
								<%if(!nppBean.getLoaiNPP().equals("0")) {%>
								<td class="plainlabel">CMTND</td>
                        		<td class="plainlabel" >
                        			<input type="text" name="cmnd" id="cmnd" value="<%= nppBean.getCMTND()%>" >
                        		</td>
                        		 <% } else {%>
                        		 <td class="plainlabel"></td>
                        		<td class="plainlabel" >
                        		
                        		</td>
                        		<%} %>
						  </TR>

						  <TR>
						  		<TD class="plainlabel">Địa chỉ xuất HĐ </TD>
                                <TD  class="plainlabel" ><INPUT name="diachixhd" id="diachixhd" type="text" value="<%= nppBean.getDiaChiXuatHoaDon() %>"  ></TD>
                                <TD class="plainlabel">Khu vực <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
								 	<SELECT name="kvId" id="KhuVuc" class="select2" style="width: 200px;" onChange="submitform();" multiple>
								    <option value=""></option>
								      <%
								      if(kv!=null)
								      try{while(kv.next()){ 
								    	if(nppBean.getKvId().indexOf(kv.getString("kvId"))  >=0){ %>
								      		<option value='<%=kv.getString("kvId")%>' selected><%=kv.getString("kvTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=kv.getString("kvId")%>'><%=kv.getString("kvTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	
                        		</TD>
                                
	                       		<TD class="plainlabel" width="140px" >Số ngày nợ<FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel" >
                                	<INPUT name="songayno" id="songayno" type="text" value="<%= nppBean.getSongayno() %>" >
                                </TD>
						  </TR>	
						  <TR>
						   		<td class="plainlabel">Người đại diện trên HĐ</td>
                                <td class="plainlabel" >
                                	<INPUT name="ddthd" id="ddthd" type="text" value="<%= nppBean.getTenNguoiDaiDien() %>">
                                </td>
                                <TD class="plainlabel">Tỉnh/Thành phố <FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel" width="220px" >
								 	<SELECT name="tpId" id="TP" onChange="submitform();" class="select2" style="width: 200px;" >
								    	<option value=""></option>
								      	<% if(tp!=null)
								      	try{while(tp.next()){ 
								    	if(tp.getString("tpId").equals(nppBean.getTpId())){ %>
								      		<option value='<%=tp.getString("tpId")%>' selected><%=tp.getString("tpTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=tp.getString("tpId")%>'><%=tp.getString("tpTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
	                       			</SELECT>	
	                       		</TD>
                                <%if(!nppBean.getLoaiNPP().equals("0")) {%>
	                       		<TD class="plainlabel"   width="130px"  >Số tiền nợ <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" width="220px" >
									<INPUT name="sotienno" id="sotienno" type="text" value="<%= nppBean.getSoTienno() %>" >
								</TD>
								 <% } else {%>
                        		 <td class="plainlabel"></td>
                        		<td class="plainlabel" >
                        		
                        		</td>
                        		<%} %>
						  </TR>	
						  <TR>
						  		<TD class="plainlabel">Mã số thuế </TD>
                                <TD class="plainlabel"><INPUT name="masothue" id="masothue" type="text" value="<%= nppBean.getmaSoThue() %>" ></TD>
                                <TD class="plainlabel">Quận/Huyện <FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel" >
								 	<SELECT name="qhId" id="QH" class="select2" style="width: 200px;">
								    	<option value=""></option>
									      <%if(qh != null){ 
									      		try{while(qh.next()){ 
									    			if(qh.getString("qhId").equals(nppBean.getQhId())){ %>
									      				<option value='<%=qh.getString("qhId")%>' selected><%=qh.getString("qhTen") %></option>
									      		 <%}else{ %>
									     				<option value='<%=qh.getString("qhId")%>'><%=qh.getString("qhTen") %></option>
									     		<%}}}catch(java.sql.SQLException e){} 
									     		
									      }	%>	  
	                       			</SELECT>	
	                       		</TD>	
	                       		  <%if(!nppBean.getLoaiNPP().equals("0")) {%>
                                <TD class="plainlabel" width="140px" >Tần suất đặt hàng<FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel" >
                                	<INPUT name="tansuat" id="tansuat" type="text" value="<%= nppBean.getTansuat() %>" >
                                </TD>
                                	 <% } else {%>
                        		 <td class="plainlabel"></td>
                        		<td class="plainlabel">
                        		
                        		</td>
                        		<%} %>
							</TR>
							
							<TR>
								<td class="plainlabel">Hình thức TT</td>
                        		<td class="plainlabel"><input type="text" name="httt" id="httt" value="<%= nppBean.getHinhThucThanhToan() %>"></td>
                        		<TD class="plainlabel">Địa bàn <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"  >
						 			<SELECT name="diaban" id="diaban" class="select2" style="width: 200px;"  multiple> 
									    <option value=""></option> 
									      <% 
									      if(diabanrs!=null)
								     		 try{ while(diabanrs.next()){ 
		 								    	if(nppBean.getDiaban().indexOf(diabanrs.getString("diabanid"))  >=0){ %>
										      		<option value='<%=diabanrs.getString("diabanid")%>' selected><%=diabanrs.getString("tendiaban") %></option> 
		 								      	<%}else{ %> 
		 								     		<option value='<%=diabanrs.getString("diabanid")%>'><%=diabanrs.getString("tendiaban") %></option>
		 								     	<%}}}catch(java.sql.SQLException e){} %>
		                         				</SELECT>	 	
								</td>
                        		
                        		<TD class="plainlabel" width="140px" >Tồn kho an toàn<FONT class="erroralert"> *</FONT></TD>
							  	<TD class="plainlabel" >
                                	<INPUT name="tonkho" id="tonkho" type="text" value="<%= nppBean.getTonkho() %>" >
                               	</TD>	
							</TR>
							<tr>
								<TD class="plainlabel">Email </TD>
                        		<TD class="plainlabel"><input name="email" id="email" type="text" value="<%= nppBean.getEmail() %>"></TD>
                        		<Td class="plainlabel" colspan="2" >
                        			
                        			<% if(nppBean.getLoaiNPP().equals("5")) { %>
	                                
		                                Quản lý tồn kho 
		                               	<%  if (nppBean.getPriSec().equals("1")){%>
		                                      <input name="prisec" type="checkbox" value ="1" checked>
		                                <%} else {%>
		                                       <input name="prisec" type="checkbox" value ="0">
		                                <%} %>    
		                                
	                                <% } else { %>
	                                	
	                                	<%-- Tự chốt xuất kho OTC 
		                               	<%  if (nppBean.getPriSec().equals("1")){%>
		                                      <input name="prisec" type="checkbox" value ="1" checked>
		                                <%} else {%>
		                                       <input name="prisec" type="checkbox" value ="1">
		                                <%} %>  --%>
		                                
		                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                                
		                                Tự xuất kho ETC 
		                               	<%  if (nppBean.getTuxuatkhoETC().equals("1")){%>
		                                      <input name="tuxuatETC" type="checkbox" value ="1" checked>
		                                <%} else {%>
		                                       <input name="tuxuatETC" type="checkbox" value ="1">
		                                <%} %> 
		                                
		                                <br />
		                                
		                                Tự tạo hóa đơn  
		                               	<%  if (nppBean.getTutaohoadonOTC().equals("1")){%>
		                                      <input name="tutaohoadon" type="checkbox" value ="1" checked>
		                                <%} else {%>
		                                       <input name="tutaohoadon" type="checkbox" value ="1">
		                                <%} %> 
		                                
		                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                                
		                                <%-- Không phân biệt kênh   
		                               	<%  if (nppBean.getDungchungkenh().equals("1")){%>
		                                      <input name="dungchungkenh" type="checkbox" value ="1" checked>
		                                <%} else {%>
		                                       <input name="dungchungkenh" type="checkbox" value ="1">
		                                <%} %>  --%>
		                                
	                                <%} %>
                        			
                        		</Td>
                        		  <%if(!nppBean.getLoaiNPP().equals("0")) {%>
								<TD class="plainlabel" >Nhóm nhà phân phối</TD>
								<TD class="plainlabel" colspan="4" >
										<SELECT name="nhomnpp" id="nhomnpp" class="select2" style="width: 200px;" onChange="submitform();" multiple>
								    <option value=""></option>
								      <%
								      if(Nhomnpp!=null)
								      try{while(Nhomnpp.next()){ 
								    	if(nppBean.getNhomNppId().indexOf(Nhomnpp.getString("Id"))  >=0){ %>
								      		<option value='<%=Nhomnpp.getString("Id")%>' selected><%=Nhomnpp.getString("Ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=Nhomnpp.getString("Id")%>'><%=Nhomnpp.getString("Ten") %></option>
								     	<%}}}catch(java.sql.SQLException e)
								      {
								     		e.printStackTrace();
								      } %>	  
                        				</SELECT>	
								</TD>
								 <% } else {%>
                        		 <td class="plainlabel"></td>
                        		<td class="plainlabel">
                        		
                        		</td>
                        		<%} %>
							</tr>
							
							<tr > 
                        		  
							  	<TD class="plainlabel">Địa chỉ giao hàng <FONT class="erroralert"> *</FONT></TD>
							  	<TD  class="plainlabel">
									<a href="" id="noidungdcgiaohang" rel="nddcgiaohang">
        	 									&nbsp; <img alt="Địa chỉ giao hàng" src="../images/vitriluu.png"></a>
        	 		
                      							<DIV id="nddcgiaohang" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
                             		background-color: white; width: 350px; min-height:50px; overflow:auto; padding: 4px;">
                    					<table width="100%" align="center">
					                        <tr>
					                            <th width="300px">Địa chỉ giao hàng</th>
					                        </tr>
			                            	<tr>
			                            		<TD class="plainlabel" >
				                                	<INPUT name="DiaChi" id="DiaChi" type="text" value="<%= nppBean.getDiachi() %>"  style="width: 100%">
				                                </TD>
			                            	</tr>
			                            	<tr>
				                                <TD class="plainlabel" >
				                                	<INPUT name="diachi2" id="diachi2" type="text" value="<%= nppBean.getDiachi2() %>"  style="width: 100%">
				                                </TD>
			                            	</tr>
				                    	</table>
				                     	<div align="left">
					                     	<label style="color:red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('nddcgiaohang')" >Hoàn tất</a>
				                     </div>
			            		</DIV>   
							  	</TD>
							  	<% if( nppBean.getLoaiNPP().equals("4") || nppBean.getLoaiNPP().equals("5") ) { %>
                        		<TD class="plainlabel">Kho đặt hàng </TD>
								 <TD class="plainlabel" >
								 	<SELECT name="khottid" id="khottid" class="select2" style="width: 200px;" >
								      <% if(rs_khott!=null) 
								      try{while(rs_khott.next()){ 
								    	if(rs_khott.getString("pk_seq").trim().equals(nppBean.getKhoTT().trim())){ %>
								      		<option value='<%=rs_khott.getString("pk_seq")%>' selected><%=rs_khott.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=rs_khott.getString("pk_seq")%>'><%=rs_khott.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        			</SELECT>	
                        		</TD>
                        		<% } else { %>
                        			<td class="plainlabel" colspan="2" >	
                        				<input type="hidden" value="100001" >
                        			</td>
                        		<% } %>
                        		  <%if(!nppBean.getLoaiNPP().equals("0")) {%>
                        		<TD class="plainlabel" width="140px" >Xuất tại kho<FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel" >
                                	<INPUT name="xuattaikho" id="xuattaikho" type="text" value="<%= nppBean.getXuattaikho() %>" >
                                </TD>
                                	 <% } else {%>
                        		 <td class="plainlabel"></td>
                        		<td class="plainlabel">
                        		
                        		</td>
                        		<%} %>
							</tr>
							
							<% if( nppBean.getIsKhachhang().equals("0") ) { %>
							<TR>								
								
								<TD class="plainlabel" style="color: red;" >Loại</TD>
								<TD class="plainlabel" >
							<select name="loaiNPP" class="select2" style="width: 200px;color: red;"  readonly = "readonly" >
										<option value="" ></option>
										<% if(nppBean.getLoaiNPP().equals("0") ) { %>
											<option value="0" selected="selected" >Công ty MTV</option>
										<% } else { %>
											<option value="0" >Công ty MTV</option>
										<% } %>
										
										<% if(nppBean.getLoaiNPP().equals("1") ) { %>
											<option value="1" selected="selected" >Nhà phân phối</option>
										<% } else { %>
											<option value="1" >Nhà phân phối</option>
										<% } %>
										
									</select>
								</TD>
								
								<TD class="plainlabel" >Trực thuộc</TD>
								<TD class="plainlabel"  >
									<select name="tructhuocId" id="tructhuocId" class="select2" style="width: 200px;" >
										<option value="" ></option>
										<% if(tructhuocRs != null) { 
											while(tructhuocRs.next())
											{
												if(tructhuocRs.getString("Id").equals(nppBean.getTructhuocId())) { %>
													<option value="<%= tructhuocRs.getString("Id") %>" selected="selected" ><%= tructhuocRs.getString("TEN") %></option>
												<% }
												else { %>
													<option value="<%= tructhuocRs.getString("Id") %>" ><%= tructhuocRs.getString("TEN") %></option>
												<% }
											}
											tructhuocRs.close();
										} %>
									</select>
								</TD>
								
								<TD class="plainlabel" colspan="2">
                                	Hoạt động 
                                	<%  if (nppBean.getTrangthai().equals("1")){%>
	                                      <input name="TrangThai" type="checkbox" value ="1" checked>
	                                <%} else {%>
	                                       <input name="TrangThai" type="checkbox" value ="0">
	                                <%} %>  
	                                      
                                </TD>
							</TR>
							
							<% } else { %>
								
								<TR>
								
								<TD class="plainlabel" >Trình dược viên</TD>
								<TD class="plainlabel"  >
									<select name="nvbhId" id="nvbhId" class="select2" style="width: 200px;" >
										<option value="" ></option>
										<% if(rs_nvbh != null) { 
											while(rs_nvbh.next())
											{
												if(rs_nvbh.getString("Id").equals(nppBean.getNvbhId())) { %>
													<option value="<%= rs_nvbh.getString("Id") %>" selected="selected" ><%= rs_nvbh.getString("TEN") %></option>
												<% }
												else { %>
													<option value="<%= rs_nvbh.getString("Id") %>" ><%= rs_nvbh.getString("TEN") %></option>
												<% }
											}
											rs_nvbh.close();
										} %>
									</select>
								</TD>
								
								<TD class="plainlabel" colspan="2" >
                                	Hoạt động 
                                	<%  if (nppBean.getTrangthai().equals("1")){%>
	                                      <input name="TrangThai" type="checkbox" value ="1" checked>
	                                <%} else {%>
	                                       <input name="TrangThai" type="checkbox" value ="0">
	                                <%} %>  
	                                                            
                                </TD>								
							</TR>
								
							<% } %>
							
							
							<Tr>
								<TD class="plainlabel">Ngân hàng</TD>
								<TD class="plainlabel" >
									<select name="nganhangId" class="select2" style="width: 200px;" >
										<option value="" ></option>
										<% if( nganhangRs != null ) { 
											while(nganhangRs.next()) { 
												if(nganhangRs.getString("pk_seq").equals(nppBean.getNganhangId()))
												{ %>
													<option value="<%= nganhangRs.getString("pk_seq") %>" selected="selected" ><%= nganhangRs.getString("ten") %></option>
												<% } else{ %>
													<option value="<%= nganhangRs.getString("pk_seq") %>" ><%= nganhangRs.getString("ten") %></option>
												<% }
											}
											nganhangRs.close();
										} %>
									</select>
								</TD>
								<TD class="plainlabel">Chi nhánh</TD>
								<TD class="plainlabel" >
									<select name="chinhanhId" class="select2" style="width: 200px;" >
										<option value="" ></option>
										<% if( chinhanhRs != null ) { 
											while(chinhanhRs.next()) { 
												if(chinhanhRs.getString("pk_seq").equals(nppBean.getChinhanhId()))
												{ %>
													<option value="<%= chinhanhRs.getString("pk_seq") %>" selected="selected" ><%= chinhanhRs.getString("ten") %></option>
												<% } else{ %>
													<option value="<%= chinhanhRs.getString("pk_seq") %>" ><%= chinhanhRs.getString("ten") %></option>
												<% }
											}
											chinhanhRs.close();
										} %>
									</select>
								</TD>
								<TD class="plainlabel">Tài khoản</TD>
								<TD class="plainlabel" class="select2" style="width: 200px;" >
									<select name="taikhoanId" >
										<option value="" ></option>
										<% if( taikhoanRs != null ) { 
											while(taikhoanRs.next()) { 
												if(taikhoanRs.getString("pk_seq").equals(nppBean.getTaikhoanId()))
												{ %>
													<option value="<%= taikhoanRs.getString("pk_seq") %>" selected="selected" ><%= taikhoanRs.getString("ten") %></option>
												<% } else{ %>
													<option value="<%= taikhoanRs.getString("pk_seq") %>" ><%= taikhoanRs.getString("ten") %></option>
												<% }
											}
											taikhoanRs.close();
										} %>
									</select>
								</TD>
							</Tr>
							
							<Tr>
								<TD class="plainlabel">Phần trăm chiết khấu</TD>
								<TD class="plainlabel" >
									<INPUT name="ptck" type="text" value="<%= nppBean.getPhantramCK() %>"  style="width: 200px; text-align: right;">
								</TD>
								<TD class="plainlabel">Chiết khấu TT (%)</TD>
								<TD class="plainlabel" >
									<INPUT name="chietkhauTT" type="text" value="<%= nppBean.getCKThanhtoan() %>"  style="width: 200px; text-align: right;">
								</TD>
								
								<TD class="plainlabel" >Khoản mục chi phí </TD>
		                        <TD class="plainlabel" colspan="6">
		                            <select name="kmcpId">
		                            <option value=""> </option>
		                            <% while(kmcpRs.next()) 
		                            {
		                              if(kmcpRs.getString("pk_seq").equals(nppBean.getKhoanmuccpId())){ %>
		                                <option value="<%= kmcpRs.getString("pk_seq") %>" selected><%= kmcpRs.getString("diengiai") %></option>
		                                
		                            <%}else { %>
		                            	<option value="<%= kmcpRs.getString("pk_seq") %>"><%= kmcpRs.getString("diengiai") %></option>
		                            <%} } kmcpRs.close(); %>
		                            	
		                            </select>
		                        </TD>
						                        
											
								
							</Tr>
							
							
					<% if(true || nppBean.getLoaiNPP().equals("4") ) { %>
							<tr class="plainlabel">
								 <TD class="plainlabel">Loại công nợ </TD>
                                    <TD  class="plainlabel" >
                                    	<SELECT name ="trangthai" onChange = "submitform();">
                                              <option value="1" class="select2"  selected>Công nợ theo hạn mức</option>
                                        </SELECT>     
                                       <%--  <% if (nppBean.getLoaiCN().equals("1")){%>
                                      		  <option value=""></option>
                                              <option value="1" selected>Công nợ tối đa</option>
                                              <option value="0">Công nợ theo mức trần</option>
                                              <option value="2">Công nợ theo hạn mức</option>
                                              
                                        <%}else if(nppBean.getLoaiCN().equals("0")) {%>
                                              <option value="" > </option>
                                              <option value="1" >Công nợ tối đa</option>
                                              <option value="0" selected>Công nợ theo mức trần</option>
                                              <option value="2">Công nợ theo hạn mức</option>
                                              
                                        <%}else if(nppBean.getLoaiCN().equals("2")) {%>                                                                                        
                                             <option value=""></option>
                                              <option value="1" >Công nợ tối đa</option>
                                              <option value="0">Công nợ theo mức trần</option>
                                              <option value="2" selected>Công nợ theo hạn mức</option>
                                        <%}else {%>
                                        	  <option value="" selected></option>
                                              <option value="1" >Công nợ tối đa</option>
                                              <option value="0">Công nợ theo mức trần</option>
                                              <option value="2">Công nợ theo hạn mức</option>
                                        <%} %> --%>                                        
                                      </TD>  
									<TD class="plainlabel">Hạn mức nợ </TD>
									<TD class="plainlabel" >
									<INPUT type="text" name="hanmucno" id="hanmucno" value="<%= nppBean.getHanmucno() %>" size="40" onKeyPress = "return keypress(event);"  onchange="DinhDang();">
									</TD>
									
									<TD class="plainlabel">Số ngày nợ</TD>
									<TD class="plainlabel" >
									<INPUT type="text" name="songayno" id="songayno" value="<%= nppBean.getSongayno() %>" size="40" onKeyPress = "return keypress(event);"  onchange="DinhDang();">
									</TD>	
									
							</TR>
					<%} %>
					
					
						</TABLE>
						<ul class="tabs">	
						  	<%if(!nppBean.getLoaiNPP().equals("0")) {%>
							<li <%=nppBean.getActiveTab().equals("0") ? "class='current'" : "" %>><a href="#tabGS">Giám sát bán hàng</a></li>
							<%} %>
							<li <%=nppBean.getActiveTab().equals("1") ? "class='current'" : "" %>><a href="#tabHT">Hệ thống</a></li>
							<li <%=nppBean.getActiveTab().equals("2") ? "class='current'" : "" %>><a href="#tabNK">Nhóm kênh</a></li>
							<li <%=nppBean.getActiveTab().equals("3") ? "class='current'" : "" %>><a href="#tabKBH">Kênh</a></li>
						</ul>
						
						<div class="panes">
							
							<div id="tabGS" class="tab_content">
									<LEGEND class="legendtitle" style="color: black">Chọn
										giám sát bán hàng</LEGEND>
									<TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
										<TR class="tbheader">
											<TH width="13%">Giám sát bán hàng</TH>
											<TH width="13%">Kênh bán hàng</TH>
											<TH width="13%">Số điện thoại</TH>
											<TH width="13%">Chọn</TH>
										</TR>
										<%
								int j = 0;
								String light = "tblightrow";
								String dark = "tbdarkrow";
								if(!(gsbh_kbh == null)){
									try{
								while(gsbh_kbh.next())
									{ 
										if (j % 2 != 0) {%>
										<TR class=<%=light%>>
											<%} else {%>
										
										<TR class=<%= dark%>>
											<%}%>
											<TD align="center"><%= gsbh_kbh.getString("gsbhTen") %>
												</TD>
											<TD align="center"><%= gsbh_kbh.getString("kbhTen") %>
												</TD>
											<TD align="center"><%= gsbh_kbh.getString("sodienthoai") %></TD>
											<TD align="center">
											<% if(nppBean.getGsbhId().indexOf(gsbh_kbh.getString("gsbh_kbhId"))>=0){%>
												<input name="gsbh" onclick="Kiemtra(this)" id="gsbh" type="checkbox"
												value="<%= gsbh_kbh.getString("gsbh_kbhId")%>"
												checked></TD>
											<%}else{ %>
		
												<input name="gsbh" onclick="Kiemtra(this)" id="gsbh" type="checkbox" value="<%= gsbh_kbh.getString("gsbh_kbhId")%>"/>
												</TD>
											<%} %>
											
										</TR>
										<%j++;}
									}
									catch(java.sql.SQLException e){}
								}
								
							  %>
									</TABLE>
							</div>
							<div id="tabHT" class="tab_content">
								
								<TABLE class="plainlabel" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    <tr >
				                    		<LEGEND class="legendtitle" style="color: black">Chọn
										hệ thống bán hàng</LEGEND>
									<TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
										<TR class="tbheader">
											<TH width="15%">Hệ thống bán hàng</TH>
											<TH width="15%">Diễn giải </TH>
											<TH width="13%">Chọn</TH>
										</TR>
										<%
								int j1 = 0;
								String light1 = "tblightrow";
								String dark1 = "tbdarkrow";
								if(!(htbh == null)){
									try{
								while(htbh.next())
									{ 
										if (j1 % 2 != 0) {%>
										<TR class=<%=light1%>>
											<%} else {%>
										
										<TR class=<%= dark1%>>
											<%}%>
											<TD align="center"><%= htbh.getString("ten") %>
												</TD>
											<TD align="center"><%= htbh.getString("diengiai") %>
												</TD>
											<TD align="center">
											<% if(nppBean.getHtbh().indexOf(htbh.getString("pk_seq"))>=0){%>
												<input name="htbh" onclick="Kiemtra(this)" id="htbh" type="checkbox"
												value="<%= htbh.getString("pk_seq")%>"
												checked></TD>
											<%}else{ %>
												<input name="htbh" onclick="Kiemtra(this)" id="htbh" type="checkbox"
												value="<%= htbh.getString("pk_seq")%>">
												</TD>
												
												<%} %>
										</TR>
										<%j1++;}
									}
									catch(java.sql.SQLException e)
									{
										e.printStackTrace();
										htbh.close();
									}
								}
								
							  %>
				                    </tr> 
				                    <TR>
									  
								  </TR>
								</TABLE>
								
							</div>
							<div id="tabNK" class="tab_content">
								
						<TABLE class="plainlabel" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				       	<LEGEND class="legendtitle" style="color: black">Chọn
										 nhóm kênh </LEGEND>
									<TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
										<TR class="tbheader">
											<TH width="13%">Nhóm kênh</TH>
											<TH width="13%">Diễn giải</TH>
											<TH width="13%">Chọn</TH>
										</TR>
										<%
								int j2 = 0;
								String light2 = "tblightrow";
								String dark2 = "tbdarkrow";
								if(!(nhomkenh == null)){
									try{
								while(nhomkenh.next())
									{ 
										if (j2 % 2 != 0) {%>
										<TR class=<%=light2%>>
											<%} else {%>
										
										<TR class=<%= dark2%>>
											<%}%>
											<TD align="center"><%= nhomkenh.getString("ten") %>
												</TD>
											<TD align="center"><%= nhomkenh.getString("diengiai") %>
												</TD>
											<TD align="center">
											<% if(nppBean.getNhomkenhid().indexOf(nhomkenh.getString("pk_seq"))>=0){%>
												<input name="nhomkenh" onclick="Kiemtra(this)" id="nhomkenh" type="checkbox"
												value="<%= nhomkenh.getString("pk_seq")%>"
												checked></TD>
											<%}else{ %>
												<input name="nhomkenh" onclick="Kiemtra(this)" id="nhomkenh" type="checkbox"
												value="<%= nhomkenh.getString("pk_seq")%>">
												</TD>
												
												
											<%} %>
										</TR>
										<%j2++;}
									}
									catch(java.sql.SQLException e){}
								}
								
							  %>
									<TR>
								
								  </TR>
								</TABLE>
								
							</div>
						<div id="tabKBH" class="tab_content">
				                 		<LEGEND class="legendtitle" style="color: black">Chọn
										 kênh</LEGEND>
									<TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
										<TR class="tbheader">
											<TH width="13%">Kênh</TH>
											<TH width="13%">Diễn giải</TH>
											<TH width="13%">Nhóm kênh </TH>
											<TH width="13%">Chọn</TH>
										</TR>
										<%
								int j3 = 0;
								String light3 = "tblightrow";
								String dark3 = "tbdarkrow";
								if(!(kenhbh == null)){
									try{
								while(kenhbh.next())
									{ 
										if (j3 % 2 != 0) {%>
										<TR class=<%=light3%>>
											<%} else {%>
										
										<TR class=<%= dark3%>>
											<%}%>
											<TD align="center"><%= kenhbh.getString("ten") %>
												</TD>
											<TD align="center"><%= kenhbh.getString("diengiai") %>
												</TD>
											<TD align="center"  value="<%= kenhbh.getString("nhomkenhid")%>"><%= kenhbh.getString("tennhomkenh") %></TD>
											<TD align="center">
											<% if(nppBean.getKenhid().indexOf(kenhbh.getString("pk_seq"))>=0){%>
												<input name="kenhbh" onclick="Kiemtra(this)" id="kenhbh" type="checkbox"
												value="<%= kenhbh.getString("pk_seq")%>"
												checked></TD>
											<%}else{ %>
												<input name="kenhbh" onclick="Kiemtra(this)" id="gsbh_kbhList" type="checkbox"
												value="<%= kenhbh.getString("pk_seq")%>"
												></TD>
											
											<%} %>
										</TR>
										<%j3++;}
									}
									catch(java.sql.SQLException e){}
								}
								
							  %>
				               
											
				                   
								</TABLE>
								</div>
						</TABLE>		
									
					
									
								
					
</TABLE>
<script type="text/javascript">
dropdowncontent.init('noidungtenxuathoadon', "right-top", 500, "click");
dropdowncontent.init('noidungdcgiaohang', "right-top", 500, "click");
</script>
</form>
</BODY>
</HTML>
<%
if(nppBean!=null)nppBean.DBclose();
	}%>
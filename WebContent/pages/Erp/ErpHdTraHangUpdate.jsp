<%@page import="geso.traphaco.erp.beans.hoadon.IErpHoaDon_SP"%>
<%@page import="javax.print.DocFlavor.READER"%>
<%@page import="geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHang"%>
<%@page import="geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHang_SanPham"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Formattable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>

<% 	
	String userId = (String)session.getAttribute("userId");
	String userTen = (String)session.getAttribute("userTen");
  	IErpHdTraHang hdthBean = (IErpHdTraHang)session.getAttribute("hdthBean");
	ResultSet rslistdh = hdthBean.getRsDthChuaXuatHD();
	ResultSet rshdct = hdthBean.getHoadonCanTruRs();
	ResultSet khRs = hdthBean.getkhRs();
	
	String[] dth = hdthBean.getDonTraHang();
	Hashtable<String, String> htbddh = new Hashtable<String, String>();
	//System.out.println("so don "+dth.length);
	int i = 0;
	if(dth != null)
	{
		while(i <1)
		{
			htbddh.put(dth[i],dth[i]);
			i = i+1;
		}
	}
	
	NumberFormat formatter=new DecimalFormat("#,###,###.##"); 
	List<IErpHdTraHang_SanPham> splist = hdthBean.GetListSanPham();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Phanam - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />


<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_erp_bgmuaKh.js"></script>

<script type="text/javascript" src="../scripts/DocTienTiengViet.js"></script>
<script type="text/javascript" src="../scripts/formattien.js"></script>


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

<script src="../scripts/ui/jquery.scrollTableBody-1.0.0.js" type="text/javascript"></script>
<script type="text/javascript">
            $(function() {
                $('#tableDDH').scrollTableBody({rowsToDisplay:9});
            });
</script> 

<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
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
	
	function replaces()
	{
		var maKh=document.getElementById("KhId");
		if(maKh.value.indexOf(" -- ") > 0 )
		{
			var str = maKh.value;
			maKh.value = str.substring(0, maKh.value.indexOf(" -- "));
			document.getElementById("KhTen").value =str.substring(str.indexOf(" -- ") + 4);
			loadcontent();
		}
		
		setTimeout(replaces, 200);
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
			if (keypressed == 8 ||  keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	
	function saveform()
	{	 
		if (document.forms["dhForm"]["NgayXuatHD"].value.length == 0) {
			setErrors("Vui lòng nhập ngày xuất hóa đơn");					
			return;
		}
		
		if(document.forms["dhForm"]["khonghd"].checked == false )
		{ 
		 	 if (document.forms["dhForm"]["KyHieu"].value.length == 0) {
					setErrors("Vui lòng nhập ký hiệu hóa đơn");					
					return;
				} 
			 
			 if (document.forms["dhForm"]["SoHoaDon"].value.length != 7) {
					setErrors("Số hóa đơn không được để trống và phải là 7 số");					
					return;
				}
		} 
		
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='0' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dhForm'].action.value='save';
	     document.forms['dhForm'].submit();
	 }
	
	 function submitform()
	 { 
		congDonSPCungMa();
		document.forms['dhForm'].action.value='submit';
	    document.forms["dhForm"].submit();
	 }
	 
	 function loadcontent()
	 {             
		document.forms['dhForm'].action.value = 'reload';
	    document.forms["dhForm"].submit();
	 }
	 
	 function ChotForm()
	 {
		 congDonSPCungMa();
		 var Ma = document.getElementsByName("Ma");
		 var TenSP = document.getElementsByName("TenSP");
		 var SoLuong = document.getElementsByName("SoLuong");
		 for(var k = 0; k < Ma.length; k++)
		 {
			 if(Ma.item(k).value != "")
				if(SoLuong.item(k).value == "" || TenSP.item(k).value == "")
				{
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
					return;
				}
		 }
		 document.forms['dhForm'].action.value='chotdonhang';
		 document.forms['dhForm'].submit();
	 }
	 
	 function congDonSPCungMa()
	 {
		var Ma = document.getElementsByName("Ma");
		var SoLuong = document.getElementsByName("SoLuong");
		var ii;
		for(ii = 0; ii < (Ma.length - 1) ; ii++)
		{
			for( j = ii + 1; j < Ma.length; j++)
			{
				if(Ma.item(ii).value != "" && Ma.item(ii).value == Ma.item(j).value)
				{
					//alert(Ma.item(ii).value + "-" + Ma.item(j).value);				
					if(SoLuong.item(j).value == "")
						SoLuong.item(j).value = "0";
					
					SoLuong.item(ii).value = parseInt(SoLuong.item(ii).value) + parseInt(SoLuong.item(j).value);
					Ma.item(j).value = "";
				}
			}
		}
	 }
	 
	 function All()
	 { 
		var Kh = document.getElementsByName("dthId");
		for(i=0; i<Kh.length; i++){
		 	if(document.dhForm.all.checked ==true){
		 	  Kh[i].checked = true;
			}else{
				Kh[i].checked = false;
			}
		}
	 }
		
	function getinfoddh()
	{
		document.forms['dhForm'].action.value='loadddh';
		document.forms['dhForm'].submit();
	}
	 function goBack()
	 {
	  	window.history.back();
	 }
	 
	 function setErrors(errorMsg) {
			var errors = document.getElementById("dataerror");
			errors.value = errorMsg;
		}
	 
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dhForm" method="post" action="../../ErpHdTraHangUpdateSvl">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type="hidden" name="action" value='reload'>
<INPUT type="hidden" name="trangthai" value=''>   
<INPUT type="hidden" name="id" value='<%=hdthBean.getId()%>'>   
<input type="hidden" name='tenform' value="newform">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0" >
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation">Quản lý bán hàng > Nghiệp vụ khác > Nhập hóa đơn trả hàng > Cập nhật </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn <%= userTen %> </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "javascript: goBack();" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    <TD width="30" align="left" ><A id = "btnSave" href="javascript:saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
							    			<TD width="30" align="left"></TD>
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="1" width="100%" cellpadding = "1" cellspacing = "0" style="border-color:gray;" >
								<tr>
								
								 <TD align="left" class="legendtitle">
									<fieldset  >
									<legend>Thông báo nhập liệu</legend>
				    				<textarea name="dataerror"  id="dataerror" style="width:100%;margin:0px " readonly="readonly" rows="1"><%=hdthBean.getMsg() %></textarea>
							  	</fieldset>
							  	 </TD>
								</tr>
								
								<TR class="plainlabel">
									<TD align="left" >	
										<TABLE width="100%" cellpadding="4" cellspacing="0" >
											<TR class="plainlabel" >
											  <TD class="plainlabel" >Ngày hóa đơn </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" class="days"  id="NgayXuatHD" name="NgayXuatHD" value="<%=hdthBean.getNgayXuatHD()%>" maxlength="10"  /> 
											   </TD>
											    <td class="plainlabel" colspan="2" >
												 <% if(hdthBean.getKhongHD().equals("0")) { %>
													 <input type="checkbox" id="khonghd" name="khonghd" value="0" checked="checked" onchange="getinfoddh();" > <i>Không có hóa đơn</i>
												<% } else { %>
													  <input type="checkbox" id="khonghd" name="khonghd" value="0" onchange="getinfoddh();" > <i>Không có hóa đơn</i>
												<% } %> 
												</td>
											</TR>
											<%-- <TR class="plainlabel" >
											<TD class="plainlabel">Khách hàng</TD>
												<TD class="plainlabel">
													<select name="KhId" id="KhId" onchange="loadcontent();" style ="width:200px" >
														<option value=""> </option>
														<%
															if(khRs != null)
															{
																try
																{
																	while(khRs.next())
																	{  									 
																	if( khRs.getString("pk_seq").equals(hdthBean.getKhId())){ %>
																	<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
																	<%		}else { %>
																	<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %> </option>
																	<% 		} 
																	} 
																	khRs.close();
																}catch(java.sql.SQLException ex){}
															}
													   %>
													</select>
												</TD>	
											</TR>
											 --%>
											<TR class="plainlabel" >												
											 	<TD>Ký hiệu hóa đơn </TD>
											  	<TD >                               
                                                 <input type="text" size="10" id="KyHieu" name="KyHieu" value="<%=hdthBean.getKyHieu() %>" >                                                  
											    </TD>
											    <td colspan="2"> </td>										    
											</TR>
												
											<TR class="plainlabel" >
											<td>
												Hình thức thanh toán
													<input type="hidden" id="NguoiMuaHang" name="NguoiMuaHang" value="<%=hdthBean.getNguoiMuaHang() %>" > 
												</td>
												<td>
												   <select name="HinhThucTT" id="HinhThucTT" style="width: 200px">
												   <%if(hdthBean.getHinhThucTT().equals("Tiền mặt")) {%>
													   	<option value="Tiền mặt" selected="selected">Tiền mặt </option>
													    <option  value="Chuyển khoản">Chuyển khoản </option>
													    <option  value="Không thu tiền">Không thu tiền </option>
													    <option  value="Đổi hàng">Đổi hàng </option>
													    <option  value="Trả hàng">Trả hàng </option>
													    <option  value="TM/CK">TM/CK</option>
												    <%}else{ 
												    	if(hdthBean.getHinhThucTT().equals("Chuyển khoản")){ %>
													      	<option value="Tiền mặt">Tiền mặt </option>
														    <option  value="Chuyển khoản" selected="selected">Chuyển khoản </option>
														    <option  value="Không thu tiền">Không thu tiền </option>
														    <option  value="Đổi hàng">Đổi hàng </option>
														    <option  value="Trả hàng">Trả hàng </option>
														    <option  value="TM/CK">TM/CK</option>
												    <%} else{ if(hdthBean.getHinhThucTT().equals("Không thu tiền")){ %>
												    		<option value="Tiền mặt">Tiền mặt </option>
														    <option  value="Chuyển khoản">Chuyển khoản </option>
														    <option  value="Không thu tiền"  selected="selected">Không thu tiền </option>
														    <option  value="Đổi hàng">Đổi hàng </option>
														    <option  value="Trả hàng">Trả hàng </option>
														    <option  value="TM/CK">TM/CK</option>
												    <% } else{ if(hdthBean.getHinhThucTT().equals("Đổi hàng")){ %>
												    		<option value="Tiền mặt">Tiền mặt </option>
														    <option  value="Chuyển khoản">Chuyển khoản </option>
														    <option  value="Không thu tiền">Không thu tiền </option>
														    <option  value="Đổi hàng" selected="selected">Đổi hàng </option>
														    <option  value="Trả hàng">Trả hàng </option>
														    <option  value="TM/CK">TM/CK</option>	
												   <% } else{ if(hdthBean.getHinhThucTT().equals("Trả hàng")){ %>
													   		<option value="Tiền mặt">Tiền mặt </option>
														    <option  value="Chuyển khoản">Chuyển khoản </option>
														    <option  value="Không thu tiền">Không thu tiền </option>
														    <option  value="Đổi hàng">Đổi hàng </option>
														    <option  value="Trả hàng" selected="selected">Trả hàng </option>	
												  <% } else{ %>
												  			<option  value="TM/CK" selected="selected">TM/CK</option>
												  			<option value="Tiền mặt">Tiền mặt </option>
														    <option  value="Chuyển khoản">Chuyển khoản </option>
														    <option  value="Không thu tiền">Không thu tiền </option>
														    <option  value="Đổi hàng">Đổi hàng </option> 
														    <option  value="Trả hàng">Trả hàng </option>   	
												   <% } } } } } %>
												   </select>
												</td>
												<td colspan="2" >
													&nbsp;
												</td>
											</tr> 
											<TR class="plainlabel" >
											    <td>Số hóa đơn </td>
												<td>
													<input type="text" id="SoHoaDon" name="SoHoaDon" onkeypress="return keypress(event);"  value="<%=hdthBean.getSoHoaDon()%>">
												</td>
												<td >
													&nbsp;
												</td>
											</TR>
											<tr class="plainlabel" >
											<td >Ghi chú</td>
											<td>
												<input type="text" id="Ghichu" name="Ghichu"  value="<%=hdthBean.getGhiChu()%>">
											</td>
											<td colspan="2">
													&nbsp;
												</td>
											</tr> 
											<TR class="plainlabel" >
											<TD class="plainlabel">Cấn trừ theo hóa đơn</TD>
												<TD class="plainlabel">
													<select name="hoadonId" id="hoadonId" onchange="loadcontent();" style ="width:200px" >
														<option value=""> </option>
														<%
															if(rshdct != null)
															{
																try
																{
																	while(rshdct.next())
																	{  									 
																	if( rshdct.getString("PK_SEQ").equals(hdthBean.getHoadonCanTruId())){ %>
																	<option value="<%= rshdct.getString("PK_SEQ") %>" selected="selected" ><%= rshdct.getString("SOHOADON") %></option>
																	<%		}else { %>
																	<option value="<%= rshdct.getString("PK_SEQ") %>" ><%= rshdct.getString("SOHOADON") %> </option>
																	<% 		} 
																	} 
																	rshdct.close();
																}catch(java.sql.SQLException ex){}
															}
													   %>
													</select>
												</TD>	
											</TR>
											
											  <tr> 
											  <td colspan="2"  class="legendtitle" >
											  <fieldset>
												<legend>Chọn đơn trả hàng</legend>
												   <div id = "headerKH" style="width:100%; "> 
													   <table width="100%">
														<TR class="tbheader">
										                        <TH align="center" width="100px">Số đơn trả hàng</TH>
										                        <TH align="center" width="150px">Tên KH</TH>
										                        <TH align="center" width="50px"> Ngày </TH>
										                        <TH align="center" width="50px"> Chọn </TH>
		                   								</TR>
		                   								</table>
	                   								</div>
	                   								
	                   							<table id="tableDDH" width="100%">												
                   								<tbody>
                   								
                   									<%
                   									if(rslistdh!=null)
                   									  while(rslistdh.next()){ %>
                   									  <tr class= 'tblightrow'>
                   										<td align="center"  width="87px"><%= rslistdh.getString("id") %></td>
                   										<td align="left"  width="147px"><%= rslistdh.getString("TEN") %> </td>                   										
                   										<td align="left" width="55px" ><%=rslistdh.getString("ngaytra") %> 	
                   										</td >
                   											<%if(htbddh.containsValue(rslistdh.getString("id").trim())){%>
                   											<TD align="center" width="50px"><input type ="radio" checked="checked" name ="dthId" value ="<%=rslistdh.getString("id") %>" onchange="getinfoddh();" ></TD>
                   											<%}else{ %>
                   											<TD align="center" width="50px"><input type ="radio" id="dthId" name ="dthId" value ="<%=rslistdh.getString("id") %>" onchange="getinfoddh();" ></TD>
                   											<%} %>
                   										</tr>
                   									<%} %>
                   								</tbody> 
                   								</table>
                   								</fieldset>
                   								</td>
                   								<td colspan="2" valign="bottom" >
                   								  <table class="plainlabel" width="100%"  style="padding-top:0 ; margin-top:0">

                   								    <tr>
                   								    	<td width="30%">Tiền CK </td>
                   								     
	                   								    <td width="70%">
	                   								    	<input type="text" style="text-align:right" id="TienCK" name="TienCK" value="<%=formatter.format(hdthBean.getTienCK()) %>" readonly="readonly">
	                   								    </td>
                   								    </tr>
                   								    <%-- <tr>
                   								    	<td>Tiền sau CK </td>
	                   								    <td>
	                   								      <input type="text" style="text-align:right"  id="TienSauCK" name="TienSauCK" value="<%=formatter.format(hdthBean.getTienSauCK())%>" readonly="readonly">
	                   								    </td>
                   								    </tr> --%>
                   								     <tr>
                   								    	<td>Tiền VAT </td>
	                   								    <td>
	                   								      <input type="text" style="text-align:right" id="TienVAT" name="TienVAT" value="<%=formatter.format(hdthBean.getTienVAT())%>" readonly="readonly">
	                   								      <input type="hidden" style="text-align:right" id="VAT" name="VAT" value="<%=formatter.format(hdthBean.getVAT())%>" readonly="readonly">
	                   								      <input type="hidden" style="text-align:right" id="BVAT" name="BVAT" value="<%=formatter.format(hdthBean.getTienBVAT())%>" readonly="readonly">
	                   								    </td>
                   								    </tr>
                   								    <tr>
	                   								    <td>Tiền sau VAT</td>
                   								    <td>
                   								      <input type="text" style="text-align:right" id="tienavat" name="tienavat" value="<%=formatter.format(hdthBean.getTienAVAT())%>" readonly="readonly">
                   								    </td>
                   								    </tr>
                   								  </table>
                   								</td>
                   								</tr>
										</TABLE>
								
								  </TD>

							   </TR>
							   <TR>
							   		<TD>
										<TABLE class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
												<TH width="5%" height="5">Số TT</TH>
													<TH width="15%" height="20">Mã sản phẩm </TH>
													<TH width="30%">Tên sản phẩm</TH>
													<TH width="10%">Số lượng</TH>
													<TH width="10%">DVT</TH>
													<TH width="15%">Đơn giá </TH>
													<TH width="15%">Thành tiền </TH>		
												</TR>
									<% 
							if(splist != null){
							IErpHdTraHang_SanPham sanpham;
							int size = splist.size();
							int m = 0;
							while (m < size){
								sanpham = splist.get(m); 
								%>
									<TR class= 'tblightrow' >
									<TD align = "left" >
								        <input name="sott" type="text" value="<%=m+1 %>" onkeypress="return keypress(event);" style="text-align:left;width:100%;">
								        </TD>
									<TD align="left" >
										<input name='SanPhamId' type='hidden' value=<%=sanpham.getId() %>>
										<input name="Ma" type="text" value="<%=sanpham.getMa()%>" autocomplete='off'  onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%">
									</TD>
									<TD align="left" >
										<input name="Ten" type="text" readonly value="<%=sanpham.getTen()%>" style="width:100%" ></TD>
							        	<TD align = "left" >
								        <input name="SoLuong" type="text" value="<%= sanpham.getSoLuong() %>" onkeypress="return keypress(event);" readonly="readonly" style="text-align:left;width:100%;">
								        </TD>
								  
								    <TD align = "center" >
								    	<input name="DonViTinh" type="text" value="<%= sanpham.getDonViTinh() %>" readonly style="width:100%">
								    	<input name="DVDL" type="hidden" value="<%= sanpham.getDVDL() %>" readonly style="width:100%">
								    	<input name="ptVat" type="hidden" value="<%= sanpham.getptVat() %>" readonly style="width:100%">
								    </TD>
								    <TD align = "center" >
								    	<input type="text" name="DonGia" value="<%= formatter.format(sanpham.getDonGia()) %>" readonly style="width:100% ;text-align:right">
								    </TD>
								    
								    <TD align = "center" ><input name="thanhtien" type="text" value="<%=formatter.format(sanpham.getThanhTien()) %>" readonly  style="width:100% ;text-align:right"></TD>
								</TR>
								<% m++; }}%>		
								
								</tbody>
								</TABLE>
															  
							</TD>
							  </TR>	
							  	  							  					   
						  </TABLE>
											</TD>
					</TR>	
					
				</TBODY>
			</TABLE>
	</td>
  </tr>

</TABLE>
<script type="text/javascript">
	replaces();
	jQuery(function()
	{		
		$("#KhId").autocomplete("/TraphacoHYERP/pages/Center/Erp_NhaPhanPhoiList.jsp");
	});	
</script>
</form>
</BODY>
</HTML>
<%
try{

	
}catch(Exception er){
	
}
%>


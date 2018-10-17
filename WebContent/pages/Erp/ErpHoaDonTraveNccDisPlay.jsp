<%@page import="geso.traphaco.erp.beans.hoadontravencc.IErpHoaDon_SP"%>
<%@page import="javax.print.DocFlavor.READER"%>
<%@page import="geso.traphaco.erp.beans.hoadontravencc.IErpHoaDon"%>
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
<%@ page import="geso.traphaco.erp.beans.nhapkhoNK.*"%>

<% String userId = (String)session.getAttribute("userId");
String userTen=(String)session.getAttribute("userTen");
%>

<%	
	
    IErpHoaDon hdBean=(IErpHoaDon)session.getAttribute("obj");
	ResultSet rslistdh=hdBean.getrsddhChuaXuathd();
	String[] ddh=hdBean.getDonDatHang();
	Hashtable<String, String> htbddh = new Hashtable<String, String>();
	int i=0;
	if(ddh!=null){
		 
		while(i< ddh.length){
			if(ddh[i]!=null){
				htbddh.put(ddh[i],ddh[i]);
			}
			i=i+1;
		}
	}
	String[] scheme_chietkhau=hdBean.getScheme_chietkhau();
	
	String[] scheme_ck_thanhtien=hdBean.getScheme_ck_thanhtien();
	
	NumberFormat formatter=new DecimalFormat("#,###,###"); 
	NumberFormat formatter2=new DecimalFormat("#,###,###.####"); 
	List<IErpHoaDon_SP> splist =hdBean.GetListSanPham();
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
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
	}	
	#dhtmlpointer
	{
		position:absolute;
		left: -300px;
		z-index: 101;
		visibility: hidden;
	}
	
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_erp_bgmuanpp.js"></script>

<script type="text/javascript" src="../scripts/DocTienTiengViet.js"></script>
<script type="text/javascript" src="../scripts/formattien.js"></script>


<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();});
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

<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
 
 
function goBack()
{
 	window.history.back();
}

function loadcontent()
{             
	document.forms['dhForm'].action.value = 'reload';
   document.forms["dhForm"].submit();
}

function getNgayhethan(id){
	// kiem tra solo : id= 108816[0]ngaysanxuat 
	console.log( " id :  "+ id) ;
	var idsanpham= id.substring(0, id.indexOf("]"));
	console.log( " idsanpham :  "+ idsanpham) ;
	var idhsd= id.substring(0, id.indexOf("."));
	console.log( " idhsd :  "+ idhsd) ;

	var hansudung = document.getElementById(idhsd+"]"+"hansudung");
	var ngaysanxuat = document.getElementById(id);
	var ngayhethan = document.getElementById(idsanpham+"]"+"ngayhethan");
	
	var songay = parseFloat(hansudung.value);
	ngayhethan.value = TangDate(ngaysanxuat.value, songay);
	// kiem tra solo
}


function padNumber(number) {
    var string  = '' + number;
    string      = string.length < 2 ? '0' + string : string;
    return string;
}
function addDays(theDate, days) {
    return new Date(theDate.getTime() + days*24*60*60*1000);
}
function TangDate(ngay, songay)
{   
	var date = new Date(ngay);
	var next_date  = addDays(new Date(ngay), parseInt(songay));
    formatted = next_date.getFullYear() + '-' + padNumber(next_date.getMonth() + 1) + '-' + padNumber(next_date.getDate());
	return formatted;
}

</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dhForm" method="post" action="../../ErpHoaDonTraveNccUpDateSvl">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type="hidden" name="id" value='<%=hdBean.getId() %>'>
<input type="hidden" name="action" value='reload'>
<INPUT type="hidden" name="trangthai" value=''>   
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
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý mua hàng > Nghiệp vụ khác > Xuất HD trả về NCC >  Hiển thị </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn&nbsp;&nbsp;  <%= userTen%> &nbsp; </TD>
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
										     <%--   <TD width="30" align="left" ><A href="../../ErpHoaDonPdfSvl?userId=<%=userId %>&ddhId=<%=hdBean.getId() %>&loaihd=<%=1 %>" ><img src="../images/Printer30.png" alt="Print"  title="Print" border="1" longdesc="Print" style="border-style:outset"></A></TD> --%> 
										       <TD width="30" align="left" ><A href="../../ErpXuatHoaDonTraVeNccPdf_Svl?userId=<%=userId %>&ddhId=<%=hdBean.getId() %>&loaihd=<%=1 %>" ><img src="../images/Printer30.png" alt="Print"  title="Print" border="1" longdesc="Print" style="border-style:outset"></A></TD>
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
									<legend> Thông báo nhập liệu</legend>
				    				<textarea name="dataerror"  style="width:100%;margin:0px " readonly="readonly" rows="1"><%=hdBean.getMessage() %></textarea>
							  	</fieldset>
							  	 </TD>
								</tr>
								
								<TR class="plainlabel">
									<TD align="left" >						
										
										<TABLE  border="0" bordercolor="white" width="100%" cellpadding = "3" cellspacing = "0" style="padding-left:2px ;" >
											<tr class="plainlabel">
											<th width='15%' ></th>
											<th width='35%' ></th>
											<th width='15%' ></th>
											<th width='35%' ></th>
											</tr>
									 		<%if(hdBean.getId().length() >0) {%>
									 		<TR class="plainlabel" >
											  <TD class="plainlabel">Số chứng từ</TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10"  
                                     			   id="sochungtu1" name="sochungtu1" value="<%=hdBean.getId()%>" maxlength="10" /> 
											    </TD>
												<td style="display: none"></td>
												<td style="display: none"></td>
											</TR>
											<%} %>
									 	
									 	<TR class="plainlabel" >
									 	<td>Người mua hàng</td>
												<td>
												<input type="text"  id="nguoimuahang" name="nguoimuahang" value="<%=hdBean.getNguoiMuaHang() %>" ></td>											
											
											</TR>
											<TR class="plainlabel" >
											  <TD class="plainlabel">Ngày xuất hóa đơn </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" class="days"
                                     			   id="ngayxuathd" name="ngayxuathd" value="<%=hdBean.getNgayxuathd()%>" maxlength="10" /> 
											   </TD>
											   <td style="display: none">Số PO Siêu thị </td>
												<td style="display: none">
												   <input type="text" id="POMT" name="POMT" value="<%=hdBean.GetPOMT()%>" maxlength="10" /> 
												</td>
												
											</TR>
											<TR class="plainlabel" >
												<TD >Nhà cung cấp </TD>
												<td >
													 <input type="text" id="nhappid" name="nhappid" value="<%= hdBean.getNppId() %>">
													 <a href="" id="thongtinkhxhd" rel="subcontentncc" >
														<img  alt="Thông tin nhà cung cấp XHD" src="../images/vitriluu.png"> Nhà cung cấp XHD </a>
	                                                <DIV id="subcontentncc" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
	                                                             background-color: white; width: 500px; padding: 4px;">
	                                                    <table width="100%" align="center">
	                                                        <tr>
	                                                            <td> Tên </td>
	                                                            <td> 
	                                                           		 <input type="text" id="tenkhxhd" name="tenkhxhd"  style="width: 100%" value="<%=hdBean.getTenKhXhd() %>"> 
	                                                            </td>
	                                                              </tr>
	                                                              <Tr>
	                                                            <td> Địa chỉ </td>
	                                                            <td> 
	                                                           		 <input type="text" id="diachikhxhd" name="diachikhxhd"  style="width: 100%" value="<%=hdBean.getDiachiXhd() %>"> 
	                                                            </td>
	                                                              </tr>
	                                                              <Tr>
	                                                            <td> Mã số thuế </td>
	                                                            <td> 
	                                                           		 <input type="text" id="masothue" name="masothue"  style="width: 100%" value="<%=hdBean.getMasothueXhd() %>"> 
	                                                            </td>
	                                                        </tr>
	                                                       
	                                                    </table>
	                                                     <div align="right">
	                                                        <label style="color: red" ></label>
	                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                                        <a href="javascript:dropdowncontent.hidediv('subcontentncc')">Hoàn tất</a>
	                                                     </div>
	                                                </DIV>
	                                                
												</td>
												<td>Tên nhà cung cấp</td>
												<td>
												<input type="text" id="tennpp" name="tennpp"  style="width: 100%" value="<%=hdBean.getNppTen() %>">
												</td>
											</TR>
											<TR class="plainlabel" >
												<TD >Ký hiệu hóa đơn </TD>
											  	<TD>                               
                                                 <input type="text" size="10" id="kyhieu" name="kyhieu" value="<%=hdBean.getKyHieu() %>" > 
											    </TD>
											    <td> Hình thức thanh toán</td>
												<td>
												   <select name="hinhthuc" id="hinhthuc">
												    <% 
												   String[] mangchuoi=new String[]{"TM/CK","Chuyển khoản","Không thu tiền","Đổi hàng","Trả hàng","Tiền mặt"};
												   for(int k=0;k < mangchuoi.length;k ++ ){
													   
													   if(hdBean.gethinhthuctt().equals(mangchuoi[k])) {
														   %>
														    	<option value="<%=mangchuoi[k] %>" selected="selected"><%=mangchuoi[k] %> </option>
														   <%
													   }else{
														   %>
													    	<option value="<%=mangchuoi[k] %>" ><%=mangchuoi[k] %> </option>
													  		 <%  
													   }
												   }
												  %>
												   </select>
												</td>
											</TR>
											<TR class="plainlabel" >
											    <td>Số hóa đơn </td>
												<td>
													<input type="text" id="sohoadon" name="sohoadon" onkeypress="return keypress(event);"  value="<%=hdBean.getSoHoaDon()%>">
												</td>
											
											</TR>
											<tr class="plainlabel">
												<td >Diễn giải chứng từ</td>
												<td>
													<input style="width: 400px " type="text" id="ghichuhd" name="ghichuhd"  onchange="loadcontent();"   value="<%=hdBean.getGhiChu()%>">
												</td>	
												
											</tr> 
											  <tr> 
											  <td colspan="2"  class="legendtitle" >
											  <fieldset>
											  <legend> Chọn đơn trả hàng</legend>
											   <table width="100%">
												<TR class="tbheader">
								                        <TH align="center" width="30%">Số đơn hàng</TH>
								                        <TH align="left" width="50%"> Ngày </TH>
								                        <TH align="center" width="20%"> Chọn </TH>
                   								</TR>                   								
                   									<% if(rslistdh!=null)
                   										
                   									  while(rslistdh.next()){ %>
                   									  <tr class= 'tblightrow'>
                   										<td>
                   										 <%=rslistdh.getString("id") %> 		
                   										</td>
                   										<td><%=rslistdh.getString("ngaydat") %> 	
                   										</td>
                   											<% 
                   											 
	                   											if(htbddh.containsValue(rslistdh.getString("id").trim())){%>
	                   											<TD align="center"><input type ="radio" checked="checked" name ="ddhid" value ="<%=rslistdh.getString("id") %>" onchange="loadcontent()"></TD>
	                   											<%}else{ %>
	                   											<TD align="center"><input type ="radio" id="ddhid" name ="ddhid" value ="<%=rslistdh.getString("id") %>" onchange="loadcontent()"></TD>
	                   											<%}
                   										 
                   											%>
                   										</tr>
                   									<%} %>
                   								</table>
                   						</fieldset>
                   								</td>
                   								<td colspan="2" valign="bottom" >
                   								  <table class="plainlabel" width="100%"  style="padding-top:0 ; margin-top:0">
                   								    <tr style="display: none">
	                   								    <td width="30%"> Tiền CK</td>
	                   								    <td width="70%">
	                   								    <input type="text" style="text-align:right" id="tienck" name="tienck" value="<%=formatter.format(hdBean.getTienCK()) %>">
	                   								    VND</td>
                   								    </tr> 
                   								    <tr>
	                   								    <td>Tổng tiền chưa VAT</td>
	                   								    <td>
	                   								      <input type="text" style="text-align:right"  id="tiensauCK" name="tiensauCK" value="<%=formatter.format(hdBean.getTongtientruocVAT())%>">
	                   								    VND</td>
                   								    </tr>
                   								     <tr>
	                   								    <td>Tiền Vat</td>
	                   								    <td>
	                   								      <input type="text" style="text-align:right" id="tienvat" name="tienvat" value="<%=formatter.format(hdBean.getTienVat())%>">
	                   								    VND</td>
                   								    </tr>
                   								    <tr>
	                   								    <td> Tiền sau Vat  </td>
	                   								    <td>
	                   								      <input type="text" style="text-align:right" id="tongsotien" name="tongsotien" value="<%=formatter.format(hdBean.getTongtiensauVAT())%>">
	                   								    VND</td>
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
													<TH width="10%" height="20">Mã sản phẩm </TH>
													<TH width="35%">Tên sản phẩm</TH>
													<TH width="12%">Số lượng</TH>
													<TH width="10%">ĐVT</TH>
													<TH width="13%">Đơn giá VND</TH>
													<TH width="15%">Thành tiền VND</TH>		
												</TR>
									<% 
							if(splist != null){
							IErpHoaDon_SP sanpham;
							int size = splist.size();
							int m = 0;
							while (m < size){
								sanpham = splist.get(m); 
								%>
									<TR class= 'tblightrow' >
									<TD align = "center" >
								        <input name="sott" type="text" value="<%=m+1 %>" onkeypress="return keypress(event);" style="text-align:center;width:100%;" readonly>
								    </TD>
									<TD align="left" >
										<input name='idsp' type='hidden' value=<%=sanpham.getIdSanPham() %>>
										<input name="masp" type="text" value="<%=sanpham.getMaSanPham()%>" autocomplete='off'  onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" readonly>
									</TD>
									<TD align="right" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTenSanPham()%>" style="width:100%" >
									</TD>
									
							        <TD align = "left" >
								        <input name="soluong" type="text" value="<%= sanpham.getSoLuong() %>" onkeypress="return keypress(event);" style="text-align:right;width:80%;" readonly>
									   
									  	<a href="" name="<%=sanpham.getIdSanPham()+".popup"+m%>"  id="<%=sanpham.getIdSanPham()+".popup"+m%>" rel="subcontent<%=m%>"><img alt="Số lô - Số lượng nhận" src="../images/vitriluu.png" ></a>
									   
									   <DIV id="subcontent<%=m%>"
											style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 650px; padding: 4px;">
											<table width="90%" align="center">
												<tr>
													<th width="100px">Số lô</th>
													<th width="100px">Số lượng</th>
													<th width="100px">Ngày nhập kho</th>
													<th width="100px">Ngày hết hạn</th>
													<th width="100px">Mã thùng</th>
													<th width="100px">Mã mẻ</th>
													<th width="100px">Mã phiếu</th>
													<th width="100px">Hàm lượng</th>
													<th width="100px">Hàm ẩm</th>
													<th width="100px">Mã RQ</th>
													<th width="100px">Nhà sản xuất</th>
												</tr>
												 <%
													List<ISpDetail> spDetailList = sanpham.getSpDetail();
													int stt = 0;
													
													if (spDetailList.size() > 0) {
														for (int sd = 0; sd < spDetailList.size(); sd++) {
															ISpDetail spDetail = spDetailList.get(sd);
													%>
														<tr>
															<td><input type="text" size="100px"
																name="<%=sanpham.getIdSanPham() + "."+ m+ ".solo"%>"
																value="<%=spDetail.getSolo()%>"  readonly/></td>
																
																
															<td><input type="text" size="100px"
																name="<%=sanpham.getIdSanPham()  + "." +m+ ".soluong"%>"
																value="<%=spDetail.getSoluong() %>"
																style="text-align: right;" readonly/></td>
																
																
															<td>
																<input type="hidden" size="100px" class="days"
																name="<%=sanpham.getIdSanPham() + "." +m+ ".ngaysanxuat"%>"
																id="<%=sanpham.getIdSanPham() + "["  +m+ "."+ stt +"]ngaysanxuat"%>"
																value="<%=spDetail.getNgaySx()%>"
																onchange="getNgayhethan('<%=sanpham.getIdSanPham() + "["  +m+ "."+ stt +"]ngaysanxuat"%>')" 
																/>
																<input type="hidden" size="100px" 
																name="<%=sanpham.getIdSanPham() + "." +m+ ".khott_fk"%>"
																value="<%=spDetail.getKhott_fk()%>"
																/>
																<input type="hidden" size="100px" 
																name="<%=sanpham.getIdSanPham() + "." +m+ ".bin_fk"%>"
																value="<%=spDetail.getkhuid()%>"
																/>
																<input type="text" size="100px" class="days"
																name="<%=sanpham.getIdSanPham() + "." +m+ ".ngaynhapkho"%>"
																id="<%=sanpham.getIdSanPham() + "["  +m+ "."+ stt +"]ngaynhapkho"%>"
																value="<%=spDetail.getNgaynhapkho()%>"
																readonly
																/>
															</td>
																
															<td><input type="text" size="100px" class="days"
																name="<%=sanpham.getIdSanPham()  + "."  +m+ ".ngayhethan"%>"
																id="<%=sanpham.getIdSanPham() + "["  +m+ "."+ stt +"]ngayhethan"%>"
																value="<%=spDetail.getNgayHethan()%>"  />
															</td>
															<td><input type="text" size="100px"
																name="<%=sanpham.getIdSanPham()  + "." +m+ ".mathung"%>"
																value="<%=spDetail.getMathung() %>  "
																style="text-align: right;" readonly/></td>
																
																<td><input type="text" size="100px"
																name="<%=sanpham.getIdSanPham()  + "." +m+ ".mame"%>"
																value="<%=spDetail.getMame() %>"
																style="text-align: right;" readonly/></td>
																
																<td><input type="text" size="100px"
																name="<%=sanpham.getIdSanPham()  + "." +m+ ".maphieu"%>"
																value="<%=spDetail.getMaphieu() %>"
																style="text-align: right;" readonly/></td>
																<td><input type="text" size="100px"
																name="<%=sanpham.getIdSanPham()  + "." +m+ ".hamluong"%>"
																value="<%=spDetail.getHamluong() %>"
																style="text-align: right;" readonly/></td>
																<td><input type="text" size="100px"
																name="<%=sanpham.getIdSanPham()  + "." +m+ ".hamam"%>"
																value="<%=spDetail.getHamam() %>"
																style="text-align: right;" readonly/></td>
															<td><input type="text" size="100px"
																name="<%=sanpham.getIdSanPham()  + "." +m+ ".marq"%>"
																value="<%=spDetail.getMarrquet() %>"
																style="text-align: right;" readonly/></td>
																<td><input type="text" size="100px"
																name="<%=sanpham.getIdSanPham()  + "." +m+ ".nsxten"%>"
																value="<%=spDetail.getNSXTen() %>"
																style="text-align: right;" readonly/>
																<input type="hidden" size="100px"
																name="<%=sanpham.getIdSanPham()  + "." +m+ ".nsxid"%>"
																value="<%=spDetail.getNSXid() %>"
																style="text-align: right;" readonly/>
																</td>

														</tr>
												<%
													stt++;
																}
															}
													
												%>
												<%
													for (int k = 0; k < 10 - spDetailList.size(); k++) {
												%>
												<%-- <tr>
													<td><input type="text" size="100px"
														name="<%=sanpham.getIdSanPham() + "."  +m+ ".solo"%>"
														value="" /></td>
													<td><input type="text" size="100px"
														name="<%=sanpham.getIdSanPham()  + "." +m+ ".soluong"%>"
														value="" style="text-align: right;"  /></td>
													<td><input type="text" size="100px" class="days"
														name="<%=sanpham.getIdSanPham() + "."  +m+ ".ngaysanxuat"%>"
														id="<%=sanpham.getIdSanPham() + "["  +m+ "."+ stt +"]ngaysanxuat"%>"
														value="" onchange="getNgayhethan('<%=sanpham.getIdSanPham() + "["  +m+ "."+ stt +"]ngaysanxuat"%>')"  /></td>
													<td><input type="text" size="100px"
														name="<%=sanpham.getIdSanPham()+ "." +m+ ".ngayhethan"%>"
														id="<%=sanpham.getIdSanPham() + "["  +m+ "."+ stt +"]ngayhethan"%>"
														class="days" value=""  /></td>
												</tr> --%>
												<%
													stt++;
															}
													
													
												%> 
											</table>
											<div align="right">
												<label style="color: red"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
												href="javascript:dropdowncontent.hidediv('subcontent<%=m%>')">Hoàn tất</a>
											</div>
										</DIV>
										
										<script type="text/javascript">
									   dropdowncontent.init('<%=sanpham.getIdSanPham()+".popup"+m%>', "left-top", 300, "click");
									   </script>
								   
								   </TD>
								  
								    <TD align = "center" >
								    	<input name="donvitinh" type="text" value="<%= sanpham.getDonViTinh() %>" readonly style="width:100%; text-align: center;">
								    	<input name="dvtId" type="hidden" value="<%= sanpham.getDvtId() %>" readonly style="width:100%">
								    </TD>
								    
								    <TD align = "center" >
								    	<input type="text" name="dongia" value="<%= formatter2.format(sanpham.getDonGia()) %>" readonly style="width:100% ;text-align:right">
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
 
	dropdowncontent.init('nhapghichu', "left-bottom", 100, "click");
	dropdowncontent.init('thongtinkhxhd', "right-bottom", 300, "click");
</script>
</form>
</BODY>
</HTML>
<%
try{
if(hdBean!=null)
{
	hdBean.DBClose();
}
	
}catch(Exception er){
	
}
%>


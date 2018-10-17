<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.dieuchinhtonkho.IDieuchinhtonkho" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.sql.SQLException" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% NumberFormat formatter = new DecimalFormat("#,###,###.##");%>
<% IDieuchinhtonkho dctkBean = (IDieuchinhtonkho)session.getAttribute("dctkBean"); %>
<% ResultSet dvkd = (ResultSet) dctkBean.getDvkd(); %>
<% ResultSet kbh = (ResultSet) dctkBean.getKbh(); %>
<% ResultSet kho = (ResultSet) dctkBean.getKho(); %>
<% String[] spId = (String[])dctkBean.getSpId(); %>
<% String[] masp = (String[])dctkBean.getMasp(); %>
<% String[] tensp = (String[])dctkBean.getTensp() ; %>
<% String[] tonkho = (String[])dctkBean.getTonkho(); %>
<% String[] dv = (String[])dctkBean.getDonvi(); %>
<% String[] dc = (String[])dctkBean.getDc(); %>
<% String[] giamua = (String[])dctkBean.getGiamua(); %>
<% String[] dongiathung = (String[])dctkBean.getDongiathung(); %>
<% String[] ttien = (String[])dctkBean.getTtien(); %>
<% String[] solo = (String[])dctkBean.getSolo(); %>
<% String[] tonthung = (String[])dctkBean.getTonthung(); %>
<% String[] tonle = (String[])dctkBean.getTonle(); %>
<% String[] quycach = (String[])dctkBean.getQuycach(); %>
<% String[] soluongthung = (String[])dctkBean.getSoluongthung(); %>
<% String[] soluongle = (String[])dctkBean.getSoluongle(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<SCRIPT src="../js/md5.js" type="text/javascript" language="javascript">
</SCRIPT>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">

<SCRIPT src="../js/scripts.js" type="text/javascript" language="javascript"> </SCRIPT>
<SCRIPT src="../js/commun.js" type="text/javascript" language="javascript"> </SCRIPT>

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

<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<SCRIPT type="text/javascript">
function setTTien()
{	 	
	var tonkhothung=document.getElementsByName("tonthung");
	var tonkho=document.getElementsByName("tonkho");
	var tonkhole=document.getElementsByName("tonle");
	var soluongthung=document.getElementsByName("soluongthung");
	var soluongle=document.getElementsByName("soluongle");
	var dongia=document.getElementsByName("giaton");
	var thanhtien=document.getElementsByName("thanhtien");
	var masp = document.getElementsByName("masp");
	var donvi=document.getElementsByName("donvi");
	var quycach=document.getElementsByName("quycach");
	var dieuchinh=document.getElementsByName("dieuchinh");
	var sodong=masp.length;
	var tongtien = 0;
	for(var i=0; i < sodong ; i++)
	{
		var don_gia=dongia.item(i).value.replace(/\,/g,'').replace(",","");
		var so_luong=soluongle.item(i).value.replace(/\,/g,'').replace(",","");
		var thanh_tien=thanhtien.item(i).value.replace(/\,/g,'').replace(",","");
		var ton_kho= tonkho.item(i).value.replace(",","");
		var dieu_chinh=dieuchinh.item(i).value.replace(",","");		
		if(parseFloat(soluongle.item(i).value) > 0)
		{ 
			var dieu_chinh = parseFloat(so_luong) - parseFloat(ton_kho);
			dieuchinh.item(i).value=dieu_chinh;
			thanh_tien=	parseFloat(so_luong	)*	parseFloat(don_gia);	
			thanhtien.item(i).value=formatCurrency(thanh_tien);
			tongtien = parseFloat(tongtien) + parseFloat(thanh_tien);
		}else 		
		{
			thanh_tien=	0;	
			thanhtien.item(i).value=formatCurrency(thanh_tien);
			tongtien = parseFloat(tongtien) + parseFloat(thanh_tien);
		} 
	}
	document.getElementById("ttien").value= formatCurrency(tongtien); 
	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtien) + " Đồng Việt Nam";		
}

function isNumeric(n)
{
    var n2 = n;
    n = parseFloat(n);
    return (n!='NaN' && n2==n);
}
           
function submitform()
{      
   document.forms["dctkForm"].submit();
}

function submitenter(myfield,e)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   return false;
   }
else
   return true;
}

 function duyetform()
 {
	 document.getElementById("duyetid").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";	
 	document.forms['dctkForm'].action.value='approve';
    document.forms["dctkForm"].submit();
 }

 function printform()
{   
	 
		document.forms['dctkForm'].action.value='excel';
        document.forms["dctkForm"].submit();
}
 
 function init()
 {	
 	var tongtienavat= document.getElementById("ttien").value;
 	tongtienavat = tongtienavat.replace(',', '');
 	tongtienavat = tongtienavat.replace(',', '');
 	tongtienavat = tongtienavat.replace(',', '');
 	tongtienavat = tongtienavat.replace(',', '');
 	tongtienavat = tongtienavat.replace(',', '');
 	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtienavat) + " Đồng Việt Nam";
 	
 }


</SCRIPT>

</HEAD>
<body onLoad = 'init();'>
<form name="dctkForm" method="post" action="../../DuyetdctkSvl">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="id" type="hidden" value='<%=dctkBean.getId() %>' size="30">
<INPUT name="action" type="hidden" value='1' size="30">

<table width="100%" border="0" cellspacing="0" cellpadding="0"  >
  <tr>
    <td colspan = 4 valign="top">
                <TABLE border =0 width = 100% >
					
                <TBODY>
                	
                    <TR>
                        <TD align="left" >
                            <TABLE width="100%" cellpadding="0" cellspacing="0">
                                <TR>
                                    <TD align="left" class="tbnavigation">
                                       <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                           <tr height="22">
                                               <TD align="left" colspan="2" class="tbnavigation">Quản lý kho chi nhánh > Duyệt kiểm kê > Hiển thị </TD>
                                               <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn  <%= dctkBean.getUserTen() %> &nbsp;</TD>

                                            </tr>
                                        </table>
                                     </TD>
                                </TR>   
                            </TABLE>
                        <TD></TR>
                        <TR><TD>
                            <TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
                                <TR ><TD >
                                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <TR class = "tbdarkrow">
                                            <TD width="30" align="left"><A href="../../DuyetdctkSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset"></A></TD>
                                            <TD width="2" align="left" >&nbsp;</TD>
                            				<TD width="30" align="left" ><A href="javascript: printform()" ><img src="../images/Printer30.png" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
                                            <TD width="2">&nbsp;</TD>
                                            <%if(dctkBean.getTrangthai().equals("0")){ %>
                                            <TD width="30" align="left" ><A id="duyetid" href="javascript: duyetform()" ><img src="../images/Chot.png" alt="Duyệt" title="Duyệt" width="30" height="30" longdesc="Duyệt" border=1 style="border-style:outset"></A></TD>
                                            <%} %>
                            				<TD align="left">&nbsp;</TD>
                                        </TR>
                                    </TABLE>
                                </TD></TR>

                            </TABLE>
                        </TD>           
                    </TR>
                    <TR>
                        <TD>
                            <TABLE border="0" width="100%" cellpadding="0" cellspacing="0" >
                                <TR>
                                    <TD align="left" colspan="4" class="legendtitle">
                                    <FIELDSET>

                                    <LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
                
                                    <textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width:100%" rows="1" readonly="readonly" ><%= dctkBean.getMessage() %></textarea>
                                    <% dctkBean.setMessage(""); %>
                                    </FIELDSET>
                                   </TD>
                                </TR>

                                <TR>
                                    <TD  align="left">                       
                                        <FIELDSET>
                                        <LEGEND class="legendtitle">&nbsp;Kiểm kho </LEGEND>
                                        <TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
                                        <TR> 
                                        <TH style="width:20%"></TH>
                						<TH style="width:100%"></TH>
                						</TR>
                                            <TR >
                                                <TD class="plainlabel">Chi nhánh / Đối tác</TD>
                                                <TD class="plainlabel"><%= dctkBean.getNppTen() %></TD>                                                                   
                                            </TR>

                                            <TR >
                                                <TD class="plainlabel">Ngày điều chỉnh </TD>
                                                <TD class="plainlabel">
                                                    <table border=0 cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td class="plainlabel" >
                                                            <input class="days" type="text" name="ngaydc" style="width:200px" value="<%= dctkBean.getNgaydc() %>" readonly="readonly"> </td>
                                                         </tr>

                                                    </table>                                                
                                                </TD>
                                            </TR>
											<TR>
							    				<TD class="plainlabel">Đơn vị kinh doanh</TD>
							      				<TD class="plainlabel">
								    						<SELECT   name="dvkdId"  disabled="disabled">								      
							  	 					<option value ="" ></option>
							  	 					<% try{ 
							  	 							while(dvkd.next()){ 
							  	 								if(dvkd.getString("dvkdId").equals(dctkBean.getDvkdId())){ %>
							      									<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkd") %></option>
							      						   	  <%}else{ %>
							     									<option value='<%=dvkd.getString("dvkdId")%>' ><%=dvkd.getString("dvkd")  %></option>
							     							  <%}
							  	 							}
							  	 						}catch(java.sql.SQLException e){} %>	
								     	
													</SELECT></TD>
											</TR>
											<TR>
							  					<TD class="plainlabel">Kênh bán hàng </TD>
							  					<TD class="plainlabel">
							      					<SELECT name="kbhId" disabled="disabled" >								      
							  	 						<option value ="" ></option>
							  	 					<% try{ while(kbh.next()){ 
							    							if(kbh.getString("kbhId").equals(dctkBean.getKbhId())){ %>
							      								<option value='<%= kbh.getString("kbhId")%>' selected><%=kbh.getString("kbh") %></option>
							      						  <%}else{ %>
							     								<option value='<%= kbh.getString("kbhId")%>'><%= kbh.getString("kbh") %></option>
							     						<%}}}catch(java.sql.SQLException e){} %>	
								     	
                               						</SELECT>
                               					</TD>
								  			</TR>

                                			<TR>
                                 				 <TD class="plainlabel">Kho</TD>
								  				 <TD class="plainlabel"><SELECT name="khoId"  disabled="disabled">
								  					<option value="" > </option>
												<%  try{
								  						while(kho.next()){								  			
								  							if (dctkBean.getKhoId().equals(kho.getString("khoId"))){ %>								  			
								  								<option value="<%= kho.getString("khoId")%>" selected><%= kho.getString("ten")%></option>
								  		  				  <%}else{ %>		
								  		  						<option value="<%= kho.getString("khoId")%>" ><%= kho.getString("ten")%></option>
								  						  <%}
								  					    }
								  						}catch (java.sql.SQLException e){} %>
                                  						</SELECT></TD>

  			                              	</TR>
												<TR class="tblightrow">
                                     			<TD  class="plainlabel">Lý do điều chỉnh </TD>
                                     			<TD  class="plainlabel"><input name="lydodc" id="lydodc" type="text" value="<%= dctkBean.getLydodc()%>" style="background-color: #FFFFFF" readonly="readonly">
                                              		</TD>
                                			</TR>
                                			<TR class="tblightrow">
                                     			<TD  class="plainlabel">Giá trị điều chỉnh </TD>
                                     			<TD  class="plainlabel"><input name="ttien" id="ttien" type="text" value="<%= formatter.format(Long.parseLong(dctkBean.getGtdc())) %>" style="background-color: #FFFFFF" readonly="readonly">
                                              		VND </TD>
                                			</TR>
                                			<TR class="tblightrow">
                                     			<TD  class="plainlabel">Bằng chữ </TD>
                                     			<TD  class="plainlabel"> <span id="lblDocSo" style="font-weight:bold;font-style:italic;"></span> </TD>
                                			</TR>
                                          
                               			</TABLE>
                           			</FIELDSET>

                                  </TD>
                               </TR>    
                             <TR>
											<TD>
												<fieldset>
													<TABLE width=100% cellpadding="1" border="1" style="border-color: #80CB9B" cellspacing="0">
														<TR class="tbheader">
															<TH rowspan="2" width="10%">Mã sản phẩm</TH>
															<TH rowspan="2" width="20%">Tên sản phẩm</TH>
															<TH width="10%" colspan="2">Tồn hiện tại</TH>
															<TH width="15%" rowspan="2">Số Lô</TH>
															<TH width="10%" colspan="2">Tồn thực tế</TH>
															<TH width="10%" rowspan="2">Điều chỉnh</TH>
															<TH width="15%" rowspan="2">Gía mua TB</TH>
															<TH width="20%" rowspan="2">T. tiền (w/o VAT)</TH>
														</TR>
														<TR class="tbheader">
															<TH width="5%">Thùng</TH>
															<TH width="5%">Lẻ</TH>
															<TH width="5%">Thùng</TH>
															<TH width="5%">Lẻ (quy đổi)</TH>
														</TR>
														<% 
               								String lightrow = "tblightrow";
                                            String darkrow = "tbdarkrow";
                                            int size = dctkBean.getSize();
                                            int m= 0;
                                            if(masp!= null){
                                            for(int i=0; i < size; i++){ 
												if(m%2 == 0){					%>
														<TR class=<%=lightrow%>>
															<% 	}else{%>
														
														<TR class=<%=lightrow%>>
															<%	} %>

															<TD>
																<input type="text" name="masp" id='<%="masp" + masp[i] %>'  readonly="readonly"  value='<%=masp[i]%>' readonly="readonly" style="text-align: left; width: 100%; border: 0">
															</TD>
															<TD>
																<input type="text" name="tensp" value='<%=tensp[i]%>' readonly="readonly" style="text-align: left; width: 100%; border: 0">
															</TD>
															<TD>
																<input type="text" name='tonthung'  id='tonthung<%=i%>'  value="<%= tonthung[i] %>" style="width: 100%; text-align: right; border: 0">
															</TD>
															<TD>
																<input type="text" name='tonle' value="<%= tonle[i] %>"   style="width: 100%; text-align: right; border: 0"   >
															</TD>
															<TD>
																<input type="text" name='solo' id='solo<%=i%>' value="<%= solo[i] %>" readonly="readonly"  style="width: 100%; text-align: left; border: 0">
															</TD>
															<TD>
																<input type="text" name='soluongthung' id="soluongthung_<%=i %>" value="<%= soluongthung[i] %>" style="width: 100%; text-align: right; border: 1"  onChange="QuyRaLe(<%=i %>);setTTien();" style="width:100% ;text-align: left;" onKeyPress="return submitenter(this,event);">
															</TD>
															<TD>
																<input type="text" name='soluongle' id="soluongle_<%=i %>" value="<%= soluongle[i] %>" style="width: 100%; text-align: right; border: 1" onChange="QuyRaThung(<%=i %>);setTTien();" style="width:100% ;text-align: left;"onKeyPress="return submitenter(this,event);">
															</TD>
															<TD>
																<input type="text" name='dieuchinh' id='dieuchinh<%=i %>' value="<%=dc[i] %>" style="width: 100%; text-align: right;" readonly="readonly" >
															</TD>
															<TD>
																<input type="text" name='dongiathung' id="dongiathung<%=i %>" value="<%=dongiathung[i] %>" style="width: 100%; text-align: right;">
															</TD>
															<TD>
																<input type="text" name='thanhtien' id='thanhtien<%=i %>' value="<%=ttien[i] %>" style="width: 100%; text-align: right;"> 
																<input type="hidden" name='tonkho'   id='<%="tkm" + masp[i] %>' value="<%= tonkho[i] %>" style="width: 100%; text-align: left;"> 
																<input type="hidden" name="spId" value="<%= spId[i] %>"> 
																<input type="hidden" name="quycach" id="quycach_<%=i %>" value='<%=quycach[i]%>'  style="text-align: right; width: 100%;border:0">
																<input type="hidden" name='giaton' id="giaton<%=i %>" value="<%=giamua[i] %>" style="width: 100%; text-align: left;">
															</TD>
														</TR>
														<% m++; }} %>
													</TABLE>
												</fieldset>
											</TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
//setTTien();
</script>
</body>
</html>
<% 	
	try{
		if(dvkd != null)
			dvkd.close();
		if(kbh != null)
			kbh.close();
	
		if(kho != null)
			kho.close();

		if(dctkBean != null){
			dctkBean.DBclose();
			dctkBean = null;
		}			
		session.setAttribute("dctkBean",null);
		spId=null;
		masp=null;
		tensp=null;
		tonkho=null;
		dv=null;
		dc=null;
		giamua=null;
		ttien=null;
	
	}
	catch (SQLException e) {}

%>
<%}%>

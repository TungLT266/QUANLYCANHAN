<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.dondathang.IDondathang" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IDondathang ddhBean = (IDondathang)session.getAttribute("ddhBean"); %>
<% ResultSet ncc = (ResultSet) ddhBean.getNcc(); %>
<% ResultSet dvkd = (ResultSet)ddhBean.getDvkdIds(); %>
<% ResultSet kbh = (ResultSet)ddhBean.getKbhIds(); %>
<% String[] spId = ddhBean.getSpId() ; %>
<% String[] masp = ddhBean.getMasp() ; %>
<% String[] denghi = ddhBean.getDenghi(); %>
<% String[] tensp = ddhBean.getTensp(); %>
<% String[] ton = ddhBean.getTonicp(); %>
<% String[] sl = ddhBean.getSl(); %>
<% Hashtable tmasp = ddhBean.getThieuMasp() ; %>
<% String[] tienbVAT = ddhBean.getTienbVAT(); %>
<% String[] dg = ddhBean.getDongia(); %>
<% String[] dv = ddhBean.getDonvi() ; %>
<% String[] qc = ddhBean.getQuycach() ; 
	String[] dg_tuvc = ddhBean.getDongia_tuvc() ;
	String[] dg_kovc = ddhBean.getDongia_kovc() ;
 	String[] tspId = ddhBean.gettSpId() ; %>
<% String[] tdenghi = ddhBean.gettDenghi(); %>
<% String[] ttensp = ddhBean.gettTensp(); %>
<% String[] tton = ddhBean.gettTonicp(); %>
<% String[] tsl = ddhBean.gettSl(); %>
<% String[] tmasparr = ddhBean.getThieuMaspArray(); %>
<% String[] ttienbVAT = ddhBean.gettTienbVAT(); %>
<% String[] tdg = ddhBean.gettDongia(); %>
<% String[] tdv = ddhBean.gettDonvi() ; %>
<% String[] thetichsp = ddhBean.getThetich() ; %>
<% String[] khoiluongsp = ddhBean.getKhoiluong() ; %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 <HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>

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

<style type="text/css">
#tab tr td input{
cursor:default;
background-repeat: no-repeat;
background: none;
}
#tab tr:HOVER{
cursor:pointer;
background: #E2F0D9;
}
</style>

<SCRIPT type="text/javascript"> 



function setTTienbVAT(){
	
	var i = <%= ddhBean.getSize()%>;
	var masp = new Array(<%= ddhBean.getMaspstr() %>);	
	var tongtienbvat = 0;
	var tongsothung = 0;
	var tongkhoiluong = 0;
	var tongthetich = 0;
	var thuesuat=document.getElementById("thuesuat").value;
	var chietkhautructiep=document.getElementById("chietkhautructiep").value;
	
	for(var x=0; x<i; x++)
	{
		var slId = "sl" + masp[x];
		var sl= document.getElementById(slId).value;
		sl=sl.replace(/\,/g,'');
		
		if(sl.length==0) 
			sl="0";
		var dgId = "dg"  + masp[x];
		
		var khoiluong=document.getElementById("khoiluong"+ masp[x]).value;
		if(khoiluong.length==0){
			khoiluong="0";
		}
		var thetich=document.getElementById("thetich"+ masp[x]).value;
		if(thetich.length==0){
			thetich="0";
		}
		
		var qc= document.getElementById("qc"+ masp[x]).value;
		
		
		
		tongsothung=parseFloat(tongsothung)+parseFloat(sl); 
		tongkhoiluong=parseFloat(tongkhoiluong) +  parseFloat(sl) * khoiluong * parseFloat(qc)/1000; 
		
		tongthetich=parseFloat(tongthetich) +  parseFloat(sl) * thetich * parseFloat(qc)/1000000; 
		 
		var dg= document.getElementById(dgId).value;
	
		var tien = parseFloat(sl)*parseFloat(dg.replace(/\,/g,''));
		
		var tienId = "t"  + masp[x];
		 
		if(!(isNaN(tien)))
		{
			document.getElementById(tienId).value = formatCurrency(tien);
			tongtienbvat = parseFloat(tongtienbvat) + parseFloat(tien);
		}
	}
	var tienck=parseFloat(tongtienbvat)*parseFloat(chietkhautructiep)/100;
	document.getElementById("tiencktt").setAttribute("value",formatCurrency(tienck)); 	
	document.getElementById("tongtienbvat").value = formatCurrency(tongtienbvat);
	var tongtienavat =Math.round(tongtienbvat)+ Math.round(tongtienbvat*thuesuat)  ;
	 
	document.getElementById("tongthung").value = formatCurrency(tongsothung);
	document.getElementById("tongtrongluong").value =  tongkhoiluong;
	document.getElementById("tongthetich").value =  tongthetich ;
	
	var tiensauck= parseFloat(tongtienbvat)- parseFloat(tongtienbvat) *parseFloat(chietkhautructiep)/100;
	document.getElementById("tiensauck").setAttribute("value",formatCurrency(tiensauck)); 
	
	var vat = Math.round(tiensauck*thuesuat);
	document.getElementById("vat").value = formatCurrency(vat);
	tongtienavat=tiensauck+vat;
	tongtienavat=Math.round(tongtienavat);
	document.getElementById("tongtienavat").value = formatCurrency(tongtienavat);
	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtienavat) + " Đồng Việt Nam";
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



function printform()
{   
	document.forms['ddhForm'].action.value='print';	
   	document.forms["ddhForm"].submit();
}

function init()
{
	var tongtienavat= document.getElementById("tongtienavat").value;
	tongtienavat = tongtienavat.replace(',', '');
	tongtienavat = tongtienavat.replace(',', '');
	tongtienavat = tongtienavat.replace(',', '');
	tongtienavat = tongtienavat.replace(',', '');
	tongtienavat = tongtienavat.replace(',', '');
	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtienavat) + " Đồng Việt Nam";
	
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
	if(element.value== '' )
	{
		element.value= '';
	}
	setTTienbVAT();
}
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onLoad = 'init();'>
<form name="ddhForm" method="post" action="../../DondathangUpdateSvl">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%=ddhBean.getId() %>'>
<input type="hidden" name="dvkdId" value='<%=ddhBean.getDvkdId() %>'>
<input type="hidden" name="kbhId" value='<%=ddhBean.getKbhId() %>'>
<input type="hidden" id="thuesuat" name="thuesuat" value='<%=ddhBean.getThueSuat()%>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"  bgcolor="#FFFFFF">
  <TR>
        <TD colspan="4" align='left' valign='top'>

            <TABLE width="100%">
                <TR>
                    <TD align="left" class="tbnavigation">
                       <table width="100%" border="0" cellpadding="0" cellspacing="0">
                           <tr height="22">
                               <TD align="left" colspan="2" class="tbnavigation">Quản lý tồn kho &gt;&nbsp;Mua hàng từ nhà cung cấp &gt; Đặt hàng &gt; Cập nhật </TD>
      
                               <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%=ddhBean.getNppTen() %> &nbsp;</TD>

                            </tr>
                        </table>
                     </TD>
                  </TR> 
            </TABLE>

            <TABLE   width="100%" border="0" cellpadding="3" cellspacing="0">
                <TR ><TD >
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR class = "tbdarkrow">

                            <TD width="30" align="left"><A href="../../DondathangSvl?userId=<%=userId%>"  ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
                         
                            <TD width="30" align="left" ><A href="../../Erp_InDonMuaHangSvl?userId=<%=userId%>&print=<%=ddhBean.getId()%> "  ><img src="../images/Printer30.png" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
                             <TD width="2" align="left" >&nbsp;</TD>
                            <TD width="2" align="left" >&nbsp;</TD>
                            <TD width="2" align="left">&nbsp;</TD>
                            <TD width="30" align="left" ></TD>
                            <TD  align="left">&nbsp;</TD>
                            
                        </TR>
                    </TABLE>
                </TD></TR>

            </TABLE>

            <TABLE width = 100% cellpadding="0" cellspacing="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="err" cols="109" rows="1"  ><%=ddhBean.getMessage()%></textarea>
						<% ddhBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			
		 	
			<TR>
                
                <TR>
                    <TD >
                        <TABLE id="tab" border="0" width="100%" cellpadding="0" cellspacing="0">
                            <TR>
                                <TD  align="left">
                                    <FIELDSET>
									<%NumberFormat formatter = new DecimalFormat("#,###,###.00"); %>
                                    <LEGEND class="legendtitle">&nbsp;Đơn đặt hàng &nbsp;</LEGEND>
                                                <TABLE cellpadding = "3" cellspacing = "0"  width = "100%" border = 0>
                                                    <TR>
                                                        <TD width="15%" class="plainlabel">Chi nhánh / Đối tác </TD>
                                                        <TD  class="plainlabel"><%= ddhBean.getNppTen() %>                                                                                                                																								                                                  
                                                   <TD class="plainlabel">Ngày đề nghị giao hàng </TD>
                                                                    <td colspan="4" class="plainlabel"><input class="days" type="text" name="ngaydenghigiaohang" size="11" value="<%= ddhBean.getNgaydenghigh() %>"></td>
                                                   
                                                    </TR>

                            
                                                    <TR >
                                                        <TD class="plainlabel">Ngày đặt hàng </TD>
                                                        <TD class="plainlabel">
        
                                                            <table border=0 cellpadding = 0 cellspacing = 0>
                                                                <tr>
                                                                    <td class="plainlabel"><input class="days" type="text" name="ngaydh" size="11" value="<%= ddhBean.getNgaydh() %>"></td>
                                                                    
                                                                </tr>

                                                            </table>                                                
                                                        </TD>                                                                                                               
														<TD class="plainlabel">Tổng số tiền (chưa VAT) </TD>
                                                      <TD class="plainlabel"><input type="text" name="tongtienbvat" id="tongtienbvat" readonly="readonly" value="<%= formatter.format(Double.parseDouble(ddhBean.getTongtienbVAT())) %>" style="text-align: right"  >
                                             		   VND </TD>
	                                                      
	                                             	</TR>                                                  
                                                       
                                                    <TR>
	                                                    <TD  class="plainlabel">Nhà cung cấp </TD>
	                                                        <TD width="30%" class="plainlabel"> 
	                                                          <select name="nccId">                                                          	
                                                           <% try{                                                        			    	
                                                         	   if(ncc != null){
                                                         			while(ncc.next()){      
                                                            	      if (ncc.getString("nccId").equals("100046")){ %>   
                                                                	       <option value='<%=ncc.getString("nccId")%>' selected><%=ncc.getString("nccTen")%></option>
                                                                	       
                                                                   <% }else{ %>
                                                                			<option value='<%=ncc.getString("nccId")%>'><%=ncc.getString("nccTen")%></option>   		   
                                                                   <% } 
                                                                	} 
                                                                  }
                                                             }catch(java.sql.SQLException e){}  
                                                           %>
                                                          </select>                                             
                                                        </TD>
                                                         	<TD  class="plainlabel"> Chiết khấu trực tiếp (<%= formatter.format(ddhBean.getChietkhauTrucTiep()) %> %) </TD>
                                                      <TD class="plainlabel">
                                                   		<input type="hidden" name="chietkhautructiep" id="chietkhautructiep" readonly value="<%= formatter.format(ddhBean.getChietkhauTrucTiep()) %>"  style="text-align: right" >
                                                      	<input type="text" name="tiencktt" id="tiencktt" readonly  style="text-align: right" >
                                                      	VNĐ
                                                       </TD>
                                                        
                                                    </TR>
                                                    
                                                    
                                                    <TR>                                                            
														<TD width="15%" class="plainlabel">Đơn vị kinh doanh </TD>
								    					<TD class="plainlabel">
								    						<SELECT  name="dvkdId" style="width:80" onChange = "submitform();">
														  	 <%
														  	System.out.println("KHoannnddk :"+ ddhBean.getDvkdId());
														  	 try{ while(dvkd.next()){ 
															    	if(dvkd.getString("dvkdId").equals(ddhBean.getDvkdId())){ %>
								      									<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      							  <%}else{ %>
								     									<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     							  <%}}}catch(java.sql.SQLException e){} %>	
                                  							</select>
                                  						</TD>	
                                  					<TD  class="plainlabel">Tổng số tiền sau CK(chưa VAT) </TD>
                                                     	 <TD colspan="3" class="plainlabel"><input type="text" name="tiensauck" id="tiensauck" readonly="readonly" value=""  style="text-align: right" >
                                              		   VND </TD> 	
                                					
                                                    </TR>
                                                    <TR class="tblightrow">
                                                        <TD class="plainlabel">Kênh bán hàng </TD>
								    					<TD  class="plainlabel">
								    						<SELECT  name="kbhId" style="width:80" >
								  		
														  	 <% try{ while(kbh.next()){ 
															    	if(kbh.getString("kbhId").equals(ddhBean.getKbhId())){ %>
								      									<option value='<%=kbh.getString("kbhId") %>' selected><%=kbh.getString("kbh") %></option>
								      							  <%}else{ %>
								     									<option value='<%=kbh.getString("kbhId") %>'><%=kbh.getString("kbh") %></option>
								     							  <%}
															 		System.out.println("KBH: " + kbh.getString("kbhId") + ";" + kbh.getString("kbh"));   	
														  	 
														  	 }}catch(java.sql.SQLException e){} %>	
                                  							</select>
                                  						</TD>
                                  						    <TD  class="plainlabel">VAT (10%) </TD>
	                                                      <TD colspan="3" class="plainlabel"><input type="text" name="vat" id="vat"  value="<%= formatter.format(Double.parseDouble(ddhBean.getVat())) %>" readonly="readonly" style="text-align: right">
	                                             		   VND </TD> 
                                  						
                                                </TR>		
                                                
                                                    <TR class="tblightrow">
                                                        <TD class="plainlabel">Chi nhánh / Đối tác tự vận chuyển </TD>
								    					<TD  class="plainlabel">
	                                                      <% if(ddhBean.getTuVanchuyen().equals("1")) {%>
	                                                      <input type="checkbox" onchange="submitform();" checked="checked" value="1" name="tuvanchuyen"  > 
	                                                      <%}else{ %>
	                                                        <input type="checkbox"  onchange="submitform();" value="1" name="tuvanchuyen" >  
	                                                      <%} %>
	                                                       </TD>
	                                                    
                                  													
	                                                       
                                  						<TD  class="plainlabel">Tổng số tiền (có VAT) </TD>
                                                     	 <TD colspan="3" class="plainlabel"><input type="text" name="tongtienavat" id="tongtienavat" readonly="readonly" value="<%= formatter.format(Double.parseDouble(ddhBean.getTongtienaVAT())) %>"  style="text-align: right" >
                                              		   VND </TD> 	
                                                </TR>		
                                                        <TR class="tblightrow">
                                                        <TD class="plainlabel">Tổng trọng lượng</TD>
								    					<TD  class="plainlabel">
	                                                  	<input type="text" name="tongtrongluong" id="tongtrongluong" readonly="readonly" value=""  style="text-align: right" > Kg   					
                                  						<TD  class="plainlabel">Tổng thể tích </TD>
                                                     	 <TD  class="plainlabel"><input type="text" name="tongthetich" id="tongthetich" readonly="readonly" value=""  style="text-align: right" > (Mét khối)
                                              		     </TD> 	
                                              		     
                                                </TR>	
                                                <tr>
                                                		 <TD  class="plainlabel">Tổng thùng</TD>
                                                     	 <TD  class="plainlabel"><input type="text" name="tongthung" id="tongthung" readonly="readonly" value=""  style="text-align: right" >
                                              		     </TD> 
                                              		      <TD  class="plainlabel"> </TD>
                                                     	 <TD  class="plainlabel"> 
                                              		     </TD> 		
                                                </tr>
                                                       <TR class="tblightrow">
                                                      <TD  class="plainlabel">Ghi chú đơn hàng </TD>
                                                      <TD colspan="6" class="plainlabel">
                                                      	<textarea name="ghichu" id="ghichu" style="width: 100%" rows="2"  ><%=ddhBean.getghichu()%></textarea>
                                                      </TD>
                                                    <TR class="tblightrow">
                                                      <TD  class="plainlabel">Bằng chữ </TD>
                                                      <TD colspan="3" class="plainlabel"> <span id="lblDocSo" style="font-weight:bold;font-style:italic;"></span> </TD>
                                                       
                                                   </TR>
                                                 <TR style="display: none" class="tblightrow">
                                                      <TD  class="plainlabel"> </TD>
                                                      <TD colspan="3" class="plainlabel">
                                                      <% if(ddhBean.getDoiHang().equals("1")) {%>
                                                      <input type="checkbox" checked="checked" name="doihang" value="1" > Đơn đổi hàng 
                                                      <%}else{ %>
                                                        <input type="checkbox" value="1" name="doihang"  > Đơn đổi hàng 
                                                      <%} %>
                                                       </TD>
                                                       
                                                   </TR>
         												 
                                                </TABLE>
                                            
                                               <TABLE class ="tabledetail"  width = 100% cellpadding="0"  border="0" cellspacing="1" >
                                                    <TR class="tbheader" >
                                                        <TH width="10%">Mã sản phẩm </TH>
                                                        <TH width="30%">Tên sản phẩm</TH>
                                                        <TH width="10%">Đề nghị</TH>
                                                        <TH width="10%">Đặt hàng</TH>
                                                        <TH width="10%">Đơn vị</TH>
                                                        <TH width="10%">Đơn giá </TH>
                                                        <TH width="10%">T. tiền (w/o VAT) </TH>

                                                    </TR>

															                                                    
                                    		<% 

               								String lightrow = "tblightrow";
                                            String darkrow = "tbdarkrow";
                                            int size = new Integer(ddhBean.getSize()).intValue();
                                            int m= 0;
                                              
                                    		for(int i=0;i< size ;i++){ %> 						
													<TR class= <%=lightrow%> >
													<% if(!tmasp.containsKey(masp[i])){ %>
                                                       <TD><input type="text"  name="ma" value='<%= masp[i] %>' size = 17 readonly="readonly"> </TD>
                                                       <TD><input type="text"  name="ten" value='<%= tensp[i] %>' size = 45 readonly="readonly"></TD>
													   <TD><input type="text"  name="denghi" value= <%= formatter.format(Double.parseDouble(denghi[i])) %> size = 10 readonly="readonly" style="text-align: right"></TD>													   
                                                       <TD><input type="text" name='<%="sl"+masp[i]%>'  value='<%= formatter.format(Double.parseDouble(sl[i])) %>' id='<%= "sl"+masp[i]%>' size = 10 onkeyup="Dinhdang(this)" size = "10" style="text-align: right"> </TD>
                                                       <TD><input type="text"  name='<%="dv"+masp[i]%>' value='<%=dv[i]%>' size=10 readonly="readonly" style="text-align: center"></TD>
                    								   <TD> 
                    								   
                    								      <input type="hidden"  name='<%="dg_kovc"+masp[i]%>'  value='<%= formatter.format(Double.parseDouble(dg_kovc[i])) %>' id='<%= "dg_kovc"+masp[i]%>' size = 10 readonly="readonly" style="text-align: right">
                       								   <input type="hidden"  name='<%="dg_tuvc"+masp[i]%>'  value='<%= formatter.format(Double.parseDouble(dg_tuvc[i])) %>' id='<%= "dg_tuvc"+masp[i]%>' size = 10 readonly="readonly" style="text-align: right">
                       								   <% 
                       								   	String dongia="0";
                       								   
                       							
                       								   
                       								   if(ddhBean.getTuVanchuyen().equals("1")) {
                       								   		 dongia=formatter.format(Double.parseDouble(dg_tuvc[i]));
                       								   	 }else{
                       									   	dongia=	formatter.format(Double.parseDouble(dg_kovc[i]));
                       								   	 }
                       								   
                       									   %>
                       								   
                       								   <input type="text"  name='<%="dg"+masp[i]%>'  value='<%=dongia%>' id='<%= "dg"+masp[i]%>' size = 10 readonly="readonly" style="text-align: right"> </TD>
                    								   
                                                       <TD><input type="text"  name='<%= "t"+ masp[i]%>' value= '<%= formatter.format(Double.parseDouble(tienbVAT[i])) %>' id='<%= "t"+ masp[i]%>' size = 15 readonly="readonly" style="text-align: right">  																						
                                                       <input type="hidden"  name="spId" value='<%= spId[i] %>' > 
                                                         <input type="hidden"  name='<%="khoiluong"+masp[i]%>' id='<%="khoiluong"+masp[i]%>' value='<%= khoiluongsp[i] %>' > 
                                                           <input type="hidden"  name='<%="thetich"+masp[i]%>' id='<%="thetich"+masp[i]%>'value='<%= thetichsp[i] %>' > 
                                                       <input type="hidden"  name="masp" value='<%= masp[i] %>' size = 17 readonly="readonly"> 
                                                       <input type="hidden"  name="tensp" value='<%= tensp[i] %>' size = 45 readonly="readonly">                                                        
                                                       <input type="hidden"  name='<%="dv"+masp[i]%>' value='<%= dv[i] %>' size=10 readonly="readonly" style="text-align: center">
                       								   <input type="hidden"  name='<%="dg"+masp[i]%>'  value='<%= formatter.format(Double.parseDouble(dg[i])) %>' id='<%= "dg"+masp[i]%>' size = 10 readonly="readonly" style="text-align: right">
                       								   <input type="hidden"  name='<%="ton"+masp[i]%>' value='<%= ton[i] %>' id='<%="ton"+masp[i]%>' >
                       								   <input type="hidden"  name='<%="qc"+masp[i]%>' value='<%= qc[i] %>' id='<%="qc"+masp[i]%>' >                                       
												    	 </TD>	
												     <%} %>
                                                  </TR>
                                            <% } %>
                                                    
                                                   
                                                </TABLE>
                                               </FIELDSET>
                                            </TD>
                                    </TR>

                                   
                                </TABLE>
                                
                        </TD>
                    </TR>   
            </TABLE>
    </td>

  </tr>
</table>
<script type="text/javascript">
	setTTienbVAT();
</script>

</form>
</body>
</html>

<% if(!(ncc == null)) ncc.close(); %>
<% if(!(dvkd == null)) dvkd.close(); %>
<% if(!(kbh == null)) kbh.close(); %>
<%
if(ddhBean != null){
	ddhBean.DBclose();
	ddhBean = null;
} %>
<%}%>
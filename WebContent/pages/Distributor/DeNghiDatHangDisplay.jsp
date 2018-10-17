<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.denghidathang.IDenghidathang" %>
<%@ page  import = "geso.traphaco.distributor.beans.denghidathang.imp.Denghidathang" %>
<%@ page  import = "java.util.Hashtable"%>
<%@ page  import = "java.util.List"%>
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




<% IDenghidathang dndhBean = (IDenghidathang)session.getAttribute("dndhBean"); %>
<% ResultSet ncc = (ResultSet) dndhBean.getNcc(); %>
<% ResultSet dvkd = (ResultSet)dndhBean.getDvkdIds(); %>
<% ResultSet kbh = (ResultSet)dndhBean.getKbhIds(); %>

<% String[] masp = dndhBean.getMasp() ; %>
<% String[] tensp = dndhBean.getTensp(); %>
<% String[] tondau = dndhBean.getTondau(); %>
<% String[] toncuoi = dndhBean.getToncuoi(); %>
<% String[] ban = dndhBean.getBan(); %>
<% String[] tbban = dndhBean.getTbban(); %>
<% String[] tonngay = dndhBean.getTonngay(); %>
<% String[] dukien = dndhBean.getDukien(); %>
<% String[] sl = dndhBean.getSl(); %>
<% String[] tienbVAT = dndhBean.getTienbVAT(); %>
<% String[] dg = dndhBean.getDongia() ; %>
<% String[] dadat = dndhBean.getDadathang() ; %>
<% String[] conlai = dndhBean.getConlai() ; %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.util.Hashtable;"%>
<HTML>
<HEAD>
<TITLE>Acecook - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<SCRIPT src="../js/md5.js" type="text/javascript" language="javascript">
</SCRIPT>
<SCRIPT src="../js/scripts.js" type="text/javascript"
    language="javascript">
</SCRIPT>
<SCRIPT src="../js/commun.js" type="text/javascript"
    language="javascript">
</SCRIPT>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery-1.3.2.min.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
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
	 	
	var i = <%= dndhBean.getSize()%>;
	var masp = new Array(<%= dndhBean.getMaspstr() %>);	
	var tongtienbvat = 0;
	
	for(x=0; x<i; x++){
		var slId = "sl" + masp[x];
	
		var sl= document.getElementById(slId).value;
		sl.replace(/^\s+|\s+$/, '');
		if(sl.length==0) 
			sl="0";
		
		var dgId = "dg"  + masp[x];
		
		var dg= document.getElementById(dgId).value;
	
		var tien = parseFloat(sl)*parseFloat(dg);
		
		var tienId = "t"  + masp[x];
				
		if(!(isNaN(tien))){
			document.getElementById(tienId).setAttribute("value", formatCurrency(tien));
			tongtienbvat = parseFloat(tongtienbvat) + parseFloat(tien);
		}
	}
	document.getElementById("tongtienbvat").setAttribute("value", formatCurrency(tongtienbvat)); 
	var vat = Math.round(tongtienbvat*0.1);
	document.getElementById("vat").setAttribute("value", formatCurrency(vat));
	var tongtienavat = Math.round(tongtienbvat*1.1);
	document.getElementById("tongtienavat").setAttribute("value", formatCurrency(tongtienavat));
	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtienavat) + " Đồng Việt Nam";
		
}
      
function init()
{
	var tongtienavat= document.getElementById("tongtienavat").value;
	tongtienavat = tongtienavat.replace(',', '').replace(',', '');
	tongtienavat = tongtienavat.replace(',', '').replace(',', '');
	tongtienavat = tongtienavat.replace(',', '').replace(',', '');
	tongtienavat = tongtienavat.replace(',', '').replace(',', '');
	tongtienavat = tongtienavat.replace(',', '').replace(',', '');
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

function chotform()
{  document.forms['dndhForm'].action.value='chot';
   document.forms["dndhForm"].submit();
}

 function saveform()
{
	document.forms['dndhForm'].action.value='save';
    document.forms["dndhForm"].submit();
}

function printform()
{   
	
	document.getElementById("btnPrint").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

	
   document.forms['dndhForm'].action.value='print';	
   document.forms["dndhForm"].submit();
}

</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onLoad="init();">
<form name="dndhForm" method="post" action="../../DenghidathangDisplaySvl">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="nppId" type="hidden" value='<%=dndhBean.getNppId() %>' size="30">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%=dndhBean.getId() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD align='left' valign='top'> <!--begin body Dossier-->

            <TABLE width="100%">
                <TR>
                    <TD align="left" class="tbnavigation">
                       <table width="100%" border="0" cellpadding="0" cellspacing="0">
                           <tr height="22">
                               <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tồn kho &gt; Mua hàng từ NCC &gt; Đề nghị đặt hàng &gt; Hiển thị
                               <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%=dndhBean.getNppTen() %>&nbsp;&nbsp; </TD>

                            </tr>
                        </table>
                     </TD>
                  </TR> 
            </TABLE>

            <TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
                <TR ><TD >
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR class = "tbdarkrow">

                            <TD width="30" align="left"><A href="../../DenghidathangSvl?userId=<%=userId%>"  ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset"></A></TD>
                            <TD width="2" align="left" >&nbsp;</TD>
                            <TD width="30" align="left" >
                            <div id="btnPrint">
                            <A href="javascript: printform()" ><img src="../images/Printer30.png" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A>
                            </div>
                            </TD>
                            <TD  align="left">&nbsp;</TD>
                        </TR>
                    </TABLE>
                </TD></TR>

            </TABLE>

            <TABLE width = 100% cellpadding="0" cellspacing="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="err" cols="152" rows="1" ><%=dndhBean.getMessage()%></textarea>
						<% dndhBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			
		 	
			<TR>
                
                <TR>
                    <TD >
                        <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                            <TR>
                                <TD  align="left">
                                    <FIELDSET>
									<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
                                    <LEGEND class="legendtitle">&nbsp;Đề nghị đặt hàng &nbsp;</LEGEND>
                                                <TABLE cellpadding = 4 cellspacing = 0 width = "100%" border = 0>
                                                    <TR>
                                                        <TD width="15%" class="plainlabel">Chi nhánh / Đối tác </TD>
                                                        <TD colspan="3" class="plainlabel"><%= dndhBean.getNppTen() %></TD>                                                   
                                                    </TR>

                            
                                                    <TR >
                                                        <TD class="plainlabel">Ngày đề nghị </TD>
                                                        <TD width="30%" class="plainlabel">
        
                                                            <table border=0 cellpadding = 0 cellspacing = 0>
                                                                <tr>
                                                                    <td class="plainlabel"><input type="text" name="ngaydn" size="11" value="<%= dndhBean.getNgaydn() %>" readonly="readonly"></td>
                                                                    <td>&nbsp;</td>
                                                                </tr>

                                                            </table>                                                
                                                        </TD>
                                                        
                                                        <TD  class="plainlabel">VAT (%) </TD>
                                                      	<TD colspan="3"  class="plainlabel"><input type="text" name="vat" id="vat"  value="<%= formatter.format(Double.parseDouble(dndhBean.getVat())) %>"   style="text-align: right" readonly="readonly">
                                              			 VND</TD>                                                                                                                                                                      	                                                
																				
                                                    </TR>
                                                    <TR class="tblightrow">
                                                        <TD  class="plainlabel">Nhà cung cấp </TD>
                                                        <TD  class="plainlabel"> 
                                                          <select name="nccId" disabled="disabled" >
                                                          	
                                                           <% try{ %>
                                                       			    	
                                                         	<%   if(!(ncc == null)){
                                                         			while(ncc.next()){      
                                                            	      if (ncc.getString("nccId").equals(dndhBean.getNccId())){ %>   
                                                                	       <option value='<%=ncc.getString("nccId")%>' selected><%=ncc.getString("nccTen")%></option>
                                                                	       
                                                                   <% }else{ %>
                                                                			<option value='<%=ncc.getString("nccId")%>'><%=ncc.getString("nccTen")%></option>   		   
                                                                 	<%} 
                                                                	} 
                                                                  }%>
                                                            <% }catch(java.sql.SQLException e){   %>
                                                                    <%  }  %>
                                                          </select>                                             
                                                        </TD>
                                                        
                                                        <TD  class="plainlabel">Tổng số tiền (chưa VAT) </TD>
                                                      	<TD  class="plainlabel"><input type="text" name="tongtienbvat" id="tongtienbvat"   value="<%= formatter.format(Double.parseDouble(dndhBean.getTongtienbVAT())) %>" style="text-align: right"  readonly="readonly">
                                              			 VND</TD>                                              			                                               			
                                                    </TR>
                                                    
                                                    <TR class="tblightrow">
	                                                    <TD width="15%" class="plainlabel">Đơn vị kinh doanh </TD>
									    					<TD class="plainlabel">
									    						<SELECT  name="dvkdId" style="width:100px"  disabled="disabled">
									  		
															  	 <% try{ while(dvkd.next()){ 
																    	if(dvkd.getString("dvkdId").equals(dndhBean.getDvkdId())){ %>
									      									<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
									      							  <%}else{ %>
									     									<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
									     							  <%}}}catch(java.sql.SQLException e){} %>	
	                                  							</select>
	                                  					</TD>
	                                  						                                  					
                                                      	<TD  class="plainlabel">Tổng số tiền (có VAT) </TD>
                                                      	<TD colspan="3" class="plainlabel"><input type="text" name="tongtienavat" id="tongtienavat"   value="<%= formatter.format(Double.parseDouble(dndhBean.getTongtienaVAT())) %>"  style="text-align: right" readonly="readonly">
                                              			 VND</TD>                                                                                                                 					
                                                    </TR>
                                                    
                                                    <TR class="tblightrow">
	                                                    <TD class="plainlabel">Kênh bán hàng </TD>
									    					<TD colspan="3" class="plainlabel">
									    						<SELECT  name="kbhId" style="width:100px" disabled="disabled">
									  		
															  	 <% try{ while(kbh.next()){ 
																    	if(kbh.getString("kbhId").equals(dndhBean.getKbhId())){ %>
									      									<option value='<%=kbh.getString("kbhId") %>' selected><%=kbh.getString("kbh") %></option>
									      							  <%}else{ %>
									     									<option value='<%=kbh.getString("kbhId") %>'><%=kbh.getString("kbh") %></option>
									     							  <%}}}catch(java.sql.SQLException e){} %>	
	                                  							</select>
	                                  					</TD>									
													</TR>																									        											                                                   
                                                   
                                                   
                                                   
                                                   
                                                   <TR class="tblightrow">
                                                      <TD  class="plainlabel">Bằng chữ </TD>
                                                      <TD colspan="3" class="plainlabel"> <span id="lblDocSo" style="font-weight:bold;font-style:italic;"></span> </TD>

                                                   </TR>                                                           
                                                </TABLE>
                                            
                                                <TABLE class="tabledetail" id="tab" width = 100% cellpadding="0"  border="0" cellspacing="1" >
                                                    <TR class="tbheader" >
                                                        <TH width="6%">Mã sản phẩm </TH>
                                                        <TH width="14%">Tên sản phẩm</TH>
                                                        <TH width="3%">Tồn đầu(L) </TH>
                                                        <TH width="3%">Tồn cuối(L) </TH>
                                                        <TH width="3%">Bán/tuầnn(L) </TH>
                                                        <TH width="3%">TB bán/ngày(L) </TH>                                                        
                                                        <TH width="3%">Tồn ngày(L) </TH>
                                                        <TH width="5%">Dự kiến bán(L) </TH>
                                                        <TH width="3%">Đề nghị đặt(T) </TH>
                                                         </TR>

															                                                    
                                    		<% 

               								String lightrow = "tblightrow";
               								
                                            int size = new Integer(dndhBean.getSize()).intValue();
                                            NumberFormat decimal = new DecimalFormat("#.##"); 
                                            
                                    		for(int i=0;i< size ;i++){ 			%>			
													<TR class= <%=lightrow%> >         
                                                       <TD><input type="text"  name="masp" value='<%= masp[i] %>' size = 15  > </TD>
                                                       <TD><input type="text"  name="tensp" value='<%= tensp[i] %>' size = 40  ></TD>
                                                       <TD><input type="text" name='<%="td"+masp[i]%>'  value='<%= tondau[i] %>' id='<%= "td"+masp[i]%>'  size = '8' style="text-align: right"  > </TD>
                                                       <TD><input type="text" name='<%="tc"+masp[i]%>'  value='<%= toncuoi[i] %>' id='<%= "tc"+masp[i]%>'  size = '8' style="text-align: right"  > </TD>
                                                       <TD><input type="text" name='<%="ban"+masp[i]%>'  value='<%= formatter.format(Double.valueOf(ban[i]).doubleValue()) %>' id='<%= "ban"+masp[i]%>'  size = '10' style="text-align: right"  > </TD>
													   <TD><input type="text" name='<%="tbban"+masp[i]%>'  value='<%= formatter.format(Double.valueOf(tbban[i]).doubleValue()) %>' id='<%= "tbban"+masp[i]%>' size = '10' style="text-align: right"  > </TD>
													   
													   <% if (tonngay[i].equals("NaN")){ %>
													   		<TD><input type="text" name='<%="tn"+masp[i]%>'  value='<%= tonngay[i] %>' id='<%= "tn"+masp[i]%>'  size = '10' style="text-align: right"  > </TD>
													   <%}else{ %>
													   		<TD><input type="text" name='<%="tn"+masp[i]%>'  value='<%= formatter.format(Double.valueOf(tonngay[i]).doubleValue()) %>' id='<%= "tn"+masp[i]%>'  size = '10' style="text-align: right"  > </TD>                                  
													   <%}%>
													   
													   <TD><input type="text" name='<%="dk"+masp[i]%>'  value='<%= formatter.format(Double.valueOf(dukien[i]).doubleValue()) %>' id='<%= "dk"+masp[i]%>'  size = '9' style="text-align: right"  > </TD>
                                                       <TD><input type="text" name='<%="sl"+masp[i]%>'  value='<%= sl[i] %>' id='<%= "sl"+masp[i]%>'  size = '8' style="text-align: right"  >                                                     
                       								   <input type="hidden"  name='<%="dg"+masp[i]%>'  value='<%= formatter.format(Double.parseDouble( dg[i])) %>' id='<%= "dg"+masp[i]%>' size = '6'   style="text-align: right"> 
                                                       <input type="hidden"  name='<%= "t"+ masp[i]%>' value= '<%= formatter.format(Double.parseDouble(tienbVAT[i])) %>' id='<%= "t"+ masp[i]%>' size = 10   style="text-align: right"> 																						
												       <input type="hidden"  name='<%= "dd"+ masp[i]%>' value= '<%= formatter.format(Double.parseDouble(dadat[i])) %>' id='<%= "dd"+ masp[i]%>' size = 10   style="text-align: right">  
												       <input type="hidden"  name='<%= "cl"+ masp[i]%>' value= '<%= formatter.format(Double.parseDouble(conlai[i])) %>' id='<%= "cl"+ masp[i]%>' size = 10   style="text-align: right">        </TD>                                  
                                                  </TR>
                                            <%} %>
                                                    
                                                   
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

</form>
</body>
</html>

<% if(!(ncc == null))  ncc.close(); %>
<% if(!(dvkd == null)) dvkd.close(); %>
<% if(!(kbh == null)) kbh.close(); %>
<% if(dndhBean != null){
	dndhBean.DBclose();
	dndhBean = null;
}} %>

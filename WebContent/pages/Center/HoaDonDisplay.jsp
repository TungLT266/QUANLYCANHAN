<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page  import = "geso.traphaco.center.beans.hoadon.IHoaDon" %>
<%@ page  import = "java.sql.ResultSet" %>
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

<% IHoaDon nhaphang = (IHoaDon)session.getAttribute("hoadon"); 
NumberFormat decimal = new DecimalFormat("#.##"); 
%>
<% String id = nhaphang.getId();   %>
<% String sohoadon = nhaphang.getSohoadon(); %>
<% ResultSet ncc = (ResultSet) nhaphang.getNcc(); %>
<% ResultSet dvkd = (ResultSet)nhaphang.getDvkdIds(); %>
<% ResultSet kbh = (ResultSet)nhaphang.getKbhIds(); %>
<% ResultSet kho = (ResultSet)nhaphang.getKhoIds(); %>
<% String[] masp = nhaphang.getMasp() ; %>
<% String[] tensp = nhaphang.getTensp(); %>
<% String[] sl = nhaphang.getSl(); %>
<% String[] tienbVAT = nhaphang.getTienbVAT(); %>
<% String[] giamua = nhaphang.getGiamua() ; %>
<% String[] donvi = nhaphang.getDonvi() ; %>
<% String[] slhoadon = nhaphang.getSLHOADON() ; %>
<% String[] quycachsp = nhaphang.getQUYCACHSP() ; %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.js"></script> 
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
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
<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
   document.forms["nhaphangForm"].submit();
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
	document.getElementById("sohoadon").focus();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onLoad='init();'>
<form id="nhaphangForm" name="nhaphangForm" method="post" action="../../HoaDonUpdateSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="id" value='<%=id %>'>
<input type="hidden" name="action" value='nhaphang'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD colspan="4" align='left' valign='top'> 
        
        <TABLE width="100%" cellspacing="0" cellpadding="0">
        	<TR>
            	<TD>
                	<TABLE width="100%" cellspacing="1" cellpadding="0">
                    	<TR>
                        	<TD align="left" class="tbnavigation">
                            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr height="22">
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý Bán hàng &gt; &nbsp;Hóa đơn
                                   
                                        <TD colspan="2" align="right" class="tbnavigation">Chào mừng nhân viên <%= nhaphang.getNppTen() %> &nbsp;</TD>
                                    </tr>
                                  </table>
                              </TD>
                         </TR> 
                     </TABLE>
		            <TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
        		        <TR ><TD >
                		    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        		<TR class = "tbdarkrow">

                            		<TD width="30" align="left"><A href="../../HoaDonSvl?userId=<%=userId%>"  ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
                            		<TD width="2" align="left" >&nbsp;</TD>
                            		<TD width="30" align="left" >
                            		<!-- <div id="btnPrint">
                            		<A href="javascript: printform()" ><img src="../images/Printer30.png" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A>
                            		</div> -->
                            		</TD>
                            		<TD  align="left">&nbsp;</TD>
                        		</TR>
                    		</TABLE>
                		</TD></TR>

		            </TABLE>
                    
                     <TABLE border="0" width="100%" cellspacing = 0 cellpadding = 0>
               			 <tr>
                    		<TD align="left" colspan="4" class="legendtitle">
                        		<FIELDSET>
                        		<LEGEND class="legendtitle">Báo lỗi nhập liệu  </LEGEND>              
                        			<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width:100%" readonly="readonly" rows="1" ><%= nhaphang.getMessage() %></textarea>
                        			<% nhaphang.setMessage(""); %>
                        		</FIELDSET>
                   			</TD>
                		</tr>

                        <TR>
                            <TD width="100%" align="left">
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;Nhận hàng&nbsp;</LEGEND>

                                <%-- <TABLE  width="100%" cellpadding="4" cellspacing="0">
                                <TR>
                                        <TD width="19%" class="plainlabel">Chi nhánh / Đối tác </TD>
                                      	<TD class="plainlabel">
                                        	<input type="text" id="nppTen" name="nppTen" value="<%= nhaphang.getNppTen() %>" size=40 readonly="readonly">                                     		
                                      	</TD>
                                      	                                      	 
										<TD width="17%" class="plainlabel">Đơn vị kinh doanh </TD>
				    					<TD class="plainlabel">
				    						<SELECT  name="dvkdId"  readonly="readonly">
								  		
									  	 <% try{ while(dvkd.next()){ 
										    	if(dvkd.getString("dvkdId").equals(nhaphang.getDvkdId())){ %>
													<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
											  <%}else{ %>
													<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
										  <%}}}catch(java.sql.SQLException e){} %>	
                    						</select>
                    					</TD>							 						 
                                </TR>
								<TR>
										<TD class="plainlabel" >Ngày chứng từ </TD>
									  	<TD class="plainlabel" >
											<input type="text" name="tungay" value="<%= nhaphang.getNgaychungtu() %>" size="20" readonly="readonly">
										</TD>
																				  
										<TD class="plainlabel">Kênh bán hàng </TD>
				    					<TD class="plainlabel">
				    						<SELECT  name="kbhId" readonly="readonly">
								  		
									  	 <%
									  	 if(nhaphang.getKbhId()==null){
									  		 nhaphang.setKbhId("");
									  	 }
									  		 
									  	 try{
									  		 while(kbh.next()){ 
										    	if(kbh.getString("kbhId").equals(nhaphang.getKbhId())){ %>
													<option value='<%=kbh.getString("kbhId") %>' selected><%=kbh.getString("kbh") %></option>
											  <%}else{ %>
													<option value='<%=kbh.getString("kbhId") %>'><%=kbh.getString("kbh") %></option>
										  <%}}
									  		 }catch(java.sql.SQLException e){} %>	
                    						</select>
                    					</TD>						 						   
								</TR>
								<TR>
										<TD class="plainlabel" >Ngày nhận hàng </TD>
									  	<TD class="plainlabel" >
											<input type="text" name="ngaynhanhang" value="<%= nhaphang.getNgaynhanhang() %>" size="20" readonly="readonly">
										</TD>
																				
										<TD class="plainlabel">Nhập vào kho </TD>
				    					<TD class="plainlabel">
				    						<SELECT  name="khoId" >
								  				<option value="0">&nbsp;</option>
									  	 <% try{ while(kho.next()){ 
										    	if(kho.getString("khoId").equals(nhaphang.getKhoId())){ %>
													<option value='<%=kho.getString("khoId")%>' selected><%=kho.getString("kho") %></option>
											  <%}else{ %>
													<option value='<%=kho.getString("khoId") %>'><%=kho.getString("kho") %></option>
										  <%}}}catch(java.sql.SQLException e){} %>	
                    						</select>
                    					</TD>							
								</TR>

                                <TR class="tblightrow">
                                         <TD class="plainlabel">Nhà cung cấp  </TD>
                                         <TD colspan = "5" class="plainlabel"> 
                                                 <select name="nccId" readonly="readonly">
                                                          	
                                                  <% try{ %>
                                                       			    	
                                                	<%   if(ncc != null){
                                                  			while(ncc.next()){      
                                                       	      if (ncc.getString("nccId").equals("100046")){ %>   
                                                           	       <option value='<%=ncc.getString("nccId")%>' selected><%=ncc.getString("nccTen")%></option>
                                                                	       
                                                           <% }else{ %>
                                                         			<option value='<%=ncc.getString("nccId")%>'><%=ncc.getString("nccTen")%></option>   		   
                                                           	<%} 
                                                       	    } 
                                                          }%>
                                                 <% }catch(java.sql.SQLException e){  } %>
                                                                    
                                                 </select>                                             
                                       </TD>

                              </TR>
							 
        									<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
                                                    <TR class="tblightrow">
                                                      <TD  class="plainlabel">Tổng số tiền(chua VAT) </TD>
                                                      <TD colspan="3" class="plainlabel"><input type="text" name="tongtienbvat" id="tongtienbvat" readonly="readonly" value="<%= formatter.format(Float.parseFloat(nhaphang.getTongtienbVAT())) %>" style="text-align: right"  >
                                              VND </TD>
                                                    </TR>
        
                                                    <TR class="tblightrow">

                                                      <TD  class="plainlabel">VAT (10%) </TD>
                                                      <TD colspan="3" class="plainlabel"><input type="text" name="vat" id="vat"  value="<%= formatter.format(Float.parseFloat(nhaphang.getTongVat())) %>" readonly="readonly" style="text-align: right">
                                              &nbsp;</TD>
                                                    </TR>
                                                    
                                                    <TR class="tblightrow">
                                                      <TD  class="plainlabel">Tổng số tiền (co VAT) </TD>
                                                      <TD colspan="3" class="plainlabel"><input type="text" name="tongtienavat" id="tongtienavat" readonly="readonly" value="<%= formatter.format(Float.parseFloat(nhaphang.getTongtienaVAT())) %>"  style="text-align: right" >
                                              VND </TD>

                                                   </TR>

                                                    <TR class="tblightrow">
                                                      <TD  class="plainlabel">Bằng chữ </TD>
                                                      <TD colspan="3" class="plainlabel"> <span id="lblDocSo" style="font-weight:bold;font-style:italic;"></span> </TD>

                                                   </TR>
        
                                                    <TR class="tblightrow">
                                                      <TD  class="plainlabel">Nhập số hóa đơn </TD>
                                                      <TD colspan="3" class="plainlabel"> <input type="text" name="sohoadon" id="sohoadon" value="<%=nhaphang.getSohoadon()%>"  style="text-align: right" >

                                                   </TR> --%>
                                                   
                                                   <%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
                                <TABLE  width="100%" cellpadding="4" cellspacing="0">
                                <TR>
                                        <TD width="15%" class="plainlabel">Chi nhánh / Đối tác </TD>
                                      	<TD colspan="5" class="plainlabel"><%= nhaphang.getNppTen() %>
                                      	<%-- <TD class="plainlabel">
                                        	<input type="text" id="nppTen" name="nppTen" value="<%= nhaphang.getNppTen() %>" size=40 readonly="readonly"> --%>                                     		
                                      	</TD>
                                      	                                      	
																		
                                 </TR>

								 <TR class="tblightrow">
                                         <TD  class="plainlabel">Nhà cung cấp  </TD>
                                         <TD  class="plainlabel"> 
                                                 <select name="nccId" readonly="readonly">
                                                          	
                                                  <% try{ %>
                                                       			    	
                                                	<%   if(ncc != null){
                                                  			while(ncc.next()){      
                                                       	      if (ncc.getString("nccId").equals("100046")){ %>   
                                                           	       <option value='<%=ncc.getString("nccId")%>' selected><%=ncc.getString("nccTen")%></option>
                                                                	       
                                                           <% }else{ %>
                                                         			<option value='<%=ncc.getString("nccId")%>'><%=ncc.getString("nccTen")%></option>   		   
                                                           	<%} 
                                                       	    } 
                                                          }%>
                                                 <% }catch(java.sql.SQLException e){  } %>
                                                                    
                                                 </select>                                             
                                       </TD>
                                        
                                        <TD width="15%" class="plainlabel" >Ngày chứng từ </TD>
									  	<TD class="plainlabel" >
											<input type="text" name="tungay" value="<%=nhaphang.getNgaychungtu() %>" size="20" readonly="readonly">
										</TD>                                      
																		
                                  </TR>
								
								 <TR>     	                                      	
										<TD class="plainlabel">Đơn vị kinh doanh </TD>
				    					<TD class="plainlabel">
				    						<SELECT  name="dvkdId"  readonly="readonly">
								  		
									  	 <% try{ while(dvkd.next()){ 
										    	if(dvkd.getString("dvkdId").equals(nhaphang.getDvkdId())){ %>
													<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
											  <%}else{ %>
													<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
										  <%}}}catch(java.sql.SQLException e){} %>	
                    						</select>
                    					</TD>
                    					
                    					<TD class="plainlabel" >Ngày nhận hàng </TD>
									  	<TD class="plainlabel" >
											<input type="text" name="ngaynhanhang" value="<%= nhaphang.getNgaynhanhang() %>" size="20" readonly="readonly">
										</TD>
                                                                                          							                                      
                                </TR>
										
								<TR>										
									<TD class="plainlabel">Kênh bán hàng </TD>
			    					<TD class="plainlabel">
			    						<SELECT  name="kbhId" readonly="readonly">
							  		
								  	 <%
								  	 if(nhaphang.getKbhId()==null){
								  		 nhaphang.setKbhId("");
								  	 }
								  		 
								  	 try{
								  		 while(kbh.next()){ 
									    	if(kbh.getString("kbhId").equals(nhaphang.getKbhId())){ %>
												<option value='<%=kbh.getString("kbhId") %>' selected><%=kbh.getString("kbh") %></option>
										  <%}else{ %>
												<option value='<%=kbh.getString("kbhId") %>'><%=kbh.getString("kbh") %></option>
									  <%}}
								  		 }catch(java.sql.SQLException e){} %>	
                   						</select>
                   					</TD>	
                   					
                   					<TD  class="plainlabel">VAT (10%) </TD>
                                        <TD colspan="3" class="plainlabel"><input type="text" name="vat" id="vat"  value="<%= formatter.format(Float.parseFloat(nhaphang.getTongVat())) %>" readonly="readonly" style="text-align: right">
                                         VND</TD>                     					
                                                                                      						
								</TR>
	
								<TR>											
									<TD class="plainlabel">Nhập vào kho </TD>
			    					<TD class="plainlabel">
			    						<SELECT  name="khoId"  >
								  	 <% try{ while(kho.next()){ 
									    	if(kho.getString("khoId").equals(nhaphang.getKhoId())){ %>
												<option value='<%=kho.getString("khoId")%>' selected><%=kho.getString("kho") %></option>
											<%}else{ %>
												<option value='<%=kho.getString("khoId")%>'><%=kho.getString("kho") %></option>
									  <%}}}catch(java.sql.SQLException e){} %>	
                   						</select>
                   					</TD>
                   					
                   					<TD  class="plainlabel">Tổng số tiền(chưa VAT) </TD>
                                    <TD colspan="3" class="plainlabel"><input type="text" name="tongtienbvat" id="tongtienbvat" readonly="readonly" value="<%= formatter.format(Float.parseFloat(nhaphang.getTongtienbVAT())) %>" style="text-align: right"  >
                                     VND </TD>           					
                                    					
								</TR>
								
								 <TR>                                       										

                                        <TD  class="plainlabel">Nhập số hóa đơn </TD>
                                        <TD colspan="0" class="plainlabel"> <input type="text" name="sohoadon" id="sohoadon" value="<%=nhaphang.getSohoadon()%>"  style="text-align: right" >
                                        
                                        <TD  class="plainlabel">Tổng số tiền (có VAT) </TD>
                                   		<TD colspan="3" class="plainlabel"><input type="text" name="tongtienavat" id="tongtienavat" readonly="readonly" value="<%= formatter.format(Float.parseFloat(nhaphang.getTongtienaVAT())) %>"  style="text-align: right" >
                                     	 VND </TD>							
                              	</TR>					
                                
                                <TR class="tblightrow">
                                  <TD  class="plainlabel">Bằng chữ </TD>
                                  <TD colspan="3" class="plainlabel"> <span id="lblDocSo" style="font-weight:bold;font-style:italic;"></span> </TD>
                               </TR>

                                                </TABLE>
                                            
                                                <TABLE id="tab" width = 100% cellpadding="0"  border="0" cellspacing="1" >
                                                    <TR class="tbheader" >
                                                   <TH width="15%">Mã sản phẩm </TH>
                                                       <TH >Tên sản phẩm</TH>
                                                       <TH width="10%">Số lượng(lẻ) </TH>
                                                       <TH width="10%">SL hóa đơn </TH>
                                                       <TH width="10%">Đơn vị </TH>
                                                       <TH width="10%">Quy cách </TH>
                                                       <TH width="10%">Giá mua </TH>
                                                       <TH width="15%">T. tiền (w/o VAT) </TH>
                                                    </TR>

															                                                    
                                    		<% 

               								String lightrow = "tblightrow";
               								
                                            int size = new Integer(nhaphang.getSize()).intValue();
                                            
                                    		for(int i=0;i< size ;i++){ 			%>			
													<TR class= <%=lightrow%> >         
                                                      <TD><input type="text"  name="masp" value='<%= masp[i] %>'  readonly="readonly" style=" width: 100%"> </TD>
                                                       <TD><input type="text"  name="tensp" value='<%= tensp[i] %>'  readonly="readonly" style=" width: 100%"></TD>
                                                       <TD><input type="text" name='<%="sl"+masp[i]%>'  value='<%= Double.parseDouble(sl[i]) %>' id='<%= "sl"+masp[i]%>'  onChange= setTTienbVAT();  style="text-align: right; width :100%" readonly="readonly"> </TD>
                                                        <TD><input type="text" name='<%="slhd"+masp[i]%>'  value='<%= Double.parseDouble(slhoadon[i]) %>' id='<%= "slhd"+masp[i]%>'    style="text-align: right; width :100%" readonly="readonly"> </TD>
                                                        <TD><input type="text"  name="dv" value='<%= donvi[i] %>' readonly="readonly" style="text-align: center;width:100%"></TD>
                                                        <TD><input type="text"  name="qdle" value='<%= quycachsp[i] %>' readonly="readonly" style="text-align: center;width:100%"></TD>
                       								   <TD><input type="text"  name='<%="dg"+masp[i]%>'  value='<%= Math.round(Double.parseDouble(giamua[i])) %>' id='<%= "dg"+masp[i]%>'  readonly="readonly" style="text-align: right;width:100%"> </TD>
                                                       <TD><input type="text"  name='<%= "t"+ masp[i]%>' value= '<%= formatter.format(Float.parseFloat(tienbVAT[i])) %>' id='<%= "t"+ masp[i]%>'  readonly="readonly" style="text-align: right; width:100%">  </TD>																							
												                                              
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

<% if (!(ncc == null)) ncc.close(); %>
<% if (!(dvkd == null)) dvkd.close(); %>
<% 
	if(kbh!=null){
		kbh.close();
	}
	if(kho!=null){
		kho.close();
	}
	masp=null;
	tensp=null;
	sl=null;
	tienbVAT=null;
	giamua=null;
	donvi=null;
	session.setAttribute("nhaphang",null);
	if(nhaphang != null){
		nhaphang.DBclose();
		nhaphang = null;}
		%>
<%}%>
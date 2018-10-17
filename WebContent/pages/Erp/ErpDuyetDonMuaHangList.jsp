<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.duyetdonmuahang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>


<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	
	
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		IDuyetdonmuahang obj = (IDuyetdonmuahang)session.getAttribute("ddmhBean"); %>
<% ResultSet dvthList = (ResultSet)obj.getDvthList() ; %>

<% ResultSet lspList = (ResultSet)obj.getLspList(); %>
<% ResultSet nccList = (ResultSet)obj.getNccList(); %>

<% ResultSet polist = (ResultSet)obj.getPoList(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###");  
	int[] quyen = new  int[5];
	int[] quyen1 = new  int[5];
	quyen = util.Getquyen("Erp_DuyetmuahangSvl","&loai="+obj.getLoaidh(),userId);
	quyen1 = util.Getquyen("Erp_DuyetmuahangSvl","&loai="+obj.getLoaidh(),userId);
	System.out.print("");
	
	
	String loai= obj.getLoaidh(); // 3: đơn hàng trả về

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Traphaco - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">

    <script type="text/javascript" src="../scripts/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
	
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
   
   
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
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	function replaces()
	{
		var ncc = document.getElementsByName("ncc");
		var nccId = document.getElementsByName("nccId");
		var nccId_old = nccId[0].value;
		var nccId_new = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			
		if(nccId_old != nccId_new){
			nccId[0].value = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			document.forms["erpDmhForm"].submit();
		}
			
		setTimeout(replaces, 300);
	}
	 function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 
	 }
	 function submitform()
	 {   
	    document.forms["erpDmhForm"].submit();
	 }
	 
	 function Luughichu(id)
	 {   
		document.forms["erpDmhForm"].Id.value = id;
		document.forms["erpDmhForm"].action.value = "luughichu";
	    document.forms["erpDmhForm"].submit();
	 }
	 
	 function clearform()
	 {   
	    document.forms["erpDmhForm"].dvthId.value = "";
	    document.forms["erpDmhForm"].lspId.value = "";
	    document.forms["erpDmhForm"].ngaymua.value = "";
	    document.forms["erpDmhForm"].maDMH.value = "";
	    document.forms["erpDmhForm"].nccId.value = "";
	    document.forms["erpDmhForm"].submit();
	 }

	 function duyetform(id){
	    document.forms["erpDmhForm"].Id.value = id;
	    document.forms["erpDmhForm"].action.value = "duyet";
	    document.forms["erpDmhForm"].submit();
			 
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 

	</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../Erp_DuyetmuahangSvl">
<input type="hidden" name="ctyId" id="ctyId" value="<%= obj.getCtyId() %>" >
<input type="hidden" name="userId"  id="userId" value="<%= userId %>" >
<input type="hidden" name="loaidh" id="loaidh" value="<%= obj.getLoaidh() %>" >
<input type="hidden" name="Id" value="" >

<input type="hidden" name="action" value="1" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
    	<%  if(obj.getLoaidh().equals("0")) {%>
    	Quản lý mua hàng > Mua hàng nhập khẩu > Duyệt đơn mua hàng
    	<%} else if(obj.getLoaidh().equals("1")) { %>
    	Quản lý mua hàng > Mua hàng trong nước > Duyệt đơn mua hàng
    	<% } else if(obj.getLoaidh().equals("3")) { %>
    	Quản lý mua hàng > Đơn trả hàng NCC > Duyệt đơn mua hàng
    	<% } else { %>
    	Quản lý mua hàng > Mua hàng VT/CPDV/TSCĐ/CCDC > Duyệt đơn mua hàng
    	<%} %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR>
                        <TD class="plainlabel" valign="middle"  width="15%" >Đơn vị thực hiện </TD>
                        <TD class="plainlabel" valign="middle" width="30%">
                            <select name="dvthId" onchange="submitform()" style = "width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(dvthList != null)
	                        		{
	                        			while(dvthList.next())
	                        			{  
	                        			if( dvthList.getString("DVTHID").equals(obj.getDvthId())){ %>
	                        				<option value="<%= dvthList.getString("DVTHID") %>" selected="selected" ><%= dvthList.getString("DVTH") %></option>
	                        			<%}else { %>
	                        				<option value="<%= dvthList.getString("DVTHID") %>" ><%= dvthList.getString("DVTH") %></option>
	                        		 <% } } dvthList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>
                        
                        <TD class="plainlabel" valign="middle" width="10%" >Loại sản phẩm </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="lspId" onchange="submitform()" style = "width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(lspList != null)
	                        		{
	                        			while(lspList.next())
	                        			{  
	                        			  if( lspList.getString("lspId").equals(obj.getLspId() )){ %>
	                        				<option value="<%= lspList.getString("lspId") %>" selected="selected" ><%= lspList.getString("lsp") %></option>
	                        			<%}else { %>
	                        				<option value="<%= lspList.getString("lspId") %>" ><%= lspList.getString("lsp") %></option>
	                        		 <% } } lspList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                                                
                    </TR>
                    <TR>
                    	<TD class="plainlabel" valign="middle" >Mã ĐMH</TD>	
                    	<TD class="plainlabel" valign="middle" >
                    		<input type="text" name="maDMH" id="maDMH"  value="<%= obj.getMaDMH() %>"   onchange="submitform()"/>
                    	 </TD>
                    	
                    	<TD class="plainlabel" valign="middle" >Ngày mua</TD>	
                    	<TD class="plainlabel" valign="middle" >
                    		<input type="text" class="days" name="ngaymua" id="ngaymua"  value="<%= obj.getNgaymua() %>" maxlength="10"  onchange="submitform()"/>
                    	 </TD>
                    </TR>
                    
                     <TR>
                        <TD class="plainlabel" valign="middle" >Nhà cung cấp</TD>
                        <TD class="plainlabel" valign="middle" colspan="3">
		                    <select name="nccId" onchange="submitform()" style = "width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(nccList != null)
	                        		{
	                        			while(nccList.next())
	                        			{  
	                        			  if( nccList.getString("pk_seq").equals(obj.getNccId() )){ %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("tenncc") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("tenncc") %></option>
	                        		 <% } } nccList.close();
	                        		}
	                        	%>
                            </select>  
                        </TD>                        
                    </TR>
                    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại </a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>
                </TABLE>  
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle">Duyệt đơn mua hàng </span>&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="10%">Duyệt</TH>
	                    <TH align="center" width="10%">Mã ĐMH</TH>	                    
	                    <TH align="center" width="9%">Tổng tiền</TH>
	                    <TH align="center" width="9%">Ngày</TH>
	                    <TH align="center" width="9%">Đơn vị thực hiện</TH>
	                    <TH align="center" width="9%">Người tạo</TH>
	                    <TH align="center" width="9%">Nhà cung cấp</TH>
	                    	                                       
	                    <TH align="center" width="9%">Mặt hàng</TH>
	                    <TH align="center" width="9%">Số lượng</TH>
	                    <% if(obj.getLoaidh().equals("1")){ %>
	                    <TH align="center" width="9%">Số lượng duyệt</TH>
	                    <% } %>
	                    <TH align="center" width="9%">Đơn giá</TH>
	                    <TH align="center" width="9%">Thành tiền</TH>
	                    
	                </TR>
	                
		<%	int m = 0;
		    try
			{
				
				String lightrow = "tblightrow";
				String darkrow = "tbdarkrow";
				String mhId = "";
				String mhId_bk = "";
				
				while(polist.next())
				{
					mhId = polist.getString("MHID");
					if(!mhId.equals(mhId_bk)){
						String daduyet = obj.getDaduyet(polist.getString("MHID"));
						String vuotNS = polist.getString("VUOTNGANSACH");
				%>				
						<%//	m = 0;
					if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
							<%  } else { %>
									<TR class= <%= darkrow%> >
							<%  } %>
							<TD align = "center" > 
							
							 <%if(quyen[4]!=0){ %>
								<%if(obj.getLoaidh().equals("3")){ %>
								<a  href="javascript:duyetform(<%= polist.getString("MHID") %>)" onclick=" if(!confirm('Bạn có muốn duyệt chứng từ trả hàng này?')) {return false ;}" >
								<img   src="../images/Chot.png" alt=""></a>&nbsp;&nbsp;&nbsp;&nbsp;
								<%} else { %>
								<a  href="javascript:duyetform(<%= polist.getString("MHID") %>)" onclick=" if(!confirm('Bạn có muốn duyệt chứng từ mua hàng này?')) {return false ;}" >
								<img   src="../images/Chot.png" alt=""></a>&nbsp;&nbsp;&nbsp;&nbsp;
								<%} %>	
								
								<%} %>
								
								<%if(quyen[1]!=0){ 
									if(!loai.trim().equals("3")) { // hoa don tra ve hk cho xoa o buoc nay
								%>
								
								
								<a  href="../../Erp_DuyetmuahangSvl?userId=<%=userId%>&delete=<%= polist.getString("MHID") %>&loai=<%= obj.getLoaidh() %>" onclick=" if(!confirm('Bạn có muốn hủy chứng từ mua hàng này?')) {return false ;}" >
								<img   src="../images/Delete20.png" alt=""></a>&nbsp;&nbsp;&nbsp;&nbsp;
									 
								<%} }%>
								
								 <%if(quyen1[0] != 0 ){ 
								   if(loai.trim().equals("3")) {%>
								   <A href = "../../ErpCongtytrahangUpdateSvl?userId=<%=userId%>&update=<%=  polist.getString("MHID") %>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;		                                
				                               
								   <%} else if(loai.trim().equals("0")){ %>
			                	<A href = "../../ErpDonmuahangUpdate_GiaySvl?userId=<%=userId%>&update=<%= polist.getString("MHID") %>">
                            		<IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;
                            		<%} 
								   
								   else if(loai.trim().equals("1")){ %>
				                	<A href = "../../ErpDonmuahangtrongnuocUpdate_GiaySvl?userId=<%=userId%>&update=<%= polist.getString("MHID") %>">
	                            		<IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;
	                            		<%} 
								   
								 }%>
                            		
                            	 <%if(obj.getLoaidh().equals("2")){  // Mua vật tư : thêm trường Ghichu%>                           	 	
									
										<a href="" id="noidungGhiChu<%= m %>" rel="ndGhiChu<%= m %>">
		           	 							&nbsp; <img title="Ghi chú" alt="Ghi chú" src="../images/vitriluu.png"></a>
		           	 		
	                          			<DIV id="ndGhiChu<%= m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
					                             background-color: white; width: 350px; min-height:150px; overflow:auto; padding: 4px;">
					                    	<table width="100%" align="center">
					                        <tr>
					                            <th width="300px">Ghi chú</th>
					                        </tr>
			                            	
			                            	<%
			                            		String[] ghichuArr = polist.getString("GHICHU").split("__");
			                            		int sodong = 0;
			                            		if(ghichuArr != null)
			                            		{
			                            			for(int i = 0; i < ghichuArr.length; i++)
			                            			{
			                            				%>
			                            				<Tr>
						                            		<td><input type="text" name="ghichu" value="<%= ghichuArr[i] %>" style="width: 100%" /></td>
						                            	</Tr>
			                            			<% sodong++; }
			                            		}
			                            		
			                            		while(sodong < 5)
			                            		{
			                            			%>
			                            			
			                            			<Tr>
					                            		<td><input type="text" name="ghichu" value="" style="width: 100%" /></td>
					                            	</Tr>
			                            			
			                            		<% sodong++; }
			                            	%>
			                            	
					                    	</table>
						                     <div align="left">
						                     	<label style="color:red" ></label>
						                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('ndGhiChu<%=m %>')" onClick="javascript:Luughichu(<%= polist.getString("MHID") %>)">Hoàn tất</a>
						                     </div>
					            		</DIV>   
										
									
                            	 <%} %>
							</TD>
							<TD align = "center"><%= polist.getString("SOCHUNGTU") %> </TD>
							<TD align = "center" ><%= formatter.format(Double.parseDouble(polist.getString("TONGTIENAVAT")))  %> </TD>
							<TD align = "center"><%= polist.getString("NGAY") %> </TD>
							<TD align = "center"><%= polist.getString("DVTH") %> </TD>
							<TD align = "center"><%= polist.getString("NGUOITAO") %> </TD>
							<TD align = "left" colspan = 2><%= polist.getString("NCC")  %> </TD>
							<TD align = "right" colspan = 4><%= "( Đã duyệt bởi : " + daduyet + ")"  %> </TD>
						</TR>

				<%//	m = 0;
					if (m % 2 != 0) {%>						
						<TR class= <%=lightrow%> >
				<%  } else { %>
						<TR class= <%= darkrow%> >
				<%  } %>
							<TD align = "center">&nbsp;</TD>
							<TD align = "right" colspan = 7><%= polist.getString("MA") + " - " + polist.getString("SP") %></TD>
							<TD align = "center"><%= polist.getString("SOLUONG") %></TD>
							<% if(obj.getLoaidh().equals("1")){ %>
							<TD align = "center">
								<INPUT type="hidden" name="mhid" style="width: 200px" value='<%=polist.getString("mhid") %>'>
								<INPUT type="hidden" name="spid" style="width: 200px" value='<%=polist.getString("spid") %>'>
								<INPUT type="hidden" name="slduyet" style="width: 200px" value='<%=polist.getString("SOLUONG") %>' readonly='readonly'/>
								<INPUT type="hidden" name="dongia" style="width: 200px" value='<%=polist.getString("DONGIA") %>'>
								
								
								<INPUT type="hidden" name="donviid" style="width: 200px" value='<%=polist.getString("donvi") %>'>
								<INPUT type="hidden" name="ngaynhanid" style="width: 200px" value='<%=polist.getString("ngaynhan") %>'>
								<INPUT type="hidden" name="thuexuatid" style="width: 200px" value='<%=polist.getString("thuexuat") %>'>
								
								<%=polist.getString("SOLUONG") %>
							</TD>
							<% } %>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("DONGIA")))  %></TD>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("THANHTIEN")))  %></TD>
							
							
						</TR>			

		<%			}else{%>

				<%	m--; if (m % 2 != 0) {%>						
						<TR class= <%=lightrow%> >
				<%  } else { %>
						<TR class= <%= darkrow%> >
				<%  } %>
							<TD align = "center">&nbsp;</TD>
							<TD align = "right" colspan = 7><%= polist.getString("MA") + " - " + polist.getString("SP")%></TD>
							<TD align = "center"><%= polist.getString("SOLUONG") %></TD>
							<% if(obj.getLoaidh().equals("1")){ %>
							<TD align = "center">
								<INPUT type="hidden" name="mhid" style="width: 200px" value='<%=polist.getString("mhid") %>'>
								<INPUT type="hidden" name="spid" style="width: 200px" value='<%=polist.getString("spid") %>'>
								<INPUT type="text" name="slduyet" style="width: 200px" value='<%=polist.getString("SOLUONG") %>' readonly='readonly'/>
								<INPUT type="hidden" name="dongia" style="width: 200px" value='<%=polist.getString("DONGIA") %>'>
								
								
								<INPUT type="hidden" name="donviid" style="width: 200px" value='<%=polist.getString("donvi") %>'>
								<INPUT type="hidden" name="ngaynhanid" style="width: 200px" value='<%=polist.getString("ngaynhan") %>'>
								<INPUT type="hidden" name="thuexuatid" style="width: 200px" value='<%=polist.getString("thuexuat") %>'>
							</TD>
							<% } %>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("DONGIA")))  %></TD>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("THANHTIEN")))%></TD>							
							
						</TR>			
			
		<%			}
					mhId_bk = polist.getString("MHID");
					m++;
		
					}
			}catch(Exception e){ e.printStackTrace();}%>
	                
				</TABLE>
            </fieldset>
        </div>
        
    </div>  
<script type="text/javascript">
<% System.out.println("M "+m);
 for(int k=0; k < m; k++) {%>
	dropdowncontent.init("noidungGhiChu<%=k %>", "right-bottom", 500, "click");
<%}%>

</script>
</div>
</form>
</body>
</html>
<% 
try{
if(polist!=null){
	polist.close();
}
obj.DBclose();
session.setAttribute("ddmhBean",null);
}catch(Exception er){
	
}
	}
%>
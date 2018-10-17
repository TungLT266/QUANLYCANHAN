<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuthanhtoan.*" %>
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
		IErpGanmachiphi obj = (IErpGanmachiphi)session.getAttribute("ddmhBean"); %>
<% ResultSet dvthList = (ResultSet)obj.getDvthList() ; %>

<% ResultSet lspList = (ResultSet)obj.getLspList(); %>
<% ResultSet nccList = (ResultSet)obj.getNccList(); %>

<% ResultSet polist = (ResultSet)obj.getPoList(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###");  
	int[] quyen = new  int[5];
	quyen = util.Getquyen("Erp_GanmachiphiSvl","230",userId);
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Phanam - Project</title>
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
		var nccId_new = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")))
			
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
<form name="erpDmhForm" method="post" action="../../Erp_GanmachiphiSvl">
<input type="hidden" name="ctyId" value="<%= obj.getCtyId() %>" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="Id" value="" >

<input type="hidden" name="action" value="1" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">Quản lý công nợ > Công nợ phải trả > Gán mã chi phí
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
            	<legend><span class="legendtitle">Gán mã chi phí </span>&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
					    <TH align="center" width="10%"></TH>	
	                    <TH align="center" width="10%">Mã ĐNTT</TH>	                    
	                    <TH align="center" width="9%">Tổng tiền</TH>
	                    <TH align="center" width="9%">Ngày</TH>
	                    <TH align="center" width="9%">Đơn vị thực hiện</TH>
	                    <TH align="center" width="9%">Người tạo</TH>
	                    <TH align="center" width="9%">Nhà cung cấp</TH>
	                    	                                       
	                    <TH align="center" width="9%">Mặt hàng</TH>
	                    <TH align="center" width="9%">Số lượng</TH>
	                    <TH align="center" width="10%">Đơn giá</TH>
	                    <TH align="center" width="10%">Thành tiền</TH>
	                    
	                </TR>
	                
		<%	try
			{
				int m = 0;
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
					if (m % 2 != 0) { %>						
									<TR class= <%=lightrow%> >
							<%  } else { %>
									<TR class= <%= darkrow%> >
							<%  } %>
							<TD align = "center" > 							
								
								<%if(quyen[4]!=0 && polist.getString("ISGANMACP").equals("1")){ %>
								<A  href="javascript:duyetform(<%= polist.getString("MHID") %>)">
								<IMG style="top: -4px;" src="../images/Chot.png" alt=""></A>&nbsp;&nbsp;&nbsp;&nbsp;
								<%} %>
								
								 <%if(quyen[0] != 0 ){ %>
			                		<A href = "../../ErpPhieuThanhToanUpdateSvl?userId=<%=userId%>&update=<%= polist.getString("MHID") %>&chucnang=ganmacp">
                            		<IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;
                                 <%} %>
							</TD>
							<TD align = "center"><%= polist.getString("SOCHUNGTU") %> </TD>
							<TD align = "center" ><%= formatter.format(Double.parseDouble(polist.getString("TONGTIENAVAT")))  %> </TD>
							<TD align = "center"><%= polist.getString("NGAY") %> </TD>
							<TD align = "center"><%= polist.getString("DVTH") %> </TD>
							<TD align = "center"><%= polist.getString("NGUOITAO") %> </TD>
							<TD align = "left" colspan = 2><%= polist.getString("NCC")  %> </TD>
							<TD align = "right" colspan = 3></TD>
						</TR>

			<%//	m = 0;
					if (m % 2 != 0) { %>						
						<TR class= <%=lightrow%> >
				<%  } else { %>
						<TR class= <%= darkrow%> >
				<%  } %>
							<TD align = "center">&nbsp;</TD>
							<TD align = "center">&nbsp;</TD>
							<TD align = "right" colspan = 6><%= polist.getString("MA") + " - " + polist.getString("SP") %></TD>
							<TD align = "center"><%= polist.getString("SOLUONG") %></TD>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("DONGIA")))  %></TD>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("THANHTIEN")))  %></TD>
							<TD> </TD>
						</TR>			

		<%			}else{%>

				<%	m--; if (m % 2 != 0) {%>						
						<TR class= <%=lightrow%> >
				<%  } else { %>
						<TR class= <%= darkrow%> >
				<%  } %>
							<TD align = "center">&nbsp;</TD>
							<TD align = "center">&nbsp;</TD>
							<TD align = "right" colspan = 6><%= polist.getString("MA") + " - " + polist.getString("SP")%></TD>
							<TD align = "center"><%= polist.getString("SOLUONG") %></TD>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("DONGIA")))  %></TD>
							<TD align = "center"><%= formatter.format(Double.parseDouble(polist.getString("THANHTIEN")))%></TD>							
							<TD> </TD>
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
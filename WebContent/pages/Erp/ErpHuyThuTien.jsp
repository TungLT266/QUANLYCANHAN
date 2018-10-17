<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.huythutien.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpHuythutienList obj = (IErpHuythutienList)session.getAttribute("obj"); %>
<% ResultSet hctmhList = (ResultSet)obj.getHctMhRs();
   List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
%>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings(); 
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpHuythutienSvl","",userId);
	
	NumberFormat formatterNT = new DecimalFormat("#,###,###.##"); 
	NumberFormat formatterVND = new DecimalFormat("#,###,###"); 
	NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>TraphacoERP - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
   
   <script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   <script type="text/javascript" src="../scripts/ajax.js"></script>
   <script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script> 
   
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
	 function submitform()
	 {   
	    document.forms["erpHctmhForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpHctmhForm"].action.value = "Tao moi";
	    document.forms["erpHctmhForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpHctmhForm"].tungay.value = "";
	    document.forms["erpHctmhForm"].denngay.value = "";
	    document.forms["erpHctmhForm"].trangthai.value = "";
	    document.forms["erpHctmhForm"].nguoitao.value = "";
	    document.forms["erpHctmhForm"].sochungtu.value = "";
	    document.forms["erpHctmhForm"].sotientt.value = "";
	    document.forms["erpHctmhForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpHctmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpHctmhForm"].msg.value);
	 }
	 

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 
	 function TinhTien()
		{
			// từng sản phẩm
			
		    var sotien = document.getElementById("sotientt").value;
			if(sotien == ''){
				 document.getElementById("sotientt").value = '';
			}
			else{
				document.getElementById("sotientt").value = DinhDangTien(sotien);
			}
			
		}
	 
	 function formatTien(e)
		{
			var giatrinhap = e.value;
			e.value = DinhDangTien(giatrinhap);
			
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
	</SCRIPT>
</head>
<body>
<form name="erpHctmhForm" method="post" action="../../ErpHuythutienSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Công nợ phải thu > Hủy phiếu thu tiền 
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
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    
                      <TR>
                        <TD class="plainlabel" >Người tạo</TD>
                        <TD class="plainlabel">
                            <input type="text" class="nguoitao" 
                                   id="nguoitao" name="nguoitao" value="<%= obj.getNguoitao() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    
                     <TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="trangthai" onChange="submitform();">
								<% if (obj.getTrangthai().equals("1")){%>
								  	<option value="1" selected>Đã chốt</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="2">Đã hủy</option>
								  	<option value=""> </option>
								  
								<%}else if(obj.getTrangthai().equals("0")) {%>
								 	<option value="0" selected>Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								 	<option value="2" >Đã hủy</option>
								  	<option value="" > </option>
								  	
								<%}else{ if(obj.getTrangthai().equals("2")) { %>											
								 	<option value="2" selected>Đã hủy</option>										  	
								  	<option value="0" >Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="" ></option> 					  		  	
								<%} else { %>
									<option value="" ></option> 									  	
								  	<option value="0" >Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="2" >Đã hủy</option>	
								<% } } %>
                           </select>
                        </TD>                        
                    </TR>    
                    <TR>
                        <TD class="plainlabel" > Số chứng từ</TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="sochungtu" name="sochungtu" value="<%= obj.getsochungtu() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                     <Tr>
                    	<TD class="plainlabel" valign="middle" >Số tiền </TD>
                        <TD class="plainlabel" valign="middle" colspan="4">
                           <input type="text" id="sotientt" name="sotientt" value="<%= obj.getsotien() %>" onChange="submitform()"  />
                        </TD>              
                    </Tr>
                    
                    <tr>
                        <td colspan="2" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Hủy chứng từ thu tiền </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a><%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Số phiếu </TH>
	                    <TH align="center">Số chứng từ </TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center"> Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="center"> Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
	                
	                <%  int m = 0;	
	                	if(hctmhList!=null) {
	                		while(hctmhList.next())
	                		{
	                			if((m % 2 ) == 0) {%>
	                         	<TR class='tbdarkrow'>
	                        	<%}else{ %>
	                          	<TR class='tblightrow'>
	                        	<%} %>
	                        	
	                        	<TD align="center"><%= hctmhList.getString("SOPHIEU")%></TD>
		                    	<TD align="center"><%= hctmhList.getString("SOCHUNGTUGOC") %></TD>
		                    	
		                    	<TD align="center">
			                    	<%
			                    		String trangthai = "";
			                    		String tt = hctmhList.getString("TRANGTHAI");
			                    		if(tt.equals("0"))
			                    			trangthai = "Chưa chốt";
			                    		else
			                    		{
			                    			if(tt.equals("1"))
			                    				trangthai = "Đã chốt";
			                    			else
			                    			{
				                    			trangthai = "Đã hủy";
			                    			}
			                    		}
			                    	%>
			                    	<%= trangthai %>
		                    	</TD>
		                    	
		                    	<TD align="center"><%= hctmhList.getString("NGAYTAO") %></TD>
			                    <TD align="center"><%= hctmhList.getString("NGUOITAO") %></TD>
			                    <TD align="center"><%= hctmhList.getString("NGAYSUA") %></TD>
			                    <TD align="center"><%= hctmhList.getString("NGUOISUA") %></TD>		
			                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %>                             
                                <%if(quyen[4]!=0){ %>
                                 <A id='chotphieu<%= hctmhList.getString("SOPHIEU") %>'
							       href=""><img
							       src="../images/Chot.png" alt="Chốt"
							       width="20" height="20" title="Chốt"
							       border="0" onclick="if(!confirm('Bạn có muốn chốt phiếu này?')) {return false ;}else{ processing('<%="chotphieu"+hctmhList.getString("SOPHIEU")%>' , '../../ErpHuythutienSvl?userId=<%=userId%>&chot=<%= hctmhList.getString("SOPHIEU") %>&loaict=<%=""%>&soct=<%=hctmhList.getString("SOCHUNGTU")%>');}"  >							     
								</A><%}                                
                                %>
                                <A href = "../../ErpHuythutienUpdateSvl?userId=<%=userId%>&update=<%= hctmhList.getString("SOPHIEU") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>
		                    	<A id='deletephieu<%= hctmhList.getString("SOPHIEU") %>'
							       href=""><img
							       src="../images/Delete20.png" alt="Xóa"
							       width="20" height="20" title="Xóa"
							       border="0" onclick="if(!confirm('Bạn có muốn xóa phiếu này?')) {return false ;}else{ processing('<%="deletephieu"+hctmhList.getString("SOPHIEU")%>' , '../../ErpHuythutienSvl?userId=<%=userId%>&delete=<%= hctmhList.getString("SOPHIEU") %>&loaict=<%=""%>&soct=<%=hctmhList.getString("SOCHUNGTU")%>');}"  >							     
								</A>
                                <% } else 
                                {	if(quyen[3]!=0){ %>
		                    	<A href = "../../ErpHuythutienUpdateSvl?userId=<%=userId%>&display=<%= hctmhList.getString("SOPHIEU") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                    	
		                    	<% }
                              } %>
		                    </TD>			
	                	<% 	m++; }
	                %>
	                
	                <%} }%>
					
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</body>
</html>
<% 
if(obj!=null)
{
	obj.DBclose();
	session.setAttribute("obj",null);
}
%>
<%@page import="geso.traphaco.erp.beans.butrucongno.*"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>


<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>


<% IButrucongnoList obj = (IButrucongnoList)session.getAttribute("obj"); %>
<%%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");
	int[] quyen = new  int[5];
	Utility util=new Utility();
	quyen = util.Getquyen("ButrucongnoSvl","",userId);

NumberFormat formatterNT = new DecimalFormat("#,###,###.##"); 
NumberFormat formatterVND = new DecimalFormat("#,###,###"); 
NumberFormat formatter = new DecimalFormat("#,###,###.##"); 

ResultSet btcnRs = obj.getBtcnList();

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Phanam - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
		
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
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{
		document.nvgnForm.tungay.value = "";  
		document.nvgnForm.trangthai.value= "";
		document.nvgnForm.denngay.value = "";  
		document.nvgnForm.sochungtu.value = "";  
		document.nvgnForm.sotientt.value = "";
		submitform();    
	}

	function submitform()
	{
		document.nvgnForm.action.value= 'search';
		document.forms['nvgnForm'].submit();
	}

	function newform()
	{
		document.nvgnForm.action.value= 'new';
		document.forms['nvgnForm'].submit();
	}
	
	function thongbao()
	 {
		 var tt = document.forms["nvgnForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["nvgnForm"].msg.value);
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

function duyetform(Id)
{
	 if(!confirm('Bạn có chắc chốt bù trừ công nợ KH này?')) 
	 {
		 return false ;
	 }
	 
	 document.forms['nvgnForm'].chungtu.value = Id;
	 document.forms['nvgnForm'].action.value= 'chot';
	 document.forms['nvgnForm'].submit();
}

function xoaform(Id)
{
	 if(!confirm('Bạn có chắc xóa bù trừ công nợ KH này?')) 
	 {
		 return false ;
	 }
	 
	 document.forms['nvgnForm'].chungtu.value = Id;
	 document.forms['nvgnForm'].action.value= 'delete';
	 document.forms['nvgnForm'].submit();
}

	</SCRIPT>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2(); });
	     
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nvgnForm" method="post" action="../../ButrucongnoSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="msg" value='<%= obj.getMgs() %>'>
<input type="hidden" name="chungtu" id="chungtu" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<input type="hidden" name="action" value="1" >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
                <TR>
                    <TD align="left" class="tbnavigation">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr height="22">
                                <TD align="left" colspan="2" class="tbnavigation">Quản lý công nợ > Công nợ phải thu > Bù trừ công nợ KH</TD>
                                <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= userTen %> &nbsp;</TD></tr>
                        </table></TD>
                </TR>
            </TABLE>
            
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                <TR>

                    <TD >
                        <FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
                            <TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
                                
                                <TR>
                                    <TD width="100px" class="plainlabel">Từ ngày  </TD>
                                    <TD width="300px" class="plainlabel">
                                        <INPUT name="tungay" type="text" size="40" class ="days" value="<%= obj.getTungay()%>"  onChange = "submitform();">
                                   </TD>
			
								    <TD width="100px" class="plainlabel">Đến ngày  </TD>
                                    <TD  class="plainlabel">
                                        <INPUT name="denngay" type="text" size="40" class ="days" value="<%= obj.getDenngay()%>"  onChange = "submitform();">
                                   </TD>
                                </TR>
                                
                                 <TR>
                                    <TD width="100px" class="plainlabel">Số chứng từ  </TD>
                                    <TD width="300px" class="plainlabel">
                                        <INPUT name="sochungtu" type="text" size="40"  value="<%=  obj.getSochungtu() %>"  onChange = "submitform();">
                                   </TD>
			
								     <TD class="plainlabel">Trạng thái </TD>
                                    <TD  class="plainlabel" >
                                    	<SELECT name ="trangthai" onChange = "submitform();" style="width: 200px">
                                           
                                        <% if (obj.getTrangthai().equals("1")){%>
                                      		  <option value=""></option>
                                              <option value="1" selected>Đã xác nhận</option>
                                              <option value="0">Chưa xác nhận</option>
                                              <option value="2">Đã xóa</option>
                                              
                                        <%}else if(obj.getTrangthai().equals("0")) {%>
                                              <option value=""></option>
                                              <option value="0" selected>Chưa xác nhận</option>
                                              <option value="1" >Đã xác nhận</option>
                                              <option value="2">Đã xóa</option>
                                              
                                        <%}else if(obj.getTrangthai().equals("2")) {%>                                                                                        
                                              <option value=""></option>
                                              <option value="1" >Đã xác nhận</option>
                                              <option value="0" >Chưa xác nhận</option>
                                              <option value="2" selected>Đã xóa </option>
                                        <%}else {%>
                                        	  <option value="" selected></option>
                                        	  <option value="0" >Chưa xác nhận</option>
                                              <option value="1" >Đã xác nhận</option>
                                              <option value="2" >Đã xóa </option>
                                        <%} %>
                                        
                                        </SELECT>
                                      </TD>
                                </TR>
                                
                                <TR>
                                    <TD width="100px" class="plainlabel">Khách hàng chuyển nợ  </TD>
                                    <TD width="300px" class="plainlabel">
                                        <INPUT name="khchuyenno" type="text" size="40" id="khchuyenno" value="<%= obj.getKHChuyenNo()%>"  onChange = "submitform();">
                                   </TD>
			
								    <TD width="100px" class="plainlabel">Khách hàng nhận nợ </TD>
                                    <TD  class="plainlabel">
                                        <INPUT name="khnhanno" type="text" size="40"  id="khnhanno" value="<%= obj.getKHNhanNo()%>"  onChange = "submitform();">
                                   </TD>
                                </TR>
                                
                                <Tr>
                    			<TD class="plainlabel" valign="middle" >Số tiền </TD>
                        		<TD class="plainlabel" valign="middle" colspan="4">
                          			 <input type="text" id="sotientt" name="sotientt" value="<%= obj.getSotien() %>" onChange="submitform();"  />
                        		</TD>              
                    			</Tr>
                                
                               <TR>
									<TD class="plainlabel" colspan="5">
									<a class="button2" href="javascript:clearform()">
											<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                               </TD>
								</TR>
								
								
                            </TABLE>
                      </FIELDSET>
                    </TD>   
                </TR>
            </TABLE>
            
            <TABLE width="100%" border="0" cellpadding="0" cellspacing ="0">
                <TR>
                    <TD width="100%">

                    <FIELDSET>
                    <LEGEND class="legendtitle">&nbsp;Bù trừ công nợ KH &nbsp;&nbsp;&nbsp;
                    	<%if(quyen[0]!=0){ %>
                    	<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a> 
    					<%} %>                           
                    </LEGEND>
                    <TABLE class="" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD>
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH align="center" width="13%">Số chứng từ </TH>
                                    <TH width="20%" align="center">Ngày chứng từ </TH>
                                    <TH width="20%" align="center">KH chuyển nợ </TH>
                                    <TH width="20%" align="center">KH nhận nợ</TH>
                                    <TH align="center" width="12%">Trạng thái</TH>
                                    <TH align="center" width="12%">Tổng tiền</TH>
                                    <TH align="center" width="12%">Tác vụ</TH>
                                </TR>
                            
                                <%  		  int m = 0;
                                if(btcnRs!=null)
            					{
                                	 while(btcnRs.next())
             						{ 	  
                               			if((m % 2 ) == 0) {%>
   		                         		<TR class="tbdarkrow">
   			                            <%}else{ %>
   			                          	<TR class="tblightrow">
   			                            <%} %>
                                        <TD align="left"><div align="center"><%= btcnRs.getString("PK_SEQ") %></div></TD>                                   
                                        <TD align="center"><%= btcnRs.getString("NGAYBUTRU") %></TD>                                                 
                                        <TD align="center"><%= btcnRs.getString("KH_CHUYENNO") %></TD>
                                        <TD align="center"><%= btcnRs.getString("KH_NHANNO") %></TD>
                                        <% if (btcnRs.getString("TRANGTHAI").equals("0")){ %>
                                            <TD align="center">Chờ xác nhận </TD>
                                        <%}else if (btcnRs.getString("TRANGTHAI").equals("1")){%>
                                            <TD align="center">Đã xác nhận</TD>
                                        <%}else{%>
                                        	 <TD align="center">Đã xóa</TD>
                                        <%} %>
                                                <TD align="center"><%=formatter.format(Double.parseDouble(btcnRs.getString("TONGTIEN"))) %></TD>                                               
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
                                                    <% 
                                                    String trangthai= btcnRs.getString("TRANGTHAI");
                                                    if(trangthai.equals("0"))
                                                    { %>
	                                                    <TD>
	                                                        <A href = "../../ButrucongnoUpdateSvl?userId=<%=userId%>&update=<%=btcnRs.getString("PK_SEQ")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cập nhật" border ="0" ></A>
	                                                    </TD>
	                                                    
	                                                    <TD>&nbsp;</TD>
	                                                     <TD>
	                                                       <%--  <A href = "../../ButrucongnoSvl?userId=<%=userId%>&chot=<%=htt.getId()%>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border="0" onclick="if(!confirm('Bạn chắc chắn muốn chốt phiếu này?')){ return false;}"></A> --%>
	                                                        <A href="javascript:duyetform(<%= btcnRs.getString("PK_SEQ") %>);" >
															 	<img  src="../images/Chot.png" alt="Chốt bù trừ công nợ KH " width="20" height="20"  border='0' title="Chốt bù trừ công nợ KH"	 >
															</A>
	                                                    </TD>
	                                                   
	                                                    <TD>&nbsp;</TD>
	                                                    <TD>
	                                                    
	                                                    <A href="javascript:xoaform(<%= btcnRs.getString("PK_SEQ") %>);" >
														 	<img  src="../images/Delete20.png" alt="Xóa bù trừ công nợ" width="20" height="20"  border='0' title="Xóa bù trừ công nợ"	 >
														</A>
										
	                                                     <%--    <A href = "../../ButrucongnoSvl?userId=<%=userId%>&delete=<%=htt.getId()%>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border="0" onclick="if(!confirm('Bạn chắc chắn muốn xóa phiếu này?')){ return false;}"></A> --%>
	                                                    </TD>
	                                                    <TD>&nbsp;</TD>
	                                                   
	                                                <%  }else{ %>
	                                                    <TD>
	                                                        <A href = "../../ButrucongnoUpdateSvl?userId=<%=userId%>&display=<%=btcnRs.getString("PK_SEQ")%>"><img src="../images/Display20.png" alt="Cap nhat" width="20" height="20" longdesc="Hiển thị" border ="0" ></A>
	                                                    </TD>
	                                                    
	                                                 <% }%>
	                                                    
                                                    </TR>
                                                 </TABLE>
                                                </TD>
                                              </TR>
                                <%		}
                                	} %>
                                	<tr class="tbfooter" > <td colspan="10" >&nbsp;</td> </tr> 
                            </TABLE>
                            </TD>
                        </TR>
                    </TABLE>

                    </FIELDSET>
                    </TD>
                </TR>
        </TABLE>

    </TR>
</TABLE>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<script type="text/javascript"> 
	  <%for(int k=0; k < m; k++) {%>
	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click");
	   
	  <%}%>
</script>
</form>
<%
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj",null);
	
%>

</BODY>
</html>
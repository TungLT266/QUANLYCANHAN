<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.erp.beans.hoadontrahang.IErpHdTraHangList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.nhapkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>

<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpHdTraHangList hdthList = (IErpHdTraHangList)session.getAttribute("hdthList"); %>
<% ResultSet hoadonlist = (ResultSet)hdthList.getRsHdTraHang(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
NumberFormat formatter = new DecimalFormat("#,###,###.##"); 

	List<IThongTinHienThi> htList = (List<IThongTinHienThi>)hdthList.getHienthiList();

String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		 int[] quyen = new  int[5];
		 quyen = util.Getquyen("ErpHdTraHangSvl","",userId);
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
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
   
   	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
    
    <link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2(); });
	     
	</script>


    <SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
	    document.forms["erpHdForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpHdForm"].action.value = "new";
	    document.forms["erpHdForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpHdForm"].TuNgay.value = "";
	    document.forms["erpHdForm"].DenNgay.value = "";
	    document.forms["erpHdForm"].sochungtu.value = "";
	    document.forms["erpHdForm"].ngayhoadon.value = "";
	    document.forms["erpHdForm"].nppId.value = "";
	    document.forms["erpHdForm"].SoHoaDon.value = "";
	    document.forms["erpHdForm"].TrangThai.value = "";
	    
	    document.forms["erpHdForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpHdForm"].Message.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpHdForm"].Message.value);
	 }
	 
	 function processing(id,chuoi)
	 {
		 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
		 document.getElementById(id).href=chuoi;
	 }
	</SCRIPT>
</head>
<body>
<form name="erpHdForm" method="post" action="../../ErpHdTraHangSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= hdthList.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= hdthList.getNxtApprSplitting() %>" >

<input type="hidden" name="Message" value='<%= hdthList.getMessage() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng >	Nghiệp vụ khác > Nhập hóa đơn trả hàng
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
                        <TD class="plainlabel" width="25%">
                            <input type="text" class="days" 
                                   id="TuNgay" name="TuNgay" value="<%= hdthList.getTuNgay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                  
                        <TD class="plainlabel" width="15%" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="DenNgay" name="DenNgay" value="<%= hdthList.getDenNgay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    <TR>
                    <TD class="plainlabel" valign="middle">Số chứng từ </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="sochungtu" value="<%= hdthList.getSoChungTu() %>" onchange="submitform()">
                        </TD> 
                        <TD class="plainlabel" valign="middle">Số hóa đơn </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="SoHoaDon" value="<%= hdthList.getSoHoaDon() %>" onchange="submitform()">
                        </TD>                        
                    </TR>
                    <TR  >
                        <TD class="plainlabel" valign="middle">Khách hàng </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="nppId" value="<%= hdthList.getKhachHang() %>" onchange="submitform()">
                        </TD> 
                        <TD class="plainlabel" >Ngày xuất hóa đơn </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" id="ngayhoadon" name="ngayhoadon" value="<%= hdthList.getNgayHoaDon() %>" maxlength="10" onchange="submitform()" />
                        </TD>                       
                    </TR>
                    
                     <TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" colspan="3">
                           <select name="TrangThai" onChange="submitform();" style="width: 200px">
                           		<option value=""> </option>
								<% if (hdthList.getTrangthai().equals("1")){%>
								  	<option value="1" selected>Đã chốt</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="2">Đã xóa</option>
								  	
								  
								<%}else if(hdthList.getTrangthai().equals("0")) {%>
								 	<option value="0" selected>Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								 	<option value="2" >Đã xóa</option>
								  	
								  	
								<%}else if(hdthList.getTrangthai().equals("2")){%>											
								 	<option value="2" selected>Đã xóa</option>										  	
								  	<option value="0" >Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	
								  		  	
								<%}else{%>
									<option value="0" >Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>											
								  	<option value="2" >Đã xóa</option>
								<%} %>
                           </select>
                        </TD>                        
                    </TR>    
                    <tr>
                        <td colspan="4" class="plainlabel">
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
            	<legend><span class="legendtitle"> Nhập hóa đơn trả hàng  &nbsp;&nbsp; </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Số chứng từ</TH>
	                    <TH align="center">Ngày xuất HD</TH>
	                    <TH align="center">Số hóa đơn</TH>
	                    <TH align="center">Khách hàng</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center"> Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                 <!--    <TH align="center"> Người sửa </TH> -->
	                    <TH width="10%" align="center" >Tác vụ</TH>
	                </TR>
					<%
                 			int m = 0;
					if(hoadonlist!=null)
					{
						while(hoadonlist.next())
						{
							if((m % 2 ) == 0) { %>
                         	<TR class='tbdarkrow'>
	                        <%}else{ %>
	                        <TR class='tblightrow'>
	                        <%} %>
	                        <TD align="center">
	                        <% if(hoadonlist.getString("Trangthai").equals("3")){ %>
		                    	<span style="color: red;" ><%= hoadonlist.getString("PK_SEQ") %></span> 
		                    <%} else { %>
		                    	<span><%= hoadonlist.getString("PK_SEQ") %></span> 
		                    <%} %>
		                    </TD>
		                    
		                    <TD align="center"><%= hoadonlist.getString("ngayxuathd") %></TD>
		                    <TD align="center"><%= hoadonlist.getString("SOHOADON") %></TD>	
		                    <TD align="left"><%= hoadonlist.getString("npp") %></TD>	
		                    
		                     <TD align="center">
		                    	<%
		                    		String Trangthai = "";
		                    		String tt = hoadonlist.getString("Trangthai");
		                    		if(tt.equals("0"))
		                    			Trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(tt.equals("1"))
		                    				Trangthai = "Đã chốt";
		                    			else
		                    			{
		                    				if(tt.equals("2"))
		                    					Trangthai = "Đã xóa";
		                    				else Trangthai="Đã hủy";
		                    			}
		                    		}
		                    	%>
		                    	<%= Trangthai %>
		                    </TD>
		                    <TD align="center"><%= hoadonlist.getString("NGAYTAO") %></TD>
		                    <TD align="center"><%= hoadonlist.getString("NGUOITAO") %></TD>
		                    <TD align="center"><%= hoadonlist.getString("NGAYSUA") %></TD>	
		                    
		                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %>
		                    <%if(quyen[1]!=0){ %>
                                <A href = "../../ErpHdTraHangUpdateSvl?userId=<%=userId%>&update=<%= hoadonlist.getString("PK_SEQ")  %>">
                                <IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" 
                                border=0 >
                                </A>&nbsp;
                                <%} %>
                                <%if(quyen[4]!=0){ %>
                                <A id='chothd<%= hoadonlist.getString("PK_SEQ")  %>'
                                href = ""> <%-- "../../ErpHdTraHangSvl?userId=<%=userId%>&chot=<%= hoadonlist.getString("pk_Seq") %>"> --%>
                                <img src="../images/Chot.png" alt="Chôt nhập kho" title="Chốt Hóa Đơn" width="20" height="20" 
                                border=0 onclick="if(!confirm('Bạn muốn chốt hóa đơn này?')) {return false ;}else{ processing('<%="chothd"+hoadonlist.getString("PK_SEQ") %>' , '../../ErpHdTraHangSvl?userId=<%=userId%>&chot=<%= hoadonlist.getString("PK_SEQ")  %>');}">
                                </A>&nbsp;
                                <%} %>
                                <%if(quyen[1]!=0){ %>
                                <A id='xoahd<%= hoadonlist.getString("PK_SEQ") %>'
                                href = "">  <%-- "../../ErpHdTraHangSvl?userId=<%=userId%>&delete=<%= hoadonlist.getString("pk_Seq") %>"> --%>
                                <img src="../images/Delete20.png" alt="Xóa nhập kho" title="Xóa Hóa Đơn" width="20" height="20" 
                                border=0 onclick="if(!confirm('Bạn muốn xóa hóa đơn này?')) {return false ;}else{ processing('<%="xoahd"+hoadonlist.getString("PK_SEQ") %>' , '../..//ErpHdTraHangSvl?userId=<%=userId%>&delete=<%=hoadonlist.getString("PK_SEQ")  %>');}">                               
                                </A>	
                                <%} %>
                               					
		                    <%}else if(tt.equals("1")){ %>                                
		                     	<A href = "../../ErpHdTraHangUpdateSvl?userId=<%=userId%>&display=<%= hoadonlist.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                     		                     
		                    <%} else {%>
		                    	<A href = "../../ErpHdTraHangUpdateSvl?userId=<%=userId%>&display=<%= hoadonlist.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                    <%} %>
		                    </TD>
		                    
						<% m++;  }
					}
						%>
		                </TR>
                     
                     
					<tr class="tbfooter">
								<TD align="center" valign="middle" colspan="13" class="tbfooter">
									<% hdthList.setNextSplittings(); %> <script type="text/javascript"
										src="../scripts/phanTrang.js"></script> <input type="hidden"
									name="crrApprSplitting"
									value="<%= hdthList.getCrrApprSplitting() %>"> <input
									type="hidden" name="nxtApprSplitting"
									value="<%= hdthList.getNxtApprSplitting() %>"> <%if(hdthList.getNxtApprSplitting() >1) {%>
									<img alt="Trang Dau" src="../images/first.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
									<%}else {%> <img alt="Trang Dau" src="../images/first.gif">
									&nbsp; <%} %> <% if(hdthList.getNxtApprSplitting() > 1){ %> <img
									alt="Trang Truoc" src="../images/prev.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')">
									&nbsp; <%}else{ %> <img alt="Trang Truoc"
									src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = hdthList.getNextSplittings();
													for(int j = 0; j < listPage.length; j++){
												%> <% 
												
												System.out.println("Current page:" + hdthList.getNxtApprSplitting());
												System.out.println("List page:" + listPage[j]);
												
												if(listPage[j] == hdthList.getNxtApprSplitting()){ %> <a
									style="color: white;"><%= listPage[j] %>/ <%=hdthList.getTheLastSplitting() %></a>
									<%}else{ %> <a
									href="javascript:View(document.forms[0].name, <%= listPage[j] %>, 'view')"><%= listPage[j] %></a>
									<%} %> <input type="hidden" name="list"
									value="<%= listPage[j] %>" /> &nbsp; <%} %> <% if(hdthList.getNxtApprSplitting() < hdthList.getTheLastSplitting()){ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')">
									&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
									src="../images/next_d.gif"> &nbsp; <%} %> <%if(hdthList.getNxtApprSplitting() == hdthList.getTheLastSplitting()) {%>
									<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%}else{ %>
									<img alt="Trang Cuoi" src="../images/last.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, -1, 'view')">
									&nbsp; <%} %>
								</TD>
							</tr>
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>

<script type="text/javascript"> 
	  <%for(int k=0; k < m; k++) {%>
		dropdowncontent.init('ktlist<%= k %>', "left-top", 300, "click");
	   
	  <%}%>
</script>

</form>
</body>
<%

try{
	hdthList.closeDB(); 
	session.setAttribute("obj",null);
	hoadonlist.close();
}catch(Exception er){
	
}
	}
%>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.center.beans.nhiemvu.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% INhiemVuList obj = (INhiemVuList)session.getAttribute("obj"); %>
<% List<INhiemVu> nvList = (List<INhiemVu>)obj.getList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{
	int[] quyen = new  int[5];
	quyen = util.Getquyen("104",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);
	System.out.println(quyen[4]);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>OneOne - Nhiệm Vụ</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	
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
	 function confirmLogout()
	 {
	    if(confirm("Ban co muon dang xuat?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 function submitform()
	 {   
	    document.forms["nvForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["nvForm"].action.value = "Tao moi";
	    document.forms["nvForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["nvForm"].diengiai.value = "";	   
	    submitform();
	 }
	 
	 function xuatExcel()
	 {
	 	document.forms['nvForm'].action.value= 'excel';
	 	document.forms['nvForm'].submit();
	 	document.forms['nvForm'].action.value= '';
	 }
	 
	</SCRIPT>
</head>
<body>
	<form name="nvForm" method="post" action="../../NhiemVuSvl">
		<input type="hidden" name="userId" value="<%= userId %>" >
		<input type="hidden" name="action" value="1" >
		<input type="hidden" name="msg" value="<%= obj.getMessage() %>" >
        
		<script type="text/javascript">if(document.forms["nvForm"].msg.value != "") alert(document.forms["nvForm"].msg.value);</script>
		<%obj.setMessage(""); %>
		<div id="main" style="width:100%; padding-left:2px">
			<div align="left" id="header" style="width:100%; float:none">
		    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
		        	Quản lý chỉ tiêu > Nhiệm Vụ
		        </div>
		        <div align="right" style="padding:5px" class="tbnavigation">
		        	Chào mừng bạn <%= userTen %> &nbsp;
		        </div>
		    </div>
		  	<div id="cotent" style="width:100%; float:none">
		    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
		        <fieldset>
		            <legend class="legendtitle"> Tiêu chí tìm kiếm  </legend>
		                <TABLE width="100%" cellpadding="6px" cellspacing="0px">								                 
		                    <TR>
		                        <TD class="plainlabel" valign="middle" width="15%">Diễn giải </TD>
		                        <TD class="plainlabel" valign="middle">
		                            <input type="text" name="diengiai" value="<%= obj.getDienGiai() %>">
		                        </TD>                        
		                    </TR>
		                    
		                    <tr style="background-color:#C5E8CD">
		                        <td colspan="2">
		                            <a class="button" href="javascript:submitform()">
		                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;
		                            <a class="button2" href="javascript:clearform()">
		                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại </a>&nbsp;&nbsp;&nbsp;&nbsp;
		                                
		                                	<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
		                        </td>
		                    </tr>        					
		                </TABLE>                      
		        </fieldset>                      
		    	</div>
		        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
		        	<fieldset>
		            	<legend><span class="legendtitle">Nhiệm Vụ </span>&nbsp;&nbsp;&nbsp;
		                	<%if(quyen[0]!=0) {%>
		                	<a class="button3" href="javascript:newform()">
		                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
		                           <%} %>
		                </legend>
		            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
				                    <TH align="center">Mã nhiệm vụ</TH>
				                    <TH align="center">Tiêu chí</TH>
				                    <TH align="center">Diễn giải</TH>
				                    <TH align="center">Đối tượng</TH>
				                    <TH align="center">Tự động</TH>
				                    <TH align="center">Loại tiêu chí</TH>
				                    <TH align="center">Ngày tạo</TH>
				                    <TH align="center">Người tạo</TH>
				                    <TH align="center">Ngày sửa</TH>
				                    <TH align="center">Người sửa</TH>
				                    <TH align="center">Tác vụ</TH>
				                </TR>
								<%      
			                        INhiemVu nv = null;
			                        int size = nvList.size();
			                        int m = 0;
			                        while (m < size) {
			                            nv = nvList.get(m);			                            
			                            
			                            if(m%2==0){
			                    %> 
		                         <TR class='tbdarkrow'>
		                         <%}else{ %>
		                           <TR class='tblightrow'>
		                         <%} %>
				                    <TD align="center"><%= nv.getId() %></TD>
				                    <TD align="left"><%= nv.getTieuChi() %></TD>
				                    <TD align="left"><%= nv.getDienGiai() %></TD>										                                    
				                    <TD align="center"><%= nv.getDoiTuong() %></TD>
				                    <TD align="center"><%= nv.getIsTuDong() ? "Có" : "Không" %></TD>										                                    
				                    <TD align="center"><%= nv.getTenLoaiTieuChi() %></TD>
				                    <TD align="center"><%= nv.getNgayTao() %></TD>
				                    <TD align="center"><%= nv.getNguoiTao() %></TD>
				                    <TD align="center"><%= nv.getNgaySua() %></TD>	
				                    <TD align="center"><%= nv.getNguoiSua() %></TD>
				                    <TD align="center">
				                        <TABLE cellpadding="1" cellspacing="0">
				                            <TR><TD>
				                            	<%if(quyen[2]!=0) {%>
				                                <A href = "../../NhiemVuUpdateSvl?userId=<%=userId%>&update=<%= nv.getId()%>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Cap nhat" border="0"></A>
				                            	<%} %>
				                            </TD><TD>
				                            	<%if(quyen[1]!=0) {%>
				                                <A href = "../../NhiemVuSvl?userId=<%=userId%>&delete=<%= nv.getId() %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa nhiệm vụ này ?')) return false;"></A>
				                            	<%} %>
				                            </TD></TR>
				                        </TABLE>									
				                    </TD>
				                </TR>
		                     <% m++; }%>														
	
	
						</TABLE>
		            </fieldset>
		        </div>
		    </div>  
		</div>
	</form>
</body>
<%} %>
</html>
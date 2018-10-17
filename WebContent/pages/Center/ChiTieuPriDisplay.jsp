<%@page import="geso.traphaco.center.beans.chitieu.IChiTieu"%>
<%@page import="geso.traphaco.center.beans.chitieu.imp.ChiTieuNPP"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="geso.traphaco.center.beans.chitieu.imp.ChiTieu"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhomkhuyenmai.INhomkhuyenmai" %>
<%@ page  import = "geso.traphaco.center.beans.nhomkhuyenmai.imp.Nhomkhuyenmai" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%
    NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen"); 
	
 	IChiTieu objbean=(IChiTieu)session.getAttribute("obj");
 	
	ResultSet listnhapp= objbean.getRsChiTieuNpp();
	String check1=(String)session.getAttribute("check");
	String loaichitieu=(String)session.getAttribute("loaichitieu");
	ResultSet rschitieunv=objbean.getRsChitieunhanvien();
	ResultSet rsCTGiamSat=objbean.getrsCT_GiamSat();
	
	ResultSet rsCT_ASM=objbean.getrsCT_ASM();
	
	ResultSet ctBmRs  =objbean.getCt_BmRs();

	String []nhomsp=objbean.getNhomSp();
	ResultSet rsVung=objbean.getRsVung();
	ResultSet rsKv=objbean.getRsKv();
	
	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 $("ul.tabs").tabs("div.panes > div");
});
</script>
<SCRIPT language="JavaScript" type="text/javascript">


function submitform()
{
    document.forms["ChiTieuTTForm"].submit();
}

</SCRIPT>
    <script type="text/javascript">
$( function() {
	//Created By: Brij Mohan
	//Website: http://techbrij.com
	function groupTable($rows, startIndex, total)
	{
		if (total === 0)
		{
			return;
		}
		var i , currentIndex = startIndex, count=1, lst=[];
		var tds = $rows.find('td:eq('+ currentIndex +')');
		var ctrl = $(tds[0]);
		lst.push($rows[0]);
		
		
		for (i=1;i<=tds.length;i++){
		if (ctrl.text() ==  $(tds[i]).text()){
		count++;
		$(tds[i]).addClass('deleted');
		lst.push($rows[i]);
		}
		else{
			if (count>1){
			ctrl.attr('rowspan',count);
			groupTable($(lst),startIndex+1,total-1)
			}
			count=1;
			lst = [];
			ctrl=$(tds[i]);
			lst.push($rows[i]);
		}
		}
	}
	var rowCount = $('#sku1 tr').length;

	groupTable($('#sku1 tr:has(td)'),0,rowCount);
	$('#sku1 .deleted').remove();
	});
    </script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="ChiTieuTTForm" method="post" action="../../ChiTieuPriNewSvl" >
<input type="hidden"  name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%=objbean.getID()%>">
<input type=hidden name="luutam" value="<%=check1%>"><!--  de luu gia tri cua radio khi cho -->
<input type="hidden" name="loaichitieu" value="<%=loaichitieu%>">
<input type= hidden name="tenform" value="update">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu > Khai báo > Chỉ tiêu Mua Vào&gt;Xem</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ChiTieuPriSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay về" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="2" align="left" ></TD>
						    
						 	<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			
				
				<TABLE  width="100%" border="0" cellpadding="0"  cellspacing="1" >
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin chỉ tiêu </LEGEND>
						<TABLE class="tablecontent" border="0" width="100%" cellpadding="6" cellspacing="0">
						 
							<TR>
								<TD width="20%" class="plainlabel" >Tháng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<input  type="text"  readonly="readonly" name="thang" style="background-color:white;" value="<%=objbean.getThang()%>"> 
									
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Năm</TD>
						  	  	<TD class="plainlabel">
								<input type="text"  readonly="readonly" name="nam" style="background-color:white;" value="<%=objbean.getNam()%>"> 
									
						  	  	</TD>
						  </TR>
						  
				  		   <TR>
						  	  	<TD class="plainlabel">Số chỉ tiêu</TD>
						  	  <TD class="plainlabel">
						  	  <input  type="text" readonly="readonly" name="tongchitieu" value="<%=formatter.format(Math.round(objbean.getChitieu()))%>" > 
						  	  	</TD>
						  	</TR>
						  	<tr class="plainlabel">
                             <td>Đơn vị kinh doanh </td>
                             <td>
                             <input type="text"  readonly="readonly" name="dvkd" style="background-color:white;" value="<%=objbean.getTenDVKD()%>"> 
                             </td>                           
                             </tr>
                             
                             
                             	<TR>
						  	  	<TD class="plainlabel">Ngày bắt đầu</TD>
						  	  <TD class="plainlabel" >
						  
						  	  <input type="text" class="days" name="ngaybatdau" value="<%=objbean.getNgayBatDau()%>" > 
						  	  		
						  	  	</TD>
						  	</TR>
						  	  
						  	<TR>
						  	  	<TD class="plainlabel">Ngày kết thúc</TD>
						  	  	<TD class="plainlabel">
						  		<input type="text" readonly="readonly" name="ngayketthuc" value="<%=objbean.getNgayKetThuc()%>">  
						  			</TD>
						  	</TR>		 
						  	
						      <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel">
						  	  	<textarea name="diengiai" readonly="readonly"  style="width: 50%"  rows="2"><%=objbean.getDienGiai()%></textarea>	
						  	  	</TD>
						  	</TR>  	
						  	<tr>
						  	<TD class="plainlabel">Kênh bán hàng </TD>
						  	  	<TD class="plainlabel">
										<input type="text" readonly="readonly" name="kenhid" value="<%=objbean.getTenKenh()%>">  
															  	  	
						  	  	</TD>
						  	</tr>					
						  		<tr>
						  		<TD class="plainlabel">Vùng</TD>
						  		<TD class="plainlabel">
						  	  	 <select name="vungId" multiple="multiple" onchange="submitform();">
			       <option value="">Chọn hết</option>
                        <% if(rsVung != null) {
                         while(rsVung.next()) 
                         {
                           if(objbean.getVungId().indexOf(rsVung.getString("pk_seq")) >= 0 ){ %>
                             <option value="<%= rsVung.getString("pk_seq") %>" selected style="padding: 2px" ><%= rsVung.getString("ten") %></option>
                         <%}else { %>
                         	<option value="<%=rsVung.getString("pk_seq") %>" style="padding: 2px"><%= rsVung.getString("ten") %></option>
                         <%} }}%>        	
                    </select>
                    </TD>
						  	</tr>		
						  	
						  		<tr>
						  		<TD class="plainlabel">Khu vực</TD>
						  		<TD class="plainlabel">
						  	  	 <select name="kvId" multiple="multiple"   onchange="submitform();">
			       <option value="">Chọn hết</option>
                        <% if(rsKv != null) {
                         while(rsKv.next()) 
                         {
                           if(objbean.getKvId().indexOf(rsKv.getString("pk_seq")) >= 0 ){ %>
                             <option value="<%= rsKv.getString("pk_seq") %>" selected style="padding: 2px" ><%= rsKv.getString("ten") %></option>
                         <%}else { %>
                         	<option value="<%=rsKv.getString("pk_seq") %>" style="padding: 2px"><%= rsKv.getString("ten") %></option>
                         <%} }}%>        	
                    </select>
                    </TD>
				</tr>	
						  	  	
						  	
						</TABLE>
					
					<ul class="tabs">
						<li><a href="#tabnpp">Đối tượng</a></li>
						<li><a href="#tabss">Phụ trách tỉnh</a></li>
						<li><a href="#tabasm">Phụ trách vùng</a></li>
						<li><a href="#tabbm">Giám đốc miền</a></li>
					</ul>
					<div class="panes">
					<div id="tabnpp">	
						
						<TABLE id="sku1"  width="100%" border="1" cellspacing="1" cellpadding="0" >							
							
								<tr>
									<TH colspan="5"  class="tbheader">CHỈ TIÊU Chi nhánh / Đối tác </TH>
								</tr>
									<TR class="tbheader">
									<TH width="15%">Vùng </TH>
									<TH width="15%">Khu vực </TH>
									<TH width=15%>Mã Chi nhánh / Đối tác </TH>
									<TH >Tên Chi nhánh / Đối tác</TH>
									<TH width="10%">Chỉ tiêu </TH>
								
								</TR>
								<%
									int m=0;
									double tongchitieu=0;
									double tongkhuvuc=0;
									double tongvung=0;
									String vung_bk="";
									String khuvuc_bk="";	
								
							if(listnhapp != null)
							{
								while (listnhapp.next()){
									
									if(m==0){
										khuvuc_bk=listnhapp.getString("khuvuc");
										vung_bk=listnhapp.getString("vung");
									}
									if(m>0){
										if(!listnhapp.getString("khuvuc").equals(khuvuc_bk)){
										
											%>
											<tr  style="color: blue;font-weight: bold; ">
											<TD > <%= vung_bk%>  </TD>
											<TH colspan="3"   >Tổng cộng khu vực :<%=khuvuc_bk  %> </TH>
											<TH   align="right"><%=formatter.format(tongkhuvuc) %> </TH>
											</tr>
										
											 <%
											tongkhuvuc=0;
											khuvuc_bk=listnhapp.getString("khuvuc");
										
										
										}
										
										if(!listnhapp.getString("vung").equals(vung_bk)){
											
											%>
											<tr style="color: red;font-weight: bold; ">
										
											<TH colspan="4"  >Tổng cộng vùng : <%=vung_bk %></TH>
											<TH    align="right"><%=formatter.format(tongvung) %> </TH>
											</tr>
										
											 <%
											 tongvung=0;
											 vung_bk=listnhapp.getString("vung");
										
										
										}
										
									}
									
									tongchitieu=tongchitieu+listnhapp.getDouble("chitieu")	;										
									tongkhuvuc=tongkhuvuc+listnhapp.getDouble("chitieu")	;
									
									tongvung=tongvung+listnhapp.getDouble("chitieu")	;
															%>
									<tr>
									<TD > <%=listnhapp.getString("vung") %>  </TD>
									<TD > <%=listnhapp.getString("khuvuc") %> </TD>
									<TD ><%=listnhapp.getString("sitecode") %></TD>
									<TD ><%=listnhapp.getString("ten") %> </TD>
									<TD align="right" > <%=formatter.format(listnhapp.getDouble("chitieu"))%> </TD>
								    </tr>
									<%
									m++;
								}
							}
							%>
									<tr  style="color: blue;font-weight: bold; ">
									<TD > <%=vung_bk %>  </TD>
									<TH colspan="3"  >Tổng cộng khu vực:<%=khuvuc_bk %> </TH>
									<TH   align="right"><%=formatter.format(tongkhuvuc) %> </TH>
									</tr>
											<tr  style="color: blue;font-weight: bold; ">
										
											<TH colspan="4" >Tổng cộng vùng : <%=vung_bk %></TH>
											<TH  align="right"><%=formatter.format(tongvung) %> </TH>
											</tr>		
									<tr>
									<TH colspan="4"  class="tbheader">Tổng cộng </TH>
									<TH  class="tbheader"  align="right"><%=formatter.format(tongchitieu) %> </TH>
								</tr>
								
						</TABLE>	
						
						</div>
					
					<div id="tabss">
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<tr>
									<TH colspan="4"  class="tbheader">CHỈ TIÊU Phụ trách tỉnh </TH>
								</tr>
								<TR class="tbheader">
									<TH width="10%">Số thứ tự </TH>
									<TH width=15%>Mã  </TH>
									<TH >Tên </TH>
								
									<TH width="10%">Chỉ tiêu </TH>
								
								</TR>
								<%
								tongchitieu=0;
									m=0;
									
																				if(rsCTGiamSat != null)
																				{
																					while (rsCTGiamSat.next()){
																						tongchitieu=tongchitieu+rsCTGiamSat.getDouble("chitieu");
																					
															%>
									<tr>
									<TD > <input type ="text" name="sott" readonly="readonly" style="width :100%" value=<%=m+1 %> >   </TD>
									<TD ><input type ="text" name="mavung" style="width :100%" readonly="readonly" value="<%=rsCTGiamSat.getString("manhanvien") %>" ></TD>
									<TD ><input type ="text" name="tenvung" style="width :100%" readonly="readonly" value="<%=rsCTGiamSat.getString("ten") %>" ></TD>
									<TD > <input type ="text" name="chitieu"  style="width :100%;text-align:right "  readonly="readonly" value="<%=formatter.format(rsCTGiamSat.getDouble("chitieu"))%>" > </TD>
								    </tr>
									
									<%
									m++;
								}
							}
							%>
								<tr>
									<TH colspan="3"  class="tbheader">Tổng cộng </TH>
									<TH  class="tbheader"  align="right"><%=formatter.format(tongchitieu) %> </TH>
								</tr>
								
						</TABLE>	
					</div>
					<div id="tabasm">
					
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<tr>
									<TH colspan="4"  class="tbheader">CHỈ TIÊU Phụ trách vùng </TH>
								</tr>
								<TR class="tbheader">
									<TH width="10%">Số thứ tự </TH>
									<TH width=15%>Mã  </TH>
									<TH >Tên </TH>
								
									<TH width="10%">Chỉ tiêu </TH>
								
								</TR>
								<%
							tongchitieu=0;
									m=0;
							if(rsCT_ASM != null)
							{
								while (rsCT_ASM.next()){
									tongchitieu=tongchitieu+rsCT_ASM.getDouble("chitieu");
																					
															%>
									<tr>
									<TD > <input type ="text" name="sott" readonly="readonly" style="width :100%" value=<%=m+1 %> >   </TD>
									<TD ><input type ="text" name="mavung" style="width :100%" readonly="readonly" value="<%=rsCT_ASM.getString("manhanvien") %>" ></TD>
									<TD ><input type ="text" name="tenvung" style="width :100%" readonly="readonly" value="<%=rsCT_ASM.getString("ten") %>" ></TD>
									<TD > <input type ="text" name="chitieu" style="width :100%;text-align:right " readonly="readonly"   value="<%=formatter.format(rsCT_ASM.getDouble("chitieu"))%>" > </TD>
								    </tr>
									
									<%
									m++;
								}
							}
							%>
								<tr>
									<TH colspan="3"  class="tbheader">Tổng cộng </TH>
									<TH  class="tbheader"  align="right"><%=formatter.format(tongchitieu) %> </TH>
								</tr>
						</TABLE>	
					</div>
					
					<div id="tabbm">
					
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="0" >							
								<tr>
									<TH colspan="4"  class="tbheader">CHỈ TIÊU Giám đốc miền </TH>
								</tr>
								<TR class="tbheader">
									<TH width="10%">Số thứ tự </TH>
									<TH width=15%>Mã  </TH>
									<TH >Tên </TH>
								
									<TH width="10%">Chỉ tiêu </TH>
								
								</TR>
								<%
							tongchitieu=0;
									m=0;
							if(ctBmRs != null)
							{
								while (ctBmRs.next()){
									tongchitieu=tongchitieu+ctBmRs.getDouble("chitieu");
																					
															%>
									<tr>
									<TD > <input type ="text" name="sott" readonly="readonly" style="width :100%" value=<%=m+1 %> >   </TD>
									<TD > 
									<input type ="hidden" name="idbm" style="width :100%" readonly="readonly" value="<%=ctBmRs.getString("pk_seq") %>" >
									<input type ="text" name="mabm" style="width :100%" readonly="readonly" value="<%=ctBmRs.getString("manhanvien") %>" ></TD>
									<TD ><input type ="text" name="tenbm" style="width :100%" readonly="readonly" value="<%=ctBmRs.getString("ten") %>" ></TD>
									<TD > <input type ="text" name="chitieubm" style="width :100%;text-align:right " onchange=" Dinhdang(this)"    value="<%=formatter.format(ctBmRs.getDouble("chitieu"))%>" > </TD>
								    </tr>
									
									<%
									m++;
								}
							}
							%>
								<tr>
									<TH colspan="3"  class="tbheader">Tổng cộng </TH>
									<TH  class="tbheader"  align="right"><%=formatter.format(tongchitieu) %> </TH>
								</tr>
						</TABLE>	
					</div>
					
					
					
						</div>
						
												
						</FIELDSET>						
					</TD>
				</TR>
				</TABLE>
						
			
		</TD>
	</TR>
	</TABLE>
</form>
<script type="text/javascript">

</script>
</BODY>
</HTML>
<%
try{
	
	objbean.closeDB();
}catch(Exception er){
	
}
%>
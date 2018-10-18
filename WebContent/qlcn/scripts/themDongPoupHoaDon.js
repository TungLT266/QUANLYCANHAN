

/* 
 * stt : số dòng ngoài
 * k: số dòng trong popuphoadon
 * l: số dòng cần thêm
 * d: số dòng tối đa có thể thêm trong pop up hóa đơn
 */
function getHoaDonTable(stt, k, l, d) {
	var i = stt;
	var html = '';
	var n = 0;
	d = parseFloat(d);
	if ($("input[name='mausoHD"+stt+"']").length < d) {
		for (var m = 0; m < l; m++) {
			n = parseFloat(k)+parseFloat(m);
			html += 
			   '   		<tr>   '+
			    '  		<td>'+
			     ' 			<input type="text" name= "mausoHD'+i+'" id="mausoHD'+i+'_'+n+'" value = "" style="width: 100%; border-radius:4px; height: 20px;" onchange="upperLetters(this);" onpaste="$(\'#mausoHD'+i+''+n+'\').autocomplete(\'MAUHOADON_DNTT.jsp?\');" onkeyup="$(\'#mausoHD'+i+''+n+'\').autocomplete(\'MAUHOADON_DNTT.jsp?\');">'+
			      					
			      
			      		'</td>'+
			      		'<td>'+
			      		'	<input type="hidden" name= "maHD'+i+'" value = "" style="width: 100%; border-radius:4px; height: 20px;" readonly="readonly" >'+									      	
			      		'	<input type="text" name= "kyhieu'+i+'" id= "kyhieu'+i+'_'+n+'"  value = "" style="width: 100%; border-radius:4px; height: 20px;" onchange="upperLetters(this);" onpaste="$(\'#kyhieu'+i+''+n+'\').autocomplete(\'KYHIEU_DNTT.jsp?\');" onkeyup="$(\'#kyhieu'+i+''+n+'\').autocomplete(\'KYHIEU_DNTT.jsp?\');">'+
			      			
			      		' </td>'+
			      		
			      		'<td>'+
			      		'	<input type="text" name= "sohd'+i+'"   value = "" style="width: 100%; border-radius:4px; height: 20px;" >'+
			      		'</td>'+
		
			      		'<td>'+
			      		'	<input type="text" name= "ngayhd'+i+'"  value = "" style="width: 100%; border-radius:4px; height: 20px;"  class="days" onclick="$(this).datepicker({ changeMonth: true, changeYear: true });">'+
			      		'</td>'+
			      		'<td>'+
			      		'	<input type="text" name= "tenncc'+i+'"  id =  "tenncc'+i+'_'+n+'" value = "" style="width: 100%; border-radius:4px; height: 20px;" >'+
			      		'</td>'+
			      		'<td><input type="text" name=  "diaChiNCC'+i+'" id = "diaChiNCC'+i+'_'+n+'"  value = "" style="width: 100%; border-radius:4px; height: 20px;"></td>'+
			      		'<td>'+
			      		'	<input type="text" name=  "mst'+i+'" id = "mst'+i+'_'+n+'"  value = "" style="width: 100%; border-radius:4px; height: 20px;" onpaste="$(this).autocomplete(\'MST_NCC_DNTT.jsp?\');" onkeyup="$(this).autocomplete(\'MST_NCC_DNTT.jsp?\');">'+
			      		
		
			      		'</td>'+
			      		
			      		'<td>'+
			      		'	<input type="text" name= "tienhang'+i+'" value = "" style="width: 100%; border-radius:4px; height: 20px; text-align: right; " onkeypress="return keypress(event);" onkeyup="tinhthue(true, '+i+', '+n+');">'+
			      		'</td>'+
		
			      		'<td>'+
			      		'	<input type="text" name= "thuesuat'+i+'" value = "" style="width: 100%; border-radius:4px; height: 20px; text-align: right;" onkeypress="return keypress(event);" onkeyup="tinhthue(true, '+i+', '+n+');">'+
			      		'	<input type="hidden" name="thuesuat_goc'+i+'" value = "" style="width: 100%" >'+
			      		'</td>'+
		
			      		'<td>'+
			      		'	<input type="text" name= "thue'+i+'" value = ""  style="width: 100%; border-radius:4px; height: 20px; text-align: right;" onkeypress="return keypress(event);" onkeyup="tinhTienThueTuDo(this,'+i+')" >'+
			      		'</td>'+
		
			      		'<td>'+
			      		'	<input type="text" name= "cong'+i+'" value = "" style="width: 100%; border-radius:4px; height: 20px; text-align: right;" readonly>'+
			      		'</td>'+
		
			      		'<td>'+
			      		'	<input type="text" name= "ghichu'+i+'" value = "" style="width: 100%; border-radius:4px; height: 20px;" >'+
			      		'</td>'+
		
						'</tr>';
		}
	}
	else {
		alert('Đã đạt số dòng tối đa được thêm là '+d+' dòng');
	}
     
  		return html;
}


function themDongHoaDon(e, ele) {
	//bỏ vào on click của nút onclick="themDongHoaDon(this)"
	//Đặt id nút thêm dòng là themdong_stt vd : themdonghoadon_0
	//Đặt id table hóa đơn là hoaDonTable_stt vd : hoaDonTable_0
	
	e.preventDefault();
	var stt = $(ele).attr("id").split("_")[1];
//	alert(stt);
	var i = $("input[name='mausoHD"+stt+"']").length;
	$("#subcontent"+stt+" table").find('tr:last').after(getHoaDonTable(stt, i, 2, 40));
	
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
	$(ele).blur();
}
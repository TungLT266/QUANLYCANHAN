//ham lon tron 4 số
function Lamtronso(e) {
    
  
    var val = e.value;
    var re = /^([0-9]+[\.]?[0-9]?[0-9]?[0-9]?[0-9]?|[0-9]+)$/g;
    var re1 = /^([0-9]+[\.]?[0-9]?[0-9]?[0-9]?[0-9]?|[0-9]+)/g;
    if (re.test(val)) {
        //do something here
    }
    else
    {
         val = re1.exec(val);
        if (val) {
            e.value = val[0];
        } else {
            e.value = "";
        }
    }
    
    
}

/*function roundnumber4so(value, decimals) {
    return Number(Math.round(value + 'e' + decimals) + 'e-' + decimals);
}*/
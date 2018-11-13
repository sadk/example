function hexCharCodeToStr(hexCharCodeStr) {
	　　var trimedStr = hexCharCodeStr.trim();
	　　var rawStr =  　trimedStr.substr(0,2).toLowerCase() === "0x" ?  trimedStr.substr(2) :  trimedStr;
	　　var len = rawStr.length;
	　　if(len % 2 !== 0) {
	　　　　console.log("Illegal Format ASCII Code!");
	　　　　return "";
	　　}
	　　var curCharCode;
	　　var resultStr = [];
	　　for(var i = 0; i < len;i = i + 2) {
	　　　　curCharCode = parseInt(rawStr.substr(i, 2), 16); // ASCII Code Value
	　　　　resultStr.push(String.fromCharCode(curCharCode));
	　　}
	　　return resultStr.join("");
}

$(document).ready(function(){
	var uauth = null;
	var authList = null;
	
	try{
		uauth = getCookie("uauth");
		authList = hexCharCodeToStr(uauth);
		//alert(authList);
		authList = JSON.parse(authList);
		//console.log(authList)
	}catch(e) {
		console.log(e);
		
		$("[code]").each(function(index){
			$(this).remove();
		})
		return ;
	}
	
	
	$("[code]").each(function(index){
		var curr = $(this) ; 
		var isPermission = false;
		
		if (authList!=null) {
			for (var i=0;i<authList.length;i++) {
				if (authList[i] == curr.attr("code")) {
					isPermission = true;
					break;
				}
			}
		}
		
		if (isPermission == false) {
			$(this).remove();
		}
    });
	
});



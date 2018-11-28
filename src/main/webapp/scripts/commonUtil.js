/**
 * 判断对象是否为无效对象
 * @param obj
 * @returns {Boolean}
 */
function isInvalid(obj) {
	if(typeof(obj) == 'undefined' || obj == null) {
		return true;
	}
	
	obj = obj+"";
	if(obj == ''){
		return true
	}
	return false;
}

function isNotInvalid(obj) {
	return !isInvalid(obj);
}
/**
 * @class Message 系统提示工具类 
 * 
 * @description 封装前台工具
 * 
 * @example
 * 初始函数 new Util({});
 * @since version 0.0.1
 * @see
 * 
 */
function Util() {
}
Util.prototype = {
	/**
	 * @description 日期比较 beginDate 是否小于 endDate
	 * @param beninDate：开始时间
	 * @param endDate ：结束时间
	 * @param format ：{yyyy:年,mm:月,dd:天}
	 * @return true||false
	 * 
	 */
	dateComple : function(beninDate, endDate, format) { // 初始化数值
		if (beninDate == '' || endDate == '') {
			return false;
		}
		// 实现js重载
		if (arguments.length == 3) {
			// 传入日期格式比较日期大小
			return this.dateCompleByFormat(beninDate, endDate, format)
		}
		if (arguments.length == 2) {
			// 比较日期大小 格式为：'yyyy-mm-dd'
			return this.dateCompleDefault(beninDate, endDate)
		}
	},
	/**
	 * @description 日期比较   beginDate 是否小于 endDate
	 * @param beninDate：开始时间
	 * @param endDate ：结束时间
	 * @param format ：{yyyy:年,mm:月,dd:天}
	 * @return true||false
	 * 
	 */
	dateCompleByFormat : function(beninDate, endDate, format) {
		var startTime = this.str2Date(beninDate, format);
		var startTimes = startTime.getTime();

		var endTime = this.str2Date(endDate, format);
		var endTimes = endTime.getTime();
		if (!this.isAllNotEmpty(startTimes, endTimes)) {
			return false;
		}
		if (startTimes > endTimes)
			return false;
		else
			return true;
	},
	/**
	 * @description 日期比较  beginDate 是否小于 endDate(格式为'yyyy-mm-dd')
	 * @param beninDate：开始时间
	 * @param endDate ：结束时间
	 * @return true||false
	 * 
	 */
	dateCompleDefault : function(beninDate, endDate) {
		return this.dateCompleByFormat(beninDate, endDate, "yyyy-mm-dd");
	},
	/**
	 * @description 判断对象是否为空
	 * @param obj： 待判断的对象
	 * @return true||false
	 * 
	 */
	isEmpty : function(obj) {
		
		if (obj===undefined || obj==="" || obj===null)
			return true;
		else
			return false;
	},
	/**
	 * @description 判断不定个数个对象是否都为非空
	 * @return true||false
	 * 
	 */
	isAllNotEmpty : function() {
		var flag = true;
		for ( var i = 0; i < arguments.length; i++) {
			if (this.isEmpty(arguments[i])) {
				flag = false;
				break;
			}
		}
		return flag;
	},
	/**  
	 * @description 时间格式的字符串转化为Date
	 * @param dateStr：待转换的时间串
	 * @param format ：{yyyy:年,mm:月,dd:天}
	 * @return true||false
	 * 
	 */
	str2Date : function(dateStr, format) {
		format=format.toString();
		var yearPosition = format.indexOf("yyyy");
		var monthPosition = format.indexOf("mm");
		var dayPosition = format.indexOf("dd");
		if (yearPosition == -1 || monthPosition == -1 || dayPosition == -1) {
			alert("传入的格式化模版不合法");
			return;
		}
		var date = new Date(dateStr.substr(yearPosition, 4), dateStr.substr(
				parseInt(monthPosition-1), 2), dateStr.substr(dayPosition, 2));
		return date;
	},
	/**
	 *@description 将数字格式化为 ：123,456.12
	 *@param s:带处理数字
	 *@paramn：几位字符用逗号分隔
	 *@paramm:保留几位小数
	 *
	 */
	formatMoney : function(s, n, m) {
		n = n > 0 && n <= 20 ? n : 2;
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(m) + "";
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
		t = "";
		for (i = 0; i < l.length; i++) {
			t += l[i] + ((i + 1) % n == 0 && (i + 1) != l.length ? "," : "");
		}
		return t.split("").reverse().join("") + "." + r;
	},
	/**
	 *@description 将对象转为string
	 *@param o:待处理对象
	 *@return 字符串
	 *
	 */
	obj2str : function(obj) {
		switch (typeof (obj)) {
		case 'object':
			var ret = [];
			if (obj instanceof Array) {
				for ( var i = 0, len = obj.length; i < len; i++) {
					ret.push(obj2Str(obj[i]));
				}
				return '[' + ret.join(',') + ']';
			} else if (obj instanceof RegExp) {
				return obj.toString();
			} else {
				for ( var a in obj) {
					ret.push(a + ':' + obj2Str(obj[a]));
				}
				return '{' + ret.join(',') + '}';
			}
		case 'function':
			return 'function() {}';
		case 'number':
			return obj.toString();
		case 'string':
			return "\""
					+ obj.replace(/(\\|\")/g, "\\$1").replace(
							/\n|\r|\t/g,
							function(a) {
								return ("\n" == a) ? "\\n"
										: ("\r" == a) ? "\\r"
												: ("\t" == a) ? "\\t" : "";
							}) + "\"";
		case 'boolean':
			return obj.toString();
		default:
			return obj.toString();
		}
	},
	/**
	 *@description 字符串转为json对象
	 *@param str:待处理字符串
	 *@return json
	 *
	 */
	strToJson : function(str) {
		var json = eval('(' + str + ')');
		return json;
	},
	 /**
	 *@description date 转化为字符串
	 *@param date:待处理日期
	 *@return format:返回字符串格式 {yyyy:年,mm:月,dd:天}
	 *
	 */
	date2String:function(date,format){
	try{
		// 获取日期年份
		var yearStr=date.getFullYear();

		console.info("yearStr="+yearStr);
		// 获取日期月份
		var monthStr=date.getMonth();
		monthStr=(parseInt(monthStr)+1);
		if(monthStr<10){
		monthStr="0"+monthStr.toString();
		}
		console.info("monthStr="+monthStr);
		// 获取日期天数
		var dayStr=date.getDate();
		dayStr=parseInt(dayStr);
		if(dayStr<10){
			dayStr="0"+dayStr.toString();
		}
		console.info("dayStr="+dayStr);
		
		var dateStr=format.replace("yyyy",yearStr).replace("mm",monthStr).replace("dd",dayStr);
		console.info("dateStrA="+dateStr);
		return dateStr;
	}
	catch(e){
		alert("日期不合法");
		return null;
	}
	}
}

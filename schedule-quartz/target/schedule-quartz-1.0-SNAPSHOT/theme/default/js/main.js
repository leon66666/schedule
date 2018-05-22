/*
 * 
 * 
 */
String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
$(function() {
	$('a,input[type="button"],input[type="submit"],button').bind('focus',
			function() {
				if (this.blur) { // 如果支持 this.blur
					this.blur();
				}
			});
});
// 未读信息-加入信息推送监听
function fnload() {
	$.ajax({
		url : "../getUnreadMailsCount.action",
		type : 'get',
		dataType : 'json',
		error : function() {
		},
		success : function(response) {
			$("#mailsCount").html(response.count);
		}
	});

}
function tabIt2(o) {
	var id = o.id;
	$("#" + id).parent().children().each(function(i) {
		$("#" + id).parent().children().removeClass();
		$("#" + id).parent().children().addClass("right_tab_unselect2");
		$("#" + $("#" + id).parent().children().get(i).id + "_content").hide();
	});
	$("#" + id).removeClass();
	$("#" + id).addClass("right_tab_select2");
	$("#" + id).show();
	$("#" + id + "_content").show();

}
function openServiceItems(type) {
	if (type == 0) {
		url = "service.html";
	} else {
		url = "privacy.html";
	}
	window
			.open(
					url,
					'regconfirm',
					'height=584,width=718,toolbar=no,menubar=no,scrollbars=no,resizable=false,location=no,status=no');
	return true;
}


function sendPhoneCode() {
	var timer;
	var i;
	var phone = $("#phone").attr("value");
	var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if (!mobile.test(phone)) {
		alert("请正确填写您的手机号码");
		return;
	}
	$.ajax({
		url : "../sendPhoneCode.action?phone=" + phone,
		type : "POST",
		dataType : "html",
		error : function() {
			$("#reveiveActiveCode").removeAttr('disabled');
			$("#reveiveActiveCode")
					.css("background",
							"url(/theme/default/images/smail.jpg) no-repeat scroll 0 0 transparent");
		},
		success : function(html) {
			var obj = eval("o=" + html);
			alert(obj.result);
		},
		beforeSend : function() {
			$("#reveiveActiveCode").attr('disabled', 'disabled');
			$("#reveiveActiveCode")
					.css("background",
							"url(/theme/default/images/smail2.jpg) no-repeat scroll 0 0 transparent");
			i = 30;
			timer = setInterval("enablePhoneSend()", 1000);
				}
			});
}

function sendPhoneCodeForSecSetting(id, phoneid) {
	var myTimer;
	var second;
	var url;
	var second=60;
	var $eml=$("#"+id);
	if(phoneid.replace(/\s/g,'')!=''&&phoneid!=null){
		var phone = $("#"+phoneid).attr("value");
		var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
		var length = phone.length;
		if (length != 11 || !mobile.test(phone)) {
			alert("请正确填写您的手机号码");
			return;
		}
		url = "../sendPhoneCode.action?phone=" + phone;
	}else{
		url = "../my/bindMobile_sendOrigionalPhoneCode.action";
	}
	
	document.getElementById(id).setAttribute('disabled','disabled');
	
	myTimer = setInterval(function(){
		if (second <= 0) {
			$eml.removeAttr('disabled');
			$eml.css("background", "url(/theme/default/images/smail.jpg) no-repeat scroll 0 0 transparent");
			$eml.attr("value", "点击获取");
			clearInterval(myTimer);
		} else {
			$eml.attr("value", "(" + second + "秒)重新获取");
			second--;
			
		};
	}, 1000);
	$.ajax({
		/*url : "../my/bindMobile_sendOrigionalPhoneCode.action",*/
		url:url,
		type : "POST",
		async:true,
		error : function() {
			/*$("#"+id).css("background", "url(/theme/default/images/smail.jpg) no-repeat scroll 0 0 transparent");*/
		},
		success : function(data) {
			if (data.result ){
				alert(data.result);
                return;
            }
		},
		beforeSend : function() {
			/*$("#"+id).css("background", "url(/theme/default/images/smail2.jpg) no-repeat scroll 0 0 transparent");*/
			
		}
	});
}

function enablePhoneSend() {
	if (i <= 0) {
		$("#timego").html("");
		$("#reveiveActiveCode").removeAttr('disabled');
		$("#reveiveActiveCode")
				.css("background",
						"url(/theme/default/images/smail.jpg) no-repeat scroll 0 0 transparent");
		$("#reveiveActiveCode").attr("value", "获取手机验证码");
		clearInterval(timer);
	} else {
		i--;
		$("#reveiveActiveCode").attr("value", "获取手机验证码 " + i);
		$("#timego").html("&nbsp;剩余:" + i + "秒");
	}
}

/**
 * 上传图片
 */
function uploadImage(width, height, path) {
	document.getElementById('page').style.position = "static";
	var overLay = document.createElement('div');
	overLay.setAttribute('id', 'mask');
	overLay.style.display = 'block';
	overLay.style.position = 'fixed';
	overLay.style.top = '0';
	overLay.style.left = '0';
	overLay.style.background = '#000';
	overLay.style.zIndex = '200';
	overLay.style.width = '100%';
	overLay.style.height = '100%';
	if ($.browser.msie) {
		overLay.style.filter = 'Alpha(Opacity=60)';
	} else {
		overLay.style.opacity = '0.6';
	}
	if ($.browser.version < 7) {
		overLay.style.position = 'absolute';
		var w = Math.max(document.body.scrollWidth,
				document.documentElement.clientWidth)
				+ 'px';
		var h = Math.max(document.body.scrollHeight,
				document.documentElement.clientHeight)
				+ 1500 + 'px';
		overLay.style.width = w;
		overLay.style.height = h;
		overLay.innerHTML = '<iframe style="width:100%;height:100%;background:#000;opacity:0.6;filter:Alpha(Opacity=0.6);" frameborder="0"></iframe>';
	}

	document.body.appendChild(overLay);

	$.ajax({
		url : "../getMediaPicker.action",
		type : "POST",
		dataType : "html",
		timeout : 10000,
		error : function() {
			$("#server_error").html("  服务器忙，请稍候重试。");
		},
		success : function(html) {

			if (hrefHack(html))
				return;
			$("#server_error").empty();

			$(".mediaPicker").html(html);
			$(".mediaPicker").show();

			$(".cancle").click(function() {
				$(".mediaPicker").hide();
				$("#mask").remove();
			});

			$(function() {
				$('#mediaPickerForm').submit(function() {
					var filePath = $("#mp_file_element").attr("value");
					$("#mediaName").attr("value", filePath);
					$("#savePath").attr("value", path);
					// 提交表单
					$(this).ajaxSubmit(uploadOptions);
					// 为了防止普通浏览器进行表单提交和产生页面导航（防止页面刷新？）返回false
					return false;
				});
			});

			var uploadOptions = {
				target : "#mediapickerStep2",
				beforeSubmit : beforeSubmit,
				success : afterSuccessSubmit
			};

			function beforeSubmit() {
				var filePath = $("#mp_file_element").attr("value");
				var point = filePath.lastIndexOf(".");
				var type = filePath.substr(point);
				if (type == ".jpg" || type == ".gif" || type == ".JPG"
						|| type == ".GIF" || type == ".PNG" || type == ".png") {
					$('.mp_upload_feedback').show();
				} else {
					$('.mp_upload_hint').html(
							"图片格式无效，您可以上传 JPG、GIF 或 PNG格式的图片。");
					return false;
				}
			}

			function afterSuccessSubmit() {
				$('#mp_upload_feedback').hide();
				$(".mp_dialog").hide();
				$(".mediapickerStep2").show();

				$(".cancle").click(function() {
					$(".mediaPicker").hide();
					$("#mask").remove();
				});

				$(function() {
					if (height != 0) {
						$('#preview').Jcrop({
							onChange : storeCoords,
							onSelect : storeCoords,
							setSelect : [ 0, 0, width, height ],
							aspectRatio : width / height
						});
					} else {
						$('#preview').Jcrop({
							onChange : storeCoords,
							onSelect : storeCoords
						});
					}
					function storeCoords(c) {
						$('#x').val(c.x);
						$('#y').val(c.y);
						$('#w').val(c.w);
						$('#h').val(c.h);
						$("#saveWidth").val(width);
						$("#saveHeight").val(height);
					}
				});
			}
		}
	});
}

// 检查年月日是否是合法日期
function isdate(intYear, intMonth, intDay) {
	if (isNaN(intYear) || isNaN(intMonth) || isNaN(intDay))
		return false;
	if (intMonth > 12 || intMonth < 1)
		return false;
	if (intDay < 1 || intDay > 31)
		return false;
	if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11)
			&& (intDay > 30))
		return false;
	if (intMonth == 2) {
		if (intDay > 29)
			return false;
		if ((((intYear % 100 == 0) && (intYear % 400 != 0)) || (intYear % 4 != 0))
				&& (intDay > 28))
			return false;
	}
	return true;
}

// 检查身份证是否是正确格式
function checkCard(cId) {
	var pattern;
	if (cId.length == 15) {
		pattern = /^\d{15}$/;// 正则表达式,15位且全是数字
		if (pattern.exec(cId) == null) {
			return false;
		}
		if (!isdate("19" + cId.substring(6, 8), cId.substring(8, 10), cId
				.substring(10, 12))) {
			return false;
		}
	} else if (cId.length == 18) {
		pattern = /^\d{17}(\d|x|X)$/;// 正则表达式,18位且前17位全是数字，最后一位只能数字,x,X
		if (pattern.exec(cId) == null) {
			return false;
		}
		if (!isdate(cId.substring(6, 10), cId.substring(10, 12), cId.substring(
				12, 14))) {
			return false;
		}
		var strJiaoYan = [ "1", "0", "X", "9", "8", "7", "6", "5", "4", "3",
				"2" ];
		var intQuan = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];
		var intTemp = 0;
		for (i = 0; i < cId.length - 1; i++)
			intTemp += cId.substring(i, i + 1) * intQuan[i];
		intTemp %= 11;
		if (cId.substring(cId.length - 1, cId.length).toUpperCase() != strJiaoYan[intTemp]) {
			return false;
		}
	} else {
		return false;
	}
	return true;
}

// 用户个人信息tab
function tabIt(data) {
	var id = data.id;
	clearTab(id);
	$("#" + id).removeClass();
	$("#" + id).addClass("right_tab_select");
	$("#" + id).show();
	$("#" + id + "_content").show();
}

function tabItDisHide(data, itme) {
	var id = data.id;
	clearTab(id);
	$("#" + id).removeClass();
	$("#refund_list").removeClass();
	$("#" + id).addClass("right_tab_select");
	$("#" + id).show();
	$("#" + id + "_content").show();
	$(itme).hide();
	$("#inrepayLoan").hide();

	$(function() {
		$.ajax({
			url : "../my/getRefundLoanList.action",
			type : "POST",
			dataType : 'html',
			timeout : 10000,
			error : function() {
				$("#refund_content").html($("#loadingFail").html());
			},
			success : function(response) {
				if (hrefHack(response))
					return;
				$("#refund_content").html(response);
			},
			beforeSend : function() {
				$("#refund_content").html($("#loading").html());
			}
		});
	});
}

function clearTab(id) {
	$("#" + id).parent().children().each(function(i) {
		$("#" + id).parent().children().removeClass();
		$("#" + id).parent().children().addClass("right_tab_unselect");
		$("#" + $("#" + id).parent().children().get(i).id + "_content").hide();
	});
}

function getCredit(type, materialType) {
	$.ajax({
		url : "../upload/uploadMaterial!uploadPage.action?type=" + type
				+ '&materialType=' + materialType + "&forAccount=forAccount",
		type : "POST",
		dataType : 'html',
		error : function(response) {
			$("#upload_credit_info_content").html(response)
			$("#upload_credit_info").show();
			tabIt($("#upload_credit_info")[0]);
		},
		success : function(response) {
			if (hrefHack(response))
				return;
			$("#upload_credit_info_content").html(response)
			$("#upload_credit_info").show();
			tabIt($("#upload_credit_info")[0]);
		}
	});
}

function getBasicCredit(type, materialType, obj) {
	var id = obj.id;
	$("#credit_one").children().removeClass("box_item_select");
	$("#" + id).addClass("box_item_select");
	$.ajax({
		url : "../upload/uploadMaterial!uploadPage.action?type=" + type
				+ '&materialType=' + materialType + "&forAccount=forAccount",
		type : "POST",
		dataType : 'html',
		error : function(response) {
			$("#right").html(response)
		},
		success : function(response) {
			if (hrefHack(response))
				return;
			$("#right").html(response)
		}
	});
}

function getInfo(data, expandFieldA, expandFieldB) {
	var expandFieldA = expandFieldA || "";
	var expandFieldB = expandFieldB || "";
	var id = data.id;
	var li = $("#li").val();

	for ( var i = 1; i < 8; i++) {
		$("#user_info_" + i).children().removeClass();
	}
	$("#" + id).addClass("box_item_select");
	var url = "../my/getUserInfoPage.action?type=" + id + "&li=" + li + "&v=" + Math.random();
	if (expandFieldA != "") {
		url = url + "&expandParamA=" + expandFieldA;
	}
	if (expandFieldB != "") {
		url = url + "&expandParamB=" + expandFieldB;
	}

	$.ajax({
		url : url,
		type : "POST",
		dataType : 'html',
		timeout : 100000,
		error : function() {
			$(".right").html($("#loadingFail").html());
		},
		success : function(response) {
			if (hrefHack(response)) {
				return;
			} else {
				$(".right").html(response)
			}
		},
		beforeSend : function() {
			$(".right").html($("#loading").html());
		}
	});
}

// 注意此处的做了JQuery兼容，原因是手机查询接口定义了$("")
function getTools(data) {
	var id = data.id;
	jQuery("#tools_1").children().removeClass("box_item_select");
	jQuery("#" + id).addClass("box_item_select");
	jQuery.ajax({
		url : "../getToolsPage.action?type=" + id,
		type : "POST",
		dataType : 'html',
		timeout : 10000,
		success : function(response) {
			if (hrefHack(response))
				return;
			jQuery(".right").html(response);
		},
		error : function(response) {
			if (hrefHack(response))
				return;
			jQuery(".right").html(response);
		}
	});
}

function getAboutInfo(data) {
	var id = data.id;

	for ( var i = 1; i < 8; i++) {
		$("#user_info_" + i).children().each(function() {
			$("#user_info_" + i).children().removeClass();
		});
	}
	$("#" + id).addClass("box_item_select");
	$("#right").children("div").each(function() {
		$(this).hide();
	});
	$("." + id + "_content").show();

}

// 鼠标滑过1级菜单时，二级菜单跟着变化
$(function() {
	var currentDisplay;
	var curIndex;
	var bigNavli = $("#big_nav li");
	bigNavli.each(function(i, element) {
		if ($(bigNavli[i]).hasClass("current")) {
			curIndex = i;
			currentDisplay = bigNavli[i];
		}
		bigNavli[i].onmouseover = function() {
			$(this).addClass("current");
			$("#big_nav table").hide();
			$(this).find("table").show();
		}

		bigNavli[i].onmouseout = function() {
			$(this).removeClass("current");
			if (typeof currentDisplay == "undefined")
				return;
			currentDisplay.className = "current";
			$("#big_nav table").hide();
			$(currentDisplay).find("table").show();
		}
	});
});

function addFav(id) {
	$.ajax({
		url : "/lend/saveLoan.action?id=" + id,
		type : "POST",
		dataType : 'json',
		timeout : 10000,
		error : function() {
			$("#addFavLink").html(
					"<a id=\"addFavLink\" onclick=\"addFav('" + id
							+ "')\"><span class=\"f_red\">" + response.result
							+ "</span></a>")
		},
		success : function(response) {
			if (hrefHack(response))
				return;
			$("#addFav").html(
					"<span class=\"f_red\">" + response.result + "</span>")
		},
		beforeSend : function() {
			$("#addFav").html("<span class=\"f_red\">正在添加关注...</span>")
		}
	});
}

function replyComment(biaoId, threadId, who) {
	if (who) {
		alert("对不起，您暂时没有权利回复留言，请与管理员联系。");
		return;
	}
	$("#" + threadId + "_commentBox").remove();
	var div = $('<div></div>').css({
		'width' : '600px'
	}).attr('id', threadId + "_commentBox").addClass("clearfix");
	var divTextArea = $('<div></div>').addClass("comment_edit");
	var textarea = $('<textarea id="' + threadId + '_comment' + '"></textarea>');
	var div5 = $('<div></div>').addClass("h5");
	var p = $('<p></p>');

	var span = $('<span id="commentError' + threadId
			+ '" class="f_red" style="padding-left:10px"></span>')

	var inputComment = $('<input type="button" value="留言" id="loanCommentBtn" onclick="replyCommentSubmit(\''
			+ biaoId
			+ '\',\''
			+ threadId
			+ '\',\''
			+ threadId
			+ '_comment'
			+ '\')"/>');
	var inputCancel = $('<input type="button" value="取消" onclick="cancelReply(\''
			+ threadId + '_commentBox\')"/>');

	p.append(inputComment);
	p.append(inputCancel);
	p.append(span);

	textarea.appendTo(divTextArea);

	divTextArea.appendTo(div);
	div5.appendTo(div);
	p.appendTo(div);
	div.insertAfter($("#" + threadId).parent());
}

function getMails(data) {
	var id = data.id;
	clearTab(id);
	$("#" + id).removeClass();
	$("#" + id).addClass("right_tab_select");
	if (id == 'system_info') {
		$("#swicthRCon0").show();
		$("#swicthRCon1").hide();
	} else if (id == 'user_inbox_info') {
		$("#swicthRCon0").hide();
		$("#swicthRCon1").show();
	}
	$("#user_inbox_type_info").remove();
	$.ajax({
		url : "../my/getInnerMails.action?type=" + id
				+ "&sysPageIndex=1&inboxPageIndex=1&sendboxPageIndex=1",
		type : "POST",
		dataType : 'html',
		timeout : 100000,
		error : function() {
			$("#inbox_content").html($("#loadingFail").html());
		},
		success : function(response) {
			if (hrefHack(response))
				return;
			$("#inbox_content").html(response);
		},
		beforeSend : function() {
			$("#inbox_content").html($("#loading").html());
		}
	});
}

function SeeAllZnx(id) {
	$("#content_" + id).hide();
	$("#all_content_" + id).show();
	$("#see_content_" + id).hide();
	$("#mails_content_" + id).show();
}

function SeeZnx(type, id) {
	$.ajax({
		url : "../my/readInnerMail.action?innerMailId=" + id,
		type : "POST",
		error : function() {
		},
		success : function() {
			$("#read_" + id).hide();
			$("#readed_" + id).show();
			$("#content_" + id).hide();
			$("#all_content_" + id).show();
			$("#see_" + id).hide();
			$("#mails_content_" + id).show();
			var totalcount = $("#mailsCount").html();
			if (totalcount && totalcount > 0) {
				totalcount = totalcount - 1;
			}
			$("#mailsCount").html(totalcount);
			var count = $("#" + type).html();
			if (count && count > 0) {
				count = count - 1;
			}
			$("#" + type).html(count);
		}
	})

}

function ResumeZnx(id) {
	$("#" + id).show();
	$("#all_" + id).hide();
	$("#mails_" + id).hide();
	$("#see_" + id).show();
}

function replyZnx(threadId) {
	readZnx("unreadInbox", threadId);
	$("#" + threadId + "_commentBox").remove();
	var form = $('<form></form>').attr('id', threadId + "_form");
	form.attr('method', 'post');
	var div = $('<div></div>').css({
		'margin' : '10px auto',
		'width' : '500px'
	}).attr('id', threadId + "_commentBox").addClass("clearfix");
	var divTextArea = $('<div></div>').addClass("comment_edit");
	var textarea = $('<textarea></textarea>').attr('id', 'sendMails');
	textarea.attr('name', 'comment');
	var div5 = $('<div></div>').addClass("h5");
	var p = $('<p></p>');

	var span = $('<span id="commentError' + threadId
			+ '" class="f_red" style="padding-left:10px"></span>')
	var inputId = $('<input type="hidden" name="innerMailId" value="'
			+ threadId + '"/>');
	var inputComment = $('<input type="button" value="回复" onclick="replyZnxSubmit(\''
			+ threadId + '\')"/>');
	var inputCancel = $('<input type="button" value="取消" onclick="cancelReply(\''
			+ threadId + '_commentBox\')"/>');

	p.append(inputId);
	p.append(inputComment);
	p.append(inputCancel);
	p.append(span);

	textarea.appendTo(divTextArea);

	divTextArea.appendTo(form);
	div5.appendTo(form);
	p.appendTo(form);
	form.appendTo(div);
	div.insertAfter($("#" + threadId));
}

function replyZnxSubmit(threadId) {
	$.ajax({
		data : $("#" + threadId + "_form").serialize(),
		type : "POST",
		url : "../my/sendInnerMail.action",
		dataType : 'json',
		error : function() {
			$("#commentError" + threadId).html("发送失败，请重试")
		},
		success : function(response) {
			if (hrefHack(response))
				return;
			$("#" + threadId + "_commentBox").remove();
			$("#del_tip").html(response.result);
			$("#blank").show();
			$("#del_tip").show();
		},
		beforeSend : function() {
			$("#commentError" + threadId).html("正在发送邮件...")
		}
	});
}

function readZnx(type, id) {
	$.ajax({
		url : "../my/readInnerMail.action?innerMailId=" + id,
		type : "POST",
		error : function() {
		},
		success : function() {
			$("#read_" + id).hide();
			$("#readed_" + id).show();
			$("#see_" + id).hide();
			$("#see_content_" + id).show();
			$("#delete_unread_" + id).hide();
			$("#delete_read_" + id).show();
			var totalcount = $("#mailsCount").html();
			if (totalcount && totalcount > 0) {
				totalcount = totalcount - 1;
			}
			$("#mailsCount").html(totalcount);
			var count = $("#" + type).html();
			if (count && count > 0) {
				count = count - 1;
			}
			$("#" + type).html(count);
		}
	})
}

function sendZnxSubmit(o, from) {
	var from = from || "";
	var self = o || $(".saveSettingBnt")[0];
	var receiver = $("#favourateReceiver").val();
	var comment = $("#sendMail").val().Trim().toString();
	var len = 0
	for ( var i = 0, l = comment.length; i < l; i++) {
		if ((comment.charCodeAt(i) < 0) || (comment.charCodeAt(i) > 255))
			len += 2;
		else
			len += 1;
	}
	len = parseInt(len / 2);
	if (len >= 301) {
		$("#send_tip").html("内容不能超过300个字！");
		return;
	}
	if (receiver == null || receiver == "") {
		$("#send_tip").html("收件人不能为空！");
	} else if (comment == null || comment == "") {
		$("#send_tip").html("邮件内容为空！");
	} else {
		$.ajax({
			data : $("#sendBoxForm").serialize(),
			type : "POST",
			url : "../my/sendInnerMail.action",
			dataType : 'json',
			beforeSend : function() {
				$("#send_tip").empty();
				self.value = "正在发送中...";
				self.disabled = "disabled";
			},
			error : function() {
				$("#send").hide();
				self.value = "发送站内信";
				self.removeAttribute("disabled");
				$("#send_tip").html("对不起，发送失败，请稍后再试！");
			},
			success : function(response) {
				$("#send").hide();
				self.value = "发送站内信";
				self.removeAttribute("disabled");
				$("#send_tip").html(response.result);
				var resultStatus = response.resultStatus;
				if (resultStatus || resultStatus == 'true') {
					$("#sendMail").val("");
					$("#sendDigSX").hide();
					if (from == "users") {
						setTimeout($("#user_inbox_info").click(), 1500);

					}
				}
			}
		});
	}
}

function delZnx(id, pageList, content) {
	var answer = confirm("确认删除?");
	if (answer) {
		$.ajax({
			url : "../my/deleteInnerMail.action?innerMailId=" + id + "&type="
					+ content,
			type : "POST",
			dataType : 'json',
			timeout : 10000,
			error : function(response) {
				$("#del_tip").html(response.result);
				$("#del_tip").show();
			},
			success : function(response) {
				if (hrefHack(response))
					return;
				$("#" + id).remove();

				var totalCount = $("#" + pageList).children().get(1).innerHTML;
				if (totalCount && totalCount > 0) {
					totalCount = totalCount - 1;
					$("#" + pageList).children().get(1).innerHTML = totalCount;
				}
				$("#del_tip").html(response.result);
				$("#blank").show();
				$("#del_tip").show();

				cancelReply(id + "_commentBox");
			}
		});
	}
}

function delUnreadZnx(id, pageList, content, type) {
	$.ajax({
		url : "../my/deleteInnerMail.action?innerMailId=" + id + "&type="
				+ content,
		type : "POST",
		dataType : 'json',
		timeout : 10000,
		error : function(response) {
			$("#del_tip").html(response.result);
			$("#del_tip").show();
		},
		success : function(response) {
			if (hrefHack(response))
				return;
			$("#" + id).remove();

			var totalCount = $("#" + pageList).children().get(1).innerHTML;
			if (totalCount && totalCount > 0) {
				totalCount = totalCount - 1;
				$("#" + pageList).children().get(1).innerHTML = totalCount;
			}
			var unreadTotalcount = $("#mailsCount").html();
			if (unreadTotalcount && unreadTotalcount > 0) {
				unreadTotalcount = unreadTotalcount - 1;
			}
			$("#mailsCount").html(unreadTotalcount);
			var count = $("#" + type).html();
			if (count && count > 0) {
				count = count - 1;
			}
			$("#" + type).html(count);

			$("#del_tip").html(response.result);
			$("#blank").show();
			$("#del_tip").show();

			cancelReply(id + "_commentBox");
		}
	});
}

function cancelReply(threadId) {
	$("#" + threadId).remove();
}

function replyCommentSubmit(biaoId, threadId, comment) {
	if (($("#" + comment).attr("value").replace(/[ ]/g, "")) == ""
			|| ($("#" + comment).attr("value").replace(/[ ]/g, "")) == null) {
		alert("请输入您的留言内容");
		return;
	}
	if ($("#" + comment).attr("value").length > 500) {
		alert("对不起，留言内容不能超过500字！");
		return;
	}
	var btn = document.getElementById("loanCommentBtn");
	btn.disabled = true;
	$
			.ajax({
				url : "../lend/commentLoan.action?id=" + biaoId + "&threadId="
						+ threadId + "&comment="
						+ encodeURIComponent($("#" + comment).attr("value")),
				type : "POST",
				dataType : 'html',
				timeout : 10000,
				error : function() {
					$("#commentError" + threadId).html("添加留言失败，请重试")
					btn.disabled = false;
				},
				success : function(response) {
					if (hrefHack(response))
						return;
					$("#commentError" + threadId).html("添加留言成功")
					btn.disabled = false;
					$("#commentError" + threadId).hide();
					$("#comment").attr("value", "");
					var newComment = $(response);
					if (threadId != 0) {
						newComment.appendTo($("#subComments", "#thread_"
								+ threadId));
					} else {
						if ($("#comments").children().length == 0) {
							newComment.appendTo($("#comments"));
						} else {
							newComment.insertBefore($("#comments").children()
									.get(0));
							var totalCount = $(".pagelist").children().get(1).innerHTML;
							if (totalCount && totalCount > 0) {
								totalCount = parseInt(totalCount) + 1;
								$(".pagelist").children().get(1).innerHTML = totalCount;
							}
						}
					}
					$("#" + threadId + "_commentBox").remove();
				},
				beforeSend : function() {
					$("#commentError" + threadId).html("正在添加留言...");
				}
			});
}

/**
 * 我要借款页面计算还款明细
 */
function getRepayDetail() {

	$("#borrowAmountError").hide();
	var borrowAmount, apr, repayTime, productId;
	borrowAmount = $("#borrowAmount").val();
	borrowAmount = isNaN(borrowAmount) || borrowAmount == '' ? 0
			: parseFloat(borrowAmount);
	if (borrowAmount == 0 || borrowAmount == "") {
		return false;
	}

	apr = parseFloat($("#apr").val());
	apr = isNaN(apr) || apr == '' ? 0 : parseFloat(apr);
	if (apr == 0 || apr == "") {
		return false;
	}
	repayTime = $("#repayTime").val();

	productId = $("#productId").val();

	$.ajax({
		url : "../borrow/getMonthlyInterest.action?amount=" + borrowAmount
				+ "&apr=" + apr + "&repayTime="
				+ (isNaN(repayTime) ? 0 : parseInt(repayTime)) + "&productId="
				+ (isNaN(productId) ? 0 : parseInt(productId)),
		type : "POST",
		dataType : 'json',
		timeout : 100000,
		error : function() {
		},
		success : function(data) {
			$("#monthRepayMoney").html("￥" + (data.monthlyRepay).toFixed(2));
			$("#managerFee").html("￥" + (data.manageFee).toFixed(2));
		}
	});
}

/**
 * 计算器
 */
function calculate() {
	var borrowAmount, apr, repayTime, isShow;
	borrowAmount = $("#borrowAmount").val();
	apr = parseFloat($("#apr").val());
	repayTime = $("#repayTime").val();
	var type=$("#type").val();
	var reg = /^[0-9]*[1-9][0-9]*$/;
	var regF = /^[+\-]?\d+(.\d+)?$/;
	if (!reg.test(borrowAmount) || borrowAmount == '') {
		$("#error").html("借款金额必须为正整数!");
	} else if (!regF.test(apr) || apr == '') {
		$("#error").html("年利率必须为数字类型!");
	} else if (!reg.test(repayTime) || repayTime == '') {
		$("#error").html("月份格必须为数字类型!");
	} else if (parseFloat(repayTime) - 120 > 0) {
		$("#error").html("月份必须在120以内!");
	} else {
		$("#error").html("");
		isShow = document.getElementById("isShow").checked;
		var url = window.location.href;
		var borrow = false;
		if(url.indexOf("borrow", 0)!=-1){
			borrow = true;
		}
		$.ajax({
			url : "../calculate.action?amount="
					+ (isNaN(borrowAmount) || borrowAmount == '' ? 0
							: parseFloat(borrowAmount)) + "&apr="
					+ (isNaN(apr) ? 0 : parseFloat(apr)) + "&repayTime="
					+ (isNaN(repayTime) ? 0 : parseInt(repayTime)) + "&show="
					+ isShow+"&type="+type+"&manageFeeShow="+borrow,
			type : "POST",
			dataType : 'html',
			timeout : 10000,
			error : function() {
				alert("服务器内部错误！");
			},
			success : function(data) {
				if (hrefHack(data))
					return;
				$("#calculate").html(data);
			}
		});
	}
}

/**
 * 获取还款详细
 */
function getRefundDetail(tab, id, type, p) {
	$.ajax({
		url : "../my/getRefundDetail.action?loanId=" + id + "&type=" + type
				+ "&tab=" + tab,
		type : "POST",
		dataType : 'html',
		timeout : 1000000,
		error : function(response) {
			$("#refund_content").html($("#loadingFail").html());
			$("#right").html(response);
		},
		success : function(response) {
			if (hrefHack(response))
				return;
			$("#refund_content").html(response);
			$("#quick_refund").addClass("right_tab_unselect");
			$("#closedLoan").addClass("right_tab_unselect");
			$("#refund_list").addClass("right_tab_select");
			$("#inrepayLoan").addClass("right_tab_unselect");
			$("#refund_list").show();
			initPayAmount();
		},
		beforeSend : function() {
			$("#refund_content").html($("#loading").html());
		}
	});
}

/**
 * 获取提前还款详细
 */
function getInrepayDetail(tab, id, type, p) {
	$.ajax({
		url : "../my/getInrepayDetail.action?loanId=" + id + "&type=" + type
				+ "&tab=" + tab,
		type : "POST",
		dataType : 'html',
		timeout : 1000000,
		error : function(response) {
			$("#refund_content").html($("#loadingFail").html());
			$("#right").html(response);
		},
		success : function(response) {
			if (hrefHack(response))
				return;
			$("#refund_content").html(response);
			$("#quick_refund").addClass("right_tab_unselect");
			$("#closedLoan").addClass("right_tab_unselect");
			$("#refund_list").addClass("right_tab_unselect");
			$("#inrepayLoan").addClass("right_tab_select");
			$("#inrepayLoan").show();
		},
		beforeSend : function() {
			$("#refund_content").html($("#loading").html());
		}
	});
}

function inrepay(type, bnt, id) {
	$(bnt).attr("disabled", "disabled");
	$.ajax({
		url : "../my/inrepayAction.action?type=" + type + "&loanId=" + id,
		type : "POST",
		dataType : 'html',
		timeout : 100000,
		error : function(response) {
			$(bnt).removeAttr("disabled");
			$("#payTip").hide();
		},
		success : function(response) {
			var o = eval("o=" + response);
			if (o.tag) {
				if (o.result == "finish") {
					alert("您已还清整笔借款！");
					tabItDisHide(document.getElementById("quick_refund"),
							document.getElementById('refund_list'));
				}
			} else {
				alert(o.result);
			}
			$(bnt).removeAttr("disabled");
			$("#payTip").hide();
		}
	});
}
/**
 * 还款
 */
function pay(type, bnt) {

	$(bnt).attr("disabled", "disabled");
	$("#payTip").show();
	var count = 0;
	var startNumber = 0;
	var endNumber = 0;
	$("#refundTab").children("tbody").children("tr").each(
			function() {
				if (true == $(this).children("td:first").children("input")
						.attr("checked")) {
					if (count == 0) {
						startNumber = $(this).children("td:first").children(
								"input").val();
					}
					endNumber = $(this).children("td:first").children("input")
							.val();
					count++;
				}
			});

	if (count != (parseInt(endNumber) - parseInt(startNumber) + 1)) {
		alert("还款失败,请顺序还款!");
		$("#payTip").hide();
		$(bnt).removeAttr("disabled");
	} else if (count != 0) {
		var b = window.confirm("您确定要还款吗？");
		if (b) {
			var parameter = $("#refundForm").serialize();
			$.ajax({
				url : "../my/refundAction.action?startNumber=" + startNumber
						+ "&endNumber=" + endNumber + "&type=" + type
						+ "&payNumberCount=" + count,
				data : parameter,
				type : "POST",
				dataType : 'html',
				timeout : 100000,
				error : function(response) {
					$("#refundTab").children("tbody").children("tr").each(
							function() {
								$(this).children("td:first").children("input")
										.attr("disabled", "disabled");
							});
					$(bnt).removeAttr("disabled");
					$("#payTip").hide();
				},
				success : function(response) {
					var o = eval("o=" + response);
					if (o.tag) {
						if (o.result == "finish") {
							alert("您已还清整笔借款！");
							tabItDisHide(document
									.getElementById("quick_refund"), document
									.getElementById('refund_list'));
						} else {
							alert(o.result);
							getRefundDetail("quick_refund", o.loanId, o.type,
									o.currentPage);
						}
					} else {
						alert(o.result);
					}
					$(bnt).removeAttr("disabled");
					$("#payTip").hide();
				}
			});
		} else {
			$("#payTip").hide();
			$(bnt).removeAttr("disabled");
			return;
		}
	} else if (count == 0) {
		alert("请选择还款列表!");
		$(bnt).removeAttr("disabled");
		$("#payTip").hide();
	}
}

function changeRefundAmount(item, index, id) {
	if (item.checked) {
		if ($(item).parent("td").parent("tr").next("tr").children("td:first")
				.children("input:first").attr("id") != undefined) {
			$(item).parent("td").parent("tr").next("tr").children("td:first")
					.children("input:first").removeAttr("disabled");
		}
	} else {
		$("#refundTab").children("tbody").children("tr").each(
				function() {
					var v = $(this).children("td:first").children("input")
							.val();
					if (parseInt(v) > parseInt($(item).val())) {
						$(this).children("td:first").children("input").attr(
								"disabled", "disabled");
						$(this).children("td:first").children("input")
								.removeAttr("checked");
					}
				});
	}
	calculateRefundCount();
}

function calculateRefundCount() {
	var count = 0;
	$("#refundTab").children("tbody").children("tr").each(
			function() {
				if ($(this).children("td:first").children("input").attr(
						"checked")) {
					var num = parseFloat($(this).children("td:eq(3)").children(
							"input").val());
					count = floatAdd(count.toFixed(2), num.toFixed(2));
				}
			});
	$("#currCount").text(count.toFixed(2));
}

function floatAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length
	} catch (e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length
	} catch (e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2))
	return (arg1 * m + arg2 * m) / m
}

function initPayAmount() {
	var allCount = 0;
	var count = 0;
	var shouldCount = 0;
	var alreadyCount = 0;
	$("#refundTab")
			.children("tbody")
			.children("tr")
			.each(
					function() {
						var num = parseFloat($(this).children("td:eq(2)")
								.children("input").val());
						var num1 = parseFloat($(this).children("td:eq(3)")
								.children("input").val());
						shouldCount = floatAdd(shouldCount.toFixed(2), num1
								.toFixed(2));
						alreadyCount = floatAdd(alreadyCount.toFixed(2), num
								.toFixed(2));
						if ($(this).children("td:first").children("input")
								.attr("id") != undefined) {
							allCount = floatAdd(allCount.toFixed(2), num1
									.toFixed(2));
						}
						if ($(this).children("td:first").children("input")
								.attr("checked")) {
							var num2 = parseFloat($(this).children("td:eq(3)")
									.children("input").val());
							count = floatAdd(count.toFixed(2), num2.toFixed(2));
						}
					});
	var fee = parseFloat($("#fee").val());
	alreadyCount = floatAdd(alreadyCount.toFixed(2), fee.toFixed(2));
	$("#allCount").text(formatNum(allCount, 2));
	$("#currCount").text(formatNum(count, 2));
	$("#repayCount").text("￥" + formatNum(allCount, 2));
	$("#alreadyCount").text("￥" + formatNum(alreadyCount, 2));
	$("#shouldCount").text("￥" + formatNum(shouldCount, 2));
}

function inRepayFee(availablePoint, amount, type, bnt, id) {
	if (availablePoint < amount) {
		alert("对不起,您的余额不足!");
	} else {
		inrepay(type, bnt, id);
	}
}

/**
 * 还款类型控制
 */
function payTip(type) {
	if ("all" == type) {
		var count = 0;
		$("#refundTab").children("tbody").children("tr").each(
				function() {
					$(this).children("td:first").children("input").attr(
							"readonly");
					$(this).children("td:first").children("input").attr(
							"checked", "checked");
					$(this).children("td:first").children("input").attr(
							"disabled", "disabled");
					if ($(this).children("td:first").children("input").attr(
							"id") != undefined) {
						count += parseFloat($(this).children("td:eq(3)")
								.children("input").val());
					}
				});
		$("#allCount").text(Math.floor((count) * 100) / 100);
	}
	if ("current" == type) {
		$("#refundTab").children("tbody").children("tr").each(
				function() {
					$(this).children("td:first").children("input").attr(
							"disabled", "disabled");
					$(this).children("td:first").children("input").removeAttr(
							"checked");
				});
		$(".due").each(function() {
			$(this).attr("checked", "checked");
		});
		$("#currentRefund").attr("checked", "checked");
		$("#currentRefund").parent("td").parent("tr").next("tr").children(
				"td:first").children("input").removeAttr("disabled");
		$("#currentRefund").removeAttr("disabled");
	}
	initPayAmount();
}

/**
 * 改变图片来源
 */
function selImgSource(item) {
	var val = $(item).val();
	if ("upload" == val) {
		$("#systemImg").hide();
		$("#upload").show();
		$("#upload_tip").show();
		$("#upload_preview").show();
		$("#systemImg_tip").hide();
	}
	if ("userImg" == val) {
		$("#systemImg").hide();
		$("#upload").hide();
		$("#upload_tip").hide();
		$("#upload_preview").hide();
		$("#systemImg_tip").hide();
	}
	if ("systemImg" == val) {
		$("#systemImg").show();
		$("#upload").hide();
		$("#upload_tip").hide();
		$("#upload_preview").hide();
		$("#systemImg_tip").show();
	}
}

function getFloatNum(id) {
	if (id == null || id == "") {
		return 0;
	} else {
		var v = $("#" + id).val();
		if (v == "")
			v = 0;
		return parseFloat(v);
	}
}

function bgOnmouseout(id, type) {
	if (type) {
		$("#" + id).removeClass("onp");
	} else {
		$("#" + id).removeClass("on");
	}
}

function bgOnmouseover(id, type) {
	if (type) {
		$("#" + id).addClass("onp");
	} else {
		$("#" + id).addClass("on");
	}
}

function delBiao(id) {
	$.ajax({
		url : "../my/deleteSavedLoan.action?id=" + id,
		type : "POST",
		dataType : 'json',
		timeout : 10000,
		error : function(response) {
			if (response.result == 1) {
				alert("请选择您要删除的标！")
			} else if (response.result == 2) {
				alert("请先登录！");
			} else {
				alert("删除失败，请重试")
			}
		},
		success : function(response) {
			if (response.res) {
				$("#" + id).remove();
				var totalCount = $("#pageList").children().get(1).innerHTML;
				if (totalCount && totalCount > 0) {
					totalCount = totalCount - 1;
					$("#pageList").children().get(1).innerHTML = totalCount;
				}
			} else {
				if (response.result == 1) {
					alert("请选择您要删除的标！")
				} else if (response.result == 2) {
					alert("请先登录！");
				} else {
					alert("删除失败，请重试")
				}
			}
		}
	});
}

function isAutoBid(item) {
	var isUse = $(item).val();
	if ("true" == isUse) {
		$("#conditionTab").children("tbody").children("tr").each(
				function(i) {
					$(this).children("td:last").children("div").children(
							"input").removeAttr("disabled");
					$(this).children("td:last").children("div").children(
							"div:eq(1)").children("input").removeAttr(
							"disabled");
				});
	}
	if ("false" == isUse) {
		$("#conditionTab").children("tbody").children("tr").each(
				function(i) {
					$(this).children("td:last").children("div").children(
							"input").attr("disabled", "disabled");
					$(this).children("td:last").children("div").children(
							"div:eq(1)").children("input").attr("disabled",
							"disabled");
				});
	}
}

function delBid(item) {
	$(item).parent("td").parent("tr").remove();
}

function addBidTr(percent, amount) {
	return s = "<tr style=\"height: 30px; background: none repeat scroll 0% 0% rgb(255, 255, 255);\"><td><div align=\"center\" id=\"monthRepay\" style=\"padding-right: 5px;\">标的利率不小于 "
			+ percent
			+ "% 时,就投 "
			+ amount
			+ " 元.</div></td><td><a onclick=\"javascript:delBid(this)\">删除</a></td></tr>"
}

function openOrClose() {
	var minPoints = $("#minpoints").val();

	var enabled = document.getElementsByName("enabled").checked;
	enabled = document.getElementById("open").checked == true ? true : false;

	$.ajax({
		url : "../lend/openorClose.action?enabled=" + enabled + "&minPoints="
				+ minPoints,
		type : "POST",
		dataType : "json",
		timeout : 10000,
		error : function() {
		},
		success : function(response) {
			if (response.result == '自动投标状态已设置成开启') {
				$("#status").attr("value", enabled);
				alert(response.result);
			} else {
				$("#status").attr("value", enabled);
				alert(response.result);
			}
		},
		beforeSend : function() {

		}
	});
}

function tasteAutoBid() {
	var enabled = $("#status").val();
	$.ajax({
		url : "../lend/tasteAutoBid.action?enabled=" + enabled,
		type : "POST",
		dataType : 'json',
		timeout : 10000,
		error : function() {

		},
		success : function(response) {
			if (response.result == '自动投标成功，30分钟内不能再次进行操作') {
				alert(response.result);
			} else if (response.result == '自动投标失败，距离上次操作还未到30分钟，请稍后再试') {
				alert(response.result);
			} else if (response.result == '对不起，您的自动投标为关闭状态，不能体验') {
				alert(response.result);
			} else {
				alert(response.result);
			}
		},
		beforeSend : function() {

		}
	});
}

function reportGuy(user, biao) {
	var reason = $("input[name='reason']:checked").val();
	var content = $("#res_content").attr("value");
	var n = parseInt(content.toString().length);
	if (n > 200) {
		alert("对不起，您输入的内容过长！");
		return;
	}
	$.ajax({
		url : "../lend/accusation.action?userId=" + user + "&biaoId=" + biao
				+ "&content="
				+ encodeURIComponent($("#res_content").attr("value"))
				+ "&reason=" + encodeURIComponent(reason),
		type : "POST",
		dataType : 'json',
		timeout : 10000,
		error : function() {
			alert("提交失败，请重试");
		},
		success : function(response) {
			if (response.result) {
				alert("举报已成功，我们将尽快处理！");
				$("#closeBut").click();
				$("#res_content").attr("value", "");
				$("#reportGuy").parent().text("已举报该用户");
			} else {
				alert("举报失败，请重试");
			}
		}
	});
}

$(function() {
	$('#reportGuy').nyroModal({
		width : 600,
		height : 500,
		minWidth : 600, // Minimum width
		minHeight : 500
	// Minimum height
	});

	$('#znx').nyroModal({
		width : 350,
		height : 240,
		minWidth : 350, // Minimum width
		minHeight : 240
	// Minimum height
	});

	$('#bid1').nyroModal({
		width : 350,
		height : 240,
		minWidth : 350, // Minimum width
		minHeight : 240
	// Minimum height
	});

	$('#bid2').nyroModal({
		width : 350,
		height : 240,
		minWidth : 350, // Minimum width
		minHeight : 240
	// Minimum height
	});
});

function checkImgType() {
	var radio = document.getElementsByName("imgType");
	var type = "";
	for ( var i = 0; i < radio.length; i++) {
		if (radio[i].checked) {
			type = radio[i].value;
		}
	}
	if ("upload" == type) {
		var v = $("#pictureUrlLoan").val();
		if (v == '' || v == null) {
			$("#uploadError").show();
			window.location.href = "#upload"
			return false;
		} else {
			$("#uploadError").hide();
		}
	} else if ("systemImg" == type) {
		var v = $("#systemImgPath").val();
		if (v == '' || v == null) {
			$("#systemError").show();
			window.location.href = "#systemImg"
			return false;
		} else {
			$("#systemError").hide();
		}
	}
	return true;
}

function saveAndPreview(type, avaliableLevel, selectedAmount, userOverDue) {
	var borrowAmount = document.getElementById("borrowAmount").value;
	if (selectedAmount) {
		if (parseFloat(borrowAmount) - avaliableLevel > 0) {
			$("#checkBorrowAmount").hide();
			$("#borrowAmountError").show();
			alert("您当前申请额度大于可用额度，系统不允许提交!");
			window.location.href = "#borrowAmountError";
			return false;
		} else {
			$("#borrowAmountError").hide();
		}
	}

	if (!checkImgType()) {
		return false;
	} else {
		if ("save" == type) {
			document.getElementById("borrowForm").action = "../borrow/tempLoan.action";
			document.getElementById("borrowForm").target = '';
		}
		if ("preview" == type) {
			var parameter = $("#borrowForm").serialize();
			$
					.ajax({
						url : "../borrow/previewLoanPage.action",
						type : "POST",
						data : parameter,
						dataType : "html",
						timeout : 10000,
						error : function() {
						},
						success : function(html) {
							window
									.showModalDialog(
											"../previewDetail.jsp",
											html,
											"height=768, width=1024, toolbar =yes, menubar=yes, scrollbars=yes, resizable=yes, location=yes, status=yes");
						}
					});
		}
		if ("publish" == type) {
			document.getElementById("borrowForm").action = "../borrow/loan.action";
			document.getElementById("borrowForm").target = '';
		}
	}
}

function delMyUser(id) {
	$.ajax({
		url : "deleteMyUser.action?id=" + id,
		dataType : 'json',
		timeout : 10000,
		error : function(response) {
			if (response.result == 1) {
				alert("请选择您要删除的标！")
			} else if (response.result == 2) {
				alert("请先登录！");
			} else {
				alert("删除失败，请重试")
			}
		},
		success : function(response) {
			if (response.res) {
				$("#user" + id).remove();
			} else {
				if (response.result == 1) {
					alert("请选择您要删除的用户！")
				} else if (response.result == 2) {
					alert("请先登录！");
				} else {
					alert("删除失败，请重试")
				}
			}
		}
	});
}

function getFullPath(obj) {
	if (obj) {
		// ie
		if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
			obj.select();
			$("#borrowPicture").val(document.selection.createRange().text);
		}
		// firefox
		else if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
			if (obj.files) {
				$("#borrowPicture").val(obj.files.item(0).getAsDataURL());
			}
			$("#borrowPicture").val(obj.value);
		}
		$("#borrowPicture").val(obj.value);
	}
}

function addFile(form, obj, size) {
	var i = $(obj).parents().find("form").attr("count");
	if (i < size - 2) {
		var current = $(obj).parent().parent();
		var item = $('<div class="user_info_item"><span class="user_info_item_left">&nbsp;</span><span><input type="file" name="upload" size="30" /> </span></div>');
		i++;
		$(obj).parents().find("form").attr("count", i);
		current
				.before('<div class="user_info_item"><span class="user_info_item_left">&nbsp;</span><span><input type="file" name="upload" size="30" /> </span><span style="font:12px Arial;color:#00f;text-decoration:underline;cursor:pointer" onclick="removeFile(\''
						+ form + '\',this)">移除</span></div>');
	} else {
		alert("一次最多上传" + size + "个附件");
	}
}

/* 删除已经添加的附件项 */
function removeFile(form, obj) {
	var i = $(obj).parents().find("form").attr("count");
	i--;
	$(obj).parents().find("form").attr("count", i);
	$(obj).parent().remove();
}

function expandRecoverDetail(id, obj) {
	$("#bid_recover_detail_" + id).slideToggle("slow", function() {
		if ($(obj).html() == "收起收款详情") {
			$(obj).html("展开收款详情");
		} else {
			$(obj).html("收起收款详情");
		}
	});
}

function displayCommentBox(obj) {
	$("#comment_box").slideToggle("slow", function() {
		if ($(obj).html() == "我要留言") {
			$(obj).html("收起留言");
		} else {
			$(obj).html("我要留言");
		}
	});
}

/**
 * 关注用户
 */
function addFavUser(id) {
	$.ajax({
		url : "../lend/saveUser.action?id=" + id,
		dataType : 'json',
		timeout : 10000,
		error : function() {
			$("#addFavUserLink").html(
					"<a id=\"addFavUserLink\" onclick=\"addFavUser('" + id
							+ "')\"><span class=\"f_red\">" + response.result
							+ "</span></a>")
		},
		success : function(response) {
			if (response.result == '对不起，您不能关注自己！') {
				alert(response.result);
				$("#addFavUserLink").html("关注此用户 ");
			} else {
				$("#addFavUserLink").parent().text("已关注此用户")
			}
		},
		beforeSend : function() {
			$("#addFavUserLink").html("<span class=\"f_red\">添加关注中</span>")
		}
	});
}

// 我关注的用户tab
function tabFavUser(data, userId) {
	var id = data.id;
	clearTab(id);
	$("#" + id).removeClass();
	$("#" + id).addClass("right_tab_select");
	$("#" + id).show();
	$("#" + id + "_content").show();

	$(function() {
		$
				.ajax({
					url : "../my/getFavoriteUser.action?userId="
							+ userId
							+ "&id="
							+ id
							+ "&myActPageIndex=1&myAttPageIndex=1&userActPageIndex=1&borrowPageIndex=1&lendPageIndex=1",
					dataType : 'html',
					error : function() {
						$("#cont").html($("#loadingFail").html());
					},
					success : function(response) {
						if (hrefHack(response))
							return;
						$("#cont").html(response);
					},
					beforeSend : function() {
						$("#cont").html($("#loading").html());
					}
				})
	});
}

// 借款详情里发送站内信
function sendInnerMail() {
	var content = $("#mail_content").attr("value");
	var n = parseInt(content.toString().length);
	if (n > 1500) {
		alert("站内信内容应少于1500字！");
		return;
	}
	$.ajax({
		data : $("#sendBoxForm").serialize(),
		url : "../my/sendInnerMail.action",
		type : "post",
		dataType : 'json',
		timeout : 10000,
		error : function() {
			alert("发送失败，请重试");
		},
		success : function(response) {
			if (response.result) {
				alert(response.result);
				$("#closeBut").click();
				$("#mail_content").attr("value", "");
			}
		}
	});
}

function calRecharge(type) {
	var free = $("#free").attr("checked");
	var s = $.trim($("#amount").val());
	var payType = $("#payFeeType").val();
	var amount = 0;
	if (s != "" && s != null) {
		amount = parseFloat(s);
	}
	var v = amount > 1 && amount < 300000
			&& /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/.test(amount);
	if (!v) {
		$("#msgAmount").removeClass("f_gray").addClass("error");
	} else {
		$("#msgAmount").removeClass("error").addClass("f_gray");

		$.ajax({
			url : "../my/getCashFee.action?amount=" + amount + "&type=" + type
					+ "&payCategory=" + payType + "&couponlog=" + free,
			dataType : 'json',
			data : $("#kuaiQianForm").serialize(),
			timeout : 10000,
			error : function() {
			},
			success : function(response) {
				var fe = parseFloat(response.fe);
				var cal = parseFloat(response.cal);
				var afterBanlance = parseFloat(response.balance);
				$("#fee").html(formatNum(fe, 2) + " 元");
				$("#realAmount").html(formatNum(cal, 2) + " 元");
				$("#afterBanlance").html(formatNum(afterBanlance, 2) + " 元");
			}
		});
	}
}

// 格式化数字，转化成带千分符，并且四舍五入保留两位小数
function formatNum(num, n) {
	num = String(num.toFixed(n));
	var re = /(\d+)(\d{3})/;
	while (re.test(num)) {
		num = num.replace(re, "$1,$2");
	}
	return num;
}

function hideErrorTip() {
	$("#forAmount").hide();
}

// 查询手机信息
function searchIpInfo(ip) {
	$("#errorMsg").html("");
	var m = jQuery("#" + ip).val();
	if (m == null || m == "") {
		$("#errorMsg").html("请输入IP地址");
		$("#errorMsg").show();
		return;
	}
	var pattern = /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	var flag_ip = pattern.test(m);
	if (flag_ip) {
		$("#search").show();
		labelIp('data', m);
	} else {
		$("#search").hide();
		jQuery("#errorMsg").text(" IP地址输入错误，请重新输入!");
	}
}

function querycallback(obj) {
	alert(obj);
}

function allLoans(data) {
	var id = data.id;
	$("#" + id).parent().children().each(function(i) {
		$("#" + id).parent().children().removeClass();
		$("#" + id).parent().children().addClass("right_tab_unselect2");
		$("#" + $("#" + id).parent().children().get(i).id + "_content").hide();
	});
	$("#" + id).removeClass();
	$("#" + id).addClass("right_tab_select2");
	$("#" + id).show();
	$("#biao_list").show();

	$(function() {
		$.ajax({
			url : "../lend/loanList.action?id=" + id + "&pageIndex=1",
			dataType : 'html',
			error : function(response) {
				if (hrefHack(response))
					return;
			},
			success : function(response) {
				if (hrefHack(response))
					return;
				$("#b_content").html(response);
			},
			beforeSend : function() {
				$("#b_content").html($("#loading").html());
			}
		})
	});
}

function checkAnswer(from, to, type, index) {
	var lendKey = [ "A", "D", "D", "A", "D", "C", "B" ];
	var borrowKey = [ "A", "C", "D", "A", "D", "A", "A", "D" ];
	var flag = true;
	var key;
	var val;
	var count = 0;
	var h = '';
	for ( var i = from; i <= to; i++) {
		var item = document.getElementsByName("option" + i);
		for (j = 0; j < item.length; j++) {
			if (item[j].checked) {
				count++;
				var v = item[j].value;
				if ("lend" == type) {
					key = lendKey;
					val = "借出学习";
				}
				if ("borrow" == type) {
					key = borrowKey;
					val = "借入学习";
				}

				if (key[i - 1] == v) {
					$("#question" + i).removeClass();
					$("#question" + i).addClass("answer_success");
					$("#question" + i).html("恭喜您,回答正确！");
				} else {
					flag = false;
					$("#question" + i).removeClass();
					$("#question" + i).addClass("answer_error");
					$("#question" + i).html("对不起，回答错误，请重新选择！");
					if (h == '') {
						h = "question" + i
					}
				}
			}
		}
		if (count == 0) {// 没有选中任何选项
			flag = false;
			$("#question" + i).removeClass();
			$("#question" + i).addClass("answer_error");
			$("#question" + i).html("必填！");
			if (h == '') {
				h = "question" + i
			}
		}
	}
	if (flag) {
		if (to == key.length) {
			alert("恭喜您通过" + val);
		} else {
			alert("恭喜您，回答正确，请点击确定继续答题!");
		}
		// $("#lend_study_step_" + index + "_content").hide();
		//
		// $("#lend_study_step_" + index).removeClass();
		// $("#lend_study_step_" + index).addClass("right_tab_unselect");
		//
		// $("#lend_study_step_" + (index + 1) + "_content").show();
		// $("#lend_study_step_" + (index + 1)).show();
		//
		// $("#lend_study_step_" + (index + 1)).removeClass();
		// $("#lend_study_step_" + (index + 1)).addClass("right_tab_select");
	}
	if (!flag) {
		window.location.href = "#" + h;
	}
	return flag;
}

// 给用户留言
function replyCommentUserSubmit(userId, threadId, comment) {
	if (($("#" + comment).attr("value").replace(/[ ]/g, "")) == ""
			|| ($("#" + comment).attr("value").replace(/[ ]/g, "")) == null) {
		alert("请输入您的留言内容");
		return;
	}
	if ($("#" + comment).attr("value").length > 500) {
		alert("对不起，留言内容不能超过500字！");
		return;
	}
	var btn = document.getElementById("userCommentBtn");
	btn.disabled = true;
	$
			.ajax({
				url : "../my/commentUser.action?id=" + userId + "&threadId="
						+ threadId + "&comment="
						+ encodeURIComponent($("#" + comment).attr("value")),
				dataType : 'html',
				timeout : 10000,
				error : function() {
					$("#commentError" + threadId).html("添加留言失败，请重试")
					btn.disabled = false;
				},
				success : function(response) {
					$("#commentError" + threadId).html("添加留言成功")
					btn.disabled = false;
					$("#commentError" + threadId).hide();
					$("#tempNull").hide();
					$("#comment").attr("value", "");
					var newComment = $(response);

					if (threadId != 0) {
						newComment.appendTo($("#subComments", "#thread_"
								+ threadId));
					} else {
						if ($("#comments").children().length == 0) {
							newComment.appendTo($("#comments"));
						} else {
							newComment.insertBefore($("#comments").children()
									.get(0));
							var totalCount = $("#commentPagelist").children()
									.get(1).innerHTML;
							if (totalCount && totalCount > 0) {
								totalCount = parseInt(totalCount) + 1;
								$("#commentPagelist").children().get(1).innerHTML = totalCount;
							}
						}
					}
					$("#" + threadId + "_commentBox").remove();
				},
				beforeSend : function() {
					$("#commentError" + threadId).html("正在添加留言...")
				}
			});
}

function replyCommentUser(userId, threadId, who) {
	if (who) {
		alert("对不起，您暂时不能回复留言，请联系管理员！");
		return;
	}

	$("#" + threadId + "_commentBox").remove();
	var div = $('<div></div>').css({
		'width' : '600px'
	}).attr('id', threadId + "_commentBox").addClass("clearfix");
	var divTextArea = $('<div></div>').addClass("comment_edit");
	var textarea = $('<textarea id="' + threadId + '_comment' + '"></textarea>');
	var div5 = $('<div></div>').addClass("h5");
	var p = $('<p></p>');

	var span = $('<span id="commentError' + threadId
			+ '" class="f_red" style="padding-left:10px"></span>')

	var inputComment = $('<input type="button" value="留言" id="userCommentBtn" onclick="replyCommentUserSubmit(\''
			+ userId
			+ '\',\''
			+ threadId
			+ '\',\''
			+ threadId
			+ '_comment'
			+ '\')"/>');
	var inputCancel = $('<input type="button" value="取消" onclick="cancelReply(\''
			+ threadId + '_commentBox\')"/>');

	p.append(inputComment);
	p.append(inputCancel);
	p.append(span);

	textarea.appendTo(divTextArea);

	divTextArea.appendTo(div);
	div5.appendTo(div);
	p.appendTo(div);
	div.insertAfter($("#" + threadId).parent());
}

function deleteUserComment(commentid) {
	$.ajax({
		url : "../my/deleteUserComment.action?commentId=" + commentid,
		dataType : 'html',
		timeout : 10000,
		error : function() {
			alert("删除失败");
		},
		success : function(response) {
			$("#" + commentid).parent().parent().parent().remove();
			alert("留言删除成功！");
			var totalCount = $("#commentPagelist").children().get(1).innerHTML;
			if (totalCount && totalCount > 0) {
				totalCount = parseInt(totalCount) - 1;
				$("#commentPagelist").children().get(1).innerHTML = totalCount;
			}
		},
		beforeSend : function() {
			$("#deleteUserCommentTip" + commentid).html("正在删除留言...")
		}
	});
}

function searchLoans() {
	var id = $(".right_tab_select2")[0].id;
	if (typeof (id) == "undefined") {
		id = "all_biao_list";
	}
	var key = $("#search").attr("value");
	if (key.replace(/[ ]/g, "") == "" || key.replace(/[ ]/g, "") == null
			|| key == "请输入您的搜索条件") {
		alert("请输入您要搜索的条件！");
		return;
	}
	$(function() {
		$.ajax({
			url : "../lend/searchLoan.action?id=" + id + "&key="
					+ encodeURIComponent(key),
			dataType : 'html',
			timeout : 10000,
			error : function() {
				$("#b_content").html($("#loadingFail").html());
			},
			success : function(response) {
				if (hrefHack(response))
					return;
				$("#b_content").html(response);
			},
			beforeSend : function() {
				$("#b_content").html($("#loading").html());
			}
		});
	});
}

function delUser(id) {
	$
			.ajax({
				url : "../my/deleteSavedUser.action?id=" + id,
				dataType : 'json',
				timeout : 10000,
				error : function(response) {
					if (response.result == 1) {
						alert("请选择您要删除的用户！")
					} else if (response.result == 2) {
						alert("请先登录！");
					} else {
						alert("删除失败，请重试")
					}
				},
				success : function(response) {
					if (response.res) {
						$("#" + id).remove();
						var totalCount = $("#myAttPageList").children().get(1).innerHTML;
						if (totalCount && totalCount > 0) {
							totalCount = totalCount - 1;
							$("#myAttPageList").children().get(1).innerHTML = totalCount;
						}
					} else {
						if (response.result == 1) {
							alert("请选择您要删除的用户！")
						} else if (response.result == 2) {
							alert("请先登录！");
						} else if (response.result == 3) {
							alert("删除失败，请重试")
						} else {
							alert("对不起，您没有权限删除该用户！");
						}
					}
				}
			});
}

function hrefHack(html) {
	if (jQuery(html).filter(".session_invalid_tag").length > 0) {
		window.top.location.href = "../";
		return true;
	} else {
		return false;
	}
}

function userFormSuccess(response) {
	if (hrefHack(response))
		return;
	$("#right").html(response);
}

// 进入银行卡管理
function bankCard1(id) {

	$.ajax({
		url : "../financeplan/userBank!listUserBank.action",
		type : "POST",
		dataType : "html",
		error : function() {
			$("#loan_account").html($("#loadingFail").html());
		},
		success : function(data) {

			$("#loan_account").html(data);
		},
		beforeSend : function() {
			$("#loan_account").html($("#loading").html());
		}
	});
}

// 充值提现
function getAccount(id) {

	clearTab(id);
	$("#" + id).removeClass();
	$("#" + id).addClass("right_tab_select");
	$("#" + id).show();

	if (id == 'bank_card') {
		bankCard1('bank_card');
		return;
	}

	$.ajax({
		url : "../my/getAccountPage.action?type=" + id,
		type : "POST",
		dataType : 'html',
		timeout : 100000,
		error : function() {
			$("#loan_account").html($("#loadingFail").html());
		},
		success : function(response) {
			if (hrefHack(response))
				return;
			$("#loan_account").html(response)
		},
		beforeSend : function() {
			$("#loan_account").html($("#loading").html());
		}
	});
}

// 我的投资
function getMyInvest(data) {
	var id = data.id;
	clearTab(id);
	$("#" + id).removeClass();
	$("#" + id).addClass("right_tab_select");
	$
			.ajax({
				url : "../my/getMyInvest.action?type="
						+ id
						+ "&bidPageIndex=1&failPageIndex=1&recoverPageIndex=1&closedPageIndex=1&badPageIndex=1",
				type : "POST",
				dataType : 'html',
				timeout : 100000,
				error : function() {
					$("#myInvest").html($("#loadingFail").html());
				},
				success : function(response) {
					if (hrefHack(response))
						return;
					$("#myInvest").html(response)
				},
				beforeSend : function() {
					$("#myInvest").html($("#loading").html());
				}
			});
}

function getClosedLoan(id, itme) {
	clearTab(id);
	$("#" + id).removeClass();
	$("#refund_list").removeClass();
	$("#" + id).addClass("right_tab_select");
	$("#" + id).show();
	$("#" + id + "_content").show();
	$(itme).hide();
	$("#inrepayLoan").hide();
	$.ajax({
		url : "../my/getClosedLoan.action?pageIndex=1",
		type : "POST",
		dataType : 'html',
		error : function() {
			$("#refund_content").html($("#loadingFail").html());
		},
		success : function(response) {
			if (hrefHack(response))
				return;
			$("#refund_content").html(response);
		},
		beforeSend : function() {
			$("#refund_content").html($("#loading").html());
		}
	});
}

function enterEvent(e) {
	if (e.keyCode == 13 || e.which == 13) {// 判断是否按下回车键
		searchLoans();
	}
}

function clearSearch() {
	$("#search").attr("value", "");
	var value = document.getElementById("search");
	value.style.color = "black";
}

function getOther(obj) {
	var selValue = $(obj).val();
	if (selValue == null || selValue == "") {
		$("#otherReceiver").show();
	} else {
		$("#otherReceiver").hide();
	}
}

function setValue(obj) {
	var txtValue = $(obj).val();
	$("#otherOption").val(txtValue);
}

function changeType(type) {
	$("#payFeeType").val(type);
	if ("alipay" == type) {
		$("#bankDiv").hide();
	}
	if ("kuaiqian" == type) {
		$("#bankDiv").hide();
	}
	if ("wangyin" == type) {
		$("#bankDiv").show();
	}
}

function getBankCode() {
	var val = $("input[name='bank']:checked").val();
	$("#bankId").val(val);
}

function getValue(obj) {
	var id = obj.id;
	var source = $("#" + id).val();
	$("#utmSource").val(source);
	$.ajax({
		url : "../partner/getUsersByUtmSource.action?utmSource=" + source,
		type : "POST",
		dataType : "html",
		error : function() {
			$("#summary_list").html($("#loadingFail").html());
		},
		success : function(data) {
			$("#summary_list").html(data);
		},
		beforeSend : function() {
			$("#summary_list").html($("#loading").html());
		}
	});
}
function getDetail(obj, loanId) {
	var id = obj.id;
	clearTab(id);
	$("#" + id).removeClass();
	$("#" + id).addClass("right_tab_select");
	$("#" + id).show();
	if (id == "dunDetail") {
		$(function() {
			$.ajax({
				url : "../lend/dunDetail.action?loanId=" + loanId,
				dataType : 'html',
				error : function(response) {
					if (hrefHack(response))
						return;
					$("#d_content").html($("#loadingFail").html());
				},
				success : function(response) {
					if (hrefHack(response))
						return;
					$("#d_content").html(response);
				},
				beforeSend : function() {
					$("#d_content").html($("#loading").html());
				}
			})
		});
	} else {
		$(function() {
			$.ajax({
				url : "../lend/getborrowerandlenderinfo.action?loanId="
						+ loanId + "&id=" + id,
				dataType : 'html',
				timeout : 10000,
				error : function() {
					$("#d_content").html($("#loadingFail").html());
				},
				success : function(response) {
					$("#d_content").html(response);
				},
				beforeSend : function() {
					$("#d_content").html($("#loading").html());
				}
			});
		});
	}
}

function delFavoriteBiao(id) {
	$
			.ajax({
				url : "../my/deleteSavedLoan.action?id=" + id,
				type : "POST",
				dataType : 'json',
				timeout : 10000,
				error : function(response) {
					if (response.result == 1) {
						alert("请选择您要删除的标！")
					} else if (response.result == 2) {
						alert("请先登录！");
					} else {
						alert("删除失败，请重试")
					}
				},
				success : function(response) {
					if (response.res) {
						$("#" + id).remove();
						var totalCount = $("#myFavoriteLoanList").children()
								.get(1).innerHTML;
						if (totalCount && totalCount > 0) {
							totalCount = totalCount - 1;
							$("#myFavoriteLoanList").children().get(1).innerHTML = totalCount;
						}
					} else {
						if (response.result == 1) {
							alert("请选择您要删除的标！")
						} else if (response.result == 2) {
							alert("请先登录！");
						} else {
							alert("删除失败，请重试")
						}
					}
				}
			});
}
/* 关于理财 */
function getAbout(type) {

	$.ajax({
		url : "../lend/getAboutPage.action?type=" + type,
		type : "POST",
		dataType : 'html',
		timeout : 10000,
		beforeSend : function() {
			$("#finance_left").html($("#loading").html());
		},
		success : function(response) {
			if (hrefHack(response))
				return;
			$("#finance_left").html(response);
		},
		error : function() {
			$("#finance_left").html($("#loadingFail").html());
		}
	});

}

function payBad(bnt) {
	var payAmount = $("#payAmount").val();
	var avaliablePoint = $("#avaliablePoint").val();
	payAmount = parseFloat(payAmount);
	avaliablePoint = parseFloat(avaliablePoint);
	if (payAmount <= 0) {
		alert("请输入您的还款金额!");
		$("#payAmount").attr("val", "");
		$("#payTip").hide();
		$(bnt).removeAttr("disabled");
		return;
	}
	if (payAmount > avaliablePoint) {
		alert("您的账户余额不足，请重新输入还款金额!");
		$("#payTip").hide();
		$(bnt).removeAttr("disabled");
		return;
	}
	$(bnt).attr("disabled", "disabled");
	$("#payTip").show();
	var loanId = $("#loanId").val();
	var b = window.confirm("您确定要还款吗？");
	if (b) {
		$.ajax({
			url : "../my/refundbadDebt.action?loanId=" + loanId + "&payAmount="
					+ payAmount + "&avaliablePoint=" + avaliablePoint,
			type : "POST",
			dataType : 'html',
			timeout : 100000,
			error : function(response) {
				$("#refundTab").children("tbody").children("tr").each(
						function() {
							$(this).children("td:first").children("input")
									.attr("disabled", "disabled");
						});
				$(bnt).removeAttr("disabled");
				$("#payTip").hide();
			},
			success : function(response) {
				var o = eval("o=" + response);
				if (o.tag) {
					alert(o.result);
					getRefundDetail("quick_refund", o.loanId, "BAD_DEBT", 0);
				} else {
					alert(o.result);
				}
				$(bnt).removeAttr("disabled");
				$("#payTip").hide();
			}
		});
	} else {
		$("#payTip").hide();
		$(bnt).removeAttr("disabled");
		return;
	}
}

function allFarmerLoans(data) {
	var id = data.id;
	clearTab(id);
	$("#" + id).removeClass();
	$("#" + id).addClass("right_tab_select");
	$("#" + id).show();

	$.ajax({
		url : "../lend/getfaramerloanbystatus.action?pageIndex=1&orderid=0&id="
				+ id,
		type : "POST",
		dataType : "html",
		error : function() {
			$("#b_content").html($("#loadingFail").html());
		},
		success : function(data) {
			$("#b_content").html(data);
		},
		beforeSend : function() {
			$("#b_content").html($("#loading").html());
		}
	});
}

function txtAmount(textarea, msg, length) {
	var val = $("#" + textarea);
	var msg = $("#" + msg);
	var ishover = false;
	var t = null;
	val.hover(function() {
		ishover = true;
		if (!ishover) {
			return;
		}
		t = setInterval(function() {
			var v = val.val();
			var len = 0
			for ( var i = 0, l = v.length; i < l; i++) {
				if ((v.charCodeAt(i) < 0) || (v.charCodeAt(i) > 255))
					len += 2;
				else
					len += 1;
			}
			len = parseInt(len / 2);
			if (len <= length) {
				var res = length - len;
				msg.html("还可以输入" + res + "字");
			} else {
				var res = len - length;
				msg.html("已超出<span style='color:red'>" + res + "</span>字");
			}
		}, 500)
	}, function() {
		ishover = false;
		clearInterval(t);
	})
}

$(function() {
	jQuery.validator.addMethod("bidNumber", function(value, element) {
		return this.optional(element) || (value >= 50);
	}, "投标金额必须大于50");
	jQuery.validator.addMethod("nickName", function(value, element) {
		return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
	}, "昵称只能由中文，英文字母、数字和下划线组成");
	jQuery.validator.addMethod("isEmail", function(value, element) {
		var myreg = /(\S)+[@]{1}(\S)+[.]{1}(\w)+/;
		return this.optional(element) || myreg.test($.trim(value));
	}, "请输入正确的邮箱地址");

});

function tips(input, msg, top, left) {
	if ($(".cashdraw_tips").length > 0)
		return;
	var tip = '<div class="cashdraw_tips" style="top: '
			+ top
			+ 'px; left:'
			+ left
			+ 'px; display: block;"><div class="cashdraw_tip_header"></div><div class="cashdraw_tip_body_container"><div class="cashdraw_tip_body"><div class="cashdraw_tip_content">'
			+ msg + '</div></div></div></div>';
	$(input).after(tip);
	input.onmouseout = function() {
		setTimeout(function() {
			$(".cashdraw_tips").remove()
		}, 500);
	}
}

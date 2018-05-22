/*
 * 
 * 
 * author:hb
 *对本工具函数，进行了严格的jsLint，jsUint验证。并用了use strict严格模式，以保存yui压缩不会出错
 *函数名规范，以驼峰形式命名，首字母小写
 *函数名规范，如果返回true,或false,函数名以isXxx()。如果函数不返回true,或false。默认都要返回:return this即返回Util对象，以进行链式调用。
 *函数名规范，与后台ajax交互，以getXxx()。
 *函数名规范，选项卡,以tabXxx();
 */
(function(win,undefined){
"use strict";
var Util = win.Util = win.Util ||{update: "20130407"};

Util.type = function(unknow) {
    //nodeType数组中的逗号不能删除，虽然jsLint会报错
    var objectType = {},
        nodeType = [, "HTMLElement", "Attribute", "Text", , , , , "Comment", "Document", , "DocumentFragment", ],
        str = "Array Boolean Date Error Function Number RegExp String",
        retryType = {'object': 1, 'function': '1'},
        toString = objectType.toString,
        strArr=str.split(" "),
        s = typeof unknow;

    for(var i=0;i<strArr.length;i++){
        objectType[ "[object " + strArr[i] + "]" ] = strArr[i].toLowerCase();
    }
    return !retryType[s] ? s
        : unknow == null ? "null"
        : unknow.type_
        || objectType[ toString.call( unknow ) ]
        || nodeType[ unknow.nodeType ]
        || ( unknow == unknow.window ? "Window" : "" )
        || "object";
};


Util.err=function(err){
    if(this.type(err)=="string")alert("错误提示："+err);
    return this;
}


Util.Is={
    isDate:function(intYear, intMonth, intDay){
        if (isNaN(intYear) || isNaN(intMonth) || isNaN(intDay))
            return false;
        if (intMonth > 12 || intMonth < 1)
            return false;
        if (intDay < 1 || intDay > 31)
            return false;
        if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11)&& (intDay > 30))
            return false;
        if (intMonth == 2) {
            if (intDay > 29)
                return false;
            if ((((intYear % 100 == 0) && (intYear % 400 != 0)) || (intYear % 4 != 0))&& (intDay > 28))
                return false;
        }
    },
    isIDNum:function(cId) {
        var pattern;
        if (cId.length == 15) {
            pattern = /^\d{15}$/;// 正则表达式,15位且全是数字
            if (pattern.exec(cId) == null) {
                return false;
            }
            if (!Util.isDate("19" + cId.substring(6, 8), cId.substring(8, 10), cId.substring(10, 12))) {
                return false;
            }
        } else if (cId.length == 18) {
            pattern = /^\d{17}(\d|x|X)$/;// 正则表达式,18位且前17位全是数字，最后一位只能数字,x,X
            if (pattern.exec(cId) == null) {
                return false;
            }
            if (!Util.isDate(cId.substring(6, 10), cId.substring(10, 12), cId.substring(12, 14))) {
                return false;
            }
            var strJiaoYan = [ "1", "0", "X", "9", "8", "7", "6", "5", "4", "3","2" ];
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
    },
    isUserName:function(n){
        var myreg = /^\w+(@)\w+(\.\w+)(\.\w+|)$/;
        var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        return myreg.test(n)||mobile.test(n);
    },
    isRealName:function(n){
        return /^[\u4E00-\u9FA5]+$/.test(n);
    },
    isNickName:function(n){
        return /^[\u4E00-\u9FA5]+$/.test(n);
    },
    isPsw:function(p){
        return /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,16}$/.test(p);
    },
    isMobile:function(t){
        return /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(t);
    },
    isPhone:function(p){
        return /^0\d{2,3}[-]?\d{8}$|^0\d{3}[-]?\d{7}$/.test(p);
    },
    isEmail:function(e){
        return /^\w+(@)\w+(\.\w+)(\.\w+|)$/.test(e);
    }
}

Util.creatMask=function(fn){
    if($("#page").length>0){$("#page").css("position","static");}
    var $div = $("<div id='mask' style='display:block;position:fixed;top:0;left:0;background:#000;z-index: 200;width:100%;height:100%;opacity: 0.6;filter:alpha(opacity=60);'></div>");
    if (/msie 6/i.test(navigator.userAgent)) {
        var w = Math.max(document.body.scrollWidth,document.documentElement.clientWidth) + 'px';
        var h = Math.max(document.body.scrollHeight,document.documentElement.clientHeight) + 'px';
        $div=$("<div id='mask' style='display:block;position:absolute;top:0;left:0;background:#000;width:"+w+";height:"+h+";filter:alpha(opacity=60)'><iframe style='width:100%;height:100%;background:#000;opacity:0.6;filter:alpha(opacity=0.6);' frameborder='0'></iframe></div>")
    }
    $("body").append($div);
    this.creatMask.close=function(){
         $div.remove();
    }
    if(Util.type(fn)==="function"){
        fn();
    }
    return this;
}

Util.uploadImage=function(width, height, path){
    this.creatMask();
    //下面有点乱，我暂时没有改
    $.ajax({
        url : "../getMediaPicker.action",
        type : "POST",
        dataType : "html",
        timeout : 10000,
        error : function() {
            $("#server_error").html("服务器忙，请稍候重试。");
        },
        success : function(html) {
            $("#server_error").empty();
            $(".mediaPicker").html(html).show();
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
                if (type == ".jpg" || type == ".gif" || type == ".JPG"|| type == ".GIF" || type == ".PNG" || type == ".png") {
                    $('.mp_upload_feedback').show();
                } else {
                    $('.mp_upload_hint').html("图片格式无效，您可以上传 JPG、GIF 或 PNG格式的图片。");
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
    return this;
}

Util.Form={
    setBirthdayByID:function(ID_id,Birth_id){
        $("#"+ID_id).keyup(function(){
            var str=this.value, BirthV="", reg ,ex;
            if(str.length==15){
                reg=/\d{6}(\d{2})([01]\d)([0123]\d)\d{3}/;
                if(reg.test(str)){
                    ex=reg.exec(str);
                    BirthV="19"+ex[1]+"-"+ex[2]+"-"+ex[3];
                }
            }else if(str.length==18){
                reg=/\d{6}([12]\d{3})([01]\d)([0123]\d)\d{3}(\d|\w)/;
                ex=reg.exec(str);
                BirthV=ex[1]+"-"+ex[2]+"-"+ex[3];
            }else{
                BirthV="";
            }
            $("#"+Birth_id).val(BirthV);
        })
    },
    setPhone:function(id1,id2,tagetPhoneId){
        var $id1=$("#"+id1),$id2=$("#"+id2),$tagetPhoneId=$("#"+tagetPhoneId);
        var $tagetV=$("#"+tagetPhoneId).val();
        if($tagetV!=""&&/-/.test($tagetV)){
            $id1.val($tagetV.split("-")[0]);
            $id2.val($tagetV.split("-")[1]);
        }else{
            $id2.val($tagetPhoneId.val());
        }
        function checkv(){
            var $id1v=$id1.val(),$id2v=$id2.val();
            if($id1v!=""&&$id2v!=""){
                $tagetPhoneId.val($id1v+"-"+$id2v);
            }else{
                $tagetPhoneId.val("");
            }
        }
        $id1.add($id2).bind("keyup",function(){
            checkv();
        })
        $id1.add($id2).bind("blur",function(){
            checkv();
        })
        $tagetPhoneId.css({"height":"1px","width":"1px","border":"0 none"}).show().keyup();
    }
}


Util.Vali={
    addValidateMethod:function(arr){
        if(Util.type(arr)!="array"){
            Util.err("参数必须为数组");
            return;
        }
        $.each(arr,function(k,v){
            jQuery.validator.addMethod(v,function(value,element){
                return this.optional(element) || Util.Is[v](value);
            }) ;
        })
    },
    validate:function(formstr,opt){
        var result={submitHandler:{},rules:{},messages:{}},isErr=false;
        if(Util.type(formstr)!="string"&&Util.type(formstr)!="HTMLElement"){
            Util.err("Util.vilidate第一个参数必须为字符串或HTMLElement");
            return;
        }
        if(Util.type(opt)!="object"){
            Util.err("Util.vilidate第二个参数必须为对象");
            return;
        }
        var def={
            submitHandler: function(form) {
                form.submit();
            },
            rules: {
                username: {
                    required: true,
                    maxlength: 32,
                    isUserName:true
                    // remote: "../checkEmail.action"
                },
                password: {
                    required: true,
                    //isPsw:true,
                    minlength: 6,
                    maxlength: 16
                },
                confirm_password: {
                    required: true,
                    minlength: 6,
                    equalTo: "#password"
                },
                agree: {
                    required:true
                }
            },
            messages: {
                username: {
                    required: "请输入邮箱地址或手机号码",
                    maxlength: "帐号长度不应超过32个字",
                    isUserName:"请输入正确的邮箱地址或手机号码"
                    //remote: "该账号已经存在"
                },
                password: {
                    required: "请输入密码",
                    //isPsw:"密码不能有空格,长度在6-16个字符之间",
                    minlength: "密码长度在6-16个字符之间",
                    maxlength: "密码长度在6-16个字符之间"
                },
                confirm_password: {
                    required: "请输入密码",
                    minlength: "密码长度在6-16个字符之间",
                    equalTo: "您输入的密码不一致"
                },
                agree: "请接受我们的条款"
            }
        };

        //def= $.extend(true,def,opt);

        result.submitHandler=typeof opt.submitHandler!="undefined"?opt.submitHandler:def.submitHandler;

        $.each(opt.rules,function(k,v){
            result.rules[k]=opt.rules[k]=="default"&&def.rules.hasOwnProperty(k)?def.rules[k]:opt.rules[k];
            result.messages[k]=opt.messages[k]=="default"&&def.messages.hasOwnProperty(k)?def.messages[k]:opt.messages[k];
        })

        $.each(result.rules,function(k,v){
            if(typeof k=="undefined"||Util.type(v)!="object"){
                Util.err("默认参数出错{"+k+":"+v+"}");
                isErr=true;
                return;
            }
        })

        $.each(result.messages,function(k,v){
            if(typeof k=="undefined"||Util.type(v)!="object"){
                Util.err("默认参数出错{"+k+":"+v+"}");
                isErr=true;
                return;
            }
        })
        if(isErr)return;
        // console.info(result.hasOwnProperty("rulesd"))
        var addMethodArr=["isRealName","isUserName","isNickName","isPsw","isMobile","isEmail"];
        Util.Vali.addValidateMethod(addMethodArr);
        $(formstr).validate({
            submitHandler:result.submitHandler,
            rules:result.rules,
            messages:result.messages
        });
    }
}



/**
 * 计算器
 */
Util.calculate=function() {
    var borrowAmount=$("#borrowAmount").val(), apr=parseFloat($("#apr").val()), repayTime=$("#repayTime").val(),type=$("#type").val(),$err= $("#error"),isShow;
    var reg = /^[0-9]*[1-9][0-9]*$/;
    var regF = /^[+\-]?\d+(.\d+)?$/;
    if (!reg.test(borrowAmount) || borrowAmount == '') {
        $err.html("借款金额必须为正整数!");
    } else if (!regF.test(apr) || apr == '') {
        $err.html("年利率必须为数字类型!");
    } else if (!reg.test(repayTime) || repayTime == '') {
        $err.html("月份格必须为数字类型!");
    } else if (parseFloat(repayTime) - 120 > 0) {
        $err.html("月份必须在120以内!");
    } else {
        $err.html("");
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




Util.Tab={
    tabCommon:function(tagId,optObj){
        var tagIdStr=/\.|#/.test(tagId)?tagId:"#"+tagId,url,$content,$lis,def;
        def={
            isCache:false,
            content:"#b_content",
            currentClass:"current",
            index:0
        }
        def= $.extend(def,optObj);
        def.content= /\.|#/.test(def.content)?def.content:"#"+def.content;
        $content= $(def.content);
        function getData(obj,url){
            var dataBind= obj[0];
            if(def.isCache&& $.data(dataBind).data!=null){
                $content.html($.data(dataBind).data);
                return;
            }
            $.ajax({
                url : url,
                dataType : 'html',
                cache:false,
                error : function() {
                },
                beforeSend : function() {
                    $content.html($("#loading").html());
                },
                success : function(data) {
                    $content.html(data);
                    if(def.isCache) $.data(dataBind,"data",data);
                }
            })
        }
        $lis=$(tagIdStr).find(">ul li");
        def.index=def.index<0?0:def.index>=$lis.length?$lis.length-1:def.index;
        $(tagIdStr).undelegate("click").delegate("ul li","click",function(){
            var $this=$(this);
            $lis.each(function(){
                $(this).removeClass(def.currentClass);
            })
            $this.addClass(def.currentClass);
            if(Util.type(def.url)=="function"){
                url=(function(){return def.url($this);})();
            }else{
                url=def.url+$this[0].id;
            }
            getData($this,url);
        })
        /*$lis.bind("click",function(){
            $lis.each(function(){
                $(this).removeClass(def.currentClass);
            })
            $(this).addClass(def.currentClass);
            url=def.url+$(this)[0].id;
            getData($(this),url);
        })*/
        $lis.eq(def.index).trigger("click");
    }
}

Util.Data={
    sendPhoneCode:function(telNum,btId,urlstr) {
        var t,i=60,_this=Util.Is;
        var $tel = typeof telNum == 'undefined'?$("#phone"):$("#"+telNum);
        var $bt= typeof btId == 'undefined'?$("#reveiveActiveCode"): $("#"+btId);
        var url = typeof urlstr == 'undefined'?"/sendPhoneCode.action?phone=" + $tel.val():urlstr+$tel.val();
        $bt.removeAttr("disabled").val("获取手机验证码").unbind("click").bind("click",function(){
            send();
        })
        function send(){
            if(!_this.isMobile($tel.val())){
                alert("请正确填写您的手机号码");
                return;
            }
            $bt.attr("disabled","disabled");
            t = setInterval(function(){
                if (i <= 0) {
                    $bt.removeAttr('disabled').val("获取手机验证码").css("background", "url(/theme/default/images/smail.jpg) no-repeat scroll 0 0 transparent");
                    clearInterval(t);
                } else {
                    $bt.val(i + "秒重新获取");
                    i--;
                };
            }, 1000);
            $.ajax({
                url:url,
                type:"POST",
                success:function(data){
                    if (data.result ){
                        alert(data.result);
                    }
                }
            })
        }
    },
    /**
     * 关注此标
     */
    addFavTender:function() {
        var $addFav=$("#addFav");
        if(!$addFav)return;
        $addFav.click(function(){
            var id=this.getAttribute("data-loanid");
            $.ajax({
                url : "/lend/saveLoan.action?id=" + id,
                type : "POST",
                dataType : 'json',
                timeout : 10000,
                beforeSend : function() {
                    $addFav.html("<span class=\"f_red\">正在添加关注...</span>");
                },
                success : function(response) {
                    $addFav.html("<span class=\"f_red\">" + response.result + "</span>");
                }
            });
        })
    },
    /**
     * 关注用户
     */
    addFavUser:function(id) {
        var $addFavUserLink=$("#addFavUserLink");
        if(!$addFavUserLink)return;
        $addFavUserLink.click(function(){
            var id=this.getAttribute("data-loanid");
            $.ajax({
                url : "/lend/saveLoan.action?id=" + id,
                dataType : 'json',
                timeout : 10000,
                beforeSend : function() {
                    $addFavUserLink.html("<span class=\"f_red\">添加关注中</span>")
                },
                success : function(response) {
                    if (response.result == '对不起，您不能关注自己！') {
                        alert(response.result);
                    } else {
                        $addFavUserLink.parent().text("已关注此用户");
                    }
                }
            });
        })
    },
    /**
     * 我要借款页面计算还款明细
     */
    getRepayDetail:function() {
        $("#borrowAmountError").hide();
        var borrowAmount=$("#borrowAmount").val(), apr=parseFloat($("#apr").val()), repayTime=$("#repayTime").val(), productId=$("#productId").val();
        borrowAmount = isNaN(borrowAmount) || borrowAmount == '' ? 0: parseFloat(borrowAmount);
        if (borrowAmount == 0 || borrowAmount == "") {
            return;
        }
        apr = isNaN(apr) || apr == '' ? 0 : parseFloat(apr);
        if (apr == 0 || apr == "") {
            return;
        }
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
    },
    /**
     * 获取提前还款详细
     */
    getInrepayDetail:function (tab, id, type, p) {
        var $con=$("#refund_content");
        $.ajax({
            url : "../my/getInrepayDetail.action?loanId=" + id + "&type=" + type + "&tab=" + tab,
            type : "POST",
            dataType : 'html',
            timeout : 1000000,
            beforeSend : function() {
                $con.html($("#loading").html());
            },
            error : function(res) {
                $con.html($("#loadingFail").html());
                $("#right").html(res);
            },
            success : function(res) {
                $con.html(res);
                $("#quick_refund").addClass("right_tab_unselect");
                $("#closedLoan").addClass("right_tab_unselect");
                $("#refund_list").addClass("right_tab_unselect");
                $("#inrepayLoan").addClass("right_tab_select").show();
            }

        });
    },
    // 借款详情里发送站内信
    sendInnerMail:function() {
        var $mail_content=$("#mail_content");
        var content = $mail_content.val();
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
                    $mail_content.val("");
                }
            }
        });
    }
}

Util.tip=function(opt){
    var def = {
		tips:'.simpletip',
        direction: 'right',
        theme: 'pastelblue',
        position:{},
		onmouseover:function(){},
		onmouseout:function(){}
    };
    var settings = $.extend(def, opt);
    var margins = {
        border: 6,
        top: 15,
        right: 15,
        bottom: 15,
        left: 15
    }
    var themes = ['', 'pastelblue', 'lightgray', 'darkgray'];
    var positions = ['top', 'top-right', 'right-top', 'right', 'right-bottom', 'bottom-right', 'bottom', 'bottom-left', 'left-bottom', 'left', 'left-top', 'top-left'];
    var titles = [];
    function getThemeName(element) {
        for (var n=0; n < themes.length; n++) {
            if (element.hasClass(themes[n])) return themes[n]
        };
        return settings.theme;
    }
    function getTooltipPosition(element) {
        for (var n=0; n < positions.length; n++) {
            if (element.hasClass(positions[n])) return positions[n];
        };
        return settings.direction;
    }
    function getTooltipTag(_event) {
        var title_ = titles[_event.data.index];
        if (title_.length > 0) return('<div id="simple-tooltip-'+_event.data.index+'" class="simple-tooltip '+getTooltipPosition($(_event.target))+' '+getThemeName($(_event.target))+'"><div id="simple-tooltip-content'+_event.data.index+'">'+title_+'</div><span class="arrow">&nbsp;</span></div>');
        return false;
    }
    function mouseOver(_event) {
        var element = $(this);
        var tooltip_tag = getTooltipTag(_event);
        if (tooltip_tag) {
            var exist_ = $('body').find('#simple-tooltip-'+_event.data.index);
            if(exist_.length > 0) {
                _event.preventDefault();
                return false;
            }
            var temp_ = $(tooltip_tag).appendTo($('body'));
            temp_.hide();
            reposTooltip(temp_, element);
			var content_=$("#simple-tooltip-content"+_event.data.index);
			settings.onmouseover(_event.data.index,content_);
            temp_.delay(180).fadeIn(200);
			
        }
        _event.preventDefault();
    };
    function mouseOut(_event) {
        var exist_ = $('body').find('#simple-tooltip-'+_event.data.index);
        if(exist_.length == 0) {
            _event.preventDefault();
            return false;
        }
        if(exist_.css('opacity') == 0) {
            _event.preventDefault();
            exist_.remove();
            return false;
        }
		//如果onmouseout返回为true,则不能关闭
		if(!settings.onmouseout(_event.data.index,exist_)){
            exist_.clearQueue().stop().fadeOut(100, function(){exist_.remove()});
		}

    };

    function reposTooltip(tooltip, element) {
        var pos = element.offset();
        switch (getTooltipPosition(element)) {
            default:
            case 'top':
                pos.top -= parseInt(tooltip.outerHeight() + margins.top);
                pos.left += parseInt((element.outerWidth()-tooltip.outerWidth())/2);
                break;
            case 'top-right':
                pos.top -= parseInt(tooltip.outerHeight() + margins.bottom);
                pos.left += parseInt(element.outerWidth() - margins.right - margins.border);
                break;
            case 'right-top':
                pos.top -= parseInt(tooltip.outerHeight() - margins.bottom);
                pos.left += parseInt(element.outerWidth() + margins.right);
                break;
            case 'right':
                pos.top += parseInt((element.outerHeight() - tooltip.outerHeight())/2);
                pos.left += parseInt(element.outerWidth() + margins.right);
                break;
            case 'right-bottom':
                pos.top += parseInt(element.outerHeight() - margins.bottom);
                pos.left += parseInt(element.outerWidth() + margins.right);
                break;
            case 'bottom-right':
                pos.top += parseInt(element.outerHeight() + margins.bottom);
                pos.left += parseInt(element.outerWidth() - margins.right - margins.border);
                break;
            case 'bottom':
                pos.top += parseInt(element.outerHeight() + margins.bottom);
                pos.left += parseInt((element.outerWidth()-tooltip.outerWidth())/2);
                break;
            case 'bottom-left':
                pos.top += parseInt(element.outerHeight() + margins.bottom);
                pos.left -= parseInt(tooltip.outerWidth() - margins.left - margins.border);
                break;
            case 'left-bottom':
                pos.top += parseInt(element.outerHeight() - margins.bottom);
                pos.left -= parseInt(tooltip.outerWidth() + margins.left);
                break;
            case 'left':
                pos.top += parseInt((element.outerHeight() - tooltip.outerHeight())/2);
                pos.left -= parseInt(tooltip.outerWidth() + margins.left);
                break;
            case 'left-top':
                pos.top -= (tooltip.outerHeight() - margins.bottom);
                pos.left -= parseInt(tooltip.outerWidth() + margins.left);
                break;
            case 'top-left':
                pos.top -= (tooltip.outerHeight() + margins.bottom);
                pos.left -= parseInt(tooltip.outerWidth() - margins.left);
                break;
        }
        tooltip.css('top', pos.top);
        tooltip.css('left', pos.left);
    }
    $(settings.tips).each(function(index, value) {
        $(value).css('cursor', 'pointer');
        titles[index] = $(value).attr('title');
        $(value).attr('title', '');
        $(value).bind('mouseenter', {index:index}, mouseOver);
        $(value).bind('mouseleave', {index:index}, mouseOut);
    })
}


})(window)
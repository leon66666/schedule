$.ajaxSetup({
    cache : false
});

$(function() {
    // 身份证验证
    jQuery.validator.addMethod("isIdCardNo", function(value, element) {
        value = $.trim(value);
        return this.optional(element) || checkCard(value);
    }, "请正确输入您的身份证号码");

    // 电话号码验证
    jQuery.validator.addMethod("isPhone", function(value, element) {
        value = $.trim(value);
        var tel = /^0\d{2,3}[-]?\d{8}$|^0\d{3}[-]?\d{7}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的电话号码");

    // 昵称验证
    jQuery.validator.addMethod("nickName", function(value, element) {
        value = $.trim(value);
        return this.optional(element) || /^[\u4e00-\u9fa5\w]+$/.test(value);
    }, "昵称只能由中文，英文字母、数字和下划线组成");

    // 手机验证
    jQuery.validator.addMethod("isMobile", function(value, element) {
        value = $.trim(value);
        var length = value.length;
        var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");

    // 邮政编码验证
    jQuery.validator.addMethod("isZipCode", function(value, element) {
        value = $.trim(value);
        var tel = /^[0-9]{6}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的邮政编码");

    jQuery.validator.addMethod("amount", function(value, element) {
        value = $.trim(value);
        return this.optional(element)
        || (value > 1 && value <= 300000 && /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/
            .test(value));
    }, "充值金额应大于1元且小于30万元，可带小数点后1位");

    jQuery.validator.addMethod("withdraw", function(value, element) {
        value = $.trim(value);
        return this.optional(element)
        || (/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/.test(value));
    }, "请输入正确金额");

    jQuery.validator.addMethod("bankCard", function(value, element) {
        value = $.trim(value);
        return this.optional(element) || /^(\d{19})|(\d{16})$/.test(value);
    }, "请输入正确的银行卡号");

    jQuery.validator.addMethod("reBankCard", function(value, element) {
        value = $.trim(value);
        return this.optional(element) || /^(\d{19})|(\d{16})$/.test(value);
    }, "请再次输入确认您的银行卡号");

    jQuery.validator.addMethod("blankCity", function(value, element) {
        return this.optional(element) || value != '请选择';
    }, "请选择省市");

    jQuery.validator.addMethod("nickNameLength", function(value, element) {
        value = $.trim(value);
        return this.optional(element)
        || (bytes(value) >= 4 && bytes(value) <= 12);
    }, "昵称长度在4-12个字节之间");

    jQuery.validator.addMethod("isFloat", function(value, element) {
        value = $.trim(value);
        return this.optional(element)
        || (/^(([1-9]{1}\d{0,9})|([0]{1}))(\.(\d){1})?$/.test(value));
    }, "请输入正确的金额");

    // 是否是手机或者电话
    jQuery.validator.addMethod("isPhoneOrMobile", function(value, element) {
        value = $.trim(value);
        var tel = /^0\d{2,3}[-]?\d{8}$|^0\d{3}[-]?\d{7}$/;
        var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        return this.optional(element) || tel.test(value) || mobile.test(value);
    }, "请正确填写您的工作电话");

    jQuery.validator.addMethod("isEmail", function(value, element) {
        var myreg = /(\S)+[@]{1}(\S)+[.]{1}(\w)+/;
        return this.optional(element) || myreg.test($.trim(value));
    }, "请输入正确的邮箱地址");

    $("#checkInForm").validate({
        submitHandler : function() {
            $("#checkInForm").ajaxSubmit({
                success : userFormSuccess
            });
        },
        rules : {
            amount : {
                required : true,
                amount : true
            }
        },
        messages : {
            amount : {
                required : "请输入充值金额"
            }
        }
    });

    $("#verifyPhoneForm").validate({
        submitHandler : function() {
            $("#verifyPhoneForm").ajaxSubmit({
                success : userFormSuccess
            });
        },
        rules : {
            phone : {
                required : true,
                isMobile : true,
                minlength : 11,
                maxlength : 11
            },
            validateCode : {
                required : true
            }
        },
        messages : {
            phone : {
                required : "请输入手机号码",
                minlength : "手机号码长度必须为11位",
                maxlength : "手机号码长度必须为11位",
                isMobile : "手机号码长度必须为11位"
            },
            validateCode : {
                required : "请输入手机验证码"
            }
        }
    });

    $("#settingForm").validate({
        submitHandler : function() {
            $("#settingForm").ajaxSubmit({
                success : userFormSuccess
            });
        }
    });

    $("#userAutoBidConditionForm").validate({
        submitHandler : function() {
            $("#userAutoBidConditionForm").ajaxSubmit({
                success : userFormSuccess
            });
        },
        rules : {
            fixedAmount : {
                required : true,
                isFloat : true
            },
            loanLowestInterest:{
                required : true,
                isFloat : true
            },
            loanHighestInterest:{
                required : true,
                isFloat : true
            },
            retainAmount:{
                required : true,
                isFloat : true
            }
        },
        messages : {
            fixedAmount : {
                required : "请输入正确的自动投标金额",
                isFloat:"请输入正确的自动投标金额"
            },
            loanLowestInterest:{
                required : "请输入正确的最低利息",
                isFloat : "请输入正确的最低利息"
            },
            loanHighestInterest:{
                required : "请输入正确的最高利息",
                isFloat : "请输入正确的最高利息"
            },
            retainAmount:{
                required : "请输入正确的账户保留金额",
                isFloat:"请输入正确的账户保留金额"
            }
        }
    });
});

function bytes(str) {
    if (typeof (str) != "string") {
        str = str.value;
    }
    var len = 0;
    for ( var i = 0; i < str.length; i++) {
        if (str.charCodeAt(i) > 127) {
            len++;
        }
        len++;
    }
    return len;
}

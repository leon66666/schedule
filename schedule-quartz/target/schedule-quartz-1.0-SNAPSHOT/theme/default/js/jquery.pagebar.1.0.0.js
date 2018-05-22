/// <reference path="jquery.js"/>
/*
 * jpagebar
 * version: 1.0.0 (01/12/2009)
 * @ jQuery v1.3.*
 * Copyright 2009 LYM [ lym6520@qq.com ]
 */
(function($) {
    $.extend($.fn, {
        jpagebar: function(setting) {
            var pb = $.extend({
                //pagebar 对象div
                renderTo: $(document.body),
                //总数
                totalNum: 0,
                //总页码
                totalpage: 0,
                //当前页码
                currentPage: 0,
                //分页条样式
                pagebarCssName: 'pagebar',
                //首页、上一页、下一页、尾页样式
                pageNameCssName:'pageName',
                //当前选中页码样式
                currentPageNumberCssName:'current_page_number',
                //点击页码action
                onClickPage : function(pageIndex){

                }
            }, setting);


            pb.resetPagebar = function(){
                var _this = this;
                _this.renderTo = (typeof _this.renderTo == 'string' ? $(_this.renderTo) : _this.renderTo);

                /* ---------->
				html :
				<div> ---->pagebar
                                        <span>共</span>
                                        <span>条</span>
                                        <span>&nbsp;&nbsp;</span>
					<a>首页</a>
					<a>上一页</a>
					<a>&nbsp;</a>...    ----> 显示页码
					<a>下一页</a>
					<a>尾页</a>
					<span class="page_hint"></span>
				</div>
				<-----------*/
                var pagebar = _this.renderTo;
                pagebar.attr('class',_this.pagebarCssName);

                //清空分页导航条
                pagebar.empty();

                if(totalpage = 0){
                    return ;
                }

                // 分页
                var front_block = parseInt(_this.currentPage) - 9;// 当前页码前面一截
                var back_block = parseInt(_this.currentPage) + 9;// 当前页码后面一截

                $('<span>共</span><span id=“total_count”>'+ _this.totalNum +'</span><span>条</span>')
                .appendTo(pagebar);
                
                $('<span>&nbsp;&nbsp;</span>')
                .appendTo(pagebar);

                //处理首页、上一页
                if(_this.currentPage == 1 ){
                    //当前页为第一页
                    $('<span>首页</span> ').attr('class',_this.pageNameCssName)
                    .appendTo(pagebar);
                    $('<span>上一页</span> ').attr('class',_this.pageNameCssName)
                    .appendTo(pagebar);

                }else{
                    //当前页大于第一页
                    $('<a>首页</a> ').attr('class',_this.pageNameCssName)
                    .bind("click", function(){
                        _this.onClickPage(1);
                    })
                    .appendTo(pagebar);
                    $('<a>上一页</a> ').attr('class',_this.pageNameCssName)
                    .bind("click", function(){
                        _this.onClickPage(_this.currentPage-1);
                    })
                    .appendTo(pagebar);
                }

                //处理数字页码

                if(_this.totalpage == 1){
                    //共1页
                    $('<span>1</span> ').attr('class',_this.currentPageNumberCssName)
                    .bind("click", function(){
                        _this.onClickPage(1);
                    })
                    .appendTo(_this.renderTo);

                }else{
                    //有多页
                    var tempBack_block = _this.totalpage;
                    var tempFront_block = 1;
                    if (back_block < _this.totalpage)
                        tempBack_block = back_block;
                    if (front_block > 1)
                        tempFront_block = front_block;

                    for (var i = tempFront_block; i <= tempBack_block; i++) {
                        if (_this.currentPage == i) {
                            //当前页
                            $('<span>'+i+'</span> ').attr('class', _this.currentPageNumberCssName)
                            .appendTo(pagebar);
                        } else {
                            $('<a>'+i+'</a> ')
                            .bind("click", function(){
                                _this.onClickPage(this.innerHTML);
                            }).appendTo(pagebar);

                        }
                    }
                }

                // 下一页, 尾页  处理
                if (_this.currentPage == _this.totalpage) {
                    //当前页为最后一页
                    $('<span>下一页</span> ').attr('class',_this.pageNameCssName)
                    .appendTo(pagebar);
                    $('<span>尾页</span> ').attr('class',_this.pageNameCssName)
                    .appendTo(pagebar);
                } else {
                    $('<a>下一页</a> ').attr('class',_this.pageNameCssName)
                    .bind("click", function(){
                        _this.onClickPage(parseInt(_this.currentPage)+1);
                    })
                    .appendTo(pagebar);
                    $('<a>尾页</a> ').attr('class',_this.pageNameCssName)
                    .bind("click", function(){
                        _this.onClickPage(_this.totalpage);
                    })
                    .appendTo(pagebar);
                }

                $('<span>&nbsp;&nbsp;</span>')
                .appendTo(pagebar);

                $(' <span id=“total_page”> '+ _this.totalpage +'</span><span>页</span>')
                .appendTo(pagebar);

                
                $('<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>')
                .appendTo(pagebar);
                
                $('<span class="page_feed_back">服务器忙，请稍后重试。</span>')
                .appendTo(pagebar);
                var lastIndex = window.location.href.indexOf("admin");
                if(lastIndex!=-1){
                    var url = window.location.href.substring(0,lastIndex);
                    var imgSrc = url + "../images/spin.gif";
                    $('<span class="page_hint"><img id="img_page_hint" src="'+imgSrc+'" style="vertical-align:middle;"/></span>').appendTo(pagebar);
                }else{
                    $('<span class="page_hint"><img id="img_page_hint" src="../images/spin.gif" style="vertical-align:middle;"/></span>').appendTo(pagebar);
                }
            }
            pb.resetPagebar();
        },
        setCurrentPage:function(_this,currentPage){
            _this.currentPage = currentPage;
            _this.resetPagebar(_this);
        }

    });
})(jQuery);
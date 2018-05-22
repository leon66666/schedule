/* common.js */
$(document).ready(function() {
	$('.dropdown-menu ul li').click(function(){
		var button = $(this).parent().parent().siblings('.dropdown-toggle');
		var input = $(button).siblings('input');
		var span = button.children('span');
		var oldHtml = span.html();
		var oldVal = span.attr('selectid');

		input.val($(this).attr('selectid'));

		span.html($(this).html());
        span.attr('selectid',$(this).attr('selectid'));
      
        $(this).html(oldHtml);
        $(this).attr('selectid',oldVal);
	});

	$('.dropdown-menu li').filter(function(){
		return $(this).data('selected') == 'selected';
	}).each(function(){
		var $button = $(this).parents('.dropdown-menu').siblings('button');
		$button.find('span').html($(this).html()).attr('selectid',$(this).attr('selectid'))
		       .end().siblings('input').val($(this).attr('selectid'));
	});

	
});
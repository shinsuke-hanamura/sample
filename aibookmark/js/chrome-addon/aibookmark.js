$(function(){

	console.log("image-blocker");

	$("img").each(function(i, elem) {
		var img_src    = $(elem).attr('src');
		var img_height = $(elem).height();
		var img_width  = $(elem).width();
		var img_pics  = img_height * img_width;
		// console.log(i + ', src:' + img_src + ', pics:' + img_pics + ', height:' + img_height + ', width:' + img_width);
		if (img_pics > 1000) { // 10000ピクセル以上を判定の対象とする
			if (true) {
				$(elem).replaceWith("<a href=" + img_src + ">この画像はブロックされました</a>");
				// console.log("この画像はブロックされました。src:" + img_src);

			}
		}
	});

});

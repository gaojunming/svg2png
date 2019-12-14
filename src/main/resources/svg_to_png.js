function svgToPng(svgStr,callback){
    var canvas= document.getElementById("canvas");
    var ctx= canvas.getContext("2d");
    var imgsrc = 'data:image/svg+xml;base64,'+ window.btoa(svgStr);
    var image = new Image();
    image.src = imgsrc;
    image.onload = function() {
        ctx.drawImage(image, 0, 0);
        var result = canvas.toDataURL("image/png");
        callback(result);
    };
}

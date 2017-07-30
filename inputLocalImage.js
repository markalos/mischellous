function loadLocalImage(event) {
		console.log('loadImage');
		var rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
		var file = event.target.files[0];
		var oFile = document.getElementById('imgsrc').files[0];
		var reader = new FileReader();
		reader.onload = function (event) {
			// document.getElementById('imgContainer').setAttribute('src', event.target.result);
			var imageContainer = document.getElementById('imgContainer');
			imageContainer.src = event.target.result;
			console.log(imageContainer.width);
		}
		if (!rFilter.test(oFile.type)) { alert("You must select a valid image file!"); return; }
		reader.readAsDataURL(file);
}


function inputLocalFile(inputElement, fileType) {
	if (fileType == 'img') {
		inputElement.addEventListener('click', loadLocalImage);
	}
}
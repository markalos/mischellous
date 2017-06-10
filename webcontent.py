#coding:utf8
import re
import urllib
import time
import os

def createFolder(folders = ['html','pictures']) :
	for folder in folders:
		if not os.path.isdir(folder):
			os.mkdirs(folder)
def getHTML(url, folder = 'html/'):
	page = urllib.urlopen(url)
	html = page.read()
	filename=url.split('/')[-2]
	with open(folder + filename + '.html', 'w') as fdst:
		fdst.write(html)
	return html

def getImg(imgList, folder = 'pictures/'):
	for imgurl in imgList:
		imgname = imgurl.split('/')[-1]
		filename = folder + imgname
		try:
			urllib.urlretrieve(imgurl, filename)
		except Exception as e:
			print 'download failed'
			print e
		
		time.sleep(0.05)

def getImgUrl(html_doc):
	# zhihu
	# pat = re.compile (r'http[a-z:/\.0-9\-_]+_r\.jpg')
	pat = re.compile(r'<img src="(http.*?)"')
	imgurl = pat.findall(html_doc)
	return imgurl

def downloadPictureFromUrl(url):
	html= getHTML(url)
	if html:
		imgSrc = list(set(getImgUrl(html)))
		print 'img number =', len(imgSrc)
		getImg(imgSrc)

def main():
	urls = []
	for i in xrange(2, 50):
		urls.append("http://www.girl13.com/page/" + str(i) + "/")
	for url in urls :
		downloadPictureFromUrl(url)

if __name__ == '__main__':
	createFolder()
	main()
 

 

 
#http://www.36kr.com/topic/brands
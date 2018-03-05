package file_operate;

public class GetContentType {
	public String getContentType(String objectName) {
		String postfix=objectName.substring(objectName.indexOf(".")+1,objectName.length());
		String contentType=null;
		switch(postfix) {
		case "mp3":
			contentType="audio/mp3";
			break;
		case "bmp":
			contentType="application/x-bmp";
			break;
		case "jpg":
			contentType="application/x-jpg";
			break;
		case "txt":
			contentType="text/plain";
			break;
		case "xls":
			contentType="application/vnd.ms-excel";
			break;
		case "xml":
			contentType="text/xml";
			break;
		case "mp4":
			contentType="video/mpeg4";
			break;
		case "doc":
			contentType="application/msword";
			break;
		case "exe":
			contentType="application/x-msdownload";
			break;
		case "apk":
			contentType="application/vnd.android.package-archive";
			break;
		default:
			break;
		}
		return contentType;
	}

}

package file_operate;

import java.net.URL;
import java.util.Date;

import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.GeneratePresignedUrlRequest;

public class GetFileUrl {
	public URL getFileUrl(String bucketName,String key,NosClient nosClient) {
	
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);

		//setExpiration为当前时间点+过期时间，设置可下载URL的过期时间。默认过期时间为1天。
		generatePresignedUrlRequest.setExpiration(new Date(System.currentTimeMillis()+3600*1000*24));
		URL url = nosClient.generatePresignedUrl(generatePresignedUrlRequest);
		return url;
	}

}

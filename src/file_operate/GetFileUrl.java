package file_operate;

import java.net.URL;
import java.util.Date;

import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.GeneratePresignedUrlRequest;

public class GetFileUrl {
	public URL getFileUrl(String bucketName,String key,NosClient nosClient) {
	
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);

		//setExpirationΪ��ǰʱ���+����ʱ�䣬���ÿ�����URL�Ĺ���ʱ�䡣Ĭ�Ϲ���ʱ��Ϊ1�졣
		generatePresignedUrlRequest.setExpiration(new Date(System.currentTimeMillis()+3600*1000*24));
		URL url = nosClient.generatePresignedUrl(generatePresignedUrlRequest);
		return url;
	}

}

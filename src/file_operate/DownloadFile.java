package file_operate;

import java.io.File;

import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.GetObjectRequest;
import com.netease.cloud.services.nos.model.ObjectMetadata;

public class DownloadFile {
	public void downloadFile(String destinationFile,String bucketName,String objectName,NosClient nosClient) {
		
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName,objectName);
		ObjectMetadata objectMetadata = nosClient.getObject(getObjectRequest, new File(destinationFile));
	}

}

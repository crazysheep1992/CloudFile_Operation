package file_operate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.netease.cloud.ClientConfiguration;
import com.netease.cloud.ClientException;
import com.netease.cloud.Protocol;
import com.netease.cloud.ServiceException;
import com.netease.cloud.auth.BasicCredentials;
import com.netease.cloud.auth.Credentials;
import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.CompleteMultipartUploadRequest;
import com.netease.cloud.services.nos.model.CompleteMultipartUploadResult;
import com.netease.cloud.services.nos.model.InitiateMultipartUploadRequest;
import com.netease.cloud.services.nos.model.InitiateMultipartUploadResult;
import com.netease.cloud.services.nos.model.ListPartsRequest;
import com.netease.cloud.services.nos.model.ObjectMetadata;
import com.netease.cloud.services.nos.model.PartETag;
import com.netease.cloud.services.nos.model.PartListing;
import com.netease.cloud.services.nos.model.PartSummary;
import com.netease.cloud.services.nos.model.UploadPartRequest;

public class UploadFile {
	public void uploadFile(String filePath,String bucketName,NosClient nosClient) {
		
		File objectName_file=new File(filePath.trim());//ȥ��ǰ��Ŀո�
		String objectName=objectName_file.getName();//��ȡ·���а������ļ���
		String contentType=new GetContentType().getContentType(objectName);//��ȡ��Ӧ�ļ���content-Type
		InputStream is=null;
	    try {
			 is = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Init inputstream IOException:�ļ�δ�ҵ�"+e.getMessage());
		}
	    InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, objectName);//��ʼ��һ���ϴ�����
	    //�㻹�����ڳ�ʼ���ֿ��ϴ�ʱ�������ļ���Content-Type
	    ObjectMetadata objectMetadata = new ObjectMetadata();//��ʼ��Ԫ���ݶ���
	    objectMetadata.setContentType(contentType);
	    initRequest.setObjectMetadata(objectMetadata);//�����󴫵�Ԫ����
	    InitiateMultipartUploadResult initResult = nosClient.initiateMultipartUpload(initRequest);
	    String uploadId = initResult.getUploadId();
	    
	    //���зֿ��ļ��ϴ�
	    int readLen=0;
	    int partIndex=1;
	    byte[] buffer=new byte[131072];
	    int buffSize=buffer.length;
	    try {
			while ((readLen = is.read(buffer, 0, buffSize)) != -1 ){
				  InputStream partStream = new ByteArrayInputStream(buffer);
				  nosClient.uploadPart(new UploadPartRequest().withBucketName(bucketName)
				  .withUploadId(uploadId).withInputStream(partStream)
				  .withKey(objectName).withPartSize(readLen).withPartNumber(partIndex));
				  partIndex++;
				}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //���ֿ��Ƿ�ȫ���ϴ����ֿ�MD5�Ƿ��뱾�ؼ�������������������ȱ�ٿ��������ϴ�
	    List<PartETag> partETags = new ArrayList<PartETag>();

	    int nextMarker = 0;
	    while (true) {
	      ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, objectName, uploadId);
	      listPartsRequest.setPartNumberMarker(nextMarker);
	      PartListing partList = nosClient.listParts(listPartsRequest);
	      for (PartSummary ps : partList.getParts()) {
	        nextMarker++;
	        partETags.add(new PartETag(ps.getPartNumber(), ps.getETag()));
	      }
	      if (!partList.isTruncated()) {
	        break;
	      }
	    }
	    
	    //��ɷֿ��ϴ�
	    CompleteMultipartUploadRequest completeRequest =  new CompleteMultipartUploadRequest(bucketName,objectName, uploadId, partETags);
	    CompleteMultipartUploadResult completeResult = nosClient.completeMultipartUpload(completeRequest);

	}

}

package file_operate;

import java.net.URL;
import java.util.Scanner;

import com.netease.cloud.ClientConfiguration;
import com.netease.cloud.Protocol;
import com.netease.cloud.auth.BasicCredentials;
import com.netease.cloud.auth.Credentials;
import com.netease.cloud.services.nos.NosClient;

public class File_Operate {
	
	public static String accessKey = "385d26b7f5c740fbbdd2501e18dd90f9";
	public static String secretKey = "d5254303e29b49ebaa05de2264658ece";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Credentials credentials = new BasicCredentials(File_Operate.accessKey, File_Operate.secretKey);
		ClientConfiguration conf = new ClientConfiguration();
		// 设置 NosClient 使用的最大连接数
		conf.setMaxConnections(200);
		// 设置 socket 超时时间
		conf.setSocketTimeout(10000);
		// 设置失败请求重试次数
		conf.setMaxErrorRetry(2);
		// 如果要用 https 协议，请加上下面语句
		conf.setProtocol(Protocol.HTTPS);
		//实例化NosClient对象
	    NosClient nosClient=new NosClient(credentials,conf);
	    nosClient.setEndpoint("nos-eastchina1.126.net");
	    
		System.out.println("请选择想要进行的操作：\n1-上传文件\n2-下载文件\n3-获取文件URL\n");
		System.out.print("请输入选择：");
		Scanner scan_choice = new Scanner(System.in);
		String str_choice=scan_choice.nextLine();
		int choice=Integer.parseInt(str_choice);
		switch(choice)
		{
		case 1://文件上传
			System.out.print("请输入上传文件路径：");
			String filePath = new Scanner(System.in).nextLine();
		    System.out.print("请输入bucket名：");
			String bucketname = new Scanner(System.in).nextLine();
			UploadFile ul=new UploadFile();
			ul.uploadFile(filePath,bucketname,nosClient);
			System.out.println("上传成功！");
			break;
		case 2://文件下载
			System.out.println("请输入需下载文件所在的bucket名：");
			String downloadBucketName = new Scanner(System.in).nextLine();
		    System.out.println("请输入需要下载的文件名：");
		    String downloadObjectName = new Scanner(System.in).nextLine();
		    System.out.println("请输入文件下载路径：");
		    String destinationFile = new Scanner(System.in).nextLine();
		    DownloadFile dlf=new DownloadFile();
		    dlf.downloadFile(destinationFile, downloadBucketName, downloadObjectName, nosClient);
		    System.out.println("下载完成！");
			break;
		case 3://获取指定文件的URL
			System.out.print("请输入bucket的名称：");
			String bucketName=new Scanner(System.in).nextLine();
			System.out.print("请输入需要生成URL的文件名：");
			String key=new Scanner(System.in).nextLine();
			URL url=new GetFileUrl().getFileUrl(bucketName, key, nosClient);
			System.out.println("该文件的下载地址为："+url);
			break;
		}
		scan_choice.close();
	}

}

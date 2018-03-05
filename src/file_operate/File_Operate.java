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
		// ���� NosClient ʹ�õ����������
		conf.setMaxConnections(200);
		// ���� socket ��ʱʱ��
		conf.setSocketTimeout(10000);
		// ����ʧ���������Դ���
		conf.setMaxErrorRetry(2);
		// ���Ҫ�� https Э�飬������������
		conf.setProtocol(Protocol.HTTPS);
		//ʵ����NosClient����
	    NosClient nosClient=new NosClient(credentials,conf);
	    nosClient.setEndpoint("nos-eastchina1.126.net");
	    
		System.out.println("��ѡ����Ҫ���еĲ�����\n1-�ϴ��ļ�\n2-�����ļ�\n3-��ȡ�ļ�URL\n");
		System.out.print("������ѡ��");
		Scanner scan_choice = new Scanner(System.in);
		String str_choice=scan_choice.nextLine();
		int choice=Integer.parseInt(str_choice);
		switch(choice)
		{
		case 1://�ļ��ϴ�
			System.out.print("�������ϴ��ļ�·����");
			String filePath = new Scanner(System.in).nextLine();
		    System.out.print("������bucket����");
			String bucketname = new Scanner(System.in).nextLine();
			UploadFile ul=new UploadFile();
			ul.uploadFile(filePath,bucketname,nosClient);
			System.out.println("�ϴ��ɹ���");
			break;
		case 2://�ļ�����
			System.out.println("�������������ļ����ڵ�bucket����");
			String downloadBucketName = new Scanner(System.in).nextLine();
		    System.out.println("��������Ҫ���ص��ļ�����");
		    String downloadObjectName = new Scanner(System.in).nextLine();
		    System.out.println("�������ļ�����·����");
		    String destinationFile = new Scanner(System.in).nextLine();
		    DownloadFile dlf=new DownloadFile();
		    dlf.downloadFile(destinationFile, downloadBucketName, downloadObjectName, nosClient);
		    System.out.println("������ɣ�");
			break;
		case 3://��ȡָ���ļ���URL
			System.out.print("������bucket�����ƣ�");
			String bucketName=new Scanner(System.in).nextLine();
			System.out.print("��������Ҫ����URL���ļ�����");
			String key=new Scanner(System.in).nextLine();
			URL url=new GetFileUrl().getFileUrl(bucketName, key, nosClient);
			System.out.println("���ļ������ص�ַΪ��"+url);
			break;
		}
		scan_choice.close();
	}

}

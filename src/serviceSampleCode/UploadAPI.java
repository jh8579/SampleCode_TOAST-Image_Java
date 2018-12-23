package serviceSampleCode;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UploadAPI {
    private String appKey;          // Image 콘솔 appKey
    private String secretKey;       // Image 콘솔 secretKey

    public UploadAPI(String appKey, String secretKey) {		// appKey secretKey 초기화
        this.appKey = appKey;
        this.secretKey = secretKey;
    }
    
    public void uploadOneFile(String path, String objectPath, boolean overwrite, boolean autorename, String[] operationId){
        try {
        	System.out.println("단일 파일 업로드 API");
        	
        	File objFile = new File(objectPath);
            byte[] fileContent = Files.readAllBytes(objFile.toPath());

            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/images?";
            apiURL += "path=" + path;
            apiURL +="&overwrite=" + overwrite;
            apiURL +="&autorename=" + autorename;
            apiURL +="&operationIds=";
            for(int i=0; i<operationId.length; i++) {
            	apiURL += operationId[i];
            	if(i != operationId.length-1) {
            		apiURL += ",";
            	}
            }
            System.out.println(apiURL);
            
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setDoOutput(true);
            
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(fileContent);
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();      // response code 입력

            BufferedReader br;
            if(responseCode==200) {     // 정상 코드
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {                    // 에러 코드
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());        // response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void uploadMultiFile(String basepath, boolean overwrite, boolean autorename, String[] operationId, String callbackUrl, String[] pathList){
        try {
        	System.out.println("다중 파일 업로드 API");
        	
        	String BOUNDARY = "----^^^----";        // boundary 변수 지정
            String LINE_FEED = "\r\n";              // 개행문자 변수 지정
	
        	File[] fileList = new File[pathList.length];        // 주어진 파일 경로 File 형식으로 변환
        	for(int i=0;  i<pathList.length; i++) {
        		fileList[i] = new File(pathList[i]);
        	}
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/images";
            System.out.println(apiURL);
            
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            
            conn.setRequestMethod("POST");      // Header 설정
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            JSONArray operationIds = new JSONArray();           // simple json 라이브러리 사용
            for(int i=0;  i<operationId.length; i++) {
            	operationIds.add(operationId[i]);
        	}
            
            JSONObject jsonData = new JSONObject();
            jsonData.put("basepath", basepath);
            jsonData.put("overwrite", overwrite);
            jsonData.put("autorename", autorename);
            jsonData.put("operationIds", operationIds);
            jsonData.put("callbackUrl", callbackUrl);

            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            writer.writeBytes("--" + BOUNDARY);
            writer.writeBytes(LINE_FEED);
            writer.writeBytes("Content-Disposition:form-data;name=\"params\"" + LINE_FEED);     // params 데이터 시작
            writer.writeBytes(LINE_FEED);
            
            writer.writeBytes(jsonData.toString() + LINE_FEED);                                 // json 데이터 전송
            writer.writeBytes(LINE_FEED);

            for(int i=0; i<fileList.length; i++) {
            	writer.writeBytes("--" + BOUNDARY);                                             // file 데이터 전송
            	writer.writeBytes(LINE_FEED);
                writer.writeBytes("Content-Disposition: form-data; name=\"files\"; filename=\"" + fileList[i].getName() + "\"" + LINE_FEED);
                writer.writeBytes(LINE_FEED);
                writer.write(Files.readAllBytes(fileList[i].toPath()));
                writer.writeBytes(LINE_FEED);
            }
           
            writer.writeBytes("--" + BOUNDARY + "--" + LINE_FEED);      // body의 끝을 알림
            writer.flush();
            writer.close();

            int responseCode = conn.getResponseCode();      // response code 입력

            BufferedReader br;
            if(responseCode==200) {     // 정상 코드
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {                    // 에러 코드
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());        // response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
	public static void main(String[] args) {
        String appKey = "{Image App Key}";         // image 콘솔 상단에 URL & APPKEY 에서 확인
        String secretKey = "{Image Sercret Key}" ;
        
        String basePath = "/jinho";         // (필수)이미지 업로드 경로
        String fileName = "/sample.jpg";    // (필수)이미지 파일 이름

        // (필수)다중 이미지 업로드 경로
        String[] rootPathList = {"C:\\Users\\NHNEnt\\Desktop\\workspaces\\java-sample\\image-guide\\public\\sample.jpg", "C:\\Users\\NHNEnt\\Desktop\\workspaces\\java-sample\\image-guide\\public\\sample.png"};

        String[] operationIdList = {"200x300", "100x100"};  // (선택)이미지 오퍼레이션 리스트
        String callbackURL = "";                            // (선택)콜백 URL 지정

        boolean overWrite = false;      // (선택)같은 이름 파일 덮어쓰기 여부
        boolean autorename = true;      // (선택)같은 이름 파일 "이름(1).확장자" 형식으로 파일명 변경 여부
        
        UploadAPI uploadAPI = new UploadAPI(appKey, secretKey);
        
        uploadAPI.uploadOneFile(basePath + fileName, rootPathList[0], overWrite, autorename,  operationIdList);
        System.out.println();
        
        uploadAPI.uploadMultiFile(basePath, overWrite, autorename, operationIdList, callbackURL, rootPathList);
        System.out.println();
	}
}

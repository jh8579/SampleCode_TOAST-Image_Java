package serviceSampleCode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ImageOperationAPI {
    private String appKey;          // Image 콘솔 appKey
    private String secretKey;       // Image 콘솔 secretKey

    public ImageOperationAPI(String appKey, String secretKey) {		// appKey secretKey 초기화
        this.appKey = appKey;
        this.secretKey = secretKey;
    }
    
    public void putOperation(String operationId, String description, boolean realtimeService, boolean deleteThumbnail){
        try {
        	System.out.println("Image Operation 생성 및 수정 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/operations/";	// API URL + image 콘솔 appKey
            String query = operationId;

            URL url = new URL(apiURL+query);								// URL 초기화
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP 연결
            																	// Header 설정
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONObject option = new JSONObject();           // simple json 라이브러리 사용

            option.put("width", 100);                       // 예시) 100x100 크기 조절 옵션
            option.put("height", 100);
            option.put("quality", 75);
            option.put("upDownSizeType", "downOnly");
            option.put("keepAnimation", true);
            option.put("keepExi", true);
            option.put("autoOrient", false);
            option.put("targetExtension", null);
            
            JSONObject operation = new JSONObject();        // 리사이즈 옵션 지정
            operation.put("templateOperationId", "resize_max_fit");
            operation.put("option", option);
            
            JSONArray data = new JSONArray();
            data.add(operation);
            
            JSONObject jsonData = new JSONObject();
            jsonData.put("description", description);
            jsonData.put("realtimeService", realtimeService);
            jsonData.put("deleteThumbnail", deleteThumbnail);
            jsonData.put("data", data);
            
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(jsonData.toString().getBytes("UTF-8"));
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

    public void getOperation(){
        try {
            System.out.println("Image Operation 목록 조회 API");

            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/operations/";	// API URL + image 콘솔 appKey

            System.out.println(apiURL);

            URL url = new URL(apiURL);											// URL 초기화
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP 연결

            conn.setRequestMethod("GET");           // Header 설정
            conn.setRequestProperty("Authorization", secretKey);
            conn.setDoOutput(true);

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

    public void getDetailOperation(String operationId){
        try {
            System.out.println("Image Operation 상세 조회 API");

            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/operations/";	// API URL + image 콘솔 appKey
            String query = operationId;
            System.out.println(apiURL+query);

            URL url = new URL(apiURL+query);								// URL 초기화
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP 연결

            conn.setRequestMethod("GET");       // Header 설정
            conn.setRequestProperty("Authorization", secretKey);
            conn.setDoOutput(true);

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
    
    public void deleteOperation(String operationId, boolean deleteThumbnail){
        try {
        	System.out.println("Image Operation 삭제 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/operations/";	// API URL + image 콘솔 appKey
            String query = operationId;
            System.out.println(apiURL+query);

            URL url = new URL(apiURL+query);								// URL 초기화
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP 연결
            																	// Header 설정
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONObject jsonData = new JSONObject();								// simple json 라이브러리 사용
            jsonData.put("deleteThumbnail", deleteThumbnail);
            
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(jsonData.toString().getBytes("UTF-8"));
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
    
    public void executeOperation(String basepath, String[] filePathList, String[] operationIdList, String callbackUrl){
        try {
        	System.out.println("Image Operation 실행(비동기) API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/operations-exec";	// API URL + image 콘솔 appKey
            
            URL url = new URL(apiURL);											// URL 초기화
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP 연결
            																	// Header 설정
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONArray filepaths = new JSONArray();
            for(int i=0; i<filePathList.length; i++) {
            	filepaths.add(filePathList[i]);
            }
            
            JSONArray operationIds = new JSONArray();
            for(int i=0; i<operationIdList.length; i++) {
            	operationIds.add(operationIdList[i]);
            }
            
            JSONObject jsonData = new JSONObject();								// simple json 라이브러리 사용
            jsonData.put("basepath", basepath);
            jsonData.put("filepaths", filepaths);
            jsonData.put("operationIds", operationIds);
            jsonData.put("callbackUrl", callbackUrl);
            
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(jsonData.toString().getBytes("UTF-8"));
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

    public static void main(String[] args) {
        String appKey = "{Image App Key}";         // image 콘솔 상단에 URL & APPKEY 에서 확인
        String secretKey = "{Image Sercret Key}";
        
        String basePath = "/";                              // (필수)이미지 오퍼레이션 결과물을 저장할 경로
        String operationId = "100x100";                     // (필수)이미지 오퍼레이션 ID(이름)
        String description = "100x100으로 만들어 주기";     // (선택)이미지 오퍼레이션 설명
        boolean realtimeService = true;                     // (선택)이미지 실시간 서비스 여부
        boolean deleteThumbnail = true;                     // (선택)해당 이미지 오퍼레이션으로 생성된 파일 삭제 여부
        
        String[] imagePathList = {"/jinho/sample.png", "/jinho/sample.jpg"};    // (필수)이미지 경로 리스트
        String[] operationIdList = {"200x300", "100x100"};                      // (필수)이미지 오퍼레이션 ID 리스트

        String callbackURL = "";                            // (선택)콜백 URL 지정
        
        ImageOperationAPI imageOperationAPI = new ImageOperationAPI(appKey, secretKey);
        
        imageOperationAPI.putOperation(operationId, description, realtimeService, deleteThumbnail);
        System.out.println();
        
        imageOperationAPI.getOperation();
        System.out.println();
        
        imageOperationAPI.getDetailOperation(operationId);
        System.out.println();
                
        imageOperationAPI.executeOperation(basePath, imagePathList, operationIdList, callbackURL);
        System.out.println();
        
        imageOperationAPI.deleteOperation(operationId, deleteThumbnail);
        System.out.println();
	}
}

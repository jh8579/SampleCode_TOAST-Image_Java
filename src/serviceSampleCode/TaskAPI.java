package serviceSampleCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TaskAPI {
    private String appKey;          // Image 콘솔 appKey
    private String secretKey;       // Image 콘솔 secretKey

    public TaskAPI(String appKey, String secretKey) {		// appKey secretKey 초기화
        this.appKey = appKey;
        this.secretKey = secretKey;
    }
    
    public void getQueue(String queueId){
        try {
        	System.out.println("작업 조회 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/queues/";	// API URL + image 콘솔 appKey
            String query = queueId;
            System.out.println(apiURL+query);

            URL url = new URL(apiURL+query);											// URL 초기화
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP 연결
            																	// Header 설정
            conn.setRequestMethod("GET");
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
    
    public static void main(String[] args) {
        String appKey = "{Image App Key}";         // image 콘솔 상단에 URL & APPKEY 에서 확인
        String secretKey = "{Image Sercret Key}";
        
        String queueId = "e340949c-e7a8-416a-b9ec-cb99ab0625be";    // (필수) 검색할 작업 ID
        
        TaskAPI taskAPI = new TaskAPI(appKey, secretKey);
        
        taskAPI.getQueue(queueId);
        System.out.println();
        
	}
}

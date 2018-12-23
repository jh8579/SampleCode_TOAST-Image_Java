package serviceSampleCode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;

public class LiveServiceAPI {
    private String appKey;          // Image 콘솔 appKey
    private String secretKey;       // Image 콘솔 secretKey

    public LiveServiceAPI(String appKey, String secretKey) {		// appKey secretKey 초기화
        this.appKey = appKey;
        this.secretKey = secretKey;
    }
    
    public void getLiveService(){
        try {
        	System.out.println("실시간 서비스 조회 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/users";	// API URL + image 콘솔 appKey
    
            System.out.println(apiURL);

            URL url = new URL(apiURL);											// URL 초기화
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
    
    public void putLiveService(boolean realtimeService) {
    	try {
        	System.out.println("실시간 서비스 변경 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/users";	// API URL + image 콘솔 appKey

            URL url = new URL(apiURL);											// URL 초기화
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP 연결
            																	// Header 설정
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONObject jsonData = new JSONObject();
            jsonData.put("realtimeService", realtimeService);
            
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
        
        boolean realtimeService = false;           // (필수)실시간 서비스 여부
        
        LiveServiceAPI liveServiceAPI = new LiveServiceAPI(appKey, secretKey);
        
        liveServiceAPI.getLiveService();
        System.out.println();
        
        liveServiceAPI.putLiveService(realtimeService);
        System.out.println();
	}
}

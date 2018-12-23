package serviceSampleCode;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FolderAPI {
	private String appKey;          // Image 콘솔 appKey
    private String secretKey;       // Image 콘솔 secretKey
    
    public FolderAPI(String appKey, String secretKey) {		// appKey secretKey 초기화
        this.appKey = appKey;
        this.secretKey = secretKey;
    }
    
    public void postFolder(String folderName){
        try {
        	System.out.println("폴더 생성 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/folders";	// API URL + image 콘솔 appKey

            URL url = new URL(apiURL);											// URL 초기화
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP 연결
            																	// Header 설정
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            String data = "{\"path\": \""+ folderName + "\"}";                  // json data 처리 방안(1) String 형태로 json 작성
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(data.getBytes("UTF-8"));                     // UTF-8 인코딩
            wr.flush();
            wr.close();
//            
//            or
//            
//            JSONObject data = new JSONObject();								// json data 처리 방안(2) simple json 라이브러리 사용
//            data.put("path", folderName);
//            
//            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//            wr.write(data.toString().getBytes("UTF-8"));
//            wr.flush();
//            wr.close();

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
    
    public void getFile(String path, String createdBy, String name, int page, int rows, String sort){
        try {
        	System.out.println("폴더 내 파일 목록 조회 API");

            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/folders?";	// API URL + image 콘솔 appKey
            String query = "basepath=" + URLEncoder.encode(path, "UTF-8");
            query += "&createdBy=" + createdBy;
            query += "&name=" + name;
            query += "&page=" + page;
            query += "&rows=" + rows;
            query += "&sort=" + sort;
            System.out.println(apiURL+query);
            
            URL url = new URL(apiURL+query);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "application/json");
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
    
    public void getFolder(String path){
        try {
        	System.out.println("폴더 속성 조회 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/"+ appKey +"/properties?";
            String query = "path="+ URLEncoder.encode(path, "UTF-8");
            System.out.println(apiURL+query);

            URL url = new URL(apiURL+query);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "application/octet-stream");

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
        
        String basePath = "/";            // image 서비스 상에 경로 지정
        String folderName = "toast";

        String createdBy = "U";                     // 목록 조회 대상 지정
        String name = "sample.png";                 // 파일 이름 확인
        int page = 1;                               // 페이지 갯수 정의
        int rows = 100;                             // 색인 열수 정의
        String sort = "name:asc";                   // 정렬 기준 정의
        
        FolderAPI folderAPI = new FolderAPI(appKey, secretKey);     // FolderAPI 생성
        
        folderAPI.postFolder(basePath + folderName);     // path="폴더 생성할 경로" + "폴더 이름"
        System.out.println();
        
        folderAPI.getFile(basePath + folderName, createdBy, name, page, rows, sort);     // 조회할 폴더 경로
        System.out.println();
        
        folderAPI.getFolder(basePath + folderName);
        System.out.println();
        
    }
}

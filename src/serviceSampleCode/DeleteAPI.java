package serviceSampleCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteAPI {
    private String appKey;          // Image 콘솔 appKey
    private String secretKey;       // Image 콘솔 secretKey

    public DeleteAPI(String appKey, String secretKey) {		// appKey secretKey 초기화
        this.appKey = appKey;
        this.secretKey = secretKey;
    }
    
    public void deleteOneFile(String folderId, String fileId, boolean includeThumbnail){
        try {
        	System.out.println("단일 삭제(동기) API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/images/sync?";	// API URL + image 콘솔 appKey
            String query = "";
            //query += "folderId=" + folderId + "&";        // folder 삭제 시 주석 삭제
            //query += "fileId=" + fileId + "&";            // file 삭제 시 주석 삭제
            query += "includeThumbnail=" + includeThumbnail;
            System.out.println(apiURL+query);

            URL url = new URL(apiURL+query);											// URL 초기화
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP 연결

            conn.setRequestMethod("DELETE");                        // Header 설정
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
    
    public void deleteMultiFile(String folderId, String fileId, boolean includeThumbnail){
        try {
        	System.out.println("다중 삭제(비동기) API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/images/async?";	// API URL + image 콘솔 appKey
            String query = "folderIds=" + folderId + "&";
            query += "files=" + fileId + "&";
            query += "includeThumbnail=" + includeThumbnail;
            System.out.println(apiURL+query);

            URL url = new URL(apiURL+query);											// URL 초기화
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP 연결
            																	// Header 설정
            conn.setRequestMethod("DELETE");
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
        String secretKey = "{Image Sercret Key}" ;
        
        String deleteFolderId = "880ddbb6-5454-47e3-9353-bd038ee4c9e9";     // (필수) 삭제를 원하는 폴더 ID
        String deleteFileId = "714794e1-7291-4b07-b102-0f3628e62c71";       // (필수) 삭제를 원하는 파일 ID
        
        String deleteFolderId2 = "21a697cb-875c-4920-ab8e-f14a35f675bc";    // (선택) 다중 삭제를 원하는 폴더 ID
        String deleteFileId2 = "9279f246-ba88-4d59-b82f-f0b2502311d7";      // (선택) 다중 삭제를 원하는 파일 ID
        
        boolean deleteIncludeThumbnail = true;          // (선택) 해당 파일로 만들어진 썸네일 같이 삭제 여부
        
        DeleteAPI deleteAPI = new DeleteAPI(appKey, secretKey);
        
        deleteAPI.deleteOneFile(deleteFolderId, deleteFileId, deleteIncludeThumbnail);
        System.out.println();
        
        deleteAPI.deleteMultiFile(deleteFolderId + "," + deleteFolderId2, deleteFileId + "," + deleteFileId2, deleteIncludeThumbnail);
        System.out.println();
	}

}

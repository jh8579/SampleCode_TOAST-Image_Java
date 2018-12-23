package totalSampleCode;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject; // ?���? ?��?��브러�? ?���?

public class ImageAPI {
    private String appKey;          // Image 콘솔 appKey
    private String secretKey;       // Image 콘솔 secretKey

    public ImageAPI(String appKey, String secretKey) {
        this.appKey = appKey;
        this.secretKey = secretKey;
    }
    
    public void postFolder(String folderName){
        try {
            System.out.println("폴더 생성 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/folders";

            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            String data = "{\"path\": \""+ folderName + "\"}";
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(data.getBytes("UTF-8"));
            wr.flush();
            wr.close();
//            
//            or
//            
//            JSONObject data = new JSONObject();
//            data.put("path", folderName);
//            
//            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//            wr.write(data.toString().getBytes("UTF-8"));
//            wr.flush();
//            wr.close();
           
            int responseCode = conn.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void getFile(String path, String createdBy, String name, int page, int rows, String sort){
        try {
            System.out.println("폴더 내 파일 목록 조회 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/folders?";
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

            int responseCode = conn.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
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

            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void uploadOneFile(String path, String objectPath, boolean overwrite, boolean autorename, String[] operationId){
        try {
            System.out.println("단일 파일 업로드 API");
        	
        	File objFile = new File(objectPath);
            byte[] fileContent = Files.readAllBytes(objFile.toPath());
            System.out.println(fileContent);

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

            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void uploadMultiFile(String basepath, boolean overwrite, boolean autorename, String[] operationId, String callbackUrl, String[] pathList){
        try {
            System.out.println("다중 파일 업로드 API");
        	
        	String boundary = "----fjslejfslejfls";
            String LINE_FEED = "\r\n";
            String charset = "UTF-8";
	
        	File[] fileList = new File[pathList.length];
        	for(int i=0;  i<pathList.length; i++) {
        		fileList[i] = new File(pathList[i]);
        	}
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/images";
            System.out.println(apiURL);
            
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            
            
            JSONArray operationIds = new JSONArray();
            for(int i=0;  i<operationId.length; i++) {
            	operationIds.add(operationId[i]);
        	}
            
            JSONObject jsonData = new JSONObject();								// JSONObject ?��?�� -?���? ?��?��브러�? ?��?��
            jsonData.put("basepath", basepath);
            jsonData.put("overwrite", overwrite);
            jsonData.put("autorename", autorename);
            jsonData.put("operationIds", operationIds);
            jsonData.put("callbackUrl", callbackUrl);

            DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
            writer.writeBytes("--" + boundary);
            writer.writeBytes(LINE_FEED);
            writer.writeBytes("Content-Disposition:form-data;name=\"params\"" + LINE_FEED);
            writer.writeBytes(LINE_FEED);
            
            writer.writeBytes("{\"basepath\":\"/jinho\",\"overwrite\": true}" + LINE_FEED);
            writer.writeBytes(LINE_FEED);
            //writer.writeBytes(params.toString() + LINE_FEED);

            for(int i=0; i<fileList.length; i++) {
            	writer.writeBytes("--" + boundary);
            	writer.writeBytes(LINE_FEED);
                writer.writeBytes("Content-Disposition: form-data; name=\"files\"; filename=\"" + fileList[i].getName() + "\"" + LINE_FEED);
                writer.writeBytes(LINE_FEED);
                writer.write(Files.readAllBytes(fileList[i].toPath()));
                writer.writeBytes(LINE_FEED);
            }
           
            writer.writeBytes("--" + boundary + "--" + LINE_FEED);
            writer.flush();
            writer.close();

            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void deleteOneFile(String folderId, String fileId, boolean includeThumbnail){
        try {
            System.out.println("단일 삭제(동기) API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/images/sync?";	// ?��?�� ?��?�� URL + appKey
            //String query = "folderId=" + folderId + "&";
            String query = "fileId=" + fileId + "&";
            query += "includeThumbnail=" + includeThumbnail;
            System.out.println(apiURL+query);

            URL url = new URL(apiURL+query);											// URL 초기?��
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP ?���?
            																	// Header ?��?��
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setDoOutput(true);
           
            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void deleteMultiFile(String folderId, String fileId, boolean includeThumbnail){
        try {
            System.out.println("다중 삭제(비동기) API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/images/async?";	// ?��?�� ?��?�� URL + appKey
            String query = "folderIds=" + folderId + "&";
            query += "files=" + fileId + "&";
            query += "includeThumbnail=" + includeThumbnail;
            System.out.println(apiURL+query);

            URL url = new URL(apiURL+query);											// URL 초기?��
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP ?���?
            																	// Header ?��?��
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setDoOutput(true);
           
            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void putOperation(String operationId, String description, boolean realtimeService, boolean deleteThumbnail){
        try {
            System.out.println("Image Operation 생성 및 수정 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/operations/";	// ?��?�� ?��?�� URL + appKey
            String query = operationId;
            URL url = new URL(apiURL+query);											// URL 초기?��
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP ?���?
            																	// Header ?��?��
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONObject option = new JSONObject();								
            option.put("width", 100);
            option.put("height", 100);
            option.put("quality", 75);
            option.put("upDownSizeType", "downOnly");
            option.put("keepAnimation", true);
            option.put("keepExi", true);
            option.put("autoOrient", false);
            option.put("targetExtension", null);
            
            JSONObject operation = new JSONObject();
            operation.put("templateOperationId", "resize_max_fit");
            operation.put("option", option);
            
            JSONArray data = new JSONArray();
            data.add(operation);
            
            JSONObject jsonData = new JSONObject();								// JSONObject ?��?�� -?���? ?��?��브러�? ?��?��
            jsonData.put("description", description);
            jsonData.put("realtimeService", realtimeService);
            jsonData.put("deleteThumbnail", deleteThumbnail);
            jsonData.put("data", data);
            
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(jsonData.toString().getBytes("UTF-8"));
            wr.flush();
            wr.close();
           
            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void deleteOperation(String operationId, boolean deleteThumbnail){
        try {
            System.out.println("Image Operation 삭제 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/operations/";	// ?��?�� ?��?�� URL + appKey
            String query = operationId;
            System.out.println(apiURL+query);

            URL url = new URL(apiURL+query);											// URL 초기?��
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP ?���?
            																	// Header ?��?��
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            JSONObject jsonData = new JSONObject();								// JSONObject ?��?�� -?���? ?��?��브러�? ?��?��
            jsonData.put("deleteThumbnail", deleteThumbnail);
            
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(jsonData.toString().getBytes("UTF-8"));
            wr.flush();
            wr.close();
           
            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void executeOperation(String basepath, String[] filePathList, String[] operationIdList, String callbackUrl){
        try {
            System.out.println("Image Operation 실행(비동기) API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/operations-exec";	// ?��?�� ?��?�� URL + appKey
            
            URL url = new URL(apiURL);											// URL 초기?��
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP ?���?
            																	// Header ?��?��
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
            
            JSONObject jsonData = new JSONObject();								// JSONObject ?��?�� -?���? ?��?��브러�? ?��?��
            jsonData.put("basepath", basepath);
            jsonData.put("filepaths", filepaths);
            jsonData.put("operationIds", operationIds);
            jsonData.put("callbackUrl", callbackUrl);
            
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(jsonData.toString().getBytes("UTF-8"));
            wr.flush();
            wr.close();
           
            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void getOperation(){
        try {
            System.out.println("Image Operation 목록 조회 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/operations/";	// ?��?�� ?��?�� URL + appKey
    
            System.out.println(apiURL);

            URL url = new URL(apiURL);											// URL 초기?��
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP ?���?
            																	// Header ?��?��
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setDoOutput(true);
           
            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getDetailOperation(String operationId){
        try {
            System.out.println("Image Operation 상세 조회 API");
        	
        	String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/operations/";	// ?��?�� ?��?�� URL + appKey
            String query = operationId;
            System.out.println(apiURL+query);

            URL url = new URL(apiURL+query);											// URL 초기?��
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP ?���?
            																	// Header ?��?��
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setDoOutput(true);
           
            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void getLiveService(){
        try {
            System.out.println("실시간 서비스 조회 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/users";	// ?��?�� ?��?�� URL + appKey
    
            System.out.println(apiURL);

            URL url = new URL(apiURL);											// URL 초기?��
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP ?���?
            																	// Header ?��?��
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setDoOutput(true);
           
            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void putLiveService(boolean realtimeService) {
    	try {
            System.out.println("실시간 서비스 변경 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/users";	// ?��?�� ?��?�� URL + appKey

            URL url = new URL(apiURL);											// URL 초기?��
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP ?���?
            																	// Header ?��?��
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
           
            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void getQueue(String queueId){
        try {
            System.out.println("작업 조회 API");
        	
            String apiURL = "https://api-image.cloud.toast.com/image/v2.0/appkeys/" + appKey + "/queues/";	// ?��?�� ?��?�� URL + appKey
            String query = queueId;
            System.out.println(apiURL+query);

            URL url = new URL(apiURL+query);											// URL 초기?��
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();	// HTTP ?���?
            																	// Header ?��?��
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", secretKey);
            conn.setDoOutput(true);
           
            int responseCode = conn.getResponseCode();							// ?��?�� 코드 
            BufferedReader br;
            if(responseCode==200) { // ?��?�� ?���?
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  				// ?��?�� 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());							// response 출력
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

package totalSampleCode;

public class MainClass {
    public static void main(String[] args) {
        String appKey = "**********";
        String secretKey = "*******" ;
        
        ImageAPI imageAPI = new ImageAPI(appKey, secretKey);
        
        String basePath = "/jinho";
        String createdBy = "U";
        String name = "sample.png";
        int page = 1;
        int rows = 100;
        String sort = "name:asc";
        
        String[] rootPathList = {"C:\\Users\\NHNEnt\\Desktop\\workspaces\\java-sample\\image-guide\\public\\sample.jpg", "C:\\Users\\NHNEnt\\Desktop\\workspaces\\java-sample\\image-guide\\public\\sample.png"};
        boolean overWrite = false;
        boolean autorename = true;
        
        String deleteFolderId = "880ddbb6-5454-47e3-9353-bd038ee4c9e9";
        String deleteFileId = "714794e1-7291-4b07-b102-0f3628e62c71";
        
        String deleteFolderId2 = "21a697cb-875c-4920-ab8e-f14a35f675bc";
        String deleteFileId2 = "9279f246-ba88-4d59-b82f-f0b2502311d7";
        boolean deleteIncludeThumbnail = true;
        
        String operationId = "100x100";
        String description = "100x100 썸네일 만들어주기";
        
        boolean realtimeService = false;
        boolean deleteThumbnail = true;
        
        String[] imagePathList = {"/jinho/sample.png", "/jinho/sample.jpg"};
        String[] operationIdList = {"ddd", "100x100"};
        
        String queueId = "e340949c-e7a8-416a-b9ec-cb99ab0625be";

        imageAPI.postFolder(basePath);
        System.out.println();
        
        imageAPI.getFile(basePath, createdBy, name, page, rows, sort);
        System.out.println();
        
        imageAPI.getFolder(basePath);
        System.out.println();
        
        imageAPI.uploadOneFile(basePath + "/sample.jpg", rootPathList[0], overWrite, autorename,  operationIdList);
        System.out.println();
        
        imageAPI.uploadMultiFile(basePath, overWrite, autorename, operationIdList, "", rootPathList);
        System.out.println();
        
        imageAPI.deleteOneFile(deleteFolderId, deleteFileId, deleteIncludeThumbnail);
        System.out.println();
        
        imageAPI.deleteMultiFile(deleteFolderId, deleteFileId, deleteIncludeThumbnail);
        System.out.println();
        
        imageAPI.putOperation(operationId, description, realtimeService, deleteThumbnail);
        System.out.println();
        
        imageAPI.getOperation();
        System.out.println();
        
        imageAPI.getDetailOperation(operationId);
        System.out.println();
                
        imageAPI.executeOperation(basePath, imagePathList, operationIdList, "");
        System.out.println();
        
        imageAPI.deleteOperation(operationId, deleteThumbnail);
        System.out.println();
        
        imageAPI.getLiveService();
        System.out.println();
        
        imageAPI.putLiveService(realtimeService);
        System.out.println();
        
        imageAPI.getQueue(queueId);
        System.out.println();
       
        
    }
}
package kibana_ingestion;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class ThreadLauncher {

    private static Integer threadsToLaunch = 80;
    private static Integer lengthOfBatch = 10000;
    public static JSONArray readJSONData() throws Exception{
        JSONParser parser = new JSONParser();
        JSONArray testData = (JSONArray) parser.parse(new FileReader("testData.json"));
        return testData;
    }

    public static void main(String[] args) throws Exception {

        IngestionService.batchRecordsLen = lengthOfBatch;
        JSONArray testData = readJSONData();
        IngestionService.myTestData = testData.toArray();

        IngestionService.entireRecordsLen = testData.toArray().length;

//        System.out.println("Main thread:"+IngestionService.entireRecordsLen);
        IngestionService.totalThreads = threadsToLaunch;

        Thread t[] = new Thread[IngestionService.totalThreads];

        for(int i=0;i<IngestionService.totalThreads;i++){

            t[i] = new Thread(new IngestionService());
            t[i].start();
        }
    }
}

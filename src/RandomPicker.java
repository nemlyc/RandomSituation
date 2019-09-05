import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomPicker {
    public static List<SituationData> situationDataList;

    public static void main(String[] args) {
        RandomPicker picker = new RandomPicker();
        situationDataList = new ArrayList<>();

        //JSONファイルをよみこんで、リストに登録。
        File jsonFile = new File("./Data.json5");
        picker.loadJsonFile(jsonFile);

        picker.getContentsAll(situationDataList);

    }

    /**
     * Jsonファイルを渡して読み込み、シチュエーションリストに格納する。
     * @param jsonFile
     */
    public void loadJsonFile(File jsonFile){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            SituationData[] data = new Gson().fromJson(reader, SituationData[].class);
            situationDataList.addAll(Arrays.asList(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Jsonに含まれているコンテンツをすべて表示するメソッド
     * @param list
     */
    private void getContentsAll(List<SituationData> list){
        for (SituationData data : list){
            System.out.println(data.getWho()+ "は" + data.getWhere()+ "で" +data.getDoSomething());
        }
    }

}

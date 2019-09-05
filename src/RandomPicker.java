import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RandomPicker {
    public static List situationDataList;

    public static void main(String[] args) {
        Gson gson = new Gson();
        SituationData data = new SituationData();
        situationDataList = new ArrayList<SituationData>();

        //stringで一行で読み込んで、それをデシリアライズをかけて使う感じかなぁ。
        //fromJson(一行, 扱いたいクラス)

        File jsonFile = new File("./Data.json5");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            String line;
            StringBuilder serializedJson = new StringBuilder();
            while ((line = reader.readLine()) != null){
                serializedJson.append(line);
            }
            System.out.println(serializedJson);

            data = gson.fromJson(serializedJson.toString(), SituationData.class);
            System.out.println("who : " + data.getWho() + "where : " + data.getWhere() + "doSomething : " + data.getDoSomething());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

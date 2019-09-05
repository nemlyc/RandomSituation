import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomPicker {
    public static List<SituationData> situationDataList;

    public static void main(String[] args) {
        RandomPicker picker = new RandomPicker();
        situationDataList = new ArrayList<>();

        //JSONファイルをよみこんで、リストに登録。
        File jsonFile = new File("./Data.json5");
        picker.loadJsonFile(jsonFile);

        while (true) {
            try {
                System.out.println("コマンドを選択してください。\n0.RandomPickUp 1.RegisterWordSet 2.DeleteWordSet \"q\"で修了");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String command = reader.readLine();
                switch (command){
                    case "0":
                    case "RandomPickUp":
                        System.out.println(picker.randomPickUp(situationDataList));
                        break;

                    case"1":
                    case "RegisterWordSet":
                        break;

                    case"2":
                    case"DeleteWordSet":
                        break;
                }






                if (command.equals("q")){
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Jsonファイルを渡して読み込み、シチュエーションリストに格納する。
     *
     * @param jsonFile
     */
    public void loadJsonFile(File jsonFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            SituationData[] data = new Gson().fromJson(reader, SituationData[].class);
            situationDataList.addAll(Arrays.asList(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * リストからwho, where, doSomethingを一つ一つ抽選し、組み合わせて返す。
     * @param list
     * @return リストからランダムに抽出した単語を組み合わせた文章。
     */
    public String randomPickUp(List<SituationData> list){
        //nullが入っていたら除外してもう一度抽選
        int selectedNum;
        Random random = new Random();
        String who = null;
        String where = null;
        String doSomething = null;

        while (true){
            selectedNum = random.nextInt(list.size());
            if (who == null){
                who = list.get(selectedNum).getWho();
                continue;
            }
            if (where == null){
                where = list.get(selectedNum).getWhere();
                continue;
            }
            if (doSomething == null){
                doSomething = list.get(selectedNum).getDoSomething();
                continue;
            }
            if (who != null && where != null && doSomething != null){
                break;
            }
        }
        String res = who + "は " + where + "で " + doSomething;

        return res;
    }



    /**
     * Jsonに含まれているコンテンツをすべて表示する。
     *
     * @param list
     */
    private void getContentsAll(List<SituationData> list) {
        for (SituationData data : list) {
            System.out.println(data.getWho() + "は" + data.getWhere() + "で" + data.getDoSomething());
        }
    }

}

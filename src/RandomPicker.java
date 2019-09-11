import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class RandomPicker {
    public static List<SituationData> situationDataList;

    public static void main(String[] args) {
        RandomPicker picker = new RandomPicker();
        situationDataList = new ArrayList<>();

        //JSONファイルをよみこんで、リストに登録。
        File jsonFile = new File("./Data.json5");

        while (true) {
            try {
                picker.loadJsonFile(jsonFile);
                System.out.println("コマンドを選択。\n0.RandomPickUp 1.RegisterWordSet 2.DeleteWordSet \"q\"で修了");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String command = reader.readLine();
                switch (command) {
                    case "0":
                    case "RandomPickUp":
                        System.out.println(picker.randomPickUp(situationDataList));
                        break;

                    case "1":
                    case "RegisterWordSet":
                        picker.registerCommand();
                        break;

                    case "2":
                    case "DeleteWordSet":
                        break;
                }


                if (command.equals("q")) {
                    break;
                }
                if (command.equals("show")) {
                    picker.getContentsAll(situationDataList);
                }
                if (command.equals("license")) {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerCommand() {
        try {
            //ワードを格納するリスト
            List<String> wordSet = new LinkedList<>();

            while (wordSet.size() < 3) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input;
                //人名を入れる。
                //ロケーションを入れる。
                //動作を入れる。
                switch (wordSet.size()) {
                    case 0:
                        System.out.println("「誰が」を入力" + "\n" + "> ");
                        input = reader.readLine();
                        wordSet.add(input);
                        break;
                    case 1:
                        System.out.println("「どこで」を入力" + "\n" + "> ");
                        input = reader.readLine();
                        wordSet.add(input);
                        break;
                    case 2:
                        System.out.println("「何をする」を入力" + "\n" + "> ");
                        input = reader.readLine();
                        wordSet.add(input);
                        break;
                }
            }
            registerWordSet(wordSet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void registerWordSet(List wordSet) {
        SituationData data = new SituationData();
        Gson gson = new Gson();

        data.setWho(wordSet.get(0).toString());
        data.setWhere(wordSet.get(1).toString());
        data.setDoSomething(wordSet.get(2).toString());

        situationDataList.add(data);

        try {
            FileWriter file = new FileWriter("./Data.json5");
            PrintWriter writer = new PrintWriter(new BufferedWriter(file));

            writer.print(gson.toJson(situationDataList));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Jsonファイルを渡して読み込み、シチュエーションリストに格納する。
     *
     * @param jsonFile
     */
    public void loadJsonFile(File jsonFile) {
        //リストをクリア
        situationDataList.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            SituationData[] data = new Gson().fromJson(reader, SituationData[].class);
            //situationDataListへ、一つずつdataを代入。
            situationDataList.addAll(Arrays.asList(data).subList(0, data.length));
            System.out.println("シチュリストのサイズ : "+situationDataList.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * リストからwho, where, doSomethingを一つ一つ抽選し、組み合わせて返す。
     *
     * @param list
     * @return リストからランダムに抽出した単語を組み合わせた文章。
     */
    public String randomPickUp(List<SituationData> list) {
        //nullが入っていたら除外してもう一度抽選
        int selectedNum;
        Random random = new Random();
        String who = null;
        String where = null;
        String doSomething = null;

        while (true) {
            selectedNum = random.nextInt(list.size());
            if (who == null) {
                who = list.get(selectedNum).getWho();
                continue;
            }
            if (where == null) {
                where = list.get(selectedNum).getWhere();
                continue;
            }
            if (doSomething == null) {
                doSomething = list.get(selectedNum).getDoSomething();
                continue;
            }
            if (who != null && where != null && doSomething != null) {
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
        for (int i = 0; i < list.size(); i++) {
            SituationData data = list.get(i);
            System.out.println("・" + data.getWho() + "は" + data.getWhere() + "で" + data.getDoSomething());
        }
    }

}

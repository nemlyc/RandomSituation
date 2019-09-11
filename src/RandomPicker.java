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
                System.out.println("\nコマンドを選択。\n0.RandomPickUp 1.RegisterWordSet 2.DeleteWordSet \"q\"で終了");
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
                        picker.deleteCommand();
                        break;
                }


                if (command.equals("q")) {
                    break;
                }
                if (command.equals("show")) {
                    picker.getContentsAll();
                }
                if (command.equals("license")) {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ワードセットを登録するための情報を入力する。
     */
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
                        System.out.println("「誰が」を入力");
                        System.out.print("> ");
                        input = reader.readLine();
                        wordSet.add(input);
                        break;
                    case 1:
                        System.out.println("「どこで」を入力");
                        System.out.print("> ");
                        input = reader.readLine();
                        wordSet.add(input);
                        break;
                    case 2:
                        System.out.println("「何をする」を入力");
                        System.out.print("> ");
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

    /**
     * リストを受け取ってSituationDataオブジェクトを作る。
     * situationDataListに追加し、それをファイルに書き込む。
     * @param wordSet who, where, doSomethingの3要素を持ったリスト
     */
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
     * ワードセットを削除するための情報を入力する。
     */
    public void deleteCommand(){
        getContentsAll();
        System.out.println("削除する番号を入力。");
        System.out.print("> ");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String num_string = reader.readLine();
            int num = Integer.parseInt(num_string);
            deleteWordSet(num);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 該当するワードセットを削除し、最新のリストをファイルに書き込む。
     * @param num 削除したいワードセットのリスト上の位置。
     */
    private void deleteWordSet(int num){
        Gson gson = new Gson();
        SituationData print = situationDataList.get(num);
        situationDataList.remove(num);

        try {
            FileWriter file = new FileWriter("./Data.json5");
            PrintWriter writer = new PrintWriter(new BufferedWriter(file));

            writer.print(gson.toJson(situationDataList));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("削除しました。> " + print.getWho() + "は " + print.getWhere() + "で " + print.getDoSomething());
    }


    /**
     * Jsonファイルを渡して読み込み、シチュエーションリストに格納する。
     *
     * @param jsonFile 読み込むJsonファイル
     */
    public void loadJsonFile(File jsonFile) {
        //リストをクリア
        situationDataList.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            SituationData[] data = new Gson().fromJson(reader, SituationData[].class);
            //situationDataListへ、一つずつdataを代入。
            situationDataList.addAll(Arrays.asList(data).subList(0, data.length));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * リストからwho, where, doSomethingを一つ一つ抽選し、組み合わせて返す。
     *
     * @param list situationList
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
     */
    private void getContentsAll() {
        for (int i = 0; i < situationDataList.size(); i++) {
            SituationData data = situationDataList.get(i);
            System.out.println("[" + i + "]" + data.getWho() + "は " + data.getWhere() + "で " + data.getDoSomething());
        }
    }
}

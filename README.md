# RandomSituation

## Overview
事前に登録したワードセットの中から、ランダムにワードを抽出して出力します。
創作のストーリー作りに役立てることができます。
Extract randomly from the prepared situation. Useful for story creation.

## Description
用意されたJsonファイルで記述されたワードセット（誰が・どこで・何をした。）をリストに登録します。
一つの項目ごとにランダムにワードを選択して、3つの単語を一つの文にして出力します。
いくつかのコマンドが存在します。
例えば、ランダムに選択して出力するものから、ワードセットの追加・削除、ワードセットの一覧表示等があります。

## Usage
1. Java 8以上の実行環境を用意する。
2. releaseからRandomPicker.jarとData.json5が含まれたフォルダをDLする。
3. コマンドラインを開き、jarファイルが含まれているディレクトリまで行く。
4. コマンドラインから、`java -jar RandomSituation.jar`を実行する。(run.batでも可能)
5. 実行すると、三つのコマンドが表示される。ユーザーは、番号ないし名前を入力する。
    + 0.RandomPickUp 
        + Data.json5に登録されているワードセット（誰が・どこで・何をした。の三要素）から一つずつランダムに選び、表示する。
    + 1.RegisterWordSet 
        + Dataファイルにワードセットを新規登録する。
        + 三要素を一つずつ順に入力していく。
    + 2.DeleteWordSet
        + 番号を指定して、登録されているワードセットを削除する。

+ 待機画面で実行できるコマンドは他に二つあり、"show"コマンドと"q"コマンドがある。
    + show
        + Dataファイルに登録されているワードセットをすべて一覧で表示する。
    + q
        + アプリを終了するためのコマンド。





## Development
### Requirement
 + JDK 1.8以上
 + [Gson](https://github.com/google/gson) 2.8.5（開発時）
 （内部でGsonを使用しています。）

### License
RandomSituation is released under the MIT License.

This software includes the work that is distributed in the Apache License 2.0


## Author
Motoki
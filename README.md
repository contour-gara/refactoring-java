# refactoring-java

## 自動販売機 (vending-machine)

### 概要

`vending-machine` プロジェクトのコードをリファクタリングしてください。</br>
受け入れテストをパスすることが条件です。

### ブランチ

`vending-machine/2024-05-17-[TEAM_NAME]` ブランチを切り、push してください。</br>
PR は不要です。</br>
push 時に GitHub Actions でテストが実行されるので、パスを確認してください。

### ローカルでの受け入れテスト実行方法
`vending-machine-acceptance-test` が受け入れテストのプロジェクトです。</br>
`vending-machine` プロジェクトに依存するため、ローカルのリポジトリに jar ファイルが必要です。</br>
プロジェクトルートにいる状態で次のコマンドを入力し、ローカルで受け入れテストを実行します。

```shell
mvn clean install -Dmaven.test.skip=true
mvn clean test -f vending-machine-acceptance-test/pom.xml

# ローカルリポジトリを掃除したい場合は以下コマンドも実行
rm -r ~/.m2/repository/org/contourgara
```

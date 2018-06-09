// Activityに設定しているViewの内適当に一つViewを取得
View view = findViewById(R.id.button1);

// importができないのでクラス名はフルで書く
android.support.design.widget.Snackbar snackbar;

// インスタンスを取得
// 第一引数はActivityに設定しているViewなら何でもいい.
// SnackbarはViewインスタンスから勝手にView階層をさかのぼってくれるので
snackbar = android.support.design.widget.Snackbar.make(
  view, "Snackbarの表示", android.support.design.widget.Snackbar.LENGTH_SHORT);

// 表示する
snackbar.show();

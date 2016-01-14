##MUSIC PLAYER
Javaで動く音楽プレイヤー  
プレイリストを書いて同時再生や羅列されているものから1曲選ぶことができる  
現在はmp3のみ再生する

###暫定的名なプレイリストの書き方
同時に再生したい曲はカンマ区切りで同じ行に書く  
1曲ランダムで再生したい時は同時再生と同じように記述した後、先頭に s, を付ける

> foo.mp3
> bar.mp3
> foo.mp3,bar.mp3
> s,foo.mp3,bar.mp3

### 使ったAPI
BasicPlayer API V3.0  
> BasicPlayer API: http://www.javazoom.net/jlgui/api.html  
  

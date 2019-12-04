### use one
```js
 layer.open({
                    content: '您是否要退出登录？',
                    btn:['确定','点错了'],
                    yes:function (index) {
                        layer.close(index);
                        login_out()

                    }
                })
```
### use two
```js
      layer.open({type:2,content:'退出中...'})
      layer.open({skin:'msg',content:'登录中'})
```
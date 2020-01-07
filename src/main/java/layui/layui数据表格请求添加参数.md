### Layui数据表格基本形态，[官网链接地址](https://www.layui.com/doc/modules/table.html)

```html
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>table模块快速使用</title>
        <link rel="stylesheet" href="/layui/css/layui.css" media="all">
    </head>
    <body>

        <table id="demo" lay-filter="test"></table>

        <script src="/layui/layui.js"></script>
        <script>
            layui.use('table', function(){
                var table = layui.table;

                //第一个实例
                table.render({
                    elem: '#demo'
                    ,height: 312
                    ,url: '/demo/table/user/' //数据接口
                    ,page: true //开启分页
                    ,cols: [
                        [ //表头
                        {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left'}
                        ,{field: 'username', title: '用户名', width:80}
                        ,{field: 'sex', title: '性别', width:80, sort: true}
                        ,{field: 'city', title: '城市', width:80} 
                        ,{field: 'sign', title: '签名', width: 177}
                        ,{field: 'experience', title: '积分', width: 80, sort: true}
                        ,{field: 'score', title: '评分', width: 80, sort: true}
                        ,{field: 'classify', title: '职业', width: 80}
                        ,{field: 'wealth', title: '财富', width: 135, sort: true}
                    	]
                    ]
                });
            });
        </script>
    </body>
</html>
```

- 虽然是官网提供，但是也要注意与其他框架结合使用时，还是需要注意格式，比如，与[thymeleaf](https://github.com/thymeleaf/thymeleaf)一起使用时，注意不要将两个`[`连用，使用时换行，比如，`[[...]]`可能会出现错误。

#### 第一次请求时加请求参数

- 默认会自动传递两个参数：*?page=1&limit=30*（该参数可通过 request 自定义），*page* 代表当前页码、*limit* 代表每页数据量
- 如果请求需要传递其他参数，可以在数据表格中添加*where*属性，在*where*中填写参数名和参数值即可
- 我们在*where*中自定义的参数以及默认的page、limit参数都会一直携带，比如重载表格时会添加其他的参数，但是之前的参数也会携带

```javascript
//“方法级渲染”配置方式
table.render({ //其它参数在此省略
  url: '/api/data/'
  ,where: {token: 'sasasas', id: 123} //如果无需传递额外参数，可不加该参数
  ,method: 'post' //如果无需自定义HTTP类型，可不加该参数
  ,request: {} //如果无需自定义请求参数，可不加该参数
  ,response: {} //如果无需自定义数据响应名称，可不加该参数
}); 
```

#### 重载表格时加请求参数

```js
//所获得的 tableIns 即为当前容器的实例
var tableIns = table.render({
  elem: '#id'
  ,cols: [] //设置表头
  ,url: '/api/data' //设置异步接口
  ,id: 'idTest'
}); 
 
//这里以搜索为例
tableIns.reload({
  where: { //设定异步数据接口的额外参数，任意设
    aaaaaa: 'xxx'
    ,bbb: 'yyy'
    //…
  }
  ,page: {
    curr: 1 //重新从第 1 页开始
  }
});
//上述方法等价于
table.reload('idTest', {
  where: { //设定异步数据接口的额外参数，任意设
    aaaaaa: 'xxx'
    ,bbb: 'yyy'
    //…
  }
  ,page: {
    curr: 1 //重新从第 1 页开始
  }
}); //只重载数据
```

- 不需要加额外请求参数可以直接调用无参方法进行重载（上述第一种方式）：`tableIns.reload()`
- 注意：上述代码中每次都会带着`page: {curr: 1}`，其实就是因为重载时依然会携带表格加载时的请求参数。例如，在第2页时，带条件（参数）重载，如果不设置从第1页开始，就会带着参数`page=2`去请求服务器。


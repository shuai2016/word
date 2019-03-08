RestfulCRUD：CRUD满足Rest风格；

URI：/资源名称/资源标识		HTTP请求方式区分对资源CRUD操作

|                                      | 请求URI  | 请求方式 |
| ------------------------------------ | -------- | -------- |
| 查询所有员工                         | emps     | GET      |
| 查询某个员工（来到修改页面前）       | emp/{id} | GET      |
| 来到添加页面                         | emp      | GET      |
| 添加页面                             | emp      | POST     |
| 来到修改页面（查出员工进行信息回显） | emp/{id} | GET      |
| 修改员工                             | emp      | PUT      |
| 删除员工                             | emp/{id} | DELETE   |

- 超链接get方式


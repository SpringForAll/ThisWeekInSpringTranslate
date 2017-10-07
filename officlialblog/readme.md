1. 增加定时任务;
2. 增加发送springio周报到邮箱

如果需要添加收件人,只要在

```
application.yml : 

     email.receiver后面加上收件人就可以了,注意用逗号隔开,前后不要留逗号.
```    

访问接口:

* 如果不需要邮件,直接访问: `curl -XGET http://localhost:8080/translate`
* 如果需要发送邮件(邮件提前配置),访问接口: `curl -XGET http://localhost:8080/mail`

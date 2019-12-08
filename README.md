# 点餐系统

PS:因为项目不算大，所以把功能图和系统架构图都放在README.md上面了

1.用户端

  1. 菜单列表：菜名、图片、价格
  2. 菜的分类
  3. 点击菜，进入菜的详情信息
  4. 计算菜的总价格
  5. 确定点餐页面后，直接订单完成
  6. 加一个历史订单

2.餐厅端

  1. 订单列表的管理，调度
  2. 自定义菜单，加一个大约准备时间
  3. 增删查改
  4. 今日收入

时间安排：

1. 先做用户端，如果有时间再做餐厅端。 
2. 后台、UI设计先做。UI设计做完，安卓端再开始做。

系统架构图：

  1. 安卓端用OKHttpClient，SQLite等等框架
  2. 后台部分使用servlet框架
  3. 数据库使用jdbc
  4. 服务器部署使用tomcat
  5. 原型设计使用“墨刀”
  
分工：
  1.  [啊哈](https://github.com/AhaYujie) 安卓
  2.  [黑沙](https://github.com/B1ackSand)
  
      [220284](https://github.com/ye220284) 
      
      [西楼](https://github.com/xilou31) 后台
  3.  [黑沙](https://github.com/B1ackSand) 兼职原型设计


开发环境：
1. IDE : IntelliJ IDEA 2019.2.3 Ultimate （ https://www.jetbrains.com/idea/download/#section=windows ）
2. Tomcat 8.5.47 （ https://tomcat.apache.org/download-80.cgi ）
3. JDBC 8.0.16 （jar包可找“开发环境”文件夹）
4. Navicat for MySQL ( https://www.navicat.com.cn/products )

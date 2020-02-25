一、简单题

#1、Mybatis动态sql是做什么的？都有哪些动态sql？简述一下动态sql的执行原理？
  答:(1)作用：Mybatis动态 SQL ，可以让我们在 XML映射文件内，以 XML标签的形 式编写动态 SQL，完成逻辑判断和动态拼接 SQL 的功能。 
   (2)动态SQL 语句主要有以下几类:
	　　1. if 语句 (简单的条件判断)
	　　2. choose (when,otherwize) ,相当于java 语言中的 switch ,与 jstl 中的choose 很类似.
	　　3. trim (对包含的内容加上 prefix,或者 suffix 等，前缀，后缀)
	　　4. where (主要是用来简化sql语句中where条件判断的，能智能的处理 and or ,不必担心多余导致语法错误)
	　　5. set (主要用于更新时)
	　　6. foreach (在实现 mybatis in 语句查询时特别有用)
   (3)原理：使用OGNL的表达式，从SQL参数对象中计算表达式的值,根据表达式的值动态拼接 SQL,以此来完成动态 SQL的功能。
   
#2、Mybatis是否支持延迟加载？如果支持，它的实现原理是什么？
 答:(1)Mybatis仅支持association关联对象和collection关联集合对象的延迟加载，association指的就是一对一，collection指的就是一对多查询。在Mybatis配置文件中，可以配置是否启用延迟加载lazyLoadingEnabled=true|false。
   (2)原理:使用CGLIB创建目标对象的代理对象，当调用目标方法时，进入拦截器方法，比如调用a.getB().getName()，拦截器invoke()方法发现a.getB()是null值，那么就会单独发送事先保存好的查询关联B对象的sql，把B查询上来，然后调用a.setB(b)，于是a的对象b属性就有值了，接着完成a.getB().getName()方法的调用。这就是延迟加载的基本原理。
   
#3、Mybatis都有哪些Executor执行器？它们之间的区别是什么？
 答：(1)执行器有：SimpleExecutor、ReuseExecutor、BatchExecutor。
   (2)区别：
   		SimpleExecutor:每执行一次update和select，就开启一个Statement对象，用完立刻关闭Statement对象。
   		ReuseExecutor：执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，用完后，不关闭Statement对象，而是放置于Map内，供下一次使用。简言之，就是重复使用Statement对象。
   	    BatchExecutor:执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理。与JDBC批处理相同。
        作用范围：Executor的这些特点，都严格限制在SqlSession生命周期范围内。
        
#4、简述下Mybatis的一级、二级缓存（分别从存储结构、范围、失效场景。三个方面来作答）？
 答:
 	(1)一级缓存：一级缓存是SqlSession级别的缓存。在操作数据库时需要构造SqlSession对象，在对象中有一个数据结构(HashMap)用于存储缓存数据。不同的SqlSession之间的缓存数据区域(HashMap)是互相不影响的。
 	失效场景：
 		没有使用当前一级缓存的情况，如果没有使用，效果就是第二次查询相同的语句还需向数据库发送sql
    (2)二级缓存:二级缓存是mapper级别的缓存，多个SqlSession去操作同一个Mapper的sql语句，多个SqlSession可以共用二级缓存，二级缓存是跨SqlSession的。
    在mapper的同一个namespace中，如果有insert、update、delete操作数据库后需要刷新缓存，如果不执行刷新缓存会出现脏读。
           失效场景：
                      第一次执行查询，发出sql语句，并将查询的结果放入缓存中-》中间进行了更新操作，进行提交。-》第二次查询，由于上次更新操作，缓存数据已经清空
，所以这里必须重新去发起sql查询
    
#5、简述Mybatis的插件运行原理，以及如何编写一个插件？
 答：
 	#运行原理：mybatis可以编写针对Executor、StatementHandler、ParameterHandler、ResultSetHandler四个接口的插件，mybatis使用JDK的动态代理为需要拦截的接口生成代理对象，然后实现接口的拦截方法，所以当执行需要拦截的接口方法时，会进入拦截方法
 	#如何编写一个插件：
 	(1)实现Mybatis的Interceptor接口并复写intercept()方法
 	(2)给插件编写注解，指定要拦截哪一个接口的哪些方法
 	(3)在配置文件中配置你编写的插件

二、编程题

请完善自定义持久层框架IPersistence，在现有代码基础上添加、修改及删除功 能。【需要采用getMapper方式】

IPersistence项目地址： https://github.com/xushuanglu/IPersistence
IPersistence_test项目地址： https://github.com/xushuanglu/IPersistence_test
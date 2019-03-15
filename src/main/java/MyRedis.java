import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * MyRedis
 *
 * @author shuai
 * @date 2019/3/15
 */
public class MyRedis {
	public static void main(String[] args) {
		Jedis jedis = getJedisConnection();
		//testString(jedis);
		//testMap(jedis);
		//testList(jedis);
		//testSet(jedis);
		test(jedis);

	}

	public static Jedis getJedisConnection() {
		String redisHost = "127.0.0.1";
		// redis的端口
		int redisPort = 6379;
		// redis超时时间
		int redisTimeout = 2000;
		// redis的db
		int redisDatabase = 0;
		Jedis jedis = null;
		try {
			jedis = new Jedis(redisHost, redisPort, redisTimeout);
		} catch (Exception e) {
			System.out.println("连接redis报错！！");
		}
		if (redisDatabase != 0) {
			try {
				jedis.select(redisDatabase);
			} catch (Exception e) {
				System.out.println("redisDatabase error");
			}
		}
		return jedis;
	}

	/**
	 * redis存储字符串
	 */
	public static void testString(Jedis jedis) {
		//-----添加数据----------
		//向key-->name中放入了value-->xinxin
		jedis.set("name", "xinxin");
		//执行结果：xinxin
		System.out.println(jedis.get("name"));
		//拼接
		jedis.append("name", " is my lover");
		System.out.println(jedis.get("name"));
		//删除某个键
		jedis.del("name");
		System.out.println(jedis.get("name"));
		//设置多个键值对
		jedis.mset("name", "liuling", "age", "23", "qq", "476777XXX");
		//进行加1操作
		jedis.incr("age");
		System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));

	}

	/**
	 * redis操作Map
	 */
	public static void testMap(Jedis jedis) {
		//-----添加数据----------
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xinxin");
		map.put("age", "22");
		map.put("qq", "123456");
		jedis.hmset("user", map);
		//取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
		//第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
		List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
		System.out.println(rsmap);

		//删除map中的某个键值
		jedis.hdel("user", "age");
		//因为删除了，所以返回的是null
		System.out.println(jedis.hmget("user", "age"));
		//返回key为user的键中存放的值的个数2
		System.out.println(jedis.hlen("user"));
		//是否存在key为user的记录 返回true
		System.out.println(jedis.exists("user"));
		//返回map对象中的所有key
		System.out.println(jedis.hkeys("user"));
		//返回map对象中的所有value
		System.out.println(jedis.hvals("user"));

		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
		}
	}

	/**
	 * jedis操作List
	 */

	public static void testList(Jedis jedis) {
		//开始前，先移除所有的内容
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		//先向key java framework中存放三条数据
		jedis.lpush("java framework", "spring");
		jedis.lpush("java framework", "struts");
		jedis.lpush("java framework", "hibernate");
		//再取出所有数据jedis.lrange是按范围取出，
		// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
		System.out.println(jedis.lrange("java framework", 0, -1));

		jedis.del("java framework");
		jedis.rpush("java framework", "spring");
		jedis.rpush("java framework", "struts");
		jedis.rpush("java framework", "hibernate");
		System.out.println(jedis.lrange("java framework", 0, -1));
	}

	/**
	 * jedis操作Set
	 */

	public static void testSet(Jedis jedis) {
		jedis.del("user");
		//添加
		jedis.sadd("user", "liuling");
		jedis.sadd("user", "xinxin");
		jedis.sadd("user", "ling");
		jedis.sadd("user", "zhangxinxin");
		jedis.sadd("user", "who");
		//移除noname
		jedis.srem("user", "who");
		//获取所有加入的value
		System.out.println(jedis.smembers("user"));
		//判断 who 是否是user集合的元素
		System.out.println(jedis.sismember("user", "who"));
		System.out.println(jedis.srandmember("user"));
		//返回集合的元素个数
		System.out.println(jedis.scard("user"));
	}


	public static void test(Jedis jedis) {
		//jedis 排序
		//注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
		//先清除数据，再加入数据进行测试
		jedis.del("a");
		jedis.rpush("a", "1");
		jedis.lpush("a", "6");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");
		// [9, 3, 6, 1]
		System.out.println(jedis.lrange("a", 0, -1));
		//[1, 3, 6, 9]  //输入排序后结果
		System.out.println(jedis.sort("a"));
		System.out.println(jedis.lrange("a", 0, -1));
	}
}

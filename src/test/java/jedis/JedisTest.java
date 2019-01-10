package jedis;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
/*		System.out.println("连接成功");

		System.out.println("服务正在运行: " + jedis.ping());

		jedis.sadd("myUser2", "name", "张三", "age", "李四");

		Map<String, String> map = new HashMap<>();
		map.put("age", "21");
		map.put("name", "张三");
		System.out.println(jedis.hmset("User2", map));
		*/
		
		System.out.println(jedis.keys("*"));
/*		
		System.out.println(jedis.smembers("myUser2"));
		
		System.out.println(jedis.lpush("userList", "夫理所当然","一氧化碳","震荡","震荡城镇"));
		
        System.out.println(jedis.lrange("userList", 0, -1));*/
		
		JedisPoolConfig config = new JedisPoolConfig();
	}

}


package jedis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class Luttes {
	public static void main(String[] args) {
		RedisClient redisClient = RedisClient.create("redis://@localhost:6379/0");
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisCommands<String, String> syncCommands = connection.sync();

		syncCommands.set("key", "Hello, Redis!");
		syncCommands.lpush("zs", "需要", "哈哈2", "魂牵梦萦 ");
		System.out.println(syncCommands.lrange("zs", 0, -1));
		connection.close();
		redisClient.shutdown();
	}
}


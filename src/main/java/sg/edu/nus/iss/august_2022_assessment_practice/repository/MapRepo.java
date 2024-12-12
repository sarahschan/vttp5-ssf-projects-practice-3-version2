package sg.edu.nus.iss.august_2022_assessment_practice.repository;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.august_2022_assessment_practice.constant.Constant;

@Repository
public class MapRepo {
    
    @Autowired
    @Qualifier(Constant.TEMPLATE02)
    RedisTemplate<String, String> redisTemplate;

    public void create(String articleID, String article) {
        redisTemplate.opsForHash().put(Constant.REDIS_KEY, articleID, article);
    }

    public Boolean articleExists(String articleID){
        return redisTemplate.opsForHash().hasKey(Constant.REDIS_KEY, articleID);
    }

    public Object get(String articleID) {
    // Object can be anything, a string, a number, a class, a collection
        return redisTemplate.opsForHash().get(Constant.REDIS_KEY, articleID);
    }


    public List<Object> getValues() {
        return redisTemplate.opsForHash().values(Constant.REDIS_KEY);
    }


    public long delete(String redisKey, String hashKey) {
        return redisTemplate.opsForHash().delete(redisKey, hashKey);
    }


    // <Object, Object> = <HashKeys, HashValues>
    public Map<Object, Object> getEntries(String redisKey){
        return redisTemplate.opsForHash().entries(redisKey);
    }

    public Set<Object> getKeys(String redisKey) {
        return redisTemplate.opsForHash().keys(redisKey);
    }

    public List<Object> getValues(String redisKey) {
        return redisTemplate.opsForHash().values(redisKey);
    }

    public Long size(String redisKey){
        return redisTemplate.opsForHash().size(redisKey);
    }

    public Boolean expire(String redisKey, Long expireValue){
        Duration expireDuration = Duration.ofSeconds(expireValue);
        return redisTemplate.expire(redisKey, expireDuration);
    }
    
}

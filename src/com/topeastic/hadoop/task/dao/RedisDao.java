package com.topeastic.hadoop.task.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;


/**
 *
 * @author sunxing
 */
public class RedisDao {
	private static Logger LOG = Logger.getLogger(RedisDao.class);

	private static final int REDIS_CONNECT_LATENCY = 120000;
	private JedisPool jedisPool = null;

	private String redisIp = null;
	private String redisPassw = null;
	private int redisPort;
	private int redisKeyKeepDay = 1;

	private RedisDao(String serversInfo, int database) {
		System.out.println("serversInfo:" + serversInfo +"; database:" + database);
		String[] splits = serversInfo.split(JRedisPoolConfig.STR_DELIMIT);
		if (splits.length < 2) {
			LOG.error("serversInfo error: " + serversInfo);
		}
		redisIp = splits[0];
		try {
			redisPort = Integer.parseInt(splits[1]);
		} catch (NumberFormatException e) {
		}

		if (StringUtils.isBlank(redisIp) || redisPort == 0) {
			LOG.error("error parsing redis, serversInfo is " + serversInfo);
			return;
		}

		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(JRedisPoolConfig.MAX_ACTIVE);
		config.setMaxIdle(JRedisPoolConfig.MAX_IDLE);
		config.setMaxWait(JRedisPoolConfig.MAX_WAIT);
		config.setTestOnBorrow(JRedisPoolConfig.TEST_ON_BORROW);
		config.setTestOnReturn(JRedisPoolConfig.TEST_ON_RETURN);
		if (splits.length == 3) {
			redisPassw = splits[2];
			LOG.info("password is " + redisPassw);
			jedisPool = new JedisPool(config, redisIp, redisPort,
					REDIS_CONNECT_LATENCY, redisPassw, database);
		} else {
			LOG.info("no password");
			jedisPool = new JedisPool(config, redisIp, redisPort,
					REDIS_CONNECT_LATENCY, null, database);
		}
		LOG.info("redisIp:"+redisIp);
		LOG.info("redisPort:"+redisPort);
		LOG.info("redisPassw:"+redisPassw);
	}
	

	private static Map<Integer, RedisDao> redisDaoMap = null;

	
	private static void initRedisDaoMap(String serversInfo){
		redisDaoMap = new HashMap<Integer, RedisDao>();
		redisDaoMap.put(0, new RedisDao(serversInfo, 0));
		redisDaoMap.put(1, new RedisDao(serversInfo, 1));
		redisDaoMap.put(2, new RedisDao(serversInfo, 2));
		redisDaoMap.put(3, new RedisDao(serversInfo, 3));
	}

	public static RedisDao getInstance(String serversInfo) {
		RedisDao redisDao = null;
		if (redisDaoMap == null) {
			initRedisDaoMap(serversInfo);
		}
		if (redisDaoMap != null) {
			redisDao = redisDaoMap.get(0);
		}
		return redisDao;
	}
	

	public static RedisDao getRedisInstance(String serversInfo) {
		RedisDao redisDao = null;
		if (redisDaoMap == null||redisDaoMap.isEmpty()) {
			initRedisDaoMap(serversInfo);
		}
		if (redisDaoMap != null) {
			redisDao = redisDaoMap.get(0);
		}
		redisDaoMap.clear();
		return redisDao;
	}
	
	/**
	
	 * @return
	 */
	public static RedisDao getInstance(String serversInfo, int database) {
		RedisDao redisDao = null;
		if (redisDaoMap == null) {
			initRedisDaoMap(serversInfo);
		}
		if (redisDaoMap != null) {
			redisDao = redisDaoMap.get(database);
		}
		return redisDao;
	}

	public JedisPool getPool() {
		return jedisPool;
	}
	
	public int getRedisKeyKeepDay() {
		return redisKeyKeepDay;
	}

	public void setRedisKeyKeepDay(int redisKeyKeepDay) {
		this.redisKeyKeepDay = redisKeyKeepDay;
	}
	
	

    public List<Integer> hgetPipeline(final String redisKey, final List<String> hashKeyList) {
        Jedis jedis = jedisPool.getResource();
        long sTime = System.currentTimeMillis();
        long eTime = 0;
        List<Integer> indexList = new ArrayList<Integer>();
        List<Response<Boolean>> resList = new ArrayList<Response<Boolean>>();
        try {
            Pipeline pipeline = jedis.pipelined();
            for (String hashKey : hashKeyList) {
            	Response<Boolean> flag = pipeline.hexists(redisKey, hashKey);
            	resList.add(flag);
			}
            pipeline.sync();
            
            for (int i = 0, size = hashKeyList.size(); i < size; i++) {
            	Response<Boolean> flag = resList.get(i);
            	if (flag.get()) {
            		indexList.add(0);
				} else {
					indexList.add(1);
				}
            }
            eTime = System.currentTimeMillis();
        } catch (Exception e) {
        	eTime = System.currentTimeMillis();
        	jedisPool.returnBrokenResource(jedis);
        	e.printStackTrace();
            LOG.error("", e);
            return null;
        } finally {
        	jedisPool.returnResource(jedis);
            if (eTime - sTime > 10000) {
            	LOG.info("hmgetPipeline time: " + (eTime-sTime) + "ms");
            }
        }
        return indexList;
    }
    
    
    public List<String> hgetPipelineResult(final String redisKey, final List<String> hashKeyList) {
        Jedis jedis = jedisPool.getResource();
        long sTime = System.currentTimeMillis();
        long eTime = 0;
        List<String> resultList = new ArrayList<String>();
        List<Response<String>> resList = new ArrayList<Response<String>>();
        try {
            Pipeline pipeline = jedis.pipelined();
            for (String hashKey : hashKeyList) {
            	Response<String> flag = pipeline.hget(redisKey, hashKey);
            	resList.add(flag);
			}
            pipeline.sync();
            
            for (int i = 0, size = hashKeyList.size(); i < size; i++) {
            	Response<String> flag = resList.get(i);
            	resultList.add(flag.get());
            }
            eTime = System.currentTimeMillis();
        } catch (Exception e) {
        	eTime = System.currentTimeMillis();
        	jedisPool.returnBrokenResource(jedis);
        	e.printStackTrace();
            LOG.error("", e);
            return null;
        } finally {
        	jedisPool.returnResource(jedis);
            if (eTime - sTime > 10000) {
            	LOG.info("hmgetPipeline time: " + (eTime-sTime) + "ms");
            }
        }
        return resultList;
    }
    
    public List<Boolean> hgetPipelineExist(final String redisKey, final List<String> hashKeyList) {
        Jedis jedis = jedisPool.getResource();
        long sTime = System.currentTimeMillis();
        long eTime = 0;
        List<Boolean> resultList = new ArrayList<Boolean>();
        List<Response<Boolean>> resList = new ArrayList<Response<Boolean>>();
        try {
            Pipeline pipeline = jedis.pipelined();
            for (String hashKey : hashKeyList) {
            	Response<Boolean> flag = pipeline.hexists(redisKey, hashKey);
            	resList.add(flag);
			}
            pipeline.sync();
            
            for (int i = 0, size = hashKeyList.size(); i < size; i++) {
            	Response<Boolean> flag = resList.get(i);
            	resultList.add(flag.get());
            }
            eTime = System.currentTimeMillis();
        } catch (Exception e) {
        	eTime = System.currentTimeMillis();
        	jedisPool.returnBrokenResource(jedis);
        	e.printStackTrace();
            LOG.error("", e);
            return null;
        } finally {
        	jedisPool.returnResource(jedis);
            if (eTime - sTime > 10000) {
            	LOG.info("hmgetPipeline time: " + (eTime-sTime) + "ms");
            }
        }
        return resultList;
    }
    

    public List<String> hgetPipelineString(final List<String> stringKeyList) {
        Jedis jedis = jedisPool.getResource();
        long sTime = System.currentTimeMillis();
        long eTime = 0;
        List<String> resultList = new ArrayList<String>();
        List<Response<String>> resList = new ArrayList<Response<String>>();
        try {
            Pipeline pipeline = jedis.pipelined();
            for (String stringKey : stringKeyList) {
            	Response<String> flag = pipeline.get(stringKey);
            	resList.add(flag);
			}
            pipeline.sync();
            
            for (int i = 0, size = stringKeyList.size(); i < size; i++) {
            	Response<String> flag = resList.get(i);
            	resultList.add(flag.get());
            }
            eTime = System.currentTimeMillis();
        } catch (Exception e) {
        	eTime = System.currentTimeMillis();
        	jedisPool.returnBrokenResource(jedis);
        	e.printStackTrace();
            LOG.error("", e);
            return null;
        } finally {
        	jedisPool.returnResource(jedis);
            if (eTime - sTime > 10000) {
            	LOG.info("hmgetPipeline time: " + (eTime-sTime) + "ms");
            }
        }
        return resultList;
    }
    
    

    public Map<String, List<Integer>> hsetPipelineNew(final String redisKey, final List<String> hashKeyList,
    		final List<String> hashValueList) {
        Jedis jedis = jedisPool.getResource();
        long sTime = System.currentTimeMillis();
        long eTime = 0;
        Map<String, List<Integer>> returnMap = new HashMap<String, List<Integer>>(2);
        List<Integer> noExistIndexList = new ArrayList<Integer>();
        List<Integer> existIndexList = new ArrayList<Integer>();
        List<Response<Long>> resList = new ArrayList<Response<Long>>();
        try {
            Pipeline pipeline = jedis.pipelined();
            for (int i = 0, size = hashKeyList.size(); i < size; i++) {
            	String hashKey = hashKeyList.get(i);
            	String hashValue = hashValueList.get(i);
            	Response<Long> resLong = pipeline.hset(redisKey, hashKey, hashValue);
            	resList.add(resLong);
            }
            pipeline.sync();
            
            for (int i = 0, size = resList.size(); i < size; i++) {
            	Response<Long> innerLong = resList.get(i);
				if (innerLong.get().intValue() == 0) {
					existIndexList.add(i);
				} else if (innerLong.get().intValue() == 1) {
					noExistIndexList.add(i);
				}
			}
            
            eTime = System.currentTimeMillis();
        } catch (Exception e) {
        	eTime = System.currentTimeMillis();
        	jedisPool.returnBrokenResource(jedis);
        	e.printStackTrace();
            LOG.error("", e);
        } finally {
        	jedisPool.returnResource(jedis);
            if (eTime - sTime > 10000) {
            	LOG.info("hmgetPipeline time: " + (eTime-sTime) + "ms");
            }
        }
        
        returnMap.put("exist", existIndexList);
        returnMap.put("noExist", noExistIndexList);
        return returnMap;
    }
    
    

    public void hsetPipelineString(final List<String> stringKeyList,
    		final List<String> stringValueList, final int seconds) {
        Jedis jedis = jedisPool.getResource();
        long sTime = System.currentTimeMillis();
        long eTime = 0;
        try {
            Pipeline pipeline = jedis.pipelined();
            
            for (int i = 0, size = stringKeyList.size(); i < size; i++) {
            	String stringKey = stringKeyList.get(i);
            	String stringValue = stringValueList.get(i);
            	pipeline.set(stringKey, stringValue);
            	pipeline.expire(stringKey, seconds);
            }
            pipeline.sync();
            eTime = System.currentTimeMillis();
        } catch (Exception e) {
        	eTime = System.currentTimeMillis();
        	jedisPool.returnBrokenResource(jedis);
        	e.printStackTrace();
            LOG.error("", e);
        } finally {
        	jedisPool.returnResource(jedis);
            if (eTime - sTime > 10000) {
            	LOG.info("hmgetPipeline time: " + (eTime-sTime) + "ms");
            }
        }
        
    }
    

    public List<Integer> hsetPipeline(final String redisKey, final List<String> hashKeyList,
    		final List<String> hashValueList) {
        Jedis jedis = jedisPool.getResource();
        long sTime = System.currentTimeMillis();
        long eTime = 0;
        List<Integer> noExistIndexList = new ArrayList<Integer>();
        List<Response<Boolean>> resList = new ArrayList<Response<Boolean>>();
        try {
            Pipeline pipeline = jedis.pipelined();
            for (String hashKey : hashKeyList) {
            	Response<Boolean> flag = pipeline.hexists(redisKey, hashKey);
            	resList.add(flag);
			}
            pipeline.sync();
            
            for (int i = 0, size = hashKeyList.size(); i < size; i++) {
            	String hashKey = hashKeyList.get(i);
            	String hashValue = hashValueList.get(i);
            	Response<Boolean> flag = resList.get(i);
            	if (!flag.get()) {
            		noExistIndexList.add(i);
					pipeline.hset(redisKey, hashKey, hashValue);
				}
            }
            pipeline.sync();
            
            eTime = System.currentTimeMillis();
        } catch (Exception e) {
        	eTime = System.currentTimeMillis();
        	jedisPool.returnBrokenResource(jedis);
        	e.printStackTrace();
            LOG.error("", e);
            return null;
        } finally {
        	jedisPool.returnResource(jedis);
            if (eTime - sTime > 10000) {
            	LOG.info("hmgetPipeline time: " + (eTime-sTime) + "ms");
            }
        }
        return noExistIndexList;
        
    }
    


    public void hIncrPipeline(final String redisKey, final List<String> hashKeyList,
    		final List<Long> hashValueList) {
        Jedis jedis = jedisPool.getResource();
        long sTime = System.currentTimeMillis();
        long eTime = 0;
        try {
            Pipeline pipeline = jedis.pipelined();
            
            for (int i = 0,size = hashKeyList.size(); i < size; i++) {
            	String hashKey = hashKeyList.get(i);
            	long hashValue = hashValueList.get(i);
            	pipeline.hincrBy(redisKey, hashKey, hashValue);
			}
            pipeline.sync();
            
            eTime = System.currentTimeMillis();
        } catch (Exception e) {
        	eTime = System.currentTimeMillis();
        	jedisPool.returnBrokenResource(jedis);
        	e.printStackTrace();
            LOG.error("", e);
        } finally {
        	jedisPool.returnResource(jedis);
            if (eTime - sTime > 10000) {
            	LOG.info("hIncrPipeline time: " + (eTime-sTime) + "ms");
            }
        }
    }
    

    public void hIncrPipeline(final String redisKey, final List<String> hashKeyList) {
        Jedis jedis = jedisPool.getResource();
        long sTime = System.currentTimeMillis();
        long eTime = 0;
        try {
            Pipeline pipeline = jedis.pipelined();
            for (int i = 0,size = hashKeyList.size(); i < size; i++) {
            	String hashKey = hashKeyList.get(i);
            	pipeline.hincrBy(redisKey, hashKey, 1l);
			}
            pipeline.sync();
            
            eTime = System.currentTimeMillis();
        } catch (Exception e) {
        	eTime = System.currentTimeMillis();
        	jedisPool.returnBrokenResource(jedis);
        	e.printStackTrace();
            LOG.error("", e);
        } finally {
        	jedisPool.returnResource(jedis);
            if (eTime - sTime > 10000) {
            	LOG.info("hIncrPipeline time: " + (eTime-sTime) + "ms");
            }
        }
    }
    
    /**
     * 获取hdfs文件上传的状态
     * @param args
     */
    
    public String getHdfsFileStatus(String key){
    	 Jedis jedis = jedisPool.getResource();
    	 return jedis.get(key);
    }
    
    public void setHdfsFileStatus(String key,String value){
   	 Jedis jedis = jedisPool.getResource();
   	 jedis.set(key, value);
   }
    
 
    /**
     * 获取hdfs任务job的状态
     * @param args
     */
    
    public String getHdfsJobStatus(String key){
    	 Jedis jedis = jedisPool.getResource();
    	 return jedis.get(key);
    }
    
    public void setHdfsJobStatus(String key,String value){
      	 Jedis jedis = jedisPool.getResource();
      	 jedis.set(key, value);
      }
    
    
 public static  void main(String[] args){
	 RedisDao dao  = RedisDao.getInstance("192.168.0.236|6379");
	 String value = dao.getHdfsFileStatus("hdfs_file");
	 System.out.println("value    ="+value);
 }

}
package org.tech.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author fangyunhe
 * @date 2017年9月22日 下午4:01:17
 * 存储每个Channel的传输数据
 */
public class ChannelGsmMessageCacheBuilder {
	private static Map<String,GsmMessage> gsmMessageMap = new HashMap<String,GsmMessage>();
	
	/**
	 * 过期的key时间集合
	 */
	private static Map<String,Long> expirationTime = new HashMap<String,Long>();
	
	/**
	 * 设置执行开始时间(单位毫秒)
	 */
	private static final int DELAY = 0;
	
	/**
	 * 设置间隔执行时间(单位毫秒)
	 */
    private static final int PERIOD = 1000 * 5;
	
	
	/**
	 * 过期时间20秒
	 */
	private static final long EXPIRATIONTIME = 1000 * 20;
	
	static{  
        Timer tt = new Timer();//定时类  
        tt.schedule(new TimerTask(){
			@Override
			public void run() {
				long now = new Date().getTime();//获取系统时间
				Iterator<Entry<String, Long>> iterator = expirationTime.entrySet().iterator();
				while(iterator.hasNext()){
					Entry<String, Long> item = iterator.next();
					String key = item.getKey();
					Long value = item.getValue();
					long rt = now - value;
					if(rt >= EXPIRATIONTIME){
						iterator.remove();
						gsmMessageMap.remove(key);
					}
				}
			}
        }, DELAY, PERIOD);
    }

	public static Map<String, GsmMessage> getHandlerMap() {
		return gsmMessageMap;
	}

	public static void setHandlerMap(Map<String, GsmMessage> handlerMap) {
		ChannelGsmMessageCacheBuilder.gsmMessageMap = handlerMap;
	}
	
	private static void put(String channelId,GsmMessage gsmMessage) {
		gsmMessageMap.put(channelId, gsmMessage);
		expirationTime.put(channelId, new Date().getTime());
	}
	
	/**
	 * 合并对象的payload数据
	 * @param channelId
	 * @param gsmMessage
	 * @return
	 */
	public static void merge(String channelId,GsmMessage newGsmMessage) {
		GsmMessage oldGsmMessage = gsmMessageMap.get(channelId);
		if(oldGsmMessage != null){
			oldGsmMessage.getPayload().putAll(newGsmMessage.getPayload());
		}else{
			put(channelId, newGsmMessage);
		}
	}
	
	public static GsmMessage get(String channelId) {
		return gsmMessageMap.get(channelId);
	}
	
	public static GsmMessage getAndRemove(String channelId) {
		GsmMessage gsmMessage = gsmMessageMap.get(channelId);
		expirationTime.remove(channelId);
		gsmMessageMap.remove(channelId);
		return gsmMessage;
	}
	
}

package com.lmiky.area.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lmiky.area.pojo.City;
import com.lmiky.jdp.logger.util.LoggerUtils;
import com.lmiky.jdp.redis.RedisOperator;
import com.lmiky.jdp.service.BaseService;
import com.lmiky.jdp.util.Environment;
import com.lmiky.jdp.util.IPUtils;

public class AreaUtils {
	// redis键
	public static final String REDIS_KEY_IP_CITY = "ip:city";
	private static final RedisOperator redisOperator = (RedisOperator) Environment.getBean("redisOperator");

	private static final BaseService service = (BaseService) Environment.getBean("baseService");;

	/**
	 * 获取城市编码
	 * @author lmiky
	 * @date 2015年5月12日 下午5:40:12
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public static String getIpCityCode(String ip) throws Exception {
		// 读缓存
		Object cityCodeObj = redisOperator.hget(REDIS_KEY_IP_CITY, ip);
		if (cityCodeObj != null) {
			return cityCodeObj.toString();
		}
		//读取定位信息
		Map<String, String> location = IPUtils.location(ip);
		String cityName = location.get(IPUtils.PARAMNAME_CITY);
		if (StringUtils.isBlank(cityName)) {
			return null;
		}
		String cityCode = null;
		City city = service.find(City.class, City.POJO_FIELD_NAME_NAME, cityName);
		if (city != null) {
			cityCode = city.getCode();
			try {
				redisOperator.hset(REDIS_KEY_IP_CITY, ip, cityCode);	//设置缓存
			} catch (Exception e) {
				LoggerUtils.logException(e);
			}
		}
		return cityCode;
	}
}

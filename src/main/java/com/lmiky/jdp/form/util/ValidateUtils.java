package com.lmiky.jdp.form.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.lmiky.jdp.form.model.ValidateError;

/**
 * 校验工具
 * @author lmiky
 * @date 2013-5-9
 */
public class ValidateUtils {

	/**
	 * 验证必填
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateRequired(HttpServletRequest request, String fieldName, String fieldDesc, List<ValidateError> validateErrors) {
		String[] paramValues = request.getParameterValues(fieldName);
		if (paramValues != null && paramValues.length != 0) {
			for (int i = 0; i < paramValues.length; i++) {
				if (!StringUtils.isBlank(paramValues[i])) {
					return true;
				}
			}
		}
		validateErrors.add(new ValidateError(fieldName, fieldDesc + "不能为空！"));
		return false;
	}

	/**
	 * 验证邮箱
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateEmail(HttpServletRequest request, String fieldName, String fieldDesc, List<ValidateError> validateErrors) {
		String email = request.getParameter(fieldName);
		if (StringUtils.isBlank(email)) {
			return false;
		}
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		if (m.find()) {
			return true;
		}
		validateErrors.add(new ValidateError(fieldName, fieldDesc + "格式错误！"));
		return false;
	}

	/**
	 * 验证日期
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateDate(HttpServletRequest request, String fieldName, String fieldDesc, List<ValidateError> validateErrors) {
		String date = request.getParameter(fieldName);
		if (StringUtils.isBlank(date)) {
			return false;
		}
		String regex = "^\\d{4}\\-((0[1-9])|(1[0-2]))\\-\\d{2}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(date);
		if (m.find()) {
			return true;
		}
		validateErrors.add(new ValidateError(fieldName, fieldDesc + "格式错误！"));
		return false;
	}

	/**
	 * 验证日期带时间
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateTime(HttpServletRequest request, String fieldName, String fieldDesc, List<ValidateError> validateErrors) {
		String dateTime = request.getParameter(fieldName);
		if (StringUtils.isBlank(dateTime)) {
			return false;
		}
		String regex = "^\\d{4}\\-((0[1-9])|(1[0-2]))\\-\\d{2} (([0-1][1-9])|(2[0-3])):[0-5]\\d:[0-5]\\d$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(dateTime);
		if (m.find()) {
			return true;
		}
		validateErrors.add(new ValidateError(fieldName, fieldDesc + "格式错误！"));
		return false;
	}

	/**
	 * 验证数字
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateNumber(HttpServletRequest request, String fieldName, String fieldDesc, List<ValidateError> validateErrors) {
		String number = request.getParameter(fieldName);
		if (StringUtils.isBlank(number)) {
			return false;
		}
		try {
			Double.parseDouble(number);
		} catch (NumberFormatException e) {
			validateErrors.add(new ValidateError(fieldName, fieldDesc + "请输入合法的数字！"));
			return false;
		}
		return true;
	}

	/**
	 * 验证整数
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateDigits(HttpServletRequest request, String fieldName, String fieldDesc, List<ValidateError> validateErrors) {
		String number = request.getParameter(fieldName);
		if (StringUtils.isBlank(number)) {
			return false;
		}
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException e) {
			validateErrors.add(new ValidateError(fieldName, fieldDesc + "只能输入整数！"));
			return false;
		}
		return true;
	}
	
	/**
	 * 验证是否正整数
	 * @author lmiky
	 * @date 2015年5月19日 上午11:02:21
	 * @param request
	 * @param fieldName
	 * @param fieldDesc
	 * @param validateErrors
	 * @return
	 */
	public static boolean validatePositiveDigits(HttpServletRequest request, String fieldName, String fieldDesc, List<ValidateError> validateErrors) {
		String number = request.getParameter(fieldName);
		if (StringUtils.isBlank(number)) {
			return false;
		}
		try {
			return Integer.parseInt(number) >= 0;
		} catch (NumberFormatException e) {
			validateErrors.add(new ValidateError(fieldName, fieldDesc + "只能输入正整数！"));
			return false;
		}
	}

	/**
	 * 验证相等
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateEqualTo(HttpServletRequest request, String fieldName, String fieldDesc, String compareFieldName,
			String compareFieldDesc, List<ValidateError> validateErrors) {
		String value = request.getParameter(fieldName);
		if (StringUtils.isBlank(value)) {
			return false;
		}
		String compareValue = request.getParameter(compareFieldName);
		if (StringUtils.isBlank(compareValue)) {
			return false;
		}
		if (!value.equals(compareValue)) {
			validateErrors.add(new ValidateError(fieldName, fieldDesc + "值与" + compareFieldDesc + "值不一样！"));
		}
		return true;
	}

	/**
	 * 验证值相等
	 * @author lmiky
	 * @date 2013-6-5
	 * @param fieldName
	 * @param fieldValue
	 * @param fieldDesc
	 * @param compareFieldName
	 * @param compareFieldValue
	 * @param compareFieldDesc
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateEqualTo(String fieldName, String fieldValue, String fieldDesc, String compareFieldName, String compareFieldValue,
			String compareFieldDesc, List<ValidateError> validateErrors) {
		if (fieldValue == null && compareFieldValue == null) {
			return true;
		}
		if (!fieldValue.equals(compareFieldValue)) {
			validateErrors.add(new ValidateError(fieldName, fieldDesc + "值与" + compareFieldDesc + "值不一样！"));
		}
		return true;
	}

	/**
	 * 验证最大长度
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param maxLength 允许的最大长度
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateMaxlength(HttpServletRequest request, String fieldName, String fieldDesc, int maxLength,
			List<ValidateError> validateErrors) {
		String value = request.getParameter(fieldName);
		if (StringUtils.isBlank(value)) {
			return false;
		}
		if (value.length() > maxLength) {
			validateErrors.add(new ValidateError(fieldName, fieldDesc + "长度允许最大长度为" + maxLength + "！"));
		}
		return true;
	}

	/**
	 * 验证最大长度
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param minLength 允许的最小长度
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateMinlength(HttpServletRequest request, String fieldName, String fieldDesc, int minLength,
			List<ValidateError> validateErrors) {
		String value = request.getParameter(fieldName);
		if (StringUtils.isBlank(value)) {
			return false;
		}
		if (value.length() < minLength) {
			validateErrors.add(new ValidateError(fieldName, fieldDesc + "长度允许最大长度为" + minLength + "！"));
		}
		return true;
	}

	/**
	 * 验证最大长度
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param maxLength 允许的最大长度
	 * @param minLength 允许的最小长度
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateRangeLength(HttpServletRequest request, String fieldName, String fieldDesc, int maxLength, int minLength,
			List<ValidateError> validateErrors) {
		String value = request.getParameter(fieldName);
		if (StringUtils.isBlank(value)) {
			return false;
		}
		if (value.length() < minLength || value.length() > maxLength) {
			validateErrors.add(new ValidateError(fieldName, fieldDesc + "长度允许最大长度为" + minLength + "！"));
		}
		return true;
	}

	/**
	 * 验证最大值
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param minValue 允许的最大值
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateNumberMax(HttpServletRequest request, String fieldName, String fieldDesc, double maxValue,
			List<ValidateError> validateErrors) {
		String number = request.getParameter(fieldName);
		if (StringUtils.isBlank(number)) {
			return false;
		}

		try {
			double value = Double.parseDouble(number);
			if (value > maxValue) {
				validateErrors.add(new ValidateError(fieldName, fieldDesc + "必须小于" + maxValue + "！"));
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 验证最小值
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param minValue 允许的最小值
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateNumberMin(HttpServletRequest request, String fieldName, String fieldDesc, double minValue,
			List<ValidateError> validateErrors) {
		String number = request.getParameter(fieldName);
		if (StringUtils.isBlank(number)) {
			return false;
		}

		try {
			double value = Double.parseDouble(number);
			if (value < minValue) {
				validateErrors.add(new ValidateError(fieldName, fieldDesc + "必须大于" + minValue + "！"));
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 验证取值范围
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param maxValue 允许的最大值
	 * @param minValue 允许的最小值
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateNumberRange(HttpServletRequest request, String fieldName, String fieldDesc, double maxValue, double minValue,
			List<ValidateError> validateErrors) {
		String number = request.getParameter(fieldName);
		if (StringUtils.isBlank(number)) {
			return false;
		}

		try {
			double value = Double.parseDouble(number);
			if (value > minValue || value < minValue) {
				validateErrors.add(new ValidateError(fieldName, fieldDesc + "值必须在" + maxValue + "与" + minValue + "之间！"));
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 验证整数最大值
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param minValue 允许的最大值
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateDigitsMax(HttpServletRequest request, String fieldName, String fieldDesc, int maxValue,
			List<ValidateError> validateErrors) {
		String number = request.getParameter(fieldName);
		if (StringUtils.isBlank(number)) {
			return false;
		}

		try {
			int value = Integer.parseInt(number);
			if (value > maxValue) {
				validateErrors.add(new ValidateError(fieldName, fieldDesc + "必须小于" + maxValue + "！"));
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 验证整数最小值
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param minValue 允许的最小值
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateDigitsMin(HttpServletRequest request, String fieldName, String fieldDesc, int minValue,
			List<ValidateError> validateErrors) {
		String number = request.getParameter(fieldName);
		if (StringUtils.isBlank(number)) {
			return false;
		}

		try {
			int value = Integer.parseInt(number);
			if (value < minValue) {
				validateErrors.add(new ValidateError(fieldName, fieldDesc + "必须大于" + minValue + "！"));
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 验证整数取值范围
	 * @author lmiky
	 * @date 2013-5-9
	 * @param request
	 * @param fieldName 字段名
	 * @param fieldDesc 字段说明
	 * @param maxValue 允许的最大值
	 * @param minValue 允许的最小值
	 * @param validateErrors
	 * @return
	 */
	public static boolean validateDigitsRange(HttpServletRequest request, String fieldName, String fieldDesc, int maxValue, int minValue,
			List<ValidateError> validateErrors) {
		String number = request.getParameter(fieldName);
		if (StringUtils.isBlank(number)) {
			return false;
		}

		try {
			int value = Integer.parseInt(number);
			if (value > minValue || value < minValue) {
				validateErrors.add(new ValidateError(fieldName, fieldDesc + "值必须在" + maxValue + "与" + minValue + "之间！"));
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}

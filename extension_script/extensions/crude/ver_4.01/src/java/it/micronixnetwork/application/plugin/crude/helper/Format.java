package it.micronixnetwork.application.plugin.crude.helper;

import it.micronixnetwork.application.plugin.crude.annotation.renderer.TextRenderer;
import it.micronixnetwork.gaf.util.DateUtil;
import it.micronixnetwork.gaf.util.NumberUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.opensymphony.xwork2.util.ValueStack;

public class Format {

    // Insieme utilizzazto per individuare gli scalari
    public static final Set<Class> SCALAR = new HashSet<Class>(Arrays.asList(String.class, Boolean.class, Byte.class, Character.class, Short.class, Integer.class, Float.class, Long.class,
	    Double.class, BigInteger.class, BigDecimal.class, AtomicBoolean.class, AtomicInteger.class, AtomicLong.class, Date.class, Calendar.class, GregorianCalendar.class, Class.class, UUID.class,
	    Number.class, Object.class, Timestamp.class, Time.class));

    public static String realDeformatter(String value, ValueStack stack) {
	String digit_sep = I18n.getText("digit.separator", ".", stack);
	String dec_sep = I18n.getText("decimal.separator", ",", stack);
	String result = value.replace(digit_sep, "");
	result = result.replace(dec_sep, ".");
	return result;
    }

    public static Object convert(Class targetClass, String value, ValueStack stack) {
	if (!SCALAR.contains(targetClass)) {
	    return null;
	}

	if (value == null) {
	    return null;
	}

	if (targetClass == String.class) {
	    if (value.length() == 0)
		return null;
	    return value;
	}

	if (targetClass == Character.class) {
	    return new Character(value.charAt(0));
	}

	if (targetClass == Date.class) {
	    try {
		return DateUtil.parseDate(value, I18n.getText("date.format", "dd/MM/yyyy", stack));
	    } catch (ParseException e) {
		return null;
	    }
	}

	if (targetClass == java.sql.Time.class) {
	    try {
		Date d_time = DateUtil.parseDate(value, I18n.getText("ora.format", "HH:mm", stack));
		if (d_time != null) {
		    return new Time(d_time.getTime());
		}
	    } catch (ParseException e) {
		return null;
	    }
	}

	if (targetClass == Integer.class) {
	    try {
		if (value.matches("[0-9][0-9]:[0-9][0-9]")) {
		    Integer ore = Integer.parseInt(value.substring(0, 2));
		    Integer min = Integer.parseInt(value.substring(3, 5));
		    int result = ore * 60 + min;
		    return result;
		}
		return new Integer(value);
	    } catch (Exception e) {
		return null;
	    }
	}

	if (targetClass == Double.class) {
	    try {
		value = Format.realDeformatter(value, stack);
		return new Double(value);
	    } catch (Exception e) {
		return null;
	    }

	}

	if (targetClass == Float.class) {
	    try {
		value = Format.realDeformatter(value, stack);
		return new Float(value);
	    } catch (Exception e) {
		return null;
	    }

	}

	if (targetClass == Long.class) {
	    try {
		return new Long(value);
	    } catch (Exception e) {
		return null;
	    }

	}

	if (targetClass == Boolean.class) {
	    try {
		return new Boolean(value);
	    } catch (Exception e) {
		return null;
	    }

	}

	if (targetClass == Short.class) {
	    try {
		return new Short(value);
	    } catch (Exception e) {
		return null;
	    }

	}

	if (targetClass == BigDecimal.class) {
	    try {
		value = Format.realDeformatter(value, stack);
		return new BigDecimal(value);
	    } catch (Exception e) {
		return null;
	    }

	}
	return null;
    }

    public static String formatValue(Object value, Field field, ValueStack stack) {
	if (value == null)
	    return "";
	
	if (value instanceof Time) {
		return DateUtil.formatDate((Date) value, I18n.getText("ora.format", "HH:mm", stack));
	}
	
	 if (value instanceof Date) {
	     Temporal tempo=field.getAnnotation(Temporal.class);
	     if(tempo!=null){
		 if(tempo.value()==TemporalType.DATE){
		     return DateUtil.formatDate((Date) value, I18n.getText("date.format", "dd/MM/yyyy", stack));
		 }
		 if(tempo.value()==TemporalType.TIME){
		     return DateUtil.formatDate((Date) value, I18n.getText("ora.format", "HH:mm", stack));
		 }
	     }
             return DateUtil.formatDate((Date) value, I18n.getText("timastamp.format", "dd/MM/yyyy - HH:mm", stack));
	 }
	

	if (value instanceof Double) {
	    String numberPattern = I18n.getText("real.format", "###,##0.#", stack);
	    String dec_sep = I18n.getText("decimal.separator", ",", stack);
	    String digit_sep = I18n.getText("digit.separator", ".", stack);
	    return NumberUtil.formatReal(value, numberPattern, dec_sep.charAt(0), digit_sep.charAt(0));
	}

	if (value instanceof Float) {
	    String numberPattern = I18n.getText("real.format", "###,##0.#", stack);
	    String dec_sep = I18n.getText("decimal.separator", ",", stack);
	    String digit_sep = I18n.getText("digit.separator", ".", stack);
	    return NumberUtil.formatReal(value, numberPattern, dec_sep.charAt(0), digit_sep.charAt(0));
	}
	
	return value.toString();
    }

}

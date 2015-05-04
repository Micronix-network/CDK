package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.service.SearchResult;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 
 * 
 * @author kobo
 * 
 */
public class Find extends CrudAction {

    private static final Set<Class> NO_LIKE = new HashSet<Class>(Arrays.asList(Boolean.class, Byte.class, Character.class, Short.class, Integer.class, Float.class, Long.class, Double.class,
	    BigInteger.class, BigDecimal.class, AtomicBoolean.class, AtomicInteger.class, AtomicLong.class, Date.class, Calendar.class, GregorianCalendar.class, Number.class, Timestamp.class,
	    Time.class));

    private static final long serialVersionUID = 1L;

    // Attributi di paginazione
    private Integer page;
    private Integer size;
    private String toOrder;
    
    //Classe principale;
    private Class target;
    
    //Alias Classe principale 
    private String tbAlias;

    // Posibili filtri di ricerca
    private Map<String, String> filters = new HashMap<String, String>();

    public Map<String, String> getFilters() {
	return filters;
    }

    public void setToOrder(String toOrder) {
	this.toOrder = toOrder;
    }
    
    public String getTbAlias() {
	return tbAlias;
    }

    // Rsultato ricerca
    private SearchResult searchResult;
    
    /*
    from Product as product 
    inner join product.productInventories inv with inv.availableQuantity>0 
     */
    @Override
    protected String doIt() throws ApplicationException {

	// Assert
	if(targetClass==null) throw new ActionException("Object class not defined");
	
	target = getPrototypeClass(targetClass);
	
	if(target==null) throw new ActionException("Principal find class not found");
        
        tbAlias = target.getSimpleName().toLowerCase().substring(0, 3);
	
	if (page == null)
	    page = 1;
	if (size == null)
	    size = 20;
	
	Integer limit = null;

	try {
	    limit = Integer.parseInt(getCardParam("result_limit", false));
	} catch (Exception ex) {

	}
        
        searchResult = getCrudeService().find(getUser(), target, filters, toOrder, page, size, limit);

	return SUCCESS;
    }
    
    

    

    public SearchResult getSearchResult() {
	return searchResult;
    }

    public Integer getPage() {
	return page;
    }

    public void setPage(Integer page) {
	this.page = page;
    }

    public Integer getSize() {
	return size;
    }

    public void setSize(Integer size) {
	this.size = size;
    }

    @Override
    protected boolean checkRole() {
	return true;
    }

}

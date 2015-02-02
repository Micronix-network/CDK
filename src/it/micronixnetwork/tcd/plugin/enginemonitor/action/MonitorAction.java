package it.micronixnetwork.tcd.plugin.enginemonitor.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.struts2.action.CardAction;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.concurrent.ScheduledExecutorTask;

public class MonitorAction extends CardAction {

    private static final long serialVersionUID = 1L;

    ScheduledExecutorTask tcdRunSplitter;

    ScheduledExecutorTask tcdRunComposer;

    ScheduledExecutorTask tcdRunRouter;

    java.util.concurrent.ScheduledThreadPoolExecutor engineExecutorFactoryBean;

    public void setTcdRunSplitter(ScheduledExecutorTask tcdRunSplitter) {
	this.tcdRunSplitter = tcdRunSplitter;
    }

    public void setTcdRunComposer(ScheduledExecutorTask tcdRunComposer) {
	this.tcdRunComposer = tcdRunComposer;
    }

    public void setTcdRunRouter(ScheduledExecutorTask tcdRunRouter) {
	this.tcdRunRouter = tcdRunRouter;
    }

    public void setEngineExecutorFactoryBean(java.util.concurrent.ScheduledThreadPoolExecutor engineExecutorFactoryBean) {
	this.engineExecutorFactoryBean = engineExecutorFactoryBean;
    }

    protected String exe() throws ApplicationException {
	return SUCCESS;
    }

    protected void stopStopEngine() {
	BlockingQueue<Runnable> quque = engineExecutorFactoryBean.getQueue();
	if (quque.size() > 0) {
	    for (Runnable runnable : quque) {
		engineExecutorFactoryBean.remove(runnable);
	    }
	}
    }

    protected void startEngine() {
	BlockingQueue<Runnable> quque = engineExecutorFactoryBean.getQueue();
	if (quque.size() == 0) {
	    engineExecutorFactoryBean.scheduleAtFixedRate(tcdRunSplitter.getRunnable(), tcdRunSplitter.getDelay(), tcdRunSplitter.getPeriod(), TimeUnit.MILLISECONDS);
	    engineExecutorFactoryBean.scheduleAtFixedRate(tcdRunComposer.getRunnable(), tcdRunComposer.getDelay(), tcdRunComposer.getPeriod(), TimeUnit.MILLISECONDS);
	    engineExecutorFactoryBean.scheduleAtFixedRate(tcdRunRouter.getRunnable(), tcdRunRouter.getDelay(), tcdRunRouter.getPeriod(), TimeUnit.MILLISECONDS);
	}
    }

    public boolean isEngineActive() {
	BlockingQueue<Runnable> quque = engineExecutorFactoryBean.getQueue();
	if (quque.size() == 0) {
	    return false;
	}
	return true;
    }
    
    protected void shutdown(){
	if(engineExecutorFactoryBean!=null){
	    engineExecutorFactoryBean.shutdown();
	}
    }
    
    public boolean isShutdown(){
	if(engineExecutorFactoryBean!=null){
	    return engineExecutorFactoryBean.isShutdown();
	}
	return true;
    }
    
    public boolean isTerminated(){
	if(engineExecutorFactoryBean!=null){
	    return engineExecutorFactoryBean.isTerminated();
	}
	return true;
    }
    
    public int getQuequeSize(){
	if(engineExecutorFactoryBean!=null){
	    return engineExecutorFactoryBean.getQueue().size();
	}
	return 0;
    }
    
    public long getCompletedTask(){
	if(engineExecutorFactoryBean!=null){
	    return engineExecutorFactoryBean.getCompletedTaskCount();
	}
	return 0;
    }
    
    public long getActiveTask(){
	if(engineExecutorFactoryBean!=null){
	    return engineExecutorFactoryBean.getActiveCount();
	}
	return 0;
    }

}

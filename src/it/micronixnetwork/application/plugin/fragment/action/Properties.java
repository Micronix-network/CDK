package it.micronixnetwork.application.plugin.fragment.action;

import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.FileSystemService;
import it.micronixnetwork.gaf.struts2.action.RetriveProperties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Properties extends RetriveProperties {

    private static final long serialVersionUID = -2911508196730073594L;
    
    public List<String> getFragments() throws ServiceException {
	File baseDirectory=new File(getApplicationPath()+"WEB-INF/view/fragments");
	debug(baseDirectory.getAbsolutePath());
	//return new ArrayList<File>();
	List<String> result=new ArrayList<String>();
	List<File> files= fileSystemService.getFiles(baseDirectory,"ftl",FileSystemService.NAME,FileSystemService.ASC);
	for (File frag : files) {
	    String toAdd=frag.getName();
	    int index=toAdd.lastIndexOf(".");
	    toAdd=toAdd.substring(0,index);
	    result.add(toAdd);
	}
	return result;
    }

    @Override
    protected String exe() throws ApplicationException {
        return super.exe();
    }


}

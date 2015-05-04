package it.micronixnetwork.application.plugin.crude.gui.ftl;


import it.micronixnetwork.application.plugin.crude.gui.component.ResultListTable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.freemarker.tags.TagModel;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * @see Set
 */
public class ResultListModel extends TagModel {

  private static final long serialVersionUID = 1L;
  
  
  public ResultListModel(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
      super(stack, req, res);
  }

  protected Component getBean() {
      return new ResultListTable(stack, req, res);
  }
  
}

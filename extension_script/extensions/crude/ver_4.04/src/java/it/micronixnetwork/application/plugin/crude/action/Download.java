package it.micronixnetwork.application.plugin.crude.action;

import it.micronixnetwork.application.plugin.crude.annotation.DownloadRule;
import it.micronixnetwork.application.plugin.crude.helper.FieldUtil;
import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.util.FileUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Command per il recupero di una commessa dal DB
 *
 * @author kobo
 *
 */
public class Download extends CrudAction {

    private static final long serialVersionUID = 1L;

    private Map<String, String> idObj = new HashMap<String, String>();

    private Object target;

    private String fieldName;

    private String documentoFileName;
    private long documentoFileSize;
    private InputStream download;

    public String getDocumentoFileName() {
        return documentoFileName;
    }

    public long getDocumentoFileSize() {
        return documentoFileSize;
    }

    public InputStream getDownload() {
        return download;
    }

    public Download() {
    }

    @Override
    protected String doIt() throws ApplicationException {

        // Assert
        if (idObj.size() == 0) {
            throw new ActionException("Object key paramenter not retrived");
        }

        if (targetClass == null) {
            throw new ActionException("Object class not defined");
        }

        if (fieldName == null) {
            throw new ActionException("Params fieldName not defined");
        }

        Class clazz = getPrototypeClass(targetClass);

        Object objId = createObjectId(clazz, idObj);

        if (objId == null) {
            throw new ActionException("Object primarykey not created");
        }

        if (clazz != null) {
            target = getCrudeService().get(clazz, objId);
        } else {
            throw new ActionException("Object class not loadable");
        }

	// Costruzione del path per il recupero del file da inviave
        Field downField = FieldUtil.retriveField(fieldName, clazz);

        // Recupero informazioni
        DownloadRule down = downField.getAnnotation(DownloadRule.class);
        if (down != null) {
            String filePath = retriveFilePath(down, target);

            if (filePath != null) {
                try {
                    createInputStream(filePath);
                } catch (Exception ex) {
                    return "download_error";
                }
            }

            Object fileNameRule = ognlEvaluation(down.fileName(), target);
            if (fileNameRule == null) {
                documentoFileName = down.fileName();
            } else {
                documentoFileName = fileNameRule.toString();
            }

            return SUCCESS;
        }

        return "nop";

    }

    private String retriveFilePath(DownloadRule down, Object target) {
        if (down != null) {
            // Path diretto
            String path = down.downloadDirectPath();
            if (!path.equals("")) {
                return path;
            }
            // Path in un field
            String rule = down.downloadRulePath();
            Object rvalue = ognlEvaluation(rule, target);
            if (rvalue != null) {
                return rvalue.toString();
            }

        }
        return null;
    }

    public Object getTarget() {
        return target;
    }

    public Map<String, String> getIdObj() {
        return idObj;
    }

    public void setIdObj(Map<String, String> idObj) {
        this.idObj = idObj;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    protected boolean checkRole() {
        return true;
    }

    private void createInputStream(String realPath) throws IOException {
        File file = new File(realPath);
        byte[] bstream = FileUtil.getBytesFromFile(file);
        documentoFileSize = bstream.length;
        download = new ByteArrayInputStream(bstream);
    }

}

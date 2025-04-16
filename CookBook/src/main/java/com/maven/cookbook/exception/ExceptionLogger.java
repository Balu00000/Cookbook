package com.maven.cookbook.exception;

public class ExceptionLogger {
    private Class actualClass;
    
    public ExceptionLogger(Class actualClass) {
        this.actualClass = actualClass;
    }
    
    /**
     * Use this for JWT class
     */
    public void errorLog(Exception ex){
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if(stackTrace.length>0){
            StackTraceElement element = stackTrace[0];
            for(StackTraceElement actualElement: stackTrace){
                if(actualElement.getClassName().contains(this.actualClass.getName())){
                    element = actualElement;
                    break;
                }
            }
            
            System.err.println(
            "\n--------------- Exception ---------------"
            + "\nStatus: JWT Class Exception"
            + "\nStatus Code: 500"
            + "\nException Message: " + ex.getMessage()
            + "\nException Class: " + ex.getClass().getName()
            + "\nFile: " + element.getFileName()
            + "\nFunction: " + element.getMethodName() + "()"
            + "\nLine: " + element.getLineNumber()
            + "\nClass Loader Name: " + element.getClassLoaderName()
            + "\nModul Name: " + element.getModuleName()
            + "\nModul Version: " + element.getModuleVersion()
            + "\n--------------- Exception ---------------"
            );
        }
    }
}

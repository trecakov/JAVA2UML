import java.io.File;
import java.io.FilenameFilter;
import javax.swing.filechooser.FileFilter;

public class FileName extends FileFilter implements FilenameFilter{
    
    public static final String java_EXTENSION = ".java";
    public static final String java_DESCRIPTION = "Java file";
    
    public boolean accept(File dir, String name){
        
        try {
            if(name != null)
                return name.endsWith(java_EXTENSION);
            
        } catch (Exception e){
        }
        return false;
        
    }//accept
    
    public boolean accept(File file){
        
        try{
            
            //check if the file is not nule and if the file is a directory
            if(file != null && file.isDirectory())
                return true;

            //check if the file extension is ".java"
            if(file != null && file.getCanonicalPath() != null)
                return file.getCanonicalPath().endsWith(java_EXTENSION);
            
        }catch (Exception e){
        }
        
        return false;
        
    }//accept
  
    public String getDescription(){
        
        return java_DESCRIPTION;
        
    }//getDescription
}//FileName

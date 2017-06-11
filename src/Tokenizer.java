import java.util.*;

public class Tokenizer implements Opcode{
  public String classType;
  public String className;
  public int classAccess; 
  //private static ArrayList<String> className;
  private ArrayList<Item> methods;
  private ArrayList<Item> vars;
  
//
// CONSTRUCTOR
//
  public Tokenizer(){
    className = "";
    classType = "";
    //className = new ArrayList<String>();
    methods   = new ArrayList<>();
    vars      = new ArrayList<>();
  }

//
// ADD CLASS
//
//  public void addClasses(String name){
//    className.add(name);
//  }

//
// ADD METHODS
//
  public void addMethods(String name, int access){
    methods.add(new Item(name, access));
  }

//
// ADD VARS
//
  public void addVars(String nametype, int access){
    vars.add(new Item(nametype, access));
  }

//
// IF EXISTS
//
  public int ifExists(String name){
    int count = 0;
    for(int i = 0; i < vars.size(); i++){
      //System.out.println(vars.get(i).nametype + " comparing " + name);
      if(vars.get(i).nametype.contains(name)){
        count++;
        if(vars.get(i).nametype.contains("[]")){
          count++;
        }
        if(vars.get(i).nametype.contains("<") && vars.get(i).nametype.contains(">")){
          count++;
        }
      }
    }

    //for(int i = 0; i < methods.size(); i++){
    //  System.out.println(methods.get(i).nametype + " comparing " + name);
    //  if(methods.get(i).nametype.contains(name)){
    //    count++;
    //  }
    //}

    return count;
  }

//
// CLASS WRITER
//
  /*
  public boolean classWriter(String fileName) throws IOException{
    try{
      FileWriter writer = new FileWriter(fileName, true);

      writer.write(classType + " " + className + "{\n"); 
      System.out.println(classType + " " + className);

      for(String str: methods){
        writer.write("\t" + str + "\n");
        //System.out.println(str);
      }

      writer.write("\n}\n");
      writer.close();
    }catch(IOException e){
      return false;
    }
  return true;
  }*/ 
  
  @Override
  public String toString(){
	  StringBuilder sb = new StringBuilder(); 
      sb.append(classType + " " + className + "{\n"); 
      //System.out.println("[FOUND] " + classType + " " + className);

      for(Item item: vars){
          sb.append("\t"+getVisibilityIcon(item.access) + item.nametype + "\n");
      }
      
      for(Item item: methods){
        sb.append("\t" +getVisibilityIcon(item.access)+item.nametype + "\n");
      }

      sb.append("\n}\n");
      return sb.toString(); 
  }

  public String toStringYUML(){
    StringBuilder sb = new StringBuilder(); 
    sb.append("["); 

      sb.append(className + "|"); 
      
      for(Item item: vars){
          sb.append(getVisibilityIcon(item.access)+ escapeYUML(item.nametype) + ";");
      }
      
      sb.append("|"); 
      
      for(Item item: methods){
        sb.append(getVisibilityIcon(item.access)+escapeYUML(item.nametype) + ";");
      }

      sb.append("]");
      return sb.toString(); 
  }

  private String getVisibilityIcon(int access){
        if((access & Opcode.ACC_PUBLIC) > 0){
           return "+"; 
        }
        if((access & Opcode.ACC_PRIVATE) > 0){
            return "-"; 
        }
        if((access & Opcode.ACC_PROTECTED) > 0){
            return "#"; 
        }
         
        return "~"; 
    }

      private String escapeYUML(String s){
 
    return s.replaceAll("\\s+", "")
        .replaceAll(",",unicode.get(","))
        .replaceAll("\\]",unicode.get("]"))
        .replaceAll("\\[",unicode.get("["))
        .replaceAll("<",unicode.get("<"))
        .replaceAll(">",unicode.get(">")); 
  }
  
  private static Map<String, String> unicode = new HashMap<String, String>(){
    {
      put("[", "&#65339;"); //FULLWIDTH
      put("]", "&#65341;"); 
      put(",", "&#65292;"); 
      put("<", "&#65308;"); 
      put(">", "&#65310;"); 
    }
  }; 

}



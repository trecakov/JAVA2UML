import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sourceforge.plantuml.SourceStringReader;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class Parser {
    
    private static final String OUT_DIR = "../output/"; 
    //private static final String TEST_DIR = "../testcases/";
    public static int index = -1;
    public static File[] files;
    public static File fileSave;
    public static String f1, f2, fileName;

    static ArrayList<Tokenizer> T;
    //static Tokenizer tokens;
    //static ArrayList<String> aggregation;
    static ArrayList<Relation> relation;
    public static void newToken(){
        T.add(new Tokenizer());
    }
    public static void newRelation(){
        relation.add(new Relation());
    }
    
    // table for storing extends and implements 
    static Map<String, String> mapExtend = new HashMap<>(); 
    static Map<String, List<String>> mapImplement = new HashMap<>(); 
    
   /* public static void main(String[] args) throws FileNotFoundException, ParseException, IOException {
        
        File[] files = fileFinder(TEST_DIR); 
        T = new ArrayList<Tokenizer>();
        aggregation = new ArrayList<String>();

        for(File file: files){
            
            String filename = file.getName();
            
            String testClass = TEST_DIR + filename;
            FileInputStream inputStream = new FileInputStream(new File(testClass)); 
            // parse the file 
            CompilationUnit cu = JavaParser.parse(inputStream); 
            new MethodVisitor().visit(cu, null);
            
            //break; 
        }
        
        // prepare output 
        StringBuilder source = new StringBuilder(); 
        source.append("@startuml\n\n");
        System.out.println("\n\n\n\n\n");
        for(int i = 0; i < T.size(); i++){
            source.append(T.get(i));
        System.out.println(T.get(i));
        }

        for(int i = 0; i < aggregation.size(); i++){
            source.append("\n\n"+aggregation.get(i)+"\n\n");
        }

        // realations 
        for (Map.Entry<String, String> entry : mapExtend.entrySet()) {
            source.append("\n\n"+entry.getValue()+" <|-- "+entry.getKey());
        }
        for (Map.Entry<String, List<String>> entry : mapImplement.entrySet()) {
            for (String s : entry.getValue()) {
               source.append("\n\n"+s+" <|.. "+entry.getKey()); 
            }
            
        }

        source.append("\n\n@enduml");
        // check output exist, else make one 
        makeOutputFolder();
        // output either text or image 
        writeUMLText(source.toString(), "out.txt");
        writeUMLImage(source.toString(), "out.png");
       
        //System.out.println(mapExtend);
        //System.out.println(mapImplement);
    }
*/
 
    public Parser(File[] names, File save, String u, String p)throws FileNotFoundException, ParseException, IOException{
        f1 = u;
        f2 = p;
	System.out.println(f1);
	System.out.println(f2);

        files = names;
        fileSave = save;
        fileName = fileSave.getPath();
        T = new ArrayList<Tokenizer>();
        //aggregation = new ArrayList<String>();
        relation = new ArrayList<Relation>();
        doWork(); // *** UNCOMMENT LATER (SET MAIN class as PARSER )
    }



 // UNCOMMENT LATER (SET MAIN class as PARSER )
    public static void doWork()throws FileNotFoundException, ParseException, IOException{
       for(File file: files){
                       
            String testClass = file.toString();
            FileInputStream inputStream = new FileInputStream(new File(testClass)); 
            // parse the file 
            CompilationUnit cu = JavaParser.parse(inputStream); 
            new MethodVisitor().visit(cu, null);
            
            //break; 
        }
        
        if(f2.equals("plantuml")){
        // prepare output 
        StringBuilder source = new StringBuilder(); 
        source.append("@startuml\n\n");
        System.out.println("\n\n\n\n\n");
        for(int i = 0; i < T.size(); i++){
            source.append(T.get(i));
        System.out.println(T.get(i));
        }

        //for(int i = 0; i < aggregation.size(); i++){
            //source.append("\n\n"+aggregation.get(i)+"\n\n");
        //}

        for(int i = 0; i < relation.size(); i++){
            source.append("\n"+relation.get(i).toString()+"\n");
        }


        // realations 
        for (Map.Entry<String, String> entry : mapExtend.entrySet()) {
            source.append("\n\n"+entry.getValue()+" <|-- "+entry.getKey());
        }
        for (Map.Entry<String, List<String>> entry : mapImplement.entrySet()) {
            for (String s : entry.getValue()) {
               source.append("\n\n"+s+" <|.. "+entry.getKey()); 
            }
            
        }

        source.append("\n\n@enduml");
        // check output exist, else make one 
        makeOutputFolder();
        // output either text or image 
        writeUMLTextPLANTUML(source.toString(), "out.txt");
        writeUMLImage(source.toString(), "out.png");
       
        //System.out.println(mapExtend);
        //System.out.println(mapImplement);
        }


        if(f1.equals("yuml")){
        // generate yuml image
        String imageUrl = "https://yuml.me/diagram/class/";
        String url = writeYumlImage();
        imageUrl += url; 
        System.out.println(imageUrl);
        String filename = OUT_DIR + "yuml-out.png";
        saveYUMLImage(imageUrl, filename);
        saveYUMLImage(imageUrl, (String)(fileSave+"-yuml.png"));
        }
    }

    public static void saveYUMLImage(String imageUrl, String filename) throws IOException {

         URL url = new URL(imageUrl);
         BufferedInputStream in = new BufferedInputStream(url.openStream());
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         byte[] buf = new byte[1024];
         int n = 0;
         while (-1!=(n=in.read(buf)))
         {
            out.write(buf, 0, n);
         }
         out.close();
         in.close();
         byte[] response = out.toByteArray();
     
         FileOutputStream fos = new FileOutputStream(filename);
         fos.write(response);
         fos.close();
    }
   
   private static String writeYumlImage(){
     
       String url = ""; 
       for(int i = 0; i < T.size(); i++){
           url += (T.get(i).toStringYUML());
           url += ",";  
       }
        
       // relations 
       for (Map.Entry<String, String> entry : mapExtend.entrySet()) {
           if(!containsRelation(entry.getValue(), entry.getKey(), "^")){
               url += ("["+entry.getValue()+"]^["+entry.getKey()+"],"); 
           }
       }
     
       url += ","; 
       for (Map.Entry<String, List<String>> entry : mapImplement.entrySet()) {
           for (String s : entry.getValue()) {
               if(!containsRelation(s, entry.getKey(), "^")){
                   url += ("["+entry.getKey()+"]-.-^["+s+"],");//uses-.->
               }
               
           }
       }
       
       for(int i = 0; i < relation.size(); i++){
           url += relation.get(i).toStringYUML();
           url += ","; 
       }
       
       try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } 
       return url; 
   }
   
   private static boolean containsRelation(String a, String b, String icon){
       for(int i = 0; i < relation.size(); i++){
           Relation rel = relation.get(i); 
           if(rel.compareR(a, b)){
               rel.addSymbol(icon); 
               return true; 
           }
       }
       return false; 
   }
 

    
    
    private static void writeUMLImage(String source, String filename)
    throws IOException{
        SourceStringReader reader = new SourceStringReader(source);
        
        final ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        reader.generateImage(bStream);
        byte[] data = bStream.toByteArray();
        InputStream in = new ByteArrayInputStream(data);
        BufferedImage img = ImageIO.read(in);
        //outputing png file in both selected and default direstory
        ImageIO.write(img, "png", new File(OUT_DIR + filename));
        ImageIO.write(img, "png", new File((String)(fileSave + "-plantuml.png")));
        bStream.close();
    }

    
    private static class MethodVisitor extends VoidVisitorAdapter<Void> implements Opcode{

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            newToken();
            String className = n.getNameAsString();
            System.out.println("*****CLASS:"+n.getNameAsString() + " " + n.getModifiers());
            
            // is the class being parsed abstract class or an interface 
            System.out.println(n.isInterface());
            System.out.println(n.isAbstract());
            
            // get extended && implemented classes  
            if(n.getExtendedTypes().size() > 0 && !mapExtend.containsKey(className)){
                mapExtend.put(className, n.getExtendedTypes().get(0).getNameAsString()); 
            }
            
            if(n.getImplementedTypes().size() > 0){
                for(int i = 0 ;i < n.getImplementedTypes().size(); i ++){
                    String cname = n.getImplementedTypes().get(i).getNameAsString(); 
                    List<String> list = (mapImplement.containsKey(className)) ? mapImplement.get(className) : new ArrayList<>(); 
                    list.add(cname); 
                    mapImplement.put(className, list); 
                }
            }

            // if current class existed in previous class
            // add aggreagation of classes
            //for(int i = 0; i <= index; i++){
                //if(T.get(i).ifExists(className)){
                    //aggregation.add(T.get(i).className + " <-- " + className);
                //}
            //}
            int count = 0;
            for(int i = 0; i <= index; i++){
                if((count = T.get(i).ifExists(className)) > 0){
                    boolean exists = false;
                    for(int j = 0; j < relation.size(); j++){
                        if(relation.get(j).compareR(T.get(i).className, className)){
                            relation.get(j).changeRelation("--");
                            exists = true;
                        }
                    }
                    if(!exists){
                        newRelation();
                        //System.out.println("\n\n#############"+count+"############\n\n");
                        if(count == 1){
                            relation.get(relation.size()-1).add(T.get(i).className, "\"1\" ", "-->", "\"1\" ", className);
                        }
                        else{
                            relation.get(relation.size()-1).add(T.get(i).className, "\"1\" ", "-->", "\"n\" ", className);
                        }
                    }
                }
            }



            T.get(++index).classType = "class";
            T.get(index).className = className;
            T.get(index).classAccess = getAccssCode(n.getModifiers()); 
            super.visit(n, arg);
        }

        // visit field 
        @Override
        public void visit(FieldDeclaration n, Void arg) {
            Iterator<VariableDeclarator> iterator = n.getVariables().iterator(); 
            while(iterator.hasNext()){
                VariableDeclarator declarator = iterator.next(); 
                T.get(index).addVars(declarator.getNameAsString()+": "+declarator.getType().toString(), 
                        getAccssCode(n.getModifiers()));
                for(int i = 0; i <= index; i++){
                System.out.println("\n"+declarator.getType().toString()+" contains."+T.get(i).className+"\n");
                    if(declarator.getType().getChildNodes().size() > 1 && declarator.getType().toString().contains(T.get(i).className)){
                        System.out.println("passed\n");
                        boolean flag = true;
                        for(int j = 0; j < relation.size(); j++){
                            if(relation.get(j).toString().contains(T.get(T.size()-1).className) && relation.get(j).toString().contains(T.get(i).className) ){
                                flag = false;
                            }
                        }
                        if(flag){
                            newRelation();
                            relation.get(relation.size()-1).add(T.get(T.size()-1).className, "\"1\" ", "-->", "\"n\" ", T.get(i).className);
                        }
                    }
                }

            }


            super.visit(n, arg);
        }
        

        @Override
        public void visit(VariableDeclarator n, Void arg) {
            //System.out.println(n.getType()+":"+n.getNameAsString());
            int vCount = 0;

            for(int i = 0; i <= index; i++){
                if((vCount = T.get(i).ifExists(n.getType().toString())) > 0){
                    for(int j = 0; j < relation.size(); j++){
                        if(relation.get(j).compareR(T.get(i).className, n.getType().toString())){
                            relation.get(j).changeRelation("--");

                            if(vCount > 1){
                                relation.get(j).changeLeft("\"m\" ");
                            }
                        }
                    }
                }
            }
            super.visit(n, arg);
        }
        
        // visit constructor/method
        @Override
        public void visit(ConstructorDeclaration n, Void arg) {
            //System.out.println(n.getModifiers() + " " + n.getDeclarationAsString(false, false, false));
            T.get(index).addMethods(n.getDeclarationAsString(false, false, false), getAccssCode(n.getModifiers()));
            super.visit(n, arg);
        }

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            //System.out.println(n.getModifiers() + " " + n.getDeclarationAsString(false, false, false));
            T.get(index).addMethods(n.getDeclarationAsString(false, false, false), 
            getAccssCode(n.getModifiers()));
            super.visit(n, arg);
        }
 
        
    }
    
    private static int getAccssCode(EnumSet<Modifier> modifiers){
        int access = 0; 
        if(modifiers.contains(Modifier.PUBLIC)){
            access |= Opcode.ACC_PUBLIC;
        }
        if(modifiers.contains(Modifier.PRIVATE)){
            access |= Opcode.ACC_PRIVATE;
        }
        if(modifiers.contains(Modifier.PROTECTED)){
            access |= Opcode.ACC_PROTECTED;
        }
        if(modifiers.contains(Modifier.STATIC)){
            access |= Opcode.ACC_STATIC;
        }
        if(modifiers.contains(Modifier.FINAL)){
            access |= Opcode.ACC_FINAL;
        }
        
        if(modifiers.contains(Modifier.SYNCHRONIZED)){
            access |= Opcode.ACC_SYNCHRONIZED;
        }
        if(modifiers.contains(Modifier.ABSTRACT)){
            access |= Opcode.ACC_ABSTRACT;
        }
        
        if(modifiers.contains(Modifier.VOLATILE)){
            access |= Opcode.ACC_VOLATILE;
        }
        if(modifiers.contains(Modifier.TRANSIENT)){
            access |= Opcode.ACC_TRANSIENT;
        }
        return access; 
    }

 public static File[] fileFinder( String dirname){
        File dir = new File(dirname);
        return dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("java");
            }
        });
    }

    
    //Writing PlantUML
    
    private static void writeUMLTextPLANTUML(String source, String filename)
    throws IOException{
        //saving it in default output directory
        FileWriter writer = new FileWriter(OUT_DIR+filename , false);
        writer.write(source);
        writer.close();
        //save on the selected spot
        
        FileWriter writers = new FileWriter((String)(fileSave+".txt"),false);
        writers.write(source);
        writers.close();
        
        
    }
    
    private static void makeOutputFolder(){
        File file = new File(OUT_DIR); 
        if(!file.exists()){
            file.mkdir(); 
        } 
    }
}

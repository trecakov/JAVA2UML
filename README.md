Overview

    We designed software that converts Java source code into visual UML class diagram. Our software extracts
    classes, variables, methods, along with their access modifiers from Java code and turns them to usable 
    tokens that allow us to implement a diagram visualizer. Moreover, it enables us to consider association 
    relationship as well as multiplicity.
    
    We developed a fast and user-friendly software with a startup GUI that guides users through the process 
    of generating a UML diagram. Our product can run both online(yUML) and offline(Planuml), depending which 
    option is chosen. More, our program accepts multiple Java source files with multiple classes in it. Our 
    software does not accept jar files, however, there is a description on how to get java source files from 
    jar file in the menu bar.
    
    We designed our software to output a UML class diagram in png format. This way user will always have UML 
    class diagram with a good layout. Moreover, our software uses the UML API style, which means that it has 
    aggregation, association, inheritance, dependency, composition and realization. After testing our program 
    on some Java source files and comparing them to the UML class diagrams, we can say that our software is 
    reliable. We did not implement the editing feature, because we would like our users to get a final 
    product. However, in the menu bar we included a description on how to manually edit our output.


Design

    Our software follows simple design. First, we have a user-friendly startup GUI with different options 
    such as save, select, apply, etc. Then depending on which options user selected, we pass those parameters 
    to our Parser. We used open source Java parser to extract classes, variables, methods, along with their 
    access modifiers from Java code, turn them into usable tokens, and store them as a Tokenizer instance. 
    This also enables us to consider relationships like, generalization (which relates to the extends keyword 
    in Java), and realization (which relates to the implements keyword in Java). After getting usable tokens, 
    we go through them and generate a string that holds all needed information to generate a text file. 
    Moreover, we implemented a few methods and classes, like Relation and Item to handle relationships. The 
    other classes are just there to help our program function as it is. Further, we generated png file by 
    passing our text file to Plantuml and yUML open source generator. The Plantuml and yUML generators are 
    handling our layout. Our software allows user to select where it wants to save the text and png file. 
    Moreover, we save a copy of it in the Java2UML directory.

To run compile and run use command:
    ant run

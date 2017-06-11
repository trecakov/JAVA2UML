
public class Relation {
  private String classA;
  private String classB;
  private String relation;
  private String leftNum;
  private String rightNum;
  private String symbol = "";

  public Relation() {
    classA = "";
    classB = "";
    relation = "";
    leftNum = "";
    rightNum = "";
  }

  public void add(String A, String Le, String R, String Ri, String B) {
    classA = A;
    relation = R;
    classB = B;
    leftNum = Le;
    rightNum = Ri;
  }

  public void changeRelation(String R) {
    relation = R;
  }

  public void changeLeft(String L) {
    leftNum = L;
  }

  public boolean compareR(String A, String B) {
    if (A.contains(classA) && B.contains(classB)) {
      return true;
    }
    if (A.contains(classB) && B.contains(classA)) {
      String t = classA;
      classA = classB;
      classB = t;
      return true;
    }
    return false;
    // return (A.contains(classA) && B.contains(classB) ||
    // A.contains(classB) && B.contains(classA));
  }

  public String toString() {
    return classA + " " + leftNum + relation + " " + rightNum + classB;
  }

  public void addSymbol(String s) {
    this.symbol += s;
  }

  public String toStringYUML() {
    return "[" + classA + "]" + leftNum.trim().replaceAll("\"", "") + this.relation.replaceAll("--", "-")
        + rightNum.trim().replaceAll("\"", "") + "[" + classB + "]";
  }

}

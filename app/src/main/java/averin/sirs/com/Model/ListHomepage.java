package averin.sirs.com.Model;

public class ListHomepage {

    private String txt1,txt2, txt3, txt4, txt5, txt6, txt7;

    public ListHomepage(String txt1, String txt2, String txt3, String txt5) {
        this.txt1  = txt1;
        this.txt2  = txt2;
        this.txt3  = txt3;
//        this.txt4  = txt4;
        this.txt5  = txt5;
//        this.txt6  = txt6;
//        this.txt7  = txt7;

    }

    public String getTxt1() {return txt1;}
    public String getTxt2() {return txt2;}
    public String getTxt3() {return txt3;}
//    public String getTxt4() {return txt4;}
    public String getTxt5() {return txt5;}
//    public String getTxt6() {return txt6;}
//    public String getTxt7() {return txt7;}

    public void setTxt1(String txt1) {
        this.txt1 = txt1; }
}

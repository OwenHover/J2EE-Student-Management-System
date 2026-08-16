package cn.zust.demo.entity;

public class Student {

    private String sno;
    private String sname;
    private String gender;
    private String classname;
    private String collegename;
    private String country;
    private String depart;
    private String psw;


    public Student() {
    }


    public Student(String sno, String sname, String gender,
                   String classname, String collegename,
                   String country, String depart, String psw) {
        this.sno = sno;
        this.sname = sname;
        this.gender = gender;
        this.classname = classname;
        this.collegename = collegename;
        this.country = country;
        this.depart = depart;
        this.psw = psw;
    }

    // Getter and Setter methods

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getCollegename() {
        return collegename;
    }

    public void setCollegename(String collegename) {
        this.collegename = collegename;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sno='" + sno + '\'' +
                ", sname='" + sname + '\'' +
                ", gender='" + gender + '\'' +
                ", classname='" + classname + '\'' +
                ", collegename='" + collegename + '\'' +
                ", country='" + country + '\'' +
                ", depart='" + depart + '\'' +
                ", psw='" + psw + '\'' +
                '}';
    }
}

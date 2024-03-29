package selab.mvc.models.entities;

import selab.mvc.models.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Student implements Model {
    private String name;
    private String studentNo;
    private List<StudentCourse> courses;

    public Student() {
        this.courses = new ArrayList<>();
    }

    @Override
    public String getPrimaryKey() {
        return this.studentNo;
    }

    public void setName(String value) { this.name = value; }
    public String getName() { return this.name; }

    public void setStudentNo(String value) {
        if (!validateStudentNo(value))
            throw new IllegalArgumentException("The format is not correct");

        this.studentNo = value;
    }
    public String getStudentNo() { return this.studentNo; }

    public float getAverage() {
        if (courses.size() == 0)
            return 0;
        float sum = 0;
        for (StudentCourse course : courses) {
            sum += Float.parseFloat(course.getPoints());
        }
        return sum / courses.size();
    }

    public String getCourses() {
        String result = "";
        for (StudentCourse course : courses) {
            result = result.concat(course.getCourse().getTitle()).concat(",");
        }
        return result;
    }

    /**
     *
     * @param studentNo Student number to be checeked
     * @return true, if the format of the student number is correct
     */
    private boolean validateStudentNo(String studentNo) {
        Pattern pattern = Pattern.compile("^[8-9]\\d{7}$");
        return pattern.matcher(studentNo).find();
    }

    public void addCourse(StudentCourse studentCourse) {
        if (!this.courses.contains(studentCourse))
            this.courses.add(studentCourse);
    }

}

package selab.mvc.models.entities;

import selab.mvc.models.Model;

public class StudentCourse implements Model {

    private Course course;
    private Student student;
    private String points;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public String getPrimaryKey() {
        return getStudent().getPrimaryKey().concat(getCourse().getPrimaryKey());
    }
}

package selab.mvc.models.entities;

import selab.mvc.models.Model;
import sun.misc.Regexp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Course implements Model {
    private String title;
    private String courseNo;
    private String startTime = null;
    private String endTime = null;
    private Weekday weekday;
    private List<StudentCourse> students;

    public Course() {
        this.students = new ArrayList<>();
    }

    @Override
    public String getPrimaryKey() {
        return this.courseNo;
    }

    public void setTitle(String value) { this.title = value; }
    public String getTitle() { return this.title; }

    public void setCourseNo(String value) {
        if (!validateCourseNo(value))
            throw new IllegalArgumentException("Format is not correct");

        this.courseNo = value;
    }
    public String getCourseNo() { return this.courseNo; }

    public void setStartTime(String value) {
        if (!validateTime(value))
            throw new IllegalArgumentException("Invalid time format.");

        if (this.endTime != null && compareTime(value, this.endTime) != -1)
            throw new IllegalArgumentException("The start time cannot be past the end time.");

        this.startTime = value;
    }
    public String getStartTime() { return this.startTime; }

    public void setEndTime(String value) {
        if (!validateTime(value))
            throw new IllegalArgumentException("Invalid time format");

        if (this.startTime != null && compareTime(value, this.startTime) != 1)
            throw new IllegalArgumentException("The end time cannot be earlier than the start time.");

        this.endTime = value;
    }

    public String getEndTime() { return this.endTime; }

    public void setWeekday(Weekday value) {
        this.weekday = value;
    }

    public String getWeekday() {
        return this.weekday.name();
    }

    public float getAverage() {
        if (students.size() == 0)
            return 0;
        float sum = 0;
        for (StudentCourse student : students) {
            sum += Float.parseFloat(student.getPoints());
        }
        return sum / students.size();
    }

    public String getStudents() {
        String result = "";
        for (StudentCourse student : students) {
            result = result.concat(student.getStudent().getName()).concat(",");
        }
        return result;
    }

    /**
     *
     * @param value The value to be validated as course number
     * @return true, if value is in a correct format
     */
    private boolean validateCourseNo(String value) {
        Pattern pattern = Pattern.compile("^\\d{5}-\\d$");
        return pattern.matcher(value).find();
    }

    /**
     *
     * @param value The time to be checked
     * @return true, if the format of the input is appropriate for a time, like
     */
    private boolean validateTime(String value) {
        Pattern pattern = Pattern.compile("^((0|1)\\d|2[0-4]):([0-5]\\d)$");
        return pattern.matcher(value).find();
    }

    /**
     *
     * @param time1 First time
     * @param time2 Second time
     * @return If time1 > time2, returns 1, if time1 < time2 returns -1, otherwise returns 0
     */
    private int compareTime(String time1, String time2) {
        int time1Hour = Integer.parseInt(time1.substring(0, 2));
        int time1Minute = Integer.parseInt(time1.substring(3, 5));
        int time2Hour = Integer.parseInt(time2.substring(0, 2));
        int time2Minute = Integer.parseInt(time2.substring(3, 5));

        if (time1Hour > time2Hour || (time1Hour == time2Hour && time1Minute > time2Minute))
            return 1;
        else if (time1Hour == time2Hour && time1Minute == time2Minute)
            return 0;
        else
            return -1;
    }

    public void addStudent(StudentCourse studentCourse){
        if (!this.students.contains(studentCourse))
            this.students.add(studentCourse);
    }
}

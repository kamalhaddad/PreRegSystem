package com.prereg.base;


import com.prereg.base.data.PreRegProto;

public final class Course {
    private PreRegProto.CourseData courseData;

    public Course(PreRegProto.CourseData courseData) {
        this.courseData = courseData;
    }

    public int getCRN() {
        return courseData.getCRN();
    }

    public int getSectionNumber() {
        return courseData.getSectionNumber();
    }

    public User getInstructor() {
        return new User(courseData.getInstructor());
    }

    public String getCourseName() {
        return courseData.getCourseName();
    }

    public String getTime() {
        return courseData.getTime();
    }

    public String getClassRoom() {
        return courseData.getClassRoom();
    }

    public int getCapacity() {
        return courseData.getCapacity();
    }

    public int getMaxCapacity() {
        return courseData.getMaxCapacity();
    }
}

package Core;

public class Course {
    private int CRN;
    private int SectionNumber;
    private String InstructorUserName;
    private String CourseName;
    private String Time;
    private String ClassRoom;
    private int capacity;
    private int max_capacity;

    Course(int CRN, int SectionNumber, String InstructorUserName, String CourseName, String Time, String ClassRoom, int capacity, int max_capacity){
        this.CRN = CRN;
        this.SectionNumber = SectionNumber;
        this.InstructorUserName =InstructorUserName;
        this.CourseName = CourseName;
        this.Time =Time;
        this.ClassRoom = ClassRoom;
        this.capacity = capacity;
        this.max_capacity = max_capacity;
    }

    @Override
    public String toString() {

        return new String(CRN + "   " + SectionNumber + "                    " + InstructorUserName + "         " + CourseName + "  " + Time + "    " + ClassRoom +"       "+ capacity+"   "+max_capacity);
    }
}

package com.kckj.student.service;

import com.kckj.student.domain.Student;

/**
*业务层
*1.响应view的调用和
 * 2.完成CRUD的操作
 */
public class StudentService {
    private Student[] students;
    private int studentNums = 1;
    private  int idCound = 1;

    //构造器：new StudentService  对象时，指定数组大小（最大学生数量）
    public StudentService(int size){
        students = new Student[size];
        students[0] = new Student(1,"张三","男","软件工程","13800138001","在组");
    }

    //add：添加新成员，返回boolean 表示是否添加成员
    public boolean add(Student newStudent) {
        //1.判断数组是否已满
        if(studentNums == students.length){
            System.out.println("数组已满，不能继续存入");
            return false;
        }
        //把newStudent加入数组
        students[studentNums++] =newStudent;
        //设计一个id自增长的机制,然后更新newStudent的id
        //注意：这里修改的是同一个对象，数组里的元素也会跟着变(引用!)
        newStudent.setId(++idCound);
        return true;
    }

    //findById方法：根据编号查找成员，返回Student对象或者null
    public  Student findById(int findId) {
        //遍历数组，注意只遍历有效的成员(studentNums个)
        for (int i = 0; i < studentNums; i++) {
            if (findId == students[i].getId()) {
                return students[i];
            }
        }
        return null;
    }
    //del方法：根据编号删除一个成员信息，返回boolean表示是否删除成功
    public boolean del(int delId) {
    //先找到要删除的成员对应的数组下标
    //注意：下标和成员的编号不是一回事！
        int index = -1;
        for (int i = 0; i < studentNums; i++) {
            if (delId == students[i].getId()) {
                index = i;
            }
        }
    if (index == -1) {//说明delId在数组中不存在
        return false;
    }
    //找到了，把index后面的元素依次前移一位(覆盖掉要删除的元素)
        for (int i = index; i < studentNums - 1; i++) {
            students[i] = students[i + 1];
        }
    //把最后一个有效位置设置为null，并把计数减1
    students[--studentNums] = null;
        return true;
    }
    //list方法：返回所有成员
    public Student[] list() {
        return students;
    }
}




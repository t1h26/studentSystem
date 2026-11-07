package com.kckj.student.view;

import com.kckj.student.domain.Student;
import com.kckj.student.service.StudentService;
import com.kckj.student.utils.Utility;

public class StudentView {
    private boolean loop = true;
    private char key = ' ';
    private StudentService studentService = new StudentService(10);

    //显示主菜单
    public void mainMenu() {
        do {
            System.out.println("\n======科创空间学生管理系统菜单==========");
            System.out.println("\t\t\t1 新 增 成 员");
            System.out.println("\t\t\t2 查 找 成 员");
            System.out.println("\t\t\t3 删 除 成 员");
            System.out.println("\t\t\t4 修 改 成 员 信 息");
            System.out.println("\t\t\t5 成 员 列 表");
            System.out.println("\t\t\t6 退      出");
            System.out.print("请输入你的选择(1-6): ");
            key = Utility.readChar();
            switch (key) {
                case '1':
                    addStudent();
                    break;
                case '2':
                    delStudent();
                    break;
                case '3':
                    update();
                    break;
                case '4':
                    listStudents();
                    break;
                case '5':
                    break;
                case '6':
                    exit();
                    break;
            }
        } while (loop);
    }

    //展示成员列表
    public void listStudents() {
        System.out.println("========成员列表========");
        System.out.println("编号\t\t姓名\t\t性别\t\t专业\t\t电话\t\t状态(在组/退出)");
        Student[] students = studentService.list();
        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {//如果为null，就不用再显示了
                break;
            }
            System.out.println(students[i]);
        }
        System.out.println("=============成员列表显示完毕============");
    }

    //添加学生
    public void addStudent() {
        System.out.println("======添加成员=====");
        System.out.print("姓名：");
        String name = Utility.readString(8);
        System.out.print("性别：");
        String gender = Utility.readString(2);
        System.out.print("专业:");
        String major = Utility.readString(10);
        System.out.print("电话：");
        String phone = Utility.readString(12);
        System.out.print("状态(在组/退出)：");
        String state = Utility.readString(4);
        //创建一个新的student对象，注意id是由系统分配的（先给0)
        Student newstudent = new Student(0, name, gender, major, phone, state);
        if (studentService.add(newstudent)) {
            System.out.println("=======添加成员成功=========");
        } else {
            System.out.println("=======添加成员失败=========");
        }
    }
    //查找学生：findStuden
    public void findStudent() {
        System.out.println("=======查找成员信息=========");
        System.out.print("请输入要查找的编号：");
        int findId = Utility.readInt();
        //调用service层的findById方法
        Student student = studentService.findById(findId);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("=======查找的成员编号不存在=========");
        }
    }
    //编写delStudent()：接收输入的id，调用Service的del方法
    public void delStudent() {
        System.out.println("=======删除成员信息=========");
        System.out.print("请输入待删除成员的编号（-1退出)：");
        int delId = Utility.readInt();
        if (delId == -1) {
            System.out.println("=======放弃删除成员信息=========");
            return;
        }
        //注意该方法本身就有循环判断的逻辑，必须输入Y或N
        char choice = Utility.readConfirmSelection();
        if (choice == 'Y') {//真的删除
            if (studentService.del(delId)) {
                System.out.println("=======删除成员信息成功==========");
            } else {
                System.out.println("=======成员编号不存在，删除失败==========");
            }
        } else {
            System.out.println("=======放弃删除成员信息=========");
        }
    }

    //根据id修改成员信息
    public void update () {
        System.out.println("========修改成员信息=========");
        System.out.println("请选择待修改成员编号(-1)表示退出)");
        int updateId = Utility.readInt();
        if (updateId == -1) {
            System.out.println("========你放弃修改成员信息=========");
            return;
        }
        //根据输入的updateId，查找对象
        //注意：findById返回的是引用类型（就是数组里的元素)
        //所以下面student.setXxx〇修改的就是studentservice中students数组的元素！
        Student student = studentService.findById(updateId);
        if (student == null) {
            System.out.println("========修改成员编号不存在=========");
            return;
        }

        //提示修改，直接回车表示不修改该项信息
        System.out.print("姓名(" + student.getName() + ")： ");
        String name = Utility.readString(8, "");
        if (!"".equals(name)) {
            student.setName(name);
        }

        System.out.print("性别(" + student.getGender() + ")： ");
        String gender = Utility.readString(2, "");
        if (!"".equals(gender)) {
            student.setGender(gender);
        }
        System.out.print("专业(" + student.getMajor() + ")： ");
        String major = Utility.readString(10, "");
        if (!"".equals(major)) {
            student.setMajor(major);
        }

        System.out.print("电话(" + student.getPhone() + ")： ");
        String phone = Utility.readString(12, "");
        if (!"".equals(phone)) {
            student.setPhone(phone);
        }
        System.out.print("状态(" + student.getState() + ")： ");
        String state = Utility.readString(4, "");
        if (!"".equals(state)) {
            student.setState(state);
        }
        System.out.println("========修改成员信息成功=========");
    }

    //完成退出确认
    public void exit() {
        //使用utility提供的方法，完成Y/N确认
        char c = Utility.readConfirmSelection();
        if (c == 'Y') {
            loop = false;
        }
    }
}

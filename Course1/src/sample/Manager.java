package sample;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

//import org.w3c.dom.Text;
import javax.management.DescriptorRead;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.concurrent.Callable;
public class Manager extends  GridPane{
    static String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Course;characterEncoding=UTF-8";
    static String username = "TianLe";
    static String password = "LHS19970420";

    public Manager() {
        home();
    }

    public void home() {
        getChildren().clear();
        setVgap(5);
        setHgap(5);
        Image image = new Image("file:image/Manager.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(500);
        imageView.setFitWidth(500);
        add(imageView,0,0);
        TextField pow = new TextField();
        pow.setMaxWidth(60);
        add(new Text("登录密码"),0,1);
        add(pow,0,2);
        Button ok = new Button("确认");
        add(ok,0,3);
        if(!pow.getText().equals("123456"))
        ok.setOnAction(e -> {
            log();
        });

    }

    public void log() {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        Button stuBt = new Button("学生管理");
        Button teaBt = new Button("教师管理");
        Button couBt = new Button("课程管理");
        Button reBt = new Button("返回上一级");

        add(stuBt,1,1);
        add(teaBt,1,2);
        add(couBt,1,3);
        add(reBt,1,4);

        stuBt.setOnAction(e -> {
            Student();
        });

        teaBt.setOnAction(e1 -> {
            Teacher();
        });

        couBt.setOnAction(e2 -> {
            Course();
        });

        reBt.setOnAction(e3 -> {
            home();
        });
    }

    public void Student() {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        Button addStu = new Button("添加学生");
        Button delete = new Button("删除学生");
        Button update = new Button("修改学生信息");
        Button select = new Button("查看学生信息");
        Button reBt = new Button("返回上一级");

        add(addStu,1,1);
        add(delete,1,2);
        add(update,1,3);
        add(select,1,4);
        add(reBt,1,5);

        addStu.setOnAction(e -> {
            addStu();
        });

        delete.setOnAction(e1 -> {
            deleteStu();
        });

        update.setOnAction(e2 -> {
            changeStu();
        });

        select.setOnAction(e3 -> {
            select();
        });

        reBt.setOnAction(e4 -> {
            log();
        });

    }

    public void addStu() {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        TextField no = new TextField();
        TextField name = new TextField();
        TextField sex = new TextField();
        TextField step = new TextField();
        TextField pro = new TextField();
        TextField clas = new TextField();
        TextField pow = new TextField();

        add(new Text("学号"),1,1);
        add(no,2,1);
        add(new Text("姓名"),1,2);
        add(name,2,2);
        add(new Text("性别"),1,3);
        add(sex,2,3);
        add(new Text("学院"),1,4);
        add(step,2,4);
        add(new Text("专业"),1,5);
        add(pro,2,5);
        add(new Text("班级"),1,6);
        add(clas,2,6);
        add(new Text("密码"),1,7);
        add(pow,2,7);

        Button ok = new Button("确定");
        Button reBt = new Button("返回上一级");
        add(reBt,2,8);
        add(ok,1,8);
        ok.setOnAction(e -> {
            String No = no.getText();
            String sql1 = "select * from Student where 学号 = " + No;
            try {
                Connection conn = DriverManager.getConnection(url,username,password);
                Statement sta = conn.createStatement();
                ResultSet result = sta.executeQuery(sql1);
                if(result.next()) {
                    add(new Text("该学号已经存在"),2,10);
                }
                else {
                    String sql = "insert into Student values(?,?,?,?,?,?,?)";

                    try {
                        Connection conn1 = DriverManager.getConnection(url,username,password);
                        PreparedStatement pre = conn1.prepareStatement(sql);

                        pre.setString(1,No);
                        pre.setString(2,name.getText());
                        pre.setString(3,sex.getText());
                        pre.setString(4,step.getText());
                        pre.setString(5,pro.getText());
                        pre.setString(6,clas.getText());
                        pre.setString(7,pow.getText());
                        pre.executeUpdate();
                        add(new Text("添加成功"),2,10);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        });

        reBt.setOnAction(e1 -> {
            Student();
        });

    }

    public void deleteStu() {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        TextField no = new TextField();
        TextField name = new TextField();

        add(new Text("学号"),1,1);
        add(no,2,1);
        add(new Text("姓名"),1,2);
        add(name,2,2);

        Button ok = new Button("确定");
        Button reBt = new Button("返回上一级");
        add(ok,1,3);
        add(reBt,2,3);
        ok.setOnAction(e -> {
            String No = no.getText();
            String Name = name.getText();
            String sql = "select * from Student where 学号 = "+ No + " and 姓名 = " + "'" + Name + "'";

            try {
                Connection conn1 = DriverManager.getConnection(url,username,password);
                Statement sta = conn1.createStatement();
                ResultSet result = sta.executeQuery(sql);
                if(result.next()) {
                    String sql1 = "delete from Student where 学号 = " + No;
                    PreparedStatement pre = conn1.prepareStatement(sql1);
                    pre.executeUpdate();

                    String sql2 = "delete from SC where 学号 = " + No;
                    PreparedStatement pre1 = conn1.prepareStatement(sql2);
                    pre1.executeUpdate();

                    add(new Text("删除成功"),2,5);
                }
                else {
                    add(new Text("不存在此学生"),2,5);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        reBt.setOnAction(e1 -> {
            Student();
        });
    }

    public void changeStu() {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        TextField no = new TextField();
        add(new Text("学号"),1,1);
        add(no,2,1);
        Button ok = new Button("确定");
        Button reBt = new Button("返回上一级");

        add(ok,1,2);
        add(reBt,2,2);


        ok.setOnAction(e -> {
            String No = no.getText();
            String sql = "select * from Student where 学号 = " + No;

            try {
                Connection conn1 = DriverManager.getConnection(url,username,password);
                Statement sta = conn1.createStatement();
                ResultSet result = sta.executeQuery(sql);
                if(result.next()) {
                    TextField no1 = new TextField();
                    TextField name1 = new TextField();
                    TextField sex1 = new TextField();
                    TextField step1 = new TextField();
                    TextField pro1 = new TextField();
                    TextField cla1 = new TextField();

                    add(new Text("姓名"),1,5);
                    add(name1,2,5);
                    add(new Text("性别"),1,6);
                    add(sex1,2,6);
                    add(new Text("学院"),1,7);
                    add(step1,2,7);
                    add(new Text("专业"),1,8);
                    add(pro1,2,8);
                    add(new Text("班级"),1,9);
                    add(cla1,2,9);
                    Button bt = new Button("确认");
                    add(bt,1,10);
                    bt.setOnAction(e1 -> {
                        String sql1 = "update Student set 姓名 = ?,性别 = ?,学院 = ?,专业 = ?,班级 = ? where 学号 =  " + No;
                        try {
                            Connection conn2 = DriverManager.getConnection(url,username,password);
                            PreparedStatement pre = conn2.prepareStatement(sql1);
                            pre.setString(1,name1.getText());
                            pre.setString(2,sex1.getText());
                            pre.setString(3,step1.getText());
                            pre.setString(4,pro1.getText());
                            pre.setString(5,cla1.getText());
                            pre.executeUpdate();
                            add(new Text("修改成功"),2,12);
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                    });

                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        reBt.setOnAction(e1 -> {
            Student();
        });
    }

    public void select() {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        TextField no = new TextField();
        add(new Text("学号"),2,2);
        add(no,3,2);

        Button ok = new Button("确认");
        Button reBt = new Button("返回上一级");

        add(ok,2,3);
        add(reBt,3,3);


        ok.setOnAction(e -> {
            String no1 = no.getText();

            add(new Text("学号"),1,5);
            add(new Text("姓名"),2,5);
            add(new Text("性别"),3,5);
            add(new Text("学院"),4,5);
            add(new Text("专业"),5,5);
            add(new Text("班级"),6,5);

            String sql = "select * from Student where 学号 = " + no1;

            try {
                Connection conn1 = DriverManager.getConnection(url,username,password);
                Statement sta = conn1.createStatement();
                ResultSet result = sta.executeQuery(sql);

                int x = 1,y = 6;
                while(result.next()) {
                    add(new Text(result.getString(1)),x,y);
                    add(new Text(result.getString(2)),x + 1,y);
                    add(new Text(result.getString(3)),x + 2,y);
                    add(new Text(result.getString(4)),x + 3,y);
                    add(new Text(result.getString(5)),x + 4,y);
                    add(new Text(result.getString(6)),x + 5,y);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        reBt.setOnAction(e1 -> {
            Student();
        });
    }


    public void Teacher() {
        getChildren().clear();

        setHgap(5);
        setVgap(5);

        Button addTea = new Button("添加教师");
        Button deleteTea = new Button("删除教师");
        Button changeTea = new Button("修改教师信息");
        Button reBt = new Button("返回上一级");

        add(addTea,2,2);
        add(deleteTea,2,3);
        add(changeTea,2,4);
        add(reBt,2,5);

        addTea.setOnAction(e -> {
            addTea();
        });

        deleteTea.setOnAction(e1 -> {
            deleteTea();
        });

        changeTea.setOnAction(e2 -> {
            changeTea();
        });

        reBt.setOnAction(e3 -> {
            log();
        });

    }

    public void addTea() {

        getChildren().clear();
        setHgap(5);
        setVgap(5);
        TextField no = new TextField();
        TextField name = new TextField();
        TextField sex = new TextField();
        TextField course = new TextField();
        TextField job = new TextField();
        TextField pass = new TextField();

        add(new Text("工号"),1,1);
        add(no,2,1);
        add(new Text("姓名"),1,2);
        add(name,2,2);
        add(new Text("性别"),1,3);
        add(sex,2,3);
        add(new Text("教授课程"),1,4);
        add(course,2,4);
        add(new Text("职称"),1,5);
        add(job,2,5);
        add(new Text("密码"),1,6);
        add(pass,2,6);
        Button ok = new Button("确定");
        Button reBt = new Button("返回上一级");

        add(ok,2,9);
        add(reBt,3,9);

        ok.setOnAction(e -> {
            String sql = "insert into Teacher values(?,?,?,?,?,?)";
            try {
                Connection conn = DriverManager.getConnection(url,username,password);
                PreparedStatement pre = conn.prepareStatement(sql);
                pre.setString(1,no.getText());
                pre.setString(2,name.getText());
                pre.setString(3,sex.getText());
                pre.setString(4,course.getText());
                pre.setString(5,job.getText());
                pre.setString(6,pass.getText());
                pre.executeUpdate();

                add(new Text("添加成功"),3,13);

            } catch (SQLException e1) {
                add(new Text("已存在该教师信息"),3,13);
                e1.printStackTrace();
            }
        });

        reBt.setOnAction(e1 -> {
            Teacher();
        });
    }

    public void deleteTea() {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        TextField no = new TextField();

        add(new Text("工号"),2,2);
        add(no,3,2);

        Button ok = new Button("确定");
        Button reBt = new Button("返回上一级");

        add(ok,2,3);
        add(reBt,3,3);
        ok.setOnAction(e -> {
            String No = no.getText();
            String sql = "select * from SC where 任课教师 in (select 姓名 from Teacher where 工号 = " + No + ")";

            try {
                Connection conn = DriverManager.getConnection(url,username,password);
                Statement sta = conn.createStatement();
                ResultSet result = sta.executeQuery(sql);
                if(result.next()) {
                    add(new Text("已有学生选了该教师的课程，无法删除"),3,6);
                }
                else {
                    String sql1 = "delete Teacher where 工号 = " + No;
                    PreparedStatement pre = conn.prepareStatement(sql1);
                    pre.executeUpdate();
                    add(new Text("成功删除"),3,6);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        reBt.setOnAction(e1 -> {
            Teacher();
        });

    }

    public void changeTea() {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        TextField no = new TextField();
        TextField name = new TextField();
        TextField sex = new TextField();
        TextField course = new TextField();
        TextField job = new TextField();
        TextField pass = new TextField();

        add(new Text("工号"),1,1);
        add(no,2,1);
        add(new Text("姓名"),1,2);
        add(name,2,2);
        add(new Text("性别"),1,3);
        add(sex,2,3);
        add(new Text("教授课程"),1,4);
        add(course,2,4);
        add(new Text("职称"),1,5);
        add(job,2,5);
        add(new Text("密码"),1,6);
        add(pass,2,6);

        Button ok = new Button("确定");
        Button reBt = new Button("返回上一级");

        add(ok,2,7);
        add(reBt,3,7);

        ok.setOnAction(e -> {
            try {
                String sql = "update Teacher set 姓名 = ?,性别 = ?,教授课程 = ?,职称 = ?,密码 = ? where 工号 =  " + no.getText();
                Connection conn = DriverManager.getConnection(url,username,password);
                PreparedStatement pre = conn.prepareStatement(sql);
                pre.setString(1,name.getText());
                pre.setString(2,sex.getText());
                pre.setString(3,course.getText());
                pre.setString(4,job.getText());
                pre.setString(5,pass.getText());
                pre.executeUpdate();

                add(new Text("修改成功"),3,9);
            } catch (SQLException e1) {
                add(new Text("不存在该教师"),3,9);
                e1.printStackTrace();
            }
        });

        reBt.setOnAction(e1 -> {
            Teacher();
        });
    }

    public void Course() {
        getChildren().clear();

        setHgap(5);
        setVgap(5);


        Button addCou = new Button("添加课程");
        Button deleteCou = new Button("删除课程");
        Button changeCou = new Button("修改课程");
        Button reBt = new Button("返回上一级");


        add(addCou,1,1);
        add(deleteCou,1,2);
        add(changeCou,1,3);
        add(reBt,1,4);

        addCou.setOnAction(e -> {
            addCou();
        });

        deleteCou.setOnAction(e1 -> {
            deleteCou();
        });

        changeCou.setOnAction(e2 -> {
            changeCou();
        });

        reBt.setOnAction(e3 -> {
            log();
        });
    }

    public void addCou() {
        getChildren().clear();

        setHgap(5);
        setVgap(5);
        TextField no = new TextField();
        TextField name = new TextField();
        TextField num = new TextField();
        TextField time = new TextField();
        TextField teacher = new TextField();

        add(new Text("课程号"),1,1);
        add(no,2,1);
        add(new Text("课程名"),1,2);
        add(name,2,2);
        add(new Text("学分"),1,3);
        add(num,2,3);
        add(new Text("上课时间"),1,4);
        add(time,2,4);
        add(new Text("任课教师"),1,5);
        add(teacher,2,5);
        Button ok = new Button("确定");
        Button reBt = new Button("返回上一级");
        add(reBt,3,6);
        add(ok,2,6);

        ok.setOnAction(e -> {
            String sql = "insert into Course values(?,?,?,?,?)";
            try {
                Connection conn = DriverManager.getConnection(url,username,password);
                PreparedStatement pre = conn.prepareStatement(sql);
                pre.setString(1,no.getText());
                pre.setString(2,name.getText());
                pre.setString(3,num.getText());
                pre.setString(4,time.getText());
                pre.setString(5,teacher.getText());
                pre.executeUpdate();

                add(new Text("添加成功"),3,8);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        reBt.setOnAction(e1 -> {
            Course();
        });

    }

    public void deleteCou() {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        TextField no = new TextField();

        add(new Text("课程号"),1,1);
        add(no,2,1);

        Button ok = new Button("确定");
        Button reBt = new Button("返回上一级");

        add(ok,1,2);
        add(reBt,2,2);

        ok.setOnAction(e -> {
            String No = no.getText();
            String sql = "select * from Course where 课程号 = " + No;

            try {
                Connection conn = DriverManager.getConnection(url,username,password);
                Statement sta = conn.createStatement();
                ResultSet result = sta.executeQuery(sql);
                if(result.next()) {
                    String sql1 = "select * from SC where 课程号 = " + No;
                    Connection conn1 = DriverManager.getConnection(url,username,password);
                    Statement sta1 = conn1.createStatement();
                    ResultSet result1 = sta1.executeQuery(sql1);
                    if(result1.next()) {
                        add(new Text("已有学生选修该门课程,无法删除"),3,5);
                    }
                    else {
                        String sql2 = "delete Course where 课程号 = " + No;
                        PreparedStatement pre = conn1.prepareStatement(sql2);
                        pre.executeUpdate();

                        add(new Text("成功删除"),3,5);
                    }

                }
                else {
                    add(new Text("不存在该课程"),3,5);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        reBt.setOnAction(e1 -> {
            Course();
        });

    }

    public void changeCou() {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        TextField no = new TextField();
        TextField name = new TextField();
        TextField num = new TextField();
        TextField time = new TextField();

        add(new Text("课程号"),1,1);
        add(no,2,1);
        add(new Text("课程名"),1,2);
        add(name,2,2);
        add(new Text("学分"),1,3);
        add(num,2,3);
        add(new Text("上课时间"),1,4);
        add(time,2,4);

        Button ok = new Button("确定");
        Button reBt = new Button("返回上一级");

        add(ok,2,5);
        add(reBt,3,5);

        ok.setOnAction(e -> {
            try {
                String sql = "update Course set 课程名 = ?,学分 = ?,上课时间 = ? where 课程号 = ?";
                Connection conn = DriverManager.getConnection(url,username,password);
                PreparedStatement pre = conn.prepareStatement(sql);
                pre.setString(1,name.getText());
                pre.setString(2,num.getText());
                pre.setString(3,time.getText());
                pre.setString(4,no.getText());
                pre.executeUpdate();

                add(new Text("修改成功"),3,8);

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        reBt.setOnAction(e1 -> {
            Course();
        });

    }


}

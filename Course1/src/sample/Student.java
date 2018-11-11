package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.*;

public class Student extends GridPane{
    static String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Course;characterEncoding=UTF-8";
    static String username = "TianLe";
    static String password = "LHS19970420";

    public Student() {
       home();
    }

    public void home() {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        Image image = new Image("file:image/Student.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(530);
        imageView.setFitWidth(500);
        add(imageView,0,0);
        Button bt1 = new Button("登录");
        Button bt2 = new Button("忘记密码");

        GridPane pane = new GridPane();

        add(bt1,0,1);
        add(bt2,0,2);

        bt1.setOnAction(e -> log());
        bt2.setOnAction(e1 -> find());
    }

    public void log() {

        getChildren().clear();

        setHgap(5);
        setVgap(5);

        TextField id = new TextField();
        TextField password1 = new TextField();

        id.setAlignment(Pos.BOTTOM_RIGHT);
        password1.setAlignment(Pos.BOTTOM_RIGHT);

        add(new Label("学号:"),0,0);
        add(id,1,0);
        add(new Label("密码:"),0,1);
        add(password1,1,1);

        Button log = new Button("登录");
        Button re = new Button("返回上一级");

        add(log,1,4);
        add(re,2,4);
        log.setOnAction(e -> {
            String id1 = id.getText();
            String password2 = password1.getText();

            String sql = "select * from Student where " + "学号 = " + id1;
            try {
                Connection conn = DriverManager.getConnection(url,"TianLe","LHS19970420");
                Statement sta = conn.createStatement();
                ResultSet resul = sta.executeQuery(sql);
                if(resul.next()) {
                   String sql1 = "select 密码 from Student where " + "密码 = " + password2;
                   Statement sta1 = conn.createStatement();
                   ResultSet resul1 = sta1.executeQuery(sql1);
                   if(resul1.next()) {
                       String ps = resul1.getString(1);
                       if(!ps.equals(password2)) {
                               selecthome(id1);
                       }
                       }
                       else {
                           add(new Text("密码错误"),10,15);
                       }
                }
                else {
                    add(new Text("不存在此学号"),10,15);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        });
        re.setOnAction(e1 -> home());

    }

    public void selecthome(String id) {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        add(new Text("请选择你需要的操作"), 0, 0);
        Button selectBt = new Button("选课");
        Button delectBt = new Button("退选");
        Button viewBt = new Button("查看已选的课程");
        Button changeBt = new Button("修改密码");
        Button excanlBt = new Button("返回上一级");


        add(selectBt, 0, 1);
        add(delectBt, 0, 2);
        add(viewBt, 0, 3);
        add(changeBt, 0, 4);
        add(excanlBt, 0, 5);

        selectBt.setOnAction(e1 -> {
            selectCoure(id);
        });

        delectBt.setOnAction(e2 -> {
            delect(id);
        });

        viewBt.setOnAction(e3 -> {
            select(id);
        });

        changeBt.setOnAction(e4 -> {
            changpow(id);
        });

        excanlBt.setOnAction(e5 -> {
            log();
        });
    }

    public void find() {
            getChildren().clear();
            setHgap(5);
            setVgap(5);
            TextField id = new TextField();
            TextField pow = new TextField();
            TextField pow1 = new TextField();
            TextField name = new TextField();
            TextField Course = new TextField();

            add(new Text("学号"),1,1);
            add(id,2,1);
            add(new Text("姓名"),1,2);
            add(name,2,2);
            add(new Text("新密码"),1,3);
            add(pow,2,3);
            add(new Text("确认密码"),1,4);
            add(pow1,2,4);
            Button ok = new Button("确认");
            Button re = new Button("返回上一级");
            add(ok,2,5);
            add(re,3,5);
            ok.setOnAction(e1 -> {
                String Id = id.getText();
                String Name = name.getText();

                String sql = "select * from Student where 学号 = " + Id + " and 姓名 = " + "'" + Name + "'";
                try {
                    Connection conn1 = DriverManager.getConnection(url, username, password);
                    Statement sta = conn1.createStatement();
                    ResultSet result = sta.executeQuery(sql);
                    Text text = new Text("成功找回");
                    if (result.next()) {
                        String Pow = pow.getText();
                        String Pow1 = pow1.getText();
                        if (Pow.equals(Pow1)) {
                            String sql1 = "update Student set 密码 = " + Pow + "where 学号 = ?";
                            PreparedStatement pre = conn1.prepareStatement(sql1);
                            pre.setString(1, Id);
                            pre.executeUpdate();
                        } else {
                            text.setText("请重新确认密码");
                        }
                    }
                        else {
                            text.setText("                 ");
                            text.setText("信息不符，无法找回");
                        }

                    add(text,2,8);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            re.setOnAction(e2 -> {
                home();
            });

    }

    public void selectCoure(String id) {
        getChildren().clear();
        setHgap(5);
        setVgap(5);
        String sql = "select * from Course";
        try {
            Connection conn1 = DriverManager.getConnection(url,"TianLe","LHS19970420");
            Statement sta2 = conn1.createStatement();
            ResultSet result3 = sta2.executeQuery(sql);

            add(new Text("课程号"), 0, 1);
            add(new Text("课程名"), 1, 1);
            add(new Text("学分"), 2, 1);
            add(new Text("上课时间"), 3, 1);
            add(new Text("任课教师"),4,1);

            int x = 0, y = 2;
            while (result3.next()) {
                add(new Text(result3.getString(1)), x, y);
                add(new Text(result3.getString(2)), x + 1, y);
                add(new Text(result3.getInt(3) + "   "), x + 2, y);
                add(new Text(result3.getString(4)), x + 3, y);
                add(new Text(result3.getString(5)),x + 4,y);
                y++;
            }
            int a = y;

            add(new Text("请输入课程及任课教师"), x, y);
            TextField Course = new TextField();
            add(new Label("课程号"), x, y + 20);
            add(Course, x + 1, y + 20);

            TextField Teacher = new TextField();
            add(new Label("任课教师"),x,y + 21);
            add(Teacher,x + 1,y + 21);
            Button okBt = new Button("确认");
            Button reBt = new Button("返回上一级");
            add(okBt,x,y + 22);
            add(reBt,x + 1,y + 22);


            okBt.setOnAction(e -> {
                String sql1 = "select * from Student";

                try {
                    Connection conn2 = DriverManager.getConnection(url,username,password);
                    Statement sta3 = conn2.createStatement();
                    ResultSet result4 = sta3.executeQuery(sql1);
                    while(result4.next()) {
                        String Id = result4.getString(1);
                        String Name = result4.getString(2);
                        String Cou = Course.getText();
                        String Tea = Teacher.getText();
                        System.out.println(Cou + " " + Tea);
                        String sql2 = "insert into SC values(?,?,?,?)";

                        PreparedStatement pre = conn2.prepareStatement(sql2);
                        pre.setString(1, Id);
                        pre.setString(2, Name);
                        pre.setString(3, Cou);
                        pre.setString(4, Tea);
                        pre.executeUpdate();
                        add(new Text("选课成功"),x + 2,a + 25);
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
            reBt.setOnAction(e1 -> selecthome(id));

            conn1.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delect(String id) {
        getChildren().clear();

        setHgap(5);
        setVgap(5);
        String sql = "select * from SC where 学号 = " + id;

        try {
            Connection conn = DriverManager.getConnection(url,"TianLe","LHS19970420");
            Statement sta = conn.createStatement();
            ResultSet reslt = sta.executeQuery(sql);

            add(new Text("课程号"),0,1);
            add(new Text("课程名"),1,1);
            add(new Text("任课教师"),2,1);

            int x = 0,y = 2;
            if(!reslt.next()) {
                add(new Text("对不起，您还没有选过课程"),x + 4,y + 3);
            }
            else {
                while (reslt.next()) {
                    String Cno = reslt.getString(3);
                    String Tea = reslt.getString(4);
                    String Cname = "";
                    String sql1 = "select 课程名 from Course where 课程号 =  " + Cno;
                    Connection conn1 = DriverManager.getConnection(url,username,password);
                    Statement sta1 = conn1.createStatement();
                    ResultSet reslt1 = sta1.executeQuery(sql1);
                    if (reslt1.next()) {
                        Cname = reslt1.getString(1);
                    }
                    add(new Text(Cno), x, y);
                    add(new Text(Cname), x + 1, y);
                    add(new Text(Tea), x + 2, y);
                    y++;
                }
                TextField Course = new TextField();
                add(new Text("课程号"), x, y + 1);
                add(Course, x + 1, y + 1);
                int y1 = y + 2;
                Button seleBt = new Button("退选");
                Button reBt = new Button("返回上一级");
                add(seleBt, x, y + 2);
                add(reBt,x + 2,y + 2);
                Text suc = new Text("退选成功");
                seleBt.setOnAction(e -> {
                    String Cno1 = Course.getText();
                    String sql2 = "delete from SC where 学号 = ? and 课程号 = ?";
                    try {
                        Connection conn2 = DriverManager.getConnection(url,username,password);
                        PreparedStatement pre = conn2.prepareStatement(sql2);
                        pre.setString(1, id);
                        pre.setString(2, Cno1);
                        pre.executeUpdate();
                        add(suc, x, y1 + 2);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                });

                reBt.setOnAction(e1 -> selecthome(id));
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }





    }

    public void select(String id) {
        getChildren().clear();

        setHgap(5);
        setVgap(5);
        String sql = "select Course.课程号,课程名,学分,上课时间,SC.任课教师,成绩 from SC,Course where SC.学号 = " + id + "and SC.课程号 = Course.课程号";

        try {
            Connection conn1 = DriverManager.getConnection(url,"TianLe","LHS19970420");
            Statement sta = conn1.createStatement();
            ResultSet reslt = sta.executeQuery(sql);

            add(new Text("课程号"),1,1);
            add(new Text("课程名"),2,1);
            add(new Text("学分"),3,1);
            add(new Text("上课时间"),4,1);
            add(new Text("任课教师  "),5,1);
            add(new Text("成绩"),6,1);

            int x = 1,y = 2;

            while(reslt.next()) {
                add(new Text(reslt.getString(1)),x,y);
                add(new Text(reslt.getString(2)),x + 1,y);
                add(new Text(reslt.getString(3)),x + 2,y);
                add(new Text(reslt.getString(4)),x + 3,y);
                add(new Text(reslt.getString(5)),x + 4,y);
                add(new Text(reslt.getString(6)),x + 5,y);
                y++;
            }
            conn1.close();

            Button reBt = new Button("返回上一级");
            add(reBt,x ,y + 3);

            reBt.setOnAction(e -> selecthome(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void changpow(String id) {
        getChildren().clear();

        setHgap(5);
        setVgap(5);
        TextField oldpow = new TextField();
        TextField newpow = new TextField();
        TextField newpow1 = new TextField();
        add(new Text("新密码"),2,4);
        add(newpow,3,4);

        add(new Text("确认密码"),2,5);
        add(newpow1,3,5);

        Button ok = new Button("确认");
        Button reBt = new Button("返回上一级");

        add(ok,3,6);
        add(reBt,4,6);

        Text tx = new Text("修改成功");
        ok.setOnAction(e -> {
            String pow = newpow.getText();
            String pow1 = newpow1.getText();
            if(!pow.equals(pow1)) {
                tx.setText("请重新确认密码");
                add(tx,4,7);
            }
            else {
                add(tx,4,7);
                String sql = "update Student set 密码 = ? where 学号 = ?";

                try {
                    Connection conn1 = DriverManager.getConnection(url, "TianLe", "LHS19970420");
                    PreparedStatement pre = conn1.prepareStatement(sql);

                    pre.setString(1, pow);
                    pre.setString(2, id);
                    pre.executeUpdate();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        });

        reBt.setOnAction(e1 -> selecthome(id));


    }

}

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
import javax.xml.transform.Result;
import java.sql.*;
import java.util.concurrent.Callable;

public class Teacher  extends GridPane{
   static String url = "jdbc:sqlserver://localhost:1433;DatabaseName=Course;characterEncoding=UTF-8";
   static String username= "TianLe";
   static String password = "LHS19970420";

   public Teacher() {
       home();
   }

   public void home() {
       getChildren().clear();
       setHgap(5);
       setVgap(5);
       Image image = new Image("file:image/Teacher.jpg");
       ImageView imageView = new ImageView(image);
       imageView.setFitWidth(500);
       imageView.setFitHeight(530);
       add(imageView,0,0);
       Button bt1 = new Button("登录");
       Button bt2 = new Button("找回密码");
       GridPane pane = new GridPane();

       add(bt1,0,1);
       add(bt2,0,2);
       bt1.setOnAction(e -> log());
       bt2.setOnAction(e1 -> findpow());

   }

   public void log() {
       getChildren().clear();
       setHgap(5);
       setVgap(5);
       TextField id = new TextField();
       TextField pow = new TextField();

       add(new Text("工号"),1,1);
       add(id,2,1);
       add(new Text("密码"),1,2);
       add(pow,2,2);

       Button ok = new Button("登录");
       Button reBt = new Button("返回上一级");

       add(ok,1,3);
       add(reBt,2,3);

       ok.setOnAction(e -> {
           String id1 = id.getText();
           String pow1 = pow.getText();

           Text text = new Text("不存在此工号");

           String sql = "select * from Teacher where 工号 = " + id1;

           try {
               Connection conn1 = DriverManager.getConnection(url,username,password);
               Statement sta = conn1.createStatement();
               ResultSet result = sta.executeQuery(sql);

               if(result.next()) {
                   String sql1 = "select 密码 from Teacher where 工号 = " + id1;
                   ResultSet result1 = sta.executeQuery(sql1);
                   while(result1.next()) {
                       String pow2 = result1.getString(1);
                       if(!pow2.equals(pow1)) {
                           selecthome(id1);
                       }
                       else {
                           text.setText("密码错误");
                           add(text,1,3);
                       }
                   }
               }
               else {
                   add(text,1,3);
               }
           } catch (SQLException e1) {
               e1.printStackTrace();
           }
       });

       reBt.setOnAction(e1 -> home());

   }

   public void selecthome(String id) {
       getChildren().clear();
       setHgap(5);
       setVgap(5);
       Button changeBt = new Button("修改密码");
       Button viewBt = new Button("查看个人信息");
       Button viewStuBt = new Button("查看选课学生");
       Button reBt = new Button("返回上一级");

       add(viewBt,1,1);
       add(viewStuBt,1,2);
       add(changeBt,1,3);
       add(reBt,1,4);

       changeBt.setOnAction(e1 -> {
           changepow(id);
       });

       viewStuBt.setOnAction(e2 -> {
           selectStu(id);
       });

       viewBt.setOnAction(e3 -> {
           selectTea(id);
       });

       reBt.setOnAction(e4 -> {
           log();
       });
   }

   public void findpow() {
       getChildren().clear();
       setHgap(5);
       setVgap(5);
       TextField id = new TextField();
       TextField pow = new TextField();
       TextField pow1 = new TextField();
       TextField name = new TextField();
       TextField Course = new TextField();

       add(new Text("工号"),1,1);
       add(id,2,1);
       add(new Text("姓名"),1,2);
       add(name,2,2);
       add(new Text("教授课程"),1,3);
       add(Course,2,3);
       add(new Text("新密码"),1,4);
       add(pow,2,4);
       add(new Text("确认密码"),1,5);
       add(pow1,2,5);
       Button ok = new Button("确认");
       Button reBt = new Button("返回上一级");
       add(ok,2,6);
       add(reBt,3,6);

       ok.setOnAction(e1 -> {
           String Id = id.getText();
           String Name = name.getText();
           String Course1 = Course.getText();

           String sql = "select 工号,姓名,教授课程 from Teacher where 工号 = " + Id;

           try {
               Connection conn1 = DriverManager.getConnection(url, username, password);
               Statement sta = conn1.createStatement();
               ResultSet result = sta.executeQuery(sql);
               Text text = new Text("成功找回");
               while (result.next()) {
                   String id1 = result.getString(1);
                   String name1 = result.getString(2);
                   String course1 = result.getString(3);
                   if (!(id1.equals(Id) && !name1.equals(Name) && course1.equals(Course1))) {
                       String Pow = pow.getText();
                       String Pow1 = pow1.getText();
                       if (Pow.equals(Pow1)) {
                           String sql1 = "update Teacher set 密码 = " + Pow + "where 工号 = ?";
                           PreparedStatement pre = conn1.prepareStatement(sql1);
                           pre.setString(1, id1);
                           pre.executeUpdate();
                       } else {
                           text.setText("请重新确认密码");
                       }
                   } else {
                           text.setText("                 ");
                           text.setText("信息不符，无法找回");
                   }

               }
               add(text,2,8);
           } catch (SQLException e) {
               e.printStackTrace();
           }
       });

       reBt.setOnAction(e2 -> home());
   }

   public void changepow(String id) {
       getChildren().clear();

       setHgap(5);
       setVgap(5);
       TextField pow = new TextField();
       TextField pow1 = new TextField();

       add(new Text("新密码"),1,1);
       add(pow,2,1);
       add(new Text("确认密码"),1,2);
       add(pow1,2,2);

       Button ok = new Button("确认");
       Button reBt = new Button("返回上一级");
       add(ok,1,3);
       add(reBt,2,3);


       ok.setOnAction(e -> {
           String Pow = pow.getText();
           String Pow1 = pow1.getText();

           if(Pow.equals(Pow1)) {
               String sql = "update Teacher set 密码 = ? where 工号 =  ?";
               try {
                   Connection conn1 = DriverManager.getConnection(url, username, password);
                   PreparedStatement pre = conn1.prepareStatement(sql);
                   pre.setString(1, Pow);
                   pre.setString(2, id);
                   pre.executeUpdate();
                   add(new Text("修改成功"),2,4);
               } catch (SQLException e1) {
                   e1.printStackTrace();
               }
           }
           else {
               add(new Text("请重新确认密码"),2,4);
           }
       });

       reBt.setOnAction(e1 -> selecthome(id));


   }

   public void selectStu(String id) {
       getChildren().clear();
       setHgap(5);
       setVgap(5);
       add(new Text("学号"),0,0);
       add(new Text("姓名"),1,0);
       add(new Text("性别"),2,0);
       add(new Text("学院"),3,0);
       add(new Text("专业"),4,0);
       add(new Text("班级"),5,0);

       String sql = "select * from Student where 学号 in (select 学号 from SC where 任课教师 in (select 姓名 from Teacher where 工号 = " + id + "))";

       try {
           Connection conn1 = DriverManager.getConnection(url,username,password);
           Statement sta = conn1.createStatement();
           ResultSet result = sta.executeQuery(sql);
           int x = 0,y = 1;
           while(result.next()) {
               add(new Text(result.getString(1)),x ,y);
               add(new Text(result.getString(2)),x + 1,y);
               add(new Text(result.getString(3)),x + 2,y);
               add(new Text(result.getString(4)),x + 3,y);
               add(new Text(result.getString(5)),x + 4,y);
               add(new Text(result.getString(6)),x + 5,y);
               y++;
           }
           Button reBt = new Button("返回上一级");
           add(reBt,x + 1,y + 2);

           reBt.setOnAction(e -> {
               selecthome(id);
           });
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }

   public void selectTea(String id) {
       getChildren().clear();
       setHgap(5);
       setVgap(5);
       String sql = "select * from Teacher where 工号 = " + id;
       add(new Text("工号"),0,0);
       add(new Text("姓名"),1,0);
       add(new Text("性别"),2,0);
       add(new Text("教授课程"),3,0);
       add(new Text("职称"),4,0);

       try {
           Connection conn1 = DriverManager.getConnection(url,username,password);
           Statement sta = conn1.createStatement();
           ResultSet result = sta.executeQuery(sql);
           int x = 0,y = 1;
           while(result.next()) {
               add(new Text(result.getString(1)),x,y);
               add(new Text(result.getString(2)),x + 1,y);
               add(new Text(result.getString(3)),x + 2,y);
               add(new Text(result.getString(4)),x + 3,y);
               add(new Text(result.getString(5)),x + 4,y);
               y++;
           }

           Button reBt = new Button("返回上一级");
           add(reBt,x + 1,y + 3);

           reBt.setOnAction(e -> selecthome(id));
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }

   public void addGrate(String id) {
       getChildren().clear();

       TextField no = new TextField();
       TextField gra = new TextField();

       add(new Text("学号"),1,1);
       add(no,2,1);
       add(new Text("成绩"),1,2);
       add(gra,2,2);

       Button ok = new Button("确定");
       Button reBt = new Button("返回上一级");

       add(ok,1,4);
       add(reBt,2,4);

       ok.setOnAction(e -> {
           String No = no.getText();
           String Gra = gra.getText();

           String sql = "update SC set 成绩 = ? where ";
       });

   }


}

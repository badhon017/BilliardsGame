package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.audio.AudioPlayer;

import java.net.InetAddress;
import java.util.Scanner;

public class Main extends Application {

    double rotation = 0;
    double opacity = 1.0;

    Ball[] balls;
    int mousepress = 0;
    int win = 0;
    int count = 0;
    int count1 = 0;
    int player = 1;
    int turn = 1;
    int isFoul = 0;
    int i1 = 10;
    int w = 0;
    int hit = 0;
    int p;
    ClientMain c;
    Label T1, T2, T3, T4, T5, T6;
    boolean value;

    class reader implements Runnable {
        String s = " ";

        reader() {
            new Thread(this).start();
        }

        public void run() {
            while (true) {
                s = (String) c.nc.read();
                double px, py, qx, qy, rx, ry;
                //String[] string = s.split("\\ ");
                if (s.contains("Position")) {
                    String[] string = s.split("\\ ");
                    System.out.println(s);
                  //  double px, py, qx, qy, rx, ry;
                    px = Double.parseDouble(string[1]);
                    py = Double.parseDouble(string[2]);
                    qx = Double.parseDouble(string[3]);
                    qy = Double.parseDouble(string[4]);
                    rx = Double.parseDouble(string[5]);
                    ry = Double.parseDouble(string[6]);

                    VelocityPos(px, py, qx, qy, rx, ry);
                }

              /*  if (s.contains("stick")) {

                    String[] string = s.split("\\ ");
                    System.out.println(s);
                    //double px, py, qx, qy, rx, ry;
                    px = Double.parseDouble(string[1]);
                    py = Double.parseDouble(string[2]);
                    qx = Double.parseDouble(string[3]);
                    qy = Double.parseDouble(string[4]);
                    rx = Double.parseDouble(string[5]);
                    ry = Double.parseDouble(string[6]);

                   // changeStick(px, py, qx, qy, rx, ry);
                } */
                /*
                else if(s.contains("coordinate")){
                    double h,m;
                    int a;

                    h = Double.parseDouble(string[1]);
                    m = Double.parseDouble(string[2]);
                    a = Integer.parseInt(string[3]);
                    balls[a].setX(h);
                    balls[a].setY(m);
                } */
                s = "";
            }
        }
    }

    public void updateValue(long now) {
        count1++;
        count++;
        //System.out.println("now is : " + now);
        if (count > 50) {
            count = 0;
        }
        for (int i = 0; i < balls.length; i++) {
            for (int j = i + 1; j < balls.length; j++) {

                double k = balls[i].Radious;
                double k1 = (balls[i].getX() + balls[i].getVx()) - (balls[j].getX() + balls[j].getVx());
                double k2 = (balls[i].getY() + balls[i].getVy()) - (balls[j].getY() + balls[j].getVy());
                double k11 = (balls[i].getX()) - (balls[j].getX());
                double k22 = (balls[i].getY()) - (balls[j].getY());

                if ((k1 * k1 + k2 * k2) <= (4 * k * k) || (k11 * k11 + k22 * k22) <= (4 * k * k)) {
                    if (w == 1) {
//                        System.out.println();
                        int m;
                        if (i == 9) {
                            m = j;
                            // System.out.println(m);
                            if (m == i1) {
                                hit = 1;
                                w = 0;
                            } else if (m != i1) {
                                isFoul = 1;
                                hit = 2;
                                p = count1 + 110;
                                w = 0;
                            }
                        } else if (j == 9) {
                            m = i;
                            // System.out.println(m);
                            if (m == i1) {
                                hit = 1;
                                w = 0;
                            } else if (m != i1) {
                                isFoul = 1;
                                hit = 2;
                                p = count1 + 110;
                                w = 0;
                            }
                        }
                    }


                    String path2 = AudioPlayer.class.getResource("/BinaryContent/Coins.mp3").toString();
                    Media media2 = new Media(path2);
                    MediaPlayer mp2 = new MediaPlayer(media2);
                    mp2.stop();
                    mp2.play();

                    double angle = (Math.atan2(balls[i].getCenterY() - balls[j].getCenterY(), balls[i].getCenterX() - balls[j].getCenterX()) + 2 * Math.PI) % (2 * Math.PI);
                    balls[i].setCenterX(balls[j].getCenterX() + balls[j].getRadius() * Math.cos(angle) + balls[i].getRadius() * Math.cos(angle) + 2 * Math.cos(angle));
                    balls[i].setCenterY(balls[j].getCenterY() + balls[j].getRadius() * Math.sin(angle) + balls[i].getRadius() * Math.sin(angle) + 2 * Math.sin(angle));
                    balls[j].setCenterX(balls[i].getCenterX() + balls[i].getRadius() * Math.cos(angle + Math.PI) + balls[j].getRadius() * Math.cos(angle + Math.PI) + 3 * Math.cos(Math.PI + angle));
                    balls[j].setCenterY(balls[i].getCenterY() + balls[i].getRadius() * Math.sin(Math.PI + angle) + balls[j].getRadius() * Math.sin(Math.PI + angle) + 3 * Math.sin(Math.PI + angle));

                    double v1self, v2self, v1load, v2load, a1self, a2self, a1load, a2load;

                    angle = (angle + Math.PI) % (2 * Math.PI);
                    a2load = angle;
                    a1load = (angle + Math.PI) % (2 * Math.PI);

                    if (balls[i].angle() > angle) {
                        a1self = angle + Math.PI / 2;
                    } else {
                        a1self = angle - Math.PI / 2;
                    }

                    v1self = balls[i].getVelocity() * Math.cos(a1self - balls[i].angle());
                    v2load = 1.2 * balls[i].getVelocity() * Math.cos(balls[i].angle() - a2load);

                    if (balls[j].angle() > a1load) {
                        a2self = a1load + Math.PI / 2;
                    } else {
                        a2self = a1load - Math.PI / 2;
                    }

                    v2self = balls[j].getVelocity() * Math.cos(a2self - balls[j].angle());
                    v1load = 0.8 * balls[j].getVelocity() * Math.cos(balls[j].angle() - a1load);

                    balls[i].setVx(v1self * Math.cos(a1self) + v1load * Math.cos(a1load));
                    balls[i].setVy(v1self * Math.sin(a1self) + v1load * Math.sin(a1load));

                    balls[j].setVx(v2self * Math.cos(a2self) + v2load * Math.cos(a2load));
                    balls[j].setVy(v2self * Math.sin(a2self) + v2load * Math.sin(a2load));

                }
            }
        }


        for (Ball b : balls) {
            //b.updateCenter(1);
            b.updateCenter(2.5);
            b.collision();
            if (((b.getCenterX() + b.getRadius()) >= 946 && (b.getCenterX() + b.getRadius()) < 956) || ((b.getCenterX() - b.getRadius()) <= 252 && (b.getCenterX() - b.getRadius()) > 242) || ((b.getCenterY() + b.getRadius()) >= 528 && (b.getCenterY() + b.getRadius()) < 538) ||
                    ((b.getCenterY() - b.getRadius()) <= 169 && (b.getCenterY() - b.getRadius()) > 159)) {
                String path2 = AudioPlayer.class.getResource("/BinaryContent/Coins.mp3").toString();
                Media media2 = new Media(path2);
                MediaPlayer mp2 = new MediaPlayer(media2);
                mp2.stop();
                mp2.play();
            }

            double bx = b.getX();
            double by = b.getY();

            //  if (bx >= 252.66 && bx < 281.3 && by <= 222 && by >= 169) {
            //    if ((by - 169) < ((281 - bx) * 1.41)) {

            String str;
            if (((bx - 236) * (bx - 236) + (by - 158) * (by - 158)) <= (30 * 30)) {
                if (b.getid() == 9) {
                    isFoul = 1;
                    hit = 2;
                    p = count1 + 110;
                } else if (b.getid() == 8) {
                    win = 1;

                    for (int z = 0; z < 9; z++) {
                        balls[z].setVx(0);
                        balls[z].setVy(0);
                        balls[z].setCenterX(10000);
                        balls[z].setCenterY(2000 + z * 30);


                        //  balls[z].setFill(Color.BLACK);
                    }
                    balls[9].setVx(0);
                    balls[9].setVy(0);
                    balls[9].setCenterX(672);
                    balls[9].setCenterY(275);
                    if (hit == 1) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setScaleX(1);
                            T3.setScaleY(1);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setScaleX(1);
                            T4.setScaleY(1);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        }

                    } else if (hit == 2) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setScaleX(1);
                            T4.setScaleY(1);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setScaleX(1);
                            T3.setScaleY(1);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        }

                    }

                    String path5 = AudioPlayer.class.getResource("/BinaryContent/win.mp3").toString();
                    Media media5 = new Media(path5);
                    MediaPlayer mp5 = new MediaPlayer(media5);
                    //if(mousepress==1){mp5.stop();}
                    mp5.play();


                } else {
                    String path3 = AudioPlayer.class.getResource("/BinaryContent/potted.mp3").toString();
                    Media media3 = new Media(path3);
                    MediaPlayer mp3 = new MediaPlayer(media3);
                    mp3.stop();
                    mp3.play();
                    b.setCenterX(900);
                    b.setCenterY(610.5);

                    b.setVx(-5.3);
                    b.setVy(0);
                    turn = turn + 1;
                }

            }

            // else if (bx > 576 && b.getX() < 624.66 && b.getY() <= 206.5 && b.getY()>200) {
            else if (((bx - 596) * (bx - 596) + (by - 158) * (by - 158) <= (26 * 26))) {
                if (b.getid() == 9) {
                    isFoul = 1;
                    hit = 2;
                    p = count1 + 110;
                } else if (b.getid() == 8) {
                    win = 1;

                    for (int z = 0; z < 9; z++) {
                        balls[z].setVx(0);
                        balls[z].setVy(0);
                        balls[z].setCenterX(10000);
                        balls[z].setCenterY(2000 + z * 30);

                        //  balls[z].setFill(Color.BLACK);
                    }
                    balls[9].setVx(0);
                    balls[9].setVy(0);
                    balls[9].setCenterX(672);
                    balls[9].setCenterY(275);
                    if (hit == 1) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setScaleX(1);
                            T3.setScaleY(1);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setScaleX(1);
                            T4.setScaleY(1);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        }

                    } else if (hit == 2) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setScaleX(1);
                            T4.setScaleY(1);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setScaleX(1);
                            T3.setScaleY(1);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        }

                    }

                    String path5 = AudioPlayer.class.getResource("/BinaryContent/win.mp3").toString();
                    Media media5 = new Media(path5);
                    MediaPlayer mp5 = new MediaPlayer(media5);
                    if (mousepress == 1) {
                        mp5.stop();
                    }
                    mp5.play();
                } else {
                    String path3 = AudioPlayer.class.getResource("/BinaryContent/potted.mp3").toString();
                    Media media3 = new Media(path3);
                    MediaPlayer mp3 = new MediaPlayer(media3);
                    mp3.stop();
                    mp3.play();
                    b.setCenterX(900);
                    b.setCenterY(610.5);

                    b.setVx(-5.3);
                    b.setVy(0);
                    turn = turn + 1;
                }


            }
            //else if (bx >= 919.33 && bx < 946 && by <= 222 && by >= 196) {
            //  if ((by - 196) < ((bx - 919) * 1.41)) {
            else if (((bx - 965) * (bx - 965) + (by - 158) * (by - 158)) <= (35 * 35)) {
                if (b.getid() == 9) {
                    isFoul = 1;
                    hit = 2;
                    p = count1 + 110;
                } else if (b.getid() == 8) {
                    win = 1;

                    for (int z = 0; z < 9; z++) {
                        balls[z].setVx(0);
                        balls[z].setVy(0);
                        balls[z].setCenterX(10000);
                        balls[z].setCenterY(2000 + z * 30);

                        //  balls[z].setFill(Color.BLACK);
                    }
                    balls[9].setVx(0);
                    balls[9].setVy(0);
                    balls[9].setCenterX(672);
                    balls[9].setCenterY(275);
                    if (hit == 1) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setScaleX(1);
                            T3.setScaleY(1);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setScaleX(1);
                            T4.setScaleY(1);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        }

                    } else if (hit == 2) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setScaleX(1);
                            T4.setScaleY(1);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setScaleX(1);
                            T3.setScaleY(1);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        }

                    }

                    //       if(mousepress==1){mp5.stop();}
                    //     mp5.play();
                } else {
                    String path3 = AudioPlayer.class.getResource("/BinaryContent/potted.mp3").toString();
                    Media media3 = new Media(path3);
                    MediaPlayer mp3 = new MediaPlayer(media3);
                    mp3.stop();
                    mp3.play();
                    b.setCenterX(900);
                    b.setCenterY(610.5);

                    b.setVx(-5.3);
                    b.setVy(0);
                }
                turn = turn + 1;
            }


            //else if (bx >= 254 && bx < 280 && by <= 501 && by >= 476) {
            //  if ((501 - by) < ((bx - 254) * 1.41)) {
            // balls[19] = new Ball(785 + 18 * 2 - 585, 348+22+171, root2, 25, 0,0, Color.YELLOW);
            else if (((bx - 236) * (bx - 236) + (by - 541) * (by - 541)) <= 30 * 30) {
                if (b.getid() == 9) {
                    isFoul = 1;
                    hit = 2;
                    p = count1 + 110;
                } else if (b.getid() == 8) {
                    win = 1;

                    for (int z = 0; z < 9; z++) {
                        balls[z].setVx(0);
                        balls[z].setVy(0);
                        balls[z].setCenterX(10000);
                        balls[z].setCenterY(2000 + z * 30);

                        //  balls[z].setFill(Color.BLACK);
                    }
                    balls[9].setVx(0);
                    balls[9].setVy(0);
                    balls[9].setCenterX(672);
                    balls[9].setCenterY(275);
                    if (hit == 1) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        }

                    } else if (hit == 2) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        }

                    }

                    String path5 = AudioPlayer.class.getResource("/BinaryContent/win.mp3").toString();
                    Media media5 = new Media(path5);
                    MediaPlayer mp5 = new MediaPlayer(media5);
                    if (mousepress == 1) {
                        mp5.stop();
                    }
                    mp5.play();
                } else {
                    String path3 = AudioPlayer.class.getResource("/BinaryContent/potted.mp3").toString();
                    Media media3 = new Media(path3);
                    MediaPlayer mp3 = new MediaPlayer(media3);
                    mp3.stop();
                    mp3.play();
                    b.setCenterX(900);
                    b.setCenterY(610.5);

                    b.setVx(-5.3);
                    b.setVy(0);
                }
                turn = turn + 1;
            }
            // else if (bx >= 576 && bx < 624.66 && by >= 490.5 && by< 500) {
            else if (((bx - 596) * (bx - 596) + (by - 541) * (by - 541)) <= 26 * 26) {
                if (b.getid() == 9) {
                    isFoul = 1;
                    hit = 2;
                    p = count1 + 110;
                } else if (b.getid() == 8) {
                    win = 1;

                    for (int z = 0; z < 9; z++) {
                        balls[z].setVx(0);
                        balls[z].setVy(0);
                        balls[z].setCenterX(10000);
                        balls[z].setCenterY(2000 + z * 30);

                        //  balls[z].setFill(Color.BLACK);
                    }
                    balls[9].setVx(0);
                    balls[9].setVy(0);
                    balls[9].setCenterX(672);
                    balls[9].setCenterY(275);
                    if (hit == 1) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        }

                    } else if (hit == 2) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        }

                    }

                    String path5 = AudioPlayer.class.getResource("/BinaryContent/win.mp3").toString();
                    Media media5 = new Media(path5);
                    MediaPlayer mp5 = new MediaPlayer(media5);
                    if (mousepress == 1) {
                        mp5.stop();
                    }
                    mp5.play();
                } else {
                    String path3 = AudioPlayer.class.getResource("/BinaryContent/potted.mp3").toString();
                    Media media3 = new Media(path3);
                    MediaPlayer mp3 = new MediaPlayer(media3);
                    mp3.stop();
                    mp3.play();
                    b.setCenterX(900);
                    b.setCenterY(610.5);

                    b.setVx(-5.3);
                    b.setVy(0);
                }
                turn = turn + 1;

            }
            //else if (b.getX() >= 919 && bx < 946 && by >= 477 && by <= 501) {
            //  if ((501 - by) < ((946 - bx) * 1.41)) {
            else if (((bx - 965) * (bx - 965) + (by - 541) * (by - 541)) <= (35 * 35)) {
                if (b.getid() == 9) {
                    isFoul = 1;
                    hit = 2;
                    p = count1 + 110;
                } else if (b.getid() == 8) {
                    win = 1;

                    for (int z = 0; z < 9; z++) {
                        balls[z].setVx(0);
                        balls[z].setVy(0);
                        balls[z].setCenterX(10000);
                        balls[z].setCenterY(2000 + z * 30);

                        //  balls[z].setFill(Color.BLACK);
                    }
                    balls[9].setVx(0);
                    balls[9].setVy(0);
                    balls[9].setCenterX(672);
                    balls[9].setCenterY(275);
                    if (hit == 1) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        }

                    } else if (hit == 2) {
                        turn = turn + 1;
                        if (turn % 2 == 1) {
                            T4.setLayoutX(455);
                            T4.setLayoutY(270);
                            T4.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T4.setTextFill(Color.WHITE);
                        } else if (turn % 2 == 0) {
                            T3.setLayoutX(455);
                            T3.setLayoutY(270);
                            T3.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
                            T3.setTextFill(Color.WHITE);
                        }

                    }

                    String path5 = AudioPlayer.class.getResource("/BinaryContent/win.mp3").toString();
                    Media media5 = new Media(path5);
                    MediaPlayer mp5 = new MediaPlayer(media5);
                    if (mousepress == 1) {
                        mp5.stop();
                    }
                    mp5.play();
                } else {
                    String path3 = AudioPlayer.class.getResource("/BinaryContent/potted.mp3").toString();
                    Media media3 = new Media(path3);
                    MediaPlayer mp3 = new MediaPlayer(media3);
                    mp3.stop();
                    mp3.play();
                    b.setCenterX(900);
                    b.setCenterY(610.5);

                    b.setVx(-5.3);
                    b.setVy(0);
                }
                turn = turn + 1;

            }
        }


        for (Ball b : balls) {
            if (b.getCenterX() > 252 && b.getCenterX() < 946 && b.getCenterY() > 169 && b.getCenterY() < 528) {
                i1 = b.getid();
                break;
            }
        }

        for (int j = 10; j <= 18; j++) {
            if (balls[j].getid() != (i1 + 10)) {
                balls[j].setScaleX(1);
                balls[j].setScaleY(1);
            }
            if (balls[j].getid() == (i1 + 10)) {
                if (count < 25) {
                    balls[j].setScaleX(1.6);
                    balls[j].setScaleY(1.6);
                }
                if (count > 25) {
                    balls[j].setScaleX(1.2);
                    balls[j].setScaleY(1.2);
                }
            }
        }

        if (isFoul == 1) {
            String path4 = AudioPlayer.class.getResource("/BinaryContent/redmiss.mp3").toString();
            Media media4 = new Media(path4);
            MediaPlayer mp4 = new MediaPlayer(media4);
//                mp4.stop();
            mp4.play();

            balls[9].setVx(0);
            balls[9].setVy(0);
            balls[9].setCenterX(600);
            balls[9].setCenterY(350);
            hit = 2;
            isFoul = 0;
        }
        if (win != 1) {
            if (turn % 2 == 1) {
                player = 1;
                if (balls[9].getVelocity() == 0) {
                    T1.setTextFill(Color.WHITE);
                    T2.setTextFill(Color.BLACK);
                }
            } else if (turn % 2 == 0) {
                player = 2;
                if (balls[9].getVelocity() == 0) {
                    T2.setTextFill(Color.WHITE);
                    T1.setTextFill(Color.BLACK);
                }
            }
        }
        if (win == 1) {
            T1.setTextFill(Color.BLACK);
            T2.setTextFill(Color.BLACK);

            if (count < 30) {
                T5.setLayoutX(430);
                T5.setLayoutY(400);
                T5.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
                T5.setTextFill(Color.WHITE);
                T5.setScaleX(1);
                T5.setScaleY(1);
            } else if (count >= 30) {
                T5.setLayoutX(430);
                T5.setLayoutY(400);
                T5.setFont(Font.font("Tahoma", FontWeight.BOLD, 0));
                T5.setTextFill(Color.WHITE);
                T5.setScaleX(0);
                T5.setScaleY(0);
            }
        } else if (win == 0) {
            T3.setScaleX(0);
            T3.setScaleY(0);

            T4.setScaleX(0);
            T4.setScaleY(0);

            T5.setScaleX(0);
            T5.setScaleY(0);

        }

//          if(w==1){
//              for(int m=0;m<9;m++){
//                 double k = 10.5;
//                double k1 = (balls[m].getX()) - (balls[9].getX());
//                double k2 = (balls[m].getY()) - (balls[9].getY());
//
//                if ((k1 * k1 + k2 * k2) <= (4 * k * k)){
//                    System.out.print(m);
//                        if(m==i1){w=0;System.out.println("m==i");}
//                        else{isFoul=1;w=0;System.out.println("m!=i");}
//                }
//                    }
//
//          }
        if (hit == 2) {
            if (win != 1) {
                if (count1 < p) {
                    T6.setLayoutX(400);
                    T6.setLayoutY(270);
                    T6.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
                    T6.setTextFill(Color.WHITE);
                } else if (count1 >= p) {
                    T6.setLayoutX(435);
                    T6.setLayoutY(270);
                    T6.setFont(Font.font("Tahoma", FontWeight.BOLD, 0));
                    T6.setTextFill(Color.WHITE);
                }
            } else {
                T6.setLayoutX(435);
                T6.setLayoutY(270);
                T6.setFont(Font.font("Tahoma", FontWeight.BOLD, 0));
                T6.setTextFill(Color.WHITE);
            }
        }

    }

    public void VelocityPos(double bx, double by, double mx, double my, double m_bx, double m_by) {
        double kx, ky, sx, sy;
        if (((m_bx) * (m_bx) + (m_by) * (m_by)) > 12 * 12) {
            double kon = Math.toDegrees(Math.atan((my - balls[9].getCenterY()) / (mx - balls[9].getCenterX())));

            if (my > by) {
                sy = by - 200 * Math.sin(Math.toRadians(kon));
                if (Math.sin(Math.toRadians(kon)) < 0) {
                    sy = by + 200 * Math.sin(Math.toRadians(kon));
                }
            } else {
                sy = by + 200 * Math.sin(Math.toRadians(kon));
                if (Math.sin(Math.toRadians(kon)) < 0) {
                    sy = by - 200 * Math.sin(Math.toRadians(kon));
                }
            }

            if (mx > bx) {
                sx = bx - 200 * Math.cos(Math.toRadians(kon));
                if (Math.cos(Math.toRadians(kon)) < 0) {
                    sx = bx + 200 * Math.cos(Math.toRadians(kon));
                }
            } else {
                sx = bx + 200 * Math.cos(Math.toRadians(kon));
                if (Math.cos(Math.toRadians(kon)) < 0) {
                    sx = bx - 200 * Math.cos(Math.toRadians(kon));
                }
            }
           // if(val.contains("saifurtrue"))
            box.setLayoutX(sx - 150.5);
            box.setLayoutY(sy - 150.5);

            if (balls[9].getVelocity() == 0) {
                //  if ((252 < mouseEvent.getX() && mouseEvent.getX() < 946) && (196 < mouseEvent.getY() && mouseEvent.getY() < 501)) {

                double tx = (mx - balls[9].getCenterX()) / 30;
                double ty = (my - balls[9].getCenterY()) / 30;

                balls[9].setVx(tx);
                balls[9].setVy(ty);
                hit = 0;
                w = 1;
                turn = turn + 1;
            }
        }
    }

    HBox box = new HBox();

/*
    public void changeStick(double mx, double my, double bx, double by, double m_bx, double m_by) {
        double kx, ky, sx, sy;
        balls[9].setCenterY(my);
        double kon = Math.toDegrees(Math.atan((my - balls[9].getCenterY()) / (mx - bx)));

        if (my > by) {
            sy = by - 250 * Math.sin(Math.toRadians(kon));
            if (Math.sin(Math.toRadians(kon)) < 0) {
                sy = by + 250 * Math.sin(Math.toRadians(kon));
            }
        } else {
            sy = by + 250 * Math.sin(Math.toRadians(kon));
            if (Math.sin(Math.toRadians(kon)) < 0) {
                sy = by - 250 * Math.sin(Math.toRadians(kon));
            }
        }

        if (mx > bx) {
            sx = bx - 250 * Math.cos(Math.toRadians(kon));
            if (Math.cos(Math.toRadians(kon)) < 0) {
                sx = bx + 250 * Math.cos(Math.toRadians(kon));
            }
        } else {
            sx = bx + 250 * Math.cos(Math.toRadians(kon));
            if (Math.cos(Math.toRadians(kon)) < 0) {
                sx = bx - 250 * Math.cos(Math.toRadians(kon));
            }
        }
        box.setLayoutX(sx - 150.5);
        box.setLayoutY(sy - 150.5);
        if (mx >= bx) {
            rotation = kon + 45;
        } else {
            rotation = kon - 135;
        }
        if (balls[9].getVelocity() > 0) {
            box.setOpacity(0);
        } else {
            box.setOpacity(100);
        }

        box.setRotate(rotation);

    }
*/

    //HBox box = new HBox();

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println("Enter your name: ");
        Scanner input = new Scanner(System.in);


        String s = input.nextLine();
        c = new ClientMain(s);
        reader rd = new reader();
        //Group root = new Group();
        String path1 = AudioPlayer.class.getResource("/BinaryContent/flourish.mp3").toString();
        Media media1 = new Media(path1);
        MediaPlayer mp1 = new MediaPlayer(media1);
        mp1.play();

        VBox root = new VBox(10);
        Image image = new Image("/BinaryContent/frontpic.jpg", 1400, 700, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        Button button1 = new Button("New Game");
        Button button2 = new Button("Quit");
        // Button button3 = new Button();

        button2.setOnAction(e -> {
            mp1.stop();
            System.exit(0);
        });

        // VBox root2 = new VBox(10);

       /* Image image2 = new Image("table.png", 1200, 600, false, true);
        BackgroundImage backImage = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background gameImage = new Background(backImage);
*/


        //  root2.setBackground(gameImage);

        //  root2.setAlignment(Pos.CENTER);
        // Scene scene2 = new Scene(root2, 1500, 800);


        button1.setLayoutX(500);
        button1.setLayoutY(600);
        button2.setLayoutX(500);
        button2.setLayoutY(700);


        root.setAlignment(Pos.CENTER);


        Text txt2 = new Text(10, 30, "Biliards Game");
        txt2.setFont(Font.font("Tahoma", FontWeight.BOLD, 50));
        txt2.setFill(Color.WHITE);
        txt2.setLayoutX(100);
        txt2.setLayoutY(50);

        root.getChildren().add(txt2);

        root.getChildren().addAll(button1, button2);
        root.setBackground(background);

        Scene scene = new Scene(root, 1400, 700);
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("POOL GAME");


        primaryStage.setScene(scene);

        Group root2 = new Group();
        Scene scene2 = new Scene(root2, 1300, 700);

        VBox root3 = new VBox(10);
        Image image2 = new Image("/BinaryContent/background.jpg", 1500, 800, false, true);
        BackgroundImage backImage = new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background2 = new Background(backImage);

        //Scene scene2 = new Scene(root2, 1500, 800);

        root3.setBackground(background2);
        // root2.getChildren().add(root3);
        Image im = new Image("/BinaryContent/table.png");
        ImageView iv = new ImageView(im);
        iv.setFitWidth(800);
        iv.setPreserveRatio(true);

        StackPane p = new StackPane();
        p.setPrefSize(700, 700);

        p.getChildren().addAll(iv);

        StackPane.setAlignment(iv, Pos.CENTER);
        StackPane.setMargin(iv, new Insets(50, 50, 50, 200));
        root2.getChildren().add(p);

        Rectangle r = new Rectangle();
        r.setLayoutX(252);
        r.setLayoutY(600);
        r.setWidth(700);
        r.setHeight(21);

//        r.setX(150);
//        r.setY(510);
        r.setFill(Color.BROWN);
        r.setStroke(Color.BLACK);
        root2.getChildren().add(r);
        Text txt3 = new Text(10, 30, "Color Hit Sequence :");
        txt3.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        txt3.setFill(Color.BLACK);
        root2.getChildren().add(txt3);


        //    Scene sc = new Scene(root3, 1500, 800);
        button1.setOnAction(e -> {

            // primaryStage.setScene(sc);
            primaryStage.setScene(scene2);
            mp1.stop();

            new AnimationTimer() {

                @Override
                public void handle(long now) {

                    updateValue(now);

                }
            }.start();
        });

        Text txt4 = new Text(620, 35, "9 Ball:");
        txt4.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        txt4.setFill(Color.BLACK);
        root2.getChildren().add(txt4);

        Text txt5 = new Text(710, 35, "RED");
        txt5.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        txt5.setFill(Color.RED);
        root2.getChildren().add(txt5);

        T1 = new Label("Turn Player 1");
        T2 = new Label("Turn Player 2");

        T1.setLayoutX(1020);
        T1.setLayoutY(235);
        T1.setFont(Font.font(20));

        T2.setLayoutX(1020);
        T2.setLayoutY(435);
        T2.setFont(Font.font(20));

        T3 = new Label("Player 1 wins..!!");
        T4 = new Label("Player 2 wins..!!");
        T5 = new Label("Click anywhere to back Mainmenu..");
        T6 = new Label("Foul...!! Opponent have white ball in hand..");


        balls = new Ball[19];

        // balls[19] = new Ball(965, 158, root2, 35, 0,0, Color.YELLOW);

        balls[0] = new Ball(785, 348, root2, 10.5, 0, 0, Color.VIOLET);
        balls[0].setId(0);

        balls[1] = new Ball(785 + 18, 348 - 11, root2, 10.5, 0, 0, Color.DARKRED);
        balls[1].setId(1);

        balls[2] = new Ball(785 + 18, 348 + 11, root2, 10.5, 0, 0, Color.GREENYELLOW);
        balls[2].setId(2);

        balls[3] = new Ball(785 + 18 * 2, 348, root2, 10.5, 0, 0, Color.DARKGREEN);
        balls[3].setId(3);

        balls[4] = new Ball(785 + 18 * 2, 348 + 22, root2, 10.5, 0, 0, Color.YELLOW);
        balls[4].setId(4);

        balls[5] = new Ball(785 + 18 * 2, 348 - 22, root2, 10.5, 0, 0, Color.DARKORANGE);
        balls[5].setId(5);

        balls[6] = new Ball(785 + 18 * 3, 348 + 11, root2, 10.5, 0, 0, Color.DARKBLUE);
        balls[6].setId(6);

        balls[7] = new Ball(785 + 18 * 3, 348 - 11, root2, 10.5, 0, 0, Color.CYAN);
        balls[7].setId(7);

        balls[8] = new Ball(785 + 18 * 4, 348, root2, 10.5, 0, 0, Color.RED);
        balls[8].setId(8);

        balls[9] = new Ball(300, 362, root2, 10.5, 0, 0, Color.WHITE);
        balls[9].setId(9);
//        balls[9].setScaleX(1.5);
//        balls[9].setScaleY(1.5);
        balls[10] = new Ball(235, 25, root2, 9, 0, 0, Color.VIOLET);
        balls[10].setId(10);

        balls[11] = new Ball(235 + 35, 25, root2, 9, 0, 0, Color.DARKRED);
        balls[11].setId(11);

        balls[12] = new Ball(235 + 35 * 2, 25, root2, 9, 0, 0, Color.GREENYELLOW);
        balls[12].setId(12);

        balls[13] = new Ball(235 + 35 * 3, 25, root2, 9, 0, 0, Color.DARKGREEN);
        balls[13].setId(13);

        balls[14] = new Ball(235 + 35 * 4, 25, root2, 9, 0, 0, Color.YELLOW);
        balls[14].setId(14);

        balls[15] = new Ball(235 + 35 * 5, 25, root2, 9, 0, 0, Color.DARKORANGE);
        balls[15].setId(15);

        balls[16] = new Ball(235 + 35 * 6, 25, root2, 9, 0, 0, Color.DARKBLUE);
        balls[16].setId(16);

        balls[17] = new Ball(235 + 35 * 7, 25, root2, 9, 0, 0, Color.CYAN);
        balls[17].setId(17);

        balls[18] = new Ball(235 + 35 * 8, 25, root2, 9, 0, 0, Color.RED);
        balls[18].setId(18);

        Image im2 = new Image("/BinaryContent/stick.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(im2);

        iv1.setFitWidth(300);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setCache(true);


        box.setRotate(50);
        box.getChildren().add(iv1);

        root2.getChildren().addAll(box, T1, T2, T3, T4, T5, T6);
        // primaryStage.setScene(new Scene( 300, 275));
        primaryStage.show();

        EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
//                    System.out.println("x=" + mouseEvent.getX());
//                    System.out.println("y=" + mouseEvent.getY());
//                    System.out.println();
                    if (win == 1) {

                        double rotation = 0;
                        double opacity = 1.0;
                        int mousepress = 0;
                        int win = 0;
                        int count = 0;
                        int player = 1;
                        int turn = 1;
                        int isFoul = 0;
                        int i1 = 10;
                        int w = 0;
                        int hit = 0;
//    String path1 = AudioPlayer.class.getResource("/flourish.mp3").toString();
//        Media media1 = new Media(path1);
//        MediaPlayer mp1 = new MediaPlayer(media1);
                        // mp5.stop();
                        mp1.play();
                        primaryStage.setScene(scene);


                    } else if (win != 1) {
                        double mx = mouseEvent.getX();
                        double my = mouseEvent.getY();

                        double bx = balls[9].getCenterX();
                        double by = balls[9].getCenterY();
                        double m_bx = mx - bx;
                        double m_by = my - by;
                        double kx, ky, sx, sy,kon;
                        if (((m_bx) * (m_bx) + (m_by) * (m_by)) > 12 * 12) {
                            //if(mx != balls[9].getCenterX())
                            kon = Math.toDegrees(Math.atan((mouseEvent.getY() - balls[9].getCenterY()) / (mouseEvent.getX() - balls[9].getCenterX())));

                            //else kon = 0;

                            if (my > by) {
                                sy = by - 340 * Math.sin(Math.toRadians(kon));
                                if (Math.sin(Math.toRadians(kon)) < 0) {
                                    sy = by + 340 * Math.sin(Math.toRadians(kon));
                                }
                            } else {
                                sy = by + 340 * Math.sin(Math.toRadians(kon));
                                if (Math.sin(Math.toRadians(kon)) < 0) {
                                    sy = by - 340 * Math.sin(Math.toRadians(kon));
                                }
                            }

                            if (mx > bx) {
                                sx = bx - 340 * Math.cos(Math.toRadians(kon));
                                if (Math.cos(Math.toRadians(kon)) < 0) {
                                    sx = bx + 340 * Math.cos(Math.toRadians(kon));
                                }
                            } else {
                                sx = bx + 340 * Math.cos(Math.toRadians(kon));
                                if (Math.cos(Math.toRadians(kon)) < 0) {
                                    sx = bx - 340 * Math.cos(Math.toRadians(kon));
                                }
                            }
                            box.setLayoutX(sx - 150.5);
                            box.setLayoutY(sy - 150.5);
                        }
                    }
                }

                if(mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {

                    double mx = mouseEvent.getX();
                    double my = mouseEvent.getY();
                    double bx = balls[9].getCenterX();
                    double by = balls[9].getCenterY();
                    double m_bx = mx - bx;
                    double m_by = my - by;
                    String s1;
                    VelocityPos(bx, by, mx, my, m_bx, m_by);
                    if(turn%2==1) {
                        s1 = "Position " + bx + " " + by + " " + mx + " " + my + " " + m_bx + " " + m_by + " " + "saifur";

                    }

                    else s1 = "Position " + bx + " " + by + " " + mx + " " + my + " " + m_bx + " " + m_by + " " + "badhon";
                    System.out.println("Write" + s1);
                    c.nc.write(s1);


                }
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    //work = 1;
                    if (hit == 2) {
                        double mx = mouseEvent.getX();
                        double my = mouseEvent.getY();
                        double bx = balls[9].getCenterX();
                        double by = balls[9].getCenterY();
                        double m_bx = mx - bx;
                        double m_by = my - by;
                        if (((m_bx) * (m_bx) + (m_by) * (m_by)) < (10 * 10)) {
                            if (balls[9].getVelocity() == 0) {

                                balls[9].setCenterX(mouseEvent.getX());
                                balls[9].setCenterY(mouseEvent.getY());
                            }

                        }

                    }
                }
               if (mouseEvent.getEventType() == MouseEvent.MOUSE_MOVED) {
                    double mx = mouseEvent.getX();
                    double my = mouseEvent.getY();
                    double bx = balls[9].getCenterX();
                    double by = balls[9].getCenterY();
                    double m_bx = mx - bx;
                    double m_by = my - by;

                   // changeStick(mx, my, bx, by, m_bx, m_by);

                   // String str = "stick " + mx + " " + my + " " + bx + " " + by + " " + m_bx + " " + m_by + " " + "saifur";
                   // System.out.println(" stick" + str);
                   // c.nc.write(str);

                    double kx, ky, sx, sy;
                    //balls[9].setCenterY(mouseEvent.getY());
                    double kon = Math.toDegrees(Math.atan((mouseEvent.getY() - balls[9].getCenterY()) / (mouseEvent.getX() - balls[9].getCenterX())));

                    if (my > by) {
                        sy = by - 250 * Math.sin(Math.toRadians(kon));
                        if (Math.sin(Math.toRadians(kon)) < 0) {
                            sy = by + 250 * Math.sin(Math.toRadians(kon));
                        }
                    } else {
                        sy = by + 250 * Math.sin(Math.toRadians(kon));
                        if (Math.sin(Math.toRadians(kon)) < 0) {
                            sy = by - 250 * Math.sin(Math.toRadians(kon));
                        }
                    }

                    if (mx > bx) {
                        sx = bx - 250 * Math.cos(Math.toRadians(kon));
                        if (Math.cos(Math.toRadians(kon)) < 0) {
                            sx = bx + 250 * Math.cos(Math.toRadians(kon));
                        }
                    } else {
                        sx = bx + 250 * Math.cos(Math.toRadians(kon));
                        if (Math.cos(Math.toRadians(kon)) < 0) {
                            sx = bx - 250 * Math.cos(Math.toRadians(kon));
                        }
                    }
                    box.setLayoutX(sx - 150.5);
                    box.setLayoutY(sy - 150.5);

                    if (mx >= bx) {
                        rotation = kon + 45;
                    } else {
                        rotation = kon - 135;
                    }
                    if (balls[9].getVelocity() > 0) {
                        box.setOpacity(0);
                    } else {
                        box.setOpacity(100);
                    }

                    box.setRotate(rotation);

                }
                }

        };


                scene2.setOnMouseClicked(mouseHandler);
                scene2.setOnMouseDragged(mouseHandler);
                scene2.setOnMouseEntered(mouseHandler);
                scene2.setOnMouseExited(mouseHandler);
                scene2.setOnMouseMoved(mouseHandler);
                scene2.setOnMousePressed(mouseHandler);
                scene2.setOnMouseReleased(mouseHandler);

                scene2.setOnMouseClicked(mouseHandler);
                scene2.setOnMouseDragged(mouseHandler);
                scene2.setOnMouseEntered(mouseHandler);
                scene2.setOnMouseExited(mouseHandler);
                scene2.setOnMouseMoved(mouseHandler);
                scene2.setOnMousePressed(mouseHandler);
                scene2.setOnMouseReleased(mouseHandler);

            }




            public static void main(String[] args) {
                launch(args);
            }
    }


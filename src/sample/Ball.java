package sample;


import javafx.scene.Group;
        import javafx.scene.Scene;
        import javafx.scene.media.Media;
        import javafx.scene.media.MediaPlayer;
        import javafx.scene.paint.Color;
        import javafx.scene.shape.Circle;
        import sun.audio.AudioPlayer;


class Ball extends Circle{
    double vx;
    double vy;
    int id;
    //double centerX;
    //double centerY;
    double Radious;
   // ClientMain c;

    Ball(double x,double y,Group root, double radius,double vvx,double vvy,Color color){
        //super(x,y,radius,Color.RED);
        super(x,y,radius,color);
        //  centerX=x;
        //centerY=y;
        root.getChildren().add(this);
        vx=vvx;
        vy=vvy;
        Radious=radius;
    }public double Radious(){
        return Radious;
    }
    public void setRadious(double x){
        Radious=x;
    }
    public void setId(int x){
        id=x;
    }
    public int getid(){
        return id;
    }

    public double getX(){
        return this.getCenterX();
    }
    public double getY(){
        return this.getCenterY();
    }
    public double getVx(){
        return vx;
    }
    public double getVy(){
        return vy;
    }
    public double getVelocity(){
        return Math.sqrt(vx*vx+vy*vy);
    }
    public double angle(){
        return Math.atan2(vy,vx);
    }

    public void setVx(double x){
        vx=x;
    }
    public void setVy(double y){
        vy=y;
    }

    public void setX(double x){ setCenterX(x);}
    public void setY(double y){ setCenterX(y);}


    public void updateCenter(double time){

        setCenterX(getCenterX()+vx*time);
        setCenterY(getCenterY()+vy*time);



        if(vx>0.0011){vx=vx-0.001;vx=vx/1.02;}
        if(vx<(-0.0011)){vx=vx+0.001;vx=vx/1.02;}
        if((vx<=0.011)&&(vx>=(-0.011)))
        {
            vx=0;
        }
        if(vy>0.0011){vy=vy-0.001;vy=vy/1.02;}
        if(vy<(-0.0011)){vy=vy+0.001;vy=vy/1.02;}
        if((vy<=0.011) && (vy>=(-0.011)))
        {
            vy=0;
        }

    }

    public void collision(){
        Scene scene=getScene();
        // double x=scene.getWidth();
        // double y=scene.getHeight();


        if(getCenterX()+getRadius()>=946){if(vx>0)vx=-vx/1.1;}
        if(getCenterX()-getRadius()<=252){if(vx<0)vx=-vx/1.1;}

       // if(getCenterY()+getRadius()>=501){if(vy>0)vy=-vy/1.1;}
       // if(getCenterY()-getRadius()<=196){if(vy<0)vy=-vy/1.1;}
        if(getCenterY()+getRadius()>=528){if(vy>0)vy=-vy/1.1;}
        if(getCenterY()-getRadius()<=169){if(vy<0)vy=-vy/1.1;}


    }

}


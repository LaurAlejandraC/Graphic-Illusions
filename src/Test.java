import processing.core.PApplet;

/**
 * Created by laura on 2/25/17.
 */
public class Test extends PApplet {
      /*

 Completar la información para cada ilusión implementada

 Ilusión 1: Scintillating Grid
 Author: Rupert Russell, October 2, 2010
 Implementado desde cero, adaptado o transcripción literal: Transcripción literal
 del código encontrado acá: https://www.openprocessing.org/sketch/26605
 Etiquetas (que describen la ilusión, como su categoría, procedencia, etc.): ilusión psicológica, rejilla, Hermann

 */

    int illusions = 7;
    int current = 1;
    boolean active = true;
    int lines = 25;
    float currentWidth = 0.0f;
    int pos_x = 0, speed = 2;
    boolean advancing_mach = true, advancing_stuart = true;
    float[] cafeInitialPositions = null;

    public static void main(String [] args){
        PApplet.main(Test.class, args);
    }

    public void settings(){
        size(800, 800);
    }

    public void setup() {
    }

    public void draw() {
        //background(255);
        pushStyle();
        switch(current) {
            case 1:
                scintillating();
                break;
            // implement from here. Don't forget to add break for each case
            case 2:
                machBand();
                break;
            case 3:
                colorGradient();
                break;
            case 4:
                stuartAnstis();
                break;
            case 5:
                ebbinghaus();
                break;
            case 6:
                cafeWall();
                break;
            case 7:
                kanizsaTriangle();
                break;
                //println("implementation is missed!");
        }
        popStyle();
    }

    // switch illusion using the spacebar
    public void keyPressed() {
        if (key == ' ')
            current = current < illusions ? current+1 : 1;
        if (key == 'a')
            active = !active;
        // increases or decreases the amount of lines for stuartAnstis illusion
        if (key == 'z' && lines > 2)
            lines-=2;
        if (key == 'x')
            lines+=2;
    }

    // illusions
    void scintillating() {
        background(0);          // black background

        //style
        strokeWeight(3);        // medium weight lines
        smooth();               // antialias lines
        stroke(100, 100, 100);  // dark grey colour for lines

        int step = 25;          // grid spacing

        //vertical lines
        for (int x = step; x < width; x = x + step) {
            line(x, 0, x, height);
        }

        //horizontal lines
        for (int y = step; y < height; y = y + step) {
            line(0, y, width, y);
        }

        // Circles
        if (active) {
            ellipseMode(CENTER);
            stroke(255, 255, 255);  // white circles
            for (int i = step; i < width -5; i = i + step) {
                for (int j = step; j < height -15; j = j + step) {
                    strokeWeight(6);
                    point(i, j);
                    strokeWeight(3);
                }
            }
        }
    }

    void machBand() {
        if( active ){
            background(255);
            noStroke();

            fill(66);
            rect(250, 260, 300, 50+currentWidth);
            fill(86);
            rect(150,340,500,50+currentWidth);
            fill(122);
            rect(50, 420, 700, 50+currentWidth);
            if (advancing_mach)
                currentWidth += 0.2f;
            else
                currentWidth -= 0.2f;

            if (currentWidth > 30.5f){
                advancing_mach = !advancing_mach;
                delay(3000);
            }
            else if (currentWidth < 0){
                advancing_mach = !advancing_mach;
            }
        }
    }

    void colorGradient(){
        background(255);
        int lineSize = Math.round(width - (width * 0.1f));
        int border = Math.round((width - lineSize)/2);
        float colorValue;

        if (active)
            for (int i = 0; i < width; ++i){
                colorValue = map(i, 0, width, 50, 201);
                stroke(colorValue);
                line(i, 0, i, height);
            }

        noStroke();
        fill(120);
        rect(border,350,width-(border*2),100);
    }

    void stuartAnstis(){
        background(255);
        float space = width / (lines * 2.0f);
        stroke(0);
        fill(0);
        for( int i = 0; i <= lines; ++i ){
            rect(2*i*space, 0, space, height);
        }

        if( advancing_stuart )
            pos_x += speed;
        else
            pos_x -= speed;

        if(pos_x <= 0 || pos_x+100 >= width)
            advancing_stuart = !advancing_stuart;

        noStroke();
        fill(255, 255, 0);
        rect(pos_x, 500, 100, 50);
        fill(0,0,102);
        rect(pos_x, 200, 100, 50);
    }

    void ebbinghaus(){
        background(255);
        noStroke();
        fill(255,128,0);
        ellipse(250, 400, 80, 80);
        ellipse(550, 400, 80, 80);
        fill(153, 204,255);

        if( active ){
            ellipse(120, 400, 120, 120);
            ellipse(380, 400, 120, 120);
            double angle = Math.toRadians(360 / 6);
            float[] coordinates = generateCoordinates(angle, 130);
            float x = coordinates[0], y = coordinates[1];

            ellipse(250 + x,400 + y,120, 120);
            ellipse(250 + x,400 - y,120, 120);
            ellipse(250 - x,400 + y,120, 120);
            ellipse(250 - x,400 - y,120, 120);

            ellipse(610, 400, 25, 25);
            ellipse(490, 400, 25, 25);
            ellipse(550, 460, 25, 25);
            ellipse(550, 340, 25, 25);
            angle = Math.toRadians(360 / 8);
            coordinates = generateCoordinates(angle, 60);
            x = coordinates[0];
            y = coordinates[1];
            ellipse(550 + x,400 + y,25, 25);
            ellipse(550 + x,400 - y,25, 25);
            ellipse(550 - x,400 + y,25, 25);
            ellipse(550 - x,400 - y,25, 25);
        }
    }

    void cafeWall(){
        int amountRows = 10, amountColumns = 10;
        background(255);
        float customHeight = height/amountRows;
        float customWidth = width/amountColumns;
        if( cafeInitialPositions == null)
            initializePositions(amountRows, customWidth);

        for (int i = 0; i < amountRows; ++i){
            strokeWeight(4);
            stroke(155);
            line(0, i*customHeight,width, i*customHeight);
            noStroke();
            fill(0);
            if(active)
                for (int j = 0; j < amountColumns; ++j){
                    rect(2 * j * customWidth + cafeInitialPositions[i], i * customHeight, customWidth, customHeight);
                }
        }
    }

    void kanizsaTriangle(){
        background(80);
        noFill();
        strokeWeight(2);
        stroke(0);
        triangle(200, 500, 600, 500, 400, 153.59f);
        noStroke();
        if( active ){
            fill(0);
            ellipse(200,273.59f, 120, 120);
            ellipse(600,273.59f, 120, 120);
            ellipse(400, 620.2f, 120, 120);
        }
        fill(80);
        triangle(200, 273.59f, 600, 273.59f, 400, 620.2f);
    }

    //Auxiliary functions
    float[] generateCoordinates( double angle, int distance ){
        float[] coordinates = new float[2];
        coordinates[0] = (float)(distance * (Math.cos(angle)));
        coordinates[1] = (float)(distance * (Math.sin(angle)));
        return coordinates;
    }

    void initializePositions(int amountRows, float customWidth){
        cafeInitialPositions = new float[amountRows];
        float range = customWidth * 0.5f;

        for (int i = 0; i < amountRows; ++i){
            cafeInitialPositions[i] = (float) (Math.random() * range);
        }
    }
}

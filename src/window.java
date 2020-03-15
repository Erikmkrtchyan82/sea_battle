import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class window extends JPanel {

    protected int tileLength = 60;
    public  static mainWindow frame;

    protected int[][] four, one;
    protected int[][][] three, two;

    protected static final int SEA = 0;
    protected static final int EMPTY = 2;
    protected static  final int NEWEMPTY=3;
    protected static final int BOAT = 1;
    protected static final int HITTED = -1;
    protected static final int DESTROYED = -2;

    protected static final int ONE = 4;
    protected static final int TWO = 3;
    protected static final int THREE = 2;
    protected static final int FOUR = 1;

    protected static Image sea, hit, boat, destroy,empty,newempty;
    protected static Image[] defLetter=new Image[10], defNumber=new Image[10], sNumber=new Image[10], sLetter=new Image[10];

    public window(){
        defNumber = new Image[10];
        defLetter = new Image[10];
        sNumber = new Image[10];
        sLetter = new Image[10];
    }

    public synchronized void missedSound(){
        try{
            File soundFile=new File("sounds/missed.wav");
            AudioInputStream audioIn= AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format=audioIn.getFormat();
            DataLine.Info info=new DataLine.Info(Clip.class,format);
            Clip clip=(Clip)AudioSystem.getLine(info);
            clip.open(audioIn);
            clip.start();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public synchronized void hittedSound(){
        try{
            File soundFile=new File("sounds/hitted.wav");
            AudioInputStream audioIn= AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format=audioIn.getFormat();
            DataLine.Info info=new DataLine.Info(Clip.class,format);
            Clip clip=(Clip)AudioSystem.getLine(info);
            clip.open(audioIn);
            clip.start();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public synchronized void destroyedSound(){
        try{
            File soundFile=new File("sounds/destroyed.wav");
            AudioInputStream audioIn= AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format=audioIn.getFormat();
            DataLine.Info info=new DataLine.Info(Clip.class,format);
            Clip clip=(Clip)AudioSystem.getLine(info);
            clip.open(audioIn);
            clip.start();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static synchronized void openSound(){
        try{
            File soundFile=new File("sounds/open.wav");
            AudioInputStream audioIn= AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format=audioIn.getFormat();
            DataLine.Info info=new DataLine.Info(Clip.class,format);
            Clip clip=(Clip)AudioSystem.getLine(info);
            clip.open(audioIn);
            clip.start();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected synchronized boolean control(int[][] boats, int i, int j) {
        if (boats[i][j] == BOAT) {
            return false;
        } else {
            if (i == 0 && j == 0) {
                if (boats[i + 1][j] == BOAT || boats[i + 1][j + 1] == BOAT || boats[i][j + 1] == BOAT) {
                    return false;
                }
            }
            if (i == 0 && j == 9) {
                if (boats[i][j - 1] == BOAT || boats[i + 1][j] == BOAT || boats[i + 1][j - 1] == BOAT) {
                    return false;
                }
            }
            if (i == 9 && j == 0) {
                if (boats[i][j + 1] == BOAT || boats[i - 1][j] == BOAT || boats[i - 1][j + 1] == BOAT) {
                    return false;
                }
            }
            if (i == 9 && j == 9) {
                if (boats[i][j - 1] == BOAT || boats[i - 1][j] == BOAT || boats[i - 1][j - 1] == BOAT) {
                    return false;
                }
            }
            if (i == 0 && j > 0 && j < 9) {
                if (boats[i][j - 1] == BOAT || boats[i][j + 1] == BOAT || boats[i + 1][j - 1] == BOAT || boats[i + 1][j] == BOAT || boats[i + 1][j + 1] == BOAT) {
                    return false;
                }
            }
            if (i == 9 && j > 0 && j < 9) {
                if (boats[i][j - 1] == BOAT || boats[i][j + 1] == BOAT || boats[i - 1][j - 1] == BOAT || boats[i - 1][j] == BOAT || boats[i - 1][j + 1] == BOAT) {
                    return false;
                }
            }
            if (j == 0 && i > 0 && i < 9) {
                if (boats[i - 1][j] == BOAT || boats[i + 1][j] == BOAT || boats[i - 1][j + 1] == BOAT || boats[i][j + 1] == BOAT || boats[i + 1][j + 1] == BOAT) {
                    return false;
                }
            }
            if (j == 9 && i > 0 && i < 9) {
                if (boats[i - 1][j] == BOAT || boats[i + 1][j] == BOAT || boats[i - 1][j - 1] == BOAT || boats[i][j - 1] == BOAT || boats[i + 1][j - 1] == BOAT) {
                    return false;
                }
            }
        }
        if (i > 0 && i < 9 && j > 0 && j < 9)
            for (int i1 = i - 1; i1 < i + 2; i1++) {
                for (int j1 = j - 1; j1 < j + 2; j1++) {
                    if (boats[i1][j1] == BOAT) {
                        return false;
                    }
                }
            }
        return true;
    }

    protected synchronized void loadImg() {
        ImageIcon iSea = new ImageIcon("pictures/board/blue.png");
        ImageIcon iHit = new ImageIcon("pictures/board/boat_red.png");
        ImageIcon iDestroy = new ImageIcon("pictures/board/boat_black.png");
        ImageIcon iBoat = new ImageIcon("pictures/board/boat_white.png");
        ImageIcon iEmpty = new ImageIcon("pictures/board/x.png");
        ImageIcon iEmpty_n = new ImageIcon("pictures/board/x_red.png");
        sea = iSea.getImage().getScaledInstance(tileLength, tileLength, Image.SCALE_SMOOTH);
        boat = iBoat.getImage().getScaledInstance(tileLength, tileLength, Image.SCALE_SMOOTH);
        hit = iHit.getImage().getScaledInstance(tileLength, tileLength, Image.SCALE_SMOOTH);
        empty = iEmpty.getImage().getScaledInstance(tileLength, tileLength, Image.SCALE_SMOOTH);
        newempty = iEmpty_n.getImage().getScaledInstance(tileLength, tileLength, Image.SCALE_SMOOTH);
        destroy = iDestroy.getImage().getScaledInstance(tileLength, tileLength, Image.SCALE_SMOOTH);
        for (int i = 0; i < 10; i++) {
            defNumber[i] = new ImageIcon("pictures/numbers/" + i + "-d.png").getImage().getScaledInstance(tileLength, tileLength, Image.SCALE_SMOOTH);
            defLetter[i] = new ImageIcon("pictures/letters/" + i + "-dl.png").getImage().getScaledInstance(tileLength, tileLength, Image.SCALE_SMOOTH);
            sNumber[i] = new ImageIcon("pictures/numbers/" + i + "-s.png").getImage().getScaledInstance(tileLength, tileLength, Image.SCALE_SMOOTH);
            sLetter[i] = new ImageIcon("pictures/letters/" + i + "-sl.png").getImage().getScaledInstance(tileLength, tileLength, Image.SCALE_SMOOTH);
        }
    }

    protected synchronized void search(int[][] boats,int x, int y) {

        int boatName = 0;

        for (int i = 0; i < ONE; i++) {
            if (x == one[i][0] && y == one[i][1]) {
                boatName = ONE;
                break;
            }
        }

        if(boatName==0)
            for (int k = 0; k < 3; k++) {
                for (int i = 0; i < 2; i++) {
                    if (x == two[k][i][0] && y == two[k][i][1]) {
                        boatName = TWO;
                        break;
                    }
                }
                if(boatName!=0)
                    break;
            }

        if(boatName==0)
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (x == three[i][j][0] && y == three[i][j][1]) {
                        boatName = THREE;
                        break;
                    }
                }
                if(boatName!=0)
                    break;
            }

        if(boatName==0)
            for (int i = 0; i < 4; i++) {
                if (x == four[i][0] && y == four[i][1]) {
                    boatName = FOUR;
                    break;
                }
            }

        int count = 0;
        switch (boatName) {
            case ONE:
                hitTheBoat(boats,x, y, ONE);
                break;
            case TWO:
                int two_k=-1;
                for (int k = 0; k < 3; k++) {
                    for (int i = 0; i < 2; i++) {
                        if ((x==two[k][i][0] && y==two[k][i][1]) &&(boats[two[k][i][0]][two[k][i][1]] == HITTED)) {
                            two_k=k;
                            break;
                        }
                    }
                    if(two_k!=-1)
                        break;
                }
                for (int i = 0; i < 2; i++) {
                    if (boats[two[two_k][i][0]][two[two_k][i][1]] == HITTED) {
                        count++;
                    }
                }
                if (count == 2)
                    hitTheBoat(boats,x, y, TWO);
                else
                    hittedSound();
                break;
            case THREE:
                int three_k=-1;
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 3; j++) {
                        if ((x==three[i][j][0] && y==three[i][j][1])&&(boats[three[i][j][0]][three[i][j][1]] == HITTED)) {
                            three_k=i;
                            break;
                        }
                    }
                    if(three_k!=-1)
                        break;
                }
                for (int i = 0; i < 3; i++) {
                    if (boats[three[three_k][i][0]][three[three_k][i][1]] == HITTED) {
                        count++;
                    }
                }
                if (count == 3)
                    hitTheBoat(boats,x, y, THREE);
                else
                    hittedSound();
                break;
            case FOUR:
                for (int i = 0; i < 4; i++) {
                    if (boats[four[i][0]][four[i][1]] == HITTED) {
                        ++count;
                    }
                }
                if (count == 4)
                    hitTheBoat(boats,x, y, FOUR);
                else
                    hittedSound();
                break;
            default:
                break;
        }
    }

    protected synchronized void hitTheBoat(int[][] boats,int x, int y, int tilesCount) {

        switch (tilesCount) {
            case ONE:
                destroy(boats,x, y);
                break;
            case TWO:
                int two_k = -1;
                for (int k = 0; k < 3; k++) {
                    for (int i = 0; i < 2; i++) {
                        if (x == two[k][i][0] && y == two[k][i][1]) {
                            two_k = k;
                            break;
                        }
                    }
                }
                for (int i = 0; i < 2; i++) {
                    destroy(boats,two[two_k][i][0], two[two_k][i][1]);
                }
                break;
            case THREE:
                int three_k = -1;
                for (int k = 0; k < 2; k++) {
                    for (int i = 0; i < 3; i++) {
                        if (x == three[k][i][0] && y == three[k][i][1]) {
                            three_k = k;
                            break;
                        }
                    }
                }
                for (int i = 0; i < 3; i++) {
                    destroy(boats,three[three_k][i][0], three[three_k][i][1]);
                }
                break;
            case FOUR:
                for (int i = 0; i < 4; i++) {
                    destroy(boats,four[i][0], four[i][1]);
                }
                break;
            default:
                break;
        }
        destroyedSound();
    }

    protected synchronized void destroy(int[][] boats,int x, int y) {

        int ax = x - 1, bx = x + 2, ay = y - 1, by = y + 2;

        if(x==0){
            ax=x;
        }else
        if(x==9){
            bx=x+1;
        }
        if(y==0){
            ay=y;
        }else
        if(y==9){
            by=y+1;
        }

        for (int i = ax; i < bx; i++) {
            for (int j = ay; j < by; j++) {
                if (boats[i][j] == HITTED)
                    boats[i][j] = DESTROYED;
                if (boats[i][j] == SEA)
                    boats[i][j] = EMPTY;
            }
        }
    }

    protected synchronized int findX(int x,int tileLength) {
        int k = -1;
        for (int i = x; i > getX()+tileLength+10; i -= tileLength+1) {
            k++;
        }
        return k;
    }

    protected synchronized int findY(int y,int tileLength) {
        int k = -1;
        for (int i = y; i > getY()+tileLength+10; i -= tileLength+1) {
            k++;
        }
        return k;
    }

    public synchronized void winner(String name){
        String title;
        ImageIcon icon;
        if(name.equalsIgnoreCase("you win")){
            title="Congratulations".toUpperCase();
            icon=new ImageIcon("pictures/happy.png");
        }else{
            title="it's a pity".toUpperCase();
            icon=new ImageIcon("pictures/sad.png");
        }
        JOptionPane.showMessageDialog(null,name,null,JOptionPane.INFORMATION_MESSAGE,icon);
        frame.repaint();
        int choose=JOptionPane.showConfirmDialog(null,"Play again?",title,JOptionPane.YES_NO_OPTION);
        if(choose==JOptionPane.YES_OPTION){
            frame.setVisible(false);
            frame=null;
            new mainWindow();
        }else{
            System.exit(0);
        }
    }

    public abstract int[][] getArray();
    public abstract int getTileLength();
    public abstract void randomInit();
}
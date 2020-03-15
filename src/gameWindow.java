import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class gameWindow extends window {

    public int count;
    private int[][] comp;
    private static Image[] copyDefLetter, copyDefNumber;
    private playerWindow playerWindow;
    private int lastX = -1, lastY = -1;
    public mouseEvent mouse;

    public gameWindow(int x, int y, int width, int height, playerWindow playerWindow) {

        super();

        count = 0;
        comp = new int[10][10];

        four = new int[4][2];
        three = new int[2][3][2];
        two = new int[3][2][2];
        one = new int[4][2];

        copyDefLetter = new Image[10];
        copyDefNumber = new Image[10];

        setBounds(x, y, width, height);
        setLayout(null);

        this.playerWindow = playerWindow;

        mouse = new mouseEvent(this);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
//        addMouseListener(new mouseEvent(this));
//        addMouseMotionListener(new mouseEvent(this));

        loadImg();

        for (int i = 0; i < 10; i++) {
            copyDefLetter[i] = defLetter[i];
            copyDefNumber[i] = defNumber[i];

        }
        randomInit();
        openSound();
    }

    public void randomInit() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                comp[i][j] = SEA;
            }
        }

        Random r = new Random();
        int rand1, rand2;

        //for 4
        for (int i = 0; i < FOUR; i++) {
            rand1 = r.nextInt(10);
            rand2 = r.nextInt(10);
            if (rand1 < 3 || rand1 > 6) {
                if (rand2 < 6) {
                    comp[rand1][rand2] = BOAT;
                    comp[rand1][rand2 + 1] = BOAT;
                    comp[rand1][rand2 + 2] = BOAT;
                    comp[rand1][rand2 + 3] = BOAT;
                    four[0][0] = rand1;
                    four[0][1] = rand2;
                    four[1][0] = rand1;
                    four[1][1] = rand2 + 1;
                    four[2][0] = rand1;
                    four[2][1] = rand2 + 2;
                    four[3][0] = rand1;
                    four[3][1] = rand2 + 3;
                } else {
                    comp[rand1][rand2] = BOAT;
                    comp[rand1][rand2 - 1] = BOAT;
                    comp[rand1][rand2 - 2] = BOAT;
                    comp[rand1][rand2 - 3] = BOAT;
                    four[0][0] = rand1;
                    four[0][1] = rand2;
                    four[1][0] = rand1;
                    four[1][1] = rand2 - 1;
                    four[2][0] = rand1;
                    four[2][1] = rand2 - 2;
                    four[3][0] = rand1;
                    four[3][1] = rand2 - 3;
                }
            } else {
                comp[rand1][rand2] = BOAT;
                comp[rand1 + 1][rand2] = BOAT;
                comp[rand1 + 2][rand2] = BOAT;
                comp[rand1 + 3][rand2] = BOAT;
                four[0][0] = rand1;
                four[0][1] = rand2;
                four[1][0] = rand1 + 1;
                four[1][1] = rand2;
                four[2][0] = rand1 + 2;
                four[2][1] = rand2;
                four[3][0] = rand1 + 3;
                four[3][1] = rand2;
            }
        }

        //for 3
        for (int i = 0; i < THREE; i++) {
            rand1 = r.nextInt(10);
            rand2 = r.nextInt(10);
            if (rand1 < 2 || rand1 > 7) {
                if (rand2 < 7) {
                    if (control(comp, rand1, rand2) && control(comp, rand1, rand2 + 1) && control(comp, rand1, rand2 + 2)) {
                        comp[rand1][rand2] = BOAT;
                        comp[rand1][rand2 + 1] = BOAT;
                        comp[rand1][rand2 + 2] = BOAT;
                        three[i][0][0] = rand1;
                        three[i][0][1] = rand2;
                        three[i][1][0] = rand1;
                        three[i][1][1] = rand2 + 1;
                        three[i][2][0] = rand1;
                        three[i][2][1] = rand2 + 2;
                    } else {
                        --i;
                    }
                } else {
                    if (control(comp, rand1, rand2) && control(comp, rand1, rand2 - 1) && control(comp, rand1, rand2 - 2)) {
                        comp[rand1][rand2] = BOAT;
                        comp[rand1][rand2 - 1] = BOAT;
                        comp[rand1][rand2 - 2] = BOAT;
                        three[i][0][0] = rand1;
                        three[i][0][1] = rand2;
                        three[i][1][0] = rand1;
                        three[i][1][1] = rand2 - 1;
                        three[i][2][0] = rand1;
                        three[i][2][1] = rand2 - 2;
                    } else {
                        --i;
                    }
                }
            } else {
                if (control(comp, rand1, rand2) && control(comp, rand1 + 1, rand2) && control(comp, rand1 + 2, rand2)) {
                    comp[rand1][rand2] = BOAT;
                    comp[rand1 + 1][rand2] = BOAT;
                    comp[rand1 + 2][rand2] = BOAT;
                    three[i][0][0] = rand1;
                    three[i][0][1] = rand2;
                    three[i][1][0] = rand1 + 1;
                    three[i][1][1] = rand2;
                    three[i][2][0] = rand1 + 2;
                    three[i][2][1] = rand2;
                } else {
                    --i;
                }
            }
        }

        //for 2
        for (int i = 0; i < TWO; i++) {
            rand1 = r.nextInt(10);
            rand2 = r.nextInt(10);
            if (rand1 < 1 || rand1 > 8) {
                if (rand2 < 8) {
                    if (control(comp, rand1, rand2) && control(comp, rand1, rand2 + 1)) {
                        comp[rand1][rand2] = BOAT;
                        comp[rand1][rand2 + 1] = BOAT;
                        two[i][0][0] = rand1;
                        two[i][0][1] = rand2;
                        two[i][1][0] = rand1;
                        two[i][1][1] = rand2 + 1;
                    } else {
                        --i;
                    }
                } else {
                    if (control(comp, rand1, rand2) && control(comp, rand1, rand2 - 1)) {
                        comp[rand1][rand2] = BOAT;
                        comp[rand1][rand2 - 1] = BOAT;
                        two[i][0][0] = rand1;
                        two[i][0][1] = rand2;
                        two[i][1][0] = rand1;
                        two[i][1][1] = rand2 - 1;
                    } else {
                        --i;
                    }
                }
            } else {
                if (control(comp, rand1, rand2) && control(comp, rand1 + 1, rand2)) {
                    comp[rand1][rand2] = BOAT;
                    comp[rand1 + 1][rand2] = BOAT;
                    two[i][0][0] = rand1;
                    two[i][0][1] = rand2;
                    two[i][1][0] = rand1 + 1;
                    two[i][1][1] = rand2;
                } else {
                    --i;
                }
            }
        }

        //for 1
        for (int i = 0; i < ONE; i++) {
            rand1 = r.nextInt(10);
            rand2 = r.nextInt(10);
            if (control(comp, rand1, rand2)) {
                comp[rand1][rand2] = BOAT;
                one[i][0] = rand1;
                one[i][1] = rand2;
            } else {
                --i;
            }
        }
    }

    public int[][] getArray() {
        return this.comp;
    }

    public int getTileLength() {
        return this.tileLength;
    }

    public void play() {
        if (lastX != -1) {
            comp[lastX][lastY] = EMPTY;
        }
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
    }

    @Override
    public synchronized void paintComponent(Graphics g) {

        frame.repaint();
        for (int i = 0; i < 10; i++) {
            g.drawImage(copyDefNumber[i], 5, (tileLength + 1) * (i + 1) + 10, this);
            g.drawImage(copyDefLetter[i], 5 + (tileLength + 1) * (i + 1), 10, this);
        }

        int x_location, y_location;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                x_location = (tileLength + 1) * (j + 1) + 5;
                y_location = (tileLength + 1) * (i + 1) + 10;
                g.drawImage(sea, x_location, y_location, this);
                switch (comp[i][j]) {
                    case NEWEMPTY:
                        g.drawImage(newempty, x_location, y_location, this);
                        break;
                    case EMPTY:
                        g.drawImage(empty, x_location, y_location, this);
                        break;
                    case HITTED:
                        g.drawImage(hit, x_location, y_location, this);
                        break;
                    case DESTROYED:
                        g.drawImage(destroy, x_location, y_location, this);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static class mouseEvent extends MouseAdapter {
        gameWindow panel;
        int[][] boats;

        mouseEvent(gameWindow panel) {
            this.panel = panel;
            boats = panel.getArray();
        }

        @Override
        public synchronized void mouseClicked(MouseEvent e) {
            int i = panel.findY(e.getY(), panel.getTileLength()), j = panel.findX(e.getX(), panel.getTileLength());

            if ((i != -1 && j != -1) && (i < 10 && j < 10)) {
                if (boats[i][j] == SEA) {

                    Thread thread = new Thread( () -> {
                        panel.removeMouseListener(panel.mouse);
                        panel.removeMouseMotionListener(panel.mouse);
                    });
                    thread.start();

                    panel.lastX = i;
                    panel.lastY = j;
                    boats[i][j] = NEWEMPTY;
                    panel.missedSound();
                    for (int k = 0; k < 10; k++) {
                        copyDefNumber[k] = defNumber[k];
                        copyDefLetter[k] = defLetter[k];
                    }
                    panel.playerWindow.t.start();
                } else if (boats[i][j] == BOAT) {
                    boats[i][j] = HITTED;
                    panel.count++;
                    panel.search(boats, i, j);
                    if (panel.count == 20) {
                        panel.removeMouseMotionListener(this);
                        panel.removeMouseListener(this);
                        panel.winner("You win");
                    }
                }
            }
        }

        @Override
        public synchronized void mouseMoved(MouseEvent e) {

            int i = panel.findY(e.getY(), panel.getTileLength()), j = panel.findX(e.getX(), panel.getTileLength());

            for (int k = 0; k < 10; k++) {
                copyDefNumber[k] = defNumber[k];
                copyDefLetter[k] = defLetter[k];
            }

            if ((i != -1 && j != -1) && (i < 10 && j < 10)) {
                copyDefNumber[i] = sNumber[i];
                copyDefLetter[j] = sLetter[j];
            }
            panel.repaint();
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class playerWindow extends window {

    public static int count;
    private int[][] player;
    private static int nextX1, nextY1, nextX2, nextY2;
    private int lastX=-1,lastY=-1;
    private static Image[] copyDefLetter, copyDefNumber;
    private gameWindow gameWindow;

    public Timer t = new Timer(1500, new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            play();
        }
    });


    public playerWindow(int x, int y, int width, int height) {

        super();

        count = 0;
        nextX1 = -1;
        nextY1 = -1;
        nextX2 = -1;
        nextY2 = -1;

        player = new int[10][10];

        four = new int[4][2];
        three = new int[2][3][2];
        two = new int[3][2][2];
        one = new int[4][2];

        copyDefLetter = new Image[10];
        copyDefNumber = new Image[10];

        setLayout(null);

        setBounds(x, y, width, height);

        loadImg();

        for (int i = 0; i < 10; i++) {
            copyDefLetter[i] = defLetter[i];
            copyDefNumber[i] = defNumber[i];
        }
    }

    public void setGameWindow(gameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public int[][] getArray() {
        return this.player;
    }

    public int getTileLength() {
        return this.tileLength;
    }

    private void nextTurn(int x, int y) {

        int ax = x - 1, bx = x + 2, ay = y - 1, by = y + 2;

        if (x == 0) {
            ax = x;
        } else if (x == 9) {
            bx = x + 1;
        }
        if (y == 0) {
            ay = y;
        } else if (y == 9) {
            by = y + 1;
        }


        for (int i = ax; i <bx; i++) {
            for (int j = ay; j < by; j++) {
                if (i == x && j==y)
                    continue;
                if (player[i][j] == BOAT) {
                    if (nextX1 == -1) {
                        nextX1 = i;
                        nextY1 = j;
                    } else if (nextX2 == -1) {
                        nextX2 = i;
                        nextY2 = j;
                    }
                }
            }
        }


/*        if(!vertical && !horizontal){
            for (int i = ax; i < bx; i++) {
                if(i==x)
                    continue;
                if(player[i][y]==HITTED)
                {
                    vertical=true;
                    break;
                }
            }
            if(!vertical){
                for (int j = ay; j < by; j++) {
                    if(j==y)
                        continue;
                    if(player[x][j]==HITTED){
                        horizontal=true;
                        break;
                    }
                }
            }
        }

        if(vertical) {
            for (int i = ax; i < bx; i++) {
                if (i == x)
                    continue;
                if (player[i][y] != DESTROYED && player[i][y] != EMPTY) {
                    if (nextX1 == -1) {
                        nextX1 = i;
                        nextY1 = y;
                    } else if (nextX2 == -1) {
                        nextX2 = i;
                        nextY2 = y;
                    }
                }
            }
            horizontal=false;
        }

        if(horizontal) {
            for (int j = ay; j < by; j++) {
                if (j == y)
                    continue;
                if (player[x][j] != DESTROYED && player[x][j] != EMPTY) {
                    if (nextX1 == -1) {
                        nextX1 = x;
                        nextY1 = j;
                    } else if (nextX2 == -1) {
                        nextX2 = x;
                        nextY2 = j;
                    }
                }
            }
            vertical=false;
        }

        if(!vertical && !horizontal){
            for (int i = ax; i < bx; i++) {
                if (i == x)
                    continue;
                if (player[i][y] != DESTROYED && player[i][y] != EMPTY) {
                    if (nextX1 == -1) {
                        nextX1 = i;
                        nextY1 = y;
                    } else if (nextX2 == -1) {
                        nextX2 = i;
                        nextY2 = y;
                    }
                }
            }
            for (int j = ay; j < by; j++) {
                if (j == y)
                    continue;
                if (player[x][j] != DESTROYED && player[x][j] != EMPTY) {
                    if (nextX1 == -1) {
                        nextX1 = x;
                        nextY1 = j;
                    } else if (nextX2 == -1) {
                        nextX2 = x;
                        nextY2 = j;
                    }
                }
            }
        }*/


    }

    public synchronized void play() {

        if(lastX!=-1){
            player[lastX][lastY]=EMPTY;
        }
        synchronized (this) {
            for (int k = 0; k < 10; k++) {
                copyDefNumber[k] = defNumber[k];
                copyDefLetter[k] = defLetter[k];
            }
            int i, j;
            Random r = new Random();
            do {
                i = r.nextInt(10);
                j = r.nextInt(10);
            } while (player[i][j] == HITTED || player[i][j] == EMPTY || player[i][j] == DESTROYED);
            if ((nextX1 != -1) && (player[nextX1][nextY1] != EMPTY && player[nextX1][nextY1] != DESTROYED && player[nextX1][nextY1] != HITTED)) {
                i = nextX1;
                j = nextY1;
                nextX1 = -1;
                nextY1 = -1;
            } else {

                if ((nextX2 != -1) && (player[nextX2][nextY2] != EMPTY && player[nextX2][nextY2] != DESTROYED && player[nextX2][nextY2] != HITTED)) {
                    i = nextX2;
                    j = nextY2;
                    nextX2 = -1;
                    nextY2 = -1;
                }
            }

            copyDefNumber[i] = sNumber[i];
            copyDefLetter[j] = sLetter[j];

            if (player[i][j] == SEA) {
                player[i][j] = NEWEMPTY;
                lastX=i;
                lastY=j;
                missedSound();
                t.stop();
                gameWindow.play();
            } else if (player[i][j] == BOAT) {
                player[i][j] = HITTED;
                count++;
                search(player, i, j);

                if (count == 20) {
                    t.stop();
                    winner("You lose");
                }
                nextTurn(i, j);
            }
            if (player[i][j] == HITTED) {
                t.start();
            }
        }
    }

    public void randomInit() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                player[i][j] = SEA;
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
                    player[rand1][rand2] = BOAT;
                    player[rand1][rand2 + 1] = BOAT;
                    player[rand1][rand2 + 2] = BOAT;
                    player[rand1][rand2 + 3] = BOAT;
                    four[0][0] = rand1;
                    four[0][1] = rand2;
                    four[1][0] = rand1;
                    four[1][1] = rand2 + 1;
                    four[2][0] = rand1;
                    four[2][1] = rand2 + 2;
                    four[3][0] = rand1;
                    four[3][1] = rand2 + 3;
                } else {
                    player[rand1][rand2] = BOAT;
                    player[rand1][rand2 - 1] = BOAT;
                    player[rand1][rand2 - 2] = BOAT;
                    player[rand1][rand2 - 3] = BOAT;
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
                player[rand1][rand2] = BOAT;
                player[rand1 + 1][rand2] = BOAT;
                player[rand1 + 2][rand2] = BOAT;
                player[rand1 + 3][rand2] = BOAT;
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
                    if (control(player, rand1, rand2) && control(player, rand1, rand2 + 1) && control(player, rand1, rand2 + 2)) {
                        player[rand1][rand2] = BOAT;
                        player[rand1][rand2 + 1] = BOAT;
                        player[rand1][rand2 + 2] = BOAT;
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
                    if (control(player, rand1, rand2) && control(player, rand1, rand2 - 1) && control(player, rand1, rand2 - 2)) {
                        player[rand1][rand2] = BOAT;
                        player[rand1][rand2 - 1] = BOAT;
                        player[rand1][rand2 - 2] = BOAT;
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
                if (control(player, rand1, rand2) && control(player, rand1 + 1, rand2) && control(player, rand1 + 2, rand2)) {
                    player[rand1][rand2] = BOAT;
                    player[rand1 + 1][rand2] = BOAT;
                    player[rand1 + 2][rand2] = BOAT;
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
                    if (control(player, rand1, rand2) && control(player, rand1, rand2 + 1)) {
                        player[rand1][rand2] = BOAT;
                        player[rand1][rand2 + 1] = BOAT;
                        two[i][0][0] = rand1;
                        two[i][0][1] = rand2;
                        two[i][1][0] = rand1;
                        two[i][1][1] = rand2 + 1;
                    } else {
                        --i;
                    }
                } else {
                    if (control(player, rand1, rand2) && control(player, rand1, rand2 - 1)) {
                        player[rand1][rand2] = BOAT;
                        player[rand1][rand2 - 1] = BOAT;
                        two[i][0][0] = rand1;
                        two[i][0][1] = rand2;
                        two[i][1][0] = rand1;
                        two[i][1][1] = rand2 - 1;
                    } else {
                        --i;
                    }
                }
            } else {
                if (control(player, rand1, rand2) && control(player, rand1 + 1, rand2)) {
                    player[rand1][rand2] = BOAT;
                    player[rand1 + 1][rand2] = BOAT;
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
            if (control(player, rand1, rand2)) {
                player[rand1][rand2] = BOAT;
                one[i][0] = rand1;
                one[i][1] = rand2;
            } else {
                --i;
            }
        }
    }

    @Override
    public synchronized void paintComponent(Graphics g) {

        frame.repaint();
        for (int i = 0; i < 10; i++) {
            g.drawImage(copyDefNumber[i], 0, (tileLength + 1) * (i + 1) + 10, this);
            g.drawImage(copyDefLetter[i], (tileLength + 1) * (i + 1), 10, this);
        }

        int x_location, y_location;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                x_location = (tileLength + 1) * (j + 1);
                y_location = (tileLength + 1) * (i + 1) + 10;
                g.drawImage(sea, x_location, y_location, this);
                switch (player[i][j]) {
                    case NEWEMPTY:
                        g.drawImage(newempty, x_location, y_location, this);
                        break;
                    case EMPTY:
                        g.drawImage(empty, x_location, y_location, this);
                        break;
                    case BOAT:
                        g.drawImage(boat, x_location, y_location, this);
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
}
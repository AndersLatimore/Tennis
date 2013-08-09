/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tennis;

/**
 *
 * @author Anders Akesson <andyweb.info>
 */
public class Player {
    private String name;
    private int points;

    public Player() {
        this.name = "";
        this.points = 0;
    }

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
    
}

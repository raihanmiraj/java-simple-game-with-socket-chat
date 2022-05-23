package TileMap;
import Main.*;

import java.awt.*;
import java.awt.Image.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.parser.Entity;

import Entity.Player;
public class TileMap
{

    //position
    private double x;
    private double y;

    //bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    private double tween;

    //map
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    private static int acode = 0;
    public static int getacode(){
        return acode;
    }
    //tile set
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;

    //drawing
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;
    private int flag=0;


    public TileMap(int tileSize)
    {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        tween = 0.07;
    }


    public void loadTiles(String s)
    {
        try
        {
            tileset = ImageIO.read(getClass().getResourceAsStream(s));

            numTilesAcross = tileset.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;
            for (int col = 0; col < numTilesAcross; col++)
            {
                subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subimage, Tile.NORMAL);
                subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void loadMap(String s)
    {
        try
        {
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());

            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;

            xmin = GamePanel.WIDTH - width;
            xmax = 0;
            ymin = GamePanel.HEIGHT - height;
            ymax = 0;

            String delims = "\\s+";
            for (int row = 0; row < numRows; row++)
            {
                String line = br.readLine();
                String[] tokens = line.split(delims);

                for (int col = 0; col < numCols; col++)
                {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public int getTileSize()
    {
        return tileSize;
    }

    public double getx()
    {
        return x;
    }

    public double gety()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getType(int row, int col)
    {
        if(Player.health ==0){
            if(GameServer.newgame==1){
                GameServer.updatestaticint();
            }else{
                GameClient.updatestaticint();
            }
        }else{

        }
        if(flag>0){
            int rc = map[row][col];
            int r = rc / numTilesAcross;
            int c = rc % numTilesAcross;
            return tiles[r][c].getType();
        }
        acode = row;

          if(row>7){
              if(GameServer.newgame==1){
                  GameServer.updatestaticint();
              }else{
                  GameClient.updatestaticint();
              }

            flag++;

          }else if(row<8){
              int rc = map[row][col];
              int r = rc / numTilesAcross;
              int c = rc % numTilesAcross;
              return tiles[r][c].getType();
          }

      return 0;
 }

    public void setTween(double d)
    {
        tween = d;
    }

    public void setPosition(double x,double y)
    {
//        System.out.println(this.x);
//        System.out.println((x - this.x) * tween);

        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

//        System.out.println(this.x + "\n==========");

        fixBounds();

        colOffset = (int)-this.x / tileSize;
        rowOffset = (int)-this.y / tileSize;
    }

    public void fixBounds()
    {
        if(x < xmin) x = xmin;
        if(y < ymin) y = ymin;
        if(x > xmax) x = xmax;
        if(y > ymax) y = ymax;
    }

    public void draw(Graphics2D g)
    {
        for(int row = rowOffset;row < rowOffset + numRowsToDraw;row++)
        {
            if(row >= numRows)
                break;

            for(int col = colOffset;col < colOffset + numColsToDraw;col++)
            {
                if(col >= numCols)
                    break;
                if(map[row][col] == 0)
                    continue;

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                g.drawImage(tiles[r][c].getImage(),(int)x + col * tileSize,(int)y + row * tileSize,null);
            }
        }
    }
}










































package frontend;

import ChessCore.Spot;
import ChessCore.exceptions.NoPieceFoundException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardSpot extends JPanel {

    private Spot spot;
    private int row, column;
    private BufferedImage img;
    ChessBoardFrame parent;
    private boolean isValidTarget;
    private JLabel labelnum;
    private JLabel labelLetter;

    public void fliplabels(int i) {
        //i=0 white turn label at row 7
        //i=1 black turn label at row 0
        if (column == 0) {
            if (labelnum.getHorizontalAlignment() == JLabel.LEFT) {

                labelnum.setHorizontalAlignment(JLabel.RIGHT);
            } else {
                labelnum.setHorizontalAlignment(JLabel.LEFT);
            }
        }

        if (row == 7) {
            if (labelLetter.getVerticalAlignment() == JLabel.BOTTOM) {
                labelLetter.setVerticalAlignment(JLabel.TOP);

            } else {
                labelLetter.setVerticalAlignment(JLabel.BOTTOM);

            }
        }
    }

    public BoardSpot(Spot sp, ChessBoardFrame par) {
        this.spot = sp;
        this.setSize(70, 70);
        this.row = sp.getRow();
        this.column = sp.getColumn();
        this.parent = par;
        this.labelnum = new JLabel();
        this.labelLetter = new JLabel();
    
        //set labels for rows and columns
        labelnum.setFont(new Font("San Serif", Font.BOLD, 12));
        labelnum.setBounds(2, 2, 66, 66);
        labelnum.setVerticalAlignment(JLabel.TOP);
        labelnum.setHorizontalAlignment(JLabel.LEFT);
        labelnum.setForeground(Color.LIGHT_GRAY);
        labelLetter.setFont(new Font("San Serif", Font.BOLD, 12));
        labelLetter.setBounds(2, 2 , 66 , 66);
        labelLetter.setForeground(Color.LIGHT_GRAY);
        labelLetter.setVerticalAlignment(JLabel.BOTTOM);
        labelLetter.setHorizontalAlignment(JLabel.LEFT);
        if (column == 0) {
            labelnum.setText(Integer.toString(8 - row));

        }
        if (row == 7) {
            labelLetter.setText(Character.toString('A' + column));
        }

        
        this.add(labelnum);
        this.add(labelLetter);
        this.setLayout(new BorderLayout());

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (isValidTarget) {
                    parent.movePiece(spot);
                }

                parent.resetAllSpots();
                parent.showAllValidMoves(spot);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });
    }

    public void showAsValidMove() {
        this.setIsValidTarget(true);

        this.revalidate();
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if ((row + column) % 2 == 0) {
            this.setBackground(Color.WHITE);
        } else {
            this.setBackground(Color.DARK_GRAY);
        }

        if (spot.getIsOcuppied()) {
            this.loadImage();
            g.drawImage(img, 0, 0, 70, 70, this);
        }

        if (isValidTarget) {
            this.setBorder(BorderFactory.createLineBorder(Color.green, 3));
        }
    }

    private void loadImage() {
        if (spot.getIsOcuppied()) {
            try {
                img = ImageIO.read(new File(spot.getCurrentPiece().getPieceImg()));    
                
            } catch (IOException | NoPieceFoundException e){
                System.out.println("EXCEPTION FOUND " + spot.getRow() + " " + spot.getColumn());
            }
        }
    }

    public Spot getSpot() {
        return this.spot;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public void setIsValidTarget(boolean isValidTarget) {
        this.isValidTarget = isValidTarget;
    }
}

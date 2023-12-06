/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frontend;

import ChessCore.Controllers.CheckMateController;
import ChessCore.*;
import ChessCore.exceptions.NoPieceFoundException;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author abdul
 */
public class ChessBoardFrame extends javax.swing.JFrame {

    BoardSpot[][] boardSpots = new BoardSpot[8][8];
    ChessGame game;
    Spot clickedOn = null;
    private HomePageFrame parent;
    final private boolean flippable;
    private ChessBoardFrame curr = this;
    private MoveObservor mObs = new MoveObservor();

    /**
     * Creates new form MainFrame
     */
    public ChessBoardFrame(boolean flip , HomePageFrame par) {
        initComponents();
        this.parent = par;
        
        ChessGameBuilder chessGameBuilder = new ChessGameBuilder();
        
        chessGameBuilder.setPlayerWhite(new Player());
        chessGameBuilder.setPlayerBlack(new Player());
        chessGameBuilder.setMoveObservor(mObs);
        game = chessGameBuilder.build();
        
        game.startGame();
        
        this.setSize(1100 , 650);
        this.setResizable(false);
        this.setTitle("Chess Game");
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            
            @Override
            public void windowClosing(WindowEvent e){
                if(!game.gameInProgress){
                    curr.dispose();
                    parent.setVisible(true); 
                }
                else{
                    int res = JOptionPane.showConfirmDialog( null , "Are you sure you want to exit Current Game?" , "Exit Game" , JOptionPane.YES_NO_OPTION);
  
                    if(res == JOptionPane.YES_OPTION){
                        curr.dispose();
                        parent.setVisible(true);   
                    }
                }
            }
        });
        
        jPanel1.setLayout(new java.awt.GridLayout( 8 , 8 ));
        jPanel1.revalidate();
        this.flippable = flip;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardSpots[i][j] = new BoardSpot(game.mainBoard.board[i][j], this);
                jPanel1.add(boardSpots[i][j]);
            }
        }
    }
  
    public void flipboard() {
        if(this.flippable){
            jPanel1.removeAll();
            if (game.turn.getPlayerColor() == ChessCore.enums.Colors.Black) {
                for (int i = 7; i >= 0; i--) {
                    for (int j = 7; j >= 0; j--) {
                        
                        jPanel1.add(boardSpots[i][j]);
                        boardSpots[i][j].fliplabels(1);
                        
                    }
                }
            } else {
                for (int i = 0; i <8 ; i++) {
                    for (int j = 0; j <8; j++) {
                
                        jPanel1.add(boardSpots[i][j]);
                        boardSpots[i][j].fliplabels(0);
                    }
                }
    
            }
        }
    }
    
    private String translate(Spot s){
        String str=" ";
        int row=8-s.getRow(),col=s.getColumn();
        String colstr = Character.toString('A' + col);
        str = str + colstr+ row;
        return str;
    }
    
    private void addTableEntry(Spot clickedOn,Spot target){
        String s = "";
        DefaultTableModel model;
        model = (DefaultTableModel) jTable1.getModel();
        if(game.turn.getPlayerColor()==ChessCore.enums.Colors.White)
            s = s + "White Move";
        else
            s = s + "Black Move";
        
        s = s+" "+translate(clickedOn)+" "+translate(target);
        
        model.addRow(new Object[]{s});   
    }

    public void movePiece(Spot target) {
        
        this.addTableEntry(clickedOn,target);
        
        new Move(clickedOn, target).move(game);
        
        flipboard();
        
        this.resetAllSpots();
                
        this.revalidate();
        this.repaint();
        
        
    }

    public void resetAllSpots() {
        try {
            clickedOn = null;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    boardSpots[i][j].setIsValidTarget(false);
                    boardSpots[i][j].setBorder(null);
                }
            }
            this.revalidate();
            this.repaint();

            if (new CheckMateController(game).kingIsInCheck(game.turn.getPlayerColor())) {
                this.kingInCheck();
            }
        } catch (NoPieceFoundException e) {
        }
    }

    public void showAllValidMoves(Spot currSpot) {
        if(game.gameInProgress){
            try {
                    if (currSpot.getCurrentPiece().getPieceColor() != game.turn.getPlayerColor()) {
                        return;
                    }

                    clickedOn = currSpot;
                    ArrayList<Move> validMoves = game.getAllValidMovesFromSquare(currSpot);
                    for (Move m : validMoves) {
                    boardSpots[m.getFinalPosition().getRow()][m.getFinalPosition().getColumn()].showAsValidMove();
                }
            } catch (NoPieceFoundException e) {}
        }
    }

    public void kingInCheck() {
        CheckMateController checkController = new CheckMateController(game);
        
        Spot s = checkController.getKing(game.turn.getPlayerColor()).getCurrentPosition();
        boardSpots[s.getRow()][s.getColumn()].setBorder(BorderFactory.createLineBorder(Color.red, 3));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(900, 900));
        setSize(new java.awt.Dimension(400, 400));
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setPreferredSize(new java.awt.Dimension(0, 0));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(565, 565));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jLabel8.setText("jLabel1");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Moves Log",
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Undo Move");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(150, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(1063, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(105, 105, 105)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(46, 46, 46)
                    .addComponent(jLabel8)
                    .addContainerGap(522, Short.MAX_VALUE)))
        );

        jPanel1.getAccessibleContext().setAccessibleParent(this);

        getContentPane().add(jPanel2);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        game.undoController.restore(game);
                
        if(this.flippable){
            this.flipboard();
        }
        
        this.resetAllSpots();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChessBoardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChessBoardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChessBoardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChessBoardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChessBoardFrame().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

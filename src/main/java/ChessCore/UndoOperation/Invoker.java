/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessCore.UndoOperation;

import ChessCore.exceptions.NoPieceFoundException;

/**
 *
 * @author abdul
 */
public class Invoker {
    
    public void executeCommand(Command c) throws NoPieceFoundException{
        c.execute();
    }
    
}

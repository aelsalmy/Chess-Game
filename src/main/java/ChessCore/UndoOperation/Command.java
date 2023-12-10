/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ChessCore.UndoOperation;

import ChessCore.exceptions.NoPieceFoundException;

/**
 *
 * @author abdul
 */

//Command Design Patterns
public interface Command {
    
    public void execute() throws NoPieceFoundException;
    
}

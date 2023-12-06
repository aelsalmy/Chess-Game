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
public interface Command {
    
    public void execute() throws NoPieceFoundException;
    
}

package ca.vitos.sudoku.viewmodel

import androidx.lifecycle.ViewModel
import ca.vitos.sudoku.game.SudokuGame

class PlaySudokuViewModel : ViewModel() {

    val sudokuGame = SudokuGame()

}
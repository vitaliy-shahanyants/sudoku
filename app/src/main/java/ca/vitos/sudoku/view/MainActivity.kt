package ca.vitos.sudoku.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ca.vitos.sudoku.R
import ca.vitos.sudoku.view.custom.SudokuAnother
import ca.vitos.sudoku.viewmodel.PlaySudokuViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SudokuAnother.OnToucListener {


    private lateinit var viewModel : PlaySudokuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sudoku.requestFocus()

        sudoku.registerListener(this)

        viewModel = ViewModelProviders.of(this).get(PlaySudokuViewModel::class.java)
        viewModel.sudokuGame.selectedCellLiveData.observe(this, Observer { updateSelectedCellUI(it) })
    }

    private fun updateSelectedCellUI(cell: Pair<Int,Int>?) = cell?.let {
        sudoku.updateSelectedCellUI(cell.first,cell.second)
    }
    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudokuGame.updateSelectedCell(row,col)
    }
}
